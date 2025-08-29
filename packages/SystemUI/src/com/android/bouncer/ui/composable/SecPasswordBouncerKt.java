package com.android.bouncer.ui.composable;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.BasicTextFieldKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.KeyboardActions;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusChangedModifierKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEvent;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.SoftwareKeyboardInterceptionModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.PrimitiveResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.PlatformImeOptions;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.unit.Dp;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.bouncer.ui.compose.theme.BouncerStyleKt;
import com.android.systemui.R;
import com.android.systemui.bouncer.ui.viewmodel.PasswordBouncerViewModel;
import com.android.systemui.common.ui.compose.SelectedUserAwareInputConnectionKt;
import com.android.systemui.media.mediaoutput.compose.theme.TypeKt;
import com.samsung.sesl.compose.component.IconButtonKt;
import com.samsung.sesl.compose.foundation.shape.RoundedCornerShapeKt;
import com.samsung.sesl.compose.theme.ThemeKt;
import kotlin.Unit;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* loaded from: classes.dex */
public abstract class SecPasswordBouncerKt {
    public static final void SecPasswordBouncer(final PasswordBouncerViewModel passwordBouncerViewModel, final Modifier modifier, Composer composer, final int i) {
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(960111765);
        if (((i | (composerImpl2.changedInstance(passwordBouncerViewModel) ? 4 : 2) | (composerImpl2.changed(modifier) ? 32 : 16)) & 19) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecPasswordBouncer (SecPasswordBouncer.kt:79)");
            }
            composerImpl2.startReplaceGroup(747136562);
            Object objRememberedValue = composerImpl2.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl2);
            }
            final FocusRequester focusRequester = (FocusRequester) objRememberedValue;
            composerImpl2.end(false);
            MutableState mutableStateCollectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isTextFieldFocusRequested, composerImpl2);
            Boolean bool = (Boolean) mutableStateCollectAsStateWithLifecycle.getValue();
            boolean zM = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool, composerImpl2, 747142617, mutableStateCollectAsStateWithLifecycle);
            Object objRememberedValue2 = composerImpl2.rememberedValue();
            if (zM || objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new SecPasswordBouncerKt$SecPasswordBouncer$1$1(focusRequester, mutableStateCollectAsStateWithLifecycle, null);
                composerImpl2.updateRememberedValue(objRememberedValue2);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, bool, (Function2) objRememberedValue2);
            final MutableState mutableStateCollectAsStateWithLifecycle2 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.password, composerImpl2);
            final MutableState mutableStateCollectAsStateWithLifecycle3 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isInputEnabled, composerImpl2);
            MutableState mutableStateCollectAsStateWithLifecycle4 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.animateFailure, composerImpl2);
            MutableState mutableStateCollectAsStateWithLifecycle5 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.selectedUserId, composerImpl2);
            final MutableState mutableStateCollectAsStateWithLifecycle6 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isWhiteBg, composerImpl2);
            final MutableState mutableStateCollectAsStateWithLifecycle7 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.isShowLastPassword, composerImpl2);
            final MutableState mutableStateCollectAsStateWithLifecycle8 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.entryBackgroundColor, composerImpl2);
            final MutableState mutableStateCollectAsStateWithLifecycle9 = FlowExtKt.collectAsStateWithLifecycle(passwordBouncerViewModel.textColor, composerImpl2);
            Object[] objArr = new Object[0];
            composerImpl2.startReplaceGroup(747177486);
            Object objRememberedValue3 = composerImpl2.rememberedValue();
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new SecPasswordBouncerKt$$ExternalSyntheticLambda2();
                composerImpl2.updateRememberedValue(objRememberedValue3);
            }
            composerImpl2.end(false);
            final MutableState mutableState = (MutableState) RememberSaveableKt.rememberSaveable(objArr, null, null, (Function0) objRememberedValue3, composerImpl2, 3072, 6);
            Object objRememberedValue4 = composerImpl2.rememberedValue();
            if (objRememberedValue4 == composer$Companion$Empty$1) {
                objRememberedValue4 = EffectsKt.createCompositionCoroutineScope(EmptyCoroutineContext.INSTANCE, composerImpl2);
                composerImpl2.updateRememberedValue(objRememberedValue4);
            }
            final CoroutineScope coroutineScope = (CoroutineScope) objRememberedValue4;
            composerImpl2.startReplaceGroup(747184411);
            Object objRememberedValue5 = composerImpl2.rememberedValue();
            if (objRememberedValue5 == composer$Companion$Empty$1) {
                objRememberedValue5 = SnapshotStateKt.mutableStateOf$default(null);
                composerImpl2.updateRememberedValue(objRememberedValue5);
            }
            final MutableState mutableState2 = (MutableState) objRememberedValue5;
            Object objM = SecBouncerContentKt$SecHintMessage$1$$ExternalSyntheticOutline0.m(composerImpl2, false, 747188774);
            if (objM == composer$Companion$Empty$1) {
                objM = SnapshotStateKt.mutableStateOf$default(new PasswordVisualTransformation((char) 0, 1, null));
                composerImpl2.updateRememberedValue(objM);
            }
            final MutableState mutableState3 = (MutableState) objM;
            composerImpl2.end(false);
            Unit unit = Unit.INSTANCE;
            composerImpl2.startReplaceGroup(747192251);
            boolean zChangedInstance = composerImpl2.changedInstance(passwordBouncerViewModel);
            Object objRememberedValue6 = composerImpl2.rememberedValue();
            if (zChangedInstance || objRememberedValue6 == composer$Companion$Empty$1) {
                objRememberedValue6 = new SecPasswordBouncerKt$$ExternalSyntheticLambda3(passwordBouncerViewModel, 0);
                composerImpl2.updateRememberedValue(objRememberedValue6);
            }
            composerImpl2.end(false);
            EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue6, composerImpl2);
            Boolean bool2 = (Boolean) mutableStateCollectAsStateWithLifecycle4.getValue();
            boolean zM2 = SecPasswordBouncerKt$$ExternalSyntheticOutline0.m(bool2, composerImpl2, 747194798, mutableStateCollectAsStateWithLifecycle4) | composerImpl2.changedInstance(passwordBouncerViewModel);
            Object objRememberedValue7 = composerImpl2.rememberedValue();
            if (zM2 || objRememberedValue7 == composer$Companion$Empty$1) {
                objRememberedValue7 = new SecPasswordBouncerKt$SecPasswordBouncer$3$1(passwordBouncerViewModel, mutableStateCollectAsStateWithLifecycle4, null);
                composerImpl2.updateRememberedValue(objRememberedValue7);
            }
            composerImpl2.end(false);
            EffectsKt.LaunchedEffect(composerImpl2, bool2, (Function2) objRememberedValue7);
            composerImpl = composerImpl2;
            SelectedUserAwareInputConnectionKt.SelectedUserAwareInputConnection(((Number) mutableStateCollectAsStateWithLifecycle5.getValue()).intValue(), ComposableLambdaKt.rememberComposableLambda(793057239, new Function2() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt.SecPasswordBouncer.4
                /* JADX WARN: Removed duplicated region for block: B:20:0x00a1  */
                /* JADX WARN: Removed duplicated region for block: B:25:0x00e4  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x010c  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x01a6  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    VisualTransformation visualTransformation;
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        if (composerImpl3.getSkipping()) {
                            composerImpl3.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecPasswordBouncer.<anonymous> (SecPasswordBouncer.kt:128)");
                            }
                            State state = mutableStateCollectAsStateWithLifecycle2;
                            String str = (String) state.getValue();
                            boolean zBooleanValue = ((Boolean) mutableStateCollectAsStateWithLifecycle3.getValue()).booleanValue();
                            final MutableState mutableState4 = mutableState;
                            if (((Boolean) mutableState4.getValue()).booleanValue()) {
                                VisualTransformation.Companion.getClass();
                                visualTransformation = VisualTransformation.Companion.None;
                            } else {
                                visualTransformation = (VisualTransformation) mutableState3.getValue();
                            }
                            VisualTransformation visualTransformation2 = visualTransformation;
                            KeyboardType.Companion.getClass();
                            int i2 = KeyboardType.Password;
                            ImeAction.Companion.getClass();
                            KeyboardOptions keyboardOptions = new KeyboardOptions(0, (Boolean) null, i2, ImeAction.Done, (PlatformImeOptions) null, (Boolean) null, (LocaleList) null, 115, (DefaultConstructorMarker) null);
                            ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                            composerImpl4.startReplaceGroup(-1637359140);
                            final PasswordBouncerViewModel passwordBouncerViewModel2 = passwordBouncerViewModel;
                            boolean zChangedInstance2 = composerImpl4.changedInstance(passwordBouncerViewModel2);
                            Object objRememberedValue8 = composerImpl4.rememberedValue();
                            Composer.Companion companion = Composer.Companion;
                            if (!zChangedInstance2) {
                                companion.getClass();
                                if (objRememberedValue8 == Composer.Companion.Empty) {
                                    objRememberedValue8 = new SecPasswordBouncerKt$$ExternalSyntheticLambda3(passwordBouncerViewModel2, 1);
                                    composerImpl4.updateRememberedValue(objRememberedValue8);
                                }
                                composerImpl4.end(false);
                                KeyboardActions keyboardActions = new KeyboardActions((Function1) objRememberedValue8, null, null, null, null, null, 62, null);
                                FocusRequester focusRequester2 = focusRequester;
                                final Modifier modifier2 = modifier;
                                Modifier modifierFocusRequester = FocusRequesterModifierKt.focusRequester(modifier2, focusRequester2);
                                composerImpl4.startReplaceGroup(-1637352825);
                                boolean zChangedInstance3 = composerImpl4.changedInstance(passwordBouncerViewModel2);
                                Object objRememberedValue9 = composerImpl4.rememberedValue();
                                if (!zChangedInstance3) {
                                    companion.getClass();
                                    if (objRememberedValue9 == Composer.Companion.Empty) {
                                        objRememberedValue9 = new SecPasswordBouncerKt$$ExternalSyntheticLambda3(passwordBouncerViewModel2, 2);
                                        composerImpl4.updateRememberedValue(objRememberedValue9);
                                    }
                                    composerImpl4.end(false);
                                    Modifier modifierOnFocusChanged = FocusChangedModifierKt.onFocusChanged(modifierFocusRequester, (Function1) objRememberedValue9);
                                    composerImpl4.startReplaceGroup(-1637349212);
                                    boolean zChangedInstance4 = composerImpl4.changedInstance(passwordBouncerViewModel2);
                                    Object objRememberedValue10 = composerImpl4.rememberedValue();
                                    if (!zChangedInstance4) {
                                        companion.getClass();
                                        if (objRememberedValue10 == Composer.Companion.Empty) {
                                            objRememberedValue10 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$SecPasswordBouncer$4$3$1
                                                @Override // kotlin.jvm.functions.Function1
                                                /* renamed from: invoke */
                                                public final Object mo781invoke(Object obj3) {
                                                    boolean z;
                                                    long jM580getKeyZmokQxo = KeyEvent_androidKt.m580getKeyZmokQxo(((KeyEvent) obj3).nativeKeyEvent);
                                                    Key.Companion.getClass();
                                                    if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.Back)) {
                                                        passwordBouncerViewModel2.requests.mo3475trySendJP2dKIU(PasswordBouncerViewModel.OnImeDismissed.INSTANCE);
                                                        z = true;
                                                    } else {
                                                        z = false;
                                                    }
                                                    return Boolean.valueOf(z);
                                                }
                                            };
                                            composerImpl4.updateRememberedValue(objRememberedValue10);
                                        }
                                        composerImpl4.end(false);
                                        Modifier modifierOnInterceptKeyBeforeSoftKeyboard = SoftwareKeyboardInterceptionModifierKt.onInterceptKeyBeforeSoftKeyboard(modifierOnFocusChanged, (Function1) objRememberedValue10);
                                        State state2 = mutableStateCollectAsStateWithLifecycle9;
                                        long j = ((Color) state2.getValue()).value;
                                        composerImpl4.startReplaceGroup(2092395929);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.PasswordEntryStyle (BouncerStyle.kt:53)");
                                        }
                                        TextStyle.Companion companion2 = TextStyle.Companion;
                                        TextStyle textStyleM756copyp1EtxEg$default = TextStyle.m756copyp1EtxEg$default(TypeKt.getSecRegular(), j, BouncerStyleKt.getDpTextUnit(PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_dot_size, composerImpl4), composerImpl4), null, null, 0L, 0, 0L, null, null, 0, 16777212);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl4.end(false);
                                        SolidColor solidColor = new SolidColor(((Color) state2.getValue()).value, null);
                                        composerImpl4.startReplaceGroup(-1637395632);
                                        boolean zChanged = composerImpl4.changed(state) | composerImpl4.changedInstance(passwordBouncerViewModel2) | composerImpl4.changed(mutableStateCollectAsStateWithLifecycle7) | composerImpl4.changedInstance(coroutineScope);
                                        Object objRememberedValue11 = composerImpl4.rememberedValue();
                                        if (!zChanged) {
                                            companion.getClass();
                                            if (objRememberedValue11 == Composer.Companion.Empty) {
                                                final State state3 = mutableStateCollectAsStateWithLifecycle7;
                                                final State state4 = mutableStateCollectAsStateWithLifecycle2;
                                                final PasswordBouncerViewModel passwordBouncerViewModel3 = passwordBouncerViewModel;
                                                final CoroutineScope coroutineScope2 = coroutineScope;
                                                final MutableState mutableState5 = mutableState3;
                                                final MutableState mutableState6 = mutableState2;
                                                objRememberedValue11 = new Function1() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$SecPasswordBouncer$4$$ExternalSyntheticLambda2
                                                    @Override // kotlin.jvm.functions.Function1
                                                    /* renamed from: invoke */
                                                    public final Object mo781invoke(Object obj3) {
                                                        String str2 = (String) obj3;
                                                        PasswordBouncerViewModel passwordBouncerViewModel4 = passwordBouncerViewModel3;
                                                        passwordBouncerViewModel4.getClass();
                                                        if (str2.length() > 0) {
                                                            passwordBouncerViewModel4.onIntentionalUserInput.invoke();
                                                        }
                                                        passwordBouncerViewModel4._password.updateState(null, str2);
                                                        boolean zBooleanValue2 = ((Boolean) state3.getValue()).booleanValue();
                                                        MutableState mutableState7 = mutableState5;
                                                        if (!zBooleanValue2 || str2.length() < ((String) state4.getValue()).length()) {
                                                            mutableState7.setValue(new PasswordVisualTransformation((char) 0, 1, null));
                                                        } else {
                                                            mutableState7.setValue(new LastCharVisualTransformation());
                                                            MutableState mutableState8 = mutableState6;
                                                            Job job = (Job) mutableState8.getValue();
                                                            if (job != null) {
                                                                job.cancel(null);
                                                            }
                                                            mutableState8.setValue(BuildersKt.launch$default(coroutineScope2, null, null, new SecPasswordBouncerKt$SecPasswordBouncer$4$4$1$1(mutableState7, null), 3));
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                };
                                                composerImpl4.updateRememberedValue(objRememberedValue11);
                                            }
                                            composerImpl4.end(false);
                                            final State state5 = mutableStateCollectAsStateWithLifecycle8;
                                            final State state6 = mutableStateCollectAsStateWithLifecycle6;
                                            BasicTextFieldKt.BasicTextField(str, (Function1) objRememberedValue11, modifierOnInterceptKeyBeforeSoftKeyboard, zBooleanValue, false, textStyleM756copyp1EtxEg$default, keyboardOptions, keyboardActions, true, 0, 0, visualTransformation2, null, null, solidColor, ComposableLambdaKt.rememberComposableLambda(1568836538, new Function3() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt.SecPasswordBouncer.4.5
                                                /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                                                /* JADX WARN: Removed duplicated region for block: B:42:0x0176  */
                                                @Override // kotlin.jvm.functions.Function3
                                                /*
                                                    Code decompiled incorrectly, please refer to instructions dump.
                                                */
                                                public final Object invoke(Object obj3, Object obj4, Object obj5) {
                                                    Function2 function2 = (Function2) obj3;
                                                    Composer composer3 = (Composer) obj4;
                                                    int iIntValue = ((Number) obj5).intValue();
                                                    if ((iIntValue & 6) == 0) {
                                                        iIntValue |= ((ComposerImpl) composer3).changedInstance(function2) ? 4 : 2;
                                                    }
                                                    if ((iIntValue & 19) == 18) {
                                                        ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                        if (composerImpl5.getSkipping()) {
                                                            composerImpl5.skipToGroupEnd();
                                                        } else {
                                                            if (ComposerKt.isTraceInProgress()) {
                                                                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.SecPasswordBouncer.<anonymous>.<anonymous> (SecPasswordBouncer.kt:175)");
                                                            }
                                                            Modifier modifier3 = modifier2;
                                                            Modifier modifierFillMaxWidth = SizeKt.fillMaxWidth(modifier3, 1.0f);
                                                            long j2 = ((Color) state5.getValue()).value;
                                                            Dp.Companion companion3 = Dp.Companion;
                                                            Modifier modifierM128paddingqDBjuR0 = PaddingKt.m128paddingqDBjuR0(BackgroundKt.m26backgroundbw27NRU(modifierFillMaxWidth, j2, RoundedCornerShapeKt.m3353SeslRoundedCornerShapeD5KLDUw$default(22)), PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_entry_padding_horizontal_start, composer3), PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_entry_padding_vertical, composer3), PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_entry_padding_horizontal_end, composer3), PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_entry_padding_vertical, composer3));
                                                            Alignment.Companion.getClass();
                                                            BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                                                            Arrangement.INSTANCE.getClass();
                                                            RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(Arrangement.Start, vertical, composer3, 48);
                                                            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                            ComposerImpl composerImpl6 = (ComposerImpl) composer3;
                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl6.currentCompositionLocalScope();
                                                            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer3, modifierM128paddingqDBjuR0);
                                                            ComposeUiNode.Companion.getClass();
                                                            Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                            if (composerImpl6.applier == null) {
                                                                ComposablesKt.invalidApplier();
                                                                throw null;
                                                            }
                                                            composerImpl6.startReusableNode();
                                                            if (composerImpl6.inserting) {
                                                                composerImpl6.createNode(function0);
                                                            } else {
                                                                composerImpl6.useNode();
                                                            }
                                                            Function2 function22 = ComposeUiNode.Companion.SetMeasurePolicy;
                                                            Updater.m337setimpl(composer3, rowMeasurePolicy, function22);
                                                            Function2 function23 = ComposeUiNode.Companion.SetResolvedCompositionLocals;
                                                            Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope, function23);
                                                            Function2 function24 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                            if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl6, currentCompositeKeyHash, function24);
                                                            }
                                                            Function2 function25 = ComposeUiNode.Companion.SetModifier;
                                                            Updater.m337setimpl(composer3, modifierMaterializeModifier, function25);
                                                            Modifier modifierM129paddingqDBjuR0$default = PaddingKt.m129paddingqDBjuR0$default(RowScopeInstance.INSTANCE.weight(modifier3, 1.0f, true), 0.0f, 0.0f, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_entry_padding_horizontal_end, composer3), 0.0f, 11);
                                                            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composer3);
                                                            PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope2 = composerImpl6.currentCompositionLocalScope();
                                                            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composer3, modifierM129paddingqDBjuR0$default);
                                                            composerImpl6.startReusableNode();
                                                            if (composerImpl6.inserting) {
                                                                composerImpl6.createNode(function0);
                                                            } else {
                                                                composerImpl6.useNode();
                                                            }
                                                            Updater.m337setimpl(composer3, measurePolicyMaybeCachedBoxMeasurePolicy, function22);
                                                            Updater.m337setimpl(composer3, persistentCompositionLocalMapCurrentCompositionLocalScope2, function23);
                                                            if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                                                                AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash2, composerImpl6, currentCompositeKeyHash2, function24);
                                                            }
                                                            Updater.m337setimpl(composer3, modifierMaterializeModifier2, function25);
                                                            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                            function2.invoke(composer3, Integer.valueOf(iIntValue & 14));
                                                            composerImpl6.end(true);
                                                            boolean zBooleanValue2 = ((Boolean) state6.getValue()).booleanValue();
                                                            final MutableState mutableState7 = mutableState4;
                                                            boolean zBooleanValue3 = ((Boolean) mutableState7.getValue()).booleanValue();
                                                            composerImpl6.startReplaceGroup(-1279916458);
                                                            boolean zChanged2 = composerImpl6.changed(mutableState7);
                                                            Object objRememberedValue12 = composerImpl6.rememberedValue();
                                                            if (!zChanged2) {
                                                                Composer.Companion.getClass();
                                                                if (objRememberedValue12 == Composer.Companion.Empty) {
                                                                    objRememberedValue12 = new Function0() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$SecPasswordBouncer$4$5$$ExternalSyntheticLambda0
                                                                        @Override // kotlin.jvm.functions.Function0
                                                                        public final Object invoke() {
                                                                            mutableState7.setValue(Boolean.valueOf(!((Boolean) r1.getValue()).booleanValue()));
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    };
                                                                    composerImpl6.updateRememberedValue(objRememberedValue12);
                                                                }
                                                                composerImpl6.end(false);
                                                                SecPasswordBouncerKt.ShowPasswordButton(zBooleanValue2, zBooleanValue3, (Function0) objRememberedValue12, composer3, 0);
                                                                composerImpl6.end(true);
                                                                if (ComposerKt.isTraceInProgress()) {
                                                                    ComposerKt.traceEventEnd();
                                                                }
                                                            }
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }, composerImpl4), composerImpl4, 102236160, 196608, 13840);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 48);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(modifier, i) { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticLambda4
                public final /* synthetic */ Modifier f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    SecPasswordBouncerKt.SecPasswordBouncer(this.f$0, this.f$1, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void ShowPasswordButton(final boolean z, final boolean z2, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-97211997);
        if ((((composerImpl.changed(z) ? 4 : 2) | i | (composerImpl.changed(z2) ? 32 : 16) | (composerImpl.changedInstance(function0) ? 256 : 128)) & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ShowPasswordButton (SecPasswordBouncer.kt:212)");
            }
            ThemeKt.SeslTheme(false, null, ComposableLambdaKt.rememberComposableLambda(159895186, new Function2() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt.ShowPasswordButton.1
                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    if ((((Number) obj2).intValue() & 3) == 2) {
                        ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                        if (composerImpl2.getSkipping()) {
                            composerImpl2.skipToGroupEnd();
                        } else {
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ShowPasswordButton.<anonymous> (SecPasswordBouncer.kt:214)");
                            }
                            Modifier modifierM140size3ABfNKs = SizeKt.m140size3ABfNKs(Modifier.Companion, PrimitiveResources_androidKt.dimensionResource(R.dimen.kg_compose_password_show_password_button_size, composer2));
                            final boolean z3 = z2;
                            final boolean z4 = z;
                            IconButtonKt.SeslIconButton(function0, modifierM140size3ABfNKs, false, (IconButtonColors) null, (MutableInteractionSource) null, (Function2) ComposableLambdaKt.rememberComposableLambda(1897794106, new Function2() { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt.ShowPasswordButton.1.1
                                /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                @Override // kotlin.jvm.functions.Function2
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object invoke(Object obj3, Object obj4) {
                                    Composer composer3 = (Composer) obj3;
                                    if ((((Number) obj4).intValue() & 3) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer3;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                        } else {
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("com.android.bouncer.ui.composable.ShowPasswordButton.<anonymous>.<anonymous> (SecPasswordBouncer.kt:219)");
                                            }
                                            boolean z5 = z3;
                                            boolean z6 = z4;
                                            Painter painterPainterResource = PainterResources_androidKt.painterResource(z5 ? z6 ? R.drawable.lock_whitebg_password_show_btn : R.drawable.lock_password_show_btn : z6 ? R.drawable.lock_whitebg_password_hide_btn : R.drawable.lock_password_hide_btn, composer3, 0);
                                            Color.Companion.getClass();
                                            IconKt.m270Iconww6aTOc(painterPainterResource, (String) null, (Modifier) null, Color.Unspecified, composer3, 3120, 4);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composer2), composer2, 196608, 28);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 384, 3);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(z, z2, function0, i) { // from class: com.android.bouncer.ui.composable.SecPasswordBouncerKt$$ExternalSyntheticLambda1
                public final /* synthetic */ boolean f$0;
                public final /* synthetic */ boolean f$1;
                public final /* synthetic */ Function0 f$2;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    boolean z3 = this.f$1;
                    Function0 function02 = this.f$2;
                    SecPasswordBouncerKt.ShowPasswordButton(this.f$0, z3, function02, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
