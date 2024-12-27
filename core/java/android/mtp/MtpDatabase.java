package android.mtp;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.ApplicationMediaCapabilities;
import android.mtp.MtpStorageManager;
import android.net.Uri;
import android.os.Bundle;
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
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.collect.Sets;
import com.samsung.android.wallpaperbackup.BnRConstants;
import dalvik.system.CloseGuard;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    private static final int THUMB_HEIGHT = 256;
    private static final int THUMB_WIDTH = 256;
    private static final int[] VIDEO_PROPERTIES;
    static String jsonData;
    private int mBatteryLevel;
    private int mBatteryScale;
    private final Context mContext;
    private SharedPreferences mDeviceProperties;
    private int mDeviceType;
    private String mHostType;
    private MtpStorageManager mManager;
    private final ContentProviderClient mMediaProvider;
    private long mNativeContext;
    private MtpServer mServer;
    private static final String TAG = MtpDatabase.class.getSimpleName();
    private static final String[] ID_PROJECTION = {"_id"};
    private static final String[] PATH_PROJECTION = {"_data"};
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final HashMap<String, MtpStorage> mStorageMap = new HashMap<>();
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByProperty = new SparseArray<>();
    private final SparseArray<MtpPropertyGroup> mPropertyGroupsByFormat = new SparseArray<>();
    private boolean mSkipThumbForHost = false;
    private volatile boolean mHostIsWindows = false;
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() { // from class: android.mtp.MtpDatabase.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                MtpDatabase.this.mBatteryScale = intent.getIntExtra("scale", 0);
                int newLevel = intent.getIntExtra("level", 0);
                if (newLevel != MtpDatabase.this.mBatteryLevel) {
                    MtpDatabase.this.mBatteryLevel = newLevel;
                    try {
                        if (MtpDatabase.this.mServer != null) {
                            MtpDatabase.this.mServer.sendDevicePropertyChanged(MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL);
                        }
                    } catch (NullPointerException ex) {
                        Log.e(MtpDatabase.TAG, ex.getMessage());
                    }
                }
            }
        }
    };
    boolean[] mIsBound = {false, false};
    boolean isStratCommand = true;
    private Messenger[] mService = new Messenger[2];
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    private int currentServiceID = -1;
    ComponentName[] serviceComponent = new ComponentName[2];
    ServiceConnection[] conn = new ServiceConnection[2];

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

    private int[] getSupportedObjectProperties(int format) {
        switch (format) {
            case 12296:
            case 12297:
            case MtpConstants.FORMAT_UNDEFINED_AUDIO /* 47360 */:
            case MtpConstants.FORMAT_WMA /* 47361 */:
            case MtpConstants.FORMAT_OGG /* 47362 */:
            case MtpConstants.FORMAT_AAC /* 47363 */:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(AUDIO_PROPERTIES)).toArray();
            case 12298:
            case 12299:
            case 12300:
            case MtpConstants.FORMAT_UNDEFINED_VIDEO /* 47488 */:
            case MtpConstants.FORMAT_WMV /* 47489 */:
            case MtpConstants.FORMAT_MP4_CONTAINER /* 47490 */:
            case MtpConstants.FORMAT_3GP_CONTAINER /* 47492 */:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(VIDEO_PROPERTIES)).toArray();
            case 14336:
            case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case MtpConstants.FORMAT_BMP /* 14340 */:
            case MtpConstants.FORMAT_GIF /* 14343 */:
            case MtpConstants.FORMAT_PNG /* 14347 */:
            case MtpConstants.FORMAT_TIFF /* 14349 */:
            case MtpConstants.FORMAT_JP2 /* 14351 */:
            case MtpConstants.FORMAT_JPX /* 14352 */:
            case MtpConstants.FORMAT_DNG /* 14353 */:
            case MtpConstants.FORMAT_HEIF /* 14354 */:
                return IntStream.concat(Arrays.stream(FILE_PROPERTIES), Arrays.stream(IMAGE_PROPERTIES)).toArray();
            default:
                return FILE_PROPERTIES;
        }
    }

    public static Uri getObjectPropertiesUri(int format, String volumeName) {
        switch (format) {
            case 12296:
            case 12297:
            case MtpConstants.FORMAT_UNDEFINED_AUDIO /* 47360 */:
            case MtpConstants.FORMAT_WMA /* 47361 */:
            case MtpConstants.FORMAT_OGG /* 47362 */:
            case MtpConstants.FORMAT_AAC /* 47363 */:
                return MediaStore.Audio.Media.getContentUri(volumeName);
            case 12298:
            case 12299:
            case 12300:
            case MtpConstants.FORMAT_UNDEFINED_VIDEO /* 47488 */:
            case MtpConstants.FORMAT_WMV /* 47489 */:
            case MtpConstants.FORMAT_MP4_CONTAINER /* 47490 */:
            case MtpConstants.FORMAT_3GP_CONTAINER /* 47492 */:
                return MediaStore.Video.Media.getContentUri(volumeName);
            case 14336:
            case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case MtpConstants.FORMAT_TIFF_EP /* 14338 */:
            case MtpConstants.FORMAT_BMP /* 14340 */:
            case MtpConstants.FORMAT_GIF /* 14343 */:
            case MtpConstants.FORMAT_JFIF /* 14344 */:
            case MtpConstants.FORMAT_PNG /* 14347 */:
            case MtpConstants.FORMAT_TIFF /* 14349 */:
            case MtpConstants.FORMAT_JP2 /* 14351 */:
            case MtpConstants.FORMAT_JPX /* 14352 */:
            case MtpConstants.FORMAT_DNG /* 14353 */:
            case MtpConstants.FORMAT_HEIF /* 14354 */:
                return MediaStore.Images.Media.getContentUri(volumeName);
            default:
                return MediaStore.Files.getContentUri(volumeName);
        }
    }

    private int[] getSupportedDeviceProperties() {
        return DEVICE_PROPERTIES;
    }

    private int[] getSupportedPlaybackFormats() {
        return PLAYBACK_FORMATS;
    }

    private int[] getSupportedCaptureFormats() {
        return null;
    }

    public MtpDatabase(Context context, String[] subDirectories) {
        native_setup();
        this.mContext = (Context) Objects.requireNonNull(context);
        this.mMediaProvider = context.getContentResolver().acquireContentProviderClient("media");
        this.mManager = new MtpStorageManager(new MtpStorageManager.MtpNotifier() { // from class: android.mtp.MtpDatabase.2
            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectAdded(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectAdded(id);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectRemoved(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectRemoved(id);
                }
            }

            @Override // android.mtp.MtpStorageManager.MtpNotifier
            public void sendObjectInfoChanged(int id) {
                if (MtpDatabase.this.mServer != null) {
                    MtpDatabase.this.mServer.sendObjectInfoChanged(id);
                }
            }
        }, subDirectories == null ? null : Sets.newHashSet(subDirectories));
        initDeviceProperties(context);
        this.mDeviceType = SystemProperties.getInt("sys.usb.mtp.device_type", 3);
        if (BnRConstants.DEVICETYPE_TABLET.equals(SystemProperties.get("ro.build.characteristics"))) {
            this.mDeviceType = 5;
        }
        this.mCloseGuard.open("close");
    }

    public void setServer(MtpServer server) {
        this.mServer = server;
        try {
            this.mContext.unregisterReceiver(this.mBatteryReceiver);
        } catch (IllegalArgumentException e) {
        }
        if (server != null) {
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
            if (this.mMediaProvider != null) {
                this.mMediaProvider.close();
            }
            native_finalize();
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
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

    public void addStorage(StorageVolume storage) {
        MtpStorage mtpStorage = this.mManager.addMtpStorage(storage, new Supplier() { // from class: android.mtp.MtpDatabase$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Boolean lambda$addStorage$0;
                lambda$addStorage$0 = MtpDatabase.this.lambda$addStorage$0();
                return lambda$addStorage$0;
            }
        });
        if (!this.mStorageMap.containsKey(storage.getPath())) {
            this.mStorageMap.put(storage.getPath(), mtpStorage);
            if (this.mServer != null) {
                this.mServer.addStorage(mtpStorage);
            }
        }
    }

    public void removeStorage(StorageVolume storage) {
        MtpStorage mtpStorage = this.mStorageMap.get(storage.getPath());
        if (mtpStorage == null) {
            return;
        }
        if (this.mServer != null) {
            this.mServer.removeStorage(mtpStorage);
        }
        this.mManager.removeMtpStorage(mtpStorage);
        this.mStorageMap.remove(storage.getPath());
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x007b, code lost:
    
        r18.deleteDatabase("device-properties");
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0078, code lost:
    
        if (r7 != null) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initDeviceProperties(android.content.Context r18) {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            java.lang.String r3 = "device-properties"
            java.lang.String r4 = "device-properties"
            r5 = 0
            android.content.SharedPreferences r0 = r2.getSharedPreferences(r4, r5)
            r1.mDeviceProperties = r0
            java.io.File r6 = r2.getDatabasePath(r4)
            boolean r0 = r6.exists()
            if (r0 == 0) goto L8a
            r7 = 0
            r8 = 0
            r0 = 0
            android.database.sqlite.SQLiteDatabase r0 = r2.openOrCreateDatabase(r4, r5, r0)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            r7 = r0
            if (r7 == 0) goto L5d
            java.lang.String r10 = "properties"
            java.lang.String r0 = "_id"
            java.lang.String r9 = "code"
            java.lang.String r11 = "value"
            java.lang.String[] r11 = new java.lang.String[]{r0, r9, r11}     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            r15 = 0
            r16 = 0
            r12 = 0
            r13 = 0
            r14 = 0
            r9 = r7
            android.database.Cursor r0 = r9.query(r10, r11, r12, r13, r14, r15, r16)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            r8 = r0
            if (r8 == 0) goto L5d
            android.content.SharedPreferences r0 = r1.mDeviceProperties     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
        L45:
            boolean r9 = r8.moveToNext()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            if (r9 == 0) goto L5a
            r9 = 1
            java.lang.String r9 = r8.getString(r9)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            r10 = 2
            java.lang.String r10 = r8.getString(r10)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            r0.putString(r9, r10)     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
            goto L45
        L5a:
            r0.apply()     // Catch: java.lang.Throwable -> L68 java.lang.Exception -> L6a
        L5d:
            if (r8 == 0) goto L62
            r8.close()
        L62:
            if (r7 == 0) goto L7b
        L64:
            r7.close()
            goto L7b
        L68:
            r0 = move-exception
            goto L7f
        L6a:
            r0 = move-exception
            java.lang.String r9 = android.mtp.MtpDatabase.TAG     // Catch: java.lang.Throwable -> L68
            java.lang.String r10 = "failed to migrate device properties"
            android.util.Log.e(r9, r10, r0)     // Catch: java.lang.Throwable -> L68
            if (r8 == 0) goto L78
            r8.close()
        L78:
            if (r7 == 0) goto L7b
            goto L64
        L7b:
            r2.deleteDatabase(r4)
            goto L8a
        L7f:
            if (r8 == 0) goto L84
            r8.close()
        L84:
            if (r7 == 0) goto L89
            r7.close()
        L89:
            throw r0
        L8a:
            java.lang.String r0 = ""
            r1.mHostType = r0
            r1.mSkipThumbForHost = r5
            r1.mHostIsWindows = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.initDeviceProperties(android.content.Context):void");
    }

    public int beginSendObject(String path, int format, int parent, int storageId) {
        MtpStorageManager mtpStorageManager = this.mManager;
        MtpStorageManager.MtpObject parentObj = parent == 0 ? mtpStorageManager.getStorageRoot(storageId) : mtpStorageManager.getObject(parent);
        if (parentObj == null) {
            return -1;
        }
        Path objPath = Paths.get(path, new String[0]);
        return this.mManager.beginSendObject(parentObj, objPath.getFileName().toString(), format);
    }

    private void endSendObject(int handle, boolean succeeded) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null || !this.mManager.endSendObject(obj, succeeded)) {
            Log.e(TAG, "Failed to successfully end send object");
        } else if (succeeded) {
            updateMediaStore(this.mContext, obj.getPath().toFile());
        }
    }

    private void rescanFile(String path, int handle, int format) {
        MediaStore.scanFile(this.mContext.getContentResolver(), new File(path));
    }

    private int[] getObjectList(int storageID, int format, int parent) {
        List<MtpStorageManager.MtpObject> objs = this.mManager.getObjects(parent, format, storageID, true);
        if (objs == null) {
            return null;
        }
        int[] ret = new int[objs.size()];
        for (int i = 0; i < objs.size(); i++) {
            ret[i] = objs.get(i).getId();
        }
        return ret;
    }

    public int getNumObjects(int storageID, int format, int parent) {
        List<MtpStorageManager.MtpObject> objs = this.mManager.getObjects(parent, format, storageID, false);
        if (objs == null) {
            return -1;
        }
        return objs.size();
    }

    private MtpPropertyList getObjectPropertyList(int handle, int format, int property, int groupCode, int depth) {
        MtpPropertyGroup propertyGroup;
        if (property == 0) {
            if (groupCode == 0) {
                return new MtpPropertyList(8198);
            }
            return new MtpPropertyList(MtpConstants.RESPONSE_SPECIFICATION_BY_GROUP_UNSUPPORTED);
        }
        if (depth == -1 && (handle == 0 || handle == -1)) {
            handle = -1;
            depth = 0;
        }
        if (depth != 0 && depth != 1) {
            return new MtpPropertyList(MtpConstants.RESPONSE_SPECIFICATION_BY_DEPTH_UNSUPPORTED);
        }
        List<MtpStorageManager.MtpObject> objs = null;
        MtpStorageManager.MtpObject thisObj = null;
        if (handle == -1) {
            objs = this.mManager.getObjects(0, format, -1, true);
            if (objs == null) {
                return new MtpPropertyList(8201);
            }
        } else if (handle != 0) {
            MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
            if (obj == null) {
                return new MtpPropertyList(8201);
            }
            if (obj.getFormat() == format || format == 0) {
                thisObj = obj;
            }
        }
        if (handle == 0 || depth == 1) {
            if (handle == 0) {
                handle = -1;
            }
            objs = this.mManager.getObjects(handle, format, -1, true);
            if (objs == null) {
                return new MtpPropertyList(8201);
            }
        }
        if (objs == null) {
            objs = new ArrayList();
        }
        if (thisObj != null) {
            objs.add(thisObj);
        }
        MtpPropertyList ret = new MtpPropertyList(8193);
        for (MtpStorageManager.MtpObject obj2 : objs) {
            if (property == -1) {
                if (format == 0 && handle != 0 && handle != -1) {
                    format = obj2.getFormat();
                }
                propertyGroup = this.mPropertyGroupsByFormat.get(format);
                if (propertyGroup == null) {
                    int[] propertyList = getSupportedObjectProperties(format);
                    propertyGroup = new MtpPropertyGroup(propertyList);
                    this.mPropertyGroupsByFormat.put(format, propertyGroup);
                }
            } else {
                propertyGroup = this.mPropertyGroupsByProperty.get(property);
                if (propertyGroup == null) {
                    int[] propertyList2 = {property};
                    propertyGroup = new MtpPropertyGroup(propertyList2);
                    this.mPropertyGroupsByProperty.put(property, propertyGroup);
                }
            }
            int err = propertyGroup.getPropertyList(this.mMediaProvider, obj2.getVolumeName(), obj2, ret);
            if (err != 8193) {
                return new MtpPropertyList(err);
            }
        }
        return ret;
    }

    private int renameFile(int handle, String newName) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        Path oldPath = obj.getPath();
        if (!this.mManager.beginRenameObject(obj, newName)) {
            return 8194;
        }
        Path newPath = obj.getPath();
        boolean success = oldPath.toFile().renameTo(newPath.toFile());
        try {
            Os.access(oldPath.toString(), OsConstants.F_OK);
            Os.access(newPath.toString(), OsConstants.F_OK);
        } catch (ErrnoException e) {
        }
        if (!this.mManager.endRenameObject(obj, oldPath.getFileName().toString(), success)) {
            Log.e(TAG, "Failed to end rename object");
        }
        if (!success) {
            return 8194;
        }
        updateMediaStore(this.mContext, oldPath.toFile());
        updateMediaStore(this.mContext, newPath.toFile());
        return 8193;
    }

    private int beginMoveObject(int handle, int newParent, int newStorage) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        MtpStorageManager.MtpObject parent = newParent == 0 ? this.mManager.getStorageRoot(newStorage) : this.mManager.getObject(newParent);
        if (obj == null || parent == null) {
            return 8201;
        }
        boolean allowed = this.mManager.beginMoveObject(obj, parent);
        return allowed ? 8193 : 8194;
    }

    private void endMoveObject(int oldParent, int newParent, int oldStorage, int newStorage, int objId, boolean success) {
        MtpStorageManager.MtpObject oldParentObj = oldParent == 0 ? this.mManager.getStorageRoot(oldStorage) : this.mManager.getObject(oldParent);
        MtpStorageManager.MtpObject newParentObj = newParent == 0 ? this.mManager.getStorageRoot(newStorage) : this.mManager.getObject(newParent);
        MtpStorageManager.MtpObject obj = this.mManager.getObject(objId);
        String name = obj.getName();
        if (newParentObj == null || oldParentObj == null || !this.mManager.endMoveObject(oldParentObj, newParentObj, name, success)) {
            Log.e(TAG, "Failed to end move object");
            return;
        }
        MtpStorageManager.MtpObject obj2 = this.mManager.getObject(objId);
        if (!success || obj2 == null) {
            return;
        }
        Path path = newParentObj.getPath().resolve(name);
        Path oldPath = oldParentObj.getPath().resolve(name);
        updateMediaStore(this.mContext, oldPath.toFile());
        updateMediaStore(this.mContext, path.toFile());
    }

    private int beginCopyObject(int handle, int newParent, int newStorage) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        MtpStorageManager.MtpObject parent = newParent == 0 ? this.mManager.getStorageRoot(newStorage) : this.mManager.getObject(newParent);
        if (obj == null || parent == null) {
            return 8201;
        }
        return this.mManager.beginCopyObject(obj, parent);
    }

    private void endCopyObject(int handle, boolean success) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null || !this.mManager.endCopyObject(obj, success)) {
            Log.e(TAG, "Failed to end copy object");
        } else {
            if (!success) {
                return;
            }
            updateMediaStore(this.mContext, obj.getPath().toFile());
        }
    }

    public static class UpdateMediaStoreThread implements Runnable {
        private File file;
        private ContentResolver resolver;

        public UpdateMediaStoreThread(ContentResolver cr, File f) {
            this.resolver = cr;
            this.file = f;
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
        ContentResolver resolver = context.getContentResolver();
        Thread t = new Thread(new UpdateMediaStoreThread(resolver, file));
        t.start();
    }

    private int setObjectProperty(int handle, int property, long intValue, String stringValue) {
        switch (property) {
            case MtpConstants.PROPERTY_OBJECT_FILE_NAME /* 56327 */:
                return renameFile(handle, stringValue);
            default:
                return MtpConstants.RESPONSE_OBJECT_PROP_NOT_SUPPORTED;
        }
    }

    private int getDeviceProperty(int property, long[] outIntValue, char[] outStringValue) {
        switch (property) {
            case MtpConstants.DEVICE_PROPERTY_BATTERY_LEVEL /* 20481 */:
                outIntValue[0] = this.mBatteryLevel;
                outIntValue[1] = this.mBatteryScale;
                break;
            case MtpConstants.DEVICE_PROPERTY_IMAGE_SIZE /* 20483 */:
                Display display = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
                int width = display.getMaximumSizeDimension();
                int height = display.getMaximumSizeDimension();
                String imageSize = Integer.toString(width) + "x" + Integer.toString(height);
                imageSize.getChars(0, imageSize.length(), outStringValue, 0);
                outStringValue[imageSize.length()] = 0;
                break;
            case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
                break;
            case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                String value = Settings.Global.getString(this.mContext.getContentResolver(), Settings.Global.DEVICE_NAME);
                int length = value.length();
                if (length > 255) {
                    length = 255;
                }
                value.getChars(0, length, outStringValue, 0);
                outStringValue[length] = 0;
                break;
            case MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO /* 54278 */:
                String value2 = this.mHostType;
                int length2 = value2.length();
                if (length2 > 255) {
                    length2 = 255;
                }
                value2.getChars(0, length2, outStringValue, 0);
                outStringValue[length2] = 0;
                break;
            case MtpConstants.DEVICE_PROPERTY_PERCEIVED_DEVICE_TYPE /* 54279 */:
                outIntValue[0] = this.mDeviceType;
                break;
        }
        return 8193;
    }

    private int setDeviceProperty(int property, long intValue, String stringValue) {
        switch (property) {
            case MtpConstants.DEVICE_PROPERTY_SYNCHRONIZATION_PARTNER /* 54273 */:
                return 8193;
            case MtpConstants.DEVICE_PROPERTY_DEVICE_FRIENDLY_NAME /* 54274 */:
                try {
                    Settings.Global.putString(this.mContext.getContentResolver(), Settings.Global.DEVICE_NAME, stringValue);
                    SharedPreferences.Editor e = this.mDeviceProperties.edit();
                    e.putString(Integer.toString(property), stringValue);
                    if (e.commit()) {
                        return 8193;
                    }
                    return 8194;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return 8194;
                }
            case MtpConstants.DEVICE_PROPERTY_SESSION_INITIATOR_VERSION_INFO /* 54278 */:
                this.mHostType = stringValue;
                Log.d(TAG, "setDeviceProperty." + Integer.toHexString(property) + "=" + stringValue);
                if (stringValue.startsWith("Android/")) {
                    this.mSkipThumbForHost = true;
                } else if (stringValue.startsWith("Windows/")) {
                    this.mHostIsWindows = true;
                }
                return 8193;
            default:
                return 8202;
        }
    }

    private boolean getObjectInfo(int handle, int[] outStorageFormatParent, char[] outName, long[] outCreatedModified) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return false;
        }
        outStorageFormatParent[0] = obj.getStorageId();
        outStorageFormatParent[1] = obj.getFormat();
        outStorageFormatParent[2] = obj.getParent().isRoot() ? 0 : obj.getParent().getId();
        int nameLen = Integer.min(obj.getName().length(), 255);
        obj.getName().getChars(0, nameLen, outName, 0);
        outName[nameLen] = 0;
        outCreatedModified[0] = obj.getModifiedTime();
        outCreatedModified[1] = obj.getModifiedTime();
        return true;
    }

    private int getObjectFilePath(int handle, char[] outFilePath, long[] outFileLengthFormat) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        String path = obj.getPath().toString();
        int pathLen = Integer.min(path.length(), 4096);
        path.getChars(0, pathLen, outFilePath, 0);
        outFilePath[pathLen] = 0;
        outFileLengthFormat[0] = obj.getSize();
        outFileLengthFormat[1] = obj.getFormat();
        return 8193;
    }

    private int openFilePath(String path, boolean transcode) {
        Uri uri = MediaStore.scanFile(this.mContext.getContentResolver(), new File(path));
        if (uri == null) {
            Log.i(TAG, "Failed to obtain URI for openFile with transcode support: " + path);
            return -1;
        }
        try {
            Log.i(TAG, "openFile with transcode support: " + path);
            Bundle bundle = new Bundle();
            if (transcode) {
                bundle.putParcelable("android.provider.extra.MEDIA_CAPABILITIES", new ApplicationMediaCapabilities.Builder().addUnsupportedVideoMimeType("video/hevc").build());
            } else {
                bundle.putParcelable("android.provider.extra.MEDIA_CAPABILITIES", new ApplicationMediaCapabilities.Builder().addSupportedVideoMimeType("video/hevc").build());
            }
            return this.mMediaProvider.openTypedAssetFileDescriptor(uri, "*/*", bundle).getParcelFileDescriptor().detachFd();
        } catch (RemoteException | FileNotFoundException e) {
            Log.w(TAG, "Failed to openFile with transcode support: " + path, e);
            return -1;
        }
    }

    private int getObjectFormat(int handle) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return -1;
        }
        return obj.getFormat();
    }

    private byte[] getThumbnailProcess(String path, Bitmap bitmap) {
        try {
            if (bitmap == null) {
                Log.d(TAG, "getThumbnailProcess: Fail to generate thumbnail. Probably unsupported or corrupted image");
                return null;
            }
            int streamLength = MAX_THUMB_SIZE;
            ByteArrayOutputStream byteStream = null;
            for (int compressSize = 100; streamLength >= MAX_THUMB_SIZE && compressSize > 0; compressSize -= 10) {
                byteStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, compressSize, byteStream);
                streamLength = byteStream.size();
            }
            byte[] byteArray = byteStream.toByteArray();
            return byteArray;
        } catch (OutOfMemoryError oomEx) {
            Log.w(TAG, "OutOfMemoryError:" + oomEx);
            return null;
        }
    }

    public boolean getThumbnailInfo(int handle, long[] outLongs) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return false;
        }
        switch (obj.getFormat()) {
            case 12297:
                outLongs[0] = 204800;
                outLongs[1] = 256;
                outLongs[2] = 256;
                break;
            case 12298:
            case 12299:
            case 12300:
            case MtpConstants.FORMAT_UNDEFINED_VIDEO /* 47488 */:
            case MtpConstants.FORMAT_WMV /* 47489 */:
            case MtpConstants.FORMAT_MP4_CONTAINER /* 47490 */:
            case MtpConstants.FORMAT_3GP_CONTAINER /* 47492 */:
                outLongs[0] = 204800;
                outLongs[1] = 256;
                outLongs[2] = 256;
                break;
            case 14336:
            case MtpConstants.FORMAT_EXIF_JPEG /* 14337 */:
            case MtpConstants.FORMAT_TIFF_EP /* 14338 */:
            case MtpConstants.FORMAT_BMP /* 14340 */:
            case MtpConstants.FORMAT_GIF /* 14343 */:
            case MtpConstants.FORMAT_JFIF /* 14344 */:
            case MtpConstants.FORMAT_PNG /* 14347 */:
            case MtpConstants.FORMAT_TIFF /* 14349 */:
            case MtpConstants.FORMAT_JP2 /* 14351 */:
            case MtpConstants.FORMAT_JPX /* 14352 */:
            case MtpConstants.FORMAT_DNG /* 14353 */:
            case MtpConstants.FORMAT_HEIF /* 14354 */:
                outLongs[0] = 204800;
                outLongs[1] = 256;
                outLongs[2] = 256;
                if (this.mSkipThumbForHost) {
                    Log.d(TAG, "getThumbnailInfo: Skip runtime thumbnail.");
                    break;
                }
                break;
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0098, code lost:
    
        if (r4 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x009a, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00bf, code lost:
    
        return getThumbnailProcess(r3, android.media.ThumbnailUtils.createImageThumbnail(new java.io.File(r3), new android.util.Size(256, 256), r5));
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00c1, code lost:
    
        android.util.Log.e(android.mtp.MtpDatabase.TAG, "cannot create thumbnail.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00a9, code lost:
    
        if (r4 == null) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getThumbnailData(int r15) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.getThumbnailData(int):byte[]");
    }

    private int beginDeleteObject(int handle) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8201;
        }
        if (!this.mManager.beginRemoveObject(obj)) {
            return 8194;
        }
        return 8193;
    }

    private void endDeleteObject(int handle, boolean success) {
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return;
        }
        if (!this.mManager.endRemoveObject(obj, success)) {
            Log.e(TAG, "Failed to end remove object");
        }
        if (success) {
            deleteFromMedia(obj, obj.getPath(), obj.isDir());
        }
    }

    private void deleteFromMedia(MtpStorageManager.MtpObject obj, Path path, boolean isDir) {
        Uri objectsUri = MediaStore.Files.getContentUri(obj.getVolumeName());
        if (isDir) {
            try {
                this.mMediaProvider.delete(objectsUri, "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)", new String[]{path + "/%", Integer.toString(path.toString().length() + 1), path.toString() + "/"});
            } catch (Exception e) {
                Log.d(TAG, "Failed to delete " + path + " from MediaProvider");
                return;
            }
        }
        String[] whereArgs = {path.toString()};
        if (this.mMediaProvider.delete(objectsUri, PATH_WHERE, whereArgs) == 0) {
            Log.i(TAG, "MediaProvider didn't delete " + path);
        }
        updateMediaStore(this.mContext, path.toFile());
    }

    private int[] getObjectReferences(int handle) {
        return null;
    }

    private int setObjectReferences(int handle, int[] references) {
        FileOutputStream fos;
        MtpStorageManager.MtpObject obj = this.mManager.getObject(handle);
        if (obj == null) {
            return 8194;
        }
        String plaPath = obj.getPath().toString();
        File plaFile = new File(plaPath);
        try {
            fos = new FileOutputStream(plaFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex2) {
            ex2.printStackTrace();
        } catch (NullPointerException ex3) {
            ex3.printStackTrace();
        }
        try {
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF8");
            try {
                BufferedWriter bw = new BufferedWriter(osw);
                try {
                    for (int ref : references) {
                        MtpStorageManager.MtpObject oRef = this.mManager.getObject(ref);
                        if (oRef != null) {
                            String path = oRef.getPath().toString();
                            bw.write(path + "\n");
                        }
                    }
                    bw.close();
                    osw.close();
                    fos.close();
                    String newPath = plaPath.substring(0, plaPath.lastIndexOf(46)) + ".m3u";
                    try {
                        Files.copy(Paths.get(plaPath, new String[0]), Paths.get(newPath, new String[0]), StandardCopyOption.REPLACE_EXISTING);
                        updateMediaStore(this.mContext, new File(newPath));
                        return 8193;
                    } catch (IOException ex4) {
                        ex4.printStackTrace();
                        return 8194;
                    }
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }

    public void initializeSerivce() {
        this.isStratCommand = false;
        this.serviceComponent[0] = new ComponentName(AGENT_PACKAGE_NAME, AGENT_SERVICE_NAME);
        this.serviceComponent[1] = new ComponentName(SSM_PACKAGE_NAME, SSM_SERVICE_NAME);
        this.conn[0] = new ServiceConnection() { // from class: android.mtp.MtpDatabase.3
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.d(MtpDatabase.TAG, "onServiceDisconnected()");
                MtpDatabase.this.mService[0] = null;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                MtpDatabase.this.mService[0] = new Messenger(service);
                Log.d(MtpDatabase.TAG, "onServiceConnected()");
                try {
                    Message msg = Message.obtain((Handler) null, 1);
                    msg.replyTo = MtpDatabase.this.mMessenger;
                    MtpDatabase.this.mService[0].send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(MtpDatabase.TAG, "say hello exception");
                }
            }
        };
        this.conn[1] = new ServiceConnection() { // from class: android.mtp.MtpDatabase.4
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.d(MtpDatabase.TAG, "onServiceDisconnected()");
                MtpDatabase.this.mService[1] = null;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                MtpDatabase.this.mService[1] = new Messenger(service);
                Log.d(MtpDatabase.TAG, "onServiceConnected()");
                try {
                    Message msg = Message.obtain((Handler) null, 1);
                    msg.replyTo = MtpDatabase.this.mMessenger;
                    MtpDatabase.this.mService[1].send(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(MtpDatabase.TAG, "say hello exception");
                }
            }
        };
    }

    public static final byte[] intToByteArray(int value) {
        byte[] byteArray = {(byte) value, (byte) (value >> 8), (byte) (value >> 16), (byte) (value >> 24)};
        return byteArray;
    }

    private byte[] sendSsmMessage(int commandId, int serviceID, byte[] sendData) {
        byte[] resultResponse;
        Log.d(TAG, "sendSsmMessage: commandId=>" + commandId + "serviceID=>" + serviceID);
        if (this.isStratCommand) {
            initializeSerivce();
        }
        switch (commandId) {
            case 1:
                this.currentServiceID = serviceID;
                byte[] resultResponse2 = new byte[4];
                if (this.currentServiceID < 0 || this.mIsBound[this.currentServiceID]) {
                    Log.d(TAG, "Already bindservice.");
                    byte[] resultResponse3 = intToByteArray(2);
                    return resultResponse3;
                }
                try {
                    Intent i = new Intent();
                    i.setComponent(this.serviceComponent[this.currentServiceID]);
                    boolean bSuccess = this.mContext.bindService(i, this.conn[this.currentServiceID], 1);
                    if (bSuccess) {
                        this.mIsBound[this.currentServiceID] = true;
                    }
                    Log.d(TAG, "bind result: " + bSuccess);
                    if (this.mIsBound[this.currentServiceID]) {
                        byte[] resultResponse4 = intToByteArray(1);
                        return resultResponse4;
                    }
                    byte[] resultResponse5 = intToByteArray(2);
                    return resultResponse5;
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "bind exception");
                    byte[] resultResponse6 = intToByteArray(2);
                    return resultResponse6;
                }
            case 2:
                byte[] resultResponse7 = new byte[4];
                if (this.currentServiceID < 0 || this.mService[this.currentServiceID] == null) {
                    byte[] resultResponse8 = intToByteArray(3);
                    return resultResponse8;
                }
                try {
                    Message msg = Message.obtain((Handler) null, 2);
                    msg.replyTo = this.mMessenger;
                    this.mService[this.currentServiceID].send(msg);
                    if (this.mIsBound[this.currentServiceID]) {
                        this.mContext.unbindService(this.conn[this.currentServiceID]);
                    }
                    this.mIsBound[this.currentServiceID] = false;
                    resultResponse = intToByteArray(1);
                    this.mService[this.currentServiceID] = null;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "byebye exception");
                    resultResponse = intToByteArray(2);
                }
                this.currentServiceID = -1;
                return resultResponse;
            case 3:
                byte[] resultResponse9 = new byte[4];
                if (this.currentServiceID < 0 || this.mService[this.currentServiceID] == null) {
                    byte[] resultResponse10 = intToByteArray(3);
                    return resultResponse10;
                }
                try {
                    Message msg2 = Message.obtain((Handler) null, 3);
                    msg2.replyTo = this.mMessenger;
                    Bundle bundle = new Bundle();
                    bundle.putString("json", new String(sendData, "UTF-8"));
                    msg2.obj = bundle;
                    this.mService[this.currentServiceID].send(msg2);
                    byte[] resultResponse11 = intToByteArray(1);
                    return resultResponse11;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    Log.e(TAG, "send command exception");
                    byte[] resultResponse12 = intToByteArray(2);
                    return resultResponse12;
                }
            case 4:
                if (this.currentServiceID < 0 || this.mService[this.currentServiceID] == null) {
                    byte[] resultResponse13 = new byte[4];
                    byte[] resultResponse14 = intToByteArray(2);
                    return resultResponse14;
                }
                if (jsonData != null) {
                    try {
                        byte[] tempData = jsonData.getBytes("UTF-8");
                        byte[] resultResponse15 = new byte[tempData.length + 8];
                        System.arraycopy(intToByteArray(1), 0, resultResponse15, 0, 4);
                        System.arraycopy(intToByteArray(tempData.length), 0, resultResponse15, 4, 4);
                        System.arraycopy(tempData, 0, resultResponse15, 8, tempData.length);
                        jsonData = null;
                        return resultResponse15;
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        Log.e(TAG, "get command exception");
                        byte[] resultResponse16 = new byte[4];
                        byte[] resultResponse17 = intToByteArray(2);
                        return resultResponse17;
                    }
                }
                byte[] resultResponse18 = new byte[4];
                byte[] resultResponse19 = intToByteArray(3);
                return resultResponse19;
            default:
                return null;
        }
    }

    static class IncomingHandler extends Handler {
        IncomingHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.d(MtpDatabase.TAG, "handleMessage: " + msg.what);
            switch (msg.what) {
                case 3:
                    Bundle bundle = (Bundle) msg.obj;
                    MtpDatabase.jsonData = bundle.getString("json");
                    Log.d(MtpDatabase.TAG, "resp, result:" + msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private void setOpenSession(boolean value) {
        if (this.mContext != null) {
            SharedPreferences pref = this.mContext.getSharedPreferences("com.samsung.android.mtp.SHARED_PREFERENCE", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("opensession", value);
            editor.apply();
        }
    }

    private int getLockStatus() {
        if (this.mContext != null) {
            SharedPreferences pref = this.mContext.getSharedPreferences("com.samsung.android.mtp.SHARED_PREFERENCE", 0);
            return pref.getInt("deviceLockStatus", 1);
        }
        return -1;
    }

    private int getSayHelloError() {
        if (this.mContext != null) {
            String emergency_mode = Settings.System.getString(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE);
            if ("1".equals(emergency_mode)) {
                return 43024;
            }
            SharedPreferences pref = this.mContext.getSharedPreferences("com.samsung.android.mtp.SHARED_PREFERENCE", 0);
            if (pref.getBoolean("knox", false)) {
                return 43025;
            }
        }
        return 0;
    }
}
