package com.android.systemui.biometrics.data.repository;

import android.hardware.face.FaceManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FacePropertyRepositoryImpl$sensorInfo$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ FacePropertyRepositoryImpl this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ FacePropertyRepositoryImpl$sensorInfo$1$callback$1 $callback;
        int label;
        final /* synthetic */ FacePropertyRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(FacePropertyRepositoryImpl facePropertyRepositoryImpl, FacePropertyRepositoryImpl$sensorInfo$1$callback$1 facePropertyRepositoryImpl$sensorInfo$1$callback$1, Continuation continuation) {
            super(2, continuation);
            this.this$0 = facePropertyRepositoryImpl;
            this.$callback = facePropertyRepositoryImpl$sensorInfo$1$callback$1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$callback, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            FaceManager faceManager = this.this$0.faceManager;
            if (faceManager == null) {
                return null;
            }
            faceManager.addAuthenticatorsRegisteredCallback(this.$callback);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FacePropertyRepositoryImpl$sensorInfo$1(FacePropertyRepositoryImpl facePropertyRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = facePropertyRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FacePropertyRepositoryImpl$sensorInfo$1 facePropertyRepositoryImpl$sensorInfo$1 = new FacePropertyRepositoryImpl$sensorInfo$1(this.this$0, continuation);
        facePropertyRepositoryImpl$sensorInfo$1.L$0 = obj;
        return facePropertyRepositoryImpl$sensorInfo$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FacePropertyRepositoryImpl$sensorInfo$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x004f, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r9, r8) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0051, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003f, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r6, r7, r8) == r0) goto L15;
     */
    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$callback$1] */
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
            r2 = 0
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L21
            if (r1 == r4) goto L19
            if (r1 != r3) goto L11
            kotlin.ResultKt.throwOnFailure(r9)
            goto L52
        L11:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L19:
            java.lang.Object r1 = r8.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r9)
            goto L42
        L21:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.Object r9 = r8.L$0
            r1 = r9
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$callback$1 r9 = new com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$callback$1
            r9.<init>()
            com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl r5 = r8.this$0
            kotlinx.coroutines.CoroutineDispatcher r6 = r5.backgroundDispatcher
            com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$1 r7 = new com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$1
            r7.<init>(r5, r9, r2)
            r8.L$0 = r1
            r8.label = r4
            java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r6, r7, r8)
            if (r9 != r0) goto L42
            goto L51
        L42:
            com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$$ExternalSyntheticLambda0 r9 = new com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1$$ExternalSyntheticLambda0
            r9.<init>()
            r8.L$0 = r2
            r8.label = r3
            java.lang.Object r8 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r9, r8)
            if (r8 != r0) goto L52
        L51:
            return r0
        L52:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.data.repository.FacePropertyRepositoryImpl$sensorInfo$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
