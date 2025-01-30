package com.samsung.android.knox.analytics.database;

import android.app.job.JobInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Bundle;
import com.samsung.android.knox.analytics.model.Event;
import com.samsung.android.knox.analytics.model.EventList;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.ZipHandler;
import com.samsung.android.knox.analytics.util.ZipResult;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import org.json.JSONException;

/* loaded from: classes5.dex */
class DatabaseCryptoAdapter {
  private static final String TAG =
      "[KnoxAnalytics] " + DatabaseCryptoAdapter.class.getSimpleName();
  private final CryptoHandler mCryptoHandler;
  private final DatabaseHelper mDbHelper;
  private int mVersioningIdCache = -1;
  private boolean mVersioningCompleted = false;
  private final Object mVersioningCompletedLock = new Object();

  public DatabaseCryptoAdapter(Context context) {
    Log.m286d(TAG, "constructor()");
    this.mCryptoHandler = new CryptoHandler();
    this.mDbHelper = new DatabaseHelper(context);
    generateKeys();
  }

  private void generateKeys() {
    generateGCMKey();
    generateCBCKey();
  }

  private void generateCBCKey() {
    this.mCryptoHandler.generateCBCKey();
  }

  private void generateGCMKey() {
    if (this.mCryptoHandler.isGCMKeyGenerated()) {
      Log.m286d(TAG, "generateGCMKey(): Key already generated");
    } else if (this.mCryptoHandler.generateGCMKey()) {
      Log.m286d(TAG, "generateGCMKey(): Key generated");
      markEventIdOnKeyGeneration();
    }
  }

  private void markEventIdOnKeyGeneration() {
    this.mDbHelper.setSyntheticRowId();
  }

  public Bundle performCompressedEventsTransaction(Bundle extras) {
    String str = TAG;
    Log.m286d(str, "performCompressedEventsTransaction()");
    if (extras == null || extras.isEmpty()) {
      Log.m286d(str, "performCompressedEventsTransaction(): Null argument. Aborting");
      return null;
    }
    ContentValues cv = (ContentValues) extras.getParcelable(Contract.CompressedEvents.Keys.f3016CV);
    if (cv == null || cv.size() <= 0) {
      Log.m286d(str, "performCompressedEventsTransaction(): Null argument. Aborting");
      return null;
    }
    byte[] encriptedContent = this.mCryptoHandler.encryptBlob(cv.getAsByteArray("content"));
    cv.put("content", encriptedContent);
    boolean res = this.mDbHelper.performCompressedEventsTransaction(cv);
    Bundle result = new Bundle();
    result.putBoolean(Contract.CompressedEvents.METHOD_PERFORM_COMPRESSED_EVENTS_TRANSACTION, res);
    return result;
  }

  public long addEvent(ContentValues values) {
    Log.m286d(TAG, "addEvent()");
    if (values.containsKey("data")) {
      byte[] encData = this.mCryptoHandler.encrypt(values.getAsString("data"));
      values.put("data", encData);
      values.put("bulk", (Integer) 1);
    }
    waitVersioningCompleted();
    values.put(Contract.Events.Field.VERSIONING_ID, Integer.valueOf(this.mVersioningIdCache));
    return this.mDbHelper.addEvent(values, 1);
  }

  public long addBulkEvents(Bundle extra) {
    String str = TAG;
    Log.m286d(str, "addBulkEvents()");
    long id = extra.getLong("id");
    List<String> eventList = extra.getStringArrayList(Contract.Events.Extra.EVENTS_LIST);
    if (eventList == null || eventList.isEmpty()) {
      Log.m286d(str, "addBulkEvents(): eventList is invalid");
      return -1L;
    }
    byte[] encEvents = this.mCryptoHandler.encryptBulk(eventList);
    ContentValues values = new ContentValues();
    values.put("id", Long.valueOf(id));
    values.put("data", encEvents);
    values.put("bulk", Integer.valueOf(eventList.size()));
    waitVersioningCompleted();
    values.put(Contract.Events.Field.VERSIONING_ID, Integer.valueOf(this.mVersioningIdCache));
    return this.mDbHelper.addEvent(values, 1);
  }

