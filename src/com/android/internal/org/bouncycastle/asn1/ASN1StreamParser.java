package com.android.internal.org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class ASN1StreamParser {
    private final InputStream _in;
    private final int _limit;
    private final byte[][] tmpBuffers;

    public ASN1StreamParser(InputStream inputStream) {
        this(inputStream, StreamUtil.findLimit(inputStream));
    }

    public ASN1StreamParser(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    public ASN1StreamParser(InputStream inputStream, int i) {
        this(inputStream, i, new byte[11][]);
    }

    ASN1StreamParser(InputStream inputStream, int i, byte[][] bArr) {
        this._in = inputStream;
        this._limit = i;
        this.tmpBuffers = bArr;
    }

    public ASN1Encodable readObject() throws IOException {
        int read = this._in.read();
        if (read < 0) {
            return null;
        }
        return implParseObject(read);
    }

    ASN1Encodable implParseObject(int i) throws IOException {
        set00Check(false);
        int readTagNumber = ASN1InputStream.readTagNumber(this._in, i);
        int readLength = ASN1InputStream.readLength(this._in, this._limit, readTagNumber == 3 || readTagNumber == 4 || readTagNumber == 16 || readTagNumber == 17 || readTagNumber == 8);
        if (readLength < 0) {
            if ((i & 32) == 0) {
                throw new IOException("indefinite-length primitive encoding encountered");
            }
            ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this._in, this._limit), this._limit, this.tmpBuffers);
            int i2 = i & 192;
            if (i2 != 0) {
                return new BERTaggedObjectParser(i2, readTagNumber, aSN1StreamParser);
            }
            return aSN1StreamParser.parseImplicitConstructedIL(readTagNumber);
        }
        DefiniteLengthInputStream definiteLengthInputStream = new DefiniteLengthInputStream(this._in, readLength, this._limit);
        if ((i & 224) == 0) {
            return parseImplicitPrimitive(readTagNumber, definiteLengthInputStream);
        }
        ASN1StreamParser aSN1StreamParser2 = new ASN1StreamParser(definiteLengthInputStream, definiteLengthInputStream.getLimit(), this.tmpBuffers);
        int i3 = i & 192;
        if (i3 != 0) {
            return new DLTaggedObjectParser(i3, readTagNumber, (i & 32) != 0, aSN1StreamParser2);
        }
        return aSN1StreamParser2.parseImplicitConstructedDL(readTagNumber);
    }

    ASN1Primitive loadTaggedDL(int i, int i2, boolean z) throws IOException {
        if (!z) {
            return ASN1TaggedObject.createPrimitive(i, i2, ((DefiniteLengthInputStream) this._in).toByteArray());
        }
        return ASN1TaggedObject.createConstructedDL(i, i2, readVector());
    }

    ASN1Primitive loadTaggedIL(int i, int i2) throws IOException {
        return ASN1TaggedObject.createConstructedIL(i, i2, readVector());
    }

    ASN1Encodable parseImplicitConstructedDL(int i) throws IOException {
        if (i == 3) {
            return new BERBitStringParser(this);
        }
        if (i == 4) {
            return new BEROctetStringParser(this);
        }
        if (i == 8) {
            return new DERExternalParser(this);
        }
        if (i == 16) {
            return new DLSequenceParser(this);
        }
        if (i == 17) {
            return new DLSetParser(this);
        }
        throw new ASN1Exception("unknown DL object encountered: 0x" + Integer.toHexString(i));
    }

    ASN1Encodable parseImplicitConstructedIL(int i) throws IOException {
        if (i == 3) {
            return new BERBitStringParser(this);
        }
        if (i == 4) {
            return new BEROctetStringParser(this);
        }
        if (i == 8) {
            return new DERExternalParser(this);
        }
        if (i == 16) {
            return new BERSequenceParser(this);
        }
        if (i == 17) {
            return new BERSetParser(this);
        }
        throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(i));
    }

    ASN1Encodable parseImplicitPrimitive(int i) throws IOException {
        return parseImplicitPrimitive(i, (DefiniteLengthInputStream) this._in);
    }

    ASN1Encodable parseImplicitPrimitive(int i, DefiniteLengthInputStream definiteLengthInputStream) throws IOException {
        if (i == 3) {
            return new DLBitStringParser(definiteLengthInputStream);
        }
        if (i == 4) {
            return new DEROctetStringParser(definiteLengthInputStream);
        }
        if (i == 8) {
            throw new ASN1Exception("externals must use constructed encoding (see X.690 8.18)");
        }
        if (i == 16) {
            throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
        }
        if (i == 17) {
            throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
        }
        try {
            return ASN1InputStream.createPrimitiveDERObject(i, definiteLengthInputStream, this.tmpBuffers);
        } catch (IllegalArgumentException e) {
            throw new ASN1Exception("corrupted stream detected", e);
        }
    }

    ASN1Encodable parseObject(int i) throws IOException {
        if (i < 0 || i > 30) {
            throw new IllegalArgumentException("invalid universal tag number: " + i);
        }
        int read = this._in.read();
        if (read < 0) {
            return null;
        }
        if ((read & (-33)) != i) {
            throw new IOException("unexpected identifier encountered: " + read);
        }
        return implParseObject(read);
    }

    ASN1TaggedObjectParser parseTaggedObject() throws IOException {
        int read = this._in.read();
        if (read < 0) {
            return null;
        }
        if ((read & 192) == 0) {
            throw new ASN1Exception("no tagged object found");
        }
        return (ASN1TaggedObjectParser) implParseObject(read);
    }

    ASN1EncodableVector readVector() throws IOException {
        int read = this._in.read();
        if (read < 0) {
            return new ASN1EncodableVector(0);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        do {
            ASN1Encodable implParseObject = implParseObject(read);
            if (implParseObject instanceof InMemoryRepresentable) {
                aSN1EncodableVector.add(((InMemoryRepresentable) implParseObject).getLoadedObject());
            } else {
                aSN1EncodableVector.add(implParseObject.toASN1Primitive());
            }
            read = this._in.read();
        } while (read >= 0);
        return aSN1EncodableVector;
    }

    private void set00Check(boolean z) {
        InputStream inputStream = this._in;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) inputStream).setEofOn00(z);
        }
    }
}
