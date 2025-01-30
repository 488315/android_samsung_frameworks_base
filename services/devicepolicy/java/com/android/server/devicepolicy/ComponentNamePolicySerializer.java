package com.android.server.devicepolicy;

import android.app.admin.ComponentNamePolicyValue;
import android.app.admin.PolicyKey;
import android.content.ComponentName;
import android.util.Log;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ComponentNamePolicySerializer extends PolicySerializer {
  @Override // com.android.server.devicepolicy.PolicySerializer
  public void saveToXml(
      PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, ComponentName componentName) {
    Objects.requireNonNull(componentName);
    typedXmlSerializer.attribute((String) null, "package-name", componentName.getPackageName());
    typedXmlSerializer.attribute((String) null, "class-name", componentName.getClassName());
  }

  @Override // com.android.server.devicepolicy.PolicySerializer
  /* renamed from: readFromXml, reason: merged with bridge method [inline-methods] */
  public ComponentNamePolicyValue mo5067readFromXml(TypedXmlPullParser typedXmlPullParser) {
    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "package-name");
    String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "class-name");
    if (attributeValue == null || attributeValue2 == null) {
      Log.e("ComponentNamePolicySerializer", "Error parsing ComponentName policy.");
      return null;
    }
    return new ComponentNamePolicyValue(new ComponentName(attributeValue, attributeValue2));
  }
}
