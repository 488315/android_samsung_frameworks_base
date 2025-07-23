package android.hardware.hdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IHdmiRecordListener extends IInterface {

    public static class Default implements IHdmiRecordListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiRecordListener
        public byte[] getOneTouchRecordSource(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.hdmi.IHdmiRecordListener
        public void onClearTimerRecordingResult(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiRecordListener
        public void onOneTouchRecordResult(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.hdmi.IHdmiRecordListener
        public void onTimerRecordingResult(int i, int i2) throws RemoteException {
        }
    }

    byte[] getOneTouchRecordSource(int i) throws RemoteException;

    void onClearTimerRecordingResult(int i, int i2) throws RemoteException;

    void onOneTouchRecordResult(int i, int i2) throws RemoteException;

    void onTimerRecordingResult(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IHdmiRecordListener {
        public static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiRecordListener";
        static final int TRANSACTION_getOneTouchRecordSource = 1;
        static final int TRANSACTION_onClearTimerRecordingResult = 4;
        static final int TRANSACTION_onOneTouchRecordResult = 2;
        static final int TRANSACTION_onTimerRecordingResult = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiRecordListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IHdmiRecordListener)) {
                return (IHdmiRecordListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getOneTouchRecordSource";
            }
            if (i == 2) {
                return "onOneTouchRecordResult";
            }
            if (i == 3) {
                return "onTimerRecordingResult";
            }
            if (i != 4) {
                return null;
            }
            return "onClearTimerRecordingResult";
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
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                byte[] oneTouchRecordSource = getOneTouchRecordSource(readInt);
                parcel2.writeNoException();
                parcel2.writeByteArray(oneTouchRecordSource);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onOneTouchRecordResult(readInt2, readInt3);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTimerRecordingResult(readInt4, readInt5);
                parcel2.writeNoException();
            } else if (i == 4) {
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onClearTimerRecordingResult(readInt6, readInt7);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IHdmiRecordListener {
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

            @Override // android.hardware.hdmi.IHdmiRecordListener
            public byte[] getOneTouchRecordSource(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiRecordListener
            public void onOneTouchRecordResult(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiRecordListener
            public void onTimerRecordingResult(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.hdmi.IHdmiRecordListener
            public void onClearTimerRecordingResult(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
