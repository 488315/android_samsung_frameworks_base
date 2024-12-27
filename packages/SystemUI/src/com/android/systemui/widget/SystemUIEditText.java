package com.android.systemui.widget;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import com.android.systemui.Dependency;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.listener.PluginLockListener;
import com.android.systemui.res.R$styleable;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class SystemUIEditText extends EditText implements SystemUIWidgetCallback {
    public final int mAttrCount;
    public String mDefaultArea;
    public final Paint mDrawPaint;
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
        public String mOriginColor;
        public int mOriginColorId;
        public String mOriginShadowColor;
        public int mOriginShadowColorId;
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

    public SystemUIEditText(Context context) {
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
            SystemUIWidgetUtil.registerSystemUIWidgetCallback(this, SystemUIWidgetUtil.convertFlag(this.mResData.mWallpaperArea) | 1024);
            this.mIsCallbackRegistered = true;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        editorInfo.targetInputMethodUser = UserHandle.semOf(ActivityManager.getCurrentUser());
        return onCreateInputConnection;
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
        ResData resData = this.mResData;
        int i = resData.mOriginColorId;
        int i2 = resData.mOriginShadowColorId;
        if (i > 0) {
            int color = ((EditText) this).mContext.getResources().getColor(i, null);
            this.mDrawPaint.setColor(color);
            setTextColor(color);
            setHintTextColor(color);
        }
        if (i2 > 0) {
            setShadowLayer(getShadowRadius(), getShadowDx(), getShadowDy(), ((EditText) this).mContext.getResources().getColor(i2, null));
        }
    }

    public final void refreshResIds$1() {
        SystemUIWidgetRes systemUIWidgetRes = SystemUIWidgetRes.getInstance(((EditText) this).mContext);
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

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0097, code lost:
    
        if (r10 > 0) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0099, code lost:
    
        r8 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00a9, code lost:
    
        if (r10 > 0) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void updateStyle(long r8, android.app.SemWallpaperColors r10) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.widget.SystemUIEditText.updateStyle(long, android.app.SemWallpaperColors):void");
    }

    public SystemUIEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public SystemUIEditText(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.widget.SystemUIEditText$1] */
    public SystemUIEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDrawPaint = new Paint();
        this.mUpdateFlag = 0L;
        this.mResData = new ResData(0);
        this.mAttrCount = 0;
        this.mIsCallbackRegistered = false;
        this.mPendingUpdateFlag = 0L;
        this.mPluginLockManager = (PluginLockManager) Dependency.sDependency.getDependencyInner(PluginLockManager.class);
        this.mLockStarCallback = new PluginLockListener.State() { // from class: com.android.systemui.widget.SystemUIEditText.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener.State
            public final void onLockStarEnabled(boolean z) {
                SystemUIEditText systemUIEditText = SystemUIEditText.this;
                systemUIEditText.mIsLockStarEnabled = z;
                if (z) {
                    ResData resData = systemUIEditText.mResData;
                    resData.mWallpaperArea = systemUIEditText.mPluginLockManager.getLockStarItemLocationInfo(resData.mGroup);
                } else {
                    systemUIEditText.mResData.mWallpaperArea = systemUIEditText.mDefaultArea;
                }
                ((WallpaperEventNotifier) Dependency.sDependency.getDependencyInner(WallpaperEventNotifier.class)).removeCallback(false, systemUIEditText);
                WallpaperUtils.registerSystemUIWidgetCallback(systemUIEditText, SystemUIWidgetUtil.convertFlag(systemUIEditText.mResData.mWallpaperArea));
            }
        };
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
                } else if (index == 7) {
                    this.mResData.mMovable = obtainStyledAttributes.getBoolean(index, false);
                } else if (index == 5) {
                    this.mResData.mGroup = obtainStyledAttributes.getString(index);
                } else if (index == 20) {
                    this.mResData.mThemePolicyIgnorable = obtainStyledAttributes.getBoolean(index, false);
                }
            }
            refreshResIds$1();
        }
        obtainStyledAttributes.recycle();
    }
}
