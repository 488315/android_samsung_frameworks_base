package android.graphics.fonts;

import android.graphics.FontListParser;
import android.text.FontConfig;
import android.util.Xml;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class FontCustomizationParser {
    private static final String TAG = "FontCustomizationParser";

    public static class Result {
        private final List<FontConfig.Alias> mAdditionalAliases;
        private final Map<String, FontConfig.NamedFamilyList> mAdditionalNamedFamilies;
        private final List<FontConfig.Customization.LocaleFallback> mLocaleFamilyCustomizations;

        public Result() {
            this.mAdditionalNamedFamilies = Collections.EMPTY_MAP;
            this.mLocaleFamilyCustomizations = Collections.EMPTY_LIST;
            this.mAdditionalAliases = Collections.EMPTY_LIST;
        }

        public Result(Map<String, FontConfig.NamedFamilyList> map, List<FontConfig.Customization.LocaleFallback> list, List<FontConfig.Alias> list2) {
            this.mAdditionalNamedFamilies = map;
            this.mLocaleFamilyCustomizations = list;
            this.mAdditionalAliases = list2;
        }

        public Map<String, FontConfig.NamedFamilyList> getAdditionalNamedFamilies() {
            return this.mAdditionalNamedFamilies;
        }

        public List<FontConfig.Alias> getAdditionalAliases() {
            return this.mAdditionalAliases;
        }

        public List<FontConfig.Customization.LocaleFallback> getLocaleFamilyCustomizations() {
            return this.mLocaleFamilyCustomizations;
        }
    }

    public static Result parse(InputStream inputStream, String str, Map<String, File> map) throws XmlPullParserException, IOException {
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(inputStream, null);
        newPullParser.nextTag();
        return readFamilies(newPullParser, str, map);
    }

    private static Result validateAndTransformToResult(List<FontConfig.NamedFamilyList> list, List<FontConfig.Customization.LocaleFallback> list2, List<FontConfig.Alias> list3) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            FontConfig.NamedFamilyList namedFamilyList = list.get(i);
            String name = namedFamilyList.getName();
            if (name != null) {
                if (hashMap.put(name, namedFamilyList) != null) {
                    throw new IllegalArgumentException("new-named-family requires unique name attribute");
                }
            } else {
                throw new IllegalArgumentException("new-named-family requires name attribute or new-default-fallback-familyrequires fallackTarget attribute");
            }
        }
        return new Result(hashMap, list2, list3);
    }

    private static Result readFamilies(XmlPullParser xmlPullParser, String str, Map<String, File> map) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        xmlPullParser.require(2, null, "fonts-modification");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("family")) {
                    readFamily(xmlPullParser, str, arrayList, arrayList3, map);
                } else if (name.equals("family-list")) {
                    readFamilyList(xmlPullParser, str, arrayList, map);
                } else if (name.equals("alias")) {
                    arrayList2.add(FontListParser.readAlias(xmlPullParser));
                } else {
                    FontListParser.skip(xmlPullParser);
                }
            }
        }
        return validateAndTransformToResult(arrayList, arrayList3, arrayList2);
    }

    private static void readFamily(XmlPullParser xmlPullParser, String str, List<FontConfig.NamedFamilyList> list, List<FontConfig.Customization.LocaleFallback> list2, Map<String, File> map) throws XmlPullParserException, IOException {
        int i;
        String attributeValue = xmlPullParser.getAttributeValue(null, "customizationType");
        if (attributeValue == null) {
            throw new IllegalArgumentException("customizationType must be specified");
        }
        if (attributeValue.equals("new-named-family")) {
            FontConfig.NamedFamilyList readNamedFamily = FontListParser.readNamedFamily(xmlPullParser, str, map, false);
            if (readNamedFamily != null) {
                list.add(readNamedFamily);
                return;
            }
            return;
        }
        if (attributeValue.equals("new-locale-family")) {
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "lang");
            String attributeValue3 = xmlPullParser.getAttributeValue(null, "operation");
            if (attributeValue3.equals("append")) {
                i = 1;
            } else if (attributeValue3.equals("prepend")) {
                i = 0;
            } else {
                if (!attributeValue3.equals("replace")) {
                    throw new IllegalArgumentException("Unknown operation=" + attributeValue3);
                }
                i = 2;
            }
            list2.add(new FontConfig.Customization.LocaleFallback(Locale.forLanguageTag(attributeValue2), i, FontListParser.readFamily(xmlPullParser, str, map, false)));
            return;
        }
        throw new IllegalArgumentException("Unknown customizationType=" + attributeValue);
    }

    private static void readFamilyList(XmlPullParser xmlPullParser, String str, List<FontConfig.NamedFamilyList> list, Map<String, File> map) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "customizationType");
        if (attributeValue == null) {
            throw new IllegalArgumentException("customizationType must be specified");
        }
        if (attributeValue.equals("new-named-family")) {
            FontConfig.NamedFamilyList readNamedFamilyList = FontListParser.readNamedFamilyList(xmlPullParser, str, map, false);
            if (readNamedFamilyList != null) {
                list.add(readNamedFamilyList);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("Unknown customizationType=" + attributeValue);
    }
}
