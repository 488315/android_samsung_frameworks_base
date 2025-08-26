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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IZtd.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IZtd)) {
                return (IZtd) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    long j = parcel.readLong();
                    IZtdListener iZtdListenerAsInterface = IZtdListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startTracing(i3, i4, j, iZtdListenerAsInterface);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopTracing(i5, i6);
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    IZtdListener iZtdListenerAsInterface2 = IZtdListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartMonitoringFiles = startMonitoringFiles(i7, iArrCreateIntArray, arrayListCreateStringArrayList, arrayListCreateStringArrayList2, iZtdListenerAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartMonitoringFiles);
                    return true;
                case 4:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStopMonitoringFiles = stopMonitoringFiles(i8);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopMonitoringFiles);
                    return true;
                case 5:
                    int i9 = parcel.readInt();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    IZtdListener iZtdListenerAsInterface3 = IZtdListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartMonitoringDomains = startMonitoringDomains(i9, iArrCreateIntArray2, arrayListCreateStringArrayList3, iZtdListenerAsInterface3);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartMonitoringDomains);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStopMonitoringDomains = stopMonitoringDomains(i10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopMonitoringDomains);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iZtdListener);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public void stopTracing(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int startMonitoringFiles(int i, int[] iArr, List<String> list, List<String> list2, IZtdListener iZtdListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStrongInterface(iZtdListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int stopMonitoringFiles(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int startMonitoringDomains(int i, int[] iArr, List<String> list, IZtdListener iZtdListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStrongInterface(iZtdListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IZtd
            public int stopMonitoringDomains(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IZtd.DESCRIPTOR);
                    parcelObtain.writeInt(i);
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
