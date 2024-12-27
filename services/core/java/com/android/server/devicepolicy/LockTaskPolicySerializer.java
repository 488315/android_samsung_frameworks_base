package com.android.server.devicepolicy;

import android.app.admin.LockTaskPolicy;
import android.app.admin.PolicyValue;
import android.util.Log;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Objects;
import java.util.Set;

public final class LockTaskPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "packages");
        if (attributeValue == null) {
            Log.e("LockTaskPolicySerializer", "Error parsing LockTask policy value.");
            return null;
        }
        try {
            return new LockTaskPolicy(
                    Set.of((Object[]) attributeValue.split(";")),
                    typedXmlPullParser.getAttributeInt((String) null, "flags"));
        } catch (XmlPullParserException e) {
            Log.e("LockTaskPolicySerializer", "Error parsing LockTask policy value", e);
            return null;
        }
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
        LockTaskPolicy lockTaskPolicy = (LockTaskPolicy) obj;
        Objects.requireNonNull(lockTaskPolicy);
        typedXmlSerializer.attribute(
                (String) null, "packages", String.join(";", lockTaskPolicy.getPackages()));
        typedXmlSerializer.attributeInt((String) null, "flags", lockTaskPolicy.getFlags());
    }
}
