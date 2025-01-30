package android.media;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface ISpatializerHeadTrackingCallback extends IInterface {
    public static final String DESCRIPTOR = "android$media$ISpatializerHeadTrackingCallback".replace('$', '.');

    void onHeadToSoundStagePoseUpdated(float[] fArr) throws RemoteException;

    void onHeadTrackingModeChanged(byte b) throws RemoteException;

    public static class Default implements ISpatializerHeadTrackingCallback {
        @Override // android.media.ISpatializerHeadTrackingCallback
        public void onHeadTrackingModeChanged(byte mode) throws RemoteException {
        }

        @Override // android.media.ISpatializerHeadTrackingCallback
        public void onHeadToSoundStagePoseUpdated(float[] headToStage) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISpatializerHeadTrackingCallback {
        static final int TRANSACTION_onHeadToSoundStagePoseUpdated = 2;
        static final int TRANSACTION_onHeadTrackingModeChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ISpatializerHeadTrackingCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ISpatializerHeadTrackingCallback)) {
                return (ISpatializerHeadTrackingCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(descriptor);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            byte _arg0 = data.readByte();
                            data.enforceNoDataAvail();
                            onHeadTrackingModeChanged(_arg0);
                            return true;
                        case 2:
                            float[] _arg02 = data.createFloatArray();
                            data.enforceNoDataAvail();
                            onHeadToSoundStagePoseUpdated(_arg02);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISpatializerHeadTrackingCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.media.ISpatializerHeadTrackingCallback
            public void onHeadTrackingModeChanged(byte mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeByte(mode);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISpatializerHeadTrackingCallback
            public void onHeadToSoundStagePoseUpdated(float[] headToStage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeFloatArray(headToStage);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
