package android.telephony.mbms;

import android.annotation.SystemApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.MbmsDownloadSession;
import android.telephony.mbms.vendor.VendorUtils;
import android.util.Log;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/* loaded from: classes4.dex */
public class MbmsDownloadReceiver extends BroadcastReceiver {
    public static final String DOWNLOAD_TOKEN_SUFFIX = ".download_token";
    private static final String EMBMS_INTENT_PERMISSION = "android.permission.SEND_EMBMS_INTENTS";
    private static final String LOG_TAG = "MbmsDownloadReceiver";
    private static final int MAX_TEMP_FILE_RETRIES = 5;
    public static final String MBMS_FILE_PROVIDER_META_DATA_KEY = "mbms-file-provider-authority";

    @SystemApi
    public static final int RESULT_APP_NOTIFICATION_ERROR = 6;

    @SystemApi
    public static final int RESULT_BAD_TEMP_FILE_ROOT = 3;

    @SystemApi
    public static final int RESULT_DOWNLOAD_FINALIZATION_ERROR = 4;

    @SystemApi
    public static final int RESULT_INVALID_ACTION = 1;

    @SystemApi
    public static final int RESULT_MALFORMED_INTENT = 2;

    @SystemApi
    public static final int RESULT_OK = 0;

    @SystemApi
    public static final int RESULT_TEMP_FILE_GENERATION_ERROR = 5;
    private static final String TEMP_FILE_STAGING_LOCATION = "staged_completed_files";
    private static final String TEMP_FILE_SUFFIX = ".embms.temp";
    private String mFileProviderAuthorityCache = null;
    private String mMiddlewarePackageNameCache = null;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws IOException {
        verifyPermissionIntegrity(context);
        if (!verifyIntentContents(context, intent)) {
            setResultCode(2);
            return;
        }
        if (!Objects.equals(intent.getStringExtra(VendorUtils.EXTRA_TEMP_FILE_ROOT), MbmsTempFileProvider.getEmbmsTempFileDir(context).getPath())) {
            setResultCode(3);
            return;
        }
        if (VendorUtils.ACTION_DOWNLOAD_RESULT_INTERNAL.equals(intent.getAction())) {
            moveDownloadedFile(context, intent);
            cleanupPostMove(context, intent);
        } else if (VendorUtils.ACTION_FILE_DESCRIPTOR_REQUEST.equals(intent.getAction())) {
            generateTempFiles(context, intent);
        } else if (VendorUtils.ACTION_CLEANUP.equals(intent.getAction())) {
            cleanupTempFiles(context, intent);
        } else {
            setResultCode(1);
        }
    }

