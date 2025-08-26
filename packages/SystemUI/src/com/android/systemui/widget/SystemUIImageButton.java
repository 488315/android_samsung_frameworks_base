package com.android.systemui.widget;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.res.R$styleable;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;

/* loaded from: classes3.dex */
public class SystemUIImageButton extends ImageButton implements SystemUIWidgetCallback {
    public final int mAttrCount;
    public String mDefaultArea;
    public boolean mIsCallbackRegistered;
    public boolean mIsLockStarEnabled;
    public final AnonymousClass1 mLockStarCallback;
    public long mPendingUpdateFlag;
    public final PluginLockManager mPluginLockManager;
    public final ResData mResData;
    public long mUpdateFlag;

    public class ResData {
        public String mGroup;
        public boolean mMovable;
        public String mOriginBackground;
        public int mOriginBackgroundId;
        public String mOriginColor;
        public String mOriginImage;
        public int mOriginImageId;
        public String mThemeBackground;
        public int mThemeBackgroundId;
        public String mThemeBlackBackground;
        public int mThemeBlackBackgroundId;
        public String mThemeBlackColor;
        public int mThemeBlackColorId;
        public String mThemeBlackImage;
        public int mThemeBlackImageId;
        public String mThemeColor;
        public int mThemeColorId;
        public String mThemeImage;
        public int mThemeImageId;
        public boolean mThemePolicyIgnorable;
        public String mWallpaperArea;
        public String mWhiteBgBackground;
        public int mWhiteBgBackgroundId;
        public String mWhiteBgColor;
        public int mWhiteBgColorId;
        public String mWhiteBgImage;
        public int mWhiteBgImageId;
        public String mWhiteBgTintColor;

        public /* synthetic */ ResData(int i) {
            this();
        }

        private ResData() {
        }
    }

    public SystemUIImageButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
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

    @Override // android.widget.ImageView, android.view.View
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
    public final void onFinishInflate() {
        Drawable drawable;
        super.onFinishInflate();
        ResData resData = this.mResData;
        int i = resData.mOriginImageId;
        int i2 = resData.mOriginBackgroundId;
        if (i > 0 && (drawable = ((ImageButton) this).mContext.getDrawable(i)) != null) {
            setImageDrawable(drawable);
        }
        if (i2 > 0) {
            setBackground(((ImageButton) this).mContext.getResources().getDrawable(i2, null));
        }
    }

