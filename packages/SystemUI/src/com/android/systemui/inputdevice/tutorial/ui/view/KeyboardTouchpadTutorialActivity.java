package com.android.systemui.inputdevice.tutorial.ui.view;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger$$ExternalSyntheticLambda0;
import com.android.systemui.inputdevice.tutorial.KeyboardTouchpadTutorialMetricsLogger;
import com.android.systemui.inputdevice.tutorial.TouchpadTutorialScreensProvider;
import com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor;
import com.android.systemui.inputdevice.tutorial.ui.viewmodel.KeyboardTouchpadTutorialViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Optional;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes2.dex */
public final class KeyboardTouchpadTutorialActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InputDeviceTutorialLogger logger;
    public final KeyboardTouchpadTutorialMetricsLogger metricsLogger;
    public final TutorialSchedulerInteractor schedulerInteractor;
    public final Optional touchpadTutorialScreensProvider;
    public final KeyboardTouchpadTutorialViewModel.Factory.ViewModelFactoryAssistedProvider viewModelFactoryAssistedProvider;
    public final ViewModelLazy vm$delegate;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity$onCreate$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadTutorialActivity.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity = KeyboardTouchpadTutorialActivity.this;
                int i2 = KeyboardTouchpadTutorialActivity.$r8$clinit;
                StateFlowImpl stateFlowImpl = ((KeyboardTouchpadTutorialViewModel) keyboardTouchpadTutorialActivity.vm$delegate.getValue()).closeActivity;
                final KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity2 = KeyboardTouchpadTutorialActivity.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity.onCreate.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (((Boolean) obj2).booleanValue()) {
                            KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity3 = keyboardTouchpadTutorialActivity2;
                            InputDeviceTutorialLogger inputDeviceTutorialLogger = keyboardTouchpadTutorialActivity3.logger;
                            InputDeviceTutorialLogger.TutorialContext tutorialContext = InputDeviceTutorialLogger.TutorialContext.KEYBOARD_TOUCHPAD_TUTORIAL;
                            inputDeviceTutorialLogger.getClass();
                            InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(3);
                            LogLevel logLevel = LogLevel.INFO;
                            LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
                            LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) logMessageObtain).str1 = tutorialContext.getString();
                            logBuffer.commit(logMessageObtain);
                            keyboardTouchpadTutorialActivity3.finish();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlowImpl.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    static {
        new Companion(null);
    }

    public KeyboardTouchpadTutorialActivity(KeyboardTouchpadTutorialViewModel.Factory.ViewModelFactoryAssistedProvider viewModelFactoryAssistedProvider, Optional<TouchpadTutorialScreensProvider> optional, TutorialSchedulerInteractor tutorialSchedulerInteractor, InputDeviceTutorialLogger inputDeviceTutorialLogger, KeyboardTouchpadTutorialMetricsLogger keyboardTouchpadTutorialMetricsLogger) {
        this.viewModelFactoryAssistedProvider = viewModelFactoryAssistedProvider;
        this.touchpadTutorialScreensProvider = optional;
        this.schedulerInteractor = tutorialSchedulerInteractor;
        this.logger = inputDeviceTutorialLogger;
        this.metricsLogger = keyboardTouchpadTutorialMetricsLogger;
        final Function0 function0 = null;
        this.vm$delegate = new ViewModelLazy(Reflection.getOrCreateKotlinClass(KeyboardTouchpadTutorialViewModel.class), new Function0() { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.getViewModelStore();
            }
        }, new Function0() { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity = this.f$0;
                return keyboardTouchpadTutorialActivity.viewModelFactoryAssistedProvider.create(keyboardTouchpadTutorialActivity.touchpadTutorialScreensProvider.isPresent());
            }
        }, new Function0() { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity$special$$inlined$viewModels$default$3
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

    /* JADX WARN: Removed duplicated region for block: B:44:0x0105  */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        TutorialSchedulerInteractor.TutorialType tutorialType;
        super.onCreate(bundle);
        EdgeToEdge.enable$default(this);
        getWindow().addPrivateFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        getWindow().addPrivateFlags(8388608);
        LifecycleRegistry lifecycleRegistry = this.lifecycleRegistry;
        lifecycleRegistry.addObserver((KeyboardTouchpadTutorialViewModel) this.vm$delegate.getValue());
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(lifecycleRegistry), null, null, new AnonymousClass1(null), 7);
        String stringExtra = getIntent().getStringExtra("entry_point");
        final boolean zEquals = stringExtra == null ? true : stringExtra.equals("scheduler");
        final boolean zEquals2 = SystemUIAnalytics.QPNE_VID_COVER_ALL.equals(getIntent().getStringExtra("tutorial_scope"));
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(1102336700, true, new Function2() { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity.onCreate.2
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
                            ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity.onCreate.<anonymous> (KeyboardTouchpadTutorialActivity.kt:102)");
                        }
                        final KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity = KeyboardTouchpadTutorialActivity.this;
                        final boolean z = zEquals;
                        final boolean z2 = zEquals2;
                        PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-93451470, new Function2() { // from class: com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity.onCreate.2.1
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
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
                                            ComposerKt.traceEventStart("com.android.systemui.inputdevice.tutorial.ui.view.KeyboardTouchpadTutorialActivity.onCreate.<anonymous>.<anonymous> (KeyboardTouchpadTutorialActivity.kt:103)");
                                        }
                                        int i = KeyboardTouchpadTutorialActivity.$r8$clinit;
                                        KeyboardTouchpadTutorialActivity keyboardTouchpadTutorialActivity2 = keyboardTouchpadTutorialActivity;
                                        KeyboardTouchpadTutorialActivityKt.KeyboardTouchpadTutorialContainer((KeyboardTouchpadTutorialViewModel) keyboardTouchpadTutorialActivity2.vm$delegate.getValue(), keyboardTouchpadTutorialActivity2.touchpadTutorialScreensProvider, z, z2, composer2, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
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
        if (bundle == null) {
            InputDeviceTutorialLogger.TutorialContext tutorialContext = InputDeviceTutorialLogger.TutorialContext.KEYBOARD_TOUCHPAD_TUTORIAL;
            InputDeviceTutorialLogger inputDeviceTutorialLogger = this.logger;
            inputDeviceTutorialLogger.getClass();
            InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(1);
            LogLevel logLevel = LogLevel.INFO;
            LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = tutorialContext.getString();
            logBuffer.commit(logMessageObtain);
            String stringExtra2 = getIntent().getStringExtra("tutorial_scope");
            this.metricsLogger.getClass();
            SysUiStatsLog.write(942, Intrinsics.areEqual(stringExtra, "scheduler") ? 2 : Intrinsics.areEqual(stringExtra, "contextual_edu") ? 3 : 4, Intrinsics.areEqual(stringExtra2, "keyboard") ? 1 : Intrinsics.areEqual(stringExtra2, "touchpad") ? 2 : 3);
            if ("scheduler".equals(stringExtra)) {
                if (stringExtra2 == null) {
                    tutorialType = TutorialSchedulerInteractor.TutorialType.NONE;
                } else {
                    int iHashCode = stringExtra2.hashCode();
                    if (iHashCode != -819522316) {
                        if (iHashCode != 96673) {
                            if (iHashCode == 503739367 && stringExtra2.equals("keyboard")) {
                                tutorialType = TutorialSchedulerInteractor.TutorialType.KEYBOARD;
                            }
                        } else if (stringExtra2.equals(SystemUIAnalytics.QPNE_VID_COVER_ALL)) {
                            tutorialType = TutorialSchedulerInteractor.TutorialType.BOTH;
                        }
                    } else if (stringExtra2.equals("touchpad")) {
                        tutorialType = TutorialSchedulerInteractor.TutorialType.TOUCHPAD;
                    }
                }
                this.schedulerInteractor.updateLaunchInfo(tutorialType);
            }
        }
    }
}
