package com.android.server.display.exynos;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes2.dex */
public abstract class ExynosDisplayUtils {
    public static String XML_SYSFS_PATH = "/sys/class/dqe/dqe/xml";

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getStringFromFile(String str) {
        ?? r1;
        FileInputStream fileInputStream;
        Throwable th;
        File file = new File(str);
        FileInputStream fileInputStream2 = null;
        if (!file.exists()) {
            Log.e("ExynosDisplayUtils", str + " File not found");
            return null;
        }
        if (!file.isFile()) {
            Log.e("ExynosDisplayUtils", str + " is not File");
            return null;
        }
        byte[] bArr = new byte[1024];
        int i = 0;
        for (int i2 = 0; i2 < 1024; i2++) {
            bArr[i2] = 0;
        }
        if (str != null) {
            try {
                try {
                    fileInputStream = new FileInputStream(new File(str));
                } catch (Exception e) {
                    e = e;
                    r1 = 0;
                    Log.e("ExynosDisplayUtils", "Exception : " + e + " , in : " + fileInputStream2 + " , value : " + r1 + " , length : " + i);
                    e.printStackTrace();
                    if (fileInputStream2 != null) {
                        return r1;
                    }
                    try {
                        fileInputStream2.close();
                        return r1;
                    } catch (Exception unused) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                        return r1;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream2 != null) {
                }
                throw th;
            }
        } else {
            fileInputStream = null;
        }
        if (fileInputStream != null) {
            try {
                try {
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        try {
                            fileInputStream2 = new String(bArr, 0, read - 1, StandardCharsets.UTF_8);
                        } catch (Exception e2) {
                            i = read;
                            e = e2;
                            r1 = fileInputStream2;
                            fileInputStream2 = fileInputStream;
                            Log.e("ExynosDisplayUtils", "Exception : " + e + " , in : " + fileInputStream2 + " , value : " + r1 + " , length : " + i);
                            e.printStackTrace();
                            if (fileInputStream2 != null) {
                            }
                        }
                    }
                    fileInputStream.close();
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
        }
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (Exception unused3) {
                Log.e("ExynosDisplayUtils", "File Close error");
            }
        }
        return fileInputStream2;
    }

    public static String getPathWithPanel(String str) {
        String stringFromFile = getStringFromFile(XML_SYSFS_PATH);
        if (str != null && stringFromFile != null) {
            String substring = str.substring(0, str.lastIndexOf(".xml"));
            String substring2 = stringFromFile.substring(stringFromFile.lastIndexOf("/") + 1);
            if (substring != null && substring2 != null) {
                String str2 = substring + "_" + substring2;
                if (existFile(str2)) {
                    return str2;
                }
            }
        }
        if (existFile(str)) {
            return str;
        }
        return null;
    }

