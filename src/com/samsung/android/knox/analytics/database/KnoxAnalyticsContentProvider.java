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
    public Uri insert(Uri uri, ContentValues contentValues) throws Throwable {
        Uri uriInsertIntoEvents;
        long jClearCallingIdentity;
        String str = TAG;
        Log.d(str, "insert()");
        SecurityUtils.enforceProviderCaller(getContext(), getCallingPackage());
        int iMatch = sUriMatcher.match(uri);
        if (iMatch == 1) {
            uriInsertIntoEvents = insertIntoEvents(contentValues);
        } else if (iMatch == 3) {
            uriInsertIntoEvents = insertIntoFeaturesBlacklist(contentValues);
        } else if (iMatch == 4) {
            uriInsertIntoEvents = insertIntoVersion(contentValues);
        } else if (iMatch == 5) {
            uriInsertIntoEvents = insertIntoCleanedEvents(contentValues);
        } else if (iMatch == 6) {
            uriInsertIntoEvents = insertIntoFeaturesWhitelist(contentValues);
        } else if (iMatch == 7) {
            uriInsertIntoEvents = insertIntoB2CFeatures(contentValues);
        } else {
            Log.d(str, "insert(): no match for URI");
            return null;
        }
        if (uriInsertIntoEvents == null) {
            return uriInsertIntoEvents;
        }
        Log.d(str, "insert(): notifyChange(" + uriInsertIntoEvents.toString() + NavigationBarInflaterView.KEY_CODE_END);
        try {
            jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                getContext().getContentResolver().notifyChange(uriInsertIntoEvents, null);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return uriInsertIntoEvents;
            } catch (Throwable th) {
                th = th;
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            jClearCallingIdentity = -1;
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
    public int delete(Uri uri, String str, String[] strArr) throws Throwable {
        long jDeleteFromEvents;
        long jClearCallingIdentity;
        String str2 = TAG;
        Log.d(str2, "delete() - " + uri);
        SecurityUtils.enforceProviderCaller(getContext(), getCallingPackage());
        switch (sUriMatcher.match(uri)) {
            case 1:
                jDeleteFromEvents = deleteFromEvents(str, strArr, 1);
                break;
            case 2:
            default:
                Log.d(str2, "delete(): no match for URI");
                return 0;
            case 3:
                jDeleteFromEvents = deleteFromFeaturesBlacklist(str, strArr);
                break;
            case 4:
                jDeleteFromEvents = deleteFromVersion(str, strArr);
                break;
            case 5:
                jDeleteFromEvents = deleteFromEvents(str, strArr, 0);
                break;
            case 6:
                jDeleteFromEvents = deleteFromFeaturesWhitelist(str, strArr);
                break;
            case 7:
                jDeleteFromEvents = deleteFromB2CFeatures(str, strArr);
                break;
            case 8:
                jDeleteFromEvents = deleteFromAllEventTables();
                break;
        }
        int i = (int) jDeleteFromEvents;
        if (i <= 0 || uri == null) {
            return i;
        }
        ContentResolver contentResolver = getContext().getContentResolver();
        try {
            jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                contentResolver.notifyChange(uri, null);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return i;
            } catch (Throwable th) {
                th = th;
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            jClearCallingIdentity = -1;
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
                long jAddBulkEvents = getDatabaseCryptoAdapter().addBulkEvents(bundle);
                if (jAddBulkEvents >= 0) {
                    long jClearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        getContext().getContentResolver().notifyChange(Contract.CONTENT_URI, null);
                    } finally {
                        Binder.restoreCallingIdentity(jClearCallingIdentity);
                    }
                }
                Bundle bundle2 = new Bundle();
                bundle2.putLong("lastEventId", jAddBulkEvents);
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
        Integer numValueOf = Contract.Events.Selection.CHUNK_SIZE.equals(str) ? Integer.valueOf(strArr[0]) : null;
        if (getDatabaseCryptoAdapter().getCompressedEventsCount() == 0 || numValueOf == null || numValueOf.intValue() % 1000 == 0) {
            return numValueOf;
        }
        throw new IllegalArgumentException("query(): Selection argument must be null or multiples of 1000");
    }

    private Uri insertIntoEvents(ContentValues contentValues) {
        long jAddEvent = getDatabaseCryptoAdapter().addEvent(contentValues);
        if (jAddEvent == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.Events.CONTENT_URI, jAddEvent);
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
        long jAddVersioningBlob = getDatabaseCryptoAdapter().addVersioningBlob(contentValues);
        if (jAddVersioningBlob == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.Versioning.CONTENT_URI, jAddVersioningBlob);
    }

    private Uri insertIntoCleanedEvents(ContentValues contentValues) {
        Log.d(TAG, "insertIntoCleanedEvents()");
        long jAddCleanedEvent = getDatabaseCryptoAdapter().addCleanedEvent(contentValues);
        if (jAddCleanedEvent == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.DatabaseClean.CONTENT_URI, jAddCleanedEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private long deleteFromEvents(String str, String[] strArr, int i) {
        String str2 = TAG;
        Log.d(str2, "deleteFromEvents()");
        if (strArr == null || strArr.length == 0) {
            Log.e(str2, "deleteFromEvents(): no selectionArgs");
            return 0L;
        }
        boolean z = false;
        if (strArr[0].isEmpty()) {
            Log.e(str2, "deleteFromEvents(): empty selectionArgs[0]");
            return 0L;
        }
        try {
            long jLongValue = Long.valueOf(strArr[0]).longValue();
            long jLongValue2 = (strArr.length != 2 || strArr[1].isEmpty()) ? 0L : Long.valueOf(strArr[1]).longValue();
            DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
            str.hashCode();
            switch (str.hashCode()) {
                case -774791398:
                    if (!str.equals(Contract.Events.Selection.DELETE_BY_SIZE)) {
                        z = -1;
                        break;
                    }
                    break;
                case -707369028:
                    if (str.equals(Contract.Events.Selection.DELETE_UP_TO_ID)) {
                        z = true;
                        break;
                    }
                    break;
                case -17614173:
                    if (str.equals(Contract.Events.Selection.DELETE_UNTIL_TARGET_DB_SIZE)) {
                        z = 2;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    break;
                case true:
                    break;
                case true:
                    break;
                default:
                    Log.e(str2, "deleteFromEvents(): invalid selection");
                    break;
            }
        } catch (NumberFormatException unused) {
            Log.e(TAG, "deleteFromEvents(): invalid number " + strArr[0]);
            return 0L;
        }
        return 0L;
    }

    private long deleteFromAllEventTables() {
        long jDeleteFromAllEventTables;
        synchronized (this.mDeactivationLock) {
            jDeleteFromAllEventTables = getDatabaseCryptoAdapter().deleteFromAllEventTables();
        }
        return jDeleteFromAllEventTables;
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
        long jCleanCompressedEventsTable = cleanCompressedEventsTable(bundle.getLong(Contract.DatabaseClean.Extra.TARGET_DB_SIZE));
        long databaseSizeInBytes2 = getDatabaseCryptoAdapter().getDatabaseSizeInBytes();
        Bundle bundle2 = new Bundle();
        bundle2.putLong(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT, jCleanCompressedEventsTable);
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
            long jDeleteCompressedEventChunk = databaseCryptoAdapter.deleteCompressedEventChunk(1L);
            String str = TAG;
            Log.d(str, "IT=" + i + " curS=" + databaseSizeInBytes + " tlDel=" + j2 + " nxtCh=" + totalCompressedEvents + " delRows=" + jDeleteCompressedEventChunk);
            if (jDeleteCompressedEventChunk == 0) {
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
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            getContext().getContentResolver().notifyChange(Uri.withAppendedPath(Contract.Debug.CONTENT_URI, str), null);
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            Bundle bundle = new Bundle();
            bundle.putBoolean("result", true);
            return bundle;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }
}
