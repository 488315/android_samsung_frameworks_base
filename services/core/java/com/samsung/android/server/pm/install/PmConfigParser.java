package com.samsung.android.server.pm.install;

import android.util.AtomicFile;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;

public final class PmConfigParser {
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0067, code lost:

       r3.close();
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List parsePackages(java.lang.String r9) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r9)
            boolean r9 = r0.exists()
            java.lang.String r1 = "PackageManager"
            if (r9 != 0) goto L12
            java.lang.String r9 = "No xml file exists."
            android.util.Log.d(r1, r9)
        L12:
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            org.xmlpull.v1.XmlPullParser r2 = android.util.Xml.newPullParser()
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.io.IOException -> L7b java.io.FileNotFoundException -> L7f org.xmlpull.v1.XmlPullParserException -> L83
            r3.<init>(r0)     // Catch: java.io.IOException -> L7b java.io.FileNotFoundException -> L7f org.xmlpull.v1.XmlPullParserException -> L83
            r0 = 0
            r2.setInput(r3, r0)     // Catch: java.lang.Throwable -> L71
            int r4 = r2.getDepth()     // Catch: java.lang.Throwable -> L71
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L71
            r5.<init>()     // Catch: java.lang.Throwable -> L71
        L2d:
            int r6 = r2.next()     // Catch: java.lang.Throwable -> L71
            r7 = 1
            if (r6 == r7) goto L67
            r7 = 3
            if (r6 != r7) goto L3d
            int r8 = r2.getDepth()     // Catch: java.lang.Throwable -> L71
            if (r8 <= r4) goto L67
        L3d:
            if (r6 == r7) goto L2d
            r7 = 4
            if (r6 != r7) goto L43
            goto L2d
        L43:
            java.lang.String r6 = r2.getName()     // Catch: java.lang.Throwable -> L71
            java.lang.String r7 = "package"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Throwable -> L71
            if (r6 == 0) goto L2d
            java.lang.String r6 = "name"
            java.lang.String r6 = r2.getAttributeValue(r0, r6)     // Catch: java.lang.Throwable -> L71
            boolean r7 = android.text.TextUtils.isEmpty(r6)     // Catch: java.lang.Throwable -> L71
            if (r7 != 0) goto L2d
            boolean r7 = r5.contains(r6)     // Catch: java.lang.Throwable -> L71
            if (r7 != 0) goto L2d
            r5.add(r6)     // Catch: java.lang.Throwable -> L71
            goto L2d
        L67:
            r3.close()     // Catch: java.io.IOException -> L6b java.io.FileNotFoundException -> L6d org.xmlpull.v1.XmlPullParserException -> L6f
            goto Lbc
        L6b:
            r9 = move-exception
            goto L87
        L6d:
            r9 = move-exception
            goto L99
        L6f:
            r9 = move-exception
            goto Lab
        L71:
            r0 = move-exception
            r3.close()     // Catch: java.lang.Throwable -> L76
            goto L7a
        L76:
            r2 = move-exception
            r0.addSuppressed(r2)     // Catch: java.io.IOException -> L7b java.io.FileNotFoundException -> L7f org.xmlpull.v1.XmlPullParserException -> L83
        L7a:
            throw r0     // Catch: java.io.IOException -> L7b java.io.FileNotFoundException -> L7f org.xmlpull.v1.XmlPullParserException -> L83
        L7b:
            r0 = move-exception
            r5 = r9
            r9 = r0
            goto L87
        L7f:
            r0 = move-exception
            r5 = r9
            r9 = r0
            goto L99
        L83:
            r0 = move-exception
            r5 = r9
            r9 = r0
            goto Lab
        L87:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Failed to parse allowlist. IOException "
            r0.<init>(r2)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.util.Log.d(r1, r9)
            goto Lbc
        L99:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Failed to parse allowlist. FileNotFoundException "
            r0.<init>(r2)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.util.Log.e(r1, r9)
            goto Lbc
        Lab:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "Failed to parse allowlist. XmlPullParserException "
            r0.<init>(r2)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.util.Log.e(r1, r9)
        Lbc:
            return r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.server.pm.install.PmConfigParser.parsePackages(java.lang.String):java.util.List");
    }

    public static void writePackagesToXml(String str, HashSet hashSet) {
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
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                XmlSerializer newSerializer = Xml.newSerializer();
                newSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
                newSerializer.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                newSerializer.startDocument(null, Boolean.TRUE);
                newSerializer.startTag(null, "packages");
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    newSerializer.startTag(null, "package");
                    newSerializer.attribute(null, "name", str2);
                    newSerializer.endTag(null, "package");
                }
                newSerializer.endTag(null, "packages");
                newSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
            } catch (Exception unused) {
                fileOutputStream = startWrite;
                atomicFile.failWrite(fileOutputStream);
            }
        } catch (Exception unused2) {
        }
    }
}
