package android.blockchain;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBlockchainManager extends IInterface {
    public static final String DESCRIPTOR = "android.blockchain.IBlockchainManager";

    public static class Default implements IBlockchainManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.blockchain.IBlockchainManager
        public byte[] getCredential(int i) throws RemoteException {
            return null;
        }

        @Override // android.blockchain.IBlockchainManager
        public byte[] getMeasurementFile() throws RemoteException {
            return null;
        }

        @Override // android.blockchain.IBlockchainManager
        public boolean putCredential(int i, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.blockchain.IBlockchainManager
        public BlockchainTZServiceCommnInfo registerBlockchainFW(BlockchainTZServiceConfig blockchainTZServiceConfig) throws RemoteException {
            return null;
        }

        @Override // android.blockchain.IBlockchainManager
        public int sspExit() throws RemoteException {
            return 0;
        }

        @Override // android.blockchain.IBlockchainManager
        public int sspInit() throws RemoteException {
            return 0;
        }
    }

    byte[] getCredential(int i) throws RemoteException;

    byte[] getMeasurementFile() throws RemoteException;

    boolean putCredential(int i, byte[] bArr) throws RemoteException;

    BlockchainTZServiceCommnInfo registerBlockchainFW(BlockchainTZServiceConfig blockchainTZServiceConfig) throws RemoteException;

    int sspExit() throws RemoteException;

    int sspInit() throws RemoteException;

    public static abstract class Stub extends Binder implements IBlockchainManager {
        static final int TRANSACTION_getCredential = 4;
        static final int TRANSACTION_getMeasurementFile = 2;
        static final int TRANSACTION_putCredential = 3;
        static final int TRANSACTION_registerBlockchainFW = 1;
        static final int TRANSACTION_sspExit = 6;
        static final int TRANSACTION_sspInit = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IBlockchainManager.DESCRIPTOR);
        }

        public static IBlockchainManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBlockchainManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBlockchainManager)) {
                return (IBlockchainManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerBlockchainFW";
                case 2:
                    return "getMeasurementFile";
                case 3:
                    return "putCredential";
                case 4:
                    return "getCredential";
                case 5:
                    return "sspInit";
                case 6:
                    return "sspExit";
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
                parcel.enforceInterface(IBlockchainManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBlockchainManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    BlockchainTZServiceConfig blockchainTZServiceConfig = (BlockchainTZServiceConfig) parcel.readTypedObject(BlockchainTZServiceConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    BlockchainTZServiceCommnInfo registerBlockchainFW = registerBlockchainFW(blockchainTZServiceConfig);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(registerBlockchainFW, 1);
                    return true;
                case 2:
                    byte[] measurementFile = getMeasurementFile();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(measurementFile);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean putCredential = putCredential(readInt, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(putCredential);
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] credential = getCredential(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(credential);
                    return true;
                case 5:
                    int sspInit = sspInit();
                    parcel2.writeNoException();
                    parcel2.writeInt(sspInit);
                    return true;
                case 6:
                    int sspExit = sspExit();
                    parcel2.writeNoException();
                    parcel2.writeInt(sspExit);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBlockchainManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBlockchainManager.DESCRIPTOR;
            }

            @Override // android.blockchain.IBlockchainManager
            public BlockchainTZServiceCommnInfo registerBlockchainFW(BlockchainTZServiceConfig blockchainTZServiceConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
                    obtain.writeTypedObject(blockchainTZServiceConfig, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (BlockchainTZServiceCommnInfo) obtain2.readTypedObject(BlockchainTZServiceCommnInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.blockchain.IBlockchainManager
            public byte[] getMeasurementFile() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.blockchain.IBlockchainManager
            public boolean putCredential(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.blockchain.IBlockchainManager
            public byte[] getCredential(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.blockchain.IBlockchainManager
            public int sspInit() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.blockchain.IBlockchainManager
            public int sspExit() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IBlockchainManager.DESCRIPTOR);
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
