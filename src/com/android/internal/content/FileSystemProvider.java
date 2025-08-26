package com.android.internal.content;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import android.provider.MediaStore;
import android.provider.MetadataReader;
import android.system.Int64Ref;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.MessagingMessage;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import libcore.io.IoUtils;

/* loaded from: classes5.dex */
public abstract class FileSystemProvider extends DocumentsProvider {
    private static final boolean LOG_INOTIFY = false;
    private static final int MAX_RESULTS_NUMBER = 23;
    protected static final String SUPPORTED_QUERY_ARGS = joinNewline(DocumentsContract.QUERY_ARG_DISPLAY_NAME, DocumentsContract.QUERY_ARG_FILE_SIZE_OVER, DocumentsContract.QUERY_ARG_LAST_MODIFIED_AFTER, DocumentsContract.QUERY_ARG_MIME_TYPES);
    private static final String TAG = "FileSystemProvider";
    private String[] mDefaultProjection;
    private Handler mHandler;
    private final ArrayMap<File, DirectoryObserver> mObservers = new ArrayMap<>();

    protected abstract Uri buildNotificationUri(String str);

    protected abstract String getDocIdForFile(File file) throws FileNotFoundException;

    protected abstract File getFileForDocId(String str, boolean z) throws FileNotFoundException;

    protected void onDocIdChanged(String str) {
    }

    protected void onDocIdDeleted(String str, boolean z) {
    }

    protected boolean shouldBlockDirectoryFromTree(String str) throws FileNotFoundException {
        return false;
    }

    protected boolean shouldHideDocument(String str) throws FileNotFoundException {
        return false;
    }

    private static String joinNewline(String... strArr) {
        return TextUtils.join(ShaderAssembler.NEWLINE, strArr);
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        throw new UnsupportedOperationException("Subclass should override this and call onCreate(defaultDocumentProjection)");
    }

    protected void onCreate(String[] strArr) {
        this.mHandler = new Handler();
        this.mDefaultProjection = strArr;
    }

    @Override // android.provider.DocumentsProvider
    public boolean isChildDocument(String str, String str2) {
        try {
            return FileUtils.contains(getFileForDocId(str).getCanonicalFile(), getFileForDocId(str2).getCanonicalFile());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to determine if " + str2 + " is child of " + str + ": " + e);
        }
    }

