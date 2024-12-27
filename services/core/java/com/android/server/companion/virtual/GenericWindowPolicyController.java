package com.android.server.companion.virtual;

import android.app.ActivityOptions;
import android.app.compat.CompatChanges;
import android.companion.virtual.VirtualDeviceManager;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;
import android.window.DisplayWindowPolicyController;

import com.android.internal.app.BlockedAppStreamingActivity;
import com.android.modules.expresslog.Counter;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GenericWindowPolicyController extends DisplayWindowPolicyController {
    public static final ComponentName BLOCKED_APP_STREAMING_COMPONENT =
            new ComponentName("android", BlockedAppStreamingActivity.class.getName());
    public final VirtualDeviceImpl$$ExternalSyntheticLambda2 mActivityBlockedCallback;
    public boolean mActivityLaunchAllowedByDefault;
    public final VirtualDeviceManager.ActivityListener mActivityListener;
    public final Set mActivityPolicyExemptions;
    public final ArraySet mAllowedUsers;
    public final AttributionSource mAttributionSource;
    public final boolean mCrossTaskNavigationAllowedByDefault;
    public final ArraySet mCrossTaskNavigationExemptions;
    public final ComponentName mCustomHomeComponent;
    public final Set mDisplayCategories;
    public final VirtualDeviceImpl$$ExternalSyntheticLambda2 mIntentListenerCallback;
    public final ComponentName mPermissionDialogComponent;
    public final VirtualDeviceImpl$$ExternalSyntheticLambda2 mPipBlockedCallback;
    public final VirtualDeviceImpl$$ExternalSyntheticLambda2 mSecureWindowCallback;
    public boolean mShowTasksInHostDeviceRecents;
    public final Object mGenericWindowPolicyControllerLock = new Object();
    public int mDisplayId = -1;
    public boolean mIsMirrorDisplay = false;
    public final CountDownLatch mDisplayIdSetLatch = new CountDownLatch(1);
    public final ArraySet mRunningUids = new ArraySet();
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final ArraySet mRunningAppsChangedListeners = new ArraySet();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface RunningAppsChangedListener {
        void onRunningAppsChanged(ArraySet arraySet);
    }

    public GenericWindowPolicyController(
            AttributionSource attributionSource,
            ArraySet arraySet,
            boolean z,
            Set set,
            boolean z2,
            Set set2,
            ComponentName componentName,
            VirtualDeviceImpl.AnonymousClass1 anonymousClass1,
            VirtualDeviceImpl$$ExternalSyntheticLambda2 virtualDeviceImpl$$ExternalSyntheticLambda2,
            VirtualDeviceImpl$$ExternalSyntheticLambda2
                    virtualDeviceImpl$$ExternalSyntheticLambda22,
            VirtualDeviceImpl$$ExternalSyntheticLambda2
                    virtualDeviceImpl$$ExternalSyntheticLambda23,
            VirtualDeviceImpl$$ExternalSyntheticLambda2
                    virtualDeviceImpl$$ExternalSyntheticLambda24,
            Set set3,
            boolean z3,
            ComponentName componentName2) {
        this.mAttributionSource = attributionSource;
        this.mAllowedUsers = arraySet;
        this.mActivityLaunchAllowedByDefault = z;
        this.mActivityPolicyExemptions = set;
        this.mCrossTaskNavigationAllowedByDefault = z2;
        this.mCrossTaskNavigationExemptions = new ArraySet(set2);
        this.mPermissionDialogComponent = componentName;
        this.mActivityBlockedCallback = virtualDeviceImpl$$ExternalSyntheticLambda22;
        setInterestedWindowFlags(8192, 524288);
        this.mActivityListener = anonymousClass1;
        this.mPipBlockedCallback = virtualDeviceImpl$$ExternalSyntheticLambda2;
        this.mSecureWindowCallback = virtualDeviceImpl$$ExternalSyntheticLambda23;
        this.mIntentListenerCallback = virtualDeviceImpl$$ExternalSyntheticLambda24;
        this.mDisplayCategories = set3;
        this.mShowTasksInHostDeviceRecents = z3;
        this.mCustomHomeComponent = componentName2;
    }

    public final boolean canActivityBeLaunched(
            ActivityInfo activityInfo, Intent intent, int i, int i2, boolean z) {
        if (android.companion.virtual.flags.Flags.interceptIntentsBeforeApplyingPolicy()) {
            VirtualDeviceImpl$$ExternalSyntheticLambda2
                    virtualDeviceImpl$$ExternalSyntheticLambda2 = this.mIntentListenerCallback;
            if (virtualDeviceImpl$$ExternalSyntheticLambda2 != null
                    && intent != null
                    && virtualDeviceImpl$$ExternalSyntheticLambda2.shouldInterceptIntent(intent)) {
                logActivityLaunchBlocked("Virtual device intercepting intent");
                return false;
            }
            if (canContainActivity(activityInfo, i, i2, z)) {
                return true;
            }
            notifyActivityBlocked(activityInfo);
            return false;
        }
        if (!canContainActivity(activityInfo, i, i2, z)) {
            notifyActivityBlocked(activityInfo);
            return false;
        }
        VirtualDeviceImpl$$ExternalSyntheticLambda2 virtualDeviceImpl$$ExternalSyntheticLambda22 =
                this.mIntentListenerCallback;
        if (virtualDeviceImpl$$ExternalSyntheticLambda22 == null
                || intent == null
                || !virtualDeviceImpl$$ExternalSyntheticLambda22.shouldInterceptIntent(intent)) {
            return true;
        }
        logActivityLaunchBlocked("Virtual device intercepting intent");
        return false;
    }

    public final boolean canContainActivity(ActivityInfo activityInfo, int i, int i2, boolean z) {
        String str;
        if (waitAndGetIsMirrorDisplay()) {
            logActivityLaunchBlocked("Mirror virtual displays cannot contain activities.");
            return false;
        }
        if (!isWindowingModeSupported(i)) {
            logActivityLaunchBlocked("Virtual device doesn't support windowing mode " + i);
            return false;
        }
        if ((activityInfo.flags & EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT)
                == 0) {
            logActivityLaunchBlocked("Activity requires android:canDisplayOnRemoteDevices=true");
            return false;
        }
        UserHandle userHandleForUid =
                UserHandle.getUserHandleForUid(activityInfo.applicationInfo.uid);
        ComponentName componentName = activityInfo.getComponentName();
        if (BLOCKED_APP_STREAMING_COMPONENT.equals(componentName) && userHandleForUid.isSystem()) {
            return true;
        }
        if (!userHandleForUid.isSystem() && !this.mAllowedUsers.contains(userHandleForUid)) {
            logActivityLaunchBlocked("Activity launch disallowed from user " + userHandleForUid);
            return false;
        }
        if (!this.mDisplayCategories.isEmpty()
                ? !((str = activityInfo.requiredDisplayCategory) == null
                        || !this.mDisplayCategories.contains(str))
                : activityInfo.requiredDisplayCategory == null) {
            logActivityLaunchBlocked(
                    "The activity's required display category '"
                            + activityInfo.requiredDisplayCategory
                            + "' not found on virtual display with the following categories: "
                            + this.mDisplayCategories);
            return false;
        }
        synchronized (this.mGenericWindowPolicyControllerLock) {
            if (this.mActivityLaunchAllowedByDefault
                    == this.mActivityPolicyExemptions.contains(componentName)) {
                logActivityLaunchBlocked("Activity launch disallowed by policy: " + componentName);
                return false;
            }
            if (z
                    && i2 != 0
                    && this.mCrossTaskNavigationAllowedByDefault
                            == this.mCrossTaskNavigationExemptions.contains(componentName)) {
                logActivityLaunchBlocked(
                        "Cross task navigation disallowed by policy: " + componentName);
                return false;
            }
            ComponentName componentName2 = this.mPermissionDialogComponent;
            if (componentName2 == null || !componentName2.equals(componentName)) {
                return true;
            }
            logActivityLaunchBlocked("Permission dialog not allowed on virtual device");
            return false;
        }
    }

    public final boolean canShowTasksInHostDeviceRecents() {
        boolean z;
        synchronized (this.mGenericWindowPolicyControllerLock) {
            z = this.mShowTasksInHostDeviceRecents;
        }
        return z;
    }

    public final ComponentName getCustomHomeComponent() {
        return this.mCustomHomeComponent;
    }

    public int getRunningAppsChangedListenersSizeForTesting() {
        int size;
        synchronized (this.mGenericWindowPolicyControllerLock) {
            size = this.mRunningAppsChangedListeners.size();
        }
        return size;
    }

    public final boolean isEnteringPipAllowed(final int i) {
        if (super.isEnteringPipAllowed(i)) {
            return true;
        }
        if (this.mPipBlockedCallback == null) {
            return false;
        }
        this.mHandler.post(
                new Runnable(
                        i) { // from class:
                             // com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        GenericWindowPolicyController.this.mPipBlockedCallback.f$0.getClass();
                    }
                });
        return false;
    }

    public final boolean keepActivityOnWindowFlagsChanged(
            final ActivityInfo activityInfo, int i, int i2) {
        final int waitAndGetDisplayId = waitAndGetDisplayId();
        int i3 = i & 8192;
        if (i3 != 0 && this.mSecureWindowCallback != null && waitAndGetDisplayId != -1) {
            this.mHandler.post(
                    new Runnable() { // from class:
                        // com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            GenericWindowPolicyController genericWindowPolicyController =
                                    GenericWindowPolicyController.this;
                            int i4 = waitAndGetDisplayId;
                            ActivityInfo activityInfo2 = activityInfo;
                            VirtualDeviceImpl$$ExternalSyntheticLambda2
                                    virtualDeviceImpl$$ExternalSyntheticLambda2 =
                                            genericWindowPolicyController.mSecureWindowCallback;
                            int i5 = activityInfo2.applicationInfo.uid;
                            VirtualDeviceImpl virtualDeviceImpl =
                                    virtualDeviceImpl$$ExternalSyntheticLambda2.f$0;
                            synchronized (virtualDeviceImpl.mVirtualDeviceLock) {
                                try {
                                    if (virtualDeviceImpl.mVirtualDisplays.contains(i4)) {
                                        if ((((DisplayManager)
                                                                        virtualDeviceImpl.mContext
                                                                                .getSystemService(
                                                                                        DisplayManager
                                                                                                .class))
                                                                .getDisplay(i4)
                                                                .getFlags()
                                                        & 2)
                                                == 0) {
                                            virtualDeviceImpl.showToastWhereUidIsRunning(
                                                    i5,
                                                    virtualDeviceImpl.mContext.getString(17043439),
                                                    virtualDeviceImpl.mContext.getMainLooper());
                                            if (android.companion.virtualdevice.flags.Flags
                                                    .metricsCollection()) {
                                                Counter.logIncrementWithUid(
                                                        "virtual_devices.value_secure_window_blocked_count",
                                                        virtualDeviceImpl.mAttributionSource
                                                                .getUid());
                                            }
                                        }
                                    }
                                } finally {
                                }
                            }
                        }
                    });
        }
        if (CompatChanges.isChangeEnabled(
                201712607L,
                activityInfo.packageName,
                UserHandle.getUserHandleForUid(activityInfo.applicationInfo.uid))) {
            return true;
        }
        if (i3 == 0 && (524288 & i2) == 0) {
            return true;
        }
        notifyActivityBlocked(activityInfo);
        return false;
    }

    public final void logActivityLaunchBlocked(String str) {
        Slog.d(
                "GenericWindowPolicyController",
                "Virtual device activity launch disallowed on display "
                        + waitAndGetDisplayId()
                        + ", reason: "
                        + str);
    }

    public final void notifyActivityBlocked(ActivityInfo activityInfo) {
        VirtualDeviceImpl$$ExternalSyntheticLambda2 virtualDeviceImpl$$ExternalSyntheticLambda2;
        int waitAndGetDisplayId = waitAndGetDisplayId();
        if (!waitAndGetIsMirrorDisplay()
                && (virtualDeviceImpl$$ExternalSyntheticLambda2 = this.mActivityBlockedCallback)
                        != null
                && waitAndGetDisplayId != -1) {
            VirtualDeviceImpl virtualDeviceImpl = virtualDeviceImpl$$ExternalSyntheticLambda2.f$0;
            virtualDeviceImpl.mContext.startActivityAsUser(
                    BlockedAppStreamingActivity.createIntent(
                                    activityInfo,
                                    virtualDeviceImpl.mAssociationInfo.getDisplayName())
                            .addFlags(268468224),
                    ActivityOptions.makeBasic().setLaunchDisplayId(waitAndGetDisplayId).toBundle(),
                    UserHandle.SYSTEM);
        }
        if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
            Counter.logIncrementWithUid(
                    "virtual_devices.value_activity_blocked_count",
                    this.mAttributionSource.getUid());
        }
    }

    public final void onRunningAppsChanged(final ArraySet arraySet) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            try {
                this.mRunningUids.clear();
                this.mRunningUids.addAll(arraySet);
                final int waitAndGetDisplayId = waitAndGetDisplayId();
                if (this.mActivityListener != null
                        && this.mRunningUids.isEmpty()
                        && waitAndGetDisplayId != -1) {
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    GenericWindowPolicyController genericWindowPolicyController =
                                            GenericWindowPolicyController.this;
                                    genericWindowPolicyController.mActivityListener.onDisplayEmpty(
                                            waitAndGetDisplayId);
                                }
                            });
                }
                if (!this.mRunningAppsChangedListeners.isEmpty()) {
                    final ArraySet arraySet2 = new ArraySet(this.mRunningAppsChangedListeners);
                    this.mHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ArraySet arraySet3 = arraySet2;
                                    ArraySet arraySet4 = arraySet;
                                    Iterator it = arraySet3.iterator();
                                    while (it.hasNext()) {
                                        ((GenericWindowPolicyController.RunningAppsChangedListener)
                                                        it.next())
                                                .onRunningAppsChanged(arraySet4);
                                    }
                                }
                            });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onTopActivityChanged(final ComponentName componentName, int i, final int i2) {
        final int waitAndGetDisplayId = waitAndGetDisplayId();
        if (this.mActivityListener == null || componentName == null || waitAndGetDisplayId == -1) {
            return;
        }
        this.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.companion.virtual.GenericWindowPolicyController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GenericWindowPolicyController genericWindowPolicyController =
                                GenericWindowPolicyController.this;
                        genericWindowPolicyController.mActivityListener.onTopActivityChanged(
                                waitAndGetDisplayId, componentName, i2);
                    }
                });
    }

    public final void setShowInHostDeviceRecents(boolean z) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            this.mShowTasksInHostDeviceRecents = z;
        }
    }

    public final void unregisterRunningAppsChangedListener(
            RunningAppsChangedListener runningAppsChangedListener) {
        synchronized (this.mGenericWindowPolicyControllerLock) {
            this.mRunningAppsChangedListeners.remove(runningAppsChangedListener);
        }
    }

    public final int waitAndGetDisplayId() {
        try {
            if (this.mDisplayIdSetLatch.await(10L, TimeUnit.SECONDS)) {
                return this.mDisplayId;
            }
            Slog.e(
                    "GenericWindowPolicyController",
                    "Timed out while waiting for GWPC displayId to be set.");
            return -1;
        } catch (InterruptedException unused) {
            Slog.e(
                    "GenericWindowPolicyController",
                    "Interrupted while waiting for GWPC displayId to be set.");
            return -1;
        }
    }

    public final boolean waitAndGetIsMirrorDisplay() {
        try {
            if (this.mDisplayIdSetLatch.await(10L, TimeUnit.SECONDS)) {
                return this.mIsMirrorDisplay;
            }
            Slog.e(
                    "GenericWindowPolicyController",
                    "Timed out while waiting for GWPC isMirrorDisplay to be set.");
            return false;
        } catch (InterruptedException unused) {
            Slog.e(
                    "GenericWindowPolicyController",
                    "Interrupted while waiting for GWPC isMirrorDisplay to be set.");
            return false;
        }
    }
}
