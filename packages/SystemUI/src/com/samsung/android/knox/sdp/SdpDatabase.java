package com.samsung.android.knox.sdp;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.sdp.core.SdpException;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.List;

/* loaded from: classes4.dex */
public class SdpDatabase {
    private static final String CLASS_NAME = "SdpDatabase";
    private static final boolean DEBUG = false;
    private static final String TAG = "SdpDatabase";
    private static final boolean runAllConvert = false;
    private String mAlias;
    private final ContextInfo mContextInfo;
    private int mEngineId;

    public SdpDatabase(String str) throws SdpException {
        this.mEngineId = -1;
        enforcePermission();
        this.mAlias = str;
        this.mContextInfo = new ContextInfo(Binder.getCallingUid());
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null) {
            throw new SdpException(-5);
        }
        this.mEngineId = engineInfo.getId();
    }

    private void enforcePermission() throws SdpException {
        IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (iDarManagerServiceAsInterface != null) {
            try {
                if (iDarManagerServiceAsInterface.isLicensed() == 0) {
                } else {
                    throw new SdpException(-9);
                }
            } catch (RemoteException e) {
                Log.e("SdpDatabase", "Failed to talk with sdp service...", e);
            }
        }
    }

    private String formSensitiveColumnStmt(int i, String str, String str2) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return null;
        }
        return ReorderTile$$ExternalSyntheticOutline0.m(i, ";", SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("table=", str, ";columns=", str2, ";engine_id="));
    }

    private String formSensitivePolicy(String str, String str2, String str3) throws Exception {
        String strFormSensitiveColumnStmt = formSensitiveColumnStmt(this.mEngineId, str2, str3);
        if (strFormSensitiveColumnStmt == null) {
            return null;
        }
        return MotionLayout$$ExternalSyntheticOutline0.m("pragma ", str == null ? "" : str.concat("."), "set_sensitive_columns(\"", strFormSensitiveColumnStmt, "\");");
    }

    private SdpEngineInfo getEngineInfo(String str) {
        try {
            IDarManagerService iDarManagerServiceAsInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
            if (iDarManagerServiceAsInterface != null) {
                return iDarManagerServiceAsInterface.getEngineInfo(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.e("SdpDatabase", "Failed to talk with sdp service...", e);
            return null;
        }
    }

    public boolean isSensitive(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        String strConcat;
        boolean z = false;
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "isSensitive :: invalid DB");
            return false;
        }
        if (this.mEngineId < 0) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("isSensitive :: invalid engine "), this.mAlias, "SdpDatabase");
            return false;
        }
        Cursor cursorRawQuery = null;
        if (str == null) {
            strConcat = "";
        } else {
            try {
                try {
                    strConcat = str.concat(".");
                } catch (SQLiteException e) {
                    e.printStackTrace();
                    if (!cursorRawQuery.isClosed()) {
                        cursorRawQuery.close();
                    }
                    return false;
                }
            } finally {
                if (!cursorRawQuery.isClosed()) {
                    cursorRawQuery.close();
                }
            }
        }
        cursorRawQuery = sQLiteDatabase.rawQuery("pragma " + strConcat + "get_sensitive_columns(" + str2 + ")", null);
        if (cursorRawQuery.moveToFirst()) {
            while (true) {
                if (str3.equals(cursorRawQuery.getString(0))) {
                    z = true;
                    break;
                }
                if (!cursorRawQuery.moveToNext()) {
                    break;
                }
            }
        }
        return z;
    }

    public boolean setSensitive(SQLiteDatabase sQLiteDatabase, String str, String str2, List<String> list) throws SdpException, SQLException {
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpDatabase.setSensitive");
        StringBuilder sb = new StringBuilder();
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "setSensitive :: invalid DB");
            return false;
        }
        if (this.mEngineId < 0) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("setSensitive :: invalid engine "), this.mAlias, "SdpDatabase");
            return false;
        }
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null || engineInfo.getState() == 1) {
            Log.d("SdpDatabase", "setSensitive failed, engine is locked!!! " + this.mAlias);
            throw new SdpException(-6);
        }
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        Cursor cursor = null;
        try {
            if (sQLiteDatabase.isReadOnly()) {
                Log.d("SdpDatabase", "Error : DB is readonly. setSensitiveDBPolicy require write permission for DB");
                return false;
            }
            sQLiteDatabase.execSQL(formSensitivePolicy(str, str2, sb.toString()));
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery("select count(*) from " + str2, null);
            if (cursorRawQuery.moveToFirst() && cursorRawQuery.getInt(0) > 0) {
                sQLiteDatabase.execSQL("VACUUM");
            }
            if (!cursorRawQuery.isClosed()) {
                cursorRawQuery.close();
            }
            sQLiteDatabase.setSdpDatabase();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (0 != 0) {
                cursor.close();
            }
            return false;
        }
    }

    public boolean updateStateToDB(SQLiteDatabase sQLiteDatabase, String str, int i) throws InterruptedException, SQLException {
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "updateStateToDB :: invalid DB");
            return false;
        }
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("updateStateToDB :: can't find engine "), this.mAlias, "SdpDatabase");
            return false;
        }
        if (engineInfo.getState() != i) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateStateToDB :: invalid state : ", " (current stat : ");
            sbM.append(engineInfo.getState());
            sbM.append(")");
            Log.d("SdpDatabase", sbM.toString());
            return false;
        }
        Cursor cursor = null;
        try {
            Log.d("SdpDatabase", "updateSDPStateToDB called with dbalias = " + str + " sdpState = " + i);
            String strConcat = str == null ? "" : str.concat(".");
            if (i == 1) {
                sQLiteDatabase.execSQL("pragma " + strConcat + "sdp_locked;");
            } else if (i == 2) {
                sQLiteDatabase.execSQL("pragma " + strConcat + "sdp_unlocked;");
                Cursor cursorRawQuery = null;
                int i2 = 1;
                while (i2 > 0) {
                    try {
                        Log.d("SdpDatabase", "calling next : pragma runoneconvert  in sdpState = " + i);
                        cursorRawQuery = sQLiteDatabase.rawQuery("pragma " + strConcat + "sdp_run_one_convert", null);
                        if (cursorRawQuery != null && cursorRawQuery.getCount() != 0) {
                            if (cursorRawQuery.moveToFirst()) {
                                i2 = cursorRawQuery.getInt(0);
                            }
                            Thread.sleep(30L);
                            cursorRawQuery.close();
                        }
                        Log.d("SdpDatabase", "Cursor is null or there are no rows after query...");
                        if (cursorRawQuery != null) {
                            cursorRawQuery.close();
                        }
                        Log.d("SdpDatabase", "DONE calling all pragma runoneconvert  in sdpState = " + i);
                    } catch (Exception e) {
                        e = e;
                        cursor = cursorRawQuery;
                        e.printStackTrace();
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    }
                }
                Log.d("SdpDatabase", "DONE calling all pragma runoneconvert  in sdpState = " + i);
            }
            return true;
        } catch (Exception e2) {
            e = e2;
        }
    }

    public boolean updateStateToDB(SQLiteDatabase sQLiteDatabase, int i) {
        return updateStateToDB(sQLiteDatabase, null, i);
    }
}
