package com.android.server.voiceinteraction;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.admin.DevicePolicyManagerInternal;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.hardware.soundtrigger.KeyphraseMetadata;
import android.hardware.soundtrigger.SoundTrigger;
import android.media.AudioFormat;
import android.media.permission.Identity;
import android.media.permission.PermissionUtil;
import android.media.permission.SafeCloseable;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SharedMemory;
import android.os.ShellCallback;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.voice.IDetectorSessionVisualQueryDetectionCallback;
import android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback;
import android.service.voice.ISandboxedDetectionService;
import android.service.voice.IVisualQueryDetectionVoiceInteractionCallback;
import android.service.voice.IVoiceInteractionService;
import android.service.voice.IVoiceInteractionSession;
import android.service.voice.VisualQueryAttentionResult;
import android.service.voice.VisualQueryDetectedResult;
import android.service.voice.VisualQueryDetectionServiceFailure;
import android.service.voice.VoiceInteractionManagerInternal;
import android.service.voice.VoiceInteractionServiceInfo;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.window.ScreenCapture;
import com.android.internal.app.IHotwordRecognitionStatusCallback;
import com.android.internal.app.IVisualQueryDetectionAttentionListener;
import com.android.internal.app.IVisualQueryRecognitionStatusListener;
import com.android.internal.app.IVoiceActionCheckCallback;
import com.android.internal.app.IVoiceInteractionAccessibilitySettingsListener;
import com.android.internal.app.IVoiceInteractionManagerService;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;
import com.android.internal.app.IVoiceInteractionSoundTriggerSession;
import com.android.internal.app.IVoiceInteractor;
import com.android.internal.content.PackageMonitor;
import com.android.internal.infra.ServiceConnector;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.UiThread;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy;
import com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider;
import com.android.server.pm.permission.LegacyPermissionManagerService;
import com.android.server.policy.AppOpsPolicy;
import com.android.server.soundtrigger.SoundTriggerEvent$ServiceEvent;
import com.android.server.soundtrigger.SoundTriggerEvent$SessionEvent$Type;
import com.android.server.soundtrigger.SoundTriggerHelper;
import com.android.server.soundtrigger.SoundTriggerService;
import com.android.server.utils.Slogf;
import com.android.server.voiceinteraction.HotwordDetectionConnection;
import com.android.server.voiceinteraction.VisualQueryDetectorSession;
import com.android.server.voiceinteraction.VoiceInteractionManagerService;
import com.android.server.wm.ActivityAssistInfo;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VoiceInteractionManagerService extends SystemService {
    public final ActivityManagerInternal mAmInternal;
    public final ActivityTaskManagerInternal mAtmInternal;
    public final Context mContext;
    public IEnrolledModelDb mDbHelper;
    public final DevicePolicyManagerInternal mDpmInternal;
    public final AnonymousClass3 mLatencyLoggingListener;
    public final ArrayMap mLoadedKeyphraseIds;
    public final DatabaseHelper mRealDbHelper;
    public final ContentResolver mResolver;
    public final VoiceInteractionManagerServiceStub mServiceStub;
    public ShortcutServiceInternal mShortcutServiceInternal;
    public SoundTriggerService.LocalSoundTriggerService mSoundTriggerInternal;
    public final UserManagerInternal mUserManagerInternal;
    public IVisualQueryRecognitionStatusListener mVisualQueryRecognitionStatusListener;
    public final RemoteCallbackList mVoiceInteractionSessionListeners;
    public final WindowManagerInternal mWmInternal;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService extends VoiceInteractionManagerInternal {
        public LocalService() {
        }

        public final VoiceInteractionManagerInternal.HotwordDetectionServiceIdentity getHotwordDetectionServiceIdentity() {
            HotwordDetectionConnection hotwordDetectionConnection;
            VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = VoiceInteractionManagerService.this.mServiceStub.mImpl;
            if (voiceInteractionManagerServiceImpl == null || (hotwordDetectionConnection = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection) == null) {
                return null;
            }
            return hotwordDetectionConnection.mIdentity;
        }

        public final String getVoiceInteractorPackageName(IBinder iBinder) {
            VoiceInteractionSessionConnection voiceInteractionSessionConnection;
            IVoiceInteractor iVoiceInteractor;
            VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = VoiceInteractionManagerService.this.mServiceStub.mImpl;
            if (voiceInteractionManagerServiceImpl == null || (voiceInteractionSessionConnection = voiceInteractionManagerServiceImpl.mActiveSession) == null || (iVoiceInteractor = voiceInteractionSessionConnection.mInteractor) == null || iVoiceInteractor.asBinder() != iBinder) {
                return null;
            }
            return voiceInteractionSessionConnection.mSessionComponentName.getPackageName();
        }

        public final boolean hasActiveSession(String str) {
            VoiceInteractionSessionConnection voiceInteractionSessionConnection;
            VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = VoiceInteractionManagerService.this.mServiceStub.mImpl;
            if (voiceInteractionManagerServiceImpl == null || (voiceInteractionSessionConnection = voiceInteractionManagerServiceImpl.mActiveSession) == null) {
                return false;
            }
            return TextUtils.equals(str, voiceInteractionSessionConnection.mSessionComponentName.getPackageName());
        }

        public final void onPreCreatedUserConversion(int i) {
            Slogf.d("VoiceInteractionManager", "onPreCreatedUserConversion(%d): calling onRoleHoldersChanged() again", Integer.valueOf(i));
            VoiceInteractionManagerService.this.mServiceStub.mRoleObserver.onRoleHoldersChanged("android.app.role.ASSISTANT", UserHandle.of(i));
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x007d A[Catch: all -> 0x0092, TryCatch #0 {, blocks: (B:18:0x0050, B:20:0x0054, B:21:0x0090, B:24:0x005a, B:26:0x0067, B:29:0x006e, B:32:0x007d, B:33:0x0083, B:34:0x0072), top: B:17:0x0050 }] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x0083 A[Catch: all -> 0x0092, TryCatch #0 {, blocks: (B:18:0x0050, B:20:0x0054, B:21:0x0090, B:24:0x005a, B:26:0x0067, B:29:0x006e, B:32:0x007d, B:33:0x0083, B:34:0x0072), top: B:17:0x0050 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void startListeningFromWearable(android.os.ParcelFileDescriptor r8, android.media.AudioFormat r9, android.os.PersistableBundle r10, android.content.ComponentName r11, int r12, android.service.voice.VoiceInteractionManagerInternal.WearableHotwordDetectionCallback r13) {
            /*
                r7 = this;
                java.lang.String r0 = "VoiceInteractionManager"
                java.lang.String r1 = "#startListeningFromWearable"
                android.util.Slog.d(r0, r1)
                com.android.server.voiceinteraction.VoiceInteractionManagerService r0 = com.android.server.voiceinteraction.VoiceInteractionManagerService.this
                com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub r0 = r0.mServiceStub
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl r0 = r0.mImpl
                if (r0 != 0) goto L15
                java.lang.String r7 = "Unable to start listening from wearable because the service impl is null."
                r13.onError(r7)
                return
            L15:
                if (r11 == 0) goto L2f
                android.content.ComponentName r1 = r0.mComponent
                boolean r1 = r11.equals(r1)
                if (r1 != 0) goto L2f
                java.lang.String r7 = "Unable to start listening from wearable because the target VoiceInteractionService %s is different from the current VoiceInteractionService %s"
                android.content.ComponentName r8 = r0.mComponent
                java.lang.Object[] r8 = new java.lang.Object[]{r11, r8}
                java.lang.String r7 = android.text.TextUtils.formatSimple(r7, r8)
                r13.onError(r7)
                return
            L2f:
                int r11 = r0.mUser
                if (r12 == r11) goto L4b
                java.lang.String r7 = "Unable to start listening from wearable because the target userId %s is different from the current VoiceInteractionManagerServiceImpl's userId %s"
                java.lang.Integer r8 = java.lang.Integer.valueOf(r12)
                int r9 = r0.mUser
                java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
                java.lang.Object[] r8 = new java.lang.Object[]{r8, r9}
                java.lang.String r7 = android.text.TextUtils.formatSimple(r7, r8)
                r13.onError(r7)
                return
            L4b:
                com.android.server.voiceinteraction.VoiceInteractionManagerService r7 = com.android.server.voiceinteraction.VoiceInteractionManagerService.this
                com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub r7 = r7.mServiceStub
                monitor-enter(r7)
                com.android.server.voiceinteraction.HotwordDetectionConnection r11 = r0.mHotwordDetectionConnection     // Catch: java.lang.Throwable -> L92
                if (r11 != 0) goto L5a
                java.lang.String r8 = "Unable to start listening from wearable because the hotword detection connection is null."
                r13.onError(r8)     // Catch: java.lang.Throwable -> L92
                goto L90
            L5a:
                com.android.server.voiceinteraction.HotwordDetectionConnection r11 = r0.mHotwordDetectionConnection     // Catch: java.lang.Throwable -> L92
                android.util.SparseArray r11 = r11.mDetectorSessions     // Catch: java.lang.Throwable -> L92
                r12 = 1
                java.lang.Object r11 = r11.get(r12)     // Catch: java.lang.Throwable -> L92
                com.android.server.voiceinteraction.DetectorSession r11 = (com.android.server.voiceinteraction.DetectorSession) r11     // Catch: java.lang.Throwable -> L92
                if (r11 == 0) goto L72
                boolean r12 = r11.isDestroyed()     // Catch: java.lang.Throwable -> L92
                if (r12 == 0) goto L6e
                goto L72
            L6e:
                com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession r11 = (com.android.server.voiceinteraction.DspTrustedHotwordDetectorSession) r11     // Catch: java.lang.Throwable -> L92
            L70:
                r0 = r11
                goto L7b
            L72:
                java.lang.String r11 = "HotwordDetectionConnection"
                java.lang.String r12 = "Not found the Dsp detector"
                android.util.Slog.v(r11, r12)     // Catch: java.lang.Throwable -> L92
                r11 = 0
                goto L70
            L7b:
                if (r0 != 0) goto L83
                java.lang.String r8 = "Unable to start listening from wearable because the trusted hotword detection session is not available."
                r13.onError(r8)     // Catch: java.lang.Throwable -> L92
                goto L90
            L83:
                com.android.server.voiceinteraction.DetectorSession$2 r4 = new com.android.server.voiceinteraction.DetectorSession$2     // Catch: java.lang.Throwable -> L92
                r4.<init>()     // Catch: java.lang.Throwable -> L92
                r5 = 0
                r6 = 0
                r1 = r8
                r2 = r9
                r3 = r10
                r0.handleExternalSourceHotwordDetectionLocked(r1, r2, r3, r4, r5, r6)     // Catch: java.lang.Throwable -> L92
            L90:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L92
                return
            L92:
                r8 = move-exception
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L92
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.voiceinteraction.VoiceInteractionManagerService.LocalService.startListeningFromWearable(android.os.ParcelFileDescriptor, android.media.AudioFormat, android.os.PersistableBundle, android.content.ComponentName, int, android.service.voice.VoiceInteractionManagerInternal$WearableHotwordDetectionCallback):void");
        }

        public final void startLocalVoiceInteraction(final IBinder iBinder, String str, Bundle bundle) {
            final VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerService.this.mServiceStub;
            if (voiceInteractionManagerServiceStub.mImpl == null) {
                return;
            }
            final int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                LatencyTracker.getInstance(VoiceInteractionManagerService.this.mContext).onActionCancel(19);
                voiceInteractionManagerServiceStub.mImpl.showSessionLocked(bundle, 16, str, new IVoiceInteractionSessionShowCallback.Stub() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.1
                    public final void onFailed() {
                    }

                    public final void onShown() {
                        synchronized (voiceInteractionManagerServiceStub) {
                            try {
                                if (voiceInteractionManagerServiceStub.mImpl != null) {
                                    voiceInteractionManagerServiceStub.mImpl.grantImplicitAccessLocked(callingUid, null);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub2 = voiceInteractionManagerServiceStub;
                        ActivityTaskManagerInternal activityTaskManagerInternal = VoiceInteractionManagerService.this.mAtmInternal;
                        IBinder iBinder2 = iBinder;
                        IVoiceInteractionSession iVoiceInteractionSession = voiceInteractionManagerServiceStub2.mImpl.mActiveSession.mSession;
                        IVoiceInteractor iVoiceInteractor = voiceInteractionManagerServiceStub.mImpl.mActiveSession.mInteractor;
                        ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
                        WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
                        WindowManagerService.boostPriorityForLockedSection();
                        synchronized (windowManagerGlobalLock) {
                            try {
                                ActivityTaskManagerService.m1053$$Nest$monLocalVoiceInteractionStartedLocked(ActivityTaskManagerService.this, iBinder2, iVoiceInteractionSession, iVoiceInteractor);
                            } catch (Throwable th2) {
                                WindowManagerService.resetPriorityAfterLockedSection();
                                throw th2;
                            }
                        }
                        WindowManagerService.resetPriorityAfterLockedSection();
                    }
                }, iBinder);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void stopLocalVoiceInteraction(IBinder iBinder) {
            VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerService.this.mServiceStub;
            if (voiceInteractionManagerServiceStub.mImpl == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                voiceInteractionManagerServiceStub.mImpl.finishLocked(iBinder, true);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean supportsLocalVoiceInteraction() {
            VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerService.this.mServiceStub;
            if (voiceInteractionManagerServiceStub.mImpl == null) {
                return false;
            }
            return voiceInteractionManagerServiceStub.mImpl.mInfo.getSupportsLocalInteraction();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VoiceInteractionManagerServiceStub extends IVoiceInteractionManagerService.Stub {
        public final boolean IS_HDS_REQUIRED;
        public int mCurUser;
        public boolean mCurUserSupported;
        public final boolean mEnableService;
        public volatile VoiceInteractionManagerServiceImpl mImpl;
        public final RoleObserver mRoleObserver;
        public boolean mSafeMode;
        public boolean mTemporarilyDisabled;
        public int mShowSessionId = 0;
        public final AnonymousClass2 mPackageMonitor = new PackageMonitor() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.2
            public final boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
                boolean z2;
                boolean z3;
                int userId = UserHandle.getUserId(i);
                ComponentName curInteractor = VoiceInteractionManagerServiceStub.this.getCurInteractor(userId);
                ComponentName curRecognizer = VoiceInteractionManagerServiceStub.this.getCurRecognizer(userId);
                int length = strArr.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        z2 = false;
                        z3 = false;
                        break;
                    }
                    String str = strArr[i2];
                    if (curInteractor != null && str.equals(curInteractor.getPackageName())) {
                        z3 = false;
                        z2 = true;
                        break;
                    }
                    if (curRecognizer != null && str.equals(curRecognizer.getPackageName())) {
                        z2 = false;
                        z3 = true;
                        break;
                    }
                    i2++;
                }
                if (z2 && z) {
                    synchronized (VoiceInteractionManagerServiceStub.this) {
                        try {
                            Slog.i("VoiceInteractionManager", "Force stopping current voice interactor: " + VoiceInteractionManagerServiceStub.this.getCurInteractor(userId));
                            VoiceInteractionManagerServiceStub.this.unloadAllKeyphraseModels();
                            if (VoiceInteractionManagerServiceStub.this.mImpl != null) {
                                VoiceInteractionManagerServiceStub.this.mImpl.shutdownLocked();
                                VoiceInteractionManagerServiceStub.this.setImplLocked(null);
                            }
                            VoiceInteractionManagerServiceStub.this.switchImplementationIfNeededLocked(true);
                        } finally {
                        }
                    }
                } else if (z3 && z) {
                    synchronized (VoiceInteractionManagerServiceStub.this) {
                        Slog.i("VoiceInteractionManager", "Force stopping current voice recognizer: " + VoiceInteractionManagerServiceStub.this.getCurRecognizer(userId));
                        VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerServiceStub.this;
                        ComponentName findAvailRecognizer = voiceInteractionManagerServiceStub.findAvailRecognizer(userId, null);
                        if (findAvailRecognizer != null) {
                            voiceInteractionManagerServiceStub.setCurRecognizer(userId, findAvailRecognizer);
                        }
                    }
                }
                return z2 || z3;
            }

            public final void onPackageModified(String str) {
                VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub;
                int i;
                ComponentName findAvailRecognizer;
                if (VoiceInteractionManagerServiceStub.this.mCurUser == getChangingUserId() && isPackageAppearing(str) == 0) {
                    VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub2 = VoiceInteractionManagerServiceStub.this;
                    if (voiceInteractionManagerServiceStub2.getCurRecognizer(voiceInteractionManagerServiceStub2.mCurUser) == null && (findAvailRecognizer = voiceInteractionManagerServiceStub.findAvailRecognizer((i = (voiceInteractionManagerServiceStub = VoiceInteractionManagerServiceStub.this).mCurUser), null)) != null) {
                        voiceInteractionManagerServiceStub.setCurRecognizer(i, findAvailRecognizer);
                    }
                    String stringForUser = Settings.Secure.getStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", VoiceInteractionManagerServiceStub.this.mCurUser);
                    VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub3 = VoiceInteractionManagerServiceStub.this;
                    ComponentName curInteractor = voiceInteractionManagerServiceStub3.getCurInteractor(voiceInteractionManagerServiceStub3.mCurUser);
                    if (curInteractor == null && !"".equals(stringForUser)) {
                        VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub4 = VoiceInteractionManagerServiceStub.this;
                        VoiceInteractionServiceInfo findAvailInteractor = voiceInteractionManagerServiceStub4.findAvailInteractor(voiceInteractionManagerServiceStub4.mCurUser, str);
                        if (findAvailInteractor != null) {
                            ComponentName componentName = new ComponentName(findAvailInteractor.getServiceInfo().packageName, findAvailInteractor.getServiceInfo().name);
                            VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub5 = VoiceInteractionManagerServiceStub.this;
                            voiceInteractionManagerServiceStub5.setCurInteractor(voiceInteractionManagerServiceStub5.mCurUser, componentName);
                            return;
                        }
                        return;
                    }
                    if (didSomePackagesChange()) {
                        if (curInteractor == null || !str.equals(curInteractor.getPackageName())) {
                            return;
                        }
                        VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub6 = VoiceInteractionManagerServiceStub.this;
                        synchronized (voiceInteractionManagerServiceStub6) {
                            voiceInteractionManagerServiceStub6.switchImplementationIfNeededLocked(true);
                        }
                        return;
                    }
                    if (curInteractor == null || !isComponentModified(curInteractor.getClassName())) {
                        return;
                    }
                    VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub7 = VoiceInteractionManagerServiceStub.this;
                    synchronized (voiceInteractionManagerServiceStub7) {
                        voiceInteractionManagerServiceStub7.switchImplementationIfNeededLocked(true);
                    }
                }
            }

            public final void onSomePackagesChanged() {
                VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub;
                ComponentName findAvailRecognizer;
                int changingUserId = getChangingUserId();
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    try {
                        ComponentName curInteractor = VoiceInteractionManagerServiceStub.this.getCurInteractor(changingUserId);
                        ComponentName curRecognizer = VoiceInteractionManagerServiceStub.this.getCurRecognizer(changingUserId);
                        String stringForUser = Settings.Secure.getStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "assistant", changingUserId);
                        ComponentName unflattenFromString = TextUtils.isEmpty(stringForUser) ? null : ComponentName.unflattenFromString(stringForUser);
                        if (curRecognizer == null && anyPackagesAppearing() && (findAvailRecognizer = (voiceInteractionManagerServiceStub = VoiceInteractionManagerServiceStub.this).findAvailRecognizer(changingUserId, null)) != null) {
                            voiceInteractionManagerServiceStub.setCurRecognizer(changingUserId, findAvailRecognizer);
                        }
                        if (curInteractor != null) {
                            if (isPackageDisappearing(curInteractor.getPackageName()) == 3) {
                                VoiceInteractionManagerServiceStub.this.setCurInteractor(changingUserId, null);
                                VoiceInteractionManagerServiceStub.this.setCurRecognizer(changingUserId, null);
                                Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "assistant", null, changingUserId);
                                VoiceInteractionManagerServiceStub.this.initForUser(changingUserId);
                                return;
                            }
                            if (isPackageAppearing(curInteractor.getPackageName()) != 0) {
                                VoiceInteractionManagerServiceStub.m1034$$Nest$mresetServicesIfNoRecognitionService(VoiceInteractionManagerServiceStub.this, curInteractor, changingUserId);
                                if (VoiceInteractionManagerServiceStub.this.mImpl != null && curInteractor.getPackageName().equals(VoiceInteractionManagerServiceStub.this.mImpl.mComponent.getPackageName())) {
                                    VoiceInteractionManagerServiceStub.this.switchImplementationIfNeededLocked(true);
                                }
                            }
                            return;
                        }
                        if (unflattenFromString != null) {
                            if (isPackageDisappearing(unflattenFromString.getPackageName()) == 3) {
                                VoiceInteractionManagerServiceStub.this.setCurInteractor(changingUserId, null);
                                VoiceInteractionManagerServiceStub.this.setCurRecognizer(changingUserId, null);
                                Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "assistant", null, changingUserId);
                                VoiceInteractionManagerServiceStub.this.initForUser(changingUserId);
                                return;
                            }
                            if (isPackageAppearing(unflattenFromString.getPackageName()) != 0) {
                                VoiceInteractionManagerServiceStub.m1034$$Nest$mresetServicesIfNoRecognitionService(VoiceInteractionManagerServiceStub.this, unflattenFromString, changingUserId);
                            }
                        }
                        if (curRecognizer != null) {
                            int isPackageDisappearing = isPackageDisappearing(curRecognizer.getPackageName());
                            if (isPackageDisappearing != 3 && isPackageDisappearing != 2) {
                                if (isPackageModified(curRecognizer.getPackageName())) {
                                    VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub2 = VoiceInteractionManagerServiceStub.this;
                                    voiceInteractionManagerServiceStub2.setCurRecognizer(changingUserId, voiceInteractionManagerServiceStub2.findAvailRecognizer(changingUserId, curRecognizer.getPackageName()));
                                }
                            }
                            VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub3 = VoiceInteractionManagerServiceStub.this;
                            voiceInteractionManagerServiceStub3.setCurRecognizer(changingUserId, voiceInteractionManagerServiceStub3.findAvailRecognizer(changingUserId, null));
                        }
                    } finally {
                    }
                }
            }
        };

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class RoleObserver implements OnRoleHoldersChangedListener {
            public final PackageManager mPm;
            public final RoleManager mRm;

            public RoleObserver(Executor executor) {
                this.mPm = VoiceInteractionManagerService.this.mContext.getPackageManager();
                RoleManager roleManager = (RoleManager) VoiceInteractionManagerService.this.mContext.getSystemService(RoleManager.class);
                this.mRm = roleManager;
                roleManager.addOnRoleHoldersChangedListenerAsUser(executor, this, UserHandle.ALL);
                if (roleManager.isRoleAvailable("android.app.role.ASSISTANT")) {
                    onRoleHoldersChanged("android.app.role.ASSISTANT", UserHandle.of(((ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class)).getCurrentUserId()));
                }
            }

            public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                UserInfo userInfo;
                if (str.equals("android.app.role.ASSISTANT")) {
                    List roleHoldersAsUser = this.mRm.getRoleHoldersAsUser(str, userHandle);
                    if (roleHoldersAsUser.isEmpty() && (userInfo = VoiceInteractionManagerService.this.mUserManagerInternal.getUserInfo(userHandle.getIdentifier())) != null && userInfo.preCreated) {
                        Slogf.d("VoiceInteractionManager", "onRoleHoldersChanged(): ignoring pre-created user %s for now, this method will be called again when it's converted to a real user", userInfo.toFullString());
                        return;
                    }
                    int identifier = userHandle.getIdentifier();
                    String str2 = "";
                    if (roleHoldersAsUser.isEmpty()) {
                        Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.getContext().getContentResolver(), "assistant", "", identifier);
                        Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.getContext().getContentResolver(), "voice_interaction_service", "", identifier);
                        return;
                    }
                    String str3 = (String) roleHoldersAsUser.get(0);
                    Iterator it = VoiceInteractionManagerServiceStub.this.queryInteractorServices(identifier, str3).iterator();
                    while (it.hasNext()) {
                        ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                        VoiceInteractionServiceInfo voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(this.mPm, serviceInfo);
                        if (voiceInteractionServiceInfo.getSupportsAssist()) {
                            String flattenToShortString = serviceInfo.getComponentName().flattenToShortString();
                            if (voiceInteractionServiceInfo.getRecognitionService() == null) {
                                Slog.e("VoiceInteractionManager", "The RecognitionService must be set to avoid boot loop on earlier platform version. Also make sure that this is a valid RecognitionService when running on Android 11 or earlier.");
                            } else {
                                str2 = flattenToShortString;
                            }
                            Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.getContext().getContentResolver(), "assistant", str2, identifier);
                            Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.getContext().getContentResolver(), "voice_interaction_service", str2, identifier);
                            return;
                        }
                    }
                    Iterator it2 = this.mPm.queryIntentActivitiesAsUser(new Intent("android.intent.action.ASSIST").setPackage(str3), 851968, identifier).iterator();
                    if (it2.hasNext()) {
                        Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.getContext().getContentResolver(), "assistant", ((ResolveInfo) it2.next()).activityInfo.getComponentName().flattenToShortString(), identifier);
                        Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.getContext().getContentResolver(), "voice_interaction_service", "", identifier);
                    }
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class SettingsObserver extends ContentObserver {
            public SettingsObserver(Handler handler) {
                super(handler);
                VoiceInteractionManagerService.this.mContext.getContentResolver().registerContentObserver(Settings.Secure.getUriFor("voice_interaction_service"), false, this, -1);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    VoiceInteractionManagerServiceStub.this.switchImplementationIfNeededLocked(false);
                }
            }
        }

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class SoundTriggerSession extends IVoiceInteractionSoundTriggerSession.Stub {
            public final SoundTriggerService.LocalSoundTriggerService.SessionImpl mSession;
            public IHotwordRecognitionStatusCallback mSessionExternalCallback;
            public HotwordDetectionConnection.SoundTriggerCallback mSessionInternalCallback;
            public final Identity mVoiceInteractorIdentity;

            /* renamed from: -$$Nest$munloadKeyphraseModel, reason: not valid java name */
            public static int m1035$$Nest$munloadKeyphraseModel(SoundTriggerSession soundTriggerSession, int i) {
                soundTriggerSession.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SoundTriggerService.LocalSoundTriggerService.SessionImpl sessionImpl = soundTriggerSession.mSession;
                    sessionImpl.mEventLogger.enqueue(new SoundTriggerEvent$ServiceEvent(SoundTriggerEvent$SessionEvent$Type.UNLOAD_MODEL, (UUID) sessionImpl.mModelUuid.get(i), (String) null));
                    return sessionImpl.mSoundTriggerHelper.unloadKeyphraseSoundModel(i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public SoundTriggerSession(SoundTriggerService.LocalSoundTriggerService.SessionImpl sessionImpl, Identity identity) {
                this.mSession = sessionImpl;
                this.mVoiceInteractorIdentity = identity;
            }

            public final void detach() {
                this.mSession.detachInternal();
            }

            public final SoundTrigger.ModuleProperties getDspModuleProperties() {
                SoundTrigger.ModuleProperties moduleProperties;
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    try {
                        VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            SoundTriggerService.LocalSoundTriggerService.SessionImpl sessionImpl = this.mSession;
                            sessionImpl.getClass();
                            sessionImpl.mEventLogger.enqueue(new SoundTriggerEvent$ServiceEvent(SoundTriggerEvent$SessionEvent$Type.GET_MODULE_PROPERTIES, (UUID) null, (String) null));
                            moduleProperties = sessionImpl.mSoundTriggerHelper.getModuleProperties();
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return moduleProperties;
            }

            public final int getParameter(int i, int i2) {
                int parameterLocked;
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SoundTriggerHelper soundTriggerHelper = this.mSession.mSoundTriggerHelper;
                    synchronized (soundTriggerHelper.mLock) {
                        if (soundTriggerHelper.mIsDetached) {
                            throw new IllegalStateException("SoundTriggerHelper has been detached");
                        }
                        parameterLocked = soundTriggerHelper.getParameterLocked(soundTriggerHelper.getKeyphraseModelDataLocked(i), i2);
                    }
                    return parameterLocked;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final SoundTrigger.ModelParamRange queryParameter(int i, int i2) {
                SoundTrigger.ModelParamRange queryParameterLocked;
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SoundTriggerHelper soundTriggerHelper = this.mSession.mSoundTriggerHelper;
                    synchronized (soundTriggerHelper.mLock) {
                        if (soundTriggerHelper.mIsDetached) {
                            throw new IllegalStateException("SoundTriggerHelper has been detached");
                        }
                        queryParameterLocked = soundTriggerHelper.queryParameterLocked(soundTriggerHelper.getKeyphraseModelDataLocked(i), i2);
                    }
                    return queryParameterLocked;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final int setParameter(int i, int i2, int i3) {
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return this.mSession.setParameter(i, i2, i3);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final int startRecognition(int i, String str, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig, boolean z) {
                HotwordDetectionConnection.SoundTriggerCallback soundTriggerCallback;
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    try {
                        VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                        if (iHotwordRecognitionStatusCallback == null || recognitionConfig == null || str == null) {
                            throw new IllegalArgumentException("Illegal argument(s) in startRecognition");
                        }
                        if (z) {
                            VoiceInteractionManagerServiceStub.this.enforceCallingPermission("android.permission.SOUND_TRIGGER_RUN_IN_BATTERY_SAVER");
                        }
                    } finally {
                    }
                }
                int callingUserId = UserHandle.getCallingUserId();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(i, callingUserId, str);
                    if (keyphraseSoundModel != null && keyphraseSoundModel.getUuid() != null && keyphraseSoundModel.getKeyphrases() != null) {
                        synchronized (VoiceInteractionManagerServiceStub.this) {
                            try {
                                VoiceInteractionManagerService.this.mLoadedKeyphraseIds.put(Integer.valueOf(i), this);
                                if (this.mSessionExternalCallback != null) {
                                    if (this.mSessionInternalCallback != null) {
                                        if (iHotwordRecognitionStatusCallback.asBinder() != this.mSessionExternalCallback.asBinder()) {
                                        }
                                    }
                                }
                                VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerServiceStub.this;
                                Identity identity = this.mVoiceInteractorIdentity;
                                if (voiceInteractionManagerServiceStub.mImpl == null) {
                                    soundTriggerCallback = null;
                                } else {
                                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = voiceInteractionManagerServiceStub.mImpl;
                                    Context context = VoiceInteractionManagerService.this.mContext;
                                    voiceInteractionManagerServiceImpl.getClass();
                                    soundTriggerCallback = new HotwordDetectionConnection.SoundTriggerCallback(context, iHotwordRecognitionStatusCallback, voiceInteractionManagerServiceImpl.mHotwordDetectionConnection, identity);
                                }
                                this.mSessionInternalCallback = soundTriggerCallback;
                                this.mSessionExternalCallback = iHotwordRecognitionStatusCallback;
                            } finally {
                            }
                        }
                        return this.mSession.startRecognition(i, keyphraseSoundModel, this.mSessionInternalCallback, recognitionConfig, z);
                    }
                    Slog.w("VoiceInteractionManager", "No matching sound model found in startRecognition");
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return Integer.MIN_VALUE;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final int stopRecognition(int i, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
                HotwordDetectionConnection.SoundTriggerCallback soundTriggerCallback;
                int stopKeyphraseRecognition;
                synchronized (VoiceInteractionManagerServiceStub.this) {
                    try {
                        VoiceInteractionManagerServiceStub.this.enforceIsCurrentVoiceInteractionService();
                        if (this.mSessionExternalCallback != null && this.mSessionInternalCallback != null && iHotwordRecognitionStatusCallback.asBinder() == this.mSessionExternalCallback.asBinder()) {
                            soundTriggerCallback = this.mSessionInternalCallback;
                            this.mSessionExternalCallback = null;
                            this.mSessionInternalCallback = null;
                        }
                        VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerServiceStub.this;
                        Identity identity = this.mVoiceInteractorIdentity;
                        if (voiceInteractionManagerServiceStub.mImpl == null) {
                            soundTriggerCallback = null;
                        } else {
                            VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = voiceInteractionManagerServiceStub.mImpl;
                            Context context = VoiceInteractionManagerService.this.mContext;
                            voiceInteractionManagerServiceImpl.getClass();
                            soundTriggerCallback = new HotwordDetectionConnection.SoundTriggerCallback(context, iHotwordRecognitionStatusCallback, voiceInteractionManagerServiceImpl.mHotwordDetectionConnection, identity);
                        }
                        Slog.w("VoiceInteractionManager", "stopRecognition() called with a different callback thanstartRecognition()");
                        this.mSessionExternalCallback = null;
                        this.mSessionInternalCallback = null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    SoundTriggerService.LocalSoundTriggerService.SessionImpl sessionImpl = this.mSession;
                    synchronized (sessionImpl) {
                        sessionImpl.mEventLogger.enqueue(new SoundTriggerEvent$ServiceEvent(SoundTriggerEvent$SessionEvent$Type.STOP_RECOGNITION, (UUID) sessionImpl.mModelUuid.get(i), (String) null));
                        stopKeyphraseRecognition = sessionImpl.mSoundTriggerHelper.stopKeyphraseRecognition(i, soundTriggerCallback);
                    }
                    return stopKeyphraseRecognition;
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* renamed from: -$$Nest$mresetServicesIfNoRecognitionService, reason: not valid java name */
        public static void m1034$$Nest$mresetServicesIfNoRecognitionService(VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub, ComponentName componentName, int i) {
            voiceInteractionManagerServiceStub.getClass();
            Iterator it = voiceInteractionManagerServiceStub.queryInteractorServices(i, componentName.getPackageName()).iterator();
            while (it.hasNext()) {
                VoiceInteractionServiceInfo voiceInteractionServiceInfo = new VoiceInteractionServiceInfo(VoiceInteractionManagerService.this.mContext.getPackageManager(), ((ResolveInfo) it.next()).serviceInfo);
                if (voiceInteractionServiceInfo.getSupportsAssist() && voiceInteractionServiceInfo.getRecognitionService() == null) {
                    Slog.e("VoiceInteractionManager", "The RecognitionService must be set to avoid boot loop on earlier platform version. Also make sure that this is a valid RecognitionService when running on Android 11 or earlier.");
                    voiceInteractionManagerServiceStub.setCurInteractor(i, null);
                    Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "assistant", null, i);
                }
            }
        }

        /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$2] */
        public VoiceInteractionManagerServiceStub() {
            this.IS_HDS_REQUIRED = AppOpsPolicy.isHotwordDetectionServiceRequired(VoiceInteractionManagerService.this.mContext.getPackageManager());
            Context context = VoiceInteractionManagerService.this.mContext;
            String string = context.getResources().getString(R.string.duration_hours_shortest_future);
            this.mEnableService = (TextUtils.isEmpty(string) ? null : string) != null ? true : context.getPackageManager().hasSystemFeature("android.software.voice_recognizers");
            this.mRoleObserver = new RoleObserver(VoiceInteractionManagerService.this.mContext.getMainExecutor());
        }

        public final boolean activeServiceSupportsAssist() {
            boolean z;
            activeServiceSupportsAssist_enforcePermission();
            synchronized (this) {
                try {
                    z = (this.mImpl == null || this.mImpl.mInfo == null || !this.mImpl.mInfo.getSupportsAssist()) ? false : true;
                } finally {
                }
            }
            return z;
        }

        public final boolean activeServiceSupportsLaunchFromKeyguard() {
            boolean z;
            activeServiceSupportsLaunchFromKeyguard_enforcePermission();
            synchronized (this) {
                try {
                    z = (this.mImpl == null || this.mImpl.mInfo == null || !this.mImpl.mInfo.getSupportsLaunchFromKeyguard()) ? false : true;
                } finally {
                }
            }
            return z;
        }

        public final void closeSystemDialogs(IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "closeSystemDialogs without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        voiceInteractionManagerServiceImpl.getClass();
                        try {
                            VoiceInteractionSessionConnection voiceInteractionSessionConnection = voiceInteractionManagerServiceImpl.mActiveSession;
                            if (voiceInteractionSessionConnection != null && iBinder == voiceInteractionSessionConnection.mToken) {
                                voiceInteractionManagerServiceImpl.mAm.closeSystemDialogs("voiceinteraction");
                            }
                            Slog.w("VoiceInteractionServiceManager", "closeSystemDialogs does not match active session");
                        } catch (RemoteException e) {
                            throw new IllegalStateException("Unexpected remote error", e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final IVoiceInteractionSoundTriggerSession createSoundTriggerSessionAsOriginator(Identity identity, IBinder iBinder, SoundTrigger.ModuleProperties moduleProperties) {
            boolean z;
            boolean z2;
            Objects.requireNonNull(identity);
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    z = true;
                    z2 = (this.mImpl == null || this.mImpl.mHotwordDetectionConnection == null) ? false : true;
                } finally {
                }
            }
            SafeCloseable establishIdentityDirect = PermissionUtil.establishIdentityDirect(identity);
            try {
                if (this.IS_HDS_REQUIRED) {
                    z = z2;
                }
                SoundTriggerSession soundTriggerSession = new SoundTriggerSession(VoiceInteractionManagerService.this.mSoundTriggerInternal.attach(iBinder, moduleProperties, z), identity);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return soundTriggerSession;
            } catch (Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final int deleteKeyphraseSoundModel(int i, String str) {
            int m1035$$Nest$munloadKeyphraseModel;
            enforceCallerAllowedToEnrollVoiceModel();
            if (str == null) {
                throw new IllegalArgumentException("Illegal argument(s) in deleteKeyphraseSoundModel");
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SoundTriggerSession soundTriggerSession = (SoundTriggerSession) VoiceInteractionManagerService.this.mLoadedKeyphraseIds.get(Integer.valueOf(i));
                if (soundTriggerSession != null && (m1035$$Nest$munloadKeyphraseModel = SoundTriggerSession.m1035$$Nest$munloadKeyphraseModel(soundTriggerSession, i)) != 0) {
                    Slog.w("VoiceInteractionManager", "Unable to unload keyphrase sound model:" + m1035$$Nest$munloadKeyphraseModel);
                }
                boolean deleteKeyphraseSoundModel = VoiceInteractionManagerService.this.mDbHelper.deleteKeyphraseSoundModel(i, callingUserId, str);
                int i2 = deleteKeyphraseSoundModel ? 0 : Integer.MIN_VALUE;
                if (deleteKeyphraseSoundModel) {
                    synchronized (this) {
                        try {
                            if (this.mImpl != null && this.mImpl.mService != null) {
                                this.mImpl.notifySoundModelsChangedLocked();
                            }
                            VoiceInteractionManagerService.this.mLoadedKeyphraseIds.remove(Integer.valueOf(i));
                        } finally {
                        }
                    }
                }
                return i2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean deliverNewSession(IBinder iBinder, IVoiceInteractionSession iVoiceInteractionSession, IVoiceInteractor iVoiceInteractor) {
            boolean deliverNewSessionLocked;
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        throw new SecurityException("deliverNewSession without running voice interaction service");
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        deliverNewSessionLocked = this.mImpl.deliverNewSessionLocked(iBinder, iVoiceInteractionSession, iVoiceInteractor);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return deliverNewSessionLocked;
        }

        public final void destroyDetector(IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "destroyDetector without running voice interaction service");
                    } else {
                        Binder.withCleanCallingIdentity(new VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda3(0, iBinder, this));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void disableVisualQueryDetection() {
            VisualQueryDetectorSession visualQueryDetectorSessionLocked;
            disableVisualQueryDetection_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "disableVisualQueryDetection without running voice interaction service");
                        return;
                    }
                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                    if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection != null && (visualQueryDetectorSessionLocked = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.getVisualQueryDetectorSessionLocked()) != null) {
                        visualQueryDetectorSessionLocked.mAttentionListener = null;
                    }
                } finally {
                }
            }
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(VoiceInteractionManagerService.this.mContext, "VoiceInteractionManager", printWriter)) {
                synchronized (this) {
                    try {
                        printWriter.println("VOICE INTERACTION MANAGER (dumpsys voiceinteraction)");
                        printWriter.println("  mEnableService: " + this.mEnableService);
                        printWriter.println("  mTemporarilyDisabled: " + this.mTemporarilyDisabled);
                        printWriter.println("  mCurUser: " + this.mCurUser);
                        printWriter.println("  mCurUserSupported: " + this.mCurUserSupported);
                        printWriter.println("  mIsHdsRequired: " + this.IS_HDS_REQUIRED);
                        VoiceInteractionManagerService.this.dumpSupportedUsers(printWriter, "  ");
                        VoiceInteractionManagerService.this.mDbHelper.dump(printWriter);
                        if (this.mImpl == null) {
                            printWriter.println("  (No active implementation)");
                        } else {
                            this.mImpl.dumpLocked(printWriter);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void enableVisualQueryDetection(IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) {
            VisualQueryDetectorSession visualQueryDetectorSessionLocked;
            enableVisualQueryDetection_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "enableVisualQueryDetection without running voice interaction service");
                        return;
                    }
                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                    if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection != null && (visualQueryDetectorSessionLocked = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.getVisualQueryDetectorSessionLocked()) != null) {
                        visualQueryDetectorSessionLocked.mAttentionListener = iVisualQueryDetectionAttentionListener;
                    }
                } finally {
                }
            }
        }

        public final void enforceCallerAllowedToEnrollVoiceModel() {
            if (VoiceInteractionManagerService.this.mContext.checkCallingOrSelfPermission("android.permission.KEYPHRASE_ENROLLMENT_APPLICATION") == 0) {
                return;
            }
            enforceCallingPermission("android.permission.MANAGE_VOICE_KEYPHRASES");
            enforceIsCurrentVoiceInteractionService();
        }

        public final void enforceCallingPermission(String str) {
            if (VoiceInteractionManagerService.this.mContext.checkCallingOrSelfPermission(str) != 0) {
                throw new SecurityException("Caller does not hold the permission ".concat(str));
            }
        }

        public final void enforceIsCurrentVoiceInteractionService() {
            if (this.mImpl == null || this.mImpl.mInfo.getServiceInfo().applicationInfo.uid != Binder.getCallingUid()) {
                throw new SecurityException("Caller is not the current voice interaction service");
            }
        }

        public final VoiceInteractionServiceInfo findAvailInteractor(int i, String str) {
            List queryInteractorServices = queryInteractorServices(i, str);
            int size = queryInteractorServices.size();
            VoiceInteractionServiceInfo voiceInteractionServiceInfo = null;
            if (size == 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "no available voice interaction services found for user ", "VoiceInteractionManager");
                return null;
            }
            for (int i2 = 0; i2 < size; i2++) {
                ServiceInfo serviceInfo = ((ResolveInfo) queryInteractorServices.get(i2)).serviceInfo;
                if ((serviceInfo.applicationInfo.flags & 1) != 0) {
                    VoiceInteractionServiceInfo voiceInteractionServiceInfo2 = new VoiceInteractionServiceInfo(VoiceInteractionManagerService.this.mContext.getPackageManager(), serviceInfo);
                    if (voiceInteractionServiceInfo2.getParseError() != null) {
                        Slog.w("VoiceInteractionManager", "Bad interaction service " + serviceInfo.packageName + "/" + serviceInfo.name + ": " + voiceInteractionServiceInfo2.getParseError());
                    } else if (voiceInteractionServiceInfo == null) {
                        voiceInteractionServiceInfo = voiceInteractionServiceInfo2;
                    } else {
                        Slog.w("VoiceInteractionManager", "More than one voice interaction service, picking first " + new ComponentName(voiceInteractionServiceInfo.getServiceInfo().packageName, voiceInteractionServiceInfo.getServiceInfo().name) + " over " + new ComponentName(serviceInfo.packageName, serviceInfo.name));
                    }
                }
            }
            return voiceInteractionServiceInfo;
        }

        public final ComponentName findAvailRecognizer(int i, String str) {
            if (str == null) {
                str = VoiceInteractionManagerService.this.mContext.getString(R.string.config_systemSpeechRecognizer);
                if (TextUtils.isEmpty(str)) {
                    str = null;
                }
            }
            Context context = VoiceInteractionManagerService.this.mContext;
            ArrayList arrayList = new ArrayList();
            Iterator it = context.getPackageManager().queryIntentServicesAsUser(new Intent("android.speech.RecognitionService"), 786432, i).iterator();
            while (it.hasNext()) {
                RecognitionServiceInfo parseInfo = RecognitionServiceInfo.parseInfo(context.getPackageManager(), ((ResolveInfo) it.next()).serviceInfo);
                String str2 = parseInfo.mParseError;
                if (!TextUtils.isEmpty(str2)) {
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Parse error in getAvailableServices: ", str2, "RecognitionServiceInfo");
                }
                arrayList.add(parseInfo);
            }
            if (arrayList.size() == 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(i, "no available voice recognition services found for user ", "VoiceInteractionManager");
                return null;
            }
            ArrayList arrayList2 = new ArrayList();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (!((RecognitionServiceInfo) arrayList.get(size)).mSelectableAsDefault) {
                    arrayList2.add((RecognitionServiceInfo) arrayList.remove(size));
                }
            }
            if (arrayList.size() == 0) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(i, "No selectableAsDefault recognition services found for user ", ". Falling back to non selectableAsDefault ones.", "VoiceInteractionManager");
                arrayList = arrayList2;
            }
            int size2 = arrayList.size();
            if (str != null) {
                for (int i2 = 0; i2 < size2; i2++) {
                    ServiceInfo serviceInfo = ((RecognitionServiceInfo) arrayList.get(i2)).mServiceInfo;
                    if (str.equals(serviceInfo.packageName)) {
                        return new ComponentName(serviceInfo.packageName, serviceInfo.name);
                    }
                }
            }
            if (size2 > 1) {
                Slog.w("VoiceInteractionManager", "more than one voice recognition service found, picking first");
            }
            ServiceInfo serviceInfo2 = ((RecognitionServiceInfo) arrayList.get(0)).mServiceInfo;
            return new ComponentName(serviceInfo2.packageName, serviceInfo2.name);
        }

        public final void finish(IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "finish without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mImpl.finishLocked(iBinder, false);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void forceRestartHotwordDetector() {
            VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
            if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection == null) {
                Slog.w("VoiceInteractionServiceManager", "Failed to force-restart hotword detection: no hotword detection active");
                return;
            }
            HotwordDetectionConnection hotwordDetectionConnection = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection;
            hotwordDetectionConnection.getClass();
            Slog.v("HotwordDetectionConnection", "Requested to restart the service internally. Performing the restart");
            synchronized (hotwordDetectionConnection.mLock) {
                hotwordDetectionConnection.restartProcessLocked();
            }
        }

        public final boolean getAccessibilityDetectionEnabled() {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "registerAccessibilityDetectionSettingsListener called without running voice interaction service");
                        return false;
                    }
                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                    return Settings.Secure.getIntForUser(voiceInteractionManagerServiceImpl.mContext.getContentResolver(), "visual_query_accessibility_detection_enabled", 0, voiceInteractionManagerServiceImpl.mUser) == 1;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final ComponentName getActiveServiceComponentName() {
            ComponentName componentName;
            synchronized (this) {
                try {
                    componentName = this.mImpl != null ? this.mImpl.mComponent : null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return componentName;
        }

        public final void getActiveServiceSupportedActions(List list, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
            getActiveServiceSupportedActions_enforcePermission();
            synchronized (this) {
                if (this.mImpl == null) {
                    try {
                        iVoiceActionCheckCallback.onComplete((List) null);
                    } catch (RemoteException unused) {
                    }
                    return;
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                    IVoiceInteractionService iVoiceInteractionService = voiceInteractionManagerServiceImpl.mService;
                    if (iVoiceInteractionService == null) {
                        Slog.w("VoiceInteractionServiceManager", "Not bound to voice interaction service " + voiceInteractionManagerServiceImpl.mComponent);
                        try {
                            iVoiceActionCheckCallback.onComplete((List) null);
                        } catch (RemoteException unused2) {
                        }
                    } else {
                        try {
                            iVoiceInteractionService.getActiveServiceSupportedActions(list, iVoiceActionCheckCallback);
                        } catch (RemoteException e) {
                            Slog.w("VoiceInteractionServiceManager", "RemoteException while calling getActiveServiceSupportedActions", e);
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        public final Intent getContextualSearchIntent(Bundle bundle) {
            Intent m;
            ResolveInfo resolveActivity;
            String string = VoiceInteractionManagerService.this.mContext.getResources().getString(R.string.date_picker_prev_month_button);
            if (string.isEmpty() || (resolveActivity = VoiceInteractionManagerService.this.mContext.getPackageManager().resolveActivity((m = ExplicitHealthCheckController$$ExternalSyntheticOutline0.m("com.android.contextualsearch.LAUNCH", string)), 2097152)) == null) {
                return null;
            }
            m.setComponent(resolveActivity.getComponentInfo().getComponentName());
            m.addFlags(268795904);
            m.putExtras(bundle);
            boolean isAssistDataAllowed = ActivityTaskManagerService.this.isAssistDataAllowed();
            List topVisibleActivities = VoiceInteractionManagerService.this.mAtmInternal.getTopVisibleActivities();
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) topVisibleActivities).iterator();
            boolean z = false;
            while (it.hasNext()) {
                ActivityAssistInfo activityAssistInfo = (ActivityAssistInfo) it.next();
                if (isAssistDataAllowed) {
                    arrayList.add(activityAssistInfo.mComponentName.getPackageName());
                }
                DevicePolicyManagerInternal devicePolicyManagerInternal = VoiceInteractionManagerService.this.mDpmInternal;
                if (devicePolicyManagerInternal != null && devicePolicyManagerInternal.isUserOrganizationManaged(activityAssistInfo.mUserId)) {
                    z = true;
                }
            }
            ScreenCapture.ScreenshotHardwareBuffer takeAssistScreenshot = VoiceInteractionManagerService.this.mWmInternal.takeAssistScreenshot(Set.of());
            Bitmap asBitmap = takeAssistScreenshot != null ? takeAssistScreenshot.asBitmap() : null;
            if (asBitmap != null) {
                m.putExtra("com.android.contextualsearch.flag_secure_found", takeAssistScreenshot.containsSecureLayers());
                if (isAssistDataAllowed) {
                    m.putExtra("com.android.contextualsearch.screenshot", asBitmap.asShared());
                }
            }
            m.putExtra("com.android.contextualsearch.is_managed_profile_visible", z);
            if (isAssistDataAllowed) {
                m.putExtra("com.android.contextualsearch.visible_package_names", arrayList);
            }
            return m;
        }

        public final ComponentName getCurInteractor(int i) {
            String stringForUser = Settings.Secure.getStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", i);
            if (TextUtils.isEmpty(stringForUser)) {
                return null;
            }
            return ComponentName.unflattenFromString(stringForUser);
        }

        public final ComponentName getCurRecognizer(int i) {
            String stringForUser = Settings.Secure.getStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_recognition_service", i);
            if (TextUtils.isEmpty(stringForUser)) {
                return null;
            }
            return ComponentName.unflattenFromString(stringForUser);
        }

        public final int getDisabledShowContext() {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "getDisabledShowContext without running voice interaction service");
                        return 0;
                    }
                    int callingUid = Binder.getCallingUid();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        int i = voiceInteractionManagerServiceImpl.mInfo.getServiceInfo().applicationInfo.uid;
                        if (callingUid != i) {
                            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, i, "Calling uid ", " does not match active uid "));
                        }
                        return voiceInteractionManagerServiceImpl.mDisabledShowContext;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final KeyphraseMetadata getEnrolledKeyphraseMetadata(String str, String str2) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
            }
            if (str2 == null) {
                throw new IllegalArgumentException("Illegal argument(s) in isEnrolledForKeyphrase");
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SoundTrigger.KeyphraseSoundModel keyphraseSoundModel = VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(callingUserId, str, str2);
                if (keyphraseSoundModel == null) {
                    return null;
                }
                for (SoundTrigger.Keyphrase keyphrase : keyphraseSoundModel.getKeyphrases()) {
                    if (str.equals(keyphrase.getText())) {
                        ArraySet arraySet = new ArraySet();
                        arraySet.add(keyphrase.getLocale());
                        return new KeyphraseMetadata(keyphrase.getId(), keyphrase.getText(), arraySet, keyphrase.getRecognitionModes());
                    }
                }
                return null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final SoundTrigger.KeyphraseSoundModel getKeyphraseSoundModel(int i, String str) {
            enforceCallerAllowedToEnrollVoiceModel();
            if (str == null) {
                throw new IllegalArgumentException("Illegal argument(s) in getKeyphraseSoundModel");
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(i, callingUserId, str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getUserDisabledShowContext() {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "getUserDisabledShowContext without running voice interaction service");
                        return 0;
                    }
                    int callingUid = Binder.getCallingUid();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        int i = voiceInteractionManagerServiceImpl.mInfo.getServiceInfo().applicationInfo.uid;
                        if (callingUid != i) {
                            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, i, "Calling uid ", " does not match active uid "));
                        }
                        VoiceInteractionSessionConnection voiceInteractionSessionConnection = voiceInteractionManagerServiceImpl.mActiveSession;
                        return voiceInteractionSessionConnection != null ? voiceInteractionSessionConnection.getUserDisabledShowContextLocked() : 0;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void hideCurrentSession() {
            hideCurrentSession_enforcePermission();
            if (this.mImpl == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (this.mImpl.mActiveSession != null && this.mImpl.mActiveSession.mSession != null) {
                    try {
                        this.mImpl.mActiveSession.mSession.closeSystemDialogs();
                    } catch (RemoteException e) {
                        Log.w("VoiceInteractionManager", "Failed to call closeSystemDialogs", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean hideSessionFromSession(IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "hideSessionFromSession without running voice interaction service");
                        return false;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mImpl.mActiveSession;
                        return voiceInteractionSessionConnection != null ? voiceInteractionSessionConnection.hideLocked() : false;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void initAndVerifyDetector(final Identity identity, final PersistableBundle persistableBundle, final SharedMemory sharedMemory, final IBinder iBinder, final IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback, final int i) {
            initAndVerifyDetector_enforcePermission();
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
                identity.uid = Binder.getCallingUid();
                identity.pid = Binder.getCallingPid();
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda5
                    /* JADX WARN: Multi-variable type inference failed */
                    public final void runOrThrow() {
                        IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback2;
                        IBinder iBinder2;
                        int i2;
                        IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback3;
                        SoftwareTrustedHotwordDetectorSession softwareTrustedHotwordDetectorSession;
                        VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this;
                        Identity identity2 = identity;
                        PersistableBundle persistableBundle2 = persistableBundle;
                        SharedMemory sharedMemory2 = sharedMemory;
                        IBinder iBinder3 = iBinder;
                        IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback4 = iHotwordRecognitionStatusCallback;
                        int i3 = i;
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = voiceInteractionManagerServiceStub.mImpl;
                        voiceInteractionManagerServiceImpl.getClass();
                        if (i3 != 3) {
                            Slog.v("VoiceInteractionServiceManager", "verifyDetectorForHotwordDetectionLocked");
                            int i4 = voiceInteractionManagerServiceImpl.mInfo.getServiceInfo().applicationInfo.uid;
                            ComponentName componentName = voiceInteractionManagerServiceImpl.mHotwordDetectionComponentName;
                            if (componentName == null) {
                                Slog.w("VoiceInteractionServiceManager", "Hotword detection service name not found");
                                VoiceInteractionManagerServiceImpl.logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback4, i3, false, i4);
                                throw new IllegalStateException("Hotword detection service name not found");
                            }
                            ServiceInfo serviceInfoLocked = VoiceInteractionManagerServiceImpl.getServiceInfoLocked(voiceInteractionManagerServiceImpl.mUser, componentName);
                            if (serviceInfoLocked == null) {
                                Slog.w("VoiceInteractionServiceManager", "Hotword detection service info not found");
                                VoiceInteractionManagerServiceImpl.logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback4, i3, false, i4);
                                throw new IllegalStateException("Hotword detection service info not found");
                            }
                            int i5 = serviceInfoLocked.flags;
                            if ((i5 & 2) == 0 || (i5 & 4) != 0) {
                                Slog.w("VoiceInteractionServiceManager", "Hotword detection service not in isolated process");
                                VoiceInteractionManagerServiceImpl.logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback4, i3, false, i4);
                                throw new IllegalStateException("Hotword detection service not in isolated process");
                            }
                            if (!"android.permission.BIND_HOTWORD_DETECTION_SERVICE".equals(serviceInfoLocked.permission)) {
                                Slog.w("VoiceInteractionServiceManager", "Hotword detection service does not require permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
                                VoiceInteractionManagerServiceImpl.logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback4, i3, false, i4);
                                throw new SecurityException("Hotword detection service does not require permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
                            }
                            if (voiceInteractionManagerServiceImpl.mContext.getPackageManager().checkPermission("android.permission.BIND_HOTWORD_DETECTION_SERVICE", voiceInteractionManagerServiceImpl.mInfo.getServiceInfo().packageName) == 0) {
                                Slog.w("VoiceInteractionServiceManager", "Voice interaction service should not hold permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
                                VoiceInteractionManagerServiceImpl.logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback4, i3, false, i4);
                                throw new SecurityException("Voice interaction service should not hold permission android.permission.BIND_HOTWORD_DETECTION_SERVICE");
                            }
                            if (sharedMemory2 != null && !sharedMemory2.setProtect(OsConstants.PROT_READ)) {
                                Slog.w("VoiceInteractionServiceManager", "Can't set sharedMemory to be read-only");
                                VoiceInteractionManagerServiceImpl.logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback4, i3, false, i4);
                                throw new IllegalStateException("Can't set sharedMemory to be read-only");
                            }
                            VoiceInteractionManagerServiceImpl.logDetectorCreateEventIfNeeded(iHotwordRecognitionStatusCallback4, i3, true, i4);
                        } else {
                            Slog.v("VoiceInteractionServiceManager", "verifyDetectorForVisualQueryDetectionLocked");
                            ComponentName componentName2 = voiceInteractionManagerServiceImpl.mVisualQueryDetectionComponentName;
                            if (componentName2 == null) {
                                Slog.w("VoiceInteractionServiceManager", "Visual query detection service name not found");
                                throw new IllegalStateException("Visual query detection service name not found");
                            }
                            ServiceInfo serviceInfoLocked2 = VoiceInteractionManagerServiceImpl.getServiceInfoLocked(voiceInteractionManagerServiceImpl.mUser, componentName2);
                            if (serviceInfoLocked2 == null) {
                                Slog.w("VoiceInteractionServiceManager", "Visual query detection service info not found");
                                throw new IllegalStateException("Visual query detection service name not found");
                            }
                            int i6 = serviceInfoLocked2.flags;
                            if ((i6 & 2) == 0 || (i6 & 4) != 0) {
                                Slog.w("VoiceInteractionServiceManager", "Visual query detection service not in isolated process");
                                throw new IllegalStateException("Visual query detection not in isolated process");
                            }
                            if (!"android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE".equals(serviceInfoLocked2.permission)) {
                                Slog.w("VoiceInteractionServiceManager", "Visual query detection does not require permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
                                throw new SecurityException("Visual query detection does not require permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
                            }
                            if (voiceInteractionManagerServiceImpl.mContext.getPackageManager().checkPermission("android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE", voiceInteractionManagerServiceImpl.mInfo.getServiceInfo().packageName) == 0) {
                                Slog.w("VoiceInteractionServiceManager", "Voice interaction service should not hold permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
                                throw new SecurityException("Voice interaction service should not hold permission android.permission.BIND_VISUAL_QUERY_DETECTION_SERVICE");
                            }
                            if (sharedMemory2 != null && !sharedMemory2.setProtect(OsConstants.PROT_READ)) {
                                Slog.w("VoiceInteractionServiceManager", "Can't set sharedMemory to be read-only");
                                throw new IllegalStateException("Can't set sharedMemory to be read-only");
                            }
                        }
                        if (VoiceInteractionManagerServiceImpl.SYSPROP_VISUAL_QUERY_SERVICE_ENABLED) {
                            ComponentName componentName3 = voiceInteractionManagerServiceImpl.mHotwordDetectionComponentName;
                            int i7 = voiceInteractionManagerServiceImpl.mUser;
                            ServiceInfo serviceInfoLocked3 = VoiceInteractionManagerServiceImpl.getServiceInfoLocked(i7, componentName3);
                            ServiceInfo serviceInfoLocked4 = VoiceInteractionManagerServiceImpl.getServiceInfoLocked(i7, voiceInteractionManagerServiceImpl.mVisualQueryDetectionComponentName);
                            if (serviceInfoLocked3 != null && serviceInfoLocked4 != null && ((serviceInfoLocked3.flags & 16) == 0 || (serviceInfoLocked4.flags & 16) == 0)) {
                                Slog.w("VoiceInteractionServiceManager", "Sandboxed detection service not in shared isolated process");
                                throw new IllegalStateException("VisualQueryDetectionService or HotworDetectionService not in a shared isolated process. Please make sure to set android:allowSharedIsolatedProcess and android:isolatedProcess to be true and android:externalService to be false in the manifest file");
                            }
                        }
                        if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection == null) {
                            iHotwordRecognitionStatusCallback2 = iHotwordRecognitionStatusCallback4;
                            iBinder2 = iBinder3;
                            i2 = 3;
                            voiceInteractionManagerServiceImpl.mHotwordDetectionConnection = new HotwordDetectionConnection(voiceInteractionManagerServiceImpl.mServiceStub, voiceInteractionManagerServiceImpl.mContext, voiceInteractionManagerServiceImpl.mInfo.getServiceInfo().applicationInfo.uid, identity2, voiceInteractionManagerServiceImpl.mHotwordDetectionComponentName, voiceInteractionManagerServiceImpl.mVisualQueryDetectionComponentName, voiceInteractionManagerServiceImpl.mUser, i3, new VoiceInteractionManagerServiceImpl$$ExternalSyntheticLambda1(voiceInteractionManagerServiceImpl));
                            voiceInteractionManagerServiceImpl.mAccessibilitySettingsListeners.add(voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.mAccessibilitySettingsListener);
                        } else {
                            iHotwordRecognitionStatusCallback2 = iHotwordRecognitionStatusCallback4;
                            iBinder2 = iBinder3;
                            i2 = 3;
                            if (i3 != 3) {
                                voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.mDetectorType = i3;
                            }
                        }
                        HotwordDetectionConnection hotwordDetectionConnection = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection;
                        DetectorSession detectorSession = (DetectorSession) hotwordDetectionConnection.mDetectorSessions.get(i3);
                        if (detectorSession != null) {
                            detectorSession.destroyLocked();
                            hotwordDetectionConnection.mDetectorSessions.remove(i3);
                        }
                        if (i3 == 1) {
                            if (hotwordDetectionConnection.mRemoteHotwordDetectionService == null) {
                                hotwordDetectionConnection.mRemoteHotwordDetectionService = hotwordDetectionConnection.mHotwordDetectionServiceConnectionFactory.createLocked();
                            }
                            iHotwordRecognitionStatusCallback3 = iHotwordRecognitionStatusCallback2;
                            DspTrustedHotwordDetectorSession dspTrustedHotwordDetectorSession = new DspTrustedHotwordDetectorSession(hotwordDetectionConnection.mRemoteHotwordDetectionService, hotwordDetectionConnection.mLock, hotwordDetectionConnection.mContext, iBinder2, iHotwordRecognitionStatusCallback3, hotwordDetectionConnection.mVoiceInteractionServiceUid, hotwordDetectionConnection.mVoiceInteractorIdentity, hotwordDetectionConnection.mScheduledExecutorService, hotwordDetectionConnection.mDebugHotwordLogging, hotwordDetectionConnection.mRemoteExceptionListener, hotwordDetectionConnection.mUserId);
                            dspTrustedHotwordDetectorSession.mValidatingDspTrigger = false;
                            dspTrustedHotwordDetectorSession.mLastHotwordRejectedResult = null;
                            softwareTrustedHotwordDetectorSession = dspTrustedHotwordDetectorSession;
                        } else {
                            iHotwordRecognitionStatusCallback3 = iHotwordRecognitionStatusCallback2;
                            IBinder iBinder4 = iBinder2;
                            if (i3 == i2) {
                                if (hotwordDetectionConnection.mRemoteVisualQueryDetectionService == null) {
                                    hotwordDetectionConnection.mRemoteVisualQueryDetectionService = hotwordDetectionConnection.mVisualQueryDetectionServiceConnectionFactory.createLocked();
                                }
                                VisualQueryDetectorSession visualQueryDetectorSession = new VisualQueryDetectorSession(hotwordDetectionConnection.mRemoteVisualQueryDetectionService, hotwordDetectionConnection.mLock, hotwordDetectionConnection.mContext, iBinder4, iHotwordRecognitionStatusCallback3, hotwordDetectionConnection.mVoiceInteractionServiceUid, hotwordDetectionConnection.mVoiceInteractorIdentity, hotwordDetectionConnection.mScheduledExecutorService, hotwordDetectionConnection.mDebugHotwordLogging, hotwordDetectionConnection.mRemoteExceptionListener, hotwordDetectionConnection.mUserId);
                                visualQueryDetectorSession.mEgressingData = false;
                                visualQueryDetectorSession.mQueryStreaming = false;
                                visualQueryDetectorSession.mAttentionListener = null;
                                visualQueryDetectorSession.mEnableAccessibilityDataEgress = Settings.Secure.getIntForUser(visualQueryDetectorSession.mContext.getContentResolver(), "visual_query_accessibility_detection_enabled", 0, visualQueryDetectorSession.mUserId) == 1;
                                softwareTrustedHotwordDetectorSession = visualQueryDetectorSession;
                            } else {
                                if (hotwordDetectionConnection.mRemoteHotwordDetectionService == null) {
                                    hotwordDetectionConnection.mRemoteHotwordDetectionService = hotwordDetectionConnection.mHotwordDetectionServiceConnectionFactory.createLocked();
                                }
                                softwareTrustedHotwordDetectorSession = new SoftwareTrustedHotwordDetectorSession(hotwordDetectionConnection.mRemoteHotwordDetectionService, hotwordDetectionConnection.mLock, hotwordDetectionConnection.mContext, iBinder4, iHotwordRecognitionStatusCallback3, hotwordDetectionConnection.mVoiceInteractionServiceUid, hotwordDetectionConnection.mVoiceInteractorIdentity, hotwordDetectionConnection.mScheduledExecutorService, hotwordDetectionConnection.mDebugHotwordLogging, hotwordDetectionConnection.mRemoteExceptionListener, hotwordDetectionConnection.mUserId);
                            }
                        }
                        hotwordDetectionConnection.mHotwordRecognitionCallback = iHotwordRecognitionStatusCallback3;
                        hotwordDetectionConnection.mDetectorSessions.put(i3, softwareTrustedHotwordDetectorSession);
                        synchronized (softwareTrustedHotwordDetectorSession.mLock) {
                            try {
                                if (!softwareTrustedHotwordDetectorSession.mInitialized && !softwareTrustedHotwordDetectorSession.mDestroyed) {
                                    if (softwareTrustedHotwordDetectorSession.mRemoteDetectionService.postAsync(new DetectorSession$$ExternalSyntheticLambda1(softwareTrustedHotwordDetectorSession, persistableBundle2, sharedMemory2)).whenComplete(new DetectorSession$$ExternalSyntheticLambda2(softwareTrustedHotwordDetectorSession)) == null) {
                                        Slog.w("DetectorSession", "Failed to create AndroidFuture");
                                    }
                                    softwareTrustedHotwordDetectorSession.mInitialized = true;
                                }
                            } finally {
                            }
                        }
                    }
                });
            }
        }

        public final void initForUser(int i) {
            VoiceInteractionServiceInfo voiceInteractionServiceInfo;
            ServiceInfo serviceInfo;
            ServiceInfo serviceInfo2;
            String stringForUser = Settings.Secure.getStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", i);
            ComponentName curRecognizer = getCurRecognizer(i);
            if (stringForUser == null && curRecognizer != null && this.mEnableService) {
                voiceInteractionServiceInfo = findAvailInteractor(i, curRecognizer.getPackageName());
                if (voiceInteractionServiceInfo != null) {
                    curRecognizer = null;
                }
            } else {
                voiceInteractionServiceInfo = null;
            }
            String string = VoiceInteractionManagerService.this.mContext.getResources().getString(R.string.duration_hours_shortest_future);
            if (TextUtils.isEmpty(string)) {
                string = null;
            }
            if (string != null && (voiceInteractionServiceInfo = findAvailInteractor(i, string)) != null) {
                curRecognizer = null;
            }
            if (!this.mEnableService && stringForUser != null && !TextUtils.isEmpty(stringForUser)) {
                setCurInteractor(i, null);
                stringForUser = "";
            }
            if (curRecognizer != null) {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                ComponentName unflattenFromString = !TextUtils.isEmpty(stringForUser) ? ComponentName.unflattenFromString(stringForUser) : null;
                try {
                    serviceInfo = packageManager.getServiceInfo(curRecognizer, 786560L, i);
                    if (serviceInfo != null) {
                        try {
                            RecognitionServiceInfo parseInfo = RecognitionServiceInfo.parseInfo(VoiceInteractionManagerService.this.mContext.getPackageManager(), serviceInfo);
                            String str = parseInfo.mParseError;
                            if (!TextUtils.isEmpty(str)) {
                                Log.w("VoiceInteractionManager", "Parse error in getAvailableServices: " + str);
                            }
                            if (!parseInfo.mSelectableAsDefault) {
                                serviceInfo = null;
                            }
                        } catch (RemoteException unused) {
                        }
                    }
                } catch (RemoteException unused2) {
                    serviceInfo = null;
                }
                if (unflattenFromString != null) {
                    serviceInfo2 = packageManager.getServiceInfo(unflattenFromString, 786432L, i);
                    if (serviceInfo != null && (unflattenFromString == null || serviceInfo2 != null)) {
                        return;
                    }
                }
                serviceInfo2 = null;
                if (serviceInfo != null) {
                    return;
                }
            }
            if (voiceInteractionServiceInfo == null && this.mEnableService && !"".equals(stringForUser)) {
                voiceInteractionServiceInfo = findAvailInteractor(i, null);
            }
            if (voiceInteractionServiceInfo != null) {
                setCurInteractor(i, new ComponentName(voiceInteractionServiceInfo.getServiceInfo().packageName, voiceInteractionServiceInfo.getServiceInfo().name));
            } else {
                setCurInteractor(i, null);
            }
            ComponentName findAvailRecognizer = findAvailRecognizer(i, null);
            if (findAvailRecognizer != null) {
                setCurRecognizer(i, findAvailRecognizer);
            }
        }

        public final boolean isEnrolledForKeyphrase(int i, String str) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
            }
            if (str == null) {
                throw new IllegalArgumentException("Illegal argument(s) in isEnrolledForKeyphrase");
            }
            int callingUserId = UserHandle.getCallingUserId();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return VoiceInteractionManagerService.this.mDbHelper.getKeyphraseSoundModel(i, callingUserId, str) != null;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isSessionRunning() {
            boolean z;
            isSessionRunning_enforcePermission();
            synchronized (this) {
                try {
                    z = (this.mImpl == null || this.mImpl.mActiveSession == null) ? false : true;
                } finally {
                }
            }
            return z;
        }

        public final void launchVoiceAssistFromKeyguard() {
            launchVoiceAssistFromKeyguard_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "launchVoiceAssistFromKeyguard without running voice interactionservice");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        IVoiceInteractionService iVoiceInteractionService = voiceInteractionManagerServiceImpl.mService;
                        if (iVoiceInteractionService == null) {
                            Slog.w("VoiceInteractionServiceManager", "Not bound to voice interaction service " + voiceInteractionManagerServiceImpl.mComponent);
                        } else {
                            try {
                                iVoiceInteractionService.launchVoiceAssistFromKeyguard();
                            } catch (RemoteException e) {
                                Slog.w("VoiceInteractionServiceManager", "RemoteException while calling launchVoiceAssistFromKeyguard", e);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List listModuleProperties(Identity identity) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
            }
            SoundTriggerService soundTriggerService = SoundTriggerService.this;
            soundTriggerService.mServiceEventLogger.enqueue(new SoundTriggerEvent$ServiceEvent(SoundTriggerEvent$ServiceEvent.Type.LIST_MODULE, identity.packageName, (String) null));
            SafeCloseable establishIdentityDirect = PermissionUtil.establishIdentityDirect(identity);
            try {
                List listUnderlyingModuleProperties = soundTriggerService.listUnderlyingModuleProperties(identity);
                if (establishIdentityDirect != null) {
                    establishIdentityDirect.close();
                }
                return listUnderlyingModuleProperties;
            } catch (Throwable th) {
                if (establishIdentityDirect != null) {
                    try {
                        establishIdentityDirect.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public final void notifyActivityEventChanged(final IBinder iBinder, final int i) {
            synchronized (this) {
                try {
                    if (this.mImpl != null && iBinder != null) {
                        Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda6
                            public final void runOrThrow() {
                                VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = this;
                                final IBinder iBinder2 = iBinder;
                                final int i2 = i;
                                final VoiceInteractionSessionConnection voiceInteractionSessionConnection = voiceInteractionManagerServiceStub.mImpl.mActiveSession;
                                if (voiceInteractionSessionConnection != null && voiceInteractionSessionConnection.mShown && voiceInteractionSessionConnection.mListeningVisibleActivity) {
                                    voiceInteractionSessionConnection.mScheduledExecutorService.execute(new Runnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionSessionConnection$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            VoiceInteractionSessionConnection voiceInteractionSessionConnection2 = VoiceInteractionSessionConnection.this;
                                            IBinder iBinder3 = iBinder2;
                                            int i3 = i2;
                                            synchronized (voiceInteractionSessionConnection2.mLock) {
                                                voiceInteractionSessionConnection2.handleVisibleActivitiesLocked(i3, iBinder3);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                } finally {
                }
            }
        }

        public final void onLockscreenShown() {
            onLockscreenShown_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (this.mImpl.mActiveSession != null && this.mImpl.mActiveSession.mSession != null) {
                            try {
                                this.mImpl.mActiveSession.mSession.onLockscreenShown();
                            } catch (RemoteException e) {
                                Log.w("VoiceInteractionManager", "Failed to call onLockscreenShown", e);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            new VoiceInteractionManagerServiceShellCommand(VoiceInteractionManagerService.this.mServiceStub).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
        }

        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                return super.onTransact(i, parcel, parcel2, i2);
            } catch (RuntimeException e) {
                if (!(e instanceof SecurityException)) {
                    Slog.wtf("VoiceInteractionManager", "VoiceInteractionManagerService Crash", e);
                }
                throw e;
            }
        }

        public final void performDirectAction(IBinder iBinder, String str, Bundle bundle, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "performDirectAction without running voice interaction service");
                        remoteCallback2.sendResult((Bundle) null);
                    } else {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            this.mImpl.performDirectActionLocked(iBinder, str, bundle, i, iBinder2, remoteCallback, remoteCallback2);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final List queryInteractorServices(int i, String str) {
            return VoiceInteractionManagerService.this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent("android.service.voice.VoiceInteractionService").setPackage(str), 786560, i);
        }

        public final void registerAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "registerAccessibilityDetectionSettingsListener called without running voice interaction service");
                    } else {
                        this.mImpl.mAccessibilitySettingsListeners.add(iVoiceInteractionAccessibilitySettingsListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) {
            registerVoiceInteractionSessionListener_enforcePermission();
            synchronized (this) {
                VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.register(iVoiceInteractionSessionListener);
            }
        }

        public final void requestDirectActions(IBinder iBinder, int i, IBinder iBinder2, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "requestDirectActions without running voice interaction service");
                        remoteCallback2.sendResult((Bundle) null);
                    } else {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            this.mImpl.requestDirectActionsLocked(iBinder, i, iBinder2, remoteCallback, remoteCallback2);
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setCurInteractor(int i, ComponentName componentName) {
            Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_interaction_service", componentName != null ? componentName.flattenToShortString() : "", i);
        }

        public final void setCurRecognizer(int i, ComponentName componentName) {
            Settings.Secure.putStringForUser(VoiceInteractionManagerService.this.mContext.getContentResolver(), "voice_recognition_service", componentName != null ? componentName.flattenToShortString() : "", i);
        }

        public final void setDisabled(boolean z) {
            setDisabled_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mTemporarilyDisabled == z) {
                        return;
                    }
                    this.mTemporarilyDisabled = z;
                    if (z) {
                        Slog.i("VoiceInteractionManager", "setDisabled(): temporarily disabling and hiding current session");
                        try {
                            hideCurrentSession();
                        } catch (RemoteException e) {
                            Log.w("VoiceInteractionManager", "Failed to call hideCurrentSession", e);
                        }
                    } else {
                        Slog.i("VoiceInteractionManager", "setDisabled(): re-enabling");
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setDisabledShowContext(int i) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "setDisabledShowContext without running voice interaction service");
                        return;
                    }
                    int callingUid = Binder.getCallingUid();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        int i2 = voiceInteractionManagerServiceImpl.mInfo.getServiceInfo().applicationInfo.uid;
                        if (callingUid != i2) {
                            throw new SecurityException(ArrayUtils$$ExternalSyntheticOutline0.m(callingUid, i2, "Calling uid ", " does not match active uid "));
                        }
                        voiceInteractionManagerServiceImpl.mDisabledShowContext = i;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setImplLocked(VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl) {
            this.mImpl = voiceInteractionManagerServiceImpl;
            ActivityTaskManagerInternal activityTaskManagerInternal = VoiceInteractionManagerService.this.mAtmInternal;
            ComponentName activeServiceComponentName = getActiveServiceComponentName();
            ActivityTaskManagerService.LocalService localService = (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
            WindowManagerGlobalLock windowManagerGlobalLock = ActivityTaskManagerService.this.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    ActivityTaskManagerService.this.mActiveVoiceInteractionServiceComponent = activeServiceComponentName;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        }

        public final void setKeepAwake(IBinder iBinder, boolean z) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "setKeepAwake without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        voiceInteractionManagerServiceImpl.getClass();
                        try {
                            VoiceInteractionSessionConnection voiceInteractionSessionConnection = voiceInteractionManagerServiceImpl.mActiveSession;
                            if (voiceInteractionSessionConnection != null && iBinder == voiceInteractionSessionConnection.mToken) {
                                voiceInteractionManagerServiceImpl.mAtm.setVoiceKeepAwake(voiceInteractionSessionConnection.mSession, z);
                            }
                            Slog.w("VoiceInteractionServiceManager", "setKeepAwake does not match active session");
                        } catch (RemoteException e) {
                            throw new IllegalStateException("Unexpected remote error", e);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } finally {
                }
            }
        }

        public final void setModelDatabaseForTestEnabled(boolean z, IBinder iBinder) {
            setModelDatabaseForTestEnabled_enforcePermission();
            enforceCallerAllowedToEnrollVoiceModel();
            synchronized (this) {
                try {
                    if (z) {
                        final TestModelEnrollmentDatabase testModelEnrollmentDatabase = new TestModelEnrollmentDatabase();
                        try {
                            iBinder.linkToDeath(new IBinder.DeathRecipient() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda4
                                @Override // android.os.IBinder.DeathRecipient
                                public final void binderDied() {
                                    VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this;
                                    TestModelEnrollmentDatabase testModelEnrollmentDatabase2 = testModelEnrollmentDatabase;
                                    synchronized (voiceInteractionManagerServiceStub) {
                                        try {
                                            VoiceInteractionManagerService voiceInteractionManagerService = VoiceInteractionManagerService.this;
                                            if (voiceInteractionManagerService.mDbHelper == testModelEnrollmentDatabase2) {
                                                voiceInteractionManagerService.mDbHelper = voiceInteractionManagerService.mRealDbHelper;
                                                voiceInteractionManagerServiceStub.mImpl.notifySoundModelsChangedLocked();
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            }, 0);
                            VoiceInteractionManagerService.this.mDbHelper = testModelEnrollmentDatabase;
                            this.mImpl.notifySoundModelsChangedLocked();
                        } catch (RemoteException unused) {
                        }
                    } else {
                        VoiceInteractionManagerService voiceInteractionManagerService = VoiceInteractionManagerService.this;
                        IEnrolledModelDb iEnrolledModelDb = voiceInteractionManagerService.mDbHelper;
                        DatabaseHelper databaseHelper = voiceInteractionManagerService.mRealDbHelper;
                        if (iEnrolledModelDb != databaseHelper) {
                            voiceInteractionManagerService.mDbHelper = databaseHelper;
                            this.mImpl.notifySoundModelsChangedLocked();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setSessionWindowVisible(IBinder iBinder, final boolean z) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "setSessionWindowVisible called without running voice interaction service");
                        return;
                    }
                    if (this.mImpl.mActiveSession != null && iBinder == this.mImpl.mActiveSession.mToken) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.broadcast(new Consumer() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    try {
                                        ((IVoiceInteractionSessionListener) obj).onVoiceSessionWindowVisibilityChanged(z);
                                    } catch (RemoteException e) {
                                        Slog.e("VoiceInteractionManager", "Error delivering window visibility event to listener.", e);
                                    }
                                }
                            });
                            return;
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    Slog.w("VoiceInteractionManager", "setSessionWindowVisible does not match active session");
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setUiHints(Bundle bundle) {
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
                int beginBroadcast = VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.getBroadcastItem(i).onSetUiHints(bundle);
                    } catch (RemoteException e) {
                        Slog.e("VoiceInteractionManager", "Error delivering UI hints.", e);
                    }
                }
                VoiceInteractionManagerService.this.mVoiceInteractionSessionListeners.finishBroadcast();
            }
        }

        public final void showSession(Bundle bundle, int i, String str) {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mImpl.showSessionLocked(bundle, i, str, null, null);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean showSessionForActiveService(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) {
            showSessionForActiveService_enforcePermission();
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "showSessionForActiveService without running voice interactionservice");
                        return false;
                    }
                    if (this.mTemporarilyDisabled) {
                        Slog.i("VoiceInteractionManager", "showSessionForActiveService(): ignored while temporarily disabled");
                        return false;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        LatencyTracker.getInstance(VoiceInteractionManagerService.this.mContext).onActionCancel(19);
                        return this.mImpl.showSessionLocked(bundle, i | 3, str, iVoiceInteractionSessionShowCallback, iBinder);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean showSessionFromSession(IBinder iBinder, Bundle bundle, int i, String str) {
            long clearCallingIdentity;
            Intent contextualSearchIntent;
            synchronized (this) {
                try {
                    String string = VoiceInteractionManagerService.this.mContext.getResources().getString(R.string.date_picker_month_typeface);
                    String string2 = VoiceInteractionManagerService.this.mContext.getResources().getString(R.string.date_picker_mode);
                    if (bundle != null && bundle.containsKey(string)) {
                        if (bundle.getBoolean(string2, true) && (contextualSearchIntent = getContextualSearchIntent(bundle)) != null) {
                            Slog.d("VoiceInteractionManager", "Handed over to contextual search helper.");
                            clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                ActivityOptions makeCustomTaskAnimation = ActivityOptions.makeCustomTaskAnimation(VoiceInteractionManagerService.this.mContext, 0, 0, null, null, null);
                                makeCustomTaskAnimation.setDisableStartingWindow(true);
                                VoiceInteractionManagerService voiceInteractionManagerService = VoiceInteractionManagerService.this;
                                return voiceInteractionManagerService.mAtmInternal.startActivityWithScreenshot(Binder.getCallingUid(), contextualSearchIntent, Binder.getCallingPid(), makeCustomTaskAnimation.toBundle(), voiceInteractionManagerService.mContext.getPackageName(), Binder.getCallingUserHandle().getIdentifier()) == 0;
                            } finally {
                            }
                        }
                        if (!bundle.getBoolean(VoiceInteractionManagerService.this.mContext.getResources().getString(R.string.date_picker_next_month_button), true)) {
                            return false;
                        }
                        String string3 = VoiceInteractionManagerService.this.mContext.getResources().getString(R.string.date_picker_prev_month_button);
                        ComponentName curInteractor = getCurInteractor(Binder.getCallingUserHandle().getIdentifier());
                        if (curInteractor != null && string3.equals(curInteractor.getPackageName())) {
                            Slog.d("VoiceInteractionManager", "Contextual search not supported yet. Proceeding with VIS.");
                        }
                        Slog.w("VoiceInteractionManager", "Contextual Search not supported yet. Returning failure.");
                        return false;
                    }
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "showSessionFromSession without running voice interaction service");
                        return false;
                    }
                    if (iBinder == null) {
                        LatencyTracker.getInstance(VoiceInteractionManagerService.this.mContext).onActionCancel(19);
                    }
                    clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return this.mImpl.showSessionLocked(bundle, i, str, null, null);
                    } finally {
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void shutdownHotwordDetectionService() {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "shutdownHotwordDetectionService without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection == null) {
                            Slog.w("VoiceInteractionServiceManager", "shutdown, but no hotword detection connection");
                        } else {
                            voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.cancelLocked();
                            voiceInteractionManagerServiceImpl.mAccessibilitySettingsListeners.remove(voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.mAccessibilitySettingsListener);
                            voiceInteractionManagerServiceImpl.mHotwordDetectionConnection = null;
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int startAssistantActivity(IBinder iBinder, Intent intent, String str, String str2, Bundle bundle) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "startAssistantActivity without running voice interaction service");
                        return -96;
                    }
                    int callingPid = Binder.getCallingPid();
                    int callingUid = Binder.getCallingUid();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return this.mImpl.startAssistantActivityLocked(str2, callingPid, callingUid, iBinder, intent, str, bundle);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void startListeningFromExternalSource(ParcelFileDescriptor parcelFileDescriptor, AudioFormat audioFormat, PersistableBundle persistableBundle, IBinder iBinder, IMicrophoneHotwordDetectionVoiceInteractionCallback iMicrophoneHotwordDetectionVoiceInteractionCallback) {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "startListeningFromExternalSource without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection != null) {
                            if (parcelFileDescriptor == null) {
                                Slog.w("VoiceInteractionServiceManager", "External source is null for hotword detector");
                                throw new IllegalStateException("External source is null for hotword detector");
                            }
                            DetectorSession detectorSessionByTokenLocked = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.getDetectorSessionByTokenLocked(iBinder);
                            if (detectorSessionByTokenLocked == null) {
                                Slog.v("HotwordDetectionConnection", "Not found the detector by token");
                            } else {
                                detectorSessionByTokenLocked.startListeningFromExternalSourceLocked(parcelFileDescriptor, audioFormat, persistableBundle, iMicrophoneHotwordDetectionVoiceInteractionCallback);
                            }
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x004a  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x004b A[Catch: all -> 0x0077, TryCatch #0 {all -> 0x0077, blocks: (B:12:0x0022, B:18:0x0029, B:20:0x0036, B:23:0x003d, B:26:0x004b, B:28:0x0051, B:29:0x0059, B:30:0x0040), top: B:11:0x0022, outer: #1 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void startListeningFromMic(android.media.AudioFormat r6, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback r7) {
            /*
                r5 = this;
                java.lang.String r6 = "android.permission.RECORD_AUDIO"
                r5.enforceCallingPermission(r6)
                java.lang.String r6 = "android.permission.CAPTURE_AUDIO_HOTWORD"
                r5.enforceCallingPermission(r6)
                monitor-enter(r5)
                r5.enforceIsCurrentVoiceInteractionService()     // Catch: java.lang.Throwable -> L1c
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl r6 = r5.mImpl     // Catch: java.lang.Throwable -> L1c
                if (r6 != 0) goto L1e
                java.lang.String r6 = "VoiceInteractionManager"
                java.lang.String r7 = "startListeningFromMic without running voice interaction service"
                android.util.Slog.w(r6, r7)     // Catch: java.lang.Throwable -> L1c
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L1c
                return
            L1c:
                r6 = move-exception
                goto L7c
            L1e:
                long r0 = android.os.Binder.clearCallingIdentity()     // Catch: java.lang.Throwable -> L1c
                com.android.server.voiceinteraction.VoiceInteractionManagerServiceImpl r6 = r5.mImpl     // Catch: java.lang.Throwable -> L77
                com.android.server.voiceinteraction.HotwordDetectionConnection r2 = r6.mHotwordDetectionConnection     // Catch: java.lang.Throwable -> L77
                if (r2 != 0) goto L29
                goto L72
            L29:
                com.android.server.voiceinteraction.HotwordDetectionConnection r6 = r6.mHotwordDetectionConnection     // Catch: java.lang.Throwable -> L77
                android.util.SparseArray r6 = r6.mDetectorSessions     // Catch: java.lang.Throwable -> L77
                r2 = 2
                java.lang.Object r6 = r6.get(r2)     // Catch: java.lang.Throwable -> L77
                com.android.server.voiceinteraction.DetectorSession r6 = (com.android.server.voiceinteraction.DetectorSession) r6     // Catch: java.lang.Throwable -> L77
                if (r6 == 0) goto L40
                boolean r3 = r6.isDestroyed()     // Catch: java.lang.Throwable -> L77
                if (r3 == 0) goto L3d
                goto L40
            L3d:
                com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession r6 = (com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession) r6     // Catch: java.lang.Throwable -> L77
                goto L48
            L40:
                java.lang.String r6 = "HotwordDetectionConnection"
                java.lang.String r3 = "Not found the software detector"
                android.util.Slog.v(r6, r3)     // Catch: java.lang.Throwable -> L77
                r6 = 0
            L48:
                if (r6 != 0) goto L4b
                goto L72
            L4b:
                r6.mSoftwareCallback = r7     // Catch: java.lang.Throwable -> L77
                boolean r7 = r6.mPerformingSoftwareHotwordDetection     // Catch: java.lang.Throwable -> L77
                if (r7 == 0) goto L59
                java.lang.String r6 = "SoftwareTrustedHotwordDetectorSession"
                java.lang.String r7 = "Hotword validation is already in progress, ignoring."
                android.util.Slog.i(r6, r7)     // Catch: java.lang.Throwable -> L77
                goto L72
            L59:
                r7 = 1
                r6.mPerformingSoftwareHotwordDetection = r7     // Catch: java.lang.Throwable -> L77
                com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$1 r7 = new com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$1     // Catch: java.lang.Throwable -> L77
                r7.<init>()     // Catch: java.lang.Throwable -> L77
                com.android.server.voiceinteraction.HotwordDetectionConnection$ServiceConnection r3 = r6.mRemoteDetectionService     // Catch: java.lang.Throwable -> L77
                com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda1 r4 = new com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda1     // Catch: java.lang.Throwable -> L77
                r4.<init>(r7)     // Catch: java.lang.Throwable -> L77
                r3.run(r4)     // Catch: java.lang.Throwable -> L77
                int r6 = r6.mVoiceInteractionServiceUid     // Catch: java.lang.Throwable -> L77
                r7 = 9
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeDetectorEvent(r2, r7, r6)     // Catch: java.lang.Throwable -> L77
            L72:
                android.os.Binder.restoreCallingIdentity(r0)     // Catch: java.lang.Throwable -> L1c
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L1c
                return
            L77:
                r6 = move-exception
                android.os.Binder.restoreCallingIdentity(r0)     // Catch: java.lang.Throwable -> L1c
                throw r6     // Catch: java.lang.Throwable -> L1c
            L7c:
                monitor-exit(r5)     // Catch: java.lang.Throwable -> L1c
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.startListeningFromMic(android.media.AudioFormat, android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback):void");
        }

        public final void startListeningVisibleActivityChanged(IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "startListeningVisibleActivityChanged without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mImpl.startListeningVisibleActivityChangedLocked(iBinder);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void startPerceiving(IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback) {
            VisualQueryDetectorSession visualQueryDetectorSessionLocked;
            IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener;
            enforceCallingPermission("android.permission.RECORD_AUDIO");
            enforceCallingPermission("android.permission.CAMERA");
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "startPerceiving without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        boolean z = false;
                        if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection != null && (visualQueryDetectorSessionLocked = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.getVisualQueryDetectorSessionLocked()) != null) {
                            final VisualQueryDetectorSession.AnonymousClass1 anonymousClass1 = new IDetectorSessionVisualQueryDetectionCallback.Stub() { // from class: com.android.server.voiceinteraction.VisualQueryDetectorSession.1
                                public final /* synthetic */ IVisualQueryDetectionVoiceInteractionCallback val$callback;

                                public AnonymousClass1(IVisualQueryDetectionVoiceInteractionCallback iVisualQueryDetectionVoiceInteractionCallback2) {
                                    r2 = iVisualQueryDetectionVoiceInteractionCallback2;
                                }

                                public final void onAttentionGained(VisualQueryAttentionResult visualQueryAttentionResult) {
                                    Slog.v("VisualQueryDetectorSession", "BinderCallback#onAttentionGained");
                                    synchronized (VisualQueryDetectorSession.this.mLock) {
                                        VisualQueryDetectorSession visualQueryDetectorSession = VisualQueryDetectorSession.this;
                                        visualQueryDetectorSession.mEgressingData = true;
                                        IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener = visualQueryDetectorSession.mAttentionListener;
                                        if (iVisualQueryDetectionAttentionListener == null) {
                                            return;
                                        }
                                        try {
                                            iVisualQueryDetectionAttentionListener.onAttentionGained(visualQueryAttentionResult);
                                        } catch (RemoteException e) {
                                            Slog.e("VisualQueryDetectorSession", "Error delivering attention gained event.", e);
                                            try {
                                                r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(3, "Attention listener fails to switch to GAINED state."));
                                            } catch (RemoteException unused) {
                                                Slog.v("VisualQueryDetectorSession", "Fail to call onVisualQueryDetectionServiceFailure");
                                            }
                                        }
                                    }
                                }

                                public final void onAttentionLost(int i) {
                                    Slog.v("VisualQueryDetectorSession", "BinderCallback#onAttentionLost");
                                    synchronized (VisualQueryDetectorSession.this.mLock) {
                                        VisualQueryDetectorSession visualQueryDetectorSession = VisualQueryDetectorSession.this;
                                        visualQueryDetectorSession.mEgressingData = false;
                                        IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener = visualQueryDetectorSession.mAttentionListener;
                                        if (iVisualQueryDetectionAttentionListener == null) {
                                            return;
                                        }
                                        try {
                                            iVisualQueryDetectionAttentionListener.onAttentionLost(i);
                                        } catch (RemoteException e) {
                                            Slog.e("VisualQueryDetectorSession", "Error delivering attention lost event.", e);
                                            try {
                                                r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(3, "Attention listener fails to switch to LOST state."));
                                            } catch (RemoteException unused) {
                                                Slog.v("VisualQueryDetectorSession", "Fail to call onVisualQueryDetectionServiceFailure");
                                            }
                                        }
                                    }
                                }

                                public final void onQueryDetected(String str) {
                                    Slog.v("VisualQueryDetectorSession", "BinderCallback#onQueryDetected");
                                    synchronized (VisualQueryDetectorSession.this.mLock) {
                                        Objects.requireNonNull(str);
                                        VisualQueryDetectorSession visualQueryDetectorSession = VisualQueryDetectorSession.this;
                                        if (!visualQueryDetectorSession.mEgressingData) {
                                            Slog.v("VisualQueryDetectorSession", "Query should not be egressed within the unattention state.");
                                            r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot stream queries without attention signals."));
                                            return;
                                        }
                                        try {
                                            Binder.withCleanCallingIdentity(new VisualQueryDetectorSession$$ExternalSyntheticLambda1(visualQueryDetectorSession, "android.permission.RECORD_AUDIO", 27));
                                            VisualQueryDetectorSession.this.mQueryStreaming = true;
                                            r2.onQueryDetected(str);
                                            Slog.i("VisualQueryDetectorSession", "Egressed from visual query detection process.");
                                        } catch (SecurityException e) {
                                            Slog.w("VisualQueryDetectorSession", "Ignoring #onQueryDetected due to a SecurityException", e);
                                            try {
                                                r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot stream queries without audio permission."));
                                            } catch (RemoteException e2) {
                                                VisualQueryDetectorSession.this.notifyOnDetectorRemoteException();
                                                throw e2;
                                            }
                                        }
                                    }
                                }

                                public final void onQueryFinished() {
                                    Slog.v("VisualQueryDetectorSession", "BinderCallback#onQueryFinished");
                                    synchronized (VisualQueryDetectorSession.this.mLock) {
                                        try {
                                            if (VisualQueryDetectorSession.this.mQueryStreaming) {
                                                r2.onQueryFinished();
                                                VisualQueryDetectorSession.this.mQueryStreaming = false;
                                            } else {
                                                Slog.v("VisualQueryDetectorSession", "Query streaming state signal FINISHED is block since there is no active query being streamed.");
                                                r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot send FINISHED signal with no query streamed."));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }

                                public final void onQueryRejected() {
                                    Slog.v("VisualQueryDetectorSession", "BinderCallback#onQueryRejected");
                                    synchronized (VisualQueryDetectorSession.this.mLock) {
                                        try {
                                            if (VisualQueryDetectorSession.this.mQueryStreaming) {
                                                r2.onQueryRejected();
                                                VisualQueryDetectorSession.this.mQueryStreaming = false;
                                            } else {
                                                Slog.v("VisualQueryDetectorSession", "Query streaming state signal REJECTED is block since there is no active query being streamed.");
                                                r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot send REJECTED signal with no query streamed."));
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }

                                public final void onResultDetected(VisualQueryDetectedResult visualQueryDetectedResult) {
                                    Slog.v("VisualQueryDetectorSession", "BinderCallback#onResultDetected");
                                    synchronized (VisualQueryDetectorSession.this.mLock) {
                                        try {
                                            Objects.requireNonNull(visualQueryDetectedResult);
                                            if (!VisualQueryDetectorSession.this.mEgressingData) {
                                                Slog.v("VisualQueryDetectorSession", "Result should not be egressed within the unattention state.");
                                                r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot stream results without attention signals."));
                                                return;
                                            }
                                            if (visualQueryDetectedResult.getAccessibilityDetectionData() != null && !VisualQueryDetectorSession.this.mEnableAccessibilityDataEgress) {
                                                Slog.v("VisualQueryDetectorSession", "Accessibility data can be egressed only when the isAccessibilityDetectionEnabled() is true.");
                                                r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot stream accessibility data without enabling the setting."));
                                                return;
                                            }
                                            if (visualQueryDetectedResult.getAccessibilityDetectionData() != null) {
                                                try {
                                                    VisualQueryDetectorSession visualQueryDetectorSession = VisualQueryDetectorSession.this;
                                                    visualQueryDetectorSession.getClass();
                                                    Binder.withCleanCallingIdentity(new VisualQueryDetectorSession$$ExternalSyntheticLambda1(visualQueryDetectorSession, "android.permission.CAMERA", 26));
                                                } catch (SecurityException e) {
                                                    Slog.w("VisualQueryDetectorSession", "Ignoring #onQueryDetected due to a SecurityException", e);
                                                    try {
                                                        r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot stream visual only accessibility data without camera permission."));
                                                        return;
                                                    } catch (RemoteException e2) {
                                                        VisualQueryDetectorSession.this.notifyOnDetectorRemoteException();
                                                        throw e2;
                                                    }
                                                }
                                            }
                                            if (!visualQueryDetectedResult.getPartialQuery().isEmpty()) {
                                                try {
                                                    VisualQueryDetectorSession visualQueryDetectorSession2 = VisualQueryDetectorSession.this;
                                                    visualQueryDetectorSession2.getClass();
                                                    Binder.withCleanCallingIdentity(new VisualQueryDetectorSession$$ExternalSyntheticLambda1(visualQueryDetectorSession2, "android.permission.RECORD_AUDIO", 27));
                                                } catch (SecurityException e3) {
                                                    Slog.w("VisualQueryDetectorSession", "Ignoring #onQueryDetected due to a SecurityException", e3);
                                                    try {
                                                        r2.onVisualQueryDetectionServiceFailure(new VisualQueryDetectionServiceFailure(4, "Cannot stream queries without audio permission."));
                                                        return;
                                                    } catch (RemoteException e4) {
                                                        VisualQueryDetectorSession.this.notifyOnDetectorRemoteException();
                                                        throw e4;
                                                    }
                                                }
                                            }
                                            VisualQueryDetectorSession.this.mQueryStreaming = true;
                                            r2.onResultDetected(visualQueryDetectedResult);
                                            Slog.i("VisualQueryDetectorSession", "Egressed from visual query detection process.");
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            };
                            z = visualQueryDetectorSessionLocked.mRemoteDetectionService.run(new ServiceConnector.VoidJob() { // from class: com.android.server.voiceinteraction.VisualQueryDetectorSession$$ExternalSyntheticLambda0
                                public final void runNoResult(Object obj) {
                                    ((ISandboxedDetectionService) obj).detectWithVisualSignals(anonymousClass1);
                                }
                            });
                        }
                        if (z && (iVisualQueryRecognitionStatusListener = VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener) != null) {
                            iVisualQueryRecognitionStatusListener.onStartPerceiving();
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int startVoiceActivity(IBinder iBinder, Intent intent, String str, String str2) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "startVoiceActivity without running voice interaction service");
                        return -96;
                    }
                    int callingPid = Binder.getCallingPid();
                    int callingUid = Binder.getCallingUid();
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(VoiceInteractionManagerService.this.mContext.getPackageManager(), 131072);
                        if (resolveActivityInfo != null) {
                            this.mImpl.grantImplicitAccessLocked(resolveActivityInfo.applicationInfo.uid, intent);
                        } else {
                            Slog.w("VoiceInteractionManager", "Cannot find ActivityInfo in startVoiceActivity.");
                        }
                        int startVoiceActivityLocked = this.mImpl.startVoiceActivityLocked(str2, callingPid, callingUid, iBinder, intent, str);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return startVoiceActivityLocked;
                    } catch (Throwable th) {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        throw th;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }

        public final void stopListeningFromMic() {
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "stopListeningFromMic without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        this.mImpl.stopListeningFromMicLocked();
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void stopListeningVisibleActivityChanged(IBinder iBinder) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "stopListeningVisibleActivityChanged without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionSessionConnection voiceInteractionSessionConnection = this.mImpl.mActiveSession;
                        if (voiceInteractionSessionConnection != null && iBinder == voiceInteractionSessionConnection.mToken) {
                            voiceInteractionSessionConnection.mListeningVisibleActivity = false;
                            voiceInteractionSessionConnection.mVisibleActivityInfoForToken.clear();
                        }
                        Slog.w("VoiceInteractionServiceManager", "stopListeningVisibleActivityChangedLocked does not match active session");
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void stopPerceiving() {
            IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener;
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "stopPerceiving without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        boolean z = false;
                        if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection == null) {
                            Slog.w("VoiceInteractionServiceManager", "stopPerceivingLocked() called but connection isn't established");
                        } else {
                            VisualQueryDetectorSession visualQueryDetectorSessionLocked = voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.getVisualQueryDetectorSessionLocked();
                            if (visualQueryDetectorSessionLocked != null) {
                                z = visualQueryDetectorSessionLocked.mRemoteDetectionService.run(new SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda0());
                            }
                        }
                        if (z && (iVisualQueryRecognitionStatusListener = VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener) != null) {
                            iVisualQueryRecognitionStatusListener.onStopPerceiving();
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void subscribeVisualQueryRecognitionStatus(IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) {
            subscribeVisualQueryRecognitionStatus_enforcePermission();
            synchronized (this) {
                VoiceInteractionManagerService.this.mVisualQueryRecognitionStatusListener = iVisualQueryRecognitionStatusListener;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x005e  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00ad  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00b4  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0105  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void switchImplementationIfNeededLocked(boolean r10) {
            /*
                Method dump skipped, instructions count: 265
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.voiceinteraction.VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.switchImplementationIfNeededLocked(boolean):void");
        }

        public final void triggerHardwareRecognitionEventForTest(SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent, IHotwordRecognitionStatusCallback iHotwordRecognitionStatusCallback) {
            enforceCallingPermission("android.permission.RECORD_AUDIO");
            enforceCallingPermission("android.permission.CAPTURE_AUDIO_HOTWORD");
            synchronized (this) {
                try {
                    enforceIsCurrentVoiceInteractionService();
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "triggerHardwareRecognitionEventForTest without running voice interaction service");
                        return;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = this.mImpl;
                        if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection == null) {
                            Slog.w("VoiceInteractionServiceManager", "triggerHardwareRecognitionEventForTestLocked() called but connection isn't established");
                        } else {
                            voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.detectFromDspSource(keyphraseRecognitionEvent, iHotwordRecognitionStatusCallback);
                        }
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final synchronized void unloadAllKeyphraseModels() {
            for (int i = 0; i < VoiceInteractionManagerService.this.mLoadedKeyphraseIds.size(); i++) {
                try {
                    int intValue = ((Integer) VoiceInteractionManagerService.this.mLoadedKeyphraseIds.keyAt(i)).intValue();
                    int m1035$$Nest$munloadKeyphraseModel = SoundTriggerSession.m1035$$Nest$munloadKeyphraseModel((SoundTriggerSession) VoiceInteractionManagerService.this.mLoadedKeyphraseIds.valueAt(i), intValue);
                    if (m1035$$Nest$munloadKeyphraseModel != 0) {
                        Slog.w("VoiceInteractionManager", "Failed to unload keyphrase " + intValue + ":" + m1035$$Nest$munloadKeyphraseModel);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            VoiceInteractionManagerService.this.mLoadedKeyphraseIds.clear();
        }

        public final void unregisterAccessibilityDetectionSettingsListener(IVoiceInteractionAccessibilitySettingsListener iVoiceInteractionAccessibilitySettingsListener) {
            synchronized (this) {
                try {
                    if (this.mImpl == null) {
                        Slog.w("VoiceInteractionManager", "unregisterAccessibilityDetectionSettingsListener called without running voice interaction service");
                    } else {
                        this.mImpl.mAccessibilitySettingsListeners.remove(iVoiceInteractionAccessibilitySettingsListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final int updateKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) {
            enforceCallerAllowedToEnrollVoiceModel();
            if (keyphraseSoundModel == null) {
                throw new IllegalArgumentException("Model must not be null");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (!VoiceInteractionManagerService.this.mDbHelper.updateKeyphraseSoundModel(keyphraseSoundModel)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return Integer.MIN_VALUE;
                }
                synchronized (this) {
                    try {
                        if (this.mImpl != null && this.mImpl.mService != null) {
                            this.mImpl.notifySoundModelsChangedLocked();
                        }
                    } finally {
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void updateState(final PersistableBundle persistableBundle, final SharedMemory sharedMemory, final IBinder iBinder) {
            updateState_enforcePermission();
            synchronized (this) {
                enforceIsCurrentVoiceInteractionService();
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda2
                    public final void runOrThrow() {
                        VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this;
                        PersistableBundle persistableBundle2 = persistableBundle;
                        SharedMemory sharedMemory2 = sharedMemory;
                        IBinder iBinder2 = iBinder;
                        VoiceInteractionManagerServiceImpl voiceInteractionManagerServiceImpl = voiceInteractionManagerServiceStub.mImpl;
                        voiceInteractionManagerServiceImpl.getClass();
                        Slog.v("VoiceInteractionServiceManager", "updateStateLocked");
                        if (sharedMemory2 != null && !sharedMemory2.setProtect(OsConstants.PROT_READ)) {
                            Slog.w("VoiceInteractionServiceManager", "Can't set sharedMemory to be read-only");
                            throw new IllegalStateException("Can't set sharedMemory to be read-only");
                        }
                        if (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection == null) {
                            Slog.w("VoiceInteractionServiceManager", "update State, but no hotword detection connection");
                            throw new IllegalStateException("Hotword detection connection not found");
                        }
                        synchronized (voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.mLock) {
                            voiceInteractionManagerServiceImpl.mHotwordDetectionConnection.updateStateLocked(persistableBundle2, sharedMemory2, iBinder2);
                        }
                    }
                });
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.voiceinteraction.VoiceInteractionManagerService$3] */
    public VoiceInteractionManagerService(Context context) {
        super(context);
        this.mLoadedKeyphraseIds = new ArrayMap();
        this.mVoiceInteractionSessionListeners = new RemoteCallbackList();
        this.mLatencyLoggingListener = new IVoiceInteractionSessionListener.Stub() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.3
            public final IBinder asBinder() {
                return VoiceInteractionManagerService.this.mServiceStub;
            }

            public final void onSetUiHints(Bundle bundle) {
            }

            public final void onVoiceSessionHidden() {
            }

            public final void onVoiceSessionShown() {
            }

            public final void onVoiceSessionWindowVisibilityChanged(boolean z) {
                if (z) {
                    LatencyTracker.getInstance(VoiceInteractionManagerService.this.mContext).onActionEnd(19);
                }
            }
        };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        Objects.requireNonNull(userManagerInternal);
        this.mUserManagerInternal = userManagerInternal;
        DatabaseHelper databaseHelper = new DatabaseHelper(context, "sound_model.db", null, 7);
        this.mRealDbHelper = databaseHelper;
        this.mDbHelper = databaseHelper;
        this.mServiceStub = new VoiceInteractionManagerServiceStub();
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Objects.requireNonNull(activityManagerInternal);
        this.mAmInternal = activityManagerInternal;
        ActivityTaskManagerInternal activityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        Objects.requireNonNull(activityTaskManagerInternal);
        this.mAtmInternal = activityTaskManagerInternal;
        WindowManagerInternal windowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        Objects.requireNonNull(windowManagerInternal);
        this.mWmInternal = windowManagerInternal;
        this.mDpmInternal = (DevicePolicyManagerInternal) LocalServices.getService(DevicePolicyManagerInternal.class);
        LegacyPermissionManagerService.Internal internal = (LegacyPermissionManagerService.Internal) LocalServices.getService(LegacyPermissionManagerService.Internal.class);
        LegacyPermissionManagerInternal$PackagesProvider legacyPermissionManagerInternal$PackagesProvider = new LegacyPermissionManagerInternal$PackagesProvider() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.1
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
            public final String[] getPackages(int i) {
                VoiceInteractionManagerService voiceInteractionManagerService = VoiceInteractionManagerService.this;
                voiceInteractionManagerService.mServiceStub.initForUser(i);
                ComponentName curInteractor = voiceInteractionManagerService.mServiceStub.getCurInteractor(i);
                if (curInteractor != null) {
                    return new String[]{curInteractor.getPackageName()};
                }
                return null;
            }
        };
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy = LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
        synchronized (defaultPermissionGrantPolicy.mLock) {
            defaultPermissionGrantPolicy.mVoiceInteractionPackagesProvider = legacyPermissionManagerInternal$PackagesProvider;
        }
    }

    @Override // com.android.server.SystemService
    public final boolean isUserSupported(SystemService.TargetUser targetUser) {
        return targetUser.isFull();
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (500 == i) {
            ShortcutServiceInternal shortcutServiceInternal = (ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class);
            Objects.requireNonNull(shortcutServiceInternal);
            this.mShortcutServiceInternal = shortcutServiceInternal;
            this.mSoundTriggerInternal = (SoundTriggerService.LocalSoundTriggerService) LocalServices.getService(SoundTriggerService.LocalSoundTriggerService.class);
            return;
        }
        if (i != 600) {
            if (i == 1000) {
                this.mServiceStub.registerVoiceInteractionSessionListener(this.mLatencyLoggingListener);
                return;
            }
            return;
        }
        VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = this.mServiceStub;
        voiceInteractionManagerServiceStub.mSafeMode = isSafeMode();
        voiceInteractionManagerServiceStub.mPackageMonitor.register(VoiceInteractionManagerService.this.mContext, BackgroundThread.getHandler().getLooper(), UserHandle.ALL, true);
        voiceInteractionManagerServiceStub.new SettingsObserver(UiThread.getHandler());
        synchronized (voiceInteractionManagerServiceStub) {
            int currentUser = ActivityManager.getCurrentUser();
            voiceInteractionManagerServiceStub.mCurUser = currentUser;
            UserInfo userInfo = VoiceInteractionManagerService.this.mUserManagerInternal.getUserInfo(currentUser);
            VoiceInteractionManagerService.this.getClass();
            voiceInteractionManagerServiceStub.mCurUserSupported = userInfo.isFull();
            voiceInteractionManagerServiceStub.switchImplementationIfNeededLocked(false);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("voiceinteraction", this.mServiceStub);
        publishLocalService(VoiceInteractionManagerInternal.class, new LocalService());
        this.mAmInternal.setVoiceInteractionManagerProvider(new ActivityManagerInternal.VoiceInteractionManagerProvider() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService.2
            public final void notifyActivityDestroyed(IBinder iBinder) {
                VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = VoiceInteractionManagerService.this.mServiceStub;
                synchronized (voiceInteractionManagerServiceStub) {
                    try {
                        if (voiceInteractionManagerServiceStub.mImpl != null && iBinder != null) {
                            Binder.withCleanCallingIdentity(new VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda3(1, iBinder, voiceInteractionManagerServiceStub));
                        }
                    } finally {
                    }
                }
            }
        });
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        this.mServiceStub.initForUser(targetUser.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        final int userIdentifier = targetUser2.getUserIdentifier();
        final VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = this.mServiceStub;
        voiceInteractionManagerServiceStub.getClass();
        FgThread.getHandler().post(new Runnable() { // from class: com.android.server.voiceinteraction.VoiceInteractionManagerService$VoiceInteractionManagerServiceStub$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VoiceInteractionManagerService.VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub2 = VoiceInteractionManagerService.VoiceInteractionManagerServiceStub.this;
                int i = userIdentifier;
                synchronized (voiceInteractionManagerServiceStub2) {
                    voiceInteractionManagerServiceStub2.mCurUser = i;
                    UserInfo userInfo = VoiceInteractionManagerService.this.mUserManagerInternal.getUserInfo(i);
                    VoiceInteractionManagerService.this.getClass();
                    voiceInteractionManagerServiceStub2.mCurUserSupported = userInfo.isFull();
                    voiceInteractionManagerServiceStub2.switchImplementationIfNeededLocked(false);
                }
            }
        });
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        int userIdentifier = targetUser.getUserIdentifier();
        VoiceInteractionManagerServiceStub voiceInteractionManagerServiceStub = this.mServiceStub;
        voiceInteractionManagerServiceStub.initForUser(userIdentifier);
        synchronized (voiceInteractionManagerServiceStub) {
            voiceInteractionManagerServiceStub.switchImplementationIfNeededLocked(false);
        }
    }
}
