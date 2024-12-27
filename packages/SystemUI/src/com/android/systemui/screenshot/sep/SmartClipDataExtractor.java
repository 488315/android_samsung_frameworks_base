package com.android.systemui.screenshot.sep;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SmartClipDataExtractor {
    public static final String[] mWhiteWebAppList = {"com.android.chrome", "com.sec.android.app.sbrowser", "com.sec.android.app.sbrowser.beta"};

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
