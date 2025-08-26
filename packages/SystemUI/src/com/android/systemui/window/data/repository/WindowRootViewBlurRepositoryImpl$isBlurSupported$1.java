package com.android.systemui.window.data.repository;

import android.app.ActivityManager;
import android.os.SystemProperties;
import android.view.CrossWindowBlurListeners;
import com.android.systemui.common.coroutine.ChannelExt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class WindowRootViewBlurRepositoryImpl$isBlurSupported$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ CrossWindowBlurListeners $crossWindowBlurListeners;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WindowRootViewBlurRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WindowRootViewBlurRepositoryImpl$isBlurSupported$1(CrossWindowBlurListeners crossWindowBlurListeners, WindowRootViewBlurRepositoryImpl windowRootViewBlurRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$crossWindowBlurListeners = crossWindowBlurListeners;
        this.this$0 = windowRootViewBlurRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WindowRootViewBlurRepositoryImpl$isBlurSupported$1 windowRootViewBlurRepositoryImpl$isBlurSupported$1 = new WindowRootViewBlurRepositoryImpl$isBlurSupported$1(this.$crossWindowBlurListeners, this.this$0, continuation);
        windowRootViewBlurRepositoryImpl$isBlurSupported$1.L$0 = obj;
        return windowRootViewBlurRepositoryImpl$isBlurSupported$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WindowRootViewBlurRepositoryImpl$isBlurSupported$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.window.data.repository.WindowRootViewBlurRepositoryImpl$isBlurSupported$1$$ExternalSyntheticLambda0, kotlin.jvm.functions.Function1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final WindowRootViewBlurRepositoryImpl windowRootViewBlurRepositoryImpl = this.this$0;
            final ?? r3 = new Function1() { // from class: com.android.systemui.window.data.repository.WindowRootViewBlurRepositoryImpl$isBlurSupported$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    int i2 = WindowRootViewBlurRepositoryImpl.$r8$clinit;
                    windowRootViewBlurRepositoryImpl.getClass();
                    boolean z = false;
                    if (ActivityManager.isHighEndGfx()) {
                        WindowRootViewBlurRepository.Companion.getClass();
                        if (!SystemProperties.getBoolean("persist.sysui.disableBlur", false) && zBooleanValue) {
                            z = true;
                        }
                    }
                    Boolean boolValueOf = Boolean.valueOf(z);
                    channelExt.getClass();
                    ChannelExt.trySendWithFailureLogging(producerScope, boolValueOf, "WindowRootViewBlurRepository", "unable to send blur enabled/disable state change");
                    return Unit.INSTANCE;
                }
            };
            this.$crossWindowBlurListeners.addListener(windowRootViewBlurRepositoryImpl.executor, new WindowRootViewBlurRepositoryKt$sam$java_util_function_Consumer$0(r3));
            r3.mo781invoke(Boolean.valueOf(this.$crossWindowBlurListeners.isCrossWindowBlurEnabled()));
            final CrossWindowBlurListeners crossWindowBlurListeners = this.$crossWindowBlurListeners;
            Function0 function0 = new Function0() { // from class: com.android.systemui.window.data.repository.WindowRootViewBlurRepositoryImpl$isBlurSupported$1$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    crossWindowBlurListeners.removeListener(new WindowRootViewBlurRepositoryKt$sam$java_util_function_Consumer$0(r3));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
