package androidx.slice;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Pair;
import androidx.collection.ArraySet;
import androidx.slice.SliceViewManager;
import androidx.slice.widget.SliceLiveData;
import androidx.slice.widget.SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class SliceViewManagerBase extends SliceViewManager {
    public final Context mContext;
    public final ArrayMap mListenerLookup = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SliceListenerImpl {
        public final SliceViewManager.SliceCallback mCallback;
        public final Executor mExecutor;
        public boolean mPinned;
        public final Uri mUri;
        public final RunnableC04911 mUpdateSlice = new Runnable() { // from class: androidx.slice.SliceViewManagerBase.SliceListenerImpl.1
            @Override // java.lang.Runnable
            public final void run() {
                SliceListenerImpl sliceListenerImpl = SliceListenerImpl.this;
                if (!sliceListenerImpl.mPinned) {
                    try {
                        SliceViewManagerBase.this.pinSlice(sliceListenerImpl.mUri);
                        sliceListenerImpl.mPinned = true;
                    } catch (SecurityException unused) {
                    }
                }
                SliceListenerImpl sliceListenerImpl2 = SliceListenerImpl.this;
                Context context = SliceViewManagerBase.this.mContext;
                ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
                final Slice wrap = SliceConvert.wrap(((android.app.slice.SliceManager) context.getSystemService(android.app.slice.SliceManager.class)).bindSlice(sliceListenerImpl2.mUri, SliceConvert.unwrap(arraySet)), context);
                SliceListenerImpl.this.mExecutor.execute(new Runnable() { // from class: androidx.slice.SliceViewManagerBase.SliceListenerImpl.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SliceViewManager.SliceCallback sliceCallback = SliceListenerImpl.this.mCallback;
                        Slice slice = wrap;
                        SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 = (SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0) sliceCallback;
                        int i = sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0.$r8$classId;
                        sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0.f$0.postValue(slice);
                    }
                });
            }
        };
        public final C04922 mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: androidx.slice.SliceViewManagerBase.SliceListenerImpl.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                AsyncTask.execute(SliceListenerImpl.this.mUpdateSlice);
            }
        };

        /* JADX WARN: Type inference failed for: r3v1, types: [androidx.slice.SliceViewManagerBase$SliceListenerImpl$1] */
        /* JADX WARN: Type inference failed for: r3v2, types: [androidx.slice.SliceViewManagerBase$SliceListenerImpl$2] */
        public SliceListenerImpl(Uri uri, Executor executor, SliceViewManager.SliceCallback sliceCallback) {
            this.mUri = uri;
            this.mExecutor = executor;
            this.mCallback = sliceCallback;
        }
    }

    public SliceViewManagerBase(Context context) {
        this.mContext = context;
    }

    public final void registerSliceCallback(Uri uri, SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0) {
        final Handler handler = new Handler(Looper.getMainLooper());
        SliceListenerImpl sliceListenerImpl = new SliceListenerImpl(uri, new Executor(this) { // from class: androidx.slice.SliceViewManagerBase.1
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                handler.post(runnable);
            }
        }, sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0);
        Pair pair = new Pair(uri, sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0);
        synchronized (this.mListenerLookup) {
            try {
                SliceListenerImpl sliceListenerImpl2 = (SliceListenerImpl) this.mListenerLookup.put(pair, sliceListenerImpl);
                if (sliceListenerImpl2 != null) {
                    SliceViewManagerBase sliceViewManagerBase = SliceViewManagerBase.this;
                    sliceViewManagerBase.mContext.getContentResolver().unregisterContentObserver(sliceListenerImpl2.mObserver);
                    if (sliceListenerImpl2.mPinned) {
                        sliceViewManagerBase.unpinSlice(sliceListenerImpl2.mUri);
                        sliceListenerImpl2.mPinned = false;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        SliceViewManagerBase sliceViewManagerBase2 = SliceViewManagerBase.this;
        ContentResolver contentResolver = sliceViewManagerBase2.mContext.getContentResolver();
        Uri uri2 = sliceListenerImpl.mUri;
        ContentProviderClient acquireContentProviderClient = contentResolver.acquireContentProviderClient(uri2);
        if (acquireContentProviderClient != null) {
            acquireContentProviderClient.release();
            sliceViewManagerBase2.mContext.getContentResolver().registerContentObserver(uri2, true, sliceListenerImpl.mObserver);
            if (sliceListenerImpl.mPinned) {
                return;
            }
            try {
                SliceViewManagerBase.this.pinSlice(sliceListenerImpl.mUri);
                sliceListenerImpl.mPinned = true;
            } catch (SecurityException unused) {
            }
        }
    }
}
