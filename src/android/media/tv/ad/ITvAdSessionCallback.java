package android.media.tv.ad;

import android.media.tv.ad.ITvAdSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvAdSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdSessionCallback";

    public static class Default implements ITvAdSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentChannelUri() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentTvInputId() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestCurrentVideoBounds() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onRequestTrackInfoList() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onSessionCreated(ITvAdSession iTvAdSession) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSessionCallback
        public void onTvAdSessionData(String str, Bundle bundle) throws RemoteException {
        }
    }

    void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException;

    void onRequestCurrentChannelUri() throws RemoteException;

    void onRequestCurrentTvInputId() throws RemoteException;

    void onRequestCurrentVideoBounds() throws RemoteException;

    void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException;

    void onRequestTrackInfoList() throws RemoteException;

    void onSessionCreated(ITvAdSession iTvAdSession) throws RemoteException;

    void onTvAdSessionData(String str, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvAdSessionCallback {
        static final int TRANSACTION_onLayoutSurface = 2;
        static final int TRANSACTION_onRequestCurrentChannelUri = 4;
        static final int TRANSACTION_onRequestCurrentTvInputId = 6;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 3;
        static final int TRANSACTION_onRequestSigning = 7;
        static final int TRANSACTION_onRequestTrackInfoList = 5;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onTvAdSessionData = 8;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, ITvAdSessionCallback.DESCRIPTOR);
        }

        public static ITvAdSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITvAdSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvAdSessionCallback)) {
                return (ITvAdSessionCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onLayoutSurface";
                case 3:
                    return "onRequestCurrentVideoBounds";
                case 4:
                    return "onRequestCurrentChannelUri";
                case 5:
                    return "onRequestTrackInfoList";
                case 6:
                    return "onRequestCurrentTvInputId";
                case 7:
                    return "onRequestSigning";
                case 8:
                    return "onTvAdSessionData";
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
                parcel.enforceInterface(ITvAdSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvAdSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ITvAdSession asInterface = ITvAdSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSessionCreated(asInterface);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt, readInt2, readInt3, readInt4);
                    return true;
                case 3:
                    onRequestCurrentVideoBounds();
                    return true;
                case 4:
                    onRequestCurrentChannelUri();
                    return true;
                case 5:
                    onRequestTrackInfoList();
                    return true;
                case 6:
                    onRequestCurrentTvInputId();
                    return true;
                case 7:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString, readString2, readString3, createByteArray);
                    return true;
                case 8:
                    String readString4 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvAdSessionData(readString4, bundle);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvAdSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onSessionCreated(ITvAdSession iTvAdSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdSession);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentVideoBounds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentChannelUri() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestTrackInfoList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentTvInputId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onTvAdSessionData(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
