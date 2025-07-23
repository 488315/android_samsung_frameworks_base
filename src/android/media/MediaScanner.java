package android.media;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;

@Deprecated
/* loaded from: classes2.dex */
public class MediaScanner implements AutoCloseable {

    @Deprecated
    private static final String[] FILES_PRESCAN_PROJECTION = new String[0];

    @Deprecated
    private final Uri mAudioUri;

    @Deprecated
    private final MyMediaScannerClient mClient = new MyMediaScannerClient(this);

    @Deprecated
    private final Context mContext;

    @Deprecated
    private String mDefaultAlarmAlertFilename;

    @Deprecated
    private String mDefaultNotificationFilename;

    @Deprecated
    private String mDefaultRingtoneFilename;

    @Deprecated
    private final Uri mFilesUri;

    @Deprecated
    private MediaInserter mMediaInserter;

    @Deprecated
    private final String mPackageName;

    private static class FileEntry {

        @Deprecated
        boolean mLastModifiedChanged;

        @Deprecated
        long mRowId;

        @Deprecated
        FileEntry(long j, String str, long j2, int i) {
            throw new UnsupportedOperationException();
        }
    }

    @Deprecated
    public MediaScanner(Context context, String str) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    private boolean isDrmEnabled() {
        throw new UnsupportedOperationException();
    }

    private class MyMediaScannerClient implements MediaScannerClient {

        @Deprecated
        private int mFileType;

        @Deprecated
        private boolean mIsDrm;

        @Deprecated
        private String mMimeType;

        @Deprecated
        private boolean mNoMedia;

        @Deprecated
        private String mPath;

        public MyMediaScannerClient(MediaScanner mediaScanner) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public FileEntry beginFile(String str, String str2, long j, long j2, boolean z, boolean z2) {
            throw new UnsupportedOperationException();
        }

        @Override // android.media.MediaScannerClient
        @Deprecated
        public void scanFile(String str, long j, long j2, boolean z, boolean z2) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public Uri doScanFile(String str, String str2, long j, long j2, boolean z, boolean z2, boolean z3) {
            throw new UnsupportedOperationException();
        }

        @Override // android.media.MediaScannerClient
        @Deprecated
        public void handleStringTag(String str, String str2) {
            throw new UnsupportedOperationException();
        }

        @Override // android.media.MediaScannerClient
        @Deprecated
        public void setMimeType(String str) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        private ContentValues toValues() {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        private Uri endFile(FileEntry fileEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) throws RemoteException {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        private int getFileTypeFromDrm(String str) {
            throw new UnsupportedOperationException();
        }
    }

    @Deprecated
    private void prescan(String str, boolean z) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    private void postscan(String[] strArr) throws RemoteException {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public Uri scanSingleFile(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static boolean isNoMediaPath(String str) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    FileEntry makeEntryFor(String str) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    private void setLocale(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        throw new UnsupportedOperationException();
    }
}
