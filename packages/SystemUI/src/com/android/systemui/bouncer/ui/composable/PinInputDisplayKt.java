package com.android.systemui.bouncer.ui.composable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.res.AnimatedVectorResources_androidKt;
import androidx.compose.animation.graphics.vector.AnimatedImageVector;
import androidx.compose.foundation.ImageKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.res.ColorResources_androidKt;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticOutline0;
import com.android.compose.PlatformButtonsKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import com.android.systemui.bouncer.ui.viewmodel.PinInputViewModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class PinInputDisplayKt {
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void PinInputDisplay(final PinBouncerViewModel pinBouncerViewModel, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-478652016);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(pinBouncerViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.PinInputDisplay (PinInputDisplay.kt:92)");
            }
            FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.hintedPinLength, composerImpl);
            composerImpl.startReplaceGroup(1174110729);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.rememberShapeAnimations (PinInputDisplay.kt:576)");
            }
            composerImpl.startReplaceGroup(1684115947);
            AnimatedImageVector.Companion companion = AnimatedImageVector.Companion;
            AnimatedImageVector animatedImageVectorAnimatedVectorResource = AnimatedVectorResources_androidKt.animatedVectorResource(R.drawable.sec_pin_dot_avd, composerImpl, 6);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1684121359);
            List list = pinBouncerViewModel.pinShapes.shapes;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            ArrayList arrayList2 = (ArrayList) list;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                int iIntValue = ((Number) obj).intValue();
                AnimatedImageVector.Companion companion2 = AnimatedImageVector.Companion;
                arrayList.add(AnimatedVectorResources_androidKt.animatedVectorResource(iIntValue, composerImpl, 6));
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1684125067);
            float fDimensionResource = PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_pin_dot_size, composerImpl);
            composerImpl.end(false);
            composerImpl.startReplaceGroup(1684128811);
            boolean zChanged = composerImpl.changed(animatedImageVectorAnimatedVectorResource) | composerImpl.changed(arrayList) | composerImpl.changed(fDimensionResource);
            Object objRememberedValue = composerImpl.rememberedValue();
            if (!zChanged) {
                Composer.Companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new ShapeAnimations(fDimensionResource, animatedImageVectorAnimatedVectorResource, arrayList, null);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                ShapeAnimations shapeAnimations = (ShapeAnimations) objRememberedValue;
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                PinInputViewModel pinInputViewModel = (PinInputViewModel) FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.pinInput, composerImpl).getValue();
                composerImpl.startReplaceGroup(1514489082);
                if (!((ArrayList) pinInputViewModel.getPin()).isEmpty()) {
                    RegularPinInputDisplay(pinBouncerViewModel, shapeAnimations, modifier, composerImpl, (i2 & 14) | ((i2 << 3) & 896));
                }
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PinInputDisplayKt.PinInputDisplay(pinBouncerViewModel, modifier, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void RegularPinInputDisplay(final PinBouncerViewModel pinBouncerViewModel, final ShapeAnimations shapeAnimations, final Modifier modifier, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-13814644);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(pinBouncerViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(shapeAnimations) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changed(modifier) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.RegularPinInputDisplay (PinInputDisplay.kt:223)");
            }
            composerImpl.startReplaceGroup(432879369);
            if (pinBouncerViewModel.isSimAreaVisible) {
                SimArea(pinBouncerViewModel, composerImpl, i2 & 14);
            }
            composerImpl.end(false);
            composerImpl.startReplaceGroup(432893461);
            boolean zChanged = composerImpl.changed(shapeAnimations);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new PinInputRow(shapeAnimations);
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                PinInputRow pinInputRow = (PinInputRow) objRememberedValue;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(432900708);
                boolean zChangedInstance = composerImpl.changedInstance(pinBouncerViewModel) | composerImpl.changedInstance(pinInputRow);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new PinInputDisplayKt$RegularPinInputDisplay$1$1(pinBouncerViewModel, pinInputRow, null);
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    EffectsKt.LaunchedEffect(pinBouncerViewModel.pinInput, pinInputRow, (Function2) objRememberedValue2, composerImpl);
                    composerImpl.startReplaceGroup(432935105);
                    boolean zChangedInstance2 = composerImpl.changedInstance(pinInputRow);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance2) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new PinInputDisplayKt$RegularPinInputDisplay$2$1(pinInputRow, null);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl.end(false);
                        EffectsKt.LaunchedEffect(composerImpl, pinInputRow, (Function2) objRememberedValue3);
                        pinInputRow.Content(pinBouncerViewModel, modifier, composerImpl, ((i2 >> 3) & 112) | (i2 & 14));
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    ShapeAnimations shapeAnimations2 = shapeAnimations;
                    Modifier modifier2 = modifier;
                    PinInputDisplayKt.RegularPinInputDisplay(pinBouncerViewModel, shapeAnimations2, modifier2, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void SimArea(final PinBouncerViewModel pinBouncerViewModel, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-778541456);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(pinBouncerViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 3) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.bouncer.ui.composable.SimArea (PinInputDisplay.kt:274)");
            }
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.isLockedEsim, composerImpl);
            final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.isSimUnlockingDialogVisible, composerImpl);
            final MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.errorDialogMessage, composerImpl);
            composerImpl.startReplaceGroup(178852699);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 178854811);
            if (objM == composer$Companion$Empty$1) {
                objM = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(objM);
            }
            final MutableState mutableState2 = (MutableState) objM;
            composerImpl.end(false);
            final Context context = ((View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView)).getContext();
            Boolean bool = (Boolean) mutableStateCollectAsStateWithLifecycle2.getValue();
            boolean zM = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl, 178859669, mutableStateCollectAsStateWithLifecycle2) | composerImpl.changedInstance(context);
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (zM || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new Function1() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        Context context2 = context;
                        boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle2.getValue()).booleanValue();
                        final MutableState mutableState3 = mutableState;
                        if (zBooleanValue) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context2);
                            builder.setMessage(context2.getString(R.string.kg_sim_unlock_progress_dialog_message));
                            builder.setCancelable(false);
                            AlertDialog alertDialogCreate = builder.create();
                            Window window = alertDialogCreate.getWindow();
                            if (window != null) {
                                window.setType(2009);
                            }
                            alertDialogCreate.show();
                            TextView textView = (TextView) alertDialogCreate.findViewById(android.R.id.message);
                            if (textView != null) {
                                textView.setGravity(17);
                            }
                            mutableState3.setValue(alertDialogCreate);
                        } else {
                            Dialog dialog = (Dialog) mutableState3.getValue();
                            if (dialog != null) {
                                dialog.hide();
                            }
                            mutableState3.setValue(null);
                        }
                        return new DisposableEffectResult() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$SimArea$lambda$39$lambda$38$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                MutableState mutableState4 = mutableState3;
                                Dialog dialog2 = (Dialog) mutableState4.getValue();
                                if (dialog2 != null) {
                                    dialog2.hide();
                                }
                                mutableState4.setValue(null);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.DisposableEffect(bool, (Function1) objRememberedValue2, composerImpl);
            String str = (String) mutableStateCollectAsStateWithLifecycle3.getValue();
            composerImpl.startReplaceGroup(178885411);
            boolean zChanged = composerImpl.changed(mutableStateCollectAsStateWithLifecycle3) | composerImpl.changedInstance(context) | composerImpl.changedInstance(pinBouncerViewModel);
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (zChanged || objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new Function1() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        Context context2 = context;
                        MutableState mutableState3 = mutableStateCollectAsStateWithLifecycle3;
                        String str2 = (String) mutableState3.getValue();
                        final MutableState mutableState4 = mutableState2;
                        if (str2 != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context2);
                            builder.setMessage((String) mutableState3.getValue());
                            builder.setCancelable(false);
                            builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
                            AlertDialog alertDialogCreate = builder.create();
                            Window window = alertDialogCreate.getWindow();
                            if (window != null) {
                                window.setType(2009);
                            }
                            final PinBouncerViewModel pinBouncerViewModel2 = pinBouncerViewModel;
                            alertDialogCreate.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$SimArea$2$1$1$1
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    pinBouncerViewModel2.requests.mo3475trySendJP2dKIU(PinBouncerViewModel.OnErrorDialogDismissed.INSTANCE);
                                }
                            });
                            alertDialogCreate.show();
                            mutableState4.setValue(alertDialogCreate);
                        } else {
                            Dialog dialog = (Dialog) mutableState4.getValue();
                            if (dialog != null) {
                                dialog.hide();
                            }
                            mutableState4.setValue(null);
                        }
                        return new DisposableEffectResult() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$SimArea$lambda$43$lambda$42$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                MutableState mutableState5 = mutableState4;
                                Dialog dialog2 = (Dialog) mutableState5.getValue();
                                if (dialog2 != null) {
                                    dialog2.hide();
                                }
                                mutableState5.setValue(null);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            composerImpl.end(false);
            EffectsKt.DisposableEffect(str, (Function1) objRememberedValue3, composerImpl);
            Dp.Companion companion = Dp.Companion;
            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(Modifier.Companion, 0.0f, 0.0f, 0.0f, 20, 7);
            Alignment.Companion.getClass();
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierM129paddingqDBjuR0$default);
            ComposeUiNode.Companion.getClass();
            Function0 function0 = ComposeUiNode.Companion.Constructor;
            if (composerImpl.applier == null) {
                ComposablesKt.invalidApplier();
                throw null;
            }
            composerImpl.startReusableNode();
            if (composerImpl.inserting) {
                composerImpl.createNode(function0);
            } else {
                composerImpl.useNode();
            }
            Updater.m337setimpl(composerImpl, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m337setimpl(composerImpl, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m337setimpl(composerImpl, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            if (Intrinsics.areEqual((Boolean) mutableStateCollectAsStateWithLifecycle.getValue(), Boolean.TRUE)) {
                composerImpl.startReplaceGroup(73016047);
                composerImpl.startReplaceGroup(-1383117277);
                boolean zChangedInstance = composerImpl.changedInstance(pinBouncerViewModel);
                Object objRememberedValue4 = composerImpl.rememberedValue();
                if (zChangedInstance || objRememberedValue4 == composer$Companion$Empty$1) {
                    objRememberedValue4 = new PinInputDisplayKt$$ExternalSyntheticLambda4(pinBouncerViewModel, 0);
                    composerImpl.updateRememberedValue(objRememberedValue4);
                }
                composerImpl.end(false);
                ComposableSingletons$PinInputDisplayKt.INSTANCE.getClass();
                PlatformButtonsKt.PlatformOutlinedButton((Function0) objRememberedValue4, null, false, null, null, ComposableSingletons$PinInputDisplayKt.f27lambda1, composerImpl, 196608);
                composerImpl.end(false);
            } else if (Intrinsics.areEqual((Boolean) mutableStateCollectAsStateWithLifecycle.getValue(), Boolean.FALSE)) {
                composerImpl.startReplaceGroup(73881474);
                ImageKt.Image(PainterResources_androidKt.painterResource(R.drawable.ic_lockscreen_sim, composerImpl, 0), null, null, null, null, 0.0f, ColorFilter.Companion.m465tintxETnrds$default(ColorFilter.Companion, ColorResources_androidKt.colorResource(R.color.background_protected, composerImpl)), composerImpl, 48, 60);
                composerImpl = composerImpl;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(74134217);
                composerImpl.end(false);
            }
            composerImpl.end(true);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PinInputDisplayKt.SimArea(pinBouncerViewModel, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
