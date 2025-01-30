package com.android.server.compat.overrides;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ChangeOverrides {
  public Long changeId;
  public Deferred deferred;
  public Raw raw;
  public Validated validated;

  public class Validated {
    public List overrideValue;

    public List getOverrideValue() {
      if (this.overrideValue == null) {
        this.overrideValue = new ArrayList();
      }
      return this.overrideValue;
    }

    public static Validated read(XmlPullParser xmlPullParser) {
      int next;
      Validated validated = new Validated();
      xmlPullParser.getDepth();
      while (true) {
        next = xmlPullParser.next();
        if (next == 1 || next == 3) {
          break;
        }
        if (xmlPullParser.getEventType() == 2) {
          if (xmlPullParser.getName().equals("override-value")) {
            validated.getOverrideValue().add(OverrideValue.read(xmlPullParser));
          } else {
            XmlParser.skip(xmlPullParser);
          }
        }
      }
      if (next == 3) {
        return validated;
      }
      throw new DatatypeConfigurationException("ChangeOverrides.Validated is not closed");
    }

    public void write(XmlWriter xmlWriter, String str) {
      xmlWriter.print("<" + str);
      xmlWriter.print(">\n");
      xmlWriter.increaseIndent();
      Iterator it = getOverrideValue().iterator();
      while (it.hasNext()) {
        ((OverrideValue) it.next()).write(xmlWriter, "override-value");
      }
      xmlWriter.decreaseIndent();
      xmlWriter.print("</" + str + ">\n");
    }
  }

  public class Deferred {
    public List overrideValue;

    public List getOverrideValue() {
      if (this.overrideValue == null) {
        this.overrideValue = new ArrayList();
      }
      return this.overrideValue;
    }

    public static Deferred read(XmlPullParser xmlPullParser) {
      int next;
      Deferred deferred = new Deferred();
      xmlPullParser.getDepth();
      while (true) {
        next = xmlPullParser.next();
        if (next == 1 || next == 3) {
          break;
        }
        if (xmlPullParser.getEventType() == 2) {
          if (xmlPullParser.getName().equals("override-value")) {
            deferred.getOverrideValue().add(OverrideValue.read(xmlPullParser));
          } else {
            XmlParser.skip(xmlPullParser);
          }
        }
      }
      if (next == 3) {
        return deferred;
      }
      throw new DatatypeConfigurationException("ChangeOverrides.Deferred is not closed");
    }

    public void write(XmlWriter xmlWriter, String str) {
      xmlWriter.print("<" + str);
      xmlWriter.print(">\n");
      xmlWriter.increaseIndent();
      Iterator it = getOverrideValue().iterator();
      while (it.hasNext()) {
        ((OverrideValue) it.next()).write(xmlWriter, "override-value");
      }
      xmlWriter.decreaseIndent();
      xmlWriter.print("</" + str + ">\n");
    }
  }

  public class Raw {
    public List rawOverrideValue;

    public List getRawOverrideValue() {
      if (this.rawOverrideValue == null) {
        this.rawOverrideValue = new ArrayList();
      }
      return this.rawOverrideValue;
    }

    public static Raw read(XmlPullParser xmlPullParser) {
      int next;
      Raw raw = new Raw();
      xmlPullParser.getDepth();
      while (true) {
        next = xmlPullParser.next();
        if (next == 1 || next == 3) {
          break;
        }
        if (xmlPullParser.getEventType() == 2) {
          if (xmlPullParser.getName().equals("raw-override-value")) {
            raw.getRawOverrideValue().add(RawOverrideValue.read(xmlPullParser));
          } else {
            XmlParser.skip(xmlPullParser);
          }
        }
      }
      if (next == 3) {
        return raw;
      }
      throw new DatatypeConfigurationException("ChangeOverrides.Raw is not closed");
    }

    public void write(XmlWriter xmlWriter, String str) {
      xmlWriter.print("<" + str);
      xmlWriter.print(">\n");
      xmlWriter.increaseIndent();
      Iterator it = getRawOverrideValue().iterator();
      while (it.hasNext()) {
        ((RawOverrideValue) it.next()).write(xmlWriter, "raw-override-value");
      }
      xmlWriter.decreaseIndent();
      xmlWriter.print("</" + str + ">\n");
    }
  }

  public Validated getValidated() {
    return this.validated;
  }

  public boolean hasValidated() {
    return this.validated != null;
  }

  public void setValidated(Validated validated) {
    this.validated = validated;
  }

  public Deferred getDeferred() {
    return this.deferred;
  }

  public boolean hasDeferred() {
    return this.deferred != null;
  }

  public void setDeferred(Deferred deferred) {
    this.deferred = deferred;
  }

  public Raw getRaw() {
    return this.raw;
  }

  public boolean hasRaw() {
    return this.raw != null;
  }

  public void setRaw(Raw raw) {
    this.raw = raw;
  }

  public long getChangeId() {
    Long l = this.changeId;
    if (l == null) {
      return 0L;
    }
    return l.longValue();
  }

  public boolean hasChangeId() {
    return this.changeId != null;
  }

  public void setChangeId(long j) {
    this.changeId = Long.valueOf(j);
  }

  public static ChangeOverrides read(XmlPullParser xmlPullParser) {
    int next;
    ChangeOverrides changeOverrides = new ChangeOverrides();
    String attributeValue = xmlPullParser.getAttributeValue(null, "changeId");
    if (attributeValue != null) {
      changeOverrides.setChangeId(Long.parseLong(attributeValue));
    }
    xmlPullParser.getDepth();
    while (true) {
      next = xmlPullParser.next();
      if (next == 1 || next == 3) {
        break;
      }
      if (xmlPullParser.getEventType() == 2) {
        String name = xmlPullParser.getName();
        if (name.equals("validated")) {
          changeOverrides.setValidated(Validated.read(xmlPullParser));
        } else if (name.equals("deferred")) {
          changeOverrides.setDeferred(Deferred.read(xmlPullParser));
        } else if (name.equals("raw")) {
          changeOverrides.setRaw(Raw.read(xmlPullParser));
        } else {
          XmlParser.skip(xmlPullParser);
        }
      }
    }
    if (next == 3) {
      return changeOverrides;
    }
    throw new DatatypeConfigurationException("ChangeOverrides is not closed");
  }

  public void write(XmlWriter xmlWriter, String str) {
    xmlWriter.print("<" + str);
    if (hasChangeId()) {
      xmlWriter.print(" changeId=\"");
      xmlWriter.print(Long.toString(getChangeId()));
      xmlWriter.print("\"");
    }
    xmlWriter.print(">\n");
    xmlWriter.increaseIndent();
    if (hasValidated()) {
      getValidated().write(xmlWriter, "validated");
    }
    if (hasDeferred()) {
      getDeferred().write(xmlWriter, "deferred");
    }
    if (hasRaw()) {
      getRaw().write(xmlWriter, "raw");
    }
    xmlWriter.decreaseIndent();
    xmlWriter.print("</" + str + ">\n");
  }
}
