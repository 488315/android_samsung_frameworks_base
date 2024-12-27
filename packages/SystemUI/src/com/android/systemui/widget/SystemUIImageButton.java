package com.android.systemui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import com.android.systemui.Dependency;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.res.R$styleable;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ResData {
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

        private ResData() {
        }

        public /* synthetic */ ResData(int i) {
            this();
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
            resData5.mOriginImageId = systemUIWidgetRes.getResIdByName(str5, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData6 = this.mResData;
        String str6 = resData6.mWhiteBgImage;
        if (str6 != null) {
            resData6.mWhiteBgImageId = systemUIWidgetRes.getResIdByName(str6, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData7 = this.mResData;
        String str7 = resData7.mThemeImage;
        if (str7 != null) {
            resData7.mThemeImageId = systemUIWidgetRes.getResIdByName(str7, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData8 = this.mResData;
        String str8 = resData8.mThemeBlackImage;
        if (str8 != null) {
            resData8.mThemeBlackImageId = systemUIWidgetRes.getResIdByName(str8, BriefViewController.URI_PATH_DRAWABLE);
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
            resData10.mOriginBackgroundId = systemUIWidgetRes.getResIdByName(str10, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData11 = this.mResData;
        String str11 = resData11.mWhiteBgBackground;
        if (str11 != null) {
            resData11.mWhiteBgBackgroundId = systemUIWidgetRes.getResIdByName(str11, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData12 = this.mResData;
        String str12 = resData12.mThemeBackground;
        if (str12 != null) {
            resData12.mThemeBackgroundId = systemUIWidgetRes.getResIdByName(str12, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData13 = this.mResData;
        String str13 = resData13.mThemeBlackBackground;
        if (str13 != null) {
            resData13.mThemeBlackBackgroundId = systemUIWidgetRes.getResIdByName(str13, BriefViewController.URI_PATH_DRAWABLE);
        }
    }

    @Override // android.widget.ImageView, android.view.View
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

    /* JADX WARN: Removed duplicated region for block: B:32:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateStyle(long r9, android.app.SemWallpaperColors r11) {
        /*
            Method dump skipped, instructions count: 361
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.widget.SystemUIImageButton.updateStyle(long, android.app.SemWallpaperColors):void");
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
        TypedArray obtainStyledAttributes = ((ImageButton) this).mContext.obtainStyledAttributes(attributeSet, R$styleable.SysuiWidgetRes, i, i2);
        this.mResData = new ResData(0);
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
                } else if (index == 10) {
                    this.mResData.mOriginImage = obtainStyledAttributes.getString(index);
                } else if (index == 19) {
                    this.mResData.mThemeImage = obtainStyledAttributes.getString(index);
                } else if (index == 16) {
                    this.mResData.mThemeBlackImage = obtainStyledAttributes.getString(index);
                } else if (index == 27) {
                    this.mResData.mWhiteBgImage = obtainStyledAttributes.getString(index);
                } else if (index == 29) {
                    this.mResData.mWhiteBgTintColor = obtainStyledAttributes.getString(index);
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
            refreshResIds$2();
        }
        obtainStyledAttributes.recycle();
    }
}
