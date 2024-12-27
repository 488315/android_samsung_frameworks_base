package com.android.server.firewall;

import android.content.ComponentName;
import android.content.Intent;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.Set;

public final class CategoryFilter implements Filter {
    public static final AnonymousClass1 FACTORY = new AnonymousClass1("category");
    public final String mCategoryName;

    /* renamed from: com.android.server.firewall.CategoryFilter$1, reason: invalid class name */
    public final class AnonymousClass1 extends FilterFactory {
        @Override // com.android.server.firewall.FilterFactory
        public final Filter newFilter(XmlPullParser xmlPullParser) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                return new CategoryFilter(attributeValue);
            }
            throw new XmlPullParserException(
                    "Category name must be specified.", xmlPullParser, null);
        }
    }

    public CategoryFilter(String str) {
        this.mCategoryName = str;
    }

    @Override // com.android.server.firewall.Filter
    public final boolean matches(
            IntentFirewall intentFirewall,
            ComponentName componentName,
            Intent intent,
            int i,
            int i2,
            String str,
            int i3) {
        Set<String> categories = intent.getCategories();
        if (categories == null) {
            return false;
        }
        return categories.contains(this.mCategoryName);
    }
}
