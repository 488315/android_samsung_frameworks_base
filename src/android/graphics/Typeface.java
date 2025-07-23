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
import com.android.text.flags.Flags;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
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
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public class Typeface {
    public static final int BOLD = 1;
    public static final int BOLD_ITALIC = 3;
    public static final Typeface DEFAULT = null;
    public static final Typeface DEFAULT_BOLD = null;
    private static final String DROIDSANS = "DroidSans.ttf";
    private static final String DROIDSANS_BOLD = "DroidSans-Bold.ttf";
    public static final boolean ENABLE_LAZY_TYPEFACE_INITIALIZATION = true;
    private static final int FONT_WEIGHT_BOLD = 700;
    private static final int FONT_WEIGHT_NORMAL = 400;
    public static final int ITALIC = 2;
    public static final Typeface MONOSPACE = null;
    public static final int NORMAL = 0;
    private static final String OWNER_SANS_LOC_PATH = "/data/app_fonts/0/sans.loc";
    public static final int RESOLVE_BY_FONT_TABLE = -1;
    private static final String SANS_LOC_POST = "/sans.loc";
    private static final String SANS_LOC_PRE = "/data/app_fonts/";
    public static final Typeface SANS_SERIF = null;
    public static final Typeface SERIF = null;
    private static final int STYLE_ITALIC = 1;
    public static final int STYLE_MASK = 3;
    private static final int STYLE_NORMAL = 0;
    private static String TAG = "Typeface";
    private static final String TAG_MONOTYPE = "Monotype";
    private static Typeface sDefaultFlipfont;
    static Typeface sDefaultTypeface;
    static Typeface[] sDefaults;
    private boolean isBoldFont;
    public boolean isLikeDefault;
    private final Runnable mCleaner;
    private final Typeface mDerivedFrom;
    public boolean mFromBuilder;
    private boolean mIsVariationInstance;
    private int mStyle;
    private int[] mSupportedAxes;
    private final String mSystemFontFamilyName;
    private final int mWeight;
    public long native_instance;
    private static final LongSparseArray<SparseArray<Typeface>> sStyledTypefaceCache = new LongSparseArray<>(3);
    private static final Object sStyledCacheLock = new Object();
    private static final LongSparseArray<SparseArray<Typeface>> sWeightTypefaceCache = new LongSparseArray<>(3);
    private static final Object sWeightCacheLock = new Object();
    private static final LruCache<String, Typeface> sDynamicTypefaceCache = new LruCache<>(16);
    private static final Object sDynamicCacheLock = new Object();
    private static final LruCache<Long, LruCache<String, Typeface>> sVariableCache = new LruCache<>(16);
    private static final Object sVariableCacheLock = new Object();
    private static final Hashtable<String, Typeface> fontCache = new Hashtable<>();
    static final Map<String, Typeface> sSystemFontMap = new ArrayMap();
    static ByteBuffer sSystemFontMapBuffer = null;
    static SharedMemory sSystemFontMapSharedMemory = null;
    private static final Object SYSTEM_FONT_MAP_LOCK = new Object();

    @Deprecated
    static final Map<String, FontFamily[]> sSystemFallbackMap = Collections.EMPTY_MAP;
    private static final int[] EMPTY_AXES = new int[0];
    private static String FlipFontPath = "";
    private static boolean isMtFontsDirectoryExists = false;
    public static boolean isFlipFontUsed = false;
    private static final ArrayList<String> FontsLikeBold = new ArrayList<>(Arrays.asList("sans-serif-medium", "sans-serif-black", "sec-semibold", "sec-bold"));
    public static final String DEFAULT_FAMILY = "sans-serif";
    private static final ArrayList<String> FontsLikeDefault = new ArrayList<>(Arrays.asList("sec-400", "sans-serif-thin", "sans-serif-light", DEFAULT_FAMILY, "sans-serif-condensed", "sans-serif-medium", "sans-serif-black", "monospace", "sec", "sec-num", "sec-num-fixed"));

    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    @CriticalNative
    private static native void nativeAddFontCollections(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateFromArray(long[] jArr, long j, int i, int i2);

    private static native long nativeCreateFromTypeface(long j, int i);

    private static native long nativeCreateFromTypefaceWithExactStyle(long j, int i, boolean z);

    private static native long nativeCreateFromTypefaceWithVariation(long j, List<FontVariationAxis> list);

    private static native long nativeCreateWeightAlias(long j, int i);

    private static native void nativeForceSetStaticFinalField(String str, Typeface typeface);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static native long nativeGetReleaseFunc();

    @CriticalNative
    private static native int nativeGetStyle(long j);

    private static native int[] nativeGetSupportedAxes(long j);

    @CriticalNative
    private static native int nativeGetWeight(long j);

    @CriticalNative
    private static native boolean nativeIsVariationInstance(long j);

    private static native long[] nativeReadTypefaces(ByteBuffer byteBuffer, int i);

    private static native void nativeRegisterGenericFamily(String str, long j);

    @FastNative
    private static native void nativeRegisterLocaleList(String str);

    @CriticalNative
    private static native void nativeSetDefault(long j);

    private static native void nativeWarmUpCache(String str);

    private static native int nativeWriteTypefaces(ByteBuffer byteBuffer, int i, long[] jArr);

    private static void staticInitializer$ravenwood() {
    }

    private static class NoImagePreloadHolder {
        static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Typeface.class.getClassLoader(), Typeface.nativeGetReleaseFunc());

        private NoImagePreloadHolder() {
        }
    }

    static {
        staticInitializer();
    }

    public static void clearTypefaceCachesForTestingPurpose() {
        synchronized (sWeightCacheLock) {
            sWeightTypefaceCache.clear();
        }
        synchronized (sDynamicCacheLock) {
            sDynamicTypefaceCache.evictAll();
        }
        synchronized (sVariableCacheLock) {
            sVariableCache.evictAll();
        }
    }

    public static SharedMemory getSystemFontMapSharedMemory() {
        Objects.requireNonNull(sSystemFontMapSharedMemory);
        return sSystemFontMapSharedMemory;
    }

    private static void setDefault(Typeface typeface) {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            sDefaultFlipfont = new Typeface(nativeCreateFromTypeface(typeface.native_instance, typeface.mStyle));
            sDefaultTypeface = typeface;
            nativeSetDefault(typeface.native_instance);
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

    public boolean isVariationInstance() {
        return this.mIsVariationInstance;
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

    public final Typeface getDerivedFrom() {
        return this.mDerivedFrom;
    }

    public final String getSystemFontFamilyName() {
        return this.mSystemFontFamilyName;
    }

    private static boolean hasFontFamily(String str) {
        boolean containsKey;
        Objects.requireNonNull(str, "familyName cannot be null");
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            containsKey = sSystemFontMap.containsKey(str);
        }
        return containsKey;
    }

    public static Typeface createFromResources(FontResourcesParser.FamilyResourceEntry familyResourceEntry, AssetManager assetManager, String str) {
        Typeface typeface;
        FontFamily.Builder builder;
        int i;
        if (familyResourceEntry instanceof FontResourcesParser.ProviderResourceEntry) {
            FontResourcesParser.ProviderResourceEntry providerResourceEntry = (FontResourcesParser.ProviderResourceEntry) familyResourceEntry;
            String systemFontFamilyName = providerResourceEntry.getSystemFontFamilyName();
            if (systemFontFamilyName != null && hasFontFamily(systemFontFamilyName)) {
                return create(systemFontFamilyName, 0);
            }
            List<List<String>> certs = providerResourceEntry.getCerts();
            ArrayList arrayList = new ArrayList();
            if (certs != null) {
                for (int i2 = 0; i2 < certs.size(); i2++) {
                    List<String> list = certs.get(i2);
                    ArrayList arrayList2 = new ArrayList();
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        arrayList2.add(Base64.decode(list.get(i3), 0));
                    }
                    arrayList.add(arrayList2);
                }
            }
            Typeface fontSync = FontsContract.getFontSync(new FontRequest(providerResourceEntry.getAuthority(), providerResourceEntry.getPackage(), providerResourceEntry.getQuery(), arrayList));
            return fontSync == null ? DEFAULT : fontSync;
        }
        Typeface findFromCache = findFromCache(assetManager, str);
        if (findFromCache != null) {
            return findFromCache;
        }
        try {
            FontResourcesParser.FontFileResourceEntry[] entries = ((FontResourcesParser.FontFamilyFilesResourceEntry) familyResourceEntry).getEntries();
            int length = entries.length;
            builder = null;
            int i4 = 0;
            while (true) {
                i = 1;
                if (i4 >= length) {
                    break;
                }
                FontResourcesParser.FontFileResourceEntry fontFileResourceEntry = entries[i4];
                Font.Builder fontVariationSettings = new Font.Builder(assetManager, fontFileResourceEntry.getFileName(), false, -1).setTtcIndex(fontFileResourceEntry.getTtcIndex()).setFontVariationSettings(fontFileResourceEntry.getVariationSettings());
                if (fontFileResourceEntry.getWeight() != -1) {
                    fontVariationSettings.setWeight(fontFileResourceEntry.getWeight());
                }
                if (fontFileResourceEntry.getItalic() != -1) {
                    if (fontFileResourceEntry.getItalic() != 1) {
                        i = 0;
                    }
                    fontVariationSettings.setSlant(i);
                }
                if (builder == null) {
                    builder = new FontFamily.Builder(fontVariationSettings.build());
                } else {
                    builder.addFont(fontVariationSettings.build());
                }
                i4++;
            }
        } catch (IOException unused) {
            typeface = DEFAULT;
        } catch (IllegalArgumentException unused2) {
            return null;
        }
        if (builder == null) {
            return DEFAULT;
        }
        android.graphics.fonts.FontFamily build = builder.build();
        FontStyle fontStyle = new FontStyle(400, 0);
        Font font = build.getFont(0);
        int matchScore = fontStyle.getMatchScore(font.getStyle());
        while (i < build.getSize()) {
            Font font2 = build.getFont(i);
            int matchScore2 = fontStyle.getMatchScore(font2.getStyle());
            if (matchScore2 < matchScore) {
                font = font2;
                matchScore = matchScore2;
            }
            i++;
        }
        typeface = new CustomFallbackBuilder(build).setStyle(font.getStyle()).build();
        synchronized (sDynamicCacheLock) {
            sDynamicTypefaceCache.put(Builder.createAssetUid(assetManager, str, 0, null, -1, -1, DEFAULT_FAMILY), typeface);
        }
        return typeface;
    }

    public static Typeface findFromCache(AssetManager assetManager, String str) {
        synchronized (sDynamicCacheLock) {
            Typeface typeface = sDynamicTypefaceCache.get(Builder.createAssetUid(assetManager, str, 0, null, -1, -1, DEFAULT_FAMILY));
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

        public Builder(File file) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new Font.Builder(file);
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(FileDescriptor fileDescriptor) {
            Font.Builder builder;
            this.mWeight = -1;
            this.mItalic = -1;
            try {
                builder = new Font.Builder(ParcelFileDescriptor.dup(fileDescriptor));
            } catch (IOException unused) {
                builder = null;
            }
            this.mFontBuilder = builder;
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(String str) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new Font.Builder(new File(str));
            this.mAssetManager = null;
            this.mPath = null;
        }

        public Builder(AssetManager assetManager, String str) {
            this(assetManager, str, true, 0);
        }

        public Builder(AssetManager assetManager, String str, boolean z, int i) {
            this.mWeight = -1;
            this.mItalic = -1;
            this.mFontBuilder = new Font.Builder(assetManager, str, z, i);
            this.mAssetManager = assetManager;
            this.mPath = str;
        }

        public Builder setWeight(int i) {
            this.mWeight = i;
            this.mFontBuilder.setWeight(i);
            return this;
        }

        public Builder setItalic(boolean z) {
            this.mItalic = z ? 1 : 0;
            this.mFontBuilder.setSlant(z ? 1 : 0);
            return this;
        }

        public Builder setTtcIndex(int i) {
            this.mFontBuilder.setTtcIndex(i);
            return this;
        }

        public Builder setFontVariationSettings(String str) {
            this.mFontBuilder.setFontVariationSettings(str);
            return this;
        }

        public Builder setFontVariationSettings(FontVariationAxis[] fontVariationAxisArr) {
            this.mFontBuilder.setFontVariationSettings(fontVariationAxisArr);
            return this;
        }

        public Builder setFallback(String str) {
            this.mFallbackFamilyName = str;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String createAssetUid(AssetManager assetManager, String str, int i, FontVariationAxis[] fontVariationAxisArr, int i2, int i3, String str2) {
            SparseArray<String> assignedPackageIdentifiers = assetManager.getAssignedPackageIdentifiers();
            StringBuilder sb = new StringBuilder();
            int size = assignedPackageIdentifiers.size();
            for (int i4 = 0; i4 < size; i4++) {
                sb.append(assignedPackageIdentifiers.valueAt(i4));
                sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            }
            sb.append(str);
            sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            sb.append(Integer.toString(i));
            sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            sb.append(Integer.toString(i2));
            sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            sb.append(Integer.toString(i3));
            sb.append("--");
            sb.append(str2);
            sb.append("--");
            if (fontVariationAxisArr != null) {
                for (FontVariationAxis fontVariationAxis : fontVariationAxisArr) {
                    sb.append(fontVariationAxis.getTag());
                    sb.append(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    sb.append(Float.toString(fontVariationAxis.getStyleValue()));
                }
            }
            return sb.toString();
        }

        private Typeface resolveFallbackTypeface() {
            String str = this.mFallbackFamilyName;
            if (str == null) {
                return null;
            }
            Typeface systemDefaultTypeface = Typeface.getSystemDefaultTypeface(str);
            int i = this.mWeight;
            if (i == -1 && this.mItalic == -1) {
                return systemDefaultTypeface;
            }
            if (i == -1) {
                i = systemDefaultTypeface.mWeight;
            }
            int i2 = this.mItalic;
            boolean z = false;
            if (i2 != -1 ? i2 == 1 : (systemDefaultTypeface.mStyle & 2) != 0) {
                z = true;
            }
            return Typeface.createWeightStyle(systemDefaultTypeface, i, z);
        }

        public Typeface build() {
            String createAssetUid;
            Font.Builder builder = this.mFontBuilder;
            if (builder == null) {
                return resolveFallbackTypeface();
            }
            try {
                Font build = builder.build();
                AssetManager assetManager = this.mAssetManager;
                if (assetManager == null) {
                    createAssetUid = null;
                } else {
                    String str = this.mPath;
                    int ttcIndex = build.getTtcIndex();
                    FontVariationAxis[] axes = build.getAxes();
                    int i = this.mWeight;
                    int i2 = this.mItalic;
                    String str2 = this.mFallbackFamilyName;
                    if (str2 == null) {
                        str2 = Typeface.DEFAULT_FAMILY;
                    }
                    createAssetUid = createAssetUid(assetManager, str, ttcIndex, axes, i, i2, str2);
                }
                if (createAssetUid != null) {
                    synchronized (Typeface.sDynamicCacheLock) {
                        Typeface typeface = (Typeface) Typeface.sDynamicTypefaceCache.get(createAssetUid);
                        if (typeface != null) {
                            return typeface;
                        }
                    }
                }
                android.graphics.fonts.FontFamily build2 = new FontFamily.Builder(build).build();
                int i3 = this.mWeight;
                if (i3 == -1) {
                    i3 = build.getStyle().getWeight();
                }
                int i4 = this.mItalic;
                if (i4 == -1) {
                    i4 = build.getStyle().getSlant();
                }
                CustomFallbackBuilder style = new CustomFallbackBuilder(build2).setStyle(new FontStyle(i3, i4));
                String str3 = this.mFallbackFamilyName;
                if (str3 != null) {
                    style.setSystemFallback(str3);
                }
                Typeface build3 = style.build();
                build3.mFromBuilder = true;
                if (createAssetUid == null) {
                    return build3;
                }
                synchronized (Typeface.sDynamicCacheLock) {
                    Typeface.sDynamicTypefaceCache.put(createAssetUid, build3);
                }
                return build3;
            } catch (IOException | IllegalArgumentException unused) {
                return resolveFallbackTypeface();
            }
        }
    }

    public static final class CustomFallbackBuilder {
        private static final int MAX_CUSTOM_FALLBACK = 64;
        private String mFallbackName;
        private final ArrayList<android.graphics.fonts.FontFamily> mFamilies;
        private FontStyle mStyle;

        public static int getMaxCustomFallbackCount() {
            return 64;
        }

        public CustomFallbackBuilder(android.graphics.fonts.FontFamily fontFamily) {
            ArrayList<android.graphics.fonts.FontFamily> arrayList = new ArrayList<>();
            this.mFamilies = arrayList;
            this.mFallbackName = null;
            Preconditions.checkNotNull(fontFamily);
            arrayList.add(fontFamily);
        }

        public CustomFallbackBuilder setSystemFallback(String str) {
            Preconditions.checkNotNull(str);
            this.mFallbackName = str;
            return this;
        }

        public CustomFallbackBuilder setStyle(FontStyle fontStyle) {
            this.mStyle = fontStyle;
            return this;
        }

        public CustomFallbackBuilder addCustomFallback(android.graphics.fonts.FontFamily fontFamily) {
            Preconditions.checkNotNull(fontFamily);
            Preconditions.checkArgument(this.mFamilies.size() < getMaxCustomFallbackCount(), "Custom fallback limit exceeded(%d)", Integer.valueOf(getMaxCustomFallbackCount()));
            this.mFamilies.add(fontFamily);
            return this;
        }

        public Typeface build() {
            int size = this.mFamilies.size();
            Typeface systemDefaultTypeface = Typeface.getSystemDefaultTypeface(this.mFallbackName);
            long[] jArr = new long[size];
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                jArr[i2] = this.mFamilies.get(i2).getNativePtr();
            }
            FontStyle fontStyle = this.mStyle;
            int weight = fontStyle == null ? 400 : fontStyle.getWeight();
            FontStyle fontStyle2 = this.mStyle;
            if (fontStyle2 != null && fontStyle2.getSlant() != 0) {
                i = 1;
            }
            return new Typeface(Typeface.nativeCreateFromArray(jArr, systemDefaultTypeface.native_instance, weight, i), (String) null);
        }
    }

    public static Typeface create(String str, int i) {
        if (isFlipFontUsed && FontsLikeDefault.contains(str)) {
            if (FontsLikeBold.contains(str)) {
                i = 1;
            }
            return defaultFromStyle(i);
        }
        return create(getSystemDefaultTypeface(str), i);
    }

    public static Typeface create(Typeface typeface, int i) {
        if ((i & (-4)) != 0) {
            i = 0;
        }
        if (typeface == null) {
            typeface = getDefault();
        }
        if (isFlipFontUsed && typeface.isLikeDefault) {
            return typeface.isBoldFont ? defaultFromStyle(1) : defaultFromStyle(0);
        }
        if (typeface.mStyle == i) {
            return typeface;
        }
        long j = typeface.native_instance;
        synchronized (sStyledCacheLock) {
            LongSparseArray<SparseArray<Typeface>> longSparseArray = sStyledTypefaceCache;
            SparseArray<Typeface> sparseArray = longSparseArray.get(j);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>(4);
                longSparseArray.put(j, sparseArray);
            } else {
                Typeface typeface2 = sparseArray.get(i);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            Typeface typeface3 = new Typeface(nativeCreateFromTypeface(j, i), typeface.getSystemFontFamilyName());
            if (typeface != null && typeface.mStyle == i) {
                typeface3.isLikeDefault = typeface.isLikeDefault;
                typeface3.isBoldFont = typeface.isBoldFont;
            }
            sparseArray.put(i, typeface3);
            return typeface3;
        }
    }

    public static Typeface create(Typeface typeface, int i, boolean z) {
        Preconditions.checkArgumentInRange(i, 0, 1000, "weight");
        if (typeface == null) {
            typeface = getDefault();
        }
        return createWeightStyle(typeface, i, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Typeface createWeightStyle(Typeface typeface, int i, boolean z) {
        int i2 = (i << 1) | (z ? 1 : 0);
        if (isFlipFontUsed && typeface.isLikeDefault) {
            return defaultFromStyle(i <= 500 ? 0 : 1);
        }
        synchronized (sWeightCacheLock) {
            LongSparseArray<SparseArray<Typeface>> longSparseArray = sWeightTypefaceCache;
            SparseArray<Typeface> sparseArray = longSparseArray.get(typeface.native_instance);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>(4);
                longSparseArray.put(typeface.native_instance, sparseArray);
            } else {
                Typeface typeface2 = sparseArray.get(i2);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            Typeface typeface3 = new Typeface(nativeCreateFromTypefaceWithExactStyle(typeface.native_instance, i, z), typeface.getSystemFontFamilyName());
            if (typeface != null) {
                typeface3.isLikeDefault = typeface.isLikeDefault;
                typeface3.isBoldFont = typeface.isBoldFont;
            }
            sparseArray.put(i2, typeface3);
            return typeface3;
        }
    }

    private static String axesToVarKey(List<FontVariationAxis> list) {
        list.sort(Comparator.comparingInt(new ToIntFunction() { // from class: android.graphics.Typeface$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((FontVariationAxis) obj).getOpenTypeTagValue();
            }
        }));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            FontVariationAxis fontVariationAxis = list.get(i);
            sb.append(fontVariationAxis.getTag());
            sb.append(fontVariationAxis.getStyleValue());
        }
        return sb.toString();
    }

    public static Typeface createFromTypefaceWithVariation(Typeface typeface, List<FontVariationAxis> list) {
        if (Flags.typefaceCacheForVarSettings()) {
            if (typeface == null) {
                typeface = DEFAULT;
            }
            Typeface typeface2 = typeface.mDerivedFrom;
            if (typeface2 != null) {
                typeface = typeface2;
            }
            String axesToVarKey = axesToVarKey(list);
            synchronized (sVariableCacheLock) {
                LruCache<Long, LruCache<String, Typeface>> lruCache = sVariableCache;
                LruCache<String, Typeface> lruCache2 = lruCache.get(Long.valueOf(typeface.native_instance));
                if (lruCache2 == null) {
                    lruCache2 = new LruCache<>(16);
                    lruCache.put(Long.valueOf(typeface.native_instance), lruCache2);
                } else {
                    Typeface typeface3 = lruCache2.get(axesToVarKey);
                    if (typeface3 != null) {
                        return typeface3;
                    }
                }
                Typeface typeface4 = new Typeface(nativeCreateFromTypefaceWithVariation(typeface.native_instance, list), typeface.getSystemFontFamilyName(), typeface);
                lruCache2.put(axesToVarKey, typeface4);
                return typeface4;
            }
        }
        if (typeface == null) {
            typeface = DEFAULT;
        }
        return new Typeface(nativeCreateFromTypefaceWithVariation(typeface.native_instance, list), typeface.getSystemFontFamilyName());
    }

    public static Typeface defaultFromStyle(int i) {
        Typeface typeface;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            if (i < 0 || i > 3) {
                i = 0;
            }
            typeface = sDefaults[i];
        }
        return typeface;
    }

    public static Typeface createFromAsset(AssetManager assetManager, String str) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(assetManager);
        Typeface build = new Builder(assetManager, str).build();
        if (build != null) {
            return build;
        }
        try {
            InputStream open = assetManager.open(str);
            if (open != null) {
                open.close();
            }
            return DEFAULT;
        } catch (IOException unused) {
            throw new RuntimeException("Font asset not found " + str);
        }
    }

    private static String createProviderUid(String str, String str2) {
        return "provider:" + str + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str2;
    }

    public static Typeface createFromFile(File file) {
        Typeface build = new Builder(file).build();
        if (build != null) {
            return build;
        }
        if (!file.exists()) {
            throw new RuntimeException("Font asset not found " + file.getAbsolutePath());
        }
        return DEFAULT;
    }

    public static Typeface createFromFile(String str) {
        Preconditions.checkNotNull(str);
        return createFromFile(new File(str));
    }

    @Deprecated
    private static Typeface createFromFamilies(FontFamily[] fontFamilyArr) {
        long[] jArr = new long[fontFamilyArr.length];
        for (int i = 0; i < fontFamilyArr.length; i++) {
            jArr[i] = fontFamilyArr[i].mNativePtr;
        }
        return new Typeface(nativeCreateFromArray(jArr, 0L, -1, -1), null);
    }

    private static Typeface createFromFamilies(String str, android.graphics.fonts.FontFamily[] fontFamilyArr) {
        long[] jArr = new long[fontFamilyArr.length];
        for (int i = 0; i < fontFamilyArr.length; i++) {
            jArr[i] = fontFamilyArr[i].getNativePtr();
        }
        return new Typeface(nativeCreateFromArray(jArr, 0L, -1, -1), str);
    }

    @Deprecated
    private static Typeface createFromFamiliesWithDefault(FontFamily[] fontFamilyArr, int i, int i2) {
        return createFromFamiliesWithDefault(fontFamilyArr, DEFAULT_FAMILY, i, i2);
    }

    @Deprecated
    private static Typeface createFromFamiliesWithDefault(FontFamily[] fontFamilyArr, String str, int i, int i2) {
        Typeface systemDefaultTypeface = getSystemDefaultTypeface(str);
        long[] jArr = new long[fontFamilyArr.length];
        for (int i3 = 0; i3 < fontFamilyArr.length; i3++) {
            jArr[i3] = fontFamilyArr[i3].mNativePtr;
        }
        return new Typeface(nativeCreateFromArray(jArr, systemDefaultTypeface.native_instance, i, i2), null);
    }

    private Typeface(long j) {
        this(j, (String) null, (Typeface) null);
    }

    private Typeface(long j, String str) {
        this(j, str, (Typeface) null);
    }

    private Typeface(long j, String str, Typeface typeface) {
        this.isLikeDefault = false;
        this.isBoldFont = false;
        if (j == 0) {
            throw new RuntimeException("native typeface cannot be made");
        }
        this.native_instance = j;
        this.mCleaner = NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.native_instance);
        this.mStyle = nativeGetStyle(j);
        this.mWeight = nativeGetWeight(j);
        this.mIsVariationInstance = nativeIsVariationInstance(j);
        this.mSystemFontFamilyName = str;
        this.mDerivedFrom = typeface;
    }

    public void releaseNativeObjectForTest() {
        this.mCleaner.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Typeface getSystemDefaultTypeface(String str) {
        Typeface typeface = sSystemFontMap.get(str);
        return typeface == null ? DEFAULT : typeface;
    }

    public static void initSystemDefaultTypefaces(Map<String, android.graphics.fonts.FontFamily[]> map, List<FontConfig.Alias> list, Map<String, Typeface> map2) {
        Typeface typeface;
        for (Map.Entry<String, android.graphics.fonts.FontFamily[]> entry : map.entrySet()) {
            Typeface createFromFamilies = createFromFamilies(entry.getKey(), entry.getValue());
            if (FontsLikeDefault.contains(entry.getKey())) {
                createFromFamilies.isLikeDefault = true;
            }
            if (FontsLikeBold.contains(entry.getKey())) {
                createFromFamilies.isBoldFont = true;
            }
            map2.put(entry.getKey(), createFromFamilies);
        }
        for (int i = 0; i < list.size(); i++) {
            FontConfig.Alias alias = list.get(i);
            if (!map2.containsKey(alias.getName()) && (typeface = map2.get(alias.getOriginal())) != null) {
                int weight = alias.getWeight();
                if (weight != 400) {
                    typeface = new Typeface(nativeCreateWeightAlias(typeface.native_instance, weight), alias.getName());
                }
                if (weight != 400) {
                    if (FontsLikeDefault.contains(alias.getOriginal())) {
                        typeface.isLikeDefault = true;
                    }
                    if (FontsLikeBold.contains(alias.getOriginal())) {
                        typeface.isBoldFont = true;
                    }
                }
                map2.put(alias.getName(), typeface);
            }
        }
    }

    private static void registerGenericFamilyNative(String str, Typeface typeface) {
        if (typeface != null) {
            nativeRegisterGenericFamily(str, typeface.native_instance);
        }
    }

    public static SharedMemory serializeFontMap(Map<String, Typeface> map) throws IOException, ErrnoException {
        long[] jArr = new long[map.size()];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        for (Map.Entry<String, Typeface> entry : map.entrySet()) {
            jArr[i] = entry.getValue().native_instance;
            writeString(byteArrayOutputStream, entry.getKey());
            i++;
        }
        int nativeWriteTypefaces = nativeWriteTypefaces(null, 4, jArr);
        SharedMemory create = SharedMemory.create("fontMap", nativeWriteTypefaces + 4 + byteArrayOutputStream.size());
        ByteBuffer order = create.mapReadWrite().order(ByteOrder.BIG_ENDIAN);
        try {
            order.putInt(nativeWriteTypefaces);
            int nativeWriteTypefaces2 = nativeWriteTypefaces(order, order.position(), jArr);
            if (nativeWriteTypefaces2 != nativeWriteTypefaces) {
                throw new IOException(String.format("Unexpected bytes written: %d, expected: %d", Integer.valueOf(nativeWriteTypefaces2), Integer.valueOf(nativeWriteTypefaces)));
            }
            order.position(order.position() + nativeWriteTypefaces2);
            order.put(byteArrayOutputStream.toByteArray());
            SharedMemory.unmap(order);
            create.setProtect(OsConstants.PROT_READ);
            return create;
        } catch (Throwable th) {
            SharedMemory.unmap(order);
            throw th;
        }
    }

    public static long[] deserializeFontMap(ByteBuffer byteBuffer, Map<String, Typeface> map) throws IOException {
        int i = byteBuffer.getInt();
        long[] nativeReadTypefaces = nativeReadTypefaces(byteBuffer, byteBuffer.position());
        if (nativeReadTypefaces == null) {
            throw new IOException("Could not read typefaces");
        }
        map.clear();
        byteBuffer.position(byteBuffer.position() + i);
        for (long j : nativeReadTypefaces) {
            String readString = readString(byteBuffer);
            Typeface typeface = new Typeface(j, readString);
            if (FontsLikeDefault.contains(readString)) {
                typeface.isLikeDefault = true;
            }
            if (FontsLikeBold.contains(readString)) {
                typeface.isBoldFont = true;
            }
            map.put(readString, typeface);
        }
        return nativeReadTypefaces;
    }

    private static String readString(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[byteBuffer.getInt()];
        byteBuffer.get(bArr);
        return new String(bArr);
    }

    private static void writeString(ByteArrayOutputStream byteArrayOutputStream, String str) throws IOException {
        byte[] bytes = str.getBytes();
        writeInt(byteArrayOutputStream, bytes.length);
        byteArrayOutputStream.write(bytes);
    }

    private static void writeInt(ByteArrayOutputStream byteArrayOutputStream, int i) {
        byteArrayOutputStream.write((i >> 24) & 255);
        byteArrayOutputStream.write((i >> 16) & 255);
        byteArrayOutputStream.write((i >> 8) & 255);
        byteArrayOutputStream.write(i & 255);
    }

    public static Map<String, Typeface> getSystemFontMap() {
        Map<String, Typeface> map;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            map = sSystemFontMap;
        }
        return map;
    }

    public static void setSystemFontMap(SharedMemory sharedMemory) throws IOException, ErrnoException {
        if (sSystemFontMapBuffer != null) {
            if (sharedMemory != null && sharedMemory != sSystemFontMapSharedMemory) {
                throw new UnsupportedOperationException("Once set, buffer-based system font map cannot be updated");
            }
            return;
        }
        sSystemFontMapSharedMemory = sharedMemory;
        Trace.traceBegin(2L, "setSystemFontMap");
        try {
            if (sharedMemory == null) {
                loadPreinstalledSystemFontMap();
                return;
            }
            sSystemFontMapBuffer = sharedMemory.mapReadOnly().order(ByteOrder.BIG_ENDIAN);
            ArrayMap arrayMap = new ArrayMap();
            for (long j : deserializeFontMap(sSystemFontMapBuffer, arrayMap)) {
                nativeAddFontCollections(j);
            }
            setSystemFontMap(arrayMap);
        } finally {
            Trace.traceEnd(2L);
        }
    }

    public static void setSystemFontMap(Map<String, Typeface> map) {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            Map<String, Typeface> map2 = sSystemFontMap;
            map2.clear();
            map2.putAll(map);
            if (map2.containsKey(DEFAULT_FAMILY)) {
                setDefault(map2.get(DEFAULT_FAMILY));
            }
            nativeForceSetStaticFinalField("DEFAULT", create(sDefaultTypeface, 0));
            nativeForceSetStaticFinalField("DEFAULT_BOLD", create(sDefaultTypeface, 1));
            nativeForceSetStaticFinalField("SANS_SERIF", create(DEFAULT_FAMILY, 0));
            nativeForceSetStaticFinalField("SERIF", create("serif", 0));
            nativeForceSetStaticFinalField("MONOSPACE", create("monospace", 0));
            sDefaults = new Typeface[]{DEFAULT, DEFAULT_BOLD, create((String) null, 2), create((String) null, 3)};
            String[] strArr = {"serif", DEFAULT_FAMILY, "cursive", "fantasy", "monospace", "system-ui"};
            for (int i = 0; i < 6; i++) {
                String str = strArr[i];
                registerGenericFamilyNative(str, map.get(str));
            }
            if (!isMtFontsDirectoryExists) {
                makeMtFontsDirectory();
                isMtFontsDirectoryExists = true;
            }
        }
    }

    public static Pair<List<Typeface>, List<Typeface>> changeDefaultFontForTest(List<Typeface> list, List<Typeface> list2) {
        Pair<List<Typeface>, List<Typeface>> pair;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            List asList = Arrays.asList(sDefaults);
            sDefaults = (Typeface[]) list.toArray(new Typeface[4]);
            setDefault(list.get(0));
            final ArrayList arrayList = new ArrayList();
            BiConsumer biConsumer = new BiConsumer() { // from class: android.graphics.Typeface$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    Typeface.lambda$changeDefaultFontForTest$0(arrayList, (Typeface) obj, (String) obj2);
                }
            };
            Typeface typeface = list2.get(0);
            biConsumer.accept(typeface, DEFAULT_FAMILY);
            biConsumer.accept(create(typeface, 100, false), "sans-serif-thin");
            biConsumer.accept(create(typeface, 300, false), "sans-serif-light");
            biConsumer.accept(create(typeface, 500, false), "sans-serif-medium");
            biConsumer.accept(create(typeface, 700, false), "sans-serif-bold");
            biConsumer.accept(create(typeface, 900, false), "sans-serif-black");
            biConsumer.accept(list2.get(1), "serif");
            biConsumer.accept(list2.get(2), "monospace");
            pair = new Pair<>(asList, arrayList);
        }
        return pair;
    }

    static /* synthetic */ void lambda$changeDefaultFontForTest$0(ArrayList arrayList, Typeface typeface, String str) {
        Map<String, Typeface> map = sSystemFontMap;
        arrayList.add(map.get(str));
        map.put(str, typeface);
    }

    private static void staticInitializer() {
        init();
    }

    public static void init() {
        preloadFontFile(SystemFonts.SYSTEM_FONT_DIR + "Roboto-Regular.ttf");
        preloadFontFile(SystemFonts.SYSTEM_FONT_DIR + "RobotoStatic-Regular.ttf");
        String script = ULocale.addLikelySubtags(ULocale.forLanguageTag(SystemProperties.get("persist.sys.locale", "en-US"))).getScript();
        FontConfig systemPreinstalledFontConfigFromLegacyXml = SystemFonts.getSystemPreinstalledFontConfigFromLegacyXml();
        for (int i = 0; i < systemPreinstalledFontConfigFromLegacyXml.getFontFamilies().size(); i++) {
            FontConfig.FontFamily fontFamily = systemPreinstalledFontConfigFromLegacyXml.getFontFamilies().get(i);
            if (!fontFamily.getLocaleList().isEmpty()) {
                nativeRegisterLocaleList(fontFamily.getLocaleList().toLanguageTags());
            }
            boolean z = false;
            for (int i2 = 0; i2 < fontFamily.getLocaleList().size() && !(z = ULocale.addLikelySubtags(ULocale.forLocale(fontFamily.getLocaleList().get(i2))).getScript().equals(script)); i2++) {
            }
            if (z) {
                for (int i3 = 0; i3 < fontFamily.getFontList().size(); i3++) {
                    preloadFontFile(fontFamily.getFontList().get(i3).getFile().getAbsolutePath());
                }
            }
        }
    }

    private static void preloadFontFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            Log.i(TAG, "Preloading " + file.getAbsolutePath());
            nativeWarmUpCache(str);
        }
    }

    public static void destroySystemFontMap() {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            Iterator<Typeface> it = sSystemFontMap.values().iterator();
            while (it.hasNext()) {
                it.next().releaseNativeObjectForTest();
            }
            sSystemFontMap.clear();
            ByteBuffer byteBuffer = sSystemFontMapBuffer;
            if (byteBuffer != null) {
                SharedMemory.unmap(byteBuffer);
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

    private static void destroyTypefaceCacheLocked(LongSparseArray<SparseArray<Typeface>> longSparseArray) {
        for (int i = 0; i < longSparseArray.size(); i++) {
            SparseArray<Typeface> valueAt = longSparseArray.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                valueAt.valueAt(i2).releaseNativeObjectForTest();
            }
        }
        longSparseArray.clear();
    }

    public static void loadPreinstalledSystemFontMap() {
        FontConfig systemPreinstalledFontConfig = SystemFonts.getSystemPreinstalledFontConfig();
        setSystemFontMap(SystemFonts.buildSystemTypefaces(systemPreinstalledFontConfig, SystemFonts.buildSystemFallback(systemPreinstalledFontConfig)));
    }

    public static void loadNativeSystemFonts() {
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            Iterator<Typeface> it = sSystemFontMap.values().iterator();
            while (it.hasNext()) {
                nativeAddFontCollections(it.next().native_instance);
            }
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Typeface typeface = (Typeface) obj;
            if (this.mStyle == typeface.mStyle && this.native_instance == typeface.native_instance) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        long j = this.native_instance;
        return ((527 + ((int) (j ^ (j >>> 32)))) * 31) + this.mStyle;
    }

    public boolean isSupportedAxes(int i) {
        synchronized (this) {
            if (this.mSupportedAxes == null) {
                int[] nativeGetSupportedAxes = nativeGetSupportedAxes(this.native_instance);
                this.mSupportedAxes = nativeGetSupportedAxes;
                if (nativeGetSupportedAxes == null) {
                    this.mSupportedAxes = EMPTY_AXES;
                }
            }
        }
        return Arrays.binarySearch(this.mSupportedAxes, i) >= 0;
    }

    public static String semGetFontPathOfCurrentFontStyle(Context context, int i) {
        return getFontPathFlipFont();
    }

    public static boolean semIsDefaultFontStyle() {
        return !isFlipFontUsed;
    }

    public static String getFontNameFlipFont() {
        String[] split = getFullFlipFont().split("#");
        if (split.length < 2) {
            if (split[0].endsWith("default")) {
                return "default";
            }
            return null;
        }
        return split[1];
    }

    private static String getFontPathFlipFont() {
        return getFullFlipFont().split("#")[0];
    }

    private static String getFullFlipFont() {
        File file = new File("/data/app_fonts/");
        if (file.isDirectory() && file.list() != null && file.list().length == 0) {
            return "default";
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(OWNER_SANS_LOC_PATH));
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                try {
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    fileInputStream.close();
                    return readLine;
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
            return "default";
        }
    }

    public static void setFlipFonts() {
        String str;
        String str2;
        if (getDefault() == null) {
            return;
        }
        String fontPathFlipFont = getFontPathFlipFont();
        if (fontPathFlipFont.endsWith("default")) {
            isFlipFontUsed = false;
            str2 = "default";
            str = "default";
        } else {
            isFlipFontUsed = true;
            str = fontPathFlipFont + "/DroidSans.ttf";
            str2 = fontPathFlipFont + "/DroidSans-Bold.ttf";
        }
        if (str.equals(FlipFontPath)) {
            return;
        }
        FlipFontPath = str;
        synchronized (SYSTEM_FONT_MAP_LOCK) {
            if (!isFlipFontUsed) {
                nativeSetDefault(sDefaultFlipfont.native_instance);
                DEFAULT.native_instance = nativeCreateFromTypeface(0L, 0);
                DEFAULT_BOLD.native_instance = nativeCreateFromTypeface(0L, 1);
            } else {
                nativeSetDefault(sDefaultFlipfont.native_instance);
                DEFAULT.native_instance = sDefaultFlipfont.native_instance;
                Typeface typeface = fontCache.get(str);
                if (typeface == null) {
                    try {
                        typeface = createFromFile(str);
                    } catch (RuntimeException unused) {
                        DEFAULT.native_instance = create((String) null, 0).native_instance;
                    }
                    Typeface typeface2 = DEFAULT;
                    if (typeface2.native_instance == 0) {
                        typeface = create((String) null, 0);
                        typeface2.native_instance = typeface.native_instance;
                    }
                    if (typeface != null) {
                        fontCache.put(str, typeface);
                    }
                }
                if (typeface != null) {
                    DEFAULT.native_instance = typeface.native_instance;
                }
                Typeface typeface3 = DEFAULT;
                typeface3.mStyle = nativeGetStyle(typeface3.native_instance);
                Typeface typeface4 = fontCache.get(str2);
                if (typeface4 == null) {
                    try {
                        typeface4 = createFromFile(str2);
                    } catch (RuntimeException unused2) {
                        DEFAULT_BOLD.native_instance = create((String) null, 1).native_instance;
                    }
                    if (DEFAULT_BOLD.native_instance == 0) {
                        typeface4 = create((String) null, 1);
                    }
                    if (typeface4 != null) {
                        fontCache.put(str2, typeface4);
                    }
                }
                if (typeface4 != null) {
                    DEFAULT_BOLD.native_instance = typeface4.native_instance;
                }
                Typeface typeface5 = DEFAULT_BOLD;
                typeface5.mStyle = nativeGetStyle(typeface5.native_instance);
                Typeface typeface6 = sDefaults[0];
                Typeface typeface7 = DEFAULT;
                typeface6.native_instance = nativeCreateFromTypefaceWithExactStyle(typeface7.native_instance, 400, false);
                Typeface typeface8 = sDefaults[0];
                typeface8.mStyle = nativeGetStyle(typeface8.native_instance);
                sDefaults[1].native_instance = nativeCreateFromTypefaceWithExactStyle(typeface5.native_instance, 700, false);
                Typeface typeface9 = sDefaults[1];
                typeface9.mStyle = nativeGetStyle(typeface9.native_instance);
                sDefaults[2].native_instance = nativeCreateFromTypefaceWithExactStyle(typeface7.native_instance, 400, true);
                Typeface typeface10 = sDefaults[2];
                typeface10.mStyle = nativeGetStyle(typeface10.native_instance);
                sDefaults[3].native_instance = nativeCreateFromTypefaceWithExactStyle(typeface5.native_instance, 700, true);
                Typeface typeface11 = sDefaults[3];
                typeface11.mStyle = nativeGetStyle(typeface11.native_instance);
                nativeSetDefault(sDefaultTypeface.native_instance);
            }
        }
    }

    private static void makeMtFontsDirectory() {
        File file = new File("/data/app_fonts/");
        if (file.exists()) {
            return;
        }
        if (!file.mkdir()) {
            Log.v(TAG_MONOTYPE, "Cannot make directory : " + file.getAbsolutePath());
        }
        file.setExecutable(true, false);
        if (!file.setReadable(true, false)) {
            Log.v(TAG_MONOTYPE, "Cannot set Readable : " + file.getAbsolutePath());
        }
        if (!file.setWritable(true, false)) {
            Log.v(TAG_MONOTYPE, "Cannot set Writable : " + file.getAbsolutePath());
        }
        Log.v(TAG_MONOTYPE, "makeMtFontsDirectory()");
    }
}
