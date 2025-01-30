package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.loader.ResourcesLoader;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableInflater;
import android.media.TtmlUtils;
import android.p009os.Bundle;
import android.p009os.Process;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pools;
import android.util.TypedValue;
import android.view.DisplayAdjustments;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import com.android.internal.C4337R;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.samsung.android.ims.options.SemCapabilities;
import com.samsung.android.share.SemShareConstants;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Resources {
  public static final int ID_NULL = 0;
  private static final int MAX_RESOURCES_TO_BE_OFFLOADED = 30;
  private static final int MAX_THEME_REFS_FLUSH_SIZE = 512;
  private static final int MIN_THEME_REFS_FLUSH_SIZE = 32;
  private static final int RESOURCE_FUTURE_GET_TIMEOUT = 2;
  static final String TAG = "Resources";
  public int mAppIconResId;
  private int mBaseApkAssetsSize;
  private UpdateCallbacks mCallbacks;
  final ClassLoader mClassLoader;
  private DrawableInflater mDrawableInflater;
  public String mPackageName;
  private ResourcesImpl mResourcesImpl;
  private final ArrayList<WeakReference<Theme>> mThemeRefs;
  private int mThemeRefsNextFlushSize;
  private ExecutorService mThreadExecutor;
  private TypedValue mTmpValue;
  private final Object mTmpValueLock;
  final Pools.SynchronizedPool<TypedArray> mTypedArrayPool;
  private final Object mUpdateLock;
  public int mUserId;
  private static final Object sSync = new Object();
  static Resources mSystem = null;
  private static Set<Resources> sResourcesHistory =
      Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
  private static List<String> sResourceList = new ArrayList();
  private static boolean sIsAppLaunching = false;
  private static Context sAppContext = null;
  private static int sResourceCount = 0;
  private static Object sFutureMapLock = new Object();
  private static Object sRlistWriteLock = new Object();
  private static final ConcurrentMap<Long, Integer> sFuturesKeyResourceIdMap =
      new ConcurrentHashMap();
  private static final ConcurrentMap<Long, Boolean> sStartedRunnablesMap = new ConcurrentHashMap();
  private static final LongSparseArray<WeakReference<Future<Drawable.ConstantState>>>
      sResourcesFutureMap = new LongSparseArray<>();

  public interface UpdateCallbacks extends ResourcesLoader.UpdateCallbacks {
    void onLoadersChanged(Resources resources, List<ResourcesLoader> list);
  }

  static /* synthetic */ Thread lambda$new$0(Runnable r) {
    return new Thread(r, "queued-work-looper-data");
  }

  public static int selectDefaultTheme(int curTheme, int targetSdkVersion) {
    return selectSystemTheme(curTheme, targetSdkVersion, 16973829, 16973931, 16974120, 16974143);
  }

  public static int selectSystemTheme(
      int curTheme, int targetSdkVersion, int orig, int holo, int dark, int deviceDefault) {
    if (curTheme != 0) {
      return curTheme;
    }
    if (targetSdkVersion < 11) {
      return orig;
    }
    if (targetSdkVersion < 14) {
      return holo;
    }
    if (targetSdkVersion < 24) {
      return dark;
    }
    return deviceDefault;
  }

  public static Resources getSystem() {
    Resources ret;
    synchronized (sSync) {
      ret = mSystem;
      if (ret == null) {
        ret = new Resources();
        mSystem = ret;
      }
    }
    return ret;
  }

  public static class NotFoundException extends RuntimeException {
    public NotFoundException() {}

    public NotFoundException(String name) {
      super(name);
    }

    public NotFoundException(String name, Exception cause) {
      super(name, cause);
    }
  }

  public class AssetManagerUpdateHandler implements UpdateCallbacks {
    public AssetManagerUpdateHandler() {}

    @Override // android.content.res.Resources.UpdateCallbacks
    public void onLoadersChanged(Resources resources, List<ResourcesLoader> newLoaders) {
      Preconditions.checkArgument(Resources.this == resources);
      ResourcesImpl impl = Resources.this.mResourcesImpl;
      Resources.this.clearFutureCaches();
      impl.clearAllCaches();
      impl.getAssets().setLoaders(newLoaders);
    }

    @Override // android.content.res.loader.ResourcesLoader.UpdateCallbacks
    public void onLoaderUpdated(ResourcesLoader loader) {
      ResourcesImpl impl = Resources.this.mResourcesImpl;
      AssetManager assets = impl.getAssets();
      if (assets.getLoaders().contains(loader)) {
        Resources.this.clearFutureCaches();
        impl.clearAllCaches();
        assets.setLoaders(assets.getLoaders());
      }
    }
  }

  @Deprecated
  public Resources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
    this(null);
    this.mResourcesImpl = new ResourcesImpl(assets, metrics, config, new DisplayAdjustments());
  }

  public Resources(ClassLoader classLoader) {
    this.mUpdateLock = new Object();
    this.mTypedArrayPool = new Pools.SynchronizedPool<>(5);
    this.mTmpValueLock = new Object();
    this.mTmpValue = new TypedValue();
    this.mCallbacks = null;
    this.mThemeRefs = new ArrayList<>();
    this.mPackageName = null;
    this.mAppIconResId = 0;
    this.mUserId = 0;
    this.mThemeRefsNextFlushSize = 32;
    this.mThreadExecutor =
        Executors.newSingleThreadExecutor(
            new ThreadFactory() { // from class:
                                  // android.content.res.Resources$$ExternalSyntheticLambda0
              @Override // java.util.concurrent.ThreadFactory
              public final Thread newThread(Runnable runnable) {
                return Resources.lambda$new$0(runnable);
              }
            });
    this.mClassLoader = classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader;
    sResourcesHistory.add(this);
  }

  private Resources() {
    this(null);
    DisplayMetrics metrics = new DisplayMetrics();
    metrics.setToDefaults();
    Configuration config = new Configuration();
    config.setToDefaults();
    this.mResourcesImpl =
        new ResourcesImpl(AssetManager.getSystem(), metrics, config, new DisplayAdjustments());
  }

  public void setImpl(ResourcesImpl impl) {
    if (impl == this.mResourcesImpl) {
      return;
    }
    this.mBaseApkAssetsSize = ArrayUtils.size(impl.getAssets().getApkAssets());
    this.mResourcesImpl = impl;
    synchronized (this.mThemeRefs) {
      cleanupThemeReferences();
      int count = this.mThemeRefs.size();
      for (int i = 0; i < count; i++) {
        Theme theme = this.mThemeRefs.get(i).get();
        if (theme != null) {
          theme.rebase(this.mResourcesImpl);
        }
      }
    }
  }

  public void setCallbacks(UpdateCallbacks callbacks) {
    if (this.mCallbacks != null) {
      throw new IllegalStateException("callback already registered");
    }
    this.mCallbacks = callbacks;
  }

  public ResourcesImpl getImpl() {
    return this.mResourcesImpl;
  }

  public ClassLoader getClassLoader() {
    return this.mClassLoader;
  }

  public final DrawableInflater getDrawableInflater() {
    if (this.mDrawableInflater == null) {
      this.mDrawableInflater = new DrawableInflater(this, this.mClassLoader);
    }
    return this.mDrawableInflater;
  }

  public ConfigurationBoundResourceCache<Animator> getAnimatorCache() {
    return this.mResourcesImpl.getAnimatorCache();
  }

  public ConfigurationBoundResourceCache<StateListAnimator> getStateListAnimatorCache() {
    return this.mResourcesImpl.getStateListAnimatorCache();
  }

  public CharSequence getText(int id) throws NotFoundException {
    CharSequence res = this.mResourcesImpl.getAssets().getResourceText(id);
    if (res != null) {
      return res;
    }
    throw new NotFoundException("String resource ID #0x" + Integer.toHexString(id));
  }

  public Typeface getFont(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValue(id, value, true);
      Typeface typeface = impl.loadFont(this, value, id);
      if (typeface != null) {
        return typeface;
      }
      releaseTempTypedValue(value);
      throw new NotFoundException("Font resource ID #0x" + Integer.toHexString(id));
    } finally {
      releaseTempTypedValue(value);
    }
  }

  Typeface getFont(TypedValue value, int id) throws NotFoundException {
    return this.mResourcesImpl.loadFont(this, value, id);
  }

  public void preloadFonts(int id) {
    TypedArray array = obtainTypedArray(id);
    try {
      int size = array.length();
      for (int i = 0; i < size; i++) {
        array.getFont(i);
      }
    } finally {
      array.recycle();
    }
  }

  public CharSequence getQuantityText(int id, int quantity) throws NotFoundException {
    return this.mResourcesImpl.getQuantityText(id, quantity);
  }

  public String getString(int id) throws NotFoundException {
    return getText(id).toString();
  }

  public String getString(int id, Object... formatArgs) throws NotFoundException {
    String raw = getString(id);
    return String.format(
        this.mResourcesImpl.getConfiguration().getLocales().get(0), raw, formatArgs);
  }

  public String getQuantityString(int id, int quantity, Object... formatArgs)
      throws NotFoundException {
    String raw = getQuantityText(id, quantity).toString();
    return String.format(
        this.mResourcesImpl.getConfiguration().getLocales().get(0), raw, formatArgs);
  }

  public String getQuantityString(int id, int quantity) throws NotFoundException {
    return getQuantityText(id, quantity).toString();
  }

  public CharSequence getText(int id, CharSequence def) {
    CharSequence res = id != 0 ? this.mResourcesImpl.getAssets().getResourceText(id) : null;
    return res != null ? res : def;
  }

  public CharSequence[] getTextArray(int id) throws NotFoundException {
    CharSequence[] res = this.mResourcesImpl.getAssets().getResourceTextArray(id);
    if (res != null) {
      return res;
    }
    throw new NotFoundException("Text array resource ID #0x" + Integer.toHexString(id));
  }

  public String[] getStringArray(int id) throws NotFoundException {
    String[] res = this.mResourcesImpl.getAssets().getResourceStringArray(id);
    if (res != null) {
      return res;
    }
    throw new NotFoundException("String array resource ID #0x" + Integer.toHexString(id));
  }

  public int[] getIntArray(int id) throws NotFoundException {
    int[] res = this.mResourcesImpl.getAssets().getResourceIntArray(id);
    if (res != null) {
      return res;
    }
    throw new NotFoundException("Int array resource ID #0x" + Integer.toHexString(id));
  }

  public TypedArray obtainTypedArray(int id) throws NotFoundException {
    ResourcesImpl impl = this.mResourcesImpl;
    int len = impl.getAssets().getResourceArraySize(id);
    if (len < 0) {
      throw new NotFoundException("Array resource ID #0x" + Integer.toHexString(id));
    }
    TypedArray array = TypedArray.obtain(this, len);
    array.mLength = impl.getAssets().getResourceArray(id, array.mData);
    array.mIndices[0] = 0;
    return array;
  }

  public float getDimension(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValue(id, value, true);
      if (value.type == 5) {
        return TypedValue.complexToDimension(value.data, impl.getDisplayMetrics());
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  public int getDimensionPixelOffset(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValue(id, value, true);
      if (value.type == 5) {
        return TypedValue.complexToDimensionPixelOffset(value.data, impl.getDisplayMetrics());
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  public int getDimensionPixelSize(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValue(id, value, true);
      if (value.type == 5) {
        return TypedValue.complexToDimensionPixelSize(value.data, impl.getDisplayMetrics());
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  public float getFraction(int id, int base, int pbase) {
    TypedValue value = obtainTempTypedValue();
    try {
      this.mResourcesImpl.getValue(id, value, true);
      if (value.type == 6) {
        return TypedValue.complexToFraction(value.data, base, pbase);
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  @Deprecated
  public Drawable getDrawable(int id) throws NotFoundException {
    Drawable d = getDrawable(id, null);
    if (d != null && d.canApplyTheme()) {
      Log.m103w(
          TAG,
          "Drawable "
              + getResourceName(id)
              + " has unresolved theme attributes! Consider using Resources.getDrawable(int, Theme)"
              + " or Context.getDrawable(int).",
          new RuntimeException());
    }
    return d;
  }

  public Drawable getDrawable(int id, Theme theme) throws NotFoundException {
    return getDrawableForDensity(id, 0, theme);
  }

  @Deprecated
  public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
    return getDrawableForDensity(id, density, null);
  }

  public Drawable getDrawableForDensity(int id, int density, Theme theme) {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValueForDensity(id, density, value, true);
      return loadDrawable(value, id, density, theme);
    } finally {
      releaseTempTypedValue(value);
    }
  }

  Drawable loadDrawable(TypedValue value, int id, int density, Theme theme)
      throws NotFoundException {
    Drawable dr = null;
    boolean checkOffloadedResources = false;
    if (sIsAppLaunching
        && (id >>> 24) != 1
        && Process.isApplicationUid(Process.myUid())
        && Process.myPid() == Process.myTid()
        && (density == 0 || value.density == this.mResourcesImpl.getDisplayMetrics().densityDpi)) {
      checkOffloadedResources = true;
      dr = fetchDrawableFromFutureCache(value, id, theme);
    }
    if (dr == null) {
      dr = this.mResourcesImpl.loadDrawable(this, value, id, density, theme);
    }
    if (checkOffloadedResources && dr != null) {
      this.mThreadExecutor.execute(new UpdateResourceList(id, value));
    }
    return dr;
  }

  @Deprecated
  public Movie getMovie(int id) throws NotFoundException {
    InputStream is = openRawResource(id);
    Movie movie = Movie.decodeStream(is);
    try {
      is.close();
    } catch (IOException e) {
    }
    return movie;
  }

  @Deprecated
  public int getColor(int id) throws NotFoundException {
    return getColor(id, null);
  }

  public int getColor(int id, Theme theme) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValue(id, value, true);
      if (value.type >= 16 && value.type <= 31) {
        return value.data;
      }
      if (value.type != 3) {
        throw new NotFoundException(
            "Resource ID #0x"
                + Integer.toHexString(id)
                + " type #0x"
                + Integer.toHexString(value.type)
                + " is not valid");
      }
      ColorStateList csl = impl.loadColorStateList(this, value, id, theme);
      return csl.getDefaultColor();
    } finally {
      releaseTempTypedValue(value);
    }
  }

  @Deprecated
  public ColorStateList getColorStateList(int id) throws NotFoundException {
    ColorStateList csl = getColorStateList(id, null);
    if (csl != null && csl.canApplyTheme()) {
      Log.m103w(
          TAG,
          "ColorStateList "
              + getResourceName(id)
              + " has unresolved theme attributes! Consider using Resources.getColorStateList(int,"
              + " Theme) or Context.getColorStateList(int).",
          new RuntimeException());
    }
    return csl;
  }

  public ColorStateList getColorStateList(int id, Theme theme) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValue(id, value, true);
      return impl.loadColorStateList(this, value, id, theme);
    } finally {
      releaseTempTypedValue(value);
    }
  }

  ColorStateList loadColorStateList(TypedValue value, int id, Theme theme)
      throws NotFoundException {
    return this.mResourcesImpl.loadColorStateList(this, value, id, theme);
  }

  public ComplexColor loadComplexColor(TypedValue value, int id, Theme theme) {
    return this.mResourcesImpl.loadComplexColor(this, value, id, theme);
  }

  public boolean getBoolean(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      this.mResourcesImpl.getValue(id, value, true);
      if (value.type >= 16 && value.type <= 31) {
        return value.data != 0;
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  public int getInteger(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      this.mResourcesImpl.getValue(id, value, true);
      if (value.type >= 16 && value.type <= 31) {
        return value.data;
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  public float getFloat(int id) {
    TypedValue value = obtainTempTypedValue();
    try {
      this.mResourcesImpl.getValue(id, value, true);
      if (value.type == 4) {
        return value.getFloat();
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  public XmlResourceParser getLayout(int id) throws NotFoundException {
    return loadXmlResourceParser(id, TtmlUtils.TAG_LAYOUT);
  }

  public XmlResourceParser getAnimation(int id) throws NotFoundException {
    return loadXmlResourceParser(id, "anim");
  }

  public XmlResourceParser getXml(int id) throws NotFoundException {
    return loadXmlResourceParser(id, "xml");
  }

  public InputStream openRawResource(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      return openRawResource(id, value);
    } finally {
      releaseTempTypedValue(value);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public TypedValue obtainTempTypedValue() {
    TypedValue tmpValue = null;
    synchronized (this.mTmpValueLock) {
      TypedValue typedValue = this.mTmpValue;
      if (typedValue != null) {
        tmpValue = typedValue;
        this.mTmpValue = null;
      }
    }
    if (tmpValue == null) {
      return new TypedValue();
    }
    return tmpValue;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void releaseTempTypedValue(TypedValue value) {
    synchronized (this.mTmpValueLock) {
      if (this.mTmpValue == null) {
        this.mTmpValue = value;
      }
    }
  }

  public InputStream openRawResource(int id, TypedValue value) throws NotFoundException {
    return this.mResourcesImpl.openRawResource(id, value);
  }

  public AssetFileDescriptor openRawResourceFd(int id) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      return this.mResourcesImpl.openRawResourceFd(id, value);
    } finally {
      releaseTempTypedValue(value);
    }
  }

  public void getValue(int id, TypedValue outValue, boolean resolveRefs) throws NotFoundException {
    this.mResourcesImpl.getValue(id, outValue, resolveRefs);
  }

  public void getValueForDensity(int id, int density, TypedValue outValue, boolean resolveRefs)
      throws NotFoundException {
    this.mResourcesImpl.getValueForDensity(id, density, outValue, resolveRefs);
  }

  public void getValue(String name, TypedValue outValue, boolean resolveRefs)
      throws NotFoundException {
    this.mResourcesImpl.getValue(name, outValue, resolveRefs);
  }

  public static int getAttributeSetSourceResId(AttributeSet set) {
    return ResourcesImpl.getAttributeSetSourceResId(set);
  }

  public final class Theme {
    private static final int MAX_NUMBER_OF_TRACING_PARENT_THEME = 100;
    private final Object mLock;
    private ResourcesImpl.ThemeImpl mThemeImpl;

    private Theme() {
      this.mLock = new Object();
    }

    void setImpl(ResourcesImpl.ThemeImpl impl) {
      synchronized (this.mLock) {
        this.mThemeImpl = impl;
      }
    }

    public void applyStyle(int resId, boolean force) {
      synchronized (this.mLock) {
        this.mThemeImpl.applyStyle(resId, force);
      }
    }

    public void setTo(Theme other) {
      synchronized (this.mLock) {
        synchronized (other.mLock) {
          this.mThemeImpl.setTo(other.mThemeImpl);
        }
      }
    }

    public TypedArray obtainStyledAttributes(int[] attrs) {
      TypedArray obtainStyledAttributes;
      synchronized (this.mLock) {
        obtainStyledAttributes = this.mThemeImpl.obtainStyledAttributes(this, null, attrs, 0, 0);
      }
      return obtainStyledAttributes;
    }

    public TypedArray obtainStyledAttributes(int resId, int[] attrs) throws NotFoundException {
      TypedArray obtainStyledAttributes;
      synchronized (this.mLock) {
        obtainStyledAttributes =
            this.mThemeImpl.obtainStyledAttributes(this, null, attrs, 0, resId);
      }
      return obtainStyledAttributes;
    }

    public TypedArray obtainStyledAttributes(
        AttributeSet set, int[] attrs, int defStyleAttr, int defStyleRes) {
      TypedArray obtainStyledAttributes;
      synchronized (this.mLock) {
        obtainStyledAttributes =
            this.mThemeImpl.obtainStyledAttributes(this, set, attrs, defStyleAttr, defStyleRes);
      }
      return obtainStyledAttributes;
    }

    public TypedArray resolveAttributes(int[] values, int[] attrs) {
      TypedArray resolveAttributes;
      synchronized (this.mLock) {
        resolveAttributes = this.mThemeImpl.resolveAttributes(this, values, attrs);
      }
      return resolveAttributes;
    }

    public boolean resolveAttribute(int resid, TypedValue outValue, boolean resolveRefs) {
      boolean resolveAttribute;
      synchronized (this.mLock) {
        resolveAttribute = this.mThemeImpl.resolveAttribute(resid, outValue, resolveRefs);
      }
      return resolveAttribute;
    }

    public int[] getAllAttributes() {
      int[] allAttributes;
      synchronized (this.mLock) {
        allAttributes = this.mThemeImpl.getAllAttributes();
      }
      return allAttributes;
    }

    public Resources getResources() {
      return Resources.this;
    }

    public Drawable getDrawable(int id) throws NotFoundException {
      return Resources.this.getDrawable(id, this);
    }

    public int getChangingConfigurations() {
      int changingConfigurations;
      synchronized (this.mLock) {
        changingConfigurations = this.mThemeImpl.getChangingConfigurations();
      }
      return changingConfigurations;
    }

    public void dump(int priority, String tag, String prefix) {
      synchronized (this.mLock) {
        this.mThemeImpl.dump(priority, tag, prefix);
      }
    }

    long getNativeTheme() {
      long nativeTheme;
      synchronized (this.mLock) {
        nativeTheme = this.mThemeImpl.getNativeTheme();
      }
      return nativeTheme;
    }

    int getAppliedStyleResId() {
      int appliedStyleResId;
      synchronized (this.mLock) {
        appliedStyleResId = this.mThemeImpl.getAppliedStyleResId();
      }
      return appliedStyleResId;
    }

    int getParentThemeIdentifier(int resId) {
      int parentThemeIdentifier;
      synchronized (this.mLock) {
        parentThemeIdentifier = this.mThemeImpl.getParentThemeIdentifier(resId);
      }
      return parentThemeIdentifier;
    }

    public ThemeKey getKey() {
      ThemeKey key;
      synchronized (this.mLock) {
        key = this.mThemeImpl.getKey();
      }
      return key;
    }

    private String getResourceNameFromHexString(String hexString) {
      return Resources.this.getResourceName(Integer.parseInt(hexString, 16));
    }

    @ViewDebug.ExportedProperty(category = "theme", hasAdjacentMapping = true)
    public String[] getTheme() {
      String[] theme;
      synchronized (this.mLock) {
        theme = this.mThemeImpl.getTheme();
      }
      return theme;
    }

    public void encode(ViewHierarchyEncoder encoder) {
      encoder.beginObject(this);
      String[] properties = getTheme();
      for (int i = 0; i < properties.length; i += 2) {
        encoder.addProperty(properties[i], properties[i + 1]);
      }
      encoder.endObject();
    }

    public void rebase() {
      synchronized (this.mLock) {
        this.mThemeImpl.rebase();
      }
    }

    void rebase(ResourcesImpl resImpl) {
      synchronized (this.mLock) {
        this.mThemeImpl.rebase(resImpl.mAssets);
      }
    }

    public int getExplicitStyle(AttributeSet set) {
      int styleAttr;
      if (set == null || (styleAttr = set.getStyleAttribute()) == 0) {
        return 0;
      }
      String styleAttrType = getResources().getResourceTypeName(styleAttr);
      if ("attr".equals(styleAttrType)) {
        TypedValue explicitStyle = new TypedValue();
        boolean resolved = resolveAttribute(styleAttr, explicitStyle, true);
        if (resolved) {
          return explicitStyle.resourceId;
        }
      } else if ("style".equals(styleAttrType)) {
        return styleAttr;
      }
      return 0;
    }

    public int[] getAttributeResolutionStack(
        int defStyleAttr, int defStyleRes, int explicitStyleRes) {
      synchronized (this.mLock) {
        int[] stack =
            this.mThemeImpl.getAttributeResolutionStack(
                defStyleAttr, defStyleRes, explicitStyleRes);
        if (stack != null) {
          return stack;
        }
        return new int[0];
      }
    }

    public int hashCode() {
      return getKey().hashCode();
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass() || hashCode() != o.hashCode()) {
        return false;
      }
      Theme other = (Theme) o;
      return getKey().equals(other.getKey());
    }

    public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append('{');
      int themeResId = getAppliedStyleResId();
      int i = 0;
      sb.append("InheritanceMap=[");
      while (true) {
        if (themeResId <= 0) {
          break;
        }
        if (i > 100) {
          sb.append(",...");
          break;
        }
        if (i > 0) {
          sb.append(", ");
        }
        sb.append("id=0x").append(Integer.toHexString(themeResId));
        sb.append(Resources.this.getResourcePackageName(themeResId))
            .append(":")
            .append(Resources.this.getResourceTypeName(themeResId))
            .append("/")
            .append(Resources.this.getResourceEntryName(themeResId));
        i++;
        themeResId = getParentThemeIdentifier(themeResId);
      }
      sb.append("], Themes=").append(Arrays.deepToString(getTheme()));
      sb.append('}');
      return sb.toString();
    }
  }

  static class ThemeKey implements Cloneable {
    int mCount;
    boolean[] mForce;
    private int mHashCode = 0;
    int[] mResId;

    ThemeKey() {}

    private int findValue(int resId, boolean force) {
      for (int i = 0; i < this.mCount; i++) {
        if (this.mResId[i] == resId && this.mForce[i] == force) {
          return i;
        }
      }
      return -1;
    }

    private void moveToLast(int index) {
      if (index >= 0) {
        if (index >= this.mCount - 1) {
          return;
        }
        int[] iArr = this.mResId;
        int id = iArr[index];
        boolean force = this.mForce[index];
        System.arraycopy(iArr, index + 1, iArr, index, (r0 - index) - 1);
        this.mResId[this.mCount - 1] = id;
        boolean[] zArr = this.mForce;
        System.arraycopy(zArr, index + 1, zArr, index, (r1 - index) - 1);
        this.mForce[this.mCount - 1] = force;
      }
    }

    public void append(int i, boolean z) {
      if (this.mResId == null) {
        this.mResId = new int[4];
      }
      if (this.mForce == null) {
        this.mForce = new boolean[4];
      }
      int findValue = findValue(i, z);
      if (findValue >= 0) {
        moveToLast(findValue);
        return;
      }
      this.mResId = GrowingArrayUtils.append(this.mResId, this.mCount, i);
      this.mForce = GrowingArrayUtils.append(this.mForce, this.mCount, z);
      this.mCount++;
      this.mHashCode = (((this.mHashCode * 31) + i) * 31) + (z ? 1 : 0);
    }

    public void setTo(ThemeKey other) {
      int[] iArr = other.mResId;
      this.mResId = iArr == null ? null : (int[]) iArr.clone();
      boolean[] zArr = other.mForce;
      this.mForce = zArr != null ? (boolean[]) zArr.clone() : null;
      this.mCount = other.mCount;
      this.mHashCode = other.mHashCode;
    }

    public int hashCode() {
      return this.mHashCode;
    }

    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass() || hashCode() != o.hashCode()) {
        return false;
      }
      ThemeKey t = (ThemeKey) o;
      if (this.mCount != t.mCount) {
        return false;
      }
      int N = this.mCount;
      for (int i = 0; i < N; i++) {
        if (this.mResId[i] != t.mResId[i] || this.mForce[i] != t.mForce[i]) {
          return false;
        }
      }
      return true;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ThemeKey m1427clone() {
      ThemeKey other = new ThemeKey();
      other.mResId = this.mResId;
      other.mForce = this.mForce;
      other.mCount = this.mCount;
      other.mHashCode = this.mHashCode;
      return other;
    }
  }

  static int nextPowerOf2(int number) {
    if (number < 2) {
      return 2;
    }
    return 1 >> (((int) (Math.log(number - 1) / Math.log(2.0d))) + 1);
  }

  private void cleanupThemeReferences() {
    if (this.mThemeRefs.size() > this.mThemeRefsNextFlushSize) {
      this.mThemeRefs.removeIf(
          new Predicate() { // from class: android.content.res.Resources$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
              boolean refersTo;
              refersTo = ((WeakReference) obj).refersTo(null);
              return refersTo;
            }
          });
      this.mThemeRefsNextFlushSize =
          Math.min(Math.max(32, nextPowerOf2(this.mThemeRefs.size())), 512);
    }
  }

  public final Theme newTheme() {
    Theme theme = new Theme();
    theme.setImpl(this.mResourcesImpl.newThemeImpl());
    synchronized (this.mThemeRefs) {
      cleanupThemeReferences();
      this.mThemeRefs.add(new WeakReference<>(theme));
    }
    return theme;
  }

  public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
    int len = attrs.length;
    TypedArray array = TypedArray.obtain(this, len);
    XmlBlock.Parser parser = (XmlBlock.Parser) set;
    this.mResourcesImpl.getAssets().retrieveAttributes(parser, attrs, array.mData, array.mIndices);
    array.mXml = parser;
    return array;
  }

  @Deprecated
  public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
    updateConfiguration(config, metrics, null);
  }

  public void updateConfiguration(
      Configuration config, DisplayMetrics metrics, CompatibilityInfo compat) {
    this.mResourcesImpl.updateConfiguration(config, metrics, compat);
  }

  public static void updateSystemConfiguration(
      Configuration config, DisplayMetrics metrics, CompatibilityInfo compat) {
    Resources resources = mSystem;
    if (resources != null) {
      resources.updateConfiguration(config, metrics, compat);
    }
  }

  public DisplayMetrics getDisplayMetrics() {
    return this.mResourcesImpl.getDisplayMetrics();
  }

  public DisplayAdjustments getDisplayAdjustments() {
    return this.mResourcesImpl.getDisplayAdjustments();
  }

  public boolean hasOverrideDisplayAdjustments() {
    return false;
  }

  public Configuration getConfiguration() {
    return this.mResourcesImpl.getConfiguration();
  }

  public Configuration[] getSizeConfigurations() {
    return this.mResourcesImpl.getSizeConfigurations();
  }

  public Configuration[] getSizeAndUiModeConfigurations() {
    return this.mResourcesImpl.getSizeAndUiModeConfigurations();
  }

  public CompatibilityInfo getCompatibilityInfo() {
    return this.mResourcesImpl.getCompatibilityInfo();
  }

  public void setCompatibilityInfo(CompatibilityInfo ci) {
    if (ci != null) {
      this.mResourcesImpl.updateConfiguration(null, null, ci);
    }
  }

  public int getIdentifier(String name, String defType, String defPackage) {
    return this.mResourcesImpl.getIdentifier(name, defType, defPackage);
  }

  public static boolean resourceHasPackage(int resid) {
    return (resid >>> 24) != 0;
  }

  public String getResourceName(int resid) throws NotFoundException {
    return this.mResourcesImpl.getResourceName(resid);
  }

  public String getResourcePackageName(int resid) throws NotFoundException {
    return this.mResourcesImpl.getResourcePackageName(resid);
  }

  public String getResourceTypeName(int resid) throws NotFoundException {
    return this.mResourcesImpl.getResourceTypeName(resid);
  }

  public String getResourceEntryName(int resid) throws NotFoundException {
    return this.mResourcesImpl.getResourceEntryName(resid);
  }

  public String getLastResourceResolution() throws NotFoundException {
    return this.mResourcesImpl.getLastResourceResolution();
  }

  public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle)
      throws XmlPullParserException, IOException {
    int outerDepth = parser.getDepth();
    while (true) {
      int type = parser.next();
      if (type != 1) {
        if (type != 3 || parser.getDepth() > outerDepth) {
          if (type != 3 && type != 4) {
            String nodeName = parser.getName();
            if (nodeName.equals(SemShareConstants.SURVEY_CONTENT_EXTRA)) {
              parseBundleExtra(SemShareConstants.SURVEY_CONTENT_EXTRA, parser, outBundle);
              XmlUtils.skipCurrentTag(parser);
            } else {
              XmlUtils.skipCurrentTag(parser);
            }
          }
        } else {
          return;
        }
      } else {
        return;
      }
    }
  }

  public void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle)
      throws XmlPullParserException {
    TypedArray sa = obtainAttributes(attrs, C4337R.styleable.Extra);
    boolean z = false;
    String name = sa.getString(0);
    if (name == null) {
      sa.recycle();
      throw new XmlPullParserException(
          "<"
              + tagName
              + "> requires an android:name attribute at "
              + attrs.getPositionDescription());
    }
    TypedValue v = sa.peekValue(1);
    if (v != null) {
      if (v.type == 3) {
        CharSequence cs = v.coerceToString();
        outBundle.putCharSequence(name, cs);
      } else if (v.type == 18) {
        if (v.data != 0) {
          z = true;
        }
        outBundle.putBoolean(name, z);
      } else if (v.type >= 16 && v.type <= 31) {
        outBundle.putInt(name, v.data);
      } else if (v.type == 4) {
        outBundle.putFloat(name, v.getFloat());
      } else {
        sa.recycle();
        throw new XmlPullParserException(
            "<"
                + tagName
                + "> only supports string, integer, float, color, and boolean at "
                + attrs.getPositionDescription());
      }
      sa.recycle();
      return;
    }
    sa.recycle();
    throw new XmlPullParserException(
        "<"
            + tagName
            + "> requires an android:value or android:resource attribute at "
            + attrs.getPositionDescription());
  }

  public final AssetManager getAssets() {
    return this.mResourcesImpl.getAssets();
  }

  public final void flushLayoutCache() {
    this.mResourcesImpl.flushLayoutCache();
  }

  public final void startPreloading() {
    this.mResourcesImpl.startPreloading();
  }

  public final void finishPreloading() {
    this.mResourcesImpl.finishPreloading();
  }

  public LongSparseArray<Drawable.ConstantState> getPreloadedDrawables() {
    return this.mResourcesImpl.getPreloadedDrawables();
  }

  XmlResourceParser loadXmlResourceParser(int id, String type) throws NotFoundException {
    TypedValue value = obtainTempTypedValue();
    try {
      ResourcesImpl impl = this.mResourcesImpl;
      impl.getValue(id, value, true);
      if (value.type == 3) {
        return loadXmlResourceParser(value.string.toString(), id, value.assetCookie, type);
      }
      throw new NotFoundException(
          "Resource ID #0x"
              + Integer.toHexString(id)
              + " type #0x"
              + Integer.toHexString(value.type)
              + " is not valid");
    } finally {
      releaseTempTypedValue(value);
    }
  }

  XmlResourceParser loadXmlResourceParser(String file, int id, int assetCookie, String type)
      throws NotFoundException {
    return this.mResourcesImpl.loadXmlResourceParser(file, id, assetCookie, type);
  }

  public int calcConfigChanges(Configuration config) {
    return this.mResourcesImpl.calcConfigChanges(config);
  }

  public static TypedArray obtainAttributes(
      Resources res, Theme theme, AttributeSet set, int[] attrs) {
    if (theme == null) {
      return res.obtainAttributes(set, attrs);
    }
    return theme.obtainStyledAttributes(set, attrs, 0, 0);
  }

  private void checkCallbacksRegistered() {
    if (this.mCallbacks == null) {
      this.mCallbacks = new AssetManagerUpdateHandler();
    }
  }

  public List<ResourcesLoader> getLoaders() {
    return this.mResourcesImpl.getAssets().getLoaders();
  }

  public void addLoaders(ResourcesLoader... loaders) {
    synchronized (this.mUpdateLock) {
      checkCallbacksRegistered();
      List<ResourcesLoader> newLoaders =
          new ArrayList<>(this.mResourcesImpl.getAssets().getLoaders());
      ArraySet<ResourcesLoader> loaderSet = new ArraySet<>(newLoaders);
      for (ResourcesLoader loader : loaders) {
        if (!loaderSet.contains(loader)) {
          newLoaders.add(loader);
        }
      }
      int i = loaderSet.size();
      if (i == newLoaders.size()) {
        return;
      }
      this.mCallbacks.onLoadersChanged(this, newLoaders);
      int n = newLoaders.size();
      for (int i2 = loaderSet.size(); i2 < n; i2++) {
        newLoaders.get(i2).registerOnProvidersChangedCallback(this, this.mCallbacks);
      }
    }
  }

  public void removeLoaders(ResourcesLoader... loaders) {
    synchronized (this.mUpdateLock) {
      checkCallbacksRegistered();
      ArraySet<ResourcesLoader> removedLoaders = new ArraySet<>(loaders);
      List<ResourcesLoader> newLoaders = new ArrayList<>();
      List<ResourcesLoader> oldLoaders = this.mResourcesImpl.getAssets().getLoaders();
      int n = oldLoaders.size();
      for (int i = 0; i < n; i++) {
        ResourcesLoader loader = oldLoaders.get(i);
        if (!removedLoaders.contains(loader)) {
          newLoaders.add(loader);
        }
      }
      int i2 = oldLoaders.size();
      if (i2 == newLoaders.size()) {
        return;
      }
      this.mCallbacks.onLoadersChanged(this, newLoaders);
      for (ResourcesLoader resourcesLoader : loaders) {
        resourcesLoader.unregisterOnProvidersChangedCallback(this);
      }
    }
  }

  public void clearLoaders() {
    synchronized (this.mUpdateLock) {
      checkCallbacksRegistered();
      List<ResourcesLoader> newLoaders = Collections.emptyList();
      List<ResourcesLoader> oldLoaders = this.mResourcesImpl.getAssets().getLoaders();
      this.mCallbacks.onLoadersChanged(this, newLoaders);
      for (ResourcesLoader loader : oldLoaders) {
        loader.unregisterOnProvidersChangedCallback(this);
      }
    }
  }

  public void dump(PrintWriter pw, String prefix) {
    pw.println(prefix + "class=" + getClass());
    pw.println(prefix + "resourcesImpl");
    this.mResourcesImpl.dump(pw, prefix + "  ");
  }

  public static void dumpHistory(PrintWriter pw, String prefix) {
    pw.println(prefix + "history");
    final Map<List<ApkAssets>, Resources> history = new ArrayMap<>();
    try {
      sResourcesHistory.forEach(
          new Consumer() { // from class: android.content.res.Resources$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
              history.put(Arrays.asList(r2.mResourcesImpl.mAssets.getApkAssets()), (Resources) obj);
            }
          });
      int i = 0;
      for (Resources r : history.values()) {
        if (r != null) {
          int i2 = i + 1;
          pw.println(prefix + i);
          r.dump(pw, prefix + "  ");
          i = i2;
        }
      }
    } catch (NullPointerException e) {
      Log.m97e(TAG, "NPE occurred, stop dumping : ", e);
    }
  }

  public static void setApplicationContext(Context c) {
    sAppContext = c;
  }

  public static void setIfAppLaunching(boolean IfAppLaunchingInProgress) {
    sIsAppLaunching = IfAppLaunchingInProgress;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void clearFutureCaches() {
    sFuturesKeyResourceIdMap.clear();
    synchronized (sFutureMapLock) {
      sResourcesFutureMap.clear();
    }
    sStartedRunnablesMap.clear();
  }

  private Future<Drawable.ConstantState> getResourceFuture(int id, long key) {
    WeakReference<Future<Drawable.ConstantState>> resourceFutureRef;
    Integer futureResId = null;
    ConcurrentMap<Long, Integer> concurrentMap = sFuturesKeyResourceIdMap;
    if (concurrentMap != null) {
      Integer futureResId2 = concurrentMap.get(Long.valueOf(key));
      futureResId = futureResId2;
    }
    if (futureResId != null && futureResId.intValue() == id) {
      synchronized (sFutureMapLock) {
        LongSparseArray<WeakReference<Future<Drawable.ConstantState>>> longSparseArray =
            sResourcesFutureMap;
        if (longSparseArray != null && (resourceFutureRef = longSparseArray.get(key)) != null) {
          return resourceFutureRef.get();
        }
        return null;
      }
    }
    return null;
  }

  private boolean verifyOffloadedResourceConfig(int changingConfigurations) {
    return ((-1073745921) & changingConfigurations) == 0;
  }

  private long calculateKey(TypedValue value) {
    if (value.type >= 28 && value.type <= 31) {
      long key = value.data;
      return key;
    }
    long key2 = (value.assetCookie << 32) | value.data;
    return key2;
  }

  private Drawable fetchDrawableFromFutureCache(TypedValue value, int id, Theme theme)
      throws NotFoundException {
    Drawable dr;
    try {
      long key = calculateKey(value);
      Future<Drawable.ConstantState> resourceFuture = getResourceFuture(id, key);
      if (resourceFuture != null) {
        if (sStartedRunnablesMap.get(Long.valueOf(key)) == null) {
          resourceFuture.cancel(true);
          return null;
        }
        Drawable.ConstantState cs = resourceFuture.get(2L, TimeUnit.SECONDS);
        if (cs != null
            && verifyOffloadedResourceConfig(cs.getChangingConfigurations())
            && (dr = cs.newDrawable(this, theme)) != null) {
          if (theme != null && dr.canApplyTheme()) {
            dr.applyTheme(theme);
          }
          dr.setChangingConfigurations(value.changingConfigurations);
          return dr;
        }
        return null;
      }
      return null;
    } catch (InterruptedException
        | NullPointerException
        | CancellationException
        | ExecutionException
        | TimeoutException e) {
      Log.m97e(TAG, "An exception occurred : ", e);
      return null;
    }
  }

  private static class UpdateResourceList implements Runnable {
    int resourceId;
    TypedValue value;

    UpdateResourceList(int resourceId, TypedValue value) {
      this.resourceId = resourceId;
      this.value = value;
    }

    @Override // java.lang.Runnable
    public void run() {
      String str;
      StringBuilder sb;
      FileOutputStream fos = null;
      ObjectOutputStream oos = null;
      try {
        try {
          String filename = SemCapabilities.FEATURE_TAG_NULL;
          TypedValue typedValue = this.value;
          if (typedValue != null) {
            filename = String.valueOf(typedValue.string);
          }
          if (Resources.sResourceCount <= 30
              && !filename.equals(SemCapabilities.FEATURE_TAG_NULL)
              && (filename.endsWith(".png")
                  || filename.endsWith(".xml")
                  || filename.endsWith(".webp"))) {
            synchronized (Resources.sRlistWriteLock) {
              Resources.sResourceList.add(String.valueOf(this.resourceId));
            }
            Resources.sResourceCount++;
            if (Resources.sAppContext != null) {
              Context context = Resources.sAppContext;
              Context unused = Resources.sAppContext;
              fos = context.openFileOutput("rList", 0);
              oos = new ObjectOutputStream(fos);
              synchronized (Resources.sRlistWriteLock) {
                oos.writeObject(Resources.sResourceList);
              }
              oos.flush();
            }
          }
          if (oos != null) {
            try {
              oos.close();
            } catch (IOException e) {
              Log.m102w(Resources.TAG, "IOException occured at : " + e.getMessage());
            }
          }
          if (fos != null) {
            try {
              fos.close();
            } catch (IOException e2) {
              e = e2;
              str = Resources.TAG;
              sb = new StringBuilder();
              Log.m102w(
                  str, sb.append("IOException occured at : ").append(e.getMessage()).toString());
            }
          }
        } catch (Throwable th) {
          if (0 != 0) {
            try {
              oos.close();
            } catch (IOException e3) {
              Log.m102w(Resources.TAG, "IOException occured at : " + e3.getMessage());
            }
          }
          if (0 != 0) {
            try {
              fos.close();
              throw th;
            } catch (IOException e4) {
              Log.m102w(Resources.TAG, "IOException occured at : " + e4.getMessage());
              throw th;
            }
          }
          throw th;
        }
      } catch (Exception e5) {
        Log.m103w(Resources.TAG, "An exception occurred : ", e5);
        if (0 != 0) {
          try {
            oos.close();
          } catch (IOException e6) {
            Log.m102w(Resources.TAG, "IOException occured at : " + e6.getMessage());
          }
        }
        if (0 != 0) {
          try {
            fos.close();
          } catch (IOException e7) {
            e = e7;
            str = Resources.TAG;
            sb = new StringBuilder();
            Log.m102w(
                str, sb.append("IOException occured at : ").append(e.getMessage()).toString());
          }
        }
      }
    }
  }

  public void offloadDrawable(int id, int density) {
    TypedValue value = obtainTempTypedValue();
    try {
      try {
        this.mResourcesImpl.getValueForDensity(id, density, value, true);
        long key = calculateKey(value);
        sFuturesKeyResourceIdMap.put(Long.valueOf(key), Integer.valueOf(id));
        synchronized (sFutureMapLock) {
          sResourcesFutureMap.put(
              key,
              new WeakReference<>(
                  this.mThreadExecutor.submit(new submitToFuture(id, density, key))));
        }
        releaseTempTypedValue(value);
      } catch (Throwable th) {
        th = th;
        releaseTempTypedValue(value);
        throw th;
      }
    } catch (Throwable th2) {
      th = th2;
    }
  }

  private final class submitToFuture implements Callable<Drawable.ConstantState> {
    private int density;

    /* renamed from: id */
    private int f55id;
    private long key;

    public submitToFuture(int id, int density, long key) {
      this.f55id = id;
      this.density = density;
      this.key = key;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.concurrent.Callable
    public Drawable.ConstantState call() throws Exception {
      Resources.sStartedRunnablesMap.put(Long.valueOf(this.key), true);
      TypedValue value = Resources.this.obtainTempTypedValue();
      try {
        ResourcesImpl impl = Resources.this.mResourcesImpl;
        impl.getValueForDensity(this.f55id, this.density, value, true);
        Drawable dr = impl.loadDrawable(Resources.this, value, this.f55id, this.density);
        if (dr != null) {
          return dr.getConstantState();
        }
        Resources.this.releaseTempTypedValue(value);
        return null;
      } finally {
        Resources.this.releaseTempTypedValue(value);
      }
    }
  }
}
