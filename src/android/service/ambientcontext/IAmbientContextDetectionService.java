package android.service.ambientcontext;

import android.app.ambientcontext.AmbientContextEventRequest;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IAmbientContextDetectionService extends IInterface {
    public static final String DESCRIPTOR = "android.service.ambientcontext.IAmbientContextDetectionService";

    public static class Default implements IAmbientContextDetectionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
        }

        @Override // android.service.ambientcontext.IAmbientContextDetectionService
        public void stopDetection(String str) throws RemoteException {
        }
    }

    void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException;

    void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException;

    void stopDetection(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAmbientContextDetectionService {
        static final int TRANSACTION_queryServiceStatus = 3;
        static final int TRANSACTION_startDetection = 1;
        static final int TRANSACTION_stopDetection = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IAmbientContextDetectionService.DESCRIPTOR);
        }

        public static IAmbientContextDetectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAmbientContextDetectionService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAmbientContextDetectionService)) {
                return (IAmbientContextDetectionService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "startDetection";
            }
            if (i == 2) {
                return "stopDetection";
            }
            if (i != 3) {
                return null;
            }
            return "queryServiceStatus";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAmbientContextDetectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAmbientContextDetectionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AmbientContextEventRequest ambientContextEventRequest = (AmbientContextEventRequest) parcel.readTypedObject(AmbientContextEventRequest.CREATOR);
                String string = parcel.readString();
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                RemoteCallback remoteCallback2 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                startDetection(ambientContextEventRequest, string, remoteCallback, remoteCallback2);
            } else if (i == 2) {
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                stopDetection(string2);
            } else if (i == 3) {
                int[] iArrCreateIntArray = parcel.createIntArray();
                String string3 = parcel.readString();
                RemoteCallback remoteCallback3 = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                queryServiceStatus(iArrCreateIntArray, string3, remoteCallback3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAmbientContextDetectionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAmbientContextDetectionService.DESCRIPTOR;
            }

            @Override // android.service.ambientcontext.IAmbientContextDetectionService
            public void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAmbientContextDetectionService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(ambientContextEventRequest, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    parcelObtain.writeTypedObject(remoteCallback2, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.ambientcontext.IAmbientContextDetectionService
            public void stopDetection(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAmbientContextDetectionService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.ambientcontext.IAmbientContextDetectionService
            public void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAmbientContextDetectionService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
