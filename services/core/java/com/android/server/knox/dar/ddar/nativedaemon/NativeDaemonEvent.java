package com.android.server.knox.dar.ddar.nativedaemon;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NativeDaemonEvent {
    public final int mCmdNumber;
    public final int mCode;
    public final String mLogMessage;
    public final String mMessage;
    public final int mResponseCode;

    public NativeDaemonEvent(int i, int i2, int i3, String str, String str2) {
        this.mCmdNumber = i;
        this.mCode = i2;
        this.mResponseCode = i3;
        this.mMessage = str;
        this.mLogMessage = str2;
    }

    public static NativeDaemonEvent parseRawEvent(String str) {
        int i;
        int i2;
        String str2;
        String[] split = str.split(" ");
        if (split.length < 2) {
            throw new IllegalArgumentException("Insufficient arguments");
        }
        try {
            int parseInt = Integer.parseInt(split[0]);
            int length = split[0].length() + 1;
            if (parseInt >= 600 && parseInt < 700) {
                i = -1;
                i2 = -1;
            } else {
                if (split.length < 2) {
                    throw new IllegalArgumentException("Insufficient arguemnts");
                }
                try {
                    i2 = Integer.parseInt(split[1]);
                    int length2 = split[1].length() + 1 + length;
                    if (split.length < 3) {
                        throw new IllegalArgumentException("Insufficient arguemnts");
                    }
                    try {
                        int parseInt2 = Integer.parseInt(split[2]);
                        length = split[2].length() + 1 + length2;
                        i = parseInt2;
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("problem parsing responseCode", e);
                    }
                } catch (NumberFormatException e2) {
                    throw new IllegalArgumentException("problem parsing cmdNumber", e2);
                }
            }
            if (split.length <= 3 || !split[3].equals("{{sensitive}}")) {
                str2 = str;
            } else {
                length += split[3].length() + 1;
                StringBuilder sb = new StringBuilder();
                sb.append(split[0]);
                sb.append(" ");
                str2 = AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, split[1], " {}");
            }
            return new NativeDaemonEvent(i2, parseInt, i, str.substring(length), str2);
        } catch (NumberFormatException e3) {
            throw new IllegalArgumentException("problem parsing code", e3);
        }
    }

    public static void unescapeArgs(String str) {
        ArrayList arrayList = new ArrayList();
        int length = str.length();
        int i = str.charAt(0) == '\"' ? 1 : 0;
        int i2 = i;
        while (i < length) {
            char c = i2 != 0 ? '\"' : ' ';
            int i3 = i;
            while (i3 < length && str.charAt(i3) != c) {
                if (str.charAt(i3) == '\\') {
                    i3++;
                }
                i3++;
            }
            if (i3 > length) {
                i3 = length;
            }
            String substring = str.substring(i, i3);
            int length2 = substring.length() + i;
            if (i2 == 0) {
                substring = substring.trim();
            } else {
                length2++;
            }
            arrayList.add(substring.replace("\\\\", "\\").replace("\\\"", "\""));
            int indexOf = str.indexOf(32, length2);
            int indexOf2 = str.indexOf(" \"", length2);
            if (indexOf2 > -1 && indexOf2 <= indexOf) {
                i = indexOf2 + 2;
                i2 = 1;
            } else if (indexOf > -1) {
                i = indexOf + 1;
                i2 = 0;
            } else {
                i2 = 0;
                i = length2;
            }
        }
    }

    public final boolean isClassContinue() {
        int i = this.mCode;
        return i >= 100 && i < 200;
    }

    public final boolean isClassOk() {
        int i = this.mCode;
        return i >= 200 && i < 300;
    }

    public final String toString() {
        return this.mLogMessage;
    }
}
