package com.sec.ims.volte2;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import com.sec.ims.Dialog;
import com.sec.ims.ImsRegistration;
import com.sec.ims.volte2.IImsCallSessionEventListener;
import com.sec.ims.volte2.IImsMediaCallProvider;
import com.sec.ims.volte2.data.CallProfile;
import com.sec.ims.volte2.data.MediaProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IImsCallSession extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsCallSession";

    void accept(CallProfile callProfile);

    int acceptECTRequest();

    void cancelTransfer();

    void extendToConference(String[] strArr);

    int getCallId();

    CallProfile getCallProfile();

    int getCallStateOrdinal();

    int getCmcType();

    int getEndReason();

    String getIncomingInviteRawSip();

    IImsMediaCallProvider getMediaCallProvider();

    CallProfile getModifyRequestedProfile();

    int getPhoneId();

    int getPrevCallStateOrdinal();

    ImsRegistration getRegistration();

    boolean getRelayChTerminated();

    int getSessionId();

    boolean getUsingCamera();

    int getVideoCrbtSupportType();

    void hold(MediaProfile mediaProfile);

    void holdVideo();

    void info(int i, String str);

    void inviteGroupParticipant(String str);

    void inviteParticipants(int i);

    boolean isQuantumEncryptionServiceAvailable();

    void merge(int i, int i2);

    int pulling(String str, Dialog dialog);

    void recording(int i, String str);

    void registerSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener);

    void reinvite();

    void reject(int i);

    int rejectECTRequest();

    void removeCallStateMachineMessage(int i);

    void removeGroupParticipant(String str);

    void removeParticipants(int i);

    void requestCallDataUsage();

    void resume();

    void resumeVideo();

    void sendDtmf(int i, int i2, Message message);

    void sendImsCallEvent(String str, Bundle bundle);

    void sendText(String str, int i);

    void setEpdgState(boolean z);

    void setEpdgStateNoNotify(boolean z);

    void setMute(boolean z);

    void setRelayChTerminated(boolean z);

    int start(String str, CallProfile callProfile);

    void startCameraForProvider(int i);

    void startConference(String[] strArr, CallProfile callProfile);

    void startDtmf(int i);

    int startECT(int i, String str);

    void stopCameraForProvider(boolean z);

    void stopDtmf();

    void terminate(int i);

    void transfer(String str);

    void unregisterSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener);

    void update(CallProfile callProfile, int i, String str);

    void updateQuantumPeerProfileStatus(int i, String str, String str2, String str3);

    void updateQuantumQMKeyStatus(int i, String str, String str2, byte[] bArr, String str3);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IImsCallSession {
        @Override // com.sec.ims.volte2.IImsCallSession
        public int acceptECTRequest() {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getCallId() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public CallProfile getCallProfile() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getCallStateOrdinal() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getCmcType() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getEndReason() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public String getIncomingInviteRawSip() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public IImsMediaCallProvider getMediaCallProvider() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public CallProfile getModifyRequestedProfile() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getPhoneId() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getPrevCallStateOrdinal() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public ImsRegistration getRegistration() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public boolean getRelayChTerminated() {
            return false;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getSessionId() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public boolean getUsingCamera() {
            return false;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getVideoCrbtSupportType() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public boolean isQuantumEncryptionServiceAvailable() {
            return false;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int pulling(String str, Dialog dialog) {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int rejectECTRequest() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int start(String str, CallProfile callProfile) {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int startECT(int i, String str) {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void cancelTransfer() {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void holdVideo() {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void reinvite() {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void requestCallDataUsage() {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void resume() {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void resumeVideo() {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void stopDtmf() {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void accept(CallProfile callProfile) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void extendToConference(String[] strArr) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void hold(MediaProfile mediaProfile) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void inviteGroupParticipant(String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void inviteParticipants(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void registerSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void reject(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void removeCallStateMachineMessage(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void removeGroupParticipant(String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void removeParticipants(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setEpdgState(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setEpdgStateNoNotify(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setMute(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setRelayChTerminated(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void startCameraForProvider(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void startDtmf(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void stopCameraForProvider(boolean z) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void terminate(int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void transfer(String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void unregisterSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void info(int i, String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void merge(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void recording(int i, String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void sendImsCallEvent(String str, Bundle bundle) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void sendText(String str, int i) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void startConference(String[] strArr, CallProfile callProfile) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void sendDtmf(int i, int i2, Message message) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void update(CallProfile callProfile, int i, String str) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void updateQuantumPeerProfileStatus(int i, String str, String str2, String str3) {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void updateQuantumQMKeyStatus(int i, String str, String str2, byte[] bArr, String str3) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IImsCallSession {
        static final int TRANSACTION_accept = 13;
        static final int TRANSACTION_acceptECTRequest = 38;
        static final int TRANSACTION_cancelTransfer = 23;
        static final int TRANSACTION_extendToConference = 36;
        static final int TRANSACTION_getCallId = 5;
        static final int TRANSACTION_getCallProfile = 1;
        static final int TRANSACTION_getCallStateOrdinal = 6;
        static final int TRANSACTION_getCmcType = 51;
        static final int TRANSACTION_getEndReason = 10;
        static final int TRANSACTION_getIncomingInviteRawSip = 25;
        static final int TRANSACTION_getMediaCallProvider = 45;
        static final int TRANSACTION_getModifyRequestedProfile = 2;
        static final int TRANSACTION_getPhoneId = 9;
        static final int TRANSACTION_getPrevCallStateOrdinal = 7;
        static final int TRANSACTION_getRegistration = 26;
        static final int TRANSACTION_getRelayChTerminated = 54;
        static final int TRANSACTION_getSessionId = 8;
        static final int TRANSACTION_getUsingCamera = 44;
        static final int TRANSACTION_getVideoCrbtSupportType = 52;
        static final int TRANSACTION_hold = 16;
        static final int TRANSACTION_holdVideo = 40;
        static final int TRANSACTION_info = 24;
        static final int TRANSACTION_inviteGroupParticipant = 34;
        static final int TRANSACTION_inviteParticipants = 32;
        static final int TRANSACTION_isQuantumEncryptionServiceAvailable = 55;
        static final int TRANSACTION_merge = 30;
        static final int TRANSACTION_pulling = 12;
        static final int TRANSACTION_recording = 21;
        static final int TRANSACTION_registerSessionEventListener = 3;
        static final int TRANSACTION_reinvite = 20;
        static final int TRANSACTION_reject = 14;
        static final int TRANSACTION_rejectECTRequest = 39;
        static final int TRANSACTION_removeCallStateMachineMessage = 58;
        static final int TRANSACTION_removeGroupParticipant = 35;
        static final int TRANSACTION_removeParticipants = 33;
        static final int TRANSACTION_requestCallDataUsage = 46;
        static final int TRANSACTION_resume = 17;
        static final int TRANSACTION_resumeVideo = 41;
        static final int TRANSACTION_sendDtmf = 47;
        static final int TRANSACTION_sendImsCallEvent = 29;
        static final int TRANSACTION_sendText = 50;
        static final int TRANSACTION_setEpdgState = 27;
        static final int TRANSACTION_setEpdgStateNoNotify = 28;
        static final int TRANSACTION_setMute = 19;
        static final int TRANSACTION_setRelayChTerminated = 53;
        static final int TRANSACTION_start = 11;
        static final int TRANSACTION_startCameraForProvider = 42;
        static final int TRANSACTION_startConference = 31;
        static final int TRANSACTION_startDtmf = 48;
        static final int TRANSACTION_startECT = 37;
        static final int TRANSACTION_stopCameraForProvider = 43;
        static final int TRANSACTION_stopDtmf = 49;
        static final int TRANSACTION_terminate = 15;
        static final int TRANSACTION_transfer = 22;
        static final int TRANSACTION_unregisterSessionEventListener = 4;
        static final int TRANSACTION_update = 18;
        static final int TRANSACTION_updateQuantumPeerProfileStatus = 56;
        static final int TRANSACTION_updateQuantumQMKeyStatus = 57;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IImsCallSession {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void accept(CallProfile callProfile) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int acceptECTRequest() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void cancelTransfer() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void extendToConference(String[] strArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getCallId() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public CallProfile getCallProfile() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CallProfile) obtain2.readTypedObject(CallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getCallStateOrdinal() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getCmcType() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getEndReason() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public String getIncomingInviteRawSip() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsCallSession.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public IImsMediaCallProvider getMediaCallProvider() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsMediaCallProvider.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public CallProfile getModifyRequestedProfile() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CallProfile) obtain2.readTypedObject(CallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getPhoneId() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getPrevCallStateOrdinal() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public ImsRegistration getRegistration() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsRegistration) obtain2.readTypedObject(ImsRegistration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public boolean getRelayChTerminated() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getSessionId() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public boolean getUsingCamera() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getVideoCrbtSupportType() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void hold(MediaProfile mediaProfile) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeTypedObject(mediaProfile, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void holdVideo() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void info(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void inviteGroupParticipant(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void inviteParticipants(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public boolean isQuantumEncryptionServiceAvailable() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void merge(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int pulling(String str, Dialog dialog) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(dialog, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void recording(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void registerSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSessionEventListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void reinvite() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void reject(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int rejectECTRequest() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void removeCallStateMachineMessage(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void removeGroupParticipant(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void removeParticipants(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void requestCallDataUsage() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void resume() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void resumeVideo() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void sendDtmf(int i, int i2, Message message) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void sendImsCallEvent(String str, Bundle bundle) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void sendText(String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setEpdgState(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setEpdgStateNoNotify(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setMute(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setRelayChTerminated(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int start(String str, CallProfile callProfile) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void startCameraForProvider(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void startConference(String[] strArr, CallProfile callProfile) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void startDtmf(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int startECT(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void stopCameraForProvider(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void stopDtmf() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void terminate(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void transfer(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void unregisterSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSessionEventListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void update(CallProfile callProfile, int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeTypedObject(callProfile, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void updateQuantumPeerProfileStatus(int i, String str, String str2, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void updateQuantumQMKeyStatus(int i, String str, String str2, byte[] bArr, String str3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str3);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IImsCallSession.DESCRIPTOR);
        }

        public static IImsCallSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsCallSession.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IImsCallSession)) ? new Proxy(iBinder) : (IImsCallSession) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsCallSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsCallSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    CallProfile callProfile = getCallProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callProfile, 1);
                    return true;
                case 2:
                    CallProfile modifyRequestedProfile = getModifyRequestedProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(modifyRequestedProfile, 1);
                    return true;
                case 3:
                    IImsCallSessionEventListener asInterface = IImsCallSessionEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSessionEventListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IImsCallSessionEventListener asInterface2 = IImsCallSessionEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSessionEventListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int callId = getCallId();
                    parcel2.writeNoException();
                    parcel2.writeInt(callId);
                    return true;
                case 6:
                    int callStateOrdinal = getCallStateOrdinal();
                    parcel2.writeNoException();
                    parcel2.writeInt(callStateOrdinal);
                    return true;
                case 7:
                    int prevCallStateOrdinal = getPrevCallStateOrdinal();
                    parcel2.writeNoException();
                    parcel2.writeInt(prevCallStateOrdinal);
                    return true;
                case 8:
                    int sessionId = getSessionId();
                    parcel2.writeNoException();
                    parcel2.writeInt(sessionId);
                    return true;
                case 9:
                    int phoneId = getPhoneId();
                    parcel2.writeNoException();
                    parcel2.writeInt(phoneId);
                    return true;
                case 10:
                    int endReason = getEndReason();
                    parcel2.writeNoException();
                    parcel2.writeInt(endReason);
                    return true;
                case 11:
                    String readString = parcel.readString();
                    CallProfile callProfile2 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    int start = start(readString, callProfile2);
                    parcel2.writeNoException();
                    parcel2.writeInt(start);
                    return true;
                case 12:
                    String readString2 = parcel.readString();
                    Dialog dialog = (Dialog) parcel.readTypedObject(Dialog.CREATOR);
                    parcel.enforceNoDataAvail();
                    int pulling = pulling(readString2, dialog);
                    parcel2.writeNoException();
                    parcel2.writeInt(pulling);
                    return true;
                case 13:
                    CallProfile callProfile3 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    accept(callProfile3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reject(readInt);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    terminate(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    MediaProfile mediaProfile = (MediaProfile) parcel.readTypedObject(MediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    hold(mediaProfile);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    resume();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    CallProfile callProfile4 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    int readInt3 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    update(callProfile4, readInt3, readString3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMute(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    reinvite();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt4 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    recording(readInt4, readString4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transfer(readString5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    cancelTransfer();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt5 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    info(readInt5, readString6);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String incomingInviteRawSip = getIncomingInviteRawSip();
                    parcel2.writeNoException();
                    parcel2.writeString(incomingInviteRawSip);
                    return true;
                case 26:
                    ImsRegistration registration = getRegistration();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registration, 1);
                    return true;
                case 27:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEpdgState(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEpdgStateNoNotify(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString7 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendImsCallEvent(readString7, bundle);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    merge(readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String[] createStringArray = parcel.createStringArray();
                    CallProfile callProfile5 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    startConference(createStringArray, callProfile5);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    inviteParticipants(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeParticipants(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    inviteGroupParticipant(readString8);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeGroupParticipant(readString9);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    extendToConference(createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int readInt10 = parcel.readInt();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startECT = startECT(readInt10, readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(startECT);
                    return true;
                case 38:
                    int acceptECTRequest = acceptECTRequest();
                    parcel2.writeNoException();
                    parcel2.writeInt(acceptECTRequest);
                    return true;
                case 39:
                    int rejectECTRequest = rejectECTRequest();
                    parcel2.writeNoException();
                    parcel2.writeInt(rejectECTRequest);
                    return true;
                case 40:
                    holdVideo();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    resumeVideo();
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startCameraForProvider(readInt11);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    stopCameraForProvider(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean usingCamera = getUsingCamera();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(usingCamera);
                    return true;
                case 45:
                    IImsMediaCallProvider mediaCallProvider = getMediaCallProvider();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(mediaCallProvider);
                    return true;
                case 46:
                    requestCallDataUsage();
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDtmf(readInt12, readInt13, message);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startDtmf(readInt14);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    stopDtmf();
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String readString11 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendText(readString11, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    int cmcType = getCmcType();
                    parcel2.writeNoException();
                    parcel2.writeInt(cmcType);
                    return true;
                case 52:
                    int videoCrbtSupportType = getVideoCrbtSupportType();
                    parcel2.writeNoException();
                    parcel2.writeInt(videoCrbtSupportType);
                    return true;
                case 53:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRelayChTerminated(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    boolean relayChTerminated = getRelayChTerminated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(relayChTerminated);
                    return true;
                case 55:
                    boolean isQuantumEncryptionServiceAvailable = isQuantumEncryptionServiceAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuantumEncryptionServiceAvailable);
                    return true;
                case 56:
                    int readInt16 = parcel.readInt();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateQuantumPeerProfileStatus(readInt16, readString12, readString13, readString14);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int readInt17 = parcel.readInt();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateQuantumQMKeyStatus(readInt17, readString15, readString16, createByteArray, readString17);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeCallStateMachineMessage(readInt18);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
