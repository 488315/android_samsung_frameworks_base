package androidx.slice.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import androidx.collection.ArraySet;
import androidx.lifecycle.LiveData;
import androidx.slice.Slice;
import androidx.slice.SliceSpec;
import androidx.slice.SliceSpecs;
import androidx.slice.SliceViewManager;
import androidx.slice.SliceViewManagerWrapper;
import com.android.systemui.volume.VolumePanelDialog;
import com.android.systemui.volume.VolumePanelDialog$$ExternalSyntheticLambda2;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class SliceLiveData {
    public static final ArraySet SUPPORTED_SPECS = new ArraySet(Arrays.asList(SliceSpecs.BASIC, SliceSpecs.LIST, SliceSpecs.LIST_V2, new SliceSpec("androidx.app.slice.BASIC", 1), new SliceSpec("androidx.app.slice.LIST", 1)));

    public interface OnErrorListener {
    }

    private SliceLiveData() {
    }

    public class SliceLiveDataImpl extends LiveData {
        public final Intent mIntent;
        public final OnErrorListener mListener;
        public final SliceViewManager.SliceCallback mSliceCallback;
        public final SliceViewManagerWrapper mSliceViewManager;
        public final AnonymousClass1 mUpdateSlice;
        public Uri mUri;

        /* JADX WARN: Type inference failed for: r0v0, types: [androidx.slice.widget.SliceLiveData$SliceLiveDataImpl$1] */
        public SliceLiveDataImpl(Context context, Uri uri, OnErrorListener onErrorListener) {
            this.mUpdateSlice = new Runnable() { // from class: androidx.slice.widget.SliceLiveData.SliceLiveDataImpl.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        SliceLiveDataImpl sliceLiveDataImpl = SliceLiveDataImpl.this;
                        Uri uri2 = sliceLiveDataImpl.mUri;
                        SliceViewManagerWrapper sliceViewManagerWrapper = sliceLiveDataImpl.mSliceViewManager;
                        Slice sliceBindSlice = uri2 != null ? sliceViewManagerWrapper.bindSlice(uri2) : sliceViewManagerWrapper.bindSlice(sliceLiveDataImpl.mIntent);
                        SliceLiveDataImpl sliceLiveDataImpl2 = SliceLiveDataImpl.this;
                        if (sliceLiveDataImpl2.mUri == null && sliceBindSlice != null) {
                            sliceLiveDataImpl2.mUri = Uri.parse(sliceBindSlice.mUri);
                            SliceLiveDataImpl sliceLiveDataImpl3 = SliceLiveDataImpl.this;
                            sliceLiveDataImpl3.mSliceViewManager.registerSliceCallback(sliceLiveDataImpl3.mUri, sliceLiveDataImpl3.mSliceCallback);
                        }
                        SliceLiveDataImpl.this.postValue(sliceBindSlice);
                    } catch (IllegalArgumentException e) {
                        SliceLiveDataImpl.this.onSliceError(e);
                        SliceLiveDataImpl.this.postValue(null);
                    } catch (Exception e2) {
                        SliceLiveDataImpl.this.onSliceError(e2);
                        SliceLiveDataImpl.this.postValue(null);
                    }
                }
            };
            this.mSliceCallback = new SliceViewManager.SliceCallback() { // from class: androidx.slice.widget.SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0
                @Override // androidx.slice.SliceViewManager.SliceCallback
                public final void onSliceUpdated(Slice slice) {
                    this.f$0.postValue(slice);
                }
            };
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
                this.mSliceViewManager.unregisterSliceCallback(uri, this.mSliceCallback);
            }
        }

        public final void onSliceError(Throwable th) {
            OnErrorListener onErrorListener = this.mListener;
            if (onErrorListener == null) {
                Log.e("SliceLiveData", "Error binding slice", th);
                return;
            }
            VolumePanelDialog$$ExternalSyntheticLambda2 volumePanelDialog$$ExternalSyntheticLambda2 = (VolumePanelDialog$$ExternalSyntheticLambda2) onErrorListener;
            Uri uri = volumePanelDialog$$ExternalSyntheticLambda2.f$1;
            Uri uri2 = VolumePanelDialog.REMOTE_MEDIA_SLICE_URI;
            VolumePanelDialog volumePanelDialog = volumePanelDialog$$ExternalSyntheticLambda2.f$0;
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
                    try {
                        SliceLiveDataImpl sliceLiveDataImpl = SliceLiveDataImpl.this;
                        Uri uri2 = sliceLiveDataImpl.mUri;
                        SliceViewManagerWrapper sliceViewManagerWrapper = sliceLiveDataImpl.mSliceViewManager;
                        Slice sliceBindSlice = uri2 != null ? sliceViewManagerWrapper.bindSlice(uri2) : sliceViewManagerWrapper.bindSlice(sliceLiveDataImpl.mIntent);
                        SliceLiveDataImpl sliceLiveDataImpl2 = SliceLiveDataImpl.this;
                        if (sliceLiveDataImpl2.mUri == null && sliceBindSlice != null) {
                            sliceLiveDataImpl2.mUri = Uri.parse(sliceBindSlice.mUri);
                            SliceLiveDataImpl sliceLiveDataImpl3 = SliceLiveDataImpl.this;
                            sliceLiveDataImpl3.mSliceViewManager.registerSliceCallback(sliceLiveDataImpl3.mUri, sliceLiveDataImpl3.mSliceCallback);
                        }
                        SliceLiveDataImpl.this.postValue(sliceBindSlice);
                    } catch (IllegalArgumentException e) {
                        SliceLiveDataImpl.this.onSliceError(e);
                        SliceLiveDataImpl.this.postValue(null);
                    } catch (Exception e2) {
                        SliceLiveDataImpl.this.onSliceError(e2);
                        SliceLiveDataImpl.this.postValue(null);
                    }
                }
            };
            this.mSliceCallback = new SliceViewManager.SliceCallback() { // from class: androidx.slice.widget.SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0
                @Override // androidx.slice.SliceViewManager.SliceCallback
                public final void onSliceUpdated(Slice slice) {
                    this.f$0.postValue(slice);
                }
            };
            this.mSliceViewManager = new SliceViewManagerWrapper(context);
            this.mUri = null;
            this.mIntent = intent;
            this.mListener = onErrorListener;
        }
    }
}
