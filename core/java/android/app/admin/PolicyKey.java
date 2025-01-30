package android.app.admin;

import android.annotation.SystemApi;
import android.p009os.Bundle;
import android.p009os.Parcelable;
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

  public abstract void writeToBundle(Bundle bundle);

  protected PolicyKey(String identifier) {
    this.mIdentifier = (String) Objects.requireNonNull(identifier);
  }

  public String getIdentifier() {
    return this.mIdentifier;
  }

  public boolean hasSameIdentifierAs(PolicyKey other) {
    if (other == null) {
      return false;
    }
    return this.mIdentifier.equals(other.mIdentifier);
  }

  public static PolicyKey readGenericPolicyKeyFromXml(TypedXmlPullParser parser) {
    String identifier = parser.getAttributeValue(null, ATTR_POLICY_IDENTIFIER);
    if (identifier == null) {
      Log.wtf(TAG, "Error parsing generic policy key, identifier is null.");
      return null;
    }
    return new NoArgsPolicyKey(identifier);
  }

  public void saveToXml(TypedXmlSerializer serializer) throws IOException {
    serializer.attribute(null, ATTR_POLICY_IDENTIFIER, this.mIdentifier);
  }

  public PolicyKey readFromXml(TypedXmlPullParser parser)
      throws XmlPullParserException, IOException {
    return this;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PolicyKey other = (PolicyKey) o;
    return Objects.equals(this.mIdentifier, other.mIdentifier);
  }

  public int hashCode() {
    return Objects.hash(this.mIdentifier);
  }
}
