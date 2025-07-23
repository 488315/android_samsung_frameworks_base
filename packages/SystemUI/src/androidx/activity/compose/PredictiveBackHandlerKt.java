package androidx.activity.compose;

import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.lifecycle.LifecycleOwner;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PredictiveBackHandlerKt {
    public static final void PredictiveBackHandler(final boolean z, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-642000585);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(z) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i2 & 2) != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                z = true;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.activity.compose.PredictiveBackHandler (PredictiveBackHandler.kt:76)");
            }
            MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function2, composerImpl);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl));
                composerImpl.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                rememberedValue = compositionScopedCoroutineScopeCanceller;
            }
            CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new PredictiveBackHandlerCallback(z, coroutineScope, (Function2) rememberUpdatedState.getValue());
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            final PredictiveBackHandlerCallback predictiveBackHandlerCallback = (PredictiveBackHandlerCallback) rememberedValue2;
            boolean changed = composerImpl.changed((Function2) rememberUpdatedState.getValue()) | composerImpl.changed(coroutineScope);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                predictiveBackHandlerCallback.currentOnBack = (Function2) rememberUpdatedState.getValue();
                predictiveBackHandlerCallback.onBackScope = coroutineScope;
                composerImpl.updateRememberedValue(Unit.INSTANCE);
            }
            Boolean valueOf = Boolean.valueOf(z);
            boolean changedInstance = composerImpl.changedInstance(predictiveBackHandlerCallback) | ((i3 & 14) == 4);
            Object rememberedValue4 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue4 == composer$Companion$Empty$1) {
                rememberedValue4 = new PredictiveBackHandlerKt$PredictiveBackHandler$2$1(predictiveBackHandlerCallback, z, null);
                composerImpl.updateRememberedValue(rememberedValue4);
            }
            EffectsKt.LaunchedEffect(composerImpl, valueOf, (Function2) rememberedValue4);
            LocalOnBackPressedDispatcherOwner.INSTANCE.getClass();
            OnBackPressedDispatcherOwner current = LocalOnBackPressedDispatcherOwner.getCurrent(composerImpl);
            if (current == null) {
                throw new IllegalStateException("No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner");
            }
            final OnBackPressedDispatcher onBackPressedDispatcher = current.getOnBackPressedDispatcher();
            final LifecycleOwner lifecycleOwner = (LifecycleOwner) composerImpl.consume(AndroidCompositionLocals_androidKt.getLocalLifecycleOwner());
            boolean changedInstance2 = composerImpl.changedInstance(onBackPressedDispatcher) | composerImpl.changedInstance(lifecycleOwner) | composerImpl.changedInstance(predictiveBackHandlerCallback);
            Object rememberedValue5 = composerImpl.rememberedValue();
            if (changedInstance2 || rememberedValue5 == composer$Companion$Empty$1) {
                rememberedValue5 = new Function1() { // from class: androidx.activity.compose.PredictiveBackHandlerKt$PredictiveBackHandler$3$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        OnBackPressedDispatcher.this.addCallback(lifecycleOwner, predictiveBackHandlerCallback);
                        final PredictiveBackHandlerCallback predictiveBackHandlerCallback2 = predictiveBackHandlerCallback;
                        return new DisposableEffectResult() { // from class: androidx.activity.compose.PredictiveBackHandlerKt$PredictiveBackHandler$3$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                PredictiveBackHandlerCallback.this.remove();
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue5);
            }
            EffectsKt.DisposableEffect(lifecycleOwner, onBackPressedDispatcher, (Function1) rememberedValue5, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.activity.compose.PredictiveBackHandlerKt$PredictiveBackHandler$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    PredictiveBackHandlerKt.PredictiveBackHandler(z, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
