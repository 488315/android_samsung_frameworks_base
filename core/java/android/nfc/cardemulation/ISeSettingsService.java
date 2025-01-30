package android.nfc.cardemulation;

import android.content.ComponentName;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface ISeSettingsService extends IInterface {
    public static final String DESCRIPTOR = "android.nfc.cardemulation.ISeSettingsService";

    void setSeacActive(ComponentName componentName, boolean z) throws RemoteException;

    public static class Default implements ISeSettingsService {
        @Override // android.nfc.cardemulation.ISeSettingsService
        public void setSeacActive(ComponentName service, boolean isMismatchCheckNeeded) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISeSettingsService {
        static final int TRANSACTION_setSeacActive = 1;

        public Stub() {
            attachInterface(this, ISeSettingsService.DESCRIPTOR);
        }

        public static ISeSettingsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISeSettingsService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISeSettingsService)) {
                return (ISeSettingsService) iin;
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
                    return "setSeacActive";
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
                data.enforceInterface(ISeSettingsService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISeSettingsService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ComponentName _arg0 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            boolean _arg1 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setSeacActive(_arg0, _arg1);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISeSettingsService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISeSettingsService.DESCRIPTOR;
            }

            @Override // android.nfc.cardemulation.ISeSettingsService
            public void setSeacActive(ComponentName service, boolean isMismatchCheckNeeded) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISeSettingsService.DESCRIPTOR);
                    _data.writeTypedObject(service, 0);
                    _data.writeBoolean(isMismatchCheckNeeded);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
