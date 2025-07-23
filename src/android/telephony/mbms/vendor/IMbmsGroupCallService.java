package android.telephony.mbms.vendor;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.IGroupCallCallback;
import android.telephony.mbms.IMbmsGroupCallSessionCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMbmsGroupCallService extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsGroupCallService";

    public static class Default implements IMbmsGroupCallService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void dispose(int i) throws RemoteException {
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int initialize(IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int i) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int startGroupCall(int i, long j, List list, List list2, IGroupCallCallback iGroupCallCallback) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void stopGroupCall(int i, long j) throws RemoteException {
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void updateGroupCall(int i, long j, List list, List list2) throws RemoteException {
        }
    }

    void dispose(int i) throws RemoteException;

    int initialize(IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int i) throws RemoteException;

    int startGroupCall(int i, long j, List list, List list2, IGroupCallCallback iGroupCallCallback) throws RemoteException;

    void stopGroupCall(int i, long j) throws RemoteException;

    void updateGroupCall(int i, long j, List list, List list2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMbmsGroupCallService {
        static final int TRANSACTION_dispose = 5;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_startGroupCall = 4;
        static final int TRANSACTION_stopGroupCall = 2;
        static final int TRANSACTION_updateGroupCall = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IMbmsGroupCallService.DESCRIPTOR);
        }

        public static IMbmsGroupCallService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMbmsGroupCallService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMbmsGroupCallService)) {
                return (IMbmsGroupCallService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "initialize";
            }
            if (i == 2) {
                return "stopGroupCall";
            }
            if (i == 3) {
                return "updateGroupCall";
            }
            if (i == 4) {
                return "startGroupCall";
            }
            if (i != 5) {
                return null;
            }
            return "dispose";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMbmsGroupCallService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMbmsGroupCallService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IMbmsGroupCallSessionCallback asInterface = IMbmsGroupCallSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int initialize = initialize(asInterface, readInt);
                parcel2.writeNoException();
                parcel2.writeInt(initialize);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                stopGroupCall(readInt2, readLong);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                long readLong2 = parcel.readLong();
                ClassLoader classLoader = getClass().getClassLoader();
                ArrayList readArrayList = parcel.readArrayList(classLoader);
                ArrayList readArrayList2 = parcel.readArrayList(classLoader);
                parcel.enforceNoDataAvail();
                updateGroupCall(readInt3, readLong2, readArrayList, readArrayList2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                long readLong3 = parcel.readLong();
                ClassLoader classLoader2 = getClass().getClassLoader();
                ArrayList readArrayList3 = parcel.readArrayList(classLoader2);
                ArrayList readArrayList4 = parcel.readArrayList(classLoader2);
                IGroupCallCallback asInterface2 = IGroupCallCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int startGroupCall = startGroupCall(readInt4, readLong3, readArrayList3, readArrayList4, asInterface2);
                parcel2.writeNoException();
                parcel2.writeInt(startGroupCall);
            } else if (i == 5) {
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                dispose(readInt5);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMbmsGroupCallService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMbmsGroupCallService.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public int initialize(IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMbmsGroupCallSessionCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void stopGroupCall(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void updateGroupCall(int i, long j, List list, List list2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeList(list);
                    obtain.writeList(list2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public int startGroupCall(int i, long j, List list, List list2, IGroupCallCallback iGroupCallCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeList(list);
                    obtain.writeList(list2);
                    obtain.writeStrongInterface(iGroupCallCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void dispose(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
