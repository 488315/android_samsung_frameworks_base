package android.service.settings.preferences;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class SettingsPreferenceValue implements Parcelable {
    public static final Parcelable.Creator<SettingsPreferenceValue> CREATOR = new Parcelable.Creator<SettingsPreferenceValue>() { // from class: android.service.settings.preferences.SettingsPreferenceValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SettingsPreferenceValue createFromParcel(Parcel parcel) {
            return new SettingsPreferenceValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SettingsPreferenceValue[] newArray(int i) {
            return new SettingsPreferenceValue[i];
        }
    };
    private static final int MAX_TYPE_VALUE = 4;
    public static final int TYPE_BOOLEAN = 0;
    public static final int TYPE_DOUBLE = 2;
    public static final int TYPE_INT = 4;
    public static final int TYPE_LONG = 1;
    public static final int TYPE_STRING = 3;
    private final int mType;
    private final Object mValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getType() {
        return this.mType;
    }

    public boolean getBooleanValue() {
        return ((Boolean) this.mValue).booleanValue();
    }

    public int getIntValue() {
        return ((Integer) this.mValue).intValue();
    }

    public long getLongValue() {
        return ((Long) this.mValue).longValue();
    }

    public double getDoubleValue() {
        return ((Double) this.mValue).doubleValue();
    }

    public String getStringValue() {
        return (String) this.mValue;
    }

    private SettingsPreferenceValue(Builder builder) {
        this.mType = builder.mType;
        this.mValue = builder.mValue;
    }

    private SettingsPreferenceValue(Parcel parcel) {
        int i = parcel.readInt();
        this.mType = i;
        if (i == 0) {
            this.mValue = Boolean.valueOf(parcel.readBoolean());
            return;
        }
        if (i == 1) {
            this.mValue = Long.valueOf(parcel.readLong());
            return;
        }
        if (i == 2) {
            this.mValue = Double.valueOf(parcel.readDouble());
            return;
        }
        if (i == 3) {
            this.mValue = parcel.readString();
        } else if (i == 4) {
            this.mValue = Integer.valueOf(parcel.readInt());
        } else {
            throw new IllegalStateException("Unknown type: " + i);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        int i2 = this.mType;
        if (i2 == 0) {
            parcel.writeBoolean(getBooleanValue());
            return;
        }
        if (i2 == 1) {
            parcel.writeLong(getLongValue());
            return;
        }
        if (i2 == 2) {
            parcel.writeDouble(getDoubleValue());
        } else if (i2 == 3) {
            parcel.writeString(getStringValue());
        } else if (i2 == 4) {
            parcel.writeInt(getIntValue());
        }
    }

    public static final class Builder {
        private final int mType;
        private Object mValue;

        public Builder(int i) {
            if (i < 0 || i > 4) {
                throw new IllegalArgumentException("Unknown type: " + i);
            }
            this.mType = i;
        }

        public Builder setBooleanValue(boolean z) {
            checkType(0);
            this.mValue = Boolean.valueOf(z);
            return this;
        }

        public Builder setIntValue(int i) {
            checkType(4);
            this.mValue = Integer.valueOf(i);
            return this;
        }

        public Builder setLongValue(long j) {
            checkType(1);
            this.mValue = Long.valueOf(j);
            return this;
        }

        public Builder setDoubleValue(double d) {
            checkType(2);
            this.mValue = Double.valueOf(d);
            return this;
        }

        public Builder setStringValue(String str) {
            checkType(3);
            this.mValue = str;
            return this;
        }

        private void checkType(int i) {
            if (this.mType == i) {
                return;
            }
            throw new IllegalArgumentException("Type is: " + this.mType);
        }

        public SettingsPreferenceValue build() {
            return new SettingsPreferenceValue(this);
        }
    }
}