    private boolean verifyIntentContents(Context context, Intent intent) {
        if (VendorUtils.ACTION_DOWNLOAD_RESULT_INTERNAL.equals(intent.getAction())) {
            if (!intent.hasExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT)) {
                Log.w(LOG_TAG, "Download result did not include a result code. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST)) {
                Log.w(LOG_TAG, "Download result did not include the associated request. Ignoring.");
                return false;
            }
            if (1 != intent.getIntExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT, 2)) {
                return true;
            }
            if (!intent.hasExtra(VendorUtils.EXTRA_TEMP_FILE_ROOT)) {
                Log.w(LOG_TAG, "Download result did not include the temp file root. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(MbmsDownloadSession.EXTRA_MBMS_FILE_INFO)) {
                Log.w(LOG_TAG, "Download result did not include the associated file info. Ignoring.");
                return false;
            }
            if (intent.hasExtra(VendorUtils.EXTRA_FINAL_URI)) {
                DownloadRequest downloadRequest = (DownloadRequest) intent.getParcelableExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, DownloadRequest.class);
                File file = new File(MbmsUtils.getEmbmsTempFileDirForService(context, downloadRequest.getFileServiceId()), downloadRequest.getHash() + DOWNLOAD_TOKEN_SUFFIX);
                if (!file.exists()) {
                    Log.w(LOG_TAG, "Supplied download request does not match a token that we have. Expected " + file);
                    return false;
                }
            } else {
                Log.w(LOG_TAG, "Download result did not include the path to the final temp file. Ignoring.");
                return false;
            }
        } else if (VendorUtils.ACTION_FILE_DESCRIPTOR_REQUEST.equals(intent.getAction())) {
            if (!intent.hasExtra(VendorUtils.EXTRA_SERVICE_ID)) {
                Log.w(LOG_TAG, "Temp file request did not include the associated service id. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(VendorUtils.EXTRA_TEMP_FILE_ROOT)) {
                Log.w(LOG_TAG, "Download result did not include the temp file root. Ignoring.");
                return false;
            }
        } else if (VendorUtils.ACTION_CLEANUP.equals(intent.getAction())) {
            if (!intent.hasExtra(VendorUtils.EXTRA_SERVICE_ID)) {
                Log.w(LOG_TAG, "Cleanup request did not include the associated service id. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(VendorUtils.EXTRA_TEMP_FILE_ROOT)) {
                Log.w(LOG_TAG, "Cleanup request did not include the temp file root. Ignoring.");
                return false;
            }
            if (!intent.hasExtra(VendorUtils.EXTRA_TEMP_FILES_IN_USE)) {
                Log.w(LOG_TAG, "Cleanup request did not include the list of temp files in use. Ignoring.");
                return false;
            }
        }
        return true;
    }

    private void moveDownloadedFile(Context context, Intent intent) {
        DownloadRequest downloadRequest = (DownloadRequest) intent.getParcelableExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, DownloadRequest.class);
        Intent intentForApp = downloadRequest.getIntentForApp();
        if (intentForApp == null) {
            Log.i(LOG_TAG, "Malformed app notification intent");
            setResultCode(6);
            return;
        }
        int intExtra = intent.getIntExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT, 2);
        intentForApp.putExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_RESULT, intExtra);
        intentForApp.putExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, downloadRequest);
        if (intExtra != 1) {
            Log.i(LOG_TAG, "Download request indicated a failed download. Aborting.");
            context.sendBroadcast(intentForApp);
            setResultCode(0);
            return;
        }
        Uri uri = (Uri) intent.getParcelableExtra(VendorUtils.EXTRA_FINAL_URI, Uri.class);
        if (!verifyTempFilePath(context, downloadRequest.getFileServiceId(), uri)) {
            Log.w(LOG_TAG, "Download result specified an invalid temp file " + uri);
            setResultCode(4);
            return;
        }
        FileInfo fileInfo = (FileInfo) intent.getParcelableExtra(MbmsDownloadSession.EXTRA_MBMS_FILE_INFO, FileInfo.class);
        try {
            intentForApp.putExtra(MbmsDownloadSession.EXTRA_MBMS_COMPLETED_FILE_URI, moveToFinalLocation(uri, FileSystems.getDefault().getPath(downloadRequest.getDestinationUri().getPath(), new String[0]), getFileRelativePath(downloadRequest.getSourceUri().getPath(), fileInfo.getUri().getPath())));
            intentForApp.putExtra(MbmsDownloadSession.EXTRA_MBMS_FILE_INFO, fileInfo);
            context.sendBroadcast(intentForApp);
            setResultCode(0);
        } catch (IOException unused) {
            Log.w(LOG_TAG, "Failed to move temp file to final destination");
            setResultCode(4);
        }
    }

    private void cleanupPostMove(Context context, Intent intent) {
        DownloadRequest downloadRequest = (DownloadRequest) intent.getParcelableExtra(MbmsDownloadSession.EXTRA_MBMS_DOWNLOAD_REQUEST, DownloadRequest.class);
        if (downloadRequest == null) {
            Log.w(LOG_TAG, "Intent does not include a DownloadRequest. Ignoring.");
            return;
        }
        ArrayList<Uri> parcelableArrayListExtra = intent.getParcelableArrayListExtra(VendorUtils.EXTRA_TEMP_LIST, Uri.class);
        if (parcelableArrayListExtra == null) {
            return;
        }
        for (Uri uri : parcelableArrayListExtra) {
            if (verifyTempFilePath(context, downloadRequest.getFileServiceId(), uri)) {
                File file = new File(uri.getSchemeSpecificPart());
                if (!file.delete()) {
                    Log.w(LOG_TAG, "Failed to delete temp file at " + file.getPath());
                }
            }
        }
    }

    private void generateTempFiles(Context context, Intent intent) throws IOException {
        String stringExtra = intent.getStringExtra(VendorUtils.EXTRA_SERVICE_ID);
        if (stringExtra == null) {
            Log.w(LOG_TAG, "Temp file request did not include the associated service id. Ignoring.");
            setResultCode(2);
            return;
        }
        int intExtra = intent.getIntExtra(VendorUtils.EXTRA_FD_COUNT, 0);
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(VendorUtils.EXTRA_PAUSED_LIST, Uri.class);
        if (intExtra == 0 && (parcelableArrayListExtra == null || parcelableArrayListExtra.size() == 0)) {
            Log.i(LOG_TAG, "No temp files actually requested. Ending.");
            setResultCode(0);
            setResultExtras(Bundle.EMPTY);
            return;
        }
        ArrayList<UriPathPair> arrayListGenerateFreshTempFiles = generateFreshTempFiles(context, stringExtra, intExtra);
        ArrayList<UriPathPair> arrayListGenerateUrisForPausedFiles = generateUrisForPausedFiles(context, stringExtra, parcelableArrayListExtra);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(VendorUtils.EXTRA_FREE_URI_LIST, arrayListGenerateFreshTempFiles);
        bundle.putParcelableArrayList(VendorUtils.EXTRA_PAUSED_URI_LIST, arrayListGenerateUrisForPausedFiles);
        setResultCode(0);
        setResultExtras(bundle);
    }

    private ArrayList<UriPathPair> generateFreshTempFiles(Context context, String str, int i) throws IOException {
        File embmsTempFileDirForService = MbmsUtils.getEmbmsTempFileDirForService(context, str);
        if (!embmsTempFileDirForService.exists()) {
            embmsTempFileDirForService.mkdirs();
        }
        ArrayList<UriPathPair> arrayList = new ArrayList<>(i);
        for (int i2 = 0; i2 < i; i2++) {
            File fileGenerateSingleTempFile = generateSingleTempFile(embmsTempFileDirForService);
            if (fileGenerateSingleTempFile == null) {
                setResultCode(5);
                Log.w(LOG_TAG, "Failed to generate a temp file. Moving on.");
            } else {
                Uri uriFromFile = Uri.fromFile(fileGenerateSingleTempFile);
                Uri uriForFile = MbmsTempFileProvider.getUriForFile(context, getFileProviderAuthorityCached(context), fileGenerateSingleTempFile);
                context.grantUriPermission(getMiddlewarePackageCached(context), uriForFile, 3);
                arrayList.add(new UriPathPair(uriFromFile, uriForFile));
            }
        }
        return arrayList;
    }

    private static File generateSingleTempFile(File file) {
        int i = 0;
        while (i < 5) {
            i++;
            File file2 = new File(file, UUID.randomUUID() + TEMP_FILE_SUFFIX);
            if (file2.createNewFile()) {
                return file2.getCanonicalFile();
            }
            continue;
        }
        return null;
    }

    private ArrayList<UriPathPair> generateUrisForPausedFiles(Context context, String str, List<Uri> list) throws IOException {
        if (list == null) {
            return new ArrayList<>(0);
        }
        ArrayList<UriPathPair> arrayList = new ArrayList<>(list.size());
        for (Uri uri : list) {
            if (!verifyTempFilePath(context, str, uri)) {
                Log.w(LOG_TAG, "Supplied file " + uri + " is not a valid temp file to resume");
                setResultCode(5);
            } else {
                File file = new File(uri.getSchemeSpecificPart());
                if (!file.exists()) {
                    Log.w(LOG_TAG, "Supplied file " + uri + " does not exist.");
                    setResultCode(5);
                } else {
                    Uri uriForFile = MbmsTempFileProvider.getUriForFile(context, getFileProviderAuthorityCached(context), file);
                    context.grantUriPermission(getMiddlewarePackageCached(context), uriForFile, 3);
                    arrayList.add(new UriPathPair(uri, uriForFile));
                }
            }
        }
        return arrayList;
    }

    private void cleanupTempFiles(Context context, Intent intent) {
        File embmsTempFileDirForService = MbmsUtils.getEmbmsTempFileDirForService(context, intent.getStringExtra(VendorUtils.EXTRA_SERVICE_ID));
        final ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(VendorUtils.EXTRA_TEMP_FILES_IN_USE, Uri.class);
        File[] fileArrListFiles = embmsTempFileDirForService.listFiles(new FileFilter(this) { // from class: android.telephony.mbms.MbmsDownloadReceiver.1
            @Override // java.io.FileFilter
            public boolean accept(File file) throws IOException {
                try {
                    if (!file.getCanonicalFile().getName().endsWith(MbmsDownloadReceiver.TEMP_FILE_SUFFIX)) {
                        return false;
                    }
                    return !parcelableArrayListExtra.contains(Uri.fromFile(r4));
                } catch (IOException unused) {
                    Log.w(MbmsDownloadReceiver.LOG_TAG, "Got IOException canonicalizing " + file + ", not deleting.");
                    return false;
                }
            }
        });
        for (File file : fileArrListFiles) {
            file.delete();
        }
    }

    private static Uri moveToFinalLocation(Uri uri, Path path, String str) throws IOException {
        if (!"file".equals(uri.getScheme())) {
            Log.w(LOG_TAG, "Downloaded file location uri " + uri + " does not have a file scheme");
            return null;
        }
        Path path2 = FileSystems.getDefault().getPath(uri.getPath(), new String[0]);
        Path pathResolve = path.resolve(str);
        if (!Files.isDirectory(pathResolve.getParent(), new LinkOption[0])) {
            Files.createDirectories(pathResolve.getParent(), new FileAttribute[0]);
        }
        return Uri.fromFile(Files.move(path2, pathResolve, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE).toFile());
    }

    public static String getFileRelativePath(String str, String str2) {
        if (str.endsWith("*")) {
            str = str.substring(0, str.lastIndexOf(47));
        }
        if (!str2.startsWith(str)) {
            Log.e(LOG_TAG, "File location specified in FileInfo does not match the source URI. source: " + str + " fileinfo path: " + str2);
            return null;
        }
        if (str2.length() == str.length()) {
            return str.substring(str.lastIndexOf(47) + 1);
        }
        String strSubstring = str2.substring(str.length());
        return strSubstring.startsWith("/") ? strSubstring.substring(1) : strSubstring;
    }

    private static boolean verifyTempFilePath(Context context, String str, Uri uri) {
        if (!"file".equals(uri.getScheme())) {
            Log.w(LOG_TAG, "Uri " + uri + " does not have a file scheme");
            return false;
        }
        String schemeSpecificPart = uri.getSchemeSpecificPart();
        File file = new File(schemeSpecificPart);
        if (!file.exists()) {
            Log.w(LOG_TAG, "File at " + schemeSpecificPart + " does not exist.");
            return false;
        }
        if (MbmsUtils.isContainedIn(MbmsUtils.getEmbmsTempFileDirForService(context, str), file)) {
            return true;
        }
        Log.w(LOG_TAG, "File at " + schemeSpecificPart + " is not contained in the temp file root, which is " + MbmsUtils.getEmbmsTempFileDirForService(context, str));
        return false;
    }

    private String getFileProviderAuthorityCached(Context context) {
        String str = this.mFileProviderAuthorityCache;
        if (str != null) {
            return str;
        }
        String fileProviderAuthority = getFileProviderAuthority(context);
        this.mFileProviderAuthorityCache = fileProviderAuthority;
        return fileProviderAuthority;
    }

    private static String getFileProviderAuthority(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null) {
                throw new RuntimeException("App must declare the file provider authority as metadata in the manifest.");
            }
            String string = applicationInfo.metaData.getString(MBMS_FILE_PROVIDER_META_DATA_KEY);
            if (string != null) {
                return string;
            }
            throw new RuntimeException("App must declare the file provider authority as metadata in the manifest.");
        } catch (PackageManager.NameNotFoundException unused) {
            throw new RuntimeException("Package manager couldn't find " + context.getPackageName());
        }
    }

    private String getMiddlewarePackageCached(Context context) {
        if (this.mMiddlewarePackageNameCache == null) {
            this.mMiddlewarePackageNameCache = MbmsUtils.getMiddlewareServiceInfo(context, MbmsDownloadSession.MBMS_DOWNLOAD_SERVICE_ACTION).packageName;
        }
        return this.mMiddlewarePackageNameCache;
    }

    private void verifyPermissionIntegrity(Context context) {
        List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(new Intent(context, (Class<?>) MbmsDownloadReceiver.class), 0);
        if (listQueryBroadcastReceivers.size() != 1) {
            throw new IllegalStateException("Non-unique download receiver in your app");
        }
        ActivityInfo activityInfo = listQueryBroadcastReceivers.get(0).activityInfo;
        if (activityInfo == null) {
            throw new IllegalStateException("Queried ResolveInfo does not contain a receiver");
        }
        if (MbmsUtils.getOverrideServiceName(context, MbmsDownloadSession.MBMS_DOWNLOAD_SERVICE_ACTION) != null) {
            if (activityInfo.permission == null) {
                throw new IllegalStateException("MbmsDownloadReceiver must require some permission");
            }
        } else if (!"android.permission.SEND_EMBMS_INTENTS".equals(activityInfo.permission)) {
            throw new IllegalStateException("MbmsDownloadReceiver must require the SEND_EMBMS_INTENTS permission.");
        }
    }
}
