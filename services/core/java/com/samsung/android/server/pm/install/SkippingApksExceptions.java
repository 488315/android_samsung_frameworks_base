package com.samsung.android.server.pm.install;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class SkippingApksExceptions {
    public final List mAdditionalSkippingApkList = new ArrayList();

    public SkippingApksExceptions() {
        parseSkippingApkList(Environment.getRootDirectory() + "/etc/skipping_apk_exceptions.xml");
    }

    public boolean hasAdditionalSkippingApkList() {
        return !this.mAdditionalSkippingApkList.isEmpty();
    }

    public List getAdditionalSkippingApkList() {
        Log.d("SkippingApksExceptions", "getAdditionalSkippingApkList: " + this.mAdditionalSkippingApkList.toString());
        return this.mAdditionalSkippingApkList;
    }

    public static String getProductName() {
        return Build.PRODUCT;
    }

    public final void parseSkippingApkList(String str) {
        File file = new File(str);
        if (!file.exists()) {
            Log.d("SkippingApksExceptions", "No skipping apk exception file exists.");
            return;
        }
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                parseSkippingApkExceptions(newPullParser);
                fileInputStream.close();
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.e("SkippingApksExceptions", "Failed to parse allowlist. FileNotFoundException " + e);
        } catch (IOException e2) {
            Log.d("SkippingApksExceptions", "Failed to parse allowlist. IOException " + e2);
        } catch (XmlPullParserException e3) {
            Log.e("SkippingApksExceptions", "Failed to parse allowlist. XmlPullParserException " + e3);
        }
    }

    public final void parseSkippingApkExceptions(XmlPullParser xmlPullParser) {
        xmlPullParser.next();
        int depth = xmlPullParser.getDepth();
        String productName = getProductName();
        if (productName == null || productName.length() < 3) {
            return;
        }
        String substring = productName.substring(productName.length() - 3, productName.length());
        Log.d("SkippingApksExceptions", "deviceCarrierName = " + substring);
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                name.hashCode();
                if (name.equals("exception")) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                    if (!TextUtils.isEmpty(attributeValue)) {
                        String attributeValue2 = xmlPullParser.getAttributeValue(null, "product");
                        if (TextUtils.isEmpty(attributeValue2)) {
                            this.mAdditionalSkippingApkList.add(attributeValue);
                        } else if (attributeValue2.length() < 3 || !attributeValue2.contains(substring)) {
                            this.mAdditionalSkippingApkList.add(attributeValue);
                        }
                    }
                } else {
                    Log.d("SkippingApksExceptions", "Invalid element name: " + name);
                }
            }
        }
    }
}
