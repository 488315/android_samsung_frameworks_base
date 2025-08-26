package android.content.res;

import android.animation.Animator;
import android.animation.StateListAnimator;
import android.app.ResourcesManager;
import android.content.pm.ApplicationInfo;
import android.content.res.ResourcesImpl;
import android.content.res.XmlBlock;
import android.content.res.loader.ResourcesLoader;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableInflater;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.TtmlUtils;
import android.os.Bundle;
import android.os.SystemClock;
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
import com.android.internal.R;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.samsung.android.share.SemShareConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Resources {
    public static final int ID_NULL = 0;
    private static final int MAX_THEME_REFS_FLUSH_SIZE = 512;
    private static final int MIN_THEME_REFS_FLUSH_SIZE = 32;
    private static final boolean PRELOAD_RESOURCES = true;
    static final String TAG = "Resources";
    static Resources mSystem;
    public int mAppIconResId;
    private int mBaseApkAssetsSize;
    private UpdateCallbacks mCallbacks;
    final ClassLoader mClassLoader;
    private DrawableInflater mDrawableInflater;
    public String mPackageName;
    private ResourcesImpl mResourcesImpl;
    private final ArrayList<WeakReference<Theme>> mThemeRefs;
    private int mThemeRefsNextFlushSize;
    private TypedValue mTmpValue;
    private final Object mTmpValueLock;
    final Pools.SynchronizedPool<TypedArray> mTypedArrayPool;
    private final Object mUpdateLock;
    public int mUserId;
    private static final Object sSync = new Object();
    private static final Set<Resources> sResourcesHistory = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));

    public interface UpdateCallbacks extends ResourcesLoader.UpdateCallbacks {
        void onLoadersChanged(Resources resources, List<ResourcesLoader> list);
    }

    public static boolean resourceHasPackage(int i) {
        return (i >>> 24) != 0;
    }

    public static int selectSystemTheme(int i, int i2, int i3, int i4, int i5, int i6) {
        return i != 0 ? i : i2 < 11 ? i3 : i2 < 14 ? i4 : i2 < 24 ? i5 : i6;
    }

    public boolean hasOverrideDisplayAdjustments() {
        return false;
    }

    public static int selectDefaultTheme(int i, int i2) {
        return selectSystemTheme(i, i2, 16973829, 16973931, 16974120, 16974143);
    }

    public static Resources getSystem() {
        Resources resources;
        synchronized (sSync) {
            resources = mSystem;
            if (resources == null) {
                resources = new Resources();
                mSystem = resources;
            }
        }
        return resources;
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
        }

        public NotFoundException(String str) {
            super(str);
        }

        public NotFoundException(String str, Exception exc) {
            super(str, exc);
        }
    }

    public class AssetManagerUpdateHandler implements UpdateCallbacks {
        public AssetManagerUpdateHandler() {
        }

        @Override // android.content.res.Resources.UpdateCallbacks
        public void onLoadersChanged(Resources resources, List<ResourcesLoader> list) {
            Preconditions.checkArgument(Resources.this == resources);
            ResourcesImpl resourcesImpl = Resources.this.mResourcesImpl;
            resourcesImpl.clearAllCaches();
            resourcesImpl.getAssets().setLoaders(list);
        }

        @Override // android.content.res.loader.ResourcesLoader.UpdateCallbacks
        public void onLoaderUpdated(ResourcesLoader resourcesLoader) {
            ResourcesImpl resourcesImpl = Resources.this.mResourcesImpl;
            AssetManager assets = resourcesImpl.getAssets();
            if (assets.getLoaders().contains(resourcesLoader)) {
                resourcesImpl.clearAllCaches();
                assets.setLoaders(assets.getLoaders());
            }
        }
    }

    @Deprecated
    public Resources(AssetManager assetManager, DisplayMetrics displayMetrics, Configuration configuration) {
        this(null);
        this.mResourcesImpl = new ResourcesImpl(assetManager, displayMetrics, configuration, new DisplayAdjustments());
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
        this.mClassLoader = classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader;
        sResourcesHistory.add(this);
        ResourcesManager.getInstance().registerAllResourcesReference(this);
    }

    private Resources() {
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
        this.mClassLoader = ClassLoader.getSystemClassLoader();
        sResourcesHistory.add(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        this.mResourcesImpl = new ResourcesImpl(AssetManager.getSystem(), displayMetrics, configuration, new DisplayAdjustments());
    }

    public void setImpl(ResourcesImpl resourcesImpl) {
        if (resourcesImpl == this.mResourcesImpl) {
            return;
        }
        this.mBaseApkAssetsSize = ArrayUtils.size(resourcesImpl.getAssets().getApkAssets());
        this.mResourcesImpl = resourcesImpl;
        synchronized (this.mThemeRefs) {
            cleanupThemeReferences();
            int size = this.mThemeRefs.size();
            for (int i = 0; i < size; i++) {
                Theme theme = this.mThemeRefs.get(i).get();
                if (theme != null) {
                    theme.rebase(this.mResourcesImpl);
                }
            }
        }
    }

    public void setCallbacks(UpdateCallbacks updateCallbacks) {
        if (this.mCallbacks != null) {
            throw new IllegalStateException("callback already registered");
        }
        this.mCallbacks = updateCallbacks;
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

    public CharSequence getText(int i) throws NotFoundException {
        CharSequence resourceText = this.mResourcesImpl.getAssets().getResourceText(i);
        if (resourceText != null) {
            return resourceText;
        }
        throw new NotFoundException("String resource ID #0x" + Integer.toHexString(i));
    }

    public Typeface getFont(int i) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            Typeface typefaceLoadFont = resourcesImpl.loadFont(this, typedValueObtainTempTypedValue, i);
            if (typefaceLoadFont != null) {
                return typefaceLoadFont;
            }
            releaseTempTypedValue(typedValueObtainTempTypedValue);
            throw new NotFoundException("Font resource ID #0x" + Integer.toHexString(i));
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    Typeface getFont(TypedValue typedValue, int i) throws NotFoundException {
        return this.mResourcesImpl.loadFont(this, typedValue, i);
    }

    public void preloadFonts(int i) throws NotFoundException {
        TypedArray typedArrayObtainTypedArray = obtainTypedArray(i);
        try {
            int length = typedArrayObtainTypedArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                typedArrayObtainTypedArray.getFont(i2);
            }
        } finally {
            typedArrayObtainTypedArray.recycle();
        }
    }

    public CharSequence getQuantityText(int i, int i2) throws NotFoundException {
        return this.mResourcesImpl.getQuantityText(i, i2);
    }

    public String getString(int i) throws NotFoundException {
        return getText(i).toString();
    }

    public String getString(int i, Object... objArr) throws NotFoundException {
        return String.format(this.mResourcesImpl.getConfiguration().getLocales().get(0), getString(i), objArr);
    }

    public String getQuantityString(int i, int i2, Object... objArr) throws NotFoundException {
        return String.format(this.mResourcesImpl.getConfiguration().getLocales().get(0), getQuantityText(i, i2).toString(), objArr);
    }

    public String getQuantityString(int i, int i2) throws NotFoundException {
        return getQuantityText(i, i2).toString();
    }

    public CharSequence getText(int i, CharSequence charSequence) {
        CharSequence resourceText = i != 0 ? this.mResourcesImpl.getAssets().getResourceText(i) : null;
        return resourceText != null ? resourceText : charSequence;
    }

    public CharSequence[] getTextArray(int i) throws NotFoundException {
        CharSequence[] resourceTextArray = this.mResourcesImpl.getAssets().getResourceTextArray(i);
        if (resourceTextArray != null) {
            return resourceTextArray;
        }
        throw new NotFoundException("Text array resource ID #0x" + Integer.toHexString(i));
    }

    public String[] getStringArray(int i) throws NotFoundException {
        String[] resourceStringArray = this.mResourcesImpl.getAssets().getResourceStringArray(i);
        if (resourceStringArray != null) {
            return resourceStringArray;
        }
        throw new NotFoundException("String array resource ID #0x" + Integer.toHexString(i));
    }

    public int[] getIntArray(int i) throws NotFoundException {
        int[] resourceIntArray = this.mResourcesImpl.getAssets().getResourceIntArray(i);
        if (resourceIntArray != null) {
            return resourceIntArray;
        }
        throw new NotFoundException("Int array resource ID #0x" + Integer.toHexString(i));
    }

    public TypedArray obtainTypedArray(int i) throws NotFoundException {
        ResourcesImpl resourcesImpl = this.mResourcesImpl;
        int resourceArraySize = resourcesImpl.getAssets().getResourceArraySize(i);
        if (resourceArraySize < 0) {
            throw new NotFoundException("Array resource ID #0x" + Integer.toHexString(i));
        }
        TypedArray typedArrayObtain = TypedArray.obtain(this, resourceArraySize);
        typedArrayObtain.mLength = resourcesImpl.getAssets().getResourceArray(i, typedArrayObtain.mData);
        typedArrayObtain.mIndices[0] = 0;
        return typedArrayObtain;
    }

    public float getDimension(int i) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type == 5) {
                return TypedValue.complexToDimension(typedValueObtainTempTypedValue.data, resourcesImpl.getDisplayMetrics());
            }
            throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    public int getDimensionPixelOffset(int i) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type == 5) {
                return TypedValue.complexToDimensionPixelOffset(typedValueObtainTempTypedValue.data, resourcesImpl.getDisplayMetrics());
            }
            throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    public int getDimensionPixelSize(int i) throws NotFoundException {
        return getDimensionPixelSize(i, null);
    }

    public int getDimensionPixelSize(int i, DisplayMetrics displayMetrics) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type == 5) {
                if (displayMetrics != null) {
                    return TypedValue.complexToDimensionPixelSize(typedValueObtainTempTypedValue.data, displayMetrics);
                }
                return TypedValue.complexToDimensionPixelSize(typedValueObtainTempTypedValue.data, resourcesImpl.getDisplayMetrics());
            }
            throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    public float getFraction(int i, int i2, int i3) {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type == 6) {
                return TypedValue.complexToFraction(typedValueObtainTempTypedValue.data, i2, i3);
            }
            throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    @Deprecated
    public Drawable getDrawable(int i) throws NotFoundException {
        Drawable drawable = getDrawable(i, null);
        if (drawable != null && drawable.canApplyTheme()) {
            Log.w(TAG, "Drawable " + getResourceName(i) + " has unresolved theme attributes! Consider using Resources.getDrawable(int, Theme) or Context.getDrawable(int).", new RuntimeException());
        }
        return drawable;
    }

    public Drawable getDrawable(int i, Theme theme) throws NotFoundException {
        return getDrawableForDensity(i, 0, theme);
    }

    @Deprecated
    public Drawable getDrawableForDensity(int i, int i2) throws NotFoundException {
        return getDrawableForDensity(i, i2, null);
    }

    public Drawable getDrawableForDensity(int i, int i2, Theme theme) {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValueForDensity(i, i2, typedValueObtainTempTypedValue, true);
            return loadDrawable(typedValueObtainTempTypedValue, i, i2, theme);
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    Drawable loadDrawable(TypedValue typedValue, int i, int i2, Theme theme) throws NotFoundException {
        return this.mResourcesImpl.loadDrawable(this, typedValue, i, i2, theme);
    }

    @Deprecated
    public Movie getMovie(int i) throws NotFoundException, IOException {
        InputStream inputStreamOpenRawResource = openRawResource(i);
        Movie movieDecodeStream = Movie.decodeStream(inputStreamOpenRawResource);
        try {
            inputStreamOpenRawResource.close();
        } catch (IOException unused) {
        }
        return movieDecodeStream;
    }

    @Deprecated
    public int getColor(int i) throws NotFoundException {
        return getColor(i, null);
    }

    public int getColor(int i, Theme theme) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type >= 16 && typedValueObtainTempTypedValue.type <= 31) {
                return typedValueObtainTempTypedValue.data;
            }
            if (typedValueObtainTempTypedValue.type != 3) {
                throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
            }
            return resourcesImpl.loadColorStateList(this, typedValueObtainTempTypedValue, i, theme).getDefaultColor();
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    @Deprecated
    public ColorStateList getColorStateList(int i) throws NotFoundException {
        ColorStateList colorStateList = getColorStateList(i, null);
        if (colorStateList != null && colorStateList.canApplyTheme()) {
            Log.w(TAG, "ColorStateList " + getResourceName(i) + " has unresolved theme attributes! Consider using Resources.getColorStateList(int, Theme) or Context.getColorStateList(int).", new RuntimeException());
        }
        return colorStateList;
    }

    public ColorStateList getColorStateList(int i, Theme theme) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            ResourcesImpl resourcesImpl = this.mResourcesImpl;
            resourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            return resourcesImpl.loadColorStateList(this, typedValueObtainTempTypedValue, i, theme);
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    ColorStateList loadColorStateList(TypedValue typedValue, int i, Theme theme) throws NotFoundException {
        return this.mResourcesImpl.loadColorStateList(this, typedValue, i, theme);
    }

    public ComplexColor loadComplexColor(TypedValue typedValue, int i, Theme theme) {
        return this.mResourcesImpl.loadComplexColor(this, typedValue, i, theme);
    }

    public boolean getBoolean(int i) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type >= 16 && typedValueObtainTempTypedValue.type <= 31) {
                return typedValueObtainTempTypedValue.data != 0;
            }
            throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    public int getInteger(int i) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type >= 16 && typedValueObtainTempTypedValue.type <= 31) {
                return typedValueObtainTempTypedValue.data;
            }
            throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    public float getFloat(int i) {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            if (typedValueObtainTempTypedValue.type == 4) {
                return typedValueObtainTempTypedValue.getFloat();
            }
            throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    public XmlResourceParser getLayout(int i) throws NotFoundException {
        return loadXmlResourceParser(i, TtmlUtils.TAG_LAYOUT);
    }

    public XmlResourceParser getAnimation(int i) throws NotFoundException {
        return loadXmlResourceParser(i, "anim");
    }

    public XmlResourceParser getXml(int i) throws NotFoundException {
        return loadXmlResourceParser(i, "xml");
    }

    public InputStream openRawResource(int i) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            return openRawResource(i, typedValueObtainTempTypedValue);
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    private TypedValue obtainTempTypedValue() {
        TypedValue typedValue;
        synchronized (this.mTmpValueLock) {
            typedValue = this.mTmpValue;
            if (typedValue != null) {
                this.mTmpValue = null;
            } else {
                typedValue = null;
            }
        }
        return typedValue == null ? new TypedValue() : typedValue;
    }

    private void releaseTempTypedValue(TypedValue typedValue) {
        synchronized (this.mTmpValueLock) {
            if (this.mTmpValue == null) {
                this.mTmpValue = typedValue;
            }
        }
    }

    public InputStream openRawResource(int i, TypedValue typedValue) throws NotFoundException {
        return this.mResourcesImpl.openRawResource(i, typedValue);
    }

    public AssetFileDescriptor openRawResourceFd(int i) throws NotFoundException {
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            return this.mResourcesImpl.openRawResourceFd(i, typedValueObtainTempTypedValue);
        } finally {
            releaseTempTypedValue(typedValueObtainTempTypedValue);
        }
    }

    public void getValue(int i, TypedValue typedValue, boolean z) throws NotFoundException {
        this.mResourcesImpl.getValue(i, typedValue, z);
    }

    public void getValueForDensity(int i, int i2, TypedValue typedValue, boolean z) throws NotFoundException {
        this.mResourcesImpl.getValueForDensity(i, i2, typedValue, z);
    }

    public void getValue(String str, TypedValue typedValue, boolean z) throws NotFoundException {
        this.mResourcesImpl.getValue(str, typedValue, z);
    }

    public static int getAttributeSetSourceResId(AttributeSet attributeSet) {
        return ResourcesImpl.getAttributeSetSourceResId(attributeSet);
    }

    public final class Theme {
        private static final int MAX_NUMBER_OF_TRACING_PARENT_THEME = 100;
        private final Object mLock;
        private ResourcesImpl.ThemeImpl mThemeImpl;

        private Theme() {
            this.mLock = new Object();
        }

        void setImpl(ResourcesImpl.ThemeImpl themeImpl) {
            synchronized (this.mLock) {
                this.mThemeImpl = themeImpl;
            }
        }

        public void applyStyle(int i, boolean z) {
            synchronized (this.mLock) {
                this.mThemeImpl.applyStyle(i, z);
            }
        }

        public void setTo(Theme theme) {
            synchronized (this.mLock) {
                synchronized (theme.mLock) {
                    this.mThemeImpl.setTo(theme.mThemeImpl);
                }
            }
        }

        public TypedArray obtainStyledAttributes(int[] iArr) {
            TypedArray typedArrayObtainStyledAttributes;
            synchronized (this.mLock) {
                typedArrayObtainStyledAttributes = this.mThemeImpl.obtainStyledAttributes(this, null, iArr, 0, 0);
            }
            return typedArrayObtainStyledAttributes;
        }

        public TypedArray obtainStyledAttributes(int i, int[] iArr) throws NotFoundException {
            TypedArray typedArrayObtainStyledAttributes;
            synchronized (this.mLock) {
                typedArrayObtainStyledAttributes = this.mThemeImpl.obtainStyledAttributes(this, null, iArr, 0, i);
            }
            return typedArrayObtainStyledAttributes;
        }

        public TypedArray obtainStyledAttributes(AttributeSet attributeSet, int[] iArr, int i, int i2) {
            TypedArray typedArrayObtainStyledAttributes;
            synchronized (this.mLock) {
                typedArrayObtainStyledAttributes = this.mThemeImpl.obtainStyledAttributes(this, attributeSet, iArr, i, i2);
            }
            return typedArrayObtainStyledAttributes;
        }

        public TypedArray resolveAttributes(int[] iArr, int[] iArr2) {
            TypedArray typedArrayResolveAttributes;
            synchronized (this.mLock) {
                typedArrayResolveAttributes = this.mThemeImpl.resolveAttributes(this, iArr, iArr2);
            }
            return typedArrayResolveAttributes;
        }

        public boolean resolveAttribute(int i, TypedValue typedValue, boolean z) {
            boolean zResolveAttribute;
            synchronized (this.mLock) {
                zResolveAttribute = this.mThemeImpl.resolveAttribute(i, typedValue, z);
            }
            return zResolveAttribute;
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

        public Drawable getDrawable(int i) throws NotFoundException {
            return Resources.this.getDrawable(i, this);
        }

        public int getChangingConfigurations() {
            int changingConfigurations;
            synchronized (this.mLock) {
                changingConfigurations = this.mThemeImpl.getChangingConfigurations();
            }
            return changingConfigurations;
        }

        public void dump(int i, String str, String str2) {
            synchronized (this.mLock) {
                this.mThemeImpl.dump(i, str, str2);
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

        int getParentThemeIdentifier(int i) {
            int parentThemeIdentifier;
            synchronized (this.mLock) {
                parentThemeIdentifier = this.mThemeImpl.getParentThemeIdentifier(i);
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

        private String getResourceNameFromHexString(String str) {
            return Resources.this.getResourceName(Integer.parseInt(str, 16));
        }

        @ViewDebug.ExportedProperty(category = "theme", hasAdjacentMapping = true)
        public String[] getTheme() {
            String[] theme;
            synchronized (this.mLock) {
                theme = this.mThemeImpl.getTheme();
            }
            return theme;
        }

        public void encode(ViewHierarchyEncoder viewHierarchyEncoder) throws IOException {
            viewHierarchyEncoder.beginObject(this);
            String[] theme = getTheme();
            for (int i = 0; i < theme.length; i += 2) {
                viewHierarchyEncoder.addProperty(theme[i], theme[i + 1]);
            }
            viewHierarchyEncoder.endObject();
        }

        public void rebase() {
            synchronized (this.mLock) {
                this.mThemeImpl.rebase();
            }
        }

        void rebase(ResourcesImpl resourcesImpl) {
            synchronized (this.mLock) {
                this.mThemeImpl.rebase(resourcesImpl.mAssets);
            }
        }

        public int getExplicitStyle(AttributeSet attributeSet) throws NotFoundException {
            int styleAttribute;
            if (attributeSet == null || (styleAttribute = attributeSet.getStyleAttribute()) == 0) {
                return 0;
            }
            String resourceTypeName = getResources().getResourceTypeName(styleAttribute);
            if ("attr".equals(resourceTypeName)) {
                TypedValue typedValue = new TypedValue();
                if (resolveAttribute(styleAttribute, typedValue, true)) {
                    return typedValue.resourceId;
                }
            } else if ("style".equals(resourceTypeName)) {
                return styleAttribute;
            }
            return 0;
        }

        public int[] getAttributeResolutionStack(int i, int i2, int i3) {
            synchronized (this.mLock) {
                int[] attributeResolutionStack = this.mThemeImpl.getAttributeResolutionStack(i, i2, i3);
                if (attributeResolutionStack != null) {
                    return attributeResolutionStack;
                }
                return new int[0];
            }
        }

        public int hashCode() {
            return getKey().hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass() && hashCode() == obj.hashCode()) {
                return getKey().equals(((Theme) obj).getKey());
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("{InheritanceMap=[");
            int appliedStyleResId = getAppliedStyleResId();
            int i = 0;
            while (true) {
                if (appliedStyleResId <= 0) {
                    break;
                }
                if (i > 100) {
                    sb.append(",...");
                    break;
                }
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append("id=0x");
                sb.append(Integer.toHexString(appliedStyleResId));
                sb.append(Resources.this.getResourcePackageName(appliedStyleResId));
                sb.append(":");
                sb.append(Resources.this.getResourceTypeName(appliedStyleResId));
                sb.append("/");
                sb.append(Resources.this.getResourceEntryName(appliedStyleResId));
                i++;
                appliedStyleResId = getParentThemeIdentifier(appliedStyleResId);
            }
            sb.append("], Themes=");
            sb.append(Arrays.deepToString(getTheme()));
            sb.append('}');
            return sb.toString();
        }
    }

    static class ThemeKey implements Cloneable {
        int mCount;
        boolean[] mForce;
        private int mHashCode = 0;
        int[] mResId;

        ThemeKey() {
        }

        private int findValue(int i, boolean z) {
            for (int i2 = 0; i2 < this.mCount; i2++) {
                if (this.mResId[i2] == i && this.mForce[i2] == z) {
                    return i2;
                }
            }
            return -1;
        }

        private void moveToLast(int i) {
            if (i >= 0) {
                if (i >= this.mCount - 1) {
                    return;
                }
                int[] iArr = this.mResId;
                int i2 = iArr[i];
                boolean z = this.mForce[i];
                int i3 = i + 1;
                System.arraycopy(iArr, i3, iArr, i, (r0 - i) - 1);
                this.mResId[this.mCount - 1] = i2;
                boolean[] zArr = this.mForce;
                System.arraycopy(zArr, i3, zArr, i, (r1 - i) - 1);
                this.mForce[this.mCount - 1] = z;
            }
        }

        public void append(int i, boolean z) {
            if (this.mResId == null) {
                this.mResId = new int[4];
            }
            if (this.mForce == null) {
                this.mForce = new boolean[4];
            }
            int iFindValue = findValue(i, z);
            if (iFindValue >= 0) {
                moveToLast(iFindValue);
                return;
            }
            this.mResId = GrowingArrayUtils.append(this.mResId, this.mCount, i);
            this.mForce = GrowingArrayUtils.append(this.mForce, this.mCount, z);
            this.mCount++;
            this.mHashCode = (((this.mHashCode * 31) + i) * 31) + (z ? 1 : 0);
        }

        public void setTo(ThemeKey themeKey) {
            int[] iArr = themeKey.mResId;
            this.mResId = iArr == null ? null : (int[]) iArr.clone();
            boolean[] zArr = themeKey.mForce;
            this.mForce = zArr != null ? (boolean[]) zArr.clone() : null;
            this.mCount = themeKey.mCount;
            this.mHashCode = themeKey.mHashCode;
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass() || hashCode() != obj.hashCode()) {
                return false;
            }
            ThemeKey themeKey = (ThemeKey) obj;
            int i = this.mCount;
            if (i != themeKey.mCount) {
                return false;
            }
            for (int i2 = 0; i2 < i; i2++) {
                if (this.mResId[i2] != themeKey.mResId[i2] || this.mForce[i2] != themeKey.mForce[i2]) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public ThemeKey m1107clone() {
            ThemeKey themeKey = new ThemeKey();
            themeKey.mResId = this.mResId;
            themeKey.mForce = this.mForce;
            themeKey.mCount = this.mCount;
            themeKey.mHashCode = this.mHashCode;
            return themeKey;
        }
    }

    static int nextPowerOf2(int i) {
        if (i < 2) {
            return 2;
        }
        return 1 >> (((int) (Math.log(i - 1) / Math.log(2.0d))) + 1);
    }

    private void cleanupThemeReferences() {
        if (this.mThemeRefs.size() > this.mThemeRefsNextFlushSize) {
            this.mThemeRefs.removeIf(new Predicate() { // from class: android.content.res.Resources$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((WeakReference) obj).refersTo(null);
                }
            });
            this.mThemeRefsNextFlushSize = Math.min(Math.max(32, nextPowerOf2(this.mThemeRefs.size())), 512);
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

    public TypedArray obtainAttributes(AttributeSet attributeSet, int[] iArr) {
        TypedArray typedArrayObtain = TypedArray.obtain(this, iArr.length);
        XmlBlock.Parser parser = (XmlBlock.Parser) attributeSet;
        this.mResourcesImpl.getAssets().retrieveAttributes(parser, iArr, typedArrayObtain.mData, typedArrayObtain.mIndices);
        typedArrayObtain.mXml = parser;
        return typedArrayObtain;
    }

    @Deprecated
    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        updateConfiguration(configuration, displayMetrics, null);
    }

    public void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo) {
        this.mResourcesImpl.updateConfiguration(configuration, displayMetrics, compatibilityInfo);
    }

    public static void updateSystemConfiguration(Configuration configuration, DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo) {
        Resources resources = mSystem;
        if (resources != null) {
            resources.updateConfiguration(configuration, displayMetrics, compatibilityInfo);
        }
    }

    public DisplayMetrics getDisplayMetrics() {
        return this.mResourcesImpl.getDisplayMetrics();
    }

    public DisplayAdjustments getDisplayAdjustments() {
        return this.mResourcesImpl.getDisplayAdjustments();
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

    public void setCompatibilityInfo(CompatibilityInfo compatibilityInfo) {
        if (compatibilityInfo != null) {
            this.mResourcesImpl.updateConfiguration(null, null, compatibilityInfo);
        }
    }

    public int getIdentifier(String str, String str2, String str3) {
        return this.mResourcesImpl.getIdentifier(str, str2, str3);
    }

    public String getResourceName(int i) throws NotFoundException {
        return this.mResourcesImpl.getResourceName(i);
    }

    public String getResourcePackageName(int i) throws NotFoundException {
        return this.mResourcesImpl.getResourcePackageName(i);
    }

    public String getResourceTypeName(int i) throws NotFoundException {
        return this.mResourcesImpl.getResourceTypeName(i);
    }

    public String getResourceEntryName(int i) throws NotFoundException {
        return this.mResourcesImpl.getResourceEntryName(i);
    }

    public String getLastResourceResolution() throws NotFoundException {
        return this.mResourcesImpl.getLastResourceResolution();
    }

    public void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) throws XmlPullParserException, IOException {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlResourceParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                if (xmlResourceParser.getName().equals(SemShareConstants.SURVEY_CONTENT_EXTRA)) {
                    parseBundleExtra(SemShareConstants.SURVEY_CONTENT_EXTRA, xmlResourceParser, bundle);
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                } else {
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
    }

    public void parseBundleExtra(String str, AttributeSet attributeSet, Bundle bundle) throws XmlPullParserException {
        TypedArray typedArrayObtainAttributes = obtainAttributes(attributeSet, R.styleable.Extra);
        String string = typedArrayObtainAttributes.getString(0);
        if (string == null) {
            typedArrayObtainAttributes.recycle();
            throw new XmlPullParserException("<" + str + "> requires an android:name attribute at " + attributeSet.getPositionDescription());
        }
        TypedValue typedValuePeekValue = typedArrayObtainAttributes.peekValue(1);
        if (typedValuePeekValue != null) {
            if (typedValuePeekValue.type == 3) {
                bundle.putCharSequence(string, typedValuePeekValue.coerceToString());
            } else if (typedValuePeekValue.type == 18) {
                bundle.putBoolean(string, typedValuePeekValue.data != 0);
            } else if (typedValuePeekValue.type >= 16 && typedValuePeekValue.type <= 31) {
                bundle.putInt(string, typedValuePeekValue.data);
            } else if (typedValuePeekValue.type == 4) {
                bundle.putFloat(string, typedValuePeekValue.getFloat());
            } else {
                typedArrayObtainAttributes.recycle();
                throw new XmlPullParserException("<" + str + "> only supports string, integer, float, color, and boolean at " + attributeSet.getPositionDescription());
            }
            typedArrayObtainAttributes.recycle();
            return;
        }
        typedArrayObtainAttributes.recycle();
        throw new XmlPullParserException("<" + str + "> requires an android:value or android:resource attribute at " + attributeSet.getPositionDescription());
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.content.res.Resources] */
    /* JADX WARN: Type inference failed for: r2v3, types: [int] */
    XmlResourceParser loadXmlResourceParser(int i, String str) throws Throwable {
        ?? r2;
        TypedValue typedValueObtainTempTypedValue = obtainTempTypedValue();
        try {
            this.mResourcesImpl.getValue(i, typedValueObtainTempTypedValue, true);
            r2 = typedValueObtainTempTypedValue.type;
            try {
                if (r2 == 3) {
                    XmlResourceParser xmlResourceParserLoadXmlResourceParser = loadXmlResourceParser(typedValueObtainTempTypedValue.string.toString(), i, typedValueObtainTempTypedValue.assetCookie, str, typedValueObtainTempTypedValue.usesFeatureFlags);
                    releaseTempTypedValue(typedValueObtainTempTypedValue);
                    return xmlResourceParserLoadXmlResourceParser;
                }
                throw new NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValueObtainTempTypedValue.type) + " is not valid");
            } catch (Throwable th) {
                th = th;
                Throwable th2 = th;
                r2.releaseTempTypedValue(typedValueObtainTempTypedValue);
                throw th2;
            }
        } catch (Throwable th3) {
            th = th3;
            r2 = this;
        }
    }

    XmlResourceParser loadXmlResourceParser(String str, int i, int i2, String str2) throws NotFoundException {
        return loadXmlResourceParser(str, i, i2, str2, true);
    }

    public XmlResourceParser loadXmlResourceParser(String str, int i, int i2, String str2, boolean z) throws NotFoundException {
        return this.mResourcesImpl.loadXmlResourceParser(str, i, i2, str2, z);
    }

    public int calcConfigChanges(Configuration configuration) {
        return this.mResourcesImpl.calcConfigChanges(configuration);
    }

    public static TypedArray obtainAttributes(Resources resources, Theme theme, AttributeSet attributeSet, int[] iArr) {
        if (theme == null) {
            return resources.obtainAttributes(attributeSet, iArr);
        }
        return theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    private void checkCallbacksRegistered() {
        if (this.mCallbacks == null) {
            this.mCallbacks = new AssetManagerUpdateHandler();
        }
    }

    public List<ResourcesLoader> getLoaders() {
        return this.mResourcesImpl.getAssets().getLoaders();
    }

    public void addLoaders(ResourcesLoader... resourcesLoaderArr) {
        synchronized (this.mUpdateLock) {
            checkCallbacksRegistered();
            ArrayList arrayList = new ArrayList(this.mResourcesImpl.getAssets().getLoaders());
            ArraySet arraySet = new ArraySet(arrayList);
            for (ResourcesLoader resourcesLoader : resourcesLoaderArr) {
                if (!arraySet.contains(resourcesLoader)) {
                    arrayList.add(resourcesLoader);
                }
            }
            if (arraySet.size() == arrayList.size()) {
                return;
            }
            this.mCallbacks.onLoadersChanged(this, arrayList);
            int size = arrayList.size();
            for (int size2 = arraySet.size(); size2 < size; size2++) {
                ((ResourcesLoader) arrayList.get(size2)).registerOnProvidersChangedCallback(this, this.mCallbacks);
            }
        }
    }

    public void removeLoaders(ResourcesLoader... resourcesLoaderArr) {
        synchronized (this.mUpdateLock) {
            checkCallbacksRegistered();
            ArraySet arraySet = new ArraySet(resourcesLoaderArr);
            ArrayList arrayList = new ArrayList();
            List<ResourcesLoader> loaders = this.mResourcesImpl.getAssets().getLoaders();
            int size = loaders.size();
            for (int i = 0; i < size; i++) {
                ResourcesLoader resourcesLoader = loaders.get(i);
                if (!arraySet.contains(resourcesLoader)) {
                    arrayList.add(resourcesLoader);
                }
            }
            if (loaders.size() == arrayList.size()) {
                return;
            }
            this.mCallbacks.onLoadersChanged(this, arrayList);
            for (ResourcesLoader resourcesLoader2 : resourcesLoaderArr) {
                resourcesLoader2.unregisterOnProvidersChangedCallback(this);
            }
        }
    }

    public void clearLoaders() {
        synchronized (this.mUpdateLock) {
            checkCallbacksRegistered();
            List<ResourcesLoader> list = Collections.EMPTY_LIST;
            List<ResourcesLoader> loaders = this.mResourcesImpl.getAssets().getLoaders();
            this.mCallbacks.onLoadersChanged(this, list);
            Iterator<ResourcesLoader> it = loaders.iterator();
            while (it.hasNext()) {
                it.next().unregisterOnProvidersChangedCallback(this);
            }
        }
    }

    public static void preloadResources() {
        try {
            Resources system = getSystem();
            system.startPreloading();
            Log.i(TAG, "Preloading resources...");
            long jUptimeMillis = SystemClock.uptimeMillis();
            TypedArray typedArrayObtainTypedArray = system.obtainTypedArray(R.array.preloaded_drawables);
            int iPreloadDrawables = preloadDrawables(system, typedArrayObtainTypedArray);
            typedArrayObtainTypedArray.recycle();
            Log.i(TAG, "...preloaded " + iPreloadDrawables + " resources in " + (SystemClock.uptimeMillis() - jUptimeMillis) + "ms.");
            long jUptimeMillis2 = SystemClock.uptimeMillis();
            TypedArray typedArrayObtainTypedArray2 = system.obtainTypedArray(R.array.preloaded_color_state_lists);
            int iPreloadColorStateLists = preloadColorStateLists(system, typedArrayObtainTypedArray2);
            typedArrayObtainTypedArray2.recycle();
            Log.i(TAG, "...preloaded " + iPreloadColorStateLists + " resources in " + (SystemClock.uptimeMillis() - jUptimeMillis2) + "ms.");
            system.finishPreloading();
        } catch (RuntimeException e) {
            Log.w(TAG, "Failure preloading resources", e);
        }
    }

    private static int preloadColorStateLists(Resources resources, TypedArray typedArray) {
        int length = typedArray.length();
        for (int i = 0; i < length; i++) {
            int resourceId = typedArray.getResourceId(i, 0);
            if (resourceId != 0 && resources.getColorStateList(resourceId, null) == null) {
                throw new IllegalArgumentException("Unable to find preloaded color resource #0x" + Integer.toHexString(resourceId) + " (" + typedArray.getString(i) + NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        return length;
    }

    private static int preloadDrawables(Resources resources, TypedArray typedArray) {
        int length = typedArray.length();
        for (int i = 0; i < length; i++) {
            int resourceId = typedArray.getResourceId(i, 0);
            if (resourceId != 0 && resources.getDrawable(resourceId, null) == null) {
                throw new IllegalArgumentException("Unable to find preloaded drawable resource #0x" + Integer.toHexString(resourceId) + " (" + typedArray.getString(i) + NavigationBarInflaterView.KEY_CODE_END);
            }
        }
        return length;
    }

    public static void resetPreloadDrawableStateCache() {
        ResourcesImpl.resetDrawableStateCache();
        preloadResources();
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "class=" + getClass());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("resourcesImpl");
        printWriter.println(sb.toString());
        ResourcesImpl resourcesImpl = this.mResourcesImpl;
        if (resourcesImpl != null) {
            resourcesImpl.dump(printWriter, str + "  ");
            return;
        }
        printWriter.println(str + "  null");
    }

    public static void dumpHistory(PrintWriter printWriter, String str) {
        printWriter.println(str + "history");
        final ArrayMap arrayMap = new ArrayMap();
        try {
            sResourcesHistory.forEach(new Consumer() { // from class: android.content.res.Resources$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Resources.lambda$dumpHistory$1(arrayMap, (Resources) obj);
                }
            });
            int i = 0;
            for (Resources resources : arrayMap.values()) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                int i2 = i + 1;
                sb.append(i);
                printWriter.println(sb.toString());
                resources.dump(printWriter, str + "  ");
                i = i2;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "NPE occurred, stop dumping : ", e);
        }
    }

    static /* synthetic */ void lambda$dumpHistory$1(ArrayMap arrayMap, Resources resources) {
        if (resources != null) {
            ResourcesImpl resourcesImpl = resources.mResourcesImpl;
            if (resourcesImpl != null) {
                arrayMap.put(Arrays.asList(resourcesImpl.mAssets.getApkAssets()), resources);
            } else {
                arrayMap.put(null, resources);
            }
        }
    }

    public static void registerResourcePaths(String str, ApplicationInfo applicationInfo) {
        if (Flags.registerResourcePaths()) {
            ResourcesManager.getInstance().registerResourcePaths(str, applicationInfo);
            return;
        }
        throw new UnsupportedOperationException("Flag android.content.res.register_resource_paths is disabled.");
    }
}
