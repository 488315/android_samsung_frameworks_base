package com.android.server.devicepolicy;

import android.app.admin.PackageSetPolicyValue;
import android.app.admin.PolicyValue;
import android.util.Log;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import java.util.Objects;
import java.util.Set;

public final class PackageSetPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "strings");
        if (attributeValue != null) {
            return new PackageSetPolicyValue(Set.of((Object[]) attributeValue.split(";")));
        }
        Log.e("DevicePolicyEngine", "Error parsing PackageSet policy value.");
        return null;
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
        Set set = (Set) obj;
        Objects.requireNonNull(set);
        typedXmlSerializer.attribute((String) null, "strings", String.join(";", set));
    }
}
