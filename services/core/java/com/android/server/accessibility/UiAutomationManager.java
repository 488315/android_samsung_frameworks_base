package com.android.server.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityTrace;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.Slog;
import android.view.accessibility.AccessibilityEvent;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.Preconditions;
import com.android.server.utils.Slogf;
import com.android.server.wm.WindowManagerInternal;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UiAutomationManager {
    public static final ComponentName COMPONENT_NAME =
            new ComponentName("com.android.server.accessibility", "UiAutomation");
    public final Object mLock;
    public AbstractAccessibilityServiceConnection.SystemSupport mSystemSupport;
    public int mUiAutomationFlags;
    public UiAutomationService mUiAutomationService;
    public IBinder mUiAutomationServiceOwner;
    public final AnonymousClass1 mUiAutomationServiceOwnerDeathRecipient =
            new IBinder
                    .DeathRecipient() { // from class:
                                        // com.android.server.accessibility.UiAutomationManager.1
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    UiAutomationManager.this.mUiAutomationServiceOwner.unlinkToDeath(this, 0);
                    UiAutomationManager uiAutomationManager = UiAutomationManager.this;
                    uiAutomationManager.mUiAutomationServiceOwner = null;
                    uiAutomationManager.destroyUiAutomationService();
                    Slog.v("UiAutomationManager", "UiAutomation service owner died");
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UiAutomationService extends AbstractAccessibilityServiceConnection {
        public final Handler mMainHandler;

        public UiAutomationService(
                Context context,
                AccessibilityServiceInfo accessibilityServiceInfo,
                int i,
                Handler handler,
                Object obj,
                AccessibilitySecurityPolicy accessibilitySecurityPolicy,
                AbstractAccessibilityServiceConnection.SystemSupport systemSupport,
                AccessibilityTrace accessibilityTrace,
                WindowManagerInternal windowManagerInternal,
                SystemActionPerformer systemActionPerformer,
                AccessibilityWindowManager accessibilityWindowManager) {
            super(
                    context,
                    UiAutomationManager.COMPONENT_NAME,
                    accessibilityServiceInfo,
                    i,
                    handler,
                    obj,
                    accessibilitySecurityPolicy,
                    systemSupport,
                    accessibilityTrace,
                    windowManagerInternal,
                    systemActionPerformer,
                    accessibilityWindowManager);
            boolean z = handler.getLooper() == Looper.getMainLooper();
            if (Build.IS_USERDEBUG || Build.IS_ENG) {
                Preconditions.checkArgument(z, "UiAutomationService must use the main handler");
            } else if (!z) {
                Slog.e("UiAutomationManager", "UiAutomationService must use the main handler");
            }
            this.mMainHandler = handler;
            this.mDisplayTypes = 3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            UiAutomationManager.this.destroyUiAutomationService();
        }

        public final void disableSelf() {}

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(this.mContext, "UiAutomationManager", printWriter)) {
                synchronized (this.mLock) {
                    printWriter.append(
                            (CharSequence)
                                    ("Ui Automation[eventTypes="
                                            + AccessibilityEvent.eventTypeToString(
                                                    this.mEventTypes)));
                    printWriter.append(
                            (CharSequence) (", notificationTimeout=" + this.mNotificationTimeout));
                    printWriter.append("]");
                }
            }
        }

        public final int getSoftKeyboardShowMode() {
            return 0;
        }

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        public final boolean hasRightsToCurrentUserLocked() {
            return true;
        }

        public final boolean isAccessibilityButtonAvailable() {
            return false;
        }

        @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
        public final boolean isCapturingFingerprintGestures() {
            return false;
        }

        @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
        public final void onFingerprintGesture(int i) {}

        @Override // com.android.server.accessibility.FingerprintGestureDispatcher.FingerprintGestureClient
        public final void onFingerprintGestureDetectionActiveChanged(boolean z) {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {}

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {}

        public final int setInputMethodEnabled(String str, boolean z) {
            return 2;
        }

        public final boolean setSoftKeyboardShowMode(int i) {
            return false;
        }

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        public final boolean supportsFlagForNotImportantViews(
                AccessibilityServiceInfo accessibilityServiceInfo) {
            return true;
        }

        public final boolean switchToInputMethod(String str) {
            return false;
        }

        @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
        public final void takeScreenshot(int i, RemoteCallback remoteCallback) {}
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.accessibility.UiAutomationManager$1] */
    public UiAutomationManager(Object obj) {
        this.mLock = obj;
    }

    public final void destroyUiAutomationService() {
        synchronized (this.mLock) {
            try {
                UiAutomationService uiAutomationService = this.mUiAutomationService;
                if (uiAutomationService != null) {
                    uiAutomationService
                            .mServiceInterface
                            .asBinder()
                            .unlinkToDeath(this.mUiAutomationService, 0);
                    this.mUiAutomationService.onRemoved();
                    this.mUiAutomationService.resetLocked();
                    this.mUiAutomationService = null;
                    IBinder iBinder = this.mUiAutomationServiceOwner;
                    if (iBinder != null) {
                        iBinder.unlinkToDeath(this.mUiAutomationServiceOwnerDeathRecipient, 0);
                        this.mUiAutomationServiceOwner = null;
                    }
                }
                this.mUiAutomationFlags = 0;
                ((AccessibilityManagerService) this.mSystemSupport)
                        .onClientChangeLocked(false, false);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerUiTestAutomationServiceLocked(
            IBinder iBinder,
            IAccessibilityServiceClient iAccessibilityServiceClient,
            Context context,
            AccessibilityServiceInfo accessibilityServiceInfo,
            int i,
            Handler handler,
            AccessibilitySecurityPolicy accessibilitySecurityPolicy,
            AbstractAccessibilityServiceConnection.SystemSupport systemSupport,
            AccessibilityTrace accessibilityTrace,
            WindowManagerInternal windowManagerInternal,
            SystemActionPerformer systemActionPerformer,
            AccessibilityWindowManager accessibilityWindowManager,
            int i2) {
        accessibilityServiceInfo.setComponentName(COMPONENT_NAME);
        Slogf.i(
                "UiAutomationManager",
                "Registering UiTestAutomationService (id=%s, flags=0x%x) when called by user %d",
                accessibilityServiceInfo.getId(),
                Integer.valueOf(i2),
                Integer.valueOf(Binder.getCallingUserHandle().getIdentifier()));
        if (this.mUiAutomationService != null) {
            throw new IllegalStateException(
                    "UiAutomationService "
                            + this.mUiAutomationService.mServiceInterface
                            + "already registered!");
        }
        try {
            iBinder.linkToDeath(this.mUiAutomationServiceOwnerDeathRecipient, 0);
            this.mUiAutomationFlags = i2;
            this.mSystemSupport = systemSupport;
            if ((i2 & 2) == 0) {
                UiAutomationService uiAutomationService =
                        new UiAutomationService(
                                context,
                                accessibilityServiceInfo,
                                i,
                                handler,
                                this.mLock,
                                accessibilitySecurityPolicy,
                                systemSupport,
                                accessibilityTrace,
                                windowManagerInternal,
                                systemActionPerformer,
                                accessibilityWindowManager);
                this.mUiAutomationService = uiAutomationService;
                this.mUiAutomationServiceOwner = iBinder;
                uiAutomationService.mServiceInterface = iAccessibilityServiceClient;
                try {
                    iAccessibilityServiceClient
                            .asBinder()
                            .linkToDeath(this.mUiAutomationService, 0);
                    final UiAutomationService uiAutomationService2 = this.mUiAutomationService;
                    uiAutomationService2.mMainHandler.post(
                            new Runnable() { // from class:
                                // com.android.server.accessibility.UiAutomationManager$UiAutomationService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    IAccessibilityServiceClient iAccessibilityServiceClient2;
                                    UiAutomationManager.UiAutomationService uiAutomationService3;
                                    UiAutomationManager.UiAutomationService uiAutomationService4 =
                                            UiAutomationManager.UiAutomationService.this;
                                    uiAutomationService4.getClass();
                                    try {
                                        synchronized (uiAutomationService4.mLock) {
                                            try {
                                                iAccessibilityServiceClient2 =
                                                        uiAutomationService4.mServiceInterface;
                                                uiAutomationService3 =
                                                        UiAutomationManager.this
                                                                .mUiAutomationService;
                                                if (iAccessibilityServiceClient2 == null) {
                                                    uiAutomationService4.mService = null;
                                                } else {
                                                    IBinder asBinder =
                                                            iAccessibilityServiceClient2.asBinder();
                                                    uiAutomationService4.mService = asBinder;
                                                    asBinder.linkToDeath(uiAutomationService4, 0);
                                                }
                                            } finally {
                                            }
                                        }
                                        if (iAccessibilityServiceClient2 == null
                                                || uiAutomationService3 == null) {
                                            return;
                                        }
                                        uiAutomationService3.addWindowTokensForAllDisplays();
                                        if (uiAutomationService4.mTrace
                                                .isA11yTracingEnabledForTypes(2L)) {
                                            uiAutomationService4.mTrace.logTrace(
                                                    "UiAutomationService.connectServiceUnknownThread",
                                                    2L,
                                                    "serviceConnection="
                                                            + uiAutomationService4
                                                            + ";connectionId="
                                                            + uiAutomationService4.mId
                                                            + "windowToken="
                                                            + uiAutomationService4
                                                                    .mOverlayWindowTokens.get(0));
                                        }
                                        iAccessibilityServiceClient2.init(
                                                uiAutomationService4,
                                                uiAutomationService4.mId,
                                                (IBinder)
                                                        uiAutomationService4.mOverlayWindowTokens
                                                                .get(0));
                                    } catch (RemoteException e) {
                                        Slog.w(
                                                "UiAutomationManager",
                                                "Error initializing connection",
                                                e);
                                        UiAutomationManager.this.destroyUiAutomationService();
                                    }
                                }
                            });
                } catch (RemoteException e) {
                    Slog.e("UiAutomationManager", "Failed registering death link: " + e);
                    destroyUiAutomationService();
                }
            }
        } catch (RemoteException e2) {
            Slog.e(
                    "UiAutomationManager",
                    "Couldn't register for the death of a UiTestAutomationService!",
                    e2);
        }
    }

    public final boolean suppressingAccessibilityServicesLocked() {
        return !(this.mUiAutomationService == null && (this.mUiAutomationFlags & 2) == 0)
                && (this.mUiAutomationFlags & 1) == 0;
    }

    public final void unregisterUiTestAutomationServiceLocked(
            IAccessibilityServiceClient iAccessibilityServiceClient) {
        UiAutomationService uiAutomationService;
        synchronized (this.mLock) {
            try {
                if ((this.mUiAutomationFlags & 2) == 0
                        && ((uiAutomationService = this.mUiAutomationService) == null
                                || iAccessibilityServiceClient == null
                                || uiAutomationService.mServiceInterface == null
                                || iAccessibilityServiceClient.asBinder()
                                        != this.mUiAutomationService.mServiceInterface
                                                .asBinder())) {
                    throw new IllegalStateException(
                            "UiAutomationService "
                                    + iAccessibilityServiceClient
                                    + " not registered!");
                }
                destroyUiAutomationService();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
