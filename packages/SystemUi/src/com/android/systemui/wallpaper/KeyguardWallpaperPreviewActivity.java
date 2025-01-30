package com.android.systemui.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.punchhole.VIDirector$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.wallpaper.view.SystemUIWallpaper;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardWallpaperPreviewActivity extends Activity {
    public static boolean sConfigured = false;
    public static boolean sIsActivityAlive;
    public ImageView mBackgroundImageView;
    public ViewGroup mBottomContainer;
    public ImageView mCapturedImageView;
    public String mColorInfo;
    public Context mContext;
    public ContextThemeWrapper mContextThemeWrapper;
    public String mPackageName;
    public ViewGroup mPreviewArea;
    public FrameLayout mPreviewContainer;
    public FrameLayout mPreviewRootView;
    public ProgressBar mProgressBar;
    public FrameLayout mRootView;
    public RoundPreviewContainer mRoundContainer;
    public boolean mSetAsWallpaper;
    public Button mSetAsWallpaperButton;
    public TextView mSettingDescriptionTextView;
    public WallpaperManager mWallpaperManager;
    public int mWallpaperType;
    public SystemUIWallpaper mWallpaperView;
    public final ExecutorService mExecutor = Executors.newSingleThreadExecutor(new KeyguardWallpaperPreviewActivity$$ExternalSyntheticLambda1());
    public int mDescriptionCount = 0;
    public int mCurrentWhich = 2;
    public final C36741 mWallpaperResultCallback = new WallpaperResultCallback() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperPreviewActivity.1
        @Override // com.android.systemui.wallpaper.WallpaperResultCallback
        public final void onDelegateBitmapReady(Bitmap bitmap) {
            KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity = KeyguardWallpaperPreviewActivity.this;
            KeyguardWallpaperPreviewActivity.m2732$$Nest$minitBackgroundImageView(keyguardWallpaperPreviewActivity, bitmap);
            keyguardWallpaperPreviewActivity.dismissProgressDialog();
        }

        @Override // com.android.systemui.wallpaper.WallpaperResultCallback
        public final void onDrawFinished() {
        }

        @Override // com.android.systemui.wallpaper.WallpaperResultCallback
        public final void onPreviewReady() {
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RoundPreviewContainer extends FrameLayout {
        public final int roundRadius;

        public RoundPreviewContainer(Context context) {
            super(context);
            this.roundRadius = (int) TypedValue.applyDimension(1, SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_ROUNDED_CORNER_RADIUS", 26), getResources().getDisplayMetrics());
        }

        @Override // android.view.ViewGroup, android.view.View
        public final void dispatchDraw(Canvas canvas) {
            Path path = new Path();
            RectF rectF = new RectF(canvas.getClipBounds());
            int i = this.roundRadius;
            path.addRoundRect(rectF, i, i, Path.Direction.CW);
            canvas.clipPath(path);
            super.dispatchDraw(canvas);
        }
    }

    /* renamed from: -$$Nest$minitBackgroundImageView, reason: not valid java name */
    public static void m2732$$Nest$minitBackgroundImageView(KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity, Bitmap bitmap) {
        ImageView imageView = keyguardWallpaperPreviewActivity.mBackgroundImageView;
        if (bitmap == null) {
            Log.e("KeyguardWallpaperPreviewActivity", "initBackgroundImageView(): wallpaperBitmap is null");
            keyguardWallpaperPreviewActivity.finish();
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == 0 || height == 0) {
            Log.e("KeyguardWallpaperPreviewActivity", "initBackgroundImageView(): bitmapWidth=" + width + ", bitmapHeight=" + height);
            keyguardWallpaperPreviewActivity.finish();
            return;
        }
        int width2 = imageView.getWidth();
        int height2 = imageView.getHeight();
        if (width2 == 0 || height2 == 0) {
            Log.e("KeyguardWallpaperPreviewActivity", "initBackgroundImageView(): viewWidth=" + width2 + ", viewHeight=" + height2);
            keyguardWallpaperPreviewActivity.finish();
            return;
        }
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("initBackgroundImageView() bw = ", width, " , bh = ", height, " , vw = "), width2, " , vh = ", height2, "KeyguardWallpaperPreviewActivity");
        Context context = keyguardWallpaperPreviewActivity.mContext;
        boolean z = WallpaperUtils.mIsEmergencyMode;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Bitmap blurredBitmap = WallpaperUtils.getBlurredBitmap(context, bitmap, displayMetrics.widthPixels, displayMetrics.heightPixels);
        imageView.setForeground(new ColorDrawable(1078807885));
        imageView.setImageBitmap(blurredBitmap);
    }

    public static Size getScreenSize(Context context, boolean z) {
        int i = context.getResources().getConfiguration().orientation;
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getRealSize(point);
        int i2 = point.x;
        int i3 = point.y;
        int i4 = (z || i == 1) ? i2 : i3;
        if (z || i == 1) {
            i2 = i3;
        }
        return new Size(i4, i2);
    }

    public final void dismissProgressDialog() {
        Log.d("KeyguardWallpaperPreviewActivity", "dismissProgressDialog()");
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar == null || !progressBar.isAnimating()) {
            return;
        }
        this.mPreviewRootView.setVisibility(0);
        this.mPreviewRootView.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.wallpaper_preview_fade_in));
        Animation loadAnimation = AnimationUtils.loadAnimation(this.mContext, R.anim.wallpaper_preview_fade_out);
        loadAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperPreviewActivity.4
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                KeyguardWallpaperPreviewActivity.this.mProgressBar.setVisibility(8);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
            }
        });
        this.mProgressBar.startAnimation(loadAnimation);
    }

    public final boolean isSubDisplay() {
        return LsRune.WALLPAPER_SUB_DISPLAY_MODE && this.mWallpaperManager.getLidState() == 0;
    }

    public final Bitmap loadAnimatedBackgroundBitmap() {
        Log.d("KeyguardWallpaperPreviewActivity", "loadAnimatedBackgroundBitmap()");
        Context context = this.mContext;
        new BitmapFactory.Options().inScaled = true;
        try {
            context = this.mContext.createPackageContext(this.mPackageName, 3);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("KeyguardWallpaperPreviewActivity", "loadAnimatedBackgroundBitmap(): NameNotFoundException mPackageName=" + this.mPackageName);
        }
        int identifier = context.getResources().getIdentifier("lockscreen_wallpaper", "drawable", this.mPackageName);
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("loadAnimatedBackgroundBitmap: id = ", identifier, " , pkg = ");
        m1m.append(this.mPackageName);
        Log.i("KeyguardWallpaperPreviewActivity", m1m.toString());
        if (identifier > 0) {
            return ((BitmapDrawable) context.getDrawable(identifier)).getBitmap();
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x053c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0545  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0557  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x056a  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x05bb  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x05f5  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0632  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x06a3  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x072b  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x073a  */
    /* JADX WARN: Removed duplicated region for block: B:150:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x070e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x05d3  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0522  */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(Bundle bundle) {
        int i;
        String str;
        FrameLayout frameLayout;
        float f;
        ViewGroup.LayoutParams layoutParams;
        Bitmap bitmap;
        Bitmap bitmap2;
        InputStream openInputStream;
        Context context;
        Resources resources;
        float f2;
        Size size;
        float width;
        int i2;
        float f3;
        int displayId;
        int i3;
        int i4;
        super.onCreate(bundle);
        Log.d("KeyguardWallpaperPreviewActivity", "onCreate()");
        sIsActivityAlive = true;
        this.mContext = getApplicationContext();
        this.mContextThemeWrapper = new ContextThemeWrapper(getApplicationContext(), android.R.style.Theme.DeviceDefault.Light);
        this.mWallpaperManager = (WallpaperManager) this.mContext.getSystemService("wallpaper");
        Intent intent = getIntent();
        if (intent == null || this.mWallpaperManager == null) {
            finish();
            return;
        }
        setContentView(R.layout.keyguard_wallpaper_preview_activity);
        String stringExtra = intent.getStringExtra("type");
        String stringExtra2 = intent.getStringExtra("locType");
        CustomizationProvider$$ExternalSyntheticOutline0.m135m("getWallpaperTypeInteger(): type=", stringExtra, ", locType=", stringExtra2, "KeyguardWallpaperPreviewActivity");
        if (!"motion".equals(stringExtra) && !"cinematic".equals(stringExtra)) {
            if ("animated".equals(stringExtra) && "download".equals(stringExtra2)) {
                i = 4;
            }
            i = -1;
        } else if ("download".equals(stringExtra2)) {
            i = 1;
        } else {
            if (stringExtra2 != null && stringExtra2.startsWith("preload")) {
                i = 2;
            }
            i = -1;
        }
        this.mWallpaperType = i;
        this.mPackageName = intent.getStringExtra("packageName");
        this.mColorInfo = intent.getStringExtra("colorInfo");
        Window window = getWindow();
        window.addFlags(1024);
        window.getAttributes().layoutInDisplayCutoutMode = 1;
        window.getDecorView().setSystemUiVisibility(768);
        this.mRootView = (FrameLayout) findViewById(R.id.root_view);
        this.mPreviewRootView = (FrameLayout) findViewById(R.id.preview_root_view);
        this.mPreviewArea = (ViewGroup) findViewById(R.id.preview_area);
        this.mPreviewContainer = (FrameLayout) findViewById(R.id.preview_container);
        this.mSettingDescriptionTextView = (TextView) findViewById(R.id.wallpaper_setting_description);
        this.mCapturedImageView = (ImageView) findViewById(R.id.captured_image_view);
        this.mBackgroundImageView = (ImageView) findViewById(R.id.background_image_view);
        this.mBottomContainer = (ViewGroup) findViewById(R.id.bottom_container);
        this.mSetAsWallpaperButton = (Button) findViewById(R.id.set_as_wallpaper_button);
        this.mProgressBar = (ProgressBar) findViewById(R.id.preview_progress_bar);
        this.mRoundContainer = new RoundPreviewContainer(this);
        int i5 = 0;
        this.mSetAsWallpaper = false;
        this.mPreviewContainer.setTag(-1);
        this.mRoundContainer.setTag(-1);
        boolean z = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z && !LsRune.WALLPAPER_SUB_WATCHFACE) {
            int i6 = this.mWallpaperType;
            String str2 = i6 != 1 ? i6 != 4 ? "" : isSubDisplay() ? "animated_sub_description_count" : "animated_main_description_count" : isSubDisplay() ? "motion_sub_description_count" : "motion_main_description_count";
            ContentResolver contentResolver = this.mContext.getContentResolver();
            this.mDescriptionCount = Settings.Global.getInt(contentResolver, str2, 0);
            if (isSubDisplay() && (i4 = this.mDescriptionCount) <= 3) {
                int i7 = i4 + 1;
                this.mDescriptionCount = i7;
                Settings.Global.putInt(contentResolver, str2, i7);
            } else if (!isSubDisplay() && (i3 = this.mDescriptionCount) <= 3) {
                int i8 = i3 + 1;
                this.mDescriptionCount = i8;
                Settings.Global.putInt(contentResolver, str2, i8);
            }
        }
        showProgressDialog();
        Log.d("KeyguardWallpaperPreviewActivity", "init()");
        int i9 = this.mWallpaperType;
        if (i9 != 1 && i9 != 2 && i9 != 4) {
            Log.e("KeyguardWallpaperPreviewActivity", "init(): mWallpaperType=" + this.mWallpaperType);
            finish();
        }
        int i10 = R.string.kg_wallpaper_foldable_sub_settting_description;
        if (!z || LsRune.WALLPAPER_SUB_WATCHFACE) {
            this.mSettingDescriptionTextView.setVisibility(8);
        } else {
            this.mSettingDescriptionTextView.setText(isSubDisplay() ? R.string.kg_wallpaper_foldable_sub_settting_description : R.string.kg_wallpaper_foldable_main_settting_description);
        }
        if (!z || LsRune.WALLPAPER_SUB_WATCHFACE) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mPreviewContainer.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams2 = new LinearLayout.LayoutParams(this.mContext, (AttributeSet) null);
            }
            layoutParams2.topMargin = this.mContext.getResources().getDimensionPixelOffset(R.dimen.kg_title_text_view_height);
            this.mPreviewContainer.setLayoutParams(layoutParams2);
            this.mSettingDescriptionTextView.setVisibility(8);
        } else if ((!isSubDisplay() || this.mDescriptionCount <= 3) && (isSubDisplay() || this.mDescriptionCount <= 3)) {
            if (!isSubDisplay()) {
                i10 = R.string.kg_wallpaper_foldable_main_settting_description;
            }
            this.mSettingDescriptionTextView.setText(i10);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.wallpaper_setting_description_container);
            linearLayout.setPadding(linearLayout.getPaddingLeft(), Math.round(getScreenSize(this.mContext, true).getHeight() * 0.078f), linearLayout.getPaddingRight(), Math.round(getScreenSize(this.mContext, true).getHeight() * 0.046f));
        } else {
            this.mSettingDescriptionTextView.setVisibility(8);
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mPreviewContainer.getLayoutParams();
            if (layoutParams3 == null) {
                layoutParams3 = new LinearLayout.LayoutParams(this.mContext, (AttributeSet) null);
            }
            layoutParams3.topMargin = Math.round(getScreenSize(this.mContext, true).getHeight() * 0.159f);
            this.mPreviewContainer.setLayoutParams(layoutParams3);
        }
        Log.d("KeyguardWallpaperPreviewActivity", "initPreviewArea()");
        this.mRoundContainer.setClipToOutline(true);
        this.mPreviewArea.post(new KeyguardWallpaperPreviewActivity$$ExternalSyntheticLambda0(this, i5));
        if (this.mWallpaperType == 4) {
            this.mBackgroundImageView.post(new Runnable() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperPreviewActivity.2
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity = KeyguardWallpaperPreviewActivity.this;
                    boolean z2 = KeyguardWallpaperPreviewActivity.sIsActivityAlive;
                    KeyguardWallpaperPreviewActivity.m2732$$Nest$minitBackgroundImageView(keyguardWallpaperPreviewActivity, keyguardWallpaperPreviewActivity.loadAnimatedBackgroundBitmap());
                    KeyguardWallpaperPreviewActivity.this.dismissProgressDialog();
                }
            });
        }
        this.mSetAsWallpaperButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.wallpaper.KeyguardWallpaperPreviewActivity.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyguardWallpaperPreviewActivity keyguardWallpaperPreviewActivity = KeyguardWallpaperPreviewActivity.this;
                boolean z2 = KeyguardWallpaperPreviewActivity.sIsActivityAlive;
                keyguardWallpaperPreviewActivity.getClass();
                Log.d("KeyguardWallpaperPreviewActivity", "doSetAsWallpaper()");
                keyguardWallpaperPreviewActivity.mSetAsWallpaper = true;
                Cursor query = keyguardWallpaperPreviewActivity.mContext.getContentResolver().query(Uri.parse("content://com.sec.knox.provider/RestrictionPolicy4"), null, "isWallpaperChangeAllowed", null, null);
                if (query != null) {
                    try {
                        query.moveToFirst();
                        if (!keyguardWallpaperPreviewActivity.mWallpaperManager.isSetWallpaperAllowed() || "false".equals(query.getString(query.getColumnIndex("isWallpaperChangeAllowed")))) {
                            Toast.makeText(keyguardWallpaperPreviewActivity.mContext, R.string.kg_wallpaper_changes_restrict_toast, 0).show();
                            keyguardWallpaperPreviewActivity.finish();
                            return;
                        }
                    } finally {
                        query.close();
                    }
                }
                boolean isSubDisplay = WallpaperUtils.isSubDisplay(keyguardWallpaperPreviewActivity.mCurrentWhich);
                int i11 = keyguardWallpaperPreviewActivity.mWallpaperType;
                if (i11 == 1) {
                    if (isSubDisplay) {
                        keyguardWallpaperPreviewActivity.setSystemSettings(keyguardWallpaperPreviewActivity.mPackageName != null ? 3 : 0, "sub_display_lockscreen_wallpaper_transparency");
                        keyguardWallpaperPreviewActivity.setSystemSettingsForUser(0, 1, "lockscreen_wallpaper_sub");
                    } else {
                        keyguardWallpaperPreviewActivity.setSystemSettings(keyguardWallpaperPreviewActivity.mPackageName != null ? 3 : 0, "lockscreen_wallpaper_transparent");
                        keyguardWallpaperPreviewActivity.setSystemSettingsForUser(0, 1, "lockscreen_wallpaper");
                    }
                    keyguardWallpaperPreviewActivity.setSystemSettingsForUser(1, 0, "white_lockscreen_wallpaper");
                    keyguardWallpaperPreviewActivity.setSystemSettingsForUser(1, 0, "white_lockscreen_statusbar");
                    keyguardWallpaperPreviewActivity.setSystemSettingsForUser(1, 0, "white_lockscreen_navigationbar");
                    keyguardWallpaperPreviewActivity.mWallpaperManager.setMotionWallpaper(keyguardWallpaperPreviewActivity.mPackageName, keyguardWallpaperPreviewActivity.mCurrentWhich, true);
                    Toast.makeText(keyguardWallpaperPreviewActivity.mContext, R.string.kg_wallpaper_applied_toast, 0).show();
                } else if (i11 == 4) {
                    if (isSubDisplay) {
                        keyguardWallpaperPreviewActivity.setSystemSettings(keyguardWallpaperPreviewActivity.mPackageName != null ? 3 : 0, "sub_display_lockscreen_wallpaper_transparency");
                        keyguardWallpaperPreviewActivity.setSystemSettingsForUser(0, 1, "lockscreen_wallpaper_sub");
                    } else {
                        keyguardWallpaperPreviewActivity.setSystemSettings(keyguardWallpaperPreviewActivity.mPackageName != null ? 3 : 0, "lockscreen_wallpaper_transparent");
                        keyguardWallpaperPreviewActivity.setSystemSettingsForUser(0, 1, "lockscreen_wallpaper");
                    }
                    keyguardWallpaperPreviewActivity.setSystemSettingsForUser(1, 0, "white_lockscreen_wallpaper");
                    keyguardWallpaperPreviewActivity.setSystemSettingsForUser(1, 0, "white_lockscreen_statusbar");
                    keyguardWallpaperPreviewActivity.setSystemSettingsForUser(1, 0, "white_lockscreen_navigationbar");
                    try {
                        Log.i("KeyguardWallpaperPreviewActivity", "doSetAsWallpaper: which = " + keyguardWallpaperPreviewActivity.mCurrentWhich);
                        keyguardWallpaperPreviewActivity.mWallpaperManager.setAnimatedLockscreenWallpaper(keyguardWallpaperPreviewActivity.mPackageName, keyguardWallpaperPreviewActivity.mCurrentWhich, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(keyguardWallpaperPreviewActivity.mContext, R.string.kg_wallpaper_applied_toast, 0).show();
                }
                if (DeviceState.isOpenTheme(keyguardWallpaperPreviewActivity.mContext)) {
                    keyguardWallpaperPreviewActivity.finish();
                } else {
                    keyguardWallpaperPreviewActivity.showProgressDialog();
                    new Handler(Looper.getMainLooper()).postDelayed(new KeyguardWallpaperPreviewActivity$$ExternalSyntheticLambda0(keyguardWallpaperPreviewActivity, 2), 1500L);
                }
            }
        });
        FrameLayout frameLayout2 = this.mRootView;
        boolean z2 = WallpaperUtils.mIsEmergencyMode;
        Context context2 = frameLayout2.getContext();
        Context context3 = frameLayout2.getContext();
        try {
            Display[] displays = ((DisplayManager) context3.getSystemService(DisplayManager.class)).getDisplays();
            if (displays.length >= 2) {
                Size logicalDisplaySize = WallpaperUtils.getLogicalDisplaySize(context3);
                float min = Math.min(logicalDisplaySize.getWidth(), logicalDisplaySize.getHeight()) / Math.max(logicalDisplaySize.getWidth(), logicalDisplaySize.getHeight());
                Size[] physicalDisplaySizes = WallpaperUtils.getPhysicalDisplaySizes(displays);
                int i11 = 0;
                while (true) {
                    if (i11 >= physicalDisplaySizes.length) {
                        displayId = displays[0].getDisplayId();
                        break;
                    }
                    Size size2 = physicalDisplaySizes[i11];
                    float min2 = Math.min(size2.getWidth(), size2.getHeight()) / Math.max(size2.getWidth(), size2.getHeight());
                    Log.d("WallpaperUtils", "getCurrentDisplayID: search: " + i11 + " logicalRatio: " + min + " physicalRatio: " + min2);
                    if (Math.abs(min2 - min) < 0.01f) {
                        displayId = displays[i11].getDisplayId();
                        Log.d("WallpaperUtils", "getCurrentDisplayID: found: " + displayId + " logicalRatio: " + min + " physicalRatio: " + min2);
                        break;
                    }
                    i11++;
                }
                i5 = displayId;
            }
        } catch (Exception e) {
            Log.e("WallpaperUtils", "getCurrentDisplayID: ", e);
        }
        Resources resources2 = context2.getResources();
        if (resources2 != null) {
            DisplayMetrics displayMetrics = resources2.getDisplayMetrics();
            float f4 = displayMetrics.density;
            str = "KeyguardWallpaperPreviewActivity";
            float f5 = displayMetrics.densityDpi;
            Size logicalDisplaySize2 = WallpaperUtils.getLogicalDisplaySize(context2);
            Point point = new Point();
            frameLayout = frameLayout2;
            try {
                resources = resources2;
                try {
                    context = context2;
                } catch (Exception e2) {
                    e = e2;
                    context = context2;
                }
                try {
                    f2 = f4;
                } catch (Exception e3) {
                    e = e3;
                    f2 = f4;
                    Log.e("WallpaperUtils", "getPhysicalDisplaySize: ", e);
                    size = null;
                    width = size.getWidth() / logicalDisplaySize2.getWidth();
                    Log.d("WallpaperUtils", "getScreenSizeScaleToBase:  logicalDisplaySize : " + logicalDisplaySize2 + " (" + (logicalDisplaySize2.getWidth() / logicalDisplaySize2.getHeight()) + ")  physicalDisplaySize : " + size + " (" + (size.getWidth() / size.getHeight()) + ") ");
                    i2 = ((Integer) Class.forName("android.view.IWindowManager").getMethod("getInitialDisplayDensity", Integer.TYPE).invoke(Class.forName("android.view.IWindowManager$Stub").getMethod("asInterface", IBinder.class).invoke(null, Class.forName("android.os.ServiceManager").getMethod("checkService", String.class).invoke(null, "window")), Integer.valueOf(i5))).intValue();
                    float f6 = width * f5;
                    float f7 = width * f2;
                    boolean z3 = false;
                    while (r7 < r4) {
                    }
                    if (resources.getConfiguration().semDesktopModeEnabled != 1) {
                        if (width >= 1.0f) {
                        }
                    }
                    f3 = 1.0f;
                    if (Float.compare(f3, WallpaperUtils.sScreenDensityRateFromBase) != 0) {
                    }
                    f = WallpaperUtils.sScreenDensityRateFromBase;
                    if (Float.compare(f, 1.0f) != 0) {
                    }
                    Size screenSize = getScreenSize(this, false);
                    TypedValue typedValue = new TypedValue();
                    getResources().getValue((LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) ? R.dimen.kg_preview_ratio : isSubDisplay() ? R.dimen.kg_foldable_sub_preview_ratio : R.dimen.kg_foldable_preview_ratio, typedValue, true);
                    float f8 = typedValue.getFloat();
                    int round = Math.round(screenSize.getWidth() * f8);
                    int round2 = Math.round(screenSize.getHeight() * f8);
                    layoutParams = this.mPreviewArea.getLayoutParams();
                    if (layoutParams == null) {
                    }
                    layoutParams.width = round;
                    layoutParams.height = round2;
                    this.mPreviewArea.setLayoutParams(layoutParams);
                    String str3 = str;
                    Log.d(str3, "initCapturedImageView()");
                    ViewGroup.LayoutParams layoutParams4 = this.mPreviewArea.getLayoutParams();
                    StringBuilder sb = new StringBuilder("content://com.android.systemui.keyguard.image");
                    sb.append(String.format("/custom?width=%d&height=%d", Integer.valueOf(layoutParams4.width), Integer.valueOf(layoutParams4.height)));
                    Log.d(str3, "uri : " + sb.toString());
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), Uri.parse(sb.toString())));
                    if (bitmap == null) {
                    }
                    bitmap2 = bitmap;
                    if (bitmap2 != null) {
                    }
                    if (bitmap2 != null) {
                    }
                    if (sConfigured) {
                    }
                }
                try {
                    Class.forName("android.view.IWindowManager").getMethod("getInitialDisplaySize", Integer.TYPE, Point.class).invoke(Class.forName("android.view.IWindowManager$Stub").getMethod("asInterface", IBinder.class).invoke(null, Class.forName("android.os.ServiceManager").getMethod("checkService", String.class).invoke(null, "window")), Integer.valueOf(i5), point);
                    size = new Size(point.x, point.y);
                } catch (Exception e4) {
                    e = e4;
                    Log.e("WallpaperUtils", "getPhysicalDisplaySize: ", e);
                    size = null;
                    width = size.getWidth() / logicalDisplaySize2.getWidth();
                    Log.d("WallpaperUtils", "getScreenSizeScaleToBase:  logicalDisplaySize : " + logicalDisplaySize2 + " (" + (logicalDisplaySize2.getWidth() / logicalDisplaySize2.getHeight()) + ")  physicalDisplaySize : " + size + " (" + (size.getWidth() / size.getHeight()) + ") ");
                    i2 = ((Integer) Class.forName("android.view.IWindowManager").getMethod("getInitialDisplayDensity", Integer.TYPE).invoke(Class.forName("android.view.IWindowManager$Stub").getMethod("asInterface", IBinder.class).invoke(null, Class.forName("android.os.ServiceManager").getMethod("checkService", String.class).invoke(null, "window")), Integer.valueOf(i5))).intValue();
                    float f62 = width * f5;
                    float f72 = width * f2;
                    boolean z32 = false;
                    while (r7 < r4) {
                    }
                    if (resources.getConfiguration().semDesktopModeEnabled != 1) {
                    }
                    f3 = 1.0f;
                    if (Float.compare(f3, WallpaperUtils.sScreenDensityRateFromBase) != 0) {
                    }
                    f = WallpaperUtils.sScreenDensityRateFromBase;
                    if (Float.compare(f, 1.0f) != 0) {
                    }
                    Size screenSize2 = getScreenSize(this, false);
                    TypedValue typedValue2 = new TypedValue();
                    getResources().getValue((LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) ? R.dimen.kg_preview_ratio : isSubDisplay() ? R.dimen.kg_foldable_sub_preview_ratio : R.dimen.kg_foldable_preview_ratio, typedValue2, true);
                    float f82 = typedValue2.getFloat();
                    int round3 = Math.round(screenSize2.getWidth() * f82);
                    int round22 = Math.round(screenSize2.getHeight() * f82);
                    layoutParams = this.mPreviewArea.getLayoutParams();
                    if (layoutParams == null) {
                    }
                    layoutParams.width = round3;
                    layoutParams.height = round22;
                    this.mPreviewArea.setLayoutParams(layoutParams);
                    String str32 = str;
                    Log.d(str32, "initCapturedImageView()");
                    ViewGroup.LayoutParams layoutParams42 = this.mPreviewArea.getLayoutParams();
                    StringBuilder sb2 = new StringBuilder("content://com.android.systemui.keyguard.image");
                    sb2.append(String.format("/custom?width=%d&height=%d", Integer.valueOf(layoutParams42.width), Integer.valueOf(layoutParams42.height)));
                    Log.d(str32, "uri : " + sb2.toString());
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), Uri.parse(sb2.toString())));
                    if (bitmap == null) {
                    }
                    bitmap2 = bitmap;
                    if (bitmap2 != null) {
                    }
                    if (bitmap2 != null) {
                    }
                    if (sConfigured) {
                    }
                }
            } catch (Exception e5) {
                e = e5;
                context = context2;
                resources = resources2;
            }
            width = size.getWidth() / logicalDisplaySize2.getWidth();
            Log.d("WallpaperUtils", "getScreenSizeScaleToBase:  logicalDisplaySize : " + logicalDisplaySize2 + " (" + (logicalDisplaySize2.getWidth() / logicalDisplaySize2.getHeight()) + ")  physicalDisplaySize : " + size + " (" + (size.getWidth() / size.getHeight()) + ") ");
            try {
                i2 = ((Integer) Class.forName("android.view.IWindowManager").getMethod("getInitialDisplayDensity", Integer.TYPE).invoke(Class.forName("android.view.IWindowManager$Stub").getMethod("asInterface", IBinder.class).invoke(null, Class.forName("android.os.ServiceManager").getMethod("checkService", String.class).invoke(null, "window")), Integer.valueOf(i5))).intValue();
            } catch (Exception e6) {
                Log.e("WallpaperUtils", "getInitialDisplayDensity: ", e6);
                i2 = -1;
            }
            float f622 = width * f5;
            float f722 = width * f2;
            boolean z322 = false;
            for (Display display : ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays("android.hardware.display.category.PRESENTATION")) {
                if (display.getDisplayId() == i5) {
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("getBaseScreenDensityRate: presentation displayId ", i5, "WallpaperUtils");
                    z322 = true;
                }
            }
            if (resources.getConfiguration().semDesktopModeEnabled != 1 && !z322) {
                if (width >= 1.0f) {
                    Log.i("WallpaperUtils", "getBaseScreenDensityRate: physical display size is smaller than logical display size : " + width);
                } else if (i2 > 0) {
                    float f9 = i2;
                    if (f622 != f9) {
                        f3 = f9 / f622;
                        if (Float.compare(f3, WallpaperUtils.sScreenDensityRateFromBase) != 0) {
                            WallpaperUtils.sScreenDensityRateFromBase = f3;
                            StringBuilder m88m = VIDirector$$ExternalSyntheticOutline0.m88m("getBaseScreenDensityRate: currentDpi: ", f5, " currentDensity: ", f2, " scaledDpi: ");
                            m88m.append(f622);
                            m88m.append(" scaledDensity: ");
                            m88m.append(f722);
                            m88m.append(" sScreenDensityRate: ");
                            m88m.append(f3);
                            m88m.append(" initialDensityDpi: ");
                            m88m.append(i2);
                            m88m.append(" resolutionScale: ");
                            m88m.append(width);
                            m88m.append(" initialDensityDpi: ");
                            m88m.append(i2);
                            Log.d("WallpaperUtils", m88m.toString());
                        }
                        f = WallpaperUtils.sScreenDensityRateFromBase;
                    }
                }
            }
            f3 = 1.0f;
            if (Float.compare(f3, WallpaperUtils.sScreenDensityRateFromBase) != 0) {
            }
            f = WallpaperUtils.sScreenDensityRateFromBase;
        } else {
            str = "KeyguardWallpaperPreviewActivity";
            frameLayout = frameLayout2;
            f = 1.0f;
        }
        if (Float.compare(f, 1.0f) != 0) {
            Log.i("WallpaperUtils", "scaleMiddleDensityViewIfNeeded: screenDensityRate : " + f);
            WallpaperUtils.setScaledView(f, frameLayout);
        } else {
            Log.i("WallpaperUtils", "scaleMiddleDensityViewIfNeeded: skipped : screenDensityRate:" + f);
        }
        Size screenSize22 = getScreenSize(this, false);
        TypedValue typedValue22 = new TypedValue();
        getResources().getValue((LsRune.WALLPAPER_SUB_DISPLAY_MODE || LsRune.WALLPAPER_SUB_WATCHFACE) ? R.dimen.kg_preview_ratio : isSubDisplay() ? R.dimen.kg_foldable_sub_preview_ratio : R.dimen.kg_foldable_preview_ratio, typedValue22, true);
        float f822 = typedValue22.getFloat();
        int round32 = Math.round(screenSize22.getWidth() * f822);
        int round222 = Math.round(screenSize22.getHeight() * f822);
        layoutParams = this.mPreviewArea.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(this.mContext, (AttributeSet) null);
        }
        layoutParams.width = round32;
        layoutParams.height = round222;
        this.mPreviewArea.setLayoutParams(layoutParams);
        String str322 = str;
        Log.d(str322, "initCapturedImageView()");
        ViewGroup.LayoutParams layoutParams422 = this.mPreviewArea.getLayoutParams();
        StringBuilder sb22 = new StringBuilder("content://com.android.systemui.keyguard.image");
        sb22.append(String.format("/custom?width=%d&height=%d", Integer.valueOf(layoutParams422.width), Integer.valueOf(layoutParams422.height)));
        Log.d(str322, "uri : " + sb22.toString());
        try {
            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), Uri.parse(sb22.toString())));
        } catch (IOException e7) {
            e7.printStackTrace();
            bitmap = null;
        }
        if (bitmap == null) {
            Log.d(str322, "getLegacyCapturedBitmap()");
            String str4 = "/storage/emulated/" + Integer.toString(UserHandle.semGetMyUserId()) + "/Android/data/com.android.systemui/cache/lockscreen_capture_port.png";
            if (new File(str4).exists()) {
                bitmap = BitmapFactory.decodeFile(str4);
            } else {
                Log.e(str322, "initCapturedImageView(): " + str4 + " isn't exist");
                try {
                    openInputStream = getContentResolver().openInputStream(Uri.parse("android.resource://com.android.systemui/drawable/lockscreen_capture_dummy_port"));
                } catch (IOException e8) {
                    e = e8;
                    bitmap2 = null;
                }
                if (openInputStream != null) {
                    bitmap2 = BitmapFactory.decodeStream(openInputStream);
                    try {
                        openInputStream.close();
                    } catch (IOException e9) {
                        e = e9;
                        e.printStackTrace();
                        if (bitmap2 != null) {
                        }
                        if (bitmap2 != null) {
                        }
                        if (sConfigured) {
                        }
                    }
                    if (bitmap2 != null) {
                        try {
                            Bitmap applyPreviewVisibility = WallpaperUtils.applyPreviewVisibility(this.mContext, loadAnimatedBackgroundBitmap(), bitmap2);
                            if (bitmap2 != applyPreviewVisibility && !applyPreviewVisibility.isRecycled()) {
                                bitmap2.recycle();
                            }
                            bitmap2 = applyPreviewVisibility;
                        } catch (Exception e10) {
                            e10.printStackTrace();
                        }
                    }
                    if (bitmap2 != null) {
                        this.mCapturedImageView.setBackground(new BitmapDrawable((Resources) null, bitmap2));
                    }
                    if (sConfigured) {
                        return;
                    }
                    SystemUIAnalytics.initSystemUIAnalyticsStates(getApplication());
                    sConfigured = true;
                    return;
                }
                bitmap = null;
            }
        }
        bitmap2 = bitmap;
        if (bitmap2 != null) {
        }
        if (bitmap2 != null) {
        }
        if (sConfigured) {
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        Log.d("KeyguardWallpaperPreviewActivity", "onDestroy()");
        dismissProgressDialog();
        sIsActivityAlive = false;
        if (this.mSetAsWallpaper) {
            new Handler(Looper.getMainLooper()).postDelayed(new KeyguardWallpaperPreviewActivity$$ExternalSyntheticLambda0(this, 1), 2500L);
        } else {
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        Log.d("KeyguardWallpaperPreviewActivity", "onPause()");
        SystemUIWallpaper systemUIWallpaper = this.mWallpaperView;
        if (systemUIWallpaper != null) {
            systemUIWallpaper.onPause();
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (isInMultiWindowMode()) {
            Toast.makeText(this.mContextThemeWrapper, R.string.kg_wallpaper_preview_disable_multi_window, 1).show();
            semExitMultiWindowMode();
        }
        this.mCurrentWhich = WallpaperUtils.getFolderStateBasedWhich(2, this.mContext);
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("onResume() which = "), this.mCurrentWhich, "KeyguardWallpaperPreviewActivity");
        SystemUIWallpaper systemUIWallpaper = this.mWallpaperView;
        if (systemUIWallpaper != null) {
            systemUIWallpaper.onResume();
        }
        Context context = this.mContext;
        String string = context.getString(R.string.kg_wallpaper_preview_title_lock);
        int i = this.mWallpaperType;
        this.mPreviewContainer.setContentDescription(context.getString(R.string.kg_wallpaper_content_desc, (i == 1 || i == 2) ? context.getString(R.string.kg_wallpaper_preview_title_motion) : i != 4 ? "" : context.getString(R.string.kg_wallpaper_preview_title_animated), string));
    }

    public final void setSystemSettings(int i, String str) {
        Log.d("KeyguardWallpaperPreviewActivity", "setSystemSettings(): key=" + str + ", value=" + i);
        if (Settings.System.getInt(this.mContext.getContentResolver(), str, 1) != i) {
            Settings.System.putInt(this.mContext.getContentResolver(), str, i);
        }
    }

    public final void setSystemSettingsForUser(int i, int i2, String str) {
        Log.d("KeyguardWallpaperPreviewActivity", "setSystemSettingsForUser(): key=" + str + ", value=" + i2);
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), str, i, -2) != i2) {
            Settings.System.putIntForUser(this.mContext.getContentResolver(), str, i2, -2);
        }
    }

    public final void showProgressDialog() {
        Log.d("KeyguardWallpaperPreviewActivity", "showProgressDialog()");
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar == null || progressBar.isAnimating()) {
            return;
        }
        this.mPreviewRootView.setVisibility(4);
        this.mProgressBar.setVisibility(0);
    }
}
