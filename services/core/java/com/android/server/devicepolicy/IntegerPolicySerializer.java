package com.android.server.devicepolicy;

import android.app.admin.IntegerPolicyValue;
import android.app.admin.PolicyValue;
import android.util.Log;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Objects;

public final class IntegerPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            return new IntegerPolicyValue(
                    typedXmlPullParser.getAttributeInt((String) null, "value"));
        } catch (XmlPullParserException e) {
            Log.e("IntegerPolicySerializer", "Error parsing Integer policy value", e);
            return null;
        }
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
        Integer num = (Integer) obj;
        Objects.requireNonNull(num);
        typedXmlSerializer.attributeInt((String) null, "value", num.intValue());
    }
}
