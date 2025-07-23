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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITvAdClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvAdClient)) {
                return (ITvAdClient) queryLocalInterface;
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
                    String readString = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    InputChannel inputChannel = (InputChannel) parcel.readTypedObject(InputChannel.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(readString, readStrongBinder, inputChannel, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionReleased(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 4:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(readInt8);
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelUri(readInt9);
                    return true;
                case 6:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTrackInfoList(readInt10);
                    return true;
                case 7:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentTvInputId(readInt11);
                    return true;
                case 8:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString2, readString3, readString4, createByteArray, readInt12);
                    return true;
                case 9:
                    String readString5 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTvAdSessionData(readString5, bundle, readInt13);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onSessionReleased(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentVideoBounds(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentChannelUri(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestTrackInfoList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestCurrentTvInputId(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onRequestSigning(String str, String str2, String str3, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdClient
            public void onTvAdSessionData(String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ITvAdClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
