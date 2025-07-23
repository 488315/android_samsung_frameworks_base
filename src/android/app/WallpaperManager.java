package android.app;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ILocalWallpaperColorConsumer;
import android.app.IWallpaperManagerCallback;
import android.app.WallpaperManager;
import android.app.compat.CompatChanges;
import android.app.wallpaper.WallpaperDescription;
import android.app.wallpaper.WallpaperInstance;
import android.content.APKContents;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
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
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadSystemException;
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
import java.util.Collections;
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
    public static final String COMMAND_KEYGUARD_APPEARING = "android.wallpaper.keyguardappearing";
    public static final String COMMAND_KEYGUARD_GOING_AWAY = "android.wallpaper.keyguardgoingaway";
    public static final String COMMAND_LOCKSCREEN_LAYOUT_CHANGED = "android.wallpaper.lockscreen_layout_changed";
    public static final String COMMAND_LOCKSCREEN_TAP = "android.wallpaper.lockscreen_tap";
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
    public static final String EXTRA_WHICH_WALLPAPER_CHANGED = "android.service.wallpaper.extra.WHICH_WALLPAPER_CHANGED";
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
    private static final int ONEUI_7_0 = 160000;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 0;
    public static final int ORIENTATION_SQUARE_LANDSCAPE = 3;
    public static final int ORIENTATION_SQUARE_PORTRAIT = 2;
    public static final int ORIENTATION_UNKNOWN = -1;
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
    public static final int SWP_TYPE_THEME_SINGLE = 3;
    private static final String SYSUI_DESKTOP_PKG_NAME = "com.samsung.desktopsystemui";
    private static String TAG = "WallpaperManager";
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

    public static int getRotatedOrientation(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return i != 3 ? -1 : 2;
        }
        return 3;
    }

    public boolean isLockscreenLiveWallpaperEnabled() {
        return true;
    }

    public void notifyCompletePurchase() {
    }

    public void restoreLockWallpaper() {
    }

    public boolean semSetWallpaperThumbnail(int i, Bitmap bitmap) {
        return false;
    }

    public void setOpenThemeWallpaper(Bitmap bitmap, Rect rect, boolean z) {
    }

    public void setOpenThemeWallpaper(boolean z) {
    }

    public static int getOrientation(Point point) {
        float f = point.x / point.y;
        if (f >= 1.3333334f) {
            return 1;
        }
        if (f > 1.0f) {
            return 3;
        }
        return f > 0.75f ? 2 : 0;
    }

    static class FastBitmapDrawable extends Drawable {
        private final Bitmap mBitmap;
        private int mDrawLeft;
        private int mDrawTop;
        private final int mHeight;
        private final Paint mPaint;
        private final int mWidth;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -1;
        }

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
        public void setBounds(int i, int i2, int i3, int i4) {
            this.mDrawLeft = i + (((i3 - i) - this.mWidth) / 2);
            this.mDrawTop = i2 + (((i4 - i2) - this.mHeight) / 2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setDither(boolean z) {
            throw new UnsupportedOperationException("Not supported with this drawable");
        }

        @Override // android.graphics.drawable.Drawable
        public void setFilterBitmap(boolean z) {
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

        CachedWallpaper(Bitmap bitmap, int i, int i2) {
            this.mCachedWallpaper = bitmap;
            this.mCachedWallpaperUserId = i;
            this.mWhich = i2;
        }

        boolean isValid(int i, int i2) {
            return i == this.mCachedWallpaperUserId && i2 == this.mWhich && !this.mCachedWallpaper.isRecycled();
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
            public void onColorsChanged(RectF rectF, WallpaperColors wallpaperColors) {
                for (LocalWallpaperColorConsumer localWallpaperColorConsumer : Globals.this.mLocalColorCallbackAreas.keySet()) {
                    ArraySet arraySet = (ArraySet) Globals.this.mLocalColorCallbackAreas.get(localWallpaperColorConsumer);
                    if (arraySet != null && arraySet.contains(rectF)) {
                        localWallpaperColorConsumer.onColorsChanged(rectF, wallpaperColors);
                    }
                }
            }
        };
        private final ArrayList<Pair<OnSemColorsChangedListener, Handler>> mSemColorListeners = new ArrayList<>();

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
        }

        Globals(IWallpaperManager iWallpaperManager, Looper looper) {
            this.mService = iWallpaperManager;
            this.mMainLooperHandler = new Handler(looper);
            forgetLoadedWallpaper();
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() {
            forgetLoadedWallpaper();
        }

        public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, Handler handler, int i, int i2) {
            synchronized (this) {
                if (!this.mColorCallbackRegistered) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, i, i2);
                        this.mColorCallbackRegistered = true;
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't register for color updates", e);
                    }
                }
                this.mColorListeners.add(new Pair<>(onColorsChangedListener, handler));
            }
        }

        public void addOnColorsChangedListener(LocalWallpaperColorConsumer localWallpaperColorConsumer, List<RectF> list, int i, int i2, int i3) {
            synchronized (this) {
                for (RectF rectF : list) {
                    ArraySet<RectF> arraySet = this.mLocalColorCallbackAreas.get(localWallpaperColorConsumer);
                    if (arraySet == null) {
                        arraySet = new ArraySet<>();
                        this.mLocalColorCallbackAreas.put(localWallpaperColorConsumer, arraySet);
                    }
                    arraySet.add(rectF);
                }
                try {
                    this.mService.addOnLocalColorsChangedListener(this.mLocalColorCallback, list, i, i2, i3);
                } catch (RemoteException e) {
                    Log.e(WallpaperManager.TAG, "Can't register for local color updates", e);
                }
            }
        }

        public void removeOnColorsChangedListener(LocalWallpaperColorConsumer localWallpaperColorConsumer, int i, int i2, int i3) {
            synchronized (this) {
                ArraySet<RectF> remove = this.mLocalColorCallbackAreas.remove(localWallpaperColorConsumer);
                if (remove != null && remove.size() != 0) {
                    for (LocalWallpaperColorConsumer localWallpaperColorConsumer2 : this.mLocalColorCallbackAreas.keySet()) {
                        ArraySet<RectF> arraySet = this.mLocalColorCallbackAreas.get(localWallpaperColorConsumer2);
                        if (arraySet != null && localWallpaperColorConsumer2 != localWallpaperColorConsumer) {
                            remove.removeAll((ArraySet<? extends RectF>) arraySet);
                        }
                    }
                    try {
                        if (remove.size() > 0) {
                            this.mService.removeOnLocalColorsChangedListener(this.mLocalColorCallback, new ArrayList(remove), i, i2, i3);
                        }
                    } catch (RemoteException e) {
                        Log.e(WallpaperManager.TAG, "Can't unregister for local color updates", e);
                    }
                }
            }
        }

        public void removeOnColorsChangedListener(final OnColorsChangedListener onColorsChangedListener, int i, int i2) {
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
                        this.mService.unregisterWallpaperColorsCallback(this, i, i2);
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't unregister color updates", e);
                    }
                }
            }
        }

        static /* synthetic */ boolean lambda$removeOnColorsChangedListener$0(OnColorsChangedListener onColorsChangedListener, Pair pair) {
            return pair.first == onColorsChangedListener;
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
            final Globals globals;
            Throwable th;
            synchronized (this) {
                try {
                    Iterator<Pair<OnColorsChangedListener, Handler>> it = this.mColorListeners.iterator();
                    while (it.hasNext()) {
                        final Pair<OnColorsChangedListener, Handler> next = it.next();
                        Handler handler = next.second;
                        if (next.second == null) {
                            try {
                                handler = this.mMainLooperHandler;
                            } catch (Throwable th2) {
                                th = th2;
                                globals = this;
                                throw th;
                            }
                        }
                        globals = this;
                        final WallpaperColors wallpaperColors2 = wallpaperColors;
                        final int i3 = i;
                        final int i4 = i2;
                        try {
                            handler.post(new Runnable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    WallpaperManager.Globals.this.lambda$onWallpaperColorsChanged$1(next, wallpaperColors2, i3, i4);
                                }
                            });
                            this = globals;
                            wallpaperColors = wallpaperColors2;
                            i = i3;
                            i2 = i4;
                        } catch (Throwable th3) {
                            th = th3;
                            th = th;
                            throw th;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    globals = this;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onWallpaperColorsChanged$1(Pair pair, WallpaperColors wallpaperColors, int i, int i2) {
            boolean contains;
            synchronized (WallpaperManager.sGlobals) {
                contains = this.mColorListeners.contains(pair);
            }
            if (contains) {
                ((OnColorsChangedListener) pair.first).onColorsChanged(wallpaperColors, i, i2);
            }
        }

        WallpaperColors getWallpaperColors(int i, int i2, int i3) {
            WallpaperManager.checkExactlyOneWallpaperFlagSet(i);
            try {
                return this.mService.getWallpaperColors(i, i2, i3);
            } catch (RemoteException unused) {
                return null;
            }
        }

        SemWallpaperColors semGetWallpaperColors(int i) {
            Log.d(WallpaperManager.TAG, "semGetWallpaperColors: which = " + i);
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return iWallpaperManager.semGetWallpaperColors(i);
            } catch (RemoteException unused) {
                return null;
            }
        }

        SemWallpaperColors semGetPrimaryWallpaperColors(int i) {
            Log.d(WallpaperManager.TAG, "semGetPrimaryWallpaperColors: which = " + i);
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return iWallpaperManager.semGetPrimaryWallpaperColors(i);
            } catch (RemoteException unused) {
                return null;
            }
        }

        void semSetSmartCropRect(int i, Rect rect, Rect rect2) {
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
            } else {
                try {
                    iWallpaperManager.semSetSmartCropRect(i, rect, rect2);
                } catch (RemoteException unused) {
                }
            }
        }

        Rect semGetSmartCropRect(int i) {
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                return iWallpaperManager.semGetSmartCropRect(i);
            } catch (RemoteException unused) {
                return null;
            }
        }

        public Bitmap peekWallpaperBitmap(Context context, boolean z, int i, ColorManagementProxy colorManagementProxy) {
            return peekWallpaperBitmap(context, z, i, context.getUserId(), false, colorManagementProxy);
        }

        public Bitmap peekWallpaperBitmap(Context context, boolean z, int i, int i2, boolean z2, ColorManagementProxy colorManagementProxy) {
            return peekWallpaperBitmap(context, z, i, i2, z2, colorManagementProxy, true);
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x0159 A[Catch: all -> 0x018c, TRY_ENTER, TryCatch #8 {, blocks: (B:8:0x0069, B:12:0x0075, B:14:0x0079, B:16:0x007f, B:18:0x0087, B:19:0x00b6, B:22:0x00b8, B:24:0x00be, B:34:0x00db, B:35:0x00de, B:44:0x00f2, B:46:0x0159, B:47:0x0160, B:49:0x0162, B:51:0x0166, B:53:0x016c, B:54:0x0170, B:56:0x0172, B:73:0x0127, B:74:0x012a, B:87:0x0188, B:88:0x018b, B:26:0x00c0, B:28:0x00c9, B:30:0x00cd, B:32:0x00d3, B:38:0x00e8, B:40:0x00ec, B:42:0x00f0, B:68:0x0107, B:70:0x0110, B:72:0x0119, B:76:0x012c, B:78:0x0136, B:79:0x0140, B:66:0x0143, B:83:0x0100, B:84:0x0104), top: B:7:0x0069, inners: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0162 A[Catch: all -> 0x018c, TryCatch #8 {, blocks: (B:8:0x0069, B:12:0x0075, B:14:0x0079, B:16:0x007f, B:18:0x0087, B:19:0x00b6, B:22:0x00b8, B:24:0x00be, B:34:0x00db, B:35:0x00de, B:44:0x00f2, B:46:0x0159, B:47:0x0160, B:49:0x0162, B:51:0x0166, B:53:0x016c, B:54:0x0170, B:56:0x0172, B:73:0x0127, B:74:0x012a, B:87:0x0188, B:88:0x018b, B:26:0x00c0, B:28:0x00c9, B:30:0x00cd, B:32:0x00d3, B:38:0x00e8, B:40:0x00ec, B:42:0x00f0, B:68:0x0107, B:70:0x0110, B:72:0x0119, B:76:0x012c, B:78:0x0136, B:79:0x0140, B:66:0x0143, B:83:0x0100, B:84:0x0104), top: B:7:0x0069, inners: #6 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.graphics.Bitmap peekWallpaperBitmap(android.content.Context r12, boolean r13, int r14, int r15, boolean r16, android.app.WallpaperManager.ColorManagementProxy r17, boolean r18) {
            /*
                Method dump skipped, instructions count: 400
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.Globals.peekWallpaperBitmap(android.content.Context, boolean, int, int, boolean, android.app.WallpaperManager$ColorManagementProxy, boolean):android.graphics.Bitmap");
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0084  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.graphics.Rect peekWallpaperDimensions(android.content.Context r14, boolean r15, int r16, int r17) {
            /*
                r13 = this;
                android.app.IWallpaperManager r0 = r13.mService
                r10 = 0
                if (r0 != 0) goto Lf
                java.lang.String r13 = android.app.WallpaperManager.m635$$Nest$sfgetTAG()
                java.lang.String r14 = "WallpaperService not running"
                android.util.Log.w(r13, r14)
                return r10
            Lf:
                java.lang.String r1 = r14.getOpPackageName()     // Catch: android.os.RemoteException -> Lc6
                boolean r0 = r0.isWallpaperSupported(r1)     // Catch: android.os.RemoteException -> Lc6
                if (r0 != 0) goto L1f
                android.graphics.Rect r13 = new android.graphics.Rect     // Catch: android.os.RemoteException -> Lc6
                r13.<init>()     // Catch: android.os.RemoteException -> Lc6
                return r13
            L1f:
                monitor-enter(r13)
                android.os.Bundle r5 = new android.os.Bundle     // Catch: java.lang.Throwable -> Lc2
                r5.<init>()     // Catch: java.lang.Throwable -> Lc2
                r11 = 1
                r12 = 0
                android.app.IWallpaperManager r0 = r13.mService     // Catch: java.io.IOException -> L6f android.os.RemoteException -> L73 java.lang.Throwable -> Lc2
                java.lang.String r1 = r14.getOpPackageName()     // Catch: java.io.IOException -> L6f android.os.RemoteException -> L73 java.lang.Throwable -> Lc2
                java.lang.String r2 = r14.getAttributionTag()     // Catch: java.io.IOException -> L6f android.os.RemoteException -> L73 java.lang.Throwable -> Lc2
                r8 = 0
                r9 = -1
                r7 = 1
                r3 = r13
                r4 = r16
                r6 = r17
                android.os.ParcelFileDescriptor r1 = r0.getWallpaperWithFeature(r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: android.os.RemoteException -> L6d java.io.IOException -> L71 java.lang.Throwable -> Lc2
                if (r1 == 0) goto L64
                android.graphics.BitmapFactory$Options r0 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> L57
                r0.<init>()     // Catch: java.lang.Throwable -> L57
                r0.inJustDecodeBounds = r11     // Catch: java.lang.Throwable -> L57
                java.io.FileDescriptor r2 = r1.getFileDescriptor()     // Catch: java.lang.Throwable -> L57
                android.graphics.BitmapFactory.decodeFileDescriptor(r2, r10, r0)     // Catch: java.lang.Throwable -> L57
                android.graphics.Rect r2 = new android.graphics.Rect     // Catch: java.lang.Throwable -> L57
                int r5 = r0.outWidth     // Catch: java.lang.Throwable -> L57
                int r0 = r0.outHeight     // Catch: java.lang.Throwable -> L57
                r2.<init>(r12, r12, r5, r0)     // Catch: java.lang.Throwable -> L57
                goto L65
            L57:
                r0 = move-exception
                r2 = r0
                if (r1 == 0) goto L63
                r1.close()     // Catch: java.lang.Throwable -> L5f
                goto L63
            L5f:
                r0 = move-exception
                r2.addSuppressed(r0)     // Catch: android.os.RemoteException -> L6d java.io.IOException -> L71 java.lang.Throwable -> Lc2
            L63:
                throw r2     // Catch: android.os.RemoteException -> L6d java.io.IOException -> L71 java.lang.Throwable -> Lc2
            L64:
                r2 = r10
            L65:
                if (r1 == 0) goto L81
                r1.close()     // Catch: android.os.RemoteException -> L6b java.io.IOException -> L81 java.lang.Throwable -> Lc2
                goto L81
            L6b:
                r0 = move-exception
                goto L77
            L6d:
                r0 = move-exception
                goto L76
            L6f:
                r4 = r16
            L71:
                r2 = r10
                goto L81
            L73:
                r0 = move-exception
                r4 = r16
            L76:
                r2 = r10
            L77:
                java.lang.String r1 = android.app.WallpaperManager.m635$$Nest$sfgetTAG()     // Catch: java.lang.Throwable -> Lc2
                java.lang.String r5 = "peek wallpaper dimensions failed"
                android.util.Log.w(r1, r5, r0)     // Catch: java.lang.Throwable -> Lc2
            L81:
                monitor-exit(r13)     // Catch: java.lang.Throwable -> Lc2
                if (r2 == 0) goto L90
                int r0 = r2.width()
                if (r0 == 0) goto L90
                int r0 = r2.height()
                if (r0 != 0) goto Lc1
            L90:
                if (r15 != 0) goto L9e
                boolean r15 = com.samsung.android.wallpaper.utils.WhichChecker.isLock(r4)
                if (r15 == 0) goto Lc1
                boolean r13 = r13.isStaticWallpaper(r4)
                if (r13 == 0) goto Lc1
            L9e:
                java.io.InputStream r13 = android.app.WallpaperManager.openDefaultWallpaper(r14, r4)
                if (r13 == 0) goto Lc1
                android.graphics.BitmapFactory$Options r14 = new android.graphics.BitmapFactory$Options     // Catch: java.lang.Throwable -> Lbb
                r14.<init>()     // Catch: java.lang.Throwable -> Lbb
                r14.inJustDecodeBounds = r11     // Catch: java.lang.Throwable -> Lbb
                android.graphics.BitmapFactory.decodeStream(r13, r10, r14)     // Catch: java.lang.Throwable -> Lbb
                android.graphics.Rect r2 = new android.graphics.Rect     // Catch: java.lang.Throwable -> Lbb
                int r15 = r14.outWidth     // Catch: java.lang.Throwable -> Lbb
                int r14 = r14.outHeight     // Catch: java.lang.Throwable -> Lbb
                r2.<init>(r12, r12, r15, r14)     // Catch: java.lang.Throwable -> Lbb
                libcore.io.IoUtils.closeQuietly(r13)
                goto Lc1
            Lbb:
                r0 = move-exception
                r14 = r0
                libcore.io.IoUtils.closeQuietly(r13)
                throw r14
            Lc1:
                return r2
            Lc2:
                r0 = move-exception
                r14 = r0
                monitor-exit(r13)     // Catch: java.lang.Throwable -> Lc2
                throw r14
            Lc6:
                r0 = move-exception
                r13 = r0
                java.lang.RuntimeException r13 = r13.rethrowFromSystemServer()
                throw r13
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.Globals.peekWallpaperDimensions(android.content.Context, boolean, int, int):android.graphics.Rect");
        }

        void forgetLoadedWallpaper() {
            synchronized (this) {
                this.mCachedWallpaper = null;
                this.mDefaultWallpaper = null;
                this.mSubDefaultWallpaper = null;
                if (Rune.SUPPORT_DESKTOP_MODE) {
                    this.mIsCachedWallpaperForDeX = false;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Bitmap getCurrentWallpaperLocked(Context context, int i, int i2, final boolean z, final ColorManagementProxy colorManagementProxy) {
            ImageDecoder.Source createSource;
            if (this.mService == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            Log.d(WallpaperManager.TAG, "getCurrentWallpaperLocked userId=" + i2 + " by : " + context.getOpPackageName());
            try {
                Bundle bundle = new Bundle();
                Trace.beginSection("WPMS.getWallpaperWithFeature_" + i);
                ParcelFileDescriptor wallpaperWithFeature = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, i, bundle, i2, true, false, -1);
                Trace.endSection();
                if (wallpaperWithFeature == null) {
                    return null;
                }
                try {
                    ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(wallpaperWithFeature);
                    try {
                        if (Flags.enableConnectedDisplaysWallpaper()) {
                            createSource = ImageDecoder.createSource(context.getResources(), autoCloseInputStream, 0);
                        } else {
                            createSource = ImageDecoder.createSource(context.getResources(), autoCloseInputStream);
                        }
                        Bitmap decodeBitmap = ImageDecoder.decodeBitmap(createSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda0
                            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                            public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                WallpaperManager.Globals.lambda$getCurrentWallpaperLocked$2(z, colorManagementProxy, imageDecoder, imageInfo, source);
                            }
                        });
                        autoCloseInputStream.close();
                        return decodeBitmap;
                    } finally {
                    }
                } catch (IOException | OutOfMemoryError e) {
                    Log.w(WallpaperManager.TAG, "Can't decode file", e);
                    return null;
                }
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }

        static /* synthetic */ void lambda$getCurrentWallpaperLocked$2(boolean z, ColorManagementProxy colorManagementProxy, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
            imageDecoder.setMutableRequired(!z);
            if (colorManagementProxy != null) {
                colorManagementProxy.doColorManagement(imageDecoder, imageInfo);
            }
        }

        private Bitmap getDefaultWallpaper(Context context, int i) {
            Trace.beginSection("WPMS.getDefaultWallpaper_" + i);
            Bitmap defaultWallpaper = getDefaultWallpaper(i);
            if (defaultWallpaper == null || defaultWallpaper.isRecycled()) {
                Trace.beginSection("WPMS.openDefaultWallpaper");
                defaultWallpaper = null;
                try {
                    InputStream openDefaultWallpaper = WallpaperManager.openDefaultWallpaper(context, i);
                    try {
                        Trace.endSection();
                        if (openDefaultWallpaper != null) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            Trace.beginSection("WPMS.decodeStream");
                            defaultWallpaper = checkDeviceDensity(context, BitmapFactory.decodeStream(openDefaultWallpaper, null, options), i);
                            Trace.endSection();
                        }
                        if (openDefaultWallpaper != null) {
                            openDefaultWallpaper.close();
                        }
                    } catch (Throwable th) {
                        if (openDefaultWallpaper != null) {
                            try {
                                openDefaultWallpaper.close();
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
                setDefaultWallpaper(i, defaultWallpaper);
            }
            Trace.endSection();
            return defaultWallpaper;
        }

        private boolean isStaticWallpaper(int i) {
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            try {
                return iWallpaperManager.isStaticWallpaper(i);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        private Bitmap getDefaultWallpaper(int i) {
            if (WhichChecker.isSubDisplay(i)) {
                return this.mSubDefaultWallpaper;
            }
            return this.mDefaultWallpaper;
        }

        private void setDefaultWallpaper(int i, Bitmap bitmap) {
            if (WhichChecker.isSubDisplay(i)) {
                this.mSubDefaultWallpaper = bitmap;
            }
            this.mDefaultWallpaper = bitmap;
        }

        public Bitmap checkDeviceDensity(Context context, Bitmap bitmap) {
            return checkDeviceDensity(context, bitmap, 0);
        }

        private Bitmap checkDeviceDensity(Context context, Bitmap bitmap, int i) {
            if (bitmap == null || bitmap.isRecycled()) {
                return null;
            }
            Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            if (i != 0) {
                boolean z = Rune.SUPPORT_SUB_DISPLAY_MODE && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE;
                boolean isDex = WhichChecker.isDex(i);
                if (z || isDex) {
                    DisplayManager displayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
                    int displayId = WallpaperManager.getDisplayId(context, i);
                    if (displayId == -1) {
                        Log.e(WallpaperManager.TAG, "checkDeviceDensity: failed to determine display id. which=" + i);
                        return bitmap;
                    }
                    Log.d(WallpaperManager.TAG, "checkDeviceDensity: getDisplayId=" + displayId);
                    defaultDisplay = displayManager.getDisplay(displayId);
                    if (defaultDisplay == null) {
                        Log.e(WallpaperManager.TAG, "checkDeviceDensity: failed to get display. which=" + i);
                        return bitmap;
                    }
                }
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(displayMetrics);
            bitmap.setDensity(displayMetrics.noncompatDensityDpi);
            DisplayInfo displayInfo = new DisplayInfo();
            defaultDisplay.getDisplayInfo(displayInfo);
            int i2 = displayInfo.logicalHeight;
            int i3 = displayInfo.logicalWidth;
            int i4 = displayInfo.rotation;
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            Log.d(WallpaperManager.TAG, "checkDeviceDensity: deviceRotation=" + i4 + " deviceHeight=" + i2 + " deviceWidth=" + i3 + " bitmapHeight=" + height + " bitmapWidth=" + width);
            PackageManager packageManager = context.getPackageManager();
            if ((packageManager == null || !packageManager.hasSystemFeature(PackageManager.SEM_FEATURE_DEVICE_CATEGORY_TABLET)) && (i4 == 1 || i4 == 3)) {
                i2 = displayInfo.logicalWidth;
                i3 = displayInfo.logicalHeight;
            }
            if (i3 == 0 || i2 == 0 || i3 >= width || i2 >= height) {
                return bitmap;
            }
            float max = Math.max(i3 / width, i2 / height);
            Bitmap resizeBitmap = resizeBitmap(bitmap, max);
            Log.d(WallpaperManager.TAG, "checkDeviceDensity: resize scale down.:" + max);
            return resizeBitmap;
        }

        private Bitmap resizeBitmap(Bitmap bitmap, float f) {
            return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * f), (int) (bitmap.getHeight() * f), true);
        }

        public void addOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener, Handler handler, int i, int i2) {
            synchronized (this) {
                if (!this.mColorCallbackRegistered) {
                    try {
                        this.mService.registerWallpaperColorsCallback(this, i, i2);
                        this.mColorCallbackRegistered = true;
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't register for color updates", e);
                    }
                }
                this.mSemColorListeners.add(new Pair<>(onSemColorsChangedListener, handler));
            }
        }

        public void removeOnSemColorsChangedListener(final OnSemColorsChangedListener onSemColorsChangedListener, int i, int i2) {
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
                        this.mService.unregisterWallpaperColorsCallback(this, i, i2);
                    } catch (RemoteException e) {
                        Log.w(WallpaperManager.TAG, "Can't unregister color updates", e);
                    }
                }
            }
        }

        static /* synthetic */ boolean lambda$removeOnSemColorsChangedListener$3(OnSemColorsChangedListener onSemColorsChangedListener, Pair pair) {
            return pair.first == onSemColorsChangedListener;
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsChanged(final SemWallpaperColors semWallpaperColors, final int i, int i2) {
            Log.d(WallpaperManager.TAG, "onSemWallpaperColorsChanged " + semWallpaperColors + ", which=" + i);
            synchronized (this) {
                Iterator<Pair<OnSemColorsChangedListener, Handler>> it = this.mSemColorListeners.iterator();
                while (it.hasNext()) {
                    final Pair<OnSemColorsChangedListener, Handler> next = it.next();
                    Handler handler = next.second;
                    if (next.second == null) {
                        handler = this.mMainLooperHandler;
                    }
                    handler.post(new Runnable() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            WallpaperManager.Globals.this.lambda$onSemWallpaperColorsChanged$4(next, semWallpaperColors, i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemWallpaperColorsChanged$4(Pair pair, SemWallpaperColors semWallpaperColors, int i) {
            boolean contains;
            synchronized (this) {
                contains = this.mSemColorListeners.contains(pair);
            }
            if (contains) {
                ((OnSemColorsChangedListener) pair.first).onColorsChanged(semWallpaperColors, i);
            }
        }
    }

    static void initGlobals(IWallpaperManager iWallpaperManager, Looper looper) {
        synchronized (sSync) {
            if (sGlobals == null) {
                sGlobals = new Globals(iWallpaperManager, looper);
            }
        }
    }

    WallpaperManager(IWallpaperManager iWallpaperManager, Context context, Handler handler) {
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mContext = context;
        if (iWallpaperManager != null) {
            initGlobals(iWallpaperManager, context.getMainLooper());
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

    public static boolean isMultiCropEnabled() {
        if (sIsMultiCropEnabled == null) {
            sIsMultiCropEnabled = Boolean.valueOf(com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.multiCrop());
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
    public Drawable getDrawable(int i) {
        Bitmap peekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, !WhichChecker.isLock(i), i, getColorManagementProxy());
        if (peekWallpaperBitmap == null) {
            return null;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), peekWallpaperBitmap);
        bitmapDrawable.setDither(false);
        return bitmapDrawable;
    }

    @Override // android.app.SemWallpaperManager
    public Drawable semGetDrawable(int i) {
        return semGetDrawable(i, 1);
    }

    public Drawable semGetDrawable(int i, int i2) {
        boolean isDesktopModeEnabled;
        Drawable drawableFromBitmap;
        AssetFileDescriptor assetFileDescriptor;
        ParcelFileDescriptor semGetThumbnailFileDescriptor;
        if (!WhichChecker.isSystem(i) && !WhichChecker.isLock(i)) {
            if (WhichChecker.isDex(i)) {
                i = 9;
            } else {
                if (i == 0) {
                    InputStream openDefaultWallpaper = openDefaultWallpaper(this.mContext, 2, false);
                    if (openDefaultWallpaper == null) {
                        openDefaultWallpaper = openDefaultWallpaper(this.mContext, 1, false);
                    }
                    return getDrawableFromStream(openDefaultWallpaper);
                }
                i = 1;
            }
        }
        if (WhichChecker.isModeAbsent(i)) {
            int modeEnsuredWhich = getModeEnsuredWhich(i);
            Log.d(TAG, "semGetDrawable: mode is absent. which=" + i + ", adjustedWhich=" + modeEnsuredWhich);
            i = modeEnsuredWhich;
        }
        int semGetWallpaperType = semGetWallpaperType(i);
        Log.i(TAG, "semGetDrawable: which = " + i + ", wallpaperType = " + semGetWallpaperType + ", orientation=" + i2 + ", caller=" + this.mContext.getPackageName());
        try {
            semGetThumbnailFileDescriptor = semGetThumbnailFileDescriptor(i, this.mContext.getUserId(), i2 == 1 ? 0 : 1);
        } catch (IOException e) {
            Log.e(TAG, "semGetDrawable: e=" + e, e);
        }
        if (semGetThumbnailFileDescriptor != null) {
            try {
                Drawable drawableFromBitmap2 = getDrawableFromBitmap(BitmapFactory.decodeFileDescriptor(semGetThumbnailFileDescriptor.getFileDescriptor()));
                if (semGetThumbnailFileDescriptor != null) {
                    semGetThumbnailFileDescriptor.close();
                }
                return drawableFromBitmap2;
            } finally {
            }
        } else {
            if (semGetThumbnailFileDescriptor != null) {
                semGetThumbnailFileDescriptor.close();
            }
            Log.w(TAG, "semGetDrawable: Couldn't get thumbnail. Keep going..");
            if (Rune.SUPPORT_DESKTOP_MODE) {
                try {
                    isDesktopModeEnabled = sGlobals.mService.isDesktopModeEnabled(i);
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            } else {
                isDesktopModeEnabled = false;
            }
            boolean z = sWallpaperResourcesInfo.isDefaultVideo(i) && !isDesktopModeEnabled && isVideoWallpaper();
            int mode = (WhichChecker.isLock(i) && isSystemAndLockPaired(i)) ? WhichChecker.getMode(i) | 1 : i;
            if (z) {
                String videoFileName = getVideoFileName(i);
                try {
                    assetFileDescriptor = getVideoFDFromPackage(WALLPAPER_PACKAGE, videoFileName);
                } catch (Exception e3) {
                    e3.printStackTrace();
                    assetFileDescriptor = null;
                }
                Bitmap videoWallpaperFrame = getVideoWallpaperFrame(assetFileDescriptor, null, videoFileName);
                if (videoWallpaperFrame != null) {
                    return new BitmapDrawable(this.mContext.getResources(), videoWallpaperFrame);
                }
            } else if (semGetWallpaperType == 7) {
                WallpaperInfo wallpaperInfo = getWallpaperInfo(mode, this.mContext.getUserId());
                if (wallpaperInfo != null) {
                    return wallpaperInfo.loadThumbnail(this.mContext.getPackageManager());
                }
                return null;
            }
            ColorManagementProxy colorManagementProxy = getColorManagementProxy();
            Globals globals = sGlobals;
            Context context = this.mContext;
            Bitmap currentWallpaperLocked = globals.getCurrentWallpaperLocked(context, mode, context.getUserId(), false, colorManagementProxy);
            return (currentWallpaperLocked == null || currentWallpaperLocked.isRecycled() || (drawableFromBitmap = getDrawableFromBitmap(currentWallpaperLocked)) == null) ? getDrawableFromStream(openDefaultWallpaper(this.mContext, i, false)) : drawableFromBitmap;
        }
    }

    private Drawable getDrawableFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Log.d(TAG, "getDrawableFromBitmap : bitmap is null");
            return null;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmap);
        bitmapDrawable.setDither(false);
        return bitmapDrawable;
    }

    private Drawable getDrawableFromStream(InputStream inputStream) {
        try {
            if (inputStream == null) {
                Log.d(TAG, "getDrawableFromStream : input stream is null");
                return null;
            }
            try {
                Bitmap checkDeviceDensity = sGlobals.checkDeviceDensity(this.mContext, BitmapFactory.decodeStream(inputStream, null, new BitmapFactory.Options()));
                if (checkDeviceDensity != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), checkDeviceDensity);
                    bitmapDrawable.setDither(false);
                    return bitmapDrawable;
                }
            } catch (OutOfMemoryError e) {
                Log.w(TAG, "Can't decode stream", e);
            }
            return null;
        } finally {
            IoUtils.closeQuietly(inputStream);
        }
    }

    public ParcelFileDescriptor semGetScreenshotFileDescriptor(int i, int i2, Bundle bundle) {
        checkExactlyOneWallpaperFlagSet(i);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getScreenshotFileDescriptor(i, i2, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public Region semGetWallpaperBackgroundRegion(int i, int i2) {
        int displayId = getDisplayId(this.mContext, i);
        if (displayId == -1) {
            Log.e(TAG, "semGetWallpaperBackgroundRegion: failed to determine display id. which=" + i);
            return null;
        }
        DisplayManager displayManager = (DisplayManager) this.mContext.getSystemService(DisplayManager.class);
        if (displayManager == null) {
            Log.e(TAG, "semGetWallpaperBackgroundRegion: failed to get display manager. which=" + i);
            return null;
        }
        Display display = displayManager.getDisplay(displayId);
        if (display == null) {
            Log.e(TAG, "semGetWallpaperBackgroundRegion: failed to get display. which=" + i);
            return null;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        return semGetWallpaperBackgroundRegion(i, i2, displayInfo.rotation);
    }

    public Region semGetWallpaperBackgroundRegion(int i, int i2, int i3) {
        checkExactlyOneWallpaperFlagSet(i);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperBackgroundRegion(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getModeEnsuredWhich(int i) {
        if (WhichChecker.isModeAbsent(i)) {
            return (isSubDisplay() ? 16 : 4) | WhichChecker.getType(i);
        }
        return i;
    }

    public Drawable getBuiltInDrawable() {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, 1);
    }

    public Drawable getBuiltInDrawable(int i) {
        return getBuiltInDrawable(0, 0, false, 0.0f, 0.0f, i);
    }

    public Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2) {
        return getBuiltInDrawable(i, i2, z, f, f2, 1);
    }

    public Drawable getBuiltInDrawable(int i, int i2, boolean z, float f, float f2, int i3) {
        int i4;
        int i5;
        int i6;
        RectF rectF;
        BitmapRegionDecoder bitmapRegionDecoder;
        Bitmap bitmap;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(i3) == 0 && isSubDisplay()) {
            Log.d(TAG, "getBuiltInDrawable, add flag");
            i4 = i3 | 16;
        } else {
            i4 = i3;
        }
        Log.d(TAG, "getBuiltInDrawable: which = " + i4);
        checkExactlyOneWallpaperFlagSet(i4);
        Resources resources = this.mContext.getResources();
        float max = Math.max(0.0f, Math.min(1.0f, f));
        float max2 = Math.max(0.0f, Math.min(1.0f, f2));
        InputStream openDefaultWallpaper = openDefaultWallpaper(this.mContext, i4);
        if (openDefaultWallpaper == null) {
            Log.w(TAG, "default wallpaper stream " + i4 + " is null");
            return null;
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(openDefaultWallpaper);
        if (i <= 0 || i2 <= 0) {
            return new BitmapDrawable(resources, BitmapFactory.decodeStream(bufferedInputStream, null, null));
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(bufferedInputStream, null, options);
        if (options.outWidth != 0 && options.outHeight != 0) {
            int i7 = options.outWidth;
            int i8 = options.outHeight;
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(openDefaultWallpaper(this.mContext, i4));
            int min = Math.min(i7, i);
            int min2 = Math.min(i8, i2);
            if (z) {
                rectF = getMaxCropRect(i7, i8, min, min2, max, max2);
                i5 = min;
                i6 = min2;
            } else {
                i5 = min;
                i6 = min2;
                float f3 = (i7 - i5) * max;
                float f4 = (i8 - i6) * max2;
                rectF = new RectF(f3, f4, i5 + f3, i6 + f4);
            }
            Rect rect = new Rect();
            rectF.roundOut(rect);
            if (rect.width() <= 0 || rect.height() <= 0) {
                Log.w(TAG, "crop has bad values for full size image");
                return null;
            }
            int min3 = Math.min(rect.width() / i5, rect.height() / i6);
            try {
                bitmapRegionDecoder = BitmapRegionDecoder.newInstance((InputStream) bufferedInputStream2, true);
            } catch (IOException unused) {
                Log.w(TAG, "cannot open region decoder for default wallpaper");
                bitmapRegionDecoder = null;
            }
            if (bitmapRegionDecoder != null) {
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                if (min3 > 1) {
                    options2.inSampleSize = min3;
                }
                bitmap = bitmapRegionDecoder.decodeRegion(rect, options2);
                bitmapRegionDecoder.recycle();
            } else {
                bitmap = null;
            }
            if (bitmap == null) {
                BufferedInputStream bufferedInputStream3 = new BufferedInputStream(openDefaultWallpaper(this.mContext, i4));
                BitmapFactory.Options options3 = new BitmapFactory.Options();
                if (min3 > 1) {
                    options3.inSampleSize = min3;
                }
                Bitmap decodeStream = BitmapFactory.decodeStream(bufferedInputStream3, null, options3);
                if (decodeStream != null) {
                    bitmap = Bitmap.createBitmap(decodeStream, rect.left, rect.top, rect.width(), rect.height());
                }
            }
            if (bitmap == null) {
                Log.w(TAG, "cannot decode default wallpaper");
                return null;
            }
            if (i5 > 0 && i6 > 0 && (bitmap.getWidth() != i5 || bitmap.getHeight() != i6)) {
                Matrix matrix = new Matrix();
                RectF rectF2 = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
                RectF rectF3 = new RectF(0.0f, 0.0f, i5, i6);
                matrix.setRectToRect(rectF2, rectF3, Matrix.ScaleToFit.FILL);
                Bitmap createBitmap = Bitmap.createBitmap((int) rectF3.width(), (int) rectF3.height(), Bitmap.Config.ARGB_8888);
                if (createBitmap != null) {
                    Canvas canvas = new Canvas(createBitmap);
                    Paint paint = new Paint();
                    paint.setFilterBitmap(true);
                    canvas.drawBitmap(bitmap, matrix, paint);
                    bitmap = createBitmap;
                }
            }
            return new BitmapDrawable(resources, bitmap);
        }
        Log.e(TAG, "default wallpaper dimensions are 0");
        return null;
    }

    private static RectF getMaxCropRect(int i, int i2, int i3, int i4, float f, float f2) {
        RectF rectF = new RectF();
        float f3 = i;
        float f4 = i2;
        float f5 = i3;
        float f6 = i4;
        if (f3 / f4 > f5 / f6) {
            rectF.top = 0.0f;
            rectF.bottom = f4;
            float f7 = f5 * (f4 / f6);
            rectF.left = (f3 - f7) * f;
            rectF.right = rectF.left + f7;
            return rectF;
        }
        rectF.left = 0.0f;
        rectF.right = f3;
        float f8 = f6 * (f3 / f5);
        rectF.top = (f4 - f8) * f2;
        rectF.bottom = rectF.top + f8;
        return rectF;
    }

    public Drawable peekDrawable() {
        return peekDrawable(1);
    }

    public Drawable peekDrawable(int i) {
        if (!canPeekWallpaper(i)) {
            checkPermission(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_WALLPAPER_INTERNAL});
            return null;
        }
        return getDrawable(i);
    }

    public Drawable getFastDrawable() {
        return getFastDrawable(1);
    }

    public Drawable getFastDrawable(int i) {
        ColorManagementProxy colorManagementProxy = getColorManagementProxy();
        boolean z = !WhichChecker.isLock(i);
        if (!canPeekWallpaper(i)) {
            checkPermission(new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE, Manifest.permission.READ_WALLPAPER_INTERNAL});
            return null;
        }
        Bitmap peekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, z, i, colorManagementProxy);
        if (peekWallpaperBitmap != null) {
            return new FastBitmapDrawable(peekWallpaperBitmap);
        }
        return null;
    }

    public Drawable peekFastDrawable() {
        return peekFastDrawable(1);
    }

    public Drawable peekFastDrawable(int i) {
        return getFastDrawable(i);
    }

    public boolean wallpaperSupportsWcg(int i) {
        ColorManagementProxy colorManagementProxy;
        Bitmap peekWallpaperBitmap;
        return (!shouldEnableWideColorGamut() || (peekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, false, i, (colorManagementProxy = getColorManagementProxy()))) == null || peekWallpaperBitmap.getColorSpace() == null || peekWallpaperBitmap.getColorSpace() == ColorSpace.get(ColorSpace.Named.SRGB) || !colorManagementProxy.isSupportedColorSpace(peekWallpaperBitmap.getColorSpace())) ? false : true;
    }

    @Override // android.app.SemWallpaperManager
    public boolean wallpaperSupportsWcg(Bitmap bitmap) {
        if (shouldEnableWideColorGamut()) {
            return (bitmap == null || bitmap.getColorSpace() == null || bitmap.getColorSpace() == ColorSpace.get(ColorSpace.Named.SRGB) || !getColorManagementProxy().isSupportedColorSpace(bitmap.getColorSpace())) ? false : true;
        }
        return false;
    }

    public Bitmap getBitmap() {
        return getBitmap(false);
    }

    public Bitmap getBitmap(boolean z) {
        return getBitmapAsUser(this.mContext.getUserId(), z, 1, true);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmap(boolean z, int i, boolean z2) {
        return getBitmapAsUser(this.mContext.getUserId(), z, i, z2);
    }

    public Bitmap getBitmap(boolean z, int i) {
        return getBitmapAsUser(this.mContext.getUserId(), z, i);
    }

    public Bitmap getBitmapAsUser(int i, boolean z) {
        return getBitmapAsUser(i, z, 1, true);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapAsUser(int i, boolean z, int i2, boolean z2) {
        return sGlobals.peekWallpaperBitmap(this.mContext, true, i2, i, z, getColorManagementProxy(), z2);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapForDex() {
        return getBitmapForDex(false);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapForDex(boolean z) {
        return getBitmapForDexAsUser(this.mContext.getUserId(), z);
    }

    @Override // android.app.SemWallpaperManager
    public Bitmap getBitmapForDexAsUser(int i, boolean z) {
        return sGlobals.peekWallpaperBitmap(this.mContext, true, 9, i, z, getColorManagementProxy());
    }

    public Bitmap getBitmapAsUser(int i, boolean z, int i2) {
        return getBitmapAsUser(i, z, i2, !WhichChecker.isLock(i2), true);
    }

    public Bitmap getBitmapAsUser(int i, boolean z, int i2, boolean z2, boolean z3) {
        return sGlobals.peekWallpaperBitmap(this.mContext, z2, i2, i, z, getColorManagementProxy(), z3);
    }

    public Rect peekBitmapDimensions() {
        return peekBitmapDimensions(1);
    }

    public Rect peekBitmapDimensions(int i) {
        if (canPeekWallpaper(i)) {
            return peekBitmapDimensions(i, !WhichChecker.isLock(i));
        }
        return null;
    }

    public Rect peekBitmapDimensions(int i, boolean z) {
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.multiCrop()) {
            return peekBitmapDimensionsAsUser(i, z, this.mContext.getUserId());
        }
        checkExactlyOneWallpaperFlagSet(i);
        if (!canPeekWallpaper(i)) {
            return null;
        }
        Globals globals = sGlobals;
        Context context = this.mContext;
        return globals.peekWallpaperDimensions(context, z, i, context.getUserId());
    }

    public Rect peekBitmapDimensionsAsUser(int i, boolean z, int i2) {
        checkExactlyOneWallpaperFlagSet(i);
        return sGlobals.peekWallpaperDimensions(this.mContext, z, i, i2);
    }

    public List<Rect> getBitmapCrops(List<Point> list, int i, boolean z) {
        checkExactlyOneWallpaperFlagSet(i);
        try {
            List<Rect> bitmapCrops = sGlobals.mService.getBitmapCrops(list, i, z, this.mContext.getUserId());
            if (bitmapCrops != null) {
                return bitmapCrops;
            }
            Rect peekBitmapDimensions = peekBitmapDimensions(i, true);
            if (peekBitmapDimensions == null) {
                return Collections.EMPTY_LIST;
            }
            return getBitmapCrops(new Point(peekBitmapDimensions.width(), peekBitmapDimensions.height()), list, (Map<Point, Rect>) null);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public List<Rect> getBitmapCrops(Point point, List<Point> list, Map<Point, Rect> map) {
        if (map == null) {
            try {
                map = Collections.EMPTY_MAP;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Set<Map.Entry<Point, Rect>> entrySet = map.entrySet();
        return sGlobals.mService.getFutureBitmapCrops(point, list, entrySet.stream().mapToInt(new ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int orientation;
                orientation = WallpaperManager.getOrientation((Point) ((Map.Entry) obj).getKey());
                return orientation;
            }
        }).toArray(), entrySet.stream().map(new WallpaperManager$$ExternalSyntheticLambda3()).toList());
    }

    public WallpaperColors getWallpaperColors(Bitmap bitmap, Map<Point, Rect> map) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        if (map == null) {
            try {
                map = Collections.EMPTY_MAP;
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        Set<Map.Entry<Point, Rect>> entrySet = map.entrySet();
        int[] array = entrySet.stream().mapToInt(new ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                int orientation;
                orientation = WallpaperManager.getOrientation((Point) ((Map.Entry) obj).getKey());
                return orientation;
            }
        }).toArray();
        List<Rect> list = entrySet.stream().map(new WallpaperManager$$ExternalSyntheticLambda3()).toList();
        Rect bitmapCrop = sGlobals.mService.getBitmapCrop(new Point(bitmap.getWidth(), bitmap.getHeight()), array, list);
        return WallpaperColors.fromBitmap(Bitmap.createBitmap(bitmap, bitmapCrop.left, bitmapCrop.top, bitmapCrop.width(), bitmapCrop.height()), getWallpaperDimAmount());
    }

    public ParcelFileDescriptor getWallpaperFile(int i) {
        return getWallpaperFile(i, this.mContext.getUserId());
    }

    public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, Handler handler) {
        addOnColorsChangedListener(onColorsChangedListener, handler, this.mContext.getUserId());
    }

    public void addOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, Handler handler, int i) {
        sGlobals.addOnColorsChangedListener(onColorsChangedListener, handler, i, this.mContext.getDisplayId());
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener) {
        removeOnColorsChangedListener(onColorsChangedListener, this.mContext.getUserId());
    }

    public void removeOnColorsChangedListener(OnColorsChangedListener onColorsChangedListener, int i) {
        sGlobals.removeOnColorsChangedListener(onColorsChangedListener, i, this.mContext.getDisplayId());
    }

    public WallpaperColors getWallpaperColors(int i) {
        return getWallpaperColors(i, this.mContext.getUserId());
    }

    public WallpaperColors getWallpaperColors(int i, int i2) {
        StrictMode.assertUiContext(this.mContext, "getWallpaperColors");
        return sGlobals.getWallpaperColors(i, i2, this.mContext.getDisplayId());
    }

    public void addOnColorsChangedListener(LocalWallpaperColorConsumer localWallpaperColorConsumer, List<RectF> list, int i) throws IllegalArgumentException {
        for (RectF rectF : list) {
            RectF rectF2 = LOCAL_COLOR_BOUNDS;
            if (!rectF2.contains(rectF)) {
                throw new IllegalArgumentException("Regions must be within bounds " + rectF2);
            }
        }
        sGlobals.addOnColorsChangedListener(localWallpaperColorConsumer, list, i, this.mContext.getUserId(), this.mContext.getDisplayId());
    }

    public void removeOnColorsChangedListener(LocalWallpaperColorConsumer localWallpaperColorConsumer) {
        sGlobals.removeOnColorsChangedListener(localWallpaperColorConsumer, 1, this.mContext.getUserId(), this.mContext.getDisplayId());
    }

    @Override // android.app.SemWallpaperManager
    public SemWallpaperColors semGetWallpaperColors(int i) {
        SemWallpaperColors preconditionWallpaperColors = getPreconditionWallpaperColors(i);
        return preconditionWallpaperColors != null ? preconditionWallpaperColors : sGlobals.semGetWallpaperColors(i);
    }

    public SemWallpaperColors semGetPrimaryWallpaperColors(int i) {
        SemWallpaperColors preconditionWallpaperColors = getPreconditionWallpaperColors(i);
        return preconditionWallpaperColors != null ? preconditionWallpaperColors : sGlobals.semGetPrimaryWallpaperColors(i);
    }

    private SemWallpaperColors getPreconditionWallpaperColors(int i) {
        if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && !Rune.SUPPORT_SUB_DISPLAY_MODE && !Rune.VIRTUAL_DISPLAY_WALLPAPER && (WhichChecker.isSubDisplay(i) || WhichChecker.isVirtualDisplay(i))) {
            Log.d(TAG, "getPreconditionWallpaperColors: Unsupported which. which = " + i);
            return SemWallpaperColors.getBlankWallpaperColors();
        }
        if ((i & 2) == 0) {
            return null;
        }
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_EMERGENCY_MODE, 0, this.mContext.getUserId());
        int intForUser2 = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_ULTRA_POWERSAVING_MODE, 0, this.mContext.getUserId());
        int intForUser3 = Settings.System.getIntForUser(this.mContext.getContentResolver(), Settings.System.SEM_MINIMAL_BATTERY_USE, 0, this.mContext.getUserId());
        if (intForUser == 1 || intForUser2 == 1 || intForUser3 == 1) {
            return SemWallpaperColors.getBlankWallpaperColors();
        }
        return null;
    }

    public void semSendWallpaperCommand(int i, String str, Bundle bundle) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.semSendWallpaperCommand(i, str, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semClearWallpaperThumbnailCache(int i, int i2) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.semClearWallpaperThumbnailCache(i, i2, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semRequestWallpaperColorsAnalysis(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.semRequestWallpaperColorsAnalysis(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor semGetThumbnailFileDescriptor(int i, int i2, int i3) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semGetThumbnailFileDescriptor(i, i2, i3);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semSetSmartCropRect(int i, Rect rect, Rect rect2) {
        sGlobals.semSetSmartCropRect(i, rect, rect2);
    }

    @Override // android.app.SemWallpaperManager
    public Rect semGetSmartCropRect(int i) {
        return sGlobals.semGetSmartCropRect(i);
    }

    public ParcelFileDescriptor getWallpaperFile(int i, int i2) {
        return getWallpaperFile(i, i2, true, 0);
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getWallpaperFile(int i, int i2, int i3) {
        return getWallpaperFile(i, i2, true, i3);
    }

    public ParcelFileDescriptor getWallpaperFile(int i, boolean z) {
        return getWallpaperFile(i, this.mContext.getUserId(), z, 0);
    }

    public ParcelFileDescriptor getWallpaperFile(int i, int i2, boolean z, int i3) {
        checkExactlyOneWallpaperFlagSet(i);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), null, i, new Bundle(), i2, z, false, i3);
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
    public ParcelFileDescriptor getLockWallpaperFile(int i) {
        return getLockWallpaperFile(i, 2);
    }

    public ParcelFileDescriptor getLockWallpaperFile(int i, int i2, boolean z) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle bundle = new Bundle();
            int mode = WhichChecker.getMode(i2);
            if (isSystemAndLockPaired(mode)) {
                i2 = mode | 1;
            }
            return sGlobals.mService.getWallpaperWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), null, i2, bundle, i, z, true, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getLockWallpaperFile(int i, int i2) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            Bundle bundle = new Bundle();
            int mode = WhichChecker.getMode(i2);
            if (isSystemAndLockPaired(mode)) {
                i2 = mode | 1;
            }
            return sGlobals.mService.getLockWallpaper(null, bundle, i, i2);
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

    public WallpaperInfo getWallpaperInfoForUser(int i) {
        return getWallpaperInfo(1, i);
    }

    public WallpaperInfo getWallpaperInfo(int i) {
        return getWallpaperInfo(i, this.mContext.getUserId());
    }

    @Override // android.app.SemWallpaperManager
    public WallpaperInfo getWallpaperInfo(int i, int i2) {
        checkExactlyOneWallpaperFlagSet(i);
        try {
            if (sGlobals.mService == null) {
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            return sGlobals.mService.getWallpaperInfoWithFlags(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public WallpaperInstance getWallpaperInstance(int i) {
        checkExactlyOneWallpaperFlagSet(i);
        try {
            if (sGlobals.mService == null) {
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            return sGlobals.mService.getWallpaperInstance(i, this.mContext.getUserId());
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

    public int getWallpaperId(int i) {
        return getWallpaperIdForUser(i, this.mContext.getUserId());
    }

    public int getWallpaperIdForUser(int i, int i2) {
        try {
            if (sGlobals.mService == null) {
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            return sGlobals.mService.getWallpaperIdForUser(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0044, code lost:
    
        if (r0.queryIntentActivities(r1, 0).size() > 0) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.content.Intent getCropAndSetWallpaperIntent(android.net.Uri r4) {
        /*
            r3 = this;
            if (r4 == 0) goto L6e
            java.lang.String r0 = "content"
            java.lang.String r1 = r4.getScheme()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L66
            android.content.Context r0 = r3.mContext
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r2 = "android.service.wallpaper.CROP_AND_SET_WALLPAPER"
            r1.<init>(r2, r4)
            r4 = 1
            r1.addFlags(r4)
            android.content.Intent r4 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.MAIN"
            r4.<init>(r2)
            java.lang.String r2 = "android.intent.category.HOME"
            android.content.Intent r4 = r4.addCategory(r2)
            r2 = 65536(0x10000, float:9.1835E-41)
            android.content.pm.ResolveInfo r4 = r0.resolveActivity(r4, r2)
            r2 = 0
            if (r4 == 0) goto L47
            android.content.pm.ActivityInfo r4 = r4.activityInfo
            java.lang.String r4 = r4.packageName
            r1.setPackage(r4)
            java.util.List r4 = r0.queryIntentActivities(r1, r2)
            int r4 = r4.size()
            if (r4 <= 0) goto L47
            goto L5d
        L47:
            android.content.Context r3 = r3.mContext
            r4 = 17040362(0x10403ea, float:2.424738E-38)
            java.lang.String r3 = r3.getString(r4)
            r1.setPackage(r3)
            java.util.List r3 = r0.queryIntentActivities(r1, r2)
            int r3 = r3.size()
            if (r3 <= 0) goto L5e
        L5d:
            return r1
        L5e:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "Cannot use passed URI to set wallpaper; check that the type returned by ContentProvider matches image/*"
            r3.<init>(r4)
            throw r3
        L66:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "Image URI must be of the content scheme type"
            r3.<init>(r4)
            throw r3
        L6e:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "Image URI must not be null"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.getCropAndSetWallpaperIntent(android.net.Uri):android.content.Intent");
    }

    private boolean isRequestForDex(int i) {
        return Rune.SUPPORT_DESKTOP_MODE && WhichChecker.isDex(i);
    }

    public void setResource(int i) throws IOException {
        setResource(i, 3);
    }

    public int setResource(int i, int i2) throws IOException {
        return setResource(this.mContext, i, i2, 0, false, false, null);
    }

    private int setPreloadedResource(Context context, int i, int i2, boolean z, Bundle bundle) throws IOException {
        return setResource(context, i, i2, 0, z, true, bundle);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00f2 A[Catch: RemoteException -> 0x00fd, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x00fd, blocks: (B:8:0x0054, B:10:0x0066, B:11:0x007f, B:23:0x00d4, B:33:0x00e8, B:34:0x00eb, B:29:0x00e4, B:38:0x00ec, B:40:0x00f2), top: B:7:0x0054 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int setResource(android.content.Context r17, int r18, int r19, int r20, boolean r21, boolean r22, android.os.Bundle r23) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 277
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.setResource(android.content.Context, int, int, int, boolean, boolean, android.os.Bundle):int");
    }

    @Override // android.app.SemWallpaperManager
    @Deprecated
    public void setWallpaperUri(String str, boolean z, int i) throws IOException, PackageManager.NameNotFoundException {
        semSetUri(Uri.parse(str), z, i);
    }

    @Override // android.app.SemWallpaperManager
    public Uri semGetUri(int i) {
        try {
            if (WhichChecker.isLock(i) && isSystemAndLockPaired(i)) {
                Log.d(TAG, "semGetUri: Converting which to system.");
                i = WhichChecker.getMode(i) | 1;
            }
            String semGetUri = sGlobals.mService.semGetUri(i, this.mContext.getOpPackageName());
            if (semGetUri != null) {
                return Uri.parse(semGetUri);
            }
            return null;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semSetDLSWallpaperColors(SemWallpaperColors semWallpaperColors, int i) {
        Log.d(TAG, "semSetDLSWallpaperColors " + semWallpaperColors + ", " + i);
        try {
            sGlobals.mService.semSetDLSWallpaperColors(semWallpaperColors, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semSetUri(Uri uri, boolean z, int i) throws IOException, PackageManager.NameNotFoundException {
        semSetUri(uri, z, i, -1);
    }

    @Override // android.app.SemWallpaperManager
    public void semSetUri(Uri uri, boolean z, int i, int i2) throws IOException, PackageManager.NameNotFoundException {
        semSetUri(uri, z, i, i2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void semSetUri(android.net.Uri r12, boolean r13, int r14, int r15, android.os.Bundle r16) throws java.io.IOException, android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 254
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.semSetUri(android.net.Uri, boolean, int, int, android.os.Bundle):void");
    }

    private void semSetWallpaper(String str, boolean z, int i, int i2, Bundle bundle) throws IOException {
        Throwable th;
        if (i2 != 1000 || WhichChecker.isLock(i)) {
            try {
                ParcelFileDescriptor semSetWallpaper = sGlobals.mService.semSetWallpaper(str, this.mContext.getOpPackageName(), null, null, z, null, i, null, this.mContext.getUserId(), i2, false, bundle);
                if (semSetWallpaper != null) {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
                    try {
                        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new ParcelFileDescriptor.AutoCloseOutputStream(semSetWallpaper);
                        try {
                            autoCloseOutputStream2.close();
                            IoUtils.closeQuietly(autoCloseOutputStream2);
                        } catch (Throwable th2) {
                            th = th2;
                            autoCloseOutputStream = autoCloseOutputStream2;
                            IoUtils.closeQuietly(autoCloseOutputStream);
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
                if (isNeedToClearBackupData()) {
                    semClearBackupWallpapers(i);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void forceRebindWallpaper(int i) {
        try {
            sGlobals.mService.forceRebindWallpaper(i, this.mContext.getUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setBitmap(Bitmap bitmap) throws IOException {
        setBitmap(bitmap, null, true);
    }

    public int setBitmap(Bitmap bitmap, Rect rect, boolean z) throws IOException {
        return setBitmap(bitmap, rect, z, 3);
    }

    public int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i) throws IOException {
        return setBitmap(bitmap, rect, z, i, this.mContext.getUserId(), 0, null);
    }

    public int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i, int i2) throws IOException {
        return setBitmap(bitmap, rect, z, i, i2, 0, null);
    }

    @Override // android.app.SemWallpaperManager
    public int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i, Bundle bundle) throws IOException {
        return setBitmap(bitmap, rect, z, i, this.mContext.getUserId(), 0, bundle);
    }

    private int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i, int i2, int i3, Bundle bundle) throws IOException {
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        String str = TAG;
        StringBuilder sb = new StringBuilder("setBitmap calling package = ");
        sb.append(this.mContext.getOpPackageName());
        sb.append(", allowBackup = ");
        sb.append(z);
        sb.append(", which = ");
        sb.append(i);
        sb.append(", userId = ");
        sb.append(i2);
        sb.append(", type = ");
        sb.append(i3);
        sb.append(", hasExtras = ");
        sb.append(bundle != null);
        Log.d(str, sb.toString());
        if (bitmap != null) {
            Log.d(TAG, "setBitmap bitmap width = " + bitmap.getWidth() + ", height = " + bitmap.getHeight());
        }
        if (rect != null) {
            Log.d(TAG, "setBitmap crop hint = " + rect);
        }
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.multiCrop()) {
            SparseArray<Rect> sparseArray = new SparseArray<>();
            if (rect != null) {
                sparseArray.put(-1, rect);
            }
            return setBitmapWithCrops(bitmap, sparseArray, z, i, i2);
        }
        validateRect(rect);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle bundle2 = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion(this);
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
        try {
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, rect == null ? null : List.of(rect), z, bundle2, i, wallpaperSetCompletion, i2, i3, false, bundle);
            if (wallpaper != null) {
                try {
                    autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                    wallpaperSetCompletion.waitForCompletion();
                    IoUtils.closeQuietly(autoCloseOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    IoUtils.closeQuietly(autoCloseOutputStream2);
                    throw th;
                }
            }
            return bundle2.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setBitmapWithCrops(Bitmap bitmap, Map<Point, Rect> map, boolean z, int i) throws IOException {
        final SparseArray<Rect> sparseArray = new SparseArray<>();
        map.forEach(new BiConsumer() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SparseArray.this.put(WallpaperManager.getOrientation((Point) obj), (Rect) obj2);
            }
        });
        return setBitmapWithCrops(bitmap, sparseArray, z, i, this.mContext.getUserId());
    }

    private int setBitmapWithCrops(Bitmap bitmap, SparseArray<Rect> sparseArray, boolean z, int i, int i2) throws IOException {
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        int size = sparseArray.size();
        int[] iArr = new int[size];
        ArrayList arrayList = new ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            iArr[i3] = sparseArray.keyAt(i3);
            Rect valueAt = sparseArray.valueAt(i3);
            validateRect(valueAt);
            arrayList.add(valueAt);
        }
        Bundle bundle = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion(this);
        try {
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), iArr, arrayList, z, bundle, i, wallpaperSetCompletion, i2, 0, false, null);
            if (wallpaper != null) {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
                try {
                    autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                    wallpaperSetCompletion.waitForCompletion();
                    IoUtils.closeQuietly(autoCloseOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    IoUtils.closeQuietly(autoCloseOutputStream2);
                    throw th;
                }
            }
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(i);
            }
            return bundle.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setBitmapWithDescription(Bitmap bitmap, WallpaperDescription wallpaperDescription, boolean z, int i) throws IOException {
        return setBitmapWithCrops(bitmap, wallpaperDescription.getCropHints(), z, i, this.mContext.getUserId());
    }

    private final void validateRect(Rect rect) {
        if (rect != null && rect.isEmpty()) {
            throw new IllegalArgumentException("visibleCrop rectangle must be valid and non-empty");
        }
    }

    public void setStream(InputStream inputStream) throws IOException {
        setStream(inputStream, null, true);
    }

    private void copyStreamToWallpaperFile(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        FileUtils.copy(inputStream, fileOutputStream);
    }

    private void copyDrawableToWallpaperFile(BitmapDrawable bitmapDrawable, FileOutputStream fileOutputStream) {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        Log.i(TAG, "copyDrawableToWallpaperFile");
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            } catch (Exception e) {
                e = e;
                byteArrayInputStream = null;
            } catch (Throwable th) {
                th = th;
                byteArrayInputStream = null;
            }
        } catch (Exception e2) {
            e = e2;
            byteArrayInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream = null;
        }
        try {
            FileUtils.copy(byteArrayInputStream, fileOutputStream);
            IoUtils.closeQuietly(byteArrayOutputStream);
            IoUtils.closeQuietly(byteArrayInputStream);
        } catch (Exception e3) {
            e = e3;
            byteArrayOutputStream2 = byteArrayOutputStream;
            try {
                e.printStackTrace();
                IoUtils.closeQuietly(byteArrayOutputStream2);
                IoUtils.closeQuietly(byteArrayInputStream);
            } catch (Throwable th3) {
                th = th3;
                IoUtils.closeQuietly(byteArrayOutputStream2);
                IoUtils.closeQuietly(byteArrayInputStream);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            byteArrayOutputStream2 = byteArrayOutputStream;
            IoUtils.closeQuietly(byteArrayOutputStream2);
            IoUtils.closeQuietly(byteArrayInputStream);
            throw th;
        }
    }

    public int setStream(InputStream inputStream, Rect rect, boolean z) throws IOException {
        return setStream(inputStream, rect, z, 3);
    }

    public int setStream(InputStream inputStream, Rect rect, boolean z, int i) throws IOException {
        return setStream(inputStream, rect, z, i, 0);
    }

    private int setPreloadedStream(InputStream inputStream, Rect rect, boolean z, int i) throws IOException {
        return setStream(inputStream, rect, z, i, 0, true, null);
    }

    private int setStream(InputStream inputStream, Rect rect, boolean z, int i, int i2) throws IOException {
        return setStream(inputStream, rect, z, i, i2, false, null);
    }

    @Override // android.app.SemWallpaperManager
    public int setStream(InputStream inputStream, Rect rect, boolean z, int i, int i2, boolean z2, Bundle bundle) throws IOException {
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        String str = TAG;
        StringBuilder sb = new StringBuilder("setStream calling package = ");
        sb.append(this.mContext.getOpPackageName());
        sb.append(", allowBackup = ");
        sb.append(z);
        sb.append(", which = ");
        sb.append(i);
        sb.append(", type = ");
        sb.append(i2);
        sb.append(", hasExtra = ");
        sb.append(bundle != null);
        Log.d(str, sb.toString());
        if (inputStream != null) {
            Log.d(TAG, "setStream bitmap data = " + inputStream);
        }
        if (rect != null) {
            Log.d(TAG, "setStream crop hint = " + rect);
        }
        if (com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags.multiCrop()) {
            SparseArray<Rect> sparseArray = new SparseArray<>();
            if (rect != null) {
                sparseArray.put(-1, rect);
            }
            return setStreamWithCrops(inputStream, sparseArray, z, i, bundle);
        }
        validateRect(rect);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle bundle2 = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion(this);
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
        List<Rect> of = rect == null ? null : List.of(rect);
        try {
            Log.d(TAG, "begin setWallpaper()");
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, of, z, bundle2, i, wallpaperSetCompletion, this.mContext.getUserId(), i2, z2, bundle);
            Log.d(TAG, "finish setWallpaper()");
            if (wallpaper != null) {
                try {
                    autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    copyStreamToWallpaperFile(inputStream, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                    wallpaperSetCompletion.waitForCompletion();
                    IoUtils.closeQuietly(autoCloseOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    IoUtils.closeQuietly(autoCloseOutputStream2);
                    throw th;
                }
            }
            return bundle2.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setStreamWithCrops(InputStream inputStream, Map<Point, Rect> map, boolean z, int i) throws IOException {
        final SparseArray<Rect> sparseArray = new SparseArray<>();
        map.forEach(new BiConsumer() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                SparseArray.this.put(WallpaperManager.getOrientation((Point) obj), (Rect) obj2);
            }
        });
        return setStreamWithCrops(inputStream, sparseArray, z, i);
    }

    @SystemApi
    public int setStreamWithCrops(InputStream inputStream, SparseArray<Rect> sparseArray, boolean z, int i) throws IOException {
        return setStreamWithCrops(inputStream, sparseArray, z, i, null);
    }

    private int setStreamWithCrops(InputStream inputStream, SparseArray<Rect> sparseArray, boolean z, int i, Bundle bundle) throws IOException {
        Throwable th;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        int size = sparseArray.size();
        int[] iArr = new int[size];
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = sparseArray.keyAt(i2);
            Rect valueAt = sparseArray.valueAt(i2);
            validateRect(valueAt);
            arrayList.add(valueAt);
        }
        Bundle bundle2 = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion(this);
        try {
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), iArr, arrayList, z, bundle2, i, wallpaperSetCompletion, this.mContext.getUserId(), 0, false, bundle);
            if (wallpaper != null) {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
                try {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    try {
                        copyStreamToWallpaperFile(inputStream, autoCloseOutputStream2);
                        autoCloseOutputStream2.close();
                        wallpaperSetCompletion.waitForCompletion();
                        IoUtils.closeQuietly(autoCloseOutputStream2);
                    } catch (Throwable th2) {
                        th = th2;
                        autoCloseOutputStream = autoCloseOutputStream2;
                        IoUtils.closeQuietly(autoCloseOutputStream);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            }
            int callingUserId = UserHandle.getCallingUserId();
            Log.i(TAG, "setStreamWithCrops: finished. which=" + i + ", userId=" + callingUserId);
            if (SemDesktopModeManager.LAUNCHER_PACKAGE.equals(this.mContext.getOpPackageName())) {
                if (WhichChecker.isSystem(i)) {
                    File file = new File(Environment.getUserSystemDirectory(this.mContext.getUserId()), "wallpaper_desktop_orig");
                    if (file.exists() && file.canRead()) {
                        Log.i(TAG, "setStreamWithCrops: result : wallpaper_desktop_orig file length=" + file.length());
                    } else {
                        Log.i(TAG, "setStreamWithCrops: result : Invalid file path. which=" + i);
                    }
                } else if (WhichChecker.isLock(i)) {
                    File file2 = new File(Environment.getUserSystemDirectory(this.mContext.getUserId()), "wallpaper_lock_images/wallpaper_desktop_lock_orig");
                    if (file2.exists() && file2.canRead()) {
                        Log.i(TAG, "setStreamWithCrops: result : wallpaper_desktop_lock_orig file length=" + file2.length());
                    } else {
                        Log.i(TAG, "setStreamWithCrops: result : Invalid file path. which=" + i);
                    }
                }
            }
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(i);
            }
            return bundle2.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int setStreamWithDescription(InputStream inputStream, WallpaperDescription wallpaperDescription, boolean z, int i) throws IOException {
        return setStreamWithCrops(inputStream, wallpaperDescription.getCropHints(), z, i);
    }

    public boolean hasResourceWallpaper(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.hasNamedWallpaper("res:" + this.mContext.getResources().getResourceName(i));
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

    public void suggestDesiredDimensions(int i, int i2) {
        StrictMode.assertUiContext(this.mContext, "suggestDesiredDimensions");
        int i3 = 0;
        try {
            try {
                i3 = SystemProperties.getInt("sys.max_texture_size", 0);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (Exception unused) {
        }
        if (i3 > 0 && (i > i3 || i2 > i3)) {
            float f = i2 / i;
            if (i > i2) {
                i2 = (int) ((i3 * f) + 0.5d);
                i = i3;
            } else {
                i = (int) ((i3 / f) + 0.5d);
                i2 = i3;
            }
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        sGlobals.mService.setDimensionHints(i, i2, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
    }

    public void setDisplayPadding(Rect rect) {
        StrictMode.assertUiContext(this.mContext, "setDisplayPadding");
        try {
            if (sGlobals.mService == null) {
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            sGlobals.mService.setDisplayPadding(rect, this.mContext.getOpPackageName(), this.mContext.getDisplayId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDisplayOffset(IBinder iBinder, int i, int i2) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperDisplayOffset(iBinder, i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearWallpaper() {
        clearWallpaper(3, this.mContext.getUserId());
    }

    @SystemApi
    public void clearWallpaper(int i, int i2) {
        Log.d(TAG, "clearWallpaper() called with: which = [" + i + "], userId = [" + i2 + NavigationBarInflaterView.SIZE_MOD_END);
        int type = WhichChecker.getType(i);
        int mode = WhichChecker.getMode(i);
        if (WhichChecker.isSystemAndLock(type)) {
            clearWallpaper(mode | 1, i2);
            clearWallpaper(mode | 2, i2);
        } else {
            if (sGlobals.mService == null) {
                Log.w(TAG, "WallpaperService not running");
                throw new RuntimeException(new DeadSystemException());
            }
            try {
                synchronized (sSync) {
                    sGlobals.mService.clearWallpaper(this.mContext.getOpPackageName(), i, i2);
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean setWallpaperComponent(ComponentName componentName) {
        return setWallpaperComponent(componentName, this.mContext.getUserId());
    }

    @SystemApi
    public void setWallpaperDimAmount(float f) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setWallpaperDimAmount(MathUtils.saturate(f));
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

    public boolean setWallpaperComponent(ComponentName componentName, int i) {
        return setWallpaperComponentWithFlags(componentName, 3, i);
    }

    @SystemApi
    public boolean setWallpaperComponentWithFlags(ComponentName componentName, int i) {
        return setWallpaperComponentWithFlags(componentName, i, this.mContext.getUserId());
    }

    public boolean setWallpaperComponentWithFlags(ComponentName componentName, int i, int i2) {
        return setWallpaperComponentWithDescription(new WallpaperDescription.Builder().setComponent(componentName).build(), i, i2);
    }

    @SystemApi
    public boolean setWallpaperComponentWithDescription(WallpaperDescription wallpaperDescription, int i) {
        return setWallpaperComponentWithDescription(wallpaperDescription, i, this.mContext.getUserId());
    }

    public boolean setWallpaperComponentWithDescription(WallpaperDescription wallpaperDescription, int i, int i2) {
        ComponentName component;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperManagerService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setWallpaperComponentChecked(wallpaperDescription, this.mContext.getOpPackageName(), i, i2, null);
            int i3 = (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && (component = wallpaperDescription.getComponent()) != null && "com.samsung.android.aremoji".equals(component.getPackageName()) && WhichChecker.getMode(i) == 0) ? i | 16 : i;
            if (!isNeedToClearBackupData()) {
                return true;
            }
            semClearBackupWallpapers(i3);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperOffsets(IBinder iBinder, float f, float f2) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(iBinder, f, f2, this.mWallpaperXStep, this.mWallpaperYStep);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperOffsetSteps(float f, float f2) {
        this.mWallpaperXStep = f;
        this.mWallpaperYStep = f2;
    }

    public void sendWallpaperCommand(IBinder iBinder, String str, int i, int i2, int i3, Bundle bundle) {
        try {
            WindowManagerGlobal.getWindowSession().sendWallpaperCommand(iBinder, str, i, i2, i3, bundle, false);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setWallpaperZoomOut(IBinder iBinder, float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("zoom must be between 0 and 1: " + f);
        }
        if (iBinder == null) {
            throw new IllegalArgumentException("windowToken must not be null");
        }
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperZoomOut(iBinder, f);
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

    public void clearWallpaperOffsets(IBinder iBinder) {
        try {
            WindowManagerGlobal.getWindowSession().setWallpaperPosition(iBinder, -1.0f, -1.0f, -1.0f, -1.0f);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clear() throws IOException {
        clear(3);
    }

    public void clear(int i) throws IOException {
        Log.d(TAG, "clear, which = [" + i + NavigationBarInflaterView.SIZE_MOD_END);
        if (!WhichChecker.isSystem(i) && !WhichChecker.isLock(i) && !WhichChecker.isSystemAndLock(i)) {
            Log.e(TAG, "Must specify a valid wallpaper category to set");
            return;
        }
        int type = WhichChecker.getType(i);
        int mode = WhichChecker.getMode(i);
        if (WhichChecker.isSystemAndLock(type)) {
            clear(mode | 1);
            clear(mode | 2);
        } else {
            clearWallpaper(i, this.mContext.getUserId());
        }
    }

    private int appendCurrentModeIfNeeded(int i) {
        return isSubDisplay() ? i | 16 : i;
    }

    public boolean isThemeSingleWallpaper(Context context, int i) {
        String str;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(i) == 0) {
            Log.d(TAG, "isThemeSingleWallpaper: which = " + i + ". 'which' does not have mode. getCurrentImplicitMode.");
            i |= WhichChecker.getCurrentImplicitMode(context);
        }
        if (WhichChecker.isSubDisplay(i)) {
            if (WhichChecker.isLock(i)) {
                str = "sub_display_lockscreen_wallpaper_transparency";
            } else {
                str = "sub_display_system_wallpaper_transparency";
            }
        } else if (WhichChecker.isLock(i)) {
            str = "lockscreen_wallpaper_transparent";
        } else {
            str = "android.wallpaper.settings_systemui_transparency";
        }
        int intForUser = Settings.System.getIntForUser(context.getContentResolver(), str, -1, context.getUserId());
        Log.d(TAG, "isThemeSingleWallpaper: which = " + i + ", name = " + str + ", type = " + intForUser);
        return intForUser == 3;
    }

    public boolean isThemeWallpaper(Context context, int i) {
        String str;
        if (Rune.SUPPORT_SUB_DISPLAY_MODE && WhichChecker.getMode(i) == 0) {
            Log.d(TAG, "isThemeWallpaper: which = " + i + ". 'which' does not have mode. getCurrentImplicitMode.");
            i |= WhichChecker.getCurrentImplicitMode(context);
        }
        if (WhichChecker.isSubDisplay(i)) {
            if (WhichChecker.isLock(i)) {
                str = "sub_display_lockscreen_wallpaper_transparency";
            } else {
                str = "sub_display_system_wallpaper_transparency";
            }
        } else if (WhichChecker.isLock(i)) {
            str = "lockscreen_wallpaper_transparent";
        } else {
            str = "android.wallpaper.settings_systemui_transparency";
        }
        int intForUser = Settings.System.getIntForUser(context.getContentResolver(), str, -1, context.getUserId());
        Log.d(TAG, "isThemeWallpaper: which = " + i + ", name = " + str + ", type = " + intForUser);
        if (intForUser == 2) {
            return true;
        }
        if (intForUser != 1) {
            return false;
        }
        String lastCallingPackage = getLastCallingPackage(context, i);
        Log.d(TAG, "isThemeWallpaper: lastCallingPackage = " + lastCallingPackage);
        return "com.samsung.android.themecenter".equals(lastCallingPackage);
    }

    public static InputStream openDefaultWallpaper(Context context, int i) {
        if (FactoryTest.isFactoryBinary()) {
            InputStream wallpaperInputStream = getWallpaperInputStream(SystemProperties.get(PROP_WALLPAPER));
            if (wallpaperInputStream != null) {
                return wallpaperInputStream;
            }
            InputStream wallpaperInputStream2 = getWallpaperInputStream(getCmfWallpaperPath());
            if (wallpaperInputStream2 != null) {
                return wallpaperInputStream2;
            }
            try {
                return context.getResources().openRawResource(R.drawable.default_wallpaper);
            } catch (Resources.NotFoundException unused) {
            }
        }
        return openDefaultWallpaper(context, i, true);
    }

    private static ParcelFileDescriptor getDefaultSystemWallpaperFile() {
        Iterator<String> it = getDefaultSystemWallpaperPaths().iterator();
        while (it.hasNext()) {
            File file = new File(it.next());
            if (file.exists()) {
                try {
                    return ParcelFileDescriptor.open(file, 268435456);
                } catch (FileNotFoundException unused) {
                    continue;
                }
            }
        }
        return null;
    }

    public static InputStream openDefaultWallpaper(Context context, int i, boolean z) {
        return openDefaultWallpaper(context, i, true, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.io.InputStream openDefaultWallpaper(android.content.Context r3, int r4, boolean r5, java.lang.String r6) {
        /*
            boolean r5 = com.samsung.android.wallpaper.utils.WhichChecker.isSubDisplay(r4)
            if (r5 == 0) goto Ld
            boolean r5 = com.samsung.android.wallpaper.Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
            if (r5 != 0) goto Lb
            goto Ld
        Lb:
            r5 = 0
            goto Le
        Ld:
            r5 = 1
        Le:
            java.lang.String r0 = android.app.WallpaperManager.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "openDefaultWallpaper() which = "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r2 = " , color = "
            r1.append(r2)
            r1.append(r6)
            java.lang.String r1 = r1.toString()
            android.util.Log.d(r0, r1)
            boolean r6 = android.text.TextUtils.isEmpty(r6)
            if (r6 == 0) goto L46
            if (r5 == 0) goto L46
            java.io.File r5 = getDefaultWallpaperFile(r3, r4)
            if (r5 == 0) goto L46
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch: java.io.IOException -> L3e
            r6.<init>(r5)     // Catch: java.io.IOException -> L3e
            goto L47
        L3e:
            r5 = move-exception
            java.lang.String r6 = android.app.WallpaperManager.TAG
            java.lang.String r0 = "getDefaultWallpaperFile error:"
            android.util.Log.w(r6, r0, r5)
        L46:
            r6 = 0
        L47:
            if (r6 != 0) goto L62
            java.lang.Object r5 = android.app.WallpaperManager.sSync
            monitor-enter(r5)
            android.app.SemWallpaperResourcesInfo r6 = android.app.WallpaperManager.sWallpaperResourcesInfo     // Catch: java.lang.Throwable -> L5f
            if (r6 != 0) goto L57
            android.app.SemWallpaperResourcesInfo r6 = new android.app.SemWallpaperResourcesInfo     // Catch: java.lang.Throwable -> L5f
            r6.<init>(r3)     // Catch: java.lang.Throwable -> L5f
            android.app.WallpaperManager.sWallpaperResourcesInfo = r6     // Catch: java.lang.Throwable -> L5f
        L57:
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L5f
            android.app.SemWallpaperResourcesInfo r5 = android.app.WallpaperManager.sWallpaperResourcesInfo
            java.io.InputStream r6 = r5.getDefaultImageWallpaper(r4)
            goto L62
        L5f:
            r3 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> L5f
            throw r3
        L62:
            java.lang.String r4 = android.app.WallpaperManager.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r0 = "openDefaultWallpaper: by ["
            r5.<init>(r0)
            java.lang.String r3 = r3.getOpPackageName()
            r5.append(r3)
            java.lang.String r3 = "]"
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            android.util.Log.d(r4, r3)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.openDefaultWallpaper(android.content.Context, int, boolean, java.lang.String):java.io.InputStream");
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSupportCMFFeature() {
        return sWallpaperResourcesInfo.isSupportCMF();
    }

    @Override // android.app.SemWallpaperManager
    public int getDefaultWallpaperType(int i) {
        return sWallpaperResourcesInfo.getDefaultWallpaperType(getModeEnsuredWhich(i), getDeviceColor(this.mContext));
    }

    @Override // android.app.SemWallpaperManager
    public String getDefaultMultipackStyle(int i) {
        return sWallpaperResourcesInfo.getDefaultMultipackStyle(getModeEnsuredWhich(i));
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSupportDefaultMultipleWallpaper() {
        return sWallpaperResourcesInfo.isDefaultMultipack(getModeEnsuredWhich(2));
    }

    public static File getDefaultWallpaperFile(Context context) {
        return getDefaultWallpaperFile(context, 1);
    }

    public static File getDefaultWallpaperFile(Context context, int i) {
        File oMCWallpaperFile = SemWallpaperResourcesUtils.getOMCWallpaperFile(context, i);
        return oMCWallpaperFile == null ? SemWallpaperResourcesUtils.getCSCWallpaperFile(context, i, null) : oMCWallpaperFile;
    }

    public static String getOMCVideoWallpaperFilePath(String str) {
        return SemWallpaperResourcesUtils.getOMCVideoWallpaperFilePath(str);
    }

    public static File getOMCWallpaperFile(Context context, int i) {
        return SemWallpaperResourcesUtils.getOMCWallpaperFile(context, i, null);
    }

    public static File getOMCWallpaperFile(Context context, int i, String str) {
        return SemWallpaperResourcesUtils.getOMCWallpaperFile(context, i, str);
    }

    public static File getCSCWallpaperFile(Context context, int i, SubUserWallpaperChecker subUserWallpaperChecker, String str) {
        return SemWallpaperResourcesUtils.getCSCWallpaperFile(context, i, str);
    }

    public static int getDisplayId(Context context, int i) {
        try {
            return getInstance(context).getIWallpaperManager().getDisplayId(i);
        } catch (RemoteException e) {
            Log.d(TAG, "getDisplayId:" + e);
            return 0;
        }
    }

    public static boolean isVirtualWallpaperDisplay(Context context, int i) {
        try {
            return getInstance(context).getIWallpaperManager().isVirtualWallpaperDisplay(i);
        } catch (RemoteException e) {
            Log.d(TAG, "isVirtualDisplay:" + e);
            return false;
        }
    }

    public static String getDeviceColor(Context context) {
        try {
            return getInstance(context).getIWallpaperManager().getDeviceColor();
        } catch (RemoteException e) {
            Log.d(TAG, "getDeviceColor:" + e);
            return "";
        }
    }

    public static String getLegacyDeviceColor(Context context) {
        try {
            return getInstance(context).getIWallpaperManager().getLegacyDeviceColor();
        } catch (RemoteException e) {
            Log.d(TAG, "getLegacyDeviceColor:" + e);
            return "";
        }
    }

    public static String getLastCallingPackage(Context context, int i) {
        try {
            return getInstance(context).getIWallpaperManager().getLastCallingPackage(i);
        } catch (RemoteException e) {
            Log.d(TAG, "getLastCallingPackage:" + e);
            return "";
        }
    }

    public static String getLastCallingPackage(Context context, int i, boolean z) {
        try {
            return getInstance(context).getIWallpaperManager().getLastCallingPackageWithPrefix(i, z);
        } catch (RemoteException e) {
            Log.d(TAG, "getLastCallingPackageWithPrefix:" + e);
            return "";
        }
    }

    private static InputStream getWallpaperInputStream(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(str);
        if (!file.exists()) {
            return null;
        }
        try {
            return new FileInputStream(file);
        } catch (IOException unused) {
            return null;
        }
    }

    private static List<String> getDefaultSystemWallpaperPaths() {
        return List.of(SystemProperties.get(PROP_WALLPAPER), getCmfWallpaperPath());
    }

    private static String getCmfWallpaperPath() {
        return Environment.getProductDirectory() + "/wallpaper/image/default_wallpaper_" + VALUE_CMF_COLOR;
    }

    public static ComponentName getDefaultWallpaperComponent(Context context) {
        String str = SystemProperties.get(PROP_WALLPAPER_COMPONENT);
        ComponentName unflattenFromString = !TextUtils.isEmpty(str) ? ComponentName.unflattenFromString(str) : null;
        if (unflattenFromString == null) {
            String string = context.getString(R.string.default_wallpaper_component);
            if (!TextUtils.isEmpty(string)) {
                unflattenFromString = ComponentName.unflattenFromString(string);
            }
        }
        if (isComponentExist(context, unflattenFromString)) {
            return unflattenFromString;
        }
        return null;
    }

    public static ComponentName getCmfDefaultWallpaperComponent(Context context) {
        ComponentName componentName;
        String[] split;
        String[] stringArray = context.getResources().getStringArray(R.array.default_wallpaper_component_per_device_color);
        if (stringArray != null && stringArray.length > 0) {
            for (String str : stringArray) {
                if (!TextUtils.isEmpty(str) && (split = str.split(",")) != null && split.length == 2 && VALUE_CMF_COLOR.equals(split[0]) && !TextUtils.isEmpty(split[1])) {
                    componentName = ComponentName.unflattenFromString(split[1]);
                    break;
                }
            }
        }
        componentName = null;
        ComponentName componentName2 = isComponentExist(context, componentName) ? componentName : null;
        return componentName2 == null ? getDefaultWallpaperComponent(context) : componentName2;
    }

    private static boolean isComponentExist(Context context, ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(componentName.getPackageName(), 786432);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public boolean setLockWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.setLockWallpaperCallback(iWallpaperManagerCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setCoverWallpaperCallback(IWallpaperManagerCallback iWallpaperManagerCallback) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.setCoverWallpaperCallback(iWallpaperManagerCallback);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isWallpaperBackupEligible(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperBackupEligible(i, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.e(TAG, "Exception querying wallpaper backup eligibility: " + e.getMessage());
            return false;
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isWallpaperBackupAllowed(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperBackupAllowed(i, this.mContext.getUserId());
        } catch (RemoteException e) {
            Log.e(TAG, "Exception querying wallpaper backup eligibility: " + e.getMessage());
            return false;
        }
    }

    public ColorManagementProxy getColorManagementProxy() {
        return this.mCmProxy;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkExactlyOneWallpaperFlagSet(int i) {
        if (!WhichChecker.isSingleType(i)) {
            throw new IllegalArgumentException("Must specify exactly one kind of wallpaper");
        }
    }

    public static class ColorManagementProxy {
        private final Set<ColorSpace> mSupportedColorSpaces;

        public ColorManagementProxy(Context context) {
            HashSet hashSet = new HashSet();
            this.mSupportedColorSpaces = hashSet;
            Display displayNoVerify = context.getDisplayNoVerify();
            if (displayNoVerify != null) {
                hashSet.addAll(Arrays.asList(displayNoVerify.getSupportedWideColorGamut()));
            }
        }

        public Set<ColorSpace> getSupportedColorSpaces() {
            return this.mSupportedColorSpaces;
        }

        boolean isSupportedColorSpace(ColorSpace colorSpace) {
            if (colorSpace != null) {
                return colorSpace == ColorSpace.get(ColorSpace.Named.SRGB) || getSupportedColorSpaces().contains(colorSpace);
            }
            return false;
        }

        void doColorManagement(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo) {
            if (isSupportedColorSpace(imageInfo.getColorSpace())) {
                return;
            }
            imageDecoder.setTargetColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
            Log.w(WallpaperManager.TAG, "Not supported color space: " + imageInfo.getColorSpace());
        }
    }

    private class WallpaperSetCompletion extends IWallpaperManagerCallback.Stub {
        final CountDownLatch mLatch = new CountDownLatch(1);

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperChanged(int i, int i2, Bundle bundle) throws RemoteException {
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsAnalysisRequested(int i, int i2) throws RemoteException {
        }

        public WallpaperSetCompletion(WallpaperManager wallpaperManager) {
        }

        public void waitForCompletion() {
            try {
                if (this.mLatch.await(30L, TimeUnit.SECONDS)) {
                    Log.d(WallpaperManager.TAG, "Wallpaper set completion.");
                } else {
                    Log.d(WallpaperManager.TAG, "Timeout waiting for wallpaper set completion!");
                }
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperChanged() throws RemoteException {
            this.mLatch.countDown();
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) throws RemoteException {
            WallpaperManager.sGlobals.onWallpaperColorsChanged(wallpaperColors, i, i2);
        }

        @Override // android.app.IWallpaperManagerCallback
        public void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) throws RemoteException {
            WallpaperManager.sGlobals.onSemWallpaperColorsChanged(semWallpaperColors, i, i2);
        }
    }

    public interface OnColorsChangedListener {
        void onColorsChanged(WallpaperColors wallpaperColors, int i);

        default void onColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
            onColorsChanged(wallpaperColors, i);
        }
    }

    public static void startBackupWallpaper(Context context, String str, String str2) {
        startBackupWallpaper(context, 1, str, str2, 0, "", "");
    }

    public static void startBackupWallpaper(Context context, int i, String str, String str2, int i2, String str3, String str4) {
        startBackupWallpaper(context, "", i, str, str2, i2, str3, str4);
    }

    public static void startBackupWallpaper(Context context, String str, int i, String str2, String str3, int i2, String str4, String str5) {
        WallpaperBackupRestoreManager wallpaperBackupRestoreManager = new WallpaperBackupRestoreManager();
        Log.d(TAG, "startBackupWallpaper action=" + str + " which=" + i + " path=" + str2 + " source=" + str3 + " securityLevel=" + i2 + " sessionTime=" + str4);
        wallpaperBackupRestoreManager.startBackupWallpaper(context, str, i, str2, str3, i2, str4, str5);
    }

    public static void startRestoreWallpaper(Context context, String str, String str2) {
        startRestoreWallpaper(context, 1, str, str2, 0, "", null);
    }

    public static void startRestoreWallpaper(Context context, int i, String str, String str2, int i2, String str3, String str4) {
        startRestoreWallpaper(context, "", i, str, str2, i2, str3, str4);
    }

    public static void startRestoreWallpaper(Context context, String str, int i, String str2, String str3, int i2, String str4, String str5) {
        WallpaperBackupRestoreManager wallpaperBackupRestoreManager = new WallpaperBackupRestoreManager();
        Log.d(TAG, "startRestoreWallpaper action=" + str + " which=" + i + " path=" + str2 + " source=" + str3 + " securityLevel=" + i2 + " restoreScreen=" + str5);
        wallpaperBackupRestoreManager.startRestoreWallpaper(context, str, i, str2, str3, i2, str4, str5);
    }

    @Override // android.app.SemWallpaperManager
    public void clearAll() throws IOException {
        Log.d(TAG, "clearAll");
        clear();
    }

    @Override // android.app.SemWallpaperManager
    public void setResourceAll(int i) throws IOException {
        Log.d(TAG, "setResourceAll");
        Bitmap generateBitmap = generateBitmap(i);
        if (generateBitmap != null) {
            setBitmap(generateBitmap);
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x00c1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.Bitmap generateBitmap(int r11) {
        /*
            r10 = this;
            java.lang.String r0 = "Package name "
            java.lang.String r1 = "themeResourceId="
            java.lang.String r2 = "resourceName="
            java.lang.String r3 = android.app.WallpaperManager.TAG
            java.lang.String r4 = "generateBitmap"
            android.util.Log.d(r3, r4)
            r3 = 0
            android.content.Context r4 = r10.mContext     // Catch: java.lang.OutOfMemoryError -> Lc8
            android.content.res.Resources r4 = r4.getResources()     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.String r11 = r4.getResourceName(r11)     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.String r4 = android.app.WallpaperManager.TAG     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.OutOfMemoryError -> Lc8
            r5.<init>(r2)     // Catch: java.lang.OutOfMemoryError -> Lc8
            r5.append(r11)     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.String r2 = r5.toString()     // Catch: java.lang.OutOfMemoryError -> Lc8
            android.util.Log.d(r4, r2)     // Catch: java.lang.OutOfMemoryError -> Lc8
            boolean r2 = android.text.TextUtils.isEmpty(r11)     // Catch: java.lang.OutOfMemoryError -> Lc8
            r4 = -1
            if (r2 != 0) goto Lad
            r2 = 58
            int r2 = r11.indexOf(r2)     // Catch: java.lang.OutOfMemoryError -> Lc8
            if (r2 <= 0) goto L40
            r5 = 0
            java.lang.String r5 = r11.substring(r5, r2)     // Catch: java.lang.OutOfMemoryError -> Lc8
            goto L41
        L40:
            r5 = r3
        L41:
            r6 = 47
            int r6 = r11.lastIndexOf(r6)     // Catch: java.lang.OutOfMemoryError -> Lc8
            if (r6 <= 0) goto L50
            int r7 = r6 + 1
            java.lang.String r7 = r11.substring(r7)     // Catch: java.lang.OutOfMemoryError -> Lc8
            goto L51
        L50:
            r7 = r3
        L51:
            if (r2 <= 0) goto L60
            if (r6 <= 0) goto L60
            int r8 = r6 - r2
            r9 = 1
            if (r8 <= r9) goto L60
            int r2 = r2 + r9
            java.lang.String r2 = r11.substring(r2, r6)     // Catch: java.lang.OutOfMemoryError -> Lc8
            goto L61
        L60:
            r2 = r3
        L61:
            if (r5 == 0) goto Lad
            if (r7 == 0) goto Lad
            if (r2 == 0) goto Lad
            android.content.APKContents r2 = new android.content.APKContents     // Catch: android.content.res.Resources.NotFoundException -> L8c android.content.pm.PackageManager.NameNotFoundException -> L95 java.lang.OutOfMemoryError -> Lc8
            java.lang.String r6 = android.content.APKContents.getMainThemePackagePath(r5)     // Catch: android.content.res.Resources.NotFoundException -> L8c android.content.pm.PackageManager.NameNotFoundException -> L95 java.lang.OutOfMemoryError -> Lc8
            r2.<init>(r6)     // Catch: android.content.res.Resources.NotFoundException -> L8c android.content.pm.PackageManager.NameNotFoundException -> L95 java.lang.OutOfMemoryError -> Lc8
            android.content.res.Resources r2 = r2.getResources()     // Catch: android.content.res.Resources.NotFoundException -> L8c android.content.pm.PackageManager.NameNotFoundException -> L95 java.lang.OutOfMemoryError -> Lc8
            if (r2 == 0) goto L7c
            int r10 = r2.getIdentifier(r11, r3, r3)     // Catch: android.content.res.Resources.NotFoundException -> L8d android.content.pm.PackageManager.NameNotFoundException -> L96 java.lang.OutOfMemoryError -> Lc8
        L7a:
            r4 = r10
            goto Lae
        L7c:
            android.content.Context r10 = r10.mContext     // Catch: android.content.res.Resources.NotFoundException -> L8d android.content.pm.PackageManager.NameNotFoundException -> L96 java.lang.OutOfMemoryError -> Lc8
            r6 = 4
            android.content.Context r10 = r10.createPackageContext(r5, r6)     // Catch: android.content.res.Resources.NotFoundException -> L8d android.content.pm.PackageManager.NameNotFoundException -> L96 java.lang.OutOfMemoryError -> Lc8
            android.content.res.Resources r2 = r10.getResources()     // Catch: android.content.res.Resources.NotFoundException -> L8d android.content.pm.PackageManager.NameNotFoundException -> L96 java.lang.OutOfMemoryError -> Lc8
            int r10 = r2.getIdentifier(r11, r3, r3)     // Catch: android.content.res.Resources.NotFoundException -> L8d android.content.pm.PackageManager.NameNotFoundException -> L96 java.lang.OutOfMemoryError -> Lc8
            goto L7a
        L8c:
            r2 = r3
        L8d:
            java.lang.String r10 = android.app.WallpaperManager.TAG     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.String r11 = "Resource not found: -1"
            android.util.Log.e(r10, r11)     // Catch: java.lang.OutOfMemoryError -> Lc8
            goto Lae
        L95:
            r2 = r3
        L96:
            java.lang.String r10 = android.app.WallpaperManager.TAG     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.OutOfMemoryError -> Lc8
            r11.<init>(r0)     // Catch: java.lang.OutOfMemoryError -> Lc8
            r11.append(r5)     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.String r0 = " not found"
            r11.append(r0)     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.String r11 = r11.toString()     // Catch: java.lang.OutOfMemoryError -> Lc8
            android.util.Log.e(r10, r11)     // Catch: java.lang.OutOfMemoryError -> Lc8
            goto Lae
        Lad:
            r2 = r3
        Lae:
            java.lang.String r10 = android.app.WallpaperManager.TAG     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.OutOfMemoryError -> Lc8
            r11.<init>(r1)     // Catch: java.lang.OutOfMemoryError -> Lc8
            r11.append(r4)     // Catch: java.lang.OutOfMemoryError -> Lc8
            java.lang.String r11 = r11.toString()     // Catch: java.lang.OutOfMemoryError -> Lc8
            android.util.Log.d(r10, r11)     // Catch: java.lang.OutOfMemoryError -> Lc8
            if (r2 == 0) goto Ld0
            if (r4 <= 0) goto Ld0
            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeResource(r2, r4)     // Catch: java.lang.OutOfMemoryError -> Lc8
            goto Ld0
        Lc8:
            r10 = move-exception
            java.lang.String r11 = android.app.WallpaperManager.TAG
            java.lang.String r0 = "Can't decode file"
            android.util.Log.w(r11, r0, r10)
        Ld0:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.generateBitmap(int):android.graphics.Bitmap");
    }

    @Override // android.app.SemWallpaperManager
    public int getLockWallpaperType() {
        return semGetWallpaperType(2);
    }

    @Override // android.app.SemWallpaperManager
    public int semGetWallpaperType(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        if (i <= 0 || WhichChecker.getType(i) == 0) {
            throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
        }
        try {
            return sGlobals.mService.semGetWallpaperType(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public ComponentName semGetWallpaperComponent(int i, int i2) {
        checkExactlyOneWallpaperFlagSet(i);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semGetWallpaperComponent(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean semIsPreloadedWallpaper(int i, int i2) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semIsPreloadedWallpaper(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isSystemAndLockPaired(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isSystemAndLockPaired(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getHighlightFilterState(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getHighlightFilterState(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setWallpaperComponentWithExtras(int i, ComponentName componentName, String str, int i2, Bundle bundle) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            resetMultipleWallpaperSettingIfNeeded();
            sGlobals.mService.setWallpaperComponentChecked(new WallpaperDescription.Builder().setComponent(componentName).build(), str, i, i2, bundle);
            if (!isNeedToClearBackupData()) {
                return true;
            }
            semClearBackupWallpapers(i);
            return true;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public Bundle getWallpaperComponentExtras(int i, int i2) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperComponentExtras(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public Bundle getWallpaperExtras(int i, int i2) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperExtras(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public Bundle getWallpaperAssets(int i, int i2) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperAssets(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public ParcelFileDescriptor getWallpaperAssetFile(int i, int i2, String str) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperAssetFile(this.mContext.getOpPackageName(), i, i2, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public int getWallpaperOrientation(int i, int i2) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getWallpaperOrientation(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isDefaultWallpaperState(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isDefaultWallpaperState(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public Rect semGetWallpaperCropHint(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.semGetWallpaperCropHint(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String str) {
        setVideoLockscreenWallpaper(str, null);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String str, String str2) {
        setVideoLockscreenWallpaper(str, str2, null, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String str, String str2, String str3, int i) {
        setVideoLockscreenWallpaper(str, str2, str3, UserHandle.getCallingUserId(), i, true);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String str, String str2, String str3, int i, boolean z) {
        setVideoWallpaper(str, str2, str3, UserHandle.getCallingUserId(), i, true, z, null);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String str, String str2, String str3, int i, int i2) {
        setVideoLockscreenWallpaper(str, str2, str3, i, i2, true);
    }

    @Override // android.app.SemWallpaperManager
    public void setVideoLockscreenWallpaper(String str, String str2, String str3, int i, int i2, boolean z) {
        setVideoWallpaper(str, str2, str3, i, i2, z, false, null);
    }

    public void setVideoWallpaper(String str, String str2, String str3, int i, int i2, Bundle bundle) {
        setVideoWallpaper(str, str2, str3, i, i2, false, true, bundle);
    }

    public void setVideoWallpaper(String str, String str2, String str3, int i, int i2, boolean z, boolean z2, Bundle bundle) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "setVideoWallpaper: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setVideoWallpaper(str, str2, str3, this.mContext.getOpPackageName(), i, i2, z2, bundle);
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(i2);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoFilePath(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getVideoFilePath(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoPackage() {
        return getVideoPackage(2);
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoPackage(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getVideoPackage(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getVideoFileName(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getVideoFileName(i);
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
            return sGlobals.mService.hasVideoWallpaper();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private Bitmap getVideoWallpaperFrame(AssetFileDescriptor assetFileDescriptor, String str, String str2) {
        long j;
        Log.d(TAG, "getVideoWallpaperFrame, creating MediaMetadataRetriever");
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        Bitmap bitmap = null;
        try {
            try {
                try {
                    if (!TextUtils.isEmpty(str)) {
                        mediaMetadataRetriever.setDataSource(str);
                    } else {
                        if (assetFileDescriptor == null) {
                            Log.w(TAG, "getVideoWallpaperFrame() file is invalid");
                            try {
                                mediaMetadataRetriever.release();
                                return null;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                        mediaMetadataRetriever.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
                    }
                    if (TextUtils.isEmpty(str2)) {
                        bitmap = mediaMetadataRetriever.getFrameAtTime(0L);
                        j = 0;
                    } else {
                        String extractMetadata = mediaMetadataRetriever.extractMetadata(32);
                        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(9);
                        if (!TextUtils.isEmpty(extractMetadata) && !TextUtils.isEmpty(extractMetadata2)) {
                            int parseInt = Integer.parseInt(extractMetadata);
                            int parseInt2 = Integer.parseInt(extractMetadata2);
                            int defaultVideoFrameInfo = sWallpaperResourcesInfo.getDefaultVideoFrameInfo(str2);
                            if (parseInt > 0 && defaultVideoFrameInfo > 0 && parseInt >= defaultVideoFrameInfo) {
                                j = (int) (parseInt2 * 1000 * (defaultVideoFrameInfo / parseInt));
                                MediaMetadataRetriever.BitmapParams bitmapParams = new MediaMetadataRetriever.BitmapParams();
                                bitmapParams.setPreferredConfig(Bitmap.Config.ARGB_8888);
                                bitmap = mediaMetadataRetriever.getFrameAtTime(j, 2, bitmapParams);
                            }
                        }
                        j = 0;
                        MediaMetadataRetriever.BitmapParams bitmapParams2 = new MediaMetadataRetriever.BitmapParams();
                        bitmapParams2.setPreferredConfig(Bitmap.Config.ARGB_8888);
                        bitmap = mediaMetadataRetriever.getFrameAtTime(j, 2, bitmapParams2);
                    }
                    Log.d(TAG, "getVideoWallpaperFrame " + j);
                    mediaMetadataRetriever.release();
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                    bitmap = mediaMetadataRetriever.getFrameAtTime(0L);
                    mediaMetadataRetriever.release();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    mediaMetadataRetriever.release();
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            Log.d(TAG, "getVideoWallpaperFrame, done");
            return bitmap;
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
                throw th;
            } catch (Exception e5) {
                e5.printStackTrace();
                throw th;
            }
        }
    }

    private void checkPermission(String[] strArr) {
        int myUid = Process.myUid();
        int myPid = Process.myPid();
        boolean z = false;
        for (String str : strArr) {
            if (this.mContext.checkPermission(str, myPid, myUid) == 0) {
                z = true;
            }
        }
        String opPackageName = this.mContext.getOpPackageName();
        if (z) {
            return;
        }
        ((StorageManager) this.mContext.getSystemService(StorageManager.class)).checkPermissionReadImages(true, myPid, myUid, opPackageName, null);
    }

    private boolean canPeekWallpaper(int i) {
        boolean isSystemAndLockPaired = isSystemAndLockPaired(i);
        if (!WhichChecker.isLock(i) || !isSystemAndLockPaired) {
            return true;
        }
        Log.w(TAG, "canPeekWallpaper failed, which = " + i);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private android.graphics.Bitmap getThemeWallpaperBackground(java.lang.String r5) {
        /*
            r4 = this;
            r0 = 0
            android.content.APKContents r1 = new android.content.APKContents     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            java.lang.String r2 = android.content.APKContents.getMainThemePackagePath(r5)     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            r1.<init>(r2)     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            if (r1 != 0) goto L1e
            android.content.Context r1 = r4.mContext     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            android.content.res.Resources r1 = r1.getResourcesForApplication(r5)     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            android.content.res.AssetManager r1 = r1.getAssets()     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
        L1e:
            if (r1 == 0) goto L28
            java.lang.String r2 = "preview/thumbnail_wallpaper.jpg"
            java.io.InputStream r1 = r1.open(r2)     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            goto L40
        L28:
            java.lang.String r1 = android.app.WallpaperManager.TAG     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            java.lang.String r2 = "getAnimatedWallpaperBackground() : Wallpaper pkg, AssetManager is null"
            android.util.Log.e(r1, r2)     // Catch: java.lang.Exception -> L30 java.io.IOException -> L38
            return r0
        L30:
            java.lang.String r1 = android.app.WallpaperManager.TAG
            java.lang.String r2 = "getThemeWallpaperBackground Exception"
            android.util.Log.e(r1, r2)
            goto L3f
        L38:
            java.lang.String r1 = android.app.WallpaperManager.TAG
            java.lang.String r2 = "getThemeWallpaperBackground IOException"
            android.util.Log.e(r1, r2)
        L3f:
            r1 = r0
        L40:
            if (r1 != 0) goto L7d
            java.lang.String r2 = ".wallpaper"
            java.lang.String r3 = ""
            java.lang.String r5 = r5.replace(r2, r3)
            android.content.APKContents r2 = new android.content.APKContents     // Catch: java.lang.Exception -> L79
            java.lang.String r3 = android.content.APKContents.getMainThemePackagePath(r5)     // Catch: java.lang.Exception -> L79
            r2.<init>(r3)     // Catch: java.lang.Exception -> L79
            android.content.res.AssetManager r2 = r2.getAssets()     // Catch: java.lang.Exception -> L79
            if (r2 != 0) goto L67
            android.content.Context r4 = r4.mContext     // Catch: java.lang.Exception -> L79
            android.content.pm.PackageManager r4 = r4.getPackageManager()     // Catch: java.lang.Exception -> L79
            android.content.res.Resources r4 = r4.getResourcesForApplication(r5)     // Catch: java.lang.Exception -> L79
            android.content.res.AssetManager r2 = r4.getAssets()     // Catch: java.lang.Exception -> L79
        L67:
            if (r2 == 0) goto L71
            java.lang.String r4 = "preview/theme_lockscreen.jpg"
            java.io.InputStream r1 = r2.open(r4)     // Catch: java.lang.Exception -> L79
            goto L7d
        L71:
            java.lang.String r4 = android.app.WallpaperManager.TAG     // Catch: java.lang.Exception -> L79
            java.lang.String r5 = "getAnimatedWallpaperBackground() : Theme pkg, AssetManager is null"
            android.util.Log.e(r4, r5)     // Catch: java.lang.Exception -> L79
            return r0
        L79:
            r4 = move-exception
            r4.printStackTrace()
        L7d:
            if (r1 == 0) goto L83
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r1)
        L83:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.WallpaperManager.getThemeWallpaperBackground(java.lang.String):android.graphics.Bitmap");
    }

    private AssetFileDescriptor getVideoFDFromPackage(String str, String str2) {
        Context context;
        Resources resources;
        AssetManager assets;
        Log.d(TAG, "getVideoFDFromPackage() pkgName = " + str + " , fileName = " + str2);
        try {
            context = this.mContext.createPackageContext(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            context = null;
        }
        if (context == null) {
            Log.e(TAG, "getVideoFDFromPackage() otherContext is null");
            APKContents aPKContents = new APKContents(APKContents.getMainThemePackagePath(str));
            resources = aPKContents.getResources();
            assets = aPKContents.getAssets();
            if (resources == null || assets == null) {
                return null;
            }
        } else {
            resources = context.getResources();
            assets = context.getAssets();
        }
        if (WALLPAPER_PACKAGE.equals(str)) {
            if (TextUtils.isEmpty(str2)) {
                return null;
            }
            try {
                return resources.openRawResourceFd(resources.getIdentifier(str2.substring(0, str2.lastIndexOf(46)), "raw", str));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            if (assets == null) {
                Log.e(TAG, "getVideoFDFromPackage() assetManager is null");
                return null;
            }
            try {
                return assets.openFd(THEME_VIDEO_RES_ID);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return null;
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String str) {
        Log.i(TAG, "setMotionWallpaper: packageName = " + str);
        setMotionWallpaper(str, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String str, int i) {
        Log.i(TAG, "setMotionWallpaper: packageName = " + str + ", which = " + i);
        setMotionWallpaper(str, 2, false);
    }

    @Override // android.app.SemWallpaperManager
    public void setMotionWallpaper(String str, int i, boolean z) {
        Log.i(TAG, "setMotionWallpaper: packageName = " + str + ", which = " + i + ", allowBackup = " + z);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setMotionWallpaper(str, this.mContext.getOpPackageName(), i, z);
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(i);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getMotionWallpaperPkgName(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getMotionWallpaperPkgName(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String str) throws IOException {
        Log.i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + str);
        setAnimatedLockscreenWallpaper(str, 2);
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String str, int i) throws IOException {
        Log.i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + str + ", which = " + i);
        setAnimatedLockscreenWallpaper(str, i, false);
    }

    @Override // android.app.SemWallpaperManager
    public void setAnimatedLockscreenWallpaper(String str, int i, boolean z) throws IOException {
        Log.i(TAG, "setAnimatedLockscreenWallpaper: packageName = " + str + ", which = " + i + ", allowBackup = " + z);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.setAnimatedWallpaper(str, this.mContext.getOpPackageName(), i, z);
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(i);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public String getAnimatedPkgName(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getAnimatedPkgName(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean isNeedToClearBackupData() {
        boolean z = false;
        if (isSnapshotTestMode()) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mContext.getApplicationInfo().packageName);
        arrayList.add(this.mContext.getOpPackageName());
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            }
            String str = (String) it.next();
            Log.d(TAG, "isNeedToClearBackupData(), pkgName = " + str);
            if ("com.samsung.android.themecenter".equals(str) || PACKAGE_NAME_LOCKSTAR.equals(str) || PACKAGE_NAME_FESTIVAL_WALLPAPER.equals(str) || ((Build.VERSION.SEM_PLATFORM_INT >= 140100 && (PACKAGE_NAME_DRESSROOM.equals(str) || "com.android.systemui".equals(str))) || ((Build.VERSION.SEM_PLATFORM_INT >= ONEUI_6_1 && "com.sec.android.emergencylauncher".equals(str)) || (Build.VERSION.SEM_PLATFORM_INT > ONEUI_6_1 && PACKAGE_NAME_DYNAMIC_LOCKSCREEN.equals(str))))) {
                break;
            }
        }
        Log.d(TAG, "needClear = " + z);
        return z;
    }

    private boolean checkWhichInvalidation(int i) {
        if (WhichChecker.isSystem(i) || WhichChecker.isLock(i) || WhichChecker.isSystemAndLock(i)) {
            return WhichChecker.isPhone(i) || WhichChecker.isDex(i) || WhichChecker.isSubDisplay(i) || WhichChecker.isVirtualDisplay(i);
        }
        return false;
    }

    @Override // android.app.SemWallpaperManager
    public int semMakeBackupWallpaper() {
        return semMakeBackupWallpaper(3);
    }

    @Override // android.app.SemWallpaperManager
    public int semMakeBackupWallpaper(int i) {
        return semMakeBackupWallpaper(i, -1);
    }

    @Override // android.app.SemWallpaperManager
    public int semMakeBackupWallpaper(int i, int i2) {
        Log.d(TAG, "semMakeBackupWallpaper: which = " + i + ", key = " + i2);
        return semMakeBackupWallpaperWithExtras(i, i2, null);
    }

    public int semMakeBackupWallpaperWithExtras(int i, int i2, Bundle bundle) {
        Log.d(TAG, "semMakeBackupWallpaper: which = " + i + ", key = " + i2 + ", extras = " + bundle);
        if (Build.VERSION.SEM_PLATFORM_INT >= 140100 && (i <= 0 || WhichChecker.getType(i) == 0)) {
            throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
        }
        if (!checkWhichInvalidation(i)) {
            Log.e(TAG, "semMakeBackupWallpaper: Invalid which. which = " + i);
            return -1;
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "semMakeBackupWallpaper: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.makeSnapshot(i, i2, bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void semClearBackupWallpapers() {
        semClearBackupWallpapers(3);
    }

    @Override // android.app.SemWallpaperManager
    public void semClearBackupWallpapers(int i) {
        Log.d(TAG, "semClearBackupWallpapers: which = " + i);
        if (Build.VERSION.SEM_PLATFORM_INT >= 140100) {
            if (i <= 0 || WhichChecker.getType(i) == 0) {
                throw new IllegalArgumentException("'which' SHOULD be a combination of FLAG_SYSTEM and FLAG_LOCK.");
            }
        } else if (i <= 0) {
            Log.e(TAG, "semClearBackupWallpapers: Invalid which.");
            return;
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.removeSnapshotByWhich(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void semClearBackupWallpapers(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "semClearBackupWallpapers: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.removeSnapshotBySource(str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public void clearBackupWallpaperGivenKey(int i) {
        Log.d(TAG, "clearBackupWallpaperGivenKey: key = " + i);
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.removeSnapshotByKey(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean semRestoreBackupWallpaper(int i) {
        Log.d(TAG, "semRestoreBackupWallpaper: key = " + i);
        if (i <= 0) {
            Log.e(TAG, "invalid key");
            return false;
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.restoreSnapshot(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSnapshotTestMode() {
        if (Rune.isShipBuild()) {
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

    public void setSnapshotTestMode(boolean z) {
        if (Rune.isShipBuild()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mContext.getApplicationInfo().packageName);
        arrayList.add(this.mContext.getOpPackageName());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (PACKAGE_NAME_DRESSROOM.equals((String) it.next())) {
                if (sGlobals.mService == null) {
                    Log.w(TAG, "setSnapshotTestMode: WallpaperService not running");
                    throw new RuntimeException(new DeadSystemException());
                }
                try {
                    sGlobals.mService.setSnapshotTestMode(z);
                    return;
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public int getSnapshotCount() {
        return getSnapshotCount(-1);
    }

    public int getSnapshotCount(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "getSnapshotCount: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getSnapshotCount(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setSnapshotSource(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Parameter 'source' cannot be null.");
        }
        if (sGlobals.mService == null) {
            Log.w(TAG, "setSnapshotSource: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.setSnapshotSource(i, str);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isValidSnapshot(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "isValidSnapshot: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isValidSnapshot(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int[] getSnapshotKeys(String str, int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "getSnapshotKeys: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getSnapshotKeys(str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    public boolean isStockLiveWallpaper(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "isStockLiveWallpaper: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isStockLiveWallpaper(i, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStockLiveWallpaperPackage(String str) {
        return PACKAGE_NAME_SPRITE.equals(str);
    }

    @Override // android.app.SemWallpaperManager
    public int getLidState() {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.getLidState();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPid(int i, int i2, String str, boolean z) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            sGlobals.mService.notifyPid(i, i2, str, z);
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
        int intForUser;
        int intForUser2;
        int i = z ? 16 : 4;
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int i2 = i | (isApplied(z ? 17 : 1, str) ? 1 : 0) | (isApplied(z ? 18 : 2, str) ? 2 : 0);
        if (WhichChecker.containsSystem(i2) && isSystemAndLockPaired(WhichChecker.getMode(i2))) {
            i2 |= 2;
        }
        Log.i(TAG, "getAppliedScreen: " + str + ", " + i2);
        int mode = WhichChecker.getMode(i2);
        if (WhichChecker.containsSystem(i2) && (intForUser2 = Settings.System.getIntForUser(this.mContext.getContentResolver(), getSettingsName(mode | 1), 1, -2)) != 2 && intForUser2 != 3) {
            i2 &= -2;
        }
        return (!WhichChecker.containsLock(i2) || (intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), getSettingsName(mode | 2), 1, -2)) == 2 || intForUser == 3) ? i2 : i2 & (-3);
    }

    private boolean isApplied(int i, String str) {
        Uri semGetUri = semGetUri(i);
        if (semGetUri != null) {
            String uri = semGetUri.toString();
            Log.i(TAG, "isApplied: uri = " + uri);
            if (!TextUtils.isEmpty(uri) && uri.contains(str)) {
                return true;
            }
        }
        if ((i & 2) == 0) {
            return false;
        }
        String videoPackage = getVideoPackage(i);
        Log.i(TAG, "isApplied: videoPkg = " + videoPackage);
        if (!TextUtils.isEmpty(videoPackage) && videoPackage.contains(str)) {
            return true;
        }
        String motionWallpaperPkgName = getMotionWallpaperPkgName(i);
        Log.i(TAG, "isApplied: motionPkg = " + motionWallpaperPkgName);
        if (!TextUtils.isEmpty(motionWallpaperPkgName) && motionWallpaperPkgName.contains(str)) {
            return true;
        }
        String animatedPkgName = getAnimatedPkgName(i);
        Log.i(TAG, "isApplied: animatedPkg = " + animatedPkgName);
        return !TextUtils.isEmpty(animatedPkgName) && animatedPkgName.contains(str);
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int i) {
        return SemWallpaperResourcesUtils.isDefaultOperatorWallpaper(context, i, null);
    }

    public static boolean isDefaultOperatorWallpaper(Context context, int i, String str) {
        return SemWallpaperResourcesUtils.isDefaultOperatorWallpaper(context, i, str);
    }

    @Override // android.app.SemWallpaperManager
    public void addOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener, Handler handler) {
        addOnSemColorsChangedListener(onSemColorsChangedListener, handler, this.mContext.getUserId());
    }

    @Override // android.app.SemWallpaperManager
    public void addOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener, Handler handler, int i) {
        sGlobals.addOnSemColorsChangedListener(onSemColorsChangedListener, handler, i, this.mContext.getDisplayId());
    }

    @Override // android.app.SemWallpaperManager
    public void removeOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener) {
        removeOnSemColorsChangedListener(onSemColorsChangedListener, this.mContext.getUserId());
    }

    @Override // android.app.SemWallpaperManager
    public void removeOnSemColorsChangedListener(OnSemColorsChangedListener onSemColorsChangedListener, int i) {
        sGlobals.removeOnSemColorsChangedListener(onSemColorsChangedListener, i, this.mContext.getDisplayId());
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(int[] iArr) {
        return getColorPalettes(iArr, false);
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(int[] iArr, boolean z) {
        if (iArr == null || iArr.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (z) {
            int[] converAccent1ToSeedColors = ColorPaletteCreator.converAccent1ToSeedColors(iArr);
            if (converAccent1ToSeedColors != null && converAccent1ToSeedColors.length > 0) {
                for (int i : converAccent1ToSeedColors) {
                    arrayList.add(new ColorPalette(new ColorScheme(i, false)).getTable());
                }
            }
        } else {
            ColorPaletteCreator colorPaletteCreator = new ColorPaletteCreator();
            colorPaletteCreator.setColors(iArr);
            colorPaletteCreator.generateColorPalette();
            List<int[][]> colorPalettes = colorPaletteCreator.getColorPalettes();
            if (colorPalettes != null && colorPalettes.size() > 0) {
                for (int i2 = 0; i2 < colorPalettes.size(); i2++) {
                    arrayList.add(colorPalettes.get(i2));
                }
            }
        }
        return arrayList;
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(Bitmap bitmap) {
        return getColorPalettes(bitmap, false);
    }

    @Override // android.app.SemWallpaperManager
    public List<int[][]> getColorPalettes(Bitmap bitmap, boolean z) {
        int[] seedColors = getSeedColors(bitmap, z);
        if (seedColors == null || seedColors.length <= 0) {
            return null;
        }
        return getColorPalettes(seedColors, z);
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(int i) {
        return getSeedColors(i, false);
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(int i, boolean z) {
        Log.d(TAG, "getSeedColors: which = " + i + ", fromGoogle = " + z);
        SemWallpaperColors semGetWallpaperColors = semGetWallpaperColors(i);
        int[] seedColors = semGetWallpaperColors != null ? semGetWallpaperColors.getSeedColors() : null;
        return z ? ColorPaletteCreator.converAccent1ToSeedColors(seedColors) : seedColors;
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(Bitmap bitmap) {
        return getSeedColors(bitmap, false);
    }

    @Override // android.app.SemWallpaperManager
    public int[] getSeedColors(Bitmap bitmap, boolean z) {
        Log.d(TAG, "getSeedColors: bitmap = " + bitmap + ", fromGoogle = " + z);
        int[] seedColors = ColorThemeExtractor.getSeedColors(bitmap);
        return z ? ColorPaletteCreator.converAccent1ToSeedColors(seedColors) : seedColors;
    }

    @Override // android.app.SemWallpaperManager
    public boolean canBackup() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(5);
        arrayList.add(6);
        if (Rune.SUPPORT_SUB_DISPLAY_MODE) {
            arrayList.add(17);
            if (!Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
                arrayList.add(18);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (canBackup(((Integer) it.next()).intValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // android.app.SemWallpaperManager
    public boolean canBackup(int i) {
        Uri semGetUri;
        int semGetWallpaperType = semGetWallpaperType(i);
        boolean z = true;
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), getSettingsName(i), 1, -2);
        boolean z2 = false;
        boolean z3 = ((intForUser == 0) || (intForUser == 3)) && isWallpaperBackupAllowed(i);
        if (semGetWallpaperType == 3 && !z3 && (semGetUri = semGetUri(i)) != null) {
            String uri = semGetUri.toString();
            if (!TextUtils.isEmpty(uri) && uri.startsWith(BnRConstants.CUSTOM_PACK_PREFIX)) {
                z3 = true;
            }
        }
        if (z3 && semGetWallpaperType == 7 && !isStockLiveWallpaper(i)) {
            Log.d(TAG, "canBackup: which = " + i + ", external live wallpaper");
        } else {
            z2 = z3;
        }
        if (Build.VERSION.SEM_PLATFORM_INT < 160000 || z2 || semGetWallpaperType != 1000) {
            z = z2;
        } else {
            Log.d(TAG, "canBackup: which = " + i + ", Dynamic Lockscreen");
        }
        Log.d(TAG, "canBackup: which = " + i + " canBackup = " + z);
        return z;
    }

    private String getSettingsName(int i) {
        int mode = WhichChecker.getMode(i);
        int type = WhichChecker.getType(i);
        if (mode == 4) {
            if (type == 2) {
                return "lockscreen_wallpaper_transparent";
            }
            return "android.wallpaper.settings_systemui_transparency";
        }
        if (mode == 8) {
            if (type == 2) {
                return BnRConstants.SETTINGS_KEYGUARD_TRANSPARENCY_DEX;
            }
            return BnRConstants.SETTINGS_SYSTEM_TRANSPARENCY_DEX;
        }
        if (mode != 16) {
            return "";
        }
        if (type == 2) {
            return "sub_display_lockscreen_wallpaper_transparency";
        }
        return "sub_display_system_wallpaper_transparency";
    }

    @Override // android.app.SemWallpaperManager
    public boolean isWallpaperDataExists(int i) {
        if (sGlobals.mService == null) {
            Log.w(TAG, "isWallpaperDataExist: WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        try {
            return sGlobals.mService.isWallpaperDataExists(this.mContext.getUserId(), i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
