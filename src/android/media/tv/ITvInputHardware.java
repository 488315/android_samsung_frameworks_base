package android.media.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

/* loaded from: classes3.dex */
public interface ITvInputHardware extends IInterface {

    public static class Default implements ITvInputHardware {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvInputHardware
        public void overrideAudioSink(int i, String str, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputHardware
        public void setStreamVolume(float f) throws RemoteException {
        }

        @Override // android.media.tv.ITvInputHardware
        public boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) throws RemoteException {
            return false;
        }
    }

    void overrideAudioSink(int i, String str, int i2, int i3, int i4) throws RemoteException;

    void setStreamVolume(float f) throws RemoteException;

    boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvInputHardware {
        public static final String DESCRIPTOR = "android.media.tv.ITvInputHardware";
        static final int TRANSACTION_overrideAudioSink = 3;
        static final int TRANSACTION_setStreamVolume = 2;
        static final int TRANSACTION_setSurface = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvInputHardware)) {
                return (ITvInputHardware) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setSurface";
            }
            if (i == 2) {
                return "setStreamVolume";
            }
            if (i != 3) {
                return null;
            }
            return "overrideAudioSink";
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
                Surface surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                TvStreamConfig tvStreamConfig = (TvStreamConfig) parcel.readTypedObject(TvStreamConfig.CREATOR);
                parcel.enforceNoDataAvail();
                boolean surface2 = setSurface(surface, tvStreamConfig);
                parcel2.writeNoException();
                parcel2.writeBoolean(surface2);
            } else if (i == 2) {
                float readFloat = parcel.readFloat();
                parcel.enforceNoDataAvail();
                setStreamVolume(readFloat);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                overrideAudioSink(readInt, readString, readInt2, readInt3, readInt4);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITvInputHardware {
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

            @Override // android.media.tv.ITvInputHardware
            public boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeTypedObject(tvStreamConfig, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputHardware
            public void setStreamVolume(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputHardware
            public void overrideAudioSink(int i, String str, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
