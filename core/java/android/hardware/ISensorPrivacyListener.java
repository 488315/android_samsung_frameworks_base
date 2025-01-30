package android.hardware;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface ISensorPrivacyListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.ISensorPrivacyListener";

    void onSensorPrivacyChanged(int i, int i2, boolean z) throws RemoteException;

    public static class Default implements ISensorPrivacyListener {
        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyChanged(int toggleType, int sensor, boolean enabled) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISensorPrivacyListener {
        static final int TRANSACTION_onSensorPrivacyChanged = 1;

        public Stub() {
            attachInterface(this, ISensorPrivacyListener.DESCRIPTOR);
        }

        public static ISensorPrivacyListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISensorPrivacyListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISensorPrivacyListener)) {
                return (ISensorPrivacyListener) iin;
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
                    return "onSensorPrivacyChanged";
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
                data.enforceInterface(ISensorPrivacyListener.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISensorPrivacyListener.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            boolean _arg2 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onSensorPrivacyChanged(_arg0, _arg1, _arg2);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISensorPrivacyListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISensorPrivacyListener.DESCRIPTOR;
            }

            @Override // android.hardware.ISensorPrivacyListener
            public void onSensorPrivacyChanged(int toggleType, int sensor, boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISensorPrivacyListener.DESCRIPTOR);
                    _data.writeInt(toggleType);
                    _data.writeInt(sensor);
                    _data.writeBoolean(enabled);
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
