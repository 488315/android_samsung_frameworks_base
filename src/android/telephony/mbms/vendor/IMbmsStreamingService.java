package android.telephony.mbms.vendor;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.IMbmsStreamingSessionCallback;
import android.telephony.mbms.IStreamingServiceCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IMbmsStreamingService extends IInterface {

    public static class Default implements IMbmsStreamingService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public void dispose(int i) throws RemoteException {
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public Uri getPlaybackUri(int i, String str) throws RemoteException {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public int initialize(IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int i) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public int requestUpdateStreamingServices(int i, List<String> list) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public int startStreaming(int i, String str, IStreamingServiceCallback iStreamingServiceCallback) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public void stopStreaming(int i, String str) throws RemoteException {
        }
    }

    void dispose(int i) throws RemoteException;

    Uri getPlaybackUri(int i, String str) throws RemoteException;

    int initialize(IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int i) throws RemoteException;

    int requestUpdateStreamingServices(int i, List<String> list) throws RemoteException;

    int startStreaming(int i, String str, IStreamingServiceCallback iStreamingServiceCallback) throws RemoteException;

    void stopStreaming(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IMbmsStreamingService {
        public static final String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsStreamingService";
        static final int TRANSACTION_dispose = 6;
        static final int TRANSACTION_getPlaybackUri = 4;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_requestUpdateStreamingServices = 2;
        static final int TRANSACTION_startStreaming = 3;
        static final int TRANSACTION_stopStreaming = 5;

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

        public static IMbmsStreamingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMbmsStreamingService)) {
                return (IMbmsStreamingService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initialize";
                case 2:
                    return "requestUpdateStreamingServices";
                case 3:
                    return "startStreaming";
                case 4:
                    return "getPlaybackUri";
                case 5:
                    return "stopStreaming";
                case 6:
                    return "dispose";
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
                    IMbmsStreamingSessionCallback asInterface = IMbmsStreamingSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialize = initialize(asInterface, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialize);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int requestUpdateStreamingServices = requestUpdateStreamingServices(readInt2, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestUpdateStreamingServices);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    String readString = parcel.readString();
                    IStreamingServiceCallback asInterface2 = IStreamingServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startStreaming = startStreaming(readInt3, readString, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startStreaming);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Uri playbackUri = getPlaybackUri(readInt4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(playbackUri, 1);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopStreaming(readInt5, readString3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispose(readInt6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMbmsStreamingService {
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

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public int initialize(IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMbmsStreamingSessionCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public int requestUpdateStreamingServices(int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public int startStreaming(int i, String str, IStreamingServiceCallback iStreamingServiceCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iStreamingServiceCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public Uri getPlaybackUri(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Uri) obtain2.readTypedObject(Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public void stopStreaming(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public void dispose(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
