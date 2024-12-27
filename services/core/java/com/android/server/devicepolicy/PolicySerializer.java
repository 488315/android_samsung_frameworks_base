package com.android.server.devicepolicy;

import android.app.admin.PolicyValue;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class PolicySerializer {
    public abstract PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser);

    public abstract void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer);
}
