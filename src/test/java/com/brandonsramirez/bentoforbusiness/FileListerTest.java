package com.brandonsramirez.bentoforbusiness;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileListerTest {
    private Path testBaseDirectory;

    @Before
    public void setUp() throws IOException {
        testBaseDirectory = Files.createTempDirectory("test");
    }

    @After
    public void tearDown() {
        delete(testBaseDirectory.toFile());
    }

    private static void delete(File f) {
        if (f.isDirectory()) {
            for (File child : f.listFiles()) {
                delete(child);
            }
        }

        f.delete();
    }

    @Test
    public void returnsEmptyListForEmptyDirectory() {
        assertTrue(FileLister.bySize(testBaseDirectory.toFile()).isEmpty());
    }

    @Test
    public void returnsSingleFileWhenSingleFileExists() throws IOException {
        File f = new File(testBaseDirectory.toFile(), "hello.txt");
        Files.createFile(f.toPath());

        assertEquals(1, FileLister.bySize(testBaseDirectory.toFile()).size());
    }

    @Test
    public void doesNotReturnDirectories() throws IOException {
        File f = new File(testBaseDirectory.toFile(), "hello.txt");
        Files.createFile(f.toPath());

        File emptySubDir = new File(testBaseDirectory.toFile(), "child");
        emptySubDir.mkdir();

        List<File> files = FileLister.bySize(testBaseDirectory.toFile());

        assertEquals(1, files.size());
        assertEquals("hello.txt", files.get(0).getName());
    }

    @Test
    public void returnsFilesFromSubdirectories() throws IOException {
        File subdir = new File(testBaseDirectory.toFile(), "child");
        subdir.mkdir();

        File f1 = new File(subdir, "hello.txt");
        Files.createFile(f1.toPath());
        File f2 = new File(subdir, "world.txt");
        Files.createFile(f2.toPath());

        assertEquals(2, FileLister.bySize(testBaseDirectory.toFile()).size());
    }

    @Test
    public void returnsFilesFromSubdirectoriesOfSubdirectories() throws IOException {
        File child = new File(testBaseDirectory.toFile(), "child");
        File grandchild = new File(child, "grandchild");
        grandchild.mkdirs();

        assertEquals(0, FileLister.bySize(testBaseDirectory.toFile()).size());

        File f1 = new File(grandchild, "hello.txt");
        Files.createFile(f1.toPath());
        File f2 = new File(grandchild, "world.txt");
        Files.createFile(f2.toPath());

        assertEquals(2, FileLister.bySize(testBaseDirectory.toFile()).size());
    }

    @Test
    public void filesReturnedInDecendingOrderBySize() throws IOException {
        // File names are intentionally obscure to ensure we don't allow sorting by file name to pass the test.

        File smallestFile = new File(testBaseDirectory.toFile(), "notabigfile.txt");
        Files.write(smallestFile.toPath(), Arrays.asList("first line"));

        File secondBiggestFile = new File(testBaseDirectory.toFile(), "elephant.txt");
        Files.write(secondBiggestFile.toPath(), Arrays.asList("first line", "second line"));

        File biggestFile = new File(testBaseDirectory.toFile(), "zzz.txt");
        Files.write(biggestFile.toPath(), Arrays.asList("first line", "second line", "third line"));

        List<File> files = FileLister.bySize(testBaseDirectory.toFile());

        assertEquals("zzz.txt", files.get(0).getName());
        assertEquals("elephant.txt", files.get(1).getName());
        assertEquals("notabigfile.txt", files.get(2).getName());
    }

    @Test
    public void filesOfSameSizeAreSortedAlphabeticallyByName() throws IOException {
        File catFile = new File(testBaseDirectory.toFile(), "cat.txt");
        Files.write(catFile.toPath(), Arrays.asList("test"));

        File bananaFile = new File(testBaseDirectory.toFile(), "banana.txt");
        Files.write(bananaFile.toPath(), Arrays.asList("test"));

        File appleFile = new File(testBaseDirectory.toFile(), "apple.txt");
        Files.write(appleFile.toPath(), Arrays.asList("test"));

        List<File> files = FileLister.bySize(testBaseDirectory.toFile());

        assertEquals("apple.txt", files.get(0).getName());
        assertEquals("banana.txt", files.get(1).getName());
        assertEquals("cat.txt", files.get(2).getName());
    }

    @Test
    public void fileSizesExpressedInGigabytes() {
        assertEquals("31.29995 GB", FileLister.formattedFileSize(31299949684L));
        assertEquals("3.1289952 GB", FileLister.formattedFileSize(3128994968L));
        assertEquals("1.0 GB", FileLister.formattedFileSize(1000000000));
    }

    @Test
    public void fileSizesExpressedInMegabytes() {
        assertEquals("31.299948 MB", FileLister.formattedFileSize(31299949L));
        assertEquals("3.128994 MB", FileLister.formattedFileSize(3128994L));
        assertEquals("1.0 MB", FileLister.formattedFileSize(1000000));
    }

    @Test
    public void fileSizesExpressedInKilybytes() {
        assertEquals("31.299 KB", FileLister.formattedFileSize(31299L));
        assertEquals("3.128 KB", FileLister.formattedFileSize(3128L));
        assertEquals("1.0 KB", FileLister.formattedFileSize(1000));
    }
}
