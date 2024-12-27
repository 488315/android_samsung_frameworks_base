package com.samsung.android.knox.analytics.database;

import android.database.AbstractCursor;
import android.database.Cursor;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import com.samsung.android.knox.analytics.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

/* loaded from: classes6.dex */
class EncryptedCursor extends AbstractCursor {
    private static int INITIAL_VALUE = -99;
    CryptoHandler mCryptoHandler;
    final Cursor mDatabaseCursor;
    final DatabaseHelper mDatabaseHelper;
    private final String TAG = "[KnoxAnalytics] " + EncryptedCursor.class.getSimpleName();
    private int mSyntheticRowId = INITIAL_VALUE;

    public EncryptedCursor(DatabaseHelper dbHelper, CryptoHandler cryptoHandler, Integer size) {
        Log.d(this.TAG, "constructor()");
        this.mDatabaseHelper = dbHelper;
        this.mDatabaseCursor = dbHelper.getEventChunk(size);
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

    @Override // android.database.AbstractCursor, android.database.Cursor
    public String getString(int column) {
        Log.d(this.TAG, "getString(" + column + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mDatabaseCursor.getColumnName(column).equals("data")) {
            String decrypted = null;
            try {
                int bulkColumn = this.mDatabaseCursor.getColumnIndex("bulk");
                int eventCounter = this.mDatabaseCursor.getInt(bulkColumn);
                if (eventCounter > 1) {
                    decrypted =
                            this.mCryptoHandler.decryptBulk(this.mDatabaseCursor.getBlob(column));
                } else {
                    decrypted =
                            this.mCryptoHandler.decrypt(
                                    this.mDatabaseCursor.getBlob(column), useLegacyKey());
                }
            } catch (UnsupportedEncodingException e) {
                Log.e(this.TAG, "getString(): UnsupportedEncodingException", e);
            } catch (InvalidKeyException e2) {
                Log.e(this.TAG, "getString(): InvalidKeyException", e2);
                this.mCryptoHandler.deleteAnalyticsLegacyKey();
            } catch (GeneralSecurityException e3) {
                Log.e(this.TAG, "getString(): GeneralSecurityException", e3);
            }
            if (decrypted == null) {
                Log.e(this.TAG, "getString(): null data.");
                this.mDatabaseHelper.deleteEventsUpToSyntheticId();
            }
            return decrypted;
        }
        return this.mDatabaseCursor.getString(column);
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
        if (this.mDatabaseCursor.getInt(this.mDatabaseCursor.getColumnIndex("id"))
                > this.mSyntheticRowId) {
            this.mCryptoHandler.deleteAnalyticsLegacyKey();
            return false;
        }
        return true;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getType(int column) {
        if (this.mDatabaseCursor.getColumnName(column).equals("data")) {
            Log.d(this.TAG, "getType(" + column + "): returning string for encrypted data");
            return 3;
        }
        return this.mDatabaseCursor.getType(column);
    }

    @Override // android.database.AbstractCursor, android.database.CrossProcessCursor
    public boolean onMove(int oldPosition, int newPosition) {
        super.onMove(oldPosition, newPosition);
        return this.mDatabaseCursor.moveToPosition(newPosition);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public boolean isNull(int column) {
        return this.mDatabaseCursor.isNull(column);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable,
              // java.lang.AutoCloseable
    public void close() {
        super.close();
        this.mDatabaseCursor.close();
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public short getShort(int column) {
        return (short) 0;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public int getInt(int column) {
        return this.mDatabaseCursor.getInt(column);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public long getLong(int column) {
        return this.mDatabaseCursor.getLong(column);
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public float getFloat(int column) {
        return 0.0f;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public double getDouble(int column) {
        return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // android.database.AbstractCursor, android.database.Cursor
    public byte[] getBlob(int column) {
        Log.d(this.TAG, "getBlob(" + column + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDatabaseCursor.getBlob(column);
    }
}
