# bento-for-business-exercises

## Building and testing

Gradle can be used to build the program and run its unit tests.

    $ ./gradlew build test

## Exercise 1: File Lister

The `FileLister` program can be run from the command line:

Exactly one argument must be supplied, the path to the target directory
whose contents you want to list.

File paths are listed along with their sizes, sorted by size,
with largest file first.

    $ java -cp build/libs/bento-for-business-exercises.jar com.brandonsramirez.bentoforbusiness.FileLister
    Usage: java FileLister </path/to/directory>
    $

    $ java -cp build/libs/bento-for-business-exercises.jar com.brandonsramirez.bentoforbusiness.FileLister /Users/bramirez/Desktop/personal/bento-for-business-exercises/src
    5.428 KB /Users/bramirez/Desktop/personal/bento-for-business-exercises/src/test/java/com/brandonsramirez/bentoforbusiness/FileListerTest.java
    1.952 KB /Users/bramirez/Desktop/personal/bento-for-business-exercises/src/main/java/com/brandonsramirez/bentoforbusiness/FileLister.java
    378 /Users/bramirez/Desktop/personal/bento-for-business-exercises/src/test/java/com/brandonsramirez/bentoforbusiness/AppTest.java
    317 /Users/bramirez/Desktop/personal/bento-for-business-exercises/src/main/java/com/brandonsramirez/bentoforbusiness/App.java
    $

### Assumptions

I worked on this exercise over the weekend, therefore I had to make assumptions.

1. The instructions called for sub-directories to be checked, but did not mention
sub-directories of sub-directories.  I assumed we'd want the program to check the
file system recursively, so that is how it works.
2. It wasn't specified whether files should be sorted with largest first or smallest
first, just sorted by size.  I took the liberty of assuming we'd want to see the
largest file first, but that can be easily adjusted.
3. File sizes are displayed in a human readable manner, such as "1.32 GB".
4. The file name is not explicitly listed because it is included as part of the path.
5. We don't need to handle symbolic links.
6. If the specified directory does not exist or is not a directory, a friendly
error message is acceptable.
