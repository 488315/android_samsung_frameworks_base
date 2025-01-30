package android.telecom;

import android.location.Location;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.OutcomeReceiver;
import android.p009os.RemoteException;
import android.p009os.ResultReceiver;
import android.telecom.Connection;
import android.telephony.Rlog;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import com.android.internal.telephony.SemTelephonyUtils;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class ConnectionServiceAdapter implements IBinder.DeathRecipient {
    private static final String LOG_TAG = "Telecom-ConnectionServiceAdapter";
    private final Set<IConnectionServiceAdapter> mAdapters = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));

    ConnectionServiceAdapter() {
    }

    void addAdapter(IConnectionServiceAdapter adapter) {
        for (IConnectionServiceAdapter it : this.mAdapters) {
            if (it.asBinder() == adapter.asBinder()) {
                Log.m75w(this, "Ignoring duplicate adapter addition.", new Object[0]);
                return;
            }
        }
        if (this.mAdapters.add(adapter)) {
            Rlog.m77d(LOG_TAG, "addAdapter - adapter: " + adapter);
            try {
                adapter.asBinder().linkToDeath(this, 0);
            } catch (RemoteException e) {
                this.mAdapters.remove(adapter);
            }
        }
    }

    void removeAdapter(IConnectionServiceAdapter adapter) {
        if (adapter != null) {
            for (IConnectionServiceAdapter it : this.mAdapters) {
                if (it.asBinder() == adapter.asBinder() && this.mAdapters.remove(it)) {
                    Rlog.m77d(LOG_TAG, "removeAdapter - adapter: " + adapter);
                    adapter.asBinder().unlinkToDeath(this, 0);
                    return;
                }
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            IConnectionServiceAdapter adapter = it.next();
            if (!adapter.asBinder().isBinderAlive()) {
                it.remove();
                adapter.asBinder().unlinkToDeath(this, 0);
            }
        }
    }

    void handleCreateConnectionComplete(String id, ConnectionRequest request, ParcelableConnection connection) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.handleCreateConnectionComplete(id, request, connection, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void handleCreateConferenceComplete(String id, ConnectionRequest request, ParcelableConference conference) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.handleCreateConferenceComplete(id, request, conference, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setActive(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setActive(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setRinging(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setRinging(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setDialing(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setDialing(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setPulling(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setPulling(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setDisconnected(String callId, DisconnectCause disconnectCause) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setDisconnected(callId, disconnectCause, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setOnHold(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setOnHold(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setRingbackRequested(String callId, boolean ringback) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setRingbackRequested(callId, ringback, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setConnectionCapabilities(String callId, int capabilities) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setConnectionCapabilities(callId, capabilities, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setConnectionProperties(String callId, int properties) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setConnectionProperties(callId, properties, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setIsConferenced(String callId, String conferenceCallId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                Log.m67d(this, "sending connection %s with conference %s", callId, conferenceCallId);
                adapter.setIsConferenced(callId, conferenceCallId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onConferenceMergeFailed(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                Log.m67d(this, "merge failed for call %s", callId);
                adapter.setConferenceMergeFailed(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void resetConnectionTime(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.resetConnectionTime(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void removeCall(String callId) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.removeCall(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onPostDialWait(String callId, String remaining) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.onPostDialWait(callId, remaining, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onPostDialChar(String callId, char nextChar) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.onPostDialChar(callId, nextChar, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void addConferenceCall(String callId, ParcelableConference parcelableConference) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.addConferenceCall(callId, parcelableConference, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void queryRemoteConnectionServices(RemoteServiceCallback callback, String callingPackage) {
        if (this.mAdapters.size() == 1) {
            try {
                this.mAdapters.iterator().next().queryRemoteConnectionServices(callback, callingPackage, Log.getExternalSession());
                return;
            } catch (RemoteException e) {
                Log.m69e(this, e, "Exception trying to query for remote CSs", new Object[0]);
                return;
            }
        }
        try {
            callback.onResult(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        } catch (RemoteException e2) {
            Log.m69e(this, e2, "Exception trying to query for remote CSs", new Object[0]);
        }
    }

    void setVideoProvider(String callId, Connection.VideoProvider videoProvider) {
        IVideoProvider iVideoProvider;
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            if (videoProvider == null) {
                iVideoProvider = null;
            } else {
                try {
                    iVideoProvider = videoProvider.getInterface();
                } catch (RemoteException e) {
                }
            }
            adapter.setVideoProvider(callId, iVideoProvider, Log.getExternalSession());
        }
    }

    void setIsVoipAudioMode(String callId, boolean isVoip) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setIsVoipAudioMode(callId, isVoip, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setStatusHints(String callId, StatusHints statusHints) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setStatusHints(callId, statusHints, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setAddress(String callId, Uri address, int presentation) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setAddress(callId, address, presentation, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setCallerDisplayName(String callId, String callerDisplayName, int presentation) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setCallerDisplayName(callId, callerDisplayName, presentation, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setVideoState(String callId, int videoState) {
        Log.m73v(this, "setVideoState: %d", Integer.valueOf(videoState));
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setVideoState(callId, videoState, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setConferenceableConnections(String callId, List<String> conferenceableCallIds) {
        Rlog.m77d(LOG_TAG, "setConferenceableConnections - callId: " + callId + ", conferenceableCallIds: " + conferenceableCallIds);
        Log.m73v(this, "setConferenceableConnections: %s, %s", callId, conferenceableCallIds);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setConferenceableConnections(callId, conferenceableCallIds, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void addExistingConnection(String callId, ParcelableConnection connection) {
        Log.m73v(this, "addExistingConnection: %s", callId);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.addExistingConnection(callId, connection, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void putExtras(String callId, Bundle extras) {
        Log.m73v(this, "putExtras: %s", callId);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.putExtras(callId, extras, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void putExtra(String callId, String key, boolean value) {
        Log.m73v(this, "putExtra: %s %s=%b", callId, key, Boolean.valueOf(value));
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putBoolean(key, value);
                adapter.putExtras(callId, bundle, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void putExtra(String callId, String key, int value) {
        Log.m73v(this, "putExtra: %s %s=%d", callId, key, Integer.valueOf(value));
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(key, value);
                adapter.putExtras(callId, bundle, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void putExtra(String callId, String key, String value) {
        Log.m73v(this, "putExtra: %s %s=%s", callId, key, SemTelephonyUtils.maskPii(value));
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString(key, value);
                adapter.putExtras(callId, bundle, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void removeExtras(String callId, List<String> keys) {
        Log.m73v(this, "removeExtras: %s %s", callId, keys);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.removeExtras(callId, keys, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setAudioRoute(String callId, int audioRoute, String bluetoothAddress) {
        Log.m73v(this, "setAudioRoute: %s %s %s", callId, CallAudioState.audioRouteToString(audioRoute), bluetoothAddress);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setAudioRoute(callId, audioRoute, bluetoothAddress, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void requestCallEndpointChange(String callId, CallEndpoint endpoint, Executor executor, OutcomeReceiver<Void, CallEndpointException> callback) {
        Log.m73v(this, "requestCallEndpointChange", new Object[0]);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.requestCallEndpointChange(callId, endpoint, new ResultReceiverC29411(null, executor, callback), Log.getExternalSession());
            } catch (RemoteException e) {
                Log.m67d(this, "Remote exception calling requestCallEndpointChange", new Object[0]);
            }
        }
    }

    /* renamed from: android.telecom.ConnectionServiceAdapter$1 */
    class ResultReceiverC29411 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ResultReceiverC29411(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.p009os.ResultReceiver
        protected void onReceiveResult(int resultCode, final Bundle result) {
            super.onReceiveResult(resultCode, result);
            long identity = Binder.clearCallingIdentity();
            try {
                if (resultCode == 0) {
                    Executor executor = this.val$executor;
                    final OutcomeReceiver outcomeReceiver = this.val$callback;
                    executor.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onResult(null);
                        }
                    });
                } else {
                    Executor executor2 = this.val$executor;
                    final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                    executor2.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            OutcomeReceiver.this.onError((CallEndpointException) result.getParcelable(CallEndpointException.CHANGE_ERROR, CallEndpointException.class));
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }
    }

    void onConnectionEvent(String callId, String event, Bundle extras) {
        Log.m73v(this, "onConnectionEvent: %s", event);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.onConnectionEvent(callId, event, extras, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onRttInitiationSuccess(String callId) {
        Log.m73v(this, "onRttInitiationSuccess: %s", callId);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.onRttInitiationSuccess(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onRttInitiationFailure(String callId, int reason) {
        Log.m73v(this, "onRttInitiationFailure: %s", callId);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.onRttInitiationFailure(callId, reason, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onRttSessionRemotelyTerminated(String callId) {
        Log.m73v(this, "onRttSessionRemotelyTerminated: %s", callId);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.onRttSessionRemotelyTerminated(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onRemoteRttRequest(String callId) {
        Log.m73v(this, "onRemoteRttRequest: %s", callId);
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.onRemoteRttRequest(callId, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onPhoneAccountChanged(String callId, PhoneAccountHandle pHandle) {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                Log.m67d(this, "onPhoneAccountChanged %s", callId);
                adapter.onPhoneAccountChanged(callId, pHandle, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void onConnectionServiceFocusReleased() {
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                Log.m67d(this, "onConnectionServiceFocusReleased", new Object[0]);
                adapter.onConnectionServiceFocusReleased(Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setConferenceState(String callId, boolean isConference) {
        Log.m73v(this, "setConferenceState: %s %b", callId, Boolean.valueOf(isConference));
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.setConferenceState(callId, isConference, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void setCallDirection(String callId, int direction) {
        for (IConnectionServiceAdapter a : this.mAdapters) {
            try {
                a.setCallDirection(callId, direction, Log.getExternalSession());
            } catch (RemoteException e) {
            }
        }
    }

    void queryLocation(String callId, long timeoutMillis, String provider, Executor executor, final OutcomeReceiver<Location, QueryLocationException> callback) {
        Log.m73v(this, "queryLocation: %s %d", callId, Long.valueOf(timeoutMillis));
        for (IConnectionServiceAdapter adapter : this.mAdapters) {
            try {
                adapter.queryLocation(callId, timeoutMillis, provider, new ResultReceiverC29422(null, executor, callback), Log.getExternalSession());
            } catch (RemoteException e) {
                Log.m67d(this, "queryLocation: Exception e : " + e, new Object[0]);
                executor.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new QueryLocationException(e.getMessage(), 5));
                    }
                });
            }
        }
    }

    /* renamed from: android.telecom.ConnectionServiceAdapter$2 */
    class ResultReceiverC29422 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ResultReceiverC29422(Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.p009os.ResultReceiver
        protected void onReceiveResult(int resultCode, final Bundle result) {
            super.onReceiveResult(resultCode, result);
            if (resultCode == 1) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult((Location) result.getParcelable(Connection.EXTRA_KEY_QUERY_LOCATION, Location.class));
                    }
                });
            } else {
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError((QueryLocationException) result.getParcelable(QueryLocationException.QUERY_LOCATION_ERROR, QueryLocationException.class));
                    }
                });
            }
        }
    }
}
