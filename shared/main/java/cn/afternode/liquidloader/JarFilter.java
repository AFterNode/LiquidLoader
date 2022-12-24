package cn.afternode.liquidloader;

import java.io.File;
import java.io.FilenameFilter;

public class JarFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".jar");
    }
}
