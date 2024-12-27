package android.telecom;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telecom.Logging.Session;

import com.android.internal.telecom.IConnectionService;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import com.android.server.telecom.flags.FeatureFlags;
import com.android.server.telecom.flags.FeatureFlagsImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

final class RemoteConnectionService {
    private final ConnectionService mOurConnectionServiceImpl;
    private final IConnectionService mOutgoingConnectionServiceRpc;
    private static final RemoteConnection NULL_CONNECTION =
            new RemoteConnection("NULL", null, null);
    private static final RemoteConference NULL_CONFERENCE = new RemoteConference("NULL", null);
    private final IConnectionServiceAdapter mServantDelegate =
            new IConnectionServiceAdapter() { // from class:
                                              // android.telecom.RemoteConnectionService.1
                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void handleCreateConnectionComplete(
                        String id,
                        ConnectionRequest request,
                        ParcelableConnection parcel,
                        Session.Info info) {
                    RemoteConnection connection =
                            RemoteConnectionService.this.findConnectionForAction(
                                    id, "handleCreateConnectionSuccessful");
                    if (connection != RemoteConnectionService.NULL_CONNECTION
                            && RemoteConnectionService.this.mPendingConnections.contains(
                                    connection)) {
                        RemoteConnectionService.this.mPendingConnections.remove(connection);
                        connection.setConnectionCapabilities(parcel.getConnectionCapabilities());
                        connection.setConnectionProperties(parcel.getConnectionProperties());
                        if (parcel.getHandle() != null || parcel.getState() != 6) {
                            connection.setAddress(
                                    parcel.getHandle(), parcel.getHandlePresentation());
                        }
                        if (parcel.getCallerDisplayName() != null || parcel.getState() != 6) {
                            connection.setCallerDisplayName(
                                    parcel.getCallerDisplayName(),
                                    parcel.getCallerDisplayNamePresentation());
                        }
                        if (parcel.getState() == 6) {
                            connection.setDisconnected(parcel.getDisconnectCause());
                        } else {
                            connection.setState(parcel.getState());
                        }
                        List<RemoteConnection> conferenceable = new ArrayList<>();
                        for (String confId : parcel.getConferenceableConnectionIds()) {
                            if (RemoteConnectionService.this.mConnectionById.containsKey(confId)) {
                                conferenceable.add(
                                        (RemoteConnection)
                                                RemoteConnectionService.this.mConnectionById.get(
                                                        confId));
                            }
                        }
                        connection.setConferenceableConnections(conferenceable);
                        connection.setVideoState(parcel.getVideoState());
                        if (connection.getState() == 6) {
                            connection.setDestroyed();
                        }
                        connection.setStatusHints(parcel.getStatusHints());
                        connection.setIsVoipAudioMode(parcel.getIsVoipAudioMode());
                        connection.setRingbackRequested(parcel.isRingbackRequested());
                        connection.putExtras(parcel.getExtras());
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void handleCreateConferenceComplete(
                        String id,
                        ConnectionRequest request,
                        ParcelableConference parcel,
                        Session.Info info) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setActive(String callId, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.mConnectionById.containsKey(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "setActive")
                                .setState(4);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "setActive")
                                .setState(4);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setRinging(String callId, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setRinging")
                            .setState(2);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setDialing(String callId, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setDialing")
                            .setState(3);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setPulling(String callId, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setPulling")
                            .setState(7);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setDisconnected(
                        String callId, DisconnectCause disconnectCause, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.mConnectionById.containsKey(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "setDisconnected")
                                .setDisconnected(disconnectCause);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "setDisconnected")
                                .setDisconnected(disconnectCause);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setOnHold(String callId, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.mConnectionById.containsKey(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "setOnHold")
                                .setState(5);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "setOnHold")
                                .setState(5);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setRingbackRequested(
                        String callId, boolean ringing, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setRingbackRequested")
                            .setRingbackRequested(ringing);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setConnectionCapabilities(
                        String callId, int connectionCapabilities, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.mConnectionById.containsKey(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "setConnectionCapabilities")
                                .setConnectionCapabilities(connectionCapabilities);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "setConnectionCapabilities")
                                .setConnectionCapabilities(connectionCapabilities);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setConnectionProperties(
                        String callId, int connectionProperties, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.mConnectionById.containsKey(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "setConnectionProperties")
                                .setConnectionProperties(connectionProperties);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "setConnectionProperties")
                                .setConnectionProperties(connectionProperties);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setIsConferenced(
                        String callId, String conferenceCallId, Session.Info sessionInfo) {
                    RemoteConnection connection =
                            RemoteConnectionService.this.findConnectionForAction(
                                    callId, "setIsConferenced");
                    if (connection != RemoteConnectionService.NULL_CONNECTION) {
                        if (conferenceCallId == null) {
                            if (connection.getConference() != null) {
                                connection.getConference().removeConnection(connection);
                            }
                        } else {
                            RemoteConference conference =
                                    RemoteConnectionService.this.findConferenceForAction(
                                            conferenceCallId, "setIsConferenced");
                            if (conference != RemoteConnectionService.NULL_CONFERENCE) {
                                conference.addConnection(connection);
                            }
                        }
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setConferenceMergeFailed(String callId, Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onPhoneAccountChanged(
                        String callId, PhoneAccountHandle pHandle, Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onConnectionServiceFocusReleased(Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void addConferenceCall(
                        final String callId,
                        ParcelableConference parcel,
                        Session.Info sessionInfo) {
                    RemoteConference conference =
                            new RemoteConference(
                                    callId,
                                    RemoteConnectionService.this.mOutgoingConnectionServiceRpc);
                    for (String id : parcel.getConnectionIds()) {
                        RemoteConnection c =
                                (RemoteConnection)
                                        RemoteConnectionService.this.mConnectionById.get(id);
                        if (c != null) {
                            conference.addConnection(c);
                        }
                    }
                    conference.setState(parcel.getState());
                    conference.setConnectionCapabilities(parcel.getConnectionCapabilities());
                    conference.setConnectionProperties(parcel.getConnectionProperties());
                    conference.putExtras(parcel.getExtras());
                    RemoteConnectionService.this.mConferenceById.put(callId, conference);
                    Bundle newExtras = new Bundle();
                    newExtras.putString(Connection.EXTRA_ORIGINAL_CONNECTION_ID, callId);
                    newExtras.putParcelable(
                            Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE, parcel.getPhoneAccount());
                    conference.putExtras(newExtras);
                    conference.registerCallback(
                            new RemoteConference
                                    .Callback() { // from class:
                                                  // android.telecom.RemoteConnectionService.1.1
                                @Override // android.telecom.RemoteConference.Callback
                                public void onDestroyed(RemoteConference c2) {
                                    RemoteConnectionService.this.mConferenceById.remove(callId);
                                    RemoteConnectionService.this.maybeDisconnectAdapter();
                                }
                            });
                    RemoteConnectionService.this.mOurConnectionServiceImpl.addRemoteConference(
                            conference);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void removeCall(String callId, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.mConnectionById.containsKey(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "removeCall")
                                .setDestroyed();
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "removeCall")
                                .setDestroyed();
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onPostDialWait(
                        String callId, String remaining, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "onPostDialWait")
                            .setPostDialWait(remaining);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onPostDialChar(String callId, char nextChar, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "onPostDialChar")
                            .onPostDialChar(nextChar);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void queryRemoteConnectionServices(
                        RemoteServiceCallback callback,
                        String callingPackage,
                        Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setVideoProvider(
                        String callId, IVideoProvider videoProvider, Session.Info sessionInfo) {
                    String callingPackage =
                            RemoteConnectionService.this
                                    .mOurConnectionServiceImpl
                                    .getApplicationContext()
                                    .getOpPackageName();
                    int targetSdkVersion =
                            RemoteConnectionService.this.mOurConnectionServiceImpl
                                    .getApplicationInfo()
                                    .targetSdkVersion;
                    RemoteConnection.VideoProvider remoteVideoProvider = null;
                    if (videoProvider != null) {
                        remoteVideoProvider =
                                new RemoteConnection.VideoProvider(
                                        videoProvider, callingPackage, targetSdkVersion);
                    }
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setVideoProvider")
                            .setVideoProvider(remoteVideoProvider);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setVideoState(String callId, int videoState, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setVideoState")
                            .setVideoState(videoState);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setIsVoipAudioMode(
                        String callId, boolean isVoip, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setIsVoipAudioMode")
                            .setIsVoipAudioMode(isVoip);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setStatusHints(
                        String callId, StatusHints statusHints, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setStatusHints")
                            .setStatusHints(statusHints);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setAddress(
                        String callId, Uri address, int presentation, Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setAddress")
                            .setAddress(address, presentation);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setCallerDisplayName(
                        String callId,
                        String callerDisplayName,
                        int presentation,
                        Session.Info sessionInfo) {
                    RemoteConnectionService.this
                            .findConnectionForAction(callId, "setCallerDisplayName")
                            .setCallerDisplayName(callerDisplayName, presentation);
                }

                @Override // android.os.IInterface
                public IBinder asBinder() {
                    throw new UnsupportedOperationException();
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public final void setConferenceableConnections(
                        String callId,
                        List<String> conferenceableConnectionIds,
                        Session.Info sessionInfo) {
                    List<RemoteConnection> conferenceable = new ArrayList<>();
                    for (String id : conferenceableConnectionIds) {
                        if (RemoteConnectionService.this.mConnectionById.containsKey(id)) {
                            conferenceable.add(
                                    (RemoteConnection)
                                            RemoteConnectionService.this.mConnectionById.get(id));
                        }
                    }
                    if (RemoteConnectionService.this.hasConnection(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "setConferenceableConnections")
                                .setConferenceableConnections(conferenceable);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "setConferenceableConnections")
                                .setConferenceableConnections(conferenceable);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void addExistingConnection(
                        final String callId,
                        ParcelableConnection connection,
                        Session.Info sessionInfo) {
                    RemoteConference parentConf;
                    Log.i(
                            RemoteConnectionService.this,
                            "addExistingConnection: callId=%s, conn=%s",
                            callId,
                            connection);
                    String callingPackage =
                            RemoteConnectionService.this
                                    .mOurConnectionServiceImpl
                                    .getApplicationContext()
                                    .getOpPackageName();
                    int callingTargetSdkVersion =
                            RemoteConnectionService.this.mOurConnectionServiceImpl
                                    .getApplicationInfo()
                                    .targetSdkVersion;
                    RemoteConnection remoteConnection =
                            new RemoteConnection(
                                    callId,
                                    RemoteConnectionService.this.mOutgoingConnectionServiceRpc,
                                    connection,
                                    callingPackage,
                                    callingTargetSdkVersion);
                    Bundle newExtras = new Bundle();
                    newExtras.putParcelable(
                            Connection.EXTRA_REMOTE_PHONE_ACCOUNT_HANDLE,
                            connection.getPhoneAccount());
                    if (connection.getParentCallId() != null
                            && (parentConf =
                                            (RemoteConference)
                                                    RemoteConnectionService.this.mConferenceById
                                                            .get(connection.getParentCallId()))
                                    != null) {
                        newExtras.putString(
                                Connection.EXTRA_ADD_TO_CONFERENCE_ID, parentConf.getId());
                        Log.i(
                                this,
                                "addExistingConnection: stash parent of %s as %s",
                                connection.getParentCallId(),
                                parentConf.getId());
                    }
                    remoteConnection.putExtras(newExtras);
                    RemoteConnectionService.this.mConnectionById.put(callId, remoteConnection);
                    remoteConnection.registerCallback(
                            new RemoteConnection
                                    .Callback() { // from class:
                                                  // android.telecom.RemoteConnectionService.1.2
                                @Override // android.telecom.RemoteConnection.Callback
                                public void onDestroyed(RemoteConnection connection2) {
                                    RemoteConnectionService.this.mConnectionById.remove(callId);
                                    RemoteConnectionService.this.maybeDisconnectAdapter();
                                }
                            });
                    RemoteConnectionService.this.mOurConnectionServiceImpl
                            .addRemoteExistingConnection(remoteConnection);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void putExtras(String callId, Bundle extras, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.hasConnection(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "putExtras")
                                .putExtras(extras);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "putExtras")
                                .putExtras(extras);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void removeExtras(
                        String callId, List<String> keys, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.hasConnection(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "removeExtra")
                                .removeExtras(keys);
                    } else {
                        RemoteConnectionService.this
                                .findConferenceForAction(callId, "removeExtra")
                                .removeExtras(keys);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setAudioRoute(
                        String callId,
                        int audioRoute,
                        String bluetoothAddress,
                        Session.Info sessionInfo) {
                    RemoteConnectionService.this.hasConnection(callId);
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onConnectionEvent(
                        String callId, String event, Bundle extras, Session.Info sessionInfo) {
                    if (RemoteConnectionService.this.mConnectionById.containsKey(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "onConnectionEvent")
                                .onConnectionEvent(event, extras);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onRttInitiationSuccess(String callId, Session.Info sessionInfo)
                        throws RemoteException {
                    if (RemoteConnectionService.this.hasConnection(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "onRttInitiationSuccess")
                                .onRttInitiationSuccess();
                    } else {
                        Log.w(
                                this,
                                "onRttInitiationSuccess called on a remote conference",
                                new Object[0]);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onRttInitiationFailure(
                        String callId, int reason, Session.Info sessionInfo)
                        throws RemoteException {
                    if (RemoteConnectionService.this.hasConnection(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "onRttInitiationFailure")
                                .onRttInitiationFailure(reason);
                    } else {
                        Log.w(
                                this,
                                "onRttInitiationFailure called on a remote conference",
                                new Object[0]);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onRttSessionRemotelyTerminated(String callId, Session.Info sessionInfo)
                        throws RemoteException {
                    if (RemoteConnectionService.this.hasConnection(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "onRttSessionRemotelyTerminated")
                                .onRttSessionRemotelyTerminated();
                    } else {
                        Log.w(
                                this,
                                "onRttSessionRemotelyTerminated called on a remote conference",
                                new Object[0]);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void onRemoteRttRequest(String callId, Session.Info sessionInfo)
                        throws RemoteException {
                    if (RemoteConnectionService.this.hasConnection(callId)) {
                        RemoteConnectionService.this
                                .findConnectionForAction(callId, "onRemoteRttRequest")
                                .onRemoteRttRequest();
                    } else {
                        Log.w(
                                this,
                                "onRemoteRttRequest called on a remote conference",
                                new Object[0]);
                    }
                }

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void resetConnectionTime(String callId, Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setConferenceState(
                        String callId, boolean isConference, Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void setCallDirection(
                        String callId, int direction, Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void requestCallEndpointChange(
                        String callId,
                        CallEndpoint endpoint,
                        ResultReceiver callback,
                        Session.Info sessionInfo) {}

                @Override // com.android.internal.telecom.IConnectionServiceAdapter
                public void queryLocation(
                        String callId,
                        long timeoutMillis,
                        String provider,
                        ResultReceiver callback,
                        Session.Info sessionInfo) {}
            };
    private final ConnectionServiceAdapterServant mServant =
            new ConnectionServiceAdapterServant(this.mServantDelegate);
    private final IBinder.DeathRecipient mDeathRecipient =
            new IBinder.DeathRecipient() { // from class: android.telecom.RemoteConnectionService.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    for (RemoteConnection c :
                            RemoteConnectionService.this.mConnectionById.values()) {
                        c.setDestroyed();
                    }
                    for (RemoteConference c2 :
                            RemoteConnectionService.this.mConferenceById.values()) {
                        c2.setDestroyed();
                    }
                    RemoteConnectionService.this.mConnectionById.clear();
                    RemoteConnectionService.this.mConferenceById.clear();
                    RemoteConnectionService.this.mPendingConnections.clear();
                    RemoteConnectionService.this
                            .mOutgoingConnectionServiceRpc
                            .asBinder()
                            .unlinkToDeath(RemoteConnectionService.this.mDeathRecipient, 0);
                }
            };
    private final Map<String, RemoteConnection> mConnectionById = new HashMap();
    private final Map<String, RemoteConference> mConferenceById = new HashMap();
    private final Set<RemoteConnection> mPendingConnections = new HashSet();
    private final FeatureFlags mTelecomFeatureFlags = new FeatureFlagsImpl();

    static {
    }

    RemoteConnectionService(
            IConnectionService outgoingConnectionServiceRpc,
            ConnectionService ourConnectionServiceImpl)
            throws RemoteException {
        this.mOutgoingConnectionServiceRpc = outgoingConnectionServiceRpc;
        this.mOutgoingConnectionServiceRpc.asBinder().linkToDeath(this.mDeathRecipient, 0);
        this.mOurConnectionServiceImpl = ourConnectionServiceImpl;
    }

    public String toString() {
        return "[RemoteCS - "
                + this.mOutgoingConnectionServiceRpc.asBinder().toString()
                + NavigationBarInflaterView.SIZE_MOD_END;
    }

    final RemoteConnection createRemoteConnection(
            PhoneAccountHandle connectionManagerPhoneAccount,
            ConnectionRequest request,
            boolean isIncoming) {
        boolean shouldShowIncomingUI;
        final String id = UUID.randomUUID().toString();
        Bundle extras = new Bundle();
        if (request.getExtras() != null) {
            extras.putAll(request.getExtras());
        }
        extras.putString(
                Connection.EXTRA_REMOTE_CONNECTION_ORIGINATING_PACKAGE_NAME,
                this.mOurConnectionServiceImpl.getApplicationContext().getOpPackageName());
        if (!this.mTelecomFeatureFlags.setRemoteConnectionCallId()) {
            shouldShowIncomingUI = false;
        } else {
            boolean shouldShowIncomingUI2 = request.shouldShowIncomingCallUi();
            shouldShowIncomingUI = shouldShowIncomingUI2;
        }
        String telecomCallId = request.getTelecomCallId();
        ConnectionRequest newRequest =
                new ConnectionRequest.Builder()
                        .setAccountHandle(request.getAccountHandle())
                        .setAddress(request.getAddress())
                        .setExtras(extras)
                        .setVideoState(request.getVideoState())
                        .setRttPipeFromInCall(request.getRttPipeFromInCall())
                        .setRttPipeToInCall(request.getRttPipeToInCall())
                        .setTelecomCallId(telecomCallId)
                        .setShouldShowIncomingCallUi(shouldShowIncomingUI)
                        .build();
        try {
            if (this.mConnectionById.isEmpty()) {
                this.mOutgoingConnectionServiceRpc.addConnectionServiceAdapter(
                        this.mServant.getStub(), null);
            }
            RemoteConnection connection =
                    new RemoteConnection(id, this.mOutgoingConnectionServiceRpc, newRequest);
            this.mPendingConnections.add(connection);
            this.mConnectionById.put(id, connection);
            this.mOutgoingConnectionServiceRpc.createConnection(
                    connectionManagerPhoneAccount, id, newRequest, isIncoming, false, null);
            connection.registerCallback(
                    new RemoteConnection
                            .Callback() { // from class: android.telecom.RemoteConnectionService.3
                        @Override // android.telecom.RemoteConnection.Callback
                        public void onDestroyed(RemoteConnection connection2) {
                            RemoteConnectionService.this.mConnectionById.remove(id);
                            RemoteConnectionService.this.maybeDisconnectAdapter();
                        }
                    });
            return connection;
        } catch (RemoteException e) {
            return RemoteConnection.failure(new DisconnectCause(1, e.toString()));
        }
    }

    RemoteConference createRemoteConference(
            PhoneAccountHandle connectionManagerPhoneAccount,
            ConnectionRequest request,
            boolean isIncoming) {
        final String id = UUID.randomUUID().toString();
        try {
            if (this.mConferenceById.isEmpty()) {
                this.mOutgoingConnectionServiceRpc.addConnectionServiceAdapter(
                        this.mServant.getStub(), null);
            }
            String telecomCallId =
                    this.mTelecomFeatureFlags.setRemoteConnectionCallId()
                            ? id
                            : request.getTelecomCallId();
            ConnectionRequest newRequest =
                    new ConnectionRequest.Builder()
                            .setAccountHandle(request.getAccountHandle())
                            .setAddress(request.getAddress())
                            .setExtras(request.getExtras())
                            .setVideoState(request.getVideoState())
                            .setShouldShowIncomingCallUi(request.shouldShowIncomingCallUi())
                            .setRttPipeFromInCall(request.getRttPipeFromInCall())
                            .setRttPipeToInCall(request.getRttPipeToInCall())
                            .setParticipants(request.getParticipants())
                            .setIsAdhocConferenceCall(request.isAdhocConferenceCall())
                            .setTelecomCallId(telecomCallId)
                            .build();
            RemoteConference conference =
                    new RemoteConference(id, this.mOutgoingConnectionServiceRpc);
            this.mOutgoingConnectionServiceRpc.createConference(
                    connectionManagerPhoneAccount, id, newRequest, isIncoming, false, null);
            conference.registerCallback(
                    new RemoteConference
                            .Callback() { // from class: android.telecom.RemoteConnectionService.4
                        @Override // android.telecom.RemoteConference.Callback
                        public void onDestroyed(RemoteConference conference2) {
                            RemoteConnectionService.this.mConferenceById.remove(id);
                            RemoteConnectionService.this.maybeDisconnectAdapter();
                        }
                    });
            conference.putExtras(newRequest.getExtras());
            return conference;
        } catch (RemoteException e) {
            return RemoteConference.failure(new DisconnectCause(1, e.toString()));
        }
    }

    public boolean hasConnection(String callId) {
        return this.mConnectionById.containsKey(callId);
    }

    public RemoteConnection findConnectionForAction(String callId, String action) {
        if (this.mConnectionById.containsKey(callId)) {
            return this.mConnectionById.get(callId);
        }
        Log.w(this, "%s - Cannot find Connection %s", action, callId);
        return NULL_CONNECTION;
    }

    public RemoteConference findConferenceForAction(String callId, String action) {
        if (this.mConferenceById.containsKey(callId)) {
            return this.mConferenceById.get(callId);
        }
        Log.w(this, "%s - Cannot find Conference %s", action, callId);
        return NULL_CONFERENCE;
    }

    public void maybeDisconnectAdapter() {
        if (this.mConnectionById.isEmpty() && this.mConferenceById.isEmpty()) {
            try {
                this.mOutgoingConnectionServiceRpc.removeConnectionServiceAdapter(
                        this.mServant.getStub(), null);
            } catch (RemoteException e) {
            }
        }
    }
}
