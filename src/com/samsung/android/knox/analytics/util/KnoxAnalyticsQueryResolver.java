package com.samsung.android.knox.analytics.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.model.Event;
import com.samsung.android.knox.analytics.model.EventList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class KnoxAnalyticsQueryResolver {
    private static final String TAG = "[KnoxAnalytics] KnoxAnalyticsQueryResolver";

    public static long addEvent(Context context, long j, String str, int i) {
        String str2 = TAG;
        Log.d(str2, "addEvent()");
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(j));
        contentValues.put("data", str);
        Uri uriFromType = getUriFromType(i);
        long id = -1;
        if (uriFromType == null) {
            Log.d(str2, "addEvent(): null ret uri");
            return -1L;
        }
        Uri uriInsert = contentResolver.insert(uriFromType, contentValues);
        if (uriInsert == null) {
            Log.d(str2, "addEvent(): null ret uri");
            return -1L;
        }
        try {
            id = ContentUris.parseId(uriInsert);
        } catch (NumberFormatException e) {
            Log.e(TAG, "addEvent(): error parsing return id - " + e.getMessage());
        }
        Log.d(TAG, "addEvent(): actualId = " + id);
        return id;
    }

    public static long addCleanEvent(Context context, long j, ContentValues contentValues) {
        String str = TAG;
        Log.d(str, "addCleanEvent()");
        ContentResolver contentResolver = context.getContentResolver();
        contentValues.put("id", Long.valueOf(j));
        Uri uriFromType = getUriFromType(0);
        long id = -1;
        if (uriFromType == null) {
            Log.d(str, "addCleanEvent(): wrong uri");
            return -1L;
        }
        Uri uriInsert = contentResolver.insert(uriFromType, contentValues);
        if (uriInsert == null) {
            Log.d(str, "addCleanEvent(): null ret uri");
            return -1L;
        }
        try {
            id = ContentUris.parseId(uriInsert);
        } catch (NumberFormatException e) {
            Log.e(TAG, "addCleanEvent(): error parsing return id - " + e.getMessage());
        }
        Log.d(TAG, "addCleanEvent(): actualId = " + id);
        return id;
    }

    public static long addBulkEvents(Context context, long j, Bundle bundle, int i) {
        String str = TAG;
        Log.d(str, "addBulkEvents()");
        ContentResolver contentResolver = context.getContentResolver();
        bundle.putLong("id", j);
        long j2 = contentResolver.call(Contract.CONTENT_URI, Contract.Events.Extra.INSERT_BULK_EVENTS, (String) null, bundle).getLong("lastEventId");
        Log.d(str, "addBulkEvents(): lastId = " + j2);
        return j2;
    }

    public static EventList queryEventChunk(Context context) {
        Event event;
        ContentResolver contentResolver = context.getContentResolver();
        EventList eventList = new EventList();
        Cursor cursorQuery = contentResolver.query(Contract.Events.CONTENT_URI, new String[]{Contract.Events.Projection.CHUNK_SIZE_ONLY_PLAIN_EVENTS}, null, null, null);
        if (cursorQuery != null) {
            try {
                if (cursorQuery.getCount() > 0) {
                    while (cursorQuery.moveToNext()) {
                        try {
                            event = new Event(cursorQuery.getInt(cursorQuery.getColumnIndex("id")), cursorQuery.getInt(cursorQuery.getColumnIndex(Contract.Events.Field.VERSIONING_ID)), cursorQuery.getInt(cursorQuery.getColumnIndex("bulk")), cursorQuery.getString(cursorQuery.getColumnIndex("data")));
                        } catch (JSONException e) {
                            Log.e(TAG, "Could not parse JSON. Invalid format", e);
                            event = null;
                        }
                        if (event != null) {
                            eventList.put(event);
                        }
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return eventList;
                }
            } finally {
            }
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return null;
    }

    public static Bundle performCompressedEventsTransaction(Context context, EventList eventList) {
        ZipResult zipResultDeflate = ZipHandler.deflate(eventList.toByteArray());
        if (zipResultDeflate == null) {
            Log.d(TAG, "performCompressedEventsTransaction(): null input data");
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", zipResultDeflate.getContent());
        contentValues.put(Contract.CompressedEvents.Field.LENGTH, Integer.valueOf(zipResultDeflate.getLength()));
        contentValues.put(Contract.CompressedEvents.Field.ORIGINAL_LENGTH, Integer.valueOf(zipResultDeflate.getOriginalLength()));
        contentValues.put("bulk", Integer.valueOf(eventList.getTotalEventsCount()));
        contentValues.put(Contract.CompressedEvents.Keys.PLAIN_EVENTS_SIZE, Integer.valueOf(eventList.length()));
        Bundle bundle = new Bundle();
        bundle.putParcelable(Contract.CompressedEvents.Keys.CV, contentValues);
        return context.getContentResolver().call(Contract.CompressedEvents.CONTENT_URI, Contract.CompressedEvents.METHOD_PERFORM_COMPRESSED_EVENTS_TRANSACTION, (String) null, bundle);
    }

    private static Uri getUriFromType(int i) {
        if (i == 0) {
            return Contract.DatabaseClean.CONTENT_URI;
        }
        if (i == 1) {
            return Contract.Events.CONTENT_URI;
        }
        Log.e(TAG, "getUriFromType(): not URI associated with this log type");
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x003e A[Catch: IllegalStateException -> 0x004d, PHI: r1
      0x003e: PHI (r1v8 long) = (r1v5 long), (r1v4 long) binds: [B:13:0x0033, B:18:0x003c] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #2 {IllegalStateException -> 0x004d, blocks: (B:3:0x000b, B:19:0x003e, B:26:0x004c, B:25:0x0049, B:6:0x001c, B:9:0x0023, B:11:0x002a, B:17:0x0037, B:22:0x0044), top: B:33:0x000b, inners: #0, #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static long getLastEventId(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getLastEventId()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.Events.CONTENT_URI, new String[]{"lastEventId"}, null, null);
            long j = -1;
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() == 0) {
                        Log.d(str, "getLastEventId(): empty cursor");
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    } else {
                        if (!cursorQuery.isNull(0)) {
                            cursorQuery.moveToFirst();
                            j = cursorQuery.getLong(0);
                        }
                        if (cursorQuery == null) {
                            return j;
                        }
                        cursorQuery.close();
                    }
                } finally {
                }
            } else {
                Log.d(str, "getLastEventId(): empty cursor");
                if (cursorQuery != null) {
                }
            }
            return j;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getLastEventId(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            throw e;
        }
    }

    public static long getEventCount(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getEventCount()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.Events.CONTENT_URI, new String[]{Contract.Events.Projection.COUNT_ONLY}, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() > 0) {
                        cursorQuery.moveToFirst();
                        long j = cursorQuery.getLong(0);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return j;
                    }
                } finally {
                }
            }
            Log.d(str, "getEventCount(): empty cursor");
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return -1L;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getEventCount(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return -1L;
        }
    }

    public static List<BlacklistedFeature> getFeaturesBlacklist(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getFeaturesBlacklist()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.FeaturesBlacklist.CONTENT_URI, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() != 0) {
                        int columnIndex = cursorQuery.getColumnIndex("feature");
                        int columnIndex2 = cursorQuery.getColumnIndex("event");
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        cursorQuery.moveToFirst();
                        do {
                            arrayList.add(new BlacklistedFeature(cursorQuery.getString(columnIndex), convertEventToList(cursorQuery.getString(columnIndex2))));
                        } while (cursorQuery.moveToNext());
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getFeaturesBlacklist(): empty cursor");
            List<BlacklistedFeature> list = Collections.EMPTY_LIST;
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return list;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getFeaturesBlacklist(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return null;
        }
    }

    private static List<String> convertEventToList(String str) {
        return Arrays.asList(str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR));
    }

    public static DatabaseCleanResult callDatabaseClean(Context context, long j) {
        Log.d(TAG, "callDatabaseClean()");
        ContentResolver contentResolver = context.getContentResolver();
        Bundle bundle = new Bundle();
        bundle.putLong(Contract.DatabaseClean.Extra.TARGET_DB_SIZE, j);
        return DatabaseCleanResult.fromBundle(contentResolver.call(Contract.CONTENT_URI, Contract.DatabaseClean.METHOD, (String) null, bundle));
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0067, code lost:
    
        if (r7 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0069, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x006c, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0072, code lost:
    
        if (r7 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0075, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String[] getVersioningBlob(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getVersioningBlob()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.Versioning.CONTENT_URI, null, null, null);
            try {
                String[] strArr = {"-1", ""};
                if (cursorQuery != null && cursorQuery.getCount() != 0) {
                    if (cursorQuery.moveToLast()) {
                        strArr[0] = String.valueOf(cursorQuery.getInt(cursorQuery.getColumnIndex("id")));
                        strArr[1] = cursorQuery.getString(cursorQuery.getColumnIndex("data"));
                        Log.d(str, "getVersioningBlob() - id = " + strArr[0] + ", data = " + strArr[1]);
                    }
                }
                Log.d(str, "getVersioningBlob(): empty cursor");
            } finally {
            }
        } catch (IllegalStateException unused) {
            Log.e(TAG, "getFeaturesBlacklist(): ERROR READING CONTENT PROVIDER! ");
            return null;
        }
    }

    public static long addVersioningBlob(Context context, int i, String str, long j) {
        String str2 = TAG;
        Log.d(str2, "addVersioningBlob()");
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Integer.valueOf(i));
        contentValues.put("data", str);
        contentValues.put(Contract.Versioning.AUX_FIELD_EVENT_ID, Long.valueOf(j));
        Uri uriInsert = contentResolver.insert(Contract.Versioning.CONTENT_URI, contentValues);
        if (uriInsert == null) {
            Log.d(str2, "addVersioningBlob(): null ret uri");
            return -1L;
        }
        try {
            return ContentUris.parseId(uriInsert);
        } catch (NumberFormatException unused) {
            Log.e(TAG, "addVersioningBlob(): error parsing return id");
            return -1L;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0038 A[Catch: IllegalStateException -> 0x0047, PHI: r1
      0x0038: PHI (r1v8 long) = (r1v5 long), (r1v4 long) binds: [B:13:0x002d, B:18:0x0036] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #1 {IllegalStateException -> 0x0047, blocks: (B:3:0x000b, B:19:0x0038, B:26:0x0046, B:25:0x0043, B:22:0x003e, B:6:0x0016, B:9:0x001d, B:11:0x0027, B:17:0x0031), top: B:32:0x000b, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static long getDatabaseSize(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getDatabaseSize()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.DatabaseSize.CONTENT_URI, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() == 0) {
                        Log.d(str, "getDatabaseSize(): empty cursor");
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                    } else {
                        cursorQuery.moveToFirst();
                        j = cursorQuery.isNull(0) ? -1L : cursorQuery.getLong(0);
                        if (cursorQuery == null) {
                            return j;
                        }
                        cursorQuery.close();
                    }
                } finally {
                }
            } else {
                Log.d(str, "getDatabaseSize(): empty cursor");
                if (cursorQuery != null) {
                }
            }
            return j;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getDatabaseSize(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            throw e;
        }
    }

    public static DatabaseCleanResult removeAllEvents(Context context) throws Throwable {
        Log.d(TAG, "removeAllEvents()");
        long databaseSize = getDatabaseSize(context);
        int iDelete = context.getContentResolver().delete(Contract.Reset.CONTENT_URI, null, null);
        long databaseSize2 = databaseSize - getDatabaseSize(context);
        Bundle bundle = new Bundle();
        bundle.putLong(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT, iDelete);
        bundle.putLong(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES, databaseSize2);
        return DatabaseCleanResult.fromBundle(bundle);
    }

    public static void callNotifyVersioningCompleted(Context context) {
        Log.d(TAG, "callNotifyVersioningCompleted()");
        context.getContentResolver().call(Contract.CONTENT_URI, Contract.Versioning.METHOD_NOTIFY_VERSIONING_COMPLETED, (String) null, (Bundle) null);
    }

    public static List<WhitelistedFeature> getFeaturesWhitelist(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getFeaturesWhitelist()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.FeaturesWhitelist.CONTENT_URI, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() != 0) {
                        int columnIndex = cursorQuery.getColumnIndex("feature");
                        int columnIndex2 = cursorQuery.getColumnIndex("enable_type");
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        cursorQuery.moveToFirst();
                        do {
                            arrayList.add(new WhitelistedFeature(cursorQuery.getString(columnIndex), cursorQuery.isNull(columnIndex2) ? null : Integer.valueOf(cursorQuery.getInt(columnIndex2))));
                        } while (cursorQuery.moveToNext());
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getFeaturesWhitelist(): empty cursor");
            List<WhitelistedFeature> list = Collections.EMPTY_LIST;
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return list;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getFeaturesWhitelist(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return Collections.EMPTY_LIST;
        }
    }

    public static List<String> getB2CFeaturePackageList(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getB2CFeaturePackages()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.B2CFeatures.CONTENT_URI, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() != 0) {
                        int columnIndex = cursorQuery.getColumnIndex("packageName");
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        cursorQuery.moveToFirst();
                        do {
                            arrayList.add(cursorQuery.getString(columnIndex));
                        } while (cursorQuery.moveToNext());
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getB2CFeaturePackages(): empty cursor");
            List<String> list = Collections.EMPTY_LIST;
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return list;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getB2CFeaturePackages(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return Collections.EMPTY_LIST;
        }
    }

    public static List<String> getB2CFeaturesList(Context context) throws Throwable {
        String str = TAG;
        Log.d(str, "getB2CFeatureFeaturesList()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.B2CFeatures.CONTENT_URI, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() != 0) {
                        int columnIndex = cursorQuery.getColumnIndex("feature_name");
                        ArrayList arrayList = new ArrayList(cursorQuery.getCount());
                        cursorQuery.moveToFirst();
                        do {
                            arrayList.add(cursorQuery.getString(columnIndex));
                        } while (cursorQuery.moveToNext());
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getB2CFeatureFeaturesList(): empty cursor");
            List<String> list = Collections.EMPTY_LIST;
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return list;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getB2CFeatureFeaturesList(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return Collections.EMPTY_LIST;
        }
    }

    public static String getB2CFeatureByPackage(Context context, String str) {
        String str2 = TAG;
        Log.d(str2, "getB2CFeatureFeaturesList()");
        try {
            Cursor cursorQuery = context.getContentResolver().query(Contract.B2CFeatures.CONTENT_URI, new String[]{"feature_name"}, "packageName", new String[]{str}, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() != 0) {
                        int columnIndex = cursorQuery.getColumnIndex("feature_name");
                        cursorQuery.moveToFirst();
                        String string = cursorQuery.getString(columnIndex);
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
                    }
                } finally {
                }
            }
            Log.d(str2, "getB2CFeatureFeaturesList(): empty cursor");
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getB2CFeatureFeaturesList(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return null;
        }
    }
}
