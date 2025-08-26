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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvAdSessionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvAdSessionCallback)) {
                return (ITvAdSessionCallback) iInterfaceQueryLocalInterface;
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
                    ITvAdSession iTvAdSessionAsInterface = ITvAdSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSessionCreated(iTvAdSessionAsInterface);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(i3, i4, i5, i6);
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(string, string2, string3, bArrCreateByteArray);
                    return true;
                case 8:
                    String string4 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvAdSessionData(string4, bundle);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvAdSession);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onLayoutSurface(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentVideoBounds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentChannelUri() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestTrackInfoList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestCurrentTvInputId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onRequestSigning(String str, String str2, String str3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSessionCallback
            public void onTvAdSessionData(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSessionCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
