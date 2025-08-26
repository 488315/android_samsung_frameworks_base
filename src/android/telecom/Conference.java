package android.telecom;

import android.annotation.SystemApi;
import android.hardware.gnss.GnssSignalType;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.telecom.Connection;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes4.dex */
public abstract class Conference extends Conferenceable {
    public static final long CONNECT_TIME_NOT_SPECIFIED = 0;
    private Uri mAddress;
    private int mAddressPresentation;
    private CallAudioState mCallAudioState;
    private int mCallDirection;
    private CallEndpoint mCallEndpoint;
    private String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private final List<Connection> mChildConnections;
    private final List<Connection> mConferenceableConnections;
    private long mConnectTimeMillis;
    private int mConnectionCapabilities;
    private final Connection.Listener mConnectionDeathListener;
    private int mConnectionProperties;
    private long mConnectionStartElapsedRealTime;
    private DisconnectCause mDisconnectCause;
    private String mDisconnectMessage;
    private Bundle mExtras;
    private final Object mExtrasLock;
    private boolean mIsMultiparty;
    private final Set<Listener> mListeners = new CopyOnWriteArraySet();
    private PhoneAccountHandle mPhoneAccount;
    private Set<String> mPreviousExtraKeys;
    private boolean mRingbackRequested;
    private int mState;
    private StatusHints mStatusHints;
    private String mTelecomCallId;
    private final List<Connection> mUnmodifiableChildConnections;
    private final List<Connection> mUnmodifiableConferenceableConnections;

    public Connection.VideoProvider getVideoProvider() {
        return null;
    }

    public int getVideoState() {
        return 0;
    }

    public void onAddConferenceParticipants(List<Uri> list) {
    }

    public void onAnswer(int i) {
    }

    @SystemApi
    @Deprecated
    public void onAudioStateChanged(AudioState audioState) {
    }

    public void onAvailableCallEndpointsChanged(List<CallEndpoint> list) {
    }

    @Deprecated
    public void onCallAudioStateChanged(CallAudioState callAudioState) {
    }

    public void onCallEndpointChanged(CallEndpoint callEndpoint) {
    }

    public void onConnectionAdded(Connection connection) {
    }

    public void onDisconnect() {
    }

    public void onExtrasChanged(Bundle bundle) {
    }

    public void onHold() {
    }

    public void onMerge() {
    }

    public void onMerge(Connection connection) {
    }

    public void onMuteStateChanged(boolean z) {
    }

    public void onPlayDtmfTone(char c) {
    }

    public void onReject() {
    }

    public void onSeparate(Connection connection) {
    }

    public void onStopDtmfTone() {
    }

    public void onSwap() {
    }

    public void onUnhold() {
    }

    static abstract class Listener {
        public void onAddressChanged(Conference conference, Uri uri, int i) {
        }

        public void onCallDirectionChanged(Conference conference, int i) {
        }

        public void onCallerDisplayNameChanged(Conference conference, String str, int i) {
        }

        public void onConferenceStateChanged(Conference conference, boolean z) {
        }

        public void onConferenceableConnectionsChanged(Conference conference, List<Connection> list) {
        }

        public void onConnectionAdded(Conference conference, Connection connection) {
        }

        public void onConnectionCapabilitiesChanged(Conference conference, int i) {
        }

        public void onConnectionEvent(Conference conference, String str, Bundle bundle) {
        }

        public void onConnectionPropertiesChanged(Conference conference, int i) {
        }

        public void onConnectionRemoved(Conference conference, Connection connection) {
        }

        public void onDestroyed(Conference conference) {
        }

        public void onDisconnected(Conference conference, DisconnectCause disconnectCause) {
        }

        public void onExtrasChanged(Conference conference, Bundle bundle) {
        }

        public void onExtrasRemoved(Conference conference, List<String> list) {
        }

        public void onRingbackRequested(Conference conference, boolean z) {
        }

        public void onStateChanged(Conference conference, int i, int i2) {
        }

        public void onStatusHintsChanged(Conference conference, StatusHints statusHints) {
        }

        public void onVideoProviderChanged(Conference conference, Connection.VideoProvider videoProvider) {
        }

