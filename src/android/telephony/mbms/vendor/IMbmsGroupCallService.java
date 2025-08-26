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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMbmsGroupCallService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMbmsGroupCallService)) {
                return (IMbmsGroupCallService) iInterfaceQueryLocalInterface;
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
                IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallbackAsInterface = IMbmsGroupCallSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int iInitialize = initialize(iMbmsGroupCallSessionCallbackAsInterface, i3);
                parcel2.writeNoException();
                parcel2.writeInt(iInitialize);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                long j = parcel.readLong();
                parcel.enforceNoDataAvail();
                stopGroupCall(i4, j);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i5 = parcel.readInt();
                long j2 = parcel.readLong();
                ClassLoader classLoader = getClass().getClassLoader();
                ArrayList arrayList = parcel.readArrayList(classLoader);
                ArrayList arrayList2 = parcel.readArrayList(classLoader);
                parcel.enforceNoDataAvail();
                updateGroupCall(i5, j2, arrayList, arrayList2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i6 = parcel.readInt();
                long j3 = parcel.readLong();
                ClassLoader classLoader2 = getClass().getClassLoader();
                ArrayList arrayList3 = parcel.readArrayList(classLoader2);
                ArrayList arrayList4 = parcel.readArrayList(classLoader2);
                IGroupCallCallback iGroupCallCallbackAsInterface = IGroupCallCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int iStartGroupCall = startGroupCall(i6, j3, arrayList3, arrayList4, iGroupCallCallbackAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(iStartGroupCall);
            } else if (i == 5) {
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                dispose(i7);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMbmsGroupCallSessionCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void stopGroupCall(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void updateGroupCall(int i, long j, List list, List list2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeList(list);
                    parcelObtain.writeList(list2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public int startGroupCall(int i, long j, List list, List list2, IGroupCallCallback iGroupCallCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeList(list);
                    parcelObtain.writeList(list2);
                    parcelObtain.writeStrongInterface(iGroupCallCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void dispose(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMbmsGroupCallService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
