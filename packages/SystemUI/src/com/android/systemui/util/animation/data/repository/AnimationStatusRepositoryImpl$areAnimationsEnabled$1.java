package com.android.systemui.util.animation.data.repository;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.util.SettingsHelper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class AnimationStatusRepositoryImpl$areAnimationsEnabled$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AnimationStatusRepositoryImpl this$0;

    public AnimationStatusRepositoryImpl$areAnimationsEnabled$1(AnimationStatusRepositoryImpl animationStatusRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = animationStatusRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AnimationStatusRepositoryImpl$areAnimationsEnabled$1 animationStatusRepositoryImpl$areAnimationsEnabled$1 = new AnimationStatusRepositoryImpl$areAnimationsEnabled$1(this.this$0, continuation);
        animationStatusRepositoryImpl$areAnimationsEnabled$1.L$0 = obj;
        return animationStatusRepositoryImpl$areAnimationsEnabled$1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ContentResolver contentResolver;
        final Handler handler;
        ContentResolver contentResolver2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            ScaleAwareTransitionProgressProvider.Companion companion = ScaleAwareTransitionProgressProvider.Companion;
            contentResolver = this.this$0.resolver;
            companion.getClass();
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver)));
            handler = this.this$0.backgroundHandler;
            final AnimationStatusRepositoryImpl animationStatusRepositoryImpl = this.this$0;
            final ?? r3 = new ContentObserver(handler) { // from class: com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl$areAnimationsEnabled$1$observer$1
                @Override // android.database.ContentObserver
                public void onChange(boolean z) {
                    ContentResolver contentResolver3;
                    ScaleAwareTransitionProgressProvider.Companion companion2 = ScaleAwareTransitionProgressProvider.Companion;
                    contentResolver3 = AnimationStatusRepositoryImpl.this.resolver;
                    companion2.getClass();
                    boolean areAnimationsEnabled = ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver3);
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(areAnimationsEnabled));
                }
            };
            contentResolver2 = this.this$0.resolver;
            contentResolver2.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), false, r3);
            final AnimationStatusRepositoryImpl animationStatusRepositoryImpl2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl$areAnimationsEnabled$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m2298invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: collision with other method in class */
                public final void m2298invoke() {
                    ContentResolver contentResolver3;
                    contentResolver3 = AnimationStatusRepositoryImpl.this.resolver;
                    contentResolver3.unregisterContentObserver(r3);
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((AnimationStatusRepositoryImpl$areAnimationsEnabled$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
