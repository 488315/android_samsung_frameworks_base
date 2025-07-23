package android.telecom;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class PhoneAccountHandle implements Parcelable {
    private final ComponentName mComponentName;
    private final String mId;
    private final UserHandle mUserHandle;
    private static final ComponentName TELEPHONY_COMPONENT_NAME = new ComponentName("com.android.phone", "com.android.services.telephony.TelephonyConnectionService");
    public static final Parcelable.Creator<PhoneAccountHandle> CREATOR = new Parcelable.Creator<PhoneAccountHandle>() { // from class: android.telecom.PhoneAccountHandle.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneAccountHandle createFromParcel(Parcel parcel) {
            return new PhoneAccountHandle(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneAccountHandle[] newArray(int i) {
            return new PhoneAccountHandle[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PhoneAccountHandle(ComponentName componentName, String str) {
        this(componentName, str, Process.myUserHandle());
    }

    public PhoneAccountHandle(ComponentName componentName, String str, UserHandle userHandle) {
        checkParameters(componentName, userHandle);
        this.mComponentName = componentName;
        this.mId = str;
        this.mUserHandle = userHandle;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public String getId() {
        return this.mId;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public int hashCode() {
        return Objects.hash(this.mComponentName, this.mId, this.mUserHandle);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mComponentName);
        sb.append(", ");
        if (TELEPHONY_COMPONENT_NAME.equals(this.mComponentName)) {
            sb.append(this.mId);
        } else {
            sb.append(Log.pii(this.mId));
        }
        sb.append(", ");
        sb.append(this.mUserHandle);
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PhoneAccountHandle)) {
            return false;
        }
        PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) obj;
        return Objects.equals(phoneAccountHandle.getComponentName(), getComponentName()) && Objects.equals(phoneAccountHandle.getId(), getId()) && Objects.equals(phoneAccountHandle.getUserHandle(), getUserHandle());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mComponentName.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        this.mUserHandle.writeToParcel(parcel, i);
    }

    private void checkParameters(ComponentName componentName, UserHandle userHandle) {
        if (componentName == null) {
            android.util.Log.w("PhoneAccountHandle", new Exception("PhoneAccountHandle has been created with null ComponentName!"));
        }
        if (userHandle == null) {
            android.util.Log.w("PhoneAccountHandle", new Exception("PhoneAccountHandle has been created with null UserHandle!"));
        }
    }

    private PhoneAccountHandle(Parcel parcel) {
        this(ComponentName.CREATOR.createFromParcel(parcel), parcel.readString(), UserHandle.CREATOR.createFromParcel(parcel));
    }

    public static boolean areFromSamePackage(PhoneAccountHandle phoneAccountHandle, PhoneAccountHandle phoneAccountHandle2) {
        return Objects.equals(phoneAccountHandle != null ? phoneAccountHandle.getComponentName().getPackageName() : null, phoneAccountHandle2 != null ? phoneAccountHandle2.getComponentName().getPackageName() : null);
    }
}
