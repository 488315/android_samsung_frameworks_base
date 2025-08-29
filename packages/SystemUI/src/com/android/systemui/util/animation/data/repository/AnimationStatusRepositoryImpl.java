package com.android.systemui.util.animation.data.repository;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class AnimationStatusRepositoryImpl implements AnimationStatusRepository {
    public static final int $stable = 8;
    private final CoroutineDispatcher backgroundDispatcher;
    private final Handler backgroundHandler;
    private final ContentResolver resolver;

    /* renamed from: com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl$areAnimationsEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(AnimationStatusRepositoryImpl animationStatusRepositoryImpl, AnimationStatusRepositoryImpl$areAnimationsEnabled$1$observer$1 animationStatusRepositoryImpl$areAnimationsEnabled$1$observer$1) {
            animationStatusRepositoryImpl.resolver.unregisterContentObserver(animationStatusRepositoryImpl$areAnimationsEnabled$1$observer$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = AnimationStatusRepositoryImpl.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v4, types: [android.database.ContentObserver, com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl$areAnimationsEnabled$1$observer$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                ScaleAwareTransitionProgressProvider.Companion companion = ScaleAwareTransitionProgressProvider.Companion;
                ContentResolver contentResolver = AnimationStatusRepositoryImpl.this.resolver;
                companion.getClass();
                ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver)));
                final Handler handler = AnimationStatusRepositoryImpl.this.backgroundHandler;
                final AnimationStatusRepositoryImpl animationStatusRepositoryImpl = AnimationStatusRepositoryImpl.this;
                final ?? r3 = new ContentObserver(handler) { // from class: com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl$areAnimationsEnabled$1$observer$1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        ScaleAwareTransitionProgressProvider.Companion companion2 = ScaleAwareTransitionProgressProvider.Companion;
                        ContentResolver contentResolver2 = animationStatusRepositoryImpl.resolver;
                        companion2.getClass();
                        boolean zAreAnimationsEnabled = ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver2);
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(Boolean.valueOf(zAreAnimationsEnabled));
                    }
                };
                AnimationStatusRepositoryImpl.this.resolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), false, r3);
                final AnimationStatusRepositoryImpl animationStatusRepositoryImpl2 = AnimationStatusRepositoryImpl.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.util.animation.data.repository.AnimationStatusRepositoryImpl$areAnimationsEnabled$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return AnimationStatusRepositoryImpl.AnonymousClass1.invokeSuspend$lambda$0(animationStatusRepositoryImpl2, r3);
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
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    public AnimationStatusRepositoryImpl(ContentResolver contentResolver, Handler handler, CoroutineDispatcher coroutineDispatcher) {
        this.resolver = contentResolver;
        this.backgroundHandler = handler;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.util.animation.data.repository.AnimationStatusRepository
    public Flow areAnimationsEnabled() {
        return FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(null)), this.backgroundDispatcher);
    }
}
