package androidx.picker.loader;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "androidx.picker.loader.AppIconFlow", m277f = "AppIconFlow.kt", m278l = {32, 33}, m279m = "collect")
/* loaded from: classes.dex */
final class AppIconFlow$collect$1 extends ContinuationImpl {
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ AppIconFlow this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppIconFlow$collect$1(AppIconFlow appIconFlow, Continuation<? super AppIconFlow$collect$1> continuation) {
        super(continuation);
        this.this$0 = appIconFlow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        return this.this$0.collect(null, this);
    }
}
