package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.app.LocaleConfig;
import android.app.ResourcesManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.FontResourcesParser;
import android.content.res.Resources;
import android.content.res.XmlBlock;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.icu.text.PluralRules;
import android.net.Uri;
import android.os.Build;
import android.os.LocaleList;
import android.os.ParcelFileDescriptor;
import android.os.Trace;
import android.provider.CallLog;
import android.sec.enterprise.ApplicationPolicy;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.Slog;
import android.util.TypedValue;
import android.util.Xml;
import android.view.DisplayAdjustments;
import com.android.internal.util.GrowingArrayUtils;
import com.samsung.android.knox.analytics.database.Contract;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Supplier;
import libcore.util.NativeAllocationRegistry;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class ResourcesImpl {
    private static final boolean DEBUG_CONFIG = false;
    private static final boolean DEBUG_LOAD = false;
    private static final int ID_OTHER = 16777220;
    static final String TAG = "Resources";
    private static final boolean TRACE_FOR_MISS_PRELOAD = false;
    private static final boolean TRACE_FOR_PRELOAD = false;
    private static final int XML_BLOCK_CACHE_SIZE = 4;
    private static boolean sPreloaded;
    private final Object mAccessLock;
    private final ConfigurationBoundResourceCache<Animator> mAnimatorCache;
    private final int mAppliedSharedLibsHash;
    final AssetManager mAssets;
    private final int[] mCachedXmlBlockCookies;
    private final String[] mCachedXmlBlockFiles;
    private final XmlBlock[] mCachedXmlBlocks;
    private final DrawableCache mColorDrawableCache;
    private final ConfigurationBoundResourceCache<ComplexColor> mComplexColorCache;
    private final Configuration mConfiguration;
    private final DisplayAdjustments mDisplayAdjustments;
    private final DrawableCache mDrawableCache;
    private int mLastCachedXmlBlockIndex;
    private final ThreadLocal<LookupStack> mLookupStack;
    private final DisplayMetrics mMetrics;
    private PluralRules mPluralRule;
    private boolean mPreloading;
    private final ConfigurationBoundResourceCache<StateListAnimator> mStateListAnimatorCache;
    private final Configuration mTmpConfig;
    private static final Object sSync = new Object();
    private static final LongSparseArray<Drawable.ConstantState> sPreloadedColorDrawables = new LongSparseArray<>();
    private static final LongSparseArray<ConstantState<ComplexColor>> sPreloadedComplexColors = new LongSparseArray<>();
    private static final LongSparseArray<Drawable.ConstantState>[] sPreloadedDrawables = {new LongSparseArray<>(), new LongSparseArray<>()};
    private static final NativeAllocationRegistry sThemeRegistry = NativeAllocationRegistry.createMalloced(ResourcesImpl.class.getClassLoader(), AssetManager.getThemeFreeFunction());

    static /* synthetic */ LookupStack lambda$new$0() {
        return new LookupStack();
    }

    static void resetDrawableStateCache() {
        synchronized (sSync) {
            LongSparseArray<Drawable.ConstantState>[] longSparseArrayArr = sPreloadedDrawables;
            longSparseArrayArr[0].clear();
            longSparseArrayArr[1].clear();
            sPreloadedColorDrawables.clear();
            sPreloadedComplexColors.clear();
            sPreloaded = false;
        }
    }

    public ResourcesImpl(AssetManager assetManager, DisplayMetrics displayMetrics, Configuration configuration, DisplayAdjustments displayAdjustments) {
        this(assetManager, displayMetrics, configuration, displayAdjustments, false);
    }

    public ResourcesImpl(ResourcesImpl resourcesImpl) {
        this(resourcesImpl.getAssets(), resourcesImpl.getMetrics(), resourcesImpl.getConfiguration(), resourcesImpl.getDisplayAdjustments(), false);
    }

    public ResourcesImpl(AssetManager assetManager, DisplayMetrics displayMetrics, Configuration configuration, DisplayAdjustments displayAdjustments, boolean z) throws Throwable {
        this.mAccessLock = new Object();
        this.mTmpConfig = new Configuration();
        this.mDrawableCache = new DrawableCache();
        this.mColorDrawableCache = new DrawableCache();
        this.mComplexColorCache = new ConfigurationBoundResourceCache<ComplexColor>(this) { // from class: android.content.res.ResourcesImpl.1
            @Override // android.content.res.ConfigurationBoundResourceCache, android.content.res.ThemedResourceCache
            public void onConfigurationChange(int i) {
                if ((i & 512) != 0) {
                    clear();
                } else {
                    super.onConfigurationChange(i);
                }
            }
        };
        this.mAnimatorCache = new ConfigurationBoundResourceCache<>();
        this.mStateListAnimatorCache = new ConfigurationBoundResourceCache<>();
        this.mLookupStack = ThreadLocal.withInitial(new Supplier() { // from class: android.content.res.ResourcesImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ResourcesImpl.lambda$new$0();
            }
        });
        this.mLastCachedXmlBlockIndex = -1;
        this.mCachedXmlBlockCookies = new int[4];
        this.mCachedXmlBlockFiles = new String[4];
        this.mCachedXmlBlocks = new XmlBlock[4];
        DisplayMetrics displayMetrics2 = new DisplayMetrics();
        this.mMetrics = displayMetrics2;
        Configuration configuration2 = new Configuration();
        this.mConfiguration = configuration2;
        Pair<AssetManager, Integer> pairUpdateResourceImplAssetsWithRegisteredLibs = ResourcesManager.getInstance().updateResourceImplAssetsWithRegisteredLibs(assetManager, z);
        this.mAssets = pairUpdateResourceImplAssetsWithRegisteredLibs.first;
        this.mAppliedSharedLibsHash = pairUpdateResourceImplAssetsWithRegisteredLibs.second.intValue();
        displayMetrics2.setToDefaults();
        this.mDisplayAdjustments = displayAdjustments;
        configuration2.setToDefaults();
        updateConfigurationImpl(configuration, displayMetrics, displayAdjustments.getCompatibilityInfo(), true);
    }

    public DisplayAdjustments getDisplayAdjustments() {
        return this.mDisplayAdjustments;
    }

    public AssetManager getAssets() {
        return this.mAssets;
    }

    public DisplayMetrics getMetrics() {
        return this.mMetrics;
    }

    DisplayMetrics getDisplayMetrics() {
        return this.mMetrics;
    }

    public Configuration getConfiguration() {
        return this.mConfiguration;
    }

    Configuration[] getSizeConfigurations() {
        return this.mAssets.getSizeConfigurations();
    }

    Configuration[] getSizeAndUiModeConfigurations() {
        return this.mAssets.getSizeAndUiModeConfigurations();
    }

    CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    private PluralRules getPluralRule() {
        PluralRules pluralRules;
        synchronized (sSync) {
            if (this.mPluralRule == null) {
                this.mPluralRule = PluralRules.forLocale(this.mConfiguration.getLocales().get(0));
            }
            pluralRules = this.mPluralRule;
        }
        return pluralRules;
    }

    public void getValue(int i, TypedValue typedValue, boolean z) throws Resources.NotFoundException {
        if (this.mAssets.getResourceValue(i, 0, typedValue, z)) {
            return;
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i));
    }

    void getValueForDensity(int i, int i2, TypedValue typedValue, boolean z) throws Resources.NotFoundException {
        if (this.mAssets.getResourceValue(i, i2, typedValue, z)) {
            return;
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i));
    }

    void getValue(String str, TypedValue typedValue, boolean z) throws Resources.NotFoundException {
        int identifier = getIdentifier(str, "string", null);
        if (identifier != 0) {
            getValue(identifier, typedValue, z);
        } else {
            throw new Resources.NotFoundException("String resource name " + str);
        }
    }

    private static boolean isIntLike(String str) {
        if (str.isEmpty() || str.length() > 10) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < '0' || cCharAt > '9') {
                return false;
            }
        }
        return true;
    }

    int getIdentifier(String str, String str2, String str3) {
        if (str == null) {
            throw new NullPointerException("name is null");
        }
        if (isIntLike(str)) {
            try {
                return Integer.parseInt(str);
            } catch (Exception unused) {
            }
        }
        return this.mAssets.getResourceIdentifier(str, str2, str3);
    }

    String getResourceName(int i) throws Resources.NotFoundException {
        String resourceName = this.mAssets.getResourceName(i);
        if (resourceName != null) {
            return resourceName;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(i));
    }

    String getResourcePackageName(int i) throws Resources.NotFoundException {
        String resourcePackageName = this.mAssets.getResourcePackageName(i);
        if (resourcePackageName != null) {
            return resourcePackageName;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(i));
    }

    String getResourceTypeName(int i) throws Resources.NotFoundException {
        String resourceTypeName = this.mAssets.getResourceTypeName(i);
        if (resourceTypeName != null) {
            return resourceTypeName;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(i));
    }

    String getResourceEntryName(int i) throws Resources.NotFoundException {
        String resourceEntryName = this.mAssets.getResourceEntryName(i);
        if (resourceEntryName != null) {
            return resourceEntryName;
        }
        throw new Resources.NotFoundException("Unable to find resource ID #0x" + Integer.toHexString(i));
    }

    String getLastResourceResolution() throws Resources.NotFoundException {
        String lastResourceResolution = this.mAssets.getLastResourceResolution();
        if (lastResourceResolution != null) {
            return lastResourceResolution;
        }
        throw new Resources.NotFoundException("Associated AssetManager hasn't resolved a resource");
    }

    CharSequence getQuantityText(int i, int i2) throws Resources.NotFoundException {
        PluralRules pluralRule = getPluralRule();
        double d = i2;
        CharSequence resourceBagText = this.mAssets.getResourceBagText(i, attrForQuantityCode(pluralRule.select(d)));
        if (resourceBagText != null) {
            return resourceBagText;
        }
        CharSequence resourceBagText2 = this.mAssets.getResourceBagText(i, ID_OTHER);
        if (resourceBagText2 != null) {
            return resourceBagText2;
        }
        throw new Resources.NotFoundException("Plural resource ID #0x" + Integer.toHexString(i) + " quantity=" + i2 + " item=" + pluralRule.select(d));
    }

    private static int attrForQuantityCode(String str) {
        str.hashCode();
        switch (str) {
            case "few":
                return 16777224;
            case "one":
                return 16777222;
            case "two":
                return 16777223;
            case "many":
                return 16777225;
            case "zero":
                return 16777221;
            default:
                return ID_OTHER;
        }
    }

    AssetFileDescriptor openRawResourceFd(int i, TypedValue typedValue) throws Resources.NotFoundException {
        getValue(i, typedValue, true);
        try {
            return this.mAssets.openNonAssetFd(typedValue.assetCookie, typedValue.string.toString());
        } catch (Exception e) {
            throw new Resources.NotFoundException("File " + typedValue.string.toString() + " from resource ID #0x" + Integer.toHexString(i), e);
        }
    }

    InputStream openRawResource(int i, TypedValue typedValue) throws Resources.NotFoundException {
        getValue(i, typedValue, true);
        try {
            return this.mAssets.openNonAsset(typedValue.assetCookie, typedValue.string.toString(), 2);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("File ");
            sb.append(typedValue.string == null ? "(null)" : typedValue.string.toString());
            sb.append(" from resource ID #0x");
            sb.append(Integer.toHexString(i));
            Resources.NotFoundException notFoundException = new Resources.NotFoundException(sb.toString());
            notFoundException.initCause(e);
            throw notFoundException;
        }
    }

    ConfigurationBoundResourceCache<Animator> getAnimatorCache() {
        return this.mAnimatorCache;
    }

    ConfigurationBoundResourceCache<StateListAnimator> getStateListAnimatorCache() {
        return this.mStateListAnimatorCache;
    }

    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo) {
        updateConfigurationImpl(configuration, displayMetrics, compatibilityInfo, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateConfigurationImpl(Configuration configuration, DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, boolean z) throws Throwable {
        long j;
        String strAdjustLanguageTag;
        int i;
        int i2;
        Locale firstMatchWithEnglishSupported;
        Trace.traceBegin(8192L, "ResourcesImpl#updateConfiguration");
        try {
            synchronized (this.mAccessLock) {
                try {
                    try {
                        if (compatibilityInfo != null) {
                            try {
                                this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                        if (displayMetrics != null) {
                            this.mMetrics.setTo(displayMetrics);
                        }
                        this.mDisplayAdjustments.getCompatibilityInfo().applyToDisplayMetrics(this.mMetrics);
                        int iCalcConfigChanges = calcConfigChanges(configuration);
                        LocaleList locales = this.mConfiguration.getLocales();
                        if (locales.isEmpty()) {
                            locales = LocaleList.getDefault();
                            this.mConfiguration.setLocales(locales);
                        }
                        LocaleConfig localeConfig = ResourcesManager.getInstance().getLocaleConfig();
                        String[] strArr = null;
                        if ((iCalcConfigChanges & 4) == 0 || locales.size() <= 1) {
                            strAdjustLanguageTag = null;
                        } else if (Flags.defaultLocale() && localeConfig.getDefaultLocale() != null) {
                            Locale[] intersection = locales.getIntersection(localeConfig.getSupportedLocales());
                            this.mConfiguration.setLocales(new LocaleList(intersection));
                            strArr = new String[intersection.length];
                            for (int i3 = 0; i3 < intersection.length; i3++) {
                                strArr[i3] = adjustLanguageTag(intersection[i3].toLanguageTag());
                            }
                            strAdjustLanguageTag = adjustLanguageTag(localeConfig.getDefaultLocale().toLanguageTag());
                            Slog.v(TAG, "Updating configuration, with default locale " + strAdjustLanguageTag + " and selected locales " + Arrays.toString(strArr));
                        } else {
                            String[] nonSystemLocales = this.mAssets.getNonSystemLocales();
                            if (LocaleList.isPseudoLocalesOnly(nonSystemLocales)) {
                                nonSystemLocales = this.mAssets.getLocales();
                                if (LocaleList.isPseudoLocalesOnly(nonSystemLocales)) {
                                    nonSystemLocales = null;
                                }
                            }
                            if (nonSystemLocales != null && (firstMatchWithEnglishSupported = locales.getFirstMatchWithEnglishSupported(nonSystemLocales)) != null) {
                                String[] strArr2 = {adjustLanguageTag(firstMatchWithEnglishSupported.toLanguageTag())};
                                if (!firstMatchWithEnglishSupported.equals(locales.get(0))) {
                                    this.mConfiguration.setLocales(new LocaleList(firstMatchWithEnglishSupported, locales));
                                }
                                strAdjustLanguageTag = null;
                                strArr = strArr2;
                            }
                        }
                        if (strArr == null) {
                            if (Flags.defaultLocale() && localeConfig.getDefaultLocale() != null) {
                                strArr = new String[locales.size()];
                                for (int i4 = 0; i4 < locales.size(); i4++) {
                                    strArr[i4] = adjustLanguageTag(locales.get(i4).toLanguageTag());
                                }
                                strAdjustLanguageTag = adjustLanguageTag(localeConfig.getDefaultLocale().toLanguageTag());
                            } else {
                                strArr = new String[]{adjustLanguageTag(locales.get(0).toLanguageTag())};
                            }
                        }
                        String str = strAdjustLanguageTag;
                        String[] strArr3 = strArr;
                        if (this.mConfiguration.densityDpi != 0) {
                            this.mMetrics.densityDpi = this.mConfiguration.densityDpi;
                            this.mMetrics.density = this.mConfiguration.densityDpi * 0.00625f;
                        }
                        DisplayMetrics displayMetrics2 = this.mMetrics;
                        displayMetrics2.scaledDensity = displayMetrics2.density * (this.mConfiguration.fontScale != 0.0f ? this.mConfiguration.fontScale : 1.0f);
                        this.mMetrics.fontScaleConverter = FontScaleConverterFactory.forScale(this.mConfiguration.fontScale);
                        if (this.mMetrics.widthPixels >= this.mMetrics.heightPixels) {
                            i = this.mMetrics.widthPixels;
                            i2 = this.mMetrics.heightPixels;
                        } else {
                            i = this.mMetrics.heightPixels;
                            i2 = this.mMetrics.widthPixels;
                        }
                        j = 8192;
                        this.mAssets.setConfigurationInternal(this.mConfiguration.mcc, this.mConfiguration.mnc, str, strArr3, this.mConfiguration.orientation, this.mConfiguration.touchscreen, this.mConfiguration.densityDpi, this.mConfiguration.keyboard, (this.mConfiguration.keyboardHidden == 1 && this.mConfiguration.hardKeyboardHidden == 2) ? 3 : this.mConfiguration.keyboardHidden, this.mConfiguration.navigation, i, i2, this.mConfiguration.smallestScreenWidthDp, this.mConfiguration.screenWidthDp, this.mConfiguration.screenHeightDp, this.mConfiguration.screenLayout, this.mConfiguration.uiMode, this.mConfiguration.colorMode, this.mConfiguration.getGrammaticalGender(), Build.VERSION.RESOURCES_SDK_INT, z);
                        this.mDrawableCache.onConfigurationChange(iCalcConfigChanges);
                        this.mColorDrawableCache.onConfigurationChange(iCalcConfigChanges);
                        this.mComplexColorCache.onConfigurationChange(iCalcConfigChanges);
                        this.mAnimatorCache.onConfigurationChange(iCalcConfigChanges);
                        this.mStateListAnimatorCache.onConfigurationChange(iCalcConfigChanges);
                        flushLayoutCache();
                        synchronized (sSync) {
                            if (this.mPluralRule != null) {
                                this.mPluralRule = PluralRules.forLocale(this.mConfiguration.getLocales().get(0));
                            }
                        }
                        Trace.traceEnd(j);
                    } catch (Throwable th2) {
                        th = th2;
                        Trace.traceEnd(j);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            j = 8192;
        }
    }

    public int calcConfigChanges(Configuration configuration) {
        if (configuration == null) {
            return -1;
        }
        this.mTmpConfig.setTo(configuration);
        int i = configuration.densityDpi;
        if (i == 0) {
            i = this.mMetrics.noncompatDensityDpi;
        }
        this.mDisplayAdjustments.getCompatibilityInfo().applyToConfiguration(i, this.mTmpConfig);
        if (this.mTmpConfig.getLocales().isEmpty()) {
            this.mTmpConfig.setLocales(LocaleList.getDefault());
        }
        return this.mConfiguration.updateFrom(this.mTmpConfig);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String adjustLanguageTag(String str) {
        String strSubstring;
        int iIndexOf = str.indexOf(45);
        char c = 0;
        if (iIndexOf == -1) {
            strSubstring = "";
        } else {
            String strSubstring2 = str.substring(0, iIndexOf);
            strSubstring = str.substring(iIndexOf);
            str = strSubstring2;
        }
        str.hashCode();
        switch (str.hashCode()) {
            case 3325:
                if (!str.equals("he")) {
                    c = 65535;
                    break;
                }
                break;
            case 3355:
                if (str.equals("id")) {
                    c = 1;
                    break;
                }
                break;
            case 3856:
                if (str.equals("yi")) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                str = "iw";
                break;
            case 1:
                str = "in";
                break;
            case 2:
                str = "ji";
                break;
        }
        return str + strSubstring;
    }

    public void flushLayoutCache() {
        synchronized (this.mCachedXmlBlocks) {
            Arrays.fill(this.mCachedXmlBlockCookies, 0);
            Arrays.fill(this.mCachedXmlBlockFiles, (Object) null);
            XmlBlock[] xmlBlockArr = this.mCachedXmlBlocks;
            for (int i = 0; i < 4; i++) {
                XmlBlock xmlBlock = xmlBlockArr[i];
                if (xmlBlock != null) {
                    xmlBlock.close();
                }
            }
            Arrays.fill(xmlBlockArr, (Object) null);
        }
    }

    public void clearAllCaches() {
        synchronized (this.mAccessLock) {
            this.mDrawableCache.clear();
            this.mColorDrawableCache.clear();
            this.mComplexColorCache.clear();
            this.mAnimatorCache.clear();
            this.mStateListAnimatorCache.clear();
            flushLayoutCache();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x0138  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    Drawable loadDrawable(Resources resources, TypedValue typedValue, int i, int i2, Resources.Theme theme) throws Resources.NotFoundException {
        String resourceName;
        byte[] applicationIconFromDb;
        DrawableCache drawableCache;
        long j;
        boolean z;
        Drawable drawable;
        Drawable.ConstantState constantState;
        Drawable drawableCache2;
        boolean z2 = true;
        boolean z3 = i2 == 0 || typedValue.density == this.mMetrics.densityDpi;
        if (i2 > 0 && typedValue.density > 0 && typedValue.density != 65535) {
            if (typedValue.density == i2) {
                typedValue.density = this.mMetrics.densityDpi;
            } else {
                typedValue.density = (typedValue.density * this.mMetrics.densityDpi) / i2;
            }
        }
        if (resources != null) {
            try {
                if (i == resources.mAppIconResId && resources.mPackageName != null) {
                    try {
                        ApplicationPolicy applicationPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
                        if (applicationPolicy != null && (applicationIconFromDb = applicationPolicy.getApplicationIconFromDb(resources.mPackageName, resources.mUserId)) != null) {
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(applicationIconFromDb);
                            TypedValue typedValue2 = new TypedValue();
                            typedValue2.density = getDisplayMetrics().densityDpi;
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inTargetDensity = getDisplayMetrics().densityDpi;
                            Drawable drawableCreateFromResourceStream = Drawable.createFromResourceStream(resources, typedValue2, byteArrayInputStream, null, options);
                            Log.i(TAG, "loadDrawable() : EDM get Icon from DB : " + resources.mPackageName);
                            return drawableCreateFromResourceStream;
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "loadDrawable() : EDM failed to get Icon", e);
                    }
                }
            } catch (Exception e2) {
                try {
                    resourceName = getResourceName(i);
                } catch (Resources.NotFoundException unused) {
                    resourceName = "(missing name)";
                }
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Drawable " + resourceName + " with resource ID #0x" + Integer.toHexString(i), e2);
                notFoundException.setStackTrace(new StackTraceElement[0]);
                throw notFoundException;
            }
        }
        if (typedValue.type < 28 || typedValue.type > 31) {
            drawableCache = this.mDrawableCache;
            j = (typedValue.assetCookie << 32) | typedValue.data;
            z = false;
        } else {
            drawableCache = this.mColorDrawableCache;
            j = typedValue.data;
            z = true;
        }
        DrawableCache drawableCache3 = drawableCache;
        int generation = drawableCache3.getGeneration();
        if (!this.mPreloading && z3 && (drawableCache2 = drawableCache3.getInstance(j, resources, theme)) != null) {
            drawableCache2.setChangingConfigurations(typedValue.changingConfigurations);
            return drawableCache2;
        }
        Drawable.ConstantState constantState2 = z ? sPreloadedColorDrawables.get(j) : sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].get(j);
        Drawable drawableNewDrawable = constantState2 != null ? constantState2.newDrawable(resources) : z ? new ColorDrawable(typedValue.data) : loadDrawableForCookie(resources, typedValue, i, i2);
        boolean z4 = drawableNewDrawable instanceof DrawableContainer;
        if (drawableNewDrawable == null || !drawableNewDrawable.canApplyTheme()) {
            z2 = false;
        }
        if (z2 && theme != null) {
            drawableNewDrawable = drawableNewDrawable.mutate();
            drawableNewDrawable.applyTheme(theme);
            drawableNewDrawable.clearMutated();
        }
        if (drawableNewDrawable != null) {
            drawableNewDrawable.setChangingConfigurations(typedValue.changingConfigurations);
            if (z3) {
                drawable = drawableNewDrawable;
                cacheDrawable(typedValue, z, drawableCache3, theme, z2, j, drawable, generation);
                if (z4 && (constantState = drawable.getConstantState()) != null) {
                    return constantState.newDrawable(resources);
                }
            } else {
                drawable = drawableNewDrawable;
            }
        }
        return drawable;
    }

    private void cacheDrawable(TypedValue typedValue, boolean z, DrawableCache drawableCache, Resources.Theme theme, boolean z2, long j, Drawable drawable, int i) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return;
        }
        if (this.mPreloading) {
            int changingConfigurations = constantState.getChangingConfigurations();
            if (z) {
                if (verifyPreloadConfig(changingConfigurations, 0, typedValue.resourceId, "drawable")) {
                    sPreloadedColorDrawables.put(j, constantState);
                    return;
                }
                return;
            } else {
                if (verifyPreloadConfig(changingConfigurations, 8192, typedValue.resourceId, "drawable")) {
                    if ((changingConfigurations & 8192) == 0) {
                        LongSparseArray<Drawable.ConstantState>[] longSparseArrayArr = sPreloadedDrawables;
                        longSparseArrayArr[0].put(j, constantState);
                        longSparseArrayArr[1].put(j, constantState);
                        return;
                    }
                    sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].put(j, constantState);
                    return;
                }
                return;
            }
        }
        synchronized (this.mAccessLock) {
            drawableCache.put(j, theme, constantState, i, z2);
        }
    }

    private boolean verifyPreloadConfig(int i, int i2, int i3, String str) {
        String resourceName;
        if ((i & (-1073745921) & (~i2)) == 0) {
            return true;
        }
        try {
            resourceName = getResourceName(i3);
        } catch (Resources.NotFoundException unused) {
            resourceName = "?";
        }
        Log.w(TAG, "Preloaded " + str + " resource #0x" + Integer.toHexString(i3) + " (" + resourceName + ") that varies with configuration!!");
        return false;
    }

    private Drawable decodeImageDrawable(AssetManager.AssetInputStream assetInputStream, Resources resources, TypedValue typedValue) {
        try {
            return ImageDecoder.decodeDrawable(new ImageDecoder.AssetInputStreamSource(assetInputStream, resources, typedValue), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.content.res.ResourcesImpl$$ExternalSyntheticLambda2
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (IOException unused) {
            return null;
        }
    }

    private Drawable decodeImageDrawable(FileInputStream fileInputStream, Resources resources) {
        try {
            return ImageDecoder.decodeDrawable(ImageDecoder.createSource(resources, fileInputStream), new ImageDecoder.OnHeaderDecodedListener() { // from class: android.content.res.ResourcesImpl$$ExternalSyntheticLambda0
                @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                    imageDecoder.setAllocator(1);
                }
            });
        } catch (IOException unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b2  */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.res.ResourcesImpl] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Drawable loadDrawableForCookie(Resources resources, TypedValue typedValue, int i, int i2) throws Exception {
        Drawable drawableDecodeImageDrawable;
        String str;
        byte[] applicationIconFromDb;
        String str2 = this;
        if (typedValue.string == null) {
            throw new Resources.NotFoundException("Resource \"" + str2.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Drawable (color or path): " + typedValue);
        }
        String string = typedValue.string.toString();
        if (resources != null && i == resources.mAppIconResId && resources.mPackageName != null) {
            try {
                ApplicationPolicy applicationPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
                if (applicationPolicy != null && (applicationIconFromDb = applicationPolicy.getApplicationIconFromDb(resources.mPackageName, resources.mUserId)) != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(applicationIconFromDb);
                    TypedValue typedValue2 = new TypedValue();
                    typedValue2.density = str2.getDisplayMetrics().densityDpi;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inTargetDensity = str2.getDisplayMetrics().densityDpi;
                    Drawable drawableCreateFromResourceStream = Drawable.createFromResourceStream(resources, typedValue2, byteArrayInputStream, null, options);
                    Log.i(TAG, "loadDrawable() : EDM get Icon from DB : " + resources.mPackageName);
                    return drawableCreateFromResourceStream;
                }
            } catch (Exception e) {
                Log.e(TAG, "loadDrawable() : EDM failed to get Icon", e);
            }
        }
        Trace.traceBegin(8192L, string);
        LookupStack lookupStack = str2.mLookupStack.get();
        try {
        } catch (Exception | StackOverflowError e2) {
            e = e2;
            str2 = string;
        }
        try {
            if (lookupStack.contains(i)) {
                throw new Exception("Recursive reference in drawable");
            }
            lookupStack.push(i);
            try {
            } catch (Throwable th) {
                th = th;
            }
            if (string.endsWith(".xml")) {
                String resourceTypeName = str2.getResourceTypeName(i);
                if (resourceTypeName != null) {
                    try {
                        if (resourceTypeName.equals("color")) {
                            str = string;
                            try {
                                drawableDecodeImageDrawable = str2.loadColorOrXmlDrawable(resources, typedValue, i, i2, str);
                            } catch (Throwable th2) {
                                th = th2;
                                lookupStack.pop();
                                throw th;
                            }
                        } else {
                            str = string;
                            try {
                                drawableDecodeImageDrawable = loadXmlDrawable(resources, typedValue, i, i2, str);
                            } catch (Throwable th3) {
                                th = th3;
                                lookupStack.pop();
                                throw th;
                            }
                        }
                        lookupStack.pop();
                        Trace.traceEnd(8192L);
                        return drawableDecodeImageDrawable;
                    } catch (Throwable th4) {
                        th = th4;
                        str = string;
                    }
                }
                lookupStack.pop();
                throw th;
            }
            try {
                if (string.startsWith("frro:/")) {
                    Uri uri = Uri.parse(string);
                    long j = Long.parseLong(uri.getQueryParameter(CallLog.Calls.OFFSET_PARAM_KEY));
                    long j2 = Long.parseLong(uri.getQueryParameter(Contract.DatabaseSize.PATH));
                    if (j < 0 || j2 <= 0) {
                        throw new Resources.NotFoundException("invalid frro parameters");
                    }
                    File file = new File("/" + uri.getHost() + uri.getPath());
                    if (!file.getCanonicalPath().startsWith(ResourcesManager.RESOURCE_CACHE_DIR) || !file.getCanonicalPath().endsWith(".frro") || !file.canRead()) {
                        throw new Resources.NotFoundException("invalid frro path");
                    }
                    drawableDecodeImageDrawable = str2.decodeImageDrawable(new AssetFileDescriptor(ParcelFileDescriptor.open(file, 268435456), j, j2).createInputStream(), resources);
                } else {
                    InputStream inputStreamOpenNonAsset = str2.mAssets.openNonAsset(typedValue.assetCookie, string, 2);
                    if (string.endsWith(".bmp") || string.endsWith(".spr")) {
                        Drawable drawableCreateFromResourceStream2 = Drawable.createFromResourceStream(resources, typedValue, inputStreamOpenNonAsset, string, null);
                        inputStreamOpenNonAsset.close();
                        drawableDecodeImageDrawable = drawableCreateFromResourceStream2;
                    } else {
                        drawableDecodeImageDrawable = str2.decodeImageDrawable((AssetManager.AssetInputStream) inputStreamOpenNonAsset, resources, typedValue);
                    }
                }
                lookupStack.pop();
                Trace.traceEnd(8192L);
                return drawableDecodeImageDrawable;
            } catch (Throwable th5) {
                th = th5;
            }
        } catch (Exception | StackOverflowError e3) {
            e = e3;
            Trace.traceEnd(8192L);
            Resources.NotFoundException notFoundException = new Resources.NotFoundException("File " + str2 + " from drawable resource ID #0x" + Integer.toHexString(i));
            notFoundException.initCause(e);
            throw notFoundException;
        }
    }

    private Drawable loadColorOrXmlDrawable(Resources resources, TypedValue typedValue, int i, int i2, String str) {
        try {
            return new ColorStateListDrawable(loadColorStateList(resources, typedValue, i, null));
        } catch (Resources.NotFoundException e) {
            try {
                return loadXmlDrawable(resources, typedValue, i, i2, str);
            } catch (Exception unused) {
                throw e;
            }
        }
    }

    private Drawable loadXmlDrawable(Resources resources, TypedValue typedValue, int i, int i2, String str) throws XmlPullParserException, Resources.NotFoundException, IOException {
        XmlResourceParser xmlResourceParserLoadXmlResourceParser = loadXmlResourceParser(str, i, typedValue.assetCookie, "drawable", typedValue.usesFeatureFlags);
        try {
            Drawable drawableCreateFromXmlForDensity = Drawable.createFromXmlForDensity(resources, xmlResourceParserLoadXmlResourceParser, i2, null);
            if (xmlResourceParserLoadXmlResourceParser != null) {
                xmlResourceParserLoadXmlResourceParser.close();
            }
            return drawableCreateFromXmlForDensity;
        } catch (Throwable th) {
            if (xmlResourceParserLoadXmlResourceParser == null) {
                throw th;
            }
            try {
                xmlResourceParserLoadXmlResourceParser.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    public Typeface loadFont(Resources resources, TypedValue typedValue, int i) {
        String strEndsWith;
        if (typedValue.string == null) {
            throw new Resources.NotFoundException("Resource \"" + getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String string = typedValue.string.toString();
        if (!string.startsWith("res/")) {
            return null;
        }
        Typeface typefaceFindFromCache = Typeface.findFromCache(this.mAssets, string);
        if (typefaceFindFromCache != null) {
            return typefaceFindFromCache;
        }
        Trace.traceBegin(8192L, string);
        try {
            try {
                strEndsWith = string.endsWith("xml");
                try {
                    if (strEndsWith == 0) {
                        return new Typeface.Builder(this.mAssets, string, false, typedValue.assetCookie).build();
                    }
                    try {
                        FontResourcesParser.FamilyResourceEntry familyResourceEntry = FontResourcesParser.parse(loadXmlResourceParser(string, i, typedValue.assetCookie, Context.FONT_SERVICE, typedValue.usesFeatureFlags), resources);
                        if (familyResourceEntry == null) {
                            return null;
                        }
                        return Typeface.createFromResources(familyResourceEntry, this.mAssets, string);
                    } catch (IOException e) {
                        e = e;
                        strEndsWith = string;
                        Log.e(TAG, "Failed to read xml resource " + strEndsWith, e);
                        return null;
                    } catch (XmlPullParserException e2) {
                        e = e2;
                        strEndsWith = string;
                        Log.e(TAG, "Failed to parse xml resource " + strEndsWith, e);
                        return null;
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (XmlPullParserException e4) {
                    e = e4;
                }
            } finally {
                Trace.traceEnd(8192L);
            }
        } catch (IOException e5) {
            e = e5;
            strEndsWith = string;
        } catch (XmlPullParserException e6) {
            e = e6;
            strEndsWith = string;
        }
    }

    private ComplexColor loadComplexColorFromName(Resources resources, Resources.Theme theme, TypedValue typedValue, int i) throws XmlPullParserException {
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        ConfigurationBoundResourceCache<ComplexColor> configurationBoundResourceCache = this.mComplexColorCache;
        ComplexColor configurationBoundResourceCache2 = configurationBoundResourceCache.getInstance(j, resources, theme);
        if (configurationBoundResourceCache2 != null) {
            return configurationBoundResourceCache2;
        }
        int generation = configurationBoundResourceCache.getGeneration();
        LongSparseArray<ConstantState<ComplexColor>> longSparseArray = sPreloadedComplexColors;
        ConstantState<ComplexColor> constantState = longSparseArray.get(j);
        if (constantState != null) {
            configurationBoundResourceCache2 = constantState.newInstance2(resources, theme);
        }
        if (configurationBoundResourceCache2 == null) {
            configurationBoundResourceCache2 = loadComplexColorForCookie(resources, typedValue, i, theme);
        }
        if (configurationBoundResourceCache2 != null) {
            configurationBoundResourceCache2.setBaseChangingConfigurations(typedValue.changingConfigurations);
            if (this.mPreloading) {
                if (verifyPreloadConfig(configurationBoundResourceCache2.getChangingConfigurations(), 0, typedValue.resourceId, "color")) {
                    longSparseArray.put(j, configurationBoundResourceCache2.getConstantState());
                    return configurationBoundResourceCache2;
                }
            } else {
                configurationBoundResourceCache.put(j, theme, configurationBoundResourceCache2.getConstantState(), generation);
            }
        }
        return configurationBoundResourceCache2;
    }

    ComplexColor loadComplexColor(Resources resources, TypedValue typedValue, int i, Resources.Theme theme) {
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return getColorStateListFromInt(typedValue, j);
        }
        String string = typedValue.string.toString();
        if (string.endsWith(".xml")) {
            try {
                return loadComplexColorFromName(resources, theme, typedValue, i);
            } catch (Exception e) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("File " + string + " from complex color resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        }
        throw new Resources.NotFoundException("File " + string + " from drawable resource ID #0x" + Integer.toHexString(i) + ": .xml extension required");
    }

    ColorStateList loadColorStateList(Resources resources, TypedValue typedValue, int i, Resources.Theme theme) throws XmlPullParserException, Resources.NotFoundException {
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return getColorStateListFromInt(typedValue, j);
        }
        ComplexColor complexColorLoadComplexColorFromName = loadComplexColorFromName(resources, theme, typedValue, i);
        if (complexColorLoadComplexColorFromName != null && (complexColorLoadComplexColorFromName instanceof ColorStateList)) {
            return (ColorStateList) complexColorLoadComplexColorFromName;
        }
        throw new Resources.NotFoundException("Can't find ColorStateList from drawable resource ID #0x" + Integer.toHexString(i));
    }

    private ColorStateList getColorStateListFromInt(TypedValue typedValue, long j) {
        LongSparseArray<ConstantState<ComplexColor>> longSparseArray = sPreloadedComplexColors;
        ConstantState<ComplexColor> constantState = longSparseArray.get(j);
        if (constantState != null) {
            return (ColorStateList) constantState.newInstance2();
        }
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(typedValue.data);
        if (this.mPreloading && verifyPreloadConfig(typedValue.changingConfigurations, 0, typedValue.resourceId, "color")) {
            longSparseArray.put(j, colorStateListValueOf.getConstantState());
        }
        return colorStateListValueOf;
    }

    private ComplexColor loadComplexColorForCookie(Resources resources, TypedValue typedValue, int i, Resources.Theme theme) throws XmlPullParserException {
        int i2;
        int next;
        ComplexColor complexColorCreateFromXmlInner;
        if (typedValue.string == null) {
            throw new UnsupportedOperationException("Can't convert to ComplexColor: type=0x" + typedValue.type);
        }
        String string = typedValue.string.toString();
        Trace.traceBegin(8192L, string);
        if (string.endsWith(".xml")) {
            try {
                i2 = i;
            } catch (Exception e) {
                e = e;
                i2 = i;
            }
            try {
                XmlResourceParser xmlResourceParserLoadXmlResourceParser = loadXmlResourceParser(string, i2, typedValue.assetCookie, "ComplexColor", typedValue.usesFeatureFlags);
                AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParserLoadXmlResourceParser);
                do {
                    next = xmlResourceParserLoadXmlResourceParser.next();
                    if (next == 2) {
                        break;
                    }
                } while (next != 1);
                if (next != 2) {
                    throw new XmlPullParserException("No start tag found");
                }
                String name = xmlResourceParserLoadXmlResourceParser.getName();
                if (name.equals("gradient")) {
                    complexColorCreateFromXmlInner = GradientColor.createFromXmlInner(resources, xmlResourceParserLoadXmlResourceParser, attributeSetAsAttributeSet, theme);
                } else {
                    complexColorCreateFromXmlInner = name.equals("selector") ? ColorStateList.createFromXmlInner(resources, xmlResourceParserLoadXmlResourceParser, attributeSetAsAttributeSet, theme) : null;
                }
                xmlResourceParserLoadXmlResourceParser.close();
                Trace.traceEnd(8192L);
                return complexColorCreateFromXmlInner;
            } catch (Exception e2) {
                e = e2;
                Trace.traceEnd(8192L);
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("File " + string + " from ComplexColor resource ID #0x" + Integer.toHexString(i2));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        }
        Trace.traceEnd(8192L);
        throw new Resources.NotFoundException("File " + string + " from drawable resource ID #0x" + Integer.toHexString(i) + ": .xml extension required");
    }

    XmlResourceParser loadXmlResourceParser(String str, int i, int i2, String str2, boolean z) throws Resources.NotFoundException {
        String str3;
        if (i != 0) {
            try {
                synchronized (this.mCachedXmlBlocks) {
                    int[] iArr = this.mCachedXmlBlockCookies;
                    String[] strArr = this.mCachedXmlBlockFiles;
                    XmlBlock[] xmlBlockArr = this.mCachedXmlBlocks;
                    int length = strArr.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        if (iArr[i3] == i2 && (str3 = strArr[i3]) != null && str3.equals(str)) {
                            return xmlBlockArr[i3].newParser(i);
                        }
                    }
                    XmlBlock xmlBlockOpenXmlBlockAsset = this.mAssets.openXmlBlockAsset(i2, str, z);
                    if (xmlBlockOpenXmlBlockAsset != null) {
                        int i4 = (this.mLastCachedXmlBlockIndex + 1) % length;
                        this.mLastCachedXmlBlockIndex = i4;
                        XmlBlock xmlBlock = xmlBlockArr[i4];
                        if (xmlBlock != null) {
                            xmlBlock.close();
                        }
                        iArr[i4] = i2;
                        strArr[i4] = str;
                        xmlBlockArr[i4] = xmlBlockOpenXmlBlockAsset;
                        return xmlBlockOpenXmlBlockAsset.newParser(i);
                    }
                }
            } catch (Exception e) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("File " + str + " from xml type " + str2 + " resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        }
        throw new Resources.NotFoundException("File " + str + " from xml type " + str2 + " resource ID #0x" + Integer.toHexString(i));
    }

    public final void startPreloading() {
        synchronized (sSync) {
            if (sPreloaded) {
                throw new IllegalStateException("Resources already preloaded");
            }
            sPreloaded = true;
            this.mPreloading = true;
            this.mConfiguration.densityDpi = DisplayMetrics.DENSITY_DEVICE;
            updateConfiguration(null, null, null);
        }
    }

    void finishPreloading() {
        if (this.mPreloading) {
            this.mPreloading = false;
            flushLayoutCache();
        }
    }

    static int getAttributeSetSourceResId(AttributeSet attributeSet) {
        if (attributeSet == null || !(attributeSet instanceof XmlBlock.Parser)) {
            return 0;
        }
        return ((XmlBlock.Parser) attributeSet).getSourceResId();
    }

    LongSparseArray<Drawable.ConstantState> getPreloadedDrawables() {
        return sPreloadedDrawables[0];
    }

    ThemeImpl newThemeImpl() {
        return new ThemeImpl();
    }

    void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "class=" + getClass());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("assets");
        printWriter.println(sb.toString());
        this.mAssets.dump(printWriter, str + "  ");
    }

    public class ThemeImpl {
        private AssetManager mAssets;
        private final long mTheme;
        private final Resources.ThemeKey mKey = new Resources.ThemeKey();
        private int mThemeResId = 0;

        ThemeImpl() {
            AssetManager assetManager = ResourcesImpl.this.mAssets;
            this.mAssets = assetManager;
            long jCreateTheme = assetManager.createTheme();
            this.mTheme = jCreateTheme;
            ResourcesImpl.sThemeRegistry.registerNativeAllocation(this, jCreateTheme);
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.mAssets.releaseTheme(this.mTheme);
        }

        Resources.ThemeKey getKey() {
            return this.mKey;
        }

        long getNativeTheme() {
            return this.mTheme;
        }

        int getAppliedStyleResId() {
            return this.mThemeResId;
        }

        int getParentThemeIdentifier(int i) {
            if (i > 0) {
                return this.mAssets.getParentThemeIdentifier(i);
            }
            return 0;
        }

        void applyStyle(int i, boolean z) {
            this.mAssets.applyStyleToTheme(this.mTheme, i, z);
            this.mThemeResId = i;
            this.mKey.append(i, z);
        }

        void setTo(ThemeImpl themeImpl) {
            this.mAssets.setThemeTo(this.mTheme, themeImpl.mAssets, themeImpl.mTheme);
            this.mThemeResId = themeImpl.mThemeResId;
            this.mKey.setTo(themeImpl.getKey());
        }

        TypedArray obtainStyledAttributes(Resources.Theme theme, AttributeSet attributeSet, int[] iArr, int i, int i2) {
            TypedArray typedArrayObtain = TypedArray.obtain(theme.getResources(), iArr.length);
            XmlBlock.Parser parser = (XmlBlock.Parser) attributeSet;
            this.mAssets.applyStyle(this.mTheme, i, i2, parser, iArr, typedArrayObtain.mDataAddress, typedArrayObtain.mIndicesAddress);
            typedArrayObtain.mTheme = theme;
            typedArrayObtain.mXml = parser;
            return typedArrayObtain;
        }

        TypedArray resolveAttributes(Resources.Theme theme, int[] iArr, int[] iArr2) {
            int length = iArr2.length;
            if (iArr == null || length != iArr.length) {
                throw new IllegalArgumentException("Base attribute values must the same length as attrs");
            }
            TypedArray typedArrayObtain = TypedArray.obtain(theme.getResources(), length);
            this.mAssets.resolveAttrs(this.mTheme, 0, 0, iArr, iArr2, typedArrayObtain.mData, typedArrayObtain.mIndices);
            typedArrayObtain.mTheme = theme;
            typedArrayObtain.mXml = null;
            return typedArrayObtain;
        }

        boolean resolveAttribute(int i, TypedValue typedValue, boolean z) {
            return this.mAssets.getThemeValue(this.mTheme, i, typedValue, z);
        }

        int[] getAllAttributes() {
            return this.mAssets.getStyleAttributes(getAppliedStyleResId());
        }

        int getChangingConfigurations() {
            return ActivityInfo.activityInfoConfigNativeToJava(AssetManager.nativeThemeGetChangingConfigurations(this.mTheme));
        }

        public void dump(int i, String str, String str2) {
            this.mAssets.dumpTheme(this.mTheme, i, str, str2);
        }

        String[] getTheme() {
            int i = this.mKey.mCount;
            int i2 = i * 2;
            String[] strArr = new String[i2];
            int i3 = i - 1;
            int i4 = 0;
            while (i4 < i2) {
                int i5 = this.mKey.mResId[i3];
                boolean z = this.mKey.mForce[i3];
                try {
                    strArr[i4] = ResourcesImpl.this.getResourceName(i5);
                } catch (Resources.NotFoundException unused) {
                    strArr[i4] = Integer.toHexString(i4);
                }
                strArr[i4 + 1] = z ? "forced" : "not forced";
                i4 += 2;
                i3--;
            }
            return strArr;
        }

        void rebase() {
            rebase(this.mAssets);
        }

        void rebase(AssetManager assetManager) {
            this.mAssets = this.mAssets.rebaseTheme(this.mTheme, assetManager, this.mKey.mResId, this.mKey.mForce, this.mKey.mCount);
        }

        public int[] getAttributeResolutionStack(int i, int i2, int i3) {
            return this.mAssets.getAttributeResolutionStack(this.mTheme, i, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LookupStack {
        private int[] mIds;
        private int mSize;

        private LookupStack() {
            this.mIds = new int[4];
            this.mSize = 0;
        }

        public void push(int i) {
            this.mIds = GrowingArrayUtils.append(this.mIds, this.mSize, i);
            this.mSize++;
        }

        public boolean contains(int i) {
            for (int i2 = 0; i2 < this.mSize; i2++) {
                if (this.mIds[i2] == i) {
                    return true;
                }
            }
            return false;
        }

        public void pop() {
            this.mSize--;
        }
    }

    public int getAppliedSharedLibsHash() {
        return this.mAppliedSharedLibsHash;
    }
}
