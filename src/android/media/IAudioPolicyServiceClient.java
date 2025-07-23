package android.media;

import android.media.audio.common.AudioConfigBase;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IAudioPolicyServiceClient extends IInterface {
    public static final String DESCRIPTOR = "android.media.IAudioPolicyServiceClient";

    public static class Default implements IAudioPolicyServiceClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onAudioPatchListUpdate() throws RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onAudioPortListUpdate() throws RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onAudioVolumeGroupChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onDynamicPolicyMixStateUpdate(String str, int i) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onRecordingConfigurationUpdate(int i, RecordClientInfo recordClientInfo, AudioConfigBase audioConfigBase, EffectDescriptor[] effectDescriptorArr, AudioConfigBase audioConfigBase2, EffectDescriptor[] effectDescriptorArr2, int i2, int i3) throws RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onRoutingUpdated() throws RemoteException {
        }

        @Override // android.media.IAudioPolicyServiceClient
        public void onVolumeRangeInitRequest() throws RemoteException {
        }
    }

    void onAudioPatchListUpdate() throws RemoteException;

    void onAudioPortListUpdate() throws RemoteException;

    void onAudioVolumeGroupChanged(int i, int i2) throws RemoteException;

    void onDynamicPolicyMixStateUpdate(String str, int i) throws RemoteException;

    void onRecordingConfigurationUpdate(int i, RecordClientInfo recordClientInfo, AudioConfigBase audioConfigBase, EffectDescriptor[] effectDescriptorArr, AudioConfigBase audioConfigBase2, EffectDescriptor[] effectDescriptorArr2, int i2, int i3) throws RemoteException;

    void onRoutingUpdated() throws RemoteException;

    void onVolumeRangeInitRequest() throws RemoteException;

    public static abstract class Stub extends Binder implements IAudioPolicyServiceClient {
        static final int TRANSACTION_onAudioPatchListUpdate = 3;
        static final int TRANSACTION_onAudioPortListUpdate = 2;
        static final int TRANSACTION_onAudioVolumeGroupChanged = 1;
        static final int TRANSACTION_onDynamicPolicyMixStateUpdate = 4;
        static final int TRANSACTION_onRecordingConfigurationUpdate = 5;
        static final int TRANSACTION_onRoutingUpdated = 6;
        static final int TRANSACTION_onVolumeRangeInitRequest = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IAudioPolicyServiceClient.DESCRIPTOR);
        }

        public static IAudioPolicyServiceClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAudioPolicyServiceClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAudioPolicyServiceClient)) {
                return (IAudioPolicyServiceClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAudioPolicyServiceClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAudioPolicyServiceClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioVolumeGroupChanged(readInt, readInt2);
                    return true;
                case 2:
                    onAudioPortListUpdate();
                    return true;
                case 3:
                    onAudioPatchListUpdate();
                    return true;
                case 4:
                    String readString = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDynamicPolicyMixStateUpdate(readString, readInt3);
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    RecordClientInfo recordClientInfo = (RecordClientInfo) parcel.readTypedObject(RecordClientInfo.CREATOR);
                    AudioConfigBase audioConfigBase = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                    EffectDescriptor[] effectDescriptorArr = (EffectDescriptor[]) parcel.createTypedArray(EffectDescriptor.CREATOR);
                    AudioConfigBase audioConfigBase2 = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                    EffectDescriptor[] effectDescriptorArr2 = (EffectDescriptor[]) parcel.createTypedArray(EffectDescriptor.CREATOR);
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecordingConfigurationUpdate(readInt4, recordClientInfo, audioConfigBase, effectDescriptorArr, audioConfigBase2, effectDescriptorArr2, readInt5, readInt6);
                    return true;
                case 6:
                    onRoutingUpdated();
                    return true;
                case 7:
                    onVolumeRangeInitRequest();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAudioPolicyServiceClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAudioPolicyServiceClient.DESCRIPTOR;
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioVolumeGroupChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioPortListUpdate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioPatchListUpdate() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onDynamicPolicyMixStateUpdate(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onRecordingConfigurationUpdate(int i, RecordClientInfo recordClientInfo, AudioConfigBase audioConfigBase, EffectDescriptor[] effectDescriptorArr, AudioConfigBase audioConfigBase2, EffectDescriptor[] effectDescriptorArr2, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(recordClientInfo, 0);
                    obtain.writeTypedObject(audioConfigBase, 0);
                    obtain.writeTypedArray(effectDescriptorArr, 0);
                    obtain.writeTypedObject(audioConfigBase2, 0);
                    obtain.writeTypedArray(effectDescriptorArr2, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onRoutingUpdated() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onVolumeRangeInitRequest() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
