package com.android.server.tv.interactive;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.graphics.Rect;
import android.media.PlaybackParams;
import android.media.tv.AdBuffer;
import android.media.tv.AdRequest;
import android.media.tv.AdResponse;
import android.media.tv.BroadcastInfoRequest;
import android.media.tv.BroadcastInfoResponse;
import android.media.tv.TvRecordingInfo;
import android.media.tv.ad.ITvAdClient;
import android.media.tv.ad.ITvAdManager;
import android.media.tv.ad.ITvAdManagerCallback;
import android.media.tv.ad.ITvAdService;
import android.media.tv.ad.ITvAdServiceCallback;
import android.media.tv.ad.ITvAdSession;
import android.media.tv.ad.ITvAdSessionCallback;
import android.media.tv.ad.TvAdServiceInfo;
import android.media.tv.interactive.AppLinkInfo;
import android.media.tv.interactive.ITvInteractiveAppClient;
import android.media.tv.interactive.ITvInteractiveAppManager;
import android.media.tv.interactive.ITvInteractiveAppManagerCallback;
import android.media.tv.interactive.ITvInteractiveAppService;
import android.media.tv.interactive.ITvInteractiveAppServiceCallback;
import android.media.tv.interactive.ITvInteractiveAppSession;
import android.media.tv.interactive.ITvInteractiveAppSessionCallback;
import android.media.tv.interactive.TvInteractiveAppServiceInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SharedMemory;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.InputChannel;
import android.view.Surface;

