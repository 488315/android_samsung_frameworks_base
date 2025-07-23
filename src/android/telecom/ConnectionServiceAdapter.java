package android.telecom;

import android.location.Location;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.OutcomeReceiver;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telecom.Connection;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
final class ConnectionServiceAdapter implements IBinder.DeathRecipient {
    private final Set<IConnectionServiceAdapter> mAdapters = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));

    ConnectionServiceAdapter() {
    }

    void addAdapter(IConnectionServiceAdapter iConnectionServiceAdapter) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            if (it.next().asBinder() == iConnectionServiceAdapter.asBinder()) {
                Log.w(this, "Ignoring duplicate adapter addition.", new Object[0]);
                return;
            }
        }
        if (this.mAdapters.add(iConnectionServiceAdapter)) {
            try {
                iConnectionServiceAdapter.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
                this.mAdapters.remove(iConnectionServiceAdapter);
            }
        }
    }

    void removeAdapter(IConnectionServiceAdapter iConnectionServiceAdapter) {
        if (iConnectionServiceAdapter != null) {
            for (IConnectionServiceAdapter iConnectionServiceAdapter2 : this.mAdapters) {
                if (iConnectionServiceAdapter2.asBinder() == iConnectionServiceAdapter.asBinder() && this.mAdapters.remove(iConnectionServiceAdapter2)) {
                    iConnectionServiceAdapter.asBinder().unlinkToDeath(this, 0);
                    return;
                }
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            IConnectionServiceAdapter next = it.next();
            if (!next.asBinder().isBinderAlive()) {
                it.remove();
                next.asBinder().unlinkToDeath(this, 0);
            }
        }
    }

    void handleCreateConnectionComplete(String str, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().handleCreateConnectionComplete(str, connectionRequest, parcelableConnection, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void handleCreateConferenceComplete(String str, ConnectionRequest connectionRequest, ParcelableConference parcelableConference) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().handleCreateConferenceComplete(str, connectionRequest, parcelableConference, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setActive(String str) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setActive(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setRinging(String str) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setRinging(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setDialing(String str) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setDialing(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setPulling(String str) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setPulling(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setDisconnected(String str, DisconnectCause disconnectCause) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setDisconnected(str, disconnectCause, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setOnHold(String str) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setOnHold(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setRingbackRequested(String str, boolean z) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setRingbackRequested(str, z, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setConnectionCapabilities(String str, int i) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConnectionCapabilities(str, i, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setConnectionProperties(String str, int i) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConnectionProperties(str, i, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setIsConferenced(String str, String str2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "sending connection %s with conference %s", str, str2);
                iConnectionServiceAdapter.setIsConferenced(str, str2, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onConferenceMergeFailed(String str) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "merge failed for call %s", str);
                iConnectionServiceAdapter.setConferenceMergeFailed(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void resetConnectionTime(String str) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().resetConnectionTime(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void removeCall(String str) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().removeCall(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onPostDialWait(String str, String str2) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onPostDialWait(str, str2, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onPostDialChar(String str, char c) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onPostDialChar(str, c, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void addConferenceCall(String str, ParcelableConference parcelableConference) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().addConferenceCall(str, parcelableConference, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String str) {
        if (this.mAdapters.size() == 1) {
            try {
                this.mAdapters.iterator().next().queryRemoteConnectionServices(remoteServiceCallback, str, Log.getExternalSession());
                return;
            } catch (RemoteException e) {
                Log.e(this, e, "Exception trying to query for remote CSs", new Object[0]);
                return;
            }
        }
        try {
            remoteServiceCallback.onResult(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        } catch (RemoteException e2) {
            Log.e(this, e2, "Exception trying to query for remote CSs", new Object[0]);
        }
    }

    void setVideoProvider(String str, Connection.VideoProvider videoProvider) {
        IVideoProvider iVideoProvider;
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            if (videoProvider == null) {
                iVideoProvider = null;
            } else {
                try {
                    iVideoProvider = videoProvider.getInterface();
                } catch (RemoteException unused) {
                }
            }
            iConnectionServiceAdapter.setVideoProvider(str, iVideoProvider, Log.getExternalSession());
        }
    }

    void setIsVoipAudioMode(String str, boolean z) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setIsVoipAudioMode(str, z, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setStatusHints(String str, StatusHints statusHints) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setStatusHints(str, statusHints, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setAddress(String str, Uri uri, int i) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setAddress(str, uri, i, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setCallerDisplayName(String str, String str2, int i) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setCallerDisplayName(str, str2, i, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setVideoState(String str, int i) {
        Log.v(this, "setVideoState: %d", Integer.valueOf(i));
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setVideoState(str, i, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setConferenceableConnections(String str, List<String> list) {
        Log.v(this, "setConferenceableConnections: %s, %s", str, list);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConferenceableConnections(str, list, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void addExistingConnection(String str, ParcelableConnection parcelableConnection) {
        Log.v(this, "addExistingConnection: %s", str);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().addExistingConnection(str, parcelableConnection, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void putExtras(String str, Bundle bundle) {
        Log.v(this, "putExtras: %s", str);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().putExtras(str, bundle, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void putExtra(String str, String str2, boolean z) {
        Log.v(this, "putExtra: %s %s=%b", str, str2, Boolean.valueOf(z));
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putBoolean(str2, z);
                iConnectionServiceAdapter.putExtras(str, bundle, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void putExtra(String str, String str2, int i) {
        Log.v(this, "putExtra: %s %s=%d", str, str2, Integer.valueOf(i));
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(str2, i);
                iConnectionServiceAdapter.putExtras(str, bundle, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void putExtra(String str, String str2, String str3) {
        Log.v(this, "putExtra: %s %s=%s", str, str2, Log.maskPii(str3));
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString(str2, str3);
                iConnectionServiceAdapter.putExtras(str, bundle, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void removeExtras(String str, List<String> list) {
        Log.v(this, "removeExtras: %s %s", str, list);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().removeExtras(str, list, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setAudioRoute(String str, int i, String str2) {
        Log.v(this, "setAudioRoute: %s %s %s", str, CallAudioState.audioRouteToString(i), str2);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setAudioRoute(str, i, str2, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void requestCallEndpointChange(String str, CallEndpoint callEndpoint, Executor executor, OutcomeReceiver<Void, CallEndpointException> outcomeReceiver) {
        Log.v(this, "requestCallEndpointChange", new Object[0]);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().requestCallEndpointChange(str, callEndpoint, new AnonymousClass1(this, null, executor, outcomeReceiver), Log.getExternalSession());
            } catch (RemoteException unused) {
                Log.d(this, "Remote exception calling requestCallEndpointChange", new Object[0]);
            }
        }
    }

    /* renamed from: android.telecom.ConnectionServiceAdapter$1, reason: invalid class name */
    class AnonymousClass1 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ConnectionServiceAdapter connectionServiceAdapter, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, final Bundle bundle) {
            super.onReceiveResult(i, bundle);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (i == 0) {
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
                            OutcomeReceiver.this.onError((CallEndpointException) bundle.getParcelable(CallEndpointException.CHANGE_ERROR, CallEndpointException.class));
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    void onConnectionEvent(String str, String str2, Bundle bundle) {
        Log.v(this, "onConnectionEvent: %s", str2);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onConnectionEvent(str, str2, bundle, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onRttInitiationSuccess(String str) {
        Log.v(this, "onRttInitiationSuccess: %s", str);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRttInitiationSuccess(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onRttInitiationFailure(String str, int i) {
        Log.v(this, "onRttInitiationFailure: %s", str);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRttInitiationFailure(str, i, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onRttSessionRemotelyTerminated(String str) {
        Log.v(this, "onRttSessionRemotelyTerminated: %s", str);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRttSessionRemotelyTerminated(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onRemoteRttRequest(String str) {
        Log.v(this, "onRemoteRttRequest: %s", str);
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().onRemoteRttRequest(str, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onPhoneAccountChanged(String str, PhoneAccountHandle phoneAccountHandle) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "onPhoneAccountChanged %s", str);
                iConnectionServiceAdapter.onPhoneAccountChanged(str, phoneAccountHandle, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void onConnectionServiceFocusReleased() {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "onConnectionServiceFocusReleased", new Object[0]);
                iConnectionServiceAdapter.onConnectionServiceFocusReleased(Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setConferenceState(String str, boolean z) {
        Log.v(this, "setConferenceState: %s %b", str, Boolean.valueOf(z));
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setConferenceState(str, z, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void setCallDirection(String str, int i) {
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                it.next().setCallDirection(str, i, Log.getExternalSession());
            } catch (RemoteException unused) {
            }
        }
    }

    void queryLocation(String str, long j, String str2, Executor executor, final OutcomeReceiver<Location, QueryLocationException> outcomeReceiver) {
        String str3;
        long j2;
        String str4;
        Log.v(this, "queryLocation: %s %d", str, Long.valueOf(j));
        Iterator<IConnectionServiceAdapter> it = this.mAdapters.iterator();
        while (it.hasNext()) {
            try {
                str3 = str;
                j2 = j;
                str4 = str2;
            } catch (RemoteException e) {
                e = e;
                str3 = str;
                j2 = j;
                str4 = str2;
            }
            try {
                it.next().queryLocation(str3, j2, str4, new AnonymousClass2(this, null, executor, outcomeReceiver), Log.getExternalSession());
            } catch (RemoteException e2) {
                e = e2;
                final RemoteException remoteException = e;
                Log.d(this, "queryLocation: Exception e : " + remoteException, new Object[0]);
                executor.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError(new QueryLocationException(remoteException.getMessage(), 5));
                    }
                });
                str = str3;
                j = j2;
                str2 = str4;
            }
            str = str3;
            j = j2;
            str2 = str4;
        }
    }

    /* renamed from: android.telecom.ConnectionServiceAdapter$2, reason: invalid class name */
    class AnonymousClass2 extends ResultReceiver {
        final /* synthetic */ OutcomeReceiver val$callback;
        final /* synthetic */ Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(ConnectionServiceAdapter connectionServiceAdapter, Handler handler, Executor executor, OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, final Bundle bundle) {
            super.onReceiveResult(i, bundle);
            if (i == 1) {
                Executor executor = this.val$executor;
                final OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onResult((Location) bundle.getParcelable(Connection.EXTRA_KEY_QUERY_LOCATION, Location.class));
                    }
                });
            } else {
                Executor executor2 = this.val$executor;
                final OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new Runnable() { // from class: android.telecom.ConnectionServiceAdapter$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        OutcomeReceiver.this.onError((QueryLocationException) bundle.getParcelable(QueryLocationException.QUERY_LOCATION_ERROR, QueryLocationException.class));
                    }
                });
            }
        }
    }
}
