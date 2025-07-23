package com.samsung.android.knox.analytics.database;

import android.app.job.JobInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.model.CleanEvent;
import com.samsung.android.knox.analytics.model.Event;
import com.samsung.android.knox.analytics.model.EventList;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.ZipHandler;
import com.samsung.android.knox.analytics.util.ZipResult;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;
import org.json.JSONException;

/* loaded from: classes6.dex */
class DatabaseCryptoAdapter {
    private static final String TAG = "[KnoxAnalytics] DatabaseCryptoAdapter";
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

    public Bundle performCompressedEventsTransaction(Bundle bundle) {
        String str = TAG;
        Log.d(str, "performCompressedEventsTransaction()");
        if (bundle == null || bundle.isEmpty()) {
            Log.d(str, "performCompressedEventsTransaction(): Null argument. Aborting");
            return null;
        }
        ContentValues contentValues = (ContentValues) bundle.getParcelable(Contract.CompressedEvents.Keys.CV);
        if (contentValues == null || contentValues.size() <= 0) {
            Log.d(str, "performCompressedEventsTransaction(): Null argument. Aborting");
            return null;
        }
        contentValues.put("content", this.mCryptoHandler.encryptBlob(contentValues.getAsByteArray("content")));
        boolean performCompressedEventsTransaction = this.mDbHelper.performCompressedEventsTransaction(contentValues);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean(Contract.CompressedEvents.METHOD_PERFORM_COMPRESSED_EVENTS_TRANSACTION, performCompressedEventsTransaction);
        return bundle2;
    }

    public long addEvent(ContentValues contentValues) {
        Log.d(TAG, "addEvent()");
        if (contentValues.containsKey("data")) {
            contentValues.put("data", this.mCryptoHandler.encrypt(contentValues.getAsString("data")));
            contentValues.put("bulk", (Integer) 1);
        }
        waitVersioningCompleted();
        contentValues.put(Contract.Events.Field.VERSIONING_ID, Integer.valueOf(this.mVersioningIdCache));
        return this.mDbHelper.addEvent(contentValues, 1);
    }

