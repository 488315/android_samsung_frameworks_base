package com.android.server.enterprise.storage;

import android.content.res.XmlResourceParser;
import android.util.Log;

/* loaded from: classes2.dex */
public class EnterpriseXmlParser {
  public final TableCallback mCallback;
  public final XmlResourceParser mParser;

  public EnterpriseXmlParser(XmlResourceParser xmlResourceParser, TableCallback tableCallback) {
    this.mParser = xmlResourceParser;
    this.mCallback = tableCallback;
  }

  public void parseXML() {
    try {
      int eventType = this.mParser.getEventType();
      Table table = null;
      while (eventType != 1) {
        if (eventType == 2) {
          String name = this.mParser.getName();
          if (name.equalsIgnoreCase("table")) {
            table =
                new Table(
                    this.mParser.getAttributeValue(null, "name"),
                    this.mParser.getAttributeValue(null, "foreignrefertable"),
                    this.mParser.getAttributeValue(null, "foreignreferkey"),
                    this.mParser.getAttributeValue(null, "foreignkeyname"));
          }
          if (name.equalsIgnoreCase("column") && table != null) {
            table.addColumn(
                new Column(
                    this.mParser.getAttributeValue(null, "name"),
                    getType(this.mParser.getAttributeValue(null, "type")),
                    isPrimaryKey(this.mParser.getAttributeValue(null, "primarykey")),
                    this.mParser.getAttributeValue(null, "properties"),
                    this.mParser.getAttributeValue(null, "default")));
          }
        } else if (eventType != 3) {
          continue;
        } else if (this.mParser.getName().equalsIgnoreCase("table") && table != null) {
          this.mCallback.onTableFound(table);
          table = null;
        }
        eventType = this.mParser.next();
      }
    } catch (Exception e) {
      Log.e("EnterpriseXmlParser", "parseXML EX:", e);
    }
  }

  public final boolean isPrimaryKey(String str) {
    return str != null && str.equalsIgnoreCase("true");
  }

  public final Column.DATA_TYPE getType(String str) {
    if (str == null) {
      return Column.DATA_TYPE.BLOB;
    }
    if (str.equalsIgnoreCase("int")) {
      return Column.DATA_TYPE.INTEGER;
    }
    if (str.equalsIgnoreCase("text")) {
      return Column.DATA_TYPE.TEXT;
    }
    if (str.equalsIgnoreCase("numeric")) {
      return Column.DATA_TYPE.NUMERIC;
    }
    if (str.equalsIgnoreCase("real")) {
      return Column.DATA_TYPE.REAL;
    }
    return Column.DATA_TYPE.BLOB;
  }
}
