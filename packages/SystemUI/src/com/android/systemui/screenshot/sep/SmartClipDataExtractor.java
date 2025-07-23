package com.android.systemui.screenshot.sep;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SmartClipDataExtractor {
    public static final String[] mWhiteWebAppList = {"com.android.chrome", "com.sec.android.app.sbrowser", "com.sec.android.app.sbrowser.beta"};

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class WebData {
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
