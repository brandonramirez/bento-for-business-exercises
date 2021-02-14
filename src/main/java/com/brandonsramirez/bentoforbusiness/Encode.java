package com.brandonsramirez.bentoforbusiness;

public class Encode {
    public static String encode(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        StringBuilder out = new StringBuilder(s.length());

        char[] chars = s.toCharArray();

        char lastChar = chars[0];
        int runLength = 1;

        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != lastChar) {
                out.append(lastChar);
                if (runLength > 1) {
                    out.append(runLength);
                }
                lastChar = chars[i];
                runLength = 1;
            } else {
                runLength++;
            }
        }

        out.append(lastChar);
        if (runLength > 1) {
            out.append(runLength);
        }

        return out.toString();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(usage());
            System.exit(1);
        }

        System.out.println(encode(args[0]));
    }

    private static String usage() {
        return "Usage: java Encode <word>";
    }
}
