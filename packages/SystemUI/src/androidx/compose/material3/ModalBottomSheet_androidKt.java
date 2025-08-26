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
            ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
            final MutableState mutableStateRememberUpdatedState = SnapshotStateKt.rememberUpdatedState(function2, composerImpl);
            UUID uuid = (UUID) RememberSaveableKt.rememberSaveable(new Object[0], null, null, new Function0() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$dialogId$1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return UUID.randomUUID();
                }
            }, composerImpl, 3072, 6);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                CompositionScopedCoroutineScopeCanceller compositionScopedCoroutineScopeCanceller = new CompositionScopedCoroutineScopeCanceller(EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl));
                composerImpl.updateRememberedValue(compositionScopedCoroutineScopeCanceller);
                objRememberedValue = compositionScopedCoroutineScopeCanceller;
            }
            CoroutineScope coroutineScope = ((CompositionScopedCoroutineScopeCanceller) objRememberedValue).coroutineScope;
            boolean zChanged = composerImpl.changed(view) | composerImpl.changed(density);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue2 == composer$Companion$Empty$1) {
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

                    /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3) {
                        Composer composer2 = (Composer) obj2;
                        if ((((Number) obj3).intValue() & 3) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                            } else {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.ModalBottomSheetDialog.<anonymous>.<anonymous>.<anonymous> (ModalBottomSheet.android.kt:311)");
                                }
                                Modifier modifierSemantics = SemanticsModifierKt.semantics(Modifier.Companion, false, new Function1() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$dialog$1$1$1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj4) {
                                        KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                                        SemanticsProperties.INSTANCE.getClass();
                                        SemanticsPropertyKey semanticsPropertyKey = SemanticsProperties.IsDialog;
                                        Unit unit = Unit.INSTANCE;
                                        ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj4)).set(semanticsPropertyKey, unit);
                                        return unit;
                                    }
                                });
                                State<Function2> state = mutableStateRememberUpdatedState;
                                Alignment.Companion.getClass();
                                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierSemantics);
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
                                Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                Function2 function22 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                    AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function22);
                                }
                                Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                ((Function2) state.getValue()).invoke(composer2, 0);
                                composerImpl3.end(true);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                            }
                        }
                        return Unit.INSTANCE;
                    }
                });
                ModalBottomSheetDialogLayout modalBottomSheetDialogLayout = modalBottomSheetDialogWrapper.dialogLayout;
                modalBottomSheetDialogLayout.setParentCompositionContext(compositionContextImplRememberCompositionContext);
                ((SnapshotMutableStateImpl) modalBottomSheetDialogLayout.content$delegate).setValue(composableLambdaImpl);
                modalBottomSheetDialogLayout.shouldCreateCompositionOnAttachedToWindow = true;
                modalBottomSheetDialogLayout.createComposition();
                composerImpl.updateRememberedValue(modalBottomSheetDialogWrapper);
                obj = modalBottomSheetDialogWrapper;
            } else {
                i4 = i2;
                obj = objRememberedValue2;
                i3 = 32;
                layoutDirection = layoutDirection2;
                i5 = 4;
            }
            final ModalBottomSheetDialogWrapper modalBottomSheetDialogWrapper2 = (ModalBottomSheetDialogWrapper) obj;
            boolean zChangedInstance = composerImpl.changedInstance(modalBottomSheetDialogWrapper2);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        modalBottomSheetDialogWrapper2.show();
                        final ModalBottomSheetDialogWrapper modalBottomSheetDialogWrapper3 = modalBottomSheetDialogWrapper2;
                        return new DisposableEffectResult() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                ModalBottomSheetDialogWrapper modalBottomSheetDialogWrapper4 = modalBottomSheetDialogWrapper3;
                                modalBottomSheetDialogWrapper4.dismiss();
                                modalBottomSheetDialogWrapper4.dialogLayout.disposeComposition();
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            EffectsKt.DisposableEffect(modalBottomSheetDialogWrapper2, (Function1) objRememberedValue3, composerImpl);
            boolean zChangedInstance2 = composerImpl.changedInstance(modalBottomSheetDialogWrapper2) | ((i4 & 14) == i5) | ((i4 & 112) == i3) | composerImpl.changed(layoutDirection);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            if (zChangedInstance2 || objRememberedValue4 == composer$Companion$Empty$1) {
                objRememberedValue4 = new Function0() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt$ModalBottomSheetDialog$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        modalBottomSheetDialogWrapper2.updateParameters(function0, modalBottomSheetProperties, layoutDirection);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue4);
            }
            EffectsKt.SideEffect((Function0) objRememberedValue4, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.ModalBottomSheet_androidKt.ModalBottomSheetDialog.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ModalBottomSheet_androidKt.ModalBottomSheetDialog(function0, modalBottomSheetProperties, animatable, function2, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
