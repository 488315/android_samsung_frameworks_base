package android.mtp;

import android.app.jank.AppJankStats;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.ApplicationMediaCapabilities;
import android.media.ThumbnailUtils;
import android.mtp.MtpStorageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.provider.Settings;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Size;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.collect.Sets;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.wallpaperbackup.BnRConstants;
import dalvik.system.CloseGuard;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/* loaded from: classes3.dex */
public class MtpDatabase implements AutoCloseable {
    static final String AGENT_PACKAGE_NAME = "com.sec.android.easyMover.Agent";
    static final String AGENT_SERVICE_NAME = "com.sec.android.easyMover.Agent.RemoteService";
    private static final int[] AUDIO_PROPERTIES;
    private static final int[] DEVICE_PROPERTIES;
    static final int EASYMOVER_AGENT = 0;
    static final int EASYMOVER_SSM = 1;
    private static final int[] FILE_PROPERTIES;
    private static final int[] IMAGE_PROPERTIES;
    private static final int MAX_THUMB_SIZE = 204800;
    static final int MSG_CLOSE = 2;
    static final int MSG_GET_SERIAL_COMMAND = 4;
    static final int MSG_OPEN = 1;
    static final int MSG_SEND_SERIAL_COMMAND = 3;
    private static final String NO_MEDIA = ".nomedia";
    private static final String PATH_WHERE = "_data=?";
    private static final int[] PLAYBACK_FORMATS;
    static final int RESP_FAIL = 2;
    static final int RESP_NONE = 0;
    static final int RESP_NOTREADY = 3;
    static final int RESP_SUCCESS = 1;
    static final String SSM_PACKAGE_NAME = "com.sec.android.easyMover";
    static final String SSM_SERVICE_NAME = "com.sec.android.easyMover.service.RemoteService";
    private static final String TAG = "MtpDatabase";
    private static final int THUMB_HEIGHT = 256;
    private static final int THUMB_WIDTH = 256;
    private static final int[] VIDEO_PROPERTIES;
    static String jsonData;
    ServiceConnection[] conn;
    private int currentServiceID;
    boolean isStratCommand;
    private int mBatteryLevel;
    private BroadcastReceiver mBatteryReceiver;
    private int mBatteryScale;
    private final CloseGuard mCloseGuard;
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final Context mContext;
    private SharedPreferences mDeviceProperties;
    private int mDeviceType;
    private volatile boolean mHostIsWindows;
    private String mHostType;
    boolean[] mIsBound;
    private MtpStorageManager mManager;
    private final ContentProviderClient mMediaProvider;
    private final Messenger mMessenger;
    private long mNativeContext;
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByFormat;
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByProperty;
    private MtpServer mServer;
    private Messenger[] mService;
    private boolean mSkipThumbForHost;
    private final HashMap<String, MtpStorage> mStorageMap;
    ComponentName[] serviceComponent;
    private static final String[] ID_PROJECTION = {"_id"};
    private static final String[] PATH_PROJECTION = {"_data"};

    private int[] getObjectReferences(int i) {
        return null;
    }

    private int[] getSupportedCaptureFormats() {
        return null;
    }

    private final native void native_finalize();

    private final native void native_setup();

