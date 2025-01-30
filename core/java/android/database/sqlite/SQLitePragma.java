package android.database.sqlite;

import android.p009os.CancellationSignal;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class SQLitePragma {
  private static final String TAG = "SQLitePragma";
  private final SQLiteDatabase mDatabase;
  private final String mSql;
  private static final Pattern mPragmaPattern =
      Pattern.compile(
          "^pragma\\s+(main\\.)?(case_sensitive_like|cache_size|automatic_index|busy_timeout|journal_mode)\\s*(=|\\()(.*)",
          2);
  private static final Pattern mTurnOnPattern = Pattern.compile("(on|yes|1|true)", 2);
  private static final Pattern mNumberPattern =
      Pattern.compile("\\s*[`\"'\\[\\s]*\\s*(\\+|-)?\\s*(0x)?([0-9a-f]+)(.*)", 2);
  private static final Pattern mJournalPattern =
      Pattern.compile("\\s*[`\"'\\[\\s]*\\s*(DELETE|TUNCATE|PERSIST|MEMORY|WAL|OFF)(.*)", 2);

  private SQLitePragma(SQLiteDatabase db, String sql) {
    this.mDatabase = db;
    this.mSql = sql;
  }

  public static void checkAndSetSpecialPragma(
      SQLiteDatabase db, String sql, CancellationSignal cancellationSignal) {
    if (cancellationSignal != null) {
      cancellationSignal.throwIfCanceled();
    }
    try {
      if (db.getMaxConnectionPoolSize() == 1) {
        return;
      }
      SQLitePragma pragma = new SQLitePragma(db, sql);
      pragma.checkAndSetSpecialPragma();
    } catch (Exception e) {
      Log.m95d(TAG, "checkAndSetSpecialPragma failed from this sql : " + sql, e);
    }
  }

  private void checkAndSetSpecialPragma() {
    Matcher sqlMatcher = mPragmaPattern.matcher(this.mSql);
    if (!sqlMatcher.matches()) {
      return;
    }
    String type = sqlMatcher.group(2);
    String value = sqlMatcher.group(4);
    if (value == null || value.length() == 0) {
      return;
    }
    if ("automatic_index".equalsIgnoreCase(type)) {
      updateAutomaticIndex(value);
      return;
    }
    if ("case_sensitive_like".equalsIgnoreCase(type)) {
      updateCaseSensitveLike(value);
      return;
    }
    if ("cache_size".equalsIgnoreCase(type)) {
      updateCacheSize(value);
    } else if ("busy_timeout".equalsIgnoreCase(type)) {
      updateBusyTimeout(value);
    } else if ("journal_mode".equalsIgnoreCase(type)) {
      updateJournalMode(value);
    }
  }

  private int extractIntFromValue(String value) {
    Matcher matcher = mNumberPattern.matcher(value);
    if (!matcher.matches()) {
      throw new IllegalStateException("Could not extract int value");
    }
    String sign = matcher.group(1);
    String scale = matcher.group(2);
    String number = matcher.group(3);
    Integer int_value = Integer.valueOf(number, scale != null ? 16 : 10);
    if (sign != null && NativeLibraryHelper.CLEAR_ABI_OVERRIDE.equalsIgnoreCase(sign)) {
      int_value = Integer.valueOf(-int_value.intValue());
    }
    return int_value.intValue();
  }

  private String extractJournalModeFromValue(String value) {
    Matcher matcher = mJournalPattern.matcher(value);
    if (!matcher.matches()) {
      return null;
    }
    return matcher.group(1);
  }

  private void updateAutomaticIndex(String value) {
    try {
      boolean enable = mTurnOnPattern.matcher(value).find();
      this.mDatabase.setAutomaticIndexEnabled(enable);
    } catch (Exception ex) {
      Log.m103w(TAG, "failed to get automatic_index value from this sql : " + this.mSql, ex);
    }
  }

  private void updateCaseSensitveLike(String value) {
    try {
      boolean enable = mTurnOnPattern.matcher(value).find();
      this.mDatabase.setCaseSensitiveLikeEnabled(enable);
    } catch (Exception ex) {
      Log.m103w(TAG, "failed to get case_sensitive_like value from this sql : " + this.mSql, ex);
    }
  }

  private void updateCacheSize(String value) {
    try {
      int size = extractIntFromValue(value);
      if (size >= 0 && size < 10) {
        Log.m96e(TAG, "Invalied cache size (under 10) '" + size + "', ignore sql : " + this.mSql);
      } else {
        this.mDatabase.setCacheSize(size);
      }
    } catch (Exception ex) {
      Log.m103w(TAG, "failed to get cache_size value from this sql : " + this.mSql, ex);
    }
  }

  private void updateBusyTimeout(String value) {
    try {
      int time = extractIntFromValue(value);
      this.mDatabase.setBusyTimeout(time);
    } catch (Exception ex) {
      Log.m103w(TAG, "failed to get busy_timeout value from this sql : " + this.mSql, ex);
    }
  }

  private void updateJournalMode(String value) {
    String journalMode = null;
    try {
      journalMode = extractJournalModeFromValue(value);
    } catch (Exception ex) {
      Log.m103w(TAG, "failed to get journal_mode value from this sql : " + this.mSql, ex);
    }
    if (journalMode != null && journalMode.length() != 0) {
      Log.m98i(
          TAG, "PRAGMA journal_mode = " + journalMode + " is executed, and it is not recommended");
      if (!"wal".equalsIgnoreCase(journalMode)) {
        try {
          this.mDatabase.disableWriteAheadLogging();
        } catch (Exception ex2) {
          Log.m103w(TAG, "disableWriteAheadLogging failed from this sql : " + this.mSql, ex2);
        }
      }
    }
  }
}
