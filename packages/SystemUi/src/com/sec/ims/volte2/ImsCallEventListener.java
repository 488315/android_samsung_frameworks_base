package com.sec.ims.volte2;

import com.sec.ims.volte2.IImsCallEventListener;
import com.sec.ims.volte2.data.ImsCallInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ImsCallEventListener extends IImsCallEventListener.Stub {
    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onAudioPathUpdated(String str) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallEstablished(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallModified(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallResumed(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallRinging(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallRingingBack(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallStarted(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallTrying(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onVideoAvailable(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onVideoHeld(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onVideoResumed(ImsCallInfo imsCallInfo) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallEnded(ImsCallInfo imsCallInfo, int i) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallModifyRequested(ImsCallInfo imsCallInfo, int i) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onConferenceParticipantAdded(ImsCallInfo imsCallInfo, String str) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onConferenceParticipantRemoved(ImsCallInfo imsCallInfo, String str) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onIncomingCall(ImsCallInfo imsCallInfo, String str) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onIncomingPreAlerting(ImsCallInfo imsCallInfo, String str) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onCallHeld(ImsCallInfo imsCallInfo, boolean z, boolean z2) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onDedicatedBearerEvent(ImsCallInfo imsCallInfo, int i, int i2) {
    }

    @Override // com.sec.ims.volte2.IImsCallEventListener
    public void onRtpLossRateNoti(int i, float f, float f2, int i2) {
    }
}
