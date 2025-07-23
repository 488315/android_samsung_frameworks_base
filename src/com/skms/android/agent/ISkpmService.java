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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISkpmService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISkpmService)) {
                return (ISkpmService) queryLocalInterface;
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
                    byte readByte = parcel.readByte();
                    byte readByte2 = parcel.readByte();
                    String readString = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte readByte3 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    int SkpmServiceKeyInjection = SkpmServiceKeyInjection(readByte, readByte2, readString, createByteArray, readByte3);
                    parcel2.writeNoException();
                    parcel2.writeInt(SkpmServiceKeyInjection);
                    return true;
                case 2:
                    byte readByte4 = parcel.readByte();
                    byte readByte5 = parcel.readByte();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int SkpmServiceInjectedKeyVerification = SkpmServiceInjectedKeyVerification(readByte4, readByte5, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(SkpmServiceInjectedKeyVerification);
                    return true;
                case 3:
                    byte readByte6 = parcel.readByte();
                    byte readByte7 = parcel.readByte();
                    String readString3 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int SkpmReadInjectedKeyUID = SkpmReadInjectedKeyUID(readByte6, readByte7, readString3, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeInt(SkpmReadInjectedKeyUID);
                    parcel2.writeByteArray(createByteArray2);
                    return true;
                case 4:
                    byte readByte8 = parcel.readByte();
                    String readString4 = parcel.readString();
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte readByte9 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    int SkpmServiceCreateGetKeySession = SkpmServiceCreateGetKeySession(readByte8, readString4, createByteArray3, readByte9);
                    parcel2.writeNoException();
                    parcel2.writeInt(SkpmServiceCreateGetKeySession);
                    parcel2.writeByteArray(createByteArray3);
                    return true;
                case 5:
                    int SkpmServiceReleaseGetKeySession = SkpmServiceReleaseGetKeySession();
                    parcel2.writeNoException();
                    parcel2.writeInt(SkpmServiceReleaseGetKeySession);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int SkpmChangeOtaServer = SkpmChangeOtaServer(readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(SkpmChangeOtaServer);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByte(b3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmServiceInjectedKeyVerification(byte b, byte b2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmReadInjectedKeyUID(byte b, byte b2, String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmServiceCreateGetKeySession(byte b, String str, byte[] bArr, byte b2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    obtain.writeByte(b);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByte(b2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.readByteArray(bArr);
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmServiceReleaseGetKeySession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.skms.android.agent.ISkpmService
            public int SkpmChangeOtaServer(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISkpmService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
