package com.android.internal.org.bouncycastle.asn1;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.android.internal.org.bouncycastle.util.Arrays;
import com.android.internal.org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

/* loaded from: classes5.dex */
public abstract class ASN1ApplicationSpecific extends ASN1Primitive {
    protected final boolean isConstructed;
    protected final byte[] octets;
    protected final int tag;

    ASN1ApplicationSpecific(boolean isConstructed, int tag, byte[] octets) {
        this.isConstructed = isConstructed;
        this.tag = tag;
        this.octets = Arrays.clone(octets);
    }

    public static ASN1ApplicationSpecific getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1ApplicationSpecific)) {
            return (ASN1ApplicationSpecific) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException(
                        "Failed to construct object from byte[]: " + e.getMessage());
            }
        }
        throw new IllegalArgumentException(
                "unknown object in getInstance: " + obj.getClass().getName());
    }

    protected static int getLengthOfHeader(byte[] data) {
        int length = data[1] & 255;
        if (length == 128 || length <= 127) {
            return 2;
        }
        int size = length & 127;
        if (size > 4) {
            throw new IllegalStateException("DER length more than 4 bytes: " + size);
        }
        return size + 2;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    public boolean isConstructed() {
        return this.isConstructed;
    }

    public byte[] getContents() {
        return Arrays.clone(this.octets);
    }

    public int getApplicationTag() {
        return this.tag;
    }

    public ASN1Primitive getObject() throws IOException {
        return ASN1Primitive.fromByteArray(getContents());
    }

    public ASN1Primitive getObject(int derTagNo) throws IOException {
        if (derTagNo >= 31) {
            throw new IOException("unsupported tag number");
        }
        byte[] orig = getEncoded();
        byte[] tmp = replaceTagNumber(derTagNo, orig);
        if ((orig[0] & 32) != 0) {
            tmp[0] = (byte) (tmp[0] | 32);
        }
        return ASN1Primitive.fromByteArray(tmp);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    int encodedLength() throws IOException {
        return StreamUtil.calculateTagLength(this.tag)
                + StreamUtil.calculateBodyLength(this.octets.length)
                + this.octets.length;
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    void encode(ASN1OutputStream out, boolean withTag) throws IOException {
        int flags = 64;
        if (this.isConstructed) {
            flags = 64 | 32;
        }
        out.writeEncoded(withTag, flags, this.tag, this.octets);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive
    boolean asn1Equals(ASN1Primitive o) {
        if (!(o instanceof ASN1ApplicationSpecific)) {
            return false;
        }
        ASN1ApplicationSpecific other = (ASN1ApplicationSpecific) o;
        return this.isConstructed == other.isConstructed
                && this.tag == other.tag
                && Arrays.areEqual(this.octets, other.octets);
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Primitive,
              // com.android.internal.org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        boolean z = this.isConstructed;
        return ((z ? 1 : 0) ^ this.tag) ^ Arrays.hashCode(this.octets);
    }

    private byte[] replaceTagNumber(int newTag, byte[] input) throws IOException {
        int tagNo = input[0] & 31;
        int b = 1;
        if (tagNo == 31) {
            int index = 1 + 1;
            int index2 = input[1];
            int b2 = index2 & 255;
            if ((b2 & 127) == 0) {
                throw new IOException("corrupted stream - invalid high tag number found");
            }
            while ((b2 & 128) != 0) {
                b2 = input[index] & 255;
                index++;
            }
            b = index;
        }
        int index3 = input.length;
        byte[] tmp = new byte[(index3 - b) + 1];
        System.arraycopy(input, b, tmp, 1, tmp.length - 1);
        tmp[0] = (byte) newTag;
        return tmp;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(NavigationBarInflaterView.SIZE_MOD_START);
        if (isConstructed()) {
            sb.append("CONSTRUCTED ");
        }
        sb.append("APPLICATION ");
        sb.append(Integer.toString(getApplicationTag()));
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        if (this.octets != null) {
            sb.append(" #");
            sb.append(Hex.toHexString(this.octets));
        } else {
            sb.append(" #null");
        }
        sb.append(" ");
        return sb.toString();
    }
}
