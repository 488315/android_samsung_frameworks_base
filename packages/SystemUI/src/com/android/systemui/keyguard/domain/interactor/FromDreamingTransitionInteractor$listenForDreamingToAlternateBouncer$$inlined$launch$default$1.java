package com.android.systemui.keyguard.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $spanName;
    int I$0;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;
    final /* synthetic */ FromDreamingTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1(String str, Continuation continuation, FromDreamingTransitionInteractor fromDreamingTransitionInteractor) {
        super(2, continuation);
        this.$spanName = str;
        this.this$0 = fromDreamingTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1 fromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1 = new FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1(this.$spanName, continuation, this.this$0);
        fromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1.L$0 = obj;
        return fromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x009f  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r10.label
            r2 = 1
            java.lang.String r3 = "Coroutines"
            if (r1 == 0) goto L26
            if (r1 != r2) goto L1e
            int r0 = r10.I$0
            boolean r1 = r10.Z$0
            java.lang.Object r2 = r10.L$1
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r10 = r10.L$0
            com.android.app.tracing.coroutines.TraceData r10 = (com.android.app.tracing.coroutines.TraceData) r10
            kotlin.ResultKt.throwOnFailure(r11)     // Catch: java.lang.Throwable -> L1b
            goto L80
        L1b:
            r11 = move-exception
            goto L98
        L1e:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L26:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.Object r11 = r10.L$0
            kotlinx.coroutines.CoroutineScope r11 = (kotlinx.coroutines.CoroutineScope) r11
            java.lang.String r11 = r10.$spanName
            com.android.app.tracing.coroutines.TraceDataThreadLocal r1 = com.android.app.tracing.coroutines.TraceContextElementKt.traceThreadLocal
            java.lang.Object r1 = r1.get()
            com.android.app.tracing.coroutines.TraceData r1 = (com.android.app.tracing.coroutines.TraceData) r1
            boolean r4 = android.os.Trace.isEnabled()
            if (r1 != 0) goto L42
            if (r4 == 0) goto L40
            goto L42
        L40:
            java.lang.String r11 = "<none>"
        L42:
            if (r1 == 0) goto L47
            r1.beginSpan(r11)
        L47:
            if (r4 == 0) goto L52
            java.util.concurrent.ThreadLocalRandom r5 = java.util.concurrent.ThreadLocalRandom.current()
            int r5 = r5.nextInt()
            goto L53
        L52:
            r5 = 0
        L53:
            if (r4 == 0) goto L58
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackBegin(r5, r3, r11)
        L58:
            com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor r6 = r10.this$0     // Catch: java.lang.Throwable -> L8f
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r7 = r6.keyguardInteractor     // Catch: java.lang.Throwable -> L8f
            kotlinx.coroutines.flow.Flow r7 = r7.alternateBouncerShowing     // Catch: java.lang.Throwable -> L8f
            com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1 r8 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1
                static {
                    /*
                        com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1 r0 = new com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT 
  (r0 I:com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1)
 com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1.INSTANCE com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        java.lang.Boolean r1 = (java.lang.Boolean) r1
                        r1.booleanValue()
                        return r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$1.invoke(java.lang.Object):java.lang.Object");
                }
            }     // Catch: java.lang.Throwable -> L8f
            com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1 r9 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$filterRelevantKeyguardStateAnd$$inlined$filter$1     // Catch: java.lang.Throwable -> L96
            r9.<init>(r7, r6, r8)     // Catch: java.lang.Throwable -> L96
            com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$2 r6 = new com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$1$2     // Catch: java.lang.Throwable -> L8f
            com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor r7 = r10.this$0     // Catch: java.lang.Throwable -> L8f
            r6.<init>()     // Catch: java.lang.Throwable -> L8f
            r10.L$0 = r1     // Catch: java.lang.Throwable -> L8f
            r10.L$1 = r11     // Catch: java.lang.Throwable -> L8f
            r10.Z$0 = r4     // Catch: java.lang.Throwable -> L8f
            r10.I$0 = r5     // Catch: java.lang.Throwable -> L8f
            r10.label = r2     // Catch: java.lang.Throwable -> L8f
            java.lang.Object r10 = r9.collect(r6, r10)     // Catch: java.lang.Throwable -> L8f
            if (r10 != r0) goto L7d
            return r0
        L7d:
            r10 = r1
            r1 = r4
            r0 = r5
        L80:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L1b
            if (r1 == 0) goto L87
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L87:
            if (r10 == 0) goto L8c
            r10.endSpan()
        L8c:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            return r10
        L8f:
            r11 = move-exception
        L90:
            r10 = r1
            r1 = r4
            r0 = r5
            goto L98
        L94:
            r11 = r10
            goto L90
        L96:
            r10 = move-exception
            goto L94
        L98:
            if (r1 == 0) goto L9d
            com.android.app.tracing.TraceProxy_platformKt.asyncTraceForTrackEnd(r0, r3)
        L9d:
            if (r10 == 0) goto La2
            r10.endSpan()
        La2:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToAlternateBouncer$$inlined$launch$default$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
