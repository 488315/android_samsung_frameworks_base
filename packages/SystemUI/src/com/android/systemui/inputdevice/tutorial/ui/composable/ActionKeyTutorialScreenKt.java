package com.android.systemui.inputdevice.tutorial.ui.composable;

import android.view.KeyEvent;
import androidx.activity.compose.BackHandlerKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.CoreTextFieldKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.saveable.MapSaverKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import com.airbnb.lottie.compose.LottieDynamicProperties;
import com.airbnb.lottie.compose.LottieDynamicPropertiesKt;
import com.airbnb.lottie.compose.LottieDynamicProperty;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.systemui.R;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialActionState;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public abstract class ActionKeyTutorialScreenKt {
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void ActionKeyTutorialScreen(final Function0 function0, final Function0 function02, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1892233530);
        int i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i | (composerImpl.changedInstance(function02) ? 32 : 16);
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.ActionKeyTutorialScreen (ActionKeyTutorialScreen.kt:45)");
            }
            BackHandlerKt.BackHandler(false, function02, composerImpl, i2 & 112, 1);
            composerImpl.startReplaceGroup(-133994219);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.buildScreenConfig (ActionKeyTutorialScreen.kt:77)");
            }
            composerImpl.startReplaceGroup(550520098);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.composable.rememberScreenColors (ActionKeyTutorialScreen.kt:93)");
            }
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = AndroidColorSchemeKt.LocalAndroidColorScheme;
            long j = ((AndroidColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).primaryFixedDim;
            long j2 = ((AndroidColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).secondaryFixedDim;
            long j3 = ((AndroidColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).onSecondaryFixed;
            LottieDynamicProperties lottieDynamicPropertiesRememberLottieDynamicProperties = LottieDynamicPropertiesKt.rememberLottieDynamicProperties(new LottieDynamicProperty[]{LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".primaryFixedDim", j, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".secondaryFixedDim", j2, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onSecondaryFixed", j3, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onSecondaryFixedVariant", ((AndroidColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).onSecondaryFixedVariant, composerImpl)}, composerImpl);
            composerImpl.startReplaceGroup(-839209760);
            boolean zChanged = composerImpl.changed(lottieDynamicPropertiesRememberLottieDynamicProperties);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    TutorialScreenConfig.Colors colors = new TutorialScreenConfig.Colors(j3, j2, lottieDynamicPropertiesRememberLottieDynamicProperties, (DefaultConstructorMarker) null);
                    composerImpl.updateRememberedValue(colors);
                    objRememberedValue = colors;
                }
                TutorialScreenConfig.Colors colors2 = (TutorialScreenConfig.Colors) objRememberedValue;
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                TutorialScreenConfig tutorialScreenConfig = new TutorialScreenConfig(colors2, new TutorialScreenConfig.Strings(R.string.tutorial_action_key_title, R.string.tutorial_action_key_guidance, R.string.tutorial_action_key_success_title, R.string.tutorial_action_key_success_body, R.string.gesture_error_title, R.string.touchpad_action_key_error_body), new TutorialScreenConfig.Animations(R.raw.action_key_edu));
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                Object[] objArr = new Object[0];
                TutorialActionState.Companion.getClass();
                SaverKt$Saver$1 saverKt$Saver$1MapSaver = MapSaverKt.mapSaver(new TutorialActionState$Companion$$ExternalSyntheticLambda0(), new TutorialActionState$Companion$$ExternalSyntheticLambda1());
                composerImpl.startReplaceGroup(-1382761733);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                companion.getClass();
                Object obj = Composer.Companion.Empty;
                if (objRememberedValue2 == obj) {
                    objRememberedValue2 = new ActionKeyTutorialScreenKt$$ExternalSyntheticLambda0();
                    composerImpl.updateRememberedValue(objRememberedValue2);
                }
                composerImpl.end(false);
                final MutableState mutableStateRememberSaveable = RememberSaveableKt.rememberSaveable(objArr, saverKt$Saver$1MapSaver, (Function0) objRememberedValue2, composerImpl);
                composerImpl.startReplaceGroup(-1382759322);
                Object objRememberedValue3 = composerImpl.rememberedValue();
                if (objRememberedValue3 == obj) {
                    objRememberedValue3 = CoreTextFieldKt$$ExternalSyntheticOutline0.m(composerImpl);
                }
                FocusRequester focusRequester = (FocusRequester) objRememberedValue3;
                composerImpl.end(false);
                Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                composerImpl.startReplaceGroup(-1382755223);
                boolean zChanged2 = composerImpl.changed(mutableStateRememberSaveable);
                Object objRememberedValue4 = composerImpl.rememberedValue();
                if (zChanged2 || objRememberedValue4 == obj) {
                    objRememberedValue4 = new Function1() { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.ActionKeyTutorialScreenKt$ActionKeyTutorialScreen$1$1
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj2) {
                            KeyEvent keyEvent = ((androidx.compose.ui.input.key.KeyEvent) obj2).nativeKeyEvent;
                            long jM580getKeyZmokQxo = KeyEvent_androidKt.m580getKeyZmokQxo(keyEvent);
                            Key.Companion.getClass();
                            if (Key.m578equalsimpl0(jM580getKeyZmokQxo, Key.MetaLeft)) {
                                int iM581getTypeZmokQxo = KeyEvent_androidKt.m581getTypeZmokQxo(keyEvent);
                                KeyEventType.Companion.getClass();
                                if (iM581getTypeZmokQxo == KeyEventType.KeyUp) {
                                    mutableStateRememberSaveable.setValue(new TutorialActionState.Finished(R.raw.action_key_success));
                                }
                            }
                            return Boolean.TRUE;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue4);
                }
                composerImpl.end(false);
                Modifier modifierFocusable$default = FocusableKt.focusable$default(FocusRequesterModifierKt.focusRequester(KeyInputModifierKt.onKeyEvent(modifierFillMaxSize, (Function1) objRememberedValue4), focusRequester), false, null, 3);
                Alignment.Companion.getClass();
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl);
                PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl.currentCompositionLocalScope();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl, modifierFocusable$default);
                ComposeUiNode.Companion.getClass();
                Function0 function03 = ComposeUiNode.Companion.Constructor;
                if (composerImpl.applier == null) {
                    ComposablesKt.invalidApplier();
                    throw null;
                }
                composerImpl.startReusableNode();
                if (composerImpl.inserting) {
                    composerImpl.createNode(function03);
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
                ActionTutorialContentKt.ActionTutorialContent((TutorialActionState) mutableStateRememberSaveable.getValue(), function0, tutorialScreenConfig, null, composerImpl, ((i2 << 3) & 112) | 512, 8);
                composerImpl.end(true);
                Unit unit = Unit.INSTANCE;
                composerImpl.startReplaceGroup(-1382740762);
                Object objRememberedValue5 = composerImpl.rememberedValue();
                if (objRememberedValue5 == obj) {
                    objRememberedValue5 = new ActionKeyTutorialScreenKt$ActionKeyTutorialScreen$3$1(focusRequester, null);
                    composerImpl.updateRememberedValue(objRememberedValue5);
                }
                composerImpl.end(false);
                EffectsKt.LaunchedEffect(composerImpl, unit, (Function2) objRememberedValue5);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(function02, i) { // from class: com.android.systemui.inputdevice.tutorial.ui.composable.ActionKeyTutorialScreenKt$$ExternalSyntheticLambda1
                public final /* synthetic */ Function0 f$1;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Integer) obj3).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    ActionKeyTutorialScreenKt.ActionKeyTutorialScreen(this.f$0, this.f$1, (Composer) obj2, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
