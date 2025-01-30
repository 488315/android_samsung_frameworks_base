package com.samsung.android.knox.analytics.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.Bundle;
import com.samsung.android.knox.analytics.database.Contract;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.SecurityUtils;

/* loaded from: classes5.dex */
public class KnoxAnalyticsContentProvider extends ContentProvider {
    private static final int B2C_FEATURE_PATH_ID = 7;
    private static final int CLEANED_EVENTS_PATH_ID = 5;
    private static final int DATABASE_SIZE_PATH_ID = 2;
    private static final int EVENTS_PATH_ID = 1;
    private static final int FEATURES_BLACKLIST_PATH_ID = 3;
    private static final int FEATURES_WHITELIST_PATH_ID = 6;
    private static final String TAG = "[KnoxAnalytics] " + KnoxAnalyticsContentProvider.class.getSimpleName();
    private static final int VERSIONING_PATH_ID = 4;
    private static final UriMatcher sUriMatcher;
    private volatile DatabaseCryptoAdapter mDatabaseCryptoAdapter;

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
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Log.m286d(TAG, "onCreate()");
        this.mDatabaseCryptoAdapter = null;
        return true;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri;
        String str = TAG;
        Log.m286d(str, "insert()");
        switch (sUriMatcher.match(uri)) {
            case 1:
                SecurityUtils.enforceInternalOnly(getCallingPackage(), Binder.getCallingPid());
                returnUri = insertIntoEvents(values);
                break;
            case 2:
            default:
                Log.m286d(str, "delete(): no match for URI");
                return null;
            case 3:
                SecurityUtils.enforcePackageNameForContentProvider(getCallingPackage(), Binder.getCallingPid());
                returnUri = insertIntoFeaturesBlacklist(values);
                break;
            case 4:
                SecurityUtils.enforceInternalOnly(getCallingPackage(), Binder.getCallingPid());
                returnUri = insertIntoVersion(values);
                break;
            case 5:
                SecurityUtils.enforceInternalOnly(getCallingPackage(), Binder.getCallingPid());
                returnUri = insertIntoCleanedEvents(values);
                break;
            case 6:
                SecurityUtils.enforcePackageNameForContentProvider(getCallingPackage(), Binder.getCallingPid());
                returnUri = insertIntoFeaturesWhitelist(values);
                break;
            case 7:
                SecurityUtils.enforcePackageNameForContentProvider(getCallingPackage(), Binder.getCallingPid());
                returnUri = insertIntoB2CFeatures(values);
                break;
        }
        if (returnUri != null) {
            Log.m286d(str, "insert(): notifyChange(" + returnUri.toString() + NavigationBarInflaterView.KEY_CODE_END);
            long token = -1;
            try {
                token = Binder.clearCallingIdentity();
                getContext().getContentResolver().notifyChange(returnUri, null);
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }
        return returnUri;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String str = TAG;
        Log.m286d(str, "query()");
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        switch (sUriMatcher.match(uri)) {
            case 1:
                SecurityUtils.enforcePackageNameForContentProvider(getCallingPackage(), Binder.getCallingPid());
                return queryEvents(projection, selection, selectionArgs);
            case 2:
                SecurityUtils.enforceInternalOnly(getCallingPackage(), Binder.getCallingPid());
                return databaseCryptoAdapter.getDatabaseSizeCursor();
            case 3:
                SecurityUtils.enforceInternalOnly(getCallingPackage(), Binder.getCallingPid());
                return databaseCryptoAdapter.getFeatureBlacklistCursor();
            case 4:
                SecurityUtils.enforcePackageNameForContentProvider(getCallingPackage(), Binder.getCallingPid());
                return databaseCryptoAdapter.getVersioningBlob();
            case 5:
                SecurityUtils.enforcePackageNameForContentProvider(getCallingPackage(), Binder.getCallingPid());
                return databaseCryptoAdapter.getCleanedEventsCursor(getFinalChunkSize(selection, selectionArgs));
            case 6:
                SecurityUtils.enforceInternalOnly(getCallingPackage(), Binder.getCallingPid());
                return databaseCryptoAdapter.getFeatureWhitelistCursor();
            case 7:
                SecurityUtils.enforceInternalOnly(getCallingPackage(), Binder.getCallingPid());
                return databaseCryptoAdapter.getB2CFeaturesCursor(selectionArgs);
            default:
                Log.m286d(str, "query(): no match for URI");
                return null;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int affectedRows;
        String str = TAG;
        Log.m286d(str, "delete()");
        SecurityUtils.enforcePackageNameForContentProvider(getCallingPackage(), Binder.getCallingPid());
        switch (sUriMatcher.match(uri)) {
            case 1:
                affectedRows = (int) deleteFromEvents(selection, selectionArgs, 1);
                break;
            case 2:
            default:
                Log.m286d(str, "delete(): no match for URI");
                return 0;
            case 3:
                affectedRows = (int) deleteFromFeaturesBlacklist(selection, selectionArgs);
                break;
            case 4:
                affectedRows = (int) deleteFromVersion(selection, selectionArgs);
                break;
            case 5:
                affectedRows = (int) deleteFromEvents(selection, selectionArgs, 0);
                break;
            case 6:
                affectedRows = (int) deleteFromFeaturesWhitelist(selection, selectionArgs);
                break;
            case 7:
                affectedRows = (int) deleteFromB2CFeatures(selection, selectionArgs);
                break;
        }
        if (affectedRows > 0 && uri != null) {
            ContentResolver contentResolver = getContext().getContentResolver();
            long token = -1;
            try {
                token = Binder.clearCallingIdentity();
                contentResolver.notifyChange(uri, null);
            } finally {
                Binder.restoreCallingIdentity(token);
            }
        }
        return affectedRows;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.ContentProvider
    public Bundle call(String method, String arg, Bundle extras) {
        char c;
        switch (method.hashCode()) {
            case -1356830322:
                if (method.equals(Contract.DatabaseClean.METHOD)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1073228700:
                if (method.equals(Contract.Events.Extra.INSERT_BULK_EVENTS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 404670296:
                if (method.equals(Contract.Versioning.METHOD_NOTIFY_VERSIONING_COMPLETED)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1374942819:
                if (method.equals(Contract.CompressedEvents.METHOD_PERFORM_COMPRESSED_EVENTS_TRANSACTION)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return callDatabaseClean(arg, extras);
            case 1:
                getDatabaseCryptoAdapter().notifyVersioningCompleted();
                return null;
            case 2:
                return getDatabaseCryptoAdapter().performCompressedEventsTransaction(extras);
            case 3:
                long lastId = getDatabaseCryptoAdapter().addBulkEvents(extras);
                if (lastId >= 0) {
                    long token = Binder.clearCallingIdentity();
                    try {
                        getContext().getContentResolver().notifyChange(Contract.CONTENT_URI, null);
                    } finally {
                        Binder.restoreCallingIdentity(token);
                    }
                }
                Bundle result = new Bundle();
                result.putLong("lastEventId", lastId);
                return result;
            default:
                Log.m287e(TAG, "call(): invalid method " + method);
                return null;
        }
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.m286d(TAG, "update()");
        return 0;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        Log.m286d(TAG, "getType()");
        return null;
    }

    private DatabaseCryptoAdapter getDatabaseCryptoAdapter() {
        DatabaseCryptoAdapter result = this.mDatabaseCryptoAdapter;
        if (result == null) {
            synchronized (this) {
                result = this.mDatabaseCryptoAdapter;
                if (result == null) {
                    DatabaseCryptoAdapter databaseCryptoAdapter = new DatabaseCryptoAdapter(getContext());
                    result = databaseCryptoAdapter;
                    this.mDatabaseCryptoAdapter = databaseCryptoAdapter;
                }
            }
        }
        return result;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private Cursor queryEvents(String[] projection, String selection, String[] selectionArgs) {
        char c;
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        if (projection != null && projection.length > 0) {
            String str = projection[0];
            switch (str.hashCode()) {
                case -114761067:
                    if (str.equals(Contract.Events.Projection.CHUNK_SIZE_ONLY_PLAIN_EVENTS)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 94851343:
                    if (str.equals(Contract.Events.Projection.COUNT_ONLY)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 507661791:
                    if (str.equals("lastEventId")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return databaseCryptoAdapter.getLastId();
                case 1:
                    return databaseCryptoAdapter.getEventCount();
                case 2:
                    return databaseCryptoAdapter.getEventChunk(1000, true);
            }
        }
        return databaseCryptoAdapter.getEventChunk(getFinalChunkSize(selection, selectionArgs), false);
    }

    private Integer getFinalChunkSize(String selection, String[] selectionArgs) {
        Log.m286d(TAG, "getFinalChunkSize()");
        Integer chunkSize = null;
        if (Contract.Events.Selection.CHUNK_SIZE.equals(selection)) {
            chunkSize = Integer.valueOf(selectionArgs[0]);
        }
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        if (databaseCryptoAdapter.getCompressedEventsCount() == 0) {
            return chunkSize;
        }
        if (chunkSize != null && chunkSize.intValue() % 1000 != 0) {
            throw new IllegalArgumentException("query(): Selection argument must be null or multiples of 1000");
        }
        return chunkSize;
    }

    private Uri insertIntoEvents(ContentValues contentValues) {
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long id = databaseCryptoAdapter.addEvent(contentValues);
        if (id == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.Events.CONTENT_URI, id);
    }

    private Uri insertIntoFeaturesBlacklist(ContentValues contentValues) {
        Log.m286d(TAG, "insertIntoFeaturesBlacklist()");
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long affected = databaseCryptoAdapter.addFeatureBlacklist(contentValues);
        if (affected < 0) {
            return null;
        }
        Uri ret = Contract.FeaturesBlacklist.CONTENT_URI;
        return ret;
    }

    private Uri insertIntoFeaturesWhitelist(ContentValues contentValues) {
        Log.m286d(TAG, "insertIntoFeaturesWhitelist()");
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long affected = databaseCryptoAdapter.addFeatureWhitelist(contentValues);
        if (affected < 0) {
            return null;
        }
        Uri ret = Contract.FeaturesWhitelist.CONTENT_URI;
        return ret;
    }

    private Uri insertIntoB2CFeatures(ContentValues contentValues) {
        Log.m286d(TAG, "insertIntoB2CFeatures()");
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long affected = databaseCryptoAdapter.addB2CFeatures(contentValues);
        if (affected < 0) {
            return null;
        }
        Uri ret = Contract.B2CFeatures.CONTENT_URI;
        return ret;
    }

    private Uri insertIntoVersion(ContentValues contentValues) {
        Log.m286d(TAG, "insertIntoVersion()");
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long id = databaseCryptoAdapter.addVersioningBlob(contentValues);
        if (id == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.Versioning.CONTENT_URI, id);
    }

    private Uri insertIntoCleanedEvents(ContentValues values) {
        Log.m286d(TAG, "insertIntoCleanedEvents()");
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long id = databaseCryptoAdapter.addCleanedEvent(values);
        if (id == -1) {
            return null;
        }
        return ContentUris.withAppendedId(Contract.DatabaseClean.CONTENT_URI, id);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0064, code lost:
    
        if (r17.equals(com.samsung.android.knox.analytics.database.Contract.Events.Selection.DELETE_UP_TO_ID) != false) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private long deleteFromEvents(String selection, String[] selectionArgs, int type) {
        String str = TAG;
        Log.m286d(str, "deleteFromEvents()");
        if (selectionArgs == null || selectionArgs.length == 0) {
            Log.m287e(str, "deleteFromEvents(): no selectionArgs");
            return 0L;
        }
        char c = 0;
        if (selectionArgs[0].isEmpty()) {
            Log.m287e(str, "deleteFromEvents(): empty selectionArgs[0]");
            return 0L;
        }
        long numberOfEvents = 0;
        try {
            long value = Long.valueOf(selectionArgs[0]).longValue();
            if (selectionArgs.length == 2 && !selectionArgs[1].isEmpty()) {
                numberOfEvents = Long.valueOf(selectionArgs[1]).longValue();
            }
            DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
            switch (selection.hashCode()) {
                case -774791398:
                    if (selection.equals(Contract.Events.Selection.DELETE_BY_SIZE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -707369028:
                    break;
                case -17614173:
                    if (selection.equals(Contract.Events.Selection.DELETE_UNTIL_TARGET_DB_SIZE)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
                    Log.m287e(str, "deleteFromEvents(): invalid selection");
                    break;
            }
        } catch (NumberFormatException e) {
            Log.m287e(TAG, "deleteFromEvents(): invalid number " + selectionArgs[0]);
            return 0L;
        }
        return 0L;
    }

    private long deleteFromFeaturesBlacklist(String selection, String[] selectionArgs) {
        Log.m286d(TAG, "deleteFromFeaturesBlacklist()");
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        return databaseCryptoAdapter.deleteFeatureBlacklist();
    }

    private long deleteFromFeaturesWhitelist(String selection, String[] selectionArgs) {
        Log.m286d(TAG, "deleteFromFeaturesWhitelist(" + selection);
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        return databaseCryptoAdapter.deleteFeatureWhitelist(selectionArgs);
    }

    private long deleteFromB2CFeatures(String selection, String[] selectionArgs) {
        Log.m286d(TAG, "deleteFromB2CFeatures(" + selection);
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        return databaseCryptoAdapter.deleteB2CFeatures(selectionArgs);
    }

    private long deleteFromVersion(String selection, String[] selectionArgs) {
        String str = TAG;
        Log.m286d(str, "deleteFromVersion()");
        if (selectionArgs == null || selectionArgs.length == 0) {
            Log.m287e(str, "deleteFromVersion(): no selectionArgs");
            return 0L;
        }
        if (selectionArgs[0].isEmpty()) {
            Log.m287e(str, "deleteFromVersion(): empty selectionArgs[0]");
            return 0L;
        }
        try {
            long value = Long.valueOf(selectionArgs[0]).longValue();
            DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
            return databaseCryptoAdapter.deleteFromVersion(value);
        } catch (NumberFormatException e) {
            Log.m287e(TAG, "deleteFromVersion(): invalid number " + selectionArgs[0]);
            return 0L;
        }
    }

    Bundle callDatabaseClean(String arg, Bundle extras) {
        if (!extras.containsKey(Contract.DatabaseClean.Extra.TARGET_DB_SIZE)) {
            Log.m287e(TAG, "callDatabaseClean(): wrong extras!");
            return null;
        }
        Log.m286d(TAG, "callDatabaseClean()");
        long initialSize = getDatabaseCryptoAdapter().getDatabaseSizeInBytes();
        long deletedEventsCount = cleanCompressedEventsTable(extras.getLong(Contract.DatabaseClean.Extra.TARGET_DB_SIZE));
        long finalSize = getDatabaseCryptoAdapter().getDatabaseSizeInBytes();
        Bundle resultBundle = new Bundle();
        resultBundle.putLong(Contract.DatabaseClean.Extra.DELETED_EVENTS_COUNT, deletedEventsCount);
        resultBundle.putLong(Contract.DatabaseClean.Extra.DELETED_SIZE_BYTES, initialSize - finalSize);
        return resultBundle;
    }

    public long cleanCompressedEventsTable(long targetDbSizeInBytes) {
        DatabaseCryptoAdapter databaseCryptoAdapter = getDatabaseCryptoAdapter();
        long currentSize = databaseCryptoAdapter.getDatabaseSizeInBytes();
        long totalDeleted = 0;
        int iteration = 0;
        while (true) {
            if (currentSize <= targetDbSizeInBytes) {
                break;
            }
            iteration++;
            int currentEvents = databaseCryptoAdapter.getTotalCompressedEvents((int) 1);
            long deletedRows = databaseCryptoAdapter.deleteCompressedEventChunk(1L);
            String str = TAG;
            Log.m286d(str, "IT=" + iteration + " curS=" + currentSize + " tlDel=" + totalDeleted + " nxtCh=" + currentEvents + " delRows=" + deletedRows);
            if (deletedRows == 0) {
                Log.m287e(str, "cleanCompressedEventsTable(): error deleting or db is empty");
                break;
            }
            totalDeleted += currentEvents;
            currentSize = databaseCryptoAdapter.getDatabaseSizeInBytes();
        }
        Log.m286d(TAG, "cleanCompressedEventsTable(): deletedEvents = " + totalDeleted + " iteration = " + iteration);
        return totalDeleted;
    }
}
