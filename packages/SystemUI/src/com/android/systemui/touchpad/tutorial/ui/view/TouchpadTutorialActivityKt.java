package com.android.systemui.touchpad.tutorial.ui.view;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.touchpad.tutorial.ui.composable.BackGestureTutorialScreenKt;
import com.android.systemui.touchpad.tutorial.ui.composable.HomeGestureTutorialScreenKt;
import com.android.systemui.touchpad.tutorial.ui.composable.RecentAppsGestureTutorialScreenKt;
import com.android.systemui.touchpad.tutorial.ui.composable.SwitchAppsGestureTutorialScreenKt;
import com.android.systemui.touchpad.tutorial.ui.composable.TutorialSelectionScreenKt;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.BackGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.EasterEggGestureViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.HomeGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.RecentAppsGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.Screen;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.SwitchAppsGestureScreenViewModel;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.TouchpadTutorialViewModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class TouchpadTutorialActivityKt {
    public static final void TouchpadTutorialScreen(final TouchpadTutorialViewModel touchpadTutorialViewModel, final BackGestureScreenViewModel backGestureScreenViewModel, final HomeGestureScreenViewModel homeGestureScreenViewModel, final RecentAppsGestureScreenViewModel recentAppsGestureScreenViewModel, final SwitchAppsGestureScreenViewModel switchAppsGestureScreenViewModel, final EasterEggGestureViewModel easterEggGestureViewModel, final Function0 function0, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-804486198);
        int i2 = i | (composerImpl.changedInstance(touchpadTutorialViewModel) ? 4 : 2) | (composerImpl.changedInstance(backGestureScreenViewModel) ? 32 : 16) | (composerImpl.changedInstance(homeGestureScreenViewModel) ? 256 : 128) | (composerImpl.changedInstance(recentAppsGestureScreenViewModel) ? 2048 : 1024) | (composerImpl.changedInstance(switchAppsGestureScreenViewModel) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192) | (composerImpl.changedInstance(easterEggGestureViewModel) ? 131072 : 65536) | (composerImpl.changedInstance(function0) ? 1048576 : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        if ((599187 & i2) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialScreen (TouchpadTutorialActivity.kt:118)");
            }
            MutableState collectAsStateWithLifecycle = FlowExtKt.collectAsStateWithLifecycle(touchpadTutorialViewModel.screen, Lifecycle.State.STARTED, composerImpl, 48);
            composerImpl.startReplaceGroup(-757657423);
            Object rememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = SnapshotStateKt.mutableStateOf$default(Screen.TUTORIAL_SELECTION);
                composerImpl.updateRememberedValue(rememberedValue);
            }
            final MutableState mutableState = (MutableState) rememberedValue;
            composerImpl.end(false);
            Enum r2 = (Enum) collectAsStateWithLifecycle.getValue();
            if (r2 == Screen.TUTORIAL_SELECTION) {
                composerImpl.startReplaceGroup(-757652963);
                composerImpl.startReplaceGroup(-757651563);
                boolean changedInstance = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue2 = composerImpl.rememberedValue();
                if (changedInstance || rememberedValue2 == composer$Companion$Empty$1) {
                    final int i3 = 0;
                    rememberedValue2 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i3) {
                                case 0:
                                    Screen screen = Screen.BACK_GESTURE;
                                    mutableState.setValue(screen);
                                    touchpadTutorialViewModel.goTo(screen);
                                    break;
                                case 1:
                                    Screen screen2 = Screen.HOME_GESTURE;
                                    mutableState.setValue(screen2);
                                    touchpadTutorialViewModel.goTo(screen2);
                                    break;
                                case 2:
                                    Screen screen3 = Screen.RECENT_APPS_GESTURE;
                                    mutableState.setValue(screen3);
                                    touchpadTutorialViewModel.goTo(screen3);
                                    break;
                                default:
                                    Screen screen4 = Screen.SWITCH_APPS_GESTURE;
                                    mutableState.setValue(screen4);
                                    touchpadTutorialViewModel.goTo(screen4);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue2);
                }
                Function0 function02 = (Function0) rememberedValue2;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-757646539);
                boolean changedInstance2 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue3 = composerImpl.rememberedValue();
                if (changedInstance2 || rememberedValue3 == composer$Companion$Empty$1) {
                    final int i4 = 1;
                    rememberedValue3 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i4) {
                                case 0:
                                    Screen screen = Screen.BACK_GESTURE;
                                    mutableState.setValue(screen);
                                    touchpadTutorialViewModel.goTo(screen);
                                    break;
                                case 1:
                                    Screen screen2 = Screen.HOME_GESTURE;
                                    mutableState.setValue(screen2);
                                    touchpadTutorialViewModel.goTo(screen2);
                                    break;
                                case 2:
                                    Screen screen3 = Screen.RECENT_APPS_GESTURE;
                                    mutableState.setValue(screen3);
                                    touchpadTutorialViewModel.goTo(screen3);
                                    break;
                                default:
                                    Screen screen4 = Screen.SWITCH_APPS_GESTURE;
                                    mutableState.setValue(screen4);
                                    touchpadTutorialViewModel.goTo(screen4);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue3);
                }
                Function0 function03 = (Function0) rememberedValue3;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-757641309);
                boolean changedInstance3 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue4 = composerImpl.rememberedValue();
                if (changedInstance3 || rememberedValue4 == composer$Companion$Empty$1) {
                    final int i5 = 2;
                    rememberedValue4 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i5) {
                                case 0:
                                    Screen screen = Screen.BACK_GESTURE;
                                    mutableState.setValue(screen);
                                    touchpadTutorialViewModel.goTo(screen);
                                    break;
                                case 1:
                                    Screen screen2 = Screen.HOME_GESTURE;
                                    mutableState.setValue(screen2);
                                    touchpadTutorialViewModel.goTo(screen2);
                                    break;
                                case 2:
                                    Screen screen3 = Screen.RECENT_APPS_GESTURE;
                                    mutableState.setValue(screen3);
                                    touchpadTutorialViewModel.goTo(screen3);
                                    break;
                                default:
                                    Screen screen4 = Screen.SWITCH_APPS_GESTURE;
                                    mutableState.setValue(screen4);
                                    touchpadTutorialViewModel.goTo(screen4);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue4);
                }
                Function0 function04 = (Function0) rememberedValue4;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-757635645);
                boolean changedInstance4 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue5 = composerImpl.rememberedValue();
                if (changedInstance4 || rememberedValue5 == composer$Companion$Empty$1) {
                    final int i6 = 3;
                    rememberedValue5 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i6) {
                                case 0:
                                    Screen screen = Screen.BACK_GESTURE;
                                    mutableState.setValue(screen);
                                    touchpadTutorialViewModel.goTo(screen);
                                    break;
                                case 1:
                                    Screen screen2 = Screen.HOME_GESTURE;
                                    mutableState.setValue(screen2);
                                    touchpadTutorialViewModel.goTo(screen2);
                                    break;
                                case 2:
                                    Screen screen3 = Screen.RECENT_APPS_GESTURE;
                                    mutableState.setValue(screen3);
                                    touchpadTutorialViewModel.goTo(screen3);
                                    break;
                                default:
                                    Screen screen4 = Screen.SWITCH_APPS_GESTURE;
                                    mutableState.setValue(screen4);
                                    touchpadTutorialViewModel.goTo(screen4);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue5);
                }
                composerImpl.end(false);
                TutorialSelectionScreenKt.TutorialSelectionScreen(function02, function03, function04, (Function0) rememberedValue5, function0, (Screen) mutableState.getValue(), composerImpl, (i2 >> 6) & 57344);
                composerImpl = composerImpl;
                composerImpl.end(false);
            } else if (r2 == Screen.BACK_GESTURE) {
                composerImpl.startReplaceGroup(-757626884);
                composerImpl.startReplaceGroup(-757622431);
                boolean changedInstance5 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue6 = composerImpl.rememberedValue();
                if (changedInstance5 || rememberedValue6 == composer$Companion$Empty$1) {
                    final int i7 = 5;
                    rememberedValue6 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i7) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue6);
                }
                Function0 function05 = (Function0) rememberedValue6;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-757620575);
                boolean changedInstance6 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue7 = composerImpl.rememberedValue();
                if (changedInstance6 || rememberedValue7 == composer$Companion$Empty$1) {
                    final int i8 = 6;
                    rememberedValue7 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i8) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue7);
                }
                composerImpl.end(false);
                BackGestureTutorialScreenKt.BackGestureTutorialScreen(backGestureScreenViewModel, easterEggGestureViewModel, function05, (Function0) rememberedValue7, null, composerImpl, ((i2 >> 3) & 14) | ((i2 >> 12) & 112), 16);
                composerImpl.end(false);
            } else if (r2 == Screen.HOME_GESTURE) {
                composerImpl.startReplaceGroup(-757617700);
                composerImpl.startReplaceGroup(-757613247);
                boolean changedInstance7 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue8 = composerImpl.rememberedValue();
                if (changedInstance7 || rememberedValue8 == composer$Companion$Empty$1) {
                    final int i9 = 7;
                    rememberedValue8 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i9) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue8);
                }
                Function0 function06 = (Function0) rememberedValue8;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-757611391);
                boolean changedInstance8 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue9 = composerImpl.rememberedValue();
                if (changedInstance8 || rememberedValue9 == composer$Companion$Empty$1) {
                    final int i10 = 1;
                    rememberedValue9 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i10) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue9);
                }
                composerImpl.end(false);
                HomeGestureTutorialScreenKt.HomeGestureTutorialScreen(homeGestureScreenViewModel, easterEggGestureViewModel, function06, (Function0) rememberedValue9, null, composerImpl, ((i2 >> 6) & 14) | ((i2 >> 12) & 112), 16);
                composerImpl.end(false);
            } else if (r2 == Screen.RECENT_APPS_GESTURE) {
                composerImpl.startReplaceGroup(-757608280);
                composerImpl.startReplaceGroup(-757603455);
                boolean changedInstance9 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue10 = composerImpl.rememberedValue();
                if (changedInstance9 || rememberedValue10 == composer$Companion$Empty$1) {
                    final int i11 = 2;
                    rememberedValue10 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i11) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue10);
                }
                Function0 function07 = (Function0) rememberedValue10;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-757601599);
                boolean changedInstance10 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue11 = composerImpl.rememberedValue();
                if (changedInstance10 || rememberedValue11 == composer$Companion$Empty$1) {
                    final int i12 = 3;
                    rememberedValue11 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i12) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue11);
                }
                composerImpl.end(false);
                RecentAppsGestureTutorialScreenKt.RecentAppsGestureTutorialScreen(recentAppsGestureScreenViewModel, easterEggGestureViewModel, function07, (Function0) rememberedValue11, composerImpl, ((i2 >> 12) & 112) | ((i2 >> 9) & 14));
                composerImpl = composerImpl;
                composerImpl.end(false);
            } else if (r2 == Screen.SWITCH_APPS_GESTURE) {
                composerImpl.startReplaceGroup(-757598482);
                composerImpl.startReplaceGroup(-757593471);
                boolean changedInstance11 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue12 = composerImpl.rememberedValue();
                if (changedInstance11 || rememberedValue12 == composer$Companion$Empty$1) {
                    final int i13 = 0;
                    rememberedValue12 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i13) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue12);
                }
                Function0 function08 = (Function0) rememberedValue12;
                composerImpl.end(false);
                composerImpl.startReplaceGroup(-757591615);
                boolean changedInstance12 = composerImpl.changedInstance(touchpadTutorialViewModel);
                Object rememberedValue13 = composerImpl.rememberedValue();
                if (changedInstance12 || rememberedValue13 == composer$Companion$Empty$1) {
                    final int i14 = 4;
                    rememberedValue13 = new Function0() { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            switch (i14) {
                                case 0:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 1:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 2:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 3:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 4:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 5:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                case 6:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                                default:
                                    touchpadTutorialViewModel.goTo(Screen.TUTORIAL_SELECTION);
                                    break;
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    composerImpl.updateRememberedValue(rememberedValue13);
                }
                composerImpl.end(false);
                SwitchAppsGestureTutorialScreenKt.SwitchAppsGestureTutorialScreen(switchAppsGestureScreenViewModel, easterEggGestureViewModel, function08, (Function0) rememberedValue13, composerImpl, (i2 >> 12) & 126);
                composerImpl = composerImpl;
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-2010452960);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2(backGestureScreenViewModel, homeGestureScreenViewModel, recentAppsGestureScreenViewModel, switchAppsGestureScreenViewModel, easterEggGestureViewModel, function0, i) { // from class: com.android.systemui.touchpad.tutorial.ui.view.TouchpadTutorialActivityKt$$ExternalSyntheticLambda3
                public final /* synthetic */ BackGestureScreenViewModel f$1;
                public final /* synthetic */ HomeGestureScreenViewModel f$2;
                public final /* synthetic */ RecentAppsGestureScreenViewModel f$3;
                public final /* synthetic */ SwitchAppsGestureScreenViewModel f$4;
                public final /* synthetic */ EasterEggGestureViewModel f$5;
                public final /* synthetic */ Function0 f$6;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    EasterEggGestureViewModel easterEggGestureViewModel2 = this.f$5;
                    Function0 function09 = this.f$6;
                    TouchpadTutorialActivityKt.TouchpadTutorialScreen(TouchpadTutorialViewModel.this, this.f$1, this.f$2, this.f$3, this.f$4, easterEggGestureViewModel2, function09, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
