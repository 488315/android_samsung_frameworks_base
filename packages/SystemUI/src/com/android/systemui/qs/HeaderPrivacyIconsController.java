package com.android.systemui.qs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.DeadObjectException;
import android.os.Parcel;
import android.permission.PermissionManager;
import android.safetycenter.SafetyCenterManager;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.TaskbarIndicatorController;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyChipEvent;
import com.android.systemui.privacy.PrivacyDialog;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyDialogControllerV2;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyType;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger$$ExternalSyntheticLambda0;
import com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl;
import com.android.systemui.shade.ConstraintsChanges;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.shade.ShadeHeaderController$chipVisibilityListener$1;
import com.android.systemui.shade.domain.interactor.ShadeDialogContextInteractor;
import com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener$Stub$Proxy;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class HeaderPrivacyIconsController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final long UPDATE_CHIP_VISIBILITY = 10000;
    public final ActivityStarter activityStarter;
    public final HeaderPrivacyIconsController$attachStateChangeListener$1 attachStateChangeListener;
    public final Executor backgroundExecutor;
    public final BroadcastDispatcher broadcastDispatcher;
    public final String cameraSlot;
    public ShadeHeaderController$chipVisibilityListener$1 chipVisibilityListener;
    public final DelayableExecutor delayableUiExecutor;
    public final HeaderPrivacyIconsController$desktopCallback$1 desktopCallback;
    public final DeviceProvisionedController deviceProvisionedController;
    public final StatusIconContainer iconContainer;
    public boolean listening;
    public boolean locationIndicatorsEnabled;
    public final String locationSlot;
    public boolean micCameraIndicatorsEnabled;
    public final String micSlot;
    public final HeaderPrivacyIconsController$panelEventReceiver$1 panelEventReceiver;
    public final HeaderPrivacyIconsController$picCallback$1 picCallback;
    public final OngoingPrivacyChip privacyChip;
    public boolean privacyChipLogged;
    public final PrivacyDialogController privacyDialogController;
    public final PrivacyItemController privacyItemController;
    public List privacyList;
    public final PrivacyLogger privacyLogger;
    public List recentLocationPrivacyList;
    public final SafetyCenterManager safetyCenterManager;
    public final HeaderPrivacyIconsController$safetyCenterReceiver$1 safetyCenterReceiver;
    public final ShadeDialogContextInteractor shadeDialogContextInteractor;
    public final SystemClock systemClock;
    public final TaskbarIndicatorController taskbarIndicatorController;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.BroadcastReceiver, com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1] */
    /* JADX WARN: Type inference failed for: r9v17, types: [com.android.systemui.qs.HeaderPrivacyIconsController$desktopCallback$1] */
    /* JADX WARN: Type inference failed for: r9v18, types: [com.android.systemui.qs.HeaderPrivacyIconsController$panelEventReceiver$1] */
    /* JADX WARN: Type inference failed for: r9v19, types: [android.view.View$OnAttachStateChangeListener, com.android.systemui.qs.HeaderPrivacyIconsController$attachStateChangeListener$1] */
    public HeaderPrivacyIconsController(PrivacyItemController privacyItemController, UiEventLogger uiEventLogger, OngoingPrivacyChip ongoingPrivacyChip, PrivacyDialogController privacyDialogController, PrivacyDialogControllerV2 privacyDialogControllerV2, PrivacyLogger privacyLogger, StatusIconContainer statusIconContainer, PermissionManager permissionManager, Executor executor, Executor executor2, ActivityStarter activityStarter, AppOpsController appOpsController, BroadcastDispatcher broadcastDispatcher, SafetyCenterManager safetyCenterManager, DeviceProvisionedController deviceProvisionedController, FeatureFlags featureFlags, ShadeDialogContextInteractor shadeDialogContextInteractor, DelayableExecutor delayableExecutor, SystemClock systemClock, TaskbarIndicatorController taskbarIndicatorController) {
        this.privacyItemController = privacyItemController;
        this.uiEventLogger = uiEventLogger;
        this.privacyChip = ongoingPrivacyChip;
        this.privacyDialogController = privacyDialogController;
        this.privacyLogger = privacyLogger;
        this.iconContainer = statusIconContainer;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.activityStarter = activityStarter;
        this.broadcastDispatcher = broadcastDispatcher;
        this.safetyCenterManager = safetyCenterManager;
        this.deviceProvisionedController = deviceProvisionedController;
        this.shadeDialogContextInteractor = shadeDialogContextInteractor;
        this.delayableUiExecutor = delayableExecutor;
        this.systemClock = systemClock;
        this.taskbarIndicatorController = taskbarIndicatorController;
        this.cameraSlot = ongoingPrivacyChip.getResources().getString(17043268);
        this.micSlot = ongoingPrivacyChip.getResources().getString(17043286);
        this.locationSlot = ongoingPrivacyChip.getResources().getString(17043284);
        EmptyList emptyList = EmptyList.INSTANCE;
        this.privacyList = emptyList;
        this.recentLocationPrivacyList = emptyList;
        this.desktopCallback = new StatusBarSignalPolicy.DesktopCallback() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$desktopCallback$1
            @Override // com.android.systemui.statusbar.phone.StatusBarSignalPolicy.DesktopCallback
            public final void updateDesktopStatusBarIcons() {
                HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
                headerPrivacyIconsController.notifyPrivacyItemsChanged(headerPrivacyIconsController.privacyChip.privacyList);
            }
        };
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$safetyCenterReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
                headerPrivacyIconsController.safetyCenterManager.isSafetyCenterEnabled();
                headerPrivacyIconsController.getClass();
            }
        };
        this.safetyCenterReceiver = r1;
        this.panelEventReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$panelEventReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                PrivacyDialog privacyDialog;
                if (!"com.samsung.systemui.statusbar.COLLAPSED".equals(intent.getAction()) || (privacyDialog = this.this$0.privacyDialogController.dialog) == null) {
                    return;
                }
                privacyDialog.dismiss();
            }
        };
        ?? r9 = new View.OnAttachStateChangeListener() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController$attachStateChangeListener$1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
                BroadcastDispatcher.registerReceiver$default(headerPrivacyIconsController.broadcastDispatcher, headerPrivacyIconsController.safetyCenterReceiver, new IntentFilter("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED"), this.this$0.backgroundExecutor, null, 0, null, 56);
                HeaderPrivacyIconsController headerPrivacyIconsController2 = this.this$0;
                BroadcastDispatcher.registerReceiver$default(headerPrivacyIconsController2.broadcastDispatcher, headerPrivacyIconsController2.panelEventReceiver, new IntentFilter("com.samsung.systemui.statusbar.COLLAPSED"), this.this$0.backgroundExecutor, null, 0, null, 56);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
                headerPrivacyIconsController.broadcastDispatcher.unregisterReceiver(headerPrivacyIconsController.safetyCenterReceiver);
                HeaderPrivacyIconsController headerPrivacyIconsController2 = this.this$0;
                headerPrivacyIconsController2.broadcastDispatcher.unregisterReceiver(headerPrivacyIconsController2.panelEventReceiver);
            }
        };
        this.attachStateChangeListener = r9;
        executor.execute(new Runnable() { // from class: com.android.systemui.qs.HeaderPrivacyIconsController.1
            @Override // java.lang.Runnable
            public final void run() {
                HeaderPrivacyIconsController headerPrivacyIconsController = HeaderPrivacyIconsController.this;
                headerPrivacyIconsController.safetyCenterManager.isSafetyCenterEnabled();
                headerPrivacyIconsController.getClass();
            }
        });
        if (ongoingPrivacyChip.isAttachedToWindow()) {
            BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, r1, new IntentFilter("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED"), executor, null, 0, null, 56);
        }
        ongoingPrivacyChip.setVisibility(8);
        ongoingPrivacyChip.addOnAttachStateChangeListener(r9);
        this.picCallback = new HeaderPrivacyIconsController$picCallback$1(this);
    }

    public final synchronized List getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return CollectionsKt___CollectionsKt.toList(this.privacyList);
    }

    public final synchronized List getRecentLocationPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
        return CollectionsKt___CollectionsKt.toList(this.recentLocationPrivacyList);
    }

    public final void notifyPrivacyItemsChanged(List list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((PrivacyItem) obj).privacyType != PrivacyType.TYPE_LOCATION) {
                arrayList.add(obj);
            }
        }
        boolean z = !arrayList.isEmpty();
        TaskbarIndicatorController taskbarIndicatorController = this.taskbarIndicatorController;
        taskbarIndicatorController.getClass();
        try {
            ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = taskbarIndicatorController.taskbarStatusIconListener;
            if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                Parcel parcelObtain = Parcel.obtain(iTaskbarStatusIconListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
                    parcelObtain.writeBoolean(z);
                    iTaskbarStatusIconListener$Stub$Proxy.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        } catch (DeadObjectException unused) {
            Log.e(taskbarIndicatorController.TAG, "notifyPrivacyItemsChanged taskbarStatusIconListener was dead, but non-null");
        }
    }

    public final void setChipVisibility(boolean z) {
        PrivacyLogger privacyLogger = this.privacyLogger;
        if (z && (this.micCameraIndicatorsEnabled || this.locationIndicatorsEnabled)) {
            privacyLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda0 = new PrivacyLogger$$ExternalSyntheticLambda0(9);
            LogBuffer logBuffer = privacyLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("PrivacyLog", logLevel, privacyLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).bool1 = true;
            logBuffer.commit(logMessageObtain);
            if (!this.privacyChipLogged && this.listening) {
                this.privacyChipLogged = true;
                this.uiEventLogger.log(PrivacyChipEvent.ONGOING_INDICATORS_CHIP_VIEW);
            }
        } else {
            privacyLogger.getClass();
            LogLevel logLevel2 = LogLevel.INFO;
            PrivacyLogger$$ExternalSyntheticLambda0 privacyLogger$$ExternalSyntheticLambda02 = new PrivacyLogger$$ExternalSyntheticLambda0(9);
            LogBuffer logBuffer2 = privacyLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("PrivacyLog", logLevel2, privacyLogger$$ExternalSyntheticLambda02, null);
            ((LogMessageImpl) logMessageObtain2).bool1 = false;
            logBuffer2.commit(logMessageObtain2);
        }
        this.privacyChip.setVisibility(z ? 0 : 8);
        ShadeHeaderController$chipVisibilityListener$1 shadeHeaderController$chipVisibilityListener$1 = this.chipVisibilityListener;
        if (shadeHeaderController$chipVisibilityListener$1 != null) {
            ShadeHeaderController shadeHeaderController = shadeHeaderController$chipVisibilityListener$1.this$0;
            ((CombinedShadeHeadersConstraintManagerImpl) shadeHeaderController.combinedShadeHeadersConstraintManager).getClass();
            final float f = z ? 0.0f : 1.0f;
            ConstraintsChanges constraintsChanges = new ConstraintsChanges(new Function1() { // from class: com.android.systemui.shade.CombinedShadeHeadersConstraintManagerImpl$$ExternalSyntheticLambda4
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    CombinedShadeHeadersConstraintManagerImpl combinedShadeHeadersConstraintManagerImpl = CombinedShadeHeadersConstraintManagerImpl.INSTANCE;
                    ((ConstraintSet) obj).setAlpha(R.id.shade_header_system_icons, f);
                    return Unit.INSTANCE;
                }
            }, null, null, 6, null);
            MotionLayout motionLayout = shadeHeaderController.header;
            Function1 function1 = constraintsChanges.qqsConstraintsChanges;
            if (function1 != null) {
                int i = ShadeHeaderController.QQS_HEADER_CONSTRAINT;
                ConstraintSet constraintSet = motionLayout.getConstraintSet(i);
                constraintSet.getClass();
                function1.mo781invoke(constraintSet);
                motionLayout.updateState(i, constraintSet);
            }
            Function1 function12 = constraintsChanges.qsConstraintsChanges;
            if (function12 != null) {
                int i2 = ShadeHeaderController.QS_HEADER_CONSTRAINT;
                ConstraintSet constraintSet2 = motionLayout.getConstraintSet(i2);
                constraintSet2.getClass();
                function12.mo781invoke(constraintSet2);
                motionLayout.updateState(i2, constraintSet2);
            }
        }
    }

    public final void updatePrivacyIconSlots() {
        boolean z = this.micCameraIndicatorsEnabled;
        boolean z2 = z || this.locationIndicatorsEnabled;
        String str = this.locationSlot;
        String str2 = this.micSlot;
        String str3 = this.cameraSlot;
        StatusIconContainer statusIconContainer = this.iconContainer;
        if (!z2) {
            statusIconContainer.removeIgnoredSlot(str3);
            statusIconContainer.removeIgnoredSlot(str2);
            statusIconContainer.removeIgnoredSlot(str);
            return;
        }
        if (z) {
            statusIconContainer.addIgnoredSlot(str3);
            statusIconContainer.addIgnoredSlot(str2);
        } else {
            statusIconContainer.removeIgnoredSlot(str3);
            statusIconContainer.removeIgnoredSlot(str2);
        }
        if (this.locationIndicatorsEnabled) {
            statusIconContainer.addIgnoredSlot(str);
        } else {
            statusIconContainer.removeIgnoredSlot(str);
        }
    }
}
