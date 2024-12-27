package com.android.server.devicepolicy;

import android.app.admin.LongPolicyValue;
import android.app.admin.PolicyValue;
import android.util.Log;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class LongPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            return new LongPolicyValue(typedXmlPullParser.getAttributeLong((String) null, "value"));
        } catch (XmlPullParserException e) {
            Log.e("LongPolicySerializer", "Error parsing Long policy value", e);
            return null;
        }
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
        Long l = (Long) obj;
        Objects.requireNonNull(l);
        typedXmlSerializer.attributeLong((String) null, "value", l.longValue());
    }
}
