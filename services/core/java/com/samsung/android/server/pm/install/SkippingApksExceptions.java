package com.samsung.android.server.pm.install;

import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SkippingApksExceptions {
    public final List mAdditionalSkippingApkList = new ArrayList();

    public SkippingApksExceptions() {
        File file = new File(Environment.getRootDirectory() + "/etc/skipping_apk_exceptions.xml");
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
        String str = Build.PRODUCT;
        if (str == null || str.length() < 3) {
            return;
        }
        String substring = str.substring(str.length() - 3, str.length());
        DualAppManagerService$$ExternalSyntheticOutline0.m("deviceCarrierName = ", substring, "SkippingApksExceptions");
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
                name.getClass();
                if (name.equals("exception")) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                    if (!TextUtils.isEmpty(attributeValue)) {
                        String attributeValue2 = xmlPullParser.getAttributeValue(null, "product");
                        if (TextUtils.isEmpty(attributeValue2)) {
                            ((ArrayList) this.mAdditionalSkippingApkList).add(attributeValue);
                        } else if (attributeValue2.length() < 3 || !attributeValue2.contains(substring)) {
                            ((ArrayList) this.mAdditionalSkippingApkList).add(attributeValue);
                        }
                    }
                } else {
                    Log.d("SkippingApksExceptions", "Invalid element name: ".concat(name));
                }
            }
        }
    }
}
