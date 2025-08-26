package com.android.systemui.touchpad.tutorial.ui.view;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.R;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger$$ExternalSyntheticLambda0;
import com.android.systemui.inputdevice.tutorial.KeyboardTouchpadTutorialMetricsLogger;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.BackGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.SwitchAppsGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.TouchpadTutorialViewModel;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KFunction;

/* loaded from: classes3.dex */
public final class TouchpadTutorialActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BackGestureScreenViewModel backGestureViewModel;
    public final EasterEggGestureViewModel easterEggGestureViewModel;
    public final HomeGestureScreenViewModel homeGestureViewModel;
    public final InputDeviceTutorialLogger logger;
    public final KeyboardTouchpadTutorialMetricsLogger metricsLogger;
    public final RecentAppsGestureScreenViewModel recentAppsGestureViewModel;
    public final SwitchAppsGestureScreenViewModel switchAppsGestureScreenViewModel;
    public final ViewModelLazy tutorialViewModel$delegate;
    public final TouchpadTutorialViewModel.Factory viewModelFactory;

    public TouchpadTutorialActivity(TouchpadTutorialViewModel.Factory factory, InputDeviceTutorialLogger inputDeviceTutorialLogger, KeyboardTouchpadTutorialMetricsLogger keyboardTouchpadTutorialMetricsLogger, BackGestureScreenViewModel backGestureScreenViewModel, HomeGestureScreenViewModel homeGestureScreenViewModel, RecentAppsGestureScreenViewModel recentAppsGestureScreenViewModel, SwitchAppsGestureScreenViewModel switchAppsGestureScreenViewModel, EasterEggGestureViewModel easterEggGestureViewModel) {
        this.viewModelFactory = factory;
        this.logger = inputDeviceTutorialLogger;
        this.metricsLogger = keyboardTouchpadTutorialMetricsLogger;
        this.backGestureViewModel = backGestureScreenViewModel;
        this.homeGestureViewModel = homeGestureScreenViewModel;
        this.recentAppsGestureViewModel = recentAppsGestureScreenViewModel;
        this.switchAppsGestureScreenViewModel = switchAppsGestureScreenViewModel;
        this.easterEggGestureViewModel = easterEggGestureViewModel;
        final Function0 function0 = null;
        this.tutorialViewModel$delegate = new ViewModelLazy(Reflection.getOrCreateKotlinClass(TouchpadTutorialViewModel.class), new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.getViewModelStore();
            }
        }, new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.viewModelFactory;
            }
        }, new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$special$$inlined$viewModels$default$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CreationExtras creationExtras;
                Function0 function02 = function0;
                return (function02 == null || (creationExtras = (CreationExtras) function02.invoke()) == null) ? this.getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable$default(this);
        setTitle(getString(R.string.launch_touchpad_tutorial_notification_content));
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(-404230632, true, new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.onCreate.1
            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                    } else {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.onCreate.<anonymous> (TouchpadTutorialActivity.kt:75)");
                        }
                        final TouchpadTutorialActivity touchpadTutorialActivity = TouchpadTutorialActivity.this;
                        PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-2016162462, new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.onCreate.1.1
                            /* JADX WARN: Removed duplicated region for block: B:15:0x004f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                            @Override // kotlin.jvm.functions.Function2
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object invoke(Object obj3, Object obj4) {
                                Composer composer2 = (Composer) obj3;
                                if ((((Number) obj4).intValue() & 3) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                    } else {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.onCreate.<anonymous>.<anonymous> (TouchpadTutorialActivity.kt:76)");
                                        }
                                        int i = TouchpadTutorialActivity.$r8$clinit;
                                        TouchpadTutorialActivity touchpadTutorialActivity2 = touchpadTutorialActivity;
                                        TouchpadTutorialViewModel touchpadTutorialViewModel = (TouchpadTutorialViewModel) touchpadTutorialActivity2.tutorialViewModel$delegate.getValue();
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        composerImpl3.startReplaceGroup(-275382062);
                                        boolean zChangedInstance = composerImpl3.changedInstance(touchpadTutorialActivity2);
                                        Object objRememberedValue = composerImpl3.rememberedValue();
                                        if (!zChangedInstance) {
                                            Composer.Companion.getClass();
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                objRememberedValue = new TouchpadTutorialActivity$onCreate$1$1$1$1(touchpadTutorialActivity2);
                                                composerImpl3.updateRememberedValue(objRememberedValue);
                                            }
                                            composerImpl3.end(false);
                                            SwitchAppsGestureScreenViewModel switchAppsGestureScreenViewModel = touchpadTutorialActivity2.switchAppsGestureScreenViewModel;
                                            EasterEggGestureViewModel easterEggGestureViewModel = touchpadTutorialActivity2.easterEggGestureViewModel;
                                            TouchpadTutorialActivityKt.TouchpadTutorialScreen(touchpadTutorialViewModel, touchpadTutorialActivity2.backGestureViewModel, touchpadTutorialActivity2.homeGestureViewModel, touchpadTutorialActivity2.recentAppsGestureViewModel, switchAppsGestureScreenViewModel, easterEggGestureViewModel, (Function0) ((KFunction) objRememberedValue), composerImpl3, 0);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                        }
                                    }
                                }
                                return Unit.INSTANCE;
                            }
                        }, composer), composer, 48, 1);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                }
                return Unit.INSTANCE;
            }
        }));
        getWindow().addPrivateFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        this.metricsLogger.getClass();
        SysUiStatsLog.write(942, 1, 2);
        InputDeviceTutorialLogger.TutorialContext tutorialContext = InputDeviceTutorialLogger.TutorialContext.TOUCHPAD_TUTORIAL;
        InputDeviceTutorialLogger inputDeviceTutorialLogger = this.logger;
        inputDeviceTutorialLogger.getClass();
        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(1);
        LogLevel logLevel = LogLevel.INFO;
        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = tutorialContext.getString();
        logBuffer.commit(logMessageObtain);
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        ((TouchpadTutorialViewModel) this.tutorialViewModel$delegate.getValue()).gesturesInteractor.enableGestures();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        ((TouchpadTutorialViewModel) this.tutorialViewModel$delegate.getValue()).gesturesInteractor.disableGestures();
    }
}
