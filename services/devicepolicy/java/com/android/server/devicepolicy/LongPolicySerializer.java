package com.android.server.devicepolicy;

import android.app.admin.LongPolicyValue;
import android.app.admin.PolicyKey;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class LongPolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, Long l) {
        Objects.requireNonNull(l);
        typedXmlSerializer.attributeLong((String) null, "value", l.longValue());
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    /* renamed from: readFromXml, reason: merged with bridge method [inline-methods] */
    public LongPolicyValue mo5067readFromXml(TypedXmlPullParser typedXmlPullParser) {
        try {
            return new LongPolicyValue(typedXmlPullParser.getAttributeLong((String) null, "value"));
        } catch (XmlPullParserException e) {
            Log.e("LongPolicySerializer", "Error parsing Long policy value", e);
            return null;
        }
    }
}