import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.media.tv.flags.Flags;
import com.android.server.ProfileService$1$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TvInteractiveAppManagerService extends SystemService {
    public final Context mContext;
    public int mCurrentUserId;
    public boolean mGetAdServiceListCalled;
    public boolean mGetAppLinkInfoListCalled;
    public boolean mGetServiceListCalled;
    public final Object mLock;
    public final Set mRunningProfiles;
    public final UserManager mUserManager;
    public final SparseArray mUserStates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdServiceCallback extends ITvAdServiceCallback.Stub {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdServiceConnection implements ServiceConnection {
        public final /* synthetic */ int $r8$classId;
        public final ComponentName mComponent;
        public final int mUserId;
        public final /* synthetic */ TvInteractiveAppManagerService this$0;

        public /* synthetic */ AdServiceConnection(
                TvInteractiveAppManagerService tvInteractiveAppManagerService,
                ComponentName componentName,
                int i,
                int i2) {
            this.$r8$classId = i2;
            this.this$0 = tvInteractiveAppManagerService;
            this.mComponent = componentName;
            this.mUserId = i;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            long clearCallingIdentity;
            switch (this.$r8$classId) {
                case 0:
                    synchronized (this.this$0.mLock) {
                        try {
                            UserState userStateLocked =
                                    this.this$0.getUserStateLocked(this.mUserId);
                            if (userStateLocked == null) {
                                this.this$0.mContext.unbindService(this);
                                return;
                            }
                            AdServiceState adServiceState =
                                    (AdServiceState)
                                            ((HashMap) userStateLocked.mAdServiceStateMap)
                                                    .get(this.mComponent);
                            adServiceState.mService = ITvAdService.Stub.asInterface(iBinder);
                            if (adServiceState.mCallback == null) {
                                AdServiceCallback adServiceCallback = new AdServiceCallback();
                                adServiceState.mCallback = adServiceCallback;
                                try {
                                    adServiceState.mService.registerCallback(adServiceCallback);
                                } catch (RemoteException e) {
                                    Slog.e(
                                            "TvInteractiveAppManagerService",
                                            "error in registerCallback",
                                            e);
                                }
                            }
                            if (!((ArrayList) adServiceState.mPendingAppLinkCommand).isEmpty()) {
                                Iterator it =
                                        ((ArrayList) adServiceState.mPendingAppLinkCommand)
                                                .iterator();
                                while (it.hasNext()) {
                                    Bundle bundle = (Bundle) it.next();
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        try {
                                            adServiceState.mService.sendAppLinkCommand(bundle);
                                            it.remove();
                                        } catch (RemoteException e2) {
                                            Slogf.e(
                                                    "TvInteractiveAppManagerService",
                                                    "error in sendAppLinkCommand("
                                                            + bundle
                                                            + ") when onServiceConnected",
                                                    e2);
                                        }
                                    } finally {
                                    }
                                }
                            }
                            ArrayList arrayList = new ArrayList();
                            Iterator it2 = ((ArrayList) adServiceState.mSessionTokens).iterator();
                            while (it2.hasNext()) {
                                IBinder iBinder2 = (IBinder) it2.next();
                                if (!TvInteractiveAppManagerService
                                        .m989$$Nest$mcreateAdSessionInternalLocked(
                                                this.this$0,
                                                adServiceState.mService,
                                                iBinder2,
                                                this.mUserId)) {
                                    arrayList.add(iBinder2);
                                }
                            }
                            Iterator it3 = arrayList.iterator();
                            while (it3.hasNext()) {
                                this.this$0.removeAdSessionStateLocked(
                                        this.mUserId, (IBinder) it3.next());
                            }
                            return;
                        } finally {
                        }
                    }
                default:
                    synchronized (this.this$0.mLock) {
                        try {
                            UserState userStateLocked2 =
                                    this.this$0.getUserStateLocked(this.mUserId);
                            if (userStateLocked2 == null) {
                                this.this$0.mContext.unbindService(this);
                                return;
                            }
                            ServiceState serviceState =
                                    (ServiceState)
                                            ((HashMap) userStateLocked2.mServiceStateMap)
                                                    .get(this.mComponent);
                            serviceState.mService =
                                    ITvInteractiveAppService.Stub.asInterface(iBinder);
                            if (serviceState.mCallback == null) {
                                ServiceCallback serviceCallback =
                                        this.this$0
                                        .new ServiceCallback(this.mComponent, this.mUserId);
                                serviceState.mCallback = serviceCallback;
                                try {
                                    serviceState.mService.registerCallback(serviceCallback);
                                } catch (RemoteException e3) {
                                    Slog.e(
                                            "TvInteractiveAppManagerService",
                                            "error in registerCallback",
                                            e3);
                                }
                            }
                            if (!((ArrayList) serviceState.mPendingAppLinkInfo).isEmpty()) {
                                Iterator it4 =
                                        ((ArrayList) serviceState.mPendingAppLinkInfo).iterator();
                                while (it4.hasNext()) {
                                    Pair pair = (Pair) it4.next();
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        try {
                                            if (((Boolean) pair.second).booleanValue()) {
                                                serviceState.mService.registerAppLinkInfo(
                                                        (AppLinkInfo) pair.first);
                                            } else {
                                                serviceState.mService.unregisterAppLinkInfo(
                                                        (AppLinkInfo) pair.first);
                                            }
                                            it4.remove();
                                        } catch (RemoteException e4) {
                                            Slogf.e(
                                                    "TvInteractiveAppManagerService",
                                                    "error in notifyAppLinkInfo("
                                                            + pair
                                                            + ") when onServiceConnected",
                                                    e4);
                                        }
                                    } finally {
                                    }
                                }
                            }
                            if (!((ArrayList) serviceState.mPendingAppLinkCommand).isEmpty()) {
                                Iterator it5 =
                                        ((ArrayList) serviceState.mPendingAppLinkCommand)
                                                .iterator();
                                while (it5.hasNext()) {
                                    Bundle bundle2 = (Bundle) it5.next();
                                    clearCallingIdentity = Binder.clearCallingIdentity();
                                    try {
                                        try {
                                            serviceState.mService.sendAppLinkCommand(bundle2);
                                            it5.remove();
                                        } finally {
                                        }
                                    } catch (RemoteException e5) {
                                        Slogf.e(
                                                "TvInteractiveAppManagerService",
                                                "error in sendAppLinkCommand("
                                                        + bundle2
                                                        + ") when onServiceConnected",
                                                e5);
                                    }
                                }
                            }
                            ArrayList arrayList2 = new ArrayList();
                            Iterator it6 = ((ArrayList) serviceState.mSessionTokens).iterator();
                            while (it6.hasNext()) {
                                IBinder iBinder3 = (IBinder) it6.next();
                                if (!TvInteractiveAppManagerService
                                        .m990$$Nest$mcreateSessionInternalLocked(
                                                this.this$0,
                                                serviceState.mService,
                                                iBinder3,
                                                this.mUserId)) {
                                    arrayList2.add(iBinder3);
                                }
                            }
                            Iterator it7 = arrayList2.iterator();
                            while (it7.hasNext()) {
                                this.this$0.removeSessionStateLocked$1(
                                        this.mUserId, (IBinder) it7.next());
                            }
                            return;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            switch (this.$r8$classId) {
                case 0:
                    if (!this.mComponent.equals(componentName)) {
                        throw new IllegalArgumentException(
                                "Mismatched ComponentName: "
                                        + this.mComponent
                                        + " (expected), "
                                        + componentName
                                        + " (actual).");
                    }
                    synchronized (this.this$0.mLock) {
                        try {
                            AdServiceState adServiceState =
                                    (AdServiceState)
                                            ((HashMap)
                                                            this.this$0.getOrCreateUserStateLocked(
                                                                            this.mUserId)
                                                                    .mAdServiceStateMap)
                                                    .get(this.mComponent);
                            if (adServiceState != null) {
                                adServiceState.mReconnecting = true;
                                adServiceState.mBound = false;
                                adServiceState.mService = null;
                                adServiceState.mCallback = null;
                                this.this$0.abortPendingCreateAdSessionRequestsLocked(
                                        adServiceState, null, this.mUserId);
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    if (!this.mComponent.equals(componentName)) {
                        throw new IllegalArgumentException(
                                "Mismatched ComponentName: "
                                        + this.mComponent
                                        + " (expected), "
                                        + componentName
                                        + " (actual).");
                    }
                    synchronized (this.this$0.mLock) {
                        try {
                            ServiceState serviceState =
                                    (ServiceState)
                                            ((HashMap)
                                                            this.this$0.getOrCreateUserStateLocked(
                                                                            this.mUserId)
                                                                    .mServiceStateMap)
                                                    .get(this.mComponent);
                            if (serviceState != null) {
                                serviceState.mReconnecting = true;
                                serviceState.mBound = false;
                                serviceState.mService = null;
                                serviceState.mCallback = null;
                                this.this$0.abortPendingCreateSessionRequestsLocked(
                                        serviceState, null, this.mUserId);
                            }
                        } finally {
                        }
                    }
                    return;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdServiceState {
        public boolean mBound;
        public AdServiceCallback mCallback;
        public final ComponentName mComponent;
        public final AdServiceConnection mConnection;
        public boolean mReconnecting;
        public ITvAdService mService;
        public final List mSessionTokens = new ArrayList();
        public final List mPendingAppLinkCommand = new ArrayList();

        public AdServiceState(
                TvInteractiveAppManagerService tvInteractiveAppManagerService,
                ComponentName componentName,
                int i) {
            this.mComponent = componentName;
            this.mConnection =
                    new AdServiceConnection(tvInteractiveAppManagerService, componentName, i, 0);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdSessionCallback extends ITvAdSessionCallback.Stub {
        public final InputChannel[] mInputChannels;
        public final AdSessionState mSessionState;

        public AdSessionCallback(AdSessionState adSessionState, InputChannel[] inputChannelArr) {
            this.mSessionState = adSessionState;
            this.mInputChannels = inputChannelArr;
        }

        public final boolean addAdSessionTokenToClientStateLocked(ITvAdSession iTvAdSession) {
            try {
                iTvAdSession.asBinder().linkToDeath(this.mSessionState, 0);
                IBinder asBinder = this.mSessionState.mClient.asBinder();
                UserState orCreateUserStateLocked =
                        TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                this.mSessionState.mUserId);
                ClientState clientState =
                        (ClientState)
                                ((HashMap) orCreateUserStateLocked.mClientStateMap).get(asBinder);
                if (clientState == null) {
                    clientState =
                            TvInteractiveAppManagerService.this
                            .new ClientState(asBinder, this.mSessionState.mUserId);
                    try {
                        asBinder.linkToDeath(clientState, 0);
                        ((HashMap) orCreateUserStateLocked.mClientStateMap)
                                .put(asBinder, clientState);
                    } catch (RemoteException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "client process has already died",
                                e);
                        return false;
                    }
                }
                ((ArrayList) clientState.mSessionTokens).add(this.mSessionState.mSessionToken);
                return true;
            } catch (RemoteException e2) {
                Slogf.e("TvInteractiveAppManagerService", "session process has already died", e2);
                return false;
            }
        }

        public final void onLayoutSurface(int i, int i2, int i3, int i4) {
            ITvAdClient iTvAdClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                AdSessionState adSessionState = this.mSessionState;
                if (adSessionState.mSession == null
                        || (iTvAdClient = adSessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvAdClient.onLayoutSurface(i, i2, i3, i4, adSessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onLayoutSurface", e);
                }
            }
        }

        public final void onRequestCurrentChannelUri() {
            ITvAdClient iTvAdClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                AdSessionState adSessionState = this.mSessionState;
                if (adSessionState.mSession == null
                        || (iTvAdClient = adSessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvAdClient.onRequestCurrentChannelUri(adSessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestCurrentChannelUri",
                            e);
                }
            }
        }

        public final void onRequestCurrentTvInputId() {
            ITvAdClient iTvAdClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                AdSessionState adSessionState = this.mSessionState;
                if (adSessionState.mSession == null
                        || (iTvAdClient = adSessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvAdClient.onRequestCurrentTvInputId(adSessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestCurrentTvInputId",
                            e);
                }
            }
        }

        public final void onRequestCurrentVideoBounds() {
            ITvAdClient iTvAdClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                AdSessionState adSessionState = this.mSessionState;
                if (adSessionState.mSession == null
                        || (iTvAdClient = adSessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvAdClient.onRequestCurrentVideoBounds(adSessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestCurrentVideoBounds",
                            e);
                }
            }
        }

        public final void onRequestSigning(String str, String str2, String str3, byte[] bArr) {
            ITvAdClient iTvAdClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                AdSessionState adSessionState = this.mSessionState;
                if (adSessionState.mSession == null
                        || (iTvAdClient = adSessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvAdClient.onRequestSigning(str, str2, str3, bArr, adSessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestSigning", e);
                }
            }
        }

        public final void onRequestTrackInfoList() {
            ITvAdClient iTvAdClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                AdSessionState adSessionState = this.mSessionState;
                if (adSessionState.mSession == null
                        || (iTvAdClient = adSessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvAdClient.onRequestTrackInfoList(adSessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestTrackInfoList", e);
                }
            }
        }

        public final void onSessionCreated(ITvAdSession iTvAdSession) {
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                try {
                    this.mSessionState.mSession = iTvAdSession;
                    if (iTvAdSession == null
                            || !addAdSessionTokenToClientStateLocked(iTvAdSession)) {
                        TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                TvInteractiveAppManagerService.this;
                        AdSessionState adSessionState = this.mSessionState;
                        tvInteractiveAppManagerService.removeAdSessionStateLocked(
                                adSessionState.mUserId, adSessionState.mSessionToken);
                        TvInteractiveAppManagerService tvInteractiveAppManagerService2 =
                                TvInteractiveAppManagerService.this;
                        AdSessionState adSessionState2 = this.mSessionState;
                        ITvAdClient iTvAdClient = adSessionState2.mClient;
                        String str = adSessionState2.mAdServiceId;
                        int i = adSessionState2.mSeq;
                        tvInteractiveAppManagerService2.getClass();
                        TvInteractiveAppManagerService.sendAdSessionTokenToClientLocked(
                                iTvAdClient, str, null, null, i);
                    } else {
                        TvInteractiveAppManagerService tvInteractiveAppManagerService3 =
                                TvInteractiveAppManagerService.this;
                        AdSessionState adSessionState3 = this.mSessionState;
                        ITvAdClient iTvAdClient2 = adSessionState3.mClient;
                        String str2 = adSessionState3.mAdServiceId;
                        IBinder iBinder = adSessionState3.mSessionToken;
                        InputChannel inputChannel = this.mInputChannels[0];
                        int i2 = adSessionState3.mSeq;
                        tvInteractiveAppManagerService3.getClass();
                        TvInteractiveAppManagerService.sendAdSessionTokenToClientLocked(
                                iTvAdClient2, str2, iBinder, inputChannel, i2);
                    }
                    this.mInputChannels[0].dispose();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onTvAdSessionData(String str, Bundle bundle) {
            ITvAdClient iTvAdClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                AdSessionState adSessionState = this.mSessionState;
                if (adSessionState.mSession == null
                        || (iTvAdClient = adSessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvAdClient.onTvAdSessionData(str, bundle, adSessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onTvAdSessionData", e);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AdSessionState implements IBinder.DeathRecipient {
        public final String mAdServiceId;
        public final int mCallingUid;
        public final ITvAdClient mClient;
        public final ComponentName mComponent;
        public final int mSeq;
        public ITvAdSession mSession;
        public final IBinder mSessionToken;
        public final String mType;
        public final int mUserId;

        public AdSessionState(
                IBinder iBinder,
                String str,
                String str2,
                ComponentName componentName,
                ITvAdClient iTvAdClient,
                int i,
                int i2,
                int i3) {
            this.mSessionToken = iBinder;
            this.mAdServiceId = str;
            this.mType = str2;
            this.mComponent = componentName;
            this.mClient = iTvAdClient;
            this.mSeq = i;
            this.mCallingUid = i2;
            this.mUserId = i3;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                this.mSession = null;
                TvInteractiveAppManagerService tvInteractiveAppManagerService =
                        TvInteractiveAppManagerService.this;
                tvInteractiveAppManagerService.getClass();
                ITvAdClient iTvAdClient = this.mClient;
                if (iTvAdClient != null) {
                    try {
                        iTvAdClient.onSessionReleased(this.mSeq);
                    } catch (RemoteException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in onSessionReleased", e);
                    }
                }
                tvInteractiveAppManagerService.removeAdSessionStateLocked(
                        this.mUserId, this.mSessionToken);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends ITvInteractiveAppManager.Stub {
        public BinderService() {}

        public final void createBiInteractiveApp(IBinder iBinder, Uri uri, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "createBiInteractiveApp");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .createBiInteractiveApp(uri, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in createBiInteractiveApp",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "createMediaView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService.getSessionLocked(
                                        TvInteractiveAppManagerService.this.getSessionStateLocked(
                                                callingUid,
                                                iBinder,
                                                m994$$Nest$mresolveCallingUserId))
                                .createMediaView(iBinder2, rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in createMediaView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void createSession(
                ITvInteractiveAppClient iTvInteractiveAppClient,
                String str,
                int i,
                int i2,
                int i3) {
            long j;
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i3,
                            "createSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    synchronized (TvInteractiveAppManagerService.this.mLock) {
                        try {
                            TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                    TvInteractiveAppManagerService.this;
                            if (i3 != tvInteractiveAppManagerService.mCurrentUserId) {
                                if (!((HashSet) tvInteractiveAppManagerService.mRunningProfiles)
                                        .contains(Integer.valueOf(i3))) {
                                    TvInteractiveAppManagerService.this.getClass();
                                    TvInteractiveAppManagerService.sendSessionTokenToClientLocked(
                                            iTvInteractiveAppClient, str, null, null, i2);
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                                }
                            }
                            UserState orCreateUserStateLocked =
                                    TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                            m994$$Nest$mresolveCallingUserId);
                            TvInteractiveAppState tvInteractiveAppState =
                                    (TvInteractiveAppState)
                                            orCreateUserStateLocked.mIAppMap.get(str);
                            if (tvInteractiveAppState == null) {
                                Slogf.w(
                                        "TvInteractiveAppManagerService",
                                        "Failed to find state for iAppServiceId=" + str);
                                TvInteractiveAppManagerService.this.getClass();
                                TvInteractiveAppManagerService.sendSessionTokenToClientLocked(
                                        iTvInteractiveAppClient, str, null, null, i2);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            ServiceState serviceState =
                                    (ServiceState)
                                            ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                                    .get(tvInteractiveAppState.mComponentName);
                            if (serviceState == null) {
                                int i4 =
                                        PackageManager.getApplicationInfoAsUserCached(
                                                        tvInteractiveAppState.mComponentName
                                                                .getPackageName(),
                                                        0L,
                                                        m994$$Nest$mresolveCallingUserId)
                                                .uid;
                                serviceState =
                                        new ServiceState(
                                                TvInteractiveAppManagerService.this,
                                                tvInteractiveAppState.mComponentName,
                                                str,
                                                m994$$Nest$mresolveCallingUserId);
                                ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                        .put(tvInteractiveAppState.mComponentName, serviceState);
                            }
                            ServiceState serviceState2 = serviceState;
                            if (serviceState2.mReconnecting) {
                                TvInteractiveAppManagerService.this.getClass();
                                TvInteractiveAppManagerService.sendSessionTokenToClientLocked(
                                        iTvInteractiveAppClient, str, null, null, i2);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            Binder binder = new Binder();
                            ((HashMap) orCreateUserStateLocked.mSessionStateMap)
                                    .put(
                                            binder,
                                            TvInteractiveAppManagerService.this
                                            .new SessionState(
                                                    binder,
                                                    str,
                                                    i,
                                                    tvInteractiveAppState.mComponentName,
                                                    iTvInteractiveAppClient,
                                                    i2,
                                                    callingUid,
                                                    m994$$Nest$mresolveCallingUserId));
                            ((ArrayList) serviceState2.mSessionTokens).add(binder);
                            ITvInteractiveAppService iTvInteractiveAppService =
                                    serviceState2.mService;
                            if (iTvInteractiveAppService == null) {
                                TvInteractiveAppManagerService.this.updateServiceConnectionLocked$1(
                                        m994$$Nest$mresolveCallingUserId,
                                        tvInteractiveAppState.mComponentName);
                            } else if (!TvInteractiveAppManagerService
                                    .m990$$Nest$mcreateSessionInternalLocked(
                                            TvInteractiveAppManagerService.this,
                                            iTvInteractiveAppService,
                                            binder,
                                            m994$$Nest$mresolveCallingUserId)) {
                                TvInteractiveAppManagerService.this.removeSessionStateLocked$1(
                                        m994$$Nest$mresolveCallingUserId, binder);
                            }
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

        public final void destroyBiInteractiveApp(IBinder iBinder, String str, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "destroyBiInteractiveApp");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .destroyBiInteractiveApp(str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in destroyBiInteractiveApp",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i4,
                            "dispatchSurfaceChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .dispatchSurfaceChanged(i, i2, i3);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in dispatchSurfaceChanged",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getAppLinkInfoList(int i) {
            ArrayList arrayList;
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "getAppLinkInfoList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                TvInteractiveAppManagerService.this;
                        if (!tvInteractiveAppManagerService.mGetAppLinkInfoListCalled) {
                            tvInteractiveAppManagerService.buildAppLinkInfoLocked(i);
                            TvInteractiveAppManagerService.this.mGetAppLinkInfoListCalled = true;
                        }
                        arrayList =
                                new ArrayList(
                                        TvInteractiveAppManagerService.this
                                                .getOrCreateUserStateLocked(
                                                        m994$$Nest$mresolveCallingUserId)
                                                .mAppLinkInfoList);
                    } finally {
                    }
                }
                return arrayList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getTvInteractiveAppServiceList(int i) {
            ArrayList arrayList;
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "getTvInteractiveAppServiceList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                TvInteractiveAppManagerService.this;
                        if (!tvInteractiveAppManagerService.mGetServiceListCalled) {
                            tvInteractiveAppManagerService.buildTvInteractiveAppServiceListLocked(
                                    i, null);
                            TvInteractiveAppManagerService.this.mGetServiceListCalled = true;
                        }
                        UserState orCreateUserStateLocked =
                                TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                        m994$$Nest$mresolveCallingUserId);
                        arrayList = new ArrayList();
                        Iterator it = orCreateUserStateLocked.mIAppMap.values().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((TvInteractiveAppState) it.next()).mInfo);
                        }
                    } finally {
                    }
                }
                return arrayList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyAdBufferConsumed(IBinder iBinder, AdBuffer adBuffer, int i) {
            SharedMemory sharedMemory;
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyAdBufferConsumed");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        try {
                            SessionState sessionStateLocked =
                                    TvInteractiveAppManagerService.this.getSessionStateLocked(
                                            callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                            TvInteractiveAppManagerService.this.getClass();
                            TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                    .notifyAdBufferConsumed(adBuffer);
                        } catch (Throwable th) {
                            if (adBuffer != null) {
                                adBuffer.getSharedMemory().close();
                            }
                            throw th;
                        }
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyAdBufferConsumed",
                                e);
                        if (adBuffer != null) {
                            sharedMemory = adBuffer.getSharedMemory();
                        }
                    }
                    if (adBuffer != null) {
                        sharedMemory = adBuffer.getSharedMemory();
                        sharedMemory.close();
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyAdResponse(IBinder iBinder, AdResponse adResponse, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyAdResponse");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyAdResponse(adResponse);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in notifyAdResponse", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyBroadcastInfoResponse(
                IBinder iBinder, BroadcastInfoResponse broadcastInfoResponse, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyBroadcastInfoResponse");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyBroadcastInfoResponse(broadcastInfoResponse);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyBroadcastInfoResponse",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyContentAllowed(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyContentAllowed");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyContentAllowed();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyContentAllowed",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyContentBlocked(IBinder iBinder, String str, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyContentBlocked");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyContentBlocked(str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyContentBlocked",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyError(IBinder iBinder, String str, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyError");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyError(str, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in notifyError", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyRecordingConnectionFailed(
                IBinder iBinder, String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyRecordingConnectionFailed");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyRecordingConnectionFailed(str, str2);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyRecordingConnectionFailed",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyRecordingDisconnected(
                IBinder iBinder, String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyRecordingDisconnected");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyRecordingDisconnected(str, str2);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyRecordingDisconnected",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyRecordingError(IBinder iBinder, String str, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "notifyRecordingError");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyRecordingError(str, i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyRecordingError",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyRecordingScheduled(
                IBinder iBinder, String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyRecordingScheduled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyRecordingScheduled(str, str2);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyRecordingScheduled",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyRecordingStarted(IBinder iBinder, String str, String str2, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyRecordingStarted");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyRecordingStarted(str, str2);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyRecordingStarted",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyRecordingStopped(IBinder iBinder, String str, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyRecordingStopped");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyRecordingStopped(str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyRecordingStopped",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyRecordingTuned(IBinder iBinder, String str, Uri uri, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyRecordingTuned");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyRecordingTuned(str, uri);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyRecordingTuned",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifySignalStrength(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "notifySignalStrength");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifySignalStrength(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifySignalStrength",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTimeShiftCurrentPositionChanged(
                IBinder iBinder, String str, long j, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyTimeShiftCurrentPositionChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTimeShiftCurrentPositionChanged(str, j);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyTimeShiftCurrentPositionChanged",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTimeShiftPlaybackParams(
                IBinder iBinder, PlaybackParams playbackParams, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyTimeShiftPlaybackParams");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTimeShiftPlaybackParams(playbackParams);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyTimeShiftPlaybackParams",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTimeShiftStartPositionChanged(
                IBinder iBinder, String str, long j, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyTimeShiftStartPositionChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTimeShiftStartPositionChanged(str, j);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyTimeShiftStartPositionChanged",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTimeShiftStatusChanged(IBinder iBinder, String str, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "notifyTimeShiftStatusChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTimeShiftStatusChanged(str, i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyTimeShiftStatusChanged",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTrackSelected(IBinder iBinder, int i, String str, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "notifyTrackSelected");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTrackSelected(i, str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyTrackSelected",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTracksChanged(IBinder iBinder, List list, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyTracksChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTracksChanged(list);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyTracksChanged",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTuned(IBinder iBinder, Uri uri, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyTuned");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTuned(uri);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in notifyTuned", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "notifyTvMessage");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyTvMessage(i, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in notifyTvMessage", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyVideoAvailable(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyVideoAvailable");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyVideoAvailable();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyVideoAvailable",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyVideoFreezeUpdated(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyVideoFreezeUpdated");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyVideoFreezeUpdated(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyVideoFreezeUpdated",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyVideoUnavailable(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "notifyVideoUnavailable");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .notifyVideoUnavailable(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyVideoUnavailable",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "registerAppLinkInfo: " + appLinkInfo);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in registerAppLinkInfo", e);
                }
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    UserState orCreateUserStateLocked =
                            TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                    m994$$Nest$mresolveCallingUserId);
                    TvInteractiveAppState tvInteractiveAppState =
                            (TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str);
                    if (tvInteractiveAppState == null) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "failed to registerAppLinkInfo - unknown TIAS id " + str);
                        return;
                    }
                    ComponentName component = tvInteractiveAppState.mInfo.getComponent();
                    ServiceState serviceState =
                            (ServiceState)
                                    ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                            .get(component);
                    if (serviceState == null) {
                        ServiceState serviceState2 =
                                new ServiceState(
                                        TvInteractiveAppManagerService.this,
                                        component,
                                        str,
                                        m994$$Nest$mresolveCallingUserId);
                        ServiceState.m995$$Nest$maddPendingAppLink(
                                serviceState2, appLinkInfo, true);
                        ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                .put(component, serviceState2);
                        TvInteractiveAppManagerService.this.updateServiceConnectionLocked$1(
                                m994$$Nest$mresolveCallingUserId, component);
                    } else {
                        ITvInteractiveAppService iTvInteractiveAppService = serviceState.mService;
                        if (iTvInteractiveAppService != null) {
                            iTvInteractiveAppService.registerAppLinkInfo(appLinkInfo);
                        } else {
                            ServiceState.m995$$Nest$maddPendingAppLink(
                                    serviceState, appLinkInfo, true);
                            TvInteractiveAppManagerService.this.updateServiceConnectionLocked$1(
                                    m994$$Nest$mresolveCallingUserId, component);
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerCallback(
                ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "registerCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (!TvInteractiveAppManagerService.this
                                .getOrCreateUserStateLocked(m994$$Nest$mresolveCallingUserId)
                                .mCallbacks
                                .register(iTvInteractiveAppManagerCallback)) {
                            Slog.e(
                                    "TvInteractiveAppManagerService",
                                    "client process has already died");
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void relayoutMediaView(IBinder iBinder, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "relayoutMediaView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService.getSessionLocked(
                                        TvInteractiveAppManagerService.this.getSessionStateLocked(
                                                callingUid,
                                                iBinder,
                                                m994$$Nest$mresolveCallingUserId))
                                .relayoutMediaView(rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in relayoutMediaView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseSession(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "releaseSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    TvInteractiveAppManagerService.m992$$Nest$mreleaseAdSessionLocked(
                            TvInteractiveAppManagerService.this,
                            iBinder,
                            callingUid,
                            m994$$Nest$mresolveCallingUserId);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeMediaView(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "removeMediaView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService.getSessionLocked(
                                        TvInteractiveAppManagerService.this.getSessionStateLocked(
                                                callingUid,
                                                iBinder,
                                                m994$$Nest$mresolveCallingUserId))
                                .removeMediaView();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in removeMediaView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resetInteractiveApp(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "resetInteractiveApp");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .resetInteractiveApp();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in reset", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendAppLinkCommand(String str, Bundle bundle, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "sendAppLinkCommand");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in sendAppLinkCommand", e);
                }
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    UserState orCreateUserStateLocked =
                            TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                    m994$$Nest$mresolveCallingUserId);
                    TvInteractiveAppState tvInteractiveAppState =
                            (TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str);
                    if (tvInteractiveAppState == null) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "failed to sendAppLinkCommand - unknown TIAS id " + str);
                        return;
                    }
                    ComponentName component = tvInteractiveAppState.mInfo.getComponent();
                    ServiceState serviceState =
                            (ServiceState)
                                    ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                            .get(component);
                    if (serviceState == null) {
                        ServiceState serviceState2 =
                                new ServiceState(
                                        TvInteractiveAppManagerService.this,
                                        component,
                                        str,
                                        m994$$Nest$mresolveCallingUserId);
                        ((ArrayList) serviceState2.mPendingAppLinkCommand).add(bundle);
                        ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                .put(component, serviceState2);
                        TvInteractiveAppManagerService.this.updateServiceConnectionLocked$1(
                                m994$$Nest$mresolveCallingUserId, component);
                    } else {
                        ITvInteractiveAppService iTvInteractiveAppService = serviceState.mService;
                        if (iTvInteractiveAppService != null) {
                            iTvInteractiveAppService.sendAppLinkCommand(bundle);
                        } else {
                            ((ArrayList) serviceState.mPendingAppLinkCommand).add(bundle);
                            TvInteractiveAppManagerService.this.updateServiceConnectionLocked$1(
                                    m994$$Nest$mresolveCallingUserId, component);
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendAvailableSpeeds(IBinder iBinder, float[] fArr, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendAvailableSpeeds");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendAvailableSpeeds(fArr);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendAvailableSpeeds",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCertificate(
                IBinder iBinder, String str, int i, Bundle bundle, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "sendCertificate");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendCertificate(str, i, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in sendCertificate", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCurrentChannelLcn(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "sendCurrentChannelLcn");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendCurrentChannelLcn(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendCurrentChannelLcn",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendCurrentChannelUri");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendCurrentChannelUri(uri);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendCurrentChannelUri",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCurrentTvInputId(IBinder iBinder, String str, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendCurrentTvInputId");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendCurrentTvInputId(str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendCurrentTvInputId",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendCurrentVideoBounds");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendCurrentVideoBounds(rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendCurrentVideoBounds",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendSelectedTrackInfo(IBinder iBinder, List list, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendSelectedTrackInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendSelectedTrackInfo(list);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendSelectedTrackInfo",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendSigningResult");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendSigningResult(str, bArr);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in sendSigningResult", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendStreamVolume(IBinder iBinder, float f, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendStreamVolume");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendStreamVolume(f);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in sendStreamVolume", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendTimeShiftMode(IBinder iBinder, int i, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "sendTimeShiftMode");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendTimeShiftMode(i);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in sendTimeShiftMode", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendTrackInfoList(IBinder iBinder, List list, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendTrackInfoList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendTrackInfoList(list);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in sendTrackInfoList", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendTvRecordingInfo(
                IBinder iBinder, TvRecordingInfo tvRecordingInfo, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendTvRecordingInfo");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendTvRecordingInfo(tvRecordingInfo);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendTvRecordingInfo",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendTvRecordingInfoList(IBinder iBinder, List list, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendTvRecordingInfoList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .sendTvRecordingInfoList(list);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendTvRecordingInfoList",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setSurface(IBinder iBinder, Surface surface, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "setSurface");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .setSurface(surface);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in setSurface", e);
                    }
                }
            } finally {
                if (surface != null) {
                    surface.release();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setTeletextAppEnabled(IBinder iBinder, boolean z, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "setTeletextAppEnabled");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .setTeletextAppEnabled(z);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in setTeletextAppEnabled",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startInteractiveApp(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "startInteractiveApp");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .startInteractiveApp();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in start", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void stopInteractiveApp(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "stopInteractiveApp");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        SessionState sessionStateLocked =
                                TvInteractiveAppManagerService.this.getSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getSessionLocked(sessionStateLocked)
                                .stopInteractiveApp();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in stop", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterAppLinkInfo(String str, AppLinkInfo appLinkInfo, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "unregisterAppLinkInfo: " + appLinkInfo);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in unregisterAppLinkInfo", e);
                }
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    UserState orCreateUserStateLocked =
                            TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                    m994$$Nest$mresolveCallingUserId);
                    TvInteractiveAppState tvInteractiveAppState =
                            (TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(str);
                    if (tvInteractiveAppState == null) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "failed to unregisterAppLinkInfo - unknown TIAS id " + str);
                        return;
                    }
                    ComponentName component = tvInteractiveAppState.mInfo.getComponent();
                    ServiceState serviceState =
                            (ServiceState)
                                    ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                            .get(component);
                    if (serviceState == null) {
                        ServiceState serviceState2 =
                                new ServiceState(
                                        TvInteractiveAppManagerService.this,
                                        component,
                                        str,
                                        m994$$Nest$mresolveCallingUserId);
                        ServiceState.m995$$Nest$maddPendingAppLink(
                                serviceState2, appLinkInfo, false);
                        ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                .put(component, serviceState2);
                        TvInteractiveAppManagerService.this.updateServiceConnectionLocked$1(
                                m994$$Nest$mresolveCallingUserId, component);
                    } else {
                        ITvInteractiveAppService iTvInteractiveAppService = serviceState.mService;
                        if (iTvInteractiveAppService != null) {
                            iTvInteractiveAppService.unregisterAppLinkInfo(appLinkInfo);
                        } else {
                            ServiceState.m995$$Nest$maddPendingAppLink(
                                    serviceState, appLinkInfo, false);
                            TvInteractiveAppManagerService.this.updateServiceConnectionLocked$1(
                                    m994$$Nest$mresolveCallingUserId, component);
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterCallback(
                ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "unregisterCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    TvInteractiveAppManagerService.this
                            .getOrCreateUserStateLocked(m994$$Nest$mresolveCallingUserId)
                            .mCallbacks
                            .unregister(iTvInteractiveAppManagerCallback);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClientState implements IBinder.DeathRecipient {
        public IBinder mClientToken;
        public final List mSessionTokens = new ArrayList();
        public final int mUserId;

        public ClientState(IBinder iBinder, int i) {
            this.mClientToken = iBinder;
            this.mUserId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                try {
                    ClientState clientState =
                            (ClientState)
                                    ((HashMap)
                                                    TvInteractiveAppManagerService.this
                                                            .getOrCreateUserStateLocked(
                                                                    this.mUserId)
                                                            .mClientStateMap)
                                            .get(this.mClientToken);
                    if (clientState != null) {
                        while (((ArrayList) clientState.mSessionTokens).size() > 0) {
                            IBinder iBinder =
                                    (IBinder) ((ArrayList) clientState.mSessionTokens).get(0);
                            TvInteractiveAppManagerService.m993$$Nest$mreleaseSessionLocked(
                                    TvInteractiveAppManagerService.this,
                                    iBinder,
                                    1000,
                                    this.mUserId);
                            if (((ArrayList) clientState.mSessionTokens).contains(iBinder)) {
                                Slogf.d(
                                        "TvInteractiveAppManagerService",
                                        "remove sessionToken "
                                                + iBinder
                                                + " for "
                                                + this.mClientToken);
                                ((ArrayList) clientState.mSessionTokens).remove(iBinder);
                            }
                        }
                    }
                    this.mClientToken = null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceCallback extends ITvInteractiveAppServiceCallback.Stub {
        public final ComponentName mComponent;
        public final int mUserId;

        public ServiceCallback(ComponentName componentName, int i) {
            this.mComponent = componentName;
            this.mUserId = i;
        }

        public final void onStateChanged(int i, int i2, int i3) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    String str =
                            TvInteractiveAppManagerService.m991$$Nest$mgetServiceStateLocked(
                                            TvInteractiveAppManagerService.this,
                                            this.mComponent,
                                            this.mUserId)
                                    .mIAppServiceId;
                    UserState userStateLocked =
                            TvInteractiveAppManagerService.this.getUserStateLocked(this.mUserId);
                    TvInteractiveAppManagerService.this.getClass();
                    int beginBroadcast = userStateLocked.mCallbacks.beginBroadcast();
                    for (int i4 = 0; i4 < beginBroadcast; i4++) {
                        try {
                            userStateLocked
                                    .mCallbacks
                                    .getBroadcastItem(i4)
                                    .onStateChanged(str, i, i2, i3);
                        } catch (RemoteException e) {
                            Slog.e(
                                    "TvInteractiveAppManagerService",
                                    "failed to report RTE state changed",
                                    e);
                        }
                    }
                    userStateLocked.mCallbacks.finishBroadcast();
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceState {
        public boolean mBound;
        public ServiceCallback mCallback;
        public final ComponentName mComponent;
        public final AdServiceConnection mConnection;
        public final String mIAppServiceId;
        public boolean mReconnecting;
        public ITvInteractiveAppService mService;
        public final List mSessionTokens = new ArrayList();
        public final List mPendingAppLinkInfo = new ArrayList();
        public final List mPendingAppLinkCommand = new ArrayList();

        /* renamed from: -$$Nest$maddPendingAppLink, reason: not valid java name */
        public static void m995$$Nest$maddPendingAppLink(
                ServiceState serviceState, AppLinkInfo appLinkInfo, boolean z) {
            ((ArrayList) serviceState.mPendingAppLinkInfo)
                    .add(Pair.create(appLinkInfo, Boolean.valueOf(z)));
        }

        public ServiceState(
                TvInteractiveAppManagerService tvInteractiveAppManagerService,
                ComponentName componentName,
                String str,
                int i) {
            this.mComponent = componentName;
            this.mConnection =
                    new AdServiceConnection(tvInteractiveAppManagerService, componentName, i, 1);
            this.mIAppServiceId = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionCallback extends ITvInteractiveAppSessionCallback.Stub {
        public final InputChannel[] mInputChannels;
        public final SessionState mSessionState;

        public SessionCallback(SessionState sessionState, InputChannel[] inputChannelArr) {
            this.mSessionState = sessionState;
            this.mInputChannels = inputChannelArr;
        }

        public final boolean addSessionTokenToClientStateLocked(
                ITvInteractiveAppSession iTvInteractiveAppSession) {
            try {
                iTvInteractiveAppSession.asBinder().linkToDeath(this.mSessionState, 0);
                IBinder asBinder = this.mSessionState.mClient.asBinder();
                UserState orCreateUserStateLocked =
                        TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                this.mSessionState.mUserId);
                ClientState clientState =
                        (ClientState)
                                ((HashMap) orCreateUserStateLocked.mClientStateMap).get(asBinder);
                if (clientState == null) {
                    clientState =
                            TvInteractiveAppManagerService.this
                            .new ClientState(asBinder, this.mSessionState.mUserId);
                    try {
                        asBinder.linkToDeath(clientState, 0);
                        ((HashMap) orCreateUserStateLocked.mClientStateMap)
                                .put(asBinder, clientState);
                    } catch (RemoteException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "client process has already died",
                                e);
                        return false;
                    }
                }
                ((ArrayList) clientState.mSessionTokens).add(this.mSessionState.mSessionToken);
                return true;
            } catch (RemoteException e2) {
                Slogf.e("TvInteractiveAppManagerService", "session process has already died", e2);
                return false;
            }
        }

        public final void onAdBufferReady(AdBuffer adBuffer) {
            SharedMemory sharedMemory;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession != null) {
                    ITvInteractiveAppClient iTvInteractiveAppClient = sessionState.mClient;
                    try {
                        if (iTvInteractiveAppClient != null) {
                            try {
                                iTvInteractiveAppClient.onAdBufferReady(
                                        adBuffer, sessionState.mSeq);
                            } catch (RemoteException e) {
                                Slogf.e("TvInteractiveAppManagerService", "error in onAdBuffer", e);
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

        public final void onAdRequest(AdRequest adRequest) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onAdRequest(adRequest, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onAdRequest", e);
                }
            }
        }

        public final void onBiInteractiveAppCreated(Uri uri, String str) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onBiInteractiveAppCreated(uri, str, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onBiInteractiveAppCreated",
                            e);
                }
            }
        }

        public final void onBroadcastInfoRequest(BroadcastInfoRequest broadcastInfoRequest) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onBroadcastInfoRequest(
                            broadcastInfoRequest, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onBroadcastInfoRequest", e);
                }
            }
        }

        public final void onCommandRequest(String str, Bundle bundle) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onCommandRequest(str, bundle, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onCommandRequest", e);
                }
            }
        }

        public final void onLayoutSurface(int i, int i2, int i3, int i4) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onLayoutSurface(i, i2, i3, i4, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onLayoutSurface", e);
                }
            }
        }

        public final void onRemoveBroadcastInfo(int i) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRemoveBroadcastInfo(i, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRemoveBroadcastInfo", e);
                }
            }
        }

        public final void onRequestAvailableSpeeds() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestAvailableSpeeds(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestAvailableSpeeds",
                            e);
                }
            }
        }

        public final void onRequestCertificate(String str, int i) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestCertificate(str, i, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestCertificate", e);
                }
            }
        }

        public final void onRequestCurrentChannelLcn() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestCurrentChannelLcn(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestCurrentChannelLcn",
                            e);
                }
            }
        }

        public final void onRequestCurrentChannelUri() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestCurrentChannelUri(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestCurrentChannelUri",
                            e);
                }
            }
        }

        public final void onRequestCurrentTvInputId() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestCurrentTvInputId(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestCurrentTvInputId",
                            e);
                }
            }
        }

        public final void onRequestCurrentVideoBounds() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestCurrentVideoBounds(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestCurrentVideoBounds",
                            e);
                }
            }
        }

        public final void onRequestScheduleRecording(
                String str, String str2, Uri uri, Uri uri2, Bundle bundle) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestScheduleRecording(
                            str, str2, uri, uri2, bundle, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestScheduleRecording",
                            e);
                }
            }
        }

        public final void onRequestScheduleRecording2(
                String str, String str2, Uri uri, long j, long j2, int i, Bundle bundle) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestScheduleRecording2(
                            str, str2, uri, j, j2, i, bundle, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestScheduleRecording2",
                            e);
                }
            }
        }

        public final void onRequestSelectedTrackInfo() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestSelectedTrackInfo(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestSelectedTrackInfo",
                            e);
                }
            }
        }

        public final void onRequestSigning(String str, String str2, String str3, byte[] bArr) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestSigning(
                            str, str2, str3, bArr, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestSigning", e);
                }
            }
        }

        public final void onRequestSigning2(
                String str, String str2, String str3, int i, byte[] bArr) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestSigning2(
                            str, str2, str3, i, bArr, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestSigning", e);
                }
            }
        }

        public final void onRequestStartRecording(String str, Uri uri) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestStartRecording(str, uri, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestStartRecording",
                            e);
                }
            }
        }

        public final void onRequestStopRecording(String str) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestStopRecording(str, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestStopRecording", e);
                }
            }
        }

        public final void onRequestStreamVolume() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestStreamVolume(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestStreamVolume", e);
                }
            }
        }

        public final void onRequestTimeShiftMode() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestTimeShiftMode(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestTimeShiftMode", e);
                }
            }
        }

        public final void onRequestTrackInfoList() {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestTrackInfoList(sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onRequestTrackInfoList", e);
                }
            }
        }

        public final void onRequestTvRecordingInfo(String str) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestTvRecordingInfo(str, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestTvRecordingInfo",
                            e);
                }
            }
        }

        public final void onRequestTvRecordingInfoList(int i) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onRequestTvRecordingInfoList(i, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onRequestTvRecordingInfoList",
                            e);
                }
            }
        }

        public final void onSessionCreated(ITvInteractiveAppSession iTvInteractiveAppSession) {
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                try {
                    this.mSessionState.mSession = iTvInteractiveAppSession;
                    if (iTvInteractiveAppSession == null
                            || !addSessionTokenToClientStateLocked(iTvInteractiveAppSession)) {
                        TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                TvInteractiveAppManagerService.this;
                        SessionState sessionState = this.mSessionState;
                        tvInteractiveAppManagerService.removeSessionStateLocked$1(
                                sessionState.mUserId, sessionState.mSessionToken);
                        TvInteractiveAppManagerService tvInteractiveAppManagerService2 =
                                TvInteractiveAppManagerService.this;
                        SessionState sessionState2 = this.mSessionState;
                        ITvInteractiveAppClient iTvInteractiveAppClient = sessionState2.mClient;
                        String str = sessionState2.mIAppServiceId;
                        int i = sessionState2.mSeq;
                        tvInteractiveAppManagerService2.getClass();
                        TvInteractiveAppManagerService.sendSessionTokenToClientLocked(
                                iTvInteractiveAppClient, str, null, null, i);
                    } else {
                        TvInteractiveAppManagerService tvInteractiveAppManagerService3 =
                                TvInteractiveAppManagerService.this;
                        SessionState sessionState3 = this.mSessionState;
                        ITvInteractiveAppClient iTvInteractiveAppClient2 = sessionState3.mClient;
                        String str2 = sessionState3.mIAppServiceId;
                        IBinder iBinder = sessionState3.mSessionToken;
                        InputChannel inputChannel = this.mInputChannels[0];
                        int i2 = sessionState3.mSeq;
                        tvInteractiveAppManagerService3.getClass();
                        TvInteractiveAppManagerService.sendSessionTokenToClientLocked(
                                iTvInteractiveAppClient2, str2, iBinder, inputChannel, i2);
                    }
                    this.mInputChannels[0].dispose();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onSessionStateChanged(int i, int i2) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onSessionStateChanged(i, i2, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onSessionStateChanged", e);
                }
            }
        }

        public final void onSetTvRecordingInfo(String str, TvRecordingInfo tvRecordingInfo) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onSetTvRecordingInfo(
                            str, tvRecordingInfo, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onSetTvRecordingInfo", e);
                }
            }
        }

        public final void onSetVideoBounds(Rect rect) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onSetVideoBounds(rect, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in onSetVideoBounds", e);
                }
            }
        }

        public final void onTeletextAppStateChanged(int i) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onTeletextAppStateChanged(i, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onTeletextAppStateChanged",
                            e);
                }
            }
        }

        public final void onTimeShiftCommandRequest(String str, Bundle bundle) {
            ITvInteractiveAppClient iTvInteractiveAppClient;
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                SessionState sessionState = this.mSessionState;
                if (sessionState.mSession == null
                        || (iTvInteractiveAppClient = sessionState.mClient) == null) {
                    return;
                }
                try {
                    iTvInteractiveAppClient.onTimeShiftCommandRequest(
                            str, bundle, sessionState.mSeq);
                } catch (RemoteException e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "error in onTimeShiftCommandRequest",
                            e);
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
        public final int mCallingUid;
        public final ITvInteractiveAppClient mClient;
        public final ComponentName mComponent;
        public final String mIAppServiceId;
        public final int mSeq;
        public ITvInteractiveAppSession mSession;
        public final IBinder mSessionToken;
        public final int mType;
        public final int mUserId;

        public SessionState(
                IBinder iBinder,
                String str,
                int i,
                ComponentName componentName,
                ITvInteractiveAppClient iTvInteractiveAppClient,
                int i2,
                int i3,
                int i4) {
            this.mSessionToken = iBinder;
            this.mIAppServiceId = str;
            this.mComponent = componentName;
            this.mType = i;
            this.mClient = iTvInteractiveAppClient;
            this.mSeq = i2;
            this.mCallingUid = i3;
            this.mUserId = i4;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (TvInteractiveAppManagerService.this.mLock) {
                this.mSession = null;
                TvInteractiveAppManagerService.this.clearSessionAndNotifyClientLocked(this);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TvAdBinderService extends ITvAdManager.Stub {
        public TvAdBinderService() {}

        public final void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "createMediaView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService.getAdSessionLocked(
                                        TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                                callingUid,
                                                iBinder,
                                                m994$$Nest$mresolveCallingUserId))
                                .createMediaView(iBinder2, rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in createMediaView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void createSession(
                ITvAdClient iTvAdClient, String str, String str2, int i, int i2) {
            long j;
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "createSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    synchronized (TvInteractiveAppManagerService.this.mLock) {
                        try {
                            TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                    TvInteractiveAppManagerService.this;
                            if (i2 != tvInteractiveAppManagerService.mCurrentUserId) {
                                if (!((HashSet) tvInteractiveAppManagerService.mRunningProfiles)
                                        .contains(Integer.valueOf(i2))) {
                                    TvInteractiveAppManagerService.this.getClass();
                                    TvInteractiveAppManagerService.sendAdSessionTokenToClientLocked(
                                            iTvAdClient, str, null, null, i);
                                    Binder.restoreCallingIdentity(clearCallingIdentity);
                                    return;
                                }
                            }
                            UserState orCreateUserStateLocked =
                                    TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                            m994$$Nest$mresolveCallingUserId);
                            TvAdServiceState tvAdServiceState =
                                    (TvAdServiceState)
                                            orCreateUserStateLocked.mAdServiceMap.get(str);
                            if (tvAdServiceState == null) {
                                Slogf.w(
                                        "TvInteractiveAppManagerService",
                                        "Failed to find state for serviceId=" + str);
                                TvInteractiveAppManagerService.this.getClass();
                                TvInteractiveAppManagerService.sendAdSessionTokenToClientLocked(
                                        iTvAdClient, str, null, null, i);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            AdServiceState adServiceState =
                                    (AdServiceState)
                                            ((HashMap) orCreateUserStateLocked.mAdServiceStateMap)
                                                    .get(tvAdServiceState.mComponentName);
                            if (adServiceState == null) {
                                int i3 =
                                        PackageManager.getApplicationInfoAsUserCached(
                                                        tvAdServiceState.mComponentName
                                                                .getPackageName(),
                                                        0L,
                                                        m994$$Nest$mresolveCallingUserId)
                                                .uid;
                                adServiceState =
                                        new AdServiceState(
                                                TvInteractiveAppManagerService.this,
                                                tvAdServiceState.mComponentName,
                                                m994$$Nest$mresolveCallingUserId);
                                ((HashMap) orCreateUserStateLocked.mAdServiceStateMap)
                                        .put(tvAdServiceState.mComponentName, adServiceState);
                            }
                            AdServiceState adServiceState2 = adServiceState;
                            if (adServiceState2.mReconnecting) {
                                TvInteractiveAppManagerService.this.getClass();
                                TvInteractiveAppManagerService.sendAdSessionTokenToClientLocked(
                                        iTvAdClient, str, null, null, i);
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return;
                            }
                            Binder binder = new Binder();
                            ((HashMap) orCreateUserStateLocked.mAdSessionStateMap)
                                    .put(
                                            binder,
                                            TvInteractiveAppManagerService.this
                                            .new AdSessionState(
                                                    binder,
                                                    str,
                                                    str2,
                                                    tvAdServiceState.mComponentName,
                                                    iTvAdClient,
                                                    i,
                                                    callingUid,
                                                    m994$$Nest$mresolveCallingUserId));
                            ((ArrayList) adServiceState2.mSessionTokens).add(binder);
                            ITvAdService iTvAdService = adServiceState2.mService;
                            if (iTvAdService == null) {
                                TvInteractiveAppManagerService.this.updateAdServiceConnectionLocked(
                                        m994$$Nest$mresolveCallingUserId,
                                        tvAdServiceState.mComponentName);
                            } else if (!TvInteractiveAppManagerService
                                    .m989$$Nest$mcreateAdSessionInternalLocked(
                                            TvInteractiveAppManagerService.this,
                                            iTvAdService,
                                            binder,
                                            m994$$Nest$mresolveCallingUserId)) {
                                TvInteractiveAppManagerService.this.removeAdSessionStateLocked(
                                        m994$$Nest$mresolveCallingUserId, binder);
                            }
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
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i4,
                            "dispatchSurfaceChanged");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .dispatchSurfaceChanged(i, i2, i3);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in dispatchSurfaceChanged",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final List getTvAdServiceList(int i) {
            ArrayList arrayList;
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "getTvAdServiceList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                TvInteractiveAppManagerService.this;
                        if (!tvInteractiveAppManagerService.mGetAdServiceListCalled) {
                            tvInteractiveAppManagerService.buildTvAdServiceListLocked(i, null);
                            TvInteractiveAppManagerService.this.mGetAdServiceListCalled = true;
                        }
                        UserState orCreateUserStateLocked =
                                TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                        m994$$Nest$mresolveCallingUserId);
                        arrayList = new ArrayList();
                        Iterator it = orCreateUserStateLocked.mAdServiceMap.values().iterator();
                        while (it.hasNext()) {
                            arrayList.add(((TvAdServiceState) it.next()).mInfo);
                        }
                    } finally {
                    }
                }
                return arrayList;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyError(IBinder iBinder, String str, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyError");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .notifyError(str, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in notifyError", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTvInputSessionData(
                IBinder iBinder, String str, Bundle bundle, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "notifyTvInputSessionData");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .notifyTvInputSessionData(str, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in notifyTvInputSessionData",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i2,
                            "notifyTvMessage");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .notifyTvMessage(i, bundle);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in notifyTvMessage", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void registerCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "registerCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        if (!TvInteractiveAppManagerService.this
                                .getOrCreateUserStateLocked(m994$$Nest$mresolveCallingUserId)
                                .mAdCallbacks
                                .register(iTvAdManagerCallback)) {
                            Slog.e(
                                    "TvInteractiveAppManagerService",
                                    "client process has already died");
                        }
                    } finally {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void relayoutMediaView(IBinder iBinder, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "relayoutMediaView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService.getAdSessionLocked(
                                        TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                                callingUid,
                                                iBinder,
                                                m994$$Nest$mresolveCallingUserId))
                                .relayoutMediaView(rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in relayoutMediaView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void releaseSession(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "releaseSession");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    TvInteractiveAppManagerService.m993$$Nest$mreleaseSessionLocked(
                            TvInteractiveAppManagerService.this,
                            iBinder,
                            callingUid,
                            m994$$Nest$mresolveCallingUserId);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void removeMediaView(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "removeMediaView");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        TvInteractiveAppManagerService.getAdSessionLocked(
                                        TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                                callingUid,
                                                iBinder,
                                                m994$$Nest$mresolveCallingUserId))
                                .removeMediaView();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in removeMediaView", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void resetAdService(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "resetAdService");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .resetAdService();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in reset", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendAppLinkCommand(String str, Bundle bundle, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "sendAppLinkCommand");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                } catch (RemoteException e) {
                    Slogf.e("TvInteractiveAppManagerService", "error in sendAppLinkCommand", e);
                }
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    UserState orCreateUserStateLocked =
                            TvInteractiveAppManagerService.this.getOrCreateUserStateLocked(
                                    m994$$Nest$mresolveCallingUserId);
                    TvAdServiceState tvAdServiceState =
                            (TvAdServiceState) orCreateUserStateLocked.mAdServiceMap.get(str);
                    if (tvAdServiceState == null) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "failed to sendAppLinkCommand - unknown service id " + str);
                        return;
                    }
                    ComponentName component = tvAdServiceState.mInfo.getComponent();
                    AdServiceState adServiceState =
                            (AdServiceState)
                                    ((HashMap) orCreateUserStateLocked.mAdServiceStateMap)
                                            .get(component);
                    if (adServiceState == null) {
                        AdServiceState adServiceState2 =
                                new AdServiceState(
                                        TvInteractiveAppManagerService.this,
                                        component,
                                        m994$$Nest$mresolveCallingUserId);
                        ((ArrayList) adServiceState2.mPendingAppLinkCommand).add(bundle);
                        ((HashMap) orCreateUserStateLocked.mAdServiceStateMap)
                                .put(component, adServiceState2);
                        TvInteractiveAppManagerService.this.updateAdServiceConnectionLocked(
                                m994$$Nest$mresolveCallingUserId, component);
                    } else {
                        ITvAdService iTvAdService = adServiceState.mService;
                        if (iTvAdService != null) {
                            iTvAdService.sendAppLinkCommand(bundle);
                        } else {
                            ((ArrayList) adServiceState.mPendingAppLinkCommand).add(bundle);
                            TvInteractiveAppManagerService.this.updateAdServiceConnectionLocked(
                                    m994$$Nest$mresolveCallingUserId, component);
                        }
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendCurrentChannelUri");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .sendCurrentChannelUri(uri);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendCurrentChannelUri",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCurrentTvInputId(IBinder iBinder, String str, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendCurrentTvInputId");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .sendCurrentTvInputId(str);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendCurrentTvInputId",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendCurrentVideoBounds");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .sendCurrentVideoBounds(rect);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "error in sendCurrentVideoBounds",
                                e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendSigningResult");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .sendSigningResult(str, bArr);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in sendSigningResult", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void sendTrackInfoList(IBinder iBinder, List list, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "sendTrackInfoList");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .sendTrackInfoList(list);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in sendTrackInfoList", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void setSurface(IBinder iBinder, Surface surface, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "setSurface");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .setSurface(surface);
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in setSurface", e);
                    }
                }
            } finally {
                if (surface != null) {
                    surface.release();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void startAdService(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "startAdService");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .startAdService();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in start", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void stopAdService(IBinder iBinder, int i) {
            int callingUid = Binder.getCallingUid();
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            callingUid,
                            i,
                            "stopAdService");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    try {
                        AdSessionState adSessionStateLocked =
                                TvInteractiveAppManagerService.this.getAdSessionStateLocked(
                                        callingUid, iBinder, m994$$Nest$mresolveCallingUserId);
                        TvInteractiveAppManagerService.this.getClass();
                        TvInteractiveAppManagerService.getAdSessionLocked(adSessionStateLocked)
                                .stopAdService();
                    } catch (RemoteException | SessionNotFoundException e) {
                        Slogf.e("TvInteractiveAppManagerService", "error in stop", e);
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void unregisterCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) {
            int m994$$Nest$mresolveCallingUserId =
                    TvInteractiveAppManagerService.m994$$Nest$mresolveCallingUserId(
                            TvInteractiveAppManagerService.this,
                            Binder.getCallingPid(),
                            Binder.getCallingUid(),
                            i,
                            "unregisterCallback");
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (TvInteractiveAppManagerService.this.mLock) {
                    TvInteractiveAppManagerService.this
                            .getOrCreateUserStateLocked(m994$$Nest$mresolveCallingUserId)
                            .mAdCallbacks
                            .unregister(iTvAdManagerCallback);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TvAdServiceState {
        public ComponentName mComponentName;
        public TvAdServiceInfo mInfo;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TvInteractiveAppState {
        public ComponentName mComponentName;
        public TvInteractiveAppServiceInfo mInfo;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserState {
        public final Map mAdServiceStateMap = new HashMap();
        public final Map mAdSessionStateMap = new HashMap();
        public Map mIAppMap = new HashMap();
        public Map mAdServiceMap = new HashMap();
        public final Map mClientStateMap = new HashMap();
        public final Map mServiceStateMap = new HashMap();
        public final Map mSessionStateMap = new HashMap();
        public final Set mPackageSet = new HashSet();
        public final List mAppLinkInfoList = new ArrayList();
        public final RemoteCallbackList mAdCallbacks = new RemoteCallbackList();
        public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
    }

    /* renamed from: -$$Nest$mcreateAdSessionInternalLocked, reason: not valid java name */
    public static boolean m989$$Nest$mcreateAdSessionInternalLocked(
            TvInteractiveAppManagerService tvInteractiveAppManagerService,
            ITvAdService iTvAdService,
            IBinder iBinder,
            int i) {
        boolean z;
        AdSessionState adSessionState =
                (AdSessionState)
                        ((HashMap)
                                        tvInteractiveAppManagerService.getOrCreateUserStateLocked(i)
                                                .mAdSessionStateMap)
                                .get(iBinder);
        InputChannel[] openInputChannelPair = InputChannel.openInputChannelPair(iBinder.toString());
        try {
            iTvAdService.createSession(
                    openInputChannelPair[1],
                    tvInteractiveAppManagerService
                    .new AdSessionCallback(adSessionState, openInputChannelPair),
                    adSessionState.mAdServiceId,
                    adSessionState.mType);
            z = true;
        } catch (RemoteException e) {
            Slogf.e("TvInteractiveAppManagerService", "error in createSession", e);
            sendAdSessionTokenToClientLocked(
                    adSessionState.mClient,
                    adSessionState.mAdServiceId,
                    null,
                    null,
                    adSessionState.mSeq);
            z = false;
        }
        openInputChannelPair[1].dispose();
        return z;
    }

    /* renamed from: -$$Nest$mcreateSessionInternalLocked, reason: not valid java name */
    public static boolean m990$$Nest$mcreateSessionInternalLocked(
            TvInteractiveAppManagerService tvInteractiveAppManagerService,
            ITvInteractiveAppService iTvInteractiveAppService,
            IBinder iBinder,
            int i) {
        boolean z;
        SessionState sessionState =
                (SessionState)
                        ((HashMap)
                                        tvInteractiveAppManagerService.getOrCreateUserStateLocked(i)
                                                .mSessionStateMap)
                                .get(iBinder);
        InputChannel[] openInputChannelPair = InputChannel.openInputChannelPair(iBinder.toString());
        try {
            iTvInteractiveAppService.createSession(
                    openInputChannelPair[1],
                    tvInteractiveAppManagerService
                    .new SessionCallback(sessionState, openInputChannelPair),
                    sessionState.mIAppServiceId,
                    sessionState.mType);
            z = true;
        } catch (RemoteException e) {
            Slogf.e("TvInteractiveAppManagerService", "error in createSession", e);
            sendSessionTokenToClientLocked(
                    sessionState.mClient,
                    sessionState.mIAppServiceId,
                    null,
                    null,
                    sessionState.mSeq);
            z = false;
        }
        openInputChannelPair[1].dispose();
        return z;
    }

    /* renamed from: -$$Nest$mgetServiceStateLocked, reason: not valid java name */
    public static ServiceState m991$$Nest$mgetServiceStateLocked(
            TvInteractiveAppManagerService tvInteractiveAppManagerService,
            ComponentName componentName,
            int i) {
        ServiceState serviceState =
                (ServiceState)
                        ((HashMap)
                                        tvInteractiveAppManagerService.getOrCreateUserStateLocked(i)
                                                .mServiceStateMap)
                                .get(componentName);
        if (serviceState != null) {
            return serviceState;
        }
        throw new IllegalStateException(
                "Service state not found for " + componentName + " (userId=" + i + ")");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0030, code lost:

       if (r6 == null) goto L20;
    */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
    /* renamed from: -$$Nest$mreleaseAdSessionLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m992$$Nest$mreleaseAdSessionLocked(
            com.android.server.tv.interactive.TvInteractiveAppManagerService r4,
            android.os.IBinder r5,
            int r6,
            int r7) {
        /*
            r4.getClass()
            r0 = 0
            com.android.server.tv.interactive.TvInteractiveAppManagerService$AdSessionState r6 = r4.getAdSessionStateLocked(r6, r5, r7)     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L27
            r4.getOrCreateUserStateLocked(r7)     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            android.media.tv.ad.ITvAdSession r1 = r6.mSession     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            if (r1 == 0) goto L21
            android.os.IBinder r1 = r1.asBinder()     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            r2 = 0
            r1.unlinkToDeath(r6, r2)     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            android.media.tv.ad.ITvAdSession r1 = r6.mSession     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            r1.release()     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            goto L21
        L1d:
            r4 = move-exception
            goto L37
        L1f:
            r1 = move-exception
            goto L29
        L21:
            r6.mSession = r0
            goto L33
        L24:
            r4 = move-exception
            r6 = r0
            goto L37
        L27:
            r1 = move-exception
            r6 = r0
        L29:
            java.lang.String r2 = "TvInteractiveAppManagerService"
            java.lang.String r3 = "error in releaseSession"
            com.android.server.utils.Slogf.e(r2, r3, r1)     // Catch: java.lang.Throwable -> L1d
            if (r6 == 0) goto L33
            goto L21
        L33:
            r4.removeAdSessionStateLocked(r7, r5)
            return
        L37:
            if (r6 == 0) goto L3b
            r6.mSession = r0
        L3b:
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.tv.interactive.TvInteractiveAppManagerService.m992$$Nest$mreleaseAdSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService,"
                    + " android.os.IBinder, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0030, code lost:

       if (r6 == null) goto L20;
    */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
    /* renamed from: -$$Nest$mreleaseSessionLocked, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m993$$Nest$mreleaseSessionLocked(
            com.android.server.tv.interactive.TvInteractiveAppManagerService r4,
            android.os.IBinder r5,
            int r6,
            int r7) {
        /*
            r4.getClass()
            r0 = 0
            com.android.server.tv.interactive.TvInteractiveAppManagerService$SessionState r6 = r4.getSessionStateLocked(r6, r5, r7)     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L27
            r4.getOrCreateUserStateLocked(r7)     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            android.media.tv.interactive.ITvInteractiveAppSession r1 = r6.mSession     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            if (r1 == 0) goto L21
            android.os.IBinder r1 = r1.asBinder()     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            r2 = 0
            r1.unlinkToDeath(r6, r2)     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            android.media.tv.interactive.ITvInteractiveAppSession r1 = r6.mSession     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            r1.release()     // Catch: java.lang.Throwable -> L1d java.lang.Throwable -> L1f
            goto L21
        L1d:
            r4 = move-exception
            goto L37
        L1f:
            r1 = move-exception
            goto L29
        L21:
            r6.mSession = r0
            goto L33
        L24:
            r4 = move-exception
            r6 = r0
            goto L37
        L27:
            r1 = move-exception
            r6 = r0
        L29:
            java.lang.String r2 = "TvInteractiveAppManagerService"
            java.lang.String r3 = "error in releaseSession"
            com.android.server.utils.Slogf.e(r2, r3, r1)     // Catch: java.lang.Throwable -> L1d
            if (r6 == 0) goto L33
            goto L21
        L33:
            r4.removeSessionStateLocked$1(r7, r5)
            return
        L37:
            if (r6 == 0) goto L3b
            r6.mSession = r0
        L3b:
            throw r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.tv.interactive.TvInteractiveAppManagerService.m993$$Nest$mreleaseSessionLocked(com.android.server.tv.interactive.TvInteractiveAppManagerService,"
                    + " android.os.IBinder, int, int):void");
    }

    /* renamed from: -$$Nest$mresolveCallingUserId, reason: not valid java name */
    public static int m994$$Nest$mresolveCallingUserId(
            TvInteractiveAppManagerService tvInteractiveAppManagerService,
            int i,
            int i2,
            int i3,
            String str) {
        tvInteractiveAppManagerService.getClass();
        return ActivityManager.handleIncomingUser(i, i2, i3, false, false, str, null);
    }

    public TvInteractiveAppManagerService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mCurrentUserId = 0;
        this.mRunningProfiles = new HashSet();
        this.mUserStates = new SparseArray();
        this.mGetServiceListCalled = false;
        this.mGetAdServiceListCalled = false;
        this.mGetAppLinkInfoListCalled = false;
        this.mContext = context;
        this.mUserManager = (UserManager) getContext().getSystemService("user");
    }

    public static ITvAdSession getAdSessionLocked(AdSessionState adSessionState) {
        ITvAdSession iTvAdSession = adSessionState.mSession;
        if (iTvAdSession != null) {
            return iTvAdSession;
        }
        throw new IllegalStateException(
                "Session not yet created for token " + adSessionState.mSessionToken);
    }

    public static ITvInteractiveAppSession getSessionLocked(SessionState sessionState) {
        ITvInteractiveAppSession iTvInteractiveAppSession = sessionState.mSession;
        if (iTvInteractiveAppSession != null) {
            return iTvInteractiveAppSession;
        }
        throw new IllegalStateException(
                "Session not yet created for token " + sessionState.mSessionToken);
    }

    public static void sendAdSessionTokenToClientLocked(
            ITvAdClient iTvAdClient,
            String str,
            IBinder iBinder,
            InputChannel inputChannel,
            int i) {
        try {
            iTvAdClient.onSessionCreated(str, iBinder, inputChannel, i);
        } catch (RemoteException e) {
            Slogf.e("TvInteractiveAppManagerService", "error in onSessionCreated", e);
        }
    }

    public static void sendSessionTokenToClientLocked(
            ITvInteractiveAppClient iTvInteractiveAppClient,
            String str,
            IBinder iBinder,
            InputChannel inputChannel,
            int i) {
        try {
            iTvInteractiveAppClient.onSessionCreated(str, iBinder, inputChannel, i);
        } catch (RemoteException e) {
            Slogf.e("TvInteractiveAppManagerService", "error in onSessionCreated", e);
        }
    }

    public final void abortPendingCreateAdSessionRequestsLocked(
            AdServiceState adServiceState, String str, int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) adServiceState.mSessionTokens).iterator();
        while (it.hasNext()) {
            AdSessionState adSessionState =
                    (AdSessionState)
                            ((HashMap) orCreateUserStateLocked.mAdSessionStateMap)
                                    .get((IBinder) it.next());
            if (adSessionState.mSession == null && adSessionState.mAdServiceId.equals(str)) {
                arrayList.add(adSessionState);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            AdSessionState adSessionState2 = (AdSessionState) it2.next();
            removeAdSessionStateLocked(adSessionState2.mUserId, adSessionState2.mSessionToken);
            sendAdSessionTokenToClientLocked(
                    adSessionState2.mClient,
                    adSessionState2.mAdServiceId,
                    null,
                    null,
                    adSessionState2.mSeq);
        }
        updateAdServiceConnectionLocked(i, adServiceState.mComponent);
    }

    public final void abortPendingCreateSessionRequestsLocked(
            ServiceState serviceState, String str, int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) serviceState.mSessionTokens).iterator();
        while (it.hasNext()) {
            SessionState sessionState =
                    (SessionState)
                            ((HashMap) orCreateUserStateLocked.mSessionStateMap)
                                    .get((IBinder) it.next());
            if (sessionState.mSession == null
                    && (str == null || sessionState.mIAppServiceId.equals(str))) {
                arrayList.add(sessionState);
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            SessionState sessionState2 = (SessionState) it2.next();
            removeSessionStateLocked$1(sessionState2.mUserId, sessionState2.mSessionToken);
            sendSessionTokenToClientLocked(
                    sessionState2.mClient,
                    sessionState2.mIAppServiceId,
                    null,
                    null,
                    sessionState2.mSeq);
        }
        updateServiceConnectionLocked$1(i, serviceState.mComponent);
    }

    public final void buildAppLinkInfoLocked(int i) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        List<ApplicationInfo> installedApplicationsAsUser =
                this.mContext
                        .getPackageManager()
                        .getInstalledApplicationsAsUser(
                                PackageManager.ApplicationInfoFlags.of(128L), i);
        ArrayList arrayList = new ArrayList();
        for (ApplicationInfo applicationInfo : installedApplicationsAsUser) {
            Bundle bundle = applicationInfo.metaData;
            AppLinkInfo appLinkInfo = null;
            if (bundle != null && applicationInfo.packageName != null) {
                String string =
                        bundle.getString(
                                "android.media.tv.interactive.AppLinkInfo.ClassName", null);
                String string2 =
                        applicationInfo.metaData.getString(
                                "android.media.tv.interactive.AppLinkInfo.Uri", null);
                if (string != null && string2 != null) {
                    appLinkInfo = new AppLinkInfo(applicationInfo.packageName, string, string2);
                }
            }
            if (appLinkInfo != null) {
                arrayList.add(appLinkInfo);
            }
        }
        Collections.sort(
                arrayList,
                Comparator.comparing(
                        new TvInteractiveAppManagerService$$ExternalSyntheticLambda0(2)));
        ((ArrayList) orCreateUserStateLocked.mAppLinkInfoList).clear();
        ((ArrayList) orCreateUserStateLocked.mAppLinkInfoList).addAll(arrayList);
    }

    public final void buildTvAdServiceListLocked(int i, String[] strArr) {
        if (Flags.enableAdServiceFw()) {
            UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
            ((HashSet) orCreateUserStateLocked.mPackageSet).clear();
            List queryIntentServicesAsUser =
                    this.mContext
                            .getPackageManager()
                            .queryIntentServicesAsUser(
                                    new Intent("android.media.tv.ad.TvAdService"), 132, i);
            ArrayList arrayList = new ArrayList();
            Iterator it = queryIntentServicesAsUser.iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                if ("android.permission.BIND_TV_AD_SERVICE".equals(serviceInfo.permission)) {
                    try {
                        arrayList.add(
                                new TvAdServiceInfo(
                                        this.mContext,
                                        new ComponentName(
                                                serviceInfo.packageName, serviceInfo.name)));
                        ((HashSet) orCreateUserStateLocked.mPackageSet)
                                .add(serviceInfo.packageName);
                    } catch (Exception e) {
                        Slogf.e(
                                "TvInteractiveAppManagerService",
                                "failed to load TV AD service " + serviceInfo.name,
                                e);
                    }
                } else {
                    ProfileService$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Skipping TV AD service "),
                            serviceInfo.name,
                            ": it does not require the permission"
                                + " android.permission.BIND_TV_AD_SERVICE",
                            "TvInteractiveAppManagerService");
                }
            }
            Collections.sort(
                    arrayList,
                    Comparator.comparing(
                            new TvInteractiveAppManagerService$$ExternalSyntheticLambda0(0)));
            HashMap hashMap = new HashMap();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                TvAdServiceInfo tvAdServiceInfo = (TvAdServiceInfo) it2.next();
                String id = tvAdServiceInfo.getId();
                TvAdServiceState tvAdServiceState =
                        (TvAdServiceState) orCreateUserStateLocked.mAdServiceMap.get(id);
                if (tvAdServiceState == null) {
                    tvAdServiceState = new TvAdServiceState();
                }
                tvAdServiceState.mInfo = tvAdServiceInfo;
                try {
                    int i2 =
                            getContext()
                                    .getPackageManager()
                                    .getApplicationInfo(
                                            tvAdServiceInfo.getServiceInfo().packageName, 0)
                                    .uid;
                } catch (PackageManager.NameNotFoundException e2) {
                    Slogf.w(
                            "TvInteractiveAppManagerService",
                            "Unable to get UID for  " + tvAdServiceInfo,
                            e2);
                }
                tvAdServiceState.mComponentName = tvAdServiceInfo.getComponent();
                hashMap.put(id, tvAdServiceState);
            }
            for (String str : hashMap.keySet()) {
                if (!orCreateUserStateLocked.mAdServiceMap.containsKey(str)) {
                    int beginBroadcast = orCreateUserStateLocked.mAdCallbacks.beginBroadcast();
                    for (int i3 = 0; i3 < beginBroadcast; i3++) {
                        try {
                            orCreateUserStateLocked
                                    .mAdCallbacks
                                    .getBroadcastItem(i3)
                                    .onAdServiceAdded(str);
                        } catch (RemoteException e3) {
                            Slog.e(
                                    "TvInteractiveAppManagerService",
                                    "failed to report added AD service to callback",
                                    e3);
                        }
                    }
                    orCreateUserStateLocked.mAdCallbacks.finishBroadcast();
                } else if (strArr != null) {
                    ComponentName component =
                            ((TvAdServiceState) hashMap.get(str)).mInfo.getComponent();
                    int length = strArr.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 < length) {
                            if (component.getPackageName().equals(strArr[i4])) {
                                updateAdServiceConnectionLocked(i, component);
                                int beginBroadcast2 =
                                        orCreateUserStateLocked.mAdCallbacks.beginBroadcast();
                                for (int i5 = 0; i5 < beginBroadcast2; i5++) {
                                    try {
                                        orCreateUserStateLocked
                                                .mAdCallbacks
                                                .getBroadcastItem(i5)
                                                .onAdServiceUpdated(str);
                                    } catch (RemoteException e4) {
                                        Slog.e(
                                                "TvInteractiveAppManagerService",
                                                "failed to report updated AD service to callback",
                                                e4);
                                    }
                                }
                                orCreateUserStateLocked.mAdCallbacks.finishBroadcast();
                            } else {
                                i4++;
                            }
                        }
                    }
                }
            }
            for (String str2 : orCreateUserStateLocked.mAdServiceMap.keySet()) {
                if (!hashMap.containsKey(str2)) {
                    AdServiceState adServiceState =
                            (AdServiceState)
                                    ((HashMap) orCreateUserStateLocked.mAdServiceStateMap)
                                            .get(
                                                    ((TvAdServiceState)
                                                                    orCreateUserStateLocked
                                                                            .mAdServiceMap.get(
                                                                            str2))
                                                            .mInfo.getComponent());
                    if (adServiceState != null) {
                        abortPendingCreateAdSessionRequestsLocked(adServiceState, str2, i);
                    }
                    int beginBroadcast3 = orCreateUserStateLocked.mAdCallbacks.beginBroadcast();
                    for (int i6 = 0; i6 < beginBroadcast3; i6++) {
                        try {
                            orCreateUserStateLocked
                                    .mAdCallbacks
                                    .getBroadcastItem(i6)
                                    .onAdServiceRemoved(str2);
                        } catch (RemoteException e5) {
                            Slog.e(
                                    "TvInteractiveAppManagerService",
                                    "failed to report removed AD service to callback",
                                    e5);
                        }
                    }
                    orCreateUserStateLocked.mAdCallbacks.finishBroadcast();
                }
            }
            orCreateUserStateLocked.mAdServiceMap.clear();
            orCreateUserStateLocked.mAdServiceMap = hashMap;
        }
    }

    public final void buildTvInteractiveAppServiceListLocked(int i, String[] strArr) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ((HashSet) orCreateUserStateLocked.mPackageSet).clear();
        List queryIntentServicesAsUser =
                this.mContext
                        .getPackageManager()
                        .queryIntentServicesAsUser(
                                new Intent("android.media.tv.interactive.TvInteractiveAppService"),
                                132,
                                i);
        ArrayList arrayList = new ArrayList();
        Iterator it = queryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
            if ("android.permission.BIND_TV_INTERACTIVE_APP".equals(serviceInfo.permission)) {
                try {
                    arrayList.add(
                            new TvInteractiveAppServiceInfo(
                                    this.mContext,
                                    new ComponentName(serviceInfo.packageName, serviceInfo.name)));
                    ((HashSet) orCreateUserStateLocked.mPackageSet).add(serviceInfo.packageName);
                } catch (Exception e) {
                    Slogf.e(
                            "TvInteractiveAppManagerService",
                            "failed to load TV Interactive App service " + serviceInfo.name,
                            e);
                }
            } else {
                ProfileService$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Skipping TV interactiva app service "),
                        serviceInfo.name,
                        ": it does not require the permission"
                            + " android.permission.BIND_TV_INTERACTIVE_APP",
                        "TvInteractiveAppManagerService");
            }
        }
        Collections.sort(
                arrayList,
                Comparator.comparing(
                        new TvInteractiveAppManagerService$$ExternalSyntheticLambda0(1)));
        HashMap hashMap = new HashMap();
        ArrayMap arrayMap = new ArrayMap(hashMap.size());
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            TvInteractiveAppServiceInfo tvInteractiveAppServiceInfo =
                    (TvInteractiveAppServiceInfo) it2.next();
            String id = tvInteractiveAppServiceInfo.getId();
            Integer num = (Integer) arrayMap.get(id);
            arrayMap.put(id, Integer.valueOf(num != null ? 1 + num.intValue() : 1));
            TvInteractiveAppState tvInteractiveAppState =
                    (TvInteractiveAppState) orCreateUserStateLocked.mIAppMap.get(id);
            if (tvInteractiveAppState == null) {
                tvInteractiveAppState = new TvInteractiveAppState();
            }
            tvInteractiveAppState.mInfo = tvInteractiveAppServiceInfo;
            try {
                int i2 =
                        getContext()
                                .getPackageManager()
                                .getApplicationInfo(
                                        tvInteractiveAppServiceInfo.getServiceInfo().packageName, 0)
                                .uid;
            } catch (PackageManager.NameNotFoundException e2) {
                Slogf.w(
                        "TvInteractiveAppManagerService",
                        "Unable to get UID for  " + tvInteractiveAppServiceInfo,
                        e2);
            }
            tvInteractiveAppState.mComponentName = tvInteractiveAppServiceInfo.getComponent();
            hashMap.put(id, tvInteractiveAppState);
        }
        for (String str : hashMap.keySet()) {
            if (!orCreateUserStateLocked.mIAppMap.containsKey(str)) {
                int beginBroadcast = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                for (int i3 = 0; i3 < beginBroadcast; i3++) {
                    try {
                        orCreateUserStateLocked
                                .mCallbacks
                                .getBroadcastItem(i3)
                                .onInteractiveAppServiceAdded(str);
                    } catch (RemoteException e3) {
                        Slog.e(
                                "TvInteractiveAppManagerService",
                                "failed to report added Interactive App service to callback",
                                e3);
                    }
                }
                orCreateUserStateLocked.mCallbacks.finishBroadcast();
            } else if (strArr != null) {
                ComponentName component =
                        ((TvInteractiveAppState) hashMap.get(str)).mInfo.getComponent();
                int length = strArr.length;
                int i4 = 0;
                while (true) {
                    if (i4 < length) {
                        if (component.getPackageName().equals(strArr[i4])) {
                            updateServiceConnectionLocked$1(i, component);
                            int beginBroadcast2 =
                                    orCreateUserStateLocked.mCallbacks.beginBroadcast();
                            for (int i5 = 0; i5 < beginBroadcast2; i5++) {
                                try {
                                    orCreateUserStateLocked
                                            .mCallbacks
                                            .getBroadcastItem(i5)
                                            .onInteractiveAppServiceUpdated(str);
                                } catch (RemoteException e4) {
                                    Slog.e(
                                            "TvInteractiveAppManagerService",
                                            "failed to report updated Interactive App service to"
                                                + " callback",
                                            e4);
                                }
                            }
                            orCreateUserStateLocked.mCallbacks.finishBroadcast();
                        } else {
                            i4++;
                        }
                    }
                }
            }
        }
        for (String str2 : orCreateUserStateLocked.mIAppMap.keySet()) {
            if (!hashMap.containsKey(str2)) {
                ServiceState serviceState =
                        (ServiceState)
                                ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                        .get(
                                                ((TvInteractiveAppState)
                                                                orCreateUserStateLocked.mIAppMap
                                                                        .get(str2))
                                                        .mInfo.getComponent());
                if (serviceState != null) {
                    abortPendingCreateSessionRequestsLocked(serviceState, str2, i);
                }
                int beginBroadcast3 = orCreateUserStateLocked.mCallbacks.beginBroadcast();
                for (int i6 = 0; i6 < beginBroadcast3; i6++) {
                    try {
                        orCreateUserStateLocked
                                .mCallbacks
                                .getBroadcastItem(i6)
                                .onInteractiveAppServiceRemoved(str2);
                    } catch (RemoteException e5) {
                        Slog.e(
                                "TvInteractiveAppManagerService",
                                "failed to report removed Interactive App service to callback",
                                e5);
                    }
                }
                orCreateUserStateLocked.mCallbacks.finishBroadcast();
            }
        }
        orCreateUserStateLocked.mIAppMap.clear();
        orCreateUserStateLocked.mIAppMap = hashMap;
    }

    public final void clearSessionAndNotifyClientLocked(SessionState sessionState) {
        ITvInteractiveAppClient iTvInteractiveAppClient = sessionState.mClient;
        if (iTvInteractiveAppClient != null) {
            try {
                iTvInteractiveAppClient.onSessionReleased(sessionState.mSeq);
            } catch (RemoteException e) {
                Slog.e("TvInteractiveAppManagerService", "error in onSessionReleased", e);
            }
        }
        removeAdSessionStateLocked(sessionState.mUserId, sessionState.mSessionToken);
    }

    public final AdSessionState getAdSessionStateLocked(int i, IBinder iBinder, int i2) {
        AdSessionState adSessionState =
                (AdSessionState)
                        ((HashMap) getOrCreateUserStateLocked(i2).mAdSessionStateMap).get(iBinder);
        if (adSessionState == null) {
            throw new SessionNotFoundException("Session state not found for token " + iBinder);
        }
        if (i == 1000 || i == adSessionState.mCallingUid) {
            return adSessionState;
        }
        throw new SecurityException(
                "Illegal access to the session with token " + iBinder + " from uid " + i);
    }

    public final UserState getOrCreateUserStateLocked(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked != null) {
            return userStateLocked;
        }
        UserState userState = new UserState();
        this.mUserStates.put(i, userState);
        return userState;
    }

    public final SessionState getSessionStateLocked(int i, IBinder iBinder, int i2) {
        SessionState sessionState =
                (SessionState)
                        ((HashMap) getOrCreateUserStateLocked(i2).mSessionStateMap).get(iBinder);
        if (sessionState == null) {
            throw new SessionNotFoundException("Session state not found for token " + iBinder);
        }
        if (i == 1000 || i == sessionState.mCallingUid) {
            return sessionState;
        }
        throw new SecurityException(
                "Illegal access to the session with token " + iBinder + " from uid " + i);
    }

    public final UserState getUserStateLocked(int i) {
        return (UserState) this.mUserStates.get(i);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            PackageMonitor packageMonitor =
                    new PackageMonitor() { // from class:
                                           // com.android.server.tv.interactive.TvInteractiveAppManagerService.1
                        public final void buildTvAdServiceList(String[] strArr) {
                            int changingUserId = getChangingUserId();
                            synchronized (TvInteractiveAppManagerService.this.mLock) {
                                try {
                                    TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                            TvInteractiveAppManagerService.this;
                                    if (tvInteractiveAppManagerService.mCurrentUserId
                                            != changingUserId) {
                                        if (((HashSet)
                                                        tvInteractiveAppManagerService
                                                                .mRunningProfiles)
                                                .contains(Integer.valueOf(changingUserId))) {}
                                    }
                                    TvInteractiveAppManagerService.this.buildTvAdServiceListLocked(
                                            changingUserId, strArr);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }

                        public final void buildTvInteractiveAppServiceList(String[] strArr) {
                            int changingUserId = getChangingUserId();
                            synchronized (TvInteractiveAppManagerService.this.mLock) {
                                try {
                                    TvInteractiveAppManagerService tvInteractiveAppManagerService =
                                            TvInteractiveAppManagerService.this;
                                    if (tvInteractiveAppManagerService.mCurrentUserId
                                            != changingUserId) {
                                        if (((HashSet)
                                                        tvInteractiveAppManagerService
                                                                .mRunningProfiles)
                                                .contains(Integer.valueOf(changingUserId))) {}
                                    }
                                    TvInteractiveAppManagerService.this
                                            .buildTvInteractiveAppServiceListLocked(
                                                    changingUserId, strArr);
                                    TvInteractiveAppManagerService.this.buildAppLinkInfoLocked(
                                            changingUserId);
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }

                        public final boolean onPackageChanged(String str, int i2, String[] strArr) {
                            return true;
                        }

                        public final void onPackageUpdateFinished(String str, int i2) {
                            buildTvInteractiveAppServiceList(new String[] {str});
                            buildTvAdServiceList(new String[] {str});
                        }

                        public final void onPackagesAvailable(String[] strArr) {
                            if (isReplacing()) {
                                buildTvInteractiveAppServiceList(strArr);
                                buildTvAdServiceList(strArr);
                            }
                        }

                        public final void onPackagesUnavailable(String[] strArr) {
                            if (isReplacing()) {
                                buildTvInteractiveAppServiceList(strArr);
                                buildTvAdServiceList(strArr);
                            }
                        }

                        public final void onSomePackagesChanged() {
                            if (isReplacing()) {
                                return;
                            }
                            buildTvInteractiveAppServiceList(null);
                            buildTvAdServiceList(null);
                        }
                    };
            Context context = this.mContext;
            UserHandle userHandle = UserHandle.ALL;
            packageMonitor.register(context, (Looper) null, userHandle, true);
            IntentFilter intentFilter = new IntentFilter();
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    intentFilter,
                    "android.intent.action.USER_SWITCHED",
                    "android.intent.action.USER_REMOVED",
                    "android.intent.action.USER_STARTED",
                    "android.intent.action.USER_STOPPED");
            this.mContext.registerReceiverAsUser(
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.tv.interactive.TvInteractiveAppManagerService.2
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context2, Intent intent) {
                            String action = intent.getAction();
                            if ("android.intent.action.USER_SWITCHED".equals(action)) {
                                TvInteractiveAppManagerService.this.switchUser$1(
                                        intent.getIntExtra("android.intent.extra.user_handle", 0));
                                return;
                            }
                            if (!"android.intent.action.USER_REMOVED".equals(action)) {
                                if (!"android.intent.action.USER_STARTED".equals(action)) {
                                    if ("android.intent.action.USER_STOPPED".equals(action)) {
                                        int intExtra =
                                                intent.getIntExtra(
                                                        "android.intent.extra.user_handle", 0);
                                        TvInteractiveAppManagerService
                                                tvInteractiveAppManagerService =
                                                        TvInteractiveAppManagerService.this;
                                        synchronized (tvInteractiveAppManagerService.mLock) {
                                            try {
                                                if (intExtra
                                                        == tvInteractiveAppManagerService
                                                                .mCurrentUserId) {
                                                    tvInteractiveAppManagerService.switchUser$1(
                                                            ActivityManager.getCurrentUser());
                                                } else {
                                                    tvInteractiveAppManagerService
                                                            .releaseSessionOfUserLocked$1(intExtra);
                                                    tvInteractiveAppManagerService
                                                            .unbindServiceOfUserLocked$1(intExtra);
                                                    ((HashSet)
                                                                    tvInteractiveAppManagerService
                                                                            .mRunningProfiles)
                                                            .remove(Integer.valueOf(intExtra));
                                                }
                                            } finally {
                                            }
                                        }
                                        return;
                                    }
                                    return;
                                }
                                int intExtra2 =
                                        intent.getIntExtra("android.intent.extra.user_handle", 0);
                                TvInteractiveAppManagerService tvInteractiveAppManagerService2 =
                                        TvInteractiveAppManagerService.this;
                                synchronized (tvInteractiveAppManagerService2.mLock) {
                                    try {
                                        if (intExtra2
                                                != tvInteractiveAppManagerService2.mCurrentUserId) {
                                            if (!((HashSet)
                                                            tvInteractiveAppManagerService2
                                                                    .mRunningProfiles)
                                                    .contains(Integer.valueOf(intExtra2))) {
                                                UserInfo userInfo =
                                                        tvInteractiveAppManagerService2.mUserManager
                                                                .getUserInfo(intExtra2);
                                                UserInfo profileParent =
                                                        tvInteractiveAppManagerService2.mUserManager
                                                                .getProfileParent(intExtra2);
                                                if (userInfo.isProfile()
                                                        && profileParent != null
                                                        && profileParent.id
                                                                == tvInteractiveAppManagerService2
                                                                        .mCurrentUserId) {
                                                    ((HashSet)
                                                                    tvInteractiveAppManagerService2
                                                                            .mRunningProfiles)
                                                            .add(Integer.valueOf(intExtra2));
                                                    tvInteractiveAppManagerService2
                                                            .buildTvInteractiveAppServiceListLocked(
                                                                    intExtra2, null);
                                                    tvInteractiveAppManagerService2
                                                            .buildAppLinkInfoLocked(intExtra2);
                                                    tvInteractiveAppManagerService2
                                                            .buildTvAdServiceListLocked(
                                                                    intExtra2, null);
                                                }
                                                return;
                                            }
                                        }
                                        return;
                                    } finally {
                                    }
                                }
                            }
                            TvInteractiveAppManagerService tvInteractiveAppManagerService3 =
                                    TvInteractiveAppManagerService.this;
                            int intExtra3 =
                                    intent.getIntExtra("android.intent.extra.user_handle", 0);
                            synchronized (tvInteractiveAppManagerService3.mLock) {
                                try {
                                    UserState userStateLocked =
                                            tvInteractiveAppManagerService3.getUserStateLocked(
                                                    intExtra3);
                                    if (userStateLocked == null) {
                                        return;
                                    }
                                    Iterator it =
                                            ((HashMap) userStateLocked.mSessionStateMap)
                                                    .values()
                                                    .iterator();
                                    while (it.hasNext()) {
                                        ITvInteractiveAppSession iTvInteractiveAppSession =
                                                ((SessionState) it.next()).mSession;
                                        if (iTvInteractiveAppSession != null) {
                                            try {
                                                iTvInteractiveAppSession.release();
                                            } catch (RemoteException e) {
                                                Slog.e(
                                                        "TvInteractiveAppManagerService",
                                                        "error in release",
                                                        e);
                                            }
                                        }
                                    }
                                    ((HashMap) userStateLocked.mSessionStateMap).clear();
                                    for (ServiceState serviceState :
                                            ((HashMap) userStateLocked.mServiceStateMap).values()) {
                                        ITvInteractiveAppService iTvInteractiveAppService =
                                                serviceState.mService;
                                        if (iTvInteractiveAppService != null) {
                                            ServiceCallback serviceCallback =
                                                    serviceState.mCallback;
                                            if (serviceCallback != null) {
                                                try {
                                                    iTvInteractiveAppService.unregisterCallback(
                                                            serviceCallback);
                                                } catch (RemoteException e2) {
                                                    Slog.e(
                                                            "TvInteractiveAppManagerService",
                                                            "error in unregisterCallback",
                                                            e2);
                                                }
                                            }
                                            tvInteractiveAppManagerService3.mContext.unbindService(
                                                    serviceState.mConnection);
                                        }
                                    }
                                    ((HashMap) userStateLocked.mServiceStateMap).clear();
                                    userStateLocked.mIAppMap.clear();
                                    ((HashSet) userStateLocked.mPackageSet).clear();
                                    ((HashMap) userStateLocked.mClientStateMap).clear();
                                    userStateLocked.mCallbacks.kill();
                                    ((HashSet) tvInteractiveAppManagerService3.mRunningProfiles)
                                            .remove(Integer.valueOf(intExtra3));
                                    tvInteractiveAppManagerService3.mUserStates.remove(intExtra3);
                                    if (intExtra3
                                            == tvInteractiveAppManagerService3.mCurrentUserId) {
                                        tvInteractiveAppManagerService3.switchUser$1(0);
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    },
                    userHandle,
                    intentFilter,
                    null,
                    null);
            return;
        }
        if (i == 600) {
            synchronized (this.mLock) {
                buildTvInteractiveAppServiceListLocked(this.mCurrentUserId, null);
                buildAppLinkInfoLocked(this.mCurrentUserId);
                buildTvAdServiceListLocked(this.mCurrentUserId, null);
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("tv_interactive_app", new BinderService());
        publishBinderService("tv_ad", new TvAdBinderService());
    }

    public final void releaseSessionOfUserLocked$1(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (SessionState sessionState : ((HashMap) userStateLocked.mSessionStateMap).values()) {
            if (sessionState.mSession != null) {
                arrayList.add(sessionState);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            SessionState sessionState2 = (SessionState) it.next();
            try {
                sessionState2.mSession.release();
            } catch (RemoteException e) {
                Slog.e("TvInteractiveAppManagerService", "error in release", e);
            }
            clearSessionAndNotifyClientLocked(sessionState2);
        }
    }

    public final void removeAdSessionStateLocked(int i, IBinder iBinder) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        AdSessionState adSessionState =
                (AdSessionState)
                        ((HashMap) orCreateUserStateLocked.mAdSessionStateMap).remove(iBinder);
        if (adSessionState == null) {
            Slogf.e(
                    "TvInteractiveAppManagerService",
                    "sessionState null, no more remove session action!");
            return;
        }
        ClientState clientState =
                (ClientState)
                        ((HashMap) orCreateUserStateLocked.mClientStateMap)
                                .get(adSessionState.mClient.asBinder());
        if (clientState != null) {
            ((ArrayList) clientState.mSessionTokens).remove(iBinder);
            if (((ArrayList) clientState.mSessionTokens).isEmpty()) {
                ((HashMap) orCreateUserStateLocked.mClientStateMap)
                        .remove(adSessionState.mClient.asBinder());
                adSessionState.mClient.asBinder().unlinkToDeath(clientState, 0);
            }
        }
        AdServiceState adServiceState =
                (AdServiceState)
                        ((HashMap) orCreateUserStateLocked.mAdServiceStateMap)
                                .get(adSessionState.mComponent);
        if (adServiceState != null) {
            ((ArrayList) adServiceState.mSessionTokens).remove(iBinder);
        }
        updateAdServiceConnectionLocked(i, adSessionState.mComponent);
    }

    public final void removeSessionStateLocked$1(int i, IBinder iBinder) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        SessionState sessionState =
                (SessionState) ((HashMap) orCreateUserStateLocked.mSessionStateMap).remove(iBinder);
        if (sessionState == null) {
            Slogf.e(
                    "TvInteractiveAppManagerService",
                    "sessionState null, no more remove session action!");
            return;
        }
        ClientState clientState =
                (ClientState)
                        ((HashMap) orCreateUserStateLocked.mClientStateMap)
                                .get(sessionState.mClient.asBinder());
        if (clientState != null) {
            ((ArrayList) clientState.mSessionTokens).remove(iBinder);
            if (((ArrayList) clientState.mSessionTokens).isEmpty()) {
                ((HashMap) orCreateUserStateLocked.mClientStateMap)
                        .remove(sessionState.mClient.asBinder());
                sessionState.mClient.asBinder().unlinkToDeath(clientState, 0);
            }
        }
        ServiceState serviceState =
                (ServiceState)
                        ((HashMap) orCreateUserStateLocked.mServiceStateMap)
                                .get(sessionState.mComponent);
        if (serviceState != null) {
            ((ArrayList) serviceState.mSessionTokens).remove(iBinder);
        }
        updateServiceConnectionLocked$1(i, sessionState.mComponent);
    }

    public final void switchUser$1(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mCurrentUserId == i) {
                    return;
                }
                if (this.mUserManager.getUserInfo(i).isProfile()) {
                    Slog.w("TvInteractiveAppManagerService", "cannot switch to a profile!");
                    return;
                }
                Iterator it = ((HashSet) this.mRunningProfiles).iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    releaseSessionOfUserLocked$1(intValue);
                    unbindServiceOfUserLocked$1(intValue);
                }
                ((HashSet) this.mRunningProfiles).clear();
                releaseSessionOfUserLocked$1(this.mCurrentUserId);
                unbindServiceOfUserLocked$1(this.mCurrentUserId);
                this.mCurrentUserId = i;
                buildTvInteractiveAppServiceListLocked(i, null);
                buildAppLinkInfoLocked(i);
                buildTvAdServiceListLocked(i, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindServiceOfUserLocked$1(int i) {
        UserState userStateLocked = getUserStateLocked(i);
        if (userStateLocked == null) {
            return;
        }
        Iterator it = ((HashMap) userStateLocked.mServiceStateMap).keySet().iterator();
        while (it.hasNext()) {
            ServiceState serviceState =
                    (ServiceState)
                            ((HashMap) userStateLocked.mServiceStateMap)
                                    .get((ComponentName) it.next());
            if (serviceState != null && ((ArrayList) serviceState.mSessionTokens).isEmpty()) {
                ServiceCallback serviceCallback = serviceState.mCallback;
                if (serviceCallback != null) {
                    try {
                        serviceState.mService.unregisterCallback(serviceCallback);
                    } catch (RemoteException e) {
                        Slog.e("TvInteractiveAppManagerService", "error in unregisterCallback", e);
                    }
                }
                this.mContext.unbindService(serviceState.mConnection);
                it.remove();
            }
        }
    }

    public final void updateAdServiceConnectionLocked(int i, ComponentName componentName) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        AdServiceState adServiceState =
                (AdServiceState)
                        ((HashMap) orCreateUserStateLocked.mAdServiceStateMap).get(componentName);
        if (adServiceState == null) {
            return;
        }
        if (adServiceState.mReconnecting) {
            if (!((ArrayList) adServiceState.mSessionTokens).isEmpty()) {
                return;
            } else {
                adServiceState.mReconnecting = false;
            }
        }
        boolean z =
                (((ArrayList) adServiceState.mSessionTokens).isEmpty()
                                && ((ArrayList) adServiceState.mPendingAppLinkCommand).isEmpty())
                        ? false
                        : true;
        ITvAdService iTvAdService = adServiceState.mService;
        AdServiceConnection adServiceConnection = adServiceState.mConnection;
        if (iTvAdService == null && z) {
            if (adServiceState.mBound) {
                return;
            }
            adServiceState.mBound =
                    this.mContext.bindServiceAsUser(
                            new Intent("android.media.tv.ad.TvAdService")
                                    .setComponent(componentName),
                            adServiceConnection,
                            33554433,
                            new UserHandle(i));
        } else {
            if (iTvAdService == null || z) {
                return;
            }
            this.mContext.unbindService(adServiceConnection);
            ((HashMap) orCreateUserStateLocked.mAdServiceStateMap).remove(componentName);
        }
    }

    public final void updateServiceConnectionLocked$1(int i, ComponentName componentName) {
        UserState orCreateUserStateLocked = getOrCreateUserStateLocked(i);
        ServiceState serviceState =
                (ServiceState)
                        ((HashMap) orCreateUserStateLocked.mServiceStateMap).get(componentName);
        if (serviceState == null) {
            return;
        }
        if (serviceState.mReconnecting) {
            if (!((ArrayList) serviceState.mSessionTokens).isEmpty()) {
                return;
            } else {
                serviceState.mReconnecting = false;
            }
        }
        boolean z =
                (((ArrayList) serviceState.mSessionTokens).isEmpty()
                                && ((ArrayList) serviceState.mPendingAppLinkInfo).isEmpty()
                                && ((ArrayList) serviceState.mPendingAppLinkCommand).isEmpty())
                        ? false
                        : true;
        ITvInteractiveAppService iTvInteractiveAppService = serviceState.mService;
        AdServiceConnection adServiceConnection = serviceState.mConnection;
        if (iTvInteractiveAppService == null && z) {
            if (serviceState.mBound) {
                return;
            }
            serviceState.mBound =
                    this.mContext.bindServiceAsUser(
                            new Intent("android.media.tv.interactive.TvInteractiveAppService")
                                    .setComponent(componentName),
                            adServiceConnection,
                            33554433,
                            new UserHandle(i));
        } else {
            if (iTvInteractiveAppService == null || z) {
                return;
            }
            this.mContext.unbindService(adServiceConnection);
            ((HashMap) orCreateUserStateLocked.mServiceStateMap).remove(componentName);
        }
    }
}
