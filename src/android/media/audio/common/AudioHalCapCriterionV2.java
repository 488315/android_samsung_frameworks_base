package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class AudioHalCapCriterionV2 implements Parcelable {
    public static final Parcelable.Creator<AudioHalCapCriterionV2> CREATOR = new Parcelable.Creator<AudioHalCapCriterionV2>() { // from class: android.media.audio.common.AudioHalCapCriterionV2.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapCriterionV2 createFromParcel(Parcel parcel) {
            return new AudioHalCapCriterionV2(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioHalCapCriterionV2[] newArray(int i) {
            return new AudioHalCapCriterionV2[i];
        }
    };
    public static final int availableInputDevices = 0;
    public static final int availableInputDevicesAddresses = 2;
    public static final int availableOutputDevices = 1;
    public static final int availableOutputDevicesAddresses = 3;
    public static final int forceConfigForUse = 5;
    public static final int telephonyMode = 4;
    private int _tag;
    private Object _value;

    public @interface LogicalDisjunction {
        public static final byte EXCLUSIVE = 0;
        public static final byte INCLUSIVE = 1;
    }

    public @interface Tag {
        public static final int availableInputDevices = 0;
        public static final int availableInputDevicesAddresses = 2;
        public static final int availableOutputDevices = 1;
        public static final int availableOutputDevicesAddresses = 3;
        public static final int forceConfigForUse = 5;
        public static final int telephonyMode = 4;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public AudioHalCapCriterionV2() {
        this._tag = 0;
        this._value = null;
    }

    private AudioHalCapCriterionV2(Parcel parcel) {
        readFromParcel(parcel);
    }

    private AudioHalCapCriterionV2(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static AudioHalCapCriterionV2 availableInputDevices(AvailableDevices availableDevices) {
        return new AudioHalCapCriterionV2(0, availableDevices);
    }

    public AvailableDevices getAvailableInputDevices() {
        _assertTag(0);
        return (AvailableDevices) this._value;
    }

    public void setAvailableInputDevices(AvailableDevices availableDevices) {
        _set(0, availableDevices);
    }

    public static AudioHalCapCriterionV2 availableOutputDevices(AvailableDevices availableDevices) {
        return new AudioHalCapCriterionV2(1, availableDevices);
    }

    public AvailableDevices getAvailableOutputDevices() {
        _assertTag(1);
        return (AvailableDevices) this._value;
    }

    public void setAvailableOutputDevices(AvailableDevices availableDevices) {
        _set(1, availableDevices);
    }

    public static AudioHalCapCriterionV2 availableInputDevicesAddresses(AvailableDevicesAddresses availableDevicesAddresses) {
        return new AudioHalCapCriterionV2(2, availableDevicesAddresses);
    }

    public AvailableDevicesAddresses getAvailableInputDevicesAddresses() {
        _assertTag(2);
        return (AvailableDevicesAddresses) this._value;
    }

    public void setAvailableInputDevicesAddresses(AvailableDevicesAddresses availableDevicesAddresses) {
        _set(2, availableDevicesAddresses);
    }

    public static AudioHalCapCriterionV2 availableOutputDevicesAddresses(AvailableDevicesAddresses availableDevicesAddresses) {
        return new AudioHalCapCriterionV2(3, availableDevicesAddresses);
    }

    public AvailableDevicesAddresses getAvailableOutputDevicesAddresses() {
        _assertTag(3);
        return (AvailableDevicesAddresses) this._value;
    }

    public void setAvailableOutputDevicesAddresses(AvailableDevicesAddresses availableDevicesAddresses) {
        _set(3, availableDevicesAddresses);
    }

    public static AudioHalCapCriterionV2 telephonyMode(TelephonyMode telephonyMode2) {
        return new AudioHalCapCriterionV2(4, telephonyMode2);
    }

    public TelephonyMode getTelephonyMode() {
        _assertTag(4);
        return (TelephonyMode) this._value;
    }

    public void setTelephonyMode(TelephonyMode telephonyMode2) {
        _set(4, telephonyMode2);
    }

    public static AudioHalCapCriterionV2 forceConfigForUse(ForceConfigForUse forceConfigForUse2) {
        return new AudioHalCapCriterionV2(5, forceConfigForUse2);
    }

    public ForceConfigForUse getForceConfigForUse() {
        _assertTag(5);
        return (ForceConfigForUse) this._value;
    }

    public void setForceConfigForUse(ForceConfigForUse forceConfigForUse2) {
        _set(5, forceConfigForUse2);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getAvailableInputDevices(), i);
            return;
        }
        if (i2 == 1) {
            parcel.writeTypedObject(getAvailableOutputDevices(), i);
            return;
        }
        if (i2 == 2) {
            parcel.writeTypedObject(getAvailableInputDevicesAddresses(), i);
            return;
        }
        if (i2 == 3) {
            parcel.writeTypedObject(getAvailableOutputDevicesAddresses(), i);
        } else if (i2 == 4) {
            parcel.writeTypedObject(getTelephonyMode(), i);
        } else {
            if (i2 != 5) {
                return;
            }
            parcel.writeTypedObject(getForceConfigForUse(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, (AvailableDevices) parcel.readTypedObject(AvailableDevices.CREATOR));
            return;
        }
        if (i == 1) {
            _set(i, (AvailableDevices) parcel.readTypedObject(AvailableDevices.CREATOR));
            return;
        }
        if (i == 2) {
            _set(i, (AvailableDevicesAddresses) parcel.readTypedObject(AvailableDevicesAddresses.CREATOR));
            return;
        }
        if (i == 3) {
            _set(i, (AvailableDevicesAddresses) parcel.readTypedObject(AvailableDevicesAddresses.CREATOR));
            return;
        }
        if (i == 4) {
            _set(i, (TelephonyMode) parcel.readTypedObject(TelephonyMode.CREATOR));
        } else if (i == 5) {
            _set(i, (ForceConfigForUse) parcel.readTypedObject(ForceConfigForUse.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getAvailableInputDevices());
        }
        if (tag == 1) {
            return describeContents(getAvailableOutputDevices());
        }
        if (tag == 2) {
            return describeContents(getAvailableInputDevicesAddresses());
        }
        if (tag == 3) {
            return describeContents(getAvailableOutputDevicesAddresses());
        }
        if (tag == 4) {
            return describeContents(getTelephonyMode());
        }
        if (tag != 5) {
            return 0;
        }
        return describeContents(getForceConfigForUse());
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
            return "availableInputDevices";
        }
        if (i == 1) {
            return "availableOutputDevices";
        }
        if (i == 2) {
            return "availableInputDevicesAddresses";
        }
        if (i == 3) {
            return "availableOutputDevicesAddresses";
        }
        if (i == 4) {
            return "telephonyMode";
        }
        if (i == 5) {
            return "forceConfigForUse";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public static class ForceConfigForUse implements Parcelable {
        public static final Parcelable.Creator<ForceConfigForUse> CREATOR = new Parcelable.Creator<ForceConfigForUse>() { // from class: android.media.audio.common.AudioHalCapCriterionV2.ForceConfigForUse.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ForceConfigForUse createFromParcel(Parcel parcel) {
                ForceConfigForUse forceConfigForUse = new ForceConfigForUse();
                forceConfigForUse.readFromParcel(parcel);
                return forceConfigForUse;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ForceConfigForUse[] newArray(int i) {
                return new ForceConfigForUse[i];
            }
        };
        public AudioPolicyForceUse defaultValue;
        public byte logic = 0;
        public AudioPolicyForceUse[] values;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedArray(this.values, i);
            parcel.writeTypedObject(this.defaultValue, i);
            parcel.writeByte(this.logic);
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
                    this.values = (AudioPolicyForceUse[]) parcel.createTypedArray(AudioPolicyForceUse.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.defaultValue = (AudioPolicyForceUse) parcel.readTypedObject(AudioPolicyForceUse.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.logic = parcel.readByte();
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
            return describeContents(this.defaultValue) | describeContents(this.values);
        }

        private int describeContents(Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Object[]) {
                int iDescribeContents = 0;
                for (Object obj2 : (Object[]) obj) {
                    iDescribeContents |= describeContents(obj2);
                }
                return iDescribeContents;
            }
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public static class TelephonyMode implements Parcelable {
        public static final Parcelable.Creator<TelephonyMode> CREATOR = new Parcelable.Creator<TelephonyMode>() { // from class: android.media.audio.common.AudioHalCapCriterionV2.TelephonyMode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TelephonyMode createFromParcel(Parcel parcel) {
                TelephonyMode telephonyMode = new TelephonyMode();
                telephonyMode.readFromParcel(parcel);
                return telephonyMode;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TelephonyMode[] newArray(int i) {
                return new TelephonyMode[i];
            }
        };
        public int defaultValue = 0;
        public byte logic = 0;
        public int[] values;

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
            parcel.writeIntArray(this.values);
            parcel.writeInt(this.defaultValue);
            parcel.writeByte(this.logic);
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
                    this.values = parcel.createIntArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.defaultValue = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.logic = parcel.readByte();
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
    }

    public static class AvailableDevices implements Parcelable {
        public static final Parcelable.Creator<AvailableDevices> CREATOR = new Parcelable.Creator<AvailableDevices>() { // from class: android.media.audio.common.AudioHalCapCriterionV2.AvailableDevices.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AvailableDevices createFromParcel(Parcel parcel) {
                AvailableDevices availableDevices = new AvailableDevices();
                availableDevices.readFromParcel(parcel);
                return availableDevices;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AvailableDevices[] newArray(int i) {
                return new AvailableDevices[i];
            }
        };
        public byte logic = 1;
        public AudioDeviceDescription[] values;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedArray(this.values, i);
            parcel.writeByte(this.logic);
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
                    this.values = (AudioDeviceDescription[]) parcel.createTypedArray(AudioDeviceDescription.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.logic = parcel.readByte();
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
            return describeContents(this.values);
        }

        private int describeContents(Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Object[]) {
                int iDescribeContents = 0;
                for (Object obj2 : (Object[]) obj) {
                    iDescribeContents |= describeContents(obj2);
                }
                return iDescribeContents;
            }
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public static class AvailableDevicesAddresses implements Parcelable {
        public static final Parcelable.Creator<AvailableDevicesAddresses> CREATOR = new Parcelable.Creator<AvailableDevicesAddresses>() { // from class: android.media.audio.common.AudioHalCapCriterionV2.AvailableDevicesAddresses.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AvailableDevicesAddresses createFromParcel(Parcel parcel) {
                AvailableDevicesAddresses availableDevicesAddresses = new AvailableDevicesAddresses();
                availableDevicesAddresses.readFromParcel(parcel);
                return availableDevicesAddresses;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AvailableDevicesAddresses[] newArray(int i) {
                return new AvailableDevicesAddresses[i];
            }
        };
        public byte logic = 1;
        public AudioDeviceAddress[] values;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeTypedArray(this.values, i);
            parcel.writeByte(this.logic);
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
                    this.values = (AudioDeviceAddress[]) parcel.createTypedArray(AudioDeviceAddress.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.logic = parcel.readByte();
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
            return describeContents(this.values);
        }

        private int describeContents(Object obj) {
            if (obj == null) {
                return 0;
            }
            if (obj instanceof Object[]) {
                int iDescribeContents = 0;
                for (Object obj2 : (Object[]) obj) {
                    iDescribeContents |= describeContents(obj2);
                }
                return iDescribeContents;
            }
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }
}