    public final void refreshResIds$2() {
        SystemUIWidgetRes systemUIWidgetRes = SystemUIWidgetRes.getInstance(((ImageButton) this).mContext);
        ResData resData = this.mResData;
        String str = resData.mOriginColor;
        if (str != null) {
            systemUIWidgetRes.getResIdByName(str, "color");
            resData.getClass();
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
        String str5 = resData5.mOriginImage;
        if (str5 != null) {
            resData5.mOriginImageId = systemUIWidgetRes.getResIdByName(str5, "drawable");
        }
        ResData resData6 = this.mResData;
        String str6 = resData6.mWhiteBgImage;
        if (str6 != null) {
            resData6.mWhiteBgImageId = systemUIWidgetRes.getResIdByName(str6, "drawable");
        }
        ResData resData7 = this.mResData;
        String str7 = resData7.mThemeImage;
        if (str7 != null) {
            resData7.mThemeImageId = systemUIWidgetRes.getResIdByName(str7, "drawable");
        }
        ResData resData8 = this.mResData;
        String str8 = resData8.mThemeBlackImage;
        if (str8 != null) {
            resData8.mThemeBlackImageId = systemUIWidgetRes.getResIdByName(str8, "drawable");
        }
        ResData resData9 = this.mResData;
        String str9 = resData9.mWhiteBgTintColor;
        if (str9 != null) {
            systemUIWidgetRes.getResIdByName(str9, "color");
            resData9.getClass();
        }
        ResData resData10 = this.mResData;
        String str10 = resData10.mOriginBackground;
        if (str10 != null) {
            resData10.mOriginBackgroundId = systemUIWidgetRes.getResIdByName(str10, "drawable");
        }
        ResData resData11 = this.mResData;
        String str11 = resData11.mWhiteBgBackground;
        if (str11 != null) {
            resData11.mWhiteBgBackgroundId = systemUIWidgetRes.getResIdByName(str11, "drawable");
        }
        ResData resData12 = this.mResData;
        String str12 = resData12.mThemeBackground;
        if (str12 != null) {
            resData12.mThemeBackgroundId = systemUIWidgetRes.getResIdByName(str12, "drawable");
        }
        ResData resData13 = this.mResData;
        String str13 = resData13.mThemeBlackBackground;
        if (str13 != null) {
            resData13.mThemeBlackBackgroundId = systemUIWidgetRes.getResIdByName(str13, "drawable");
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final void setVisibility(int i) throws Resources.NotFoundException {
        super.setVisibility(i);
        if (i == 0) {
            long j = this.mPendingUpdateFlag;
            if (j != 0) {
                updateStyle(j, ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).getSemWallpaperColors(false));
                this.mPendingUpdateFlag = 0L;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) throws Resources.NotFoundException {
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2;
        Drawable drawable;
        if (j == 0) {
            return;
        }
        if (getVisibility() != 0) {
            this.mPendingUpdateFlag = j;
            return;
        }
        Log.d("SystemUIImageButton", "updateStyle() flag=" + Long.toHexString(this.mUpdateFlag) + "," + Long.toHexString(j) + " : " + toString());
        this.mUpdateFlag = j;
        refreshResIds$2();
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(this.mResData.mWallpaperArea);
        ResData resData = this.mResData;
        int i = zIsWhiteKeyguardWallpaper ? resData.mWhiteBgImageId : resData.mOriginImageId;
        ResData resData2 = this.mResData;
        int i2 = zIsWhiteKeyguardWallpaper ? resData2.mWhiteBgBackgroundId : resData2.mOriginBackgroundId;
        if ((this.mUpdateFlag & 1) == 0 || !WallpaperUtils.isOpenThemeLook()) {
            if (zIsWhiteKeyguardWallpaper) {
                ResData resData3 = this.mResData;
                if (resData3.mWhiteBgImage != null || resData3.mWhiteBgColor != null) {
                    Log.d("SystemUIImageButton", "apply style: white-bg");
                    ResData resData4 = this.mResData;
                    i = resData4.mWhiteBgImageId;
                    if (i <= 0) {
                        if (resData4.mWhiteBgColorId > 0) {
                            int color = ((ImageButton) this).mContext.getResources().getColor(this.mResData.mWhiteBgColorId, null);
                            porterDuffColorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                            Log.d("SystemUIImageButton", "filter: ".concat(String.format("#%08X", Integer.valueOf(color))));
                        } else {
                            porterDuffColorFilter = null;
                        }
                        PorterDuffColorFilter porterDuffColorFilter3 = porterDuffColorFilter;
                        i = this.mResData.mOriginImageId;
                        porterDuffColorFilter2 = porterDuffColorFilter3;
                    }
                }
            }
            if (i > 0 && (drawable = ((ImageButton) this).mContext.getDrawable(i)) != null) {
                if (porterDuffColorFilter2 != null) {
                    Log.e("SystemUIImageButton", "filter is not null!!");
                    drawable.setColorFilter(porterDuffColorFilter2);
                }
                setImageDrawable(drawable);
            }
            if (i2 <= 0) {
                Log.e("SystemUIImageButton", "resBgId is not null!!");
                setBackground(((ImageButton) this).mContext.getResources().getDrawable(i2, null));
                return;
            }
            return;
        }
        if (SystemUIWidgetUtil.needsBlackComponent(((ImageButton) this).mContext, SystemUIWidgetUtil.convertFlag(this.mResData.mWallpaperArea), this.mResData.mThemePolicyIgnorable)) {
            Log.d("SystemUIImageButton", "apply style: theme : white");
            ResData resData5 = this.mResData;
            i = resData5.mThemeBlackImageId;
            if (i <= 0) {
                if (resData5.mThemeBlackColorId > 0) {
                    setImageTintList(ColorStateList.valueOf(((ImageButton) this).mContext.getResources().getColor(this.mResData.mThemeBlackColorId, null)));
                }
                i = this.mResData.mWhiteBgImageId;
            }
            i2 = this.mResData.mThemeBlackBackgroundId;
        } else {
            Log.d("SystemUIImageButton", "apply style: theme");
            ResData resData6 = this.mResData;
            int i3 = resData6.mThemeImageId;
            if (i3 <= 0) {
                if (resData6.mThemeColorId > 0) {
                    setImageTintList(ColorStateList.valueOf(((ImageButton) this).mContext.getResources().getColor(this.mResData.mThemeColorId, null)));
                }
                i = zIsWhiteKeyguardWallpaper ? this.mResData.mWhiteBgImageId : this.mResData.mOriginImageId;
            } else {
                i = i3;
            }
            i2 = this.mResData.mThemeBackgroundId;
        }
        porterDuffColorFilter2 = null;
        if (i > 0) {
            if (porterDuffColorFilter2 != null) {
            }
            setImageDrawable(drawable);
        }
        if (i2 <= 0) {
        }
    }

    public SystemUIImageButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SystemUIImageButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.widget.SystemUIImageButton$1] */
    public SystemUIImageButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mUpdateFlag = 0L;
        this.mResData = null;
        this.mAttrCount = 0;
        this.mIsCallbackRegistered = false;
        this.mPendingUpdateFlag = 0L;
        this.mPluginLockManager = (PluginLockManager) Dependency.sDependency.getDependencyInner(PluginLockManager.class);
        this.mLockStarCallback = new PluginLockListener.State() { // from class: com.android.systemui.widget.SystemUIImageButton.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public final void onLockStarEnabled(boolean z) {
                SystemUIImageButton systemUIImageButton = SystemUIImageButton.this;
                systemUIImageButton.mIsLockStarEnabled = z;
                if (z) {
                    ResData resData = systemUIImageButton.mResData;
                    resData.mWallpaperArea = systemUIImageButton.mPluginLockManager.getLockStarItemLocationInfo(resData.mGroup);
                } else {
                    systemUIImageButton.mResData.mWallpaperArea = systemUIImageButton.mDefaultArea;
                }
                ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).removeCallback(false, systemUIImageButton);
                WallpaperUtils.registerSystemUIWidgetCallback(systemUIImageButton, SystemUIWidgetUtil.convertFlag(systemUIImageButton.mResData.mWallpaperArea));
            }
        };
        TypedArray typedArrayObtainStyledAttributes = ((ImageButton) this).mContext.obtainStyledAttributes(attributeSet, R$styleable.SysuiWidgetRes, i, i2);
        this.mResData = new ResData(0);
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
                } else if (index == 10) {
                    this.mResData.mOriginImage = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 19) {
                    this.mResData.mThemeImage = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 16) {
                    this.mResData.mThemeBlackImage = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 27) {
                    this.mResData.mWhiteBgImage = typedArrayObtainStyledAttributes.getString(index);
                } else if (index == 29) {
                    this.mResData.mWhiteBgTintColor = typedArrayObtainStyledAttributes.getString(index);
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
            refreshResIds$2();
        }
        typedArrayObtainStyledAttributes.recycle();
    }
}