    public static boolean sysfsWrite(String str, int i) {
        FileOutputStream fileOutputStream;
        IOException e;
        File file = new File(str);
        if (file.exists()) {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                    return false;
                }
            } catch (IOException e3) {
                fileOutputStream = null;
                e = e3;
            }
            try {
                fileOutputStream.write(Integer.toString(i).getBytes());
                fileOutputStream.close();
                return true;
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                try {
                    fileOutputStream.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    public static boolean sysfsWriteSting(String str, String str2) {
        FileOutputStream fileOutputStream;
        IOException e;
        File file = new File(str);
        if (file.exists()) {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                    return false;
                }
            } catch (IOException e3) {
                fileOutputStream = null;
                e = e3;
            }
            try {
                fileOutputStream.write(str2.getBytes());
                fileOutputStream.close();
                return true;
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                try {
                    fileOutputStream.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    public static boolean existFile(String str) {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            Log.i("ExynosDisplayUtils", str + " File not found");
            return false;
        }
        if (file.isFile()) {
            return true;
        }
        Log.e("ExynosDisplayUtils", str + " is not File");
        return false;
    }

    public static boolean existPath(String str) {
        return str != null && new File(str).exists();
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x015d, code lost:
    
        if (r3 == null) goto L110;
     */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] parserXML(String str, String str2, String str3) {
        FileInputStream fileInputStream;
        ?? isFile = new File(str).isFile();
        FileInputStream fileInputStream2 = null;
        try {
            if (isFile == 0) {
                Log.e("ExynosDisplayUtils", str + " File not found");
                return null;
            }
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                    newPullParser.setInput(fileInputStream, null);
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    char c = 0;
                    for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                        if (eventType != 0 && eventType != 1) {
                            if (eventType == 2) {
                                String name = newPullParser.getName();
                                if (name.equals("mode") && newPullParser.getAttributeValue(null, "name").equals(str2)) {
                                    z = true;
                                }
                                if (str3.equals("dqe")) {
                                    if (name.equals("cgc")) {
                                        c = '\f';
                                    }
                                    c = name.equals("hsc") ? (char) 14 : name.equals("gamma") ? '\r' : c;
                                } else if (name.equals(str3)) {
                                    c = 11;
                                }
                            } else if (eventType == 3) {
                                String name2 = newPullParser.getName();
                                if (name2.equals("mode")) {
                                    z = false;
                                }
                                if (str3.equals("dqe")) {
                                    if (name2.equals("cgc")) {
                                        c = 0;
                                    }
                                    if (name2.equals("gamma")) {
                                        c = 0;
                                    }
                                    if (name2.equals("hsc")) {
                                        c = 0;
                                    }
                                } else {
                                    if (!name2.equals(str3)) {
                                    }
                                    c = 0;
                                }
                            } else if (eventType == 4) {
                                if (str3.equals("dqe")) {
                                    boolean z2 = true;
                                    if (z) {
                                        if (c == '\f') {
                                            arrayList.add(newPullParser.getText());
                                        }
                                        z2 = true;
                                    }
                                    if (z == z2) {
                                        if (c == '\r') {
                                            arrayList.add(newPullParser.getText());
                                        }
                                        z2 = true;
                                    }
                                    if (z == z2 && c == 14) {
                                        arrayList.add(newPullParser.getText());
                                    }
                                } else if (z && c == 11) {
                                    arrayList.add(newPullParser.getText());
                                }
                            }
                        }
                    }
                    String[] strArr = new String[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        String str4 = (String) arrayList.get(i);
                        strArr[i] = str4;
                        strArr[i] = str4.trim();
                    }
                    try {
                        fileInputStream.close();
                    } catch (Exception unused) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                    return strArr;
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception unused2) {
                            Log.e("ExynosDisplayUtils", "File Close error");
                        }
                    }
                    return null;
                } catch (XmlPullParserException e2) {
                    e = e2;
                    e.printStackTrace();
                }
            } catch (IOException e3) {
                e = e3;
                fileInputStream = null;
            } catch (XmlPullParserException e4) {
                e = e4;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused3) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = isFile;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x00b2, code lost:
    
