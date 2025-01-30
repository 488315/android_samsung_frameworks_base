package com.android.p038wm.shell.startingsurface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.util.Slog;
import android.view.Choreographer;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.window.SplashScreenView;
import android.window.StartingWindowInfo;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.internal.R;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.Quantizer;
import com.android.internal.graphics.palette.VariationalKMeansQuantizer;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.animation.Interpolators;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.TransactionPool;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.startingsurface.SplashScreenExitAnimationUtils;
import com.android.p038wm.shell.startingsurface.SplashscreenContentDrawer;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.rune.CoreRune;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntSupplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplashscreenContentDrawer {
    public static final int CLEAR_PREALOD_ICON_TIMEOUT_MILLIS = ViewConfiguration.getLongPressTimeout() + 200;
    public static boolean mIsNightMode = false;
    public static int mThemeBackgroundColor;
    public int mBrandingImageHeight;
    public int mBrandingImageWidth;
    final ColorCache mColorCache;
    public final Context mContext;
    public int mDefaultIconSize;
    public final HighResIconProvider mHighResIconProvider;
    public int mIconSize;
    public int mLastPackageContextConfigHash;
    public final PreloadIconData mPreloadIcon;
    public final SettingObserver mSettingObserver;
    public final ShellExecutor mSplashScreenExecutor;
    public final Handler mSplashscreenWorkerHandler;
    public String mThemeIconPackageName;
    public String mThemePackageName;
    public final TransactionPool mTransactionPool;
    public final SplashScreenWindowAttrs mTmpAttrs = new SplashScreenWindowAttrs();
    public final PreLoadIconDataHandler mHandler = new PreLoadIconDataHandler(this, 0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class ColorCache extends BroadcastReceiver {
        public final ArrayMap mColorMap = new ArrayMap();

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public class Cache {
            public final int mHash;
            public int mReuseCount;

            public Cache(int i) {
                this.mHash = i;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Colors {
            public final IconColor[] mIconColors;
            public final WindowColor[] mWindowColors;

            public /* synthetic */ Colors(int i) {
                this();
            }

            private Colors() {
                this.mWindowColors = new WindowColor[2];
                this.mIconColors = new IconColor[2];
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class IconColor extends Cache {
            public final int mBgColor;
            public final int mFgColor;
            public final float mFgNonTranslucentRatio;
            public final boolean mIsBgComplex;
            public final boolean mIsBgGrayscale;

            public IconColor(int i, int i2, int i3, boolean z, boolean z2, float f) {
                super(i);
                this.mFgColor = i2;
                this.mBgColor = i3;
                this.mIsBgComplex = z;
                this.mIsBgGrayscale = z2;
                this.mFgNonTranslucentRatio = f;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class WindowColor extends Cache {
            public final int mBgColor;

            public WindowColor(int i, int i2) {
                super(i);
                this.mBgColor = i2;
            }
        }

        public ColorCache(Context context, Handler handler) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            context.registerReceiverAsUser(this, UserHandle.ALL, intentFilter, null, handler);
        }

        public static Cache getCache(Cache[] cacheArr, int i, int[] iArr) {
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < 2; i3++) {
                Cache cache = cacheArr[i3];
                if (cache == null) {
                    iArr[0] = i3;
                    i2 = -1;
                } else {
                    if (cache.mHash == i) {
                        cache.mReuseCount++;
                        return cache;
                    }
                    int i4 = cache.mReuseCount;
                    if (i4 < i2) {
                        iArr[0] = i3;
                        i2 = i4;
                    }
                }
            }
            return null;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Uri data = intent.getData();
            if (data != null) {
                this.mColorMap.remove(data.getEncodedSchemeSpecificPart());
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DrawableColorTester {
        public final ColorTester mColorChecker;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public interface ColorTester {
            int getDominantColor();

            boolean isComplexColor();

            boolean isGrayscale();

            float passFilterRatio();
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ComplexDrawableTester implements ColorTester {
            public static final AlphaFilterQuantizer ALPHA_FILTER_QUANTIZER = new AlphaFilterQuantizer(0);
            public final boolean mFilterTransparent;
            public final Palette mPalette;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            public final class AlphaFilterQuantizer implements Quantizer {
                public C4125xf5e80cfe mFilter;
                public final Quantizer mInnerQuantizer;
                public float mPassFilterRatio;
                public final C4125xf5e80cfe mTranslucentFilter;
                public final C4125xf5e80cfe mTransparentFilter;

                public /* synthetic */ AlphaFilterQuantizer(int i) {
                    this();
                }

                public final List getQuantizedColors() {
                    return this.mInnerQuantizer.getQuantizedColors();
                }

                public final void quantize(int[] iArr, int i) {
                    this.mPassFilterRatio = 0.0f;
                    int i2 = 0;
                    int i3 = 0;
                    for (int length = iArr.length - 1; length > 0; length--) {
                        if (this.mFilter.test(iArr[length])) {
                            i3++;
                        }
                    }
                    if (i3 == 0) {
                        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -424415681, 0, null, null);
                        }
                        this.mInnerQuantizer.quantize(iArr, i);
                        return;
                    }
                    this.mPassFilterRatio = i3 / iArr.length;
                    int[] iArr2 = new int[i3];
                    int length2 = iArr.length;
                    while (true) {
                        length2--;
                        if (length2 <= 0) {
                            this.mInnerQuantizer.quantize(iArr2, i);
                            return;
                        } else if (this.mFilter.test(iArr[length2])) {
                            iArr2[i2] = iArr[length2];
                            i2++;
                        }
                    }
                }

                private AlphaFilterQuantizer() {
                    this.mInnerQuantizer = new VariationalKMeansQuantizer();
                    C4125xf5e80cfe c4125xf5e80cfe = new C4125xf5e80cfe(0);
                    this.mTransparentFilter = c4125xf5e80cfe;
                    this.mTranslucentFilter = new C4125xf5e80cfe(1);
                    this.mFilter = c4125xf5e80cfe;
                }
            }

            public ComplexDrawableTester(Drawable drawable, int i) {
                int i2;
                Palette.Builder maximumColorCount;
                Trace.traceBegin(32L, "ComplexDrawableTester");
                Rect copyBounds = drawable.copyBounds();
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int i3 = 40;
                if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                    i2 = 40;
                } else {
                    i3 = Math.min(intrinsicWidth, 40);
                    i2 = Math.min(intrinsicHeight, 40);
                }
                Bitmap createBitmap = Bitmap.createBitmap(i3, i2, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, createBitmap.getWidth(), createBitmap.getHeight());
                drawable.draw(canvas);
                drawable.setBounds(copyBounds);
                boolean z = i != 0;
                this.mFilterTransparent = z;
                if (z) {
                    AlphaFilterQuantizer alphaFilterQuantizer = ALPHA_FILTER_QUANTIZER;
                    if (i != 2) {
                        alphaFilterQuantizer.mFilter = alphaFilterQuantizer.mTransparentFilter;
                    } else {
                        alphaFilterQuantizer.mFilter = alphaFilterQuantizer.mTranslucentFilter;
                    }
                    maximumColorCount = new Palette.Builder(createBitmap, alphaFilterQuantizer).maximumColorCount(5);
                } else {
                    maximumColorCount = new Palette.Builder(createBitmap, (Quantizer) null).maximumColorCount(5);
                }
                this.mPalette = maximumColorCount.generate();
                createBitmap.recycle();
                Trace.traceEnd(32L);
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final int getDominantColor() {
                Palette.Swatch dominantSwatch = this.mPalette.getDominantSwatch();
                return dominantSwatch != null ? dominantSwatch.getInt() : EmergencyPhoneWidget.BG_COLOR;
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final boolean isComplexColor() {
                return this.mPalette.getSwatches().size() > 1;
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final boolean isGrayscale() {
                List swatches = this.mPalette.getSwatches();
                if (swatches != null) {
                    for (int size = swatches.size() - 1; size >= 0; size--) {
                        int i = ((Palette.Swatch) swatches.get(size)).getInt();
                        int red = Color.red(i);
                        int green = Color.green(i);
                        if (!(red == green && green == Color.blue(i))) {
                            return false;
                        }
                    }
                }
                return true;
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final float passFilterRatio() {
                if (this.mFilterTransparent) {
                    return ALPHA_FILTER_QUANTIZER.mPassFilterRatio;
                }
                return 1.0f;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SingleColorTester implements ColorTester {
            public final ColorDrawable mColorDrawable;

            public SingleColorTester(ColorDrawable colorDrawable) {
                this.mColorDrawable = colorDrawable;
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final int getDominantColor() {
                return this.mColorDrawable.getColor();
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final boolean isComplexColor() {
                return false;
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final boolean isGrayscale() {
                int color = this.mColorDrawable.getColor();
                int red = Color.red(color);
                int green = Color.green(color);
                return red == green && green == Color.blue(color);
            }

            @Override // com.android.wm.shell.startingsurface.SplashscreenContentDrawer.DrawableColorTester.ColorTester
            public final float passFilterRatio() {
                return this.mColorDrawable.getAlpha() / 255.0f;
            }
        }

        public DrawableColorTester(Drawable drawable) {
            this(drawable, 0);
        }

        public DrawableColorTester(Drawable drawable, int i) {
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                if (layerDrawable.getNumberOfLayers() > 0) {
                    if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 428468608, 0, null, null);
                    }
                    drawable = layerDrawable.getDrawable(0);
                }
            }
            if (drawable != null) {
                this.mColorChecker = drawable instanceof ColorDrawable ? new SingleColorTester((ColorDrawable) drawable) : new ComplexDrawableTester(drawable, i);
            } else {
                int i2 = SplashscreenContentDrawer.mThemeBackgroundColor;
                this.mColorChecker = new SingleColorTester(new ColorDrawable(SplashscreenContentDrawer.getSystemBGColor()));
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HighResIconProvider {
        public boolean mLoadInDetail;
        public final Context mSharedContext;
        public final IconProvider mSharedIconProvider;
        public Context mStandaloneContext;
        public IconProvider mStandaloneIconProvider;

        public HighResIconProvider(Context context, IconProvider iconProvider) {
            this.mSharedContext = context;
            this.mSharedIconProvider = iconProvider;
        }

        public final Drawable getIcon(ActivityInfo activityInfo, int i, int i2, int i3) {
            this.mLoadInDetail = false;
            Drawable icon = (i >= i2 || i >= 320) ? this.mSharedIconProvider.getIcon(i2, activityInfo) : i3 == 2 ? loadFromStandalone(activityInfo, i, i2, i3) : loadFromStandalone(activityInfo, i, i2, -1);
            return icon == null ? this.mSharedContext.getPackageManager().getDefaultActivityIcon() : icon;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x004b  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0031  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Drawable loadFromStandalone(ActivityInfo activityInfo, int i, int i2, int i3) {
            Resources resourcesForApplication;
            if (this.mStandaloneContext == null) {
                Context context = this.mSharedContext;
                this.mStandaloneContext = context.createConfigurationContext(context.getResources().getConfiguration());
                this.mStandaloneIconProvider = new IconProvider(this.mStandaloneContext);
            }
            if (i3 != 2) {
                try {
                    resourcesForApplication = this.mStandaloneContext.getPackageManager().getResourcesForApplication(activityInfo.applicationInfo);
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
                }
                if (resourcesForApplication != null) {
                    Configuration configuration = resourcesForApplication.getConfiguration();
                    DisplayMetrics displayMetrics = resourcesForApplication.getDisplayMetrics();
                    configuration.densityDpi = i2;
                    displayMetrics.densityDpi = i2;
                    resourcesForApplication.updateConfiguration(configuration, displayMetrics);
                }
                Drawable icon = this.mStandaloneIconProvider.getIcon(i2, activityInfo);
                this.mLoadInDetail = true;
                if (resourcesForApplication != null) {
                    Configuration configuration2 = resourcesForApplication.getConfiguration();
                    DisplayMetrics displayMetrics2 = resourcesForApplication.getDisplayMetrics();
                    configuration2.densityDpi = i;
                    displayMetrics2.densityDpi = i;
                    resourcesForApplication.updateConfiguration(configuration2, displayMetrics2);
                }
                return icon;
            }
            resourcesForApplication = null;
            if (resourcesForApplication != null) {
            }
            Drawable icon2 = this.mStandaloneIconProvider.getIcon(i2, activityInfo);
            this.mLoadInDetail = true;
            if (resourcesForApplication != null) {
            }
            return icon2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PreLoadIconDataHandler extends Handler {
        public /* synthetic */ PreLoadIconDataHandler(SplashscreenContentDrawer splashscreenContentDrawer, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            ((HandlerExecutor) SplashscreenContentDrawer.this.mSplashScreenExecutor).execute(new RunnableC4126x43925de8(this, 2));
        }

        private PreLoadIconDataHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PreloadIconData {
        public Context mContext;
        public boolean mIsPreloaded;
        public Drawable[] mPreloadIconDrawable;
        public int mPreloadIconSize;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingObserver extends ContentObserver {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Handler mHandler;

        public SettingObserver(Handler handler) {
            super(handler);
            this.mHandler = handler;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            this.mHandler.post(new RunnableC4126x43925de8(this, 1));
        }

        public final void updateSettings(boolean z) {
            Resources resources;
            Application currentApplication;
            String str;
            String string = Settings.System.getString(SplashscreenContentDrawer.this.mContext.getContentResolver(), "current_sec_active_themepackage");
            int i = SplashscreenContentDrawer.mThemeBackgroundColor;
            final int i2 = 0;
            final int i3 = 1;
            if (string == null) {
                i = 0;
            } else if (!string.equals(SplashscreenContentDrawer.this.mThemePackageName) || z) {
                Drawable drawable = null;
                try {
                    resources = SplashscreenContentDrawer.this.mContext.getPackageManager().getResourcesForApplication("android");
                } catch (PackageManager.NameNotFoundException unused) {
                    Slog.e("ShellStartingWindow", "updateSettings: NameNotFoundException");
                    resources = null;
                }
                if (resources != null) {
                    int identifier = resources.getIdentifier("tw_screen_background_color_light", "color", "android");
                    if (identifier != 0) {
                        i = resources.getColor(identifier);
                    } else {
                        int identifier2 = resources.getIdentifier("tw_screen_background_light", "drawable", "android");
                        if (identifier2 != 0) {
                            drawable = resources.getDrawable(identifier2);
                        }
                    }
                }
                if (i == 0 && drawable == null && (currentApplication = ActivityThread.currentApplication()) != null) {
                    TypedArray obtainStyledAttributes = currentApplication.obtainStyledAttributes(R.styleable.Window);
                    if (obtainStyledAttributes.hasValue(1)) {
                        drawable = obtainStyledAttributes.getDrawable(1);
                    }
                }
                if (drawable != null) {
                    i = new DrawableColorTester(drawable, 1).mColorChecker.getDominantColor();
                }
            }
            if (i != SplashscreenContentDrawer.mThemeBackgroundColor) {
                SplashscreenContentDrawer.this.mColorCache.mColorMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        int i4 = 0;
                        switch (i2) {
                            case 0:
                                SplashscreenContentDrawer.ColorCache.Colors colors = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                                while (i4 < 2) {
                                    colors.mWindowColors[i4] = null;
                                    i4++;
                                }
                                break;
                            default:
                                SplashscreenContentDrawer.ColorCache.Colors colors2 = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                                while (i4 < 2) {
                                    colors2.mIconColors[i4] = null;
                                    i4++;
                                }
                                break;
                        }
                    }
                });
            }
            String string2 = Settings.System.getString(SplashscreenContentDrawer.this.mContext.getContentResolver(), "current_sec_appicon_theme_package");
            if ((string2 != null && !string2.equals(SplashscreenContentDrawer.this.mThemeIconPackageName)) || ((str = SplashscreenContentDrawer.this.mThemeIconPackageName) != null && !str.equals(string2))) {
                SplashscreenContentDrawer.this.mColorCache.mColorMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        int i4 = 0;
                        switch (i3) {
                            case 0:
                                SplashscreenContentDrawer.ColorCache.Colors colors = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                                while (i4 < 2) {
                                    colors.mWindowColors[i4] = null;
                                    i4++;
                                }
                                break;
                            default:
                                SplashscreenContentDrawer.ColorCache.Colors colors2 = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                                while (i4 < 2) {
                                    colors2.mIconColors[i4] = null;
                                    i4++;
                                }
                                break;
                        }
                    }
                });
            }
            SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
            splashscreenContentDrawer.mThemeIconPackageName = string2;
            splashscreenContentDrawer.mThemePackageName = string;
            SplashscreenContentDrawer.mThemeBackgroundColor = i;
            Slog.d("ShellStartingWindow", "updateSettings: theme=" + SplashscreenContentDrawer.this.mThemePackageName + ", iconTheme=" + SplashscreenContentDrawer.this.mThemeIconPackageName + ", color=" + Integer.toHexString(SplashscreenContentDrawer.mThemeBackgroundColor));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SplashScreenWindowAttrs {
        public int mWindowBgResId = 0;
        public int mWindowBgColor = 0;
        public Drawable mSplashScreenIcon = null;
        public Drawable mBrandingImage = null;
        public int mIconBgColor = 0;
        public Drawable mWindowBackground = null;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SplashViewBuilder {
        public final ActivityInfo mActivityInfo;
        public boolean mAllowHandleSolidColor;
        public final Context mContext;
        public int mDisplayId;
        public Drawable[] mFinalIconDrawables;
        public int mFinalIconSize;
        public Drawable mOverlayDrawable;
        public int mSuggestType;
        public int mThemeColor;
        public Consumer mUiThreadInitTask;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ShapeIconFactory extends BaseIconFactory {
            public ShapeIconFactory(SplashViewBuilder splashViewBuilder, Context context, int i, int i2) {
                super(context, i, i2, true);
            }
        }

        public SplashViewBuilder(Context context, ActivityInfo activityInfo) {
            this.mFinalIconSize = SplashscreenContentDrawer.this.mIconSize;
            this.mContext = context;
            this.mActivityInfo = activityInfo;
        }

        /* JADX WARN: Code restructure failed: missing block: B:69:0x00ef, code lost:
        
            if (r8 != null) goto L48;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final SplashScreenView build(boolean z) {
            String str;
            ActivityInfo activityInfo;
            Context context;
            int i;
            Drawable drawable;
            Drawable drawable2;
            Drawable icon;
            ColorCache.IconColor iconColor;
            int i2;
            long j;
            int i3 = this.mSuggestType;
            ActivityInfo activityInfo2 = this.mActivityInfo;
            Context context2 = this.mContext;
            int i4 = 0;
            SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
            if (i3 == 3 || i3 == 4) {
                str = "ShellStartingWindow";
                activityInfo = activityInfo2;
                context = context2;
                i = 1;
                this.mFinalIconSize = 0;
            } else {
                if (!z && this.mDisplayId == 0) {
                    PreloadIconData preloadIconData = splashscreenContentDrawer.mPreloadIcon;
                    int themeResId = context2.getThemeResId();
                    if (preloadIconData.mIsPreloaded && themeResId != 0 && themeResId == preloadIconData.mContext.getThemeResId()) {
                        Slog.d("ShellStartingWindow", "use preloaded icon");
                        PreloadIconData preloadIconData2 = splashscreenContentDrawer.mPreloadIcon;
                        this.mFinalIconSize = preloadIconData2.mPreloadIconSize;
                        this.mFinalIconDrawables = preloadIconData2.mPreloadIconDrawable;
                        str = "ShellStartingWindow";
                        activityInfo = activityInfo2;
                        context = context2;
                        i = 1;
                    }
                }
                SplashScreenWindowAttrs splashScreenWindowAttrs = splashscreenContentDrawer.mTmpAttrs;
                Drawable drawable3 = splashScreenWindowAttrs.mSplashScreenIcon;
                if (drawable3 != null) {
                    int i5 = splashScreenWindowAttrs.mIconBgColor;
                    if (i5 == 0 || i5 == this.mThemeColor) {
                        this.mFinalIconSize = (int) (this.mFinalIconSize * 1.2f);
                    }
                    createIconDrawable(drawable3, false, false);
                    str = "ShellStartingWindow";
                    activityInfo = activityInfo2;
                    context = context2;
                    i = 1;
                } else {
                    int i6 = context2.getResources().getConfiguration().densityDpi;
                    int i7 = (int) (((splashscreenContentDrawer.mIconSize / splashscreenContentDrawer.mDefaultIconSize) * i6 * 1.2f) + 0.5f);
                    Trace.traceBegin(32L, "getIcon");
                    String str2 = splashscreenContentDrawer.mThemeIconPackageName;
                    HighResIconProvider highResIconProvider = splashscreenContentDrawer.mHighResIconProvider;
                    if (str2 == null || context2.getUserId() != 0) {
                        int i8 = this.mDisplayId;
                        icon = i8 == 2 ? highResIconProvider.getIcon(activityInfo2, i6, i7, i8) : highResIconProvider.getIcon(activityInfo2, i6, i7, -1);
                    } else {
                        icon = activityInfo2.loadIcon(context2.getPackageManager());
                    }
                    Trace.traceEnd(32L);
                    if (icon instanceof AdaptiveIconDrawable) {
                        Trace.traceBegin(32L, "processAdaptiveIcon");
                        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) icon;
                        Drawable foreground = adaptiveIconDrawable.getForeground();
                        ColorCache colorCache = splashscreenContentDrawer.mColorCache;
                        String str3 = activityInfo2.packageName;
                        int iconResource = activityInfo2.getIconResource();
                        int i9 = splashscreenContentDrawer.mLastPackageContextConfigHash;
                        ColorCache.Colors colors = (ColorCache.Colors) colorCache.mColorMap.get(str3);
                        int i10 = (iconResource * 31) + i9;
                        int[] iArr = {0};
                        if (colors != null) {
                            iconColor = (ColorCache.IconColor) ColorCache.getCache(colors.mIconColors, i10, iArr);
                        } else {
                            colors = new ColorCache.Colors(i4);
                            colorCache.mColorMap.put(str3, colors);
                        }
                        DrawableColorTester drawableColorTester = new DrawableColorTester(foreground, 2);
                        DrawableColorTester drawableColorTester2 = new DrawableColorTester(adaptiveIconDrawable.getBackground());
                        DrawableColorTester.ColorTester colorTester = drawableColorTester.mColorChecker;
                        int dominantColor = colorTester.getDominantColor();
                        DrawableColorTester.ColorTester colorTester2 = drawableColorTester2.mColorChecker;
                        ColorCache.IconColor iconColor2 = new ColorCache.IconColor(i10, dominantColor, colorTester2.getDominantColor(), colorTester2.isComplexColor(), colorTester2.isGrayscale(), colorTester.passFilterRatio());
                        colors.mIconColors[iArr[0]] = iconColor2;
                        iconColor = iconColor2;
                        boolean z2 = ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled;
                        int i11 = iconColor.mBgColor;
                        int i12 = iconColor.mFgColor;
                        boolean z3 = iconColor.mIsBgComplex;
                        if (z2) {
                            context = context2;
                            str = "ShellStartingWindow";
                            activityInfo = activityInfo2;
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -1141104614, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp, null, String.valueOf(Integer.toHexString(i12)), String.valueOf(Integer.toHexString(i11)), Boolean.valueOf(z3), Boolean.valueOf(iconColor.mReuseCount > 0), String.valueOf(Integer.toHexString(this.mThemeColor)));
                        } else {
                            str = "ShellStartingWindow";
                            activityInfo = activityInfo2;
                            context = context2;
                        }
                        if (z3 || splashscreenContentDrawer.mTmpAttrs.mIconBgColor != 0 || (!SplashscreenContentDrawer.m2758$$Nest$smisRgbSimilarInHsv(this.mThemeColor, i11) && (!iconColor.mIsBgGrayscale || SplashscreenContentDrawer.m2758$$Nest$smisRgbSimilarInHsv(this.mThemeColor, i12)))) {
                            i2 = 0;
                            if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1288760762, 0, null, null);
                            }
                            createIconDrawable(icon, false, highResIconProvider.mLoadInDetail);
                        } else {
                            if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1960014443, 0, null, null);
                            }
                            this.mFinalIconSize = (int) ((splashscreenContentDrawer.mIconSize * (iconColor.mFgNonTranslucentRatio < 0.44444445f ? 1.2f : 1.0f)) + 0.5f);
                            i2 = 0;
                            createIconDrawable(foreground, false, highResIconProvider.mLoadInDetail);
                        }
                        j = 32;
                        Trace.traceEnd(32L);
                        i4 = 1;
                    } else {
                        str = "ShellStartingWindow";
                        activityInfo = activityInfo2;
                        context = context2;
                        j = 32;
                        i2 = 0;
                    }
                    if (i4 == 0) {
                        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 888452073, i2, null, null);
                        }
                        Trace.traceBegin(j, "legacy_icon_factory");
                        Bitmap createScaledBitmap = new ShapeIconFactory(this, splashscreenContentDrawer.mContext, i7, this.mFinalIconSize).createScaledBitmap(i2, icon);
                        Trace.traceEnd(j);
                        i = 1;
                        createIconDrawable(new BitmapDrawable(createScaledBitmap), true, highResIconProvider.mLoadInDetail);
                    }
                    i = 1;
                }
            }
            splashscreenContentDrawer.mHandler.removeMessages(i);
            PreloadIconData preloadIconData3 = splashscreenContentDrawer.mPreloadIcon;
            if (z) {
                Slog.d(str, "preload Icon " + activityInfo.packageName);
                int i13 = this.mFinalIconSize;
                Drawable[] drawableArr = this.mFinalIconDrawables;
                preloadIconData3.mContext = context;
                preloadIconData3.mPreloadIconSize = i13;
                preloadIconData3.mPreloadIconDrawable = drawableArr;
                preloadIconData3.mIsPreloaded = true;
                PreLoadIconDataHandler preLoadIconDataHandler = splashscreenContentDrawer.mHandler;
                preLoadIconDataHandler.sendMessageDelayed(preLoadIconDataHandler.obtainMessage(1), SplashscreenContentDrawer.CLEAR_PREALOD_ICON_TIMEOUT_MILLIS);
                return null;
            }
            Context context3 = context;
            Drawable drawable4 = null;
            preloadIconData3.mIsPreloaded = false;
            preloadIconData3.mContext = null;
            preloadIconData3.mPreloadIconDrawable = null;
            int i14 = this.mFinalIconSize;
            Drawable[] drawableArr2 = this.mFinalIconDrawables;
            Consumer consumer = this.mUiThreadInitTask;
            if (drawableArr2 != null) {
                Drawable drawable5 = drawableArr2.length > 0 ? drawableArr2[0] : null;
                if (drawableArr2.length > 1) {
                    drawable4 = drawable5;
                    drawable = drawableArr2[1];
                } else {
                    drawable4 = drawable5;
                    drawable = null;
                }
            } else {
                drawable = null;
            }
            Trace.traceBegin(32L, "fillViewWithIcon");
            SplashScreenView.Builder allowHandleSolidColor = new SplashScreenView.Builder(new ContextThemeWrapper(context3, splashscreenContentDrawer.mContext.getTheme())).setBackgroundColor(this.mThemeColor).setOverlayDrawable(this.mOverlayDrawable).setIconSize(i14).setIconBackground(drawable).setCenterViewDrawable(drawable4).setUiThreadInitConsumer(consumer).setAllowHandleSolidColor(this.mAllowHandleSolidColor);
            int i15 = this.mSuggestType;
            SplashScreenWindowAttrs splashScreenWindowAttrs2 = splashscreenContentDrawer.mTmpAttrs;
            if (i15 == 1 && (drawable2 = splashScreenWindowAttrs2.mBrandingImage) != null) {
                allowHandleSolidColor.setBrandingDrawable(drawable2, splashscreenContentDrawer.mBrandingImageWidth, splashscreenContentDrawer.mBrandingImageHeight);
            }
            Drawable drawable6 = splashScreenWindowAttrs2.mWindowBackground;
            if (drawable6 != null) {
                allowHandleSolidColor.setBackgroundDrawable(drawable6);
            }
            SplashScreenView build = allowHandleSolidColor.build();
            Trace.traceEnd(32L);
            return build;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x005b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void createIconDrawable(Drawable drawable, boolean z, boolean z2) {
            SplashScreenView.IconAnimateListener splashscreenIconDrawableFactory$ImmobileIconDrawable;
            SplashScreenView.IconAnimateListener splashscreenIconDrawableFactory$ImmobileIconDrawable2;
            SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
            if (z) {
                this.mFinalIconDrawables = new Drawable[]{new SplashscreenIconDrawableFactory$ImmobileIconDrawable(drawable, splashscreenContentDrawer.mDefaultIconSize, this.mFinalIconSize, z2, splashscreenContentDrawer.mSplashscreenWorkerHandler)};
                return;
            }
            int i = splashscreenContentDrawer.mTmpAttrs.mIconBgColor;
            int i2 = this.mThemeColor;
            int i3 = splashscreenContentDrawer.mDefaultIconSize;
            int i4 = this.mFinalIconSize;
            Handler handler = splashscreenContentDrawer.mSplashscreenWorkerHandler;
            boolean z3 = false;
            boolean z4 = (i == 0 || i == i2) ? false : true;
            if (drawable instanceof Animatable) {
                splashscreenIconDrawableFactory$ImmobileIconDrawable = new SplashscreenIconDrawableFactory$AnimatableIconAnimateListener(drawable);
            } else {
                if (drawable instanceof AdaptiveIconDrawable) {
                    splashscreenIconDrawableFactory$ImmobileIconDrawable2 = new SplashscreenIconDrawableFactory$ImmobileIconDrawable(drawable, i3, i4, z2, handler);
                    this.mFinalIconDrawables = new Drawable[]{splashscreenIconDrawableFactory$ImmobileIconDrawable2, !z3 ? new SplashscreenIconDrawableFactory$MaskBackgroundDrawable(i) : null};
                }
                splashscreenIconDrawableFactory$ImmobileIconDrawable = new SplashscreenIconDrawableFactory$ImmobileIconDrawable(new SplashscreenIconDrawableFactory$AdaptiveForegroundDrawable(drawable), i3, i4, z2, handler);
            }
            z3 = z4;
            splashscreenIconDrawableFactory$ImmobileIconDrawable2 = splashscreenIconDrawableFactory$ImmobileIconDrawable;
            this.mFinalIconDrawables = new Drawable[]{splashscreenIconDrawableFactory$ImmobileIconDrawable2, !z3 ? new SplashscreenIconDrawableFactory$MaskBackgroundDrawable(i) : null};
        }
    }

    /* renamed from: -$$Nest$smisRgbSimilarInHsv, reason: not valid java name */
    public static boolean m2758$$Nest$smisRgbSimilarInHsv(int i, int i2) {
        double d;
        if (i == i2) {
            return true;
        }
        float luminance = Color.luminance(i);
        float luminance2 = Color.luminance(i2);
        float f = luminance > luminance2 ? (luminance + 0.05f) / (luminance2 + 0.05f) : (luminance2 + 0.05f) / (luminance + 0.05f);
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -853329785, 32, null, String.valueOf(Integer.toHexString(i)), String.valueOf(Integer.toHexString(i2)), Double.valueOf(f));
        }
        if (f < 2.0f) {
            return true;
        }
        float[] fArr = new float[3];
        float[] fArr2 = new float[3];
        Color.colorToHSV(i, fArr);
        Color.colorToHSV(i2, fArr2);
        int abs = ((((int) Math.abs(fArr[0] - fArr2[0])) + 180) % 360) - 180;
        double pow = Math.pow(abs / 180.0f, 2.0d);
        double pow2 = Math.pow(fArr[1] - fArr2[1], 2.0d);
        double pow3 = Math.pow(fArr[2] - fArr2[2], 2.0d);
        double sqrt = Math.sqrt(((pow + pow2) + pow3) / 3.0d);
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            d = sqrt;
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -137676175, 2796201, null, Long.valueOf(abs), Double.valueOf(fArr[0]), Double.valueOf(fArr2[0]), Double.valueOf(fArr[1]), Double.valueOf(fArr2[1]), Double.valueOf(fArr[2]), Double.valueOf(fArr2[2]), Double.valueOf(pow), Double.valueOf(pow2), Double.valueOf(pow3), Double.valueOf(d));
        } else {
            d = sqrt;
        }
        return d < 0.1d;
    }

    public SplashscreenContentDrawer(Context context, IconProvider iconProvider, TransactionPool transactionPool, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mHighResIconProvider = new HighResIconProvider(context, iconProvider);
        this.mTransactionPool = transactionPool;
        this.mSplashScreenExecutor = shellExecutor;
        final HandlerThread handlerThread = new HandlerThread("wmshell.splashworker", -10);
        handlerThread.start();
        if (CoreRune.SYSPERF_VI_BOOST) {
            new Handler().postDelayed(new Runnable(this) { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer.1
                @Override // java.lang.Runnable
                public final void run() {
                    ICustomFrequencyManager asInterface;
                    IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
                    if (service == null || (asInterface = ICustomFrequencyManager.Stub.asInterface(service)) == null) {
                        return;
                    }
                    try {
                        asInterface.sendTid(Process.myPid(), handlerThread.getThreadId(), 4);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }, 10000L);
        }
        Handler threadHandler = handlerThread.getThreadHandler();
        this.mSplashscreenWorkerHandler = threadHandler;
        this.mColorCache = new ColorCache(context, threadHandler);
        SettingObserver settingObserver = new SettingObserver(threadHandler);
        this.mSettingObserver = settingObserver;
        settingObserver.mHandler.post(new RunnableC4126x43925de8(settingObserver, 0));
        this.mPreloadIcon = new PreloadIconData();
    }

    public static Context createContext(Context context, StartingWindowInfo startingWindowInfo, int i, int i2, DisplayManager displayManager) {
        String str;
        ActivityManager.RunningTaskInfo runningTaskInfo = startingWindowInfo.taskInfo;
        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
        if (activityInfo == null) {
            activityInfo = runningTaskInfo.topActivityInfo;
        }
        if (activityInfo == null || (str = activityInfo.packageName) == null) {
            return null;
        }
        int i3 = runningTaskInfo.displayId;
        int i4 = runningTaskInfo.taskId;
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 793751866, 80, null, str, String.valueOf(Integer.toHexString(i)), Long.valueOf(i4), Long.valueOf(i2));
        }
        Display display = displayManager.getDisplay(i3);
        if (display == null) {
            return null;
        }
        if (i3 != 0) {
            context = context.createDisplayContext(display);
        }
        if (context == null) {
            return null;
        }
        if (i != context.getThemeResId()) {
            try {
                context = context.createPackageContextAsUser(activityInfo.packageName, 4, UserHandle.of(runningTaskInfo.userId));
                context.setTheme(i);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.w("ShellStartingWindow", "Failed creating package context with package name " + activityInfo.packageName + " for user " + runningTaskInfo.userId, e);
                return null;
            }
        }
        Configuration configuration = runningTaskInfo.getConfiguration();
        if (configuration.diffPublicOnly(context.getResources().getConfiguration()) != 0) {
            if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -2038605641, 0, null, String.valueOf(configuration));
            }
            Context createConfigurationContext = context.createConfigurationContext(configuration);
            createConfigurationContext.setTheme(i);
            TypedArray obtainStyledAttributes = createConfigurationContext.obtainStyledAttributes(R.styleable.Window);
            int resourceId = obtainStyledAttributes.getResourceId(1, 0);
            if (resourceId != 0) {
                try {
                    if (createConfigurationContext.getDrawable(resourceId) != null) {
                        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 619393728, 0, null, String.valueOf(configuration));
                        }
                        context = createConfigurationContext;
                    }
                } catch (Resources.NotFoundException e2) {
                    Slog.w("ShellStartingWindow", "failed creating starting window for overrideConfig at taskId: " + i4, e2);
                    return null;
                }
            }
            obtainStyledAttributes.recycle();
        }
        return context;
    }

    public static WindowManager.LayoutParams createLayoutParameters(Context context, StartingWindowInfo startingWindowInfo, int i, CharSequence charSequence, int i2, IBinder iBinder) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(3);
        layoutParams.setFitInsetsSides(0);
        layoutParams.setFitInsetsTypes(0);
        layoutParams.format = i2;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
        int i3 = obtainStyledAttributes.getBoolean(14, false) ? android.R.bool.config_cecSetMenuLanguageEnabled_default : android.R.attr.transcriptMode;
        if (i != 4 || obtainStyledAttributes.getBoolean(33, false)) {
            i3 |= VideoPlayer.MEDIA_ERROR_SYSTEM;
        }
        layoutParams.layoutInDisplayCutoutMode = obtainStyledAttributes.getInt(50, layoutParams.layoutInDisplayCutoutMode);
        layoutParams.windowAnimations = obtainStyledAttributes.getResourceId(8, 0);
        obtainStyledAttributes.recycle();
        ActivityManager.RunningTaskInfo runningTaskInfo = startingWindowInfo.taskInfo;
        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
        if (activityInfo == null) {
            activityInfo = runningTaskInfo.topActivityInfo;
        }
        int i4 = runningTaskInfo.displayId;
        if ((i4 == 0 || i4 == 2) && startingWindowInfo.isKeyguardOccluded) {
            i3 |= 524288;
        }
        layoutParams.flags = 131096 | i3;
        layoutParams.token = iBinder;
        layoutParams.packageName = activityInfo.packageName;
        layoutParams.privateFlags |= 16;
        if (!context.getResources().getCompatibilityInfo().supportsScreen()) {
            layoutParams.privateFlags |= 128;
        }
        layoutParams.setTitle("Splash Screen " + ((Object) charSequence));
        return layoutParams;
    }

    public static int estimateWindowBGColor(Drawable drawable) {
        DrawableColorTester.ColorTester colorTester = new DrawableColorTester(drawable, 2).mColorChecker;
        if (colorTester.passFilterRatio() == 1.0f) {
            return colorTester.getDominantColor();
        }
        Slog.w("ShellStartingWindow", "Window background is translucent, fill background with black color");
        return getSystemBGColor();
    }

    public static long getShowingDuration(long j, long j2) {
        return j <= j2 ? j2 : j2 < 500 ? (j > 500 || j2 < 400) ? 400L : 500L : j2;
    }

    public static int getSystemBGColor() {
        Application currentApplication = ActivityThread.currentApplication();
        if (currentApplication == null) {
            Slog.e("ShellStartingWindow", "System context does not exist!");
            return EmergencyPhoneWidget.BG_COLOR;
        }
        int i = mThemeBackgroundColor;
        return i != 0 ? i : currentApplication.getResources().getColor(com.android.systemui.R.color.splash_window_background_default);
    }

    public static void getWindowAttrs(Context context, SplashScreenWindowAttrs splashScreenWindowAttrs) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
        splashScreenWindowAttrs.mWindowBgResId = obtainStyledAttributes.getResourceId(1, 0);
        Drawable drawable = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda4(obtainStyledAttributes, 0), null);
        splashScreenWindowAttrs.mWindowBackground = drawable;
        if (drawable != null && !(drawable instanceof BitmapDrawable)) {
            splashScreenWindowAttrs.mWindowBackground = null;
        }
        Drawable drawable2 = splashScreenWindowAttrs.mWindowBackground;
        if (drawable2 != null) {
            splashScreenWindowAttrs.mWindowBgColor = estimateWindowBGColor(drawable2);
        } else {
            splashScreenWindowAttrs.mWindowBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda4(obtainStyledAttributes, 1), 0)).intValue();
        }
        splashScreenWindowAttrs.mSplashScreenIcon = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda4(obtainStyledAttributes, 2), null);
        splashScreenWindowAttrs.mBrandingImage = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda4(obtainStyledAttributes, 3), null);
        splashScreenWindowAttrs.mIconBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda4(obtainStyledAttributes, 4), 0)).intValue();
        obtainStyledAttributes.recycle();
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1867686022, 12, null, String.valueOf(Integer.toHexString(splashScreenWindowAttrs.mWindowBgColor)), Boolean.valueOf(splashScreenWindowAttrs.mSplashScreenIcon != null));
        }
    }

    public static int peekWindowBGColor(Context context, SplashScreenWindowAttrs splashScreenWindowAttrs) {
        Drawable colorDrawable;
        Trace.traceBegin(32L, "peekWindowBGColor");
        if (splashScreenWindowAttrs.mWindowBgColor != 0) {
            colorDrawable = new ColorDrawable(splashScreenWindowAttrs.mWindowBgColor);
        } else {
            int i = splashScreenWindowAttrs.mWindowBgResId;
            if (i != 0) {
                colorDrawable = context.getDrawable(i);
            } else {
                colorDrawable = new ColorDrawable(getSystemBGColor());
                Slog.w("ShellStartingWindow", "Window background does not exist, using " + colorDrawable);
            }
        }
        int estimateWindowBGColor = estimateWindowBGColor(colorDrawable);
        Trace.traceEnd(32L);
        return estimateWindowBGColor;
    }

    public static Object safeReturnAttrDefault(SplashscreenContentDrawer$$ExternalSyntheticLambda4 splashscreenContentDrawer$$ExternalSyntheticLambda4, Object obj) {
        try {
            return splashscreenContentDrawer$$ExternalSyntheticLambda4.apply(obj);
        } catch (RuntimeException e) {
            Slog.w("ShellStartingWindow", "Get attribute fail, return default: " + e.getMessage());
            return obj;
        }
    }

    public final void applyExitAnimation(final SplashScreenView splashScreenView, final SurfaceControl surfaceControl, final Rect rect, final Runnable runnable, long j, final float f, long j2) {
        Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SplashScreenExitAnimationUtils.RadialVanishAnimation radialVanishAnimation;
                View view;
                SplashScreenExitAnimationUtils.ShiftUpAnimation shiftUpAnimation;
                SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
                SplashScreenView splashScreenView2 = splashScreenView;
                SurfaceControl surfaceControl2 = surfaceControl;
                Rect rect2 = rect;
                Runnable runnable3 = runnable;
                float f2 = f;
                splashscreenContentDrawer.getClass();
                SplashScreenExitAnimation splashScreenExitAnimation = new SplashScreenExitAnimation(splashscreenContentDrawer.mContext, splashScreenView2, surfaceControl2, rect2, 0, splashscreenContentDrawer.mTransactionPool, runnable3, f2);
                final SplashScreenView splashScreenView3 = splashScreenExitAnimation.mSplashScreenView;
                SurfaceControl surfaceControl3 = splashScreenExitAnimation.mFirstWindowSurface;
                int i = splashScreenExitAnimation.mMainWindowShiftLength;
                TransactionPool transactionPool = splashScreenExitAnimation.mTransactionPool;
                Rect rect3 = splashScreenExitAnimation.mFirstWindowFrame;
                final int i2 = splashScreenExitAnimation.mAnimationDuration;
                final int i3 = splashScreenExitAnimation.mIconFadeOutDuration;
                final float f3 = splashScreenExitAnimation.mIconStartAlpha;
                final float f4 = splashScreenExitAnimation.mBrandingStartAlpha;
                final int i4 = splashScreenExitAnimation.mAppRevealDuration;
                float f5 = splashScreenExitAnimation.mRoundedCornerRadius;
                Interpolator interpolator = SplashScreenExitAnimationUtils.ICON_INTERPOLATOR;
                int height = splashScreenView3.getHeight() - 0;
                int width = splashScreenView3.getWidth() / 2;
                SplashScreenExitAnimationUtils.RadialVanishAnimation radialVanishAnimation2 = new SplashScreenExitAnimationUtils.RadialVanishAnimation(splashScreenView3);
                radialVanishAnimation2.mCircleCenter.set(width, 0);
                radialVanishAnimation2.mInitRadius = 0;
                radialVanishAnimation2.mFinishRadius = (int) ((((int) Math.sqrt((width * width) + (height * height))) * 1.25f) + 0.5d);
                radialVanishAnimation2.mVanishPaint.setShader(new RadialGradient(0.0f, 0.0f, 1.0f, new int[]{-1, -1, 0}, new float[]{0.0f, 0.8f, 1.0f}, Shader.TileMode.CLAMP));
                radialVanishAnimation2.mVanishPaint.setBlendMode(BlendMode.DST_OUT);
                if (surfaceControl3 == null || !surfaceControl3.isValid()) {
                    radialVanishAnimation = radialVanishAnimation2;
                    view = null;
                    shiftUpAnimation = null;
                } else {
                    view = new View(splashScreenView3.getContext());
                    view.setBackgroundColor(splashScreenView3.getInitBackgroundColor());
                    splashScreenView3.addView(view, new ViewGroup.LayoutParams(-1, i));
                    radialVanishAnimation = radialVanishAnimation2;
                    shiftUpAnimation = new SplashScreenExitAnimationUtils.ShiftUpAnimation(0.0f, -i, view, surfaceControl3, splashScreenView3, transactionPool, rect3, i, f5);
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(i2);
                ofFloat.setInterpolator(Interpolators.LINEAR);
                ofFloat.addListener(splashScreenExitAnimation);
                final SplashScreenExitAnimationUtils.RadialVanishAnimation radialVanishAnimation3 = radialVanishAnimation;
                ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.startingsurface.SplashScreenExitAnimationUtils.1
                    public final /* synthetic */ View val$finalOccludeHoleView;
                    public final /* synthetic */ RadialVanishAnimation val$radialVanishAnimation;
                    public final /* synthetic */ ViewGroup val$splashScreenView;

                    public C41241(final ViewGroup splashScreenView32, final RadialVanishAnimation radialVanishAnimation32, View view2) {
                        r2 = splashScreenView32;
                        r3 = radialVanishAnimation32;
                        r4 = view2;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        final SurfaceControl surfaceControl4;
                        super.onAnimationEnd(animator);
                        ShiftUpAnimation shiftUpAnimation2 = ShiftUpAnimation.this;
                        if (shiftUpAnimation2 != null && (surfaceControl4 = shiftUpAnimation2.mFirstWindowSurface) != null && surfaceControl4.isValid()) {
                            TransactionPool transactionPool2 = shiftUpAnimation2.mTransactionPool;
                            SurfaceControl.Transaction acquire = transactionPool2.acquire();
                            if (shiftUpAnimation2.mSplashScreenView.isAttachedToWindow()) {
                                acquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                                shiftUpAnimation2.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl4).withWindowCrop((Rect) null).withMergeTransaction(acquire).build()});
                            } else {
                                acquire.setWindowCrop(surfaceControl4, null);
                                acquire.apply();
                            }
                            transactionPool2.release(acquire);
                            Choreographer.getSfInstance().postCallback(4, new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashScreenExitAnimationUtils$ShiftUpAnimation$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    surfaceControl4.release();
                                }
                            }, null);
                        }
                        r2.removeView(r3);
                        r2.removeView(r4);
                    }
                });
                final SplashScreenExitAnimationUtils.ShiftUpAnimation shiftUpAnimation2 = shiftUpAnimation;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.startingsurface.SplashScreenExitAnimationUtils$$ExternalSyntheticLambda0
                    public final /* synthetic */ int f$5 = 0;

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        View view2;
                        View view3;
                        SurfaceControl surfaceControl4;
                        int i5 = i3;
                        int i6 = i2;
                        ViewGroup viewGroup = splashScreenView32;
                        float f6 = f3;
                        float f7 = f4;
                        int i7 = this.f$5;
                        int i8 = i4;
                        SplashScreenExitAnimationUtils.RadialVanishAnimation radialVanishAnimation4 = radialVanishAnimation32;
                        SplashScreenExitAnimationUtils.ShiftUpAnimation shiftUpAnimation3 = shiftUpAnimation2;
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue() * i6;
                        float interpolation = ((PathInterpolator) SplashScreenExitAnimationUtils.ICON_INTERPOLATOR).getInterpolation(MathUtils.constrain((floatValue - 0) / i5, 0.0f, 1.0f));
                        if (viewGroup instanceof SplashScreenView) {
                            SplashScreenView splashScreenView4 = (SplashScreenView) viewGroup;
                            view2 = splashScreenView4.getIconView();
                            view3 = splashScreenView4.getBrandingView();
                        } else {
                            view2 = null;
                            view3 = null;
                        }
                        if (view2 != null) {
                            view2.setAlpha((1.0f - interpolation) * f6);
                        }
                        if (view3 != null) {
                            view3.setAlpha((1.0f - interpolation) * f7);
                        }
                        float constrain = MathUtils.constrain((floatValue - i7) / i8, 0.0f, 1.0f);
                        if (radialVanishAnimation4.mVanishPaint.getShader() != null) {
                            float interpolation2 = ((PathInterpolator) SplashScreenExitAnimationUtils.MASK_RADIUS_INTERPOLATOR).getInterpolation(constrain);
                            float interpolation3 = ((PathInterpolator) Interpolators.ALPHA_OUT).getInterpolation(constrain);
                            float f8 = ((radialVanishAnimation4.mFinishRadius - r3) * interpolation2) + radialVanishAnimation4.mInitRadius;
                            radialVanishAnimation4.mVanishMatrix.setScale(f8, f8);
                            Matrix matrix = radialVanishAnimation4.mVanishMatrix;
                            Point point = radialVanishAnimation4.mCircleCenter;
                            matrix.postTranslate(point.x, point.y);
                            radialVanishAnimation4.mVanishPaint.getShader().setLocalMatrix(radialVanishAnimation4.mVanishMatrix);
                            radialVanishAnimation4.mVanishPaint.setAlpha(Math.round(interpolation3 * 255.0f));
                            radialVanishAnimation4.postInvalidate();
                        }
                        if (shiftUpAnimation3 == null || (surfaceControl4 = shiftUpAnimation3.mFirstWindowSurface) == null || !surfaceControl4.isValid() || !shiftUpAnimation3.mSplashScreenView.isAttachedToWindow()) {
                            return;
                        }
                        float interpolation4 = ((PathInterpolator) SplashScreenExitAnimationUtils.SHIFT_UP_INTERPOLATOR).getInterpolation(constrain);
                        float f9 = shiftUpAnimation3.mToYDelta;
                        float f10 = shiftUpAnimation3.mFromYDelta;
                        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f9, f10, interpolation4, f10);
                        shiftUpAnimation3.mOccludeHoleView.setTranslationY(m20m);
                        Matrix matrix2 = shiftUpAnimation3.mTmpTransform;
                        matrix2.setTranslate(0.0f, m20m);
                        TransactionPool transactionPool2 = shiftUpAnimation3.mTransactionPool;
                        SurfaceControl.Transaction acquire = transactionPool2.acquire();
                        acquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                        Rect rect4 = shiftUpAnimation3.mFirstWindowFrame;
                        matrix2.postTranslate(rect4.left, rect4.top + shiftUpAnimation3.mMainWindowShiftLength);
                        shiftUpAnimation3.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl4).withMatrix(matrix2).withMergeTransaction(acquire).build()});
                        transactionPool2.release(acquire);
                    }
                });
                ofFloat.start();
            }
        };
        if (splashScreenView.getIconView() == null) {
            runnable2.run();
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - j;
        long showingDuration = getShowingDuration(splashScreenView.getIconAnimationDuration() != null ? splashScreenView.getIconAnimationDuration().toMillis() : 0L, uptimeMillis) - uptimeMillis;
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 482713286, 0, null, String.valueOf(showingDuration));
        }
        if (showingDuration > 0) {
            splashScreenView.postDelayed(runnable2, showingDuration);
            return;
        }
        int integer = (int) ((j2 - uptimeMillis) - this.mContext.getResources().getInteger(com.android.systemui.R.integer.starting_window_app_reveal_anim_duration_reduced));
        if (integer > 0) {
            splashScreenView.postDelayed(runnable2, integer);
        } else {
            runnable2.run();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0029, code lost:
    
        if (r5 != null) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getBGColorFromCache(ActivityInfo activityInfo, IntSupplier intSupplier) {
        ColorCache.WindowColor windowColor;
        ColorCache colorCache = this.mColorCache;
        String str = activityInfo.packageName;
        int i = this.mLastPackageContextConfigHash;
        SplashScreenWindowAttrs splashScreenWindowAttrs = this.mTmpAttrs;
        int i2 = splashScreenWindowAttrs.mWindowBgColor;
        int i3 = splashScreenWindowAttrs.mWindowBgResId;
        ColorCache.Colors colors = (ColorCache.Colors) colorCache.mColorMap.get(str);
        int i4 = (((i * 31) + i2) * 31) + i3;
        int i5 = 0;
        int[] iArr = {0};
        if (colors != null) {
            windowColor = (ColorCache.WindowColor) ColorCache.getCache(colors.mWindowColors, i4, iArr);
        } else {
            colors = new ColorCache.Colors(i5);
            colorCache.mColorMap.put(str, colors);
        }
        windowColor = new ColorCache.WindowColor(i4, intSupplier.getAsInt());
        colors.mWindowColors[iArr[0]] = windowColor;
        return windowColor.mBgColor;
    }

    public final SplashScreenView makeSplashScreenContentView(Context context, StartingWindowInfo startingWindowInfo, int i, Consumer consumer) {
        final Drawable drawable;
        updateDensity();
        SplashScreenWindowAttrs splashScreenWindowAttrs = this.mTmpAttrs;
        getWindowAttrs(context, splashScreenWindowAttrs);
        this.mLastPackageContextConfigHash = context.getResources().getConfiguration().hashCode();
        if (!context.getResources().getConfiguration().isDexMode() || context.getResources().getAssets().getSamsungThemeOverlays().size() <= 0) {
            if (i == 4) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
                int intValue = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda4(obtainStyledAttributes, 5), 0)).intValue();
                obtainStyledAttributes.recycle();
                if (intValue != 0) {
                    drawable = context.getDrawable(intValue);
                } else {
                    int i2 = splashScreenWindowAttrs.mWindowBgResId;
                    if (i2 != 0) {
                        drawable = context.getDrawable(i2);
                    }
                }
            }
            drawable = null;
        } else {
            drawable = new ColorDrawable(getSystemBGColor());
        }
        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
        if (activityInfo == null) {
            activityInfo = startingWindowInfo.taskInfo.topActivityInfo;
        }
        int bGColorFromCache = drawable != null ? getBGColorFromCache(activityInfo, new IntSupplier() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda5
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return SplashscreenContentDrawer.estimateWindowBGColor(drawable);
            }
        }) : getBGColorFromCache(activityInfo, new SplashscreenContentDrawer$$ExternalSyntheticLambda1(this, context, 1));
        SplashViewBuilder splashViewBuilder = new SplashViewBuilder(context, activityInfo);
        splashViewBuilder.mThemeColor = bGColorFromCache;
        splashViewBuilder.mOverlayDrawable = drawable;
        splashViewBuilder.mSuggestType = i;
        splashViewBuilder.mUiThreadInitTask = consumer;
        splashViewBuilder.mAllowHandleSolidColor = startingWindowInfo.allowHandleSolidColorSplashScreen();
        splashViewBuilder.mDisplayId = startingWindowInfo.taskInfo.displayId;
        return splashViewBuilder.build(false);
    }

    public final void updateDensity() {
        Context context = this.mContext;
        this.mIconSize = context.getResources().getDimensionPixelSize(17106171);
        this.mDefaultIconSize = context.getResources().getDimensionPixelSize(17106170);
        this.mBrandingImageWidth = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_brand_image_width);
        this.mBrandingImageHeight = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_brand_image_height);
    }
}
