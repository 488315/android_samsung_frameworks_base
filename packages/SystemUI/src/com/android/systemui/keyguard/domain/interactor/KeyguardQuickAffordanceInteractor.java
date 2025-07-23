package com.android.systemui.keyguard.domain.interactor;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.StatsEvent;
import android.util.StatsLog;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.logging.KeyguardQuickAffordancesLogger;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancesMetricsLogger;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancesMetricsLoggerImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _launchingFromTriggeredResult;
    public final AccessibilityManager accessibilityManager;
    public final ActivityStarter activityStarter;
    public final Context appContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final DevicePolicyManager devicePolicyManager;
    public final DockManager dockManager;
    public final FeatureFlags featureFlags;
    public final KeyguardInteractor keyguardInteractor;
    public final Lazy keyguardShortcutManager;
    public final KeyguardStateController keyguardStateController;
    public final DialogTransitionAnimator launchAnimator;
    public final ReadonlyStateFlow launchingAffordance;
    public final ReadonlyStateFlow launchingFromTriggeredResult;
    public final KeyguardQuickAffordancesLogger logger;
    public final KeyguardQuickAffordancesMetricsLogger metricsLogger;
    public final Lazy repository;
    public final ShadeInteractor shadeInteractor;
    public final UserTracker userTracker;

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

    public KeyguardQuickAffordanceInteractor(KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ActivityStarter activityStarter, FeatureFlags featureFlags, Lazy lazy, DialogTransitionAnimator dialogTransitionAnimator, KeyguardQuickAffordancesLogger keyguardQuickAffordancesLogger, KeyguardQuickAffordancesMetricsLogger keyguardQuickAffordancesMetricsLogger, DevicePolicyManager devicePolicyManager, DockManager dockManager, BiometricSettingsRepository biometricSettingsRepository, AccessibilityManager accessibilityManager, CoroutineDispatcher coroutineDispatcher, Context context, Lazy lazy2, Lazy lazy3) {
        this.keyguardInteractor = keyguardInteractor;
        this.shadeInteractor = shadeInteractor;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.featureFlags = featureFlags;
        this.repository = lazy;
        this.launchAnimator = dialogTransitionAnimator;
        this.logger = keyguardQuickAffordancesLogger;
        this.metricsLogger = keyguardQuickAffordancesMetricsLogger;
        this.devicePolicyManager = devicePolicyManager;
        this.dockManager = dockManager;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.accessibilityManager = accessibilityManager;
        this.backgroundDispatcher = coroutineDispatcher;
        this.appContext = context;
        this.keyguardShortcutManager = lazy3;
        this.launchingAffordance = FlowKt.asStateFlow(((KeyguardQuickAffordanceRepository) lazy.get()).launchingAffordance);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._launchingFromTriggeredResult = MutableStateFlow;
        this.launchingFromTriggeredResult = FlowKt.asStateFlow(MutableStateFlow);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPickerFlags(kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.getPickerFlags(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x004d, code lost:
    
        if (r7 == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00ab A[LOOP:0: B:15:0x00a5->B:17:0x00ab, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getSelections(kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.getSelections(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getSlotPickerRepresentations(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r4 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r4.isFeatureDisabledByDevicePolicy(r0)
            if (r5 != r1) goto L41
            return r1
        L41:
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L4c
            kotlin.collections.EmptyList r4 = kotlin.collections.EmptyList.INSTANCE
            return r4
        L4c:
            dagger.Lazy r4 = r4.repository
            java.lang.Object r4 = r4.get()
            com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository r4 = (com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository) r4
            java.util.List r4 = r4.getSlotPickerRepresentations()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.getSlotPickerRepresentations(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object isFeatureDisabledByDevicePolicy(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r5)
            return r5
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2 r5 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            kotlinx.coroutines.CoroutineDispatcher r4 = r4.backgroundDispatcher
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.isFeatureDisabledByDevicePolicy(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void launchQuickAffordance(final Intent intent, boolean z) {
        intent.putExtra("fromLockscreen", true);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if ((!(!keyguardStateControllerImpl.mCanDismissLockScreen) || !(keyguardStateControllerImpl.mSecure & (keyguardStateControllerImpl.mTrusted ^ true))) || !z) {
            this.activityStarter.startActivity(intent, false);
        } else {
            intent.putExtra("isSecure", true);
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$launchQuickAffordance$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    intent.addFlags(872480768);
                    try {
                        IActivityTaskManager service = ActivityTaskManager.getService();
                        String basePackageName = this.appContext.getBasePackageName();
                        String attributionTag = this.appContext.getAttributionTag();
                        Intent intent2 = intent;
                        service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent2, intent2.resolveTypeIfNeeded(this.appContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, makeBasic.toBundle(), ((UserTrackerImpl) this.userTracker).getUserHandle().getIdentifier());
                    } catch (RemoteException e) {
                        Log.w("KeyguardQuickAffordanceInteractor", "Unable to start activity", e);
                    }
                }
            });
        }
    }

    public final void onQuickAffordanceTriggered(String str, Expandable expandable, String str2) {
        Object obj;
        ArrayList arrayList = (ArrayList) ((KeyguardShortcutManager) this.keyguardShortcutManager.get()).getQuickAffordanceConfigList();
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i);
            i++;
            if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj).getKey(), str)) {
                break;
            }
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) obj;
        if (keyguardQuickAffordanceConfig == null) {
            Log.e("KeyguardQuickAffordanceInteractor", "Affordance config with key of \"" + str + "\" not found!");
            return;
        }
        ((KeyguardQuickAffordancesMetricsLoggerImpl) this.metricsLogger).getClass();
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(612);
        newBuilder.writeString(str2);
        newBuilder.writeString(str);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
        KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered = keyguardQuickAffordanceConfig.onTriggered(expandable);
        boolean z = onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity;
        StateFlowImpl stateFlowImpl = this._launchingFromTriggeredResult;
        if (z) {
            stateFlowImpl.setValue(new KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult(true, str));
            KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity startActivity = (KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) onTriggered;
            launchQuickAffordance(startActivity.intent, startActivity.canShowWhileLocked);
            return;
        }
        if (onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled) {
            stateFlowImpl.setValue(new KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult(((KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled) onTriggered).actionLaunched, str));
            return;
        }
        if (!(onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog)) {
            throw new NoWhenBranchMatchedException();
        }
        stateFlowImpl.setValue(new KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult(true, str));
        KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog showDialog = (KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog) onTriggered;
        AlertDialog alertDialog = showDialog.dialog;
        Expandable expandable2 = showDialog.expandable;
        if (expandable2 != null) {
            Expandable.Companion companion = Expandable.Companion;
            DialogTransitionAnimator.Controller dialogTransitionController = expandable2.dialogTransitionController(null);
            if (dialogTransitionController != null) {
                SystemUIDialog.applyFlags(alertDialog, true);
                SystemUIDialog.setShowForAllUsers(alertDialog);
                SystemUIDialog.registerDismissListener(alertDialog);
                SystemUIDialog.setDialogSize(alertDialog);
                TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                this.launchAnimator.show(alertDialog, dialogTransitionController, false);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x006e, code lost:
    
        if (r14 == r1) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0070, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0051, code lost:
    
        if (r14 == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object quickAffordance(com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r13, kotlin.coroutines.jvm.internal.ContinuationImpl r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1
            if (r0 == 0) goto L13
            r0 = r14
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1
            r0.<init>(r12, r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            r5 = 0
            if (r2 == 0) goto L44
            if (r2 == r4) goto L37
            if (r2 != r3) goto L2f
            java.lang.Object r12 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r12 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor) r12
            kotlin.ResultKt.throwOnFailure(r14)
            goto L71
        L2f:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L37:
            java.lang.Object r12 = r0.L$1
            r13 = r12
            com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r13 = (com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition) r13
            java.lang.Object r12 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r12 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor) r12
            kotlin.ResultKt.throwOnFailure(r14)
            goto L54
        L44:
            kotlin.ResultKt.throwOnFailure(r14)
            r0.L$0 = r12
            r0.L$1 = r13
            r0.label = r4
            java.lang.Object r14 = r12.isFeatureDisabledByDevicePolicy(r0)
            if (r14 != r1) goto L54
            goto L70
        L54:
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            boolean r14 = r14.booleanValue()
            if (r14 == 0) goto L64
            com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel$Hidden r12 = com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel.Hidden.INSTANCE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r13 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r13.<init>(r12)
            return r13
        L64:
            r0.L$0 = r12
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r14 = r12.quickAffordanceAlwaysVisible(r13, r5, r0)
            if (r14 != r1) goto L71
        L70:
            return r1
        L71:
            r6 = r14
            kotlinx.coroutines.flow.Flow r6 = (kotlinx.coroutines.flow.Flow) r6
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r13 = r12.keyguardInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r13.isDozing
            com.android.systemui.shade.domain.interactor.ShadeInteractor r14 = r12.shadeInteractor
            com.android.systemui.shade.domain.interactor.ShadeInteractorImpl r14 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl) r14
            com.android.systemui.shade.domain.interactor.BaseShadeInteractor r14 = r14.baseShadeInteractor
            kotlinx.coroutines.flow.StateFlow r14 = r14.getAnyExpansion()
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$$inlined$map$2 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$$inlined$map$2
            r0.<init>()
            kotlinx.coroutines.flow.Flow r9 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r0)
            com.android.systemui.keyguard.data.repository.BiometricSettingsRepository r12 = r12.biometricSettingsRepository
            com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl r12 = (com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl) r12
            com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1 r10 = r12.isCurrentUserInLockdown
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$4 r11 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$4
            r11.<init>(r5)
            kotlinx.coroutines.flow.StateFlowImpl r8 = r13.isKeyguardShowing
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 r12 = kotlinx.coroutines.flow.FlowKt.combine(r6, r7, r8, r9, r10, r11)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.quickAffordance(com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object quickAffordanceAlwaysVisible(final com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r5, java.lang.String r6, kotlin.coroutines.jvm.internal.ContinuationImpl r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceAlwaysVisible$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceAlwaysVisible$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceAlwaysVisible$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceAlwaysVisible$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceAlwaysVisible$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r4 = r0.L$2
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r4 = r0.L$1
            r5 = r4
            com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r5 = (com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition) r5
            java.lang.Object r4 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r4 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4e
        L34:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3c:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r4
            r0.L$1 = r5
            r0.L$2 = r6
            r0.label = r3
            java.lang.Object r7 = r4.isFeatureDisabledByDevicePolicy(r0)
            if (r7 != r1) goto L4e
            return r1
        L4e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r6 = r7.booleanValue()
            if (r6 == 0) goto L5e
            com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel$Hidden r4 = com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel.Hidden.INSTANCE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r5 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r5.<init>(r4)
            return r5
        L5e:
            dagger.Lazy r6 = r4.keyguardShortcutManager
            java.lang.Object r6 = r6.get()
            com.android.systemui.statusbar.KeyguardShortcutManager r6 = (com.android.systemui.statusbar.KeyguardShortcutManager) r6
            java.util.List r6 = r6.getQuickAffordanceConfigList()
            int r7 = r5.ordinal()
            java.util.ArrayList r6 = (java.util.ArrayList) r6
            java.lang.Object r6 = r6.get(r7)
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r6 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r6
            java.util.List r6 = java.util.Collections.singletonList(r6)
            boolean r7 = r6.isEmpty()
            if (r7 == 0) goto L88
            com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel$Hidden r4 = com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel.Hidden.INSTANCE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r5 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r5.<init>(r4)
            return r5
        L88:
            r7 = r6
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 10
            int r1 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r7, r1)
            r0.<init>(r1)
            java.util.Iterator r7 = r7.iterator()
        L9a:
            boolean r1 = r7.hasNext()
            if (r1 == 0) goto Lb9
            java.lang.Object r1 = r7.next()
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r1 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r1
            kotlinx.coroutines.flow.Flow r1 = r1.getLockScreenState()
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$1$1 r2 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$1$1
            r3 = 0
            r2.<init>(r3)
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r3 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
            r3.<init>(r2, r1)
            r0.add(r3)
            goto L9a
        Lb9:
            java.util.List r7 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r0)
            java.util.Collection r7 = (java.util.Collection) r7
            r0 = 0
            kotlinx.coroutines.flow.Flow[] r0 = new kotlinx.coroutines.flow.Flow[r0]
            java.lang.Object[] r7 = r7.toArray(r0)
            kotlinx.coroutines.flow.Flow[] r7 = (kotlinx.coroutines.flow.Flow[]) r7
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1
            r0.<init>()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.quickAffordanceAlwaysVisible(com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition, java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object select(java.lang.String r7, java.lang.String r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            Method dump skipped, instructions count: 276
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.select(java.lang.String, java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object unselect(java.lang.String r7, java.lang.String r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.unselect(java.lang.String, java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
