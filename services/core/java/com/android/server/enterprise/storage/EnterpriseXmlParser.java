package com.android.server.enterprise.storage;

import android.content.res.XmlResourceParser;
import android.util.Log;
import com.android.server.enterprise.storage.Column;
import com.android.server.enterprise.storage.EdmStorageHelper;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EnterpriseXmlParser {
    public final EdmStorageHelper.AnonymousClass1 mCallback;
    public final XmlResourceParser mParser;

    public EnterpriseXmlParser(XmlResourceParser xmlResourceParser, EdmStorageHelper.AnonymousClass1 anonymousClass1) {
        this.mParser = xmlResourceParser;
        this.mCallback = anonymousClass1;
    }

    public final void parseXML() {
        try {
            int eventType = this.mParser.getEventType();
            Table table = null;
            while (true) {
                boolean z = true;
                if (eventType == 1) {
                    return;
                }
                if (eventType == 2) {
                    String name = this.mParser.getName();
                    if (name.equalsIgnoreCase("table")) {
                        String attributeValue = this.mParser.getAttributeValue(null, "name");
                        String attributeValue2 = this.mParser.getAttributeValue(null, "foreignrefertable");
                        String attributeValue3 = this.mParser.getAttributeValue(null, "foreignreferkey");
                        String attributeValue4 = this.mParser.getAttributeValue(null, "foreignkeyname");
                        table = new Table();
                        table.mColumns = new ArrayList();
                        table.mTableName = attributeValue;
                        table.mForeignReferTable = attributeValue2;
                        table.mForeignReferKey = attributeValue3;
                        table.mForeignKeyName = attributeValue4;
                    }
                    if (name.equalsIgnoreCase("column") && table != null) {
                        String attributeValue5 = this.mParser.getAttributeValue(null, "name");
                        String attributeValue6 = this.mParser.getAttributeValue(null, "type");
                        Column.DATA_TYPE data_type = Column.DATA_TYPE.BLOB;
                        if (attributeValue6 != null) {
                            if (attributeValue6.equalsIgnoreCase("int")) {
                                data_type = Column.DATA_TYPE.INTEGER;
                            } else if (attributeValue6.equalsIgnoreCase("text")) {
                                data_type = Column.DATA_TYPE.TEXT;
                            } else if (attributeValue6.equalsIgnoreCase("numeric")) {
                                data_type = Column.DATA_TYPE.NUMERIC;
                            } else if (attributeValue6.equalsIgnoreCase("real")) {
                                data_type = Column.DATA_TYPE.REAL;
                            }
                        }
                        String attributeValue7 = this.mParser.getAttributeValue(null, "primarykey");
                        if (attributeValue7 == null || !attributeValue7.equalsIgnoreCase("true")) {
                            z = false;
                        }
                        String attributeValue8 = this.mParser.getAttributeValue(null, "properties");
                        String attributeValue9 = this.mParser.getAttributeValue(null, "default");
                        Column column = new Column();
                        column.mColumnName = attributeValue5;
                        column.mColumnType = data_type;
                        column.mIsPrimaryKey = z;
                        column.mProperties = attributeValue8;
                        column.mDefaultValue = attributeValue9;
                        table.mColumns.add(column);
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
}
