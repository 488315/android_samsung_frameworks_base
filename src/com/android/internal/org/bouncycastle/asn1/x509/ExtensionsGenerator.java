package com.android.internal.org.bouncycastle.asn1.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Encodable;
import com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.internal.org.bouncycastle.asn1.ASN1Encoding;
import com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.internal.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.asn1.DEROctetString;
import com.android.internal.org.bouncycastle.asn1.DERSequence;
import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

/* loaded from: classes5.dex */
public class ExtensionsGenerator {
    private static final Set dupsAllowed;
    private Hashtable extensions = new Hashtable();
    private Vector extOrdering = new Vector();

    static {
        HashSet hashSet = new HashSet();
        hashSet.add(Extension.subjectAlternativeName);
        hashSet.add(Extension.issuerAlternativeName);
        hashSet.add(Extension.subjectDirectoryAttributes);
        hashSet.add(Extension.certificateIssuer);
        dupsAllowed = Collections.unmodifiableSet(hashSet);
    }

    public void reset() {
        this.extensions = new Hashtable();
        this.extOrdering = new Vector();
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, ASN1Encodable aSN1Encodable) throws IOException {
        addExtension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
    }

    public void addExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        if (this.extensions.containsKey(aSN1ObjectIdentifier)) {
            if (dupsAllowed.contains(aSN1ObjectIdentifier)) {
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance(DEROctetString.getInstance(((Extension) this.extensions.get(aSN1ObjectIdentifier)).getExtnValue()).getOctets());
                ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(bArr);
                ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(aSN1Sequence.size() + aSN1Sequence2.size());
                Enumeration objects = aSN1Sequence.getObjects();
                while (objects.hasMoreElements()) {
                    aSN1EncodableVector.add((ASN1Encodable) objects.nextElement());
                }
                Enumeration objects2 = aSN1Sequence2.getObjects();
                while (objects2.hasMoreElements()) {
                    aSN1EncodableVector.add((ASN1Encodable) objects2.nextElement());
                }
                try {
                    this.extensions.put(aSN1ObjectIdentifier, new Extension(aSN1ObjectIdentifier, z, new DERSequence(aSN1EncodableVector).getEncoded()));
                    return;
                } catch (IOException e) {
                    throw new ASN1ParsingException(e.getMessage(), e);
                }
            }
            throw new IllegalArgumentException("extension " + aSN1ObjectIdentifier + " already added");
        }
        this.extOrdering.addElement(aSN1ObjectIdentifier);
        this.extensions.put(aSN1ObjectIdentifier, new Extension(aSN1ObjectIdentifier, z, new DEROctetString(Arrays.clone(bArr))));
    }

    public void addExtension(Extension extension) {
        if (this.extensions.containsKey(extension.getExtnId())) {
            throw new IllegalArgumentException("extension " + extension.getExtnId() + " already added");
        }
        this.extOrdering.addElement(extension.getExtnId());
        this.extensions.put(extension.getExtnId(), extension);
    }

    public void replaceExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, ASN1Encodable aSN1Encodable) throws IOException {
        replaceExtension(aSN1ObjectIdentifier, z, aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER));
    }

    public void replaceExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z, byte[] bArr) {
        replaceExtension(new Extension(aSN1ObjectIdentifier, z, bArr));
    }

    public void replaceExtension(Extension extension) {
        if (!this.extensions.containsKey(extension.getExtnId())) {
            throw new IllegalArgumentException("extension " + extension.getExtnId() + " not present");
        }
        this.extensions.put(extension.getExtnId(), extension);
    }

    public void removeExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if (!this.extensions.containsKey(aSN1ObjectIdentifier)) {
            throw new IllegalArgumentException("extension " + aSN1ObjectIdentifier + " not present");
        }
        this.extOrdering.removeElement(aSN1ObjectIdentifier);
        this.extensions.remove(aSN1ObjectIdentifier);
    }

    public boolean hasExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.extensions.containsKey(aSN1ObjectIdentifier);
    }

    public Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (Extension) this.extensions.get(aSN1ObjectIdentifier);
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public Extensions generate() {
        Extension[] extensionArr = new Extension[this.extOrdering.size()];
        for (int i = 0; i != this.extOrdering.size(); i++) {
            extensionArr[i] = (Extension) this.extensions.get(this.extOrdering.elementAt(i));
        }
        return new Extensions(extensionArr);
    }

    public void addExtension(Extensions extensions) {
        ASN1ObjectIdentifier[] extensionOIDs = extensions.getExtensionOIDs();
        for (int i = 0; i != extensionOIDs.length; i++) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = extensionOIDs[i];
            Extension extension = extensions.getExtension(aSN1ObjectIdentifier);
            addExtension(ASN1ObjectIdentifier.getInstance(aSN1ObjectIdentifier), extension.isCritical(), extension.getExtnValue().getOctets());
        }
    }
}
