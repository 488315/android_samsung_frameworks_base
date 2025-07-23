package androidx.compose.ui.platform;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ PlatformTextInputSessionScope $parentSession;
    final /* synthetic */ PlatformTextInputMethodRequest $request;
    int label;
    final /* synthetic */ ChainedPlatformTextInputInterceptor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ PlatformTextInputSessionScope $parentSession;
        final /* synthetic */ PlatformTextInputMethodRequest $request;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(PlatformTextInputMethodRequest platformTextInputMethodRequest, PlatformTextInputSessionScope platformTextInputSessionScope, Continuation continuation) {
            super(2, continuation);
            this.$request = platformTextInputMethodRequest;
            this.$parentSession = platformTextInputSessionScope;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$request, this.$parentSession, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((PlatformTextInputInterceptor) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PlatformTextInputInterceptor platformTextInputInterceptor = (PlatformTextInputInterceptor) this.L$0;
                PlatformTextInputMethodRequest platformTextInputMethodRequest = this.$request;
                PlatformTextInputSessionScope platformTextInputSessionScope = this.$parentSession;
                this.label = 1;
                if (((SelectedUserAwareInputConnectionKt$SelectedUserAwareInputConnection$1$1) platformTextInputInterceptor).interceptStartInputMethod(platformTextInputMethodRequest, platformTextInputSessionScope, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3(ChainedPlatformTextInputInterceptor chainedPlatformTextInputInterceptor, PlatformTextInputMethodRequest platformTextInputMethodRequest, PlatformTextInputSessionScope platformTextInputSessionScope, Continuation continuation) {
        super(2, continuation);
        this.this$0 = chainedPlatformTextInputInterceptor;
        this.$request = platformTextInputMethodRequest;
        this.$parentSession = platformTextInputSessionScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3(this.this$0, this.$request, this.$parentSession, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ChainedPlatformTextInputInterceptor chainedPlatformTextInputInterceptor = this.this$0;
            SafeFlow snapshotFlow = SnapshotStateKt.snapshotFlow(new Function0() { // from class: androidx.compose.ui.platform.ChainedPlatformTextInputInterceptor$textInputSession$2$scope$1$startInputMethod$3.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return (PlatformTextInputInterceptor) ((SnapshotMutableStateImpl) ChainedPlatformTextInputInterceptor.this.interceptor$delegate).getValue();
                }
            });
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$request, this.$parentSession, null);
            this.label = 1;
            if (FlowKt.collectLatest(snapshotFlow, anonymousClass2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new IllegalStateException("Interceptors flow should never terminate.");
    }
}
