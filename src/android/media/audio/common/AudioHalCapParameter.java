package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class AudioHalCapParameter implements Parcelable {
    public static final Parcelable.Creator<AudioHalCapParameter> CREATOR = new Parcelable.Creator<AudioHalCapParameter>() { // from class: android.media.audio.common.AudioHalCapParameter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapParameter createFromParcel(Parcel parcel) {
            return new AudioHalCapParameter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapParameter[] newArray(int i) {
            return new AudioHalCapParameter[i];
        }
    };
    public static final int selectedInputSourceDevice = 1;
    public static final int selectedStrategyDevice = 0;
    public static final int strategyDeviceAddress = 2;
    public static final int streamVolumeProfile = 3;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int selectedInputSourceDevice = 1;
        public static final int selectedStrategyDevice = 0;
        public static final int strategyDeviceAddress = 2;
        public static final int streamVolumeProfile = 3;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioHalCapParameter() {
        this._tag = 0;
        this._value = null;
    }

    private AudioHalCapParameter(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioHalCapParameter(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioHalCapParameter selectedStrategyDevice(StrategyDevice strategyDevice) {
        return new AudioHalCapParameter(0, strategyDevice);
    }

    public StrategyDevice getSelectedStrategyDevice() {
        _assertTag(0);
        return (StrategyDevice) this._value;
    }

    public void setSelectedStrategyDevice(StrategyDevice strategyDevice) {
        _set(0, strategyDevice);
    }

    public static AudioHalCapParameter selectedInputSourceDevice(InputSourceDevice inputSourceDevice) {
        return new AudioHalCapParameter(1, inputSourceDevice);
    }

    public InputSourceDevice getSelectedInputSourceDevice() {
        _assertTag(1);
        return (InputSourceDevice) this._value;
    }

    public void setSelectedInputSourceDevice(InputSourceDevice inputSourceDevice) {
        _set(1, inputSourceDevice);
    }

    public static AudioHalCapParameter strategyDeviceAddress(StrategyDeviceAddress strategyDeviceAddress2) {
        return new AudioHalCapParameter(2, strategyDeviceAddress2);
    }

    public StrategyDeviceAddress getStrategyDeviceAddress() {
        _assertTag(2);
        return (StrategyDeviceAddress) this._value;
    }

    public void setStrategyDeviceAddress(StrategyDeviceAddress strategyDeviceAddress2) {
        _set(2, strategyDeviceAddress2);
    }

    public static AudioHalCapParameter streamVolumeProfile(StreamVolumeProfile streamVolumeProfile2) {
        return new AudioHalCapParameter(3, streamVolumeProfile2);
    }

    public StreamVolumeProfile getStreamVolumeProfile() {
        _assertTag(3);
        return (StreamVolumeProfile) this._value;
    }

    public void setStreamVolumeProfile(StreamVolumeProfile streamVolumeProfile2) {
        _set(3, streamVolumeProfile2);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getSelectedStrategyDevice(), i);
            return;
        }
        if (i2 == 1) {
            parcel.writeTypedObject(getSelectedInputSourceDevice(), i);
        } else if (i2 == 2) {
            parcel.writeTypedObject(getStrategyDeviceAddress(), i);
        } else {
            if (i2 != 3) {
                return;
            }
            parcel.writeTypedObject(getStreamVolumeProfile(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, (StrategyDevice) parcel.readTypedObject(StrategyDevice.CREATOR));
            return;
        }
        if (i == 1) {
            _set(i, (InputSourceDevice) parcel.readTypedObject(InputSourceDevice.CREATOR));
            return;
        }
        if (i == 2) {
            _set(i, (StrategyDeviceAddress) parcel.readTypedObject(StrategyDeviceAddress.CREATOR));
        } else if (i == 3) {
            _set(i, (StreamVolumeProfile) parcel.readTypedObject(StreamVolumeProfile.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getSelectedStrategyDevice());
        }
        if (tag == 1) {
            return describeContents(getSelectedInputSourceDevice());
        }
        if (tag == 2) {
            return describeContents(getStrategyDeviceAddress());
        }
        if (tag != 3) {
            return 0;
        }
        return describeContents(getStreamVolumeProfile());
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "selectedStrategyDevice";
        }
        if (i == 1) {
            return "selectedInputSourceDevice";
        }
        if (i == 2) {
            return "strategyDeviceAddress";
        }
        if (i == 3) {
            return "streamVolumeProfile";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public static class StrategyDevice implements Parcelable {
        public static final Parcelable.Creator<StrategyDevice> CREATOR = new Parcelable.Creator<StrategyDevice>() { // from class: android.media.audio.common.AudioHalCapParameter.StrategyDevice.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StrategyDevice createFromParcel(Parcel parcel) {
                StrategyDevice strategyDevice = new StrategyDevice();
                strategyDevice.readFromParcel(parcel);
                return strategyDevice;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StrategyDevice[] newArray(int i) {
                return new StrategyDevice[i];
            }
        };
        public AudioDeviceDescription device;
        public int id = -1;
        public boolean isSelected = false;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.device, i);
            parcel.writeInt(this.id);
            parcel.writeBoolean(this.isSelected);
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
                    this.device = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.id = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isSelected = parcel.readBoolean();
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.device);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public static class InputSourceDevice implements Parcelable {
        public static final Parcelable.Creator<InputSourceDevice> CREATOR = new Parcelable.Creator<InputSourceDevice>() { // from class: android.media.audio.common.AudioHalCapParameter.InputSourceDevice.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InputSourceDevice createFromParcel(Parcel parcel) {
                InputSourceDevice inputSourceDevice = new InputSourceDevice();
                inputSourceDevice.readFromParcel(parcel);
                return inputSourceDevice;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InputSourceDevice[] newArray(int i) {
                return new InputSourceDevice[i];
            }
        };
        public AudioDeviceDescription device;
        public int inputSource = 0;
        public boolean isSelected = false;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.device, i);
            parcel.writeInt(this.inputSource);
            parcel.writeBoolean(this.isSelected);
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
                    this.device = (AudioDeviceDescription) parcel.readTypedObject(AudioDeviceDescription.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.inputSource = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isSelected = parcel.readBoolean();
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.device);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public static class StrategyDeviceAddress implements Parcelable {
        public static final Parcelable.Creator<StrategyDeviceAddress> CREATOR = new Parcelable.Creator<StrategyDeviceAddress>() { // from class: android.media.audio.common.AudioHalCapParameter.StrategyDeviceAddress.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StrategyDeviceAddress createFromParcel(Parcel parcel) {
                StrategyDeviceAddress strategyDeviceAddress = new StrategyDeviceAddress();
                strategyDeviceAddress.readFromParcel(parcel);
                return strategyDeviceAddress;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StrategyDeviceAddress[] newArray(int i) {
                return new StrategyDeviceAddress[i];
            }
        };
        public AudioDeviceAddress deviceAddress;
        public int id = -1;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedObject(this.deviceAddress, i);
            parcel.writeInt(this.id);
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
                    this.deviceAddress = (AudioDeviceAddress) parcel.readTypedObject(AudioDeviceAddress.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.id = parcel.readInt();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
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

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.deviceAddress);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public static class StreamVolumeProfile implements Parcelable {
        public static final Parcelable.Creator<StreamVolumeProfile> CREATOR = new Parcelable.Creator<StreamVolumeProfile>() { // from class: android.media.audio.common.AudioHalCapParameter.StreamVolumeProfile.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StreamVolumeProfile createFromParcel(Parcel parcel) {
                StreamVolumeProfile streamVolumeProfile = new StreamVolumeProfile();
                streamVolumeProfile.readFromParcel(parcel);
                return streamVolumeProfile;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public StreamVolumeProfile[] newArray(int i) {
                return new StreamVolumeProfile[i];
            }
        };
        public int stream = -2;
        public int profile = -2;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.stream);
            parcel.writeInt(this.profile);
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
                    this.stream = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.profile = parcel.readInt();
                        if (iDataPosition > Integer.MAX_VALUE - i) {
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
    }
}
