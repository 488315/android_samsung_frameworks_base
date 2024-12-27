package com.samsung.android.server.pm.appcategory;

import android.os.Environment;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AppCategoryListParser {
    public static final String SYSTEM_FILE_PATH =
            Environment.getRootDirectory() + "/etc/pm_appcategory.xml";
    public final Map mPackageMap = new ArrayMap();

    public void parseAppCategoryList() {
        parseAppCategoryList(null);
    }

    public final void parseAppCategoryList(String str) {
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = SYSTEM_FILE_PATH;
        if (isEmpty) {
            str = str2;
        }
        File file = new File(str);
        if (!file.exists()) {
            Log.d("AppCategoryParser", "No xml file exists.");
        }
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                parseAppCategoryListElement(newPullParser);
                fileInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e("AppCategoryParser", "Failed to parse the AppCategory file. " + e);
            if (str2.equals(str)) {
                return;
            }
            restoreToSystemFile();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e9 A[LOOP:1: B:24:0x00e3->B:26:0x00e9, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseAppCategoryListElement(org.xmlpull.v1.XmlPullParser r17) {
        /*
            Method dump skipped, instructions count: 324
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.pm.appcategory.AppCategoryListParser.parseAppCategoryListElement(org.xmlpull.v1.XmlPullParser):void");
    }

    public List parsePackages(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals("package")) {
                String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                if (!TextUtils.isEmpty(attributeValue) && !arrayList.contains(attributeValue)) {
                    arrayList.add(attributeValue);
                }
            }
        }
        return arrayList;
    }

    public abstract void restoreToSystemFile();
}
