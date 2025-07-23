package android.app.admin;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ComponentNamePolicyValue extends PolicyValue<ComponentName> {
    public static final Parcelable.Creator<ComponentNamePolicyValue> CREATOR = new Parcelable.Creator<ComponentNamePolicyValue>() { // from class: android.app.admin.ComponentNamePolicyValue.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentNamePolicyValue createFromParcel(Parcel parcel) {
            return new ComponentNamePolicyValue(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ComponentNamePolicyValue[] newArray(int i) {
            return new ComponentNamePolicyValue[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ComponentNamePolicyValue(ComponentName componentName) {
        super(componentName);
        PolicySizeVerifier.enforceMaxComponentNameLength(componentName);
    }

    private ComponentNamePolicyValue(Parcel parcel) {
        this((ComponentName) parcel.readParcelable(ComponentName.class.getClassLoader()));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValue(), ((ComponentNamePolicyValue) obj).getValue());
    }

    public int hashCode() {
        return Objects.hash(getValue());
    }

    public String toString() {
        return "ComponentNamePolicyValue { mValue= " + getValue() + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(getValue(), i);
    }
}
