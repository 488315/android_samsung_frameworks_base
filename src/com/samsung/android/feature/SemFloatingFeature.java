package com.samsung.android.feature;

import android.os.SystemProperties;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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

    private SemFloatingFeature() throws Throwable {
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

    /* JADX WARN: Can't wrap try/catch for region: R(6:11|(2:86|13)(3:14|(2:16|(0)(2:19|(3:70|21|88)(2:75|26)))|87)|72|30|90|87) */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0079, code lost:
    
        r5 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007a, code lost:
    
        loge(r5.toString());
     */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:62:0x00c0 -> B:79:0x00c7). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void loadFeatureFile() throws Throwable {
        File file;
        FileInputStream fileInputStream = null;
        String name = null;
        FileInputStream fileInputStream2 = null;
        FileInputStream fileInputStream3 = null;
        try {
            try {
                try {
                    this.mFeatureList.clear();
                    file = new File(FEATURE_XML);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (FileNotFoundException e) {
                e = e;
            } catch (XmlPullParserException e2) {
                e = e2;
            }
        } catch (IOException e3) {
            loge(e3.toString());
        }
        if (file.exists() && file.length() > 0) {
            XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
            xmlPullParserFactoryNewInstance.setNamespaceAware(true);
            XmlPullParser xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
            FileInputStream fileInputStream4 = new FileInputStream(file);
            try {
                xmlPullParserNewPullParser.setInput(fileInputStream4, null);
                int eventType = xmlPullParserNewPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        name = xmlPullParserNewPullParser.getName();
                    } else if (eventType == 4) {
                        String text = xmlPullParserNewPullParser.getText();
                        if (name != null && text != null) {
                            if (this.mFeatureList.containsKey(name)) {
                                try {
                                    eventType = xmlPullParserNewPullParser.next();
                                } catch (IOException e4) {
                                    loge(e4.toString());
                                }
                            } else {
                                try {
                                    this.mFeatureList.put(name, text.trim());
                                } catch (Exception e5) {
                                    loge(e5.toString());
                                }
                            }
                        }
                    }
                    eventType = xmlPullParserNewPullParser.next();
                }
                try {
                    fileInputStream4.close();
                } catch (IOException e6) {
                    loge(e6.toString());
                }
                fileInputStream4.close();
            } catch (FileNotFoundException e7) {
                e = e7;
                fileInputStream3 = fileInputStream4;
                loge(e.toString());
                if (fileInputStream3 != null) {
                    fileInputStream3.close();
                    return;
                }
                return;
            } catch (XmlPullParserException e8) {
                e = e8;
                fileInputStream = fileInputStream4;
                loge(e.toString());
                if (fileInputStream != null) {
                    fileInputStream.close();
                    return;
                }
                return;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream4;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e9) {
                        loge(e9.toString());
                    }
                }
                throw th;
            }
            return;
        }
        loge("Cannot read floating_feature.xml file");
    }
}
