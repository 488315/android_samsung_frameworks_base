package android.graphics.fonts;

import android.graphics.FontListParser;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.os.LocaleList;
import android.os.SystemProperties;
import android.text.FontConfig;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.text.flags.Flags;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class SystemFonts {
    private static final String FONTS_ADDITIONAL_XML = "/system/etc/fonts_additional.xml";
    private static final String FONTS_XML = "/system/etc/font_fallback.xml";
    private static final String LEGACY_FONTS_XML = "/system/etc/fonts.xml";
    private static final Object LOCK = new Object();
    public static final String OEM_FONT_DIR = "/product/fonts/";
    private static final String OEM_XML = "/product/etc/fonts_customization.xml";
    public static final String SYSTEM_FONT_DIR = "/system/fonts/";
    private static final String TAG = "SystemFonts";
    private static Set<Font> sAvailableFonts;

    private SystemFonts() {
    }

    public static Set<Font> getAvailableFonts() {
        Set<Font> set;
        synchronized (LOCK) {
            if (sAvailableFonts == null) {
                sAvailableFonts = Font.getAvailableFonts();
            }
            set = sAvailableFonts;
        }
        return set;
    }

    public static void resetAvailableFonts() {
        synchronized (LOCK) {
            sAvailableFonts = null;
        }
    }

    private static ByteBuffer mmap(String fullPath) {
        try {
            FileInputStream file = new FileInputStream(fullPath);
            try {
                FileChannel fileChannel = file.getChannel();
                long fontSize = fileChannel.size();
                MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, fontSize);
                file.close();
                return map;
            } finally {
            }
        } catch (IOException e) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0031 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int resolveVarFamilyType(android.text.FontConfig.FontFamily r10, java.lang.String r11) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            java.util.List r4 = r10.getFontList()
            r5 = 0
        L9:
            int r6 = r4.size()
            r7 = 0
            r8 = 1
            if (r5 >= r6) goto L4e
            java.lang.Object r6 = r4.get(r5)
            android.text.FontConfig$Font r6 = (android.text.FontConfig.Font) r6
            if (r11 != 0) goto L20
            java.lang.String r9 = r6.getFontFamilyName()
            if (r9 == 0) goto L2b
            goto L4b
        L20:
            java.lang.String r9 = r6.getFontFamilyName()
            boolean r9 = r11.equals(r9)
            if (r9 != 0) goto L2b
            goto L4b
        L2b:
            int r9 = r6.getVarTypeAxes()
            if (r9 != 0) goto L32
            return r7
        L32:
            r7 = r9 & 1
            if (r7 == 0) goto L38
            int r0 = r0 + 1
        L38:
            r7 = r9 & 2
            if (r7 == 0) goto L3e
            int r1 = r1 + 1
        L3e:
            android.graphics.fonts.FontStyle r7 = r6.getStyle()
            int r7 = r7.getSlant()
            if (r7 != r8) goto L49
            r3 = 1
        L49:
            int r2 = r2 + 1
        L4b:
            int r5 = r5 + 1
            goto L9
        L4e:
            r5 = 2
            if (r1 != 0) goto L5e
            if (r2 != r8) goto L56
            if (r0 != r8) goto L56
            return r8
        L56:
            if (r2 != r5) goto L65
            if (r0 != r5) goto L65
            if (r3 == 0) goto L65
            r5 = 3
            return r5
        L5e:
            if (r1 != r8) goto L65
            if (r0 != r8) goto L65
            if (r2 != r8) goto L65
            return r5
        L65:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.fonts.SystemFonts.resolveVarFamilyType(android.text.FontConfig$FontFamily, java.lang.String):int");
    }

    private static void pushFamilyToFallback(FontConfig.FontFamily xmlFamily, ArrayMap<String, NativeFamilyListSet> fallbackMap, Map<String, ByteBuffer> cache) {
        String languageTags = xmlFamily.getLocaleList().toLanguageTags();
        int variant = xmlFamily.getVariant();
        ArrayList<FontConfig.Font> defaultFonts = new ArrayList<>();
        ArrayMap<String, ArrayList<FontConfig.Font>> specificFallbackFonts = new ArrayMap<>();
        for (FontConfig.Font font : xmlFamily.getFonts()) {
            String fallbackName = font.getFontFamilyName();
            if (fallbackName == null) {
                defaultFonts.add(font);
            } else {
                ArrayList<FontConfig.Font> fallback = specificFallbackFonts.get(fallbackName);
                if (fallback == null) {
                    fallback = new ArrayList<>();
                    specificFallbackFonts.put(fallbackName, fallback);
                }
                fallback.add(font);
            }
        }
        FontFamily defaultFamily = defaultFonts.isEmpty() ? null : createFontFamily(defaultFonts, languageTags, variant, resolveVarFamilyType(xmlFamily, null), false, cache);
        for (int i = 0; i < fallbackMap.size(); i++) {
            String name = fallbackMap.keyAt(i);
            NativeFamilyListSet familyListSet = fallbackMap.valueAt(i);
            int identityHash = System.identityHashCode(xmlFamily);
            if (familyListSet.seenXmlFamilies.get(identityHash, -1) == -1) {
                familyListSet.seenXmlFamilies.append(identityHash, 1);
                ArrayList<FontConfig.Font> fallback2 = specificFallbackFonts.get(name);
                if (fallback2 == null) {
                    if (defaultFamily != null) {
                        familyListSet.familyList.add(defaultFamily);
                    }
                } else {
                    FontFamily family = createFontFamily(fallback2, languageTags, variant, resolveVarFamilyType(xmlFamily, name), false, cache);
                    if (family != null) {
                        familyListSet.familyList.add(family);
                    } else if (defaultFamily != null) {
                        familyListSet.familyList.add(defaultFamily);
                    }
                }
            }
        }
    }

    private static FontFamily createFontFamily(List<FontConfig.Font> fonts, String languageTags, int variant, int varFamilyType, boolean isDefaultFallback, Map<String, ByteBuffer> cache) {
        if (fonts.size() == 0) {
            return null;
        }
        FontFamily.Builder b = null;
        for (int i = 0; i < fonts.size(); i++) {
            FontConfig.Font fontConfig = fonts.get(i);
            String fullPath = fontConfig.getFile().getAbsolutePath();
            ByteBuffer buffer = cache.get(fullPath);
            try {
                if (buffer == null) {
                    if (cache.containsKey(fullPath)) {
                        continue;
                    } else {
                        buffer = mmap(fullPath);
                        cache.put(fullPath, buffer);
                        if (buffer == null) {
                            continue;
                        }
                    }
                }
                Font font = new Font.Builder(buffer, new File(fullPath), languageTags).setWeight(fontConfig.getStyle().getWeight()).setSlant(fontConfig.getStyle().getSlant()).setTtcIndex(fontConfig.getTtcIndex()).setFontVariationSettings(fontConfig.getFontVariationSettings()).build();
                if (b == null) {
                    b = new FontFamily.Builder(font);
                } else {
                    b.addFont(font);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (b == null) {
            return null;
        }
        return b.build(languageTags, variant, false, isDefaultFallback, varFamilyType);
    }

    private static void appendNamedFamilyList(FontConfig.NamedFamilyList namedFamilyList, ArrayMap<String, ByteBuffer> bufferCache, ArrayMap<String, NativeFamilyListSet> fallbackListMap) {
        String familyName = namedFamilyList.getName();
        NativeFamilyListSet familyListSet = new NativeFamilyListSet();
        List<FontConfig.FontFamily> xmlFamilies = namedFamilyList.getFamilies();
        for (int i = 0; i < xmlFamilies.size(); i++) {
            FontConfig.FontFamily xmlFamily = xmlFamilies.get(i);
            FontFamily family = createFontFamily(xmlFamily.getFontList(), xmlFamily.getLocaleList().toLanguageTags(), xmlFamily.getVariant(), resolveVarFamilyType(xmlFamily, null), true, bufferCache);
            if (family == null) {
                return;
            }
            familyListSet.familyList.add(family);
            familyListSet.seenXmlFamilies.append(System.identityHashCode(xmlFamily), 1);
        }
        fallbackListMap.put(familyName, familyListSet);
    }

    public static FontConfig getSystemFontConfig(Map<String, File> updatableFontMap, long lastModifiedDate, int configVersion) {
        String fontsXml;
        if (Flags.newFontsFallbackXml()) {
            fontsXml = FONTS_XML;
        } else {
            fontsXml = LEGACY_FONTS_XML;
        }
        if (getSpecificSalesCode()) {
            return getSystemFontConfigInternal(FONTS_ADDITIONAL_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, updatableFontMap, lastModifiedDate, configVersion);
        }
        return getSystemFontConfigInternal(fontsXml, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, updatableFontMap, lastModifiedDate, configVersion);
    }

    public static FontConfig getSystemFontConfigForTesting(String fontsXml, Map<String, File> updatableFontMap, long lastModifiedDate, int configVersion) {
        return getSystemFontConfigInternal(fontsXml, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, updatableFontMap, lastModifiedDate, configVersion);
    }

    public static FontConfig getSystemPreinstalledFontConfig() {
        String fontsXml;
        if (Flags.newFontsFallbackXml()) {
            fontsXml = FONTS_XML;
        } else {
            fontsXml = LEGACY_FONTS_XML;
        }
        if (getSpecificSalesCode()) {
            return getSystemFontConfigInternal(FONTS_ADDITIONAL_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
        }
        return getSystemFontConfigInternal(fontsXml, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
    }

    public static FontConfig getSystemPreinstalledFontConfigFromLegacyXml() {
        return getSystemFontConfigInternal(LEGACY_FONTS_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
    }

    static FontConfig getSystemFontConfigInternal(String fontsXml, String systemFontDir, String oemXml, String productFontDir, Map<String, File> updatableFontMap, long lastModifiedDate, int configVersion) {
        try {
        } catch (IOException e) {
            e = e;
        } catch (XmlPullParserException e2) {
            e = e2;
        }
        try {
            Log.i(TAG, "Loading font config from " + fontsXml);
            return FontListParser.parse(fontsXml, systemFontDir, oemXml, productFontDir, updatableFontMap, lastModifiedDate, configVersion);
        } catch (IOException e3) {
            e = e3;
            Log.e(TAG, "Failed to open/read system font configurations.", e);
            return new FontConfig(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), 0L, 0);
        } catch (XmlPullParserException e4) {
            e = e4;
            Log.e(TAG, "Failed to parse the system font configuration.", e);
            return new FontConfig(Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), 0L, 0);
        }
    }

    public static Map<String, FontFamily[]> buildSystemFallback(FontConfig fontConfig) {
        return buildSystemFallback(fontConfig, new ArrayMap());
    }

    private static final class NativeFamilyListSet {
        public List<FontFamily> familyList;
        public SparseIntArray seenXmlFamilies;

        private NativeFamilyListSet() {
            this.familyList = new ArrayList();
            this.seenXmlFamilies = new SparseIntArray();
        }
    }

    public static Map<String, FontFamily[]> buildSystemFallback(FontConfig fontConfig, ArrayMap<String, ByteBuffer> outBufferCache) {
        ArrayMap<String, NativeFamilyListSet> fallbackListMap = new ArrayMap<>();
        List<FontConfig.Customization.LocaleFallback> localeFallbacks = fontConfig.getLocaleFallbackCustomizations();
        List<FontConfig.NamedFamilyList> namedFamilies = fontConfig.getNamedFamilyLists();
        for (int i = 0; i < namedFamilies.size(); i++) {
            FontConfig.NamedFamilyList namedFamilyList = namedFamilies.get(i);
            appendNamedFamilyList(namedFamilyList, outBufferCache, fallbackListMap);
        }
        List<FontConfig.Customization.LocaleFallback> customizations = new ArrayList<>();
        List<FontConfig.FontFamily> xmlFamilies = fontConfig.getFontFamilies();
        SparseIntArray seenCustomization = new SparseIntArray();
        for (int i2 = 0; i2 < xmlFamilies.size(); i2++) {
            FontConfig.FontFamily xmlFamily = xmlFamilies.get(i2);
            customizations.clear();
            for (int j = 0; j < localeFallbacks.size(); j++) {
                if (seenCustomization.get(j, -1) == -1) {
                    FontConfig.Customization.LocaleFallback localeFallback = localeFallbacks.get(j);
                    if (scriptMatch(xmlFamily.getLocaleList(), localeFallback.getScript())) {
                        customizations.add(localeFallback);
                        seenCustomization.put(j, 1);
                    }
                }
            }
            if (customizations.isEmpty()) {
                pushFamilyToFallback(xmlFamily, fallbackListMap, outBufferCache);
            } else {
                for (int j2 = 0; j2 < customizations.size(); j2++) {
                    FontConfig.Customization.LocaleFallback localeFallback2 = customizations.get(j2);
                    if (localeFallback2.getOperation() == 0) {
                        pushFamilyToFallback(localeFallback2.getFamily(), fallbackListMap, outBufferCache);
                    }
                }
                int j3 = 0;
                for (int j4 = 0; j4 < customizations.size(); j4++) {
                    FontConfig.Customization.LocaleFallback localeFallback3 = customizations.get(j4);
                    if (localeFallback3.getOperation() == 2) {
                        pushFamilyToFallback(localeFallback3.getFamily(), fallbackListMap, outBufferCache);
                        j3 = 1;
                    }
                }
                if (j3 == 0) {
                    pushFamilyToFallback(xmlFamily, fallbackListMap, outBufferCache);
                }
                for (int j5 = 0; j5 < customizations.size(); j5++) {
                    FontConfig.Customization.LocaleFallback localeFallback4 = customizations.get(j5);
                    if (localeFallback4.getOperation() == 1) {
                        pushFamilyToFallback(localeFallback4.getFamily(), fallbackListMap, outBufferCache);
                    }
                }
            }
        }
        Map<String, FontFamily[]> fallbackMap = new ArrayMap<>();
        for (int i3 = 0; i3 < fallbackListMap.size(); i3++) {
            String fallbackName = fallbackListMap.keyAt(i3);
            List<FontFamily> familyList = fallbackListMap.valueAt(i3).familyList;
            fallbackMap.put(fallbackName, (FontFamily[]) familyList.toArray(new FontFamily[0]));
        }
        return fallbackMap;
    }

    public static Map<String, Typeface> buildSystemTypefaces(FontConfig fontConfig, Map<String, FontFamily[]> fallbackMap) {
        ArrayMap<String, Typeface> result = new ArrayMap<>();
        Typeface.initSystemDefaultTypefaces(fallbackMap, fontConfig.getAliases(), result);
        return result;
    }

    private static boolean scriptMatch(LocaleList localeList, String targetScript) {
        if (localeList == null || localeList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < localeList.size(); i++) {
            Locale locale = localeList.get(i);
            if (locale != null) {
                String baseScript = FontConfig.resolveScript(locale);
                if (baseScript.equals(targetScript)) {
                    return true;
                }
                if (targetScript.equals("Bopo") && baseScript.equals("Hanb")) {
                    return true;
                }
                if (targetScript.equals("Hani")) {
                    if (baseScript.equals("Hanb") || baseScript.equals("Hans") || baseScript.equals("Hant") || baseScript.equals("Kore") || baseScript.equals("Jpan")) {
                        return true;
                    }
                } else if ((targetScript.equals("Hira") || targetScript.equals("Hrkt") || targetScript.equals("Kana")) && (baseScript.equals("Jpan") || baseScript.equals("Hrkt"))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean getSpecificSalesCode() {
        String sales_code = SystemProperties.get("ro.csc.sales_code");
        return sales_code.equals("MYM") || sales_code.equals("BKD") || sales_code.equals("BNG") || sales_code.equals("BCK");
    }
}
