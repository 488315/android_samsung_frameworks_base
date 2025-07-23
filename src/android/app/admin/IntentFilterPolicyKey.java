package android.app.admin;

import android.annotation.SystemApi;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes.dex */
public final class IntentFilterPolicyKey extends PolicyKey {
    public static final Parcelable.Creator<IntentFilterPolicyKey> CREATOR = new Parcelable.Creator<IntentFilterPolicyKey>() { // from class: android.app.admin.IntentFilterPolicyKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilterPolicyKey createFromParcel(Parcel parcel) {
            return new IntentFilterPolicyKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntentFilterPolicyKey[] newArray(int i) {
            return new IntentFilterPolicyKey[i];
        }
    };
    private static final String TAG = "IntentFilterPolicyKey";
    private static final String TAG_INTENT_FILTER_ENTRY = "filter";
    private final IntentFilter mFilter;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IntentFilterPolicyKey(String str, IntentFilter intentFilter) {
        super(str);
        this.mFilter = (IntentFilter) Objects.requireNonNull(intentFilter);
    }

    public IntentFilterPolicyKey(String str) {
        super(str);
        this.mFilter = null;
    }

    private IntentFilterPolicyKey(Parcel parcel) {
        super(parcel.readString());
        this.mFilter = (IntentFilter) parcel.readTypedObject(IntentFilter.CREATOR);
    }

    public IntentFilter getIntentFilter() {
        return this.mFilter;
    }

    @Override // android.app.admin.PolicyKey
    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attribute(null, "policy-identifier", getIdentifier());
        typedXmlSerializer.startTag(null, TAG_INTENT_FILTER_ENTRY);
        this.mFilter.writeToXml(typedXmlSerializer);
        typedXmlSerializer.endTag(null, TAG_INTENT_FILTER_ENTRY);
    }

    @Override // android.app.admin.PolicyKey
    public IntentFilterPolicyKey readFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        return new IntentFilterPolicyKey(typedXmlPullParser.getAttributeValue(null, "policy-identifier"), readIntentFilterFromXml(typedXmlPullParser));
    }

    private IntentFilter readIntentFilterFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            String name = typedXmlPullParser.getName();
            if (name.equals(TAG_INTENT_FILTER_ENTRY)) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.readFromXml(typedXmlPullParser);
                return intentFilter;
            }
            Log.e(TAG, "Unknown tag: " + name);
        }
        Log.e(TAG, "Error parsing IntentFilterPolicyKey, IntentFilter not found");
        return null;
    }

    @Override // android.app.admin.PolicyKey
    public void writeToBundle(Bundle bundle) {
        bundle.putString(PolicyUpdateReceiver.EXTRA_POLICY_KEY, getIdentifier());
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable(PolicyUpdateReceiver.EXTRA_INTENT_FILTER, this.mFilter);
        bundle.putBundle(PolicyUpdateReceiver.EXTRA_POLICY_BUNDLE_KEY, bundle2);
    }

    @Override // android.app.admin.PolicyKey
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            IntentFilterPolicyKey intentFilterPolicyKey = (IntentFilterPolicyKey) obj;
            if (Objects.equals(getIdentifier(), intentFilterPolicyKey.getIdentifier()) && IntentFilter.filterEquals(this.mFilter, intentFilterPolicyKey.mFilter)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.app.admin.PolicyKey
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }

    public String toString() {
        return "IntentFilterPolicyKey{mKey= " + getIdentifier() + "; mFilter= " + this.mFilter + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getIdentifier());
        parcel.writeTypedObject(this.mFilter, i);
    }
}
