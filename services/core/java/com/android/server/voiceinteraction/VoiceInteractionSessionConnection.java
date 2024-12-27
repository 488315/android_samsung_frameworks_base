package com.android.server.voiceinteraction;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AppOpsManager;
import android.app.IActivityTaskManager;
import android.app.UriGrantsManager;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.voice.IVoiceInteractionSession;
import android.service.voice.IVoiceInteractionSessionService;
import android.service.voice.VisibleActivityInfo;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.IWindowManager;

import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.statusbar.IStatusBar;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.am.AssistDataRequester;
import com.android.server.power.LowPowerStandbyController;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.statusbar.StatusBarManagerService;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.wm.ActivityAssistInfo;
import com.android.server.wm.ActivityTaskManagerInternal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class VoiceInteractionSessionConnection
        implements ServiceConnection, AssistDataRequester.AssistDataRequesterCallbacks {
    public static final int POWER_BOOST_TIMEOUT_MS =
            Integer.parseInt(System.getProperty("vendor.powerhal.interaction.max", "200"));
    public final IActivityTaskManager mActivityTaskManager;
    public final AssistDataRequester mAssistDataRequester;
    public final Intent mBindIntent;
    public boolean mBound;
    public final Callback mCallback;
    public final int mCallingUid;
    public boolean mCanceled;
    public final Context mContext;
    public final Handler mFgHandler;
    public final AnonymousClass2 mFullConnection;
    public boolean mFullyBound;
    public final Handler mHandler;
    public final IWindowManager mIWindowManager;
    public IVoiceInteractor mInteractor;
    public boolean mListeningVisibleActivity;
    public final Object mLock;
    public boolean mLowPowerStandbyAllowlisted;
    public final LowPowerStandbyController.LocalService mLowPowerStandbyControllerInternal;
    public List mPendingHandleAssistWithoutData;
    public final ArrayList mPendingShowCallbacks;
    public final IBinder mPermissionOwner;
    public final PowerManagerInternal mPowerManagerInternal;
    public final VoiceInteractionSessionConnection$$ExternalSyntheticLambda0
            mRemoveFromLowPowerStandbyAllowlistRunnable;
    public final ScheduledExecutorService mScheduledExecutorService;
    public IVoiceInteractionSessionService mService;
    public IVoiceInteractionSession mSession;
    public final ComponentName mSessionComponentName;
    public PowerBoostSetter mSetPowerBoostRunnable;
    public Bundle mShowArgs;
    public final AnonymousClass3 mShowAssistDisclosureRunnable;
    public final AnonymousClass1 mShowCallback;
    public int mShowFlags;
    public boolean mShown;
    public final IBinder mToken;
    public final UriGrantsManagerInternal mUgmInternal;
    public final int mUser;
    public final ArrayMap mVisibleActivityInfoForToken;

    /* renamed from: com.android.server.voiceinteraction.VoiceInteractionSessionConnection$2, reason: invalid class name */
    public final class AnonymousClass2 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {}

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {}
    }

    /* renamed from: com.android.server.voiceinteraction.VoiceInteractionSessionConnection$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {
            IStatusBar iStatusBar;
            StatusBarManagerInternal statusBarManagerInternal =
                    (StatusBarManagerInternal)
                            LocalServices.getService(StatusBarManagerInternal.class);
            if (statusBarManagerInternal == null
                    || (iStatusBar = StatusBarManagerService.this.mBar) == null) {
                return;
            }
            try {
                iStatusBar.showAssistDisclosure();
            } catch (RemoteException unused) {
            }
        }
    }

    public interface Callback {}

    public final class PowerBoostSetter implements Runnable {
        public boolean mCanceled;
        public final Instant mExpiryTime;

        public PowerBoostSetter(Instant instant) {
            this.mExpiryTime = instant;
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (VoiceInteractionSessionConnection.this.mLock) {
                try {
                    if (this.mCanceled) {
                        return;
                    }
                    if (Instant.now().isBefore(this.mExpiryTime)) {
                        VoiceInteractionSessionConnection.this.mPowerManagerInternal.setPowerBoost(
                                0, 300);
                        VoiceInteractionSessionConnection voiceInteractionSessionConnection =
                                VoiceInteractionSessionConnection.this;
                        PowerBoostSetter powerBoostSetter =
                                voiceInteractionSessionConnection.mSetPowerBoostRunnable;
                        if (powerBoostSetter != null) {
                            voiceInteractionSessionConnection.mFgHandler.postDelayed(
                                    powerBoostSetter,
                                    VoiceInteractionSessionConnection.POWER_BOOST_TIMEOUT_MS);
                        }
                    } else {
                        Slog.w(
                                "VoiceInteractionServiceManager",
                                "Reset power boost INTERACTION because reaching max timeout.");
                        VoiceInteractionSessionConnection.this.mPowerManagerInternal.setPowerBoost(
                                0, -1);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.voiceinteraction.VoiceInteractionSessionConnection$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.voiceinteraction.VoiceInteractionSessionConnection$1] */
    public VoiceInteractionSessionConnection(
            Object obj,
            ComponentName componentName,
            int i,
            Context context,
            Callback callback,
            int i2,
            Handler handler) {
        Binder binder = new Binder();
        this.mToken = binder;
        this.mPendingShowCallbacks = new ArrayList();
        this.mPendingHandleAssistWithoutData = new ArrayList();
        this.mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.mVisibleActivityInfoForToken = new ArrayMap();
        this.mRemoveFromLowPowerStandbyAllowlistRunnable =
                new Runnable() { // from class:
                                 // com.android.server.voiceinteraction.VoiceInteractionSessionConnection$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VoiceInteractionSessionConnection.this.removeFromLowPowerStandbyAllowlist();
                    }
                };
        this.mShowCallback =
                new IVoiceInteractionSessionShowCallback
                        .Stub() { // from class:
                                  // com.android.server.voiceinteraction.VoiceInteractionSessionConnection.1
                    public final void onFailed() {
                        synchronized (VoiceInteractionSessionConnection.this.mLock) {
                            VoiceInteractionSessionConnection voiceInteractionSessionConnection =
                                    VoiceInteractionSessionConnection.this;
                            for (int i3 = 0;
                                    i3
                                            < voiceInteractionSessionConnection
                                                    .mPendingShowCallbacks.size();
                                    i3++) {
                                try {
                                    ((IVoiceInteractionSessionShowCallback)
                                                    voiceInteractionSessionConnection
                                                            .mPendingShowCallbacks.get(i3))
                                            .onFailed();
                                } catch (RemoteException unused) {
                                }
                            }
                            voiceInteractionSessionConnection.mPendingShowCallbacks.clear();
                        }
                    }

                    public final void onShown() {
                        synchronized (VoiceInteractionSessionConnection.this.mLock) {
                            VoiceInteractionSessionConnection voiceInteractionSessionConnection =
                                    VoiceInteractionSessionConnection.this;
                            for (int i3 = 0;
                                    i3
                                            < voiceInteractionSessionConnection
                                                    .mPendingShowCallbacks.size();
                                    i3++) {
                                try {
                                    ((IVoiceInteractionSessionShowCallback)
                                                    voiceInteractionSessionConnection
                                                            .mPendingShowCallbacks.get(i3))
                                            .onShown();
                                } catch (RemoteException unused) {
                                }
                            }
                            voiceInteractionSessionConnection.mPendingShowCallbacks.clear();
                        }
                    }
                };
        this.mFullConnection = new AnonymousClass2();
        this.mShowAssistDisclosureRunnable = new AnonymousClass3();
        this.mLock = obj;
        this.mSessionComponentName = componentName;
        this.mUser = i;
        this.mContext = context;
        this.mCallback = callback;
        this.mCallingUid = i2;
        this.mHandler = handler;
        this.mActivityTaskManager = ActivityTaskManager.getService();
        ActivityManager.getService();
        UriGrantsManagerInternal uriGrantsManagerInternal =
                (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        this.mUgmInternal = uriGrantsManagerInternal;
        IWindowManager asInterface =
                IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        this.mIWindowManager = asInterface;
        this.mPowerManagerInternal =
                (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        this.mLowPowerStandbyControllerInternal =
                (LowPowerStandbyController.LocalService)
                        LocalServices.getService(LowPowerStandbyController.LocalService.class);
        this.mFgHandler = FgThread.getHandler();
        this.mAssistDataRequester =
                new AssistDataRequester(
                        context,
                        asInterface,
                        (AppOpsManager) context.getSystemService("appops"),
                        this,
                        obj,
                        50);
        this.mPermissionOwner =
                ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal)
                        .newUriPermissionOwner(
                                "voicesession:" + componentName.flattenToShortString());
        Intent intent = new Intent("android.service.voice.VoiceInteractionService");
        this.mBindIntent = intent;
        intent.setComponent(componentName);
        boolean bindServiceAsUser =
                context.bindServiceAsUser(intent, this, 1048625, new UserHandle(i));
        this.mBound = bindServiceAsUser;
        if (!bindServiceAsUser) {
            Slog.w(
                    "VoiceInteractionServiceManager",
                    "Failed binding to voice interaction session service " + componentName);
        } else {
            try {
                asInterface.addWindowToken(binder, 2031, 0, (Bundle) null);
            } catch (RemoteException e) {
                Slog.w("VoiceInteractionServiceManager", "Failed adding window token", e);
            }
        }
    }

    public static ArrayMap getTopVisibleActivityInfosLocked() {
        ArrayList arrayList =
                (ArrayList)
                        ((ActivityTaskManagerInternal)
                                        LocalServices.getService(ActivityTaskManagerInternal.class))
                                .getTopVisibleActivities();
        if (arrayList.isEmpty()) {
            Slog.w("VoiceInteractionServiceManager", "no visible activity");
            return null;
        }
        int size = arrayList.size();
        ArrayMap arrayMap = new ArrayMap(size);
        for (int i = 0; i < size; i++) {
            ActivityAssistInfo activityAssistInfo = (ActivityAssistInfo) arrayList.get(i);
            arrayMap.put(
                    activityAssistInfo.mActivityToken,
                    new VisibleActivityInfo(
                            activityAssistInfo.mTaskId, activityAssistInfo.mAssistToken));
        }
        return arrayMap;
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public final boolean canHandleReceivedAssistDataLocked() {
        return this.mSession != null;
    }

    public final void cancelLocked(boolean z) {
        this.mListeningVisibleActivity = false;
        this.mVisibleActivityInfoForToken.clear();
        hideLocked();
        this.mCanceled = true;
        if (this.mBound) {
            IVoiceInteractionSession iVoiceInteractionSession = this.mSession;
            if (iVoiceInteractionSession != null) {
                try {
                    iVoiceInteractionSession.destroy();
                } catch (RemoteException unused) {
                    Slog.w(
                            "VoiceInteractionServiceManager",
                            "Voice interation session already dead");
                }
            }
            if (z && this.mSession != null) {
                try {
                    ActivityTaskManager.getService().finishVoiceTask(this.mSession);
                } catch (RemoteException unused2) {
                }
            }
            this.mContext.unbindService(this);
            try {
                this.mIWindowManager.removeWindowToken(this.mToken, 0);
            } catch (RemoteException e) {
                Slog.w("VoiceInteractionServiceManager", "Failed removing window token", e);
            }
            this.mBound = false;
            this.mService = null;
            this.mSession = null;
            this.mInteractor = null;
        }
        if (this.mFullyBound) {
            this.mContext.unbindService(this.mFullConnection);
            this.mFullyBound = false;
        }
    }

    public final void doHandleAssistWithoutData(List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ActivityAssistInfo activityAssistInfo = (ActivityAssistInfo) list.get(i);
            try {
                this.mSession.handleAssist(
                        activityAssistInfo.mTaskId,
                        activityAssistInfo.mAssistToken,
                        (Bundle) null,
                        (AssistStructure) null,
                        (AssistContent) null,
                        i,
                        size);
            } catch (RemoteException unused) {
            }
        }
    }

    public final int getUserDisabledShowContextLocked() {
        int i =
                Settings.Secure.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "assist_structure_enabled",
                                        1,
                                        this.mUser)
                                == 0
                        ? 1
                        : 0;
        return Settings.Secure.getIntForUser(
                                this.mContext.getContentResolver(),
                                "assist_screenshot_enabled",
                                1,
                                this.mUser)
                        == 0
                ? i | 2
                : i;
    }

    public final void grantClipDataPermissions(
            ClipData clipData, int i, int i2, int i3, String str) {
        int itemCount = clipData.getItemCount();
        for (int i4 = 0; i4 < itemCount; i4++) {
            ClipData.Item itemAt = clipData.getItemAt(i4);
            if (itemAt.getUri() != null) {
                grantUriPermission(itemAt.getUri(), i, str, i2);
            }
            Intent intent = itemAt.getIntent();
            if (intent != null && intent.getData() != null) {
                grantUriPermission(intent.getData(), i, str, i2);
            }
        }
    }

    public final void grantUriPermission(Uri uri, int i, String str, int i2) {
        if ("content".equals(uri.getScheme())) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    UriGrantsManagerInternal uriGrantsManagerInternal = this.mUgmInternal;
                    ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal)
                            .checkGrantUriPermission(
                                    i2,
                                    null,
                                    ContentProvider.getUriWithoutUserId(uri),
                                    i,
                                    ContentProvider.getUserIdFromUri(
                                            uri, UserHandle.getUserId(i2)));
                    int userIdFromUri = ContentProvider.getUserIdFromUri(uri, this.mUser);
                    UriGrantsManager.getService()
                            .grantUriPermissionFromOwner(
                                    this.mPermissionOwner,
                                    i2,
                                    str,
                                    ContentProvider.getUriWithoutUserId(uri),
                                    1,
                                    userIdFromUri,
                                    this.mUser);
                } catch (RemoteException unused) {
                } catch (SecurityException e) {
                    Slog.w("VoiceInteractionServiceManager", "Can't propagate permission", e);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void handleVisibleActivitiesLocked(int i, IBinder iBinder) {
        VisibleActivityInfo visibleActivityInfo;
        boolean z;
        if (this.mListeningVisibleActivity
                && this.mShown
                && !this.mCanceled
                && this.mSession != null) {
            if (i != 1 && i != 2) {
                z = false;
                if (i == 3) {
                    ArrayMap topVisibleActivityInfosLocked = getTopVisibleActivityInfosLocked();
                    if ((topVisibleActivityInfosLocked != null
                                            ? (VisibleActivityInfo)
                                                    topVisibleActivityInfosLocked.get(iBinder)
                                            : null)
                                    != null
                            || (visibleActivityInfo =
                                            (VisibleActivityInfo)
                                                    this.mVisibleActivityInfoForToken.get(iBinder))
                                    == null) {
                        return;
                    }
                } else if (i != 4) {
                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                            i,
                            "notifyActivityEventChangedLocked unexpected type=",
                            "VoiceInteractionServiceManager");
                    return;
                } else {
                    visibleActivityInfo =
                            (VisibleActivityInfo) this.mVisibleActivityInfoForToken.get(iBinder);
                    if (visibleActivityInfo == null) {
                        return;
                    }
                }
            } else {
                if (this.mVisibleActivityInfoForToken.containsKey(iBinder)) {
                    return;
                }
                ArrayMap topVisibleActivityInfosLocked2 = getTopVisibleActivityInfosLocked();
                VisibleActivityInfo visibleActivityInfo2 =
                        topVisibleActivityInfosLocked2 != null
                                ? (VisibleActivityInfo) topVisibleActivityInfosLocked2.get(iBinder)
                                : null;
                if (visibleActivityInfo2 == null) {
                    return;
                }
                visibleActivityInfo = visibleActivityInfo2;
                z = true;
            }
            try {
                this.mSession.notifyVisibleActivityInfoChanged(visibleActivityInfo, z ? 1 : 2);
            } catch (RemoteException unused) {
            }
            if (z) {
                this.mVisibleActivityInfoForToken.put(iBinder, visibleActivityInfo);
            } else {
                this.mVisibleActivityInfoForToken.remove(iBinder);
            }
        }
    }

    public final boolean hideLocked() {
        if (!this.mBound) {
            return false;
        }
        if (this.mShown) {
            this.mShown = false;
            this.mShowArgs = null;
            this.mShowFlags = 0;
            AssistDataRequester assistDataRequester = this.mAssistDataRequester;
            assistDataRequester.mCanceled = true;
            assistDataRequester.mPendingDataCount = 0;
            assistDataRequester.mPendingScreenshotCount = 0;
            assistDataRequester.mAssistData.clear();
            assistDataRequester.mAssistScreenshot.clear();
            this.mPendingShowCallbacks.clear();
            IVoiceInteractionSession iVoiceInteractionSession = this.mSession;
            if (iVoiceInteractionSession != null) {
                try {
                    iVoiceInteractionSession.hide();
                } catch (RemoteException unused) {
                }
            }
            ((UriGrantsManagerService.LocalService) this.mUgmInternal)
                    .revokeUriPermissionFromOwner(this.mPermissionOwner, null, 3, this.mUser);
            if (this.mSession != null) {
                try {
                    ActivityTaskManager.getService().finishVoiceTask(this.mSession);
                } catch (RemoteException unused2) {
                }
            }
            PowerBoostSetter powerBoostSetter = this.mSetPowerBoostRunnable;
            if (powerBoostSetter != null) {
                synchronized (VoiceInteractionSessionConnection.this.mLock) {
                    powerBoostSetter.mCanceled = true;
                }
                this.mSetPowerBoostRunnable = null;
            }
            this.mPowerManagerInternal.setPowerBoost(0, -1);
            if (this.mLowPowerStandbyControllerInternal != null) {
                removeFromLowPowerStandbyAllowlist();
            }
            VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl =
                    (VoiceInteractionManagerServiceImpl) this.mCallback;
            VoiceInteractionManagerService.VoiceInteractionManagerServiceStub
                    voiceInteractionManagerServiceStub =
                            voiceInteractionManagerServiceImpl.mServiceStub;
            synchronized (voiceInteractionManagerServiceStub) {
                int beginBroadcast =
                        VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners
                                .beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        VoiceInteractionManagerService.this
                                .mVoiceInteractionSessionListeners
                                .getBroadcastItem(i)
                                .onVoiceSessionHidden();
                    } catch (RemoteException e) {
                        Slog.e(
                                "VoiceInteractionManager",
                                "Error delivering voice interaction closed event.",
                                e);
                    }
                }
                VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners
                        .finishBroadcast();
            }
            voiceInteractionManagerServiceImpl.mServiceStub.setSessionWindowVisible(
                    this.mToken, false);
        }
        if (this.mFullyBound) {
            this.mContext.unbindService(this.mFullConnection);
            this.mFullyBound = false;
        }
        return true;
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public final void onAssistDataReceivedLocked(int i, int i2, Bundle bundle) {
        ClipData clipData;
        IVoiceInteractionSession iVoiceInteractionSession = this.mSession;
        if (iVoiceInteractionSession == null) {
            return;
        }
        try {
            if (bundle == null) {
                iVoiceInteractionSession.handleAssist(
                        -1,
                        (IBinder) null,
                        (Bundle) null,
                        (AssistStructure) null,
                        (AssistContent) null,
                        0,
                        0);
            } else {
                int i3 = bundle.getInt("taskId");
                IBinder binder = bundle.getBinder("activityId");
                Bundle bundle2 = bundle.getBundle("data");
                AssistStructure assistStructure =
                        (AssistStructure) bundle.getParcelable("structure", AssistStructure.class);
                AssistContent assistContent =
                        (AssistContent) bundle.getParcelable("content", AssistContent.class);
                int i4 =
                        bundle2 != null
                                ? bundle2.getInt("android.intent.extra.ASSIST_UID", -1)
                                : -1;
                if (i4 >= 0 && assistContent != null) {
                    Intent intent = assistContent.getIntent();
                    if (intent != null
                            && (clipData = intent.getClipData()) != null
                            && Intent.isAccessUriMode(intent.getFlags())) {
                        grantClipDataPermissions(
                                clipData,
                                intent.getFlags(),
                                i4,
                                this.mCallingUid,
                                this.mSessionComponentName.getPackageName());
                    }
                    ClipData clipData2 = assistContent.getClipData();
                    if (clipData2 != null) {
                        grantClipDataPermissions(
                                clipData2,
                                1,
                                i4,
                                this.mCallingUid,
                                this.mSessionComponentName.getPackageName());
                    }
                }
                this.mSession.handleAssist(
                        i3, binder, bundle2, assistStructure, assistContent, i, i2);
            }
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public final void onAssistScreenshotReceivedLocked(Bitmap bitmap) {
        IVoiceInteractionSession iVoiceInteractionSession = this.mSession;
        if (iVoiceInteractionSession == null) {
            return;
        }
        try {
            iVoiceInteractionSession.handleScreenshot(bitmap);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.mLock) {
            IVoiceInteractionSessionService asInterface =
                    IVoiceInteractionSessionService.Stub.asInterface(iBinder);
            this.mService = asInterface;
            if (!this.mCanceled) {
                try {
                    asInterface.newSession(this.mToken, this.mShowArgs, this.mShowFlags);
                } catch (RemoteException e) {
                    Slog.w("VoiceInteractionServiceManager", "Failed adding window token", e);
                }
            }
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl =
                (VoiceInteractionManagerServiceImpl) this.mCallback;
        synchronized (voiceInteractionManagerServiceImpl.mServiceStub) {
            voiceInteractionManagerServiceImpl.finishLocked(this.mToken, false);
        }
        synchronized (this.mLock) {
            this.mService = null;
        }
    }

    public final void removeFromLowPowerStandbyAllowlist() {
        synchronized (this.mLock) {
            try {
                if (this.mLowPowerStandbyAllowlisted) {
                    this.mFgHandler.removeCallbacks(
                            this.mRemoveFromLowPowerStandbyAllowlistRunnable);
                    LowPowerStandbyController.LocalService localService =
                            this.mLowPowerStandbyControllerInternal;
                    LowPowerStandbyController.m793$$Nest$mremoveFromAllowlistInternal(
                            LowPowerStandbyController.this, this.mCallingUid, 1);
                    this.mLowPowerStandbyAllowlisted = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
