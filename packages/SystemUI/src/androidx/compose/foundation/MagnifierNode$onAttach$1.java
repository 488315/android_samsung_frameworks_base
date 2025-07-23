package androidx.compose.foundation;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MagnifierNode$onAttach$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MagnifierNode this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagnifierNode$onAttach$1(MagnifierNode magnifierNode, Continuation continuation) {
        super(2, continuation);
        this.this$0 = magnifierNode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MagnifierNode$onAttach$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MagnifierNode$onAttach$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:15:0x0032 -> B:8:0x001f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0049 -> B:6:0x004c). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4c
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            kotlin.ResultKt.throwOnFailure(r6)
            goto L2e
        L1c:
            kotlin.ResultKt.throwOnFailure(r6)
        L1f:
            androidx.compose.foundation.MagnifierNode r6 = r5.this$0
            kotlinx.coroutines.channels.BufferedChannel r6 = r6.drawSignalChannel
            if (r6 == 0) goto L2e
            r5.label = r3
            java.lang.Object r6 = r6.receive(r5)
            if (r6 != r0) goto L2e
            goto L4b
        L2e:
            androidx.compose.foundation.MagnifierNode r6 = r5.this$0
            androidx.compose.foundation.PlatformMagnifier r6 = r6.magnifier
            if (r6 == 0) goto L1f
            androidx.compose.foundation.MagnifierNode$onAttach$1$1 r6 = new kotlin.jvm.functions.Function1() { // from class: androidx.compose.foundation.MagnifierNode$onAttach$1.1
                static {
                    /*
                        androidx.compose.foundation.MagnifierNode$onAttach$1$1 r0 = new androidx.compose.foundation.MagnifierNode$onAttach$1$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:androidx.compose.foundation.MagnifierNode$onAttach$1$1) androidx.compose.foundation.MagnifierNode$onAttach$1.1.INSTANCE androidx.compose.foundation.MagnifierNode$onAttach$1$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierNode$onAttach$1.AnonymousClass1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierNode$onAttach$1.AnonymousClass1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final /* bridge */ /* synthetic */ java.lang.Object mo779invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        java.lang.Number r1 = (java.lang.Number) r1
                        r1.longValue()
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierNode$onAttach$1.AnonymousClass1.mo779invoke(java.lang.Object):java.lang.Object");
                }
            }
            r5.label = r2
            kotlin.coroutines.CoroutineContext r1 = r5.getContext()
            androidx.compose.runtime.MonotonicFrameClock r1 = androidx.compose.runtime.MonotonicFrameClockKt.getMonotonicFrameClock(r1)
            androidx.compose.runtime.MonotonicFrameClockKt$withFrameMillis$2 r4 = new androidx.compose.runtime.MonotonicFrameClockKt$withFrameMillis$2
            r4.<init>(r6)
            java.lang.Object r6 = r1.withFrameNanos(r4, r5)
            if (r6 != r0) goto L4c
        L4b:
            return r0
        L4c:
            androidx.compose.foundation.MagnifierNode r6 = r5.this$0
            androidx.compose.foundation.PlatformMagnifier r6 = r6.magnifier
            if (r6 == 0) goto L1f
            androidx.compose.foundation.PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl r6 = (androidx.compose.foundation.PlatformMagnifierFactoryApi28Impl$PlatformMagnifierImpl) r6
            android.widget.Magnifier r6 = r6.magnifier
            r6.update()
            goto L1f
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.MagnifierNode$onAttach$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
