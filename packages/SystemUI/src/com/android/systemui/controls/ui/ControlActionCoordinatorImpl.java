package com.android.systemui.controls.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.service.controls.Control;
import android.service.controls.CustomControl;
import android.service.controls.actions.BooleanAction;
import android.service.controls.actions.CommandAction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda1;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.AUIFacadeImpl;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.taskview.TaskViewFactoryController;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$BooleanRef;

/* loaded from: classes2.dex */
public final class ControlActionCoordinatorImpl implements ControlActionCoordinator, SecControlActionCoordinator {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Set actionsInProgress = new LinkedHashSet();
    public Context activityContext;
    public final ActivityStarter activityStarter;
    public final AUIFacade auiFacade;
    public final DelayableExecutor bgExecutor;
    public final BroadcastSender broadcastSender;
    public final Context context;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public final DesktopManager desktopManagerWrapper;
    public DetailDialog dialog;
    public final KeyguardStateController keyguardStateController;
    public Action pendingAction;
    public final SALogger saLogger;
    public final Optional taskViewFactory;
    public final DelayableExecutor uiExecutor;
    public final VibratorHelper vibrator;

    public final class Action {
        public final boolean authIsRequired;
        public final boolean blockable;
        public final String controlId;
        public final Function0 f;

        public Action(String str, Function0 function0, boolean z, boolean z2) {
            this.controlId = str;
            this.f = function0;
            this.blockable = z;
            this.authIsRequired = z2;
        }

