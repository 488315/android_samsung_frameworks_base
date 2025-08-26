package com.android.systemui.widget;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.res.R$styleable;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIButton;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class SystemUIButton extends Button implements SystemUIWidgetCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int mAttrCount;
    public final BlurSettingsListener mBlurSettings;
    public String mDefaultArea;
    public int mDensityDpi;
    public float mFontScale;
    public boolean mIsCallbackRegistered;
    public boolean mIsLockStarEnabled;
    public final AnonymousClass1 mLockStarCallback;
    public final Executor mMainExecutor;
    public float mOriginalFontSizeDp;
    public long mPendingUpdateFlag;
    public final PluginLockManager mPluginLockManager;
    public final ResData mResData;
    public long mUpdateFlag;

    public final class BlurSettingsListener implements SettingsHelper.OnChangedCallback {
        public BlurSettingsListener() {
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this, Settings.System.getUriFor(SettingsHelper.INDEX_ACCESSIBILITY_REDUCE_TRANSPARENCY));
        }

        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            Log.d("SystemUIButton", "onChanged " + uri);
            SystemUIButton.this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.widget.SystemUIButton$BlurSettingsListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemUIButton.BlurSettingsListener blurSettingsListener = this.f$0;
                    blurSettingsListener.getClass();
                    int i = SystemUIButton.$r8$clinit;
                    SystemUIButton.this.updateButtonColor();
                }
            });
        }
    }

    public class ResData {
        public String mGroup;
        public boolean mMovable;
        public String mOriginBackground;
        public int mOriginBackgroundId;
        public String mOriginColor;
        public int mOriginColorId;
        public String mOriginShadowColor;
        public int mOriginShadowColorId;
        public String mThemeBackground;
        public int mThemeBackgroundId;
        public String mThemeBlackBackground;
        public int mThemeBlackBackgroundId;
        public String mThemeBlackColor;
        public int mThemeBlackColorId;
        public String mThemeBlackShadowColor;
        public int mThemeBlackShadowColorId;
        public String mThemeColor;
        public int mThemeColorId;
        public boolean mThemePolicyIgnorable;
        public String mThemeShadowColor;
        public int mThemeShadowColorId;
        public String mWallpaperArea;
        public String mWhiteBgBackground;
        public int mWhiteBgBackgroundId;
        public String mWhiteBgColor;
        public int mWhiteBgColorId;
        public String mWhiteBgShadowColor;
        public int mWhiteBgShadowColorId;

        public /* synthetic */ ResData(int i) {
            this();
        }

        private ResData() {
        }
    }

    public SystemUIButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAttrCount > 0) {
            ResData resData = this.mResData;
            this.mDefaultArea = resData.mWallpaperArea;
            if (resData.mMovable) {
                this.mPluginLockManager.registerSystemUIViewCallback(this.mLockStarCallback);
                if (this.mIsLockStarEnabled) {
                    ResData resData2 = this.mResData;
                    resData2.mWallpaperArea = this.mPluginLockManager.getLockStarItemLocationInfo(resData2.mGroup);
                }
            }
            SystemUIWidgetUtil.registerSystemUIWidgetCallback(this, SystemUIWidgetUtil.convertFlag(this.mResData.mWallpaperArea));
            this.mIsCallbackRegistered = true;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateFontSizeInKeyguardBoundary(configuration);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIsCallbackRegistered) {
            this.mIsCallbackRegistered = false;
            ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).removeCallback(false, this);
        }
        if (this.mResData.mMovable) {
            this.mPluginLockManager.removeSystemUIViewCallback(this.mLockStarCallback);
            this.mResData.mWallpaperArea = this.mDefaultArea;
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        float textSize = getTextSize();
        float f = getContext().getResources().getDisplayMetrics().density;
        if (f > 0.0f) {
            float f2 = getResources().getConfiguration().fontScale;
            float f3 = textSize / (f * f2);
            this.mOriginalFontSizeDp = f3;
            setTextSize(0, Math.max(1.0f, Math.min(1.2f, f2)) * f3 * f);
        }
        updateFontSizeInKeyguardBoundary(getContext().getResources().getConfiguration());
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        ResData resData = this.mResData;
        int i = resData.mOriginColorId;
        int i2 = resData.mOriginShadowColorId;
        int i3 = resData.mOriginBackgroundId;
        if (i > 0) {
            setTextColor(((Button) this).mContext.getResources().getColor(i, null));
        }
        if (i2 > 0) {
            setShadowLayer(getShadowRadius(), getShadowDx(), getShadowDy(), ((Button) this).mContext.getResources().getColor(i2, null));
        }
        if (i3 > 0) {
            setBackground(((Button) this).mContext.getResources().getDrawable(i3, null));
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public final void refreshResIds() {
        SystemUIWidgetRes systemUIWidgetRes = SystemUIWidgetRes.getInstance(((Button) this).mContext);
        ResData resData = this.mResData;
        String str = resData.mOriginColor;
        if (str != null) {
            resData.mOriginColorId = systemUIWidgetRes.getResIdByName(str, "color");
        }
        ResData resData2 = this.mResData;
        String str2 = resData2.mWhiteBgColor;
        if (str2 != null) {
            resData2.mWhiteBgColorId = systemUIWidgetRes.getResIdByName(str2, "color");
        }
        ResData resData3 = this.mResData;
        String str3 = resData3.mThemeColor;
        if (str3 != null) {
            resData3.mThemeColorId = systemUIWidgetRes.getResIdByName(str3, "color");
        }
        ResData resData4 = this.mResData;
        String str4 = resData4.mThemeBlackColor;
        if (str4 != null) {
            resData4.mThemeBlackColorId = systemUIWidgetRes.getResIdByName(str4, "color");
        }
        ResData resData5 = this.mResData;
        String str5 = resData5.mOriginShadowColor;
        if (str5 != null) {
            resData5.mOriginShadowColorId = systemUIWidgetRes.getResIdByName(str5, "color");
        }
        ResData resData6 = this.mResData;
        String str6 = resData6.mWhiteBgShadowColor;
        if (str6 != null) {
            resData6.mWhiteBgShadowColorId = systemUIWidgetRes.getResIdByName(str6, "color");
        }
        ResData resData7 = this.mResData;
        String str7 = resData7.mThemeShadowColor;
        if (str7 != null) {
            resData7.mThemeShadowColorId = systemUIWidgetRes.getResIdByName(str7, "color");
        }
        ResData resData8 = this.mResData;
        String str8 = resData8.mThemeBlackShadowColor;
        if (str8 != null) {
            resData8.mThemeBlackShadowColorId = systemUIWidgetRes.getResIdByName(str8, "color");
        }
        ResData resData9 = this.mResData;
        String str9 = resData9.mOriginBackground;
        if (str9 != null) {
            resData9.mOriginBackgroundId = systemUIWidgetRes.getResIdByName(str9, "drawable");
        }
        ResData resData10 = this.mResData;
        String str10 = resData10.mWhiteBgBackground;
        if (str10 != null) {
            resData10.mWhiteBgBackgroundId = systemUIWidgetRes.getResIdByName(str10, "drawable");
        }
        ResData resData11 = this.mResData;
        String str11 = resData11.mThemeBackground;
        if (str11 != null) {
            resData11.mThemeBackgroundId = systemUIWidgetRes.getResIdByName(str11, "drawable");
        }
        ResData resData12 = this.mResData;
        String str12 = resData12.mThemeBlackBackground;
        if (str12 != null) {
            resData12.mThemeBlackBackgroundId = systemUIWidgetRes.getResIdByName(str12, "drawable");
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            long j = this.mPendingUpdateFlag;
            if (j != 0) {
                updateStyle(j, ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).getSemWallpaperColors(false));
                this.mPendingUpdateFlag = 0L;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0072 A[PHI: r5 r6 r8
      0x0072: PHI (r5v14 int) = (r5v12 int), (r5v15 int) binds: [B:36:0x0087, B:27:0x0070] A[DONT_GENERATE, DONT_INLINE]
      0x0072: PHI (r6v7 int) = (r6v5 int), (r6v8 int) binds: [B:36:0x0087, B:27:0x0070] A[DONT_GENERATE, DONT_INLINE]
      0x0072: PHI (r8v17 int) = (r8v16 int), (r8v20 int) binds: [B:36:0x0087, B:27:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateButtonColor() {
        int i;
        semClearAllTextEffect();
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(this.mResData.mWallpaperArea);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        ResData resData = this.mResData;
        int i2 = zIsWhiteKeyguardWallpaper ? resData.mWhiteBgColorId : resData.mOriginColorId;
        ResData resData2 = this.mResData;
        int i3 = zIsWhiteKeyguardWallpaper ? resData2.mWhiteBgShadowColorId : resData2.mOriginShadowColorId;
        ResData resData3 = this.mResData;
        int i4 = zIsWhiteKeyguardWallpaper ? resData3.mWhiteBgBackgroundId : resData3.mOriginBackgroundId;
        if ((this.mUpdateFlag & 1) != 0 && WallpaperUtils.isOpenThemeLook()) {
            if (SystemUIWidgetUtil.needsBlackComponent(((Button) this).mContext, SystemUIWidgetUtil.convertFlag(this.mResData.mWallpaperArea), this.mResData.mThemePolicyIgnorable)) {
                Log.d("SystemUIButton", "apply style: theme : white");
                ResData resData4 = this.mResData;
                int i5 = resData4.mThemeBlackColorId;
                if (i5 > 0) {
                    i2 = i5;
                }
                int i6 = resData4.mThemeBlackShadowColorId;
                if (i6 > 0) {
                    i3 = i6;
                }
                i = resData4.mThemeBlackBackgroundId;
                if (i > 0) {
                    i4 = i;
                }
            } else {
                Log.d("SystemUIButton", "apply style: theme : black");
                ResData resData5 = this.mResData;
                int i7 = resData5.mThemeColorId;
                if (i7 > 0) {
                    i2 = i7;
                }
                int i8 = resData5.mThemeShadowColorId;
                if (i8 > 0) {
                    i3 = i8;
                }
                i = resData5.mThemeBackgroundId;
                if (i > 0) {
                }
            }
        }
        this.mBlurSettings.getClass();
        if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
            i2 = zIsWhiteKeyguardWallpaper ? R.color.reduce_transparency_color_white : R.color.reduce_transparency_color_black;
            i4 = zIsWhiteKeyguardWallpaper ? R.drawable.reduce_transparency_drawable_light : R.drawable.reduce_transparency_drawable_dark;
        }
        if (i2 > 0) {
            setTextColor(((Button) this).mContext.getResources().getColor(i2, null));
        }
        if (i3 > 0) {
            setShadowLayer(getShadowRadius(), getShadowDx(), getShadowDy(), ((Button) this).mContext.getResources().getColor(i3, null));
        }
        if (i4 > 0) {
            setBackground(((Button) this).mContext.getResources().getDrawable(i4, null));
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public final void updateFontSizeInKeyguardBoundary(Configuration configuration) {
        boolean z;
        float fMax = Math.max(1.0f, Math.min(1.2f, configuration.fontScale));
        int i = configuration.densityDpi;
        boolean z2 = true;
        if (i != this.mDensityDpi) {
            this.mDensityDpi = i;
            z = true;
        } else {
            z = false;
        }
        if (Float.compare(this.mFontScale, fMax) != 0) {
            this.mFontScale = fMax;
        } else {
            z2 = z;
        }
        if (z2) {
            setTextSize(0, this.mOriginalFontSizeDp * this.mFontScale * ((Button) this).mContext.getResources().getDisplayMetrics().density);
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if (j == 0) {
            return;
        }
        if (getVisibility() != 0) {
            this.mPendingUpdateFlag = j;
            return;
        }
        Log.d("SystemUIButton", "updateStyle() flag=" + Long.toHexString(this.mUpdateFlag) + "," + Long.toHexString(j) + " : " + toString());
        this.mUpdateFlag = j;
        refreshResIds();
        updateButtonColor();
    }

    public SystemUIButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SystemUIButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.widget.SystemUIButton$1] */
    public SystemUIButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mResData = new ResData(0);
        this.mDensityDpi = 0;
        this.mOriginalFontSizeDp = 0.0f;
        this.mFontScale = 1.0f;
        this.mUpdateFlag = 0L;
        this.mAttrCount = 0;
        this.mIsCallbackRegistered = false;
        this.mPendingUpdateFlag = 0L;
        this.mPluginLockManager = (PluginLockManager) Dependency.sDependency.getDependencyInner(PluginLockManager.class);
        this.mLockStarCallback = new PluginLockListener.State() { // from class: com.android.systemui.widget.SystemUIButton.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public final void onLockStarEnabled(boolean z) {
                SystemUIButton systemUIButton = SystemUIButton.this;
                systemUIButton.mIsLockStarEnabled = z;
                if (z) {
                    ResData resData = systemUIButton.mResData;
                    resData.mWallpaperArea = systemUIButton.mPluginLockManager.getLockStarItemLocationInfo(resData.mGroup);
                } else {
                    systemUIButton.mResData.mWallpaperArea = systemUIButton.mDefaultArea;
                }
                ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).removeCallback(false, systemUIButton);
                WallpaperUtils.registerSystemUIWidgetCallback(systemUIButton, SystemUIWidgetUtil.convertFlag(systemUIButton.mResData.mWallpaperArea));
            }
        };
        this.mBlurSettings = new BlurSettingsListener();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SysuiWidgetRes, i, i2);
        if (typedArrayObtainStyledAttributes != null) {
            this.mAttrCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < this.mAttrCount; i3++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i3);
                if (index == 23) {
                    this.mResData.mWallpaperArea = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 0) {
                    ResData resData = this.mResData;
                    typedArrayObtainStyledAttributes.getString(index);
                    resData.getClass();
                } else if (index == 9) {
                    this.mResData.mOriginColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 26) {
                    this.mResData.mWhiteBgColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 18) {
                    this.mResData.mThemeColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 15) {
                    this.mResData.mThemeBlackColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 11) {
                    this.mResData.mOriginShadowColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 28) {
                    this.mResData.mWhiteBgShadowColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 21) {
                    this.mResData.mThemeShadowColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 17) {
                    this.mResData.mThemeBlackShadowColor = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 8) {
                    this.mResData.mOriginBackground = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 25) {
                    this.mResData.mWhiteBgBackground = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 13) {
                    this.mResData.mThemeBackground = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 14) {
                    this.mResData.mThemeBlackBackground = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 7) {
                    this.mResData.mMovable = typedArrayObtainStyledAttributes.getBoolean(index, false);
                } else if (index == 5) {
                    this.mResData.mGroup = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 20) {
                    this.mResData.mThemePolicyIgnorable = typedArrayObtainStyledAttributes.getBoolean(index, false);
                }
            }
            refreshResIds();
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mMainExecutor = context.getMainExecutor();
    }
}
