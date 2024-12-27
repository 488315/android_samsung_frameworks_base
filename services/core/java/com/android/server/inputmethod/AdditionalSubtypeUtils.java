package com.android.server.inputmethod;

import android.icu.util.ULocale;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import android.view.inputmethod.InputMethodSubtype;

import com.android.modules.utils.TypedXmlSerializer;

import libcore.io.IoUtils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class AdditionalSubtypeUtils {
    public static AdditionalSubtypeMap load(int i) {
        AtomicFile atomicFile =
                new AtomicFile(
                        new File(
                                new File(
                                        i == 0
                                                ? new File(Environment.getDataDirectory(), "system")
                                                : Environment.getUserSystemDirectory(i),
                                        "inputmethod"),
                                "subtypes.xml"),
                        "input-subtypes");
        return atomicFile.exists() ? loadFromFile(atomicFile) : AdditionalSubtypeMap.EMPTY_MAP;
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0185, code lost:

       r2 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0187, code lost:

       android.util.Slog.w(r2, "IME uninstalled or not valid.: " + r9);
    */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0199, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0026, code lost:

       r1 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0054, code lost:

       if (r11 == r7) goto L24;
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0060, code lost:

       r11 = r4.getName();
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006b, code lost:

       if ("imi".equals(r11) == false) goto L117;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006d, code lost:

       r9 = r4.getAttributeValue(r8, "id");
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0078, code lost:

       if (android.text.TextUtils.isEmpty(r9) == false) goto L119;
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x007a, code lost:

       android.util.Slog.w(r1, "Invalid imi id found in subtypes.xml");
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0080, code lost:

       r10 = new java.util.ArrayList();
       r2.put(r9, r10);
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0056, code lost:

       r17 = r2;
       r18 = r3;
       r16 = r5;
       r5 = r8;
       r2 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x018a, code lost:

       r1 = r2;
       r8 = r5;
       r5 = r16;
       r2 = r17;
       r3 = r18;
       r6 = 1;
       r7 = 2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0090, code lost:

       if ("subtype".equals(r11) == false) goto L23;
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0096, code lost:

       if (android.text.TextUtils.isEmpty(r9) != false) goto L35;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0098, code lost:

       if (r10 != null) goto L110;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a5, code lost:

       r11 = r4.getAttributeInt(r8, com.samsung.android.knox.custom.KnoxCustomManagerService.ICON);
       r12 = r4.getAttributeInt(r8, "label");
       r13 = r4.getAttributeValue(r8, "nameOverride");
       r14 = r4.getAttributeValue(r8, "pkLanguageTag");
       r15 = r4.getAttributeValue(r8, "pkLayoutType");
       r6 = r4.getAttributeValue(r8, "imeSubtypeLocale");
       r7 = r4.getAttributeValue(r8, "languageTag");
       r16 = r5;
       r5 = r4.getAttributeValue(r8, "imeSubtypeMode");
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00df, code lost:

       r17 = r2;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00e1, code lost:

       r2 = r4.getAttributeValue(r8, "imeSubtypeExtraValue");
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00e8, code lost:

       r18 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00ea, code lost:

       r3 = "1".equals(java.lang.String.valueOf(r4.getAttributeValue(r8, "isAuxiliary")));
    */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00f9, code lost:

       r19 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00fb, code lost:

       r1 = "1".equals(java.lang.String.valueOf(r4.getAttributeValue(r8, "isAsciiCapable")));
       r8 = new android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder().setSubtypeNameResId(r12);
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0113, code lost:

       if (r14 != null) goto L45;
    */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0115, code lost:

       r12 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011c, code lost:

       if (r15 != null) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x011e, code lost:

       r15 = "";
    */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0127, code lost:

       r1 = r8.setPhysicalKeyboardHint(r12, r15).setSubtypeIconResId(r11).setSubtypeLocale(r6).setLanguageTag(r7).setSubtypeMode(r5).setSubtypeExtraValue(r2).setIsAuxiliary(r3).setIsAsciiCapable(r1);
       r5 = null;
       r2 = r4.getAttributeInt((java.lang.String) null, "subtypeId", 0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0150, code lost:

       if (r2 == 0) goto L53;
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0152, code lost:

       r1.setSubtypeId(r2);
    */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0155, code lost:

       if (r13 == null) goto L55;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0157, code lost:

       r1.setSubtypeNameOverride(r13);
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x015a, code lost:

       r10.add(r1.build());
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0161, code lost:

       r2 = r19;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0117, code lost:

       r12 = new android.icu.util.ULocale(r14);
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0121, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0122, code lost:

       r1 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01b3, code lost:

       if (r18 != null) goto L108;
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01b5, code lost:

       r18.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:?, code lost:

       throw r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01b9, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01bb, code lost:

       r1.addSuppressed(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01be, code lost:

       throw r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:87:?, code lost:

       throw r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0164, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0165, code lost:

       r19 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0168, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0169, code lost:

       r19 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x016b, code lost:

       r18 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x016e, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x016f, code lost:

       r19 = r1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x009a, code lost:

       r17 = r2;
       r18 = r3;
       r16 = r5;
       r5 = r8;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:? A[Catch: IOException | NumberFormatException | XmlPullParserException -> 0x01a4, IOException | NumberFormatException | XmlPullParserException -> 0x01a4, IOException | NumberFormatException | XmlPullParserException -> 0x01a4, SYNTHETIC, TRY_LEAVE, TryCatch #0 {IOException | NumberFormatException | XmlPullParserException -> 0x01a4, blocks: (B:23:0x01a0, B:86:0x01be, B:86:0x01be, B:86:0x01be, B:85:0x01bb, B:85:0x01bb, B:85:0x01bb), top: B:2:0x0009 }] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.util.ArrayMap] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v6, types: [android.util.ArrayMap] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.inputmethod.AdditionalSubtypeMap loadFromFile(
            android.util.AtomicFile r20) {
        /*
            Method dump skipped, instructions count: 475
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.inputmethod.AdditionalSubtypeUtils.loadFromFile(android.util.AtomicFile):com.android.server.inputmethod.AdditionalSubtypeMap");
    }

    public static void saveToFile(
            AdditionalSubtypeMap additionalSubtypeMap,
            InputMethodMap inputMethodMap,
            AtomicFile atomicFile) {
        boolean z = inputMethodMap != null && inputMethodMap.mMap.size() > 0;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.setFeature(
                            "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startTag((String) null, "subtypes");
                    for (String str : additionalSubtypeMap.mMap.keySet()) {
                        if (!z || inputMethodMap.mMap.containsKey(str)) {
                            List<InputMethodSubtype> list =
                                    (List) additionalSubtypeMap.mMap.get(str);
                            if (list == null) {
                                Slog.e(
                                        "AdditionalSubtypeUtils",
                                        "Null subtype list for IME " + str);
                            } else {
                                resolveSerializer.startTag((String) null, "imi");
                                resolveSerializer.attribute((String) null, "id", str);
                                for (InputMethodSubtype inputMethodSubtype : list) {
                                    resolveSerializer.startTag((String) null, "subtype");
                                    if (inputMethodSubtype.hasSubtypeId()) {
                                        resolveSerializer.attributeInt(
                                                (String) null,
                                                "subtypeId",
                                                inputMethodSubtype.getSubtypeId());
                                    }
                                    resolveSerializer.attributeInt(
                                            (String) null,
                                            KnoxCustomManagerService.ICON,
                                            inputMethodSubtype.getIconResId());
                                    resolveSerializer.attributeInt(
                                            (String) null,
                                            "label",
                                            inputMethodSubtype.getNameResId());
                                    resolveSerializer.attribute(
                                            (String) null,
                                            "nameOverride",
                                            inputMethodSubtype.getNameOverride().toString());
                                    ULocale physicalKeyboardHintLanguageTag =
                                            inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
                                    if (physicalKeyboardHintLanguageTag != null) {
                                        resolveSerializer.attribute(
                                                (String) null,
                                                "pkLanguageTag",
                                                physicalKeyboardHintLanguageTag.toLanguageTag());
                                    }
                                    resolveSerializer.attribute(
                                            (String) null,
                                            "pkLayoutType",
                                            inputMethodSubtype.getPhysicalKeyboardHintLayoutType());
                                    resolveSerializer.attribute(
                                            (String) null,
                                            "imeSubtypeLocale",
                                            inputMethodSubtype.getLocale());
                                    resolveSerializer.attribute(
                                            (String) null,
                                            "languageTag",
                                            inputMethodSubtype.getLanguageTag());
                                    resolveSerializer.attribute(
                                            (String) null,
                                            "imeSubtypeMode",
                                            inputMethodSubtype.getMode());
                                    resolveSerializer.attribute(
                                            (String) null,
                                            "imeSubtypeExtraValue",
                                            inputMethodSubtype.getExtraValue());
                                    resolveSerializer.attributeInt(
                                            (String) null,
                                            "isAuxiliary",
                                            inputMethodSubtype.isAuxiliary() ? 1 : 0);
                                    resolveSerializer.attributeInt(
                                            (String) null,
                                            "isAsciiCapable",
                                            inputMethodSubtype.isAsciiCapable() ? 1 : 0);
                                    resolveSerializer.endTag((String) null, "subtype");
                                }
                                resolveSerializer.endTag((String) null, "imi");
                            }
                        } else {
                            Slog.w(
                                    "AdditionalSubtypeUtils",
                                    "IME uninstalled or not valid.: " + str);
                        }
                    }
                    resolveSerializer.endTag((String) null, "subtypes");
                    resolveSerializer.endDocument();
                    atomicFile.finishWrite(startWrite);
                    IoUtils.closeQuietly(startWrite);
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = startWrite;
                    Slog.w("AdditionalSubtypeUtils", "Error writing subtypes", e);
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    IoUtils.closeQuietly(fileOutputStream);
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = startWrite;
                    IoUtils.closeQuietly(fileOutputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }
}
