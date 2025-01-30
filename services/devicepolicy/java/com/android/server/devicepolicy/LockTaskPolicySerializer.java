package com.android.server.devicepolicy;

import android.app.admin.LockTaskPolicy;
import android.app.admin.PolicyKey;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class LockTaskPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, LockTaskPolicy lockTaskPolicy) {
        Objects.requireNonNull(lockTaskPolicy);
        typedXmlSerializer.attribute((String) null, "packages", String.join(KnoxVpnFirewallHelper.DELIMITER, lockTaskPolicy.getPackages()));
        typedXmlSerializer.attributeInt((String) null, "flags", lockTaskPolicy.getFlags());
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    /* renamed from: readFromXml, reason: merged with bridge method [inline-methods] */
    public LockTaskPolicy mo5067readFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "packages");
        if (attributeValue == null) {
            Log.e("LockTaskPolicySerializer", "Error parsing LockTask policy value.");
            return null;
        }
        try {
            return new LockTaskPolicy(Set.of((Object[]) attributeValue.split(KnoxVpnFirewallHelper.DELIMITER)), typedXmlPullParser.getAttributeInt((String) null, "flags"));
        } catch (XmlPullParserException e) {
            Log.e("LockTaskPolicySerializer", "Error parsing LockTask policy value", e);
            return null;
        }
    }
}
