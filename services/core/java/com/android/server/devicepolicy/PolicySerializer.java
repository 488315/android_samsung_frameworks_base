package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

public abstract class PolicySerializer {
    public abstract PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser);

    public abstract void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer);
}
