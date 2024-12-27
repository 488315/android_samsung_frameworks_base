package com.android.server.media;

import android.R;
import android.app.ActivityManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRoutesInfo;
import android.media.IAudioRoutesObserver;
import android.media.IAudioService;
import android.media.IMediaRouter2;
import android.media.IMediaRouter2Manager;
import android.media.IMediaRouterClient;
import android.media.IMediaRouterService;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.MediaRouterClientState;
import android.media.RemoteDisplayState;
import android.media.RouteDiscoveryPreference;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.media.flags.Flags;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.UserManagerInternal;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class MediaRouterService extends IMediaRouterService.Stub implements Watchdog.Monitor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BluetoothDevice mActiveBluetoothDevice;
    public final AudioPlayerStateMonitor mAudioPlayerStateMonitor;
    public final IAudioService mAudioService;
    public final String mBluetoothA2dpRouteId;
    public final Context mContext;
    public final String mDefaultAudioRouteId;
    public final Handler mHandler;
    public final Looper mLooper;
    public final MediaRouter2ServiceImpl mService2;
    public final UserManagerInternal mUserManagerInternal;
    public final Object mLock = new Object();
    public final SparseArray mUserRecords = new SparseArray();
    public final ArrayMap mAllClientRecords = new ArrayMap();
    public int mCurrentActiveUserId = -1;
    public final IntArray mActivePlayerMinPriorityQueue = new IntArray();
    public final IntArray mActivePlayerUidMinPriorityQueue = new IntArray();
    public final MediaRouterServiceBroadcastReceiver mReceiver =
            new MediaRouterServiceBroadcastReceiver();
    public int mAudioRouteMainType = 0;
    public boolean mGlobalBluetoothA2dpOn = false;
    public boolean mForceBluetoothA2dpOn = false;

    public final class AudioPlayerActiveStateChangedListenerImpl
            implements AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener {
        public final
        MediaRouterService$AudioPlayerActiveStateChangedListenerImpl$$ExternalSyntheticLambda0
                mRestoreBluetoothA2dpRunnable;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.media.MediaRouterService$AudioPlayerActiveStateChangedListenerImpl$$ExternalSyntheticLambda0] */
        public AudioPlayerActiveStateChangedListenerImpl() {
            this.mRestoreBluetoothA2dpRunnable =
                    new Runnable() { // from class:
                                     // com.android.server.media.MediaRouterService$AudioPlayerActiveStateChangedListenerImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaRouterService.this.restoreBluetoothA2dp();
                        }
                    };
        }

        @Override // com.android.server.media.AudioPlayerStateMonitor.OnAudioPlayerActiveStateChangedListener
        public final void onAudioPlayerActiveStateChanged(
                AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z) {
            int i;
            ClientRecord clientRecord;
            ArrayList arrayList;
            boolean z2 = !z && audioPlaybackConfiguration.isActive();
            int clientUid = audioPlaybackConfiguration.getClientUid();
            int indexOf =
                    MediaRouterService.this.mActivePlayerMinPriorityQueue.indexOf(
                            audioPlaybackConfiguration.getPlayerInterfaceId());
            if (indexOf >= 0) {
                MediaRouterService.this.mActivePlayerMinPriorityQueue.remove(indexOf);
                MediaRouterService.this.mActivePlayerUidMinPriorityQueue.remove(indexOf);
            }
            if (z2) {
                MediaRouterService.this.mActivePlayerMinPriorityQueue.add(
                        audioPlaybackConfiguration.getPlayerInterfaceId());
                MediaRouterService.this.mActivePlayerUidMinPriorityQueue.add(clientUid);
                i = clientUid;
            } else if (MediaRouterService.this.mActivePlayerUidMinPriorityQueue.size() > 0) {
                IntArray intArray = MediaRouterService.this.mActivePlayerUidMinPriorityQueue;
                i = intArray.get(intArray.size() - 1);
            } else {
                i = -1;
            }
            MediaRouterService.this.mHandler.removeCallbacks(this.mRestoreBluetoothA2dpRunnable);
            if (i < 0) {
                MediaRouterService.this.mHandler.postDelayed(
                        this.mRestoreBluetoothA2dpRunnable, 500L);
                int i2 = MediaRouterService.$r8$clinit;
                Slog.d(
                        "MediaRouterService",
                        "onAudioPlayerActiveStateChanged: uid="
                                + clientUid
                                + ", active="
                                + z2
                                + ", delaying");
                return;
            }
            MediaRouterService mediaRouterService = MediaRouterService.this;
            synchronized (mediaRouterService.mLock) {
                try {
                    UserRecord userRecord =
                            (UserRecord)
                                    mediaRouterService.mUserRecords.get(
                                            UserHandle.getUserHandleForUid(i).getIdentifier());
                    if (userRecord != null && (arrayList = userRecord.mClientRecords) != null) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            clientRecord = (ClientRecord) it.next();
                            if (mediaRouterService.validatePackageName$1(
                                    i, clientRecord.mPackageName)) {
                                break;
                            }
                        }
                    }
                    clientRecord = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (clientRecord != null) {
                try {
                    clientRecord.mClient.onRestoreRoute();
                } catch (RemoteException unused) {
                    Slog.w(
                            "MediaRouterService",
                            "Failed to call onRestoreRoute. Client probably died.");
                }
            } else {
                mediaRouterService.restoreBluetoothA2dp();
            }
            int i3 = MediaRouterService.$r8$clinit;
            StringBuilder sb = new StringBuilder("onAudioPlayerActiveStateChanged: uid=");
            sb.append(clientUid);
            sb.append(", active=");
            sb.append(z2);
            sb.append(", restoreUid=");
            DeviceIdleController$$ExternalSyntheticOutline0.m(sb, i, "MediaRouterService");
        }
    }

    public final class AudioRoutesObserverImpl extends IAudioRoutesObserver.Stub {
        public AudioRoutesObserverImpl() {}

        public final void dispatchAudioRoutesChanged(AudioRoutesInfo audioRoutesInfo) {
            synchronized (MediaRouterService.this.mLock) {
                try {
                    boolean z = true;
                    if ("BT".equals(audioRoutesInfo.getSetForcePath())
                            && audioRoutesInfo.bluetoothName != null) {
                        MediaRouterService.this.mForceBluetoothA2dpOn = true;
                    } else if ("OTHERS".equals(audioRoutesInfo.getSetForcePath())) {
                        MediaRouterService.this.mForceBluetoothA2dpOn = false;
                    }
                    Log.i(
                            "MediaRouterService",
                            "dispatchAudioRoutesChanged setForcePath = "
                                    + audioRoutesInfo.getSetForcePath()
                                    + " mForceBluetoothA2dpOn = "
                                    + MediaRouterService.this.mForceBluetoothA2dpOn);
                    int i = audioRoutesInfo.mainType;
                    MediaRouterService mediaRouterService = MediaRouterService.this;
                    if (i != mediaRouterService.mAudioRouteMainType) {
                        if ((i & 19) == 0) {
                            CharSequence charSequence = audioRoutesInfo.bluetoothName;
                            if (charSequence == null
                                    && mediaRouterService.mActiveBluetoothDevice == null) {
                                z = false;
                            }
                            mediaRouterService.mGlobalBluetoothA2dpOn = z;
                            if (charSequence != null && z) {
                                Log.w("MediaRouterService", "force restoreBluetoothA2dp()");
                                MediaRouterService.this.restoreBluetoothA2dp();
                                MediaRouterService.this.mForceBluetoothA2dpOn = false;
                            }
                        } else {
                            mediaRouterService.mGlobalBluetoothA2dpOn = false;
                        }
                        MediaRouterService.this.mAudioRouteMainType = audioRoutesInfo.mainType;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class ClientGroup {
        public final List mClientRecords = new ArrayList();
        public String mSelectedRouteId;
    }

    public final class ClientRecord implements IBinder.DeathRecipient {
        public boolean mActiveScan;
        public final IMediaRouterClient mClient;
        public String mGroupId;
        public final String mPackageName;
        public final int mPid;
        public int mRouteTypes;
        public String mSelectedRouteId;
        public final boolean mTrusted;
        public final int mUid;
        public final UserRecord mUserRecord;

        public ClientRecord(
                UserRecord userRecord,
                IMediaRouterClient iMediaRouterClient,
                int i,
                int i2,
                String str,
                boolean z) {
            this.mUserRecord = userRecord;
            this.mClient = iMediaRouterClient;
            this.mUid = i;
            this.mPid = i2;
            this.mPackageName = str;
            this.mTrusted = z;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            MediaRouterService mediaRouterService = MediaRouterService.this;
            synchronized (mediaRouterService.mLock) {
                mediaRouterService.unregisterClientLocked(this.mClient, true);
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Client ");
            sb.append(this.mPackageName);
            sb.append(" (pid ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mPid, sb, ")");
        }
    }

    public final class MediaRouterServiceBroadcastReceiver extends BroadcastReceiver {
        public MediaRouterServiceBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction()
                    .equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                BluetoothDevice bluetoothDevice =
                        (BluetoothDevice)
                                intent.getParcelableExtra(
                                        "android.bluetooth.device.extra.DEVICE",
                                        BluetoothDevice.class);
                synchronized (MediaRouterService.this.mLock) {
                    MediaRouterService mediaRouterService = MediaRouterService.this;
                    mediaRouterService.mActiveBluetoothDevice = bluetoothDevice;
                    mediaRouterService.mGlobalBluetoothA2dpOn = bluetoothDevice != null;
                }
            }
        }
    }

    public final class UserHandler extends Handler
            implements RemoteDisplayProviderWatcher.Callback, RemoteDisplayProviderProxy.Callback {
        public boolean mClientStateUpdateScheduled;
        public int mConnectionPhase;
        public int mConnectionTimeoutReason;
        public long mConnectionTimeoutStartTime;
        public int mDiscoveryMode;
        public final ArrayList mProviderRecords;
        public boolean mRunning;
        public RouteRecord mSelectedRouteRecord;
        public final MediaRouterService mService;
        public final ArrayList mTempClients;
        public boolean mTriggeredBySmartView;
        public final UserRecord mUserRecord;
        public final RemoteDisplayProviderWatcher mWatcher;

        public final class ProviderRecord {
            public RemoteDisplayState mDescriptor;
            public final RemoteDisplayProviderProxy mProvider;
            public final ArrayList mRoutes = new ArrayList();
            public final String mUniquePrefix;

            public ProviderRecord(RemoteDisplayProviderProxy remoteDisplayProviderProxy) {
                this.mProvider = remoteDisplayProviderProxy;
                this.mUniquePrefix =
                        remoteDisplayProviderProxy.mComponentName.flattenToShortString() + ":";
            }

            public final String toString() {
                return "Provider " + this.mProvider.mComponentName.flattenToShortString();
            }

            /* JADX WARN: Removed duplicated region for block: B:30:0x00b3 A[LOOP:2: B:29:0x00b1->B:30:0x00b3, LOOP_END] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean updateDescriptor(android.media.RemoteDisplayState r13) {
                /*
                    r12 = this;
                    android.media.RemoteDisplayState r0 = r12.mDescriptor
                    r1 = 0
                    if (r0 == r13) goto Lc3
                    r12.mDescriptor = r13
                    r0 = 1
                    if (r13 == 0) goto La9
                    boolean r2 = r13.isValid()
                    java.lang.String r3 = "MediaRouterService"
                    if (r2 == 0) goto L90
                    java.util.ArrayList r13 = r13.displays
                    int r2 = r13.size()
                    r4 = r1
                    r5 = r4
                    r6 = r5
                L1b:
                    if (r4 >= r2) goto L8e
                    java.lang.Object r7 = r13.get(r4)
                    android.media.RemoteDisplayState$RemoteDisplayInfo r7 = (android.media.RemoteDisplayState.RemoteDisplayInfo) r7
                    java.lang.String r8 = r7.id
                    java.util.ArrayList r9 = r12.mRoutes
                    int r9 = r9.size()
                    r10 = r1
                L2c:
                    if (r10 >= r9) goto L42
                    java.util.ArrayList r11 = r12.mRoutes
                    java.lang.Object r11 = r11.get(r10)
                    com.android.server.media.MediaRouterService$UserHandler$RouteRecord r11 = (com.android.server.media.MediaRouterService.UserHandler.RouteRecord) r11
                    java.lang.String r11 = r11.mDescriptorId
                    boolean r11 = r11.equals(r8)
                    if (r11 == 0) goto L3f
                    goto L43
                L3f:
                    int r10 = r10 + 1
                    goto L2c
                L42:
                    r10 = -1
                L43:
                    if (r10 >= 0) goto L62
                    java.lang.StringBuilder r5 = new java.lang.StringBuilder
                    r5.<init>()
                    java.lang.String r9 = r12.mUniquePrefix
                    java.lang.String r5 = android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0.m(r5, r9, r8)
                    com.android.server.media.MediaRouterService$UserHandler$RouteRecord r9 = new com.android.server.media.MediaRouterService$UserHandler$RouteRecord
                    r9.<init>(r12, r8, r5)
                    java.util.ArrayList r5 = r12.mRoutes
                    int r8 = r6 + 1
                    r5.add(r6, r9)
                    r9.updateDescriptor(r7)
                    r5 = r0
                    r6 = r8
                    goto L8b
                L62:
                    if (r10 >= r6) goto L76
                    java.lang.StringBuilder r8 = new java.lang.StringBuilder
                    java.lang.String r9 = "Ignoring route descriptor with duplicate id: "
                    r8.<init>(r9)
                    r8.append(r7)
                    java.lang.String r7 = r8.toString()
                    android.util.Slog.w(r3, r7)
                    goto L8b
                L76:
                    java.util.ArrayList r8 = r12.mRoutes
                    java.lang.Object r8 = r8.get(r10)
                    com.android.server.media.MediaRouterService$UserHandler$RouteRecord r8 = (com.android.server.media.MediaRouterService.UserHandler.RouteRecord) r8
                    java.util.ArrayList r9 = r12.mRoutes
                    int r11 = r6 + 1
                    java.util.Collections.swap(r9, r10, r6)
                    boolean r6 = r8.updateDescriptor(r7)
                    r5 = r5 | r6
                    r6 = r11
                L8b:
                    int r4 = r4 + 1
                    goto L1b
                L8e:
                    r1 = r5
                    goto Laa
                L90:
                    java.lang.StringBuilder r13 = new java.lang.StringBuilder
                    java.lang.String r2 = "Ignoring invalid descriptor from media route provider: "
                    r13.<init>(r2)
                    com.android.server.media.RemoteDisplayProviderProxy r2 = r12.mProvider
                    android.content.ComponentName r2 = r2.mComponentName
                    java.lang.String r2 = r2.flattenToShortString()
                    r13.append(r2)
                    java.lang.String r13 = r13.toString()
                    android.util.Slog.w(r3, r13)
                La9:
                    r6 = r1
                Laa:
                    java.util.ArrayList r13 = r12.mRoutes
                    int r13 = r13.size()
                    int r13 = r13 - r0
                Lb1:
                    if (r13 < r6) goto Lc3
                    java.util.ArrayList r1 = r12.mRoutes
                    java.lang.Object r1 = r1.remove(r13)
                    com.android.server.media.MediaRouterService$UserHandler$RouteRecord r1 = (com.android.server.media.MediaRouterService.UserHandler.RouteRecord) r1
                    r2 = 0
                    r1.updateDescriptor(r2)
                    int r13 = r13 + (-1)
                    r1 = r0
                    goto Lb1
                Lc3:
                    return r1
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.android.server.media.MediaRouterService.UserHandler.ProviderRecord.updateDescriptor(android.media.RemoteDisplayState):boolean");
            }
        }

        public final class RouteRecord {
            public RemoteDisplayState.RemoteDisplayInfo mDescriptor;
            public final String mDescriptorId;
            public MediaRouterClientState.RouteInfo mImmutableInfo;
            public final MediaRouterClientState.RouteInfo mMutableInfo;
            public final ProviderRecord mProviderRecord;

            public RouteRecord(ProviderRecord providerRecord, String str, String str2) {
                this.mProviderRecord = providerRecord;
                this.mDescriptorId = str;
                this.mMutableInfo = new MediaRouterClientState.RouteInfo(str2);
            }

            public final String toString() {
                StringBuilder sb = new StringBuilder("Route ");
                sb.append(this.mMutableInfo.name);
                sb.append(" (");
                return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.mMutableInfo.id, ")");
            }

            public final boolean updateDescriptor(
                    RemoteDisplayState.RemoteDisplayInfo remoteDisplayInfo) {
                boolean z;
                boolean z2 = false;
                z2 = false;
                if (this.mDescriptor != remoteDisplayInfo) {
                    this.mDescriptor = remoteDisplayInfo;
                    if (remoteDisplayInfo != null) {
                        String str = remoteDisplayInfo.name;
                        if (Objects.equals(this.mMutableInfo.name, str)) {
                            z = false;
                        } else {
                            this.mMutableInfo.name = str;
                            z = true;
                        }
                        String str2 = remoteDisplayInfo.description;
                        if (TextUtils.isEmpty(str2)) {
                            str2 = null;
                        }
                        if (!Objects.equals(this.mMutableInfo.description, str2)) {
                            this.mMutableInfo.description = str2;
                            z = true;
                        }
                        MediaRouterClientState.RouteInfo routeInfo = this.mMutableInfo;
                        if (routeInfo.supportedTypes != 7) {
                            routeInfo.supportedTypes = 7;
                            z = true;
                        }
                        int i = remoteDisplayInfo.status;
                        boolean z3 = i == 2 || i == 3 || i == 4;
                        if (routeInfo.enabled != z3) {
                            routeInfo.enabled = z3;
                            z = true;
                        }
                        int i2 = i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? 0 : 6 : 2 : 3 : 5 : 4;
                        if (routeInfo.statusCode != i2) {
                            routeInfo.statusCode = i2;
                            z = true;
                        }
                        if (routeInfo.playbackType != 1) {
                            routeInfo.playbackType = 1;
                            z = true;
                        }
                        if (routeInfo.playbackStream != 3) {
                            routeInfo.playbackStream = 3;
                            z = true;
                        }
                        int i3 = remoteDisplayInfo.volume;
                        int i4 = remoteDisplayInfo.volumeMax;
                        if (i3 < 0) {
                            i3 = 0;
                        } else if (i3 > i4) {
                            i3 = i4;
                        }
                        if (routeInfo.volume != i3) {
                            routeInfo.volume = i3;
                            z = true;
                        }
                        if (i4 <= 0) {
                            i4 = 0;
                        }
                        if (routeInfo.volumeMax != i4) {
                            routeInfo.volumeMax = i4;
                            z = true;
                        }
                        int i5 = remoteDisplayInfo.volumeHandling == 1 ? 1 : 0;
                        if (routeInfo.volumeHandling != i5) {
                            routeInfo.volumeHandling = i5;
                            z2 = true;
                        } else {
                            z2 = z;
                        }
                        int i6 = remoteDisplayInfo.presentationDisplayId;
                        if (i6 < 0) {
                            i6 = -1;
                        }
                        if (routeInfo.presentationDisplayId != i6) {
                            routeInfo.presentationDisplayId = i6;
                            z2 = true;
                        }
                    }
                }
                if (z2) {
                    this.mImmutableInfo = null;
                }
                return z2;
            }
        }

        public UserHandler(
                MediaRouterService mediaRouterService, UserRecord userRecord, Looper looper) {
            super(looper, null, true);
            this.mProviderRecords = new ArrayList();
            this.mTempClients = new ArrayList();
            this.mDiscoveryMode = 0;
            this.mConnectionPhase = -1;
            this.mService = mediaRouterService;
            this.mUserRecord = userRecord;
            this.mWatcher =
                    new RemoteDisplayProviderWatcher(
                            mediaRouterService.mContext, this, this, userRecord.mUserId);
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x0028, code lost:

           if (r0 != 6) goto L22;
        */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0034 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x004d A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:25:0x004e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void checkSelectedRouteState() {
            /*
                r8 = this;
                com.android.server.media.MediaRouterService$UserHandler$RouteRecord r0 = r8.mSelectedRouteRecord
                r1 = -1
                r2 = 0
                if (r0 != 0) goto Lc
                r8.mConnectionPhase = r1
                r8.updateConnectionTimeout(r2)
                return
            Lc:
                android.media.RemoteDisplayState$RemoteDisplayInfo r3 = r0.mDescriptor
                r4 = 1
                if (r3 == 0) goto L91
                android.media.MediaRouterClientState$RouteInfo r0 = r0.mMutableInfo
                boolean r3 = r0.enabled
                if (r3 != 0) goto L19
                goto L91
            L19:
                int r3 = r8.mConnectionPhase
                int r0 = r0.statusCode
                r5 = 3
                r6 = 2
                if (r0 == 0) goto L2f
                if (r0 == r4) goto L2d
                if (r0 == r6) goto L2b
                if (r0 == r5) goto L2d
                r7 = 6
                if (r0 == r7) goto L2f
                goto L30
            L2b:
                r1 = r4
                goto L30
            L2d:
                r1 = r2
                goto L30
            L2f:
                r1 = r6
            L30:
                r8.mConnectionPhase = r1
                if (r3 < r4) goto L3a
                if (r1 >= r4) goto L3a
                r8.updateConnectionTimeout(r6)
                return
            L3a:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "checkSelectedRouteState: mTriggeredBySmartView="
                r0.<init>(r1)
                boolean r1 = r8.mTriggeredBySmartView
                java.lang.String r7 = "MediaRouterService"
                com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0.m(r7, r0, r1)
                boolean r0 = r8.mTriggeredBySmartView
                if (r0 == 0) goto L4e
                return
            L4e:
                int r0 = r8.mConnectionPhase
                if (r0 == 0) goto L8d
                if (r0 == r4) goto L73
                if (r0 == r6) goto L5a
                r8.updateConnectionTimeout(r4)
                goto L90
            L5a:
                if (r3 == r6) goto L6f
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Connected to route: "
                r0.<init>(r1)
                com.android.server.media.MediaRouterService$UserHandler$RouteRecord r1 = r8.mSelectedRouteRecord
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                android.util.Slog.i(r7, r0)
            L6f:
                r8.updateConnectionTimeout(r2)
                goto L90
            L73:
                if (r3 == r4) goto L88
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "Connecting to route: "
                r0.<init>(r1)
                com.android.server.media.MediaRouterService$UserHandler$RouteRecord r1 = r8.mSelectedRouteRecord
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                android.util.Slog.i(r7, r0)
            L88:
                r0 = 4
                r8.updateConnectionTimeout(r0)
                goto L90
            L8d:
                r8.updateConnectionTimeout(r5)
            L90:
                return
            L91:
                r8.updateConnectionTimeout(r4)
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.media.MediaRouterService.UserHandler.checkSelectedRouteState():void");
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            boolean z;
            RouteRecord routeRecord;
            RouteRecord routeRecord2;
            String str;
            String str2;
            int i2 = message.what;
            if (i2 == 20) {
                this.mTriggeredBySmartView = ((Boolean) message.obj).booleanValue();
                return;
            }
            int i3 = 1;
            int i4 = 0;
            switch (i2) {
                case 1:
                    if (this.mRunning) {
                        return;
                    }
                    this.mRunning = true;
                    RemoteDisplayProviderWatcher remoteDisplayProviderWatcher = this.mWatcher;
                    if (remoteDisplayProviderWatcher.mRunning) {
                        return;
                    }
                    remoteDisplayProviderWatcher.mRunning = true;
                    IntentFilter m =
                            VcnManagementService$$ExternalSyntheticOutline0.m(
                                    "android.intent.action.PACKAGE_ADDED",
                                    "android.intent.action.PACKAGE_REMOVED",
                                    "android.intent.action.PACKAGE_CHANGED",
                                    "android.intent.action.PACKAGE_REPLACED",
                                    "android.intent.action.PACKAGE_RESTARTED");
                    m.addDataScheme("package");
                    remoteDisplayProviderWatcher.mContext.registerReceiverAsUser(
                            remoteDisplayProviderWatcher.mScanPackagesReceiver,
                            new UserHandle(remoteDisplayProviderWatcher.mUserId),
                            m,
                            null,
                            remoteDisplayProviderWatcher.mHandler);
                    remoteDisplayProviderWatcher.mHandler.post(
                            remoteDisplayProviderWatcher.mScanPackagesRunnable);
                    return;
                case 2:
                    if (this.mRunning) {
                        this.mRunning = false;
                        unselectSelectedRoute();
                        RemoteDisplayProviderWatcher remoteDisplayProviderWatcher2 = this.mWatcher;
                        if (remoteDisplayProviderWatcher2.mRunning) {
                            remoteDisplayProviderWatcher2.mRunning = false;
                            remoteDisplayProviderWatcher2.mContext.unregisterReceiver(
                                    remoteDisplayProviderWatcher2.mScanPackagesReceiver);
                            remoteDisplayProviderWatcher2.mHandler.removeCallbacks(
                                    remoteDisplayProviderWatcher2.mScanPackagesRunnable);
                            for (int size = remoteDisplayProviderWatcher2.mProviders.size() - 1;
                                    size >= 0;
                                    size--) {
                                ((RemoteDisplayProviderProxy)
                                                remoteDisplayProviderWatcher2.mProviders.get(size))
                                        .stop();
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 3:
                    synchronized (this.mService.mLock) {
                        try {
                            int size2 = this.mUserRecord.mClientRecords.size();
                            i = 0;
                            z = false;
                            for (int i5 = 0; i5 < size2; i5++) {
                                ClientRecord clientRecord =
                                        (ClientRecord) this.mUserRecord.mClientRecords.get(i5);
                                i |= clientRecord.mRouteTypes;
                                z |= clientRecord.mActiveScan;
                            }
                        } finally {
                        }
                    }
                    if ((i & 4) == 0) {
                        i3 = 0;
                    } else if (z) {
                        i3 = 2;
                    }
                    if (this.mDiscoveryMode != i3) {
                        this.mDiscoveryMode = i3;
                        int size3 = this.mProviderRecords.size();
                        while (i4 < size3) {
                            ((ProviderRecord) this.mProviderRecords.get(i4))
                                    .mProvider.setDiscoveryMode(this.mDiscoveryMode);
                            i4++;
                        }
                        return;
                    }
                    return;
                case 4:
                    String str3 = (String) message.obj;
                    if (str3 != null) {
                        RouteRecord routeRecord3 = this.mSelectedRouteRecord;
                        if (routeRecord3 == null || !str3.equals(routeRecord3.mMutableInfo.id)) {
                            int size4 = this.mProviderRecords.size();
                            int i6 = 0;
                            while (true) {
                                routeRecord = null;
                                if (i6 < size4) {
                                    ProviderRecord providerRecord =
                                            (ProviderRecord) this.mProviderRecords.get(i6);
                                    int size5 = providerRecord.mRoutes.size();
                                    int i7 = 0;
                                    while (true) {
                                        if (i7 < size5) {
                                            RouteRecord routeRecord4 =
                                                    (RouteRecord) providerRecord.mRoutes.get(i7);
                                            if (routeRecord4.mMutableInfo.id.equals(str3)) {
                                                routeRecord = routeRecord4;
                                            } else {
                                                i7++;
                                            }
                                        }
                                    }
                                    if (routeRecord == null) {
                                        i6++;
                                    }
                                }
                            }
                            if (routeRecord != null) {
                                unselectSelectedRoute();
                                Slog.i("MediaRouterService", "Selected route:" + routeRecord);
                                this.mSelectedRouteRecord = routeRecord;
                                checkSelectedRouteState();
                                routeRecord.mProviderRecord.mProvider.setSelectedDisplay(
                                        routeRecord.mDescriptorId);
                                scheduleUpdateClientState();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 5:
                    String str4 = (String) message.obj;
                    if (str4 == null
                            || (routeRecord2 = this.mSelectedRouteRecord) == null
                            || !str4.equals(routeRecord2.mMutableInfo.id)) {
                        return;
                    }
                    unselectSelectedRoute();
                    return;
                case 6:
                    String str5 = (String) message.obj;
                    int i8 = message.arg1;
                    RouteRecord routeRecord5 = this.mSelectedRouteRecord;
                    if (routeRecord5 == null || !str5.equals(routeRecord5.mMutableInfo.id)) {
                        return;
                    }
                    RemoteDisplayProviderProxy remoteDisplayProviderProxy =
                            this.mSelectedRouteRecord.mProviderRecord.mProvider;
                    if (!remoteDisplayProviderProxy.mConnectionReady
                            || (str = remoteDisplayProviderProxy.mSelectedDisplayId) == null) {
                        return;
                    }
                    RemoteDisplayProviderProxy.Connection connection =
                            remoteDisplayProviderProxy.mActiveConnection;
                    connection.getClass();
                    try {
                        connection.mProvider.setVolume(str, i8);
                        return;
                    } catch (RemoteException e) {
                        Slog.e(
                                "RemoteDisplayProvider",
                                "Failed to deliver request to set display volume.",
                                e);
                        return;
                    }
                case 7:
                    String str6 = (String) message.obj;
                    int i9 = message.arg1;
                    RouteRecord routeRecord6 = this.mSelectedRouteRecord;
                    if (routeRecord6 == null || !str6.equals(routeRecord6.mMutableInfo.id)) {
                        return;
                    }
                    RemoteDisplayProviderProxy remoteDisplayProviderProxy2 =
                            this.mSelectedRouteRecord.mProviderRecord.mProvider;
                    if (!remoteDisplayProviderProxy2.mConnectionReady
                            || (str2 = remoteDisplayProviderProxy2.mSelectedDisplayId) == null) {
                        return;
                    }
                    RemoteDisplayProviderProxy.Connection connection2 =
                            remoteDisplayProviderProxy2.mActiveConnection;
                    connection2.getClass();
                    try {
                        connection2.mProvider.adjustVolume(str2, i9);
                        return;
                    } catch (RemoteException e2) {
                        Slog.e(
                                "RemoteDisplayProvider",
                                "Failed to deliver request to adjust display volume.",
                                e2);
                        return;
                    }
                case 8:
                    this.mClientStateUpdateScheduled = false;
                    MediaRouterClientState mediaRouterClientState = new MediaRouterClientState();
                    int size6 = this.mProviderRecords.size();
                    for (int i10 = 0; i10 < size6; i10++) {
                        ProviderRecord providerRecord2 =
                                (ProviderRecord) this.mProviderRecords.get(i10);
                        int size7 = providerRecord2.mRoutes.size();
                        for (int i11 = 0; i11 < size7; i11++) {
                            ArrayList arrayList = mediaRouterClientState.routes;
                            RouteRecord routeRecord7 =
                                    (RouteRecord) providerRecord2.mRoutes.get(i11);
                            if (routeRecord7.mImmutableInfo == null) {
                                routeRecord7.mImmutableInfo =
                                        new MediaRouterClientState.RouteInfo(
                                                routeRecord7.mMutableInfo);
                            }
                            arrayList.add(routeRecord7.mImmutableInfo);
                        }
                    }
                    try {
                        synchronized (this.mService.mLock) {
                            try {
                                UserRecord userRecord = this.mUserRecord;
                                userRecord.mRouterState = mediaRouterClientState;
                                int size8 = userRecord.mClientRecords.size();
                                for (int i12 = 0; i12 < size8; i12++) {
                                    this.mTempClients.add(
                                            ((ClientRecord)
                                                            this.mUserRecord.mClientRecords.get(
                                                                    i12))
                                                    .mClient);
                                }
                            } finally {
                            }
                        }
                        int size9 = this.mTempClients.size();
                        while (i4 < size9) {
                            try {
                                ((IMediaRouterClient) this.mTempClients.get(i4)).onStateChanged();
                            } catch (RemoteException unused) {
                                Slog.w(
                                        "MediaRouterService",
                                        "Failed to call onStateChanged. Client probably died.");
                            }
                            i4++;
                        }
                        return;
                    } finally {
                    }
                case 9:
                    int i13 = this.mConnectionTimeoutReason;
                    if (i13 == 0 || this.mSelectedRouteRecord == null) {
                        Log.wtf("MediaRouterService", "Handled connection timeout for no reason.");
                        return;
                    }
                    if (i13 == 1) {
                        Slog.i(
                                "MediaRouterService",
                                "Selected route no longer available: " + this.mSelectedRouteRecord);
                    } else if (i13 == 2) {
                        Slog.i(
                                "MediaRouterService",
                                "Selected route connection lost: " + this.mSelectedRouteRecord);
                    } else if (i13 == 3) {
                        Slog.i(
                                "MediaRouterService",
                                "Selected route timed out while waiting for connection attempt to"
                                    + " begin after "
                                        + (SystemClock.uptimeMillis()
                                                - this.mConnectionTimeoutStartTime)
                                        + " ms: "
                                        + this.mSelectedRouteRecord);
                    } else if (i13 == 4) {
                        Slog.i(
                                "MediaRouterService",
                                "Selected route timed out while connecting after "
                                        + (SystemClock.uptimeMillis()
                                                - this.mConnectionTimeoutStartTime)
                                        + " ms: "
                                        + this.mSelectedRouteRecord);
                    }
                    this.mConnectionTimeoutReason = 0;
                    unselectSelectedRoute();
                    return;
                case 10:
                    String str7 = (String) message.obj;
                    try {
                        synchronized (this.mService.mLock) {
                            try {
                                ClientGroup clientGroup =
                                        (ClientGroup) this.mUserRecord.mClientGroupMap.get(str7);
                                if (clientGroup != null) {
                                    String str8 = clientGroup.mSelectedRouteId;
                                    int size10 = ((ArrayList) clientGroup.mClientRecords).size();
                                    for (int i14 = 0; i14 < size10; i14++) {
                                        ClientRecord clientRecord2 =
                                                (ClientRecord)
                                                        ((ArrayList) clientGroup.mClientRecords)
                                                                .get(i14);
                                        if (!TextUtils.equals(
                                                str8, clientRecord2.mSelectedRouteId)) {
                                            this.mTempClients.add(clientRecord2.mClient);
                                        }
                                    }
                                    int size11 = this.mTempClients.size();
                                    while (i4 < size11) {
                                        try {
                                            ((IMediaRouterClient) this.mTempClients.get(i4))
                                                    .onGroupRouteSelected(str8);
                                        } catch (RemoteException unused2) {
                                            Slog.w(
                                                    "MediaRouterService",
                                                    "Failed to call onSelectedRouteChanged. Client"
                                                        + " probably died.");
                                        }
                                        i4++;
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    } finally {
                    }
                default:
                    return;
            }
        }

        public final void scheduleUpdateClientState() {
            if (this.mClientStateUpdateScheduled) {
                return;
            }
            this.mClientStateUpdateScheduled = true;
            sendEmptyMessage(8);
        }

        public final void unselectSelectedRoute() {
            if (this.mSelectedRouteRecord != null) {
                Slog.i("MediaRouterService", "Unselected route:" + this.mSelectedRouteRecord);
                this.mSelectedRouteRecord.mProviderRecord.mProvider.setSelectedDisplay(null);
                this.mSelectedRouteRecord = null;
                checkSelectedRouteState();
                scheduleUpdateClientState();
            }
        }

        public final void updateConnectionTimeout(int i) {
            int i2 = this.mConnectionTimeoutReason;
            if (i != i2) {
                if (i2 != 0) {
                    removeMessages(9);
                }
                this.mConnectionTimeoutReason = i;
                this.mConnectionTimeoutStartTime = SystemClock.uptimeMillis();
                if (i == 1 || i == 2) {
                    sendEmptyMessage(9);
                } else if (i == 3) {
                    sendEmptyMessageDelayed(9, 5000L);
                } else {
                    if (i != 4) {
                        return;
                    }
                    sendEmptyMessageDelayed(9, 60000L);
                }
            }
        }
    }

    public final class UserRecord {
        public final UserHandler mHandler;
        public MediaRouterClientState mRouterState;
        public final int mUserId;
        public final /* synthetic */ MediaRouterService this$0;
        public final ArrayList mClientRecords = new ArrayList();
        public final ArrayMap mClientGroupMap = new ArrayMap();

        public UserRecord(MediaRouterService mediaRouterService, int i) {
            this.mUserId = i;
            this.mHandler = new UserHandler(mediaRouterService, this, mediaRouterService.mLooper);
        }

        public final void dump(final PrintWriter printWriter) {
            printWriter.println("" + this);
            int size = this.mClientRecords.size();
            final String str = "  ";
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    ClientRecord clientRecord = (ClientRecord) this.mClientRecords.get(i);
                    clientRecord.getClass();
                    printWriter.println("  " + clientRecord);
                    StringBuilder m =
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("    mTrusted="),
                                    clientRecord.mTrusted,
                                    printWriter,
                                    "    mRouteTypes=0x");
                    m.append(Integer.toHexString(clientRecord.mRouteTypes));
                    printWriter.println(m.toString());
                    StringBuilder m2 =
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("    mActiveScan="),
                                    clientRecord.mActiveScan,
                                    printWriter,
                                    "    mSelectedRouteId=");
                    m2.append(clientRecord.mSelectedRouteId);
                    printWriter.println(m2.toString());
                }
            } else {
                printWriter.println("  <no clients>");
            }
            StringBuilder m$1 =
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                            printWriter, "  State", "  mRouterState=");
            m$1.append(this.mRouterState);
            printWriter.println(m$1.toString());
            if (this.mHandler.runWithScissors(
                    new Runnable() { // from class:
                        // com.android.server.media.MediaRouterService.UserRecord.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            UserHandler userHandler = UserRecord.this.mHandler;
                            PrintWriter printWriter2 = printWriter;
                            String str2 = str;
                            userHandler.getClass();
                            printWriter2.println(str2 + "Handler");
                            String str3 = str2 + "  ";
                            StringBuilder m3 =
                                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                    .m(
                                                            Preconditions$$ExternalSyntheticOutline0
                                                                    .m(str3, "mRunning="),
                                                            userHandler.mRunning,
                                                            printWriter2,
                                                            str3,
                                                            "mDiscoveryMode="),
                                            userHandler.mDiscoveryMode,
                                            printWriter2,
                                            str3,
                                            "mSelectedRouteRecord=");
                            m3.append(userHandler.mSelectedRouteRecord);
                            printWriter2.println(m3.toString());
                            StringBuilder sb = new StringBuilder();
                            sb.append(str3);
                            sb.append("mConnectionPhase=");
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                    .m(
                                                            sb,
                                                            userHandler.mConnectionPhase,
                                                            printWriter2,
                                                            str3,
                                                            "mConnectionTimeoutReason="),
                                            userHandler.mConnectionTimeoutReason,
                                            printWriter2,
                                            str3,
                                            "mConnectionTimeoutStartTime="),
                                    userHandler.mConnectionTimeoutReason != 0
                                            ? TimeUtils.formatUptime(
                                                    userHandler.mConnectionTimeoutStartTime)
                                            : "<n/a>",
                                    printWriter2);
                            RemoteDisplayProviderWatcher remoteDisplayProviderWatcher =
                                    userHandler.mWatcher;
                            remoteDisplayProviderWatcher.getClass();
                            printWriter2.println(str2 + "Watcher");
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str2);
                            sb2.append("  mUserId=");
                            StringBuilder m4 =
                                    MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(
                                            MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                    .m(
                                                            sb2,
                                                            remoteDisplayProviderWatcher.mUserId,
                                                            printWriter2,
                                                            str2,
                                                            "  mRunning="),
                                            remoteDisplayProviderWatcher.mRunning,
                                            printWriter2,
                                            str2,
                                            "  mProviders.size()=");
                            m4.append(remoteDisplayProviderWatcher.mProviders.size());
                            printWriter2.println(m4.toString());
                            int size2 = userHandler.mProviderRecords.size();
                            if (size2 == 0) {
                                printWriter2.println(str3 + "<no providers>");
                                return;
                            }
                            for (int i2 = 0; i2 < size2; i2++) {
                                UserHandler.ProviderRecord providerRecord =
                                        (UserHandler.ProviderRecord)
                                                userHandler.mProviderRecords.get(i2);
                                providerRecord.getClass();
                                printWriter2.println(str2 + providerRecord);
                                String str4 = str2 + "  ";
                                RemoteDisplayProviderProxy remoteDisplayProviderProxy =
                                        providerRecord.mProvider;
                                remoteDisplayProviderProxy.getClass();
                                printWriter2.println(str4 + "Proxy");
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(str4);
                                sb3.append("  mUserId=");
                                StringBuilder m5 =
                                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                .m(
                                                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                                .m(
                                                                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        sb3,
                                                                                        remoteDisplayProviderProxy
                                                                                                .mUserId,
                                                                                        printWriter2,
                                                                                        str4,
                                                                                        "  mRunning="),
                                                                        remoteDisplayProviderProxy
                                                                                .mRunning,
                                                                        printWriter2,
                                                                        str4,
                                                                        "  mBound="),
                                                        remoteDisplayProviderProxy.mBound,
                                                        printWriter2,
                                                        str4,
                                                        "  mActiveConnection=");
                                m5.append(remoteDisplayProviderProxy.mActiveConnection);
                                printWriter2.println(m5.toString());
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(str4);
                                sb4.append("  mConnectionReady=");
                                StringBuilder m6 =
                                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                .m(
                                                        MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0
                                                                .m(
                                                                        sb4,
                                                                        remoteDisplayProviderProxy
                                                                                .mConnectionReady,
                                                                        printWriter2,
                                                                        str4,
                                                                        "  mDiscoveryMode="),
                                                        remoteDisplayProviderProxy.mDiscoveryMode,
                                                        printWriter2,
                                                        str4,
                                                        "  mSelectedDisplayId=");
                                m6.append(remoteDisplayProviderProxy.mSelectedDisplayId);
                                printWriter2.println(m6.toString());
                                printWriter2.println(
                                        str4
                                                + "  mDisplayState="
                                                + remoteDisplayProviderProxy.mDisplayState);
                                int size3 = providerRecord.mRoutes.size();
                                if (size3 != 0) {
                                    for (int i3 = 0; i3 < size3; i3++) {
                                        UserHandler.RouteRecord routeRecord =
                                                (UserHandler.RouteRecord)
                                                        providerRecord.mRoutes.get(i3);
                                        routeRecord.getClass();
                                        printWriter2.println(str4 + routeRecord);
                                        String str5 = str4 + "  ";
                                        StringBuilder m7 =
                                                Preconditions$$ExternalSyntheticOutline0.m(
                                                        str5, "mMutableInfo=");
                                        m7.append(routeRecord.mMutableInfo);
                                        printWriter2.println(m7.toString());
                                        printWriter2.println(
                                                str5
                                                        + "mDescriptorId="
                                                        + routeRecord.mDescriptorId);
                                        printWriter2.println(
                                                str5 + "mDescriptor=" + routeRecord.mDescriptor);
                                    }
                                } else {
                                    printWriter2.println(str4 + "<no routes>");
                                }
                            }
                        }
                    },
                    1000L)) {
                return;
            }
            printWriter.println("  <could not dump handler state>");
        }

        public final void removeFromGroup(String str, ClientRecord clientRecord) {
            ClientGroup clientGroup = (ClientGroup) this.mClientGroupMap.get(str);
            if (clientGroup != null) {
                ((ArrayList) clientGroup.mClientRecords).remove(clientRecord);
                if (((ArrayList) clientGroup.mClientRecords).size() == 0) {
                    this.mClientGroupMap.remove(str);
                }
            }
        }

        public final String toString() {
            return "User " + this.mUserId;
        }
    }

    public MediaRouterService(Context context) {
        if (Flags.enableMr2ServiceNonMainBgThread()) {
            HandlerThread handlerThread = new HandlerThread("MediaRouterServiceThread");
            handlerThread.start();
            this.mLooper = handlerThread.getLooper();
        } else {
            this.mLooper = Looper.myLooper();
        }
        Handler handler = new Handler(this.mLooper);
        this.mHandler = handler;
        this.mService2 = new MediaRouter2ServiceImpl(context, this.mLooper);
        this.mContext = context;
        Watchdog.getInstance().addMonitor(this);
        Resources resources = context.getResources();
        this.mDefaultAudioRouteId = resources.getString(R.string.global_action_logout);
        this.mBluetoothA2dpRouteId =
                resources.getString(R.string.config_defaultWearableSensingService);
        this.mUserManagerInternal =
                (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        IAudioService asInterface =
                IAudioService.Stub.asInterface(ServiceManager.getService("audio"));
        this.mAudioService = asInterface;
        AudioPlayerStateMonitor audioPlayerStateMonitor =
                AudioPlayerStateMonitor.getInstance(context);
        this.mAudioPlayerStateMonitor = audioPlayerStateMonitor;
        audioPlayerStateMonitor.registerListener(
                new AudioPlayerActiveStateChangedListenerImpl(), handler);
        try {
            asInterface.startWatchingRoutes(new AudioRoutesObserverImpl());
        } catch (RemoteException unused) {
            Slog.w("MediaRouterService", "RemoteException in the audio service.");
        }
        context.registerReceiverAsUser(
                this.mReceiver,
                UserHandle.ALL,
                new IntentFilter("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED"),
                null,
                null);
    }

    public final void deselectRouteWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.deselectRouteWithManagerLocked(
                        iMediaRouter2Manager, i, str, mediaRoute2Info);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void deselectRouteWithRouter2(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.deselectRouteWithRouter2Locked(
                        iMediaRouter2, str, mediaRoute2Info);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void disposeUserIfNeededLocked(UserRecord userRecord) {
        UserManagerInternal userManagerInternal = this.mUserManagerInternal;
        int i = userRecord.mUserId;
        if (userManagerInternal.getProfileParentId(i) != this.mCurrentActiveUserId
                && userRecord.mClientRecords.isEmpty()) {
            Slog.d("MediaRouterService", userRecord + ": Disposed");
            this.mUserRecords.remove(i);
        }
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        if (DumpUtils.checkDumpPermission(this.mContext, "MediaRouterService", printWriter)) {
            printWriter.println("MEDIA ROUTER SERVICE (dumpsys media_router)");
            printWriter.println();
            printWriter.println("Global state");
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  mCurrentUserId="), this.mCurrentActiveUserId, printWriter);
            synchronized (this.mLock) {
                try {
                    int size = this.mUserRecords.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        UserRecord userRecord = (UserRecord) this.mUserRecords.valueAt(i2);
                        printWriter.println();
                        userRecord.dump(printWriter);
                    }
                } finally {
                }
            }
            printWriter.println();
            MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
            mediaRouter2ServiceImpl.getClass();
            printWriter.println("MediaRouter2ServiceImpl");
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    printWriter.println(
                            "  mNextRouterOrManagerId="
                                    + mediaRouter2ServiceImpl.mNextRouterOrManagerId.get());
                    printWriter.println(
                            "  mCurrentActiveUserId="
                                    + mediaRouter2ServiceImpl.mCurrentActiveUserId);
                    StringBuilder sb = new StringBuilder();
                    sb.append("  ");
                    sb.append("UserRecords:");
                    printWriter.println(sb.toString());
                    if (mediaRouter2ServiceImpl.mUserRecords.size() > 0) {
                        for (i = 0; i < mediaRouter2ServiceImpl.mUserRecords.size(); i++) {
                            ((MediaRouter2ServiceImpl.UserRecord)
                                            mediaRouter2ServiceImpl.mUserRecords.valueAt(i))
                                    .dump(printWriter, "    ");
                        }
                    } else {
                        printWriter.println("    <no user records>");
                    }
                } finally {
                }
            }
        }
    }

    public final List getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager) {
        List remoteSessionsLocked;
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                remoteSessionsLocked =
                        mediaRouter2ServiceImpl.getRemoteSessionsLocked(iMediaRouter2Manager);
            }
            return remoteSessionsLocked;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) {
        MediaRouterClientState mediaRouterClientState;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                ClientRecord clientRecord =
                        (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
                mediaRouterClientState = null;
                if (clientRecord != null && clientRecord.mTrusted) {
                    mediaRouterClientState = clientRecord.mUserRecord.mRouterState;
                }
            }
            return mediaRouterClientState;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final List getSystemRoutes(String str, boolean z) {
        Collection collection;
        Collection collection2;
        if (!validatePackageName$1(Binder.getCallingUid(), str)) {
            throw new SecurityException("callerPackageName does not match calling uid.");
        }
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
        boolean z2 = false;
        if (z
                ? mediaRouter2ServiceImpl.checkMediaContentControlPermission(callingUid, callingPid)
                        || mediaRouter2ServiceImpl.checkMediaRoutingControlPermission(
                                callingUid, callingPid, str)
                : mediaRouter2ServiceImpl.mContext.checkPermission(
                                        "android.permission.MODIFY_AUDIO_ROUTING",
                                        callingPid,
                                        callingUid)
                                == 0
                        || mediaRouter2ServiceImpl.checkCallerHasBluetoothPermissions(
                                callingPid, callingUid)) {
            z2 = true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                try {
                    MediaRouter2ServiceImpl.UserRecord orCreateUserRecordLocked =
                            mediaRouter2ServiceImpl.getOrCreateUserRecordLocked(identifier);
                    if (z2) {
                        MediaRoute2ProviderInfo mediaRoute2ProviderInfo =
                                orCreateUserRecordLocked.mHandler.mSystemProvider.mProviderInfo;
                        if (mediaRoute2ProviderInfo != null) {
                            collection2 = mediaRoute2ProviderInfo.getRoutes();
                        } else {
                            Collection emptyList = Collections.emptyList();
                            emptyList.add(
                                    orCreateUserRecordLocked
                                            .mHandler
                                            .mSystemProvider
                                            .mDefaultRoute);
                            collection = emptyList;
                        }
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(
                                orCreateUserRecordLocked.mHandler.mSystemProvider.mDefaultRoute);
                        collection = arrayList;
                    }
                    collection2 = collection;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return new ArrayList(collection2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final RoutingSessionInfo getSystemSessionInfo() {
        return this.mService2.getSystemSessionInfo(null, null, false);
    }

    public final RoutingSessionInfo getSystemSessionInfoForPackage(String str, String str2) {
        boolean z;
        int callingUid = Binder.getCallingUid();
        int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
        if (!validatePackageName$1(callingUid, str)) {
            throw new SecurityException("callerPackageName does not match calling uid.");
        }
        synchronized (this.mLock) {
            try {
                UserRecord userRecord = (UserRecord) this.mUserRecords.get(identifier);
                Iterator it =
                        (userRecord != null ? userRecord.mClientRecords : Collections.emptyList())
                                .iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    ClientRecord clientRecord = (ClientRecord) it.next();
                    if (TextUtils.equals(clientRecord.mPackageName, str2)
                            && this.mDefaultAudioRouteId.equals(clientRecord.mSelectedRouteId)) {
                        z = true;
                        break;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mService2.getSystemSessionInfo(str, str2, z);
    }

    public final boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) {
        ClientRecord clientRecord;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                clientRecord =
                        (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
            }
            if (clientRecord != null) {
                return this.mAudioPlayerStateMonitor.isPlaybackActive(clientRecord.mUid);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        synchronized (this.mLock) {
        }
    }

    public final void registerClientAsUser(
            IMediaRouterClient iMediaRouterClient, String str, int i) {
        int callingUid = Binder.getCallingUid();
        if (!validatePackageName$1(callingUid, str)) {
            throw new SecurityException("packageName must match the calling uid");
        }
        int callingPid = Binder.getCallingPid();
        int handleIncomingUser =
                ActivityManager.handleIncomingUser(
                        callingPid, callingUid, i, false, true, "registerClientAsUser", str);
        boolean z =
                this.mContext.checkCallingOrSelfPermission(
                                "android.permission.CONFIGURE_WIFI_DISPLAY")
                        == 0;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerClientLocked(
                        iMediaRouterClient, callingUid, callingPid, str, handleIncomingUser, z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.CONFIGURE_WIFI_DISPLAY")
                != 0) {
            Log.w(
                    "MediaRouterService",
                    "Ignoring client group request because the client doesn't have the"
                        + " CONFIGURE_WIFI_DISPLAY permission.");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                registerClientGroupIdLocked(iMediaRouterClient, str);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerClientGroupIdLocked(
            IMediaRouterClient iMediaRouterClient, String str) {
        ClientRecord clientRecord =
                (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord == null) {
            Log.w(
                    "MediaRouterService",
                    "Ignoring group id register request of a unregistered client.");
            return;
        }
        if (TextUtils.equals(clientRecord.mGroupId, str)) {
            return;
        }
        UserRecord userRecord = clientRecord.mUserRecord;
        String str2 = clientRecord.mGroupId;
        if (str2 != null) {
            userRecord.removeFromGroup(str2, clientRecord);
        }
        clientRecord.mGroupId = str;
        if (str != null) {
            ClientGroup clientGroup = (ClientGroup) userRecord.mClientGroupMap.get(str);
            if (clientGroup == null) {
                clientGroup = new ClientGroup();
                userRecord.mClientGroupMap.put(str, clientGroup);
            }
            ((ArrayList) clientGroup.mClientRecords).add(clientRecord);
            userRecord.mHandler.obtainMessage(10, str).sendToTarget();
        }
    }

    public final void registerClientLocked(
            IMediaRouterClient iMediaRouterClient, int i, int i2, String str, int i3, boolean z) {
        UserRecord userRecord;
        boolean z2;
        IBinder asBinder = iMediaRouterClient.asBinder();
        if (((ClientRecord) this.mAllClientRecords.get(asBinder)) == null) {
            UserRecord userRecord2 = (UserRecord) this.mUserRecords.get(i3);
            if (userRecord2 == null) {
                userRecord = new UserRecord(this, i3);
                z2 = true;
            } else {
                userRecord = userRecord2;
                z2 = false;
            }
            ClientRecord clientRecord =
                    new ClientRecord(userRecord, iMediaRouterClient, i, i2, str, z);
            try {
                asBinder.linkToDeath(clientRecord, 0);
                if (z2) {
                    this.mUserRecords.put(i3, userRecord);
                    Slog.d("MediaRouterService", userRecord + ": Initialized");
                    if (this.mUserManagerInternal.getProfileParentId(userRecord.mUserId)
                            == this.mCurrentActiveUserId) {
                        userRecord.mHandler.sendEmptyMessage(1);
                    }
                }
                userRecord.mClientRecords.add(clientRecord);
                this.mAllClientRecords.put(asBinder, clientRecord);
                Slog.d("MediaRouterService", clientRecord + ": Registered");
            } catch (RemoteException e) {
                throw new RuntimeException("Media router client died prematurely.", e);
            }
        }
    }

    public final void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str) {
        if (!validatePackageName$1(Binder.getCallingUid(), str)) {
            throw new SecurityException("callerPackageName must match the calling uid");
        }
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("callerPackageName must not be empty");
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        UserHandle callingUserHandle = Binder.getCallingUserHandle();
        if (!mediaRouter2ServiceImpl.checkMediaContentControlPermission(callingUid, callingPid)
                && !mediaRouter2ServiceImpl.checkMediaRoutingControlPermission(
                        callingUid, callingPid, str)) {
            throw new SecurityException(
                    "Must hold MEDIA_CONTENT_CONTROL or MEDIA_ROUTING_CONTROL permissions.");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.registerManagerLocked(
                        iMediaRouter2Manager, callingUid, callingPid, str, null, callingUserHandle);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerProxyRouter(
            IMediaRouter2Manager iMediaRouter2Manager,
            String str,
            String str2,
            UserHandle userHandle) {
        if (!validatePackageName$1(Binder.getCallingUid(), str)) {
            throw new SecurityException("callerPackageName must match the calling uid");
        }
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        Objects.requireNonNull(userHandle, "targetUser must not be null");
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("targetPackageName must not be empty");
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (!mediaRouter2ServiceImpl.checkMediaContentControlPermission(callingUid, callingPid)
                    && !mediaRouter2ServiceImpl.checkMediaRoutingControlPermission(
                            callingUid, callingPid, str)) {
                throw new SecurityException(
                        "Must hold MEDIA_CONTENT_CONTROL or MEDIA_ROUTING_CONTROL permissions.");
            }
            if (userHandle.getIdentifier() != UserHandle.getUserId(callingUid)) {
                mediaRouter2ServiceImpl.mContext.enforcePermission(
                        "android.permission.INTERACT_ACROSS_USERS_FULL",
                        callingPid,
                        callingUid,
                        "Must hold INTERACT_ACROSS_USERS_FULL to control an app in a different"
                            + " userId.");
            }
            try {
                mediaRouter2ServiceImpl
                        .mContext
                        .getPackageManager()
                        .getPackageInfoAsUser(
                                str2,
                                PackageManager.PackageInfoFlags.of(0L),
                                userHandle.getIdentifier());
                synchronized (mediaRouter2ServiceImpl.mLock) {
                    mediaRouter2ServiceImpl.registerManagerLocked(
                            iMediaRouter2Manager, callingUid, callingPid, str, str2, userHandle);
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (PackageManager.NameNotFoundException unused) {
                throw new IllegalArgumentException("targetPackageName does not exist: " + str2);
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void registerRouter2(IMediaRouter2 iMediaRouter2, String str) {
        if (!validatePackageName$1(Binder.getCallingUid(), str)) {
            throw new SecurityException("packageName must match the calling uid");
        }
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("packageName must not be empty");
        }
        int callingUid = Binder.getCallingUid();
        int callingPid = Binder.getCallingPid();
        int identifier = UserHandle.getUserHandleForUid(callingUid).getIdentifier();
        boolean z =
                mediaRouter2ServiceImpl.mContext.checkCallingOrSelfPermission(
                                "android.permission.CONFIGURE_WIFI_DISPLAY")
                        == 0;
        boolean z2 =
                mediaRouter2ServiceImpl.mContext.checkPermission(
                                "android.permission.MODIFY_AUDIO_ROUTING", callingPid, callingUid)
                        == 0;
        boolean checkMediaContentControlPermission =
                mediaRouter2ServiceImpl.checkMediaContentControlPermission(callingUid, callingPid);
        boolean checkMediaRoutingControlPermission =
                mediaRouter2ServiceImpl.checkMediaRoutingControlPermission(
                        callingUid, callingPid, str);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.registerRouter2Locked(
                        iMediaRouter2,
                        callingUid,
                        callingPid,
                        str,
                        identifier,
                        z,
                        z2,
                        checkMediaContentControlPermission,
                        checkMediaRoutingControlPermission);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void releaseSessionWithManager(
            IMediaRouter2Manager iMediaRouter2Manager, int i, String str) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.releaseSessionWithManagerLocked(
                        iMediaRouter2Manager, i, str);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.releaseSessionWithRouter2Locked(iMediaRouter2, str);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestCreateSessionWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            RoutingSessionInfo routingSessionInfo,
            MediaRoute2Info mediaRoute2Info,
            UserHandle userHandle,
            String str) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        Objects.requireNonNull(routingSessionInfo, "oldSession must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        Objects.requireNonNull(userHandle);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.requestCreateSessionWithManagerLocked(
                        iMediaRouter2Manager,
                        i,
                        routingSessionInfo,
                        mediaRoute2Info,
                        userHandle,
                        str);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestCreateSessionWithRouter2(
            IMediaRouter2 iMediaRouter2,
            int i,
            long j,
            RoutingSessionInfo routingSessionInfo,
            MediaRoute2Info mediaRoute2Info,
            Bundle bundle) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        Objects.requireNonNull(routingSessionInfo, "oldSession must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.requestCreateSessionWithRouter2Locked(
                        iMediaRouter2, i, j, routingSessionInfo, mediaRoute2Info, bundle);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestSetVolume(IMediaRouterClient iMediaRouterClient, String str, int i) {
        Objects.requireNonNull(str, "routeId must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                ClientRecord clientRecord =
                        (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
                if (clientRecord != null) {
                    clientRecord.mUserRecord.mHandler.obtainMessage(6, i, 0, str).sendToTarget();
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void requestUpdateVolume(
            IMediaRouterClient iMediaRouterClient, String str, int i) {
        Objects.requireNonNull(str, "routeId must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                ClientRecord clientRecord =
                        (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
                if (clientRecord != null) {
                    clientRecord.mUserRecord.mHandler.obtainMessage(7, i, 0, str).sendToTarget();
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void restoreBluetoothA2dp() {
        boolean z;
        BluetoothDevice bluetoothDevice;
        try {
            synchronized (this.mLock) {
                try {
                    if (!this.mGlobalBluetoothA2dpOn && !this.mForceBluetoothA2dpOn) {
                        z = false;
                        bluetoothDevice = this.mActiveBluetoothDevice;
                    }
                    z = true;
                    bluetoothDevice = this.mActiveBluetoothDevice;
                } finally {
                }
            }
            if (bluetoothDevice == null) {
                synchronized (this.mLock) {
                    this.mForceBluetoothA2dpOn = false;
                }
            } else {
                Slog.d("MediaRouterService", "restoreBluetoothA2dp(" + z + ")");
                this.mAudioService.setBluetoothA2dpOn(z);
            }
        } catch (RemoteException unused) {
            Slog.w("MediaRouterService", "RemoteException while calling setBluetoothA2dpOn.");
        }
    }

    public final void selectRouteWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.selectRouteWithManagerLocked(
                        iMediaRouter2Manager, i, str, mediaRoute2Info);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void selectRouteWithRouter2(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.selectRouteWithRouter2Locked(
                        iMediaRouter2, str, mediaRoute2Info);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setBluetoothA2dpOn(IMediaRouterClient iMediaRouterClient, boolean z) {
        if (iMediaRouterClient == null) {
            throw new IllegalArgumentException("client must not be null");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mAudioService.setBluetoothA2dpOn(z);
            } catch (RemoteException unused) {
                Slog.w(
                        "MediaRouterService",
                        "RemoteException while calling setBluetoothA2dpOn. on=" + z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int i, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setDiscoveryRequestLocked(iMediaRouterClient, i, z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setDiscoveryRequestLocked(
            IMediaRouterClient iMediaRouterClient, int i, boolean z) {
        ClientRecord clientRecord =
                (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            if (!clientRecord.mTrusted) {
                i &= -5;
            }
            if (clientRecord.mRouteTypes == i && clientRecord.mActiveScan == z) {
                return;
            }
            Slog.d(
                    "MediaRouterService",
                    clientRecord
                            + ": Set discovery request, routeTypes=0x"
                            + Integer.toHexString(i)
                            + ", activeScan="
                            + z);
            clientRecord.mRouteTypes = i;
            clientRecord.mActiveScan = z;
            clientRecord.mUserRecord.mHandler.sendEmptyMessage(3);
        }
    }

    public final void setDiscoveryRequestWithRouter2(
            IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        Objects.requireNonNull(routeDiscoveryPreference, "preference must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                MediaRouter2ServiceImpl.RouterRecord routerRecord =
                        (MediaRouter2ServiceImpl.RouterRecord)
                                mediaRouter2ServiceImpl.mAllRouterRecords.get(
                                        iMediaRouter2.asBinder());
                if (routerRecord == null) {
                    Slog.w(
                            "MR2ServiceImpl",
                            "Ignoring updating discoveryRequest of null routerRecord.");
                } else {
                    MediaRouter2ServiceImpl.setDiscoveryRequestWithRouter2Locked(
                            routerRecord, routeDiscoveryPreference);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRouteListingPreference(
            IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        ComponentName linkedItemComponentName =
                routeListingPreference != null
                        ? routeListingPreference.getLinkedItemComponentName()
                        : null;
        if (linkedItemComponentName != null) {
            MediaServerUtils.enforcePackageName(
                    mediaRouter2ServiceImpl.mContext,
                    linkedItemComponentName.getPackageName(),
                    Binder.getCallingUid());
            Context context = mediaRouter2ServiceImpl.mContext;
            UserHandle callingUserHandle = Binder.getCallingUserHandle();
            new Intent("android.media.action.TRANSFER_MEDIA").setComponent(linkedItemComponentName);
            if (!(!context.getPackageManager()
                    .queryIntentActivitiesAsUser(r4, 0, callingUserHandle)
                    .isEmpty())) {
                throw new IllegalArgumentException(
                        "Unable to resolve "
                                + linkedItemComponentName
                                + " to a valid activity for android.media.action.TRANSFER_MEDIA");
            }
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                MediaRouter2ServiceImpl.RouterRecord routerRecord =
                        (MediaRouter2ServiceImpl.RouterRecord)
                                mediaRouter2ServiceImpl.mAllRouterRecords.get(
                                        iMediaRouter2.asBinder());
                if (routerRecord == null) {
                    Slog.w(
                            "MR2ServiceImpl",
                            "Ignoring updating route listing of null routerRecord.");
                } else {
                    MediaRouter2ServiceImpl.setRouteListingPreferenceLocked(
                            routerRecord, routeListingPreference);
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRouteVolumeWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            MediaRoute2Info mediaRoute2Info,
            int i2) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.setRouteVolumeWithManagerLocked(
                        iMediaRouter2Manager, i, mediaRoute2Info, i2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setRouteVolumeWithRouter2(
            IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.setRouteVolumeWithRouter2Locked(
                        iMediaRouter2, mediaRoute2Info, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSelectedRoute(
            IMediaRouterClient iMediaRouterClient, String str, boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                setSelectedRouteLocked(iMediaRouterClient, str, z);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSelectedRouteLocked(
            IMediaRouterClient iMediaRouterClient, String str, boolean z) {
        ClientGroup clientGroup;
        ClientRecord clientRecord =
                (ClientRecord) this.mAllClientRecords.get(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            String str2 =
                    (this.mDefaultAudioRouteId.equals(clientRecord.mSelectedRouteId)
                                    || this.mBluetoothA2dpRouteId.equals(
                                            clientRecord.mSelectedRouteId))
                            ? null
                            : clientRecord.mSelectedRouteId;
            clientRecord.mSelectedRouteId = str;
            if (this.mDefaultAudioRouteId.equals(str) || this.mBluetoothA2dpRouteId.equals(str)) {
                str = null;
            }
            if (Objects.equals(str, str2)) {
                return;
            }
            Slog.d(
                    "MediaRouterService",
                    clientRecord
                            + ": Set selected route, routeId="
                            + str
                            + ", oldRouteId="
                            + str2
                            + ", explicit="
                            + z);
            if (z && clientRecord.mTrusted) {
                if (KnoxCustomManagerService.SMARTMIRRORING_PACKAGE_NAME.equals(
                        clientRecord.mPackageName)) {
                    clientRecord
                            .mUserRecord
                            .mHandler
                            .obtainMessage(20, Boolean.TRUE)
                            .sendToTarget();
                } else {
                    clientRecord
                            .mUserRecord
                            .mHandler
                            .obtainMessage(20, Boolean.FALSE)
                            .sendToTarget();
                }
                if (str2 != null) {
                    clientRecord.mUserRecord.mHandler.obtainMessage(5, str2).sendToTarget();
                }
                if (str != null) {
                    clientRecord.mUserRecord.mHandler.obtainMessage(4, str).sendToTarget();
                }
                String str3 = clientRecord.mGroupId;
                if (str3 == null
                        || (clientGroup =
                                        (ClientGroup)
                                                clientRecord.mUserRecord.mClientGroupMap.get(str3))
                                == null) {
                    return;
                }
                clientGroup.mSelectedRouteId = str;
                clientRecord
                        .mUserRecord
                        .mHandler
                        .obtainMessage(10, clientRecord.mGroupId)
                        .sendToTarget();
            }
        }
    }

    public final void setSessionVolumeWithManager(
            IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.setSessionVolumeWithManagerLocked(
                        iMediaRouter2Manager, i, str, i2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        Objects.requireNonNull(str, "uniqueSessionId must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.setSessionVolumeWithRouter2Locked(iMediaRouter2, str, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean showMediaOutputSwitcherWithProxyRouter(
            IMediaRouter2Manager iMediaRouter2Manager) {
        boolean showOutputSwitcher;
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "Proxy router must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                MediaRouter2ServiceImpl.ManagerRecord managerRecord =
                        (MediaRouter2ServiceImpl.ManagerRecord)
                                mediaRouter2ServiceImpl.mAllManagerRecords.get(
                                        iMediaRouter2Manager.asBinder());
                String str = managerRecord.mTargetPackageName;
                if (str == null) {
                    throw new UnsupportedOperationException(
                            "Only proxy routers can show the Output Switcher.");
                }
                showOutputSwitcher =
                        mediaRouter2ServiceImpl.showOutputSwitcher(
                                str, UserHandle.of(managerRecord.mUserRecord.mUserId));
            }
            return showOutputSwitcher;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean showMediaOutputSwitcherWithRouter2(String str) {
        if (!validatePackageName$1(Binder.getCallingUid(), str)) {
            throw new SecurityException("packageName must match the calling identity");
        }
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        UserHandle callingUserHandle = Binder.getCallingUserHandle();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return mediaRouter2ServiceImpl.showOutputSwitcher(str, callingUserHandle);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void transferToRouteWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info,
            UserHandle userHandle,
            String str2) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        Objects.requireNonNull(userHandle);
        Objects.requireNonNull(str2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.transferToRouteWithManagerLocked(
                        iMediaRouter2Manager, i, str, mediaRoute2Info, userHandle, str2);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void transferToRouteWithRouter2(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        Objects.requireNonNull(mediaRoute2Info, "route must not be null");
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("uniqueSessionId must not be empty");
        }
        UserHandle callingUserHandle = Binder.getCallingUserHandle();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.transferToRouteWithRouter2Locked(
                        iMediaRouter2, callingUserHandle, str, mediaRoute2Info);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterClient(IMediaRouterClient iMediaRouterClient) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                unregisterClientLocked(iMediaRouterClient, false);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterClientLocked(IMediaRouterClient iMediaRouterClient, boolean z) {
        ClientRecord clientRecord =
                (ClientRecord) this.mAllClientRecords.remove(iMediaRouterClient.asBinder());
        if (clientRecord != null) {
            UserRecord userRecord = clientRecord.mUserRecord;
            userRecord.mClientRecords.remove(clientRecord);
            String str = clientRecord.mGroupId;
            if (str != null) {
                userRecord.removeFromGroup(str, clientRecord);
                clientRecord.mGroupId = null;
            }
            if (z) {
                Slog.d("MediaRouterService", clientRecord + ": Died!");
            } else {
                Slog.d("MediaRouterService", clientRecord + ": Unregistered");
            }
            if (clientRecord.mRouteTypes != 0 || clientRecord.mActiveScan) {
                clientRecord.mUserRecord.mHandler.sendEmptyMessage(3);
            }
            clientRecord.mClient.asBinder().unlinkToDeath(clientRecord, 0);
            disposeUserIfNeededLocked(userRecord);
        }
    }

    public final void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.unregisterManagerLocked(iMediaRouter2Manager, false);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterRouter2(IMediaRouter2 iMediaRouter2) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.unregisterRouter2Locked(iMediaRouter2, false);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateRunningUserAndProfiles(int i) {
        int i2;
        synchronized (this.mLock) {
            try {
                if (this.mCurrentActiveUserId != i) {
                    this.mCurrentActiveUserId = i;
                    SparseArray clone = this.mUserRecords.clone();
                    for (int i3 = 0; i3 < clone.size(); i3++) {
                        int keyAt = clone.keyAt(i3);
                        UserRecord userRecord = (UserRecord) clone.valueAt(i3);
                        if (this.mUserManagerInternal.getProfileParentId(keyAt)
                                == this.mCurrentActiveUserId) {
                            userRecord.mHandler.sendEmptyMessage(1);
                        } else {
                            userRecord.mHandler.sendEmptyMessage(2);
                            disposeUserIfNeededLocked(userRecord);
                        }
                    }
                }
            } finally {
            }
        }
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        synchronized (mediaRouter2ServiceImpl.mLock) {
            try {
                if (mediaRouter2ServiceImpl.mCurrentActiveUserId != i) {
                    Slog.i(
                            "MR2ServiceImpl",
                            TextUtils.formatSimple(
                                    "switchUser | user: %d", new Object[] {Integer.valueOf(i)}));
                    mediaRouter2ServiceImpl.mCurrentActiveUserId = i;
                    SparseArray clone2 = mediaRouter2ServiceImpl.mUserRecords.clone();
                    for (i2 = 0; i2 < clone2.size(); i2++) {
                        int keyAt2 = clone2.keyAt(i2);
                        MediaRouter2ServiceImpl.UserRecord userRecord2 =
                                (MediaRouter2ServiceImpl.UserRecord) clone2.valueAt(i2);
                        if (mediaRouter2ServiceImpl.mUserManagerInternal.getProfileParentId(keyAt2)
                                == mediaRouter2ServiceImpl.mCurrentActiveUserId) {
                            MediaRouter2ServiceImpl.UserHandler userHandler = userRecord2.mHandler;
                            userHandler.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(
                                                    2),
                                            userHandler));
                        } else {
                            MediaRouter2ServiceImpl.UserHandler userHandler2 = userRecord2.mHandler;
                            userHandler2.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(
                                                    1),
                                            userHandler2));
                            mediaRouter2ServiceImpl.disposeUserIfNeededLocked(userRecord2);
                        }
                    }
                }
            } finally {
            }
        }
    }

    public final void updateScanningState(IMediaRouter2Manager iMediaRouter2Manager, int i) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2Manager, "manager must not be null");
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException(
                    TextUtils.formatSimple(
                            "Scanning state %d is not valid.", new Object[] {Integer.valueOf(i)}));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.updateScanningStateLocked(iMediaRouter2Manager, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateScanningStateWithRouter2(IMediaRouter2 iMediaRouter2, int i) {
        MediaRouter2ServiceImpl mediaRouter2ServiceImpl = this.mService2;
        mediaRouter2ServiceImpl.getClass();
        Objects.requireNonNull(iMediaRouter2, "router must not be null");
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException(
                    TextUtils.formatSimple(
                            "Scanning state %d is not valid.", new Object[] {Integer.valueOf(i)}));
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (mediaRouter2ServiceImpl.mLock) {
                mediaRouter2ServiceImpl.updateScanningStateLocked(iMediaRouter2, i);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean validatePackageName$1(int i, String str) {
        String[] packagesForUid;
        if (str != null
                && (packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i))
                        != null) {
            for (String str2 : packagesForUid) {
                if (str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}
