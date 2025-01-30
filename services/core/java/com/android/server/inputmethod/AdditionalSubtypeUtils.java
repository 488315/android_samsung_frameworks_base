package com.android.server.inputmethod;

import android.icu.util.ULocale;
import android.os.Environment;
import android.os.FileUtils;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import android.view.inputmethod.InputMethodSubtype;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public abstract class AdditionalSubtypeUtils {
    public static File getInputMethodDir(int i) {
        File userSystemDirectory;
        if (i == 0) {
            userSystemDirectory = new File(Environment.getDataDirectory(), "system");
        } else {
            userSystemDirectory = Environment.getUserSystemDirectory(i);
        }
        return new File(userSystemDirectory, "inputmethod");
    }

    public static AtomicFile getAdditionalSubtypeFile(File file) {
        return new AtomicFile(new File(file, "subtypes.xml"), "input-subtypes");
    }

    public static void save(ArrayMap arrayMap, ArrayMap arrayMap2, int i) {
        File inputMethodDir = getInputMethodDir(i);
        if (arrayMap.isEmpty()) {
            if (inputMethodDir.exists()) {
                AtomicFile additionalSubtypeFile = getAdditionalSubtypeFile(inputMethodDir);
                if (additionalSubtypeFile.exists()) {
                    additionalSubtypeFile.delete();
                }
                if (FileUtils.listFilesOrEmpty(inputMethodDir).length != 0 || inputMethodDir.delete()) {
                    return;
                }
                Slog.e("AdditionalSubtypeUtils", "Failed to delete the empty parent directory " + inputMethodDir);
                return;
            }
            return;
        }
        if (!inputMethodDir.exists() && !inputMethodDir.mkdirs()) {
            Slog.e("AdditionalSubtypeUtils", "Failed to create a parent directory " + inputMethodDir);
            return;
        }
        saveToFile(arrayMap, arrayMap2, getAdditionalSubtypeFile(inputMethodDir));
    }

    public static void saveToFile(ArrayMap arrayMap, ArrayMap arrayMap2, AtomicFile atomicFile) {
        FileOutputStream startWrite;
        boolean z = arrayMap2 != null && arrayMap2.size() > 0;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                startWrite = atomicFile.startWrite();
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((String) null, "subtypes");
            for (String str : arrayMap.keySet()) {
                if (z && !arrayMap2.containsKey(str)) {
                    Slog.w("AdditionalSubtypeUtils", "IME uninstalled or not valid.: " + str);
                } else {
                    List<InputMethodSubtype> list = (List) arrayMap.get(str);
                    if (list == null) {
                        Slog.e("AdditionalSubtypeUtils", "Null subtype list for IME " + str);
                    } else {
                        resolveSerializer.startTag((String) null, "imi");
                        resolveSerializer.attribute((String) null, "id", str);
                        for (InputMethodSubtype inputMethodSubtype : list) {
                            resolveSerializer.startTag((String) null, "subtype");
                            if (inputMethodSubtype.hasSubtypeId()) {
                                resolveSerializer.attributeInt((String) null, "subtypeId", inputMethodSubtype.getSubtypeId());
                            }
                            resolveSerializer.attributeInt((String) null, KnoxCustomManagerService.ICON, inputMethodSubtype.getIconResId());
                            resolveSerializer.attributeInt((String) null, "label", inputMethodSubtype.getNameResId());
                            resolveSerializer.attribute((String) null, "nameOverride", inputMethodSubtype.getNameOverride().toString());
                            ULocale physicalKeyboardHintLanguageTag = inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
                            if (physicalKeyboardHintLanguageTag != null) {
                                resolveSerializer.attribute((String) null, "pkLanguageTag", physicalKeyboardHintLanguageTag.toLanguageTag());
                            }
                            resolveSerializer.attribute((String) null, "pkLayoutType", inputMethodSubtype.getPhysicalKeyboardHintLayoutType());
                            resolveSerializer.attribute((String) null, "imeSubtypeLocale", inputMethodSubtype.getLocale());
                            resolveSerializer.attribute((String) null, "languageTag", inputMethodSubtype.getLanguageTag());
                            resolveSerializer.attribute((String) null, "imeSubtypeMode", inputMethodSubtype.getMode());
                            resolveSerializer.attribute((String) null, "imeSubtypeExtraValue", inputMethodSubtype.getExtraValue());
                            resolveSerializer.attributeInt((String) null, "isAuxiliary", inputMethodSubtype.isAuxiliary() ? 1 : 0);
                            resolveSerializer.attributeInt((String) null, "isAsciiCapable", inputMethodSubtype.isAsciiCapable() ? 1 : 0);
                            resolveSerializer.endTag((String) null, "subtype");
                        }
                        resolveSerializer.endTag((String) null, "imi");
                    }
                }
            }
            resolveSerializer.endTag((String) null, "subtypes");
            resolveSerializer.endDocument();
            atomicFile.finishWrite(startWrite);
            IoUtils.closeQuietly(startWrite);
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            Slog.w("AdditionalSubtypeUtils", "Error writing subtypes", e);
            if (fileOutputStream != null) {
                atomicFile.failWrite(fileOutputStream);
            }
            IoUtils.closeQuietly(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = startWrite;
            IoUtils.closeQuietly(fileOutputStream);
            throw th;
        }
    }

    public static void load(ArrayMap arrayMap, int i) {
        arrayMap.clear();
        AtomicFile additionalSubtypeFile = getAdditionalSubtypeFile(getInputMethodDir(i));
        if (additionalSubtypeFile.exists()) {
            loadFromFile(arrayMap, additionalSubtypeFile);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x0185 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:? A[Catch: IOException | NumberFormatException | XmlPullParserException -> 0x018f, IOException | NumberFormatException | XmlPullParserException -> 0x018f, IOException | NumberFormatException | XmlPullParserException -> 0x018f, SYNTHETIC, TRY_LEAVE, TryCatch #1 {IOException | NumberFormatException | XmlPullParserException -> 0x018f, blocks: (B:23:0x016d, B:79:0x018e, B:79:0x018e, B:79:0x018e, B:78:0x018b, B:78:0x018b, B:78:0x018b), top: B:2:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void loadFromFile(ArrayMap arrayMap, AtomicFile atomicFile) {
        FileInputStream fileInputStream;
        Throwable th;
        int i;
        int i2;
        int i3;
        String str;
        String str2;
        String str3 = "AdditionalSubtypeUtils";
        try {
            try {
                FileInputStream openRead = atomicFile.openRead();
                try {
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                    int next = resolvePullParser.next();
                    while (true) {
                        i = 1;
                        i2 = 2;
                        if (next == 2 || next == 1) {
                            break;
                        } else {
                            next = resolvePullParser.next();
                        }
                    }
                    try {
                        if (!"subtypes".equals(resolvePullParser.getName())) {
                            throw new XmlPullParserException("Xml doesn't start with subtypes");
                        }
                        int depth = resolvePullParser.getDepth();
                        String str4 = null;
                        String str5 = null;
                        ArrayList arrayList = null;
                        while (true) {
                            int next2 = resolvePullParser.next();
                            if ((next2 != 3 || resolvePullParser.getDepth() > depth) && next2 != i) {
                                if (next2 == i2) {
                                    String name = resolvePullParser.getName();
                                    if ("imi".equals(name)) {
                                        str5 = resolvePullParser.getAttributeValue(str4, "id");
                                        if (TextUtils.isEmpty(str5)) {
                                            Slog.w(str3, "Invalid imi id found in subtypes.xml");
                                        } else {
                                            arrayList = new ArrayList();
                                            arrayMap.put(str5, arrayList);
                                        }
                                    } else if ("subtype".equals(name)) {
                                        try {
                                            if (!TextUtils.isEmpty(str5) && arrayList != null) {
                                                int attributeInt = resolvePullParser.getAttributeInt(str4, KnoxCustomManagerService.ICON);
                                                int attributeInt2 = resolvePullParser.getAttributeInt(str4, "label");
                                                String attributeValue = resolvePullParser.getAttributeValue(str4, "nameOverride");
                                                String attributeValue2 = resolvePullParser.getAttributeValue(str4, "pkLanguageTag");
                                                String attributeValue3 = resolvePullParser.getAttributeValue(str4, "pkLayoutType");
                                                String attributeValue4 = resolvePullParser.getAttributeValue(str4, "imeSubtypeLocale");
                                                String attributeValue5 = resolvePullParser.getAttributeValue(str4, "languageTag");
                                                i3 = depth;
                                                String attributeValue6 = resolvePullParser.getAttributeValue(str4, "imeSubtypeMode");
                                                String attributeValue7 = resolvePullParser.getAttributeValue(str4, "imeSubtypeExtraValue");
                                                fileInputStream = openRead;
                                                try {
                                                    boolean equals = "1".equals(String.valueOf(resolvePullParser.getAttributeValue(str4, "isAuxiliary")));
                                                    String str6 = str3;
                                                    try {
                                                        boolean equals2 = "1".equals(String.valueOf(resolvePullParser.getAttributeValue(str4, "isAsciiCapable")));
                                                        InputMethodSubtype.InputMethodSubtypeBuilder subtypeNameResId = new InputMethodSubtype.InputMethodSubtypeBuilder().setSubtypeNameResId(attributeInt2);
                                                        ULocale uLocale = attributeValue2 == null ? null : new ULocale(attributeValue2);
                                                        if (attributeValue3 == null) {
                                                            attributeValue3 = "";
                                                        }
                                                        InputMethodSubtype.InputMethodSubtypeBuilder isAsciiCapable = subtypeNameResId.setPhysicalKeyboardHint(uLocale, attributeValue3).setSubtypeIconResId(attributeInt).setSubtypeLocale(attributeValue4).setLanguageTag(attributeValue5).setSubtypeMode(attributeValue6).setSubtypeExtraValue(attributeValue7).setIsAuxiliary(equals).setIsAsciiCapable(equals2);
                                                        str = null;
                                                        int attributeInt3 = resolvePullParser.getAttributeInt((String) null, "subtypeId", 0);
                                                        if (attributeInt3 != 0) {
                                                            isAsciiCapable.setSubtypeId(attributeInt3);
                                                        }
                                                        if (attributeValue != null) {
                                                            isAsciiCapable.setSubtypeNameOverride(attributeValue);
                                                        }
                                                        arrayList.add(isAsciiCapable.build());
                                                        str2 = str6;
                                                        str3 = str2;
                                                        str4 = str;
                                                        depth = i3;
                                                        openRead = fileInputStream;
                                                        i = 1;
                                                        i2 = 2;
                                                    } catch (Throwable th2) {
                                                        th = th2;
                                                        if (fileInputStream == null) {
                                                        }
                                                    }
                                                } catch (Throwable th3) {
                                                    th = th3;
                                                    th = th;
                                                    if (fileInputStream == null) {
                                                    }
                                                }
                                            }
                                            str2 = str3;
                                            Slog.w(str2, "IME uninstalled or not valid.: " + str5);
                                            str3 = str2;
                                            str4 = str;
                                            depth = i3;
                                            openRead = fileInputStream;
                                            i = 1;
                                            i2 = 2;
                                        } catch (Throwable th4) {
                                            th = th4;
                                            th = th;
                                            if (fileInputStream == null) {
                                                throw th;
                                            }
                                            try {
                                                fileInputStream.close();
                                                throw th;
                                            } catch (Throwable th5) {
                                                th.addSuppressed(th5);
                                                throw th;
                                            }
                                        }
                                        fileInputStream = openRead;
                                        i3 = depth;
                                        str = str4;
                                    }
                                }
                                fileInputStream = openRead;
                                i3 = depth;
                                str = str4;
                                str2 = str3;
                                str3 = str2;
                                str4 = str;
                                depth = i3;
                                openRead = fileInputStream;
                                i = 1;
                                i2 = 2;
                            }
                        }
                        FileInputStream fileInputStream2 = openRead;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    fileInputStream = openRead;
                }
            } catch (IOException | NumberFormatException | XmlPullParserException e) {
                e = e;
                Slog.w("AdditionalSubtypeUtils", "Error reading subtypes", e);
            }
        } catch (IOException | NumberFormatException | XmlPullParserException e2) {
            e = e2;
            Slog.w("AdditionalSubtypeUtils", "Error reading subtypes", e);
        }
    }
}
