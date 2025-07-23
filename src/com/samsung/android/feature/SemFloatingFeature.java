package com.samsung.android.feature;

import android.os.SystemProperties;
import android.util.Log;
import java.util.Hashtable;

/* loaded from: classes6.dex */
public final class SemFloatingFeature implements IFloatingFeature {
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    private static final int DEFAULT_INT_VALUE = -1;
    private static final String DEFAULT_STRING_VALUE = "";
    private static final String FEATURE_XML = "/system/etc/floating_feature.xml";
    private static final boolean LOG_ENABLED;
    private static final String TAG = "SemFloatingFeature";
    private static final SemFloatingFeature sInstance;
    private final Hashtable<String, String> mFeatureList = new Hashtable<>();

    static {
        boolean z;
        try {
            z = Boolean.parseBoolean(SystemProperties.get("ro.product_ship"));
        } catch (Exception unused) {
            z = true;
        }
        LOG_ENABLED = true ^ z;
        sInstance = new SemFloatingFeature();
    }

    private static void logw(Object obj) {
        if (LOG_ENABLED) {
            Log.w(TAG, obj.toString());
        }
    }

    private static void loge(Object obj) {
        if (LOG_ENABLED) {
            Log.e(TAG, obj.toString());
        }
    }

    public static SemFloatingFeature getInstance() {
        return sInstance;
    }

