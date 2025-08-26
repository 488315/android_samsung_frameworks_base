package android.telephony.mbms;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.telephony.MbmsDownloadSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/* loaded from: classes4.dex */
public class MbmsTempFileProvider extends ContentProvider {
    public static final String TEMP_FILE_ROOT_PREF_FILE_NAME = "MbmsTempFileRootPrefs";
    public static final String TEMP_FILE_ROOT_PREF_NAME = "mbms_temp_file_root";
    private String mAuthority;
    private Context mContext;

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new UnsupportedOperationException("No querying supported");
    }

    @Override // android.content.ContentProvider, android.content.ContentInterface
    public String getType(Uri uri) {
        return "application/octet-stream";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No inserting supported");
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("No deleting supported");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("No updating supported");
    }

    @Override // android.content.ContentProvider
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        return ParcelFileDescriptor.open(getFileForUri(this.mContext, this.mAuthority, uri), ParcelFileDescriptor.parseMode(str));
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grant uri permissions");
        }
        this.mAuthority = providerInfo.authority;
        this.mContext = context;
    }

    public static Uri getUriForFile(Context context, String str, File file) throws IOException {
        String strSubstring;
        try {
            String canonicalPath = file.getCanonicalPath();
            File embmsTempFileDir = getEmbmsTempFileDir(context);
            if (!MbmsUtils.isContainedIn(embmsTempFileDir, file)) {
                throw new IllegalArgumentException("File " + file + " is not contained in the temp file directory, which is " + embmsTempFileDir);
            }
            try {
                String canonicalPath2 = embmsTempFileDir.getCanonicalPath();
                if (canonicalPath2.endsWith("/")) {
                    strSubstring = canonicalPath.substring(canonicalPath2.length());
                } else {
                    strSubstring = canonicalPath.substring(canonicalPath2.length() + 1);
                }
                return new Uri.Builder().scheme("content").authority(str).encodedPath(Uri.encode(strSubstring)).build();
            } catch (IOException unused) {
                throw new RuntimeException("Could not get canonical path for temp file root dir " + embmsTempFileDir);
            }
        } catch (IOException unused2) {
            throw new IllegalArgumentException("Could not get canonical path for file " + file);
        }
    }

    public static File getFileForUri(Context context, String str, Uri uri) throws IOException {
        if (!"content".equals(uri.getScheme())) {
            throw new IllegalArgumentException("Uri must have scheme content");
        }
        if (!Objects.equals(str, uri.getAuthority())) {
            throw new IllegalArgumentException("Uri does not have a matching authority: " + str + ", " + uri.getAuthority());
        }
        String strDecode = Uri.decode(uri.getEncodedPath());
        try {
            File canonicalFile = getEmbmsTempFileDir(context).getCanonicalFile();
            File canonicalFile2 = new File(canonicalFile, strDecode).getCanonicalFile();
            if (canonicalFile2.getPath().startsWith(canonicalFile.getPath())) {
                return canonicalFile2;
            }
            throw new SecurityException("Resolved path jumped beyond configured root");
        } catch (IOException unused) {
            throw new FileNotFoundException("Could not resolve paths");
        }
    }

    public static File getEmbmsTempFileDir(Context context) {
        String string = context.getSharedPreferences(TEMP_FILE_ROOT_PREF_FILE_NAME, 0).getString(TEMP_FILE_ROOT_PREF_NAME, null);
        try {
            if (string != null) {
                return new File(string).getCanonicalFile();
            }
            return new File(context.getFilesDir(), MbmsDownloadSession.DEFAULT_TOP_LEVEL_TEMP_DIRECTORY).getCanonicalFile();
        } catch (IOException e) {
            throw new RuntimeException("Unable to canonicalize temp file root path " + e);
        }
    }
}
