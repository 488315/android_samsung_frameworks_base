package androidx.slice;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Pair;
import androidx.collection.ArraySet;
import androidx.collection.ArraySet.ElementIterator;
import androidx.slice.SliceViewManager;
import androidx.slice.widget.SliceLiveData;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SliceViewManagerBase extends SliceViewManager {
    public final Context mContext;
    public final ArrayMap mListenerLookup = new ArrayMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SliceListenerImpl {
        public final SliceViewManager.SliceCallback mCallback;
        public final Executor mExecutor;
        public boolean mPinned;
        public final Uri mUri;
        public final AnonymousClass1 mUpdateSlice = new Runnable() { // from class: androidx.slice.SliceViewManagerBase.SliceListenerImpl.1
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
                Uri uri = sliceListenerImpl2.mUri;
                ArraySet arraySet = SliceLiveData.SUPPORTED_SPECS;
                android.app.slice.SliceManager sliceManager = (android.app.slice.SliceManager) context.getSystemService(android.app.slice.SliceManager.class);
                ArraySet arraySet2 = new ArraySet();
                if (arraySet != null) {
                    ArraySet.ElementIterator elementIterator = arraySet.new ElementIterator();
                    while (elementIterator.hasNext()) {
                        SliceSpec sliceSpec = (SliceSpec) elementIterator.next();
                        arraySet2.add(sliceSpec == null ? null : new android.app.slice.SliceSpec(sliceSpec.mType, sliceSpec.mRevision));
                    }
                }
                final Slice wrap = SliceConvert.wrap(sliceManager.bindSlice(uri, arraySet2), context);
                SliceListenerImpl.this.mExecutor.execute(new Runnable() { // from class: androidx.slice.SliceViewManagerBase.SliceListenerImpl.1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SliceListenerImpl.this.mCallback.onSliceUpdated(wrap);
                    }
                });
            }
        };
        public final AnonymousClass2 mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: androidx.slice.SliceViewManagerBase.SliceListenerImpl.2
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

    @Override // androidx.slice.SliceViewManager
    public final void registerSliceCallback(Uri uri, SliceViewManager.SliceCallback sliceCallback) {
        final Handler handler = new Handler(Looper.getMainLooper());
        SliceListenerImpl sliceListenerImpl = new SliceListenerImpl(uri, new Executor(this) { // from class: androidx.slice.SliceViewManagerBase.1
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                handler.post(runnable);
            }
        }, sliceCallback);
        Pair pair = new Pair(uri, sliceCallback);
        synchronized (this.mListenerLookup) {
            SliceListenerImpl sliceListenerImpl2 = (SliceListenerImpl) this.mListenerLookup.put(pair, sliceListenerImpl);
            if (sliceListenerImpl2 != null) {
                SliceViewManagerBase sliceViewManagerBase = SliceViewManagerBase.this;
                sliceViewManagerBase.mContext.getContentResolver().unregisterContentObserver(sliceListenerImpl2.mObserver);
                if (sliceListenerImpl2.mPinned) {
                    sliceViewManagerBase.unpinSlice(sliceListenerImpl2.mUri);
                    sliceListenerImpl2.mPinned = false;
                }
            }
        }
        SliceViewManagerBase sliceViewManagerBase2 = SliceViewManagerBase.this;
        ContentProviderClient acquireContentProviderClient = sliceViewManagerBase2.mContext.getContentResolver().acquireContentProviderClient(sliceListenerImpl.mUri);
        if (acquireContentProviderClient != null) {
            acquireContentProviderClient.release();
            sliceViewManagerBase2.mContext.getContentResolver().registerContentObserver(sliceListenerImpl.mUri, true, sliceListenerImpl.mObserver);
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

    @Override // androidx.slice.SliceViewManager
    public final void unregisterSliceCallback(Uri uri, SliceViewManager.SliceCallback sliceCallback) {
        synchronized (this.mListenerLookup) {
            SliceListenerImpl sliceListenerImpl = (SliceListenerImpl) this.mListenerLookup.remove(new Pair(uri, sliceCallback));
            if (sliceListenerImpl != null) {
                SliceViewManagerBase sliceViewManagerBase = SliceViewManagerBase.this;
                sliceViewManagerBase.mContext.getContentResolver().unregisterContentObserver(sliceListenerImpl.mObserver);
                if (sliceListenerImpl.mPinned) {
                    sliceViewManagerBase.unpinSlice(sliceListenerImpl.mUri);
                    sliceListenerImpl.mPinned = false;
                }
            }
        }
    }
}
