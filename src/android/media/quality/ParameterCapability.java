package android.media.quality;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class ParameterCapability implements Parcelable {
    public static final String CAPABILITY_DEFAULT = "default";
    public static final String CAPABILITY_ENUM = "enum";
    public static final String CAPABILITY_MAX = "max";
    public static final String CAPABILITY_MIN = "min";
    public static final Parcelable.Creator<ParameterCapability> CREATOR = new Parcelable.Creator<ParameterCapability>() { // from class: android.media.quality.ParameterCapability.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParameterCapability createFromParcel(Parcel parcel) {
            return new ParameterCapability(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParameterCapability[] newArray(int i) {
            return new ParameterCapability[i];
        }
    };
    public static final int TYPE_DOUBLE = 3;
    public static final int TYPE_INT = 1;
    public static final int TYPE_LONG = 2;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_STRING = 4;
    private final Bundle mCaps;
    private final boolean mIsSupported;
    private final String mName;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Capability {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ParameterType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParameterCapability(Parcel parcel) {
        this.mName = parcel.readString();
        this.mIsSupported = parcel.readBoolean();
        this.mType = parcel.readInt();
        this.mCaps = parcel.readBundle();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeBoolean(this.mIsSupported);
        parcel.writeInt(this.mType);
        parcel.writeBundle(this.mCaps);
    }

    public ParameterCapability(String str, boolean z, int i, Bundle bundle) {
        this.mName = str;
        this.mIsSupported = z;
        this.mType = i;
        this.mCaps = bundle;
    }

    public String getParameterName() {
        return this.mName;
    }

    public boolean isSupported() {
        return this.mIsSupported;
    }

    public int getParameterType() {
        return this.mType;
    }

    public Bundle getCapabilities() {
        return new Bundle(this.mCaps);
    }
}
