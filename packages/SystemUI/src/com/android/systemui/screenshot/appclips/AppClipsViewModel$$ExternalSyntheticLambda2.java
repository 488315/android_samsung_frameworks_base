package com.android.systemui.screenshot.appclips;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.screenshot.ImageExporter;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes2.dex */
public final /* synthetic */ class AppClipsViewModel$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppClipsViewModel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AppClipsViewModel$$ExternalSyntheticLambda2(AppClipsViewModel appClipsViewModel, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = appClipsViewModel;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AppClipsViewModel appClipsViewModel = this.f$0;
                CallbackToFutureAdapter.SafeFuture safeFuture = (CallbackToFutureAdapter.SafeFuture) this.f$1;
                MutableLiveData mutableLiveData = appClipsViewModel.mErrorLiveData;
                try {
                    Uri uri = ((ImageExporter.Result) safeFuture.delegate.get()).uri;
                    if (uri == null) {
                        mutableLiveData.setValue(1);
                    } else {
                        appClipsViewModel.mResultLiveData.setValue(uri);
                    }
                    break;
                } catch (InterruptedException | CancellationException | ExecutionException unused) {
                    mutableLiveData.setValue(1);
                    return;
                }
            default:
                AppClipsViewModel appClipsViewModel2 = this.f$0;
                Bitmap bitmap = (Bitmap) this.f$1;
                if (bitmap != null) {
                    appClipsViewModel2.mScreenshotLiveData.setValue(bitmap);
                    break;
                } else {
                    appClipsViewModel2.mErrorLiveData.setValue(1);
                    break;
                }
        }
    }
}
