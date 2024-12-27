package com.android.server.clipboard;

import android.R;
import android.app.ActivityManagerInternal;
import android.app.AppOpsManager;
import android.app.IUriGrantsManager;
import android.app.KeyguardManager;
import android.app.UriGrantsManager;
import android.companion.virtual.VirtualDeviceManager;
import android.companion.virtual.flags.Flags;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.IClipboard;
import android.content.IOnPrimaryClipChangedListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserInfo;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IUserManager;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.sec.clipboard.IClipboardService;
import android.sec.clipboard.data.ClipboardConstants;
import android.sec.clipboard.util.ClipboardPolicyObserver;
import android.sec.clipboard.util.SemClipboardPolicy;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SafetyProtectionUtils;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.SparseBooleanArray;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.autofill.AutofillManagerInternal;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLinks;
import android.widget.Toast;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.UiThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.contentcapture.ContentCaptureManagerService;
import com.android.server.semclipboard.SemClipboardService;
import com.android.server.uri.UriGrantsManagerInternal;
import com.android.server.uri.UriGrantsManagerService;
import com.android.server.wm.WindowManagerInternal;

import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class ClipboardService extends SystemService {
    public static final long DEFAULT_CLIPBOARD_TIMEOUT_MILLIS = 3600000;
    public boolean mAllowVirtualDeviceSilos;
    public final ActivityManagerInternal mAmInternal;
    public final AppOpsManager mAppOps;
    public final AutofillManagerInternal mAutofillInternal;
    public final Consumer mClipboardMonitor;
    public final SparseArrayMap mClipboards;
    public final ContentCaptureManagerService.LocalService mContentCaptureInternal;
    public final Context mContext;
    public final Object mLock;
    public int mMaxClassificationLength;
    public final IBinder mPermissionOwner;
    public SemPersonaManager mPersonaManager;
    public final PackageManager mPm;
    public SemClipboardService mSemClipboardService;
    public boolean mShowAccessNotifications;
    public final IUriGrantsManager mUgm;
    public final UriGrantsManagerInternal mUgmInternal;
    public final IUserManager mUm;
    public final VirtualDeviceManager mVdm;
    public final VirtualDeviceManagerInternal mVdmInternal;
    public AnonymousClass2 mVirtualDeviceListener;
    public AnonymousClass1 mVirtualDeviceRemovedReceiver;
    public final WindowManagerInternal mWm;
    public final Handler mWorkerHandler;

    public final class Clipboard {
        public final HashSet activePermissionOwners;
        public final int deviceId;
        public final SparseBooleanArray mNotifiedTextClassifierUids;
        public final SparseBooleanArray mNotifiedUids;
        public String mPrimaryClipPackage;
        public TextClassifier mTextClassifier;
        public ClipData primaryClip;
        public final Map primaryClipByUid;
        public final RemoteCallbackList primaryClipListeners = new RemoteCallbackList();
        public int primaryClipUid;

        public Clipboard(int i) {
            new RemoteCallbackList();
            this.primaryClipByUid = new HashMap();
            this.primaryClipUid = 9999;
            this.mNotifiedUids = new SparseBooleanArray();
            this.mNotifiedTextClassifierUids = new SparseBooleanArray();
            this.activePermissionOwners = new HashSet();
            this.deviceId = i;
        }
    }

    public final class ClipboardImpl extends IClipboard.Stub {
        public final ClipboardClearHandler mClipboardClearHandler;

        public final class ClipboardClearHandler extends Handler {
            public ClipboardClearHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                if (message.what != 101) {
                    Slog.wtf(
                            "ClipboardService",
                            "ClipboardClearHandler received unknown message " + message.what);
                    return;
                }
                int i = message.arg1;
                int i2 = message.arg2;
                int intValue = ((Integer) ((Pair) message.obj).second).intValue();
                synchronized (ClipboardService.this.mLock) {
                    try {
                        Clipboard clipboardLocked =
                                ClipboardService.this.getClipboardLocked(i, intValue);
                        if (clipboardLocked != null && clipboardLocked.primaryClip != null) {
                            FrameworkStatsLog.write(FrameworkStatsLog.CLIPBOARD_CLEARED, 1);
                            ClipboardService.this.setPrimaryClipInternalLocked(
                                    (ClipData) null, i2, intValue, (String) null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public ClipboardImpl() {
            this.mClipboardClearHandler =
                    new ClipboardClearHandler(ClipboardService.this.mWorkerHandler.getLooper());
        }

        public final void addPrimaryClipChangedListener(
                IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener,
                String str,
                String str2,
                int i,
                int i2) {
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            if (m337$$Nest$mgetIntendingDeviceId == -1) {
                StringBuilder m =
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i,
                                m338$$Nest$mgetIntendingUid,
                                "addPrimaryClipChangedListener invalid deviceId for userId:",
                                " uid:",
                                " callingPackage:");
                m.append(str);
                m.append(" requestedDeviceId:");
                m.append(i2);
                Slog.i("ClipboardService", m.toString());
                return;
            }
            synchronized (ClipboardService.this.mLock) {
                try {
                    Clipboard clipboardLocked =
                            ClipboardService.this.getClipboardLocked(
                                    userId, m337$$Nest$mgetIntendingDeviceId);
                    if (clipboardLocked == null) {
                        return;
                    }
                    clipboardLocked.primaryClipListeners.register(
                            iOnPrimaryClipChangedListener,
                            new ListenerInfo(m338$$Nest$mgetIntendingUid, str, str2));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean areClipboardAccessNotificationsEnabledForUser(int i) {
            if (ClipboardService.this
                            .getContext()
                            .checkCallingOrSelfPermission(
                                    "android.permission.MANAGE_CLIPBOARD_ACCESS_NOTIFICATION")
                    != 0) {
                throw new SecurityException(
                        "areClipboardAccessNotificationsEnable requires permission"
                            + " MANAGE_CLIPBOARD_ACCESS_NOTIFICATION");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return Settings.Secure.getIntForUser(
                                ClipboardService.this.getContext().getContentResolver(),
                                "clipboard_show_access_notifications",
                                DeviceConfig.getBoolean(
                                                "clipboard", "show_access_notifications", true)
                                        ? 1
                                        : 0,
                                i)
                        != 0;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void checkAndSetPrimaryClip(
                ClipData clipData, String str, String str2, int i, int i2, String str3) {
            if (clipData == null || clipData.getItemCount() <= 0) {
                throw new IllegalArgumentException("No items");
            }
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            if (ClipboardService.this.clipboardAccessAllowed(
                    30,
                    m338$$Nest$mgetIntendingUid,
                    userId,
                    m337$$Nest$mgetIntendingDeviceId,
                    str,
                    str2,
                    true)) {
                if (ClipboardService.m339$$Nest$mgetSemClipboardService(ClipboardService.this)
                                != null
                        && clipData.getDescription().getExtras() != null
                        && clipData.getDescription().getExtras().getBoolean("direct_clip")
                        && SemClipboardPolicy.getInstance()
                                .canAccessSemClipboard(ClipboardService.this.mContext, str3, i)) {
                    ClipboardService.m339$$Nest$mgetSemClipboardService(ClipboardService.this)
                            .setPrimaryClip(clipData, m338$$Nest$mgetIntendingUid);
                    return;
                }
                synchronized (ClipboardService.this.mLock) {
                    try {
                        Clipboard clipboardLocked =
                                ClipboardService.this.getClipboardLocked(
                                        userId, m337$$Nest$mgetIntendingDeviceId);
                        if (isClipboardAllowed(getPersonaId()) == 0) {
                            clipboardLocked.primaryClip = null;
                            ((HashMap) clipboardLocked.primaryClipByUid).clear();
                            new Handler(Looper.getMainLooper())
                                    .post(
                                            new Runnable() { // from class:
                                                             // com.android.server.clipboard.ClipboardService.ClipboardImpl.1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    Toast.makeText(
                                                                    new ContextThemeWrapper(
                                                                            ClipboardService.this
                                                                                    .mContext,
                                                                            R.style
                                                                                    .Theme
                                                                                    .DeviceDefault
                                                                                    .Light),
                                                                    R.string
                                                                            .config_usbConfirmActivity,
                                                                    0)
                                                            .show();
                                                }
                                            });
                            return;
                        }
                        if (isClipboardShareAllowed(getPersonaId()) == 0) {
                            clipboardLocked.primaryClip = null;
                        } else {
                            ((HashMap) clipboardLocked.primaryClipByUid).clear();
                        }
                        ClipboardService clipboardService = ClipboardService.this;
                        clipboardService.getClass();
                        int itemCount = clipData.getItemCount();
                        for (int i3 = 0; i3 < itemCount; i3++) {
                            ClipData.Item itemAt = clipData.getItemAt(i3);
                            if (itemAt.getUri() != null) {
                                clipboardService.checkUriOwner(
                                        m338$$Nest$mgetIntendingUid, itemAt.getUri());
                            }
                            Intent intent = itemAt.getIntent();
                            if (intent != null && intent.getData() != null) {
                                clipboardService.checkUriOwner(
                                        m338$$Nest$mgetIntendingUid, intent.getData());
                            }
                        }
                        synchronized (ClipboardService.this.mLock) {
                            try {
                                Clipboard clipboardLocked2 =
                                        ClipboardService.this.getClipboardLocked(
                                                userId, m337$$Nest$mgetIntendingDeviceId);
                                if (isClipboardShareAllowed(getPersonaId()) == 0) {
                                    ((HashMap) clipboardLocked2.primaryClipByUid)
                                            .put(Integer.valueOf(Binder.getCallingUid()), clipData);
                                    return;
                                }
                                ClipboardService.this.getClass();
                                clipboardLocked2.primaryClip = clipData;
                                synchronized (ClipboardService.this.mLock) {
                                    try {
                                        scheduleAutoClear(
                                                i,
                                                m338$$Nest$mgetIntendingUid,
                                                m337$$Nest$mgetIntendingDeviceId);
                                        ClipboardService.this.setPrimaryClipInternalLocked(
                                                clipData,
                                                m338$$Nest$mgetIntendingUid,
                                                m337$$Nest$mgetIntendingDeviceId,
                                                str3);
                                        if (ClipboardService.m339$$Nest$mgetSemClipboardService(
                                                        ClipboardService.this)
                                                != null) {
                                            ClipboardService.m339$$Nest$mgetSemClipboardService(
                                                            ClipboardService.this)
                                                    .setPrimaryClip(
                                                            clipData, m338$$Nest$mgetIntendingUid);
                                        }
                                    } finally {
                                    }
                                }
                            } finally {
                            }
                        }
                    } finally {
                    }
                }
            }
        }

        public final void clearPrimaryClip(String str, String str2, int i, int i2) {
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            if (ClipboardService.this.clipboardAccessAllowed(
                    30,
                    m338$$Nest$mgetIntendingUid,
                    userId,
                    m337$$Nest$mgetIntendingDeviceId,
                    str,
                    str2,
                    true)) {
                synchronized (ClipboardService.this.mLock) {
                    this.mClipboardClearHandler.removeEqualMessages(
                            101, new Pair(Integer.valueOf(i), Integer.valueOf(i2)));
                    ClipboardService.this.setPrimaryClipInternalLocked(
                            (ClipData) null,
                            m338$$Nest$mgetIntendingUid,
                            m337$$Nest$mgetIntendingDeviceId,
                            str);
                }
            }
        }

        public final int getPersonaId() {
            ClipboardService clipboardService = ClipboardService.this;
            Context context = clipboardService.mContext;
            if (context != null && clipboardService.mPersonaManager == null) {
                clipboardService.mPersonaManager =
                        (SemPersonaManager) context.getSystemService("persona");
            }
            SemPersonaManager semPersonaManager = ClipboardService.this.mPersonaManager;
            if (semPersonaManager != null) {
                int focusedKnoxId = semPersonaManager.getFocusedKnoxId();
                return focusedKnoxId == 0 ? UserHandle.getCallingUserId() : focusedKnoxId;
            }
            Slog.e("ClipboardService", "personaManager is null!");
            return UserHandle.getCallingUserId();
        }

        public final ClipData getPrimaryClip(String str, String str2, int i, int i2) {
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            if (!ClipboardService.this.clipboardAccessAllowed(
                            29,
                            m338$$Nest$mgetIntendingUid,
                            userId,
                            m337$$Nest$mgetIntendingDeviceId,
                            str,
                            str2,
                            true)
                    || ClipboardService.m340$$Nest$misDeviceLocked(
                            ClipboardService.this, userId, i2)) {
                return null;
            }
            synchronized (ClipboardService.this.mLock) {
                try {
                    if (isClipboardAllowed(getPersonaId()) == 0) {
                        ClipboardService.this.getClipboardLocked(
                                                userId, m337$$Nest$mgetIntendingDeviceId)
                                        .primaryClip =
                                null;
                        ClipboardService.this
                                .getClipboardLocked(userId, m337$$Nest$mgetIntendingDeviceId)
                                .getClass();
                        ((HashMap)
                                        ClipboardService.this.getClipboardLocked(
                                                        userId, m337$$Nest$mgetIntendingDeviceId)
                                                .primaryClipByUid)
                                .clear();
                        return null;
                    }
                    if (isClipboardShareAllowed(getPersonaId()) != 0) {
                        ((HashMap)
                                        ClipboardService.this.getClipboardLocked(
                                                        userId, m337$$Nest$mgetIntendingDeviceId)
                                                .primaryClipByUid)
                                .clear();
                    } else {
                        ClipboardService.this.getClipboardLocked(
                                                userId, m337$$Nest$mgetIntendingDeviceId)
                                        .primaryClip =
                                null;
                        ClipboardService.this
                                .getClipboardLocked(userId, m337$$Nest$mgetIntendingDeviceId)
                                .getClass();
                    }
                    try {
                        ClipboardService.m336$$Nest$maddActiveOwnerLocked(
                                ClipboardService.this,
                                m338$$Nest$mgetIntendingUid,
                                m337$$Nest$mgetIntendingDeviceId,
                                str);
                        if (isClipboardShareAllowed(getPersonaId()) == 0) {
                            return (ClipData)
                                    ((HashMap)
                                                    ClipboardService.this.getClipboardLocked(
                                                                    userId,
                                                                    m337$$Nest$mgetIntendingDeviceId)
                                                            .primaryClipByUid)
                                            .get(Integer.valueOf(Binder.getCallingUid()));
                        }
                        ClipboardService.this.getClass();
                        Clipboard clipboardLocked =
                                ClipboardService.this.getClipboardLocked(
                                        userId, m337$$Nest$mgetIntendingDeviceId);
                        if (clipboardLocked == null) {
                            return null;
                        }
                        ClipboardService.m342$$Nest$mshowAccessNotificationLocked(
                                ClipboardService.this,
                                str,
                                m338$$Nest$mgetIntendingUid,
                                userId,
                                clipboardLocked);
                        ClipboardService.m341$$Nest$mnotifyTextClassifierLocked(
                                ClipboardService.this,
                                clipboardLocked,
                                str,
                                m338$$Nest$mgetIntendingUid);
                        if (clipboardLocked.primaryClip != null) {
                            scheduleAutoClear(
                                    i,
                                    m338$$Nest$mgetIntendingUid,
                                    m337$$Nest$mgetIntendingDeviceId);
                        }
                        return clipboardLocked.primaryClip;
                    } catch (SecurityException unused) {
                        Slog.i(
                                "ClipboardService",
                                "Could not grant permission to primary clip. Clearing clipboard.");
                        ClipboardService.this.setPrimaryClipInternalLocked(
                                (ClipData) null,
                                m338$$Nest$mgetIntendingUid,
                                m337$$Nest$mgetIntendingDeviceId,
                                str);
                        return null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final ClipDescription getPrimaryClipDescription(
                String str, String str2, int i, int i2) {
            ClipData clipData;
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            boolean clipboardAccessAllowed =
                    ClipboardService.this.clipboardAccessAllowed(
                            29,
                            m338$$Nest$mgetIntendingUid,
                            userId,
                            m337$$Nest$mgetIntendingDeviceId,
                            str,
                            str2,
                            false);
            ClipDescription clipDescription = null;
            if (!clipboardAccessAllowed
                    || ClipboardService.m340$$Nest$misDeviceLocked(
                            ClipboardService.this, userId, i2)) {
                return null;
            }
            synchronized (ClipboardService.this.mLock) {
                try {
                    Clipboard clipboardLocked =
                            ClipboardService.this.getClipboardLocked(
                                    userId, m337$$Nest$mgetIntendingDeviceId);
                    if (clipboardLocked != null
                            && (clipData = clipboardLocked.primaryClip) != null) {
                        clipDescription = clipData.getDescription();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return clipDescription;
        }

        public final String getPrimaryClipSource(String str, String str2, int i, int i2) {
            getPrimaryClipSource_enforcePermission();
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            if (!ClipboardService.this.clipboardAccessAllowed(
                            29,
                            m338$$Nest$mgetIntendingUid,
                            userId,
                            m337$$Nest$mgetIntendingDeviceId,
                            str,
                            str2,
                            false)
                    || ClipboardService.m340$$Nest$misDeviceLocked(
                            ClipboardService.this, userId, i2)) {
                return null;
            }
            synchronized (ClipboardService.this.mLock) {
                try {
                    Clipboard clipboardLocked =
                            ClipboardService.this.getClipboardLocked(
                                    userId, m337$$Nest$mgetIntendingDeviceId);
                    if (clipboardLocked == null || clipboardLocked.primaryClip == null) {
                        return null;
                    }
                    return clipboardLocked.mPrimaryClipPackage;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean hasClipboardText(String str, String str2, int i, int i2) {
            ClipData clipData;
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            boolean clipboardAccessAllowed =
                    ClipboardService.this.clipboardAccessAllowed(
                            29,
                            m338$$Nest$mgetIntendingUid,
                            userId,
                            m337$$Nest$mgetIntendingDeviceId,
                            str,
                            str2,
                            false);
            boolean z = false;
            if (!clipboardAccessAllowed
                    || ClipboardService.m340$$Nest$misDeviceLocked(
                            ClipboardService.this, userId, i2)) {
                return false;
            }
            synchronized (ClipboardService.this.mLock) {
                try {
                    Clipboard clipboardLocked =
                            ClipboardService.this.getClipboardLocked(
                                    userId, m337$$Nest$mgetIntendingDeviceId);
                    if (clipboardLocked == null
                            || (clipData = clipboardLocked.primaryClip) == null) {
                        return false;
                    }
                    CharSequence text = clipData.getItemAt(0).getText();
                    if (text != null && text.length() > 0) {
                        z = true;
                    }
                    return z;
                } finally {
                }
            }
        }

        public final boolean hasPrimaryClip(String str, String str2, int i, int i2) {
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int userId = UserHandle.getUserId(m338$$Nest$mgetIntendingUid);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            boolean clipboardAccessAllowed =
                    ClipboardService.this.clipboardAccessAllowed(
                            29,
                            m338$$Nest$mgetIntendingUid,
                            userId,
                            m337$$Nest$mgetIntendingDeviceId,
                            str,
                            str2,
                            false);
            if (!clipboardAccessAllowed
                    || ClipboardService.m340$$Nest$misDeviceLocked(
                            ClipboardService.this, userId, i2)) {
                return false;
            }
            synchronized (ClipboardService.this.mLock) {
                try {
                    Clipboard clipboardLocked =
                            ClipboardService.this.getClipboardLocked(
                                    userId, m337$$Nest$mgetIntendingDeviceId);
                    int isClipboardAllowed = isClipboardAllowed(getPersonaId());
                    int isClipboardShareAllowed = isClipboardShareAllowed(getPersonaId());
                    if (isClipboardAllowed == 0) {
                        clipboardLocked.primaryClip = null;
                        ((HashMap) clipboardLocked.primaryClipByUid).clear();
                        return false;
                    }
                    if (isClipboardShareAllowed == 0) {
                        clipboardLocked.primaryClip = null;
                        return ((HashMap) clipboardLocked.primaryClipByUid)
                                        .get(Integer.valueOf(Binder.getCallingUid()))
                                != null;
                    }
                    ((HashMap) clipboardLocked.primaryClipByUid).clear();
                    Clipboard clipboardLocked2 =
                            ClipboardService.this.getClipboardLocked(
                                    userId, m337$$Nest$mgetIntendingDeviceId);
                    if (clipboardLocked2 != null && clipboardLocked2.primaryClip != null) {
                        r11 = true;
                    }
                    return r11;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int isClipboardAllowed(int i) {
            Context context = ClipboardService.this.mContext;
            if (context == null) {
                return -1;
            }
            return (ClipboardPolicyObserver.getInstance(context).isClipboardAllowed(i)
                            && ClipboardPolicyObserver.getInstance(ClipboardService.this.mContext)
                                    .isPackageAllowed(i))
                    ? 1
                    : 0;
        }

        public final int isClipboardShareAllowed(int i) {
            Context context = ClipboardService.this.mContext;
            if (context == null) {
                return -1;
            }
            return ClipboardPolicyObserver.getInstance(context).isClipboardSharedAllowed(i) ? 1 : 0;
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (RuntimeException e) {
                if (!(e instanceof SecurityException)) {
                    Slog.wtf("clipboard", "Exception: ", e);
                }
                throw e;
            }
        }

        public final void removePrimaryClipChangedListener(
                IOnPrimaryClipChangedListener iOnPrimaryClipChangedListener,
                String str,
                String str2,
                int i,
                int i2) {
            int m338$$Nest$mgetIntendingUid =
                    ClipboardService.m338$$Nest$mgetIntendingUid(ClipboardService.this, str, i);
            int intendingUserId = ClipboardService.this.getIntendingUserId(i, str);
            int m337$$Nest$mgetIntendingDeviceId =
                    ClipboardService.m337$$Nest$mgetIntendingDeviceId(
                            ClipboardService.this, i2, m338$$Nest$mgetIntendingUid);
            if (m337$$Nest$mgetIntendingDeviceId == -1) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i,
                                m338$$Nest$mgetIntendingUid,
                                "removePrimaryClipChangedListener invalid deviceId for userId:",
                                " uid:",
                                " callingPackage:"),
                        str,
                        "ClipboardService");
                return;
            }
            synchronized (ClipboardService.this.mLock) {
                try {
                    Clipboard clipboardLocked =
                            ClipboardService.this.getClipboardLocked(
                                    intendingUserId, m337$$Nest$mgetIntendingDeviceId);
                    if (clipboardLocked != null) {
                        clipboardLocked.primaryClipListeners.unregister(
                                iOnPrimaryClipChangedListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void scheduleAutoClear(int i, int i2, int i3) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (DeviceConfig.getBoolean("clipboard", "auto_clear_enabled", true)) {
                    Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i3));
                    this.mClipboardClearHandler.removeEqualMessages(101, pair);
                    this.mClipboardClearHandler.sendMessageDelayed(
                            Message.obtain(this.mClipboardClearHandler, 101, i, i2, pair),
                            DeviceConfig.getLong(
                                    "clipboard",
                                    "auto_clear_timeout",
                                    ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setClipboardAccessNotificationsEnabledForUser(boolean z, int i) {
            if (ClipboardService.this
                            .getContext()
                            .checkCallingOrSelfPermission(
                                    "android.permission.MANAGE_CLIPBOARD_ACCESS_NOTIFICATION")
                    != 0) {
                throw new SecurityException(
                        "areClipboardAccessNotificationsEnable requires permission"
                            + " MANAGE_CLIPBOARD_ACCESS_NOTIFICATION");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Settings.Secure.putInt(
                        ClipboardService.this
                                .getContext()
                                .createContextAsUser(UserHandle.of(i), 0)
                                .getContentResolver(),
                        "clipboard_show_access_notifications",
                        z ? 1 : 0);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setPrimaryClip(
                ClipData clipData, String str, String str2, int i, int i2) {
            checkAndSetPrimaryClip(clipData, str, str2, i, i2, str);
        }

        public final void setPrimaryClipAsPackage(
                ClipData clipData, String str, String str2, int i, int i2, String str3) {
            setPrimaryClipAsPackage_enforcePermission();
            checkAndSetPrimaryClip(clipData, str, str2, i, i2, str3);
        }
    }

    public final class ListenerInfo {
        public final String mAttributionTag;
        public final String mPackageName;
        public final int mUid;

        public ListenerInfo(int i, String str, String str2) {
            this.mUid = i;
            this.mPackageName = str;
            this.mAttributionTag = str2;
        }
    }

    /* renamed from: -$$Nest$maddActiveOwnerLocked, reason: not valid java name */
    public static void m336$$Nest$maddActiveOwnerLocked(
            ClipboardService clipboardService, int i, int i2, String str) {
        clipboardService.getClass();
        PackageManagerInternal packageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        int callingUserId = UserHandle.getCallingUserId();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!packageManagerInternal.isSameApp(i, callingUserId, 0L, str)) {
                throw new SecurityException("Calling uid " + i + " does not own package " + str);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Clipboard clipboardLocked =
                    clipboardService.getClipboardLocked(UserHandle.getUserId(i), i2);
            if (clipboardLocked == null
                    || clipboardLocked.primaryClip == null
                    || clipboardLocked.activePermissionOwners.contains(str)) {
                return;
            }
            int itemCount = clipboardLocked.primaryClip.getItemCount();
            for (int i3 = 0; i3 < itemCount; i3++) {
                ClipData.Item itemAt = clipboardLocked.primaryClip.getItemAt(i3);
                int i4 = clipboardLocked.primaryClipUid;
                int userId = UserHandle.getUserId(i);
                if (itemAt.getUri() != null) {
                    clipboardService.grantUriPermission(itemAt.getUri(), i4, str, userId);
                }
                Intent intent = itemAt.getIntent();
                if (intent != null && intent.getData() != null) {
                    clipboardService.grantUriPermission(intent.getData(), i4, str, userId);
                }
                if (KnoxCustomManagerService.SAMSUNG_HONEYBOARD_PKG_NAME.equals(str)) {
                    Slog.i("ClipboardService", "adding mcfds");
                    ClipData.Item itemAt2 = clipboardLocked.primaryClip.getItemAt(i3);
                    int i5 = clipboardLocked.primaryClipUid;
                    int userId2 = UserHandle.getUserId(i);
                    if (itemAt2.getUri() != null) {
                        clipboardService.grantUriPermission(
                                itemAt2.getUri(), i5, "com.samsung.android.mcfds", userId2);
                    }
                    Intent intent2 = itemAt2.getIntent();
                    if (intent2 != null && intent2.getData() != null) {
                        clipboardService.grantUriPermission(
                                intent2.getData(), i5, "com.samsung.android.mcfds", userId2);
                    }
                }
            }
            clipboardLocked.activePermissionOwners.add(str);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: -$$Nest$mgetIntendingDeviceId, reason: not valid java name */
    public static int m337$$Nest$mgetIntendingDeviceId(
            ClipboardService clipboardService, int i, int i2) {
        boolean z;
        VirtualDeviceManagerInternal virtualDeviceManagerInternal = clipboardService.mVdmInternal;
        if (virtualDeviceManagerInternal == null) {
            return 0;
        }
        ArraySet deviceIdsForUid = virtualDeviceManagerInternal.getDeviceIdsForUid(i2);
        synchronized (clipboardService.mLock) {
            try {
                if (clipboardService.mAllowVirtualDeviceSilos
                        || (deviceIdsForUid.isEmpty() && i == 0)) {
                    Iterator it = deviceIdsForUid.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = true;
                            break;
                        }
                        if (!clipboardService.deviceUsesDefaultClipboard(
                                ((Integer) it.next()).intValue())) {
                            z = false;
                            break;
                        }
                    }
                    if (i != 0) {
                        int i3 = clipboardService.deviceUsesDefaultClipboard(i) ? 0 : i;
                        if (clipboardService.mVdmInternal.getDeviceOwnerUid(i) == i2
                                || deviceIdsForUid.contains(Integer.valueOf(i))
                                || (i3 == 0 && z)) {
                            return i3;
                        }
                        int intValue = ((Integer) deviceIdsForUid.valueAt(0)).intValue();
                        if (clipboardService.deviceUsesDefaultClipboard(intValue)) {
                            return 0;
                        }
                        return intValue;
                    }
                    if (z) {
                        return 0;
                    }
                }
            } finally {
            }
        }
        return -1;
    }

    /* renamed from: -$$Nest$mgetIntendingUid, reason: not valid java name */
    public static int m338$$Nest$mgetIntendingUid(
            ClipboardService clipboardService, String str, int i) {
        return UserHandle.getUid(
                clipboardService.getIntendingUserId(i, str),
                UserHandle.getAppId(Binder.getCallingUid()));
    }

    /* renamed from: -$$Nest$mgetSemClipboardService, reason: not valid java name */
    public static SemClipboardService m339$$Nest$mgetSemClipboardService(
            ClipboardService clipboardService) {
        SemClipboardService semClipboardService = clipboardService.mSemClipboardService;
        if (semClipboardService != null) {
            return semClipboardService;
        }
        SemClipboardService asInterface =
                IClipboardService.Stub.asInterface(ServiceManager.getService("semclipboard"));
        clipboardService.mSemClipboardService = asInterface;
        return asInterface;
    }

    /* renamed from: -$$Nest$misDeviceLocked, reason: not valid java name */
    public static boolean m340$$Nest$misDeviceLocked(
            ClipboardService clipboardService, int i, int i2) {
        boolean z;
        clipboardService.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            KeyguardManager keyguardManager =
                    (KeyguardManager)
                            clipboardService.getContext().getSystemService(KeyguardManager.class);
            if (keyguardManager != null) {
                if (keyguardManager.isDeviceLocked(i, i2)) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* renamed from: -$$Nest$mnotifyTextClassifierLocked, reason: not valid java name */
    public static void m341$$Nest$mnotifyTextClassifierLocked(
            ClipboardService clipboardService, final Clipboard clipboard, final String str, int i) {
        final TextClassifier textClassifier;
        clipboardService.getClass();
        ClipData clipData = clipboard.primaryClip;
        if (clipData == null || clipData.getItemAt(0) == null) {
            return;
        }
        ClipData clipData2 = clipboard.primaryClip;
        if (clipData2.getItemCount() > 1) {
            return;
        }
        ClipData.Item itemAt = clipData2.getItemAt(0);
        if (TextUtils.isEmpty(itemAt.getText())
                || itemAt.getUri() != null
                || itemAt.getIntent() != null
                || (textClassifier = clipboard.mTextClassifier) == null
                || !clipboardService.mWm.isUidFocused(i)
                || clipboard.mNotifiedTextClassifierUids.get(i)) {
            return;
        }
        clipboard.mNotifiedTextClassifierUids.put(i, true);
        Binder.withCleanCallingIdentity(
                new FunctionalUtils.ThrowingRunnable() { // from class:
                    // com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda4
                    public final void runOrThrow() {
                        textClassifier.onTextClassifierEvent(
                                ((TextClassifierEvent.TextLinkifyEvent.Builder)
                                                ((TextClassifierEvent.TextLinkifyEvent.Builder)
                                                                new TextClassifierEvent
                                                                                .TextLinkifyEvent
                                                                                .Builder(22)
                                                                        .setEventContext(
                                                                                new TextClassificationContext
                                                                                                .Builder(
                                                                                                str,
                                                                                                "clipboard")
                                                                                        .build()))
                                                        .setExtras(
                                                                Bundle.forPair(
                                                                        "source_package",
                                                                        clipboard
                                                                                .mPrimaryClipPackage)))
                                        .build());
                    }
                });
    }

    /* renamed from: -$$Nest$mshowAccessNotificationLocked, reason: not valid java name */
    public static void m342$$Nest$mshowAccessNotificationLocked(
            final ClipboardService clipboardService,
            final String str,
            int i,
            final int i2,
            Clipboard clipboard) {
        Display display;
        clipboardService.getClass();
        if (clipboard.primaryClip == null
                || Settings.Secure.getInt(
                                clipboardService.getContext().getContentResolver(),
                                "clipboard_show_access_notifications",
                                clipboardService.mShowAccessNotifications ? 1 : 0)
                        == 0
                || UserHandle.isSameApp(i, clipboard.primaryClipUid)
                || clipboardService.isDefaultIme(i2, str)) {
            return;
        }
        ContentCaptureManagerService.LocalService localService =
                clipboardService.mContentCaptureInternal;
        if (localService == null || !localService.isContentCaptureServiceForUser(i, i2)) {
            AutofillManagerInternal autofillManagerInternal = clipboardService.mAutofillInternal;
            if ((autofillManagerInternal == null
                            || !autofillManagerInternal.isAugmentedAutofillServiceForUser(i, i2))
                    && clipboardService.mPm.checkPermission(
                                    "android.permission.SUPPRESS_CLIPBOARD_ACCESS_NOTIFICATION",
                                    str)
                            != 0) {
                VirtualDeviceManagerInternal virtualDeviceManagerInternal =
                        clipboardService.mVdmInternal;
                int i3 = clipboard.deviceId;
                if ((i3 == 0 || virtualDeviceManagerInternal.getDeviceOwnerUid(i3) != i)
                        && !clipboard.mNotifiedUids.get(i)) {
                    final ArraySet arraySet = new ArraySet();
                    if (i3 != 0) {
                        DisplayManager displayManager =
                                (DisplayManager)
                                        clipboardService
                                                .getContext()
                                                .getSystemService(DisplayManager.class);
                        int topFocusedDisplayId = clipboardService.mWm.getTopFocusedDisplayId();
                        ArraySet displayIdsForDevice =
                                virtualDeviceManagerInternal.getDisplayIdsForDevice(i3);
                        if (!displayIdsForDevice.contains(Integer.valueOf(topFocusedDisplayId))
                                || (display = displayManager.getDisplay(topFocusedDisplayId))
                                        == null) {
                            for (int i4 = 0; i4 < displayIdsForDevice.size(); i4++) {
                                Display display2 =
                                        displayManager.getDisplay(
                                                ((Integer) displayIdsForDevice.valueAt(i4))
                                                        .intValue());
                                if (display2 != null) {
                                    arraySet.add(
                                            clipboardService
                                                    .getContext()
                                                    .createDisplayContext(display2));
                                }
                            }
                            if (arraySet.isEmpty()) {
                                NandswapManager$$ExternalSyntheticOutline0.m(
                                        i3,
                                        "getToastContexts Couldn't find any VirtualDisplays for"
                                            + " VirtualDevice ",
                                        "ClipboardService");
                            }
                        } else {
                            arraySet.add(
                                    clipboardService.getContext().createDisplayContext(display));
                        }
                        Binder.withCleanCallingIdentity(
                                new FunctionalUtils
                                        .ThrowingRunnable() { // from class:
                                                              // com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda5
                                    public final void runOrThrow() {
                                        ClipboardService clipboardService2 = ClipboardService.this;
                                        String str2 = str;
                                        int i5 = i2;
                                        ArraySet arraySet2 = arraySet;
                                        clipboardService2.getClass();
                                        try {
                                            PackageManager packageManager = clipboardService2.mPm;
                                            String string =
                                                    clipboardService2
                                                            .getContext()
                                                            .getString(
                                                                    17043264,
                                                                    packageManager
                                                                            .getApplicationLabel(
                                                                                    packageManager
                                                                                            .getApplicationInfoAsUser(
                                                                                                    str2,
                                                                                                    0,
                                                                                                    i5)));
                                            Slog.i("ClipboardService", string);
                                            for (int i6 = 0; i6 < arraySet2.size(); i6++) {
                                                Context context = (Context) arraySet2.valueAt(i6);
                                                (SafetyProtectionUtils
                                                                        .shouldShowSafetyProtectionResources(
                                                                                clipboardService2
                                                                                        .getContext())
                                                                ? Toast.makeCustomToastWithIcon(
                                                                        context,
                                                                        UiThread.get().getLooper(),
                                                                        string,
                                                                        1,
                                                                        clipboardService2
                                                                                .getContext()
                                                                                .getDrawable(
                                                                                        R.drawable
                                                                                                .ic_safety_protection))
                                                                : Toast.makeText(
                                                                        context,
                                                                        UiThread.get().getLooper(),
                                                                        string,
                                                                        1))
                                                        .show();
                                            }
                                        } catch (PackageManager.NameNotFoundException unused) {
                                        }
                                    }
                                });
                        clipboard.mNotifiedUids.put(i, true);
                    }
                    arraySet.add(clipboardService.getContext());
                    Binder.withCleanCallingIdentity(
                            new FunctionalUtils
                                    .ThrowingRunnable() { // from class:
                                                          // com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda5
                                public final void runOrThrow() {
                                    ClipboardService clipboardService2 = ClipboardService.this;
                                    String str2 = str;
                                    int i5 = i2;
                                    ArraySet arraySet2 = arraySet;
                                    clipboardService2.getClass();
                                    try {
                                        PackageManager packageManager = clipboardService2.mPm;
                                        String string =
                                                clipboardService2
                                                        .getContext()
                                                        .getString(
                                                                17043264,
                                                                packageManager.getApplicationLabel(
                                                                        packageManager
                                                                                .getApplicationInfoAsUser(
                                                                                        str2, 0,
                                                                                        i5)));
                                        Slog.i("ClipboardService", string);
                                        for (int i6 = 0; i6 < arraySet2.size(); i6++) {
                                            Context context = (Context) arraySet2.valueAt(i6);
                                            (SafetyProtectionUtils
                                                                    .shouldShowSafetyProtectionResources(
                                                                            clipboardService2
                                                                                    .getContext())
                                                            ? Toast.makeCustomToastWithIcon(
                                                                    context,
                                                                    UiThread.get().getLooper(),
                                                                    string,
                                                                    1,
                                                                    clipboardService2
                                                                            .getContext()
                                                                            .getDrawable(
                                                                                    R.drawable
                                                                                            .ic_safety_protection))
                                                            : Toast.makeText(
                                                                    context,
                                                                    UiThread.get().getLooper(),
                                                                    string,
                                                                    1))
                                                    .show();
                                        }
                                    } catch (PackageManager.NameNotFoundException unused) {
                                    }
                                }
                            });
                    clipboard.mNotifiedUids.put(i, true);
                }
            }
        }
    }

    public ClipboardService(Context context) {
        super(context);
        this.mClipboards = new SparseArrayMap();
        this.mContext = null;
        this.mPersonaManager = null;
        this.mShowAccessNotifications = true;
        this.mAllowVirtualDeviceSilos = true;
        this.mMaxClassificationLength = 400;
        this.mLock = new Object();
        this.mSemClipboardService = null;
        this.mAmInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mUgm = UriGrantsManager.getService();
        UriGrantsManagerInternal uriGrantsManagerInternal =
                (UriGrantsManagerInternal) LocalServices.getService(UriGrantsManagerInternal.class);
        this.mUgmInternal = uriGrantsManagerInternal;
        this.mWm = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        VirtualDeviceManagerInternal virtualDeviceManagerInternal =
                (VirtualDeviceManagerInternal)
                        LocalServices.getService(VirtualDeviceManagerInternal.class);
        this.mVdmInternal = virtualDeviceManagerInternal;
        this.mVdm =
                virtualDeviceManagerInternal != null
                        ? (VirtualDeviceManager)
                                getContext().getSystemService(VirtualDeviceManager.class)
                        : null;
        this.mPm = getContext().getPackageManager();
        this.mUm = ServiceManager.getService("user");
        this.mAppOps = (AppOpsManager) getContext().getSystemService("appops");
        this.mContentCaptureInternal =
                (ContentCaptureManagerService.LocalService)
                        LocalServices.getService(ContentCaptureManagerService.LocalService.class);
        this.mAutofillInternal =
                (AutofillManagerInternal) LocalServices.getService(AutofillManagerInternal.class);
        IBinder newUriPermissionOwner =
                ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal)
                        .newUriPermissionOwner("clipboard");
        this.mContext = context;
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_ALLOWED_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_SHARED_ALLOWED_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_ALLOWED_DENYLIST_APP_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
        context.getContentResolver()
                .registerContentObserver(
                        ClipboardConstants.CLIPBOARD_ALLOWED_ALLOWLIST_APP_URI,
                        true,
                        ClipboardPolicyObserver.getInstance(context),
                        -1);
        this.mPermissionOwner = newUriPermissionOwner;
        if (Build.IS_EMULATOR) {
            Consumer consumer =
                    new Consumer() { // from class:
                                     // com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ClipboardService clipboardService = ClipboardService.this;
                            ClipData clipData = (ClipData) obj;
                            synchronized (clipboardService.mLock) {
                                try {
                                    ClipboardService.Clipboard clipboardLocked =
                                            clipboardService.getClipboardLocked(0, 0);
                                    if (clipboardLocked != null) {
                                        clipboardService.setPrimaryClipInternalLocked(
                                                clipboardLocked, clipData, 1000, (String) null);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    };
            EmulatorClipboardMonitor emulatorClipboardMonitor = new EmulatorClipboardMonitor();
            emulatorClipboardMonitor.mPipe = null;
            new Thread(
                            new EmulatorClipboardMonitor$$ExternalSyntheticLambda0(
                                    0, emulatorClipboardMonitor, consumer))
                    .start();
            this.mClipboardMonitor = emulatorClipboardMonitor;
        } else if (Build.IS_ARC) {
            ArcClipboardMonitor arcClipboardMonitor = new ArcClipboardMonitor();
            LocalServices.addService(ArcClipboardMonitor.class, arcClipboardMonitor);
            this.mClipboardMonitor = arcClipboardMonitor;
        } else {
            this.mClipboardMonitor = new ClipboardService$$ExternalSyntheticLambda1();
        }
        updateConfig();
        DeviceConfig.addOnPropertiesChangedListener(
                "clipboard",
                getContext().getMainExecutor(),
                new DeviceConfig
                        .OnPropertiesChangedListener() { // from class:
                                                         // com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda2
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        ClipboardService.this.updateConfig();
                    }
                });
        HandlerThread handlerThread = new HandlerThread("ClipboardService");
        handlerThread.start();
        this.mWorkerHandler = handlerThread.getThreadHandler();
    }

    public final void applyClassificationAndSendBroadcastLocked(
            Clipboard clipboard,
            ArrayMap arrayMap,
            TextLinks textLinks,
            TextClassifier textClassifier) {
        clipboard.mTextClassifier = textClassifier;
        clipboard.primaryClip.getDescription().setConfidenceScores(arrayMap);
        if (!textLinks.getLinks().isEmpty()) {
            clipboard.primaryClip.getItemAt(0).setTextLinks(textLinks);
        }
        sendClipChangedBroadcast(clipboard);
    }

    public final void checkUriOwner(int i, Uri uri) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            UriGrantsManagerInternal uriGrantsManagerInternal = this.mUgmInternal;
            ((UriGrantsManagerService.LocalService) uriGrantsManagerInternal)
                    .checkGrantUriPermission(
                            i,
                            null,
                            ContentProvider.getUriWithoutUserId(uri),
                            1,
                            ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x006d, code lost:

       if (r6.isUidFocused(r14) != false) goto L36;
    */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean clipboardAccessAllowed(
            int r13,
            int r14,
            int r15,
            int r16,
            java.lang.String r17,
            java.lang.String r18,
            boolean r19) {
        /*
            Method dump skipped, instructions count: 226
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.clipboard.ClipboardService.clipboardAccessAllowed(int,"
                    + " int, int, int, java.lang.String, java.lang.String, boolean):boolean");
    }

    public final boolean deviceUsesDefaultClipboard(int i) {
        VirtualDeviceManager virtualDeviceManager;
        return i == 0
                || (virtualDeviceManager = this.mVdm) == null
                || virtualDeviceManager.getDevicePolicy(i, 4) == 1;
    }

    public final Clipboard getClipboardLocked(int i, int i2) {
        Clipboard clipboard = (Clipboard) this.mClipboards.get(i, Integer.valueOf(i2));
        if (clipboard != null) {
            return clipboard;
        }
        try {
            if (!this.mUm.isUserRunning(i)) {
                Slog.w(
                        "ClipboardService",
                        "getClipboardLocked called with not running userId " + i);
                return null;
            }
            if (i2 != 0 && !this.mVdm.isValidVirtualDeviceId(i2)) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i2,
                        "getClipboardLocked called with invalid (possibly released) deviceId ",
                        "ClipboardService");
                return null;
            }
            Clipboard clipboard2 = new Clipboard(i2);
            this.mClipboards.add(i, Integer.valueOf(i2), clipboard2);
            return clipboard2;
        } catch (RemoteException e) {
            Slog.e("ClipboardService", "RemoteException calling UserManager: " + e);
            return null;
        }
    }

    public final int getIntendingUserId(int i, String str) {
        int userId = UserHandle.getUserId(Binder.getCallingUid());
        return (!UserManager.supportsMultipleUsers() || userId == i)
                ? userId
                : this.mAmInternal.handleIncomingUser(
                        Binder.getCallingPid(),
                        Binder.getCallingUid(),
                        i,
                        false,
                        2,
                        "checkClipboardServiceCallingUser",
                        str);
    }

    public final List getRelatedProfiles(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return this.mUm.getProfiles(i, true);
            } catch (RemoteException e) {
                Slog.e("ClipboardService", "Remote Exception calling UserManager: " + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void grantUriPermission(Uri uri, int i, String str, int i2) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mUgm.grantUriPermissionFromOwner(
                    this.mPermissionOwner,
                    i,
                    str,
                    ContentProvider.getUriWithoutUserId(uri),
                    1,
                    ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)),
                    i2);
        } catch (RemoteException unused) {
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final boolean hasRestriction(int i, String str) {
        try {
            return this.mUm.hasUserRestriction(str, i);
        } catch (RemoteException e) {
            Slog.e(
                    "ClipboardService",
                    "Remote Exception calling UserManager.getUserRestrictions: ",
                    e);
            return true;
        }
    }

    public final boolean isDefaultIme(int i, String str) {
        ComponentName unflattenFromString;
        String stringForUser =
                Settings.Secure.getStringForUser(
                        getContext().getContentResolver(), "default_input_method", i);
        if (TextUtils.isEmpty(stringForUser)
                || (unflattenFromString = ComponentName.unflattenFromString(stringForUser))
                        == null) {
            return false;
        }
        return unflattenFromString.getPackageName().equals(str);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.server.clipboard.ClipboardService$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.clipboard.ClipboardService$2] */
    @Override // com.android.server.SystemService
    public final void onStart() {
        VirtualDeviceManager virtualDeviceManager;
        publishBinderService("clipboard", new ClipboardImpl());
        if (!Flags.vdmPublicApis() && this.mVdmInternal != null) {
            if (this.mVirtualDeviceRemovedReceiver != null) {
                return;
            }
            this.mVirtualDeviceRemovedReceiver =
                    new BroadcastReceiver() { // from class:
                        // com.android.server.clipboard.ClipboardService.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (intent.getAction()
                                    .equals(
                                            "android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED")) {
                                int intExtra =
                                        intent.getIntExtra(
                                                "android.companion.virtual.extra.VIRTUAL_DEVICE_ID",
                                                -1);
                                synchronized (ClipboardService.this.mLock) {
                                    try {
                                        for (int numMaps =
                                                        ClipboardService.this.mClipboards.numMaps()
                                                                - 1;
                                                numMaps >= 0;
                                                numMaps--) {
                                            SparseArrayMap sparseArrayMap =
                                                    ClipboardService.this.mClipboards;
                                            sparseArrayMap.delete(
                                                    sparseArrayMap.keyAt(numMaps),
                                                    Integer.valueOf(intExtra));
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                            }
                        }
                    };
            getContext()
                    .registerReceiver(
                            this.mVirtualDeviceRemovedReceiver,
                            new IntentFilter(
                                    "android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED"),
                            4);
            return;
        }
        if (Flags.vdmPublicApis()
                && (virtualDeviceManager = this.mVdm) != null
                && this.mVirtualDeviceListener == null) {
            this.mVirtualDeviceListener =
                    new VirtualDeviceManager
                            .VirtualDeviceListener() { // from class:
                                                       // com.android.server.clipboard.ClipboardService.2
                        public final void onVirtualDeviceClosed(int i) {
                            synchronized (ClipboardService.this.mLock) {
                                try {
                                    for (int numMaps =
                                                    ClipboardService.this.mClipboards.numMaps() - 1;
                                            numMaps >= 0;
                                            numMaps--) {
                                        SparseArrayMap sparseArrayMap =
                                                ClipboardService.this.mClipboards;
                                        sparseArrayMap.delete(
                                                sparseArrayMap.keyAt(numMaps), Integer.valueOf(i));
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    };
            virtualDeviceManager.registerVirtualDeviceListener(
                    getContext().getMainExecutor(), this.mVirtualDeviceListener);
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopped(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mClipboards.delete(targetUser.getUserIdentifier());
        }
    }

    public final void revokeUriPermission(int i, Uri uri) {
        if (uri == null || !"content".equals(uri.getScheme())) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            ((UriGrantsManagerService.LocalService) this.mUgmInternal)
                    .revokeUriPermissionFromOwner(
                            this.mPermissionOwner,
                            ContentProvider.getUriWithoutUserId(uri),
                            1,
                            ContentProvider.getUserIdFromUri(uri, UserHandle.getUserId(i)));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void sendClipChangedBroadcast(Clipboard clipboard) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        int beginBroadcast = clipboard.primaryClipListeners.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                ListenerInfo listenerInfo =
                        (ListenerInfo) clipboard.primaryClipListeners.getBroadcastCookie(i);
                String str = listenerInfo.mPackageName;
                String str2 = listenerInfo.mAttributionTag;
                int i2 = listenerInfo.mUid;
                if (clipboardAccessAllowed(
                        29, i2, UserHandle.getUserId(i2), clipboard.deviceId, str, str2, true)) {
                    clipboard.primaryClipListeners.getBroadcastItem(i).dispatchPrimaryClipChanged();
                }
            } catch (RemoteException | SecurityException unused) {
            } catch (Throwable th) {
                clipboard.primaryClipListeners.finishBroadcast();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
        clipboard.primaryClipListeners.finishBroadcast();
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public final void setPrimaryClipInternalLocked(ClipData clipData, int i, int i2, String str) {
        int size;
        Clipboard clipboardLocked;
        if (i2 == 0) {
            this.mClipboardMonitor.accept(clipData);
        }
        int userId = UserHandle.getUserId(i);
        Clipboard clipboardLocked2 = getClipboardLocked(userId, i2);
        if (clipboardLocked2 == null) {
            return;
        }
        setPrimaryClipInternalLocked(clipboardLocked2, clipData, i, str);
        List relatedProfiles = getRelatedProfiles(userId);
        if (relatedProfiles == null || (size = relatedProfiles.size()) <= 1) {
            return;
        }
        if (!(!hasRestriction(userId, "no_cross_profile_copy_paste"))) {
            clipData = null;
        } else if (clipData != null) {
            ClipData clipData2 = new ClipData(clipData);
            for (int itemCount = clipData2.getItemCount() - 1; itemCount >= 0; itemCount--) {
                clipData2.setItemAt(itemCount, new ClipData.Item(clipData2.getItemAt(itemCount)));
            }
            if (!"SemImageClipData".equals(clipData2.getDescription().getLabel())) {
                clipData2.fixUrisLight(userId);
            }
            clipData = clipData2;
        }
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = ((UserInfo) relatedProfiles.get(i3)).id;
            if (i4 != userId
                    && (!hasRestriction(i4, "no_sharing_into_profile"))
                    && (clipboardLocked = getClipboardLocked(i4, i2)) != null) {
                setPrimaryClipInternalNoClassifyLocked(clipboardLocked, clipData, i, str);
            }
        }
    }

    public final void setPrimaryClipInternalLocked(
            Clipboard clipboard, final ClipData clipData, int i, String str) {
        final int userId = UserHandle.getUserId(i);
        if (clipData != null) {
            final CharSequence text =
                    clipData.getItemCount() == 0 ? null : clipData.getItemAt(0).getText();
            if (TextUtils.isEmpty(text) || text.length() > this.mMaxClassificationLength) {
                clipData.getDescription().setClassificationStatus(2);
            } else {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    final TextClassifier createTextClassificationSession =
                            ((TextClassificationManager)
                                            getContext()
                                                    .createContextAsUser(UserHandle.of(userId), 0)
                                                    .getSystemService(
                                                            TextClassificationManager.class))
                                    .createTextClassificationSession(
                                            new TextClassificationContext.Builder(
                                                            getContext().getPackageName(),
                                                            "clipboard")
                                                    .build());
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    if (text.length()
                            > createTextClassificationSession.getMaxGenerateLinksTextLength()) {
                        clipData.getDescription().setClassificationStatus(2);
                    } else {
                        final int i2 = clipboard.deviceId;
                        this.mWorkerHandler.post(
                                new Runnable() { // from class:
                                    // com.android.server.clipboard.ClipboardService$$ExternalSyntheticLambda3
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ClipboardService.Clipboard clipboardLocked;
                                        ClipData clipData2;
                                        ClipboardService clipboardService = ClipboardService.this;
                                        CharSequence charSequence = text;
                                        ClipData clipData3 = clipData;
                                        TextClassifier textClassifier =
                                                createTextClassificationSession;
                                        int i3 = userId;
                                        int i4 = i2;
                                        clipboardService.getClass();
                                        TextLinks generateLinks =
                                                textClassifier.generateLinks(
                                                        new TextLinks.Request.Builder(charSequence)
                                                                .build());
                                        ArrayMap arrayMap = new ArrayMap();
                                        Iterator<TextLinks.TextLink> it =
                                                generateLinks.getLinks().iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                break;
                                            }
                                            TextLinks.TextLink next = it.next();
                                            for (int i5 = 0; i5 < next.getEntityCount(); i5++) {
                                                String entity = next.getEntity(i5);
                                                float confidenceScore =
                                                        next.getConfidenceScore(entity);
                                                if (confidenceScore
                                                        > ((Float)
                                                                        arrayMap.getOrDefault(
                                                                                entity,
                                                                                Float.valueOf(
                                                                                        FullScreenMagnificationGestureHandler
                                                                                                .MAX_SCALE)))
                                                                .floatValue()) {
                                                    arrayMap.put(
                                                            entity, Float.valueOf(confidenceScore));
                                                }
                                            }
                                        }
                                        synchronized (clipboardService.mLock) {
                                            try {
                                                ClipboardService.Clipboard clipboardLocked2 =
                                                        clipboardService.getClipboardLocked(i3, i4);
                                                if (clipboardLocked2 == null) {
                                                    return;
                                                }
                                                if (clipboardLocked2.primaryClip == clipData3) {
                                                    clipboardService
                                                            .applyClassificationAndSendBroadcastLocked(
                                                                    clipboardLocked2,
                                                                    arrayMap,
                                                                    generateLinks,
                                                                    textClassifier);
                                                    List relatedProfiles =
                                                            clipboardService.getRelatedProfiles(i3);
                                                    if (relatedProfiles != null) {
                                                        int size = relatedProfiles.size();
                                                        for (int i6 = 0; i6 < size; i6++) {
                                                            int i7 =
                                                                    ((UserInfo)
                                                                                    relatedProfiles
                                                                                            .get(
                                                                                                    i6))
                                                                            .id;
                                                            if (i7 != i3
                                                                    && (!clipboardService
                                                                            .hasRestriction(
                                                                                    i7,
                                                                                    "no_sharing_into_profile"))
                                                                    && (clipboardLocked =
                                                                                    clipboardService
                                                                                            .getClipboardLocked(
                                                                                                    i7,
                                                                                                    i4))
                                                                            != null
                                                                    && (clipData2 =
                                                                                    clipboardLocked
                                                                                            .primaryClip)
                                                                            != null
                                                                    && clipData2.getItemCount() > 0
                                                                    && charSequence.equals(
                                                                            clipboardLocked
                                                                                    .primaryClip
                                                                                    .getItemAt(0)
                                                                                    .getText())) {
                                                                clipboardService
                                                                        .applyClassificationAndSendBroadcastLocked(
                                                                                clipboardLocked,
                                                                                arrayMap,
                                                                                generateLinks,
                                                                                textClassifier);
                                                            }
                                                        }
                                                    }
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                });
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }
        setPrimaryClipInternalNoClassifyLocked(clipboard, clipData, i, str);
    }

    public final void setPrimaryClipInternalNoClassifyLocked(
            Clipboard clipboard, ClipData clipData, int i, String str) {
        ClipDescription description;
        ClipData clipData2 = clipboard.primaryClip;
        if (clipData2 != null) {
            int itemCount = clipData2.getItemCount();
            for (int i2 = 0; i2 < itemCount; i2++) {
                ClipData.Item itemAt = clipboard.primaryClip.getItemAt(i2);
                int i3 = clipboard.primaryClipUid;
                if (itemAt.getUri() != null) {
                    revokeUriPermission(i3, itemAt.getUri());
                }
                Intent intent = itemAt.getIntent();
                if (intent != null && intent.getData() != null) {
                    revokeUriPermission(i3, intent.getData());
                }
            }
        }
        clipboard.activePermissionOwners.clear();
        if (clipData == null && clipboard.primaryClip == null) {
            return;
        }
        clipboard.primaryClip = clipData;
        clipboard.mNotifiedUids.clear();
        clipboard.mNotifiedTextClassifierUids.clear();
        if (clipData != null) {
            clipboard.primaryClipUid = i;
            clipboard.mPrimaryClipPackage = str;
        } else {
            clipboard.primaryClipUid = 9999;
            clipboard.mPrimaryClipPackage = null;
        }
        if (clipData != null && (description = clipData.getDescription()) != null) {
            description.setTimestamp(System.currentTimeMillis());
        }
        sendClipChangedBroadcast(clipboard);
    }

    public final void updateConfig() {
        synchronized (this.mLock) {
            this.mShowAccessNotifications =
                    DeviceConfig.getBoolean("clipboard", "show_access_notifications", true);
            this.mAllowVirtualDeviceSilos =
                    DeviceConfig.getBoolean("clipboard", "allow_virtualdevice_silos", true);
            this.mMaxClassificationLength =
                    DeviceConfig.getInt("clipboard", "max_classification_length", 400);
        }
    }
}
