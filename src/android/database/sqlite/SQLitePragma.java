package android.database.sqlite;

import android.os.CancellationSignal;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class SQLitePragma {
    private static final String TAG = "SQLitePragma";
    private final SQLiteDatabase mDatabase;
    private final String mSql;
    private static final Pattern mPragmaPattern = Pattern.compile("^pragma\\s+(main\\.)?(case_sensitive_like|cache_size|automatic_index|busy_timeout|journal_mode)\\s*(=|\\()(.*)", 2);
    private static final Pattern mTurnOnPattern = Pattern.compile("(on|yes|1|true)", 2);
    private static final Pattern mNumberPattern = Pattern.compile("\\s*[`\"'\\[\\s]*\\s*(\\+|-)?\\s*(0x)?([0-9a-f]+)(.*)", 2);
    private static final Pattern mJournalPattern = Pattern.compile("\\s*[`\"'\\[\\s]*\\s*(DELETE|TUNCATE|PERSIST|MEMORY|WAL|OFF)(.*)", 2);

    private SQLitePragma(SQLiteDatabase sQLiteDatabase, String str) {
        this.mDatabase = sQLiteDatabase;
        this.mSql = str;
    }

    public static void checkAndSetSpecialPragma(SQLiteDatabase sQLiteDatabase, String str, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        try {
            if (sQLiteDatabase.getMaxConnectionPoolSize() == 1) {
                return;
            }
            new SQLitePragma(sQLiteDatabase, str).checkAndSetSpecialPragma();
        } catch (Exception e) {
            Log.d(TAG, "checkAndSetSpecialPragma failed from this sql : " + str, e);
        }
    }

    private void checkAndSetSpecialPragma() {
        Matcher matcher = mPragmaPattern.matcher(this.mSql);
        if (matcher.matches()) {
            String group = matcher.group(2);
            String group2 = matcher.group(4);
            if (group2 == null || group2.length() == 0) {
                return;
            }
            if ("automatic_index".equalsIgnoreCase(group)) {
                updateAutomaticIndex(group2);
                return;
            }
            if ("case_sensitive_like".equalsIgnoreCase(group)) {
                updateCaseSensitveLike(group2);
                return;
            }
            if ("cache_size".equalsIgnoreCase(group)) {
                updateCacheSize(group2);
            } else if ("busy_timeout".equalsIgnoreCase(group)) {
                updateBusyTimeout(group2);
            } else if ("journal_mode".equalsIgnoreCase(group)) {
                updateJournalMode(group2);
            }
        }
    }

    private int extractIntFromValue(String str) {
        Matcher matcher = mNumberPattern.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalStateException("Could not extract int value");
        }
        String group = matcher.group(1);
        Integer valueOf = Integer.valueOf(matcher.group(3), matcher.group(2) != null ? 16 : 10);
        if (group != null && NativeLibraryHelper.CLEAR_ABI_OVERRIDE.equalsIgnoreCase(group)) {
            valueOf = Integer.valueOf(-valueOf.intValue());
        }
        return valueOf.intValue();
    }

    private String extractJournalModeFromValue(String str) {
        Matcher matcher = mJournalPattern.matcher(str);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    private void updateAutomaticIndex(String str) {
        try {
            this.mDatabase.setAutomaticIndexEnabled(mTurnOnPattern.matcher(str).find());
        } catch (Exception e) {
            Log.w(TAG, "failed to get automatic_index value from this sql : " + this.mSql, e);
        }
    }

    private void updateCaseSensitveLike(String str) {
        try {
            this.mDatabase.setCaseSensitiveLikeEnabled(mTurnOnPattern.matcher(str).find());
        } catch (Exception e) {
            Log.w(TAG, "failed to get case_sensitive_like value from this sql : " + this.mSql, e);
        }
    }

    private void updateCacheSize(String str) {
        try {
            int extractIntFromValue = extractIntFromValue(str);
            if (extractIntFromValue < 0 || extractIntFromValue >= 10) {
                this.mDatabase.setCacheSize(extractIntFromValue);
                return;
            }
            Log.e(TAG, "Invalied cache size (under 10) '" + extractIntFromValue + "', ignore sql : " + this.mSql);
        } catch (Exception e) {
            Log.w(TAG, "failed to get cache_size value from this sql : " + this.mSql, e);
        }
    }

    private void updateBusyTimeout(String str) {
        try {
            this.mDatabase.setBusyTimeout(extractIntFromValue(str));
        } catch (Exception e) {
            Log.w(TAG, "failed to get busy_timeout value from this sql : " + this.mSql, e);
        }
    }

    private void updateJournalMode(String str) {
        String str2;
        try {
            str2 = extractJournalModeFromValue(str);
        } catch (Exception e) {
            Log.w(TAG, "failed to get journal_mode value from this sql : " + this.mSql, e);
            str2 = null;
        }
        if (str2 == null || str2.length() == 0) {
            return;
        }
        Log.i(TAG, "PRAGMA journal_mode = " + str2 + " is executed, and it is not recommended");
        if ("wal".equalsIgnoreCase(str2)) {
            return;
        }
        try {
            this.mDatabase.disableWriteAheadLogging();
        } catch (Exception e2) {
            Log.w(TAG, "disableWriteAheadLogging failed from this sql : " + this.mSql, e2);
        }
    }
}
