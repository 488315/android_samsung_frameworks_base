package com.samsung.android.feature;

import android.p009os.SystemProperties;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes5.dex */
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
        boolean productShip = true;
        try {
            productShip = Boolean.parseBoolean(SystemProperties.get("ro.product_ship"));
        } catch (Exception e) {
        }
        LOG_ENABLED = !productShip;
        sInstance = new SemFloatingFeature();
    }

    private static void logw(Object message) {
        if (LOG_ENABLED) {
            Log.m102w(TAG, message.toString());
        }
    }

    private static void loge(Object message) {
        if (LOG_ENABLED) {
            Log.m96e(TAG, message.toString());
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
    public boolean getBoolean(String tag) {
        if (tag == null) {
            loge("The first argument of getBoolean() cannot be null.");
            return false;
        }
        String original = this.mFeatureList.get(tag);
        if (original == null) {
            return false;
        }
        return Boolean.parseBoolean(original);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public boolean getBoolean(String tag, boolean defaultValue) {
        logw("You called API `boolean getBoolean(String tag, String defaultValue)` with feature [" + tag + "].It has been deprecated after android Q. Instead, please Use `boolean getBoolean(String tag)`");
        if (tag == null) {
            loge("The first argument of getBoolean() cannot be null.");
            return false;
        }
        String original = this.mFeatureList.get(tag);
        return original == null ? defaultValue : Boolean.parseBoolean(original);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public String getString(String tag) {
        if (tag == null) {
            loge("The first argument of getString() cannot be null.");
            return "";
        }
        String original = this.mFeatureList.get(tag);
        return original == null ? "" : original;
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public String getString(String tag, String defaultValue) {
        logw("You called API `String getString(String tag, String defaultValue)` with feature [" + tag + "].It has been deprecated after android Q. Instead, please Use `String getString(String tag)`");
        if (tag == null) {
            loge("The first argument of getString() cannot be null.");
            return "";
        }
        String original = this.mFeatureList.get(tag);
        return original == null ? defaultValue : original;
    }

    public int getInteger(String tag) {
        return getInt(tag);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    public int getInt(String tag) {
        if (tag == null) {
            loge("The first argument of getInt() cannot be null.");
            return -1;
        }
        String original = this.mFeatureList.get(tag);
        if (original == null) {
            return -1;
        }
        try {
            return Integer.parseInt(original);
        } catch (Exception e) {
            loge(String.format("[%s] cannot be parsed to Integer value", original));
            return -1;
        }
    }

    @Deprecated
    public int getInteger(String tag, int defaultValue) {
        return getInt(tag, defaultValue);
    }

    @Override // com.samsung.android.feature.IFloatingFeature
    @Deprecated
    public int getInt(String tag, int defaultValue) {
        logw("You called API `int getInt(String tag, int defaultValue)` with feature [" + tag + "].It has been deprecated after android Q. Instead, please Use `int getInt(String tag)`");
        if (tag == null) {
            loge("The first argument of getInt() cannot be null.");
            return defaultValue;
        }
        String original = this.mFeatureList.get(tag);
        if (original == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(original);
        } catch (Exception e) {
            loge(String.format("[%s] cannot be parsed to Integer value", original));
            return defaultValue;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:11|(2:44|45)(2:13|(2:15|(2:18|(6:20|21|23|24|25|26)(2:30|31))))|35|36|38|39|40|26) */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0086, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0087, code lost:
    
        loge(r8.toString());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void loadFeatureFile() {
        InputStream fi = null;
        String TagName = null;
        try {
            try {
                try {
                    try {
                        this.mFeatureList.clear();
                        File featureXmlFile = new File(FEATURE_XML);
                        if (featureXmlFile.exists() && featureXmlFile.length() > 0) {
                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            factory.setNamespaceAware(true);
                            XmlPullParser parser = factory.newPullParser();
                            InputStream fi2 = new FileInputStream(featureXmlFile);
                            parser.setInput(fi2, null);
                            int eventType = parser.getEventType();
                            while (eventType != 1) {
                                if (eventType == 2) {
                                    TagName = parser.getName();
                                } else if (eventType == 4) {
                                    String TagValue = parser.getText();
                                    if (TagName != null && TagValue != null) {
                                        if (this.mFeatureList.containsKey(TagName)) {
                                            try {
                                                eventType = parser.next();
                                            } catch (IOException e) {
                                                loge(e.toString());
                                            }
                                        } else {
                                            try {
                                                this.mFeatureList.put(TagName, TagValue.trim());
                                            } catch (Exception ex) {
                                                loge(ex.toString());
                                            }
                                        }
                                    }
                                }
                                eventType = parser.next();
                            }
                            try {
                                fi2.close();
                            } catch (IOException e2) {
                                loge(e2.toString());
                            }
                            try {
                                fi2.close();
                                return;
                            } catch (IOException e3) {
                                loge(e3.toString());
                                return;
                            }
                        }
                        loge("Cannot read floating_feature.xml file");
                        if (0 != 0) {
                            try {
                                fi.close();
                            } catch (IOException e4) {
                                loge(e4.toString());
                            }
                        }
                    } catch (Throwable th) {
                        if (0 != 0) {
                            try {
                                fi.close();
                            } catch (IOException e5) {
                                loge(e5.toString());
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e6) {
                    loge(e6.toString());
                    if (0 != 0) {
                        fi.close();
                    }
                }
            } catch (XmlPullParserException e7) {
                loge(e7.toString());
                if (0 != 0) {
                    fi.close();
                }
            }
        } catch (IOException e8) {
            loge(e8.toString());
        }
    }
}
