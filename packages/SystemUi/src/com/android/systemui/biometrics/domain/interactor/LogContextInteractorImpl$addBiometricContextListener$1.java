package com.android.systemui.biometrics.domain.interactor;

import android.hardware.biometrics.IBiometricContextListener;
import android.os.IBinder;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1", m277f = "LogContextInteractor.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class LogContextInteractorImpl$addBiometricContextListener$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ IBiometricContextListener $listener;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LogContextInteractorImpl this$0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$1", m277f = "LogContextInteractor.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$1 */
    final class C10911 extends SuspendLambda implements Function2 {
        final /* synthetic */ IBiometricContextListener $listener;
        /* synthetic */ int I$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10911(IBiometricContextListener iBiometricContextListener, Continuation<? super C10911> continuation) {
            super(2, continuation);
            this.$listener = iBiometricContextListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10911 c10911 = new C10911(this.$listener, continuation);
            c10911.I$0 = ((Number) obj).intValue();
            return c10911;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10911) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$listener.onFoldChanged(this.I$0);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$2", m277f = "LogContextInteractor.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$2 */
    final class C10922 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        int label;

        public C10922(Continuation<? super C10922> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C10922 c10922 = new C10922((Continuation) obj3);
            c10922.L$0 = (Throwable) obj2;
            return c10922.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.w("ContextRepositoryImpl", "failed to notify new fold state", (Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$3", m277f = "LogContextInteractor.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$3 */
    final class C10933 extends SuspendLambda implements Function2 {
        final /* synthetic */ IBiometricContextListener $listener;
        /* synthetic */ int I$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10933(IBiometricContextListener iBiometricContextListener, Continuation<? super C10933> continuation) {
            super(2, continuation);
            this.$listener = iBiometricContextListener;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C10933 c10933 = new C10933(this.$listener, continuation);
            c10933.I$0 = ((Number) obj).intValue();
            return c10933;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10933) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$listener.onDisplayStateChanged(this.I$0);
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$4", m277f = "LogContextInteractor.kt", m278l = {}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1$4 */
    final class C10944 extends SuspendLambda implements Function3 {
        /* synthetic */ Object L$0;
        int label;

        public C10944(Continuation<? super C10944> continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C10944 c10944 = new C10944((Continuation) obj3);
            c10944.L$0 = (Throwable) obj2;
            return c10944.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Log.w("ContextRepositoryImpl", "failed to notify new display state", (Throwable) this.L$0);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LogContextInteractorImpl$addBiometricContextListener$1(LogContextInteractorImpl logContextInteractorImpl, IBiometricContextListener iBiometricContextListener, Continuation<? super LogContextInteractorImpl$addBiometricContextListener$1> continuation) {
        super(2, continuation);
        this.this$0 = logContextInteractorImpl;
        this.$listener = iBiometricContextListener;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LogContextInteractorImpl$addBiometricContextListener$1 logContextInteractorImpl$addBiometricContextListener$1 = new LogContextInteractorImpl$addBiometricContextListener$1(this.this$0, this.$listener, continuation);
        logContextInteractorImpl$addBiometricContextListener$1.L$0 = obj;
        return logContextInteractorImpl$addBiometricContextListener$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LogContextInteractorImpl$addBiometricContextListener$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        FlowKt.launchIn(new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(this.this$0.foldState, new C10911(this.$listener, null)), new C10922(null)), coroutineScope);
        FlowKt.launchIn(new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(this.this$0.displayState), new C10933(this.$listener, null)), new C10944(null)), coroutineScope);
        this.$listener.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.systemui.biometrics.domain.interactor.LogContextInteractorImpl$addBiometricContextListener$1.5
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                CoroutineScopeKt.cancel$default(CoroutineScope.this);
            }
        }, 0);
        return Unit.INSTANCE;
    }
}
