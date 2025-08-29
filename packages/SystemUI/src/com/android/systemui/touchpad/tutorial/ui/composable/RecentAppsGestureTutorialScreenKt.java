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
import com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureScreenViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KFunction;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes3.dex */
public abstract class RecentAppsGestureTutorialScreenKt {
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void RecentAppsGestureTutorialScreen(final RecentAppsGestureScreenViewModel recentAppsGestureScreenViewModel, final EasterEggGestureViewModel easterEggGestureViewModel, final Function0 function0, final Function0 function02, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1302536854);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changedInstance(recentAppsGestureScreenViewModel) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(easterEggGestureViewModel) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerImpl.changedInstance(function02) ? 2048 : 1024;
        }
        if ((i2 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.RecentAppsGestureTutorialScreen (RecentAppsGestureTutorialScreen.kt:34)");
            }
            composerImpl.startReplaceGroup(504825741);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.composable.rememberScreenColors (RecentAppsGestureTutorialScreen.kt:65)");
            }
            CompositionLocal compositionLocal = AndroidColorSchemeKt.LocalAndroidColorScheme;
            long j = ((AndroidColorScheme) composerImpl.consume(compositionLocal)).secondaryFixedDim;
            long j2 = ((AndroidColorScheme) composerImpl.consume(compositionLocal)).onSecondaryFixed;
            int i3 = i2;
            LottieDynamicProperties lottieDynamicPropertiesRememberLottieDynamicProperties = LottieDynamicPropertiesKt.rememberLottieDynamicProperties(new LottieDynamicProperty[]{LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".secondaryFixedDim", j, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onSecondaryFixed", j2, composerImpl), LottieHelpersKt.m2583rememberColorFilterPropertyRPmYEkk(".onSecondaryFixedVariant", ((AndroidColorScheme) composerImpl.consume(compositionLocal)).onSecondaryFixedVariant, composerImpl)}, composerImpl);
            composerImpl.startReplaceGroup(1152968658);
            boolean zChanged = composerImpl.changed(lottieDynamicPropertiesRememberLottieDynamicProperties);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChanged) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    Object colors = new TutorialScreenConfig.Colors(j2, j, lottieDynamicPropertiesRememberLottieDynamicProperties, (DefaultConstructorMarker) null);
                    composerImpl.updateRememberedValue(colors);
                    objRememberedValue = colors;
                }
                TutorialScreenConfig.Colors colors2 = (TutorialScreenConfig.Colors) objRememberedValue;
                composerImpl.end(false);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl.end(false);
                TutorialScreenConfig tutorialScreenConfig = new TutorialScreenConfig(colors2, new TutorialScreenConfig.Strings(R.string.touchpad_recent_apps_gesture_action_title, R.string.touchpad_recent_apps_gesture_guidance, R.string.touchpad_recent_apps_gesture_success_title, R.string.touchpad_recent_apps_gesture_success_body, R.string.gesture_error_title, R.string.touchpad_recent_gesture_error_body), new TutorialScreenConfig.Animations(R.raw.trackpad_recent_apps_edu));
                SafeFlow safeFlow = recentAppsGestureScreenViewModel.tutorialState;
                composerImpl.startReplaceGroup(1508089324);
                boolean zChangedInstance = composerImpl.changedInstance(easterEggGestureViewModel) | composerImpl.changedInstance(recentAppsGestureScreenViewModel);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function1() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.RecentAppsGestureTutorialScreenKt$$ExternalSyntheticLambda0
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke */
                            public final Object mo781invoke(Object obj) {
                                MotionEvent motionEvent = (MotionEvent) obj;
                                TouchpadEventsFilterKt.handleTouchpadMotionEvent(easterEggGestureViewModel.gestureRecognizer, motionEvent);
                                return Boolean.valueOf(TouchpadEventsFilterKt.handleTouchpadMotionEvent(recentAppsGestureScreenViewModel.gestureRecognizer, motionEvent));
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    Function1 function1 = (Function1) objRememberedValue2;
                    composerImpl.end(false);
                    FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = easterEggGestureViewModel.easterEggTriggered;
                    composerImpl.startReplaceGroup(1508095960);
                    boolean zChangedInstance2 = composerImpl.changedInstance(easterEggGestureViewModel);
                    Object objRememberedValue3 = composerImpl.rememberedValue();
                    if (!zChangedInstance2) {
                        companion.getClass();
                        if (objRememberedValue3 == Composer.Companion.Empty) {
                            objRememberedValue3 = new RecentAppsGestureTutorialScreenKt$RecentAppsGestureTutorialScreen$2$1(easterEggGestureViewModel);
                            composerImpl.updateRememberedValue(objRememberedValue3);
                        }
                        composerImpl.end(false);
                        int i4 = i3 << 9;
                        GestureTutorialScreenKt.GestureTutorialScreen(tutorialScreenConfig, safeFlow, function1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, (Function0) ((KFunction) objRememberedValue3), function0, function02, null, composerImpl, (458752 & i4) | 8 | (i4 & 3670016), 128);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.composable.RecentAppsGestureTutorialScreenKt$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).intValue();
                    RecentAppsGestureTutorialScreenKt.RecentAppsGestureTutorialScreen(recentAppsGestureScreenViewModel, easterEggGestureViewModel, function0, function02, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
