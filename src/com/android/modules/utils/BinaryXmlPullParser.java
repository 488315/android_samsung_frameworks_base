package com.android.modules.utils;

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class BinaryXmlPullParser implements TypedXmlPullParser {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', DateFormat.AM_PM, 'b', 'c', DateFormat.DATE, 'e', 'f'};
    private Attribute[] mAttributes;
    private String mCurrentName;
    private String mCurrentText;
    private FastDataInput mIn;
    private int mCurrentToken = 0;
    private int mCurrentDepth = 0;
    private int mAttributeCount = 0;

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributePrefix(int i) {
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) throws XmlPullParserException {
        return 0;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPrefix() {
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isAttributeDefault(int i) {
        return false;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(InputStream inputStream, String str) throws XmlPullParserException, IOException {
        if (str != null && !StandardCharsets.UTF_8.name().equalsIgnoreCase(str)) {
            throw new UnsupportedOperationException();
        }
        FastDataInput fastDataInput = this.mIn;
        if (fastDataInput != null) {
            fastDataInput.release();
            this.mIn = null;
        }
        this.mIn = obtainFastDataInput(inputStream);
        int i = 0;
        this.mCurrentToken = 0;
        this.mCurrentDepth = 0;
        this.mCurrentName = null;
        this.mCurrentText = null;
        this.mAttributeCount = 0;
        this.mAttributes = new Attribute[8];
        while (true) {
            Attribute[] attributeArr = this.mAttributes;
            if (i >= attributeArr.length) {
                break;
            }
            attributeArr[i] = new Attribute();
            i++;
        }
        try {
            byte[] bArr = new byte[4];
            this.mIn.readFully(bArr);
            if (!Arrays.equals(bArr, BinaryXmlSerializer.PROTOCOL_MAGIC_VERSION_0)) {
                throw new IOException("Unexpected magic " + bytesToHexString(bArr));
            }
            if (peekNextExternalToken() == 0) {
                consumeToken();
            }
        } catch (IOException e) {
            throw new XmlPullParserException(e.toString());
        }
    }

    protected FastDataInput obtainFastDataInput(InputStream inputStream) {
        return FastDataInput.obtain(inputStream);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(Reader reader) throws XmlPullParserException {
        throw new UnsupportedOperationException();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0020, code lost:
    
        return r0;
     */
    @Override // org.xmlpull.v1.XmlPullParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int next() throws XmlPullParserException, IOException {
        while (true) {
            int iNextToken = nextToken();
            if (iNextToken == 1 || iNextToken == 2 || iNextToken == 3) {
                break;
            }
            if (iNextToken == 4) {
                consumeAdditionalText();
                String str = this.mCurrentText;
                if (str != null && str.length() != 0) {
                    return 4;
                }
            }
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextToken() throws XmlPullParserException, IOException {
        int iPeekNextExternalToken;
        if (this.mCurrentToken == 3) {
            this.mCurrentDepth--;
        }
        try {
            iPeekNextExternalToken = peekNextExternalToken();
            consumeToken();
        } catch (EOFException unused) {
            iPeekNextExternalToken = 1;
        }
        if (iPeekNextExternalToken == 2) {
            peekNextExternalToken();
            this.mCurrentDepth++;
        }
        this.mCurrentToken = iPeekNextExternalToken;
        return iPeekNextExternalToken;
    }

    private int peekNextExternalToken() throws XmlPullParserException, IOException {
        while (true) {
            int iPeekNextToken = peekNextToken();
            if (iPeekNextToken != 15) {
                return iPeekNextToken;
            }
            consumeToken();
        }
    }

    private int peekNextToken() throws IOException {
        return this.mIn.peekByte() & 15;
    }

    private void consumeToken() throws XmlPullParserException, IOException {
        byte b = this.mIn.readByte();
        int i = b & 15;
        int i2 = b & 240;
        if (i == 15) {
            Attribute attributeObtainAttribute = obtainAttribute();
            attributeObtainAttribute.name = this.mIn.readInternedUTF();
            attributeObtainAttribute.type = i2;
            switch (i2) {
                case 16:
                case 192:
                case 208:
                    return;
                case 32:
                    attributeObtainAttribute.valueString = this.mIn.readUTF();
                    return;
                case 48:
                    attributeObtainAttribute.valueString = this.mIn.readInternedUTF();
                    return;
                case 64:
                case 80:
                    byte[] bArr = new byte[this.mIn.readUnsignedShort()];
                    this.mIn.readFully(bArr);
                    attributeObtainAttribute.valueBytes = bArr;
                    return;
                case 96:
                case 112:
                    attributeObtainAttribute.valueInt = this.mIn.readInt();
                    return;
                case 128:
                case 144:
                    attributeObtainAttribute.valueLong = this.mIn.readLong();
                    return;
                case 160:
                    attributeObtainAttribute.valueFloat = this.mIn.readFloat();
                    return;
                case 176:
                    attributeObtainAttribute.valueDouble = this.mIn.readDouble();
                    return;
                default:
                    throw new IOException("Unexpected data type " + i2);
            }
        }
        switch (i) {
            case 0:
                this.mCurrentName = null;
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 1:
                this.mCurrentName = null;
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 2:
                this.mCurrentName = this.mIn.readInternedUTF();
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 3:
                this.mCurrentName = this.mIn.readInternedUTF();
                this.mCurrentText = null;
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                this.mCurrentName = null;
                this.mCurrentText = this.mIn.readUTF();
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            case 6:
                String utf = this.mIn.readUTF();
                this.mCurrentName = utf;
                this.mCurrentText = resolveEntity(utf);
                if (this.mAttributeCount > 0) {
                    resetAttributes();
                    return;
                }
                return;
            default:
                throw new IOException("Unknown token " + i + " with type " + i2);
        }
    }

    private void consumeAdditionalText() throws XmlPullParserException, IOException {
        String str = this.mCurrentText;
        while (true) {
            int iPeekNextExternalToken = peekNextExternalToken();
            if (iPeekNextExternalToken == 4 || iPeekNextExternalToken == 5 || iPeekNextExternalToken == 6) {
                consumeToken();
                str = str + this.mCurrentText;
            } else if (iPeekNextExternalToken == 8 || iPeekNextExternalToken == 9) {
                consumeToken();
            } else {
                this.mCurrentToken = 4;
                this.mCurrentName = null;
                this.mCurrentText = str;
                return;
            }
        }
    }

    static String resolveEntity(String str) throws XmlPullParserException {
        str.hashCode();
        switch (str) {
            case "gt":
                return ">";
            case "lt":
                return "<";
            case "amp":
                return "&";
            case "apos":
                return "'";
            case "quot":
                return "\"";
            default:
                if (str.length() > 1 && str.charAt(0) == '#') {
                    return new String(new char[]{(char) Integer.parseInt(str.substring(1))});
                }
                throw new XmlPullParserException("Unknown entity " + str);
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void require(int i, String str, String str2) throws XmlPullParserException, IOException {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        if (this.mCurrentToken != i || !Objects.equals(this.mCurrentName, str2)) {
            throw new XmlPullParserException(getPositionDescription());
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String nextText() throws XmlPullParserException, IOException {
        if (getEventType() != 2) {
            throw new XmlPullParserException(getPositionDescription());
        }
        int next = next();
        if (next != 4) {
            if (next == 3) {
                return "";
            }
            throw new XmlPullParserException(getPositionDescription());
        }
        String text = getText();
        if (next() == 3) {
            return text;
        }
        throw new XmlPullParserException(getPositionDescription());
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextTag() throws XmlPullParserException, IOException {
        int next = next();
        if (next == 4 && isWhitespace()) {
            next = next();
        }
        if (next == 2 || next == 3) {
            return next;
        }
        throw new XmlPullParserException(getPositionDescription());
    }

    private Attribute obtainAttribute() {
        int i = this.mAttributeCount;
        Attribute[] attributeArr = this.mAttributes;
        if (i == attributeArr.length) {
            int length = attributeArr.length;
            int i2 = (length >> 1) + length;
            this.mAttributes = (Attribute[]) Arrays.copyOf(attributeArr, i2);
            while (length < i2) {
                this.mAttributes[length] = new Attribute();
                length++;
            }
        }
        Attribute[] attributeArr2 = this.mAttributes;
        int i3 = this.mAttributeCount;
        this.mAttributeCount = i3 + 1;
        return attributeArr2[i3];
    }

    private void resetAttributes() {
        for (int i = 0; i < this.mAttributeCount; i++) {
            this.mAttributes[i].reset();
        }
        this.mAttributeCount = 0;
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public int getAttributeIndex(String str, String str2) {
        if (str != null && !str.isEmpty()) {
            throw illegalNamespace();
        }
        for (int i = 0; i < this.mAttributeCount; i++) {
            if (Objects.equals(this.mAttributes[i].name, str2)) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(String str, String str2) {
        int attributeIndex = getAttributeIndex(str, str2);
        if (attributeIndex != -1) {
            return this.mAttributes[attributeIndex].getValueString();
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(int i) {
        return this.mAttributes[i].getValueString();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public byte[] getAttributeBytesHex(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueBytesHex();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public byte[] getAttributeBytesBase64(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueBytesBase64();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public int getAttributeInt(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueInt();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public int getAttributeIntHex(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueIntHex();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public long getAttributeLong(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueLong();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public long getAttributeLongHex(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueLongHex();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public float getAttributeFloat(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueFloat();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public double getAttributeDouble(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueDouble();
    }

    @Override // com.android.modules.utils.TypedXmlPullParser
    public boolean getAttributeBoolean(int i) throws XmlPullParserException {
        return this.mAttributes[i].getValueBoolean();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getText() {
        return this.mCurrentText;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public char[] getTextCharacters(int[] iArr) {
        char[] charArray = this.mCurrentText.toCharArray();
        iArr[0] = 0;
        iArr[1] = charArray.length;
        return charArray;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getInputEncoding() {
        return StandardCharsets.UTF_8.name();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        return this.mCurrentDepth;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPositionDescription() {
        return "Token " + this.mCurrentToken + " at depth " + this.mCurrentDepth;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() throws XmlPullParserException {
        int i = this.mCurrentToken;
        if (i == 4 || i == 5) {
            return !TextUtils.isGraphic(this.mCurrentText);
        }
        if (i == 7) {
            return true;
        }
        throw new XmlPullParserException("Not applicable for token " + this.mCurrentToken);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace() {
        int i = this.mCurrentToken;
        if (i == 2 || i == 3) {
            return "";
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getName() {
        return this.mCurrentName;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() throws XmlPullParserException {
        if (this.mCurrentToken == 2) {
            try {
                return peekNextExternalToken() == 3;
            } catch (IOException e) {
                throw new XmlPullParserException(e.toString());
            }
        }
        throw new XmlPullParserException("Not at START_TAG");
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getAttributeCount() {
        return this.mAttributeCount;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeNamespace(int i) {
        return "";
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeName(int i) {
        return this.mAttributes[i].name;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeType(int i) {
        return "CDATA";
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getEventType() throws XmlPullParserException {
        return this.mCurrentToken;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespacePrefix(int i) throws XmlPullParserException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespaceUri(int i) throws XmlPullParserException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setFeature(String str, boolean z) throws XmlPullParserException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean getFeature(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setProperty(String str, Object obj) throws XmlPullParserException {
        throw new UnsupportedOperationException();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public Object getProperty(String str) {
        throw new UnsupportedOperationException();
    }

    private static IllegalArgumentException illegalNamespace() {
        throw new IllegalArgumentException("Namespaces are not supported");
    }

    private static class Attribute {
        public String name;
        public int type;
        public byte[] valueBytes;
        public double valueDouble;
        public float valueFloat;
        public int valueInt;
        public long valueLong;
        public String valueString;

        private Attribute() {
        }

        public void reset() {
            this.name = null;
            this.valueString = null;
            this.valueBytes = null;
        }

        public String getValueString() {
            switch (this.type) {
                case 32:
                case 48:
                    return this.valueString;
                case 64:
                    return BinaryXmlPullParser.bytesToHexString(this.valueBytes);
                case 80:
                    return Base64.encodeToString(this.valueBytes, 2);
                case 96:
                    return Integer.toString(this.valueInt);
                case 112:
                    return Integer.toString(this.valueInt, 16);
                case 128:
                    return Long.toString(this.valueLong);
                case 144:
                    return Long.toString(this.valueLong, 16);
                case 160:
                    return Float.toString(this.valueFloat);
                case 176:
                    return Double.toString(this.valueDouble);
                case 192:
                    return "true";
                case 208:
                    return "false";
                default:
                    return null;
            }
        }

        public byte[] getValueBytesHex() throws XmlPullParserException {
            int i = this.type;
            if (i == 16) {
                return null;
            }
            if (i != 32 && i != 48) {
                if (i == 64 || i == 80) {
                    return this.valueBytes;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return BinaryXmlPullParser.hexStringToBytes(this.valueString);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public byte[] getValueBytesBase64() throws XmlPullParserException {
            int i = this.type;
            if (i == 16) {
                return null;
            }
            if (i != 32 && i != 48) {
                if (i == 64 || i == 80) {
                    return this.valueBytes;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return Base64.decode(this.valueString, 2);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public int getValueInt() throws XmlPullParserException {
            int i = this.type;
            if (i != 32 && i != 48) {
                if (i == 96 || i == 112) {
                    return this.valueInt;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return Integer.parseInt(this.valueString);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public int getValueIntHex() throws XmlPullParserException {
            int i = this.type;
            if (i != 32 && i != 48) {
                if (i == 96 || i == 112) {
                    return this.valueInt;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return Integer.parseInt(this.valueString, 16);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public long getValueLong() throws XmlPullParserException {
            int i = this.type;
            if (i != 32 && i != 48) {
                if (i == 128 || i == 144) {
                    return this.valueLong;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return Long.parseLong(this.valueString);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public long getValueLongHex() throws XmlPullParserException {
            int i = this.type;
            if (i != 32 && i != 48) {
                if (i == 128 || i == 144) {
                    return this.valueLong;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return Long.parseLong(this.valueString, 16);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public float getValueFloat() throws XmlPullParserException {
            int i = this.type;
            if (i != 32 && i != 48) {
                if (i == 160) {
                    return this.valueFloat;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return Float.parseFloat(this.valueString);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public double getValueDouble() throws XmlPullParserException {
            int i = this.type;
            if (i != 32 && i != 48) {
                if (i == 176) {
                    return this.valueDouble;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            try {
                return Double.parseDouble(this.valueString);
            } catch (Exception e) {
                throw new XmlPullParserException("Invalid attribute " + this.name + ": " + e);
            }
        }

        public boolean getValueBoolean() throws XmlPullParserException {
            int i = this.type;
            if (i != 32 && i != 48) {
                if (i == 192) {
                    return true;
                }
                if (i == 208) {
                    return false;
                }
                throw new XmlPullParserException("Invalid conversion from " + this.type);
            }
            if ("true".equalsIgnoreCase(this.valueString)) {
                return true;
            }
            if ("false".equalsIgnoreCase(this.valueString)) {
                return false;
            }
            throw new XmlPullParserException("Invalid attribute " + this.name + ": " + this.valueString);
        }
    }

    private static int toByte(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c >= 'a' && c <= 'f') {
            return c - 'W';
        }
        throw new IllegalArgumentException("Invalid hex char '" + c + "'");
    }

    static String bytesToHexString(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        int i = 0;
        for (byte b : bArr) {
            int i2 = i + 1;
            char[] cArr2 = HEX_DIGITS;
            cArr[i] = cArr2[(b >>> 4) & 15];
            i += 2;
            cArr[i2] = cArr2[b & 15];
        }
        return new String(cArr);
    }

    static byte[] hexStringToBytes(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex length " + length);
        }
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((toByte(str.charAt(i)) << 4) | toByte(str.charAt(i + 1)));
        }
        return bArr;
    }
}
