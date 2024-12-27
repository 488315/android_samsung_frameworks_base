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
import android.graphics.Point;
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
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.R;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import libcore.io.IoUtils;

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
    public static final String COMMAND_DISPLAY_SWITCH = "android.wallpaper.displayswitch";
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
    public static final int LANDSCAPE = 1;
    public static final int LID_ABSENT = -1;
    public static final int LID_CLOSED = 0;
    public static final int LID_OPEN = 1;
    private static final int ONEUI_5_1 = 140100;
    private static final int ONEUI_6_1 = 150100;
    public static final int ORIENTATION_UNKNOWN = -1;
    private static final String PACKAGE_NAME_DRESSROOM = "com.samsung.android.app.dressroom";
    private static final String PACKAGE_NAME_DYNAMIC_LOCKSCREEN = "com.samsung.android.dynamiclock";
    private static final String PACKAGE_NAME_EMERGENCY_LAUNCHER = "com.sec.android.emergencylauncher";
    private static final String PACKAGE_NAME_FESTIVAL_WALLPAPER = "com.samsung.android.festivalwallpaper";
    private static final String PACKAGE_NAME_LOCKSTAR = "com.samsung.systemui.lockstar";
    private static final String PACKAGE_NAME_SPRITE = "com.samsung.android.wallpaper.live";
    private static final String PACKAGE_NAME_SYSTEMUI = "com.android.systemui";
    private static final String PACKAGE_NAME_THEME_CENTER = "com.samsung.android.themecenter";
    public static final int PORTRAIT = 0;
    private static final String PROP_LOCK_WALLPAPER = "ro.config.lock_wallpaper";
    private static final String PROP_WALLPAPER = "ro.config.wallpaper";
    private static final String PROP_WALLPAPER_COMPONENT = "ro.config.wallpaper_component";
    static final long RETURN_DEFAULT_ON_SECURITY_EXCEPTION = 239784307;
    public static final String SEM_ATTRIBUTE_TILT = "tilt";
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
    public static final int SQUARE_LANDSCAPE = 3;
    public static final int SQUARE_PORTRAIT = 2;
    public static final int SWP_TYPE_CUSTOM = 0;
    public static final int SWP_TYPE_PRELOAD = 1;
    public static final int SWP_TYPE_THEME = 2;
    public static final int SWP_TYPE_THEME_SINGLE = 3;
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
    private static Boolean sIsMultiCropEnabled = null;

    public interface LocalWallpaperColorConsumer {
        void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScreenOrientation {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SetWallpaperFlags {
    }

    public static int getOrientation(Point screenSize) {
        float ratio = screenSize.x / screenSize.y;
        if (ratio >= 1.3333334f) {
            return 1;
        }
        if (ratio > 1.0f) {
            return 3;
        }
        return ratio > 0.75f ? 2 : 0;
    }

    public static int getRotatedOrientation(int orientation) {
        switch (orientation) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            case 3:
                return 2;
            default:
                return -1;
        }
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
            this.mWidth = bitmap.getWidth();
            this.mHeight = bitmap.getHeight();
            setBounds(0, 0, this.mWidth, this.mHeight);
            this.mPaint = new Paint();
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
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

        public void addOnColorsChangedListener(OnColorsChangedListener callback, Handler handler, int userId, int displayId) {
            synchronized (this) {
                if (!this.mColorCallbackRegistered) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, userId, displayId);
                        this.mColorCallbackRegistered = true;
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't register for color updates", e);
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
                    Log.e(WallpaperManager.TAG, "Can't register for local color updates", e);
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
                        Log.e(WallpaperManager.TAG, "Can't unregister for local color updates", e);
                    }
                }
            }
        }

        public void removeOnColorsChangedListener(final OnColorsChangedListener callback, int userId, int displayId) {
            synchronized (this) {
                this.mColorListeners.removeIf(new Predicate() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda3
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
                        Log.w(WallpaperManager.TAG, "Can't unregister color updates", e);
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
                    handler.post(new Runnable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WallpaperManager.Globals.this.lambda$onWallpaperColorsChanged$1(listener, colors, which, userId);
                        }
                    });
                }
            }
        }

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
            Log.d(WallpaperManager.TAG, "semGetWallpaperColors: which = " + which);
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return this.mService.semGetWallpaperColors(which);
            } catch (RemoteException e) {
                return null;
            }
        }

        SemWallpaperColors semGetPrimaryWallpaperColors(int which) {
            Log.d(WallpaperManager.TAG, "semGetPrimaryWallpaperColors: which = " + which);
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return this.mService.semGetPrimaryWallpaperColors(which);
            } catch (RemoteException e) {
                return null;
            }
        }

        void semSetSmartCropRect(int which, Rect original, Rect smartCrop) {
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
            } else {
                try {
                    this.mService.semSetSmartCropRect(which, original, smartCrop);
                } catch (RemoteException e) {
                }
            }
        }

        Rect semGetSmartCropRect(int which) {
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return this.mService.semGetSmartCropRect(which);
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
            Log.d(WallpaperManager.TAG, "peekWallpaperBitmap: which =" + which + ", useCache = " + useCache);
            if (this.mService != null) {
                try {
                    try {
                        Trace.beginSection("WPMS.isWallpaperSupported");
                        if (!this.mService.isWallpaperSupported(context.getOpPackageName())) {
                            return null;
                        }
                    } finally {
                        Trace.endSection();
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            try {
                boolean isDesktopMode = WallpaperManager.sGlobals.mService.isDesktopModeEnabled(which);
                synchronized (this) {
                    if (((this.mIsCachedWallpaperForDeX && isDesktopMode) || (!this.mIsCachedWallpaperForDeX && !isDesktopMode)) && useCache && this.mCachedWallpaper != null && this.mCachedWallpaper.isValid(userId, which) && context.checkSelfPermission(Manifest.permission.READ_WALLPAPER_INTERNAL) == 0) {
                        Log.d(WallpaperManager.TAG, "peekWallpaperBitmap() cached image height=" + this.mCachedWallpaper.mCachedWallpaper.getHeight() + " width=" + this.mCachedWallpaper.mCachedWallpaper.getWidth());
                        return this.mCachedWallpaper.mCachedWallpaper;
                    }
                    this.mCachedWallpaper = null;
                    Bitmap currentWallpaper = null;
                    this.mIsCachedWallpaperForDeX = false;
                    try {
                        try {
                            try {
                                try {
                                    Trace.beginSection("WPMS.getCurrentWallpaperLocked");
                                } catch (RemoteException e2) {
                                    throw e2.rethrowFromSystemServer();
                                }
                            } catch (SecurityException e3) {
                                if (CompatChanges.isChangeEnabled(WallpaperManager.RETURN_DEFAULT_ON_SECURITY_EXCEPTION) && !CompatChanges.isChangeEnabled(WallpaperManager.THROW_ON_SECURITY_EXCEPTION)) {
                                    Log.w(WallpaperManager.TAG, "No permission to access wallpaper, returning default wallpaper to avoid crashing legacy app.");
                                    return getDefaultWallpaper(context, 1);
                                }
                                if (context.getApplicationInfo().targetSdkVersion >= 27) {
                                    throw e3;
                                }
                                Log.w(WallpaperManager.TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                            }
                        } catch (OutOfMemoryError e4) {
                            Log.w(WallpaperManager.TAG, "Out of memory loading the current wallpaper: " + e4);
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
                        if (this.mCachedWallpaper != null && this.mCachedWallpaper.isValid(userId, which)) {
                            return this.mCachedWallpaper.mCachedWallpaper;
                        }
                        if (returnDefault || (WhichChecker.isLock(which) && isStaticWallpaper(which))) {
                            return getDefaultWallpaper(context, which);
                        }
                        return null;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (RemoteException e5) {
                throw e5.rethrowFromSystemServer();
            }
        }

        public Rect peekWallpaperDimensions(Context context, boolean returnDefault, int which, int userId) {
            int i;
            ParcelFileDescriptor pfd;
            BitmapFactory.Options options;
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                if (!this.mService.isWallpaperSupported(context.getOpPackageName())) {
                    return new Rect();
                }
                Rect dimensions = null;
                synchronized (this) {
                    try {
                        Bundle params = new Bundle();
                        try {
                            i = which;
                            try {
                                pfd = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, i, params, userId, true, false, -1);
                            } catch (RemoteException e) {
                                ex = e;
                                i = 0;
                            } catch (IOException e2) {
                                i = 0;
                            }
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
                            } catch (RemoteException e3) {
                                ex = e3;
                                Log.w(WallpaperManager.TAG, "peek wallpaper dimensions failed", ex);
                            } catch (IOException e4) {
                            }
                        } catch (RemoteException e5) {
                            ex = e5;
                            i = 0;
                        } catch (IOException e6) {
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
            } catch (RemoteException e7) {
                throw e7.rethrowFromSystemServer();
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

        public Bitmap getCurrentWallpaperLocked(Context context, int which, int userId, final boolean hardware, final ColorManagementProxy cmProxy) {
            Bundle params;
            ImageDecoder.Source src;
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            Log.d(WallpaperManager.TAG, "getCurrentWallpaperLocked userId=" + userId + " by : " + context.getOpPackageName());
            try {
                params = new Bundle();
            } catch (RemoteException e) {
                e = e;
            }
            try {
                Trace.beginSection("WPMS.getWallpaperWithFeature_" + which);
                ParcelFileDescriptor pfd = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, which, params, userId, true, false, -1);
                Trace.endSection();
                try {
                    if (pfd == null) {
                        return null;
                    }
                    try {
                        InputStream is = new ParcelFileDescriptor.AutoCloseInputStream(pfd);
                        try {
                            try {
                                src = ImageDecoder.createSource(context.getResources(), is);
                            } catch (Throwable th) {
                                th = th;
                            }
                            try {
                                Bitmap decodeBitmap = ImageDecoder.decodeBitmap(src, new ImageDecoder.OnHeaderDecodedListener() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda0
                                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                                    public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                        WallpaperManager.Globals.lambda$getCurrentWallpaperLocked$2(hardware, cmProxy, imageDecoder, imageInfo, source);
                                    }
                                });
                                is.close();
                                return decodeBitmap;
                            } catch (Throwable th2) {
                                th = th2;
                                Throwable th3 = th;
                                try {
                                    is.close();
                                    throw th3;
                                } catch (Throwable th4) {
                                    th3.addSuppressed(th4);
                                    throw th3;
                                }
                            }
                        } catch (IOException | OutOfMemoryError e2) {
                            e = e2;
                            Log.w(WallpaperManager.TAG, "Can't decode file", e);
                            return null;
                        }
                    } catch (IOException | OutOfMemoryError e3) {
                        e = e3;
                    }
                } catch (RemoteException e4) {
                    e = e4;
                    throw e.rethrowFromSystemServer();
                }
            } catch (RemoteException e5) {
                e = e5;
                throw e.rethrowFromSystemServer();
            }
        }

        static /* synthetic */ void lambda$getCurrentWallpaperLocked$2(boolean hardware, ColorManagementProxy cmProxy, ImageDecoder decoder, ImageDecoder.ImageInfo info, ImageDecoder.Source source) {
            decoder.setMutableRequired(!hardware);
            if (cmProxy != null) {
                cmProxy.doColorManagement(decoder, info);
            }
        }

        private Bitmap getDefaultWallpaper(Context context, int which) {
            Trace.beginSection("WPMS.getDefaultWallpaper_" + which);
            Bitmap defaultWallpaper = getDefaultWallpaper(which);
            if (defaultWallpaper == null || defaultWallpaper.isRecycled()) {
                defaultWallpaper = null;
                Trace.beginSection("WPMS.openDefaultWallpaper");
                try {
                    InputStream is = WallpaperManager.openDefaultWallpaper(context, which);
                    try {
                        Trace.endSection();
                        if (is != null) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            Trace.beginSection("WPMS.decodeStream");
                            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                            defaultWallpaper = checkDeviceDensity(context, bitmap, which);
                            Trace.endSection();
                        }
                        if (is != null) {
                            is.close();
                        }
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
                } catch (IOException | OutOfMemoryError e) {
                    Log.w(WallpaperManager.TAG, "Can't decode stream", e);
                }
            }
            synchronized (this) {
                setDefaultWallpaper(which, defaultWallpaper);
            }
            Trace.endSection();
            return defaultWallpaper;
        }

        private boolean isStaticWallpaper(int which) {
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            try {
                return this.mService.isStaticWallpaper(which);
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
                Log.d(WallpaperManager.TAG, "checkDeviceDensity getDisplayId=" + displayId);
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
            Log.d(WallpaperManager.TAG, "checkDeviceDensity deviceRotation=" + deviceRotation + " deviceHeight=" + deviceHeight + " deviceWidth=" + deviceWidth + " bitmapHeight=" + bitmapHeight + " bitmapWidth=" + bitmapWidth);
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
            Log.d(WallpaperManager.TAG, "resize scale down.:" + scale);
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
                        Log.w(WallpaperManager.TAG, "Can't register for color updates", e);
                    }
                }
                this.mSemColorListeners.add(new Pair<>(callback, handler));
            }
        }

        public void removeOnSemColorsChangedListener(final OnSemColorsChangedListener callback, int userId, int displayId) {
            synchronized (this) {
                this.mSemColorListeners.removeIf(new Predicate() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return WallpaperManager.Globals.lambda$removeOnSemColorsChangedListener$3(OnSemColorsChangedListener.this, (Pair) obj);
                    }
                });
                if (this.mSemColorListeners.size() == 0 && this.mColorCallbackRegistered) {
                    this.mColorCallbackRegistered = false;
                    try {
                        this.mService.unregisterWallpaperColorsCallback(this, userId, displayId);
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't unregister color updates", e);
                    }
                }
            }
        }

        static /* synthetic */ boolean lambda$removeOnSemColorsChangedListener$3(OnSemColorsChangedListener callback, Pair pair) {
            return pair.first == callback;
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsChanged(final SemWallpaperColors colors, final int which, int userId) {
            Log.d(WallpaperManager.TAG, "onSemWallpaperColorsChanged " + colors + ", which=" + which);
            synchronized (this) {
                Iterator<Pair<OnSemColorsChangedListener, Handler>> it = this.mSemColorListeners.iterator();
                while (it.hasNext()) {
                    final Pair<OnSemColorsChangedListener, Handler> listener = it.next();
                    Handler handler = listener.second;
                    if (listener.second == null) {
                        handler = this.mMainLooperHandler;
                    }
                    handler.post(new Runnable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            WallpaperManager.Globals.this.lambda$onSemWallpaperColorsChanged$4(listener, colors, which);
                        }
                    });
                }
            }
        }

        public /* synthetic */ void lambda$onSemWallpaperColorsChanged$4(Pair listener, SemWallpaperColors colors, int which) {
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
        synchronized (sSync) {
            if (sWallpaperResourcesInfo == null) {
                sWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
            }
        }
        this.mWcgEnabled = context.getResources().getConfiguration().isScreenWideColorGamut() && (context.getResources().getBoolean(R.bool.config_enableWcgMode) || Rune.SUPPORT_WCG);
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
        return true;
    }

    public static boolean isMultiCropEnabled() {
        if (sIsMultiCropEnabled == null) {
            sIsMultiCropEnabled = Boolean.valueOf(com.android.window.flags.Flags.multiCrop());
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
        int which2;
        int modeEnsuredWhich;
        int i;
        int whichForSystemAndLockPaired;
        Drawable dr;
        ParcelFileDescriptor fd;
        if (!WhichChecker.isSystem(which) && !WhichChecker.isLock(which)) {
            if (WhichChecker.isDex(which)) {
                which2 = 9;
            } else {
                if (which == 0) {
                    InputStream is = openDefaultWallpaper(this.mContext, 2, false);
                    if (is == null) {
                        is = openDefaultWallpaper(this.mContext, 1, false);
                    }
                    return getDrawableFromStream(is);
                }
                which2 = 1;
            }
        } else {
            which2 = which;
        }
        if (!WhichChecker.isModeAbsent(which2)) {
            modeEnsuredWhich = which2;
        } else {
            modeEnsuredWhich = getModeEnsuredWhich(which2);
            Log.d(TAG, "semGetDrawable: mode is absent. which=" + which2 + ", adjustedWhich=" + modeEnsuredWhich);
        }
        int wallpaperType = semGetWallpaperType(modeEnsuredWhich);
        Log.i(TAG, "semGetDrawable: which = " + modeEnsuredWhich + ", wallpaperType = " + wallpaperType + ", orientation=" + orientation + ", caller=" + this.mContext.getPackageName());
        if (orientation == 1) {
            i = 0;
        } else {
            i = 1;
        }
        int rotation = i;
        try {
            fd = semGetThumbnailFileDescriptor(modeEnsuredWhich, this.mContext.getUserId(), rotation);
        } catch (IOException e) {
            Log.e(TAG, "semGetDrawable: e=" + e, e);
        }
        if (fd != null) {
            try {
                Drawable drawableFromBitmap = getDrawableFromBitmap(BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor()));
                if (fd != null) {
                    fd.close();
                }
                return drawableFromBitmap;
            } finally {
            }
        } else {
            if (fd != null) {
                fd.close();
            }
            Log.w(TAG, "semGetDrawable: Couldn't get thumbnail. Keep going..");
            try {
                boolean isDesktopMode = sGlobals.mService.isDesktopModeEnabled(modeEnsuredWhich);
                boolean isDefaultVideoWallpaper = sWallpaperResourcesInfo.isDefaultVideo(modeEnsuredWhich) && !isDesktopMode && isVideoWallpaper();
                boolean isLock = WhichChecker.isLock(modeEnsuredWhich);
                int whichForSystemAndLockPaired2 = modeEnsuredWhich;
                if (isLock && isSystemAndLockPaired(modeEnsuredWhich)) {
                    int whichForSystemAndLockPaired3 = WhichChecker.getMode(modeEnsuredWhich) | 1;
                    whichForSystemAndLockPaired = whichForSystemAndLockPaired3;
                } else {
                    whichForSystemAndLockPaired = whichForSystemAndLockPaired2;
                }
                if (isDefaultVideoWallpaper) {
                    String fileName = getVideoFileName(modeEnsuredWhich);
                    AssetFileDescriptor assetFd = null;
                    try {
                        assetFd = getVideoFDFromPackage(WALLPAPER_PACKAGE, fileName);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    Bitmap bitmap = getVideoWallpaperFrame(assetFd, null, fileName);
                    if (bitmap != null) {
                        return new BitmapDrawable(this.mContext.getResources(), bitmap);
                    }
                } else if (wallpaperType == 7) {
                    WallpaperInfo info = getWallpaperInfo(whichForSystemAndLockPaired, this.mContext.getUserId());
                    if (info != null) {
                        return info.loadThumbnail(this.mContext.getPackageManager());
                    }
                    return null;
                }
                ColorManagementProxy cmProxy = getColorManagementProxy();
                Bitmap wallpaperBmp = sGlobals.getCurrentWallpaperLocked(this.mContext, whichForSystemAndLockPaired, this.mContext.getUserId(), false, cmProxy);
                if (wallpaperBmp != null && !wallpaperBmp.isRecycled() && (dr = getDrawableFromBitmap(wallpaperBmp)) != null) {
                    return dr;
                }
                return getDrawableFromStream(openDefaultWallpaper(this.mContext, modeEnsuredWhich, false));
            } catch (RemoteException e3) {
                throw e3.rethrowFromSystemServer();
            }
        }
    }

    private Drawable getDrawableFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Log.d(TAG, "getDrawableFromBitmap : bitmap is null");
            return null;
        }
        Drawable dr = new BitmapDrawable(this.mContext.getResources(), bitmap);
        dr.setDither(false);
        return dr;
    }

    private Drawable getDrawableFromStream(InputStream is) {
        if (is == null) {
            Log.d(TAG, "getDrawableFromStream : input stream is null");
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
                Log.w(TAG, "Can't decode stream", e);
            }
            return null;
        } finally {
            IoUtils.closeQuietly(is);
        }
    }

    public ParcelFileDescriptor semGetScreenshotFileDescriptor(int which, int userId, Bundle extras) {
        checkExactlyOneWallpaperFlagSet(which);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getScreenshotFileDescriptor(which, userId, extras);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getModeEnsuredWhich(int which) {
        if (!WhichChecker.isModeAbsent(which)) {
            return which;
        }
        int mode = isSubDisplay() ? 16 : 4;
        return WhichChecker.getType(which) | mode;
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

    public Drawable getBuiltInDrawable(int outWidth, int outHeight, boolean scaleToFit, float horizontalAlignment, float verticalAlignment, int which) {
        int which2;
        ?? r1;
        InputStream is;
        boolean z;
        int outWidth2;
        RectF cropRectF;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(which) == 0 && isSubDisplay()) {
            Log.d(TAG, "getBuiltInDrawable, add flag");
            which2 = which | 16;
        } else {
            which2 = which;
        }
        Log.d(TAG, "getBuiltInDrawable: which = " + which2);
        checkExactlyOneWallpaperFlagSet(which2);
        Resources resources = this.mContext.getResources();
        float horizontalAlignment2 = Math.max(0.0f, Math.min(1.0f, horizontalAlignment));
        float verticalAlignment2 = Math.max(0.0f, Math.min(1.0f, verticalAlignment));
        InputStream wpStream = openDefaultWallpaper(this.mContext, which2);
        if (wpStream == null) {
            Log.w(TAG, "default wallpaper stream " + which2 + " is null");
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
                        Log.w(TAG, "cannot open region decoder for default wallpaper");
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
                        Log.w(TAG, "cannot decode default wallpaper");
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
                Log.w(TAG, "crop has bad values for full size image");
                return null;
            }
            Log.e(TAG, "default wallpaper dimensions are 0");
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
        if (!canPeekWallpaper(which)) {
            checkPermission(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_WALLPAPER_INTERNAL});
            return null;
        }
        return getDrawable(which);
    }

    public Drawable getFastDrawable() {
        return getFastDrawable(1);
    }

    public Drawable getFastDrawable(int which) {
        ColorManagementProxy cmProxy = getColorManagementProxy();
        boolean returnDefault = !WhichChecker.isLock(which);
        if (!canPeekWallpaper(which)) {
            checkPermission(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_WALLPAPER_INTERNAL});
            return null;
        }
        Bitmap bm = sGlobals.peekWallpaperBitmap(this.mContext, returnDefault, which, cmProxy);
        if (bm != null) {
            return new FastBitmapDrawable(bm);
        }
        return null;
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
        if (!canPeekWallpaper(which)) {
            return null;
        }
        return sGlobals.peekWallpaperDimensions(this.mContext, returnDefault, which, this.mContext.getUserId());
    }

    public List<Rect> getBitmapCrops(List<Point> displaySizes, int which, boolean originalBitmap) {
        checkExactlyOneWallpaperFlagSet(which);
        try {
            List<Rect> result = sGlobals.mService.getBitmapCrops(displaySizes, which, originalBitmap, this.mContext.getUserId());
            if (result != null) {
                return result;
            }
            Rect bitmapDimensions = peekBitmapDimensions(which, true);
            if (bitmapDimensions == null) {
                return List.of();
            }
            Point bitmapSize = new Point(bitmapDimensions.width(), bitmapDimensions.height());
            return getBitmapCrops(bitmapSize, displaySizes, (Map<Point, Rect>) null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Rect> getBitmapCrops(Point bitmapSize, List<Point> displaySizes, Map<Point, Rect> cropHints) {
        if (cropHints == null) {
            try {
                cropHints = Map.of();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Set<Map.Entry<Point, Rect>> entries = cropHints.entrySet();
        int[] screenOrientations = entries.stream().mapToInt(new ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int orientation;
                orientation = WallpaperManager.getOrientation((Point) ((Map.Entry) obj).getKey());
                return orientation;
            }
        }).toArray();
        List<Rect> crops = entries.stream().map(new WallpaperManager$$ExternalSyntheticLambda3()).toList();
        return sGlobals.mService.getFutureBitmapCrops(bitmapSize, displaySizes, screenOrientations, crops);
    }

    public WallpaperColors getWallpaperColors(Bitmap bitmap, Map<Point, Rect> cropHints) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        if (cropHints == null) {
            try {
                cropHints = Map.of();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Set<Map.Entry<Point, Rect>> entries = cropHints.entrySet();
        int[] screenOrientations = entries.stream().mapToInt(new ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int orientation;
                orientation = WallpaperManager.getOrientation((Point) ((Map.Entry) obj).getKey());
                return orientation;
            }
        }).toArray();
        List<Rect> crops = entries.stream().map(new WallpaperManager$$ExternalSyntheticLambda3()).toList();
        Point bitmapSize = new Point(bitmap.getWidth(), bitmap.getHeight());
        Rect crop = sGlobals.mService.getBitmapCrop(bitmapSize, screenOrientations, crops);
        float dimAmount = getWallpaperDimAmount();
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, crop.left, crop.top, crop.width(), crop.height());
        WallpaperColors result = WallpaperColors.fromBitmap(croppedBitmap, dimAmount);
        return result;
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
            if (!LOCAL_COLOR_BOUNDS.contains(region)) {
                throw new IllegalArgumentException("Regions must be within bounds " + LOCAL_COLOR_BOUNDS);
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
            Log.d(TAG, "getPreconditionWallpaperColors: Unsupported which. which = " + which);
            return SemWallpaperColors.getBlankWallpaperColors();
        }
        if ((which & 2) != 0) {
            int em = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0, this.mContext.getUserId());
            int upsm = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_ULTRA_POWERSAVING_MODE, 0, this.mContext.getUserId());
            int mbu = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_MINIMAL_BATTERY_USE, 0, this.mContext.getUserId());
            if (em == 1 || upsm == 1 || mbu == 1) {
                return SemWallpaperColors.getBlankWallpaperColors();
            }
            return null;
        }
        return null;
    }

    public void semSendWallpaperCommand(int which, String action, Bundle extras) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            sGlobals.mService.semRequestWallpaperColorsAnalysis(which, callingPackage);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor semGetThumbnailFileDescriptor(int which, int userId, int rotation) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semGetThumbnailFileDescriptor(which, userId, rotation);
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
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle outParams = new Bundle();
            return sGlobals.mService.getWallpaperWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), null, which, outParams, userId, getCropped, false, kwpType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (SecurityException e2) {
            if (CompatChanges.isChangeEnabled(RETURN_DEFAULT_ON_SECURITY_EXCEPTION) && !CompatChanges.isChangeEnabled(THROW_ON_SECURITY_EXCEPTION)) {
                Log.w(TAG, "No permission to access wallpaper, returning default wallpaper file to avoid crashing legacy app.");
                return getDefaultSystemWallpaperFile();
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion < 27) {
                Log.w(TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
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
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle outParams = new Bundle();
            int mode = WhichChecker.getMode(which);
            if (isSystemAndLockPaired(mode)) {
                which = mode | 1;
            }
            return sGlobals.mService.getWallpaperWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), null, which, outParams, userId, getCropped, true, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getLockWallpaperFile(int userId, int which) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle outParams = new Bundle();
            int mode = WhichChecker.getMode(which);
            if (isSystemAndLockPaired(mode)) {
                which = mode | 1;
            }
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
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            return sGlobals.mService.getWallpaperInfoWithFlags(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor getWallpaperInfoFile() {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
                Log.w(TAG, "WallpaperService not running");
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
        String cropperPackage = this.mContext.getString(R.string.config_wallpaperCropperPackage);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int setResource(android.content.Context r19, int r20, int r21, int r22, boolean r23, boolean r24, android.os.Bundle r25) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.setResource(android.content.Context, int, int, int, boolean, boolean, android.os.Bundle):int");
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
        Log.d(TAG, "semSetDLSWallpaperColors " + colors + ", " + which);
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

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void semSetUri(android.net.Uri r18, boolean r19, int r20, int r21, android.os.Bundle r22) throws java.io.IOException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 356
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.semSetUri(android.net.Uri, boolean, int, int, android.os.Bundle):void");
    }

    private void semSetWallpaper(String uri, boolean allowBackup, int which, int type, Bundle extras) throws IOException {
        if (type == 1000 && !WhichChecker.isLock(which)) {
            return;
        }
        try {
            ParcelFileDescriptor fd = sGlobals.mService.semSetWallpaper(uri, this.mContext.getOpPackageName(), null, null, allowBackup, null, which, null, this.mContext.getUserId(), type, false, extras);
            if (fd != null) {
                FileOutputStream fos = null;
                try {
                    fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                    fos.close();
                    IoUtils.closeQuietly(fos);
                } catch (Throwable th) {
                    IoUtils.closeQuietly(fos);
                    throw th;
                }
            }
            if (isNeedToClearBackupData()) {
                try {
                    semClearBackupWallpapers(which);
                } catch (RemoteException e) {
                    e = e;
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e2) {
            e = e2;
            e.printStackTrace();
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

    @Override // android.app.SemWallpaperManager
    public int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which, Bundle extras) throws IOException {
        return setBitmap(fullImage, visibleCropHint, allowBackup, which, this.mContext.getUserId(), 0, extras);
    }

    private int setBitmap(Bitmap fullImage, Rect visibleCropHint, boolean allowBackup, int which, int userId, int type, Bundle extras) throws IOException {
        Log.d(TAG, "setBitmap calling package = " + this.mContext.getOpPackageName() + ", allowBackup = " + allowBackup + ", which = " + which + ", userId = " + userId + ", type = " + type + ", hasExtras = " + (extras != null));
        if (fullImage != null) {
            Log.d(TAG, "setBitmap bitmap width = " + fullImage.getWidth() + ", height = " + fullImage.getHeight());
        }
        if (visibleCropHint != null) {
            Log.d(TAG, "setBitmap crop hint = " + visibleCropHint);
        }
        if (com.android.window.flags.Flags.multiCrop()) {
            SparseArray<Rect> cropMap = new SparseArray<>();
            if (visibleCropHint != null) {
                cropMap.put(-1, visibleCropHint);
            }
            return setBitmapWithCrops(fullImage, cropMap, allowBackup, which, userId);
        }
        validateRect(visibleCropHint);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle result = new Bundle();
        WallpaperSetCompletion completion = new WallpaperSetCompletion();
        List<Rect> crops = visibleCropHint == null ? null : List.of(visibleCropHint);
        try {
            ParcelFileDescriptor fd = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, crops, allowBackup, result, which, completion, userId, type, false, extras);
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
            return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setBitmapWithCrops(Bitmap fullImage, Map<Point, Rect> cropHints, boolean allowBackup, int which) throws IOException {
        final SparseArray<Rect> crops = new SparseArray<>();
        cropHints.forEach(new BiConsumer() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SparseArray.this.put(WallpaperManager.getOrientation((Point) obj), (Rect) obj2);
            }
        });
        return setBitmapWithCrops(fullImage, crops, allowBackup, which, this.mContext.getUserId());
    }

    private int setBitmapWithCrops(Bitmap fullImage, SparseArray<Rect> cropHints, boolean allowBackup, int which, int userId) throws IOException {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        int size = cropHints.size();
        int[] screenOrientations = new int[size];
        List<Rect> crops = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            screenOrientations[i] = cropHints.keyAt(i);
            Rect cropHint = cropHints.valueAt(i);
            validateRect(cropHint);
            crops.add(cropHint);
        }
        Bundle result = new Bundle();
        WallpaperSetCompletion completion = new WallpaperSetCompletion();
        try {
            try {
                ParcelFileDescriptor fd = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), screenOrientations, crops, allowBackup, result, which, completion, userId, 0, false, null);
                if (fd != null) {
                    FileOutputStream fos = null;
                    try {
                        try {
                            fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                        } catch (Throwable th) {
                            th = th;
                        }
                        try {
                            fullImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
                            fos.close();
                            completion.waitForCompletion();
                            IoUtils.closeQuietly(fos);
                        } catch (Throwable th2) {
                            th = th2;
                            IoUtils.closeQuietly(fos);
                            throw th;
                        }
                    } catch (RemoteException e) {
                        e = e;
                        throw e.rethrowFromSystemServer();
                    }
                }
                try {
                    if (isNeedToClearBackupData()) {
                        try {
                            semClearBackupWallpapers(which);
                        } catch (RemoteException e2) {
                            e = e2;
                            throw e.rethrowFromSystemServer();
                        }
                    }
                    return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
                } catch (RemoteException e3) {
                    e = e3;
                    throw e.rethrowFromSystemServer();
                }
            } catch (RemoteException e4) {
                e = e4;
            }
        } catch (RemoteException e5) {
            e = e5;
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
        Log.i(TAG, "copyDrawableToWallpaperFile");
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

    @Override // android.app.SemWallpaperManager
    public int setStream(InputStream bitmapData, Rect visibleCropHint, boolean allowBackup, int which, int type, boolean isPreloaded, Bundle extras) throws IOException {
        Log.d(TAG, "setStream calling package = " + this.mContext.getOpPackageName() + ", allowBackup = " + allowBackup + ", which = " + which + ", type = " + type + ", hasExtra = " + (extras != null));
        if (bitmapData != null) {
            Log.d(TAG, "setStream bitmap data = " + bitmapData);
        }
        if (visibleCropHint != null) {
            Log.d(TAG, "setStream crop hint = " + visibleCropHint);
        }
        if (com.android.window.flags.Flags.multiCrop()) {
            SparseArray<Rect> cropMap = new SparseArray<>();
            if (visibleCropHint != null) {
                cropMap.put(-1, visibleCropHint);
            }
            return setStreamWithCrops(bitmapData, cropMap, allowBackup, which, extras);
        }
        validateRect(visibleCropHint);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle result = new Bundle();
        WallpaperSetCompletion completion = new WallpaperSetCompletion();
        List<Rect> crops = visibleCropHint == null ? null : List.of(visibleCropHint);
        try {
            Log.d(TAG, "begin setWallpaper()");
            ParcelFileDescriptor fd = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, crops, allowBackup, result, which, completion, this.mContext.getUserId(), type, isPreloaded, extras);
            Log.d(TAG, "finish setWallpaper()");
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
            return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setStreamWithCrops(InputStream bitmapData, Map<Point, Rect> cropHints, boolean allowBackup, int which) throws IOException {
        final SparseArray<Rect> crops = new SparseArray<>();
        cropHints.forEach(new BiConsumer() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SparseArray.this.put(WallpaperManager.getOrientation((Point) obj), (Rect) obj2);
            }
        });
        return setStreamWithCrops(bitmapData, crops, allowBackup, which);
    }

    public int setStreamWithCrops(InputStream bitmapData, SparseArray<Rect> cropHints, boolean allowBackup, int which) throws IOException {
        return setStreamWithCrops(bitmapData, cropHints, allowBackup, which, null);
    }

    private int setStreamWithCrops(InputStream bitmapData, SparseArray<Rect> cropHints, boolean allowBackup, int which, Bundle extras) throws IOException {
        ParcelFileDescriptor fd;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        int size = cropHints.size();
        int[] screenOrientations = new int[size];
        List<Rect> crops = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            screenOrientations[i] = cropHints.keyAt(i);
            Rect cropHint = cropHints.valueAt(i);
            validateRect(cropHint);
            crops.add(cropHint);
        }
        Bundle result = new Bundle();
        WallpaperSetCompletion completion = new WallpaperSetCompletion();
        try {
            try {
                fd = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), screenOrientations, crops, allowBackup, result, which, completion, this.mContext.getUserId(), 0, false, extras);
            } catch (RemoteException e) {
                e = e;
            }
            try {
                if (fd != null) {
                    FileOutputStream fos = null;
                    try {
                        fos = new ParcelFileDescriptor.AutoCloseOutputStream(fd);
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        copyStreamToWallpaperFile(bitmapData, fos);
                        fos.close();
                        completion.waitForCompletion();
                        IoUtils.closeQuietly(fos);
                    } catch (Throwable th2) {
                        th = th2;
                        IoUtils.closeQuietly(fos);
                        throw th;
                    }
                }
                try {
                    int userId = UserHandle.getCallingUserId();
                    Log.i(TAG, "setStreamWithCrops: finished. which=" + which + ", userId=" + userId);
                    if (SemDesktopModeManager.LAUNCHER_PACKAGE.equals(this.mContext.getOpPackageName())) {
                        if (WhichChecker.isSystem(which)) {
                            File systemFile = new File(Environment.getUserSystemDirectory(this.mContext.getUserId()), "wallpaper_desktop_orig");
                            if (systemFile.exists() && systemFile.canRead()) {
                                Log.i(TAG, "setStreamWithCrops: result : wallpaper_desktop_orig file length=" + systemFile.length());
                            } else {
                                Log.i(TAG, "setStreamWithCrops: result : Invalid file path. which=" + which);
                            }
                        } else if (WhichChecker.isLock(which)) {
                            File lockFile = new File(Environment.getUserSystemDirectory(this.mContext.getUserId()), "wallpaper_lock_images/wallpaper_desktop_lock_orig");
                            if (lockFile.exists() && lockFile.canRead()) {
                                Log.i(TAG, "setStreamWithCrops: result : wallpaper_desktop_lock_orig file length=" + lockFile.length());
                            } else {
                                Log.i(TAG, "setStreamWithCrops: result : Invalid file path. which=" + which);
                            }
                        }
                    }
                    if (isNeedToClearBackupData()) {
                        semClearBackupWallpapers(which);
                    }
                    return result.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
                } catch (RemoteException e2) {
                    e = e2;
                    e.printStackTrace();
                    throw e.rethrowFromSystemServer();
                }
            } catch (RemoteException e3) {
                e = e3;
                e.printStackTrace();
                throw e.rethrowFromSystemServer();
            }
        } catch (RemoteException e4) {
            e = e4;
        }
    }

    public boolean hasResourceWallpaper(int resid) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            } catch (Exception e) {
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
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            sGlobals.mService.setDimensionHints(minimumWidth, minimumHeight, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
        } catch (RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    public void setDisplayPadding(Rect padding) {
        StrictMode.assertUiContext(this.mContext, "setDisplayPadding");
        try {
            if (sGlobals.mService == null) {
                Log.w(TAG, "WallpaperService not running");
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
        clearWallpaper(3, this.mContext.getUserId());
    }

    @SystemApi
    public void clearWallpaper(int which, int userId) {
        Log.d(TAG, "clearWallpaper() called with: which = [" + which + "], userId = [" + userId + NavigationBarInflaterView.SIZE_MOD_END);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            synchronized (sSync) {
                sGlobals.mService.clearWallpaper(this.mContext.getOpPackageName(), which, userId);
            }
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperManagerService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
        clear(3);
    }

    public void clear(int which) throws IOException {
        Log.d(TAG, "clear, which = [" + which + NavigationBarInflaterView.SIZE_MOD_END);
        if (!WhichChecker.isSystem(which) && !WhichChecker.isLock(which) && !WhichChecker.isSystemAndLock(which)) {
            Log.e(TAG, "Must specify a valid wallpaper category to set");
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

    private int appendCurrentModeIfNeeded(int which) {
        if (!isSubDisplay()) {
            return which;
        }
        int convertedWhich = which | 16;
        return convertedWhich;
    }

    public boolean isThemeSingleWallpaper(Context context, int which) {
        String settingsName;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(which) == 0) {
            Log.d(TAG, "isThemeSingleWallpaper: which = " + which + ". 'which' does not have mode. getCurrentImplicitMode.");
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
        Log.d(TAG, "isThemeSingleWallpaper: which = " + which + ", name = " + settingsName + ", type = " + type);
        if (type == 3) {
            return true;
        }
        return false;
    }

    public boolean isThemeWallpaper(Context context, int which) {
        String settingsName;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(which) == 0) {
            Log.d(TAG, "isThemeWallpaper: which = " + which + ". 'which' does not have mode. getCurrentImplicitMode.");
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
        Log.d(TAG, "isThemeWallpaper: which = " + which + ", name = " + settingsName + ", type = " + type);
        if (type == 2) {
            return true;
        }
        if (type == 1) {
            String lastCallingPckage = getLastCallingPackage(context, which);
            Log.d(TAG, "isThemeWallpaper: lastCallingPackage = " + lastCallingPckage);
            return "com.samsung.android.themecenter".equals(lastCallingPckage);
        }
        return false;
    }

    public static InputStream openDefaultWallpaper(Context context, int which) {
        if (FactoryTest.isFactoryBinary()) {
            String path = SystemProperties.get(PROP_WALLPAPER);
            InputStream wallpaperInputStream = getWallpaperInputStream(path);
            if (wallpaperInputStream != null) {
                return wallpaperInputStream;
            }
            String cmfPath = getCmfWallpaperPath();
            InputStream cmfWallpaperInputStream = getWallpaperInputStream(cmfPath);
            if (cmfWallpaperInputStream == null) {
                try {
                    return context.getResources().openRawResource(R.drawable.default_wallpaper);
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
        Log.d(TAG, "openDefaultWallpaper() which = " + which + " , color = " + color);
        if (TextUtils.isEmpty(color) && supportOperatorWallpaper && (defaultWallpaper = getDefaultWallpaperFile(context, which)) != null) {
            try {
                is = new FileInputStream(defaultWallpaper);
            } catch (IOException e) {
                Log.w(TAG, "getDefaultWallpaperFile error:", e);
            }
        }
        if (is == null) {
            synchronized (sSync) {
                if (sWallpaperResourcesInfo == null) {
                    sWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
                }
            }
            is = sWallpaperResourcesInfo.getDefaultImageWallpaper(which);
        }
        Log.d(TAG, "openDefaultWallpaper: by [" + context.getOpPackageName() + NavigationBarInflaterView.SIZE_MOD_END);
        return is;
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSupportCMFFeature() {
        return sWallpaperResourcesInfo.isSupportCMF();
    }

    @Override // android.app.SemWallpaperManager
    public int getDefaultWallpaperType(int which) {
        return sWallpaperResourcesInfo.getDefaultWallpaperType(getModeEnsuredWhich(which), getDeviceColor(this.mContext));
    }

    @Override // android.app.SemWallpaperManager
    public String getDefaultMultipackStyle(int which) {
        return sWallpaperResourcesInfo.getDefaultMultipackStyle(getModeEnsuredWhich(which));
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSupportDefaultMultipleWallpaper() {
        int modeEnsuredWhich = getModeEnsuredWhich(2);
        return sWallpaperResourcesInfo.isDefaultMultipack(modeEnsuredWhich);
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
            Log.d(TAG, "getDisplayId:" + e);
            return 0;
        }
    }

    public static boolean isVirtualWallpaperDisplay(Context context, int displayId) {
        try {
            boolean isVirtualDisplay = getInstance(context).getIWallpaperManager().isVirtualWallpaperDisplay(displayId);
            return isVirtualDisplay;
        } catch (RemoteException e) {
            Log.d(TAG, "isVirtualDisplay:" + e);
            return false;
        }
    }

    public static String getDeviceColor(Context context) {
        try {
            String deviceColor = getInstance(context).getIWallpaperManager().getDeviceColor();
            return deviceColor;
        } catch (RemoteException e) {
            Log.d(TAG, "getDeviceColor:" + e);
            return "";
        }
    }

    public static String getLegacyDeviceColor(Context context) {
        try {
            String legacyDeviceColor = getInstance(context).getIWallpaperManager().getLegacyDeviceColor();
            return legacyDeviceColor;
        } catch (RemoteException e) {
            Log.d(TAG, "getLegacyDeviceColor:" + e);
            return "";
        }
    }

    public static String getLastCallingPackage(Context context, int which) {
        try {
            String pakcage = getInstance(context).getIWallpaperManager().getLastCallingPackage(which);
            return pakcage;
        } catch (RemoteException e) {
            Log.d(TAG, "getLastCallingPackage:" + e);
            return "";
        }
    }

    public static String getLastCallingPackage(Context context, int which, boolean includePrefix) {
        try {
            String pakcage = getInstance(context).getIWallpaperManager().getLastCallingPackageWithPrefix(which, includePrefix);
            return pakcage;
        } catch (RemoteException e) {
            Log.d(TAG, "getLastCallingPackageWithPrefix:" + e);
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
            String flat2 = context.getString(R.string.default_wallpaper_component);
            if (!TextUtils.isEmpty(flat2)) {
                cn = ComponentName.unflattenFromString(flat2);
            }
        }
        if (!isComponentExist(context, cn)) {
            return null;
        }
        return cn;
    }

    public static ComponentName getCmfDefaultWallpaperComponent(Context context) {
        String[] cmfWallpaper;
        ComponentName cn = null;
        String[] cmfWallpaperMap = context.getResources().getStringArray(R.array.default_wallpaper_component_per_device_color);
        if (cmfWallpaperMap != null && cmfWallpaperMap.length > 0) {
            int length = cmfWallpaperMap.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String entry = cmfWallpaperMap[i];
                if (TextUtils.isEmpty(entry) || (cmfWallpaper = entry.split(",")) == null || cmfWallpaper.length != 2 || !VALUE_CMF_COLOR.equals(cmfWallpaper[0]) || TextUtils.isEmpty(cmfWallpaper[1])) {
                    i++;
                } else {
                    cn = ComponentName.unflattenFromString(cmfWallpaper[1]);
                    break;
                }
            }
        }
        if (!isComponentExist(context, cn)) {
            cn = null;
        }
        return cn == null ? getDefaultWallpaperComponent(context) : cn;
    }

    private static boolean isComponentExist(Context context, ComponentName cn) {
        if (cn == null) {
            return false;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo(cn.getPackageName(), 786432);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean setLockWallpaperCallback(IWallpaperManagerCallback callback) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperBackupEligible(which, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.e(TAG, "Exception querying wallpaper backup eligibility: " + e.getMessage());
            return false;
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isWallpaperBackupAllowed(int which) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperBackupAllowed(which, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.e(TAG, "Exception querying wallpaper backup eligibility: " + e.getMessage());
            return false;
        }
    }

    public ColorManagementProxy getColorManagementProxy() {
        return this.mCmProxy;
    }

    public static void checkExactlyOneWallpaperFlagSet(int which) {
        if (WhichChecker.isSingleType(which)) {
        } else {
            throw new IllegalArgumentException("Must specify exactly one kind of wallpaper");
        }
    }

    public static class ColorManagementProxy {
        private final Set<ColorSpace> mSupportedColorSpaces = new HashSet();

        public ColorManagementProxy(Context context) {
            Display display = context.getDisplayNoVerify();
            if (display != null) {
                this.mSupportedColorSpaces.addAll(Arrays.asList(display.getSupportedWideColorGamut()));
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
                Log.w(WallpaperManager.TAG, "Not supported color space: " + info.getColorSpace());
            }
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
                    Log.d(WallpaperManager.TAG, "Wallpaper set completion.");
                } else {
                    Log.d(WallpaperManager.TAG, "Timeout waiting for wallpaper set completion!");
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
    }

    public interface OnColorsChangedListener {
        void onColorsChanged(WallpaperColors wallpaperColors, int i);

        default void onColorsChanged(WallpaperColors colors, int which, int userId) {
            onColorsChanged(colors, which);
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
        Log.d(TAG, "startBackupWallpaper action=" + action + " which=" + which + " path=" + path + " source=" + source + " securityLevel=" + securityLevel + " sessionTime=" + sessionTime);
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
        Log.d(TAG, "startRestoreWallpaper action=" + action + " which=" + which + " path=" + pathValue + " source=" + source + " securityLevel=" + securityLevel + " restoreScreen=" + restoreScreen);
        restoreManager.startRestoreWallpaper(context, action, which, pathValue, source, securityLevel, sessionKey, restoreScreen);
    }

    @Override // android.app.SemWallpaperManager
    public void clearAll() throws IOException {
        Log.d(TAG, "clearAll");
        clear();
    }

    @Override // android.app.SemWallpaperManager
    public void setResourceAll(int resid) throws IOException {
        Log.d(TAG, "setResourceAll");
        Bitmap bitmap = generateBitmap(resid);
        if (bitmap != null) {
            setBitmap(bitmap);
            try {
                Settings.System.putInt(this.mContext.getContentResolver(), "android.wallpaper.settings_systemui_transparency", 2);
                return;
            } catch (SecurityException e) {
                Log.e(TAG, "Can't put value of SETTINGS_SYSTEMUI_TRANSPARENCY", e);
                return;
            }
        }
        Log.e(TAG, "theme bitmap is null");
    }

    private Bitmap generateBitmap(int resid) {
        Log.d(TAG, "generateBitmap");
        try {
            Resources resources = this.mContext.getResources();
            String resourceName = resources.getResourceName(resid);
            Log.d(TAG, "resourceName=" + resourceName);
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
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.e(TAG, "Package name " + themePackage + " not found");
                        }
                    } catch (Resources.NotFoundException e2) {
                        Log.e(TAG, "Resource not found: " + themeResourceId);
                    }
                }
            }
            String themePackage2 = TAG;
            Log.d(themePackage2, "themeResourceId=" + themeResourceId);
            if (themeResources == null || themeResourceId <= 0) {
                return null;
            }
            Bitmap bm = BitmapFactory.decodeResource(themeResources, themeResourceId);
            return bm;
        } catch (OutOfMemoryError e3) {
            Log.w(TAG, "Can't decode file", e3);
            return null;
        }
    }

    @Override // android.app.SemWallpaperManager
    public int getLockWallpaperType() {
        return semGetWallpaperType(2);
    }

    @Override // android.app.SemWallpaperManager
    public int semGetWallpaperType(int which) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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

    @Override // android.app.SemWallpaperManager
    public ComponentName semGetWallpaperComponent(int which, int userId) {
        checkExactlyOneWallpaperFlagSet(which);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semGetWallpaperComponent(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semIsPreloadedWallpaper(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semIsPreloadedWallpaper(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSystemAndLockPaired(int mode) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            resetMultipleWallpaperSettingIfNeeded();
            sGlobals.mService.setWallpaperComponentChecked(name, callingPackage, which, userId, extras);
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(which);
                return true;
            }
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWallpaperComponentExtras(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperComponentExtras(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public Bundle getWallpaperExtras(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperExtras(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public Bundle getWallpaperAssets(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperAssets(which, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getWallpaperAssetFile(int which, int userId, String assetFilePath) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperAssetFile(this.mContext.getOpPackageName(), which, userId, assetFilePath);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public int getWallpaperOrientation(int which, int userId) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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

    public void setVideoWallpaper(String videoFilePath, String themePackage, String fileName, int userId, int which, boolean updateSetting, boolean allowBackup, Bundle extras) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "setVideoWallpaper: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            sGlobals.mService.setVideoWallpaper(videoFilePath, themePackage, fileName, callingPackage, userId, which, allowBackup, extras);
            if (isNeedToClearBackupData()) {
                try {
                    semClearBackupWallpapers(which);
                } catch (RemoteException e) {
                    e = e;
                    e.printStackTrace();
                    throw e.rethrowFromSystemServer();
                }
            }
        } catch (RemoteException e2) {
            e = e2;
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
            Log.w(TAG, "saveWallpaperThumbnailFile() bitmap or fd is null, b = " + bitmap + ", t = " + targetFileFd + ", quality = " + quality);
            return;
        }
        FileOutputStream fos = null;
        try {
            try {
                try {
                    fos = new ParcelFileDescriptor.AutoCloseOutputStream(targetFileFd);
                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, fos);
                    fos.close();
                } catch (Exception e) {
                    Log.e(TAG, "saveWallpaperThumbnailFile() Exception", e);
                }
            } catch (IOException ie) {
                Log.w(TAG, "saveWallpaperThumbnailFile() IOException", ie);
            }
            IoUtils.closeQuietly(fos);
            Log.d(TAG, "saveWallpaperThumbnailFile() saved complete " + bitmap.getByteCount());
        } catch (Throwable th) {
            IoUtils.closeQuietly(fos);
            throw th;
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoFilePath(int which) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getVideoFileName(which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isVideoWallpaper() {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            boolean isVideoWallpaper = sGlobals.mService.isVideoWallpaper();
            Log.d(TAG, "isVideoWallpaper = " + isVideoWallpaper);
            return isVideoWallpaper;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean hasVideoWallpaper() {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
        Log.d(TAG, "getVideoWallpaperFrame, creating MediaMetadataRetriever");
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        Bitmap bitmap = null;
        long timeUs = 0;
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            try {
                try {
                    if (!TextUtils.isEmpty(filePath)) {
                        mediaMetadataRetriever.setDataSource(filePath);
                    } else {
                        if (fd == null) {
                            Log.w(TAG, "getVideoWallpaperFrame() file is invalid");
                            try {
                                mediaMetadataRetriever.release();
                                return null;
                            } catch (Exception e2) {
                                e2.printStackTrace();
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
                        bitmap = mediaMetadataRetriever.getFrameAtTime(timeUs, 2, param);
                    }
                    Log.d(TAG, "getVideoWallpaperFrame " + timeUs);
                    mediaMetadataRetriever.release();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    mediaMetadataRetriever.release();
                }
            } catch (NumberFormatException e4) {
                e4.printStackTrace();
                bitmap = mediaMetadataRetriever.getFrameAtTime(0L);
                mediaMetadataRetriever.release();
            }
            Log.d(TAG, "getVideoWallpaperFrame, done");
            return bitmap;
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            throw th;
        }
    }

    private void checkPermission(String[] permissions) {
        int uid = Process.myUid();
        int pid = Process.myPid();
        boolean hasPrivilege = false;
        for (String permission : permissions) {
            if (this.mContext.checkPermission(permission, pid, uid) == 0) {
                hasPrivilege = true;
            }
        }
        String callingPackage = this.mContext.getOpPackageName();
        if (!hasPrivilege) {
            ((StorageManager) this.mContext.getSystemService(StorageManager.class)).checkPermissionReadImages(true, pid, uid, callingPackage, null);
        }
    }

    private boolean canPeekWallpaper(int which) {
        boolean isSystemAndLockPaired = isSystemAndLockPaired(which);
        if (WhichChecker.isLock(which) && isSystemAndLockPaired) {
            Log.w(TAG, "canPeekWallpaper failed, which = " + which);
            return false;
        }
        return true;
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
            Log.e(TAG, "getThemeWallpaperBackground IOException");
        } catch (Exception e2) {
            Log.e(TAG, "getThemeWallpaperBackground Exception");
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
                        Log.e(TAG, "getAnimatedWallpaperBackground() : Theme pkg, AssetManager is null");
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
        Log.e(TAG, "getAnimatedWallpaperBackground() : Wallpaper pkg, AssetManager is null");
        return null;
    }

    private AssetFileDescriptor getVideoFDFromPackage(String pkgName, String fileName) {
        Resources otherResources;
        AssetManager otherAssetManager;
        Log.d(TAG, "getVideoFDFromPackage() pkgName = " + pkgName + " , fileName = " + fileName);
        Context otherContext = null;
        try {
            otherContext = this.mContext.createPackageContext(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (otherContext == null) {
            Log.e(TAG, "getVideoFDFromPackage() otherContext is null");
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
                Log.e(TAG, "getVideoFDFromPackage() assetManager is null");
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
        Log.i(TAG, "setMotionWallpaper: packageName = " + packageName);
        setMotionWallpaper(packageName, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String packageName, int which) {
        Log.i(TAG, "setMotionWallpaper: packageName = " + packageName + ", which = " + which);
        setMotionWallpaper(packageName, 2, false);
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String packageName, int which, boolean allowBackup) {
        Log.i(TAG, "setMotionWallpaper: packageName = " + packageName + ", which = " + which + ", allowBackup = " + allowBackup);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            Bitmap motionBackground = getThemeWallpaperBackground(packageName);
            ParcelFileDescriptor thumbnailFileFd = sGlobals.mService.getWallpaperThumbnailFileDescriptor(1, this.mContext.getUserId(), which, 1, 1006632960);
            saveWallpaperThumbnailFile(motionBackground, thumbnailFileFd);
            sGlobals.mService.setMotionWallpaper(packageName, callingPackage, which, allowBackup);
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
            Log.w(TAG, "WallpaperService not running");
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
        Log.i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + packageName);
        setAnimatedLockscreenWallpaper(packageName, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String packageName, int which) throws IOException {
        Log.i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + packageName + ", which = " + which);
        setAnimatedLockscreenWallpaper(packageName, which, false);
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String packageName, int which, boolean allowBackup) throws IOException {
        Log.i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + packageName + ", which = " + which + ", allowBackup = " + allowBackup);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            String callingPackage = this.mContext.getOpPackageName();
            Bitmap animatedBackground = getThemeWallpaperBackground(packageName);
            ParcelFileDescriptor thumbnailFileFd = sGlobals.mService.getWallpaperThumbnailFileDescriptor(4, this.mContext.getUserId(), which, 1, 1006632960);
            saveWallpaperThumbnailFile(animatedBackground, thumbnailFileFd);
            sGlobals.mService.setAnimatedWallpaper(packageName, callingPackage, which, allowBackup);
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.d(TAG, "isNeedToClearBackupData(), pkgName = " + callingPkg);
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
        Log.d(TAG, "needClear = " + needClear);
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
        Log.d(TAG, "semMakeBackupWallpaper: which = " + which + ", key = " + key);
        return semMakeBackupWallpaperWithExtras(which, key, null);
    }

    public int semMakeBackupWallpaperWithExtras(int which, int key, Bundle extras) {
        Log.d(TAG, "semMakeBackupWallpaper: which = " + which + ", key = " + key + ", extras = " + extras);
        if (Build.VERSION.SEM_PLATFORM_INT >= 140100 && (which <= 0 || WhichChecker.getType(which) == 0)) {
            throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
        }
        if (!checkWhichInvalidation(which)) {
            Log.e(TAG, "semMakeBackupWallpaper: Invalid which. which = " + which);
            return -1;
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "semMakeBackupWallpaper: WallpaperService not running");
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
        Log.d(TAG, "semClearBackupWallpapers: which = " + which);
        if (Build.VERSION.SEM_PLATFORM_INT >= 140100) {
            if (which <= 0 || WhichChecker.getType(which) == 0) {
                throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
            }
        } else if (which <= 0) {
            Log.e(TAG, "semClearBackupWallpapers: Invalid which.");
            return;
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "semClearBackupWallpapers: WallpaperService not running");
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
        Log.d(TAG, "clearBackupWallpaperGivenKey: key = " + key);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
        Log.d(TAG, "semRestoreBackupWallpaper: key = " + key);
        if (key <= 0) {
            Log.e(TAG, "invalid key");
            return false;
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "isSnapshotTestMode: WallpaperService not running");
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
            Log.w(TAG, "setSnapshotTestMode: WallpaperService not running");
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
            Log.w(TAG, "getSnapshotCount: WallpaperService not running");
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
            Log.w(TAG, "setSnapshotSource: WallpaperService not running");
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
            Log.w(TAG, "isValidSnapshot: WallpaperService not running");
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
            Log.w(TAG, "getSnapshotKeys: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getSnapshotKeys(source, which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isStockLiveWallpaper(int which) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "isStockLiveWallpaper: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isStockLiveWallpaper(which, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStockLiveWallpaperPackage(String packageName) {
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
            Log.w(TAG, "WallpaperService not running");
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
            Log.w(TAG, "WallpaperService not running");
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
        Log.i(TAG, "getAppliedScreen: " + str + ", " + i2);
        return i2;
    }

    private boolean isApplied(int which, String pkgName) {
        Uri uri = semGetUri(which);
        if (uri != null) {
            String uriString = uri.toString();
            Log.i(TAG, "isApplied: uri = " + uriString);
            if (!TextUtils.isEmpty(uriString) && uriString.contains(pkgName)) {
                return true;
            }
        }
        if ((which & 2) != 0) {
            String videoPkg = getVideoPackage(which);
            Log.i(TAG, "isApplied: videoPkg = " + videoPkg);
            if (!TextUtils.isEmpty(videoPkg) && videoPkg.contains(pkgName)) {
                return true;
            }
            String motionPkg = getMotionWallpaperPkgName(which);
            Log.i(TAG, "isApplied: motionPkg = " + motionPkg);
            if (!TextUtils.isEmpty(motionPkg) && motionPkg.contains(pkgName)) {
                return true;
            }
            String animatedPkg = getAnimatedPkgName(which);
            Log.i(TAG, "isApplied: animatedPkg = " + animatedPkg);
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
        Log.d(TAG, "getSeedColors: which = " + which + ", fromGoogle = " + fromGoogle);
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
        Log.d(TAG, "getSeedColors: bitmap = " + bitmap + ", fromGoogle = " + fromGoogle);
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
        if (canBackup && wallpaperType == 7 && !isStockLiveWallpaper(which)) {
            Log.d(TAG, "canBackup: which = " + which + ", external live wallpaper");
            canBackup = false;
        }
        Log.d(TAG, "canBackup: which = " + which + " canBackup = " + canBackup);
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

    @Override // android.app.SemWallpaperManager
    public boolean isWallpaperDataExists(int which) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "isWallpaperDataExist: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperDataExists(this.mContext.getUserId(), which);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
