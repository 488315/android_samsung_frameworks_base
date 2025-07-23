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
        long j2 = -1;
        if (uriFromType == null) {
            Log.d(str2, "addEvent(): null ret uri");
            return -1L;
        }
        Uri insert = contentResolver.insert(uriFromType, contentValues);
        if (insert == null) {
            Log.d(str2, "addEvent(): null ret uri");
            return -1L;
        }
        try {
            j2 = ContentUris.parseId(insert);
        } catch (NumberFormatException e) {
            Log.e(TAG, "addEvent(): error parsing return id - " + e.getMessage());
        }
        Log.d(TAG, "addEvent(): actualId = " + j2);
        return j2;
    }

    public static long addCleanEvent(Context context, long j, ContentValues contentValues) {
        String str = TAG;
        Log.d(str, "addCleanEvent()");
        ContentResolver contentResolver = context.getContentResolver();
        contentValues.put("id", Long.valueOf(j));
        Uri uriFromType = getUriFromType(0);
        long j2 = -1;
        if (uriFromType == null) {
            Log.d(str, "addCleanEvent(): wrong uri");
            return -1L;
        }
        Uri insert = contentResolver.insert(uriFromType, contentValues);
        if (insert == null) {
            Log.d(str, "addCleanEvent(): null ret uri");
            return -1L;
        }
        try {
            j2 = ContentUris.parseId(insert);
        } catch (NumberFormatException e) {
            Log.e(TAG, "addCleanEvent(): error parsing return id - " + e.getMessage());
        }
        Log.d(TAG, "addCleanEvent(): actualId = " + j2);
        return j2;
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
        Cursor query = contentResolver.query(Contract.Events.CONTENT_URI, new String[]{Contract.Events.Projection.CHUNK_SIZE_ONLY_PLAIN_EVENTS}, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() > 0) {
                    while (query.moveToNext()) {
                        try {
                            event = new Event(query.getInt(query.getColumnIndex("id")), query.getInt(query.getColumnIndex(Contract.Events.Field.VERSIONING_ID)), query.getInt(query.getColumnIndex("bulk")), query.getString(query.getColumnIndex("data")));
                        } catch (JSONException e) {
                            Log.e(TAG, "Could not parse JSON. Invalid format", e);
                            event = null;
                        }
                        if (event != null) {
                            eventList.put(event);
                        }
                    }
                    if (query != null) {
                        query.close();
                    }
                    return eventList;
                }
            } finally {
            }
        }
        if (query != null) {
            query.close();
        }
        return null;
    }

    public static Bundle performCompressedEventsTransaction(Context context, EventList eventList) {
        ZipResult deflate = ZipHandler.deflate(eventList.toByteArray());
        if (deflate == null) {
            Log.d(TAG, "performCompressedEventsTransaction(): null input data");
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", deflate.getContent());
        contentValues.put(Contract.CompressedEvents.Field.LENGTH, Integer.valueOf(deflate.getLength()));
        contentValues.put(Contract.CompressedEvents.Field.ORIGINAL_LENGTH, Integer.valueOf(deflate.getOriginalLength()));
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

    /* JADX WARN: Code restructure failed: missing block: B:7:0x003c, code lost:
    
        if (r4 != null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getLastEventId(android.content.Context r4) {
        /*
            java.lang.String r0 = com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.TAG
            java.lang.String r1 = "getLastEventId()"
            com.samsung.android.knox.analytics.util.Log.d(r0, r1)
            android.content.ContentResolver r4 = r4.getContentResolver()
            android.net.Uri r1 = com.samsung.android.knox.analytics.database.Contract.Events.CONTENT_URI     // Catch: java.lang.IllegalStateException -> L4d
            java.lang.String r2 = "lastEventId"
            java.lang.String[] r2 = new java.lang.String[]{r2}     // Catch: java.lang.IllegalStateException -> L4d
            r3 = 0
            android.database.Cursor r4 = r4.query(r1, r2, r3, r3)     // Catch: java.lang.IllegalStateException -> L4d
            r1 = -1
            if (r4 == 0) goto L37
            int r3 = r4.getCount()     // Catch: java.lang.Throwable -> L35
            if (r3 != 0) goto L23
            goto L37
        L23:
            r0 = 0
            boolean r3 = r4.isNull(r0)     // Catch: java.lang.Throwable -> L35
            if (r3 != 0) goto L31
            r4.moveToFirst()     // Catch: java.lang.Throwable -> L35
            long r1 = r4.getLong(r0)     // Catch: java.lang.Throwable -> L35
        L31:
            if (r4 == 0) goto L34
            goto L3e
        L34:
            return r1
        L35:
            r0 = move-exception
            goto L42
        L37:
            java.lang.String r3 = "getLastEventId(): empty cursor"
            com.samsung.android.knox.analytics.util.Log.d(r0, r3)     // Catch: java.lang.Throwable -> L35
            if (r4 == 0) goto L41
        L3e:
            r4.close()     // Catch: java.lang.IllegalStateException -> L4d
        L41:
            return r1
        L42:
            if (r4 == 0) goto L4c
            r4.close()     // Catch: java.lang.Throwable -> L48
            goto L4c
        L48:
            r4 = move-exception
            r0.addSuppressed(r4)     // Catch: java.lang.IllegalStateException -> L4d
        L4c:
            throw r0     // Catch: java.lang.IllegalStateException -> L4d
        L4d:
            r4 = move-exception
            java.lang.String r0 = com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "getLastEventId(): ERROR READING CONTENT PROVIDER! "
            r1.<init>(r2)
            java.lang.String r2 = r4.getLocalizedMessage()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.samsung.android.knox.analytics.util.Log.e(r0, r1)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.getLastEventId(android.content.Context):long");
    }

    public static long getEventCount(Context context) {
        String str = TAG;
        Log.d(str, "getEventCount()");
        try {
            Cursor query = context.getContentResolver().query(Contract.Events.CONTENT_URI, new String[]{Contract.Events.Projection.COUNT_ONLY}, null, null);
            if (query != null) {
                try {
                    if (query.getCount() > 0) {
                        query.moveToFirst();
                        long j = query.getLong(0);
                        if (query != null) {
                            query.close();
                        }
                        return j;
                    }
                } finally {
                }
            }
            Log.d(str, "getEventCount(): empty cursor");
            if (query != null) {
                query.close();
            }
            return -1L;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getEventCount(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return -1L;
        }
    }

    public static List<BlacklistedFeature> getFeaturesBlacklist(Context context) {
        String str = TAG;
        Log.d(str, "getFeaturesBlacklist()");
        try {
            Cursor query = context.getContentResolver().query(Contract.FeaturesBlacklist.CONTENT_URI, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        int columnIndex = query.getColumnIndex("feature");
                        int columnIndex2 = query.getColumnIndex("event");
                        ArrayList arrayList = new ArrayList(query.getCount());
                        query.moveToFirst();
                        do {
                            arrayList.add(new BlacklistedFeature(query.getString(columnIndex), convertEventToList(query.getString(columnIndex2))));
                        } while (query.moveToNext());
                        if (query != null) {
                            query.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getFeaturesBlacklist(): empty cursor");
            List<BlacklistedFeature> list = Collections.EMPTY_LIST;
            if (query != null) {
                query.close();
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

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0067, code lost:
    
        if (r7 != null) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0069, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x006c, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0075, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0072, code lost:
    
        if (r7 != null) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] getVersioningBlob(android.content.Context r7) {
        /*
            java.lang.String r0 = "getVersioningBlob() - id = "
            java.lang.String r1 = com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.TAG
            java.lang.String r2 = "getVersioningBlob()"
            com.samsung.android.knox.analytics.util.Log.d(r1, r2)
            android.content.ContentResolver r7 = r7.getContentResolver()
            r2 = 0
            android.net.Uri r3 = com.samsung.android.knox.analytics.database.Contract.Versioning.CONTENT_URI     // Catch: java.lang.IllegalStateException -> L82
            android.database.Cursor r7 = r7.query(r3, r2, r2, r2)     // Catch: java.lang.IllegalStateException -> L82
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch: java.lang.Throwable -> L76
            java.lang.String r4 = "-1"
            r5 = 0
            r3[r5] = r4     // Catch: java.lang.Throwable -> L76
            java.lang.String r4 = ""
            r6 = 1
            r3[r6] = r4     // Catch: java.lang.Throwable -> L76
            if (r7 == 0) goto L6d
            int r4 = r7.getCount()     // Catch: java.lang.Throwable -> L76
            if (r4 != 0) goto L2a
            goto L6d
        L2a:
            boolean r4 = r7.moveToLast()     // Catch: java.lang.Throwable -> L76
            if (r4 == 0) goto L67
            java.lang.String r4 = "id"
            int r4 = r7.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L76
            int r4 = r7.getInt(r4)     // Catch: java.lang.Throwable -> L76
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch: java.lang.Throwable -> L76
            r3[r5] = r4     // Catch: java.lang.Throwable -> L76
            java.lang.String r4 = "data"
            int r4 = r7.getColumnIndex(r4)     // Catch: java.lang.Throwable -> L76
            java.lang.String r4 = r7.getString(r4)     // Catch: java.lang.Throwable -> L76
            r3[r6] = r4     // Catch: java.lang.Throwable -> L76
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L76
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L76
            r0 = r3[r5]     // Catch: java.lang.Throwable -> L76
            r4.append(r0)     // Catch: java.lang.Throwable -> L76
            java.lang.String r0 = ", data = "
            r4.append(r0)     // Catch: java.lang.Throwable -> L76
            r0 = r3[r6]     // Catch: java.lang.Throwable -> L76
            r4.append(r0)     // Catch: java.lang.Throwable -> L76
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L76
            com.samsung.android.knox.analytics.util.Log.d(r1, r0)     // Catch: java.lang.Throwable -> L76
        L67:
            if (r7 == 0) goto L75
        L69:
            r7.close()     // Catch: java.lang.IllegalStateException -> L82
            return r3
        L6d:
            java.lang.String r0 = "getVersioningBlob(): empty cursor"
            com.samsung.android.knox.analytics.util.Log.d(r1, r0)     // Catch: java.lang.Throwable -> L76
            if (r7 == 0) goto L75
            goto L69
        L75:
            return r3
        L76:
            r0 = move-exception
            if (r7 == 0) goto L81
            r7.close()     // Catch: java.lang.Throwable -> L7d
            goto L81
        L7d:
            r7 = move-exception
            r0.addSuppressed(r7)     // Catch: java.lang.IllegalStateException -> L82
        L81:
            throw r0     // Catch: java.lang.IllegalStateException -> L82
        L82:
            java.lang.String r7 = com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.TAG
            java.lang.String r0 = "getFeaturesBlacklist(): ERROR READING CONTENT PROVIDER! "
            com.samsung.android.knox.analytics.util.Log.e(r7, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.getVersioningBlob(android.content.Context):java.lang.String[]");
    }

    public static long addVersioningBlob(Context context, int i, String str, long j) {
        String str2 = TAG;
        Log.d(str2, "addVersioningBlob()");
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Integer.valueOf(i));
        contentValues.put("data", str);
        contentValues.put(Contract.Versioning.AUX_FIELD_EVENT_ID, Long.valueOf(j));
        Uri insert = contentResolver.insert(Contract.Versioning.CONTENT_URI, contentValues);
        if (insert == null) {
            Log.d(str2, "addVersioningBlob(): null ret uri");
            return -1L;
        }
        try {
            return ContentUris.parseId(insert);
        } catch (NumberFormatException unused) {
            Log.e(TAG, "addVersioningBlob(): error parsing return id");
            return -1L;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0036, code lost:
    
        if (r4 != null) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static long getDatabaseSize(android.content.Context r4) {
        /*
            java.lang.String r0 = com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.TAG
            java.lang.String r1 = "getDatabaseSize()"
            com.samsung.android.knox.analytics.util.Log.d(r0, r1)
            android.content.ContentResolver r4 = r4.getContentResolver()
            android.net.Uri r1 = com.samsung.android.knox.analytics.database.Contract.DatabaseSize.CONTENT_URI     // Catch: java.lang.IllegalStateException -> L47
            r2 = 0
            android.database.Cursor r4 = r4.query(r1, r2, r2, r2)     // Catch: java.lang.IllegalStateException -> L47
            r1 = -1
            if (r4 == 0) goto L31
            int r3 = r4.getCount()     // Catch: java.lang.Throwable -> L2f
            if (r3 != 0) goto L1d
            goto L31
        L1d:
            r4.moveToFirst()     // Catch: java.lang.Throwable -> L2f
            r0 = 0
            boolean r3 = r4.isNull(r0)     // Catch: java.lang.Throwable -> L2f
            if (r3 != 0) goto L2b
            long r1 = r4.getLong(r0)     // Catch: java.lang.Throwable -> L2f
        L2b:
            if (r4 == 0) goto L2e
            goto L38
        L2e:
            return r1
        L2f:
            r0 = move-exception
            goto L3c
        L31:
            java.lang.String r3 = "getDatabaseSize(): empty cursor"
            com.samsung.android.knox.analytics.util.Log.d(r0, r3)     // Catch: java.lang.Throwable -> L2f
            if (r4 == 0) goto L3b
        L38:
            r4.close()     // Catch: java.lang.IllegalStateException -> L47
        L3b:
            return r1
        L3c:
            if (r4 == 0) goto L46
            r4.close()     // Catch: java.lang.Throwable -> L42
            goto L46
        L42:
            r4 = move-exception
            r0.addSuppressed(r4)     // Catch: java.lang.IllegalStateException -> L47
        L46:
            throw r0     // Catch: java.lang.IllegalStateException -> L47
        L47:
            r4 = move-exception
            java.lang.String r0 = com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "getDatabaseSize(): ERROR READING CONTENT PROVIDER! "
            r1.<init>(r2)
            java.lang.String r2 = r4.getLocalizedMessage()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.samsung.android.knox.analytics.util.Log.e(r0, r1)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.util.KnoxAnalyticsQueryResolver.getDatabaseSize(android.content.Context):long");
    }

    public static DatabaseCleanResult removeAllEvents(Context context) {
        Log.d(TAG, "removeAllEvents()");
        long databaseSize = getDatabaseSize(context);
        int delete = context.getContentResolver().delete(Contract.Reset.CONTENT_URI, null, null);
        long databaseSize2 = databaseSize - getDatabaseSize(context);
        Bundle bundle = new Bundle();
        bundle.putLong(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT, delete);
        bundle.putLong(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES, databaseSize2);
        return DatabaseCleanResult.fromBundle(bundle);
    }

    public static void callNotifyVersioningCompleted(Context context) {
        Log.d(TAG, "callNotifyVersioningCompleted()");
        context.getContentResolver().call(Contract.CONTENT_URI, Contract.Versioning.METHOD_NOTIFY_VERSIONING_COMPLETED, (String) null, (Bundle) null);
    }

    public static List<WhitelistedFeature> getFeaturesWhitelist(Context context) {
        String str = TAG;
        Log.d(str, "getFeaturesWhitelist()");
        try {
            Cursor query = context.getContentResolver().query(Contract.FeaturesWhitelist.CONTENT_URI, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        int columnIndex = query.getColumnIndex("feature");
                        int columnIndex2 = query.getColumnIndex("enable_type");
                        ArrayList arrayList = new ArrayList(query.getCount());
                        query.moveToFirst();
                        do {
                            arrayList.add(new WhitelistedFeature(query.getString(columnIndex), query.isNull(columnIndex2) ? null : Integer.valueOf(query.getInt(columnIndex2))));
                        } while (query.moveToNext());
                        if (query != null) {
                            query.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getFeaturesWhitelist(): empty cursor");
            List<WhitelistedFeature> list = Collections.EMPTY_LIST;
            if (query != null) {
                query.close();
            }
            return list;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getFeaturesWhitelist(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return Collections.EMPTY_LIST;
        }
    }

    public static List<String> getB2CFeaturePackageList(Context context) {
        String str = TAG;
        Log.d(str, "getB2CFeaturePackages()");
        try {
            Cursor query = context.getContentResolver().query(Contract.B2CFeatures.CONTENT_URI, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        int columnIndex = query.getColumnIndex("packageName");
                        ArrayList arrayList = new ArrayList(query.getCount());
                        query.moveToFirst();
                        do {
                            arrayList.add(query.getString(columnIndex));
                        } while (query.moveToNext());
                        if (query != null) {
                            query.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getB2CFeaturePackages(): empty cursor");
            List<String> list = Collections.EMPTY_LIST;
            if (query != null) {
                query.close();
            }
            return list;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getB2CFeaturePackages(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return Collections.EMPTY_LIST;
        }
    }

    public static List<String> getB2CFeaturesList(Context context) {
        String str = TAG;
        Log.d(str, "getB2CFeatureFeaturesList()");
        try {
            Cursor query = context.getContentResolver().query(Contract.B2CFeatures.CONTENT_URI, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        int columnIndex = query.getColumnIndex("feature_name");
                        ArrayList arrayList = new ArrayList(query.getCount());
                        query.moveToFirst();
                        do {
                            arrayList.add(query.getString(columnIndex));
                        } while (query.moveToNext());
                        if (query != null) {
                            query.close();
                        }
                        return arrayList;
                    }
                } finally {
                }
            }
            Log.d(str, "getB2CFeatureFeaturesList(): empty cursor");
            List<String> list = Collections.EMPTY_LIST;
            if (query != null) {
                query.close();
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
            Cursor query = context.getContentResolver().query(Contract.B2CFeatures.CONTENT_URI, new String[]{"feature_name"}, "packageName", new String[]{str}, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        int columnIndex = query.getColumnIndex("feature_name");
                        query.moveToFirst();
                        String string = query.getString(columnIndex);
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } finally {
                }
            }
            Log.d(str2, "getB2CFeatureFeaturesList(): empty cursor");
            if (query != null) {
                query.close();
            }
            return null;
        } catch (IllegalStateException e) {
            Log.e(TAG, "getB2CFeatureFeaturesList(): ERROR READING CONTENT PROVIDER! " + e.getLocalizedMessage());
            return null;
        }
    }
}
