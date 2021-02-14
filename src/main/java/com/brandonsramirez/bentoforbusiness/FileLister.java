package com.brandonsramirez.bentoforbusiness;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileLister {
    private static final Comparator<File> FILE_COMPARATOR = Comparator
            .comparingLong(File::length).reversed()
            .thenComparing(File::getName);

    static List<File> bySize(File baseDirectory) {
        return collectFiles(baseDirectory)
                .stream()
                .sorted(FILE_COMPARATOR)
                .collect(Collectors.toList());
    }

    private static List<File> collectFiles(File directory) {
        List<File> result = new LinkedList<>(Arrays.asList(directory.listFiles(File::isFile)));

        for (File subdirectory : directory.listFiles(File::isDirectory)) {
            result.addAll(collectFiles(subdirectory));
        }

        return result;
    }

    static String formattedFileSize(File f) {
        return formattedFileSize(f.length());
    }

    static String formattedFileSize(long length) {
        if (length >= 1000000000) {
            return (length / 1000000000.0f) + " GB";
        } else if (length >= 1000000) {
            return (length / 1000000.0f) + " MB";
        } else if (length >= 1000) {
            return (length / 1000.0f) + " KB";
        }

        return String.valueOf(length);
    }

    private static void displayFileEntry(File f) {
        System.out.println(formattedFileSize(f) + " " + f.getAbsolutePath());
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(usage());
            System.exit(1);
        }

        File directory = new File(args[0]);

        if (!directory.exists()) {
            System.err.println("Directory does not exist.");
            System.exit(2);
        }

        if (!directory.isDirectory()) {
            System.err.println("Not a directory");
            System.exit(3);
        }

        bySize(directory).forEach(FileLister::displayFileEntry);
    }

    private static String usage() {
        return "Usage: java FileLister </path/to/directory>";
    }
}
