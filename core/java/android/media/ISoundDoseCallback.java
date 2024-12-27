package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISoundDoseCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISoundDoseCallback";

    void onMomentaryExposure(float f, int i) throws RemoteException;

    void onNewCsdValue(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException;

    public static class Default implements ISoundDoseCallback {
        @Override // android.media.ISoundDoseCallback
        public void onMomentaryExposure(float currentMel, int deviceId) throws RemoteException {}

        @Override // android.media.ISoundDoseCallback
        public void onNewCsdValue(float currentCsd, SoundDoseRecord[] records)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISoundDoseCallback {
        static final int TRANSACTION_onMomentaryExposure = 1;
        static final int TRANSACTION_onNewCsdValue = 2;

        public Stub() {
            attachInterface(this, ISoundDoseCallback.DESCRIPTOR);
        }

        public static ISoundDoseCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISoundDoseCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISoundDoseCallback)) {
                return (ISoundDoseCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISoundDoseCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISoundDoseCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    float _arg0 = data.readFloat();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onMomentaryExposure(_arg0, _arg1);
                    return true;
                case 2:
                    float _arg02 = data.readFloat();
                    SoundDoseRecord[] _arg12 =
                            (SoundDoseRecord[]) data.createTypedArray(SoundDoseRecord.CREATOR);
                    data.enforceNoDataAvail();
                    onNewCsdValue(_arg02, _arg12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISoundDoseCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundDoseCallback.DESCRIPTOR;
            }

            @Override // android.media.ISoundDoseCallback
            public void onMomentaryExposure(float currentMel, int deviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDoseCallback.DESCRIPTOR);
                    _data.writeFloat(currentMel);
                    _data.writeInt(deviceId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDoseCallback
            public void onNewCsdValue(float currentCsd, SoundDoseRecord[] records)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDoseCallback.DESCRIPTOR);
                    _data.writeFloat(currentCsd);
                    _data.writeTypedArray(records, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
