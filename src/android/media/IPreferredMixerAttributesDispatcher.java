package android.media;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IPreferredMixerAttributesDispatcher extends IInterface {
    public static final String DESCRIPTOR = "android.media.IPreferredMixerAttributesDispatcher";

    public static class Default implements IPreferredMixerAttributesDispatcher {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IPreferredMixerAttributesDispatcher
        public void dispatchPrefMixerAttributesChanged(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) throws RemoteException {
        }
    }

    void dispatchPrefMixerAttributesChanged(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) throws RemoteException;

    public static abstract class Stub extends Binder implements IPreferredMixerAttributesDispatcher {
        static final int TRANSACTION_dispatchPrefMixerAttributesChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPreferredMixerAttributesDispatcher.DESCRIPTOR);
        }

        public static IPreferredMixerAttributesDispatcher asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPreferredMixerAttributesDispatcher)) {
                return (IPreferredMixerAttributesDispatcher) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "dispatchPrefMixerAttributesChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AudioAttributes audioAttributes = (AudioAttributes) parcel.readTypedObject(AudioAttributes.CREATOR);
                int readInt = parcel.readInt();
                AudioMixerAttributes audioMixerAttributes = (AudioMixerAttributes) parcel.readTypedObject(AudioMixerAttributes.CREATOR);
                parcel.enforceNoDataAvail();
                dispatchPrefMixerAttributesChanged(audioAttributes, readInt, audioMixerAttributes);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPreferredMixerAttributesDispatcher {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPreferredMixerAttributesDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IPreferredMixerAttributesDispatcher
            public void dispatchPrefMixerAttributesChanged(AudioAttributes audioAttributes, int i, AudioMixerAttributes audioMixerAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPreferredMixerAttributesDispatcher.DESCRIPTOR);
                    obtain.writeTypedObject(audioAttributes, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(audioMixerAttributes, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
