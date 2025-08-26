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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAudioPolicyServiceClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAudioPolicyServiceClient)) {
                return (IAudioPolicyServiceClient) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioVolumeGroupChanged(i3, i4);
                    return true;
                case 2:
                    onAudioPortListUpdate();
                    return true;
                case 3:
                    onAudioPatchListUpdate();
                    return true;
                case 4:
                    String string = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDynamicPolicyMixStateUpdate(string, i5);
                    return true;
                case 5:
                    int i6 = parcel.readInt();
                    RecordClientInfo recordClientInfo = (RecordClientInfo) parcel.readTypedObject(RecordClientInfo.CREATOR);
                    AudioConfigBase audioConfigBase = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                    EffectDescriptor[] effectDescriptorArr = (EffectDescriptor[]) parcel.createTypedArray(EffectDescriptor.CREATOR);
                    AudioConfigBase audioConfigBase2 = (AudioConfigBase) parcel.readTypedObject(AudioConfigBase.CREATOR);
                    EffectDescriptor[] effectDescriptorArr2 = (EffectDescriptor[]) parcel.createTypedArray(EffectDescriptor.CREATOR);
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecordingConfigurationUpdate(i6, recordClientInfo, audioConfigBase, effectDescriptorArr, audioConfigBase2, effectDescriptorArr2, i7, i8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioPortListUpdate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onAudioPatchListUpdate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onDynamicPolicyMixStateUpdate(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onRecordingConfigurationUpdate(int i, RecordClientInfo recordClientInfo, AudioConfigBase audioConfigBase, EffectDescriptor[] effectDescriptorArr, AudioConfigBase audioConfigBase2, EffectDescriptor[] effectDescriptorArr2, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(recordClientInfo, 0);
                    parcelObtain.writeTypedObject(audioConfigBase, 0);
                    parcelObtain.writeTypedArray(effectDescriptorArr, 0);
                    parcelObtain.writeTypedObject(audioConfigBase2, 0);
                    parcelObtain.writeTypedArray(effectDescriptorArr2, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onRoutingUpdated() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IAudioPolicyServiceClient
            public void onVolumeRangeInitRequest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IAudioPolicyServiceClient.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
