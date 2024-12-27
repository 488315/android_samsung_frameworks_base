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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KnoxAnalyticsQueryResolver {
    private static final String TAG =
            "[KnoxAnalytics] " + KnoxAnalyticsQueryResolver.class.getSimpleName();

    public static long addEvent(Context context, long id, String data, int type) {
        Log.d(TAG, "addEvent()");
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Long.valueOf(id));
        contentValues.put("data", data);
        Uri uri = getUriFromType(type);
        if (uri == null) {
            Log.d(TAG, "addEvent(): null ret uri");
            return -1L;
        }
        Uri ret = contentResolver.insert(uri, contentValues);
        if (ret == null) {
            Log.d(TAG, "addEvent(): null ret uri");
            return -1L;
        }
        long actualId = -1;
        try {
            actualId = ContentUris.parseId(ret);
        } catch (NumberFormatException ex) {
            Log.e(TAG, "addEvent(): error parsing return id - " + ex.getMessage());
        }
        Log.d(TAG, "addEvent(): actualId = " + actualId);
        return actualId;
    }

    public static long addBulkEvents(Context context, long id, Bundle data, int type) {
        Log.d(TAG, "addBulkEvents()");
        ContentResolver contentResolver = context.getContentResolver();
        data.putLong("id", id);
        Bundle result =
                contentResolver.call(
                        Contract.CONTENT_URI,
                        Contract.Events.Extra.INSERT_BULK_EVENTS,
                        (String) null,
                        data);
        long lastId = result.getLong("lastEventId");
        Log.d(TAG, "addBulkEvents(): lastId = " + lastId);
        return lastId;
    }

    public static EventList queryEventChunk(Context ctx) {
        ContentResolver contentResolver = ctx.getContentResolver();
        EventList events = new EventList();
        Cursor cursor =
                contentResolver.query(
                        Contract.Events.CONTENT_URI,
                        new String[] {Contract.Events.Projection.CHUNK_SIZE_ONLY_PLAIN_EVENTS},
                        null,
                        null,
                        null);
        if (cursor != null) {
            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        int vid =
                                cursor.getInt(
                                        cursor.getColumnIndex(Contract.Events.Field.VERSIONING_ID));
                        int bulk = cursor.getInt(cursor.getColumnIndex("bulk"));
                        String data = cursor.getString(cursor.getColumnIndex("data"));
                        Event event = null;
                        try {
                            event = new Event(id, vid, bulk, data);
                        } catch (JSONException e) {
                            Log.e(TAG, "Could not parse JSON. Invalid format", e);
                        }
                        if (event != null) {
                            events.put(event);
                        }
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return events;
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if (cursor != null) {
            cursor.close();
            return null;
        }
        return null;
    }

    public static Bundle performCompressedEventsTransaction(Context ctx, EventList events) {
        ZipResult result = ZipHandler.deflate(events.toByteArray());
        if (result == null) {
            Log.d(TAG, "performCompressedEventsTransaction(): null input data");
            return null;
        }
        ContentValues cv = new ContentValues();
        cv.put("content", result.getContent());
        cv.put(Contract.CompressedEvents.Field.LENGTH, Integer.valueOf(result.getLength()));
        cv.put(
                Contract.CompressedEvents.Field.ORIGINAL_LENGTH,
                Integer.valueOf(result.getOriginalLength()));
        cv.put("bulk", Integer.valueOf(events.getTotalEventsCount()));
        cv.put(Contract.CompressedEvents.Keys.PLAIN_EVENTS_SIZE, Integer.valueOf(events.length()));
        Bundle extras = new Bundle();
        extras.putParcelable(Contract.CompressedEvents.Keys.CV, cv);
        ContentResolver contentResolver = ctx.getContentResolver();
        return contentResolver.call(
                Contract.CompressedEvents.CONTENT_URI,
                Contract.CompressedEvents.METHOD_PERFORM_COMPRESSED_EVENTS_TRANSACTION,
                (String) null,
                extras);
    }

    private static Uri getUriFromType(int type) {
        switch (type) {
            case 0:
                Uri uri = Contract.DatabaseClean.CONTENT_URI;
                return uri;
            case 1:
                Uri uri2 = Contract.Events.CONTENT_URI;
                return uri2;
            default:
                Log.e(TAG, "getUriFromType(): not URI associated with this log type");
                return null;
        }
    }

    public static long getLastEventId(Context context) {
        Log.d(TAG, "getLastEventId()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(
                            Contract.Events.CONTENT_URI, new String[] {"lastEventId"}, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        long res = -1;
                        if (!cursor.isNull(0)) {
                            cursor.moveToFirst();
                            res = cursor.getLong(0);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        return res;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getLastEventId(): empty cursor");
            if (cursor != null) {
                cursor.close();
                return -1L;
            }
            return -1L;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getLastEventId(): ERROR READING CONTENT PROVIDER! "
                            + ex.getLocalizedMessage());
            throw ex;
        }
    }

    public static long getEventCount(Context context) {
        Log.d(TAG, "getEventCount()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(
                            Contract.Events.CONTENT_URI,
                            new String[] {Contract.Events.Projection.COUNT_ONLY},
                            null,
                            null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        long j = cursor.getLong(0);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return j;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getEventCount(): empty cursor");
            if (cursor != null) {
                cursor.close();
            }
            return -1L;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getEventCount(): ERROR READING CONTENT PROVIDER! " + ex.getLocalizedMessage());
            return -1L;
        }
    }

    public static List<BlacklistedFeature> getFeaturesBlacklist(Context context) {
        Log.d(TAG, "getFeaturesBlacklist()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(Contract.FeaturesBlacklist.CONTENT_URI, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        int columnFeature = cursor.getColumnIndex("feature");
                        int columnEvent = cursor.getColumnIndex("event");
                        ArrayList<BlacklistedFeature> featuresBlacklist =
                                new ArrayList<>(cursor.getCount());
                        cursor.moveToFirst();
                        do {
                            String feature = cursor.getString(columnFeature);
                            List<String> eventList =
                                    convertEventToList(cursor.getString(columnEvent));
                            featuresBlacklist.add(new BlacklistedFeature(feature, eventList));
                        } while (cursor.moveToNext());
                        if (cursor != null) {
                            cursor.close();
                        }
                        return featuresBlacklist;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getFeaturesBlacklist(): empty cursor");
            List<BlacklistedFeature> emptyList = Collections.emptyList();
            if (cursor != null) {
                cursor.close();
            }
            return emptyList;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getFeaturesBlacklist(): ERROR READING CONTENT PROVIDER! "
                            + ex.getLocalizedMessage());
            return null;
        }
    }

    private static List<String> convertEventToList(String event) {
        return Arrays.asList(event.split(NavigationBarInflaterView.GRAVITY_SEPARATOR));
    }

    public static DatabaseCleanResult callDatabaseClean(Context context, long targetDbSize) {
        Log.d(TAG, "callDatabaseClean()");
        ContentResolver contentResolver = context.getContentResolver();
        Bundle extras = new Bundle();
        extras.putLong(Contract.DatabaseClean.Extra.TARGET_DB_SIZE, targetDbSize);
        Bundle result =
                contentResolver.call(
                        Contract.CONTENT_URI, Contract.DatabaseClean.METHOD, (String) null, extras);
        return DatabaseCleanResult.fromBundle(result);
    }

    public static String[] getVersioningBlob(Context context) {
        Log.d(TAG, "getVersioningBlob()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(Contract.Versioning.CONTENT_URI, null, null, null);
            try {
                String[] res = {"-1", ""};
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToLast()) {
                        res[0] = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                        res[1] = cursor.getString(cursor.getColumnIndex("data"));
                        Log.d(TAG, "getVersioningBlob() - id = " + res[0] + ", data = " + res[1]);
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    return res;
                }
                Log.d(TAG, "getVersioningBlob(): empty cursor");
                if (cursor != null) {
                    cursor.close();
                }
                return res;
            } finally {
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "getFeaturesBlacklist(): ERROR READING CONTENT PROVIDER! ");
            return null;
        }
    }

    public static long addVersioningBlob(Context context, int id, String data, long eventId) {
        Log.d(TAG, "addVersioningBlob()");
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Integer.valueOf(id));
        contentValues.put("data", data);
        contentValues.put(Contract.Versioning.AUX_FIELD_EVENT_ID, Long.valueOf(eventId));
        Uri ret = contentResolver.insert(Contract.Versioning.CONTENT_URI, contentValues);
        if (ret == null) {
            Log.d(TAG, "addVersioningBlob(): null ret uri");
            return -1L;
        }
        try {
            long actualId = ContentUris.parseId(ret);
            return actualId;
        } catch (NumberFormatException e) {
            Log.e(TAG, "addVersioningBlob(): error parsing return id");
            return -1L;
        }
    }

    public static long getDatabaseSize(Context context) {
        Log.d(TAG, "getDatabaseSize()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(Contract.DatabaseSize.CONTENT_URI, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        long res = -1;
                        cursor.moveToFirst();
                        if (!cursor.isNull(0)) {
                            res = cursor.getLong(0);
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        return res;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getDatabaseSize(): empty cursor");
            if (cursor != null) {
                cursor.close();
                return -1L;
            }
            return -1L;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getDatabaseSize(): ERROR READING CONTENT PROVIDER! "
                            + ex.getLocalizedMessage());
            throw ex;
        }
    }

    public static void callNotifyVersioningCompleted(Context context) {
        Log.d(TAG, "callNotifyVersioningCompleted()");
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.call(
                Contract.CONTENT_URI,
                Contract.Versioning.METHOD_NOTIFY_VERSIONING_COMPLETED,
                (String) null,
                (Bundle) null);
    }

    public static List<WhitelistedFeature> getFeaturesWhitelist(Context context) {
        Log.d(TAG, "getFeaturesWhitelist()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(Contract.FeaturesWhitelist.CONTENT_URI, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        int columnFeature = cursor.getColumnIndex("feature");
                        int columnEnableType = cursor.getColumnIndex("enable_type");
                        ArrayList<WhitelistedFeature> featuresWhitelist =
                                new ArrayList<>(cursor.getCount());
                        cursor.moveToFirst();
                        do {
                            WhitelistedFeature feature =
                                    new WhitelistedFeature(
                                            cursor.getString(columnFeature),
                                            cursor.isNull(columnEnableType)
                                                    ? null
                                                    : Integer.valueOf(
                                                            cursor.getInt(columnEnableType)));
                            featuresWhitelist.add(feature);
                        } while (cursor.moveToNext());
                        if (cursor != null) {
                            cursor.close();
                        }
                        return featuresWhitelist;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getFeaturesWhitelist(): empty cursor");
            List<WhitelistedFeature> emptyList = Collections.emptyList();
            if (cursor != null) {
                cursor.close();
            }
            return emptyList;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getFeaturesWhitelist(): ERROR READING CONTENT PROVIDER! "
                            + ex.getLocalizedMessage());
            return Collections.emptyList();
        }
    }

    public static List<String> getB2CFeaturePackageList(Context context) {
        Log.d(TAG, "getB2CFeaturePackages()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(Contract.B2CFeatures.CONTENT_URI, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        int columnPackage = cursor.getColumnIndex("packageName");
                        ArrayList<String> packageList = new ArrayList<>(cursor.getCount());
                        cursor.moveToFirst();
                        do {
                            String packageName = cursor.getString(columnPackage);
                            packageList.add(packageName);
                        } while (cursor.moveToNext());
                        if (cursor != null) {
                            cursor.close();
                        }
                        return packageList;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getB2CFeaturePackages(): empty cursor");
            List<String> emptyList = Collections.emptyList();
            if (cursor != null) {
                cursor.close();
            }
            return emptyList;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getB2CFeaturePackages(): ERROR READING CONTENT PROVIDER! "
                            + ex.getLocalizedMessage());
            return Collections.emptyList();
        }
    }

    public static List<String> getB2CFeaturesList(Context context) {
        Log.d(TAG, "getB2CFeatureFeaturesList()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(Contract.B2CFeatures.CONTENT_URI, null, null, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        int columnFeature = cursor.getColumnIndex("feature_name");
                        ArrayList<String> featuresList = new ArrayList<>(cursor.getCount());
                        cursor.moveToFirst();
                        do {
                            String feature = cursor.getString(columnFeature);
                            featuresList.add(feature);
                        } while (cursor.moveToNext());
                        if (cursor != null) {
                            cursor.close();
                        }
                        return featuresList;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getB2CFeatureFeaturesList(): empty cursor");
            List<String> emptyList = Collections.emptyList();
            if (cursor != null) {
                cursor.close();
            }
            return emptyList;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getB2CFeatureFeaturesList(): ERROR READING CONTENT PROVIDER! "
                            + ex.getLocalizedMessage());
            return Collections.emptyList();
        }
    }

    public static String getB2CFeatureByPackage(Context context, String packageName) {
        Log.d(TAG, "getB2CFeatureFeaturesList()");
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursor =
                    contentResolver.query(
                            Contract.B2CFeatures.CONTENT_URI,
                            new String[] {"feature_name"},
                            "packageName",
                            new String[] {packageName},
                            null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() != 0) {
                        int columnFeature = cursor.getColumnIndex("feature_name");
                        cursor.moveToFirst();
                        String string = cursor.getString(columnFeature);
                        if (cursor != null) {
                            cursor.close();
                        }
                        return string;
                    }
                } finally {
                }
            }
            Log.d(TAG, "getB2CFeatureFeaturesList(): empty cursor");
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (IllegalStateException ex) {
            Log.e(
                    TAG,
                    "getB2CFeatureFeaturesList(): ERROR READING CONTENT PROVIDER! "
                            + ex.getLocalizedMessage());
            return null;
        }
    }
}
