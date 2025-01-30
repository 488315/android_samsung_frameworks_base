package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.p001om.SamsungThemeConstants;
import android.content.p002pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.icu.text.PluralRules;
import android.net.Uri;
import android.p009os.Build;
import android.p009os.LocaleList;
import android.p009os.ParcelFileDescriptor;
import android.p009os.Trace;
import android.provider.CallLog;
import android.sec.enterprise.ApplicationPolicy;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.DisplayAdjustments;
import com.android.internal.util.GrowingArrayUtils;
import com.samsung.android.knox.analytics.database.Contract;
import java.io.BufferedInputStream;
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
  final AssetManager mAssets;
  private final Configuration mConfiguration;
  private final DisplayAdjustments mDisplayAdjustments;
  private final DisplayMetrics mMetrics;
  private PluralRules mPluralRule;
  private boolean mPreloading;
  private static final Object sSync = new Object();
  private static final LongSparseArray<Drawable.ConstantState> sPreloadedColorDrawables =
      new LongSparseArray<>();
  private static final LongSparseArray<ConstantState<ComplexColor>> sPreloadedComplexColors =
      new LongSparseArray<>();
  private static final LongSparseArray<Drawable.ConstantState>[] sPreloadedDrawables = {
    new LongSparseArray<>(), new LongSparseArray<>()
  };
  private static final NativeAllocationRegistry sThemeRegistry =
      NativeAllocationRegistry.createMalloced(
          ResourcesImpl.class.getClassLoader(), AssetManager.getThemeFreeFunction());
  private static int sLoadDensityDpi = 0;
  private final Object mAccessLock = new Object();
  private final Configuration mTmpConfig = new Configuration();
  private final DrawableCache mDrawableCache = new DrawableCache();
  private final DrawableCache mColorDrawableCache = new DrawableCache();
  private final ConfigurationBoundResourceCache<ComplexColor> mComplexColorCache =
      new ConfigurationBoundResourceCache<
          ComplexColor>() { // from class: android.content.res.ResourcesImpl.1
        @Override // android.content.res.ConfigurationBoundResourceCache,
                  // android.content.res.ThemedResourceCache
        public void onConfigurationChange(int configChanges) {
          if ((configChanges & 512) != 0) {
            clear();
          } else {
            super.onConfigurationChange(configChanges);
          }
        }
      };
  private final ConfigurationBoundResourceCache<Animator> mAnimatorCache =
      new ConfigurationBoundResourceCache<>();
  private final ConfigurationBoundResourceCache<StateListAnimator> mStateListAnimatorCache =
      new ConfigurationBoundResourceCache<>();
  private final ThreadLocal<LookupStack> mLookupStack =
      ThreadLocal.withInitial(
          new Supplier() { // from class:
                           // android.content.res.ResourcesImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
              return ResourcesImpl.lambda$new$0();
            }
          });
  private int mLastCachedXmlBlockIndex = -1;
  private final int[] mCachedXmlBlockCookies = new int[4];
  private final String[] mCachedXmlBlockFiles = new String[4];
  private final XmlBlock[] mCachedXmlBlocks = new XmlBlock[4];

  static /* synthetic */ LookupStack lambda$new$0() {
    return new LookupStack();
  }

  public ResourcesImpl(
      AssetManager assets,
      DisplayMetrics metrics,
      Configuration config,
      DisplayAdjustments displayAdjustments) {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    this.mMetrics = displayMetrics;
    Configuration configuration = new Configuration();
    this.mConfiguration = configuration;
    this.mAssets = assets;
    displayMetrics.setToDefaults();
    this.mDisplayAdjustments = displayAdjustments;
    configuration.setToDefaults();
    updateConfiguration(config, metrics, displayAdjustments.getCompatibilityInfo());
  }

  public DisplayAdjustments getDisplayAdjustments() {
    return this.mDisplayAdjustments;
  }

  public AssetManager getAssets() {
    return this.mAssets;
  }

  DisplayMetrics getDisplayMetrics() {
    return this.mMetrics;
  }

  Configuration getConfiguration() {
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

  void getValue(int id, TypedValue outValue, boolean resolveRefs)
      throws Resources.NotFoundException {
    boolean found = this.mAssets.getResourceValue(id, 0, outValue, resolveRefs);
    if (found) {
    } else {
      throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(id));
    }
  }

  void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs)
      throws Resources.NotFoundException {
    boolean found = this.mAssets.getResourceValue(id, density, outValue, resolveRefs);
    if (found) {
    } else {
      throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(id));
    }
  }

  void getValue(String name, TypedValue outValue, boolean resolveRefs)
      throws Resources.NotFoundException {
    int id = getIdentifier(name, "string", null);
    if (id != 0) {
      getValue(id, outValue, resolveRefs);
      return;
    }
    throw new Resources.NotFoundException("String resource name " + name);
  }

  int getIdentifier(String name, String defType, String defPackage) {
    if (name == null) {
      throw new NullPointerException("name is null");
    }
    try {
      return Integer.parseInt(name);
    } catch (Exception e) {
      return this.mAssets.getResourceIdentifier(name, defType, defPackage);
    }
  }

  String getResourceName(int resid) throws Resources.NotFoundException {
    String str = this.mAssets.getResourceName(resid);
    if (str != null) {
      return str;
    }
    throw new Resources.NotFoundException(
        "Unable to find resource ID #0x" + Integer.toHexString(resid));
  }

  String getResourcePackageName(int resid) throws Resources.NotFoundException {
    String str = this.mAssets.getResourcePackageName(resid);
    if (str != null) {
      return str;
    }
    throw new Resources.NotFoundException(
        "Unable to find resource ID #0x" + Integer.toHexString(resid));
  }

  String getResourceTypeName(int resid) throws Resources.NotFoundException {
    String str = this.mAssets.getResourceTypeName(resid);
    if (str != null) {
      return str;
    }
    throw new Resources.NotFoundException(
        "Unable to find resource ID #0x" + Integer.toHexString(resid));
  }

  String getResourceEntryName(int resid) throws Resources.NotFoundException {
    String str = this.mAssets.getResourceEntryName(resid);
    if (str != null) {
      return str;
    }
    throw new Resources.NotFoundException(
        "Unable to find resource ID #0x" + Integer.toHexString(resid));
  }

  String getLastResourceResolution() throws Resources.NotFoundException {
    String str = this.mAssets.getLastResourceResolution();
    if (str != null) {
      return str;
    }
    throw new Resources.NotFoundException("Associated AssetManager hasn't resolved a resource");
  }

  CharSequence getQuantityText(int id, int quantity) throws Resources.NotFoundException {
    PluralRules rule = getPluralRule();
    CharSequence res =
        this.mAssets.getResourceBagText(id, attrForQuantityCode(rule.select(quantity)));
    if (res != null) {
      return res;
    }
    CharSequence res2 = this.mAssets.getResourceBagText(id, ID_OTHER);
    if (res2 != null) {
      return res2;
    }
    throw new Resources.NotFoundException(
        "Plural resource ID #0x"
            + Integer.toHexString(id)
            + " quantity="
            + quantity
            + " item="
            + rule.select(quantity));
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  private static int attrForQuantityCode(String quantityCode) {
    char c;
    switch (quantityCode.hashCode()) {
      case 101272:
        if (quantityCode.equals("few")) {
          c = 3;
          break;
        }
        c = 65535;
        break;
      case 110182:
        if (quantityCode.equals("one")) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      case 115276:
        if (quantityCode.equals("two")) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case 3343967:
        if (quantityCode.equals("many")) {
          c = 4;
          break;
        }
        c = 65535;
        break;
      case 3735208:
        if (quantityCode.equals("zero")) {
          c = 0;
          break;
        }
        c = 65535;
        break;
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
        return 16777221;
      case 1:
        return 16777222;
      case 2:
        return 16777223;
      case 3:
        return 16777224;
      case 4:
        return 16777225;
      default:
        return ID_OTHER;
    }
  }

  AssetFileDescriptor openRawResourceFd(int id, TypedValue tempValue)
      throws Resources.NotFoundException {
    getValue(id, tempValue, true);
    try {
      return this.mAssets.openNonAssetFd(tempValue.assetCookie, tempValue.string.toString());
    } catch (Exception e) {
      throw new Resources.NotFoundException(
          "File " + tempValue.string.toString() + " from resource ID #0x" + Integer.toHexString(id),
          e);
    }
  }

  InputStream openRawResource(int id, TypedValue value) throws Resources.NotFoundException {
    getValue(id, value, true);
    try {
      return this.mAssets.openNonAsset(value.assetCookie, value.string.toString(), 2);
    } catch (Exception e) {
      Resources.NotFoundException rnf =
          new Resources.NotFoundException(
              "File "
                  + (value.string == null ? "(null)" : value.string.toString())
                  + " from resource ID #0x"
                  + Integer.toHexString(id));
      rnf.initCause(e);
      throw rnf;
    }
  }

  ConfigurationBoundResourceCache<Animator> getAnimatorCache() {
    return this.mAnimatorCache;
  }

  ConfigurationBoundResourceCache<StateListAnimator> getStateListAnimatorCache() {
    return this.mStateListAnimatorCache;
  }

  public void updateConfiguration(
      Configuration config, DisplayMetrics metrics, CompatibilityInfo compat) {
    int width;
    int height;
    int keyboardHidden;
    Locale bestLocale;
    Trace.traceBegin(8192L, "ResourcesImpl#updateConfiguration");
    try {
      synchronized (this.mAccessLock) {
        if (compat != null) {
          try {
            this.mDisplayAdjustments.setCompatibilityInfo(compat);
          } catch (Throwable th) {
            throw th;
          }
        }
        if (metrics != null) {
          this.mMetrics.setTo(metrics);
        }
        this.mDisplayAdjustments.getCompatibilityInfo().applyToDisplayMetrics(this.mMetrics);
        int configChanges = calcConfigChanges(config);
        LocaleList locales = this.mConfiguration.getLocales();
        if (locales.isEmpty()) {
          locales = LocaleList.getDefault();
          this.mConfiguration.setLocales(locales);
        }
        if ((configChanges & 4) != 0 && locales.size() > 1) {
          String[] availableLocales = this.mAssets.getNonSystemLocales();
          if (LocaleList.isPseudoLocalesOnly(availableLocales)) {
            availableLocales = this.mAssets.getLocales();
            if (LocaleList.isPseudoLocalesOnly(availableLocales)) {
              availableLocales = null;
            }
          }
          if (availableLocales != null
              && (bestLocale = locales.getFirstMatchWithEnglishSupported(availableLocales)) != null
              && bestLocale != locales.get(0)) {
            this.mConfiguration.setLocales(new LocaleList(bestLocale, locales));
          }
        }
        if (this.mConfiguration.densityDpi != 0) {
          this.mMetrics.densityDpi = this.mConfiguration.densityDpi;
          this.mMetrics.density = this.mConfiguration.densityDpi * 0.00625f;
        }
        DisplayMetrics displayMetrics = this.mMetrics;
        displayMetrics.scaledDensity =
            displayMetrics.density
                * (this.mConfiguration.fontScale != 0.0f ? this.mConfiguration.fontScale : 1.0f);
        this.mMetrics.fontScaleConverter =
            FontScaleConverterFactory.forScale(this.mConfiguration.fontScale);
        if (this.mMetrics.widthPixels >= this.mMetrics.heightPixels) {
          width = this.mMetrics.widthPixels;
          height = this.mMetrics.heightPixels;
        } else {
          width = this.mMetrics.heightPixels;
          height = this.mMetrics.widthPixels;
        }
        if (this.mConfiguration.keyboardHidden == 1
            && this.mConfiguration.hardKeyboardHidden == 2) {
          keyboardHidden = 3;
        } else {
          keyboardHidden = this.mConfiguration.keyboardHidden;
        }
        this.mAssets.setConfiguration(
            this.mConfiguration.mcc,
            this.mConfiguration.mnc,
            adjustLanguageTag(this.mConfiguration.getLocales().get(0).toLanguageTag()),
            this.mConfiguration.orientation,
            this.mConfiguration.touchscreen,
            this.mConfiguration.densityDpi,
            this.mConfiguration.keyboard,
            keyboardHidden,
            this.mConfiguration.navigation,
            width,
            height,
            this.mConfiguration.smallestScreenWidthDp,
            this.mConfiguration.screenWidthDp,
            this.mConfiguration.screenHeightDp,
            this.mConfiguration.screenLayout,
            this.mConfiguration.uiMode,
            this.mConfiguration.colorMode,
            this.mConfiguration.getGrammaticalGender(),
            Build.VERSION.RESOURCES_SDK_INT);
        this.mDrawableCache.onConfigurationChange(configChanges);
        this.mColorDrawableCache.onConfigurationChange(configChanges);
        this.mComplexColorCache.onConfigurationChange(configChanges);
        this.mAnimatorCache.onConfigurationChange(configChanges);
        this.mStateListAnimatorCache.onConfigurationChange(configChanges);
        flushLayoutCache();
      }
      synchronized (sSync) {
        if (this.mPluralRule != null) {
          this.mPluralRule = PluralRules.forLocale(this.mConfiguration.getLocales().get(0));
        }
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  public int calcConfigChanges(Configuration config) {
    if (config == null) {
      return -1;
    }
    this.mTmpConfig.setTo(config);
    int density = config.densityDpi;
    if (density == 0) {
      density = this.mMetrics.noncompatDensityDpi;
    }
    this.mDisplayAdjustments.getCompatibilityInfo().applyToConfiguration(density, this.mTmpConfig);
    if (this.mTmpConfig.getLocales().isEmpty()) {
      this.mTmpConfig.setLocales(LocaleList.getDefault());
    }
    return this.mConfiguration.updateFrom(this.mTmpConfig);
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Code restructure failed: missing block: B:18:0x002f, code lost:

     if (r3.equals("id") != false) goto L19;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private static String adjustLanguageTag(String languageTag) {
    String language;
    String remainder;
    String adjustedLanguage;
    int separator = languageTag.indexOf(45);
    char c = 0;
    if (separator == -1) {
      language = languageTag;
      remainder = "";
    } else {
      language = languageTag.substring(0, separator);
      remainder = languageTag.substring(separator);
    }
    switch (language.hashCode()) {
      case 3325:
        if (language.equals("he")) {
          c = 2;
          break;
        }
        c = 65535;
        break;
      case 3355:
        break;
      case 3856:
        if (language.equals("yi")) {
          c = 1;
          break;
        }
        c = 65535;
        break;
      default:
        c = 65535;
        break;
    }
    switch (c) {
      case 0:
        adjustedLanguage = "in";
        break;
      case 1:
        adjustedLanguage = "ji";
        break;
      case 2:
        adjustedLanguage = "iw";
        break;
      default:
        adjustedLanguage = language;
        break;
    }
    return adjustedLanguage + remainder;
  }

  public void flushLayoutCache() {
    synchronized (this.mCachedXmlBlocks) {
      Arrays.fill(this.mCachedXmlBlockCookies, 0);
      Arrays.fill(this.mCachedXmlBlockFiles, (Object) null);
      XmlBlock[] cachedXmlBlocks = this.mCachedXmlBlocks;
      for (int i = 0; i < 4; i++) {
        XmlBlock oldBlock = cachedXmlBlocks[i];
        if (oldBlock != null) {
          oldBlock.close();
        }
      }
      Arrays.fill(cachedXmlBlocks, (Object) null);
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

  Drawable loadDrawable(
      Resources wrapper, TypedValue value, int id, int density, Resources.Theme theme)
      throws Resources.NotFoundException {
    int i;
    byte[] imageData;
    String name;
    boolean isColorDrawable;
    DrawableCache caches;
    long key;
    Drawable.ConstantState cs;
    Drawable dr;
    boolean needsNewDrawableAfterCache;
    Drawable dr2;
    Drawable dr3;
    Drawable.ConstantState state;
    Drawable cachedDrawable;
    boolean useCache = density == 0 || value.density == this.mMetrics.densityDpi;
    if (density > 0 && value.density > 0 && value.density != 65535) {
      if (value.density != density) {
        value.density = (value.density * this.mMetrics.densityDpi) / density;
      } else {
        value.density = this.mMetrics.densityDpi;
      }
    }
    if (wrapper != null) {
      try {
        if (id == wrapper.mAppIconResId && wrapper.mPackageName != null) {
          try {
            ApplicationPolicy appPolicy =
                EnterpriseDeviceManager.getInstance().getApplicationPolicy();
            if (appPolicy != null
                && (imageData =
                        appPolicy.getApplicationIconFromDb(wrapper.mPackageName, wrapper.mUserId))
                    != null) {
              ByteArrayInputStream is = new ByteArrayInputStream(imageData);
              TypedValue typedValue = new TypedValue();
              typedValue.density = getDisplayMetrics().densityDpi;
              BitmapFactory.Options opts = new BitmapFactory.Options();
              opts.inTargetDensity = getDisplayMetrics().densityDpi;
              Drawable drw = Drawable.createFromResourceStream(wrapper, typedValue, is, null, opts);
              Log.m98i(TAG, "loadDrawable() : EDM get Icon from DB : " + wrapper.mPackageName);
              return drw;
            }
          } catch (Exception e) {
            Log.m97e(TAG, "loadDrawable() : EDM failed to get Icon", e);
          }
        }
      } catch (Exception e2) {
        e = e2;
        i = 0;
        Exception e3 = e;
        try {
          name = getResourceName(id);
        } catch (Resources.NotFoundException e4) {
          name = "(missing name)";
        }
        Resources.NotFoundException nfe =
            new Resources.NotFoundException(
                "Drawable " + name + " with resource ID #0x" + Integer.toHexString(id), e3);
        nfe.setStackTrace(new StackTraceElement[i]);
        throw nfe;
      }
    }
    if (value.type >= 28 && value.type <= 31) {
      isColorDrawable = true;
      DrawableCache caches2 = this.mColorDrawableCache;
      caches = caches2;
      key = value.data;
    } else {
      isColorDrawable = false;
      DrawableCache caches3 = this.mDrawableCache;
      caches = caches3;
      key = (value.assetCookie << 32) | value.data;
    }
    int cacheGeneration = caches.getGeneration();
    if (!this.mPreloading
        && useCache
        && (cachedDrawable = caches.getInstance(key, wrapper, theme)) != null) {
      cachedDrawable.setChangingConfigurations(value.changingConfigurations);
      return cachedDrawable;
    }
    if (!isColorDrawable) {
      cs = sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].get(key);
    } else {
      cs = sPreloadedColorDrawables.get(key);
    }
    if (cs != null) {
      dr = cs.newDrawable(wrapper);
    } else if (isColorDrawable) {
      dr = new ColorDrawable(value.data);
    } else {
      dr = loadDrawableForCookie(wrapper, value, id, density);
    }
    if (!(dr instanceof DrawableContainer)) {
      needsNewDrawableAfterCache = false;
    } else {
      needsNewDrawableAfterCache = true;
    }
    boolean canApplyTheme = dr != null && dr.canApplyTheme();
    if (canApplyTheme && theme != null) {
      Drawable dr4 = dr.mutate();
      dr4.applyTheme(theme);
      dr4.clearMutated();
      dr2 = dr4;
    } else {
      dr2 = dr;
    }
    if (dr2 == null) {
      dr3 = dr2;
    } else {
      dr2.setChangingConfigurations(value.changingConfigurations);
      if (!useCache) {
        dr3 = dr2;
      } else {
        dr3 = dr2;
        i = 0;
        try {
          cacheDrawable(
              value, isColorDrawable, caches, theme, canApplyTheme, key, dr3, cacheGeneration);
          if (needsNewDrawableAfterCache && (state = dr3.getConstantState()) != null) {
            Drawable dr5 = state.newDrawable(wrapper);
            return dr5;
          }
        } catch (Exception e5) {
          e = e5;
          Exception e32 = e;
          name = getResourceName(id);
          Resources.NotFoundException nfe2 =
              new Resources.NotFoundException(
                  "Drawable " + name + " with resource ID #0x" + Integer.toHexString(id), e32);
          nfe2.setStackTrace(new StackTraceElement[i]);
          throw nfe2;
        }
      }
    }
    return dr3;
  }

  Drawable loadDrawable(Resources wrapper, TypedValue value, int id, int density)
      throws Resources.NotFoundException {
    String name;
    boolean isColorDrawable;
    long key;
    Drawable.ConstantState cs;
    if (density > 0 && value.density > 0 && value.density != 65535) {
      if (value.density == density) {
        value.density = this.mMetrics.densityDpi;
      } else {
        value.density = (value.density * this.mMetrics.densityDpi) / density;
      }
    }
    try {
      if (value.type >= 28 && value.type <= 31) {
        isColorDrawable = true;
        key = value.data;
      } else {
        isColorDrawable = false;
        key = (value.assetCookie << 32) | value.data;
      }
      if (isColorDrawable) {
        cs = sPreloadedColorDrawables.get(key);
      } else {
        cs = sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].get(key);
      }
      if (cs != null) {
        Drawable dr = cs.newDrawable(wrapper);
        return dr;
      }
      if (isColorDrawable) {
        Drawable dr2 = new ColorDrawable(value.data);
        return dr2;
      }
      Drawable dr3 = loadDrawableForCookie(wrapper, value, id, density);
      return dr3;
    } catch (Exception e) {
      try {
        name = getResourceName(id);
      } catch (Resources.NotFoundException e2) {
        name = "(missing name)";
      }
      Resources.NotFoundException nfe =
          new Resources.NotFoundException(
              "Drawable " + name + " with resource ID #0x" + Integer.toHexString(id), e);
      nfe.setStackTrace(new StackTraceElement[0]);
      throw nfe;
    }
  }

  private void cacheDrawable(
      TypedValue value,
      boolean isColorDrawable,
      DrawableCache caches,
      Resources.Theme theme,
      boolean usesTheme,
      long key,
      Drawable dr,
      int cacheGeneration) {
    Drawable.ConstantState cs = dr.getConstantState();
    if (cs == null) {
      return;
    }
    if (this.mPreloading) {
      int changingConfigs = cs.getChangingConfigurations();
      if (isColorDrawable) {
        if (verifyPreloadConfig(changingConfigs, 0, value.resourceId, "drawable")) {
          sPreloadedColorDrawables.put(key, cs);
          return;
        }
        return;
      } else {
        if (verifyPreloadConfig(changingConfigs, 8192, value.resourceId, "drawable")) {
          if ((changingConfigs & 8192) != 0) {
            sPreloadedDrawables[this.mConfiguration.getLayoutDirection()].put(key, cs);
            return;
          }
          LongSparseArray<Drawable.ConstantState>[] longSparseArrayArr = sPreloadedDrawables;
          longSparseArrayArr[0].put(key, cs);
          longSparseArrayArr[1].put(key, cs);
          return;
        }
        return;
      }
    }
    synchronized (this.mAccessLock) {
      caches.put(key, theme, cs, cacheGeneration, usesTheme);
    }
  }

  private boolean verifyPreloadConfig(
      int changingConfigurations, int allowVarying, int resourceId, String name) {
    String resName;
    if (((-1073745921) & changingConfigurations & (~allowVarying)) != 0) {
      try {
        resName = getResourceName(resourceId);
      } catch (Resources.NotFoundException e) {
        resName = "?";
      }
      Log.m102w(
          TAG,
          "Preloaded "
              + name
              + " resource #0x"
              + Integer.toHexString(resourceId)
              + " ("
              + resName
              + ") that varies with configuration!!");
      return false;
    }
    return true;
  }

  private Drawable decodeImageDrawable(
      AssetManager.AssetInputStream ais, Resources wrapper, TypedValue value) {
    ImageDecoder.Source src = new ImageDecoder.AssetInputStreamSource(ais, wrapper, value);
    try {
      return ImageDecoder.decodeDrawable(
          src,
          new ImageDecoder
              .OnHeaderDecodedListener() { // from class:
                                           // android.content.res.ResourcesImpl$$ExternalSyntheticLambda2
            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(
                ImageDecoder imageDecoder,
                ImageDecoder.ImageInfo imageInfo,
                ImageDecoder.Source source) {
              imageDecoder.setAllocator(1);
            }
          });
    } catch (IOException e) {
      return null;
    }
  }

  private Drawable decodeImageDrawable(FileInputStream fis, Resources wrapper) {
    ImageDecoder.Source src = ImageDecoder.createSource(wrapper, fis);
    try {
      return ImageDecoder.decodeDrawable(
          src,
          new ImageDecoder
              .OnHeaderDecodedListener() { // from class:
                                           // android.content.res.ResourcesImpl$$ExternalSyntheticLambda0
            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(
                ImageDecoder imageDecoder,
                ImageDecoder.ImageInfo imageInfo,
                ImageDecoder.Source source) {
              imageDecoder.setAllocator(1);
            }
          });
    } catch (IOException e) {
      return null;
    }
  }

  private Drawable loadDrawableForCookie(Resources wrapper, TypedValue value, int id, int density) {
    Drawable dr;
    Drawable dr2;
    Drawable dr3;
    byte[] imageData;
    if (value.string == null) {
      throw new Resources.NotFoundException(
          "Resource \""
              + getResourceName(id)
              + "\" ("
              + Integer.toHexString(id)
              + ") is not a Drawable (color or path): "
              + value);
    }
    String file = value.string.toString();
    if (wrapper != null && id == wrapper.mAppIconResId && wrapper.mPackageName != null) {
      try {
        ApplicationPolicy appPolicy = EnterpriseDeviceManager.getInstance().getApplicationPolicy();
        if (appPolicy != null
            && (imageData =
                    appPolicy.getApplicationIconFromDb(wrapper.mPackageName, wrapper.mUserId))
                != null) {
          ByteArrayInputStream is = new ByteArrayInputStream(imageData);
          TypedValue typedValue = new TypedValue();
          typedValue.density = getDisplayMetrics().densityDpi;
          BitmapFactory.Options opts = new BitmapFactory.Options();
          opts.inTargetDensity = getDisplayMetrics().densityDpi;
          Drawable drw = Drawable.createFromResourceStream(wrapper, typedValue, is, null, opts);
          Log.m98i(TAG, "loadDrawable() : EDM get Icon from DB : " + wrapper.mPackageName);
          return drw;
        }
      } catch (Exception e) {
        Log.m97e(TAG, "loadDrawable() : EDM failed to get Icon", e);
      }
    }
    Trace.traceBegin(8192L, file);
    LookupStack stack = this.mLookupStack.get();
    try {
      if (stack.contains(id)) {
        throw new Exception("Recursive reference in drawable");
      }
      stack.push(id);
      try {
        if (file.endsWith(".xml")) {
          String typeName = getResourceTypeName(id);
          if (typeName != null && typeName.equals("color")) {
            dr3 = loadColorOrXmlDrawable(wrapper, value, id, density, file);
          } else {
            dr3 = loadXmlDrawable(wrapper, value, id, density, file);
          }
          dr = dr3;
        } else if (file.startsWith("frro://")) {
          Uri uri = Uri.parse(file);
          File f = new File('/' + uri.getHost() + uri.getPath());
          ParcelFileDescriptor pfd = ParcelFileDescriptor.open(f, 268435456);
          AssetFileDescriptor afd =
              new AssetFileDescriptor(
                  pfd,
                  Long.parseLong(uri.getQueryParameter(CallLog.Calls.OFFSET_PARAM_KEY)),
                  Long.parseLong(uri.getQueryParameter(Contract.DatabaseSize.PATH)));
          Drawable dr4 = decodeImageDrawable(afd.createInputStream(), wrapper);
          dr = dr4;
        } else {
          InputStream is2 = this.mAssets.openNonAsset(value.assetCookie, file, 2);
          boolean z = true;
          String apkPath = this.mAssets.getApkPaths()[value.assetCookie - 1];
          if (file.endsWith(".qmg")
              && (apkPath.startsWith("/data/overlays")
                  || apkPath.startsWith(SamsungThemeConstants.DATA_APP_DIR))) {
            AssetManager.AssetInputStream aisQMG = (AssetManager.AssetInputStream) is2;
            if (isQMGImage(is2)) {
              dr2 = decodeImageDrawableQMG(aisQMG, wrapper, value);
            } else {
              Drawable dr5 = decodeImageDrawable(aisQMG, wrapper, value);
              try {
                StringBuilder append =
                    new StringBuilder()
                        .append("QMG file ")
                        .append(file)
                        .append(", is decoded with original one, success load = ");
                if (dr5 == null) {
                  z = false;
                }
                Log.m94d(TAG, append.append(z).toString());
                dr2 = dr5;
              } catch (Throwable th) {
                th = th;
                stack.pop();
                throw th;
              }
            }
            dr = dr2;
          } else {
            if (!file.endsWith(".bmp") && !file.endsWith(".spr")) {
              AssetManager.AssetInputStream ais = (AssetManager.AssetInputStream) is2;
              dr = decodeImageDrawable(ais, wrapper, value);
            }
            Drawable dr6 = Drawable.createFromResourceStream(wrapper, value, is2, file, null);
            try {
              is2.close();
              dr = dr6;
            } catch (Throwable th2) {
              th = th2;
              stack.pop();
              throw th;
            }
          }
        }
        stack.pop();
        Trace.traceEnd(8192L);
        return dr;
      } catch (Throwable th3) {
        th = th3;
      }
    } catch (Exception | StackOverflowError e2) {
      Trace.traceEnd(8192L);
      Resources.NotFoundException rnf =
          new Resources.NotFoundException(
              "File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
      rnf.initCause(e2);
      throw rnf;
    }
  }

  private Drawable loadColorOrXmlDrawable(
      Resources wrapper, TypedValue value, int id, int density, String file) {
    try {
      ColorStateList csl = loadColorStateList(wrapper, value, id, null);
      return new ColorStateListDrawable(csl);
    } catch (Resources.NotFoundException originalException) {
      try {
        return loadXmlDrawable(wrapper, value, id, density, file);
      } catch (Exception e) {
        throw originalException;
      }
    }
  }

  private Drawable loadXmlDrawable(
      Resources wrapper, TypedValue value, int id, int density, String file)
      throws IOException, XmlPullParserException {
    XmlResourceParser rp = loadXmlResourceParser(file, id, value.assetCookie, "drawable");
    try {
      Drawable createFromXmlForDensity =
          Drawable.createFromXmlForDensity(wrapper, rp, density, null);
      if (rp != null) {
        rp.close();
      }
      return createFromXmlForDensity;
    } catch (Throwable th) {
      if (rp != null) {
        try {
          rp.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
      }
      throw th;
    }
  }

  public Typeface loadFont(Resources wrapper, TypedValue value, int id) {
    if (value.string == null) {
      throw new Resources.NotFoundException(
          "Resource \""
              + getResourceName(id)
              + "\" ("
              + Integer.toHexString(id)
              + ") is not a Font: "
              + value);
    }
    String file = value.string.toString();
    if (!file.startsWith("res/")) {
      return null;
    }
    Typeface cached = Typeface.findFromCache(this.mAssets, file);
    if (cached != null) {
      return cached;
    }
    Trace.traceBegin(8192L, file);
    try {
      try {
        if (!file.endsWith("xml")) {
          return new Typeface.Builder(this.mAssets, file, false, value.assetCookie).build();
        }
        XmlResourceParser rp =
            loadXmlResourceParser(file, id, value.assetCookie, Context.FONT_SERVICE);
        FontResourcesParser.FamilyResourceEntry familyEntry =
            FontResourcesParser.parse(rp, wrapper);
        if (familyEntry == null) {
          return null;
        }
        return Typeface.createFromResources(familyEntry, this.mAssets, file);
      } catch (IOException e) {
        Log.m97e(TAG, "Failed to read xml resource " + file, e);
        return null;
      } catch (XmlPullParserException e2) {
        Log.m97e(TAG, "Failed to parse xml resource " + file, e2);
        return null;
      }
    } finally {
      Trace.traceEnd(8192L);
    }
  }

  private ComplexColor loadComplexColorFromName(
      Resources wrapper, Resources.Theme theme, TypedValue value, int id) {
    long key = (value.assetCookie << 32) | value.data;
    ConfigurationBoundResourceCache<ComplexColor> cache = this.mComplexColorCache;
    ComplexColor complexColor = cache.getInstance(key, wrapper, theme);
    if (complexColor != null) {
      return complexColor;
    }
    int cacheGeneration = cache.getGeneration();
    LongSparseArray<ConstantState<ComplexColor>> longSparseArray = sPreloadedComplexColors;
    ConstantState<ComplexColor> factory = longSparseArray.get(key);
    if (factory != null) {
      complexColor = factory.newInstance2(wrapper, theme);
    }
    ComplexColor complexColor2 =
        complexColor == null ? loadComplexColorForCookie(wrapper, value, id, theme) : complexColor;
    if (complexColor2 == null) {
      return complexColor2;
    }
    complexColor2.setBaseChangingConfigurations(value.changingConfigurations);
    if (this.mPreloading) {
      if (!verifyPreloadConfig(
          complexColor2.getChangingConfigurations(), 0, value.resourceId, "color")) {
        return complexColor2;
      }
      longSparseArray.put(key, complexColor2.getConstantState());
      return complexColor2;
    }
    ComplexColor complexColor3 = complexColor2;
    cache.put(key, theme, complexColor2.getConstantState(), cacheGeneration);
    return complexColor3;
  }

  ComplexColor loadComplexColor(
      Resources wrapper, TypedValue value, int id, Resources.Theme theme) {
    long key = (value.assetCookie << 32) | value.data;
    if (value.type >= 28 && value.type <= 31) {
      return getColorStateListFromInt(value, key);
    }
    String file = value.string.toString();
    if (file.endsWith(".xml")) {
      try {
        ComplexColor complexColor = loadComplexColorFromName(wrapper, theme, value, id);
        return complexColor;
      } catch (Exception e) {
        Resources.NotFoundException rnf =
            new Resources.NotFoundException(
                "File " + file + " from complex color resource ID #0x" + Integer.toHexString(id));
        rnf.initCause(e);
        throw rnf;
      }
    }
    throw new Resources.NotFoundException(
        "File "
            + file
            + " from drawable resource ID #0x"
            + Integer.toHexString(id)
            + ": .xml extension required");
  }

  ColorStateList loadColorStateList(
      Resources wrapper, TypedValue value, int id, Resources.Theme theme)
      throws Resources.NotFoundException {
    long key = (value.assetCookie << 32) | value.data;
    if (value.type >= 28 && value.type <= 31) {
      return getColorStateListFromInt(value, key);
    }
    ComplexColor complexColor = loadComplexColorFromName(wrapper, theme, value, id);
    if (complexColor != null && (complexColor instanceof ColorStateList)) {
      return (ColorStateList) complexColor;
    }
    throw new Resources.NotFoundException(
        "Can't find ColorStateList from drawable resource ID #0x" + Integer.toHexString(id));
  }

  private ColorStateList getColorStateListFromInt(TypedValue value, long key) {
    LongSparseArray<ConstantState<ComplexColor>> longSparseArray = sPreloadedComplexColors;
    ConstantState<ComplexColor> factory = longSparseArray.get(key);
    if (factory != null) {
      return (ColorStateList) factory.newInstance2();
    }
    ColorStateList csl = ColorStateList.valueOf(value.data);
    if (this.mPreloading
        && verifyPreloadConfig(value.changingConfigurations, 0, value.resourceId, "color")) {
      longSparseArray.put(key, csl.getConstantState());
    }
    return csl;
  }

  private ComplexColor loadComplexColorForCookie(
      Resources wrapper, TypedValue value, int id, Resources.Theme theme) {
    int type;
    if (value.string == null) {
      throw new UnsupportedOperationException(
          "Can't convert to ComplexColor: type=0x" + value.type);
    }
    String file = value.string.toString();
    ComplexColor complexColor = null;
    Trace.traceBegin(8192L, file);
    if (file.endsWith(".xml")) {
      try {
        XmlResourceParser parser =
            loadXmlResourceParser(file, id, value.assetCookie, "ComplexColor");
        AttributeSet attrs = Xml.asAttributeSet(parser);
        do {
          type = parser.next();
          if (type == 2) {
            break;
          }
        } while (type != 1);
        if (type != 2) {
          throw new XmlPullParserException("No start tag found");
        }
        String name = parser.getName();
        if (name.equals("gradient")) {
          complexColor = GradientColor.createFromXmlInner(wrapper, parser, attrs, theme);
        } else if (name.equals("selector")) {
          complexColor = ColorStateList.createFromXmlInner(wrapper, parser, attrs, theme);
        }
        parser.close();
        Trace.traceEnd(8192L);
        return complexColor;
      } catch (Exception e) {
        Trace.traceEnd(8192L);
        Resources.NotFoundException rnf =
            new Resources.NotFoundException(
                "File " + file + " from ComplexColor resource ID #0x" + Integer.toHexString(id));
        rnf.initCause(e);
        throw rnf;
      }
    }
    Trace.traceEnd(8192L);
    throw new Resources.NotFoundException(
        "File "
            + file
            + " from drawable resource ID #0x"
            + Integer.toHexString(id)
            + ": .xml extension required");
  }

  XmlResourceParser loadXmlResourceParser(String file, int id, int assetCookie, String type)
      throws Resources.NotFoundException {
    if (id != 0) {
      try {
        synchronized (this.mCachedXmlBlocks) {
          int[] cachedXmlBlockCookies = this.mCachedXmlBlockCookies;
          String[] cachedXmlBlockFiles = this.mCachedXmlBlockFiles;
          XmlBlock[] cachedXmlBlocks = this.mCachedXmlBlocks;
          int num = cachedXmlBlockFiles.length;
          for (int i = 0; i < num; i++) {
            if (cachedXmlBlockCookies[i] == assetCookie
                && cachedXmlBlockFiles[i] != null
                && cachedXmlBlockFiles[i].equals(file)) {
              return cachedXmlBlocks[i].newParser(id);
            }
          }
          XmlBlock block = this.mAssets.openXmlBlockAsset(assetCookie, file);
          if (block != null) {
            int pos = (this.mLastCachedXmlBlockIndex + 1) % num;
            this.mLastCachedXmlBlockIndex = pos;
            XmlBlock oldBlock = cachedXmlBlocks[pos];
            if (oldBlock != null) {
              oldBlock.close();
            }
            cachedXmlBlockCookies[pos] = assetCookie;
            cachedXmlBlockFiles[pos] = file;
            cachedXmlBlocks[pos] = block;
            return block.newParser(id);
          }
        }
      } catch (Exception e) {
        Resources.NotFoundException rnf =
            new Resources.NotFoundException(
                "File "
                    + file
                    + " from xml type "
                    + type
                    + " resource ID #0x"
                    + Integer.toHexString(id));
        rnf.initCause(e);
        throw rnf;
      }
    }
    throw new Resources.NotFoundException(
        "File " + file + " from xml type " + type + " resource ID #0x" + Integer.toHexString(id));
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

  static int getAttributeSetSourceResId(AttributeSet set) {
    if (set == null || !(set instanceof XmlBlock.Parser)) {
      return 0;
    }
    return ((XmlBlock.Parser) set).getSourceResId();
  }

  LongSparseArray<Drawable.ConstantState> getPreloadedDrawables() {
    return sPreloadedDrawables[0];
  }

  ThemeImpl newThemeImpl() {
    return new ThemeImpl();
  }

  void dump(PrintWriter pw, String prefix) {
    pw.println(prefix + "class=" + getClass());
    pw.println(prefix + "assets");
    this.mAssets.dump(pw, prefix + "  ");
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

    int getParentThemeIdentifier(int resId) {
      if (resId > 0) {
        return this.mAssets.getParentThemeIdentifier(resId);
      }
      return 0;
    }

    void applyStyle(int resId, boolean force) {
      this.mAssets.applyStyleToTheme(this.mTheme, resId, force);
      this.mThemeResId = resId;
      this.mKey.append(resId, force);
    }

    void setTo(ThemeImpl other) {
      this.mAssets.setThemeTo(this.mTheme, other.mAssets, other.mTheme);
      this.mThemeResId = other.mThemeResId;
      this.mKey.setTo(other.getKey());
    }

    TypedArray obtainStyledAttributes(
        Resources.Theme wrapper, AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) {
      int len = attrs.length;
      TypedArray array = TypedArray.obtain(wrapper.getResources(), len);
      XmlBlock.Parser parser = (XmlBlock.Parser) set;
      this.mAssets.applyStyle(
          this.mTheme,
          defStyleAttr,
          defStyleRes,
          parser,
          attrs,
          array.mDataAddress,
          array.mIndicesAddress);
      array.mTheme = wrapper;
      array.mXml = parser;
      return array;
    }

    TypedArray resolveAttributes(Resources.Theme wrapper, int[] values, int[] attrs) {
      int len = attrs.length;
      if (values == null || len != values.length) {
        throw new IllegalArgumentException("Base attribute values must the same length as attrs");
      }
      TypedArray array = TypedArray.obtain(wrapper.getResources(), len);
      this.mAssets.resolveAttrs(this.mTheme, 0, 0, values, attrs, array.mData, array.mIndices);
      array.mTheme = wrapper;
      array.mXml = null;
      return array;
    }

    boolean resolveAttribute(int resid, TypedValue outValue, boolean resolveRefs) {
      return this.mAssets.getThemeValue(this.mTheme, resid, outValue, resolveRefs);
    }

    int[] getAllAttributes() {
      return this.mAssets.getStyleAttributes(getAppliedStyleResId());
    }

    int getChangingConfigurations() {
      int nativeChangingConfig = AssetManager.nativeThemeGetChangingConfigurations(this.mTheme);
      return ActivityInfo.activityInfoConfigNativeToJava(nativeChangingConfig);
    }

    public void dump(int priority, String tag, String prefix) {
      this.mAssets.dumpTheme(this.mTheme, priority, tag, prefix);
    }

    String[] getTheme() {
      int n = this.mKey.mCount;
      String[] themes = new String[n * 2];
      int i = 0;
      int j = n - 1;
      while (i < themes.length) {
        int resId = this.mKey.mResId[j];
        boolean forced = this.mKey.mForce[j];
        try {
          themes[i] = ResourcesImpl.this.getResourceName(resId);
        } catch (Resources.NotFoundException e) {
          themes[i] = Integer.toHexString(i);
        }
        themes[i + 1] = forced ? "forced" : "not forced";
        i += 2;
        j--;
      }
      return themes;
    }

    void rebase() {
      rebase(this.mAssets);
    }

    void rebase(AssetManager newAssets) {
      this.mAssets =
          this.mAssets.rebaseTheme(
              this.mTheme, newAssets, this.mKey.mResId, this.mKey.mForce, this.mKey.mCount);
    }

    public int[] getAttributeResolutionStack(
        int defStyleAttr, int defStyleRes, int explicitStyleRes) {
      return this.mAssets.getAttributeResolutionStack(
          this.mTheme, defStyleAttr, defStyleRes, explicitStyleRes);
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

    public void push(int id) {
      this.mIds = GrowingArrayUtils.append(this.mIds, this.mSize, id);
      this.mSize++;
    }

    public boolean contains(int id) {
      for (int i = 0; i < this.mSize; i++) {
        if (this.mIds[i] == id) {
          return true;
        }
      }
      return false;
    }

    public void pop() {
      this.mSize--;
    }
  }

  public static void setLoadDensityDpi(int loadDensityDpi) {
    sLoadDensityDpi = loadDensityDpi;
  }

  private static boolean isQMGImage(InputStream is) {
    byte[] buffer = new byte[2];
    try {
      if (!is.markSupported()) {
        is = new BufferedInputStream(is);
      }
      is.mark(2);
      is.read(buffer);
      char h0 = (char) buffer[0];
      char h1 = (char) buffer[1];
      if (h0 == 'Q' && h1 == 'G') {
        is.reset();
        return true;
      }
      is.reset();
      return false;
    } catch (IOException ioe) {
      Log.m96e(TAG, "Failed to read input stream: " + ioe);
      return false;
    }
  }

  private Drawable decodeImageDrawableQMG(
      AssetManager.AssetInputStream ais, Resources wrapper, TypedValue value) {
    ImageDecoder.Source src = new ImageDecoder.AssetInputStreamSourceQMG(ais, wrapper, value);
    try {
      return ImageDecoder.decodeDrawable(
          src,
          new ImageDecoder
              .OnHeaderDecodedListener() { // from class:
                                           // android.content.res.ResourcesImpl$$ExternalSyntheticLambda1
            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
            public final void onHeaderDecoded(
                ImageDecoder imageDecoder,
                ImageDecoder.ImageInfo imageInfo,
                ImageDecoder.Source source) {
              imageDecoder.setAllocator(1);
            }
          });
    } catch (IOException e) {
      return null;
    }
  }
}
