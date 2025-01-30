package com.android.server.chimera.psitracker;

import android.content.ContentValues;
import android.content.Context;
import android.net.INetd;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class PSITrackerCollector {
  public static boolean DEBUG = true;
  public static int availableMemSaveCount = 0;
  public static boolean availableMemUpdateFlag = false;
  public final List mAvailableMemRecords;
  public Context mContext;

  public PSITrackerCollector(Context context) {
    DEBUG = PSITracker.DEBUG;
    this.mContext = context;
    this.mAvailableMemRecords = new ArrayList();
  }

  public PSIAvailableMemRecord generateAvailableMemRecord(long j, long j2, long j3, long j4) {
    PSIAvailableMemRecord pSIAvailableMemRecord = new PSIAvailableMemRecord();
    pSIAvailableMemRecord.availMem = j;
    pSIAvailableMemRecord.running = j2;
    pSIAvailableMemRecord.cached = j3;
    pSIAvailableMemRecord.checkTime = j4;
    return pSIAvailableMemRecord;
  }

  public void saveAvailableMemRecord(PSIAvailableMemRecord pSIAvailableMemRecord) {
    synchronized (this.mAvailableMemRecords) {
      if (DEBUG) {
        Log.d(
            "PSITrackerCollector",
            "save PSIAvailableMemRecord...\n (" + pSIAvailableMemRecord.toString() + ")");
      }
      this.mAvailableMemRecords.add(pSIAvailableMemRecord);
    }
  }

  public void saveAvailableMemRecords() {
    synchronized (this.mAvailableMemRecords) {
      for (int i = 0; i < this.mAvailableMemRecords.size(); i++) {
        saveAvailableMemRecord2db((PSIAvailableMemRecord) this.mAvailableMemRecords.get(i));
      }
      this.mAvailableMemRecords.clear();
    }
    if (DEBUG) {
      Log.d("PSITrackerCollector", "finish save PSIAvailableMemRecords successfully!");
    }
  }

  public boolean isAvailableMemRecordsSizeMax() {
    List list = this.mAvailableMemRecords;
    return list != null && list.size() > 0;
  }

  public final void saveAvailableMemRecord2db(PSIAvailableMemRecord pSIAvailableMemRecord) {
    PSIDBManager pSIDBManager = PSIDBManager.getInstance();
    if (pSIDBManager.isDBClosed()) {
      Log.e("PSITrackerCollector", "save PSIEntryAppRecord failed! db is closed!");
      return;
    }
    if (!availableMemUpdateFlag && availableMemSaveCount % 12160 == 0) {
      availableMemUpdateFlag = pSIDBManager.getRecordsCount(pSIDBManager.mAvailMemTable) >= 12160;
    }
    if (!availableMemUpdateFlag) {
      availableMemSaveCount++;
    }
    synchronized (PSIDBManager.LOCK_DATABASE) {
      if (!availableMemUpdateFlag) {
        pSIDBManager.beginTransaction();
        try {
          try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("availMem", Long.valueOf(pSIAvailableMemRecord.availMem));
            contentValues.put("cached", Long.valueOf(pSIAvailableMemRecord.cached));
            contentValues.put(INetd.IF_FLAG_RUNNING, Long.valueOf(pSIAvailableMemRecord.running));
            contentValues.put("checkTime", Long.valueOf(pSIAvailableMemRecord.checkTime));
            pSIDBManager.insert(pSIDBManager.mAvailMemTable, contentValues);
            pSIDBManager.setTransactionSuccessful();
            if (DEBUG) {
              Log.d("PSITrackerCollector", "save one PSIAvailableMemRecord successfully!");
            }
          } catch (Exception e) {
            Log.e("PSITrackerCollector", e.toString());
          }
        } finally {
        }
      } else {
        pSIDBManager.beginTransaction();
        try {
          try {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("availMem", Long.valueOf(pSIAvailableMemRecord.availMem));
            contentValues2.put("cached", Long.valueOf(pSIAvailableMemRecord.cached));
            contentValues2.put(INetd.IF_FLAG_RUNNING, Long.valueOf(pSIAvailableMemRecord.running));
            contentValues2.put("checkTime", Long.valueOf(pSIAvailableMemRecord.checkTime));
            long update =
                pSIDBManager.update(
                    pSIDBManager.mAvailMemTable,
                    contentValues2,
                    "checkTime=(select min(checkTime) from " + pSIDBManager.mAvailMemTable + ")",
                    null);
            pSIDBManager.setTransactionSuccessful();
            if (DEBUG) {
              Log.d(
                  "PSITrackerCollector",
                  "update one PSIAvailableMemRecord successfully!, num=" + update);
            }
          } finally {
          }
        } catch (Exception e2) {
          Log.e("PSITrackerCollector", e2.toString());
        }
      }
    }
  }
}
