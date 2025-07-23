package com.android.ims.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.RtpHeaderExtension;
import android.telephony.ims.aidl.IImsCallSessionListener;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.internal.telephony.SemRILConstants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IImsCallSession extends IInterface {

    public static class Default implements IImsCallSession {
        @Override // com.android.ims.internal.IImsCallSession
        public void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void callSessionNotifyAnbr(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void cancelTransferCall() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void close() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void consultativeTransfer(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void deflect(String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void extendToConference(String[] strArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public String getCallId() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getCallProfile() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getLocalCallProfile() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public String getProperty(String str) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getRemoteCallProfile() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public int getState() throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public IImsVideoCallProvider getVideoCallProvider() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void hold(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void inviteParticipants(String[] strArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isInCall() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isMultiparty() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void merge() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void notifyReadyToHandleImsCallbacks() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void reject(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void removeParticipants(String[] strArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void resume(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendDtmf(char c, Message message) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendImsCallEvent(String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRtpHeaderExtensions(List<RtpHeaderExtension> list) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttMessage(String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyRequest(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyResponse(boolean z) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendUssd(String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setMute(boolean z) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void start(String str, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startConference(String[] strArr, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startDtmf(char c) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void stopDtmf() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void terminate(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void transfer(String str, boolean z) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }
    }

    void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionNotifyAnbr(int i, int i2, int i3) throws RemoteException;

    void cancelTransferCall() throws RemoteException;

    void close() throws RemoteException;

    void consultativeTransfer(IImsCallSession iImsCallSession) throws RemoteException;

    void deflect(String str) throws RemoteException;

    void extendToConference(String[] strArr) throws RemoteException;

    String getCallId() throws RemoteException;

    ImsCallProfile getCallProfile() throws RemoteException;

    ImsCallProfile getLocalCallProfile() throws RemoteException;

    String getProperty(String str) throws RemoteException;

    ImsCallProfile getRemoteCallProfile() throws RemoteException;

    int getState() throws RemoteException;

    IImsVideoCallProvider getVideoCallProvider() throws RemoteException;

    void hold(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void inviteParticipants(String[] strArr) throws RemoteException;

    boolean isInCall() throws RemoteException;

    boolean isMultiparty() throws RemoteException;

    void merge() throws RemoteException;

    void notifyReadyToHandleImsCallbacks() throws RemoteException;

    void reject(int i) throws RemoteException;

    void removeParticipants(String[] strArr) throws RemoteException;

    void resume(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void sendDtmf(char c, Message message) throws RemoteException;

    void sendImsCallEvent(String str, Bundle bundle) throws RemoteException;

    void sendRtpHeaderExtensions(List<RtpHeaderExtension> list) throws RemoteException;

    void sendRttMessage(String str) throws RemoteException;

    void sendRttModifyRequest(ImsCallProfile imsCallProfile) throws RemoteException;

    void sendRttModifyResponse(boolean z) throws RemoteException;

    void sendUssd(String str) throws RemoteException;

    @Deprecated
    void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws RemoteException;

    void setMute(boolean z) throws RemoteException;

    void start(String str, ImsCallProfile imsCallProfile) throws RemoteException;

    void startConference(String[] strArr, ImsCallProfile imsCallProfile) throws RemoteException;

    void startDtmf(char c) throws RemoteException;

    void stopDtmf() throws RemoteException;

    void terminate(int i) throws RemoteException;

    void transfer(String str, boolean z) throws RemoteException;

    void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsCallSession {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsCallSession";
        static final int TRANSACTION_accept = 13;
        static final int TRANSACTION_callSessionNotifyAnbr = 39;
        static final int TRANSACTION_cancelTransferCall = 36;
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_consultativeTransfer = 17;
        static final int TRANSACTION_deflect = 14;
        static final int TRANSACTION_extendToConference = 23;
        static final int TRANSACTION_getCallId = 2;
        static final int TRANSACTION_getCallProfile = 3;
        static final int TRANSACTION_getLocalCallProfile = 4;
        static final int TRANSACTION_getProperty = 6;
        static final int TRANSACTION_getRemoteCallProfile = 5;
        static final int TRANSACTION_getState = 7;
        static final int TRANSACTION_getVideoCallProvider = 30;
        static final int TRANSACTION_hold = 19;
        static final int TRANSACTION_inviteParticipants = 24;
        static final int TRANSACTION_isInCall = 8;
        static final int TRANSACTION_isMultiparty = 31;
        static final int TRANSACTION_merge = 21;
        static final int TRANSACTION_notifyReadyToHandleImsCallbacks = 38;
        static final int TRANSACTION_reject = 15;
        static final int TRANSACTION_removeParticipants = 25;
        static final int TRANSACTION_resume = 20;
        static final int TRANSACTION_sendDtmf = 26;
        static final int TRANSACTION_sendImsCallEvent = 37;
        static final int TRANSACTION_sendRtpHeaderExtensions = 35;
        static final int TRANSACTION_sendRttMessage = 34;
        static final int TRANSACTION_sendRttModifyRequest = 32;
        static final int TRANSACTION_sendRttModifyResponse = 33;
        static final int TRANSACTION_sendUssd = 29;
        static final int TRANSACTION_setListener = 9;
        static final int TRANSACTION_setMute = 10;
        static final int TRANSACTION_start = 11;
        static final int TRANSACTION_startConference = 12;
        static final int TRANSACTION_startDtmf = 27;
        static final int TRANSACTION_stopDtmf = 28;
        static final int TRANSACTION_terminate = 18;
        static final int TRANSACTION_transfer = 16;
        static final int TRANSACTION_update = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsCallSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsCallSession)) {
                return (IImsCallSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "close";
                case 2:
                    return "getCallId";
                case 3:
                    return "getCallProfile";
                case 4:
                    return "getLocalCallProfile";
                case 5:
                    return "getRemoteCallProfile";
                case 6:
                    return "getProperty";
                case 7:
                    return "getState";
                case 8:
                    return "isInCall";
                case 9:
                    return "setListener";
                case 10:
                    return "setMute";
                case 11:
                    return "start";
                case 12:
                    return "startConference";
                case 13:
                    return "accept";
                case 14:
                    return "deflect";
                case 15:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_REJECT;
                case 16:
                    return "transfer";
                case 17:
                    return "consultativeTransfer";
                case 18:
                    return "terminate";
                case 19:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_HOLD;
                case 20:
                    return "resume";
                case 21:
                    return "merge";
                case 22:
                    return "update";
                case 23:
                    return "extendToConference";
                case 24:
                    return "inviteParticipants";
                case 25:
                    return "removeParticipants";
                case 26:
                    return "sendDtmf";
                case 27:
                    return "startDtmf";
                case 28:
                    return "stopDtmf";
                case 29:
                    return "sendUssd";
                case 30:
                    return "getVideoCallProvider";
                case 31:
                    return "isMultiparty";
                case 32:
                    return "sendRttModifyRequest";
                case 33:
                    return "sendRttModifyResponse";
                case 34:
                    return "sendRttMessage";
                case 35:
                    return "sendRtpHeaderExtensions";
                case 36:
                    return "cancelTransferCall";
                case 37:
                    return "sendImsCallEvent";
                case 38:
                    return "notifyReadyToHandleImsCallbacks";
                case 39:
                    return "callSessionNotifyAnbr";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String callId = getCallId();
                    parcel2.writeNoException();
                    parcel2.writeString(callId);
                    return true;
                case 3:
                    ImsCallProfile callProfile = getCallProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callProfile, 1);
                    return true;
                case 4:
                    ImsCallProfile localCallProfile = getLocalCallProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(localCallProfile, 1);
                    return true;
                case 5:
                    ImsCallProfile remoteCallProfile = getRemoteCallProfile();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(remoteCallProfile, 1);
                    return true;
                case 6:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String property = getProperty(readString);
                    parcel2.writeNoException();
                    parcel2.writeString(property);
                    return true;
                case 7:
                    int state = getState();
                    parcel2.writeNoException();
                    parcel2.writeInt(state);
                    return true;
                case 8:
                    boolean isInCall = isInCall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInCall);
                    return true;
                case 9:
                    android.telephony.ims.aidl.IImsCallSessionListener asInterface = IImsCallSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMute(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString2 = parcel.readString();
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    start(readString2, imsCallProfile);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String[] createStringArray = parcel.createStringArray();
                    ImsCallProfile imsCallProfile2 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    startConference(createStringArray, imsCallProfile2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt = parcel.readInt();
                    ImsStreamMediaProfile imsStreamMediaProfile = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    accept(readInt, imsStreamMediaProfile);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deflect(readString3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reject(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    transfer(readString4, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IImsCallSession asInterface2 = asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    consultativeTransfer(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    terminate(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ImsStreamMediaProfile imsStreamMediaProfile2 = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    hold(imsStreamMediaProfile2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    ImsStreamMediaProfile imsStreamMediaProfile3 = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    resume(imsStreamMediaProfile3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    merge();
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt4 = parcel.readInt();
                    ImsStreamMediaProfile imsStreamMediaProfile4 = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    update(readInt4, imsStreamMediaProfile4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    extendToConference(createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    inviteParticipants(createStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    removeParticipants(createStringArray4);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    char readInt5 = (char) parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDtmf(readInt5, message);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    char readInt6 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startDtmf(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    stopDtmf();
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendUssd(readString5);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IImsVideoCallProvider videoCallProvider = getVideoCallProvider();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(videoCallProvider);
                    return true;
                case 31:
                    boolean isMultiparty = isMultiparty();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMultiparty);
                    return true;
                case 32:
                    ImsCallProfile imsCallProfile3 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendRttModifyRequest(imsCallProfile3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttModifyResponse(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRttMessage(readString6);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(RtpHeaderExtension.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendRtpHeaderExtensions(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    cancelTransferCall();
                    parcel2.writeNoException();
                    return true;
                case 37:
                    String readString7 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendImsCallEvent(readString7, bundle);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    notifyReadyToHandleImsCallbacks();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionNotifyAnbr(readInt7, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsCallSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public String getCallId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public ImsCallProfile getCallProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsCallProfile) obtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public ImsCallProfile getLocalCallProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsCallProfile) obtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public ImsCallProfile getRemoteCallProfile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsCallProfile) obtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public String getProperty(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public int getState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public boolean isInCall() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSessionListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void setMute(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void start(String str, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void startConference(String[] strArr, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void deflect(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void reject(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void transfer(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void consultativeTransfer(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void terminate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void hold(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void resume(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void merge() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void extendToConference(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void inviteParticipants(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void removeParticipants(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendDtmf(char c, Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void startDtmf(char c) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(c);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void stopDtmf() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendUssd(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public IImsVideoCallProvider getVideoCallProvider() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsVideoCallProvider.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public boolean isMultiparty() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttModifyRequest(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttModifyResponse(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttMessage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRtpHeaderExtensions(List<RtpHeaderExtension> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void cancelTransferCall() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendImsCallEvent(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void notifyReadyToHandleImsCallbacks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void callSessionNotifyAnbr(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
