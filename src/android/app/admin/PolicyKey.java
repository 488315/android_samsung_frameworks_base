package android.app.admin;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes.dex */
public abstract class PolicyKey implements Parcelable {
    static final String ATTR_POLICY_IDENTIFIER = "policy-identifier";
    static final String TAG = "PolicyKey";
    private final String mIdentifier;

    public PolicyKey readFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        return this;
    }

    public abstract void writeToBundle(Bundle bundle);

    protected PolicyKey(String str) {
        this.mIdentifier = (String) Objects.requireNonNull(str);
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public boolean hasSameIdentifierAs(PolicyKey policyKey) {
        if (policyKey == null) {
            return false;
        }
        return this.mIdentifier.equals(policyKey.mIdentifier);
    }

    public static PolicyKey readGenericPolicyKeyFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue(null, ATTR_POLICY_IDENTIFIER);
        if (attributeValue == null) {
            Log.wtf(TAG, "Error parsing generic policy key, identifier is null.");
            return null;
        }
        return new NoArgsPolicyKey(attributeValue);
    }

    public void saveToXml(TypedXmlSerializer typedXmlSerializer) throws IOException {
        typedXmlSerializer.attribute(null, ATTR_POLICY_IDENTIFIER, this.mIdentifier);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mIdentifier, ((PolicyKey) obj).mIdentifier);
    }

    public int hashCode() {
        return Objects.hash(this.mIdentifier);
    }
}
