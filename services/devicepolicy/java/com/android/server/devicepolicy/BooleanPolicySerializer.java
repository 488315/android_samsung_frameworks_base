package com.android.server.devicepolicy;

import android.app.admin.BooleanPolicyValue;
import android.app.admin.PolicyKey;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class BooleanPolicySerializer extends PolicySerializer {
  @Override // com.android.server.devicepolicy.PolicySerializer
  public void saveToXml(PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, Boolean bool) {
    Objects.requireNonNull(bool);
    typedXmlSerializer.attributeBoolean((String) null, "value", bool.booleanValue());
  }

  @Override // com.android.server.devicepolicy.PolicySerializer
  /* renamed from: readFromXml, reason: merged with bridge method [inline-methods] */
  public BooleanPolicyValue mo5067readFromXml(TypedXmlPullParser typedXmlPullParser) {
    try {
      return new BooleanPolicyValue(typedXmlPullParser.getAttributeBoolean((String) null, "value"));
    } catch (XmlPullParserException e) {
      Log.e("BooleanPolicySerializer", "Error parsing Boolean policy value", e);
      return null;
    }
  }
}
