package com.android.internal.alsa;

/* loaded from: classes5.dex */
public class LineTokenizer {
    public static final int kTokenNotFound = -1;
    private final String mDelimiters;

    public LineTokenizer(String str) {
        this.mDelimiters = str;
    }

    int nextToken(String str, int i) {
        int length = str.length();
        while (i < length && this.mDelimiters.indexOf(str.charAt(i)) != -1) {
            i++;
        }
        if (i < length) {
            return i;
        }
        return -1;
    }

    int nextDelimiter(String str, int i) {
        int length = str.length();
        while (i < length && this.mDelimiters.indexOf(str.charAt(i)) == -1) {
            i++;
        }
        if (i < length) {
            return i;
        }
        return -1;
    }
}
