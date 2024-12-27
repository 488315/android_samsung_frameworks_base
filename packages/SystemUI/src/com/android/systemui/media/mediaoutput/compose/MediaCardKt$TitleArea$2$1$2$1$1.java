package com.android.systemui.media.mediaoutput.compose;

import com.android.systemui.media.mediaoutput.analytics.MediaOutputLogging;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaCardKt$TitleArea$2$1$2$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $packageName;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCardKt$TitleArea$2$1$2$1$1(String str, Continuation continuation) {
        super(2, continuation);
        this.$packageName = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaCardKt$TitleArea$2$1$2$1$1(this.$packageName, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaCardKt$TitleArea$2$1$2$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MediaOutputLogging mediaOutputLogging = MediaOutputLogging.INSTANCE;
        MediaOutputLogging.ScreenId screenId = MediaOutputLogging.ScreenId.MEDIA_OUTPUT;
        MediaOutputLogging.Event event = MediaOutputLogging.Event.APP_ICON;
        Map mapOf = MapsKt__MapsJVMKt.mapOf(new Pair(MediaOutputLogging.CustomKey.APP, this.$packageName));
        mediaOutputLogging.getClass();
        MediaOutputLogging.sendEventCDLog(screenId, event, mapOf);
        return Unit.INSTANCE;
    }
}
