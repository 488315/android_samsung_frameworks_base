package com.android.systemui.controls.ui;

import android.content.Context;
import android.service.controls.Control;
import android.service.controls.CustomControl;
import android.service.controls.actions.BooleanAction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda1;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.controls.ui.ControlActionCoordinatorImpl;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        ControlActionCoordinatorImpl.this.actionsInProgress.remove(str);
                    }
                }, 3000L);
            }
            this.f.invoke();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$bouncerOrRun$1
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                Log.d("ControlsUiController", "Device unlocked, invoking controls action");
                ControlActionCoordinatorImpl.Action.this.invoke();
                return true;
            }
        }, new Runnable() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$bouncerOrRun$2
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
                ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                ControlsActionButton controlsActionButton = controlViewHolder2.getSecControlViewHolder().actionIcon;
                if (controlsActionButton != null) {
                    ProgressBar progressBar = controlsActionButton.actionIconProgress;
                    int i2 = 0;
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
                            i2 = customControl2.getCustomSound();
                        }
                        ControlWithState controlWithState5 = controlViewHolder2.cws;
                        ((AUIFacadeImpl) aUIFacade).playClick(i2, (controlWithState5 != null ? controlWithState5 : null).ci.deviceType, imageView, !z3);
                    }
                    controlViewHolder2.action(new BooleanAction(str, !z3));
                }
                return Unit.INSTANCE;
            }
        }, true, z2));
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void touchActionButton(final com.android.systemui.controls.ui.ControlViewHolder r12, final java.lang.String r13, final android.service.controls.Control r14) {
        /*
            r11 = this;
            java.lang.String r0 = "touchMainAction: ["
            java.lang.String r1 = "]"
            java.lang.String r2 = "ControlsUiController"
            com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m(r0, r13, r1, r2)
            com.android.systemui.controls.ControlsMetricsLogger r0 = r11.controlsMetricsLogger
            boolean r1 = r11.isLocked()
            r0.touch(r12, r1)
            com.android.systemui.controls.util.SALogger$Event$TapMainActionButton r0 = new com.android.systemui.controls.util.SALogger$Event$TapMainActionButton
            r0.<init>(r12)
            com.android.systemui.controls.util.SALogger r1 = r11.saLogger
            com.android.systemui.controls.util.SystemUIAnalyticsWrapper r1 = r1.systemUIAnalyticsWrapper
            r0.sendEvent(r1)
            com.android.systemui.controls.ui.ControlWithState r0 = r12.cws
            r1 = 0
            if (r0 == 0) goto L25
            goto L26
        L25:
            r0 = r1
        L26:
            android.service.controls.Control r0 = r0.control
            r2 = 1
            if (r0 == 0) goto L30
            boolean r0 = r0.isAuthRequired()
            goto L31
        L30:
            r0 = r2
        L31:
            r3 = 0
            if (r0 == 0) goto L4e
            com.android.systemui.controls.ui.ControlWithState r0 = r12.cws
            if (r0 == 0) goto L39
            goto L3a
        L39:
            r0 = r1
        L3a:
            android.service.controls.Control r0 = r0.control
            if (r0 == 0) goto L49
            android.service.controls.CustomControl r0 = r0.getCustomControl()
            if (r0 == 0) goto L49
            boolean r0 = r0.getAllowBasicActionWhenLocked()
            goto L4a
        L49:
            r0 = r3
        L4a:
            if (r0 != 0) goto L4e
            r0 = r2
            goto L4f
        L4e:
            r0 = r3
        L4f:
            boolean r6 = r12.usePanel()
            kotlin.jvm.internal.Ref$BooleanRef r9 = new kotlin.jvm.internal.Ref$BooleanRef
            r9.<init>()
            com.android.systemui.controls.ui.ControlWithState r4 = r12.cws
            if (r4 == 0) goto L5d
            goto L5e
        L5d:
            r4 = r1
        L5e:
            android.service.controls.Control r4 = r4.control
            if (r4 == 0) goto L6b
            android.service.controls.CustomControl r4 = r4.getCustomControl()
            boolean r4 = r4.getUseFullScreenDetailDialog()
            goto L6c
        L6b:
            r4 = r3
        L6c:
            r9.element = r4
            if (r0 != 0) goto L76
            if (r6 == 0) goto L75
            if (r4 == 0) goto L75
            goto L76
        L75:
            r2 = r3
        L76:
            boolean r0 = r12.usePanel()
            com.android.systemui.controls.ui.ControlWithState r3 = r12.cws
            if (r3 == 0) goto L7f
            r1 = r3
        L7f:
            com.android.systemui.controls.controller.ControlInfo r1 = r1.ci
            java.lang.String r1 = r1.controlId
            com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda1 r4 = new com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda1
            r7 = r11
            r5 = r12
            r10 = r13
            r8 = r14
            r4.<init>()
            com.android.systemui.controls.ui.ControlActionCoordinatorImpl$Action r11 = r7.createAction(r1, r4, r0, r2)
            r7.bouncerOrRun(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.touchActionButton(com.android.systemui.controls.ui.ControlViewHolder, java.lang.String, android.service.controls.Control):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void touchCard(final com.android.systemui.controls.ui.ControlViewHolder r5, java.lang.String r6, android.service.controls.Control r7) {
        /*
            r4 = this;
            java.lang.String r0 = "touchCard: ["
            java.lang.String r1 = "]"
            java.lang.String r2 = "ControlsUiController"
            com.android.keyguard.KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m(r0, r6, r1, r2)
            com.android.systemui.controls.ControlsMetricsLogger r6 = r4.controlsMetricsLogger
            boolean r0 = r4.isLocked()
            r6.touch(r5, r0)
            com.android.systemui.controls.util.SALogger$Event$TapCardLayout r6 = new com.android.systemui.controls.util.SALogger$Event$TapCardLayout
            r6.<init>(r5)
            com.android.systemui.controls.util.SALogger r0 = r4.saLogger
            com.android.systemui.controls.util.SystemUIAnalyticsWrapper r1 = r0.systemUIAnalyticsWrapper
            r6.sendEvent(r1)
            android.service.controls.CustomControl r6 = r7.getCustomControl()
            int r6 = r6.getLayoutType()
            r1 = 1
            r2 = 0
            if (r6 != r1) goto L2c
            goto L2d
        L2c:
            r7 = r2
        L2d:
            if (r7 == 0) goto L49
            com.android.systemui.controls.util.SALogger$Event$TapSmallTypeCard r6 = new com.android.systemui.controls.util.SALogger$Event$TapSmallTypeCard
            java.lang.CharSequence r3 = r7.getTitle()
            java.lang.String r3 = r3.toString()
            int r7 = r7.getDeviceType()
            java.lang.String r7 = java.lang.String.valueOf(r7)
            r6.<init>(r3, r7)
            com.android.systemui.controls.util.SystemUIAnalyticsWrapper r7 = r0.systemUIAnalyticsWrapper
            r6.sendEvent(r7)
        L49:
            com.android.systemui.controls.ui.ControlWithState r6 = r5.cws
            if (r6 == 0) goto L4e
            goto L4f
        L4e:
            r6 = r2
        L4f:
            android.service.controls.Control r6 = r6.control
            if (r6 == 0) goto L58
            boolean r6 = r6.isAuthRequired()
            goto L59
        L58:
            r6 = r1
        L59:
            r7 = 0
            if (r6 == 0) goto L76
            com.android.systemui.controls.ui.ControlWithState r6 = r5.cws
            if (r6 == 0) goto L61
            goto L62
        L61:
            r6 = r2
        L62:
            android.service.controls.Control r6 = r6.control
            if (r6 == 0) goto L71
            android.service.controls.CustomControl r6 = r6.getCustomControl()
            if (r6 == 0) goto L71
            boolean r6 = r6.getAllowBasicActionWhenLocked()
            goto L72
        L71:
            r6 = r7
        L72:
            if (r6 != 0) goto L76
            r6 = r1
            goto L77
        L76:
            r6 = r7
        L77:
            kotlin.jvm.internal.Ref$BooleanRef r0 = new kotlin.jvm.internal.Ref$BooleanRef
            r0.<init>()
            com.android.systemui.controls.ui.ControlWithState r3 = r5.cws
            if (r3 == 0) goto L81
            goto L82
        L81:
            r3 = r2
        L82:
            android.service.controls.Control r3 = r3.control
            if (r3 == 0) goto L8f
            android.service.controls.CustomControl r3 = r3.getCustomControl()
            boolean r3 = r3.getUseFullScreenDetailDialog()
            goto L90
        L8f:
            r3 = r7
        L90:
            r0.element = r3
            if (r6 != 0) goto L98
            if (r3 == 0) goto L97
            goto L98
        L97:
            r1 = r7
        L98:
            com.android.systemui.controls.ui.ControlWithState r6 = r5.cws
            if (r6 == 0) goto L9d
            r2 = r6
        L9d:
            com.android.systemui.controls.controller.ControlInfo r6 = r2.ci
            java.lang.String r6 = r6.controlId
            com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda0 r2 = new com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda0
            r2.<init>()
            com.android.systemui.controls.ui.ControlActionCoordinatorImpl$Action r5 = r4.createAction(r6, r2, r7, r1)
            r4.bouncerOrRun(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.ControlActionCoordinatorImpl.touchCard(com.android.systemui.controls.ui.ControlViewHolder, java.lang.String, android.service.controls.Control):void");
    }
}
