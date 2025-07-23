package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
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

    public ResourcesImpl(AssetManager assetManager, DisplayMetrics displayMetrics, Configuration configuration, DisplayAdjustments displayAdjustments, boolean z) {
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
        Pair<AssetManager, Integer> updateResourceImplAssetsWithRegisteredLibs = ResourcesManager.getInstance().updateResourceImplAssetsWithRegisteredLibs(assetManager, z);
        this.mAssets = updateResourceImplAssetsWithRegisteredLibs.first;
        this.mAppliedSharedLibsHash = updateResourceImplAssetsWithRegisteredLibs.second.intValue();
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
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x0107 A[Catch: all -> 0x0018, TryCatch #2 {all -> 0x0018, blocks: (B:93:0x0012, B:9:0x001f, B:10:0x0024, B:12:0x003f, B:13:0x0048, B:15:0x0057, B:17:0x005d, B:19:0x0063, B:21:0x0069, B:22:0x007f, B:24:0x0082, B:26:0x0091, B:28:0x0107, B:30:0x010d, B:32:0x0113, B:33:0x011a, B:35:0x0120, B:37:0x0131, B:38:0x013e, B:39:0x014e, B:41:0x0156, B:42:0x016b, B:44:0x0178, B:45:0x017f, B:47:0x0198, B:48:0x01a9, B:50:0x01b3, B:53:0x01c0, B:76:0x01bc, B:77:0x01a1, B:79:0x00c0, B:81:0x00cc, B:85:0x00db, B:87:0x00e1, B:89:0x00f7), top: B:92:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0156 A[Catch: all -> 0x0018, TryCatch #2 {all -> 0x0018, blocks: (B:93:0x0012, B:9:0x001f, B:10:0x0024, B:12:0x003f, B:13:0x0048, B:15:0x0057, B:17:0x005d, B:19:0x0063, B:21:0x0069, B:22:0x007f, B:24:0x0082, B:26:0x0091, B:28:0x0107, B:30:0x010d, B:32:0x0113, B:33:0x011a, B:35:0x0120, B:37:0x0131, B:38:0x013e, B:39:0x014e, B:41:0x0156, B:42:0x016b, B:44:0x0178, B:45:0x017f, B:47:0x0198, B:48:0x01a9, B:50:0x01b3, B:53:0x01c0, B:76:0x01bc, B:77:0x01a1, B:79:0x00c0, B:81:0x00cc, B:85:0x00db, B:87:0x00e1, B:89:0x00f7), top: B:92:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0178 A[Catch: all -> 0x0018, TryCatch #2 {all -> 0x0018, blocks: (B:93:0x0012, B:9:0x001f, B:10:0x0024, B:12:0x003f, B:13:0x0048, B:15:0x0057, B:17:0x005d, B:19:0x0063, B:21:0x0069, B:22:0x007f, B:24:0x0082, B:26:0x0091, B:28:0x0107, B:30:0x010d, B:32:0x0113, B:33:0x011a, B:35:0x0120, B:37:0x0131, B:38:0x013e, B:39:0x014e, B:41:0x0156, B:42:0x016b, B:44:0x0178, B:45:0x017f, B:47:0x0198, B:48:0x01a9, B:50:0x01b3, B:53:0x01c0, B:76:0x01bc, B:77:0x01a1, B:79:0x00c0, B:81:0x00cc, B:85:0x00db, B:87:0x00e1, B:89:0x00f7), top: B:92:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0198 A[Catch: all -> 0x0018, TryCatch #2 {all -> 0x0018, blocks: (B:93:0x0012, B:9:0x001f, B:10:0x0024, B:12:0x003f, B:13:0x0048, B:15:0x0057, B:17:0x005d, B:19:0x0063, B:21:0x0069, B:22:0x007f, B:24:0x0082, B:26:0x0091, B:28:0x0107, B:30:0x010d, B:32:0x0113, B:33:0x011a, B:35:0x0120, B:37:0x0131, B:38:0x013e, B:39:0x014e, B:41:0x0156, B:42:0x016b, B:44:0x0178, B:45:0x017f, B:47:0x0198, B:48:0x01a9, B:50:0x01b3, B:53:0x01c0, B:76:0x01bc, B:77:0x01a1, B:79:0x00c0, B:81:0x00cc, B:85:0x00db, B:87:0x00e1, B:89:0x00f7), top: B:92:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x023d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01a1 A[Catch: all -> 0x0018, TryCatch #2 {all -> 0x0018, blocks: (B:93:0x0012, B:9:0x001f, B:10:0x0024, B:12:0x003f, B:13:0x0048, B:15:0x0057, B:17:0x005d, B:19:0x0063, B:21:0x0069, B:22:0x007f, B:24:0x0082, B:26:0x0091, B:28:0x0107, B:30:0x010d, B:32:0x0113, B:33:0x011a, B:35:0x0120, B:37:0x0131, B:38:0x013e, B:39:0x014e, B:41:0x0156, B:42:0x016b, B:44:0x0178, B:45:0x017f, B:47:0x0198, B:48:0x01a9, B:50:0x01b3, B:53:0x01c0, B:76:0x01bc, B:77:0x01a1, B:79:0x00c0, B:81:0x00cc, B:85:0x00db, B:87:0x00e1, B:89:0x00f7), top: B:92:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x017d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateConfigurationImpl(android.content.res.Configuration r36, android.util.DisplayMetrics r37, android.content.res.CompatibilityInfo r38, boolean r39) {
        /*
            Method dump skipped, instructions count: 615
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ResourcesImpl.updateConfigurationImpl(android.content.res.Configuration, android.util.DisplayMetrics, android.content.res.CompatibilityInfo, boolean):void");
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
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003f, code lost:
    
        if (r4.equals("he") == false) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String adjustLanguageTag(java.lang.String r4) {
        /*
            r0 = 45
            int r0 = r4.indexOf(r0)
            r1 = 0
            r2 = -1
            if (r0 != r2) goto Ld
            java.lang.String r0 = ""
            goto L16
        Ld:
            java.lang.String r3 = r4.substring(r1, r0)
            java.lang.String r0 = r4.substring(r0)
            r4 = r3
        L16:
            r4.hashCode()
            int r3 = r4.hashCode()
            switch(r3) {
                case 3325: goto L39;
                case 3355: goto L2e;
                case 3856: goto L22;
                default: goto L20;
            }
        L20:
            r1 = r2
            goto L42
        L22:
            java.lang.String r1 = "yi"
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L2c
            goto L20
        L2c:
            r1 = 2
            goto L42
        L2e:
            java.lang.String r1 = "id"
            boolean r1 = r4.equals(r1)
            if (r1 != 0) goto L37
            goto L20
        L37:
            r1 = 1
            goto L42
        L39:
            java.lang.String r3 = "he"
            boolean r3 = r4.equals(r3)
            if (r3 != 0) goto L42
            goto L20
        L42:
            switch(r1) {
                case 0: goto L4c;
                case 1: goto L49;
                case 2: goto L46;
                default: goto L45;
            }
        L45:
            goto L4e
        L46:
            java.lang.String r4 = "ji"
            goto L4e
        L49:
            java.lang.String r4 = "in"
            goto L4e
        L4c:
            java.lang.String r4 = "iw"
        L4e:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r4)
            r1.append(r0)
            java.lang.String r4 = r1.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.res.ResourcesImpl.adjustLanguageTag(java.lang.String):java.lang.String");
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

    Drawable loadDrawable(Resources resources, TypedValue typedValue, int i, int i2, Resources.Theme theme) throws Resources.NotFoundException {
        String str;
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
                            Drawable createFromResourceStream = Drawable.createFromResourceStream(resources, typedValue2, byteArrayInputStream, null, options);
                            Log.i(TAG, "loadDrawable() : EDM get Icon from DB : " + resources.mPackageName);
                            return createFromResourceStream;
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "loadDrawable() : EDM failed to get Icon", e);
                    }
                }
            } catch (Exception e2) {
                try {
                    str = getResourceName(i);
                } catch (Resources.NotFoundException unused) {
                    str = "(missing name)";
                }
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("Drawable " + str + " with resource ID #0x" + Integer.toHexString(i), e2);
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
        Drawable newDrawable = constantState2 != null ? constantState2.newDrawable(resources) : z ? new ColorDrawable(typedValue.data) : loadDrawableForCookie(resources, typedValue, i, i2);
        boolean z4 = newDrawable instanceof DrawableContainer;
        if (newDrawable == null || !newDrawable.canApplyTheme()) {
            z2 = false;
        }
        if (z2 && theme != null) {
            newDrawable = newDrawable.mutate();
            newDrawable.applyTheme(theme);
            newDrawable.clearMutated();
        }
        if (newDrawable != null) {
            newDrawable.setChangingConfigurations(typedValue.changingConfigurations);
            if (z3) {
                drawable = newDrawable;
                cacheDrawable(typedValue, z, drawableCache3, theme, z2, j, drawable, generation);
                if (z4 && (constantState = drawable.getConstantState()) != null) {
                    return constantState.newDrawable(resources);
                }
                return drawable;
            }
        }
        drawable = newDrawable;
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
        String str2;
        if ((i & (-1073745921) & (~i2)) == 0) {
            return true;
        }
        try {
            str2 = getResourceName(i3);
        } catch (Resources.NotFoundException unused) {
            str2 = "?";
        }
        Log.w(TAG, "Preloaded " + str + " resource #0x" + Integer.toHexString(i3) + " (" + str2 + ") that varies with configuration!!");
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
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.res.ResourcesImpl] */
    /* JADX WARN: Type inference failed for: r1v3 */
    private Drawable loadDrawableForCookie(Resources resources, TypedValue typedValue, int i, int i2) {
        Drawable drawable;
        String str;
        byte[] applicationIconFromDb;
        String str2 = this;
        if (typedValue.string == null) {
            throw new Resources.NotFoundException("Resource \"" + str2.getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Drawable (color or path): " + typedValue);
        }
        String charSequence = typedValue.string.toString();
        if (resources != null && i == resources.mAppIconResId && resources.mPackageName != null) {
            try {
                ApplicationPolicy applicationPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
                if (applicationPolicy != null && (applicationIconFromDb = applicationPolicy.getApplicationIconFromDb(resources.mPackageName, resources.mUserId)) != null) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(applicationIconFromDb);
                    TypedValue typedValue2 = new TypedValue();
                    typedValue2.density = str2.getDisplayMetrics().densityDpi;
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inTargetDensity = str2.getDisplayMetrics().densityDpi;
                    Drawable createFromResourceStream = Drawable.createFromResourceStream(resources, typedValue2, byteArrayInputStream, null, options);
                    Log.i(TAG, "loadDrawable() : EDM get Icon from DB : " + resources.mPackageName);
                    return createFromResourceStream;
                }
            } catch (Exception e) {
                Log.e(TAG, "loadDrawable() : EDM failed to get Icon", e);
            }
        }
        Trace.traceBegin(8192L, charSequence);
        LookupStack lookupStack = str2.mLookupStack.get();
        try {
            try {
                if (lookupStack.contains(i)) {
                    throw new Exception("Recursive reference in drawable");
                }
                lookupStack.push(i);
                try {
                    if (charSequence.endsWith(".xml")) {
                        String resourceTypeName = str2.getResourceTypeName(i);
                        if (resourceTypeName != null) {
                            try {
                                if (resourceTypeName.equals("color")) {
                                    str = charSequence;
                                    try {
                                        drawable = str2.loadColorOrXmlDrawable(resources, typedValue, i, i2, str);
                                    } catch (Throwable th) {
                                        th = th;
                                        lookupStack.pop();
                                        throw th;
                                    }
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                str = charSequence;
                            }
                        }
                        str = charSequence;
                        try {
                            drawable = loadXmlDrawable(resources, typedValue, i, i2, str);
                        } catch (Throwable th3) {
                            th = th3;
                            lookupStack.pop();
                            throw th;
                        }
                    } else {
                        try {
                            if (charSequence.startsWith("frro:/")) {
                                Uri parse = Uri.parse(charSequence);
                                long parseLong = Long.parseLong(parse.getQueryParameter(CallLog.Calls.OFFSET_PARAM_KEY));
                                long parseLong2 = Long.parseLong(parse.getQueryParameter(Contract.DatabaseSize.PATH));
                                if (parseLong < 0 || parseLong2 <= 0) {
                                    throw new Resources.NotFoundException("invalid frro parameters");
                                }
                                File file = new File("/" + parse.getHost() + parse.getPath());
                                if (!file.getCanonicalPath().startsWith(ResourcesManager.RESOURCE_CACHE_DIR) || !file.getCanonicalPath().endsWith(".frro") || !file.canRead()) {
                                    throw new Resources.NotFoundException("invalid frro path");
                                }
                                drawable = str2.decodeImageDrawable(new AssetFileDescriptor(ParcelFileDescriptor.open(file, 268435456), parseLong, parseLong2).createInputStream(), resources);
                            } else {
                                InputStream openNonAsset = str2.mAssets.openNonAsset(typedValue.assetCookie, charSequence, 2);
                                if (!charSequence.endsWith(".bmp") && !charSequence.endsWith(".spr")) {
                                    drawable = str2.decodeImageDrawable((AssetManager.AssetInputStream) openNonAsset, resources, typedValue);
                                }
                                Drawable createFromResourceStream2 = Drawable.createFromResourceStream(resources, typedValue, openNonAsset, charSequence, null);
                                openNonAsset.close();
                                drawable = createFromResourceStream2;
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            lookupStack.pop();
                            throw th;
                        }
                    }
                    lookupStack.pop();
                    Trace.traceEnd(8192L);
                    return drawable;
                } catch (Throwable th5) {
                    th = th5;
                }
            } catch (Exception | StackOverflowError e2) {
                e = e2;
                Trace.traceEnd(8192L);
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("File " + str2 + " from drawable resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        } catch (Exception | StackOverflowError e3) {
            e = e3;
            str2 = charSequence;
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

    private Drawable loadXmlDrawable(Resources resources, TypedValue typedValue, int i, int i2, String str) throws IOException, XmlPullParserException {
        XmlResourceParser loadXmlResourceParser = loadXmlResourceParser(str, i, typedValue.assetCookie, "drawable", typedValue.usesFeatureFlags);
        try {
            Drawable createFromXmlForDensity = Drawable.createFromXmlForDensity(resources, loadXmlResourceParser, i2, null);
            if (loadXmlResourceParser != null) {
                loadXmlResourceParser.close();
            }
            return createFromXmlForDensity;
        } catch (Throwable th) {
            if (loadXmlResourceParser == null) {
                throw th;
            }
            try {
                loadXmlResourceParser.close();
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
        String str;
        if (typedValue.string == null) {
            throw new Resources.NotFoundException("Resource \"" + getResourceName(i) + "\" (" + Integer.toHexString(i) + ") is not a Font: " + typedValue);
        }
        String charSequence = typedValue.string.toString();
        if (!charSequence.startsWith("res/")) {
            return null;
        }
        Typeface findFromCache = Typeface.findFromCache(this.mAssets, charSequence);
        if (findFromCache != null) {
            return findFromCache;
        }
        Trace.traceBegin(8192L, charSequence);
        try {
            try {
                str = charSequence.endsWith("xml");
                try {
                    if (str == 0) {
                        return new Typeface.Builder(this.mAssets, charSequence, false, typedValue.assetCookie).build();
                    }
                    try {
                        FontResourcesParser.FamilyResourceEntry parse = FontResourcesParser.parse(loadXmlResourceParser(charSequence, i, typedValue.assetCookie, Context.FONT_SERVICE, typedValue.usesFeatureFlags), resources);
                        if (parse == null) {
                            return null;
                        }
                        return Typeface.createFromResources(parse, this.mAssets, charSequence);
                    } catch (IOException e) {
                        e = e;
                        str = charSequence;
                        Log.e(TAG, "Failed to read xml resource " + str, e);
                        return null;
                    } catch (XmlPullParserException e2) {
                        e = e2;
                        str = charSequence;
                        Log.e(TAG, "Failed to parse xml resource " + str, e);
                        return null;
                    }
                } catch (IOException e3) {
                    e = e3;
                } catch (XmlPullParserException e4) {
                    e = e4;
                }
            } catch (IOException e5) {
                e = e5;
                str = charSequence;
            } catch (XmlPullParserException e6) {
                e = e6;
                str = charSequence;
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private ComplexColor loadComplexColorFromName(Resources resources, Resources.Theme theme, TypedValue typedValue, int i) {
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
        String charSequence = typedValue.string.toString();
        if (charSequence.endsWith(".xml")) {
            try {
                return loadComplexColorFromName(resources, theme, typedValue, i);
            } catch (Exception e) {
                Resources.NotFoundException notFoundException = new Resources.NotFoundException("File " + charSequence + " from complex color resource ID #0x" + Integer.toHexString(i));
                notFoundException.initCause(e);
                throw notFoundException;
            }
        }
        throw new Resources.NotFoundException("File " + charSequence + " from drawable resource ID #0x" + Integer.toHexString(i) + ": .xml extension required");
    }

    ColorStateList loadColorStateList(Resources resources, TypedValue typedValue, int i, Resources.Theme theme) throws Resources.NotFoundException {
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        if (typedValue.type >= 28 && typedValue.type <= 31) {
            return getColorStateListFromInt(typedValue, j);
        }
        ComplexColor loadComplexColorFromName = loadComplexColorFromName(resources, theme, typedValue, i);
        if (loadComplexColorFromName != null && (loadComplexColorFromName instanceof ColorStateList)) {
            return (ColorStateList) loadComplexColorFromName;
        }
        throw new Resources.NotFoundException("Can't find ColorStateList from drawable resource ID #0x" + Integer.toHexString(i));
    }

    private ColorStateList getColorStateListFromInt(TypedValue typedValue, long j) {
        LongSparseArray<ConstantState<ComplexColor>> longSparseArray = sPreloadedComplexColors;
        ConstantState<ComplexColor> constantState = longSparseArray.get(j);
        if (constantState != null) {
            return (ColorStateList) constantState.newInstance2();
        }
        ColorStateList valueOf = ColorStateList.valueOf(typedValue.data);
        if (this.mPreloading && verifyPreloadConfig(typedValue.changingConfigurations, 0, typedValue.resourceId, "color")) {
            longSparseArray.put(j, valueOf.getConstantState());
        }
        return valueOf;
    }

    private ComplexColor loadComplexColorForCookie(Resources resources, TypedValue typedValue, int i, Resources.Theme theme) {
        int i2;
        int next;
        ComplexColor createFromXmlInner;
        if (typedValue.string == null) {
            throw new UnsupportedOperationException("Can't convert to ComplexColor: type=0x" + typedValue.type);
        }
        String charSequence = typedValue.string.toString();
        Trace.traceBegin(8192L, charSequence);
        if (charSequence.endsWith(".xml")) {
            try {
                i2 = i;
                try {
                    XmlResourceParser loadXmlResourceParser = loadXmlResourceParser(charSequence, i2, typedValue.assetCookie, "ComplexColor", typedValue.usesFeatureFlags);
                    AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlResourceParser);
                    do {
                        next = loadXmlResourceParser.next();
                        if (next == 2) {
                            break;
                        }
                    } while (next != 1);
                    if (next != 2) {
                        throw new XmlPullParserException("No start tag found");
                    }
                    String name = loadXmlResourceParser.getName();
                    if (name.equals("gradient")) {
                        createFromXmlInner = GradientColor.createFromXmlInner(resources, loadXmlResourceParser, asAttributeSet, theme);
                    } else {
                        createFromXmlInner = name.equals("selector") ? ColorStateList.createFromXmlInner(resources, loadXmlResourceParser, asAttributeSet, theme) : null;
                    }
                    loadXmlResourceParser.close();
                    Trace.traceEnd(8192L);
                    return createFromXmlInner;
                } catch (Exception e) {
                    e = e;
                    Trace.traceEnd(8192L);
                    Resources.NotFoundException notFoundException = new Resources.NotFoundException("File " + charSequence + " from ComplexColor resource ID #0x" + Integer.toHexString(i2));
                    notFoundException.initCause(e);
                    throw notFoundException;
                }
            } catch (Exception e2) {
                e = e2;
                i2 = i;
            }
        } else {
            Trace.traceEnd(8192L);
            throw new Resources.NotFoundException("File " + charSequence + " from drawable resource ID #0x" + Integer.toHexString(i) + ": .xml extension required");
        }
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
                    XmlBlock openXmlBlockAsset = this.mAssets.openXmlBlockAsset(i2, str, z);
                    if (openXmlBlockAsset != null) {
                        int i4 = (this.mLastCachedXmlBlockIndex + 1) % length;
                        this.mLastCachedXmlBlockIndex = i4;
                        XmlBlock xmlBlock = xmlBlockArr[i4];
                        if (xmlBlock != null) {
                            xmlBlock.close();
                        }
                        iArr[i4] = i2;
                        strArr[i4] = str;
                        xmlBlockArr[i4] = openXmlBlockAsset;
                        return openXmlBlockAsset.newParser(i);
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
            long createTheme = assetManager.createTheme();
            this.mTheme = createTheme;
            ResourcesImpl.sThemeRegistry.registerNativeAllocation(this, createTheme);
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
            TypedArray obtain = TypedArray.obtain(theme.getResources(), iArr.length);
            XmlBlock.Parser parser = (XmlBlock.Parser) attributeSet;
            this.mAssets.applyStyle(this.mTheme, i, i2, parser, iArr, obtain.mDataAddress, obtain.mIndicesAddress);
            obtain.mTheme = theme;
            obtain.mXml = parser;
            return obtain;
        }

        TypedArray resolveAttributes(Resources.Theme theme, int[] iArr, int[] iArr2) {
            int length = iArr2.length;
            if (iArr == null || length != iArr.length) {
                throw new IllegalArgumentException("Base attribute values must the same length as attrs");
            }
            TypedArray obtain = TypedArray.obtain(theme.getResources(), length);
            this.mAssets.resolveAttrs(this.mTheme, 0, 0, iArr, iArr2, obtain.mData, obtain.mIndices);
            obtain.mTheme = theme;
            obtain.mXml = null;
            return obtain;
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
