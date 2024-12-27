package com.android.server.devicepolicy;

import android.app.admin.BooleanPolicyValue;
import android.app.admin.PolicyValue;
import android.util.Log;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BooleanPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            return new BooleanPolicyValue(
                    typedXmlPullParser.getAttributeBoolean((String) null, "value"));
        } catch (XmlPullParserException e) {
            Log.e("BooleanPolicySerializer", "Error parsing Boolean policy value", e);
            return null;
        }
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
        Boolean bool = (Boolean) obj;
        Objects.requireNonNull(bool);
        typedXmlSerializer.attributeBoolean((String) null, "value", bool.booleanValue());
    }
}
