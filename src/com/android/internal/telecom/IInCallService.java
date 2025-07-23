package com.android.internal.telecom;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.CallEndpoint;
import android.telecom.ParcelableCall;
import com.android.internal.telecom.IInCallAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IInCallService extends IInterface {

    public static class Default implements IInCallService {
        @Override // com.android.internal.telecom.IInCallService
        public void addCall(ParcelableCall parcelableCall) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IInCallService
        public void bringToForeground(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onAvailableCallEndpointsChanged(List<CallEndpoint> list) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCallEndpointChanged(CallEndpoint callEndpoint) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onCanAddCallChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onConnectionEvent(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onHandoverComplete(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onHandoverFailed(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onMuteStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onRttInitiationFailure(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void onRttUpgradeRequest(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setInCallAdapter(IInCallAdapter iInCallAdapter) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setPostDial(String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void setPostDialWait(String str, String str2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void silenceRinger() throws RemoteException {
        }

        @Override // com.android.internal.telecom.IInCallService
        public void updateCall(ParcelableCall parcelableCall) throws RemoteException {
        }
    }

    void addCall(ParcelableCall parcelableCall) throws RemoteException;

    void bringToForeground(boolean z) throws RemoteException;

    void onAvailableCallEndpointsChanged(List<CallEndpoint> list) throws RemoteException;

    void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException;

    void onCallEndpointChanged(CallEndpoint callEndpoint) throws RemoteException;

    void onCanAddCallChanged(boolean z) throws RemoteException;

    void onConnectionEvent(String str, String str2, Bundle bundle) throws RemoteException;

    void onHandoverComplete(String str) throws RemoteException;

    void onHandoverFailed(String str, int i) throws RemoteException;

    void onMuteStateChanged(boolean z) throws RemoteException;

    void onRttInitiationFailure(String str, int i) throws RemoteException;

    void onRttUpgradeRequest(String str, int i) throws RemoteException;

    void setInCallAdapter(IInCallAdapter iInCallAdapter) throws RemoteException;

    void setPostDial(String str, String str2) throws RemoteException;

    void setPostDialWait(String str, String str2) throws RemoteException;

    void silenceRinger() throws RemoteException;

    void updateCall(ParcelableCall parcelableCall) throws RemoteException;

    public static abstract class Stub extends Binder implements IInCallService {
        public static final String DESCRIPTOR = "com.android.internal.telecom.IInCallService";
        static final int TRANSACTION_addCall = 2;
        static final int TRANSACTION_bringToForeground = 10;
        static final int TRANSACTION_onAvailableCallEndpointsChanged = 8;
        static final int TRANSACTION_onCallAudioStateChanged = 6;
        static final int TRANSACTION_onCallEndpointChanged = 7;
        static final int TRANSACTION_onCanAddCallChanged = 11;
        static final int TRANSACTION_onConnectionEvent = 13;
        static final int TRANSACTION_onHandoverComplete = 17;
        static final int TRANSACTION_onHandoverFailed = 16;
        static final int TRANSACTION_onMuteStateChanged = 9;
        static final int TRANSACTION_onRttInitiationFailure = 15;
        static final int TRANSACTION_onRttUpgradeRequest = 14;
        static final int TRANSACTION_setInCallAdapter = 1;
        static final int TRANSACTION_setPostDial = 4;
        static final int TRANSACTION_setPostDialWait = 5;
        static final int TRANSACTION_silenceRinger = 12;
        static final int TRANSACTION_updateCall = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInCallService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInCallService)) {
                return (IInCallService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setInCallAdapter";
                case 2:
                    return "addCall";
                case 3:
                    return "updateCall";
                case 4:
                    return "setPostDial";
                case 5:
                    return "setPostDialWait";
                case 6:
                    return "onCallAudioStateChanged";
                case 7:
                    return "onCallEndpointChanged";
                case 8:
                    return "onAvailableCallEndpointsChanged";
                case 9:
                    return "onMuteStateChanged";
                case 10:
                    return "bringToForeground";
                case 11:
                    return "onCanAddCallChanged";
                case 12:
                    return "silenceRinger";
                case 13:
                    return "onConnectionEvent";
                case 14:
                    return "onRttUpgradeRequest";
                case 15:
                    return "onRttInitiationFailure";
                case 16:
                    return "onHandoverFailed";
                case 17:
                    return "onHandoverComplete";
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
                    IInCallAdapter asInterface = IInCallAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInCallAdapter(asInterface);
                    return true;
                case 2:
                    ParcelableCall parcelableCall = (ParcelableCall) parcel.readTypedObject(ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    addCall(parcelableCall);
                    return true;
                case 3:
                    ParcelableCall parcelableCall2 = (ParcelableCall) parcel.readTypedObject(ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCall(parcelableCall2);
                    return true;
                case 4:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPostDial(readString, readString2);
                    return true;
                case 5:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setPostDialWait(readString3, readString4);
                    return true;
                case 6:
                    CallAudioState callAudioState = (CallAudioState) parcel.readTypedObject(CallAudioState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallAudioStateChanged(callAudioState);
                    return true;
                case 7:
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(callEndpoint);
                    return true;
                case 8:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(createTypedArrayList);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(readBoolean);
                    return true;
                case 10:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    bringToForeground(readBoolean2);
                    return true;
                case 11:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCanAddCallChanged(readBoolean3);
                    return true;
                case 12:
                    silenceRinger();
                    return true;
                case 13:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionEvent(readString5, readString6, bundle);
                    return true;
                case 14:
                    String readString7 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRttUpgradeRequest(readString7, readInt);
                    return true;
                case 15:
                    String readString8 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRttInitiationFailure(readString8, readInt2);
                    return true;
                case 16:
                    String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onHandoverFailed(readString9, readInt3);
                    return true;
                case 17:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onHandoverComplete(readString10);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IInCallService {
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

            @Override // com.android.internal.telecom.IInCallService
            public void setInCallAdapter(IInCallAdapter iInCallAdapter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iInCallAdapter);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void addCall(ParcelableCall parcelableCall) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void updateCall(ParcelableCall parcelableCall) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void setPostDial(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void setPostDialWait(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callAudioState, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onCallEndpointChanged(CallEndpoint callEndpoint) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callEndpoint, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onAvailableCallEndpointsChanged(List<CallEndpoint> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onMuteStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void bringToForeground(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onCanAddCallChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void silenceRinger() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onConnectionEvent(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onRttUpgradeRequest(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onRttInitiationFailure(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onHandoverFailed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IInCallService
            public void onHandoverComplete(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
