package com.android.server.compat.overrides;

import org.xmlpull.v1.XmlPullParser;

public final class OverrideValue {
    public Boolean enabled;
    public String packageName;

    public static OverrideValue read(XmlPullParser xmlPullParser) {
        OverrideValue overrideValue = new OverrideValue();
        String attributeValue = xmlPullParser.getAttributeValue(null, "packageName");
        if (attributeValue != null) {
            overrideValue.packageName = attributeValue;
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "enabled");
        if (attributeValue2 != null) {
            overrideValue.enabled = Boolean.valueOf(Boolean.parseBoolean(attributeValue2));
        }
        XmlParser.skip(xmlPullParser);
        return overrideValue;
    }

    public final void write(XmlWriter xmlWriter) {
        xmlWriter.print("<override-value");
        if (this.packageName != null) {
            xmlWriter.print(" packageName=\"");
            xmlWriter.print(this.packageName);
            xmlWriter.print("\"");
        }
        if (this.enabled != null) {
            xmlWriter.print(" enabled=\"");
            Boolean bool = this.enabled;
            xmlWriter.print(Boolean.toString(bool == null ? false : bool.booleanValue()));
            xmlWriter.print("\"");
        }
        xmlWriter.print(">\n");
        xmlWriter.print("</override-value>\n");
    }
}
