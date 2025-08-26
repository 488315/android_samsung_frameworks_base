package android.media;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface ISoundDose extends IInterface {
    public static final String DESCRIPTOR = "android.media.ISoundDose";

    public static class Default implements ISoundDose {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.ISoundDose
        public void forceComputeCsdOnAllDevices(boolean z) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void forceUseFrameworkMel(boolean z) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public float getCsd() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.ISoundDose
        public float getOutputRs2UpperBound() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.ISoundDose
        public void initCachedAudioDeviceCategories(AudioDeviceCategory[] audioDeviceCategoryArr) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public boolean isSoundDoseHalSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.ISoundDose
        public void resetCsd(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void setAudioDeviceCategory(AudioDeviceCategory audioDeviceCategory) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void setCsdEnabled(boolean z) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void setOutputRs2UpperBound(float f) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void updateAttenuation(float f, int i) throws RemoteException {
        }
    }

    void forceComputeCsdOnAllDevices(boolean z) throws RemoteException;

    void forceUseFrameworkMel(boolean z) throws RemoteException;

    float getCsd() throws RemoteException;

    float getOutputRs2UpperBound() throws RemoteException;

    void initCachedAudioDeviceCategories(AudioDeviceCategory[] audioDeviceCategoryArr) throws RemoteException;

    boolean isSoundDoseHalSupported() throws RemoteException;

    void resetCsd(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException;

    void setAudioDeviceCategory(AudioDeviceCategory audioDeviceCategory) throws RemoteException;

    void setCsdEnabled(boolean z) throws RemoteException;

    void setOutputRs2UpperBound(float f) throws RemoteException;

    void updateAttenuation(float f, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISoundDose {
        static final int TRANSACTION_forceComputeCsdOnAllDevices = 11;
        static final int TRANSACTION_forceUseFrameworkMel = 10;
        static final int TRANSACTION_getCsd = 8;
        static final int TRANSACTION_getOutputRs2UpperBound = 7;
        static final int TRANSACTION_initCachedAudioDeviceCategories = 5;
        static final int TRANSACTION_isSoundDoseHalSupported = 9;
        static final int TRANSACTION_resetCsd = 2;
        static final int TRANSACTION_setAudioDeviceCategory = 6;
        static final int TRANSACTION_setCsdEnabled = 4;
        static final int TRANSACTION_setOutputRs2UpperBound = 1;
        static final int TRANSACTION_updateAttenuation = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISoundDose.DESCRIPTOR);
        }

        public static ISoundDose asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISoundDose.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISoundDose)) {
                return (ISoundDose) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISoundDose.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISoundDose.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setOutputRs2UpperBound(f);
                    return true;
                case 2:
                    float f2 = parcel.readFloat();
                    SoundDoseRecord[] soundDoseRecordArr = (SoundDoseRecord[]) parcel.createTypedArray(SoundDoseRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetCsd(f2, soundDoseRecordArr);
                    return true;
                case 3:
                    float f3 = parcel.readFloat();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateAttenuation(f3, i3);
                    return true;
                case 4:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCsdEnabled(z);
                    return true;
                case 5:
                    AudioDeviceCategory[] audioDeviceCategoryArr = (AudioDeviceCategory[]) parcel.createTypedArray(AudioDeviceCategory.CREATOR);
                    parcel.enforceNoDataAvail();
                    initCachedAudioDeviceCategories(audioDeviceCategoryArr);
                    return true;
                case 6:
                    AudioDeviceCategory audioDeviceCategory = (AudioDeviceCategory) parcel.readTypedObject(AudioDeviceCategory.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAudioDeviceCategory(audioDeviceCategory);
                    return true;
                case 7:
                    float outputRs2UpperBound = getOutputRs2UpperBound();
                    parcel2.writeNoException();
                    parcel2.writeFloat(outputRs2UpperBound);
                    return true;
                case 8:
                    float csd = getCsd();
                    parcel2.writeNoException();
                    parcel2.writeFloat(csd);
                    return true;
                case 9:
                    boolean zIsSoundDoseHalSupported = isSoundDoseHalSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSoundDoseHalSupported);
                    return true;
                case 10:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceUseFrameworkMel(z2);
                    return true;
                case 11:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceComputeCsdOnAllDevices(z3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISoundDose {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundDose.DESCRIPTOR;
            }

            @Override // android.media.ISoundDose
            public void setOutputRs2UpperBound(float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void resetCsd(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeTypedArray(soundDoseRecordArr, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void updateAttenuation(float f, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void setCsdEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void initCachedAudioDeviceCategories(AudioDeviceCategory[] audioDeviceCategoryArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeTypedArray(audioDeviceCategoryArr, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void setAudioDeviceCategory(AudioDeviceCategory audioDeviceCategory) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeTypedObject(audioDeviceCategory, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public float getOutputRs2UpperBound() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public float getCsd() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public boolean isSoundDoseHalSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void forceUseFrameworkMel(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void forceComputeCsdOnAllDevices(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class AudioDeviceCategory implements Parcelable {
        public static final Parcelable.Creator<AudioDeviceCategory> CREATOR = new Parcelable.Creator<AudioDeviceCategory>() { // from class: android.media.ISoundDose.AudioDeviceCategory.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioDeviceCategory createFromParcel(Parcel parcel) {
                AudioDeviceCategory audioDeviceCategory = new AudioDeviceCategory();
                audioDeviceCategory.readFromParcel(parcel);
                return audioDeviceCategory;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioDeviceCategory[] newArray(int i) {
                return new AudioDeviceCategory[i];
            }
        };
        public String address;
        public int internalAudioType = 0;
        public boolean csdCompatible = false;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.address);
            parcel.writeInt(this.internalAudioType);
            parcel.writeBoolean(this.csdCompatible);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.address = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.internalAudioType = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.csdCompatible = parcel.readBoolean();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }

        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
            stringJoiner.add("address: " + Objects.toString(this.address));
            stringJoiner.add("internalAudioType: " + this.internalAudioType);
            stringJoiner.add("csdCompatible: " + this.csdCompatible);
            return "AudioDeviceCategory" + stringJoiner.toString();
        }
    }
}