        public void onVideoStateChanged(Conference conference, int i) {
        }

        Listener() {
        }
    }

    public Conference(PhoneAccountHandle phoneAccountHandle) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.mChildConnections = copyOnWriteArrayList;
        this.mUnmodifiableChildConnections = Collections.unmodifiableList(copyOnWriteArrayList);
        ArrayList arrayList = new ArrayList();
        this.mConferenceableConnections = arrayList;
        this.mUnmodifiableConferenceableConnections = Collections.unmodifiableList(arrayList);
        this.mState = 1;
        this.mConnectTimeMillis = 0L;
        this.mConnectionStartElapsedRealTime = 0L;
        this.mExtrasLock = new Object();
        this.mAddressPresentation = 3;
        this.mRingbackRequested = false;
        this.mIsMultiparty = true;
        this.mConnectionDeathListener = new Connection.Listener() { // from class: android.telecom.Conference.1
            @Override // android.telecom.Connection.Listener
            public void onDestroyed(Connection connection) {
                if (Conference.this.mConferenceableConnections.remove(connection)) {
                    Conference.this.fireOnConferenceableConnectionsChanged();
                }
            }
        };
        this.mPhoneAccount = phoneAccountHandle;
    }

    @SystemApi
    public final String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public final void setTelecomCallId(String str) {
        this.mTelecomCallId = str;
    }

    public final PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccount;
    }

    public final List<Connection> getConnections() {
        return this.mUnmodifiableChildConnections;
    }

    public final int getState() {
        return this.mState;
    }

    public final boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    @SystemApi
    @Deprecated
    public final AudioState getAudioState() {
        return new AudioState(this.mCallAudioState);
    }

    @Deprecated
    public final CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    public final CallEndpoint getCurrentCallEndpoint() {
        return this.mCallEndpoint;
    }

    public final void onAnswer() {
        onAnswer(0);
    }

    public final void setOnHold() {
        setState(5);
    }

    public final void setDialing() {
        setState(3);
    }

    public final void setRinging() {
        setState(2);
    }

    public final void setActive() {
        setRingbackRequested(false);
        setState(4);
    }

    public final void setDisconnected(DisconnectCause disconnectCause) {
        this.mDisconnectCause = disconnectCause;
        setState(6);
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDisconnected(this, this.mDisconnectCause);
        }
    }

    public final DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final void setConnectionCapabilities(int i) {
        if (i != this.mConnectionCapabilities) {
            this.mConnectionCapabilities = i;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionCapabilitiesChanged(this, this.mConnectionCapabilities);
            }
        }
    }

    public final void setConnectionProperties(int i) {
        if (i != this.mConnectionProperties) {
            this.mConnectionProperties = i;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onConnectionPropertiesChanged(this, this.mConnectionProperties);
            }
        }
    }

    public final boolean addConnection(Connection connection) {
        Log.d(this, "Connection=%s, connection=", connection);
        if (connection == null || this.mChildConnections.contains(connection) || !connection.setConference(this)) {
            return false;
        }
        this.mChildConnections.add(connection);
        onConnectionAdded(connection);
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionAdded(this, connection);
        }
        return true;
    }

    public final void removeConnection(Connection connection) {
        Log.d(this, "removing %s from %s", connection, this.mChildConnections);
        if (connection == null || !this.mChildConnections.remove(connection)) {
            return;
        }
        connection.resetConference();
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionRemoved(this, connection);
        }
    }

    public final void setConferenceableConnections(List<Connection> list) {
        clearConferenceableList();
        for (Connection connection : list) {
            if (!this.mConferenceableConnections.contains(connection)) {
                connection.addConnectionListener(this.mConnectionDeathListener);
                this.mConferenceableConnections.add(connection);
            }
        }
        fireOnConferenceableConnectionsChanged();
    }

    public final void setRingbackRequested(boolean z) {
        if (this.mRingbackRequested != z) {
            this.mRingbackRequested = z;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onRingbackRequested(this, z);
            }
        }
    }

    public final void setVideoState(Connection connection, int i) {
        Log.d(this, "setVideoState Conference: %s Connection: %s VideoState: %s", this, connection, Integer.valueOf(i));
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoStateChanged(this, i);
        }
    }

    public final void setVideoProvider(Connection connection, Connection.VideoProvider videoProvider) {
        Log.d(this, "setVideoProvider Conference: %s Connection: %s VideoState: %s", this, connection, videoProvider);
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onVideoProviderChanged(this, videoProvider);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fireOnConferenceableConnectionsChanged() {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceableConnectionsChanged(this, getConferenceableConnections());
        }
    }

    public final List<Connection> getConferenceableConnections() {
        return this.mUnmodifiableConferenceableConnections;
    }

    public final void destroy() {
        Log.d(this, "destroying conference : %s", this);
        for (Connection connection : this.mChildConnections) {
            Log.d(this, "removing connection %s", connection);
            removeConnection(connection);
        }
        if (this.mState != 6) {
            Log.d(this, "setting to disconnected", new Object[0]);
            setDisconnected(new DisconnectCause(2));
        }
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onDestroyed(this);
        }
    }

    final Conference addListener(Listener listener) {
        this.mListeners.add(listener);
        return this;
    }

    final Conference removeListener(Listener listener) {
        this.mListeners.remove(listener);
        return this;
    }

    @SystemApi
    public Connection getPrimaryConnection() {
        List<Connection> list = this.mUnmodifiableChildConnections;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.mUnmodifiableChildConnections.get(0);
    }

    @SystemApi
    @Deprecated
    public final void setConnectTimeMillis(long j) {
        setConnectionTime(j);
    }

    public final void setConnectionTime(long j) {
        this.mConnectTimeMillis = j;
    }

    @Deprecated
    public final void setConnectionStartElapsedRealTime(long j) {
        setConnectionStartElapsedRealtimeMillis(j);
    }

    public final void setConnectionStartElapsedRealtimeMillis(long j) {
        this.mConnectionStartElapsedRealTime = j;
    }

    @SystemApi
    @Deprecated
    public final long getConnectTimeMillis() {
        return getConnectionTime();
    }

    public final long getConnectionTime() {
        return this.mConnectTimeMillis;
    }

    public final long getConnectionStartElapsedRealtimeMillis() {
        return this.mConnectionStartElapsedRealTime;
    }

    final void setCallAudioState(CallAudioState callAudioState) {
        Log.d(this, "setCallAudioState %s", callAudioState);
        this.mCallAudioState = callAudioState;
        onAudioStateChanged(getAudioState());
        onCallAudioStateChanged(callAudioState);
    }

    final void setCallEndpoint(CallEndpoint callEndpoint) {
        Log.d(this, "setCallEndpoint %s", callEndpoint);
        this.mCallEndpoint = callEndpoint;
        onCallEndpointChanged(callEndpoint);
    }

    final void setAvailableCallEndpoints(List<CallEndpoint> list) {
        Log.d(this, "setAvailableCallEndpoints", new Object[0]);
        onAvailableCallEndpointsChanged(list);
    }

    final void setMuteState(boolean z) {
        Log.d(this, "setMuteState %s", Boolean.valueOf(z));
        onMuteStateChanged(z);
    }

    private void setState(int i) {
        int i2 = this.mState;
        if (i2 != i) {
            this.mState = i;
            Iterator<Listener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onStateChanged(this, i2, i);
            }
        }
    }

    private static class FailureSignalingConference extends Conference {
        private boolean mImmutable;

        public FailureSignalingConference(DisconnectCause disconnectCause, PhoneAccountHandle phoneAccountHandle) {
            super(phoneAccountHandle);
            this.mImmutable = false;
            setDisconnected(disconnectCause);
            this.mImmutable = true;
        }

        public void checkImmutable() {
            if (this.mImmutable) {
                throw new UnsupportedOperationException("Conference is immutable");
            }
        }
    }

    public static Conference createFailedConference(DisconnectCause disconnectCause, PhoneAccountHandle phoneAccountHandle) {
        return new FailureSignalingConference(disconnectCause, phoneAccountHandle);
    }

    private final void clearConferenceableList() {
        Iterator<Connection> it = this.mConferenceableConnections.iterator();
        while (it.hasNext()) {
            it.next().removeConnectionListener(this.mConnectionDeathListener);
        }
        this.mConferenceableConnections.clear();
    }

    public String toString() {
        return String.format(Locale.US, "[State: %s,Capabilites: %s, VideoState: %s, VideoProvider: %s,isRingbackRequested: %s, ThisObject %s]", Connection.stateToString(this.mState), Call.Details.capabilitiesToString(this.mConnectionCapabilities), Integer.valueOf(getVideoState()), getVideoProvider(), isRingbackRequested() ? GnssSignalType.CODE_TYPE_Y : GnssSignalType.CODE_TYPE_N, super.toString());
    }

    public final void setStatusHints(StatusHints statusHints) {
        this.mStatusHints = statusHints;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onStatusHintsChanged(this, statusHints);
        }
    }

    public final StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final void setExtras(Bundle bundle) {
        synchronized (this.mExtrasLock) {
            putExtras(bundle);
            if (this.mPreviousExtraKeys != null) {
                ArrayList arrayList = new ArrayList();
                for (String str : this.mPreviousExtraKeys) {
                    if (bundle == null || !bundle.containsKey(str)) {
                        arrayList.add(str);
                    }
                }
                if (!arrayList.isEmpty()) {
                    removeExtras(arrayList);
                }
            }
            if (this.mPreviousExtraKeys == null) {
                this.mPreviousExtraKeys = new ArraySet();
            }
            this.mPreviousExtraKeys.clear();
            if (bundle != null) {
                this.mPreviousExtraKeys.addAll(bundle.keySet());
            }
        }
    }

    public final void putExtras(Bundle bundle) {
        Bundle bundle2;
        if (bundle == null) {
            return;
        }
        synchronized (this.mExtrasLock) {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            this.mExtras.putAll(bundle);
            bundle2 = new Bundle(this.mExtras);
        }
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onExtrasChanged(this, new Bundle(bundle2));
        }
    }

    public final void putExtra(String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(str, z);
        putExtras(bundle);
    }

    public final void putExtra(String str, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(str, i);
        putExtras(bundle);
    }

    public final void putExtra(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        putExtras(bundle);
    }

    public final void removeExtras(List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        synchronized (this.mExtrasLock) {
            if (this.mExtras != null) {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    this.mExtras.remove(it.next());
                }
            }
        }
        List<String> listUnmodifiableList = Collections.unmodifiableList(list);
        Iterator<Listener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onExtrasRemoved(this, listUnmodifiableList);
        }
    }

    public final void removeExtras(String... strArr) {
        removeExtras(Arrays.asList(strArr));
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    @SystemApi
    public void setConferenceState(boolean z) {
        this.mIsMultiparty = z;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConferenceStateChanged(this, z);
        }
    }

    public final void setCallDirection(int i) {
        Log.d(this, "setDirection %d", Integer.valueOf(i));
        this.mCallDirection = i;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallDirectionChanged(this, i);
        }
    }

    public boolean isMultiparty() {
        return this.mIsMultiparty;
    }

    @SystemApi
    public final void setAddress(Uri uri, int i) {
        Log.d(this, "setAddress %s", Log.maskPii(uri));
        this.mAddress = uri;
        this.mAddressPresentation = i;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onAddressChanged(this, uri, i);
        }
    }

    public final Uri getAddress() {
        return this.mAddress;
    }

    public final int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public final String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public final int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public final int getCallDirection() {
        return this.mCallDirection;
    }

    @SystemApi
    public final void setCallerDisplayName(String str, int i) {
        Log.d(this, "setCallerDisplayName %s", Log.maskPii(str));
        this.mCallerDisplayName = str;
        this.mCallerDisplayNamePresentation = i;
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallerDisplayNameChanged(this, str, i);
        }
    }

    final void handleExtrasChanged(Bundle bundle) {
        Bundle bundle2;
        synchronized (this.mExtrasLock) {
            this.mExtras = bundle;
            bundle2 = bundle != null ? new Bundle(this.mExtras) : null;
        }
        onExtrasChanged(bundle2);
    }

    public void sendConferenceEvent(String str, Bundle bundle) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onConnectionEvent(this, str, bundle);
        }
    }
}