    public long addBulkEvents(Bundle bundle) {
        String str = TAG;
        Log.d(str, "addBulkEvents()");
        long j = bundle.getLong("id");
        ArrayList<String> stringArrayList = bundle.getStringArrayList(Contract.Events.Extra.EVENTS_LIST);
        if (stringArrayList == null || stringArrayList.isEmpty()) {
            Log.d(str, "addBulkEvents(): eventList is invalid");
            return -1L;
        }
        byte[] encryptBulk = this.mCryptoHandler.encryptBulk(stringArrayList);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(j));
        contentValues.put("data", encryptBulk);
        contentValues.put("bulk", Integer.valueOf(stringArrayList.size()));
        waitVersioningCompleted();
        contentValues.put(Contract.Events.Field.VERSIONING_ID, Integer.valueOf(this.mVersioningIdCache));
        return this.mDbHelper.addEvent(contentValues, 1);
    }

    public long addCleanedEvent(ContentValues contentValues) {
        Log.d(TAG, "addCleanedEvent()");
        waitVersioningCompleted();
        contentValues.put(Contract.Events.Field.VERSIONING_ID, Integer.valueOf(this.mVersioningIdCache));
        return this.mDbHelper.addEvent(contentValues, 0);
    }

    public void waitVersioningCompleted() {
        Log.d(TAG, "waitVersioningCompleted()");
        while (!this.mVersioningCompleted) {
            try {
                synchronized (this.mVersioningCompletedLock) {
                    this.mVersioningCompletedLock.wait(JobInfo.MIN_BACKOFF_MILLIS);
                }
                Log.d(TAG, "waitVersioningCompleted(): after wait");
            } catch (InterruptedException unused) {
                Log.e(TAG, "waitVersioningCompleted(): Interrupted exception");
            }
        }
    }

    public Cursor getEventChunk(Integer num, boolean z) {
        Log.d(TAG, "getEventChunk(" + num + ", " + z + " )");
        if (!z && getCompressedEventsCount() > 0) {
            return createMergedCursor(num);
        }
        return new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, num);
    }

    private Cursor createMergedCursor(Integer num) {
        String str = TAG;
        Log.d(str, "createMergedCursor(" + num + NavigationBarInflaterView.KEY_CODE_END);
        if (num == null) {
            return createCursorWithAllEvents();
        }
        int checkCompressedChunksLimit = checkCompressedChunksLimit(num.intValue());
        int totalCompressedEvents = getTotalCompressedEvents(checkCompressedChunksLimit);
        int totalPlainEvents = getTotalPlainEvents(num.intValue(), totalCompressedEvents);
        Log.d(str, "createCursorWith: " + totalCompressedEvents + " compressed events and " + totalPlainEvents + " plain events");
        if (totalPlainEvents == 0) {
            return createCursorOnlyWithCompressedEvents(checkCompressedChunksLimit);
        }
        return createCursorWithEventsSizeSpecified(totalPlainEvents, checkCompressedChunksLimit);
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0014, code lost:
    
        r2 = r3.getInt(0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int getTotalPlainEvents(int r4, int r5) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != r5) goto L4
            goto Lc
        L4:
            com.samsung.android.knox.analytics.database.DatabaseHelper r3 = r3.mDbHelper
            android.database.Cursor r3 = r3.getEventCountCursor()
            if (r3 != 0) goto Ld
        Lc:
            return r0
        Ld:
            r1 = r0
        Le:
            boolean r2 = r3.moveToNext()     // Catch: java.lang.Throwable -> L22
            if (r2 == 0) goto L1e
            int r2 = r3.getInt(r0)     // Catch: java.lang.Throwable -> L22
            int r5 = r5 + r2
            if (r5 <= r4) goto L1c
            goto L1e
        L1c:
            int r1 = r1 + r2
            goto Le
        L1e:
            r3.close()
            return r1
        L22:
            r4 = move-exception
            r3.close()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.database.DatabaseCryptoAdapter.getTotalPlainEvents(int, int):int");
    }

    private Cursor createCursorWithEventsSizeSpecified(int i, int i2) {
        Log.d(TAG, "createCursorWithEventsSizeSpecified(): query " + i + " events");
        Cursor compressedEvents = getCompressedEvents(Integer.valueOf(i2));
        if (compressedEvents != null && compressedEvents.getCount() > 0) {
            return new MergeCursor(new Cursor[]{compressedEvents, new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, Integer.valueOf(i))});
        }
        if (compressedEvents == null) {
            return null;
        }
        compressedEvents.close();
        return null;
    }

    private Cursor createCursorOnlyWithCompressedEvents(int i) {
        Log.d(TAG, "createCursorOnlyWithCompressedEvents(): query only compressed events");
        Cursor compressedEvents = getCompressedEvents(Integer.valueOf(i));
        if (compressedEvents != null && compressedEvents.getCount() > 0) {
            return new MergeCursor(new Cursor[]{compressedEvents});
        }
        if (compressedEvents == null) {
            return null;
        }
        compressedEvents.close();
        return null;
    }

    private Cursor createCursorWithAllEvents() {
        Log.d(TAG, "createCursorWithAllEvents()");
        Cursor compressedEvents = getCompressedEvents(null);
        if (compressedEvents != null && compressedEvents.getCount() > 0) {
            return new MergeCursor(new Cursor[]{compressedEvents, new EncryptedCursor(this.mDbHelper, this.mCryptoHandler, null)});
        }
        if (compressedEvents != null) {
            compressedEvents.close();
        }
        return null;
    }

    public long deleteEventChunk(long j, int i) {
        return deleteEventChunk(j, 0L, i);
    }

    public long deleteEventChunk(long j, long j2, int i) {
        Log.d(TAG, "deleteEventChunk(size:" + j + ", type:" + i + ") ");
        if (i == 0) {
            return deleteCleanedEventChunk(j);
        }
        if (i != 1) {
            return 0L;
        }
        return deleteMergedChunk(j, j2);
    }

    private long deleteMergedChunk(long j, long j2) {
        String str = TAG;
        Log.d(str, "deleteMergedChunk(" + j + ", " + j2 + NavigationBarInflaterView.KEY_CODE_END);
        if (this.mDbHelper.getCompressedEventCountValue() <= 0) {
            return this.mDbHelper.deleteEventChunk(j, 1);
        }
        int checkCompressedChunksLimit = checkCompressedChunksLimit(j2);
        int totalCompressedEvents = getTotalCompressedEvents(checkCompressedChunksLimit);
        if (this.mDbHelper.deleteCompressedEventChunk(checkCompressedChunksLimit) == 0) {
            Log.e(str, "deleteMergedChunk(): Some error occurred when deleting.");
            return 0L;
        }
        long calculateRemainingEventsForDelete = calculateRemainingEventsForDelete((int) j2, totalCompressedEvents);
        return calculateRemainingEventsForDelete == 0 ? j : (j - calculateRemainingEventsForDelete) + this.mDbHelper.deleteEventChunk(calculateRemainingEventsForDelete, 1);
    }

    public long deleteFromAllEventTables() {
        Log.d(TAG, "deleteFromAllEventTables()");
        return this.mDbHelper.deleteFromAllEventTables();
    }

    public int getTotalCompressedEvents(int i) {
        Log.d(TAG, "getTotalCompressedEvents(" + i + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDbHelper.getTotalCompressedEvent(Integer.valueOf(i));
    }

    private int checkCompressedChunksLimit(long j) {
        Log.d(TAG, "checkCompressedChunksLimit(" + j + NavigationBarInflaterView.KEY_CODE_END);
        int i = 0;
        if (j > 0) {
            Cursor totalCompressedEventCursor = this.mDbHelper.getTotalCompressedEventCursor();
            if (totalCompressedEventCursor != null) {
                try {
                    if (totalCompressedEventCursor.getCount() > 0) {
                        int i2 = 0;
                        int i3 = 0;
                        while (totalCompressedEventCursor.moveToNext()) {
                            i2 += totalCompressedEventCursor.getInt(0);
                            if (i2 > j) {
                                break;
                            }
                            i3++;
                        }
                        i = i3;
                    }
                } finally {
                    if (totalCompressedEventCursor != null) {
                        totalCompressedEventCursor.close();
                    }
                }
            }
        }
        return i;
    }

    private int calculateRemainingEventsForDelete(int i, int i2) {
        int i3 = i - i2;
        Log.d(TAG, "calculateRemainingEventsForDelete(" + i + ", " + i2 + "): totalToDelete: " + i3);
        Cursor eventCountCursor = this.mDbHelper.getEventCountCursor();
        if (eventCountCursor != null) {
            try {
                if (eventCountCursor.getCount() > 0) {
                    int i4 = 0;
                    int i5 = 0;
                    while (eventCountCursor.moveToNext() && (i4 = i4 + eventCountCursor.getInt(0)) <= i3) {
                        i5++;
                    }
                    return i5;
                }
            } finally {
                if (eventCountCursor != null) {
                    eventCountCursor.close();
                }
            }
        }
        if (eventCountCursor != null) {
            eventCountCursor.close();
        }
        return 0;
    }

    public long deleteCompressedEventChunk(long j) {
        Log.d(TAG, "deleteCompressedEventChunk(size: " + j + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDbHelper.deleteCompressedEventChunk(j);
    }

    public long deleteCleanedEventChunk(long j) {
        return this.mDbHelper.deleteEventChunk(j, 0);
    }

    public long deleteUpTo(long j) {
        Log.d(TAG, "deleteUpTo(" + j + NavigationBarInflaterView.KEY_CODE_END);
        return this.mDbHelper.deleteUpTo(j);
    }

    public long deleteUntilTargetDbSize(long j) {
        Log.d(TAG, "deleteUntilTargetDbSize(" + j + NavigationBarInflaterView.KEY_CODE_END);
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

    public int addVersioningBlob(ContentValues contentValues) {
        Log.d(TAG, "addVersioning()");
        int addVersioningBlob = this.mDbHelper.addVersioningBlob(contentValues);
        if (addVersioningBlob == -1) {
            return -1;
        }
        this.mVersioningIdCache = addVersioningBlob;
        return addVersioningBlob;
    }

    public Cursor getVersioningBlob() {
        Log.d(TAG, "getVersioningBlob()");
        Cursor versioningBlob = this.mDbHelper.getVersioningBlob();
        updateVersioningCache(versioningBlob);
        return versioningBlob;
    }

    private void updateVersioningCache(Cursor cursor) {
        int columnIndex;
        Log.d(TAG, "updateVersioningCache()");
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToLast() || (columnIndex = cursor.getColumnIndex("id")) == -1) {
            return;
        }
        this.mVersioningIdCache = cursor.getInt(columnIndex);
    }

    public long deleteFromVersion(long j) {
        Log.d(TAG, "deleteFromVersion()");
        return this.mDbHelper.deleteFromVersion(j);
    }

    public long addFeatureBlacklist(ContentValues contentValues) {
        Log.d(TAG, "addFeatureBlacklist()");
        long deleteFeaturesBlacklist = this.mDbHelper.deleteFeaturesBlacklist();
        for (String str : contentValues.keySet()) {
            String asString = contentValues.getAsString(str);
            if (asString == null) {
                Log.d(TAG, "addFeatureBlacklist(): null value");
            } else {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("feature", str);
                contentValues2.put("event", asString);
                deleteFeaturesBlacklist += this.mDbHelper.addFeaturesBlacklist(contentValues2);
            }
        }
        return deleteFeaturesBlacklist;
    }

    public long getCompressedEventsCount() {
        return this.mDbHelper.getCompressedEventCountValue();
    }

    public Cursor getCompressedEvents(Integer num) {
        String str = TAG;
        Log.d(str, "getCompressedEvents()");
        if (this.mDbHelper.getCompressedEventCountValue() <= 0) {
            Log.d(str, "There is no compressed data to be queried");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            Cursor compressedEventChunk = this.mDbHelper.getCompressedEventChunk(num);
            if (compressedEventChunk != null) {
                try {
                    if (compressedEventChunk.getCount() > 0) {
                        while (compressedEventChunk.moveToNext()) {
                            byte[] decryptBlob = this.mCryptoHandler.decryptBlob(compressedEventChunk.getBlob(compressedEventChunk.getColumnIndex("content")));
                            int i = compressedEventChunk.getInt(compressedEventChunk.getColumnIndex(Contract.CompressedEvents.Field.LENGTH));
                            int i2 = compressedEventChunk.getInt(compressedEventChunk.getColumnIndex(Contract.CompressedEvents.Field.ORIGINAL_LENGTH));
                            if (i != -1 || i2 != -1 || decryptBlob.length > 0) {
                                arrayList.add(new ZipResult(decryptBlob, i, i2));
                            }
                        }
                        Cursor createCursor = createCursor(arrayList);
                        if (compressedEventChunk != null) {
                            compressedEventChunk.close();
                        }
                        return createCursor;
                    }
                } finally {
                }
            }
            if (compressedEventChunk != null) {
                compressedEventChunk.close();
            }
            return null;
        } catch (GeneralSecurityException e) {
            Log.e(TAG, "getCompressedEvents(): ", e);
            return null;
        }
    }

    private Cursor createCursor(List<ZipResult> list) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", Contract.Events.Field.VERSIONING_ID, "bulk", "data"}, 1);
        Iterator<ZipResult> it = list.iterator();
        while (it.hasNext()) {
            EventList eventsList = getEventsList(it.next());
            if (eventsList == null || eventsList.length() <= 0) {
                Log.d(TAG, "createCursor(): Null events received");
            } else {
                for (int i = 0; i < eventsList.length(); i++) {
                    try {
                        Event event = new Event(eventsList.getString(i));
                        matrixCursor.addRow(new Object[]{Integer.valueOf(event.getInt("id")), Integer.valueOf(event.getInt(Contract.Events.Field.VERSIONING_ID)), Integer.valueOf(event.getInt("bulk")), event.getString("data")});
                    } catch (JSONException e) {
                        Log.e(TAG, "createCursor(): Parsing error object.", e);
                    }
                }
            }
        }
        return matrixCursor;
    }

    private EventList getEventsList(ZipResult zipResult) {
        try {
            return ZipHandler.inflate(zipResult);
        } catch (DataFormatException unused) {
            Log.d(TAG, "getEventsList(): Data is malformed");
            return null;
        } catch (JSONException unused2) {
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

    public Cursor getCleanedEventsCursor() {
        Cursor cleanedEventsCursor = this.mDbHelper.getCleanedEventsCursor();
        if (cleanedEventsCursor != null) {
            try {
                if (cleanedEventsCursor.getCount() > 0) {
                    Cursor recreateCleanedEventCursor = recreateCleanedEventCursor(cleanedEventsCursor);
                    if (recreateCleanedEventCursor != null) {
                        recreateCleanedEventCursor.close();
                    }
                    if (cleanedEventsCursor != null) {
                        cleanedEventsCursor.close();
                    }
                    return recreateCleanedEventCursor;
                }
            } catch (Throwable th) {
                if (cleanedEventsCursor != null) {
                    try {
                        cleanedEventsCursor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (cleanedEventsCursor == null) {
            return null;
        }
        cleanedEventsCursor.close();
        return null;
    }

    public void notifyVersioningCompleted() {
        synchronized (this.mVersioningCompletedLock) {
            this.mVersioningCompleted = true;
            this.mVersioningCompletedLock.notifyAll();
        }
    }

    public long addFeatureWhitelist(ContentValues contentValues) {
        long addFeaturesWhitelist;
        Log.d(TAG, "addFeatureWhitelist()");
        long j = 0;
        for (String str : contentValues.keySet()) {
            Integer asInteger = contentValues.getAsInteger(str);
            if (asInteger == null) {
                Log.d(TAG, "addFeatureWhitelist(): null value");
            } else {
                if (asInteger.intValue() == 1) {
                    addFeaturesWhitelist = this.mDbHelper.deleteFeatureWhitelist(str);
                } else {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("feature", str);
                    contentValues2.put("enable_type", asInteger);
                    addFeaturesWhitelist = this.mDbHelper.addFeaturesWhitelist(contentValues2);
                }
                j += addFeaturesWhitelist;
            }
        }
        return j;
    }

    public Cursor getFeatureWhitelistCursor() {
        Log.d(TAG, "getFeatureWhitelistCursor()");
        return this.mDbHelper.getFeaturesWhitelist();
    }

    public long deleteFeatureWhitelist(String[] strArr) {
        Log.d(TAG, "deleteFeatureWhitelist()");
        return this.mDbHelper.deleteFeaturesWhitelist(strArr);
    }

    public long addB2CFeatures(ContentValues contentValues) {
        Log.d(TAG, "addB2CFeatures()");
        return this.mDbHelper.addB2CFeatures(contentValues);
    }

    public Cursor getB2CFeaturesCursor(String[] strArr) {
        Log.d(TAG, "getB2CFeatures()");
        return this.mDbHelper.getB2CFeatures(strArr);
    }

    public long deleteB2CFeatures(String[] strArr) {
        Log.d(TAG, "deleteB2CFeatures()");
        return this.mDbHelper.deleteB2CFeatures(strArr);
    }

    private Cursor recreateCleanedEventCursor(Cursor cursor) {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"id", Contract.Events.Field.VERSIONING_ID, "data"}, 1);
        try {
            matrixCursor.addRow(new Object[]{Integer.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("id"))), Integer.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Events.Field.VERSIONING_ID))), new CleanEvent(cursor.getInt(cursor.getColumnIndexOrThrow("id")), cursor.getInt(cursor.getColumnIndexOrThrow(Contract.Events.Field.VERSIONING_ID)), cursor.getInt(cursor.getColumnIndexOrThrow(Contract.DatabaseClean.Field.COUNTER)), cursor.getLong(cursor.getColumnIndexOrThrow(Contract.DatabaseClean.Field.FIRST_TIMESTAMP)), cursor.getLong(cursor.getColumnIndexOrThrow(Contract.DatabaseClean.Field.LAST_TIMESTAMP)), cursor.getInt(cursor.getColumnIndexOrThrow(Contract.DatabaseClean.Field.REMOVED_EVENTS)), cursor.getInt(cursor.getColumnIndexOrThrow(Contract.DatabaseClean.Field.REMOVED_SIZE)), cursor.getInt(cursor.getColumnIndexOrThrow("reason"))).toJsonString()});
            return matrixCursor;
        } catch (IllegalArgumentException | JSONException e) {
            Log.e(TAG, "recreateCleanedEventCursor(): Failed: ", e);
            return matrixCursor;
        }
    }
}
