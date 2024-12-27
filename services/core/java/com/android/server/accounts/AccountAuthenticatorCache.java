package com.android.server.accounts;

import android.accounts.AuthenticatorDescription;
import android.content.Context;
import android.content.pm.RegisteredServicesCache;
import android.content.pm.XmlSerializerAndParser;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.android.internal.R;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccountAuthenticatorCache extends RegisteredServicesCache
        implements IAccountAuthenticatorCache {
    public static final MySerializer sSerializer = new MySerializer();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MySerializer implements XmlSerializerAndParser {
        public final Object createFromXml(TypedXmlPullParser typedXmlPullParser) {
            return AuthenticatorDescription.newKey(
                    typedXmlPullParser.getAttributeValue((String) null, "type"));
        }

        public final void writeAsXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
            typedXmlSerializer.attribute(
                    (String) null, "type", ((AuthenticatorDescription) obj).type);
        }
    }

    public AccountAuthenticatorCache(Context context) {
        super(
                context,
                "android.accounts.AccountAuthenticator",
                "android.accounts.AccountAuthenticator",
                "account-authenticator",
                sSerializer);
    }

    public final Object parseServiceAttributes(
            Resources resources, String str, AttributeSet attributeSet) {
        TypedArray obtainAttributes =
                resources.obtainAttributes(attributeSet, R.styleable.AccountAuthenticator);
        try {
            String string = obtainAttributes.getString(2);
            int resourceId = obtainAttributes.getResourceId(0, 0);
            int resourceId2 = obtainAttributes.getResourceId(1, 0);
            int resourceId3 = obtainAttributes.getResourceId(3, 0);
            int resourceId4 = obtainAttributes.getResourceId(4, 0);
            boolean z = obtainAttributes.getBoolean(5, false);
            if (!TextUtils.isEmpty(string)) {
                return new AuthenticatorDescription(
                        string, str, resourceId, resourceId2, resourceId3, resourceId4, z);
            }
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }
}
