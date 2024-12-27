package android.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.FontResourcesParser;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontStyle;
import android.graphics.fonts.FontVariationAxis;
import android.graphics.fonts.SystemFonts;
import android.icu.util.ULocale;
import android.os.ParcelFileDescriptor;
import android.os.SharedMemory;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.FontRequest;
import android.provider.FontsContract;
import android.system.ErrnoException;
import android.system.OsConstants;
import android.text.FontConfig;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.LruCache;
import android.util.Pair;
import android.util.SparseArray;

import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.Preconditions;

import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;

import libcore.util.NativeAllocationRegistry;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Typeface {
    public static final int BOLD = 1;
    public static final int BOLD_ITALIC = 3;
    private static final String DROIDSANS = "DroidSans.ttf";
    private static final String DROIDSANS_BOLD = "DroidSans-Bold.ttf";
    public static final boolean ENABLE_LAZY_TYPEFACE_INITIALIZATION = true;
    private static final int FONT_WEIGHT_BOLD = 700;
    private static final int FONT_WEIGHT_NORMAL = 400;
    public static final int ITALIC = 2;
    public static final int NORMAL = 0;
    private static final String OWNER_SANS_LOC_PATH = "/data/app_fonts/0/sans.loc";
    public static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String SANS_LOC_POST = "/sans.loc";
    private static final String SANS_LOC_PRE = "/data/app_fonts/";
    private static final int STYLE_ITALIC = 1;
    public static final int STYLE_MASK = 3;
    private static final int STYLE_NORMAL = 0;
    private static final String TAG_MONOTYPE = "Monotype";
    private static Typeface sDefaultFlipfont;
    static Typeface sDefaultTypeface;
    static Typeface[] sDefaults;
    private boolean isBoldFont;
    public boolean isLikeDefault;
    private final Runnable mCleaner;
    private int mStyle;
    private int[] mSupportedAxes;
    private final String mSystemFontFamilyName;
    private final int mWeight;
    public long native_instance;
    private static String TAG = "Typeface";
    private static final NativeAllocationRegistry sRegistry =
            NativeAllocationRegistry.createMalloced(
                    Typeface.class.getClassLoader(), nativeGetReleaseFunc());
    public static final Typeface DEFAULT = null;
    public static final Typeface DEFAULT_BOLD = null;
    public static final Typeface SANS_SERIF = null;
    public static final Typeface SERIF = null;
    public static final Typeface MONOSPACE = null;
    private static final LongSparseArray<SparseArray<Typeface>> sStyledTypefaceCache =
            new LongSparseArray<>(3);
    private static final Object sStyledCacheLock = new Object();
    private static final LongSparseArray<SparseArray<Typeface>> sWeightTypefaceCache =
            new LongSparseArray<>(3);
    private static final Object sWeightCacheLock = new Object();
    private static final LruCache<String, Typeface> sDynamicTypefaceCache = new LruCache<>(16);
    private static final Object sDynamicCacheLock = new Object();
    private static final Hashtable<String, Typeface> fontCache = new Hashtable<>();
    static final Map<String, Typeface> sSystemFontMap = new ArrayMap();
    static ByteBuffer sSystemFontMapBuffer = null;
    static SharedMemory sSystemFontMapSharedMemory = null;
    private static final Object SYSTEM_FONT_MAP_LOCK = new Object();

    @Deprecated static final Map<String, FontFamily[]> sSystemFallbackMap = Collections.emptyMap();
    private static final int[] EMPTY_AXES = new int[0];
    private static String FlipFontPath = "";
    private static boolean isMtFontsDirectoryExists = false;
    public static boolean isFlipFontUsed = false;
    private static final ArrayList<String> FontsLikeBold =
            new ArrayList<>(
                    Arrays.asList(
                            "sans-serif-medium", "sans-serif-black", "sec-semibold", "sec-bold"));
    public static final String DEFAULT_FAMILY = "sans-serif";
    private static final ArrayList<String> FontsLikeDefault =
            new ArrayList<>(
                    Arrays.asList(
                            "sec-400",
                            "sans-serif-thin",
                            "sans-serif-light",
                            DEFAULT_FAMILY,
                            "sans-serif-condensed",
                            "sans-serif-medium",
                            "sans-serif-black",
                            "monospace",
                            "sec",
                            "sec-num",
                            "sec-num-fixed"));

    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {}

    @CriticalNative
    private static native void nativeAddFontCollections(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateFromArray(long[] jArr, long j, int i, int i2);

    private static native long nativeCreateFromTypeface(long j, int i);

    private static native long nativeCreateFromTypefaceWithExactStyle(long j, int i, boolean z);

    private static native long nativeCreateFromTypefaceWithVariation(
            long j, List<FontVariationAxis> list);

    private static native long nativeCreateWeightAlias(long j, int i);

    private static native void nativeForceSetStaticFinalField(String str, Typeface typeface);

    @CriticalNative
    private static native long nativeGetReleaseFunc();

    @CriticalNative
    private static native int nativeGetStyle(long j);

    private static native int[] nativeGetSupportedAxes(long j);

    @CriticalNative
    private static native int nativeGetWeight(long j);

    private static native long[] nativeReadTypefaces(ByteBuffer byteBuffer, int i);

    private static native void nativeRegisterGenericFamily(String str, long j);

    @FastNative
    private static native void nativeRegisterLocaleList(String str);

    @CriticalNative
    private static native void nativeSetDefault(long j);

    private static native void nativeWarmUpCache(String str);

    private static native int nativeWriteTypefaces(ByteBuffer byteBuffer, int i, long[] jArr);

    static {
        preloadFontFile("/system/fonts/Roboto-Regular.ttf");
        preloadFontFile("/system/fonts/RobotoStatic-Regular.ttf");
        String locale = SystemProperties.get("persist.sys.locale", "en-US");
        String script = ULocale.addLikelySubtags(ULocale.forLanguageTag(locale)).getScript();
        FontConfig config = SystemFonts.getSystemPreinstalledFontConfigFromLegacyXml();
        for (int i = 0; i < config.getFontFamilies().size(); i++) {
            FontConfig.FontFamily family = config.getFontFamilies().get(i);
            if (!family.getLocaleList().isEmpty()) {
                nativeRegisterLocaleList(family.getLocaleList().toLanguageTags());
            }
            boolean loadFamily = false;
            for (int j = 0; j < family.getLocaleList().size(); j++) {
                String fontScript =
                        ULocale.addLikelySubtags(ULocale.forLocale(family.getLocaleList().get(j)))
                                .getScript();
                loadFamily = fontScript.equals(script);
                if (loadFamily) {
                    break;
                }
            }
            if (loadFamily) {
                for (int j2 = 0; j2 < family.getFontList().size(); j2++) {
                    preloadFontFile(family.getFontList().get(j2).getFile().getAbsolutePath());
                }
            }
        }
    }

    public static SharedMemory getSystemFontMapSharedMemory() {
        Objects.requireNonNull(sSystemFontMapSharedMemory);
        return sSystemFontMapSharedMemory;
    }

    private static void setDefault(Typeface t) {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            sDefaultFlipfont = new Typeface(nativeCreateFromTypeface(t.native_instance, t.mStyle));
            sDefaultTypeface = t;
            nativeSetDefault(t.native_instance);
        }
    }

    private static Typeface getDefault() {
        Typeface typeface;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            typeface = sDefaultTypeface;
        }
        return typeface;
    }

    public int getWeight() {
        return this.mWeight;
    }

    public int getStyle() {
        return this.mStyle;
    }

    public final boolean isBold() {
        return (this.mStyle & 1) != 0;
    }

    public final boolean isItalic() {
        return (this.mStyle & 2) != 0;
    }

    public final String getSystemFontFamilyName() {
        return this.mSystemFontFamilyName;
    }

    private static boolean hasFontFamily(String familyName) {
        boolean containsKey;
        Objects.requireNonNull(familyName, "familyName cannot be null");
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            containsKey = sSystemFontMap.containsKey(familyName);
        }
        return containsKey;
    }

    public static Typeface createFromResources(
            FontResourcesParser.FamilyResourceEntry entry, AssetManager mgr, String path) {
        Typeface typeface;
        if (entry instanceof FontResourcesParser.ProviderResourceEntry) {
            FontResourcesParser.ProviderResourceEntry providerEntry =
                    (FontResourcesParser.ProviderResourceEntry) entry;
            String systemFontFamilyName = providerEntry.getSystemFontFamilyName();
            if (systemFontFamilyName != null && hasFontFamily(systemFontFamilyName)) {
                return create(systemFontFamilyName, 0);
            }
            List<List<String>> givenCerts = providerEntry.getCerts();
            List<List<byte[]>> certs = new ArrayList<>();
            if (givenCerts != null) {
                for (int i = 0; i < givenCerts.size(); i++) {
                    List<String> certSet = givenCerts.get(i);
                    List<byte[]> byteArraySet = new ArrayList<>();
                    for (int j = 0; j < certSet.size(); j++) {
                        byteArraySet.add(Base64.decode(certSet.get(j), 0));
                    }
                    certs.add(byteArraySet);
                }
            }
            FontRequest request =
                    new FontRequest(
                            providerEntry.getAuthority(),
                            providerEntry.getPackage(),
                            providerEntry.getQuery(),
                            certs);
            Typeface typeface2 = FontsContract.getFontSync(request);
            return typeface2 == null ? DEFAULT : typeface2;
        }
        Typeface typeface3 = findFromCache(mgr, path);
        if (typeface3 != null) {
            return typeface3;
        }
        FontResourcesParser.FontFamilyFilesResourceEntry filesEntry =
                (FontResourcesParser.FontFamilyFilesResourceEntry) entry;
        FontFamily.Builder familyBuilder = null;
        try {
            for (FontResourcesParser.FontFileResourceEntry fontFile : filesEntry.getEntries()) {
                Font.Builder fontBuilder =
                        new Font.Builder(mgr, fontFile.getFileName(), false, -1)
                                .setTtcIndex(fontFile.getTtcIndex())
                                .setFontVariationSettings(fontFile.getVariationSettings());
                if (fontFile.getWeight() != -1) {
                    fontBuilder.setWeight(fontFile.getWeight());
                }
                if (fontFile.getItalic() != -1) {
                    int i2 = 1;
                    if (fontFile.getItalic() != 1) {
                        i2 = 0;
                    }
                    fontBuilder.setSlant(i2);
                }
                if (familyBuilder == null) {
                    familyBuilder = new FontFamily.Builder(fontBuilder.build());
                } else {
                    familyBuilder.addFont(fontBuilder.build());
                }
            }
        } catch (IOException e) {
            typeface = DEFAULT;
        } catch (IllegalArgumentException e2) {
            return null;
        }
        if (familyBuilder == null) {
            return DEFAULT;
        }
        android.graphics.fonts.FontFamily family = familyBuilder.build();
        FontStyle normal = new FontStyle(400, 0);
        Font bestFont = family.getFont(0);
        int bestScore = normal.getMatchScore(bestFont.getStyle());
        for (int i3 = 1; i3 < family.getSize(); i3++) {
            Font candidate = family.getFont(i3);
            int score = normal.getMatchScore(candidate.getStyle());
            if (score < bestScore) {
                bestFont = candidate;
                bestScore = score;
            }
        }
        typeface = new CustomFallbackBuilder(family).setStyle(bestFont.getStyle()).build();
        synchronized (sDynamicCacheLock) {
            String key = Builder.createAssetUid(mgr, path, 0, null, -1, -1, DEFAULT_FAMILY);
            sDynamicTypefaceCache.put(key, typeface);
        }
        return typeface;
    }

    public static Typeface findFromCache(AssetManager mgr, String path) {
        synchronized (sDynamicCacheLock) {
            String key = Builder.createAssetUid(mgr, path, 0, null, -1, -1, DEFAULT_FAMILY);
            Typeface typeface = sDynamicTypefaceCache.get(key);
            if (typeface != null) {
                return typeface;
            }
            return null;
        }
    }

    public static final class Builder {
        public static final int BOLD_WEIGHT = 700;
        public static final int NORMAL_WEIGHT = 400;
        private final AssetManager mAssetManager;
        private String mFallbackFamilyName;
        private final Font.Builder mFontBuilder;
        private int mItalic;
        private final String mPath;
        private int mWeight;

        public Builder(File path) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new Font.Builder(path);
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(FileDescriptor fd) {
            Font.Builder builder;
            this.mWeight = -1;
            this.mItalic = -1;
            try {
                builder = new Font.Builder(ParcelFileDescriptor.dup(fd));
            } catch (IOException e) {
                builder = null;
            }
            this.mFontBuilder = builder;
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(String path) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new Font.Builder(new File(path));
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(AssetManager assetManager, String path) {
            this(assetManager, path, true, 0);
        }

        public Builder(AssetManager assetManager, String path, boolean isAsset, int cookie) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new Font.Builder(assetManager, path, isAsset, cookie);
            this.mAssetManager = assetManager;
            this.mPath = path;
        }

        public Builder setWeight(int weight) {
            this.mWeight = weight;
            this.mFontBuilder.setWeight(weight);
            return this;
        }

        public Builder setItalic(boolean z) {
            this.mItalic = z ? 1 : 0;
            this.mFontBuilder.setSlant(this.mItalic);
            return this;
        }

        public Builder setTtcIndex(int ttcIndex) {
            this.mFontBuilder.setTtcIndex(ttcIndex);
            return this;
        }

        public Builder setFontVariationSettings(String variationSettings) {
            this.mFontBuilder.setFontVariationSettings(variationSettings);
            return this;
        }

        public Builder setFontVariationSettings(FontVariationAxis[] axes) {
            this.mFontBuilder.setFontVariationSettings(axes);
            return this;
        }

        public Builder setFallback(String familyName) {
            this.mFallbackFamilyName = familyName;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String createAssetUid(
                AssetManager mgr,
                String path,
                int ttcIndex,
                FontVariationAxis[] axes,
                int weight,
                int italic,
                String fallback) {
            SparseArray<String> pkgs = mgr.getAssignedPackageIdentifiers();
            StringBuilder builder = new StringBuilder();
            int size = pkgs.size();
            for (int i = 0; i < size; i++) {
                builder.append(pkgs.valueAt(i));
                builder.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            builder.append(path);
            builder.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            builder.append(Integer.toString(ttcIndex));
            builder.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            builder.append(Integer.toString(weight));
            builder.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            builder.append(Integer.toString(italic));
            builder.append("--");
            builder.append(fallback);
            builder.append("--");
            if (axes != null) {
                for (FontVariationAxis axis : axes) {
                    builder.append(axis.getTag());
                    builder.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    builder.append(Float.toString(axis.getStyleValue()));
                }
            }
            return builder.toString();
        }

        private Typeface resolveFallbackTypeface() {
            if (this.mFallbackFamilyName == null) {
                return null;
            }
            Typeface base = Typeface.getSystemDefaultTypeface(this.mFallbackFamilyName);
            if (this.mWeight == -1 && this.mItalic == -1) {
                return base;
            }
            int weight = this.mWeight == -1 ? base.mWeight : this.mWeight;
            boolean z = false;
            if (this.mItalic != -1 ? this.mItalic == 1 : (base.mStyle & 2) != 0) {
                z = true;
            }
            boolean italic = z;
            return Typeface.createWeightStyle(base, weight, italic);
        }

        public Typeface build() {
            String key;
            if (this.mFontBuilder == null) {
                return resolveFallbackTypeface();
            }
            try {
                Font font = this.mFontBuilder.build();
                if (this.mAssetManager == null) {
                    key = null;
                } else {
                    key =
                            createAssetUid(
                                    this.mAssetManager,
                                    this.mPath,
                                    font.getTtcIndex(),
                                    font.getAxes(),
                                    this.mWeight,
                                    this.mItalic,
                                    this.mFallbackFamilyName == null
                                            ? Typeface.DEFAULT_FAMILY
                                            : this.mFallbackFamilyName);
                }
                if (key != null) {
                    synchronized (Typeface.sDynamicCacheLock) {
                        Typeface typeface = (Typeface) Typeface.sDynamicTypefaceCache.get(key);
                        if (typeface != null) {
                            return typeface;
                        }
                    }
                }
                android.graphics.fonts.FontFamily family = new FontFamily.Builder(font).build();
                int weight = this.mWeight == -1 ? font.getStyle().getWeight() : this.mWeight;
                int slant = this.mItalic == -1 ? font.getStyle().getSlant() : this.mItalic;
                CustomFallbackBuilder builder =
                        new CustomFallbackBuilder(family).setStyle(new FontStyle(weight, slant));
                if (this.mFallbackFamilyName != null) {
                    builder.setSystemFallback(this.mFallbackFamilyName);
                }
                Typeface typeface2 = builder.build();
                if (key != null) {
                    synchronized (Typeface.sDynamicCacheLock) {
                        Typeface.sDynamicTypefaceCache.put(key, typeface2);
                    }
                }
                return typeface2;
            } catch (IOException | IllegalArgumentException e) {
                return resolveFallbackTypeface();
            }
        }
    }

    public static final class CustomFallbackBuilder {
        private static final int MAX_CUSTOM_FALLBACK = 64;
        private FontStyle mStyle;
        private final ArrayList<android.graphics.fonts.FontFamily> mFamilies = new ArrayList<>();
        private String mFallbackName = null;

        public static int getMaxCustomFallbackCount() {
            return 64;
        }

        public CustomFallbackBuilder(android.graphics.fonts.FontFamily family) {
            Preconditions.checkNotNull(family);
            this.mFamilies.add(family);
        }

        public CustomFallbackBuilder setSystemFallback(String familyName) {
            Preconditions.checkNotNull(familyName);
            this.mFallbackName = familyName;
            return this;
        }

        public CustomFallbackBuilder setStyle(FontStyle style) {
            this.mStyle = style;
            return this;
        }

        public CustomFallbackBuilder addCustomFallback(android.graphics.fonts.FontFamily family) {
            Preconditions.checkNotNull(family);
            Preconditions.checkArgument(
                    this.mFamilies.size() < getMaxCustomFallbackCount(),
                    "Custom fallback limit exceeded(%d)",
                    Integer.valueOf(getMaxCustomFallbackCount()));
            this.mFamilies.add(family);
            return this;
        }

        public Typeface build() {
            int size = this.mFamilies.size();
            Typeface systemDefaultTypeface = Typeface.getSystemDefaultTypeface(this.mFallbackName);
            long[] jArr = new long[size];
            for (int i = 0; i < size; i++) {
                jArr[i] = this.mFamilies.get(i).getNativePtr();
            }
            return new Typeface(
                    Typeface.nativeCreateFromArray(
                            jArr,
                            systemDefaultTypeface.native_instance,
                            this.mStyle == null ? 400 : this.mStyle.getWeight(),
                            (this.mStyle == null || this.mStyle.getSlant() == 0) ? 0 : 1),
                    null);
        }
    }

    public static Typeface create(String familyName, int style) {
        if (isFlipFontUsed && FontsLikeDefault.contains(familyName)) {
            if (FontsLikeBold.contains(familyName)) {
                style = 1;
            }
            return defaultFromStyle(style);
        }
        return create(getSystemDefaultTypeface(familyName), style);
    }

    public static Typeface create(Typeface family, int style) {
        if ((style & (-4)) != 0) {
            style = 0;
        }
        if (family == null) {
            family = getDefault();
        }
        if (isFlipFontUsed && family.isLikeDefault) {
            return defaultFromStyle(family.isBoldFont ? 1 : 0);
        }
        if (family.mStyle == style) {
            return family;
        }
        long ni = family.native_instance;
        synchronized (sStyledCacheLock) {
            SparseArray<Typeface> styles = sStyledTypefaceCache.get(ni);
            if (styles == null) {
                styles = new SparseArray<>(4);
                sStyledTypefaceCache.put(ni, styles);
            } else {
                Typeface typeface = styles.get(style);
                if (typeface != null) {
                    return typeface;
                }
            }
            Typeface typeface2 =
                    new Typeface(
                            nativeCreateFromTypeface(ni, style), family.getSystemFontFamilyName());
            if (family != null && family.mStyle == style) {
                typeface2.isLikeDefault = family.isLikeDefault;
                typeface2.isBoldFont = family.isBoldFont;
            }
            styles.put(style, typeface2);
            return typeface2;
        }
    }

    public static Typeface create(Typeface family, int weight, boolean italic) {
        Preconditions.checkArgumentInRange(weight, 0, 1000, "weight");
        if (family == null) {
            family = getDefault();
        }
        return createWeightStyle(family, weight, italic);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Typeface createWeightStyle(Typeface typeface, int i, boolean z) {
        int i2 = (i << 1) | (z ? 1 : 0);
        if (isFlipFontUsed && typeface.isLikeDefault) {
            return defaultFromStyle(i <= 500 ? 0 : 1);
        }
        synchronized (sWeightCacheLock) {
            SparseArray<Typeface> sparseArray = sWeightTypefaceCache.get(typeface.native_instance);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>(4);
                sWeightTypefaceCache.put(typeface.native_instance, sparseArray);
            } else {
                Typeface typeface2 = sparseArray.get(i2);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            Typeface typeface3 =
                    new Typeface(
                            nativeCreateFromTypefaceWithExactStyle(typeface.native_instance, i, z),
                            typeface.getSystemFontFamilyName());
            if (typeface != null) {
                typeface3.isLikeDefault = typeface.isLikeDefault;
                typeface3.isBoldFont = typeface.isBoldFont;
            }
            sparseArray.put(i2, typeface3);
            return typeface3;
        }
    }

    public static Typeface createFromTypefaceWithVariation(
            Typeface family, List<FontVariationAxis> axes) {
        Typeface base = family == null ? DEFAULT : family;
        Typeface typeface =
                new Typeface(
                        nativeCreateFromTypefaceWithVariation(base.native_instance, axes),
                        base.getSystemFontFamilyName());
        return typeface;
    }

    public static Typeface defaultFromStyle(int style) {
        Typeface typeface;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            if (style < 0 || style > 3) {
                style = 0;
            }
            typeface = sDefaults[style];
        }
        return typeface;
    }

    public static Typeface createFromAsset(AssetManager mgr, String path) {
        Preconditions.checkNotNull(path);
        Preconditions.checkNotNull(mgr);
        Typeface typeface = new Builder(mgr, path).build();
        if (typeface != null) {
            return typeface;
        }
        try {
            InputStream inputStream = mgr.open(path);
            if (inputStream != null) {
                inputStream.close();
            }
            return DEFAULT;
        } catch (IOException e) {
            throw new RuntimeException("Font asset not found " + path);
        }
    }

    private static String createProviderUid(String authority, String query) {
        return "provider:" + authority + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + query;
    }

    public static Typeface createFromFile(File file) {
        Typeface typeface = new Builder(file).build();
        if (typeface != null) {
            return typeface;
        }
        if (!file.exists()) {
            throw new RuntimeException("Font asset not found " + file.getAbsolutePath());
        }
        return DEFAULT;
    }

    public static Typeface createFromFile(String path) {
        Preconditions.checkNotNull(path);
        return createFromFile(new File(path));
    }

    @Deprecated
    private static Typeface createFromFamilies(FontFamily[] families) {
        long[] ptrArray = new long[families.length];
        for (int i = 0; i < families.length; i++) {
            ptrArray[i] = families[i].mNativePtr;
        }
        return new Typeface(nativeCreateFromArray(ptrArray, 0L, -1, -1), null);
    }

    private static Typeface createFromFamilies(
            String familyName, android.graphics.fonts.FontFamily[] families) {
        long[] ptrArray = new long[families.length];
        for (int i = 0; i < families.length; i++) {
            ptrArray[i] = families[i].getNativePtr();
        }
        return new Typeface(nativeCreateFromArray(ptrArray, 0L, -1, -1), familyName);
    }

    @Deprecated
    private static Typeface createFromFamiliesWithDefault(
            FontFamily[] families, int weight, int italic) {
        return createFromFamiliesWithDefault(families, DEFAULT_FAMILY, weight, italic);
    }

    @Deprecated
    private static Typeface createFromFamiliesWithDefault(
            FontFamily[] families, String fallbackName, int weight, int italic) {
        Typeface fallbackTypeface = getSystemDefaultTypeface(fallbackName);
        long[] ptrArray = new long[families.length];
        for (int i = 0; i < families.length; i++) {
            ptrArray[i] = families[i].mNativePtr;
        }
        return new Typeface(
                nativeCreateFromArray(ptrArray, fallbackTypeface.native_instance, weight, italic),
                null);
    }

    private Typeface(long ni) {
        this(ni, null);
    }

    private Typeface(long ni, String systemFontFamilyName) {
        this.isLikeDefault = false;
        this.isBoldFont = false;
        if (ni == 0) {
            throw new RuntimeException("native typeface cannot be made");
        }
        this.native_instance = ni;
        this.mCleaner = sRegistry.registerNativeAllocation(this, this.native_instance);
        this.mStyle = nativeGetStyle(ni);
        this.mWeight = nativeGetWeight(ni);
        this.mSystemFontFamilyName = systemFontFamilyName;
    }

    public void releaseNativeObjectForTest() {
        this.mCleaner.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Typeface getSystemDefaultTypeface(String familyName) {
        Typeface tf = sSystemFontMap.get(familyName);
        return tf == null ? DEFAULT : tf;
    }

    public static void initSystemDefaultTypefaces(
            Map<String, android.graphics.fonts.FontFamily[]> fallbacks,
            List<FontConfig.Alias> aliases,
            Map<String, Typeface> outSystemFontMap) {
        Typeface base;
        for (Map.Entry<String, android.graphics.fonts.FontFamily[]> entry : fallbacks.entrySet()) {
            Typeface typeface = createFromFamilies(entry.getKey(), entry.getValue());
            if (FontsLikeDefault.contains(entry.getKey())) {
                typeface.isLikeDefault = true;
            }
            if (FontsLikeBold.contains(entry.getKey())) {
                typeface.isBoldFont = true;
            }
            outSystemFontMap.put(entry.getKey(), typeface);
        }
        for (int i = 0; i < aliases.size(); i++) {
            FontConfig.Alias alias = aliases.get(i);
            if (!outSystemFontMap.containsKey(alias.getName())
                    && (base = outSystemFontMap.get(alias.getOriginal())) != null) {
                int weight = alias.getWeight();
                Typeface newFace =
                        weight == 400
                                ? base
                                : new Typeface(
                                        nativeCreateWeightAlias(base.native_instance, weight),
                                        alias.getName());
                if (weight != 400) {
                    if (FontsLikeDefault.contains(alias.getOriginal())) {
                        newFace.isLikeDefault = true;
                    }
                    if (FontsLikeBold.contains(alias.getOriginal())) {
                        newFace.isBoldFont = true;
                    }
                }
                outSystemFontMap.put(alias.getName(), newFace);
            }
        }
    }

    private static void registerGenericFamilyNative(String familyName, Typeface typeface) {
        if (typeface != null) {
            nativeRegisterGenericFamily(familyName, typeface.native_instance);
        }
    }

    public static SharedMemory serializeFontMap(Map<String, Typeface> fontMap)
            throws IOException, ErrnoException {
        long[] nativePtrs = new long[fontMap.size()];
        ByteArrayOutputStream namesBytes = new ByteArrayOutputStream();
        int i = 0;
        for (Map.Entry<String, Typeface> entry : fontMap.entrySet()) {
            nativePtrs[i] = entry.getValue().native_instance;
            writeString(namesBytes, entry.getKey());
            i++;
        }
        int typefacesBytesCount = nativeWriteTypefaces(null, 4, nativePtrs);
        SharedMemory sharedMemory =
                SharedMemory.create("fontMap", typefacesBytesCount + 4 + namesBytes.size());
        ByteBuffer writableBuffer = sharedMemory.mapReadWrite().order(ByteOrder.BIG_ENDIAN);
        try {
            writableBuffer.putInt(typefacesBytesCount);
            int writtenBytesCount =
                    nativeWriteTypefaces(writableBuffer, writableBuffer.position(), nativePtrs);
            if (writtenBytesCount != typefacesBytesCount) {
                throw new IOException(
                        String.format(
                                "Unexpected bytes written: %d, expected: %d",
                                Integer.valueOf(writtenBytesCount),
                                Integer.valueOf(typefacesBytesCount)));
            }
            writableBuffer.position(writableBuffer.position() + writtenBytesCount);
            writableBuffer.put(namesBytes.toByteArray());
            SharedMemory.unmap(writableBuffer);
            sharedMemory.setProtect(OsConstants.PROT_READ);
            return sharedMemory;
        } catch (Throwable th) {
            SharedMemory.unmap(writableBuffer);
            throw th;
        }
    }

    public static long[] deserializeFontMap(ByteBuffer buffer, Map<String, Typeface> out)
            throws IOException {
        int typefacesBytesCount = buffer.getInt();
        long[] nativePtrs = nativeReadTypefaces(buffer, buffer.position());
        if (nativePtrs == null) {
            throw new IOException("Could not read typefaces");
        }
        out.clear();
        buffer.position(buffer.position() + typefacesBytesCount);
        for (long nativePtr : nativePtrs) {
            String name = readString(buffer);
            Typeface tf = new Typeface(nativePtr, name);
            if (FontsLikeDefault.contains(name)) {
                tf.isLikeDefault = true;
            }
            if (FontsLikeBold.contains(name)) {
                tf.isBoldFont = true;
            }
            out.put(name, tf);
        }
        return nativePtrs;
    }

    private static String readString(ByteBuffer buffer) {
        int length = buffer.getInt();
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes);
    }

    private static void writeString(ByteArrayOutputStream bos, String value) throws IOException {
        byte[] bytes = value.getBytes();
        writeInt(bos, bytes.length);
        bos.write(bytes);
    }

    private static void writeInt(ByteArrayOutputStream bos, int value) {
        bos.write((value >> 24) & 255);
        bos.write((value >> 16) & 255);
        bos.write((value >> 8) & 255);
        bos.write(value & 255);
    }

    public static Map<String, Typeface> getSystemFontMap() {
        Map<String, Typeface> map;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            map = sSystemFontMap;
        }
        return map;
    }

    public static void setSystemFontMap(SharedMemory sharedMemory)
            throws IOException, ErrnoException {
        if (sSystemFontMapBuffer != null) {
            if (sharedMemory == null || sharedMemory == sSystemFontMapSharedMemory) {
                return;
            } else {
                throw new UnsupportedOperationException(
                        "Once set, buffer-based system font map cannot be updated");
            }
        }
        sSystemFontMapSharedMemory = sharedMemory;
        Trace.traceBegin(2L, "setSystemFontMap");
        try {
            if (sharedMemory == null) {
                loadPreinstalledSystemFontMap();
                return;
            }
            sSystemFontMapBuffer = sharedMemory.mapReadOnly().order(ByteOrder.BIG_ENDIAN);
            Map<String, Typeface> systemFontMap = new ArrayMap<>();
            long[] nativePtrs = deserializeFontMap(sSystemFontMapBuffer, systemFontMap);
            for (long ptr : nativePtrs) {
                nativeAddFontCollections(ptr);
            }
            setSystemFontMap(systemFontMap);
        } finally {
            Trace.traceEnd(2L);
        }
    }

    public static void setSystemFontMap(Map<String, Typeface> systemFontMap) {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            sSystemFontMap.clear();
            sSystemFontMap.putAll(systemFontMap);
            if (sSystemFontMap.containsKey(DEFAULT_FAMILY)) {
                setDefault(sSystemFontMap.get(DEFAULT_FAMILY));
            }
            nativeForceSetStaticFinalField("DEFAULT", create(sDefaultTypeface, 0));
            nativeForceSetStaticFinalField("DEFAULT_BOLD", create(sDefaultTypeface, 1));
            nativeForceSetStaticFinalField("SANS_SERIF", create(DEFAULT_FAMILY, 0));
            nativeForceSetStaticFinalField("SERIF", create("serif", 0));
            nativeForceSetStaticFinalField("MONOSPACE", create("monospace", 0));
            sDefaults =
                    new Typeface[] {
                        DEFAULT, DEFAULT_BOLD, create((String) null, 2), create((String) null, 3)
                    };
            String[] genericFamilies = {
                "serif", DEFAULT_FAMILY, "cursive", "fantasy", "monospace", "system-ui"
            };
            for (String genericFamily : genericFamilies) {
                registerGenericFamilyNative(genericFamily, systemFontMap.get(genericFamily));
            }
            if (!isMtFontsDirectoryExists) {
                makeMtFontsDirectory();
                isMtFontsDirectoryExists = true;
            }
        }
    }

    public static Pair<List<Typeface>, List<Typeface>> changeDefaultFontForTest(
            List<Typeface> defaults, List<Typeface> genericFamilies) {
        Pair<List<Typeface>, List<Typeface>> pair;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            List<Typeface> oldDefaults = Arrays.asList(sDefaults);
            sDefaults = (Typeface[]) defaults.toArray(new Typeface[4]);
            setDefault(defaults.get(0));
            ArrayList<Typeface> oldGenerics = new ArrayList<>();
            oldGenerics.add(sSystemFontMap.get(DEFAULT_FAMILY));
            sSystemFontMap.put(DEFAULT_FAMILY, genericFamilies.get(0));
            oldGenerics.add(sSystemFontMap.get("serif"));
            sSystemFontMap.put("serif", genericFamilies.get(1));
            oldGenerics.add(sSystemFontMap.get("monospace"));
            sSystemFontMap.put("monospace", genericFamilies.get(2));
            pair = new Pair<>(oldDefaults, oldGenerics);
        }
        return pair;
    }

    private static void preloadFontFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Log.i(TAG, "Preloading " + file.getAbsolutePath());
            nativeWarmUpCache(filePath);
        }
    }

    public static void destroySystemFontMap() {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            for (Typeface typeface : sSystemFontMap.values()) {
                typeface.releaseNativeObjectForTest();
            }
            sSystemFontMap.clear();
            if (sSystemFontMapBuffer != null) {
                SharedMemory.unmap(sSystemFontMapBuffer);
            }
            sSystemFontMapBuffer = null;
            sSystemFontMapSharedMemory = null;
            synchronized (sStyledCacheLock) {
                destroyTypefaceCacheLocked(sStyledTypefaceCache);
            }
            synchronized (sWeightCacheLock) {
                destroyTypefaceCacheLocked(sWeightTypefaceCache);
            }
        }
    }

    private static void destroyTypefaceCacheLocked(LongSparseArray<SparseArray<Typeface>> cache) {
        for (int i = 0; i < cache.size(); i++) {
            SparseArray<Typeface> array = cache.valueAt(i);
            for (int j = 0; j < array.size(); j++) {
                array.valueAt(j).releaseNativeObjectForTest();
            }
        }
        cache.clear();
    }

    public static void loadPreinstalledSystemFontMap() {
        FontConfig fontConfig = SystemFonts.getSystemPreinstalledFontConfig();
        Map<String, android.graphics.fonts.FontFamily[]> fallback =
                SystemFonts.buildSystemFallback(fontConfig);
        Map<String, Typeface> typefaceMap = SystemFonts.buildSystemTypefaces(fontConfig, fallback);
        setSystemFontMap(typefaceMap);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Typeface typeface = (Typeface) o;
        if (this.mStyle == typeface.mStyle && this.native_instance == typeface.native_instance) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = (17 * 31) + ((int) (this.native_instance ^ (this.native_instance >>> 32)));
        return (result * 31) + this.mStyle;
    }

    public boolean isSupportedAxes(int axis) {
        synchronized (this) {
            if (this.mSupportedAxes == null) {
                this.mSupportedAxes = nativeGetSupportedAxes(this.native_instance);
                if (this.mSupportedAxes == null) {
                    this.mSupportedAxes = EMPTY_AXES;
                }
            }
        }
        return Arrays.binarySearch(this.mSupportedAxes, axis) >= 0;
    }

    public static String semGetFontPathOfCurrentFontStyle(Context ctx, int typefaceIndex) {
        return getFontPathFlipFont();
    }

    public static boolean semIsDefaultFontStyle() {
        return !isFlipFontUsed;
    }

    public static String getFontNameFlipFont() {
        String sx = getFullFlipFont();
        String[] parts = sx.split("#");
        if (parts.length < 2) {
            if (parts[0].endsWith("default")) {
                return "default";
            }
            return null;
        }
        return parts[1];
    }

    private static String getFontPathFlipFont() {
        String sx = getFullFlipFont();
        String[] parts = sx.split("#");
        return parts[0];
    }

    private static String getFullFlipFont() {
        File mtFontsDir = new File("/data/app_fonts/");
        if (mtFontsDir.isDirectory()
                && mtFontsDir.list() != null
                && mtFontsDir.list().length == 0) {
            return "default";
        }
        File file = new File(OWNER_SANS_LOC_PATH);
        try {
            FileInputStream fis = new FileInputStream(file);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                try {
                    String systemFont = br.readLine();
                    br.close();
                    fis.close();
                    return systemFont;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            return "default";
        }
    }

    public static void setFlipFonts() {
        String strFontPathBold;
        String strFontPath;
        if (getDefault() == null) {
            return;
        }
        String strFontPath2 = getFontPathFlipFont();
        if (strFontPath2.endsWith("default")) {
            isFlipFontUsed = false;
            strFontPathBold = "default";
            strFontPath = "default";
        } else {
            isFlipFontUsed = true;
            strFontPathBold = strFontPath2 + "/" + DROIDSANS_BOLD;
            strFontPath = strFontPath2 + "/" + DROIDSANS;
        }
        if (strFontPath.equals(FlipFontPath)) {
            return;
        }
        FlipFontPath = strFontPath;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            if (!isFlipFontUsed) {
                nativeSetDefault(sDefaultFlipfont.native_instance);
                DEFAULT.native_instance = nativeCreateFromTypeface(0L, 0);
                DEFAULT_BOLD.native_instance = nativeCreateFromTypeface(0L, 1);
            } else {
                nativeSetDefault(sDefaultFlipfont.native_instance);
                DEFAULT.native_instance = sDefaultFlipfont.native_instance;
                Typeface flipfontTypeface = fontCache.get(strFontPath);
                if (flipfontTypeface == null) {
                    try {
                        flipfontTypeface = createFromFile(strFontPath);
                    } catch (RuntimeException e) {
                        DEFAULT.native_instance = create((String) null, 0).native_instance;
                    }
                    if (DEFAULT.native_instance == 0) {
                        flipfontTypeface = create((String) null, 0);
                        DEFAULT.native_instance = flipfontTypeface.native_instance;
                    }
                    if (flipfontTypeface != null) {
                        fontCache.put(strFontPath, flipfontTypeface);
                    }
                }
                if (flipfontTypeface != null) {
                    DEFAULT.native_instance = flipfontTypeface.native_instance;
                }
                DEFAULT.mStyle = nativeGetStyle(DEFAULT.native_instance);
                Typeface flipfontBoldTypeface = fontCache.get(strFontPathBold);
                if (flipfontBoldTypeface == null) {
                    try {
                        flipfontBoldTypeface = createFromFile(strFontPathBold);
                    } catch (RuntimeException e2) {
                        DEFAULT_BOLD.native_instance = create((String) null, 1).native_instance;
                    }
                    if (DEFAULT_BOLD.native_instance == 0) {
                        flipfontBoldTypeface = create((String) null, 1);
                    }
                    if (flipfontBoldTypeface != null) {
                        fontCache.put(strFontPathBold, flipfontBoldTypeface);
                    }
                }
                if (flipfontBoldTypeface != null) {
                    DEFAULT_BOLD.native_instance = flipfontBoldTypeface.native_instance;
                }
                DEFAULT_BOLD.mStyle = nativeGetStyle(DEFAULT_BOLD.native_instance);
                sDefaults[0].native_instance =
                        nativeCreateFromTypefaceWithExactStyle(DEFAULT.native_instance, 400, false);
                sDefaults[0].mStyle = nativeGetStyle(sDefaults[0].native_instance);
                sDefaults[1].native_instance =
                        nativeCreateFromTypefaceWithExactStyle(
                                DEFAULT_BOLD.native_instance, 700, false);
                sDefaults[1].mStyle = nativeGetStyle(sDefaults[1].native_instance);
                sDefaults[2].native_instance =
                        nativeCreateFromTypefaceWithExactStyle(DEFAULT.native_instance, 400, true);
                sDefaults[2].mStyle = nativeGetStyle(sDefaults[2].native_instance);
                sDefaults[3].native_instance =
                        nativeCreateFromTypefaceWithExactStyle(
                                DEFAULT_BOLD.native_instance, 700, true);
                sDefaults[3].mStyle = nativeGetStyle(sDefaults[3].native_instance);
                nativeSetDefault(sDefaultTypeface.native_instance);
            }
        }
    }

    private static void makeMtFontsDirectory() {
        File mtFontsDir = new File("/data/app_fonts/");
        if (!mtFontsDir.exists()) {
            if (!mtFontsDir.mkdir()) {
                Log.v(TAG_MONOTYPE, "Cannot make directory : " + mtFontsDir.getAbsolutePath());
            }
            mtFontsDir.setExecutable(true, false);
            if (!mtFontsDir.setReadable(true, false)) {
                Log.v(TAG_MONOTYPE, "Cannot set Readable : " + mtFontsDir.getAbsolutePath());
            }
            if (!mtFontsDir.setWritable(true, false)) {
                Log.v(TAG_MONOTYPE, "Cannot set Writable : " + mtFontsDir.getAbsolutePath());
            }
            Log.v(TAG_MONOTYPE, "makeMtFontsDirectory()");
        }
    }
}
