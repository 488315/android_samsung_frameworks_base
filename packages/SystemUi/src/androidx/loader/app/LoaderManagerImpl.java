package androidx.loader.app;

import android.os.Bundle;
import androidx.collection.SparseArrayCompat;
import androidx.collection.SparseArrayCompatKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import com.android.settingslib.suggestions.SuggestionControllerMixinCompat;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LoaderManagerImpl extends LoaderManager {
    public final LifecycleOwner mLifecycleOwner;
    public final LoaderViewModel mLoaderViewModel;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoaderInfo extends MutableLiveData implements Loader.OnLoadCompleteListener {
        public final Bundle mArgs;
        public final int mId;
        public LifecycleOwner mLifecycleOwner;
        public final Loader mLoader;
        public LoaderObserver mObserver;
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

        public final Loader destroy(boolean z) {
            Loader loader = this.mLoader;
            loader.onCancelLoad();
            loader.mAbandoned = true;
            LoaderObserver loaderObserver = this.mObserver;
            if (loaderObserver != null) {
                removeObserver(loaderObserver);
                if (z && loaderObserver.mDeliveredData) {
                    loaderObserver.mCallback.getClass();
                }
            }
            Loader.OnLoadCompleteListener onLoadCompleteListener = loader.mListener;
            if (onLoadCompleteListener == null) {
                throw new IllegalStateException("No listener register");
            }
            if (onLoadCompleteListener != this) {
                throw new IllegalArgumentException("Attempting to unregister the wrong listener");
            }
            loader.mListener = null;
            if ((loaderObserver == null || loaderObserver.mDeliveredData) && !z) {
                return loader;
            }
            loader.onReset();
            loader.mReset = true;
            loader.mStarted = false;
            loader.mAbandoned = false;
            loader.mContentChanged = false;
            loader.mProcessingChange = false;
            return this.mPriorLoader;
        }

        public final void markForRedelivery() {
            LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
            LoaderObserver loaderObserver = this.mObserver;
            if (lifecycleOwner == null || loaderObserver == null) {
                return;
            }
            super.removeObserver(loaderObserver);
            observe(lifecycleOwner, loaderObserver);
        }

        @Override // androidx.lifecycle.LiveData
        public final void onActive() {
            Loader loader = this.mLoader;
            loader.mStarted = true;
            loader.mReset = false;
            loader.mAbandoned = false;
            loader.onStartLoading();
        }

        @Override // androidx.lifecycle.LiveData
        public final void onInactive() {
            Loader loader = this.mLoader;
            loader.mStarted = false;
            loader.onStopLoading();
        }

        @Override // androidx.lifecycle.LiveData
        public final void removeObserver(Observer observer) {
            super.removeObserver(observer);
            this.mLifecycleOwner = null;
            this.mObserver = null;
        }

        @Override // androidx.lifecycle.MutableLiveData, androidx.lifecycle.LiveData
        public final void setValue(Object obj) {
            super.setValue(obj);
            Loader loader = this.mPriorLoader;
            if (loader != null) {
                loader.onReset();
                loader.mReset = true;
                loader.mStarted = false;
                loader.mAbandoned = false;
                loader.mContentChanged = false;
                loader.mProcessingChange = false;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoaderObserver implements Observer {
        public final LoaderManager.LoaderCallbacks mCallback;
        public boolean mDeliveredData = false;

        public LoaderObserver(Loader loader, LoaderManager.LoaderCallbacks loaderCallbacks) {
            this.mCallback = loaderCallbacks;
        }

        @Override // androidx.lifecycle.Observer
        public final void onChanged(Object obj) {
            this.mDeliveredData = true;
            ((SuggestionControllerMixinCompat) this.mCallback).getClass();
            throw null;
        }

        public final String toString() {
            return this.mCallback.toString();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoaderViewModel extends ViewModel {
        public static final C03091 FACTORY = new ViewModelProvider.Factory() { // from class: androidx.loader.app.LoaderManagerImpl.LoaderViewModel.1
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
                ((LoaderInfo) sparseArrayCompat.valueAt(i)).destroy(true);
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
        this.mLoaderViewModel = (LoaderViewModel) new ViewModelProvider(viewModelStore, LoaderViewModel.FACTORY).get(LoaderViewModel.class);
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        SparseArrayCompat sparseArrayCompat = this.mLoaderViewModel.mLoaders;
        if (sparseArrayCompat.size() > 0) {
            printWriter.print(str);
            printWriter.println("Loaders:");
            String str2 = str + "    ";
            for (int i = 0; i < sparseArrayCompat.size(); i++) {
                LoaderInfo loaderInfo = (LoaderInfo) sparseArrayCompat.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                if (sparseArrayCompat.garbage) {
                    SparseArrayCompatKt.access$gc(sparseArrayCompat);
                }
                printWriter.print(sparseArrayCompat.keys[i]);
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
                loader.dump(str2 + "  ", fileDescriptor, printWriter, strArr);
                if (loaderInfo.mObserver != null) {
                    printWriter.print(str2);
                    printWriter.print("mCallbacks=");
                    printWriter.println(loaderInfo.mObserver);
                    LoaderObserver loaderObserver = loaderInfo.mObserver;
                    loaderObserver.getClass();
                    printWriter.print(str2 + "  ");
                    printWriter.print("mDeliveredData=");
                    printWriter.println(loaderObserver.mDeliveredData);
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
                printWriter.println(loaderInfo.mActiveCount > 0);
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
