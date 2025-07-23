package com.android.internal.org.bouncycastle.asn1;

import com.android.internal.org.bouncycastle.util.Arrays;
import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class ASN1TaggedObject extends ASN1Primitive implements ASN1TaggedObjectParser {
    private static final int DECLARED_EXPLICIT = 1;
    private static final int DECLARED_IMPLICIT = 2;
    private static final int PARSED_EXPLICIT = 3;
    private static final int PARSED_IMPLICIT = 4;
    final int explicitness;
    final ASN1Encodable obj;
    final int tagClass;
    final int tagNo;

    @Override // com.android.internal.org.bouncycastle.asn1.InMemoryRepresentable
    public final ASN1Primitive getLoadedObject() {
        return this;
    }

    abstract ASN1Sequence rebuildConstructed(ASN1Primitive aSN1Primitive);

    abstract ASN1TaggedObject replaceTag(int i, int i2);

    public static ASN1TaggedObject getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1TaggedObject)) {
            return (ASN1TaggedObject) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1TaggedObject) {
                return (ASN1TaggedObject) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return checkedCast(fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("failed to construct tagged object from byte[]: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1TaggedObject getInstance(Object obj, int i) {
        if (obj == null) {
            throw new NullPointerException("'obj' cannot be null");
        }
        ASN1TaggedObject aSN1TaggedObject = getInstance(obj);
        if (i == aSN1TaggedObject.getTagClass()) {
            return aSN1TaggedObject;
        }
        throw new IllegalArgumentException("unexpected tag in getInstance: " + ASN1Util.getTagText(aSN1TaggedObject));
    }

    public static ASN1TaggedObject getInstance(Object obj, int i, int i2) {
        if (obj == null) {
            throw new NullPointerException("'obj' cannot be null");
        }
        ASN1TaggedObject aSN1TaggedObject = getInstance(obj);
        if (aSN1TaggedObject.hasTag(i, i2)) {
            return aSN1TaggedObject;
        }
        throw new IllegalArgumentException("unexpected tag in getInstance: " + ASN1Util.getTagText(aSN1TaggedObject));
    }

    public static ASN1TaggedObject getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (128 != aSN1TaggedObject.getTagClass()) {
            throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
        }
        if (z) {
            return aSN1TaggedObject.getExplicitBaseTagged();
        }
        throw new IllegalArgumentException("this method not valid for implicitly tagged tagged objects");
    }

    protected ASN1TaggedObject(boolean z, int i, ASN1Encodable aSN1Encodable) {
        this(z, 128, i, aSN1Encodable);
    }

    protected ASN1TaggedObject(boolean z, int i, int i2, ASN1Encodable aSN1Encodable) {
        this(z ? 1 : 2, i, i2, aSN1Encodable);
    }

    ASN1TaggedObject(int i, int i2, int i3, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable == null) {
            throw new NullPointerException("'obj' cannot be null");
        }
        if (i2 == 0 || (i2 & 192) != i2) {
            throw new IllegalArgumentException("invalid tag class: " + i2);
        }
        this.explicitness = aSN1Encodable instanceof ASN1Choice ? 1 : i;
        this.tagClass = i2;
        this.tagNo = i3;
        this.obj = aSN1Encodable;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    final boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1TaggedObject)) {
            return false;
        }
        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
        if (this.tagNo != aSN1TaggedObject.tagNo || this.tagClass != aSN1TaggedObject.tagClass) {
            return false;
        }
        if (this.explicitness != aSN1TaggedObject.explicitness && isExplicit() != aSN1TaggedObject.isExplicit()) {
            return false;
        }
        ASN1Primitive aSN1Primitive2 = this.obj.toASN1Primitive();
        ASN1Primitive aSN1Primitive3 = aSN1TaggedObject.obj.toASN1Primitive();
        if (aSN1Primitive2 == aSN1Primitive3) {
            return true;
        }
        if (!isExplicit()) {
            try {
                return Arrays.areEqual(getEncoded(), aSN1TaggedObject.getEncoded());
            } catch (IOException unused) {
                return false;
            }
        }
        return aSN1Primitive2.asn1Equals(aSN1Primitive3);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive, com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return this.obj.toASN1Primitive().hashCode() ^ (((this.tagClass * 7919) ^ this.tagNo) ^ (isExplicit() ? 15 : 240));
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagClass() {
        return this.tagClass;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this.tagNo;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasContextTag() {
        return this.tagClass == 128;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasContextTag(int i) {
        return this.tagClass == 128 && this.tagNo == i;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasTag(int i, int i2) {
        return this.tagClass == i && this.tagNo == i2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasTagClass(int i) {
        return this.tagClass == i;
    }

    public ASN1Primitive getObject() {
        return this.obj.toASN1Primitive();
    }

    public boolean isExplicit() {
        int i = this.explicitness;
        return i == 1 || i == 3;
    }

    boolean isParsed() {
        int i = this.explicitness;
        return i == 3 || i == 4;
    }

    public ASN1Object getBaseObject() {
        ASN1Encodable aSN1Encodable = this.obj;
        return aSN1Encodable instanceof ASN1Object ? (ASN1Object) aSN1Encodable : aSN1Encodable.toASN1Primitive();
    }

    public ASN1Object getExplicitBaseObject() {
        if (!isExplicit()) {
            throw new IllegalStateException("object implicit - explicit expected.");
        }
        ASN1Encodable aSN1Encodable = this.obj;
        return aSN1Encodable instanceof ASN1Object ? (ASN1Object) aSN1Encodable : aSN1Encodable.toASN1Primitive();
    }

    public ASN1TaggedObject getExplicitBaseTagged() {
        if (!isExplicit()) {
            throw new IllegalStateException("object implicit - explicit expected.");
        }
        return checkedCast(this.obj.toASN1Primitive());
    }

    public ASN1TaggedObject getImplicitBaseTagged(int i, int i2) {
        if (i == 0 || (i & 192) != i) {
            throw new IllegalArgumentException("invalid base tag class: " + i);
        }
        int i3 = this.explicitness;
        if (i3 == 1) {
            throw new IllegalStateException("object explicit - implicit expected.");
        }
        if (i3 == 2) {
            return ASN1Util.checkTag(checkedCast(this.obj.toASN1Primitive()), i, i2);
        }
        return replaceTag(i, i2);
    }

    public ASN1Primitive getBaseUniversal(boolean z, int i) {
        ASN1UniversalType aSN1UniversalType = ASN1UniversalTypes.get(i);
        if (aSN1UniversalType == null) {
            throw new IllegalArgumentException("unsupported UNIVERSAL tag number: " + i);
        }
        return getBaseUniversal(z, aSN1UniversalType);
    }

    ASN1Primitive getBaseUniversal(boolean z, ASN1UniversalType aSN1UniversalType) {
        if (z) {
            if (!isExplicit()) {
                throw new IllegalStateException("object explicit - implicit expected.");
            }
            return aSN1UniversalType.checkedCast(this.obj.toASN1Primitive());
        }
        if (1 == this.explicitness) {
            throw new IllegalStateException("object explicit - implicit expected.");
        }
        ASN1Primitive aSN1Primitive = this.obj.toASN1Primitive();
        int i = this.explicitness;
        if (i == 3) {
            return aSN1UniversalType.fromImplicitConstructed(rebuildConstructed(aSN1Primitive));
        }
        if (i == 4) {
            if (aSN1Primitive instanceof ASN1Sequence) {
                return aSN1UniversalType.fromImplicitConstructed((ASN1Sequence) aSN1Primitive);
            }
            return aSN1UniversalType.fromImplicitPrimitive((DEROctetString) aSN1Primitive);
        }
        return aSN1UniversalType.checkedCast(aSN1Primitive);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseBaseUniversal(boolean z, int i) throws IOException {
        ASN1Primitive baseUniversal = getBaseUniversal(z, i);
        if (i == 3) {
            return ((ASN1BitString) baseUniversal).parser();
        }
        if (i == 4) {
            return ((ASN1OctetString) baseUniversal).parser();
        }
        if (i != 16) {
            return i != 17 ? baseUniversal : ((ASN1Set) baseUniversal).parser();
        }
        return ((ASN1Sequence) baseUniversal).parser();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseExplicitBaseObject() throws IOException {
        return getExplicitBaseObject();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseExplicitBaseTagged() throws IOException {
        return getExplicitBaseTagged();
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int i, int i2) throws IOException {
        return getImplicitBaseTagged(i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDERObject() {
        return new DERTaggedObject(this.explicitness, this.tagClass, this.tagNo, this.obj);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    ASN1Primitive toDLObject() {
        return new DLTaggedObject(this.explicitness, this.tagClass, this.tagNo, this.obj);
    }

    public String toString() {
        return ASN1Util.getTagText(this.tagClass, this.tagNo) + this.obj;
    }

    static ASN1Primitive createConstructedDL(int i, int i2, ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() == 1) {
            return new DLTaggedObject(3, i, i2, aSN1EncodableVector.get(0));
        }
        return new DLTaggedObject(4, i, i2, DLFactory.createSequence(aSN1EncodableVector));
    }

    static ASN1Primitive createConstructedIL(int i, int i2, ASN1EncodableVector aSN1EncodableVector) {
        if (aSN1EncodableVector.size() == 1) {
            return new BERTaggedObject(3, i, i2, aSN1EncodableVector.get(0));
        }
        return new BERTaggedObject(4, i, i2, BERFactory.createSequence(aSN1EncodableVector));
    }

    static ASN1Primitive createPrimitive(int i, int i2, byte[] bArr) {
        return new DLTaggedObject(4, i, i2, new DEROctetString(bArr));
    }

    private static ASN1TaggedObject checkedCast(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1TaggedObject) {
            return (ASN1TaggedObject) aSN1Primitive;
        }
        throw new IllegalStateException("unexpected object: " + aSN1Primitive.getClass().getName());
    }
}
