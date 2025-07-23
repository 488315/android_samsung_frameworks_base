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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemMobileWipsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemMobileWipsService)) {
                return (ISemMobileWipsService) queryLocalInterface;
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
                    ISemMobileWipsFramework asInterface = ISemMobileWipsFramework.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerCallback = registerCallback(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerCallback);
                    return true;
                case 2:
                    ISemMobileWipsFramework asInterface2 = ISemMobileWipsFramework.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterCallback = unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterCallback);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    broadcastBcnIntervalEvent(readString, readString2, readString3, readInt, readInt2, readLong, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    broadcastBcnEventAbort(readString4, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkMWIPS = checkMWIPS(readString5, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkMWIPS);
                    return true;
                case 6:
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendMessage(message);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateWifiChipInfo(readString6, readString7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean currentBss = setCurrentBss(readString8, readString9, readString10, readInt5, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(currentBss);
                    return true;
                case 9:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(SemMobileWipsScanResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    onScanResults(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDnsResponses(createStringArrayList, readString11);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ISemMobileWipsPacketSender asInterface3 = ISemMobileWipsPacketSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerPacketSender = registerPacketSender(asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerPacketSender);
                    return true;
                case 12:
                    ISemMobileWipsPacketSender asInterface4 = ISemMobileWipsPacketSender.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterPacketSender = unregisterPacketSender(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterPacketSender);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemMobileWipsFramework);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean unregisterCallback(ISemMobileWipsFramework iSemMobileWipsFramework) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemMobileWipsFramework);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void broadcastBcnIntervalEvent(String str, String str2, String str3, int i, int i2, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void broadcastBcnEventAbort(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean checkMWIPS(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void sendMessage(Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void updateWifiChipInfo(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean setCurrentBss(String str, String str2, String str3, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void onScanResults(List<SemMobileWipsScanResult> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public void onDnsResponses(List<String> list, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean registerPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemMobileWipsPacketSender);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsService
            public boolean unregisterPacketSender(ISemMobileWipsPacketSender iSemMobileWipsPacketSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSemMobileWipsPacketSender);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
