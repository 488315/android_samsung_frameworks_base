package com.android.server.tv;

import android.R;
import android.app.ActivityManager;
import android.content.AttributionSource;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.graphics.Rect;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.IHdmiControlService;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.AdResponse;
import android.media.tv.AitInfo;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.DvbDeviceInfo;
import android.media.tv.ITvInputClient;
import android.media.tv.ITvInputHardware;
import android.media.tv.ITvInputHardwareCallback;
import android.media.tv.ITvInputManager;
import android.media.tv.ITvInputManagerCallback;
import android.media.tv.ITvInputService;
import android.media.tv.ITvInputServiceCallback;
import android.media.tv.ITvInputSession;
import android.media.tv.ITvInputSessionCallback;
import android.media.tv.TunedInfo;
import android.media.tv.TvContentRating;
import android.media.tv.TvContentRatingSystemInfo;
import android.media.tv.TvContract;
import android.media.tv.TvInputHardwareInfo;
import android.media.tv.TvInputInfo;
import android.media.tv.TvStreamConfig;
import android.media.tv.tunerresourcemanager.TunerResourceManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SharedMemory;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.Surface;
import com.android.internal.content.PackageMonitor;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.tv.PersistentDataStore;
import com.android.server.tv.TvInputHardwareManager;
import dalvik.annotation.optimization.NeverCompile;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TvInputManagerService extends SystemService {
    public final Context mContext;
    public int mCurrentUserId;
    public final List mExternalInputLoggingDeviceBrandNames;
    public final HashSet mExternalInputLoggingDeviceOnScreenDisplayNames;
    public final boolean mExternalInputLoggingDisplayNameFilterEnabled;
    public final Object mLock;
    public final MessageHandler mMessageHandler;
    public String mOnScreenInputId;
    public SessionState mOnScreenSessionState;
    public final Set mRunningProfiles;
    public final Map mSessionIdToSessionStateMap;
    public final TvInputHardwareManager mTvInputHardwareManager;
    public final UserManager mUserManager;
    public final SparseArray mUserStates;
    public static final Pattern sFrontEndDevicePattern = Pattern.compile("^dvb([0-9]+)\\.frontend([0-9]+)$");
    public static final Pattern sAdapterDirPattern = Pattern.compile("^adapter([0-9]+)$");
    public static final Pattern sFrontEndInAdapterDirPattern = Pattern.compile("^frontend([0-9]+)$");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends ITvInputManager.Stub {
        public BinderService() {
        }

        public final ITvInputHardware acquireTvInputHardware(int i, ITvInputHardwareCallback iTvInputHardwareCallback, TvInputInfo tvInputInfo, int i2, String str, int i3) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return null;
            }
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "acquireTvInputHardware");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return TvInputManagerService.this.mTvInputHardwareManager.acquireHardware(i, iTvInputHardwareCallback, tvInputInfo, callingUid, m984$$Nest$mresolveCallingUserId, str, i3);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void addBlockedRating(String str, int i) {
            ensureParentalControlsPermission();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "addBlockedRating");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    PersistentDataStore persistentDataStore = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).persistentDataStore;
                    TvContentRating unflattenFromString = TvContentRating.unflattenFromString(str);
                    persistentDataStore.loadIfNeeded();
                    if (unflattenFromString != null && !persistentDataStore.mBlockedRatings.contains(unflattenFromString)) {
                        persistentDataStore.mBlockedRatings.add(unflattenFromString);
                        persistentDataStore.mBlockedRatingsChanged = true;
                        Handler handler = persistentDataStore.mHandler;
                        PersistentDataStore.AnonymousClass1 anonymousClass1 = persistentDataStore.mSaveRunnable;
                        handler.removeCallbacks(anonymousClass1);
                        handler.post(anonymousClass1);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void addHardwareDevice(int i) {
            TvInputManagerService.this.mTvInputHardwareManager.onDeviceAvailable(new TvInputHardwareInfo.Builder().deviceId(i).type(9).audioType(0).audioAddress("0").hdmiPortId(0).build(), new TvStreamConfig[]{new TvStreamConfig.Builder().streamId(19001).generation(1).maxHeight(600).maxWidth(800).type(1).build()});
        }

        public final boolean captureFrame(String str, Surface surface, TvStreamConfig tvStreamConfig, int i) {
            String str2;
            IBinder iBinder;
            ensureCaptureTvInputPermission();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "captureFrame");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                    if (orCreateUserStateLocked.inputMap.get(str) == null) {
                        Slog.e("TvInputManagerService", "input not found for " + str);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return false;
                    }
                    Iterator it = ((HashMap) orCreateUserStateLocked.sessionStateMap).values().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            str2 = null;
                            break;
                        }
                        SessionState sessionState = (SessionState) it.next();
                        if (sessionState.inputId.equals(str) && (iBinder = sessionState.hardwareSessionToken) != null) {
                            str2 = ((SessionState) ((HashMap) orCreateUserStateLocked.sessionStateMap).get(iBinder)).inputId;
                            break;
                        }
                    }
                    TvInputHardwareManager tvInputHardwareManager = TvInputManagerService.this.mTvInputHardwareManager;
                    if (str2 != null) {
                        str = str2;
                    }
                    return tvInputHardwareManager.captureFrame(str, surface, tvStreamConfig);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void createOverlayView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "createOverlayView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).createOverlayView(iBinder2, rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in createOverlayView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void createSession(ITvInputClient iTvInputClient, String str, AttributionSource attributionSource, boolean z, int i, int i2) {
            long j;
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, callingPid, callingUid, i2, "createSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            String uuid = UUID.randomUUID().toString();
            try {
                try {
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                            if (i2 != tvInputManagerService.mCurrentUserId) {
                                if (!((HashSet) tvInputManagerService.mRunningProfiles).contains(Integer.valueOf(i2)) && !z) {
                                    TvInputManagerService.this.getClass();
                                    TvInputManagerService.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                                }
                            }
                            UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                            TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
                            if (tvInputState == null) {
                                Slog.w("TvInputManagerService", "Failed to find input state for inputId=" + str);
                                TvInputManagerService.this.getClass();
                                TvInputManagerService.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            TvInputInfo tvInputInfo = tvInputState.info;
                            ServiceState serviceState = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(tvInputInfo.getComponent());
                            if (serviceState == null) {
                                int i3 = PackageManager.getApplicationInfoAsUserCached(tvInputInfo.getComponent().getPackageName(), 0L, m984$$Nest$mresolveCallingUserId).uid;
                                serviceState = new ServiceState(TvInputManagerService.this, tvInputInfo.getComponent(), m984$$Nest$mresolveCallingUserId);
                                ((HashMap) orCreateUserStateLocked.serviceStateMap).put(tvInputInfo.getComponent(), serviceState);
                            }
                            ServiceState serviceState2 = serviceState;
                            if (serviceState2.reconnecting) {
                                TvInputManagerService.this.getClass();
                                TvInputManagerService.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            Binder binder = new Binder();
                            SessionState sessionState = TvInputManagerService.this.new SessionState(binder, tvInputInfo.getId(), tvInputInfo.getComponent(), z, iTvInputClient, i, callingUid, callingPid, m984$$Nest$mresolveCallingUserId, uuid, attributionSource);
                            ((HashMap) orCreateUserStateLocked.sessionStateMap).put(binder, sessionState);
                            ((HashMap) TvInputManagerService.this.mSessionIdToSessionStateMap).put(uuid, sessionState);
                            ((ArrayList) serviceState2.sessionTokens).add(binder);
                            ITvInputService iTvInputService = serviceState2.service;
                            if (iTvInputService == null) {
                                TvInputManagerService.this.updateServiceConnectionLocked(m984$$Nest$mresolveCallingUserId, tvInputInfo.getComponent());
                            } else if (!TvInputManagerService.m979$$Nest$mcreateSessionInternalLocked(TvInputManagerService.this, iTvInputService, binder, m984$$Nest$mresolveCallingUserId)) {
                                TvInputManagerService.this.removeSessionStateLocked(m984$$Nest$mresolveCallingUserId, binder);
                            }
                            TvInputManagerService.m982$$Nest$mlogTuneStateChanged(TvInputManagerService.this, 1, sessionState, tvInputState);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (Throwable th) {
                            th = th;
                            j = clearCallingIdentity;
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                th = th2;
                                Binder.restoreCallingIdentity(j);
                                throw th;
                            }
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
                j = clearCallingIdentity;
            }
        }

        public final void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i4, "dispatchSurfaceChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                        TvInputManagerService.this.getClass();
                        TvInputManagerService.getSessionLocked(sessionStateLocked).dispatchSurfaceChanged(i, i2, i3);
                        IBinder iBinder2 = sessionStateLocked.hardwareSessionToken;
                        if (iBinder2 != null) {
                            TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder2, 1000, m984$$Nest$mresolveCallingUserId).dispatchSurfaceChanged(i, i2, i3);
                        }
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in dispatchSurfaceChanged", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @NeverCompile
        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            int i;
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
            if (DumpUtils.checkDumpPermission(TvInputManagerService.this.mContext, "TvInputManagerService", indentingPrintWriter)) {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        indentingPrintWriter.println("User Ids (Current user: " + TvInputManagerService.this.mCurrentUserId + "):");
                        indentingPrintWriter.increaseIndent();
                        for (int i2 = 0; i2 < TvInputManagerService.this.mUserStates.size(); i2++) {
                            indentingPrintWriter.println(Integer.valueOf(TvInputManagerService.this.mUserStates.keyAt(i2)));
                        }
                        indentingPrintWriter.decreaseIndent();
                        for (int i3 = 0; i3 < TvInputManagerService.this.mUserStates.size(); i3++) {
                            int keyAt = TvInputManagerService.this.mUserStates.keyAt(i3);
                            UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(keyAt);
                            indentingPrintWriter.println("UserState (" + keyAt + "):");
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("inputMap: inputId -> TvInputState");
                            indentingPrintWriter.increaseIndent();
                            for (Map.Entry entry : orCreateUserStateLocked.inputMap.entrySet()) {
                                indentingPrintWriter.println(((String) entry.getKey()) + ": " + entry.getValue());
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("packageSet:");
                            indentingPrintWriter.increaseIndent();
                            Iterator it = ((HashSet) orCreateUserStateLocked.packageSet).iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter.println((String) it.next());
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("clientStateMap: ITvInputClient -> ClientState");
                            indentingPrintWriter.increaseIndent();
                            for (Map.Entry entry2 : ((HashMap) orCreateUserStateLocked.clientStateMap).entrySet()) {
                                ClientState clientState = (ClientState) entry2.getValue();
                                indentingPrintWriter.println(entry2.getKey() + ": " + clientState);
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.println("sessionTokens:");
                                indentingPrintWriter.increaseIndent();
                                Iterator it2 = ((ArrayList) clientState.sessionTokens).iterator();
                                while (it2.hasNext()) {
                                    indentingPrintWriter.println("" + ((IBinder) it2.next()));
                                }
                                indentingPrintWriter.decreaseIndent();
                                indentingPrintWriter.println("clientTokens: " + clientState.clientToken);
                                indentingPrintWriter.println("userId: " + clientState.userId);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("serviceStateMap: ComponentName -> ServiceState");
                            indentingPrintWriter.increaseIndent();
                            for (Map.Entry entry3 : ((HashMap) orCreateUserStateLocked.serviceStateMap).entrySet()) {
                                ServiceState serviceState = (ServiceState) entry3.getValue();
                                indentingPrintWriter.println(entry3.getKey() + ": " + serviceState);
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.println("sessionTokens:");
                                indentingPrintWriter.increaseIndent();
                                Iterator it3 = ((ArrayList) serviceState.sessionTokens).iterator();
                                while (it3.hasNext()) {
                                    indentingPrintWriter.println("" + ((IBinder) it3.next()));
                                }
                                indentingPrintWriter.decreaseIndent();
                                indentingPrintWriter.println("service: " + serviceState.service);
                                indentingPrintWriter.println("callback: " + serviceState.callback);
                                indentingPrintWriter.println("bound: " + serviceState.bound);
                                indentingPrintWriter.println("reconnecting: " + serviceState.reconnecting);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("sessionStateMap: ITvInputSession -> SessionState");
                            indentingPrintWriter.increaseIndent();
                            for (Map.Entry entry4 : ((HashMap) orCreateUserStateLocked.sessionStateMap).entrySet()) {
                                SessionState sessionState = (SessionState) entry4.getValue();
                                indentingPrintWriter.println(entry4.getKey() + ": " + sessionState);
                                indentingPrintWriter.increaseIndent();
                                indentingPrintWriter.println("inputId: " + sessionState.inputId);
                                indentingPrintWriter.println("sessionId: " + sessionState.sessionId);
                                indentingPrintWriter.println("client: " + sessionState.client);
                                indentingPrintWriter.println("seq: " + sessionState.seq);
                                indentingPrintWriter.println("callingUid: " + sessionState.callingUid);
                                indentingPrintWriter.println("callingPid: " + sessionState.callingPid);
                                indentingPrintWriter.println("userId: " + sessionState.userId);
                                indentingPrintWriter.println("sessionToken: " + sessionState.sessionToken);
                                indentingPrintWriter.println("session: " + sessionState.session);
                                indentingPrintWriter.println("hardwareSessionToken: " + sessionState.hardwareSessionToken);
                                indentingPrintWriter.decreaseIndent();
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("mCallbacks:");
                            indentingPrintWriter.increaseIndent();
                            int beginBroadcast = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                            for (int i4 = 0; i4 < beginBroadcast; i4++) {
                                indentingPrintWriter.println(orCreateUserStateLocked.mCallbacks.getRegisteredCallbackItem(i4));
                            }
                            orCreateUserStateLocked.mCallbacks.finishBroadcast();
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.println("mainSessionToken: " + orCreateUserStateLocked.mainSessionToken);
                            indentingPrintWriter.decreaseIndent();
                        }
                    } finally {
                    }
                }
                TvInputHardwareManager tvInputHardwareManager = TvInputManagerService.this.mTvInputHardwareManager;
                tvInputHardwareManager.getClass();
                IndentingPrintWriter indentingPrintWriter2 = new IndentingPrintWriter(printWriter, "  ");
                if (DumpUtils.checkDumpPermission(tvInputHardwareManager.mContext, "TvInputHardwareManager", indentingPrintWriter2)) {
                    synchronized (tvInputHardwareManager.mLock) {
                        try {
                            indentingPrintWriter2.println("TvInputHardwareManager Info:");
                            indentingPrintWriter2.increaseIndent();
                            indentingPrintWriter2.println("mConnections: deviceId -> Connection");
                            indentingPrintWriter2.increaseIndent();
                            for (int i5 = 0; i5 < tvInputHardwareManager.mConnections.size(); i5++) {
                                indentingPrintWriter2.println(tvInputHardwareManager.mConnections.keyAt(i5) + ": " + ((TvInputHardwareManager.Connection) tvInputHardwareManager.mConnections.valueAt(i5)));
                            }
                            indentingPrintWriter2.decreaseIndent();
                            indentingPrintWriter2.println("mHardwareList:");
                            indentingPrintWriter2.increaseIndent();
                            Iterator it4 = ((ArrayList) tvInputHardwareManager.mHardwareList).iterator();
                            while (it4.hasNext()) {
                                indentingPrintWriter2.println((TvInputHardwareInfo) it4.next());
                            }
                            indentingPrintWriter2.decreaseIndent();
                            indentingPrintWriter2.println("mHdmiDeviceList:");
                            indentingPrintWriter2.increaseIndent();
                            Iterator it5 = ((ArrayList) tvInputHardwareManager.mHdmiDeviceList).iterator();
                            while (it5.hasNext()) {
                                indentingPrintWriter2.println((HdmiDeviceInfo) it5.next());
                            }
                            indentingPrintWriter2.decreaseIndent();
                            indentingPrintWriter2.println("mHardwareInputIdMap: deviceId -> inputId");
                            indentingPrintWriter2.increaseIndent();
                            for (int i6 = 0; i6 < tvInputHardwareManager.mHardwareInputIdMap.size(); i6++) {
                                indentingPrintWriter2.println(tvInputHardwareManager.mHardwareInputIdMap.keyAt(i6) + ": " + ((String) tvInputHardwareManager.mHardwareInputIdMap.valueAt(i6)));
                            }
                            indentingPrintWriter2.decreaseIndent();
                            indentingPrintWriter2.println("mHdmiInputIdMap: id -> inputId");
                            indentingPrintWriter2.increaseIndent();
                            for (i = 0; i < tvInputHardwareManager.mHdmiInputIdMap.size(); i++) {
                                indentingPrintWriter2.println(tvInputHardwareManager.mHdmiInputIdMap.keyAt(i) + ": " + ((String) tvInputHardwareManager.mHdmiInputIdMap.valueAt(i)));
                            }
                            indentingPrintWriter2.decreaseIndent();
                            indentingPrintWriter2.println("mInputMap: inputId -> inputInfo");
                            indentingPrintWriter2.increaseIndent();
                            for (Map.Entry entry5 : ((ArrayMap) tvInputHardwareManager.mInputMap).entrySet()) {
                                indentingPrintWriter2.println(((String) entry5.getKey()) + ": " + entry5.getValue());
                            }
                            indentingPrintWriter2.decreaseIndent();
                            indentingPrintWriter2.decreaseIndent();
                        } finally {
                        }
                    }
                }
            }
        }

        public final void ensureCaptureTvInputPermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.CAPTURE_TV_INPUT") != 0) {
                throw new SecurityException("Requires CAPTURE_TV_INPUT permission");
            }
        }

        public final void ensureParentalControlsPermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.MODIFY_PARENTAL_CONTROLS") != 0) {
                throw new SecurityException("The caller does not have parental controls permission");
            }
        }

        public final List getAvailableExtensionInterfaceNames(String str, int i) {
            ITvInputService iTvInputService;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TIS_EXTENSION_INTERFACE") != 0) {
                throw new SecurityException("Requires TIS_EXTENSION_INTERFACE permission");
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, callingPid, callingUid, i, "getAvailableExtensionInterfaceNames");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                        TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
                        if (tvInputState != null) {
                            ServiceState serviceState = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(tvInputState.info.getComponent());
                            if (serviceState != null && serviceState.isHardware && (iTvInputService = serviceState.service) != null) {
                            }
                        }
                        iTvInputService = null;
                    } finally {
                    }
                }
                if (iTvInputService != null) {
                    try {
                        ArrayList arrayList = new ArrayList();
                        for (String str2 : CollectionUtils.emptyIfNull(iTvInputService.getAvailableExtensionInterfaceNames())) {
                            String extensionInterfacePermission = iTvInputService.getExtensionInterfacePermission(str2);
                            if (extensionInterfacePermission != null && TvInputManagerService.this.mContext.checkPermission(extensionInterfacePermission, callingPid, callingUid) != 0) {
                            }
                            arrayList.add(str2);
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return arrayList;
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in getAvailableExtensionInterfaceNames or getExtensionInterfacePermission", e);
                    }
                }
                ArrayList arrayList2 = new ArrayList();
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return arrayList2;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final List getAvailableTvStreamConfigList(String str, int i) {
            ensureCaptureTvInputPermission();
            TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "getAvailableTvStreamConfigList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return TvInputManagerService.this.mTvInputHardwareManager.getAvailableTvStreamConfigList(str);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getBlockedRatings(int i) {
            ArrayList arrayList;
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "getBlockedRatings");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                        arrayList = new ArrayList();
                        PersistentDataStore persistentDataStore = orCreateUserStateLocked.persistentDataStore;
                        persistentDataStore.loadIfNeeded();
                        List list = persistentDataStore.mBlockedRatings;
                        for (TvContentRating tvContentRating : (TvContentRating[]) list.toArray(new TvContentRating[list.size()])) {
                            arrayList.add(tvContentRating.flattenToString());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return arrayList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final String getCallingPackageName() {
            String[] packagesForUid = TvInputManagerService.this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
            return (packagesForUid == null || packagesForUid.length <= 0) ? "unknown" : packagesForUid[0];
        }

        public final int getClientPid(String str) {
            int i;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TUNER_RESOURCE_ACCESS") != 0) {
                throw new SecurityException("Requires TUNER_RESOURCE_ACCESS permission");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        i = getClientPidLocked(str);
                    } catch (ClientPidNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in getClientPid", e);
                        i = -1;
                    }
                }
                return i;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getClientPidLocked(String str) {
            if (((HashMap) TvInputManagerService.this.mSessionIdToSessionStateMap).get(str) != null) {
                return ((SessionState) ((HashMap) TvInputManagerService.this.mSessionIdToSessionStateMap).get(str)).callingPid;
            }
            throw new ClientPidNotFoundException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Client Pid not found with sessionId ", str));
        }

        public final int getClientPriority(int i, String str) {
            int i2;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TUNER_RESOURCE_ACCESS") != 0) {
                throw new SecurityException("Requires TUNER_RESOURCE_ACCESS permission");
            }
            int callingPid = Binder.getCallingPid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            if (str != null) {
                try {
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            i2 = getClientPidLocked(str);
                        } catch (ClientPidNotFoundException e) {
                            Slog.e("TvInputManagerService", "error in getClientPriority", e);
                            i2 = -1;
                        }
                    }
                    callingPid = i2;
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
            int clientPriority = ((TunerResourceManager) TvInputManagerService.this.mContext.getSystemService("tv_tuner_resource_mgr")).getClientPriority(i, callingPid);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return clientPriority;
        }

        public final List getCurrentTunedInfos(int i) {
            List currentTunedInfosInternalLocked;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.ACCESS_TUNED_INFO") != 0) {
                throw new SecurityException("The caller does not have access tuned info permission");
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, callingPid, callingUid, i, "getTvCurrentChannelInfos");
            synchronized (TvInputManagerService.this.mLock) {
                currentTunedInfosInternalLocked = TvInputManagerService.this.getCurrentTunedInfosInternalLocked(TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId), callingPid, callingUid);
            }
            return currentTunedInfosInternalLocked;
        }

        public final List getDvbDeviceList() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.DVB_DEVICE") != 0) {
                throw new SecurityException("Requires DVB_DEVICE permission");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ArrayList arrayList = new ArrayList();
                boolean z = false;
                for (String str : new File("/dev").list()) {
                    Matcher matcher = TvInputManagerService.sFrontEndDevicePattern.matcher(str);
                    if (matcher.find()) {
                        arrayList.add(new DvbDeviceInfo(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
                    }
                    if (TextUtils.equals("dvb", str)) {
                        z = true;
                    }
                }
                if (!z) {
                    List unmodifiableList = Collections.unmodifiableList(arrayList);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return unmodifiableList;
                }
                File file = new File("/dev/dvb");
                ArrayList arrayList2 = new ArrayList();
                for (String str2 : file.list()) {
                    Matcher matcher2 = TvInputManagerService.sAdapterDirPattern.matcher(str2);
                    if (matcher2.find()) {
                        int parseInt = Integer.parseInt(matcher2.group(1));
                        String[] list = new File("/dev/dvb/" + str2).list();
                        int length = list.length;
                        for (int i = 0; i < length; i++) {
                            Matcher matcher3 = TvInputManagerService.sFrontEndInAdapterDirPattern.matcher(list[i]);
                            if (matcher3.find()) {
                                arrayList2.add(new DvbDeviceInfo(parseInt, Integer.parseInt(matcher3.group(1))));
                            }
                        }
                    }
                }
                List unmodifiableList2 = arrayList2.isEmpty() ? Collections.unmodifiableList(arrayList) : Collections.unmodifiableList(arrayList2);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return unmodifiableList2;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final IBinder getExtensionInterface(String str, String str2, int i) {
            ITvInputService iTvInputService;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TIS_EXTENSION_INTERFACE") != 0) {
                throw new SecurityException("Requires TIS_EXTENSION_INTERFACE permission");
            }
            int callingUid = Binder.getCallingUid();
            int callingPid = Binder.getCallingPid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, callingPid, callingUid, i, "getExtensionInterface");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                        TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
                        if (tvInputState != null) {
                            ServiceState serviceState = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(tvInputState.info.getComponent());
                            if (serviceState != null && serviceState.isHardware && (iTvInputService = serviceState.service) != null) {
                            }
                        }
                        iTvInputService = null;
                    } finally {
                    }
                }
                if (iTvInputService != null) {
                    try {
                        String extensionInterfacePermission = iTvInputService.getExtensionInterfacePermission(str2);
                        if (extensionInterfacePermission != null) {
                            if (TvInputManagerService.this.mContext.checkPermission(extensionInterfacePermission, callingPid, callingUid) == 0) {
                            }
                        }
                        IBinder extensionInterface = iTvInputService.getExtensionInterface(str2);
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return extensionInterface;
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in getExtensionInterfacePermission or getExtensionInterface", e);
                    }
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final List getHardwareList() {
            List unmodifiableList;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return null;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                TvInputHardwareManager tvInputHardwareManager = TvInputManagerService.this.mTvInputHardwareManager;
                synchronized (tvInputHardwareManager.mLock) {
                    unmodifiableList = Collections.unmodifiableList(tvInputHardwareManager.mHardwareList);
                }
                return unmodifiableList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getTvContentRatingSystemList(int i) {
            List list;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.READ_CONTENT_RATING_SYSTEMS") != 0) {
                throw new SecurityException("The caller does not have permission to read content rating systems");
            }
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvContentRatingSystemList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    list = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).contentRatingSystemList;
                }
                return list;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final TvInputInfo getTvInputInfo(String str, int i) {
            TvInputInfo tvInputInfo;
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvInputInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputState tvInputState = (TvInputState) TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).inputMap.get(str);
                    tvInputInfo = tvInputState == null ? null : tvInputState.info;
                }
                return tvInputInfo;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getTvInputList(int i) {
            ArrayList arrayList;
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvInputList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                        arrayList = new ArrayList();
                        Iterator it = orCreateUserStateLocked.inputMap.values().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((TvInputState) it.next()).info);
                        }
                    } finally {
                    }
                }
                return arrayList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getTvInputState(String str, int i) {
            int i2;
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "getTvInputState");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputState tvInputState = (TvInputState) TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).inputMap.get(str);
                    i2 = tvInputState == null ? 0 : tvInputState.state;
                }
                return i2;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isParentalControlsEnabled(int i) {
            boolean z;
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "isParentalControlsEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    PersistentDataStore persistentDataStore = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).persistentDataStore;
                    persistentDataStore.loadIfNeeded();
                    z = persistentDataStore.mParentalControlsEnabled;
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isRatingBlocked(String str, int i) {
            boolean z;
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "isRatingBlocked");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    PersistentDataStore persistentDataStore = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).persistentDataStore;
                    TvContentRating unflattenFromString = TvContentRating.unflattenFromString(str);
                    persistentDataStore.loadIfNeeded();
                    synchronized (persistentDataStore.mBlockedRatings) {
                        try {
                            Iterator it = persistentDataStore.mBlockedRatings.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    z = false;
                                    break;
                                }
                                if (unflattenFromString.contains((TvContentRating) it.next())) {
                                    z = true;
                                    break;
                                }
                            }
                        } finally {
                        }
                    }
                }
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isSingleSessionActive(int i) {
            ensureCaptureTvInputPermission();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "isSingleSessionActive");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                    boolean z = true;
                    if (((HashMap) orCreateUserStateLocked.sessionStateMap).size() == 1) {
                        return true;
                    }
                    if (((HashMap) orCreateUserStateLocked.sessionStateMap).size() != 2) {
                        return false;
                    }
                    SessionState[] sessionStateArr = (SessionState[]) ((HashMap) orCreateUserStateLocked.sessionStateMap).values().toArray(new SessionState[2]);
                    if (sessionStateArr[0].hardwareSessionToken == null && sessionStateArr[1].hardwareSessionToken == null) {
                        z = false;
                    }
                    return z;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyAdBufferReady(IBinder iBinder, AdBuffer adBuffer, int i) {
            SharedMemory sharedMemory;
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "notifyAdBuffer");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                            TvInputManagerService.this.getClass();
                            TvInputManagerService.getSessionLocked(sessionStateLocked).notifyAdBufferReady(adBuffer);
                        } catch (RemoteException | SessionNotFoundException e) {
                            Slog.e("TvInputManagerService", "error in notifyAdBuffer", e);
                            if (adBuffer != null) {
                                sharedMemory = adBuffer.getSharedMemory();
                            }
                        }
                        if (adBuffer != null) {
                            sharedMemory = adBuffer.getSharedMemory();
                            sharedMemory.close();
                        }
                    }
                } catch (Throwable th) {
                    if (adBuffer != null) {
                        adBuffer.getSharedMemory().close();
                    }
                    throw th;
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTvAdSessionData(IBinder iBinder, String str, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "notifyTvAdSessionData");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                        TvInputManagerService.this.getClass();
                        TvInputManagerService.getSessionLocked(sessionStateLocked).notifyTvAdSessionData(str, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in notifyTvAdSessionData", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "notifyTvmessage");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).notifyTvMessage(i, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in notifyTvMessage", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParcelFileDescriptor openDvbDevice(DvbDeviceInfo dvbDeviceInfo, int i) {
            String format;
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.DVB_DEVICE") != 0) {
                throw new SecurityException("Requires DVB_DEVICE permission");
            }
            boolean z = false;
            for (String str : new File("/dev").list()) {
                if (TextUtils.equals("dvb", str)) {
                    for (String str2 : new File("/dev/dvb").list()) {
                        if (TvInputManagerService.sAdapterDirPattern.matcher(str2).find()) {
                            String[] list = new File(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("/dev/dvb/", str2)).list();
                            int length = list.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    break;
                                }
                                if (TvInputManagerService.sFrontEndInAdapterDirPattern.matcher(list[i2]).find()) {
                                    z = true;
                                    break;
                                }
                                i2++;
                            }
                        }
                        if (z) {
                            break;
                        }
                    }
                }
                if (z) {
                    break;
                }
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i == 0) {
                    format = String.format(z ? "/dev/dvb/adapter%d/demux%d" : "/dev/dvb%d.demux%d", Integer.valueOf(dvbDeviceInfo.getAdapterId()), Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                } else if (i == 1) {
                    format = String.format(z ? "/dev/dvb/adapter%d/dvr%d" : "/dev/dvb%d.dvr%d", Integer.valueOf(dvbDeviceInfo.getAdapterId()), Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                } else {
                    if (i != 2) {
                        throw new IllegalArgumentException("Invalid DVB device: " + i);
                    }
                    format = String.format(z ? "/dev/dvb/adapter%d/frontend%d" : "/dev/dvb%d.frontend%d", Integer.valueOf(dvbDeviceInfo.getAdapterId()), Integer.valueOf(dvbDeviceInfo.getDeviceId()));
                }
                try {
                    ParcelFileDescriptor open = ParcelFileDescriptor.open(new File(format), 2 == i ? 805306368 : 268435456);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return open;
                } catch (FileNotFoundException unused) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return null;
                }
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void pauseRecording(IBinder iBinder, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "pauseRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).pauseRecording(bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in pauseRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) {
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, callingPid, callingUid, i, "registerCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                        if (orCreateUserStateLocked.mCallbacks.register(iTvInputManagerCallback)) {
                            ((HashMap) orCreateUserStateLocked.callbackPidUidMap).put(iTvInputManagerCallback, Pair.create(Integer.valueOf(callingPid), Integer.valueOf(callingUid)));
                        } else {
                            Slog.e("TvInputManagerService", "client process has already died");
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void relayoutOverlayView(IBinder iBinder, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "relayoutOverlayView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).relayoutOverlayView(rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in relayoutOverlayView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseSession(IBinder iBinder, int i) {
            SessionState releaseSessionLocked;
            UserState userStateLocked;
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "releaseSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    releaseSessionLocked = TvInputManagerService.this.releaseSessionLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                    userStateLocked = TvInputManagerService.this.getUserStateLocked(i);
                }
                if (releaseSessionLocked != null) {
                    TvInputManagerService.m982$$Nest$mlogTuneStateChanged(TvInputManagerService.this, 4, releaseSessionLocked, TvInputManagerService.m988$$Nest$smgetTvInputState(releaseSessionLocked, userStateLocked));
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseTvInputHardware(int i, ITvInputHardware iTvInputHardware, int i2) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                return;
            }
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "releaseTvInputHardware");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                TvInputManagerService.this.mTvInputHardwareManager.releaseHardware(i, iTvInputHardware, callingUid, m984$$Nest$mresolveCallingUserId);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeBlockedRating(String str, int i) {
            ensureParentalControlsPermission();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "removeBlockedRating");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    PersistentDataStore persistentDataStore = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).persistentDataStore;
                    TvContentRating unflattenFromString = TvContentRating.unflattenFromString(str);
                    persistentDataStore.loadIfNeeded();
                    if (unflattenFromString != null && persistentDataStore.mBlockedRatings.contains(unflattenFromString)) {
                        persistentDataStore.mBlockedRatings.remove(unflattenFromString);
                        persistentDataStore.mBlockedRatingsChanged = true;
                        Handler handler = persistentDataStore.mHandler;
                        PersistentDataStore.AnonymousClass1 anonymousClass1 = persistentDataStore.mSaveRunnable;
                        handler.removeCallbacks(anonymousClass1);
                        handler.post(anonymousClass1);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeBroadcastInfo(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "removeBroadcastInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                        TvInputManagerService.this.getClass();
                        TvInputManagerService.getSessionLocked(sessionStateLocked).removeBroadcastInfo(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in removeBroadcastInfo", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeHardwareDevice(int i) {
            TvInputManagerService.this.mTvInputHardwareManager.onDeviceUnavailable(i);
        }

        public final void removeOverlayView(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "removeOverlayView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).removeOverlayView();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in removeOverlayView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestAd(IBinder iBinder, AdRequest adRequest, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "requestAd");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                        TvInputManagerService.this.getClass();
                        TvInputManagerService.getSessionLocked(sessionStateLocked).requestAd(adRequest);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in requestAd", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestBroadcastInfo(IBinder iBinder, BroadcastInfoRequest broadcastInfoRequest, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "requestBroadcastInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                        TvInputManagerService.this.getClass();
                        TvInputManagerService.getSessionLocked(sessionStateLocked).requestBroadcastInfo(broadcastInfoRequest);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in requestBroadcastInfo", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void requestChannelBrowsable(Uri uri, int i) {
            String callingPackageName = getCallingPackageName();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "requestChannelBrowsable");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Intent intent = new Intent("android.media.tv.action.CHANNEL_BROWSABLE_REQUESTED");
                List<ResolveInfo> queryBroadcastReceivers = TvInputManagerService.this.getContext().getPackageManager().queryBroadcastReceivers(intent, 0);
                if (queryBroadcastReceivers != null) {
                    Iterator<ResolveInfo> it = queryBroadcastReceivers.iterator();
                    while (it.hasNext()) {
                        String str = it.next().activityInfo.packageName;
                        intent.putExtra("android.media.tv.extra.CHANNEL_ID", ContentUris.parseId(uri));
                        intent.putExtra("android.media.tv.extra.PACKAGE_NAME", callingPackageName);
                        intent.setPackage(str);
                        TvInputManagerService.this.getContext().sendBroadcastAsUser(intent, new UserHandle(m984$$Nest$mresolveCallingUserId));
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resumePlayback(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "resumePlayback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).resumePlayback();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in resumePlayback()", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resumeRecording(IBinder iBinder, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "resumeRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).resumeRecording(bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in resumeRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void selectAudioPresentation(IBinder iBinder, int i, int i2, int i3) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i3, "selectAudioPresentation");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).selectAudioPresentation(i, i2);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in selectAudioPresentation", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void selectTrack(IBinder iBinder, int i, String str, int i2) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "selectTrack");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).selectTrack(i, str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in selectTrack", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendAppPrivateCommand(IBinder iBinder, String str, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "sendAppPrivateCommand");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).appPrivateCommand(str, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in appPrivateCommand", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendTvInputNotifyIntent(Intent intent, int i) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.NOTIFY_TV_INPUTS") != 0) {
                throw new SecurityException("The caller: " + getCallingPackageName() + " doesn't have permission: android.permission.NOTIFY_TV_INPUTS");
            }
            if (TextUtils.isEmpty(intent.getPackage())) {
                throw new IllegalArgumentException("Must specify package name to notify.");
            }
            String action = intent.getAction();
            action.getClass();
            switch (action) {
                case "android.media.tv.action.WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED":
                    if (intent.getLongExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid watch next program ID.");
                    }
                    break;
                case "android.media.tv.action.PREVIEW_PROGRAM_BROWSABLE_DISABLED":
                    if (intent.getLongExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid preview program ID.");
                    }
                    break;
                case "android.media.tv.action.PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT":
                    if (intent.getLongExtra("android.media.tv.extra.PREVIEW_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid preview program ID.");
                    }
                    if (intent.getLongExtra("android.media.tv.extra.WATCH_NEXT_PROGRAM_ID", -1L) < 0) {
                        throw new IllegalArgumentException("Invalid watch next program ID.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid TV input notifying action: " + intent.getAction());
            }
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "sendTvInputNotifyIntent");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                TvInputManagerService.this.getContext().sendBroadcastAsUser(intent, new UserHandle(m984$$Nest$mresolveCallingUserId));
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setCaptionEnabled(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "setCaptionEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).setCaptionEnabled(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setCaptionEnabled", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setInteractiveAppNotificationEnabled(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "setInteractiveAppNotificationEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).setInteractiveAppNotificationEnabled(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setInteractiveAppNotificationEnabled", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setMainSession(IBinder iBinder, int i) {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.CHANGE_HDMI_CEC_ACTIVE_SOURCE") != 0) {
                throw new SecurityException("The caller does not have CHANGE_HDMI_CEC_ACTIVE_SOURCE permission");
            }
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "setMainSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                    IBinder iBinder2 = orCreateUserStateLocked.mainSessionToken;
                    if (iBinder2 == iBinder) {
                        return;
                    }
                    orCreateUserStateLocked.mainSessionToken = iBinder;
                    if (iBinder != null) {
                        TvInputManagerService.this.setMainLocked(callingUid, i, iBinder, true);
                    }
                    if (iBinder2 != null) {
                        TvInputManagerService.this.setMainLocked(1000, i, iBinder2, false);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setParentalControlsEnabled(boolean z, int i) {
            ensureParentalControlsPermission();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "setParentalControlsEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    PersistentDataStore persistentDataStore = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId).persistentDataStore;
                    persistentDataStore.loadIfNeeded();
                    if (persistentDataStore.mParentalControlsEnabled != z) {
                        persistentDataStore.mParentalControlsEnabled = z;
                        persistentDataStore.mParentalControlsEnabledChanged = true;
                        Handler handler = persistentDataStore.mHandler;
                        PersistentDataStore.AnonymousClass1 anonymousClass1 = persistentDataStore.mSaveRunnable;
                        handler.removeCallbacks(anonymousClass1);
                        handler.post(anonymousClass1);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:21:0x006c  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0071  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setSurface(android.os.IBinder r10, android.view.Surface r11, int r12) {
            /*
                r9 = this;
                int r0 = android.os.Binder.getCallingUid()
                com.android.server.tv.TvInputManagerService r1 = com.android.server.tv.TvInputManagerService.this
                int r2 = android.os.Binder.getCallingPid()
                java.lang.String r3 = "setSurface"
                int r1 = com.android.server.tv.TvInputManagerService.m984$$Nest$mresolveCallingUserId(r1, r2, r0, r12, r3)
                long r2 = android.os.Binder.clearCallingIdentity()
                r4 = 2
                r5 = 3
                r6 = 0
                com.android.server.tv.TvInputManagerService r7 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L85
                java.lang.Object r7 = r7.mLock     // Catch: java.lang.Throwable -> L85
                monitor-enter(r7)     // Catch: java.lang.Throwable -> L85
                com.android.server.tv.TvInputManagerService r8 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L60
                com.android.server.tv.TvInputManagerService$UserState r12 = r8.getUserStateLocked(r12)     // Catch: java.lang.Throwable -> L5e java.lang.Throwable -> L60
                com.android.server.tv.TvInputManagerService r8 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                com.android.server.tv.TvInputManagerService$SessionState r6 = r8.getSessionStateLocked(r0, r10, r1)     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                android.os.IBinder r10 = r6.hardwareSessionToken     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                if (r10 != 0) goto L3e
                com.android.server.tv.TvInputManagerService r10 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                r10.getClass()     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                android.media.tv.ITvInputSession r10 = com.android.server.tv.TvInputManagerService.getSessionLocked(r6)     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                r10.setSurface(r11)     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                goto L49
            L3a:
                r10 = move-exception
                goto L81
            L3c:
                r10 = move-exception
                goto L62
            L3e:
                com.android.server.tv.TvInputManagerService r0 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                r8 = 1000(0x3e8, float:1.401E-42)
                android.media.tv.ITvInputSession r10 = com.android.server.tv.TvInputManagerService.m981$$Nest$mgetSessionLocked(r0, r10, r8, r1)     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                r10.setSurface(r11)     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
            L49:
                if (r11 != 0) goto L4d
                r10 = 1
                goto L4e
            L4d:
                r10 = 0
            L4e:
                boolean r0 = r6.isVisible     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                if (r0 == r10) goto L69
                r6.isVisible = r10     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                com.android.server.tv.TvInputManagerService r10 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                r10.notifyCurrentChannelInfosUpdatedLocked(r12)     // Catch: java.lang.Throwable -> L3a java.lang.Throwable -> L3c
                goto L69
            L5a:
                r12 = r6
                goto L81
            L5c:
                r12 = r6
                goto L62
            L5e:
                r10 = move-exception
                goto L5a
            L60:
                r10 = move-exception
                goto L5c
            L62:
                java.lang.String r0 = "TvInputManagerService"
                java.lang.String r1 = "error in setSurface"
                android.util.Slog.e(r0, r1, r10)     // Catch: java.lang.Throwable -> L3a
            L69:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L3a
                if (r11 == 0) goto L6f
                r11.release()
            L6f:
                if (r6 == 0) goto L7d
                if (r11 != 0) goto L74
                r4 = r5
            L74:
                com.android.server.tv.TvInputManagerService r9 = com.android.server.tv.TvInputManagerService.this
                com.android.server.tv.TvInputManagerService$TvInputState r10 = com.android.server.tv.TvInputManagerService.m988$$Nest$smgetTvInputState(r6, r12)
                com.android.server.tv.TvInputManagerService.m982$$Nest$mlogTuneStateChanged(r9, r4, r6, r10)
            L7d:
                android.os.Binder.restoreCallingIdentity(r2)
                return
            L81:
                monitor-exit(r7)     // Catch: java.lang.Throwable -> L3a
                throw r10     // Catch: java.lang.Throwable -> L83
            L83:
                r10 = move-exception
                goto L87
            L85:
                r10 = move-exception
                r12 = r6
            L87:
                if (r11 == 0) goto L8c
                r11.release()
            L8c:
                if (r6 == 0) goto L9a
                if (r11 != 0) goto L91
                r4 = r5
            L91:
                com.android.server.tv.TvInputManagerService r9 = com.android.server.tv.TvInputManagerService.this
                com.android.server.tv.TvInputManagerService$TvInputState r11 = com.android.server.tv.TvInputManagerService.m988$$Nest$smgetTvInputState(r6, r12)
                com.android.server.tv.TvInputManagerService.m982$$Nest$mlogTuneStateChanged(r9, r4, r6, r11)
            L9a:
                android.os.Binder.restoreCallingIdentity(r2)
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputManagerService.BinderService.setSurface(android.os.IBinder, android.view.Surface, int):void");
        }

        public final void setTvMessageEnabled(IBinder iBinder, int i, boolean z, int i2) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "setTvMessageEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.this.mTvInputHardwareManager.setTvMessageEnabled(i, TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, i2).inputId, z);
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).setTvMessageEnabled(i, z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setTvMessageEnabled", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setVideoFrozen(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "setVideoFrozen");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).setVideoFrozen(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setVideoFrozen", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setVolume(IBinder iBinder, float f, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "setVolume");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked = TvInputManagerService.this.getSessionStateLocked(callingUid, iBinder, m984$$Nest$mresolveCallingUserId);
                        TvInputManagerService.this.getClass();
                        TvInputManagerService.getSessionLocked(sessionStateLocked).setVolume(f);
                        IBinder iBinder2 = sessionStateLocked.hardwareSessionToken;
                        if (iBinder2 != null) {
                            ITvInputSession m981$$Nest$mgetSessionLocked = TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder2, 1000, m984$$Nest$mresolveCallingUserId);
                            float f2 = FullScreenMagnificationGestureHandler.MAX_SCALE;
                            if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                                f2 = 1.0f;
                            }
                            m981$$Nest$mgetSessionLocked.setVolume(f2);
                        }
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in setVolume", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startRecording(IBinder iBinder, Uri uri, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "startRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).startRecording(uri, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in startRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void stopPlayback(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "stopPlayback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).stopPlayback(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in stopPlayback(mode)", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void stopRecording(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "stopRecording");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).stopRecording();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in stopRecording", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void timeShiftEnablePositionTracking(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "timeShiftEnablePositionTracking");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).timeShiftEnablePositionTracking(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftEnablePositionTracking", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void timeShiftPause(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "timeShiftPause");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).timeShiftPause();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftPause", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void timeShiftPlay(IBinder iBinder, Uri uri, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "timeShiftPlay");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).timeShiftPlay(uri);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftPlay", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void timeShiftResume(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "timeShiftResume");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).timeShiftResume();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftResume", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void timeShiftSeekTo(IBinder iBinder, long j, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "timeShiftSeekTo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).timeShiftSeekTo(j);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftSeekTo", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void timeShiftSetMode(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i2, "timeShiftSetMode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).timeShiftSetMode(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftSetMode", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void timeShiftSetPlaybackParams(IBinder iBinder, PlaybackParams playbackParams, int i) {
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "timeShiftSetPlaybackParams");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).timeShiftSetPlaybackParams(playbackParams);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in timeShiftSetPlaybackParams", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0073 A[Catch: all -> 0x0040, DONT_GENERATE, TRY_ENTER, TRY_LEAVE, TryCatch #0 {all -> 0x0040, blocks: (B:6:0x001a, B:8:0x0037, B:11:0x006d, B:13:0x0073, B:17:0x0078, B:19:0x007c, B:22:0x0081, B:23:0x00c2, B:27:0x0045, B:29:0x0052, B:31:0x0062, B:32:0x0067, B:39:0x00bb), top: B:5:0x001a, outer: #2, inners: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0078 A[Catch: all -> 0x0040, RemoteException | SessionNotFoundException -> 0x0043, RemoteException | SessionNotFoundException -> 0x0043, TRY_ENTER, TRY_LEAVE, TryCatch #1 {RemoteException | SessionNotFoundException -> 0x0043, blocks: (B:6:0x001a, B:8:0x0037, B:11:0x006d, B:17:0x0078, B:17:0x0078, B:22:0x0081, B:22:0x0081, B:27:0x0045, B:29:0x0052, B:31:0x0062, B:32:0x0067), top: B:5:0x001a, outer: #0 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void tune(android.os.IBinder r9, android.net.Uri r10, android.os.Bundle r11, int r12) {
            /*
                r8 = this;
                int r0 = android.os.Binder.getCallingUid()
                int r1 = android.os.Binder.getCallingPid()
                com.android.server.tv.TvInputManagerService r2 = com.android.server.tv.TvInputManagerService.this
                java.lang.String r3 = "tune"
                int r12 = com.android.server.tv.TvInputManagerService.m984$$Nest$mresolveCallingUserId(r2, r1, r0, r12, r3)
                long r1 = android.os.Binder.clearCallingIdentity()
                com.android.server.tv.TvInputManagerService r3 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> Lc9
                java.lang.Object r3 = r3.mLock     // Catch: java.lang.Throwable -> Lc9
                monitor-enter(r3)     // Catch: java.lang.Throwable -> Lc9
                com.android.server.tv.TvInputManagerService r4 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                android.media.tv.ITvInputSession r4 = com.android.server.tv.TvInputManagerService.m981$$Nest$mgetSessionLocked(r4, r9, r0, r12)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r4.tune(r10, r11)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService r4 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService$UserState r12 = r4.getOrCreateUserStateLocked(r12)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService r4 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r4.getClass()     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService$SessionState r0 = com.android.server.tv.TvInputManagerService.getSessionStateLocked(r9, r0, r12)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                boolean r4 = r0.isCurrent     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r5 = 1
                if (r4 == 0) goto L45
                android.net.Uri r4 = r0.currentChannel     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                boolean r4 = java.util.Objects.equals(r4, r10)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                if (r4 != 0) goto L6d
                goto L45
            L40:
                r8 = move-exception
                goto Lc7
            L43:
                r8 = move-exception
                goto Lbb
            L45:
                r0.isCurrent = r5     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r0.currentChannel = r10     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService r4 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r4.notifyCurrentChannelInfosUpdatedLocked(r12)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                boolean r4 = r0.isRecordingSession     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                if (r4 != 0) goto L6d
                com.android.server.tv.TvInputManagerService r4 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                java.lang.String r4 = com.android.server.tv.TvInputManagerService.m980$$Nest$mgetSessionActualInputId(r4, r0)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService r6 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                java.lang.String r6 = r6.mOnScreenInputId     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                boolean r6 = android.text.TextUtils.equals(r6, r4)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                if (r6 != 0) goto L67
                com.android.server.tv.TvInputManagerService r6 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r6.logExternalInputEvent(r5, r4, r0)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
            L67:
                com.android.server.tv.TvInputManagerService r6 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r6.mOnScreenInputId = r4     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                r6.mOnScreenSessionState = r0     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
            L6d:
                boolean r4 = android.media.tv.TvContract.isChannelUriForPassthroughInput(r10)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43
                if (r4 == 0) goto L78
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
                android.os.Binder.restoreCallingIdentity(r1)
                return
            L78:
                boolean r4 = r0.isRecordingSession     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                if (r4 == 0) goto L81
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
                android.os.Binder.restoreCallingIdentity(r1)
                return
            L81:
                com.android.server.tv.TvInputManagerService r4 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService$TvInputState r12 = com.android.server.tv.TvInputManagerService.m988$$Nest$smgetTvInputState(r0, r12)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                r6 = 5
                com.android.server.tv.TvInputManagerService.m982$$Nest$mlogTuneStateChanged(r4, r6, r0, r12)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                com.android.internal.os.SomeArgs r12 = com.android.internal.os.SomeArgs.obtain()     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                android.content.ComponentName r0 = r0.componentName     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                java.lang.String r0 = r0.getPackageName()     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                r12.arg1 = r0     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                long r6 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                java.lang.Long r0 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                r12.arg2 = r0     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                long r6 = android.content.ContentUris.parseId(r10)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                java.lang.Long r10 = java.lang.Long.valueOf(r6)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                r12.arg3 = r10     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                r12.arg4 = r11     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                r12.arg5 = r9     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService r8 = com.android.server.tv.TvInputManagerService.this     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                com.android.server.tv.TvInputManagerService$MessageHandler r8 = r8.mMessageHandler     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                android.os.Message r8 = r8.obtainMessage(r5, r12)     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                r8.sendToTarget()     // Catch: java.lang.Throwable -> L40 java.lang.Throwable -> L43 java.lang.Throwable -> L43
                goto Lc2
            Lbb:
                java.lang.String r9 = "TvInputManagerService"
                java.lang.String r10 = "error in tune"
                android.util.Slog.e(r9, r10, r8)     // Catch: java.lang.Throwable -> L40
            Lc2:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
                android.os.Binder.restoreCallingIdentity(r1)
                return
            Lc7:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L40
                throw r8     // Catch: java.lang.Throwable -> Lc9
            Lc9:
                r8 = move-exception
                android.os.Binder.restoreCallingIdentity(r1)
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputManagerService.BinderService.tune(android.os.IBinder, android.net.Uri, android.os.Bundle, int):void");
        }

        public final void unblockContent(IBinder iBinder, String str, int i) {
            ensureParentalControlsPermission();
            int callingUid = Binder.getCallingUid();
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), callingUid, i, "unblockContent");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    try {
                        TvInputManagerService.m981$$Nest$mgetSessionLocked(TvInputManagerService.this, iBinder, callingUid, m984$$Nest$mresolveCallingUserId).unblockContent(str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInputManagerService", "error in unblockContent", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterCallback(ITvInputManagerCallback iTvInputManagerCallback, int i) {
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "unregisterCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId);
                    orCreateUserStateLocked.mCallbacks.unregister(iTvInputManagerCallback);
                    ((HashMap) orCreateUserStateLocked.callbackPidUidMap).remove(iTvInputManagerCallback);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void updateTvInputInfo(TvInputInfo tvInputInfo, int i) {
            String str = tvInputInfo.getServiceInfo().packageName;
            String callingPackageName = getCallingPackageName();
            if (!TextUtils.equals(str, callingPackageName) && TvInputManagerService.this.mContext.checkCallingPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
                throw new IllegalArgumentException(BootReceiver$$ExternalSyntheticOutline0.m("calling package ", callingPackageName, " is not allowed to change TvInputInfo for ", str));
            }
            int m984$$Nest$mresolveCallingUserId = TvInputManagerService.m984$$Nest$mresolveCallingUserId(TvInputManagerService.this, Binder.getCallingPid(), Binder.getCallingUid(), i, "updateTvInputInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.m987$$Nest$mupdateTvInputInfoLocked(TvInputManagerService.this, TvInputManagerService.this.getOrCreateUserStateLocked(m984$$Nest$mresolveCallingUserId), tvInputInfo);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class ClientPidNotFoundException extends IllegalArgumentException {
        public ClientPidNotFoundException(String str) {
            super(str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClientState implements IBinder.DeathRecipient {
        public IBinder clientToken;
        public final List sessionTokens = new ArrayList();
        public final int userId;

        public ClientState(IBinder iBinder, int i) {
            this.clientToken = iBinder;
            this.userId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (TvInputManagerService.this.mLock) {
                try {
                    ClientState clientState = (ClientState) ((HashMap) TvInputManagerService.this.getOrCreateUserStateLocked(this.userId).clientStateMap).get(this.clientToken);
                    if (clientState != null) {
                        while (((ArrayList) clientState.sessionTokens).size() > 0) {
                            IBinder iBinder = (IBinder) ((ArrayList) clientState.sessionTokens).get(0);
                            TvInputManagerService.this.releaseSessionLocked(1000, iBinder, this.userId);
                            if (((ArrayList) clientState.sessionTokens).contains(iBinder)) {
                                Slog.d("TvInputManagerService", "remove sessionToken " + iBinder + " for " + this.clientToken);
                                ((ArrayList) clientState.sessionTokens).remove(iBinder);
                            }
                        }
                    }
                    this.clientToken = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HardwareListener {
        public HardwareListener() {
        }

        public final void onHardwareDeviceAdded(TvInputHardwareInfo tvInputHardwareInfo) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                for (ServiceState serviceState : ((HashMap) tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap).values()) {
                    if (serviceState.isHardware) {
                        try {
                            TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                            tvInputManagerService2.bindService(serviceState, tvInputManagerService2.mCurrentUserId);
                            ITvInputService iTvInputService = serviceState.service;
                            if (iTvInputService != null) {
                                iTvInputService.notifyHardwareAdded(tvInputHardwareInfo);
                            }
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHardwareAdded", e);
                        }
                    }
                }
                TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                tvInputManagerService3.updateHardwareServiceConnectionDelayed(tvInputManagerService3.mCurrentUserId);
            }
        }

        public final void onHardwareDeviceRemoved(TvInputHardwareInfo tvInputHardwareInfo) {
            SparseArray clone;
            synchronized (TvInputManagerService.this.mLock) {
                try {
                    TvInputHardwareManager tvInputHardwareManager = TvInputManagerService.this.mTvInputHardwareManager;
                    synchronized (tvInputHardwareManager.mLock) {
                        clone = tvInputHardwareManager.mHardwareInputIdMap.clone();
                    }
                    String str = (String) clone.get(tvInputHardwareInfo.getDeviceId());
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    TvInputManagerService.m983$$Nest$mremoveHardwareInputLocked(tvInputManagerService, str, tvInputManagerService.mCurrentUserId);
                    TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                    for (ServiceState serviceState : ((HashMap) tvInputManagerService2.getOrCreateUserStateLocked(tvInputManagerService2.mCurrentUserId).serviceStateMap).values()) {
                        if (serviceState.isHardware) {
                            try {
                                TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                                tvInputManagerService3.bindService(serviceState, tvInputManagerService3.mCurrentUserId);
                                ITvInputService iTvInputService = serviceState.service;
                                if (iTvInputService != null) {
                                    iTvInputService.notifyHardwareRemoved(tvInputHardwareInfo);
                                } else {
                                    ((ArrayList) serviceState.hardwareDeviceRemovedBuffer).add(tvInputHardwareInfo);
                                }
                            } catch (RemoteException e) {
                                Slog.e("TvInputManagerService", "error in notifyHardwareRemoved", e);
                            }
                        }
                    }
                    TvInputManagerService tvInputManagerService4 = TvInputManagerService.this;
                    tvInputManagerService4.updateHardwareServiceConnectionDelayed(tvInputManagerService4.mCurrentUserId);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                for (ServiceState serviceState : ((HashMap) tvInputManagerService.getOrCreateUserStateLocked(tvInputManagerService.mCurrentUserId).serviceStateMap).values()) {
                    if (serviceState.isHardware) {
                        try {
                            TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                            tvInputManagerService2.bindService(serviceState, tvInputManagerService2.mCurrentUserId);
                            ITvInputService iTvInputService = serviceState.service;
                            if (iTvInputService != null) {
                                iTvInputService.notifyHdmiDeviceAdded(hdmiDeviceInfo);
                            }
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in notifyHdmiDeviceAdded", e);
                        }
                    }
                }
                TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                tvInputManagerService3.updateHardwareServiceConnectionDelayed(tvInputManagerService3.mCurrentUserId);
            }
        }

        public final void onHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) {
            SparseArray clone;
            synchronized (TvInputManagerService.this.mLock) {
                try {
                    TvInputHardwareManager tvInputHardwareManager = TvInputManagerService.this.mTvInputHardwareManager;
                    synchronized (tvInputHardwareManager.mLock) {
                        clone = tvInputHardwareManager.mHdmiInputIdMap.clone();
                    }
                    String str = (String) clone.get(hdmiDeviceInfo.getId());
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    TvInputManagerService.m983$$Nest$mremoveHardwareInputLocked(tvInputManagerService, str, tvInputManagerService.mCurrentUserId);
                    TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                    for (ServiceState serviceState : ((HashMap) tvInputManagerService2.getOrCreateUserStateLocked(tvInputManagerService2.mCurrentUserId).serviceStateMap).values()) {
                        if (serviceState.isHardware) {
                            try {
                                TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                                tvInputManagerService3.bindService(serviceState, tvInputManagerService3.mCurrentUserId);
                                ITvInputService iTvInputService = serviceState.service;
                                if (iTvInputService != null) {
                                    iTvInputService.notifyHdmiDeviceRemoved(hdmiDeviceInfo);
                                } else {
                                    ((ArrayList) serviceState.hdmiDeviceRemovedBuffer).add(hdmiDeviceInfo);
                                }
                            } catch (RemoteException e) {
                                Slog.e("TvInputManagerService", "error in notifyHdmiDeviceRemoved", e);
                            }
                        }
                    }
                    TvInputManagerService tvInputManagerService4 = TvInputManagerService.this;
                    tvInputManagerService4.updateHardwareServiceConnectionDelayed(tvInputManagerService4.mCurrentUserId);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onStateChanged(int i, String str) {
            synchronized (TvInputManagerService.this.mLock) {
                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                TvInputManagerService.m985$$Nest$msetStateLocked(tvInputManagerService, str, i, tvInputManagerService.mCurrentUserId);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InputServiceConnection implements ServiceConnection {
        public final ComponentName mComponent;
        public final int mUserId;

        public InputServiceConnection(ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            List unmodifiableList;
            List unmodifiableList2;
            synchronized (TvInputManagerService.this.mLock) {
                try {
                    UserState userStateLocked = TvInputManagerService.this.getUserStateLocked(this.mUserId);
                    if (userStateLocked == null) {
                        TvInputManagerService.this.mContext.unbindService(this);
                        return;
                    }
                    ServiceState serviceState = (ServiceState) ((HashMap) userStateLocked.serviceStateMap).get(this.mComponent);
                    serviceState.service = ITvInputService.Stub.asInterface(iBinder);
                    serviceState.neverConnected = false;
                    if (serviceState.isHardware && serviceState.callback == null) {
                        ServiceCallback serviceCallback = TvInputManagerService.this.new ServiceCallback(this.mComponent, this.mUserId);
                        serviceState.callback = serviceCallback;
                        try {
                            serviceState.service.registerCallback(serviceCallback);
                        } catch (RemoteException e) {
                            Slog.e("TvInputManagerService", "error in registerCallback", e);
                        }
                    }
                    for (TvInputState tvInputState : userStateLocked.inputMap.values()) {
                        if (tvInputState.info.getComponent().equals(componentName) && tvInputState.state != 0) {
                            TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                            String id = tvInputState.info.getId();
                            int i = tvInputState.state;
                            tvInputManagerService.getClass();
                            TvInputManagerService.notifyInputStateChangedLocked(userStateLocked, id, i);
                        }
                    }
                    if (serviceState.isHardware) {
                        Iterator it = ((ArrayList) serviceState.hardwareDeviceRemovedBuffer).iterator();
                        while (it.hasNext()) {
                            try {
                                serviceState.service.notifyHardwareRemoved((TvInputHardwareInfo) it.next());
                            } catch (RemoteException e2) {
                                Slog.e("TvInputManagerService", "error in hardwareDeviceRemovedBuffer", e2);
                            }
                        }
                        ((ArrayList) serviceState.hardwareDeviceRemovedBuffer).clear();
                        Iterator it2 = ((ArrayList) serviceState.hdmiDeviceRemovedBuffer).iterator();
                        while (it2.hasNext()) {
                            try {
                                serviceState.service.notifyHdmiDeviceRemoved((HdmiDeviceInfo) it2.next());
                            } catch (RemoteException e3) {
                                Slog.e("TvInputManagerService", "error in hdmiDeviceRemovedBuffer", e3);
                            }
                        }
                        ((ArrayList) serviceState.hdmiDeviceRemovedBuffer).clear();
                        TvInputHardwareManager tvInputHardwareManager = TvInputManagerService.this.mTvInputHardwareManager;
                        synchronized (tvInputHardwareManager.mLock) {
                            unmodifiableList = Collections.unmodifiableList(tvInputHardwareManager.mHardwareList);
                        }
                        Iterator it3 = unmodifiableList.iterator();
                        while (it3.hasNext()) {
                            try {
                                serviceState.service.notifyHardwareAdded((TvInputHardwareInfo) it3.next());
                            } catch (RemoteException e4) {
                                Slog.e("TvInputManagerService", "error in notifyHardwareAdded", e4);
                            }
                        }
                        TvInputHardwareManager tvInputHardwareManager2 = TvInputManagerService.this.mTvInputHardwareManager;
                        synchronized (tvInputHardwareManager2.mLock) {
                            unmodifiableList2 = Collections.unmodifiableList(tvInputHardwareManager2.mHdmiDeviceList);
                        }
                        Iterator it4 = unmodifiableList2.iterator();
                        while (it4.hasNext()) {
                            try {
                                serviceState.service.notifyHdmiDeviceAdded((HdmiDeviceInfo) it4.next());
                            } catch (RemoteException e5) {
                                Slog.e("TvInputManagerService", "error in notifyHdmiDeviceAdded", e5);
                            }
                        }
                        Iterator it5 = ((ArrayList) serviceState.hdmiDeviceUpdatedBuffer).iterator();
                        while (it5.hasNext()) {
                            try {
                                serviceState.service.notifyHdmiDeviceUpdated((HdmiDeviceInfo) it5.next());
                            } catch (RemoteException e6) {
                                Slog.e("TvInputManagerService", "error in hdmiDeviceUpdatedBuffer", e6);
                            }
                        }
                        ((ArrayList) serviceState.hdmiDeviceUpdatedBuffer).clear();
                    }
                    ArrayList arrayList = new ArrayList();
                    Iterator it6 = ((ArrayList) serviceState.sessionTokens).iterator();
                    while (it6.hasNext()) {
                        IBinder iBinder2 = (IBinder) it6.next();
                        if (!TvInputManagerService.m979$$Nest$mcreateSessionInternalLocked(TvInputManagerService.this, serviceState.service, iBinder2, this.mUserId)) {
                            arrayList.add(iBinder2);
                        }
                    }
                    Iterator it7 = arrayList.iterator();
                    while (it7.hasNext()) {
                        TvInputManagerService.this.removeSessionStateLocked(this.mUserId, (IBinder) it7.next());
                    }
                    if (serviceState.isHardware) {
                        TvInputManagerService.this.updateHardwareServiceConnectionDelayed(this.mUserId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            if (!this.mComponent.equals(componentName)) {
                throw new IllegalArgumentException("Mismatched ComponentName: " + this.mComponent + " (expected), " + componentName + " (actual).");
            }
            synchronized (TvInputManagerService.this.mLock) {
                try {
                    ServiceState serviceState = (ServiceState) ((HashMap) TvInputManagerService.this.getOrCreateUserStateLocked(this.mUserId).serviceStateMap).get(this.mComponent);
                    if (serviceState != null) {
                        serviceState.reconnecting = true;
                        serviceState.bound = false;
                        serviceState.service = null;
                        serviceState.callback = null;
                        TvInputManagerService.this.abortPendingCreateSessionRequestsLocked(serviceState, null, this.mUserId);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MessageHandler extends Handler {
        public ContentResolver mContentResolver;

        public MessageHandler(ContentResolver contentResolver, Looper looper) {
            super(looper);
            this.mContentResolver = contentResolver;
        }

        public static String replaceEscapeCharacters(String str) {
            StringBuilder sb = new StringBuilder();
            for (char c : str.toCharArray()) {
                if ("%=,".indexOf(c) >= 0) {
                    sb.append('%');
                }
                sb.append(c);
            }
            return sb.toString();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i == 2) {
                    SomeArgs someArgs = (SomeArgs) message.obj;
                    IBinder iBinder = (IBinder) someArgs.arg1;
                    Long l = (Long) someArgs.arg2;
                    l.getClass();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("watch_end_time_utc_millis", l);
                    contentValues.put("session_token", iBinder.toString());
                    try {
                        this.mContentResolver.insert(TvContract.WatchedPrograms.CONTENT_URI, contentValues);
                    } catch (IllegalArgumentException e) {
                        Slog.w("TvInputManagerService", "error in insert db for MSG_LOG_WATCH_END", e);
                    }
                    someArgs.recycle();
                    return;
                }
                if (i == 3) {
                    this.mContentResolver = (ContentResolver) message.obj;
                    return;
                }
                if (i != 4) {
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(new StringBuilder("unhandled message code: "), message.what, "TvInputManagerService");
                    return;
                }
                SomeArgs someArgs2 = (SomeArgs) message.obj;
                int intValue = ((Integer) someArgs2.arg1).intValue();
                synchronized (TvInputManagerService.this.mLock) {
                    TvInputManagerService.m986$$Nest$mupdateHardwareTvInputServiceBindingLocked(TvInputManagerService.this, intValue);
                }
                someArgs2.recycle();
                return;
            }
            SomeArgs someArgs3 = (SomeArgs) message.obj;
            String str = (String) someArgs3.arg1;
            Long l2 = (Long) someArgs3.arg2;
            l2.getClass();
            Long l3 = (Long) someArgs3.arg3;
            l3.getClass();
            Bundle bundle = (Bundle) someArgs3.arg4;
            IBinder iBinder2 = (IBinder) someArgs3.arg5;
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("package_name", str);
            contentValues2.put("watch_start_time_utc_millis", l2);
            contentValues2.put("channel_id", l3);
            if (bundle != null) {
                StringBuilder sb = new StringBuilder();
                Iterator<String> it = bundle.keySet().iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    Object obj = bundle.get(next);
                    if (obj != null) {
                        sb.append(replaceEscapeCharacters(next));
                        sb.append("=");
                        sb.append(replaceEscapeCharacters(obj.toString()));
                        if (it.hasNext()) {
                            sb.append(", ");
                        }
                    }
                }
                contentValues2.put("tune_params", sb.toString());
            }
            contentValues2.put("session_token", iBinder2.toString());
            try {
                this.mContentResolver.insert(TvContract.WatchedPrograms.CONTENT_URI, contentValues2);
            } catch (IllegalArgumentException e2) {
                Slog.w("TvInputManagerService", "error in insert db for MSG_LOG_WATCH_START", e2);
            }
            someArgs3.recycle();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceCallback extends ITvInputServiceCallback.Stub {
        public final ComponentName mComponent;
        public final int mUserId;

        public ServiceCallback(ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        public final void addHardwareInput(int i, TvInputInfo tvInputInfo) {
            ensureHardwarePermission();
            if (tvInputInfo.getId() == null || !this.mComponent.equals(tvInputInfo.getComponent())) {
                throw new IllegalArgumentException("Invalid TvInputInfo");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    if (((HashMap) TvInputManagerService.this.getServiceStateLocked(this.mUserId, this.mComponent).hardwareInputMap).containsKey(tvInputInfo.getId())) {
                        return;
                    }
                    Slog.d("ServiceCallback", "addHardwareInput: device id " + i + ", " + tvInputInfo.toString());
                    TvInputManagerService.this.mTvInputHardwareManager.addHardwareInput(i, tvInputInfo);
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    ComponentName componentName = this.mComponent;
                    int i2 = this.mUserId;
                    ((HashMap) tvInputManagerService.getServiceStateLocked(i2, componentName).hardwareInputMap).put(tvInputInfo.getId(), tvInputInfo);
                    tvInputManagerService.buildTvInputListLocked(i2, null);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void addHdmiInput(int i, TvInputInfo tvInputInfo) {
            ensureHardwarePermission();
            if (tvInputInfo.getId() == null || !this.mComponent.equals(tvInputInfo.getComponent())) {
                throw new IllegalArgumentException("Invalid TvInputInfo");
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    if (((HashMap) TvInputManagerService.this.getServiceStateLocked(this.mUserId, this.mComponent).hardwareInputMap).containsKey(tvInputInfo.getId())) {
                        return;
                    }
                    TvInputManagerService.this.mTvInputHardwareManager.addHdmiInput(i, tvInputInfo);
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    ComponentName componentName = this.mComponent;
                    int i2 = this.mUserId;
                    ((HashMap) tvInputManagerService.getServiceStateLocked(i2, componentName).hardwareInputMap).put(tvInputInfo.getId(), tvInputInfo);
                    tvInputManagerService.buildTvInputListLocked(i2, null);
                    TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                    String str = tvInputManagerService2.mOnScreenInputId;
                    if (str != null && tvInputManagerService2.mOnScreenSessionState != null) {
                        if (TextUtils.equals(str, tvInputInfo.getParentId())) {
                            TvInputManagerService.this.logExternalInputEvent(1, tvInputInfo.getId(), TvInputManagerService.this.mOnScreenSessionState);
                            TvInputManagerService.this.mOnScreenInputId = tvInputInfo.getId();
                        } else if (TextUtils.equals(TvInputManagerService.this.mOnScreenInputId, tvInputInfo.getId())) {
                            TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                            tvInputManagerService3.logExternalInputEvent(4, tvInputManagerService3.mOnScreenInputId, tvInputManagerService3.mOnScreenSessionState);
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void ensureHardwarePermission() {
            if (TvInputManagerService.this.mContext.checkCallingPermission("android.permission.TV_INPUT_HARDWARE") != 0) {
                throw new SecurityException("The caller does not have hardware permission");
            }
        }

        public final void removeHardwareInput(String str) {
            ensureHardwarePermission();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInputManagerService.this.mLock) {
                    Slog.d("ServiceCallback", "removeHardwareInput " + str + " by " + this.mComponent);
                    TvInputManagerService.m983$$Nest$mremoveHardwareInputLocked(TvInputManagerService.this, str, this.mUserId);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceState {
        public boolean bound;
        public ServiceCallback callback;
        public final ComponentName component;
        public final InputServiceConnection connection;
        public final boolean isHardware;
        public boolean neverConnected;
        public boolean reconnecting;
        public ITvInputService service;
        public final List sessionTokens = new ArrayList();
        public final Map hardwareInputMap = new HashMap();
        public final List hardwareDeviceRemovedBuffer = new ArrayList();
        public final List hdmiDeviceRemovedBuffer = new ArrayList();
        public final List hdmiDeviceUpdatedBuffer = new ArrayList();

        public ServiceState(TvInputManagerService tvInputManagerService, ComponentName componentName, int i) {
            this.component = componentName;
            this.connection = tvInputManagerService.new InputServiceConnection(componentName, i);
            this.isHardware = tvInputManagerService.mContext.getPackageManager().checkPermission("android.permission.TV_INPUT_HARDWARE", componentName.getPackageName()) == 0;
            this.neverConnected = true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionCallback extends ITvInputSessionCallback.Stub {
        public final InputChannel[] mChannels;
        public final SessionState mSessionState;

        public SessionCallback(SessionState sessionState, InputChannel[] inputChannelArr) {
            this.mSessionState = sessionState;
            this.mChannels = inputChannelArr;
        }

        public final boolean addSessionTokenToClientStateLocked(ITvInputSession iTvInputSession) {
            try {
                iTvInputSession.asBinder().linkToDeath(this.mSessionState, 0);
                IBinder asBinder = this.mSessionState.client.asBinder();
                UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(this.mSessionState.userId);
                ClientState clientState = (ClientState) ((HashMap) orCreateUserStateLocked.clientStateMap).get(asBinder);
                if (clientState == null) {
                    clientState = TvInputManagerService.this.new ClientState(asBinder, this.mSessionState.userId);
                    try {
                        asBinder.linkToDeath(clientState, 0);
                        ((HashMap) orCreateUserStateLocked.clientStateMap).put(asBinder, clientState);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "client process has already died", e);
                        return false;
                    }
                }
                ((ArrayList) clientState.sessionTokens).add(this.mSessionState.sessionToken);
                return true;
            } catch (RemoteException e2) {
                Slog.e("TvInputManagerService", "session process has already died", e2);
                return false;
            }
        }

        public final void onAdBufferConsumed(AdBuffer adBuffer) {
            SharedMemory sharedMemory;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session != null) {
                    ITvInputClient iTvInputClient = sessionState.client;
                    try {
                        if (iTvInputClient != null) {
                            try {
                                iTvInputClient.onAdBufferConsumed(adBuffer, sessionState.seq);
                            } catch (RemoteException e) {
                                Slog.e("TvInputManagerService", "error in onAdBufferConsumed", e);
                                if (adBuffer != null) {
                                    sharedMemory = adBuffer.getSharedMemory();
                                }
                            }
                            if (adBuffer != null) {
                                sharedMemory = adBuffer.getSharedMemory();
                                sharedMemory.close();
                            }
                        }
                    } catch (Throwable th) {
                        if (adBuffer != null) {
                            adBuffer.getSharedMemory().close();
                        }
                        throw th;
                    }
                }
            }
        }

        public final void onAdResponse(AdResponse adResponse) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onAdResponse(adResponse, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAdResponse", e);
                }
            }
        }

        public final void onAitInfoUpdated(AitInfo aitInfo) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onAitInfoUpdated(aitInfo, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAitInfoUpdated", e);
                }
            }
        }

        public final void onAudioPresentationSelected(int i, int i2) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onAudioPresentationSelected(i, i2, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAudioPresentationSelected", e);
                }
            }
        }

        public final void onAudioPresentationsChanged(List list) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onAudioPresentationsChanged(list, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAudioPresentationsChanged", e);
                }
            }
        }

        public final void onAvailableSpeeds(float[] fArr) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onAvailableSpeeds(fArr, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onAvailableSpeeds", e);
                }
            }
        }

        public final void onBroadcastInfoResponse(BroadcastInfoResponse broadcastInfoResponse) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onBroadcastInfoResponse(broadcastInfoResponse, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onBroadcastInfoResponse", e);
                }
            }
        }

        public final void onChannelRetuned(Uri uri) {
            ITvInputClient iTvInputClient;
            SessionState sessionState;
            synchronized (TvInputManagerService.this.mLock) {
                try {
                    SessionState sessionState2 = this.mSessionState;
                    if (sessionState2.session == null || (iTvInputClient = sessionState2.client) == null) {
                        return;
                    }
                    try {
                        iTvInputClient.onChannelRetuned(uri, sessionState2.seq);
                        sessionState = this.mSessionState;
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in onChannelRetuned", e);
                    }
                    if (sessionState.isCurrent) {
                        if (!Objects.equals(sessionState.currentChannel, uri)) {
                        }
                    }
                    UserState orCreateUserStateLocked = TvInputManagerService.this.getOrCreateUserStateLocked(this.mSessionState.userId);
                    SessionState sessionState3 = this.mSessionState;
                    sessionState3.isCurrent = true;
                    sessionState3.currentChannel = uri;
                    TvInputManagerService.this.notifyCurrentChannelInfosUpdatedLocked(orCreateUserStateLocked);
                    SessionState sessionState4 = this.mSessionState;
                    if (!sessionState4.isRecordingSession) {
                        String m980$$Nest$mgetSessionActualInputId = TvInputManagerService.m980$$Nest$mgetSessionActualInputId(TvInputManagerService.this, sessionState4);
                        if (!TextUtils.equals(TvInputManagerService.this.mOnScreenInputId, m980$$Nest$mgetSessionActualInputId)) {
                            TvInputManagerService.this.logExternalInputEvent(1, m980$$Nest$mgetSessionActualInputId, this.mSessionState);
                        }
                        TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                        tvInputManagerService.mOnScreenInputId = m980$$Nest$mgetSessionActualInputId;
                        tvInputManagerService.mOnScreenSessionState = this.mSessionState;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onContentAllowed() {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onContentAllowed(sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onContentAllowed", e);
                }
            }
        }

        public final void onContentBlocked(String str) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onContentBlocked(str, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onContentBlocked", e);
                }
            }
        }

        public final void onCueingMessageAvailability(boolean z) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onCueingMessageAvailability(z, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onCueingMessageAvailability", e);
                }
            }
        }

        public final void onError(int i) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onError(i, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onError", e);
                }
            }
        }

        public final void onLayoutSurface(int i, int i2, int i3, int i4) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onLayoutSurface(i, i2, i3, i4, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onLayoutSurface", e);
                }
            }
        }

        public final void onRecordingStopped(Uri uri) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onRecordingStopped(uri, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onRecordingStopped", e);
                }
            }
        }

        public final void onSessionCreated(ITvInputSession iTvInputSession, IBinder iBinder) {
            synchronized (TvInputManagerService.this.mLock) {
                try {
                    SessionState sessionState = this.mSessionState;
                    sessionState.session = iTvInputSession;
                    sessionState.hardwareSessionToken = iBinder;
                    if (iTvInputSession == null || !addSessionTokenToClientStateLocked(iTvInputSession)) {
                        TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                        SessionState sessionState2 = this.mSessionState;
                        tvInputManagerService.removeSessionStateLocked(sessionState2.userId, sessionState2.sessionToken);
                        TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                        SessionState sessionState3 = this.mSessionState;
                        ITvInputClient iTvInputClient = sessionState3.client;
                        String str = sessionState3.inputId;
                        int i = sessionState3.seq;
                        tvInputManagerService2.getClass();
                        TvInputManagerService.sendSessionTokenToClientLocked(iTvInputClient, str, null, null, i);
                    } else {
                        TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                        SessionState sessionState4 = this.mSessionState;
                        ITvInputClient iTvInputClient2 = sessionState4.client;
                        String str2 = sessionState4.inputId;
                        IBinder iBinder2 = sessionState4.sessionToken;
                        InputChannel inputChannel = this.mChannels[0];
                        int i2 = sessionState4.seq;
                        tvInputManagerService3.getClass();
                        TvInputManagerService.sendSessionTokenToClientLocked(iTvInputClient2, str2, iBinder2, inputChannel, i2);
                    }
                    this.mChannels[0].dispose();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onSessionEvent(String str, Bundle bundle) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onSessionEvent(str, bundle, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onSessionEvent", e);
                }
            }
        }

        public final void onSignalStrength(int i) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onSignalStrength(i, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onSignalStrength", e);
                }
            }
        }

        public final void onTimeShiftCurrentPositionChanged(long j) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTimeShiftCurrentPositionChanged(j, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftCurrentPositionChanged", e);
                }
            }
        }

        public final void onTimeShiftMode(int i) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTimeShiftMode(i, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftMode", e);
                }
            }
        }

        public final void onTimeShiftStartPositionChanged(long j) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTimeShiftStartPositionChanged(j, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftStartPositionChanged", e);
                }
            }
        }

        public final void onTimeShiftStatusChanged(int i) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTimeShiftStatusChanged(i, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTimeShiftStatusChanged", e);
                }
            }
        }

        public final void onTrackSelected(int i, String str) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTrackSelected(i, str, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTrackSelected", e);
                }
            }
        }

        public final void onTracksChanged(List list) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTracksChanged(list, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTracksChanged", e);
                }
            }
        }

        public final void onTuned(Uri uri) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTuned(uri, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTuned", e);
                }
            }
        }

        public final void onTvInputSessionData(String str, Bundle bundle) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTvInputSessionData(str, bundle, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTvInputSessionData", e);
                }
            }
        }

        public final void onTvMessage(int i, Bundle bundle) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onTvMessage(i, bundle, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onTvMessage", e);
                }
            }
        }

        public final void onVideoAvailable() {
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session != null && sessionState.client != null) {
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    TvInputState m988$$Nest$smgetTvInputState = TvInputManagerService.m988$$Nest$smgetTvInputState(sessionState, tvInputManagerService.getUserStateLocked(tvInputManagerService.mCurrentUserId));
                    try {
                        SessionState sessionState2 = this.mSessionState;
                        sessionState2.client.onVideoAvailable(sessionState2.seq);
                        TvInputManagerService.m982$$Nest$mlogTuneStateChanged(TvInputManagerService.this, 6, this.mSessionState, m988$$Nest$smgetTvInputState);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in onVideoAvailable", e);
                    }
                }
            }
        }

        public final void onVideoFreezeUpdated(boolean z) {
            ITvInputClient iTvInputClient;
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session == null || (iTvInputClient = sessionState.client) == null) {
                    return;
                }
                try {
                    iTvInputClient.onVideoFreezeUpdated(z, sessionState.seq);
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in onVideoFreezeUpdated", e);
                }
            }
        }

        public final void onVideoUnavailable(int i) {
            synchronized (TvInputManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.session != null && sessionState.client != null) {
                    TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                    TvInputState m988$$Nest$smgetTvInputState = TvInputManagerService.m988$$Nest$smgetTvInputState(sessionState, tvInputManagerService.getUserStateLocked(tvInputManagerService.mCurrentUserId));
                    try {
                        SessionState sessionState2 = this.mSessionState;
                        sessionState2.client.onVideoUnavailable(i, sessionState2.seq);
                        TvInputManagerService.m982$$Nest$mlogTuneStateChanged(TvInputManagerService.this, TvInputManagerService.getVideoUnavailableReasonForStatsd(i), this.mSessionState, m988$$Nest$smgetTvInputState);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in onVideoUnavailable", e);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class SessionNotFoundException extends IllegalArgumentException {
        public SessionNotFoundException(String str) {
            super(str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionState implements IBinder.DeathRecipient {
        public final int callingPid;
        public final int callingUid;
        public final ITvInputClient client;
        public final ComponentName componentName;
        public IBinder hardwareSessionToken;
        public final String inputId;
        public final boolean isRecordingSession;
        public final int seq;
        public ITvInputSession session;
        public final String sessionId;
        public final IBinder sessionToken;
        public final AttributionSource tvAppAttributionSource;
        public final int userId;
        public boolean isCurrent = false;
        public Uri currentChannel = null;
        public boolean isVisible = false;
        public boolean isMainSession = false;

        public SessionState(IBinder iBinder, String str, ComponentName componentName, boolean z, ITvInputClient iTvInputClient, int i, int i2, int i3, int i4, String str2, AttributionSource attributionSource) {
            this.sessionToken = iBinder;
            this.inputId = str;
            this.componentName = componentName;
            this.isRecordingSession = z;
            this.client = iTvInputClient;
            this.seq = i;
            this.callingUid = i2;
            this.callingPid = i3;
            this.userId = i4;
            this.sessionId = str2;
            this.tvAppAttributionSource = attributionSource;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (TvInputManagerService.this.mLock) {
                this.session = null;
                TvInputManagerService.this.clearSessionAndNotifyClientLocked(this);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TvInputState {
        public TvInputInfo info;
        public int inputNumber;
        public int state;
        public int uid;

        public final String toString() {
            return "info: " + this.info + "; state: " + this.state;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserState {
        public final PersistentDataStore persistentDataStore;
        public Map inputMap = new HashMap();
        public final Set packageSet = new HashSet();
        public final List contentRatingSystemList = new ArrayList();
        public final Map clientStateMap = new HashMap();
        public final Map serviceStateMap = new HashMap();
        public final Map sessionStateMap = new HashMap();
        public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
        public final Map callbackPidUidMap = new HashMap();
        public IBinder mainSessionToken = null;
        public final Map mAppTagMap = new HashMap();
        public int mNextAppTag = 1;

        public UserState(Context context, int i) {
            this.persistentDataStore = new PersistentDataStore(context, i);
        }
    }

    /* renamed from: -$$Nest$mcreateSessionInternalLocked, reason: not valid java name */
    public static boolean m979$$Nest$mcreateSessionInternalLocked(TvInputManagerService tvInputManagerService, ITvInputService iTvInputService, IBinder iBinder, int i) {
        boolean z;
        SessionState sessionState = (SessionState) ((HashMap) tvInputManagerService.getOrCreateUserStateLocked(i).sessionStateMap).get(iBinder);
        InputChannel[] openInputChannelPair = InputChannel.openInputChannelPair(iBinder.toString());
        SessionCallback sessionCallback = tvInputManagerService.new SessionCallback(sessionState, openInputChannelPair);
        try {
            if (sessionState.isRecordingSession) {
                iTvInputService.createRecordingSession(sessionCallback, sessionState.inputId, sessionState.sessionId);
            } else {
                iTvInputService.createSession(openInputChannelPair[1], sessionCallback, sessionState.inputId, sessionState.sessionId, sessionState.tvAppAttributionSource);
            }
            z = true;
        } catch (RemoteException e) {
            Slog.e("TvInputManagerService", "error in createSession", e);
            sendSessionTokenToClientLocked(sessionState.client, sessionState.inputId, null, null, sessionState.seq);
            z = false;
        }
        openInputChannelPair[1].dispose();
        return z;
    }

    /* renamed from: -$$Nest$mgetSessionActualInputId, reason: not valid java name */
    public static String m980$$Nest$mgetSessionActualInputId(TvInputManagerService tvInputManagerService, SessionState sessionState) {
        Map unmodifiableMap;
        tvInputManagerService.getClass();
        TvInputState tvInputState = (TvInputState) tvInputManagerService.getOrCreateUserStateLocked(sessionState.userId).inputMap.get(sessionState.inputId);
        if (tvInputState == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("No TvInputState for sessionState.inputId "), sessionState.inputId, "TvInputManagerService");
            return sessionState.inputId;
        }
        TvInputInfo tvInputInfo = tvInputState.info;
        if (tvInputInfo == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("TvInputInfo is null for input id "), sessionState.inputId, "TvInputManagerService");
            return sessionState.inputId;
        }
        String str = sessionState.inputId;
        if (tvInputInfo.getType() == 1007) {
            TvInputHardwareManager tvInputHardwareManager = tvInputManagerService.mTvInputHardwareManager;
            synchronized (tvInputHardwareManager.mLock) {
                unmodifiableMap = Collections.unmodifiableMap(tvInputHardwareManager.mHdmiParentInputMap);
            }
            if (unmodifiableMap.containsKey(sessionState.inputId)) {
                return (String) ((List) unmodifiableMap.get(sessionState.inputId)).get(0);
            }
        }
        return str;
    }

    /* renamed from: -$$Nest$mgetSessionLocked, reason: not valid java name */
    public static ITvInputSession m981$$Nest$mgetSessionLocked(TvInputManagerService tvInputManagerService, IBinder iBinder, int i, int i2) {
        return getSessionLocked(tvInputManagerService.getSessionStateLocked(i, iBinder, i2));
    }

    /* renamed from: -$$Nest$mlogTuneStateChanged, reason: not valid java name */
    public static void m982$$Nest$mlogTuneStateChanged(TvInputManagerService tvInputManagerService, int i, SessionState sessionState, TvInputState tvInputState) {
        int i2;
        int i3;
        int i4;
        int i5;
        tvInputManagerService.getClass();
        if (tvInputState != null) {
            i2 = tvInputState.uid;
            int type = tvInputState.info.getType();
            if (type == 0) {
                type = 1;
            }
            int i6 = tvInputState.inputNumber;
            HdmiDeviceInfo hdmiDeviceInfo = tvInputState.info.getHdmiDeviceInfo();
            i5 = hdmiDeviceInfo != null ? hdmiDeviceInfo.getPortId() : 0;
            i3 = type;
            i4 = i6;
        } else {
            i2 = -1;
            i3 = 0;
            i4 = 0;
            i5 = 0;
        }
        FrameworkStatsLog.write(327, new int[]{sessionState.callingUid, i2}, new String[]{"tif_player", "tv_input_service"}, i, sessionState.sessionId, i3, i4, i5);
    }

    /* renamed from: -$$Nest$mremoveHardwareInputLocked, reason: not valid java name */
    public static void m983$$Nest$mremoveHardwareInputLocked(TvInputManagerService tvInputManagerService, String str, int i) {
        Map unmodifiableMap;
        Map unmodifiableMap2;
        TvInputHardwareManager tvInputHardwareManager = tvInputManagerService.mTvInputHardwareManager;
        synchronized (tvInputHardwareManager.mLock) {
            unmodifiableMap = Collections.unmodifiableMap(tvInputHardwareManager.mInputMap);
        }
        if (unmodifiableMap.containsKey(str)) {
            TvInputHardwareManager tvInputHardwareManager2 = tvInputManagerService.mTvInputHardwareManager;
            synchronized (tvInputHardwareManager2.mLock) {
                unmodifiableMap2 = Collections.unmodifiableMap(tvInputHardwareManager2.mInputMap);
            }
            if (((HashMap) tvInputManagerService.getServiceStateLocked(i, ((TvInputInfo) unmodifiableMap2.get(str)).getComponent()).hardwareInputMap).remove(str) != null) {
                tvInputManagerService.buildTvInputListLocked(i, null);
                TvInputHardwareManager tvInputHardwareManager3 = tvInputManagerService.mTvInputHardwareManager;
                synchronized (tvInputHardwareManager3.mLock) {
                    try {
                        int indexOfEqualValue = TvInputHardwareManager.indexOfEqualValue(tvInputHardwareManager3.mHardwareInputIdMap, str);
                        if (indexOfEqualValue >= 0) {
                            tvInputHardwareManager3.mHardwareInputIdMap.removeAt(indexOfEqualValue);
                        }
                        int indexOfEqualValue2 = TvInputHardwareManager.indexOfEqualValue(tvInputHardwareManager3.mHdmiInputIdMap, str);
                        if (indexOfEqualValue2 >= 0) {
                            tvInputHardwareManager3.mHdmiInputIdMap.removeAt(indexOfEqualValue2);
                        }
                        if (((ArrayMap) tvInputHardwareManager3.mInputMap).containsKey(str)) {
                            String parentId = ((TvInputInfo) ((ArrayMap) tvInputHardwareManager3.mInputMap).get(str)).getParentId();
                            if (parentId != null && ((ArrayMap) tvInputHardwareManager3.mHdmiParentInputMap).containsKey(parentId)) {
                                List list = (List) ((ArrayMap) tvInputHardwareManager3.mHdmiParentInputMap).get(parentId);
                                list.remove(str);
                                if (list.isEmpty()) {
                                    ((ArrayMap) tvInputHardwareManager3.mHdmiParentInputMap).remove(parentId);
                                }
                            }
                            ((ArrayMap) tvInputHardwareManager3.mInputMap).remove(str);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* renamed from: -$$Nest$mresolveCallingUserId, reason: not valid java name */
    public static int m984$$Nest$mresolveCallingUserId(TvInputManagerService tvInputManagerService, int i, int i2, int i3, String str) {
        tvInputManagerService.getClass();
        return ActivityManager.handleIncomingUser(i, i2, i3, false, false, str, null);
    }

    /* renamed from: -$$Nest$msetStateLocked, reason: not valid java name */
    public static void m985$$Nest$msetStateLocked(TvInputManagerService tvInputManagerService, String str, int i, int i2) {
        UserState orCreateUserStateLocked = tvInputManagerService.getOrCreateUserStateLocked(i2);
        TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
        if (tvInputState == null) {
            BootReceiver$$ExternalSyntheticOutline0.m("failed to setStateLocked - unknown input id ", str, "TvInputManagerService");
            return;
        }
        ServiceState serviceState = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(tvInputState.info.getComponent());
        int i3 = tvInputState.state;
        tvInputState.state = i;
        if ((serviceState == null || !serviceState.reconnecting) && i3 != i) {
            if (str.equals(tvInputManagerService.mOnScreenInputId)) {
                tvInputManagerService.logExternalInputEvent(3, tvInputManagerService.mOnScreenInputId, tvInputManagerService.mOnScreenSessionState);
            } else {
                String str2 = tvInputManagerService.mOnScreenInputId;
                if (str2 != null) {
                    TvInputState tvInputState2 = (TvInputState) orCreateUserStateLocked.inputMap.get(str2);
                    TvInputInfo tvInputInfo = tvInputState2 != null ? tvInputState2.info : null;
                    if (tvInputInfo != null && tvInputInfo.getHdmiDeviceInfo() != null && str.equals(tvInputInfo.getParentId())) {
                        tvInputManagerService.logExternalInputEvent(3, str, tvInputManagerService.mOnScreenSessionState);
                        if (i == 1) {
                            tvInputManagerService.mOnScreenInputId = tvInputInfo.getParentId();
                        }
                    }
                }
            }
            notifyInputStateChangedLocked(orCreateUserStateLocked, str, i);
        }
    }

    /* renamed from: -$$Nest$mupdateHardwareTvInputServiceBindingLocked, reason: not valid java name */
    public static void m986$$Nest$mupdateHardwareTvInputServiceBindingLocked(TvInputManagerService tvInputManagerService, int i) {
        PackageManager packageManager = tvInputManagerService.mContext.getPackageManager();
        Iterator it = packageManager.queryIntentServicesAsUser(new Intent("android.media.tv.TvInputService"), 132, i).iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            if ("android.permission.BIND_TV_INPUT".equals(serviceInfo.permission)) {
                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (packageManager.checkPermission("android.permission.TV_INPUT_HARDWARE", componentName.getPackageName()) == 0) {
                    tvInputManagerService.updateServiceConnectionLocked(i, componentName);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0109 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: -$$Nest$mupdateTvInputInfoLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m987$$Nest$mupdateTvInputInfoLocked(com.android.server.tv.TvInputManagerService r7, com.android.server.tv.TvInputManagerService.UserState r8, android.media.tv.TvInputInfo r9) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputManagerService.m987$$Nest$mupdateTvInputInfoLocked(com.android.server.tv.TvInputManagerService, com.android.server.tv.TvInputManagerService$UserState, android.media.tv.TvInputInfo):void");
    }

    /* renamed from: -$$Nest$smgetTvInputState, reason: not valid java name */
    public static TvInputState m988$$Nest$smgetTvInputState(SessionState sessionState, UserState userState) {
        if (userState != null) {
            return (TvInputState) userState.inputMap.get(sessionState.inputId);
        }
        return null;
    }

    public TvInputManagerService(Context context) {
        super(context);
        Object obj = new Object();
        this.mLock = obj;
        this.mCurrentUserId = 0;
        this.mOnScreenInputId = null;
        this.mOnScreenSessionState = null;
        this.mRunningProfiles = new HashSet();
        this.mUserStates = new SparseArray();
        this.mSessionIdToSessionStateMap = new HashMap();
        this.mExternalInputLoggingDisplayNameFilterEnabled = false;
        HashSet hashSet = new HashSet();
        this.mExternalInputLoggingDeviceOnScreenDisplayNames = hashSet;
        ArrayList arrayList = new ArrayList();
        this.mExternalInputLoggingDeviceBrandNames = arrayList;
        this.mContext = context;
        this.mMessageHandler = new MessageHandler(context.getContentResolver(), IoThread.get().getLooper());
        this.mTvInputHardwareManager = new TvInputHardwareManager(context, new HardwareListener());
        this.mUserManager = (UserManager) getContext().getSystemService("user");
        synchronized (obj) {
            getOrCreateUserStateLocked(this.mCurrentUserId);
        }
        boolean z = context.getResources().getBoolean(R.bool.config_useRoundIcon);
        this.mExternalInputLoggingDisplayNameFilterEnabled = z;
        if (z) {
            String[] stringArray = context.getResources().getStringArray(17236338);
            String[] stringArray2 = context.getResources().getStringArray(17236337);
            hashSet.addAll(Arrays.asList(stringArray));
            arrayList.addAll(Arrays.asList(stringArray2));
        }
    }

    public static ITvInputSession getSessionLocked(SessionState sessionState) {
        ITvInputSession iTvInputSession = sessionState.session;
        if (iTvInputSession != null) {
            return iTvInputSession;
        }
        throw new IllegalStateException("Session not yet created for token " + sessionState.sessionToken);
    }

    public static SessionState getSessionStateLocked(IBinder iBinder, int i, UserState userState) {
        SessionState sessionState = (SessionState) ((HashMap) userState.sessionStateMap).get(iBinder);
        if (sessionState == null) {
            throw new SessionNotFoundException("Session state not found for token " + iBinder);
        }
        if (i == 1000 || i == sessionState.callingUid) {
            return sessionState;
        }
        throw new SecurityException("Illegal access to the session with token " + iBinder + " from uid " + i);
    }

    public static int getVideoUnavailableReasonForStatsd(int i) {
        int i2 = i + 100;
        if (i2 < 100 || i2 > 118) {
            return 100;
        }
        return i2;
    }

    public static void notifyInputStateChangedLocked(UserState userState, String str, int i) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                userState.mCallbacks.getBroadcastItem(i2).onInputStateChanged(str, i);
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "failed to report state change to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    public static void sendSessionTokenToClientLocked(ITvInputClient iTvInputClient, String str, IBinder iBinder, InputChannel inputChannel, int i) {
        try {
            iTvInputClient.onSessionCreated(str, iBinder, inputChannel, i);
        } catch (RemoteException e) {
            Slog.e("TvInputManagerService", "error in onSessionCreated", e);
        }
    }

    public final void abortPendingCreateSessionRequestsLocked(ServiceState serviceState, String str, int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) serviceState.sessionTokens).iterator();
        while (it.hasNext()) {
            SessionState sessionState = (SessionState) ((HashMap) orCreateUserStateLocked.sessionStateMap).get((IBinder) it.next());
            if (sessionState.session == null && (str == null || sessionState.inputId.equals(str))) {
                arrayList.add(sessionState);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            SessionState sessionState2 = (SessionState) it2.next();
            removeSessionStateLocked(sessionState2.userId, sessionState2.sessionToken);
            sendSessionTokenToClientLocked(sessionState2.client, sessionState2.inputId, null, null, sessionState2.seq);
        }
        if (serviceState.isHardware) {
            updateHardwareServiceConnectionDelayed(i);
        } else {
            updateServiceConnectionLocked(i, serviceState.component);
        }
    }

    public final void bindService(ServiceState serviceState, int i) {
        if (serviceState.bound) {
            if (serviceState.isHardware) {
                updateHardwareServiceConnectionDelayed(i);
                return;
            }
            return;
        }
        Intent component = new Intent("android.media.tv.TvInputService").setComponent(serviceState.component);
        Context context = this.mContext;
        UserHandle userHandle = new UserHandle(i);
        InputServiceConnection inputServiceConnection = serviceState.connection;
        boolean bindServiceAsUser = context.bindServiceAsUser(component, inputServiceConnection, 33554433, userHandle);
        serviceState.bound = bindServiceAsUser;
        if (bindServiceAsUser) {
            return;
        }
        Slog.e("TvInputManagerService", "failed to bind " + serviceState.component + " for userId " + i);
        this.mContext.unbindService(inputServiceConnection);
    }

    public final void buildTvContentRatingSystemListLocked(int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ((ArrayList) orCreateUserStateLocked.contentRatingSystemList).clear();
        Iterator<ResolveInfo> it = this.mContext.getPackageManager().queryBroadcastReceivers(new Intent("android.media.tv.action.QUERY_CONTENT_RATING_SYSTEMS"), 128).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            Bundle bundle = activityInfo.metaData;
            if (bundle != null) {
                int i2 = bundle.getInt("android.media.tv.metadata.CONTENT_RATING_SYSTEMS");
                if (i2 == 0) {
                    StringBuilder sb = new StringBuilder("Missing meta-data 'android.media.tv.metadata.CONTENT_RATING_SYSTEMS' on receiver ");
                    sb.append(activityInfo.packageName);
                    sb.append("/");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, activityInfo.name, "TvInputManagerService");
                } else {
                    ((ArrayList) orCreateUserStateLocked.contentRatingSystemList).add(TvContentRatingSystemInfo.createTvContentRatingSystemInfo(i2, activityInfo.applicationInfo));
                }
            }
        }
    }

    public final void buildTvInputListLocked(int i, String[] strArr) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ((HashSet) orCreateUserStateLocked.packageSet).clear();
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(new Intent("android.media.tv.TvInputService"), 132, i);
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo : queryIntentServicesAsUser) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if ("android.permission.BIND_TV_INPUT".equals(serviceInfo.permission)) {
                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (packageManager.checkPermission("android.permission.TV_INPUT_HARDWARE", componentName.getPackageName()) == 0) {
                    ServiceState serviceState = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(componentName);
                    if (serviceState == null) {
                        ((HashMap) orCreateUserStateLocked.serviceStateMap).put(componentName, new ServiceState(this, componentName, i));
                        updateServiceConnectionLocked(i, componentName);
                    } else {
                        arrayList.addAll(((HashMap) serviceState.hardwareInputMap).values());
                    }
                } else {
                    try {
                        arrayList.add(new TvInputInfo.Builder(this.mContext, resolveInfo).build());
                    } catch (Exception e) {
                        Slog.e("TvInputManagerService", "failed to load TV input " + serviceInfo.name, e);
                    }
                }
                ((HashSet) orCreateUserStateLocked.packageSet).add(serviceInfo.packageName);
            } else {
                ProfileService$1$$ExternalSyntheticOutline0.m(new StringBuilder("Skipping TV input "), serviceInfo.name, ": it does not require the permission android.permission.BIND_TV_INPUT", "TvInputManagerService");
            }
        }
        Collections.sort(arrayList, Comparator.comparing(new TvInputManagerService$$ExternalSyntheticLambda0()));
        HashMap hashMap = new HashMap();
        ArrayMap arrayMap = new ArrayMap(hashMap.size());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            TvInputInfo tvInputInfo = (TvInputInfo) it.next();
            String id = tvInputInfo.getId();
            Integer num = (Integer) arrayMap.get(id);
            int intValue = num != null ? 1 + num.intValue() : 1;
            arrayMap.put(id, Integer.valueOf(intValue));
            TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(id);
            if (tvInputState == null) {
                tvInputState = new TvInputState();
                tvInputState.state = 0;
            }
            tvInputState.info = tvInputInfo;
            tvInputState.uid = getInputUid(tvInputInfo);
            hashMap.put(id, tvInputState);
            tvInputState.inputNumber = intValue;
        }
        for (String str : hashMap.keySet()) {
            if (!orCreateUserStateLocked.inputMap.containsKey(str)) {
                int beginBroadcast = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                for (int i2 = 0; i2 < beginBroadcast; i2++) {
                    try {
                        orCreateUserStateLocked.mCallbacks.getBroadcastItem(i2).onInputAdded(str);
                    } catch (RemoteException e2) {
                        Slog.e("TvInputManagerService", "failed to report added input to callback", e2);
                    }
                }
                orCreateUserStateLocked.mCallbacks.finishBroadcast();
            } else if (strArr != null) {
                ComponentName component = ((TvInputState) hashMap.get(str)).info.getComponent();
                int length = strArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        if (component.getPackageName().equals(strArr[i3])) {
                            updateServiceConnectionLocked(i, component);
                            int beginBroadcast2 = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                            for (int i4 = 0; i4 < beginBroadcast2; i4++) {
                                try {
                                    orCreateUserStateLocked.mCallbacks.getBroadcastItem(i4).onInputUpdated(str);
                                } catch (RemoteException e3) {
                                    Slog.e("TvInputManagerService", "failed to report updated input to callback", e3);
                                }
                            }
                            orCreateUserStateLocked.mCallbacks.finishBroadcast();
                        } else {
                            i3++;
                        }
                    }
                }
            }
        }
        for (String str2 : orCreateUserStateLocked.inputMap.keySet()) {
            if (!hashMap.containsKey(str2)) {
                ServiceState serviceState2 = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(((TvInputState) orCreateUserStateLocked.inputMap.get(str2)).info.getComponent());
                if (serviceState2 != null) {
                    abortPendingCreateSessionRequestsLocked(serviceState2, str2, i);
                }
                int beginBroadcast3 = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                for (int i5 = 0; i5 < beginBroadcast3; i5++) {
                    try {
                        orCreateUserStateLocked.mCallbacks.getBroadcastItem(i5).onInputRemoved(str2);
                    } catch (RemoteException e4) {
                        Slog.e("TvInputManagerService", "failed to report removed input to callback", e4);
                    }
                }
                orCreateUserStateLocked.mCallbacks.finishBroadcast();
            }
        }
        orCreateUserStateLocked.inputMap.clear();
        orCreateUserStateLocked.inputMap = hashMap;
    }

    public final void clearSessionAndNotifyClientLocked(SessionState sessionState) {
        ITvInputClient iTvInputClient = sessionState.client;
        if (iTvInputClient != null) {
            try {
                iTvInputClient.onSessionReleased(sessionState.seq);
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "error in onSessionReleased", e);
            }
        }
        for (SessionState sessionState2 : ((HashMap) getOrCreateUserStateLocked(sessionState.userId).sessionStateMap).values()) {
            if (sessionState.sessionToken == sessionState2.hardwareSessionToken) {
                releaseSessionLocked(1000, sessionState2.sessionToken, sessionState.userId);
                try {
                    sessionState2.client.onSessionReleased(sessionState2.seq);
                } catch (RemoteException e2) {
                    Slog.e("TvInputManagerService", "error in onSessionReleased", e2);
                }
            }
        }
        removeSessionStateLocked(sessionState.userId, sessionState.sessionToken);
    }

    public final List getCurrentTunedInfosInternalLocked(UserState userState, int i, int i2) {
        Integer num;
        int i3;
        int i4;
        ArrayList arrayList = new ArrayList();
        boolean z = this.mContext.checkPermission("com.android.providers.tv.permission.ACCESS_WATCHED_PROGRAMS", i, i2) == 0;
        for (SessionState sessionState : ((HashMap) userState.sessionStateMap).values()) {
            if (sessionState.isCurrent) {
                int i5 = sessionState.callingUid;
                if (i5 == i2) {
                    num = 0;
                    i4 = 1;
                } else {
                    num = (Integer) ((HashMap) userState.mAppTagMap).get(Integer.valueOf(i5));
                    if (num == null) {
                        int i6 = userState.mNextAppTag;
                        userState.mNextAppTag = i6 + 1;
                        num = Integer.valueOf(i6);
                        ((HashMap) userState.mAppTagMap).put(Integer.valueOf(sessionState.callingUid), num);
                    }
                    if ((this.mContext.getPackageManager().getApplicationInfo(sessionState.componentName.getPackageName(), 0).flags & 1) != 0) {
                        i3 = 2;
                        i4 = i3;
                    }
                    i3 = 3;
                    i4 = i3;
                }
                arrayList.add(new TunedInfo(sessionState.inputId, z ? sessionState.currentChannel : null, sessionState.isRecordingSession, sessionState.isVisible, sessionState.isMainSession, i4, num.intValue()));
            }
        }
        return arrayList;
    }

    public final int getInputUid(TvInputInfo tvInputInfo) {
        try {
            return getContext().getPackageManager().getApplicationInfo(tvInputInfo.getServiceInfo().packageName, 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.w("TvInputManagerService", "Unable to get UID for  " + tvInputInfo, e);
            return -1;
        }
    }

    public final UserState getOrCreateUserStateLocked(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked != null) {
            return userStateLocked;
        }
        UserState userState = new UserState(this.mContext, i);
        this.mUserStates.put(i, userState);
        return userState;
    }

    public final ServiceState getServiceStateLocked(int i, ComponentName componentName) {
        ServiceState serviceState = (ServiceState) ((HashMap) getOrCreateUserStateLocked(i).serviceStateMap).get(componentName);
        if (serviceState != null) {
            return serviceState;
        }
        throw new IllegalStateException("Service state not found for " + componentName + " (userId=" + i + ")");
    }

    public final SessionState getSessionStateLocked(int i, IBinder iBinder, int i2) {
        return getSessionStateLocked(iBinder, i, getOrCreateUserStateLocked(i2));
    }

    public final UserState getUserStateLocked(int i) {
        return (UserState) this.mUserStates.get(i);
    }

    public final void logExternalInputEvent(int i, String str, SessionState sessionState) {
        int i2;
        String str2;
        int i3;
        HdmiDeviceInfo hdmiDeviceInfo;
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(sessionState.userId);
        TvInputState tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
        if (tvInputState == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m("Cannot find input state for input id ", str, "TvInputManagerService");
            str = sessionState.inputId;
            tvInputState = (TvInputState) orCreateUserStateLocked.inputMap.get(str);
        }
        if (tvInputState == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m("Cannot find input state for sessionState.inputId ", str, "TvInputManagerService");
            return;
        }
        TvInputInfo tvInputInfo = tvInputState.info;
        if (tvInputInfo == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m("TvInputInfo is null for input id ", str, "TvInputManagerService");
            return;
        }
        int i4 = tvInputState.state;
        int type = tvInputInfo.getType();
        String charSequence = tvInputInfo.loadLabel(this.mContext).toString();
        String str3 = sessionState.sessionId;
        if (tvInputInfo.getType() != 1007 || (hdmiDeviceInfo = tvInputInfo.getHdmiDeviceInfo()) == null) {
            i2 = -1;
            str2 = charSequence;
        } else {
            int portId = hdmiDeviceInfo.getPortId();
            if (hdmiDeviceInfo.isCecDevice()) {
                String displayName = hdmiDeviceInfo.getDisplayName();
                if (this.mExternalInputLoggingDisplayNameFilterEnabled) {
                    if (displayName == null) {
                        displayName = "NULL_DISPLAY_NAME";
                    } else if (!this.mExternalInputLoggingDeviceOnScreenDisplayNames.contains(displayName)) {
                        Iterator it = ((ArrayList) this.mExternalInputLoggingDeviceBrandNames).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                displayName = "FILTERED_DISPLAY_NAME";
                                break;
                            }
                            String str4 = (String) it.next();
                            if (displayName.toUpperCase().contains(str4.toUpperCase())) {
                                displayName = str4;
                                break;
                            }
                        }
                    }
                }
                i3 = hdmiDeviceInfo.getVendorId();
                str2 = displayName;
                i2 = portId;
                FrameworkStatsLog.write(FrameworkStatsLog.EXTERNAL_TV_INPUT_EVENT, i, i4, type, i3, i2, str3, str2);
            }
            str2 = charSequence;
            i2 = portId;
        }
        i3 = 16777215;
        FrameworkStatsLog.write(FrameworkStatsLog.EXTERNAL_TV_INPUT_EVENT, i, i4, type, i3, i2, str3, str2);
    }

    public final void notifyCurrentChannelInfosUpdatedLocked(UserState userState) {
        int beginBroadcast = userState.mCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                ITvInputManagerCallback broadcastItem = userState.mCallbacks.getBroadcastItem(i);
                Pair pair = (Pair) ((HashMap) userState.callbackPidUidMap).get(broadcastItem);
                if (this.mContext.checkPermission("android.permission.ACCESS_TUNED_INFO", ((Integer) pair.first).intValue(), ((Integer) pair.second).intValue()) == 0) {
                    broadcastItem.onCurrentTunedInfosUpdated(getCurrentTunedInfosInternalLocked(userState, ((Integer) pair.first).intValue(), ((Integer) pair.second).intValue()));
                }
            } catch (RemoteException e) {
                Slog.e("TvInputManagerService", "failed to report updated current channel infos to callback", e);
            }
        }
        userState.mCallbacks.finishBroadcast();
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            PackageMonitor packageMonitor = new PackageMonitor() { // from class: com.android.server.tv.TvInputManagerService.1
                public final void buildTvInputList(String[] strArr) {
                    int changingUserId = getChangingUserId();
                    synchronized (TvInputManagerService.this.mLock) {
                        try {
                            TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                            if (tvInputManagerService.mCurrentUserId != changingUserId) {
                                if (((HashSet) tvInputManagerService.mRunningProfiles).contains(Integer.valueOf(changingUserId))) {
                                }
                            }
                            TvInputManagerService.this.buildTvInputListLocked(changingUserId, strArr);
                            TvInputManagerService.this.buildTvContentRatingSystemListLocked(changingUserId);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public final boolean onPackageChanged(String str, int i2, String[] strArr) {
                    return true;
                }

                public final void onPackageUpdateFinished(String str, int i2) {
                    buildTvInputList(new String[]{str});
                }

                public final void onPackagesAvailable(String[] strArr) {
                    if (isReplacing()) {
                        buildTvInputList(strArr);
                    }
                }

                public final void onPackagesUnavailable(String[] strArr) {
                    if (isReplacing()) {
                        buildTvInputList(strArr);
                    }
                }

                public final void onSomePackagesChanged() {
                    if (isReplacing()) {
                        return;
                    }
                    buildTvInputList(null);
                }
            };
            Context context = this.mContext;
            UserHandle userHandle = UserHandle.ALL;
            packageMonitor.register(context, (Looper) null, userHandle, true);
            IntentFilter intentFilter = new IntentFilter();
            ActivityManagerService$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.USER_SWITCHED", "android.intent.action.USER_REMOVED", "android.intent.action.USER_STARTED", "android.intent.action.USER_STOPPED");
            this.mContext.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.tv.TvInputManagerService.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    String action = intent.getAction();
                    if ("android.intent.action.USER_SWITCHED".equals(action)) {
                        TvInputManagerService.this.switchUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                        return;
                    }
                    if (!"android.intent.action.USER_REMOVED".equals(action)) {
                        if (!"android.intent.action.USER_STARTED".equals(action)) {
                            if ("android.intent.action.USER_STOPPED".equals(action)) {
                                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                                TvInputManagerService tvInputManagerService = TvInputManagerService.this;
                                synchronized (tvInputManagerService.mLock) {
                                    try {
                                        if (intExtra == tvInputManagerService.mCurrentUserId) {
                                            tvInputManagerService.switchUser(ActivityManager.getCurrentUser());
                                        } else {
                                            tvInputManagerService.releaseSessionOfUserLocked(intExtra);
                                            tvInputManagerService.unbindServiceOfUserLocked(intExtra);
                                            ((HashSet) tvInputManagerService.mRunningProfiles).remove(Integer.valueOf(intExtra));
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            }
                            return;
                        }
                        int intExtra2 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                        TvInputManagerService tvInputManagerService2 = TvInputManagerService.this;
                        synchronized (tvInputManagerService2.mLock) {
                            try {
                                if (intExtra2 != tvInputManagerService2.mCurrentUserId) {
                                    if (!((HashSet) tvInputManagerService2.mRunningProfiles).contains(Integer.valueOf(intExtra2))) {
                                        UserInfo userInfo = tvInputManagerService2.mUserManager.getUserInfo(intExtra2);
                                        UserInfo profileParent = tvInputManagerService2.mUserManager.getProfileParent(intExtra2);
                                        if (userInfo.isProfile() && profileParent != null && profileParent.id == tvInputManagerService2.mCurrentUserId) {
                                            ((HashSet) tvInputManagerService2.mRunningProfiles).add(Integer.valueOf(intExtra2));
                                            tvInputManagerService2.buildTvInputListLocked(intExtra2, null);
                                            tvInputManagerService2.buildTvContentRatingSystemListLocked(intExtra2);
                                        }
                                        return;
                                    }
                                }
                                return;
                            } finally {
                            }
                        }
                    }
                    TvInputManagerService tvInputManagerService3 = TvInputManagerService.this;
                    int intExtra3 = intent.getIntExtra("android.intent.extra.user_handle", 0);
                    synchronized (tvInputManagerService3.mLock) {
                        try {
                            UserState userStateLocked = tvInputManagerService3.getUserStateLocked(intExtra3);
                            if (userStateLocked == null) {
                                return;
                            }
                            boolean z = false;
                            for (SessionState sessionState : ((HashMap) userStateLocked.sessionStateMap).values()) {
                                ITvInputSession iTvInputSession = sessionState.session;
                                if (iTvInputSession != null) {
                                    try {
                                        try {
                                            iTvInputSession.release();
                                            sessionState.currentChannel = null;
                                            if (sessionState.isCurrent) {
                                                sessionState.isCurrent = false;
                                                z = true;
                                            }
                                        } catch (RemoteException e) {
                                            Slog.e("TvInputManagerService", "error in release", e);
                                            if (z) {
                                            }
                                        }
                                        if (z) {
                                            tvInputManagerService3.notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                                        }
                                    } catch (Throwable th) {
                                        if (z) {
                                            tvInputManagerService3.notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                                        }
                                        throw th;
                                    }
                                }
                            }
                            ((HashMap) userStateLocked.sessionStateMap).clear();
                            for (ServiceState serviceState : ((HashMap) userStateLocked.serviceStateMap).values()) {
                                ITvInputService iTvInputService = serviceState.service;
                                if (iTvInputService != null) {
                                    ServiceCallback serviceCallback = serviceState.callback;
                                    if (serviceCallback != null) {
                                        try {
                                            iTvInputService.unregisterCallback(serviceCallback);
                                        } catch (RemoteException e2) {
                                            Slog.e("TvInputManagerService", "error in unregisterCallback", e2);
                                        }
                                    }
                                    tvInputManagerService3.unbindService(serviceState);
                                }
                            }
                            ((HashMap) userStateLocked.serviceStateMap).clear();
                            userStateLocked.inputMap.clear();
                            ((HashSet) userStateLocked.packageSet).clear();
                            ((ArrayList) userStateLocked.contentRatingSystemList).clear();
                            ((HashMap) userStateLocked.clientStateMap).clear();
                            userStateLocked.mCallbacks.kill();
                            userStateLocked.mainSessionToken = null;
                            ((HashSet) tvInputManagerService3.mRunningProfiles).remove(Integer.valueOf(intExtra3));
                            tvInputManagerService3.mUserStates.remove(intExtra3);
                            if (intExtra3 == tvInputManagerService3.mCurrentUserId) {
                                tvInputManagerService3.switchUser(0);
                            }
                        } finally {
                        }
                    }
                }
            }, userHandle, intentFilter, null, null);
        } else if (i == 600) {
            synchronized (this.mLock) {
                buildTvInputListLocked(this.mCurrentUserId, null);
                buildTvContentRatingSystemListLocked(this.mCurrentUserId);
            }
        }
        TvInputHardwareManager tvInputHardwareManager = this.mTvInputHardwareManager;
        tvInputHardwareManager.getClass();
        if (i == 500) {
            IHdmiControlService asInterface = IHdmiControlService.Stub.asInterface(ServiceManager.getService("hdmi_control"));
            if (asInterface != null) {
                try {
                    asInterface.addHotplugEventListener(tvInputHardwareManager.mHdmiHotplugEventListener);
                    asInterface.addDeviceEventListener(tvInputHardwareManager.mHdmiDeviceEventListener);
                    asInterface.addSystemAudioModeChangeListener(tvInputHardwareManager.mHdmiSystemAudioModeChangeListener);
                    synchronized (tvInputHardwareManager.mLock) {
                        ((ArrayList) tvInputHardwareManager.mHdmiDeviceList).addAll(asInterface.getInputDevices());
                    }
                } catch (RemoteException e) {
                    Slog.w("TvInputHardwareManager", "Error registering listeners to HdmiControlService:", e);
                }
            } else {
                Slog.w("TvInputHardwareManager", "HdmiControlService is not available");
            }
            tvInputHardwareManager.mContext.registerReceiver(tvInputHardwareManager.mVolumeReceiver, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.media.VOLUME_CHANGED_ACTION", "android.media.STREAM_MUTE_CHANGED_ACTION"));
            tvInputHardwareManager.mCurrentMaxIndex = tvInputHardwareManager.mAudioManager.getStreamMaxVolume(3);
            tvInputHardwareManager.mCurrentIndex = tvInputHardwareManager.mAudioManager.getStreamVolume(3);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("tv_input", new BinderService());
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId != targetUser.getUserIdentifier()) {
                    return;
                }
                buildTvInputListLocked(this.mCurrentUserId, null);
                buildTvContentRatingSystemListLocked(this.mCurrentUserId);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0042, code lost:
    
        if (r1 == null) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.tv.TvInputManagerService.SessionState releaseSessionLocked(int r6, android.os.IBinder r7, int r8) {
        /*
            r5 = this;
            r0 = 0
            com.android.server.tv.TvInputManagerService$SessionState r1 = r5.getSessionStateLocked(r6, r7, r8)     // Catch: java.lang.Throwable -> L36 java.lang.Throwable -> L39
            com.android.server.tv.TvInputManagerService$UserState r2 = r5.getOrCreateUserStateLocked(r8)     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            android.media.tv.ITvInputSession r3 = r1.session     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            r4 = 0
            if (r3 == 0) goto L28
            android.os.IBinder r3 = r2.mainSessionToken     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            if (r7 != r3) goto L1a
            r5.setMainLocked(r6, r8, r7, r4)     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            goto L1a
        L16:
            r5 = move-exception
            goto L57
        L18:
            r6 = move-exception
            goto L3b
        L1a:
            android.media.tv.ITvInputSession r6 = r1.session     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            android.os.IBinder r6 = r6.asBinder()     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            r6.unlinkToDeath(r1, r4)     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            android.media.tv.ITvInputSession r6 = r1.session     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            r6.release()     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
        L28:
            r1.currentChannel = r0     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            boolean r6 = r1.isCurrent     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            if (r6 == 0) goto L33
            r1.isCurrent = r4     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
            r5.notifyCurrentChannelInfosUpdatedLocked(r2)     // Catch: java.lang.Throwable -> L16 java.lang.Throwable -> L18
        L33:
            r1.session = r0
            goto L45
        L36:
            r5 = move-exception
            r1 = r0
            goto L57
        L39:
            r6 = move-exception
            r1 = r0
        L3b:
            java.lang.String r2 = "TvInputManagerService"
            java.lang.String r3 = "error in releaseSession"
            android.util.Slog.e(r2, r3, r6)     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L45
            goto L33
        L45:
            com.android.server.tv.TvInputManagerService$SessionState r6 = r5.mOnScreenSessionState
            if (r6 != r1) goto L53
            r6 = 2
            java.lang.String r2 = r5.mOnScreenInputId
            r5.logExternalInputEvent(r6, r2, r1)
            r5.mOnScreenInputId = r0
            r5.mOnScreenSessionState = r0
        L53:
            r5.removeSessionStateLocked(r8, r7)
            return r1
        L57:
            if (r1 == 0) goto L5b
            r1.session = r0
        L5b:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.tv.TvInputManagerService.releaseSessionLocked(int, android.os.IBinder, int):com.android.server.tv.TvInputManagerService$SessionState");
    }

    public final void releaseSessionOfUserLocked(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (SessionState sessionState : ((HashMap) userStateLocked.sessionStateMap).values()) {
            if (sessionState.session != null && !sessionState.isRecordingSession) {
                arrayList.add(sessionState);
            }
        }
        Iterator it = arrayList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            SessionState sessionState2 = (SessionState) it.next();
            try {
                try {
                    sessionState2.session.release();
                    sessionState2.currentChannel = null;
                    if (sessionState2.isCurrent) {
                        sessionState2.isCurrent = false;
                        z = true;
                    }
                } catch (RemoteException e) {
                    Slog.e("TvInputManagerService", "error in release", e);
                    if (!z) {
                    }
                }
                if (!z) {
                    clearSessionAndNotifyClientLocked(sessionState2);
                }
                notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                clearSessionAndNotifyClientLocked(sessionState2);
            } catch (Throwable th) {
                if (z) {
                    notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                }
                throw th;
            }
        }
    }

    public final void removeSessionStateLocked(int i, IBinder iBinder) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        if (iBinder == orCreateUserStateLocked.mainSessionToken) {
            orCreateUserStateLocked.mainSessionToken = null;
        }
        SessionState sessionState = (SessionState) ((HashMap) orCreateUserStateLocked.sessionStateMap).remove(iBinder);
        if (sessionState == null) {
            Slog.e("TvInputManagerService", "sessionState null, no more remove session action!");
            return;
        }
        ClientState clientState = (ClientState) ((HashMap) orCreateUserStateLocked.clientStateMap).get(sessionState.client.asBinder());
        if (clientState != null) {
            ((ArrayList) clientState.sessionTokens).remove(iBinder);
            if (((ArrayList) clientState.sessionTokens).isEmpty()) {
                ((HashMap) orCreateUserStateLocked.clientStateMap).remove(sessionState.client.asBinder());
                sessionState.client.asBinder().unlinkToDeath(clientState, 0);
            }
        }
        ((HashMap) this.mSessionIdToSessionStateMap).remove(sessionState.sessionId);
        ServiceState serviceState = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(sessionState.componentName);
        if (serviceState != null) {
            ((ArrayList) serviceState.sessionTokens).remove(iBinder);
        }
        if (serviceState.isHardware) {
            updateHardwareServiceConnectionDelayed(i);
        } else {
            updateServiceConnectionLocked(i, sessionState.componentName);
        }
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = iBinder;
        obtain.arg2 = Long.valueOf(System.currentTimeMillis());
        this.mMessageHandler.obtainMessage(2, obtain).sendToTarget();
    }

    public final void setMainLocked(int i, int i2, IBinder iBinder, boolean z) {
        try {
            SessionState sessionStateLocked = getSessionStateLocked(i, iBinder, i2);
            IBinder iBinder2 = sessionStateLocked.hardwareSessionToken;
            if (iBinder2 != null) {
                sessionStateLocked = getSessionStateLocked(1000, iBinder2, i2);
            }
            if (getServiceStateLocked(i2, sessionStateLocked.componentName).isHardware) {
                getSessionLocked(sessionStateLocked).setMain(z);
                if (sessionStateLocked.isMainSession != z) {
                    UserState userStateLocked = getUserStateLocked(i2);
                    sessionStateLocked.isMainSession = z;
                    notifyCurrentChannelInfosUpdatedLocked(userStateLocked);
                }
            }
        } catch (RemoteException | SessionNotFoundException e) {
            Slog.e("TvInputManagerService", "error in setMain", e);
        }
    }

    public final void switchUser(int i) {
        Context context;
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId == i) {
                    return;
                }
                if (this.mUserManager.getUserInfo(i).isProfile()) {
                    Slog.w("TvInputManagerService", "cannot switch to a profile!");
                    return;
                }
                Iterator it = ((HashSet) this.mRunningProfiles).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    releaseSessionOfUserLocked(intValue);
                    unbindServiceOfUserLocked(intValue);
                }
                ((HashSet) this.mRunningProfiles).clear();
                releaseSessionOfUserLocked(this.mCurrentUserId);
                unbindServiceOfUserLocked(this.mCurrentUserId);
                this.mCurrentUserId = i;
                buildTvInputListLocked(i, null);
                buildTvContentRatingSystemListLocked(i);
                MessageHandler messageHandler = this.mMessageHandler;
                UserHandle userHandle = new UserHandle(i);
                try {
                    context = this.mContext.createPackageContextAsUser("android", 0, userHandle);
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.e("TvInputManagerService", "failed to create package context as user " + userHandle);
                    context = this.mContext;
                }
                messageHandler.obtainMessage(3, context.getContentResolver()).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindService(ServiceState serviceState) {
        if (serviceState.bound) {
            this.mContext.unbindService(serviceState.connection);
            serviceState.bound = false;
            serviceState.service = null;
            serviceState.callback = null;
        }
    }

    public final void unbindServiceOfUserLocked(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        Iterator it = ((HashMap) userStateLocked.serviceStateMap).keySet().iterator();
        while (it.hasNext()) {
            ServiceState serviceState = (ServiceState) ((HashMap) userStateLocked.serviceStateMap).get((ComponentName) it.next());
            if (serviceState != null && ((ArrayList) serviceState.sessionTokens).isEmpty()) {
                ServiceCallback serviceCallback = serviceState.callback;
                if (serviceCallback != null) {
                    try {
                        serviceState.service.unregisterCallback(serviceCallback);
                    } catch (RemoteException e) {
                        Slog.e("TvInputManagerService", "error in unregisterCallback", e);
                    }
                }
                unbindService(serviceState);
                it.remove();
            }
        }
    }

    public final void updateHardwareServiceConnectionDelayed(int i) {
        MessageHandler messageHandler = this.mMessageHandler;
        messageHandler.removeMessages(4);
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = Integer.valueOf(i);
        messageHandler.sendMessageDelayed(messageHandler.obtainMessage(4, obtain), 10000L);
    }

    public final void updateServiceConnectionLocked(int i, ComponentName componentName) {
        boolean z;
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ServiceState serviceState = (ServiceState) ((HashMap) orCreateUserStateLocked.serviceStateMap).get(componentName);
        if (serviceState == null) {
            return;
        }
        boolean z2 = false;
        if (serviceState.reconnecting) {
            if (!((ArrayList) serviceState.sessionTokens).isEmpty()) {
                return;
            } else {
                serviceState.reconnecting = false;
            }
        }
        int i2 = this.mCurrentUserId;
        boolean z3 = serviceState.isHardware;
        if (i != i2) {
            if (!((HashSet) this.mRunningProfiles).contains(Integer.valueOf(i))) {
                z = !((ArrayList) serviceState.sessionTokens).isEmpty();
                if (!z && !serviceState.bound) {
                    bindService(serviceState, i);
                    return;
                }
                if (z && serviceState.bound) {
                    unbindService(serviceState);
                    if (z3) {
                        return;
                    }
                    ((HashMap) orCreateUserStateLocked.serviceStateMap).remove(componentName);
                    return;
                }
            }
        }
        if (!((ArrayList) serviceState.sessionTokens).isEmpty() || (z3 && serviceState.neverConnected)) {
            z2 = true;
        }
        z = z2;
        if (!z) {
        }
        if (z) {
        }
    }
}
