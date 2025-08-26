package android.telecom;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.Connection;
import android.telecom.Logging.Session;
import android.telecom.VideoProfile;
import android.view.Surface;
import com.android.internal.telecom.IConnectionService;
import com.android.internal.telecom.IVideoCallback;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public final class RemoteConnection {
    private Uri mAddress;
    private int mAddressPresentation;
    private final Set<CallbackRecord> mCallbackRecords = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));
    private String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private String mCallingPackageAbbreviation;
    private RemoteConference mConference;
    private final List<RemoteConnection> mConferenceableConnections;
    private boolean mConnected;
    private int mConnectionCapabilities;
    private final String mConnectionId;
    private int mConnectionProperties;
    private IConnectionService mConnectionService;
    private DisconnectCause mDisconnectCause;
    private Bundle mExtras;
    private boolean mIsVoipAudioMode;
    private boolean mRingbackRequested;
    private int mState;
    private StatusHints mStatusHints;
    private final List<RemoteConnection> mUnmodifiableconferenceableConnections;
    private VideoProvider mVideoProvider;
    private int mVideoState;

    public static abstract class Callback {
        public void onAddressChanged(RemoteConnection remoteConnection, Uri uri, int i) {
        }

        public void onCallerDisplayNameChanged(RemoteConnection remoteConnection, String str, int i) {
        }

        public void onConferenceChanged(RemoteConnection remoteConnection, RemoteConference remoteConference) {
        }

        public void onConferenceableConnectionsChanged(RemoteConnection remoteConnection, List<RemoteConnection> list) {
        }

        public void onConnectionCapabilitiesChanged(RemoteConnection remoteConnection, int i) {
        }

        public void onConnectionEvent(RemoteConnection remoteConnection, String str, Bundle bundle) {
        }

        public void onConnectionPropertiesChanged(RemoteConnection remoteConnection, int i) {
        }

        public void onDestroyed(RemoteConnection remoteConnection) {
        }

        public void onDisconnected(RemoteConnection remoteConnection, DisconnectCause disconnectCause) {
        }

        public void onExtrasChanged(RemoteConnection remoteConnection, Bundle bundle) {
        }

        public void onPostDialChar(RemoteConnection remoteConnection, char c) {
        }

        public void onPostDialWait(RemoteConnection remoteConnection, String str) {
        }

        public void onRemoteRttRequest(RemoteConnection remoteConnection) {
        }

        public void onRingbackRequested(RemoteConnection remoteConnection, boolean z) {
        }

        public void onRttInitiationFailure(RemoteConnection remoteConnection, int i) {
        }

        public void onRttInitiationSuccess(RemoteConnection remoteConnection) {
        }

        public void onRttSessionRemotelyTerminated(RemoteConnection remoteConnection) {
        }

        public void onStateChanged(RemoteConnection remoteConnection, int i) {
        }

        public void onStatusHintsChanged(RemoteConnection remoteConnection, StatusHints statusHints) {
        }

        public void onVideoProviderChanged(RemoteConnection remoteConnection, VideoProvider videoProvider) {
        }

        public void onVideoStateChanged(RemoteConnection remoteConnection, int i) {
        }

        public void onVoipAudioChanged(RemoteConnection remoteConnection, boolean z) {
        }
    }

    public static class VideoProvider {
        private final Set<Callback> mCallbacks;
        private final String mCallingPackage;
        private final int mTargetSdkVersion;
        private final IVideoCallback mVideoCallbackDelegate;
        private final VideoCallbackServant mVideoCallbackServant;
        private final IVideoProvider mVideoProviderBinder;

        public static abstract class Callback {
            public void onCallDataUsageChanged(VideoProvider videoProvider, long j) {
            }

            public void onCallSessionEvent(VideoProvider videoProvider, int i) {
            }

            public void onCameraCapabilitiesChanged(VideoProvider videoProvider, VideoProfile.CameraCapabilities cameraCapabilities) {
            }

            public void onPeerDimensionsChanged(VideoProvider videoProvider, int i, int i2) {
            }

            public void onSessionModifyRequestReceived(VideoProvider videoProvider, VideoProfile videoProfile) {
            }

            public void onSessionModifyResponseReceived(VideoProvider videoProvider, int i, VideoProfile videoProfile, VideoProfile videoProfile2) {
            }

            public void onVideoQualityChanged(VideoProvider videoProvider, int i) {
            }
        }

        VideoProvider(IVideoProvider iVideoProvider, String str, int i) {
            IVideoCallback iVideoCallback = new IVideoCallback() { // from class: android.telecom.RemoteConnection.VideoProvider.1
                @Override // android.os.IInterface
                public IBinder asBinder() {
                    return null;
                }

                @Override // com.android.internal.telecom.IVideoCallback
                public void receiveSessionModifyRequest(VideoProfile videoProfile) {
                    Iterator it = VideoProvider.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onSessionModifyRequestReceived(VideoProvider.this, videoProfile);
                    }
                }

                @Override // com.android.internal.telecom.IVideoCallback
                public void receiveSessionModifyResponse(int i2, VideoProfile videoProfile, VideoProfile videoProfile2) {
                    Iterator it = VideoProvider.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onSessionModifyResponseReceived(VideoProvider.this, i2, videoProfile, videoProfile2);
                    }
                }

                @Override // com.android.internal.telecom.IVideoCallback
                public void handleCallSessionEvent(int i2) {
                    Iterator it = VideoProvider.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onCallSessionEvent(VideoProvider.this, i2);
                    }
                }

                @Override // com.android.internal.telecom.IVideoCallback
                public void changePeerDimensions(int i2, int i3) {
                    Iterator it = VideoProvider.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onPeerDimensionsChanged(VideoProvider.this, i2, i3);
                    }
                }

                @Override // com.android.internal.telecom.IVideoCallback
                public void changeCallDataUsage(long j) {
                    Iterator it = VideoProvider.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onCallDataUsageChanged(VideoProvider.this, j);
                    }
                }

                @Override // com.android.internal.telecom.IVideoCallback
                public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
                    Iterator it = VideoProvider.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onCameraCapabilitiesChanged(VideoProvider.this, cameraCapabilities);
                    }
                }

                @Override // com.android.internal.telecom.IVideoCallback
                public void changeVideoQuality(int i2) {
                    Iterator it = VideoProvider.this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((Callback) it.next()).onVideoQualityChanged(VideoProvider.this, i2);
                    }
                }
            };
            this.mVideoCallbackDelegate = iVideoCallback;
            VideoCallbackServant videoCallbackServant = new VideoCallbackServant(iVideoCallback);
            this.mVideoCallbackServant = videoCallbackServant;
            this.mCallbacks = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));
            this.mVideoProviderBinder = iVideoProvider;
            this.mCallingPackage = str;
            this.mTargetSdkVersion = i;
            try {
                iVideoProvider.addVideoCallback(videoCallbackServant.getStub().asBinder());
            } catch (RemoteException unused) {
            }
        }

        public void registerCallback(Callback callback) {
            this.mCallbacks.add(callback);
        }

        public void unregisterCallback(Callback callback) {
            this.mCallbacks.remove(callback);
        }

        public void setCamera(String str) {
            try {
                this.mVideoProviderBinder.setCamera(str, this.mCallingPackage, this.mTargetSdkVersion);
            } catch (RemoteException unused) {
            }
        }

        public void setPreviewSurface(Surface surface) {
            try {
                this.mVideoProviderBinder.setPreviewSurface(surface);
            } catch (RemoteException unused) {
            }
        }

        public void setDisplaySurface(Surface surface) {
            try {
                this.mVideoProviderBinder.setDisplaySurface(surface);
            } catch (RemoteException unused) {
            }
        }

        public void setDeviceOrientation(int i) {
            try {
                this.mVideoProviderBinder.setDeviceOrientation(i);
            } catch (RemoteException unused) {
            }
        }

        public void setZoom(float f) {
            try {
                this.mVideoProviderBinder.setZoom(f);
            } catch (RemoteException unused) {
            }
        }

        public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) {
            try {
                this.mVideoProviderBinder.sendSessionModifyRequest(videoProfile, videoProfile2);
            } catch (RemoteException unused) {
            }
        }

        public void sendSessionModifyResponse(VideoProfile videoProfile) {
            try {
                this.mVideoProviderBinder.sendSessionModifyResponse(videoProfile);
            } catch (RemoteException unused) {
            }
        }

        public void requestCameraCapabilities() {
            try {
                this.mVideoProviderBinder.requestCameraCapabilities();
            } catch (RemoteException unused) {
            }
        }

        public void requestCallDataUsage() {
            try {
                this.mVideoProviderBinder.requestCallDataUsage();
            } catch (RemoteException unused) {
            }
        }

        public void setPauseImage(Uri uri) {
            try {
                this.mVideoProviderBinder.setPauseImage(uri);
            } catch (RemoteException unused) {
            }
        }
    }

    RemoteConnection(String str, IConnectionService iConnectionService, ConnectionRequest connectionRequest) {
        ArrayList arrayList = new ArrayList();
        this.mConferenceableConnections = arrayList;
        this.mUnmodifiableconferenceableConnections = Collections.unmodifiableList(arrayList);
        this.mConnectionId = str;
        this.mConnectionService = iConnectionService;
        this.mConnected = true;
        this.mState = 0;
        if (connectionRequest == null || connectionRequest.getExtras() == null || !connectionRequest.getExtras().containsKey(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME)) {
            return;
        }
        this.mCallingPackageAbbreviation = Log.getPackageAbbreviation(connectionRequest.getExtras().getString(Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME));
    }

    RemoteConnection(String str, IConnectionService iConnectionService, ParcelableConnection parcelableConnection, String str2, int i) {
        ArrayList arrayList = new ArrayList();
        this.mConferenceableConnections = arrayList;
        this.mUnmodifiableconferenceableConnections = Collections.unmodifiableList(arrayList);
        this.mState = 1;
        this.mConnectionId = str;
        this.mConnectionService = iConnectionService;
        this.mConnected = true;
        this.mState = parcelableConnection.getState();
        this.mDisconnectCause = parcelableConnection.getDisconnectCause();
        this.mRingbackRequested = parcelableConnection.isRingbackRequested();
        this.mConnectionCapabilities = parcelableConnection.getConnectionCapabilities();
        this.mConnectionProperties = parcelableConnection.getConnectionProperties();
        this.mVideoState = parcelableConnection.getVideoState();
        IVideoProvider videoProvider = parcelableConnection.getVideoProvider();
        if (videoProvider != null) {
            this.mVideoProvider = new VideoProvider(videoProvider, str2, i);
        } else {
            this.mVideoProvider = null;
        }
        this.mIsVoipAudioMode = parcelableConnection.getIsVoipAudioMode();
        this.mStatusHints = parcelableConnection.getStatusHints();
        this.mAddress = parcelableConnection.getHandle();
        this.mAddressPresentation = parcelableConnection.getHandlePresentation();
        this.mCallerDisplayName = parcelableConnection.getCallerDisplayName();
        this.mCallerDisplayNamePresentation = parcelableConnection.getCallerDisplayNamePresentation();
        this.mConference = null;
        putExtras(parcelableConnection.getExtras());
        Bundle bundle = new Bundle();
        bundle.putString(Connection.EXTRA_ORIGINAL_CONNECTION_ID, str);
        putExtras(bundle);
        this.mCallingPackageAbbreviation = Log.getPackageAbbreviation(str2);
    }

    RemoteConnection(DisconnectCause disconnectCause) {
        ArrayList arrayList = new ArrayList();
        this.mConferenceableConnections = arrayList;
        this.mUnmodifiableconferenceableConnections = Collections.unmodifiableList(arrayList);
        this.mConnectionId = "NULL";
        this.mConnected = false;
        this.mState = 6;
        this.mDisconnectCause = disconnectCause;
    }

    public void registerCallback(Callback callback) {
        registerCallback(callback, new Handler());
    }

    public void registerCallback(Callback callback, Handler handler) {
        unregisterCallback(callback);
        if (callback == null || handler == null) {
            return;
        }
        this.mCallbackRecords.add(new CallbackRecord(callback, handler));
    }

    public void unregisterCallback(Callback callback) {
        if (callback != null) {
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                if (callbackRecord.getCallback() == callback) {
                    this.mCallbackRecords.remove(callbackRecord);
                    return;
                }
            }
        }
    }

    public int getState() {
        return this.mState;
    }

    public DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public boolean isVoipAudioMode() {
        return this.mIsVoipAudioMode;
    }

    public StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public CharSequence getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public final VideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public void abort() {
        Log.startSession("RC.a", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.abort(this.mConnectionId, Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void answer() {
        Log.startSession("RC.an", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.answer(this.mConnectionId, Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void answer(int i) {
        Log.startSession("RC.an2", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.answerVideo(this.mConnectionId, i, Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void reject() {
        Log.startSession("RC.r", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.reject(this.mConnectionId, Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void hold() {
        Log.startSession("RC.h", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.hold(this.mConnectionId, Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void unhold() {
        Log.startSession("RC.u", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.unhold(this.mConnectionId, Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void disconnect() {
        Log.startSession("RC.d", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.disconnect(this.mConnectionId, Log.getExternalSession(this.mCallingPackageAbbreviation));
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void playDtmfTone(char c) {
        Log.startSession("RC.pDT", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.playDtmfTone(this.mConnectionId, c, null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void stopDtmfTone() {
        Log.startSession("RC.sDT", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.stopDtmfTone(this.mConnectionId, null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void postDialContinue(boolean z) {
        Log.startSession("RC.pDC", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.onPostDialContinue(this.mConnectionId, z, null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void pullExternalCall() {
        Log.startSession("RC.pEC", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.pullExternalCall(this.mConnectionId, null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void addConferenceParticipants(List<Uri> list) {
        try {
            if (this.mConnected) {
                this.mConnectionService.addConferenceParticipants(this.mConnectionId, list, null);
            }
        } catch (RemoteException unused) {
        }
    }

    @SystemApi
    @Deprecated
    public void setAudioState(AudioState audioState) {
        setCallAudioState(new CallAudioState(audioState));
    }

    public void setCallAudioState(CallAudioState callAudioState) {
        Log.startSession("RC.sCAS", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.onCallAudioStateChanged(this.mConnectionId, callAudioState, null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void startRtt(Connection.RttTextStream rttTextStream) {
        Log.startSession("RC.sR", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.startRtt(this.mConnectionId, rttTextStream.getFdFromInCall(), rttTextStream.getFdToInCall(), null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void stopRtt() {
        Log.startSession("RC.stR", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.stopRtt(this.mConnectionId, null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    @SystemApi
    public void onCallFilteringCompleted(Connection.CallFilteringCompletionInfo callFilteringCompletionInfo) {
        Log.startSession("RC.oCFC", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                this.mConnectionService.onCallFilteringCompleted(this.mConnectionId, callFilteringCompletionInfo, null);
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public void sendRttUpgradeResponse(Connection.RttTextStream rttTextStream) {
        Log.startSession("RC.sRUR", getActiveOwnerInfo());
        try {
            if (this.mConnected) {
                if (rttTextStream == null) {
                    this.mConnectionService.respondToRttUpgradeRequest(this.mConnectionId, null, null, null);
                } else {
                    this.mConnectionService.respondToRttUpgradeRequest(this.mConnectionId, rttTextStream.getFdFromInCall(), rttTextStream.getFdToInCall(), null);
                }
            }
        } catch (RemoteException unused) {
        } finally {
            Log.endSession();
        }
    }

    public List<RemoteConnection> getConferenceableConnections() {
        return this.mUnmodifiableconferenceableConnections;
    }

    public RemoteConference getConference() {
        return this.mConference;
    }

    private String getActiveOwnerInfo() {
        Session.Info externalSession = Log.getExternalSession();
        if (externalSession == null) {
            return null;
        }
        return externalSession.ownerInfo;
    }

    String getId() {
        return this.mConnectionId;
    }

    IConnectionService getConnectionService() {
        return this.mConnectionService;
    }

    void setState(final int i) {
        if (this.mState != i) {
            this.mState = i;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.1
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onStateChanged(this, i);
                    }
                });
            }
        }
    }

    void setDisconnected(final DisconnectCause disconnectCause) {
        if (this.mState != 6) {
            this.mState = 6;
            this.mDisconnectCause = disconnectCause;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.2
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onDisconnected(this, disconnectCause);
                    }
                });
            }
        }
    }

    void setRingbackRequested(final boolean z) {
        if (this.mRingbackRequested != z) {
            this.mRingbackRequested = z;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.3
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onRingbackRequested(this, z);
                    }
                });
            }
        }
    }

    void setConnectionCapabilities(final int i) {
        this.mConnectionCapabilities = i;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.4
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConnectionCapabilitiesChanged(this, i);
                }
            });
        }
    }

    void setConnectionProperties(final int i) {
        this.mConnectionProperties = i;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.5
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConnectionPropertiesChanged(this, i);
                }
            });
        }
    }

    void setDestroyed() {
        if (this.mCallbackRecords.isEmpty()) {
            return;
        }
        if (this.mState != 6) {
            setDisconnected(new DisconnectCause(1, "Connection destroyed."));
        }
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.6
                @Override // java.lang.Runnable
                public void run() {
                    callback.onDestroyed(this);
                }
            });
        }
        this.mCallbackRecords.clear();
        this.mConnected = false;
    }

    void setPostDialWait(final String str) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.7
                @Override // java.lang.Runnable
                public void run() {
                    callback.onPostDialWait(this, str);
                }
            });
        }
    }

    void onPostDialChar(final char c) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.8
                @Override // java.lang.Runnable
                public void run() {
                    callback.onPostDialChar(this, c);
                }
            });
        }
    }

    void setVideoState(final int i) {
        this.mVideoState = i;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.9
                @Override // java.lang.Runnable
                public void run() {
                    callback.onVideoStateChanged(this, i);
                }
            });
        }
    }

    void setVideoProvider(final VideoProvider videoProvider) {
        this.mVideoProvider = videoProvider;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.10
                @Override // java.lang.Runnable
                public void run() {
                    callback.onVideoProviderChanged(this, videoProvider);
                }
            });
        }
    }

    void setIsVoipAudioMode(final boolean z) {
        this.mIsVoipAudioMode = z;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.11
                @Override // java.lang.Runnable
                public void run() {
                    callback.onVoipAudioChanged(this, z);
                }
            });
        }
    }

    void setStatusHints(final StatusHints statusHints) {
        this.mStatusHints = statusHints;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.12
                @Override // java.lang.Runnable
                public void run() {
                    callback.onStatusHintsChanged(this, statusHints);
                }
            });
        }
    }

    void setAddress(final Uri uri, final int i) {
        this.mAddress = uri;
        this.mAddressPresentation = i;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.13
                @Override // java.lang.Runnable
                public void run() {
                    callback.onAddressChanged(this, uri, i);
                }
            });
        }
    }

    void setCallerDisplayName(final String str, final int i) {
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.14
                @Override // java.lang.Runnable
                public void run() {
                    callback.onCallerDisplayNameChanged(this, str, i);
                }
            });
        }
    }

    void setConferenceableConnections(List<RemoteConnection> list) {
        this.mConferenceableConnections.clear();
        this.mConferenceableConnections.addAll(list);
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConnection.15
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConferenceableConnectionsChanged(this, RemoteConnection.this.mUnmodifiableconferenceableConnections);
                }
            });
        }
    }

    void setConference(final RemoteConference remoteConference) {
        if (this.mConference != remoteConference) {
            this.mConference = remoteConference;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.16
                    @Override // java.lang.Runnable
                    public void run() {
                        callback.onConferenceChanged(this, remoteConference);
                    }
                });
            }
        }
    }

    void putExtras(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        try {
            this.mExtras.putAll(bundle);
        } catch (BadParcelableException e) {
            Log.w(this, "putExtras: could not unmarshal extras; exception = " + e, new Object[0]);
        }
        notifyExtrasChanged();
    }

    void removeExtras(List<String> list) {
        if (this.mExtras == null || list == null || list.isEmpty()) {
            return;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            this.mExtras.remove(it.next());
        }
        notifyExtrasChanged();
    }

    private void notifyExtrasChanged() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConnection.17
                @Override // java.lang.Runnable
                public void run() {
                    callback.onExtrasChanged(this, RemoteConnection.this.mExtras);
                }
            });
        }
    }

    void onConnectionEvent(final String str, final Bundle bundle) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(this) { // from class: android.telecom.RemoteConnection.18
                @Override // java.lang.Runnable
                public void run() {
                    callback.onConnectionEvent(this, str, bundle);
                }
            });
        }
    }

    void onRttInitiationSuccess() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    callback.onRttInitiationSuccess(this);
                }
            });
        }
    }

    void onRttInitiationFailure(final int i) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    callback.onRttInitiationFailure(this, i);
                }
            });
        }
    }

    void onRttSessionRemotelyTerminated() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    callback.onRttSessionRemotelyTerminated(this);
                }
            });
        }
    }

    void onRemoteRttRequest() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable() { // from class: android.telecom.RemoteConnection$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    callback.onRemoteRttRequest(this);
                }
            });
        }
    }

    public static RemoteConnection failure(DisconnectCause disconnectCause) {
        return new RemoteConnection(disconnectCause);
    }

    private static final class CallbackRecord extends Callback {
        private final Callback mCallback;
        private final Handler mHandler;

        public CallbackRecord(Callback callback, Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
        }

        public Callback getCallback() {
            return this.mCallback;
        }

        public Handler getHandler() {
            return this.mHandler;
        }
    }
}
