package androidx.loader.app;

import android.os.Bundle;
import androidx.collection.SparseArrayCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.content.Loader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class LoaderManagerImpl extends LoaderManager {
    public final LifecycleOwner mLifecycleOwner;
    public final LoaderViewModel mLoaderViewModel;

    public class LoaderInfo extends MutableLiveData {
        public final Bundle mArgs;
        public final int mId;
        public final Loader mLoader;
        public Loader mPriorLoader;

        public LoaderInfo(int i, Bundle bundle, Loader loader, Loader loader2) {
            this.mId = i;
            this.mArgs = bundle;
            this.mLoader = loader;
            this.mPriorLoader = loader2;
            if (loader.mListener != null) {
                throw new IllegalStateException("There is already a listener registered");
            }
            loader.mListener = this;
            loader.mId = i;
        }

        @Override // androidx.lifecycle.LiveData
        public final void onActive() {
            Loader loader = this.mLoader;
            loader.mStarted = true;
            loader.mReset = false;
            loader.mAbandoned = false;
        }

        @Override // androidx.lifecycle.LiveData
        public final void onInactive() {
            this.mLoader.mStarted = false;
        }

        @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
        public final void setValue(Object obj) {
            super.setValue(obj);
            Loader loader = this.mPriorLoader;
            if (loader != null) {
                loader.mReset = true;
                loader.mStarted = false;
                loader.mAbandoned = false;
                this.mPriorLoader = null;
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(this.mId);
            sb.append(" : ");
            Class<?> cls = this.mLoader.getClass();
            sb.append(cls.getSimpleName());
            sb.append("{");
            sb.append(Integer.toHexString(System.identityHashCode(cls)));
            sb.append("}}");
            return sb.toString();
        }
    }

    public class LoaderViewModel extends ViewModel {
        public static final AnonymousClass1 FACTORY = new ViewModelProvider.Factory() { // from class: androidx.loader.app.LoaderManagerImpl.LoaderViewModel.1
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public final ViewModel create(Class cls) {
                return new LoaderViewModel();
            }
        };
        public final SparseArrayCompat mLoaders = new SparseArrayCompat();

        @Override // androidx.lifecycle.ViewModel
        public final void onCleared() {
            SparseArrayCompat sparseArrayCompat = this.mLoaders;
            int size = sparseArrayCompat.size();
            for (int i = 0; i < size; i++) {
                LoaderInfo loaderInfo = (LoaderInfo) sparseArrayCompat.valueAt(i);
                Loader loader = loaderInfo.mLoader;
                loader.getClass();
                loader.mAbandoned = true;
                LoaderInfo loaderInfo2 = loader.mListener;
                if (loaderInfo2 == null) {
                    throw new IllegalStateException("No listener register");
                }
                if (loaderInfo2 != loaderInfo) {
                    throw new IllegalArgumentException("Attempting to unregister the wrong listener");
                }
                loader.mListener = null;
                loader.mReset = true;
                loader.mStarted = false;
                loader.mAbandoned = false;
            }
            int i2 = sparseArrayCompat.size;
            Object[] objArr = sparseArrayCompat.values;
            for (int i3 = 0; i3 < i2; i3++) {
                objArr[i3] = null;
            }
            sparseArrayCompat.size = 0;
            sparseArrayCompat.garbage = false;
        }
    }

    public LoaderManagerImpl(LifecycleOwner lifecycleOwner, ViewModelStore viewModelStore) {
        this.mLifecycleOwner = lifecycleOwner;
        LoaderViewModel.AnonymousClass1 anonymousClass1 = LoaderViewModel.FACTORY;
        this.mLoaderViewModel = (LoaderViewModel) new ViewModelProvider(viewModelStore, LoaderViewModel.FACTORY).get(LoaderViewModel.class);
    }

    public final void dump(PrintWriter printWriter, String str) {
        SparseArrayCompat sparseArrayCompat = this.mLoaderViewModel.mLoaders;
        if (sparseArrayCompat.size() > 0) {
            printWriter.print(str);
            printWriter.println("Loaders:");
            String str2 = str + "    ";
            for (int i = 0; i < sparseArrayCompat.size(); i++) {
                LoaderInfo loaderInfo = (LoaderInfo) sparseArrayCompat.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(sparseArrayCompat.keyAt(i));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                printWriter.print(str2);
                printWriter.print("mId=");
                printWriter.print(loaderInfo.mId);
                printWriter.print(" mArgs=");
                printWriter.println(loaderInfo.mArgs);
                printWriter.print(str2);
                printWriter.print("mLoader=");
                Loader loader = loaderInfo.mLoader;
                printWriter.println(loader);
                String str3 = str2 + "  ";
                loader.getClass();
                printWriter.print(str3);
                printWriter.print("mId=");
                printWriter.print(loader.mId);
                printWriter.print(" mListener=");
                printWriter.println(loader.mListener);
                if (loader.mStarted) {
                    printWriter.print(str3);
                    printWriter.print("mStarted=");
                    printWriter.print(loader.mStarted);
                    printWriter.print(" mContentChanged=");
                    printWriter.print(false);
                    printWriter.print(" mProcessingChange=");
                    printWriter.println(false);
                }
                if (loader.mAbandoned || loader.mReset) {
                    printWriter.print(str3);
                    printWriter.print("mAbandoned=");
                    printWriter.print(loader.mAbandoned);
                    printWriter.print(" mReset=");
                    printWriter.println(loader.mReset);
                }
                printWriter.print(str2);
                printWriter.print("mData=");
                Object value = loaderInfo.getValue();
                StringBuilder sb = new StringBuilder(64);
                if (value == null) {
                    sb.append("null");
                } else {
                    Class<?> cls = value.getClass();
                    sb.append(cls.getSimpleName());
                    sb.append("{");
                    sb.append(Integer.toHexString(System.identityHashCode(cls)));
                    sb.append("}");
                }
                printWriter.println(sb.toString());
                printWriter.print(str2);
                printWriter.print("mStarted=");
                printWriter.println(loaderInfo.hasActiveObservers());
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        Class<?> cls = this.mLifecycleOwner.getClass();
        sb.append(cls.getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(cls)));
        sb.append("}}");
        return sb.toString();
    }
}
