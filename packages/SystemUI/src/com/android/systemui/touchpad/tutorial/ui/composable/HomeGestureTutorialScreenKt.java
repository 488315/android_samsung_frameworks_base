package com.android.systemui.touchpad.tutorial.ui.composable;

import android.view.MotionEvent;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocal;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import com.airbnb.lottie.compose.LottieDynamicProperties;
import com.airbnb.lottie.compose.LottieDynamicPropertiesKt;
import com.airbnb.lottie.compose.LottieDynamicProperty;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.systemui.R;
import com.android.systemui.inputdevice.tutorial.ui.composable.LottieHelpersKt;
import com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig;
import com.android.systemui.touchpad.tutorial.ui.gesture.TouchpadEventsFilterKt;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureScreenViewModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes3.dex */
public abstract class HomeGestureTutorialScreenKt {
    /* JADX WARN: Removed duplicated region for block: B:48:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void HomeGestureTutorialScreen(final HomeGestureScreenViewModel homeGestureScreenViewModel, final EasterEggGestureViewModel easterEggGestureViewModel, final Function0 function0, final Function0 function02, Function1 function1, Composer composer, final int i, final int i2) {
        int i3;
        Function1 function12;
        boolean zChanged;
        final Function1 function13;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1014721168);
        if ((i & 6) == 0) {
            i3 = (composerImpl.changedInstance(homeGestureScreenViewModel) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl.changedInstance(easterEggGestureViewModel) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl.changedInstance(function02) ? 2048 : 1024;
        }
        int i4 = i2 & 16;
        if (i4 == 0) {
            if ((i & 24576) == 0) {
                function12 = function1;
                i3 |= composerImpl.changedInstance(function12) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
            }
            if ((i3 & 9363) == 9362 || !composerImpl.getSkipping()) {
                Function1 function14 = i4 == 0 ? null : function12;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.HomeGestureTutorialScreen (HomeGestureTutorialScreen.kt:35)");
                }
                composerImpl.startReplaceGroup(1254636543);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.rememberScreenColors (HomeGestureTutorialScreen.kt:66)");
                }
                CompositionLocal compositionLocal = AndroidColorSchemeKt.LocalAndroidColorScheme;
                long j = ((AndroidColorScheme) composerImpl.consume(compositionLocal)).primaryFixedDim;
                long j2 = ((AndroidColorScheme) composerImpl.consume(compositionLocal)).onPrimaryFixed;
                int i5 = i3;
                LottieDynamicProperties lottieDynamicPropertiesRememberLottieDynamicProperties = LottieDynamicPropertiesKt.rememberLottieDynamicProperties(new LottieDynamicProperty[]{LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".primaryFixedDim", j, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onPrimaryFixed", j2, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onPrimaryFixedVariant", ((AndroidColorScheme) composerImpl.consume(compositionLocal)).onPrimaryFixedVariant, composerImpl)}, composerImpl);
                composerImpl.startReplaceGroup(1152968430);
                zChanged = composerImpl.changed(lottieDynamicPropertiesRememberLottieDynamicProperties);
                Object objRememberedValue = composerImpl.rememberedValue();
                Composer.Companion companion = Composer.Companion;
                if (zChanged) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new TutorialScreenConfig.Colors(j2, j, lottieDynamicPropertiesRememberLottieDynamicProperties, (DefaultConstructorMarker) null);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    TutorialScreenConfig.Colors colors = (TutorialScreenConfig.Colors) objRememberedValue;
                    composerImpl.end(false);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl.end(false);
                    TutorialScreenConfig tutorialScreenConfig = new TutorialScreenConfig(colors, new TutorialScreenConfig.Strings(R.string.touchpad_home_gesture_action_title, R.string.touchpad_home_gesture_guidance, R.string.touchpad_home_gesture_success_title, R.string.touchpad_home_gesture_success_body, R.string.gesture_error_title, R.string.touchpad_home_gesture_error_body), new TutorialScreenConfig.Animations(R.raw.trackpad_home_edu));
                    SafeFlow safeFlow = homeGestureScreenViewModel.tutorialState;
                    composerImpl.startReplaceGroup(1932667002);
                    boolean zChangedInstance = composerImpl.changedInstance(easterEggGestureViewModel) | composerImpl.changedInstance(homeGestureScreenViewModel);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new Function1() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.HomeGestureTutorialScreenKt$$ExternalSyntheticLambda0
                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj) {
                                    MotionEvent motionEvent = (MotionEvent) obj;
                                    TouchpadEventsFilterKt.handleTouchpadMotionEvent(easterEggGestureViewModel.gestureRecognizer, motionEvent);
                                    return Boolean.valueOf(TouchpadEventsFilterKt.handleTouchpadMotionEvent(homeGestureScreenViewModel.gestureRecognizer, motionEvent));
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        Function1 function15 = (Function1) objRememberedValue2;
                        composerImpl.end(false);
                        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = easterEggGestureViewModel.easterEggTriggered;
                        composerImpl.startReplaceGroup(1932673638);
                        boolean zChangedInstance2 = composerImpl.changedInstance(easterEggGestureViewModel);
                        Object objRememberedValue3 = composerImpl.rememberedValue();
                        if (!zChangedInstance2) {
                            companion.getClass();
                            if (objRememberedValue3 == Composer.Companion.Empty) {
                                objRememberedValue3 = new HomeGestureTutorialScreenKt$HomeGestureTutorialScreen$2$1(easterEggGestureViewModel);
                                composerImpl.updateRememberedValue(objRememberedValue3);
                            }
                            composerImpl.end(false);
                            int i6 = i5 << 9;
                            GestureTutorialScreenKt.GestureTutorialScreen(tutorialScreenConfig, safeFlow, function15, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, (Function0) ((KFunction) objRememberedValue3), function0, function02, function14, composerImpl, (458752 & i6) | 8 | (3670016 & i6) | (i6 & 29360128), 0);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            function13 = function14;
                        }
                    }
                }
            } else {
                composerImpl.skipToGroupEnd();
                function13 = function12;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.HomeGestureTutorialScreenKt$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Integer) obj2).getClass();
                        int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                        Function1 function16 = function13;
                        HomeGestureTutorialScreenKt.HomeGestureTutorialScreen(homeGestureScreenViewModel, easterEggGestureViewModel, function0, function02, function16, (Composer) obj, iUpdateChangedFlags, i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 24576;
        function12 = function1;
        if ((i3 & 9363) == 9362) {
            if (i4 == 0) {
            }
            if (ComposerKt.isTraceInProgress()) {
            }
            composerImpl.startReplaceGroup(1254636543);
            if (ComposerKt.isTraceInProgress()) {
            }
            CompositionLocal compositionLocal2 = AndroidColorSchemeKt.LocalAndroidColorScheme;
            long j3 = ((AndroidColorScheme) composerImpl.consume(compositionLocal2)).primaryFixedDim;
            long j22 = ((AndroidColorScheme) composerImpl.consume(compositionLocal2)).onPrimaryFixed;
            int i52 = i3;
            LottieDynamicProperties lottieDynamicPropertiesRememberLottieDynamicProperties2 = LottieDynamicPropertiesKt.rememberLottieDynamicProperties(new LottieDynamicProperty[]{LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".primaryFixedDim", j3, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onPrimaryFixed", j22, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onPrimaryFixedVariant", ((AndroidColorScheme) composerImpl.consume(compositionLocal2)).onPrimaryFixedVariant, composerImpl)}, composerImpl);
            composerImpl.startReplaceGroup(1152968430);
            zChanged = composerImpl.changed(lottieDynamicPropertiesRememberLottieDynamicProperties2);
            Object objRememberedValue4 = composerImpl.rememberedValue();
            Composer.Companion companion2 = Composer.Companion;
            if (zChanged) {
            }
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }
}
