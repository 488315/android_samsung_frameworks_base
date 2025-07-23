package android.security.intrusiondetection;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IIntrusionDetectionEventTransport extends IInterface {
    public static final String DESCRIPTOR = "android.security.intrusiondetection.IIntrusionDetectionEventTransport";

    public static class Default implements IIntrusionDetectionEventTransport {
        @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
        public void addData(List<IntrusionDetectionEvent> list, AndroidFuture<Boolean> androidFuture) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
        public void initialize(AndroidFuture<Boolean> androidFuture) throws RemoteException {
        }

        @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
        public void release(AndroidFuture<Boolean> androidFuture) throws RemoteException {
        }
    }

    void addData(List<IntrusionDetectionEvent> list, AndroidFuture<Boolean> androidFuture) throws RemoteException;

    void initialize(AndroidFuture<Boolean> androidFuture) throws RemoteException;

    void release(AndroidFuture<Boolean> androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IIntrusionDetectionEventTransport {
        static final int TRANSACTION_addData = 2;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_release = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IIntrusionDetectionEventTransport.DESCRIPTOR);
        }

        public static IIntrusionDetectionEventTransport asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIntrusionDetectionEventTransport.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIntrusionDetectionEventTransport)) {
                return (IIntrusionDetectionEventTransport) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "initialize";
            }
            if (i == 2) {
                return "addData";
            }
            if (i != 3) {
                return null;
            }
            return "release";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIntrusionDetectionEventTransport.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIntrusionDetectionEventTransport.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AndroidFuture<Boolean> androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                initialize(androidFuture);
            } else if (i == 2) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(IntrusionDetectionEvent.CREATOR);
                AndroidFuture<Boolean> androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                addData(createTypedArrayList, androidFuture2);
            } else if (i == 3) {
                AndroidFuture<Boolean> androidFuture3 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                parcel.enforceNoDataAvail();
                release(androidFuture3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIntrusionDetectionEventTransport {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntrusionDetectionEventTransport.DESCRIPTOR;
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
            public void initialize(AndroidFuture<Boolean> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIntrusionDetectionEventTransport.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
            public void addData(List<IntrusionDetectionEvent> list, AndroidFuture<Boolean> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIntrusionDetectionEventTransport.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.intrusiondetection.IIntrusionDetectionEventTransport
            public void release(AndroidFuture<Boolean> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IIntrusionDetectionEventTransport.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
