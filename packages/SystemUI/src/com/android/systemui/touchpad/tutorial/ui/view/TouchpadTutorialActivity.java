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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                return ComponentActivity.this.getViewModelStore();
            }
        }, new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return TouchpadTutorialActivity.this.viewModelFactory;
            }
        }, new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$special$$inlined$viewModels$default$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CreationExtras creationExtras;
                Function0 function02 = Function0.this;
                return (function02 == null || (creationExtras = (CreationExtras) function02.invoke()) == null) ? this.getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable$default(this);
        setTitle(getString(R.string.launch_touchpad_tutorial_notification_content));
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(-404230632, true, new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.onCreate.<anonymous> (TouchpadTutorialActivity.kt:75)");
                }
                final TouchpadTutorialActivity touchpadTutorialActivity = TouchpadTutorialActivity.this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-2016162462, new Function2() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1.1
                    /* JADX WARN: Code restructure failed: missing block: B:15:0x004d, code lost:
                    
                        if (r11 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                     */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r10, java.lang.Object r11) {
                        /*
                            r9 = this;
                            androidx.compose.runtime.Composer r10 = (androidx.compose.runtime.Composer) r10
                            java.lang.Number r11 = (java.lang.Number) r11
                            int r11 = r11.intValue()
                            r11 = r11 & 3
                            r0 = 2
                            if (r11 != r0) goto L1b
                            r11 = r10
                            androidx.compose.runtime.ComposerImpl r11 = (androidx.compose.runtime.ComposerImpl) r11
                            boolean r0 = r11.getSkipping()
                            if (r0 != 0) goto L17
                            goto L1b
                        L17:
                            r11.skipToGroupEnd()
                            goto L77
                        L1b:
                            boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r11 == 0) goto L26
                            java.lang.String r11 = "com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.onCreate.<anonymous>.<anonymous> (TouchpadTutorialActivity.kt:76)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r11)
                        L26:
                            int r11 = com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.$r8$clinit
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity r9 = com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity.this
                            androidx.lifecycle.ViewModelLazy r11 = r9.tutorialViewModel$delegate
                            java.lang.Object r11 = r11.getValue()
                            r0 = r11
                            com.android.systemui.touchpad.tutorial.ui.viewmodel.TouchpadTutorialViewModel r0 = (com.android.systemui.touchpad.tutorial.ui.viewmodel.TouchpadTutorialViewModel) r0
                            r7 = r10
                            androidx.compose.runtime.ComposerImpl r7 = (androidx.compose.runtime.ComposerImpl) r7
                            r10 = -275382062(0xffffffffef9600d2, float:-9.284749E28)
                            r7.startReplaceGroup(r10)
                            boolean r10 = r7.changedInstance(r9)
                            java.lang.Object r11 = r7.rememberedValue()
                            if (r10 != 0) goto L4f
                            androidx.compose.runtime.Composer$Companion r10 = androidx.compose.runtime.Composer.Companion
                            r10.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r10 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r11 != r10) goto L57
                        L4f:
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1$1$1$1 r11 = new com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1$1$1$1
                            r11.<init>(r9)
                            r7.updateRememberedValue(r11)
                        L57:
                            kotlin.reflect.KFunction r11 = (kotlin.reflect.KFunction) r11
                            r10 = 0
                            r7.end(r10)
                            r6 = r11
                            kotlin.jvm.functions.Function0 r6 = (kotlin.jvm.functions.Function0) r6
                            com.android.systemui.touchpad.tutorial.ui.viewmodel.SwitchAppsGestureScreenViewModel r4 = r9.switchAppsGestureScreenViewModel
                            com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel r5 = r9.easterEggGestureViewModel
                            com.android.systemui.touchpad.tutorial.ui.viewmodel.BackGestureScreenViewModel r1 = r9.backGestureViewModel
                            com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureScreenViewModel r2 = r9.homeGestureViewModel
                            com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureScreenViewModel r3 = r9.recentAppsGestureViewModel
                            r8 = 0
                            com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt.TouchpadTutorialScreen(r0, r1, r2, r3, r4, r5, r6, r7, r8)
                            boolean r9 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r9 == 0) goto L77
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L77:
                            kotlin.Unit r9 = kotlin.Unit.INSTANCE
                            return r9
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivity$onCreate$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }, composer), composer, 48, 1);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
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
        LogMessage obtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) obtain).str1 = tutorialContext.getString();
        logBuffer.commit(obtain);
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
