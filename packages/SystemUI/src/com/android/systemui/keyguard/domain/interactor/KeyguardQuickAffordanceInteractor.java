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
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
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
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
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
    public final KeyguardQuickAffordancesMetricsLogger logger;
    public final Lazy repository;
    public final ShadeInteractor shadeInteractor;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public KeyguardQuickAffordanceInteractor(KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ActivityStarter activityStarter, FeatureFlags featureFlags, Lazy lazy, DialogTransitionAnimator dialogTransitionAnimator, KeyguardQuickAffordancesMetricsLogger keyguardQuickAffordancesMetricsLogger, DevicePolicyManager devicePolicyManager, DockManager dockManager, BiometricSettingsRepository biometricSettingsRepository, CoroutineDispatcher coroutineDispatcher, Context context, Lazy lazy2, Lazy lazy3) {
        this.keyguardInteractor = keyguardInteractor;
        this.shadeInteractor = shadeInteractor;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.featureFlags = featureFlags;
        this.repository = lazy;
        this.launchAnimator = dialogTransitionAnimator;
        this.logger = keyguardQuickAffordancesMetricsLogger;
        this.devicePolicyManager = devicePolicyManager;
        this.dockManager = dockManager;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.backgroundDispatcher = coroutineDispatcher;
        this.appContext = context;
        this.keyguardShortcutManager = lazy3;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getPickerFlags(kotlin.coroutines.Continuation r8) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.getPickerFlags(kotlin.coroutines.Continuation):java.lang.Object");
    }

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
    public final java.lang.Object getSelections(kotlin.coroutines.Continuation r7) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.getSelections(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getSlotPickerRepresentations(kotlin.coroutines.Continuation r5) {
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
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.getSlotPickerRepresentations(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object isFeatureDisabledByDevicePolicy(ContinuationImpl continuationImpl) {
        return BuildersKt.withContext(this.backgroundDispatcher, new KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$$inlined$withContext$1("KeyguardQuickAffordanceInteractor#isFeatureDisabledByDevicePolicy", null, this), continuationImpl);
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
                        service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent2, intent2.resolveTypeIfNeeded(this.appContext.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), ((UserTrackerImpl) this.userTracker).getUserHandle().getIdentifier());
                    } catch (RemoteException e) {
                        Log.w("KeyguardQuickAffordanceInteractor", "Unable to start activity", e);
                    }
                }
            });
        }
    }

    public final void onQuickAffordanceTriggered(String str, Expandable expandable, String str2) {
        Object obj;
        Iterator it = ((ArrayList) ((KeyguardShortcutManager) this.keyguardShortcutManager.get()).getQuickAffordanceConfigList()).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj).getKey(), str)) {
                    break;
                }
            }
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) obj;
        if (keyguardQuickAffordanceConfig == null) {
            Log.e("KeyguardQuickAffordanceInteractor", "Affordance config with key of \"" + str + "\" not found!");
            return;
        }
        ((KeyguardQuickAffordancesMetricsLoggerImpl) this.logger).getClass();
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(612);
        newBuilder.writeString(str2);
        newBuilder.writeString(str);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
        KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered = keyguardQuickAffordanceConfig.onTriggered(expandable);
        if (onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) {
            KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity startActivity = (KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) onTriggered;
            launchQuickAffordance(startActivity.intent, startActivity.canShowWhileLocked);
            return;
        }
        if ((onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled) || !(onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog)) {
            return;
        }
        KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog showDialog = (KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog) onTriggered;
        AlertDialog alertDialog = showDialog.dialog;
        Expandable expandable2 = showDialog.expandable;
        if (expandable2 != null) {
            Expandable.Companion companion = Expandable.Companion;
            DialogTransitionAnimator.Controller dialogTransitionController = expandable2.dialogTransitionController(null);
            if (dialogTransitionController != null) {
                SystemUIDialog.applyFlags(alertDialog);
                SystemUIDialog.setShowForAllUsers(alertDialog);
                SystemUIDialog.registerDismissListener(alertDialog);
                SystemUIDialog.setDialogSize(alertDialog);
                TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                this.launchAnimator.show(alertDialog, dialogTransitionController, false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object quickAffordance(com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r11, kotlin.coroutines.Continuation r12) {
        /*
            r10 = this;
            boolean r0 = r12 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1
            if (r0 == 0) goto L13
            r0 = r12
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
            r0.<init>(r10, r12)
        L18:
            java.lang.Object r12 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L44
            if (r2 == r5) goto L37
            if (r2 != r4) goto L2f
            java.lang.Object r10 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r10 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor) r10
            kotlin.ResultKt.throwOnFailure(r12)
            goto L71
        L2f:
            java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
            java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
            r10.<init>(r11)
            throw r10
        L37:
            java.lang.Object r10 = r0.L$1
            r11 = r10
            com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r11 = (com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition) r11
            java.lang.Object r10 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r10 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor) r10
            kotlin.ResultKt.throwOnFailure(r12)
            goto L54
        L44:
            kotlin.ResultKt.throwOnFailure(r12)
            r0.L$0 = r10
            r0.L$1 = r11
            r0.label = r5
            java.lang.Object r12 = r10.isFeatureDisabledByDevicePolicy(r0)
            if (r12 != r1) goto L54
            return r1
        L54:
            java.lang.Boolean r12 = (java.lang.Boolean) r12
            boolean r12 = r12.booleanValue()
            if (r12 == 0) goto L64
            com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel$Hidden r10 = com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel.Hidden.INSTANCE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r11 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r11.<init>(r10)
            return r11
        L64:
            r0.L$0 = r10
            r0.L$1 = r3
            r0.label = r4
            java.lang.Object r12 = r10.quickAffordanceAlwaysVisible(r11, r0)
            if (r12 != r1) goto L71
            return r1
        L71:
            r4 = r12
            kotlinx.coroutines.flow.Flow r4 = (kotlinx.coroutines.flow.Flow) r4
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r11 = r10.keyguardInteractor
            kotlinx.coroutines.flow.StateFlow r5 = r11.isDozing
            com.android.systemui.Flags.sceneContainer()
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r11 = r10.keyguardInteractor
            kotlinx.coroutines.flow.Flow r6 = r11.isKeyguardShowing
            com.android.systemui.shade.domain.interactor.ShadeInteractor r11 = r10.shadeInteractor
            com.android.systemui.shade.domain.interactor.ShadeInteractorImpl r11 = (com.android.systemui.shade.domain.interactor.ShadeInteractorImpl) r11
            com.android.systemui.shade.domain.interactor.BaseShadeInteractor r11 = r11.baseShadeInteractor
            kotlinx.coroutines.flow.StateFlow r11 = r11.getAnyExpansion()
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$$inlined$map$2 r12 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$$inlined$map$2
            r12.<init>()
            kotlinx.coroutines.flow.Flow r7 = kotlinx.coroutines.flow.FlowKt.distinctUntilChanged(r12)
            com.android.systemui.keyguard.data.repository.BiometricSettingsRepository r10 = r10.biometricSettingsRepository
            com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl r10 = (com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl) r10
            com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl$special$$inlined$map$1 r8 = r10.isCurrentUserInLockdown
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$4 r9 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$4
            r9.<init>(r3)
            kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 r10 = kotlinx.coroutines.flow.FlowKt.combine(r4, r5, r6, r7, r8, r9)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.quickAffordance(com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object quickAffordanceAlwaysVisible(final com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
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
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            java.lang.Object r5 = r0.L$1
            r6 = r5
            com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition r6 = (com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition) r6
            java.lang.Object r5 = r0.L$0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor r5 = (com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor) r5
            kotlin.ResultKt.throwOnFailure(r7)
            goto L48
        L30:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L38:
            kotlin.ResultKt.throwOnFailure(r7)
            r0.L$0 = r5
            r0.L$1 = r6
            r0.label = r3
            java.lang.Object r7 = r5.isFeatureDisabledByDevicePolicy(r0)
            if (r7 != r1) goto L48
            return r1
        L48:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L58
            com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel$Hidden r5 = com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel.Hidden.INSTANCE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r6 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r6.<init>(r5)
            goto Lc8
        L58:
            dagger.Lazy r7 = r5.keyguardShortcutManager
            java.lang.Object r7 = r7.get()
            com.android.systemui.statusbar.KeyguardShortcutManager r7 = (com.android.systemui.statusbar.KeyguardShortcutManager) r7
            java.util.List r7 = r7.getQuickAffordanceConfigList()
            int r0 = r6.ordinal()
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            java.lang.Object r7 = r7.get(r0)
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r7 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r7
            java.util.List r7 = java.util.Collections.singletonList(r7)
            boolean r0 = r7.isEmpty()
            if (r0 == 0) goto L82
            com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel$Hidden r5 = com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel.Hidden.INSTANCE
            kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 r6 = new kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2
            r6.<init>(r5)
            goto Lc8
        L82:
            r0 = r7
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r0, r2)
            r1.<init>(r2)
            java.util.Iterator r0 = r0.iterator()
        L94:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto Lb3
            java.lang.Object r2 = r0.next()
            com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig r2 = (com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig) r2
            kotlinx.coroutines.flow.Flow r2 = r2.getLockScreenState()
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$1$1 r3 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$1$1
            r4 = 0
            r3.<init>(r4)
            kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 r4 = new kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1
            r4.<init>(r3, r2)
            r1.add(r4)
            goto L94
        Lb3:
            java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.toList(r1)
            java.util.Collection r0 = (java.util.Collection) r0
            r1 = 0
            kotlinx.coroutines.flow.Flow[] r1 = new kotlinx.coroutines.flow.Flow[r1]
            java.lang.Object[] r0 = r0.toArray(r1)
            kotlinx.coroutines.flow.Flow[] r0 = (kotlinx.coroutines.flow.Flow[]) r0
            com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1 r1 = new com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1
            r1.<init>()
            r6 = r1
        Lc8:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.quickAffordanceAlwaysVisible(com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object select(java.lang.String r5, java.lang.String r6, kotlin.coroutines.Continuation r7) {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.select(java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object unselect(java.lang.String r5, java.lang.String r6, kotlin.coroutines.Continuation r7) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.unselect(java.lang.String, java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
