package android.provider;

import android.annotation.SystemApi;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.MimeTypeFilter;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.ParcelableException;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.Size;
import com.android.internal.util.Preconditions;
import com.samsung.android.app.SemDualAppManager;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class DocumentsContract {

    @SystemApi
    public static final String ACTION_DOCUMENT_ROOT_SETTINGS = "android.provider.action.DOCUMENT_ROOT_SETTINGS";
    public static final String ACTION_DOCUMENT_SETTINGS = "android.provider.action.DOCUMENT_SETTINGS";

    @SystemApi
    public static final String ACTION_MANAGE_DOCUMENT = "android.provider.action.MANAGE_DOCUMENT";

    @SystemApi
    public static final String DOWNLOADS_PROVIDER_AUTHORITY = "downloads";
    public static final String EXTERNAL_STORAGE_PRIMARY_EMULATED_ROOT_ID = "primary";

    @SystemApi
    public static final String EXTERNAL_STORAGE_PROVIDER_AUTHORITY = "com.android.externalstorage.documents";
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_EXCLUDE_SELF = "android.provider.extra.EXCLUDE_SELF";
    public static final String EXTRA_INFO = "info";
    public static final String EXTRA_INITIAL_URI = "android.provider.extra.INITIAL_URI";
    public static final String EXTRA_LOADING = "loading";
    public static final String EXTRA_OPTIONS = "options";
    public static final String EXTRA_ORIENTATION = "android.provider.extra.ORIENTATION";

    @Deprecated
    public static final String EXTRA_PACKAGE_NAME = "android.intent.extra.PACKAGE_NAME";
    public static final String EXTRA_PARENT_URI = "parentUri";
    public static final String EXTRA_PROMPT = "android.provider.extra.PROMPT";
    public static final String EXTRA_RESULT = "result";

    @SystemApi
    public static final String EXTRA_SHOW_ADVANCED = "android.provider.extra.SHOW_ADVANCED";
    public static final String EXTRA_TARGET_URI = "android.content.extra.TARGET_URI";
    public static final String EXTRA_URI = "uri";
    public static final String EXTRA_URI_PERMISSIONS = "uriPermissions";
    public static final String METADATA_EXIF = "android:documentExif";
    public static final String METADATA_TREE_COUNT = "android:metadataTreeCount";
    public static final String METADATA_TREE_SIZE = "android:metadataTreeSize";
    public static final String METADATA_TYPES = "android:documentMetadataTypes";
    public static final String METHOD_COPY_DOCUMENT = "android:copyDocument";
    public static final String METHOD_CREATE_DOCUMENT = "android:createDocument";
    public static final String METHOD_CREATE_WEB_LINK_INTENT = "android:createWebLinkIntent";
    public static final String METHOD_DELETE_DOCUMENT = "android:deleteDocument";
    public static final String METHOD_EJECT_ROOT = "android:ejectRoot";
    public static final String METHOD_FIND_DOCUMENT_PATH = "android:findDocumentPath";
    public static final String METHOD_GET_DOCUMENT_METADATA = "android:getDocumentMetadata";
    public static final String METHOD_IS_CHILD_DOCUMENT = "android:isChildDocument";
    public static final String METHOD_MOVE_DOCUMENT = "android:moveDocument";
    public static final String METHOD_REMOVE_DOCUMENT = "android:removeDocument";
    public static final String METHOD_RENAME_DOCUMENT = "android:renameDocument";
    public static final String PACKAGE_DOCUMENTS_UI = "com.android.documentsui";
    private static final String PARAM_MANAGE = "manage";
    private static final String PARAM_QUERY = "query";
    private static final String PATH_CHILDREN = "children";
    private static final String PATH_DOCUMENT = "document";
    private static final String PATH_RECENT = "recent";
    private static final String PATH_ROOT = "root";
    private static final String PATH_SEARCH = "search";
    private static final String PATH_TREE = "tree";
    public static final String PROVIDER_INTERFACE = "android.content.action.DOCUMENTS_PROVIDER";
    public static final String QUERY_ARG_DISPLAY_NAME = "android:query-arg-display-name";
    public static final String QUERY_ARG_EXCLUDE_MEDIA = "android:query-arg-exclude-media";
    public static final String QUERY_ARG_FILE_SIZE_OVER = "android:query-arg-file-size-over";
    public static final String QUERY_ARG_LAST_MODIFIED_AFTER = "android:query-arg-last-modified-after";
    public static final String QUERY_ARG_MIME_TYPES = "android:query-arg-mime-types";
    private static final String TAG = "DocumentsContract";

    private DocumentsContract() {
    }

    public static final class Document {
        public static final String COLUMN_DISPLAY_NAME = "_display_name";
        public static final String COLUMN_DOCUMENT_ID = "document_id";
        public static final String COLUMN_FLAGS = "flags";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_LAST_MODIFIED = "last_modified";
        public static final String COLUMN_MIME_TYPE = "mime_type";
        public static final String COLUMN_SIZE = "_size";
        public static final String COLUMN_SUMMARY = "summary";
        public static final int FLAG_DIR_BLOCKS_OPEN_DOCUMENT_TREE = 32768;
        public static final int FLAG_DIR_PREFERS_GRID = 16;
        public static final int FLAG_DIR_PREFERS_LAST_MODIFIED = 32;
        public static final int FLAG_DIR_SUPPORTS_CREATE = 8;
        public static final int FLAG_PARTIAL = 8192;
        public static final int FLAG_SUPPORTS_COPY = 128;
        public static final int FLAG_SUPPORTS_DELETE = 4;
        public static final int FLAG_SUPPORTS_METADATA = 16384;
        public static final int FLAG_SUPPORTS_MOVE = 256;
        public static final int FLAG_SUPPORTS_REMOVE = 1024;
        public static final int FLAG_SUPPORTS_RENAME = 64;
        public static final int FLAG_SUPPORTS_SETTINGS = 2048;
        public static final int FLAG_SUPPORTS_THUMBNAIL = 1;
        public static final int FLAG_SUPPORTS_WRITE = 2;
        public static final int FLAG_VIRTUAL_DOCUMENT = 512;
        public static final int FLAG_WEB_LINKABLE = 4096;
        public static final String MIME_TYPE_DIR = "vnd.android.document/directory";

        private Document() {
        }
    }

    public static final class Root {
        public static final String COLUMN_AVAILABLE_BYTES = "available_bytes";
        public static final String COLUMN_CAPACITY_BYTES = "capacity_bytes";
        public static final String COLUMN_DOCUMENT_ID = "document_id";
        public static final String COLUMN_FLAGS = "flags";
        public static final String COLUMN_ICON = "icon";
        public static final String COLUMN_MIME_TYPES = "mime_types";
        public static final String COLUMN_QUERY_ARGS = "query_args";
        public static final String COLUMN_ROOT_ID = "root_id";
        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_TITLE = "title";

        @SystemApi
        public static final int FLAG_ADVANCED = 65536;
        public static final int FLAG_EMPTY = 64;

        @SystemApi
        public static final int FLAG_HAS_SETTINGS = 131072;
        public static final int FLAG_LOCAL_ONLY = 2;

        @SystemApi
        public static final int FLAG_REMOVABLE_SD = 262144;

        @SystemApi
        public static final int FLAG_REMOVABLE_USB = 524288;
        public static final int FLAG_SUPPORTS_CREATE = 1;
        public static final int FLAG_SUPPORTS_EJECT = 32;
        public static final int FLAG_SUPPORTS_IS_CHILD = 16;
        public static final int FLAG_SUPPORTS_RECENTS = 4;
        public static final int FLAG_SUPPORTS_SEARCH = 8;
        public static final String MIME_TYPE_ITEM = "vnd.android.document/root";

        private Root() {
        }
    }

    public static Uri buildRootsUri(String str) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).build();
    }

    public static Uri buildRootUri(String str, String str2) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).appendPath(str2).build();
    }

    public static Uri buildRecentDocumentsUri(String str, String str2) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).appendPath(str2).appendPath("recent").build();
    }

    public static Uri buildTreeDocumentUri(String str, String str2) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(PATH_TREE).appendPath(str2).build();
    }

    public static Uri buildDocumentUri(String str, String str2) {
        return getBaseDocumentUriBuilder(str).appendPath(str2).build();
    }

    @SystemApi
    public static Uri buildDocumentUriAsUser(String str, String str2, UserHandle userHandle) {
        return ContentProvider.maybeAddUserId(buildDocumentUri(str, str2), userHandle.getIdentifier());
    }

    public static Uri buildBaseDocumentUri(String str) {
        return getBaseDocumentUriBuilder(str).build();
    }

    private static Uri.Builder getBaseDocumentUriBuilder(String str) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(PATH_DOCUMENT);
    }

    public static Uri buildDocumentUriUsingTree(Uri uri, String str) {
        return new Uri.Builder().scheme("content").authority(uri.getAuthority()).appendPath(PATH_TREE).appendPath(getTreeDocumentId(uri)).appendPath(PATH_DOCUMENT).appendPath(str).build();
    }

    public static Uri buildDocumentUriMaybeUsingTree(Uri uri, String str) {
        if (isTreeUri(uri)) {
            return buildDocumentUriUsingTree(uri, str);
        }
        return buildDocumentUri(uri.getAuthority(), str);
    }

    public static Uri buildChildDocumentsUri(String str, String str2) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(PATH_DOCUMENT).appendPath(str2).appendPath(PATH_CHILDREN).build();
    }

    public static Uri buildChildDocumentsUriUsingTree(Uri uri, String str) {
        return new Uri.Builder().scheme("content").authority(uri.getAuthority()).appendPath(PATH_TREE).appendPath(getTreeDocumentId(uri)).appendPath(PATH_DOCUMENT).appendPath(str).appendPath(PATH_CHILDREN).build();
    }

    public static Uri buildSearchDocumentsUri(String str, String str2, String str3) {
        return new Uri.Builder().scheme("content").authority(str).appendPath(PATH_ROOT).appendPath(str2).appendPath("search").appendQueryParameter("query", str3).build();
    }

    public static boolean matchSearchQueryArguments(Bundle bundle, String str, String str2, long j, long j2) {
        if (bundle == null) {
            return true;
        }
        String string = bundle.getString(QUERY_ARG_DISPLAY_NAME, "");
        if (!string.isEmpty() && !str.toLowerCase().contains(string.toLowerCase())) {
            return false;
        }
        long j3 = bundle.getLong(QUERY_ARG_FILE_SIZE_OVER, -1L);
        if (j3 != -1 && j2 < j3) {
            return false;
        }
        long j4 = bundle.getLong(QUERY_ARG_LAST_MODIFIED_AFTER, -1L);
        if (j4 != -1 && j < j4) {
            return false;
        }
        String[] stringArray = bundle.getStringArray(QUERY_ARG_MIME_TYPES);
        if (stringArray == null || stringArray.length <= 0) {
            return true;
        }
        String normalizeMimeType = Intent.normalizeMimeType(str2);
        for (String str3 : stringArray) {
            if (MimeTypeFilter.matches(normalizeMimeType, Intent.normalizeMimeType(str3))) {
                return true;
            }
        }
        return false;
    }

    public static String[] getHandledQueryArguments(Bundle bundle) {
        if (bundle == null) {
            return new String[0];
        }
        ArrayList arrayList = new ArrayList();
        if (bundle.keySet().contains(QUERY_ARG_EXCLUDE_MEDIA)) {
            arrayList.add(QUERY_ARG_EXCLUDE_MEDIA);
        }
        if (bundle.keySet().contains(QUERY_ARG_DISPLAY_NAME)) {
            arrayList.add(QUERY_ARG_DISPLAY_NAME);
        }
        if (bundle.keySet().contains(QUERY_ARG_FILE_SIZE_OVER)) {
            arrayList.add(QUERY_ARG_FILE_SIZE_OVER);
        }
        if (bundle.keySet().contains(QUERY_ARG_LAST_MODIFIED_AFTER)) {
            arrayList.add(QUERY_ARG_LAST_MODIFIED_AFTER);
        }
        if (bundle.keySet().contains(QUERY_ARG_MIME_TYPES)) {
            arrayList.add(QUERY_ARG_MIME_TYPES);
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        if (isContentUri(uri) && isDocumentsProvider(context, uri.getAuthority())) {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments.size() == 2) {
                return PATH_DOCUMENT.equals(pathSegments.get(0));
            }
            if (pathSegments.size() == 4 && PATH_TREE.equals(pathSegments.get(0)) && PATH_DOCUMENT.equals(pathSegments.get(2))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRootsUri(Context context, Uri uri) {
        Preconditions.checkNotNull(context, "context can not be null");
        return isRootUri(context, uri, 1);
    }

    public static boolean isRootUri(Context context, Uri uri) {
        Preconditions.checkNotNull(context, "context can not be null");
        return isRootUri(context, uri, 2);
    }

    public static boolean isContentUri(Uri uri) {
        return uri != null && "content".equals(uri.getScheme());
    }

    public static boolean isTreeUri(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        return pathSegments.size() >= 2 && PATH_TREE.equals(pathSegments.get(0));
    }

    private static boolean isRootUri(Context context, Uri uri, int i) {
        if (isContentUri(uri) && isDocumentsProvider(context, uri.getAuthority())) {
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments.size() == i && PATH_ROOT.equals(pathSegments.get(0))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDocumentsProvider(Context context, String str) {
        List<ResolveInfo> queryIntentContentProviders = context.getPackageManager().queryIntentContentProviders(new Intent(PROVIDER_INTERFACE), 0);
        if (SemDualAppManager.isDualAppId(UserHandle.getCallingUserId())) {
            str = ContentProvider.getAuthorityWithoutUserId(str);
        }
        Iterator<ResolveInfo> it = queryIntentContentProviders.iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().providerInfo.authority)) {
                return true;
            }
        }
        return false;
    }

    public static String getRootId(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() >= 2 && PATH_ROOT.equals(pathSegments.get(0))) {
            return pathSegments.get(1);
        }
        throw new IllegalArgumentException("Invalid URI: " + uri);
    }

    public static String getDocumentId(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() >= 2 && PATH_DOCUMENT.equals(pathSegments.get(0))) {
            return pathSegments.get(1);
        }
        if (pathSegments.size() >= 4 && PATH_TREE.equals(pathSegments.get(0)) && PATH_DOCUMENT.equals(pathSegments.get(2))) {
            return pathSegments.get(3);
        }
        throw new IllegalArgumentException("Invalid URI: " + uri);
    }

    public static String getTreeDocumentId(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() >= 2 && PATH_TREE.equals(pathSegments.get(0))) {
            return pathSegments.get(1);
        }
        throw new IllegalArgumentException("Invalid URI: " + uri);
    }

    public static String getSearchDocumentsQuery(Uri uri) {
        return uri.getQueryParameter("query");
    }

    public static String getSearchDocumentsQuery(Bundle bundle) {
        Preconditions.checkNotNull(bundle, "bundle can not be null");
        return bundle.getString(QUERY_ARG_DISPLAY_NAME, "");
    }

    @SystemApi
    public static Uri setManageMode(Uri uri) {
        Preconditions.checkNotNull(uri, "uri can not be null");
        return uri.buildUpon().appendQueryParameter(PARAM_MANAGE, "true").build();
    }

    @SystemApi
    public static boolean isManageMode(Uri uri) {
        Preconditions.checkNotNull(uri, "uri can not be null");
        return uri.getBooleanQueryParameter(PARAM_MANAGE, false);
    }

    public static Bitmap getDocumentThumbnail(ContentResolver contentResolver, Uri uri, Point point, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            return ContentResolver.loadThumbnail(contentResolver, uri, new Size(point.x, point.y), cancellationSignal, 1);
        } catch (Exception e) {
            if (!(e instanceof OperationCanceledException)) {
                Log.w(TAG, "Failed to load thumbnail for " + uri + ": " + e);
            }
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static Uri createDocument(ContentResolver contentResolver, Uri uri, String str, String str2) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putString("mime_type", str);
            bundle.putString("_display_name", str2);
            return (Uri) contentResolver.call(uri.getAuthority(), METHOD_CREATE_DOCUMENT, (String) null, bundle).getParcelable("uri", Uri.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to create document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static boolean isChildDocument(ContentResolver contentResolver, Uri uri, Uri uri2) throws FileNotFoundException {
        Preconditions.checkNotNull(contentResolver, "content can not be null");
        Preconditions.checkNotNull(uri, "parentDocumentUri can not be null");
        Preconditions.checkNotNull(uri2, "childDocumentUri can not be null");
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_TARGET_URI, uri2);
            Bundle call = contentResolver.call(uri.getAuthority(), METHOD_IS_CHILD_DOCUMENT, (String) null, bundle);
            if (call == null) {
                throw new RemoteException("Failed to get a response from isChildDocument query.");
            }
            if (!call.containsKey("result")) {
                throw new RemoteException("Response did not include result field..");
            }
            return call.getBoolean("result");
        } catch (Exception e) {
            Log.w(TAG, "Failed to create document", e);
            rethrowIfNecessary(e);
            return false;
        }
    }

    public static Uri renameDocument(ContentResolver contentResolver, Uri uri, String str) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putString("_display_name", str);
            Uri uri2 = (Uri) contentResolver.call(uri.getAuthority(), METHOD_RENAME_DOCUMENT, (String) null, bundle).getParcelable("uri", Uri.class);
            return uri2 != null ? uri2 : uri;
        } catch (Exception e) {
            Log.w(TAG, "Failed to rename document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static boolean deleteDocument(ContentResolver contentResolver, Uri uri) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            contentResolver.call(uri.getAuthority(), METHOD_DELETE_DOCUMENT, (String) null, bundle);
            return true;
        } catch (Exception e) {
            Log.w(TAG, "Failed to delete document", e);
            rethrowIfNecessary(e);
            return false;
        }
    }

    public static Uri copyDocument(ContentResolver contentResolver, Uri uri, Uri uri2) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_TARGET_URI, uri2);
            return (Uri) contentResolver.call(uri.getAuthority(), METHOD_COPY_DOCUMENT, (String) null, bundle).getParcelable("uri", Uri.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to copy document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static Uri moveDocument(ContentResolver contentResolver, Uri uri, Uri uri2, Uri uri3) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_PARENT_URI, uri2);
            bundle.putParcelable(EXTRA_TARGET_URI, uri3);
            return (Uri) contentResolver.call(uri.getAuthority(), METHOD_MOVE_DOCUMENT, (String) null, bundle).getParcelable("uri", Uri.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to move document", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static boolean removeDocument(ContentResolver contentResolver, Uri uri, Uri uri2) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            bundle.putParcelable(EXTRA_PARENT_URI, uri2);
            contentResolver.call(uri.getAuthority(), METHOD_REMOVE_DOCUMENT, (String) null, bundle);
            return true;
        } catch (Exception e) {
            Log.w(TAG, "Failed to remove document", e);
            rethrowIfNecessary(e);
            return false;
        }
    }

    public static void ejectRoot(ContentResolver contentResolver, Uri uri) {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            contentResolver.call(uri.getAuthority(), METHOD_EJECT_ROOT, (String) null, bundle);
        } catch (Exception e) {
            Log.w(TAG, "Failed to eject", e);
        }
    }

    public static Bundle getDocumentMetadata(ContentResolver contentResolver, Uri uri) throws FileNotFoundException {
        Preconditions.checkNotNull(contentResolver, "content can not be null");
        Preconditions.checkNotNull(uri, "documentUri can not be null");
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            return contentResolver.call(uri.getAuthority(), METHOD_GET_DOCUMENT_METADATA, (String) null, bundle);
        } catch (Exception e) {
            Log.w(TAG, "Failed to get document metadata");
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static Path findDocumentPath(ContentResolver contentResolver, Uri uri) throws FileNotFoundException {
        try {
            Bundle bundle = new Bundle();
            bundle.putParcelable("uri", uri);
            return (Path) contentResolver.call(uri.getAuthority(), METHOD_FIND_DOCUMENT_PATH, (String) null, bundle).getParcelable("result", Path.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to find path", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static IntentSender createWebLinkIntent(ContentResolver contentResolver, Uri uri, Bundle bundle) throws FileNotFoundException {
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("uri", uri);
            if (bundle != null) {
                bundle2.putBundle("options", bundle);
            }
            return (IntentSender) contentResolver.call(uri.getAuthority(), METHOD_CREATE_WEB_LINK_INTENT, (String) null, bundle2).getParcelable("result", IntentSender.class);
        } catch (Exception e) {
            Log.w(TAG, "Failed to create a web link intent", e);
            rethrowIfNecessary(e);
            return null;
        }
    }

    public static AssetFileDescriptor openImageThumbnail(File file) throws FileNotFoundException {
        Bundle bundle;
        ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 268435456);
        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            long[] thumbnailRange = exifInterface.getThumbnailRange();
            if (thumbnailRange != null) {
                int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
                if (attributeInt == 3) {
                    bundle = new Bundle(1);
                    bundle.putInt(EXTRA_ORIENTATION, 180);
                } else if (attributeInt == 6) {
                    bundle = new Bundle(1);
                    bundle.putInt(EXTRA_ORIENTATION, 90);
                } else if (attributeInt != 8) {
                    bundle = null;
                } else {
                    bundle = new Bundle(1);
                    bundle.putInt(EXTRA_ORIENTATION, 270);
                }
                return new AssetFileDescriptor(open, thumbnailRange[0], thumbnailRange[1], bundle);
            }
        } catch (IOException unused) {
        }
        return new AssetFileDescriptor(open, 0L, -1L, null);
    }

    private static void rethrowIfNecessary(Exception exc) throws FileNotFoundException {
        if (VMRuntime.getRuntime().getTargetSdkVersion() >= 26) {
            if (exc instanceof ParcelableException) {
                ((ParcelableException) exc).maybeRethrow(FileNotFoundException.class);
            } else if (exc instanceof RemoteException) {
                ((RemoteException) exc).rethrowAsRuntimeException();
            } else if (exc instanceof RuntimeException) {
                throw ((RuntimeException) exc);
            }
        }
    }

    public static final class Path implements Parcelable {
        public static final Parcelable.Creator<Path> CREATOR = new Parcelable.Creator<Path>() { // from class: android.provider.DocumentsContract.Path.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Path createFromParcel(Parcel parcel) {
                return new Path(parcel.readString(), parcel.createStringArrayList());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Path[] newArray(int i) {
                return new Path[i];
            }
        };
        private final List<String> mPath;
        private final String mRootId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Path(String str, List<String> list) {
            Preconditions.checkCollectionNotEmpty(list, "path");
            Preconditions.checkCollectionElementsNotNull(list, "path");
            this.mRootId = str;
            this.mPath = list;
        }

        public String getRootId() {
            return this.mRootId;
        }

        public List<String> getPath() {
            return this.mPath;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof Path)) {
                Path path = (Path) obj;
                if (Objects.equals(this.mRootId, path.mRootId) && Objects.equals(this.mPath, path.mPath)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mRootId, this.mPath);
        }

        public String toString() {
            return "DocumentsContract.Path{rootId=" + this.mRootId + ", path=" + this.mPath + "}";
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mRootId);
            parcel.writeStringList(this.mPath);
        }
    }
}
