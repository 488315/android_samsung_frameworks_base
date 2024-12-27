package com.android.systemui.widget;

import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import com.android.systemui.Dependency;
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.lockstar.PluginLockStarManager;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.res.R$styleable;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;

public class SystemUIImageView extends ImageView implements SystemUIWidgetCallback {
    public String mDefaultArea;
    public boolean mHasAttr;
    public boolean mIsCallbackRegistered;
    public boolean mIsLockStarEnabled;
    public boolean mIsPluginLockEnabled;
    public final AnonymousClass1 mLockStarCallback;
    public long mPendingUpdateFlag;
    public final PluginLockManager mPluginLockManager;
    public final AnonymousClass2 mPluginLockStarCallback;
    public final PluginLockStarManager mPluginLockStarManager;
    public final ResData mResData;
    public long mUpdateFlag;
    public final SystemUIWidgetRes mWidgetRes;

    public final class ResData {
        public String mGroup;
        public boolean mLockStar;
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

    public SystemUIImageView(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mHasAttr) {
            ResData resData = this.mResData;
            this.mDefaultArea = resData.mWallpaperArea;
            if (resData.mMovable) {
                this.mPluginLockManager.registerSystemUIViewCallback(this.mLockStarCallback);
                if (this.mIsPluginLockEnabled) {
                    ResData resData2 = this.mResData;
                    resData2.mWallpaperArea = this.mPluginLockManager.getLockStarItemLocationInfo(resData2.mGroup);
                }
            }
            if (this.mResData.mLockStar) {
                this.mPluginLockStarManager.registerCallback(((ImageView) this).mContext.getResources().getResourceEntryName(getId()), this.mPluginLockStarCallback);
            }
            SystemUIWidgetUtil.registerSystemUIWidgetCallback(this, SystemUIWidgetUtil.convertFlag(this.mResData.mWallpaperArea));
            this.mIsCallbackRegistered = true;
        }
    }

    @Override // android.widget.ImageView, android.view.View
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
        if (this.mResData.mLockStar) {
            this.mPluginLockStarManager.unregisterCallback(((ImageView) this).mContext.getResources().getResourceEntryName(getId()));
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setImageTintList(null);
        ResData resData = this.mResData;
        int i = resData.mOriginImageId;
        int i2 = resData.mOriginBackgroundId;
        if (i > 0) {
            Log.e("SystemUIImageView", "set Image Drawable!!");
            setImageDrawable(((ImageView) this).mContext.getDrawable(i));
        }
        if (i2 > 0) {
            Log.e("SystemUIImageView", "set Background Drawable!!");
            setBackground(((ImageView) this).mContext.getDrawable(i2));
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i == 16) {
            return false;
        }
        return super.performAccessibilityAction(i, bundle);
    }

