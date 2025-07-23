package android.graphics;

import android.graphics.fonts.FontCustomizationParser;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.text.FontConfig;
import android.util.ArraySet;
import android.util.Xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class FontListParser {
    public static final String ATTR_FALLBACK_FOR = "fallbackFor";
    public static final String ATTR_INDEX = "index";
    private static final String ATTR_LANG = "lang";
    private static final String ATTR_NAME = "name";
    public static final String ATTR_POSTSCRIPT_NAME = "postScriptName";
    public static final String ATTR_STYLE = "style";
    public static final String ATTR_STYLEVALUE = "stylevalue";
    public static final String ATTR_SUPPORTED_AXES = "supportedAxes";
    public static final String ATTR_TAG = "tag";
    private static final String ATTR_VARIANT = "variant";
    public static final String ATTR_WEIGHT = "weight";
    private static final Pattern FILENAME_WHITESPACE_PATTERN = Pattern.compile("^[ \\n\\r\\t]+|[ \\n\\r\\t]+$");
    public static final String STYLE_ITALIC = "italic";
    public static final String STYLE_NORMAL = "normal";
    private static final String TAG = "FontListParser";
    public static final String TAG_AXIS = "axis";
    private static final String TAG_FONT = "font";
    private static final String TAG_ITAL = "ital";
    private static final String TAG_WGHT = "wght";
    private static final String VARIANT_COMPACT = "compact";
    private static final String VARIANT_ELEGANT = "elegant";

    public static FontConfig parse(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, null);
        newPullParser.nextTag();
        return readFamilies(newPullParser, "/system/fonts/", new FontCustomizationParser.Result(), null, 0L, 0, true);
    }

    public static FontConfig parse(String str, String str2, String str3, String str4, Map<String, File> map, long j, int i) throws IOException, XmlPullParserException {
        FontCustomizationParser.Result result;
        if (str3 != null) {
            try {
                FileInputStream fileInputStream = new FileInputStream(str3);
                try {
                    result = FontCustomizationParser.parse(fileInputStream, str4, map);
                    fileInputStream.close();
                } finally {
                }
            } catch (IOException unused) {
                result = new FontCustomizationParser.Result();
            }
        } else {
            result = new FontCustomizationParser.Result();
        }
        FontCustomizationParser.Result result2 = result;
        FileInputStream fileInputStream2 = new FileInputStream(str);
        try {
            XmlPullParser newPullParser = Xml.newPullParser();
            newPullParser.setInput(fileInputStream2, null);
            newPullParser.nextTag();
            FontConfig readFamilies = readFamilies(newPullParser, str2, result2, map, j, i, false);
            fileInputStream2.close();
            return readFamilies;
        } finally {
        }
    }

    public static FontConfig readFamilies(XmlPullParser xmlPullParser, String str, FontCustomizationParser.Result result, Map<String, File> map, long j, int i, boolean z) throws XmlPullParserException, IOException {
        int i2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList(result.getAdditionalAliases());
        Map<String, FontConfig.NamedFamilyList> additionalNamedFamilies = result.getAdditionalNamedFamilies();
        xmlPullParser.require(2, null, "familyset");
        boolean z2 = true;
        while (true) {
            if (!keepReading(xmlPullParser)) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("family")) {
                    String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                    if (attributeValue == null) {
                        FontConfig.FontFamily readFamily = readFamily(xmlPullParser, str, map, z);
                        if (readFamily != null) {
                            arrayList.add(readFamily);
                            z2 = false;
                        }
                    } else {
                        FontConfig.NamedFamilyList readNamedFamily = readNamedFamily(xmlPullParser, str, map, z);
                        if (readNamedFamily != null) {
                            if (!additionalNamedFamilies.containsKey(attributeValue)) {
                                arrayList2.add(readNamedFamily);
                            }
                            if (z2) {
                                arrayList.addAll(readNamedFamily.getFamilies());
                            }
                            z2 = false;
                        }
                    }
                } else if (name.equals("family-list")) {
                    FontConfig.NamedFamilyList readNamedFamilyList = readNamedFamilyList(xmlPullParser, str, map, z);
                    if (readNamedFamilyList != null) {
                        if (!additionalNamedFamilies.containsKey(readNamedFamilyList.getName())) {
                            arrayList2.add(readNamedFamilyList);
                        }
                        if (z2) {
                            arrayList.addAll(readNamedFamilyList.getFamilies());
                        }
                        z2 = false;
                    }
                } else if (name.equals("alias")) {
                    arrayList3.add(readAlias(xmlPullParser));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        arrayList2.addAll(additionalNamedFamilies.values());
        ArraySet arraySet = new ArraySet();
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            String name2 = ((FontConfig.NamedFamilyList) arrayList2.get(i3)).getName();
            if (name2 != null) {
                arraySet.add(name2);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (i2 = 0; i2 < arrayList3.size(); i2++) {
            FontConfig.Alias alias = (FontConfig.Alias) arrayList3.get(i2);
            if (arraySet.contains(alias.getOriginal())) {
                arrayList4.add(alias);
            }
        }
        return new FontConfig(arrayList, arrayList4, arrayList2, result.getLocaleFamilyCustomizations(), j, i);
    }

    private static boolean keepReading(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next = xmlPullParser.next();
        return (next == 3 || next == 1) ? false : true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0055, code lost:
    
        if (r1.equals(android.graphics.FontListParser.VARIANT_ELEGANT) != false) goto L22;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.text.FontConfig.FontFamily readFamily(org.xmlpull.v1.XmlPullParser r7, java.lang.String r8, java.util.Map<java.lang.String, java.io.File> r9, boolean r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = "lang"
            java.lang.String r0 = r7.getAttributeValue(r0, r1)
            java.lang.String r1 = "variant"
            r2 = 0
            java.lang.String r1 = r7.getAttributeValue(r2, r1)
            java.lang.String r3 = "ignore"
            java.lang.String r3 = r7.getAttributeValue(r2, r3)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
        L1b:
            boolean r5 = keepReading(r7)
            r6 = 2
            if (r5 == 0) goto L43
            int r5 = r7.getEventType()
            if (r5 == r6) goto L29
            goto L1b
        L29:
            java.lang.String r5 = r7.getName()
            java.lang.String r6 = "font"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L3f
            android.text.FontConfig$Font r5 = readFont(r7, r8, r9, r10)
            if (r5 == 0) goto L1b
            r4.add(r5)
            goto L1b
        L3f:
            skip(r7)
            goto L1b
        L43:
            if (r1 == 0) goto L58
            java.lang.String r7 = "compact"
            boolean r7 = r1.equals(r7)
            if (r7 == 0) goto L4f
            r6 = 1
            goto L59
        L4f:
            java.lang.String r7 = "elegant"
            boolean r7 = r1.equals(r7)
            if (r7 == 0) goto L58
            goto L59
        L58:
            r6 = 0
        L59:
            if (r3 == 0) goto L6d
            java.lang.String r7 = "true"
            boolean r7 = r3.equals(r7)
            if (r7 != 0) goto L73
            java.lang.String r7 = "1"
            boolean r7 = r3.equals(r7)
            if (r7 == 0) goto L6d
            goto L73
        L6d:
            boolean r7 = r4.isEmpty()
            if (r7 == 0) goto L74
        L73:
            return r2
        L74:
            android.text.FontConfig$FontFamily r7 = new android.text.FontConfig$FontFamily
            android.os.LocaleList r8 = android.os.LocaleList.forLanguageTags(r0)
            r7.<init>(r4, r8, r6)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.FontListParser.readFamily(org.xmlpull.v1.XmlPullParser, java.lang.String, java.util.Map, boolean):android.text.FontConfig$FontFamily");
    }

    private static void throwIfAttributeExists(String str, XmlPullParser xmlPullParser) {
        if (xmlPullParser.getAttributeValue(null, str) == null) {
            return;
        }
        throw new IllegalArgumentException(str + " cannot be used in FontFamily inside  family or family-list with name attribute.");
    }

    public static FontConfig.NamedFamilyList readNamedFamily(XmlPullParser xmlPullParser, String str, Map<String, File> map, boolean z) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        throwIfAttributeExists(ATTR_LANG, xmlPullParser);
        throwIfAttributeExists("variant", xmlPullParser);
        throwIfAttributeExists("ignore", xmlPullParser);
        FontConfig.FontFamily readFamily = readFamily(xmlPullParser, str, map, z);
        if (readFamily == null) {
            return null;
        }
        return new FontConfig.NamedFamilyList(Collections.singletonList(readFamily), attributeValue);
    }

    public static FontConfig.NamedFamilyList readNamedFamilyList(XmlPullParser xmlPullParser, String str, Map<String, File> map, boolean z) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        ArrayList arrayList = new ArrayList();
        while (keepReading(xmlPullParser)) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("family")) {
                    throwIfAttributeExists("name", xmlPullParser);
                    throwIfAttributeExists(ATTR_LANG, xmlPullParser);
                    throwIfAttributeExists("variant", xmlPullParser);
                    throwIfAttributeExists("ignore", xmlPullParser);
                    FontConfig.FontFamily readFamily = readFamily(xmlPullParser, str, map, z);
                    if (readFamily != null) {
                        arrayList.add(readFamily);
                    }
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new FontConfig.NamedFamilyList(arrayList, attributeValue);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static FontConfig.Font readFont(XmlPullParser xmlPullParser, String str, Map<String, File> map, boolean z) throws XmlPullParserException, IOException {
        char c;
        int i;
        String str2;
        FontConfig.Font font = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "index");
        int parseInt = attributeValue == null ? 0 : Integer.parseInt(attributeValue);
        ArrayList arrayList = new ArrayList();
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "weight");
        int parseInt2 = attributeValue2 == null ? 400 : Integer.parseInt(attributeValue2);
        boolean equals = STYLE_ITALIC.equals(xmlPullParser.getAttributeValue(null, "style"));
        String attributeValue3 = xmlPullParser.getAttributeValue(null, ATTR_FALLBACK_FOR);
        String attributeValue4 = xmlPullParser.getAttributeValue(null, ATTR_POSTSCRIPT_NAME);
        String attributeValue5 = xmlPullParser.getAttributeValue(null, ATTR_SUPPORTED_AXES);
        StringBuilder sb = new StringBuilder();
        while (true) {
            c = 4;
            if (!keepReading(xmlPullParser)) {
                break;
            }
            if (xmlPullParser.getEventType() == 4) {
                sb.append(xmlPullParser.getText());
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(TAG_AXIS)) {
                    arrayList.add(readAxis(xmlPullParser));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        String replaceAll = FILENAME_WHITESPACE_PATTERN.matcher(sb).replaceAll("");
        if (attributeValue5 != null) {
            String[] split = attributeValue5.split(",");
            int length = split.length;
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                FontConfig.Font font2 = font;
                String strip = split[i2].strip();
                char c2 = c;
                if (strip.equals(TAG_WGHT)) {
                    i3 |= 1;
                } else if (strip.equals(TAG_ITAL)) {
                    i3 |= 2;
                }
                i2++;
                c = c2;
                font = font2;
            }
            i = i3;
        } else {
            i = 0;
        }
        FontConfig.Font font3 = font;
        if (attributeValue4 == null) {
            attributeValue4 = replaceAll.substring(0, replaceAll.length() - 4);
        }
        String str3 = attributeValue4;
        String findUpdatedFontFile = findUpdatedFontFile(str3, map);
        if (findUpdatedFontFile != null) {
            str2 = str + replaceAll;
        } else {
            findUpdatedFontFile = str + replaceAll;
            str2 = font3;
        }
        String fontVariationSettings = arrayList.isEmpty() ? "" : FontVariationAxis.toFontVariationSettings((FontVariationAxis[]) arrayList.toArray(new FontVariationAxis[0]));
        File file = new File(findUpdatedFontFile);
        if (z || file.isFile()) {
            return new FontConfig.Font(file, str2 == 0 ? font3 : new File(str2), str3, new FontStyle(parseInt2, equals ? 1 : 0), parseInt, fontVariationSettings, attributeValue3, i);
        }
        return font3;
    }

    private static String findUpdatedFontFile(String str, Map<String, File> map) {
        File file;
        if (map == null || (file = map.get(str)) == null) {
            return null;
        }
        return file.getAbsolutePath();
    }

    private static FontVariationAxis readAxis(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "tag");
        String attributeValue2 = xmlPullParser.getAttributeValue(null, ATTR_STYLEVALUE);
        skip(xmlPullParser);
        return new FontVariationAxis(attributeValue, Float.parseFloat(attributeValue2));
    }

    public static FontConfig.Alias readAlias(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "to");
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "weight");
        int parseInt = attributeValue3 == null ? 400 : Integer.parseInt(attributeValue3);
        skip(xmlPullParser);
        return new FontConfig.Alias(attributeValue, attributeValue2, parseInt);
    }

    public static void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int i = 1;
        while (i > 0) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }
}
