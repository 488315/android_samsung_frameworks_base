package android.telecom;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.OutcomeReceiver;
import android.telecom.InCallService;
import android.util.ArrayMap;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

@SystemApi
@Deprecated
/* loaded from: classes4.dex */
public final class Phone {
    public static final int SDK_VERSION_R = 30;
    private CallAudioState mCallAudioState;
    private final Map<String, Call> mCallByTelecomCallId = new ArrayMap();
    private final String mCallingPackage;
    private final List<Call> mCalls;
    private boolean mCanAddCall;
    private final InCallAdapter mInCallAdapter;
    private final List<Listener> mListeners;
    private final Object mLock;
    private final int mTargetSdkVersion;
    private final List<Call> mUnmodifiableCalls;

    public static abstract class Listener {
        @Deprecated
        public void onAudioStateChanged(Phone phone, AudioState audioState) {
        }

        public void onBringToForeground(Phone phone, boolean z) {
        }

        public void onCallAdded(Phone phone, Call call) {
        }

        public void onCallAudioStateChanged(Phone phone, CallAudioState callAudioState) {
        }

        public void onCallRemoved(Phone phone, Call call) {
        }

        public void onCanAddCallChanged(Phone phone, boolean z) {
        }

        public void onSilenceRinger(Phone phone) {
        }
    }

    Phone(InCallAdapter inCallAdapter, String str, int i) {
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        this.mCalls = copyOnWriteArrayList;
        this.mUnmodifiableCalls = Collections.unmodifiableList(copyOnWriteArrayList);
        this.mListeners = new CopyOnWriteArrayList();
        this.mCanAddCall = true;
        this.mLock = new Object();
        this.mInCallAdapter = inCallAdapter;
        this.mCallingPackage = str;
        this.mTargetSdkVersion = i;
    }

    final void internalAddCall(ParcelableCall parcelableCall) {
        if (this.mTargetSdkVersion < 30 && parcelableCall.getState() == 12) {
            Log.i(this, "Skipping adding audio processing call for sdk compatibility", new Object[0]);
            return;
        }
        Call callById = getCallById(parcelableCall.getId());
        if (callById == null) {
            Call call = new Call(this, parcelableCall.getId(), this.mInCallAdapter, parcelableCall.getState(), this.mCallingPackage, this.mTargetSdkVersion);
            synchronized (this.mLock) {
                this.mCallByTelecomCallId.put(parcelableCall.getId(), call);
                this.mCalls.add(call);
            }
            checkCallTree(parcelableCall);
            call.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
            fireCallAdded(call);
            if (call.getState() == 7) {
                internalRemoveCall(call);
                return;
            }
            return;
        }
        Log.w(this, "Call %s added, but it was already present", callById.internalGetCallId());
        checkCallTree(parcelableCall);
        callById.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
    }

    final void internalRemoveCall(Call call) {
        synchronized (this.mLock) {
            this.mCallByTelecomCallId.remove(call.internalGetCallId());
            this.mCalls.remove(call);
        }
        InCallService.VideoCall videoCall = call.getVideoCall();
        if (videoCall != null) {
            videoCall.destroy();
        }
        fireCallRemoved(call);
    }

    final void internalUpdateCall(ParcelableCall parcelableCall) {
        if (this.mTargetSdkVersion < 30 && parcelableCall.getState() == 12) {
            Log.i(this, "removing audio processing call during update for sdk compatibility", new Object[0]);
            Call callById = getCallById(parcelableCall.getId());
            if (callById != null) {
                internalRemoveCall(callById);
                return;
            }
            return;
        }
        Call callById2 = getCallById(parcelableCall.getId());
        if (callById2 != null) {
            checkCallTree(parcelableCall);
            callById2.internalUpdate(parcelableCall, this.mCallByTelecomCallId);
        } else if (this.mTargetSdkVersion < 30) {
            if (parcelableCall.getState() == 4 || parcelableCall.getState() == 13) {
                Log.i(this, "adding call during update for sdk compatibility", new Object[0]);
                internalAddCall(parcelableCall);
            }
        }
    }

    Call getCallById(String str) {
        Call call;
        synchronized (this.mLock) {
            call = this.mCallByTelecomCallId.get(str);
        }
        return call;
    }

