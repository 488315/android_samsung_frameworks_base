package android.media.tv.ad;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.InputChannel;

/* loaded from: classes3.dex */
public interface ITvAdClient extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdClient";

    public static class Default implements ITvAdClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentChannelUri(int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentTvInputId(int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestCurrentVideoBounds(int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onRequestTrackInfoList(int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onSessionReleased(int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdClient
        public void onTvAdSessionData(String str, Bundle bundle, int i) throws RemoteException {
        }
    }

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void onRequestCurrentChannelUri(int i) throws RemoteException;

    void onRequestCurrentTvInputId(int i) throws RemoteException;

    void onRequestCurrentVideoBounds(int i) throws RemoteException;

    void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException;

    void onRequestTrackInfoList(int i) throws RemoteException;

    void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException;

    void onSessionReleased(int i) throws RemoteException;

    void onTvAdSessionData(String str, Bundle bundle, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvAdClient {
        static final int TRANSACTION_onLayoutSurface = 3;
        static final int TRANSACTION_onRequestCurrentChannelUri = 5;
        static final int TRANSACTION_onRequestCurrentTvInputId = 7;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 4;
        static final int TRANSACTION_onRequestSigning = 8;
        static final int TRANSACTION_onRequestTrackInfoList = 6;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onTvAdSessionData = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ITvAdClient.DESCRIPTOR);
        }

        public static ITvAdClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvAdClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvAdClient)) {
                return (ITvAdClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onSessionReleased";
                case 3:
                    return "onLayoutSurface";
                case 4:
                    return "onRequestCurrentVideoBounds";
                case 5:
                    return "onRequestCurrentChannelUri";
                case 6:
                    return "onRequestTrackInfoList";
                case 7:
                    return "onRequestCurrentTvInputId";
                case 8:
                    return "onRequestSigning";
                case 9:
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
                parcel.enforceInterface(ITvAdClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvAdClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    IBinder strongBinder = parcel.readStrongBinder();
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(string, strongBinder, inputChannel, i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionReleased(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(i5, i6, i7, i8, i9);
                    return true;
                case 4:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(i10);
                    return true;
                case 5:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelUri(i11);
                    return true;
                case 6:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTrackInfoList(i12);
                    return true;
                case 7:
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentTvInputId(i13);
                    return true;
                case 8:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(string2, string3, string4, bArrCreateByteArray, i14);
                    return true;
                case 9:
                    String string5 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTvAdSessionData(string5, bundle, i15);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvAdClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdClient.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onSessionCreated(String str, IBinder iBinder, InputChannel inputChannel, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(inputChannel, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onSessionReleased(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentVideoBounds(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentChannelUri(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestTrackInfoList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentTvInputId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onTvAdSessionData(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
