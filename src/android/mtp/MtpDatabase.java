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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int[] getSupportedObjectProperties(int r1) {
        /*
            r0 = this;
            r0 = 14336(0x3800, float:2.0089E-41)
            if (r1 == r0) goto L56
            r0 = 14337(0x3801, float:2.009E-41)
            if (r1 == r0) goto L56
            r0 = 14340(0x3804, float:2.0095E-41)
            if (r1 == r0) goto L56
            r0 = 14343(0x3807, float:2.0099E-41)
            if (r1 == r0) goto L56
            r0 = 14347(0x380b, float:2.0104E-41)
            if (r1 == r0) goto L56
            r0 = 14349(0x380d, float:2.0107E-41)
            if (r1 == r0) goto L56
            r0 = 47492(0xb984, float:6.655E-41)
            if (r1 == r0) goto L41
            switch(r1) {
                case 12296: goto L2c;
                case 12297: goto L2c;
                case 12298: goto L41;
                case 12299: goto L41;
                case 12300: goto L41;
                default: goto L20;
            }
        L20:
            switch(r1) {
                case 14351: goto L56;
                case 14352: goto L56;
                case 14353: goto L56;
                case 14354: goto L56;
                default: goto L23;
            }
        L23:
            switch(r1) {
                case 47360: goto L2c;
                case 47361: goto L2c;
                case 47362: goto L2c;
                case 47363: goto L2c;
                default: goto L26;
            }
        L26:
            switch(r1) {
                case 47488: goto L41;
                case 47489: goto L41;
                case 47490: goto L41;
                default: goto L29;
            }
        L29:
            int[] r0 = android.mtp.MtpDatabase.FILE_PROPERTIES
            return r0
        L2c:
            int[] r0 = android.mtp.MtpDatabase.FILE_PROPERTIES
            java.util.stream.IntStream r0 = java.util.Arrays.stream(r0)
            int[] r1 = android.mtp.MtpDatabase.AUDIO_PROPERTIES
            java.util.stream.IntStream r1 = java.util.Arrays.stream(r1)
            java.util.stream.IntStream r0 = java.util.stream.IntStream.concat(r0, r1)
            int[] r0 = r0.toArray()
            return r0
        L41:
            int[] r0 = android.mtp.MtpDatabase.FILE_PROPERTIES
            java.util.stream.IntStream r0 = java.util.Arrays.stream(r0)
            int[] r1 = android.mtp.MtpDatabase.VIDEO_PROPERTIES
            java.util.stream.IntStream r1 = java.util.Arrays.stream(r1)
            java.util.stream.IntStream r0 = java.util.stream.IntStream.concat(r0, r1)
            int[] r0 = r0.toArray()
            return r0
        L56:
            int[] r0 = android.mtp.MtpDatabase.FILE_PROPERTIES
            java.util.stream.IntStream r0 = java.util.Arrays.stream(r0)
            int[] r1 = android.mtp.MtpDatabase.IMAGE_PROPERTIES
            java.util.stream.IntStream r1 = java.util.Arrays.stream(r1)
            java.util.stream.IntStream r0 = java.util.stream.IntStream.concat(r0, r1)
            int[] r0 = r0.toArray()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.getSupportedObjectProperties(int):int[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.net.Uri getObjectPropertiesUri(int r1, java.lang.String r2) {
        /*
            r0 = 14340(0x3804, float:2.0095E-41)
            if (r1 == r0) goto L37
            r0 = 14347(0x380b, float:2.0104E-41)
            if (r1 == r0) goto L37
            r0 = 14349(0x380d, float:2.0107E-41)
            if (r1 == r0) goto L37
            r0 = 47492(0xb984, float:6.655E-41)
            if (r1 == r0) goto L32
            r0 = 14343(0x3807, float:2.0099E-41)
            if (r1 == r0) goto L37
            r0 = 14344(0x3808, float:2.01E-41)
            if (r1 == r0) goto L37
            switch(r1) {
                case 12296: goto L2d;
                case 12297: goto L2d;
                case 12298: goto L32;
                case 12299: goto L32;
                case 12300: goto L32;
                default: goto L1c;
            }
        L1c:
            switch(r1) {
                case 14336: goto L37;
                case 14337: goto L37;
                case 14338: goto L37;
                default: goto L1f;
            }
        L1f:
            switch(r1) {
                case 14351: goto L37;
                case 14352: goto L37;
                case 14353: goto L37;
                case 14354: goto L37;
                default: goto L22;
            }
        L22:
            switch(r1) {
                case 47360: goto L2d;
                case 47361: goto L2d;
                case 47362: goto L2d;
                case 47363: goto L2d;
                default: goto L25;
            }
        L25:
            switch(r1) {
                case 47488: goto L32;
                case 47489: goto L32;
                case 47490: goto L32;
                default: goto L28;
            }
        L28:
            android.net.Uri r1 = android.provider.MediaStore.Files.getContentUri(r2)
            return r1
        L2d:
            android.net.Uri r1 = android.provider.MediaStore.Audio.Media.getContentUri(r2)
            return r1
        L32:
            android.net.Uri r1 = android.provider.MediaStore.Video.Media.getContentUri(r2)
            return r1
        L37:
            android.net.Uri r1 = android.provider.MediaStore.Images.Media.getContentUri(r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.getObjectPropertiesUri(int, java.lang.String):android.net.Uri");
    }

    private int[] getSupportedDeviceProperties() {
        return DEVICE_PROPERTIES;
    }

    private int[] getSupportedPlaybackFormats() {
        return PLAYBACK_FORMATS;
    }

    public MtpDatabase(Context context, String[] strArr) {
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
        MtpStorage addMtpStorage = this.mManager.addMtpStorage(storageVolume, new Supplier() { // from class: android.mtp.MtpDatabase$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                Boolean lambda$addStorage$0;
                lambda$addStorage$0 = MtpDatabase.this.lambda$addStorage$0();
                return lambda$addStorage$0;
            }
        });
        if (this.mStorageMap.containsKey(storageVolume.getPath())) {
            return;
        }
        this.mStorageMap.put(storageVolume.getPath(), addMtpStorage);
        MtpServer mtpServer = this.mServer;
        if (mtpServer != null) {
            mtpServer.addStorage(addMtpStorage);
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x005a, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0073, code lost:
    
        r13.deleteDatabase("device-properties");
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0070, code lost:
    
        if (r4 != null) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0058, code lost:
    
        if (r4 != null) goto L20;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initDeviceProperties(android.content.Context r13) {
        /*
            r12 = this;
            java.lang.String r1 = "device-properties"
            r2 = 0
            android.content.SharedPreferences r0 = r13.getSharedPreferences(r1, r2)
            r12.mDeviceProperties = r0
            java.io.File r0 = r13.getDatabasePath(r1)
            boolean r0 = r0.exists()
            if (r0 == 0) goto L84
            r3 = 0
            android.database.sqlite.SQLiteDatabase r4 = r13.openOrCreateDatabase(r1, r2, r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L62
            if (r4 == 0) goto L53
            java.lang.String r5 = "properties"
            java.lang.String r0 = "_id"
            java.lang.String r6 = "code"
            java.lang.String r7 = "value"
            java.lang.String[] r6 = new java.lang.String[]{r0, r6, r7}     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            r10 = 0
            r11 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r3 = r4.query(r5, r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            if (r3 == 0) goto L53
            android.content.SharedPreferences r0 = r12.mDeviceProperties     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            android.content.SharedPreferences$Editor r0 = r0.edit()     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
        L39:
            boolean r5 = r3.moveToNext()     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            if (r5 == 0) goto L4d
            r5 = 1
            java.lang.String r5 = r3.getString(r5)     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            r6 = 2
            java.lang.String r6 = r3.getString(r6)     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            r0.putString(r5, r6)     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            goto L39
        L4d:
            r0.apply()     // Catch: java.lang.Exception -> L51 java.lang.Throwable -> L77
            goto L53
        L51:
            r0 = move-exception
            goto L64
        L53:
            if (r3 == 0) goto L58
            r3.close()
        L58:
            if (r4 == 0) goto L73
        L5a:
            r4.close()
            goto L73
        L5e:
            r0 = move-exception
            r12 = r0
            r4 = r3
            goto L79
        L62:
            r0 = move-exception
            r4 = r3
        L64:
            java.lang.String r5 = android.mtp.MtpDatabase.TAG     // Catch: java.lang.Throwable -> L77
            java.lang.String r6 = "failed to migrate device properties"
            android.util.Log.e(r5, r6, r0)     // Catch: java.lang.Throwable -> L77
            if (r3 == 0) goto L70
            r3.close()
        L70:
            if (r4 == 0) goto L73
            goto L5a
        L73:
            r13.deleteDatabase(r1)
            goto L84
        L77:
            r0 = move-exception
            r12 = r0
        L79:
            if (r3 == 0) goto L7e
            r3.close()
        L7e:
            if (r4 == 0) goto L83
            r4.close()
        L83:
            throw r12
        L84:
            java.lang.String r13 = ""
            r12.mHostType = r13
            r12.mSkipThumbForHost = r2
            r12.mHostIsWindows = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.initDeviceProperties(android.content.Context):void");
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

    private int[] getObjectList(int i, int i2, int i3) {
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

    public int getNumObjects(int i, int i2, int i3) {
        List<MtpStorageManager.MtpObject> objects = this.mManager.getObjects(i3, i2, i, false);
        if (objects == null) {
            return -1;
        }
        return objects.size();
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x005a, code lost:
    
        if (r9 != 0) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:? A[LOOP:0: B:32:0x008a->B:49:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.mtp.MtpPropertyList getObjectPropertyList(int r8, int r9, int r10, int r11, int r12) {
        /*
            Method dump skipped, instructions count: 231
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.getObjectPropertyList(int, int, int, int, int):android.mtp.MtpPropertyList");
    }

    private int renameFile(int i, String str) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        Path path = object.getPath();
        if (!this.mManager.beginRenameObject(object, str)) {
            return 8194;
        }
        Path path2 = object.getPath();
        boolean renameTo = path.toFile().renameTo(path2.toFile());
        try {
            Os.access(path.toString(), OsConstants.F_OK);
            Os.access(path2.toString(), OsConstants.F_OK);
        } catch (ErrnoException unused) {
        }
        if (!this.mManager.endRenameObject(object, path.getFileName().toString(), renameTo)) {
            Log.e(TAG, "Failed to end rename object");
        }
        if (!renameTo) {
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
        Path resolve = storageRoot2.getPath().resolve(name);
        updateMediaStore(this.mContext, storageRoot.getPath().resolve(name).toFile());
        updateMediaStore(this.mContext, resolve.toFile());
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
                    SharedPreferences.Editor edit = this.mDeviceProperties.edit();
                    edit.putString(Integer.toString(i), str);
                    if (edit.commit()) {
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
        int min = Integer.min(object.getName().length(), 255);
        object.getName().getChars(0, min, cArr, 0);
        cArr[min] = 0;
        jArr[0] = object.getModifiedTime();
        jArr[1] = object.getModifiedTime();
        return true;
    }

    private int getObjectFilePath(int i, char[] cArr, long[] jArr) {
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8201;
        }
        String path = object.getPath().toString();
        int min = Integer.min(path.length(), 4096);
        path.getChars(0, min, cArr, 0);
        cArr[min] = 0;
        jArr[0] = object.getSize();
        jArr[1] = object.getFormat();
        return 8193;
    }

    private int openFilePath(String str, boolean z) {
        Uri scanFile = MediaStore.scanFile(this.mContext.getContentResolver(), new File(str));
        if (scanFile == null) {
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
            return this.mMediaProvider.openTypedAssetFileDescriptor(scanFile, "*/*", bundle).getParcelFileDescriptor().detachFd();
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
            int i = MAX_THUMB_SIZE;
            for (int i2 = 100; i >= MAX_THUMB_SIZE && i2 > 0; i2 -= 10) {
                byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream);
                i = byteArrayOutputStream.size();
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean getThumbnailInfo(int r9, long[] r10) {
        /*
            r8 = this;
            android.mtp.MtpStorageManager r0 = r8.mManager
            android.mtp.MtpStorageManager$MtpObject r9 = r0.getObject(r9)
            r0 = 0
            if (r9 != 0) goto La
            return r0
        La:
            int r9 = r9.getFormat()
            r1 = 14340(0x3804, float:2.0095E-41)
            r2 = 2
            r3 = 204800(0x32000, double:1.011846E-318)
            r5 = 256(0x100, double:1.265E-321)
            r7 = 1
            if (r9 == r1) goto L49
            r1 = 14347(0x380b, float:2.0104E-41)
            if (r9 == r1) goto L49
            r1 = 14349(0x380d, float:2.0107E-41)
            if (r9 == r1) goto L49
            r1 = 47492(0xb984, float:6.655E-41)
            if (r9 == r1) goto L42
            r1 = 14343(0x3807, float:2.0099E-41)
            if (r9 == r1) goto L49
            r1 = 14344(0x3808, float:2.01E-41)
            if (r9 == r1) goto L49
            switch(r9) {
                case 12297: goto L3b;
                case 12298: goto L42;
                case 12299: goto L42;
                case 12300: goto L42;
                default: goto L31;
            }
        L31:
            switch(r9) {
                case 14336: goto L49;
                case 14337: goto L49;
                case 14338: goto L49;
                default: goto L34;
            }
        L34:
            switch(r9) {
                case 14351: goto L49;
                case 14352: goto L49;
                case 14353: goto L49;
                case 14354: goto L49;
                default: goto L37;
            }
        L37:
            switch(r9) {
                case 47488: goto L42;
                case 47489: goto L42;
                case 47490: goto L42;
                default: goto L3a;
            }
        L3a:
            return r0
        L3b:
            r10[r0] = r3
            r10[r7] = r5
            r10[r2] = r5
            return r7
        L42:
            r10[r0] = r3
            r10[r7] = r5
            r10[r2] = r5
            return r7
        L49:
            r10[r0] = r3
            r10[r7] = r5
            r10[r2] = r5
            boolean r8 = r8.mSkipThumbForHost
            if (r8 == 0) goto L5a
            java.lang.String r8 = android.mtp.MtpDatabase.TAG
            java.lang.String r9 = "getThumbnailInfo: Skip runtime thumbnail."
            android.util.Log.d(r8, r9)
        L5a:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.getThumbnailInfo(int, long[]):boolean");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(5:11|(2:13|(2:15|(1:17)))|27|28|29) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0078, code lost:
    
        android.util.Log.e(android.mtp.MtpDatabase.TAG, "cannot create thumbnail");
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0101, code lost:
    
        if (r2 == null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0103, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0118, code lost:
    
        return r10.getThumbnailProcess(r0, android.media.ThumbnailUtils.createImageThumbnail(new java.io.File(r0), new android.util.Size(256, 256), r11));
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0119, code lost:
    
        android.util.Log.e(android.mtp.MtpDatabase.TAG, "cannot create thumbnail.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00f3, code lost:
    
        if (r2 != null) goto L50;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0088 A[Catch: all -> 0x00f6, IOException -> 0x00f9, TryCatch #7 {IOException -> 0x00f9, all -> 0x00f6, blocks: (B:34:0x0084, B:36:0x0088, B:37:0x008f), top: B:33:0x0084 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e9 A[Catch: IOException -> 0x00fa, all -> 0x0121, TRY_LEAVE, TryCatch #3 {all -> 0x0121, blocks: (B:57:0x00a5, B:59:0x00ab, B:41:0x00e9, B:47:0x00fa), top: B:33:0x0084 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:66:? A[SYNTHETIC] */
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getThumbnailData(int r11) {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.mtp.MtpDatabase.getThumbnailData(int):byte[]");
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

    private int setObjectReferences(int i, int[] iArr) {
        FileOutputStream fileOutputStream;
        MtpStorageManager.MtpObject object = this.mManager.getObject(i);
        if (object == null) {
            return 8194;
        }
        String path = object.getPath().toString();
        try {
            fileOutputStream = new FileOutputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (NullPointerException e3) {
            e3.printStackTrace();
        }
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
                    String str = path.substring(0, path.lastIndexOf(46)) + ".m3u";
                    try {
                        Files.copy(Paths.get(path, new String[0]), Paths.get(str, new String[0]), StandardCopyOption.REPLACE_EXISTING);
                        updateMediaStore(this.mContext, new File(str));
                        return 8193;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        return 8194;
                    }
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
                    Message obtain = Message.obtain((Handler) null, 1);
                    obtain.replyTo = MtpDatabase.this.mMessenger;
                    MtpDatabase.this.mService[0].send(obtain);
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
                    Message obtain = Message.obtain((Handler) null, 1);
                    obtain.replyTo = MtpDatabase.this.mMessenger;
                    MtpDatabase.this.mService[1].send(obtain);
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

    private byte[] sendSsmMessage(int i, int i2, byte[] bArr) {
        byte[] intToByteArray;
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
                boolean bindService = this.mContext.bindService(intent, this.conn[this.currentServiceID], 1);
                if (bindService) {
                    this.mIsBound[this.currentServiceID] = true;
                }
                Log.d(str, "bind result: " + bindService);
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
                Message obtain = Message.obtain((Handler) null, 2);
                obtain.replyTo = this.mMessenger;
                this.mService[this.currentServiceID].send(obtain);
                boolean[] zArr = this.mIsBound;
                int i4 = this.currentServiceID;
                if (zArr[i4]) {
                    this.mContext.unbindService(this.conn[i4]);
                }
                this.mIsBound[this.currentServiceID] = false;
                intToByteArray = intToByteArray(1);
                this.mService[this.currentServiceID] = null;
            } catch (Exception e2) {
                e2.printStackTrace();
                Log.e(TAG, "byebye exception");
                intToByteArray = intToByteArray(2);
            }
            this.currentServiceID = -1;
            return intToByteArray;
        }
        if (i == 3) {
            int i5 = this.currentServiceID;
            if (i5 < 0 || this.mService[i5] == null) {
                return intToByteArray(3);
            }
            try {
                Message obtain2 = Message.obtain((Handler) null, 3);
                obtain2.replyTo = this.mMessenger;
                Bundle bundle = new Bundle();
                bundle.putString("json", new String(bArr, "UTF-8"));
                obtain2.obj = bundle;
                this.mService[this.currentServiceID].send(obtain2);
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
            SharedPreferences.Editor edit = context.getSharedPreferences("com.samsung.android.mtp.SHARED_PREFERENCE", 0).edit();
            edit.putBoolean("opensession", z);
            edit.apply();
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
