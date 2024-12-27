package com.android.systemui.widget;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.res.R$styleable;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.util.concurrent.Executor;

public class SystemUITextView extends TextView implements SystemUIWidgetCallback {
    public final int mAttrCount;
    public final BlurSettingsListener mBlurSettings;
    public final boolean mCompoundVisible;
    public String mDefaultArea;
    public int mDensityDpi;
    public float mFontScale;
    public boolean mIsCallbackRegistered;
    public final boolean mIsFixedFontSize;
    public boolean mIsLockStarEnabled;
    public final AnonymousClass1 mLockStarCallback;
    public final Executor mMainExecutor;
    public float mMaxFontScale;
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
            Log.d("SystemUITextView", "onChanged " + uri);
            SystemUITextView.this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.widget.SystemUITextView$BlurSettingsListener$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemUITextView.this.updateTextView();
                }
            });
        }
    }

    public final class ResData {
        public String mCompoundDrawable;
        public int mCompoundDrawableId;
        public String mCompoundPadding;
        public int mCompoundPaddingId;
        public String mCompoundScale;
        public int mCompoundScaleId;
        public String mGroup;
        public boolean mMovable;
        public String mOriginBackground;
        public int mOriginBackgroundId;
        public String mOriginColor;
        public int mOriginColorId;
        public String mOriginShadowColor;
        public int mOriginShadowColorId;
        public boolean mReduceTransparency;
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
        public boolean mWallpaperColorIgnorable;
        public String mWhiteBgBackground;
        public int mWhiteBgBackgroundId;
        public String mWhiteBgColor;
        public int mWhiteBgColorId;
        public String mWhiteBgShadowColor;
        public int mWhiteBgShadowColorId;
    }

    public SystemUITextView(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        RecyclerView$$ExternalSyntheticOutline0.m(this.mAttrCount, "SystemUITextView", new StringBuilder("onAttachedToWindow mAttrCount = "));
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
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateFontSizeInKeyguardBoundary(false, configuration);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
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
        if (getContext().getResources().getDisplayMetrics().density > 0.0f) {
            this.mOriginalFontSizeDp = textSize / getContext().getResources().getDisplayMetrics().density;
            Log.d("SystemUITextView", "mOriginalFontSizeDp " + this.mOriginalFontSizeDp + "  " + toString());
        }
        updateFontSizeInKeyguardBoundary(false, getContext().getResources().getConfiguration());
        ResData resData = this.mResData;
        int i = resData.mOriginColorId;
        int i2 = resData.mOriginShadowColorId;
        int i3 = resData.mOriginBackgroundId;
        if (i > 0) {
            setTextColor(((TextView) this).mContext.getResources().getColor(i, null));
        }
        if (i2 > 0) {
            setShadowLayer(getShadowRadius(), getShadowDx(), getShadowDy(), ((TextView) this).mContext.getResources().getColor(i2, null));
        }
        if (i3 > 0) {
            setBackground(((TextView) this).mContext.getDrawable(i3));
        }
    }

    public final void refreshResIds$4() {
        SystemUIWidgetRes systemUIWidgetRes = SystemUIWidgetRes.getInstance(((TextView) this).mContext);
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
        ResData resData13 = this.mResData;
        String str13 = resData13.mCompoundDrawable;
        if (str13 != null) {
            resData13.mCompoundDrawableId = systemUIWidgetRes.getResIdByName(str13, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData14 = this.mResData;
        String str14 = resData14.mCompoundScale;
        if (str14 != null) {
            resData14.mCompoundScaleId = systemUIWidgetRes.getResIdByName(str14, "dimen");
        }
        ResData resData15 = this.mResData;
        String str15 = resData15.mCompoundPadding;
        if (str15 != null) {
            resData15.mCompoundPaddingId = systemUIWidgetRes.getResIdByName(str15, "dimen");
        }
    }

    public final void setMaxFontScale(float f) {
        if (f < 1.0f || f > 1.2f) {
            f = this.mMaxFontScale;
        }
        this.mMaxFontScale = f;
        updateFontSizeInKeyguardBoundary(false, getContext().getResources().getConfiguration());
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

    public final void updateCompoundDrawable(boolean z) {
        PorterDuffColorFilter porterDuffColorFilter;
        if (!z) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            setCompoundDrawablePadding(0);
            return;
        }
        ResData resData = this.mResData;
        if (resData.mCompoundDrawableId <= 0 || resData.mCompoundScaleId <= 0) {
            return;
        }
        Drawable drawable = ((TextView) this).mContext.getResources().getDrawable(this.mResData.mCompoundDrawableId, null);
        int dimension = (int) ((TextView) this).mContext.getResources().getDimension(this.mResData.mCompoundScaleId);
        float f = this.mFontScale;
        int i = (int) (dimension * ((f - 1.0f) + f));
        if (drawable != null) {
            drawable.setBounds(0, 0, i, i);
        }
        if ((this.mUpdateFlag & 1) == 0 || !WallpaperUtils.isOpenThemeLook()) {
            if (WallpaperUtils.isWhiteKeyguardWallpaper(this.mResData.mWallpaperArea)) {
                ResData resData2 = this.mResData;
                if (resData2.mWhiteBgColor != null && resData2.mWhiteBgShadowColor != null && resData2.mWhiteBgColorId > 0) {
                    porterDuffColorFilter = new PorterDuffColorFilter(((TextView) this).mContext.getResources().getColor(this.mResData.mWhiteBgColorId, null), PorterDuff.Mode.SRC_ATOP);
                }
            }
            porterDuffColorFilter = null;
        } else if (WallpaperUtils.isOpenThemeLockWallpaper() || !WallpaperUtils.isWhiteKeyguardWallpaper(this.mResData.mWallpaperArea)) {
            if (this.mResData.mThemeColorId > 0) {
                int color = ((TextView) this).mContext.getResources().getColor(this.mResData.mThemeColorId, null);
                Log.d("SystemUITextView", "filter: ".concat(String.format("#%08X", Integer.valueOf(color))));
                porterDuffColorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
            porterDuffColorFilter = null;
        } else {
            if (this.mResData.mThemeBlackColorId > 0) {
                int color2 = ((TextView) this).mContext.getResources().getColor(this.mResData.mThemeBlackColorId, null);
                Log.d("SystemUITextView", "filter: ".concat(String.format("#%08X", Integer.valueOf(color2))));
                porterDuffColorFilter = new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_ATOP);
            }
            porterDuffColorFilter = null;
        }
        if (drawable != null) {
            drawable.setColorFilter(porterDuffColorFilter);
        }
        try {
            setCompoundDrawables(drawable, null, null, null);
            if (this.mResData.mCompoundPaddingId > 0) {
                setCompoundDrawablePadding((int) getContext().getResources().getDimension(this.mResData.mCompoundPaddingId));
            }
        } catch (Exception unused) {
            Log.d("SystemUITextView", "Exception adding mCompoundDrawable!");
        }
    }

    public final void updateFontSizeInKeyguardBoundary(boolean z, Configuration configuration) {
        boolean z2;
        boolean z3 = true;
        float max = !this.mIsFixedFontSize ? Math.max(1.0f, Math.min(1.2f, Math.min(configuration.fontScale, this.mMaxFontScale))) : 1.0f;
        int i = configuration.densityDpi;
        if (i != this.mDensityDpi) {
            this.mDensityDpi = i;
            z2 = true;
        } else {
            z2 = false;
        }
        if (Float.compare(this.mFontScale, max) != 0) {
            this.mFontScale = max;
        } else {
            z3 = z2;
        }
        if (z3 || z) {
            setTextSize(0, this.mOriginalFontSizeDp * this.mFontScale * ((TextView) this).mContext.getResources().getDisplayMetrics().density);
            updateCompoundDrawable(this.mCompoundVisible);
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
        Log.d("SystemUITextView", "updateStyle() flag=" + Long.toHexString(this.mUpdateFlag) + "," + Long.toHexString(j) + " : " + toString());
        this.mUpdateFlag = j;
        refreshResIds$4();
        updateTextView();
        updateCompoundDrawable(this.mCompoundVisible);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0094, code lost:
    
        if (r6 > 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0096, code lost:
    
        r5 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0097, code lost:
    
        r6 = r7;
        r7 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00ae, code lost:
    
        if (r6 > 0) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateTextView() {
        /*
            Method dump skipped, instructions count: 342
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.widget.SystemUITextView.updateTextView():void");
    }

    public SystemUITextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SystemUITextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SystemUITextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOriginalFontSizeDp = 0.0f;
        this.mUpdateFlag = 0L;
        this.mResData = null;
        this.mMaxFontScale = 1.2f;
        this.mFontScale = 1.0f;
        this.mDensityDpi = 0;
        this.mCompoundVisible = true;
        this.mAttrCount = 0;
        this.mIsCallbackRegistered = false;
        this.mPendingUpdateFlag = 0L;
        this.mPluginLockManager = (PluginLockManager) Dependency.sDependency.getDependencyInner(PluginLockManager.class);
        this.mLockStarCallback = new PluginLockListener.State() { // from class: com.android.systemui.widget.SystemUITextView.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public final void onLockStarEnabled(boolean z) {
                SystemUITextView systemUITextView = SystemUITextView.this;
                systemUITextView.mIsLockStarEnabled = z;
                if (z) {
                    ResData resData = systemUITextView.mResData;
                    resData.mWallpaperArea = systemUITextView.mPluginLockManager.getLockStarItemLocationInfo(resData.mGroup);
                } else {
                    systemUITextView.mResData.mWallpaperArea = systemUITextView.mDefaultArea;
                }
                ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).removeCallback(false, systemUITextView);
                WallpaperUtils.registerSystemUIWidgetCallback(systemUITextView, SystemUIWidgetUtil.convertFlag(systemUITextView.mResData.mWallpaperArea));
            }
        };
        this.mBlurSettings = new BlurSettingsListener();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyguardFontSize);
        this.mIsFixedFontSize = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.SysuiWidgetRes, i, i2);
        this.mResData = new ResData();
        if (obtainStyledAttributes2 != null) {
            this.mAttrCount = obtainStyledAttributes2.getIndexCount();
            for (int i3 = 0; i3 < this.mAttrCount; i3++) {
                int index = obtainStyledAttributes2.getIndex(i3);
                if (index == 23) {
                    this.mResData.mWallpaperArea = obtainStyledAttributes2.getString(index);
                } else if (index == 0) {
                    ResData resData = this.mResData;
                    obtainStyledAttributes2.getString(index);
                    resData.getClass();
                } else if (index == 9) {
                    this.mResData.mOriginColor = obtainStyledAttributes2.getString(index);
                } else if (index == 26) {
                    this.mResData.mWhiteBgColor = obtainStyledAttributes2.getString(index);
                } else if (index == 18) {
                    this.mResData.mThemeColor = obtainStyledAttributes2.getString(index);
                } else if (index == 15) {
                    this.mResData.mThemeBlackColor = obtainStyledAttributes2.getString(index);
                } else if (index == 11) {
                    this.mResData.mOriginShadowColor = obtainStyledAttributes2.getString(index);
                } else if (index == 28) {
                    this.mResData.mWhiteBgShadowColor = obtainStyledAttributes2.getString(index);
                } else if (index == 21) {
                    this.mResData.mThemeShadowColor = obtainStyledAttributes2.getString(index);
                } else if (index == 17) {
                    this.mResData.mThemeBlackShadowColor = obtainStyledAttributes2.getString(index);
                } else if (index == 8) {
                    this.mResData.mOriginBackground = obtainStyledAttributes2.getString(index);
                } else if (index == 25) {
                    this.mResData.mWhiteBgBackground = obtainStyledAttributes2.getString(index);
                } else if (index == 13) {
                    this.mResData.mThemeBackground = obtainStyledAttributes2.getString(index);
                } else if (index == 14) {
                    this.mResData.mThemeBlackBackground = obtainStyledAttributes2.getString(index);
                } else if (index == 2) {
                    this.mResData.mCompoundDrawable = obtainStyledAttributes2.getString(index);
                } else if (index == 4) {
                    this.mResData.mCompoundScale = obtainStyledAttributes2.getString(index);
                } else if (index == 3) {
                    this.mResData.mCompoundPadding = obtainStyledAttributes2.getString(index);
                } else if (index == 7) {
                    this.mResData.mMovable = obtainStyledAttributes2.getBoolean(index, false);
                } else if (index == 5) {
                    this.mResData.mGroup = obtainStyledAttributes2.getString(index);
                } else if (index == 12) {
                    this.mResData.mReduceTransparency = obtainStyledAttributes2.getBoolean(index, false);
                } else if (index == 20) {
                    this.mResData.mThemePolicyIgnorable = obtainStyledAttributes2.getBoolean(index, false);
                } else if (index == 24) {
                    this.mResData.mWallpaperColorIgnorable = obtainStyledAttributes2.getBoolean(index, false);
                }
            }
            refreshResIds$4();
        }
        obtainStyledAttributes2.recycle();
        this.mMainExecutor = context.getMainExecutor();
    }
}