    private SemFloatingFeature() {
        try {
            loadFeatureFile();
        } catch (Exception e) {
            loge(e);
        }
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public boolean getBoolean(String str) {
        if (str == null) {
            loge("The first argument of getBoolean() cannot be null.");
            return false;
        }
        String str2 = this.mFeatureList.get(str);
        if (str2 == null) {
            return false;
        }
        return Boolean.parseBoolean(str2);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public boolean getBoolean(String str, boolean z) {
        logw("You called API `boolean getBoolean(String tag, String defaultValue)` with feature [" + str + "].It has been deprecated after android Q. Instead, please Use `boolean getBoolean(String tag)`");
        if (str == null) {
            loge("The first argument of getBoolean() cannot be null.");
            return false;
        }
        String str2 = this.mFeatureList.get(str);
        return str2 == null ? z : Boolean.parseBoolean(str2);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public String getString(String str) {
        if (str == null) {
            loge("The first argument of getString() cannot be null.");
            return "";
        }
        String str2 = this.mFeatureList.get(str);
        return str2 == null ? "" : str2;
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public String getString(String str, String str2) {
        logw("You called API `String getString(String tag, String defaultValue)` with feature [" + str + "].It has been deprecated after android Q. Instead, please Use `String getString(String tag)`");
        if (str == null) {
            loge("The first argument of getString() cannot be null.");
            return "";
        }
        String str3 = this.mFeatureList.get(str);
        return str3 == null ? str2 : str3;
    }

    public int getInteger(String str) {
        return getInt(str);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public int getInt(String str) {
        if (str == null) {
            loge("The first argument of getInt() cannot be null.");
            return -1;
        }
        String str2 = this.mFeatureList.get(str);
        if (str2 == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str2);
        } catch (Exception unused) {
            loge(String.format("[%s] cannot be parsed to Integer value", str2));
            return -1;
        }
    }

    @Deprecated
    public int getInteger(String str, int i) {
        return getInt(str, i);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public int getInt(String str, int i) {
        logw("You called API `int getInt(String tag, int defaultValue)` with feature [" + str + "].It has been deprecated after android Q. Instead, please Use `int getInt(String tag)`");
        if (str == null) {
            loge("The first argument of getInt() cannot be null.");
            return i;
        }
        String str2 = this.mFeatureList.get(str);
        if (str2 == null) {
            return i;
        }
        try {
            return Integer.parseInt(str2);
        } catch (Exception unused) {
            loge(String.format("[%s] cannot be parsed to Integer value", str2));
            return i;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(6:13|(2:42|43)(2:15|(2:17|(2:20|(4:22|23|25|26)(2:30|31))))|35|36|38|26) */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0079, code lost:
    
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x007a, code lost:
    
        loge(r5.toString());
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x00c0 -> B:41:0x00c7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void loadFeatureFile() {
        /*
            r7 = this;
            r0 = 0
            java.util.Hashtable<java.lang.String, java.lang.String> r1 = r7.mFeatureList     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            r1.clear()     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            java.lang.String r2 = "/system/etc/floating_feature.xml"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            boolean r2 = r1.exists()     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            if (r2 == 0) goto L9b
            long r2 = r1.length()     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            r4 = 0
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 > 0) goto L1f
            goto L9b
        L1f:
            org.xmlpull.v1.XmlPullParserFactory r2 = org.xmlpull.v1.XmlPullParserFactory.newInstance()     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            r3 = 1
            r2.setNamespaceAware(r3)     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            org.xmlpull.v1.XmlPullParser r2 = r2.newPullParser()     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            r4.<init>(r1)     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            r2.setInput(r4, r0)     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            int r1 = r2.getEventType()     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
        L37:
            if (r1 == r3) goto L82
            r5 = 2
            if (r1 != r5) goto L41
            java.lang.String r0 = r2.getName()     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            goto L74
        L41:
            r5 = 4
            if (r1 != r5) goto L74
            java.lang.String r5 = r2.getText()     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            if (r0 == 0) goto L74
            if (r5 == 0) goto L74
            java.util.Hashtable<java.lang.String, java.lang.String> r6 = r7.mFeatureList     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            boolean r6 = r6.containsKey(r0)     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            if (r6 == 0) goto L62
            int r1 = r2.next()     // Catch: java.io.IOException -> L59 java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            goto L37
        L59:
            r5 = move-exception
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            loge(r5)     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            goto L37
        L62:
            java.lang.String r5 = r5.trim()     // Catch: java.lang.Exception -> L6c java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            java.util.Hashtable<java.lang.String, java.lang.String> r6 = r7.mFeatureList     // Catch: java.lang.Exception -> L6c java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            r6.put(r0, r5)     // Catch: java.lang.Exception -> L6c java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            goto L74
        L6c:
            r5 = move-exception
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            loge(r5)     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
        L74:
            int r1 = r2.next()     // Catch: java.io.IOException -> L79 java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            goto L37
        L79:
            r5 = move-exception
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            loge(r5)     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            goto L37
        L82:
            r4.close()     // Catch: java.io.IOException -> L86 java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            goto L8e
        L86:
            r7 = move-exception
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
            loge(r7)     // Catch: java.lang.Throwable -> L92 java.io.FileNotFoundException -> L95 org.xmlpull.v1.XmlPullParserException -> L98
        L8e:
            r4.close()     // Catch: java.io.IOException -> Lbf
            goto Lc7
        L92:
            r7 = move-exception
            r0 = r4
            goto Lc8
        L95:
            r7 = move-exception
            r0 = r4
            goto La4
        L98:
            r7 = move-exception
            r0 = r4
            goto Lb2
        L9b:
            java.lang.String r7 = "Cannot read floating_feature.xml file"
            loge(r7)     // Catch: java.lang.Throwable -> La1 java.io.FileNotFoundException -> La3 org.xmlpull.v1.XmlPullParserException -> Lb1
            return
        La1:
            r7 = move-exception
            goto Lc8
        La3:
            r7 = move-exception
        La4:
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> La1
            loge(r7)     // Catch: java.lang.Throwable -> La1
            if (r0 == 0) goto Lc7
            r0.close()     // Catch: java.io.IOException -> Lbf
            goto Lc7
        Lb1:
            r7 = move-exception
        Lb2:
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> La1
            loge(r7)     // Catch: java.lang.Throwable -> La1
            if (r0 == 0) goto Lc7
            r0.close()     // Catch: java.io.IOException -> Lbf
            goto Lc7
        Lbf:
            r7 = move-exception
            java.lang.String r7 = r7.toString()
            loge(r7)
        Lc7:
            return
        Lc8:
            if (r0 == 0) goto Ld6
            r0.close()     // Catch: java.io.IOException -> Lce
            goto Ld6
        Lce:
            r0 = move-exception
            java.lang.String r0 = r0.toString()
            loge(r0)
        Ld6:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.feature.SemFloatingFeature.loadFeatureFile():void");
    }
}
