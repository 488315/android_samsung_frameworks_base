package android.media.tv.ad;

import android.graphics.Rect;
import android.media.tv.TvTrackInfo;
import android.media.tv.ad.ITvAdClient;
import android.media.tv.ad.ITvAdManagerCallback;
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
public interface ITvAdManager extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdManager";

    public static class Default implements ITvAdManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void createSession(ITvAdClient iTvAdClient, String str, String str2, int i, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public List<TvAdServiceInfo> getTvAdServiceList(int i) throws RemoteException {
            return null;
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyTvInputSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void registerCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void releaseSession(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void removeMediaView(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void resetAdService(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void startAdService(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void stopAdService(IBinder iBinder, int i) throws RemoteException {
        }

        @Override // android.media.tv.ad.ITvAdManager
        public void unregisterCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException {
        }
    }

    void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException;

    void createSession(ITvAdClient iTvAdClient, String str, String str2, int i, int i2) throws RemoteException;

    void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException;

    List<TvAdServiceInfo> getTvAdServiceList(int i) throws RemoteException;

    void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException;

    void notifyTvInputSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException;

    void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException;

    void registerCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException;

    void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void releaseSession(IBinder iBinder, int i) throws RemoteException;

    void removeMediaView(IBinder iBinder, int i) throws RemoteException;

    void resetAdService(IBinder iBinder, int i) throws RemoteException;

    void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException;

    void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException;

    void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException;

    void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException;

    void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException;

    void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException;

    void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException;

    void startAdService(IBinder iBinder, int i) throws RemoteException;

    void stopAdService(IBinder iBinder, int i) throws RemoteException;

    void unregisterCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvAdManager {
        static final int TRANSACTION_createMediaView = 19;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_dispatchSurfaceChanged = 9;
        static final int TRANSACTION_getTvAdServiceList = 1;
        static final int TRANSACTION_notifyError = 15;
        static final int TRANSACTION_notifyTvInputSessionData = 22;
        static final int TRANSACTION_notifyTvMessage = 16;
        static final int TRANSACTION_registerCallback = 17;
        static final int TRANSACTION_relayoutMediaView = 20;
        static final int TRANSACTION_releaseSession = 4;
        static final int TRANSACTION_removeMediaView = 21;
        static final int TRANSACTION_resetAdService = 7;
        static final int TRANSACTION_sendAppLinkCommand = 2;
        static final int TRANSACTION_sendCurrentChannelUri = 11;
        static final int TRANSACTION_sendCurrentTvInputId = 13;
        static final int TRANSACTION_sendCurrentVideoBounds = 10;
        static final int TRANSACTION_sendSigningResult = 14;
        static final int TRANSACTION_sendTrackInfoList = 12;
        static final int TRANSACTION_setSurface = 8;
        static final int TRANSACTION_startAdService = 5;
        static final int TRANSACTION_stopAdService = 6;
        static final int TRANSACTION_unregisterCallback = 18;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub() {
            attachInterface(this, ITvAdManager.DESCRIPTOR);
        }

        public static ITvAdManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITvAdManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvAdManager)) {
                return (ITvAdManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTvAdServiceList";
                case 2:
                    return "sendAppLinkCommand";
                case 3:
                    return "createSession";
                case 4:
                    return "releaseSession";
                case 5:
                    return "startAdService";
                case 6:
                    return "stopAdService";
                case 7:
                    return "resetAdService";
                case 8:
                    return "setSurface";
                case 9:
                    return "dispatchSurfaceChanged";
                case 10:
                    return "sendCurrentVideoBounds";
                case 11:
                    return "sendCurrentChannelUri";
                case 12:
                    return "sendTrackInfoList";
                case 13:
                    return "sendCurrentTvInputId";
                case 14:
                    return "sendSigningResult";
                case 15:
                    return "notifyError";
                case 16:
                    return "notifyTvMessage";
                case 17:
                    return "registerCallback";
                case 18:
                    return "unregisterCallback";
                case 19:
                    return "createMediaView";
                case 20:
                    return "relayoutMediaView";
                case 21:
                    return "removeMediaView";
                case 22:
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
                parcel.enforceInterface(ITvAdManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITvAdManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvAdServiceInfo> tvAdServiceList = getTvAdServiceList(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvAdServiceList, 1);
                    return true;
                case 2:
                    String readString = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(readString, bundle, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ITvAdClient asInterface = ITvAdClient.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(asInterface, readString2, readString3, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(readStrongBinder, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startAdService(readStrongBinder2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAdService(readStrongBinder3, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetAdService(readStrongBinder4, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(readStrongBinder5, surface, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readStrongBinder6, readInt10, readInt11, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(readStrongBinder7, rect, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(readStrongBinder8, uri, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(readStrongBinder9, createTypedArrayList, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    String readString4 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(readStrongBinder10, readString4, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    String readString5 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(readStrongBinder11, readString5, createByteArray, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    String readString6 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyError(readStrongBinder12, readString6, bundle2, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readStrongBinder13, readInt20, bundle3, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ITvAdManagerCallback asInterface2 = ITvAdManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface2, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ITvAdManagerCallback asInterface3 = ITvAdManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface3, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IBinder readStrongBinder14 = parcel.readStrongBinder();
                    IBinder readStrongBinder15 = parcel.readStrongBinder();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createMediaView(readStrongBinder14, readStrongBinder15, rect2, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IBinder readStrongBinder16 = parcel.readStrongBinder();
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(readStrongBinder16, rect3, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IBinder readStrongBinder17 = parcel.readStrongBinder();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMediaView(readStrongBinder17, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IBinder readStrongBinder18 = parcel.readStrongBinder();
                    String readString7 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvInputSessionData(readStrongBinder18, readString7, bundle4, readInt27);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITvAdManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdManager.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdManager
            public List<TvAdServiceInfo> getTvAdServiceList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(TvAdServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createSession(ITvAdClient iTvAdClient, String str, String str2, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdClient);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void releaseSession(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void startAdService(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void stopAdService(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void resetAdService(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void registerCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void unregisterCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvAdManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void removeMediaView(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvInputSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
