package android.media.tv.extension.time;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IBroadcastTime extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.extension.time.IBroadcastTime";

    public static class Default implements IBroadcastTime {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.extension.time.IBroadcastTime
        public long getLocalTime() throws RemoteException {
            return 0L;
        }

        @Override // android.media.tv.extension.time.IBroadcastTime
        public long getLocalTimePerStream(String str) throws RemoteException {
            return 0L;
        }

        @Override // android.media.tv.extension.time.IBroadcastTime
        public Bundle getTimeZoneInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.tv.extension.time.IBroadcastTime
        public long getUtcTime() throws RemoteException {
            return 0L;
        }

        @Override // android.media.tv.extension.time.IBroadcastTime
        public long getUtcTimePerStream(String str) throws RemoteException {
            return 0L;
        }
    }

    long getLocalTime() throws RemoteException;

    long getLocalTimePerStream(String str) throws RemoteException;

    Bundle getTimeZoneInfo() throws RemoteException;

    long getUtcTime() throws RemoteException;

    long getUtcTimePerStream(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IBroadcastTime {
        static final int TRANSACTION_getLocalTime = 2;
        static final int TRANSACTION_getLocalTimePerStream = 5;
        static final int TRANSACTION_getTimeZoneInfo = 3;
        static final int TRANSACTION_getUtcTime = 1;
        static final int TRANSACTION_getUtcTimePerStream = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, "android.media.tv.extension.time.IBroadcastTime");
        }

        public static IBroadcastTime asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("android.media.tv.extension.time.IBroadcastTime");
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBroadcastTime)) {
                return (IBroadcastTime) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getUtcTime";
            }
            if (i == 2) {
                return "getLocalTime";
            }
            if (i == 3) {
                return "getTimeZoneInfo";
            }
            if (i == 4) {
                return "getUtcTimePerStream";
            }
            if (i != 5) {
                return null;
            }
            return "getLocalTimePerStream";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.media.tv.extension.time.IBroadcastTime");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.media.tv.extension.time.IBroadcastTime");
                return true;
            }
            if (i == 1) {
                long utcTime = getUtcTime();
                parcel2.writeNoException();
                parcel2.writeLong(utcTime);
            } else if (i == 2) {
                long localTime = getLocalTime();
                parcel2.writeNoException();
                parcel2.writeLong(localTime);
            } else if (i == 3) {
                Bundle timeZoneInfo = getTimeZoneInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(timeZoneInfo, 1);
            } else if (i == 4) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                long utcTimePerStream = getUtcTimePerStream(readString);
                parcel2.writeNoException();
                parcel2.writeLong(utcTimePerStream);
            } else if (i == 5) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                long localTimePerStream = getLocalTimePerStream(readString2);
                parcel2.writeNoException();
                parcel2.writeLong(localTimePerStream);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBroadcastTime {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.media.tv.extension.time.IBroadcastTime";
            }

            @Override // android.media.tv.extension.time.IBroadcastTime
            public long getUtcTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.time.IBroadcastTime");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.time.IBroadcastTime
            public long getLocalTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.time.IBroadcastTime");
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.time.IBroadcastTime
            public Bundle getTimeZoneInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.time.IBroadcastTime");
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.time.IBroadcastTime
            public long getUtcTimePerStream(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.time.IBroadcastTime");
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.extension.time.IBroadcastTime
            public long getLocalTimePerStream(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("android.media.tv.extension.time.IBroadcastTime");
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
