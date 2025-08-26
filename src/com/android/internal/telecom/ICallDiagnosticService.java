package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.BluetoothCallQualityReport;
import android.telecom.CallAudioState;
import android.telecom.DisconnectCause;
import android.telecom.ParcelableCall;
import android.telephony.CallQuality;
import com.android.internal.telecom.ICallDiagnosticServiceAdapter;

/* loaded from: classes4.dex */
public interface ICallDiagnosticService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallDiagnosticService";

    public static class Default implements ICallDiagnosticService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void callQualityChanged(String str, CallQuality callQuality) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void initializeDiagnosticCall(ParcelableCall parcelableCall) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void notifyCallDisconnected(String str, DisconnectCause disconnectCause) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveBluetoothCallQualityReport(BluetoothCallQualityReport bluetoothCallQualityReport) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void receiveDeviceToDeviceMessage(String str, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void removeDiagnosticCall(String str) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void setAdapter(ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCall(ParcelableCall parcelableCall) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallDiagnosticService
        public void updateCallAudioState(CallAudioState callAudioState) throws RemoteException {
        }
    }

    void callQualityChanged(String str, CallQuality callQuality) throws RemoteException;

    void initializeDiagnosticCall(ParcelableCall parcelableCall) throws RemoteException;

    void notifyCallDisconnected(String str, DisconnectCause disconnectCause) throws RemoteException;

    void receiveBluetoothCallQualityReport(BluetoothCallQualityReport bluetoothCallQualityReport) throws RemoteException;

    void receiveDeviceToDeviceMessage(String str, int i, int i2) throws RemoteException;

    void removeDiagnosticCall(String str) throws RemoteException;

    void setAdapter(ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws RemoteException;

    void updateCall(ParcelableCall parcelableCall) throws RemoteException;

    void updateCallAudioState(CallAudioState callAudioState) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallDiagnosticService {
        static final int TRANSACTION_callQualityChanged = 7;
        static final int TRANSACTION_initializeDiagnosticCall = 2;
        static final int TRANSACTION_notifyCallDisconnected = 9;
        static final int TRANSACTION_receiveBluetoothCallQualityReport = 8;
        static final int TRANSACTION_receiveDeviceToDeviceMessage = 6;
        static final int TRANSACTION_removeDiagnosticCall = 5;
        static final int TRANSACTION_setAdapter = 1;
        static final int TRANSACTION_updateCall = 3;
        static final int TRANSACTION_updateCallAudioState = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ICallDiagnosticService.DESCRIPTOR);
        }

        public static ICallDiagnosticService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICallDiagnosticService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICallDiagnosticService)) {
                return (ICallDiagnosticService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setAdapter";
                case 2:
                    return "initializeDiagnosticCall";
                case 3:
                    return "updateCall";
                case 4:
                    return "updateCallAudioState";
                case 5:
                    return "removeDiagnosticCall";
                case 6:
                    return "receiveDeviceToDeviceMessage";
                case 7:
                    return "callQualityChanged";
                case 8:
                    return "receiveBluetoothCallQualityReport";
                case 9:
                    return "notifyCallDisconnected";
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
                parcel.enforceInterface(ICallDiagnosticService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallDiagnosticService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapterAsInterface = ICallDiagnosticServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setAdapter(iCallDiagnosticServiceAdapterAsInterface);
                    return true;
                case 2:
                    ParcelableCall parcelableCall = (ParcelableCall) parcel.readTypedObject(ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    initializeDiagnosticCall(parcelableCall);
                    return true;
                case 3:
                    ParcelableCall parcelableCall2 = (ParcelableCall) parcel.readTypedObject(ParcelableCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCall(parcelableCall2);
                    return true;
                case 4:
                    CallAudioState callAudioState = (CallAudioState) parcel.readTypedObject(CallAudioState.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateCallAudioState(callAudioState);
                    return true;
                case 5:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeDiagnosticCall(string);
                    return true;
                case 6:
                    String string2 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    receiveDeviceToDeviceMessage(string2, i3, i4);
                    return true;
                case 7:
                    String string3 = parcel.readString();
                    CallQuality callQuality = (CallQuality) parcel.readTypedObject(CallQuality.CREATOR);
                    parcel.enforceNoDataAvail();
                    callQualityChanged(string3, callQuality);
                    return true;
                case 8:
                    BluetoothCallQualityReport bluetoothCallQualityReport = (BluetoothCallQualityReport) parcel.readTypedObject(BluetoothCallQualityReport.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveBluetoothCallQualityReport(bluetoothCallQualityReport);
                    return true;
                case 9:
                    String string4 = parcel.readString();
                    DisconnectCause disconnectCause = (DisconnectCause) parcel.readTypedObject(DisconnectCause.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyCallDisconnected(string4, disconnectCause);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICallDiagnosticService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallDiagnosticService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void setAdapter(ICallDiagnosticServiceAdapter iCallDiagnosticServiceAdapter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCallDiagnosticServiceAdapter);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void initializeDiagnosticCall(ParcelableCall parcelableCall) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void updateCall(ParcelableCall parcelableCall) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelableCall, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void updateCallAudioState(CallAudioState callAudioState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callAudioState, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void removeDiagnosticCall(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void receiveDeviceToDeviceMessage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void callQualityChanged(String str, CallQuality callQuality) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(callQuality, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void receiveBluetoothCallQualityReport(BluetoothCallQualityReport bluetoothCallQualityReport) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bluetoothCallQualityReport, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallDiagnosticService
            public void notifyCallDisconnected(String str, DisconnectCause disconnectCause) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallDiagnosticService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(disconnectCause, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
