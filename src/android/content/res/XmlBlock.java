package android.content.res;

import android.system.OsConstants;
import android.util.TypedValue;
import com.android.internal.pm.pkg.parsing.ParsingPackageUtils;
import com.android.internal.util.XmlUtils;
import dalvik.annotation.optimization.CriticalNative;
import dalvik.annotation.optimization.FastNative;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class XmlBlock implements AutoCloseable {
    public static final String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    private static final boolean DEBUG = false;
    private static final int ERROR_BAD_DOCUMENT = -OsConstants.EINVAL;
    private static final int ERROR_NULL_DOCUMENT = -2147483640;
    private final AssetManager mAssets;
    private long mNative;
    private boolean mOpen;
    private int mOpenCount;
    final StringBlock mStrings;
    private final boolean mUsesFeatureFlags;

    private static final native long nativeCreate(byte[] bArr, int i, int i2);

    private static final native long nativeCreateParseState(long j, int i);

    private static final native void nativeDestroy(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void nativeDestroyParseState(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetAttributeCount(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetAttributeData(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetAttributeDataType(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @FastNative
    public static native int nativeGetAttributeIndex(long j, String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetAttributeName(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetAttributeNamespace(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetAttributeResource(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetAttributeStringValue(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetClassAttribute(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetIdAttribute(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetLineNumber(long j);

    @CriticalNative
    static final native int nativeGetName(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetNamespace(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetSourceResId(long j);

    private static final native long nativeGetStringBlock(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetStyleAttribute(long j);

    /* JADX INFO: Access modifiers changed from: private */
    @CriticalNative
    public static final native int nativeGetText(long j);

    @CriticalNative
    static final native int nativeNext(long j);

    public XmlBlock(byte[] bArr) {
        this.mOpen = true;
        this.mOpenCount = 1;
        this.mAssets = null;
        this.mNative = nativeCreate(bArr, 0, bArr.length);
        this.mStrings = new StringBlock(nativeGetStringBlock(this.mNative), false);
        this.mUsesFeatureFlags = true;
    }

    public XmlBlock(byte[] bArr, int i, int i2) {
        this.mOpen = true;
        this.mOpenCount = 1;
        this.mAssets = null;
        this.mNative = nativeCreate(bArr, i, i2);
        this.mStrings = new StringBlock(nativeGetStringBlock(this.mNative), false);
        this.mUsesFeatureFlags = true;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                decOpenCountLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void decOpenCountLocked() {
        int i = this.mOpenCount - 1;
        this.mOpenCount = i;
        if (i == 0) {
            this.mStrings.close();
            nativeDestroy(this.mNative);
            this.mNative = 0L;
            AssetManager assetManager = this.mAssets;
            if (assetManager != null) {
                assetManager.xmlBlockGone(hashCode());
            }
        }
    }

    public XmlResourceParser newParser() {
        return newParser(0);
    }

    public XmlResourceParser newParser(int i) {
        synchronized (this) {
            if (this.mNative == 0) {
                return null;
            }
            return new Parser(nativeCreateParseState(this.mNative, i), this);
        }
    }

    public XmlResourceParser newParser(int i, Validator validator) throws Throwable {
        synchronized (this) {
            try {
                try {
                    if (this.mNative == 0) {
                        return null;
                    }
                    return new Parser(this, nativeCreateParseState(this.mNative, i), this, validator);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public final class Parser implements XmlResourceParser {
        private final XmlBlock mBlock;
        private boolean mDecNextDepth;
        private int mDepth;
        private int mEventType;
        long mParseState;
        private boolean mStarted;
        Validator mValidator;

        private static boolean useLayoutReadwrite$ravenwood() {
            return false;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getColumnNumber() {
            return -1;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getInputEncoding() {
            return null;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public Object getProperty(String str) {
            return null;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean isAttributeDefault(int i) {
            return false;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean isEmptyElementTag() throws XmlPullParserException {
            return false;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean isWhitespace() throws XmlPullParserException {
            return false;
        }

        Parser(long j, XmlBlock xmlBlock) {
            this.mStarted = false;
            this.mDecNextDepth = false;
            this.mDepth = 0;
            this.mEventType = 0;
            this.mParseState = j;
            this.mBlock = xmlBlock;
            xmlBlock.mOpenCount++;
        }

        Parser(XmlBlock xmlBlock, long j, XmlBlock xmlBlock2, Validator validator) {
            this(j, xmlBlock2);
            this.mValidator = validator;
        }

        public int getSourceResId() {
            return XmlBlock.nativeGetSourceResId(this.mParseState);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setFeature(String str, boolean z) throws XmlPullParserException {
            if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(str) && z) {
                return;
            }
            if ("http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes".equals(str) && z) {
                return;
            }
            throw new XmlPullParserException("Unsupported feature: " + str);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public boolean getFeature(String str) {
            return "http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(str) || "http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes".equals(str);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setProperty(String str, Object obj) throws XmlPullParserException {
            throw new XmlPullParserException("setProperty() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setInput(Reader reader) throws XmlPullParserException {
            throw new XmlPullParserException("setInput() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void setInput(InputStream inputStream, String str) throws XmlPullParserException {
            throw new XmlPullParserException("setInput() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
            throw new XmlPullParserException("defineEntityReplacementText() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getNamespacePrefix(int i) throws XmlPullParserException {
            throw new XmlPullParserException("getNamespacePrefix() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getNamespace(String str) {
            throw new RuntimeException("getNamespace() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getNamespaceCount(int i) throws XmlPullParserException {
            throw new XmlPullParserException("getNamespaceCount() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public String getPositionDescription() {
            return "Binary XML file line #" + getLineNumber();
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getNamespaceUri(int i) throws XmlPullParserException {
            throw new XmlPullParserException("getNamespaceUri() not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getDepth() {
            return this.mDepth;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getText() {
            int iNativeGetText = XmlBlock.nativeGetText(this.mParseState);
            if (iNativeGetText >= 0) {
                return getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetText));
            }
            return null;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getLineNumber() {
            int iNativeGetLineNumber = XmlBlock.nativeGetLineNumber(this.mParseState);
            if (iNativeGetLineNumber != -2147483640) {
                return iNativeGetLineNumber;
            }
            throw new NullPointerException("Null document");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int getEventType() throws XmlPullParserException {
            return this.mEventType;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getPrefix() {
            throw new RuntimeException("getPrefix not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public char[] getTextCharacters(int[] iArr) {
            String text = getText();
            if (text == null) {
                return null;
            }
            iArr[0] = 0;
            iArr[1] = text.length();
            char[] cArr = new char[text.length()];
            text.getChars(0, text.length(), cArr, 0);
            return cArr;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getNamespace() {
            int iNativeGetNamespace = XmlBlock.nativeGetNamespace(this.mParseState);
            return iNativeGetNamespace >= 0 ? getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetNamespace)) : "";
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getName() {
            int iNativeGetName = XmlBlock.nativeGetName(this.mParseState);
            if (iNativeGetName >= 0) {
                return getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetName));
            }
            return null;
        }

        @Override // android.content.res.XmlResourceParser, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public String getAttributeNamespace(int i) {
            int iNativeGetAttributeNamespace = XmlBlock.nativeGetAttributeNamespace(this.mParseState, i);
            if (iNativeGetAttributeNamespace == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeNamespace >= 0) {
                return getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetAttributeNamespace));
            }
            if (iNativeGetAttributeNamespace == -1) {
                return "";
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public String getAttributeName(int i) {
            int iNativeGetAttributeName = XmlBlock.nativeGetAttributeName(this.mParseState, i);
            if (iNativeGetAttributeName == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeName >= 0) {
                return getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetAttributeName));
            }
            throw new IndexOutOfBoundsException(String.valueOf(i));
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getAttributePrefix(int i) {
            throw new RuntimeException("getAttributePrefix not supported");
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public int getAttributeCount() {
            if (this.mEventType != 2) {
                return -1;
            }
            int iNativeGetAttributeCount = XmlBlock.nativeGetAttributeCount(this.mParseState);
            if (iNativeGetAttributeCount != -2147483640) {
                return iNativeGetAttributeCount;
            }
            throw new NullPointerException("Null document");
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public String getAttributeValue(int i) {
            int iNativeGetAttributeStringValue = XmlBlock.nativeGetAttributeStringValue(this.mParseState, i);
            if (iNativeGetAttributeStringValue == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeStringValue >= 0) {
                return getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetAttributeStringValue));
            }
            int iNativeGetAttributeDataType = XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (iNativeGetAttributeDataType == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeDataType == 0) {
                throw new IndexOutOfBoundsException(String.valueOf(i));
            }
            int iNativeGetAttributeData = XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (iNativeGetAttributeData == -2147483640) {
                throw new NullPointerException("Null document");
            }
            return TypedValue.coerceToString(iNativeGetAttributeDataType, iNativeGetAttributeData);
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String getAttributeType(int i) {
            return "CDATA";
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int nextToken() throws XmlPullParserException, IOException {
            return next();
        }

        @Override // org.xmlpull.v1.XmlPullParser, android.util.AttributeSet
        public String getAttributeValue(String str, String str2) {
            int iNativeGetAttributeIndex = XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            if (iNativeGetAttributeIndex < 0) {
                return null;
            }
            String attributeValue = getAttributeValue(iNativeGetAttributeIndex);
            Validator validator = this.mValidator;
            if (validator != null) {
                validator.validateStrAttr(this, str2, attributeValue);
            }
            return attributeValue;
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public int next() throws XmlPullParserException, IOException {
            int i = 1;
            if (!this.mStarted) {
                this.mStarted = true;
                return 0;
            }
            long j = this.mParseState;
            if (j == 0) {
                return 1;
            }
            int iNativeNext = XmlBlock.nativeNext(j);
            if (iNativeNext == XmlBlock.ERROR_BAD_DOCUMENT) {
                throw new XmlPullParserException("Corrupt XML binary file");
            }
            if (useLayoutReadwrite() && XmlBlock.this.mUsesFeatureFlags && iNativeNext == 2 && ParsingPackageUtils.getAconfigFlags().skipCurrentElement(null, this)) {
                while (i > 0) {
                    int iNativeNext2 = XmlBlock.nativeNext(this.mParseState);
                    if (iNativeNext2 == XmlBlock.ERROR_BAD_DOCUMENT) {
                        throw new XmlPullParserException("Corrupt XML binary file");
                    }
                    if (iNativeNext2 == 2) {
                        i++;
                    } else if (iNativeNext2 == 3) {
                        i--;
                    }
                }
                return next();
            }
            if (this.mDecNextDepth) {
                this.mDepth--;
                this.mDecNextDepth = false;
            }
            if (iNativeNext == 2) {
                this.mDepth++;
            } else if (iNativeNext == 3) {
                this.mDecNextDepth = true;
            }
            this.mEventType = iNativeNext;
            Validator validator = this.mValidator;
            if (validator != null) {
                validator.validate(this);
            }
            if (iNativeNext == 1) {
                close();
            }
            return iNativeNext;
        }

        private static boolean useLayoutReadwrite() {
            return Flags.layoutReadwriteFlags();
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public void require(int i, String str, String str2) throws XmlPullParserException, IOException {
            if (i == getEventType() && ((str == null || str.equals(getNamespace())) && (str2 == null || str2.equals(getName())))) {
                return;
            }
            throw new XmlPullParserException("expected " + TYPES[i] + getPositionDescription());
        }

        @Override // org.xmlpull.v1.XmlPullParser
        public String nextText() throws XmlPullParserException, IOException {
            if (getEventType() != 2) {
                throw new XmlPullParserException(getPositionDescription() + ": parser must be on START_TAG to read next text", this, null);
            }
            int next = next();
            if (next != 4) {
                if (next == 3) {
                    return "";
                }
                throw new XmlPullParserException(getPositionDescription() + ": parser must be on START_TAG or TEXT to read text", this, null);
            }
            String text = getText();
            if (next() == 3) {
                return text;
            }
            throw new XmlPullParserException(getPositionDescription() + ": event TEXT it must be immediately followed by END_TAG", this, null);
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
            throw new XmlPullParserException(getPositionDescription() + ": expected start or end tag", this, null);
        }

        @Override // android.util.AttributeSet
        public int getAttributeNameResource(int i) {
            int iNativeGetAttributeResource = XmlBlock.nativeGetAttributeResource(this.mParseState, i);
            if (iNativeGetAttributeResource != -2147483640) {
                return iNativeGetAttributeResource;
            }
            throw new NullPointerException("Null document");
        }

        @Override // android.util.AttributeSet
        public int getAttributeListValue(String str, String str2, String[] strArr, int i) {
            int iNativeGetAttributeIndex = XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            return iNativeGetAttributeIndex >= 0 ? getAttributeListValue(iNativeGetAttributeIndex, strArr, i) : i;
        }

        @Override // android.util.AttributeSet
        public boolean getAttributeBooleanValue(String str, String str2, boolean z) {
            int iNativeGetAttributeIndex = XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            return iNativeGetAttributeIndex >= 0 ? getAttributeBooleanValue(iNativeGetAttributeIndex, z) : z;
        }

        @Override // android.util.AttributeSet
        public int getAttributeResourceValue(String str, String str2, int i) {
            int iNativeGetAttributeIndex = XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            return iNativeGetAttributeIndex >= 0 ? getAttributeResourceValue(iNativeGetAttributeIndex, i) : i;
        }

        @Override // android.util.AttributeSet
        public int getAttributeIntValue(String str, String str2, int i) {
            int iNativeGetAttributeIndex = XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            return iNativeGetAttributeIndex >= 0 ? getAttributeIntValue(iNativeGetAttributeIndex, i) : i;
        }

        @Override // android.util.AttributeSet
        public int getAttributeUnsignedIntValue(String str, String str2, int i) {
            int iNativeGetAttributeIndex = XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            return iNativeGetAttributeIndex >= 0 ? getAttributeUnsignedIntValue(iNativeGetAttributeIndex, i) : i;
        }

        @Override // android.util.AttributeSet
        public float getAttributeFloatValue(String str, String str2, float f) {
            int iNativeGetAttributeIndex = XmlBlock.nativeGetAttributeIndex(this.mParseState, str, str2);
            return iNativeGetAttributeIndex >= 0 ? getAttributeFloatValue(iNativeGetAttributeIndex, f) : f;
        }

        @Override // android.util.AttributeSet
        public int getAttributeListValue(int i, String[] strArr, int i2) {
            int iNativeGetAttributeDataType = XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (iNativeGetAttributeDataType == -2147483640) {
                throw new NullPointerException("Null document");
            }
            int iNativeGetAttributeData = XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (iNativeGetAttributeData != -2147483640) {
                return iNativeGetAttributeDataType == 3 ? XmlUtils.convertValueToList(XmlBlock.this.mStrings.getSequence(iNativeGetAttributeData), strArr, i2) : iNativeGetAttributeData;
            }
            throw new NullPointerException("Null document");
        }

        @Override // android.util.AttributeSet
        public boolean getAttributeBooleanValue(int i, boolean z) {
            int iNativeGetAttributeDataType = XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (iNativeGetAttributeDataType == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeDataType < 16 || iNativeGetAttributeDataType > 31) {
                return z;
            }
            int iNativeGetAttributeData = XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (iNativeGetAttributeData != -2147483640) {
                return iNativeGetAttributeData != 0;
            }
            throw new NullPointerException("Null document");
        }

        @Override // android.util.AttributeSet
        public int getAttributeResourceValue(int i, int i2) {
            int iNativeGetAttributeDataType = XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (iNativeGetAttributeDataType == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeDataType != 1) {
                return i2;
            }
            int iNativeGetAttributeData = XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (iNativeGetAttributeData != -2147483640) {
                return iNativeGetAttributeData;
            }
            throw new NullPointerException("Null document");
        }

        @Override // android.util.AttributeSet
        public int getAttributeIntValue(int i, int i2) {
            int iNativeGetAttributeDataType = XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (iNativeGetAttributeDataType == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeDataType < 16 || iNativeGetAttributeDataType > 31) {
                return i2;
            }
            int iNativeGetAttributeData = XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (iNativeGetAttributeData != -2147483640) {
                return iNativeGetAttributeData;
            }
            throw new NullPointerException("Null document");
        }

        @Override // android.util.AttributeSet
        public int getAttributeUnsignedIntValue(int i, int i2) {
            int iNativeGetAttributeDataType = XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (iNativeGetAttributeDataType == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeDataType < 16 || iNativeGetAttributeDataType > 31) {
                return i2;
            }
            int iNativeGetAttributeData = XmlBlock.nativeGetAttributeData(this.mParseState, i);
            if (iNativeGetAttributeData != -2147483640) {
                return iNativeGetAttributeData;
            }
            throw new NullPointerException("Null document");
        }

        @Override // android.util.AttributeSet
        public float getAttributeFloatValue(int i, float f) {
            int iNativeGetAttributeDataType = XmlBlock.nativeGetAttributeDataType(this.mParseState, i);
            if (iNativeGetAttributeDataType == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetAttributeDataType == 4) {
                int iNativeGetAttributeData = XmlBlock.nativeGetAttributeData(this.mParseState, i);
                if (iNativeGetAttributeData == -2147483640) {
                    throw new NullPointerException("Null document");
                }
                return Float.intBitsToFloat(iNativeGetAttributeData);
            }
            throw new RuntimeException("not a float!");
        }

        @Override // android.util.AttributeSet
        public String getIdAttribute() {
            int iNativeGetIdAttribute = XmlBlock.nativeGetIdAttribute(this.mParseState);
            if (iNativeGetIdAttribute == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetIdAttribute >= 0) {
                return getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetIdAttribute));
            }
            return null;
        }

        @Override // android.util.AttributeSet
        public String getClassAttribute() {
            int iNativeGetClassAttribute = XmlBlock.nativeGetClassAttribute(this.mParseState);
            if (iNativeGetClassAttribute == -2147483640) {
                throw new NullPointerException("Null document");
            }
            if (iNativeGetClassAttribute >= 0) {
                return getSequenceString(XmlBlock.this.mStrings.getSequence(iNativeGetClassAttribute));
            }
            return null;
        }

        @Override // android.util.AttributeSet
        public int getIdAttributeResourceValue(int i) {
            return getAttributeResourceValue(null, "id", i);
        }

        @Override // android.util.AttributeSet
        public int getStyleAttribute() {
            int iNativeGetStyleAttribute = XmlBlock.nativeGetStyleAttribute(this.mParseState);
            if (iNativeGetStyleAttribute != -2147483640) {
                return iNativeGetStyleAttribute;
            }
            throw new NullPointerException("Null document");
        }

        private String getSequenceString(CharSequence charSequence) {
            if (charSequence == null) {
                throw new IllegalStateException("Retrieving a string from the StringPool of an XmlBlock should never fail");
            }
            return charSequence.toString();
        }

        @Override // android.content.res.XmlResourceParser, java.lang.AutoCloseable
        public void close() {
            synchronized (this.mBlock) {
                long j = this.mParseState;
                if (j != 0) {
                    XmlBlock.nativeDestroyParseState(j);
                    this.mParseState = 0L;
                    this.mBlock.decOpenCountLocked();
                }
            }
        }

        protected void finalize() throws Throwable {
            close();
        }

        final CharSequence getPooledString(int i) {
            return XmlBlock.this.mStrings.getSequence(i);
        }
    }

    protected void finalize() throws Throwable {
        close();
    }

    XmlBlock(AssetManager assetManager, long j, boolean z) {
        this.mOpen = true;
        this.mOpenCount = 1;
        this.mAssets = assetManager;
        this.mNative = j;
        this.mStrings = new StringBlock(nativeGetStringBlock(j), false);
        this.mUsesFeatureFlags = z;
    }
}
