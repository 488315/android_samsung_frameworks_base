package com.android.wm.shell.startingsurface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
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
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.Choreographer;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.window.SplashScreenView;
import android.window.StartingWindowInfo;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.internal.R;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.Quantizer;
import com.android.internal.graphics.palette.VariationalKMeansQuantizer;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.samsung.android.rune.CoreRune;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.UnaryOperator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplashscreenContentDrawer {
    public static final int CLEAR_PREALOD_ICON_TIMEOUT_MILLIS = ViewConfiguration.getLongPressTimeout() + 200;
    public static boolean mIsNightMode = false;
    public static int mThemeBackgroundColor;
    public int mBrandingImageHeight;
    public int mBrandingImageWidth;
    public final boolean mCanUseAppIconForSplashScreen;
    final ColorCache mColorCache;
    public final Context mContext;
    public int mDefaultIconSize;
    public float mEnlargeForegroundIconThreshold;
    public final HighResIconProvider mHighResIconProvider;
    public int mIconSize;
    public int mLastPackageContextConfigHash;
    public int mMainWindowShiftLength;
    public float mNoBackgroundScale;
    public final PreloadIconData mPreloadIcon;
    public final SettingObserver mSettingObserver;
    public final ShellExecutor mSplashScreenExecutor;
    public final Handler mSplashscreenWorkerHandler;
    public String mThemeIconPackageName;
    public String mThemePackageName;
    public final TransactionPool mTransactionPool;
    public final SplashScreenWindowAttrs mTmpAttrs = new SplashScreenWindowAttrs();
    public final PreLoadIconDataHandler mHandler = new PreLoadIconDataHandler(this, 0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class ColorCache extends BroadcastReceiver {
        public final ArrayMap mColorMap = new ArrayMap();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Cache {
            public final int mHash;
            public int mReuseCount;

            public Cache(int i) {
                this.mHash = i;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Colors {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class IconColor extends Cache {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class WindowColor extends Cache {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DrawableColorTester {
        public final ColorTester mColorChecker;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public interface ColorTester {
            int getDominantColor();

            boolean isComplexColor();

            boolean isGrayscale();

            float passFilterRatio();
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class ComplexDrawableTester implements ColorTester {
            public static final AlphaFilterQuantizer ALPHA_FILTER_QUANTIZER = new AlphaFilterQuantizer(0);
            public final boolean mFilterTransparent;
            public final Palette mPalette;

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            public class AlphaFilterQuantizer implements Quantizer {
                public IntPredicate mFilter;
                public final Quantizer mInnerQuantizer;
                public float mPassFilterRatio;
                public final SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester$AlphaFilterQuantizer$$ExternalSyntheticLambda0 mTranslucentFilter;
                public final SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester$AlphaFilterQuantizer$$ExternalSyntheticLambda0 mTransparentFilter;

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
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 2086008807179892616L, 0, null);
                        }
                        this.mInnerQuantizer.quantize(iArr, i);
                        return;
                    }
                    this.mPassFilterRatio = i3 / iArr.length;
                    int[] iArr2 = new int[i3];
                    for (int length2 = iArr.length - 1; length2 > 0; length2--) {
                        if (this.mFilter.test(iArr[length2])) {
                            iArr2[i2] = iArr[length2];
                            i2++;
                        }
                    }
                    this.mInnerQuantizer.quantize(iArr2, i);
                }

                /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester$AlphaFilterQuantizer$$ExternalSyntheticLambda0, java.util.function.IntPredicate] */
                /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester$AlphaFilterQuantizer$$ExternalSyntheticLambda0] */
                private AlphaFilterQuantizer() {
                    this.mInnerQuantizer = new VariationalKMeansQuantizer();
                    final int i = 0;
                    ?? r0 = new IntPredicate() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester$AlphaFilterQuantizer$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i2) {
                            switch (i) {
                                case 0:
                                    if (((-16777216) & i2) != 0) {
                                    }
                                    break;
                                default:
                                    if ((i2 & (-16777216)) == -16777216) {
                                    }
                                    break;
                            }
                            return false;
                        }
                    };
                    this.mTransparentFilter = r0;
                    final int i2 = 1;
                    this.mTranslucentFilter = new IntPredicate() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$DrawableColorTester$ComplexDrawableTester$AlphaFilterQuantizer$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntPredicate
                        public final boolean test(int i22) {
                            switch (i2) {
                                case 0:
                                    if (((-16777216) & i22) != 0) {
                                    }
                                    break;
                                default:
                                    if ((i22 & (-16777216)) == -16777216) {
                                    }
                                    break;
                            }
                            return false;
                        }
                    };
                    this.mFilter = r0;
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
                if (dominantSwatch != null) {
                    return dominantSwatch.getInt();
                }
                return -16777216;
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
                        int blue = Color.blue(i);
                        if (red != green || green != blue) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class SingleColorTester implements ColorTester {
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
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -2298264882754273930L, 0, null);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HighResIconProvider {
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
            return (i >= i2 || i >= 320) ? this.mSharedIconProvider.getIcon(activityInfo, i2) : SplashscreenContentDrawer.isExternalDesktopMode(i3, this.mSharedContext) ? loadFromStandalone(activityInfo, i, i2, i3) : (CoreRune.BAIDU_CARLIFE && SplashscreenContentDrawer.m3261$$Nest$smisCarLifeDisplay(i3, this.mSharedContext)) ? loadFromStandalone(activityInfo, i, i2, i3) : loadFromStandalone(activityInfo, i, i2, -1);
        }

        public final Drawable loadFromStandalone(ActivityInfo activityInfo, int i, int i2, int i3) {
            if (this.mStandaloneContext == null) {
                this.mStandaloneContext = this.mSharedContext.createConfigurationContext(this.mSharedContext.getResources().getConfiguration());
                this.mStandaloneIconProvider = new IconProvider(this.mStandaloneContext);
            }
            Resources resources = null;
            if (!SplashscreenContentDrawer.isExternalDesktopMode(i3, this.mSharedContext) && (!CoreRune.BAIDU_CARLIFE || !SplashscreenContentDrawer.m3261$$Nest$smisCarLifeDisplay(i3, this.mSharedContext))) {
                try {
                    resources = this.mStandaloneContext.getPackageManager().getResourcesForApplication(activityInfo.applicationInfo);
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
                }
            }
            if (resources != null) {
                Configuration configuration = resources.getConfiguration();
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                configuration.densityDpi = i2;
                displayMetrics.densityDpi = i2;
                resources.updateConfiguration(configuration, displayMetrics);
            }
            Drawable icon = this.mStandaloneIconProvider.getIcon(activityInfo, i2);
            this.mLoadInDetail = true;
            if (resources != null) {
                Configuration configuration2 = resources.getConfiguration();
                DisplayMetrics displayMetrics2 = resources.getDisplayMetrics();
                configuration2.densityDpi = i;
                displayMetrics2.densityDpi = i;
                resources.updateConfiguration(configuration2, displayMetrics2);
            }
            return icon;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PreLoadIconDataHandler extends Handler {
        public /* synthetic */ PreLoadIconDataHandler(SplashscreenContentDrawer splashscreenContentDrawer, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            SplashscreenContentDrawer.this.mSplashScreenExecutor.execute(new SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda0(this, 2));
        }

        private PreLoadIconDataHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PreloadIconData {
        public Context mContext;
        public boolean mIsPreloaded;
        public Drawable[] mPreloadIconDrawable;
        public int mPreloadIconSize;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SettingObserver extends ContentObserver {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Handler mHandler;

        public SettingObserver(Handler handler) {
            super(handler);
            this.mHandler = handler;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri, int i) {
            this.mHandler.post(new SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda0(this, 1));
        }

        public final void updateSettings(boolean z) {
            Resources resources;
            Application currentApplication;
            ContentResolver contentResolver = SplashscreenContentDrawer.this.mContext.getContentResolver();
            String string = Settings.System.getString(contentResolver, SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE);
            int i = SplashscreenContentDrawer.mThemeBackgroundColor;
            if (TextUtils.isEmpty(string)) {
                i = 0;
            } else if (!string.equals(SplashscreenContentDrawer.this.mThemePackageName) || z) {
                Drawable drawable = null;
                try {
                    resources = SplashscreenContentDrawer.this.mContext.getPackageManager().getResourcesForApplication("android");
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.e("ShellStartingWindow", "updateSettings: NameNotFoundException, " + e);
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
                final int i2 = 0;
                SplashscreenContentDrawer.this.mColorCache.mColorMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        int i3 = 0;
                        SplashscreenContentDrawer.ColorCache.Colors colors = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                        switch (i2) {
                            case 0:
                                int i4 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i3 < 2) {
                                    colors.mWindowColors[i3] = null;
                                    i3++;
                                }
                                break;
                            default:
                                int i5 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i3 < 2) {
                                    colors.mIconColors[i3] = null;
                                    i3++;
                                }
                                break;
                        }
                    }
                });
            }
            String string2 = Settings.System.getString(contentResolver, SettingsHelper.INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE);
            if ((!TextUtils.isEmpty(string2) && !string2.equals(SplashscreenContentDrawer.this.mThemeIconPackageName)) || (!TextUtils.isEmpty(SplashscreenContentDrawer.this.mThemeIconPackageName) && !SplashscreenContentDrawer.this.mThemeIconPackageName.equals(string2))) {
                final int i3 = 1;
                SplashscreenContentDrawer.this.mColorCache.mColorMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        int i32 = 0;
                        SplashscreenContentDrawer.ColorCache.Colors colors = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                        switch (i3) {
                            case 0:
                                int i4 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i32 < 2) {
                                    colors.mWindowColors[i32] = null;
                                    i32++;
                                }
                                break;
                            default:
                                int i5 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i32 < 2) {
                                    colors.mIconColors[i32] = null;
                                    i32++;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SplashScreenWindowAttrs {
        public int mWindowBgResId = 0;
        public int mWindowBgColor = 0;
        public Drawable mSplashScreenIcon = null;
        public Drawable mBrandingImage = null;
        public int mIconBgColor = 0;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SplashViewBuilder {
        public final ActivityInfo mActivityInfo;
        public boolean mAllowHandleSolidColor;
        public final Context mContext;
        public int mDisplayId;
        public Drawable[] mFinalIconDrawables;
        public int mFinalIconSize;
        public Drawable mOverlayDrawable;
        public int mSuggestType;
        public int mThemeColor;
        public SplashscreenWindowCreator$$ExternalSyntheticLambda2 mUiThreadInitTask;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class ShapeIconFactory extends BaseIconFactory {
            public ShapeIconFactory(SplashViewBuilder splashViewBuilder, Context context, int i, int i2) {
                super(context, i, i2);
            }
        }

        public SplashViewBuilder(Context context, ActivityInfo activityInfo) {
            this.mFinalIconSize = SplashscreenContentDrawer.this.mIconSize;
            this.mContext = context;
            this.mActivityInfo = activityInfo;
        }

        /* JADX WARN: Code restructure failed: missing block: B:59:0x0146, code lost:
        
            if (r10 != null) goto L52;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.window.SplashScreenView build(boolean r27) {
            /*
                Method dump skipped, instructions count: 757
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.startingsurface.SplashscreenContentDrawer.SplashViewBuilder.build(boolean):android.window.SplashScreenView");
        }

        public final void createIconDrawable(Drawable drawable, boolean z, boolean z2) {
            Drawable splashscreenIconDrawableFactory$ImmobileIconDrawable;
            SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
            if (z) {
                this.mFinalIconDrawables = new Drawable[]{new SplashscreenIconDrawableFactory$ImmobileIconDrawable(drawable, splashscreenContentDrawer.mDefaultIconSize, this.mFinalIconSize, z2, splashscreenContentDrawer.mSplashscreenWorkerHandler)};
                return;
            }
            int i = splashscreenContentDrawer.mTmpAttrs.mIconBgColor;
            int i2 = this.mThemeColor;
            int i3 = splashscreenContentDrawer.mDefaultIconSize;
            int i4 = this.mFinalIconSize;
            boolean z3 = (i == 0 || i == i2) ? false : true;
            if (drawable instanceof Animatable) {
                splashscreenIconDrawableFactory$ImmobileIconDrawable = new SplashscreenIconDrawableFactory$AnimatableIconAnimateListener(drawable);
            } else {
                boolean z4 = drawable instanceof AdaptiveIconDrawable;
                Handler handler = splashscreenContentDrawer.mSplashscreenWorkerHandler;
                if (z4) {
                    z3 = false;
                    splashscreenIconDrawableFactory$ImmobileIconDrawable = new SplashscreenIconDrawableFactory$ImmobileIconDrawable(drawable, i3, i4, z2, handler);
                } else {
                    splashscreenIconDrawableFactory$ImmobileIconDrawable = new SplashscreenIconDrawableFactory$ImmobileIconDrawable(new SplashscreenIconDrawableFactory$AdaptiveForegroundDrawable(drawable), i3, i4, z2, handler);
                }
            }
            this.mFinalIconDrawables = new Drawable[]{splashscreenIconDrawableFactory$ImmobileIconDrawable, z3 ? new SplashscreenIconDrawableFactory$MaskBackgroundDrawable(i) : null};
        }
    }

    /* renamed from: -$$Nest$smisCarLifeDisplay, reason: not valid java name */
    public static boolean m3261$$Nest$smisCarLifeDisplay(int i, Context context) {
        Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(i);
        return (display == null || (display.getFlags() & 1048576) == 0) ? false : true;
    }

    /* renamed from: -$$Nest$smisRgbSimilarInHsv, reason: not valid java name */
    public static boolean m3262$$Nest$smisRgbSimilarInHsv(int i, int i2) {
        boolean z;
        boolean z2;
        double d;
        if (i != i2) {
            float luminance = Color.luminance(i);
            float luminance2 = Color.luminance(i2);
            float f = luminance > luminance2 ? (luminance + 0.05f) / (luminance2 + 0.05f) : (luminance2 + 0.05f) / (luminance + 0.05f);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -985106566387254744L, 32, String.valueOf(Integer.toHexString(i)), String.valueOf(Integer.toHexString(i2)), Double.valueOf(f));
            }
            if (f >= 2.0f) {
                float[] fArr = new float[3];
                float[] fArr2 = new float[3];
                Color.colorToHSV(i, fArr);
                Color.colorToHSV(i2, fArr2);
                int abs = ((((int) Math.abs(fArr[0] - fArr2[0])) + 180) % 360) - 180;
                double pow = Math.pow(abs / 180.0f, 2.0d);
                double pow2 = Math.pow(fArr[1] - fArr2[1], 2.0d);
                double pow3 = Math.pow(fArr[2] - fArr2[2], 2.0d);
                double sqrt = Math.sqrt(((pow + pow2) + pow3) / 3.0d);
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                    z = false;
                    z2 = true;
                    d = sqrt;
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 449850539114510075L, 2796201, Long.valueOf(abs), Double.valueOf(fArr[0]), Double.valueOf(fArr2[0]), Double.valueOf(fArr[1]), Double.valueOf(fArr2[1]), Double.valueOf(fArr[2]), Double.valueOf(fArr2[2]), Double.valueOf(pow), Double.valueOf(pow2), Double.valueOf(pow3), Double.valueOf(d));
                } else {
                    z = false;
                    z2 = true;
                    d = sqrt;
                }
                return d < 0.1d ? z2 : z;
            }
        }
        return true;
    }

    public SplashscreenContentDrawer(Context context, IconProvider iconProvider, TransactionPool transactionPool, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mHighResIconProvider = new HighResIconProvider(context, iconProvider);
        this.mTransactionPool = transactionPool;
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
        this.mCanUseAppIconForSplashScreen = context.getResources().getBoolean(com.android.systemui.R.bool.config_canUseAppIconForSplashScreen);
        SettingObserver settingObserver = new SettingObserver(threadHandler);
        this.mSettingObserver = settingObserver;
        settingObserver.mHandler.post(new SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda0(settingObserver, 0));
        this.mSplashScreenExecutor = shellExecutor;
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
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -3363243702063253373L, 80, String.valueOf(str), String.valueOf(Integer.toHexString(i)), Long.valueOf(i4), Long.valueOf(i2));
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
        if ((configuration.uiMode & 48) == (context.getResources().getConfiguration().uiMode & 48)) {
            return context;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 6600937306632370785L, 0, String.valueOf(configuration));
        }
        Context createConfigurationContext = context.createConfigurationContext(configuration);
        createConfigurationContext.setTheme(i);
        return createConfigurationContext;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x006e, code lost:
    
        if (com.android.wm.shell.shared.desktopmode.DesktopStateImpl.Companion.inDesktopWindowing(r6) != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.view.WindowManager.LayoutParams createLayoutParameters(android.content.Context r6, android.window.StartingWindowInfo r7, int r8, java.lang.CharSequence r9, int r10, android.os.IBinder r11) {
        /*
            android.view.WindowManager$LayoutParams r0 = new android.view.WindowManager$LayoutParams
            r1 = 3
            r0.<init>(r1)
            r2 = 0
            r0.setFitInsetsSides(r2)
            r0.setFitInsetsTypes(r2)
            r0.format = r10
            int[] r10 = com.android.internal.R.styleable.Window
            android.content.res.TypedArray r6 = r6.obtainStyledAttributes(r10)
            r10 = 14
            boolean r10 = r6.getBoolean(r10, r2)
            if (r10 == 0) goto L21
            r10 = 17891584(0x1110100, float:2.663301E-38)
            goto L24
        L21:
            r10 = 16843008(0x1010100, float:2.3694275E-38)
        L24:
            r3 = 4
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r8 != r3) goto L31
            r8 = 33
            boolean r8 = r6.getBoolean(r8, r2)
            if (r8 == 0) goto L32
        L31:
            r10 = r10 | r4
        L32:
            android.app.ActivityManager$RunningTaskInfo r8 = r7.taskInfo
            android.content.pm.ActivityInfo r3 = r7.targetActivityInfo
            if (r3 == 0) goto L39
            goto L3b
        L39:
            android.content.pm.ActivityInfo r3 = r8.topActivityInfo
        L3b:
            android.content.pm.ApplicationInfo r4 = r3.applicationInfo
            boolean r4 = com.android.internal.policy.PhoneWindow.isEdgeToEdgeEnforced(r4, r2, r6)
            if (r4 == 0) goto L49
            int r5 = r0.privateFlags
            r5 = r5 | 2048(0x800, float:2.87E-42)
            r0.privateFlags = r5
        L49:
            if (r4 == 0) goto L4c
            goto L4e
        L4c:
            int r1 = r0.layoutInDisplayCutoutMode
        L4e:
            r4 = 50
            int r1 = r6.getInt(r4, r1)
            r0.layoutInDisplayCutoutMode = r1
            r1 = 8
            int r1 = r6.getResourceId(r1, r2)
            r0.windowAnimations = r1
            r6.recycle()
            int r6 = r8.displayId
            if (r6 == 0) goto L70
            com.android.wm.shell.shared.desktopmode.DesktopStateImpl$Companion r8 = com.android.wm.shell.shared.desktopmode.DesktopStateImpl.Companion
            r8.getClass()
            boolean r6 = com.android.wm.shell.shared.desktopmode.DesktopStateImpl.Companion.inDesktopWindowing(r6)
            if (r6 == 0) goto L77
        L70:
            boolean r6 = r7.isKeyguardOccluded
            if (r6 == 0) goto L77
            r6 = 524288(0x80000, float:7.34684E-40)
            r10 = r10 | r6
        L77:
            r6 = 131096(0x20018, float:1.83705E-40)
            r6 = r6 | r10
            r0.flags = r6
            r0.token = r11
            java.lang.String r6 = r3.packageName
            r0.packageName = r6
            int r6 = r0.privateFlags
            r6 = r6 | 16
            r0.privateFlags = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Splash Screen "
            r6.<init>(r7)
            r6.append(r9)
            java.lang.String r6 = r6.toString()
            r0.setTitle(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.startingsurface.SplashscreenContentDrawer.createLayoutParameters(android.content.Context, android.window.StartingWindowInfo, int, java.lang.CharSequence, int, android.os.IBinder):android.view.WindowManager$LayoutParams");
    }

    public static int estimateWindowBGColor(Drawable drawable) {
        DrawableColorTester.ColorTester colorTester = new DrawableColorTester(drawable, 2).mColorChecker;
        if (colorTester.passFilterRatio() >= 0.5f) {
            return colorTester.getDominantColor();
        }
        Slog.w("ShellStartingWindow", "Window background is translucent, fill background with black color");
        return getSystemBGColor();
    }

    public static long getShowingDuration(long j, long j2) {
        return (j > j2 && j2 < 500) ? (j > 500 || j2 < 400) ? 400L : 500L : j2;
    }

    public static int getSystemBGColor() {
        Application currentApplication = ActivityThread.currentApplication();
        if (currentApplication == null) {
            Slog.e("ShellStartingWindow", "System context does not exist!");
            return -16777216;
        }
        int i = mThemeBackgroundColor;
        return i != 0 ? i : currentApplication.getResources().getColor(com.android.systemui.R.color.splash_window_background_default);
    }

    public static void getWindowAttrs(Context context, SplashScreenWindowAttrs splashScreenWindowAttrs) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
        splashScreenWindowAttrs.mWindowBgResId = obtainStyledAttributes.getResourceId(1, 0);
        splashScreenWindowAttrs.mWindowBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(obtainStyledAttributes, 0), 0)).intValue();
        splashScreenWindowAttrs.mSplashScreenIcon = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(obtainStyledAttributes, 2), null);
        splashScreenWindowAttrs.mBrandingImage = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(obtainStyledAttributes, 3), null);
        splashScreenWindowAttrs.mIconBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(obtainStyledAttributes, 4), 0)).intValue();
        obtainStyledAttributes.recycle();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -2397845537672908446L, 12, String.valueOf(Integer.toHexString(splashScreenWindowAttrs.mWindowBgColor)), Boolean.valueOf(splashScreenWindowAttrs.mSplashScreenIcon != null));
        }
    }

    public static boolean isExternalDesktopMode(int i, Context context) {
        Display display;
        return (i == -1 || i == 0 || (display = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i)) == null || !DisplayManager.isExternalDesktopDisplay(display)) ? false : true;
    }

    public static int peekWindowBGColor(Context context, SplashScreenWindowAttrs splashScreenWindowAttrs) {
        Drawable drawable;
        Trace.traceBegin(32L, "peekWindowBGColor");
        if (splashScreenWindowAttrs.mWindowBgColor != 0) {
            drawable = new ColorDrawable(splashScreenWindowAttrs.mWindowBgColor);
        } else {
            int i = splashScreenWindowAttrs.mWindowBgResId;
            if (i != 0) {
                try {
                    drawable = context.getDrawable(i);
                } catch (Resources.NotFoundException e) {
                    Slog.w("ShellStartingWindow", "Unable get drawable from resource", e);
                }
            }
            drawable = null;
        }
        if (drawable == null) {
            drawable = new ColorDrawable(getSystemBGColor());
            Slog.w("ShellStartingWindow", "Window background does not exist, using " + drawable);
        }
        int estimateWindowBGColor = estimateWindowBGColor(drawable);
        Trace.traceEnd(32L);
        return estimateWindowBGColor;
    }

    public static Object safeReturnAttrDefault(UnaryOperator unaryOperator, Object obj) {
        try {
            return unaryOperator.apply(obj);
        } catch (RuntimeException e) {
            Slog.w("ShellStartingWindow", "Get attribute fail, return default: " + e.getMessage());
            return obj;
        }
    }

    public final void applyExitAnimation(final SplashScreenView splashScreenView, final SurfaceControl surfaceControl, final Rect rect, final Runnable runnable, long j, final float f) {
        Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                SplashScreenView splashScreenView2;
                View view;
                final SplashScreenExitAnimationUtils.ShiftUpAnimation shiftUpAnimation;
                ValueAnimator valueAnimator;
                SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
                SplashScreenView splashScreenView3 = splashScreenView;
                SurfaceControl surfaceControl2 = surfaceControl;
                Rect rect2 = rect;
                Runnable runnable3 = runnable;
                float f2 = f;
                int i = SplashscreenContentDrawer.mThemeBackgroundColor;
                splashscreenContentDrawer.getClass();
                SplashScreenExitAnimation splashScreenExitAnimation = new SplashScreenExitAnimation(splashscreenContentDrawer.mContext, splashScreenView3, surfaceControl2, rect2, splashscreenContentDrawer.mMainWindowShiftLength, splashscreenContentDrawer.mTransactionPool, runnable3, f2);
                int i2 = splashScreenExitAnimation.mAnimationType;
                final SplashScreenView splashScreenView4 = splashScreenExitAnimation.mSplashScreenView;
                SurfaceControl surfaceControl3 = splashScreenExitAnimation.mFirstWindowSurface;
                int i3 = splashScreenExitAnimation.mMainWindowShiftLength;
                TransactionPool transactionPool = splashScreenExitAnimation.mTransactionPool;
                Rect rect3 = splashScreenExitAnimation.mFirstWindowFrame;
                final int i4 = splashScreenExitAnimation.mAnimationDuration;
                final int i5 = splashScreenExitAnimation.mIconFadeOutDuration;
                final float f3 = splashScreenExitAnimation.mIconStartAlpha;
                final float f4 = splashScreenExitAnimation.mBrandingStartAlpha;
                final int i6 = splashScreenExitAnimation.mAppRevealDuration;
                float f5 = splashScreenExitAnimation.mRoundedCornerRadius;
                Interpolator interpolator = SplashScreenExitAnimationUtils.ICON_INTERPOLATOR;
                if (i2 == 1) {
                    valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                    valueAnimator.setDuration(i4);
                    valueAnimator.setInterpolator(Interpolators.LINEAR);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            View view2;
                            View view3;
                            int i7 = i5;
                            int i8 = i4;
                            ViewGroup viewGroup = splashScreenView4;
                            float f6 = f3;
                            float f7 = f4;
                            int i9 = i6;
                            Interpolator interpolator2 = SplashScreenExitAnimationUtils.ICON_INTERPOLATOR;
                            float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            float interpolation = ((PathInterpolator) SplashScreenExitAnimationUtils.ICON_INTERPOLATOR).getInterpolation(SplashScreenExitAnimationUtils.getProgress(0L, i7, i8, floatValue));
                            if (viewGroup instanceof SplashScreenView) {
                                SplashScreenView splashScreenView5 = (SplashScreenView) viewGroup;
                                view2 = splashScreenView5.getIconView();
                                view3 = splashScreenView5.getBrandingView();
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
                            viewGroup.setAlpha(1.0f - ((PathInterpolator) Interpolators.ALPHA_OUT).getInterpolation(SplashScreenExitAnimationUtils.getProgress(0, i9, i8, floatValue)));
                        }
                    });
                    valueAnimator.addListener(splashScreenExitAnimation);
                } else {
                    int height = splashScreenView4.getHeight();
                    int width = splashScreenView4.getWidth() / 2;
                    final SplashScreenExitAnimationUtils.RadialVanishAnimation radialVanishAnimation = new SplashScreenExitAnimationUtils.RadialVanishAnimation(splashScreenView4);
                    radialVanishAnimation.mCircleCenter.set(width, 0);
                    radialVanishAnimation.mFinishRadius = (int) ((((int) Math.sqrt((width * width) + (height * height))) * 1.25f) + 0.5d);
                    radialVanishAnimation.mVanishPaint.setShader(new RadialGradient(0.0f, 0.0f, 1.0f, new int[]{-1, -1, 0}, new float[]{0.0f, 0.8f, 1.0f}, Shader.TileMode.CLAMP));
                    radialVanishAnimation.mVanishPaint.setBlendMode(BlendMode.DST_OUT);
                    if (surfaceControl3 == null || !surfaceControl3.isValid()) {
                        splashScreenView2 = splashScreenView4;
                        view = null;
                        shiftUpAnimation = null;
                    } else {
                        View view2 = new View(splashScreenView4.getContext());
                        view2.setBackgroundColor(splashScreenView4.getInitBackgroundColor());
                        splashScreenView4.addView(view2, new ViewGroup.LayoutParams(-1, i3));
                        splashScreenView2 = splashScreenView4;
                        shiftUpAnimation = new SplashScreenExitAnimationUtils.ShiftUpAnimation(0.0f, -i3, view2, surfaceControl3, splashScreenView2, transactionPool, rect3, i3, f5);
                        view = view2;
                    }
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    ofFloat.setDuration(i4);
                    ofFloat.setInterpolator(Interpolators.LINEAR);
                    ofFloat.addListener(splashScreenExitAnimation);
                    ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils.1
                        public final /* synthetic */ View val$finalOccludeHoleView;
                        public final /* synthetic */ RadialVanishAnimation val$radialVanishAnimation;
                        public final /* synthetic */ ViewGroup val$splashScreenView;

                        public AnonymousClass1(ViewGroup splashScreenView22, final RadialVanishAnimation radialVanishAnimation2, View view3) {
                            r2 = splashScreenView22;
                            r3 = radialVanishAnimation2;
                            r4 = view3;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            SurfaceControl surfaceControl4;
                            super.onAnimationEnd(animator);
                            ShiftUpAnimation shiftUpAnimation2 = ShiftUpAnimation.this;
                            if (shiftUpAnimation2 != null && (surfaceControl4 = shiftUpAnimation2.mFirstWindowSurface) != null && surfaceControl4.isValid()) {
                                TransactionPool transactionPool2 = shiftUpAnimation2.mTransactionPool;
                                SurfaceControl.Transaction acquire = transactionPool2.acquire();
                                if (shiftUpAnimation2.mSplashScreenView.isAttachedToWindow()) {
                                    acquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                                    shiftUpAnimation2.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(shiftUpAnimation2.mFirstWindowSurface).withWindowCrop((Rect) null).withMergeTransaction(acquire).build()});
                                } else {
                                    acquire.setWindowCrop(shiftUpAnimation2.mFirstWindowSurface, null);
                                    acquire.apply();
                                }
                                transactionPool2.release(acquire);
                                Choreographer sfInstance = Choreographer.getSfInstance();
                                final SurfaceControl surfaceControl5 = shiftUpAnimation2.mFirstWindowSurface;
                                Objects.requireNonNull(surfaceControl5);
                                sfInstance.postCallback(4, new Runnable() { // from class: com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils$ShiftUpAnimation$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        surfaceControl5.release();
                                    }
                                }, null);
                            }
                            r2.removeView(r3);
                            r2.removeView(r4);
                        }
                    });
                    final SplashScreenView splashScreenView5 = splashScreenView22;
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            View view3;
                            View view4;
                            SurfaceControl surfaceControl4;
                            int i7 = i5;
                            int i8 = i4;
                            ViewGroup viewGroup = splashScreenView5;
                            float f6 = f3;
                            float f7 = f4;
                            int i9 = i6;
                            SplashScreenExitAnimationUtils.RadialVanishAnimation radialVanishAnimation2 = radialVanishAnimation2;
                            SplashScreenExitAnimationUtils.ShiftUpAnimation shiftUpAnimation2 = shiftUpAnimation;
                            Interpolator interpolator2 = SplashScreenExitAnimationUtils.ICON_INTERPOLATOR;
                            float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            float interpolation = ((PathInterpolator) SplashScreenExitAnimationUtils.ICON_INTERPOLATOR).getInterpolation(SplashScreenExitAnimationUtils.getProgress(0L, i7, i8, floatValue));
                            if (viewGroup instanceof SplashScreenView) {
                                SplashScreenView splashScreenView6 = (SplashScreenView) viewGroup;
                                view3 = splashScreenView6.getIconView();
                                view4 = splashScreenView6.getBrandingView();
                            } else {
                                view3 = null;
                                view4 = null;
                            }
                            if (view3 != null) {
                                view3.setAlpha((1.0f - interpolation) * f6);
                            }
                            if (view4 != null) {
                                view4.setAlpha((1.0f - interpolation) * f7);
                            }
                            float progress = SplashScreenExitAnimationUtils.getProgress(0, i9, i8, floatValue);
                            if (radialVanishAnimation2.mVanishPaint.getShader() != null) {
                                float interpolation2 = ((PathInterpolator) SplashScreenExitAnimationUtils.MASK_RADIUS_INTERPOLATOR).getInterpolation(progress);
                                float interpolation3 = ((PathInterpolator) Interpolators.ALPHA_OUT).getInterpolation(progress);
                                float f8 = (radialVanishAnimation2.mFinishRadius * interpolation2) + 0;
                                radialVanishAnimation2.mVanishMatrix.setScale(f8, f8);
                                Matrix matrix = radialVanishAnimation2.mVanishMatrix;
                                Point point = radialVanishAnimation2.mCircleCenter;
                                matrix.postTranslate(point.x, point.y);
                                radialVanishAnimation2.mVanishPaint.getShader().setLocalMatrix(radialVanishAnimation2.mVanishMatrix);
                                radialVanishAnimation2.mVanishPaint.setAlpha(Math.round(interpolation3 * 255.0f));
                                radialVanishAnimation2.postInvalidate();
                            }
                            if (shiftUpAnimation2 == null || (surfaceControl4 = shiftUpAnimation2.mFirstWindowSurface) == null || !surfaceControl4.isValid() || !shiftUpAnimation2.mSplashScreenView.isAttachedToWindow()) {
                                return;
                            }
                            float interpolation4 = ((PathInterpolator) SplashScreenExitAnimationUtils.SHIFT_UP_INTERPOLATOR).getInterpolation(progress);
                            float f9 = shiftUpAnimation2.mToYDelta;
                            float f10 = shiftUpAnimation2.mFromYDelta;
                            float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f9, f10, interpolation4, f10);
                            shiftUpAnimation2.mOccludeHoleView.setTranslationY(m$1);
                            shiftUpAnimation2.mTmpTransform.setTranslate(0.0f, m$1);
                            TransactionPool transactionPool2 = shiftUpAnimation2.mTransactionPool;
                            SurfaceControl.Transaction acquire = transactionPool2.acquire();
                            acquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                            Matrix matrix2 = shiftUpAnimation2.mTmpTransform;
                            Rect rect4 = shiftUpAnimation2.mFirstWindowFrame;
                            matrix2.postTranslate(rect4.left, rect4.top + shiftUpAnimation2.mMainWindowShiftLength);
                            shiftUpAnimation2.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(shiftUpAnimation2.mFirstWindowSurface).withMatrix(shiftUpAnimation2.mTmpTransform).withMergeTransaction(acquire).build()});
                            transactionPool2.release(acquire);
                        }
                    });
                    valueAnimator = ofFloat;
                }
                valueAnimator.start();
            }
        };
        if (splashScreenView.getIconView() == null) {
            runnable2.run();
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - j;
        long showingDuration = getShowingDuration(splashScreenView.getIconAnimationDuration() != null ? splashScreenView.getIconAnimationDuration().toMillis() : 0L, uptimeMillis) - uptimeMillis;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -5778344489450129331L, 0, String.valueOf(showingDuration));
        }
        if (showingDuration > 0) {
            splashScreenView.postDelayed(runnable2, showingDuration);
        } else {
            runnable2.run();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0029, code lost:
    
        if (r5 != null) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getBGColorFromCache(android.content.pm.ActivityInfo r5, java.util.function.IntSupplier r6) {
        /*
            r4 = this;
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache r0 = r4.mColorCache
            java.lang.String r5 = r5.packageName
            int r1 = r4.mLastPackageContextConfigHash
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SplashScreenWindowAttrs r4 = r4.mTmpAttrs
            int r2 = r4.mWindowBgColor
            int r4 = r4.mWindowBgResId
            android.util.ArrayMap r3 = r0.mColorMap
            java.lang.Object r3 = r3.get(r5)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors r3 = (com.android.wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.Colors) r3
            int r1 = r1 * 31
            int r1 = r1 + r2
            int r1 = r1 * 31
            int r1 = r1 + r4
            r4 = 0
            int[] r2 = new int[]{r4}
            if (r3 == 0) goto L2c
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor[] r5 = r3.mWindowColors
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Cache r5 = com.android.wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.getCache(r5, r1, r2)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor r5 = (com.android.wm.shell.startingsurface.SplashscreenContentDrawer.ColorCache.WindowColor) r5
            if (r5 == 0) goto L36
            goto L45
        L2c:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors r3 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$Colors
            r3.<init>(r4)
            android.util.ArrayMap r0 = r0.mColorMap
            r0.put(r5, r3)
        L36:
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor r5 = new com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor
            int r6 = r6.getAsInt()
            r5.<init>(r1, r6)
            com.android.wm.shell.startingsurface.SplashscreenContentDrawer$ColorCache$WindowColor[] r6 = r3.mWindowColors
            r4 = r2[r4]
            r6[r4] = r5
        L45:
            int r4 = r5.mBgColor
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.startingsurface.SplashscreenContentDrawer.getBGColorFromCache(android.content.pm.ActivityInfo, java.util.function.IntSupplier):int");
    }

    public final SplashScreenView makeSplashScreenContentView(Context context, StartingWindowInfo startingWindowInfo, int i, SplashscreenWindowCreator$$ExternalSyntheticLambda2 splashscreenWindowCreator$$ExternalSyntheticLambda2) {
        final Drawable drawable;
        updateDensity();
        SplashScreenWindowAttrs splashScreenWindowAttrs = this.mTmpAttrs;
        getWindowAttrs(context, splashScreenWindowAttrs);
        this.mLastPackageContextConfigHash = context.getResources().getConfiguration().hashCode();
        if (i == 1 && !this.mCanUseAppIconForSplashScreen && splashScreenWindowAttrs.mSplashScreenIcon == null && (startingWindowInfo.startingWindowTypeParameter & 512) == 0) {
            i = 3;
        }
        if (!isExternalDesktopMode(startingWindowInfo.taskInfo.displayId, context) || context.getResources().getAssets().getSamsungThemeOverlays().size() <= 0) {
            Drawable drawable2 = null;
            if (i == 4) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
                int intValue = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(obtainStyledAttributes, 1), 0)).intValue();
                obtainStyledAttributes.recycle();
                if (intValue != 0) {
                    drawable2 = context.getDrawable(intValue);
                } else {
                    int i2 = splashScreenWindowAttrs.mWindowBgResId;
                    if (i2 != 0) {
                        drawable2 = context.getDrawable(i2);
                    }
                }
            }
            drawable = drawable2;
        } else {
            drawable = new ColorDrawable(ActivityThread.currentApplication().getResources().getColor(com.android.systemui.R.color.splash_window_background_default));
        }
        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
        if (activityInfo == null) {
            activityInfo = startingWindowInfo.taskInfo.topActivityInfo;
        }
        int bGColorFromCache = drawable != null ? getBGColorFromCache(activityInfo, new IntSupplier() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda8
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return SplashscreenContentDrawer.estimateWindowBGColor(drawable);
            }
        }) : getBGColorFromCache(activityInfo, new SplashscreenContentDrawer$$ExternalSyntheticLambda6(this, context, 1));
        SplashViewBuilder splashViewBuilder = new SplashViewBuilder(context, activityInfo);
        splashViewBuilder.mThemeColor = bGColorFromCache;
        splashViewBuilder.mOverlayDrawable = drawable;
        splashViewBuilder.mSuggestType = i;
        splashViewBuilder.mUiThreadInitTask = splashscreenWindowCreator$$ExternalSyntheticLambda2;
        splashViewBuilder.mAllowHandleSolidColor = startingWindowInfo.allowHandleSolidColorSplashScreen();
        splashViewBuilder.mDisplayId = startingWindowInfo.taskInfo.displayId;
        return splashViewBuilder.build(false);
    }

    public final void updateDensity() {
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(17106371);
        this.mDefaultIconSize = this.mContext.getResources().getDimensionPixelSize(17106370);
        this.mBrandingImageWidth = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_brand_image_width);
        this.mBrandingImageHeight = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_brand_image_height);
        this.mMainWindowShiftLength = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_exit_animation_window_shift_length);
        this.mEnlargeForegroundIconThreshold = this.mContext.getResources().getFloat(com.android.systemui.R.dimen.splash_icon_enlarge_foreground_threshold);
        this.mNoBackgroundScale = this.mContext.getResources().getFloat(com.android.systemui.R.dimen.splash_icon_no_background_scale_factor);
    }
}
