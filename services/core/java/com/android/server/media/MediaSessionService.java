package com.android.server.media;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ForegroundServiceDelegationOptions;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioSystem;
import android.media.IRemoteSessionCallback;
import android.media.MediaCommunicationManager;
import android.media.MediaMetadata;
import android.media.Session2Token;
import android.media.session.IActiveSessionsListener;
import android.media.session.IOnMediaKeyEventDispatchedListener;
import android.media.session.IOnMediaKeyEventSessionChangedListener;
import android.media.session.IOnMediaKeyListener;
import android.media.session.IOnVolumeKeyLongPressListener;
import android.media.session.ISession;
import android.media.session.ISession2TokensListener;
import android.media.session.ISessionCallback;
import android.media.session.ISessionManager;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.media.flags.Flags;
import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.audio.AudioService;
import com.android.server.utils.EventLogger;

import com.samsung.android.audio.Rune;
import com.samsung.android.knox.custom.CustomDeviceManagerProxy;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.knoxguard.service.utils.IntegritySeUtil;
import com.samsung.android.server.audio.CoverHelper;
import com.samsung.android.server.audio.DesktopModeHelper;
import com.samsung.android.server.audio.SemAudioServiceInternal;
import com.samsung.android.server.media.MediaSessionDataPlatform;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class MediaSessionService extends SystemService implements Watchdog.Monitor {
    public static final int LONG_PRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout() + 50;
    public static final int MULTI_TAP_TIMEOUT = 0;
    public ActivityManagerInternal mActivityManagerInternal;
    public int mAppCastingUid;
    public final AudioManager mAudioManager;
    public AudioPlayerStateMonitor mAudioPlayerStateMonitor;
    public final SemAudioServiceInternal mAudioServiceInternal;
    public final Context mContext;
    public final CoverHelper mCoverHelper;
    public FullUserRecord mCurrentFullUserRecord;
    public MediaSessionPolicyProvider mCustomMediaSessionPolicyProvider;
    public final DesktopModeHelper mDesktopModeHelper;
    public int mDevice;
    public final Set mFgsAllowedMediaSessionRecords;
    public final SparseIntArray mFullUserIds;
    public MediaSessionRecord mGlobalPrioritySession;
    public final MessageHandler mHandler;
    public boolean mHasFeatureLeanback;
    public ComponentName mHighPriorityMediaKeyReceiver;
    public boolean mIsAppCastingOn;
    public boolean mIsMultiSoundOn;
    public KeyguardManager mKeyguardManager;
    public final Object mLock;
    public final PowerManager.WakeLock mMediaEventWakeLock;
    public final Map mMediaNotifications;
    public final MediaSessionDataPlatform mMediaSessionDataPlatform;
    public final NotificationListener mNotificationListener;
    public final AnonymousClass2 mNotificationListenerEnabledChangedReceiver;
    public final NotificationManager mNotificationManager;
    public final PowerManagerInternal mPowerManagerInternal;
    public final HandlerThread mRecordThread;
    public final RemoteCallbackList mRemoteVolumeControllers;
    public final AnonymousClass1 mSession2TokenCallback;
    public final List mSession2TokensListenerRecords;
    public final SessionManagerImpl mSessionManagerImpl;
    public final ArrayList mSessionsListeners;
    public final int mSystemServerPid;
    public UsageStatsManagerInternal mUsageStatsManagerInternal;
    public final Map mUserEngagedSessionsForFgs;
    public final SparseArray mUserEngagedSessionsForUsageLogging;
    public final SparseArray mUserRecords;
    public ComponentName mVolumeKeyLongPressReceiver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FullUserRecord {
        public final ContentResolver mContentResolver;
        public final int mFullUserId;
        public MediaButtonReceiverHolder mLastMediaButtonReceiverHolder;
        public IOnMediaKeyListener mOnMediaKeyListener;
        public int mOnMediaKeyListenerUid;
        public IOnVolumeKeyLongPressListener mOnVolumeKeyLongPressListener;
        public int mOnVolumeKeyLongPressListenerUid;
        public final MediaSessionStack mPriorityStack;
        public final HashMap mOnMediaKeyEventDispatchedListeners = new HashMap();
        public final HashMap mOnMediaKeyEventSessionChangedListeners = new HashMap();
        public final SparseIntArray mUidToSessionCount = new SparseIntArray();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class OnMediaKeyEventDispatchedListenerRecord
                implements IBinder.DeathRecipient {
            public final IOnMediaKeyEventDispatchedListener callback;
            public final int uid;

            public OnMediaKeyEventDispatchedListenerRecord(
                    IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener, int i) {
                this.callback = iOnMediaKeyEventDispatchedListener;
                this.uid = i;
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord.this.mOnMediaKeyEventDispatchedListeners.remove(
                            this.callback.asBinder());
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class OnMediaKeyEventSessionChangedListenerRecord
                implements IBinder.DeathRecipient {
            public final IOnMediaKeyEventSessionChangedListener callback;
            public final int uid;

            public OnMediaKeyEventSessionChangedListenerRecord(
                    IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener,
                    int i) {
                this.callback = iOnMediaKeyEventSessionChangedListener;
                this.uid = i;
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord.this.mOnMediaKeyEventSessionChangedListeners.remove(
                            this.callback.asBinder());
                }
            }
        }

        /* JADX WARN: Can't wrap try/catch for region: R(6:15|(2:16|17)|(2:19|20)|21|22|23) */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0088, code lost:

           if (r8.getServiceInfo(r0, 786436) != null) goto L27;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public FullUserRecord(int r9) {
            /*
                r7 = this;
                r7.<init>()
                com.android.server.media.MediaSessionService.this = r8
                java.util.HashMap r0 = new java.util.HashMap
                r0.<init>()
                r7.mOnMediaKeyEventDispatchedListeners = r0
                java.util.HashMap r0 = new java.util.HashMap
                r0.<init>()
                r7.mOnMediaKeyEventSessionChangedListeners = r0
                android.util.SparseIntArray r0 = new android.util.SparseIntArray
                r0.<init>()
                r7.mUidToSessionCount = r0
                r7.mFullUserId = r9
                android.content.Context r0 = r8.mContext
                android.os.UserHandle r9 = android.os.UserHandle.of(r9)
                r1 = 0
                android.content.Context r9 = r0.createContextAsUser(r9, r1)
                android.content.ContentResolver r9 = r9.getContentResolver()
                r7.mContentResolver = r9
                com.android.server.media.MediaSessionStack r0 = new com.android.server.media.MediaSessionStack
                com.android.server.media.AudioPlayerStateMonitor r2 = r8.mAudioPlayerStateMonitor
                r0.<init>(r2, r7)
                r7.mPriorityStack = r0
                java.lang.String r0 = "media_button_receiver"
                java.lang.String r9 = android.provider.Settings.Secure.getString(r9, r0)
                android.content.Context r8 = r8.mContext
                boolean r0 = android.text.TextUtils.isEmpty(r9)
                r2 = 0
                if (r0 == 0) goto L47
                goto L93
            L47:
                java.lang.String r0 = ","
                java.lang.String[] r9 = r9.split(r0)
                if (r9 == 0) goto L93
                int r0 = r9.length
                r3 = 3
                r4 = 2
                if (r0 == r4) goto L58
                int r0 = r9.length
                if (r0 == r3) goto L58
                goto L93
            L58:
                r0 = r9[r1]
                android.content.ComponentName r0 = android.content.ComponentName.unflattenFromString(r0)
                if (r0 != 0) goto L61
                goto L93
            L61:
                r1 = 1
                r5 = r9[r1]
                int r5 = java.lang.Integer.parseInt(r5)
                int r6 = r9.length
                if (r6 != r3) goto L72
                r8 = r9[r4]
                int r8 = java.lang.Integer.parseInt(r8)
                goto L8d
            L72:
                android.content.pm.PackageManager r8 = r8.getPackageManager()
                r9 = 786433(0xc0001, float:1.102027E-39)
                android.content.pm.ActivityInfo r9 = r8.getActivityInfo(r0, r9)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L81
                if (r9 == 0) goto L81
                r3 = r4
                goto L8c
            L81:
                r9 = 786436(0xc0004, float:1.102032E-39)
                android.content.pm.ServiceInfo r8 = r8.getServiceInfo(r0, r9)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L8b
                if (r8 == 0) goto L8b
                goto L8c
            L8b:
                r3 = r1
            L8c:
                r8 = r3
            L8d:
                com.android.server.media.MediaButtonReceiverHolder r9 = new com.android.server.media.MediaButtonReceiverHolder
                r9.<init>(r5, r2, r0, r8)
                r2 = r9
            L93:
                r7.mLastMediaButtonReceiverHolder = r2
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.MediaSessionService.FullUserRecord.<init>(com.android.server.media.MediaSessionService,"
                        + " int):void");
        }

        public final void dumpLocked(PrintWriter printWriter) {
            StringBuilder sb = new StringBuilder("Record for full_user=");
            int i = this.mFullUserId;
            sb.append(i);
            printWriter.print(sb.toString());
            MediaSessionService mediaSessionService = MediaSessionService.this;
            int size = mediaSessionService.mFullUserIds.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (mediaSessionService.mFullUserIds.keyAt(i2)
                                != mediaSessionService.mFullUserIds.valueAt(i2)
                        && mediaSessionService.mFullUserIds.valueAt(i2) == i) {
                    printWriter.print(
                            ", profile_user=" + mediaSessionService.mFullUserIds.keyAt(i2));
                }
            }
            printWriter.println();
            printWriter.println(
                    "  Volume key long-press listener: " + this.mOnVolumeKeyLongPressListener);
            printWriter.println(
                    "  Volume key long-press listener package: "
                            + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                    mediaSessionService, this.mOnVolumeKeyLongPressListenerUid));
            printWriter.println("  Media key listener: " + this.mOnMediaKeyListener);
            printWriter.println(
                    "  Media key listener package: "
                            + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                    mediaSessionService, this.mOnMediaKeyListenerUid));
            printWriter.println(
                    "  OnMediaKeyEventDispatchedListener: added "
                            + this.mOnMediaKeyEventDispatchedListeners.size()
                            + " listener(s)");
            Iterator it = this.mOnMediaKeyEventDispatchedListeners.values().iterator();
            while (it.hasNext()) {
                printWriter.println(
                        "    from "
                                + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                        mediaSessionService,
                                        ((OnMediaKeyEventDispatchedListenerRecord) it.next()).uid));
            }
            printWriter.println(
                    "  OnMediaKeyEventSessionChangedListener: added "
                            + this.mOnMediaKeyEventSessionChangedListeners.size()
                            + " listener(s)");
            Iterator it2 = this.mOnMediaKeyEventSessionChangedListeners.values().iterator();
            while (it2.hasNext()) {
                printWriter.println(
                        "    from "
                                + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                        mediaSessionService,
                                        ((OnMediaKeyEventSessionChangedListenerRecord) it2.next())
                                                .uid));
            }
            printWriter.println(
                    "  Last MediaButtonReceiver: " + this.mLastMediaButtonReceiverHolder);
            printWriter.println(
                    "  High priority mediakey receiver: "
                            + mediaSessionService.mHighPriorityMediaKeyReceiver);
            printWriter.println(
                    "  Volume key long-press receiver: "
                            + mediaSessionService.mVolumeKeyLongPressReceiver);
            MediaSessionStack mediaSessionStack = this.mPriorityStack;
            mediaSessionStack.getClass();
            printWriter.println(
                    "  Media button session is " + mediaSessionStack.mMediaButtonSession);
            printWriter.println(
                    "  Sessions Stack - have "
                            + ((ArrayList) mediaSessionStack.mSessions).size()
                            + " sessions:");
            Iterator it3 = ((ArrayList) mediaSessionStack.mSessions).iterator();
            while (it3.hasNext()) {
                ((MediaSessionRecordImpl) it3.next()).dump(printWriter, "    ");
            }
        }

        public final MediaSessionRecordImpl getMediaButtonSessionLocked() {
            MediaSessionService mediaSessionService = MediaSessionService.this;
            return mediaSessionService.isGlobalPriorityActiveLocked()
                    ? mediaSessionService.mGlobalPrioritySession
                    : this.mPriorityStack.mMediaButtonSession;
        }

        public final void pushAddressedPlayerChangedLocked() {
            Iterator it = this.mOnMediaKeyEventSessionChangedListeners.values().iterator();
            while (it.hasNext()) {
                pushAddressedPlayerChangedLocked(
                        ((OnMediaKeyEventSessionChangedListenerRecord) it.next()).callback);
            }
        }

        public final void pushAddressedPlayerChangedLocked(
                IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) {
            try {
                MediaSessionRecordImpl mediaButtonSessionLocked = getMediaButtonSessionLocked();
                MediaSessionService mediaSessionService = MediaSessionService.this;
                if (mediaButtonSessionLocked == null) {
                    MediaButtonReceiverHolder mediaButtonReceiverHolder =
                            mediaSessionService
                                    .mCurrentFullUserRecord
                                    .mLastMediaButtonReceiverHolder;
                    if (mediaButtonReceiverHolder != null) {
                        iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(
                                mediaButtonReceiverHolder.mPackageName, (MediaSession.Token) null);
                    } else {
                        iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(
                                "", (MediaSession.Token) null);
                    }
                } else if (mediaButtonSessionLocked instanceof MediaSessionRecord) {
                    MediaSessionRecord mediaSessionRecord =
                            (MediaSessionRecord) mediaButtonSessionLocked;
                    mediaSessionService.setMultiSoundFlag(mediaSessionRecord);
                    iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(
                            mediaSessionRecord.mPackageName, mediaSessionRecord.mSessionToken);
                }
            } catch (RemoteException e) {
                Log.w("MediaSessionService", "Failed to pushAddressedPlayerChangedLocked", e);
            }
        }

        public final void rememberMediaButtonReceiverLocked(
                MediaSessionRecordImpl mediaSessionRecordImpl) {
            MediaButtonReceiverHolder mediaButtonReceiverHolder;
            ComponentName componentName;
            if ((mediaSessionRecordImpl instanceof MediaSession2Record)
                    || (mediaButtonReceiverHolder =
                                    ((MediaSessionRecord) mediaSessionRecordImpl)
                                            .mMediaButtonReceiverHolder)
                            == null) {
                return;
            }
            this.mLastMediaButtonReceiverHolder = mediaButtonReceiverHolder;
            String str = "";
            if (mediaButtonReceiverHolder != null
                    && (componentName = mediaButtonReceiverHolder.mComponentName) != null) {
                str =
                        String.join(
                                ",",
                                componentName.flattenToString(),
                                String.valueOf(mediaButtonReceiverHolder.mUserId),
                                String.valueOf(mediaButtonReceiverHolder.mComponentType));
            }
            Settings.Secure.putString(this.mContentResolver, "media_button_receiver", str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MediaSessionServiceInternal {
        public MediaSessionServiceInternal() {}

        public final boolean adjustMirroringVolume(int i, int i2, int i3, int i4, String str) {
            synchronized (MediaSessionService.this.mLock) {
                try {
                    MediaSessionService mediaSessionService = MediaSessionService.this;
                    MediaSessionRecord defaultVolumeSession =
                            mediaSessionService.isGlobalPriorityActiveLocked()
                                    ? mediaSessionService.mGlobalPrioritySession
                                    : mediaSessionService.mCurrentFullUserRecord.mPriorityStack
                                            .getDefaultVolumeSession();
                    if (defaultVolumeSession == null
                            || !"com.samsung.android.audiomirroring"
                                    .equals(defaultVolumeSession.mPackageName)
                            || !MediaSessionService.m668$$Nest$misMirroringPackage(
                                    MediaSessionService.this, str)) {
                        return false;
                    }
                    defaultVolumeSession.adjustVolume(str, str, i4, i3, false, i, i2, true);
                    return true;
                } finally {
                }
            }
        }

        public final boolean isAudioMirroring() {
            boolean z;
            synchronized (MediaSessionService.this.mLock) {
                try {
                    MediaSessionService mediaSessionService = MediaSessionService.this;
                    MediaSessionRecord defaultVolumeSession =
                            mediaSessionService.isGlobalPriorityActiveLocked()
                                    ? mediaSessionService.mGlobalPrioritySession
                                    : mediaSessionService.mCurrentFullUserRecord.mPriorityStack
                                            .getDefaultVolumeSession();
                    z =
                            defaultVolumeSession != null
                                    && "com.samsung.android.audiomirroring"
                                            .equals(defaultVolumeSession.mPackageName);
                } finally {
                }
            }
            return z;
        }

        public final void setMediaKeyEventReceiver(ComponentName componentName) {
            synchronized (MediaSessionService.this.mLock) {
                MediaSessionService.this.mHighPriorityMediaKeyReceiver = componentName;
            }
        }

        public final void setVolumeLongPressReceiver(ComponentName componentName) {
            synchronized (MediaSessionService.this.mLock) {
                MediaSessionService.this.mVolumeKeyLongPressReceiver = componentName;
            }
        }

        public final void updateMultiSoundInfo(int i, boolean z) {
            Log.d(
                    "MediaSessionService",
                    "updateMultiSoundInfo device:" + Integer.toHexString(i) + " on:" + z);
            MediaSessionService mediaSessionService = MediaSessionService.this;
            if (i != -1) {
                mediaSessionService.mDevice = i;
            }
            mediaSessionService.mIsMultiSoundOn = z;
            mediaSessionService.mCurrentFullUserRecord.mPriorityStack.mIsMultiSoundOn = z;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MessageHandler extends Handler {
        public final SparseArray mIntegerCache = new SparseArray();

        public MessageHandler() {}

        /* JADX WARN: Code restructure failed: missing block: B:75:0x0053, code lost:

           if (r3 != 0) goto L18;
        */
        /* JADX WARN: Code restructure failed: missing block: B:91:0x007d, code lost:

           if (r5.isActive() != false) goto L18;
        */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void handleMessage(android.os.Message r11) {
            /*
                Method dump skipped, instructions count: 954
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.MediaSessionService.MessageHandler.handleMessage(android.os.Message):void");
        }

        public final void postSessionsChanged(MediaSessionRecordImpl mediaSessionRecordImpl) {
            Integer num = (Integer) this.mIntegerCache.get(mediaSessionRecordImpl.getUserId());
            if (num == null) {
                num = Integer.valueOf(mediaSessionRecordImpl.getUserId());
                this.mIntegerCache.put(mediaSessionRecordImpl.getUserId(), num);
            }
            int i = mediaSessionRecordImpl instanceof MediaSessionRecord ? 1 : 2;
            removeMessages(i, num);
            obtainMessage(i, num).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationListener extends NotificationListenerService {
        public NotificationListener() {}

        public final MediaSessionRecordImpl getLinkedMediaSessionRecord(
                int i, Notification notification) {
            synchronized (MediaSessionService.this.mLock) {
                try {
                    for (MediaSessionRecordImpl mediaSessionRecordImpl :
                            (Set)
                                    ((HashMap) MediaSessionService.this.mUserEngagedSessionsForFgs)
                                            .getOrDefault(Integer.valueOf(i), Set.of())) {
                        if (mediaSessionRecordImpl.isLinkedToNotification(notification)) {
                            return mediaSessionRecordImpl;
                        }
                    }
                    return null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationPosted(StatusBarNotification statusBarNotification) {
            super.onNotificationPosted(statusBarNotification);
            Notification notification = statusBarNotification.getNotification();
            int uid = statusBarNotification.getUid();
            if (notification.isMediaNotification()) {
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        ((HashMap) MediaSessionService.this.mMediaNotifications)
                                .putIfAbsent(Integer.valueOf(uid), new HashSet());
                        ((Set)
                                        ((HashMap) MediaSessionService.this.mMediaNotifications)
                                                .get(Integer.valueOf(uid)))
                                .add(notification);
                        for (MediaSessionRecordImpl mediaSessionRecordImpl :
                                (Set)
                                        ((HashMap)
                                                        MediaSessionService.this
                                                                .mUserEngagedSessionsForFgs)
                                                .getOrDefault(Integer.valueOf(uid), Set.of())) {
                            if (mediaSessionRecordImpl.isLinkedToNotification(notification)) {
                                MediaSessionService.this.startFgsDelegateLocked(
                                        mediaSessionRecordImpl);
                                return;
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        @Override // android.service.notification.NotificationListenerService
        public final void onNotificationRemoved(StatusBarNotification statusBarNotification) {
            super.onNotificationRemoved(statusBarNotification);
            Notification notification = statusBarNotification.getNotification();
            int uid = statusBarNotification.getUid();
            if (notification.isMediaNotification()) {
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        Set set =
                                (Set)
                                        ((HashMap) MediaSessionService.this.mMediaNotifications)
                                                .get(Integer.valueOf(uid));
                        if (set != null) {
                            set.remove(notification);
                            if (set.isEmpty()) {
                                ((HashMap) MediaSessionService.this.mMediaNotifications)
                                        .remove(Integer.valueOf(uid));
                            }
                        }
                        MediaSessionRecordImpl linkedMediaSessionRecord =
                                getLinkedMediaSessionRecord(uid, notification);
                        if (linkedMediaSessionRecord == null) {
                            return;
                        }
                        MediaSessionService.this.stopFgsIfNoSessionIsLinkedToNotification(
                                linkedMediaSessionRecord);
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Session2TokensListenerRecord implements IBinder.DeathRecipient {
        public final ISession2TokensListener listener;
        public final int userId;

        public Session2TokensListenerRecord(
                ISession2TokensListener iSession2TokensListener, int i) {
            this.listener = iSession2TokensListener;
            this.userId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (MediaSessionService.this.mLock) {
                ((ArrayList) MediaSessionService.this.mSession2TokensListenerRecords).remove(this);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionManagerImpl extends ISessionManager.Stub {
        public final KeyEventWakeLockReceiver mKeyEventReceiver;
        public boolean mSkippedFirstKeyFromKeyCustomizer;
        public VolumeKeyLongPressControlThread mVolumeKeyLongPressControlThread;
        public final KeyEventHandler mMediaKeyEventHandler = new KeyEventHandler(0);
        public final KeyEventHandler mVolumeKeyEventHandler = new KeyEventHandler(1);
        public final EventLogger mEventLogger = new EventLogger(80, "MediaSession events");
        public final MediaSessionService$SessionManagerImpl$$ExternalSyntheticLambda0
                mKeyCustomizerRunnable =
                        new MediaSessionService$SessionManagerImpl$$ExternalSyntheticLambda0(
                                0, this);

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.media.MediaSessionService$SessionManagerImpl$1, reason: invalid class name */
        public final class AnonymousClass1 implements IBinder.DeathRecipient {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ SessionManagerImpl this$1;
            public final /* synthetic */ FullUserRecord val$user;

            public /* synthetic */ AnonymousClass1(
                    SessionManagerImpl sessionManagerImpl, FullUserRecord fullUserRecord, int i) {
                this.$r8$classId = i;
                this.this$1 = sessionManagerImpl;
                this.val$user = fullUserRecord;
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                switch (this.$r8$classId) {
                    case 0:
                        synchronized (MediaSessionService.this.mLock) {
                            this.val$user.mOnVolumeKeyLongPressListener = null;
                        }
                        return;
                    default:
                        synchronized (MediaSessionService.this.mLock) {
                            this.val$user.mOnMediaKeyListener = null;
                        }
                        return;
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class KeyEventHandler {
            public boolean mIsLongPressing;
            public final int mKeyType;
            public final MediaSessionService$SessionManagerImpl$$ExternalSyntheticLambda0
                    mLongPressRunnableInBlackScreen =
                            new MediaSessionService$SessionManagerImpl$$ExternalSyntheticLambda0(
                                    1, this);
            public final int mMultiTapKeyCode;
            public final Runnable mMultiTapTimeoutRunnable;
            public KeyEvent mTrackingFirstDownKeyEvent;

            public KeyEventHandler(int i) {
                this.mKeyType = i;
            }

            public static boolean isFirstLongPressKeyEvent(KeyEvent keyEvent) {
                return (keyEvent.getFlags() & 128) != 0 && keyEvent.getRepeatCount() == 1;
            }

            /* JADX WARN: Code restructure failed: missing block: B:113:0x0128, code lost:

               if (com.android.server.media.MediaSessionService.SessionManagerImpl.m670$$Nest$misVoiceKey(r15, r28.getKeyCode()) == false) goto L147;
            */
            /* JADX WARN: Code restructure failed: missing block: B:18:0x011c, code lost:

               if (com.android.server.media.MediaSessionService.m669$$Nest$mneedVolumeKeyLongPressBroadCastLocked(r1) == false) goto L147;
            */
            /* JADX WARN: Removed duplicated region for block: B:112:0x0120  */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0110  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void handleKeyEventLocked(
                    java.lang.String r24,
                    int r25,
                    int r26,
                    boolean r27,
                    android.view.KeyEvent r28,
                    boolean r29,
                    java.lang.String r30,
                    int r31,
                    boolean r32) {
                /*
                    Method dump skipped, instructions count: 733
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.handleKeyEventLocked(java.lang.String,"
                            + " int, int, boolean, android.view.KeyEvent, boolean,"
                            + " java.lang.String, int, boolean):void");
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class KeyEventWakeLockReceiver extends ResultReceiver
                implements Runnable, PendingIntent.OnFinished {
            public final Handler mHandler;
            public int mLastTimeoutId;
            public int mRefCount;

            public KeyEventWakeLockReceiver(Handler handler) {
                super(handler);
                this.mRefCount = 0;
                this.mLastTimeoutId = 0;
                this.mHandler = handler;
            }

            public final void acquireWakeLockLocked() {
                if (this.mRefCount == 0) {
                    MediaSessionService.this.mMediaEventWakeLock.acquire();
                }
                this.mRefCount++;
                this.mHandler.removeCallbacks(this);
                this.mHandler.postDelayed(this, 5000L);
            }

            @Override // android.os.ResultReceiver
            public final void onReceiveResult(int i, Bundle bundle) {
                if (i < this.mLastTimeoutId) {
                    return;
                }
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        int i2 = this.mRefCount;
                        if (i2 > 0) {
                            int i3 = i2 - 1;
                            this.mRefCount = i3;
                            if (i3 == 0) {
                                MediaSessionService.this.mMediaEventWakeLock.release();
                                this.mHandler.removeCallbacks(this);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.app.PendingIntent.OnFinished
            public final void onSendFinished(
                    PendingIntent pendingIntent, Intent intent, int i, String str, Bundle bundle) {
                onReceiveResult(i, null);
            }

            @Override // java.lang.Runnable
            public final void run() {
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        if (this.mRefCount == 0) {
                            return;
                        }
                        this.mLastTimeoutId++;
                        this.mRefCount = 0;
                        MediaSessionService.this.mMediaEventWakeLock.release();
                        this.mHandler.removeCallbacks(this);
                    } finally {
                    }
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class MediaKeyListenerResultReceiver extends ResultReceiver
                implements Runnable {
            public final boolean mAsSystemService;
            public boolean mHandled;
            public final KeyEvent mKeyEvent;
            public final boolean mNeedWakeLock;
            public final String mPackageName;
            public final int mPid;
            public final int mUid;

            public MediaKeyListenerResultReceiver(
                    String str, int i, int i2, boolean z, KeyEvent keyEvent, boolean z2) {
                super(MediaSessionService.this.mHandler);
                MediaSessionService.this.mHandler.postDelayed(this, 1000L);
                this.mPackageName = str;
                this.mPid = i;
                this.mUid = i2;
                this.mAsSystemService = z;
                this.mKeyEvent = keyEvent;
                this.mNeedWakeLock = z2;
            }

            public final void dispatchMediaKeyEvent() {
                if (this.mHandled) {
                    return;
                }
                this.mHandled = true;
                MediaSessionService.this.mHandler.removeCallbacks(this);
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        if (MediaSessionService.this.isGlobalPriorityActiveLocked()) {
                            SessionManagerImpl.this.dispatchMediaKeyEventLocked(
                                    this.mPackageName,
                                    this.mPid,
                                    this.mUid,
                                    this.mAsSystemService,
                                    this.mKeyEvent,
                                    this.mNeedWakeLock);
                        } else {
                            SessionManagerImpl.this.mMediaKeyEventHandler.handleKeyEventLocked(
                                    this.mPackageName,
                                    this.mPid,
                                    this.mUid,
                                    this.mAsSystemService,
                                    this.mKeyEvent,
                                    this.mNeedWakeLock,
                                    null,
                                    0,
                                    false);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // android.os.ResultReceiver
            public final void onReceiveResult(int i, Bundle bundle) {
                if (i != 1) {
                    dispatchMediaKeyEvent();
                } else {
                    this.mHandled = true;
                    MediaSessionService.this.mHandler.removeCallbacks(this);
                }
            }

            @Override // java.lang.Runnable
            public final void run() {
                Log.d(
                        "MediaSessionService",
                        "The media key listener is timed-out for " + this.mKeyEvent);
                dispatchMediaKeyEvent();
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class VolumeKeyLongPressControlThread extends Thread {
            public int mDirection;
            public int mFlags;
            public boolean mNeedToRun;
            public final String mOpPackageName;
            public final String mPackageName;
            public final PowerManager mPm;
            public int mSleepDuration;
            public int mStream;

            public VolumeKeyLongPressControlThread(String str, String str2) {
                this.mPm =
                        (PowerManager)
                                MediaSessionService.this.getContext().getSystemService("power");
                this.mPackageName = str;
                this.mOpPackageName = str2;
            }

            /* JADX WARN: Code restructure failed: missing block: B:28:0x008c, code lost:

               return;
            */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    r13 = this;
                    r0 = 0
                    r1 = r0
                L2:
                    int r2 = r13.mSleepDuration     // Catch: java.lang.Exception -> L8
                    long r2 = (long) r2     // Catch: java.lang.Exception -> L8
                    java.lang.Thread.sleep(r2)     // Catch: java.lang.Exception -> L8
                L8:
                    com.android.server.media.MediaSessionService$SessionManagerImpl r2 = com.android.server.media.MediaSessionService.SessionManagerImpl.this
                    com.android.server.media.MediaSessionService r2 = com.android.server.media.MediaSessionService.this
                    java.lang.Object r2 = r2.mLock
                    monitor-enter(r2)
                    android.os.PowerManager r3 = r13.mPm     // Catch: java.lang.Throwable -> L3d
                    boolean r3 = r3.isInteractive()     // Catch: java.lang.Throwable -> L3d
                    r4 = 1
                    if (r3 != 0) goto L1a
                    r3 = r0
                    goto L31
                L1a:
                    com.android.server.media.MediaSessionService$SessionManagerImpl r3 = com.android.server.media.MediaSessionService.SessionManagerImpl.this     // Catch: java.lang.Throwable -> L3d
                    com.android.server.media.MediaSessionService r3 = com.android.server.media.MediaSessionService.this     // Catch: java.lang.Throwable -> L3d
                    com.samsung.android.server.audio.DesktopModeHelper r5 = r3.mDesktopModeHelper     // Catch: java.lang.Throwable -> L3d
                    if (r5 == 0) goto L26
                    boolean r5 = r5.mIsDesktopMode     // Catch: java.lang.Throwable -> L3d
                    if (r5 == 0) goto L2a
                L26:
                    android.os.PowerManagerInternal r3 = r3.mPowerManagerInternal     // Catch: java.lang.Throwable -> L3d
                    if (r3 != 0) goto L2c
                L2a:
                    r3 = r4
                    goto L31
                L2c:
                    boolean r3 = r3.isInternalDisplayOff()     // Catch: java.lang.Throwable -> L3d
                    r3 = r3 ^ r4
                L31:
                    if (r3 == 0) goto L3f
                    java.lang.String r13 = "MediaSessionService"
                    java.lang.String r0 = "screen is on"
                    android.util.Log.d(r13, r0)     // Catch: java.lang.Throwable -> L3d
                    monitor-exit(r2)     // Catch: java.lang.Throwable -> L3d
                    goto L8c
                L3d:
                    r13 = move-exception
                    goto L8d
                L3f:
                    boolean r3 = r13.mNeedToRun     // Catch: java.lang.Throwable -> L3d
                    if (r3 == 0) goto L8b
                    r3 = 200(0xc8, float:2.8E-43)
                    if (r1 < r3) goto L48
                    goto L8b
                L48:
                    int r1 = r1 + 1
                    java.lang.String r3 = "MediaSessionService"
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3d
                    r5.<init>()     // Catch: java.lang.Throwable -> L3d
                    java.lang.String r6 = "volumekey long press repeat:"
                    r5.append(r6)     // Catch: java.lang.Throwable -> L3d
                    r5.append(r1)     // Catch: java.lang.Throwable -> L3d
                    java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L3d
                    android.util.Log.d(r3, r5)     // Catch: java.lang.Throwable -> L3d
                    int r3 = r13.mSleepDuration     // Catch: java.lang.Throwable -> L3d
                    r5 = 500(0x1f4, float:7.0E-43)
                    if (r3 != r5) goto L6b
                    r3 = 50
                    r13.mSleepDuration = r3     // Catch: java.lang.Throwable -> L3d
                L6b:
                    int r11 = r13.mFlags     // Catch: java.lang.Throwable -> L3d
                    r3 = r11 & 512(0x200, float:7.175E-43)
                    if (r3 == 0) goto L73
                    r12 = r4
                    goto L74
                L73:
                    r12 = r0
                L74:
                    com.android.server.media.MediaSessionService$SessionManagerImpl r3 = com.android.server.media.MediaSessionService.SessionManagerImpl.this     // Catch: java.lang.Throwable -> L3d
                    java.lang.String r4 = r13.mPackageName     // Catch: java.lang.Throwable -> L3d
                    java.lang.String r5 = r13.mOpPackageName     // Catch: java.lang.Throwable -> L3d
                    com.android.server.media.MediaSessionService r6 = com.android.server.media.MediaSessionService.this     // Catch: java.lang.Throwable -> L3d
                    int r6 = r6.mSystemServerPid     // Catch: java.lang.Throwable -> L3d
                    int r9 = r13.mStream     // Catch: java.lang.Throwable -> L3d
                    int r10 = r13.mDirection     // Catch: java.lang.Throwable -> L3d
                    r7 = 1000(0x3e8, float:1.401E-42)
                    r8 = 1
                    r3.dispatchAdjustVolumeLocked(r4, r5, r6, r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> L3d
                    monitor-exit(r2)     // Catch: java.lang.Throwable -> L3d
                    goto L2
                L8b:
                    monitor-exit(r2)     // Catch: java.lang.Throwable -> L3d
                L8c:
                    return
                L8d:
                    monitor-exit(r2)     // Catch: java.lang.Throwable -> L3d
                    throw r13
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.media.MediaSessionService.SessionManagerImpl.VolumeKeyLongPressControlThread.run():void");
            }
        }

        /* renamed from: -$$Nest$misVoiceKey, reason: not valid java name */
        public static boolean m670$$Nest$misVoiceKey(SessionManagerImpl sessionManagerImpl, int i) {
            if (i == 79) {
                sessionManagerImpl.getClass();
            } else if (MediaSessionService.this.mHasFeatureLeanback || i != 85) {
                return false;
            }
            return true;
        }

        public SessionManagerImpl() {
            this.mKeyEventReceiver =
                    new KeyEventWakeLockReceiver(MediaSessionService.this.mHandler);
        }

        public final void addOnMediaKeyEventDispatchedListener(
                IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) {
            if (iOnMediaKeyEventDispatchedListener == null) {
                Log.w(
                        "MediaSessionService",
                        "addOnMediaKeyEventDispatchedListener: listener is null, ignoring");
                return;
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!MediaSessionService.this.hasMediaControlPermission(callingPid, callingUid)) {
                    throw new SecurityException(
                            "MEDIA_CONTENT_CONTROL permission is required to  add"
                                + " MediaKeyEventDispatchedListener");
                }
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked != null
                            && fullUserRecordLocked.mFullUserId == identifier) {
                        IBinder asBinder = iOnMediaKeyEventDispatchedListener.asBinder();
                        FullUserRecord.OnMediaKeyEventDispatchedListenerRecord
                                onMediaKeyEventDispatchedListenerRecord =
                                        fullUserRecordLocked
                                        .new OnMediaKeyEventDispatchedListenerRecord(
                                                iOnMediaKeyEventDispatchedListener, callingUid);
                        fullUserRecordLocked.mOnMediaKeyEventDispatchedListeners.put(
                                asBinder, onMediaKeyEventDispatchedListenerRecord);
                        try {
                            asBinder.linkToDeath(onMediaKeyEventDispatchedListenerRecord, 0);
                        } catch (RemoteException e) {
                            Log.w("MediaSessionService", "Failed to add listener", e);
                            fullUserRecordLocked.mOnMediaKeyEventDispatchedListeners.remove(
                                    asBinder);
                        }
                        Log.d(
                                "MediaSessionService",
                                "The MediaKeyEventDispatchedListener ("
                                        + iOnMediaKeyEventDispatchedListener.asBinder()
                                        + ") is added by "
                                        + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                                MediaSessionService.this, callingUid));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    Log.w(
                            "MediaSessionService",
                            "Only the full user can add the listener, userId=" + identifier);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void addOnMediaKeyEventSessionChangedListener(
                IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener,
                String str) {
            if (iOnMediaKeyEventSessionChangedListener == null) {
                Log.w(
                        "MediaSessionService",
                        "addOnMediaKeyEventSessionChangedListener: listener is null, ignoring");
                return;
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaServerUtils.enforcePackageName(
                        MediaSessionService.this.mContext, str, callingUid);
                MediaSessionService.this.enforceMediaPermissions(
                        callingPid, callingUid, identifier, str);
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked != null
                            && fullUserRecordLocked.mFullUserId == identifier) {
                        IBinder asBinder = iOnMediaKeyEventSessionChangedListener.asBinder();
                        FullUserRecord.OnMediaKeyEventSessionChangedListenerRecord
                                onMediaKeyEventSessionChangedListenerRecord =
                                        fullUserRecordLocked
                                        .new OnMediaKeyEventSessionChangedListenerRecord(
                                                iOnMediaKeyEventSessionChangedListener, callingUid);
                        fullUserRecordLocked.mOnMediaKeyEventSessionChangedListeners.put(
                                asBinder, onMediaKeyEventSessionChangedListenerRecord);
                        try {
                            asBinder.linkToDeath(onMediaKeyEventSessionChangedListenerRecord, 0);
                            fullUserRecordLocked.pushAddressedPlayerChangedLocked(
                                    iOnMediaKeyEventSessionChangedListener);
                        } catch (RemoteException e) {
                            Log.w("MediaSessionService", "Failed to add listener", e);
                            fullUserRecordLocked.mOnMediaKeyEventSessionChangedListeners.remove(
                                    asBinder);
                        }
                        Log.d(
                                "MediaSessionService",
                                "The MediaKeyEventSessionChangedListener ("
                                        + iOnMediaKeyEventSessionChangedListener.asBinder()
                                        + ") is added by "
                                        + str);
                        return;
                    }
                    Log.w(
                            "MediaSessionService",
                            "Only the full user can add the listener, userId=" + identifier);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void addSession2TokensListener(
                ISession2TokensListener iSession2TokensListener, int i) {
            if (iSession2TokensListener == null) {
                Log.w(
                        "MediaSessionService",
                        "addSession2TokensListener: listener is null, ignoring");
                return;
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, null);
                synchronized (MediaSessionService.this.mLock) {
                    if (MediaSessionService.m664$$Nest$mfindIndexOfSession2TokensListenerLocked(
                                    MediaSessionService.this, iSession2TokensListener)
                            >= 0) {
                        Log.w(
                                "MediaSessionService",
                                "addSession2TokensListener: listener is already added, ignoring");
                        return;
                    }
                    MediaSessionService mediaSessionService = MediaSessionService.this;
                    ((ArrayList) mediaSessionService.mSession2TokensListenerRecords)
                            .add(
                                    mediaSessionService
                                    .new Session2TokensListenerRecord(
                                            iSession2TokensListener, handleIncomingUser));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void addSessionsListener(
                IActiveSessionsListener iActiveSessionsListener,
                ComponentName componentName,
                int i) {
            String packageName;
            if (iActiveSessionsListener == null) {
                Log.w("MediaSessionService", "addSessionsListener: listener is null, ignoring");
                return;
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (componentName != null) {
                try {
                    packageName = componentName.getPackageName();
                    MediaServerUtils.enforcePackageName(
                            MediaSessionService.this.mContext, packageName, callingUid);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else {
                packageName = null;
            }
            int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, packageName);
            MediaSessionService.this.enforceMediaPermissions(
                    callingPid, callingUid, handleIncomingUser, packageName);
            synchronized (MediaSessionService.this.mLock) {
                if (MediaSessionService.m665$$Nest$mfindIndexOfSessionsListenerLocked(
                                MediaSessionService.this, iActiveSessionsListener)
                        != -1) {
                    Log.w(
                            "MediaSessionService",
                            "ActiveSessionsListener is already added, ignoring");
                    return;
                }
                SessionsListenerRecord sessionsListenerRecord =
                        MediaSessionService.this
                        .new SessionsListenerRecord(
                                iActiveSessionsListener,
                                componentName,
                                handleIncomingUser,
                                callingPid,
                                callingUid);
                try {
                    iActiveSessionsListener.asBinder().linkToDeath(sessionsListenerRecord, 0);
                    MediaSessionService.this.mSessionsListeners.add(sessionsListenerRecord);
                } catch (RemoteException e) {
                    Log.e("MediaSessionService", "ActiveSessionsListener is dead, ignoring it", e);
                }
            }
        }

        public final int adjustVolumeForRotation(int i) {
            WindowManager windowManager;
            CustomDeviceManagerProxy customDeviceManagerProxy =
                    CustomDeviceManagerProxy.getInstance();
            if (customDeviceManagerProxy == null
                    || !customDeviceManagerProxy.getVolumeButtonRotationState()
                    || (windowManager =
                                    (WindowManager)
                                            MediaSessionService.this.mContext.getSystemService(
                                                    "window"))
                            == null) {
                return i;
            }
            int rotation = windowManager.getDefaultDisplay().getRotation();
            if (MediaSessionService.this.mContext.getResources().getConfiguration().orientation
                    == 2) {
                if (rotation != 1 && rotation != 2) {
                    return i;
                }
            } else if (rotation != 2 && rotation != 3) {
                return i;
            }
            return i * (-1);
        }

        public final ISession createSession(
                String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, int i) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    MediaServerUtils.enforcePackageName(
                            MediaSessionService.this.mContext, str, callingUid);
                    int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, str);
                    if (iSessionCallback == null) {
                        throw new IllegalArgumentException("Controller callback cannot be null");
                    }
                    MediaSessionRecord.SessionStub sessionStub =
                            MediaSessionService.m662$$Nest$mcreateSessionInternal(
                                            MediaSessionService.this,
                                            callingPid,
                                            callingUid,
                                            handleIncomingUser,
                                            str,
                                            iSessionCallback,
                                            str2,
                                            bundle)
                                    .mSession;
                    if (sessionStub == null) {
                        throw new IllegalStateException("Invalid session record");
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return sessionStub;
                } catch (Exception e) {
                    Log.w("MediaSessionService", "Exception in creating a new session", e);
                    throw e;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void dispatchAdjustVolume(String str, String str2, int i, int i2, int i3) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (MediaSessionService.this.mLock) {
                    dispatchAdjustVolumeLocked(
                            str, str2, callingPid, callingUid, false, i, i2, i3, false);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dispatchAdjustVolumeLocked(
                final String str,
                final String str2,
                final int i,
                final int i2,
                final boolean z,
                final int i3,
                final int i4,
                final int i5,
                boolean z2) {
            MediaSessionRecord defaultVolumeSession =
                    MediaSessionService.this.isGlobalPriorityActiveLocked()
                            ? MediaSessionService.this.mGlobalPrioritySession
                            : MediaSessionService.this.mCurrentFullUserRecord.mPriorityStack
                                    .getDefaultVolumeSession();
            boolean z3 = true;
            boolean z4 = i3 >= 0 && i3 <= 5 && AudioSystem.isStreamActive(i3, 0);
            if (defaultVolumeSession != null
                    && "com.samsung.android.audiomirroring"
                            .equals(defaultVolumeSession.mPackageName)) {
                z4 =
                        !MediaSessionService.m668$$Nest$misMirroringPackage(
                                        MediaSessionService.this, str)
                                && z4;
            }
            if (!MediaSessionService.this.isGlobalPriorityActiveLocked()
                    && !Constants.SYSTEMUI_PACKAGE_NAME.equals(str)
                    && defaultVolumeSession != null
                    && defaultVolumeSession.mOwnerUid != i2) {
                AudioPlayerStateMonitor audioPlayerStateMonitor =
                        MediaSessionService.this.mAudioPlayerStateMonitor;
                synchronized (audioPlayerStateMonitor.mLock) {
                    try {
                        if (((ArrayList) audioPlayerStateMonitor.mSortedAudioPlaybackClientUids)
                                        .isEmpty()
                                || i2
                                        != ((Integer)
                                                        ((ArrayList)
                                                                        audioPlayerStateMonitor
                                                                                .mSortedAudioPlaybackClientUids)
                                                                .get(0))
                                                .intValue()) {
                            z3 = false;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (z3) {
                    if (Flags.adjustVolumeForForegroundAppPlayingAudioWithoutMediaSession()) {
                        Log.d(
                                "MediaSessionService",
                                "Ignoring session="
                                        + defaultVolumeSession
                                        + " and adjusting suggestedStream="
                                        + i3
                                        + " instead");
                        defaultVolumeSession = null;
                    } else {
                        Log.d(
                                "MediaSessionService",
                                "Session="
                                        + defaultVolumeSession
                                        + " will not be not ignored and will receive the volume"
                                        + " adjustment event");
                    }
                }
            }
            if (defaultVolumeSession != null && !z4) {
                StringBuilder sb = new StringBuilder("Adjusting ");
                sb.append(defaultVolumeSession);
                sb.append(" by ");
                sb.append(i4);
                sb.append(". flags=");
                ServiceKeeper$$ExternalSyntheticOutline0.m(
                        i5, i3, ", suggestedStream=", ", preferSuggestedStream=", sb);
                RCPManagerService$$ExternalSyntheticOutline0.m("MediaSessionService", sb, z4);
                defaultVolumeSession.adjustVolume(str, str2, i, i2, z, i4, i5, true);
                return;
            }
            StringBuilder m =
                    ArrayUtils$$ExternalSyntheticOutline0.m(
                            i3, i4, "Adjusting suggestedStream=", " by ", ". flags=");
            m.append(i5);
            m.append(", preferSuggestedStream=");
            m.append(z4);
            m.append(", session=");
            m.append(defaultVolumeSession);
            Log.d("MediaSessionService", m.toString());
            if (!z2 || AudioSystem.isStreamActive(3, 0) || AudioSystem.isStreamActive(0, 0)) {
                MediaSessionService.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.media.MediaSessionService.SessionManagerImpl.3
                            @Override // java.lang.Runnable
                            public final void run() {
                                String str3;
                                int i6;
                                int i7;
                                if (z) {
                                    str3 = MediaSessionService.this.mContext.getOpPackageName();
                                    i6 = Process.myUid();
                                    i7 = Process.myPid();
                                } else {
                                    str3 = str2;
                                    i6 = i2;
                                    i7 = i;
                                }
                                String str4 = str3;
                                int i8 = i6;
                                int i9 = i7;
                                try {
                                    MediaSessionService mediaSessionService =
                                            MediaSessionService.this;
                                    mediaSessionService.mAudioManager
                                            .adjustSuggestedStreamVolumeForUid(
                                                    i3,
                                                    i4,
                                                    i5,
                                                    str4,
                                                    i8,
                                                    i9,
                                                    mediaSessionService
                                                            .getContext()
                                                            .getApplicationInfo()
                                                            .targetSdkVersion);
                                } catch (IllegalArgumentException | SecurityException e) {
                                    Log.e(
                                            "MediaSessionService",
                                            "Cannot adjust volume: direction="
                                                    + i4
                                                    + ", suggestedStream="
                                                    + i3
                                                    + ", flags="
                                                    + i5
                                                    + ", packageName="
                                                    + str
                                                    + ", uid="
                                                    + i2
                                                    + ", asSystemService="
                                                    + z,
                                            e);
                                }
                            }
                        });
            } else {
                NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                        i5,
                        "Nothing is playing on the music stream. Skipping volume event, flags=",
                        "MediaSessionService");
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:40:0x015e A[Catch: all -> 0x013e, TryCatch #4 {all -> 0x013e, blocks: (B:32:0x0133, B:33:0x0139, B:56:0x01a9, B:38:0x0143, B:40:0x015e, B:41:0x01a4, B:44:0x016e, B:48:0x017a, B:49:0x0194), top: B:12:0x00dc }] */
        /* JADX WARN: Removed duplicated region for block: B:44:0x016e A[Catch: all -> 0x013e, TryCatch #4 {all -> 0x013e, blocks: (B:32:0x0133, B:33:0x0139, B:56:0x01a9, B:38:0x0143, B:40:0x015e, B:41:0x01a4, B:44:0x016e, B:48:0x017a, B:49:0x0194), top: B:12:0x00dc }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dispatchMediaKeyEvent(
                java.lang.String r21, boolean r22, android.view.KeyEvent r23, boolean r24) {
            /*
                Method dump skipped, instructions count: 472
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.MediaSessionService.SessionManagerImpl.dispatchMediaKeyEvent(java.lang.String,"
                        + " boolean, android.view.KeyEvent, boolean):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:120:0x0276  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x01c1  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x01ba  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x01c4  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x0266  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x026d  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0286  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x03c1  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x03dc  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x03c6  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x02ea  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dispatchMediaKeyEventLocked(
                java.lang.String r22,
                int r23,
                int r24,
                boolean r25,
                android.view.KeyEvent r26,
                boolean r27) {
            /*
                Method dump skipped, instructions count: 1058
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.MediaSessionService.SessionManagerImpl.dispatchMediaKeyEventLocked(java.lang.String,"
                        + " int, int, boolean, android.view.KeyEvent, boolean):void");
        }

        public final boolean dispatchMediaKeyEventToSessionAsSystemService(
                String str, KeyEvent keyEvent, MediaSession.Token token) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (MediaSessionService.this.mLock) {
                    MediaSessionRecord m667$$Nest$mgetMediaSessionRecordLocked =
                            MediaSessionService.m667$$Nest$mgetMediaSessionRecordLocked(
                                    MediaSessionService.this, token);
                    Log.d(
                            "MediaSessionService",
                            "dispatchMediaKeyEventToSessionAsSystemService, pkg="
                                    + str
                                    + ", pid="
                                    + callingPid
                                    + ", uid="
                                    + callingUid
                                    + ", sessionToken="
                                    + token
                                    + ", event="
                                    + keyEvent
                                    + ", session="
                                    + m667$$Nest$mgetMediaSessionRecordLocked);
                    if (m667$$Nest$mgetMediaSessionRecordLocked != null) {
                        return m667$$Nest$mgetMediaSessionRecordLocked.sendMediaButton(
                                str, callingPid, callingUid, true, keyEvent, 0, null);
                    }
                    Log.w("MediaSessionService", "Failed to find session to dispatch key event.");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return false;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dispatchVolumeKeyEvent(
                String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) {
            Object obj;
            if (keyEvent == null
                    || !(keyEvent.getKeyCode() == 24
                            || keyEvent.getKeyCode() == 25
                            || keyEvent.getKeyCode() == 164)) {
                Log.w("MediaSessionService", "Attempted to dispatch null or non-volume key event.");
                return;
            }
            if (keyEvent.getAction() == 0
                    && keyEvent.getRepeatCount() == 0
                    && (keyEvent.getFlags() & 268435456) != 0
                    && (keyEvent.getFlags() & 128) != 0) {
                this.mSkippedFirstKeyFromKeyCustomizer = true;
                MediaSessionService.this.mHandler.removeCallbacks(this.mKeyCustomizerRunnable);
                MediaSessionService.this.mHandler.postDelayed(
                        this.mKeyCustomizerRunnable, MediaSessionService.LONG_PRESS_TIMEOUT);
                Log.i("MediaSessionService", "Skip key cause by keycustomizer:" + keyEvent);
                return;
            }
            if ((keyEvent.getFlags() & 128) != 0
                    && keyEvent.getRepeatCount() == 1
                    && this.mSkippedFirstKeyFromKeyCustomizer) {
                KeyEvent changeTimeRepeat =
                        KeyEvent.changeTimeRepeat(keyEvent, keyEvent.getEventTime(), 0);
                changeTimeRepeat.setFlags(keyEvent.getFlags() & (-129));
                dispatchVolumeKeyEvent(str, str2, z, changeTimeRepeat, i, z2);
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            StringBuilder m =
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "dispatchVolumeKeyEvent, pkg=", str, ", opPkg=", str2, ", pid=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    callingPid, callingUid, ", uid=", ", asSystem=", m);
            m.append(z);
            m.append(", event=");
            m.append(keyEvent);
            m.append(", stream=");
            m.append(i);
            m.append(", musicOnly=");
            m.append(z2);
            Log.d("MediaSessionService", m.toString());
            EventLogger eventLogger = this.mEventLogger;
            StringBuilder m2 =
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "VolKeyEvt, pkg=", str, ",opPkg=", str2, ",pid=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    callingPid, callingUid, ",uid=", ",asSystem=", m2);
            m2.append(z);
            m2.append(",code=");
            m2.append(KeyEvent.keyCodeToString(keyEvent.getKeyCode()));
            m2.append(",act:");
            m2.append(KeyEvent.actionToString(keyEvent.getAction()));
            m2.append(",stream=");
            m2.append(i);
            m2.append(",musicOnly=");
            m2.append(z2);
            eventLogger.enqueue(new EventLogger.StringEvent(m2.toString()));
            try {
                Object obj2 = MediaSessionService.this.mLock;
                try {
                    synchronized (obj2) {
                        try {
                            if (MediaSessionService.this.isGlobalPriorityActiveLocked()) {
                                obj = obj2;
                                dispatchVolumeKeyEventLocked(
                                        str, str2, callingPid, callingUid, z, keyEvent, i, z2);
                            } else {
                                obj = obj2;
                                this.mVolumeKeyEventHandler.handleKeyEventLocked(
                                        str,
                                        callingPid,
                                        callingUid,
                                        z,
                                        keyEvent,
                                        false,
                                        str2,
                                        i,
                                        z2);
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
                throw th;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:54:0x00ad, code lost:

           if (r14 == r11.this$0.mSystemServerPid) goto L71;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void dispatchVolumeKeyEventLocked(
                java.lang.String r12,
                java.lang.String r13,
                int r14,
                int r15,
                boolean r16,
                android.view.KeyEvent r17,
                int r18,
                boolean r19) {
            /*
                Method dump skipped, instructions count: 281
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.MediaSessionService.SessionManagerImpl.dispatchVolumeKeyEventLocked(java.lang.String,"
                        + " java.lang.String, int, int, boolean, android.view.KeyEvent, int,"
                        + " boolean):void");
        }

        public final void dispatchVolumeKeyEventToSessionAsSystemService(
                String str, String str2, KeyEvent keyEvent, MediaSession.Token token) {
            Object obj;
            Object obj2 = "Failed to find session to dispatch key event, token=";
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Object obj3 = MediaSessionService.this.mLock;
                try {
                    synchronized (obj3) {
                        try {
                            MediaSessionRecord m667$$Nest$mgetMediaSessionRecordLocked =
                                    MediaSessionService.m667$$Nest$mgetMediaSessionRecordLocked(
                                            MediaSessionService.this, token);
                            MediaSessionRecord defaultVolumeSession =
                                    MediaSessionService.this.mCurrentFullUserRecord.mPriorityStack
                                            .getDefaultVolumeSession();
                            if (defaultVolumeSession != null
                                    && "com.samsung.android.audiomirroring"
                                            .equals(defaultVolumeSession.mPackageName)
                                    && m667$$Nest$mgetMediaSessionRecordLocked != null
                                    && MediaSessionService.m668$$Nest$misMirroringPackage(
                                            MediaSessionService.this,
                                            m667$$Nest$mgetMediaSessionRecordLocked.mPackageName)) {
                                m667$$Nest$mgetMediaSessionRecordLocked = defaultVolumeSession;
                            }
                            Log.d(
                                    "MediaSessionService",
                                    "dispatchVolumeKeyEventToSessionAsSystemService, pkg="
                                            + str
                                            + ", opPkg="
                                            + str2
                                            + ", pid="
                                            + callingPid
                                            + ", uid="
                                            + callingUid
                                            + ", sessionToken="
                                            + token
                                            + ", event="
                                            + keyEvent
                                            + ", session="
                                            + m667$$Nest$mgetMediaSessionRecordLocked);
                            if (m667$$Nest$mgetMediaSessionRecordLocked == null) {
                                Log.w(
                                        "MediaSessionService",
                                        "Failed to find session to dispatch key event, token="
                                                + token
                                                + ". Fallbacks to the default handling.");
                                dispatchVolumeKeyEventLocked(
                                        str,
                                        str2,
                                        callingPid,
                                        callingUid,
                                        true,
                                        keyEvent,
                                        Integer.MIN_VALUE,
                                        false);
                                return;
                            }
                            if (Flags
                                            .fallbackToDefaultHandlingWhenMediaSessionHasFixedVolumeHandling()
                                    && !m667$$Nest$mgetMediaSessionRecordLocked
                                            .canHandleVolumeKey()) {
                                Log.d(
                                        "MediaSessionService",
                                        "Session with packageName="
                                                + m667$$Nest$mgetMediaSessionRecordLocked
                                                        .mPackageName
                                                + " doesn't support volume adjustment. Fallbacks to"
                                                + " the default handling.");
                                dispatchVolumeKeyEventLocked(
                                        str,
                                        str2,
                                        callingPid,
                                        callingUid,
                                        true,
                                        keyEvent,
                                        Integer.MIN_VALUE,
                                        false);
                                return;
                            }
                            int action = keyEvent.getAction();
                            if (action == 0) {
                                obj = obj3;
                                int keyCode = keyEvent.getKeyCode();
                                m667$$Nest$mgetMediaSessionRecordLocked.adjustVolume(
                                        str,
                                        str2,
                                        callingPid,
                                        callingUid,
                                        true,
                                        adjustVolumeForRotation(
                                                keyCode != 24
                                                        ? keyCode != 25
                                                                ? keyCode != 164 ? 0 : 101
                                                                : -1
                                                        : 1),
                                        IntegritySeUtil.CLIENT_INTEGRITY_BASE2,
                                        false);
                            } else if (action != 1) {
                                obj = obj3;
                            } else {
                                obj = obj3;
                                m667$$Nest$mgetMediaSessionRecordLocked.adjustVolume(
                                        str, str2, callingPid, callingUid, true, 0, 4116, false);
                            }
                        } catch (Throwable th) {
                            th = th;
                            obj2 = obj3;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            MediaSessionDataPlatform mediaSessionDataPlatform;
            if (MediaSessionService.this.mContext.checkCallingOrSelfPermission(
                            "android.permission.DUMP")
                    != 0) {
                printWriter.println(
                        "Permission Denial: can't dump MediaSessionService from from pid="
                                + Binder.getCallingPid()
                                + ", uid="
                                + Binder.getCallingUid()
                                + " due to missing android.permission.DUMP permission");
                return;
            }
            printWriter.println("MEDIA SESSION SERVICE (dumpsys media_session)");
            printWriter.println();
            synchronized (MediaSessionService.this.mLock) {
                try {
                    printWriter.println(
                            MediaSessionService.this.mSessionsListeners.size()
                                    + " sessions listeners.");
                    StringBuilder sb = new StringBuilder("Global priority session is ");
                    sb.append(MediaSessionService.this.mGlobalPrioritySession);
                    printWriter.println(sb.toString());
                    MediaSessionRecord mediaSessionRecord =
                            MediaSessionService.this.mGlobalPrioritySession;
                    if (mediaSessionRecord != null) {
                        mediaSessionRecord.dump(printWriter, "  ");
                    }
                    printWriter.println("User Records:");
                    int size = MediaSessionService.this.mUserRecords.size();
                    for (int i = 0; i < size; i++) {
                        ((FullUserRecord) MediaSessionService.this.mUserRecords.valueAt(i))
                                .dumpLocked(printWriter);
                    }
                    printWriter.println(
                            "isAppCastingOn:" + MediaSessionService.this.mIsAppCastingOn);
                    printWriter.println(
                            "isMultiSoundOn:" + MediaSessionService.this.mIsMultiSoundOn);
                    this.mEventLogger.dump(printWriter);
                    printWriter.println();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("isSupportMediaDataPlatform:");
                    boolean z = Rune.SEC_AUDIO_MEDIA_SESSION_AI;
                    sb2.append(z);
                    printWriter.println(sb2.toString());
                    if (z
                            && (mediaSessionDataPlatform =
                                            MediaSessionService.this.mMediaSessionDataPlatform)
                                    != null) {
                        mediaSessionDataPlatform.mEventLogger.dump(printWriter);
                    }
                    printWriter.println();
                    MediaSessionService mediaSessionService = MediaSessionService.this;
                    mediaSessionService.mAudioPlayerStateMonitor.dump(
                            mediaSessionService.mContext, printWriter);
                } catch (Throwable th) {
                    throw th;
                }
            }
            printWriter.println("Media session config:");
            printWriter.println(
                    TextUtils.formatSimple(
                            "  %s: [cur: %s, def: %s]",
                            new Object[] {
                                "media_button_receiver_fgs_allowlist_duration_ms",
                                Long.valueOf(
                                        MediaSessionDeviceConfig
                                                .sMediaButtonReceiverFgsAllowlistDurationMs),
                                10000L
                            }));
            printWriter.println(
                    TextUtils.formatSimple(
                            "  %s: [cur: %s, def: %s]",
                            new Object[] {
                                "media_session_calback_fgs_allowlist_duration_ms",
                                Long.valueOf(
                                        MediaSessionDeviceConfig
                                                .sMediaSessionCallbackFgsAllowlistDurationMs),
                                10000L
                            }));
            printWriter.println(
                    TextUtils.formatSimple(
                            "  %s: [cur: %s, def: %s]",
                            new Object[] {
                                "media_session_callback_fgs_while_in_use_temp_allow_duration_ms",
                                Long.valueOf(
                                        MediaSessionDeviceConfig
                                                .sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs),
                                10000L
                            }));
        }

        public final void expireTempEngagedSessions() {
            if (Flags.enableNotifyingActivityManagerWithMediaSessionStatusChange()) {
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        Iterator it =
                                ((HashMap) MediaSessionService.this.mUserEngagedSessionsForFgs)
                                        .values()
                                        .iterator();
                        while (it.hasNext()) {
                            Iterator it2 = ((Set) it.next()).iterator();
                            while (it2.hasNext()) {
                                ((MediaSessionRecordImpl) it2.next()).expireTempEngaged();
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final MediaSession.Token getMediaKeyEventSession(String str) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaServerUtils.enforcePackageName(
                        MediaSessionService.this.mContext, str, callingUid);
                MediaSessionService.this.enforceMediaPermissions(
                        callingPid, callingUid, identifier, str);
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null) {
                        Log.w(
                                "MediaSessionService",
                                "No matching user record to get the media key event session,"
                                    + " userId="
                                        + identifier);
                        return null;
                    }
                    MediaSessionRecordImpl mediaButtonSessionLocked =
                            fullUserRecordLocked.getMediaButtonSessionLocked();
                    if (mediaButtonSessionLocked instanceof MediaSessionRecord) {
                        return ((MediaSessionRecord) mediaButtonSessionLocked).mSessionToken;
                    }
                    return null;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final String getMediaKeyEventSessionPackageName(String str) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                MediaServerUtils.enforcePackageName(
                        MediaSessionService.this.mContext, str, callingUid);
                MediaSessionService.this.enforceMediaPermissions(
                        callingPid, callingUid, identifier, str);
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked == null) {
                        Log.w(
                                "MediaSessionService",
                                "No matching user record to get the media key event session package"
                                    + " , userId="
                                        + identifier);
                        return "";
                    }
                    MediaSessionRecordImpl mediaButtonSessionLocked =
                            fullUserRecordLocked.getMediaButtonSessionLocked();
                    if (mediaButtonSessionLocked instanceof MediaSessionRecord) {
                        return ((MediaSessionRecord) mediaButtonSessionLocked).mPackageName;
                    }
                    MediaButtonReceiverHolder mediaButtonReceiverHolder =
                            fullUserRecordLocked.mLastMediaButtonReceiverHolder;
                    if (mediaButtonReceiverHolder == null) {
                        return "";
                    }
                    return mediaButtonReceiverHolder.mPackageName;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getSessionPolicies(MediaSession.Token token) {
            synchronized (MediaSessionService.this.mLock) {
                try {
                    MediaSessionRecord m667$$Nest$mgetMediaSessionRecordLocked =
                            MediaSessionService.m667$$Nest$mgetMediaSessionRecordLocked(
                                    MediaSessionService.this, token);
                    if (m667$$Nest$mgetMediaSessionRecordLocked == null) {
                        return 0;
                    }
                    return m667$$Nest$mgetMediaSessionRecordLocked.getSessionPolicies();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List getSessions(ComponentName componentName, int i) {
            String packageName;
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (componentName != null) {
                try {
                    packageName = componentName.getPackageName();
                    MediaServerUtils.enforcePackageName(
                            MediaSessionService.this.mContext, packageName, callingUid);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else {
                packageName = null;
            }
            int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, packageName);
            MediaSessionService.this.enforceMediaPermissions(
                    callingPid, callingUid, handleIncomingUser, packageName);
            ArrayList arrayList = new ArrayList();
            synchronized (MediaSessionService.this.mLock) {
                try {
                    Iterator it =
                            ((ArrayList)
                                            MediaSessionService.this.getActiveSessionsLocked(
                                                    handleIncomingUser))
                                    .iterator();
                    while (it.hasNext()) {
                        arrayList.add(((MediaSessionRecord) it.next()).mSessionToken);
                    }
                } finally {
                }
            }
            return arrayList;
        }

        public final int handleIncomingUser(int i, int i2, int i3, String str) {
            int identifier = UserHandle.getUserHandleForUid(i2).getIdentifier();
            if (i3 == identifier) {
                return i3;
            }
            if (MediaSessionService.this.mContext.checkPermission(
                            "android.permission.INTERACT_ACROSS_USERS_FULL", i, i2)
                    == 0) {
                return i3 == UserHandle.CURRENT.getIdentifier()
                        ? ActivityManager.getCurrentUser()
                        : i3;
            }
            throw new SecurityException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            identifier,
                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                    i3,
                                    "Permission denied while calling from ",
                                    str,
                                    " with user id: ",
                                    "; Need to run as either the calling user id ("),
                            "), or with android.permission.INTERACT_ACROSS_USERS_FULL permission"));
        }

        public final boolean hasCustomMediaKeyDispatcher(String str) {
            MediaSessionService.this.getClass();
            return false;
        }

        public final boolean hasCustomMediaSessionPolicyProvider(String str) {
            MediaSessionPolicyProvider mediaSessionPolicyProvider =
                    MediaSessionService.this.mCustomMediaSessionPolicyProvider;
            if (mediaSessionPolicyProvider == null) {
                return false;
            }
            return TextUtils.equals(str, mediaSessionPolicyProvider.getClass().getName());
        }

        public final boolean hasEnabledNotificationListener(int i, int i2, String str) {
            if (i != UserHandle.getUserHandleForUid(i2).getIdentifier()) {
                return false;
            }
            try {
                if (i2
                        != MediaSessionService.this
                                .mContext
                                .getPackageManager()
                                .getPackageUidAsUser(str, UserHandle.getUserId(i2))) {
                    Log.w(
                            "MediaSessionService",
                            "Failed to check enabled notification listener. Package name and UID"
                                + " doesn't match");
                    return false;
                }
                if (MediaSessionService.this.mNotificationManager.hasEnabledNotificationListener(
                        str, UserHandle.getUserHandleForUid(i2))) {
                    return true;
                }
                int i3 = MediaSessionService.LONG_PRESS_TIMEOUT;
                Log.d(
                        "MediaSessionService",
                        str + " (uid=" + i2 + ") doesn't have an enabled notification listener");
                return false;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w(
                        "MediaSessionService",
                        "Failed to check enabled notification listener. Package name doesn't"
                            + " exist");
                return false;
            }
        }

        public final boolean isGlobalPriorityActive() {
            boolean isGlobalPriorityActiveLocked;
            synchronized (MediaSessionService.this.mLock) {
                isGlobalPriorityActiveLocked =
                        MediaSessionService.this.isGlobalPriorityActiveLocked();
            }
            return isGlobalPriorityActiveLocked;
        }

        public final boolean isTrusted(String str, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            boolean z = true;
            if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                    .filterAppAccess(callingUid, identifier, str, true)) {
                return false;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!MediaSessionService.this.hasMediaControlPermission(i, i2)) {
                    if (!hasEnabledNotificationListener(identifier, i2, str)) {
                        z = false;
                    }
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(
                FileDescriptor fileDescriptor,
                FileDescriptor fileDescriptor2,
                FileDescriptor fileDescriptor3,
                String[] strArr,
                ShellCallback shellCallback,
                ResultReceiver resultReceiver) {
            String[] packagesForUid =
                    MediaSessionService.this
                            .mContext
                            .getPackageManager()
                            .getPackagesForUid(Binder.getCallingUid());
            new MediaShellCommand(
                            (packagesForUid == null || packagesForUid.length <= 0)
                                    ? "com.android.shell"
                                    : packagesForUid[0])
                    .exec(
                            this,
                            fileDescriptor,
                            fileDescriptor2,
                            fileDescriptor3,
                            strArr,
                            shellCallback,
                            resultReceiver);
        }

        public final void registerRemoteSessionCallback(
                IRemoteSessionCallback iRemoteSessionCallback) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            synchronized (MediaSessionService.this.mLock) {
                try {
                    if (MediaSessionService.this.mContext.checkPermission(
                                    "android.permission.STATUS_BAR_SERVICE", callingPid, callingUid)
                            != 0) {
                        throw new SecurityException(
                                "Only System UI and Settings may listen for volume changes");
                    }
                    MediaSessionService.this.mRemoteVolumeControllers.register(
                            iRemoteSessionCallback);
                    this.mEventLogger.enqueue(
                            new EventLogger.StringEvent(
                                    "registerRemoteVolumeController uid : "
                                            + callingUid
                                            + ", pid : "
                                            + callingPid
                                            + ", rvc : "
                                            + iRemoteSessionCallback.toString()));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final void removeOnMediaKeyEventDispatchedListener(
                IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) {
            if (iOnMediaKeyEventDispatchedListener == null) {
                Log.w(
                        "MediaSessionService",
                        "removeOnMediaKeyEventDispatchedListener: listener is null, ignoring");
                return;
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!MediaSessionService.this.hasMediaControlPermission(callingPid, callingUid)) {
                    throw new SecurityException(
                            "MEDIA_CONTENT_CONTROL permission is required to  remove"
                                + " MediaKeyEventDispatchedListener");
                }
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked != null
                            && fullUserRecordLocked.mFullUserId == identifier) {
                        IBinder asBinder = iOnMediaKeyEventDispatchedListener.asBinder();
                        asBinder.unlinkToDeath(
                                (FullUserRecord.OnMediaKeyEventDispatchedListenerRecord)
                                        fullUserRecordLocked.mOnMediaKeyEventDispatchedListeners
                                                .remove(asBinder),
                                0);
                        Log.d(
                                "MediaSessionService",
                                "The MediaKeyEventDispatchedListener ("
                                        + iOnMediaKeyEventDispatchedListener.asBinder()
                                        + ") is removed by "
                                        + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                                MediaSessionService.this, callingUid));
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    Log.w(
                            "MediaSessionService",
                            "Only the full user can remove the listener, userId=" + identifier);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void removeOnMediaKeyEventSessionChangedListener(
                IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) {
            if (iOnMediaKeyEventSessionChangedListener == null) {
                Log.w(
                        "MediaSessionService",
                        "removeOnMediaKeyEventSessionChangedListener: listener is null, ignoring");
                return;
            }
            Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (MediaSessionService.this.mLock) {
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked != null
                            && fullUserRecordLocked.mFullUserId == identifier) {
                        IBinder asBinder = iOnMediaKeyEventSessionChangedListener.asBinder();
                        asBinder.unlinkToDeath(
                                (FullUserRecord.OnMediaKeyEventSessionChangedListenerRecord)
                                        fullUserRecordLocked.mOnMediaKeyEventSessionChangedListeners
                                                .remove(asBinder),
                                0);
                        Log.d(
                                "MediaSessionService",
                                "The MediaKeyEventSessionChangedListener ("
                                        + iOnMediaKeyEventSessionChangedListener.asBinder()
                                        + ") is removed by "
                                        + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                                MediaSessionService.this, callingUid));
                        return;
                    }
                    Log.w(
                            "MediaSessionService",
                            "Only the full user can remove the listener, userId=" + identifier);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeSession2TokensListener(
                ISession2TokensListener iSession2TokensListener) {
            Binder.getCallingPid();
            Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (MediaSessionService.this.mLock) {
                    int m664$$Nest$mfindIndexOfSession2TokensListenerLocked =
                            MediaSessionService.m664$$Nest$mfindIndexOfSession2TokensListenerLocked(
                                    MediaSessionService.this, iSession2TokensListener);
                    if (m664$$Nest$mfindIndexOfSession2TokensListenerLocked >= 0) {
                        Session2TokensListenerRecord session2TokensListenerRecord =
                                (Session2TokensListenerRecord)
                                        ((ArrayList)
                                                        MediaSessionService.this
                                                                .mSession2TokensListenerRecords)
                                                .remove(
                                                        m664$$Nest$mfindIndexOfSession2TokensListenerLocked);
                        try {
                            session2TokensListenerRecord
                                    .listener
                                    .asBinder()
                                    .unlinkToDeath(session2TokensListenerRecord, 0);
                        } catch (Exception unused) {
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) {
            synchronized (MediaSessionService.this.mLock) {
                int m665$$Nest$mfindIndexOfSessionsListenerLocked =
                        MediaSessionService.m665$$Nest$mfindIndexOfSessionsListenerLocked(
                                MediaSessionService.this, iActiveSessionsListener);
                if (m665$$Nest$mfindIndexOfSessionsListenerLocked != -1) {
                    SessionsListenerRecord sessionsListenerRecord =
                            (SessionsListenerRecord)
                                    MediaSessionService.this.mSessionsListeners.remove(
                                            m665$$Nest$mfindIndexOfSessionsListenerLocked);
                    try {
                        sessionsListenerRecord
                                .listener
                                .asBinder()
                                .unlinkToDeath(sessionsListenerRecord, 0);
                    } catch (Exception unused) {
                    }
                }
            }
        }

        public final void setCustomMediaKeyDispatcher(String str) {
            MediaSessionService.this.instantiateCustomDispatcher(str);
        }

        public final void setCustomMediaSessionPolicyProvider(String str) {
            MediaSessionService.this.instantiateCustomProvider(str);
        }

        public final void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (MediaSessionService.this.mContext.checkPermission(
                                "android.permission.SET_MEDIA_KEY_LISTENER", callingPid, callingUid)
                        != 0) {
                    throw new SecurityException("Must hold the SET_MEDIA_KEY_LISTENER permission.");
                }
                synchronized (MediaSessionService.this.mLock) {
                    int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked != null
                            && fullUserRecordLocked.mFullUserId == identifier) {
                        if (fullUserRecordLocked.mOnMediaKeyListener != null
                                && fullUserRecordLocked.mOnMediaKeyListenerUid != callingUid) {
                            Log.w(
                                    "MediaSessionService",
                                    "The media key listener cannot be reset by another app. ,"
                                        + " mOnMediaKeyListenerUid="
                                            + fullUserRecordLocked.mOnMediaKeyListenerUid
                                            + ", uid="
                                            + callingUid);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return;
                        }
                        fullUserRecordLocked.mOnMediaKeyListener = iOnMediaKeyListener;
                        fullUserRecordLocked.mOnMediaKeyListenerUid = callingUid;
                        Log.d(
                                "MediaSessionService",
                                "The media key listener "
                                        + fullUserRecordLocked.mOnMediaKeyListener
                                        + " is set by "
                                        + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                                MediaSessionService.this, callingUid));
                        IOnMediaKeyListener iOnMediaKeyListener2 =
                                fullUserRecordLocked.mOnMediaKeyListener;
                        if (iOnMediaKeyListener2 != null) {
                            try {
                                iOnMediaKeyListener2
                                        .asBinder()
                                        .linkToDeath(
                                                new AnonymousClass1(this, fullUserRecordLocked, 1),
                                                0);
                            } catch (RemoteException unused) {
                                Log.w(
                                        "MediaSessionService",
                                        "Failed to set death recipient "
                                                + fullUserRecordLocked.mOnMediaKeyListener);
                                fullUserRecordLocked.mOnMediaKeyListener = null;
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    Log.w(
                            "MediaSessionService",
                            "Only the full user can set the media key listener, userId="
                                    + identifier);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void setOnVolumeKeyLongPressListener(
                IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (MediaSessionService.this.mContext.checkPermission(
                                "android.permission.SET_VOLUME_KEY_LONG_PRESS_LISTENER",
                                callingPid,
                                callingUid)
                        != 0) {
                    throw new SecurityException(
                            "Must hold the SET_VOLUME_KEY_LONG_PRESS_LISTENER permission.");
                }
                synchronized (MediaSessionService.this.mLock) {
                    int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
                    FullUserRecord fullUserRecordLocked =
                            MediaSessionService.this.getFullUserRecordLocked(identifier);
                    if (fullUserRecordLocked != null
                            && fullUserRecordLocked.mFullUserId == identifier) {
                        if (fullUserRecordLocked.mOnVolumeKeyLongPressListener != null
                                && fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid
                                        != callingUid) {
                            Log.w(
                                    "MediaSessionService",
                                    "The volume key long-press listener cannot be reset by another"
                                        + " app , mOnVolumeKeyLongPressListener="
                                            + fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid
                                            + ", uid="
                                            + callingUid);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            return;
                        }
                        fullUserRecordLocked.mOnVolumeKeyLongPressListener =
                                iOnVolumeKeyLongPressListener;
                        fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid = callingUid;
                        Log.d(
                                "MediaSessionService",
                                "The volume key long-press listener "
                                        + iOnVolumeKeyLongPressListener
                                        + " is set by "
                                        + MediaSessionService.m666$$Nest$mgetCallingPackageName(
                                                MediaSessionService.this, callingUid));
                        IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener2 =
                                fullUserRecordLocked.mOnVolumeKeyLongPressListener;
                        if (iOnVolumeKeyLongPressListener2 != null) {
                            try {
                                iOnVolumeKeyLongPressListener2
                                        .asBinder()
                                        .linkToDeath(
                                                new AnonymousClass1(this, fullUserRecordLocked, 0),
                                                0);
                            } catch (RemoteException unused) {
                                Log.w(
                                        "MediaSessionService",
                                        "Failed to set death recipient "
                                                + fullUserRecordLocked
                                                        .mOnVolumeKeyLongPressListener);
                                fullUserRecordLocked.mOnVolumeKeyLongPressListener = null;
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return;
                    }
                    Log.w(
                            "MediaSessionService",
                            "Only the full user can set the volume key long-press listener, userId="
                                    + identifier);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void setSessionPolicies(MediaSession.Token token, int i) {
            FullUserRecord fullUserRecordLocked;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (MediaSessionService.this.mLock) {
                    try {
                        MediaSessionRecord m667$$Nest$mgetMediaSessionRecordLocked =
                                MediaSessionService.m667$$Nest$mgetMediaSessionRecordLocked(
                                        MediaSessionService.this, token);
                        if (m667$$Nest$mgetMediaSessionRecordLocked != null
                                && (fullUserRecordLocked =
                                                MediaSessionService.this.getFullUserRecordLocked(
                                                        m667$$Nest$mgetMediaSessionRecordLocked
                                                                .mUserId))
                                        != null) {
                            synchronized (m667$$Nest$mgetMediaSessionRecordLocked.mLock) {
                                m667$$Nest$mgetMediaSessionRecordLocked.mPolicies = i;
                            }
                            MediaSessionStack mediaSessionStack =
                                    fullUserRecordLocked.mPriorityStack;
                            mediaSessionStack.getClass();
                            if ((m667$$Nest$mgetMediaSessionRecordLocked.getSessionPolicies() & 2)
                                    == 0) {
                                mediaSessionStack.updateMediaButtonSessionIfNeeded();
                            } else if (m667$$Nest$mgetMediaSessionRecordLocked
                                    == mediaSessionStack.mMediaButtonSession) {
                                mediaSessionStack.updateMediaButtonSession(null);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterRemoteSessionCallback(
                IRemoteSessionCallback iRemoteSessionCallback) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            synchronized (MediaSessionService.this.mLock) {
                try {
                    if (MediaSessionService.this.mContext.checkPermission(
                                    "android.permission.STATUS_BAR_SERVICE", callingPid, callingUid)
                            != 0) {
                        throw new SecurityException(
                                "Only System UI and Settings may listen for volume changes");
                    }
                    MediaSessionService.this.mRemoteVolumeControllers.unregister(
                            iRemoteSessionCallback);
                    this.mEventLogger.enqueue(
                            new EventLogger.StringEvent(
                                    "unregisterRemoteVolumeController uid : "
                                            + callingUid
                                            + ", pid : "
                                            + callingPid
                                            + ", rvc : "
                                            + iRemoteSessionCallback.toString()));
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionsListenerRecord implements IBinder.DeathRecipient {
        public final ComponentName componentName;
        public final IActiveSessionsListener listener;
        public final int pid;
        public final int uid;
        public final int userId;

        public SessionsListenerRecord(
                IActiveSessionsListener iActiveSessionsListener,
                ComponentName componentName,
                int i,
                int i2,
                int i3) {
            this.listener = iActiveSessionsListener;
            this.componentName = componentName;
            this.userId = i;
            this.pid = i2;
            this.uid = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (MediaSessionService.this.mLock) {
                MediaSessionService.this.mSessionsListeners.remove(this);
            }
        }
    }

    /* renamed from: -$$Nest$mcreateSessionInternal, reason: not valid java name */
    public static MediaSessionRecord m662$$Nest$mcreateSessionInternal(
            MediaSessionService mediaSessionService,
            int i,
            int i2,
            int i3,
            String str,
            ISessionCallback iSessionCallback,
            String str2,
            Bundle bundle) {
        MediaSessionRecord mediaSessionRecord;
        synchronized (mediaSessionService.mLock) {
            try {
                MediaSessionPolicyProvider mediaSessionPolicyProvider =
                        mediaSessionService.mCustomMediaSessionPolicyProvider;
                int sessionPoliciesForApplication =
                        mediaSessionPolicyProvider != null
                                ? mediaSessionPolicyProvider.getSessionPoliciesForApplication(
                                        i2, str)
                                : 0;
                FullUserRecord fullUserRecordLocked =
                        mediaSessionService.getFullUserRecordLocked(i3);
                if (fullUserRecordLocked == null) {
                    Log.w(
                            "MediaSessionService",
                            "Request from invalid user: " + i3 + ", pkg=" + str);
                    throw new RuntimeException("Session request from invalid user.");
                }
                int i4 = fullUserRecordLocked.mUidToSessionCount.get(i2, 0);
                if (i4 >= 100 && !mediaSessionService.hasMediaControlPermission(i, i2)) {
                    throw new RuntimeException("Created too many sessions. count=" + i4 + ")");
                }
                try {
                    mediaSessionRecord =
                            new MediaSessionRecord(
                                    i,
                                    i2,
                                    i3,
                                    str,
                                    iSessionCallback,
                                    str2,
                                    bundle,
                                    mediaSessionService,
                                    mediaSessionService.mRecordThread.getLooper(),
                                    sessionPoliciesForApplication);
                    fullUserRecordLocked.mUidToSessionCount.put(i2, i4 + 1);
                    fullUserRecordLocked.mPriorityStack.addSession(mediaSessionRecord);
                    mediaSessionService.mHandler.postSessionsChanged(mediaSessionRecord);
                    Log.d(
                            "MediaSessionService",
                            "Created session for " + str + " with tag " + str2);
                } catch (RemoteException e) {
                    throw new RuntimeException("Media Session owner died prematurely.", e);
                }
            } finally {
            }
        }
        return mediaSessionRecord;
    }

    /* renamed from: -$$Nest$mdispatchVolumeKeyLongPressLocked, reason: not valid java name */
    public static void m663$$Nest$mdispatchVolumeKeyLongPressLocked(
            MediaSessionService mediaSessionService, KeyEvent keyEvent) {
        if (mediaSessionService.mVolumeKeyLongPressReceiver != null
                && keyEvent.getRepeatCount() % 15 == 0) {
            Intent intent = new Intent("com.samsung.android.intent.action.SOUND_EVENT");
            intent.putExtra("type", 32);
            intent.putExtra("keyevent", keyEvent);
            intent.setComponent(mediaSessionService.mVolumeKeyLongPressReceiver);
            mediaSessionService.getContext().sendBroadcastAsUser(intent, UserHandle.ALL);
        }
        IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener =
                mediaSessionService.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener;
        if (iOnVolumeKeyLongPressListener == null) {
            return;
        }
        try {
            iOnVolumeKeyLongPressListener.onVolumeKeyLongPress(keyEvent);
        } catch (RemoteException unused) {
            Log.w(
                    "MediaSessionService",
                    "Failed to send " + keyEvent + " to volume key long-press listener");
        }
    }

    /* renamed from: -$$Nest$mfindIndexOfSession2TokensListenerLocked, reason: not valid java name */
    public static int m664$$Nest$mfindIndexOfSession2TokensListenerLocked(
            MediaSessionService mediaSessionService,
            ISession2TokensListener iSession2TokensListener) {
        for (int size = ((ArrayList) mediaSessionService.mSession2TokensListenerRecords).size() - 1;
                size >= 0;
                size--) {
            if (((Session2TokensListenerRecord)
                                    ((ArrayList) mediaSessionService.mSession2TokensListenerRecords)
                                            .get(size))
                            .listener.asBinder()
                    == iSession2TokensListener.asBinder()) {
                return size;
            }
        }
        return -1;
    }

    /* renamed from: -$$Nest$mfindIndexOfSessionsListenerLocked, reason: not valid java name */
    public static int m665$$Nest$mfindIndexOfSessionsListenerLocked(
            MediaSessionService mediaSessionService,
            IActiveSessionsListener iActiveSessionsListener) {
        for (int size = mediaSessionService.mSessionsListeners.size() - 1; size >= 0; size--) {
            if (((SessionsListenerRecord) mediaSessionService.mSessionsListeners.get(size))
                            .listener.asBinder()
                    == iActiveSessionsListener.asBinder()) {
                return size;
            }
        }
        return -1;
    }

    /* renamed from: -$$Nest$mgetCallingPackageName, reason: not valid java name */
    public static String m666$$Nest$mgetCallingPackageName(
            MediaSessionService mediaSessionService, int i) {
        String[] packagesForUid =
                mediaSessionService.mContext.getPackageManager().getPackagesForUid(i);
        return (packagesForUid == null || packagesForUid.length <= 0) ? "" : packagesForUid[0];
    }

    /* renamed from: -$$Nest$mgetMediaSessionRecordLocked, reason: not valid java name */
    public static MediaSessionRecord m667$$Nest$mgetMediaSessionRecordLocked(
            MediaSessionService mediaSessionService, MediaSession.Token token) {
        mediaSessionService.getClass();
        FullUserRecord fullUserRecordLocked =
                mediaSessionService.getFullUserRecordLocked(
                        UserHandle.getUserHandleForUid(token.getUid()).getIdentifier());
        if (fullUserRecordLocked == null) {
            return null;
        }
        Iterator it = ((ArrayList) fullUserRecordLocked.mPriorityStack.mSessions).iterator();
        while (it.hasNext()) {
            MediaSessionRecordImpl mediaSessionRecordImpl = (MediaSessionRecordImpl) it.next();
            if (mediaSessionRecordImpl instanceof MediaSessionRecord) {
                MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) mediaSessionRecordImpl;
                if (Objects.equals(mediaSessionRecord.mSessionToken, token)) {
                    return mediaSessionRecord;
                }
            }
        }
        return null;
    }

    /* renamed from: -$$Nest$misMirroringPackage, reason: not valid java name */
    public static boolean m668$$Nest$misMirroringPackage(
            MediaSessionService mediaSessionService, String str) {
        mediaSessionService.getClass();
        try {
            Bundle call =
                    mediaSessionService
                            .mContext
                            .getContentResolver()
                            .call(
                                    Uri.parse("content://com.samsung.android.audiomirroring"),
                                    "request_package_name",
                                    (String) null,
                                    (Bundle) null);
            if (call != null && call.getString("mediaPackageName", "").equals(str)) {
                Log.d("MediaSessionService", "audiomirroring package: " + str);
                return true;
            }
        } catch (IllegalArgumentException e) {
            Log.d("MediaSessionService", e.getMessage());
        }
        return false;
    }

    /* renamed from: -$$Nest$mneedVolumeKeyLongPressBroadCastLocked, reason: not valid java name */
    public static boolean m669$$Nest$mneedVolumeKeyLongPressBroadCastLocked(
            MediaSessionService mediaSessionService) {
        return (mediaSessionService.mVolumeKeyLongPressReceiver == null
                        || mediaSessionService.mCurrentFullUserRecord.getMediaButtonSessionLocked()
                                == null
                        || (((PowerManager)
                                                mediaSessionService
                                                        .getContext()
                                                        .getSystemService("power"))
                                        .isInteractive()
                                && !mediaSessionService.mKeyguardManager.isKeyguardLocked()))
                ? false
                : true;
    }

    static {
        ViewConfiguration.getMultiPressTimeout();
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [com.android.server.media.MediaSessionService$1] */
    /* JADX WARN: Type inference failed for: r0v13, types: [com.android.server.media.MediaSessionService$2] */
    public MediaSessionService(Context context) {
        super(context);
        MediaSessionDataPlatform mediaSessionDataPlatform;
        this.mHandler = new MessageHandler();
        this.mLock = new Object();
        this.mRecordThread = new HandlerThread("SessionRecordThread");
        this.mFullUserIds = new SparseIntArray();
        this.mUserRecords = new SparseArray();
        this.mSessionsListeners = new ArrayList();
        this.mSession2TokensListenerRecords = new ArrayList();
        this.mUserEngagedSessionsForUsageLogging = new SparseArray();
        this.mUserEngagedSessionsForFgs = new HashMap();
        this.mMediaNotifications = new HashMap();
        this.mFgsAllowedMediaSessionRecords = new HashSet();
        this.mRemoteVolumeControllers = new RemoteCallbackList();
        this.mSession2TokenCallback =
                new MediaCommunicationManager
                        .SessionCallback() { // from class:
                                             // com.android.server.media.MediaSessionService.1
                    public final void onSession2TokenCreated(Session2Token session2Token, int i) {
                        int i2 = MediaSessionService.LONG_PRESS_TIMEOUT;
                        Log.d("MediaSessionService", "Session2 is created " + session2Token);
                        MediaSessionService mediaSessionService = MediaSessionService.this;
                        MediaSession2Record mediaSession2Record =
                                new MediaSession2Record(
                                        session2Token,
                                        mediaSessionService,
                                        mediaSessionService.mRecordThread.getLooper());
                        synchronized (MediaSessionService.this.mLock) {
                            try {
                                FullUserRecord fullUserRecordLocked =
                                        MediaSessionService.this.getFullUserRecordLocked(
                                                mediaSession2Record.getUserId());
                                if (fullUserRecordLocked != null) {
                                    fullUserRecordLocked.mPriorityStack.addSession(
                                            mediaSession2Record);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mNotificationListenerEnabledChangedReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.media.MediaSessionService.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        MediaSessionService mediaSessionService = MediaSessionService.this;
                        synchronized (mediaSessionService.mLock) {
                            for (int size = mediaSessionService.mSessionsListeners.size() - 1;
                                    size >= 0;
                                    size--) {
                                SessionsListenerRecord sessionsListenerRecord =
                                        (SessionsListenerRecord)
                                                mediaSessionService.mSessionsListeners.get(size);
                                try {
                                    ComponentName componentName =
                                            sessionsListenerRecord.componentName;
                                    mediaSessionService.enforceMediaPermissions(
                                            sessionsListenerRecord.pid,
                                            sessionsListenerRecord.uid,
                                            sessionsListenerRecord.userId,
                                            componentName == null
                                                    ? null
                                                    : componentName.getPackageName());
                                } catch (SecurityException unused) {
                                    Log.i(
                                            "MediaSessionService",
                                            "ActiveSessionsListener "
                                                    + sessionsListenerRecord.componentName
                                                    + " is no longer authorized. Disconnecting.");
                                    mediaSessionService.mSessionsListeners.remove(size);
                                    try {
                                        sessionsListenerRecord.listener.onActiveSessionsChanged(
                                                new ArrayList());
                                    } catch (Exception unused2) {
                                    }
                                }
                            }
                        }
                    }
                };
        this.mCoverHelper = CoverHelper.getInstance();
        this.mIsMultiSoundOn = false;
        this.mDevice = 0;
        this.mIsAppCastingOn = false;
        this.mAppCastingUid = -1;
        this.mVolumeKeyLongPressReceiver = null;
        this.mHighPriorityMediaKeyReceiver = null;
        this.mContext = context;
        this.mSessionManagerImpl = new SessionManagerImpl();
        this.mMediaEventWakeLock =
                ((PowerManager) context.getSystemService(PowerManager.class))
                        .newWakeLock(1, "handleMediaEvent");
        this.mNotificationManager =
                (NotificationManager) context.getSystemService(NotificationManager.class);
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mNotificationListener = new NotificationListener();
        LocalServices.addService(
                MediaSessionServiceInternal.class, new MediaSessionServiceInternal());
        this.mAudioServiceInternal =
                (SemAudioServiceInternal) LocalServices.getService(SemAudioServiceInternal.class);
        this.mSystemServerPid = Process.myPid();
        this.mDesktopModeHelper = DesktopModeHelper.getInstance(context);
        this.mPowerManagerInternal =
                (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
        if (Rune.SEC_AUDIO_MEDIA_SESSION_AI) {
            synchronized (MediaSessionDataPlatform.class) {
                try {
                    if (MediaSessionDataPlatform.sInstance == null) {
                        MediaSessionDataPlatform.sInstance = new MediaSessionDataPlatform(context);
                    }
                    mediaSessionDataPlatform = MediaSessionDataPlatform.sInstance;
                } catch (Throwable th) {
                    throw th;
                }
            }
            this.mMediaSessionDataPlatform = mediaSessionDataPlatform;
        }
    }

    public final void destroySessionLocked(MediaSessionRecordImpl mediaSessionRecordImpl) {
        Log.d("MediaSessionService", "Destroying " + mediaSessionRecordImpl);
        if (mediaSessionRecordImpl.isClosed()) {
            Log.w("MediaSessionService", "Destroying already destroyed session. Ignoring.");
            return;
        }
        FullUserRecord fullUserRecordLocked =
                getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
        if (fullUserRecordLocked != null
                && (mediaSessionRecordImpl instanceof MediaSessionRecord)) {
            int uid = mediaSessionRecordImpl.getUid();
            int i = fullUserRecordLocked.mUidToSessionCount.get(uid, 0);
            if (i <= 0) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i,
                        "destroySessionLocked: sessionCount should be positive. sessionCount=",
                        "MediaSessionService");
            } else {
                fullUserRecordLocked.mUidToSessionCount.put(uid, i - 1);
            }
        }
        if (this.mGlobalPrioritySession == mediaSessionRecordImpl) {
            this.mGlobalPrioritySession = null;
            if (mediaSessionRecordImpl.isActive() && fullUserRecordLocked != null) {
                fullUserRecordLocked.pushAddressedPlayerChangedLocked();
            }
        } else if (fullUserRecordLocked != null) {
            fullUserRecordLocked.mPriorityStack.removeSession(mediaSessionRecordImpl);
        }
        mediaSessionRecordImpl.close();
        Log.d("MediaSessionService", "destroySessionLocked: record=" + mediaSessionRecordImpl);
        reportMediaInteractionEvent(mediaSessionRecordImpl, false);
        this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
        if (Rune.SEC_AUDIO_MEDIA_SESSION_AI) {
            sendChangedMessages(mediaSessionRecordImpl, 3);
        }
    }

    public final void enforceMediaPermissions(int i, int i2, int i3, String str) {
        boolean hasEnabledNotificationListener;
        if (this.mContext.checkPermission("android.permission.STATUS_BAR_SERVICE", i, i2) == 0
                || hasMediaControlPermission(i, i2)) {
            return;
        }
        if (str != null) {
            UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i2);
            if (userHandleForUid.getIdentifier() != i3) {
                hasEnabledNotificationListener = false;
            } else {
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        "Checking whether the package ",
                        str,
                        " has an enabled notification listener.",
                        "MediaSessionService");
                hasEnabledNotificationListener =
                        this.mNotificationManager.hasEnabledNotificationListener(
                                str, userHandleForUid);
            }
            if (hasEnabledNotificationListener) {
                return;
            }
        }
        throw new SecurityException("Missing permission to control media.");
    }

    public final List getActiveSessionsLocked(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == UserHandle.ALL.getIdentifier()) {
            int size = this.mUserRecords.size();
            for (int i2 = 0; i2 < size; i2++) {
                MediaSessionStack mediaSessionStack =
                        ((FullUserRecord) this.mUserRecords.valueAt(i2)).mPriorityStack;
                List list = (List) mediaSessionStack.mCachedActiveLists.get(i);
                if (list == null) {
                    list = mediaSessionStack.getPriorityList(i, true);
                    mediaSessionStack.mCachedActiveLists.put(i, list);
                }
                arrayList.addAll(list);
            }
        } else {
            FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i);
            if (fullUserRecordLocked == null) {
                NetworkScoreService$$ExternalSyntheticOutline0.m(
                        i, "getSessions failed. Unknown user ", "MediaSessionService");
                return arrayList;
            }
            MediaSessionStack mediaSessionStack2 = fullUserRecordLocked.mPriorityStack;
            List list2 = (List) mediaSessionStack2.mCachedActiveLists.get(i);
            if (list2 == null) {
                list2 = mediaSessionStack2.getPriorityList(i, true);
                mediaSessionStack2.mCachedActiveLists.put(i, list2);
            }
            arrayList.addAll(list2);
        }
        if (isGlobalPriorityActiveLocked()
                && (i == UserHandle.ALL.getIdentifier()
                        || i == this.mGlobalPrioritySession.mUserId)) {
            arrayList.add(0, this.mGlobalPrioritySession);
        }
        return arrayList;
    }

    public final FullUserRecord getFullUserRecordLocked(int i) {
        int i2 = this.mFullUserIds.get(i, -1);
        if (i2 < 0) {
            return null;
        }
        return (FullUserRecord) this.mUserRecords.get(i2);
    }

    public final List getSession2TokensLocked(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == UserHandle.ALL.getIdentifier()) {
            int size = this.mUserRecords.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.addAll(
                        ((FullUserRecord) this.mUserRecords.valueAt(i2))
                                .mPriorityStack.getSession2Tokens(i));
            }
        } else {
            arrayList.addAll(getFullUserRecordLocked(i).mPriorityStack.getSession2Tokens(i));
        }
        return arrayList;
    }

    public final boolean hasMediaControlPermission(int i, int i2) {
        if (i2 == 1000
                || this.mContext.checkPermission("android.permission.MEDIA_CONTENT_CONTROL", i, i2)
                        == 0) {
            return true;
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(
                i2, "uid(", ") hasn't granted MEDIA_CONTENT_CONTROL", "MediaSessionService");
        return false;
    }

    public final void instantiateCustomDispatcher(String str) {
        synchronized (this.mLock) {
            if (str != null) {
                try {
                    if (!TextUtils.isEmpty(str)) {
                        DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                Class.forName(str)
                                        .getDeclaredConstructor(Context.class)
                                        .newInstance(this.mContext));
                    }
                } catch (ClassNotFoundException
                        | IllegalAccessException
                        | InstantiationException
                        | NoSuchMethodException
                        | InvocationTargetException e) {
                    Log.w("MediaSessionService", "Encountered problem while using reflection", e);
                }
            }
        }
    }

    public final void instantiateCustomProvider(String str) {
        synchronized (this.mLock) {
            this.mCustomMediaSessionPolicyProvider = null;
            if (str != null) {
                try {
                    if (!TextUtils.isEmpty(str)) {
                        this.mCustomMediaSessionPolicyProvider =
                                (MediaSessionPolicyProvider)
                                        Class.forName(str)
                                                .getDeclaredConstructor(Context.class)
                                                .newInstance(this.mContext);
                    }
                } catch (ClassNotFoundException
                        | IllegalAccessException
                        | InstantiationException
                        | NoSuchMethodException
                        | InvocationTargetException e) {
                    Log.w("MediaSessionService", "Encountered problem while using reflection", e);
                }
            }
        }
    }

    public final boolean isGlobalPriorityActiveLocked() {
        MediaSessionRecord mediaSessionRecord = this.mGlobalPrioritySession;
        return mediaSessionRecord != null && mediaSessionRecord.isActive();
    }

    public final boolean isMultiSoundOn() {
        return this.mIsMultiSoundOn || this.mIsAppCastingOn;
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        synchronized (this.mLock) {
        }
    }

    public final void notifyRemoteVolumeChanged(int i, MediaSessionRecord mediaSessionRecord) {
        if (mediaSessionRecord.isActive()) {
            synchronized (this.mLock) {
                int beginBroadcast = this.mRemoteVolumeControllers.beginBroadcast();
                MediaSession.Token token = mediaSessionRecord.mSessionToken;
                for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
                    try {
                        this.mRemoteVolumeControllers
                                .getBroadcastItem(i2)
                                .onVolumeChanged(token, i);
                    } catch (Exception e) {
                        Log.w("MediaSessionService", "Error sending volume change.", e);
                    }
                }
                this.mRemoteVolumeControllers.finishBroadcast();
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 550) {
            DeviceConfig.addOnPropertiesChangedListener(
                    "media",
                    this.mContext.getMainExecutor(),
                    new MediaSessionDeviceConfig$$ExternalSyntheticLambda0());
            DeviceConfig.Properties properties = DeviceConfig.getProperties("media", new String[0]);
            properties.getKeyset();
            properties
                    .getKeyset()
                    .forEach(new MediaSessionDeviceConfig$$ExternalSyntheticLambda1(properties));
            return;
        }
        if (i != 1000) {
            return;
        }
        ((MediaCommunicationManager)
                        this.mContext.getSystemService(MediaCommunicationManager.class))
                .registerSessionCallback(
                        new HandlerExecutor(this.mHandler), this.mSession2TokenCallback);
        if (Flags.enableNotifyingActivityManagerWithMediaSessionStatusChange()) {
            try {
                this.mNotificationListener.registerAsSystemService(
                        this.mContext,
                        new ComponentName(this.mContext, (Class<?>) NotificationListener.class),
                        -1);
            } catch (RemoteException unused) {
            }
        }
    }

    public final void onMediaButtonReceiverChanged(MediaSessionRecordImpl mediaSessionRecordImpl) {
        synchronized (this.mLock) {
            try {
                FullUserRecord fullUserRecordLocked =
                        getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
                MediaSessionRecordImpl mediaSessionRecordImpl2 =
                        fullUserRecordLocked.mPriorityStack.mMediaButtonSession;
                if (mediaSessionRecordImpl == mediaSessionRecordImpl2) {
                    fullUserRecordLocked.rememberMediaButtonReceiverLocked(mediaSessionRecordImpl2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSessionActiveStateChanged(
            MediaSessionRecordImpl mediaSessionRecordImpl, PlaybackState playbackState) {
        synchronized (this.mLock) {
            try {
                FullUserRecord fullUserRecordLocked =
                        getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
                if (fullUserRecordLocked == null) {
                    Log.w("MediaSessionService", "Unknown session updated. Ignoring.");
                    return;
                }
                if (mediaSessionRecordImpl.isSystemPriority()) {
                    Log.d(
                            "MediaSessionService",
                            "Global priority session updated - user id="
                                    + mediaSessionRecordImpl.getUserId()
                                    + " package="
                                    + mediaSessionRecordImpl.getPackageName()
                                    + " active="
                                    + mediaSessionRecordImpl.isActive());
                    fullUserRecordLocked.pushAddressedPlayerChangedLocked();
                } else {
                    if (!((ArrayList) fullUserRecordLocked.mPriorityStack.mSessions)
                            .contains(mediaSessionRecordImpl)) {
                        Log.w("MediaSessionService", "Unknown session updated. Ignoring.");
                        return;
                    }
                    MediaSessionStack mediaSessionStack = fullUserRecordLocked.mPriorityStack;
                    mediaSessionStack.getClass();
                    mediaSessionStack.mCachedActiveLists.remove(mediaSessionRecordImpl.getUserId());
                    mediaSessionStack.mCachedActiveLists.remove(-1);
                }
                boolean z = true;
                if (playbackState == null) {
                    z = mediaSessionRecordImpl.checkPlaybackActiveState(true);
                } else if (!playbackState.isActive() || !mediaSessionRecordImpl.isActive()) {
                    z = false;
                }
                Log.d(
                        "MediaSessionService",
                        "onSessionActiveStateChanged: record="
                                + mediaSessionRecordImpl
                                + " playbackState="
                                + playbackState);
                reportMediaInteractionEvent(mediaSessionRecordImpl, z);
                this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSessionMetadataChanged(
            MediaSessionRecordImpl mediaSessionRecordImpl, MediaMetadata mediaMetadata) {
        synchronized (this.mLock) {
            try {
                FullUserRecord fullUserRecordLocked =
                        getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
                if (fullUserRecordLocked != null
                        && ((ArrayList) fullUserRecordLocked.mPriorityStack.mSessions)
                                .contains(mediaSessionRecordImpl)) {
                    Log.d(
                            "MediaSessionService",
                            "onSessionMetadataChanged: record="
                                    + mediaSessionRecordImpl
                                    + " mediaMetadata="
                                    + mediaMetadata);
                    if (Rune.SEC_AUDIO_MEDIA_SESSION_AI) {
                        sendChangedMessages(mediaSessionRecordImpl, 1);
                    }
                    return;
                }
                Log.d("MediaSessionService", "Unknown session changed playback state. Ignoring.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSessionPlaybackStateChanged(
            MediaSessionRecordImpl mediaSessionRecordImpl, boolean z, PlaybackState playbackState) {
        synchronized (this.mLock) {
            try {
                FullUserRecord fullUserRecordLocked =
                        getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
                if (fullUserRecordLocked != null
                        && ((ArrayList) fullUserRecordLocked.mPriorityStack.mSessions)
                                .contains(mediaSessionRecordImpl)) {
                    fullUserRecordLocked.mPriorityStack.onPlaybackStateChanged(
                            mediaSessionRecordImpl, z);
                    boolean z2 = true;
                    if (playbackState == null) {
                        z2 = mediaSessionRecordImpl.checkPlaybackActiveState(true);
                    } else if (!playbackState.isActive() || !mediaSessionRecordImpl.isActive()) {
                        z2 = false;
                    }
                    Log.d(
                            "MediaSessionService",
                            "onSessionPlaybackStateChanged: record="
                                    + mediaSessionRecordImpl
                                    + " playbackState="
                                    + playbackState);
                    reportMediaInteractionEvent(mediaSessionRecordImpl, z2);
                    if (Rune.SEC_AUDIO_MEDIA_SESSION_AI) {
                        sendChangedMessages(mediaSessionRecordImpl, 0);
                    }
                    return;
                }
                Log.d("MediaSessionService", "Unknown session changed playback state. Ignoring.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onSessionPlaybackTypeChanged(MediaSessionRecord mediaSessionRecord) {
        synchronized (this.mLock) {
            try {
                FullUserRecord fullUserRecordLocked =
                        getFullUserRecordLocked(mediaSessionRecord.mUserId);
                if (fullUserRecordLocked != null
                        && ((ArrayList) fullUserRecordLocked.mPriorityStack.mSessions)
                                .contains(mediaSessionRecord)) {
                    if (Rune.SEC_AUDIO_MEDIA_SESSION_AI) {
                        sendChangedMessages(mediaSessionRecord, 2);
                    }
                    pushRemoteVolumeUpdateLocked(mediaSessionRecord.mUserId);
                    return;
                }
                Log.d("MediaSessionService", "Unknown session changed playback type. Ignoring.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("media_session", this.mSessionManagerImpl);
        Watchdog.getInstance().addMonitor(this);
        this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
        AudioPlayerStateMonitor audioPlayerStateMonitor =
                AudioPlayerStateMonitor.getInstance(this.mContext);
        this.mAudioPlayerStateMonitor = audioPlayerStateMonitor;
        audioPlayerStateMonitor.registerListener(
                new AudioPlayerStateMonitor
                        .OnAudioPlayerActiveStateChangedListener() { // from class:
                                                                     // com.android.server.media.MediaSessionService$$ExternalSyntheticLambda0
                    @Override // com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener
                    public final void onAudioPlayerActiveStateChanged(
                            AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
                        MediaSessionService mediaSessionService = MediaSessionService.this;
                        mediaSessionService.getClass();
                        Log.d(
                                "MediaSessionService",
                                "Audio playback is changed, config="
                                        + audioPlaybackConfiguration
                                        + ", removed="
                                        + z);
                        if (audioPlaybackConfiguration.getPlayerType() == 3) {
                            return;
                        }
                        synchronized (mediaSessionService.mLock) {
                            try {
                                MediaSessionService.FullUserRecord fullUserRecordLocked =
                                        mediaSessionService.getFullUserRecordLocked(
                                                UserHandle.getUserHandleForUid(
                                                                audioPlaybackConfiguration
                                                                        .getClientUid())
                                                        .getIdentifier());
                                if (fullUserRecordLocked != null) {
                                    if (mediaSessionService.isMultiSoundOn()
                                            && !z
                                            && !audioPlaybackConfiguration.isActive()) {
                                        int clientUid = audioPlaybackConfiguration.getClientUid();
                                        MediaSessionRecordImpl mediaButtonSessionLocked =
                                                mediaSessionService.mCurrentFullUserRecord
                                                        .getMediaButtonSessionLocked();
                                        if (mediaButtonSessionLocked != null
                                                && mediaButtonSessionLocked.getUid()
                                                        == clientUid) {}
                                    }
                                    fullUserRecordLocked.mPriorityStack
                                            .updateMediaButtonSessionIfNeeded();
                                }
                            } finally {
                            }
                        }
                    }
                },
                null);
        this.mHasFeatureLeanback =
                this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
        updateUser();
        instantiateCustomProvider(
                this.mContext
                        .getResources()
                        .getString(R.string.config_customMediaSessionPolicyProvider));
        instantiateCustomDispatcher(
                this.mContext.getResources().getString(R.string.config_customMediaKeyDispatcher));
        this.mRecordThread.start();
        this.mContext.registerReceiver(
                this.mNotificationListenerEnabledChangedReceiver,
                new IntentFilter("android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED"));
        this.mActivityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        this.mUsageStatsManagerInternal =
                (UsageStatsManagerInternal)
                        LocalServices.getService(UsageStatsManagerInternal.class);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        Log.d("MediaSessionService", "onStartUser: " + targetUser);
        updateUser();
    }

    @Override // com.android.server.SystemService
    public final void onUserStopped(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                userIdentifier, "onCleanupUser: ", "MediaSessionService");
        synchronized (this.mLock) {
            try {
                FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(userIdentifier);
                if (fullUserRecordLocked != null) {
                    if (fullUserRecordLocked.mFullUserId == userIdentifier) {
                        Iterator it =
                                ((ArrayList)
                                                fullUserRecordLocked.mPriorityStack.getPriorityList(
                                                        UserHandle.ALL.getIdentifier(), false))
                                        .iterator();
                        while (it.hasNext()) {
                            MediaSessionService.this.destroySessionLocked(
                                    (MediaSessionRecord) it.next());
                        }
                        this.mUserRecords.remove(userIdentifier);
                    } else {
                        Iterator it2 =
                                ((ArrayList)
                                                fullUserRecordLocked.mPriorityStack.getPriorityList(
                                                        userIdentifier, false))
                                        .iterator();
                        while (it2.hasNext()) {
                            MediaSessionService.this.destroySessionLocked(
                                    (MediaSessionRecord) it2.next());
                        }
                    }
                }
                updateUser();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(
            SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        Log.d("MediaSessionService", "onSwitchUser: " + targetUser2);
        updateUser();
    }

    public final void pushRemoteVolumeUpdateLocked(int i) {
        MediaSession.Token token;
        MediaSessionRecord mediaSessionRecord;
        FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i);
        if (fullUserRecordLocked == null) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i,
                    "pushRemoteVolumeUpdateLocked failed. No user with id=",
                    "MediaSessionService");
            return;
        }
        synchronized (this.mLock) {
            try {
                int beginBroadcast = this.mRemoteVolumeControllers.beginBroadcast();
                ArrayList arrayList =
                        (ArrayList) fullUserRecordLocked.mPriorityStack.getPriorityList(i, true);
                int size = arrayList.size();
                int i2 = 0;
                while (true) {
                    token = null;
                    if (i2 >= size) {
                        mediaSessionRecord = null;
                        break;
                    }
                    mediaSessionRecord = (MediaSessionRecord) arrayList.get(i2);
                    if (mediaSessionRecord.mVolumeType != 1) {
                        break;
                    } else {
                        i2++;
                    }
                }
                if (mediaSessionRecord instanceof MediaSession2Record) {
                    return;
                }
                if (mediaSessionRecord != null) {
                    token = mediaSessionRecord.mSessionToken;
                }
                for (int i3 = beginBroadcast - 1; i3 >= 0; i3--) {
                    try {
                        this.mRemoteVolumeControllers.getBroadcastItem(i3).onSessionChanged(token);
                    } catch (Exception e) {
                        Log.w("MediaSessionService", "Error sending default remote volume.", e);
                    }
                }
                this.mRemoteVolumeControllers.finishBroadcast();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void reportMediaInteractionEvent(
            MediaSessionRecordImpl mediaSessionRecordImpl, boolean z) {
        if (android.app.usage.Flags.userInteractionTypeApi()) {
            String packageName = mediaSessionRecordImpl.getPackageName();
            int uid = mediaSessionRecordImpl.getUid();
            if (z) {
                if (!this.mUserEngagedSessionsForUsageLogging.contains(uid)) {
                    this.mUserEngagedSessionsForUsageLogging.put(uid, new HashSet());
                    int userId = mediaSessionRecordImpl.getUserId();
                    PersistableBundle persistableBundle = new PersistableBundle();
                    persistableBundle.putString(
                            "android.app.usage.extra.EVENT_CATEGORY", "android.media");
                    persistableBundle.putString("android.app.usage.extra.EVENT_ACTION", "start");
                    this.mUsageStatsManagerInternal.reportUserInteractionEvent(
                            packageName, userId, persistableBundle);
                }
                ((Set) this.mUserEngagedSessionsForUsageLogging.get(uid))
                        .add(mediaSessionRecordImpl);
                return;
            }
            if (this.mUserEngagedSessionsForUsageLogging.contains(uid)) {
                ((Set) this.mUserEngagedSessionsForUsageLogging.get(uid))
                        .remove(mediaSessionRecordImpl);
                if (((Set) this.mUserEngagedSessionsForUsageLogging.get(uid)).isEmpty()) {
                    int userId2 = mediaSessionRecordImpl.getUserId();
                    PersistableBundle persistableBundle2 = new PersistableBundle();
                    persistableBundle2.putString(
                            "android.app.usage.extra.EVENT_CATEGORY", "android.media");
                    persistableBundle2.putString("android.app.usage.extra.EVENT_ACTION", "stop");
                    this.mUsageStatsManagerInternal.reportUserInteractionEvent(
                            packageName, userId2, persistableBundle2);
                    this.mUserEngagedSessionsForUsageLogging.remove(uid);
                }
            }
        }
    }

    public final void sendChangedMessages(MediaSessionRecordImpl mediaSessionRecordImpl, int i) {
        if (mediaSessionRecordImpl instanceof MediaSessionRecord) {
            MessageHandler messageHandler = this.mHandler;
            messageHandler.removeMessages(3);
            messageHandler
                    .obtainMessage(3, i, 0, (MediaSessionRecord) mediaSessionRecordImpl)
                    .sendToTarget();
        }
    }

    public final void setGlobalPrioritySession(MediaSessionRecord mediaSessionRecord) {
        synchronized (this.mLock) {
            try {
                FullUserRecord fullUserRecordLocked =
                        getFullUserRecordLocked(mediaSessionRecord.mUserId);
                if (this.mGlobalPrioritySession != mediaSessionRecord) {
                    Log.d(
                            "MediaSessionService",
                            "Global priority session is changed from "
                                    + this.mGlobalPrioritySession
                                    + " to "
                                    + mediaSessionRecord);
                    this.mGlobalPrioritySession = mediaSessionRecord;
                    if (fullUserRecordLocked != null
                            && ((ArrayList) fullUserRecordLocked.mPriorityStack.mSessions)
                                    .contains(mediaSessionRecord)) {
                        fullUserRecordLocked.mPriorityStack.removeSession(mediaSessionRecord);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setMultiSoundFlag(MediaSessionRecord mediaSessionRecord) {
        if (this.mIsMultiSoundOn) {
            SemAudioServiceInternal semAudioServiceInternal = this.mAudioServiceInternal;
            int i = mediaSessionRecord.mOwnerUid;
            AudioService audioService = (AudioService) semAudioServiceInternal.mAudioService.get();
            int appDevice =
                    audioService == null ? 0 : audioService.mMultiSoundManager.getAppDevice(i);
            int i2 = (int) mediaSessionRecord.mFlags;
            try {
                mediaSessionRecord.mSession.setFlags(
                        ((appDevice == 0 && this.mDevice == 128) || appDevice == 128)
                                ? 536870912 | i2
                                : (-536870913) & i2);
            } catch (RemoteException e) {
                Log.e("MediaSessionService", "Error setFlags", e);
            }
        }
    }

    public final void startFgsDelegateLocked(MediaSessionRecordImpl mediaSessionRecordImpl) {
        ForegroundServiceDelegationOptions foregroundServiceDelegationOptions =
                mediaSessionRecordImpl.getForegroundServiceDelegationOptions();
        if (foregroundServiceDelegationOptions != null
                && ((HashSet) this.mFgsAllowedMediaSessionRecords).add(mediaSessionRecordImpl)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.i(
                        "MediaSessionService",
                        TextUtils.formatSimple(
                                "startFgsDelegate: pkg=%s uid=%d",
                                new Object[] {
                                    foregroundServiceDelegationOptions.mClientPackageName,
                                    Integer.valueOf(foregroundServiceDelegationOptions.mClientUid)
                                }));
                this.mActivityManagerInternal.startForegroundServiceDelegate(
                        foregroundServiceDelegationOptions, (ServiceConnection) null);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void stopFgsDelegateLocked(MediaSessionRecordImpl mediaSessionRecordImpl) {
        ForegroundServiceDelegationOptions foregroundServiceDelegationOptions =
                mediaSessionRecordImpl.getForegroundServiceDelegationOptions();
        if (foregroundServiceDelegationOptions != null
                && ((HashSet) this.mFgsAllowedMediaSessionRecords).remove(mediaSessionRecordImpl)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Log.i(
                        "MediaSessionService",
                        TextUtils.formatSimple(
                                "stopFgsDelegate: pkg=%s uid=%d",
                                new Object[] {
                                    foregroundServiceDelegationOptions.mClientPackageName,
                                    Integer.valueOf(foregroundServiceDelegationOptions.mClientUid)
                                }));
                this.mActivityManagerInternal.stopForegroundServiceDelegate(
                        foregroundServiceDelegationOptions);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void stopFgsIfNoSessionIsLinkedToNotification(
            MediaSessionRecordImpl mediaSessionRecordImpl) {
        Log.d(
                "MediaSessionService",
                "stopFgsIfNoSessionIsLinkedToNotification: record=" + mediaSessionRecordImpl);
        if (Flags.enableNotifyingActivityManagerWithMediaSessionStatusChange()) {
            synchronized (this.mLock) {
                try {
                    int uid = mediaSessionRecordImpl.getUid();
                    if (mediaSessionRecordImpl.getForegroundServiceDelegationOptions() == null) {
                        return;
                    }
                    for (MediaSessionRecordImpl mediaSessionRecordImpl2 :
                            (Set)
                                    ((HashMap) this.mUserEngagedSessionsForFgs)
                                            .getOrDefault(Integer.valueOf(uid), Set.of())) {
                        Iterator it =
                                ((Set)
                                                ((HashMap) this.mMediaNotifications)
                                                        .getOrDefault(
                                                                Integer.valueOf(uid), Set.of()))
                                        .iterator();
                        while (it.hasNext()) {
                            if (mediaSessionRecordImpl2.isLinkedToNotification(
                                    (Notification) it.next())) {
                                return;
                            }
                        }
                    }
                    stopFgsDelegateLocked(mediaSessionRecordImpl);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005a A[Catch: all -> 0x0022, TryCatch #0 {all -> 0x0022, blocks: (B:3:0x0007, B:5:0x000e, B:7:0x0017, B:11:0x0025, B:15:0x0047, B:18:0x004e, B:20:0x005a, B:22:0x0063), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0063 A[Catch: all -> 0x0022, TRY_LEAVE, TryCatch #0 {all -> 0x0022, blocks: (B:3:0x0007, B:5:0x000e, B:7:0x0017, B:11:0x0025, B:15:0x0047, B:18:0x004e, B:20:0x005a, B:22:0x0063), top: B:2:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void tempAllowlistTargetPkgIfPossible(
            int r10,
            int r11,
            java.lang.String r12,
            java.lang.String r13,
            java.lang.String r14,
            int r15) {
        /*
            r9 = this;
            java.lang.String r0 = "tempAllowlistTargetPkgIfPossible callingPackage:"
            long r1 = android.os.Binder.clearCallingIdentity()
            android.content.Context r3 = r9.mContext     // Catch: java.lang.Throwable -> L22
            com.android.server.media.MediaServerUtils.enforcePackageName(r3, r13, r15)     // Catch: java.lang.Throwable -> L22
            if (r10 == r15) goto L83
            android.app.ActivityManagerInternal r3 = r9.mActivityManagerInternal     // Catch: java.lang.Throwable -> L22
            boolean r3 = r3.canAllowWhileInUsePermissionInFgs(r11, r15, r13)     // Catch: java.lang.Throwable -> L22
            r4 = 0
            if (r3 != 0) goto L24
            android.app.ActivityManagerInternal r5 = r9.mActivityManagerInternal     // Catch: java.lang.Throwable -> L22
            boolean r11 = r5.canStartForegroundService(r11, r15, r13)     // Catch: java.lang.Throwable -> L22
            if (r11 == 0) goto L20
            goto L24
        L20:
            r11 = r4
            goto L25
        L22:
            r9 = move-exception
            goto L87
        L24:
            r11 = 1
        L25:
            java.lang.String r15 = "MediaSessionService"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L22
            r5.<init>(r0)     // Catch: java.lang.Throwable -> L22
            r5.append(r13)     // Catch: java.lang.Throwable -> L22
            java.lang.String r13 = " targetPackage:"
            r5.append(r13)     // Catch: java.lang.Throwable -> L22
            r5.append(r12)     // Catch: java.lang.Throwable -> L22
            java.lang.String r13 = " reason:"
            r5.append(r13)     // Catch: java.lang.Throwable -> L22
            r5.append(r14)     // Catch: java.lang.Throwable -> L22
            java.lang.String r13 = ""
            if (r3 == 0) goto L46
            java.lang.String r0 = " [WIU]"
            goto L47
        L46:
            r0 = r13
        L47:
            r5.append(r0)     // Catch: java.lang.Throwable -> L22
            if (r11 == 0) goto L4e
            java.lang.String r13 = " [FGS]"
        L4e:
            r5.append(r13)     // Catch: java.lang.Throwable -> L22
            java.lang.String r13 = r5.toString()     // Catch: java.lang.Throwable -> L22
            android.util.Log.i(r15, r13)     // Catch: java.lang.Throwable -> L22
            if (r3 == 0) goto L61
            android.app.ActivityManagerInternal r13 = r9.mActivityManagerInternal     // Catch: java.lang.Throwable -> L22
            long r5 = com.android.server.media.MediaSessionDeviceConfig.sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs     // Catch: java.lang.Throwable -> L22
            r13.tempAllowWhileInUsePermissionInFgs(r10, r5)     // Catch: java.lang.Throwable -> L22
        L61:
            if (r11 == 0) goto L83
            android.content.Context r9 = r9.mContext     // Catch: java.lang.Throwable -> L22
            int r10 = android.os.UserHandle.getUserId(r10)     // Catch: java.lang.Throwable -> L22
            android.os.UserHandle r10 = android.os.UserHandle.of(r10)     // Catch: java.lang.Throwable -> L22
            android.content.Context r9 = r9.createContextAsUser(r10, r4)     // Catch: java.lang.Throwable -> L22
            java.lang.Class<android.os.PowerExemptionManager> r10 = android.os.PowerExemptionManager.class
            java.lang.Object r9 = r9.getSystemService(r10)     // Catch: java.lang.Throwable -> L22
            r3 = r9
            android.os.PowerExemptionManager r3 = (android.os.PowerExemptionManager) r3     // Catch: java.lang.Throwable -> L22
            long r7 = com.android.server.media.MediaSessionDeviceConfig.sMediaSessionCallbackFgsAllowlistDurationMs     // Catch: java.lang.Throwable -> L22
            r5 = 317(0x13d, float:4.44E-43)
            r4 = r12
            r6 = r14
            r3.addToTemporaryAllowList(r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L22
        L83:
            android.os.Binder.restoreCallingIdentity(r1)
            return
        L87:
            android.os.Binder.restoreCallingIdentity(r1)
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.media.MediaSessionService.tempAllowlistTargetPkgIfPossible(int,"
                    + " int, java.lang.String, java.lang.String, java.lang.String, int):void");
    }

    public final void updateUser() {
        synchronized (this.mLock) {
            try {
                UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                this.mFullUserIds.clear();
                List<UserHandle> userHandles = userManager.getUserHandles(false);
                if (userHandles != null) {
                    for (UserHandle userHandle : userHandles) {
                        UserHandle profileParent = userManager.getProfileParent(userHandle);
                        if (profileParent != null) {
                            this.mFullUserIds.put(
                                    userHandle.getIdentifier(), profileParent.getIdentifier());
                        } else {
                            this.mFullUserIds.put(
                                    userHandle.getIdentifier(), userHandle.getIdentifier());
                            if (this.mUserRecords.get(userHandle.getIdentifier()) == null) {
                                this.mUserRecords.put(
                                        userHandle.getIdentifier(),
                                        new FullUserRecord(this, userHandle.getIdentifier()));
                            }
                        }
                    }
                }
                int currentUser = ActivityManager.getCurrentUser();
                FullUserRecord fullUserRecord = (FullUserRecord) this.mUserRecords.get(currentUser);
                this.mCurrentFullUserRecord = fullUserRecord;
                if (fullUserRecord == null) {
                    Log.w(
                            "MediaSessionService",
                            "Cannot find FullUserInfo for the current user " + currentUser);
                    FullUserRecord fullUserRecord2 = new FullUserRecord(this, currentUser);
                    this.mCurrentFullUserRecord = fullUserRecord2;
                    this.mUserRecords.put(currentUser, fullUserRecord2);
                }
                this.mFullUserIds.put(currentUser, currentUser);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
