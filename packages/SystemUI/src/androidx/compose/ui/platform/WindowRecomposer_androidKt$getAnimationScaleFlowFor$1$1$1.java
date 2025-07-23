package androidx.compose.ui.platform;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Uri $animationScaleUri;
    final /* synthetic */ Context $applicationContext;
    final /* synthetic */ Channel $channel;
    final /* synthetic */ WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1 $contentObserver;
    final /* synthetic */ ContentResolver $resolver;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1(ContentResolver contentResolver, Uri uri, WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1 windowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1, Channel channel, Context context, Continuation continuation) {
        super(2, continuation);
        this.$resolver = contentResolver;
        this.$animationScaleUri = uri;
        this.$contentObserver = windowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1;
        this.$channel = channel;
        this.$applicationContext = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1 windowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1 = new WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1(this.$resolver, this.$animationScaleUri, this.$contentObserver, this.$channel, this.$applicationContext, continuation);
        windowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1.L$0 = obj;
        return windowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x007f, code lost:
    
        if (r4.emit(r5, r8) == r0) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005f A[Catch: all -> 0x0019, TRY_LEAVE, TryCatch #0 {all -> 0x0019, blocks: (B:7:0x0014, B:9:0x0047, B:15:0x0057, B:17:0x005f, B:25:0x002c, B:27:0x0041), top: B:2:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0082  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x007f -> B:8:0x0017). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L30
            if (r1 == r3) goto L24
            if (r1 != r2) goto L1c
            java.lang.Object r1 = r8.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r1
            java.lang.Object r4 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L19
        L17:
            r9 = r4
            goto L47
        L19:
            r9 = move-exception
            goto L8c
        L1c:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L24:
            java.lang.Object r1 = r8.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r1
            java.lang.Object r4 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: java.lang.Throwable -> L19
            goto L57
        L30:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
            android.content.ContentResolver r1 = r8.$resolver
            android.net.Uri r4 = r8.$animationScaleUri
            r5 = 0
            androidx.compose.ui.platform.WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1 r6 = r8.$contentObserver
            r1.registerContentObserver(r4, r5, r6)
            kotlinx.coroutines.channels.Channel r1 = r8.$channel     // Catch: java.lang.Throwable -> L19
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L19
        L47:
            r8.L$0 = r9     // Catch: java.lang.Throwable -> L19
            r8.L$1 = r1     // Catch: java.lang.Throwable -> L19
            r8.label = r3     // Catch: java.lang.Throwable -> L19
            java.lang.Object r4 = r1.hasNext(r8)     // Catch: java.lang.Throwable -> L19
            if (r4 != r0) goto L54
            goto L81
        L54:
            r7 = r4
            r4 = r9
            r9 = r7
        L57:
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: java.lang.Throwable -> L19
            boolean r9 = r9.booleanValue()     // Catch: java.lang.Throwable -> L19
            if (r9 == 0) goto L82
            r1.next()     // Catch: java.lang.Throwable -> L19
            android.content.Context r9 = r8.$applicationContext     // Catch: java.lang.Throwable -> L19
            android.content.ContentResolver r9 = r9.getContentResolver()     // Catch: java.lang.Throwable -> L19
            java.lang.String r5 = "animator_duration_scale"
            r6 = 1065353216(0x3f800000, float:1.0)
            float r9 = android.provider.Settings.Global.getFloat(r9, r5, r6)     // Catch: java.lang.Throwable -> L19
            java.lang.Float r5 = new java.lang.Float     // Catch: java.lang.Throwable -> L19
            r5.<init>(r9)     // Catch: java.lang.Throwable -> L19
            r8.L$0 = r4     // Catch: java.lang.Throwable -> L19
            r8.L$1 = r1     // Catch: java.lang.Throwable -> L19
            r8.label = r2     // Catch: java.lang.Throwable -> L19
            java.lang.Object r9 = r4.emit(r5, r8)     // Catch: java.lang.Throwable -> L19
            if (r9 != r0) goto L17
        L81:
            return r0
        L82:
            android.content.ContentResolver r9 = r8.$resolver
            androidx.compose.ui.platform.WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1 r8 = r8.$contentObserver
            r9.unregisterContentObserver(r8)
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        L8c:
            android.content.ContentResolver r0 = r8.$resolver
            androidx.compose.ui.platform.WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$contentObserver$1 r8 = r8.$contentObserver
            r0.unregisterContentObserver(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.WindowRecomposer_androidKt$getAnimationScaleFlowFor$1$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
