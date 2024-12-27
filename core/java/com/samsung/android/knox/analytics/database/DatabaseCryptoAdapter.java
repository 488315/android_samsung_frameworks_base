package com.samsung.android.knox.analytics.database;

import android.app.job.JobInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;

import com.samsung.android.knox.analytics.model.Event;
import com.samsung.android.knox.analytics.model.EventList;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.ZipHandler;
import com.samsung.android.knox.analytics.util.ZipResult;

import org.json.JSONException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

/* loaded from: classes6.dex */
class DatabaseCryptoAdapter {
    private static final String TAG =
            "[KnoxAnalytics] " + DatabaseCryptoAdapter.class.getSimpleName();
    private final CryptoHandler mCryptoHandler;
    private final DatabaseHelper mDbHelper;
    private int mVersioningIdCache = -1;
    private boolean mVersioningCompleted = false;
    private final Object mVersioningCompletedLock = new Object();

    public DatabaseCryptoAdapter(Context context) {
        Log.d(TAG, "constructor()");
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
            Log.d(TAG, "generateGCMKey(): Key already generated");
        } else if (this.mCryptoHandler.generateGCMKey()) {
            Log.d(TAG, "generateGCMKey(): Key generated");
            markEventIdOnKeyGeneration();
        }
    }

    private void markEventIdOnKeyGeneration() {
        this.mDbHelper.setSyntheticRowId();
    }

    public Bundle performCompressedEventsTransaction(Bundle extras) {
        Log.d(TAG, "performCompressedEventsTransaction()");
        if (extras == null || extras.isEmpty()) {
            Log.d(TAG, "performCompressedEventsTransaction(): Null argument. Aborting");
            return null;
        }
        ContentValues cv = (ContentValues) extras.getParcelable(Contract.CompressedEvents.Keys.CV);
        if (cv == null || cv.size() <= 0) {
            Log.d(TAG, "performCompressedEventsTransaction(): Null argument. Aborting");
            return null;
        }
        byte[] encriptedContent = this.mCryptoHandler.encryptBlob(cv.getAsByteArray("content"));
        cv.put("content", encriptedContent);
        boolean res = this.mDbHelper.performCompressedEventsTransaction(cv);
        Bundle result = new Bundle();
        result.putBoolean(
                Contract.CompressedEvents.METHOD_PERFORM_COMPRESSED_EVENTS_TRANSACTION, res);
        return result;
    }

    public long addEvent(ContentValues values) {
        Log.d(TAG, "addEvent()");
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
        Log.d(TAG, "addBulkEvents()");
        long id = extra.getLong("id");
        List<String> eventList = extra.getStringArrayList(Contract.Events.Extra.EVENTS_LIST);
        if (eventList == null || eventList.isEmpty()) {
            Log.d(TAG, "addBulkEvents(): eventList is invalid");
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
        Log.d(TAG, "addCleanedEvent()");
        waitVersioningCompleted();
        values.put(Contract.Events.Field.VERSIONING_ID, Integer.valueOf(this.mVersioningIdCache));
        return this.mDbHelper.addEvent(values, 0);
    }

    public void waitVersioningCompleted() {
        Log.d(TAG, "waitVersioningCompleted()");
        while (!this.mVersioningCompleted) {
            try {
                synchronized (this.mVersioningCompletedLock) {
                    this.mVersioningCompletedLock.wait(JobInfo.MIN_BACKOFF_MILLIS);
                }
                Log.d(TAG, "waitVersioningCompleted(): after wait");
            } catch (InterruptedException e) {
                Log.e(TAG, "waitVersioningCompleted(): Interrupted exception");
            }
        }
    }

    public Cursor getEventChunk(Integer size, boolean onlyPlainEvents) {
        Log.d(TAG, "getEventChunk(" + size + ", " + onlyPlainEvents + " )");
        if (!onlyPlainEvents && getCompressedEventsCount() > 0) {
            return createMergedCursor(size);
        }
        return new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, size);
    }

    private Cursor createMergedCursor(Integer requestedSize) {
        Log.d(TAG, "createMergedCursor(" + requestedSize + NavigationBarInflaterView.KEY_CODE_END);
        if (requestedSize == null) {
            return createCursorWithAllEvents();
        }
        int compressedChunkLimit = checkCompressedChunksLimit(requestedSize.intValue());
        int compressedEventsCount = getTotalCompressedEvents(compressedChunkLimit);
        int numberOfPlainEvents =
                getTotalPlainEvents(requestedSize.intValue(), compressedEventsCount);
        Log.d(
                TAG,
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getTotalPlainEvents(int r5, int r6) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != r6) goto L4
            return r0
        L4:
            com.samsung.android.knox.analytics.database.DatabaseHelper r1 = r4.mDbHelper
            android.database.Cursor r1 = r1.getEventCountCursor()
            if (r1 != 0) goto Ld
            return r0
        Ld:
            r2 = r6
        Le:
            boolean r3 = r1.moveToNext()     // Catch: java.lang.Throwable -> L24
            if (r3 == 0) goto L1f
            r3 = 0
            int r3 = r1.getInt(r3)     // Catch: java.lang.Throwable -> L24
            int r2 = r2 + r3
            if (r2 <= r5) goto L1d
            goto L1f
        L1d:
            int r0 = r0 + r3
            goto Le
        L1f:
            r1.close()
            return r0
        L24:
            r2 = move-exception
            r1.close()
            throw r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.knox.analytics.database.DatabaseCryptoAdapter.getTotalPlainEvents(int,"
                    + " int):int");
    }

    private Cursor createCursorWithEventsSizeSpecified(
            int plainEventsSize, int compressedEventsSize) {
        Log.d(TAG, "createCursorWithEventsSizeSpecified(): query " + plainEventsSize + " events");
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
                    new EncryptedCursor(
                            this.mDbHelper, this.mCryptoHandler, Integer.valueOf(plainEventsSize))
                });
    }

    private Cursor createCursorOnlyWithCompressedEvents(int compressedChunkLimit) {
        Log.d(TAG, "createCursorOnlyWithCompressedEvents(): query only compressed events");
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
        Log.d(TAG, "createCursorWithAllEvents()");
        Cursor cursor = getCompressedEvents(null);
        if (cursor == null || cursor.getCount() <= 0) {
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
        return new MergeCursor(
                new Cursor[] {
                    cursor, new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, null)
                });
    }

    public long deleteEventChunk(long size, int type) {
        return deleteEventChunk(size, 0L, type);
    }

    public long deleteEventChunk(long size, long numberOfEvents, int type) {
        Log.d(TAG, "deleteEventChunk(size:" + size + ", type:" + type + ") ");
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
        Log.d(
                TAG,
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
            return this.mDbHelper.deleteEventChunk(remaining, 1) + totalDeleted;
        }
        Log.e(TAG, "deleteMergedChunk(): Some error occurred when deleting.");
        return 0L;
    }

    public int getTotalCompressedEvents(int rows) {
        Log.d(TAG, "getTotalCompressedEvents(" + rows + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDbHelper.getTotalCompressedEvent(Integer.valueOf(rows));
    }

    private int checkCompressedChunksLimit(long numberOfEvents) {
        Log.d(
                TAG,
                "checkCompressedChunksLimit("
                        + numberOfEvents
                        + NavigationBarInflaterView.KEY_CODE_END);
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
        Log.d(
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
                            && (plainEvents = plainEvents + cursor.getInt(0))
                                    <= totalEventsToDelete) {
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
        Log.d(
                TAG,
                "deleteCompressedEventChunk(size: "
                        + size
                        + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDbHelper.deleteCompressedEventChunk(size);
    }

    public long deleteCleanedEventChunk(long size) {
        return this.mDbHelper.deleteEventChunk(size, 0);
    }

    public long deleteUpTo(long id) {
        Log.d(TAG, "deleteUpTo(" + id + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDbHelper.deleteUpTo(id);
    }

    public long deleteUntilTargetDbSize(long targetDbSize) {
        Log.d(
                TAG,
                "deleteUntilTargetDbSize(" + targetDbSize + NavigationBarInflaterView.KEY_CODE_END);
        return -1L;
    }

    public Cursor getLastId() {
        Log.d(TAG, "getLastId()");
        return this.mDbHelper.getLastId();
    }

    public Cursor getEventCount() {
        return this.mDbHelper.getEventCount();
    }

    public long getEventCountValue() {
        return this.mDbHelper.getEventCountValue();
    }

    public int addVersioningBlob(ContentValues values) {
        Log.d(TAG, "addVersioning()");
        int ret = this.mDbHelper.addVersioningBlob(values);
        if (ret == -1) {
            return -1;
        }
        this.mVersioningIdCache = ret;
        return ret;
    }

    public Cursor getVersioningBlob() {
        Log.d(TAG, "getVersioningBlob()");
        Cursor cursor = this.mDbHelper.getVersioningBlob();
        updateVersioningCache(cursor);
        return cursor;
    }

    private void updateVersioningCache(Cursor getVersioningBlobCursor) {
        int index;
        Log.d(TAG, "updateVersioningCache()");
        if (getVersioningBlobCursor != null
                && getVersioningBlobCursor.getCount() > 0
                && getVersioningBlobCursor.moveToLast()
                && (index = getVersioningBlobCursor.getColumnIndex("id")) != -1) {
            this.mVersioningIdCache = getVersioningBlobCursor.getInt(index);
        }
    }

    public long deleteFromVersion(long versioningBlobId) {
        Log.d(TAG, "deleteFromVersion()");
        return this.mDbHelper.deleteFromVersion(versioningBlobId);
    }

    public long addFeatureBlacklist(ContentValues values) {
        Log.d(TAG, "addFeatureBlacklist()");
        long ret = this.mDbHelper.deleteFeaturesBlacklist();
        for (String key : values.keySet()) {
            String value = values.getAsString(key);
            if (value == null) {
                Log.d(TAG, "addFeatureBlacklist(): null value");
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
        Log.d(TAG, "getCompressedEvents()");
        if (this.mDbHelper.getCompressedEventCountValue() <= 0) {
            Log.d(TAG, "There is no compressed data to be queried");
            return null;
        }
        List<ZipResult> zips = new ArrayList<>();
        try {
            Cursor cursor = this.mDbHelper.getCompressedEventChunk(limit);
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            byte[] encryptedBytes =
                                    cursor.getBlob(cursor.getColumnIndex("content"));
                            byte[] compressedContent =
                                    this.mCryptoHandler.decryptBlob(encryptedBytes);
                            int length =
                                    cursor.getInt(
                                            cursor.getColumnIndex(
                                                    Contract.CompressedEvents.Field.LENGTH));
                            int originalLength =
                                    cursor.getInt(
                                            cursor.getColumnIndex(
                                                    Contract.CompressedEvents.Field
                                                            .ORIGINAL_LENGTH));
                            if (length != -1
                                    || originalLength != -1
                                    || compressedContent.length > 0) {
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
            Log.e(TAG, "getCompressedEvents(): ", e);
            return null;
        }
    }

    private Cursor createCursor(List<ZipResult> zips) {
        MatrixCursor cursor =
                new MatrixCursor(
                        new String[] {"id", Contract.Events.Field.VERSIONING_ID, "bulk", "data"},
                        1);
        for (ZipResult zip : zips) {
            EventList events = getEventsList(zip);
            if (events == null || events.length() <= 0) {
                Log.d(TAG, "createCursor(): Null events received");
            } else {
                for (int i = 0; i < events.length(); i++) {
                    try {
                        String json = events.getString(i);
                        Event event = new Event(json);
                        cursor.addRow(
                                new Object[] {
                                    Integer.valueOf(event.getInt("id")),
                                    Integer.valueOf(
                                            event.getInt(Contract.Events.Field.VERSIONING_ID)),
                                    Integer.valueOf(event.getInt("bulk")),
                                    event.getString("data")
                                });
                    } catch (JSONException e) {
                        Log.e(TAG, "createCursor(): Parsing error object.", e);
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
            Log.d(TAG, "getEventsList(): Data is malformed");
            return null;
        } catch (JSONException e2) {
            Log.d(TAG, "getEventsList(): Parsing error");
            return null;
        }
    }

    public Cursor getFeatureBlacklistCursor() {
        Log.d(TAG, "getFeatureBlacklistCursor()");
        return this.mDbHelper.getFeaturesBlacklist();
    }

    public long deleteFeatureBlacklist() {
        Log.d(TAG, "deleteFeatureBlacklist()");
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
        Log.d(TAG, "addFeatureWhitelist()");
        long ret = 0;
        for (String key : values.keySet()) {
            Integer value = values.getAsInteger(key);
            if (value == null) {
                Log.d(TAG, "addFeatureWhitelist(): null value");
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
        Log.d(TAG, "getFeatureWhitelistCursor()");
        return this.mDbHelper.getFeaturesWhitelist();
    }

    public long deleteFeatureWhitelist(String[] features) {
        Log.d(TAG, "deleteFeatureWhitelist()");
        return this.mDbHelper.deleteFeaturesWhitelist(features);
    }

    public long addB2CFeatures(ContentValues values) {
        Log.d(TAG, "addB2CFeatures()");
        return this.mDbHelper.addB2CFeatures(values);
    }

    public Cursor getB2CFeaturesCursor(String[] packageName) {
        Log.d(TAG, "getB2CFeatures()");
        return this.mDbHelper.getB2CFeatures(packageName);
    }

    public long deleteB2CFeatures(String[] packageName) {
        Log.d(TAG, "deleteB2CFeatures()");
        return this.mDbHelper.deleteB2CFeatures(packageName);
    }
}
