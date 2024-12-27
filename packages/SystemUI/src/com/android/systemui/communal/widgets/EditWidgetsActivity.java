package com.android.systemui.communal.widgets;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.ComponentActivityKt;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts$StartActivityForResult;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.FillElement;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
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
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.compose.theme.AndroidColorScheme;
import com.android.compose.theme.AndroidColorSchemeKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.communal.data.repository.CommunalWidgetRepositoryImpl;
import com.android.systemui.communal.shared.log.CommunalUiEvent;
import com.android.systemui.communal.ui.compose.CommunalHubKt;
import com.android.systemui.communal.ui.viewmodel.BaseCommunalViewModel;
import com.android.systemui.communal.ui.viewmodel.CommunalEditModeViewModel;
import com.android.systemui.communal.util.WidgetPickerIntentUtils;
import com.android.systemui.communal.widgets.WidgetConfigurationController;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import java.util.HashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CompletableDeferred;
import kotlinx.coroutines.CompletableDeferredImpl;

public final class EditWidgetsActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityResultRegistry.AnonymousClass2 addWidgetActivityLauncher;
    public final CommunalEditModeViewModel communalViewModel;
    public final Logger logger;
    public boolean shouldOpenWidgetPickerOnStart;
    public final UiEventLogger uiEventLogger;
    public final Lazy widgetConfigurator$delegate;
    public final WidgetConfigurationController.Factory widgetConfiguratorFactory;
    public final IWindowManager windowManagerService;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ EditWidgetsActivity(CommunalEditModeViewModel communalEditModeViewModel, IWindowManager iWindowManager, UiEventLogger uiEventLogger, WidgetConfigurationController.Factory factory, LogBuffer logBuffer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(communalEditModeViewModel, (i & 2) != 0 ? null : iWindowManager, uiEventLogger, factory, logBuffer);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        CompletableDeferred result;
        super.onActivityResult(i, i2, intent);
        if (i != 100 || (result = ((WidgetConfigurationController) this.widgetConfigurator$delegate.getValue()).getResult()) == null) {
            return;
        }
        ((CompletableDeferredImpl) result).makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Boolean.valueOf(i2 == -1));
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new EditWidgetsActivity$listenForTransitionAndChangeScene$1(this, null), 3);
        CommunalEditModeViewModel communalEditModeViewModel = this.communalViewModel;
        communalEditModeViewModel.communalInteractor._editModeOpen.updateState(null, Boolean.TRUE);
        WindowInsetsController windowInsetsController = getWindow().getDecorView().getWindowInsetsController();
        if (windowInsetsController != null) {
            windowInsetsController.hide(WindowInsets.Type.systemBars());
        }
        getWindow().setDecorFitsSystemWindows(false);
        String stringExtra = getIntent().getStringExtra("preselected_key");
        this.shouldOpenWidgetPickerOnStart = getIntent().getBooleanExtra("open_widget_picker_on_start", false);
        communalEditModeViewModel.setSelectedKey(stringExtra);
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(-664677188, true, new Function2() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity$onCreate$1
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.communal.widgets.EditWidgetsActivity$onCreate$1$1, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey = ComposerKt.invocation;
                final EditWidgetsActivity editWidgetsActivity = EditWidgetsActivity.this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-700058190, composer, new Function2() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity$onCreate$1.1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 11) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        Modifier.Companion companion = Modifier.Companion;
                        FillElement fillElement = SizeKt.FillWholeMaxSize;
                        companion.then(fillElement);
                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                        Modifier m24backgroundbw27NRU = BackgroundKt.m24backgroundbw27NRU(fillElement, ((AndroidColorScheme) composerImpl3.consume(AndroidColorSchemeKt.LocalAndroidColorScheme)).outlineVariant, RectangleShapeKt.RectangleShape);
                        EditWidgetsActivity editWidgetsActivity2 = EditWidgetsActivity.this;
                        Alignment.Companion.getClass();
                        MeasurePolicy maybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                        int i = composerImpl3.compoundKeyHash;
                        PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                        Modifier materializeModifier = ComposedModifierKt.materializeModifier(composerImpl3, m24backgroundbw27NRU);
                        ComposeUiNode.Companion.getClass();
                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                        if (!(composerImpl3.applier instanceof Applier)) {
                            ComposablesKt.invalidApplier();
                            throw null;
                        }
                        composerImpl3.startReusableNode();
                        if (composerImpl3.inserting) {
                            composerImpl3.createNode(function0);
                        } else {
                            composerImpl3.useNode();
                        }
                        Updater.m276setimpl(composerImpl3, maybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                        Updater.m276setimpl(composerImpl3, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
                        if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(i))) {
                            AnimatedContentKt$$ExternalSyntheticOutline0.m(i, composerImpl3, i, function2);
                        }
                        Updater.m276setimpl(composerImpl3, materializeModifier, ComposeUiNode.Companion.SetModifier);
                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                        CommunalHubKt.CommunalHub(null, editWidgetsActivity2.communalViewModel, null, null, (WidgetConfigurationController) editWidgetsActivity2.widgetConfigurator$delegate.getValue(), new EditWidgetsActivity$onCreate$1$1$1$1(editWidgetsActivity2), new EditWidgetsActivity$onCreate$1$1$1$2(editWidgetsActivity2), composerImpl3, 32832, 13);
                        composerImpl3.end(true);
                        return Unit.INSTANCE;
                    }
                }), composer, 48, 1);
                return Unit.INSTANCE;
            }
        }));
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        CommunalEditModeViewModel communalEditModeViewModel = this.communalViewModel;
        communalEditModeViewModel.communalSceneInteractor._editModeState.setValue(null);
        communalEditModeViewModel.communalInteractor._editModeOpen.updateState(null, Boolean.FALSE);
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        if (this.shouldOpenWidgetPickerOnStart) {
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new EditWidgetsActivity$onOpenWidgetPicker$1(this, null), 3);
            this.shouldOpenWidgetPickerOnStart = false;
        }
        Logger.i$default(this.logger, "Starting the communal widget editor activity", null, 2, null);
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_EDIT_MODE_SHOWN);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        Logger.i$default(this.logger, "Stopping the communal widget editor activity", null, 2, null);
        this.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_EDIT_MODE_GONE);
    }

    public EditWidgetsActivity(CommunalEditModeViewModel communalEditModeViewModel, IWindowManager iWindowManager, UiEventLogger uiEventLogger, WidgetConfigurationController.Factory factory, LogBuffer logBuffer) {
        this.communalViewModel = communalEditModeViewModel;
        this.windowManagerService = iWindowManager;
        this.uiEventLogger = uiEventLogger;
        this.widgetConfiguratorFactory = factory;
        this.logger = new Logger(logBuffer, "EditWidgetsActivity");
        this.widgetConfigurator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity$widgetConfigurator$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                EditWidgetsActivity editWidgetsActivity = EditWidgetsActivity.this;
                return editWidgetsActivity.widgetConfiguratorFactory.create(editWidgetsActivity);
            }
        });
        ActivityResultContracts$StartActivityForResult activityResultContracts$StartActivityForResult = new ActivityResultContracts$StartActivityForResult();
        ActivityResultCallback activityResultCallback = new ActivityResultCallback() { // from class: com.android.systemui.communal.widgets.EditWidgetsActivity$addWidgetActivityLauncher$1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ActivityResult activityResult = (ActivityResult) obj;
                int i = activityResult.mResultCode;
                if (i != -1) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "Failed to receive result from widget picker, code=", "EditWidgetsActivity");
                    return;
                }
                EditWidgetsActivity editWidgetsActivity = EditWidgetsActivity.this;
                editWidgetsActivity.uiEventLogger.log(CommunalUiEvent.COMMUNAL_HUB_WIDGET_PICKER_SHOWN);
                Intent intent = activityResult.mData;
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
                    ((CommunalWidgetRepositoryImpl) ((BaseCommunalViewModel) editWidgetsActivity.communalViewModel).communalInteractor.widgetRepository).addWidget(componentName, userHandle, 0, (WidgetConfigurationController) editWidgetsActivity.widgetConfigurator$delegate.getValue());
                }
            }
        };
        String str = "activity_rq#" + this.mNextLocalRequestCode.getAndIncrement();
        ComponentActivity.AnonymousClass1 anonymousClass1 = this.mActivityResultRegistry;
        anonymousClass1.getClass();
        LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
        if (lifecycleRegistry.state.isAtLeast(Lifecycle.State.STARTED)) {
            throw new IllegalStateException("LifecycleOwner " + this + " is attempting to register while current state is " + lifecycleRegistry.state + ". LifecycleOwners must call register before they are STARTED.");
        }
        anonymousClass1.registerKey(str);
        ActivityResultRegistry.LifecycleContainer lifecycleContainer = (ActivityResultRegistry.LifecycleContainer) ((HashMap) anonymousClass1.mKeyToLifecycleContainers).get(str);
        lifecycleContainer = lifecycleContainer == null ? new ActivityResultRegistry.LifecycleContainer(lifecycleRegistry) : lifecycleContainer;
        ActivityResultRegistry.AnonymousClass1 anonymousClass12 = new LifecycleEventObserver() { // from class: androidx.activity.result.ActivityResultRegistry.1
            public final /* synthetic */ ActivityResultCallback val$callback;
            public final /* synthetic */ ActivityResultContract val$contract;
            public final /* synthetic */ String val$key;

            public AnonymousClass1(String str2, ActivityResultCallback activityResultCallback2, ActivityResultContract activityResultContracts$StartActivityForResult2) {
                r2 = str2;
                r3 = activityResultCallback2;
                r4 = activityResultContracts$StartActivityForResult2;
            }

            @Override // androidx.lifecycle.LifecycleEventObserver
            public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
                boolean equals = Lifecycle.Event.ON_START.equals(event);
                String str2 = r2;
                ActivityResultRegistry activityResultRegistry = ActivityResultRegistry.this;
                if (!equals) {
                    if (Lifecycle.Event.ON_STOP.equals(event)) {
                        ((HashMap) activityResultRegistry.mKeyToCallback).remove(str2);
                        return;
                    } else {
                        if (Lifecycle.Event.ON_DESTROY.equals(event)) {
                            activityResultRegistry.unregister(str2);
                            return;
                        }
                        return;
                    }
                }
                Map map = activityResultRegistry.mKeyToCallback;
                ActivityResultCallback activityResultCallback2 = r3;
                ActivityResultContract activityResultContract = r4;
                ((HashMap) map).put(str2, new CallbackAndContract(activityResultCallback2, activityResultContract));
                if (((HashMap) activityResultRegistry.mParsedPendingResults).containsKey(str2)) {
                    Object obj = ((HashMap) activityResultRegistry.mParsedPendingResults).get(str2);
                    ((HashMap) activityResultRegistry.mParsedPendingResults).remove(str2);
                    activityResultCallback2.onActivityResult(obj);
                }
                ActivityResult activityResult = (ActivityResult) activityResultRegistry.mPendingResults.getParcelable(str2);
                if (activityResult != null) {
                    activityResultRegistry.mPendingResults.remove(str2);
                    activityResultCallback2.onActivityResult(activityResultContract.parseResult(activityResult.mResultCode, activityResult.mData));
                }
            }
        };
        lifecycleContainer.mLifecycle.addObserver(anonymousClass12);
        lifecycleContainer.mObservers.add(anonymousClass12);
        ((HashMap) anonymousClass1.mKeyToLifecycleContainers).put(str2, lifecycleContainer);
        this.addWidgetActivityLauncher = new ActivityResultLauncher() { // from class: androidx.activity.result.ActivityResultRegistry.2
            public final /* synthetic */ ActivityResultContract val$contract;
            public final /* synthetic */ String val$key;

            public AnonymousClass2(String str2, ActivityResultContract activityResultContracts$StartActivityForResult2) {
                r2 = str2;
                r3 = activityResultContracts$StartActivityForResult2;
            }

            @Override // androidx.activity.result.ActivityResultLauncher
            public final void launch(Object obj) {
                ActivityResultRegistry activityResultRegistry = ActivityResultRegistry.this;
                HashMap hashMap = (HashMap) activityResultRegistry.mKeyToRc;
                String str2 = r2;
                Integer num = (Integer) hashMap.get(str2);
                ActivityResultContract activityResultContract = r3;
                if (num != null) {
                    activityResultRegistry.mLaunchedKeys.add(str2);
                    try {
                        activityResultRegistry.onLaunch(num.intValue(), activityResultContract, obj);
                        return;
                    } catch (Exception e) {
                        activityResultRegistry.mLaunchedKeys.remove(str2);
                        throw e;
                    }
                }
                throw new IllegalStateException("Attempting to launch an unregistered ActivityResultLauncher with contract " + activityResultContract + " and input " + obj + ". You must ensure the ActivityResultLauncher is registered before calling launch().");
            }
        };
    }
}
