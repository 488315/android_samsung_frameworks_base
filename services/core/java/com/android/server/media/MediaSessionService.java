package com.android.server.media;

import android.R;
import android.app.ActivityManager;
import android.app.BroadcastOptions;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.p000pm.PackageManagerInternal;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioSystem;
import android.media.IRemoteSessionCallback;
import android.media.MediaCommunicationManager;
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
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerExemptionManager;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.os.UserManager;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.LocalManagerRegistry;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.Watchdog;
import com.android.server.am.ActivityManagerLocal;
import com.android.server.am.FreecessController;
import com.android.server.audio.CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.utils.EventLogger;
import com.samsung.android.knox.custom.CustomDeviceManagerProxy;
import com.samsung.android.server.audio.CoverHelper;
import com.samsung.android.server.audio.DesktopModeHelper;
import com.samsung.android.server.audio.SemAudioServiceInternal;
import com.samsung.android.server.audio.utils.AudioUtils;
import com.samsung.android.server.audio.utils.PlatformTypeUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class MediaSessionService extends SystemService implements Watchdog.Monitor {
  public static final boolean DEBUG = Log.isLoggable("MediaSessionService", 3);
  public static final int LONG_PRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout() + 50;
  public static final int MULTI_TAP_TIMEOUT = ViewConfiguration.getMultiPressTimeout();
  public ActivityManagerLocal mActivityManagerLocal;
  public int mAppCastingUid;
  public AudioManager mAudioManager;
  public AudioPlayerStateMonitor mAudioPlayerStateMonitor;
  public SemAudioServiceInternal mAudioServiceInternal;
  public MediaCommunicationManager mCommunicationManager;
  public final Context mContext;
  public CoverHelper mCoverHelper;
  public FullUserRecord mCurrentFullUserRecord;
  public MediaSessionPolicyProvider mCustomMediaSessionPolicyProvider;
  public DesktopModeHelper mDesktopModeHelper;
  public int mDevice;
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
  public final BroadcastReceiver mNotificationListenerEnabledChangedReceiver;
  public final NotificationManager mNotificationManager;
  public PowerManagerInternal mPowerManagerInternal;
  public final HandlerThread mRecordThread;
  public final RemoteCallbackList mRemoteVolumeControllers;
  public final MediaCommunicationManager.SessionCallback mSession2TokenCallback;
  public final List mSession2TokensListenerRecords;
  public final SessionManagerImpl mSessionManagerImpl;
  public final ArrayList mSessionsListeners;
  public int mSystemServerPid;
  public final SparseArray mUserRecords;
  public ComponentName mVolumeKeyLongPressReceiver;

  /* renamed from: -$$Nest$fgetmCustomMediaKeyDispatcher, reason: not valid java name */
  public static /* bridge */ /* synthetic */ MediaKeyDispatcher
      m8558$$Nest$fgetmCustomMediaKeyDispatcher(MediaSessionService mediaSessionService) {
    mediaSessionService.getClass();
    return null;
  }

  public MediaSessionService(Context context) {
    super(context);
    this.mHandler = new MessageHandler();
    this.mLock = new Object();
    this.mRecordThread = new HandlerThread("SessionRecordThread");
    this.mFullUserIds = new SparseIntArray();
    this.mUserRecords = new SparseArray();
    this.mSessionsListeners = new ArrayList();
    this.mSession2TokensListenerRecords = new ArrayList();
    this.mRemoteVolumeControllers = new RemoteCallbackList();
    this.mSession2TokenCallback =
        new MediaCommunicationManager
            .SessionCallback() { // from class: com.android.server.media.MediaSessionService.1
          public void onSession2TokenCreated(Session2Token session2Token) {
            if (MediaSessionService.DEBUG) {
              Log.d("MediaSessionService", "Session2 is created " + session2Token);
            }
            MediaSessionService mediaSessionService = MediaSessionService.this;
            MediaSession2Record mediaSession2Record =
                new MediaSession2Record(
                    session2Token,
                    mediaSessionService,
                    mediaSessionService.mRecordThread.getLooper(),
                    0);
            synchronized (MediaSessionService.this.mLock) {
              FullUserRecord fullUserRecordLocked =
                  MediaSessionService.this.getFullUserRecordLocked(mediaSession2Record.getUserId());
              if (fullUserRecordLocked != null) {
                fullUserRecordLocked.mPriorityStack.addSession(mediaSession2Record);
              }
            }
          }
        };
    this.mNotificationListenerEnabledChangedReceiver =
        new BroadcastReceiver() { // from class: com.android.server.media.MediaSessionService.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            MediaSessionService.this.updateActiveSessionListeners();
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
    setupCustomRoutine();
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("media_session", this.mSessionManagerImpl);
    Watchdog.getInstance().addMonitor(this);
    this.mKeyguardManager = (KeyguardManager) this.mContext.getSystemService("keyguard");
    AudioPlayerStateMonitor audioPlayerStateMonitor =
        AudioPlayerStateMonitor.getInstance(this.mContext);
    this.mAudioPlayerStateMonitor = audioPlayerStateMonitor;
    audioPlayerStateMonitor.registerListener(
        new AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener() { // from class:
          // com.android.server.media.MediaSessionService$$ExternalSyntheticLambda0
          @Override // com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener
          public final void onAudioPlayerActiveStateChanged(
              AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
            MediaSessionService.this.lambda$onStart$0(audioPlaybackConfiguration, z);
          }
        },
        null);
    this.mHasFeatureLeanback =
        this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
    updateUser();
    instantiateCustomProvider(
        this.mContext.getResources().getString(R.string.config_customMediaSessionPolicyProvider));
    instantiateCustomDispatcher(
        this.mContext.getResources().getString(R.string.config_customMediaKeyDispatcher));
    this.mRecordThread.start();
    this.mContext.registerReceiver(
        this.mNotificationListenerEnabledChangedReceiver,
        new IntentFilter("android.app.action.NOTIFICATION_LISTENER_ENABLED_CHANGED"));
    this.mActivityManagerLocal =
        (ActivityManagerLocal) LocalManagerRegistry.getManager(ActivityManagerLocal.class);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onStart$0(
      AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
    if (DEBUG) {
      Log.d(
          "MediaSessionService",
          "Audio playback is changed, config=" + audioPlaybackConfiguration + ", removed=" + z);
    }
    if (audioPlaybackConfiguration.getPlayerType() == 3) {
      return;
    }
    synchronized (this.mLock) {
      FullUserRecord fullUserRecordLocked =
          getFullUserRecordLocked(
              UserHandle.getUserHandleForUid(audioPlaybackConfiguration.getClientUid())
                  .getIdentifier());
      if (fullUserRecordLocked != null
          && (!isMultiSoundOn()
              || z
              || audioPlaybackConfiguration.isActive()
              || !isMediaButtonSessionUid(audioPlaybackConfiguration.getClientUid()))) {
        fullUserRecordLocked.mPriorityStack.updateMediaButtonSessionIfNeeded();
      }
    }
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    super.onBootPhase(i);
    if (i == 550) {
      MediaSessionDeviceConfig.initialize(this.mContext);
    } else {
      if (i != 1000) {
        return;
      }
      MediaCommunicationManager mediaCommunicationManager =
          (MediaCommunicationManager)
              this.mContext.getSystemService(MediaCommunicationManager.class);
      this.mCommunicationManager = mediaCommunicationManager;
      mediaCommunicationManager.registerSessionCallback(
          new HandlerExecutor(this.mHandler), this.mSession2TokenCallback);
    }
  }

  public final boolean isGlobalPriorityActiveLocked() {
    MediaSessionRecord mediaSessionRecord = this.mGlobalPrioritySession;
    return mediaSessionRecord != null && mediaSessionRecord.isActive();
  }

  public void onSessionActiveStateChanged(MediaSessionRecordImpl mediaSessionRecordImpl) {
    synchronized (this.mLock) {
      FullUserRecord fullUserRecordLocked =
          getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
      if (fullUserRecordLocked == null) {
        Log.w("MediaSessionService", "Unknown session updated. Ignoring.");
        return;
      }
      if (mediaSessionRecordImpl.isSystemPriority()) {
        Log.d(
            "MediaSessionService",
            "Global priority session is updated, active=" + mediaSessionRecordImpl.isActive());
        fullUserRecordLocked.pushAddressedPlayerChangedLocked();
      } else {
        if (!fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecordImpl)) {
          Log.w("MediaSessionService", "Unknown session updated. Ignoring.");
          return;
        }
        fullUserRecordLocked.mPriorityStack.onSessionActiveStateChanged(mediaSessionRecordImpl);
      }
      this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
    }
  }

  public void setGlobalPrioritySession(MediaSessionRecord mediaSessionRecord) {
    synchronized (this.mLock) {
      FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecord.getUserId());
      if (this.mGlobalPrioritySession != mediaSessionRecord) {
        Log.d(
            "MediaSessionService",
            "Global priority session is changed from "
                + this.mGlobalPrioritySession
                + " to "
                + mediaSessionRecord);
        this.mGlobalPrioritySession = mediaSessionRecord;
        if (fullUserRecordLocked != null
            && fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecord)) {
          fullUserRecordLocked.mPriorityStack.removeSession(mediaSessionRecord);
        }
      }
    }
  }

  public final List getActiveSessionsLocked(int i) {
    ArrayList arrayList = new ArrayList();
    if (i == UserHandle.ALL.getIdentifier()) {
      int size = this.mUserRecords.size();
      for (int i2 = 0; i2 < size; i2++) {
        arrayList.addAll(
            ((FullUserRecord) this.mUserRecords.valueAt(i2)).mPriorityStack.getActiveSessions(i));
      }
    } else {
      FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i);
      if (fullUserRecordLocked == null) {
        Log.w("MediaSessionService", "getSessions failed. Unknown user " + i);
        return arrayList;
      }
      arrayList.addAll(fullUserRecordLocked.mPriorityStack.getActiveSessions(i));
    }
    if (isGlobalPriorityActiveLocked()
        && (i == UserHandle.ALL.getIdentifier() || i == this.mGlobalPrioritySession.getUserId())) {
      arrayList.add(0, this.mGlobalPrioritySession);
    }
    return arrayList;
  }

  public List getSession2TokensLocked(int i) {
    ArrayList arrayList = new ArrayList();
    if (i == UserHandle.ALL.getIdentifier()) {
      int size = this.mUserRecords.size();
      for (int i2 = 0; i2 < size; i2++) {
        arrayList.addAll(
            ((FullUserRecord) this.mUserRecords.valueAt(i2)).mPriorityStack.getSession2Tokens(i));
      }
    } else {
      arrayList.addAll(getFullUserRecordLocked(i).mPriorityStack.getSession2Tokens(i));
    }
    return arrayList;
  }

  public void notifyRemoteVolumeChanged(int i, MediaSessionRecord mediaSessionRecord) {
    if (mediaSessionRecord.isActive()) {
      synchronized (this.mLock) {
        int beginBroadcast = this.mRemoteVolumeControllers.beginBroadcast();
        MediaSession.Token sessionToken = mediaSessionRecord.getSessionToken();
        for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
          try {
            this.mRemoteVolumeControllers.getBroadcastItem(i2).onVolumeChanged(sessionToken, i);
          } catch (Exception e) {
            Log.w("MediaSessionService", "Error sending volume change.", e);
          }
        }
        this.mRemoteVolumeControllers.finishBroadcast();
      }
    }
  }

  public void onSessionPlaybackStateChanged(
      MediaSessionRecordImpl mediaSessionRecordImpl, boolean z) {
    synchronized (this.mLock) {
      FullUserRecord fullUserRecordLocked =
          getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
      if (fullUserRecordLocked != null
          && fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecordImpl)) {
        fullUserRecordLocked.mPriorityStack.onPlaybackStateChanged(mediaSessionRecordImpl, z);
        return;
      }
      Log.d("MediaSessionService", "Unknown session changed playback state. Ignoring.");
    }
  }

  public void onSessionPlaybackTypeChanged(MediaSessionRecord mediaSessionRecord) {
    synchronized (this.mLock) {
      FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(mediaSessionRecord.getUserId());
      if (fullUserRecordLocked != null
          && fullUserRecordLocked.mPriorityStack.contains(mediaSessionRecord)) {
        pushRemoteVolumeUpdateLocked(mediaSessionRecord.getUserId());
        return;
      }
      Log.d("MediaSessionService", "Unknown session changed playback type. Ignoring.");
    }
  }

  @Override // com.android.server.SystemService
  public void onUserStarting(SystemService.TargetUser targetUser) {
    if (DEBUG) {
      Log.d("MediaSessionService", "onStartUser: " + targetUser);
    }
    updateUser();
  }

  @Override // com.android.server.SystemService
  public void onUserSwitching(
      SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
    if (DEBUG) {
      Log.d("MediaSessionService", "onSwitchUser: " + targetUser2);
    }
    updateUser();
  }

  @Override // com.android.server.SystemService
  public void onUserStopped(SystemService.TargetUser targetUser) {
    int userIdentifier = targetUser.getUserIdentifier();
    if (DEBUG) {
      Log.d("MediaSessionService", "onCleanupUser: " + userIdentifier);
    }
    synchronized (this.mLock) {
      FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(userIdentifier);
      if (fullUserRecordLocked != null) {
        if (fullUserRecordLocked.mFullUserId == userIdentifier) {
          fullUserRecordLocked.destroySessionsForUserLocked(UserHandle.ALL.getIdentifier());
          this.mUserRecords.remove(userIdentifier);
        } else {
          fullUserRecordLocked.destroySessionsForUserLocked(userIdentifier);
        }
      }
      updateUser();
    }
  }

  @Override // com.android.server.Watchdog.Monitor
  public void monitor() {
    synchronized (this.mLock) {
    }
  }

  public void enforcePhoneStatePermission(int i, int i2) {
    if (this.mContext.checkPermission("android.permission.MODIFY_PHONE_STATE", i, i2) != 0) {
      throw new SecurityException("Must hold the MODIFY_PHONE_STATE permission.");
    }
  }

  public void onSessionDied(MediaSessionRecordImpl mediaSessionRecordImpl) {
    synchronized (this.mLock) {
      destroySessionLocked(mediaSessionRecordImpl);
    }
  }

  public final void updateUser() {
    synchronized (this.mLock) {
      UserManager userManager = (UserManager) this.mContext.getSystemService("user");
      this.mFullUserIds.clear();
      List<UserHandle> userHandles = userManager.getUserHandles(false);
      if (userHandles != null) {
        for (UserHandle userHandle : userHandles) {
          UserHandle profileParent = userManager.getProfileParent(userHandle);
          if (profileParent != null) {
            this.mFullUserIds.put(userHandle.getIdentifier(), profileParent.getIdentifier());
          } else {
            this.mFullUserIds.put(userHandle.getIdentifier(), userHandle.getIdentifier());
            if (this.mUserRecords.get(userHandle.getIdentifier()) == null) {
              this.mUserRecords.put(
                  userHandle.getIdentifier(), new FullUserRecord(userHandle.getIdentifier()));
            }
          }
        }
      }
      int currentUser = ActivityManager.getCurrentUser();
      FullUserRecord fullUserRecord = (FullUserRecord) this.mUserRecords.get(currentUser);
      this.mCurrentFullUserRecord = fullUserRecord;
      if (fullUserRecord == null) {
        Log.w(
            "MediaSessionService", "Cannot find FullUserInfo for the current user " + currentUser);
        FullUserRecord fullUserRecord2 = new FullUserRecord(currentUser);
        this.mCurrentFullUserRecord = fullUserRecord2;
        this.mUserRecords.put(currentUser, fullUserRecord2);
      }
      this.mFullUserIds.put(currentUser, currentUser);
    }
  }

  public final void updateActiveSessionListeners() {
    synchronized (this.mLock) {
      for (int size = this.mSessionsListeners.size() - 1; size >= 0; size--) {
        SessionsListenerRecord sessionsListenerRecord =
            (SessionsListenerRecord) this.mSessionsListeners.get(size);
        try {
          ComponentName componentName = sessionsListenerRecord.componentName;
          enforceMediaPermissions(
              componentName == null ? null : componentName.getPackageName(),
              sessionsListenerRecord.pid,
              sessionsListenerRecord.uid,
              sessionsListenerRecord.userId);
        } catch (SecurityException unused) {
          Log.i(
              "MediaSessionService",
              "ActiveSessionsListener "
                  + sessionsListenerRecord.componentName
                  + " is no longer authorized. Disconnecting.");
          this.mSessionsListeners.remove(size);
          try {
            sessionsListenerRecord.listener.onActiveSessionsChanged(new ArrayList());
          } catch (Exception unused2) {
          }
        }
      }
    }
  }

  public final void destroySessionLocked(MediaSessionRecordImpl mediaSessionRecordImpl) {
    if (DEBUG) {
      Log.d("MediaSessionService", "Destroying " + mediaSessionRecordImpl);
    }
    if (mediaSessionRecordImpl.isClosed()) {
      Log.w("MediaSessionService", "Destroying already destroyed session. Ignoring.");
      return;
    }
    FullUserRecord fullUserRecordLocked =
        getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
    if (fullUserRecordLocked != null && (mediaSessionRecordImpl instanceof MediaSessionRecord)) {
      int uid = mediaSessionRecordImpl.getUid();
      int i = fullUserRecordLocked.mUidToSessionCount.get(uid, 0);
      if (i <= 0) {
        Log.w(
            "MediaSessionService",
            "destroySessionLocked: sessionCount should be positive. sessionCount=" + i);
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
    this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
  }

  /* JADX WARN: Removed duplicated region for block: B:14:0x0042  */
  /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
  /* JADX WARN: Removed duplicated region for block: B:20:0x0059 A[Catch: all -> 0x008a, TryCatch #0 {all -> 0x008a, blocks: (B:3:0x0004, B:5:0x0009, B:7:0x0012, B:11:0x001e, B:15:0x0046, B:18:0x004d, B:20:0x0059, B:22:0x0064), top: B:2:0x0004 }] */
  /* JADX WARN: Removed duplicated region for block: B:22:0x0064 A[Catch: all -> 0x008a, TRY_LEAVE, TryCatch #0 {all -> 0x008a, blocks: (B:3:0x0004, B:5:0x0009, B:7:0x0012, B:11:0x001e, B:15:0x0046, B:18:0x004d, B:20:0x0059, B:22:0x0064), top: B:2:0x0004 }] */
  /* JADX WARN: Removed duplicated region for block: B:24:0x0045  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void tempAllowlistTargetPkgIfPossible(
      int i, String str, int i2, int i3, String str2, String str3) {
    boolean z;
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      MediaServerUtils.enforcePackageName(str2, i3);
      if (i != i3) {
        boolean canAllowWhileInUsePermissionInFgs =
            this.mActivityManagerLocal.canAllowWhileInUsePermissionInFgs(i2, i3, str2);
        if (!canAllowWhileInUsePermissionInFgs
            && !this.mActivityManagerLocal.canStartForegroundService(i2, i3, str2)) {
          z = false;
          StringBuilder sb = new StringBuilder();
          sb.append("tempAllowlistTargetPkgIfPossible callingPackage:");
          sb.append(str2);
          sb.append(" targetPackage:");
          sb.append(str);
          sb.append(" reason:");
          sb.append(str3);
          sb.append(!canAllowWhileInUsePermissionInFgs ? " [WIU]" : "");
          sb.append(z ? " [FGS]" : "");
          Log.i("MediaSessionService", sb.toString());
          if (canAllowWhileInUsePermissionInFgs) {
            this.mActivityManagerLocal.tempAllowWhileInUsePermissionInFgs(
                i,
                MediaSessionDeviceConfig.getMediaSessionCallbackFgsWhileInUseTempAllowDurationMs());
          }
          if (z) {
            ((PowerExemptionManager)
                    this.mContext
                        .createContextAsUser(UserHandle.of(UserHandle.getUserId(i)), 0)
                        .getSystemService(PowerExemptionManager.class))
                .addToTemporaryAllowList(
                    str,
                    FrameworkStatsLog.f90x54490b7,
                    str3,
                    MediaSessionDeviceConfig.getMediaSessionCallbackFgsAllowlistDurationMs());
          }
        }
        z = true;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("tempAllowlistTargetPkgIfPossible callingPackage:");
        sb2.append(str2);
        sb2.append(" targetPackage:");
        sb2.append(str);
        sb2.append(" reason:");
        sb2.append(str3);
        sb2.append(!canAllowWhileInUsePermissionInFgs ? " [WIU]" : "");
        sb2.append(z ? " [FGS]" : "");
        Log.i("MediaSessionService", sb2.toString());
        if (canAllowWhileInUsePermissionInFgs) {}
        if (z) {}
      }
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public final void enforceMediaPermissions(String str, int i, int i2, int i3) {
    if (hasStatusBarServicePermission(i, i2) || hasMediaControlPermission(i, i2)) {
      return;
    }
    if (str == null
        || !hasEnabledNotificationListener(str, UserHandle.getUserHandleForUid(i2), i3)) {
      throw new SecurityException("Missing permission to control media.");
    }
  }

  public final boolean hasStatusBarServicePermission(int i, int i2) {
    return this.mContext.checkPermission("android.permission.STATUS_BAR_SERVICE", i, i2) == 0;
  }

  public final void enforceStatusBarServicePermission(String str, int i, int i2) {
    if (hasStatusBarServicePermission(i, i2)) {
      return;
    }
    throw new SecurityException("Only System UI and Settings may " + str);
  }

  public final boolean hasMediaControlPermission(int i, int i2) {
    if (i2 == 1000
        || this.mContext.checkPermission("android.permission.MEDIA_CONTENT_CONTROL", i, i2) == 0) {
      return true;
    }
    if (!DEBUG) {
      return false;
    }
    Log.d("MediaSessionService", "uid(" + i2 + ") hasn't granted MEDIA_CONTENT_CONTROL");
    return false;
  }

  public final boolean hasEnabledNotificationListener(String str, UserHandle userHandle, int i) {
    if (userHandle.getIdentifier() != i) {
      return false;
    }
    if (DEBUG) {
      Log.d(
          "MediaSessionService",
          "Checking whether the package " + str + " has an enabled notification listener.");
    }
    return this.mNotificationManager.hasEnabledNotificationListener(str, userHandle);
  }

  public final MediaSessionRecord createSessionInternal(
      int i,
      int i2,
      int i3,
      String str,
      ISessionCallback iSessionCallback,
      String str2,
      Bundle bundle) {
    MediaSessionRecord mediaSessionRecord;
    synchronized (this.mLock) {
      MediaSessionPolicyProvider mediaSessionPolicyProvider =
          this.mCustomMediaSessionPolicyProvider;
      int sessionPoliciesForApplication =
          mediaSessionPolicyProvider != null
              ? mediaSessionPolicyProvider.getSessionPoliciesForApplication(i2, str)
              : 0;
      FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i3);
      if (fullUserRecordLocked == null) {
        Log.w("MediaSessionService", "Request from invalid user: " + i3 + ", pkg=" + str);
        throw new RuntimeException("Session request from invalid user.");
      }
      int i4 = fullUserRecordLocked.mUidToSessionCount.get(i2, 0);
      if (i4 >= 100 && !hasMediaControlPermission(i, i2)) {
        throw new RuntimeException("Created too many sessions. count=" + i4 + ")");
      }
      try {
        MediaSessionRecord mediaSessionRecord2 =
            new MediaSessionRecord(
                i,
                i2,
                i3,
                str,
                iSessionCallback,
                str2,
                bundle,
                this,
                this.mRecordThread.getLooper(),
                sessionPoliciesForApplication);
        fullUserRecordLocked.mUidToSessionCount.put(i2, i4 + 1);
        fullUserRecordLocked.mPriorityStack.addSession(mediaSessionRecord2);
        this.mHandler.postSessionsChanged(mediaSessionRecord2);
        if (DEBUG) {
          StringBuilder sb = new StringBuilder();
          sb.append("Created session for ");
          mediaSessionRecord = mediaSessionRecord2;
          sb.append(str);
          sb.append(" with tag ");
          sb.append(str2);
          Log.d("MediaSessionService", sb.toString());
        } else {
          mediaSessionRecord = mediaSessionRecord2;
        }
      } catch (RemoteException e) {
        throw new RuntimeException("Media Session owner died prematurely.", e);
      }
    }
    return mediaSessionRecord;
  }

  public final int findIndexOfSessionsListenerLocked(
      IActiveSessionsListener iActiveSessionsListener) {
    for (int size = this.mSessionsListeners.size() - 1; size >= 0; size--) {
      if (((SessionsListenerRecord) this.mSessionsListeners.get(size)).listener.asBinder()
          == iActiveSessionsListener.asBinder()) {
        return size;
      }
    }
    return -1;
  }

  public final int findIndexOfSession2TokensListenerLocked(
      ISession2TokensListener iSession2TokensListener) {
    for (int size = this.mSession2TokensListenerRecords.size() - 1; size >= 0; size--) {
      if (((Session2TokensListenerRecord) this.mSession2TokensListenerRecords.get(size))
              .listener.asBinder()
          == iSession2TokensListener.asBinder()) {
        return size;
      }
    }
    return -1;
  }

  public final void pushSession1Changed(int i) {
    synchronized (this.mLock) {
      if (getFullUserRecordLocked(i) == null) {
        Log.w("MediaSessionService", "pushSession1ChangedOnHandler failed. No user with id=" + i);
        return;
      }
      List activeSessionsLocked = getActiveSessionsLocked(i);
      int size = activeSessionsLocked.size();
      ArrayList arrayList = new ArrayList();
      for (int i2 = 0; i2 < size; i2++) {
        setMultiSoundFlag((MediaSessionRecord) activeSessionsLocked.get(i2));
        arrayList.add(((MediaSessionRecord) activeSessionsLocked.get(i2)).getSessionToken());
      }
      pushRemoteVolumeUpdateLocked(i);
      for (int size2 = this.mSessionsListeners.size() - 1; size2 >= 0; size2--) {
        SessionsListenerRecord sessionsListenerRecord =
            (SessionsListenerRecord) this.mSessionsListeners.get(size2);
        if (sessionsListenerRecord.userId == UserHandle.ALL.getIdentifier()
            || sessionsListenerRecord.userId == i) {
          try {
            sessionsListenerRecord.listener.onActiveSessionsChanged(arrayList);
          } catch (RemoteException e) {
            Log.w(
                "MediaSessionService",
                "Dead ActiveSessionsListener in pushSessionsChanged, removing",
                e);
            this.mSessionsListeners.remove(size2);
          }
        }
      }
    }
  }

  public void pushSession2Changed(int i) {
    synchronized (this.mLock) {
      List session2TokensLocked = getSession2TokensLocked(UserHandle.ALL.getIdentifier());
      List session2TokensLocked2 = getSession2TokensLocked(i);
      for (int size = this.mSession2TokensListenerRecords.size() - 1; size >= 0; size--) {
        Session2TokensListenerRecord session2TokensListenerRecord =
            (Session2TokensListenerRecord) this.mSession2TokensListenerRecords.get(size);
        try {
          if (session2TokensListenerRecord.userId == UserHandle.ALL.getIdentifier()) {
            session2TokensListenerRecord.listener.onSession2TokensChanged(session2TokensLocked);
          } else if (session2TokensListenerRecord.userId == i) {
            session2TokensListenerRecord.listener.onSession2TokensChanged(session2TokensLocked2);
          }
        } catch (RemoteException e) {
          Log.w(
              "MediaSessionService",
              "Failed to notify Session2Token change. Removing listener.",
              e);
          this.mSession2TokensListenerRecords.remove(size);
        }
      }
    }
  }

  public final void pushRemoteVolumeUpdateLocked(int i) {
    FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(i);
    if (fullUserRecordLocked == null) {
      Log.w("MediaSessionService", "pushRemoteVolumeUpdateLocked failed. No user with id=" + i);
      return;
    }
    synchronized (this.mLock) {
      int beginBroadcast = this.mRemoteVolumeControllers.beginBroadcast();
      MediaSessionRecordImpl defaultRemoteSession =
          fullUserRecordLocked.mPriorityStack.getDefaultRemoteSession(i);
      if (defaultRemoteSession instanceof MediaSession2Record) {
        return;
      }
      MediaSession.Token sessionToken =
          defaultRemoteSession == null
              ? null
              : ((MediaSessionRecord) defaultRemoteSession).getSessionToken();
      for (int i2 = beginBroadcast - 1; i2 >= 0; i2--) {
        try {
          this.mRemoteVolumeControllers.getBroadcastItem(i2).onSessionChanged(sessionToken);
        } catch (Exception e) {
          Log.w("MediaSessionService", "Error sending default remote volume.", e);
        }
      }
      this.mRemoteVolumeControllers.finishBroadcast();
    }
  }

  public void onMediaButtonReceiverChanged(MediaSessionRecordImpl mediaSessionRecordImpl) {
    synchronized (this.mLock) {
      FullUserRecord fullUserRecordLocked =
          getFullUserRecordLocked(mediaSessionRecordImpl.getUserId());
      MediaSessionRecordImpl mediaButtonSession =
          fullUserRecordLocked.mPriorityStack.getMediaButtonSession();
      if (mediaSessionRecordImpl == mediaButtonSession) {
        fullUserRecordLocked.rememberMediaButtonReceiverLocked(mediaButtonSession);
      }
    }
  }

  public final String getCallingPackageName(int i) {
    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
    return (packagesForUid == null || packagesForUid.length <= 0) ? "" : packagesForUid[0];
  }

  public final void dispatchVolumeKeyLongPressLocked(KeyEvent keyEvent) {
    if (this.mVolumeKeyLongPressReceiver != null && keyEvent.getRepeatCount() % 15 == 0) {
      Intent intent = new Intent("com.samsung.android.intent.action.SOUND_EVENT");
      intent.putExtra("type", 32);
      intent.putExtra("keyevent", keyEvent);
      intent.setComponent(this.mVolumeKeyLongPressReceiver);
      getContext().sendBroadcastAsUser(intent, UserHandle.ALL);
    }
    if (this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener == null) {
      return;
    }
    try {
      this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener.onVolumeKeyLongPress(keyEvent);
    } catch (RemoteException unused) {
      Log.w(
          "MediaSessionService",
          "Failed to send " + keyEvent + " to volume key long-press listener");
    }
  }

  public final FullUserRecord getFullUserRecordLocked(int i) {
    int i2 = this.mFullUserIds.get(i, -1);
    if (i2 < 0) {
      return null;
    }
    return (FullUserRecord) this.mUserRecords.get(i2);
  }

  public final MediaSessionRecord getMediaSessionRecordLocked(MediaSession.Token token) {
    FullUserRecord fullUserRecordLocked =
        getFullUserRecordLocked(UserHandle.getUserHandleForUid(token.getUid()).getIdentifier());
    if (fullUserRecordLocked != null) {
      return fullUserRecordLocked.mPriorityStack.getMediaSessionRecord(token);
    }
    return null;
  }

  public final void instantiateCustomDispatcher(String str) {
    synchronized (this.mLock) {
      if (str != null) {
        try {
          if (!TextUtils.isEmpty(str)) {
            CurrentDeviceManager$$ExternalSyntheticThrowCCEIfNotNull0.m4m(
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

  public final class FullUserRecord
      implements MediaSessionStack.OnMediaButtonSessionChangedListener {
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

    public FullUserRecord(int i) {
      this.mFullUserId = i;
      ContentResolver contentResolver =
          MediaSessionService.this
              .mContext
              .createContextAsUser(UserHandle.of(i), 0)
              .getContentResolver();
      this.mContentResolver = contentResolver;
      this.mPriorityStack =
          new MediaSessionStack(MediaSessionService.this.mAudioPlayerStateMonitor, this);
      this.mLastMediaButtonReceiverHolder =
          MediaButtonReceiverHolder.unflattenFromString(
              MediaSessionService.this.mContext,
              Settings.Secure.getString(contentResolver, "media_button_receiver"));
    }

    public void destroySessionsForUserLocked(int i) {
      Iterator it = this.mPriorityStack.getPriorityList(false, i).iterator();
      while (it.hasNext()) {
        MediaSessionService.this.destroySessionLocked((MediaSessionRecord) it.next());
      }
    }

    public void addOnMediaKeyEventDispatchedListenerLocked(
        IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener, int i) {
      IBinder asBinder = iOnMediaKeyEventDispatchedListener.asBinder();
      OnMediaKeyEventDispatchedListenerRecord onMediaKeyEventDispatchedListenerRecord =
          new OnMediaKeyEventDispatchedListenerRecord(iOnMediaKeyEventDispatchedListener, i);
      this.mOnMediaKeyEventDispatchedListeners.put(
          asBinder, onMediaKeyEventDispatchedListenerRecord);
      try {
        asBinder.linkToDeath(onMediaKeyEventDispatchedListenerRecord, 0);
      } catch (RemoteException e) {
        Log.w("MediaSessionService", "Failed to add listener", e);
        this.mOnMediaKeyEventDispatchedListeners.remove(asBinder);
      }
    }

    public void removeOnMediaKeyEventDispatchedListenerLocked(
        IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener) {
      IBinder asBinder = iOnMediaKeyEventDispatchedListener.asBinder();
      asBinder.unlinkToDeath(
          (OnMediaKeyEventDispatchedListenerRecord)
              this.mOnMediaKeyEventDispatchedListeners.remove(asBinder),
          0);
    }

    public void addOnMediaKeyEventSessionChangedListenerLocked(
        IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, int i) {
      IBinder asBinder = iOnMediaKeyEventSessionChangedListener.asBinder();
      OnMediaKeyEventSessionChangedListenerRecord onMediaKeyEventSessionChangedListenerRecord =
          new OnMediaKeyEventSessionChangedListenerRecord(
              iOnMediaKeyEventSessionChangedListener, i);
      this.mOnMediaKeyEventSessionChangedListeners.put(
          asBinder, onMediaKeyEventSessionChangedListenerRecord);
      try {
        asBinder.linkToDeath(onMediaKeyEventSessionChangedListenerRecord, 0);
        pushAddressedPlayerChangedLocked(onMediaKeyEventSessionChangedListenerRecord.callback);
      } catch (RemoteException e) {
        Log.w("MediaSessionService", "Failed to add listener", e);
        this.mOnMediaKeyEventSessionChangedListeners.remove(asBinder);
      }
    }

    public void removeOnMediaKeyEventSessionChangedListener(
        IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) {
      IBinder asBinder = iOnMediaKeyEventSessionChangedListener.asBinder();
      asBinder.unlinkToDeath(
          (OnMediaKeyEventSessionChangedListenerRecord)
              this.mOnMediaKeyEventSessionChangedListeners.remove(asBinder),
          0);
    }

    public void dumpLocked(PrintWriter printWriter, String str) {
      printWriter.print(str + "Record for full_user=" + this.mFullUserId);
      int size = MediaSessionService.this.mFullUserIds.size();
      for (int i = 0; i < size; i++) {
        if (MediaSessionService.this.mFullUserIds.keyAt(i)
                != MediaSessionService.this.mFullUserIds.valueAt(i)
            && MediaSessionService.this.mFullUserIds.valueAt(i) == this.mFullUserId) {
          printWriter.print(", profile_user=" + MediaSessionService.this.mFullUserIds.keyAt(i));
        }
      }
      printWriter.println();
      String str2 = str + "  ";
      printWriter.println(
          str2 + "Volume key long-press listener: " + this.mOnVolumeKeyLongPressListener);
      printWriter.println(
          str2
              + "Volume key long-press listener package: "
              + MediaSessionService.this.getCallingPackageName(
                  this.mOnVolumeKeyLongPressListenerUid));
      printWriter.println(str2 + "Media key listener: " + this.mOnMediaKeyListener);
      printWriter.println(
          str2
              + "Media key listener package: "
              + MediaSessionService.this.getCallingPackageName(this.mOnMediaKeyListenerUid));
      printWriter.println(
          str2
              + "OnMediaKeyEventDispatchedListener: added "
              + this.mOnMediaKeyEventDispatchedListeners.size()
              + " listener(s)");
      Iterator it = this.mOnMediaKeyEventDispatchedListeners.values().iterator();
      while (it.hasNext()) {
        printWriter.println(
            str2
                + "  from "
                + MediaSessionService.this.getCallingPackageName(
                    ((OnMediaKeyEventDispatchedListenerRecord) it.next()).uid));
      }
      printWriter.println(
          str2
              + "OnMediaKeyEventSessionChangedListener: added "
              + this.mOnMediaKeyEventSessionChangedListeners.size()
              + " listener(s)");
      Iterator it2 = this.mOnMediaKeyEventSessionChangedListeners.values().iterator();
      while (it2.hasNext()) {
        printWriter.println(
            str2
                + "  from "
                + MediaSessionService.this.getCallingPackageName(
                    ((OnMediaKeyEventSessionChangedListenerRecord) it2.next()).uid));
      }
      printWriter.println(
          str2 + "Last MediaButtonReceiver: " + this.mLastMediaButtonReceiverHolder);
      printWriter.println(
          str2
              + "High priority mediakey receiver: "
              + MediaSessionService.this.mHighPriorityMediaKeyReceiver);
      printWriter.println(
          str2
              + "Volume key long-press receiver: "
              + MediaSessionService.this.mVolumeKeyLongPressReceiver);
      this.mPriorityStack.dump(printWriter, str2);
    }

    @Override // com.android.server.media.MediaSessionStack.OnMediaButtonSessionChangedListener
    public void onMediaButtonSessionChanged(
        MediaSessionRecordImpl mediaSessionRecordImpl,
        MediaSessionRecordImpl mediaSessionRecordImpl2) {
      Log.d("MediaSessionService", "Media button session is changed to " + mediaSessionRecordImpl2);
      synchronized (MediaSessionService.this.mLock) {
        if (mediaSessionRecordImpl != null) {
          try {
            MediaSessionService.this.mHandler.postSessionsChanged(mediaSessionRecordImpl);
          } catch (Throwable th) {
            throw th;
          }
        }
        if (mediaSessionRecordImpl2 != null) {
          rememberMediaButtonReceiverLocked(mediaSessionRecordImpl2);
          MediaSessionService.this.mHandler.postSessionsChanged(mediaSessionRecordImpl2);
        }
        pushAddressedPlayerChangedLocked();
      }
    }

    public void rememberMediaButtonReceiverLocked(MediaSessionRecordImpl mediaSessionRecordImpl) {
      if (mediaSessionRecordImpl instanceof MediaSession2Record) {
        return;
      }
      MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) mediaSessionRecordImpl;
      if (mediaSessionRecord.getMediaButtonReceiver() == null) {
        return;
      }
      MediaButtonReceiverHolder mediaButtonReceiver = mediaSessionRecord.getMediaButtonReceiver();
      this.mLastMediaButtonReceiverHolder = mediaButtonReceiver;
      Settings.Secure.putString(
          this.mContentResolver,
          "media_button_receiver",
          mediaButtonReceiver == null ? "" : mediaButtonReceiver.flattenToString());
    }

    public final void pushAddressedPlayerChangedLocked(
        IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener) {
      try {
        MediaSessionRecordImpl mediaButtonSessionLocked = getMediaButtonSessionLocked();
        if (mediaButtonSessionLocked != null) {
          if (mediaButtonSessionLocked instanceof MediaSessionRecord) {
            MediaSessionRecord mediaSessionRecord = (MediaSessionRecord) mediaButtonSessionLocked;
            MediaSessionService.this.setMultiSoundFlag(mediaSessionRecord);
            iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(
                mediaSessionRecord.getPackageName(), mediaSessionRecord.getSessionToken());
          }
        } else if (MediaSessionService.this.mCurrentFullUserRecord.mLastMediaButtonReceiverHolder
            != null) {
          iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(
              MediaSessionService.this.mCurrentFullUserRecord.mLastMediaButtonReceiverHolder
                  .getPackageName(),
              (MediaSession.Token) null);
        } else {
          iOnMediaKeyEventSessionChangedListener.onMediaKeyEventSessionChanged(
              "", (MediaSession.Token) null);
        }
      } catch (RemoteException e) {
        Log.w("MediaSessionService", "Failed to pushAddressedPlayerChangedLocked", e);
      }
    }

    public final void pushAddressedPlayerChangedLocked() {
      Iterator it = this.mOnMediaKeyEventSessionChangedListeners.values().iterator();
      while (it.hasNext()) {
        pushAddressedPlayerChangedLocked(
            ((OnMediaKeyEventSessionChangedListenerRecord) it.next()).callback);
      }
    }

    public final MediaSessionRecordImpl getMediaButtonSessionLocked() {
      return MediaSessionService.this.isGlobalPriorityActiveLocked()
          ? MediaSessionService.this.mGlobalPrioritySession
          : this.mPriorityStack.getMediaButtonSession();
    }

    public final class OnMediaKeyEventDispatchedListenerRecord implements IBinder.DeathRecipient {
      public final IOnMediaKeyEventDispatchedListener callback;
      public final int uid;

      public OnMediaKeyEventDispatchedListenerRecord(
          IOnMediaKeyEventDispatchedListener iOnMediaKeyEventDispatchedListener, int i) {
        this.callback = iOnMediaKeyEventDispatchedListener;
        this.uid = i;
      }

      @Override // android.os.IBinder.DeathRecipient
      public void binderDied() {
        synchronized (MediaSessionService.this.mLock) {
          FullUserRecord.this.mOnMediaKeyEventDispatchedListeners.remove(this.callback.asBinder());
        }
      }
    }

    public final class OnMediaKeyEventSessionChangedListenerRecord
        implements IBinder.DeathRecipient {
      public final IOnMediaKeyEventSessionChangedListener callback;
      public final int uid;

      public OnMediaKeyEventSessionChangedListenerRecord(
          IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, int i) {
        this.callback = iOnMediaKeyEventSessionChangedListener;
        this.uid = i;
      }

      @Override // android.os.IBinder.DeathRecipient
      public void binderDied() {
        synchronized (MediaSessionService.this.mLock) {
          FullUserRecord.this.mOnMediaKeyEventSessionChangedListeners.remove(
              this.callback.asBinder());
        }
      }
    }
  }

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
    public void binderDied() {
      synchronized (MediaSessionService.this.mLock) {
        MediaSessionService.this.mSessionsListeners.remove(this);
      }
    }
  }

  public final class Session2TokensListenerRecord implements IBinder.DeathRecipient {
    public final ISession2TokensListener listener;
    public final int userId;

    public Session2TokensListenerRecord(ISession2TokensListener iSession2TokensListener, int i) {
      this.listener = iSession2TokensListener;
      this.userId = i;
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      synchronized (MediaSessionService.this.mLock) {
        MediaSessionService.this.mSession2TokensListenerRecords.remove(this);
      }
    }
  }

  public class SessionManagerImpl extends ISessionManager.Stub {
    public KeyEventWakeLockReceiver mKeyEventReceiver;
    public boolean mSkippedFirstKeyFromKeyCustomizer;
    public VolumeKeyLongPressControlThread mVolumeKeyLongPressControlThread;
    public KeyEventHandler mMediaKeyEventHandler = new KeyEventHandler(0);
    public KeyEventHandler mVolumeKeyEventHandler = new KeyEventHandler(1);
    public final EventLogger mEventLogger = new EventLogger(80, "MediaSession events");
    public final Runnable mKeyCustomizerRunnable = new Runnable() { // from class:
          // com.android.server.media.MediaSessionService$SessionManagerImpl$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            MediaSessionService.SessionManagerImpl.this.lambda$new$0();
          }
        };

    public final boolean isValidLocalStreamType(int i) {
      return i >= 0 && i <= 5;
    }

    public SessionManagerImpl() {
      this.mKeyEventReceiver = new KeyEventWakeLockReceiver(MediaSessionService.this.mHandler);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onShellCommand(
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

    public ISession createSession(
        String str, ISessionCallback iSessionCallback, String str2, Bundle bundle, int i) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        try {
          MediaServerUtils.enforcePackageName(str, callingUid);
          int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, str);
          if (iSessionCallback == null) {
            throw new IllegalArgumentException("Controller callback cannot be null");
          }
          MediaSessionRecord createSessionInternal =
              MediaSessionService.this.createSessionInternal(
                  callingPid, callingUid, handleIncomingUser, str, iSessionCallback, str2, bundle);
          if (createSessionInternal == null) {
            throw new IllegalStateException("Failed to create a new session record");
          }
          ISession sessionBinder = createSessionInternal.getSessionBinder();
          if (sessionBinder != null) {
            return sessionBinder;
          }
          throw new IllegalStateException("Invalid session record");
        } catch (Exception e) {
          Log.w("MediaSessionService", "Exception in creating a new session", e);
          throw e;
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public List getSessions(ComponentName componentName, int i) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        int verifySessionsRequest = verifySessionsRequest(componentName, i, callingPid, callingUid);
        ArrayList arrayList = new ArrayList();
        synchronized (MediaSessionService.this.mLock) {
          Iterator it =
              MediaSessionService.this.getActiveSessionsLocked(verifySessionsRequest).iterator();
          while (it.hasNext()) {
            arrayList.add(((MediaSessionRecord) it.next()).getSessionToken());
          }
        }
        return arrayList;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public MediaSession.Token getMediaKeyEventSession(String str) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        MediaServerUtils.enforcePackageName(str, callingUid);
        MediaSessionService.this.enforceMediaPermissions(str, callingPid, callingUid, identifier);
        synchronized (MediaSessionService.this.mLock) {
          FullUserRecord fullUserRecordLocked =
              MediaSessionService.this.getFullUserRecordLocked(identifier);
          if (fullUserRecordLocked == null) {
            Log.w(
                "MediaSessionService",
                "No matching user record to get the media key event session, userId=" + identifier);
            return null;
          }
          MediaSessionRecordImpl mediaButtonSessionLocked =
              fullUserRecordLocked.getMediaButtonSessionLocked();
          if (mediaButtonSessionLocked instanceof MediaSessionRecord) {
            return ((MediaSessionRecord) mediaButtonSessionLocked).getSessionToken();
          }
          return null;
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public String getMediaKeyEventSessionPackageName(String str) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        MediaServerUtils.enforcePackageName(str, callingUid);
        MediaSessionService.this.enforceMediaPermissions(str, callingPid, callingUid, identifier);
        synchronized (MediaSessionService.this.mLock) {
          FullUserRecord fullUserRecordLocked =
              MediaSessionService.this.getFullUserRecordLocked(identifier);
          if (fullUserRecordLocked == null) {
            Log.w(
                "MediaSessionService",
                "No matching user record to get the media key event session package , userId="
                    + identifier);
            return "";
          }
          MediaSessionRecordImpl mediaButtonSessionLocked =
              fullUserRecordLocked.getMediaButtonSessionLocked();
          if (mediaButtonSessionLocked instanceof MediaSessionRecord) {
            return mediaButtonSessionLocked.getPackageName();
          }
          if (fullUserRecordLocked.mLastMediaButtonReceiverHolder == null) {
            return "";
          }
          return fullUserRecordLocked.mLastMediaButtonReceiverHolder.getPackageName();
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void addSessionsListener(
        IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int i) {
      if (iActiveSessionsListener == null) {
        Log.w("MediaSessionService", "addSessionsListener: listener is null, ignoring");
        return;
      }
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        int verifySessionsRequest = verifySessionsRequest(componentName, i, callingPid, callingUid);
        synchronized (MediaSessionService.this.mLock) {
          if (MediaSessionService.this.findIndexOfSessionsListenerLocked(iActiveSessionsListener)
              != -1) {
            Log.w("MediaSessionService", "ActiveSessionsListener is already added, ignoring");
            return;
          }
          SessionsListenerRecord sessionsListenerRecord =
              MediaSessionService.this
              .new SessionsListenerRecord(
                  iActiveSessionsListener,
                  componentName,
                  verifySessionsRequest,
                  callingPid,
                  callingUid);
          try {
            iActiveSessionsListener.asBinder().linkToDeath(sessionsListenerRecord, 0);
            MediaSessionService.this.mSessionsListeners.add(sessionsListenerRecord);
          } catch (RemoteException e) {
            Log.e("MediaSessionService", "ActiveSessionsListener is dead, ignoring it", e);
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) {
      synchronized (MediaSessionService.this.mLock) {
        int findIndexOfSessionsListenerLocked =
            MediaSessionService.this.findIndexOfSessionsListenerLocked(iActiveSessionsListener);
        if (findIndexOfSessionsListenerLocked != -1) {
          SessionsListenerRecord sessionsListenerRecord =
              (SessionsListenerRecord)
                  MediaSessionService.this.mSessionsListeners.remove(
                      findIndexOfSessionsListenerLocked);
          try {
            sessionsListenerRecord.listener.asBinder().unlinkToDeath(sessionsListenerRecord, 0);
          } catch (Exception unused) {
          }
        }
      }
    }

    public void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int i) {
      if (iSession2TokensListener == null) {
        Log.w("MediaSessionService", "addSession2TokensListener: listener is null, ignoring");
        return;
      }
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        int handleIncomingUser = handleIncomingUser(callingPid, callingUid, i, null);
        synchronized (MediaSessionService.this.mLock) {
          if (MediaSessionService.this.findIndexOfSession2TokensListenerLocked(
                  iSession2TokensListener)
              >= 0) {
            Log.w(
                "MediaSessionService",
                "addSession2TokensListener: listener is already added, ignoring");
          } else {
            MediaSessionService.this.mSession2TokensListenerRecords.add(
                MediaSessionService.this
                .new Session2TokensListenerRecord(iSession2TokensListener, handleIncomingUser));
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) {
      Binder.getCallingPid();
      Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (MediaSessionService.this.mLock) {
          int findIndexOfSession2TokensListenerLocked =
              MediaSessionService.this.findIndexOfSession2TokensListenerLocked(
                  iSession2TokensListener);
          if (findIndexOfSession2TokensListenerLocked >= 0) {
            Session2TokensListenerRecord session2TokensListenerRecord =
                (Session2TokensListenerRecord)
                    MediaSessionService.this.mSession2TokensListenerRecords.remove(
                        findIndexOfSession2TokensListenerLocked);
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

    public void dispatchMediaKeyEvent(String str, boolean z, KeyEvent keyEvent, boolean z2) {
      KeyEvent keyEvent2;
      if (keyEvent == null || !KeyEvent.isMediaSessionKey(keyEvent.getKeyCode())) {
        Log.w("MediaSessionService", "Attempted to dispatch null or non-media key event.");
        return;
      }
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (MediaSessionService.DEBUG) {
          Log.d(
              "MediaSessionService",
              "dispatchMediaKeyEvent, pkg="
                  + str
                  + " pid="
                  + callingPid
                  + ", uid="
                  + callingUid
                  + ", asSystem="
                  + z
                  + ", event="
                  + keyEvent);
          this.mEventLogger.enqueue(
              new EventLogger.StringEvent(
                  "MediaKeyEvt,pkg="
                      + str
                      + " pid="
                      + callingPid
                      + ",uid="
                      + callingUid
                      + ",asSystem="
                      + z
                      + ",code="
                      + KeyEvent.keyCodeToString(keyEvent.getKeyCode())
                      + ",act:"
                      + KeyEvent.actionToString(keyEvent.getAction())));
        }
        if (!isUserSetupComplete()) {
          Log.i(
              "MediaSessionService",
              "Not dispatching media key event because user setup is in progress.");
          return;
        }
        if (PlatformTypeUtils.ignoreVoiceKey(MediaSessionService.this.mContext)) {
          Log.d("MediaSessionService", "dispatchMediaKeyEvent: voice key is ignored");
          return;
        }
        synchronized (MediaSessionService.this.mLock) {
          boolean isGlobalPriorityActiveLocked =
              MediaSessionService.this.isGlobalPriorityActiveLocked();
          if (isGlobalPriorityActiveLocked && callingUid != 1000) {
            Log.i(
                "MediaSessionService",
                "Only the system can dispatch media key event to the global priority session.");
            return;
          }
          if (!isGlobalPriorityActiveLocked
              && MediaSessionService.this.mCurrentFullUserRecord.mOnMediaKeyListener != null) {
            Log.d("MediaSessionService", "Send " + keyEvent + " to the media key listener");
            try {
              MediaSessionService.this.mCurrentFullUserRecord.mOnMediaKeyListener.onMediaKey(
                  keyEvent,
                  new MediaKeyListenerResultReceiver(str, callingPid, callingUid, z, keyEvent, z2));
              return;
            } catch (RemoteException unused) {
              Log.w(
                  "MediaSessionService",
                  "Failed to send " + keyEvent + " to the media key listener");
            }
          }
          if (isGlobalPriorityActiveLocked) {
            dispatchMediaKeyEventLocked(str, callingPid, callingUid, z, keyEvent, z2);
          } else {
            if (MediaSessionService.this.isMultiSoundOn() && callingUid == 1002) {
              Log.d("MediaSessionService", "dispatchMediaKeyEvent called by BT, " + keyEvent);
              keyEvent2 = KeyEvent.changeFlags(keyEvent, 536870912);
            } else {
              keyEvent2 = keyEvent;
            }
            this.mMediaKeyEventHandler.handleMediaKeyEventLocked(
                str, callingPid, callingUid, z, keyEvent2, z2);
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public boolean dispatchMediaKeyEventToSessionAsSystemService(
        String str, KeyEvent keyEvent, MediaSession.Token token) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (MediaSessionService.this.mLock) {
          MediaSessionRecord mediaSessionRecordLocked =
              MediaSessionService.this.getMediaSessionRecordLocked(token);
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
                  + mediaSessionRecordLocked);
          if (mediaSessionRecordLocked == null) {
            Log.w("MediaSessionService", "Failed to find session to dispatch key event.");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
          }
          return mediaSessionRecordLocked.sendMediaButton(
              str, callingPid, callingUid, true, keyEvent, 0, null);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void addOnMediaKeyEventDispatchedListener(
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
          if (fullUserRecordLocked != null && fullUserRecordLocked.mFullUserId == identifier) {
            fullUserRecordLocked.addOnMediaKeyEventDispatchedListenerLocked(
                iOnMediaKeyEventDispatchedListener, callingUid);
            Log.d(
                "MediaSessionService",
                "The MediaKeyEventDispatchedListener ("
                    + iOnMediaKeyEventDispatchedListener.asBinder()
                    + ") is added by "
                    + MediaSessionService.this.getCallingPackageName(callingUid));
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

    public void removeOnMediaKeyEventDispatchedListener(
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
          if (fullUserRecordLocked != null && fullUserRecordLocked.mFullUserId == identifier) {
            fullUserRecordLocked.removeOnMediaKeyEventDispatchedListenerLocked(
                iOnMediaKeyEventDispatchedListener);
            Log.d(
                "MediaSessionService",
                "The MediaKeyEventDispatchedListener ("
                    + iOnMediaKeyEventDispatchedListener.asBinder()
                    + ") is removed by "
                    + MediaSessionService.this.getCallingPackageName(callingUid));
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

    public void addOnMediaKeyEventSessionChangedListener(
        IOnMediaKeyEventSessionChangedListener iOnMediaKeyEventSessionChangedListener, String str) {
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
        MediaServerUtils.enforcePackageName(str, callingUid);
        MediaSessionService.this.enforceMediaPermissions(str, callingPid, callingUid, identifier);
        synchronized (MediaSessionService.this.mLock) {
          FullUserRecord fullUserRecordLocked =
              MediaSessionService.this.getFullUserRecordLocked(identifier);
          if (fullUserRecordLocked != null && fullUserRecordLocked.mFullUserId == identifier) {
            fullUserRecordLocked.addOnMediaKeyEventSessionChangedListenerLocked(
                iOnMediaKeyEventSessionChangedListener, callingUid);
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

    public void removeOnMediaKeyEventSessionChangedListener(
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
          if (fullUserRecordLocked != null && fullUserRecordLocked.mFullUserId == identifier) {
            fullUserRecordLocked.removeOnMediaKeyEventSessionChangedListener(
                iOnMediaKeyEventSessionChangedListener);
            Log.d(
                "MediaSessionService",
                "The MediaKeyEventSessionChangedListener ("
                    + iOnMediaKeyEventSessionChangedListener.asBinder()
                    + ") is removed by "
                    + MediaSessionService.this.getCallingPackageName(callingUid));
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

    public void setOnVolumeKeyLongPressListener(
        IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (MediaSessionService.this.mContext.checkPermission(
                "android.permission.SET_VOLUME_KEY_LONG_PRESS_LISTENER", callingPid, callingUid)
            != 0) {
          throw new SecurityException(
              "Must hold the SET_VOLUME_KEY_LONG_PRESS_LISTENER permission.");
        }
        synchronized (MediaSessionService.this.mLock) {
          int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
          final FullUserRecord fullUserRecordLocked =
              MediaSessionService.this.getFullUserRecordLocked(identifier);
          if (fullUserRecordLocked != null && fullUserRecordLocked.mFullUserId == identifier) {
            if (fullUserRecordLocked.mOnVolumeKeyLongPressListener != null
                && fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid != callingUid) {
              Log.w(
                  "MediaSessionService",
                  "The volume key long-press listener cannot be reset by another app ,"
                      + " mOnVolumeKeyLongPressListener="
                      + fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid
                      + ", uid="
                      + callingUid);
              return;
            }
            fullUserRecordLocked.mOnVolumeKeyLongPressListener = iOnVolumeKeyLongPressListener;
            fullUserRecordLocked.mOnVolumeKeyLongPressListenerUid = callingUid;
            Log.d(
                "MediaSessionService",
                "The volume key long-press listener "
                    + iOnVolumeKeyLongPressListener
                    + " is set by "
                    + MediaSessionService.this.getCallingPackageName(callingUid));
            if (fullUserRecordLocked.mOnVolumeKeyLongPressListener != null) {
              try {
                fullUserRecordLocked
                    .mOnVolumeKeyLongPressListener
                    .asBinder()
                    .linkToDeath(
                        new IBinder.DeathRecipient() { // from class:
                          // com.android.server.media.MediaSessionService.SessionManagerImpl.1
                          @Override // android.os.IBinder.DeathRecipient
                          public void binderDied() {
                            synchronized (MediaSessionService.this.mLock) {
                              fullUserRecordLocked.mOnVolumeKeyLongPressListener = null;
                            }
                          }
                        },
                        0);
              } catch (RemoteException unused) {
                Log.w(
                    "MediaSessionService",
                    "Failed to set death recipient "
                        + fullUserRecordLocked.mOnVolumeKeyLongPressListener);
                fullUserRecordLocked.mOnVolumeKeyLongPressListener = null;
              }
            }
            return;
          }
          Log.w(
              "MediaSessionService",
              "Only the full user can set the volume key long-press listener, userId="
                  + identifier);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) {
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
          final FullUserRecord fullUserRecordLocked =
              MediaSessionService.this.getFullUserRecordLocked(identifier);
          if (fullUserRecordLocked != null && fullUserRecordLocked.mFullUserId == identifier) {
            if (fullUserRecordLocked.mOnMediaKeyListener != null
                && fullUserRecordLocked.mOnMediaKeyListenerUid != callingUid) {
              Log.w(
                  "MediaSessionService",
                  "The media key listener cannot be reset by another app. , mOnMediaKeyListenerUid="
                      + fullUserRecordLocked.mOnMediaKeyListenerUid
                      + ", uid="
                      + callingUid);
              return;
            }
            fullUserRecordLocked.mOnMediaKeyListener = iOnMediaKeyListener;
            fullUserRecordLocked.mOnMediaKeyListenerUid = callingUid;
            Log.d(
                "MediaSessionService",
                "The media key listener "
                    + fullUserRecordLocked.mOnMediaKeyListener
                    + " is set by "
                    + MediaSessionService.this.getCallingPackageName(callingUid));
            if (fullUserRecordLocked.mOnMediaKeyListener != null) {
              try {
                fullUserRecordLocked
                    .mOnMediaKeyListener
                    .asBinder()
                    .linkToDeath(
                        new IBinder.DeathRecipient() { // from class:
                          // com.android.server.media.MediaSessionService.SessionManagerImpl.2
                          @Override // android.os.IBinder.DeathRecipient
                          public void binderDied() {
                            synchronized (MediaSessionService.this.mLock) {
                              fullUserRecordLocked.mOnMediaKeyListener = null;
                            }
                          }
                        },
                        0);
              } catch (RemoteException unused) {
                Log.w(
                    "MediaSessionService",
                    "Failed to set death recipient " + fullUserRecordLocked.mOnMediaKeyListener);
                fullUserRecordLocked.mOnMediaKeyListener = null;
              }
            }
            return;
          }
          Log.w(
              "MediaSessionService",
              "Only the full user can set the media key listener, userId=" + identifier);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void dispatchVolumeKeyEvent(
        String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) {
      if (keyEvent == null
          || (keyEvent.getKeyCode() != 24
              && keyEvent.getKeyCode() != 25
              && keyEvent.getKeyCode() != 164)) {
        Log.w("MediaSessionService", "Attempted to dispatch null or non-volume key event.");
        return;
      }
      if (needSkipKeyEventFromKeyCustomizer(str, str2, z, keyEvent, i, z2)) {
        return;
      }
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      Log.d(
          "MediaSessionService",
          "dispatchVolumeKeyEvent, pkg="
              + str
              + ", opPkg="
              + str2
              + ", pid="
              + callingPid
              + ", uid="
              + callingUid
              + ", asSystem="
              + z
              + ", event="
              + keyEvent
              + ", stream="
              + i
              + ", musicOnly="
              + z2);
      this.mEventLogger.enqueue(
          new EventLogger.StringEvent(
              "VolKeyEvt, pkg="
                  + str
                  + ",opPkg="
                  + str2
                  + ",pid="
                  + callingPid
                  + ",uid="
                  + callingUid
                  + ",asSystem="
                  + z
                  + ",code="
                  + KeyEvent.keyCodeToString(keyEvent.getKeyCode())
                  + ",act:"
                  + KeyEvent.actionToString(keyEvent.getAction())
                  + ",stream="
                  + i
                  + ",musicOnly="
                  + z2));
      try {
        synchronized (MediaSessionService.this.mLock) {
          if (MediaSessionService.this.isGlobalPriorityActiveLocked()) {
            dispatchVolumeKeyEventLocked(str, str2, callingPid, callingUid, z, keyEvent, i, z2);
          } else {
            this.mVolumeKeyEventHandler.handleVolumeKeyEventLocked(
                str, callingPid, callingUid, z, keyEvent, str2, i, z2);
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public final void dispatchVolumeKeyEventLocked(
        String str, String str2, int i, int i2, boolean z, KeyEvent keyEvent, int i3, boolean z2) {
      boolean z3;
      int i4;
      int adjustVolumeForRotation;
      int i5;
      boolean z4 = keyEvent.getAction() == 0;
      boolean z5 = keyEvent.getAction() == 1;
      int keyCode = keyEvent.getKeyCode();
      if (keyCode == 24) {
        z3 = false;
        i4 = 1;
      } else if (keyCode == 25) {
        z3 = false;
        i4 = -1;
      } else if (keyCode != 164) {
        i4 = 0;
        z3 = false;
      } else {
        i4 = 0;
        z3 = true;
      }
      if (z4 || z5) {
        boolean z6 =
            (MediaSessionService.this.mKeyguardManager != null
                    && MediaSessionService.this.mKeyguardManager.isKeyguardLocked()
                    && MediaSessionService.this.mKeyguardManager
                        .semIsKeyguardShowingAndNotOccluded()
                    && MediaSessionService.this.mCoverHelper.isCoverOpen())
                ? true
                : (MediaSessionService.this.mDesktopModeHelper == null
                        || !MediaSessionService.this.mDesktopModeHelper.isDesktopMode())
                    ? z2
                    : false;
        int i6 = z6 ? 4608 : IInstalld.FLAG_USE_QUOTA;
        if (!z6) {
          i6 = z5 ? i6 | 20 : i6 | 17;
        }
        int i7 = i6;
        if (i4 != 0) {
          if (z5) {
            i4 = 0;
          }
          if ((i4 != -1 && i4 != 1)
              || (i7 & IInstalld.FLAG_USE_QUOTA) == 0
              || (adjustVolumeForRotation = adjustVolumeForRotation(i4)) == i4) {
            i5 = i4;
          } else {
            Log.d(
                "MediaSessionService",
                "adjustVolumeForRotation() dir changed to " + adjustVolumeForRotation);
            i5 = adjustVolumeForRotation;
          }
          handleVolumeKeyLongPressLocked(
              str, str2, i, i3, i5, i7, z4 && keyEvent.getRepeatCount() == 0);
          dispatchAdjustVolumeLocked(str, str2, i, i2, z, i3, i5, i7, z6);
          return;
        }
        if (z3 && z4 && keyEvent.getRepeatCount() == 0) {
          dispatchAdjustVolumeLocked(str, str2, i, i2, z, i3, 101, i7, z6);
        }
      }
    }

    public void dispatchVolumeKeyEventToSessionAsSystemService(
        String str, String str2, KeyEvent keyEvent, MediaSession.Token token) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (MediaSessionService.this.mLock) {
          MediaSessionRecord mediaSessionRecordLocked =
              MediaSessionService.this.getMediaSessionRecordLocked(token);
          MediaSessionRecordImpl defaultVolumeSession =
              MediaSessionService.this.mCurrentFullUserRecord.mPriorityStack
                  .getDefaultVolumeSession();
          if (defaultVolumeSession != null
              && "com.samsung.android.audiomirroring".equals(defaultVolumeSession.getPackageName())
              && mediaSessionRecordLocked != null
              && MediaSessionService.this.isMirroringPackage(
                  mediaSessionRecordLocked.getPackageName())) {
            mediaSessionRecordLocked = (MediaSessionRecord) defaultVolumeSession;
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
                  + mediaSessionRecordLocked);
          if (mediaSessionRecordLocked == null) {
            Log.w(
                "MediaSessionService",
                "Failed to find session to dispatch key event, token="
                    + token
                    + ". Fallbacks to the default handling.");
            dispatchVolumeKeyEventLocked(
                str, str2, callingPid, callingUid, true, keyEvent, Integer.MIN_VALUE, false);
            return;
          }
          int action = keyEvent.getAction();
          if (action == 0) {
            int keyCode = keyEvent.getKeyCode();
            mediaSessionRecordLocked.adjustVolume(
                str,
                str2,
                callingPid,
                callingUid,
                true,
                adjustVolumeForRotation(
                    keyCode != 24 ? keyCode != 25 ? keyCode != 164 ? 0 : 101 : -1 : 1),
                4097,
                false);
          } else if (action == 1) {
            mediaSessionRecordLocked.adjustVolume(
                str, str2, callingPid, callingUid, true, 0, 4116, false);
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public final int adjustVolumeForRotation(int i) {
      WindowManager windowManager;
      CustomDeviceManagerProxy customDeviceManagerProxy = CustomDeviceManagerProxy.getInstance();
      if (customDeviceManagerProxy == null
          || !customDeviceManagerProxy.getVolumeButtonRotationState()
          || (windowManager =
                  (WindowManager) MediaSessionService.this.mContext.getSystemService("window"))
              == null) {
        return i;
      }
      int rotation = windowManager.getDefaultDisplay().getRotation();
      if (MediaSessionService.this.mContext.getResources().getConfiguration().orientation == 2) {
        if (rotation != 1 && rotation != 2) {
          return i;
        }
      } else if (rotation != 2 && rotation != 3) {
        return i;
      }
      return i * (-1);
    }

    public void dispatchAdjustVolume(String str, String str2, int i, int i2, int i3) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (MediaSessionService.this.mLock) {
          dispatchAdjustVolumeLocked(str, str2, callingPid, callingUid, false, i, i2, i3, false);
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void registerRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      synchronized (MediaSessionService.this.mLock) {
        try {
          MediaSessionService.this.enforceStatusBarServicePermission(
              "listen for volume changes", callingPid, callingUid);
          MediaSessionService.this.mRemoteVolumeControllers.register(iRemoteSessionCallback);
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

    public void unregisterRemoteSessionCallback(IRemoteSessionCallback iRemoteSessionCallback) {
      int callingPid = Binder.getCallingPid();
      int callingUid = Binder.getCallingUid();
      long clearCallingIdentity = Binder.clearCallingIdentity();
      synchronized (MediaSessionService.this.mLock) {
        try {
          MediaSessionService.this.enforceStatusBarServicePermission(
              "listen for volume changes", callingPid, callingUid);
          MediaSessionService.this.mRemoteVolumeControllers.unregister(iRemoteSessionCallback);
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

    public boolean isGlobalPriorityActive() {
      boolean isGlobalPriorityActiveLocked;
      synchronized (MediaSessionService.this.mLock) {
        isGlobalPriorityActiveLocked = MediaSessionService.this.isGlobalPriorityActiveLocked();
      }
      return isGlobalPriorityActiveLocked;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
      if (MediaServerUtils.checkDumpPermission(
          MediaSessionService.this.mContext, "MediaSessionService", printWriter)) {
        printWriter.println("MEDIA SESSION SERVICE (dumpsys media_session)");
        printWriter.println();
        synchronized (MediaSessionService.this.mLock) {
          printWriter.println(
              MediaSessionService.this.mSessionsListeners.size() + " sessions listeners.");
          printWriter.println(
              "Global priority session is " + MediaSessionService.this.mGlobalPrioritySession);
          if (MediaSessionService.this.mGlobalPrioritySession != null) {
            MediaSessionService.this.mGlobalPrioritySession.dump(printWriter, "  ");
          }
          printWriter.println("User Records:");
          int size = MediaSessionService.this.mUserRecords.size();
          for (int i = 0; i < size; i++) {
            ((FullUserRecord) MediaSessionService.this.mUserRecords.valueAt(i))
                .dumpLocked(printWriter, "");
          }
          printWriter.println("isAppCastingOn:" + MediaSessionService.this.mIsAppCastingOn);
          printWriter.println("isMultiSoundOn:" + MediaSessionService.this.mIsMultiSoundOn);
          this.mEventLogger.dump(printWriter);
          MediaSessionService.this.mAudioPlayerStateMonitor.dump(
              MediaSessionService.this.mContext, printWriter, "");
        }
        MediaSessionDeviceConfig.dump(printWriter, "");
      }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x002c, code lost:

       if (hasEnabledNotificationListener(r1, r6, r8) != false) goto L10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isTrusted(String str, int i, int i2) {
      int callingUid = Binder.getCallingUid();
      int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
      boolean z = false;
      if (((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
          .filterAppAccess(str, callingUid, identifier)) {
        return false;
      }
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        if (!MediaSessionService.this.hasMediaControlPermission(i, i2)) {}
        z = true;
        return z;
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public void setCustomMediaKeyDispatcher(String str) {
      MediaSessionService.this.instantiateCustomDispatcher(str);
    }

    public void setCustomMediaSessionPolicyProvider(String str) {
      MediaSessionService.this.instantiateCustomProvider(str);
    }

    public boolean hasCustomMediaKeyDispatcher(String str) {
      MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(MediaSessionService.this);
      return false;
    }

    public boolean hasCustomMediaSessionPolicyProvider(String str) {
      if (MediaSessionService.this.mCustomMediaSessionPolicyProvider == null) {
        return false;
      }
      return TextUtils.equals(
          str, MediaSessionService.this.mCustomMediaSessionPolicyProvider.getClass().getName());
    }

    public int getSessionPolicies(MediaSession.Token token) {
      synchronized (MediaSessionService.this.mLock) {
        MediaSessionRecord mediaSessionRecordLocked =
            MediaSessionService.this.getMediaSessionRecordLocked(token);
        if (mediaSessionRecordLocked == null) {
          return 0;
        }
        return mediaSessionRecordLocked.getSessionPolicies();
      }
    }

    public void setSessionPolicies(MediaSession.Token token, int i) {
      long clearCallingIdentity = Binder.clearCallingIdentity();
      try {
        synchronized (MediaSessionService.this.mLock) {
          MediaSessionRecord mediaSessionRecordLocked =
              MediaSessionService.this.getMediaSessionRecordLocked(token);
          FullUserRecord fullUserRecordLocked =
              MediaSessionService.this.getFullUserRecordLocked(
                  mediaSessionRecordLocked.getUserId());
          if (fullUserRecordLocked != null) {
            mediaSessionRecordLocked.setSessionPolicies(i);
            fullUserRecordLocked.mPriorityStack.updateMediaButtonSessionBySessionPolicyChange(
                mediaSessionRecordLocked);
          }
        }
      } finally {
        Binder.restoreCallingIdentity(clearCallingIdentity);
      }
    }

    public final int verifySessionsRequest(ComponentName componentName, int i, int i2, int i3) {
      String str;
      if (componentName != null) {
        str = componentName.getPackageName();
        MediaServerUtils.enforcePackageName(str, i3);
      } else {
        str = null;
      }
      int handleIncomingUser = handleIncomingUser(i2, i3, i, str);
      MediaSessionService.this.enforceMediaPermissions(str, i2, i3, handleIncomingUser);
      return handleIncomingUser;
    }

    public final int handleIncomingUser(int i, int i2, int i3, String str) {
      int identifier = UserHandle.getUserHandleForUid(i2).getIdentifier();
      if (i3 == identifier) {
        return i3;
      }
      if (MediaSessionService.this.mContext.checkPermission(
              "android.permission.INTERACT_ACROSS_USERS_FULL", i, i2)
          == 0) {
        return i3 == UserHandle.CURRENT.getIdentifier() ? ActivityManager.getCurrentUser() : i3;
      }
      throw new SecurityException(
          "Permission denied while calling from "
              + str
              + " with user id: "
              + i3
              + "; Need to run as either the calling user id ("
              + identifier
              + "), or with android.permission.INTERACT_ACROSS_USERS_FULL permission");
    }

    public final boolean hasEnabledNotificationListener(int i, String str, int i2) {
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
              "Failed to check enabled notification listener. Package name and UID doesn't match");
          return false;
        }
        if (MediaSessionService.this.mNotificationManager.hasEnabledNotificationListener(
            str, UserHandle.getUserHandleForUid(i2))) {
          return true;
        }
        if (MediaSessionService.DEBUG) {
          Log.d(
              "MediaSessionService",
              str + " (uid=" + i2 + ") doesn't have an enabled notification listener");
        }
        return false;
      } catch (PackageManager.NameNotFoundException unused) {
        Log.w(
            "MediaSessionService",
            "Failed to check enabled notification listener. Package name doesn't exist");
        return false;
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
      MediaSessionRecordImpl defaultVolumeSession =
          MediaSessionService.this.isGlobalPriorityActiveLocked()
              ? MediaSessionService.this.mGlobalPrioritySession
              : MediaSessionService.this.mCurrentFullUserRecord.mPriorityStack
                  .getDefaultVolumeSession();
      boolean z3 = isValidLocalStreamType(i3) && AudioSystem.isStreamActive(i3, 0);
      if (defaultVolumeSession != null
          && "com.samsung.android.audiomirroring".equals(defaultVolumeSession.getPackageName())) {
        z3 = !MediaSessionService.this.isMirroringPackage(str) && z3;
      }
      if (defaultVolumeSession == null || z3) {
        Log.d(
            "MediaSessionService",
            "Adjusting suggestedStream="
                + i3
                + " by "
                + i4
                + ". flags="
                + i5
                + ", preferSuggestedStream="
                + z3
                + ", session="
                + defaultVolumeSession);
        if (z2 && !AudioSystem.isStreamActive(3, 0) && !AudioSystem.isStreamActive(0, 0)) {
          Log.d(
              "MediaSessionService",
              "Nothing is playing on the music stream. Skipping volume event, flags=" + i5);
          return;
        }
        MediaSessionService.this.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.media.MediaSessionService.SessionManagerImpl.3
              @Override // java.lang.Runnable
              public void run() {
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
                try {
                  MediaSessionService.this.mAudioManager.adjustSuggestedStreamVolumeForUid(
                      i3,
                      i4,
                      i5,
                      str3,
                      i6,
                      i7,
                      MediaSessionService.this.getContext().getApplicationInfo().targetSdkVersion);
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
        return;
      }
      Log.d(
          "MediaSessionService",
          "Adjusting "
              + defaultVolumeSession
              + " by "
              + i4
              + ". flags="
              + i5
              + ", suggestedStream="
              + i3
              + ", preferSuggestedStream="
              + z3);
      defaultVolumeSession.adjustVolume(str, str2, i, i2, z, i4, i5, true);
    }

    public final void dispatchMediaKeyEventLocked(
        String str, int i, int i2, boolean z, KeyEvent keyEvent, boolean z2) {
      String packageName;
      KeyEvent keyEvent2 = keyEvent;
      if (MediaSessionService.this.mCurrentFullUserRecord.getMediaButtonSessionLocked()
          instanceof MediaSession2Record) {
        return;
      }
      MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(MediaSessionService.this);
      MediaSessionRecord mediaSessionRecord =
          (MediaSessionRecord)
              MediaSessionService.this.mCurrentFullUserRecord.getMediaButtonSessionLocked();
      boolean z3 = false;
      if (MediaSessionService.this.isMultiSoundOn()) {
        mediaSessionRecord =
            MediaSessionService.this.getMultiSoundSessionLocked(keyEvent2, mediaSessionRecord);
        if (keyEvent.getFlags() == 536870912) {
          keyEvent2 = KeyEvent.changeFlags(keyEvent2, 0);
        }
      }
      MediaButtonReceiverHolder mediaButtonReceiverHolder =
          mediaSessionRecord == null
              ? MediaSessionService.this.mCurrentFullUserRecord.mLastMediaButtonReceiverHolder
              : null;
      if (mediaButtonReceiverHolder == null) {
        mediaButtonReceiverHolder =
            MediaSessionService.this.mCurrentFullUserRecord.mLastMediaButtonReceiverHolder;
      }
      MediaButtonReceiverHolder mediaButtonReceiverHolder2 = mediaButtonReceiverHolder;
      if (mediaSessionRecord != null
          && (mediaSessionRecord.isActive()
              || MediaSessionService.this.mHighPriorityMediaKeyReceiver == null)) {
        Log.d("MediaSessionService", "Sending " + keyEvent2 + " to " + mediaSessionRecord);
        if (FreecessController.getInstance().getFreecessEnabled()
            && FreecessController.getInstance()
                .isFreezedPackage(
                    mediaSessionRecord.getPackageName(), mediaSessionRecord.getUserId())) {
          FreecessController.getInstance()
              .unFreezePackage(
                  mediaSessionRecord.getPackageName(),
                  mediaSessionRecord.getUserId(),
                  "MediaKeyEvent");
        }
        if (z2) {
          this.mKeyEventReceiver.acquireWakeLockLocked();
        }
        mediaSessionRecord.sendMediaButton(
            str,
            i,
            i2,
            z,
            keyEvent2,
            z2 ? this.mKeyEventReceiver.mLastTimeoutId : -1,
            this.mKeyEventReceiver);
        try {
          Iterator it =
              MediaSessionService.this
                  .mCurrentFullUserRecord
                  .mOnMediaKeyEventDispatchedListeners
                  .values()
                  .iterator();
          while (it.hasNext()) {
            ((FullUserRecord.OnMediaKeyEventDispatchedListenerRecord) it.next())
                .callback.onMediaKeyEventDispatched(
                    keyEvent2,
                    mediaSessionRecord.getPackageName(),
                    mediaSessionRecord.getSessionToken());
          }
          return;
        } catch (RemoteException e) {
          Log.w("MediaSessionService", "Failed to send callback", e);
          return;
        }
      }
      if (mediaButtonReceiverHolder2 == null
          && MediaSessionService.this.mHighPriorityMediaKeyReceiver == null) {
        return;
      }
      if (z2) {
        this.mKeyEventReceiver.acquireWakeLockLocked();
      }
      String packageName2 = z ? MediaSessionService.this.mContext.getPackageName() : str;
      if (MediaSessionService.this.mHighPriorityMediaKeyReceiver != null) {
        Log.d(
            "MediaSessionService",
            "Sending "
                + keyEvent2
                + " to soundassistant "
                + MediaSessionService.this.mHighPriorityMediaKeyReceiver.getPackageName());
        Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent2);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", packageName2);
        intent.setPackage(MediaSessionService.this.mHighPriorityMediaKeyReceiver.getPackageName());
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(
            MediaSessionDeviceConfig.getMediaButtonReceiverFgsAllowlistDurationMs(),
            0,
            FrameworkStatsLog.f89x74364b3b,
            "");
        makeBasic.setBackgroundActivityStartsAllowed(true);
        MediaSessionService.this.getContext().sendBroadcast(intent, null, makeBasic.toBundle());
        z3 = true;
      } else if (mediaButtonReceiverHolder2 != null) {
        z3 =
            mediaButtonReceiverHolder2.send(
                MediaSessionService.this.mContext,
                keyEvent2,
                packageName2,
                z2 ? this.mKeyEventReceiver.mLastTimeoutId : -1,
                this.mKeyEventReceiver,
                MediaSessionService.this.mHandler,
                MediaSessionDeviceConfig.getMediaButtonReceiverFgsAllowlistDurationMs());
      }
      if (z3) {
        if (MediaSessionService.this.mHighPriorityMediaKeyReceiver != null) {
          packageName = MediaSessionService.this.mHighPriorityMediaKeyReceiver.getPackageName();
        } else {
          packageName = mediaButtonReceiverHolder2.getPackageName();
        }
        for (FullUserRecord.OnMediaKeyEventDispatchedListenerRecord
            onMediaKeyEventDispatchedListenerRecord :
                MediaSessionService.this.mCurrentFullUserRecord.mOnMediaKeyEventDispatchedListeners
                    .values()) {
          try {
            onMediaKeyEventDispatchedListenerRecord.callback.onMediaKeyEventDispatched(
                keyEvent2, packageName, (MediaSession.Token) null);
          } catch (RemoteException e2) {
            Log.w(
                "MediaSessionService",
                "Failed notify key event dispatch, uid="
                    + onMediaKeyEventDispatchedListenerRecord.uid,
                e2);
          }
        }
      }
    }

    public final void startVoiceInput(boolean z) {
      Intent intent;
      PowerManager powerManager =
          (PowerManager) MediaSessionService.this.mContext.getSystemService("power");
      boolean z2 =
          MediaSessionService.this.mKeyguardManager != null
              && MediaSessionService.this.mKeyguardManager.isKeyguardLocked();
      if (!z2 && powerManager.isScreenOn()) {
        intent = new Intent("android.speech.action.VOICE_SEARCH_HANDS_FREE");
        intent.putExtra(
            "android.speech.extras.EXTRA_SECURE",
            z2 && MediaSessionService.this.mKeyguardManager.isKeyguardSecure());
        Log.i(
            "MediaSessionService",
            "voice-based interactions: about to use ACTION_VOICE_SEARCH_HANDS_FREE");
      } else {
        intent = new Intent("android.speech.action.VOICE_SEARCH_HANDS_FREE");
        intent.putExtra(
            "android.speech.extras.EXTRA_SECURE",
            z2 && MediaSessionService.this.mKeyguardManager.isKeyguardSecure());
        Log.i(
            "MediaSessionService",
            "voice-based interactions: about to use ACTION_VOICE_SEARCH_HANDS_FREE");
      }
      if (z) {
        MediaSessionService.this.mMediaEventWakeLock.acquire();
      }
      try {
        try {
          intent.setFlags(276824064);
          if (MediaSessionService.DEBUG) {
            Log.d("MediaSessionService", "voiceIntent: " + intent);
          }
          MediaSessionService.this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
          if (!z) {
            return;
          }
        } catch (ActivityNotFoundException e) {
          Log.w("MediaSessionService", "No activity for search: " + e);
          if (!z) {
            return;
          }
        }
        MediaSessionService.this.mMediaEventWakeLock.release();
      } catch (Throwable th) {
        if (z) {
          MediaSessionService.this.mMediaEventWakeLock.release();
        }
        throw th;
      }
    }

    public final boolean isVoiceKey(int i) {
      return i == 79 || (!MediaSessionService.this.mHasFeatureLeanback && i == 85);
    }

    public final boolean isUserSetupComplete() {
      return Settings.Secure.getIntForUser(
              MediaSessionService.this.mContext.getContentResolver(),
              "user_setup_complete",
              0,
              UserHandle.CURRENT.getIdentifier())
          != 0;
    }

    public class MediaKeyListenerResultReceiver extends ResultReceiver implements Runnable {
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

      @Override // java.lang.Runnable
      public void run() {
        Log.d("MediaSessionService", "The media key listener is timed-out for " + this.mKeyEvent);
        dispatchMediaKeyEvent();
      }

      @Override // android.os.ResultReceiver
      public void onReceiveResult(int i, Bundle bundle) {
        if (i == 1) {
          this.mHandled = true;
          MediaSessionService.this.mHandler.removeCallbacks(this);
        } else {
          dispatchMediaKeyEvent();
        }
      }

      public final void dispatchMediaKeyEvent() {
        if (this.mHandled) {
          return;
        }
        this.mHandled = true;
        MediaSessionService.this.mHandler.removeCallbacks(this);
        synchronized (MediaSessionService.this.mLock) {
          if (MediaSessionService.this.isGlobalPriorityActiveLocked()) {
            SessionManagerImpl.this.dispatchMediaKeyEventLocked(
                this.mPackageName,
                this.mPid,
                this.mUid,
                this.mAsSystemService,
                this.mKeyEvent,
                this.mNeedWakeLock);
          } else {
            SessionManagerImpl.this.mMediaKeyEventHandler.handleMediaKeyEventLocked(
                this.mPackageName,
                this.mPid,
                this.mUid,
                this.mAsSystemService,
                this.mKeyEvent,
                this.mNeedWakeLock);
          }
        }
      }
    }

    public class KeyEventWakeLockReceiver extends ResultReceiver
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

      public void onTimeout() {
        synchronized (MediaSessionService.this.mLock) {
          if (this.mRefCount == 0) {
            return;
          }
          this.mLastTimeoutId++;
          this.mRefCount = 0;
          releaseWakeLockLocked();
        }
      }

      public void acquireWakeLockLocked() {
        if (this.mRefCount == 0) {
          MediaSessionService.this.mMediaEventWakeLock.acquire();
        }
        this.mRefCount++;
        this.mHandler.removeCallbacks(this);
        this.mHandler.postDelayed(this, 5000L);
      }

      @Override // java.lang.Runnable
      public void run() {
        onTimeout();
      }

      @Override // android.os.ResultReceiver
      public void onReceiveResult(int i, Bundle bundle) {
        if (i < this.mLastTimeoutId) {
          return;
        }
        synchronized (MediaSessionService.this.mLock) {
          int i2 = this.mRefCount;
          if (i2 > 0) {
            int i3 = i2 - 1;
            this.mRefCount = i3;
            if (i3 == 0) {
              releaseWakeLockLocked();
            }
          }
        }
      }

      public final void releaseWakeLockLocked() {
        MediaSessionService.this.mMediaEventWakeLock.release();
        this.mHandler.removeCallbacks(this);
      }

      @Override // android.app.PendingIntent.OnFinished
      public void onSendFinished(
          PendingIntent pendingIntent, Intent intent, int i, String str, Bundle bundle) {
        onReceiveResult(i, null);
      }
    }

    public class KeyEventHandler {
      public boolean mIsLongPressing;
      public int mKeyType;
      public Runnable mLongPressRunnableInBlackScreen = new Runnable() { // from class:
            // com.android.server.media.MediaSessionService$SessionManagerImpl$KeyEventHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              MediaSessionService.SessionManagerImpl.KeyEventHandler.this.lambda$new$0();
            }
          };
      public Runnable mLongPressTimeoutRunnable;
      public int mMultiTapCount;
      public int mMultiTapKeyCode;
      public Runnable mMultiTapTimeoutRunnable;
      public KeyEvent mTrackingFirstDownKeyEvent;

      public KeyEventHandler(int i) {
        this.mKeyType = i;
      }

      public void handleMediaKeyEventLocked(
          String str, int i, int i2, boolean z, KeyEvent keyEvent, boolean z2) {
        handleKeyEventLocked(str, i, i2, z, keyEvent, z2, null, 0, false);
      }

      public void handleVolumeKeyEventLocked(
          String str,
          int i,
          int i2,
          boolean z,
          KeyEvent keyEvent,
          String str2,
          int i3,
          boolean z2) {
        handleKeyEventLocked(str, i, i2, z, keyEvent, false, str2, i3, z2);
      }

      public void handleKeyEventLocked(
          String str,
          int i,
          int i2,
          boolean z,
          KeyEvent keyEvent,
          boolean z2,
          String str2,
          int i3,
          boolean z3) {
        if (keyEvent.isCanceled()) {
          return;
        }
        MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(MediaSessionService.this);
        cancelTrackingIfNeeded(str, i, i2, z, keyEvent, z2, str2, i3, z3, 0);
        if (!needTracking(keyEvent, 0)) {
          if (this.mKeyType == 1) {
            SessionManagerImpl.this.dispatchVolumeKeyEventLocked(
                str, str2, i, i2, z, keyEvent, i3, z3);
            return;
          } else {
            SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, keyEvent, z2);
            return;
          }
        }
        if (isFirstDownKeyEvent(keyEvent)) {
          this.mTrackingFirstDownKeyEvent = keyEvent;
          this.mIsLongPressing = false;
          if (this.mKeyType == 1) {
            MediaSessionService.this.mHandler.removeCallbacks(this.mLongPressRunnableInBlackScreen);
            MediaSessionService.this.mHandler.postDelayed(
                this.mLongPressRunnableInBlackScreen, MediaSessionService.LONG_PRESS_TIMEOUT);
            return;
          }
          return;
        }
        if (isFirstLongPressKeyEvent(keyEvent)) {
          this.mIsLongPressing = true;
          if (this.mKeyType == 1) {
            MediaSessionService.this.mHandler.removeCallbacks(this.mLongPressRunnableInBlackScreen);
          }
        }
        if (this.mIsLongPressing) {
          handleLongPressLocked(keyEvent, z2, 0);
          return;
        }
        if (keyEvent.getAction() == 1) {
          if (this.mKeyType == 1) {
            MediaSessionService.this.mHandler.removeCallbacks(this.mLongPressRunnableInBlackScreen);
          }
          this.mTrackingFirstDownKeyEvent = null;
          if (shouldTrackForMultipleTapsLocked(0)) {
            int i4 = this.mMultiTapCount;
            if (i4 == 0) {
              this.mMultiTapTimeoutRunnable =
                  createSingleTapRunnable(
                      str,
                      i,
                      i2,
                      z,
                      keyEvent,
                      z2,
                      str2,
                      i3,
                      z3,
                      MediaKeyDispatcher.isSingleTapOverridden(0));
              if (MediaKeyDispatcher.isSingleTapOverridden(0)
                  && !MediaKeyDispatcher.isDoubleTapOverridden(0)
                  && !MediaKeyDispatcher.isTripleTapOverridden(0)) {
                this.mMultiTapTimeoutRunnable.run();
                return;
              }
              MediaSessionService.this.mHandler.postDelayed(
                  this.mMultiTapTimeoutRunnable, MediaSessionService.MULTI_TAP_TIMEOUT);
              this.mMultiTapCount = 1;
              this.mMultiTapKeyCode = keyEvent.getKeyCode();
              return;
            }
            if (i4 != 1) {
              if (i4 == 2) {
                MediaSessionService.this.mHandler.removeCallbacks(this.mMultiTapTimeoutRunnable);
                onTripleTap(keyEvent);
                return;
              }
              return;
            }
            MediaSessionService.this.mHandler.removeCallbacks(this.mMultiTapTimeoutRunnable);
            this.mMultiTapTimeoutRunnable =
                createDoubleTapRunnable(
                    str,
                    i,
                    i2,
                    z,
                    keyEvent,
                    z2,
                    str2,
                    i3,
                    z3,
                    MediaKeyDispatcher.isSingleTapOverridden(0),
                    MediaKeyDispatcher.isDoubleTapOverridden(0));
            if (MediaKeyDispatcher.isTripleTapOverridden(0)) {
              MediaSessionService.this.mHandler.postDelayed(
                  this.mMultiTapTimeoutRunnable, MediaSessionService.MULTI_TAP_TIMEOUT);
              this.mMultiTapCount = 2;
              return;
            } else {
              this.mMultiTapTimeoutRunnable.run();
              return;
            }
          }
          dispatchDownAndUpKeyEventsLocked(str, i, i2, z, keyEvent, z2, str2, i3, z3);
        }
      }

      public final boolean shouldTrackForMultipleTapsLocked(int i) {
        return MediaKeyDispatcher.isSingleTapOverridden(i)
            || MediaKeyDispatcher.isDoubleTapOverridden(i)
            || MediaKeyDispatcher.isTripleTapOverridden(i);
      }

      public final void cancelTrackingIfNeeded(
          String str,
          int i,
          int i2,
          boolean z,
          KeyEvent keyEvent,
          boolean z2,
          String str2,
          int i3,
          boolean z3,
          int i4) {
        if (this.mTrackingFirstDownKeyEvent == null && this.mMultiTapTimeoutRunnable == null) {
          return;
        }
        if (isFirstDownKeyEvent(keyEvent)) {
          if (this.mLongPressTimeoutRunnable != null) {
            MediaSessionService.this.mHandler.removeCallbacks(this.mLongPressTimeoutRunnable);
            this.mLongPressTimeoutRunnable.run();
          }
          if (this.mMultiTapTimeoutRunnable != null
              && keyEvent.getKeyCode() != this.mMultiTapKeyCode) {
            runExistingMultiTapRunnableLocked();
          }
          resetLongPressTracking();
          return;
        }
        KeyEvent keyEvent2 = this.mTrackingFirstDownKeyEvent;
        if (keyEvent2 != null
            && keyEvent2.getDownTime() == keyEvent.getDownTime()
            && this.mTrackingFirstDownKeyEvent.getKeyCode() == keyEvent.getKeyCode()
            && keyEvent.getAction() == 0) {
          if (isFirstLongPressKeyEvent(keyEvent)) {
            if (this.mMultiTapTimeoutRunnable != null) {
              runExistingMultiTapRunnableLocked();
            }
            if ((i4 & 8) == 0) {
              if (this.mKeyType == 1) {
                if (MediaSessionService.this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener
                        != null
                    || MediaSessionService.this.needVolumeKeyLongPressBroadCastLocked()) {
                  return;
                }
                SessionManagerImpl.this.dispatchVolumeKeyEventLocked(
                    str, str2, i, i2, z, keyEvent, i3, z3);
                this.mTrackingFirstDownKeyEvent = null;
                return;
              }
              if (SessionManagerImpl.this.isVoiceKey(keyEvent.getKeyCode())) {
                return;
              }
              SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, keyEvent, z2);
              this.mTrackingFirstDownKeyEvent = null;
              return;
            }
            return;
          }
          if (keyEvent.getRepeatCount() <= 1 || this.mIsLongPressing) {
            return;
          }
          resetLongPressTracking();
        }
      }

      public final boolean needTracking(KeyEvent keyEvent, int i) {
        KeyEvent keyEvent2;
        if (!isFirstDownKeyEvent(keyEvent)
            && ((keyEvent2 = this.mTrackingFirstDownKeyEvent) == null
                || keyEvent2.getDownTime() != keyEvent.getDownTime()
                || this.mTrackingFirstDownKeyEvent.getKeyCode() != keyEvent.getKeyCode())) {
          return false;
        }
        if (i == 0) {
          if (this.mKeyType == 1) {
            if (MediaSessionService.this.mCurrentFullUserRecord.mOnVolumeKeyLongPressListener
                    == null
                && !MediaSessionService.this.needVolumeKeyLongPressBroadCastLocked()) {
              return false;
            }
          } else if (!SessionManagerImpl.this.isVoiceKey(keyEvent.getKeyCode())) {
            return false;
          }
        }
        return true;
      }

      public final void runExistingMultiTapRunnableLocked() {
        MediaSessionService.this.mHandler.removeCallbacks(this.mMultiTapTimeoutRunnable);
        this.mMultiTapTimeoutRunnable.run();
      }

      public final void resetMultiTapTrackingLocked() {
        this.mMultiTapCount = 0;
        this.mMultiTapTimeoutRunnable = null;
        this.mMultiTapKeyCode = 0;
      }

      public final void handleLongPressLocked(KeyEvent keyEvent, boolean z, int i) {
        MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(MediaSessionService.this);
        if (this.mKeyType == 1) {
          if (isFirstLongPressKeyEvent(keyEvent)) {
            MediaSessionService.this.dispatchVolumeKeyLongPressLocked(
                this.mTrackingFirstDownKeyEvent);
          }
          MediaSessionService.this.dispatchVolumeKeyLongPressLocked(keyEvent);
        } else if (isFirstLongPressKeyEvent(keyEvent)
            && SessionManagerImpl.this.isVoiceKey(keyEvent.getKeyCode())) {
          SessionManagerImpl.this.startVoiceInput(z);
        }
      }

      public final void resetLongPressTracking() {
        this.mTrackingFirstDownKeyEvent = null;
        this.mIsLongPressing = false;
        this.mLongPressTimeoutRunnable = null;
      }

      public final boolean isFirstLongPressKeyEvent(KeyEvent keyEvent) {
        return (keyEvent.getFlags() & 128) != 0 && keyEvent.getRepeatCount() == 1;
      }

      public final boolean isFirstDownKeyEvent(KeyEvent keyEvent) {
        return keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0;
      }

      public final void dispatchDownAndUpKeyEventsLocked(
          String str,
          int i,
          int i2,
          boolean z,
          KeyEvent keyEvent,
          boolean z2,
          String str2,
          int i3,
          boolean z3) {
        KeyEvent changeAction = KeyEvent.changeAction(keyEvent, 0);
        if (this.mKeyType == 1) {
          SessionManagerImpl.this.dispatchVolumeKeyEventLocked(
              str, str2, i, i2, z, changeAction, i3, z3);
          SessionManagerImpl.this.dispatchVolumeKeyEventLocked(
              str, str2, i, i2, z, keyEvent, i3, z3);
        } else {
          SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, changeAction, z2);
          SessionManagerImpl.this.dispatchMediaKeyEventLocked(str, i, i2, z, keyEvent, z2);
        }
      }

      public Runnable createSingleTapRunnable(
          final String str,
          final int i,
          final int i2,
          final boolean z,
          final KeyEvent keyEvent,
          final boolean z2,
          final String str2,
          final int i3,
          final boolean z3,
          final boolean z4) {
        return new Runnable() { // from class:
          // com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.2
          @Override // java.lang.Runnable
          public void run() {
            KeyEventHandler.this.resetMultiTapTrackingLocked();
            if (z4) {
              MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(
                  MediaSessionService.this);
              throw null;
            }
            KeyEventHandler.this.dispatchDownAndUpKeyEventsLocked(
                str, i, i2, z, keyEvent, z2, str2, i3, z3);
          }
        };
      }

      public Runnable createDoubleTapRunnable(
          final String str,
          final int i,
          final int i2,
          final boolean z,
          final KeyEvent keyEvent,
          final boolean z2,
          final String str2,
          final int i3,
          final boolean z3,
          final boolean z4,
          final boolean z5) {
        return new Runnable() { // from class:
          // com.android.server.media.MediaSessionService.SessionManagerImpl.KeyEventHandler.3
          @Override // java.lang.Runnable
          public void run() {
            KeyEventHandler.this.resetMultiTapTrackingLocked();
            if (z5) {
              MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(
                  MediaSessionService.this);
              throw null;
            }
            if (z4) {
              MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(
                  MediaSessionService.this);
              throw null;
            }
            KeyEventHandler.this.dispatchDownAndUpKeyEventsLocked(
                str, i, i2, z, keyEvent, z2, str2, i3, z3);
            KeyEventHandler.this.dispatchDownAndUpKeyEventsLocked(
                str, i, i2, z, keyEvent, z2, str2, i3, z3);
          }
        };
      }

      public final void onTripleTap(KeyEvent keyEvent) {
        resetMultiTapTrackingLocked();
        MediaSessionService.m8558$$Nest$fgetmCustomMediaKeyDispatcher(MediaSessionService.this);
        throw null;
      }

      /* JADX INFO: Access modifiers changed from: private */
      public /* synthetic */ void lambda$new$0() {
        synchronized (MediaSessionService.this.mLock) {
          if (this.mTrackingFirstDownKeyEvent != null
              && MediaSessionService.this.mVolumeKeyLongPressReceiver != null) {
            MediaSessionService.this.dispatchVolumeKeyLongPressLocked(
                this.mTrackingFirstDownKeyEvent);
            this.mIsLongPressing = true;
          }
        }
      }
    }

    public class VolumeKeyLongPressControlThread extends Thread {
      public int mDirection;
      public int mFlags;
      public boolean mNeedToRun;
      public String mOpPackageName;
      public String mPackageName;
      public PowerManager mPm;
      public int mSleepDuration;
      public int mStream;

      public VolumeKeyLongPressControlThread(String str, String str2) {
        this.mPm = (PowerManager) MediaSessionService.this.getContext().getSystemService("power");
        this.mPackageName = str;
        this.mOpPackageName = str2;
      }

      public void updateInfoLocked(int i, int i2, int i3, boolean z) {
        this.mStream = i;
        this.mDirection = i2;
        this.mSleepDuration = 500;
        this.mNeedToRun = z;
        this.mFlags = i3;
      }

      public final boolean isScreenOn() {
        if (!this.mPm.isInteractive()) {
          return false;
        }
        if ((MediaSessionService.this.mDesktopModeHelper == null
                || MediaSessionService.this.mDesktopModeHelper.isDesktopMode())
            && MediaSessionService.this.mPowerManagerInternal != null) {
          return !MediaSessionService.this.mPowerManagerInternal.isInternalDisplayOff();
        }
        return true;
      }

      /* JADX WARN: Code restructure failed: missing block: B:26:0x0072, code lost:

         return;
      */
      @Override // java.lang.Thread, java.lang.Runnable
      /*
          Code decompiled incorrectly, please refer to instructions dump.
      */
      public void run() {
        int i = 0;
        while (true) {
          try {
            Thread.sleep(this.mSleepDuration);
          } catch (Exception unused) {
          }
          synchronized (MediaSessionService.this.mLock) {
            if (isScreenOn()) {
              Log.d("MediaSessionService", "screen is on");
              return;
            }
            if (!this.mNeedToRun || i >= 200) {
              break;
            }
            i++;
            Log.d("MediaSessionService", "volumekey long press repeat:" + i);
            if (this.mSleepDuration == 500) {
              this.mSleepDuration = 50;
            }
            boolean z = (this.mFlags & 512) != 0;
            SessionManagerImpl sessionManagerImpl = SessionManagerImpl.this;
            sessionManagerImpl.dispatchAdjustVolumeLocked(
                this.mPackageName,
                this.mOpPackageName,
                MediaSessionService.this.mSystemServerPid,
                1000,
                true,
                this.mStream,
                this.mDirection,
                this.mFlags,
                z);
          }
        }
      }
    }

    public final void handleVolumeKeyLongPressLocked(
        String str, String str2, int i, int i2, int i3, int i4, boolean z) {
      if (i3 == 101 || i != MediaSessionService.this.mSystemServerPid) {
        z = false;
      }
      if (z) {
        VolumeKeyLongPressControlThread volumeKeyLongPressControlThread =
            this.mVolumeKeyLongPressControlThread;
        if (volumeKeyLongPressControlThread != null) {
          volumeKeyLongPressControlThread.updateInfoLocked(i2, i3, i4, false);
        }
        VolumeKeyLongPressControlThread volumeKeyLongPressControlThread2 =
            new VolumeKeyLongPressControlThread(str, str2);
        this.mVolumeKeyLongPressControlThread = volumeKeyLongPressControlThread2;
        volumeKeyLongPressControlThread2.updateInfoLocked(i2, i3, i4, z);
        this.mVolumeKeyLongPressControlThread.start();
        return;
      }
      VolumeKeyLongPressControlThread volumeKeyLongPressControlThread3 =
          this.mVolumeKeyLongPressControlThread;
      if (volumeKeyLongPressControlThread3 != null) {
        volumeKeyLongPressControlThread3.updateInfoLocked(i2, i3, i4, false);
        this.mVolumeKeyLongPressControlThread = null;
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
      this.mSkippedFirstKeyFromKeyCustomizer = false;
    }

    public final boolean isFirstLongPressKeyEvent(KeyEvent keyEvent) {
      return (keyEvent.getFlags() & 128) != 0 && keyEvent.getRepeatCount() == 1;
    }

    public final boolean needSkipKeyEventFromKeyCustomizer(
        String str, String str2, boolean z, KeyEvent keyEvent, int i, boolean z2) {
      if (isFromKeyCustomizer(keyEvent)) {
        this.mSkippedFirstKeyFromKeyCustomizer = true;
        MediaSessionService.this.mHandler.removeCallbacks(this.mKeyCustomizerRunnable);
        MediaSessionService.this.mHandler.postDelayed(
            this.mKeyCustomizerRunnable, MediaSessionService.LONG_PRESS_TIMEOUT);
        Log.i("MediaSessionService", "Skip key cause by keycustomizer:" + keyEvent);
        return true;
      }
      if (isFirstLongPressKeyEvent(keyEvent) && this.mSkippedFirstKeyFromKeyCustomizer) {
        KeyEvent changeTimeRepeat = KeyEvent.changeTimeRepeat(keyEvent, keyEvent.getEventTime(), 0);
        changeTimeRepeat.setFlags(keyEvent.getFlags() & (-268435585));
        dispatchVolumeKeyEvent(str, str2, z, changeTimeRepeat, i, z2);
      }
      return false;
    }

    public final boolean isFromKeyCustomizer(KeyEvent keyEvent) {
      return keyEvent.getAction() == 0
          && keyEvent.getRepeatCount() == 0
          && (keyEvent.getFlags() & 268435456) != 0
          && (keyEvent.getFlags() & 128) != 0;
    }
  }

  public final class MessageHandler extends Handler {
    public final SparseArray mIntegerCache = new SparseArray();

    public MessageHandler() {}

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      int i = message.what;
      if (i == 1) {
        MediaSessionService.this.pushSession1Changed(((Integer) message.obj).intValue());
      } else {
        if (i != 2) {
          return;
        }
        MediaSessionService.this.pushSession2Changed(((Integer) message.obj).intValue());
      }
    }

    public void postSessionsChanged(MediaSessionRecordImpl mediaSessionRecordImpl) {
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

  public final boolean isMultiSoundOn() {
    return this.mIsMultiSoundOn || this.mIsAppCastingOn;
  }

  public final void setMultiSoundFlag(MediaSessionRecord mediaSessionRecord) {
    if (this.mIsMultiSoundOn) {
      int appDevice = this.mAudioServiceInternal.getAppDevice(mediaSessionRecord.getUid());
      int flags = (int) mediaSessionRecord.getFlags();
      try {
        mediaSessionRecord
            .getSessionBinder()
            .setFlags(
                ((appDevice == 0 && this.mDevice == 128) || appDevice == 128)
                    ? 536870912 | flags
                    : (-536870913) & flags);
      } catch (RemoteException e) {
        Log.e("MediaSessionService", "Error setFlags", e);
      }
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:32:0x00a1, code lost:

     if (r7.getUid() != r8.getUid()) goto L66;
  */
  /* JADX WARN: Code restructure failed: missing block: B:35:0x00b1, code lost:

     if (r8.checkPlaybackActiveState(r11.mAudioPlayerStateMonitor.isPlaybackActive(r8.getUid())) == false) goto L67;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final MediaSessionRecord getMultiSoundSessionLocked(
      KeyEvent keyEvent, MediaSessionRecord mediaSessionRecord) {
    InputDevice device;
    if ((mediaSessionRecord != null && mediaSessionRecord.hasFlag(65536))
        || (device = InputDevice.getDevice(keyEvent.getDeviceId())) == null) {
      return mediaSessionRecord;
    }
    boolean equals = "Headset".equals(device.getName());
    boolean z = keyEvent.getFlags() == 536870912;
    int currentUserId = AudioUtils.getCurrentUserId();
    FullUserRecord fullUserRecordLocked = getFullUserRecordLocked(currentUserId);
    if (fullUserRecordLocked == null) {
      Log.w(
          "MediaSessionService",
          "getFullUserRecordLocked failed. No user with id=" + currentUserId);
      return mediaSessionRecord;
    }
    List<MediaSessionRecord> activeSessionsSortedPlayback =
        fullUserRecordLocked.mPriorityStack.getActiveSessionsSortedPlayback(currentUserId);
    MediaSessionRecord mediaSessionRecord2 = null;
    for (MediaSessionRecord mediaSessionRecord3 : activeSessionsSortedPlayback) {
      if (mediaSessionRecord3.hasFlag(65536)) {
        Log.w("MediaSessionService", "skip global session");
      } else {
        int appDevice = this.mAudioServiceInternal.getAppDevice(mediaSessionRecord3.getUid());
        if (appDevice == 0) {
          appDevice = this.mDevice;
        }
        if ((z && appDevice == 128)
            || ((equals && appDevice == 8) || (this.mIsAppCastingOn && appDevice != 32768))) {
          mediaSessionRecord2 = mediaSessionRecord3;
        }
      }
    }
    if (mediaSessionRecord2 == null) {
      return (mediaSessionRecord != null || activeSessionsSortedPlayback.size() == 0)
          ? mediaSessionRecord
          : (MediaSessionRecord) activeSessionsSortedPlayback.get(0);
    }
    Log.d(
        "MediaSessionService", "package " + mediaSessionRecord2.getPackageName() + " is selected");
    if (mediaSessionRecord != mediaSessionRecord2) {
      fullUserRecordLocked.mPriorityStack.updateMediaButtonSession(mediaSessionRecord2);
    }
    return mediaSessionRecord2;
  }

  public final class MediaSessionServiceInternal {
    public MediaSessionServiceInternal() {}

    public void updateMultiSoundInfo(int i, boolean z) {
      Log.d(
          "MediaSessionService",
          "updateMultiSoundInfo device:" + Integer.toHexString(i) + " on:" + z);
      if (i != -1) {
        MediaSessionService.this.mDevice = i;
      }
      MediaSessionService.this.mIsMultiSoundOn = z;
      MediaSessionService.this.mCurrentFullUserRecord.mPriorityStack.updateMultiSoundInfo(z);
    }

    public void setAppCastingState(boolean z, int i) {
      MediaSessionService mediaSessionService = MediaSessionService.this;
      mediaSessionService.mIsAppCastingOn = z;
      mediaSessionService.mAppCastingUid = i;
    }

    public void setVolumeLongPressReceiver(ComponentName componentName) {
      synchronized (MediaSessionService.this.mLock) {
        MediaSessionService.this.mVolumeKeyLongPressReceiver = componentName;
      }
    }

    public ComponentName getVolumeLongPressReceiver() {
      return MediaSessionService.this.mVolumeKeyLongPressReceiver;
    }

    public void setMediaKeyEventReceiver(ComponentName componentName) {
      synchronized (MediaSessionService.this.mLock) {
        MediaSessionService.this.mHighPriorityMediaKeyReceiver = componentName;
      }
    }

    public ComponentName getMediaKeyEventReceiver() {
      return MediaSessionService.this.mHighPriorityMediaKeyReceiver;
    }

    public boolean isAudioMirroring() {
      boolean z;
      synchronized (MediaSessionService.this.mLock) {
        MediaSessionRecordImpl volumeSessionLocked = getVolumeSessionLocked();
        z =
            volumeSessionLocked != null
                && "com.samsung.android.audiomirroring"
                    .equals(volumeSessionLocked.getPackageName());
      }
      return z;
    }

    public boolean adjustMirroringVolume(int i, int i2, String str, int i3, int i4) {
      synchronized (MediaSessionService.this.mLock) {
        MediaSessionRecordImpl volumeSessionLocked = getVolumeSessionLocked();
        if (volumeSessionLocked == null
            || !"com.samsung.android.audiomirroring".equals(volumeSessionLocked.getPackageName())
            || !MediaSessionService.this.isMirroringPackage(str)) {
          return false;
        }
        volumeSessionLocked.adjustVolume(str, str, i4, i3, false, i, i2, true);
        return true;
      }
    }

    public final MediaSessionRecordImpl getVolumeSessionLocked() {
      return MediaSessionService.this.isGlobalPriorityActiveLocked()
          ? MediaSessionService.this.mGlobalPrioritySession
          : MediaSessionService.this.mCurrentFullUserRecord.mPriorityStack
              .getDefaultVolumeSession();
    }
  }

  public final boolean isMediaButtonSessionUid(int i) {
    MediaSessionRecordImpl mediaButtonSessionLocked =
        this.mCurrentFullUserRecord.getMediaButtonSessionLocked();
    return mediaButtonSessionLocked != null && mediaButtonSessionLocked.getUid() == i;
  }

  public final boolean needVolumeKeyLongPressBroadCastLocked() {
    if (this.mVolumeKeyLongPressReceiver == null
        || this.mCurrentFullUserRecord.getMediaButtonSessionLocked() == null) {
      return false;
    }
    return !((PowerManager) getContext().getSystemService("power")).isInteractive()
        || this.mKeyguardManager.isKeyguardLocked();
  }

  public final void setupCustomRoutine() {
    LocalServices.addService(MediaSessionServiceInternal.class, new MediaSessionServiceInternal());
    this.mAudioServiceInternal =
        (SemAudioServiceInternal) LocalServices.getService(SemAudioServiceInternal.class);
    this.mSystemServerPid = Process.myPid();
    this.mDesktopModeHelper = DesktopModeHelper.getInstance(this.mContext);
    this.mPowerManagerInternal =
        (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
  }

  public final boolean isMirroringPackage(String str) {
    try {
      Bundle call =
          this.mContext
              .getContentResolver()
              .call(
                  Uri.parse("content://com.samsung.android.audiomirroring"),
                  "request_package_name",
                  (String) null,
                  (Bundle) null);
      if (call == null || !call.getString("mediaPackageName", "").equals(str)) {
        return false;
      }
      Log.d("MediaSessionService", "audiomirroring package: " + str);
      return true;
    } catch (IllegalArgumentException e) {
      Log.d("MediaSessionService", e.getMessage());
      return false;
    }
  }
}
