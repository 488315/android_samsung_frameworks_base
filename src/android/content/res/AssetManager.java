package android.content.res;

import android.app.ResourcesManager;
import android.app.blob.XmlTags;
import android.content.om.SamsungThemeConstants;
import android.content.pm.ActivityInfo;
import android.content.res.XmlBlock;
import android.content.res.loader.ResourcesLoader;
import android.os.ParcelFileDescriptor;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.ravenwood.RavenwoodEnvironment;
import com.samsung.android.util.CustomizedTextParser;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public final class AssetManager implements AutoCloseable {
    public static final int ACCESS_BUFFER = 3;
    public static final int ACCESS_RANDOM = 1;
    public static final int ACCESS_STREAMING = 2;
    public static final int ACCESS_UNKNOWN = 0;
    public static final int COOKIE_UNKNOWN = -1;
    private static final boolean DEBUG_REFS = false;
    private static final String FRAMEWORK_APK_PATH_DEVICE = "/system/framework/framework-res.apk";
    private static final String FRAMEWORK_APK_PATH_RAVENWOOD = "ravenwood-data/framework-res.apk";
    private static final String MEDIATEK_APK_PATH = "/system/app/mediatek-res/mediatek-res.apk";
    private static final String PROPERTY_MTK_MODEL = "ro.vendor.mtk_model";
    private static final String TAG = "AssetManager";
    private static ArraySet<ApkAssets> sSystemApkAssetsSet;
    private ApkAssets[] mApkAssets;
    private ResourcesLoader[] mLoaders;
    private AtomicInteger mNumRefs;
    private long mObject;
    private final long[] mOffsets;
    private boolean mOpen;
    private HashMap<Long, RuntimeException> mRefStacks;
    final ArrayList<String> mSamsungThemeOverlays;
    private final TypedValue mValue;
    public static final String FRAMEWORK_APK_PATH = getFrameworkApkPath();
    private static final Object sSync = new Object();
    private static final ApkAssets[] sEmptyApkAssets = new ApkAssets[0];
    static AssetManager sSystem = null;
    private static ApkAssets[] sSystemApkAssets = new ApkAssets[0];
    private static CustomizedTextParser sCTxtParser = null;

    public static native String getAssetAllocations();

    public static native int getGlobalAssetCount();

    public static native int getGlobalAssetManagerCount();

    private void invalidateCachesLocked(int i) {
    }

    private static native void nativeApplyStyle(long j, long j2, int i, int i2, long j3, int[] iArr, long j4, long j5);

    private static native void nativeApplyStyleWithArray(long j, long j2, int i, int i2, long j3, int[] iArr, int[] iArr2, int[] iArr3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAssetDestroy(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeAssetGetLength(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeAssetGetRemainingLength(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeAssetRead(long j, byte[] bArr, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native int nativeAssetReadChar(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeAssetSeek(long j, long j2, int i);

    private static native int[] nativeAttributeResolutionStack(long j, long j2, int i, int i2, int i3);

    private static native boolean nativeContainsAllocatedTable(long j);

    private static native long nativeCreate();

    private static native void nativeDestroy(long j);

    private static native SparseArray<String> nativeGetAssignedPackageIdentifiers(long j, boolean z, boolean z2);

    private static native String nativeGetLastResourceResolution(long j);

    private static native String[] nativeGetLocales(long j, boolean z);

    private static native Map nativeGetOverlayableMap(long j, String str);

    private static native String nativeGetOverlayablesToString(long j, String str);

    private static native int nativeGetParentThemeIdentifier(long j, int i);

    private static native int nativeGetResourceArray(long j, int i, int[] iArr);

    private static native int nativeGetResourceArraySize(long j, int i);

    private static native int nativeGetResourceBagValue(long j, int i, int i2, TypedValue typedValue);

    private static native String nativeGetResourceEntryName(long j, int i);

    private static native int nativeGetResourceIdentifier(long j, String str, String str2, String str3);

    private static native int[] nativeGetResourceIntArray(long j, int i);

    private static native String nativeGetResourceName(long j, int i);

    private static native String nativeGetResourcePackageName(long j, int i);

    private static native String[] nativeGetResourceStringArray(long j, int i);

    private static native int[] nativeGetResourceStringArrayInfo(long j, int i);

    private static native String nativeGetResourceTypeName(long j, int i);

    private static native int nativeGetResourceValue(long j, int i, short s, TypedValue typedValue, boolean z);

    private static native Configuration[] nativeGetSizeAndUiModeConfigurations(long j);

    private static native Configuration[] nativeGetSizeConfigurations(long j);

    private static native int[] nativeGetStyleAttributes(long j, int i);

    private static native long nativeGetThemeFreeFunction();

    private static native String[] nativeList(long j, String str) throws IOException;

    private static native long nativeOpenAsset(long j, String str, int i);

    private static native ParcelFileDescriptor nativeOpenAssetFd(long j, String str, long[] jArr) throws IOException;

    private static native long nativeOpenNonAsset(long j, int i, String str, int i2);

    private static native ParcelFileDescriptor nativeOpenNonAssetFd(long j, int i, String str, long[] jArr) throws IOException;

    private static native long nativeOpenXmlAsset(long j, int i, String str);

    private static native long nativeOpenXmlAssetFd(long j, int i, FileDescriptor fileDescriptor);

    private static native boolean nativeResolveAttrs(long j, long j2, int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4);

    private static native boolean nativeRetrieveAttributes(long j, long j2, int[] iArr, int[] iArr2, int[] iArr3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetApkAssets(long j, ApkAssets[] apkAssetsArr, boolean z, boolean z2);

    private static native void nativeSetConfiguration(long j, int i, int i2, String str, String[] strArr, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, boolean z);

    private static native void nativeSetOverlayConstraints(long j, int i, int i2);

    private static native void nativeSetResourceResolutionLoggingEnabled(long j, boolean z);

    private static native void nativeThemeApplyStyle(long j, long j2, int i, boolean z);

    private static native void nativeThemeCopy(long j, long j2, long j3, long j4);

    private static native long nativeThemeCreate(long j);

    private static native void nativeThemeDump(long j, long j2, int i, String str, String str2);

    private static native int nativeThemeGetAttributeValue(long j, long j2, int i, TypedValue typedValue, boolean z);

    static native int nativeThemeGetChangingConfigurations(long j);

    private static native void nativeThemeRebase(long j, long j2, int[] iArr, boolean[] zArr, int i);

    public static class Builder {
        private final ArrayList<ApkAssets> mUserApkAssets = new ArrayList<>();
        private final ArrayList<ResourcesLoader> mLoaders = new ArrayList<>();
        private boolean mNoInit = false;

        public Builder addApkAssets(ApkAssets apkAssets) {
            this.mUserApkAssets.add(apkAssets);
            return this;
        }

        public Builder addLoader(ResourcesLoader resourcesLoader) {
            this.mLoaders.add(resourcesLoader);
            return this;
        }

        public Builder setNoInit() {
            this.mNoInit = true;
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public AssetManager build() {
            boolean z;
            ApkAssets[] apkAssets = AssetManager.getSystem().getApkAssets();
            ArrayList arrayList = new ArrayList();
            ArraySet arraySet = new ArraySet();
            int size = this.mLoaders.size();
            while (true) {
                size--;
                z = false;
                if (size < 0) {
                    break;
                }
                List<ApkAssets> apkAssets2 = this.mLoaders.get(size).getApkAssets();
                for (int size2 = apkAssets2.size() - 1; size2 >= 0; size2--) {
                    ApkAssets apkAssets3 = apkAssets2.get(size2);
                    if (arraySet.add(apkAssets3)) {
                        arrayList.add(0, apkAssets3);
                    }
                }
            }
            ApkAssets[] apkAssetsArr = new ApkAssets[apkAssets.length + this.mUserApkAssets.size() + arrayList.size()];
            System.arraycopy(apkAssets, 0, apkAssetsArr, 0, apkAssets.length);
            int size3 = this.mUserApkAssets.size();
            for (int i = 0; i < size3; i++) {
                apkAssetsArr[apkAssets.length + i] = this.mUserApkAssets.get(i);
            }
            int size4 = arrayList.size();
            for (int i2 = 0; i2 < size4; i2++) {
                apkAssetsArr[apkAssets.length + i2 + this.mUserApkAssets.size()] = (ApkAssets) arrayList.get(i2);
            }
            AssetManager assetManager = new AssetManager(z);
            assetManager.mApkAssets = apkAssetsArr;
            AssetManager.nativeSetApkAssets(assetManager.mObject, apkAssetsArr, false, this.mNoInit);
            assetManager.mLoaders = this.mLoaders.isEmpty() ? null : (ResourcesLoader[]) this.mLoaders.toArray(new ResourcesLoader[0]);
            assetManager.updateSamsungThemeOverlays();
            return assetManager;
        }
    }

    private static String getFrameworkApkPath() {
        return FRAMEWORK_APK_PATH_DEVICE;
    }

    private static String getFrameworkApkPath$ravenwood() {
        return RavenwoodEnvironment.getInstance().getRavenwoodRuntimePath() + FRAMEWORK_APK_PATH_RAVENWOOD;
    }

    public AssetManager() {
        ApkAssets[] apkAssetsArr;
        this.mValue = new TypedValue();
        this.mOffsets = new long[2];
        this.mOpen = true;
        this.mNumRefs = new AtomicInteger(1);
        this.mSamsungThemeOverlays = new ArrayList<>();
        synchronized (sSync) {
            createSystemAssetsInZygoteLocked(false, FRAMEWORK_APK_PATH);
            apkAssetsArr = sSystemApkAssets;
        }
        this.mObject = nativeCreate();
        setApkAssets(apkAssetsArr, false);
    }

    private AssetManager(boolean z) {
        this.mValue = new TypedValue();
        this.mOffsets = new long[2];
        this.mOpen = true;
        this.mNumRefs = new AtomicInteger(1);
        this.mSamsungThemeOverlays = new ArrayList<>();
        this.mObject = nativeCreate();
    }

    public static void createSystemAssetsInZygoteLocked(boolean z, String str) {
        if (sSystem == null || z) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(ApkAssets.loadFromPath(str, 1));
                for (String str2 : RavenwoodEnvironment.getInstance().isRunningOnRavenwood() ? new String[0] : OverlayConfig.getZygoteInstance().createImmutableFrameworkIdmapsInZygote()) {
                    arrayList.add(ApkAssets.loadOverlayFromPath(str2, 1));
                }
                if ("1".equals(SystemProperties.get(PROPERTY_MTK_MODEL))) {
                    arrayList.add(ApkAssets.loadFromPath(MEDIATEK_APK_PATH, 1));
                }
                sSystemApkAssetsSet = new ArraySet<>(arrayList);
                sSystemApkAssets = (ApkAssets[]) arrayList.toArray(new ApkAssets[0]);
                if (sSystem == null) {
                    sSystem = new AssetManager(true);
                }
                sSystem.setApkAssets(sSystemApkAssets, false);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create system AssetManager", e);
            }
        }
    }

    public static AssetManager getSystem() {
        AssetManager assetManager;
        synchronized (sSync) {
            createSystemAssetsInZygoteLocked(false, FRAMEWORK_APK_PATH);
            assetManager = sSystem;
        }
        return assetManager;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                decRefs(hashCode());
            }
        }
    }

    public void setApkAssets(ApkAssets[] apkAssetsArr, boolean z) {
        Objects.requireNonNull(apkAssetsArr, "apkAssets");
        ApkAssets[] apkAssetsArr2 = sSystemApkAssets;
        int length = apkAssetsArr2.length + apkAssetsArr.length;
        ApkAssets[] apkAssetsArr3 = new ApkAssets[length];
        System.arraycopy(apkAssetsArr2, 0, apkAssetsArr3, 0, apkAssetsArr2.length);
        int length2 = sSystemApkAssets.length;
        for (ApkAssets apkAssets : apkAssetsArr) {
            if (!sSystemApkAssetsSet.contains(apkAssets)) {
                apkAssetsArr3[length2] = apkAssets;
                length2++;
            }
        }
        if (length2 != length) {
            apkAssetsArr3 = (ApkAssets[]) Arrays.copyOf(apkAssetsArr3, length2);
        }
        synchronized (this) {
            ensureOpenLocked();
            this.mApkAssets = apkAssetsArr3;
            nativeSetApkAssets(this.mObject, apkAssetsArr3, z, false);
            if (z) {
                invalidateCachesLocked(-1);
            }
            updateSamsungThemeOverlays();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSamsungThemeOverlays() {
        synchronized (this) {
            if (this.mSamsungThemeOverlays.size() > 0) {
                this.mSamsungThemeOverlays.clear();
            }
            for (ApkAssets apkAssets : this.mApkAssets) {
                String assetPath = apkAssets.getAssetPath();
                if (assetPath.startsWith(SamsungThemeConstants.PATH_OVERLAY_CURRENT_STYLE) && !this.mSamsungThemeOverlays.contains(assetPath)) {
                    this.mSamsungThemeOverlays.add(assetPath);
                }
            }
        }
    }

    void setLoaders(List<ResourcesLoader> list) {
        Objects.requireNonNull(list, "newLoaders");
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            ApkAssets[] apkAssetsArr = this.mApkAssets;
            if (i >= apkAssetsArr.length) {
                break;
            }
            if (!apkAssetsArr[i].isForLoader()) {
                arrayList.add(this.mApkAssets[i]);
            }
            i++;
        }
        if (!list.isEmpty()) {
            int size = arrayList.size();
            ArraySet arraySet = new ArraySet();
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                List<ApkAssets> apkAssets = list.get(size2).getApkAssets();
                for (int size3 = apkAssets.size() - 1; size3 >= 0; size3--) {
                    ApkAssets apkAssets2 = apkAssets.get(size3);
                    if (arraySet.add(apkAssets2)) {
                        arrayList.add(size, apkAssets2);
                    }
                }
            }
        }
        this.mLoaders = (ResourcesLoader[]) list.toArray(new ResourcesLoader[0]);
        setApkAssets((ApkAssets[]) arrayList.toArray(new ApkAssets[0]), true);
    }

    public ApkAssets[] getApkAssets() {
        synchronized (this) {
            if (this.mOpen) {
                return this.mApkAssets;
            }
            return sEmptyApkAssets;
        }
    }

    public String[] getApkPaths() {
        synchronized (this) {
            if (this.mOpen) {
                ApkAssets[] apkAssetsArr = this.mApkAssets;
                String[] strArr = new String[apkAssetsArr.length];
                int length = apkAssetsArr.length;
                for (int i = 0; i < length; i++) {
                    strArr[i] = this.mApkAssets[i].getAssetPath();
                }
                return strArr;
            }
            return new String[0];
        }
    }

    public int findCookieForPath(String str) {
        Objects.requireNonNull(str, "path");
        synchronized (this) {
            ensureValidLocked();
            int length = this.mApkAssets.length;
            for (int i = 0; i < length; i++) {
                if (str.equals(this.mApkAssets[i].getAssetPath())) {
                    return i + 1;
                }
            }
            return 0;
        }
    }

    @Deprecated
    public int addAssetPath(String str) {
        return addAssetPathInternal(List.of(new ResourcesManager.ApkKey(str, false, false)), false);
    }

    @Deprecated
    public int addAssetPathAsSharedLibrary(String str) {
        return addAssetPathInternal(List.of(new ResourcesManager.ApkKey(str, true, false)), false);
    }

    @Deprecated
    public int addOverlayPath(String str) {
        return addAssetPathInternal(List.of(new ResourcesManager.ApkKey(str, false, true)), false);
    }

    public void addPresetApkKeys(List<ResourcesManager.ApkKey> list) {
        addAssetPathInternal(list, true);
    }

    private int addAssetPathInternal(List<ResourcesManager.ApkKey> list, boolean z) {
        Objects.requireNonNull(list, "apkKeys");
        if (list.isEmpty()) {
            return 0;
        }
        synchronized (this) {
            ensureOpenLocked();
            int length = this.mApkAssets.length;
            ArrayMap arrayMap = new ArrayMap(length);
            for (int i = 0; i < length; i++) {
                arrayMap.put(this.mApkAssets[i].getAssetPath(), Integer.valueOf(i));
            }
            ArrayList arrayList = new ArrayList(list.size());
            int size = list.size();
            int iIntValue = -1;
            for (int i2 = 0; i2 < size; i2++) {
                ResourcesManager.ApkKey apkKey = list.get(i2);
                Integer num = (Integer) arrayMap.get(apkKey.path);
                if (num == null) {
                    arrayList.add(apkKey);
                } else {
                    iIntValue = num.intValue();
                }
            }
            if (arrayList.isEmpty()) {
                return iIntValue + 1;
            }
            ArrayList<ApkAssets> arrayListLoadAssets = loadAssets(arrayList);
            if (arrayListLoadAssets.isEmpty()) {
                return 0;
            }
            ApkAssets[] apkAssetsArrMakeNewAssetsArrayLocked = makeNewAssetsArrayLocked(arrayListLoadAssets);
            this.mApkAssets = apkAssetsArrMakeNewAssetsArrayLocked;
            nativeSetApkAssets(this.mObject, apkAssetsArrMakeNewAssetsArrayLocked, true, z);
            invalidateCachesLocked(-1);
            return length + 1;
        }
    }

    private ApkAssets[] makeNewAssetsArrayLocked(ArrayList<ApkAssets> arrayList) {
        int length = this.mApkAssets.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                i = length;
                break;
            }
            if (this.mApkAssets[i].isForLoader()) {
                break;
            }
            i++;
        }
        int size = arrayList.size();
        ApkAssets[] apkAssetsArr = new ApkAssets[length + size];
        if (i > 0) {
            System.arraycopy(this.mApkAssets, 0, apkAssetsArr, 0, i);
        }
        for (int i2 = 0; i2 < size; i2++) {
            apkAssetsArr[i + i2] = arrayList.get(i2);
        }
        if (length > i) {
            System.arraycopy(this.mApkAssets, i, apkAssetsArr, size + i, length - i);
        }
        return apkAssetsArr;
    }

    private static ArrayList<ApkAssets> loadAssets(ArrayList<ResourcesManager.ApkKey> arrayList) {
        int size = arrayList.size();
        ArrayList<ApkAssets> arrayList2 = new ArrayList<>(size);
        ResourcesManager resourcesManager = ResourcesManager.getInstance();
        for (int i = 0; i < size; i++) {
            ResourcesManager.ApkKey apkKey = arrayList.get(i);
            try {
                arrayList2.add(resourcesManager.loadApkAssets(apkKey));
            } catch (IOException e) {
                Log.w(TAG, "Failed to load asset, key = " + apkKey, e);
            }
        }
        return arrayList2;
    }

    public List<ResourcesLoader> getLoaders() {
        ResourcesLoader[] resourcesLoaderArr = this.mLoaders;
        return resourcesLoaderArr == null ? Collections.EMPTY_LIST : Arrays.asList(resourcesLoaderArr);
    }

    public ArrayList<String> getSamsungThemeOverlays() {
        ArrayList<String> arrayList;
        synchronized (this) {
            arrayList = this.mSamsungThemeOverlays;
        }
        return arrayList;
    }

    private void ensureValidLocked() {
        if (this.mObject == 0) {
            throw new RuntimeException("AssetManager has been destroyed");
        }
    }

    private void ensureOpenLocked() {
        if (!this.mOpen) {
            throw new RuntimeException("AssetManager has been closed");
        }
        if (this.mObject == 0) {
            throw new RuntimeException("AssetManager is open but the native object is gone");
        }
    }

    boolean getResourceValue(int i, int i2, TypedValue typedValue, boolean z) {
        Objects.requireNonNull(typedValue, "outValue");
        synchronized (this) {
            ensureValidLocked();
            int iNativeGetResourceValue = nativeGetResourceValue(this.mObject, i, (short) i2, typedValue, z);
            if (iNativeGetResourceValue <= 0) {
                return false;
            }
            typedValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                CharSequence pooledStringForCookie = getPooledStringForCookie(iNativeGetResourceValue, typedValue.data);
                typedValue.string = pooledStringForCookie;
                if (pooledStringForCookie == null) {
                    return false;
                }
            }
            return true;
        }
    }

    CharSequence getResourceText(int i) {
        synchronized (this) {
            TypedValue typedValue = this.mValue;
            if (!getResourceValue(i, 0, typedValue, true)) {
                return null;
            }
            return typedValue.coerceToString();
        }
    }

    CharSequence getResourceBagText(int i, int i2) {
        synchronized (this) {
            ensureValidLocked();
            TypedValue typedValue = this.mValue;
            int iNativeGetResourceBagValue = nativeGetResourceBagValue(this.mObject, i, i2, typedValue);
            if (iNativeGetResourceBagValue <= 0) {
                return null;
            }
            typedValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                return getPooledStringForCookie(iNativeGetResourceBagValue, typedValue.data);
            }
            return typedValue.coerceToString();
        }
    }

    int getResourceArraySize(int i) {
        int iNativeGetResourceArraySize;
        synchronized (this) {
            ensureValidLocked();
            iNativeGetResourceArraySize = nativeGetResourceArraySize(this.mObject, i);
        }
        return iNativeGetResourceArraySize;
    }

    int getResourceArray(int i, int[] iArr) {
        int iNativeGetResourceArray;
        Objects.requireNonNull(iArr, "outData");
        synchronized (this) {
            ensureValidLocked();
            iNativeGetResourceArray = nativeGetResourceArray(this.mObject, i, iArr);
        }
        return iNativeGetResourceArray;
    }

    String[] getResourceStringArray(int i) {
        String[] strArrNativeGetResourceStringArray;
        synchronized (this) {
            ensureValidLocked();
            strArrNativeGetResourceStringArray = nativeGetResourceStringArray(this.mObject, i);
        }
        return strArrNativeGetResourceStringArray;
    }

    CharSequence[] getResourceTextArray(int i) {
        synchronized (this) {
            ensureValidLocked();
            int[] iArrNativeGetResourceStringArrayInfo = nativeGetResourceStringArrayInfo(this.mObject, i);
            if (iArrNativeGetResourceStringArrayInfo == null) {
                return null;
            }
            int length = iArrNativeGetResourceStringArrayInfo.length;
            CharSequence[] charSequenceArr = new CharSequence[length / 2];
            int i2 = 0;
            int i3 = 0;
            while (i2 < length) {
                int i4 = iArrNativeGetResourceStringArrayInfo[i2];
                int i5 = iArrNativeGetResourceStringArrayInfo[i2 + 1];
                charSequenceArr[i3] = (i5 < 0 || i4 <= 0) ? null : getPooledStringForCookie(i4, i5);
                i2 += 2;
                i3++;
            }
            return charSequenceArr;
        }
    }

    int[] getResourceIntArray(int i) {
        int[] iArrNativeGetResourceIntArray;
        synchronized (this) {
            ensureValidLocked();
            iArrNativeGetResourceIntArray = nativeGetResourceIntArray(this.mObject, i);
        }
        return iArrNativeGetResourceIntArray;
    }

    int[] getStyleAttributes(int i) {
        int[] iArrNativeGetStyleAttributes;
        synchronized (this) {
            ensureValidLocked();
            iArrNativeGetStyleAttributes = nativeGetStyleAttributes(this.mObject, i);
        }
        return iArrNativeGetStyleAttributes;
    }

    boolean getThemeValue(long j, int i, TypedValue typedValue, boolean z) {
        Objects.requireNonNull(typedValue, "outValue");
        synchronized (this) {
            ensureValidLocked();
            int iNativeThemeGetAttributeValue = nativeThemeGetAttributeValue(this.mObject, j, i, typedValue, z);
            if (iNativeThemeGetAttributeValue <= 0) {
                return false;
            }
            typedValue.changingConfigurations = ActivityInfo.activityInfoConfigNativeToJava(typedValue.changingConfigurations);
            if (typedValue.type == 3) {
                CharSequence pooledStringForCookie = getPooledStringForCookie(iNativeThemeGetAttributeValue, typedValue.data);
                typedValue.string = pooledStringForCookie;
                if (pooledStringForCookie == null) {
                    return false;
                }
            }
            return true;
        }
    }

    void dumpTheme(long j, int i, String str, String str2) {
        synchronized (this) {
            ensureValidLocked();
            nativeThemeDump(this.mObject, j, i, str, str2);
        }
    }

    String getResourceName(int i) {
        String strNativeGetResourceName;
        synchronized (this) {
            ensureValidLocked();
            strNativeGetResourceName = nativeGetResourceName(this.mObject, i);
        }
        return strNativeGetResourceName;
    }

    String getResourcePackageName(int i) {
        String strNativeGetResourcePackageName;
        synchronized (this) {
            ensureValidLocked();
            strNativeGetResourcePackageName = nativeGetResourcePackageName(this.mObject, i);
        }
        return strNativeGetResourcePackageName;
    }

    String getResourceTypeName(int i) {
        String strNativeGetResourceTypeName;
        synchronized (this) {
            ensureValidLocked();
            strNativeGetResourceTypeName = nativeGetResourceTypeName(this.mObject, i);
        }
        return strNativeGetResourceTypeName;
    }

    String getResourceEntryName(int i) {
        String strNativeGetResourceEntryName;
        synchronized (this) {
            ensureValidLocked();
            strNativeGetResourceEntryName = nativeGetResourceEntryName(this.mObject, i);
        }
        return strNativeGetResourceEntryName;
    }

    int getResourceIdentifier(String str, String str2, String str3) {
        int iNativeGetResourceIdentifier;
        synchronized (this) {
            ensureValidLocked();
            iNativeGetResourceIdentifier = nativeGetResourceIdentifier(this.mObject, str, str2, str3);
        }
        return iNativeGetResourceIdentifier;
    }

    int getParentThemeIdentifier(int i) {
        int iNativeGetParentThemeIdentifier;
        synchronized (this) {
            ensureValidLocked();
            iNativeGetParentThemeIdentifier = nativeGetParentThemeIdentifier(this.mObject, i);
        }
        return iNativeGetParentThemeIdentifier;
    }

    public void setResourceResolutionLoggingEnabled(boolean z) {
        synchronized (this) {
            ensureValidLocked();
            nativeSetResourceResolutionLoggingEnabled(this.mObject, z);
        }
    }

    public String getLastResourceResolution() {
        String strNativeGetLastResourceResolution;
        synchronized (this) {
            ensureValidLocked();
            strNativeGetLastResourceResolution = nativeGetLastResourceResolution(this.mObject);
        }
        return strNativeGetLastResourceResolution;
    }

    public boolean containsAllocatedTable() {
        boolean zNativeContainsAllocatedTable;
        synchronized (this) {
            ensureValidLocked();
            zNativeContainsAllocatedTable = nativeContainsAllocatedTable(this.mObject);
        }
        return zNativeContainsAllocatedTable;
    }

    CharSequence getPooledStringForCookie(int i, int i2) {
        ApkAssets[] apkAssets = getApkAssets();
        if (apkAssets == null || i > apkAssets.length) {
            return null;
        }
        return apkAssets[i - 1].getStringFromPool(i2);
    }

    public InputStream open(String str) throws IOException {
        return open(str, 2);
    }

    public InputStream open(String str, int i) throws IOException {
        AssetInputStream assetInputStream;
        Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long jNativeOpenAsset = nativeOpenAsset(this.mObject, str, i);
            if (jNativeOpenAsset == 0) {
                throw new FileNotFoundException("Asset file: " + str);
            }
            assetInputStream = new AssetInputStream(jNativeOpenAsset);
            incRefsLocked(assetInputStream.hashCode());
        }
        return assetInputStream;
    }

    public AssetFileDescriptor openFd(String str) throws IOException {
        AssetFileDescriptor assetFileDescriptor;
        Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            ParcelFileDescriptor parcelFileDescriptorNativeOpenAssetFd = nativeOpenAssetFd(this.mObject, str, this.mOffsets);
            if (parcelFileDescriptorNativeOpenAssetFd == null) {
                throw new FileNotFoundException("Asset file: " + str);
            }
            long[] jArr = this.mOffsets;
            assetFileDescriptor = new AssetFileDescriptor(parcelFileDescriptorNativeOpenAssetFd, jArr[0], jArr[1]);
        }
        return assetFileDescriptor;
    }

    public String[] list(String str) throws IOException {
        String[] strArrNativeList;
        Objects.requireNonNull(str, "path");
        synchronized (this) {
            ensureValidLocked();
            strArrNativeList = nativeList(this.mObject, str);
        }
        return strArrNativeList;
    }

    public InputStream openNonAsset(String str) throws IOException {
        return openNonAsset(0, str, 2);
    }

    public InputStream openNonAsset(String str, int i) throws IOException {
        return openNonAsset(0, str, i);
    }

    public InputStream openNonAsset(int i, String str) throws IOException {
        return openNonAsset(i, str, 2);
    }

    public InputStream openNonAsset(int i, String str, int i2) throws IOException {
        AssetInputStream assetInputStream;
        Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long jNativeOpenNonAsset = nativeOpenNonAsset(this.mObject, i, str, i2);
            if (jNativeOpenNonAsset == 0) {
                throw new FileNotFoundException("Asset absolute file: " + str);
            }
            assetInputStream = new AssetInputStream(jNativeOpenNonAsset);
            incRefsLocked(assetInputStream.hashCode());
        }
        return assetInputStream;
    }

    public AssetFileDescriptor openNonAssetFd(String str) throws IOException {
        return openNonAssetFd(0, str);
    }

    public AssetFileDescriptor openNonAssetFd(int i, String str) throws IOException {
        AssetFileDescriptor assetFileDescriptor;
        Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            ParcelFileDescriptor parcelFileDescriptorNativeOpenNonAssetFd = nativeOpenNonAssetFd(this.mObject, i, str, this.mOffsets);
            if (parcelFileDescriptorNativeOpenNonAssetFd == null) {
                throw new FileNotFoundException("Asset absolute file: " + str);
            }
            long[] jArr = this.mOffsets;
            assetFileDescriptor = new AssetFileDescriptor(parcelFileDescriptorNativeOpenNonAssetFd, jArr[0], jArr[1]);
        }
        return assetFileDescriptor;
    }

    public XmlResourceParser openXmlResourceParser(String str) throws IOException {
        return openXmlResourceParser(0, str);
    }

    public XmlResourceParser openXmlResourceParser(int i, String str) throws IOException {
        XmlBlock xmlBlockOpenXmlBlockAsset = openXmlBlockAsset(i, str, true);
        try {
            XmlResourceParser xmlResourceParserNewParser = xmlBlockOpenXmlBlockAsset.newParser(0, new Validator());
            if (xmlResourceParserNewParser == null) {
                throw new AssertionError("block.newParser() returned a null parser");
            }
            if (xmlBlockOpenXmlBlockAsset != null) {
                xmlBlockOpenXmlBlockAsset.close();
            }
            return xmlResourceParserNewParser;
        } catch (Throwable th) {
            if (xmlBlockOpenXmlBlockAsset != null) {
                try {
                    xmlBlockOpenXmlBlockAsset.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    XmlBlock openXmlBlockAsset(String str) throws IOException {
        return openXmlBlockAsset(0, str, true);
    }

    XmlBlock openXmlBlockAsset(int i, String str, boolean z) throws IOException {
        XmlBlock xmlBlock;
        Objects.requireNonNull(str, "fileName");
        synchronized (this) {
            ensureOpenLocked();
            long jNativeOpenXmlAsset = nativeOpenXmlAsset(this.mObject, i, str);
            if (jNativeOpenXmlAsset == 0) {
                throw new FileNotFoundException("Asset XML file: " + str);
            }
            xmlBlock = new XmlBlock(this, jNativeOpenXmlAsset, z);
            incRefsLocked(xmlBlock.hashCode());
        }
        return xmlBlock;
    }

    void xmlBlockGone(int i) {
        decRefs(i);
    }

    void applyStyle(long j, int i, int i2, XmlBlock.Parser parser, int[] iArr, long j2, long j3) {
        Objects.requireNonNull(iArr, "inAttrs");
        synchronized (this) {
            ensureValidLocked();
            nativeApplyStyle(this.mObject, j, i, i2, parser != null ? parser.mParseState : 0L, iArr, j2, j3);
        }
    }

    int[] getAttributeResolutionStack(long j, int i, int i2, int i3) {
        int[] iArrNativeAttributeResolutionStack;
        synchronized (this) {
            ensureValidLocked();
            iArrNativeAttributeResolutionStack = nativeAttributeResolutionStack(this.mObject, j, i3, i, i2);
        }
        return iArrNativeAttributeResolutionStack;
    }

    boolean resolveAttrs(long j, int i, int i2, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        boolean zNativeResolveAttrs;
        Objects.requireNonNull(iArr2, "inAttrs");
        Objects.requireNonNull(iArr3, "outValues");
        Objects.requireNonNull(iArr4, "outIndices");
        synchronized (this) {
            ensureValidLocked();
            zNativeResolveAttrs = nativeResolveAttrs(this.mObject, j, i, i2, iArr, iArr2, iArr3, iArr4);
        }
        return zNativeResolveAttrs;
    }

    boolean retrieveAttributes(XmlBlock.Parser parser, int[] iArr, int[] iArr2, int[] iArr3) {
        boolean zNativeRetrieveAttributes;
        Objects.requireNonNull(parser, "parser");
        Objects.requireNonNull(iArr, "inAttrs");
        Objects.requireNonNull(iArr2, "outValues");
        Objects.requireNonNull(iArr3, "outIndices");
        synchronized (this) {
            ensureValidLocked();
            zNativeRetrieveAttributes = nativeRetrieveAttributes(this.mObject, parser.mParseState, iArr, iArr2, iArr3);
        }
        return zNativeRetrieveAttributes;
    }

    long createTheme() {
        long jNativeThemeCreate;
        synchronized (this) {
            ensureValidLocked();
            jNativeThemeCreate = nativeThemeCreate(this.mObject);
            incRefsLocked(jNativeThemeCreate);
        }
        return jNativeThemeCreate;
    }

    void releaseTheme(long j) {
        decRefs(j);
    }

    static long getThemeFreeFunction() {
        return nativeGetThemeFreeFunction();
    }

    void applyStyleToTheme(long j, int i, boolean z) {
        synchronized (this) {
            ensureValidLocked();
            nativeThemeApplyStyle(this.mObject, j, i, z);
        }
    }

    AssetManager rebaseTheme(long j, AssetManager assetManager, int[] iArr, boolean[] zArr, int i) {
        if (this != assetManager) {
            synchronized (this) {
                ensureValidLocked();
                decRefs(j);
            }
            synchronized (assetManager) {
                assetManager.ensureValidLocked();
                assetManager.incRefsLocked(j);
            }
        }
        try {
            synchronized (assetManager) {
                assetManager.ensureValidLocked();
                nativeThemeRebase(assetManager.mObject, j, iArr, zArr, i);
            }
            return assetManager;
        } finally {
            Reference.reachabilityFence(assetManager);
        }
    }

    void setThemeTo(long j, AssetManager assetManager, long j2) {
        synchronized (this) {
            ensureValidLocked();
            synchronized (assetManager) {
                assetManager.ensureValidLocked();
                nativeThemeCopy(this.mObject, j, assetManager.mObject, j2);
            }
        }
    }

    protected void finalize() throws Throwable {
        synchronized (this) {
            long j = this.mObject;
            if (j != 0) {
                nativeDestroy(j);
                this.mObject = 0L;
            }
        }
    }

    public final class AssetInputStream extends InputStream {
        private long mAssetNativePtr;
        private long mLength;
        private long mMarkPos;

        @Override // java.io.InputStream
        public final boolean markSupported() {
            return true;
        }

        public final int getAssetInt() {
            throw new UnsupportedOperationException();
        }

        public final long getNativeAsset() {
            return this.mAssetNativePtr;
        }

        private AssetInputStream(long j) {
            this.mAssetNativePtr = j;
            this.mLength = AssetManager.nativeAssetGetLength(j);
        }

        @Override // java.io.InputStream
        public final int read() throws IOException {
            ensureOpen();
            return AssetManager.nativeAssetReadChar(this.mAssetNativePtr);
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr) throws IOException {
            ensureOpen();
            Objects.requireNonNull(bArr, XmlTags.TAG_BLOB);
            return AssetManager.nativeAssetRead(this.mAssetNativePtr, bArr, 0, bArr.length);
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr, int i, int i2) throws IOException {
            ensureOpen();
            Objects.requireNonNull(bArr, XmlTags.TAG_BLOB);
            return AssetManager.nativeAssetRead(this.mAssetNativePtr, bArr, i, i2);
        }

        @Override // java.io.InputStream
        public final long skip(long j) throws IOException {
            ensureOpen();
            long jNativeAssetSeek = AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0L, 0);
            long j2 = jNativeAssetSeek + j;
            long j3 = this.mLength;
            if (j2 > j3) {
                j = j3 - jNativeAssetSeek;
            }
            if (j > 0) {
                AssetManager.nativeAssetSeek(this.mAssetNativePtr, j, 0);
            }
            return j;
        }

        @Override // java.io.InputStream
        public final int available() throws IOException {
            ensureOpen();
            long jNativeAssetGetRemainingLength = AssetManager.nativeAssetGetRemainingLength(this.mAssetNativePtr);
            if (jNativeAssetGetRemainingLength > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            return (int) jNativeAssetGetRemainingLength;
        }

        @Override // java.io.InputStream
        public final void mark(int i) {
            ensureOpen();
            this.mMarkPos = AssetManager.nativeAssetSeek(this.mAssetNativePtr, 0L, 0);
        }

        @Override // java.io.InputStream
        public final void reset() throws IOException {
            ensureOpen();
            AssetManager.nativeAssetSeek(this.mAssetNativePtr, this.mMarkPos, -1);
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public final void close() throws IOException {
            long j = this.mAssetNativePtr;
            if (j != 0) {
                AssetManager.nativeAssetDestroy(j);
                this.mAssetNativePtr = 0L;
                AssetManager.this.decRefs(hashCode());
            }
        }

        protected void finalize() throws Throwable {
            close();
        }

        private void ensureOpen() {
            if (this.mAssetNativePtr == 0) {
                throw new IllegalStateException("AssetInputStream is closed");
            }
        }
    }

    public static String getCustomizedString(String str) {
        if (sCTxtParser == null) {
            sCTxtParser = CustomizedTextParser.getInstance();
        }
        return sCTxtParser.getCustomizedText(str);
    }

    public boolean isUpToDate() {
        synchronized (this) {
            if (!this.mOpen) {
                return false;
            }
            for (ApkAssets apkAssets : this.mApkAssets) {
                if (!apkAssets.isUpToDate()) {
                    return false;
                }
            }
            return true;
        }
    }

    public String[] getLocales() {
        String[] strArrNativeGetLocales;
        synchronized (this) {
            ensureValidLocked();
            strArrNativeGetLocales = nativeGetLocales(this.mObject, false);
        }
        return strArrNativeGetLocales;
    }

    public String[] getNonSystemLocales() {
        String[] strArrNativeGetLocales;
        synchronized (this) {
            ensureValidLocked();
            strArrNativeGetLocales = nativeGetLocales(this.mObject, true);
        }
        return strArrNativeGetLocales;
    }

    Configuration[] getSizeConfigurations() {
        Configuration[] configurationArrNativeGetSizeConfigurations;
        synchronized (this) {
            ensureValidLocked();
            configurationArrNativeGetSizeConfigurations = nativeGetSizeConfigurations(this.mObject);
        }
        return configurationArrNativeGetSizeConfigurations;
    }

    Configuration[] getSizeAndUiModeConfigurations() {
        Configuration[] configurationArrNativeGetSizeAndUiModeConfigurations;
        synchronized (this) {
            ensureValidLocked();
            configurationArrNativeGetSizeAndUiModeConfigurations = nativeGetSizeAndUiModeConfigurations(this.mObject);
        }
        return configurationArrNativeGetSizeAndUiModeConfigurations;
    }

    public void setConfiguration(int i, int i2, String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) {
        if (str != null) {
            setConfiguration(i, i2, null, new String[]{str}, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18);
        } else {
            setConfiguration(i, i2, null, null, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18);
        }
    }

    public void setConfiguration(int i, int i2, String str, String[] strArr, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18) {
        setConfigurationInternal(i, i2, str, strArr, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, false);
    }

    void setConfigurationInternal(int i, int i2, String str, String[] strArr, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, boolean z) {
        synchronized (this) {
            ensureValidLocked();
            nativeSetConfiguration(this.mObject, i, i2, str, strArr, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, z);
        }
    }

    public void setOverlayConstraints(int i, int i2) {
        if (Flags.rroConstraints()) {
            synchronized (this) {
                ensureValidLocked();
                nativeSetOverlayConstraints(this.mObject, i, i2);
            }
        }
    }

    public SparseArray<String> getAssignedPackageIdentifiers() {
        return getAssignedPackageIdentifiers(true, true);
    }

    public SparseArray<String> getAssignedPackageIdentifiers(boolean z, boolean z2) {
        SparseArray<String> sparseArrayNativeGetAssignedPackageIdentifiers;
        synchronized (this) {
            ensureValidLocked();
            sparseArrayNativeGetAssignedPackageIdentifiers = nativeGetAssignedPackageIdentifiers(this.mObject, z, z2);
        }
        return sparseArrayNativeGetAssignedPackageIdentifiers;
    }

    public Map<String, String> getOverlayableMap(String str) {
        Map<String, String> mapNativeGetOverlayableMap;
        synchronized (this) {
            ensureValidLocked();
            mapNativeGetOverlayableMap = nativeGetOverlayableMap(this.mObject, str);
        }
        return mapNativeGetOverlayableMap;
    }

    public String getOverlayablesToString(String str) {
        String strNativeGetOverlayablesToString;
        synchronized (this) {
            ensureValidLocked();
            strNativeGetOverlayablesToString = nativeGetOverlayablesToString(this.mObject, str);
        }
        return strNativeGetOverlayablesToString;
    }

    private void incRefsLocked(long j) {
        this.mNumRefs.incrementAndGet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decRefs(long j) {
        if (this.mNumRefs.decrementAndGet() == 0) {
            synchronized (this) {
                if (this.mNumRefs.get() == 0) {
                    long j2 = this.mObject;
                    if (j2 != 0) {
                        nativeDestroy(j2);
                        this.mObject = 0L;
                        this.mApkAssets = sEmptyApkAssets;
                    }
                }
            }
        }
    }

    synchronized void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "class=" + getClass());
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("apkAssets=");
        printWriter.println(sb.toString());
        for (int i = 0; i < this.mApkAssets.length; i++) {
            printWriter.println(str + i);
            this.mApkAssets[i].dump(printWriter, str + "  ");
        }
    }

    void applyStyle$ravenwood(long j, int i, int i2, XmlBlock.Parser parser, int[] iArr, long j2, long j3) {
        Objects.requireNonNull(iArr, "inAttrs");
        RavenwoodEnvironment ravenwoodEnvironment = RavenwoodEnvironment.getInstance();
        int[] iArr2 = (int[]) ravenwoodEnvironment.fromAddress(j2);
        int[] iArr3 = (int[]) ravenwoodEnvironment.fromAddress(j3);
        synchronized (this) {
            ensureValidLocked();
            nativeApplyStyleWithArray(this.mObject, j, i, i2, parser != null ? parser.mParseState : 0L, iArr, iArr2, iArr3);
        }
    }
}
