package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telecom.CallEndpoint;
import android.telecom.PhoneAccountHandle;
import android.telephony.ims.ImsCallProfile;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IInCallAdapter extends IInterface {

    public static class Default implements IInCallAdapter {
        @Override // com.android.internal.telecom.IInCallAdapter
        public void addConferenceParticipants(String str, List<Uri> list) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void answerCall(String str, int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void conference(String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void consultativeTransfer(String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void deflectCall(String str, Uri uri) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void disconnectCall(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void enterBackgroundAudioProcessing(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void exitBackgroundAudioProcessing(String str, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void handoverTo(String str, PhoneAccountHandle phoneAccountHandle, int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void holdCall(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void mergeConference(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void mute(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void phoneAccountSelected(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void playDtmfTone(String str, char c) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void postDialContinue(String str, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void pullExternalCall(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void putExtras(String str, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void rejectCall(String str, boolean z, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void rejectCallWithReason(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void removeExtras(String str, List<String> list) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void respondToRttRequest(String str, int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void sendCallEvent(String str, String str2, int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void sendRttRequest(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void setAudioRoute(int i, String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void setRttMode(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void splitFromConference(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void stopDtmfTone(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void stopRtt(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void swapConference(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void transferCall(String str, Uri uri, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void turnOffProximitySensor(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void turnOnProximitySensor() throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallAdapter
        public void unholdCall(String str) throws RemoteException {
        }
    }

    void addConferenceParticipants(String str, List<Uri> list) throws RemoteException;

    void answerCall(String str, int i) throws RemoteException;

    void conference(String str, String str2) throws RemoteException;

    void consultativeTransfer(String str, String str2) throws RemoteException;

    void deflectCall(String str, Uri uri) throws RemoteException;

    void disconnectCall(String str) throws RemoteException;

    void enterBackgroundAudioProcessing(String str) throws RemoteException;

    void exitBackgroundAudioProcessing(String str, boolean z) throws RemoteException;

    void handoverTo(String str, PhoneAccountHandle phoneAccountHandle, int i, Bundle bundle) throws RemoteException;

    void holdCall(String str) throws RemoteException;

    void mergeConference(String str) throws RemoteException;

    void mute(boolean z) throws RemoteException;

    void phoneAccountSelected(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException;

    void playDtmfTone(String str, char c) throws RemoteException;

    void postDialContinue(String str, boolean z) throws RemoteException;

    void pullExternalCall(String str) throws RemoteException;

    void putExtras(String str, Bundle bundle) throws RemoteException;

    void rejectCall(String str, boolean z, String str2) throws RemoteException;

    void rejectCallWithReason(String str, int i) throws RemoteException;

    void removeExtras(String str, List<String> list) throws RemoteException;

    void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException;

    void respondToRttRequest(String str, int i, boolean z) throws RemoteException;

    void sendCallEvent(String str, String str2, int i, Bundle bundle) throws RemoteException;

    void sendRttRequest(String str) throws RemoteException;

    void setAudioRoute(int i, String str) throws RemoteException;

    void setRttMode(String str, int i) throws RemoteException;

    void splitFromConference(String str) throws RemoteException;

    void stopDtmfTone(String str) throws RemoteException;

    void stopRtt(String str) throws RemoteException;

    void swapConference(String str) throws RemoteException;

    void transferCall(String str, Uri uri, boolean z) throws RemoteException;

    void turnOffProximitySensor(boolean z) throws RemoteException;

    void turnOnProximitySensor() throws RemoteException;

    void unholdCall(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IInCallAdapter {
        public static final String DESCRIPTOR = "com.android.internal.telecom.IInCallAdapter";
        static final int TRANSACTION_addConferenceParticipants = 23;
        static final int TRANSACTION_answerCall = 1;
        static final int TRANSACTION_conference = 19;
        static final int TRANSACTION_consultativeTransfer = 6;
        static final int TRANSACTION_deflectCall = 2;
        static final int TRANSACTION_disconnectCall = 7;
        static final int TRANSACTION_enterBackgroundAudioProcessing = 13;
        static final int TRANSACTION_exitBackgroundAudioProcessing = 14;
        static final int TRANSACTION_handoverTo = 34;
        static final int TRANSACTION_holdCall = 8;
        static final int TRANSACTION_mergeConference = 21;
        static final int TRANSACTION_mute = 10;
        static final int TRANSACTION_phoneAccountSelected = 18;
        static final int TRANSACTION_playDtmfTone = 15;
        static final int TRANSACTION_postDialContinue = 17;
        static final int TRANSACTION_pullExternalCall = 26;
        static final int TRANSACTION_putExtras = 28;
        static final int TRANSACTION_rejectCall = 3;
        static final int TRANSACTION_rejectCallWithReason = 4;
        static final int TRANSACTION_removeExtras = 29;
        static final int TRANSACTION_requestCallEndpointChange = 12;
        static final int TRANSACTION_respondToRttRequest = 31;
        static final int TRANSACTION_sendCallEvent = 27;
        static final int TRANSACTION_sendRttRequest = 30;
        static final int TRANSACTION_setAudioRoute = 11;
        static final int TRANSACTION_setRttMode = 33;
        static final int TRANSACTION_splitFromConference = 20;
        static final int TRANSACTION_stopDtmfTone = 16;
        static final int TRANSACTION_stopRtt = 32;
        static final int TRANSACTION_swapConference = 22;
        static final int TRANSACTION_transferCall = 5;
        static final int TRANSACTION_turnOffProximitySensor = 25;
        static final int TRANSACTION_turnOnProximitySensor = 24;
        static final int TRANSACTION_unholdCall = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 33;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInCallAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInCallAdapter)) {
                return (IInCallAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "answerCall";
                case 2:
                    return "deflectCall";
                case 3:
                    return "rejectCall";
                case 4:
                    return "rejectCallWithReason";
                case 5:
                    return "transferCall";
                case 6:
                    return "consultativeTransfer";
                case 7:
                    return "disconnectCall";
                case 8:
                    return "holdCall";
                case 9:
                    return "unholdCall";
                case 10:
                    return "mute";
                case 11:
                    return "setAudioRoute";
                case 12:
                    return "requestCallEndpointChange";
                case 13:
                    return "enterBackgroundAudioProcessing";
                case 14:
                    return "exitBackgroundAudioProcessing";
                case 15:
                    return "playDtmfTone";
                case 16:
                    return "stopDtmfTone";
                case 17:
                    return "postDialContinue";
                case 18:
                    return "phoneAccountSelected";
                case 19:
                    return ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED;
                case 20:
                    return "splitFromConference";
                case 21:
                    return "mergeConference";
                case 22:
                    return "swapConference";
                case 23:
                    return "addConferenceParticipants";
                case 24:
                    return "turnOnProximitySensor";
                case 25:
                    return "turnOffProximitySensor";
                case 26:
                    return "pullExternalCall";
                case 27:
                    return "sendCallEvent";
                case 28:
                    return "putExtras";
                case 29:
                    return "removeExtras";
                case 30:
                    return "sendRttRequest";
                case 31:
                    return "respondToRttRequest";
                case 32:
                    return "stopRtt";
                case 33:
                    return "setRttMode";
                case 34:
                    return "handoverTo";
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
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    answerCall(readString, readInt);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    deflectCall(readString2, uri);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rejectCall(readString3, readBoolean, readString4);
                    return true;
                case 4:
                    String readString5 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rejectCallWithReason(readString5, readInt2);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    transferCall(readString6, uri2, readBoolean2);
                    return true;
                case 6:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    consultativeTransfer(readString7, readString8);
                    return true;
                case 7:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disconnectCall(readString9);
                    return true;
                case 8:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    holdCall(readString10);
                    return true;
                case 9:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unholdCall(readString11);
                    return true;
                case 10:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    mute(readBoolean3);
                    return true;
                case 11:
                    int readInt3 = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setAudioRoute(readInt3, readString12);
                    return true;
                case 12:
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(callEndpoint, resultReceiver);
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    enterBackgroundAudioProcessing(readString13);
                    return true;
                case 14:
                    String readString14 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    exitBackgroundAudioProcessing(readString14, readBoolean4);
                    return true;
                case 15:
                    String readString15 = parcel.readString();
                    char readInt4 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    playDtmfTone(readString15, readInt4);
                    return true;
                case 16:
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopDtmfTone(readString16);
                    return true;
                case 17:
                    String readString17 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    postDialContinue(readString17, readBoolean5);
                    return true;
                case 18:
                    String readString18 = parcel.readString();
                    PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    phoneAccountSelected(readString18, phoneAccountHandle, readBoolean6);
                    return true;
                case 19:
                    String readString19 = parcel.readString();
                    String readString20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    conference(readString19, readString20);
                    return true;
                case 20:
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    splitFromConference(readString21);
                    return true;
                case 21:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    mergeConference(readString22);
                    return true;
                case 22:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    swapConference(readString23);
                    return true;
                case 23:
                    String readString24 = parcel.readString();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceParticipants(readString24, createTypedArrayList);
                    return true;
                case 24:
                    turnOnProximitySensor();
                    return true;
                case 25:
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    turnOffProximitySensor(readBoolean7);
                    return true;
                case 26:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    pullExternalCall(readString25);
                    return true;
                case 27:
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCallEvent(readString26, readString27, readInt5, bundle);
                    return true;
                case 28:
                    String readString28 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    putExtras(readString28, bundle2);
                    return true;
                case 29:
                    String readString29 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    removeExtras(readString29, createStringArrayList);
                    return true;
                case 30:
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRttRequest(readString30);
                    return true;
                case 31:
                    String readString31 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    respondToRttRequest(readString31, readInt6, readBoolean8);
                    return true;
                case 32:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopRtt(readString32);
                    return true;
                case 33:
                    String readString33 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRttMode(readString33, readInt7);
                    return true;
                case 34:
                    String readString34 = parcel.readString();
                    PhoneAccountHandle phoneAccountHandle2 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    int readInt8 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverTo(readString34, phoneAccountHandle2, readInt8, bundle3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInCallAdapter {
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

            @Override // com.android.internal.telecom.IInCallAdapter
            public void answerCall(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void deflectCall(String str, Uri uri) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void rejectCall(String str, boolean z, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void rejectCallWithReason(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void transferCall(String str, Uri uri, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void consultativeTransfer(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void disconnectCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void holdCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void unholdCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void mute(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void setAudioRoute(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void enterBackgroundAudioProcessing(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void exitBackgroundAudioProcessing(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void playDtmfTone(String str, char c) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(c);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void stopDtmfTone(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void postDialContinue(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void phoneAccountSelected(String str, PhoneAccountHandle phoneAccountHandle, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void conference(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void splitFromConference(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void mergeConference(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void swapConference(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void addConferenceParticipants(String str, List<Uri> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void turnOnProximitySensor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void turnOffProximitySensor(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void pullExternalCall(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void sendCallEvent(String str, String str2, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void putExtras(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void removeExtras(String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void sendRttRequest(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void respondToRttRequest(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void stopRtt(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void setRttMode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallAdapter
            public void handoverTo(String str, PhoneAccountHandle phoneAccountHandle, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
