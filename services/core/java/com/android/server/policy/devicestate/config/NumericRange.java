package com.android.server.policy.devicestate.config;

import java.math.BigDecimal;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes3.dex */
public class NumericRange {
  public BigDecimal maxInclusive_optional;
  public BigDecimal max_optional;
  public BigDecimal minInclusive_optional;
  public BigDecimal min_optional;

  public BigDecimal getMin_optional() {
    return this.min_optional;
  }

  public void setMin_optional(BigDecimal bigDecimal) {
    this.min_optional = bigDecimal;
  }

  public BigDecimal getMinInclusive_optional() {
    return this.minInclusive_optional;
  }

  public void setMinInclusive_optional(BigDecimal bigDecimal) {
    this.minInclusive_optional = bigDecimal;
  }

  public BigDecimal getMax_optional() {
    return this.max_optional;
  }

  public void setMax_optional(BigDecimal bigDecimal) {
    this.max_optional = bigDecimal;
  }

  public BigDecimal getMaxInclusive_optional() {
    return this.maxInclusive_optional;
  }

  public void setMaxInclusive_optional(BigDecimal bigDecimal) {
    this.maxInclusive_optional = bigDecimal;
  }

  public static NumericRange read(XmlPullParser xmlPullParser) {
    int next;
    NumericRange numericRange = new NumericRange();
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        String name = xmlPullParser.getName();
        if (name.equals("min")) {
          numericRange.setMin_optional(new BigDecimal(XmlParser.readText(xmlPullParser)));
        } else if (name.equals("min-inclusive")) {
          numericRange.setMinInclusive_optional(new BigDecimal(XmlParser.readText(xmlPullParser)));
        } else if (name.equals("max")) {
          numericRange.setMax_optional(new BigDecimal(XmlParser.readText(xmlPullParser)));
        } else if (name.equals("max-inclusive")) {
          numericRange.setMaxInclusive_optional(new BigDecimal(XmlParser.readText(xmlPullParser)));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return numericRange;
    }
    throw new DatatypeConfigurationException("NumericRange is not closed");
  }
}