  public long addCleanedEvent(ContentValues values) {
    Log.m286d(TAG, "addCleanedEvent()");
    waitVersioningCompleted();
    values.put(Contract.Events.Field.VERSIONING_ID, Integer.valueOf(this.mVersioningIdCache));
    return this.mDbHelper.addEvent(values, 0);
  }

  public void waitVersioningCompleted() {
    Log.m286d(TAG, "waitVersioningCompleted()");
    while (!this.mVersioningCompleted) {
      try {
        synchronized (this.mVersioningCompletedLock) {
          this.mVersioningCompletedLock.wait(JobInfo.MIN_BACKOFF_MILLIS);
        }
        Log.m286d(TAG, "waitVersioningCompleted(): after wait");
      } catch (InterruptedException e) {
        Log.m287e(TAG, "waitVersioningCompleted(): Interrupted exception");
      }
    }
  }

  public Cursor getEventChunk(Integer size, boolean onlyPlainEvents) {
    Log.m286d(TAG, "getEventChunk(" + size + ", " + onlyPlainEvents + " )");
    if (!onlyPlainEvents && getCompressedEventsCount() > 0) {
      return createMergedCursor(size);
    }
    return new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, size);
  }

  private Cursor createMergedCursor(Integer requestedSize) {
    String str = TAG;
    Log.m286d(str, "createMergedCursor(" + requestedSize + NavigationBarInflaterView.KEY_CODE_END);
    if (requestedSize == null) {
      return createCursorWithAllEvents();
    }
    int compressedChunkLimit = checkCompressedChunksLimit(requestedSize.intValue());
    int compressedEventsCount = getTotalCompressedEvents(compressedChunkLimit);
    int numberOfPlainEvents = getTotalPlainEvents(requestedSize.intValue(), compressedEventsCount);
    Log.m286d(
        str,
        "createCursorWith: "
            + compressedEventsCount
            + " compressed events and "
            + numberOfPlainEvents
            + " plain events");
    if (numberOfPlainEvents == 0) {
      return createCursorOnlyWithCompressedEvents(compressedChunkLimit);
    }
    return createCursorWithEventsSizeSpecified(numberOfPlainEvents, compressedChunkLimit);
  }

  /* JADX WARN: Code restructure failed: missing block: B:12:0x0014, code lost:

     r3 = r1.getInt(0);
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  private int getTotalPlainEvents(int requestedSize, int totalCompressedEvents) {
    Cursor eventsCursor;
    int events;
    int numberOfPlainEvents = 0;
    if (requestedSize == totalCompressedEvents
        || (eventsCursor = this.mDbHelper.getEventCountCursor()) == null) {
      return 0;
    }
    int totalSize = totalCompressedEvents;
    while (eventsCursor.moveToNext() && (totalSize = totalSize + events) <= requestedSize) {
      try {
        numberOfPlainEvents += events;
      } finally {
        eventsCursor.close();
      }
    }
    return numberOfPlainEvents;
  }

  private Cursor createCursorWithEventsSizeSpecified(
      int plainEventsSize, int compressedEventsSize) {
    Log.m286d(TAG, "createCursorWithEventsSizeSpecified(): query " + plainEventsSize + " events");
    Cursor compressedCursor = getCompressedEvents(Integer.valueOf(compressedEventsSize));
    if (compressedCursor == null || compressedCursor.getCount() <= 0) {
      if (compressedCursor != null) {
        compressedCursor.close();
        return null;
      }
      return null;
    }
    return new MergeCursor(
        new Cursor[] {
          compressedCursor,
          new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, Integer.valueOf(plainEventsSize))
        });
  }

  private Cursor createCursorOnlyWithCompressedEvents(int compressedChunkLimit) {
    Log.m286d(TAG, "createCursorOnlyWithCompressedEvents(): query only compressed events");
    Cursor cursor = getCompressedEvents(Integer.valueOf(compressedChunkLimit));
    if (cursor == null || cursor.getCount() <= 0) {
      if (cursor != null) {
        cursor.close();
        return null;
      }
      return null;
    }
    return new MergeCursor(new Cursor[] {cursor});
  }

  private Cursor createCursorWithAllEvents() {
    Log.m286d(TAG, "createCursorWithAllEvents()");
    Cursor cursor = getCompressedEvents(null);
    if (cursor == null || cursor.getCount() <= 0) {
      if (cursor != null) {
        cursor.close();
      }
      return null;
    }
    return new MergeCursor(
        new Cursor[] {cursor, new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, null)});
  }

  public long deleteEventChunk(long size, int type) {
    return deleteEventChunk(size, 0L, type);
  }

  public long deleteEventChunk(long size, long numberOfEvents, int type) {
    Log.m286d(TAG, "deleteEventChunk(size:" + size + ", type:" + type + ") ");
    switch (type) {
      case 0:
        return deleteCleanedEventChunk(size);
      case 1:
        return deleteMergedChunk(size, numberOfEvents);
      default:
        return 0L;
    }
  }

  private long deleteMergedChunk(long size, long numberOfEvents) {
    String str = TAG;
    Log.m286d(
        str,
        "deleteMergedChunk("
            + size
            + ", "
            + numberOfEvents
            + NavigationBarInflaterView.KEY_CODE_END);
    long compressedChunkCountValue = this.mDbHelper.getCompressedEventCountValue();
    if (compressedChunkCountValue <= 0) {
      return this.mDbHelper.deleteEventChunk(size, 1);
    }
    int compressedChunkCount = checkCompressedChunksLimit(numberOfEvents);
    int compressedEventsCount = getTotalCompressedEvents(compressedChunkCount);
    long chunkAffected = this.mDbHelper.deleteCompressedEventChunk(compressedChunkCount);
    if (chunkAffected != 0) {
      long remaining =
          calculateRemainingEventsForDelete((int) numberOfEvents, compressedEventsCount);
      if (remaining == 0) {
        return size;
      }
      long totalDeleted = size - remaining;
      return totalDeleted + this.mDbHelper.deleteEventChunk(remaining, 1);
    }
    Log.m287e(str, "deleteMergedChunk(): Some error occurred when deleting.");
    return 0L;
  }

  public int getTotalCompressedEvents(int rows) {
    Log.m286d(TAG, "getTotalCompressedEvents(" + rows + NavigationBarInflaterView.KEY_CODE_END);
    return this.mDbHelper.getTotalCompressedEvent(Integer.valueOf(rows));
  }

  private int checkCompressedChunksLimit(long numberOfEvents) {
    Log.m286d(
        TAG,
        "checkCompressedChunksLimit(" + numberOfEvents + NavigationBarInflaterView.KEY_CODE_END);
    int compressedEvents = 0;
    int compressedChunksToDelete = 0;
    if (numberOfEvents > 0) {
      Cursor cursor = this.mDbHelper.getTotalCompressedEventCursor();
      if (cursor != null) {
        try {
          if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
              compressedEvents += cursor.getInt(0);
              if (compressedEvents > numberOfEvents) {
                break;
              }
              compressedChunksToDelete++;
            }
          }
        } finally {
          if (cursor != null) {
            cursor.close();
          }
        }
      }
    }
    return compressedChunksToDelete;
  }

  private int calculateRemainingEventsForDelete(int numberOfEvents, int compressedDeleted) {
    int totalEventsToDelete = numberOfEvents - compressedDeleted;
    Log.m286d(
        TAG,
        "calculateRemainingEventsForDelete("
            + numberOfEvents
            + ", "
            + compressedDeleted
            + "): totalToDelete: "
            + totalEventsToDelete);
    Cursor cursor = this.mDbHelper.getEventCountCursor();
    int plainEvents = 0;
    int plainEventsToDelete = 0;
    if (cursor != null) {
      try {
        if (cursor.getCount() > 0) {
          while (cursor.moveToNext()
              && (plainEvents = plainEvents + cursor.getInt(0)) <= totalEventsToDelete) {
            plainEventsToDelete++;
          }
          return plainEventsToDelete;
        }
      } finally {
        if (cursor != null) {
          cursor.close();
        }
      }
    }
    if (cursor != null) {
      cursor.close();
    }
    return 0;
  }

  public long deleteCompressedEventChunk(long size) {
    Log.m286d(
        TAG, "deleteCompressedEventChunk(size: " + size + NavigationBarInflaterView.KEY_CODE_END);
    return this.mDbHelper.deleteCompressedEventChunk(size);
  }

  public long deleteCleanedEventChunk(long size) {
    return this.mDbHelper.deleteEventChunk(size, 0);
  }

  public long deleteUpTo(long id) {
    Log.m286d(TAG, "deleteUpTo(" + id + NavigationBarInflaterView.KEY_CODE_END);
    return this.mDbHelper.deleteUpTo(id);
  }

  public long deleteUntilTargetDbSize(long targetDbSize) {
    Log.m286d(
        TAG, "deleteUntilTargetDbSize(" + targetDbSize + NavigationBarInflaterView.KEY_CODE_END);
    return -1L;
  }

  public Cursor getLastId() {
    Log.m286d(TAG, "getLastId()");
    return this.mDbHelper.getLastId();
  }

  public Cursor getEventCount() {
    return this.mDbHelper.getEventCount();
  }

  public long getEventCountValue() {
    return this.mDbHelper.getEventCountValue();
  }

  public int addVersioningBlob(ContentValues values) {
    Log.m286d(TAG, "addVersioning()");
    int ret = this.mDbHelper.addVersioningBlob(values);
    if (ret == -1) {
      return -1;
    }
    this.mVersioningIdCache = ret;
    return ret;
  }

  public Cursor getVersioningBlob() {
    Log.m286d(TAG, "getVersioningBlob()");
    Cursor cursor = this.mDbHelper.getVersioningBlob();
    updateVersioningCache(cursor);
    return cursor;
  }

  private void updateVersioningCache(Cursor getVersioningBlobCursor) {
    int index;
    Log.m286d(TAG, "updateVersioningCache()");
    if (getVersioningBlobCursor != null
        && getVersioningBlobCursor.getCount() > 0
        && getVersioningBlobCursor.moveToLast()
        && (index = getVersioningBlobCursor.getColumnIndex("id")) != -1) {
      this.mVersioningIdCache = getVersioningBlobCursor.getInt(index);
    }
  }

  public long deleteFromVersion(long versioningBlobId) {
    Log.m286d(TAG, "deleteFromVersion()");
    return this.mDbHelper.deleteFromVersion(versioningBlobId);
  }

  public long addFeatureBlacklist(ContentValues values) {
    Log.m286d(TAG, "addFeatureBlacklist()");
    long ret = this.mDbHelper.deleteFeaturesBlacklist();
    for (String key : values.keySet()) {
      String value = values.getAsString(key);
      if (value == null) {
        Log.m286d(TAG, "addFeatureBlacklist(): null value");
      } else {
        ContentValues finalCv = new ContentValues();
        finalCv.put("feature", key);
        finalCv.put("event", value);
        ret += this.mDbHelper.addFeaturesBlacklist(finalCv);
      }
    }
    return ret;
  }

  public long getCompressedEventsCount() {
    return this.mDbHelper.getCompressedEventCountValue();
  }

  public Cursor getCompressedEvents(Integer limit) {
    String str = TAG;
    Log.m286d(str, "getCompressedEvents()");
    if (this.mDbHelper.getCompressedEventCountValue() <= 0) {
      Log.m286d(str, "There is no compressed data to be queried");
      return null;
    }
    List<ZipResult> zips = new ArrayList<>();
    try {
      Cursor cursor = this.mDbHelper.getCompressedEventChunk(limit);
      if (cursor != null) {
        try {
          if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
              byte[] encryptedBytes = cursor.getBlob(cursor.getColumnIndex("content"));
              byte[] compressedContent = this.mCryptoHandler.decryptBlob(encryptedBytes);
              int length =
                  cursor.getInt(cursor.getColumnIndex(Contract.CompressedEvents.Field.LENGTH));
              int originalLength =
                  cursor.getInt(
                      cursor.getColumnIndex(Contract.CompressedEvents.Field.ORIGINAL_LENGTH));
              if (length != -1 || originalLength != -1 || compressedContent.length > 0) {
                zips.add(new ZipResult(compressedContent, length, originalLength));
              }
            }
            Cursor createCursor = createCursor(zips);
            if (cursor != null) {
              cursor.close();
            }
            return createCursor;
          }
        } finally {
        }
      }
      if (cursor != null) {
        cursor.close();
      }
      return null;
    } catch (GeneralSecurityException e) {
      Log.m288e(TAG, "getCompressedEvents(): ", e);
      return null;
    }
  }

  private Cursor createCursor(List<ZipResult> zips) {
    MatrixCursor cursor =
        new MatrixCursor(
            new String[] {"id", Contract.Events.Field.VERSIONING_ID, "bulk", "data"}, 1);
    for (ZipResult zip : zips) {
      EventList events = getEventsList(zip);
      if (events == null || events.length() <= 0) {
        Log.m286d(TAG, "createCursor(): Null events received");
      } else {
        for (int i = 0; i < events.length(); i++) {
          try {
            String json = events.getString(i);
            Event event = new Event(json);
            cursor.addRow(
                new Object[] {
                  Integer.valueOf(event.getInt("id")),
                  Integer.valueOf(event.getInt(Contract.Events.Field.VERSIONING_ID)),
                  Integer.valueOf(event.getInt("bulk")),
                  event.getString("data")
                });
          } catch (JSONException e) {
            Log.m288e(TAG, "createCursor(): Parsing error object.", e);
          }
        }
      }
    }
    return cursor;
  }

  private EventList getEventsList(ZipResult zip) {
    try {
      return ZipHandler.inflate(zip);
    } catch (DataFormatException e) {
      Log.m286d(TAG, "getEventsList(): Data is malformed");
      return null;
    } catch (JSONException e2) {
      Log.m286d(TAG, "getEventsList(): Parsing error");
      return null;
    }
  }

  public Cursor getFeatureBlacklistCursor() {
    Log.m286d(TAG, "getFeatureBlacklistCursor()");
    return this.mDbHelper.getFeaturesBlacklist();
  }

  public long deleteFeatureBlacklist() {
    Log.m286d(TAG, "deleteFeatureBlacklist()");
    return this.mDbHelper.deleteFeaturesBlacklist();
  }

  public Cursor getDatabaseSizeCursor() {
    return this.mDbHelper.getCurrentDatabaseSizeCursor();
  }

  public long getDatabaseSizeInBytes() {
    return this.mDbHelper.getCurrentDatabaseSizeInBytes();
  }

  public Cursor getCleanedEventsCursor(Integer size) {
    return this.mDbHelper.getCleanedEventsCursor(size);
  }

  public void notifyVersioningCompleted() {
    synchronized (this.mVersioningCompletedLock) {
      this.mVersioningCompleted = true;
      this.mVersioningCompletedLock.notifyAll();
    }
  }

  public long addFeatureWhitelist(ContentValues values) {
    Log.m286d(TAG, "addFeatureWhitelist()");
    long ret = 0;
    for (String key : values.keySet()) {
      Integer value = values.getAsInteger(key);
      if (value == null) {
        Log.m286d(TAG, "addFeatureWhitelist(): null value");
      } else if (value.intValue() == 1) {
        ret += this.mDbHelper.deleteFeatureWhitelist(key);
      } else {
        ContentValues finalCv = new ContentValues();
        finalCv.put("feature", key);
        finalCv.put("enable_type", value);
        ret += this.mDbHelper.addFeaturesWhitelist(finalCv);
      }
    }
    return ret;
  }

  public Cursor getFeatureWhitelistCursor() {
    Log.m286d(TAG, "getFeatureWhitelistCursor()");
    return this.mDbHelper.getFeaturesWhitelist();
  }

  public long deleteFeatureWhitelist(String[] features) {
    Log.m286d(TAG, "deleteFeatureWhitelist()");
    return this.mDbHelper.deleteFeaturesWhitelist(features);
  }

  public long addB2CFeatures(ContentValues values) {
    Log.m286d(TAG, "addB2CFeatures()");
    return this.mDbHelper.addB2CFeatures(values);
  }

  public Cursor getB2CFeaturesCursor(String[] packageName) {
    Log.m286d(TAG, "getB2CFeatures()");
    return this.mDbHelper.getB2CFeatures(packageName);
  }

  public long deleteB2CFeatures(String[] packageName) {
    Log.m286d(TAG, "deleteB2CFeatures()");
    return this.mDbHelper.deleteB2CFeatures(packageName);
  }
}
