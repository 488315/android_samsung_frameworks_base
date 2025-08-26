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
import android.media.AudioAttributes;
import android.media.IAudioService;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.FileUtils;
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
import com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public class RingtoneManager {
    public static final String ACTION_RINGTONE_PICKER = "android.intent.action.RINGTONE_PICKER";
    public static final String EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS = "android.intent.extra.ringtone.AUDIO_ATTRIBUTES_FLAGS";
    public static final String EXTRA_RINGTONE_DEFAULT_URI = "android.intent.extra.ringtone.DEFAULT_URI";
    public static final String EXTRA_RINGTONE_EXISTING_URI = "android.intent.extra.ringtone.EXISTING_URI";

    @Deprecated
    public static final String EXTRA_RINGTONE_INCLUDE_DRM = "android.intent.extra.ringtone.INCLUDE_DRM";
    public static final String EXTRA_RINGTONE_PICKED_URI = "android.intent.extra.ringtone.PICKED_URI";
    public static final String EXTRA_RINGTONE_SHOW_DEFAULT = "android.intent.extra.ringtone.SHOW_DEFAULT";
    public static final String EXTRA_RINGTONE_SHOW_SILENT = "android.intent.extra.ringtone.SHOW_SILENT";
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
    private static final String[] INTERNAL_COLUMNS = {"_id", "title", "title", "title_key", "volume_name", "bucket_display_name", "is_ringtone", "is_notification", "is_alarm", "bookmark", "mime_type"};
    private static final String[] MEDIA_COLUMNS = {"_id", "title", "title", "title_key", "volume_name", "bucket_display_name", "is_ringtone", "is_notification", "is_alarm", "bookmark", "mime_type"};
    protected static String PREFIX_OPEN_THEME = "theme_";
    private static String OPEN_THEME_DIRECTORY = "/data/overlays/media/";
    private static Uri mDefaultRingtoneUri = null;
    private static Uri mDefaultRingtone2Uri = null;
    private static Uri mDefaultNotificationUri = null;
    private static Uri mDefaultNotification2Uri = null;
    private static Uri mDefaultAlarmUri = null;

    @Deprecated
    public boolean getIncludeDrm() {
        return false;
    }

    public RingtoneManager(Activity activity) {
        this(activity, false);
    }

    public RingtoneManager(Activity activity, boolean z) {
        this.mType = 1;
        this.mFilterColumns = new ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = activity;
        this.mContext = activity;
        setType(this.mType);
        this.mIncludeParentRingtones = z;
    }

    public RingtoneManager(Context context) {
        this(context, false);
    }

    public RingtoneManager(Context context, boolean z) {
        this.mType = 1;
        this.mFilterColumns = new ArrayList();
        this.mStopPreviousRingtone = true;
        this.mActivity = null;
        this.mContext = context;
        setType(this.mType);
        this.mIncludeParentRingtones = z;
    }

    public void setType(int i) {
        if (this.mCursor != null) {
            throw new IllegalStateException("Setting filter columns should be done before querying for ringtones.");
        }
        this.mType = i;
        if ((i & 133) != 0) {
            i |= 5;
        }
        setFilterColumnsList(i);
    }

    public int inferStreamType() {
        int i = this.mType;
        if (i == 128) {
            return 2;
        }
        if (i == 256 || i == 2) {
            return 5;
        }
        return i != 4 ? 2 : 4;
    }

    public void setStopPreviousRingtone(boolean z) {
        this.mStopPreviousRingtone = z;
    }

    public boolean getStopPreviousRingtone() {
        return this.mStopPreviousRingtone;
    }

    public void stopPreviousRingtone() {
        Ringtone ringtone = this.mPreviousRingtone;
        if (ringtone != null) {
            ringtone.stop();
        }
    }

    @Deprecated
    public void setIncludeDrm(boolean z) {
        if (z) {
            Log.w(TAG, "setIncludeDrm no longer supported");
        }
    }

    public Cursor getCursor() throws Throwable {
        Cursor parentProfileRingtones;
        Cursor cursor = this.mCursor;
        if (cursor != null && cursor.requery()) {
            return this.mCursor;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(getInternalRingtones());
        arrayList.add(getMediaRingtones());
        Cursor openThemeRingtone = getOpenThemeRingtone();
        if (openThemeRingtone != null) {
            arrayList.add(openThemeRingtone);
        }
        if (this.mIncludeParentRingtones && (parentProfileRingtones = getParentProfileRingtones()) != null) {
            arrayList.add(parentProfileRingtones);
        }
        SortCursor sortCursor = new SortCursor((Cursor[]) arrayList.toArray(new Cursor[arrayList.size()]), "title_key");
        this.mCursor = sortCursor;
        return sortCursor;
    }

    private Cursor getParentProfileRingtones() {
        Context contextCreatePackageContextAsUser;
        UserInfo profileParent = UserManager.get(this.mContext).getProfileParent(this.mContext.getUserId());
        if (profileParent == null || profileParent.id == this.mContext.getUserId() || (contextCreatePackageContextAsUser = createPackageContextAsUser(this.mContext, profileParent.id)) == null) {
            return null;
        }
        return new ExternalRingtonesCursorWrapper(getMediaRingtones(contextCreatePackageContextAsUser), ContentProvider.maybeAddUserId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, profileParent.id));
    }

    public Ringtone getRingtone(int i) {
        Ringtone ringtone;
        if (this.mStopPreviousRingtone && (ringtone = this.mPreviousRingtone) != null) {
            ringtone.stop();
        }
        Ringtone ringtone2 = getRingtone(this.mContext, getRingtoneUri(i), inferStreamType(), true);
        this.mPreviousRingtone = ringtone2;
        return ringtone2;
    }

    public Uri getRingtoneUri(int i) {
        try {
            Cursor cursor = this.mCursor;
            if (cursor != null) {
                if (cursor.moveToPosition(i)) {
                    return getUriFromCursor(this.mContext, this.mCursor);
                }
            }
            return null;
        } catch (StaleDataException | IllegalStateException e) {
            Log.e(TAG, "Unexpected Exception has been catched.", e);
            return null;
        }
    }

    public static Uri getRingtoneUriForRestore(ContentResolver contentResolver, String str, int i) throws IllegalArgumentException, UnsupportedEncodingException, FileNotFoundException {
        String str2;
        if (str == null) {
            return null;
        }
        Uri uri = Uri.parse(str);
        Uri uriUncanonicalize = contentResolver.uncanonicalize(uri);
        if (uriUncanonicalize != null) {
            return contentResolver.canonicalize(uriUncanonicalize);
        }
        String queryParameter = uri.getQueryParameter("title");
        Uri uriBuild = ContentUris.removeId(uri).buildUpon().clearQuery().build();
        if (i == 1) {
            str2 = "is_ringtone";
        } else if (i == 2) {
            str2 = "is_notification";
        } else if (i == 4) {
            str2 = "is_alarm";
        } else {
            throw new IllegalArgumentException("Unknown ringtone type: " + i);
        }
        try {
            Cursor cursorQuery = contentResolver.query(uriBuild, new String[]{"_id"}, str2.concat("=1 AND title=?"), new String[]{queryParameter}, null, null);
            if (cursorQuery == null) {
                throw new FileNotFoundException("Missing cursor for " + uriBuild);
            }
            if (cursorQuery.getCount() == 0) {
                FileUtils.closeQuietly(cursorQuery);
                throw new FileNotFoundException("No item found for " + uriBuild);
            }
            if (cursorQuery.getCount() > 1) {
                int count = cursorQuery.getCount();
                FileUtils.closeQuietly(cursorQuery);
                throw new FileNotFoundException("Find multiple ringtone candidates by title+ringtone_type query: count: " + count);
            }
            if (cursorQuery.moveToFirst()) {
                Uri uriWithAppendedId = ContentUris.withAppendedId(uriBuild, cursorQuery.getLong(0));
                FileUtils.closeQuietly(cursorQuery);
                Uri uriCanonicalize = contentResolver.canonicalize(uriWithAppendedId);
                Log.v(TAG, "Find a valid result: " + uriCanonicalize);
                return uriCanonicalize;
            }
            FileUtils.closeQuietly(cursorQuery);
            throw new FileNotFoundException("Failed to read row from the result.");
        } catch (IllegalArgumentException unused) {
            throw new FileNotFoundException("Volume not found for " + uriBuild);
        }
    }

    private static Uri getUriFromCursor(Context context, Cursor cursor) {
        if (isOpenThemeRingtone(context, cursor)) {
            return ContentUris.withAppendedId(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, cursor.getLong(0));
        }
        return context.getContentResolver().canonicalizeOrElse(ContentUris.withAppendedId(Uri.parse(cursor.getString(2)), cursor.getLong(0)));
    }

    public int getRingtonePosition(Uri uri) throws Throwable {
        Cursor cursor;
        if (uri == null) {
            return -1;
        }
        try {
            cursor = getCursor();
            cursor.moveToPosition(-1);
        } catch (NumberFormatException e) {
            Log.e(TAG, "NumberFormatException while getting ringtone position, returning -1", e);
        }
        if (!TextUtils.isEmpty(uri.toString()) && TextUtils.isDigitsOnly(uri.getLastPathSegment())) {
            long id = ContentUris.parseId(uri);
            String title = Ringtone.getTitle(this.mContext, uri, false, false);
            Log.d(TAG, "getRingtonePosition uri :" + uri + " / title : " + title + " / id : " + id);
            while (cursor.moveToNext()) {
                if (id == cursor.getLong(0) && title.equals(cursor.getString(1))) {
                    return cursor.getPosition();
                }
            }
            return -1;
        }
        Log.e(TAG, "getRingtonePosition - filter invalid case " + uri);
        return -1;
    }

    public static Uri getValidRingtoneUri(Context context) {
        RingtoneManager ringtoneManager = new RingtoneManager(context);
        Uri validRingtoneUriFromCursorAndClose = getValidRingtoneUriFromCursorAndClose(context, ringtoneManager.getInternalRingtones());
        return validRingtoneUriFromCursorAndClose == null ? getValidRingtoneUriFromCursorAndClose(context, ringtoneManager.getMediaRingtones()) : validRingtoneUriFromCursorAndClose;
    }

    private static Uri getValidRingtoneUriFromCursorAndClose(Context context, Cursor cursor) {
        if (cursor != null) {
            uriFromCursor = cursor.moveToFirst() ? getUriFromCursor(context, cursor) : null;
            cursor.close();
        }
        return uriFromCursor;
    }

    private Cursor getInternalRingtones() {
        return new ExternalRingtonesCursorWrapper(query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, INTERNAL_COLUMNS, (constructBooleanTrueWhereClause(this.mFilterColumns) + excludedRingtonesWhereClauseForCSC()) + excludedRingtonesWhereClauseForOpenTheme(), null, "title_key"), MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private Cursor getMediaRingtones() {
        return new ExternalRingtonesCursorWrapper(getMediaRingtones(this.mContext), MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private Cursor getMediaRingtones(Context context) {
        return query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, MEDIA_COLUMNS, constructBooleanTrueWhereClause(this.mFilterColumns), null, "title_key", context);
    }

    private void setFilterColumnsList(int i) {
        List<String> list = this.mFilterColumns;
        list.clear();
        if ((i & 1) != 0 || (i & 128) != 0) {
            list.add("is_ringtone");
        }
        if ((i & 2) != 0 || (i & 256) != 0) {
            list.add("is_notification");
        }
        if ((i & 4) != 0) {
            list.add("is_alarm");
        }
    }

    private static String constructBooleanTrueWhereClause(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(NavigationBarInflaterView.KEY_CODE_START);
        for (int size = list.size() - 1; size >= 0; size--) {
            sb.append(list.get(size));
            sb.append("=1 or ");
        }
        if (list.size() > 0) {
            sb.setLength(sb.length() - 4);
        }
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        sb.append("AND (");
        sb.append("mime_type");
        sb.append(" NOT LIKE 'audio/x-ms-wma')");
        return sb.toString();
    }

    private Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return query(uri, strArr, str, strArr2, str2, this.mContext);
    }

    private Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, Context context) {
        Activity activity = this.mActivity;
        if (activity != null) {
            return activity.managedQuery(uri, strArr, str, strArr2, str2);
        }
        return context.getContentResolver().query(uri, strArr, str, strArr2, str2);
    }

    public static Ringtone getRingtone(Context context, Uri uri) {
        return getRingtone(context, uri, -1, true);
    }

    public static Ringtone getRingtone(Context context, Uri uri, VolumeShaper.Configuration configuration) {
        return getRingtone(context, uri, -1, configuration, true);
    }

    public static Ringtone getRingtone(Context context, Uri uri, VolumeShaper.Configuration configuration, boolean z) {
        return getRingtone(context, uri, -1, configuration, z);
    }

    public static Ringtone getRingtone(Context context, Uri uri, VolumeShaper.Configuration configuration, AudioAttributes audioAttributes) {
        Ringtone ringtone = getRingtone(context, uri, -1, configuration, false);
        if (muteHapticChannelForVibration(context, uri)) {
            audioAttributes = new AudioAttributes.Builder(audioAttributes).setHapticChannelsMuted(true).build();
        }
        if (ringtone != null) {
            ringtone.setAudioAttributesField(audioAttributes);
            if (!ringtone.createLocalMediaPlayer()) {
                Log.e(TAG, "Failed to open ringtone " + uri);
                return null;
            }
        }
        return ringtone;
    }

    private static Ringtone getRingtone(Context context, Uri uri, int i, boolean z) {
        return getRingtone(context, uri, i, null, z);
    }

    private static Ringtone getRingtone(Context context, Uri uri, int i, VolumeShaper.Configuration configuration, boolean z) {
        try {
            Ringtone ringtone = new Ringtone(context, true);
            if (i >= 0) {
                ringtone.setStreamType(i);
            }
            ringtone.setVolumeShaperConfig(configuration);
            ringtone.setUri(uri, configuration);
            if (!z || ringtone.createLocalMediaPlayer()) {
                return ringtone;
            }
            Log.e(TAG, "Failed to open ringtone " + uri);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ringtone " + uri + ": " + e);
            return null;
        }
    }

    public static Uri getActualDefaultRingtoneUri(Context context, int i) throws Throwable {
        Log.d(TAG, "getActualDefaultRingtoneUri  type    :" + i);
        if (!checkDefaultRingtoneProperUri(context, i)) {
            setRingtonesAsInitValue(context, i);
        }
        String settingForType = getSettingForType(i);
        if (settingForType == null) {
            return null;
        }
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), settingForType, context.getUserId());
        Uri uri = stringForUser != null ? Uri.parse(stringForUser) : null;
        int userIdFromUri = ContentProvider.getUserIdFromUri(uri);
        int userId = context.getUserId();
        return (uri == null || !((userIdFromUri == 0 || userId == 0) && userIdFromUri == userId)) ? uri : ContentProvider.getUriWithoutUserId(uri);
    }

    public static void setActualDefaultRingtoneUri(Context context, int i, Uri uri) {
        String settingForType = getSettingForType(i);
        if (settingForType == null) {
            return;
        }
        ContentResolver contentResolver = context.getContentResolver();
        if (AsPackageName.CSC.equals(context.getPackageName()) && context.getUserId() != 0) {
            Log.e(TAG, "setActualDefaultRingtoneUri userId is in sub user from CscService");
            return;
        }
        if (isDefault(uri) || (isMediaProviderUri(uri) && ContentUris.parseId(uri) < 0)) {
            Log.i(TAG, "Invalid uri type : " + uri);
            return;
        }
        Settings.System.putStringForUser(contentResolver, getSettingKeyForAbsolutePath(i), null, context.getUserId());
        Uri uriMaybeAddUserId = maybeAddUserId(uri, context.getUserId());
        if (uriMaybeAddUserId != null) {
            String type = contentResolver.getType(uriMaybeAddUserId);
            if (type == null) {
                Log.e(TAG, "setActualDefaultRingtoneUri for URI:" + uriMaybeAddUserId + " ignored: failure to find mimeType (no access from this context?)");
                return;
            }
            if (!type.startsWith("audio/") && !type.equals("application/ogg") && !type.equals(ContentType.AUDIO_X_FLAC) && !type.startsWith(BnRConstants.VIDEO_DIR_PATH) && !type.equals("application/mp4")) {
                Log.e(TAG, "setActualDefaultRingtoneUri for URI:" + uriMaybeAddUserId + " ignored: associated MIME type:" + type + " is not a recognized audio or video type");
                return;
            }
        }
        Settings.System.putStringForUser(contentResolver, settingForType, uriMaybeAddUserId != null ? uriMaybeAddUserId.toString() : null, context.getUserId());
        logCallStackDetails(context, i, uriMaybeAddUserId);
        if (uriMaybeAddUserId != null) {
            saveAbsolutePath(context, i, uriMaybeAddUserId);
        }
        if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE) {
            turnOffSyncHapticOnCscSounds(context, uriMaybeAddUserId, settingForType);
        }
        int i2 = Settings.Global.getInt(contentResolver, Settings.Global.ENABLED_SIM2_ONLY, 0);
        Log.d(TAG, "setActualDefaultRingtoneUri :: enabled sim2 only =  " + i2);
        int i3 = 1;
        if (i2 == 1) {
            if (i != 128) {
                i3 = 256;
                if (i != 2) {
                    i3 = i == 256 ? 2 : 128;
                }
            }
            String settingForType2 = getSettingForType(i3);
            Settings.System.putStringForUser(contentResolver, settingForType2, uriMaybeAddUserId != null ? uriMaybeAddUserId.toString() : null, context.getUserId());
            logCallStackDetails(context, i3, uriMaybeAddUserId);
            if (uriMaybeAddUserId != null) {
                saveAbsolutePath(context, i3, uriMaybeAddUserId);
            }
            if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE) {
                turnOffSyncHapticOnCscSounds(context, uriMaybeAddUserId, settingForType2);
            }
        }
    }

    public static boolean isInternalRingtoneUri(Uri uri) {
        return isRingtoneUriInStorage(uri, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
    }

    private static boolean isExternalRingtoneUri(Uri uri) {
        return isRingtoneUriInStorage(uri, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
    }

    private static boolean isRingtoneUriInStorage(Uri uri, Uri uri2) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        if (uriWithoutUserId == null) {
            return false;
        }
        return uriWithoutUserId.toString().startsWith(uri2.toString());
    }

    public Uri addCustomExternalRingtone(Uri uri, int i) throws IOException, IllegalArgumentException {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("External storage is not mounted. Unable to install ringtones.");
        }
        String type = this.mContext.getContentResolver().getType(uri);
        if (type == null || (!type.startsWith("audio/") && !type.equals("application/ogg") && !type.contains("audio/"))) {
            throw new IllegalArgumentException("Ringtone file must have MIME type \"audio/*\". Given file has MIME type \"" + type + "\"");
        }
        String externalDirectoryForType = getExternalDirectoryForType(i);
        Context context = this.mContext;
        File uniqueExternalFile = Utils.getUniqueExternalFile(context, externalDirectoryForType, FileUtils.buildValidFatFilename(Utils.getFileDisplayNameFromUri(context, uri)), type);
        InputStream inputStreamOpenInputStream = this.mContext.getContentResolver().openInputStream(uri);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(uniqueExternalFile);
            try {
                FileUtils.copy(inputStreamOpenInputStream, fileOutputStream);
                fileOutputStream.close();
                if (inputStreamOpenInputStream != null) {
                    inputStreamOpenInputStream.close();
                }
                return MediaStore.scanFile(this.mContext.getContentResolver(), uniqueExternalFile);
            } finally {
            }
        } catch (Throwable th) {
            if (inputStreamOpenInputStream != null) {
                try {
                    inputStreamOpenInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static final String getExternalDirectoryForType(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 4) {
                    return Environment.DIRECTORY_ALARMS;
                }
                if (i != 128) {
                    if (i != 256) {
                        throw new IllegalArgumentException("Unsupported ringtone type: " + i);
                    }
                }
            }
            return Environment.DIRECTORY_NOTIFICATIONS;
        }
        return Environment.DIRECTORY_RINGTONES;
    }

    private static String getSettingForType(int i) {
        if ((i & 1) != 0) {
            return Settings.System.RINGTONE;
        }
        if ((i & 2) != 0) {
            return Settings.System.NOTIFICATION_SOUND;
        }
        if ((i & 4) != 0) {
            return Settings.System.ALARM_ALERT;
        }
        if ((i & 128) != 0) {
            return Settings.System.RINGTONE_2;
        }
        if ((i & 256) != 0) {
            return Settings.System.NOTIFICATION_SOUND_2;
        }
        return null;
    }

    public static Uri getCacheForType(int i) {
        return getCacheForType(i, UserHandle.getCallingUserId());
    }

    public static Uri getCacheForType(int i, int i2) {
        if ((i & 1) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.RINGTONE_CACHE_URI, i2);
        }
        if ((i & 2) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.NOTIFICATION_SOUND_CACHE_URI, i2);
        }
        if ((i & 4) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.ALARM_ALERT_CACHE_URI, i2);
        }
        if ((i & 128) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.RINGTONE2_CACHE_URI, i2);
        }
        if ((i & 256) != 0) {
            return ContentProvider.maybeAddUserId(Settings.System.NOTIFICATION_SOUND2_CACHE_URI, i2);
        }
        return null;
    }

    public static boolean isDefault(Uri uri) {
        return getDefaultType(uri) != -1;
    }

    public static int getDefaultType(Uri uri) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        if (uriWithoutUserId == null) {
            return -1;
        }
        if (Flags.enableRingtoneHapticsCustomization() && Utils.hasVibration(uriWithoutUserId)) {
            if (uriWithoutUserId.toString().contains(Settings.System.DEFAULT_RINGTONE_URI.toString())) {
                return 1;
            }
            if (uriWithoutUserId.toString().contains(Settings.System.DEFAULT_NOTIFICATION_URI.toString())) {
                return 2;
            }
            if (uriWithoutUserId.toString().contains(Settings.System.DEFAULT_ALARM_ALERT_URI.toString())) {
                return 4;
            }
        }
        if (uriWithoutUserId.equals(Settings.System.DEFAULT_RINGTONE_URI)) {
            return 1;
        }
        if (uriWithoutUserId.equals(Settings.System.DEFAULT_NOTIFICATION_URI)) {
            return 2;
        }
        if (uriWithoutUserId.equals(Settings.System.DEFAULT_ALARM_ALERT_URI)) {
            return 4;
        }
        if (uriWithoutUserId.equals(Settings.System.DEFAULT_RINGTONE_URI_2)) {
            return 128;
        }
        if (uriWithoutUserId.equals(Settings.System.DEFAULT_RINGTONE_URI_3)) {
            return 1;
        }
        return uriWithoutUserId.equals(Settings.System.DEFAULT_NOTIFICATION_URI_2) ? 256 : -1;
    }

    public static Uri getDefaultUri(int i) {
        if ((i & 1) != 0) {
            return Settings.System.DEFAULT_RINGTONE_URI;
        }
        if ((i & 2) != 0) {
            return Settings.System.DEFAULT_NOTIFICATION_URI;
        }
        if ((i & 4) != 0) {
            return Settings.System.DEFAULT_ALARM_ALERT_URI;
        }
        if ((i & 128) != 0) {
            return Settings.System.DEFAULT_RINGTONE_URI_2;
        }
        if ((i & 256) != 0) {
            return Settings.System.DEFAULT_NOTIFICATION_URI_2;
        }
        return null;
    }

    public static AssetFileDescriptor openDefaultRingtoneUri(Context context, Uri uri) throws Throwable {
        AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor;
        int defaultType = getDefaultType(uri);
        Uri cacheForType = getCacheForType(defaultType, context.getUserId());
        Uri actualDefaultRingtoneUri = getActualDefaultRingtoneUri(context, defaultType);
        ContentResolver contentResolver = context.getContentResolver();
        if (cacheForType != null) {
            assetFileDescriptorOpenAssetFileDescriptor = contentResolver.openAssetFileDescriptor(cacheForType, "r");
            if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                return assetFileDescriptorOpenAssetFileDescriptor;
            }
        } else {
            assetFileDescriptorOpenAssetFileDescriptor = null;
        }
        return actualDefaultRingtoneUri != null ? contentResolver.openAssetFileDescriptor(actualDefaultRingtoneUri, "r") : assetFileDescriptorOpenAssetFileDescriptor;
    }

    public boolean hasHapticChannels(int i) {
        return AudioManager.hasHapticChannels(this.mContext, getRingtoneUri(i));
    }

    public static boolean hasHapticChannels(Uri uri) {
        return AudioManager.hasHapticChannels(null, uri);
    }

    public static boolean hasHapticChannels(Context context, Uri uri) {
        return AudioManager.hasHapticChannels(context, uri);
    }

    private static Context createPackageContextAsUser(Context context, int i) {
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(i));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to create package context", e);
            return null;
        }
    }

    @SystemApi
    public static void ensureDefaultRingtones(Context context) {
        Uri uriComputeDefaultRingtoneUri;
        int[] iArr = {1, 2, 4, 128, 256};
        for (int i = 0; i < 5; i++) {
            int i2 = iArr[i];
            String defaultRingtoneSetting = getDefaultRingtoneSetting(i2);
            if (Settings.System.getInt(context.getContentResolver(), defaultRingtoneSetting, 0) == 0 && (uriComputeDefaultRingtoneUri = computeDefaultRingtoneUri(context, i2)) != null) {
                setActualDefaultRingtoneUri(context, i2, uriComputeDefaultRingtoneUri);
                Settings.System.putInt(context.getContentResolver(), defaultRingtoneSetting, 1);
            }
        }
    }

    private static Uri computeDefaultRingtoneUri(Context context, int i) {
        String defaultRingtoneFilename = getDefaultRingtoneFilename(i);
        String str = "_display_name=? AND " + getQueryStringForType(i) + "=?";
        Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_id"}, str, new String[]{defaultRingtoneFilename, "1"}, null);
        try {
            if (cursorQuery.moveToFirst()) {
                Uri uriCanonicalizeOrElse = context.getContentResolver().canonicalizeOrElse(ContentUris.withAppendedId(uri, cursorQuery.getLong(0)));
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return uriCanonicalizeOrElse;
            }
            if (cursorQuery == null) {
                return null;
            }
            cursorQuery.close();
            return null;
        } catch (Throwable th) {
            if (cursorQuery == null) {
                throw th;
            }
            try {
                cursorQuery.close();
                throw th;
            } catch (Throwable th2) {
                th.addSuppressed(th2);
                throw th;
            }
        }
    }

    private static String getDefaultRingtoneSetting(int i) {
        if (i == 1) {
            return "ringtone_set";
        }
        if (i == 2) {
            return "notification_sound_set";
        }
        if (i == 4) {
            return "alarm_alert_set";
        }
        if (i == 128) {
            return "ringtone_2_set";
        }
        if (i == 256) {
            return "notification_sound_2_set";
        }
        throw new IllegalArgumentException();
    }

    private static String getDefaultRingtoneFilename(int i) {
        if (i == 1) {
            return SystemProperties.get(AsProperty.PROP_CFG_RINGTONE);
        }
        if (i == 2) {
            return SystemProperties.get(AsProperty.PROP_CFG_NOTIFICATION_SOUND);
        }
        if (i == 4) {
            return SystemProperties.get(AsProperty.PROP_CFG_ALARM_ALERT);
        }
        if (i == 128) {
            return SystemProperties.get(AsProperty.PROP_CFG_RINGTONE2);
        }
        if (i == 256) {
            return SystemProperties.get(AsProperty.PROP_CFG_NOTIFICATION_SOUND2);
        }
        throw new IllegalArgumentException();
    }

    private static String getQueryStringForType(int i) {
        if (i == 1) {
            return "is_ringtone";
        }
        if (i == 2) {
            return "is_notification";
        }
        if (i == 4 || i == 128) {
            return "is_ringtone";
        }
        if (i == 256) {
            return "is_notification";
        }
        throw new IllegalArgumentException();
    }

    private static boolean muteHapticChannelForVibration(Context context, Uri uri) {
        Uri vibrationUri = Utils.getVibrationUri(uri);
        return vibrationUri != null && !Objects.equals(vibrationUri.toString(), Utils.SYNCHRONIZED_VIBRATION) && Flags.enableRingtoneHapticsCustomization() && Utils.isRingtoneVibrationSettingsSupported(context) && hasHapticChannels(uri);
    }

    private static Ringtone getRingtone(Context context, Uri uri, int i, int i2) {
        try {
            Ringtone ringtone = new Ringtone(context, true);
            if (i >= 0) {
                ringtone.setStreamType(i);
            }
            if (i2 >= 0) {
                ringtone.setSecForSeek(i2);
            }
            ringtone.setUri(uri);
            return ringtone;
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ringtone " + uri + ": " + e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void saveAbsolutePath(Context context, int i, Uri uri) {
        Log.i(TAG, "Save path type :" + i + ", URI : " + uri);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            Cursor cursorQuery = contentResolver.query(uri, new String[]{"_data"}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() == 0) {
                        Log.e(TAG, "cannot find the " + uri);
                        if (cursorQuery == null) {
                            return;
                        }
                    } else {
                        cursorQuery.moveToFirst();
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        Log.i(TAG, "ringtone path: " + string);
                        Settings.System.putStringForUser(contentResolver, getSettingKeyForAbsolutePath(i), new Uri.Builder().appendQueryParameter("path", string).build().toString(), context.getUserId());
                        if (cursorQuery == null) {
                            return;
                        }
                    }
                } finally {
                }
            } else {
                Log.e(TAG, "cannot find the " + uri);
                if (cursorQuery == null) {
                }
            }
            cursorQuery.close();
        } catch (Exception e) {
            Log.i(TAG, "saveAbsolutePath " + e);
        }
    }

    public static void setRingtonesAsInitValue(Context context, int i) throws Throwable {
        if ((i & 1) != 0) {
            mDefaultRingtoneUri = null;
        } else if ((i & 128) != 0) {
            mDefaultRingtone2Uri = null;
        } else if ((i & 2) != 0) {
            mDefaultNotificationUri = null;
        } else if ((i & 256) != 0) {
            mDefaultNotification2Uri = null;
        } else if ((i & 4) != 0) {
            mDefaultAlarmUri = null;
        }
        Uri defaultSoundUri = getDefaultSoundUri(context, i);
        if (defaultSoundUri == null) {
            return;
        }
        setActualDefaultRingtoneUri(context, i, context.getContentResolver().canonicalizeOrElse(defaultSoundUri));
    }

    public static String getSettingKeyForAbsolutePath(int i) {
        if (i == 1) {
            return "ringtone_CONSTANT_PATH";
        }
        if (i == 2) {
            return "notification_sound_CONSTANT_PATH";
        }
        if (i == 4) {
            return "alarm_alert_CONSTANT_PATH";
        }
        if (i == 128) {
            return "ringtone_2_CONSTANT_PATH";
        }
        if (i != 256) {
            return null;
        }
        return "notification_sound_2_CONSTANT_PATH";
    }

    public static String getRingtoneTitleForCached(Context context, int i) {
        String settingForType = getSettingForType(i);
        if (settingForType == null) {
            return context.getString(R.string.ringtone_unknown);
        }
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), settingForType, context.getUserId());
        if (stringForUser == null) {
            return context.getString(R.string.sec_ringtone_silent);
        }
        String queryParameter = getQueryParameter(Uri.parse(stringForUser), "title");
        return queryParameter != null ? queryParameter : context.getString(R.string.ringtone_unknown);
    }

    public static String semGetDefaultRingtoneTitle(Context context, int i) {
        return getRingtoneTitleForCached(context, i);
    }

    private static Uri getDefaultSettingSound(int i) {
        if ((i & 1) != 0) {
            return mDefaultRingtoneUri;
        }
        if ((i & 128) != 0) {
            return mDefaultRingtone2Uri;
        }
        if ((i & 2) != 0) {
            return mDefaultNotificationUri;
        }
        if ((i & 256) != 0) {
            return mDefaultNotification2Uri;
        }
        if ((i & 4) != 0) {
            return mDefaultAlarmUri;
        }
        return null;
    }

    public static Uri getDefaultSoundUri(Context context, int i) throws Throwable {
        String strSubstring;
        Uri uri;
        ArrayList arrayList = new ArrayList();
        int i2 = i & 1;
        Cursor cursor = null;
        uriWithAppendedId = null;
        Uri uriWithAppendedId = null;
        cursor = null;
        if (i2 != 0) {
            Uri uri2 = mDefaultRingtoneUri;
            if (uri2 != null) {
                return uri2;
            }
            strSubstring = SystemProperties.get(AsProperty.PROP_CFG_RINGTONE);
            arrayList.add("is_ringtone");
        } else if ((i & 128) != 0) {
            Uri uri3 = mDefaultRingtone2Uri;
            if (uri3 != null) {
                return uri3;
            }
            String str = SystemProperties.get(AsProperty.PROP_CFG_RINGTONE2);
            if (str.trim().isEmpty()) {
                Log.e(TAG, "ro.config.ringtone_2 is not set");
                strSubstring = SystemProperties.get(AsProperty.PROP_CFG_RINGTONE);
            } else {
                strSubstring = str;
            }
            arrayList.add("is_ringtone");
        } else if ((i & 2) != 0) {
            Uri uri4 = mDefaultNotificationUri;
            if (uri4 != null) {
                return uri4;
            }
            strSubstring = SystemProperties.get(AsProperty.PROP_CFG_NOTIFICATION_SOUND);
            arrayList.add("is_notification");
        } else if ((i & 256) != 0) {
            Uri uri5 = mDefaultNotification2Uri;
            if (uri5 != null) {
                return uri5;
            }
            strSubstring = SystemProperties.get(AsProperty.PROP_CFG_NOTIFICATION_SOUND2);
            if (strSubstring.trim().isEmpty()) {
                Log.e(TAG, "ro.config.notification_sound_2 is not set");
                strSubstring = SystemProperties.get(AsProperty.PROP_CFG_NOTIFICATION_SOUND);
            }
            arrayList.add("is_notification");
        } else {
            if ((i & 4) == 0) {
                return null;
            }
            Uri uri6 = mDefaultAlarmUri;
            if (uri6 != null) {
                return uri6;
            }
            strSubstring = SystemProperties.get(AsProperty.PROP_CFG_ALARM_ALERT);
            arrayList.add("is_ringtone");
            arrayList.add("is_alarm");
        }
        String str2 = SystemProperties.get(getOMCRingtonePropertyName(i), "");
        if (!str2.isEmpty()) {
            strSubstring = str2;
        }
        if (strSubstring.contains(MediaMetrics.SEPARATOR)) {
            strSubstring = strSubstring.substring(0, strSubstring.lastIndexOf(MediaMetrics.SEPARATOR));
        }
        Log.d(TAG, "Default ringtone/notification sound is :" + strSubstring);
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, new String[]{"_id"}, constructBooleanTrueWhereClause(arrayList) + " and _data like '%/" + strSubstring + ".___'", null, "title_key");
                if (cursorQuery == null) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return null;
                }
                try {
                    cursorQuery.moveToFirst();
                    while (!cursorQuery.isAfterLast()) {
                        uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, cursorQuery.getLong(0));
                        if (i2 != 0) {
                            mDefaultRingtoneUri = uriWithAppendedId;
                        } else if ((i & 128) != 0) {
                            mDefaultRingtone2Uri = uriWithAppendedId;
                        } else if ((i & 2) != 0) {
                            mDefaultNotificationUri = uriWithAppendedId;
                        } else if ((i & 256) != 0) {
                            mDefaultNotification2Uri = uriWithAppendedId;
                        } else if ((i & 4) != 0) {
                            mDefaultAlarmUri = uriWithAppendedId;
                        }
                        cursorQuery.moveToNext();
                    }
                    Log.d(TAG, "Default ringtone/notification's uri found : " + uriWithAppendedId);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return uriWithAppendedId;
                } catch (Exception e) {
                    e = e;
                    Uri uri7 = uriWithAppendedId;
                    cursor = cursorQuery;
                    uri = uri7;
                    Log.e(TAG, "Can't read ro.config value", e);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return uri;
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
        } catch (Exception e2) {
            e = e2;
            uri = null;
        }
    }

    private static boolean checkDefaultRingtoneProperUri(Context context, int i) {
        String stringForUser;
        String settingForType = getSettingForType(i);
        String settingKeyForAbsolutePath = getSettingKeyForAbsolutePath(i);
        if (settingForType == null || settingKeyForAbsolutePath == null || (stringForUser = Settings.System.getStringForUser(context.getContentResolver(), settingForType, context.getUserId())) == null) {
            return true;
        }
        Log.i(TAG, "Ringtone value : " + stringForUser);
        Uri uri = Uri.parse(stringForUser);
        if (isMediaProviderUri(uri) && !isInternalRingtoneUri(uri)) {
            if (ContentUris.parseId(uri) <= 0) {
                return false;
            }
            String stringForUser2 = Settings.System.getStringForUser(context.getContentResolver(), settingKeyForAbsolutePath, context.getUserId());
            if (stringForUser2 == null) {
                Log.w(TAG, "Ringtone path is null");
                return true;
            }
            try {
                Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_id"}, "_data=?", new String[]{getQueryParameter(Uri.parse(stringForUser2), "path")}, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.getCount() > 0) {
                            Log.i(TAG, "path and URI match to each other ");
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return true;
                        }
                    } finally {
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                Log.w(TAG, "path and URI don't match");
                return false;
            } catch (Exception e) {
                Log.i(TAG, "checkDefaultRingtoneProperUri : " + e);
            }
        }
        return true;
    }

    protected static boolean isMediaProviderUri(Uri uri) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        return uriWithoutUserId != null && uriWithoutUserId.toString().startsWith(MediaStore.AUTHORITY_URI.toString());
    }

    public static Ringtone semGetRingtone(Context context, int i, Uri uri) {
        try {
            Ringtone ringtone = new Ringtone(context, true);
            if (i >= 0) {
                ringtone.setSecForSeek(i);
            }
            ringtone.setUri(uri);
            return ringtone;
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ringtone " + uri + ": " + e);
            return null;
        }
    }

    public Ringtone semGetRingtone(int i, int i2) {
        Ringtone ringtone;
        if (this.mStopPreviousRingtone && (ringtone = this.mPreviousRingtone) != null) {
            ringtone.stop();
        }
        Ringtone ringtone2 = getRingtone(this.mContext, getRingtoneUri(i), inferStreamType(), i2);
        this.mPreviousRingtone = ringtone2;
        return ringtone2;
    }

    private List<String> getExcludedRingtoneTitles() {
        List<String> list = Collections.EMPTY_LIST;
        try {
            return IAudioService.Stub.asInterface(ServiceManager.getService("audio")).getExcludedRingtoneTitles((this.mType & 258) != 0 ? 2 : 1);
        } catch (RemoteException unused) {
            Log.e(TAG, "Unable to get excluded ringtones.");
            return list;
        }
    }

    private String excludedRingtonesWhereClauseForCSC() {
        List<String> excludedRingtoneTitles = getExcludedRingtoneTitles();
        if (excludedRingtoneTitles.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" and (");
        for (String str : excludedRingtoneTitles) {
            sb.append("_display_name!=");
            sb.append("'" + str + "'");
            sb.append(" and ");
        }
        sb.setLength(sb.length() - 5);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static String getOMCRingtonePropertyName(int i) {
        if (i == 1) {
            return AsProperty.PROP_CFG_OMC_RINGTONE;
        }
        if (i == 128) {
            return AsProperty.PROP_CFG_OMC_RINGTONE2;
        }
        if (i == 2) {
            return AsProperty.PROP_CFG_OMC_NOTIFICATION_SOUND;
        }
        if (i == 256) {
            return AsProperty.PROP_CFG_OMC_NOTIFICATION_SOUND2;
        }
        if (i == 4) {
            return AsProperty.PROP_CFG_OMC_ALARM_ALERT;
        }
        return "";
    }

    private static String getQueryParameter(Uri uri, String str) {
        if (uri != null && str != null) {
            try {
                return uri.getQueryParameter(str);
            } catch (UnsupportedOperationException unused) {
            }
        }
        return null;
    }

    private static final String hidden_EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS() {
        return EXTRA_RINGTONE_AUDIO_ATTRIBUTES_FLAGS;
    }

    private static void logCallStackDetails(Context context, int i, Uri uri) {
        try {
            IAudioService.Stub.asInterface(ServiceManager.getService("audio")).recordRingtoneChanger(context.getPackageName() + " uid/pid: " + Binder.getCallingUid() + "/" + Binder.getCallingPid() + " type: " + i + " user: " + context.getUserId() + " uri: " + uri);
        } catch (RemoteException unused) {
            Log.e(TAG, "Unable to dumpCallStack.");
        }
    }

    public Uri addCustomRingtone(Uri uri, int i) throws IOException, IllegalArgumentException {
        return addCustomExternalRingtone(uri, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Cursor getOpenThemeRingtone() throws Throwable {
        String str;
        Exception exc;
        MatrixCursor matrixCursor;
        MatrixCursor matrixCursor2;
        Throwable th;
        String string = this.mContext.getString(R.string.sec_ringtone_category_open_theme);
        int i = this.mType;
        if (i == 2 || i == 256) {
            str = "is_notification";
        } else if (i == 4) {
            str = "is_alarm";
        } else {
            str = "is_ringtone";
        }
        String str2 = "(_display_name like '" + PREFIX_OPEN_THEME + "%') and " + str + "=1";
        try {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Uri uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
            String[] strArr = INTERNAL_COLUMNS;
            Cursor cursorQuery = contentResolver.query(uri, strArr, str2, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() != 0) {
                        cursorQuery.moveToFirst();
                        matrixCursor2 = new MatrixCursor(strArr);
                        try {
                            String[] strArr2 = new String[strArr.length];
                            for (int i2 = 0; i2 < INTERNAL_COLUMNS.length; i2++) {
                                if (i2 == 1) {
                                    strArr2[i2] = string;
                                } else {
                                    strArr2[i2] = cursorQuery.getString(i2);
                                }
                            }
                            matrixCursor2.addRow(strArr2);
                        } catch (Throwable th2) {
                            matrixCursor = matrixCursor2;
                            th = th2;
                            try {
                                if (cursorQuery == null) {
                                    throw th;
                                }
                                try {
                                    cursorQuery.close();
                                    throw th;
                                } catch (Throwable th3) {
                                    th.addSuppressed(th3);
                                    throw th;
                                }
                            } catch (Exception e) {
                                exc = e;
                                Log.e(TAG, "DB exception", exc);
                                matrixCursor2 = matrixCursor;
                                if (matrixCursor2 != null) {
                                }
                            }
                        }
                    } else {
                        matrixCursor2 = null;
                    }
                    if (cursorQuery != null) {
                        try {
                            cursorQuery.close();
                        } catch (Exception e2) {
                            exc = e2;
                            matrixCursor = matrixCursor2;
                            Log.e(TAG, "DB exception", exc);
                            matrixCursor2 = matrixCursor;
                            if (matrixCursor2 != null) {
                            }
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    matrixCursor = null;
                }
            }
        } catch (Exception e3) {
            exc = e3;
            matrixCursor = null;
        }
        if (matrixCursor2 != null) {
            return new ExternalRingtonesCursorWrapper(matrixCursor2, MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
        }
        return null;
    }

    private static boolean isOpenThemeRingtone(Context context, Cursor cursor) {
        return cursor.getString(2).startsWith(PREFIX_OPEN_THEME) || cursor.getString(1).equals(context.getString(R.string.sec_ringtone_category_open_theme));
    }

    public static boolean shouldMigrationThemeSoundFile(Context context, int i) {
        String settingKeyForAbsolutePath = getSettingKeyForAbsolutePath(i);
        if (TextUtils.isEmpty(settingKeyForAbsolutePath)) {
            return false;
        }
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), settingKeyForAbsolutePath, context.getUserId());
        if (TextUtils.isEmpty(stringForUser)) {
            return false;
        }
        String queryParameter = getQueryParameter(Uri.parse(stringForUser), "path");
        Log.i(TAG, "shouldMigrationThemeSoundFile absolutePath : " + queryParameter);
        return (TextUtils.isEmpty(queryParameter) || !queryParameter.startsWith(OPEN_THEME_DIRECTORY) || queryParameter.contains(PREFIX_OPEN_THEME)) ? false : true;
    }

    private String excludedRingtonesWhereClauseForOpenTheme() {
        StringBuilder sb = new StringBuilder(" AND (_display_name");
        sb.append(" not like '" + PREFIX_OPEN_THEME + "%')");
        return sb.toString();
    }

    private static Context getContextForUser(Context context, UserHandle userHandle) {
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, userHandle);
        } catch (PackageManager.NameNotFoundException unused) {
            return context;
        }
    }

    private static boolean isAchAvailable(Context context) {
        return AudioManager.isCurrentHapticPlaybackSupported(false) && (Settings.System.getInt(context.getContentResolver(), Settings.System.SYNC_VIBRATION_WITH_NOTIFICATION, 1) != 0);
    }

    public static Uri getActualAchRingtoneUriIfAvailable(Context context, Uri uri, UserHandle userHandle) {
        if (UserHandle.ALL.equals(userHandle)) {
            userHandle = UserHandle.SYSTEM;
        }
        Context contextForUser = getContextForUser(context, userHandle);
        if (!isAchAvailable(contextForUser)) {
            return null;
        }
        if (uri.equals(getDefaultUri(2))) {
            return getActualDefaultRingtoneUri(contextForUser, 2);
        }
        return uri.equals(getDefaultUri(256)) ? getActualDefaultRingtoneUri(contextForUser, 256) : uri;
    }

    private static void turnOffSyncHapticOnCscSounds(Context context, Uri uri, String str) {
        if (AsPackageName.CSC.equals(context.getPackageName())) {
            try {
                if (AudioManager.hasHapticChannels(context, uri)) {
                    return;
                }
                Log.i(TAG, "sound has not haptic channel");
                String syncHapticDbName = getSyncHapticDbName(str);
                if (TextUtils.isEmpty(syncHapticDbName)) {
                    return;
                }
                Log.i(TAG, "turn off " + syncHapticDbName);
                Settings.System.putInt(context.getContentResolver(), syncHapticDbName, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getSyncHapticDbName(String str) {
        if (str.equals(Settings.System.RINGTONE)) {
            return Settings.System.SYNC_VIBRATION_WITH_RINGTONE;
        }
        if (str.equals(Settings.System.RINGTONE_2)) {
            return Settings.System.SYNC_VIBRATION_WITH_RINGTONE_2;
        }
        if (str.equals(Settings.System.NOTIFICATION_SOUND) || str.equals(Settings.System.NOTIFICATION_SOUND_2)) {
            return Settings.System.SYNC_VIBRATION_WITH_NOTIFICATION;
        }
        return null;
    }

    private static Uri maybeAddUserId(Uri uri, int i) {
        if (uri == null) {
            return null;
        }
        if (!"content".equals(uri.getScheme()) || uriHasUserId(uri)) {
            return uri;
        }
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.encodedAuthority("" + i + "@" + uri.getEncodedAuthority());
        return builderBuildUpon.build();
    }

    private static boolean uriHasUserId(Uri uri) {
        if (uri == null) {
            return false;
        }
        return !TextUtils.isEmpty(uri.getUserInfo());
    }
}
