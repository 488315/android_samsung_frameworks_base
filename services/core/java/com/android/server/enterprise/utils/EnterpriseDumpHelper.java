package com.android.server.enterprise.utils;

import android.content.ContentValues;
import android.content.Context;
import com.android.server.enterprise.storage.EdmStorageProvider;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class EnterpriseDumpHelper {
  public Context mContext;
  public EdmStorageProvider mEdmStorageProvider;

  public EnterpriseDumpHelper(Context context) {
    this.mContext = context;
    this.mEdmStorageProvider = new EdmStorageProvider(context);
  }

  public void dumpTable(PrintWriter printWriter, String str, String[] strArr) {
    dumpTable(printWriter, str, strArr, null);
  }

  public void dumpTable(PrintWriter printWriter, String str, String[] strArr, String[] strArr2) {
    printWriter.write("[" + str + " table]" + System.lineSeparator());
    if ("generic".equals(str)) {
      String[] strArr3 = {"name", "value", "containerID", "userID"};
      for (String str2 : strArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", str2);
        printColumns(printWriter, str, strArr3, strArr2, contentValues);
      }
      return;
    }
    printColumns(printWriter, str, strArr, strArr2, null);
  }

  public final void printColumns(
      PrintWriter printWriter,
      String str,
      String[] strArr,
      String[] strArr2,
      ContentValues contentValues) {
    String readColumns = readColumns(str, strArr, strArr2, contentValues);
    if (readColumns != null) {
      printWriter.write(readColumns);
    }
  }

  public String readTable(String str, String[] strArr) {
    String readColumns = readColumns(str, strArr, null, null);
    return readColumns != null ? readColumns : "";
  }

  public final String readColumns(
      String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
    List<ContentValues> values = this.mEdmStorageProvider.getValues(str, strArr, contentValues);
    if (values.isEmpty()) {
      return null;
    }
    List asList = strArr2 != null ? Arrays.asList(strArr2) : null;
    StringBuilder sb = new StringBuilder();
    for (ContentValues contentValues2 : values) {
      for (String str2 : strArr) {
        String asString = contentValues2.getAsString(str2);
        sb.append(String.format("   %s: %s", str2, asString));
        if (asList != null && asList.contains(str2)) {
          try {
            sb.append(getBitNumbers(asString));
          } catch (NumberFormatException unused) {
          }
        }
        sb.append(System.lineSeparator());
      }
      sb.append(System.lineSeparator());
    }
    sb.append(System.lineSeparator());
    return sb.toString();
  }

  public final String getBitNumbers(String str) {
    StringBuilder sb = new StringBuilder();
    int parseInt = Integer.parseInt(str);
    sb.append("(0x" + Integer.toHexString(parseInt) + ")");
    if (parseInt != 0) {
      sb.append(": ");
    }
    int i = 0;
    while (parseInt != 0) {
      if ((parseInt & 1) == 1) {
        sb.append(i + " ");
      }
      parseInt >>= 1;
      i++;
    }
    return sb.toString();
  }
}