    final void internalSetPostDialWait(String str, String str2) {
        Call callById = getCallById(str);
        if (callById != null) {
            callById.internalSetPostDialWait(str2);
        }
    }

    final void internalCallAudioStateChanged(CallAudioState callAudioState) {
        if (Objects.equals(this.mCallAudioState, callAudioState)) {
            return;
        }
        this.mCallAudioState = callAudioState;
        fireCallAudioStateChanged(callAudioState);
    }

    final Call internalGetCallByTelecomId(String str) {
        return getCallById(str);
    }

    final void internalBringToForeground(boolean z) {
        fireBringToForeground(z);
    }

    final void internalSetCanAddCall(boolean z) {
        if (this.mCanAddCall != z) {
            this.mCanAddCall = z;
            fireCanAddCallChanged(z);
        }
    }

    final void internalSilenceRinger() {
        fireSilenceRinger();
    }

    final void internalOnConnectionEvent(String str, String str2, Bundle bundle) {
        Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnConnectionEvent(str2, bundle);
        }
    }

    final void internalOnRttUpgradeRequest(String str, int i) {
        Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnRttUpgradeRequest(i);
        }
    }

    final void internalOnRttInitiationFailure(String str, int i) {
        Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnRttInitiationFailure(i);
        }
    }

    final void internalOnHandoverFailed(String str, int i) {
        Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnHandoverFailed(i);
        }
    }

    final void internalOnHandoverComplete(String str) {
        Call callById = getCallById(str);
        if (callById != null) {
            callById.internalOnHandoverComplete();
        }
    }

    final void destroy() {
        for (Call call : this.mCalls) {
            InCallService.VideoCall videoCall = call.getVideoCall();
            if (videoCall != null) {
                videoCall.destroy();
            }
            if (call.getState() != 7) {
                call.internalSetDisconnected();
            }
        }
    }

    public final void addListener(Listener listener) {
        this.mListeners.add(listener);
    }

    public final void removeListener(Listener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
        }
    }

    public final List<Call> getCalls() {
        return this.mUnmodifiableCalls;
    }

    public final boolean canAddCall() {
        return this.mCanAddCall;
    }

    public final void setMuted(boolean z) {
        this.mInCallAdapter.mute(z);
    }

    public final void setAudioRoute(int i) {
        this.mInCallAdapter.setAudioRoute(i);
    }

    public void requestBluetoothAudio(String str) {
        this.mInCallAdapter.requestBluetoothAudio(str);
    }

    public void requestCallEndpointChange(CallEndpoint callEndpoint, Executor executor, OutcomeReceiver<Void, CallEndpointException> outcomeReceiver) {
        this.mInCallAdapter.requestCallEndpointChange(callEndpoint, executor, outcomeReceiver);
    }

    public final void setProximitySensorOn() {
        this.mInCallAdapter.turnProximitySensorOn();
    }

    public final void setProximitySensorOff(boolean z) {
        this.mInCallAdapter.turnProximitySensorOff(z);
    }

    @Deprecated
    public final AudioState getAudioState() {
        return new AudioState(this.mCallAudioState);
    }

    public final CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    private void fireCallAdded(Call call) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallAdded(this, call);
        }
    }

    private void fireCallRemoved(Call call) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCallRemoved(this, call);
        }
    }

    private void fireCallAudioStateChanged(CallAudioState callAudioState) {
        for (Listener listener : this.mListeners) {
            listener.onCallAudioStateChanged(this, callAudioState);
            listener.onAudioStateChanged(this, new AudioState(callAudioState));
        }
    }

    private void fireBringToForeground(boolean z) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onBringToForeground(this, z);
        }
    }

    private void fireCanAddCallChanged(boolean z) {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCanAddCallChanged(this, z);
        }
    }

    private void fireSilenceRinger() {
        Iterator<Listener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onSilenceRinger(this);
        }
    }

    private void checkCallTree(ParcelableCall parcelableCall) {
        if (parcelableCall.getChildCallIds() != null) {
            for (int i = 0; i < parcelableCall.getChildCallIds().size(); i++) {
                if (!this.mCallByTelecomCallId.containsKey(parcelableCall.getChildCallIds().get(i))) {
                    Log.wtf(this, "ParcelableCall %s has nonexistent child %s", parcelableCall.getId(), parcelableCall.getChildCallIds().get(i));
                }
            }
        }
    }
}
