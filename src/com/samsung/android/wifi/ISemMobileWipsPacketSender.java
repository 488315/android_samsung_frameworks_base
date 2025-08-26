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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemMobileWipsPacketSender.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemMobileWipsPacketSender)) {
                return (ISemMobileWipsPacketSender) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> listSendArp = sendArp(i3, bArrCreateByteArray, bArrCreateByteArray2, string);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listSendArp);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> listSendArpToSniffing = sendArpToSniffing(i4, bArrCreateByteArray3, bArrCreateByteArray4, string2);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listSendArpToSniffing);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> listSendIcmp = sendIcmp(i5, bArrCreateByteArray5, bArrCreateByteArray6, string3);
                    parcel2.writeNoException();
                    parcel2.writeStringList(listSendIcmp);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    int i7 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSendDhcp = sendDhcp(i6, bArrCreateByteArray7, i7, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSendDhcp);
                    return true;
                case 5:
                    long[] jArrCreateLongArray = parcel.createLongArray();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    String string5 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    byte[] bArrSendDns = sendDns(jArrCreateLongArray, bArrCreateByteArray8, bArrCreateByteArray9, bArrCreateByteArray10, string5, z);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrSendDns);
                    return true;
                case 6:
                    long[] jArrCreateLongArray2 = parcel.createLongArray();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    String string6 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSendDnsQueries = sendDnsQueries(jArrCreateLongArray2, bArrCreateByteArray11, bArrCreateByteArray12, string6, arrayListCreateStringArrayList, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendDnsQueries);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    byte[] bArrCreateByteArray13 = parcel.createByteArray();
                    byte[] bArrCreateByteArray14 = parcel.createByteArray();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zSendTcp = sendTcp(i9, bArrCreateByteArray13, bArrCreateByteArray14, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendTcp);
                    return true;
                case 8:
                    byte[] bArrCreateByteArray15 = parcel.createByteArray();
                    byte[] bArrCreateByteArray16 = parcel.createByteArray();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zPingTcp = pingTcp(bArrCreateByteArray15, bArrCreateByteArray16, i10, i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zPingTcp);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendArpToSniffing(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public List<String> sendIcmp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public int sendDhcp(int i, byte[] bArr, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public byte[] sendDns(long[] jArr, byte[] bArr, byte[] bArr2, byte[] bArr3, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeLongArray(jArr);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean sendDnsQueries(long[] jArr, byte[] bArr, byte[] bArr2, String str, List<String> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeLongArray(jArr);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean sendTcp(int i, byte[] bArr, byte[] bArr2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsPacketSender
            public boolean pingTcp(byte[] bArr, byte[] bArr2, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsPacketSender.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
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
