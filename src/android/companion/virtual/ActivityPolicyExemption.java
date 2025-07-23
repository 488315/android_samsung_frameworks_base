package android.companion.virtual;

import android.annotation.SystemApi;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class ActivityPolicyExemption implements Parcelable {
    public static final Parcelable.Creator<ActivityPolicyExemption> CREATOR = new Parcelable.Creator<ActivityPolicyExemption>() { // from class: android.companion.virtual.ActivityPolicyExemption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityPolicyExemption createFromParcel(Parcel parcel) {
            return new ActivityPolicyExemption(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityPolicyExemption[] newArray(int i) {
            return new ActivityPolicyExemption[i];
        }
    };
    private final ComponentName mComponentName;
    private final int mDisplayId;
    private final String mPackageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ActivityPolicyExemption(ComponentName componentName, String str, int i) {
        this.mComponentName = componentName;
        this.mPackageName = str;
        this.mDisplayId = i;
    }

    private ActivityPolicyExemption(Parcel parcel) {
        this.mComponentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.mPackageName = parcel.readString8();
        this.mDisplayId = parcel.readInt();
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mComponentName, i);
        parcel.writeString8(this.mPackageName);
        parcel.writeInt(this.mDisplayId);
    }

    public static final class Builder {
        private ComponentName mComponentName;
        private int mDisplayId = -1;
        private String mPackageName;

        public Builder setComponentName(ComponentName componentName) {
            this.mComponentName = (ComponentName) Objects.requireNonNull(componentName);
            this.mPackageName = null;
            return this;
        }

        public Builder setPackageName(String str) {
            this.mComponentName = null;
            this.mPackageName = (String) Objects.requireNonNull(str);
            return this;
        }

        public Builder setDisplayId(int i) {
            this.mDisplayId = i;
            return this;
        }

        public ActivityPolicyExemption build() {
            if ((this.mComponentName == null) == (this.mPackageName == null)) {
                throw new IllegalArgumentException("Either component name or package name must be set");
            }
            return new ActivityPolicyExemption(this.mComponentName, this.mPackageName, this.mDisplayId);
        }
    }
}
