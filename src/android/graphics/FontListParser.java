package android.graphics;

import android.graphics.fonts.FontCustomizationParser;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.os.LocaleList;
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
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, null);
        xmlPullParserNewPullParser.nextTag();
        return readFamilies(xmlPullParserNewPullParser, "/system/fonts/", new FontCustomizationParser.Result(), null, 0L, 0, true);
    }

    public static FontConfig parse(String str, String str2, String str3, String str4, Map<String, File> map, long j, int i) throws XmlPullParserException, IOException {
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
            XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
            xmlPullParserNewPullParser.setInput(fileInputStream2, null);
            xmlPullParserNewPullParser.nextTag();
            FontConfig families = readFamilies(xmlPullParserNewPullParser, str2, result2, map, j, i, false);
            fileInputStream2.close();
            return families;
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
                        FontConfig.FontFamily family = readFamily(xmlPullParser, str, map, z);
                        if (family != null) {
                            arrayList.add(family);
                            z2 = false;
                        }
                    } else {
                        FontConfig.NamedFamilyList namedFamily = readNamedFamily(xmlPullParser, str, map, z);
                        if (namedFamily != null) {
                            if (!additionalNamedFamilies.containsKey(attributeValue)) {
                                arrayList2.add(namedFamily);
                            }
                            if (z2) {
                                arrayList.addAll(namedFamily.getFamilies());
                            }
                            z2 = false;
                        }
                    }
                } else if (name.equals("family-list")) {
                    FontConfig.NamedFamilyList namedFamilyList = readNamedFamilyList(xmlPullParser, str, map, z);
                    if (namedFamilyList != null) {
                        if (!additionalNamedFamilies.containsKey(namedFamilyList.getName())) {
                            arrayList2.add(namedFamilyList);
                        }
                        if (z2) {
                            arrayList.addAll(namedFamilyList.getFamilies());
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FontConfig.FontFamily readFamily(XmlPullParser xmlPullParser, String str, Map<String, File> map, boolean z) throws XmlPullParserException, IOException {
        int i;
        String attributeValue = xmlPullParser.getAttributeValue("", ATTR_LANG);
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "variant");
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "ignore");
        ArrayList arrayList = new ArrayList();
        while (true) {
            i = 2;
            if (!keepReading(xmlPullParser)) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("font")) {
                    FontConfig.Font font = readFont(xmlPullParser, str, map, z);
                    if (font != null) {
                        arrayList.add(font);
                    }
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (attributeValue2 == null) {
            i = 0;
        } else if (attributeValue2.equals(VARIANT_COMPACT)) {
            i = 1;
        } else if (!attributeValue2.equals(VARIANT_ELEGANT)) {
        }
        if ((attributeValue3 == null || !(attributeValue3.equals("true") || attributeValue3.equals("1"))) && !arrayList.isEmpty()) {
            return new FontConfig.FontFamily(arrayList, LocaleList.forLanguageTags(attributeValue), i);
        }
        return null;
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
        FontConfig.FontFamily family = readFamily(xmlPullParser, str, map, z);
        if (family == null) {
            return null;
        }
        return new FontConfig.NamedFamilyList(Collections.singletonList(family), attributeValue);
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
                    FontConfig.FontFamily family = readFamily(xmlPullParser, str, map, z);
                    if (family != null) {
                        arrayList.add(family);
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
        int i2 = attributeValue == null ? 0 : Integer.parseInt(attributeValue);
        ArrayList arrayList = new ArrayList();
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "weight");
        int i3 = attributeValue2 == null ? 400 : Integer.parseInt(attributeValue2);
        boolean zEquals = STYLE_ITALIC.equals(xmlPullParser.getAttributeValue(null, "style"));
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
        String strReplaceAll = FILENAME_WHITESPACE_PATTERN.matcher(sb).replaceAll("");
        if (attributeValue5 != null) {
            String[] strArrSplit = attributeValue5.split(",");
            int length = strArrSplit.length;
            int i4 = 0;
            int i5 = 0;
            while (i4 < length) {
                FontConfig.Font font2 = font;
                String strStrip = strArrSplit[i4].strip();
                char c2 = c;
                if (strStrip.equals(TAG_WGHT)) {
                    i5 |= 1;
                } else if (strStrip.equals(TAG_ITAL)) {
                    i5 |= 2;
                }
                i4++;
                c = c2;
                font = font2;
            }
            i = i5;
        } else {
            i = 0;
        }
        FontConfig.Font font3 = font;
        if (attributeValue4 == null) {
            attributeValue4 = strReplaceAll.substring(0, strReplaceAll.length() - 4);
        }
        String str3 = attributeValue4;
        String strFindUpdatedFontFile = findUpdatedFontFile(str3, map);
        if (strFindUpdatedFontFile != null) {
            str2 = str + strReplaceAll;
        } else {
            strFindUpdatedFontFile = str + strReplaceAll;
            str2 = font3;
        }
        String fontVariationSettings = arrayList.isEmpty() ? "" : FontVariationAxis.toFontVariationSettings((FontVariationAxis[]) arrayList.toArray(new FontVariationAxis[0]));
        File file = new File(strFindUpdatedFontFile);
        if (z || file.isFile()) {
            return new FontConfig.Font(file, str2 == 0 ? font3 : new File(str2), str3, new FontStyle(i3, zEquals ? 1 : 0), i2, fontVariationSettings, attributeValue3, i);
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
        int i = attributeValue3 == null ? 400 : Integer.parseInt(attributeValue3);
        skip(xmlPullParser);
        return new FontConfig.Alias(attributeValue, attributeValue2, i);
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
