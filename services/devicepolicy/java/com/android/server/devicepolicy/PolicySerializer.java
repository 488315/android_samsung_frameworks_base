package com.android.server.devicepolicy;

import android.app.admin.PolicyKey;
import android.app.admin.PolicyValue;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

/* loaded from: classes2.dex */
public abstract class PolicySerializer {
    /* renamed from: readFromXml */
    public abstract PolicyValue mo5067readFromXml(TypedXmlPullParser typedXmlPullParser);

    public abstract void saveToXml(PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, Object obj);
}
