package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemMobileWipsPacketSender extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemMobileWipsPacketSender";

    public static class Default implements ISemMobileWipsPacketSender {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public boolean pingTcp(byte[] bArr, byte[] bArr2, int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public List<String> sendArp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public List<String> sendArpToSniffing(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public int sendDhcp(int i, byte[] bArr, int i2, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public byte[] sendDns(long[] jArr, byte[] bArr, byte[] bArr2, byte[] bArr3, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public boolean sendDnsQueries(long[] jArr, byte[] bArr, byte[] bArr2, String str, List<String> list, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public List<String> sendIcmp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
        public boolean sendTcp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
            return false;
        }
    }

    boolean pingTcp(byte[] bArr, byte[] bArr2, int i, int i2, int i3) throws RemoteException;

    List<String> sendArp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    List<String> sendArpToSniffing(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    int sendDhcp(int i, byte[] bArr, int i2, String str) throws RemoteException;

    byte[] sendDns(long[] jArr, byte[] bArr, byte[] bArr2, byte[] bArr3, String str, boolean z) throws RemoteException;

    boolean sendDnsQueries(long[] jArr, byte[] bArr, byte[] bArr2, String str, List<String> list, int i) throws RemoteException;

    List<String> sendIcmp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    boolean sendTcp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemMobileWipsPacketSender {
        static final int TRANSACTION_pingTcp = 8;
        static final int TRANSACTION_sendArp = 1;
        static final int TRANSACTION_sendArpToSniffing = 2;
        static final int TRANSACTION_sendDhcp = 4;
        static final int TRANSACTION_sendDns = 5;
        static final int TRANSACTION_sendDnsQueries = 6;
        static final int TRANSACTION_sendIcmp = 3;
        static final int TRANSACTION_sendTcp = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, ISemMobileWipsPacketSender.DESCRIPTOR);
        }

        public static ISemMobileWipsPacketSender asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemMobileWipsPacketSender.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemMobileWipsPacketSender)) {
                return (ISemMobileWipsPacketSender) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendArp";
                case 2:
                    return "sendArpToSniffing";
                case 3:
                    return "sendIcmp";
                case 4:
                    return "sendDhcp";
                case 5:
                    return "sendDns";
                case 6:
                    return "sendDnsQueries";
                case 7:
                    return "sendTcp";
                case 8:
                    return "pingTcp";
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
                parcel.enforceInterface(ISemMobileWipsPacketSender.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemMobileWipsPacketSender.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> sendArp = sendArp(readInt, createByteArray, createByteArray2, readString);
                    parcel2.writeNoException();
                    parcel2.writeStringList(sendArp);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte[] createByteArray4 = parcel.createByteArray();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> sendArpToSniffing = sendArpToSniffing(readInt2, createByteArray3, createByteArray4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStringList(sendArpToSniffing);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> sendIcmp = sendIcmp(readInt3, createByteArray5, createByteArray6, readString3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(sendIcmp);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    byte[] createByteArray7 = parcel.createByteArray();
                    int readInt5 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int sendDhcp = sendDhcp(readInt4, createByteArray7, readInt5, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendDhcp);
                    return true;
                case 5:
                    long[] createLongArray = parcel.createLongArray();
                    byte[] createByteArray8 = parcel.createByteArray();
                    byte[] createByteArray9 = parcel.createByteArray();
                    byte[] createByteArray10 = parcel.createByteArray();
                    String readString5 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] sendDns = sendDns(createLongArray, createByteArray8, createByteArray9, createByteArray10, readString5, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sendDns);
                    return true;
                case 6:
                    long[] createLongArray2 = parcel.createLongArray();
                    byte[] createByteArray11 = parcel.createByteArray();
                    byte[] createByteArray12 = parcel.createByteArray();
                    String readString6 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean sendDnsQueries = sendDnsQueries(createLongArray2, createByteArray11, createByteArray12, readString6, createStringArrayList, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendDnsQueries);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray13 = parcel.createByteArray();
                    byte[] createByteArray14 = parcel.createByteArray();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean sendTcp = sendTcp(readInt7, createByteArray13, createByteArray14, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendTcp);
                    return true;
                case 8:
                    byte[] createByteArray15 = parcel.createByteArray();
                    byte[] createByteArray16 = parcel.createByteArray();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean pingTcp = pingTcp(createByteArray15, createByteArray16, readInt8, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(pingTcp);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemMobileWipsPacketSender {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMobileWipsPacketSender.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendArp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendArpToSniffing(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendIcmp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public int sendDhcp(int i, byte[] bArr, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public byte[] sendDns(long[] jArr, byte[] bArr, byte[] bArr2, byte[] bArr3, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeLongArray(jArr);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean sendDnsQueries(long[] jArr, byte[] bArr, byte[] bArr2, String str, List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeLongArray(jArr);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean sendTcp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean pingTcp(byte[] bArr, byte[] bArr2, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
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
