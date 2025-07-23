package android.media.audiopolicy;

import android.media.AudioFocusInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAudioPolicyCallback extends IInterface {

    public static class Default implements IAudioPolicyCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int i) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean z) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int i) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyMixStateUpdate(String str, int i) throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyUnregistration() throws RemoteException {
        }

        @Override // android.media.audiopolicy.IAudioPolicyCallback
        public void notifyVolumeAdjust(int i) throws RemoteException {
        }
    }

    void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) throws RemoteException;

    void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int i) throws RemoteException;

    void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean z) throws RemoteException;

    void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int i) throws RemoteException;

    void notifyMixStateUpdate(String str, int i) throws RemoteException;

    void notifyUnregistration() throws RemoteException;

    void notifyVolumeAdjust(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioPolicyCallback {
        public static final String DESCRIPTOR = "android.media.audiopolicy.IAudioPolicyCallback";
        static final int TRANSACTION_notifyAudioFocusAbandon = 4;
        static final int TRANSACTION_notifyAudioFocusGrant = 1;
        static final int TRANSACTION_notifyAudioFocusLoss = 2;
        static final int TRANSACTION_notifyAudioFocusRequest = 3;
        static final int TRANSACTION_notifyMixStateUpdate = 5;
        static final int TRANSACTION_notifyUnregistration = 7;
        static final int TRANSACTION_notifyVolumeAdjust = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAudioPolicyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAudioPolicyCallback)) {
                return (IAudioPolicyCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyAudioFocusGrant";
                case 2:
                    return "notifyAudioFocusLoss";
                case 3:
                    return "notifyAudioFocusRequest";
                case 4:
                    return "notifyAudioFocusAbandon";
                case 5:
                    return "notifyMixStateUpdate";
                case 6:
                    return "notifyVolumeAdjust";
                case 7:
                    return "notifyUnregistration";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    AudioFocusInfo audioFocusInfo = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusGrant(audioFocusInfo, readInt);
                    return true;
                case 2:
                    AudioFocusInfo audioFocusInfo2 = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusLoss(audioFocusInfo2, readBoolean);
                    return true;
                case 3:
                    AudioFocusInfo audioFocusInfo3 = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusRequest(audioFocusInfo3, readInt2);
                    return true;
                case 4:
                    AudioFocusInfo audioFocusInfo4 = (AudioFocusInfo) parcel.readTypedObject(AudioFocusInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAudioFocusAbandon(audioFocusInfo4);
                    return true;
                case 5:
                    String readString = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyMixStateUpdate(readString, readInt3);
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVolumeAdjust(readInt4);
                    return true;
                case 7:
                    notifyUnregistration();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAudioPolicyCallback {
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

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusGrant(AudioFocusInfo audioFocusInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusLoss(AudioFocusInfo audioFocusInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusRequest(AudioFocusInfo audioFocusInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusAbandon(AudioFocusInfo audioFocusInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(audioFocusInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyMixStateUpdate(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyVolumeAdjust(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyUnregistration() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
