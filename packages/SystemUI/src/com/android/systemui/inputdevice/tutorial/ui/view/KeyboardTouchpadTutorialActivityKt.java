package com.android.systemui.inputdevice.tutorial.ui.view;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.compose.FlowExtKt;
import com.android.systemui.inputdevice.tutorial.TouchpadTutorialScreensProvider;
import com.android.systemui.inputdevice.tutorial.ui.composable.ActionKeyTutorialScreenKt;
import com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel;
import com.android.systemui.inputdevice.tutorial.ui.viewmodel.Screen;
import com.android.systemui.touchpad.tutorial.ScreensProvider;
import com.android.systemui.touchpad.tutorial.ui.composable.BackGestureTutorialScreenKt;
import com.android.systemui.touchpad.tutorial.ui.composable.HomeGestureTutorialScreenKt;
import java.util.Optional;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KFunction;

/* loaded from: classes2.dex */
public abstract class KeyboardTouchpadTutorialActivityKt {
    /* JADX WARN: Removed duplicated region for block: B:33:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0230  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void KeyboardTouchpadTutorialContainer(final KeyboardTouchpadTutorialViewModel keyboardTouchpadTutorialViewModel, final Optional optional, final boolean z, final boolean z2, Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1246816741);
        if ((((composerImpl.changedInstance(keyboardTouchpadTutorialViewModel) ? 4 : 2) | i | (composerImpl.changedInstance(optional) ? 32 : 16) | (composerImpl.changed(z) ? 256 : 128) | (composerImpl.changed(z2) ? 2048 : 1024)) & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialContainer (KeyboardTouchpadTutorialActivity.kt:140)");
            }
            Enum r13 = (Enum) FlowExtKt.collectAsStateWithLifecycle(keyboardTouchpadTutorialViewModel.screen, Lifecycle.State.STARTED, composerImpl, 48).getValue();
            Screen screen = Screen.BACK_GESTURE;
            KFunction kFunction = null;
            Composer.Companion companion = Composer.Companion;
            if (r13 == screen) {
                composerImpl.startReplaceGroup(-1934238508);
                TouchpadTutorialScreensProvider touchpadTutorialScreensProvider = (TouchpadTutorialScreensProvider) optional.get();
                composerImpl.startReplaceGroup(-1934236947);
                boolean zChangedInstance = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                Object objRememberedValue = composerImpl.rememberedValue();
                if (!zChangedInstance) {
                    companion.getClass();
                    if (objRememberedValue == Composer.Companion.Empty) {
                        objRememberedValue = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$1$1(keyboardTouchpadTutorialViewModel);
                        composerImpl.updateRememberedValue(objRememberedValue);
                    }
                    composerImpl.end(false);
                    Function0 function0 = (Function0) ((KFunction) objRememberedValue);
                    composerImpl.startReplaceGroup(-1934235232);
                    boolean zChangedInstance2 = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                    Object objRememberedValue2 = composerImpl.rememberedValue();
                    if (!zChangedInstance2) {
                        companion.getClass();
                        if (objRememberedValue2 == Composer.Companion.Empty) {
                            objRememberedValue2 = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$2$1(keyboardTouchpadTutorialViewModel);
                            composerImpl.updateRememberedValue(objRememberedValue2);
                        }
                        composerImpl.end(false);
                        Function0 function02 = (Function0) ((KFunction) objRememberedValue2);
                        composerImpl.startReplaceGroup(-1934233660);
                        if (z) {
                            composerImpl.startReplaceGroup(-1934233081);
                            boolean zChangedInstance3 = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                            Object objRememberedValue3 = composerImpl.rememberedValue();
                            if (!zChangedInstance3) {
                                companion.getClass();
                                if (objRememberedValue3 == Composer.Companion.Empty) {
                                    objRememberedValue3 = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$3$1(keyboardTouchpadTutorialViewModel);
                                    composerImpl.updateRememberedValue(objRememberedValue3);
                                }
                                kFunction = (KFunction) objRememberedValue3;
                                composerImpl.end(false);
                            }
                        }
                        composerImpl.end(false);
                        Function1 function1 = (Function1) kFunction;
                        ScreensProvider screensProvider = (ScreensProvider) touchpadTutorialScreensProvider;
                        screensProvider.getClass();
                        composerImpl.startReplaceGroup(-668522249);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ScreensProvider.BackGesture (TouchpadTutorialModule.kt:136)");
                        }
                        BackGestureTutorialScreenKt.BackGestureTutorialScreen(screensProvider.backGestureScreenViewModel, screensProvider.easterEggGestureViewModel, function0, function02, function1, composerImpl, 0, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl.end(false);
                        composerImpl.end(false);
                    }
                }
            } else if (r13 == Screen.HOME_GESTURE) {
                composerImpl.startReplaceGroup(-1934228431);
                TouchpadTutorialScreensProvider touchpadTutorialScreensProvider2 = (TouchpadTutorialScreensProvider) optional.get();
                composerImpl.startReplaceGroup(-1934226867);
                boolean zChangedInstance4 = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                Object objRememberedValue4 = composerImpl.rememberedValue();
                if (!zChangedInstance4) {
                    companion.getClass();
                    if (objRememberedValue4 == Composer.Companion.Empty) {
                        objRememberedValue4 = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$4$1(keyboardTouchpadTutorialViewModel);
                        composerImpl.updateRememberedValue(objRememberedValue4);
                    }
                    composerImpl.end(false);
                    Function0 function03 = (Function0) ((KFunction) objRememberedValue4);
                    composerImpl.startReplaceGroup(-1934225152);
                    boolean zChangedInstance5 = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                    Object objRememberedValue5 = composerImpl.rememberedValue();
                    if (!zChangedInstance5) {
                        companion.getClass();
                        if (objRememberedValue5 == Composer.Companion.Empty) {
                            objRememberedValue5 = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$5$1(keyboardTouchpadTutorialViewModel);
                            composerImpl.updateRememberedValue(objRememberedValue5);
                        }
                        composerImpl.end(false);
                        Function0 function04 = (Function0) ((KFunction) objRememberedValue5);
                        composerImpl.startReplaceGroup(-1934223583);
                        if (z2) {
                            composerImpl.startReplaceGroup(-1934223097);
                            boolean zChangedInstance6 = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                            Object objRememberedValue6 = composerImpl.rememberedValue();
                            if (!zChangedInstance6) {
                                companion.getClass();
                                if (objRememberedValue6 == Composer.Companion.Empty) {
                                    objRememberedValue6 = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$6$1(keyboardTouchpadTutorialViewModel);
                                    composerImpl.updateRememberedValue(objRememberedValue6);
                                }
                                kFunction = (KFunction) objRememberedValue6;
                                composerImpl.end(false);
                            }
                        }
                        composerImpl.end(false);
                        Function1 function12 = (Function1) kFunction;
                        ScreensProvider screensProvider2 = (ScreensProvider) touchpadTutorialScreensProvider2;
                        screensProvider2.getClass();
                        composerImpl.startReplaceGroup(-623204353);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("com.android.systemui.touchpad.tutorial.ScreensProvider.HomeGesture (TouchpadTutorialModule.kt:151)");
                        }
                        HomeGestureTutorialScreenKt.HomeGestureTutorialScreen(screensProvider2.homeGestureScreenViewModel, screensProvider2.easterEggGestureViewModel, function03, function04, function12, composerImpl, 0, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl.end(false);
                        composerImpl.end(false);
                    }
                }
            } else if (r13 == Screen.ACTION_KEY) {
                composerImpl.startReplaceGroup(-1934220384);
                composerImpl.startReplaceGroup(-1934218483);
                boolean zChangedInstance7 = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                Object objRememberedValue7 = composerImpl.rememberedValue();
                if (!zChangedInstance7) {
                    companion.getClass();
                    if (objRememberedValue7 == Composer.Companion.Empty) {
                        objRememberedValue7 = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$7$1(keyboardTouchpadTutorialViewModel);
                        composerImpl.updateRememberedValue(objRememberedValue7);
                    }
                    composerImpl.end(false);
                    Function0 function05 = (Function0) ((KFunction) objRememberedValue7);
                    composerImpl.startReplaceGroup(-1934216896);
                    boolean zChangedInstance8 = composerImpl.changedInstance(keyboardTouchpadTutorialViewModel);
                    Object objRememberedValue8 = composerImpl.rememberedValue();
                    if (!zChangedInstance8) {
                        companion.getClass();
                        if (objRememberedValue8 == Composer.Companion.Empty) {
                            objRememberedValue8 = new KeyboardTouchpadTutorialActivityKt$KeyboardTouchpadTutorialContainer$8$1(keyboardTouchpadTutorialViewModel);
                            composerImpl.updateRememberedValue(objRememberedValue8);
                        }
                        composerImpl.end(false);
                        ActionKeyTutorialScreenKt.ActionKeyTutorialScreen(function05, (Function0) ((KFunction) objRememberedValue8), composerImpl, 0);
                        composerImpl.end(false);
                    }
                }
            } else {
                composerImpl.startReplaceGroup(168848812);
                composerImpl.end(false);
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(optional, z, z2, i) { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivityKt$$ExternalSyntheticLambda0
                public final /* synthetic */ Optional f$1;
                public final /* synthetic */ boolean f$2;
                public final /* synthetic */ boolean f$3;

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    boolean z3 = this.f$2;
                    boolean z4 = this.f$3;
                    KeyboardTouchpadTutorialActivityKt.KeyboardTouchpadTutorialContainer(this.f$0, this.f$1, z3, z4, (Composer) obj, iUpdateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
