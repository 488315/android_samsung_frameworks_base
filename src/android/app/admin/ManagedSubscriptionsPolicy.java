package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ManagedSubscriptionsPolicy implements Parcelable {
    public static final Parcelable.Creator<ManagedSubscriptionsPolicy> CREATOR = new Parcelable.Creator<ManagedSubscriptionsPolicy>() { // from class: android.app.admin.ManagedSubscriptionsPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedSubscriptionsPolicy createFromParcel(Parcel parcel) {
            return new ManagedSubscriptionsPolicy(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ManagedSubscriptionsPolicy[] newArray(int i) {
            return new ManagedSubscriptionsPolicy[i];
        }
    };
    private static final String KEY_POLICY_TYPE = "policy_type";
    private static final String TAG = "ManagedSubscriptionsPolicy";
    public static final int TYPE_ALL_MANAGED_SUBSCRIPTIONS = 1;
    public static final int TYPE_ALL_PERSONAL_SUBSCRIPTIONS = 0;
    private final int mPolicyType;

    @Retention(RetentionPolicy.SOURCE)
    @interface ManagedSubscriptionsPolicyType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ManagedSubscriptionsPolicy(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Invalid policy type");
        }
        this.mPolicyType = i;
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    public String toString() {
        return TextUtils.formatSimple("ManagedSubscriptionsPolicy (type: %d)", Integer.valueOf(this.mPolicyType));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mPolicyType);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ManagedSubscriptionsPolicy) && this.mPolicyType == ((ManagedSubscriptionsPolicy) obj).mPolicyType;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mPolicyType));
    }

    public static ManagedSubscriptionsPolicy readFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            return new ManagedSubscriptionsPolicy(typedXmlPullParser.getAttributeInt(null, KEY_POLICY_TYPE, -1));
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Load xml failed", e);
            return null;
        }
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attributeInt(null, KEY_POLICY_TYPE, this.mPolicyType);
    }
}