        if (r1 == null) goto L56;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] parserXMLAttribute(String str, String str2, String str3) {
        FileInputStream fileInputStream;
        ?? isFile = new File(str).isFile();
        FileInputStream fileInputStream2 = null;
        try {
            if (isFile == 0) {
                Log.e("ExynosDisplayUtils", str + " File not found");
                return null;
            }
            try {
                fileInputStream = new FileInputStream(str);
            } catch (IOException e) {
                e = e;
                fileInputStream = null;
            } catch (XmlPullParserException e2) {
                e = e2;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileInputStream, null);
                ArrayList arrayList = new ArrayList();
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    if (eventType != 0 && eventType != 1) {
                        if (eventType == 2) {
                            if (newPullParser.getName().equals(str2)) {
                                arrayList.add(newPullParser.getAttributeValue(null, str3));
                            }
                        } else if (eventType == 3) {
                            newPullParser.getName().equals(str2);
                        }
                    }
                }
                if (arrayList.size() == 0) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                    return null;
                }
                String[] strArr = new String[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {
                    String str4 = (String) arrayList.get(i);
                    strArr[i] = str4;
                    strArr[i] = str4.trim();
                }
                try {
                    fileInputStream.close();
                } catch (Exception unused3) {
                    Log.e("ExynosDisplayUtils", "File Close error");
                }
                return strArr;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused4) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                return null;
            } catch (XmlPullParserException e4) {
                e = e4;
                e.printStackTrace();
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = isFile;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:82:0x00e2, code lost:
    
        if (r1 == null) goto L71;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] parserXMLALText(String str, String str2, int i, String str3) {
        FileInputStream fileInputStream;
        ?? isFile = new File(str).isFile();
        FileInputStream fileInputStream2 = null;
        try {
            if (isFile == 0) {
                Log.e("ExynosDisplayUtils", str + " File not found");
                return null;
            }
            try {
                fileInputStream = new FileInputStream(str);
            } catch (IOException e) {
                e = e;
                fileInputStream = null;
            } catch (XmlPullParserException e2) {
                e = e2;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileInputStream, null);
                ArrayList arrayList = new ArrayList();
                new ArrayList();
                boolean z = false;
                char c = 0;
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    if (eventType != 0 && eventType != 1) {
                        if (eventType == 2) {
                            String name = newPullParser.getName();
                            if (name.equals(str2) && Integer.parseInt(newPullParser.getAttributeValue(null, "al")) == i) {
                                z = true;
                            }
                            if (name.equals(str3)) {
                                c = 11;
                            }
                        } else if (eventType == 3) {
                            String name2 = newPullParser.getName();
                            if (name2.equals(str2)) {
                                z = false;
                            }
                            if (name2.equals(str3)) {
                                c = 0;
                            }
                        } else if (eventType == 4 && z && c == 11) {
                            arrayList.add(newPullParser.getText());
                        }
                    }
                }
                if (arrayList.size() == 0) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                    return null;
                }
                String[] strArr = new String[arrayList.size()];
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    String str4 = (String) arrayList.get(i2);
                    strArr[i2] = str4;
                    strArr[i2] = str4.trim();
                }
                try {
                    fileInputStream.close();
                } catch (Exception unused3) {
                    Log.e("ExynosDisplayUtils", "File Close error");
                }
                return strArr;
            } catch (IOException e3) {
                e = e3;
                e.printStackTrace();
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused4) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                return null;
            } catch (XmlPullParserException e4) {
                e = e4;
                e.printStackTrace();
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = isFile;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x00be, code lost:
    
        if (r1 == null) goto L62;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] parserXMLNodeText(String str, String str2) {
        FileInputStream fileInputStream;
        ?? isFile = new File(str).isFile();
        FileInputStream fileInputStream2 = null;
        try {
            if (isFile == 0) {
                Log.e("ExynosDisplayUtils", str + " File not found");
                return null;
            }
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                    newPullParser.setInput(fileInputStream, null);
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                        if (eventType != 0 && eventType != 1) {
                            if (eventType == 2) {
                                if (newPullParser.getName().equals(str2)) {
                                    z = true;
                                }
                            } else if (eventType == 3) {
                                if (newPullParser.getName().equals(str2)) {
                                    z = false;
                                }
                            } else if (eventType == 4 && z) {
                                arrayList.add(newPullParser.getText());
                            }
                        }
                    }
                    if (arrayList.size() == 0) {
                        try {
                            fileInputStream.close();
                        } catch (Exception unused) {
                            Log.e("ExynosDisplayUtils", "File Close error");
                        }
                        return null;
                    }
                    String[] strArr = new String[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        String str3 = (String) arrayList.get(i);
                        strArr[i] = str3;
                        strArr[i] = str3.trim();
                    }
                    try {
                        fileInputStream.close();
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                    return strArr;
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception unused3) {
                            Log.e("ExynosDisplayUtils", "File Close error");
                        }
                    }
                    return null;
                } catch (XmlPullParserException e2) {
                    e = e2;
                    e.printStackTrace();
                }
            } catch (IOException e3) {
                e = e3;
                fileInputStream = null;
            } catch (XmlPullParserException e4) {
                e = e4;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused4) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = isFile;
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [boolean] */
    public static String[] parserFactoryXMLALText(String str, String str2, String str3, int i, String str4, int i2) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        ?? isFile = new File(str).isFile();
        String str5 = null;
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
                            if (name.equals("mode") && newPullParser.getAttributeValue(str5, "name").equals(str2)) {
                                z = true;
                            }
                            if (name.equals(str3) && Integer.parseInt(newPullParser.getAttributeValue(str5, "al")) == i) {
                                c = 2;
                            }
                            if (name.equals(str4)) {
                                String attributeValue = newPullParser.getAttributeValue(str5, "att0");
                                if (attributeValue != null && Integer.parseInt(attributeValue) != i2) {
                                }
                                c2 = 11;
                            }
                        } else if (eventType == 3) {
                            String name2 = newPullParser.getName();
                            if (name2.equals("mode")) {
                                z = false;
                            }
                            if (name2.equals(str3)) {
                                c = 0;
                            }
                            if (name2.equals(str4)) {
                                c2 = 0;
                            }
                        } else if (eventType == 4 && z && c == 2 && c2 == 11) {
                            arrayList.add(newPullParser.getText());
                        }
                    }
                    eventType = newPullParser.next();
                    str5 = null;
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
                    String str6 = (String) arrayList.get(i3);
                    strArr[i3] = str6;
                    strArr[i3] = str6.trim();
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

    /* JADX WARN: Code restructure failed: missing block: B:74:0x00d8, code lost:
    
        if (r1 == null) goto L68;
     */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] parserFactoryXMLAttribute(String str, String str2, String str3, String str4) {
        FileInputStream fileInputStream;
        ?? isFile = new File(str).isFile();
        FileInputStream fileInputStream2 = null;
        try {
            if (isFile == 0) {
                Log.e("ExynosDisplayUtils", str + " File not found");
                return null;
            }
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                    newPullParser.setInput(fileInputStream, null);
                    ArrayList arrayList = new ArrayList();
                    boolean z = false;
                    for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                        if (eventType != 0 && eventType != 1) {
                            if (eventType == 2) {
                                String name = newPullParser.getName();
                                if (name.equals("mode") && newPullParser.getAttributeValue(null, "name").equals(str2)) {
                                    z = true;
                                }
                                if (z && (str3 == null || name.equals(str3))) {
                                    arrayList.add(newPullParser.getAttributeValue(null, str4));
                                }
                            } else if (eventType == 3) {
                                String name2 = newPullParser.getName();
                                if (name2.equals("mode")) {
                                    z = false;
                                }
                                if (str3 != null) {
                                    name2.equals(str3);
                                }
                            }
                        }
                    }
                    if (arrayList.size() == 0) {
                        try {
                            fileInputStream.close();
                        } catch (Exception unused) {
                            Log.e("ExynosDisplayUtils", "File Close error");
                        }
                        return null;
                    }
                    String[] strArr = new String[arrayList.size()];
                    for (int i = 0; i < arrayList.size(); i++) {
                        String str5 = (String) arrayList.get(i);
                        strArr[i] = str5;
                        strArr[i] = str5.trim();
                    }
                    try {
                        fileInputStream.close();
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                    return strArr;
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception unused3) {
                            Log.e("ExynosDisplayUtils", "File Close error");
                        }
                    }
                    return null;
                } catch (XmlPullParserException e2) {
                    e = e2;
                    e.printStackTrace();
                }
            } catch (IOException e3) {
                e = e3;
                fileInputStream = null;
            } catch (XmlPullParserException e4) {
                e = e4;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception unused4) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = isFile;
        }
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [boolean] */
    public static String[] parserFactoryXMLText(String str, String str2, String str3, int i, int i2) {
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
                                if (str3.equals("cgc17_enc")) {
                                    if (name.equals("cgc17_enc")) {
                                        String attributeValue = newPullParser.getAttributeValue(null, "att0");
                                        String attributeValue2 = newPullParser.getAttributeValue(null, "att1");
                                        if (Integer.parseInt(attributeValue) == i) {
                                            if (Integer.parseInt(attributeValue2) == i2) {
                                            }
                                        }
                                    }
                                } else if (str3.equals("hsc48_lcg")) {
                                    if (name.equals("hsc48_lcg")) {
                                        if (Integer.parseInt(newPullParser.getAttributeValue(null, "att0")) != i) {
                                        }
                                    }
                                } else if (str3.equals("gamma")) {
                                    if (name.equals("gamma")) {
                                        String attributeValue3 = newPullParser.getAttributeValue(null, "att0");
                                        if (attributeValue3 != null) {
                                            if (Integer.parseInt(attributeValue3) == i) {
                                            }
                                        }
                                    }
                                } else if (str3.equals("degamma")) {
                                    if (name.equals("degamma")) {
                                        String attributeValue4 = newPullParser.getAttributeValue(null, "att0");
                                        if (attributeValue4 != null) {
                                            if (Integer.parseInt(attributeValue4) == i) {
                                            }
                                        }
                                    }
                                } else if (!name.equals(str3)) {
                                }
                                c = 2;
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
                        } catch (Exception unused) {
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
                    } catch (Exception unused2) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                    return strArr;
                } catch (IOException e) {
                    e = e;
                    e.printStackTrace();
                    if (fileInputStream2 == null) {
                        return null;
                    }
                    try {
                        fileInputStream2.close();
                        return null;
                    } catch (Exception unused3) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                        return null;
                    }
                } catch (XmlPullParserException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (fileInputStream2 == null) {
                        return null;
                    }
                    fileInputStream2.close();
                    return null;
                }
            } catch (IOException e3) {
                e = e3;
                fileInputStream2 = null;
            } catch (XmlPullParserException e4) {
                e = e4;
                fileInputStream2 = null;
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception unused4) {
                        Log.e("ExynosDisplayUtils", "File Close error");
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = isFile;
        }
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
}
