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
        if (Rune.isMultiFoldable()) {
            if (f > 2.0f) {
                return 1;
            }
            if (f > 1.0f) {
                return 3;
            }
            return f < 0.5f ? 0 : 2;
        }
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
                    this.mColorListeners.add(new Pair<>(onColorsChangedListener, handler));
                } else {
                    this.mColorListeners.add(new Pair<>(onColorsChangedListener, handler));
                }
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
                ArraySet<RectF> arraySetRemove = this.mLocalColorCallbackAreas.remove(localWallpaperColorConsumer);
                if (arraySetRemove != null && arraySetRemove.size() != 0) {
                    for (LocalWallpaperColorConsumer localWallpaperColorConsumer2 : this.mLocalColorCallbackAreas.keySet()) {
                        ArraySet<RectF> arraySet = this.mLocalColorCallbackAreas.get(localWallpaperColorConsumer2);
                        if (arraySet != null && localWallpaperColorConsumer2 != localWallpaperColorConsumer) {
                            arraySetRemove.removeAll((ArraySet<? extends RectF>) arraySet);
                        }
                    }
                    try {
                        if (arraySetRemove.size() > 0) {
                            this.mService.removeOnLocalColorsChangedListener(this.mLocalColorCallback, new ArrayList(arraySetRemove), i, i2, i3);
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
                        return WallpaperManager.Globals.lambda$removeOnColorsChangedListener$0(onColorsChangedListener, (Pair) obj);
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
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) throws Throwable {
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
                                    this.f$0.lambda$onWallpaperColorsChanged$1(next, wallpaperColors2, i3, i4);
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
            boolean zContains;
            synchronized (WallpaperManager.sGlobals) {
                zContains = this.mColorListeners.contains(pair);
            }
            if (zContains) {
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

        /* JADX WARN: Removed duplicated region for block: B:90:0x0159 A[Catch: all -> 0x018c, TRY_ENTER, TryCatch #8 {, blocks: (B:25:0x0069, B:31:0x0075, B:33:0x0079, B:35:0x007f, B:37:0x0087, B:38:0x00b6, B:40:0x00b8, B:42:0x00be, B:51:0x00db, B:52:0x00de, B:59:0x00f2, B:90:0x0159, B:91:0x0160, B:93:0x0162, B:95:0x0166, B:97:0x016c, B:98:0x0170, B:100:0x0172, B:78:0x0127, B:79:0x012a, B:110:0x0188, B:111:0x018b, B:43:0x00c0, B:45:0x00c9, B:47:0x00cd, B:49:0x00d3, B:55:0x00e8, B:56:0x00ec, B:58:0x00f0, B:73:0x0107, B:75:0x0110, B:77:0x0119, B:81:0x012c, B:83:0x0136, B:84:0x0140, B:87:0x0143, B:69:0x0100, B:70:0x0104), top: B:123:0x0069, inners: #6 }] */
        /* JADX WARN: Removed duplicated region for block: B:93:0x0162 A[Catch: all -> 0x018c, TryCatch #8 {, blocks: (B:25:0x0069, B:31:0x0075, B:33:0x0079, B:35:0x007f, B:37:0x0087, B:38:0x00b6, B:40:0x00b8, B:42:0x00be, B:51:0x00db, B:52:0x00de, B:59:0x00f2, B:90:0x0159, B:91:0x0160, B:93:0x0162, B:95:0x0166, B:97:0x016c, B:98:0x0170, B:100:0x0172, B:78:0x0127, B:79:0x012a, B:110:0x0188, B:111:0x018b, B:43:0x00c0, B:45:0x00c9, B:47:0x00cd, B:49:0x00d3, B:55:0x00e8, B:56:0x00ec, B:58:0x00f0, B:73:0x0107, B:75:0x0110, B:77:0x0119, B:81:0x012c, B:83:0x0136, B:84:0x0140, B:87:0x0143, B:69:0x0100, B:70:0x0104), top: B:123:0x0069, inners: #6 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Bitmap peekWallpaperBitmap(Context context, boolean z, int i, int i2, boolean z2, ColorManagementProxy colorManagementProxy, boolean z3) {
            boolean zIsDesktopModeEnabled;
            Bitmap currentWallpaperLocked;
            CachedWallpaper cachedWallpaper;
            Log.d(WallpaperManager.TAG, "peekWallpaperBitmap: which =" + i + ", useCache = " + z3);
            if (this.mService != null) {
                try {
                    try {
                        Trace.beginSection("WPMS.isWallpaperSupported");
                        if (!this.mService.isWallpaperSupported(context.getOpPackageName())) {
                            return null;
                        }
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                } finally {
                }
            }
            if (Rune.SUPPORT_DESKTOP_MODE) {
                try {
                    zIsDesktopModeEnabled = WallpaperManager.sGlobals.mService.isDesktopModeEnabled(i);
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            } else {
                zIsDesktopModeEnabled = false;
            }
            synchronized (this) {
                boolean z4 = this.mIsCachedWallpaperForDeX;
                if (((z4 && zIsDesktopModeEnabled) || (!z4 && !zIsDesktopModeEnabled)) && z3 && (cachedWallpaper = this.mCachedWallpaper) != null && cachedWallpaper.isValid(i2, i) && context.checkSelfPermission(Manifest.permission.READ_WALLPAPER_INTERNAL) == 0) {
                    Log.d(WallpaperManager.TAG, "peekWallpaperBitmap() cached image height=" + this.mCachedWallpaper.mCachedWallpaper.getHeight() + " width=" + this.mCachedWallpaper.mCachedWallpaper.getWidth());
                    return this.mCachedWallpaper.mCachedWallpaper;
                }
                this.mCachedWallpaper = null;
                if (Rune.SUPPORT_DESKTOP_MODE) {
                    this.mIsCachedWallpaperForDeX = false;
                }
                try {
                    try {
                        try {
                            Trace.beginSection("WPMS.getCurrentWallpaperLocked");
                        } catch (RemoteException e3) {
                            throw e3.rethrowFromSystemServer();
                        }
                    } finally {
                    }
                } catch (OutOfMemoryError e4) {
                    e = e4;
                    currentWallpaperLocked = null;
                } catch (SecurityException e5) {
                    e = e5;
                    currentWallpaperLocked = null;
                }
                if (Rune.SUPPORT_COVER_DISPLAY_WATCHFACE && this.mService != null && WhichChecker.isSubDisplay(i) && this.mService.isWaitingForUnlockUser(i, i2)) {
                    return null;
                }
                currentWallpaperLocked = getCurrentWallpaperLocked(context, i, i2, z2, colorManagementProxy);
                try {
                    if (Rune.SUPPORT_DESKTOP_MODE) {
                        this.mIsCachedWallpaperForDeX = zIsDesktopModeEnabled;
                    }
                } catch (OutOfMemoryError e6) {
                    e = e6;
                    Log.w(WallpaperManager.TAG, "Out of memory loading the current wallpaper: " + e);
                    if (currentWallpaperLocked != null) {
                    }
                } catch (SecurityException e7) {
                    e = e7;
                    if (CompatChanges.isChangeEnabled(WallpaperManager.RETURN_DEFAULT_ON_SECURITY_EXCEPTION) && !CompatChanges.isChangeEnabled(WallpaperManager.THROW_ON_SECURITY_EXCEPTION)) {
                        Log.w(WallpaperManager.TAG, "No permission to access wallpaper, returning default wallpaper to avoid crashing legacy app.");
                        return getDefaultWallpaper(context, 1);
                    }
                    if (context.getApplicationInfo().targetSdkVersion >= 27) {
                        throw e;
                    }
                    Log.w(WallpaperManager.TAG, "No permission to access wallpaper, suppressing exception to avoid crashing legacy app.");
                    if (currentWallpaperLocked != null) {
                    }
                }
                if (currentWallpaperLocked != null) {
                    this.mCachedWallpaper = new CachedWallpaper(currentWallpaperLocked, i2, i);
                    return currentWallpaperLocked;
                }
                CachedWallpaper cachedWallpaper2 = this.mCachedWallpaper;
                if (cachedWallpaper2 != null && cachedWallpaper2.isValid(i2, i)) {
                    return this.mCachedWallpaper.mCachedWallpaper;
                }
                if (z || (WhichChecker.isLock(i) && isStaticWallpaper(i))) {
                    return getDefaultWallpaper(context, i);
                }
                return null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:43:0x0084  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x0090 A[ADDED_TO_REGION] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Rect peekWallpaperDimensions(Context context, boolean z, int i, int i2) {
            int i3;
            Rect rect;
            InputStream inputStreamOpenDefaultWallpaper;
            IWallpaperManager iWallpaperManager = this.mService;
            if (iWallpaperManager == null) {
                Log.w(WallpaperManager.TAG, "WallpaperService not running");
                return null;
            }
            try {
                if (!iWallpaperManager.isWallpaperSupported(context.getOpPackageName())) {
                    return new Rect();
                }
                synchronized (this) {
                    try {
                        i3 = i;
                    } catch (RemoteException e) {
                        e = e;
                        i3 = i;
                    } catch (IOException unused) {
                        i3 = i;
                    }
                    try {
                        ParcelFileDescriptor wallpaperWithFeature = this.mService.getWallpaperWithFeature(context.getOpPackageName(), context.getAttributionTag(), this, i3, new Bundle(), i2, true, false, -1);
                        if (wallpaperWithFeature != null) {
                            try {
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inJustDecodeBounds = true;
                                BitmapFactory.decodeFileDescriptor(wallpaperWithFeature.getFileDescriptor(), null, options);
                                rect = new Rect(0, 0, options.outWidth, options.outHeight);
                            } catch (Throwable th) {
                                if (wallpaperWithFeature == null) {
                                    throw th;
                                }
                                try {
                                    wallpaperWithFeature.close();
                                    throw th;
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                    throw th;
                                }
                            }
                        } else {
                            rect = null;
                        }
                        if (wallpaperWithFeature != null) {
                            try {
                                wallpaperWithFeature.close();
                            } catch (RemoteException e2) {
                                e = e2;
                                Log.w(WallpaperManager.TAG, "peek wallpaper dimensions failed", e);
                            } catch (IOException unused2) {
                            }
                        }
                    } catch (RemoteException e3) {
                        e = e3;
                        rect = null;
                        Log.w(WallpaperManager.TAG, "peek wallpaper dimensions failed", e);
                        if (rect == null) {
                            try {
                                BitmapFactory.Options options2 = new BitmapFactory.Options();
                                options2.inJustDecodeBounds = true;
                                BitmapFactory.decodeStream(inputStreamOpenDefaultWallpaper, null, options2);
                                rect = new Rect(0, 0, options2.outWidth, options2.outHeight);
                            } finally {
                                IoUtils.closeQuietly(inputStreamOpenDefaultWallpaper);
                            }
                        } else {
                            BitmapFactory.Options options22 = new BitmapFactory.Options();
                            options22.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(inputStreamOpenDefaultWallpaper, null, options22);
                            rect = new Rect(0, 0, options22.outWidth, options22.outHeight);
                        }
                        return rect;
                    } catch (IOException unused3) {
                        rect = null;
                        if (rect == null) {
                        }
                        return rect;
                    }
                }
                if ((rect == null || rect.width() == 0 || rect.height() == 0) && ((z || (WhichChecker.isLock(i3) && isStaticWallpaper(i3))) && (inputStreamOpenDefaultWallpaper = WallpaperManager.openDefaultWallpaper(context, i3)) != null)) {
                    BitmapFactory.Options options222 = new BitmapFactory.Options();
                    options222.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStreamOpenDefaultWallpaper, null, options222);
                    rect = new Rect(0, 0, options222.outWidth, options222.outHeight);
                }
                return rect;
            } catch (RemoteException e4) {
                throw e4.rethrowFromSystemServer();
            }
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
        public Bitmap getCurrentWallpaperLocked(Context context, int i, int i2, final boolean z, final ColorManagementProxy colorManagementProxy) throws IOException {
            ImageDecoder.Source sourceCreateSource;
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
                            sourceCreateSource = ImageDecoder.createSource(context.getResources(), autoCloseInputStream, 0);
                        } else {
                            sourceCreateSource = ImageDecoder.createSource(context.getResources(), autoCloseInputStream);
                        }
                        Bitmap bitmapDecodeBitmap = ImageDecoder.decodeBitmap(sourceCreateSource, new ImageDecoder.OnHeaderDecodedListener() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda0
                            @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                            public final void onHeaderDecoded(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
                                WallpaperManager.Globals.lambda$getCurrentWallpaperLocked$2(z, colorManagementProxy, imageDecoder, imageInfo, source);
                            }
                        });
                        autoCloseInputStream.close();
                        return bitmapDecodeBitmap;
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

        private Bitmap getDefaultWallpaper(Context context, int i) throws IOException {
            Trace.beginSection("WPMS.getDefaultWallpaper_" + i);
            Bitmap defaultWallpaper = getDefaultWallpaper(i);
            if (defaultWallpaper == null || defaultWallpaper.isRecycled()) {
                Trace.beginSection("WPMS.openDefaultWallpaper");
                defaultWallpaper = null;
                try {
                    InputStream inputStreamOpenDefaultWallpaper = WallpaperManager.openDefaultWallpaper(context, i);
                    try {
                        Trace.endSection();
                        if (inputStreamOpenDefaultWallpaper != null) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            Trace.beginSection("WPMS.decodeStream");
                            defaultWallpaper = checkDeviceDensity(context, BitmapFactory.decodeStream(inputStreamOpenDefaultWallpaper, null, options), i);
                            Trace.endSection();
                        }
                        if (inputStreamOpenDefaultWallpaper != null) {
                            inputStreamOpenDefaultWallpaper.close();
                        }
                    } catch (Throwable th) {
                        if (inputStreamOpenDefaultWallpaper != null) {
                            try {
                                inputStreamOpenDefaultWallpaper.close();
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
                boolean zIsDex = WhichChecker.isDex(i);
                if (z || zIsDex) {
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
            float fMax = Math.max(i3 / width, i2 / height);
            Bitmap bitmapResizeBitmap = resizeBitmap(bitmap, fMax);
            Log.d(WallpaperManager.TAG, "checkDeviceDensity: resize scale down.:" + fMax);
            return bitmapResizeBitmap;
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
                    this.mSemColorListeners.add(new Pair<>(onSemColorsChangedListener, handler));
                } else {
                    this.mSemColorListeners.add(new Pair<>(onSemColorsChangedListener, handler));
                }
            }
        }

        public void removeOnSemColorsChangedListener(final OnSemColorsChangedListener onSemColorsChangedListener, int i, int i2) {
            synchronized (this) {
                this.mSemColorListeners.removeIf(new Predicate() { // from class: android.app.WallpaperManager$Globals$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return WallpaperManager.Globals.lambda$removeOnSemColorsChangedListener$3(onSemColorsChangedListener, (Pair) obj);
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
                            this.f$0.lambda$onSemWallpaperColorsChanged$4(next, semWallpaperColors, i);
                        }
                    });
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSemWallpaperColorsChanged$4(Pair pair, SemWallpaperColors semWallpaperColors, int i) {
            boolean zContains;
            synchronized (this) {
                zContains = this.mSemColorListeners.contains(pair);
            }
            if (zContains) {
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
        Bitmap bitmapPeekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, !WhichChecker.isLock(i), i, getColorManagementProxy());
        if (bitmapPeekWallpaperBitmap == null) {
            return null;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmapPeekWallpaperBitmap);
        bitmapDrawable.setDither(false);
        return bitmapDrawable;
    }

    @Override // android.app.SemWallpaperManager
    public Drawable semGetDrawable(int i) {
        return semGetDrawable(i, 1);
    }

    public Drawable semGetDrawable(int i, int i2) throws IOException {
        boolean zIsDesktopModeEnabled;
        Drawable drawableFromBitmap;
        AssetFileDescriptor videoFDFromPackage;
        ParcelFileDescriptor parcelFileDescriptorSemGetThumbnailFileDescriptor;
        if (!WhichChecker.isSystem(i) && !WhichChecker.isLock(i)) {
            if (WhichChecker.isDex(i)) {
                i = 9;
            } else {
                if (i == 0) {
                    InputStream inputStreamOpenDefaultWallpaper = openDefaultWallpaper(this.mContext, 2, false);
                    if (inputStreamOpenDefaultWallpaper == null) {
                        inputStreamOpenDefaultWallpaper = openDefaultWallpaper(this.mContext, 1, false);
                    }
                    return getDrawableFromStream(inputStreamOpenDefaultWallpaper);
                }
                i = 1;
            }
        }
        if (WhichChecker.isModeAbsent(i)) {
            int modeEnsuredWhich = getModeEnsuredWhich(i);
            Log.d(TAG, "semGetDrawable: mode is absent. which=" + i + ", adjustedWhich=" + modeEnsuredWhich);
            i = modeEnsuredWhich;
        }
        int iSemGetWallpaperType = semGetWallpaperType(i);
        Log.i(TAG, "semGetDrawable: which = " + i + ", wallpaperType = " + iSemGetWallpaperType + ", orientation=" + i2 + ", caller=" + this.mContext.getPackageName());
        try {
            parcelFileDescriptorSemGetThumbnailFileDescriptor = semGetThumbnailFileDescriptor(i, this.mContext.getUserId(), i2 == 1 ? 0 : 1);
        } catch (IOException e) {
            Log.e(TAG, "semGetDrawable: e=" + e, e);
        }
        if (parcelFileDescriptorSemGetThumbnailFileDescriptor != null) {
            try {
                Drawable drawableFromBitmap2 = getDrawableFromBitmap(BitmapFactory.decodeFileDescriptor(parcelFileDescriptorSemGetThumbnailFileDescriptor.getFileDescriptor()));
                if (parcelFileDescriptorSemGetThumbnailFileDescriptor != null) {
                    parcelFileDescriptorSemGetThumbnailFileDescriptor.close();
                }
                return drawableFromBitmap2;
            } finally {
            }
        } else {
            if (parcelFileDescriptorSemGetThumbnailFileDescriptor != null) {
                parcelFileDescriptorSemGetThumbnailFileDescriptor.close();
            }
            Log.w(TAG, "semGetDrawable: Couldn't get thumbnail. Keep going..");
            if (Rune.SUPPORT_DESKTOP_MODE) {
                try {
                    zIsDesktopModeEnabled = sGlobals.mService.isDesktopModeEnabled(i);
                } catch (RemoteException e2) {
                    throw e2.rethrowFromSystemServer();
                }
            } else {
                zIsDesktopModeEnabled = false;
            }
            boolean z = sWallpaperResourcesInfo.isDefaultVideo(i) && !zIsDesktopModeEnabled && isVideoWallpaper();
            int mode = (WhichChecker.isLock(i) && isSystemAndLockPaired(i)) ? WhichChecker.getMode(i) | 1 : i;
            if (z) {
                String videoFileName = getVideoFileName(i);
                try {
                    videoFDFromPackage = getVideoFDFromPackage(WALLPAPER_PACKAGE, videoFileName);
                } catch (Exception e3) {
                    e3.printStackTrace();
                    videoFDFromPackage = null;
                }
                Bitmap videoWallpaperFrame = getVideoWallpaperFrame(videoFDFromPackage, null, videoFileName);
                if (videoWallpaperFrame != null) {
                    return new BitmapDrawable(this.mContext.getResources(), videoWallpaperFrame);
                }
            } else if (iSemGetWallpaperType == 7) {
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
                Bitmap bitmapCheckDeviceDensity = sGlobals.checkDeviceDensity(this.mContext, BitmapFactory.decodeStream(inputStream, null, new BitmapFactory.Options()));
                if (bitmapCheckDeviceDensity != null) {
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), bitmapCheckDeviceDensity);
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
        BitmapRegionDecoder bitmapRegionDecoderNewInstance;
        Bitmap bitmapCreateBitmap;
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
        float fMax = Math.max(0.0f, Math.min(1.0f, f));
        float fMax2 = Math.max(0.0f, Math.min(1.0f, f2));
        InputStream inputStreamOpenDefaultWallpaper = openDefaultWallpaper(this.mContext, i4);
        if (inputStreamOpenDefaultWallpaper == null) {
            Log.w(TAG, "default wallpaper stream " + i4 + " is null");
            return null;
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamOpenDefaultWallpaper);
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
            int iMin = Math.min(i7, i);
            int iMin2 = Math.min(i8, i2);
            if (z) {
                rectF = getMaxCropRect(i7, i8, iMin, iMin2, fMax, fMax2);
                i5 = iMin;
                i6 = iMin2;
            } else {
                i5 = iMin;
                i6 = iMin2;
                float f3 = (i7 - i5) * fMax;
                float f4 = (i8 - i6) * fMax2;
                rectF = new RectF(f3, f4, i5 + f3, i6 + f4);
            }
            Rect rect = new Rect();
            rectF.roundOut(rect);
            if (rect.width() <= 0 || rect.height() <= 0) {
                Log.w(TAG, "crop has bad values for full size image");
                return null;
            }
            int iMin3 = Math.min(rect.width() / i5, rect.height() / i6);
            try {
                bitmapRegionDecoderNewInstance = BitmapRegionDecoder.newInstance((InputStream) bufferedInputStream2, true);
            } catch (IOException unused) {
                Log.w(TAG, "cannot open region decoder for default wallpaper");
                bitmapRegionDecoderNewInstance = null;
            }
            if (bitmapRegionDecoderNewInstance != null) {
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                if (iMin3 > 1) {
                    options2.inSampleSize = iMin3;
                }
                bitmapCreateBitmap = bitmapRegionDecoderNewInstance.decodeRegion(rect, options2);
                bitmapRegionDecoderNewInstance.recycle();
            } else {
                bitmapCreateBitmap = null;
            }
            if (bitmapCreateBitmap == null) {
                BufferedInputStream bufferedInputStream3 = new BufferedInputStream(openDefaultWallpaper(this.mContext, i4));
                BitmapFactory.Options options3 = new BitmapFactory.Options();
                if (iMin3 > 1) {
                    options3.inSampleSize = iMin3;
                }
                Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(bufferedInputStream3, null, options3);
                if (bitmapDecodeStream != null) {
                    bitmapCreateBitmap = Bitmap.createBitmap(bitmapDecodeStream, rect.left, rect.top, rect.width(), rect.height());
                }
            }
            if (bitmapCreateBitmap == null) {
                Log.w(TAG, "cannot decode default wallpaper");
                return null;
            }
            if (i5 > 0 && i6 > 0 && (bitmapCreateBitmap.getWidth() != i5 || bitmapCreateBitmap.getHeight() != i6)) {
                Matrix matrix = new Matrix();
                RectF rectF2 = new RectF(0.0f, 0.0f, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
                RectF rectF3 = new RectF(0.0f, 0.0f, i5, i6);
                matrix.setRectToRect(rectF2, rectF3, Matrix.ScaleToFit.FILL);
                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap((int) rectF3.width(), (int) rectF3.height(), Bitmap.Config.ARGB_8888);
                if (bitmapCreateBitmap2 != null) {
                    Canvas canvas = new Canvas(bitmapCreateBitmap2);
                    Paint paint = new Paint();
                    paint.setFilterBitmap(true);
                    canvas.drawBitmap(bitmapCreateBitmap, matrix, paint);
                    bitmapCreateBitmap = bitmapCreateBitmap2;
                }
            }
            return new BitmapDrawable(resources, bitmapCreateBitmap);
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
        Bitmap bitmapPeekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, z, i, colorManagementProxy);
        if (bitmapPeekWallpaperBitmap != null) {
            return new FastBitmapDrawable(bitmapPeekWallpaperBitmap);
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
        Bitmap bitmapPeekWallpaperBitmap;
        return (!shouldEnableWideColorGamut() || (bitmapPeekWallpaperBitmap = sGlobals.peekWallpaperBitmap(this.mContext, false, i, (colorManagementProxy = getColorManagementProxy()))) == null || bitmapPeekWallpaperBitmap.getColorSpace() == null || bitmapPeekWallpaperBitmap.getColorSpace() == ColorSpace.get(ColorSpace.Named.SRGB) || !colorManagementProxy.isSupportedColorSpace(bitmapPeekWallpaperBitmap.getColorSpace())) ? false : true;
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
            Rect rectPeekBitmapDimensions = peekBitmapDimensions(i, true);
            if (rectPeekBitmapDimensions == null) {
                return Collections.EMPTY_LIST;
            }
            return getBitmapCrops(new Point(rectPeekBitmapDimensions.width(), rectPeekBitmapDimensions.height()), list, (Map<Point, Rect>) null);
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
        Set<Map.Entry<Point, Rect>> setEntrySet = map.entrySet();
        return sGlobals.mService.getFutureBitmapCrops(point, list, setEntrySet.stream().mapToInt(new ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda2
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return WallpaperManager.getOrientation((Point) ((Map.Entry) obj).getKey());
            }
        }).toArray(), setEntrySet.stream().map(new WallpaperManager$$ExternalSyntheticLambda3()).toList());
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
        Set<Map.Entry<Point, Rect>> setEntrySet = map.entrySet();
        int[] array = setEntrySet.stream().mapToInt(new ToIntFunction() { // from class: android.app.WallpaperManager$$ExternalSyntheticLambda4
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return WallpaperManager.getOrientation((Point) ((Map.Entry) obj).getKey());
            }
        }).toArray();
        List<Rect> list = setEntrySet.stream().map(new WallpaperManager$$ExternalSyntheticLambda3()).toList();
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Intent getCropAndSetWallpaperIntent(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Image URI must not be null");
        }
        if (!"content".equals(uri.getScheme())) {
            throw new IllegalArgumentException("Image URI must be of the content scheme type");
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent(ACTION_CROP_AND_SET_WALLPAPER, uri);
        intent.addFlags(1);
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 65536);
        if (resolveInfoResolveActivity != null) {
            intent.setPackage(resolveInfoResolveActivity.activityInfo.packageName);
            if (packageManager.queryIntentActivities(intent, 0).size() <= 0) {
                intent.setPackage(this.mContext.getString(R.string.config_wallpaperCropperPackage));
                if (packageManager.queryIntentActivities(intent, 0).size() <= 0) {
                    throw new IllegalArgumentException("Cannot use passed URI to set wallpaper; check that the type returned by ContentProvider matches image/*");
                }
            }
        }
        return intent;
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

    /* JADX WARN: Removed duplicated region for block: B:37:0x00f2 A[Catch: RemoteException -> 0x00fd, TRY_LEAVE, TryCatch #0 {RemoteException -> 0x00fd, blocks: (B:9:0x0054, B:11:0x0066, B:12:0x007f, B:22:0x00d4, B:33:0x00e8, B:34:0x00eb, B:32:0x00e4, B:35:0x00ec, B:37:0x00f2), top: B:45:0x0054 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int setResource(Context context, int i, int i2, int i3, boolean z, boolean z2, Bundle bundle) throws Throwable {
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        String str = TAG;
        StringBuilder sb = new StringBuilder("setResource: which = ");
        sb.append(i2);
        sb.append(", resid = 0x");
        sb.append(Integer.toHexString(i));
        sb.append(", callingPkg = ");
        sb.append(context.getOpPackageName());
        sb.append(", hasExtras = ");
        sb.append(bundle != null);
        Log.i(str, sb.toString());
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        Bundle bundle2 = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion(this);
        try {
            Resources resources = context.getResources();
            if ("com.samsung.android.themecenter".equals(this.mContext.getPackageName())) {
                resources = new APKContents(APKContents.getMainThemePackagePath(Settings.System.getString(this.mContext.getContentResolver(), "current_sec_wallpaper_theme_package"))).getResources();
            }
            Resources resources2 = resources;
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper("res:" + resources.getResourceName(i), context.getOpPackageName(), null, null, z, bundle2, i2, wallpaperSetCompletion, UserHandle.myUserId(), i3, z2, bundle);
            if (wallpaper != null) {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
                try {
                    try {
                        autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    } catch (ClassCastException e) {
                        e = e;
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) resources2.getDrawable(i);
                    if (bitmapDrawable != null && !z2) {
                        copyDrawableToWallpaperFile(bitmapDrawable, autoCloseOutputStream);
                    } else {
                        copyStreamToWallpaperFile(resources2.openRawResource(i), autoCloseOutputStream);
                    }
                    autoCloseOutputStream.close();
                    wallpaperSetCompletion.waitForCompletion();
                    IoUtils.closeQuietly(autoCloseOutputStream);
                } catch (ClassCastException e2) {
                    e = e2;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    e.printStackTrace();
                    IoUtils.closeQuietly(autoCloseOutputStream2);
                    if (isNeedToClearBackupData()) {
                    }
                    return bundle2.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
                } catch (Throwable th2) {
                    th = th2;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    IoUtils.closeQuietly(autoCloseOutputStream2);
                    throw th;
                }
            }
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(i2);
            }
            return bundle2.getInt(EXTRA_NEW_WALLPAPER_ID, 0);
        } catch (RemoteException e3) {
            throw e3.rethrowFromSystemServer();
        }
    }

    @Override // android.app.SemWallpaperManager
    @Deprecated
    public void setWallpaperUri(String str, boolean z, int i) throws Throwable {
        semSetUri(Uri.parse(str), z, i);
    }

    @Override // android.app.SemWallpaperManager
    public Uri semGetUri(int i) {
        try {
            if (WhichChecker.isLock(i) && isSystemAndLockPaired(i)) {
                Log.d(TAG, "semGetUri: Converting which to system.");
                i = WhichChecker.getMode(i) | 1;
            }
            String strSemGetUri = sGlobals.mService.semGetUri(i, this.mContext.getOpPackageName());
            if (strSemGetUri != null) {
                return Uri.parse(strSemGetUri);
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
    public void semSetUri(Uri uri, boolean z, int i) throws Throwable {
        semSetUri(uri, z, i, -1);
    }

    @Override // android.app.SemWallpaperManager
    public void semSetUri(Uri uri, boolean z, int i, int i2) throws Throwable {
        semSetUri(uri, z, i, i2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void semSetUri(Uri uri, boolean z, int i, int i2, Bundle bundle) throws Throwable {
        Context contextCreatePackageContext;
        String str = TAG;
        StringBuilder sb = new StringBuilder("semSetUri: which = ");
        sb.append(i);
        sb.append(", type = ");
        sb.append(i2);
        sb.append(", uri = ");
        sb.append(uri);
        sb.append(", allowBackup = ");
        sb.append(z);
        sb.append(", hasExtras = ");
        sb.append(bundle != null);
        Log.d(str, sb.toString());
        if (uri == null) {
            return;
        }
        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        String lastPathSegment = uri.getLastPathSegment();
        int i3 = SEM_SCHEME_MULTIPACK.equals(scheme) ? 3 : i2;
        if (i3 == 1000 || i3 == 3) {
            semSetWallpaper(uri.toString(), z, i, i3, bundle);
            return;
        }
        try {
            int i4 = i3;
            try {
                sGlobals.mService.semSetUri(uri.toString(), z, i, i4, this.mContext.getOpPackageName(), this.mContext.getUserId(), bundle);
                i3 = i4;
            } catch (RemoteException e) {
                e = e;
                i3 = i4;
                e.printStackTrace();
                if (i3 != 5) {
                }
            }
        } catch (RemoteException e2) {
            e = e2;
        }
        if (i3 != 5) {
            if (isNeedToClearBackupData()) {
                semClearBackupWallpapers(i);
                return;
            }
            return;
        }
        if (authority == null || authority.isEmpty() || lastPathSegment == null || lastPathSegment.isEmpty()) {
            return;
        }
        try {
            contextCreatePackageContext = this.mContext.createPackageContext(authority, 0);
        } catch (PackageManager.NameNotFoundException e3) {
            e3.printStackTrace();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        if (contextCreatePackageContext == null) {
            return;
        }
        int identifier = contextCreatePackageContext.getResources().getIdentifier(lastPathSegment, "drawable", authority);
        if (identifier <= 0) {
            Log.d(TAG, "Resource id not found");
            return;
        }
        Bundle bundle2 = bundle == null ? new Bundle() : bundle;
        bundle2.putString("uri", uri.toString());
        setPreloadedResource(contextCreatePackageContext, identifier, i, z, bundle2);
        Log.d(TAG, "Set wallpaper based on END");
    }

    private void semSetWallpaper(String str, boolean z, int i, int i2, Bundle bundle) throws Throwable {
        Throwable th;
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        if (i2 != 1000 || WhichChecker.isLock(i)) {
            try {
                ParcelFileDescriptor parcelFileDescriptorSemSetWallpaper = sGlobals.mService.semSetWallpaper(str, this.mContext.getOpPackageName(), null, null, z, null, i, null, this.mContext.getUserId(), i2, false, bundle);
                if (parcelFileDescriptorSemSetWallpaper != null) {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
                    try {
                        autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptorSemSetWallpaper);
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    try {
                        autoCloseOutputStream.close();
                        IoUtils.closeQuietly(autoCloseOutputStream);
                    } catch (Throwable th3) {
                        th = th3;
                        autoCloseOutputStream2 = autoCloseOutputStream;
                        IoUtils.closeQuietly(autoCloseOutputStream2);
                        throw th;
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

    private int setBitmap(Bitmap bitmap, Rect rect, boolean z, int i, int i2, int i3, Bundle bundle) throws Throwable {
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
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
        try {
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, rect == null ? null : List.of(rect), z, bundle2, i, wallpaperSetCompletion, i2, i3, false, bundle);
            if (wallpaper != null) {
                try {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, autoCloseOutputStream2);
                        autoCloseOutputStream2.close();
                        wallpaperSetCompletion.waitForCompletion();
                        IoUtils.closeQuietly(autoCloseOutputStream2);
                    } catch (Throwable th) {
                        th = th;
                        autoCloseOutputStream = autoCloseOutputStream2;
                        IoUtils.closeQuietly(autoCloseOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
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
                sparseArray.put(WallpaperManager.getOrientation((Point) obj), (Rect) obj2);
            }
        });
        return setBitmapWithCrops(bitmap, sparseArray, z, i, this.mContext.getUserId());
    }

    private int setBitmapWithCrops(Bitmap bitmap, SparseArray<Rect> sparseArray, boolean z, int i, int i2) throws Throwable {
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        int size = sparseArray.size();
        int[] iArr = new int[size];
        ArrayList arrayList = new ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            iArr[i3] = sparseArray.keyAt(i3);
            Rect rectValueAt = sparseArray.valueAt(i3);
            validateRect(rectValueAt);
            arrayList.add(rectValueAt);
        }
        Bundle bundle = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion(this);
        try {
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), iArr, arrayList, z, bundle, i, wallpaperSetCompletion, i2, 0, false, null);
            if (wallpaper != null) {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
                try {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, autoCloseOutputStream2);
                        autoCloseOutputStream2.close();
                        wallpaperSetCompletion.waitForCompletion();
                        IoUtils.closeQuietly(autoCloseOutputStream2);
                    } catch (Throwable th) {
                        th = th;
                        autoCloseOutputStream = autoCloseOutputStream2;
                        IoUtils.closeQuietly(autoCloseOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
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

    private void copyDrawableToWallpaperFile(BitmapDrawable bitmapDrawable, FileOutputStream fileOutputStream) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        Log.i(TAG, "copyDrawableToWallpaperFile");
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2);
                byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream2.toByteArray());
                try {
                    FileUtils.copy(byteArrayInputStream, fileOutputStream);
                    IoUtils.closeQuietly(byteArrayOutputStream2);
                    IoUtils.closeQuietly(byteArrayInputStream);
                } catch (Exception e) {
                    e = e;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    try {
                        e.printStackTrace();
                        IoUtils.closeQuietly(byteArrayOutputStream);
                        IoUtils.closeQuietly(byteArrayInputStream);
                    } catch (Throwable th) {
                        th = th;
                        IoUtils.closeQuietly(byteArrayOutputStream);
                        IoUtils.closeQuietly(byteArrayInputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    IoUtils.closeQuietly(byteArrayOutputStream);
                    IoUtils.closeQuietly(byteArrayInputStream);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                byteArrayInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                byteArrayInputStream = null;
            }
        } catch (Exception e3) {
            e = e3;
            byteArrayInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            byteArrayInputStream = null;
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
    public int setStream(InputStream inputStream, Rect rect, boolean z, int i, int i2, boolean z2, Bundle bundle) throws Throwable {
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
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = null;
        List<Rect> listOf = rect == null ? null : List.of(rect);
        try {
            Log.d(TAG, "begin setWallpaper()");
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), null, listOf, z, bundle2, i, wallpaperSetCompletion, this.mContext.getUserId(), i2, z2, bundle);
            Log.d(TAG, "finish setWallpaper()");
            if (wallpaper != null) {
                try {
                    ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                    try {
                        copyStreamToWallpaperFile(inputStream, autoCloseOutputStream2);
                        autoCloseOutputStream2.close();
                        wallpaperSetCompletion.waitForCompletion();
                        IoUtils.closeQuietly(autoCloseOutputStream2);
                    } catch (Throwable th) {
                        th = th;
                        autoCloseOutputStream = autoCloseOutputStream2;
                        IoUtils.closeQuietly(autoCloseOutputStream);
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
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
                sparseArray.put(WallpaperManager.getOrientation((Point) obj), (Rect) obj2);
            }
        });
        return setStreamWithCrops(inputStream, sparseArray, z, i);
    }

    @SystemApi
    public int setStreamWithCrops(InputStream inputStream, SparseArray<Rect> sparseArray, boolean z, int i) throws IOException {
        return setStreamWithCrops(inputStream, sparseArray, z, i, null);
    }

    private int setStreamWithCrops(InputStream inputStream, SparseArray<Rect> sparseArray, boolean z, int i, Bundle bundle) throws Throwable {
        Throwable th;
        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
        if (sGlobals.mService == null) {
            Log.w(TAG, "WallpaperService not running");
            throw new RuntimeException(new DeadSystemException());
        }
        int size = sparseArray.size();
        int[] iArr = new int[size];
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = sparseArray.keyAt(i2);
            Rect rectValueAt = sparseArray.valueAt(i2);
            validateRect(rectValueAt);
            arrayList.add(rectValueAt);
        }
        Bundle bundle2 = new Bundle();
        WallpaperSetCompletion wallpaperSetCompletion = new WallpaperSetCompletion(this);
        try {
            ParcelFileDescriptor wallpaper = sGlobals.mService.setWallpaper(null, this.mContext.getOpPackageName(), iArr, arrayList, z, bundle2, i, wallpaperSetCompletion, this.mContext.getUserId(), 0, false, bundle);
            if (wallpaper != null) {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream2 = null;
                try {
                    autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(wallpaper);
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    copyStreamToWallpaperFile(inputStream, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                    wallpaperSetCompletion.waitForCompletion();
                    IoUtils.closeQuietly(autoCloseOutputStream);
                } catch (Throwable th3) {
                    th = th3;
                    autoCloseOutputStream2 = autoCloseOutputStream;
                    IoUtils.closeQuietly(autoCloseOutputStream2);
                    throw th;
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
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
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

    public static InputStream openDefaultWallpaper(Context context, int i, boolean z, String str) {
        InputStream defaultImageWallpaper;
        File defaultWallpaperFile;
        boolean z2 = (WhichChecker.isSubDisplay(i) && Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) ? false : true;
        Log.d(TAG, "openDefaultWallpaper() which = " + i + " , color = " + str);
        if (TextUtils.isEmpty(str) && z2 && (defaultWallpaperFile = getDefaultWallpaperFile(context, i)) != null) {
            try {
                defaultImageWallpaper = new FileInputStream(defaultWallpaperFile);
            } catch (IOException e) {
                Log.w(TAG, "getDefaultWallpaperFile error:", e);
            }
        } else {
            defaultImageWallpaper = null;
        }
        if (defaultImageWallpaper == null) {
            synchronized (sSync) {
                if (sWallpaperResourcesInfo == null) {
                    sWallpaperResourcesInfo = new SemWallpaperResourcesInfo(context);
                }
            }
            defaultImageWallpaper = sWallpaperResourcesInfo.getDefaultImageWallpaper(i);
        }
        Log.d(TAG, "openDefaultWallpaper: by [" + context.getOpPackageName() + NavigationBarInflaterView.SIZE_MOD_END);
        return defaultImageWallpaper;
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
        ComponentName componentNameUnflattenFromString = !TextUtils.isEmpty(str) ? ComponentName.unflattenFromString(str) : null;
        if (componentNameUnflattenFromString == null) {
            String string = context.getString(R.string.default_wallpaper_component);
            if (!TextUtils.isEmpty(string)) {
                componentNameUnflattenFromString = ComponentName.unflattenFromString(string);
            }
        }
        if (isComponentExist(context, componentNameUnflattenFromString)) {
            return componentNameUnflattenFromString;
        }
        return null;
    }

    public static ComponentName getCmfDefaultWallpaperComponent(Context context) throws Resources.NotFoundException {
        ComponentName componentNameUnflattenFromString;
        String[] strArrSplit;
        String[] stringArray = context.getResources().getStringArray(R.array.default_wallpaper_component_per_device_color);
        if (stringArray == null || stringArray.length <= 0) {
            componentNameUnflattenFromString = null;
        } else {
            for (String str : stringArray) {
                if (!TextUtils.isEmpty(str) && (strArrSplit = str.split(",")) != null && strArrSplit.length == 2 && VALUE_CMF_COLOR.equals(strArrSplit[0]) && !TextUtils.isEmpty(strArrSplit[1])) {
                    componentNameUnflattenFromString = ComponentName.unflattenFromString(strArrSplit[1]);
                    break;
                }
            }
            componentNameUnflattenFromString = null;
        }
        ComponentName componentName = isComponentExist(context, componentNameUnflattenFromString) ? componentNameUnflattenFromString : null;
        return componentName == null ? getDefaultWallpaperComponent(context) : componentName;
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
        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) throws Throwable {
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
    public void setResourceAll(int i) throws Resources.NotFoundException, IOException {
        Log.d(TAG, "setResourceAll");
        Bitmap bitmapGenerateBitmap = generateBitmap(i);
        if (bitmapGenerateBitmap != null) {
            setBitmap(bitmapGenerateBitmap);
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

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Bitmap generateBitmap(int i) throws Resources.NotFoundException {
        Resources resources;
        int identifier;
        Log.d(TAG, "generateBitmap");
        try {
            String resourceName = this.mContext.getResources().getResourceName(i);
            Log.d(TAG, "resourceName=" + resourceName);
            int i2 = -1;
            if (TextUtils.isEmpty(resourceName)) {
                resources = null;
            } else {
                int iIndexOf = resourceName.indexOf(58);
                String strSubstring = iIndexOf > 0 ? resourceName.substring(0, iIndexOf) : null;
                int iLastIndexOf = resourceName.lastIndexOf(47);
                String strSubstring2 = iLastIndexOf > 0 ? resourceName.substring(iLastIndexOf + 1) : null;
                String strSubstring3 = (iIndexOf <= 0 || iLastIndexOf <= 0 || iLastIndexOf - iIndexOf <= 1) ? null : resourceName.substring(iIndexOf + 1, iLastIndexOf);
                if (strSubstring != null && strSubstring2 != null && strSubstring3 != null) {
                    try {
                        resources = new APKContents(APKContents.getMainThemePackagePath(strSubstring)).getResources();
                        try {
                            if (resources != null) {
                                identifier = resources.getIdentifier(resourceName, null, null);
                            } else {
                                resources = this.mContext.createPackageContext(strSubstring, 4).getResources();
                                identifier = resources.getIdentifier(resourceName, null, null);
                            }
                            i2 = identifier;
                        } catch (PackageManager.NameNotFoundException unused) {
                            Log.e(TAG, "Package name " + strSubstring + " not found");
                            Log.d(TAG, "themeResourceId=" + i2);
                            return resources != null ? null : null;
                        } catch (Resources.NotFoundException unused2) {
                            Log.e(TAG, "Resource not found: -1");
                            Log.d(TAG, "themeResourceId=" + i2);
                            if (resources != null) {
                            }
                        }
                    } catch (PackageManager.NameNotFoundException unused3) {
                        resources = null;
                    } catch (Resources.NotFoundException unused4) {
                        resources = null;
                    }
                }
            }
            Log.d(TAG, "themeResourceId=" + i2);
            if (resources != null && i2 > 0) {
                return BitmapFactory.decodeResource(resources, i2);
            }
        } catch (OutOfMemoryError e) {
            Log.w(TAG, "Can't decode file", e);
            return null;
        }
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
            boolean zIsVideoWallpaper = sGlobals.mService.isVideoWallpaper();
            Log.d(TAG, "isVideoWallpaper = " + zIsVideoWallpaper);
            return zIsVideoWallpaper;
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Bitmap getVideoWallpaperFrame(AssetFileDescriptor assetFileDescriptor, String str, String str2) {
        long j;
        Log.d(TAG, "getVideoWallpaperFrame, creating MediaMetadataRetriever");
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        Bitmap frameAtTime = null;
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
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (NumberFormatException e3) {
                e3.printStackTrace();
                frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L);
                mediaMetadataRetriever.release();
            } catch (Exception e4) {
                e4.printStackTrace();
                mediaMetadataRetriever.release();
            }
            if (!TextUtils.isEmpty(str2)) {
                String strExtractMetadata = mediaMetadataRetriever.extractMetadata(32);
                String strExtractMetadata2 = mediaMetadataRetriever.extractMetadata(9);
                if (TextUtils.isEmpty(strExtractMetadata) || TextUtils.isEmpty(strExtractMetadata2)) {
                    j = 0;
                    MediaMetadataRetriever.BitmapParams bitmapParams = new MediaMetadataRetriever.BitmapParams();
                    bitmapParams.setPreferredConfig(Bitmap.Config.ARGB_8888);
                    frameAtTime = mediaMetadataRetriever.getFrameAtTime(j, 2, bitmapParams);
                } else {
                    int i = Integer.parseInt(strExtractMetadata);
                    int i2 = Integer.parseInt(strExtractMetadata2);
                    int defaultVideoFrameInfo = sWallpaperResourcesInfo.getDefaultVideoFrameInfo(str2);
                    if (i > 0 && defaultVideoFrameInfo > 0 && i >= defaultVideoFrameInfo) {
                        j = (int) (i2 * 1000 * (defaultVideoFrameInfo / i));
                    }
                    MediaMetadataRetriever.BitmapParams bitmapParams2 = new MediaMetadataRetriever.BitmapParams();
                    bitmapParams2.setPreferredConfig(Bitmap.Config.ARGB_8888);
                    frameAtTime = mediaMetadataRetriever.getFrameAtTime(j, 2, bitmapParams2);
                }
                Log.d(TAG, "getVideoWallpaperFrame, done");
                return frameAtTime;
            }
            frameAtTime = mediaMetadataRetriever.getFrameAtTime(0L);
            j = 0;
            Log.d(TAG, "getVideoWallpaperFrame " + j);
            mediaMetadataRetriever.release();
            Log.d(TAG, "getVideoWallpaperFrame, done");
            return frameAtTime;
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
        int iMyUid = Process.myUid();
        int iMyPid = Process.myPid();
        boolean z = false;
        for (String str : strArr) {
            if (this.mContext.checkPermission(str, iMyPid, iMyUid) == 0) {
                z = true;
            }
        }
        String opPackageName = this.mContext.getOpPackageName();
        if (z) {
            return;
        }
        ((StorageManager) this.mContext.getSystemService(StorageManager.class)).checkPermissionReadImages(true, iMyPid, iMyUid, opPackageName, null);
    }

    private boolean canPeekWallpaper(int i) {
        boolean zIsSystemAndLockPaired = isSystemAndLockPaired(i);
        if (!WhichChecker.isLock(i) || !zIsSystemAndLockPaired) {
            return true;
        }
        Log.w(TAG, "canPeekWallpaper failed, which = " + i);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Bitmap getThemeWallpaperBackground(String str) {
        InputStream inputStreamOpen;
        AssetManager assets;
        try {
            assets = new APKContents(APKContents.getMainThemePackagePath(str)).getAssets();
            if (assets == null) {
                assets = this.mContext.getPackageManager().getResourcesForApplication(str).getAssets();
            }
        } catch (IOException unused) {
            Log.e(TAG, "getThemeWallpaperBackground IOException");
            inputStreamOpen = null;
            if (inputStreamOpen == null) {
            }
            if (inputStreamOpen != null) {
            }
        } catch (Exception unused2) {
            Log.e(TAG, "getThemeWallpaperBackground Exception");
            inputStreamOpen = null;
            if (inputStreamOpen == null) {
            }
            if (inputStreamOpen != null) {
            }
        }
        if (assets != null) {
            inputStreamOpen = assets.open("preview/thumbnail_wallpaper.jpg");
            if (inputStreamOpen == null) {
                String strReplace = str.replace(".wallpaper", "");
                try {
                    AssetManager assets2 = new APKContents(APKContents.getMainThemePackagePath(strReplace)).getAssets();
                    if (assets2 == null) {
                        assets2 = this.mContext.getPackageManager().getResourcesForApplication(strReplace).getAssets();
                    }
                    if (assets2 != null) {
                        inputStreamOpen = assets2.open("preview/theme_lockscreen.jpg");
                    } else {
                        Log.e(TAG, "getAnimatedWallpaperBackground() : Theme pkg, AssetManager is null");
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamOpen != null) {
                return BitmapFactory.decodeStream(inputStreamOpen);
            }
            return null;
        }
        Log.e(TAG, "getAnimatedWallpaperBackground() : Wallpaper pkg, AssetManager is null");
        return null;
    }

    private AssetFileDescriptor getVideoFDFromPackage(String str, String str2) {
        Context contextCreatePackageContext;
        Resources resources;
        AssetManager assets;
        Log.d(TAG, "getVideoFDFromPackage() pkgName = " + str + " , fileName = " + str2);
        try {
            contextCreatePackageContext = this.mContext.createPackageContext(str, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            contextCreatePackageContext = null;
        }
        if (contextCreatePackageContext == null) {
            Log.e(TAG, "getVideoFDFromPackage() otherContext is null");
            APKContents aPKContents = new APKContents(APKContents.getMainThemePackagePath(str));
            resources = aPKContents.getResources();
            assets = aPKContents.getAssets();
            if (resources == null || assets == null) {
                return null;
            }
        } else {
            resources = contextCreatePackageContext.getResources();
            assets = contextCreatePackageContext.getAssets();
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
        Uri uriSemGetUri = semGetUri(i);
        if (uriSemGetUri != null) {
            String string = uriSemGetUri.toString();
            Log.i(TAG, "isApplied: uri = " + string);
            if (!TextUtils.isEmpty(string) && string.contains(str)) {
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
            int[] iArrConverAccent1ToSeedColors = ColorPaletteCreator.converAccent1ToSeedColors(iArr);
            if (iArrConverAccent1ToSeedColors != null && iArrConverAccent1ToSeedColors.length > 0) {
                for (int i : iArrConverAccent1ToSeedColors) {
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
        SemWallpaperColors semWallpaperColorsSemGetWallpaperColors = semGetWallpaperColors(i);
        int[] seedColors = semWallpaperColorsSemGetWallpaperColors != null ? semWallpaperColorsSemGetWallpaperColors.getSeedColors() : null;
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
        Uri uriSemGetUri;
        int iSemGetWallpaperType = semGetWallpaperType(i);
        boolean z = true;
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), getSettingsName(i), 1, -2);
        boolean z2 = false;
        boolean z3 = ((intForUser == 0) || (intForUser == 3)) && isWallpaperBackupAllowed(i);
        if (iSemGetWallpaperType == 3 && !z3 && (uriSemGetUri = semGetUri(i)) != null) {
            String string = uriSemGetUri.toString();
            if (!TextUtils.isEmpty(string) && string.startsWith(BnRConstants.CUSTOM_PACK_PREFIX)) {
                z3 = true;
            }
        }
        if (z3 && iSemGetWallpaperType == 7 && !isStockLiveWallpaper(i)) {
            Log.d(TAG, "canBackup: which = " + i + ", external live wallpaper");
        } else {
            z2 = z3;
        }
        if (Build.VERSION.SEM_PLATFORM_INT < 160000 || z2 || iSemGetWallpaperType != 1000) {
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