    static {
        System.loadLibrary("media_jni");
        PLAYBACK_FORMATS = new int[]{12288, 12289, 12292, 12293, 12296, 12297, 12299, MtpConstants.FORMAT_EXIF_JPEG, MtpConstants.FORMAT_TIFF_EP, MtpConstants.FORMAT_BMP, MtpConstants.FORMAT_GIF, MtpConstants.FORMAT_JFIF, MtpConstants.FORMAT_PNG, MtpConstants.FORMAT_TIFF, MtpConstants.FORMAT_WMA, MtpConstants.FORMAT_OGG, MtpConstants.FORMAT_AAC, MtpConstants.FORMAT_MP4_CONTAINER, MtpConstants.FORMAT_MP2, MtpConstants.FORMAT_3GP_CONTAINER, MtpConstants.FORMAT_ABSTRACT_AV_PLAYLIST, MtpConstants.FORMAT_WPL_PLAYLIST, MtpConstants.FORMAT_M3U_PLAYLIST, MtpConstants.FORMAT_PLS_PLAYLIST, MtpConstants.FORMAT_XML_DOCUMENT, MtpConstants.FORMAT_FLAC, MtpConstants.FORMAT_DNG, MtpConstants.FORMAT_HEIF};
        FILE_PROPERTIES = new int[]{MtpConstants.PROPERTY_STORAGE_ID, MtpConstants.PROPERTY_OBJECT_FORMAT, MtpConstants.PROPERTY_PROTECTION_STATUS, MtpConstants.PROPERTY_OBJECT_SIZE, MtpConstants.PROPERTY_OBJECT_FILE_NAME, MtpConstants.PROPERTY_DATE_MODIFIED, MtpConstants.PROPERTY_PERSISTENT_UID, MtpConstants.PROPERTY_PARENT_OBJECT, MtpConstants.PROPERTY_NAME, MtpConstants.PROPERTY_DISPLAY_NAME, MtpConstants.PROPERTY_DATE_ADDED, MtpConstants.PROPERTY_HIDDEN};
        AUDIO_PROPERTIES = new int[]{MtpConstants.PROPERTY_ARTIST, MtpConstants.PROPERTY_ALBUM_NAME, MtpConstants.PROPERTY_ALBUM_ARTIST, MtpConstants.PROPERTY_TRACK, MtpConstants.PROPERTY_ORIGINAL_RELEASE_DATE, MtpConstants.PROPERTY_DURATION, MtpConstants.PROPERTY_GENRE, MtpConstants.PROPERTY_COMPOSER, MtpConstants.PROPERTY_AUDIO_WAVE_CODEC, MtpConstants.PROPERTY_BITRATE_TYPE, MtpConstants.PROPERTY_AUDIO_BITRATE, MtpConstants.PROPERTY_NUMBER_OF_CHANNELS, MtpConstants.PROPERTY_SAMPLE_RATE};
        VIDEO_PROPERTIES = new int[]{MtpConstants.PROPERTY_ARTIST, MtpConstants.PROPERTY_ALBUM_NAME, MtpConstants.PROPERTY_DURATION, MtpConstants.PROPERTY_DESCRIPTION};
        IMAGE_PROPERTIES = new int[]{MtpConstants.PROPERTY_DESCRIPTION};
        DEVICE_PROPERTIES = new int[]{MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER, MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME, MtpConstants.DEVICE_PROPERTY_IMAGE_SIZE, MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL, MtpConstants.DEVICE_PROPERTY_PERCEIVED_DEVICE_TYPE, MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO};
        jsonData = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int[] getSupportedObjectProperties(int i) {
        if (i != 14336 && i != 14337 && i != 14340 && i != 14343 && i != 14347 && i != 14349) {
            if (i != 47492) {
                switch (i) {
                    case 12296:
                    case 12297:
                        return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(AUDIO_PROPERTIES)).toArray();
                    case 12298:
                    case 12299:
                    case 12300:
                        break;
                    default:
                        switch (i) {
                            case MtpConstants.FORMAT_JP2 /* 14351 */:
                            case MtpConstants.FORMAT_JPX /* 14352 */:
                            case MtpConstants.FORMAT_DNG /* 14353 */:
                            case MtpConstants.FORMAT_HEIF /* 14354 */:
                                break;
                            default:
                                switch (i) {
                                    case 47360:
                                    case MtpConstants.FORMAT_WMA /* 47361 */:
                                    case MtpConstants.FORMAT_OGG /* 47362 */:
                                    case MtpConstants.FORMAT_AAC /* 47363 */:
                                        break;
                                    default:
                                        switch (i) {
                                            case MtpConstants.FORMAT_UNDEFINED_VIDEO /* 47488 */:
                                            case MtpConstants.FORMAT_WMV /* 47489 */:
                                            case MtpConstants.FORMAT_MP4_CONTAINER /* 47490 */:
                                                break;
                                            default:
                                                return FILE_PROPERTIES;
                                        }
                                }
                        }
                }
            }
            return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(VIDEO_PROPERTIES)).toArray();
        }
        return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(IMAGE_PROPERTIES)).toArray();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Uri getObjectPropertiesUri(int i, String str) {
        if (i != 14340 && i != 14347 && i != 14349) {
            if (i != 47492) {
                if (i != 14343 && i != 14344) {
                    switch (i) {
                        case 12296:
                        case 12297:
                            return MediaStore.Audio.Media.getContentUri(str);
                        case 12298:
                        case 12299:
                        case 12300:
                            break;
                        default:
                            switch (i) {
                                default:
                                    switch (i) {
                                        case MtpConstants.FORMAT_JP2 /* 14351 */:
                                        case MtpConstants.FORMAT_JPX /* 14352 */:
                                        case MtpConstants.FORMAT_DNG /* 14353 */:
                                        case MtpConstants.FORMAT_HEIF /* 14354 */:
                                            break;
                                        default:
                                            switch (i) {
                                                case 47360:
                                                case MtpConstants.FORMAT_WMA /* 47361 */:
                                                case MtpConstants.FORMAT_OGG /* 47362 */:
                                                case MtpConstants.FORMAT_AAC /* 47363 */:
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case MtpConstants.FORMAT_UNDEFINED_VIDEO /* 47488 */:
                                                        case MtpConstants.FORMAT_WMV /* 47489 */:
                                                        case MtpConstants.FORMAT_MP4_CONTAINER /* 47490 */:
                                                            break;
                                                        default:
                                                            return MediaStore.Files.getContentUri(str);
                                                    }
                                            }
                                    }
                                case 14336:
                                case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
                                case MtpConstants.FORMAT_TIFF_EP /* 14338 */:
                                    return MediaStore.Images.Media.getContentUri(str);
                            }
                    }
                }
            }
            return MediaStore.Video.Media.getContentUri(str);
        }
        return MediaStore.Images.Media.getContentUri(str);
    }

    private int[] getSupportedDeviceProperties() {
        return DEVICE_PROPERTIES;
    }

    private int[] getSupportedPlaybackFormats() {
        return PLAYBACK_FORMATS;
    }

    public MtpDatabase(Context context, String[] strArr) throws Throwable {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mStorageMap = new HashMap<>();
        this.mPropertyGroupsByProperty = new SparseArray<>();
        this.mPropertyGroupsByFormat = new SparseArray<>();
        this.mSkipThumbForHost = false;
        this.mHostIsWindows = false;
        this.mBatteryReceiver = new BroadcastReceiver() { // from class: android.mtp.MtpDatabase.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                    MtpDatabase.this.mBatteryScale = intent.getIntExtra("scale", 0);
                    int intExtra = intent.getIntExtra("level", 0);
                    if (intExtra != MtpDatabase.this.mBatteryLevel) {
                        MtpDatabase.this.mBatteryLevel = intExtra;
                        try {
                            if (MtpDatabase.this.mServer != null) {
                                MtpDatabase.this.mServer.sendDevicePropertyChanged(MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL);
                            }
                        } catch (NullPointerException e) {
                            Log.e(MtpDatabase.TAG, e.getMessage());
                        }
                    }
                }
            }
        };
        this.mIsBound = new boolean[]{false, false};
        this.isStratCommand = true;
        this.mService = new Messenger[2];
        this.mMessenger = new Messenger(new IncomingHandler());
        this.currentServiceID = -1;
        this.serviceComponent = new ComponentName[2];
        this.conn = new ServiceConnection[2];
        native_setup();
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mMediaProvider = context.getContentResolver().acquireContentProviderClient(AppJankStats.WIDGET_CATEGORY_MEDIA);
        this.mManager = new MtpStorageManager(new MtpStorageManager.MtpNotifier() { // from class: android.mtp.MtpDatabase.2
            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectAdded(int i) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectAdded(i);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectRemoved(int i) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectRemoved(i);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectInfoChanged(int i) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectInfoChanged(i);
                }
            }
        }, strArr == null ? null : Sets.newHashSet(strArr));
        initDeviceProperties(context);
        this.mDeviceType = SystemProperties.getInt("sys.usb.mtp.device_type", 3);
        if (BnRConstants.DEVICETYPE_TABLET.equals(SystemProperties.get("ro.build.characteristics"))) {
            this.mDeviceType = 5;
        }
        closeGuard.open("close");
    }

    public void setServer(MtpServer mtpServer) {
        this.mServer = mtpServer;
        try {
            this.mContext.unregisterReceiver(this.mBatteryReceiver);
        } catch (IllegalArgumentException unused) {
        }
        if (mtpServer != null) {
            this.mContext.registerReceiver(this.mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mManager.close();
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            ContentProviderClient contentProviderClient = this.mMediaProvider;
            if (contentProviderClient != null) {
                contentProviderClient.close();
            }
            native_finalize();
        }
    }

    protected void finalize() throws Throwable {
        try {
            CloseGuard closeGuard = this.mCloseGuard;
            if (closeGuard != null) {
                closeGuard.warnIfOpen();
            }
            close();
        } finally {
            super.finalize();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$addStorage$0() {
        return Boolean.valueOf(this.mHostIsWindows);
    }

    public void addStorage(StorageVolume storageVolume) {
        MtpStorage mtpStorageAddMtpStorage = this.mManager.addMtpStorage(storageVolume, new Supplier() { // from class: android.mtp.MtpDatabase$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.lambda$addStorage$0();
            }
        });
        if (this.mStorageMap.containsKey(storageVolume.getPath())) {
            return;
        }
        this.mStorageMap.put(storageVolume.getPath(), mtpStorageAddMtpStorage);
        MtpServer mtpServer = this.mServer;
        if (mtpServer != null) {
            mtpServer.addStorage(mtpStorageAddMtpStorage);
        }
    }

    public void removeStorage(StorageVolume storageVolume) {
        MtpStorage mtpStorage = this.mStorageMap.get(storageVolume.getPath());
        if (mtpStorage == null) {
            return;
        }
        MtpServer mtpServer = this.mServer;
        if (mtpServer != null) {
            mtpServer.removeStorage(mtpStorage);
        }
        this.mManager.removeMtpStorage(mtpStorage);
        this.mStorageMap.remove(storageVolume.getPath());
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x005a A[PHI: r4
      0x005a: PHI (r4v4 android.database.sqlite.SQLiteDatabase) = (r4v3 android.database.sqlite.SQLiteDatabase), (r4v5 android.database.sqlite.SQLiteDatabase) binds: [B:28:0x0070, B:19:0x0058] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void initDeviceProperties(Context context) throws Throwable {
        Throwable th;
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase;
        this.mDeviceProperties = context.getSharedPreferences("device-properties", 0);
        if (context.getDatabasePath("device-properties").exists()) {
            Cursor cursorQuery = null;
            try {
                try {
                    sQLiteDatabaseOpenOrCreateDatabase = context.openOrCreateDatabase("device-properties", 0, null);
                    if (sQLiteDatabaseOpenOrCreateDatabase != null) {
                        try {
                            cursorQuery = sQLiteDatabaseOpenOrCreateDatabase.query("properties", new String[]{"_id", "code", "value"}, null, null, null, null, null);
                            if (cursorQuery != null) {
                                SharedPreferences.Editor editorEdit = this.mDeviceProperties.edit();
                                while (cursorQuery.moveToNext()) {
                                    editorEdit.putString(cursorQuery.getString(1), cursorQuery.getString(2));
                                }
                                editorEdit.apply();
                            }
                        } catch (Exception e) {
                            e = e;
                            Log.e(TAG, "failed to migrate device properties", e);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (sQLiteDatabaseOpenOrCreateDatabase != null) {
                            }
                            context.deleteDatabase("device-properties");
                            this.mHostType = "";
                            this.mSkipThumbForHost = false;
                            this.mHostIsWindows = false;
                        }
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (0 != 0) {
                        cursorQuery.close();
                    }
                    if (0 == 0) {
                        cursorQuery.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                sQLiteDatabaseOpenOrCreateDatabase = null;
            } catch (Throwable th3) {
                th = th3;
                if (0 != 0) {
                }
                if (0 == 0) {
                }
            }
            if (sQLiteDatabaseOpenOrCreateDatabase != null) {
                sQLiteDatabaseOpenOrCreateDatabase.close();
            }
            context.deleteDatabase("device-properties");
        }
        this.mHostType = "";
        this.mSkipThumbForHost = false;
        this.mHostIsWindows = false;
    }

    public int beginSendObject(String str, int i, int i2, int i3) {
        MtpStorageManager.MtpObject storageRoot = i2 == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i2);
        if (storageRoot == null) {
            return -1;
        }
        return this.mManager.beginSendObject(storageRoot, Paths.get(str, new String[0]).getFileName().toString(), i);
    }

    private void endSendObject(int i, boolean z) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null || !this.mManager.endSendObject(object, z)) {
            Log.e(TAG, "Failed to successfully end send object");
        } else if (z) {
            updateMediaStore(this.mContext, object.getPath().toFile());
        }
    }

    private void rescanFile(String str, int i, int i2) {
        MediaStore.scanFile(this.mContext.getContentResolver(), new File(str));
    }

    private int[] getObjectList(int i, int i2, int i3) throws Throwable {
        List<MtpStorageManager.MtpObject> objects = this.mManager.getObjects(i3, i2, i, true);
        if (objects == null) {
            return null;
        }
        int[] iArr = new int[objects.size()];
        for (int i4 = 0; i4 < objects.size(); i4++) {
            iArr[i4] = objects.get(i4).getId();
        }
        return iArr;
    }

    public int getNumObjects(int i, int i2, int i3) throws Throwable {
        List<MtpStorageManager.MtpObject> objects = this.mManager.getObjects(i3, i2, i, false);
        if (objects == null) {
            return -1;
        }
        return objects.size();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private MtpPropertyList getObjectPropertyList(int i, int i2, int i3, int i4, int i5) throws Throwable {
        MtpStorageManager.MtpObject object;
        MtpPropertyGroup mtpPropertyGroup;
        MtpPropertyGroup mtpPropertyGroup2;
        if (i3 == 0) {
            if (i4 == 0) {
                return new MtpPropertyList(8198);
            }
            return new MtpPropertyList(MtpConstants.RESPONSE_SPECIFICATION_BY_GROUP_UNSUPPORTED);
        }
        if (i5 == -1 && (i == 0 || i == -1)) {
            i5 = 0;
            i = -1;
        }
        if (i5 != 0 && i5 != 1) {
            return new MtpPropertyList(MtpConstants.RESPONSE_SPECIFICATION_BY_DEPTH_UNSUPPORTED);
        }
        List<MtpStorageManager.MtpObject> objects = null;
        if (i == -1) {
            List<MtpStorageManager.MtpObject> objects2 = this.mManager.getObjects(0, i2, -1, true);
            if (objects2 == null) {
                return new MtpPropertyList(8201);
            }
            objects = objects2;
            object = null;
        } else if (i == 0) {
            object = null;
        } else {
            object = this.mManager.getObject(i);
            if (object == null) {
                return new MtpPropertyList(8201);
            }
            if (object.getFormat() != i2 && i2 != 0) {
            }
        }
        if (i == 0 || i5 == 1) {
            if (i == 0) {
                i = -1;
            }
            objects = this.mManager.getObjects(i, i2, -1, true);
            if (objects == null) {
                return new MtpPropertyList(8201);
            }
        }
        if (objects == null) {
            objects = new ArrayList<>();
        }
        if (object != null) {
            objects.add(object);
        }
        MtpPropertyList mtpPropertyList = new MtpPropertyList(8193);
        for (MtpStorageManager.MtpObject mtpObject : objects) {
            if (i3 == -1) {
                if (i2 == 0 && i != 0 && i != -1) {
                    i2 = mtpObject.getFormat();
                }
                mtpPropertyGroup = this.mPropertyGroupsByFormat.get(i2);
                if (mtpPropertyGroup == null) {
                    mtpPropertyGroup2 = new MtpPropertyGroup(getSupportedObjectProperties(i2));
                    this.mPropertyGroupsByFormat.put(i2, mtpPropertyGroup2);
                    mtpPropertyGroup = mtpPropertyGroup2;
                }
            } else {
                mtpPropertyGroup = this.mPropertyGroupsByProperty.get(i3);
                if (mtpPropertyGroup == null) {
                    mtpPropertyGroup2 = new MtpPropertyGroup(new int[]{i3});
                    this.mPropertyGroupsByProperty.put(i3, mtpPropertyGroup2);
                    mtpPropertyGroup = mtpPropertyGroup2;
                }
            }
            int propertyList = mtpPropertyGroup.getPropertyList(this.mMediaProvider, mtpObject.getVolumeName(), mtpObject, mtpPropertyList);
            if (propertyList != 8193) {
                return new MtpPropertyList(propertyList);
            }
        }
        return mtpPropertyList;
    }

    private int renameFile(int i, String str) throws ErrnoException {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        Path path = object.getPath();
        if (!this.mManager.beginRenameObject(object, str)) {
            return 8194;
        }
        Path path2 = object.getPath();
        boolean zRenameTo = path.toFile().renameTo(path2.toFile());
        try {
            Os.access(path.toString(), OsConstants.F_OK);
            Os.access(path2.toString(), OsConstants.F_OK);
        } catch (ErrnoException unused) {
        }
        if (!this.mManager.endRenameObject(object, path.getFileName().toString(), zRenameTo)) {
            Log.e(TAG, "Failed to end rename object");
        }
        if (!zRenameTo) {
            return 8194;
        }
        updateMediaStore(this.mContext, path.toFile());
        updateMediaStore(this.mContext, path2.toFile());
        return 8193;
    }

    private int beginMoveObject(int i, int i2, int i3) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        MtpStorageManager.MtpObject storageRoot = i2 == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i2);
        if (object == null || storageRoot == null) {
            return 8201;
        }
        return this.mManager.beginMoveObject(object, storageRoot) ? 8193 : 8194;
    }

    private void endMoveObject(int i, int i2, int i3, int i4, int i5, boolean z) {
        MtpStorageManager.MtpObject storageRoot = i == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i);
        MtpStorageManager.MtpObject storageRoot2 = i2 == 0 ? this.mManager.getStorageRoot(i4) : this.mManager.getObject(i2);
        String name = this.mManager.getObject(i5).getName();
        if (storageRoot2 == null || storageRoot == null || !this.mManager.endMoveObject(storageRoot, storageRoot2, name, z)) {
            Log.e(TAG, "Failed to end move object");
            return;
        }
        MtpStorageManager.MtpObject object = this.mManager.getObject(i5);
        if (!z || object == null) {
            return;
        }
        Path pathResolve = storageRoot2.getPath().resolve(name);
        updateMediaStore(this.mContext, storageRoot.getPath().resolve(name).toFile());
        updateMediaStore(this.mContext, pathResolve.toFile());
    }

    private int beginCopyObject(int i, int i2, int i3) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        MtpStorageManager.MtpObject storageRoot = i2 == 0 ? this.mManager.getStorageRoot(i3) : this.mManager.getObject(i2);
        if (object == null || storageRoot == null) {
            return 8201;
        }
        return this.mManager.beginCopyObject(object, storageRoot);
    }

    private void endCopyObject(int i, boolean z) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null || !this.mManager.endCopyObject(object, z)) {
            Log.e(TAG, "Failed to end copy object");
        } else if (z) {
            updateMediaStore(this.mContext, object.getPath().toFile());
        }
    }

    public static class UpdateMediaStoreThread implements Runnable {
        private File file;
        private ContentResolver resolver;

        public UpdateMediaStoreThread(ContentResolver contentResolver, File file) {
            this.resolver = contentResolver;
            this.file = file;
        }

        @Override // java.lang.Runnable
        public void run() {
            Log.v(MtpDatabase.TAG, "start scan files");
            if (!this.file.isDirectory() && this.file.getName().toLowerCase(Locale.ROOT).endsWith(MtpDatabase.NO_MEDIA)) {
                File parentFile = this.file.getParentFile();
                if (parentFile != null) {
                    MediaStore.scanFile(this.resolver, parentFile);
                }
            } else {
                MediaStore.scanFile(this.resolver, this.file);
            }
            Log.i(MtpDatabase.TAG, "finish scan files");
        }
    }

    private static void updateMediaStore(Context context, File file) {
        new Thread(new UpdateMediaStoreThread(context.getContentResolver(), file)).start();
    }

    private int setObjectProperty(int i, int i2, long j, String str) {
        return i2 != 56327 ? MtpConstants.RESPONSE_OBJECT_PROP_NOT_SUPPORTED : renameFile(i, str);
    }

    private int getDeviceProperty(int i, long[] jArr, char[] cArr) {
        int i2;
        switch (i) {
            case MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL /* 20481 */:
                jArr[0] = this.mBatteryLevel;
                jArr[1] = this.mBatteryScale;
                return 8193;
            case MtpConstants.DEVICE_PROPERTY_IMAGE_SIZE /* 20483 */:
                Display defaultDisplay = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                String str = Integer.toString(defaultDisplay.getMaximumSizeDimension()) + "x" + Integer.toString(defaultDisplay.getMaximumSizeDimension());
                str.getChars(0, str.length(), cArr, 0);
                cArr[str.length()] = 0;
                return 8193;
            case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                String string = Settings.Global.getString(this.mContext.getContentResolver(), Settings.Global.DEVICE_NAME);
                int length = string.length();
                i2 = length <= 255 ? length : 255;
                string.getChars(0, i2, cArr, 0);
                cArr[i2] = 0;
            case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
                return 8193;
            case MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO /* 54278 */:
                String str2 = this.mHostType;
                int length2 = str2.length();
                i2 = length2 <= 255 ? length2 : 255;
                str2.getChars(0, i2, cArr, 0);
                cArr[i2] = 0;
                return 8193;
            case MtpConstants.DEVICE_PROPERTY_PERCEIVED_DEVICE_TYPE /* 54279 */:
                jArr[0] = this.mDeviceType;
                return 8193;
            default:
                return 8202;
        }
    }

    private int setDeviceProperty(int i, long j, String str) {
        switch (i) {
            case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
                break;
            case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                try {
                    Settings.Global.putString(this.mContext.getContentResolver(), Settings.Global.DEVICE_NAME, str);
                    SharedPreferences.Editor editorEdit = this.mDeviceProperties.edit();
                    editorEdit.putString(Integer.toString(i), str);
                    if (editorEdit.commit()) {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return 8194;
                }
                break;
            case MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO /* 54278 */:
                this.mHostType = str;
                Log.d(TAG, "setDeviceProperty." + Integer.toHexString(i) + "=" + str);
                if (str.startsWith("Android/")) {
                    this.mSkipThumbForHost = true;
                    break;
                } else if (str.startsWith("Windows/")) {
                    this.mHostIsWindows = true;
                    break;
                }
                break;
        }
        return 8193;
    }

    private boolean getObjectInfo(int i, int[] iArr, char[] cArr, long[] jArr) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return false;
        }
        iArr[0] = object.getStorageId();
        iArr[1] = object.getFormat();
        iArr[2] = object.getParent().isRoot() ? 0 : object.getParent().getId();
        int iMin = Integer.min(object.getName().length(), 255);
        object.getName().getChars(0, iMin, cArr, 0);
        cArr[iMin] = 0;
        jArr[0] = object.getModifiedTime();
        jArr[1] = object.getModifiedTime();
        return true;
    }

    private int getObjectFilePath(int i, char[] cArr, long[] jArr) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        String string = object.getPath().toString();
        int iMin = Integer.min(string.length(), 4096);
        string.getChars(0, iMin, cArr, 0);
        cArr[iMin] = 0;
        jArr[0] = object.getSize();
        jArr[1] = object.getFormat();
        return 8193;
    }

    private int openFilePath(String str, boolean z) {
        Uri uriScanFile = MediaStore.scanFile(this.mContext.getContentResolver(), new File(str));
        if (uriScanFile == null) {
            Log.i(TAG, "Failed to obtain URI for openFile with transcode support: " + str);
            return -1;
        }
        try {
            Log.i(TAG, "openFile with transcode support: " + str);
            Bundle bundle = new Bundle();
            if (z) {
                bundle.putParcelable("android.provider.extra.MEDIA_CAPABILITIES", new ApplicationMediaCapabilities.Builder().addUnsupportedVideoMimeType("video/hevc").build());
            } else {
                bundle.putParcelable("android.provider.extra.MEDIA_CAPABILITIES", new ApplicationMediaCapabilities.Builder().addSupportedVideoMimeType("video/hevc").build());
            }
            return this.mMediaProvider.openTypedAssetFileDescriptor(uriScanFile, "*/*", bundle).getParcelFileDescriptor().detachFd();
        } catch (RemoteException | FileNotFoundException e) {
            Log.w(TAG, "Failed to openFile with transcode support: " + str, e);
            return -1;
        }
    }

    private int getObjectFormat(int i) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return -1;
        }
        return object.getFormat();
    }

    private byte[] getThumbnailProcess(String str, Bitmap bitmap) {
        try {
            if (bitmap == null) {
                Log.d(TAG, "getThumbnailProcess: Fail to generate thumbnail. Probably unsupported or corrupted image");
                return null;
            }
            ByteArrayOutputStream byteArrayOutputStream = null;
            int size = MAX_THUMB_SIZE;
            for (int i = 100; size >= MAX_THUMB_SIZE && i > 0; i -= 10) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
                size = byteArrayOutputStream.size();
            }
            return byteArrayOutputStream.toByteArray();
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "OutOfMemoryError:" + e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean getThumbnailInfo(int i, long[] jArr) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return false;
        }
        int format = object.getFormat();
        if (format != 14340 && format != 14347 && format != 14349) {
            if (format != 47492) {
                if (format != 14343 && format != 14344) {
                    switch (format) {
                        case 12297:
                            jArr[0] = 204800;
                            jArr[1] = 256;
                            jArr[2] = 256;
                            break;
                        default:
                            switch (format) {
                                default:
                                    switch (format) {
                                        case MtpConstants.FORMAT_JP2 /* 14351 */:
                                        case MtpConstants.FORMAT_JPX /* 14352 */:
                                        case MtpConstants.FORMAT_DNG /* 14353 */:
                                        case MtpConstants.FORMAT_HEIF /* 14354 */:
                                            break;
                                        default:
                                            switch (format) {
                                            }
                                    }
                                case 14336:
                                case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
                                case MtpConstants.FORMAT_TIFF_EP /* 14338 */:
                                    jArr[0] = 204800;
                                    jArr[1] = 256;
                                    jArr[2] = 256;
                                    if (this.mSkipThumbForHost) {
                                        Log.d(TAG, "getThumbnailInfo: Skip runtime thumbnail.");
                                        break;
                                    }
                                    break;
                            }
                        case 12298:
                        case 12299:
                        case 12300:
                            jArr[0] = 204800;
                            jArr[1] = 256;
                            jArr[2] = 256;
                            break;
                    }
                    return false;
                }
            }
            jArr[0] = 204800;
            jArr[1] = 256;
            jArr[2] = 256;
            return true;
        }
        jArr[0] = 204800;
        jArr[1] = 256;
        jArr[2] = 256;
        if (this.mSkipThumbForHost) {
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007f A[FALL_THROUGH, PHI: r2
      0x007f: PHI (r2v1 ??) = 
      (r2v0 ??)
      (r2v10 ??)
      (r2v11 ??)
      (r2v14 ??)
      (r2v17 ?? I:??[int, float, short, byte, char])
      (r2v18 ?? I:??[int, float, short, byte, char])
      (r2v15 ??)
     binds: [B:6:0x001a, B:8:0x001e, B:10:0x0022, B:14:0x002d, B:16:0x0031, B:18:0x0036, B:19:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0103 A[PHI: r2
      0x0103: PHI (r2v4 android.database.Cursor) = (r2v5 android.database.Cursor), (r2v8 android.database.Cursor) binds: [B:49:0x0101, B:43:0x00f3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0060 A[EXC_TOP_SPLITTER, FALL_THROUGH, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r2v15 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v18 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] getThumbnailData(int i) throws Throwable {
        Throwable th;
        Cursor cursorQuery;
        Bitmap bitmapLoadThumbnail;
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        AutoCloseable autoCloseable = null;
        if (object == null) {
            return null;
        }
        String string = object.getPath().toString();
        int format = object.getFormat();
        ?? r2 = 14340;
        if (format != 14340) {
            r2 = 14347;
            if (format != 14347) {
                r2 = 14349;
                if (format == 14349) {
                    CancellationSignal cancellationSignal = new CancellationSignal();
                    try {
                        try {
                            if (this.mSkipThumbForHost) {
                                Log.d(TAG, "getThumbnailData: Skip runtime thumbnail.");
                            }
                            cursorQuery = this.mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, PATH_WHERE, new String[]{string}, null);
                        } catch (Throwable th2) {
                            th = th2;
                            autoCloseable = r2;
                            if (autoCloseable == null) {
                                autoCloseable.close();
                                throw th;
                            }
                            throw th;
                        }
                    } catch (IOException unused) {
                        cursorQuery = null;
                    } catch (Throwable th3) {
                        th = th3;
                        if (autoCloseable == null) {
                        }
                    }
                    if (cursorQuery != null) {
                        try {
                            if (cursorQuery.getCount() > 0) {
                                cursorQuery.moveToFirst();
                                bitmapLoadThumbnail = this.mContext.getContentResolver().loadThumbnail(Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + "/" + cursorQuery.getInt(cursorQuery.getColumnIndex("_id"))), new Size(256, 256), cancellationSignal);
                            } else {
                                bitmapLoadThumbnail = null;
                            }
                        } catch (IOException unused2) {
                            Log.w(TAG, "cannot load thumbnail.");
                            if (cursorQuery != null) {
                            }
                            try {
                                return this.getThumbnailProcess(string, ThumbnailUtils.createImageThumbnail(new File(string), new Size(256, 256), cancellationSignal));
                            } catch (IOException unused3) {
                                Log.e(TAG, "cannot create thumbnail.");
                            }
                        }
                        if (bitmapLoadThumbnail != null) {
                            byte[] thumbnailProcess = getThumbnailProcess(string, bitmapLoadThumbnail);
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return thumbnailProcess;
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return this.getThumbnailProcess(string, ThumbnailUtils.createImageThumbnail(new File(string), new Size(256, 256), cancellationSignal));
                    }
                } else if (format != 47492) {
                    r2 = 14343;
                    if (format != 14343) {
                        r2 = 14344;
                        r2 = 14344;
                        r2 = 14344;
                        if (format != 14344) {
                            switch (format) {
                                case 12297:
                                    try {
                                        return getThumbnailProcess(string, ThumbnailUtils.createAudioThumbnail(new File(string), new Size(256, 256), new CancellationSignal()));
                                    } catch (IOException unused4) {
                                        Log.e(TAG, "cannot create thumbnail");
                                        break;
                                    }
                                default:
                                    switch (format) {
                                        default:
                                            switch (format) {
                                                case MtpConstants.FORMAT_JP2 /* 14351 */:
                                                case MtpConstants.FORMAT_JPX /* 14352 */:
                                                case MtpConstants.FORMAT_DNG /* 14353 */:
                                                case MtpConstants.FORMAT_HEIF /* 14354 */:
                                                    break;
                                                default:
                                                    switch (format) {
                                                    }
                                            }
                                        case 14336:
                                        case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
                                        case MtpConstants.FORMAT_TIFF_EP /* 14338 */:
                                            break;
                                    }
                                case 12298:
                                case 12299:
                                case 12300:
                                    try {
                                        return getThumbnailProcess(string, ThumbnailUtils.createVideoThumbnail(new File(string), new Size(256, 256), new CancellationSignal()));
                                    } catch (IOException unused5) {
                                        Log.e(TAG, "cannot create thumbnail");
                                        break;
                                    }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private int beginDeleteObject(int i) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        return !this.mManager.beginRemoveObject(object) ? 8194 : 8193;
    }

    private void endDeleteObject(int i, boolean z) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return;
        }
        if (!this.mManager.endRemoveObject(object, z)) {
            Log.e(TAG, "Failed to end remove object");
        }
        if (z) {
            deleteFromMedia(object, object.getPath(), object.isDir());
        }
    }

    private void deleteFromMedia(MtpStorageManager.MtpObject mtpObject, Path path, boolean z) {
        Uri contentUri = MediaStore.Files.getContentUri(mtpObject.getVolumeName());
        if (z) {
            try {
                this.mMediaProvider.delete(contentUri, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new String[]{path + "/%", Integer.toString(path.toString().length() + 1), path.toString() + "/"});
            } catch (Exception unused) {
                Log.d(TAG, "Failed to delete " + path + " from MediaProvider");
                return;
            }
        }
        if (this.mMediaProvider.delete(contentUri, PATH_WHERE, new String[]{path.toString()}) == 0) {
            Log.i(TAG, "MediaProvider didn't delete " + path);
        }
        updateMediaStore(this.mContext, path.toFile());
    }

    private int setObjectReferences(int i, int[] iArr) throws IOException {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8194;
        }
        String string = object.getPath().toString();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(string));
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF8");
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    try {
                        for (int i2 : iArr) {
                            MtpStorageManager.MtpObject object2 = this.mManager.getObject(i2);
                            if (object2 != null) {
                                bufferedWriter.write(object2.getPath().toString() + ShaderAssembler.NEWLINE);
                            }
                        }
                        bufferedWriter.close();
                        outputStreamWriter.close();
                        fileOutputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            e3.printStackTrace();
        }
        String str = string.substring(0, string.lastIndexOf(46)) + ".m3u";
        try {
            Files.copy(Paths.get(string, new String[0]), Paths.get(str, new String[0]), StandardCopyOption.REPLACE_EXISTING);
            updateMediaStore(this.mContext, new File(str));
            return 8193;
        } catch (IOException e4) {
            e4.printStackTrace();
            return 8194;
        }
    }

    public void initializeSerivce() {
        this.isStratCommand = false;
        this.serviceComponent[0] = new ComponentName(AGENT_PACKAGE_NAME, AGENT_SERVICE_NAME);
        this.serviceComponent[1] = new ComponentName(SSM_PACKAGE_NAME, SSM_SERVICE_NAME);
        this.conn[0] = new ServiceConnection() { // from class: android.mtp.MtpDatabase.3
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(MtpDatabase.TAG, "onServiceDisconnected()");
                MtpDatabase.this.mService[0] = null;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MtpDatabase.this.mService[0] = new Messenger(iBinder);
                Log.d(MtpDatabase.TAG, "onServiceConnected()");
                try {
                    Message messageObtain = Message.obtain((Handler) null, 1);
                    messageObtain.replyTo = MtpDatabase.this.mMessenger;
                    MtpDatabase.this.mService[0].send(messageObtain);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(MtpDatabase.TAG, "say hello exception");
                }
            }
        };
        this.conn[1] = new ServiceConnection() { // from class: android.mtp.MtpDatabase.4
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(MtpDatabase.TAG, "onServiceDisconnected()");
                MtpDatabase.this.mService[1] = null;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MtpDatabase.this.mService[1] = new Messenger(iBinder);
                Log.d(MtpDatabase.TAG, "onServiceConnected()");
                try {
                    Message messageObtain = Message.obtain((Handler) null, 1);
                    messageObtain.replyTo = MtpDatabase.this.mMessenger;
                    MtpDatabase.this.mService[1].send(messageObtain);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(MtpDatabase.TAG, "say hello exception");
                }
            }
        };
    }

    public static final byte[] intToByteArray(int i) {
        return new byte[]{(byte) i, (byte) (i >> 8), (byte) (i >> 16), (byte) (i >> 24)};
    }

    private byte[] sendSsmMessage(int i, int i2, byte[] bArr) throws UnsupportedEncodingException {
        byte[] bArrIntToByteArray;
        String str = TAG;
        Log.d(str, "sendSsmMessage: commandId=>" + i + "serviceID=>" + i2);
        if (this.isStratCommand) {
            initializeSerivce();
        }
        if (i == 1) {
            this.currentServiceID = i2;
            if (i2 < 0 || this.mIsBound[i2]) {
                Log.d(str, "Already bindservice.");
                return intToByteArray(2);
            }
            try {
                Intent intent = new Intent();
                intent.setComponent(this.serviceComponent[this.currentServiceID]);
                boolean zBindService = this.mContext.bindService(intent, this.conn[this.currentServiceID], 1);
                if (zBindService) {
                    this.mIsBound[this.currentServiceID] = true;
                }
                Log.d(str, "bind result: " + zBindService);
                if (this.mIsBound[this.currentServiceID]) {
                    return intToByteArray(1);
                }
                return intToByteArray(2);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "bind exception");
                return intToByteArray(2);
            }
        }
        if (i == 2) {
            int i3 = this.currentServiceID;
            if (i3 < 0 || this.mService[i3] == null) {
                return intToByteArray(3);
            }
            try {
                Message messageObtain = Message.obtain((Handler) null, 2);
                messageObtain.replyTo = this.mMessenger;
                this.mService[this.currentServiceID].send(messageObtain);
                boolean[] zArr = this.mIsBound;
                int i4 = this.currentServiceID;
                if (zArr[i4]) {
                    this.mContext.unbindService(this.conn[i4]);
                }
                this.mIsBound[this.currentServiceID] = false;
                bArrIntToByteArray = intToByteArray(1);
                this.mService[this.currentServiceID] = null;
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e(TAG, "byebye exception");
                bArrIntToByteArray = intToByteArray(2);
            }
            this.currentServiceID = -1;
            return bArrIntToByteArray;
        }
        if (i == 3) {
            int i5 = this.currentServiceID;
            if (i5 < 0 || this.mService[i5] == null) {
                return intToByteArray(3);
            }
            try {
                Message messageObtain2 = Message.obtain((Handler) null, 3);
                messageObtain2.replyTo = this.mMessenger;
                Bundle bundle = new Bundle();
                bundle.putString("json", new String(bArr, "UTF-8"));
                messageObtain2.obj = bundle;
                this.mService[this.currentServiceID].send(messageObtain2);
                return intToByteArray(1);
            } catch (Exception e3) {
                e3.printStackTrace();
                Log.e(TAG, "send command exception");
                return intToByteArray(2);
            }
        }
        if (i != 4) {
            return null;
        }
        int i6 = this.currentServiceID;
        if (i6 < 0 || this.mService[i6] == null) {
            return intToByteArray(2);
        }
        String str2 = jsonData;
        if (str2 == null) {
            return intToByteArray(3);
        }
        try {
            byte[] bytes = str2.getBytes("UTF-8");
            byte[] bArr2 = new byte[bytes.length + 8];
            System.arraycopy(intToByteArray(1), 0, bArr2, 0, 4);
            System.arraycopy(intToByteArray(bytes.length), 0, bArr2, 4, 4);
            System.arraycopy(bytes, 0, bArr2, 8, bytes.length);
            jsonData = null;
            return bArr2;
        } catch (Exception e4) {
            e4.printStackTrace();
            Log.e(TAG, "get command exception");
            return intToByteArray(2);
        }
    }

    static class IncomingHandler extends Handler {
        IncomingHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Log.d(MtpDatabase.TAG, "handleMessage: " + message.what);
            if (message.what == 3) {
                MtpDatabase.jsonData = ((Bundle) message.obj).getString("json");
                Log.d(MtpDatabase.TAG, "resp, result:" + message.arg1);
                return;
            }
            super.handleMessage(message);
        }
    }

    private void setOpenSession(boolean z) {
        Context context = this.mContext;
        if (context != null) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.samsung.android.mtp.SHARED_PREFERENCE", 0).edit();
            editorEdit.putBoolean("opensession", z);
            editorEdit.apply();
        }
    }

    private int getLockStatus() {
        Context context = this.mContext;
        if (context != null) {
            return context.getSharedPreferences("com.samsung.android.mtp.SHARED_PREFERENCE", 0).getInt("deviceLockStatus", 1);
        }
        return -1;
    }

    private int getSayHelloError() {
        Context context = this.mContext;
        if (context != null) {
            if ("1".equals(Settings.System.getString(context.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE))) {
                return 43024;
            }
            if (this.mContext.getSharedPreferences("com.samsung.android.mtp.SHARED_PREFERENCE", 0).getBoolean("knox", false)) {
                return 43025;
            }
        }
        return 0;
    }
}
