package android.os.logcat;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ILogcatManagerService extends IInterface {
    public static final String DESCRIPTOR = "android.os.logcat.ILogcatManagerService";

    public static class Default implements ILogcatManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.logcat.ILogcatManagerService
        public void finishThread(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.os.logcat.ILogcatManagerService
        public void onKnoxSecurityLogEvent(SecurityLogEvent securityLogEvent) throws RemoteException {
        }

        @Override // android.os.logcat.ILogcatManagerService
        public void startThread(int i, int i2, int i3, int i4) throws RemoteException {
        }
    }

    void finishThread(int i, int i2, int i3, int i4) throws RemoteException;

    void onKnoxSecurityLogEvent(SecurityLogEvent securityLogEvent) throws RemoteException;

    void startThread(int i, int i2, int i3, int i4) throws RemoteException;

    public static abstract class Stub extends Binder implements ILogcatManagerService {
        static final int TRANSACTION_finishThread = 2;
        static final int TRANSACTION_onKnoxSecurityLogEvent = 3;
        static final int TRANSACTION_startThread = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ILogcatManagerService.DESCRIPTOR);
        }

        public static ILogcatManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ILogcatManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILogcatManagerService)) {
                return (ILogcatManagerService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startThread";
            }
            if (i == 2) {
                return "finishThread";
            }
            if (i != 3) {
                return null;
            }
            return "onKnoxSecurityLogEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ILogcatManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ILogcatManagerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                startThread(i3, i4, i5, i6);
            } else if (i == 2) {
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                int i10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                finishThread(i7, i8, i9, i10);
            } else if (i == 3) {
                SecurityLogEvent securityLogEvent = (SecurityLogEvent) parcel.readTypedObject(SecurityLogEvent.CREATOR);
                parcel.enforceNoDataAvail();
                onKnoxSecurityLogEvent(securityLogEvent);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ILogcatManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILogcatManagerService.DESCRIPTOR;
            }

            @Override // android.os.logcat.ILogcatManagerService
            public void startThread(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ILogcatManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.logcat.ILogcatManagerService
            public void finishThread(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ILogcatManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.logcat.ILogcatManagerService
            public void onKnoxSecurityLogEvent(SecurityLogEvent securityLogEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ILogcatManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(securityLogEvent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
