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
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
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
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.internal.R;
import com.android.internal.graphics.palette.Palette;
import com.android.internal.graphics.palette.Quantizer;
import com.android.internal.graphics.palette.VariationalKMeansQuantizer;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.launcher3.icons.BaseIconFactory;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.UnaryOperator;

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

    class ColorCache extends BroadcastReceiver {
        public final ArrayMap mColorMap = new ArrayMap();

        public class Cache {
            public final int mHash;
            public int mReuseCount;

            public Cache(int i) {
                this.mHash = i;
            }
        }

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

    public class DrawableColorTester {
        public final ColorTester mColorChecker;

        public interface ColorTester {
            int getDominantColor();

            boolean isComplexColor();

            boolean isGrayscale();

            float passFilterRatio();
        }

        public class ComplexDrawableTester implements ColorTester {
            public static final AlphaFilterQuantizer ALPHA_FILTER_QUANTIZER = new AlphaFilterQuantizer(0);
            public final boolean mFilterTransparent;
            public final Palette mPalette;

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
                int iMin;
                Palette.Builder builderMaximumColorCount;
                Trace.traceBegin(32L, "ComplexDrawableTester");
                Rect rectCopyBounds = drawable.copyBounds();
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int iMin2 = 40;
                if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                    iMin = 40;
                } else {
                    iMin2 = Math.min(intrinsicWidth, 40);
                    iMin = Math.min(intrinsicHeight, 40);
                }
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMin2, iMin, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
                drawable.draw(canvas);
                drawable.setBounds(rectCopyBounds);
                boolean z = i != 0;
                this.mFilterTransparent = z;
                if (z) {
                    AlphaFilterQuantizer alphaFilterQuantizer = ALPHA_FILTER_QUANTIZER;
                    if (i != 2) {
                        alphaFilterQuantizer.mFilter = alphaFilterQuantizer.mTransparentFilter;
                    } else {
                        alphaFilterQuantizer.mFilter = alphaFilterQuantizer.mTranslucentFilter;
                    }
                    builderMaximumColorCount = new Palette.Builder(bitmapCreateBitmap, alphaFilterQuantizer).maximumColorCount(5);
                } else {
                    builderMaximumColorCount = new Palette.Builder(bitmapCreateBitmap, (Quantizer) null).maximumColorCount(5);
                }
                this.mPalette = builderMaximumColorCount.generate();
                bitmapCreateBitmap.recycle();
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
                        int iRed = Color.red(i);
                        int iGreen = Color.green(i);
                        int iBlue = Color.blue(i);
                        if (iRed != iGreen || iGreen != iBlue) {
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
                int iRed = Color.red(color);
                int iGreen = Color.green(color);
                return iRed == iGreen && iGreen == Color.blue(color);
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
            return (i >= i2 || i >= 320) ? this.mSharedIconProvider.getIcon(activityInfo, i2) : SplashscreenContentDrawer.isExternalDesktopMode(i3, this.mSharedContext) ? loadFromStandalone(activityInfo, i, i2, i3) : (CoreRune.BAIDU_CARLIFE && SplashscreenContentDrawer.m3279$$Nest$smisCarLifeDisplay(i3, this.mSharedContext)) ? loadFromStandalone(activityInfo, i, i2, i3) : loadFromStandalone(activityInfo, i, i2, -1);
        }

        public final Drawable loadFromStandalone(ActivityInfo activityInfo, int i, int i2, int i3) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
            if (this.mStandaloneContext == null) {
                this.mStandaloneContext = this.mSharedContext.createConfigurationContext(this.mSharedContext.getResources().getConfiguration());
                this.mStandaloneIconProvider = new IconProvider(this.mStandaloneContext);
            }
            Resources resourcesForApplication = null;
            if (!SplashscreenContentDrawer.isExternalDesktopMode(i3, this.mSharedContext) && (!CoreRune.BAIDU_CARLIFE || !SplashscreenContentDrawer.m3279$$Nest$smisCarLifeDisplay(i3, this.mSharedContext))) {
                try {
                    resourcesForApplication = this.mStandaloneContext.getPackageManager().getResourcesForApplication(activityInfo.applicationInfo);
                } catch (PackageManager.NameNotFoundException | Resources.NotFoundException unused) {
                }
            }
            if (resourcesForApplication != null) {
                Configuration configuration = resourcesForApplication.getConfiguration();
                DisplayMetrics displayMetrics = resourcesForApplication.getDisplayMetrics();
                configuration.densityDpi = i2;
                displayMetrics.densityDpi = i2;
                resourcesForApplication.updateConfiguration(configuration, displayMetrics);
            }
            Drawable icon = this.mStandaloneIconProvider.getIcon(activityInfo, i2);
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
    }

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

    public class PreloadIconData {
        public Context mContext;
        public boolean mIsPreloaded;
        public Drawable[] mPreloadIconDrawable;
        public int mPreloadIconSize;
    }

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
            Resources resourcesForApplication;
            Application applicationCurrentApplication;
            ContentResolver contentResolver = SplashscreenContentDrawer.this.mContext.getContentResolver();
            String string = Settings.System.getString(contentResolver, SettingsHelper.INDEX_CURRENT_SEC_ACTIVE_THEMEPACKAGE);
            int dominantColor = SplashscreenContentDrawer.mThemeBackgroundColor;
            if (TextUtils.isEmpty(string)) {
                dominantColor = 0;
            } else if (!string.equals(SplashscreenContentDrawer.this.mThemePackageName) || z) {
                Drawable drawable = null;
                try {
                    resourcesForApplication = SplashscreenContentDrawer.this.mContext.getPackageManager().getResourcesForApplication("android");
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.e("ShellStartingWindow", "updateSettings: NameNotFoundException, " + e);
                    resourcesForApplication = null;
                }
                if (resourcesForApplication != null) {
                    int identifier = resourcesForApplication.getIdentifier("tw_screen_background_color_light", "color", "android");
                    if (identifier != 0) {
                        dominantColor = resourcesForApplication.getColor(identifier);
                    } else {
                        int identifier2 = resourcesForApplication.getIdentifier("tw_screen_background_light", "drawable", "android");
                        if (identifier2 != 0) {
                            drawable = resourcesForApplication.getDrawable(identifier2);
                        }
                    }
                }
                if (dominantColor == 0 && drawable == null && (applicationCurrentApplication = ActivityThread.currentApplication()) != null) {
                    TypedArray typedArrayObtainStyledAttributes = applicationCurrentApplication.obtainStyledAttributes(R.styleable.Window);
                    if (typedArrayObtainStyledAttributes.hasValue(1)) {
                        drawable = typedArrayObtainStyledAttributes.getDrawable(1);
                    }
                }
                if (drawable != null) {
                    dominantColor = new DrawableColorTester(drawable, 1).mColorChecker.getDominantColor();
                }
            }
            if (dominantColor != SplashscreenContentDrawer.mThemeBackgroundColor) {
                final int i = 0;
                SplashscreenContentDrawer.this.mColorCache.mColorMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        int i2 = 0;
                        SplashscreenContentDrawer.ColorCache.Colors colors = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                        switch (i) {
                            case 0:
                                int i3 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i2 < 2) {
                                    colors.mWindowColors[i2] = null;
                                    i2++;
                                }
                                break;
                            default:
                                int i4 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i2 < 2) {
                                    colors.mIconColors[i2] = null;
                                    i2++;
                                }
                                break;
                        }
                    }
                });
            }
            String string2 = Settings.System.getString(contentResolver, SettingsHelper.INDEX_CURRENT_SEC_APPICON_THEME_PACKAGE);
            if ((!TextUtils.isEmpty(string2) && !string2.equals(SplashscreenContentDrawer.this.mThemeIconPackageName)) || (!TextUtils.isEmpty(SplashscreenContentDrawer.this.mThemeIconPackageName) && !SplashscreenContentDrawer.this.mThemeIconPackageName.equals(string2))) {
                final int i2 = 1;
                SplashscreenContentDrawer.this.mColorCache.mColorMap.forEach(new BiConsumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$SettingObserver$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        int i22 = 0;
                        SplashscreenContentDrawer.ColorCache.Colors colors = (SplashscreenContentDrawer.ColorCache.Colors) obj2;
                        switch (i2) {
                            case 0:
                                int i3 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i22 < 2) {
                                    colors.mWindowColors[i22] = null;
                                    i22++;
                                }
                                break;
                            default:
                                int i4 = SplashscreenContentDrawer.SettingObserver.$r8$clinit;
                                while (i22 < 2) {
                                    colors.mIconColors[i22] = null;
                                    i22++;
                                }
                                break;
                        }
                    }
                });
            }
            SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
            splashscreenContentDrawer.mThemeIconPackageName = string2;
            splashscreenContentDrawer.mThemePackageName = string;
            SplashscreenContentDrawer.mThemeBackgroundColor = dominantColor;
            Slog.d("ShellStartingWindow", "updateSettings: theme=" + SplashscreenContentDrawer.this.mThemePackageName + ", iconTheme=" + SplashscreenContentDrawer.this.mThemeIconPackageName + ", color=" + Integer.toHexString(SplashscreenContentDrawer.mThemeBackgroundColor));
        }
    }

    public class SplashScreenWindowAttrs {
        public int mWindowBgResId = 0;
        public int mWindowBgColor = 0;
        public Drawable mSplashScreenIcon = null;
        public Drawable mBrandingImage = null;
        public int mIconBgColor = 0;
    }

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

        /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00d1  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x0195  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x01d3  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x01f4  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x022b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final SplashScreenView build(boolean z) {
            long j;
            int i;
            Drawable drawable;
            Drawable drawable2;
            Drawable icon;
            ColorCache.IconColor iconColor;
            boolean z2;
            boolean z3;
            int i2 = this.mSuggestType;
            int i3 = 0;
            SplashscreenContentDrawer splashscreenContentDrawer = SplashscreenContentDrawer.this;
            if (i2 == 3 || i2 == 4) {
                j = 32;
                i = 1;
                this.mFinalIconSize = 0;
            } else if (z || this.mDisplayId != 0) {
                SplashScreenWindowAttrs splashScreenWindowAttrs = splashscreenContentDrawer.mTmpAttrs;
                Drawable drawable3 = splashScreenWindowAttrs.mSplashScreenIcon;
                if (drawable3 != null) {
                    int i4 = splashScreenWindowAttrs.mIconBgColor;
                    if (i4 == 0 || i4 == this.mThemeColor) {
                        this.mFinalIconSize = (int) (this.mFinalIconSize * splashscreenContentDrawer.mNoBackgroundScale);
                    }
                    createIconDrawable(drawable3, false, false);
                } else {
                    int i5 = this.mContext.getResources().getConfiguration().densityDpi;
                    int i6 = (int) (((splashscreenContentDrawer.mIconSize / splashscreenContentDrawer.mDefaultIconSize) * i5 * splashscreenContentDrawer.mNoBackgroundScale) + 0.5f);
                    Trace.traceBegin(32L, "getIcon");
                    boolean zIsEmpty = TextUtils.isEmpty(splashscreenContentDrawer.mThemeIconPackageName);
                    HighResIconProvider highResIconProvider = splashscreenContentDrawer.mHighResIconProvider;
                    if (!zIsEmpty && this.mContext.getUserId() == 0) {
                        icon = this.mActivityInfo.loadIcon(this.mContext.getPackageManager());
                    } else if (SplashscreenContentDrawer.isExternalDesktopMode(this.mDisplayId, this.mContext)) {
                        icon = highResIconProvider.getIcon(this.mActivityInfo, i5, i6, this.mDisplayId);
                    } else if (CoreRune.BAIDU_CARLIFE) {
                        icon = SplashscreenContentDrawer.m3279$$Nest$smisCarLifeDisplay(this.mDisplayId, this.mContext) ? highResIconProvider.getIcon(this.mActivityInfo, i5, i6, this.mDisplayId) : highResIconProvider.getIcon(this.mActivityInfo, i5, i6, -1);
                    }
                    Trace.traceEnd(32L);
                    if (icon instanceof AdaptiveIconDrawable) {
                        Trace.traceBegin(32L, "processAdaptiveIcon");
                        AdaptiveIconDrawable adaptiveIconDrawable = (AdaptiveIconDrawable) icon;
                        Drawable foreground = adaptiveIconDrawable.getForeground();
                        ColorCache colorCache = splashscreenContentDrawer.mColorCache;
                        ActivityInfo activityInfo = this.mActivityInfo;
                        String str = activityInfo.packageName;
                        int iconResource = activityInfo.getIconResource();
                        int i7 = splashscreenContentDrawer.mLastPackageContextConfigHash;
                        j = 32;
                        ColorCache.Colors colors = (ColorCache.Colors) colorCache.mColorMap.get(str);
                        int i8 = (iconResource * 31) + i7;
                        int[] iArr = {0};
                        if (colors != null) {
                            iconColor = (ColorCache.IconColor) ColorCache.getCache(colors.mIconColors, i8, iArr);
                            if (iconColor == null) {
                            }
                            z2 = ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1];
                            boolean z4 = iconColor.mIsBgComplex;
                            int i9 = iconColor.mBgColor;
                            int i10 = iconColor.mFgColor;
                            if (z2) {
                                i = 1;
                            } else {
                                String strValueOf = String.valueOf(Integer.toHexString(i10));
                                String strValueOf2 = String.valueOf(Integer.toHexString(i9));
                                if (iconColor.mReuseCount > 0) {
                                    z3 = true;
                                    i = 1;
                                } else {
                                    i = 1;
                                    z3 = false;
                                }
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -75961434200786365L, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp, strValueOf, strValueOf2, Boolean.valueOf(z4), Boolean.valueOf(z3), String.valueOf(Integer.toHexString(this.mThemeColor)));
                            }
                            if (foreground != null || z4 || splashscreenContentDrawer.mTmpAttrs.mIconBgColor != 0 || (!SplashscreenContentDrawer.m3280$$Nest$smisRgbSimilarInHsv(this.mThemeColor, i9) && (!iconColor.mIsBgGrayscale || SplashscreenContentDrawer.m3280$$Nest$smisRgbSimilarInHsv(this.mThemeColor, i10)))) {
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[i]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 5477106358074685500L, 0, null);
                                }
                                createIconDrawable(icon, false, highResIconProvider.mLoadInDetail);
                            } else {
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[i]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -651562876532081491L, 0, null);
                                }
                                this.mFinalIconSize = (int) ((splashscreenContentDrawer.mIconSize * (iconColor.mFgNonTranslucentRatio < splashscreenContentDrawer.mEnlargeForegroundIconThreshold ? splashscreenContentDrawer.mNoBackgroundScale : 1.0f)) + 0.5f);
                                createIconDrawable(foreground, false, highResIconProvider.mLoadInDetail);
                            }
                            Trace.traceEnd(32L);
                        } else {
                            colors = new ColorCache.Colors(i3);
                            colorCache.mColorMap.put(str, colors);
                        }
                        DrawableColorTester drawableColorTester = new DrawableColorTester(foreground, 2);
                        DrawableColorTester drawableColorTester2 = new DrawableColorTester(adaptiveIconDrawable.getBackground());
                        DrawableColorTester.ColorTester colorTester = drawableColorTester.mColorChecker;
                        int dominantColor = colorTester.getDominantColor();
                        DrawableColorTester.ColorTester colorTester2 = drawableColorTester2.mColorChecker;
                        ColorCache.IconColor iconColor2 = new ColorCache.IconColor(i8, dominantColor, colorTester2.getDominantColor(), colorTester2.isComplexColor(), colorTester2.isGrayscale(), colorTester.passFilterRatio());
                        colors.mIconColors[iArr[0]] = iconColor2;
                        iconColor = iconColor2;
                        z2 = ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1];
                        boolean z42 = iconColor.mIsBgComplex;
                        int i92 = iconColor.mBgColor;
                        int i102 = iconColor.mFgColor;
                        if (z2) {
                        }
                        if (foreground != null) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[i]) {
                            }
                            createIconDrawable(icon, false, highResIconProvider.mLoadInDetail);
                            Trace.traceEnd(32L);
                        }
                    } else {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 943133194207638789L, 0, null);
                        }
                        Trace.traceBegin(32L, "legacy_icon_factory");
                        Bitmap bitmapCreateScaledBitmap = new ShapeIconFactory(this, splashscreenContentDrawer.mContext, i6, this.mFinalIconSize).createScaledBitmap(icon, 0);
                        Trace.traceEnd(32L);
                        createIconDrawable(new BitmapDrawable(bitmapCreateScaledBitmap), true, highResIconProvider.mLoadInDetail);
                    }
                }
                j = 32;
                i = 1;
            } else {
                PreloadIconData preloadIconData = splashscreenContentDrawer.mPreloadIcon;
                int themeResId = this.mContext.getThemeResId();
                if (preloadIconData.mIsPreloaded && themeResId != 0 && themeResId == preloadIconData.mContext.getThemeResId()) {
                    Slog.d("ShellStartingWindow", "use preloaded icon");
                    PreloadIconData preloadIconData2 = splashscreenContentDrawer.mPreloadIcon;
                    this.mFinalIconSize = preloadIconData2.mPreloadIconSize;
                    this.mFinalIconDrawables = preloadIconData2.mPreloadIconDrawable;
                }
                j = 32;
                i = 1;
            }
            splashscreenContentDrawer.mHandler.removeMessages(i);
            PreloadIconData preloadIconData3 = splashscreenContentDrawer.mPreloadIcon;
            if (z) {
                Slog.d("ShellStartingWindow", "preload Icon " + this.mActivityInfo.packageName);
                Context context = this.mContext;
                int i11 = this.mFinalIconSize;
                Drawable[] drawableArr = this.mFinalIconDrawables;
                preloadIconData3.mContext = context;
                preloadIconData3.mPreloadIconSize = i11;
                preloadIconData3.mPreloadIconDrawable = drawableArr;
                preloadIconData3.mIsPreloaded = true;
                PreLoadIconDataHandler preLoadIconDataHandler = splashscreenContentDrawer.mHandler;
                preLoadIconDataHandler.sendMessageDelayed(preLoadIconDataHandler.obtainMessage(1), SplashscreenContentDrawer.CLEAR_PREALOD_ICON_TIMEOUT_MILLIS);
                return null;
            }
            Drawable drawable4 = null;
            preloadIconData3.mIsPreloaded = false;
            preloadIconData3.mContext = null;
            preloadIconData3.mPreloadIconDrawable = null;
            int i12 = this.mFinalIconSize;
            Drawable[] drawableArr2 = this.mFinalIconDrawables;
            SplashscreenWindowCreator$$ExternalSyntheticLambda2 splashscreenWindowCreator$$ExternalSyntheticLambda2 = this.mUiThreadInitTask;
            if (drawableArr2 != null) {
                drawable = drawableArr2.length > 0 ? drawableArr2[0] : null;
                if (drawableArr2.length > 1) {
                    drawable4 = drawableArr2[1];
                }
            } else {
                drawable = null;
            }
            Trace.traceBegin(j, "fillViewWithIcon");
            SplashScreenView.Builder allowHandleSolidColor = new SplashScreenView.Builder(new ContextThemeWrapper(this.mContext, splashscreenContentDrawer.mContext.getTheme())).setBackgroundColor(this.mThemeColor).setOverlayDrawable(this.mOverlayDrawable).setIconSize(i12).setIconBackground(drawable4).setCenterViewDrawable(drawable).setUiThreadInitConsumer(splashscreenWindowCreator$$ExternalSyntheticLambda2).setAllowHandleSolidColor(this.mAllowHandleSolidColor);
            if (this.mSuggestType == 1 && (drawable2 = splashscreenContentDrawer.mTmpAttrs.mBrandingImage) != null) {
                allowHandleSolidColor.setBrandingDrawable(drawable2, splashscreenContentDrawer.mBrandingImageWidth, splashscreenContentDrawer.mBrandingImageHeight);
            }
            SplashScreenView splashScreenViewBuild = allowHandleSolidColor.build();
            Trace.traceEnd(32L);
            return splashScreenViewBuild;
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
    public static boolean m3279$$Nest$smisCarLifeDisplay(int i, Context context) {
        Display display = ((DisplayManager) context.getSystemService("display")).getDisplay(i);
        return (display == null || (display.getFlags() & 1048576) == 0) ? false : true;
    }

    /* renamed from: -$$Nest$smisRgbSimilarInHsv, reason: not valid java name */
    public static boolean m3280$$Nest$smisRgbSimilarInHsv(int i, int i2) {
        boolean z;
        boolean z2;
        double d;
        if (i != i2) {
            float fLuminance = Color.luminance(i);
            float fLuminance2 = Color.luminance(i2);
            float f = fLuminance > fLuminance2 ? (fLuminance + 0.05f) / (fLuminance2 + 0.05f) : (fLuminance2 + 0.05f) / (fLuminance + 0.05f);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -985106566387254744L, 32, String.valueOf(Integer.toHexString(i)), String.valueOf(Integer.toHexString(i2)), Double.valueOf(f));
            }
            if (f >= 2.0f) {
                float[] fArr = new float[3];
                float[] fArr2 = new float[3];
                Color.colorToHSV(i, fArr);
                Color.colorToHSV(i2, fArr2);
                int iAbs = ((((int) Math.abs(fArr[0] - fArr2[0])) + 180) % 360) - 180;
                double dPow = Math.pow(iAbs / 180.0f, 2.0d);
                double dPow2 = Math.pow(fArr[1] - fArr2[1], 2.0d);
                double dPow3 = Math.pow(fArr[2] - fArr2[2], 2.0d);
                double dSqrt = Math.sqrt(((dPow + dPow2) + dPow3) / 3.0d);
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                    z = false;
                    z2 = true;
                    d = dSqrt;
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 449850539114510075L, 2796201, Long.valueOf(iAbs), Double.valueOf(fArr[0]), Double.valueOf(fArr2[0]), Double.valueOf(fArr[1]), Double.valueOf(fArr2[1]), Double.valueOf(fArr[2]), Double.valueOf(fArr2[2]), Double.valueOf(dPow), Double.valueOf(dPow2), Double.valueOf(dPow3), Double.valueOf(d));
                } else {
                    z = false;
                    z2 = true;
                    d = dSqrt;
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
                    ICustomFrequencyManager iCustomFrequencyManagerAsInterface;
                    IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
                    if (service == null || (iCustomFrequencyManagerAsInterface = ICustomFrequencyManager.Stub.asInterface(service)) == null) {
                        return;
                    }
                    try {
                        iCustomFrequencyManagerAsInterface.sendTid(Process.myPid(), handlerThread.getThreadId(), 4);
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
        Context contextCreateConfigurationContext = context.createConfigurationContext(configuration);
        contextCreateConfigurationContext.setTheme(i);
        return contextCreateConfigurationContext;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static WindowManager.LayoutParams createLayoutParameters(Context context, StartingWindowInfo startingWindowInfo, int i, CharSequence charSequence, int i2, IBinder iBinder) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(3);
        layoutParams.setFitInsetsSides(0);
        layoutParams.setFitInsetsTypes(0);
        layoutParams.format = i2;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
        int i3 = typedArrayObtainStyledAttributes.getBoolean(14, false) ? android.R.bool.config_cecSetMenuLanguageEnabled_default : android.R.attr.transcriptMode;
        if (i != 4 || typedArrayObtainStyledAttributes.getBoolean(33, false)) {
            i3 |= Integer.MIN_VALUE;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = startingWindowInfo.taskInfo;
        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
        if (activityInfo == null) {
            activityInfo = runningTaskInfo.topActivityInfo;
        }
        boolean zIsEdgeToEdgeEnforced = PhoneWindow.isEdgeToEdgeEnforced(activityInfo.applicationInfo, false, typedArrayObtainStyledAttributes);
        if (zIsEdgeToEdgeEnforced) {
            layoutParams.privateFlags |= 2048;
        }
        layoutParams.layoutInDisplayCutoutMode = typedArrayObtainStyledAttributes.getInt(50, zIsEdgeToEdgeEnforced ? 3 : layoutParams.layoutInDisplayCutoutMode);
        layoutParams.windowAnimations = typedArrayObtainStyledAttributes.getResourceId(8, 0);
        typedArrayObtainStyledAttributes.recycle();
        int i4 = runningTaskInfo.displayId;
        if (i4 != 0) {
            DesktopStateImpl.Companion.getClass();
            if (DesktopStateImpl.Companion.inDesktopWindowing(i4)) {
                if (startingWindowInfo.isKeyguardOccluded) {
                    i3 |= NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
                }
            }
        }
        layoutParams.flags = 131096 | i3;
        layoutParams.token = iBinder;
        layoutParams.packageName = activityInfo.packageName;
        layoutParams.privateFlags |= 16;
        layoutParams.setTitle("Splash Screen " + ((Object) charSequence));
        return layoutParams;
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
        Application applicationCurrentApplication = ActivityThread.currentApplication();
        if (applicationCurrentApplication == null) {
            Slog.e("ShellStartingWindow", "System context does not exist!");
            return -16777216;
        }
        int i = mThemeBackgroundColor;
        return i != 0 ? i : applicationCurrentApplication.getResources().getColor(com.android.systemui.R.color.splash_window_background_default);
    }

    public static void getWindowAttrs(Context context, SplashScreenWindowAttrs splashScreenWindowAttrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
        splashScreenWindowAttrs.mWindowBgResId = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        splashScreenWindowAttrs.mWindowBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(typedArrayObtainStyledAttributes, 0), 0)).intValue();
        splashScreenWindowAttrs.mSplashScreenIcon = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(typedArrayObtainStyledAttributes, 2), null);
        splashScreenWindowAttrs.mBrandingImage = (Drawable) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(typedArrayObtainStyledAttributes, 3), null);
        splashScreenWindowAttrs.mIconBgColor = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(typedArrayObtainStyledAttributes, 4), 0)).intValue();
        typedArrayObtainStyledAttributes.recycle();
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
            } else {
                drawable = null;
            }
        }
        if (drawable == null) {
            drawable = new ColorDrawable(getSystemBGColor());
            Slog.w("ShellStartingWindow", "Window background does not exist, using " + drawable);
        }
        int iEstimateWindowBGColor = estimateWindowBGColor(drawable);
        Trace.traceEnd(32L);
        return iEstimateWindowBGColor;
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
                ValueAnimator valueAnimatorOfFloat;
                SplashscreenContentDrawer splashscreenContentDrawer = this.f$0;
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
                    valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    valueAnimatorOfFloat.setDuration(i4);
                    valueAnimatorOfFloat.setInterpolator(Interpolators.LINEAR);
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            View iconView;
                            View brandingView;
                            int i7 = i5;
                            int i8 = i4;
                            ViewGroup viewGroup = splashScreenView4;
                            float f6 = f3;
                            float f7 = f4;
                            int i9 = i6;
                            Interpolator interpolator2 = SplashScreenExitAnimationUtils.ICON_INTERPOLATOR;
                            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            float interpolation = ((PathInterpolator) SplashScreenExitAnimationUtils.ICON_INTERPOLATOR).getInterpolation(SplashScreenExitAnimationUtils.getProgress(0L, i7, i8, fFloatValue));
                            if (viewGroup instanceof SplashScreenView) {
                                SplashScreenView splashScreenView5 = (SplashScreenView) viewGroup;
                                iconView = splashScreenView5.getIconView();
                                brandingView = splashScreenView5.getBrandingView();
                            } else {
                                iconView = null;
                                brandingView = null;
                            }
                            if (iconView != null) {
                                iconView.setAlpha((1.0f - interpolation) * f6);
                            }
                            if (brandingView != null) {
                                brandingView.setAlpha((1.0f - interpolation) * f7);
                            }
                            viewGroup.setAlpha(1.0f - ((PathInterpolator) Interpolators.ALPHA_OUT).getInterpolation(SplashScreenExitAnimationUtils.getProgress(0, i9, i8, fFloatValue)));
                        }
                    });
                    valueAnimatorOfFloat.addListener(splashScreenExitAnimation);
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
                    ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
                    valueAnimatorOfFloat2.setDuration(i4);
                    valueAnimatorOfFloat2.setInterpolator(Interpolators.LINEAR);
                    valueAnimatorOfFloat2.addListener(splashScreenExitAnimation);
                    valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils.1
                        public final /* synthetic */ View val$finalOccludeHoleView;
                        public final /* synthetic */ RadialVanishAnimation val$radialVanishAnimation;
                        public final /* synthetic */ ViewGroup val$splashScreenView;

                        public AnonymousClass1(ViewGroup splashScreenView22, final RadialVanishAnimation radialVanishAnimation2, View view3) {
                            viewGroup = splashScreenView22;
                            radialVanishAnimation = radialVanishAnimation2;
                            view = view3;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            SurfaceControl surfaceControl4;
                            super.onAnimationEnd(animator);
                            ShiftUpAnimation shiftUpAnimation2 = shiftUpAnimation;
                            if (shiftUpAnimation2 != null && (surfaceControl4 = shiftUpAnimation2.mFirstWindowSurface) != null && surfaceControl4.isValid()) {
                                TransactionPool transactionPool2 = shiftUpAnimation2.mTransactionPool;
                                SurfaceControl.Transaction transactionAcquire = transactionPool2.acquire();
                                if (shiftUpAnimation2.mSplashScreenView.isAttachedToWindow()) {
                                    transactionAcquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                                    shiftUpAnimation2.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(shiftUpAnimation2.mFirstWindowSurface).withWindowCrop((Rect) null).withMergeTransaction(transactionAcquire).build()});
                                } else {
                                    transactionAcquire.setWindowCrop(shiftUpAnimation2.mFirstWindowSurface, null);
                                    transactionAcquire.apply();
                                }
                                transactionPool2.release(transactionAcquire);
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
                            viewGroup.removeView(radialVanishAnimation);
                            viewGroup.removeView(view);
                        }
                    });
                    final SplashScreenView splashScreenView5 = splashScreenView22;
                    valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.shared.startingsurface.SplashScreenExitAnimationUtils$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            View iconView;
                            View brandingView;
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
                            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            float interpolation = ((PathInterpolator) SplashScreenExitAnimationUtils.ICON_INTERPOLATOR).getInterpolation(SplashScreenExitAnimationUtils.getProgress(0L, i7, i8, fFloatValue));
                            if (viewGroup instanceof SplashScreenView) {
                                SplashScreenView splashScreenView6 = (SplashScreenView) viewGroup;
                                iconView = splashScreenView6.getIconView();
                                brandingView = splashScreenView6.getBrandingView();
                            } else {
                                iconView = null;
                                brandingView = null;
                            }
                            if (iconView != null) {
                                iconView.setAlpha((1.0f - interpolation) * f6);
                            }
                            if (brandingView != null) {
                                brandingView.setAlpha((1.0f - interpolation) * f7);
                            }
                            float progress = SplashScreenExitAnimationUtils.getProgress(0, i9, i8, fFloatValue);
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
                            float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f9, f10, interpolation4, f10);
                            shiftUpAnimation2.mOccludeHoleView.setTranslationY(fM$1);
                            shiftUpAnimation2.mTmpTransform.setTranslate(0.0f, fM$1);
                            TransactionPool transactionPool2 = shiftUpAnimation2.mTransactionPool;
                            SurfaceControl.Transaction transactionAcquire = transactionPool2.acquire();
                            transactionAcquire.setFrameTimelineVsync(Choreographer.getSfInstance().getVsyncId());
                            Matrix matrix2 = shiftUpAnimation2.mTmpTransform;
                            Rect rect4 = shiftUpAnimation2.mFirstWindowFrame;
                            matrix2.postTranslate(rect4.left, rect4.top + shiftUpAnimation2.mMainWindowShiftLength);
                            shiftUpAnimation2.mApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(shiftUpAnimation2.mFirstWindowSurface).withMatrix(shiftUpAnimation2.mTmpTransform).withMergeTransaction(transactionAcquire).build()});
                            transactionPool2.release(transactionAcquire);
                        }
                    });
                    valueAnimatorOfFloat = valueAnimatorOfFloat2;
                }
                valueAnimatorOfFloat.start();
            }
        };
        if (splashScreenView.getIconView() == null) {
            runnable2.run();
            return;
        }
        long jUptimeMillis = SystemClock.uptimeMillis() - j;
        long showingDuration = getShowingDuration(splashScreenView.getIconAnimationDuration() != null ? splashScreenView.getIconAnimationDuration().toMillis() : 0L, jUptimeMillis) - jUptimeMillis;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -5778344489450129331L, 0, String.valueOf(showingDuration));
        }
        if (showingDuration > 0) {
            splashScreenView.postDelayed(runnable2, showingDuration);
        } else {
            runnable2.run();
        }
    }

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
            if (windowColor == null) {
            }
            return windowColor.mBgColor;
        }
        colors = new ColorCache.Colors(i5);
        colorCache.mColorMap.put(str, colors);
        windowColor = new ColorCache.WindowColor(i4, intSupplier.getAsInt());
        colors.mWindowColors[iArr[0]] = windowColor;
        return windowColor.mBgColor;
    }

    public final SplashScreenView makeSplashScreenContentView(Context context, StartingWindowInfo startingWindowInfo, int i, SplashscreenWindowCreator$$ExternalSyntheticLambda2 splashscreenWindowCreator$$ExternalSyntheticLambda2) {
        final Drawable colorDrawable;
        updateDensity();
        SplashScreenWindowAttrs splashScreenWindowAttrs = this.mTmpAttrs;
        getWindowAttrs(context, splashScreenWindowAttrs);
        this.mLastPackageContextConfigHash = context.getResources().getConfiguration().hashCode();
        if (i == 1 && !this.mCanUseAppIconForSplashScreen && splashScreenWindowAttrs.mSplashScreenIcon == null && (startingWindowInfo.startingWindowTypeParameter & 512) == 0) {
            i = 3;
        }
        if (!isExternalDesktopMode(startingWindowInfo.taskInfo.displayId, context) || context.getResources().getAssets().getSamsungThemeOverlays().size() <= 0) {
            Drawable drawable = null;
            if (i == 4) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
                int iIntValue = ((Integer) safeReturnAttrDefault(new SplashscreenContentDrawer$$ExternalSyntheticLambda0(typedArrayObtainStyledAttributes, 1), 0)).intValue();
                typedArrayObtainStyledAttributes.recycle();
                if (iIntValue != 0) {
                    drawable = context.getDrawable(iIntValue);
                } else {
                    int i2 = splashScreenWindowAttrs.mWindowBgResId;
                    if (i2 != 0) {
                        drawable = context.getDrawable(i2);
                    }
                }
            }
            colorDrawable = drawable;
        } else {
            colorDrawable = new ColorDrawable(ActivityThread.currentApplication().getResources().getColor(com.android.systemui.R.color.splash_window_background_default));
        }
        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
        if (activityInfo == null) {
            activityInfo = startingWindowInfo.taskInfo.topActivityInfo;
        }
        int bGColorFromCache = colorDrawable != null ? getBGColorFromCache(activityInfo, new IntSupplier() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda8
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return SplashscreenContentDrawer.estimateWindowBGColor(colorDrawable);
            }
        }) : getBGColorFromCache(activityInfo, new SplashscreenContentDrawer$$ExternalSyntheticLambda6(this, context, 1));
        SplashViewBuilder splashViewBuilder = new SplashViewBuilder(context, activityInfo);
        splashViewBuilder.mThemeColor = bGColorFromCache;
        splashViewBuilder.mOverlayDrawable = colorDrawable;
        splashViewBuilder.mSuggestType = i;
        splashViewBuilder.mUiThreadInitTask = splashscreenWindowCreator$$ExternalSyntheticLambda2;
        splashViewBuilder.mAllowHandleSolidColor = startingWindowInfo.allowHandleSolidColorSplashScreen();
        splashViewBuilder.mDisplayId = startingWindowInfo.taskInfo.displayId;
        return splashViewBuilder.build(false);
    }

    public final void updateDensity() {
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(17106372);
        this.mDefaultIconSize = this.mContext.getResources().getDimensionPixelSize(17106371);
        this.mBrandingImageWidth = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_brand_image_width);
        this.mBrandingImageHeight = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_brand_image_height);
        this.mMainWindowShiftLength = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.starting_surface_exit_animation_window_shift_length);
        this.mEnlargeForegroundIconThreshold = this.mContext.getResources().getFloat(com.android.systemui.R.dimen.splash_icon_enlarge_foreground_threshold);
        this.mNoBackgroundScale = this.mContext.getResources().getFloat(com.android.systemui.R.dimen.splash_icon_no_background_scale_factor);
    }
}
