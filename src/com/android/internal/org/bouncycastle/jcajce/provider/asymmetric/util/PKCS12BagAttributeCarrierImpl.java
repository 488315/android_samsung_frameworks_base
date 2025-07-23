package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes5.dex */
public class PKCS12BagAttributeCarrierImpl implements PKCS12BagAttributeCarrier {
    private Hashtable pkcs12Attributes;
    private Vector pkcs12Ordering;

    PKCS12BagAttributeCarrierImpl(Hashtable hashtable, Vector vector) {
        this.pkcs12Attributes = hashtable;
        this.pkcs12Ordering = vector;
    }

    public PKCS12BagAttributeCarrierImpl() {
        this(new Hashtable(), new Vector());
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        if (this.pkcs12Attributes.containsKey(aSN1ObjectIdentifier)) {
            this.pkcs12Attributes.put(aSN1ObjectIdentifier, aSN1Encodable);
        } else {
            this.pkcs12Attributes.put(aSN1ObjectIdentifier, aSN1Encodable);
            this.pkcs12Ordering.addElement(aSN1ObjectIdentifier);
        }
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (ASN1Encodable) this.pkcs12Attributes.get(aSN1ObjectIdentifier);
    }

    @Override // com.android.internal.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier
    public Enumeration getBagAttributeKeys() {
        return this.pkcs12Ordering.elements();
    }

    int size() {
        return this.pkcs12Ordering.size();
    }

    Hashtable getAttributes() {
        return this.pkcs12Attributes;
    }

    Vector getOrdering() {
        return this.pkcs12Ordering;
    }

    public void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.pkcs12Ordering.size() == 0) {
            objectOutputStream.writeObject(new Hashtable());
            objectOutputStream.writeObject(new Vector());
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1OutputStream create = ASN1OutputStream.create(byteArrayOutputStream);
        Enumeration bagAttributeKeys = getBagAttributeKeys();
        while (bagAttributeKeys.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(bagAttributeKeys.nextElement());
            create.writeObject((ASN1Primitive) aSN1ObjectIdentifier);
            create.writeObject((ASN1Encodable) this.pkcs12Attributes.get(aSN1ObjectIdentifier));
        }
        objectOutputStream.writeObject(byteArrayOutputStream.toByteArray());
    }

    public void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Object readObject = objectInputStream.readObject();
        if (readObject instanceof Hashtable) {
            this.pkcs12Attributes = (Hashtable) readObject;
            this.pkcs12Ordering = (Vector) objectInputStream.readObject();
        } else {
            ASN1InputStream aSN1InputStream = new ASN1InputStream((byte[]) readObject);
            while (true) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) aSN1InputStream.readObject();
                if (aSN1ObjectIdentifier == null) {
                    return;
                } else {
                    setBagAttribute(aSN1ObjectIdentifier, aSN1InputStream.readObject());
                }
            }
        }
    }
}
