package cn.afternode.liquidloader;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.file.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class LiquidLoader {
    public static final boolean debug = false;

    private final Logger logger;
    private final List<Plugin> loadedPlugins;

    private boolean modulesLoaded = false;
    private boolean commandsLoaded = false;

    public LiquidLoader() {
        logger = LogManager.getLogger("LiquidLoader");
        loadedPlugins = new ArrayList<>();
    }

    /**
     * Load plugins from a folder
     * @return Array of loaded plugins
     */
    public Plugin[] loadPlugins(File folder) {
        if (!folder.exists() || !folder.isDirectory()) throw new IllegalArgumentException();

        File[] pluginFiles  = folder.listFiles(new JarFilter());
        if (pluginFiles == null) return null;

        logger.info("Loading for " + pluginFiles.length + " jars in " + folder.getAbsolutePath());

        List<Plugin> plugin = new ArrayList<>();
        for (File pluginFile : pluginFiles) {
            try {
                plugin.add(loadPlugin(pluginFile));
            } catch (Exception e) {
                logger.error("Error loading jar: ", e);
            }
        }
        return plugin.toArray(new Plugin[0]);
    }

    /**
     * Load plugins from a jar file
     */
    public Plugin loadPlugin(File pluginFile) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        URLClassLoader ucl = new URLClassLoader(new URL[]{new URL("file:" + pluginFile.getAbsolutePath())}, LiquidBounce.class.getClassLoader());

        JarFile jf = new JarFile(pluginFile);
        Enumeration<JarEntry> entries = jf.entries();
        PluginDescription pd;
        try {
            JarEntry descriptionFile = jf.getJarEntry("plugin.yml");
            pd = new Yaml().loadAs(jf.getInputStream(descriptionFile), PluginDescription.class);
        } catch (Exception e) {
            logger.error("Error loading plugin info", e);
            throw new IllegalArgumentException(e);
        }

        List<Class<?>> pluginClasses = new ArrayList<>();
        Class<?> mainClass = null;
        while (entries.hasMoreElements()) {
            JarEntry je = entries.nextElement();
            if (je.getName().endsWith(".class")) {
                Class<?> c = ucl.loadClass(je.getName().split("\\.")[0].replace("/", "."));
                pluginClasses.add(c);
                if (debug) logger.info("Load class " + c.getName() + " from entry " + je.getName());
                if (c.getName().equals(pd.main)) mainClass = c;
            }
        }
        if (mainClass == null) {
            throw new NullPointerException("Main class not found: " + pd.main);
        }

        Object o = mainClass.getDeclaredConstructor().newInstance();
        if (!(o instanceof Plugin)) throw new IllegalArgumentException("Invalid plugin main class: " + pd.main);
        Plugin p = (Plugin) o;

        p.setPd(pd);

        logger.info("Loading " + pd.name + " v" + pd.version + ", by " + pd.author);
        p.onLoad();
        loadedPlugins.add(p);

        return p;
    }

    public void loadModules(ModuleManager mm) {
        if (modulesLoaded) return;
        for (Plugin p: loadedPlugins) {
            try {
                p.registerModules(mm);
            } catch (Exception e) {
                logger.error("Error loading modules of plugin: " + p.getName(), e);
            }
        }
        modulesLoaded = true;
    }

    public void loadCommands(CommandManager cm) {
        if (commandsLoaded) return;
        for (Plugin p: loadedPlugins) {
            try {
                p.registerCommands(cm);
            } catch (Exception e) {
                logger.error("Error loading commands of plugin: " + p.getName(), e);
            }
        }
        commandsLoaded = true;
    }

    public Logger getLogger() {
        return logger;
    }
}
