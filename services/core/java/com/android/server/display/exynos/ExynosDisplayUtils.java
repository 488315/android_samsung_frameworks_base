package com.android.server.display.exynos;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ExynosDisplayUtils {
    public static boolean existFile(String str) {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            Log.i("ExynosDisplayUtils", str.concat(" File not found"));
            return false;
        }
        if (file.isFile()) {
            return true;
        }
        Log.e("ExynosDisplayUtils", str.concat(" is not File"));
        return false;
    }

    public static String getPathWithPanel(String str) {
        String stringFromFile = getStringFromFile("/sys/class/dqe/dqe/xml");
        if (str != null && stringFromFile != null) {
            String substring = str.substring(0, str.lastIndexOf(".xml"));
            String substring2 = stringFromFile.substring(stringFromFile.lastIndexOf("/") + 1);
            if (substring != null && substring2 != null) {
                String m = AnyMotionDetector$$ExternalSyntheticOutline0.m(substring, "_", substring2);
                if (existFile(m)) {
                    return m;
                }
            }
        }
        if (existFile(str)) {
            return str;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00be A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getStringFromFile(java.lang.String r9) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r9)
            boolean r1 = r0.exists()
            r2 = 0
            java.lang.String r3 = "ExynosDisplayUtils"
            if (r1 != 0) goto L23
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r9)
            java.lang.String r9 = " File not found"
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.util.Log.e(r3, r9)
            return r2
        L23:
            boolean r0 = r0.isFile()
            if (r0 != 0) goto L3e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r9)
            java.lang.String r9 = " is not File"
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            android.util.Log.e(r3, r9)
            return r2
        L3e:
            r0 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r0]
            r4 = 0
            r5 = r4
        L44:
            if (r5 >= r0) goto L4b
            r1[r5] = r4
            int r5 = r5 + 1
            goto L44
        L4b:
            java.lang.String r0 = "File Close error"
            if (r9 == 0) goto L60
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d
            r6.<init>(r9)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5d
            goto L61
        L5a:
            r9 = move-exception
            goto Lbc
        L5d:
            r9 = move-exception
            r1 = r2
            goto L83
        L60:
            r5 = r2
        L61:
            if (r5 == 0) goto Lc6
            int r9 = r5.read(r1)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L81
            if (r9 <= 0) goto L7d
            java.lang.String r6 = new java.lang.String     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L77
            int r7 = r9 + (-1)
            java.nio.charset.Charset r8 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L77
            r6.<init>(r1, r4, r7, r8)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L77
            r2 = r6
            goto L7d
        L74:
            r9 = move-exception
            r2 = r5
            goto Lbc
        L77:
            r1 = move-exception
            r4 = r9
            r9 = r1
        L7a:
            r1 = r2
            r2 = r5
            goto L83
        L7d:
            r5.close()     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L77
            goto Lc6
        L81:
            r9 = move-exception
            goto L7a
        L83:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5a
            r5.<init>()     // Catch: java.lang.Throwable -> L5a
            java.lang.String r6 = "Exception : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L5a
            r5.append(r9)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r6 = " , in : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L5a
            r5.append(r2)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r6 = " , value : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L5a
            r5.append(r1)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r6 = " , length : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L5a
            r5.append(r4)     // Catch: java.lang.Throwable -> L5a
            java.lang.String r4 = r5.toString()     // Catch: java.lang.Throwable -> L5a
            android.util.Log.e(r3, r4)     // Catch: java.lang.Throwable -> L5a
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L5a
            if (r2 == 0) goto Ld0
            r2.close()     // Catch: java.lang.Exception -> Lb8
            goto Ld0
        Lb8:
            android.util.Log.e(r3, r0)
            goto Ld0
        Lbc:
            if (r2 == 0) goto Lc5
            r2.close()     // Catch: java.lang.Exception -> Lc2
            goto Lc5
        Lc2:
            android.util.Log.e(r3, r0)
        Lc5:
            throw r9
        Lc6:
            if (r5 == 0) goto Lcf
            r5.close()     // Catch: java.lang.Exception -> Lcc
            goto Lcf
        Lcc:
            android.util.Log.e(r3, r0)
        Lcf:
            r1 = r2
        Ld0:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayUtils.getStringFromFile(java.lang.String):java.lang.String");
    }

    public static float[][] matrixMultiplication(float[][] fArr, float[][] fArr2) {
        int length = fArr.length;
        int length2 = fArr[0].length;
        int length3 = fArr2[0].length;
        float[][] fArr3 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, length, length3);
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length3; i2++) {
                for (int i3 = 0; i3 < length2; i3++) {
                    float[] fArr4 = fArr3[i];
                    fArr4[i2] = (fArr[i][i3] * fArr2[i3][i2]) + fArr4[i2];
                }
            }
        }
        return fArr3;
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [boolean] */
    public static String[] parserFactoryXMLALText(int i, int i2, String str, String str2) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        ?? isFile = new File(str).isFile();
        String str3 = null;
        try {
            if (isFile == 0) {
                Log.e("ExynosDisplayUtils", str.concat(" File not found"));
                return null;
            }
            try {
                fileInputStream2 = new FileInputStream(str);
            } catch (IOException e) {
                e = e;
                fileInputStream2 = null;
            } catch (XmlPullParserException e2) {
                e = e2;
                fileInputStream2 = null;
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileInputStream2, null);
                int eventType = newPullParser.getEventType();
                ArrayList arrayList = new ArrayList();
                new ArrayList();
                boolean z = false;
                char c = 0;
                char c2 = 0;
                while (eventType != 1) {
                    if (eventType != 0 && eventType != 1) {
                        if (eventType == 2) {
                            String name = newPullParser.getName();
                            if (name.equals("mode") && newPullParser.getAttributeValue(str3, "name").equals("tune")) {
                                z = true;
                            }
                            if (name.equals("atc") && Integer.parseInt(newPullParser.getAttributeValue(str3, "al")) == i) {
                                c = 2;
                            }
                            if (name.equals(str2)) {
                                String attributeValue = newPullParser.getAttributeValue(str3, "att0");
                                if (attributeValue != null && Integer.parseInt(attributeValue) != i2) {
                                }
                                c2 = 11;
                            }
                        } else if (eventType == 3) {
                            String name2 = newPullParser.getName();
                            if (name2.equals("mode")) {
                                z = false;
                            }
                            if (name2.equals("atc")) {
                                c = 0;
                            }
                            if (name2.equals(str2)) {
                                c2 = 0;
                            }
                        } else if (eventType == 4 && z && c == 2 && c2 == 11) {
                            arrayList.add(newPullParser.getText());
                        }
                    }
                    eventType = newPullParser.next();
                    str3 = null;
                }
                if (arrayList.size() == 0) {
                    try {
                        fileInputStream2.close();
                        return null;
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                        return null;
                    }
                }
                String[] strArr = new String[arrayList.size()];
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    String str4 = (String) arrayList.get(i3);
                    strArr[i3] = str4;
                    strArr[i3] = str4.trim();
                }
                try {
                    fileInputStream2.close();
                } catch (Exception unused3) {
                    Log.e("ExynosDisplayUtils", "File Close error");
                }
                return strArr;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                if (fileInputStream2 == null) {
                    return null;
                }
                try {
                    fileInputStream2.close();
                    return null;
                } catch (Exception unused4) {
                    Log.e("ExynosDisplayUtils", "File Close error");
                    return null;
                }
            } catch (XmlPullParserException e4) {
                e = e4;
                e.printStackTrace();
                if (fileInputStream2 == null) {
                    return null;
                }
                fileInputStream2.close();
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = isFile;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x00db, code lost:
    
        if (r1 == null) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00dd, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00e1, code lost:
    
        android.util.Log.e("ExynosDisplayUtils", "File Close error");
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00e8, code lost:
    
        if (r1 == null) goto L70;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] parserFactoryXMLAttribute(java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayUtils.parserFactoryXMLAttribute(java.lang.String, java.lang.String, java.lang.String, java.lang.String):java.lang.String[]");
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [boolean] */
    public static String[] parserFactoryXMLText(int i, int i2, String str, String str2, String str3) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        ?? isFile = new File(str).isFile();
        try {
            if (isFile == 0) {
                Log.e("ExynosDisplayUtils", str + " File not found");
                return null;
            }
            try {
                fileInputStream2 = new FileInputStream(str);
            } catch (IOException e) {
                e = e;
                fileInputStream2 = null;
            } catch (XmlPullParserException e2) {
                e = e2;
                fileInputStream2 = null;
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileInputStream2, null);
                ArrayList arrayList = new ArrayList();
                new ArrayList();
                boolean z = false;
                char c = 0;
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    if (eventType != 0 && eventType != 1) {
                        if (eventType == 2) {
                            String name = newPullParser.getName();
                            if (name.equals("mode") && newPullParser.getAttributeValue(null, "name").equals(str2)) {
                                z = true;
                            }
                            if (!str3.equals("cgc17_enc")) {
                                if (str3.equals("hsc48_lcg")) {
                                    if (name.equals("hsc48_lcg")) {
                                        if (Integer.parseInt(newPullParser.getAttributeValue(null, "att0")) != i) {
                                        }
                                    }
                                } else if (str3.equals("gamma")) {
                                    if (name.equals("gamma")) {
                                        String attributeValue = newPullParser.getAttributeValue(null, "att0");
                                        if (attributeValue != null) {
                                            if (Integer.parseInt(attributeValue) == i) {
                                            }
                                        }
                                    }
                                } else if (str3.equals("degamma")) {
                                    if (name.equals("degamma")) {
                                        String attributeValue2 = newPullParser.getAttributeValue(null, "att0");
                                        if (attributeValue2 != null) {
                                            if (Integer.parseInt(attributeValue2) == i) {
                                            }
                                        }
                                    }
                                } else if (name.equals(str3)) {
                                }
                                c = 2;
                            } else if (name.equals("cgc17_enc")) {
                                String attributeValue3 = newPullParser.getAttributeValue(null, "att0");
                                String attributeValue4 = newPullParser.getAttributeValue(null, "att1");
                                if (Integer.parseInt(attributeValue3) == i) {
                                    if (Integer.parseInt(attributeValue4) != i2) {
                                    }
                                    c = 2;
                                }
                            }
                        } else if (eventType == 3) {
                            String name2 = newPullParser.getName();
                            if (name2.equals("mode")) {
                                z = false;
                            }
                            if (str3.equals("cgc17_enc")) {
                                if (!name2.equals("cgc17_enc")) {
                                }
                                c = 0;
                            } else if (str3.equals("hsc48_lcg")) {
                                if (name2.equals("hsc48_lcg")) {
                                    c = 0;
                                }
                            } else if (name2.equals(str3)) {
                                c = 0;
                            }
                        } else if (eventType == 4 && z && c == 2) {
                            arrayList.add(newPullParser.getText());
                        }
                    }
                }
                if (arrayList.size() == 0) {
                    try {
                        fileInputStream2.close();
                        return null;
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                        return null;
                    }
                }
                String[] strArr = new String[arrayList.size()];
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    String str4 = (String) arrayList.get(i3);
                    strArr[i3] = str4;
                    strArr[i3] = str4.trim();
                }
                try {
                    fileInputStream2.close();
                } catch (Exception unused3) {
                    Log.e("ExynosDisplayUtils", "File Close error");
                }
                return strArr;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                if (fileInputStream2 == null) {
                    return null;
                }
                try {
                    fileInputStream2.close();
                    return null;
                } catch (Exception unused4) {
                    Log.e("ExynosDisplayUtils", "File Close error");
                    return null;
                }
            } catch (XmlPullParserException e4) {
                e = e4;
                e.printStackTrace();
                if (fileInputStream2 == null) {
                    return null;
                }
                fileInputStream2.close();
                return null;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = isFile;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:113:0x015c, code lost:
    
        if (r3 == null) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x015e, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0162, code lost:
    
        android.util.Log.e("ExynosDisplayUtils", "File Close error");
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0169, code lost:
    
        if (r3 == null) goto L110;
     */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] parserXML(java.lang.String r16, java.lang.String r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 375
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayUtils.parserXML(java.lang.String, java.lang.String, java.lang.String):java.lang.String[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x00e8, code lost:
    
        if (r1 == null) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00ea, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00ee, code lost:
    
        android.util.Log.e("ExynosDisplayUtils", "File Close error");
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00f5, code lost:
    
        if (r1 == null) goto L74;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] parserXMLALText(int r13, java.lang.String r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayUtils.parserXMLALText(int, java.lang.String, java.lang.String):java.lang.String[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00b2, code lost:
    
        if (r1 == null) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00b4, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b8, code lost:
    
        android.util.Log.e("ExynosDisplayUtils", "File Close error");
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00bf, code lost:
    
        if (r1 == null) goto L58;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] parserXMLAttribute(java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.String r0 = "File Close error"
            java.io.File r1 = new java.io.File
            r1.<init>(r7)
            boolean r1 = r1.isFile()
            java.lang.String r2 = "ExynosDisplayUtils"
            r3 = 0
            if (r1 != 0) goto L25
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            java.lang.String r7 = " File not found"
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            android.util.Log.e(r2, r7)
            return r3
        L25:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> La7 java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lac
            r1.<init>(r7)     // Catch: java.lang.Throwable -> La7 java.io.IOException -> La9 org.xmlpull.v1.XmlPullParserException -> Lac
            org.xmlpull.v1.XmlPullParserFactory r7 = org.xmlpull.v1.XmlPullParserFactory.newInstance()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            org.xmlpull.v1.XmlPullParser r7 = r7.newPullParser()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            r7.setInput(r1, r3)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            int r4 = r7.getEventType()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            r5.<init>()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
        L3e:
            r6 = 1
            if (r4 == r6) goto L73
            if (r4 != 0) goto L44
            goto L6e
        L44:
            if (r4 != r6) goto L47
            goto L6e
        L47:
            r6 = 2
            if (r4 != r6) goto L64
            java.lang.String r4 = r7.getName()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            boolean r4 = r4.equals(r8)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            if (r4 == 0) goto L6e
            java.lang.String r4 = r7.getAttributeValue(r3, r9)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            r5.add(r4)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            goto L6e
        L5c:
            r7 = move-exception
            r3 = r1
            goto Lc3
        L60:
            r7 = move-exception
            goto Laf
        L62:
            r7 = move-exception
            goto Lbc
        L64:
            r6 = 3
            if (r4 != r6) goto L6e
            java.lang.String r4 = r7.getName()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            r4.equals(r8)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
        L6e:
            int r4 = r7.next()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            goto L3e
        L73:
            int r7 = r5.size()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            if (r7 != 0) goto L81
            r1.close()     // Catch: java.lang.Exception -> L7d
            goto L80
        L7d:
            android.util.Log.e(r2, r0)
        L80:
            return r3
        L81:
            int r7 = r5.size()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            java.lang.String[] r7 = new java.lang.String[r7]     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            r8 = 0
        L88:
            int r9 = r5.size()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            if (r8 >= r9) goto L9f
            java.lang.Object r9 = r5.get(r8)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            java.lang.String r9 = (java.lang.String) r9     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            r7[r8] = r9     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            java.lang.String r9 = r9.trim()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            r7[r8] = r9     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 org.xmlpull.v1.XmlPullParserException -> L62
            int r8 = r8 + 1
            goto L88
        L9f:
            r1.close()     // Catch: java.lang.Exception -> La3
            goto La6
        La3:
            android.util.Log.e(r2, r0)
        La6:
            return r7
        La7:
            r7 = move-exception
            goto Lc3
        La9:
            r7 = move-exception
            r1 = r3
            goto Laf
        Lac:
            r7 = move-exception
            r1 = r3
            goto Lbc
        Laf:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L5c
            if (r1 == 0) goto Lc2
        Lb4:
            r1.close()     // Catch: java.lang.Exception -> Lb8
            goto Lc2
        Lb8:
            android.util.Log.e(r2, r0)
            goto Lc2
        Lbc:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L5c
            if (r1 == 0) goto Lc2
            goto Lb4
        Lc2:
            return r3
        Lc3:
            if (r3 == 0) goto Lcc
            r3.close()     // Catch: java.lang.Exception -> Lc9
            goto Lcc
        Lc9:
            android.util.Log.e(r2, r0)
        Lcc:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayUtils.parserXMLAttribute(java.lang.String, java.lang.String, java.lang.String):java.lang.String[]");
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c2, code lost:
    
        if (r1 == null) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00c4, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00c8, code lost:
    
        android.util.Log.e("ExynosDisplayUtils", "File Close error");
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00cf, code lost:
    
        if (r1 == null) goto L65;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] parserXMLNodeText(java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 221
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayUtils.parserXMLNodeText(java.lang.String):java.lang.String[]");
    }

    public static void sendEmptyUpdate() {
        try {
            IBinder service = ServiceManager.getService("SurfaceFlinger");
            if (service != null) {
                Parcel obtain = Parcel.obtain();
                obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
                obtain.writeInt(0);
                service.transact(1006, obtain, null, 0);
                obtain.recycle();
            }
        } catch (RemoteException unused) {
            Log.d("ExynosDisplayUtils", "failed to sendEmptyUpdate");
        }
    }

    public static void sysfsWrite(int i, String str) {
        File file = new File(str);
        if (!file.exists()) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(Integer.toString(i).getBytes());
                    fileOutputStream2.close();
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e4) {
            e = e4;
        }
    }

    public static boolean sysfsWriteSting(String str, String str2) {
        FileOutputStream fileOutputStream;
        IOException e;
        File file = new File(str);
        if (file.exists()) {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        fileOutputStream.write(str2.getBytes());
                        fileOutputStream.close();
                        return true;
                    } catch (IOException e2) {
                        e = e2;
                        e.printStackTrace();
                        try {
                            fileOutputStream.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        return false;
                    }
                } catch (FileNotFoundException e4) {
                    e4.printStackTrace();
                    return false;
                }
            } catch (IOException e5) {
                fileOutputStream = null;
                e = e5;
            }
        }
        return false;
    }

    public static float[][] toMat4(float[][] fArr) {
        if (fArr.length > 4) {
            return null;
        }
        float[] fArr2 = fArr[0];
        if (fArr2.length > 4) {
            return null;
        }
        if (fArr.length == 4 && fArr2.length == 4) {
            return fArr;
        }
        float[][] fArr3 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 4, 4);
        fArr3[3][3] = 1.0f;
        for (int i = 0; i < fArr.length; i++) {
            int i2 = 0;
            while (true) {
                float[] fArr4 = fArr[i];
                if (i2 < fArr4.length) {
                    fArr3[i][i2] = fArr4[i2];
                    i2++;
                }
            }
        }
        return fArr3;
    }

    public static String toString(float[][] fArr) {
        try {
            StringBuilder sb = new StringBuilder("1,");
            for (float[] fArr2 : toMat4(fArr)) {
                for (float f : fArr2) {
                    sb.append(Math.round(f * 65536.0f));
                    sb.append(",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
