package androidx.slice.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import androidx.collection.ArraySet;
import androidx.lifecycle.LiveData;
import androidx.slice.Slice;
import androidx.slice.SliceConvert;
import androidx.slice.SliceSpec;
import androidx.slice.SliceSpecs;
import androidx.slice.SliceViewManagerBase;
import androidx.slice.SliceViewManagerWrapper;
import com.android.systemui.volume.VolumePanelDialog;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda1;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SliceLiveData {
    public static final ArraySet SUPPORTED_SPECS = new ArraySet(Arrays.asList(SliceSpecs.BASIC, SliceSpecs.LIST, SliceSpecs.LIST_V2, new SliceSpec("androidx.app.slice.BASIC", 1), new SliceSpec("androidx.app.slice.LIST", 1)));

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnErrorListener {
    }

    private SliceLiveData() {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SliceLiveDataImpl extends LiveData {
        public final Intent mIntent;
        public final OnErrorListener mListener;
        public final SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 mSliceCallback;
        public final SliceViewManagerWrapper mSliceViewManager;
        public final RunnableC05121 mUpdateSlice;
        public Uri mUri;

        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.slice.widget.SliceLiveData$SliceLiveDataImpl$1] */
        public SliceLiveDataImpl(Context context, Uri uri, OnErrorListener onErrorListener) {
            this.mUpdateSlice = new Runnable() { // from class: androidx.slice.widget.SliceLiveData.SliceLiveDataImpl.1
                @Override // java.lang.Runnable
                public final void run() {
                    Slice bindSlice;
                    try {
                        SliceLiveDataImpl sliceLiveDataImpl = SliceLiveDataImpl.this;
                        Uri uri2 = sliceLiveDataImpl.mUri;
                        SliceViewManagerWrapper sliceViewManagerWrapper = sliceLiveDataImpl.mSliceViewManager;
                        if (uri2 != null) {
                            sliceViewManagerWrapper.getClass();
                            if (sliceViewManagerWrapper.isAuthoritySuspended(uri2.getAuthority())) {
                                bindSlice = null;
                            } else {
                                bindSlice = SliceConvert.wrap(sliceViewManagerWrapper.mManager.bindSlice(uri2, sliceViewManagerWrapper.mSpecs), sliceViewManagerWrapper.mContext);
                            }
                        } else {
                            bindSlice = sliceViewManagerWrapper.bindSlice(sliceLiveDataImpl.mIntent);
                        }
                        SliceLiveDataImpl sliceLiveDataImpl2 = SliceLiveDataImpl.this;
                        if (sliceLiveDataImpl2.mUri == null && bindSlice != null) {
                            sliceLiveDataImpl2.mUri = bindSlice.getUri();
                            SliceLiveDataImpl sliceLiveDataImpl3 = SliceLiveDataImpl.this;
                            sliceLiveDataImpl3.mSliceViewManager.registerSliceCallback(sliceLiveDataImpl3.mUri, sliceLiveDataImpl3.mSliceCallback);
                        }
                        SliceLiveDataImpl.this.postValue(bindSlice);
                    } catch (IllegalArgumentException e) {
                        SliceLiveDataImpl.this.onSliceError(e);
                        SliceLiveDataImpl.this.postValue(null);
                    } catch (Exception e2) {
                        SliceLiveDataImpl.this.onSliceError(e2);
                        SliceLiveDataImpl.this.postValue(null);
                    }
                }
            };
            this.mSliceCallback = new SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0(this, 0);
            this.mSliceViewManager = new SliceViewManagerWrapper(context);
            this.mUri = uri;
            this.mIntent = null;
            this.mListener = onErrorListener;
        }

        @Override // androidx.lifecycle.LiveData
        public final void onActive() {
            AsyncTask.execute(this.mUpdateSlice);
            Uri uri = this.mUri;
            if (uri != null) {
                this.mSliceViewManager.registerSliceCallback(uri, this.mSliceCallback);
            }
        }

        @Override // androidx.lifecycle.LiveData
        public final void onInactive() {
            Uri uri = this.mUri;
            if (uri != null) {
                SliceViewManagerWrapper sliceViewManagerWrapper = this.mSliceViewManager;
                SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 = this.mSliceCallback;
                synchronized (sliceViewManagerWrapper.mListenerLookup) {
                    SliceViewManagerBase.SliceListenerImpl sliceListenerImpl = (SliceViewManagerBase.SliceListenerImpl) sliceViewManagerWrapper.mListenerLookup.remove(new Pair(uri, sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0));
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

        public final void onSliceError(Throwable th) {
            OnErrorListener onErrorListener = this.mListener;
            if (onErrorListener == null) {
                Log.e("SliceLiveData", "Error binding slice", th);
                return;
            }
            VolumePanelDialog$$ExternalSyntheticLambda1 volumePanelDialog$$ExternalSyntheticLambda1 = (VolumePanelDialog$$ExternalSyntheticLambda1) onErrorListener;
            VolumePanelDialog volumePanelDialog = volumePanelDialog$$ExternalSyntheticLambda1.f$0;
            Uri uri = volumePanelDialog$$ExternalSyntheticLambda1.f$1;
            if (volumePanelDialog.removeSliceLiveData(uri)) {
                return;
            }
            volumePanelDialog.mLoadedSlices.add(uri);
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.slice.widget.SliceLiveData$SliceLiveDataImpl$1] */
        public SliceLiveDataImpl(Context context, Intent intent, OnErrorListener onErrorListener) {
            this.mUpdateSlice = new Runnable() { // from class: androidx.slice.widget.SliceLiveData.SliceLiveDataImpl.1
                @Override // java.lang.Runnable
                public final void run() {
                    Slice bindSlice;
                    try {
                        SliceLiveDataImpl sliceLiveDataImpl = SliceLiveDataImpl.this;
                        Uri uri2 = sliceLiveDataImpl.mUri;
                        SliceViewManagerWrapper sliceViewManagerWrapper = sliceLiveDataImpl.mSliceViewManager;
                        if (uri2 != null) {
                            sliceViewManagerWrapper.getClass();
                            if (sliceViewManagerWrapper.isAuthoritySuspended(uri2.getAuthority())) {
                                bindSlice = null;
                            } else {
                                bindSlice = SliceConvert.wrap(sliceViewManagerWrapper.mManager.bindSlice(uri2, sliceViewManagerWrapper.mSpecs), sliceViewManagerWrapper.mContext);
                            }
                        } else {
                            bindSlice = sliceViewManagerWrapper.bindSlice(sliceLiveDataImpl.mIntent);
                        }
                        SliceLiveDataImpl sliceLiveDataImpl2 = SliceLiveDataImpl.this;
                        if (sliceLiveDataImpl2.mUri == null && bindSlice != null) {
                            sliceLiveDataImpl2.mUri = bindSlice.getUri();
                            SliceLiveDataImpl sliceLiveDataImpl3 = SliceLiveDataImpl.this;
                            sliceLiveDataImpl3.mSliceViewManager.registerSliceCallback(sliceLiveDataImpl3.mUri, sliceLiveDataImpl3.mSliceCallback);
                        }
                        SliceLiveDataImpl.this.postValue(bindSlice);
                    } catch (IllegalArgumentException e) {
                        SliceLiveDataImpl.this.onSliceError(e);
                        SliceLiveDataImpl.this.postValue(null);
                    } catch (Exception e2) {
                        SliceLiveDataImpl.this.onSliceError(e2);
                        SliceLiveDataImpl.this.postValue(null);
                    }
                }
            };
            this.mSliceCallback = new SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0(this, 1);
            this.mSliceViewManager = new SliceViewManagerWrapper(context);
            this.mUri = null;
            this.mIntent = intent;
            this.mListener = onErrorListener;
        }
    }
}
