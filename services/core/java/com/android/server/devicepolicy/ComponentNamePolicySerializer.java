package com.android.server.devicepolicy;

import android.app.admin.ComponentNamePolicyValue;
import android.app.admin.PolicyValue;
import android.content.ComponentName;
import android.util.Log;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import java.util.Objects;

public final class ComponentNamePolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "package-name");
        String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "class-name");
        if (attributeValue != null && attributeValue2 != null) {
            return new ComponentNamePolicyValue(new ComponentName(attributeValue, attributeValue2));
        }
        Log.e("ComponentNamePolicySerializer", "Error parsing ComponentName policy.");
        return null;
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
        ComponentName componentName = (ComponentName) obj;
        Objects.requireNonNull(componentName);
        typedXmlSerializer.attribute((String) null, "package-name", componentName.getPackageName());
        typedXmlSerializer.attribute((String) null, "class-name", componentName.getClassName());
    }
}
