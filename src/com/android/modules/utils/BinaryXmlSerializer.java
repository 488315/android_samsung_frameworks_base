package com.android.modules.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes6.dex */
public class BinaryXmlSerializer implements TypedXmlSerializer {
    static final int ATTRIBUTE = 15;
    public static final byte[] PROTOCOL_MAGIC_VERSION_0 = {65, 66, 88, 0};
    static final int TYPE_BOOLEAN_FALSE = 208;
    static final int TYPE_BOOLEAN_TRUE = 192;
    static final int TYPE_BYTES_BASE64 = 80;
    static final int TYPE_BYTES_HEX = 64;
    static final int TYPE_DOUBLE = 176;
    static final int TYPE_FLOAT = 160;
    static final int TYPE_INT = 96;
    static final int TYPE_INT_HEX = 112;
    static final int TYPE_LONG = 128;
    static final int TYPE_LONG_HEX = 144;
    static final int TYPE_NULL = 16;
    static final int TYPE_STRING = 32;
    static final int TYPE_STRING_INTERNED = 48;
    private FastDataOutput mOut;
    private int mTagCount = 0;
    private String[] mTagNames;

    private void writeToken(int i, String str) throws IOException {
        if (str != null) {
            this.mOut.writeByte(i | 32);
            this.mOut.writeUTF(str);
        } else {
            this.mOut.writeByte(i | 16);
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IOException {
        if (str != null && !StandardCharsets.UTF_8.name().equalsIgnoreCase(str)) {
            throw new UnsupportedOperationException();
        }
        FastDataOutput fastDataOutputObtainFastDataOutput = obtainFastDataOutput(outputStream);
        this.mOut = fastDataOutputObtainFastDataOutput;
        fastDataOutputObtainFastDataOutput.write(PROTOCOL_MAGIC_VERSION_0);
        this.mTagCount = 0;
        this.mTagNames = new String[8];
    }

    protected FastDataOutput obtainFastDataOutput(OutputStream outputStream) {
        return FastDataOutput.obtain(outputStream);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        FastDataOutput fastDataOutput = this.mOut;
        if (fastDataOutput != null) {
            fastDataOutput.flush();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IOException {
        if (str != null && !StandardCharsets.UTF_8.name().equalsIgnoreCase(str)) {
            throw new UnsupportedOperationException();
        }
        if (bool != null && !bool.booleanValue()) {
            throw new UnsupportedOperationException();
        }
        this.mOut.writeByte(16);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IOException {
        this.mOut.writeByte(17);
        flush();
        this.mOut.release();
        this.mOut = null;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        return this.mTagCount;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        return "";
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        return this.mTagNames[this.mTagCount - 1];
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        int i = this.mTagCount;
        String[] strArr = this.mTagNames;
        if (i == strArr.length) {
            this.mTagNames = (String[]) Arrays.copyOf(strArr, i + (i >> 1));
        }
        String[] strArr2 = this.mTagNames;
        int i2 = this.mTagCount;
        this.mTagCount = i2 + 1;
        strArr2[i2] = str2;
        this.mOut.writeByte(50);
        this.mOut.writeInternedUTF(str2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mTagCount--;
        this.mOut.writeByte(51);
        this.mOut.writeInternedUTF(str2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(47);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeUTF(str3);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeInterned(String str, String str2, String str3) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(63);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeInternedUTF(str3);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeBytesHex(String str, String str2, byte[] bArr) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(79);
        this.mOut.writeInternedUTF(str2);
        if (bArr.length > 65535) {
            throw new IOException("attributeBytesHex: input size (" + bArr.length + ") exceeds maximum allowed size (65535)");
        }
        this.mOut.writeShort(bArr.length);
        this.mOut.write(bArr);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeBytesBase64(String str, String str2, byte[] bArr) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(95);
        this.mOut.writeInternedUTF(str2);
        if (bArr.length > 65535) {
            throw new IOException("attributeBytesBase64: input size (" + bArr.length + ") exceeds maximum allowed size (65535)");
        }
        this.mOut.writeShort(bArr.length);
        this.mOut.write(bArr);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeInt(String str, String str2, int i) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(111);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeInt(i);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeIntHex(String str, String str2, int i) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(127);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeInt(i);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeLong(String str, String str2, long j) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(143);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeLong(j);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeLongHex(String str, String str2, long j) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(159);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeLong(j);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeFloat(String str, String str2, float f) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(175);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeFloat(f);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeDouble(String str, String str2, double d) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        this.mOut.writeByte(191);
        this.mOut.writeInternedUTF(str2);
        this.mOut.writeDouble(d);
        return this;
    }

    @Override // com.android.modules.utils.TypedXmlSerializer
    public XmlSerializer attributeBoolean(String str, String str2, boolean z) throws IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        if (z) {
            this.mOut.writeByte(207);
            this.mOut.writeInternedUTF(str2);
            return this;
        }
        this.mOut.writeByte(223);
        this.mOut.writeInternedUTF(str2);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i, int i2) throws IOException {
        writeToken(4, new String(cArr, i, i2));
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IOException {
        writeToken(4, str);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IOException {
        writeToken(5, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IOException {
        writeToken(6, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IOException {
        writeToken(8, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IOException {
        writeToken(9, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IOException {
        writeToken(10, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IOException {
        writeToken(7, str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z) {
        if (!"http://xmlpull.org/v1/doc/features.html#indent-output".equals(str)) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z) {
        throw new UnsupportedOperationException();
    }

    private static IllegalArgumentException illegalNamespace() {
        throw new IllegalArgumentException("Namespaces are not supported");
    }
}
