package android.provider;

import android.Manifest;
import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentSender;
import android.content.MimeTypeFilter;
import android.content.UriMatcher;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.ParcelableException;
import android.provider.DocumentsContract;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Objects;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public abstract class DocumentsProvider extends ContentProvider {
    private static final int MATCH_CHILDREN = 6;
    private static final int MATCH_CHILDREN_TREE = 8;
    private static final int MATCH_DOCUMENT = 5;
    private static final int MATCH_DOCUMENT_TREE = 7;
    private static final int MATCH_RECENT = 3;
    private static final int MATCH_ROOT = 2;
    private static final int MATCH_ROOTS = 1;
    private static final int MATCH_SEARCH = 4;
    private static final String TAG = "DocumentsProvider";
    private String mAuthority;
    private UriMatcher mMatcher;

    public boolean isChildDocument(String str, String str2) {
        return false;
    }

    public abstract ParcelFileDescriptor openDocument(String str, String str2, CancellationSignal cancellationSignal) throws FileNotFoundException;

    public abstract Cursor queryChildDocuments(String str, String[] strArr, String str2) throws FileNotFoundException;

    public abstract Cursor queryDocument(String str, String[] strArr) throws FileNotFoundException;

    public abstract Cursor queryRoots(String[] strArr) throws FileNotFoundException;

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        registerAuthority(providerInfo.authority);
        if (!providerInfo.exported) {
            throw new SecurityException("Provider must be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grantUriPermissions");
        }
        if (!Manifest.permission.MANAGE_DOCUMENTS.equals(providerInfo.readPermission) || !Manifest.permission.MANAGE_DOCUMENTS.equals(providerInfo.writePermission)) {
            throw new SecurityException("Provider must be protected by MANAGE_DOCUMENTS");
        }
        super.attachInfo(context, providerInfo);
    }

    @Override // android.content.ContentProvider
    public void attachInfoForTesting(Context context, ProviderInfo providerInfo) {
        registerAuthority(providerInfo.authority);
        super.attachInfoForTesting(context, providerInfo);
    }

    private void registerAuthority(String str) {
        this.mAuthority = str;
        UriMatcher uriMatcher = new UriMatcher(-1);
        this.mMatcher = uriMatcher;
        uriMatcher.addURI(this.mAuthority, "root", 1);
        this.mMatcher.addURI(this.mAuthority, "root/*", 2);
        this.mMatcher.addURI(this.mAuthority, "root/*/recent", 3);
        this.mMatcher.addURI(this.mAuthority, "root/*/search", 4);
        this.mMatcher.addURI(this.mAuthority, "document/*", 5);
        this.mMatcher.addURI(this.mAuthority, "document/*/children", 6);
        this.mMatcher.addURI(this.mAuthority, "tree/*/document/*", 7);
        this.mMatcher.addURI(this.mAuthority, "tree/*/document/*/children", 8);
    }

    private void enforceTreeForExtraUris(Bundle bundle) {
        enforceTree((Uri) bundle.getParcelable("uri", Uri.class));
        enforceTree((Uri) bundle.getParcelable(DocumentsContract.EXTRA_PARENT_URI, Uri.class));
        enforceTree((Uri) bundle.getParcelable(DocumentsContract.EXTRA_TARGET_URI, Uri.class));
    }

    private void enforceTree(Uri uri) {
        if (uri == null || !DocumentsContract.isTreeUri(uri)) {
            return;
        }
        String treeDocumentId = DocumentsContract.getTreeDocumentId(uri);
        String documentId = DocumentsContract.getDocumentId(uri);
        if (Objects.equals(treeDocumentId, documentId) || isChildDocument(treeDocumentId, documentId)) {
            return;
        }
        throw new SecurityException("Document " + documentId + " is not a descendant of " + treeDocumentId);
    }

    private Uri validateIncomingNullableUri(Uri uri) {
        if (uri == null) {
            return null;
        }
        return validateIncomingUri(uri);
    }

    public String createDocument(String str, String str2, String str3) throws FileNotFoundException {
        throw new UnsupportedOperationException("Create not supported");
    }

    public String renameDocument(String str, String str2) throws FileNotFoundException {
        throw new UnsupportedOperationException("Rename not supported");
    }

    public void deleteDocument(String str) throws FileNotFoundException {
        throw new UnsupportedOperationException("Delete not supported");
    }

    public String copyDocument(String str, String str2) throws FileNotFoundException {
        throw new UnsupportedOperationException("Copy not supported");
    }

    public String moveDocument(String str, String str2, String str3) throws FileNotFoundException {
        throw new UnsupportedOperationException("Move not supported");
    }

    public void removeDocument(String str, String str2) throws FileNotFoundException {
        throw new UnsupportedOperationException("Remove not supported");
    }

    public DocumentsContract.Path findDocumentPath(String str, String str2) throws FileNotFoundException {
        throw new UnsupportedOperationException("findDocumentPath not supported.");
    }

    public IntentSender createWebLinkIntent(String str, Bundle bundle) throws FileNotFoundException {
        throw new UnsupportedOperationException("createWebLink is not supported.");
    }

    public Cursor queryRecentDocuments(String str, String[] strArr) throws FileNotFoundException {
        throw new UnsupportedOperationException("Recent not supported");
    }

    public Cursor queryRecentDocuments(String str, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        Preconditions.checkNotNull(str, "rootId can not be null");
        Cursor cursorQueryRecentDocuments = queryRecentDocuments(str, strArr);
        Bundle bundle2 = new Bundle();
        cursorQueryRecentDocuments.setExtras(bundle2);
        bundle2.putStringArray(ContentResolver.EXTRA_HONORED_ARGS, new String[0]);
        return cursorQueryRecentDocuments;
    }

    public Cursor queryChildDocuments(String str, String[] strArr, Bundle bundle) throws FileNotFoundException {
        return queryChildDocuments(str, strArr, getSortClause(bundle));
    }

    public Cursor queryChildDocumentsForManage(String str, String[] strArr, String str2) throws FileNotFoundException {
        throw new UnsupportedOperationException("Manage not supported");
    }

    public Cursor querySearchDocuments(String str, String str2, String[] strArr) throws FileNotFoundException {
        throw new UnsupportedOperationException("Search not supported");
    }

    public Cursor querySearchDocuments(String str, String[] strArr, Bundle bundle) throws FileNotFoundException {
        Preconditions.checkNotNull(str, "rootId can not be null");
        Preconditions.checkNotNull(bundle, "queryArgs can not be null");
        return querySearchDocuments(str, DocumentsContract.getSearchDocumentsQuery(bundle), strArr);
    }

    public void ejectRoot(String str) {
        throw new UnsupportedOperationException("Eject not supported");
    }

    public Bundle getDocumentMetadata(String str) throws FileNotFoundException {
        throw new UnsupportedOperationException("Metadata not supported");
    }

    public String getDocumentType(String str) throws FileNotFoundException {
        Cursor cursorQueryDocument = queryDocument(str, null);
        try {
            if (cursorQueryDocument.moveToFirst()) {
                return cursorQueryDocument.getString(cursorQueryDocument.getColumnIndexOrThrow("mime_type"));
            }
            return null;
        } finally {
            IoUtils.closeQuietly(cursorQueryDocument);
        }
    }

    public AssetFileDescriptor openDocumentThumbnail(String str, Point point, CancellationSignal cancellationSignal) throws FileNotFoundException {
        throw new UnsupportedOperationException("Thumbnails not supported");
    }

    public AssetFileDescriptor openTypedDocument(String str, String str2, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        throw new FileNotFoundException("The requested MIME type is not supported.");
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException("Pre-Android-O query format not supported.");
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        throw new UnsupportedOperationException("Pre-Android-O query format not supported.");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final Cursor query(Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) {
        try {
            switch (this.mMatcher.match(uri)) {
                case 1:
                    return queryRoots(strArr);
                case 2:
                default:
                    throw new UnsupportedOperationException("Unsupported Uri " + uri);
                case 3:
                    return queryRecentDocuments(DocumentsContract.getRootId(uri), strArr, bundle, cancellationSignal);
                case 4:
                    return querySearchDocuments(DocumentsContract.getRootId(uri), strArr, bundle);
                case 5:
                case 7:
                    enforceTree(uri);
                    return queryDocument(DocumentsContract.getDocumentId(uri), strArr);
                case 6:
                case 8:
                    enforceTree(uri);
                    if (DocumentsContract.isManageMode(uri)) {
                        return queryChildDocumentsForManage(DocumentsContract.getDocumentId(uri), strArr, getSortClause(bundle));
                    }
                    return queryChildDocuments(DocumentsContract.getDocumentId(uri), strArr, bundle);
            }
        } catch (FileNotFoundException e) {
            Log.w(TAG, "Failed during query", e);
            return null;
        }
    }

    private static String getSortClause(Bundle bundle) {
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        String string = bundle.getString(ContentResolver.QUERY_ARG_SQL_SORT_ORDER);
        return (string == null && bundle.containsKey(ContentResolver.QUERY_ARG_SORT_COLUMNS)) ? ContentResolver.createSqlSortClause(bundle) : string;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final String getType(Uri uri) {
        try {
            int iMatch = this.mMatcher.match(uri);
            if (iMatch == 2) {
                return DocumentsContract.Root.MIME_TYPE_ITEM;
            }
            if (iMatch != 5 && iMatch != 7) {
                return null;
            }
            enforceTree(uri);
            return getDocumentType(DocumentsContract.getDocumentId(uri));
        } catch (FileNotFoundException e) {
            Log.w(TAG, "Failed during getType", e);
            return null;
        }
    }

    @Override // android.content.ContentProvider
    public final String getTypeAnonymous(Uri uri) {
        if (this.mMatcher.match(uri) != 2) {
            return null;
        }
        return DocumentsContract.Root.MIME_TYPE_ITEM;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public Uri canonicalize(Uri uri) {
        Context context = getContext();
        if (this.mMatcher.match(uri) != 7) {
            return null;
        }
        enforceTree(uri);
        Uri uriBuildDocumentUri = DocumentsContract.buildDocumentUri(uri.getAuthority(), DocumentsContract.getDocumentId(uri));
        context.grantUriPermission(getCallingPackage(), uriBuildDocumentUri, getCallingOrSelfUriPermissionModeFlags(context, uri));
        return uriBuildDocumentUri;
    }

    private static int getCallingOrSelfUriPermissionModeFlags(Context context, Uri uri) {
        int i = context.checkCallingOrSelfUriPermission(uri, 1) != 0 ? 0 : 1;
        if (context.checkCallingOrSelfUriPermission(uri, 2) == 0) {
            i |= 2;
        }
        return context.checkCallingOrSelfUriPermission(uri, 65) == 0 ? i | 64 : i;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Insert not supported");
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("Delete not supported");
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("Update not supported");
    }

    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        if (!str.startsWith("android:")) {
            return super.call(str, str2, bundle);
        }
        try {
            return callUnchecked(str, str2, bundle);
        } catch (FileNotFoundException e) {
            throw new ParcelableException(e);
        }
    }

    private Bundle callUnchecked(String str, String str2, Bundle bundle) throws FileNotFoundException {
        Context context = getContext();
        Bundle bundle2 = new Bundle();
        enforceTreeForExtraUris(bundle);
        Uri uriValidateIncomingNullableUri = validateIncomingNullableUri((Uri) bundle.getParcelable("uri", Uri.class));
        Uri uriValidateIncomingNullableUri2 = validateIncomingNullableUri((Uri) bundle.getParcelable(DocumentsContract.EXTRA_TARGET_URI, Uri.class));
        Uri uriValidateIncomingNullableUri3 = validateIncomingNullableUri((Uri) bundle.getParcelable(DocumentsContract.EXTRA_PARENT_URI, Uri.class));
        if (DocumentsContract.METHOD_EJECT_ROOT.equals(str)) {
            enforceWritePermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
            ejectRoot(DocumentsContract.getRootId(uriValidateIncomingNullableUri));
            return bundle2;
        }
        String authorityWithoutUserId = getAuthorityWithoutUserId(uriValidateIncomingNullableUri.getAuthority());
        String documentId = DocumentsContract.getDocumentId(uriValidateIncomingNullableUri);
        if (!this.mAuthority.equals(authorityWithoutUserId)) {
            throw new SecurityException("Requested authority " + authorityWithoutUserId + " doesn't match provider " + this.mAuthority);
        }
        if (DocumentsContract.METHOD_IS_CHILD_DOCUMENT.equals(str)) {
            enforceReadPermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
            bundle2.putBoolean("result", this.mAuthority.equals(uriValidateIncomingNullableUri2.getAuthority()) && isChildDocument(documentId, DocumentsContract.getDocumentId(uriValidateIncomingNullableUri2)));
            return bundle2;
        }
        if (DocumentsContract.METHOD_CREATE_DOCUMENT.equals(str)) {
            enforceWritePermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
            bundle2.putParcelable("uri", DocumentsContract.buildDocumentUriMaybeUsingTree(uriValidateIncomingNullableUri, createDocument(documentId, bundle.getString("mime_type"), bundle.getString("_display_name"))));
            return bundle2;
        }
        if (DocumentsContract.METHOD_CREATE_WEB_LINK_INTENT.equals(str)) {
            enforceWritePermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
            bundle2.putParcelable("result", createWebLinkIntent(documentId, bundle.getBundle("options")));
            return bundle2;
        }
        if (DocumentsContract.METHOD_RENAME_DOCUMENT.equals(str)) {
            enforceWritePermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
            String strRenameDocument = renameDocument(documentId, bundle.getString("_display_name"));
            if (strRenameDocument != null) {
                Uri uriBuildDocumentUriMaybeUsingTree = DocumentsContract.buildDocumentUriMaybeUsingTree(uriValidateIncomingNullableUri, strRenameDocument);
                if (!DocumentsContract.isTreeUri(uriBuildDocumentUriMaybeUsingTree)) {
                    context.grantUriPermission(getCallingPackage(), uriBuildDocumentUriMaybeUsingTree, getCallingOrSelfUriPermissionModeFlags(context, uriValidateIncomingNullableUri));
                }
                bundle2.putParcelable("uri", uriBuildDocumentUriMaybeUsingTree);
                revokeDocumentPermission(documentId);
                return bundle2;
            }
        } else {
            if (DocumentsContract.METHOD_DELETE_DOCUMENT.equals(str)) {
                enforceWritePermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
                deleteDocument(documentId);
                revokeDocumentPermission(documentId);
                return bundle2;
            }
            if (DocumentsContract.METHOD_COPY_DOCUMENT.equals(str)) {
                String documentId2 = DocumentsContract.getDocumentId(uriValidateIncomingNullableUri2);
                enforceReadPermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
                enforceWritePermissionInner(uriValidateIncomingNullableUri2, getCallingAttributionSource());
                String strCopyDocument = copyDocument(documentId, documentId2);
                if (strCopyDocument != null) {
                    Uri uriBuildDocumentUriMaybeUsingTree2 = DocumentsContract.buildDocumentUriMaybeUsingTree(uriValidateIncomingNullableUri, strCopyDocument);
                    if (!DocumentsContract.isTreeUri(uriBuildDocumentUriMaybeUsingTree2)) {
                        context.grantUriPermission(getCallingPackage(), uriBuildDocumentUriMaybeUsingTree2, getCallingOrSelfUriPermissionModeFlags(context, uriValidateIncomingNullableUri));
                    }
                    bundle2.putParcelable("uri", uriBuildDocumentUriMaybeUsingTree2);
                    return bundle2;
                }
            } else if (DocumentsContract.METHOD_MOVE_DOCUMENT.equals(str)) {
                String documentId3 = DocumentsContract.getDocumentId(uriValidateIncomingNullableUri3);
                String documentId4 = DocumentsContract.getDocumentId(uriValidateIncomingNullableUri2);
                enforceWritePermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
                enforceReadPermissionInner(uriValidateIncomingNullableUri3, getCallingAttributionSource());
                enforceWritePermissionInner(uriValidateIncomingNullableUri2, getCallingAttributionSource());
                String strMoveDocument = moveDocument(documentId, documentId3, documentId4);
                if (strMoveDocument != null) {
                    Uri uriBuildDocumentUriMaybeUsingTree3 = DocumentsContract.buildDocumentUriMaybeUsingTree(uriValidateIncomingNullableUri, strMoveDocument);
                    if (!DocumentsContract.isTreeUri(uriBuildDocumentUriMaybeUsingTree3)) {
                        context.grantUriPermission(getCallingPackage(), uriBuildDocumentUriMaybeUsingTree3, getCallingOrSelfUriPermissionModeFlags(context, uriValidateIncomingNullableUri));
                    }
                    bundle2.putParcelable("uri", uriBuildDocumentUriMaybeUsingTree3);
                }
            } else {
                if (DocumentsContract.METHOD_REMOVE_DOCUMENT.equals(str)) {
                    String documentId5 = DocumentsContract.getDocumentId(uriValidateIncomingNullableUri3);
                    enforceReadPermissionInner(uriValidateIncomingNullableUri3, getCallingAttributionSource());
                    enforceWritePermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
                    removeDocument(documentId, documentId5);
                    return bundle2;
                }
                if (DocumentsContract.METHOD_FIND_DOCUMENT_PATH.equals(str)) {
                    boolean zIsTreeUri = DocumentsContract.isTreeUri(uriValidateIncomingNullableUri);
                    if (zIsTreeUri) {
                        enforceReadPermissionInner(uriValidateIncomingNullableUri, getCallingAttributionSource());
                    } else {
                        getContext().enforceCallingPermission(Manifest.permission.MANAGE_DOCUMENTS, null);
                    }
                    String treeDocumentId = zIsTreeUri ? DocumentsContract.getTreeDocumentId(uriValidateIncomingNullableUri) : null;
                    DocumentsContract.Path pathFindDocumentPath = findDocumentPath(treeDocumentId, documentId);
                    if (zIsTreeUri) {
                        if (!Objects.equals(pathFindDocumentPath.getPath().get(0), treeDocumentId)) {
                            Log.wtf(TAG, "Provider doesn't return path from the tree root. Expected: " + treeDocumentId + " found: " + pathFindDocumentPath.getPath().get(0));
                            LinkedList linkedList = new LinkedList(pathFindDocumentPath.getPath());
                            while (linkedList.size() > 1 && !Objects.equals(linkedList.getFirst(), treeDocumentId)) {
                                linkedList.removeFirst();
                            }
                            pathFindDocumentPath = new DocumentsContract.Path(null, linkedList);
                        }
                        if (pathFindDocumentPath.getRootId() != null) {
                            Log.wtf(TAG, "Provider returns root id :" + pathFindDocumentPath.getRootId() + " unexpectedly. Erase root id.");
                            pathFindDocumentPath = new DocumentsContract.Path(null, pathFindDocumentPath.getPath());
                        }
                    }
                    bundle2.putParcelable("result", pathFindDocumentPath);
                    return bundle2;
                }
                if (DocumentsContract.METHOD_GET_DOCUMENT_METADATA.equals(str)) {
                    return getDocumentMetadata(documentId);
                }
                throw new UnsupportedOperationException("Method not supported " + str);
            }
        }
        return bundle2;
    }

    public final void revokeDocumentPermission(String str) {
        Context context = getContext();
        context.revokeUriPermission(DocumentsContract.buildDocumentUri(this.mAuthority, str), -1);
        context.revokeUriPermission(DocumentsContract.buildTreeDocumentUri(this.mAuthority, str), -1);
    }

    @Override // android.content.ContentProvider
    public final ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        enforceTree(uri);
        return openDocument(DocumentsContract.getDocumentId(uri), str, null);
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final ParcelFileDescriptor openFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        enforceTree(uri);
        return openDocument(DocumentsContract.getDocumentId(uri), str, cancellationSignal);
    }

    @Override // android.content.ContentProvider
    public final AssetFileDescriptor openAssetFile(Uri uri, String str) throws FileNotFoundException {
        enforceTree(uri);
        ParcelFileDescriptor parcelFileDescriptorOpenDocument = openDocument(DocumentsContract.getDocumentId(uri), str, null);
        if (parcelFileDescriptorOpenDocument != null) {
            return new AssetFileDescriptor(parcelFileDescriptorOpenDocument, 0L, -1L);
        }
        return null;
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final AssetFileDescriptor openAssetFile(Uri uri, String str, CancellationSignal cancellationSignal) throws FileNotFoundException {
        enforceTree(uri);
        ParcelFileDescriptor parcelFileDescriptorOpenDocument = openDocument(DocumentsContract.getDocumentId(uri), str, cancellationSignal);
        if (parcelFileDescriptorOpenDocument != null) {
            return new AssetFileDescriptor(parcelFileDescriptorOpenDocument, 0L, -1L);
        }
        return null;
    }

    @Override // android.content.ContentProvider
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle) throws FileNotFoundException {
        return openTypedAssetFileImpl(uri, str, bundle, null);
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public final AssetFileDescriptor openTypedAssetFile(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return openTypedAssetFileImpl(uri, str, bundle, cancellationSignal);
    }

    public String[] getDocumentStreamTypes(String str, String str2) throws Throwable {
        Cursor cursorQueryDocument;
        Cursor cursor = null;
        try {
            cursorQueryDocument = queryDocument(str, null);
        } catch (FileNotFoundException unused) {
            cursorQueryDocument = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (cursorQueryDocument.moveToFirst()) {
                String string = cursorQueryDocument.getString(cursorQueryDocument.getColumnIndexOrThrow("mime_type"));
                if ((cursorQueryDocument.getLong(cursorQueryDocument.getColumnIndexOrThrow("flags")) & 512) == 0 && string != null && MimeTypeFilter.matches(string, str2)) {
                    String[] strArr = {string};
                    IoUtils.closeQuietly(cursorQueryDocument);
                    return strArr;
                }
            }
            IoUtils.closeQuietly(cursorQueryDocument);
            return null;
        } catch (FileNotFoundException unused2) {
            IoUtils.closeQuietly(cursorQueryDocument);
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQueryDocument;
            IoUtils.closeQuietly(cursor);
            throw th;
        }
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String[] getStreamTypes(Uri uri, String str) {
        enforceTree(uri);
        return getDocumentStreamTypes(DocumentsContract.getDocumentId(uri), str);
    }

    private final AssetFileDescriptor openTypedAssetFileImpl(Uri uri, String str, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        enforceTree(uri);
        String documentId = DocumentsContract.getDocumentId(uri);
        if (bundle != null && bundle.containsKey(ContentResolver.EXTRA_SIZE)) {
            return openDocumentThumbnail(documentId, (Point) bundle.getParcelable(ContentResolver.EXTRA_SIZE, Point.class), cancellationSignal);
        }
        if ("*/*".equals(str)) {
            return openAssetFile(uri, "r");
        }
        String type = getType(uri);
        if (type != null && ClipDescription.compareMimeTypes(type, str)) {
            return openAssetFile(uri, "r");
        }
        return openTypedDocument(documentId, str, bundle, cancellationSignal);
    }
}
