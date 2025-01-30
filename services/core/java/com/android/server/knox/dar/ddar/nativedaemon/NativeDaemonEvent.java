package com.android.server.knox.dar.ddar.nativedaemon;

import java.io.FileDescriptor;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class NativeDaemonEvent {
    public final int mCmdNumber;
    public final int mCode;
    public FileDescriptor[] mFdList;
    public final String mLogMessage;
    public final String mMessage;
    public String[] mParsed = null;
    public final String mRawEvent;
    public final int mResponseCode;

    public static boolean isClassUnsolicited(int i) {
        return i >= 600 && i < 700;
    }

    public NativeDaemonEvent(int i, int i2, int i3, String str, String str2, String str3, FileDescriptor[] fileDescriptorArr) {
        this.mCmdNumber = i;
        this.mCode = i2;
        this.mResponseCode = i3;
        this.mMessage = str;
        this.mRawEvent = str2;
        this.mLogMessage = str3;
        this.mFdList = fileDescriptorArr;
    }

    public int getCmdNumber() {
        return this.mCmdNumber;
    }

    public int getCode() {
        return this.mCode;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String toString() {
        return this.mLogMessage;
    }

    public boolean isClassContinue() {
        int i = this.mCode;
        return i >= 100 && i < 200;
    }

    public boolean isClassOk() {
        int i = this.mCode;
        return i >= 200 && i < 300;
    }

    public boolean isClassClientError() {
        int i = this.mCode;
        return i >= 500 && i < 600;
    }

    public boolean isClassUnsolicited() {
        return isClassUnsolicited(this.mCode);
    }

    public static NativeDaemonEvent parseRawEvent(String str, FileDescriptor[] fileDescriptorArr) {
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
            if (isClassUnsolicited(parseInt)) {
                i = -1;
                i2 = -1;
            } else {
                if (split.length < 2) {
                    throw new IllegalArgumentException("Insufficient arguemnts");
                }
                try {
                    i2 = Integer.parseInt(split[1]);
                    int length2 = length + split[1].length() + 1;
                    if (split.length < 3) {
                        throw new IllegalArgumentException("Insufficient arguemnts");
                    }
                    try {
                        int parseInt2 = Integer.parseInt(split[2]);
                        length = length2 + split[2].length() + 1;
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
                str2 = split[0] + " " + split[1] + " {}";
            }
            return new NativeDaemonEvent(i2, parseInt, i, str.substring(length), str, str2, fileDescriptorArr);
        } catch (NumberFormatException e3) {
            throw new IllegalArgumentException("problem parsing code", e3);
        }
    }

    public static String[] unescapeArgs(String str) {
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
            i += substring.length();
            if (i2 == 0) {
                substring = substring.trim();
            } else {
                i++;
            }
            arrayList.add(substring.replace("\\\\", "\\").replace("\\\"", "\""));
            int indexOf = str.indexOf(32, i);
            int indexOf2 = str.indexOf(" \"", i);
            if (indexOf2 <= -1 || indexOf2 > indexOf) {
                if (indexOf > -1) {
                    i = indexOf + 1;
                }
                i2 = 0;
            } else {
                i2 = 1;
                i = indexOf2 + 2;
            }
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
