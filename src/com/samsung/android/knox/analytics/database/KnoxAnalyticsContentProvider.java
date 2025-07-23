package com.samsung.android.knox.analytics.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.SecurityUtils;

/* loaded from: classes6.dex */
public class KnoxAnalyticsContentProvider extends ContentProvider {
    private static final int B2C_FEATURE_PATH_ID = 7;
    private static final int CLEANED_EVENTS_PATH_ID = 5;
    private static final int DATABASE_SIZE_PATH_ID = 2;
    private static final int DEBUG_PATH_ID = 9;
    private static final int EVENTS_PATH_ID = 1;
    private static final int FEATURES_BLACKLIST_PATH_ID = 3;
    private static final int FEATURES_WHITELIST_PATH_ID = 6;
    private static final int RESET_EVENT_TABLES_PATH_ID = 8;
    private static final String TAG = "[KnoxAnalytics] KnoxAnalyticsContentProvider";
    private static final int VERSIONING_PATH_ID = 4;
    private static final UriMatcher sUriMatcher;
    private volatile DatabaseCryptoAdapter mDatabaseCryptoAdapter;
    private Object mDeactivationLock = new Object();

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI(Contract.AUTHORITY, Contract.Events.PATH, 1);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.DatabaseSize.PATH, 2);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.FeaturesBlacklist.PATH, 3);
        uriMatcher.addURI(Contract.AUTHORITY, "version", 4);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.DatabaseClean.PATH, 5);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.FeaturesWhitelist.PATH, 6);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.B2CFeatures.PATH, 7);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.Reset.PATH, 8);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.Debug.PATH, 9);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Log.d(TAG, "onCreate()");
        this.mDatabaseCryptoAdapter = null;
        return true;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri insertIntoEvents;
        long j;
        String str = TAG;
        Log.d(str, "insert()");
        SecurityUtils.enforceProviderCaller(getContext(), getCallingPackage());
        int match = sUriMatcher.match(uri);
        if (match == 1) {
            insertIntoEvents = insertIntoEvents(contentValues);
        } else if (match == 3) {
            insertIntoEvents = insertIntoFeaturesBlacklist(contentValues);
        } else if (match == 4) {
            insertIntoEvents = insertIntoVersion(contentValues);
        } else if (match == 5) {
            insertIntoEvents = insertIntoCleanedEvents(contentValues);
        } else if (match == 6) {
            insertIntoEvents = insertIntoFeaturesWhitelist(contentValues);
        } else if (match == 7) {
            insertIntoEvents = insertIntoB2CFeatures(contentValues);
        } else {
            Log.d(str, "insert(): no match for URI");
            return null;
        }
        if (insertIntoEvents == null) {
            return insertIntoEvents;
        }
        Log.d(str, "insert(): notifyChange(" + insertIntoEvents.toString() + NavigationBarInflaterView.KEY_CODE_END);
        try {
            j = Binder.clearCallingIdentity();
        } catch (Throwable th) {
            th = th;
            j = -1;
        }
        try {
            getContext().getContentResolver().notifyChange(insertIntoEvents, null);
            Binder.restoreCallingIdentity(j);
            return insertIntoEvents;
        } catch (Throwable th2) {
            th = th2;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String str3 = TAG;
        Log.d(str3, "query()");
        synchronized (this.mDeactivationLock) {
            DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
            SecurityUtils.enforceProviderCaller(getContext(), getCallingPackage());
            switch (sUriMatcher.match(uri)) {
                case 1:
                    return queryEvents(strArr, str, strArr2);
                case 2:
                    return databaseCryptoAdapter.getDatabaseSizeCursor();
                case 3:
                    return databaseCryptoAdapter.getFeatureBlacklistCursor();
                case 4:
                    return databaseCryptoAdapter.getVersioningBlob();
                case 5:
                    return databaseCryptoAdapter.getCleanedEventsCursor();
                case 6:
                    return databaseCryptoAdapter.getFeatureWhitelistCursor();
                case 7:
                    return databaseCryptoAdapter.getB2CFeaturesCursor(strArr2);
                default:
                    Log.d(str3, "query(): no match for URI");
                    return null;
            }
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        long deleteFromEvents;
        long j;
        String str2 = TAG;
        Log.d(str2, "delete() - " + uri);
        SecurityUtils.enforceProviderCaller(getContext(), getCallingPackage());
        switch (sUriMatcher.match(uri)) {
            case 1:
                deleteFromEvents = deleteFromEvents(str, strArr, 1);
                break;
            case 2:
            default:
                Log.d(str2, "delete(): no match for URI");
                return 0;
            case 3:
                deleteFromEvents = deleteFromFeaturesBlacklist(str, strArr);
                break;
            case 4:
                deleteFromEvents = deleteFromVersion(str, strArr);
                break;
            case 5:
                deleteFromEvents = deleteFromEvents(str, strArr, 0);
                break;
            case 6:
                deleteFromEvents = deleteFromFeaturesWhitelist(str, strArr);
                break;
            case 7:
                deleteFromEvents = deleteFromB2CFeatures(str, strArr);
                break;
            case 8:
                deleteFromEvents = deleteFromAllEventTables();
                break;
        }
        int i = (int) deleteFromEvents;
        if (i <= 0 || uri == null) {
            return i;
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        try {
            j = Binder.clearCallingIdentity();
        } catch (Throwable th) {
            th = th;
            j = -1;
        }
        try {
            contentResolver.notifyChange(uri, null);
            Binder.restoreCallingIdentity(j);
            return i;
        } catch (Throwable th2) {
            th = th2;
            Binder.restoreCallingIdentity(j);
            throw th;
        }
    }

    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        str.hashCode();
        switch (str) {
            case "dumpCache":
            case "saveCache":
                if (!SecurityUtils.isAnalyticsTestDevice(getContext(), getCallingPackage())) {
                    Log.e(TAG, "call(): " + str + " can only be called by KnoxAnalyticsTestApp");
                    return null;
                }
                return debugCall(str);
            case "databaseClean":
                return callDatabaseClean(str2, bundle);
            case "insertBulkEvents":
                long addBulkEvents = getDatabaseCryptoAdapter().addBulkEvents(bundle);
                if (addBulkEvents >= 0) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        getContext().getContentResolver().notifyChange(Contract.CONTENT_URI, null);
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
                Bundle bundle2 = new Bundle();
                bundle2.putLong("lastEventId", addBulkEvents);
                return bundle2;
            case "notifyVersioningCompleted":
                getDatabaseCryptoAdapter().notifyVersioningCompleted();
                return null;
            case "performCompressedEventsTransaction":
                return getDatabaseCryptoAdapter().performCompressedEventsTransaction(bundle);
            default:
                Log.e(TAG, "call(): invalid method " + str);
                return null;
        }
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Log.d(TAG, "update()");
        return 0;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        Log.d(TAG, "getType()");
        return null;
    }

    private DatabaseCryptoAdapter getDatabaseCryptoAdapter() {
        DatabaseCryptoAdapter databaseCryptoAdapter;
        DatabaseCryptoAdapter databaseCryptoAdapter2 = this.mDatabaseCryptoAdapter;
        if (databaseCryptoAdapter2 != null) {
            return databaseCryptoAdapter2;
        }
        synchronized (this) {
            databaseCryptoAdapter = this.mDatabaseCryptoAdapter;
            if (databaseCryptoAdapter == null) {
                databaseCryptoAdapter = new DatabaseCryptoAdapter(getContext());
                this.mDatabaseCryptoAdapter = databaseCryptoAdapter;
            }
        }
        return databaseCryptoAdapter;
    }

    private Cursor queryEvents(String[] strArr, String str, String[] strArr2) {
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        if (strArr != null && strArr.length > 0) {
            String str2 = strArr[0];
            str2.hashCode();
            switch (str2) {
                case "chunkSizePlainEvents":
                    return databaseCryptoAdapter.getEventChunk(1000, true);
                case "count":
                    return databaseCryptoAdapter.getEventCount();
                case "lastEventId":
                    return databaseCryptoAdapter.getLastId();
            }
        }
        return databaseCryptoAdapter.getEventChunk(getFinalChunkSize(str, strArr2), false);
    }

    private Integer getFinalChunkSize(String str, String[] strArr) {
        Log.d(TAG, "getFinalChunkSize()");
        Integer valueOf = Contract.Events.Selection.CHUNK_SIZE.equals(str) ? Integer.valueOf(strArr[0]) : null;
        if (getDatabaseCryptoAdapter().getCompressedEventsCount() == 0 || valueOf == null || valueOf.intValue() % 1000 == 0) {
            return valueOf;
        }
        throw new IllegalArgumentException("query(): Selection argument must be null or multiples of 1000");
    }

    private Uri insertIntoEvents(ContentValues contentValues) {
        long addEvent = getDatabaseCryptoAdapter().addEvent(contentValues);
        if (addEvent == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.Events.CONTENT_URI, addEvent);
    }

    private Uri insertIntoFeaturesBlacklist(ContentValues contentValues) {
        Log.d(TAG, "insertIntoFeaturesBlacklist()");
        if (getDatabaseCryptoAdapter().addFeatureBlacklist(contentValues) >= 0) {
            return Contract.FeaturesBlacklist.CONTENT_URI;
        }
        return null;
    }

    private Uri insertIntoFeaturesWhitelist(ContentValues contentValues) {
        Log.d(TAG, "insertIntoFeaturesWhitelist()");
        if (getDatabaseCryptoAdapter().addFeatureWhitelist(contentValues) >= 0) {
            return Contract.FeaturesWhitelist.CONTENT_URI;
        }
        return null;
    }

    private Uri insertIntoB2CFeatures(ContentValues contentValues) {
        Log.d(TAG, "insertIntoB2CFeatures()");
        if (getDatabaseCryptoAdapter().addB2CFeatures(contentValues) >= 0) {
            return Contract.B2CFeatures.CONTENT_URI;
        }
        return null;
    }

    private Uri insertIntoVersion(ContentValues contentValues) {
        Log.d(TAG, "insertIntoVersion()");
        long addVersioningBlob = getDatabaseCryptoAdapter().addVersioningBlob(contentValues);
        if (addVersioningBlob == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.Versioning.CONTENT_URI, addVersioningBlob);
    }

    private Uri insertIntoCleanedEvents(ContentValues contentValues) {
        Log.d(TAG, "insertIntoCleanedEvents()");
        long addCleanedEvent = getDatabaseCryptoAdapter().addCleanedEvent(contentValues);
        if (addCleanedEvent == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.DatabaseClean.CONTENT_URI, addCleanedEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x006f, code lost:
    
        if (r12.equals(com.samsung.android.knox.analytics.database.Contract.Events.Selection.DELETE_BY_SIZE) == false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private long deleteFromEvents(java.lang.String r12, java.lang.String[] r13, int r14) {
        /*
            r11 = this;
            java.lang.String r0 = com.samsung.android.knox.analytics.database.KnoxAnalyticsContentProvider.TAG
            java.lang.String r1 = "deleteFromEvents()"
            com.samsung.android.knox.analytics.util.Log.d(r0, r1)
            r1 = 0
            if (r13 == 0) goto La3
            int r3 = r13.length
            if (r3 != 0) goto L10
            goto La3
        L10:
            r3 = 0
            r4 = r13[r3]
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L1f
            java.lang.String r11 = "deleteFromEvents(): empty selectionArgs[0]"
            com.samsung.android.knox.analytics.util.Log.e(r0, r11)
            return r1
        L1f:
            r4 = r13[r3]     // Catch: java.lang.NumberFormatException -> L8d
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch: java.lang.NumberFormatException -> L8d
            long r6 = r4.longValue()     // Catch: java.lang.NumberFormatException -> L8d
            int r4 = r13.length     // Catch: java.lang.NumberFormatException -> L8d
            r5 = 2
            r8 = 1
            if (r4 != r5) goto L41
            r4 = r13[r8]     // Catch: java.lang.NumberFormatException -> L8d
            boolean r4 = r4.isEmpty()     // Catch: java.lang.NumberFormatException -> L8d
            if (r4 != 0) goto L41
            r4 = r13[r8]     // Catch: java.lang.NumberFormatException -> L8d
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch: java.lang.NumberFormatException -> L8d
            long r9 = r4.longValue()     // Catch: java.lang.NumberFormatException -> L8d
            goto L42
        L41:
            r9 = r1
        L42:
            com.samsung.android.knox.analytics.database.DatabaseCryptoAdapter r11 = r11.getDatabaseCryptoAdapter()
            r12.hashCode()
            int r13 = r12.hashCode()
            r4 = -1
            switch(r13) {
                case -774791398: goto L69;
                case -707369028: goto L5e;
                case -17614173: goto L53;
                default: goto L51;
            }
        L51:
            r3 = r4
            goto L72
        L53:
            java.lang.String r13 = "deleteUntilTargetDbSize"
            boolean r12 = r12.equals(r13)
            if (r12 != 0) goto L5c
            goto L51
        L5c:
            r3 = r5
            goto L72
        L5e:
            java.lang.String r13 = "deleteUpToId"
            boolean r12 = r12.equals(r13)
            if (r12 != 0) goto L67
            goto L51
        L67:
            r3 = r8
            goto L72
        L69:
            java.lang.String r13 = "deleteChunkBySize"
            boolean r12 = r12.equals(r13)
            if (r12 != 0) goto L72
            goto L51
        L72:
            switch(r3) {
                case 0: goto L85;
                case 1: goto L80;
                case 2: goto L7b;
                default: goto L75;
            }
        L75:
            java.lang.String r11 = "deleteFromEvents(): invalid selection"
            com.samsung.android.knox.analytics.util.Log.e(r0, r11)
            return r1
        L7b:
            long r11 = r11.deleteUntilTargetDbSize(r6)
            return r11
        L80:
            long r11 = r11.deleteUpTo(r6)
            return r11
        L85:
            r5 = r11
            r8 = r9
            r10 = r14
            long r11 = r5.deleteEventChunk(r6, r8, r10)
            return r11
        L8d:
            java.lang.String r11 = com.samsung.android.knox.analytics.database.KnoxAnalyticsContentProvider.TAG
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r14 = "deleteFromEvents(): invalid number "
            r12.<init>(r14)
            r13 = r13[r3]
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            com.samsung.android.knox.analytics.util.Log.e(r11, r12)
            return r1
        La3:
            java.lang.String r11 = "deleteFromEvents(): no selectionArgs"
            com.samsung.android.knox.analytics.util.Log.e(r0, r11)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.analytics.database.KnoxAnalyticsContentProvider.deleteFromEvents(java.lang.String, java.lang.String[], int):long");
    }

    private long deleteFromAllEventTables() {
        long deleteFromAllEventTables;
        synchronized (this.mDeactivationLock) {
            deleteFromAllEventTables = getDatabaseCryptoAdapter().deleteFromAllEventTables();
        }
        return deleteFromAllEventTables;
    }

    private long deleteFromFeaturesBlacklist(String str, String[] strArr) {
        Log.d(TAG, "deleteFromFeaturesBlacklist()");
        return getDatabaseCryptoAdapter().deleteFeatureBlacklist();
    }

    private long deleteFromFeaturesWhitelist(String str, String[] strArr) {
        Log.d(TAG, "deleteFromFeaturesWhitelist(" + str);
        return getDatabaseCryptoAdapter().deleteFeatureWhitelist(strArr);
    }

    private long deleteFromB2CFeatures(String str, String[] strArr) {
        Log.d(TAG, "deleteFromB2CFeatures(" + str);
        return getDatabaseCryptoAdapter().deleteB2CFeatures(strArr);
    }

    private long deleteFromVersion(String str, String[] strArr) {
        String str2 = TAG;
        Log.d(str2, "deleteFromVersion()");
        if (strArr == null || strArr.length == 0) {
            Log.e(str2, "deleteFromVersion(): no selectionArgs");
            return 0L;
        }
        if (strArr[0].isEmpty()) {
            Log.e(str2, "deleteFromVersion(): empty selectionArgs[0]");
            return 0L;
        }
        try {
            return getDatabaseCryptoAdapter().deleteFromVersion(Long.valueOf(strArr[0]).longValue());
        } catch (NumberFormatException unused) {
            Log.e(TAG, "deleteFromVersion(): invalid number " + strArr[0]);
            return 0L;
        }
    }

    Bundle callDatabaseClean(String str, Bundle bundle) {
        if (!bundle.containsKey(Contract.DatabaseClean.Extra.TARGET_DB_SIZE)) {
            Log.e(TAG, "callDatabaseClean(): wrong extras!");
            return null;
        }
        Log.d(TAG, "callDatabaseClean()");
        long databaseSizeInBytes = getDatabaseCryptoAdapter().getDatabaseSizeInBytes();
        long cleanCompressedEventsTable = cleanCompressedEventsTable(bundle.getLong(Contract.DatabaseClean.Extra.TARGET_DB_SIZE));
        long databaseSizeInBytes2 = getDatabaseCryptoAdapter().getDatabaseSizeInBytes();
        Bundle bundle2 = new Bundle();
        bundle2.putLong(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT, cleanCompressedEventsTable);
        bundle2.putLong(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES, databaseSizeInBytes - databaseSizeInBytes2);
        return bundle2;
    }

    public long cleanCompressedEventsTable(long j) {
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long databaseSizeInBytes = databaseCryptoAdapter.getDatabaseSizeInBytes();
        int i = 0;
        long j2 = 0;
        while (true) {
            if (databaseSizeInBytes <= j) {
                break;
            }
            i++;
            int totalCompressedEvents = databaseCryptoAdapter.getTotalCompressedEvents((int) 1);
            long deleteCompressedEventChunk = databaseCryptoAdapter.deleteCompressedEventChunk(1L);
            String str = TAG;
            Log.d(str, "IT=" + i + " curS=" + databaseSizeInBytes + " tlDel=" + j2 + " nxtCh=" + totalCompressedEvents + " delRows=" + deleteCompressedEventChunk);
            if (deleteCompressedEventChunk == 0) {
                Log.e(str, "cleanCompressedEventsTable(): error deleting or db is empty");
                break;
            }
            j2 += totalCompressedEvents;
            databaseSizeInBytes = databaseCryptoAdapter.getDatabaseSizeInBytes();
        }
        Log.d(TAG, "cleanCompressedEventsTable(): deletedEvents = " + j2 + " iteration = " + i);
        return j2;
    }

    public Bundle debugCall(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getContext().getContentResolver().notifyChange(Uri.withAppendedPath(Contract.Debug.CONTENT_URI, str), null);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            Bundle bundle = new Bundle();
            bundle.putBoolean("result", true);
            return bundle;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
