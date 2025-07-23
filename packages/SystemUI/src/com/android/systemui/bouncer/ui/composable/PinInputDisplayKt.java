package com.android.systemui.bouncer.ui.composable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
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
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.composable.SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0;
import com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticOutline0;
import com.android.compose.PlatformButtonsKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PinInputDisplayKt {
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00d7, code lost:
    
        if (r6 == androidx.compose.runtime.Composer.Companion.Empty) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void PinInputDisplay(final com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r10, final androidx.compose.ui.Modifier r11, androidx.compose.runtime.Composer r12, final int r13) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt.PinInputDisplay(com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x007f, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a9, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00d2, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void RegularPinInputDisplay(final com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel r7, final com.android.systemui.bouncer.ui.composable.ShapeAnimations r8, final androidx.compose.ui.Modifier r9, androidx.compose.runtime.Composer r10, final int r11) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt.RegularPinInputDisplay(com.android.systemui.bouncer.ui.viewmodel.PinBouncerViewModel, com.android.systemui.bouncer.ui.composable.ShapeAnimations, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int):void");
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
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.isLockedEsim, composerImpl);
            final MutableState collectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.isSimUnlockingDialogVisible, composerImpl);
            final MutableState collectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(pinBouncerViewModel.errorDialogMessage, composerImpl);
            composerImpl.startReplaceGroup(178852699);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            Object m = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl, false, 178854811);
            if (m == composer$Companion$Empty$1) {
                m = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl.updateRememberedValue(m);
            }
            final MutableState mutableState2 = (MutableState) m;
            composerImpl.end(false);
            final Context context = ((View) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalView)).getContext();
            Boolean bool = (Boolean) collectAsStateWithLifecycle2.getValue();
            boolean m2 = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl, 178859669, collectAsStateWithLifecycle2) | composerImpl.changedInstance(context);
            Object rememberedValue2 = composerImpl.rememberedValue();
            if (m2 || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 = new Function1() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        Context context2 = context;
                        boolean booleanValue = ((Boolean) collectAsStateWithLifecycle2.getValue()).booleanValue();
                        final MutableState mutableState3 = mutableState;
                        if (booleanValue) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context2);
                            builder.setMessage(context2.getString(R.string.kg_sim_unlock_progress_dialog_message));
                            builder.setCancelable(false);
                            AlertDialog create = builder.create();
                            Window window = create.getWindow();
                            if (window != null) {
                                window.setType(2009);
                            }
                            create.show();
                            TextView textView = (TextView) create.findViewById(android.R.id.message);
                            if (textView != null) {
                                textView.setGravity(17);
                            }
                            mutableState3.setValue(create);
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
                                MutableState mutableState4 = MutableState.this;
                                Dialog dialog2 = (Dialog) mutableState4.getValue();
                                if (dialog2 != null) {
                                    dialog2.hide();
                                }
                                mutableState4.setValue(null);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue2);
            }
            composerImpl.end(false);
            EffectsKt.DisposableEffect(bool, (Function1) rememberedValue2, composerImpl);
            String str = (String) collectAsStateWithLifecycle3.getValue();
            composerImpl.startReplaceGroup(178885411);
            boolean changed = composerImpl.changed(collectAsStateWithLifecycle3) | composerImpl.changedInstance(context) | composerImpl.changedInstance(pinBouncerViewModel);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (changed || rememberedValue3 == composer$Companion$Empty$1) {
                rememberedValue3 = new Function1() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        Context context2 = context;
                        MutableState mutableState3 = collectAsStateWithLifecycle3;
                        String str2 = (String) mutableState3.getValue();
                        final MutableState mutableState4 = mutableState2;
                        if (str2 != null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context2);
                            builder.setMessage((String) mutableState3.getValue());
                            builder.setCancelable(false);
                            builder.setNeutralButton(R.string.ok, (DialogInterface.OnClickListener) null);
                            AlertDialog create = builder.create();
                            Window window = create.getWindow();
                            if (window != null) {
                                window.setType(2009);
                            }
                            final PinBouncerViewModel pinBouncerViewModel2 = pinBouncerViewModel;
                            create.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$SimArea$2$1$1$1
                                @Override // android.content.DialogInterface.OnDismissListener
                                public final void onDismiss(DialogInterface dialogInterface) {
                                    PinBouncerViewModel.this.requests.mo3456trySendJP2dKIU(PinBouncerViewModel.OnErrorDialogDismissed.INSTANCE);
                                }
                            });
                            create.show();
                            mutableState4.setValue(create);
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
                                MutableState mutableState5 = MutableState.this;
                                Dialog dialog2 = (Dialog) mutableState5.getValue();
                                if (dialog2 != null) {
                                    dialog2.hide();
                                }
                                mutableState5.setValue(null);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(rememberedValue3);
            }
            composerImpl.end(false);
            EffectsKt.DisposableEffect(str, (Function1) rememberedValue3, composerImpl);
            Dp.Companion companion = Dp.Companion;
            Modifier m128paddingqDBjuR0$default = PaddingKt.m128paddingqDBjuR0$default(Modifier.Companion, 0.0f, 0.0f, 0.0f, 20, 7);
            Alignment.Companion.getClass();
            MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
            PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl, m128paddingqDBjuR0$default);
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
            Updater.m336setimpl(composerImpl, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m336setimpl(composerImpl, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl, currentCompositeKeyHash, function2);
            }
            Updater.m336setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            if (Intrinsics.areEqual((Boolean) collectAsStateWithLifecycle.getValue(), Boolean.TRUE)) {
                composerImpl.startReplaceGroup(73016047);
                composerImpl.startReplaceGroup(-1383117277);
                boolean changedInstance = composerImpl.changedInstance(pinBouncerViewModel);
                Object rememberedValue4 = composerImpl.rememberedValue();
                if (changedInstance || rememberedValue4 == composer$Companion$Empty$1) {
                    rememberedValue4 = new PinInputDisplayKt$$ExternalSyntheticLambda4(pinBouncerViewModel, 0);
                    composerImpl.updateRememberedValue(rememberedValue4);
                }
                composerImpl.end(false);
                ComposableSingletons$PinInputDisplayKt.INSTANCE.getClass();
                PlatformButtonsKt.PlatformOutlinedButton((Function0) rememberedValue4, null, false, null, null, ComposableSingletons$PinInputDisplayKt.f27lambda1, composerImpl, 196608);
                composerImpl.end(false);
            } else if (Intrinsics.areEqual((Boolean) collectAsStateWithLifecycle.getValue(), Boolean.FALSE)) {
                composerImpl.startReplaceGroup(73881474);
                ImageKt.Image(PainterResources_androidKt.painterResource(R.drawable.ic_lockscreen_sim, composerImpl, 0), null, null, null, null, 0.0f, ColorFilter.Companion.m463tintxETnrds$default(ColorFilter.Companion, ColorResources_androidKt.colorResource(R.color.background_protected, composerImpl)), composerImpl, 48, 60);
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
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.bouncer.ui.composable.PinInputDisplayKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    PinInputDisplayKt.SimArea(PinBouncerViewModel.this, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
