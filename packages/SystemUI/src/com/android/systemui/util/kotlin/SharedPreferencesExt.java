package com.android.systemui.util.kotlin;

import android.content.SharedPreferences;
import com.android.systemui.util.kotlin.SharedPreferencesExt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
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
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class SharedPreferencesExt {
    public static final int $stable = 0;
    public static final SharedPreferencesExt INSTANCE = new SharedPreferencesExt();

    /* renamed from: com.android.systemui.util.kotlin.SharedPreferencesExt$observe$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ SharedPreferences $this_observe;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SharedPreferences sharedPreferences, Continuation continuation) {
            super(2, continuation);
            this.$this_observe = sharedPreferences;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$0(SharedPreferences sharedPreferences, SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_observe, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.android.systemui.util.kotlin.SharedPreferencesExt$observe$1$listener$1
                    @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
                    public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                        ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Unit.INSTANCE);
                    }
                };
                this.$this_observe.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
                final SharedPreferences sharedPreferences = this.$this_observe;
                Function0 function0 = new Function0() { // from class: com.android.systemui.util.kotlin.SharedPreferencesExt$observe$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return SharedPreferencesExt.AnonymousClass1.invokeSuspend$lambda$0(sharedPreferences, onSharedPreferenceChangeListener);
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

    private SharedPreferencesExt() {
    }

    public final Flow observe(SharedPreferences sharedPreferences) {
        return FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(sharedPreferences, null));
    }
}
