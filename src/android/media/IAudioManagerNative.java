package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IAudioManagerNative extends IInterface {
    public static final String DESCRIPTOR = "android.media.IAudioManagerNative";

    public static class Default implements IAudioManagerNative {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IAudioManagerNative
        public void permissionUpdateBarrier() throws RemoteException {
        }

        @Override // android.media.IAudioManagerNative
        public void playbackHardeningEvent(int i, byte b, boolean z) throws RemoteException {
        }

        @Override // android.media.IAudioManagerNative
        public void portMuteEvent(int i, int i2) throws RemoteException {
        }
    }

    public @interface HardeningType {
        public static final byte FULL = 1;
        public static final byte PARTIAL = 0;
    }

    void permissionUpdateBarrier() throws RemoteException;

    void playbackHardeningEvent(int i, byte b, boolean z) throws RemoteException;

    void portMuteEvent(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioManagerNative {
        static final int TRANSACTION_permissionUpdateBarrier = 2;
        static final int TRANSACTION_playbackHardeningEvent = 1;
        static final int TRANSACTION_portMuteEvent = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAudioManagerNative.DESCRIPTOR);
        }

        public static IAudioManagerNative asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAudioManagerNative.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioManagerNative)) {
                return (IAudioManagerNative) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAudioManagerNative.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAudioManagerNative.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                byte b = parcel.readByte();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                playbackHardeningEvent(i3, b, z);
            } else if (i == 2) {
                permissionUpdateBarrier();
                parcel2.writeNoException();
            } else if (i == 3) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                portMuteEvent(i4, i5);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAudioManagerNative {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioManagerNative.DESCRIPTOR;
            }

            @Override // android.media.IAudioManagerNative
            public void playbackHardeningEvent(int i, byte b, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioManagerNative.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioManagerNative
            public void permissionUpdateBarrier() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAudioManagerNative.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioManagerNative
            public void portMuteEvent(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioManagerNative.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
