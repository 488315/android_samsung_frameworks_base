package android.database.sqlite;

import android.database.Cursor;

/* loaded from: classes.dex */
public interface SQLiteCursorDriver {
  void cursorClosed();

  void cursorDeactivated();

  void cursorRequeried(Cursor cursor);

  Cursor query(SQLiteDatabase.CursorFactory cursorFactory, String[] strArr);

  void setBindArguments(String[] strArr);
}
