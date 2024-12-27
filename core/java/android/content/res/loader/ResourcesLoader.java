package android.content.res.loader;

import android.content.res.ApkAssets;
import android.util.ArrayMap;
import android.util.ArraySet;

import com.android.internal.util.ArrayUtils;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        List<ResourcesProvider> emptyList;
        synchronized (this.mLock) {
            emptyList =
                    this.mProviders == null
                            ? Collections.emptyList()
                            : Arrays.asList(this.mProviders);
        }
        return emptyList;
    }

    public void addProvider(ResourcesProvider resourcesProvider) {
        synchronized (this.mLock) {
            this.mProviders =
                    (ResourcesProvider[])
                            ArrayUtils.appendElement(
                                    ResourcesProvider.class, this.mProviders, resourcesProvider);
            notifyProvidersChangedLocked();
        }
    }

    public void removeProvider(ResourcesProvider resourcesProvider) {
        synchronized (this.mLock) {
            this.mProviders =
                    (ResourcesProvider[])
                            ArrayUtils.removeElement(
                                    ResourcesProvider.class, this.mProviders, resourcesProvider);
            notifyProvidersChangedLocked();
        }
    }

    public void setProviders(List<ResourcesProvider> resourcesProviders) {
        synchronized (this.mLock) {
            this.mProviders =
                    (ResourcesProvider[]) resourcesProviders.toArray(new ResourcesProvider[0]);
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
            if (this.mApkAssets == null) {
                return Collections.emptyList();
            }
            return Arrays.asList(this.mApkAssets);
        }
    }

    public void registerOnProvidersChangedCallback(Object instance, UpdateCallbacks callbacks) {
        synchronized (this.mLock) {
            this.mChangeCallbacks.put(new WeakReference<>(instance), callbacks);
        }
    }

    public void unregisterOnProvidersChangedCallback(Object instance) {
        synchronized (this.mLock) {
            int n = this.mChangeCallbacks.size();
            for (int i = 0; i < n; i++) {
                WeakReference<Object> key = this.mChangeCallbacks.keyAt(i);
                if (instance == key.get()) {
                    this.mChangeCallbacks.removeAt(i);
                    return;
                }
            }
        }
    }

    private static boolean arrayEquals(ResourcesProvider[] a1, ResourcesProvider[] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null || a1.length != a2.length) {
            return false;
        }
        int n = a1.length;
        for (int i = 0; i < n; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }

    private void notifyProvidersChangedLocked() {
        ArraySet<UpdateCallbacks> uniqueCallbacks = new ArraySet<>();
        if (arrayEquals(this.mPreviousProviders, this.mProviders)) {
            return;
        }
        if (this.mProviders == null || this.mProviders.length == 0) {
            this.mApkAssets = null;
        } else {
            this.mApkAssets = new ApkAssets[this.mProviders.length];
            int n = this.mProviders.length;
            for (int i = 0; i < n; i++) {
                this.mProviders[i].incrementRefCount();
                this.mApkAssets[i] = this.mProviders[i].getApkAssets();
            }
        }
        if (this.mPreviousProviders != null) {
            for (ResourcesProvider provider : this.mPreviousProviders) {
                provider.decrementRefCount();
            }
        }
        this.mPreviousProviders = this.mProviders;
        for (int i2 = this.mChangeCallbacks.size() - 1; i2 >= 0; i2--) {
            WeakReference<Object> key = this.mChangeCallbacks.keyAt(i2);
            if (key.refersTo(null)) {
                this.mChangeCallbacks.removeAt(i2);
            } else {
                uniqueCallbacks.add(this.mChangeCallbacks.valueAt(i2));
            }
        }
        int n2 = uniqueCallbacks.size();
        for (int i3 = 0; i3 < n2; i3++) {
            uniqueCallbacks.valueAt(i3).onLoaderUpdated(this);
        }
    }
}
