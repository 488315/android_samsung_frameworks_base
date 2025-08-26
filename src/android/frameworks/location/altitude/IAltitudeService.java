package android.frameworks.location.altitude;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAltitudeService extends IInterface {
    public static final String DESCRIPTOR = "android$frameworks$location$altitude$IAltitudeService".replace('$', '.');
    public static final String HASH = "e47d23f579ff7a897fb03e7e7f1c3006cfc6036b";
    public static final int VERSION = 2;

    AddMslAltitudeToLocationResponse addMslAltitudeToLocation(AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws RemoteException;

    GetGeoidHeightResponse getGeoidHeight(GetGeoidHeightRequest getGeoidHeightRequest) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    public static class Default implements IAltitudeService {
        @Override // android.frameworks.location.altitude.IAltitudeService
        public AddMslAltitudeToLocationResponse addMslAltitudeToLocation(AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public GetGeoidHeightResponse getGeoidHeight(GetGeoidHeightRequest getGeoidHeightRequest) throws RemoteException {
            return null;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.frameworks.location.altitude.IAltitudeService
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IAltitudeService {
        static final int TRANSACTION_addMslAltitudeToLocation = 1;
        static final int TRANSACTION_getGeoidHeight = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IAltitudeService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAltitudeService)) {
                return (IAltitudeService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addMslAltitudeToLocation";
            }
            if (i == 2) {
                return "getGeoidHeight";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            if (i == 1) {
                AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest = (AddMslAltitudeToLocationRequest) parcel.readTypedObject(AddMslAltitudeToLocationRequest.CREATOR);
                parcel.enforceNoDataAvail();
                AddMslAltitudeToLocationResponse addMslAltitudeToLocationResponseAddMslAltitudeToLocation = addMslAltitudeToLocation(addMslAltitudeToLocationRequest);
                parcel2.writeNoException();
                parcel2.writeTypedObject(addMslAltitudeToLocationResponseAddMslAltitudeToLocation, 1);
            } else if (i == 2) {
                GetGeoidHeightRequest getGeoidHeightRequest = (GetGeoidHeightRequest) parcel.readTypedObject(GetGeoidHeightRequest.CREATOR);
                parcel.enforceNoDataAvail();
                GetGeoidHeightResponse geoidHeight = getGeoidHeight(getGeoidHeightRequest);
                parcel2.writeNoException();
                parcel2.writeTypedObject(geoidHeight, 1);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAltitudeService {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public AddMslAltitudeToLocationResponse addMslAltitudeToLocation(AddMslAltitudeToLocationRequest addMslAltitudeToLocationRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(addMslAltitudeToLocationRequest, 0);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method addMslAltitudeToLocation is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (AddMslAltitudeToLocationResponse) parcelObtain2.readTypedObject(AddMslAltitudeToLocationResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public GetGeoidHeightResponse getGeoidHeight(GetGeoidHeightRequest getGeoidHeightRequest) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(getGeoidHeightRequest, 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getGeoidHeight is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (GetGeoidHeightResponse) parcelObtain2.readTypedObject(GetGeoidHeightResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.frameworks.location.altitude.IAltitudeService
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
