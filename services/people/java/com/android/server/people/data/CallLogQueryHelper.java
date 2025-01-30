package com.android.server.people.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseIntArray;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class CallLogQueryHelper {
  public static final SparseIntArray CALL_TYPE_TO_EVENT_TYPE;
  public final Context mContext;
  public final BiConsumer mEventConsumer;
  public long mLastCallTimestamp;

  static {
    SparseIntArray sparseIntArray = new SparseIntArray();
    CALL_TYPE_TO_EVENT_TYPE = sparseIntArray;
    sparseIntArray.put(1, 11);
    sparseIntArray.put(2, 10);
    sparseIntArray.put(3, 12);
  }

  public CallLogQueryHelper(Context context, BiConsumer biConsumer) {
    this.mContext = context;
    this.mEventConsumer = biConsumer;
  }

  /* JADX WARN: Removed duplicated region for block: B:22:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:31:? A[Catch: SecurityException -> 0x0093, SYNTHETIC, TRY_LEAVE, TryCatch #0 {SecurityException -> 0x0093, blocks: (B:3:0x001d, B:42:0x0034, B:30:0x0092, B:29:0x008f, B:34:0x007f, B:23:0x0089), top: B:2:0x001d, inners: #4 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public boolean querySince(long j) {
    Throwable th;
    Cursor cursor;
    String string;
    long j2;
    long j3;
    int i;
    try {
      Cursor query =
          this.mContext
              .getContentResolver()
              .query(
                  CallLog.Calls.CONTENT_URI,
                  new String[] {"normalized_number", "date", "duration", "type"},
                  "date > ?",
                  new String[] {Long.toString(j)},
                  "date DESC");
      if (query == null) {
        try {
          Slog.w("CallLogQueryHelper", "Cursor is null when querying call log.");
          if (query != null) {
            query.close();
          }
          return false;
        } catch (Throwable th2) {
          th = th2;
          cursor = query;
        }
      } else {
        boolean z = false;
        while (query.moveToNext()) {
          try {
            string = query.getString(query.getColumnIndex("normalized_number"));
            j2 = query.getLong(query.getColumnIndex("date"));
            j3 = query.getLong(query.getColumnIndex("duration"));
            i = query.getInt(query.getColumnIndex("type"));
            cursor = query;
          } catch (Throwable th3) {
            th = th3;
            cursor = query;
          }
          try {
            this.mLastCallTimestamp = Math.max(this.mLastCallTimestamp, j2);
            if (addEvent(string, j2, j3, i)) {
              z = true;
            }
            query = cursor;
          } catch (Throwable th4) {
            th = th4;
            th = th;
            if (cursor != null) {}
          }
        }
        query.close();
        return z;
      }
      if (cursor != null) {
        throw th;
      }
      try {
        cursor.close();
        throw th;
      } catch (Throwable th5) {
        th.addSuppressed(th5);
        throw th;
      }
    } catch (SecurityException e) {
      Slog.e("CallLogQueryHelper", "Query call log failed: " + e);
      return false;
    }
  }

  public long getLastCallTimestamp() {
    return this.mLastCallTimestamp;
  }

  public final boolean addEvent(String str, long j, long j2, int i) {
    if (!validateEvent(str, j, i)) {
      return false;
    }
    this.mEventConsumer.accept(
        str,
        new Event.Builder(j, CALL_TYPE_TO_EVENT_TYPE.get(i)).setDurationSeconds((int) j2).build());
    return true;
  }

  public final boolean validateEvent(String str, long j, int i) {
    return !TextUtils.isEmpty(str) && j > 0 && CALL_TYPE_TO_EVENT_TYPE.indexOfKey(i) >= 0;
  }
}
