package com.samsung.android.sesl.transparentvideo;

import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$MediaError;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class VideoRenderModel$setMediaInternal$2 extends Lambda implements Function1 {
    final /* synthetic */ VideoRenderModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoRenderModel$setMediaInternal$2(VideoRenderModel videoRenderModel) {
        super(1);
        this.this$0 = videoRenderModel;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        IMediaPlayer$MediaError iMediaPlayer$MediaError = (IMediaPlayer$MediaError) obj;
        Function1 function1 = this.this$0.onError;
        if (function1 != null) {
            function1.mo779invoke(iMediaPlayer$MediaError);
        }
        return Unit.INSTANCE;
    }
}
