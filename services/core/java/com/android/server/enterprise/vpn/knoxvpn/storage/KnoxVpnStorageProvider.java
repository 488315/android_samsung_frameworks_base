package com.android.server.enterprise.vpn.knoxvpn.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.android.server.enterprise.storage.EdmStorageProvider;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class KnoxVpnStorageProvider {
  public static KnoxVpnStorageProvider mDefaultProvider;
  public static EdmStorageProvider mEDM;
  public static Object mSynObj = new Object();
  public Context mContext;

  public KnoxVpnStorageProvider(Context context) {
    this.mContext = null;
    synchronized (mSynObj) {
      if (mEDM == null) {
        this.mContext = context;
        mEDM = new EdmStorageProvider(context);
      }
    }
  }

  public static synchronized KnoxVpnStorageProvider getInstance(Context context) {
    KnoxVpnStorageProvider knoxVpnStorageProvider;
    synchronized (KnoxVpnStorageProvider.class) {
      if (mDefaultProvider == null) {
        mDefaultProvider = new KnoxVpnStorageProvider(context);
      }
      knoxVpnStorageProvider = mDefaultProvider;
    }
    return knoxVpnStorageProvider;
  }

  public boolean putDataByFields(
      String str, String[] strArr, String[] strArr2, ContentValues contentValues) {
    return mEDM.putDataByFields(str, strArr, strArr2, contentValues);
  }

  public boolean deleteDataByFields(String str, String[] strArr, String[] strArr2) {
    return mEDM.deleteDataByFields(str, strArr, strArr2);
  }

  public ArrayList getDataByFields(
      String str, String[] strArr, String[] strArr2, String[] strArr3) {
    return mEDM.getDataByFields(str, strArr, strArr2, strArr3);
  }

  /* JADX WARN: Code restructure failed: missing block: B:13:0x006b, code lost:

     if (r3 == null) goto L25;
  */
  /* JADX WARN: Code restructure failed: missing block: B:8:0x004a, code lost:

     if (r3 == null) goto L25;
  */
  /* JADX WARN: Code restructure failed: missing block: B:9:0x004c, code lost:

     r3.close();
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public int getProfileId() {
    EdmStorageProvider edmStorageProvider = mEDM;
    int i = -1;
    if (edmStorageProvider != null) {
      Cursor query =
          edmStorageProvider
              .mEdmDbHelper
              .getReadableDatabase()
              .query(
                  "VpnAnalyticsTable", new String[] {"profileCount"}, null, null, null, null, null);
      try {
        if (query != null) {
          try {
            if (query.getCount() > 0) {
              if (query.getCount() > 0) {
                query.moveToFirst();
                i = query.getInt(0);
              }
            }
          } catch (Exception e) {
            Log.e("KnoxVpnStorageProvider", "Exception = " + Log.getStackTraceString(e));
          }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("profileCount", (Integer) 0);
        putDataByFields("VpnAnalyticsTable", null, null, contentValues);
        i = 0;
      } catch (Throwable th) {
        if (query != null) {
          query.close();
        }
        throw th;
      }
    }
    Log.d("KnoxVpnStorageProvider", "profile id : " + i);
    return i;
  }

  public ArrayList getDomainsByProfileName(String str) {
    if (mEDM == null) {
      return null;
    }
    ArrayList arrayList = new ArrayList();
    Cursor query =
        mEDM.mEdmDbHelper
            .getReadableDatabase()
            .query(
                "VpnPackageInfo",
                new String[] {"packageCid", "packageUid"},
                "profileName=?",
                new String[] {str},
                null,
                null,
                null);
    if (query != null) {
      Log.d(
          "KnoxVpnStorageProvider", "getDomainsByProfileName : cursor.size : " + query.getCount());
      try {
        query.moveToFirst();
        if (query.getCount() > 0) {
          do {
            int i = query.getInt(0);
            Log.d("KnoxVpnStorageProvider", "getDomainsByProfileName : cid : " + i);
            int i2 = query.getInt(1);
            Log.d("KnoxVpnStorageProvider", "getDomainsByProfileName : uid : " + i2);
            if (i2 != -1 && i >= 0) {
              arrayList.add(String.valueOf(i));
            }
          } while (query.moveToNext());
        }
      } finally {
        query.close();
      }
    }
    return arrayList;
  }
}
