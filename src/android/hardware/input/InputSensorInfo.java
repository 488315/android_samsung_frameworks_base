package android.hardware.input;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;

/* loaded from: classes2.dex */
public class InputSensorInfo implements Parcelable {
    public static final Parcelable.Creator<InputSensorInfo> CREATOR = new Parcelable.Creator<InputSensorInfo>() { // from class: android.hardware.input.InputSensorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputSensorInfo[] newArray(int i) {
            return new InputSensorInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InputSensorInfo createFromParcel(Parcel parcel) {
            return new InputSensorInfo(parcel);
        }
    };
    private int mFifoMaxEventCount;
    private int mFifoReservedEventCount;
    private int mFlags;
    private int mHandle;
    private int mId;
    private int mMaxDelay;
    private float mMaxRange;
    private int mMinDelay;
    private String mName;
    private float mPower;
    private String mRequiredPermission;
    private float mResolution;
    private String mStringType;
    private int mType;
    private String mVendor;
    private int mVersion;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InputSensorInfo(String str, String str2, int i, int i2, int i3, float f, float f2, float f3, int i4, int i5, int i6, String str3, String str4, int i7, int i8, int i9) {
        this.mName = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mVendor = str2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str2);
        this.mVersion = i;
        this.mHandle = i2;
        this.mType = i3;
        this.mMaxRange = f;
        this.mResolution = f2;
        this.mPower = f3;
        this.mMinDelay = i4;
        this.mFifoReservedEventCount = i5;
        this.mFifoMaxEventCount = i6;
        this.mStringType = str3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str3);
        this.mRequiredPermission = str4;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str4);
        this.mMaxDelay = i7;
        this.mFlags = i8;
        this.mId = i9;
    }

    public String getName() {
        return this.mName;
    }

    public String getVendor() {
        return this.mVendor;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public int getType() {
        return this.mType;
    }

    public float getMaxRange() {
        return this.mMaxRange;
    }

    public float getResolution() {
        return this.mResolution;
    }

    public float getPower() {
        return this.mPower;
    }

    public int getMinDelay() {
        return this.mMinDelay;
    }

    public int getFifoReservedEventCount() {
        return this.mFifoReservedEventCount;
    }

    public int getFifoMaxEventCount() {
        return this.mFifoMaxEventCount;
    }

    public String getStringType() {
        return this.mStringType;
    }

    public String getRequiredPermission() {
        return this.mRequiredPermission;
    }

    public int getMaxDelay() {
        return this.mMaxDelay;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getId() {
        return this.mId;
    }

    public String toString() {
        return "InputSensorInfo { name = " + this.mName + ", vendor = " + this.mVendor + ", version = " + this.mVersion + ", handle = " + this.mHandle + ", type = " + this.mType + ", maxRange = " + this.mMaxRange + ", resolution = " + this.mResolution + ", power = " + this.mPower + ", minDelay = " + this.mMinDelay + ", fifoReservedEventCount = " + this.mFifoReservedEventCount + ", fifoMaxEventCount = " + this.mFifoMaxEventCount + ", stringType = " + this.mStringType + ", requiredPermission = " + this.mRequiredPermission + ", maxDelay = " + this.mMaxDelay + ", flags = " + this.mFlags + ", id = " + this.mId + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mVendor);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mHandle);
        parcel.writeInt(this.mType);
        parcel.writeFloat(this.mMaxRange);
        parcel.writeFloat(this.mResolution);
        parcel.writeFloat(this.mPower);
        parcel.writeInt(this.mMinDelay);
        parcel.writeInt(this.mFifoReservedEventCount);
        parcel.writeInt(this.mFifoMaxEventCount);
        parcel.writeString(this.mStringType);
        parcel.writeString(this.mRequiredPermission);
        parcel.writeInt(this.mMaxDelay);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mId);
    }

    protected InputSensorInfo(Parcel parcel) {
        String string = parcel.readString();
        String string2 = parcel.readString();
        int i = parcel.readInt();
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        float f = parcel.readFloat();
        float f2 = parcel.readFloat();
        float f3 = parcel.readFloat();
        int i4 = parcel.readInt();
        int i5 = parcel.readInt();
        int i6 = parcel.readInt();
        String string3 = parcel.readString();
        String string4 = parcel.readString();
        int i7 = parcel.readInt();
        int i8 = parcel.readInt();
        int i9 = parcel.readInt();
        this.mName = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mVendor = string2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string2);
        this.mVersion = i;
        this.mHandle = i2;
        this.mType = i3;
        this.mMaxRange = f;
        this.mResolution = f2;
        this.mPower = f3;
        this.mMinDelay = i4;
        this.mFifoReservedEventCount = i5;
        this.mFifoMaxEventCount = i6;
        this.mStringType = string3;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string3);
        this.mRequiredPermission = string4;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string4);
        this.mMaxDelay = i7;
        this.mFlags = i8;
        this.mId = i9;
    }
}