    public final void refreshResIds$3() {
        ResData resData = this.mResData;
        String str = resData.mOriginColor;
        if (str != null) {
            this.mWidgetRes.getResIdByName(str, "color");
            resData.getClass();
        }
        ResData resData2 = this.mResData;
        String str2 = resData2.mWhiteBgColor;
        if (str2 != null) {
            resData2.mWhiteBgColorId = this.mWidgetRes.getResIdByName(str2, "color");
        }
        ResData resData3 = this.mResData;
        String str3 = resData3.mThemeColor;
        if (str3 != null) {
            resData3.mThemeColorId = this.mWidgetRes.getResIdByName(str3, "color");
        }
        ResData resData4 = this.mResData;
        String str4 = resData4.mThemeBlackColor;
        if (str4 != null) {
            resData4.mThemeBlackColorId = this.mWidgetRes.getResIdByName(str4, "color");
        }
        ResData resData5 = this.mResData;
        String str5 = resData5.mOriginImage;
        if (str5 != null) {
            resData5.mOriginImageId = this.mWidgetRes.getResIdByName(str5, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData6 = this.mResData;
        String str6 = resData6.mWhiteBgImage;
        if (str6 != null) {
            resData6.mWhiteBgImageId = this.mWidgetRes.getResIdByName(str6, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData7 = this.mResData;
        String str7 = resData7.mThemeImage;
        if (str7 != null) {
            resData7.mThemeImageId = this.mWidgetRes.getResIdByName(str7, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData8 = this.mResData;
        String str8 = resData8.mThemeBlackImage;
        if (str8 != null) {
            resData8.mThemeBlackImageId = this.mWidgetRes.getResIdByName(str8, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData9 = this.mResData;
        String str9 = resData9.mWhiteBgTintColor;
        if (str9 != null) {
            this.mWidgetRes.getResIdByName(str9, "color");
            resData9.getClass();
        }
        ResData resData10 = this.mResData;
        String str10 = resData10.mOriginBackground;
        if (str10 != null) {
            resData10.mOriginBackgroundId = this.mWidgetRes.getResIdByName(str10, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData11 = this.mResData;
        String str11 = resData11.mWhiteBgBackground;
        if (str11 != null) {
            resData11.mWhiteBgBackgroundId = this.mWidgetRes.getResIdByName(str11, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData12 = this.mResData;
        String str12 = resData12.mThemeBackground;
        if (str12 != null) {
            resData12.mThemeBackgroundId = this.mWidgetRes.getResIdByName(str12, BriefViewController.URI_PATH_DRAWABLE);
        }
        ResData resData13 = this.mResData;
        String str13 = resData13.mThemeBlackBackground;
        if (str13 != null) {
            resData13.mThemeBlackBackgroundId = this.mWidgetRes.getResIdByName(str13, BriefViewController.URI_PATH_DRAWABLE);
        }
    }

    public final void setOriginImage(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals(this.mResData.mOriginImage)) {
            this.mResData.mOriginImage = str;
        }
        if (this.mResData.mOriginImage != null) {
            if (!this.mHasAttr) {
                this.mHasAttr = true;
                ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).registerCallback(false, this, -1L);
            }
            ResData resData = this.mResData;
            resData.mOriginImageId = this.mWidgetRes.getResIdByName(resData.mOriginImage, BriefViewController.URI_PATH_DRAWABLE);
            Log.d("SystemUIImageView", "setOriginImage() this = " + toString());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            long j = this.mPendingUpdateFlag;
            if (j != 0) {
                updateStyle(j, ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).getSemWallpaperColors(false));
                this.mPendingUpdateFlag = 0L;
            }
        }
    }

    public final void setWhiteBgImage(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals(this.mResData.mWhiteBgImage)) {
            this.mResData.mWhiteBgImage = str;
        }
        if (this.mResData.mWhiteBgImage != null) {
            if (!this.mHasAttr) {
                this.mHasAttr = true;
                ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).registerCallback(false, this, -1L);
            }
            ResData resData = this.mResData;
            resData.mWhiteBgImageId = this.mWidgetRes.getResIdByName(resData.mWhiteBgImage, BriefViewController.URI_PATH_DRAWABLE);
            Log.d("SystemUIImageView", "setWhiteBgImage() this = " + toString());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01ee  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateImage() {
        /*
            Method dump skipped, instructions count: 515
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.widget.SystemUIImageView.updateImage():void");
    }

    public void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if (j == 0) {
            return;
        }
        if (getVisibility() != 0) {
            this.mPendingUpdateFlag = j;
            return;
        }
        Log.d("SystemUIImageView", "updateStyle() flag=" + Long.toHexString(this.mUpdateFlag) + "," + Long.toHexString(j) + " : " + toString());
        this.mUpdateFlag = j;
        refreshResIds$3();
        updateImage();
    }

    public SystemUIImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SystemUIImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.widget.SystemUIImageView$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.widget.SystemUIImageView$2] */
    public SystemUIImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mUpdateFlag = 0L;
        this.mResData = new ResData(0);
        this.mHasAttr = false;
        this.mIsCallbackRegistered = false;
        this.mPendingUpdateFlag = 0L;
        this.mPluginLockManager = (PluginLockManager) Dependency.sDependency.getDependencyInner(PluginLockManager.class);
        this.mLockStarCallback = new PluginLockListener.State() { // from class: com.android.systemui.widget.SystemUIImageView.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public final void onLockStarEnabled(boolean z) {
                SystemUIImageView systemUIImageView = SystemUIImageView.this;
                systemUIImageView.mIsPluginLockEnabled = z;
                if (z) {
                    ResData resData = systemUIImageView.mResData;
                    resData.mWallpaperArea = systemUIImageView.mPluginLockManager.getLockStarItemLocationInfo(resData.mGroup);
                } else {
                    systemUIImageView.mResData.mWallpaperArea = systemUIImageView.mDefaultArea;
                }
                ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).removeCallback(false, systemUIImageView);
                WallpaperUtils.registerSystemUIWidgetCallback(systemUIImageView, SystemUIWidgetUtil.convertFlag(systemUIImageView.mResData.mWallpaperArea));
            }
        };
        this.mPluginLockStarManager = (PluginLockStarManager) Dependency.sDependency.getDependencyInner(PluginLockStarManager.class);
        this.mPluginLockStarCallback = new PluginLockStarManager.LockStarCallback() { // from class: com.android.systemui.widget.SystemUIImageView.2
            @Override // com.android.systemui.lockstar.PluginLockStarManager.LockStarCallback
            public final void onChangedLockStarData(boolean z) {
                SystemUIImageView systemUIImageView = SystemUIImageView.this;
                systemUIImageView.mIsLockStarEnabled = z;
                if (z) {
                    return;
                }
                systemUIImageView.updateImage();
            }
        };
        this.mWidgetRes = SystemUIWidgetRes.getInstance(context);
        TypedArray obtainStyledAttributes = ((ImageView) this).mContext.obtainStyledAttributes(attributeSet, R$styleable.SysuiWidgetRes, i, i2);
        if (obtainStyledAttributes != null) {
            int indexCount = obtainStyledAttributes.getIndexCount();
            if (indexCount > 0) {
                this.mHasAttr = true;
            }
            for (int i3 = 0; i3 < indexCount; i3++) {
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
                } else if (index == 6) {
                    this.mResData.mLockStar = obtainStyledAttributes.getBoolean(index, false);
                } else if (index == 5) {
                    this.mResData.mGroup = obtainStyledAttributes.getString(index);
                } else if (index == 20) {
                    this.mResData.mThemePolicyIgnorable = obtainStyledAttributes.getBoolean(index, false);
                }
            }
            refreshResIds$3();
        }
        obtainStyledAttributes.recycle();
    }
}
