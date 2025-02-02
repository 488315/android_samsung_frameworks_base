package androidx.loader.app;

import android.util.Log;
import androidx.collection.SparseArrayCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
final class LoaderManagerImpl extends LoaderManager {
    private final LifecycleOwner mLifecycleOwner;
    private final LoaderViewModel mLoaderViewModel;

    public static class LoaderInfo<D> extends MutableLiveData<D> {
        @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
        public final void setValue(D d) {
            super.setValue(d);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #0 : ");
            throw null;
        }
    }

    static class LoaderViewModel extends ViewModel {
        private static final ViewModelProvider.Factory FACTORY = new C01481();
        private SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat<>();

        /* renamed from: androidx.loader.app.LoaderManagerImpl$LoaderViewModel$1 */
        final class C01481 implements ViewModelProvider.Factory {
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public final <T extends ViewModel> T create(Class<T> cls) {
                return new LoaderViewModel();
            }
        }

        LoaderViewModel() {
        }

        static LoaderViewModel getInstance(ViewModelStore viewModelStore) {
            return (LoaderViewModel) new ViewModelProvider(viewModelStore, FACTORY).get(LoaderViewModel.class);
        }

        public final void dump(String str, PrintWriter printWriter) {
            if (this.mLoaders.size > 0) {
                printWriter.print(str);
                printWriter.println("Loaders:");
                SparseArrayCompat<LoaderInfo> sparseArrayCompat = this.mLoaders;
                if (sparseArrayCompat.size <= 0) {
                    return;
                }
                LoaderInfo loaderInfo = (LoaderInfo) sparseArrayCompat.values[0];
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.mLoaders.keys[0]);
                printWriter.print(": ");
                loaderInfo.toString();
                throw null;
            }
        }

        final void markForRedelivery() {
            int i = this.mLoaders.size;
            for (int i2 = 0; i2 < i; i2++) {
                ((LoaderInfo) this.mLoaders.values[i2]).getClass();
            }
        }

        @Override // androidx.lifecycle.ViewModel
        protected final void onCleared() {
            SparseArrayCompat<LoaderInfo> sparseArrayCompat = this.mLoaders;
            int i = sparseArrayCompat.size;
            if (i <= 0) {
                Object[] objArr = sparseArrayCompat.values;
                for (int i2 = 0; i2 < i; i2++) {
                    objArr[i2] = null;
                }
                sparseArrayCompat.size = 0;
                return;
            }
            LoaderInfo loaderInfo = (LoaderInfo) sparseArrayCompat.values[0];
            loaderInfo.getClass();
            if (!Log.isLoggable("LoaderManager", 3)) {
                throw null;
            }
            Log.d("LoaderManager", "  Destroying: " + loaderInfo);
            throw null;
        }
    }

    LoaderManagerImpl(LifecycleOwner lifecycleOwner, ViewModelStore viewModelStore) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mLoaderViewModel = LoaderViewModel.getInstance(viewModelStore);
    }

    @Override // androidx.loader.app.LoaderManager
    @Deprecated
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mLoaderViewModel.dump(str, printWriter);
    }

    @Override // androidx.loader.app.LoaderManager
    public final void markForRedelivery() {
        this.mLoaderViewModel.markForRedelivery();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        sb.append(lifecycleOwner.getClass().getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(lifecycleOwner)));
        sb.append("}}");
        return sb.toString();
    }
}
