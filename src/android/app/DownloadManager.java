package android.app;

import android.annotation.SystemApi;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.Downloads;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.Pair;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes.dex */
public class DownloadManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE";

    @SystemApi
    public static final String ACTION_DOWNLOAD_COMPLETED = "android.intent.action.DOWNLOAD_COMPLETED";
    public static final String ACTION_NOTIFICATION_CLICKED = "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";
    public static final String ACTION_VIEW_DOWNLOADS = "android.intent.action.VIEW_DOWNLOADS";
    public static final String ACTION_VIEW_SEC_DOWNLOADS = "android.intent.action.VIEW_SEC_DOWNLOADS";
    public static final String COLUMN_ALLOW_WRITE = "allow_write";
    public static final String COLUMN_DD_CONTENT_SIZE = "dd_contentSize";
    public static final String COLUMN_DD_FILE_DESCRIPTION = "dd_description";
    public static final String COLUMN_DD_FILE_NAME = "dd_fileName";
    public static final String COLUMN_DD_OBJ_URL = "dd_objUrl";
    public static final String COLUMN_DD_PRIMARY_MIMETYPE = "dd_primaryMimeType";
    public static final String COLUMN_DD_VENDOR_NAME = "dd_vendor";
    public static final String COLUMN_DD_VERSION_NUMBER = "dd_majorVersion";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_DOWNLOAD_METHOD = "downloadmethod";
    public static final String COLUMN_DOWNLOAD_STATE = "state";
    public static final String COLUMN_FILE_NAME_HINT = "hint";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEDIAPROVIDER_URI = "mediaprovider_uri";
    public static final String COLUMN_MEDIASTORE_URI = "mediastore_uri";
    public static final String COLUMN_NOTIFICATION_PACKAGE = "notificationpackage";
    public static final String COLUMN_RANGE_END = "range_end";
    public static final String COLUMN_RANGE_FIRSTCHUNK_END = "range_first_end";
    public static final String COLUMN_RANGE_START = "range_start";
    public static final String COLUMN_REASON = "reason";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_URI = "uri";
    public static final int ERROR_BLOCKED = 1010;
    public static final int ERROR_CANNOT_RESUME = 1008;
    public static final int ERROR_DEVICE_NOT_FOUND = 1007;
    public static final int ERROR_FILE_ALREADY_EXISTS = 1009;
    public static final int ERROR_FILE_ERROR = 1001;
    public static final int ERROR_HTTP_DATA_ERROR = 1004;
    public static final int ERROR_INSUFFICIENT_SPACE = 1006;
    public static final int ERROR_TOO_MANY_REDIRECTS = 1005;
    public static final int ERROR_UNHANDLED_HTTP_CODE = 1002;
    public static final int ERROR_UNKNOWN = 1000;
    public static final String EXTRA_DOWNLOAD_ID = "extra_download_id";
    public static final String EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS = "extra_click_download_ids";
    public static final String INTENT_EXTRAS_SORT_BY_SIZE = "android.app.DownloadManager.extra_sortBySize";
    private static final String NON_DOWNLOADMANAGER_DOWNLOAD = "non-dwnldmngr-download-dont-retry2download";
    public static final int PAUSED_BY_APP = 5;
    public static final int PAUSED_QUEUED_FOR_WIFI = 3;
    public static final int PAUSED_UNKNOWN = 4;
    public static final int PAUSED_WAITING_FOR_NETWORK = 2;
    public static final int PAUSED_WAITING_TO_RETRY = 1;
    private static final String SBROWSER_PACKAGE_NAME = "com.sec.android.app.sbrowser";
    public static final String SEM_COLUMN_DD_CONTENT_SIZE = "dd_contentSize";
    public static final String SEM_COLUMN_DD_FILE_DESCRIPTION = "dd_description";
    public static final String SEM_COLUMN_DD_FILE_NAME = "dd_fileName";
    public static final String SEM_COLUMN_DD_PRIMARY_MIMETYPE = "dd_primaryMimeType";
    public static final String SEM_COLUMN_DD_VENDOR_NAME = "dd_vendor";
    public static final String SEM_COLUMN_DD_VERSION_NUMBER = "dd_majorVersion";
    public static final int SEM_STATUS_OMA_PENDING = 65536;
    public static final int STATUS_FAILED = 16;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_SUCCESSFUL = 8;
    private boolean mAccessFilename;
    private final String mPackageName;
    private final ContentResolver mResolver;
    public static final String COLUMN_MEDIA_TYPE = "media_type";
    public static final String COLUMN_TOTAL_SIZE_BYTES = "total_size";
    public static final String COLUMN_LOCAL_URI = "local_uri";
    public static final String COLUMN_BYTES_DOWNLOADED_SO_FAR = "bytes_so_far";
    public static final String COLUMN_LAST_MODIFIED_TIMESTAMP = "last_modified_timestamp";

    @Deprecated
    public static final String COLUMN_LOCAL_FILENAME = "local_filename";
    public static final String COLUMN_STORAGE_TYPE = "storage_type";
    private static final String[] SEC_COLUMNS = {"_id", "mediaprovider_uri", "title", "description", "uri", COLUMN_MEDIA_TYPE, COLUMN_TOTAL_SIZE_BYTES, COLUMN_LOCAL_URI, "status", "reason", COLUMN_BYTES_DOWNLOADED_SO_FAR, COLUMN_LAST_MODIFIED_TIMESTAMP, "dd_fileName", "dd_vendor", "dd_description", "dd_majorVersion", "dd_primaryMimeType", "dd_contentSize", "state", "downloadmethod", COLUMN_LOCAL_FILENAME, COLUMN_STORAGE_TYPE};
    public static final String[] UNDERLYING_COLUMNS = {"_id", COLUMN_LOCAL_FILENAME, "mediaprovider_uri", "destination", "title", "description", "uri", "status", "hint", COLUMN_MEDIA_TYPE, COLUMN_TOTAL_SIZE_BYTES, COLUMN_LAST_MODIFIED_TIMESTAMP, COLUMN_BYTES_DOWNLOADED_SO_FAR, "allow_write", "notificationpackage", "dd_primaryMimeType", "dd_fileName", "dd_vendor", "dd_description", "dd_contentSize", "dd_objUrl", "dd_majorVersion", "range_start", "range_end", "range_first_end", COLUMN_LOCAL_URI, "reason"};
    private static final String[] SEC_UNDERLYING_COLUMNS = {"_id", "title", "status", "state", Downloads.Impl.COLUMN_TOTAL_BYTES, Downloads.Impl.COLUMN_CURRENT_BYTES, "_data", "description", "mimetype", Downloads.Impl.COLUMN_LAST_MODIFICATION, "visibility", "downloadmethod", "uri", "destination", "dd_primaryMimeType", Downloads.Impl.COLUMN_DD_SECONDARY_MIMETYPE1, Downloads.Impl.COLUMN_DD_SECONDARY_MIMETYPE2, "dd_fileName", "dd_vendor", "dd_description", "dd_contentSize", "dd_objUrl", Downloads.Impl.COLUMN_DD_NOTIFY_URL, "dd_majorVersion", Downloads.Impl.COLUMN_STORAGE_TYPE};
    private static final Set<String> LONG_COLUMNS = new HashSet(Arrays.asList("_id", COLUMN_TOTAL_SIZE_BYTES, "status", "reason", COLUMN_BYTES_DOWNLOADED_SO_FAR, COLUMN_LAST_MODIFIED_TIMESTAMP, "dd_contentSize", "state", "downloadmethod", COLUMN_STORAGE_TYPE));
    private Uri mBaseUri = Downloads.Impl.CONTENT_URI;
    private Uri mSecBaseUri = Downloads.Impl.CONTENT_CDURI;

    public static long getActiveNetworkWarningBytes(Context context) {
        return -1L;
    }

    public static boolean isActiveNetworkExpensive(Context context) {
        return false;
    }

    public static class Request {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Deprecated
        public static final int NETWORK_BLUETOOTH = 4;
        public static final int NETWORK_ETHERNET = 512;
        public static final int NETWORK_MOBILE = 1;
        public static final int NETWORK_WIFI = 2;
        private static final int SCANNABLE_VALUE_NO = 2;
        private static final int SCANNABLE_VALUE_YES = 0;
        public static final int VISIBILITY_HIDDEN = 2;
        public static final int VISIBILITY_VISIBLE = 0;
        public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
        public static final int VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION = 3;
        private CharSequence mDescription;
        private Uri mDestinationUri;
        private String mMimeType;
        private CharSequence mTitle;
        private Uri mUri;
        private List<Pair<String, String>> mRequestHeaders = new ArrayList();
        private int mStorageType = 0;
        private int mAllowedNetworkTypes = -1;
        private boolean mRoamingAllowed = true;
        private boolean mMeteredAllowed = true;
        private int mFlags = 0;
        private boolean mIsVisibleInDownloadsUi = true;
        private boolean mScannable = false;
        private boolean mUseSystemCache = false;
        private int mNotificationVisibility = 0;

        public Request(Uri uri) {
            uri.getClass();
            String scheme = uri.getScheme();
            if (scheme == null || (!scheme.equals(IntentFilter.SCHEME_HTTP) && !scheme.equals(IntentFilter.SCHEME_HTTPS))) {
                throw new IllegalArgumentException("Can only download HTTP/HTTPS URIs: " + uri);
            }
            this.mUri = uri;
        }

        Request(String str) {
            this.mUri = Uri.parse(str);
        }

        public Request setDestinationUri(Uri uri) {
            this.mDestinationUri = uri;
            return this;
        }

        public Request setDestinationToSystemCache() {
            this.mUseSystemCache = true;
            return this;
        }

        public Request setDestinationInExternalFilesDir(Context context, String str, String str2) {
            File externalFilesDir = context.getExternalFilesDir(str);
            if (externalFilesDir == null) {
                throw new IllegalStateException("Failed to get external storage files directory");
            }
            if (externalFilesDir.exists()) {
                if (!externalFilesDir.isDirectory()) {
                    throw new IllegalStateException(externalFilesDir.getAbsolutePath() + " already exists and is not a directory");
                }
            } else if (!externalFilesDir.mkdirs()) {
                throw new IllegalStateException("Unable to create directory: " + externalFilesDir.getAbsolutePath());
            }
            setDestinationFromBase(externalFilesDir, str2);
            return this;
        }

        public Request setDestinationInExternalPublicDir(String str, String str2) {
            File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(str);
            if (externalStoragePublicDirectory == null) {
                throw new IllegalStateException("Failed to get external storage public directory");
            }
            Application initialApplication = AppGlobals.getInitialApplication();
            if (initialApplication.getApplicationInfo().targetSdkVersion >= 29 || !Environment.isExternalStorageLegacy()) {
                try {
                    ContentProviderClient contentProviderClientAcquireContentProviderClient = initialApplication.getContentResolver().acquireContentProviderClient("downloads");
                    try {
                        if (contentProviderClientAcquireContentProviderClient == null) {
                            Log.i("DownloadManager", "client is null maybe due to download provider disabled");
                            if (contentProviderClientAcquireContentProviderClient != null) {
                                contentProviderClientAcquireContentProviderClient.close();
                            }
                            return null;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString(Downloads.DIR_TYPE, str);
                        contentProviderClientAcquireContentProviderClient.call(Downloads.CALL_CREATE_EXTERNAL_PUBLIC_DIR, null, bundle);
                        if (contentProviderClientAcquireContentProviderClient != null) {
                            contentProviderClientAcquireContentProviderClient.close();
                        }
                    } finally {
                    }
                } catch (RemoteException e) {
                    throw new IllegalStateException("Unable to create directory: " + externalStoragePublicDirectory.getAbsolutePath(), e);
                }
            } else if (externalStoragePublicDirectory.exists()) {
                if (!externalStoragePublicDirectory.isDirectory()) {
                    throw new IllegalStateException(externalStoragePublicDirectory.getAbsolutePath() + " already exists and is not a directory");
                }
            } else if (!externalStoragePublicDirectory.mkdirs()) {
                throw new IllegalStateException("Unable to create directory: " + externalStoragePublicDirectory.getAbsolutePath());
            }
            setDestinationFromBase(externalStoragePublicDirectory, str2);
            return this;
        }

        private void setDestinationFromBase(File file, String str) {
            if (str == null) {
                throw new NullPointerException("subPath cannot be null");
            }
            this.mDestinationUri = Uri.withAppendedPath(Uri.fromFile(file), str);
        }

        @Deprecated
        public void allowScanningByMediaScanner() {
            this.mScannable = true;
        }

        public Request addRequestHeader(String str, String str2) {
            if (str == null) {
                throw new NullPointerException("header cannot be null");
            }
            if (str.contains(":")) {
                throw new IllegalArgumentException("header may not contain ':'");
            }
            if (str2 == null) {
                str2 = "";
            }
            this.mRequestHeaders.add(Pair.create(str, str2));
            return this;
        }

        public Request setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        public Request setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Request setMimeType(String str) {
            this.mMimeType = str;
            return this;
        }

        public Request setStorageType(int i) {
            this.mStorageType = i;
            return this;
        }

        @Deprecated
        public Request setShowRunningNotification(boolean z) {
            if (z) {
                return setNotificationVisibility(0);
            }
            return setNotificationVisibility(2);
        }

        public Request setNotificationVisibility(int i) {
            this.mNotificationVisibility = i;
            return this;
        }

        public Request setAllowedNetworkTypes(int i) {
            this.mAllowedNetworkTypes = i;
            return this;
        }

        public Request setAllowedOverRoaming(boolean z) {
            this.mRoamingAllowed = z;
            return this;
        }

        public Request setAllowedOverMetered(boolean z) {
            this.mMeteredAllowed = z;
            return this;
        }

        public Request setRequiresCharging(boolean z) {
            if (z) {
                this.mFlags |= 1;
                return this;
            }
            this.mFlags &= -2;
            return this;
        }

        public Request setRequiresDeviceIdle(boolean z) {
            if (z) {
                this.mFlags |= 2;
                return this;
            }
            this.mFlags &= -3;
            return this;
        }

        @Deprecated
        public Request setVisibleInDownloadsUi(boolean z) {
            this.mIsVisibleInDownloadsUi = z;
            return this;
        }

        ContentValues toContentValues(String str) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("uri", this.mUri.toString());
            contentValues.put(Downloads.Impl.COLUMN_IS_PUBLIC_API, (Boolean) true);
            contentValues.put("notificationpackage", str);
            if (this.mDestinationUri != null) {
                contentValues.put("destination", (Integer) 4);
                contentValues.put("hint", this.mDestinationUri.toString());
            } else {
                contentValues.put("destination", (Integer) 2);
            }
            contentValues.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(this.mScannable ? 0 : 2));
            if (!this.mRequestHeaders.isEmpty()) {
                encodeHttpHeaders(contentValues);
            }
            putIfNonNull(contentValues, "title", this.mTitle);
            putIfNonNull(contentValues, "description", this.mDescription);
            putIfNonNull(contentValues, "mimetype", this.mMimeType);
            contentValues.put("visibility", Integer.valueOf(this.mNotificationVisibility));
            contentValues.put("allowed_network_types", Integer.valueOf(this.mAllowedNetworkTypes));
            contentValues.put(Downloads.Impl.COLUMN_ALLOW_ROAMING, Boolean.valueOf(this.mRoamingAllowed));
            contentValues.put("allow_metered", Boolean.valueOf(this.mMeteredAllowed));
            contentValues.put("flags", Integer.valueOf(this.mFlags));
            contentValues.put(Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI, Boolean.valueOf(this.mIsVisibleInDownloadsUi));
            return contentValues;
        }

        ContentValues sectoContentValues(String str) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("uri", this.mUri.toString());
            contentValues.put(Downloads.Impl.COLUMN_IS_PUBLIC_API, (Boolean) true);
            contentValues.put("notificationpackage", str);
            Uri uri = this.mDestinationUri;
            if (uri != null) {
                contentValues.put("hint", uri.toString());
            }
            contentValues.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(this.mScannable ? 0 : 2));
            if (!this.mRequestHeaders.isEmpty()) {
                encodeHttpHeaders(contentValues);
            }
            putIfNonNull(contentValues, "title", this.mTitle);
            putIfNonNull(contentValues, "description", this.mDescription);
            putIfNonNull(contentValues, "mimetype", this.mMimeType);
            contentValues.put("visibility", Integer.valueOf(this.mNotificationVisibility));
            contentValues.put("allowed_network_types", Integer.valueOf(this.mAllowedNetworkTypes));
            contentValues.put(Downloads.Impl.COLUMN_ALLOW_ROAMING, Boolean.valueOf(this.mRoamingAllowed));
            contentValues.put("allow_metered", Boolean.valueOf(this.mMeteredAllowed));
            contentValues.put(Downloads.Impl.COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI, Boolean.valueOf(this.mIsVisibleInDownloadsUi));
            contentValues.put(Downloads.Impl.COLUMN_STORAGE_TYPE, Integer.valueOf(this.mStorageType));
            return contentValues;
        }

        private void encodeHttpHeaders(ContentValues contentValues) {
            int i = 0;
            for (Pair<String, String> pair : this.mRequestHeaders) {
                contentValues.put(Downloads.Impl.RequestHeaders.INSERT_KEY_PREFIX + i, pair.first + ": " + pair.second);
                i++;
            }
        }

        private void putIfNonNull(ContentValues contentValues, String str, Object obj) {
            if (obj != null) {
                contentValues.put(str, obj.toString());
            }
        }
    }

    public static class SecQuery {
        public static final int ORDER_ASCENDING = 1;
        public static final int ORDER_DESCENDING = 2;
        private long[] mIds = null;
        private Integer mStatusFlags = null;
        private String mFilterString = null;
        private String mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
        private int mOrderDirection = 2;
        private boolean mOnlyIncludeVisibleInDownloadsUi = false;

        public SecQuery setFilterById(long... jArr) {
            this.mIds = jArr;
            return this;
        }

        public SecQuery setFilterByString(String str) {
            this.mFilterString = str;
            return this;
        }

        public SecQuery setFilterByStatus(int i) {
            this.mStatusFlags = Integer.valueOf(i);
            return this;
        }

        public SecQuery orderBy(String str, int i) {
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException("Invalid direction: " + i);
            }
            if (str.equals(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (str.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else if (str.equals("title")) {
                this.mOrderByColumn = "title COLLATE NOCASE";
            } else {
                throw new IllegalArgumentException("Cannot order by " + str);
            }
            this.mOrderDirection = i;
            return this;
        }

        Cursor runQuery(ContentResolver contentResolver, String[] strArr, Uri uri) {
            ArrayList arrayList = new ArrayList();
            long[] jArr = this.mIds;
            int length = jArr == null ? 0 : jArr.length;
            if (this.mFilterString != null) {
                length++;
            }
            String[] strArr2 = new String[length];
            if (length > 0) {
                if (jArr != null) {
                    arrayList.add(DownloadManager.getWhereClauseForIds(jArr));
                    DownloadManager.getWhereArgsForIds(this.mIds, strArr2);
                }
                if (this.mFilterString != null) {
                    arrayList.add("title LIKE ?");
                    strArr2[length - 1] = "%" + this.mFilterString + "%";
                }
            }
            return contentResolver.query(uri, strArr, joinStrings(" AND ", arrayList), strArr2, this.mOrderByColumn + " " + (this.mOrderDirection == 1 ? "ASC" : "DESC"));
        }

        private String joinStrings(String str, Iterable<String> iterable) {
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            for (String str2 : iterable) {
                if (!z) {
                    sb.append(str);
                }
                sb.append(str2);
                z = false;
            }
            return sb.toString();
        }

        private String statusClause(String str, int i) {
            return "status" + str + "'" + i + "'";
        }
    }

    public static class Query {
        public static final int ORDER_ASCENDING = 1;
        public static final int ORDER_DESCENDING = 2;
        private long[] mIds = null;
        private Integer mStatusFlags = null;
        private String mFilterString = null;
        private String mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
        private int mOrderDirection = 2;
        private boolean mOnlyIncludeVisibleInDownloadsUi = false;

        public Query setFilterById(long... jArr) {
            this.mIds = jArr;
            return this;
        }

        public Query setFilterByString(String str) {
            this.mFilterString = str;
            return this;
        }

        public Query setFilterByStatus(int i) {
            this.mStatusFlags = Integer.valueOf(i);
            return this;
        }

        public Query semSetOnlyIncludeVisibleInDownloadsUi(boolean z) {
            return setOnlyIncludeVisibleInDownloadsUi(z);
        }

        public Query setOnlyIncludeVisibleInDownloadsUi(boolean z) {
            this.mOnlyIncludeVisibleInDownloadsUi = z;
            return this;
        }

        public Query orderBy(String str, int i) {
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException("Invalid direction: " + i);
            }
            if (str.equals(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (str.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else if (str.equals("title")) {
                this.mOrderByColumn = "title COLLATE NOCASE";
            } else {
                throw new IllegalArgumentException("Cannot order by " + str);
            }
            this.mOrderDirection = i;
            return this;
        }

        public Query orderByLocalized(String str, int i) {
            if (i != 1 && i != 2) {
                throw new IllegalArgumentException("Invalid direction: " + i);
            }
            if (str.equals(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_LAST_MODIFICATION;
            } else if (str.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                this.mOrderByColumn = Downloads.Impl.COLUMN_TOTAL_BYTES;
            } else if (str.equals("title")) {
                this.mOrderByColumn = "title COLLATE LOCALIZED";
            } else {
                throw new IllegalArgumentException("Cannot order Localized " + str);
            }
            this.mOrderDirection = i;
            return this;
        }

        Cursor runQuery(ContentResolver contentResolver, String[] strArr, Uri uri) {
            String[] whereArgsForIds;
            ArrayList arrayList = new ArrayList();
            long[] jArr = this.mIds;
            if (jArr != null) {
                arrayList.add(DownloadManager.getWhereClauseForIds(jArr));
                whereArgsForIds = DownloadManager.getWhereArgsForIds(this.mIds);
            } else {
                whereArgsForIds = null;
            }
            String[] strArr2 = whereArgsForIds;
            if (this.mStatusFlags != null) {
                ArrayList arrayList2 = new ArrayList();
                if ((this.mStatusFlags.intValue() & 1) != 0) {
                    arrayList2.add(statusClause("=", 190));
                }
                if ((this.mStatusFlags.intValue() & 2) != 0) {
                    arrayList2.add(statusClause("=", 192));
                }
                if ((this.mStatusFlags.intValue() & 4) != 0) {
                    arrayList2.add(statusClause("=", 193));
                    arrayList2.add(statusClause("=", 194));
                    arrayList2.add(statusClause("=", 195));
                    arrayList2.add(statusClause("=", 196));
                }
                if ((this.mStatusFlags.intValue() & 8) != 0) {
                    arrayList2.add(statusClause("=", 200));
                }
                if ((this.mStatusFlags.intValue() & 16) != 0) {
                    arrayList2.add(NavigationBarInflaterView.KEY_CODE_START + statusClause(">=", 400) + " AND " + statusClause("<", 600) + NavigationBarInflaterView.KEY_CODE_END);
                }
                arrayList.add(joinStrings(" OR ", arrayList2));
            }
            if (this.mOnlyIncludeVisibleInDownloadsUi) {
                arrayList.add("is_visible_in_downloads_ui != '0'");
            }
            arrayList.add("deleted != '1'");
            return contentResolver.query(uri, strArr, joinStrings(" AND ", arrayList), strArr2, this.mOrderByColumn + " " + (this.mOrderDirection == 1 ? "ASC" : "DESC"));
        }

        private String joinStrings(String str, Iterable<String> iterable) {
            StringBuilder sb = new StringBuilder();
            boolean z = true;
            for (String str2 : iterable) {
                if (!z) {
                    sb.append(str);
                }
                sb.append(str2);
                z = false;
            }
            return sb.toString();
        }

        private String statusClause(String str, int i) {
            return "status" + str + "'" + i + "'";
        }
    }

    public DownloadManager(Context context) {
        this.mResolver = context.getContentResolver();
        this.mPackageName = context.getPackageName();
        this.mAccessFilename = context.getApplicationInfo().targetSdkVersion < 24;
    }

    public void setAccessAllDownloads(boolean z) {
        if (z) {
            this.mBaseUri = Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI;
        } else {
            this.mBaseUri = Downloads.Impl.CONTENT_URI;
        }
    }

    public void setAccessFilename(boolean z) {
        this.mAccessFilename = z;
    }

    public void setSecDownloads(boolean z) {
        if (z) {
            this.mBaseUri = Downloads.Impl.CONTENT_CDURI;
        }
    }

    @SystemApi
    public void onMediaStoreDownloadsDeleted(LongSparseArray<String> longSparseArray) {
        try {
            ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient = this.mResolver.acquireUnstableContentProviderClient(this.mBaseUri);
            try {
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    Log.i("DownloadManager", "client is null maybe due to download provider disabled");
                    if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                        return;
                    }
                } else {
                    Bundle bundle = new Bundle();
                    long[] jArr = new long[longSparseArray.size()];
                    String[] strArr = new String[longSparseArray.size()];
                    for (int size = longSparseArray.size() - 1; size >= 0; size--) {
                        jArr[size] = longSparseArray.keyAt(size);
                        strArr[size] = longSparseArray.valueAt(size);
                    }
                    bundle.putLongArray(Downloads.EXTRA_IDS, jArr);
                    bundle.putStringArray("mime_types", strArr);
                    contentProviderClientAcquireUnstableContentProviderClient.call(Downloads.CALL_MEDIASTORE_DOWNLOADS_DELETED, null, bundle);
                    if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                        return;
                    }
                }
                contentProviderClientAcquireUnstableContentProviderClient.close();
            } finally {
            }
        } catch (RemoteException unused) {
        }
    }

    public long enqueue(Request request) {
        Uri uriInsert = this.mResolver.insert(Downloads.Impl.CONTENT_URI, request.toContentValues(this.mPackageName));
        if (uriInsert != null) {
            return Long.parseLong(uriInsert.getLastPathSegment());
        }
        return -1L;
    }

    public int markRowDeleted(long... jArr) {
        if (jArr == null || jArr.length == 0) {
            throw new IllegalArgumentException("input param 'ids' can't be null");
        }
        return this.mResolver.delete(this.mBaseUri, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr));
    }

    public int remove(long... jArr) {
        return markRowDeleted(jArr);
    }

    public int secmarkRowDeleted(long... jArr) {
        if (jArr == null || jArr.length == 0) {
            throw new IllegalArgumentException("input param 'ids' can't be null");
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("deleted", (Integer) 1);
        return this.mResolver.update(ContentUris.withAppendedId(Downloads.Impl.CONTENT_CDURI, jArr[0]), contentValues, null, null);
    }

    private String joinStrings(String str, Iterable<String> iterable) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str2 : iterable) {
            if (!z) {
                sb.append(str);
            }
            sb.append(str2);
            z = false;
        }
        return sb.toString();
    }

    public int secremove(long... jArr) {
        if (jArr == null || jArr.length == 0) {
            throw new IllegalArgumentException("input param 'ids' can't be null");
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(getWhereClauseForIds(jArr));
        String[] whereArgsForIds = getWhereArgsForIds(jArr);
        return this.mResolver.delete(this.mSecBaseUri, joinStrings(" AND ", arrayList), whereArgsForIds);
    }

    public Cursor query(Query query) {
        return query(query, UNDERLYING_COLUMNS);
    }

    public Cursor query(Query query, String[] strArr) {
        Cursor cursorRunQuery = query.runQuery(this.mResolver, strArr, this.mBaseUri);
        if (cursorRunQuery == null) {
            return null;
        }
        return new CursorTranslator(cursorRunQuery, this.mBaseUri, this.mAccessFilename);
    }

    public Cursor secquery(SecQuery secQuery) {
        Cursor cursorRunQuery = secQuery.runQuery(this.mResolver, SEC_UNDERLYING_COLUMNS, this.mSecBaseUri);
        if (cursorRunQuery == null) {
            return null;
        }
        return new SecCursorTranslator(cursorRunQuery, this.mSecBaseUri);
    }

    public ParcelFileDescriptor openDownloadedFile(long j) throws FileNotFoundException {
        return this.mResolver.openFileDescriptor(getDownloadUri(j), "r");
    }

    public Uri getUriForDownloadedFile(long j) throws Throwable {
        Cursor cursor = null;
        try {
            Cursor cursorQuery = query(new Query().setFilterById(j));
            if (cursorQuery == null) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            }
            try {
                if (!cursorQuery.moveToFirst() || 8 != cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("status"))) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                Uri uriWithAppendedId = ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, j);
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return uriWithAppendedId;
            } catch (Throwable th) {
                th = th;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public String getMimeTypeForDownloadedFile(long j) throws Throwable {
        Cursor cursor = null;
        try {
            Cursor cursorQuery = query(new Query().setFilterById(j));
            if (cursorQuery == null) {
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return null;
            }
            try {
                if (!cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow(COLUMN_MEDIA_TYPE));
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return string;
            } catch (Throwable th) {
                th = th;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean restartDownload(long... jArr) {
        Cursor cursorQuery = query(new Query().setFilterById(jArr));
        try {
            cursorQuery.moveToFirst();
            while (!cursorQuery.isAfterLast()) {
                int i = cursorQuery.getInt(cursorQuery.getColumnIndex("status"));
                if (i != 8 && i != 16) {
                    return false;
                }
                cursorQuery.moveToNext();
            }
            cursorQuery.close();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Downloads.Impl.COLUMN_CURRENT_BYTES, (Integer) 0);
            contentValues.put(Downloads.Impl.COLUMN_TOTAL_BYTES, (Integer) (-1));
            contentValues.putNull("_data");
            contentValues.put("status", (Integer) 190);
            contentValues.put(Downloads.Impl.COLUMN_FAILED_CONNECTIONS, (Integer) 0);
            contentValues.put("state", (Integer) 0);
            contentValues.put("range_start", (Long) 0L);
            contentValues.put("range_end", (Long) 0L);
            contentValues.put("range_first_end", (Long) 0L);
            this.mResolver.update(this.mBaseUri, contentValues, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr));
            return true;
        } finally {
            cursorQuery.close();
        }
    }

    public void pauseDownload(long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Downloads.Impl.COLUMN_CONTROL, (Integer) 1);
        this.mResolver.update(ContentUris.withAppendedId(this.mBaseUri, j), contentValues, null, null);
    }

    public void resumeDownload(long j) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Downloads.Impl.COLUMN_CONTROL, (Integer) 0);
        this.mResolver.update(ContentUris.withAppendedId(this.mBaseUri, j), contentValues, null, null);
    }

    public boolean secrestartDownload(long... jArr) {
        Cursor cursorSecquery = secquery(new SecQuery().setFilterById(jArr));
        if (cursorSecquery == null) {
            return false;
        }
        try {
            cursorSecquery.moveToFirst();
            while (!cursorSecquery.isAfterLast()) {
                int i = cursorSecquery.getInt(cursorSecquery.getColumnIndex("status"));
                if (i != 8 && i != 16) {
                    return false;
                }
                cursorSecquery.moveToNext();
            }
            cursorSecquery.close();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Downloads.Impl.COLUMN_CURRENT_BYTES, (Integer) 0);
            contentValues.put(Downloads.Impl.COLUMN_TOTAL_BYTES, (Integer) (-1));
            contentValues.putNull("_data");
            contentValues.put("state", (Integer) 0);
            contentValues.put("visibility", (Integer) 1);
            contentValues.put("status", (Integer) 190);
            this.mResolver.update(this.mSecBaseUri, contentValues, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr));
            return true;
        } finally {
            cursorSecquery.close();
        }
    }

    public void forceDownload(long... jArr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", (Integer) 190);
        contentValues.put(Downloads.Impl.COLUMN_CONTROL, (Integer) 0);
        contentValues.put(Downloads.Impl.COLUMN_BYPASS_RECOMMENDED_SIZE_LIMIT, (Integer) 1);
        this.mResolver.update(this.mBaseUri, contentValues, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr));
    }

    public static Long getMaxBytesOverMobile(Context context) {
        try {
            return Long.valueOf(Settings.Global.getLong(context.getContentResolver(), Settings.Global.DOWNLOAD_MAX_BYTES_OVER_MOBILE));
        } catch (Settings.SettingNotFoundException unused) {
            return null;
        }
    }

    public boolean rename(Context context, long j, String str) {
        if (!FileUtils.isValidFatFilename(str)) {
            throw new SecurityException(str + " is not a valid filename");
        }
        Cursor cursorQuery = query(new Query().setFilterById(j));
        try {
            if (cursorQuery == null) {
                throw new IllegalStateException("Missing cursor for download id=" + j);
            }
            if (cursorQuery.moveToFirst()) {
                if (cursorQuery.getInt(cursorQuery.getColumnIndexOrThrow("status")) != 8) {
                    throw new IllegalStateException("Download is not completed yet: " + DatabaseUtils.dumpCurrentRowToString(cursorQuery));
                }
                String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow(COLUMN_LOCAL_FILENAME));
                if (string == null) {
                    throw new IllegalStateException("Download doesn't have a valid file path: " + DatabaseUtils.dumpCurrentRowToString(cursorQuery));
                }
                if (!new File(string).exists()) {
                    throw new IllegalStateException("Downloaded file doesn't exist anymore: " + DatabaseUtils.dumpCurrentRowToString(cursorQuery));
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                File file = new File(string);
                File file2 = new File(file.getParentFile(), str);
                if (file2.exists()) {
                    throw new IllegalStateException("File already exists: " + file2);
                }
                if (!file.renameTo(file2)) {
                    throw new IllegalStateException("Failed to rename file from " + file + " to " + file2);
                }
                MediaStore.scanFile(this.mResolver, file);
                MediaStore.scanFile(this.mResolver, file2);
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", str);
                contentValues.put("_data", file2.toString());
                contentValues.putNull("mediaprovider_uri");
                long[] jArr = {j};
                return this.mResolver.update(this.mBaseUri, contentValues, getWhereClauseForIds(jArr), getWhereArgsForIds(jArr)) == 1;
            }
            throw new IllegalStateException("Missing download id=" + j);
        } catch (Throwable th) {
            if (cursorQuery != null) {
                try {
                    cursorQuery.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static Long getRecommendedMaxBytesOverMobile(Context context) {
        try {
            return Long.valueOf(Settings.Global.getLong(context.getContentResolver(), Settings.Global.DOWNLOAD_RECOMMENDED_MAX_BYTES_OVER_MOBILE));
        } catch (Settings.SettingNotFoundException unused) {
            return null;
        }
    }

    @Deprecated
    public long addCompletedDownload(String str, String str2, boolean z, String str3, String str4, long j, boolean z2) {
        return addCompletedDownload(str, str2, z, str3, str4, j, z2, false, null, null);
    }

    @Deprecated
    public long addCompletedDownload(String str, String str2, boolean z, String str3, String str4, long j, boolean z2, Uri uri, Uri uri2) {
        return addCompletedDownload(str, str2, z, str3, str4, j, z2, false, uri, uri2);
    }

    @Deprecated
    public long addCompletedDownload(String str, String str2, boolean z, String str3, String str4, long j, boolean z2, boolean z3) {
        return addCompletedDownload(str, str2, z, str3, str4, j, z2, z3, null, null);
    }

    @Deprecated
    public long addCompletedDownload(String str, String str2, boolean z, String str3, String str4, long j, boolean z2, boolean z3, Uri uri, Uri uri2) {
        Request request;
        validateArgumentIsNonEmpty("title", str);
        validateArgumentIsNonEmpty("description", str2);
        validateArgumentIsNonEmpty("path", str4);
        validateArgumentIsNonEmpty("mimeType", str3);
        if (j < 0) {
            throw new IllegalArgumentException(" invalid value for param: totalBytes");
        }
        if (uri != null) {
            request = new Request(uri);
        } else {
            request = new Request(NON_DOWNLOADMANAGER_DOWNLOAD);
        }
        request.setTitle(str).setDescription(str2).setMimeType(str3);
        if (uri2 != null) {
            request.addRequestHeader("Referer", uri2.toString());
        }
        ContentValues contentValues = request.toContentValues(this.mPackageName.contains(SBROWSER_PACKAGE_NAME) ? this.mPackageName : null);
        contentValues.put("destination", (Integer) 6);
        contentValues.put("_data", str4);
        contentValues.put("mimetype", resolveMimeType(new File(str4)));
        contentValues.put("status", (Integer) 200);
        contentValues.put("state", (Integer) 10);
        contentValues.put(Downloads.Impl.COLUMN_TOTAL_BYTES, Long.valueOf(j));
        contentValues.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(z ? 0 : 2));
        contentValues.put("visibility", Integer.valueOf(z2 ? 3 : 2));
        contentValues.put("allow_write", Integer.valueOf(z3 ? 1 : 0));
        Uri uriInsert = this.mResolver.insert(Downloads.Impl.CONTENT_URI, contentValues);
        if (uriInsert == null) {
            return -1L;
        }
        return Long.parseLong(uriInsert.getLastPathSegment());
    }

    private static String resolveMimeType(File file) {
        String mimeTypeFromExtension;
        String strExtractFileExtension = extractFileExtension(file.getPath());
        return (strExtractFileExtension == null || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(strExtractFileExtension.toLowerCase(Locale.ROOT))) == null) ? "application/octet-stream" : mimeTypeFromExtension;
    }

    private static String extractDisplayName(String str) {
        if (str == null) {
            return null;
        }
        if (str.indexOf(47) == -1) {
            return str;
        }
        if (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        return str.substring(str.lastIndexOf(47) + 1);
    }

    private static String extractFileExtension(String str) {
        String strExtractDisplayName;
        int iLastIndexOf;
        if (str == null || (iLastIndexOf = (strExtractDisplayName = extractDisplayName(str)).lastIndexOf(46)) == -1) {
            return null;
        }
        return strExtractDisplayName.substring(iLastIndexOf + 1);
    }

    public long secAddCompletedDownload(String str, String str2, boolean z, String str3, String str4, long j, boolean z2) {
        validateArgumentIsNonEmpty("title", str);
        validateArgumentIsNonEmpty("description", str2);
        validateArgumentIsNonEmpty("path", str4);
        validateArgumentIsNonEmpty("mimeType", str3);
        if (j < 0) {
            throw new IllegalArgumentException(" invalid value for param: totalBytes");
        }
        ContentValues contentValuesSectoContentValues = new Request(NON_DOWNLOADMANAGER_DOWNLOAD).setTitle(str).setDescription(str2).setMimeType(str3).sectoContentValues(null);
        contentValuesSectoContentValues.put("destination", (Integer) 0);
        contentValuesSectoContentValues.put("_data", str4);
        contentValuesSectoContentValues.put("status", (Integer) 200);
        contentValuesSectoContentValues.put("state", (Integer) 10);
        contentValuesSectoContentValues.put(Downloads.Impl.COLUMN_STORAGE_TYPE, (Integer) 1);
        contentValuesSectoContentValues.put(Downloads.Impl.COLUMN_TOTAL_BYTES, Long.valueOf(j));
        contentValuesSectoContentValues.put(Downloads.Impl.COLUMN_MEDIA_SCANNED, Integer.valueOf(z ? 0 : 2));
        contentValuesSectoContentValues.put("visibility", Integer.valueOf(z2 ? 1 : 2));
        Uri uriInsert = this.mResolver.insert(Downloads.Impl.CONTENT_CDURI, contentValuesSectoContentValues);
        if (uriInsert == null) {
            return -1L;
        }
        return Long.parseLong(uriInsert.getLastPathSegment());
    }

    private static void validateArgumentIsNonEmpty(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException(str + " can't be null");
        }
    }

    public Uri getDownloadUri(long j) {
        return ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, j);
    }

    static String getWhereClauseForIds(long[] jArr) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.KEY_CODE_START);
        for (int i = 0; i < jArr.length; i++) {
            if (i > 0) {
                sb.append("OR ");
            }
            sb.append("_id = ? ");
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    static String[] getWhereArgsForIds(long[] jArr) {
        return getWhereArgsForIds(jArr, new String[jArr.length]);
    }

    static String[] getWhereArgsForIds(long[] jArr, String[] strArr) {
        for (int i = 0; i < jArr.length; i++) {
            strArr[i] = Long.toString(jArr[i]);
        }
        return strArr;
    }

    private static class CursorTranslator extends CursorWrapper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final boolean mAccessFilename;
        private final Uri mBaseUri;

        private long getErrorCode(int i) {
            if ((400 <= i && i < 488) || (500 <= i && i < 700)) {
                return i;
            }
            if (i == 198) {
                return 1006L;
            }
            if (i == 199) {
                return 1007L;
            }
            if (i == 488) {
                return 1009L;
            }
            if (i == 489) {
                return 1008L;
            }
            if (i == 497) {
                return 1005L;
            }
            switch (i) {
                case 492:
                    return 1001L;
                case 493:
                case 494:
                    return 1002L;
                case 495:
                    return 1004L;
                default:
                    return 1000L;
            }
        }

        private long getPausedReason(int i) {
            switch (i) {
                case 193:
                    return 5L;
                case 194:
                    return 1L;
                case 195:
                    return 2L;
                case 196:
                    return 3L;
                default:
                    return 4L;
            }
        }

        private int translateStatus(int i) {
            switch (i) {
                case 181:
                case 183:
                case 184:
                case 185:
                case 186:
                case 188:
                case 201:
                    return 2;
                case 182:
                    return 65536;
                case 187:
                case 189:
                case 191:
                case 197:
                case 198:
                case 199:
                default:
                    return 16;
                case 190:
                    return 1;
                case 192:
                    return 2;
                case 193:
                case 194:
                case 195:
                case 196:
                    return 4;
                case 200:
                    return 8;
            }
        }

        public CursorTranslator(Cursor cursor, Uri uri, boolean z) {
            super(cursor);
            this.mBaseUri = uri;
            this.mAccessFilename = z;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getInt(int i) {
            return (int) getLong(i);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public long getLong(int i) {
            if (getColumnName(i).equals("reason")) {
                return getReason(super.getInt(getColumnIndex("status")));
            }
            if (getColumnName(i).equals("status")) {
                return translateStatus(super.getInt(getColumnIndex("status")));
            }
            return super.getLong(i);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public String getString(int i) {
            String columnName = getColumnName(i);
            columnName.hashCode();
            if (columnName.equals(DownloadManager.COLUMN_LOCAL_URI)) {
                return getLocalUri();
            }
            if (columnName.equals(DownloadManager.COLUMN_LOCAL_FILENAME) && !this.mAccessFilename) {
                throw new SecurityException("COLUMN_LOCAL_FILENAME is deprecated; use ContentResolver.openFileDescriptor() instead");
            }
            return super.getString(i);
        }

        private String getLocalUri() {
            long j = getLong(getColumnIndex("destination"));
            if (j == 4 || j == 0 || j == 6) {
                String string = super.getString(getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                if (string == null) {
                    return null;
                }
                return Uri.fromFile(new File(string)).toString();
            }
            return ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, getLong(getColumnIndex("_id"))).toString();
        }

        private long getReason(int i) {
            int iTranslateStatus = translateStatus(i);
            if (iTranslateStatus == 4) {
                return getPausedReason(i);
            }
            if (iTranslateStatus != 16) {
                return 0L;
            }
            return getErrorCode(i);
        }
    }

    private static class SecCursorTranslator extends CursorWrapper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Uri mBaseUri;

        private long getErrorCode(int i) {
            if ((400 <= i && i < 488) || (500 <= i && i < 700)) {
                return i;
            }
            if (i == 198) {
                return 1006L;
            }
            if (i == 199) {
                return 1007L;
            }
            if (i == 488) {
                return 1009L;
            }
            if (i != 489) {
                return i;
            }
            return 1008L;
        }

        private long getPausedReason(int i) {
            switch (i) {
                case 194:
                    return 1L;
                case 195:
                    return 2L;
                case 196:
                    return 3L;
                default:
                    return 4L;
            }
        }

        public int translateStatus(int i) {
            switch (i) {
                case 181:
                case 183:
                case 184:
                case 185:
                case 186:
                case 188:
                case 201:
                    return 2;
                case 182:
                    return 65536;
                case 187:
                case 189:
                case 191:
                case 197:
                case 198:
                case 199:
                default:
                    return 16;
                case 190:
                    return 1;
                case 192:
                    return 2;
                case 193:
                case 194:
                case 195:
                case 196:
                    return 4;
                case 200:
                case 202:
                case 203:
                    return 8;
            }
        }

        public SecCursorTranslator(Cursor cursor, Uri uri) {
            super(cursor);
            this.mBaseUri = uri;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getColumnIndex(String str) {
            return Arrays.asList(DownloadManager.SEC_COLUMNS).indexOf(str);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getColumnIndexOrThrow(String str) throws IllegalArgumentException {
            int columnIndex = getColumnIndex(str);
            if (columnIndex != -1) {
                return columnIndex;
            }
            throw new IllegalArgumentException("No such column: " + str);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public String getColumnName(int i) {
            int length = DownloadManager.SEC_COLUMNS.length;
            if (i < 0 || i >= length) {
                throw new IllegalArgumentException("Invalid column index " + i + ", " + length + " columns exist");
            }
            return DownloadManager.SEC_COLUMNS[i];
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public String[] getColumnNames() {
            String[] strArr = new String[DownloadManager.SEC_COLUMNS.length];
            System.arraycopy(DownloadManager.SEC_COLUMNS, 0, strArr, 0, DownloadManager.SEC_COLUMNS.length);
            return strArr;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getColumnCount() {
            return DownloadManager.SEC_COLUMNS.length;
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public byte[] getBlob(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public double getDouble(int i) {
            return getLong(i);
        }

        private boolean isLongColumn(String str) {
            return DownloadManager.LONG_COLUMNS.contains(str);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public float getFloat(int i) {
            return (float) getDouble(i);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public int getInt(int i) {
            return (int) getLong(i);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public long getLong(int i) {
            return translateLong(getColumnName(i));
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public short getShort(int i) {
            return (short) getLong(i);
        }

        @Override // android.database.CursorWrapper, android.database.Cursor
        public String getString(int i) {
            return translateString(getColumnName(i));
        }

        private String translateString(String str) {
            if (isLongColumn(str)) {
                return Long.toString(translateLong(str));
            }
            if (str.equals("title")) {
                return getUnderlyingString("title");
            }
            if (str.equals("description")) {
                return getUnderlyingString("description");
            }
            if (str.equals("uri")) {
                return getUnderlyingString("uri");
            }
            if (str.equals(DownloadManager.COLUMN_MEDIA_TYPE)) {
                return getUnderlyingString("mimetype");
            }
            if (str.equals(DownloadManager.COLUMN_LOCAL_FILENAME)) {
                return getUnderlyingString("_data");
            }
            if (str.equals("dd_fileName")) {
                return getUnderlyingString("dd_fileName");
            }
            if (str.equals("dd_vendor")) {
                return getUnderlyingString("dd_vendor");
            }
            if (str.equals("dd_majorVersion")) {
                return getUnderlyingString("dd_majorVersion");
            }
            if (str.equals("dd_primaryMimeType")) {
                return getUnderlyingString("dd_primaryMimeType");
            }
            if (str.equals("dd_description")) {
                return getUnderlyingString("dd_description");
            }
            return getLocalUri();
        }

        private String getLocalUri() {
            long underlyingLong = getUnderlyingLong("destination");
            if (underlyingLong == 4) {
                return getUnderlyingString("hint");
            }
            if (underlyingLong == 0) {
                String underlyingString = getUnderlyingString("_data");
                if (underlyingString == null) {
                    return null;
                }
                return Uri.fromFile(new File(underlyingString)).toString();
            }
            if (underlyingLong == 6) {
                String string = getString(getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                if (string == null) {
                    return null;
                }
                return Uri.fromFile(new File(string)).toString();
            }
            return ContentUris.withAppendedId(this.mBaseUri, getUnderlyingLong("_id")).toString();
        }

        private long translateLong(String str) {
            if (!isLongColumn(str)) {
                return Long.valueOf(translateString(str)).longValue();
            }
            if (str.equals("_id")) {
                return getUnderlyingLong("_id");
            }
            if (str.equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) {
                return getUnderlyingLong(Downloads.Impl.COLUMN_TOTAL_BYTES);
            }
            if (str.equals("status")) {
                return translateStatus((int) getUnderlyingLong("status"));
            }
            if (str.equals("reason")) {
                return getReason((int) getUnderlyingLong("status"));
            }
            if (str.equals(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) {
                return getUnderlyingLong(Downloads.Impl.COLUMN_CURRENT_BYTES);
            }
            if (str.equals("dd_contentSize")) {
                return getUnderlyingLong("dd_contentSize");
            }
            if (str.equals("downloadmethod")) {
                return getUnderlyingLong("downloadmethod");
            }
            if (str.equals("state")) {
                return getUnderlyingLong("state");
            }
            if (str.equals(DownloadManager.COLUMN_STORAGE_TYPE)) {
                return getUnderlyingLong(Downloads.Impl.COLUMN_STORAGE_TYPE);
            }
            if (str.equals("range_start")) {
                return getUnderlyingLong("range_start");
            }
            if (str.equals("range_end")) {
                return getUnderlyingLong("range_end");
            }
            if (str.equals("range_first_end")) {
                return getUnderlyingLong("range_first_end");
            }
            return getUnderlyingLong(Downloads.Impl.COLUMN_LAST_MODIFICATION);
        }

        public long getReason(int i) {
            int iTranslateStatus = translateStatus(i);
            if (iTranslateStatus == 4) {
                return getPausedReason(i);
            }
            if (iTranslateStatus != 16) {
                return 0L;
            }
            return getErrorCode(i);
        }

        private long getUnderlyingLong(String str) {
            return super.getLong(super.getColumnIndex(str));
        }

        private String getUnderlyingString(String str) {
            return super.getString(super.getColumnIndex(str));
        }
    }
}
