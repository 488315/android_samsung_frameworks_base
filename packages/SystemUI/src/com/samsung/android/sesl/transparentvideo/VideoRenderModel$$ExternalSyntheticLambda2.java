package com.samsung.android.sesl.transparentvideo;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.util.Log;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes4.dex */
public final /* synthetic */ class VideoRenderModel$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ VideoRenderModel f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ VideoRenderModel$$ExternalSyntheticLambda2(VideoRenderModel videoRenderModel, AssetFileDescriptor assetFileDescriptor) {
        this.f$0 = videoRenderModel;
        this.f$1 = assetFileDescriptor;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        VideoRenderModel videoRenderModel = this.f$0;
        Object obj = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                int i = VideoRenderModel.$r8$clinit;
                videoRenderModel.getClass();
                Log.i("VideoRenderModel", "Media set. uri=" + ((Uri) obj));
                break;
            default:
                int i2 = VideoRenderModel.$r8$clinit;
                videoRenderModel.getClass();
                Log.i("VideoRenderModel", "Media set. assetFd=" + ((AssetFileDescriptor) obj));
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ VideoRenderModel$$ExternalSyntheticLambda2(VideoRenderModel videoRenderModel, Uri uri) {
        this.f$0 = videoRenderModel;
        this.f$1 = uri;
    }
}
