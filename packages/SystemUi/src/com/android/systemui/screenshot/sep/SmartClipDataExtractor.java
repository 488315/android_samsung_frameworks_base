package com.android.systemui.screenshot.sep;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SmartClipDataExtractor {
    public static final String[] mWhiteWebAppList = {"com.android.chrome", "com.sec.android.app.sbrowser", "com.sec.android.app.sbrowser.beta"};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WebData {
        public final String mAppPkgName;
        public final String mUrl;

        public WebData(String str, String str2) {
            this.mUrl = str;
            this.mAppPkgName = str2;
        }

        public final String toString() {
            return "WebData: pkg=" + this.mAppPkgName + " URL=" + this.mUrl;
        }
    }
}