    @Override // android.provider.DocumentsProvider
    public Bundle getDocumentMetadata(String str) throws Throwable {
        FileInputStream fileInputStream;
        File fileForDocId = getFileForDocId(str);
        if (!fileForDocId.exists()) {
            throw new FileNotFoundException("Can't find the file for documentId: " + str);
        }
        String documentType = getDocumentType(str);
        FileInputStream fileInputStream2 = null;
        if (DocumentsContract.Document.MIME_TYPE_DIR.equals(documentType)) {
            final Int64Ref int64Ref = new Int64Ref(0L);
            final Int64Ref int64Ref2 = new Int64Ref(0L);
            try {
                Files.walkFileTree(FileSystems.getDefault().getPath(fileForDocId.getAbsolutePath(), new String[0]), new FileVisitor<Path>(this) { // from class: com.android.internal.content.FileSystemProvider.1
                    @Override // java.nio.file.FileVisitor
                    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override // java.nio.file.FileVisitor
                    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) {
                        int64Ref.value++;
                        int64Ref2.value += basicFileAttributes.size();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override // java.nio.file.FileVisitor
                    public FileVisitResult visitFileFailed(Path path, IOException iOException) {
                        return FileVisitResult.CONTINUE;
                    }

                    @Override // java.nio.file.FileVisitor
                    public FileVisitResult postVisitDirectory(Path path, IOException iOException) {
                        return FileVisitResult.CONTINUE;
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putLong(DocumentsContract.METADATA_TREE_COUNT, int64Ref.value);
                bundle.putLong(DocumentsContract.METADATA_TREE_SIZE, int64Ref2.value);
                return bundle;
            } catch (IOException e) {
                Log.e(TAG, "An error occurred retrieving the metadata", e);
                return null;
            }
        }
        if (!fileForDocId.isFile()) {
            Log.w(TAG, "Can't stream non-regular file. Returning empty metadata.");
            return null;
        }
        if (!fileForDocId.canRead()) {
            Log.w(TAG, "Can't stream non-readable file. Returning empty metadata.");
            return null;
        }
        if (!MetadataReader.isSupportedMimeType(documentType)) {
            Log.w(TAG, "Unsupported type " + documentType + ". Returning empty metadata.");
            return null;
        }
        try {
            Bundle bundle2 = new Bundle();
            fileInputStream = new FileInputStream(fileForDocId.getAbsolutePath());
            try {
                try {
                    MetadataReader.getMetadata(bundle2, fileInputStream, documentType, null);
                    IoUtils.closeQuietly(fileInputStream);
                    return bundle2;
                } catch (IOException e2) {
                    e = e2;
                    Log.e(TAG, "An error occurred retrieving the metadata", e);
                    IoUtils.closeQuietly(fileInputStream);
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                fileInputStream2 = fileInputStream;
                IoUtils.closeQuietly(fileInputStream2);
                throw th;
            }
        } catch (IOException e3) {
            e = e3;
            fileInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            IoUtils.closeQuietly(fileInputStream2);
            throw th;
        }
    }

    protected final List<String> findDocumentPath(File file, File file2) throws FileNotFoundException {
        if (!file2.exists()) {
            throw new FileNotFoundException(file2 + " is not found.");
        }
        if (!FileUtils.contains(file, file2)) {
            throw new FileNotFoundException(file2 + " is not found under " + file);
        }
        ArrayList arrayList = new ArrayList();
        while (file2 != null && FileUtils.contains(file, file2)) {
            arrayList.add(0, getDocIdForFile(file2));
            file2 = file2.getParentFile();
        }
        return arrayList;
    }

    @Override // android.provider.DocumentsProvider
    public String createDocument(String str, String str2, String str3) throws FileNotFoundException {
        String docIdForFile;
        String strBuildValidFatFilename = FileUtils.buildValidFatFilename(str3);
        File fileForDocId = getFileForDocId(str);
        if (!fileForDocId.isDirectory()) {
            throw new IllegalArgumentException("Parent document isn't a directory");
        }
        File fileBuildUniqueFile = FileUtils.buildUniqueFile(fileForDocId, str2, strBuildValidFatFilename);
        if (DocumentsContract.Document.MIME_TYPE_DIR.equals(str2)) {
            if (!fileBuildUniqueFile.mkdir()) {
                throw new IllegalStateException("Failed to mkdir " + fileBuildUniqueFile);
            }
            docIdForFile = getDocIdForFile(fileBuildUniqueFile);
            onDocIdChanged(docIdForFile);
        } else {
            try {
                if (!fileBuildUniqueFile.createNewFile()) {
                    throw new IllegalStateException("Failed to touch " + fileBuildUniqueFile);
                }
                docIdForFile = getDocIdForFile(fileBuildUniqueFile);
                onDocIdChanged(docIdForFile);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to touch " + fileBuildUniqueFile + ": " + e);
            }
        }
        updateMediaStore(getContext(), fileBuildUniqueFile);
        return docIdForFile;
    }

    @Override // android.provider.DocumentsProvider
    public String renameDocument(String str, String str2) throws FileNotFoundException {
        String strBuildValidFatFilename = FileUtils.buildValidFatFilename(str2);
        File fileForDocId = getFileForDocId(str);
        File fileForDocId2 = getFileForDocId(str, true);
        File fileBuildUniqueFile = FileUtils.buildUniqueFile(fileForDocId.getParentFile(), strBuildValidFatFilename);
        if (!fileForDocId.renameTo(fileBuildUniqueFile)) {
            throw new IllegalStateException("Failed to rename to " + fileBuildUniqueFile);
        }
        String docIdForFile = getDocIdForFile(fileBuildUniqueFile);
        onDocIdChanged(str);
        onDocIdChanged(docIdForFile);
        File fileForDocId3 = getFileForDocId(docIdForFile, true);
        updateMediaStore(getContext(), fileForDocId2);
        updateMediaStore(getContext(), fileForDocId3);
        if (TextUtils.equals(str, docIdForFile)) {
            return null;
        }
        onDocIdDeleted(str, false);
        return docIdForFile;
    }

    @Override // android.provider.DocumentsProvider
    public String moveDocument(String str, String str2, String str3) throws FileNotFoundException {
        File fileForDocId = getFileForDocId(str);
        File file = new File(getFileForDocId(str3), fileForDocId.getName());
        File fileForDocId2 = getFileForDocId(str, true);
        if (file.exists()) {
            throw new IllegalStateException("Already exists " + file);
        }
        if (!fileForDocId.renameTo(file)) {
            throw new IllegalStateException("Failed to move to " + file);
        }
        String docIdForFile = getDocIdForFile(file);
        onDocIdChanged(str);
        onDocIdDeleted(str, true);
        onDocIdChanged(docIdForFile);
        updateMediaStore(getContext(), fileForDocId2);
        updateMediaStore(getContext(), getFileForDocId(docIdForFile, true));
        return docIdForFile;
    }

    private static void updateMediaStore(Context context, File file) {
        if (file != null) {
            ContentResolver contentResolver = context.getContentResolver();
            if (!file.isDirectory() && file.getName().toLowerCase(Locale.ROOT).endsWith(".nomedia")) {
                MediaStore.scanFile(contentResolver, file.getParentFile());
            } else {
                MediaStore.scanFile(contentResolver, file);
            }
        }
    }

    @Override // android.provider.DocumentsProvider
    public void deleteDocument(String str) throws FileNotFoundException {
        File fileForDocId = getFileForDocId(str);
        File fileForDocId2 = getFileForDocId(str, true);
        if (fileForDocId.isDirectory()) {
            FileUtils.deleteContents(fileForDocId);
        }
        if (fileForDocId.exists() && !fileForDocId.delete()) {
            throw new IllegalStateException("Failed to delete " + fileForDocId);
        }
        onDocIdChanged(str);
        onDocIdDeleted(str, true);
        updateMediaStore(getContext(), fileForDocId2);
    }

    @Override // android.provider.DocumentsProvider
    public Cursor queryDocument(String str, String[] strArr) throws FileNotFoundException {
        MatrixCursor matrixCursor = new MatrixCursor(resolveProjection(strArr));
        includeFile(matrixCursor, str, null);
        return matrixCursor;
    }

    @Override // android.provider.DocumentsProvider
    public Cursor queryChildDocuments(String str, String[] strArr, String str2) throws FileNotFoundException {
        return queryChildDocuments(str, strArr, str2, false);
    }

    @Override // android.provider.DocumentsProvider
    public final Cursor queryChildDocumentsForManage(String str, String[] strArr, String str2) throws FileNotFoundException {
        return queryChildDocuments(str, strArr, str2, true);
    }

    protected Cursor queryChildDocuments(String str, String[] strArr, String str2, boolean z) throws FileNotFoundException {
        File fileForDocId = getFileForDocId(str);
        DirectoryCursor directoryCursor = new DirectoryCursor(resolveProjection(strArr), str, fileForDocId);
        if (!fileForDocId.isDirectory()) {
            Log.w(TAG, "\"" + str + "\" is not a directory");
            return directoryCursor;
        }
        if (!z && shouldHideDocument(str)) {
            Log.w(TAG, "Queried directory \"" + str + "\" is hidden");
            return directoryCursor;
        }
        File[] fileArrListFilesOrEmpty = FileUtils.listFilesOrEmpty(fileForDocId);
        for (File file : fileArrListFilesOrEmpty) {
            if (z || !shouldHideDocument(file)) {
                includeFile(directoryCursor, null, file);
            }
        }
        return directoryCursor;
    }

    protected final Cursor querySearchDocuments(File file, String[] strArr, Set<String> set, Bundle bundle) throws FileNotFoundException {
        MatrixCursor matrixCursor = new MatrixCursor(resolveProjection(strArr));
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.offer(file);
        while (!arrayDeque.isEmpty() && matrixCursor.getCount() < 23) {
            File file2 = (File) arrayDeque.poll();
            if (!shouldHideDocument(file2)) {
                if (file2.isDirectory()) {
                    for (File file3 : FileUtils.listFilesOrEmpty(file2)) {
                        arrayDeque.offer(file3);
                    }
                }
                if (!set.contains(file2.getAbsolutePath()) && matchSearchQueryArguments(file2, bundle)) {
                    includeFile(matrixCursor, null, file2);
                }
            }
        }
        String[] handledQueryArguments = DocumentsContract.getHandledQueryArguments(bundle);
        if (handledQueryArguments.length > 0) {
            Bundle bundle2 = new Bundle();
            bundle2.putStringArray(ContentResolver.EXTRA_HONORED_ARGS, handledQueryArguments);
            matrixCursor.setExtras(bundle2);
        }
        return matrixCursor;
    }

    @Override // android.provider.DocumentsProvider
    public String getDocumentType(String str) throws FileNotFoundException {
        return getDocumentType(str, getFileForDocId(str));
    }

    private String getDocumentType(String str, File file) throws FileNotFoundException {
        if (file.isDirectory()) {
            return DocumentsContract.Document.MIME_TYPE_DIR;
        }
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(iLastIndexOf + 1).toLowerCase());
            return mimeTypeFromExtension != null ? mimeTypeFromExtension : "application/octet-stream";
        }
        return "application/octet-stream";
    }

    @Override // android.provider.DocumentsProvider
    public ParcelFileDescriptor openDocument(final String str, String str2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        File fileForDocId = getFileForDocId(str);
        final File fileForDocId2 = getFileForDocId(str, true);
        int mode = ParcelFileDescriptor.parseMode(str2);
        if (fileForDocId2 == null) {
            return ParcelFileDescriptor.open(fileForDocId, mode);
        }
        if (mode == 268435456) {
            return openFileForRead(fileForDocId2);
        }
        try {
            return ParcelFileDescriptor.open(fileForDocId, mode, this.mHandler, new ParcelFileDescriptor.OnCloseListener() { // from class: com.android.internal.content.FileSystemProvider$$ExternalSyntheticLambda0
                @Override // android.os.ParcelFileDescriptor.OnCloseListener
                public final void onClose(IOException iOException) {
                    this.f$0.lambda$openDocument$0(str, fileForDocId2, iOException);
                }
            });
        } catch (IOException e) {
            throw new FileNotFoundException("Failed to open for writing: " + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDocument$0(String str, File file, IOException iOException) {
        onDocIdChanged(str);
        scanFile(file);
    }

    private ParcelFileDescriptor openFileForRead(File file) throws FileNotFoundException {
        Uri uriScanFile = MediaStore.scanFile(getContext().getContentResolver(), file);
        if (uriScanFile == null) {
            Log.w(TAG, "Failed to retrieve media store URI for: " + file);
            return ParcelFileDescriptor.open(file, 268435456);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("android.provider.extra.MEDIA_CAPABILITIES_UID", Binder.getCallingUid());
        AssetFileDescriptor assetFileDescriptorOpenTypedAssetFileDescriptor = getContext().getContentResolver().openTypedAssetFileDescriptor(uriScanFile, "*/*", bundle);
        if (assetFileDescriptorOpenTypedAssetFileDescriptor == null) {
            Log.w(TAG, "Failed to open with media_capabilities uid for URI: " + uriScanFile);
            return ParcelFileDescriptor.open(file, 268435456);
        }
        return assetFileDescriptorOpenTypedAssetFileDescriptor.getParcelFileDescriptor();
    }

    private boolean matchSearchQueryArguments(File file, Bundle bundle) {
        String mimeTypeFromExtension;
        if (file == null) {
            return false;
        }
        String name = file.getName();
        if (file.isDirectory()) {
            mimeTypeFromExtension = DocumentsContract.Document.MIME_TYPE_DIR;
        } else {
            int iLastIndexOf = name.lastIndexOf(46);
            if (iLastIndexOf < 0) {
                return false;
            }
            mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(iLastIndexOf + 1));
        }
        return DocumentsContract.matchSearchQueryArguments(bundle, name, mimeTypeFromExtension, file.lastModified(), file.length());
    }

    private void scanFile(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        getContext().sendBroadcast(intent);
    }

    @Override // android.provider.DocumentsProvider
    public AssetFileDescriptor openDocumentThumbnail(String str, Point point, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return DocumentsContract.openImageThumbnail(getFileForDocId(str));
    }

    protected MatrixCursor.RowBuilder includeFile(MatrixCursor matrixCursor, String str, File file) throws FileNotFoundException {
        String[] columnNames = matrixCursor.getColumnNames();
        MatrixCursor.RowBuilder rowBuilderNewRow = matrixCursor.newRow();
        if (str == null) {
            str = getDocIdForFile(file);
        } else {
            file = getFileForDocId(str);
        }
        String documentType = getDocumentType(str, file);
        rowBuilderNewRow.add("document_id", str);
        rowBuilderNewRow.add("mime_type", documentType);
        int iIndexOf = ArrayUtils.indexOf(columnNames, "flags");
        if (iIndexOf != -1) {
            boolean zEquals = documentType.equals(DocumentsContract.Document.MIME_TYPE_DIR);
            int i = file.canWrite() ? zEquals ? 332 : 326 : 0;
            if (zEquals && shouldBlockDirectoryFromTree(str)) {
                i |= 32768;
            }
            if (documentType.startsWith(MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                i |= 1;
            }
            if (typeSupportsMetadata(documentType)) {
                i |= 16384;
            }
            rowBuilderNewRow.add(iIndexOf, Integer.valueOf(i));
        }
        int iIndexOf2 = ArrayUtils.indexOf(columnNames, "_display_name");
        if (iIndexOf2 != -1) {
            rowBuilderNewRow.add(iIndexOf2, file.getName());
        }
        int iIndexOf3 = ArrayUtils.indexOf(columnNames, "last_modified");
        if (iIndexOf3 != -1) {
            long jLastModified = file.lastModified();
            if (jLastModified > 31536000000L) {
                rowBuilderNewRow.add(iIndexOf3, Long.valueOf(jLastModified));
            }
        }
        int iIndexOf4 = ArrayUtils.indexOf(columnNames, "_size");
        if (iIndexOf4 != -1) {
            rowBuilderNewRow.add(iIndexOf4, Long.valueOf(file.length()));
        }
        return rowBuilderNewRow;
    }

    protected final boolean shouldHideDocument(File file) throws FileNotFoundException {
        return shouldHideDocument(getDocIdForFile(file));
    }

    protected boolean typeSupportsMetadata(String str) {
        return MetadataReader.isSupportedMimeType(str) || DocumentsContract.Document.MIME_TYPE_DIR.equals(str);
    }

    protected final File getFileForDocId(String str) throws FileNotFoundException {
        return getFileForDocId(str, false);
    }

    private String[] resolveProjection(String[] strArr) {
        return strArr == null ? this.mDefaultProjection : strArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startObserving(File file, Uri uri, DirectoryCursor directoryCursor) {
        synchronized (this.mObservers) {
            DirectoryObserver directoryObserver = this.mObservers.get(file);
            if (directoryObserver == null) {
                directoryObserver = new DirectoryObserver(file, getContext().getContentResolver(), uri);
                directoryObserver.startWatching();
                this.mObservers.put(file, directoryObserver);
            }
            directoryObserver.mCursors.add(directoryCursor);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopObserving(File file, DirectoryCursor directoryCursor) {
        synchronized (this.mObservers) {
            DirectoryObserver directoryObserver = this.mObservers.get(file);
            if (directoryObserver == null) {
                return;
            }
            directoryObserver.mCursors.remove(directoryCursor);
            if (directoryObserver.mCursors.size() == 0) {
                this.mObservers.remove(file);
                directoryObserver.stopWatching();
            }
        }
    }

    private static class DirectoryObserver extends FileObserver {
        private static final int NOTIFY_EVENTS = 4044;
        private final CopyOnWriteArrayList<DirectoryCursor> mCursors;
        private final File mFile;
        private final Uri mNotifyUri;
        private final ContentResolver mResolver;

        DirectoryObserver(File file, ContentResolver contentResolver, Uri uri) {
            super(file.getAbsolutePath(), NOTIFY_EVENTS);
            this.mFile = file;
            this.mResolver = contentResolver;
            this.mNotifyUri = uri;
            this.mCursors = new CopyOnWriteArrayList<>();
        }

        @Override // android.os.FileObserver
        public void onEvent(int i, String str) {
            if ((i & NOTIFY_EVENTS) != 0) {
                Iterator<DirectoryCursor> it = this.mCursors.iterator();
                while (it.hasNext()) {
                    it.next().notifyChanged();
                }
                this.mResolver.notifyChange(this.mNotifyUri, (ContentObserver) null, false);
            }
        }

        public String toString() {
            return "DirectoryObserver{file=" + this.mFile.getAbsolutePath() + ", ref=" + this.mCursors.size() + "}";
        }
    }

    private class DirectoryCursor extends MatrixCursor {
        private final File mFile;

        public DirectoryCursor(String[] strArr, String str, File file) {
            super(strArr);
            Uri uriBuildNotificationUri = FileSystemProvider.this.buildNotificationUri(str);
            setNotificationUris(FileSystemProvider.this.getContext().getContentResolver(), Arrays.asList(uriBuildNotificationUri), FileSystemProvider.this.getContext().getContentResolver().getUserId(), false);
            this.mFile = file;
            FileSystemProvider.this.startObserving(file, uriBuildNotificationUri, this);
        }

        public void notifyChanged() {
            onChange(false);
        }

        @Override // android.database.AbstractCursor, android.database.Cursor, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            super.close();
            FileSystemProvider.this.stopObserving(this.mFile, this);
        }
    }
}
