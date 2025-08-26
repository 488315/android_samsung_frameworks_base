package android.support.v4.media;

import android.util.Log;

/* loaded from: classes.dex */
public abstract /* synthetic */ class MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0 {
    public static String m(int i, String str) {
        return str + i;
    }

    public static StringBuilder m(int i, String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(i);
        sb.append(str2);
        return sb;
    }

    public static StringBuilder m(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    public static void m(String str, String str2, String str3) {
        Log.d(str3, str + str2);
    }
}
