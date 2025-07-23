package com.android.internal.org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
class ConstructedBitStream extends InputStream {
    private ASN1BitStringParser _currentParser;
    private InputStream _currentStream;
    private final boolean _octetAligned;
    private final ASN1StreamParser _parser;
    private boolean _first = true;
    private int _padBits = 0;

    ConstructedBitStream(ASN1StreamParser aSN1StreamParser, boolean z) {
        this._parser = aSN1StreamParser;
        this._octetAligned = z;
    }

    int getPadBits() {
        return this._padBits;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        if (this._currentStream == null) {
            if (!this._first) {
                return -1;
            }
            ASN1BitStringParser nextParser = getNextParser();
            this._currentParser = nextParser;
            if (nextParser == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getBitStream();
        }
        while (true) {
            int read = this._currentStream.read(bArr, i + i3, i2 - i3);
            if (read >= 0) {
                i3 += read;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                this._padBits = this._currentParser.getPadBits();
                ASN1BitStringParser nextParser2 = getNextParser();
                this._currentParser = nextParser2;
                if (nextParser2 == null) {
                    this._currentStream = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this._currentStream = nextParser2.getBitStream();
            }
        }
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this._currentStream == null) {
            if (!this._first) {
                return -1;
            }
            ASN1BitStringParser nextParser = getNextParser();
            this._currentParser = nextParser;
            if (nextParser == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getBitStream();
        }
        while (true) {
            int read = this._currentStream.read();
            if (read >= 0) {
                return read;
            }
            this._padBits = this._currentParser.getPadBits();
            ASN1BitStringParser nextParser2 = getNextParser();
            this._currentParser = nextParser2;
            if (nextParser2 == null) {
                this._currentStream = null;
                return -1;
            }
            this._currentStream = nextParser2.getBitStream();
        }
    }

    private ASN1BitStringParser getNextParser() throws IOException {
        ASN1Encodable readObject = this._parser.readObject();
        if (readObject == null) {
            if (!this._octetAligned || this._padBits == 0) {
                return null;
            }
            throw new IOException("expected octet-aligned bitstring, but found padBits: " + this._padBits);
        }
        if (readObject instanceof ASN1BitStringParser) {
            if (this._padBits != 0) {
                throw new IOException("only the last nested bitstring can have padding");
            }
            return (ASN1BitStringParser) readObject;
        }
        throw new IOException("unknown object encountered: " + readObject.getClass());
    }
}