        public final void invoke() {
            if (this.blockable) {
                final ControlActionCoordinatorImpl controlActionCoordinatorImpl = ControlActionCoordinatorImpl.this;
                Set set = controlActionCoordinatorImpl.actionsInProgress;
                final String str = this.controlId;
                if (!set.add(str)) {
                    return;
                }
                controlActionCoordinatorImpl.uiExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$shouldRunAction$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        controlActionCoordinatorImpl.actionsInProgress.remove(str);
                    }
                }, 3000L);
            }
            this.f.invoke();
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

    public ControlActionCoordinatorImpl(Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ActivityStarter activityStarter, BroadcastSender broadcastSender, KeyguardStateController keyguardStateController, Optional<TaskViewFactoryController.TaskViewFactoryImpl> optional, ControlsMetricsLogger controlsMetricsLogger, VibratorHelper vibratorHelper, ControlsSettingsRepository controlsSettingsRepository, SALogger sALogger, DesktopManager desktopManager, AUIFacade aUIFacade) {
        this.context = context;
        this.bgExecutor = delayableExecutor;
        this.uiExecutor = delayableExecutor2;
        this.activityStarter = activityStarter;
        this.broadcastSender = broadcastSender;
        this.keyguardStateController = keyguardStateController;
        this.taskViewFactory = optional;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.vibrator = vibratorHelper;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.saLogger = sALogger;
        this.desktopManagerWrapper = desktopManager;
        this.auiFacade = aUIFacade;
    }

    public final void bouncerOrRun(final Action action) {
        boolean z = action.authIsRequired || !((Boolean) ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.$$delegate_0.getValue()).booleanValue();
        if (!((KeyguardStateControllerImpl) this.keyguardStateController).mShowing || !z) {
            action.invoke();
            return;
        }
        if (isLocked()) {
            BroadcastSender broadcastSender = this.broadcastSender;
            broadcastSender.getClass();
            broadcastSender.sendInBackground("closeSystemDialogs", new BroadcastSender$$ExternalSyntheticLambda1(broadcastSender));
            this.pendingAction = action;
        }
        this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.bouncerOrRun.1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                Log.d("ControlsUiController", "Device unlocked, invoking controls action");
                action.invoke();
                return true;
            }
        }, new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.bouncerOrRun.2
            @Override // java.lang.Runnable
            public final void run() {
                ControlActionCoordinatorImpl.this.pendingAction = null;
            }
        }, true);
    }

    public final Action createAction(String str, Function0 function0, boolean z, boolean z2) {
        return new Action(str, function0, z, z2);
    }

    public final boolean isLocked() {
        return !this.keyguardStateController.isUnlocked();
    }

    public final void toggleActionButton(final ControlViewHolder controlViewHolder, final String str, final boolean z) {
        CustomControl customControl;
        Log.d("ControlsUiController", "toggleMainAction: [" + str + "]: " + z);
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        new SALogger.Event.TapMainActionButton(controlViewHolder).sendEvent(this.saLogger.systemUIAnalyticsWrapper);
        ControlWithState controlWithState = controlViewHolder.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        Control control = controlWithState.control;
        boolean z2 = false;
        if (control != null ? control.isAuthRequired() : true) {
            ControlWithState controlWithState2 = controlViewHolder.cws;
            if (controlWithState2 == null) {
                controlWithState2 = null;
            }
            Control control2 = controlWithState2.control;
            if (!((control2 == null || (customControl = control2.getCustomControl()) == null) ? false : customControl.getAllowBasicActionWhenLocked())) {
                z2 = true;
            }
        }
        ControlWithState controlWithState3 = controlViewHolder.cws;
        bouncerOrRun(createAction((controlWithState3 != null ? controlWithState3 : null).ci.controlId, new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CustomControl customControl2;
                int i = ControlActionCoordinatorImpl.$r8$clinit;
                ControlViewHolder controlViewHolder2 = controlViewHolder;
                ControlsActionButton controlsActionButton = controlViewHolder2.getSecControlViewHolder().actionIcon;
                if (controlsActionButton != null) {
                    ProgressBar progressBar = controlsActionButton.actionIconProgress;
                    int customSound = 0;
                    if (progressBar != null) {
                        progressBar.setVisibility(0);
                    }
                    ImageView imageView = controlsActionButton.actionIcon;
                    boolean z3 = z;
                    if (imageView != null) {
                        AUIFacade aUIFacade = this.auiFacade;
                        ControlWithState controlWithState4 = controlViewHolder2.cws;
                        if (controlWithState4 == null) {
                            controlWithState4 = null;
                        }
                        Control control3 = controlWithState4.control;
                        if (control3 != null && (customControl2 = control3.getCustomControl()) != null) {
                            customSound = customControl2.getCustomSound();
                        }
                        ControlWithState controlWithState5 = controlViewHolder2.cws;
                        ((AUIFacadeImpl) aUIFacade).playClick(customSound, (controlWithState5 != null ? controlWithState5 : null).ci.deviceType, imageView, !z3);
                    }
                    controlViewHolder2.action(new BooleanAction(str, !z3));
                }
                return Unit.INSTANCE;
            }
        }, true, z2));
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void touchActionButton(final ControlViewHolder controlViewHolder, final String str, final Control control) {
        boolean z;
        CustomControl customControl;
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("touchMainAction: [", str, "]", "ControlsUiController");
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        new SALogger.Event.TapMainActionButton(controlViewHolder).sendEvent(this.saLogger.systemUIAnalyticsWrapper);
        ControlWithState controlWithState = controlViewHolder.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        Control control2 = controlWithState.control;
        boolean z2 = true;
        if (control2 != null ? control2.isAuthRequired() : true) {
            ControlWithState controlWithState2 = controlViewHolder.cws;
            if (controlWithState2 == null) {
                controlWithState2 = null;
            }
            Control control3 = controlWithState2.control;
            if (!((control3 == null || (customControl = control3.getCustomControl()) == null) ? false : customControl.getAllowBasicActionWhenLocked())) {
                z = true;
            }
        } else {
            z = false;
        }
        final boolean zUsePanel = controlViewHolder.usePanel();
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ControlWithState controlWithState3 = controlViewHolder.cws;
        if (controlWithState3 == null) {
            controlWithState3 = null;
        }
        Control control4 = controlWithState3.control;
        boolean useFullScreenDetailDialog = control4 != null ? control4.getCustomControl().getUseFullScreenDetailDialog() : false;
        ref$BooleanRef.element = useFullScreenDetailDialog;
        if (!z && (!zUsePanel || !useFullScreenDetailDialog)) {
            z2 = false;
        }
        boolean zUsePanel2 = controlViewHolder.usePanel();
        ControlWithState controlWithState4 = controlViewHolder.cws;
        bouncerOrRun(createAction((controlWithState4 != null ? controlWithState4 : null).ci.controlId, new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CustomControl customControl2;
                Control control5 = control;
                int i = ControlActionCoordinatorImpl.$r8$clinit;
                ControlViewHolder controlViewHolder2 = controlViewHolder;
                ControlsActionButton controlsActionButton = controlViewHolder2.getSecControlViewHolder().actionIcon;
                if (controlsActionButton != null) {
                    ProgressBar progressBar = controlsActionButton.actionIconProgress;
                    int customSound = 0;
                    if (progressBar != null) {
                        progressBar.setVisibility(0);
                    }
                    ImageView imageView = controlsActionButton.actionIcon;
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    if (imageView != null) {
                        AUIFacade aUIFacade = controlActionCoordinatorImpl.auiFacade;
                        ControlWithState controlWithState5 = controlViewHolder2.cws;
                        if (controlWithState5 == null) {
                            controlWithState5 = null;
                        }
                        Control control6 = controlWithState5.control;
                        if (control6 != null && (customControl2 = control6.getCustomControl()) != null) {
                            customSound = customControl2.getCustomSound();
                        }
                        ControlWithState controlWithState6 = controlViewHolder2.cws;
                        ((AUIFacadeImpl) aUIFacade).playClick(customSound, (controlWithState6 != null ? controlWithState6 : null).ci.deviceType, imageView, true);
                    }
                    if (zUsePanel) {
                        PendingIntent appIntent = control5.getAppIntent();
                        boolean z3 = ref$BooleanRef.element;
                        controlActionCoordinatorImpl.getClass();
                        controlActionCoordinatorImpl.bgExecutor.execute(new ControlActionCoordinatorImpl$showDetail$1(controlActionCoordinatorImpl, appIntent, z3, controlViewHolder2));
                    } else {
                        controlViewHolder2.action(new CommandAction(str));
                    }
                }
                return Unit.INSTANCE;
            }
        }, zUsePanel2, z2));
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void touchCard(final ControlViewHolder controlViewHolder, String str, Control control) {
        boolean z;
        CustomControl customControl;
        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("touchCard: [", str, "]", "ControlsUiController");
        this.controlsMetricsLogger.touch(controlViewHolder, isLocked());
        SALogger.Event.TapCardLayout tapCardLayout = new SALogger.Event.TapCardLayout(controlViewHolder);
        SALogger sALogger = this.saLogger;
        tapCardLayout.sendEvent(sALogger.systemUIAnalyticsWrapper);
        boolean z2 = true;
        if (control.getCustomControl().getLayoutType() != 1) {
            control = null;
        }
        if (control != null) {
            new SALogger.Event.TapSmallTypeCard(control.getTitle().toString(), String.valueOf(control.getDeviceType())).sendEvent(sALogger.systemUIAnalyticsWrapper);
        }
        ControlWithState controlWithState = controlViewHolder.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        Control control2 = controlWithState.control;
        if (control2 != null ? control2.isAuthRequired() : true) {
            ControlWithState controlWithState2 = controlViewHolder.cws;
            if (controlWithState2 == null) {
                controlWithState2 = null;
            }
            Control control3 = controlWithState2.control;
            if (!((control3 == null || (customControl = control3.getCustomControl()) == null) ? false : customControl.getAllowBasicActionWhenLocked())) {
                z = true;
            }
        } else {
            z = false;
        }
        final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        ControlWithState controlWithState3 = controlViewHolder.cws;
        if (controlWithState3 == null) {
            controlWithState3 = null;
        }
        Control control4 = controlWithState3.control;
        boolean useFullScreenDetailDialog = control4 != null ? control4.getCustomControl().getUseFullScreenDetailDialog() : false;
        ref$BooleanRef.element = useFullScreenDetailDialog;
        if (!z && !useFullScreenDetailDialog) {
            z2 = false;
        }
        ControlWithState controlWithState4 = controlViewHolder.cws;
        bouncerOrRun(createAction((controlWithState4 != null ? controlWithState4 : null).ci.controlId, new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = ControlActionCoordinatorImpl.$r8$clinit;
                ControlViewHolder controlViewHolder2 = controlViewHolder;
                ControlWithState controlWithState5 = controlViewHolder2.cws;
                if (controlWithState5 == null) {
                    controlWithState5 = null;
                }
                Control control5 = controlWithState5.control;
                if (control5 != null) {
                    PendingIntent appIntent = control5.getAppIntent();
                    boolean z3 = ref$BooleanRef.element;
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = this;
                    controlActionCoordinatorImpl.getClass();
                    controlActionCoordinatorImpl.bgExecutor.execute(new ControlActionCoordinatorImpl$showDetail$1(controlActionCoordinatorImpl, appIntent, z3, controlViewHolder2));
                }
                return Unit.INSTANCE;
            }
        }, false, z2));
    }
}
