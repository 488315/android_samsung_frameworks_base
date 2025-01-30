package com.android.server.bridge.operations;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.samsung.android.knox.SemPersonaManager;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class RCPDumpState {
  public static RCPDumpState mRCPDumpState;
  public ContentResolver mContentResolver;
  public Context mContext;
  public final String TAG = RCPDumpState.class.getSimpleName();
  public Uri mOwnerUri = null;
  public Uri mContainerUri = null;
  public Uri mQueryUri = null;

  public RCPDumpState(Context context) {
    this.mContentResolver = null;
    this.mContext = context;
    this.mContentResolver = context.getContentResolver();
  }

  public static RCPDumpState getInstance(Context context, PrintWriter printWriter) {
    if (mRCPDumpState == null) {
      mRCPDumpState = new RCPDumpState(context);
    }
    return mRCPDumpState;
  }

  public void dumpStateFileOpsTable(PrintWriter printWriter, int i) {
    Cursor query =
        this.mContext
            .getContentResolver()
            .query(
                Uri.parse(
                    "content://"
                        + Integer.toString(i)
                        + "@"
                        + (isSecureFolderId(i)
                            ? "com.samsung.knox.securefolder.rcpcomponents.move.provider.knoxcontentmgrdbprovider"
                            : "com.samsung.android.knox.containercore.rcpcomponents.move.provider.knoxcontentmgrdbprovider")
                        + "/"),
                null,
                null,
                null,
                null);
    if (query != null) {
      if (query.moveToFirst()) {
        do {
          printWriter.print(query.getString(query.getColumnIndex("timeStamp")));
          printWriter.print(" (");
          printWriter.print(query.getString(query.getColumnIndex("operation")));
          printWriter.print(")");
          printWriter.print(" ret:");
          printWriter.print(query.getInt(query.getColumnIndex("resultCode")));
          printWriter.print(" srcUri:");
          printWriter.print(query.getString(query.getColumnIndex("srcUri")));
          printWriter.print(" destUri:");
          printWriter.print(query.getString(query.getColumnIndex("destUri")));
          printWriter.print(" src:");
          printWriter.print(query.getString(query.getColumnIndex("source")));
          printWriter.print(" dest:");
          printWriter.println(query.getString(query.getColumnIndex("destination")));
        } while (query.moveToNext());
      }
      query.close();
    }
  }

  public void dumpBackupAndRestoreHistory(PrintWriter printWriter, int i) {
    printWriter.println("dumpBackupAndRestoreHistory , userId: " + i);
    Cursor query =
        this.mContentResolver.query(
            Uri.parse(
                "content://" + Integer.toString(i) + "@com.samsung.knox.securefolder/bnr_logs"),
            null,
            null,
            null,
            null);
    if (query != null) {
      if (query.moveToFirst()) {
        do {
          printWriter.print("timeStamp: ");
          printWriter.print(query.getString(query.getColumnIndex("timestamp")));
          printWriter.print("text: ");
          printWriter.print(query.getString(query.getColumnIndex("text")));
          printWriter.println();
        } while (query.moveToNext());
      }
      query.close();
    }
  }

  public final boolean isSecureFolderId(int i) {
    return SemPersonaManager.isSecureFolderId(i);
  }
}
