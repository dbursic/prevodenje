package hr.istratech.prevodenje.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by dbursic on 3.10.2017..
 */
public class DirectoryUtils {
    public static File[] getFileListForApplication(File path, final String aplikacija) {
        File[] list = null;
        if (path.exists() && path.isDirectory()) {
            list = path.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (aplikacija.equals("GAS"))
                        return (name.toUpperCase().startsWith(aplikacija) || name.toUpperCase().startsWith("NST")) && (name.endsWith(".fmb") || name.endsWith(".FMB"));
                    return name.toUpperCase().startsWith(aplikacija) && (name.endsWith(".fmb") || name.endsWith(".FMB"));
                }
            });
        } else {
            System.out.println("I'm sorry. I can't find directory " + path);
        }
        return list;
    }

    public static File[] getFileListForExtension(File path, final String extension) {
        File[] list = null;
        if (path.exists() && path.isDirectory()) {
            list = path.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(extension);
                }
            });
        } else {
            System.out.println("I'm sorry. I can't find the file" + path);
        }
        return list;
    }

    public static File[] getMenues(File path, final String aplikacija) {
        File[] list = null;
        if (path.exists() && path.isDirectory()) {
            list = path.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (aplikacija.equals("ZMP"))
                        return (name.toUpperCase().startsWith(aplikacija) || name.toUpperCase().startsWith("CRM")) && (name.endsWith(".mmb") || name.toUpperCase().endsWith(".MMB"));
                    return name.toUpperCase().startsWith(aplikacija) && (name.endsWith(".mmb") || name.endsWith(".MMB"));
                }
            });
        } else {
            System.out.println("I'm sorry. I can't find the file " + path);
        }
        return list;
    }
}
