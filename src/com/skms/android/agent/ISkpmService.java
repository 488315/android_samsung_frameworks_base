package com.skms.android.agent;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISkpmService extends IInterface {
    public static final String DESCRIPTOR = "com.skms.android.agent.ISkpmService";

    public static class Default implements ISkpmService {
        @Override // com.skms.android.agent.ISkpmService
        public int SkpmChangeOtaServer(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.skms.android.agent.ISkpmService
        public int SkpmReadInjectedKeyUID(byte b, byte b2, String str, byte[] bArr) throws RemoteException {
            return 0;
        }

        @Override // com.skms.android.agent.ISkpmService
        public int SkpmServiceCreateGetKeySession(byte b, String str, byte[] bArr, byte b2) throws RemoteException {
            return 0;
        }

        @Override // com.skms.android.agent.ISkpmService
        public int SkpmServiceInjectedKeyVerification(byte b, byte b2, String str) throws RemoteException {
            return 0;
        }

        @Override // com.skms.android.agent.ISkpmService
        public int SkpmServiceKeyInjection(byte b, byte b2, String str, byte[] bArr, byte b3) throws RemoteException {
            return 0;
        }

        @Override // com.skms.android.agent.ISkpmService
        public int SkpmServiceReleaseGetKeySession() throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    int SkpmChangeOtaServer(String str, String str2) throws RemoteException;

    int SkpmReadInjectedKeyUID(byte b, byte b2, String str, byte[] bArr) throws RemoteException;

    int SkpmServiceCreateGetKeySession(byte b, String str, byte[] bArr, byte b2) throws RemoteException;

    int SkpmServiceInjectedKeyVerification(byte b, byte b2, String str) throws RemoteException;

    int SkpmServiceKeyInjection(byte b, byte b2, String str, byte[] bArr, byte b3) throws RemoteException;

    int SkpmServiceReleaseGetKeySession() throws RemoteException;

    public static abstract class Stub extends Binder implements ISkpmService {
        static final int TRANSACTION_SkpmChangeOtaServer = 6;
        static final int TRANSACTION_SkpmReadInjectedKeyUID = 3;
        static final int TRANSACTION_SkpmServiceCreateGetKeySession = 4;
        static final int TRANSACTION_SkpmServiceInjectedKeyVerification = 2;
        static final int TRANSACTION_SkpmServiceKeyInjection = 1;
        static final int TRANSACTION_SkpmServiceReleaseGetKeySession = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, ISkpmService.DESCRIPTOR);
        }

        public static ISkpmService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISkpmService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISkpmService)) {
                return (ISkpmService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "SkpmServiceKeyInjection";
                case 2:
                    return "SkpmServiceInjectedKeyVerification";
                case 3:
                    return "SkpmReadInjectedKeyUID";
                case 4:
                    return "SkpmServiceCreateGetKeySession";
                case 5:
                    return "SkpmServiceReleaseGetKeySession";
                case 6:
                    return "SkpmChangeOtaServer";
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
                parcel.enforceInterface(ISkpmService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISkpmService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte b = parcel.readByte();
                    byte b2 = parcel.readByte();
                    String string = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte b3 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    int iSkpmServiceKeyInjection = SkpmServiceKeyInjection(b, b2, string, bArrCreateByteArray, b3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSkpmServiceKeyInjection);
                    return true;
                case 2:
                    byte b4 = parcel.readByte();
                    byte b5 = parcel.readByte();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSkpmServiceInjectedKeyVerification = SkpmServiceInjectedKeyVerification(b4, b5, string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSkpmServiceInjectedKeyVerification);
                    return true;
                case 3:
                    byte b6 = parcel.readByte();
                    byte b7 = parcel.readByte();
                    String string3 = parcel.readString();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iSkpmReadInjectedKeyUID = SkpmReadInjectedKeyUID(b6, b7, string3, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSkpmReadInjectedKeyUID);
                    parcel2.writeByteArray(bArrCreateByteArray2);
                    return true;
                case 4:
                    byte b8 = parcel.readByte();
                    String string4 = parcel.readString();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    byte b9 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    int iSkpmServiceCreateGetKeySession = SkpmServiceCreateGetKeySession(b8, string4, bArrCreateByteArray3, b9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSkpmServiceCreateGetKeySession);
                    parcel2.writeByteArray(bArrCreateByteArray3);
                    return true;
                case 5:
                    int iSkpmServiceReleaseGetKeySession = SkpmServiceReleaseGetKeySession();
                    parcel2.writeNoException();
                    parcel2.writeInt(iSkpmServiceReleaseGetKeySession);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iSkpmChangeOtaServer = SkpmChangeOtaServer(string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iSkpmChangeOtaServer);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISkpmService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISkpmService.DESCRIPTOR;
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmServiceKeyInjection(byte b, byte b2, String str, byte[] bArr, byte b3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeByte(b2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByte(b3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmServiceInjectedKeyVerification(byte b, byte b2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeByte(b2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmReadInjectedKeyUID(byte b, byte b2, String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeByte(b2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmServiceCreateGetKeySession(byte b, String str, byte[] bArr, byte b2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByte(b2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    parcelObtain2.readByteArray(bArr);
                    return i;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmServiceReleaseGetKeySession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmChangeOtaServer(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
