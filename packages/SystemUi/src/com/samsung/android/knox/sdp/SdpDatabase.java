package com.samsung.android.knox.sdp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import com.samsung.android.knox.sdp.core.SdpException;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SdpDatabase {
    private static final String CLASS_NAME = "SdpDatabase";
    private static final boolean DEBUG = false;
    private static final String TAG = "SdpDatabase";
    private static final boolean runAllConvert = false;
    private String mAlias;
    private final ContextInfo mContextInfo;
    private int mEngineId;

    public SdpDatabase(String str) {
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

    private void enforcePermission() {
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface != null) {
            try {
                if (asInterface.isLicensed() == 0) {
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
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(AbstractC0866xb1ce8deb.m87m("table=", str, ";columns=", str2, ";engine_id="), i, ";");
    }

    private String formSensitivePolicy(String str, String str2, String str3) {
        String formSensitiveColumnStmt = formSensitiveColumnStmt(this.mEngineId, str2, str3);
        if (formSensitiveColumnStmt == null) {
            return null;
        }
        return MotionLayout$$ExternalSyntheticOutline0.m22m("pragma ", str == null ? "" : str.concat("."), "set_sensitive_columns(\"", formSensitiveColumnStmt, "\");");
    }

    private SdpEngineInfo getEngineInfo(String str) {
        try {
            IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
            if (asInterface != null) {
                return asInterface.getEngineInfo(str);
            }
            return null;
        } catch (RemoteException e) {
            Log.e("SdpDatabase", "Failed to talk with sdp service...", e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0071, code lost:
    
        if (r5.isClosed() != false) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0073, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0076, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0064, code lost:
    
        if (r5.isClosed() == false) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isSensitive(SQLiteDatabase sQLiteDatabase, String str, String str2, String str3) {
        String concat;
        boolean z = false;
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "isSensitive :: invalid DB");
            return false;
        }
        if (this.mEngineId < 0) {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("isSensitive :: invalid engine "), this.mAlias, "SdpDatabase");
            return false;
        }
        Cursor cursor = null;
        if (str == null) {
            concat = "";
        } else {
            try {
                try {
                    concat = str.concat(".");
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            } catch (Throwable th) {
                if (!cursor.isClosed()) {
                    cursor.close();
                }
                throw th;
            }
        }
        cursor = sQLiteDatabase.rawQuery("pragma " + concat + "get_sensitive_columns(" + str2 + ")", null);
        if (cursor.moveToFirst()) {
            while (true) {
                if (str3.equals(cursor.getString(0))) {
                    z = true;
                    break;
                }
                if (!cursor.moveToNext()) {
                    break;
                }
            }
        }
    }

    public boolean setSensitive(SQLiteDatabase sQLiteDatabase, String str, String str2, List<String> list) {
        EnterpriseLicenseManager.log(this.mContextInfo, "SdpDatabase.setSensitive");
        StringBuilder sb = new StringBuilder();
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "setSensitive :: invalid DB");
            return false;
        }
        if (this.mEngineId < 0) {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("setSensitive :: invalid engine "), this.mAlias, "SdpDatabase");
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
            Cursor rawQuery = sQLiteDatabase.rawQuery("select count(*) from " + str2, null);
            if (rawQuery.moveToFirst() && rawQuery.getInt(0) > 0) {
                sQLiteDatabase.execSQL("VACUUM");
            }
            if (!rawQuery.isClosed()) {
                rawQuery.close();
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

    public boolean updateStateToDB(SQLiteDatabase sQLiteDatabase, String str, int i) {
        Cursor cursor;
        Exception e;
        if (sQLiteDatabase == null) {
            Log.d("SdpDatabase", "updateStateToDB :: invalid DB");
            return false;
        }
        SdpEngineInfo engineInfo = getEngineInfo(this.mAlias);
        if (engineInfo == null) {
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("updateStateToDB :: can't find engine "), this.mAlias, "SdpDatabase");
            return false;
        }
        if (engineInfo.getState() != i) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("updateStateToDB :: invalid state : ", i, " (current stat : ");
            m1m.append(engineInfo.getState());
            m1m.append(")");
            Log.d("SdpDatabase", m1m.toString());
            return false;
        }
        try {
            Log.d("SdpDatabase", "updateSDPStateToDB called with dbalias = " + str + " sdpState = " + i);
            String concat = str == null ? "" : str.concat(".");
            if (i == 1) {
                sQLiteDatabase.execSQL("pragma " + concat + "sdp_locked;");
            } else if (i == 2) {
                sQLiteDatabase.execSQL("pragma " + concat + "sdp_unlocked;");
                cursor = null;
                int i2 = 1;
                while (i2 > 0) {
                    try {
                        Log.d("SdpDatabase", "calling next : pragma runoneconvert  in sdpState = " + i);
                        cursor = sQLiteDatabase.rawQuery("pragma " + concat + "sdp_run_one_convert", null);
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                i2 = cursor.getInt(0);
                            }
                            Thread.sleep(30L);
                            cursor.close();
                        }
                        Log.d("SdpDatabase", "Cursor is null or there are no rows after query...");
                        if (cursor != null) {
                            cursor.close();
                        }
                        Log.d("SdpDatabase", "DONE calling all pragma runoneconvert  in sdpState = " + i);
                    } catch (Exception e2) {
                        e = e2;
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
        } catch (Exception e3) {
            cursor = null;
            e = e3;
        }
    }

    public boolean updateStateToDB(SQLiteDatabase sQLiteDatabase, int i) {
        return updateStateToDB(sQLiteDatabase, null, i);
    }
}
