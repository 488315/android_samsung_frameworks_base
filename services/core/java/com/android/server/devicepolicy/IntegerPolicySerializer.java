package com.android.server.devicepolicy;

import android.app.admin.IntegerPolicyValue;
import android.app.admin.PolicyValue;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IntegerPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            return new IntegerPolicyValue(typedXmlPullParser.getAttributeInt((String) null, "value"));
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
