package com.android.systemui.screenshot.appclips;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.lifecycle.MutableLiveData;
import com.android.systemui.screenshot.ImageExporter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                Bitmap bitmap = (Bitmap) this.f$1;
                if (bitmap != null) {
                    appClipsViewModel.mScreenshotLiveData.setValue(bitmap);
                    break;
                } else {
                    appClipsViewModel.mErrorLiveData.setValue(1);
                    break;
                }
            default:
                AppClipsViewModel appClipsViewModel2 = this.f$0;
                ListenableFuture listenableFuture = (ListenableFuture) this.f$1;
                MutableLiveData mutableLiveData = appClipsViewModel2.mErrorLiveData;
                try {
                    Uri uri = ((ImageExporter.Result) listenableFuture.get()).uri;
                    if (uri == null) {
                        mutableLiveData.setValue(1);
                    } else {
                        appClipsViewModel2.mResultLiveData.setValue(uri);
                    }
                    break;
                } catch (InterruptedException | CancellationException | ExecutionException unused) {
                    mutableLiveData.setValue(1);
                    return;
                }
        }
    }
}
