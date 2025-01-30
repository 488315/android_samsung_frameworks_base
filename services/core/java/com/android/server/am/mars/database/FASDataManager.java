package com.android.server.am.mars.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Slog;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class FASDataManager {
  public final String TAG;
  public Context context;
  public Boolean mIsDisableReasonColumnExist;
  public Boolean mIsPreBatteryUsageColumnExist;

  public abstract class FASDataManagerHolder {
    public static final FASDataManager INSTANCE = new FASDataManager();
  }

  public FASDataManager() {
    this.TAG = FASDataManager.class.getSimpleName();
    this.context = null;
    this.mIsPreBatteryUsageColumnExist = null;
    this.mIsDisableReasonColumnExist = null;
  }

  public static FASDataManager getInstance() {
    return FASDataManagerHolder.INSTANCE;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  public Context getContext() {
    return this.context;
  }

  public void init(Context context) {
    setContext(context);
  }

  /* JADX WARN: Code restructure failed: missing block: B:21:0x003b, code lost:

     if (r0 == null) goto L17;
  */
  /* JADX WARN: Code restructure failed: missing block: B:8:0x0025, code lost:

     if (r0 != null) goto L16;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final Boolean checkPreBatteryUsageColumnExist() {
    Boolean bool = this.mIsPreBatteryUsageColumnExist;
    if (bool != null) {
      return bool;
    }
    this.mIsPreBatteryUsageColumnExist = new Boolean(false);
    Cursor cursor = null;
    try {
      try {
        cursor =
            getContext()
                .getContentResolver()
                .query(
                    FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI,
                    FASTableContract.preBattetyUsageProjection,
                    null,
                    null,
                    null);
        this.mIsPreBatteryUsageColumnExist = Boolean.TRUE;
      } catch (SQLiteException unused) {
        Slog.e(this.TAG, "checkPreBatteryUsageColumnExist-sql, catch no column exception!");
        if (cursor != null) {
          cursor.close();
        }
        return this.mIsPreBatteryUsageColumnExist;
      } catch (IllegalArgumentException unused2) {
        Slog.e(this.TAG, "checkPreBatteryUsageColumnExist, catch no column exception!");
      }
    } catch (Throwable th) {
      if (cursor != null) {
        cursor.close();
      }
      throw th;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:21:0x003b, code lost:

     if (r0 == null) goto L17;
  */
  /* JADX WARN: Code restructure failed: missing block: B:8:0x0025, code lost:

     if (r0 != null) goto L16;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final Boolean checkDisableReasonColumnExist() {
    Boolean bool = this.mIsDisableReasonColumnExist;
    if (bool != null) {
      return bool;
    }
    this.mIsDisableReasonColumnExist = new Boolean(false);
    Cursor cursor = null;
    try {
      try {
        cursor =
            getContext()
                .getContentResolver()
                .query(
                    FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI,
                    FASTableContract.disableReasonProjection,
                    null,
                    null,
                    null);
        this.mIsDisableReasonColumnExist = Boolean.TRUE;
      } catch (SQLiteException unused) {
        Slog.e(this.TAG, "checkDisableReasonColumnExist-sql, catch no column exception!");
        if (cursor != null) {
          cursor.close();
        }
        return this.mIsDisableReasonColumnExist;
      } catch (IllegalArgumentException unused2) {
        Slog.e(this.TAG, "checkDisableReasonColumnExist, catch no column exception!");
      }
    } catch (Throwable th) {
      if (cursor != null) {
        cursor.close();
      }
      throw th;
    }
  }

  public ArrayList getFASDataFromDB() {
    String[] strArr;
    Cursor cursor;
    FASEntity build;
    if (checkDisableReasonColumnExist().booleanValue()
        && checkPreBatteryUsageColumnExist().booleanValue()) {
      strArr = FASTableContract.FASQueryProjectionV3;
    } else {
      strArr =
          checkPreBatteryUsageColumnExist().booleanValue()
              ? FASTableContract.FASQueryProjectionV2
              : FASTableContract.FASQueryProjectionV1;
    }
    try {
      cursor =
          getContext()
              .getContentResolver()
              .query(FASTableContract.SMART_MGR_FORCED_APP_STANDBY_URI, strArr, null, null, null);
    } catch (Exception e) {
      Slog.e(this.TAG, "Exception with contentResolver : " + e.getMessage());
      e.printStackTrace();
      cursor = null;
    }
    if (cursor != null) {
      ArrayList arrayList = new ArrayList();
      while (cursor.moveToNext()) {
        if (cursor.getString(0) != null) {
          if (checkDisableReasonColumnExist().booleanValue()
              && checkPreBatteryUsageColumnExist().booleanValue()) {
            build =
                new FASEntityBuilder()
                    .setStrPkgName(cursor.getString(0))
                    .setStrUid(cursor.getString(1))
                    .setStrMode(cursor.getString(2))
                    .setStrNew(cursor.getString(3))
                    .setStrFasReason(cursor.getString(4))
                    .setStrExtras(cursor.getString(5))
                    .setStrResetTime(cursor.getString(6))
                    .setStrPackageType(cursor.getString(7))
                    .setStrLevel(cursor.getString(8))
                    .setStrDisableType(cursor.getString(9))
                    .setStrDisableResetTime(cursor.getString(10))
                    .setStrPreBatteryUsage(cursor.getString(11))
                    .setStrDisableReason(cursor.getString(12))
                    .build();
          } else if (checkPreBatteryUsageColumnExist().booleanValue()) {
            build =
                new FASEntityBuilder()
                    .setStrPkgName(cursor.getString(0))
                    .setStrUid(cursor.getString(1))
                    .setStrMode(cursor.getString(2))
                    .setStrNew(cursor.getString(3))
                    .setStrFasReason(cursor.getString(4))
                    .setStrExtras(cursor.getString(5))
                    .setStrResetTime(cursor.getString(6))
                    .setStrPackageType(cursor.getString(7))
                    .setStrLevel(cursor.getString(8))
                    .setStrDisableType(cursor.getString(9))
                    .setStrDisableResetTime(cursor.getString(10))
                    .setStrPreBatteryUsage(cursor.getString(11))
                    .build();
          } else {
            build =
                new FASEntityBuilder()
                    .setStrPkgName(cursor.getString(0))
                    .setStrUid(cursor.getString(1))
                    .setStrMode(cursor.getString(2))
                    .setStrNew(cursor.getString(3))
                    .setStrFasReason(cursor.getString(4))
                    .setStrExtras(cursor.getString(5))
                    .setStrResetTime(cursor.getString(6))
                    .setStrPackageType(cursor.getString(7))
                    .setStrLevel(cursor.getString(8))
                    .setStrDisableType(cursor.getString(9))
                    .setStrDisableResetTime(cursor.getString(10))
                    .build();
          }
          if (build != null) {
            arrayList.add(build);
          }
        }
      }
      Slog.d(this.TAG, "getFASDataFromDB fasEntityList size : " + arrayList.size());
      cursor.close();
      return arrayList;
    }
    Slog.e(this.TAG, "getFASDataFromDB no database!");
    return null;
  }
}
