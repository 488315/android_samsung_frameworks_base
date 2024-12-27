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
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.res.R$styleable;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIButton;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                    SystemUIButton.BlurSettingsListener blurSettingsListener = SystemUIButton.BlurSettingsListener.this;
                    blurSettingsListener.getClass();
                    int i = SystemUIButton.$r8$clinit;
                    SystemUIButton.this.updateButtonColor();
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ResData {
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

        private ResData() {
        }

        public /* synthetic */ ResData(int i) {
            this();
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
            resData9.mOriginBackgroundId = systemUIWidgetRes.getResIdByName(str9, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData10 = this.mResData;
        String str10 = resData10.mWhiteBgBackground;
        if (str10 != null) {
            resData10.mWhiteBgBackgroundId = systemUIWidgetRes.getResIdByName(str10, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData11 = this.mResData;
        String str11 = resData11.mThemeBackground;
        if (str11 != null) {
            resData11.mThemeBackgroundId = systemUIWidgetRes.getResIdByName(str11, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData12 = this.mResData;
        String str12 = resData12.mThemeBlackBackground;
        if (str12 != null) {
            resData12.mThemeBlackBackgroundId = systemUIWidgetRes.getResIdByName(str12, BriefViewController.URI_PATH_DRAWABLE);
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0070, code lost:
    
        if (r8 > 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0072, code lost:
    
        r7 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0087, code lost:
    
        if (r8 > 0) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateButtonColor() {
        /*
            Method dump skipped, instructions count: 241
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.widget.SystemUIButton.updateButtonColor():void");
    }

    public final void updateFontSizeInKeyguardBoundary(Configuration configuration) {
        boolean z;
        float max = Math.max(1.0f, Math.min(1.2f, configuration.fontScale));
        int i = configuration.densityDpi;
        boolean z2 = true;
        if (i != this.mDensityDpi) {
            this.mDensityDpi = i;
            z = true;
        } else {
            z = false;
        }
        if (Float.compare(this.mFontScale, max) != 0) {
            this.mFontScale = max;
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
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SysuiWidgetRes, i, i2);
        if (obtainStyledAttributes != null) {
            this.mAttrCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < this.mAttrCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                if (index == 23) {
                    this.mResData.mWallpaperArea = obtainStyledAttributes.getString(index);
                } else if (index == 0) {
                    ResData resData = this.mResData;
                    obtainStyledAttributes.getString(index);
                    resData.getClass();
                } else if (index == 9) {
                    this.mResData.mOriginColor = obtainStyledAttributes.getString(index);
                } else if (index == 26) {
                    this.mResData.mWhiteBgColor = obtainStyledAttributes.getString(index);
                } else if (index == 18) {
                    this.mResData.mThemeColor = obtainStyledAttributes.getString(index);
                } else if (index == 15) {
                    this.mResData.mThemeBlackColor = obtainStyledAttributes.getString(index);
                } else if (index == 11) {
                    this.mResData.mOriginShadowColor = obtainStyledAttributes.getString(index);
                } else if (index == 28) {
                    this.mResData.mWhiteBgShadowColor = obtainStyledAttributes.getString(index);
                } else if (index == 21) {
                    this.mResData.mThemeShadowColor = obtainStyledAttributes.getString(index);
                } else if (index == 17) {
                    this.mResData.mThemeBlackShadowColor = obtainStyledAttributes.getString(index);
                } else if (index == 8) {
                    this.mResData.mOriginBackground = obtainStyledAttributes.getString(index);
                } else if (index == 25) {
                    this.mResData.mWhiteBgBackground = obtainStyledAttributes.getString(index);
                } else if (index == 13) {
                    this.mResData.mThemeBackground = obtainStyledAttributes.getString(index);
                } else if (index == 14) {
                    this.mResData.mThemeBlackBackground = obtainStyledAttributes.getString(index);
                } else if (index == 7) {
                    this.mResData.mMovable = obtainStyledAttributes.getBoolean(index, false);
                } else if (index == 5) {
                    this.mResData.mGroup = obtainStyledAttributes.getString(index);
                } else if (index == 20) {
                    this.mResData.mThemePolicyIgnorable = obtainStyledAttributes.getBoolean(index, false);
                }
            }
            refreshResIds();
        }
        obtainStyledAttributes.recycle();
        this.mMainExecutor = context.getMainExecutor();
    }
}
