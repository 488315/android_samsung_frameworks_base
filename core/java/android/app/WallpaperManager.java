package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ILocalWallpaperColorConsumer;
import android.app.IWallpaperManagerCallback;
import android.app.WallpaperManager;
import android.app.compat.CompatChanges;
import android.content.APKContents;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorSpace;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadSystemException;
import android.os.Debug;
import android.os.Environment;
import android.os.FactoryTest;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.C4337R;
import com.samsung.android.app.SemWallpaperUtils;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.colortheme.ColorPalette;
import com.samsung.android.wallpaper.colortheme.ColorPaletteCreator;
import com.samsung.android.wallpaper.colortheme.ColorThemeExtractor;
import com.samsung.android.wallpaper.colortheme.monet.ColorScheme;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaperbackup.BnRConstants;
import com.samsung.android.wallpaperbackup.WallpaperBackupRestoreManager;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class WallpaperManager implements SemWallpaperManager {
    public static final String ACTION_CHANGE_LIVE_WALLPAPER = "android.service.wallpaper.CHANGE_LIVE_WALLPAPER";
    public static final String ACTION_CROP_AND_SET_WALLPAPER = "android.service.wallpaper.CROP_AND_SET_WALLPAPER";
    public static final String ACTION_LIVE_WALLPAPER_CHOOSER = "android.service.wallpaper.LIVE_WALLPAPER_CHOOSER";
    public static final String BNR_ORIGINAL_BACKUP_FILE_PATH = "/Android/data/com.android.systemui/files/backupwallpapers/";
    public static final String BNR_ORIGINAL_FILE_NAME_HOME = "original_file_home.jpg";
    public static final String BNR_ORIGINAL_FILE_NAME_LOCK = "original_file_lock.jpg";
    public static final String BNR_SUB_DISPLAY = "sub_display/";
    public static final String BNR_XML_FILE_NAME_HOME = "backup_home.xml";
    public static final String BNR_XML_FILE_NAME_LOCK = "backup_lock.xml";
    public static final String COMMAND_AOD_STATE = "android.wallpaper.aodstate";
    public static final String COMMAND_DROP = "android.home.drop";
    public static final String COMMAND_FREEZE = "android.wallpaper.freeze";
    public static final String COMMAND_GOING_TO_SLEEP = "android.wallpaper.goingtosleep";
    public static final String COMMAND_KEYGUARD_GOING_AWAY = "android.wallpaper.keyguardgoingaway";
    public static final String COMMAND_REAPPLY = "android.wallpaper.reapply";
    public static final String COMMAND_SECONDARY_TAP = "android.wallpaper.secondaryTap";
    public static final String COMMAND_TAP = "android.wallpaper.tap";
    public static final String COMMAND_UNFREEZE = "android.wallpaper.unfreeze";
    public static final String COMMAND_WAKING_UP = "android.wallpaper.wakingup";
    private static final boolean DEBUG = false;
    public static final int DEFAULT_HIGHLIGHT_FILTER_AMOUNT = 60;
    private static final String DEFAULT_THEME_VIDEO_RES_ID_SUFFIX = ".mp4";
    private static final String DEFAULT_VIDEO_WALLPAPER_RES_ID = "video";
    public static final String EXTRA_FROM_FOREGROUND_APP = "android.service.wallpaper.extra.FROM_FOREGROUND_APP";
    public static final String EXTRA_LIVE_WALLPAPER_COMPONENT = "android.service.wallpaper.extra.LIVE_WALLPAPER_COMPONENT";
    public static final String EXTRA_NEW_WALLPAPER_ID = "android.service.wallpaper.extra.ID";
    public static final int FLAG_DISPLAY_DEX = 8;
    public static final int FLAG_DISPLAY_PHONE = 4;
    public static final int FLAG_DISPLAY_SUB = 16;
    public static final int FLAG_DISPLAY_VIRTUAL = 32;
    public static final int FLAG_LOCK = 2;
    public static final int FLAG_MODE_MASK = 60;
    public static final int FLAG_NONE = 0;
    public static final int FLAG_SYSTEM = 1;
    public static final int FLAG_TYPE_MASK = 3;
    private static final String IMAGE_WALLPAPER_SERVICE_NAME = "com.android.systemui.ImageWallpaper";
    public static final int KWP_TYPE_ANIMATED = 4;
    public static final int KWP_TYPE_DEFAULT = 0;
    public static final int KWP_TYPE_ERROR = -1;
    public static final int KWP_TYPE_MOTION = 1;
    public static final int KWP_TYPE_PRELOAD_MOTION = 2;
    public static final int KWP_TYPE_VIDEO = 8;
    public static final int LID_ABSENT = -1;
    public static final int LID_CLOSED = 0;
    public static final int LID_OPEN = 1;
    private static final int ONEUI_5_1 = 140100;
    private static final int ONEUI_6_1 = 150100;
    private static final String PACKAGE_NAME_DRESSROOM = "com.samsung.android.app.dressroom";
    private static final String PACKAGE_NAME_DYNAMIC_LOCKSCREEN = "com.samsung.android.dynamiclock";
    private static final String PACKAGE_NAME_EMERGENCY_LAUNCHER = "com.sec.android.emergencylauncher";
    private static final String PACKAGE_NAME_FESTIVAL_WALLPAPER = "com.samsung.android.festivalwallpaper";
    private static final String PACKAGE_NAME_LOCKSTAR = "com.samsung.systemui.lockstar";
    private static final String PACKAGE_NAME_SPRITE = "com.samsung.android.wallpaper.live";
    private static final String PACKAGE_NAME_SYSTEMUI = "com.android.systemui";
    private static final String PACKAGE_NAME_THEME_CENTER = "com.samsung.android.themecenter";
    private static final String PROP_LOCK_WALLPAPER = "ro.config.lock_wallpaper";
    private static final String PROP_WALLPAPER = "ro.config.wallpaper";
    private static final String PROP_WALLPAPER_COMPONENT = "ro.config.wallpaper_component";
    static final long RETURN_DEFAULT_ON_SECURITY_EXCEPTION = 239784307;
    public static final String SEM_ATTRIBUTE_TILT = "tilt";
    public static final int SEM_BACKUP_STATUS_CANCELED = 2;
    public static final int SEM_BACKUP_STATUS_CLEARED = 4;
    public static final int SEM_BACKUP_STATUS_REQUESTED = 1;
    public static final int SEM_BACKUP_STATUS_RESTORED = 3;
    public static final int SEM_FLAG_DEX = 8;
    public static final int SEM_FLAG_LOCK = 2;
    public static final int SEM_FLAG_SUB_DISPLAY = 16;
    public static final int SEM_FLAG_SYSTEM = 1;
    public static final String SEM_SCHEME_MULTIPACK = "multipack";
    public static final int SEM_WALLPAPER_TYPE_ANIMATED = 4;
    public static final int SEM_WALLPAPER_TYPE_DEPRECATED_DLS = 9;
    public static final int SEM_WALLPAPER_TYPE_DLS = 1000;
    public static final int SEM_WALLPAPER_TYPE_EXTERNAL_LIVE = 7;
    public static final int SEM_WALLPAPER_TYPE_GIF = 5;
    public static final int SEM_WALLPAPER_TYPE_IMAGE = 0;
    public static final int SEM_WALLPAPER_TYPE_MOTION = 1;
    public static final int SEM_WALLPAPER_TYPE_MULTIPLE = 3;
    public static final int SEM_WALLPAPER_TYPE_NONE = -1;
    public static final int SEM_WALLPAPER_TYPE_VIDEO = 8;
    public static final String SETTINGS_CURRENT_SEC_ACTIVE_THEMEPACKAGE = "current_sec_active_themepackage";
    public static final String SETTINGS_LOCKSCREEN_WALLPAPER = "lockscreen_wallpaper";
    public static final String SETTINGS_LOCKSCREEN_WALLPAPER_SUB = "lockscreen_wallpaper_sub";
    private static final String SETTINGS_LOCKSCREEN_WALLPAPER_TRANSPARENCY = "lockscreen_wallpaper_transparent";
    private static final String SETTINGS_LOCKSCREEN_WALLPAPER_TRANSPARENCY_SUB = "sub_display_lockscreen_wallpaper_transparency";
    private static final String SETTINGS_SYSTEMUI_TRANSPARENCY = "android.wallpaper.settings_systemui_transparency";
    private static final String SETTINGS_SYSTEMUI_TRANSPARENCY_SUB = "sub_display_system_wallpaper_transparency";
    public static final String SETTINGS_TSS_ACTIVATED = "tss_activated";
    public static final int SWP_TYPE_CUSTOM = 0;
    public static final int SWP_TYPE_PRELOAD = 1;
    public static final int SWP_TYPE_THEME = 2;
    private static final String SYSUI_DESKTOP_PKG_NAME = "com.samsung.desktopsystemui";
    private static final String THEME_VIDEO_RES_ID = "video_1.mp4";
    static final long THROW_ON_SECURITY_EXCEPTION = 237508058;
    public static final int TRANSPARENT_DISABLE = 1;
    public static final int TRANSPARENT_ENABLE = 0;
    private static final String WALLPAPER_CMF_PATH = "/wallpaper/image/";
    private static final String WALLPAPER_PACKAGE = "com.samsung.android.wallpaper.res";
    public static final String WALLPAPER_PREVIEW_META_DATA = "android.wallpaper.preview";
    private static Globals sGlobals;
    private static SemWallpaperResourcesInfo sWallpaperResourcesInfo;
    private final ColorManagementProxy mCmProxy;
    private final Context mContext;
    private float mWallpaperXStep;
    private float mWallpaperYStep;
    private final boolean mWcgEnabled;
    private static String TAG = "WallpaperManager";
    private static final RectF LOCAL_COLOR_BOUNDS = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
    private static final String VALUE_CMF_COLOR = SystemProperties.get("ro.boot.hardware.color");
    private static final Object sSync = new Object[0];
    private static Boolean sIsLockscreenLiveWallpaperEnabled = null;
    private static Boolean sIsMultiCropEnabled = null;

    public interface LocalWallpaperColorConsumer {
        void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetWallpaperFlags {
    }

    static class FastBitmapDrawable extends Drawable {
        private final Bitmap mBitmap;
        private int mDrawLeft;
        private int mDrawTop;
        private final int mHeight;
        private final Paint mPaint;
        private final int mWidth;

        private FastBitmapDrawable(Bitmap bitmap) {
            this.mBitmap = bitmap;
            int width = bitmap.getWidth();
            this.mWidth = width;
            int height = bitmap.getHeight();
            this.mHeight = height;
            setBounds(0, 0, width, height);
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.drawBitmap(this.mBitmap, this.mDrawLeft, this.mDrawTop, this.mPaint);
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -1;
        }

        @Override // android.graphics.drawable.Drawable
        public void setBounds(int left, int top, int right, int bottom) {
            this.mDrawLeft = (((right - left) - this.mWidth) / 2) + left;
            this.mDrawTop = (((bottom - top) - this.mHeight) / 2) + top;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int alpha) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setDither(boolean dither) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setFilterBitmap(boolean filter) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.mWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.mHeight;
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumWidth() {
            return this.mWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public int getMinimumHeight() {
            return this.mHeight;
        }
    }

    private static class CachedWallpaper {
        final Bitmap mCachedWallpaper;
        final int mCachedWallpaperUserId;
        final int mWhich;

        CachedWallpaper(Bitmap cachedWallpaper, int cachedWallpaperUserId, int which) {
            this.mCachedWallpaper = cachedWallpaper;
            this.mCachedWallpaperUserId = cachedWallpaperUserId;
            this.mWhich = which;
        }

        boolean isValid(int userId, int which) {
            return userId == this.mCachedWallpaperUserId && which == this.mWhich && !this.mCachedWallpaper.isRecycled();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Globals extends IWallpaperManagerCallback.Stub {
        private CachedWallpaper mCachedWallpaper;
        private boolean mColorCallbackRegistered;
        private Bitmap mDefaultWallpaper;
        private boolean mIsCachedWallpaperForDeX;
        private Handler mMainLooperHandler;
        private final IWallpaperManager mService;
        private Bitmap mSubDefaultWallpaper;
        private final ArrayList<Pair<OnColorsChangedListener, Handler>> mColorListeners = new ArrayList<>();
        private ArrayMap<LocalWallpaperColorConsumer, ArraySet<RectF>> mLocalColorCallbackAreas = new ArrayMap<>();
        private ILocalWallpaperColorConsumer mLocalColorCallback = new ILocalWallpaperColorConsumer.Stub() { // from class: android.app.WallpaperManager.Globals.1
            @Override // android.app.ILocalWallpaperColorConsumer
            public void onColorsChanged(RectF area, WallpaperColors colors) {
                for (LocalWallpaperColorConsumer callback : Globals.this.mLocalColorCallbackAreas.keySet()) {
                    ArraySet<RectF> areas = (ArraySet) Globals.this.mLocalColorCallbackAreas.get(callback);
                    if (areas != null && areas.contains(area)) {
                        callback.onColorsChanged(area, colors);
                    }
                }
            }
        };
        private final ArrayList<Pair<OnSemColorsChangedListener, Handler>> mSemColorListeners = new ArrayList<>();

        Globals(IWallpaperManager service, Looper looper) {
            this.mService = service;
            this.mMainLooperHandler = new Handler(looper);
            forgetLoadedWallpaper();
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() {
            forgetLoadedWallpaper();
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperChanged(int type, int which, Bundle extras) {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemMultipackApplied(int which) {
        }

        public void addOnColorsChangedListener(OnColorsChangedListener callback, Handler handler, int userId, int displayId) {
            synchronized (this) {
                if (!this.mColorCallbackRegistered) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, userId, displayId);
                        this.mColorCallbackRegistered = true;
                    } catch (RemoteException e) {
                        Log.m103w(WallpaperManager.TAG, "Can't register for color updates", e);
                    }
                }
                this.mColorListeners.add(new Pair<>(callback, handler));
            }
        }

        public void addOnColorsChangedListener(LocalWallpaperColorConsumer callback, List<RectF> regions, int which, int userId, int displayId) {
            synchronized (this) {
                for (RectF area : regions) {
                    ArraySet<RectF> areas = this.mLocalColorCallbackAreas.get(callback);
                    if (areas == null) {
                        areas = new ArraySet<>();
                        this.mLocalColorCallbackAreas.put(callback, areas);
                    }
                    areas.add(area);
                }
                try {
                    this.mService.addOnLocalColorsChangedListener(this.mLocalColorCallback, regions, which, userId, displayId);
                } catch (RemoteException e) {
                    Log.m97e(WallpaperManager.TAG, "Can't register for local color updates", e);
                }
            }
        }

        public void removeOnColorsChangedListener(LocalWallpaperColorConsumer callback, int which, int userId, int displayId) {
            synchronized (this) {
                ArraySet<RectF> removeAreas = this.mLocalColorCallbackAreas.remove(callback);
                if (removeAreas != null && removeAreas.size() != 0) {
                    for (LocalWallpaperColorConsumer cb : this.mLocalColorCallbackAreas.keySet()) {
                        ArraySet<RectF> areas = this.mLocalColorCallbackAreas.get(cb);
                        if (areas != null && cb != callback) {
                            removeAreas.removeAll((ArraySet<? extends RectF>) areas);
                        }
                    }
                    try {
                        if (removeAreas.size() > 0) {
                            this.mService.removeOnLocalColorsChangedListener(this.mLocalColorCallback, new ArrayList(removeAreas), which, userId, displayId);
                        }
                    } catch (RemoteException e) {
                        Log.m97e(WallpaperManager.TAG, "Can't unregister for local color updates", e);
                    }
                }
            }
        }

        public void removeOnColorsChangedListener(final OnColorsChangedListener callback, int userId, int displayId) {
            synchronized (this) {
                this.mColorListeners.removeIf(new Predicate() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return WallpaperManager.Globals.lambda$removeOnColorsChangedListener$0(WallpaperManager.OnColorsChangedListener.this, (Pair) obj);
                    }
                });
                if (this.mColorListeners.size() == 0 && this.mColorCallbackRegistered) {
                    this.mColorCallbackRegistered = false;
                    try {
                        this.mService.unregisterWallpaperColorsCallback(this, userId, displayId);
                    } catch (RemoteException e) {
                        Log.m103w(WallpaperManager.TAG, "Can't unregister color updates", e);
                    }
                }
            }
        }

        static /* synthetic */ boolean lambda$removeOnColorsChangedListener$0(OnColorsChangedListener callback, Pair pair) {
            return pair.first == callback;
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(final WallpaperColors colors, final int which, final int userId) {
            Handler handler;
            synchronized (this) {
                Iterator<Pair<OnColorsChangedListener, Handler>> it = this.mColorListeners.iterator();
                while (it.hasNext()) {
                    final Pair<OnColorsChangedListener, Handler> listener = it.next();
                    Handler handler2 = listener.second;
                    if (listener.second != null) {
                        handler = handler2;
                    } else {
                        Handler handler3 = this.mMainLooperHandler;
                        handler = handler3;
                    }
                    handler.post(new Runnable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            WallpaperManager.Globals.this.lambda$onWallpaperColorsChanged$1(listener, colors, which, userId);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWallpaperColorsChanged$1(Pair listener, WallpaperColors colors, int which, int userId) {
            boolean stillExists;
            synchronized (WallpaperManager.sGlobals) {
                stillExists = this.mColorListeners.contains(listener);
            }
            if (stillExists) {
                ((OnColorsChangedListener) listener.first).onColorsChanged(colors, which, userId);
            }
        }

        WallpaperColors getWallpaperColors(int which, int userId, int displayId) {
            WallpaperManager.checkExactlyOneWallpaperFlagSet(which);
            try {
                return this.mService.getWallpaperColors(which, userId, displayId);
            } catch (RemoteException e) {
                return null;
            }
        }

        SemWallpaperColors semGetWallpaperColors(int which) {
            Log.m94d(WallpaperManager.TAG, "semGetWallpaperColors: which = " + which);
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.m102w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return iWallpaperManager.semGetWallpaperColors(which);
            } catch (RemoteException e) {
                return null;
            }
        }

        SemWallpaperColors semGetPrimaryWallpaperColors(int which) {
            Log.m94d(WallpaperManager.TAG, "semGetPrimaryWallpaperColors: which = " + which);
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.m102w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return iWallpaperManager.semGetPrimaryWallpaperColors(which);
            } catch (RemoteException e) {
                return null;
            }
        }

        void semSetSmartCropRect(int which, Rect original, Rect smartCrop) {
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.m102w(WallpaperManager.TAG, "WallpaperService not running");
            } else {
                try {
                    iWallpaperManager.semSetSmartCropRect(which, original, smartCrop);
                } catch (RemoteException e) {
                }
            }
        }

        Rect semGetSmartCropRect(int which) {
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.m102w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return iWallpaperManager.semGetSmartCropRect(which);
            } catch (RemoteException e) {
                return null;
            }
        }

        public Bitmap peekWallpaperBitmap(Context context, boolean returnDefault, int which, ColorManagementProxy cmProxy) {
            return peekWallpaperBitmap(context, returnDefault, which, context.getUserId(), false, cmProxy);
        }

        public Bitmap peekWallpaperBitmap(Context context, boolean returnDefault, int which, int userId, boolean hardware, ColorManagementProxy cmProxy) {
            return peekWallpaperBitmap(context, returnDefault, which, userId, hardware, cmProxy, true);
        }

        public Bitmap peekWallpaperBitmap(Context context, boolean returnDefault, int which, int userId, boolean hardware, ColorManagementProxy cmProxy, boolean useCache) {
            CachedWallpaper cachedWallpaper;
            Log.m94d(WallpaperManager.TAG, "peekWallpaperBitmap: which =" + which + ", useCache = " + useCache);
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager != null) {
                try {
                    if (!iWallpaperManager.isWallpaperSupported(context.getOpPackageName())) {
                        return null;
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            try {
                boolean isDesktopMode = WallpaperManager.sGlobals.mService.isDesktopModeEnabled(which);
                synchronized (this) {
                    boolean z = this.mIsCachedWallpaperForDeX;
                    if (((z && isDesktopMode) || (!z && !isDesktopMode)) && useCache && (cachedWallpaper = this.mCachedWallpaper) != null && cachedWallpaper.isValid(userId, which) && context.checkSelfPermission(Manifest.permission.READ_WALLPAPER_INTERNAL) == 0) {
                        Log.m94d(WallpaperManager.TAG, "peekWallpaperBitmap() cached image height=" + this.mCachedWallpaper.mCachedWallpaper.getHeight() + " width=" + this.mCachedWallpaper.mCachedWallpaper.getWidth());
                        return this.mCachedWallpaper.mCachedWallpaper;
                    }
                    this.mCachedWallpaper = null;
                    Bitmap currentWallpaper = null;
                    this.mIsCachedWallpaperForDeX = false;
                    try {
                    } catch (RemoteException e2) {
                        throw e2.rethrowFromSystemServer();
                    } catch (OutOfMemoryError e3) {
                        Log.m102w(WallpaperManager.TAG, "Out of memory loading the current wallpaper: " + e3);
                    } catch (SecurityException e4) {
                        if (CompatChanges.isChangeEnabled(WallpaperManager.RETURN_DEFAULT_ON_SECURITY_EXCEPTION) && !CompatChanges.isChangeEnabled(WallpaperManager.THROW_ON_SECURITY_EXCEPTION)) {
                            Log.m102w(WallpaperManager.TAG, "No permission to access wallpaper, returning default wallpaper to avoid crashing legacy app.");
                            return getDefaultWallpaper(context, 1);
                        }
                        if (context.getApplicationInfo().targetSdkVersion >= 27) {
                            throw e4;
                        }
                        Log.m102w(WallpaperManager.TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                    }
                    if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mService != null && WhichChecker.isSubDisplay(which) && this.mService.isWaitingForUnlockUser(which, userId)) {
                        return null;
                    }
                    currentWallpaper = getCurrentWallpaperLocked(context, which, userId, hardware, cmProxy);
                    this.mIsCachedWallpaperForDeX = isDesktopMode;
                    if (currentWallpaper != null) {
                        this.mCachedWallpaper = new CachedWallpaper(currentWallpaper, userId, which);
                        return currentWallpaper;
                    }
                    CachedWallpaper cachedWallpaper2 = this.mCachedWallpaper;
                    if (cachedWallpaper2 != null && cachedWallpaper2.isValid(userId, which)) {
                        return this.mCachedWallpaper.mCachedWallpaper;
                    }
                    if (returnDefault || (WhichChecker.isLock(which) && isStaticWallpaper(which))) {
                        return getDefaultWallpaper(context, which);
                    }
                    return null;
                }
            } catch (RemoteException e5) {
                throw e5.rethrowFromSystemServer();
            }
        }

        public Rect peekWallpaperDimensions(Context context, boolean returnDefault, int which, int userId) {
            int i;
            BitmapFactory.Options options;
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager != null) {
                try {
                    if (!iWallpaperManager.isWallpaperSupported(context.getOpPackageName())) {
                        return new Rect();
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            Rect dimensions = null;
            synchronized (this) {
                try {
                    Bundle params = new Bundle();
                    try {
                        i = which;
                        try {
                            ParcelFileDescriptor pfd = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, i, params, userId, true, false, -1);
                            try {
                                if (pfd != null) {
                                    try {
                                        options = new BitmapFactory.Options();
                                        options.inJustDecodeBounds = true;
                                        BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor(), null, options);
                                        i = 0;
                                    } catch (Throwable th) {
                                        th = th;
                                    }
                                    try {
                                        dimensions = new Rect(0, 0, options.outWidth, options.outHeight);
                                    } catch (Throwable th2) {
                                        th = th2;
                                        Throwable th3 = th;
                                        if (pfd == null) {
                                            throw th3;
                                        }
                                        try {
                                            pfd.close();
                                            throw th3;
                                        } catch (Throwable th4) {
                                            th3.addSuppressed(th4);
                                            throw th3;
                                        }
                                    }
                                } else {
                                    i = 0;
                                }
                                if (pfd != null) {
                                    pfd.close();
                                }
                            } catch (RemoteException e2) {
                                ex = e2;
                                Log.m103w(WallpaperManager.TAG, "peek wallpaper dimensions failed", ex);
                            } catch (IOException e3) {
                            }
                        } catch (RemoteException e4) {
                            ex = e4;
                            i = 0;
                        } catch (IOException e5) {
                            i = 0;
                        }
                    } catch (RemoteException e6) {
                        ex = e6;
                        i = 0;
                    } catch (IOException e7) {
                        i = 0;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    while (true) {
                        try {
                            throw th;
                        } catch (Throwable th6) {
                            th = th6;
                        }
                    }
                }
            }
            if (dimensions != null && dimensions.width() != 0 && dimensions.height() != 0) {
                return dimensions;
            }
            if (!returnDefault && (!WhichChecker.isLock(which) || !isStaticWallpaper(which))) {
                return dimensions;
            }
            InputStream is = WallpaperManager.openDefaultWallpaper(context, which);
            if (is == null) {
                return dimensions;
            }
            try {
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(is, null, options2);
                return new Rect(i, i, options2.outWidth, options2.outHeight);
            } finally {
                IoUtils.closeQuietly(is);
            }
        }

        void forgetLoadedWallpaper() {
            synchronized (this) {
                this.mCachedWallpaper = null;
                this.mDefaultWallpaper = null;
                this.mSubDefaultWallpaper = null;
                this.mIsCachedWallpaperForDeX = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Bitmap getCurrentWallpaperLocked(Context context, int which, int userId, final boolean hardware, final ColorManagementProxy cmProxy) {
            ImageDecoder.Source src;
            if (this.mService == null) {
                Log.m102w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            Log.m94d(WallpaperManager.TAG, "getCurrentWallpaperLocked userId=" + userId + " by : " + context.getOpPackageName());
            try {
                Bundle params = new Bundle();
                final ParcelFileDescriptor pfd = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, which, params, userId, true, false, -1);
                if (pfd != null) {
                    Bitmap bitmap = null;
                    try {
                        try {
                            try {
                                try {
                                    InputStream fis = new FileInputStream(pfd.getFileDescriptor());
                                    try {
                                        BufferedInputStream bis = new BufferedInputStream(fis);
                                        try {
                                            if (SemWallpaperUtils.isQmgImage(bis)) {
                                                Log.m94d(WallpaperManager.TAG, "getCurrentWallpaperLocked : QMG image type");
                                                BitmapFactory.Options options = new BitmapFactory.Options();
                                                bitmap = BitmapFactory.decodeStreamQMG(bis, null, options);
                                                if (bitmap == null) {
                                                    Log.m96e(WallpaperManager.TAG, "getCurrentWallpaperLocked : failed to decode QMG");
                                                }
                                            }
                                            bis.close();
                                            fis.close();
                                        } finally {
                                        }
                                    } finally {
                                    }
                                } catch (RemoteException e) {
                                    e = e;
                                    throw e.rethrowFromSystemServer();
                                }
                            } catch (ArrayIndexOutOfBoundsException e2) {
                                e = e2;
                                Log.m97e(WallpaperManager.TAG, "Can't decode file", e);
                                return null;
                            }
                        } catch (Exception e3) {
                            Log.m97e(WallpaperManager.TAG, "getCurrentWallpaperLocked : e=" + e3, e3);
                        }
                        if (bitmap == null) {
                            try {
                                src = ImageDecoder.createSource((Callable<AssetFileDescriptor>) new Callable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda4
                                    @Override // java.util.concurrent.Callable
                                    public final Object call() {
                                        return WallpaperManager.Globals.lambda$getCurrentWallpaperLocked$2(ParcelFileDescriptor.this);
                                    }
                                });
                            } catch (RemoteException e4) {
                                e = e4;
                            } catch (IOException | OutOfMemoryError e5) {
                                e = e5;
                            } catch (ArrayIndexOutOfBoundsException e6) {
                                e = e6;
                            }
                            try {
                                bitmap = ImageDecoder.decodeBitmap(src, new ImageDecoder.OnHeaderDecodedListener() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda5
                                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                        WallpaperManager.Globals.lambda$getCurrentWallpaperLocked$3(hardware, cmProxy, imageDecoder, imageInfo, source);
                                    }
                                });
                            } catch (RemoteException e7) {
                                e = e7;
                                throw e.rethrowFromSystemServer();
                            } catch (IOException | OutOfMemoryError e8) {
                                e = e8;
                                Log.m103w(WallpaperManager.TAG, "Can't decode file", e);
                                return null;
                            } catch (ArrayIndexOutOfBoundsException e9) {
                                e = e9;
                                Log.m97e(WallpaperManager.TAG, "Can't decode file", e);
                                return null;
                            }
                        }
                    } catch (IOException | OutOfMemoryError e10) {
                        e = e10;
                    }
                    try {
                        return checkDeviceDensity(context, bitmap, which);
                    } catch (IOException | OutOfMemoryError e11) {
                        e = e11;
                        Log.m103w(WallpaperManager.TAG, "Can't decode file", e);
                        return null;
                    } catch (ArrayIndexOutOfBoundsException e12) {
                        e = e12;
                        Log.m97e(WallpaperManager.TAG, "Can't decode file", e);
                        return null;
                    }
                }
                return null;
            } catch (RemoteException e13) {
                e = e13;
            }
        }

        static /* synthetic */ AssetFileDescriptor lambda$getCurrentWallpaperLocked$2(ParcelFileDescriptor pfd) throws Exception {
            AssetFileDescriptor afd = new AssetFileDescriptor(pfd, 0L, -1L);
            return afd;
        }

        static /* synthetic */ void lambda$getCurrentWallpaperLocked$3(boolean hardware, ColorManagementProxy cmProxy, ImageDecoder decoder, ImageDecoder.ImageInfo info, ImageDecoder.Source source) {
            decoder.setMutableRequired(!hardware);
            if (cmProxy != null) {
                cmProxy.doColorManagement(decoder, info);
            }
        }

        private Bitmap getDefaultWallpaper(Context context, int which) {
            Bitmap defaultWallpaper = getDefaultWallpaper(which);
            if (defaultWallpaper == null || defaultWallpaper.isRecycled()) {
                defaultWallpaper = null;
                try {
                    InputStream is = WallpaperManager.openDefaultWallpaper(context, which);
                    if (is != null) {
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                            defaultWallpaper = checkDeviceDensity(context, bitmap, which);
                        } catch (Throwable th) {
                            if (is != null) {
                                try {
                                    is.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    }
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException | OutOfMemoryError e) {
                    Log.m103w(WallpaperManager.TAG, "Can't decode stream", e);
                }
            }
            synchronized (this) {
                setDefaultWallpaper(which, defaultWallpaper);
            }
            return defaultWallpaper;
        }

        private boolean isStaticWallpaper(int which) {
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.m102w(WallpaperManager.TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            try {
                return iWallpaperManager.isStaticWallpaper(which);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private Bitmap getDefaultWallpaper(int which) {
            if (WhichChecker.isSubDisplay(which)) {
                return this.mSubDefaultWallpaper;
            }
            return this.mDefaultWallpaper;
        }

        private void setDefaultWallpaper(int which, Bitmap bitmap) {
            if (WhichChecker.isSubDisplay(which)) {
                this.mSubDefaultWallpaper = bitmap;
            }
            this.mDefaultWallpaper = bitmap;
        }

        public Bitmap checkDeviceDensity(Context context, Bitmap bitmap) {
            return checkDeviceDensity(context, bitmap, 0);
        }

        private Bitmap checkDeviceDensity(Context context, Bitmap bitmap, int which) {
            if (bitmap == null || bitmap.isRecycled()) {
                return null;
            }
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            if (Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && which != 0) {
                DisplayManager dm = (DisplayManager) context.getSystemService(DisplayManager.class);
                int displayId = WallpaperManager.getDisplayId(context, which);
                Log.m94d(WallpaperManager.TAG, "checkDeviceDensity getDisplayId=" + displayId);
                display = dm.getDisplay(displayId);
            }
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            bitmap.setDensity(metrics.noncompatDensityDpi);
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            int deviceHeight = displayInfo.logicalHeight;
            int deviceWidth = displayInfo.logicalWidth;
            int deviceRotation = displayInfo.rotation;
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();
            Log.m94d(WallpaperManager.TAG, "checkDeviceDensity deviceRotation=" + deviceRotation + " deviceHeight=" + deviceHeight + " deviceWidth=" + deviceWidth + " bitmapHeight=" + bitmapHeight + " bitmapWidth=" + bitmapWidth);
            PackageManager pm = context.getPackageManager();
            boolean isTablet = pm != null && pm.hasSystemFeature(PackageManager.SEM_FEATURE_DEVICE_CATEGORY_TABLET);
            if (!isTablet && (deviceRotation == 1 || deviceRotation == 3)) {
                deviceHeight = displayInfo.logicalWidth;
                deviceWidth = displayInfo.logicalHeight;
            }
            if (deviceWidth == 0 || deviceHeight == 0 || deviceWidth >= bitmapWidth || deviceHeight >= bitmapHeight) {
                return bitmap;
            }
            float scale = Math.max(deviceWidth / bitmapWidth, deviceHeight / bitmapHeight);
            Bitmap resizedBmp = resizeBitmap(bitmap, scale);
            Log.m94d(WallpaperManager.TAG, "resize scale down.:" + scale);
            return resizedBmp;
        }

        private Bitmap resizeBitmap(Bitmap bitmap, float scale) {
            int bitmapWidth = (int) (bitmap.getWidth() * scale);
            int bitmapHeight = (int) (bitmap.getHeight() * scale);
            Bitmap bm = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true);
            return bm;
        }

        public void addOnSemColorsChangedListener(OnSemColorsChangedListener callback, Handler handler, int userId, int displayId) {
            synchronized (this) {
                if (!this.mColorCallbackRegistered) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, userId, displayId);
                        this.mColorCallbackRegistered = true;
                    } catch (RemoteException e) {
                        Log.m103w(WallpaperManager.TAG, "Can't register for color updates", e);
                    }
                }
                this.mSemColorListeners.add(new Pair<>(callback, handler));
            }
        }

        public void removeOnSemColorsChangedListener(final OnSemColorsChangedListener callback, int userId, int displayId) {
            synchronized (this) {
                this.mSemColorListeners.removeIf(new Predicate() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return WallpaperManager.Globals.lambda$removeOnSemColorsChangedListener$4(OnSemColorsChangedListener.this, (Pair) obj);
                    }
                });
                if (this.mSemColorListeners.size() == 0 && this.mColorCallbackRegistered) {
                    this.mColorCallbackRegistered = false;
                    try {
                        this.mService.unregisterWallpaperColorsCallback(this, userId, displayId);
                    } catch (RemoteException e) {
                        Log.m103w(WallpaperManager.TAG, "Can't unregister color updates", e);
                    }
                }
            }
        }

        static /* synthetic */ boolean lambda$removeOnSemColorsChangedListener$4(OnSemColorsChangedListener callback, Pair pair) {
            return pair.first == callback;
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsChanged(final SemWallpaperColors colors, final int which, int userId) {
            Log.m94d(WallpaperManager.TAG, "onSemWallpaperColorsChanged " + colors + ", which=" + which);
            synchronized (this) {
                Iterator<Pair<OnSemColorsChangedListener, Handler>> it = this.mSemColorListeners.iterator();
                while (it.hasNext()) {
                    final Pair<OnSemColorsChangedListener, Handler> listener = it.next();
                    Handler handler = listener.second;
                    if (listener.second == null) {
                        handler = this.mMainLooperHandler;
                    }
                    handler.post(new Runnable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WallpaperManager.Globals.this.lambda$onSemWallpaperColorsChanged$5(listener, colors, which);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemWallpaperColorsChanged$5(Pair listener, SemWallpaperColors colors, int which) {
            boolean stillExists;
            synchronized (this) {
                stillExists = this.mSemColorListeners.contains(listener);
            }
            if (stillExists) {
                ((OnSemColorsChangedListener) listener.first).onColorsChanged(colors, which);
            }
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsAnalysisRequested(int which, int userId) {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemBackupStatusChanged(int which, int status, int key) {
        }
    }

    static void initGlobals(IWallpaperManager service, Looper looper) {
        synchronized (sSync) {
            if (sGlobals == null) {
                sGlobals = new Globals(service, looper);
            }
        }
    }

    WallpaperManager(IWallpaperManager service, Context context, Handler handler) {
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mContext = context;
        if (service != null) {
            initGlobals(service, context.getMainLooper());
        }
        if (sWallpaperResourcesInfo == null) {
            sWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
        }
        this.mWcgEnabled = context.getResources().getConfiguration().isScreenWideColorGamut() && (context.getResources().getBoolean(C4337R.bool.config_enableWcgMode) || Rune.SUPPORT_WCG);
        this.mCmProxy = new ColorManagementProxy(context);
    }

    WallpaperManager() {
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mContext = null;
        this.mCmProxy = null;
        this.mWcgEnabled = false;
    }

    public static WallpaperManager getInstance(Context context) {
        return (WallpaperManager) context.getSystemService("wallpaper");
    }

    public IWallpaperManager getIWallpaperManager() {
        return sGlobals.mService;
    }

    public boolean isLockscreenLiveWallpaperEnabled() {
        return isLockscreenLiveWallpaperEnabledHelper();
    }

    private static boolean isLockscreenLiveWallpaperEnabledHelper() {
        if (sGlobals == null) {
            sIsLockscreenLiveWallpaperEnabled = Boolean.valueOf(SystemProperties.getBoolean("persist.wm.debug.lockscreen_live_wallpaper", false));
        }
        if (sIsLockscreenLiveWallpaperEnabled == null) {
            try {
                sIsLockscreenLiveWallpaperEnabled = Boolean.valueOf(sGlobals.mService.isLockscreenLiveWallpaperEnabled());
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return sIsLockscreenLiveWallpaperEnabled.booleanValue();
    }

    public static boolean isMultiCropEnabled() {
        if (sGlobals == null) {
            sIsMultiCropEnabled = Boolean.valueOf(SystemProperties.getBoolean("persist.wm.debug.wallpaper_multi_crop", false));
        }
        if (sIsMultiCropEnabled == null) {
            try {
                sIsMultiCropEnabled = Boolean.valueOf(sGlobals.mService.isMultiCropEnabled());
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        return sIsMultiCropEnabled.booleanValue();
    }

    public boolean shouldEnableWideColorGamut() {
        return this.mWcgEnabled;
    }

    public Drawable getDrawable() {
        return getDrawable(1);
    }

    @Override // android.app.SemWallpaperManager
    public Drawable getDrawable(int which) {
        ColorManagementProxy cmProxy = getColorManagementProxy();
        boolean returnDefault = !WhichChecker.isLock(which);
        Bitmap bm = sGlobals.peekWallpaperBitmap(this.mContext, returnDefault, which, cmProxy);
        if (bm != null) {
            Drawable dr = new BitmapDrawable(this.mContext.getResources(), bm);
            dr.setDither(false);
            return dr;
        }
        return null;
    }

    @Override // android.app.SemWallpaperManager
    public Drawable semGetDrawable(int which) {
        return semGetDrawable(which, 1);
    }

    public Drawable semGetDrawable(int which, int orientation) {
        ParcelFileDescriptor fd;
        if (!WhichChecker.isSystem(which) && !WhichChecker.isLock(which)) {
            if (WhichChecker.isDex(which)) {
                which = 9;
            } else {
                if (which == 0) {
                    InputStream is = openDefaultWallpaper(this.mContext, 2, false);
                    if (is == null) {
                        is = openDefaultWallpaper(this.mContext, 1, false);
                    }
                    return getDrawableFromStream(is);
                }
                which = 1;
            }
        }
        try {
            boolean isDesktopMode = sGlobals.mService.isDesktopModeEnabled(which);
            if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.isModeAbsent(which)) {
                which |= isSubDisplay() ? 16 : 4;
            }
            boolean isKeyguardWallpaperShowing = isKeyguardWallpaperShowing(which);
            boolean isDefaultVideoWallpaper = sWallpaperResourcesInfo.isDefaultVideo(2) && !isDesktopMode && isVideoWallpaper();
            boolean isLock = WhichChecker.isLock(which);
            if (isKeyguardWallpaperShowing && isDefaultVideoWallpaper && isLock) {
                String fileName = getVideoFileName(which);
                AssetFileDescriptor assetFd = null;
                try {
                    assetFd = getVideoFDFromPackage(WALLPAPER_PACKAGE, fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = getVideoWallpaperFrame(assetFd, null, fileName);
                if (bitmap != null) {
                    return new BitmapDrawable(this.mContext.getResources(), bitmap);
                }
            } else if (isLiveWallpaper(which) && !WhichChecker.isDex(which) && (!isKeyguardWallpaperShowing || WhichChecker.isSystem(which))) {
                if (WhichChecker.isLock(which)) {
                    Log.m94d(TAG, "semGetDrawable: Converting FLAG_LOCK to FLAG_SYSTEM. Since lockscreen wallpaper does not exist.");
                    which = WhichChecker.getMode(which) | 1;
                }
                ParcelFileDescriptor fd2 = null;
                try {
                    try {
                        fd = sGlobals.mService.getWallpaperThumbnailFileDescriptor(7, this.mContext.getUserId(), which, orientation, 268435456);
                        if (fd == null) {
                            WallpaperInfo info = getWallpaperInfo(which, this.mContext.getUserId());
                            if (info != null) {
                                return info.loadThumbnail(this.mContext.getPackageManager());
                            }
                            return null;
                        }
                    } catch (RemoteException e2) {
                        throw e2.rethrowFromSystemServer();
                    }
                } finally {
                }
            }
            ColorManagementProxy cmProxy = getColorManagementProxy();
            Globals globals = sGlobals;
            Context context = this.mContext;
            Bitmap wallpaperBmp = globals.getCurrentWallpaperLocked(context, which, context.getUserId(), false, cmProxy);
            if (wallpaperBmp == null && isLock && isSystemAndLockPaired(which)) {
                fd = null;
                try {
                    try {
                        fd = getLockWallpaperFile(this.mContext.getUserId(), which);
                        if (fd != null) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            wallpaperBmp = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, options);
                        }
                    } catch (OutOfMemoryError e3) {
                        Log.m103w(TAG, "Can't decode file descriptor", e3);
                    }
                } finally {
                }
            }
            Drawable dr = getDrawableFromBitmap(wallpaperBmp);
            return dr != null ? dr : getDrawableFromStream(openDefaultWallpaper(this.mContext, which, false));
        } catch (RemoteException e4) {
            throw e4.rethrowFromSystemServer();
        }
    }

    private Drawable getDrawableFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Log.m94d(TAG, "getDrawableFromBitmap : bitmap is null");
            return null;
        }
        Drawable dr = new BitmapDrawable(this.mContext.getResources(), bitmap);
        dr.setDither(false);
        return dr;
    }

    private Drawable getDrawableFromStream(InputStream is) {
        if (is == null) {
            Log.m94d(TAG, "getDrawableFromStream : input stream is null");
            return null;
        }
        try {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bm = sGlobals.checkDeviceDensity(this.mContext, BitmapFactory.decodeStream(is, null, options));
                if (bm != null) {
                    Drawable dr = new BitmapDrawable(this.mContext.getResources(), bm);
                    dr.setDither(false);
                    return dr;
                }
            } catch (OutOfMemoryError e) {
                Log.m103w(TAG, "Can't decode stream", e);
            }
            return null;
        } finally {
            IoUtils.closeQuietly(is);
        }
    }

    public Drawable getBuiltInDrawable() {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, 1);
    }

    public Drawable getBuiltInDrawable(int which) {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, which);
    }

    public Drawable getBuiltInDrawable(int outWidth, int outHeight, boolean scaleToFit, float horizontalAlignment, float verticalAlignment) {
        return getBuiltInDrawable(outWidth, outHeight, scaleToFit, horizontalAlignment, verticalAlignment, 1);
    }

    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.graphics.BitmapFactory$Options, android.graphics.Rect] */
    public Drawable getBuiltInDrawable(int outWidth, int outHeight, boolean scaleToFit, float horizontalAlignment, float verticalAlignment, int which) {
        int which2;
        ?? r1;
        InputStream is;
        boolean z;
        int outWidth2;
        RectF cropRectF;
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(which) == 0 && isSubDisplay()) {
            Log.m94d(TAG, "getBuiltInDrawable, add flag");
            which2 = which | 16;
        } else {
            which2 = which;
        }
        Log.m94d(TAG, "getBuiltInDrawable: which = " + which2);
        checkExactlyOneWallpaperFlagSet(which2);
        Resources resources = this.mContext.getResources();
        float horizontalAlignment2 = Math.max(0.0f, Math.min(1.0f, horizontalAlignment));
        float verticalAlignment2 = Math.max(0.0f, Math.min(1.0f, verticalAlignment));
        InputStream wpStream = openDefaultWallpaper(this.mContext, which2);
        if (wpStream == null) {
            Log.m102w(TAG, "default wallpaper stream " + which2 + " is null");
            return null;
        }
        InputStream is2 = new BufferedInputStream(wpStream);
        if (outWidth <= 0) {
            r1 = 0;
        } else if (outHeight <= 0) {
            r1 = 0;
        } else {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is2, null, options);
            if (options.outWidth != 0 && options.outHeight != 0) {
                int inWidth = options.outWidth;
                int inHeight = options.outHeight;
                InputStream is3 = new BufferedInputStream(openDefaultWallpaper(this.mContext, which2));
                int outWidth3 = Math.min(inWidth, outWidth);
                int outHeight2 = Math.min(inHeight, outHeight);
                if (scaleToFit) {
                    is = is3;
                    z = true;
                    outWidth2 = outWidth3;
                    cropRectF = getMaxCropRect(inWidth, inHeight, outWidth3, outHeight2, horizontalAlignment2, verticalAlignment2);
                } else {
                    is = is3;
                    z = true;
                    outWidth2 = outWidth3;
                    float left = (inWidth - outWidth2) * horizontalAlignment2;
                    float right = outWidth2 + left;
                    float top = (inHeight - outHeight2) * verticalAlignment2;
                    float bottom = outHeight2 + top;
                    cropRectF = new RectF(left, top, right, bottom);
                }
                Rect roundedTrueCrop = new Rect();
                cropRectF.roundOut(roundedTrueCrop);
                if (roundedTrueCrop.width() > 0 && roundedTrueCrop.height() > 0) {
                    int scaleDownSampleSize = Math.min(roundedTrueCrop.width() / outWidth2, roundedTrueCrop.height() / outHeight2);
                    BitmapRegionDecoder decoder = null;
                    try {
                        decoder = BitmapRegionDecoder.newInstance(is, z);
                    } catch (IOException e) {
                        Log.m102w(TAG, "cannot open region decoder for default wallpaper");
                    }
                    Bitmap crop = null;
                    if (decoder != null) {
                        BitmapFactory.Options options2 = new BitmapFactory.Options();
                        if (scaleDownSampleSize > 1) {
                            options2.inSampleSize = scaleDownSampleSize;
                        }
                        crop = decoder.decodeRegion(roundedTrueCrop, options2);
                        decoder.recycle();
                    }
                    if (crop == null) {
                        InputStream is4 = new BufferedInputStream(openDefaultWallpaper(this.mContext, which2));
                        BitmapFactory.Options options3 = new BitmapFactory.Options();
                        if (scaleDownSampleSize > 1) {
                            options3.inSampleSize = scaleDownSampleSize;
                        }
                        Bitmap fullSize = BitmapFactory.decodeStream(is4, null, options3);
                        if (fullSize != null) {
                            crop = Bitmap.createBitmap(fullSize, roundedTrueCrop.left, roundedTrueCrop.top, roundedTrueCrop.width(), roundedTrueCrop.height());
                        }
                    }
                    if (crop == null) {
                        Log.m102w(TAG, "cannot decode default wallpaper");
                        return null;
                    }
                    if (outWidth2 > 0 && outHeight2 > 0) {
                        if (crop.getWidth() != outWidth2 || crop.getHeight() != outHeight2) {
                            Matrix m = new Matrix();
                            RectF cropRect = new RectF(0.0f, 0.0f, crop.getWidth(), crop.getHeight());
                            float horizontalAlignment3 = outHeight2;
                            RectF returnRect = new RectF(0.0f, 0.0f, outWidth2, horizontalAlignment3);
                            m.setRectToRect(cropRect, returnRect, Matrix.ScaleToFit.FILL);
                            Bitmap tmp = Bitmap.createBitmap((int) returnRect.width(), (int) returnRect.height(), Bitmap.Config.ARGB_8888);
                            if (tmp != null) {
                                Canvas c = new Canvas(tmp);
                                Paint p = new Paint();
                                p.setFilterBitmap(true);
                                c.drawBitmap(crop, m, p);
                                crop = tmp;
                            }
                        }
                    }
                    return new BitmapDrawable(resources, crop);
                }
                Log.m102w(TAG, "crop has bad values for full size image");
                return null;
            }
            Log.m96e(TAG, "default wallpaper dimensions are 0");
            return null;
        }
        return new BitmapDrawable(resources, BitmapFactory.decodeStream(is2, r1, r1));
    }

    private static RectF getMaxCropRect(int inWidth, int inHeight, int outWidth, int outHeight, float horizontalAlignment, float verticalAlignment) {
        RectF cropRect = new RectF();
        if (inWidth / inHeight > outWidth / outHeight) {
            cropRect.top = 0.0f;
            cropRect.bottom = inHeight;
            float cropWidth = outWidth * (inHeight / outHeight);
            cropRect.left = (inWidth - cropWidth) * horizontalAlignment;
            cropRect.right = cropRect.left + cropWidth;
        } else {
            cropRect.left = 0.0f;
            cropRect.right = inWidth;
            float cropHeight = outHeight * (inWidth / outWidth);
            cropRect.top = (inHeight - cropHeight) * verticalAlignment;
            cropRect.bottom = cropRect.top + cropHeight;
        }
        return cropRect;
    }

    public Drawable peekDrawable() {
        return peekDrawable(1);
    }

    public Drawable peekDrawable(int which) {
        return getDrawable(which);
    }

    public Drawable getFastDrawable() {
        return getFastDrawable(1);
    }

    public Drawable getFastDrawable(int which) {
        ColorManagementProxy cmProxy = getColorManagementProxy();
        boolean returnDefault = !WhichChecker.isLock(which);
        Bitmap bm = sGlobals.peekWallpaperBitmap(this.mContext, returnDefault, which, cmProxy);
        if (bm == null) {
            return null;
        }
        return new FastBitmapDrawable(bm);
    }

    public Drawable peekFastDrawable() {
        return peekFastDrawable(1);
    }

    public Drawable peekFastDrawable(int which) {
        return getFastDrawable(which);
    }

    public boolean wallpaperSupportsWcg(int which) {
        ColorManagementProxy cmProxy;
        Bitmap bitmap;
        return (!shouldEnableWideColorGamut() || (bitmap = sGlobals.peekWallpaperBitmap(this.mContext, false, which, (cmProxy = getColorManagementProxy()))) == null || bitmap.getColorSpace() == null || bitmap.getColorSpace() == ColorSpace.get(ColorSpace.Named.SRGB) || !cmProxy.isSupportedColorSpace(bitmap.getColorSpace())) ? false : true;
    }

    @Override // android.app.SemWallpaperManager
    public boolean wallpaperSupportsWcg(Bitmap bitmap) {
        if (!shouldEnableWideColorGamut()) {
            return false;
        }
        ColorManagementProxy cmProxy = getColorManagementProxy();
        return (bitmap == null || bitmap.getColorSpace() == null || bitmap.getColorSpace() == ColorSpace.get(ColorSpace.Named.SRGB) || !cmProxy.isSupportedColorSpace(bitmap.getColorSpace())) ? false : true;
    }

    public Bitmap getBitmap() {
        return getBitmap(false);
    }

    public Bitmap getBitmap(boolean hardware) {
        return getBitmapAsUser(this.mContext.getUserId(), hardware, 1, true);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmap(boolean hardware, int which, boolean useCache) {
        return getBitmapAsUser(this.mContext.getUserId(), hardware, which, useCache);
    }

    public Bitmap getBitmap(boolean hardware, int which) {
        return getBitmapAsUser(this.mContext.getUserId(), hardware, which);
    }

    public Bitmap getBitmapAsUser(int userId, boolean hardware) {
        return getBitmapAsUser(userId, hardware, 1, true);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapAsUser(int userId, boolean hardware, int which, boolean useCache) {
        ColorManagementProxy cmProxy = getColorManagementProxy();
        return sGlobals.peekWallpaperBitmap(this.mContext, true, which, userId, hardware, cmProxy, useCache);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapForDex() {
        return getBitmapForDex(false);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapForDex(boolean hardware) {
        return getBitmapForDexAsUser(this.mContext.getUserId(), hardware);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapForDexAsUser(int userId, boolean hardware) {
        ColorManagementProxy cmProxy = getColorManagementProxy();
        return sGlobals.peekWallpaperBitmap(this.mContext, true, 9, userId, hardware, cmProxy);
    }

    public Bitmap getBitmapAsUser(int userId, boolean hardware, int which) {
        boolean returnDefault = !WhichChecker.isLock(which);
        return getBitmapAsUser(userId, hardware, which, returnDefault, true);
    }

    public Bitmap getBitmapAsUser(int userId, boolean hardware, int which, boolean returnDefault, boolean useCache) {
        ColorManagementProxy cmProxy = getColorManagementProxy();
        return sGlobals.peekWallpaperBitmap(this.mContext, returnDefault, which, userId, hardware, cmProxy, useCache);
    }

    public Rect peekBitmapDimensions() {
        return peekBitmapDimensions(1);
    }

    public Rect peekBitmapDimensions(int which) {
        boolean returnDefault = !WhichChecker.isLock(which);
        return peekBitmapDimensions(which, returnDefault);
    }

    public Rect peekBitmapDimensions(int which, boolean returnDefault) {
        checkExactlyOneWallpaperFlagSet(which);
        Globals globals = sGlobals;
        Context context = this.mContext;
        return globals.peekWallpaperDimensions(context, returnDefault, which, context.getUserId());
    }

    public ParcelFileDescriptor getWallpaperFile(int which) {
        return getWallpaperFile(which, this.mContext.getUserId());
    }

    public void addOnColorsChangedListener(OnColorsChangedListener listener, Handler handler) {
        addOnColorsChangedListener(listener, handler, this.mContext.getUserId());
    }

    public void addOnColorsChangedListener(OnColorsChangedListener listener, Handler handler, int userId) {
        sGlobals.addOnColorsChangedListener(listener, handler, userId, this.mContext.getDisplayId());
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener callback) {
        removeOnColorsChangedListener(callback, this.mContext.getUserId());
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener callback, int userId) {
        sGlobals.removeOnColorsChangedListener(callback, userId, this.mContext.getDisplayId());
    }

    public WallpaperColors getWallpaperColors(int which) {
        return getWallpaperColors(which, this.mContext.getUserId());
    }

    public WallpaperColors getWallpaperColors(int which, int userId) {
        StrictMode.assertUiContext(this.mContext, "getWallpaperColors");
        return sGlobals.getWallpaperColors(which, userId, this.mContext.getDisplayId());
    }

    public void addOnColorsChangedListener(LocalWallpaperColorConsumer callback, List<RectF> regions, int which) throws IllegalArgumentException {
        for (RectF region : regions) {
            RectF rectF = LOCAL_COLOR_BOUNDS;
            if (!rectF.contains(region)) {
                throw new IllegalArgumentException("Regions must be within bounds " + rectF);
            }
        }
        sGlobals.addOnColorsChangedListener(callback, regions, which, this.mContext.getUserId(), this.mContext.getDisplayId());
    }

    public void removeOnColorsChangedListener(LocalWallpaperColorConsumer callback) {
        sGlobals.removeOnColorsChangedListener(callback, 1, this.mContext.getUserId(), this.mContext.getDisplayId());
    }

    @Override // android.app.SemWallpaperManager
    public SemWallpaperColors semGetWallpaperColors(int which) {
        SemWallpaperColors colors = getPreconditionWallpaperColors(which);
        if (colors != null) {
            return colors;
        }
        return sGlobals.semGetWallpaperColors(which);
    }

    public SemWallpaperColors semGetPrimaryWallpaperColors(int which) {
        SemWallpaperColors colors = getPreconditionWallpaperColors(which);
        if (colors != null) {
            return colors;
        }
        return sGlobals.semGetPrimaryWallpaperColors(which);
    }

    private SemWallpaperColors getPreconditionWallpaperColors(int which) {
        if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && !Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.VIRTUAL_DISPLAY_WALLPAPER && (WhichChecker.isSubDisplay(which) || WhichChecker.isVirtualDisplay(which))) {
            Log.m94d(TAG, "getPreconditionWallpaperColors: Unsupported which. which = " + which);
            return SemWallpaperColors.getBlankWallpaperColors();
        }
        if ((which & 2) != 0) {
            try {
                if (sGlobals.mService.isDesktopStandAloneMode()) {
                    if (WhichChecker.getMode(which) == 0) {
                        int i = which | 8;
                    }
                }
                int em = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0, this.mContext.getUserId());
                int upsm = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_ULTRA_POWERSAVING_MODE, 0, this.mContext.getUserId());
                int mbu = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_MINIMAL_BATTERY_USE, 0, this.mContext.getUserId());
                if (em == 1 || upsm == 1 || mbu == 1) {
                    return SemWallpaperColors.getBlankWallpaperColors();
                }
                return null;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void semSendWallpaperCommand(int which, String action, Bundle extras) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.semSendWallpaperCommand(which, action, extras);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semClearWallpaperThumbnailCache(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            sGlobals.mService.semClearWallpaperThumbnailCache(which, userId, callingPackage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semRequestWallpaperColorsAnalysis(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            sGlobals.mService.semRequestWallpaperColorsAnalysis(which, callingPackage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semSetWallpaperColorOverrideAreas(int which, int userId, String colorAreas) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.semSetWallpaperColorOverrideAreas(which, userId, colorAreas);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semSetSmartCropRect(int which, Rect original, Rect smartCrop) {
        sGlobals.semSetSmartCropRect(which, original, smartCrop);
    }

    @Override // android.app.SemWallpaperManager
    public Rect semGetSmartCropRect(int which) {
        return sGlobals.semGetSmartCropRect(which);
    }

    public ParcelFileDescriptor getWallpaperFile(int which, int userId) {
        return getWallpaperFile(which, userId, true, 0);
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getWallpaperFile(int which, int userId, int kwpType) {
        return getWallpaperFile(which, userId, true, kwpType);
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getWallpaperFile(int which, boolean getCropped) {
        return getWallpaperFile(which, this.mContext.getUserId(), getCropped, 0);
    }

    public ParcelFileDescriptor getWallpaperFile(int which, int userId, boolean getCropped, int kwpType) {
        checkExactlyOneWallpaperFlagSet(which);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle outParams = new Bundle();
            return sGlobals.mService.getWallpaperWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), null, which, outParams, userId, getCropped, false, kwpType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            if (CompatChanges.isChangeEnabled(RETURN_DEFAULT_ON_SECURITY_EXCEPTION) && !CompatChanges.isChangeEnabled(THROW_ON_SECURITY_EXCEPTION)) {
                Log.m102w(TAG, "No permission to access wallpaper, returning default wallpaper file to avoid crashing legacy app.");
                return getDefaultSystemWallpaperFile();
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion < 27) {
                Log.m102w(TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                return null;
            }
            throw e2;
        }
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getLockWallpaperFile(int userId) {
        return getLockWallpaperFile(userId, 2);
    }

    public ParcelFileDescriptor getLockWallpaperFile(int userId, int which, boolean getCropped) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle outParams = new Bundle();
            return sGlobals.mService.getWallpaperWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), null, which, outParams, userId, getCropped, true, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getLockWallpaperFile(int userId, int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle outParams = new Bundle();
            return sGlobals.mService.getLockWallpaper(null, outParams, userId, which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forgetLoadedWallpaper() {
        sGlobals.forgetLoadedWallpaper();
    }

    public WallpaperInfo getWallpaperInfo() {
        return getWallpaperInfoForUser(this.mContext.getUserId());
    }

    public WallpaperInfo getWallpaperInfoForUser(int userId) {
        return getWallpaperInfo(1, userId);
    }

    public WallpaperInfo getWallpaperInfo(int which) {
        return getWallpaperInfo(which, this.mContext.getUserId());
    }

    @Override // android.app.SemWallpaperManager
    public WallpaperInfo getWallpaperInfo(int which, int userId) {
        checkExactlyOneWallpaperFlagSet(which);
        try {
            if (sGlobals.mService == null) {
                Log.m102w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            return sGlobals.mService.getWallpaperInfoWithFlags(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor getWallpaperInfoFile() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperInfoFile(this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWallpaperId(int which) {
        return getWallpaperIdForUser(which, this.mContext.getUserId());
    }

    public int getWallpaperIdForUser(int which, int userId) {
        try {
            if (sGlobals.mService == null) {
                Log.m102w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            return sGlobals.mService.getWallpaperIdForUser(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Intent getCropAndSetWallpaperIntent(Uri imageUri) {
        if (imageUri == null) {
            throw new IllegalArgumentException("Image URI must not be null");
        }
        if (!"content".equals(imageUri.getScheme())) {
            throw new IllegalArgumentException("Image URI must be of the content scheme type");
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent cropAndSetWallpaperIntent = new Intent(ACTION_CROP_AND_SET_WALLPAPER, imageUri);
        cropAndSetWallpaperIntent.addFlags(1);
        Intent homeIntent = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolvedHome = packageManager.resolveActivity(homeIntent, 65536);
        if (resolvedHome != null) {
            cropAndSetWallpaperIntent.setPackage(resolvedHome.activityInfo.packageName);
            List<ResolveInfo> cropAppList = packageManager.queryIntentActivities(cropAndSetWallpaperIntent, 0);
            if (cropAppList.size() > 0) {
                return cropAndSetWallpaperIntent;
            }
        }
        String cropperPackage = this.mContext.getString(C4337R.string.config_wallpaperCropperPackage);
        cropAndSetWallpaperIntent.setPackage(cropperPackage);
        List<ResolveInfo> cropAppList2 = packageManager.queryIntentActivities(cropAndSetWallpaperIntent, 0);
        if (cropAppList2.size() > 0) {
            return cropAndSetWallpaperIntent;
        }
        throw new IllegalArgumentException("Cannot use passed URI to set wallpaper; check that the type returned by ContentProvider matches image/*");
    }

    private boolean isRequestForDex(int which) {
        if (WhichChecker.isDex(which)) {
            return true;
        }
        return false;
    }

    public void setResource(int resid) throws IOException {
        setResource(resid, 3);
    }

    public int setResource(int resid, int which) throws IOException {
        return setResource(this.mContext, resid, which, 0, false, false, null);
    }

    private int setPreloadedResource(Context context, int resid, int which, boolean allowBackup, Bundle extras) throws IOException {
        return setResource(context, resid, which, 0, allowBackup, true, extras);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x011f A[Catch: RemoteException -> 0x014b, TRY_ENTER, TryCatch #5 {RemoteException -> 0x014b, blocks: (B:28:0x00fd, B:32:0x011f, B:35:0x0127, B:38:0x0193, B:41:0x019b, B:44:0x01ae, B:49:0x0138, B:52:0x0152, B:55:0x015a, B:57:0x0160, B:59:0x016d, B:61:0x0173, B:62:0x0175, B:64:0x0182, B:70:0x010d, B:73:0x0112, B:74:0x0116), top: B:16:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01ae A[Catch: RemoteException -> 0x014b, TRY_ENTER, TRY_LEAVE, TryCatch #5 {RemoteException -> 0x014b, blocks: (B:28:0x00fd, B:32:0x011f, B:35:0x0127, B:38:0x0193, B:41:0x019b, B:44:0x01ae, B:49:0x0138, B:52:0x0152, B:55:0x015a, B:57:0x0160, B:59:0x016d, B:61:0x0173, B:62:0x0175, B:64:0x0182, B:70:0x010d, B:73:0x0112, B:74:0x0116), top: B:16:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0150  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int setResource(Context context, int resid, int which, int type, boolean allowBackup, boolean isPreloaded, Bundle extras) throws IOException {
        Resources resources;
        Resources resources2;
        ParcelFileDescriptor fd;
        int i;
        Log.m98i(TAG, "setResource: which = " + which + ", resid = 0x" + Integer.toHexString(resid) + ", callingPkg = " + context.getOpPackageName() + ", hasExtras = " + (extras != null));
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        boolean isLiveWallpaper = isLiveWallpaper(which);
        boolean isKeyguardWallpaperShowing = isKeyguardWallpaperShowing(which);
        Bundle result = new Bundle();
        WallpaperSetCompletion completion = new WallpaperSetCompletion();
        try {
            Resources resources3 = context.getResources();
            if ("com.samsung.android.themecenter".equals(this.mContext.getPackageName())) {
                try {
                    String currentWallpaperPackage = Settings.System.getString(this.mContext.getContentResolver(), "current_sec_wallpaper_theme_package");
                    Resources resources4 = new APKContents(APKContents.getMainThemePackagePath(currentWallpaperPackage)).getResources();
                    resources = resources4;
                } catch (RemoteException e) {
                    e = e;
                    throw e.rethrowFromSystemServer();
                }
            } else {
                resources = resources3;
            }
            resources2 = resources;
            try {
                fd = sGlobals.mService.setWallpaper("res:" + resources.getResourceName(resid), context.getOpPackageName(), null, allowBackup, result, which, completion, UserHandle.myUserId(), type, isPreloaded, extras);
            } catch (RemoteException e2) {
                e = e2;
            }
        } catch (RemoteException e3) {
            e = e3;
        }
        try {
            if (fd != null) {
                FileOutputStream fos = null;
                try {
                    try {
                        fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                        try {
                            BitmapDrawable drawable = (BitmapDrawable) resources2.getDrawable(resid);
                            if (drawable == null || isPreloaded) {
                                copyStreamToWallpaperFile(resources2.openRawResource(resid), fos);
                            } else {
                                copyDrawableToWallpaperFile(drawable, fos);
                            }
                            fos.close();
                            completion.waitForCompletion();
                            IoUtils.closeQuietly(fos);
                        } catch (ClassCastException e4) {
                            e = e4;
                            e.printStackTrace();
                            IoUtils.closeQuietly(fos);
                            if (WhichChecker.isSystemAndLock(which)) {
                            }
                            if (!isRequestForDex(which)) {
                                sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), i);
                            }
                            if (isNeedToClearBackupData()) {
                            }
                            return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
                        }
                    } catch (Throwable th) {
                        th = th;
                        IoUtils.closeQuietly((AutoCloseable) null);
                        throw th;
                    }
                } catch (ClassCastException e5) {
                    e = e5;
                } catch (Throwable th2) {
                    th = th2;
                    IoUtils.closeQuietly((AutoCloseable) null);
                    throw th;
                }
            }
            if (WhichChecker.isSystemAndLock(which)) {
                if (!isLiveWallpaper || WhichChecker.isDex(which)) {
                    i = 1;
                } else if (isKeyguardWallpaperShowing) {
                    i = 1;
                    Log.m94d(TAG, "setResource: The type of lockscreen wallpaper is not live wallpaper already. Just keep going.");
                } else if (WhichChecker.isSystem(which)) {
                    Log.m94d(TAG, "setResource: call clear() to change the lock wallpaper to default. Since the system wallpaper has been changed and live wallpaper has been broken");
                    int flag = 2;
                    if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.isSubDisplay(which)) {
                        flag = 2 | 16;
                    }
                    i = 1;
                    sGlobals.mService.setKWPTypeLiveWallpaperWithMode(flag, 1);
                } else {
                    i = 1;
                }
            } else if (isLockscreenLiveWallpaperEnabled()) {
                i = 1;
            } else if (isPreloaded) {
                sGlobals.mService.copyPreloadedFileToWallpaperFile(which, this.mContext.getOpPackageName());
                i = 1;
            } else {
                sGlobals.mService.copyFileToWallpaperFile(which, this.mContext.getOpPackageName());
                i = 1;
            }
            if (!isRequestForDex(which) && WhichChecker.isLock(which) && !isKeyguardWallpaperShowing) {
                sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), i);
            }
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(which);
            }
            return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e6) {
            e = e6;
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    @Deprecated
    public void setWallpaperUri(String uriString, boolean allowBackup, int which) throws IOException, PackageManager.NameNotFoundException {
        Uri uri = Uri.parse(uriString);
        semSetUri(uri, allowBackup, which);
    }

    @Override // android.app.SemWallpaperManager
    public Uri semGetUri(int which) {
        try {
            String strUri = sGlobals.mService.semGetUri(which, this.mContext.getOpPackageName());
            if (strUri != null) {
                return Uri.parse(strUri);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semSetDLSWallpaperColors(SemWallpaperColors colors, int which) {
        Log.m94d(TAG, "semSetDLSWallpaperColors " + colors + ", " + which);
        try {
            sGlobals.mService.semSetDLSWallpaperColors(colors, which);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semSetUri(Uri uri, boolean allowBackup, int which) throws IOException, PackageManager.NameNotFoundException {
        semSetUri(uri, allowBackup, which, -1);
    }

    @Override // android.app.SemWallpaperManager
    public void semSetUri(Uri uri, boolean allowBackup, int which, int type) throws IOException, PackageManager.NameNotFoundException {
        semSetUri(uri, allowBackup, which, type, null);
    }

    public void semSetUri(Uri uri, boolean allowBackup, int which, int type, Bundle extras) throws IOException, PackageManager.NameNotFoundException {
        int type2;
        int i;
        String str;
        String resName;
        int type3;
        String resName2;
        Context pkgContext;
        Log.m94d(TAG, "semSetUri: uri =" + uri + ",allowBackup=" + allowBackup + ",which=" + which + ",hasExtras=" + (extras != null));
        if (uri == null) {
            return;
        }
        String scheme = uri.getScheme();
        String pkgName = uri.getAuthority();
        String resName3 = uri.getLastPathSegment();
        int type4 = SEM_SCHEME_MULTIPACK.equals(scheme) ? 3 : type;
        try {
            IWallpaperManager iWallpaperManager = sGlobals.mService;
            String uri2 = uri.toString();
            String opPackageName = this.mContext.getOpPackageName();
            int userId = this.mContext.getUserId();
            type2 = type4;
            i = 3;
            str = SEM_SCHEME_MULTIPACK;
            resName = resName3;
            try {
                iWallpaperManager.semSetUri(uri2, allowBackup, which, type2, opPackageName, userId, extras);
                if (WhichChecker.isLock(which)) {
                    sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), 1);
                }
            } catch (RemoteException e) {
                e = e;
                e.printStackTrace();
                type3 = type2;
                if (type3 != i) {
                }
                semClearBackupWallpapers(which);
                if (!str.equals(scheme)) {
                }
                return;
            }
        } catch (RemoteException e2) {
            e = e2;
            type2 = type4;
            i = 3;
            str = SEM_SCHEME_MULTIPACK;
            resName = resName3;
        }
        type3 = type2;
        if ((type3 != i || type3 == 5) && isNeedToClearBackupData()) {
            semClearBackupWallpapers(which);
        }
        if (!str.equals(scheme) || type3 == 5 || pkgName == null) {
            return;
        }
        if (!pkgName.isEmpty() && (resName2 = resName) != null && !resName2.isEmpty()) {
            try {
                pkgContext = this.mContext.createPackageContext(pkgName, 0);
            } catch (PackageManager.NameNotFoundException e3) {
                nnfe = e3;
            } catch (IOException e4) {
                ioe = e4;
            }
            if (pkgContext == null) {
                return;
            }
            int resId = pkgContext.getResources().getIdentifier(resName2, "drawable", pkgName);
            if (resId <= 0) {
                try {
                    Log.m94d(TAG, "Resource id not found");
                    return;
                } catch (PackageManager.NameNotFoundException e5) {
                    nnfe = e5;
                    nnfe.printStackTrace();
                    Log.m94d(TAG, "Set wallpaper based on END");
                } catch (IOException e6) {
                    ioe = e6;
                    ioe.printStackTrace();
                    Log.m94d(TAG, "Set wallpaper based on END");
                }
            }
            try {
                setPreloadedResource(pkgContext, resId, which, allowBackup, extras);
            } catch (PackageManager.NameNotFoundException e7) {
                nnfe = e7;
                nnfe.printStackTrace();
                Log.m94d(TAG, "Set wallpaper based on END");
            } catch (IOException e8) {
                ioe = e8;
                ioe.printStackTrace();
                Log.m94d(TAG, "Set wallpaper based on END");
            }
            Log.m94d(TAG, "Set wallpaper based on END");
        }
    }

    public void forceRebindWallpaper(int which) {
        try {
            sGlobals.mService.forceRebindWallpaper(which, this.mContext.getUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setBitmap(Bitmap bitmap) throws IOException {
        setBitmap(bitmap, null, true);
    }

    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup) throws IOException {
        return setBitmap(fullImage, visibleCropHint, allowBackup, 3);
    }

    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which) throws IOException {
        return setBitmap(fullImage, visibleCropHint, allowBackup, which, this.mContext.getUserId(), 0, null);
    }

    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which, int userId) throws IOException {
        return setBitmap(fullImage, visibleCropHint, allowBackup, which, userId, 0, null);
    }

    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which, Bundle extras) throws IOException {
        return setBitmap(fullImage, visibleCropHint, allowBackup, which, this.mContext.getUserId(), 0, extras);
    }

    private int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which, int userId, int type, Bundle extras) throws IOException {
        ParcelFileDescriptor fd;
        int i;
        Log.m94d(TAG, "setBitmap calling package = " + this.mContext.getOpPackageName() + ", allowBackup = " + allowBackup + ", which = " + which + ", userId = " + userId + ", type = " + type + ", hasExtras = " + (extras != null));
        if (fullImage != null) {
            Log.m94d(TAG, "setBitmap bitmap width = " + fullImage.getWidth() + ", height = " + fullImage.getHeight());
        }
        if (visibleCropHint != null) {
            Log.m94d(TAG, "setBitmap crop hint = " + visibleCropHint);
        }
        validateRect(visibleCropHint);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        boolean isLiveWallpaper = isLiveWallpaper(which);
        boolean isKeyguardWallpaperShowing = isKeyguardWallpaperShowing(which);
        Bundle result = new Bundle();
        WallpaperSetCompletion completion = new WallpaperSetCompletion();
        try {
            try {
                fd = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), visibleCropHint, allowBackup, result, which, completion, userId, type, false, extras);
            } catch (RemoteException e) {
                e = e;
            }
            try {
                if (fd != null) {
                    FileOutputStream fos = null;
                    try {
                        fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                        fullImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
                        fos.close();
                        completion.waitForCompletion();
                        IoUtils.closeQuietly(fos);
                    } catch (Throwable th) {
                        IoUtils.closeQuietly(fos);
                        throw th;
                    }
                }
                if (WhichChecker.isSystemAndLock(which)) {
                    if (!isLockscreenLiveWallpaperEnabled()) {
                        sGlobals.mService.copyFileToWallpaperFile(which, this.mContext.getOpPackageName());
                        i = 1;
                    } else {
                        i = 1;
                    }
                } else if (!isLiveWallpaper || WhichChecker.isDex(which)) {
                    i = 1;
                } else if (!isKeyguardWallpaperShowing) {
                    if (!WhichChecker.isSystem(which)) {
                        i = 1;
                    } else {
                        Log.m94d(TAG, "setBitmap: call clear() to change the lock wallpaper to default. Since the system wallpaper has been changed and live wallpaper has been broken");
                        int flag = 2;
                        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.isSubDisplay(which)) {
                            flag = 2 | 16;
                        }
                        i = 1;
                        sGlobals.mService.setKWPTypeLiveWallpaperWithMode(flag, 1);
                    }
                } else {
                    i = 1;
                    Log.m94d(TAG, "setBitmap: The type of lockscreen wallpaper is not live wallpaper already. Just keep going.");
                }
                if (!isRequestForDex(which) && WhichChecker.isLock(which) && !isKeyguardWallpaperShowing) {
                    sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), i);
                }
                if (isNeedToClearBackupData()) {
                    semClearBackupWallpapers(which);
                }
                return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
            } catch (RemoteException e2) {
                e = e2;
                throw e.rethrowFromSystemServer();
            }
        } catch (RemoteException e3) {
            e = e3;
        }
    }

    private final void validateRect(Rect rect) {
        if (rect != null && rect.isEmpty()) {
            throw new IllegalArgumentException("visibleCrop rectangle must be valid and non-empty");
        }
    }

    public void setStream(InputStream bitmapData) throws IOException {
        setStream(bitmapData, null, true);
    }

    private void copyStreamToWallpaperFile(InputStream data, FileOutputStream fos) throws IOException {
        FileUtils.copy(data, fos);
    }

    private void copyDrawableToWallpaperFile(BitmapDrawable drawable, FileOutputStream fos) {
        Log.m98i(TAG, "copyDrawableToWallpaperFile");
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            try {
                outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] data = outputStream.toByteArray();
                inputStream = new ByteArrayInputStream(data);
                FileUtils.copy(inputStream, fos);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            IoUtils.closeQuietly(outputStream);
            IoUtils.closeQuietly(inputStream);
        }
    }

    public int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup) throws IOException {
        return setStream(bitmapData, visibleCropHint, allowBackup, 3);
    }

    public int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup, int which) throws IOException {
        return setStream(bitmapData, visibleCropHint, allowBackup, which, 0);
    }

    private int setPreloadedStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup, int which) throws IOException {
        return setStream(bitmapData, visibleCropHint, allowBackup, which, 0, true, null);
    }

    private int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup, int which, int type) throws IOException {
        return setStream(bitmapData, visibleCropHint, allowBackup, which, type, false, null);
    }

    public int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup, int which, int type, boolean isPreloaded, Bundle extras) throws IOException {
        int i;
        Log.m94d(TAG, "setStream calling package = " + this.mContext.getOpPackageName() + ", allowBackup = " + allowBackup + ", which = " + which + ", type = " + type + ", hasExtra = " + (extras != null));
        if (bitmapData != null) {
            Log.m94d(TAG, "setStream bitmap data = " + bitmapData);
        }
        if (visibleCropHint != null) {
            Log.m94d(TAG, "setStream crop hint = " + visibleCropHint);
        }
        validateRect(visibleCropHint);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        boolean isLiveWallpaper = isLiveWallpaper(which);
        boolean isKeyguardWallpaperShowing = isKeyguardWallpaperShowing(which);
        Bundle result = new Bundle();
        WallpaperSetCompletion completion = new WallpaperSetCompletion();
        try {
            Log.m94d(TAG, "begin setWallpaper()");
            try {
                ParcelFileDescriptor fd = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), visibleCropHint, allowBackup, result, which, completion, this.mContext.getUserId(), type, isPreloaded, extras);
                Log.m94d(TAG, "finish setWallpaper()");
                try {
                    if (fd != null) {
                        FileOutputStream fos = null;
                        try {
                            fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                            copyStreamToWallpaperFile(bitmapData, fos);
                            fos.close();
                            completion.waitForCompletion();
                            IoUtils.closeQuietly(fos);
                        } catch (Throwable th) {
                            IoUtils.closeQuietly(fos);
                            throw th;
                        }
                    }
                    int userId = UserHandle.getCallingUserId();
                    Log.m94d(TAG, "setStream: userId : " + userId);
                    if (WhichChecker.isSystemAndLock(which)) {
                        if (isLockscreenLiveWallpaperEnabled()) {
                            i = 1;
                        } else if (isPreloaded) {
                            sGlobals.mService.copyPreloadedFileToWallpaperFile(which, this.mContext.getOpPackageName());
                            i = 1;
                        } else {
                            sGlobals.mService.copyFileToWallpaperFile(which, this.mContext.getOpPackageName());
                            i = 1;
                        }
                    } else if (!isLiveWallpaper || WhichChecker.isDex(which)) {
                        i = 1;
                    } else if (!isKeyguardWallpaperShowing) {
                        if (!WhichChecker.isSystem(which)) {
                            i = 1;
                        } else {
                            Log.m94d(TAG, "setStream: call clear() to change the lock wallpaper to default. Since the system wallpaper has been changed and live wallpaper has been broken");
                            int flag = 2;
                            if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.isSubDisplay(which)) {
                                flag = 2 | 16;
                            }
                            i = 1;
                            sGlobals.mService.setKWPTypeLiveWallpaperWithMode(flag, 1);
                        }
                    } else {
                        i = 1;
                        Log.m94d(TAG, "setStream: The type of lockscreen wallpaper is not live wallpaper already. Just keep going.");
                    }
                    if (SemDesktopModeManager.LAUNCHER_PACKAGE.equals(this.mContext.getOpPackageName())) {
                        if (WhichChecker.isSystem(which)) {
                            File systemFile = new File(Environment.getUserSystemDirectory(this.mContext.getUserId()), "wallpaper_desktop_orig");
                            if (systemFile.exists() && systemFile.canRead()) {
                                Log.m94d(TAG, "setStream() result : wallpaper_desktop_orig file length=" + systemFile.length());
                            } else {
                                Log.m94d(TAG, "setStream() result : Invalid file path. which=" + which);
                            }
                        } else if (WhichChecker.isLock(which)) {
                            File lockFile = new File(Environment.getUserSystemDirectory(this.mContext.getUserId()), "wallpaper_lock_images/wallpaper_desktop_lock_orig");
                            if (lockFile.exists() && lockFile.canRead()) {
                                Log.m94d(TAG, "setStream() result : wallpaper_desktop_lock_orig file length=" + lockFile.length());
                            } else {
                                Log.m94d(TAG, "setStream() result : Invalid file path. which=" + which);
                            }
                        }
                    }
                    if (!isRequestForDex(which) && WhichChecker.isLock(which) && !isKeyguardWallpaperShowing) {
                        sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), i);
                    }
                    if (isNeedToClearBackupData()) {
                        semClearBackupWallpapers(which);
                    }
                    return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
                } catch (RemoteException e) {
                    e = e;
                    e.printStackTrace();
                    throw e.rethrowFromSystemServer();
                }
            } catch (RemoteException e2) {
                e = e2;
            }
        } catch (RemoteException e3) {
            e = e3;
        }
    }

    public boolean hasResourceWallpaper(int resid) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Resources resources = this.mContext.getResources();
            String name = "res:" + resources.getResourceName(resid);
            return sGlobals.mService.hasNamedWallpaper(name);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDesiredMinimumWidth() {
        StrictMode.assertUiContext(this.mContext, "getDesiredMinimumWidth");
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWidthHint(this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDesiredMinimumHeight() {
        StrictMode.assertUiContext(this.mContext, "getDesiredMinimumHeight");
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getHeightHint(this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void suggestDesiredDimensions(int minimumWidth, int minimumHeight) {
        int maximumTextureSize;
        StrictMode.assertUiContext(this.mContext, "suggestDesiredDimensions");
        try {
            try {
                maximumTextureSize = SystemProperties.getInt("sys.max_texture_size", 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (Exception e2) {
            maximumTextureSize = 0;
        }
        if (maximumTextureSize > 0 && (minimumWidth > maximumTextureSize || minimumHeight > maximumTextureSize)) {
            float aspect = minimumHeight / minimumWidth;
            if (minimumWidth > minimumHeight) {
                minimumWidth = maximumTextureSize;
                minimumHeight = (int) ((minimumWidth * aspect) + 0.5d);
            } else {
                minimumHeight = maximumTextureSize;
                minimumWidth = (int) ((minimumHeight / aspect) + 0.5d);
            }
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        sGlobals.mService.setDimensionHints(minimumWidth, minimumHeight, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
    }

    public void setDisplayPadding(Rect padding) {
        StrictMode.assertUiContext(this.mContext, "setDisplayPadding");
        try {
            if (sGlobals.mService == null) {
                Log.m102w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            sGlobals.mService.setDisplayPadding(padding, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDisplayOffset(IBinder windowToken, int x, int y) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperDisplayOffset(windowToken, x, y);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWallpaper() {
        Log.m94d(TAG, "clearWallpaper()");
        clearWallpaper(appendCurrentModeIfNeeded(5), this.mContext.getUserId());
        clearWallpaper(appendCurrentModeIfNeeded(6), this.mContext.getUserId());
    }

    @SystemApi
    public void clearWallpaper(int which, int userId) {
        Log.m94d(TAG, "clearWallpaper() called with: which = [" + which + "], userId = [" + userId + NavigationBarInflaterView.SIZE_MOD_END);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.clearWallpaper(this.mContext.getOpPackageName(), which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setWallpaperComponent(ComponentName name) {
        return setWallpaperComponent(name, this.mContext.getUserId());
    }

    @SystemApi
    public void setWallpaperDimAmount(float dimAmount) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setWallpaperDimAmount(MathUtils.saturate(dimAmount));
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public float getWallpaperDimAmount() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperDimAmount();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean lockScreenWallpaperExists() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.lockScreenWallpaperExists();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWallpaperComponent(ComponentName name, int userId) {
        return setWallpaperComponentWithFlags(name, 3, userId);
    }

    @SystemApi
    public boolean setWallpaperComponentWithFlags(ComponentName name, int which) {
        return setWallpaperComponentWithFlags(name, which, this.mContext.getUserId());
    }

    public boolean setWallpaperComponentWithFlags(ComponentName name, int which, int userId) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperManagerService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setWallpaperComponentChecked(name, this.mContext.getOpPackageName(), which, userId, null);
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && "com.samsung.android.aremoji".equals(name.getPackageName()) && WhichChecker.getMode(which) == 0) {
                which |= 16;
            }
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(which);
                return true;
            }
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperOffsets(IBinder windowToken, float xOffset, float yOffset) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(windowToken, xOffset, yOffset, this.mWallpaperXStep, this.mWallpaperYStep);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperOffsetSteps(float xStep, float yStep) {
        this.mWallpaperXStep = xStep;
        this.mWallpaperYStep = yStep;
    }

    public void sendWallpaperCommand(IBinder windowToken, String action, int x, int y, int z, Bundle extras) {
        try {
            WindowManagerGlobal.getWindowSession().sendWallpaperCommand(windowToken, action, x, y, z, extras, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperZoomOut(IBinder windowToken, float zoom) {
        if (zoom < 0.0f || zoom > 1.0f) {
            throw new IllegalArgumentException("zoom must be between 0 and 1: " + zoom);
        }
        if (windowToken == null) {
            throw new IllegalArgumentException("windowToken must not be null");
        }
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperZoomOut(windowToken, zoom);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWallpaperSupported() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperSupported(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSetWallpaperAllowed() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isSetWallpaperAllowed(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWallpaperOffsets(IBinder windowToken) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(windowToken, -1.0f, -1.0f, -1.0f, -1.0f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clear() throws IOException {
        Log.m94d(TAG, "clear()");
        int mode = isSubDisplay() ? 16 : 4;
        int systemFlag = mode | 1;
        int lockFlag = mode | 2;
        setStream(openDefaultWallpaper(this.mContext, systemFlag, false), null, false, systemFlag);
        clear(lockFlag);
    }

    public void clear(int which) throws IOException {
        Log.m94d(TAG, "clear, which = [" + which + NavigationBarInflaterView.SIZE_MOD_END);
        if (!WhichChecker.isSystem(which) && !WhichChecker.isLock(which) && !WhichChecker.isSystemAndLock(which)) {
            Log.m96e(TAG, "Must specify a valid wallpaper category to set");
            return;
        }
        int type = WhichChecker.getType(which);
        int mode = WhichChecker.getMode(which);
        if (WhichChecker.isSystemAndLock(type)) {
            clear(mode | 1);
            clear(mode | 2);
        } else {
            clearWallpaper(which, this.mContext.getUserId());
        }
    }

    private boolean isKeyguardWallpaperShowing(int which) {
        try {
            boolean isSubDisplay = WhichChecker.isSubDisplay(which);
            String settingName = isSubDisplay ? SETTINGS_LOCKSCREEN_WALLPAPER_SUB : SETTINGS_LOCKSCREEN_WALLPAPER;
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), settingName, 1, this.mContext.getUserId()) == 1;
        } catch (Exception e) {
            Log.m96e(TAG, "isKeyguardWallpaperShowing: " + e.getMessage());
            return true;
        }
    }

    private int appendCurrentModeIfNeeded(int which) {
        if (!isSubDisplay()) {
            return which;
        }
        int convertedWhich = which & (-5);
        return convertedWhich | 16;
    }

    public boolean isThemeWallpaper(Context context, int which) {
        String settingsName;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(which) == 0) {
            Log.m94d(TAG, "isThemeWallpaper: which = " + which + ". 'which' does not have mode. getCurrentImplicitMode.");
            which |= WhichChecker.getCurrentImplicitMode(context);
        }
        if (WhichChecker.isSubDisplay(which)) {
            if (WhichChecker.isLock(which)) {
                settingsName = "sub_display_lockscreen_wallpaper_transparency";
            } else {
                settingsName = "sub_display_system_wallpaper_transparency";
            }
        } else if (WhichChecker.isLock(which)) {
            settingsName = "lockscreen_wallpaper_transparent";
        } else {
            settingsName = "android.wallpaper.settings_systemui_transparency";
        }
        int type = Settings.System.getIntForUser(context.getContentResolver(), settingsName, -1, context.getUserId());
        Log.m94d(TAG, "isThemeWallpaper: which = " + which + ", name = " + settingsName + ", type = " + type);
        if (type == 2) {
            return true;
        }
        if (type == 1) {
            String lastCallingPckage = getLastCallingPackage(context, which);
            Log.m94d(TAG, "isThemeWallpaper: lastCallingPackage = " + lastCallingPckage);
            return "com.samsung.android.themecenter".equals(lastCallingPckage);
        }
        return false;
    }

    public static InputStream openDefaultWallpaper(Context context, int which) {
        if (FactoryTest.isFactoryBinary()) {
            if (WhichChecker.isLock(which) && !isLockscreenLiveWallpaperEnabledHelper()) {
                return null;
            }
            String path = SystemProperties.get(PROP_WALLPAPER);
            InputStream wallpaperInputStream = getWallpaperInputStream(path);
            if (wallpaperInputStream != null) {
                return wallpaperInputStream;
            }
            String cmfPath = getCmfWallpaperPath();
            InputStream cmfWallpaperInputStream = getWallpaperInputStream(cmfPath);
            if (cmfWallpaperInputStream == null) {
                try {
                    return context.getResources().openRawResource(C4337R.drawable.default_wallpaper);
                } catch (Resources.NotFoundException e) {
                }
            } else {
                return cmfWallpaperInputStream;
            }
        }
        return openDefaultWallpaper(context, which, true);
    }

    private static ParcelFileDescriptor getDefaultSystemWallpaperFile() {
        Iterator<String> it = getDefaultSystemWallpaperPaths().iterator();
        while (it.hasNext()) {
            String path = it.next();
            File file = new File(path);
            if (file.exists()) {
                try {
                    return ParcelFileDescriptor.open(file, 268435456);
                } catch (FileNotFoundException e) {
                }
            }
        }
        return null;
    }

    public static InputStream openDefaultWallpaper(Context context, int which, boolean changeSettings) {
        return openDefaultWallpaper(context, which, true, null);
    }

    public static InputStream openDefaultWallpaper(Context context, int which, boolean changeSettings, String color) {
        File defaultWallpaper;
        InputStream is = null;
        boolean supportOperatorWallpaper = (WhichChecker.isSubDisplay(which) && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) ? false : true;
        Log.m94d(TAG, "openDefaultWallpaper() which = " + which + " , color = " + color);
        if (TextUtils.isEmpty(color) && supportOperatorWallpaper && (defaultWallpaper = getDefaultWallpaperFile(context, which)) != null) {
            try {
                is = new FileInputStream(defaultWallpaper);
            } catch (IOException e) {
                Log.m103w(TAG, "getDefaultWallpaperFile error:", e);
            }
        }
        if (is == null) {
            if (sWallpaperResourcesInfo == null) {
                sWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
            }
            is = sWallpaperResourcesInfo.getDefaultImageWallpaper(which);
        }
        Log.m94d(TAG, "openDefaultWallpaper: by [" + context.getOpPackageName() + NavigationBarInflaterView.SIZE_MOD_END);
        return is;
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSupportCMFFeature() {
        return sWallpaperResourcesInfo.isSupportCMF();
    }

    @Override // android.app.SemWallpaperManager
    public int getDefaultWallpaperType(int which) {
        return sWallpaperResourcesInfo.getDefaultWallpaperType(which, getDeviceColor(this.mContext));
    }

    @Override // android.app.SemWallpaperManager
    public String getDefaultMultipackStyle(int which) {
        return sWallpaperResourcesInfo.getDefaultMultipackStyle(which);
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSupportDefaultMultipleWallpaper() {
        return isSubDisplay() ? sWallpaperResourcesInfo.isDefaultMultipack(18) : sWallpaperResourcesInfo.isDefaultMultipack(2);
    }

    public static File getDefaultWallpaperFile(Context context) {
        return getDefaultWallpaperFile(context, 1);
    }

    public static File getDefaultWallpaperFile(Context context, int which) {
        File wallpaperFile = SemWallpaperResourcesUtils.getOMCWallpaperFile(context, which);
        if (wallpaperFile == null) {
            return SemWallpaperResourcesUtils.getCSCWallpaperFile(context, which, null);
        }
        return wallpaperFile;
    }

    public static String getOMCVideoWallpaperFilePath(String videoName) {
        return SemWallpaperResourcesUtils.getOMCVideoWallpaperFilePath(videoName);
    }

    public static File getOMCWallpaperFile(Context context, int flag) {
        return SemWallpaperResourcesUtils.getOMCWallpaperFile(context, flag, null);
    }

    public static File getOMCWallpaperFile(Context context, int flag, String color) {
        return SemWallpaperResourcesUtils.getOMCWallpaperFile(context, flag, color);
    }

    public static File getCSCWallpaperFile(Context context, int flag, SubUserWallpaperChecker additionalCheck, String color) {
        return SemWallpaperResourcesUtils.getCSCWallpaperFile(context, flag, color);
    }

    public static int getDisplayId(Context context, int which) {
        try {
            int displayId = getInstance(context).getIWallpaperManager().getDisplayId(which);
            return displayId;
        } catch (RemoteException e) {
            Log.m94d(TAG, "getDisplayId:" + e);
            return 0;
        }
    }

    public static boolean isVirtualWallpaperDisplay(Context context, int displayId) {
        try {
            boolean isVirtualDisplay = getInstance(context).getIWallpaperManager().isVirtualWallpaperDisplay(displayId);
            return isVirtualDisplay;
        } catch (RemoteException e) {
            Log.m94d(TAG, "isVirtualDisplay:" + e);
            return false;
        }
    }

    public static String getDeviceColor(Context context) {
        try {
            String deviceColor = getInstance(context).getIWallpaperManager().getDeviceColor();
            return deviceColor;
        } catch (RemoteException e) {
            Log.m94d(TAG, "getDeviceColor:" + e);
            return "";
        }
    }

    public static String getLegacyDeviceColor(Context context) {
        try {
            String legacyDeviceColor = getInstance(context).getIWallpaperManager().getLegacyDeviceColor();
            return legacyDeviceColor;
        } catch (RemoteException e) {
            Log.m94d(TAG, "getLegacyDeviceColor:" + e);
            return "";
        }
    }

    public static String getLastCallingPackage(Context context, int which) {
        try {
            String pakcage = getInstance(context).getIWallpaperManager().getLastCallingPackage(which);
            return pakcage;
        } catch (RemoteException e) {
            Log.m94d(TAG, "getLastCallingPackage:" + e);
            return "";
        }
    }

    private static InputStream getWallpaperInputStream(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    return new FileInputStream(file);
                } catch (IOException e) {
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    private static List<String> getDefaultSystemWallpaperPaths() {
        return List.of(SystemProperties.get(PROP_WALLPAPER), getCmfWallpaperPath());
    }

    private static String getCmfWallpaperPath() {
        return Environment.getProductDirectory() + WALLPAPER_CMF_PATH + "default_wallpaper_" + VALUE_CMF_COLOR;
    }

    public static ComponentName getDefaultWallpaperComponent(Context context) {
        ComponentName cn = null;
        String flat = SystemProperties.get(PROP_WALLPAPER_COMPONENT);
        if (!TextUtils.isEmpty(flat)) {
            cn = ComponentName.unflattenFromString(flat);
        }
        if (cn == null) {
            String flat2 = context.getString(C4337R.string.default_wallpaper_component);
            if (!TextUtils.isEmpty(flat2)) {
                cn = ComponentName.unflattenFromString(flat2);
            }
        }
        if (cn != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                packageManager.getPackageInfo(cn.getPackageName(), 786432);
                return cn;
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }
        return cn;
    }

    public boolean setLockWallpaperCallback(IWallpaperManagerCallback callback) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.setLockWallpaperCallback(callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setCoverWallpaperCallback(IWallpaperManagerCallback callback) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.setCoverWallpaperCallback(callback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWallpaperBackupEligible(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperBackupEligible(which, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.m96e(TAG, "Exception querying wallpaper backup eligibility: " + e.getMessage());
            return false;
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isWallpaperBackupAllowed(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperBackupAllowed(which, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.m96e(TAG, "Exception querying wallpaper backup eligibility: " + e.getMessage());
            return false;
        }
    }

    public ColorManagementProxy getColorManagementProxy() {
        return this.mCmProxy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkExactlyOneWallpaperFlagSet(int which) {
        if (WhichChecker.isSingleType(which)) {
        } else {
            throw new IllegalArgumentException("Must specify exactly one kind of wallpaper");
        }
    }

    public static class ColorManagementProxy {
        private final Set<ColorSpace> mSupportedColorSpaces;

        public ColorManagementProxy(Context context) {
            HashSet hashSet = new HashSet();
            this.mSupportedColorSpaces = hashSet;
            Display display = context.getDisplayNoVerify();
            if (display != null) {
                hashSet.addAll(Arrays.asList(display.getSupportedWideColorGamut()));
            }
        }

        public Set<ColorSpace> getSupportedColorSpaces() {
            return this.mSupportedColorSpaces;
        }

        boolean isSupportedColorSpace(ColorSpace colorSpace) {
            return colorSpace != null && (colorSpace == ColorSpace.get(ColorSpace.Named.SRGB) || getSupportedColorSpaces().contains(colorSpace));
        }

        void doColorManagement(ImageDecoder decoder, ImageDecoder.ImageInfo info) {
            if (!isSupportedColorSpace(info.getColorSpace())) {
                decoder.setTargetColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
                Log.m102w(WallpaperManager.TAG, "Not supported color space: " + info.getColorSpace());
            }
        }
    }

    public static void startBackupWallpaper(Context context, String path, String source) {
        startBackupWallpaper(context, 1, path, source, 0, "", "");
    }

    public static void startBackupWallpaper(Context context, int which, String path, String source, int securityLevel, String sessionTime, String saveKey) {
        startBackupWallpaper(context, "", which, path, source, securityLevel, sessionTime, saveKey);
    }

    public static void startBackupWallpaper(Context context, String action, int which, String path, String source, int securityLevel, String sessionTime, String saveKey) {
        WallpaperBackupRestoreManager backupManager = new WallpaperBackupRestoreManager();
        Log.m94d(TAG, "startBackupWallpaper action=" + action + " which=" + which + " path=" + path + " source=" + source + " securityLevel=" + securityLevel + " sessionTime=" + sessionTime);
        backupManager.startBackupWallpaper(context, action, which, path, source, securityLevel, sessionTime, saveKey);
    }

    public static void startRestoreWallpaper(Context context, String pathValue, String source) {
        startRestoreWallpaper(context, 1, pathValue, source, 0, "", null);
    }

    public static void startRestoreWallpaper(Context context, int which, String pathValue, String source, int securityLevel, String sessionKey, String restoreScreen) {
        startRestoreWallpaper(context, "", which, pathValue, source, securityLevel, sessionKey, restoreScreen);
    }

    public static void startRestoreWallpaper(Context context, String action, int which, String pathValue, String source, int securityLevel, String sessionKey, String restoreScreen) {
        WallpaperBackupRestoreManager restoreManager = new WallpaperBackupRestoreManager();
        Log.m94d(TAG, "startRestoreWallpaper action=" + action + " which=" + which + " path=" + pathValue + " source=" + source + " securityLevel=" + securityLevel + " restoreScreen=" + restoreScreen);
        restoreManager.startRestoreWallpaper(context, action, which, pathValue, source, securityLevel, sessionKey, restoreScreen);
    }

    @Override // android.app.SemWallpaperManager
    public void clearAll() throws IOException {
        Log.m94d(TAG, "clearAll");
        clear();
    }

    @Override // android.app.SemWallpaperManager
    public void setResourceAll(int resid) throws IOException {
        Log.m94d(TAG, "setResourceAll");
        Bitmap bitmap = generateBitmap(resid);
        if (bitmap != null) {
            setBitmap(bitmap);
            try {
                Settings.System.putInt(this.mContext.getContentResolver(), "android.wallpaper.settings_systemui_transparency", 2);
                return;
            } catch (SecurityException e) {
                Log.m97e(TAG, "Can't put value of SETTINGS_SYSTEMUI_TRANSPARENCY", e);
                return;
            }
        }
        Log.m96e(TAG, "theme bitmap is null");
    }

    private Bitmap generateBitmap(int resid) {
        Log.m94d(TAG, "generateBitmap");
        try {
            Resources resources = this.mContext.getResources();
            String resourceName = resources.getResourceName(resid);
            Log.m94d(TAG, "resourceName=" + resourceName);
            int themeResourceId = -1;
            Resources themeResources = null;
            if (!TextUtils.isEmpty(resourceName)) {
                String themePackage = null;
                int colon = resourceName.indexOf(58);
                if (colon > 0) {
                    themePackage = resourceName.substring(0, colon);
                }
                String ident = null;
                int slash = resourceName.lastIndexOf(47);
                if (slash > 0) {
                    ident = resourceName.substring(slash + 1);
                }
                String type = null;
                if (colon > 0 && slash > 0 && slash - colon > 1) {
                    type = resourceName.substring(colon + 1, slash);
                }
                if (themePackage != null && ident != null && type != null) {
                    try {
                        try {
                            themeResources = new APKContents(APKContents.getMainThemePackagePath(themePackage)).getResources();
                            if (themeResources != null) {
                                themeResourceId = themeResources.getIdentifier(resourceName, null, null);
                            } else {
                                Context c = this.mContext.createPackageContext(themePackage, 4);
                                themeResources = c.getResources();
                                themeResourceId = themeResources.getIdentifier(resourceName, null, null);
                            }
                        } catch (Resources.NotFoundException e) {
                            Log.m96e(TAG, "Resource not found: " + themeResourceId);
                        }
                    } catch (PackageManager.NameNotFoundException e2) {
                        Log.m96e(TAG, "Package name " + themePackage + " not found");
                    }
                }
            }
            String themePackage2 = TAG;
            Log.m94d(themePackage2, "themeResourceId=" + themeResourceId);
            if (themeResources == null || themeResourceId <= 0) {
                return null;
            }
            Bitmap bm = BitmapFactory.decodeResource(themeResources, themeResourceId);
            return bm;
        } catch (OutOfMemoryError e3) {
            Log.m103w(TAG, "Can't decode file", e3);
            return null;
        }
    }

    private class WallpaperSetCompletion extends IWallpaperManagerCallback.Stub {
        final CountDownLatch mLatch = new CountDownLatch(1);

        public WallpaperSetCompletion() {
        }

        public void waitForCompletion() {
            try {
                boolean completed = this.mLatch.await(30L, TimeUnit.SECONDS);
                if (completed) {
                    Log.m94d(WallpaperManager.TAG, "Wallpaper set completion.");
                } else {
                    Log.m94d(WallpaperManager.TAG, "Timeout waiting for wallpaper set completion!");
                }
            } catch (InterruptedException e) {
            }
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() throws RemoteException {
            this.mLatch.countDown();
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperChanged(int type, int which, Bundle extras) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemMultipackApplied(int which) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(WallpaperColors colors, int which, int userId) throws RemoteException {
            WallpaperManager.sGlobals.onWallpaperColorsChanged(colors, which, userId);
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsChanged(SemWallpaperColors colors, int which, int userId) throws RemoteException {
            WallpaperManager.sGlobals.onSemWallpaperColorsChanged(colors, which, userId);
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsAnalysisRequested(int which, int userId) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemBackupStatusChanged(int which, int status, int key) throws RemoteException {
            WallpaperManager.sGlobals.onSemBackupStatusChanged(which, status, key);
        }
    }

    public interface OnColorsChangedListener {
        void onColorsChanged(WallpaperColors wallpaperColors, int i);

        default void onColorsChanged(WallpaperColors colors, int which, int userId) {
            onColorsChanged(colors, which);
        }
    }

    @Override // android.app.SemWallpaperManager
    public int getLockWallpaperType() {
        return semGetWallpaperType(2);
    }

    @Override // android.app.SemWallpaperManager
    public int semGetWallpaperType(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        if (which <= 0 || WhichChecker.getType(which) == 0) {
            throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
        }
        try {
            return sGlobals.mService.semGetWallpaperType(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ComponentName semGetWallpaperComponent(int which, int userId) {
        checkExactlyOneWallpaperFlagSet(which);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semGetWallpaperComponent(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSystemAndLockPaired(int mode) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isSystemAndLockPaired(mode);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getHighlightFilterState(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getHighlightFilterState(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWallpaperComponentWithExtras(int which, ComponentName name, String callingPackage, int userId, Bundle extras) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        boolean isSupportLockOnlyLive = Rune.SUPPORT_PREVIEW_LOCK_ONLY_LIVE_WALLPAPER || isLockscreenLiveWallpaperEnabled();
        if (!isSupportLockOnlyLive && WhichChecker.isLock(which)) {
            Log.m96e(TAG, "setWallpaperComponentWithExtras : lock type is not supported. which = " + which);
            return false;
        }
        try {
            resetMultipleWallpaperSettingIfNeeded();
            sGlobals.mService.setWallpaperComponentChecked(name, callingPackage, which, userId, extras);
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(which);
            }
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWallpaperComponentExtras(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperComponentExtras(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWallpaperExtras(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperExtras(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWallpaperAssets(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperAssets(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor getWallpaperAssetFile(int which, int userId, String assetFilePath) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperAssetFile(this.mContext.getOpPackageName(), which, userId, assetFilePath);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getWallpaperOrientation(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperOrientation(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isDefaultWallpaperState(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isDefaultWallpaperState(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public Rect semGetWallpaperCropHint(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semGetWallpaperCropHint(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String videoFilePath) {
        setVideoLockscreenWallpaper(videoFilePath, null);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String videoFilePath, String themePackage) {
        setVideoLockscreenWallpaper(videoFilePath, themePackage, null, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int which) {
        setVideoLockscreenWallpaper(videoFilePath, themePackage, fileName, UserHandle.getCallingUserId(), which, true);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int which, boolean allowBackup) {
        setVideoWallpaper(videoFilePath, themePackage, fileName, UserHandle.getCallingUserId(), which, true, allowBackup, null);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which) {
        setVideoLockscreenWallpaper(videoFilePath, themePackage, fileName, userId, which, true);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which, boolean updateSetting) {
        setVideoWallpaper(videoFilePath, themePackage, fileName, userId, which, updateSetting, false, null);
    }

    public void setVideoWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which, Bundle extras) {
        setVideoWallpaper(videoFilePath, themePackage, fileName, userId, which, false, true, extras);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0110 A[Catch: RemoteException -> 0x0129, TryCatch #3 {RemoteException -> 0x0129, blocks: (B:45:0x010b, B:47:0x0110, B:48:0x011e, B:50:0x0124), top: B:44:0x010b }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0124 A[Catch: RemoteException -> 0x0129, TRY_LEAVE, TryCatch #3 {RemoteException -> 0x0129, blocks: (B:45:0x010b, B:47:0x0110, B:48:0x011e, B:50:0x0124), top: B:44:0x010b }] */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00dd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setVideoWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which, boolean updateSetting, boolean allowBackup, Bundle extras) {
        boolean isThumbnailCopied;
        String str;
        String str2;
        String videoFilePath2;
        AssetFileDescriptor assetFd;
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "setVideoWallpaper: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            ParcelFileDescriptor thumbnailFileFd = sGlobals.mService.getWallpaperThumbnailFileDescriptor(8, userId, which, 1, 1006632960);
            try {
                try {
                    try {
                        try {
                            if ((which & 1) != 0 && extras != null) {
                                try {
                                    String thumbnailUri = extras.getString("thumbnailUri");
                                    Log.m94d(TAG, "setVideoWallpaper: thumbnailUri = " + thumbnailUri);
                                    if (!TextUtils.isEmpty(thumbnailUri)) {
                                        File file = new File(thumbnailUri);
                                        if (file.exists()) {
                                            FileInputStream fin = null;
                                            FileOutputStream fout = null;
                                            try {
                                                try {
                                                    fin = new FileInputStream(thumbnailUri);
                                                    fout = new FileOutputStream(thumbnailFileFd.getFileDescriptor());
                                                    boolean isThumbnailCopied2 = FileUtils.copy(fin, fout) > 0;
                                                    isThumbnailCopied = isThumbnailCopied2;
                                                } finally {
                                                    IoUtils.closeQuietly(fin);
                                                    IoUtils.closeQuietly(fout);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                IoUtils.closeQuietly(fin);
                                                IoUtils.closeQuietly(fout);
                                            }
                                            if (TextUtils.isEmpty(videoFilePath)) {
                                                str = themePackage;
                                                str2 = fileName;
                                                videoFilePath2 = videoFilePath;
                                                assetFd = null;
                                            } else {
                                                str = themePackage;
                                                str2 = fileName;
                                                try {
                                                    AssetFileDescriptor assetFd2 = getVideoFDFromPackage(str, str2);
                                                    assetFd = assetFd2;
                                                    videoFilePath2 = "";
                                                } catch (RemoteException e2) {
                                                    e = e2;
                                                    e.printStackTrace();
                                                    throw e.rethrowFromSystemServer();
                                                }
                                            }
                                            String themePackage2 = !TextUtils.isEmpty(themePackage) ? "" : str;
                                            String fileName2 = !TextUtils.isEmpty(fileName) ? "" : str2;
                                            if (!isThumbnailCopied) {
                                                try {
                                                    Bitmap frame = getVideoWallpaperFrame(assetFd, videoFilePath2, fileName2);
                                                    saveWallpaperThumbnailFile(frame, thumbnailFileFd, 100);
                                                } catch (RemoteException e3) {
                                                    e = e3;
                                                    e.printStackTrace();
                                                    throw e.rethrowFromSystemServer();
                                                }
                                            }
                                            IoUtils.closeQuietly(assetFd);
                                            IoUtils.closeQuietly(thumbnailFileFd);
                                            String callingPackage = this.mContext.getOpPackageName();
                                            sGlobals.mService.setVideoWallpaper(videoFilePath2, themePackage2, fileName2, callingPackage, userId, which, allowBackup, extras);
                                            if (updateSetting) {
                                                sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), 1);
                                            }
                                            if (isNeedToClearBackupData()) {
                                                return;
                                            }
                                            semClearBackupWallpapers(which);
                                            return;
                                        }
                                    }
                                } catch (RemoteException e4) {
                                    e = e4;
                                    e.printStackTrace();
                                    throw e.rethrowFromSystemServer();
                                }
                            }
                            sGlobals.mService.setVideoWallpaper(videoFilePath2, themePackage2, fileName2, callingPackage, userId, which, allowBackup, extras);
                            if (updateSetting) {
                            }
                            if (isNeedToClearBackupData()) {
                            }
                        } catch (RemoteException e5) {
                            e = e5;
                            e.printStackTrace();
                            throw e.rethrowFromSystemServer();
                        }
                        IoUtils.closeQuietly(assetFd);
                        IoUtils.closeQuietly(thumbnailFileFd);
                        String callingPackage2 = this.mContext.getOpPackageName();
                    } catch (RemoteException e6) {
                        e = e6;
                    }
                    if (!TextUtils.isEmpty(fileName)) {
                    }
                    if (!isThumbnailCopied) {
                    }
                } catch (RemoteException e7) {
                    e = e7;
                }
                if (!TextUtils.isEmpty(themePackage)) {
                }
            } catch (RemoteException e8) {
                e = e8;
            }
            isThumbnailCopied = false;
            if (TextUtils.isEmpty(videoFilePath)) {
            }
        } catch (RemoteException e9) {
            e = e9;
        }
    }

    public boolean semSetWallpaperThumbnail(int which, Bitmap bitmap) {
        return false;
    }

    private void saveWallpaperThumbnailFile(Bitmap bitmap, ParcelFileDescriptor targetFileFd) {
        saveWallpaperThumbnailFile(bitmap, targetFileFd, 90);
    }

    private void saveWallpaperThumbnailFile(Bitmap bitmap, ParcelFileDescriptor targetFileFd, int quality) {
        if (bitmap == null || targetFileFd == null) {
            Log.m102w(TAG, "saveWallpaperThumbnailFile() bitmap or fd is null, b = " + bitmap + ", t = " + targetFileFd + ", quality = " + quality);
            return;
        }
        FileOutputStream fos = null;
        try {
            try {
                try {
                    fos = new ParcelFileDescriptor.AutoCloseOutputStream(targetFileFd);
                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, fos);
                    fos.close();
                } catch (IOException ie) {
                    Log.m103w(TAG, "saveWallpaperThumbnailFile() IOException", ie);
                }
            } catch (Exception e) {
                Log.m97e(TAG, "saveWallpaperThumbnailFile() Exception", e);
            }
            IoUtils.closeQuietly(fos);
            Log.m94d(TAG, "saveWallpaperThumbnailFile() saved complete " + bitmap.getByteCount());
        } catch (Throwable th) {
            IoUtils.closeQuietly(fos);
            throw th;
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoFilePath(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getVideoFilePath(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoPackage() {
        return getVideoPackage(2);
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoPackage(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getVideoPackage(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoFileName(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getVideoFileName(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWaitingForUnlockUser(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            boolean isWaitingForUnlockUser = sGlobals.mService.isWaitingForUnlockUser(which, userId);
            return isWaitingForUnlockUser;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isVideoWallpaper() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            boolean isVideoWallpaper = sGlobals.mService.isVideoWallpaper();
            Log.m94d(TAG, "isVideoWallpaper = " + isVideoWallpaper);
            return isVideoWallpaper;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean hasVideoWallpaper() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            boolean hasVideoWallpaper = sGlobals.mService.hasVideoWallpaper();
            return hasVideoWallpaper;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private Bitmap getVideoWallpaperFrame(AssetFileDescriptor fd, String filePath, String fileName) {
        Log.m94d(TAG, "getVideoWallpaperFrame, creating MediaMetadataRetriever");
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        Bitmap bitmap = null;
        long timeUs = 0;
        try {
            try {
                try {
                    try {
                        if (!TextUtils.isEmpty(filePath)) {
                            mediaMetadataRetriever.setDataSource(filePath);
                        } else {
                            if (fd == null) {
                                Log.m102w(TAG, "getVideoWallpaperFrame() file is invalid");
                                try {
                                    mediaMetadataRetriever.release();
                                    return null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                            mediaMetadataRetriever.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                        }
                        if (TextUtils.isEmpty(fileName)) {
                            bitmap = mediaMetadataRetriever.getFrameAtTime(0L);
                        } else {
                            String frameCountStr = mediaMetadataRetriever.extractMetadata(32);
                            String durationStr = mediaMetadataRetriever.extractMetadata(9);
                            if (!TextUtils.isEmpty(frameCountStr) && !TextUtils.isEmpty(durationStr)) {
                                int frameCount = Integer.parseInt(frameCountStr);
                                int duration = Integer.parseInt(durationStr);
                                int frameIndex = sWallpaperResourcesInfo.getDefaultVideoFrameInfo(fileName);
                                if (frameCount > 0 && frameIndex > 0 && frameCount >= frameIndex) {
                                    timeUs = (int) (duration * 1000 * (frameIndex / frameCount));
                                }
                            }
                            MediaMetadataRetriever.BitmapParams param = new MediaMetadataRetriever.BitmapParams();
                            param.setPreferredConfig(Bitmap.Config.ARGB_8888);
                            bitmap = mediaMetadataRetriever.getFrameAtTime(timeUs, 3, param);
                        }
                        Log.m94d(TAG, "getVideoWallpaperFrame " + timeUs);
                        mediaMetadataRetriever.release();
                    } catch (Throwable th) {
                        try {
                            mediaMetadataRetriever.release();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        throw th;
                    }
                } catch (NumberFormatException e3) {
                    e3.printStackTrace();
                    bitmap = mediaMetadataRetriever.getFrameAtTime(0L);
                    mediaMetadataRetriever.release();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
                mediaMetadataRetriever.release();
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        Log.m94d(TAG, "getVideoWallpaperFrame, done");
        return bitmap;
    }

    private Bitmap getThemeWallpaperBackground(String pkgname) {
        AssetManager assetManager;
        InputStream inputStream = null;
        try {
            APKContents apkContents = new APKContents(APKContents.getMainThemePackagePath(pkgname));
            assetManager = apkContents.getAssets();
            if (assetManager == null) {
                assetManager = this.mContext.getPackageManager().getResourcesForApplication(pkgname).getAssets();
            }
        } catch (IOException e) {
            Log.m96e(TAG, "getThemeWallpaperBackground IOException");
        } catch (Exception e2) {
            Log.m96e(TAG, "getThemeWallpaperBackground Exception");
        }
        if (assetManager != null) {
            inputStream = assetManager.open("preview/thumbnail_wallpaper.jpg");
            if (inputStream == null) {
                String themePkgName = pkgname.replace(".wallpaper", "");
                try {
                    AssetManager assetManager2 = new APKContents(APKContents.getMainThemePackagePath(themePkgName)).getAssets();
                    if (assetManager2 == null) {
                        assetManager2 = this.mContext.getPackageManager().getResourcesForApplication(themePkgName).getAssets();
                    }
                    if (assetManager2 != null) {
                        inputStream = assetManager2.open("preview/theme_lockscreen.jpg");
                    } else {
                        Log.m96e(TAG, "getAnimatedWallpaperBackground() : Theme pkg, AssetManager is null");
                        return null;
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (inputStream == null) {
                return null;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        Log.m96e(TAG, "getAnimatedWallpaperBackground() : Wallpaper pkg, AssetManager is null");
        return null;
    }

    private AssetFileDescriptor getVideoFDFromPackage(String pkgName, String fileName) {
        Resources otherResources;
        AssetManager otherAssetManager;
        Log.m94d(TAG, "getVideoFDFromPackage() pkgName = " + pkgName + " , fileName = " + fileName);
        Context otherContext = null;
        try {
            otherContext = this.mContext.createPackageContext(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (otherContext == null) {
            Log.m96e(TAG, "getVideoFDFromPackage() otherContext is null");
            APKContents apkContents = new APKContents(APKContents.getMainThemePackagePath(pkgName));
            otherResources = apkContents.getResources();
            otherAssetManager = apkContents.getAssets();
            if (otherResources == null || otherAssetManager == null) {
                return null;
            }
        } else {
            otherResources = otherContext.getResources();
            otherAssetManager = otherContext.getAssets();
        }
        if (WALLPAPER_PACKAGE.equals(pkgName)) {
            if (TextUtils.isEmpty(fileName)) {
                return null;
            }
            try {
                String name = fileName.substring(0, fileName.lastIndexOf(46));
                int resId = otherResources.getIdentifier(name, "raw", pkgName);
                AssetFileDescriptor afd = otherResources.openRawResourceFd(resId);
                return afd;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            if (otherAssetManager == null) {
                Log.m96e(TAG, "getVideoFDFromPackage() assetManager is null");
                return null;
            }
            try {
                return otherAssetManager.openFd(THEME_VIDEO_RES_ID);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String packageName) {
        Log.m98i(TAG, "setMotionWallpaper: packageName = " + packageName);
        setMotionWallpaper(packageName, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String packageName, int which) {
        Log.m98i(TAG, "setMotionWallpaper: packageName = " + packageName + ", which = " + which);
        setMotionWallpaper(packageName, 2, false);
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String packageName, int which, boolean allowBackup) {
        Log.m98i(TAG, "setMotionWallpaper: packageName = " + packageName + ", which = " + which + ", allowBackup = " + allowBackup);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            Bitmap motionBackground = getThemeWallpaperBackground(packageName);
            ParcelFileDescriptor thumbnailFileFd = sGlobals.mService.getWallpaperThumbnailFileDescriptor(1, this.mContext.getUserId(), which, 1, 1006632960);
            saveWallpaperThumbnailFile(motionBackground, thumbnailFileFd);
            sGlobals.mService.setMotionWallpaper(packageName, callingPackage, which, allowBackup);
            sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), 1);
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(which);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getMotionWallpaperPkgName(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getMotionWallpaperPkgName(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String packageName) throws IOException {
        Log.m98i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + packageName);
        setAnimatedLockscreenWallpaper(packageName, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String packageName, int which) throws IOException {
        Log.m98i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + packageName + ", which = " + which);
        setAnimatedLockscreenWallpaper(packageName, which, false);
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String packageName, int which, boolean allowBackup) throws IOException {
        Log.m98i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + packageName + ", which = " + which + ", allowBackup = " + allowBackup);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            Bitmap animatedBackground = getThemeWallpaperBackground(packageName);
            ParcelFileDescriptor thumbnailFileFd = sGlobals.mService.getWallpaperThumbnailFileDescriptor(4, this.mContext.getUserId(), which, 1, 1006632960);
            saveWallpaperThumbnailFile(animatedBackground, thumbnailFileFd);
            sGlobals.mService.setAnimatedWallpaper(packageName, callingPackage, which, allowBackup);
            sGlobals.mService.setKWPTypeLiveWallpaperWithMode(WhichChecker.getMode(which), 1);
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(which);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getAnimatedPkgName(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getAnimatedPkgName(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean isNeedToClearBackupData() {
        if (isSnapshotTestMode()) {
            return false;
        }
        boolean needClear = true;
        ArrayList<String> passList = new ArrayList<>();
        passList.add(this.mContext.getApplicationInfo().packageName);
        passList.add(this.mContext.getOpPackageName());
        Iterator<String> it = passList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String callingPkg = it.next();
            Log.m94d(TAG, "isNeedToClearBackupData(), pkgName = " + callingPkg);
            if ("com.samsung.android.themecenter".equals(callingPkg) || PACKAGE_NAME_LOCKSTAR.equals(callingPkg) || PACKAGE_NAME_FESTIVAL_WALLPAPER.equals(callingPkg)) {
                break;
            }
            if (Build.VERSION.SEM_PLATFORM_INT < 140100 || (!PACKAGE_NAME_DRESSROOM.equals(callingPkg) && !"com.android.systemui".equals(callingPkg))) {
                if (Build.VERSION.SEM_PLATFORM_INT >= ONEUI_6_1 && "com.sec.android.emergencylauncher".equals(callingPkg)) {
                    needClear = false;
                    break;
                }
                if (Build.VERSION.SEM_PLATFORM_INT > ONEUI_6_1 && PACKAGE_NAME_DYNAMIC_LOCKSCREEN.equals(callingPkg)) {
                    needClear = false;
                    break;
                }
            }
        }
        needClear = false;
        Log.m94d(TAG, "needClear = " + needClear);
        return needClear;
    }

    private boolean checkWhichInvalidation(int which) {
        if (WhichChecker.isSystem(which) || WhichChecker.isLock(which) || WhichChecker.isSystemAndLock(which)) {
            return WhichChecker.isPhone(which) || WhichChecker.isDex(which) || WhichChecker.isSubDisplay(which) || WhichChecker.isVirtualDisplay(which);
        }
        return false;
    }

    @Override // android.app.SemWallpaperManager
    public int semMakeBackupWallpaper() {
        return semMakeBackupWallpaper(3);
    }

    @Override // android.app.SemWallpaperManager
    public int semMakeBackupWallpaper(int which) {
        return semMakeBackupWallpaper(which, -1);
    }

    @Override // android.app.SemWallpaperManager
    public int semMakeBackupWallpaper(int which, int key) {
        Log.m94d(TAG, "semMakeBackupWallpaper: which = " + which + ", key = " + key);
        return semMakeBackupWallpaperWithExtras(which, key, null);
    }

    public int semMakeBackupWallpaperWithExtras(int which, int key, Bundle extras) {
        Log.m94d(TAG, "semMakeBackupWallpaper: which = " + which + ", key = " + key + ", extras = " + extras);
        if (Build.VERSION.SEM_PLATFORM_INT >= 140100 && (which <= 0 || WhichChecker.getType(which) == 0)) {
            throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
        }
        if (!checkWhichInvalidation(which)) {
            Log.m96e(TAG, "semMakeBackupWallpaper: Invalid which. which = " + which);
            return -1;
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "semMakeBackupWallpaper: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.makeSnapshot(which, key, extras);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semClearBackupWallpapers() {
        semClearBackupWallpapers(3);
    }

    @Override // android.app.SemWallpaperManager
    public void semClearBackupWallpapers(int which) {
        Log.m94d(TAG, "semClearBackupWallpapers: which = " + which);
        if (Build.VERSION.SEM_PLATFORM_INT >= 140100) {
            if (which <= 0 || WhichChecker.getType(which) == 0) {
                throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
            }
        } else if (which <= 0) {
            Log.m96e(TAG, "semClearBackupWallpapers: Invalid which.");
            return;
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.removeSnapshotByWhich(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semClearBackupWallpapers(String source) {
        if (TextUtils.isEmpty(source)) {
            throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "semClearBackupWallpapers: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.removeSnapshotBySource(source);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void clearBackupWallpaperGivenKey(int key) {
        Log.m94d(TAG, "clearBackupWallpaperGivenKey: key = " + key);
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.removeSnapshotByKey(key);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean semRestoreBackupWallpaper(int key) {
        Log.m94d(TAG, "semRestoreBackupWallpaper: key = " + key);
        if (key <= 0) {
            Log.m96e(TAG, "invalid key");
            return false;
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.restoreSnapshot(key, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSnapshotTestMode() {
        if (!Debug.semIsProductDev()) {
            return false;
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "isSnapshotTestMode: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isSnapshotTestMode();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSnapshotTestMode(boolean enable) {
        if (!Debug.semIsProductDev()) {
            return;
        }
        boolean isAllowedToSetTestMode = false;
        ArrayList<String> passList = new ArrayList<>();
        passList.add(this.mContext.getApplicationInfo().packageName);
        passList.add(this.mContext.getOpPackageName());
        Iterator<String> it = passList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String callingPkg = it.next();
            if (PACKAGE_NAME_DRESSROOM.equals(callingPkg)) {
                isAllowedToSetTestMode = true;
                break;
            }
        }
        if (!isAllowedToSetTestMode) {
            return;
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "setSnapshotTestMode: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setSnapshotTestMode(enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSnapshotCount() {
        return getSnapshotCount(-1);
    }

    public int getSnapshotCount(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "getSnapshotCount: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getSnapshotCount(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setSnapshotSource(int key, String source) {
        if (TextUtils.isEmpty(source)) {
            throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        }
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "setSnapshotSource: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.setSnapshotSource(key, source);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isValidSnapshot(int key) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "isValidSnapshot: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isValidSnapshot(key);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getSnapshotKeys(String source, int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "getSnapshotKeys: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getSnapshotKeys(source, which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isLiveWallpaper(int which) {
        int whichToCheck = which;
        if (!isLockscreenLiveWallpaperEnabled() || WhichChecker.isSystemAndLock(which)) {
            whichToCheck = WhichChecker.getMode(which) | 1;
        }
        return getWallpaperInfo(whichToCheck, this.mContext.getUserId()) != null || semGetWallpaperType(whichToCheck) == 7;
    }

    @Override // android.app.SemWallpaperManager
    public boolean isExternalLiveWallpaper() {
        String settingsName;
        if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || Rune.VIRTUAL_DISPLAY_WALLPAPER) {
            settingsName = SETTINGS_LOCKSCREEN_WALLPAPER;
        } else if (isSubDisplay()) {
            settingsName = SETTINGS_LOCKSCREEN_WALLPAPER_SUB;
        } else {
            settingsName = SETTINGS_LOCKSCREEN_WALLPAPER;
        }
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), settingsName, 1, this.mContext.getUserId()) == 0;
    }

    @Override // android.app.SemWallpaperManager
    public boolean isExternalLiveWallpaper(int which) {
        String settingsName;
        if (WhichChecker.isLock(which) && (WhichChecker.isSubDisplay(which) || WhichChecker.isPhone(which))) {
            if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE || Rune.VIRTUAL_DISPLAY_WALLPAPER) {
                settingsName = SETTINGS_LOCKSCREEN_WALLPAPER;
            } else if (WhichChecker.isSubDisplay(which)) {
                settingsName = SETTINGS_LOCKSCREEN_WALLPAPER_SUB;
            } else {
                settingsName = SETTINGS_LOCKSCREEN_WALLPAPER;
            }
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), settingsName, 1, this.mContext.getUserId()) == 0;
        }
        ComponentName component = semGetWallpaperComponent(which, this.mContext.getUserId());
        if (component == null) {
            return false;
        }
        String serviceName = component.getClassName();
        return true ^ IMAGE_WALLPAPER_SERVICE_NAME.equals(serviceName);
    }

    public boolean isPreloadedLiveWallpaper(int which) {
        ComponentName component = semGetWallpaperComponent(which, this.mContext.getUserId());
        if (component == null) {
            return false;
        }
        String pkgName = component.getPackageName();
        return isPreloadedLiveWallpaperPackage(pkgName);
    }

    public boolean isPreloadedLiveWallpaperPackage(String packageName) {
        return PACKAGE_NAME_SPRITE.equals(packageName);
    }

    public void notifyCompletePurchase() {
    }

    public void setOpenThemeWallpaper(Bitmap bitmap, Rect visibleCropHint, boolean needBackup) {
    }

    public void setOpenThemeWallpaper(boolean needBackup) {
    }

    public void restoreLockWallpaper() {
    }

    @Override // android.app.SemWallpaperManager
    public int getLidState() {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            int state = sGlobals.mService.getLidState();
            return state;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPid(int uid, int pid, String packageName, boolean enable) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.notifyPid(uid, pid, packageName, enable);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSubDisplay() {
        return Rune.SUPPORT_SUB_DISPLAY_MODE && getLidState() == 0;
    }

    @Override // android.app.SemWallpaperManager
    public int getAppliedScreen(String str, boolean z) {
        int i = z ? 16 : 4;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int i2 = i | (isApplied(z ? 17 : 1, str) ? 1 : 0) | (isApplied(z ? 18 : 2, str) ? 2 : 0);
        Log.m98i(TAG, "getAppliedScreen: " + str + ", " + i2);
        return i2;
    }

    private boolean isApplied(int which, String pkgName) {
        Uri uri = semGetUri(which);
        if (uri != null) {
            String uriString = uri.toString();
            Log.m98i(TAG, "isApplied: uri = " + uriString);
            if (!TextUtils.isEmpty(uriString) && uriString.contains(pkgName)) {
                return true;
            }
        }
        if ((which & 2) != 0) {
            String videoPkg = getVideoPackage(which);
            Log.m98i(TAG, "isApplied: videoPkg = " + videoPkg);
            if (!TextUtils.isEmpty(videoPkg) && videoPkg.contains(pkgName)) {
                return true;
            }
            String motionPkg = getMotionWallpaperPkgName(which);
            Log.m98i(TAG, "isApplied: motionPkg = " + motionPkg);
            if (!TextUtils.isEmpty(motionPkg) && motionPkg.contains(pkgName)) {
                return true;
            }
            String animatedPkg = getAnimatedPkgName(which);
            Log.m98i(TAG, "isApplied: animatedPkg = " + animatedPkg);
            if (!TextUtils.isEmpty(animatedPkg) && animatedPkg.contains(pkgName)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int which) {
        return SemWallpaperResourcesUtils.isDefaultOperatorWallpaper(context, which, null);
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int which, String color) {
        return SemWallpaperResourcesUtils.isDefaultOperatorWallpaper(context, which, color);
    }

    @Override // android.app.SemWallpaperManager
    public void addOnSemColorsChangedListener(OnSemColorsChangedListener listener, Handler handler) {
        addOnSemColorsChangedListener(listener, handler, this.mContext.getUserId());
    }

    @Override // android.app.SemWallpaperManager
    public void addOnSemColorsChangedListener(OnSemColorsChangedListener listener, Handler handler, int userId) {
        sGlobals.addOnSemColorsChangedListener(listener, handler, userId, this.mContext.getDisplayId());
    }

    @Override // android.app.SemWallpaperManager
    public void removeOnSemColorsChangedListener(OnSemColorsChangedListener callback) {
        removeOnSemColorsChangedListener(callback, this.mContext.getUserId());
    }

    @Override // android.app.SemWallpaperManager
    public void removeOnSemColorsChangedListener(OnSemColorsChangedListener callback, int userId) {
        sGlobals.removeOnSemColorsChangedListener(callback, userId, this.mContext.getDisplayId());
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(int[] seeds) {
        return getColorPalettes(seeds, false);
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(int[] seeds, boolean fromGoogle) {
        if (seeds == null || seeds.length <= 0) {
            return null;
        }
        List<int[][]> listPalettes = new ArrayList<>();
        if (fromGoogle) {
            int[] convertedSeeds = ColorPaletteCreator.converAccent1ToSeedColors(seeds);
            if (convertedSeeds != null && convertedSeeds.length > 0) {
                for (int seed : convertedSeeds) {
                    ColorScheme colorScheme = new ColorScheme(seed, false);
                    listPalettes.add(new ColorPalette(colorScheme).getTable());
                }
            }
        } else {
            ColorPaletteCreator paletteCreator = new ColorPaletteCreator();
            paletteCreator.setColors(seeds);
            paletteCreator.generateColorPalette();
            List<int[][]> palettes = paletteCreator.getColorPalettes();
            if (palettes != null && palettes.size() > 0) {
                for (int i = 0; i < palettes.size(); i++) {
                    listPalettes.add(palettes.get(i));
                }
            }
        }
        return listPalettes;
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(Bitmap bitmap) {
        return getColorPalettes(bitmap, false);
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(Bitmap bitmap, boolean fromGoogle) {
        int[] seeds = getSeedColors(bitmap, fromGoogle);
        if (seeds != null && seeds.length > 0) {
            return getColorPalettes(seeds, fromGoogle);
        }
        return null;
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(int which) {
        return getSeedColors(which, false);
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(int which, boolean fromGoogle) {
        Log.m94d(TAG, "getSeedColors: which = " + which + ", fromGoogle = " + fromGoogle);
        SemWallpaperColors colors = semGetWallpaperColors(which);
        int[] seeds = null;
        if (colors != null) {
            seeds = colors.getSeedColors();
        }
        if (fromGoogle) {
            return ColorPaletteCreator.converAccent1ToSeedColors(seeds);
        }
        return seeds;
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(Bitmap bitmap) {
        return getSeedColors(bitmap, false);
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(Bitmap bitmap, boolean fromGoogle) {
        Log.m94d(TAG, "getSeedColors: bitmap = " + bitmap + ", fromGoogle = " + fromGoogle);
        int[] seeds = ColorThemeExtractor.getSeedColors(bitmap);
        if (fromGoogle) {
            return ColorPaletteCreator.converAccent1ToSeedColors(seeds);
        }
        return seeds;
    }

    @Override // android.app.SemWallpaperManager
    public boolean canBackup() {
        List<Integer> whichSet = new ArrayList<>();
        whichSet.add(5);
        whichSet.add(6);
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            whichSet.add(17);
            if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                whichSet.add(18);
            }
        }
        Iterator<Integer> it = whichSet.iterator();
        while (it.hasNext()) {
            int which = it.next().intValue();
            if (canBackup(which)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.app.SemWallpaperManager
    public boolean canBackup(int which) {
        boolean isCustomWallpaper;
        boolean isDownloadedThemeWallpaper;
        Uri uri;
        int wallpaperType = semGetWallpaperType(which);
        boolean z = true;
        int settingsType = Settings.System.getIntForUser(this.mContext.getContentResolver(), getSettingsName(which), 1, -2);
        if (settingsType == 0) {
            isCustomWallpaper = true;
        } else {
            isCustomWallpaper = false;
        }
        boolean isBackupAllowed = isWallpaperBackupAllowed(which);
        if (settingsType == 3) {
            isDownloadedThemeWallpaper = true;
        } else {
            isDownloadedThemeWallpaper = false;
        }
        if (!isCustomWallpaper || !isBackupAllowed) {
        }
        if ((!isCustomWallpaper && !isDownloadedThemeWallpaper) || !isBackupAllowed) {
            z = false;
        }
        boolean canBackup = z;
        if (wallpaperType == 3 && !canBackup && (uri = semGetUri(which)) != null) {
            String stringUri = uri.toString();
            if (!TextUtils.isEmpty(stringUri) && stringUri.startsWith(BnRConstants.CUSTOM_PACK_PREFIX)) {
                canBackup = true;
            }
        }
        if (canBackup && wallpaperType == 7 && !isPreloadedLiveWallpaper(which)) {
            Log.m94d(TAG, "canBackup: which = " + which + ", external live wallpaper");
            canBackup = false;
        }
        Log.m94d(TAG, "canBackup: which = " + which + " canBackup = " + canBackup);
        return canBackup;
    }

    private String getSettingsName(int which) {
        int mode = WhichChecker.getMode(which);
        int type = WhichChecker.getType(which);
        switch (mode) {
            case 4:
                if (type == 2) {
                    return "lockscreen_wallpaper_transparent";
                }
                return "android.wallpaper.settings_systemui_transparency";
            case 8:
                if (type == 2) {
                    return BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY_DEX;
                }
                return BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY_DEX;
            case 16:
                if (type == 2) {
                    return "sub_display_lockscreen_wallpaper_transparency";
                }
                return "sub_display_system_wallpaper_transparency";
            default:
                return "";
        }
    }

    public boolean isWallpaperDataExists(int which) {
        if (sGlobals.mService == null) {
            Log.m102w(TAG, "isWallpaperDataExist: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperDataExists(this.mContext.getUserId(), which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
