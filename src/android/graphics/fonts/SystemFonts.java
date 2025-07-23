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
import com.android.internal.ravenwood.RavenwoodEnvironment;
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
    private static final String DEVICE_FONTS_XML_DIR = "/system/etc/";
    private static final String DEVICE_FONT_DIR = "/system/fonts/";
    private static final String FONTS_ADDITIONAL_XML = "/system/etc/fonts_additional.xml";
    public static final String OEM_FONT_DIR = "/product/fonts/";
    private static final String OEM_XML = "/product/etc/fonts_customization.xml";
    private static final String TAG = "SystemFonts";
    private static Set<Font> sAvailableFonts;
    private static final String FONTS_XML = getFontsXmlDir() + "font_fallback.xml";
    public static final String LEGACY_FONTS_XML = getFontsXmlDir() + "fonts.xml";
    public static final String SYSTEM_FONT_DIR = getSystemFontDir();
    private static final Object LOCK = new Object();

    private SystemFonts() {
    }

    private static String getFontsXmlDir() {
        return DEVICE_FONTS_XML_DIR;
    }

    private static String getFontsXmlDir$ravenwood() {
        return RavenwoodEnvironment.getInstance().getRavenwoodRuntimePath() + "fonts/";
    }

    private static String getSystemFontDir() {
        return DEVICE_FONT_DIR;
    }

    private static String getSystemFontDir$ravenwood() {
        return RavenwoodEnvironment.getInstance().getRavenwoodRuntimePath() + "fonts/";
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

    private static ByteBuffer mmap(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            try {
                FileChannel channel = fileInputStream.getChannel();
                MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
                fileInputStream.close();
                return map;
            } finally {
            }
        } catch (IOException unused) {
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
            java.util.List r10 = r10.getFontList()
            r0 = 0
            r1 = r0
            r2 = r1
            r3 = r2
            r4 = r3
            r5 = r4
        La:
            int r6 = r10.size()
            r7 = 1
            if (r1 >= r6) goto L4e
            java.lang.Object r6 = r10.get(r1)
            android.text.FontConfig$Font r6 = (android.text.FontConfig.Font) r6
            if (r11 != 0) goto L20
            java.lang.String r8 = r6.getFontFamilyName()
            if (r8 == 0) goto L2b
            goto L4b
        L20:
            java.lang.String r8 = r6.getFontFamilyName()
            boolean r8 = r11.equals(r8)
            if (r8 != 0) goto L2b
            goto L4b
        L2b:
            int r8 = r6.getVarTypeAxes()
            if (r8 != 0) goto L32
            return r0
        L32:
            r9 = r8 & 1
            if (r9 == 0) goto L38
            int r4 = r4 + 1
        L38:
            r8 = r8 & 2
            if (r8 == 0) goto L3e
            int r2 = r2 + 1
        L3e:
            android.graphics.fonts.FontStyle r6 = r6.getStyle()
            int r6 = r6.getSlant()
            if (r6 != r7) goto L49
            r5 = r7
        L49:
            int r3 = r3 + 1
        L4b:
            int r1 = r1 + 1
            goto La
        L4e:
            r10 = 2
            if (r2 != 0) goto L5e
            if (r3 != r7) goto L56
            if (r4 != r7) goto L56
            return r7
        L56:
            if (r3 != r10) goto L65
            if (r4 != r10) goto L65
            if (r5 == 0) goto L65
            r10 = 3
            return r10
        L5e:
            if (r2 != r7) goto L65
            if (r4 != r7) goto L65
            if (r3 != r7) goto L65
            return r10
        L65:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.graphics.fonts.SystemFonts.resolveVarFamilyType(android.text.FontConfig$FontFamily, java.lang.String):int");
    }

    private static void pushFamilyToFallback(FontConfig.FontFamily fontFamily, ArrayMap<String, NativeFamilyListSet> arrayMap, Map<String, ByteBuffer> map) {
        Map<String, ByteBuffer> map2;
        String languageTags = fontFamily.getLocaleList().toLanguageTags();
        int variant = fontFamily.getVariant();
        ArrayList arrayList = new ArrayList();
        ArrayMap arrayMap2 = new ArrayMap();
        for (FontConfig.Font font : fontFamily.getFonts()) {
            String fontFamilyName = font.getFontFamilyName();
            if (fontFamilyName == null) {
                arrayList.add(font);
            } else {
                ArrayList arrayList2 = (ArrayList) arrayMap2.get(fontFamilyName);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    arrayMap2.put(fontFamilyName, arrayList2);
                }
                arrayList2.add(font);
            }
        }
        FontFamily fontFamily2 = null;
        if (arrayList.isEmpty()) {
            map2 = map;
        } else {
            map2 = map;
            fontFamily2 = createFontFamily(arrayList, languageTags, variant, resolveVarFamilyType(fontFamily, null), false, map2);
        }
        FontFamily fontFamily3 = fontFamily2;
        for (int i = 0; i < arrayMap.size(); i++) {
            String keyAt = arrayMap.keyAt(i);
            NativeFamilyListSet valueAt = arrayMap.valueAt(i);
            int identityHashCode = System.identityHashCode(fontFamily);
            if (valueAt.seenXmlFamilies.get(identityHashCode, -1) == -1) {
                valueAt.seenXmlFamilies.append(identityHashCode, 1);
                ArrayList arrayList3 = (ArrayList) arrayMap2.get(keyAt);
                if (arrayList3 != null) {
                    FontFamily createFontFamily = createFontFamily(arrayList3, languageTags, variant, resolveVarFamilyType(fontFamily, keyAt), false, map2);
                    if (createFontFamily != null) {
                        valueAt.familyList.add(createFontFamily);
                    } else if (fontFamily3 != null) {
                        valueAt.familyList.add(fontFamily3);
                    }
                } else if (fontFamily3 != null) {
                    valueAt.familyList.add(fontFamily3);
                }
            }
        }
    }

    private static FontFamily createFontFamily(List<FontConfig.Font> list, String str, int i, int i2, boolean z, Map<String, ByteBuffer> map) {
        if (list.size() == 0) {
            return null;
        }
        FontFamily.Builder builder = null;
        for (int i3 = 0; i3 < list.size(); i3++) {
            FontConfig.Font font = list.get(i3);
            String absolutePath = font.getFile().getAbsolutePath();
            ByteBuffer byteBuffer = map.get(absolutePath);
            try {
                if (byteBuffer == null) {
                    if (map.containsKey(absolutePath)) {
                        continue;
                    } else {
                        byteBuffer = mmap(absolutePath);
                        map.put(absolutePath, byteBuffer);
                        if (byteBuffer == null) {
                            continue;
                        }
                    }
                }
                Font build = new Font.Builder(byteBuffer, new File(absolutePath), str).setWeight(font.getStyle().getWeight()).setSlant(font.getStyle().getSlant()).setTtcIndex(font.getTtcIndex()).setFontVariationSettings(font.getFontVariationSettings()).build();
                if (builder == null) {
                    builder = new FontFamily.Builder(build);
                } else {
                    builder.addFont(build);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (builder == null) {
            return null;
        }
        return builder.build(str, i, false, z, i2);
    }

    private static void appendNamedFamilyList(FontConfig.NamedFamilyList namedFamilyList, ArrayMap<String, ByteBuffer> arrayMap, ArrayMap<String, NativeFamilyListSet> arrayMap2) {
        String name = namedFamilyList.getName();
        NativeFamilyListSet nativeFamilyListSet = new NativeFamilyListSet();
        List<FontConfig.FontFamily> families = namedFamilyList.getFamilies();
        int i = 0;
        while (i < families.size()) {
            FontConfig.FontFamily fontFamily = families.get(i);
            ArrayMap<String, ByteBuffer> arrayMap3 = arrayMap;
            FontFamily createFontFamily = createFontFamily(fontFamily.getFontList(), fontFamily.getLocaleList().toLanguageTags(), fontFamily.getVariant(), resolveVarFamilyType(fontFamily, null), true, arrayMap3);
            if (createFontFamily == null) {
                return;
            }
            nativeFamilyListSet.familyList.add(createFontFamily);
            nativeFamilyListSet.seenXmlFamilies.append(System.identityHashCode(fontFamily), 1);
            i++;
            arrayMap = arrayMap3;
        }
        arrayMap2.put(name, nativeFamilyListSet);
    }

    public static FontConfig getSystemFontConfig(Map<String, File> map, long j, int i) {
        if (getSpecificSalesCode()) {
            return getSystemFontConfigInternal(FONTS_ADDITIONAL_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, map, j, i);
        }
        return getSystemFontConfigInternal(FONTS_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, map, j, i);
    }

    public static FontConfig getSystemFontConfigForTesting(String str, Map<String, File> map, long j, int i) {
        return getSystemFontConfigInternal(str, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, map, j, i);
    }

    public static FontConfig getSystemPreinstalledFontConfig() {
        if (getSpecificSalesCode()) {
            return getSystemFontConfigInternal(FONTS_ADDITIONAL_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
        }
        return getSystemFontConfigInternal(FONTS_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
    }

    public static FontConfig getSystemPreinstalledFontConfigFromLegacyXml() {
        return getSystemFontConfigInternal(LEGACY_FONTS_XML, SYSTEM_FONT_DIR, OEM_XML, OEM_FONT_DIR, null, 0L, 0);
    }

    static FontConfig getSystemFontConfigInternal(String str, String str2, String str3, String str4, Map<String, File> map, long j, int i) {
        try {
            Log.i(TAG, "Loading font config from " + str);
            return FontListParser.parse(str, str2, str3, str4, map, j, i);
        } catch (IOException e) {
            Log.e(TAG, "Failed to open/read system font configurations.", e);
            return new FontConfig(Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, 0L, 0);
        } catch (XmlPullParserException e2) {
            Log.e(TAG, "Failed to parse the system font configuration.", e2);
            return new FontConfig(Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST, 0L, 0);
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

    public static Map<String, FontFamily[]> buildSystemFallback(FontConfig fontConfig, ArrayMap<String, ByteBuffer> arrayMap) {
        ArrayMap arrayMap2 = new ArrayMap();
        List<FontConfig.Customization.LocaleFallback> localeFallbackCustomizations = fontConfig.getLocaleFallbackCustomizations();
        List<FontConfig.NamedFamilyList> namedFamilyLists = fontConfig.getNamedFamilyLists();
        for (int i = 0; i < namedFamilyLists.size(); i++) {
            appendNamedFamilyList(namedFamilyLists.get(i), arrayMap, arrayMap2);
        }
        ArrayList arrayList = new ArrayList();
        List<FontConfig.FontFamily> fontFamilies = fontConfig.getFontFamilies();
        SparseIntArray sparseIntArray = new SparseIntArray();
        for (int i2 = 0; i2 < fontFamilies.size(); i2++) {
            FontConfig.FontFamily fontFamily = fontFamilies.get(i2);
            arrayList.clear();
            for (int i3 = 0; i3 < localeFallbackCustomizations.size(); i3++) {
                if (sparseIntArray.get(i3, -1) == -1) {
                    FontConfig.Customization.LocaleFallback localeFallback = localeFallbackCustomizations.get(i3);
                    if (scriptMatch(fontFamily.getLocaleList(), localeFallback.getScript())) {
                        arrayList.add(localeFallback);
                        sparseIntArray.put(i3, 1);
                    }
                }
            }
            if (arrayList.isEmpty()) {
                pushFamilyToFallback(fontFamily, arrayMap2, arrayMap);
            } else {
                for (int i4 = 0; i4 < arrayList.size(); i4++) {
                    FontConfig.Customization.LocaleFallback localeFallback2 = (FontConfig.Customization.LocaleFallback) arrayList.get(i4);
                    if (localeFallback2.getOperation() == 0) {
                        pushFamilyToFallback(localeFallback2.getFamily(), arrayMap2, arrayMap);
                    }
                }
                boolean z = false;
                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                    FontConfig.Customization.LocaleFallback localeFallback3 = (FontConfig.Customization.LocaleFallback) arrayList.get(i5);
                    if (localeFallback3.getOperation() == 2) {
                        pushFamilyToFallback(localeFallback3.getFamily(), arrayMap2, arrayMap);
                        z = true;
                    }
                }
                if (!z) {
                    pushFamilyToFallback(fontFamily, arrayMap2, arrayMap);
                }
                for (int i6 = 0; i6 < arrayList.size(); i6++) {
                    FontConfig.Customization.LocaleFallback localeFallback4 = (FontConfig.Customization.LocaleFallback) arrayList.get(i6);
                    if (localeFallback4.getOperation() == 1) {
                        pushFamilyToFallback(localeFallback4.getFamily(), arrayMap2, arrayMap);
                    }
                }
            }
        }
        ArrayMap arrayMap3 = new ArrayMap();
        for (int i7 = 0; i7 < arrayMap2.size(); i7++) {
            arrayMap3.put((String) arrayMap2.keyAt(i7), (FontFamily[]) ((NativeFamilyListSet) arrayMap2.valueAt(i7)).familyList.toArray(new FontFamily[0]));
        }
        return arrayMap3;
    }

    public static Map<String, Typeface> buildSystemTypefaces(FontConfig fontConfig, Map<String, FontFamily[]> map) {
        ArrayMap arrayMap = new ArrayMap();
        Typeface.initSystemDefaultTypefaces(map, fontConfig.getAliases(), arrayMap);
        return arrayMap;
    }

    private static boolean scriptMatch(LocaleList localeList, String str) {
        if (localeList != null && !localeList.isEmpty()) {
            for (int i = 0; i < localeList.size(); i++) {
                Locale locale = localeList.get(i);
                if (locale != null) {
                    String resolveScript = FontConfig.resolveScript(locale);
                    if (resolveScript.equals(str)) {
                        return true;
                    }
                    if (str.equals("Bopo") && resolveScript.equals("Hanb")) {
                        return true;
                    }
                    if (str.equals("Hani")) {
                        if (resolveScript.equals("Hanb") || resolveScript.equals("Hans") || resolveScript.equals("Hant") || resolveScript.equals("Kore") || resolveScript.equals("Jpan")) {
                            return true;
                        }
                    } else if ((str.equals("Hira") || str.equals("Hrkt") || str.equals("Kana")) && (resolveScript.equals("Jpan") || resolveScript.equals("Hrkt"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean getSpecificSalesCode() {
        String str = SystemProperties.get("ro.csc.sales_code");
        return str.equals("MYM") || str.equals("BKD") || str.equals("BNG") || str.equals("BCK");
    }
}
