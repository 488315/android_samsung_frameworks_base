package com.android.server.devicepolicy;

import android.app.admin.IntegerPolicyValue;
import android.app.admin.PolicyKey;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class IntegerPolicySerializer extends PolicySerializer {
  @Override // com.android.server.devicepolicy.PolicySerializer
  public void saveToXml(PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, Integer num) {
    Objects.requireNonNull(num);
    typedXmlSerializer.attributeInt((String) null, "value", num.intValue());
  }

  @Override // com.android.server.devicepolicy.PolicySerializer
  /* renamed from: readFromXml, reason: merged with bridge method [inline-methods] */
  public IntegerPolicyValue mo5067readFromXml(TypedXmlPullParser typedXmlPullParser) {
    try {
      return new IntegerPolicyValue(typedXmlPullParser.getAttributeInt((String) null, "value"));
    } catch (XmlPullParserException e) {
      Log.e("IntegerPolicySerializer", "Error parsing Integer policy value", e);
      return null;
    }
  }
}
