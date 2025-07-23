package androidx.compose.material3;

import android.view.View;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Animatable;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import java.util.UUID;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KProperty;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ModalBottomSheet_androidKt {
    public static final void ModalBottomSheetDialog(final Function0 function0, final ModalBottomSheetProperties modalBottomSheetProperties, final Animatable animatable, final Function2 function2, Composer composer, final int i) {
        int i2;
        int i3;
        int i4;
        final LayoutDirection layoutDirection;
        int i5;
        Object obj;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1254951810);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modalBottomSheetProperties) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= (i & 512) == 0 ? composerImpl.changed(animatable) : composerImpl.changedInstance(animatable) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheetDialog (ModalBottomSheet.android.kt:289)");
            }
            View view = (View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView);
            Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
            LayoutDirection layoutDirection2 = (LayoutDirection) composerImpl.consume(CompositionLocalsKt.LocalLayoutDirection);
            ComposerImpl.CompositionContextImpl rememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
            final MutableState rememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function2, composerImpl);
            UUID uuid = (UUID) RememberSaveableKt.rememberSaveable(new Object[0], null, null, new Function0() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$dialogId$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return UUID.randomUUID();
                }
            }, composerImpl, 3072, 6);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl));
                composerImpl.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                rememberedValue = compositionScopedCoroutineScopeCanceller;
            }
            CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
            boolean changed = composerImpl.changed(view) | composerImpl.changed(density);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                i3 = 32;
                i4 = i2;
                layoutDirection = layoutDirection2;
                i5 = 4;
                ModalBottomSheetDialogWrapper modalBottomSheetDialogWrapper = new ModalBottomSheetDialogWrapper(function0, modalBottomSheetProperties, view, layoutDirection, density, uuid, animatable, coroutineScope);
                ComposableLambdaImpl composableLambdaImpl = new ComposableLambdaImpl(-1560960657, true, new Function2() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$dialog$1$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        if ((((Number) obj3).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheetDialog.<anonymous>.<anonymous>.<anonymous> (ModalBottomSheet.android.kt:311)");
                        }
                        Modifier semantics = SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$dialog$1$1$1.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo779invoke(Object obj4) {
                                KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                SemanticsProperties.INSTANCE.getClass();
                                SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.IsDialog;
                                Unit unit = Unit.INSTANCE;
                                ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj4)).set(semanticsPropertyKey, unit);
                                return unit;
                            }
                        });
                        State<Function2> state = rememberUpdatedState;
                        Alignment.Companion.getClass();
                        MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, semantics);
                        ComposeUiNode.Companion.getClass();
                        Function0 function02 = ComposeUiNode.Companion.Constructor;
                        if (composerImpl3.applier == null) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function02);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m336setimpl(composer2, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m336setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function22);
                        }
                        Updater.m336setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        ((Function2) state.getValue()).invoke(composer2, 0);
                        composerImpl3.end(true);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        return Unit.INSTANCE;
                    }
                });
                ModalBottomSheetDialogLayout modalBottomSheetDialogLayout = modalBottomSheetDialogWrapper.dialogLayout;
                modalBottomSheetDialogLayout.setParentCompositionContext(rememberCompositionContext);
                ((SnapshotMutableStateImpl) modalBottomSheetDialogLayout.content$delegate).setValue(composableLambdaImpl);
                modalBottomSheetDialogLayout.shouldCreateCompositionOnAttachedToWindow = true;
                modalBottomSheetDialogLayout.createComposition();
                composerImpl.updateRememberedValue(modalBottomSheetDialogWrapper);
                obj = modalBottomSheetDialogWrapper;
            } else {
                i4 = i2;
                obj = rememberedValue2;
                i3 = 32;
                layoutDirection = layoutDirection2;
                i5 = 4;
            }
            final ModalBottomSheetDialogWrapper modalBottomSheetDialogWrapper2 = (ModalBottomSheetDialogWrapper) obj;
            boolean changedInstance = composerImpl.changedInstance(modalBottomSheetDialogWrapper2);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changedInstance || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj2) {
                        ModalBottomSheetDialogWrapper.this.show();
                        final ModalBottomSheetDialogWrapper modalBottomSheetDialogWrapper3 = ModalBottomSheetDialogWrapper.this;
                        return new DisposableEffectResult() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                ModalBottomSheetDialogWrapper modalBottomSheetDialogWrapper4 = ModalBottomSheetDialogWrapper.this;
                                modalBottomSheetDialogWrapper4.dismiss();
                                modalBottomSheetDialogWrapper4.dialogLayout.disposeComposition();
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            EffectsKt.DisposableEffect(modalBottomSheetDialogWrapper2, (Function1) rememberedValue3, composerImpl);
            boolean changedInstance2 = composerImpl.changedInstance(modalBottomSheetDialogWrapper2) | ((i4 & 14) == i5) | ((i4 & 112) == i3) | composerImpl.changed(layoutDirection);
            Object rememberedValue4 = composerImpl.rememberedValue();
            if (changedInstance2 || rememberedValue4 == composer$Companion$Empty$1) {
                rememberedValue4 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ModalBottomSheetDialogWrapper.this.updateParameters(function0, modalBottomSheetProperties, layoutDirection);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue4);
            }
            EffectsKt.SideEffect((Function0) rememberedValue4, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ModalBottomSheet_androidKt.ModalBottomSheetDialog(Function0.this, modalBottomSheetProperties, animatable, function2, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
