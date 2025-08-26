package com.android.systemui.communal.widgets;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.lifecycle.LifecycleKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.communal.ui.compose.CommunalHubKt;
import com.android.systemui.communal.ui.view.layout.sections.CommunalAppWidgetSection;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import com.android.systemui.communal.util.WidgetPickerIntentUtils;
import com.android.systemui.communal.widgets.WidgetConfigurationController;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.settings.UserTracker;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* loaded from: classes2.dex */
public final class EditWidgetsActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityControllerImpl activityController;
    public final CommunalEditModeViewModel communalViewModel;
    public final KeyguardInteractor keyguardInteractor;
    public final Logger logger;
    public boolean shouldOpenWidgetPickerOnStart;
    public final UiEventLogger uiEventLogger;
    public final UserTracker userTracker;
    public final Lazy widgetConfigurator$delegate;
    public final WidgetConfigurationController.Factory widgetConfiguratorFactory;
    public final CommunalAppWidgetSection widgetSection;
    public final IWindowManager windowManagerService;

    public final class ActivityControllerImpl {
        public boolean activityFullyVisible;
        public boolean waitingForResult;

        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            new Companion(null);
        }

        public ActivityControllerImpl(Activity activity) {
            activity.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity.ActivityControllerImpl.1
                @Override // android.app.Application.ActivityLifecycleCallbacks
                public final void onActivityCreated(Activity activity2, Bundle bundle) {
                    ActivityControllerImpl.this.waitingForResult = bundle != null ? bundle.getBoolean("extra_is_waiting_for_result") : false;
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public final void onActivitySaveInstanceState(Activity activity2, Bundle bundle) {
                    bundle.putBoolean("extra_is_waiting_for_result", ActivityControllerImpl.this.waitingForResult);
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public final void onActivityStopped(Activity activity2) {
                    ActivityControllerImpl activityControllerImpl = ActivityControllerImpl.this;
                    if (activityControllerImpl.waitingForResult || !activityControllerImpl.activityFullyVisible || activity2.isChangingConfigurations()) {
                        return;
                    }
                    activity2.finish();
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public final void onActivityDestroyed(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public final void onActivityPaused(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public final void onActivityResumed(Activity activity2) {
                }

                @Override // android.app.Application.ActivityLifecycleCallbacks
                public final void onActivityStarted(Activity activity2) {
                }
            });
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ EditWidgetsActivity(CommunalEditModeViewModel communalEditModeViewModel, KeyguardInteractor keyguardInteractor, IWindowManager iWindowManager, UiEventLogger uiEventLogger, WidgetConfigurationController.Factory factory, CommunalAppWidgetSection communalAppWidgetSection, UserTracker userTracker, LogBuffer logBuffer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(communalEditModeViewModel, keyguardInteractor, (i & 4) != 0 ? null : iWindowManager, uiEventLogger, factory, communalAppWidgetSection, userTracker, logBuffer);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        this.activityController.waitingForResult = false;
        super.onActivityResult(i, i2, intent);
        Lazy lazy = this.widgetConfigurator$delegate;
        if (i == 100) {
            ((WidgetConfigurationController) lazy.getValue()).setConfigurationResult(i2);
            return;
        }
        if (i != 200) {
            return;
        }
        if (i2 != -1) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i2, "Failed to receive result from widget picker, code=", "EditWidgetsActivity");
            return;
        }
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_WIDGET_PICKER_SHOWN);
        if (intent == null) {
            Log.w("EditWidgetsActivity", "No data in result.");
            return;
        }
        if (intent.getBooleanExtra("is_pending_widget_drag", false)) {
            return;
        }
        WidgetPickerIntentUtils.INSTANCE.getClass();
        WidgetPickerIntentUtils.WidgetExtra widgetExtra = new WidgetPickerIntentUtils.WidgetExtra((ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME", ComponentName.class), (UserHandle) intent.getParcelableExtra("android.intent.extra.USER", UserHandle.class));
        ComponentName componentName = widgetExtra.componentName;
        UserHandle userHandle = widgetExtra.user;
        if (componentName == null || userHandle == null) {
            Log.w("EditWidgetsActivity", "No AppWidgetProviderInfo found in result.");
        } else {
            this.communalViewModel.onAddWidget(componentName, userHandle, null, (WidgetConfigurationController) lazy.getValue());
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(this.lifecycleRegistry), null, null, new EditWidgetsActivity$listenForTransitionAndChangeScene$1(this, null), 7);
        this.activityController.activityFullyVisible = false;
        this.communalViewModel.communalInteractor._editModeOpen.updateState(null, Boolean.TRUE);
        WindowInsetsController windowInsetsController = getWindow().getDecorView().getWindowInsetsController();
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsets.Type.systemBars());
        }
        getWindow().setDecorFitsSystemWindows(false);
        this.shouldOpenWidgetPickerOnStart = getIntent().getBooleanExtra("open_widget_picker_on_start", false);
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(-664677188, true, new Function2() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity.onCreate.1
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
                            ComposerKt.traceEventStart("com.android.systemui.communal.widgets.EditWidgetsActivity.onCreate.<anonymous> (EditWidgetsActivity.kt:194)");
                        }
                        final EditWidgetsActivity editWidgetsActivity = EditWidgetsActivity.this;
                        PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-700058190, new Function2() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity.onCreate.1.1
                            /* JADX WARN: Removed duplicated region for block: B:26:0x00ba  */
                            /* JADX WARN: Removed duplicated region for block: B:31:0x00e7  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
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
                                            ComposerKt.traceEventStart("com.android.systemui.communal.widgets.EditWidgetsActivity.onCreate.<anonymous>.<anonymous> (EditWidgetsActivity.kt:195)");
                                        }
                                        Modifier modifierFillMaxSize = SizeKt.fillMaxSize(Modifier.Companion, 1.0f);
                                        MaterialTheme.INSTANCE.getClass();
                                        Modifier modifierM26backgroundbw27NRU = BackgroundKt.m26backgroundbw27NRU(modifierFillMaxSize, MaterialTheme.getColorScheme(composer2).surfaceDim, RectangleShapeKt.RectangleShape);
                                        Alignment.Companion.getClass();
                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierM26backgroundbw27NRU);
                                        ComposeUiNode.Companion.getClass();
                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                        if (composerImpl3.applier == null) {
                                            ComposablesKt.invalidApplier();
                                            throw null;
                                        }
                                        composerImpl3.startReusableNode();
                                        if (composerImpl3.inserting) {
                                            composerImpl3.createNode(function0);
                                        } else {
                                            composerImpl3.useNode();
                                        }
                                        Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                        Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function2);
                                        }
                                        Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                        EditWidgetsActivity editWidgetsActivity2 = editWidgetsActivity;
                                        CommunalEditModeViewModel communalEditModeViewModel = editWidgetsActivity2.communalViewModel;
                                        composerImpl3.startReplaceGroup(49112580);
                                        boolean zChangedInstance = composerImpl3.changedInstance(editWidgetsActivity2);
                                        Object objRememberedValue = composerImpl3.rememberedValue();
                                        Composer.Companion companion = Composer.Companion;
                                        if (!zChangedInstance) {
                                            companion.getClass();
                                            if (objRememberedValue == Composer.Companion.Empty) {
                                                objRememberedValue = new EditWidgetsActivity$onCreate$1$1$1$1$1(editWidgetsActivity2);
                                                composerImpl3.updateRememberedValue(objRememberedValue);
                                            }
                                            KFunction kFunction = (KFunction) objRememberedValue;
                                            composerImpl3.end(false);
                                            WidgetConfigurationController widgetConfigurationController = (WidgetConfigurationController) editWidgetsActivity2.widgetConfigurator$delegate.getValue();
                                            composerImpl3.startReplaceGroup(49116540);
                                            boolean zChangedInstance2 = composerImpl3.changedInstance(editWidgetsActivity2);
                                            Object objRememberedValue2 = composerImpl3.rememberedValue();
                                            if (!zChangedInstance2) {
                                                companion.getClass();
                                                if (objRememberedValue2 == Composer.Companion.Empty) {
                                                    objRememberedValue2 = new EditWidgetsActivity$onCreate$1$1$1$2$1(editWidgetsActivity2);
                                                    composerImpl3.updateRememberedValue(objRememberedValue2);
                                                }
                                                composerImpl3.end(false);
                                                CommunalHubKt.CommunalHub(null, communalEditModeViewModel, editWidgetsActivity2.widgetSection, null, null, widgetConfigurationController, (Function0) kFunction, (Function0) ((KFunction) objRememberedValue2), null, composer2, 0, IKnoxCustomManager.Stub.TRANSACTION_setBootingAnimationSub);
                                                composerImpl3.end(true);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
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
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        CommunalEditModeViewModel communalEditModeViewModel = this.communalViewModel;
        communalEditModeViewModel.communalSceneInteractor._editModeState.setValue(null);
        int i = communalEditModeViewModel.currentScrollIndex;
        int i2 = communalEditModeViewModel.currentScrollOffset;
        CommunalInteractor communalInteractor = ((BaseCommunalViewModel) communalEditModeViewModel).communalInteractor;
        communalInteractor._firstVisibleItemIndex = i;
        communalInteractor._firstVisibleItemOffset = i2;
        communalEditModeViewModel.communalInteractor._editModeOpen.updateState(null, Boolean.FALSE);
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        this.communalViewModel.communalInteractor._editActivityShowing.updateState(null, Boolean.TRUE);
        Logger.i$default(this.logger, "Starting the communal widget editor activity", null, 2, null);
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_EDIT_MODE_SHOWN);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        this.communalViewModel.communalInteractor._editActivityShowing.updateState(null, Boolean.FALSE);
        Logger.i$default(this.logger, "Stopping the communal widget editor activity", null, 2, null);
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_EDIT_MODE_GONE);
    }

    @Override // android.app.Activity
    public final void startActivityForResult(Intent intent, int i, Bundle bundle) {
        this.activityController.waitingForResult = true;
        super.startActivityForResult(intent, i, bundle);
    }

    @Override // android.app.Activity
    public final void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        this.activityController.waitingForResult = true;
        super.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }

    public EditWidgetsActivity(CommunalEditModeViewModel communalEditModeViewModel, KeyguardInteractor keyguardInteractor, IWindowManager iWindowManager, UiEventLogger uiEventLogger, WidgetConfigurationController.Factory factory, CommunalAppWidgetSection communalAppWidgetSection, UserTracker userTracker, LogBuffer logBuffer) {
        this.communalViewModel = communalEditModeViewModel;
        this.keyguardInteractor = keyguardInteractor;
        this.windowManagerService = iWindowManager;
        this.uiEventLogger = uiEventLogger;
        this.widgetConfiguratorFactory = factory;
        this.widgetSection = communalAppWidgetSection;
        this.userTracker = userTracker;
        this.logger = new Logger(logBuffer, "EditWidgetsActivity");
        this.widgetConfigurator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                EditWidgetsActivity editWidgetsActivity = this.f$0;
                return editWidgetsActivity.widgetConfiguratorFactory.create(editWidgetsActivity);
            }
        });
        this.activityController = new ActivityControllerImpl(this);
    }
}
