package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.wifi.ISemMobileWipsFramework;
import com.samsung.android.wifi.ISemMobileWipsPacketSender;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemMobileWipsService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemMobileWipsService";

    public static class Default implements ISemMobileWipsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void broadcastBcnEventAbort(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void broadcastBcnIntervalEvent(String str, String str2, String str3, int i, int i2, long j, long j2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean checkMWIPS(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void onDnsResponses(List<String> list, String str) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void onScanResults(List<SemMobileWipsScanResult> list) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean registerCallback(ISemMobileWipsFramework iSemMobileWipsFramework) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean registerPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void sendMessage(Message message) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean setCurrentBss(String str, String str2, String str3, int i, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean unregisterCallback(ISemMobileWipsFramework iSemMobileWipsFramework) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public boolean unregisterPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsService
        public void updateWifiChipInfo(String str, String str2) throws RemoteException {
        }
    }

    void broadcastBcnEventAbort(String str, int i) throws RemoteException;

    void broadcastBcnIntervalEvent(String str, String str2, String str3, int i, int i2, long j, long j2) throws RemoteException;

    boolean checkMWIPS(String str, int i) throws RemoteException;

    void onDnsResponses(List<String> list, String str) throws RemoteException;

    void onScanResults(List<SemMobileWipsScanResult> list) throws RemoteException;

    boolean registerCallback(ISemMobileWipsFramework iSemMobileWipsFramework) throws RemoteException;

    boolean registerPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException;

    void sendMessage(Message message) throws RemoteException;

    boolean setCurrentBss(String str, String str2, String str3, int i, byte[] bArr) throws RemoteException;

    boolean unregisterCallback(ISemMobileWipsFramework iSemMobileWipsFramework) throws RemoteException;

    boolean unregisterPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException;

    void updateWifiChipInfo(String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemMobileWipsService {
        static final int TRANSACTION_broadcastBcnEventAbort = 4;
        static final int TRANSACTION_broadcastBcnIntervalEvent = 3;
        static final int TRANSACTION_checkMWIPS = 5;
        static final int TRANSACTION_onDnsResponses = 10;
        static final int TRANSACTION_onScanResults = 9;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_registerPacketSender = 11;
        static final int TRANSACTION_sendMessage = 6;
        static final int TRANSACTION_setCurrentBss = 8;
        static final int TRANSACTION_unregisterCallback = 2;
        static final int TRANSACTION_unregisterPacketSender = 12;
        static final int TRANSACTION_updateWifiChipInfo = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, ISemMobileWipsService.DESCRIPTOR);
        }

        public static ISemMobileWipsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemMobileWipsService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemMobileWipsService)) {
                return (ISemMobileWipsService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "broadcastBcnIntervalEvent";
                case 4:
                    return "broadcastBcnEventAbort";
                case 5:
                    return "checkMWIPS";
                case 6:
                    return "sendMessage";
                case 7:
                    return "updateWifiChipInfo";
                case 8:
                    return "setCurrentBss";
                case 9:
                    return "onScanResults";
                case 10:
                    return "onDnsResponses";
                case 11:
                    return "registerPacketSender";
                case 12:
                    return "unregisterPacketSender";
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
                parcel.enforceInterface(ISemMobileWipsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemMobileWipsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ISemMobileWipsFramework iSemMobileWipsFrameworkAsInterface = ISemMobileWipsFramework.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterCallback = registerCallback(iSemMobileWipsFrameworkAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterCallback);
                    return true;
                case 2:
                    ISemMobileWipsFramework iSemMobileWipsFrameworkAsInterface2 = ISemMobileWipsFramework.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterCallback = unregisterCallback(iSemMobileWipsFrameworkAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterCallback);
                    return true;
                case 3:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    broadcastBcnIntervalEvent(string, string2, string3, i3, i4, j, j2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    broadcastBcnEventAbort(string4, i5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckMWIPS = checkMWIPS(string5, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckMWIPS);
                    return true;
                case 6:
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessage(message);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateWifiChipInfo(string6, string7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    int i7 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean currentBss = setCurrentBss(string8, string9, string10, i7, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(currentBss);
                    return true;
                case 9:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemMobileWipsScanResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onScanResults(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDnsResponses(arrayListCreateStringArrayList, string11);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ISemMobileWipsPacketSender iSemMobileWipsPacketSenderAsInterface = ISemMobileWipsPacketSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterPacketSender = registerPacketSender(iSemMobileWipsPacketSenderAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterPacketSender);
                    return true;
                case 12:
                    ISemMobileWipsPacketSender iSemMobileWipsPacketSenderAsInterface2 = ISemMobileWipsPacketSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterPacketSender = unregisterPacketSender(iSemMobileWipsPacketSenderAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterPacketSender);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemMobileWipsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMobileWipsService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean registerCallback(ISemMobileWipsFramework iSemMobileWipsFramework) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemMobileWipsFramework);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean unregisterCallback(ISemMobileWipsFramework iSemMobileWipsFramework) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemMobileWipsFramework);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void broadcastBcnIntervalEvent(String str, String str2, String str3, int i, int i2, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void broadcastBcnEventAbort(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean checkMWIPS(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void sendMessage(Message message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(message, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void updateWifiChipInfo(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean setCurrentBss(String str, String str2, String str3, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void onScanResults(List<SemMobileWipsScanResult> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void onDnsResponses(List<String> list, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean registerPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemMobileWipsPacketSender);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean unregisterPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSemMobileWipsPacketSender);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
