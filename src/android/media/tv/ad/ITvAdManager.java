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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITvAdManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITvAdManager)) {
                return (ITvAdManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<TvAdServiceInfo> tvAdServiceList = getTvAdServiceList(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvAdServiceList, 1);
                    return true;
                case 2:
                    String string = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(string, bundle, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ITvAdClient iTvAdClientAsInterface = ITvAdClient.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(iTvAdClientAsInterface, string2, string3, i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(strongBinder, i7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startAdService(strongBinder2, i8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopAdService(strongBinder3, i9);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetAdService(strongBinder4, i10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(strongBinder5, surface, i11);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(strongBinder6, i12, i13, i14, i15);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(strongBinder7, rect, i16);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(strongBinder8, uri, i17);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(TvTrackInfo.CREATOR);
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(strongBinder9, arrayListCreateTypedArrayList, i18);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    String string4 = parcel.readString();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(strongBinder10, string4, i19);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    String string5 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(strongBinder11, string5, bArrCreateByteArray, i20);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    String string6 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyError(strongBinder12, string6, bundle2, i21);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    int i22 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(strongBinder13, i22, bundle3, i23);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ITvAdManagerCallback iTvAdManagerCallbackAsInterface = ITvAdManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(iTvAdManagerCallbackAsInterface, i24);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ITvAdManagerCallback iTvAdManagerCallbackAsInterface2 = ITvAdManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(iTvAdManagerCallbackAsInterface2, i25);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IBinder strongBinder14 = parcel.readStrongBinder();
                    IBinder strongBinder15 = parcel.readStrongBinder();
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createMediaView(strongBinder14, strongBinder15, rect2, i26);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IBinder strongBinder16 = parcel.readStrongBinder();
                    Rect rect3 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(strongBinder16, rect3, i27);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IBinder strongBinder17 = parcel.readStrongBinder();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMediaView(strongBinder17, i28);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IBinder strongBinder18 = parcel.readStrongBinder();
                    String string7 = parcel.readString();
                    Bundle bundle4 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvInputSessionData(strongBinder18, string7, bundle4, i29);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(TvAdServiceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendAppLinkCommand(String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createSession(ITvAdClient iTvAdClient, String str, String str2, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvAdClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void releaseSession(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void startAdService(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void stopAdService(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void resetAdService(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void setSurface(IBinder iBinder, Surface surface, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(surface, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void dispatchSurfaceChanged(IBinder iBinder, int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentVideoBounds(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentChannelUri(IBinder iBinder, Uri uri, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendTrackInfoList(IBinder iBinder, List<TvTrackInfo> list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendCurrentTvInputId(IBinder iBinder, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void sendSigningResult(IBinder iBinder, String str, byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyError(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvMessage(IBinder iBinder, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void registerCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvAdManagerCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void unregisterCallback(ITvAdManagerCallback iTvAdManagerCallback, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTvAdManagerCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void createMediaView(IBinder iBinder, IBinder iBinder2, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void relayoutMediaView(IBinder iBinder, Rect rect, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void removeMediaView(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManager
            public void notifyTvInputSessionData(IBinder iBinder, String str, Bundle bundle, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITvAdManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
