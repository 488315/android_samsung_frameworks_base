package android.os;

import android.os.IZtdListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IZtd extends IInterface {
    public static final String DESCRIPTOR = "android.os.IZtd";

    public static class Default implements IZtd {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IZtd
        public int startMonitoringDomains(int i, int[] iArr, List<String> list, IZtdListener iZtdListener) throws RemoteException {
            return 0;
        }

        @Override // android.os.IZtd
        public int startMonitoringFiles(int i, int[] iArr, List<String> list, List<String> list2, IZtdListener iZtdListener) throws RemoteException {
            return 0;
        }

        @Override // android.os.IZtd
        public void startTracing(int i, int i2, long j, IZtdListener iZtdListener) throws RemoteException {
        }

        @Override // android.os.IZtd
        public int stopMonitoringDomains(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IZtd
        public int stopMonitoringFiles(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IZtd
        public void stopTracing(int i, int i2) throws RemoteException {
        }
    }

    int startMonitoringDomains(int i, int[] iArr, List<String> list, IZtdListener iZtdListener) throws RemoteException;

    int startMonitoringFiles(int i, int[] iArr, List<String> list, List<String> list2, IZtdListener iZtdListener) throws RemoteException;

    void startTracing(int i, int i2, long j, IZtdListener iZtdListener) throws RemoteException;

    int stopMonitoringDomains(int i) throws RemoteException;

    int stopMonitoringFiles(int i) throws RemoteException;

    void stopTracing(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IZtd {
        static final int TRANSACTION_startMonitoringDomains = 5;
        static final int TRANSACTION_startMonitoringFiles = 3;
        static final int TRANSACTION_startTracing = 1;
        static final int TRANSACTION_stopMonitoringDomains = 6;
        static final int TRANSACTION_stopMonitoringFiles = 4;
        static final int TRANSACTION_stopTracing = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, IZtd.DESCRIPTOR);
        }

        public static IZtd asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IZtd.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IZtd)) {
                return (IZtd) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startTracing";
                case 2:
                    return "stopTracing";
                case 3:
                    return "startMonitoringFiles";
                case 4:
                    return "stopMonitoringFiles";
                case 5:
                    return "startMonitoringDomains";
                case 6:
                    return "stopMonitoringDomains";
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
                parcel.enforceInterface(IZtd.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IZtd.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    IZtdListener asInterface = IZtdListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startTracing(readInt, readInt2, readLong, asInterface);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopTracing(readInt3, readInt4);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    IZtdListener asInterface2 = IZtdListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startMonitoringFiles = startMonitoringFiles(readInt5, createIntArray, createStringArrayList, createStringArrayList2, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startMonitoringFiles);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int stopMonitoringFiles = stopMonitoringFiles(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopMonitoringFiles);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    IZtdListener asInterface3 = IZtdListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startMonitoringDomains = startMonitoringDomains(readInt7, createIntArray2, createStringArrayList3, asInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(startMonitoringDomains);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int stopMonitoringDomains = stopMonitoringDomains(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopMonitoringDomains);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IZtd {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IZtd.DESCRIPTOR;
            }

            @Override // android.os.IZtd
            public void startTracing(int i, int i2, long j, IZtdListener iZtdListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iZtdListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public void stopTracing(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int startMonitoringFiles(int i, int[] iArr, List<String> list, List<String> list2, IZtdListener iZtdListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(iZtdListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int stopMonitoringFiles(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int startMonitoringDomains(int i, int[] iArr, List<String> list, IZtdListener iZtdListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringList(list);
                    obtain.writeStrongInterface(iZtdListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int stopMonitoringDomains(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    obtain.writeInt(i);
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
