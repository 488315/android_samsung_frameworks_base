package android.media;

import android.annotation.SystemApi;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.StaleDataException;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.R;
import com.android.internal.database.SortCursor;

import com.google.android.mms.ContentType;
import com.samsung.android.audio.Rune;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.common.AsProperty;
import com.samsung.android.wallpaperbackup.BnRConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public class RingtoneManager {
    public static final String ACTION_RINGTONE_PICKER = "android.intent.action.RINGTONE_PICKER";
    public static final String EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS =
            "android.intent.extra.ringtone.AUDIO_ATTRIBUTES_FLAGS";
    public static final String EXTRA_RINGTONE_DEFAULT_URI =
            "android.intent.extra.ringtone.DEFAULT_URI";
    public static final String EXTRA_RINGTONE_EXISTING_URI =
            "android.intent.extra.ringtone.EXISTING_URI";

    @Deprecated
    public static final String EXTRA_RINGTONE_INCLUDE_DRM =
            "android.intent.extra.ringtone.INCLUDE_DRM";

    public static final String EXTRA_RINGTONE_PICKED_URI =
            "android.intent.extra.ringtone.PICKED_URI";
    public static final String EXTRA_RINGTONE_SHOW_DEFAULT =
            "android.intent.extra.ringtone.SHOW_DEFAULT";
    public static final String EXTRA_RINGTONE_SHOW_SILENT =
            "android.intent.extra.ringtone.SHOW_SILENT";
    public static final String EXTRA_RINGTONE_TITLE = "android.intent.extra.ringtone.TITLE";
    public static final String EXTRA_RINGTONE_TYPE = "android.intent.extra.ringtone.TYPE";
    private static final String FILE_PATH = "path";
    protected static final String HIGHLIGHT_OFFSET = "highlight_offset";
    public static final int ID_COLUMN_INDEX = 0;
    public static final int SEM_TYPE_NOTIFICATION_SECOND = 256;
    public static final int SEM_TYPE_RINGTONE_SECOND = 128;
    private static final String TAG = "RingtoneManager";
    private static final String TITLE_CACHE = "title";
    public static final int TITLE_COLUMN_INDEX = 1;
    public static final int TYPE_ALARM = 4;
    public static final int TYPE_ALL = 7;
    public static final int TYPE_NOTIFICATION = 2;
    public static final int TYPE_RINGTONE = 1;
    public static final int TYPE_SYSTEM_SOUND = 512;
    public static final int URI_COLUMN_INDEX = 2;
    private final Activity mActivity;
    private final Context mContext;
    private Cursor mCursor;
    private final List<String> mFilterColumns;
    private boolean mIncludeParentRingtones;
    private Ringtone mPreviousRingtone;
    private boolean mStopPreviousRingtone;
    private int mType;
    private static final String[] INTERNAL_COLUMNS = {
        "_id",
        "title",
        "title",
        "title_key",
        "volume_name",
        "bucket_display_name",
        "is_ringtone",
        "is_notification",
        "is_alarm",
        "bookmark",
        "mime_type"
    };
    private static final String[] MEDIA_COLUMNS = {
        "_id",
        "title",
        "title",
        "title_key",
        "volume_name",
        "bucket_display_name",
        "is_ringtone",
        "is_notification",
        "is_alarm",
        "bookmark",
        "mime_type"
    };
    protected static String PREFIX_OPEN_THEME = "theme_";
    private static String OPEN_THEME_DIRECTORY = "/data/overlays/media/";
    private static Uri mDefaultRingtoneUri = null;
    private static Uri mDefaultRingtone2Uri = null;
    private static Uri mDefaultNotificationUri = null;
    private static Uri mDefaultNotification2Uri = null;
    private static Uri mDefaultAlarmUri = null;

    public RingtoneManager(Activity activity) {
        this(activity, false);
    }

    public RingtoneManager(Activity activity, boolean includeParentRingtones) {
        this.mType = 1;
        this.mFilterColumns = new ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = activity;
        this.mContext = activity;
        setType(this.mType);
        this.mIncludeParentRingtones = includeParentRingtones;
    }

    public RingtoneManager(Context context) {
        this(context, false);
    }

    public RingtoneManager(Context context, boolean includeParentRingtones) {
        this.mType = 1;
        this.mFilterColumns = new ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = null;
        this.mContext = context;
        setType(this.mType);
        this.mIncludeParentRingtones = includeParentRingtones;
    }

    public void setType(int type) {
        if (this.mCursor != null) {
            throw new IllegalStateException(
                    "Setting filter columns should be done before querying for ringtones.");
        }
        this.mType = type;
        if ((type & 133) != 0) {
            type |= 5;
        }
        setFilterColumnsList(type);
    }

    public int inferStreamType() {
        switch (this.mType) {
            case 128:
                return 2;
            case 256:
                return 5;
            default:
                switch (this.mType) {
                    case 2:
                        return 5;
                    case 3:
                    default:
                        return 2;
                    case 4:
                        return 4;
                }
        }
    }

    public void setStopPreviousRingtone(boolean stopPreviousRingtone) {
        this.mStopPreviousRingtone = stopPreviousRingtone;
    }

    public boolean getStopPreviousRingtone() {
        return this.mStopPreviousRingtone;
    }

    public void stopPreviousRingtone() {
        if (this.mPreviousRingtone != null) {
            this.mPreviousRingtone.stop();
        }
    }

    @Deprecated
    public boolean getIncludeDrm() {
        return false;
    }

    @Deprecated
    public void setIncludeDrm(boolean includeDrm) {
        if (includeDrm) {
            Log.w(TAG, "setIncludeDrm no longer supported");
        }
    }

    public Cursor getCursor() {
        Cursor parentRingtonesCursor;
        if (this.mCursor != null && this.mCursor.requery()) {
            return this.mCursor;
        }
        ArrayList<Cursor> ringtoneCursors = new ArrayList<>();
        ringtoneCursors.add(getInternalRingtones());
        ringtoneCursors.add(getMediaRingtones());
        Cursor themeCursor = getOpenThemeRingtone();
        if (themeCursor != null) {
            ringtoneCursors.add(themeCursor);
        }
        if (this.mIncludeParentRingtones
                && (parentRingtonesCursor = getParentProfileRingtones()) != null) {
            ringtoneCursors.add(parentRingtonesCursor);
        }
        SortCursor sortCursor =
                new SortCursor(
                        (Cursor[]) ringtoneCursors.toArray(new Cursor[ringtoneCursors.size()]),
                        "title_key");
        this.mCursor = sortCursor;
        return sortCursor;
    }

    private Cursor getParentProfileRingtones() {
        Context parentContext;
        UserManager um = UserManager.get(this.mContext);
        UserInfo parentInfo = um.getProfileParent(this.mContext.getUserId());
        if (parentInfo != null
                && parentInfo.id != this.mContext.getUserId()
                && (parentContext = createPackageContextAsUser(this.mContext, parentInfo.id))
                        != null) {
            Cursor res = getMediaRingtones(parentContext);
            return new ExternalRingtonesCursorWrapper(
                    res,
                    ContentProvider.maybeAddUserId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, parentInfo.id));
        }
        return null;
    }

    public Ringtone getRingtone(int position) {
        if (this.mStopPreviousRingtone && this.mPreviousRingtone != null) {
            this.mPreviousRingtone.stop();
        }
        this.mPreviousRingtone =
                getRingtone(this.mContext, getRingtoneUri(position), inferStreamType(), true);
        return this.mPreviousRingtone;
    }

    public Uri getRingtoneUri(int position) {
        try {
            if (this.mCursor != null) {
                if (this.mCursor.moveToPosition(position)) {
                    return getUriFromCursor(this.mContext, this.mCursor);
                }
            }
            return null;
        } catch (StaleDataException | IllegalStateException e) {
            Log.e(TAG, "Unexpected Exception has been catched.", e);
            return null;
        }
    }

    public static Uri getRingtoneUriForRestore(
            ContentResolver contentResolver, String value, int ringtoneType)
            throws FileNotFoundException, IllegalArgumentException {
        String ringtoneTypeSelection;
        if (value == null) {
            return null;
        }
        Uri canonicalUri = Uri.parse(value);
        Uri ringtoneUri = contentResolver.uncanonicalize(canonicalUri);
        if (ringtoneUri != null) {
            return contentResolver.canonicalize(ringtoneUri);
        }
        String title = canonicalUri.getQueryParameter("title");
        Uri baseUri = ContentUris.removeId(canonicalUri).buildUpon().clearQuery().build();
        switch (ringtoneType) {
            case 1:
                ringtoneTypeSelection = "is_ringtone";
                break;
            case 2:
                ringtoneTypeSelection = "is_notification";
                break;
            case 3:
            default:
                throw new IllegalArgumentException("Unknown ringtone type: " + ringtoneType);
            case 4:
                ringtoneTypeSelection = "is_alarm";
                break;
        }
        String selection = ringtoneTypeSelection + "=1 AND title=?";
        try {
            Cursor cursor =
                    contentResolver.query(
                            baseUri,
                            new String[] {"_id"},
                            selection,
                            new String[] {title},
                            null,
                            null);
            if (cursor == null) {
                throw new FileNotFoundException("Missing cursor for " + baseUri);
            }
            if (cursor.getCount() == 0) {
                FileUtils.closeQuietly(cursor);
                throw new FileNotFoundException("No item found for " + baseUri);
            }
            if (cursor.getCount() > 1) {
                int resultCount = cursor.getCount();
                FileUtils.closeQuietly(cursor);
                throw new FileNotFoundException(
                        "Find multiple ringtone candidates by title+ringtone_type query: count: "
                                + resultCount);
            }
            if (cursor.moveToFirst()) {
                Uri ringtoneUri2 = ContentUris.withAppendedId(baseUri, cursor.getLong(0));
                FileUtils.closeQuietly(cursor);
                Uri ringtoneUri3 = contentResolver.canonicalize(ringtoneUri2);
                Log.v(TAG, "Find a valid result: " + ringtoneUri3);
                return ringtoneUri3;
            }
            FileUtils.closeQuietly(cursor);
            throw new FileNotFoundException("Failed to read row from the result.");
        } catch (IllegalArgumentException e) {
            throw new FileNotFoundException("Volume not found for " + baseUri);
        }
    }

    private static Uri getUriFromCursor(Context context, Cursor cursor) {
        if (isOpenThemeRingtone(context, cursor)) {
            Uri uri =
                    ContentUris.withAppendedId(
                            MediaStore.Audio.Media.INTERNAL_CONTENT_URI, cursor.getLong(0));
            return uri;
        }
        Uri uri2 = ContentUris.withAppendedId(Uri.parse(cursor.getString(2)), cursor.getLong(0));
        return context.getContentResolver().canonicalizeOrElse(uri2);
    }

    public int getRingtonePosition(Uri ringtoneUri) {
        Cursor cursor;
        if (ringtoneUri == null) {
            return -1;
        }
        try {
            cursor = getCursor();
            cursor.moveToPosition(-1);
        } catch (NumberFormatException e) {
            Log.e(TAG, "NumberFormatException while getting ringtone position, returning -1", e);
        }
        if (!TextUtils.isEmpty(ringtoneUri.toString())
                && TextUtils.isDigitsOnly(ringtoneUri.getLastPathSegment())) {
            long ringtoneId = ContentUris.parseId(ringtoneUri);
            String ringtoneTitle = Ringtone.getTitle(this.mContext, ringtoneUri, false, false);
            Log.d(
                    TAG,
                    "getRingtonePosition uri :"
                            + ringtoneUri
                            + " / title : "
                            + ringtoneTitle
                            + " / id : "
                            + ringtoneId);
            while (cursor.moveToNext()) {
                if (ringtoneId == cursor.getLong(0) && ringtoneTitle.equals(cursor.getString(1))) {
                    return cursor.getPosition();
                }
            }
            return -1;
        }
        Log.e(TAG, "getRingtonePosition - filter invalid case " + ringtoneUri);
        return -1;
    }

    public static Uri getValidRingtoneUri(Context context) {
        RingtoneManager rm = new RingtoneManager(context);
        Uri uri = getValidRingtoneUriFromCursorAndClose(context, rm.getInternalRingtones());
        if (uri == null) {
            return getValidRingtoneUriFromCursorAndClose(context, rm.getMediaRingtones());
        }
        return uri;
    }

    private static Uri getValidRingtoneUriFromCursorAndClose(Context context, Cursor cursor) {
        if (cursor != null) {
            Uri uri = null;
            if (cursor.moveToFirst()) {
                uri = getUriFromCursor(context, cursor);
            }
            cursor.close();
            return uri;
        }
        return null;
    }

    private Cursor getInternalRingtones() {
        String whereClause = constructBooleanTrueWhereClause(this.mFilterColumns);
        Cursor res =
                query(
                        MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                        INTERNAL_COLUMNS,
                        (whereClause + excludedRingtonesWhereClauseForCSC())
                                + excludedRingtonesWhereClauseForOpenTheme(),
                        null,
                        "title_key");
        return new ExternalRingtonesCursorWrapper(res, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private Cursor getMediaRingtones() {
        Cursor res = getMediaRingtones(this.mContext);
        return new ExternalRingtonesCursorWrapper(res, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private Cursor getMediaRingtones(Context context) {
        return query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                MEDIA_COLUMNS,
                constructBooleanTrueWhereClause(this.mFilterColumns),
                null,
                "title_key",
                context);
    }

    private void setFilterColumnsList(int type) {
        List<String> columns = this.mFilterColumns;
        columns.clear();
        if ((type & 1) != 0 || (type & 128) != 0) {
            columns.add("is_ringtone");
        }
        if ((type & 2) != 0 || (type & 256) != 0) {
            columns.add("is_notification");
        }
        if ((type & 4) != 0) {
            columns.add("is_alarm");
        }
    }

    private static String constructBooleanTrueWhereClause(List<String> columns) {
        if (columns == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(NavigationBarInflaterView.KEY_CODE_START);
        for (int i = columns.size() - 1; i >= 0; i--) {
            sb.append(columns.get(i)).append("=1 or ");
        }
        int i2 = columns.size();
        if (i2 > 0) {
            sb.setLength(sb.length() - 4);
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        sb.append("AND (");
        sb.append("mime_type");
        sb.append(" NOT LIKE 'audio/x-ms-wma')");
        return sb.toString();
    }

    private Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        return query(uri, projection, selection, selectionArgs, sortOrder, this.mContext);
    }

    private Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder,
            Context context) {
        if (this.mActivity != null) {
            return this.mActivity.managedQuery(
                    uri, projection, selection, selectionArgs, sortOrder);
        }
        return context.getContentResolver()
                .query(uri, projection, selection, selectionArgs, sortOrder);
    }

    public static Ringtone getRingtone(Context context, Uri ringtoneUri) {
        return getRingtone(context, ringtoneUri, -1, true);
    }

    public static Ringtone getRingtone(
            Context context, Uri ringtoneUri, VolumeShaper.Configuration volumeShaperConfig) {
        return getRingtone(context, ringtoneUri, -1, volumeShaperConfig, true);
    }

    public static Ringtone getRingtone(
            Context context,
            Uri ringtoneUri,
            VolumeShaper.Configuration volumeShaperConfig,
            boolean createLocalMediaPlayer) {
        return getRingtone(context, ringtoneUri, -1, volumeShaperConfig, createLocalMediaPlayer);
    }

    public static Ringtone getRingtone(
            Context context,
            Uri ringtoneUri,
            VolumeShaper.Configuration volumeShaperConfig,
            AudioAttributes audioAttributes) {
        Ringtone ringtone = getRingtone(context, ringtoneUri, -1, volumeShaperConfig, false);
        if (ringtone != null) {
            ringtone.setAudioAttributesField(audioAttributes);
            if (!ringtone.createLocalMediaPlayer()) {
                Log.e(TAG, "Failed to open ringtone " + ringtoneUri);
                return null;
            }
        }
        return ringtone;
    }

    private static Ringtone getRingtone(
            Context context, Uri ringtoneUri, int streamType, boolean createLocalMediaPlayer) {
        return getRingtone(context, ringtoneUri, streamType, null, createLocalMediaPlayer);
    }

    private static Ringtone getRingtone(
            Context context,
            Uri ringtoneUri,
            int streamType,
            VolumeShaper.Configuration volumeShaperConfig,
            boolean createLocalMediaPlayer) {
        try {
            Ringtone r = new Ringtone(context, true);
            if (streamType >= 0) {
                r.setStreamType(streamType);
            }
            r.setVolumeShaperConfig(volumeShaperConfig);
            r.setUri(ringtoneUri, volumeShaperConfig);
            if (createLocalMediaPlayer && !r.createLocalMediaPlayer()) {
                Log.e(TAG, "Failed to open ringtone " + ringtoneUri);
                return null;
            }
            return r;
        } catch (Exception ex) {
            Log.e(TAG, "Failed to open ringtone " + ringtoneUri + ": " + ex);
            return null;
        }
    }

    public static Uri getActualDefaultRingtoneUri(Context context, int type) {
        Log.d(TAG, "getActualDefaultRingtoneUri  type    :" + type);
        if (!checkDefaultRingtoneProperUri(context, type)) {
            setRingtonesAsInitValue(context, type);
        }
        String setting = getSettingForType(type);
        if (setting == null) {
            return null;
        }
        String uriString =
                Settings.System.getStringForUser(
                        context.getContentResolver(), setting, context.getUserId());
        Uri ringtoneUri = uriString != null ? Uri.parse(uriString) : null;
        int cpUserid = ContentProvider.getUserIdFromUri(ringtoneUri);
        int ctUserid = context.getUserId();
        if (ringtoneUri == null) {
            return ringtoneUri;
        }
        if ((cpUserid == 0 || ctUserid == 0) && cpUserid == ctUserid) {
            return ContentProvider.getUriWithoutUserId(ringtoneUri);
        }
        return ringtoneUri;
    }

    public static void setActualDefaultRingtoneUri(Context context, int type, Uri ringtoneUri) {
        String setting = getSettingForType(type);
        if (setting == null) {
            return;
        }
        ContentResolver resolver = context.getContentResolver();
        if (AsPackageName.CSC.equals(context.getPackageName()) && context.getUserId() != 0) {
            Log.e(TAG, "setActualDefaultRingtoneUri userId is in sub user from CscService");
            return;
        }
        if (isDefault(ringtoneUri)
                || (isMediaProviderUri(ringtoneUri) && ContentUris.parseId(ringtoneUri) < 0)) {
            Log.i(TAG, "Invalid uri type : " + ringtoneUri);
            return;
        }
        Settings.System.putStringForUser(
                resolver, getSettingKeyForAbsolutePath(type), null, context.getUserId());
        Uri ringtoneUri2 = maybeAddUserId(ringtoneUri, context.getUserId());
        if (ringtoneUri2 != null) {
            String mimeType = resolver.getType(ringtoneUri2);
            if (mimeType == null) {
                Log.e(
                        TAG,
                        "setActualDefaultRingtoneUri for URI:"
                                + ringtoneUri2
                                + " ignored: failure to find mimeType (no access from this"
                                + " context?)");
                return;
            } else if (!mimeType.startsWith("audio/")
                    && !mimeType.equals("application/ogg")
                    && !mimeType.equals(ContentType.AUDIO_X_FLAC)
                    && !mimeType.startsWith(BnRConstants.VIDEO_DIR_PATH)
                    && !mimeType.equals("application/mp4")) {
                Log.e(
                        TAG,
                        "setActualDefaultRingtoneUri for URI:"
                                + ringtoneUri2
                                + " ignored: associated MIME type:"
                                + mimeType
                                + " is not a recognized audio or video type");
                return;
            }
        }
        Settings.System.putStringForUser(
                resolver,
                setting,
                ringtoneUri2 != null ? ringtoneUri2.toString() : null,
                context.getUserId());
        logCallStackDetails(context, type, ringtoneUri2);
        if (ringtoneUri2 != null) {
            saveAbsolutePath(context, type, ringtoneUri2);
        }
        if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE) {
            turnOffSyncHapticOnCscSounds(context, ringtoneUri2, setting);
        }
        int enabledSim2Only =
                Settings.System.getInt(resolver, Settings.System.ENABLED_SIM2_ONLY, 0);
        Log.d(TAG, "setActualDefaultRingtoneUri :: enabled sim2 only =  " + enabledSim2Only);
        if (enabledSim2Only == 1) {
            int typeforSync = 128;
            if (type == 128) {
                typeforSync = 1;
            } else if (type == 2) {
                typeforSync = 256;
            } else if (type == 256) {
                typeforSync = 2;
            }
            String settingForSync = getSettingForType(typeforSync);
            Settings.System.putStringForUser(
                    resolver,
                    settingForSync,
                    ringtoneUri2 != null ? ringtoneUri2.toString() : null,
                    context.getUserId());
            logCallStackDetails(context, typeforSync, ringtoneUri2);
            if (ringtoneUri2 != null) {
                saveAbsolutePath(context, typeforSync, ringtoneUri2);
            }
            if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE) {
                turnOffSyncHapticOnCscSounds(context, ringtoneUri2, settingForSync);
            }
        }
    }

    public static boolean isInternalRingtoneUri(Uri uri) {
        return isRingtoneUriInStorage(uri, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private static boolean isExternalRingtoneUri(Uri uri) {
        return isRingtoneUriInStorage(uri, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private static boolean isRingtoneUriInStorage(Uri ringtone, Uri storage) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(ringtone);
        if (uriWithoutUserId == null) {
            return false;
        }
        return uriWithoutUserId.toString().startsWith(storage.toString());
    }

    public Uri addCustomExternalRingtone(Uri fileUri, int type)
            throws FileNotFoundException, IllegalArgumentException, IOException {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("External storage is not mounted. Unable to install ringtones.");
        }
        String mimeType = this.mContext.getContentResolver().getType(fileUri);
        if (mimeType == null
                || (!mimeType.startsWith("audio/")
                        && !mimeType.equals("application/ogg")
                        && !mimeType.contains("audio/"))) {
            throw new IllegalArgumentException(
                    "Ringtone file must have MIME type \"audio/*\". Given file has MIME type \""
                            + mimeType
                            + "\"");
        }
        String subdirectory = getExternalDirectoryForType(type);
        File outFile =
                Utils.getUniqueExternalFile(
                        this.mContext,
                        subdirectory,
                        FileUtils.buildValidFatFilename(
                                Utils.getFileDisplayNameFromUri(this.mContext, fileUri)),
                        mimeType);
        InputStream input = this.mContext.getContentResolver().openInputStream(fileUri);
        try {
            OutputStream output = new FileOutputStream(outFile);
            try {
                FileUtils.copy(input, output);
                output.close();
                if (input != null) {
                    input.close();
                }
                return MediaStore.scanFile(this.mContext.getContentResolver(), outFile);
            } finally {
            }
        } catch (Throwable th) {
            if (input != null) {
                try {
                    input.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static final String getExternalDirectoryForType(int type) {
        switch (type) {
            case 1:
            case 128:
                return Environment.DIRECTORY_RINGTONES;
            case 2:
            case 256:
                return Environment.DIRECTORY_NOTIFICATIONS;
            case 4:
                return Environment.DIRECTORY_ALARMS;
            default:
                throw new IllegalArgumentException("Unsupported ringtone type: " + type);
        }
    }

    private static String getSettingForType(int type) {
        if ((type & 1) != 0) {
            return Settings.System.RINGTONE;
        }
        if ((type & 2) != 0) {
            return Settings.System.NOTIFICATION_SOUND;
        }
        if ((type & 4) != 0) {
            return Settings.System.ALARM_ALERT;
        }
        if ((type & 128) != 0) {
            return Settings.System.RINGTONE_2;
        }
        if ((type & 256) != 0) {
            return Settings.System.NOTIFICATION_SOUND_2;
        }
        return null;
    }

    public static Uri getCacheForType(int type) {
        return getCacheForType(type, UserHandle.getCallingUserId());
    }

    public static Uri getCacheForType(int type, int userId) {
        if ((type & 1) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.RINGTONE_CACHE_URI, userId);
        }
        if ((type & 2) != 0) {
            return ContentProvider.maybeAddUserId(
                    Settings.System.NOTIFICATION_SOUND_CACHE_URI, userId);
        }
        if ((type & 4) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.ALARM_ALERT_CACHE_URI, userId);
        }
        if ((type & 128) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.RINGTONE2_CACHE_URI, userId);
        }
        if ((type & 256) != 0) {
            return ContentProvider.maybeAddUserId(
                    Settings.System.NOTIFICATION_SOUND2_CACHE_URI, userId);
        }
        return null;
    }

    public static boolean isDefault(Uri ringtoneUri) {
        return getDefaultType(ringtoneUri) != -1;
    }

    public static int getDefaultType(Uri defaultRingtoneUri) {
        Uri defaultRingtoneUri2 = ContentProvider.getUriWithoutUserId(defaultRingtoneUri);
        if (defaultRingtoneUri2 == null) {
            return -1;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_RINGTONE_URI)) {
            return 1;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_NOTIFICATION_URI)) {
            return 2;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_ALARM_ALERT_URI)) {
            return 4;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_RINGTONE_URI_2)) {
            return 128;
        }
        if (defaultRingtoneUri2.equals(Settings.System.DEFAULT_RINGTONE_URI_3)) {
            return 1;
        }
        if (!defaultRingtoneUri2.equals(Settings.System.DEFAULT_NOTIFICATION_URI_2)) {
            return -1;
        }
        return 256;
    }

    public static Uri getDefaultUri(int type) {
        if ((type & 1) != 0) {
            return Settings.System.DEFAULT_RINGTONE_URI;
        }
        if ((type & 2) != 0) {
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
        if ((type & 4) != 0) {
            return Settings.System.DEFAULT_ALARM_ALERT_URI;
        }
        if ((type & 128) != 0) {
            return Settings.System.DEFAULT_RINGTONE_URI_2;
        }
        if ((type & 256) != 0) {
            return Settings.System.DEFAULT_NOTIFICATION_URI_2;
        }
        return null;
    }

    public static AssetFileDescriptor openDefaultRingtoneUri(Context context, Uri uri)
            throws FileNotFoundException {
        int type = getDefaultType(uri);
        Uri cacheUri = getCacheForType(type, context.getUserId());
        Uri actualUri = getActualDefaultRingtoneUri(context, type);
        ContentResolver resolver = context.getContentResolver();
        AssetFileDescriptor afd = null;
        if (cacheUri != null && (afd = resolver.openAssetFileDescriptor(cacheUri, "r")) != null) {
            return afd;
        }
        if (actualUri != null) {
            AssetFileDescriptor afd2 = resolver.openAssetFileDescriptor(actualUri, "r");
            return afd2;
        }
        return afd;
    }

    public boolean hasHapticChannels(int position) {
        return AudioManager.hasHapticChannels(this.mContext, getRingtoneUri(position));
    }

    public static boolean hasHapticChannels(Uri ringtoneUri) {
        return AudioManager.hasHapticChannels(null, ringtoneUri);
    }

    public static boolean hasHapticChannels(Context context, Uri ringtoneUri) {
        return AudioManager.hasHapticChannels(context, ringtoneUri);
    }

    private static Context createPackageContextAsUser(Context context, int userId) {
        try {
            return context.createPackageContextAsUser(
                    context.getPackageName(), 0, UserHandle.of(userId));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to create package context", e);
            return null;
        }
    }

    @SystemApi
    public static void ensureDefaultRingtones(Context context) {
        Uri ringtoneUri;
        int[] iArr = {1, 2, 4, 128, 256};
        for (int i = 0; i < 5; i++) {
            int type = iArr[i];
            String setting = getDefaultRingtoneSetting(type);
            if (Settings.System.getInt(context.getContentResolver(), setting, 0) == 0
                    && (ringtoneUri = computeDefaultRingtoneUri(context, type)) != null) {
                setActualDefaultRingtoneUri(context, type, ringtoneUri);
                Settings.System.putInt(context.getContentResolver(), setting, 1);
            }
        }
    }

    private static Uri computeDefaultRingtoneUri(Context context, int type) {
        String filename = getDefaultRingtoneFilename(type);
        String whichAudio = getQueryStringForType(type);
        String where = "_display_name=? AND " + whichAudio + "=?";
        Uri baseUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        Cursor cursor =
                context.getContentResolver()
                        .query(
                                baseUri,
                                new String[] {"_id"},
                                where,
                                new String[] {filename, "1"},
                                null);
        try {
            if (cursor.moveToFirst()) {
                Uri ringtoneUri =
                        context.getContentResolver()
                                .canonicalizeOrElse(
                                        ContentUris.withAppendedId(baseUri, cursor.getLong(0)));
                if (cursor != null) {
                    cursor.close();
                }
                return ringtoneUri;
            }
            if (cursor != null) {
                cursor.close();
                return null;
            }
            return null;
        } catch (Throwable th) {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static String getDefaultRingtoneSetting(int type) {
        switch (type) {
            case 1:
                return "ringtone_set";
            case 2:
                return "notification_sound_set";
            case 4:
                return "alarm_alert_set";
            case 128:
                return "ringtone_2_set";
            case 256:
                return "notification_sound_2_set";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String getDefaultRingtoneFilename(int type) {
        switch (type) {
            case 1:
                return SystemProperties.get(AsProperty.PROP_CFG_RINGTONE);
            case 2:
                return SystemProperties.get(AsProperty.PROP_CFG_NOTIFICATION_SOUND);
            case 4:
                return SystemProperties.get(AsProperty.PROP_CFG_ALARM_ALERT);
            case 128:
                return SystemProperties.get(AsProperty.PROP_CFG_RINGTONE2);
            case 256:
                return SystemProperties.get(AsProperty.PROP_CFG_NOTIFICATION_SOUND2);
            default:
                throw new IllegalArgumentException();
        }
    }

    private static String getQueryStringForType(int type) {
        switch (type) {
            case 1:
                return "is_ringtone";
            case 2:
                return "is_notification";
            case 4:
                return "is_ringtone";
            case 128:
                return "is_ringtone";
            case 256:
                return "is_notification";
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Ringtone getRingtone(
            Context context, Uri ringtoneUri, int streamType, int seek) {
        try {
            Ringtone r = new Ringtone(context, true);
            if (streamType >= 0) {
                r.setStreamType(streamType);
            }
            if (seek >= 0) {
                r.setSecForSeek(seek);
            }
            r.setUri(ringtoneUri);
            return r;
        } catch (Exception ex) {
            Log.e(TAG, "Failed to open ringtone " + ringtoneUri + ": " + ex);
            return null;
        }
    }

    private static void saveAbsolutePath(Context context, int type, Uri ringtoneUri) {
        Log.i(TAG, "Save path type :" + type + ", URI : " + ringtoneUri);
        ContentResolver resolver = context.getContentResolver();
        try {
            Cursor cs = resolver.query(ringtoneUri, new String[] {"_data"}, null, null, null);
            if (cs != null) {
                try {
                    if (cs.getCount() != 0) {
                        cs.moveToFirst();
                        int index = cs.getColumnIndexOrThrow("_data");
                        String path = cs.getString(index);
                        Log.i(TAG, "ringtone path: " + path);
                        Uri absolutePath =
                                new Uri.Builder().appendQueryParameter("path", path).build();
                        Settings.System.putStringForUser(
                                resolver,
                                getSettingKeyForAbsolutePath(type),
                                absolutePath.toString(),
                                context.getUserId());
                        if (cs != null) {
                            cs.close();
                            return;
                        }
                        return;
                    }
                } finally {
                }
            }
            Log.e(TAG, "cannot find the " + ringtoneUri);
            if (cs != null) {
                cs.close();
            }
        } catch (Exception e) {
            Log.i(TAG, "saveAbsolutePath " + e);
        }
    }

    public static void setRingtonesAsInitValue(Context context, int type) {
        if ((type & 1) != 0) {
            mDefaultRingtoneUri = null;
        } else if ((type & 128) != 0) {
            mDefaultRingtone2Uri = null;
        } else if ((type & 2) != 0) {
            mDefaultNotificationUri = null;
        } else if ((type & 256) != 0) {
            mDefaultNotification2Uri = null;
        } else if ((type & 4) != 0) {
            mDefaultAlarmUri = null;
        }
        Uri defaultUri = getDefaultSoundUri(context, type);
        if (defaultUri == null) {
            return;
        }
        setActualDefaultRingtoneUri(
                context, type, context.getContentResolver().canonicalizeOrElse(defaultUri));
    }

    public static String getSettingKeyForAbsolutePath(int type) {
        switch (type) {
            case 1:
                return Settings.System.RINGTONE + "_CONSTANT_PATH";
            case 2:
                return Settings.System.NOTIFICATION_SOUND + "_CONSTANT_PATH";
            case 4:
                return Settings.System.ALARM_ALERT + "_CONSTANT_PATH";
            case 128:
                return Settings.System.RINGTONE_2 + "_CONSTANT_PATH";
            case 256:
                return Settings.System.NOTIFICATION_SOUND_2 + "_CONSTANT_PATH";
            default:
                return null;
        }
    }

    public static String getRingtoneTitleForCached(Context context, int type) {
        String setting = getSettingForType(type);
        if (setting == null) {
            return context.getString(R.string.ringtone_unknown);
        }
        String ringtone =
                Settings.System.getStringForUser(
                        context.getContentResolver(), setting, context.getUserId());
        if (ringtone == null) {
            return context.getString(R.string.sec_ringtone_silent);
        }
        String title = getQueryParameter(Uri.parse(ringtone), "title");
        if (title != null) {
            return title;
        }
        return context.getString(R.string.ringtone_unknown);
    }

    public static String semGetDefaultRingtoneTitle(Context context, int type) {
        return getRingtoneTitleForCached(context, type);
    }

    private static Uri getDefaultSettingSound(int type) {
        if ((type & 1) != 0) {
            return mDefaultRingtoneUri;
        }
        if ((type & 128) != 0) {
            return mDefaultRingtone2Uri;
        }
        if ((type & 2) != 0) {
            return mDefaultNotificationUri;
        }
        if ((type & 256) != 0) {
            return mDefaultNotification2Uri;
        }
        if ((type & 4) != 0) {
            return mDefaultAlarmUri;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0177, code lost:

       if (r3 != null) goto L77;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0179, code lost:

       r3.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0189, code lost:

       return r5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0186, code lost:

       if (r3 == null) goto L84;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.net.Uri getDefaultSoundUri(android.content.Context r14, int r15) {
        /*
            Method dump skipped, instructions count: 401
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.media.RingtoneManager.getDefaultSoundUri(android.content.Context,"
                    + " int):android.net.Uri");
    }

    private static boolean checkDefaultRingtoneProperUri(Context context, int type) {
        String settingValue;
        String settingKey = getSettingForType(type);
        String settingKeyPath = getSettingKeyForAbsolutePath(type);
        if (settingKey == null
                || settingKeyPath == null
                || (settingValue =
                                Settings.System.getStringForUser(
                                        context.getContentResolver(),
                                        settingKey,
                                        context.getUserId()))
                        == null) {
            return true;
        }
        Log.i(TAG, "Ringtone value : " + settingValue);
        Uri ringtoneUri = Uri.parse(settingValue);
        if (!isMediaProviderUri(ringtoneUri) || isInternalRingtoneUri(ringtoneUri)) {
            return true;
        }
        if (ContentUris.parseId(ringtoneUri) <= 0) {
            return false;
        }
        String ringtonePath =
                Settings.System.getStringForUser(
                        context.getContentResolver(), settingKeyPath, context.getUserId());
        if (ringtonePath == null) {
            Log.w(TAG, "Ringtone path is null");
            return true;
        }
        String absolutePath = getQueryParameter(Uri.parse(ringtonePath), "path");
        ContentResolver resolver = context.getContentResolver();
        String[] projection = {"_id"};
        try {
            Cursor cs =
                    resolver.query(
                            ringtoneUri, projection, "_data=?", new String[] {absolutePath}, null);
            if (cs != null) {
                try {
                    if (cs.getCount() > 0) {
                        Log.i(TAG, "path and URI match to each other ");
                        if (cs != null) {
                            cs.close();
                        }
                        return true;
                    }
                } finally {
                }
            }
            if (cs != null) {
                cs.close();
            }
            Log.w(TAG, "path and URI don't match");
            return false;
        } catch (Exception e) {
            Log.i(TAG, "checkDefaultRingtoneProperUri : " + e);
            return true;
        }
    }

    protected static boolean isMediaProviderUri(Uri uri) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        return uriWithoutUserId != null
                && uriWithoutUserId.toString().startsWith(MediaStore.AUTHORITY_URI.toString());
    }

    public static Ringtone semGetRingtone(Context context, int mSec, Uri ringtoneUri) {
        try {
            Ringtone r = new Ringtone(context, true);
            if (mSec >= 0) {
                r.setSecForSeek(mSec);
            }
            r.setUri(ringtoneUri);
            return r;
        } catch (Exception ex) {
            Log.e(TAG, "Failed to open ringtone " + ringtoneUri + ": " + ex);
            return null;
        }
    }

    public Ringtone semGetRingtone(int position, int seek) {
        if (this.mStopPreviousRingtone && this.mPreviousRingtone != null) {
            this.mPreviousRingtone.stop();
        }
        this.mPreviousRingtone =
                getRingtone(this.mContext, getRingtoneUri(position), inferStreamType(), seek);
        return this.mPreviousRingtone;
    }

    private List<String> getExcludedRingtoneTitles() {
        List<String> excludeRingtones = Collections.emptyList();
        IBinder b = ServiceManager.getService("audio");
        IAudioService service = IAudioService.Stub.asInterface(b);
        try {
            List<String> excludeRingtones2 =
                    service.getExcludedRingtoneTitles((this.mType & 258) != 0 ? 2 : 1);
            return excludeRingtones2;
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to get excluded ringtones.");
            return excludeRingtones;
        }
    }

    private String excludedRingtonesWhereClauseForCSC() {
        List<String> excludedRingtones = getExcludedRingtoneTitles();
        if (excludedRingtones.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" and (");
        for (String displayName : excludedRingtones) {
            sb.append("_display_name");
            sb.append("!=");
            sb.append("'" + displayName + "'");
            sb.append(" and ");
        }
        sb.setLength(sb.length() - 5);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static String getOMCRingtonePropertyName(int type) {
        if (type == 1) {
            return AsProperty.PROP_CFG_OMC_RINGTONE;
        }
        if (type == 128) {
            return AsProperty.PROP_CFG_OMC_RINGTONE2;
        }
        if (type == 2) {
            return AsProperty.PROP_CFG_OMC_NOTIFICATION_SOUND;
        }
        if (type == 256) {
            return AsProperty.PROP_CFG_OMC_NOTIFICATION_SOUND2;
        }
        if (type != 4) {
            return "";
        }
        return AsProperty.PROP_CFG_OMC_ALARM_ALERT;
    }

    private static String getQueryParameter(Uri uri, String key) {
        if (uri == null || key == null) {
            return null;
        }
        try {
            return uri.getQueryParameter(key);
        } catch (UnsupportedOperationException e) {
            return null;
        }
    }

    private static final String hidden_EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS() {
        return EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS;
    }

    private static void logCallStackDetails(Context context, int type, Uri uri) {
        StringBuilder builder = new StringBuilder(context.getPackageName());
        builder.append(" uid/pid: ")
                .append(Binder.getCallingUid())
                .append("/")
                .append(Binder.getCallingPid())
                .append(" type: ")
                .append(type)
                .append(" user: ")
                .append(context.getUserId())
                .append(" uri: ")
                .append(uri);
        IBinder b = ServiceManager.getService("audio");
        IAudioService service = IAudioService.Stub.asInterface(b);
        try {
            service.recordRingtoneChanger(builder.toString());
        } catch (RemoteException e) {
            Log.e(TAG, "Unable to dumpCallStack.");
        }
    }

    public Uri addCustomRingtone(Uri fileUri, int type)
            throws FileNotFoundException, IllegalArgumentException, IOException {
        return addCustomExternalRingtone(fileUri, type);
    }

    private Cursor getOpenThemeRingtone() {
        String column;
        MatrixCursor themeCursor = null;
        String themeTitle = this.mContext.getString(R.string.sec_ringtone_category_open_theme);
        if (this.mType == 2 || this.mType == 256) {
            column = "is_notification";
        } else if (this.mType == 4) {
            column = "is_alarm";
        } else {
            column = "is_ringtone";
        }
        String dbWhere = "(_display_name like '" + PREFIX_OPEN_THEME + "%') and " + column + "=1";
        try {
            Cursor tempCursor =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                                    INTERNAL_COLUMNS,
                                    dbWhere,
                                    null,
                                    null);
            if (tempCursor != null) {
                try {
                    if (tempCursor.getCount() != 0) {
                        tempCursor.moveToFirst();
                        themeCursor = new MatrixCursor(INTERNAL_COLUMNS);
                        String[] themeColumns = new String[INTERNAL_COLUMNS.length];
                        for (int i = 0; i < INTERNAL_COLUMNS.length; i++) {
                            if (i == 1) {
                                themeColumns[i] = themeTitle;
                            } else {
                                themeColumns[i] = tempCursor.getString(i);
                            }
                        }
                        themeCursor.addRow(themeColumns);
                    }
                } finally {
                }
            }
            if (tempCursor != null) {
                tempCursor.close();
            }
        } catch (Exception ex) {
            Log.e(TAG, "DB exception", ex);
        }
        if (themeCursor != null) {
            return new ExternalRingtonesCursorWrapper(
                    themeCursor, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
        }
        return null;
    }

    private static boolean isOpenThemeRingtone(Context context, Cursor cursor) {
        String themeTitle = context.getString(R.string.sec_ringtone_category_open_theme);
        return cursor.getString(2).startsWith(PREFIX_OPEN_THEME)
                || cursor.getString(1).equals(themeTitle);
    }

    public static boolean shouldMigrationThemeSoundFile(Context context, int type) {
        String settingKeyPath = getSettingKeyForAbsolutePath(type);
        if (!TextUtils.isEmpty(settingKeyPath)) {
            String ringtonePath =
                    Settings.System.getStringForUser(
                            context.getContentResolver(), settingKeyPath, context.getUserId());
            if (!TextUtils.isEmpty(ringtonePath)) {
                String absolutePath = getQueryParameter(Uri.parse(ringtonePath), "path");
                Log.i(TAG, "shouldMigrationThemeSoundFile absolutePath : " + absolutePath);
                if (!TextUtils.isEmpty(absolutePath)
                        && absolutePath.startsWith(OPEN_THEME_DIRECTORY)
                        && !absolutePath.contains(PREFIX_OPEN_THEME)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private String excludedRingtonesWhereClauseForOpenTheme() {
        StringBuilder sb = new StringBuilder();
        sb.append(" AND (");
        sb.append("_display_name");
        sb.append(" not like '" + PREFIX_OPEN_THEME + "%')");
        return sb.toString();
    }

    private static Context getContextForUser(Context context, UserHandle user) {
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, user);
        } catch (PackageManager.NameNotFoundException e) {
            return context;
        }
    }

    private static boolean isAchAvailable(Context context) {
        boolean hapticPlaybackSupported = AudioManager.isCurrentHapticPlaybackSupported(false);
        boolean syncWithNotiSettings =
                Settings.System.getInt(
                                context.getContentResolver(),
                                Settings.System.SYNC_VIBRATION_WITH_NOTIFICATION,
                                1)
                        != 0;
        return hapticPlaybackSupported && syncWithNotiSettings;
    }

    public static Uri getActualAchRingtoneUriIfAvailable(
            Context context, Uri uri, UserHandle user) {
        if (UserHandle.ALL.equals(user)) {
            user = UserHandle.SYSTEM;
        }
        Context userContext = getContextForUser(context, user);
        if (!isAchAvailable(userContext)) {
            return null;
        }
        if (uri.equals(getDefaultUri(2))) {
            Uri ringtoneUri = getActualDefaultRingtoneUri(userContext, 2);
            return ringtoneUri;
        }
        if (uri.equals(getDefaultUri(256))) {
            Uri ringtoneUri2 = getActualDefaultRingtoneUri(userContext, 256);
            return ringtoneUri2;
        }
        return uri;
    }

    private static void turnOffSyncHapticOnCscSounds(
            Context context, Uri ringtoneUri, String setting) {
        if (AsPackageName.CSC.equals(context.getPackageName())) {
            try {
                if (!AudioManager.hasHapticChannels(context, ringtoneUri)) {
                    Log.i(TAG, "sound has not haptic channel");
                    String syncDbName = getSyncHapticDbName(setting);
                    if (!TextUtils.isEmpty(syncDbName)) {
                        Log.i(TAG, "turn off " + syncDbName);
                        Settings.System.putInt(context.getContentResolver(), syncDbName, 0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getSyncHapticDbName(String soundType) {
        if (soundType.equals(Settings.System.RINGTONE)) {
            return Settings.System.SYNC_VIBRATION_WITH_RINGTONE;
        }
        if (soundType.equals(Settings.System.RINGTONE_2)) {
            return Settings.System.SYNC_VIBRATION_WITH_RINGTONE_2;
        }
        if (soundType.equals(Settings.System.NOTIFICATION_SOUND)
                || soundType.equals(Settings.System.NOTIFICATION_SOUND_2)) {
            return Settings.System.SYNC_VIBRATION_WITH_NOTIFICATION;
        }
        return null;
    }

    private static Uri maybeAddUserId(Uri uri, int userId) {
        if (uri == null) {
            return null;
        }
        if ("content".equals(uri.getScheme()) && !uriHasUserId(uri)) {
            Uri.Builder builder = uri.buildUpon();
            builder.encodedAuthority("" + userId + "@" + uri.getEncodedAuthority());
            return builder.build();
        }
        return uri;
    }

    private static boolean uriHasUserId(Uri uri) {
        if (uri == null) {
            return false;
        }
        return !TextUtils.isEmpty(uri.getUserInfo());
    }
}
