package com.samsung.android.sesl.transparentvideo;

import com.samsung.android.sesl.transparentvideo.mediaplayer.IMediaPlayer$MediaError;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final /* synthetic */ class VideoRenderModel$$ExternalSyntheticLambda5 implements Function1 {
    public final /* synthetic */ VideoRenderModel f$0;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        IMediaPlayer$MediaError iMediaPlayer$MediaError = (IMediaPlayer$MediaError) obj;
        Function1 function1 = this.f$0.onError;
        if (function1 != null) {
            function1.mo781invoke(iMediaPlayer$MediaError);
        }
        return Unit.INSTANCE;
    }
}
