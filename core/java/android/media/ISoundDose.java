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

    public static class Default implements ISoundDose {
        @Override // android.media.ISoundDose
        public void setOutputRs2UpperBound(float rs2Value) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void resetCsd(float currentCsd, SoundDoseRecord[] records) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void updateAttenuation(float attenuationDB, int device) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void setCsdEnabled(boolean enabled) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void initCachedAudioDeviceCategories(AudioDeviceCategory[] audioDevices) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void setAudioDeviceCategory(AudioDeviceCategory audioDevice) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public float getOutputRs2UpperBound() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.ISoundDose
        public float getCsd() throws RemoteException {
            return 0.0f;
        }

        @Override // android.media.ISoundDose
        public boolean isSoundDoseHalSupported() throws RemoteException {
            return false;
        }

        @Override // android.media.ISoundDose
        public void forceUseFrameworkMel(boolean useFrameworkMel) throws RemoteException {
        }

        @Override // android.media.ISoundDose
        public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, ISoundDose.DESCRIPTOR);
        }

        public static ISoundDose asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISoundDose.DESCRIPTOR);
            if (iin != null && (iin instanceof ISoundDose)) {
                return (ISoundDose) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISoundDose.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISoundDose.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    float _arg0 = data.readFloat();
                    data.enforceNoDataAvail();
                    setOutputRs2UpperBound(_arg0);
                    return true;
                case 2:
                    float _arg02 = data.readFloat();
                    SoundDoseRecord[] _arg1 = (SoundDoseRecord[]) data.createTypedArray(SoundDoseRecord.CREATOR);
                    data.enforceNoDataAvail();
                    resetCsd(_arg02, _arg1);
                    return true;
                case 3:
                    float _arg03 = data.readFloat();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    updateAttenuation(_arg03, _arg12);
                    return true;
                case 4:
                    boolean _arg04 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setCsdEnabled(_arg04);
                    return true;
                case 5:
                    AudioDeviceCategory[] _arg05 = (AudioDeviceCategory[]) data.createTypedArray(AudioDeviceCategory.CREATOR);
                    data.enforceNoDataAvail();
                    initCachedAudioDeviceCategories(_arg05);
                    return true;
                case 6:
                    AudioDeviceCategory _arg06 = (AudioDeviceCategory) data.readTypedObject(AudioDeviceCategory.CREATOR);
                    data.enforceNoDataAvail();
                    setAudioDeviceCategory(_arg06);
                    return true;
                case 7:
                    float _result = getOutputRs2UpperBound();
                    reply.writeNoException();
                    reply.writeFloat(_result);
                    return true;
                case 8:
                    float _result2 = getCsd();
                    reply.writeNoException();
                    reply.writeFloat(_result2);
                    return true;
                case 9:
                    boolean _result3 = isSoundDoseHalSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 10:
                    boolean _arg07 = data.readBoolean();
                    data.enforceNoDataAvail();
                    forceUseFrameworkMel(_arg07);
                    return true;
                case 11:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    forceComputeCsdOnAllDevices(_arg08);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISoundDose {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISoundDose.DESCRIPTOR;
            }

            @Override // android.media.ISoundDose
            public void setOutputRs2UpperBound(float rs2Value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeFloat(rs2Value);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void resetCsd(float currentCsd, SoundDoseRecord[] records) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeFloat(currentCsd);
                    _data.writeTypedArray(records, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void updateAttenuation(float attenuationDB, int device) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeFloat(attenuationDB);
                    _data.writeInt(device);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void setCsdEnabled(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void initCachedAudioDeviceCategories(AudioDeviceCategory[] audioDevices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeTypedArray(audioDevices, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void setAudioDeviceCategory(AudioDeviceCategory audioDevice) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeTypedObject(audioDevice, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public float getOutputRs2UpperBound() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public float getCsd() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public boolean isSoundDoseHalSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void forceUseFrameworkMel(boolean useFrameworkMel) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeBoolean(useFrameworkMel);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.ISoundDose
            public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISoundDose.DESCRIPTOR);
                    _data.writeBoolean(computeCsdOnAllDevices);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }

    public static class AudioDeviceCategory implements Parcelable {
        public static final Parcelable.Creator<AudioDeviceCategory> CREATOR = new Parcelable.Creator<AudioDeviceCategory>() { // from class: android.media.ISoundDose.AudioDeviceCategory.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioDeviceCategory createFromParcel(Parcel _aidl_source) {
                AudioDeviceCategory _aidl_out = new AudioDeviceCategory();
                _aidl_out.readFromParcel(_aidl_source);
                return _aidl_out;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioDeviceCategory[] newArray(int _aidl_size) {
                return new AudioDeviceCategory[_aidl_size];
            }
        };
        public String address;
        public int internalAudioType = 0;
        public boolean csdCompatible = false;

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel _aidl_parcel, int _aidl_flag) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.writeInt(0);
            _aidl_parcel.writeString(this.address);
            _aidl_parcel.writeInt(this.internalAudioType);
            _aidl_parcel.writeBoolean(this.csdCompatible);
            int _aidl_end_pos = _aidl_parcel.dataPosition();
            _aidl_parcel.setDataPosition(_aidl_start_pos);
            _aidl_parcel.writeInt(_aidl_end_pos - _aidl_start_pos);
            _aidl_parcel.setDataPosition(_aidl_end_pos);
        }

        public final void readFromParcel(Parcel _aidl_parcel) {
            int _aidl_start_pos = _aidl_parcel.dataPosition();
            int _aidl_parcelable_size = _aidl_parcel.readInt();
            try {
                if (_aidl_parcelable_size < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.address = _aidl_parcel.readString();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                    return;
                }
                this.internalAudioType = _aidl_parcel.readInt();
                if (_aidl_parcel.dataPosition() - _aidl_start_pos >= _aidl_parcelable_size) {
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                } else {
                    this.csdCompatible = _aidl_parcel.readBoolean();
                    if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                }
            } catch (Throwable th) {
                if (_aidl_start_pos > Integer.MAX_VALUE - _aidl_parcelable_size) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                _aidl_parcel.setDataPosition(_aidl_start_pos + _aidl_parcelable_size);
                throw th;
            }
        }

        public String toString() {
            StringJoiner _aidl_sj = new StringJoiner(", ", "{", "}");
            _aidl_sj.add("address: " + Objects.toString(this.address));
            _aidl_sj.add("internalAudioType: " + this.internalAudioType);
            _aidl_sj.add("csdCompatible: " + this.csdCompatible);
            return "AudioDeviceCategory" + _aidl_sj.toString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
