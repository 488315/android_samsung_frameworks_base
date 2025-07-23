package android.util;

import com.android.internal.util.XmlUtils;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes4.dex */
class XmlPullAttributes implements AttributeSet {
    XmlPullParser mParser;

    @Override // android.util.AttributeSet
    public int getAttributeNameResource(int i) {
        return 0;
    }

    public XmlPullAttributes(XmlPullParser xmlPullParser) {
        this.mParser = xmlPullParser;
    }

    @Override // android.util.AttributeSet
    public int getAttributeCount() {
        return this.mParser.getAttributeCount();
    }

    @Override // android.util.AttributeSet
    public String getAttributeNamespace(int i) {
        return this.mParser.getAttributeNamespace(i);
    }

    @Override // android.util.AttributeSet
    public String getAttributeName(int i) {
        return this.mParser.getAttributeName(i);
    }

    @Override // android.util.AttributeSet
    public String getAttributeValue(int i) {
        return this.mParser.getAttributeValue(i);
    }

    @Override // android.util.AttributeSet
    public String getAttributeValue(String str, String str2) {
        return this.mParser.getAttributeValue(str, str2);
    }

    @Override // android.util.AttributeSet
    public String getPositionDescription() {
        return this.mParser.getPositionDescription();
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(String str, String str2, String[] strArr, int i) {
        return XmlUtils.convertValueToList(getAttributeValue(str, str2), strArr, i);
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(String str, String str2, boolean z) {
        return XmlUtils.convertValueToBoolean(getAttributeValue(str, str2), z);
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(String str, String str2, int i) {
        return XmlUtils.convertValueToInt(getAttributeValue(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(String str, String str2, int i) {
        return XmlUtils.convertValueToInt(getAttributeValue(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(String str, String str2, int i) {
        return XmlUtils.convertValueToUnsignedInt(getAttributeValue(str, str2), i);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(String str, String str2, float f) {
        String attributeValue = getAttributeValue(str, str2);
        return attributeValue != null ? Float.parseFloat(attributeValue) : f;
    }

    @Override // android.util.AttributeSet
    public int getAttributeListValue(int i, String[] strArr, int i2) {
        return XmlUtils.convertValueToList(getAttributeValue(i), strArr, i2);
    }

    @Override // android.util.AttributeSet
    public boolean getAttributeBooleanValue(int i, boolean z) {
        return XmlUtils.convertValueToBoolean(getAttributeValue(i), z);
    }

    @Override // android.util.AttributeSet
    public int getAttributeResourceValue(int i, int i2) {
        return XmlUtils.convertValueToInt(getAttributeValue(i), i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeIntValue(int i, int i2) {
        return XmlUtils.convertValueToInt(getAttributeValue(i), i2);
    }

    @Override // android.util.AttributeSet
    public int getAttributeUnsignedIntValue(int i, int i2) {
        return XmlUtils.convertValueToUnsignedInt(getAttributeValue(i), i2);
    }

    @Override // android.util.AttributeSet
    public float getAttributeFloatValue(int i, float f) {
        String attributeValue = getAttributeValue(i);
        return attributeValue != null ? Float.parseFloat(attributeValue) : f;
    }

    @Override // android.util.AttributeSet
    public String getIdAttribute() {
        return getAttributeValue(null, "id");
    }

    @Override // android.util.AttributeSet
    public String getClassAttribute() {
        return getAttributeValue(null, "class");
    }

    @Override // android.util.AttributeSet
    public int getIdAttributeResourceValue(int i) {
        return getAttributeResourceValue(null, "id", i);
    }

    @Override // android.util.AttributeSet
    public int getStyleAttribute() {
        return getAttributeResourceValue(null, "style", 0);
    }
}
