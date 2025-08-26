package com.samsung.android.knox.analytics.database;

import android.database.AbstractCursor;
import android.database.Cursor;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.samsung.android.knox.analytics.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0077  */
    @Override // android.database.AbstractCursor, android.database.Cursor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getString(int i) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        String strDecrypt;
        Log.d(this.TAG, "getString(" + i + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mDatabaseCursor.getColumnName(i).equals("data")) {
            try {
                if (this.mDatabaseCursor.getInt(this.mDatabaseCursor.getColumnIndex("bulk")) > 1) {
                    strDecrypt = this.mCryptoHandler.decryptBulk(this.mDatabaseCursor.getBlob(i));
                } else {
                    strDecrypt = this.mCryptoHandler.decrypt(this.mDatabaseCursor.getBlob(i), useLegacyKey());
                }
            } catch (UnsupportedEncodingException e) {
                Log.e(this.TAG, "getString(): UnsupportedEncodingException", e);
                strDecrypt = null;
                if (strDecrypt == null) {
                }
                return strDecrypt;
            } catch (InvalidKeyException e2) {
                Log.e(this.TAG, "getString(): InvalidKeyException", e2);
                this.mCryptoHandler.deleteAnalyticsLegacyKey();
                strDecrypt = null;
                if (strDecrypt == null) {
                }
                return strDecrypt;
            } catch (GeneralSecurityException e3) {
                Log.e(this.TAG, "getString(): GeneralSecurityException", e3);
                strDecrypt = null;
                if (strDecrypt == null) {
                }
                return strDecrypt;
            }
            if (strDecrypt == null) {
                Log.e(this.TAG, "getString(): null data.");
                this.mDatabaseHelper.deleteEventsUpToSyntheticId();
            }
            return strDecrypt;
        }
        return this.mDatabaseCursor.getString(i);
    }

    private boolean useLegacyKey() throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
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
