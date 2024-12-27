package com.android.systemui.screenshot.sep;

public final class SmartClipDataExtractor {
    public static final String[] mWhiteWebAppList = {"com.android.chrome", "com.sec.android.app.sbrowser", "com.sec.android.app.sbrowser.beta"};

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
