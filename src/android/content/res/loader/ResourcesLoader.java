package android.content.res.loader;

import android.content.res.ApkAssets;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.util.ArrayUtils;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class ResourcesLoader {
    private ApkAssets[] mApkAssets;
    private ResourcesProvider[] mPreviousProviders;
    private ResourcesProvider[] mProviders;
    private final Object mLock = new Object();
    private ArrayMap<WeakReference<Object>, UpdateCallbacks> mChangeCallbacks = new ArrayMap<>();

    public interface UpdateCallbacks {
        void onLoaderUpdated(ResourcesLoader resourcesLoader);
    }

    public List<ResourcesProvider> getProviders() {
        List<ResourcesProvider> listAsList;
        synchronized (this.mLock) {
            ResourcesProvider[] resourcesProviderArr = this.mProviders;
            listAsList = resourcesProviderArr == null ? Collections.EMPTY_LIST : Arrays.asList(resourcesProviderArr);
        }
        return listAsList;
    }

    public void addProvider(ResourcesProvider resourcesProvider) {
        synchronized (this.mLock) {
            this.mProviders = (ResourcesProvider[]) ArrayUtils.appendElement(ResourcesProvider.class, this.mProviders, resourcesProvider);
            notifyProvidersChangedLocked();
        }
    }

    public void removeProvider(ResourcesProvider resourcesProvider) {
        synchronized (this.mLock) {
            this.mProviders = (ResourcesProvider[]) ArrayUtils.removeElement(ResourcesProvider.class, this.mProviders, resourcesProvider);
            notifyProvidersChangedLocked();
        }
    }

    public void setProviders(List<ResourcesProvider> list) {
        synchronized (this.mLock) {
            this.mProviders = (ResourcesProvider[]) list.toArray(new ResourcesProvider[0]);
            notifyProvidersChangedLocked();
        }
    }

    public void clearProviders() {
        synchronized (this.mLock) {
            this.mProviders = null;
            notifyProvidersChangedLocked();
        }
    }

    public List<ApkAssets> getApkAssets() {
        synchronized (this.mLock) {
            ApkAssets[] apkAssetsArr = this.mApkAssets;
            if (apkAssetsArr == null) {
                return Collections.EMPTY_LIST;
            }
            return Arrays.asList(apkAssetsArr);
        }
    }

    public void registerOnProvidersChangedCallback(Object obj, UpdateCallbacks updateCallbacks) {
        synchronized (this.mLock) {
            this.mChangeCallbacks.put(new WeakReference<>(obj), updateCallbacks);
        }
    }

    public void unregisterOnProvidersChangedCallback(Object obj) {
        synchronized (this.mLock) {
            int size = this.mChangeCallbacks.size();
            for (int i = 0; i < size; i++) {
                if (obj == this.mChangeCallbacks.keyAt(i).get()) {
                    this.mChangeCallbacks.removeAt(i);
                    return;
                }
            }
        }
    }

    private static boolean arrayEquals(ResourcesProvider[] resourcesProviderArr, ResourcesProvider[] resourcesProviderArr2) {
        if (resourcesProviderArr == resourcesProviderArr2) {
            return true;
        }
        if (resourcesProviderArr == null || resourcesProviderArr2 == null || resourcesProviderArr.length != resourcesProviderArr2.length) {
            return false;
        }
        int length = resourcesProviderArr.length;
        for (int i = 0; i < length; i++) {
            if (resourcesProviderArr[i] != resourcesProviderArr2[i]) {
                return false;
            }
        }
        return true;
    }

    private void notifyProvidersChangedLocked() {
        ArraySet arraySet = new ArraySet();
        if (arrayEquals(this.mPreviousProviders, this.mProviders)) {
            return;
        }
        ResourcesProvider[] resourcesProviderArr = this.mProviders;
        if (resourcesProviderArr == null || resourcesProviderArr.length == 0) {
            this.mApkAssets = null;
        } else {
            this.mApkAssets = new ApkAssets[resourcesProviderArr.length];
            int length = resourcesProviderArr.length;
            for (int i = 0; i < length; i++) {
                this.mProviders[i].incrementRefCount();
                this.mApkAssets[i] = this.mProviders[i].getApkAssets();
            }
        }
        ResourcesProvider[] resourcesProviderArr2 = this.mPreviousProviders;
        if (resourcesProviderArr2 != null) {
            for (ResourcesProvider resourcesProvider : resourcesProviderArr2) {
                resourcesProvider.decrementRefCount();
            }
        }
        this.mPreviousProviders = this.mProviders;
        for (int size = this.mChangeCallbacks.size() - 1; size >= 0; size--) {
            if (this.mChangeCallbacks.keyAt(size).refersTo(null)) {
                this.mChangeCallbacks.removeAt(size);
            } else {
                arraySet.add(this.mChangeCallbacks.valueAt(size));
            }
        }
        int size2 = arraySet.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ((UpdateCallbacks) arraySet.valueAt(i2)).onLoaderUpdated(this);
        }
    }
}
