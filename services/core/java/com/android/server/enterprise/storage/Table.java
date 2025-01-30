package com.android.server.enterprise.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class Table {
  public ArrayList mColumns = new ArrayList();
  public String mForeignKeyName;
  public String mForeignReferKey;
  public String mForeignReferTable;
  public String mTableName;

  public Table(String str, String str2, String str3, String str4) {
    this.mTableName = str;
    this.mForeignReferTable = str2;
    this.mForeignReferKey = str3;
    this.mForeignKeyName = str4;
  }

  public void addColumn(Column column) {
    this.mColumns.add(column);
  }

  public String buildTableColumns() {
    Iterator it = this.mColumns.iterator();
    String str = "";
    while (it.hasNext()) {
      str = str + ((Column) it.next()).getSQLDeclaration() + ", ";
    }
    return str.length() > 0 ? str.substring(0, str.length() - 2) : "";
  }

  public String buildPrimaryKeys() {
    Iterator it = this.mColumns.iterator();
    String str = "";
    while (it.hasNext()) {
      Column column = (Column) it.next();
      if (column.mIsPrimaryKey) {
        str = str + column.mColumnName + ", ";
      }
    }
    if (str.length() > 0) {
      return str.substring(0, str.length() - 2);
    }
    return null;
  }

  public ArrayList getMissingColumns(List list) {
    ArrayList arrayList = new ArrayList();
    Iterator it = this.mColumns.iterator();
    while (it.hasNext()) {
      Column column = (Column) it.next();
      if (!list.contains(column.mColumnName)) {
        arrayList.add(column);
      }
    }
    return arrayList;
  }
}
