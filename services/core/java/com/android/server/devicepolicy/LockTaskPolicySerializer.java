package com.android.server.devicepolicy;

import android.app.admin.LockTaskPolicy;
import android.app.admin.PolicyValue;
import android.util.Log;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
