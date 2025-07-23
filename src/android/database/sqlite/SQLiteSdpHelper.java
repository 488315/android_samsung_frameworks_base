package android.database.sqlite;

import android.database.DatabaseUtils;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.IDarManagerService;

/* loaded from: classes.dex */
public class SQLiteSdpHelper {
    private static final String TAG = "SQLiteSdpHelper";
    private static final boolean mIsSdpSupported = false;
    private static IDarManagerService sService;
    private SQLiteDatabase mDatabase;
    private final Object mLock = new Object();
    private SQLiteSdpListener mSdpListener = null;
    private boolean mIsInitialized = false;
    private int mEngineId = -1;

    private void prepare() {
    }

    public SQLiteSdpHelper(SQLiteDatabase sQLiteDatabase) {
        this.mDatabase = sQLiteDatabase;
    }

    /* renamed from: android.database.sqlite.SQLiteSdpHelper$1, reason: invalid class name */
    class AnonymousClass1 extends SQLiteSdpListener {
        @Override // com.samsung.android.knox.dar.sdp.ISdpListener
        public void onEngineRemoved() throws RemoteException {
        }

        AnonymousClass1() {
        }

        @Override // com.samsung.android.knox.dar.sdp.ISdpListener
        public void onStateChange(int i) throws RemoteException {
            if (i == 1) {
                SQLiteSdpHelper.this.mDatabase.execSQL("PRAGMA sdp_locked");
            } else {
                if (i != 2) {
                    return;
                }
                SQLiteSdpHelper.this.mDatabase.execSQL("PRAGMA sdp_unlocked");
            }
        }
    }

    private static synchronized IDarManagerService getDarService() {
        IDarManagerService iDarManagerService;
        synchronized (SQLiteSdpHelper.class) {
            if (sService == null) {
                sService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
            }
            iDarManagerService = sService;
        }
        return iDarManagerService;
    }

    private int getEngineId() {
        long j = -1;
        try {
            SQLiteDatabase sQLiteDatabase = this.mDatabase;
            if (sQLiteDatabase != null) {
                j = Long.valueOf(DatabaseUtils.longForQuery(sQLiteDatabase, "PRAGMA sdp_get_engine_id;", null)).intValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) j;
    }

    public void registerListener() {
        prepare();
        synchronized (this.mLock) {
            if (this.mIsInitialized) {
                try {
                    Log.e(TAG, "registerListener() - Engine Id : " + this.mEngineId);
                    sService.registerClient(this.mEngineId, this.mSdpListener);
                } catch (RemoteException e) {
                    Log.e(TAG, "RemoteException from registerClient", e);
                }
            }
        }
    }

    public void unregisterListener() {
        synchronized (this.mLock) {
            if (this.mIsInitialized) {
                try {
                    Log.e(TAG, "unregisterListener() - Engine Id : " + this.mEngineId);
                    sService.unregisterClient(this.mEngineId, this.mSdpListener);
                } catch (RemoteException e) {
                    Log.e(TAG, "RemoteException from unregisterClient", e);
                }
            }
        }
    }
}
