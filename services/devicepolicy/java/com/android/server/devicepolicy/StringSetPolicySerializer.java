package com.android.server.devicepolicy;

import android.app.admin.PolicyKey;
import android.app.admin.PolicyValue;
import android.app.admin.StringSetPolicyValue;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class StringSetPolicySerializer extends PolicySerializer {
  @Override // com.android.server.devicepolicy.PolicySerializer
  public void saveToXml(PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, Set set) {
    Objects.requireNonNull(set);
    typedXmlSerializer.attribute(
        (String) null, "strings", String.join(KnoxVpnFirewallHelper.DELIMITER, set));
  }

  @Override // com.android.server.devicepolicy.PolicySerializer
  /* renamed from: readFromXml */
  public PolicyValue mo5067readFromXml(TypedXmlPullParser typedXmlPullParser) {
    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "strings");
    if (attributeValue == null) {
      Log.e("DevicePolicyEngine", "Error parsing StringSet policy value.");
      return null;
    }
    return new StringSetPolicyValue(
        Set.of((Object[]) attributeValue.split(KnoxVpnFirewallHelper.DELIMITER)));
  }
}
