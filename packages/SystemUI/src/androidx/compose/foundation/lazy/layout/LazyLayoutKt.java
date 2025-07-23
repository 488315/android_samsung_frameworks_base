package androidx.compose.foundation.lazy.layout;

import android.view.View;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.SaveableStateHolder;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.SubcomposeLayoutKt;
import androidx.compose.ui.layout.SubcomposeLayoutState;
import androidx.compose.ui.layout.SubcomposeMeasureScope;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.unit.Constraints;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyLayoutKt {
    public static final void LazyLayout(final Function0 function0, final Modifier modifier, final LazyLayoutPrefetchState lazyLayoutPrefetchState, final Function2 function2, Composer composer, final int i, final int i2) {
        int i3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(2002163445);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 != 0) {
            i3 |= 48;
        } else if ((i & 48) == 0) {
            i3 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        int i5 = i2 & 4;
        if (i5 != 0) {
            i3 |= 384;
        } else if ((i & 384) == 0) {
            i3 |= composerImpl.changed(lazyLayoutPrefetchState) ? 256 : 128;
        }
        if ((i2 & 8) != 0) {
            i3 |= 3072;
        } else if ((i & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function2) ? 2048 : 1024;
        }
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 1171) != 1170)) {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (i5 != 0) {
                lazyLayoutPrefetchState = null;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.LazyLayout (LazyLayout.kt:52)");
            }
            final MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function0, composerImpl);
            LazySaveableStateHolderKt.LazySaveableStateHolderProvider(ComposableLambdaKt.rememberComposableLambda(-1488997347, new Function3() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    Modifier modifier2;
                    SaveableStateHolder saveableStateHolder = (SaveableStateHolder) obj;
                    Composer composer2 = (Composer) obj2;
                    ((Number) obj3).intValue();
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.LazyLayout.<anonymous> (LazyLayout.kt:56)");
                    }
                    final State<Function0> state = rememberUpdatedState;
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    Object rememberedValue = composerImpl2.rememberedValue();
                    Composer.Companion.getClass();
                    Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
                    if (rememberedValue == composer$Companion$Empty$1) {
                        rememberedValue = new LazyLayoutItemContentFactory(saveableStateHolder, new Function0() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$1$itemContentFactory$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final Object invoke() {
                                return (LazyLayoutItemProvider) ((Function0) state.getValue()).invoke();
                            }
                        });
                        composerImpl2.updateRememberedValue(rememberedValue);
                    }
                    final LazyLayoutItemContentFactory lazyLayoutItemContentFactory = (LazyLayoutItemContentFactory) rememberedValue;
                    Object rememberedValue2 = composerImpl2.rememberedValue();
                    if (rememberedValue2 == composer$Companion$Empty$1) {
                        rememberedValue2 = new SubcomposeLayoutState(new LazyLayoutItemReusePolicy(lazyLayoutItemContentFactory));
                        composerImpl2.updateRememberedValue(rememberedValue2);
                    }
                    final SubcomposeLayoutState subcomposeLayoutState = (SubcomposeLayoutState) rememberedValue2;
                    if (LazyLayoutPrefetchState.this != null) {
                        composerImpl2.startReplaceGroup(204281539);
                        final PrefetchScheduler prefetchScheduler = LazyLayoutPrefetchState.this.prefetchScheduler;
                        if (prefetchScheduler == null) {
                            composerImpl2.startReplaceGroup(6591363);
                            PrefetchScheduler_androidKt$RobolectricImpl$1 prefetchScheduler_androidKt$RobolectricImpl$1 = PrefetchScheduler_androidKt.RobolectricImpl;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.rememberDefaultPrefetchScheduler (PrefetchScheduler.android.kt:33)");
                            }
                            prefetchScheduler = PrefetchScheduler_androidKt.RobolectricImpl;
                            if (prefetchScheduler != null) {
                                composerImpl2.startReplaceGroup(1213929743);
                                composerImpl2.end(false);
                            } else {
                                composerImpl2.startReplaceGroup(1213968648);
                                View view = (View) composerImpl2.consume(AndroidCompositionLocals_androidKt.LocalView);
                                boolean changed = composerImpl2.changed(view);
                                Object rememberedValue3 = composerImpl2.rememberedValue();
                                if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                                    rememberedValue3 = new AndroidPrefetchScheduler(view);
                                    composerImpl2.updateRememberedValue(rememberedValue3);
                                }
                                composerImpl2.end(false);
                                prefetchScheduler = (AndroidPrefetchScheduler) rememberedValue3;
                            }
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        } else {
                            composerImpl2.startReplaceGroup(6590278);
                        }
                        composerImpl2.end(false);
                        LazyLayoutPrefetchState lazyLayoutPrefetchState2 = LazyLayoutPrefetchState.this;
                        Object[] objArr = {lazyLayoutPrefetchState2, lazyLayoutItemContentFactory, subcomposeLayoutState, prefetchScheduler};
                        boolean changed2 = composerImpl2.changed(lazyLayoutPrefetchState2) | composerImpl2.changedInstance(lazyLayoutItemContentFactory) | composerImpl2.changedInstance(subcomposeLayoutState) | composerImpl2.changedInstance(prefetchScheduler);
                        final LazyLayoutPrefetchState lazyLayoutPrefetchState3 = LazyLayoutPrefetchState.this;
                        Object rememberedValue4 = composerImpl2.rememberedValue();
                        if (changed2 || rememberedValue4 == composer$Companion$Empty$1) {
                            rememberedValue4 = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo779invoke(Object obj4) {
                                    LazyLayoutPrefetchState.this.prefetchHandleProvider = new PrefetchHandleProvider(lazyLayoutItemContentFactory, subcomposeLayoutState, prefetchScheduler);
                                    final LazyLayoutPrefetchState lazyLayoutPrefetchState4 = LazyLayoutPrefetchState.this;
                                    return new DisposableEffectResult() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$1$1$1$invoke$$inlined$onDispose$1
                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                        public final void dispose() {
                                            LazyLayoutPrefetchState.this.prefetchHandleProvider = null;
                                        }
                                    };
                                }
                            };
                            composerImpl2.updateRememberedValue(rememberedValue4);
                        }
                        EffectsKt.DisposableEffect(objArr, (Function1) rememberedValue4, (Composer) composerImpl2);
                        composerImpl2.end(false);
                    } else {
                        composerImpl2.startReplaceGroup(204710145);
                        composerImpl2.end(false);
                    }
                    Modifier modifier3 = modifier;
                    LazyLayoutPrefetchState lazyLayoutPrefetchState4 = LazyLayoutPrefetchState.this;
                    int i6 = LazyLayoutPrefetchStateKt.$r8$clinit;
                    if (lazyLayoutPrefetchState4 == null || (modifier2 = modifier3.then(new TraversablePrefetchStateModifierElement(lazyLayoutPrefetchState4))) == null) {
                        modifier2 = modifier3;
                    }
                    boolean changed3 = composerImpl2.changed(lazyLayoutItemContentFactory) | composerImpl2.changed(function2);
                    final Function2 function22 = function2;
                    Object rememberedValue5 = composerImpl2.rememberedValue();
                    if (changed3 || rememberedValue5 == composer$Companion$Empty$1) {
                        rememberedValue5 = new Function2() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$1$2$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj4, Object obj5) {
                                long j = ((Constraints) obj5).value;
                                return (MeasureResult) function22.invoke(new LazyLayoutMeasureScopeImpl(LazyLayoutItemContentFactory.this, (SubcomposeMeasureScope) obj4), Constraints.m813boximpl(j));
                            }
                        };
                        composerImpl2.updateRememberedValue(rememberedValue5);
                    }
                    SubcomposeLayoutKt.SubcomposeLayout(subcomposeLayoutState, modifier2, (Function2) rememberedValue5, composerImpl2, 8, 0);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        final Modifier modifier2 = modifier;
        final LazyLayoutPrefetchState lazyLayoutPrefetchState2 = lazyLayoutPrefetchState;
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutKt$LazyLayout$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LazyLayoutKt.LazyLayout(Function0.this, modifier2, lazyLayoutPrefetchState2, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
