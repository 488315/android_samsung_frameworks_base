package com.samsung.android.knox.analytics.database;

import android.database.AbstractCursor;
import android.database.Cursor;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.knox.analytics.util.Log;

/* loaded from: classes6.dex */
class EncryptedCursor extends AbstractCursor {
    private static int INITIAL_VALUE = -99;
    CryptoHandler mCryptoHandler;
    final Cursor mDatabaseCursor;
    final DatabaseHelper mDatabaseHelper;
    private final String TAG = "[KnoxAnalytics] EncryptedCursor";
    private int mSyntheticRowId = INITIAL_VALUE;

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int i) {
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int i) {
        return 0.0f;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int i) {
        return (short) 0;
    }

    public EncryptedCursor(DatabaseHelper databaseHelper, CryptoHandler cryptoHandler, Integer num) {
        Log.d("[KnoxAnalytics] EncryptedCursor", "constructor()");
        this.mDatabaseHelper = databaseHelper;
        this.mDatabaseCursor = databaseHelper.getEventChunk(num);
        this.mCryptoHandler = cryptoHandler;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getCount() {
        return this.mDatabaseCursor.getCount();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String[] getColumnNames() {
        return this.mDatabaseCursor.getColumnNames();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0077  */
    @Override // android.database.AbstractCursor, android.database.Cursor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getString(int r4) {
        /*
            r3 = this;
            java.lang.String r0 = r3.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "getString("
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r2 = ")"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.samsung.android.knox.analytics.util.Log.d(r0, r1)
            android.database.Cursor r0 = r3.mDatabaseCursor
            java.lang.String r0 = r0.getColumnName(r4)
            java.lang.String r1 = "data"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L84
            android.database.Cursor r0 = r3.mDatabaseCursor     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            java.lang.String r1 = "bulk"
            int r0 = r0.getColumnIndex(r1)     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            android.database.Cursor r1 = r3.mDatabaseCursor     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            int r0 = r1.getInt(r0)     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            r1 = 1
            if (r0 <= r1) goto L44
            com.samsung.android.knox.analytics.database.CryptoHandler r0 = r3.mCryptoHandler     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            android.database.Cursor r1 = r3.mDatabaseCursor     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            byte[] r4 = r1.getBlob(r4)     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            java.lang.String r4 = r0.decryptBulk(r4)     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            goto L75
        L44:
            com.samsung.android.knox.analytics.database.CryptoHandler r0 = r3.mCryptoHandler     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            android.database.Cursor r1 = r3.mDatabaseCursor     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            byte[] r4 = r1.getBlob(r4)     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            boolean r1 = r3.useLegacyKey()     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            java.lang.String r4 = r0.decrypt(r4, r1)     // Catch: java.io.UnsupportedEncodingException -> L55 java.security.GeneralSecurityException -> L5e java.security.InvalidKeyException -> L67
            goto L75
        L55:
            r4 = move-exception
            java.lang.String r0 = r3.TAG
            java.lang.String r1 = "getString(): UnsupportedEncodingException"
            com.samsung.android.knox.analytics.util.Log.e(r0, r1, r4)
            goto L74
        L5e:
            r4 = move-exception
            java.lang.String r0 = r3.TAG
            java.lang.String r1 = "getString(): GeneralSecurityException"
            com.samsung.android.knox.analytics.util.Log.e(r0, r1, r4)
            goto L74
        L67:
            r4 = move-exception
            java.lang.String r0 = r3.TAG
            java.lang.String r1 = "getString(): InvalidKeyException"
            com.samsung.android.knox.analytics.util.Log.e(r0, r1, r4)
            com.samsung.android.knox.analytics.database.CryptoHandler r4 = r3.mCryptoHandler
            r4.deleteAnalyticsLegacyKey()
        L74:
            r4 = 0
        L75:
            if (r4 != 0) goto L83
            java.lang.String r0 = r3.TAG
            java.lang.String r1 = "getString(): null data."
            com.samsung.android.knox.analytics.util.Log.e(r0, r1)
            com.samsung.android.knox.analytics.database.DatabaseHelper r3 = r3.mDatabaseHelper
            r3.deleteEventsUpToSyntheticId()
        L83:
            return r4
        L84:
            android.database.Cursor r3 = r3.mDatabaseCursor
            java.lang.String r3 = r3.getString(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.database.EncryptedCursor.getString(int):java.lang.String");
    }

    private boolean useLegacyKey() {
        Log.d(this.TAG, "useLegacyKey()");
        if (this.mSyntheticRowId == INITIAL_VALUE) {
            this.mSyntheticRowId = this.mDatabaseHelper.getSyntheticRowId();
        }
        if (this.mSyntheticRowId == -1) {
            Log.d(this.TAG, "useLegacyKey(): There is no marked event ID");
            return false;
        }
        Cursor cursor = this.mDatabaseCursor;
        if (cursor.getInt(cursor.getColumnIndex("id")) <= this.mSyntheticRowId) {
            return true;
        }
        this.mCryptoHandler.deleteAnalyticsLegacyKey();
        return false;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getType(int i) {
        if (this.mDatabaseCursor.getColumnName(i).equals("data")) {
            Log.d(this.TAG, "getType(" + i + "): returning string for encrypted data");
            return 3;
        }
        return this.mDatabaseCursor.getType(i);
    }

    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int i, int i2) {
        super.onMove(i, i2);
        return this.mDatabaseCursor.moveToPosition(i2);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int i) {
        return this.mDatabaseCursor.isNull(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        super.close();
        this.mDatabaseCursor.close();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int i) {
        return this.mDatabaseCursor.getInt(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int i) {
        return this.mDatabaseCursor.getLong(i);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int i) {
        Log.d(this.TAG, "getBlob(" + i + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDatabaseCursor.getBlob(i);
    }
}
