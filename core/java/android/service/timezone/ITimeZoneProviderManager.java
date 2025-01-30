package android.service.timezone;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface ITimeZoneProviderManager extends IInterface {
    public static final String DESCRIPTOR = "android.service.timezone.ITimeZoneProviderManager";

    void onTimeZoneProviderEvent(TimeZoneProviderEvent timeZoneProviderEvent) throws RemoteException;

    public static class Default implements ITimeZoneProviderManager {
        @Override // android.service.timezone.ITimeZoneProviderManager
        public void onTimeZoneProviderEvent(TimeZoneProviderEvent timeZoneProviderEvent) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITimeZoneProviderManager {
        static final int TRANSACTION_onTimeZoneProviderEvent = 1;

        public Stub() {
            attachInterface(this, ITimeZoneProviderManager.DESCRIPTOR);
        }

        public static ITimeZoneProviderManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITimeZoneProviderManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ITimeZoneProviderManager)) {
                return (ITimeZoneProviderManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onTimeZoneProviderEvent";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ITimeZoneProviderManager.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ITimeZoneProviderManager.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            TimeZoneProviderEvent _arg0 = (TimeZoneProviderEvent) data.readTypedObject(TimeZoneProviderEvent.CREATOR);
                            data.enforceNoDataAvail();
                            onTimeZoneProviderEvent(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ITimeZoneProviderManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITimeZoneProviderManager.DESCRIPTOR;
            }

            @Override // android.service.timezone.ITimeZoneProviderManager
            public void onTimeZoneProviderEvent(TimeZoneProviderEvent timeZoneProviderEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITimeZoneProviderManager.DESCRIPTOR);
                    _data.writeTypedObject(timeZoneProviderEvent, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
