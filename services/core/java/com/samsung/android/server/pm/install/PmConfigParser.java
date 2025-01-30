package com.samsung.android.server.pm.install;

import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes2.dex */
public class PmConfigParser {
    public List parsePackages(String str) {
        File file = new File(str);
        if (!file.exists()) {
            Log.d("PackageManager", "No xml file exists.");
        }
        List arrayList = new ArrayList();
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                arrayList = parsePackageInternal(newPullParser);
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
            Log.e("PackageManager", "Failed to parse allowlist. FileNotFoundException " + e);
        } catch (IOException e2) {
            Log.d("PackageManager", "Failed to parse allowlist. IOException " + e2);
        } catch (XmlPullParserException e3) {
            Log.e("PackageManager", "Failed to parse allowlist. XmlPullParserException " + e3);
        }
        return arrayList;
    }

    public final List parsePackageInternal(XmlPullParser xmlPullParser) {
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

    public void writePackagesToXml(HashSet hashSet, String str) {
        FileOutputStream startWrite;
        File file = new File(str);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AtomicFile atomicFile = new AtomicFile(file);
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = atomicFile.startWrite();
        } catch (Exception unused) {
        }
        try {
            XmlSerializer newSerializer = Xml.newSerializer();
            newSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
            newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            newSerializer.startDocument(null, Boolean.TRUE);
            serializePackages(hashSet, newSerializer);
            newSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
        } catch (Exception unused2) {
            fileOutputStream = startWrite;
            atomicFile.failWrite(fileOutputStream);
        }
    }

    public final void serializePackages(HashSet hashSet, XmlSerializer xmlSerializer) {
        xmlSerializer.startTag(null, "packages");
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            xmlSerializer.startTag(null, "package");
            xmlSerializer.attribute(null, "name", str);
            xmlSerializer.endTag(null, "package");
        }
        xmlSerializer.endTag(null, "packages");
    }
}
