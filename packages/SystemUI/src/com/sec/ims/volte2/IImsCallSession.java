package com.sec.ims.volte2;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.Dialog;
import com.sec.ims.ImsRegistration;
import com.sec.ims.volte2.IImsCallSessionEventListener;
import com.sec.ims.volte2.IImsMediaCallProvider;
import com.sec.ims.volte2.data.CallProfile;
import com.sec.ims.volte2.data.MediaProfile;

/* loaded from: classes4.dex */
public interface IImsCallSession extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IImsCallSession";

    void accept(CallProfile callProfile) throws RemoteException;

    int acceptECTRequest() throws RemoteException;

    void cancelTransfer() throws RemoteException;

    void extendToConference(String[] strArr) throws RemoteException;

    int getCallId() throws RemoteException;

    CallProfile getCallProfile() throws RemoteException;

    int getCallStateOrdinal() throws RemoteException;

    int getCmcType() throws RemoteException;

    int getEndReason() throws RemoteException;

    String getIncomingInviteRawSip() throws RemoteException;

    IImsMediaCallProvider getMediaCallProvider() throws RemoteException;

    CallProfile getModifyRequestedProfile() throws RemoteException;

    int getPhoneId() throws RemoteException;

    int getPrevCallStateOrdinal() throws RemoteException;

    ImsRegistration getRegistration() throws RemoteException;

    boolean getRelayChTerminated() throws RemoteException;

    int getSessionId() throws RemoteException;

    boolean getUsingCamera() throws RemoteException;

    int getVideoCrbtSupportType() throws RemoteException;

    void hold(MediaProfile mediaProfile) throws RemoteException;

    void holdVideo() throws RemoteException;

    void info(int i, String str) throws RemoteException;

    void inviteGroupParticipant(String str) throws RemoteException;

    void inviteParticipants(int i) throws RemoteException;

    boolean isQuantumEncryptionServiceAvailable() throws RemoteException;

    void merge(int i, int i2) throws RemoteException;

    int pulling(String str, Dialog dialog) throws RemoteException;

    void recording(int i, String str) throws RemoteException;

    void registerSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) throws RemoteException;

    void reinvite() throws RemoteException;

    void reject(int i) throws RemoteException;

    int rejectECTRequest() throws RemoteException;

    void removeCallStateMachineMessage(int i) throws RemoteException;

    void removeGroupParticipant(String str) throws RemoteException;

    void removeParticipants(int i) throws RemoteException;

    void requestCallDataUsage() throws RemoteException;

    void resume() throws RemoteException;

    void resumeVideo() throws RemoteException;

    void sendDtmf(int i, int i2, Message message) throws RemoteException;

    void sendImsCallEvent(String str, Bundle bundle) throws RemoteException;

    void sendText(String str, int i) throws RemoteException;

    void setEpdgState(boolean z) throws RemoteException;

    void setEpdgStateNoNotify(boolean z) throws RemoteException;

    void setMute(boolean z) throws RemoteException;

    void setRelayChTerminated(boolean z) throws RemoteException;

    int start(String str, CallProfile callProfile) throws RemoteException;

    void startCameraForProvider(int i) throws RemoteException;

    void startConference(String[] strArr, CallProfile callProfile) throws RemoteException;

    void startDtmf(int i) throws RemoteException;

    int startECT(int i, String str) throws RemoteException;

    void stopCameraForProvider(boolean z) throws RemoteException;

    void stopDtmf() throws RemoteException;

    void terminate(int i) throws RemoteException;

    void transfer(String str) throws RemoteException;

    void unregisterSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) throws RemoteException;

    void update(CallProfile callProfile, int i, String str) throws RemoteException;

    void updateQuantumPeerProfileStatus(int i, String str, String str2, String str3) throws RemoteException;

    void updateQuantumQMKeyStatus(int i, String str, String str2, byte[] bArr, String str3) throws RemoteException;

    public class Default implements IImsCallSession {
        @Override // com.sec.ims.volte2.IImsCallSession
        public int acceptECTRequest() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getCallId() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public CallProfile getCallProfile() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getCallStateOrdinal() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getCmcType() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getEndReason() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public String getIncomingInviteRawSip() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public IImsMediaCallProvider getMediaCallProvider() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public CallProfile getModifyRequestedProfile() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getPhoneId() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getPrevCallStateOrdinal() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public ImsRegistration getRegistration() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public boolean getRelayChTerminated() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getSessionId() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public boolean getUsingCamera() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int getVideoCrbtSupportType() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public boolean isQuantumEncryptionServiceAvailable() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int pulling(String str, Dialog dialog) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int rejectECTRequest() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int start(String str, CallProfile callProfile) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public int startECT(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void cancelTransfer() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void holdVideo() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void reinvite() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void requestCallDataUsage() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void resume() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void resumeVideo() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void stopDtmf() throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void accept(CallProfile callProfile) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void extendToConference(String[] strArr) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void hold(MediaProfile mediaProfile) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void inviteGroupParticipant(String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void inviteParticipants(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void registerSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void reject(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void removeCallStateMachineMessage(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void removeGroupParticipant(String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void removeParticipants(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setEpdgState(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setEpdgStateNoNotify(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setMute(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void setRelayChTerminated(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void startCameraForProvider(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void startDtmf(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void stopCameraForProvider(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void terminate(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void transfer(String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void unregisterSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void info(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void merge(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void recording(int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void sendImsCallEvent(String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void sendText(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void startConference(String[] strArr, CallProfile callProfile) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void sendDtmf(int i, int i2, Message message) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void update(CallProfile callProfile, int i, String str) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void updateQuantumPeerProfileStatus(int i, String str, String str2, String str3) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IImsCallSession
        public void updateQuantumQMKeyStatus(int i, String str, String str2, byte[] bArr, String str3) throws RemoteException {
        }
    }

    public abstract class Stub extends Binder implements IImsCallSession {
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

        class Proxy implements IImsCallSession {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void accept(CallProfile callProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int acceptECTRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void cancelTransfer() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void extendToConference(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getCallId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public CallProfile getCallProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CallProfile) parcelObtain2.readTypedObject(CallProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getCallStateOrdinal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getCmcType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getEndReason() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public String getIncomingInviteRawSip() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IImsCallSession.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public IImsMediaCallProvider getMediaCallProvider() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsMediaCallProvider.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public CallProfile getModifyRequestedProfile() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CallProfile) parcelObtain2.readTypedObject(CallProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getPhoneId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getPrevCallStateOrdinal() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public ImsRegistration getRegistration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsRegistration) parcelObtain2.readTypedObject(ImsRegistration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public boolean getRelayChTerminated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getSessionId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public boolean getUsingCamera() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int getVideoCrbtSupportType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void hold(MediaProfile mediaProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(mediaProfile, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void holdVideo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void info(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void inviteGroupParticipant(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void inviteParticipants(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public boolean isQuantumEncryptionServiceAvailable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void merge(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int pulling(String str, Dialog dialog) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(dialog, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void recording(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void registerSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCallSessionEventListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void reinvite() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void reject(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int rejectECTRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void removeCallStateMachineMessage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void removeGroupParticipant(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void removeParticipants(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void requestCallDataUsage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void resume() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void resumeVideo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void sendDtmf(int i, int i2, Message message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(message, 0);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void sendImsCallEvent(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void sendText(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setEpdgState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setEpdgStateNoNotify(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setMute(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void setRelayChTerminated(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int start(String str, CallProfile callProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void startCameraForProvider(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void startConference(String[] strArr, CallProfile callProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void startDtmf(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public int startECT(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void stopCameraForProvider(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void stopDtmf() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void terminate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void transfer(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void unregisterSessionEventListener(IImsCallSessionEventListener iImsCallSessionEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCallSessionEventListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void update(CallProfile callProfile, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void updateQuantumPeerProfileStatus(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IImsCallSession
            public void updateQuantumQMKeyStatus(int i, String str, String str2, byte[] bArr, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsCallSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsCallSession.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IImsCallSession)) ? new Proxy(iBinder) : (IImsCallSession) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                    IImsCallSessionEventListener iImsCallSessionEventListenerAsInterface = IImsCallSessionEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerSessionEventListener(iImsCallSessionEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IImsCallSessionEventListener iImsCallSessionEventListenerAsInterface2 = IImsCallSessionEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterSessionEventListener(iImsCallSessionEventListenerAsInterface2);
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
                    String string = parcel.readString();
                    CallProfile callProfile2 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iStart = start(string, callProfile2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStart);
                    return true;
                case 12:
                    String string2 = parcel.readString();
                    Dialog dialog = (Dialog) parcel.readTypedObject(Dialog.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iPulling = pulling(string2, dialog);
                    parcel2.writeNoException();
                    parcel2.writeInt(iPulling);
                    return true;
                case 13:
                    CallProfile callProfile3 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    accept(callProfile3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reject(i3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    terminate(i4);
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
                    int i5 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    update(callProfile4, i5, string3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMute(z);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    reinvite();
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i6 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    recording(i6, string4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transfer(string5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    cancelTransfer();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i7 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    info(i7, string6);
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
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEpdgState(z2);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setEpdgStateNoNotify(z3);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String string7 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendImsCallEvent(string7, bundle);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    merge(i8, i9);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    CallProfile callProfile5 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    startConference(strArrCreateStringArray, callProfile5);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    inviteParticipants(i10);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeParticipants(i11);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    inviteGroupParticipant(string8);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeGroupParticipant(string9);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    extendToConference(strArrCreateStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i12 = parcel.readInt();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStartECT = startECT(i12, string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartECT);
                    return true;
                case 38:
                    int iAcceptECTRequest = acceptECTRequest();
                    parcel2.writeNoException();
                    parcel2.writeInt(iAcceptECTRequest);
                    return true;
                case 39:
                    int iRejectECTRequest = rejectECTRequest();
                    parcel2.writeNoException();
                    parcel2.writeInt(iRejectECTRequest);
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
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startCameraForProvider(i13);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    stopCameraForProvider(z4);
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
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDtmf(i14, i15, message);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startDtmf(i16);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    stopDtmf();
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String string11 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendText(string11, i17);
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
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setRelayChTerminated(z5);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    boolean relayChTerminated = getRelayChTerminated();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(relayChTerminated);
                    return true;
                case 55:
                    boolean zIsQuantumEncryptionServiceAvailable = isQuantumEncryptionServiceAvailable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsQuantumEncryptionServiceAvailable);
                    return true;
                case 56:
                    int i18 = parcel.readInt();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateQuantumPeerProfileStatus(i18, string12, string13, string14);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    int i19 = parcel.readInt();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateQuantumQMKeyStatus(i19, string15, string16, bArrCreateByteArray, string17);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeCallStateMachineMessage(i20);
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
