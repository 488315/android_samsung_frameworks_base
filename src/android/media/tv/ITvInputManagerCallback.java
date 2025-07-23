package android.media.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvInputManagerCallback extends IInterface {

    public static class Default implements ITvInputManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onCurrentTunedInfosUpdated(List<TunedInfo> list) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputAdded(String str) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputRemoved(String str) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputStateChanged(String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onInputUpdated(String str) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputManagerCallback
        public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) throws RemoteException {
        }
    }

    void onCurrentTunedInfosUpdated(List<TunedInfo> list) throws RemoteException;

    void onInputAdded(String str) throws RemoteException;

    void onInputRemoved(String str) throws RemoteException;

    void onInputStateChanged(String str, int i) throws RemoteException;

    void onInputUpdated(String str) throws RemoteException;

    void onTvInputInfoUpdated(TvInputInfo tvInputInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputManagerCallback {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputManagerCallback";
        static final int TRANSACTION_onCurrentTunedInfosUpdated = 6;
        static final int TRANSACTION_onInputAdded = 1;
        static final int TRANSACTION_onInputRemoved = 2;
        static final int TRANSACTION_onInputStateChanged = 4;
        static final int TRANSACTION_onInputUpdated = 3;
        static final int TRANSACTION_onTvInputInfoUpdated = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInputManagerCallback)) {
                return (ITvInputManagerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onInputAdded";
                case 2:
                    return "onInputRemoved";
                case 3:
                    return "onInputUpdated";
                case 4:
                    return "onInputStateChanged";
                case 5:
                    return "onTvInputInfoUpdated";
                case 6:
                    return "onCurrentTunedInfosUpdated";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInputAdded(readString);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInputRemoved(readString2);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onInputUpdated(readString3);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onInputStateChanged(readString4, readInt);
                    return true;
                case 5:
                    TvInputInfo tvInputInfo = (TvInputInfo) parcel.readTypedObject(TvInputInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvInputInfoUpdated(tvInputInfo);
                    return true;
                case 6:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(TunedInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCurrentTunedInfosUpdated(createTypedArrayList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvInputManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputAdded(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputRemoved(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputUpdated(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onInputStateChanged(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onTvInputInfoUpdated(TvInputInfo tvInputInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManagerCallback
            public void onCurrentTunedInfosUpdated(List<TunedInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
