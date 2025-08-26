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
            String strGroup = matcher.group(2);
            String strGroup2 = matcher.group(4);
            if (strGroup2 == null || strGroup2.length() == 0) {
                return;
            }
            if ("automatic_index".equalsIgnoreCase(strGroup)) {
                updateAutomaticIndex(strGroup2);
                return;
            }
            if ("case_sensitive_like".equalsIgnoreCase(strGroup)) {
                updateCaseSensitveLike(strGroup2);
                return;
            }
            if ("cache_size".equalsIgnoreCase(strGroup)) {
                updateCacheSize(strGroup2);
            } else if ("busy_timeout".equalsIgnoreCase(strGroup)) {
                updateBusyTimeout(strGroup2);
            } else if ("journal_mode".equalsIgnoreCase(strGroup)) {
                updateJournalMode(strGroup2);
            }
        }
    }

    private int extractIntFromValue(String str) throws NumberFormatException {
        Matcher matcher = mNumberPattern.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalStateException("Could not extract int value");
        }
        String strGroup = matcher.group(1);
        Integer numValueOf = Integer.valueOf(matcher.group(3), matcher.group(2) != null ? 16 : 10);
        if (strGroup != null && NativeLibraryHelper.CLEAR_ABI_OVERRIDE.equalsIgnoreCase(strGroup)) {
            numValueOf = Integer.valueOf(-numValueOf.intValue());
        }
        return numValueOf.intValue();
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
            int iExtractIntFromValue = extractIntFromValue(str);
            if (iExtractIntFromValue < 0 || iExtractIntFromValue >= 10) {
                this.mDatabase.setCacheSize(iExtractIntFromValue);
                return;
            }
            Log.e(TAG, "Invalied cache size (under 10) '" + iExtractIntFromValue + "', ignore sql : " + this.mSql);
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
        String strExtractJournalModeFromValue;
        try {
            strExtractJournalModeFromValue = extractJournalModeFromValue(str);
        } catch (Exception e) {
            Log.w(TAG, "failed to get journal_mode value from this sql : " + this.mSql, e);
            strExtractJournalModeFromValue = null;
        }
        if (strExtractJournalModeFromValue == null || strExtractJournalModeFromValue.length() == 0) {
            return;
        }
        Log.i(TAG, "PRAGMA journal_mode = " + strExtractJournalModeFromValue + " is executed, and it is not recommended");
        if ("wal".equalsIgnoreCase(strExtractJournalModeFromValue)) {
            return;
        }
        try {
            this.mDatabase.disableWriteAheadLogging();
        } catch (Exception e2) {
            Log.w(TAG, "disableWriteAheadLogging failed from this sql : " + this.mSql, e2);
        }
    }
}
