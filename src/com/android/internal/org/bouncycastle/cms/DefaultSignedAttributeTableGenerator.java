package com.android.internal.org.bouncycastle.cms;

import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSet;
import com.android.internal.org.bouncycastle.asn1.cms.Attribute;
import com.android.internal.org.bouncycastle.asn1.cms.AttributeTable;
import com.android.internal.org.bouncycastle.asn1.cms.CMSAlgorithmProtection;
import com.android.internal.org.bouncycastle.asn1.cms.CMSAttributes;
import com.android.internal.org.bouncycastle.asn1.cms.Time;
import com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

/* loaded from: classes5.dex */
public class DefaultSignedAttributeTableGenerator implements CMSAttributeTableGenerator {
    private final Hashtable table;

    public DefaultSignedAttributeTableGenerator() {
        this.table = new Hashtable();
    }

    public DefaultSignedAttributeTableGenerator(AttributeTable attributeTable) {
        if (attributeTable != null) {
            this.table = attributeTable.toHashtable();
        } else {
            this.table = new Hashtable();
        }
    }

    protected Hashtable createStandardAttributeTable(Map map) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        Hashtable copyHashTable = copyHashTable(this.table);
        if (!copyHashTable.containsKey(CMSAttributes.contentType) && (aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(map.get("contentType"))) != null) {
            Attribute attribute = new Attribute(CMSAttributes.contentType, new DERSet(aSN1ObjectIdentifier));
            copyHashTable.put(attribute.getAttrType(), attribute);
        }
        if (!copyHashTable.containsKey(CMSAttributes.signingTime)) {
            Attribute attribute2 = new Attribute(CMSAttributes.signingTime, new DERSet(new Time(new Date())));
            copyHashTable.put(attribute2.getAttrType(), attribute2);
        }
        if (!copyHashTable.containsKey(CMSAttributes.messageDigest)) {
            Attribute attribute3 = new Attribute(CMSAttributes.messageDigest, new DERSet(new DEROctetString((byte[]) map.get(CMSAttributeTableGenerator.DIGEST))));
            copyHashTable.put(attribute3.getAttrType(), attribute3);
        }
        if (!copyHashTable.contains(CMSAttributes.cmsAlgorithmProtect)) {
            Attribute attribute4 = new Attribute(CMSAttributes.cmsAlgorithmProtect, new DERSet(new CMSAlgorithmProtection((AlgorithmIdentifier) map.get(CMSAttributeTableGenerator.DIGEST_ALGORITHM_IDENTIFIER), 1, (AlgorithmIdentifier) map.get(CMSAttributeTableGenerator.SIGNATURE_ALGORITHM_IDENTIFIER))));
            copyHashTable.put(attribute4.getAttrType(), attribute4);
        }
        return copyHashTable;
    }

    @Override // com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator
    public AttributeTable getAttributes(Map map) {
        return new AttributeTable(createStandardAttributeTable(map));
    }

    private static Hashtable copyHashTable(Hashtable hashtable) {
        Hashtable hashtable2 = new Hashtable();
        Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            Object nextElement = keys.nextElement();
            hashtable2.put(nextElement, hashtable.get(nextElement));
        }
        return hashtable2;
    }
}
