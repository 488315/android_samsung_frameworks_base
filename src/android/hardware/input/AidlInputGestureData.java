package android.hardware.input;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class AidlInputGestureData implements Parcelable {
    public static final Parcelable.Creator<AidlInputGestureData> CREATOR = new Parcelable.Creator<AidlInputGestureData>() { // from class: android.hardware.input.AidlInputGestureData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AidlInputGestureData createFromParcel(Parcel parcel) {
            AidlInputGestureData aidlInputGestureData = new AidlInputGestureData();
            aidlInputGestureData.readFromParcel(parcel);
            return aidlInputGestureData;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AidlInputGestureData[] newArray(int i) {
            return new AidlInputGestureData[i];
        }
    };
    public String appLaunchCategory;
    public String appLaunchClassName;
    public String appLaunchPackageName;
    public String appLaunchRole;
    public int gestureType = 0;
    public Trigger trigger;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.trigger, i);
        parcel.writeInt(this.gestureType);
        parcel.writeString(this.appLaunchCategory);
        parcel.writeString(this.appLaunchRole);
        parcel.writeString(this.appLaunchPackageName);
        parcel.writeString(this.appLaunchClassName);
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
                this.trigger = (Trigger) parcel.readTypedObject(Trigger.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.gestureType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.appLaunchCategory = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.appLaunchRole = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.appLaunchPackageName = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.appLaunchClassName = parcel.readString();
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AidlInputGestureData)) {
            return false;
        }
        AidlInputGestureData aidlInputGestureData = (AidlInputGestureData) obj;
        return Objects.deepEquals(this.trigger, aidlInputGestureData.trigger) && Objects.deepEquals(Integer.valueOf(this.gestureType), Integer.valueOf(aidlInputGestureData.gestureType)) && Objects.deepEquals(this.appLaunchCategory, aidlInputGestureData.appLaunchCategory) && Objects.deepEquals(this.appLaunchRole, aidlInputGestureData.appLaunchRole) && Objects.deepEquals(this.appLaunchPackageName, aidlInputGestureData.appLaunchPackageName) && Objects.deepEquals(this.appLaunchClassName, aidlInputGestureData.appLaunchClassName);
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(this.trigger, Integer.valueOf(this.gestureType), this.appLaunchCategory, this.appLaunchRole, this.appLaunchPackageName, this.appLaunchClassName).toArray());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.trigger);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    public static class KeyTrigger implements Parcelable {
        public static final Parcelable.Creator<KeyTrigger> CREATOR = new Parcelable.Creator<KeyTrigger>() { // from class: android.hardware.input.AidlInputGestureData.KeyTrigger.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyTrigger createFromParcel(Parcel parcel) {
                KeyTrigger keyTrigger = new KeyTrigger();
                keyTrigger.readFromParcel(parcel);
                return keyTrigger;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public KeyTrigger[] newArray(int i) {
                return new KeyTrigger[i];
            }
        };
        public int keycode = 0;
        public int modifierState = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.keycode);
            parcel.writeInt(this.modifierState);
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
                    this.keycode = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.modifierState = parcel.readInt();
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

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof KeyTrigger)) {
                return false;
            }
            KeyTrigger keyTrigger = (KeyTrigger) obj;
            return Objects.deepEquals(Integer.valueOf(this.keycode), Integer.valueOf(keyTrigger.keycode)) && Objects.deepEquals(Integer.valueOf(this.modifierState), Integer.valueOf(keyTrigger.modifierState));
        }

        public int hashCode() {
            return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.keycode), Integer.valueOf(this.modifierState)).toArray());
        }
    }

    public static class TouchpadGestureTrigger implements Parcelable {
        public static final Parcelable.Creator<TouchpadGestureTrigger> CREATOR = new Parcelable.Creator<TouchpadGestureTrigger>() { // from class: android.hardware.input.AidlInputGestureData.TouchpadGestureTrigger.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TouchpadGestureTrigger createFromParcel(Parcel parcel) {
                TouchpadGestureTrigger touchpadGestureTrigger = new TouchpadGestureTrigger();
                touchpadGestureTrigger.readFromParcel(parcel);
                return touchpadGestureTrigger;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TouchpadGestureTrigger[] newArray(int i) {
                return new TouchpadGestureTrigger[i];
            }
        };
        public int gestureType = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.gestureType);
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
                    this.gestureType = parcel.readInt();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && (obj instanceof TouchpadGestureTrigger) && Objects.deepEquals(Integer.valueOf(this.gestureType), Integer.valueOf(((TouchpadGestureTrigger) obj).gestureType));
        }

        public int hashCode() {
            return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.gestureType)).toArray());
        }
    }

    public static final class Trigger implements Parcelable {
        public static final Parcelable.Creator<Trigger> CREATOR = new Parcelable.Creator<Trigger>() { // from class: android.hardware.input.AidlInputGestureData.Trigger.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Trigger createFromParcel(Parcel parcel) {
                return new Trigger(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Trigger[] newArray(int i) {
                return new Trigger[i];
            }
        };
        public static final int key = 0;
        public static final int touchpadGesture = 1;
        private int _tag;
        private Object _value;

        public @interface Tag {
            public static final int key = 0;
            public static final int touchpadGesture = 1;
        }

        public Trigger() {
            this._tag = 0;
            this._value = null;
        }

        private Trigger(Parcel parcel) {
            readFromParcel(parcel);
        }

        private Trigger(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public int getTag() {
            return this._tag;
        }

        public static Trigger key(KeyTrigger keyTrigger) {
            return new Trigger(0, keyTrigger);
        }

        public KeyTrigger getKey() {
            _assertTag(0);
            return (KeyTrigger) this._value;
        }

        public void setKey(KeyTrigger keyTrigger) {
            _set(0, keyTrigger);
        }

        public static Trigger touchpadGesture(TouchpadGestureTrigger touchpadGestureTrigger) {
            return new Trigger(1, touchpadGestureTrigger);
        }

        public TouchpadGestureTrigger getTouchpadGesture() {
            _assertTag(1);
            return (TouchpadGestureTrigger) this._value;
        }

        public void setTouchpadGesture(TouchpadGestureTrigger touchpadGestureTrigger) {
            _set(1, touchpadGestureTrigger);
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this._tag);
            int i2 = this._tag;
            if (i2 == 0) {
                parcel.writeTypedObject(getKey(), i);
            } else {
                if (i2 != 1) {
                    return;
                }
                parcel.writeTypedObject(getTouchpadGesture(), i);
            }
        }

        public void readFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            if (i == 0) {
                _set(i, (KeyTrigger) parcel.readTypedObject(KeyTrigger.CREATOR));
            } else if (i == 1) {
                _set(i, (TouchpadGestureTrigger) parcel.readTypedObject(TouchpadGestureTrigger.CREATOR));
            } else {
                throw new IllegalArgumentException("union: unknown tag: " + i);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            int tag = getTag();
            if (tag == 0) {
                return describeContents(getKey());
            }
            if (tag != 1) {
                return 0;
            }
            return describeContents(getTouchpadGesture());
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof Trigger)) {
                return false;
            }
            Trigger trigger = (Trigger) obj;
            return this._tag == trigger._tag && Objects.deepEquals(this._value, trigger._value);
        }

        public int hashCode() {
            return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this._tag), this._value).toArray());
        }

        private void _assertTag(int i) {
            if (getTag() == i) {
                return;
            }
            throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }

        private String _tagString(int i) {
            if (i == 0) {
                return "key";
            }
            if (i == 1) {
                return "touchpadGesture";
            }
            throw new IllegalStateException("unknown field: " + i);
        }

        private void _set(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }
    }
}
