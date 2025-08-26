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

    private static ByteBuffer mmap(String str) throws IOException {
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int resolveVarFamilyType(FontConfig.FontFamily fontFamily, String str) {
        List<FontConfig.Font> fontList = fontFamily.getFontList();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        for (int i4 = 0; i4 < fontList.size(); i4++) {
            FontConfig.Font font = fontList.get(i4);
            if (str == null) {
                if (font.getFontFamilyName() != null) {
                    continue;
                } else {
                    int varTypeAxes = font.getVarTypeAxes();
                    if (varTypeAxes == 0) {
                        return 0;
                    }
                    if ((varTypeAxes & 1) != 0) {
                        i3++;
                    }
                    if ((varTypeAxes & 2) != 0) {
                        i++;
                    }
                    if (font.getStyle().getSlant() == 1) {
                        z = true;
                    }
                    i2++;
                }
            } else if (!str.equals(font.getFontFamilyName())) {
                continue;
            }
        }
        if (i == 0) {
            if (i2 == 1 && i3 == 1) {
                return 1;
            }
            if (i2 == 2 && i3 == 2 && z) {
                return 3;
            }
        } else if (i == 1 && i3 == 1 && i2 == 1) {
            return 2;
        }
        return 0;
    }

    private static void pushFamilyToFallback(FontConfig.FontFamily fontFamily, ArrayMap<String, NativeFamilyListSet> arrayMap, Map<String, ByteBuffer> map) throws IOException {
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
        FontFamily fontFamilyCreateFontFamily = null;
        if (arrayList.isEmpty()) {
            map2 = map;
        } else {
            map2 = map;
            fontFamilyCreateFontFamily = createFontFamily(arrayList, languageTags, variant, resolveVarFamilyType(fontFamily, null), false, map2);
        }
        FontFamily fontFamily2 = fontFamilyCreateFontFamily;
        for (int i = 0; i < arrayMap.size(); i++) {
            String strKeyAt = arrayMap.keyAt(i);
            NativeFamilyListSet nativeFamilyListSetValueAt = arrayMap.valueAt(i);
            int iIdentityHashCode = System.identityHashCode(fontFamily);
            if (nativeFamilyListSetValueAt.seenXmlFamilies.get(iIdentityHashCode, -1) == -1) {
                nativeFamilyListSetValueAt.seenXmlFamilies.append(iIdentityHashCode, 1);
                ArrayList arrayList3 = (ArrayList) arrayMap2.get(strKeyAt);
                if (arrayList3 != null) {
                    FontFamily fontFamilyCreateFontFamily2 = createFontFamily(arrayList3, languageTags, variant, resolveVarFamilyType(fontFamily, strKeyAt), false, map2);
                    if (fontFamilyCreateFontFamily2 != null) {
                        nativeFamilyListSetValueAt.familyList.add(fontFamilyCreateFontFamily2);
                    } else if (fontFamily2 != null) {
                        nativeFamilyListSetValueAt.familyList.add(fontFamily2);
                    }
                } else if (fontFamily2 != null) {
                    nativeFamilyListSetValueAt.familyList.add(fontFamily2);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0038 A[EXC_TOP_SPLITTER, PHI: r5
      0x0038: PHI (r5v2 java.nio.ByteBuffer) = (r5v1 java.nio.ByteBuffer), (r5v7 java.nio.ByteBuffer) binds: [B:9:0x0025, B:14:0x0035] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static FontFamily createFontFamily(List<FontConfig.Font> list, String str, int i, int i2, boolean z, Map<String, ByteBuffer> map) throws IOException {
        if (list.size() == 0) {
            return null;
        }
        FontFamily.Builder builder = null;
        for (int i3 = 0; i3 < list.size(); i3++) {
            FontConfig.Font font = list.get(i3);
            String absolutePath = font.getFile().getAbsolutePath();
            ByteBuffer byteBufferMmap = map.get(absolutePath);
            if (byteBufferMmap == null) {
                if (map.containsKey(absolutePath)) {
                    continue;
                } else {
                    byteBufferMmap = mmap(absolutePath);
                    map.put(absolutePath, byteBufferMmap);
                    if (byteBufferMmap == null) {
                        continue;
                    }
                }
            } else {
                try {
                    Font fontBuild = new Font.Builder(byteBufferMmap, new File(absolutePath), str).setWeight(font.getStyle().getWeight()).setSlant(font.getStyle().getSlant()).setTtcIndex(font.getTtcIndex()).setFontVariationSettings(font.getFontVariationSettings()).build();
                    if (builder == null) {
                        builder = new FontFamily.Builder(fontBuild);
                    } else {
                        builder.addFont(fontBuild);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (builder == null) {
            return null;
        }
        return builder.build(str, i, false, z, i2);
    }

    private static void appendNamedFamilyList(FontConfig.NamedFamilyList namedFamilyList, ArrayMap<String, ByteBuffer> arrayMap, ArrayMap<String, NativeFamilyListSet> arrayMap2) throws IOException {
        String name = namedFamilyList.getName();
        NativeFamilyListSet nativeFamilyListSet = new NativeFamilyListSet();
        List<FontConfig.FontFamily> families = namedFamilyList.getFamilies();
        int i = 0;
        while (i < families.size()) {
            FontConfig.FontFamily fontFamily = families.get(i);
            ArrayMap<String, ByteBuffer> arrayMap3 = arrayMap;
            FontFamily fontFamilyCreateFontFamily = createFontFamily(fontFamily.getFontList(), fontFamily.getLocaleList().toLanguageTags(), fontFamily.getVariant(), resolveVarFamilyType(fontFamily, null), true, arrayMap3);
            if (fontFamilyCreateFontFamily == null) {
                return;
            }
            nativeFamilyListSet.familyList.add(fontFamilyCreateFontFamily);
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

    public static Map<String, FontFamily[]> buildSystemFallback(FontConfig fontConfig, ArrayMap<String, ByteBuffer> arrayMap) throws IOException {
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
                    String strResolveScript = FontConfig.resolveScript(locale);
                    if (strResolveScript.equals(str)) {
                        return true;
                    }
                    if (str.equals("Bopo") && strResolveScript.equals("Hanb")) {
                        return true;
                    }
                    if (str.equals("Hani")) {
                        if (strResolveScript.equals("Hanb") || strResolveScript.equals("Hans") || strResolveScript.equals("Hant") || strResolveScript.equals("Kore") || strResolveScript.equals("Jpan")) {
                            return true;
                        }
                    } else if ((str.equals("Hira") || str.equals("Hrkt") || str.equals("Kana")) && (strResolveScript.equals("Jpan") || strResolveScript.equals("Hrkt"))) {
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
