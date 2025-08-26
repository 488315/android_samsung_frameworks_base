package android.media.tv.ad;

import android.graphics.Rect;
import android.media.tv.TvTrackInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ITvAdSession extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdSession";

    public static class Default implements ITvAdSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void createMediaView(IBinder iBinder, Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyError(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyTvInputSessionData(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void notifyTvMessage(int i, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void relayoutMediaView(Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void release() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void removeMediaView() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void resetAdService() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentChannelUri(Uri uri) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentTvInputId(String str) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendCurrentVideoBounds(Rect rect) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendSigningResult(String str, byte[] bArr) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void sendTrackInfoList(List<TvTrackInfo> list) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void setSurface(Surface surface) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void startAdService() throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdSession
        public void stopAdService() throws RemoteException {
        }
    }

    void createMediaView(IBinder iBinder, Rect rect) throws RemoteException;

    void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException;

    void notifyError(String str, Bundle bundle) throws RemoteException;

    void notifyTvInputSessionData(String str, Bundle bundle) throws RemoteException;

    void notifyTvMessage(int i, Bundle bundle) throws RemoteException;

    void relayoutMediaView(Rect rect) throws RemoteException;

    void release() throws RemoteException;

    void removeMediaView() throws RemoteException;

    void resetAdService() throws RemoteException;

    void sendCurrentChannelUri(Uri uri) throws RemoteException;

    void sendCurrentTvInputId(String str) throws RemoteException;

    void sendCurrentVideoBounds(Rect rect) throws RemoteException;

    void sendSigningResult(String str, byte[] bArr) throws RemoteException;

    void sendTrackInfoList(List<TvTrackInfo> list) throws RemoteException;

    void setSurface(Surface surface) throws RemoteException;

    void startAdService() throws RemoteException;

    void stopAdService() throws RemoteException;

    public static abstract class Stub extends Binder implements ITvAdSession {
        static final int TRANSACTION_createMediaView = 14;
        static final int TRANSACTION_dispatchSurfaceChanged = 6;
        static final int TRANSACTION_notifyError = 12;
        static final int TRANSACTION_notifyTvInputSessionData = 17;
        static final int TRANSACTION_notifyTvMessage = 13;
        static final int TRANSACTION_relayoutMediaView = 15;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_removeMediaView = 16;
        static final int TRANSACTION_resetAdService = 4;
        static final int TRANSACTION_sendCurrentChannelUri = 8;
        static final int TRANSACTION_sendCurrentTvInputId = 10;
        static final int TRANSACTION_sendCurrentVideoBounds = 7;
        static final int TRANSACTION_sendSigningResult = 11;
        static final int TRANSACTION_sendTrackInfoList = 9;
        static final int TRANSACTION_setSurface = 5;
        static final int TRANSACTION_startAdService = 2;
        static final int TRANSACTION_stopAdService = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16;
        }

        public Stub() {
            attachInterface(this, ITvAdSession.DESCRIPTOR);
        }

        public static ITvAdSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvAdSession.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvAdSession)) {
                return (ITvAdSession) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "release";
                case 2:
                    return "startAdService";
                case 3:
                    return "stopAdService";
                case 4:
                    return "resetAdService";
                case 5:
                    return "setSurface";
                case 6:
                    return "dispatchSurfaceChanged";
                case 7:
                    return "sendCurrentVideoBounds";
                case 8:
                    return "sendCurrentChannelUri";
                case 9:
                    return "sendTrackInfoList";
                case 10:
                    return "sendCurrentTvInputId";
                case 11:
                    return "sendSigningResult";
                case 12:
                    return "notifyError";
                case 13:
                    return "notifyTvMessage";
                case 14:
                    return "createMediaView";
                case 15:
                    return "relayoutMediaView";
                case 16:
                    return "removeMediaView";
                case 17:
                    return "notifyTvInputSessionData";
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
                parcel.enforceInterface(ITvAdSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvAdSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    release();
                    return true;
                case 2:
                    startAdService();
                    return true;
                case 3:
                    stopAdService();
                    return true;
                case 4:
                    resetAdService();
                    return true;
                case 5:
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSurface(surface);
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(i3, i4, i5);
                    return true;
                case 7:
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(rect);
                    return true;
                case 8:
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(uri);
                    return true;
                case 9:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(arrayListCreateTypedArrayList);
                    return true;
                case 10:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(string);
                    return true;
                case 11:
                    String string2 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(string2, bArrCreateByteArray);
                    return true;
                case 12:
                    String string3 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyError(string3, bundle);
                    return true;
                case 13:
                    int i6 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(i6, bundle2);
                    return true;
                case 14:
                    IBinder strongBinder = parcel.readStrongBinder();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    createMediaView(strongBinder, rect2);
                    return true;
                case 15:
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(rect3);
                    return true;
                case 16:
                    removeMediaView();
                    return true;
                case 17:
                    String string4 = parcel.readString();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvInputSessionData(string4, bundle3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvAdSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdSession.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void startAdService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void stopAdService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void resetAdService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void setSurface(Surface surface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void dispatchSurfaceChanged(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentVideoBounds(Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentChannelUri(Uri uri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendTrackInfoList(List<TvTrackInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendCurrentTvInputId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void sendSigningResult(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyError(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyTvMessage(int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void createMediaView(IBinder iBinder, Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void relayoutMediaView(Rect rect) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void removeMediaView() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdSession
            public void notifyTvInputSessionData(String str, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITvAdSession.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
