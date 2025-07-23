package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.cms.AttributeTable;
import java.util.Map;

/* loaded from: classes5.dex */
public class SimpleAttributeTableGenerator implements CMSAttributeTableGenerator {
    private final AttributeTable attributes;

    public SimpleAttributeTableGenerator(AttributeTable attributeTable) {
        this.attributes = attributeTable;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator
    public AttributeTable getAttributes(Map map) {
        return this.attributes;
    }
}
