package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public class StatsBootstrapAtomValue implements Parcelable {
    public static final Parcelable.Creator<StatsBootstrapAtomValue> CREATOR = new Parcelable.Creator<StatsBootstrapAtomValue>() { // from class: android.os.StatsBootstrapAtomValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatsBootstrapAtomValue createFromParcel(Parcel parcel) {
            StatsBootstrapAtomValue statsBootstrapAtomValue = new StatsBootstrapAtomValue();
            statsBootstrapAtomValue.readFromParcel(parcel);
            return statsBootstrapAtomValue;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StatsBootstrapAtomValue[] newArray(int i) {
            return new StatsBootstrapAtomValue[i];
        }
    };
    public Annotation[] annotations;
    public Primitive value;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.value, i);
        parcel.writeTypedArray(this.annotations, i);
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
                this.value = (Primitive) parcel.readTypedObject(Primitive.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.annotations = (Annotation[]) parcel.createTypedArray(Annotation.CREATOR);
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
        return describeContents(this.annotations) | describeContents(this.value);
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

    public static final class Primitive implements Parcelable {
        public static final Parcelable.Creator<Primitive> CREATOR = new Parcelable.Creator<Primitive>() { // from class: android.os.StatsBootstrapAtomValue.Primitive.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Primitive createFromParcel(Parcel parcel) {
                return new Primitive(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Primitive[] newArray(int i) {
                return new Primitive[i];
            }
        };
        public static final int boolValue = 0;
        public static final int bytesValue = 5;
        public static final int floatValue = 3;
        public static final int intValue = 1;
        public static final int longValue = 2;
        public static final int stringArrayValue = 6;
        public static final int stringValue = 4;
        private int _tag;
        private Object _value;

        public @interface Tag {
            public static final int boolValue = 0;
            public static final int bytesValue = 5;
            public static final int floatValue = 3;
            public static final int intValue = 1;
            public static final int longValue = 2;
            public static final int stringArrayValue = 6;
            public static final int stringValue = 4;
        }

        public Primitive() {
            this._tag = 0;
            this._value = false;
        }

        private Primitive(Parcel parcel) {
            readFromParcel(parcel);
        }

        private Primitive(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }

        public int getTag() {
            return this._tag;
        }

        public static Primitive boolValue(boolean z) {
            return new Primitive(0, Boolean.valueOf(z));
        }

        public boolean getBoolValue() {
            _assertTag(0);
            return ((Boolean) this._value).booleanValue();
        }

        public void setBoolValue(boolean z) {
            _set(0, Boolean.valueOf(z));
        }

        public static Primitive intValue(int i) {
            return new Primitive(1, Integer.valueOf(i));
        }

        public int getIntValue() {
            _assertTag(1);
            return ((Integer) this._value).intValue();
        }

        public void setIntValue(int i) {
            _set(1, Integer.valueOf(i));
        }

        public static Primitive longValue(long j) {
            return new Primitive(2, Long.valueOf(j));
        }

        public long getLongValue() {
            _assertTag(2);
            return ((Long) this._value).longValue();
        }

        public void setLongValue(long j) {
            _set(2, Long.valueOf(j));
        }

        public static Primitive floatValue(float f) {
            return new Primitive(3, Float.valueOf(f));
        }

        public float getFloatValue() {
            _assertTag(3);
            return ((Float) this._value).floatValue();
        }

        public void setFloatValue(float f) {
            _set(3, Float.valueOf(f));
        }

        public static Primitive stringValue(String str) {
            return new Primitive(4, str);
        }

        public String getStringValue() {
            _assertTag(4);
            return (String) this._value;
        }

        public void setStringValue(String str) {
            _set(4, str);
        }

        public static Primitive bytesValue(byte[] bArr) {
            return new Primitive(5, bArr);
        }

        public byte[] getBytesValue() {
            _assertTag(5);
            return (byte[]) this._value;
        }

        public void setBytesValue(byte[] bArr) {
            _set(5, bArr);
        }

        public static Primitive stringArrayValue(String[] strArr) {
            return new Primitive(6, strArr);
        }

        public String[] getStringArrayValue() {
            _assertTag(6);
            return (String[]) this._value;
        }

        public void setStringArrayValue(String[] strArr) {
            _set(6, strArr);
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this._tag);
            switch (this._tag) {
                case 0:
                    parcel.writeBoolean(getBoolValue());
                    break;
                case 1:
                    parcel.writeInt(getIntValue());
                    break;
                case 2:
                    parcel.writeLong(getLongValue());
                    break;
                case 3:
                    parcel.writeFloat(getFloatValue());
                    break;
                case 4:
                    parcel.writeString(getStringValue());
                    break;
                case 5:
                    parcel.writeByteArray(getBytesValue());
                    break;
                case 6:
                    parcel.writeStringArray(getStringArrayValue());
                    break;
            }
        }

        public void readFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            switch (i) {
                case 0:
                    _set(i, Boolean.valueOf(parcel.readBoolean()));
                    return;
                case 1:
                    _set(i, Integer.valueOf(parcel.readInt()));
                    return;
                case 2:
                    _set(i, Long.valueOf(parcel.readLong()));
                    return;
                case 3:
                    _set(i, Float.valueOf(parcel.readFloat()));
                    return;
                case 4:
                    _set(i, parcel.readString());
                    return;
                case 5:
                    _set(i, parcel.createByteArray());
                    return;
                case 6:
                    _set(i, parcel.createStringArray());
                    return;
                default:
                    throw new IllegalArgumentException("union: unknown tag: " + i);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            getTag();
            return 0;
        }

        private void _assertTag(int i) {
            if (getTag() == i) {
                return;
            }
            throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
        }

        private String _tagString(int i) {
            switch (i) {
                case 0:
                    return "boolValue";
                case 1:
                    return "intValue";
                case 2:
                    return "longValue";
                case 3:
                    return "floatValue";
                case 4:
                    return "stringValue";
                case 5:
                    return "bytesValue";
                case 6:
                    return "stringArrayValue";
                default:
                    throw new IllegalStateException("unknown field: " + i);
            }
        }

        private void _set(int i, Object obj) {
            this._tag = i;
            this._value = obj;
        }
    }

    public static class Annotation implements Parcelable {
        public static final Parcelable.Creator<Annotation> CREATOR = new Parcelable.Creator<Annotation>() { // from class: android.os.StatsBootstrapAtomValue.Annotation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Annotation createFromParcel(Parcel parcel) {
                Annotation annotation = new Annotation();
                annotation.readFromParcel(parcel);
                return annotation;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Annotation[] newArray(int i) {
                return new Annotation[i];
            }
        };
        public byte id;
        public Primitive value;

        public @interface Id {
            public static final byte IS_UID = 1;
            public static final byte NONE = 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeByte(this.id);
            parcel.writeTypedObject(this.value, i);
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
                    this.id = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.value = (Primitive) parcel.readTypedObject(Primitive.CREATOR);
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
            return describeContents(this.value);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }

        public static final class Primitive implements Parcelable {
            public static final Parcelable.Creator<Primitive> CREATOR = new Parcelable.Creator<Primitive>() { // from class: android.os.StatsBootstrapAtomValue.Annotation.Primitive.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Primitive createFromParcel(Parcel parcel) {
                    return new Primitive(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public Primitive[] newArray(int i) {
                    return new Primitive[i];
                }
            };
            public static final int boolValue = 0;
            public static final int intValue = 1;
            private int _tag;
            private Object _value;

            public @interface Tag {
                public static final int boolValue = 0;
                public static final int intValue = 1;
            }

            public Primitive() {
                this._tag = 0;
                this._value = false;
            }

            private Primitive(Parcel parcel) {
                readFromParcel(parcel);
            }

            private Primitive(int i, Object obj) {
                this._tag = i;
                this._value = obj;
            }

            public int getTag() {
                return this._tag;
            }

            public static Primitive boolValue(boolean z) {
                return new Primitive(0, Boolean.valueOf(z));
            }

            public boolean getBoolValue() {
                _assertTag(0);
                return ((Boolean) this._value).booleanValue();
            }

            public void setBoolValue(boolean z) {
                _set(0, Boolean.valueOf(z));
            }

            public static Primitive intValue(int i) {
                return new Primitive(1, Integer.valueOf(i));
            }

            public int getIntValue() {
                _assertTag(1);
                return ((Integer) this._value).intValue();
            }

            public void setIntValue(int i) {
                _set(1, Integer.valueOf(i));
            }

            @Override // android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this._tag);
                int i2 = this._tag;
                if (i2 == 0) {
                    parcel.writeBoolean(getBoolValue());
                } else {
                    if (i2 != 1) {
                        return;
                    }
                    parcel.writeInt(getIntValue());
                }
            }

            public void readFromParcel(Parcel parcel) {
                int i = parcel.readInt();
                if (i == 0) {
                    _set(i, Boolean.valueOf(parcel.readBoolean()));
                } else if (i == 1) {
                    _set(i, Integer.valueOf(parcel.readInt()));
                } else {
                    throw new IllegalArgumentException("union: unknown tag: " + i);
                }
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                getTag();
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
                    return "boolValue";
                }
                if (i == 1) {
                    return "intValue";
                }
                throw new IllegalStateException("unknown field: " + i);
            }

            private void _set(int i, Object obj) {
                this._tag = i;
                this._value = obj;
            }
        }
    }
}
