package com.android.systemui.widget;

import android.R;
import android.app.ActivityManager;
import android.app.SemWallpaperColors;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.R$styleable;
import com.android.systemui.pluginlock.PluginLockManager;
import com.android.systemui.pluginlock.PluginLockManagerImpl;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.wallpaper.WallpaperUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SystemUIEditText extends EditText implements SystemUIWidgetCallback {
    public int mAttrCount;
    public String mDefaultArea;
    public final Paint mDrawPaint;
    public boolean mIsCallbackRegistered;
    public boolean mIsLockStarEnabled;
    public final C37301 mLockStarCallback;
    public long mPendingUpdateFlag;
    public final PluginLockManager mPluginLockManager;
    public final ResData mResData;
    public long mUpdateFlag;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                ((PluginLockManagerImpl) this.mPluginLockManager).registerSystemUIViewCallback(this.mLockStarCallback);
                if (this.mIsLockStarEnabled) {
                    ResData resData2 = this.mResData;
                    resData2.mWallpaperArea = ((PluginLockManagerImpl) this.mPluginLockManager).getLockStarItemLocationInfo(resData2.mGroup);
                }
            }
            SystemUIWidgetUtil.registerSystemUIWidgetCallback(this, SystemUIWidgetUtil.convertFlag(this.mResData.mWallpaperArea) | 1024);
            this.mIsCallbackRegistered = true;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        int currentUser = ActivityManager.getCurrentUser();
        if (currentUser == 77) {
            editorInfo.targetInputMethodUser = UserHandle.semOf(currentUser);
        } else {
            editorInfo.targetInputMethodUser = UserHandle.semOf(KeyguardUpdateMonitor.getCurrentUser());
        }
        return onCreateInputConnection;
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mIsCallbackRegistered) {
            this.mIsCallbackRegistered = false;
            ((WallpaperEventNotifier) Dependency.get(WallpaperEventNotifier.class)).removeCallback(false, this);
        }
        if (this.mResData.mMovable) {
            ((PluginLockManagerImpl) this.mPluginLockManager).removeSystemUIViewCallback(this.mLockStarCallback);
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

    public final void refreshResIds() {
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
                updateStyle(j, ((WallpaperEventNotifier) Dependency.get(WallpaperEventNotifier.class)).getSemWallpaperColors(false));
                this.mPendingUpdateFlag = 0L;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0098, code lost:
    
        if (r9 > 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ab, code lost:
    
        r8 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a9, code lost:
    
        if (r9 > 0) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        int i;
        boolean z = false;
        if (j != 0) {
            if (getVisibility() != 0) {
                this.mPendingUpdateFlag = j;
            } else {
                z = true;
            }
        }
        if (z) {
            Log.d("SystemUIEditText", "updateStyle() flag=" + Long.toHexString(this.mUpdateFlag) + "," + Long.toHexString(j) + " : " + toString());
            this.mUpdateFlag = j;
            refreshResIds();
            semClearAllTextEffect();
            boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(this.mResData.mWallpaperArea);
            ResData resData = this.mResData;
            int i2 = isWhiteKeyguardWallpaper ? resData.mWhiteBgColorId : resData.mOriginColorId;
            int i3 = isWhiteKeyguardWallpaper ? resData.mWhiteBgShadowColorId : resData.mOriginShadowColorId;
            if ((this.mUpdateFlag & 1) != 0 && WallpaperUtils.isOpenThemeLook()) {
                if (SystemUIWidgetUtil.needsBlackComponent(((EditText) this).mContext, SystemUIWidgetUtil.convertFlag(this.mResData.mWallpaperArea), this.mResData.mThemePolicyIgnorable)) {
                    Log.d("SystemUIEditText", "apply style: theme : white");
                    ResData resData2 = this.mResData;
                    int i4 = resData2.mThemeBlackColorId;
                    if (i4 > 0) {
                        i2 = i4;
                    }
                    i = resData2.mThemeBlackShadowColorId;
                } else {
                    Log.d("SystemUIEditText", "apply style: theme : black");
                    ResData resData3 = this.mResData;
                    int i5 = resData3.mThemeColorId;
                    if (i5 > 0) {
                        i2 = i5;
                    }
                    i = resData3.mThemeShadowColorId;
                }
            }
            if (i2 > 0) {
                int color = ((EditText) this).mContext.getResources().getColor(i2, null);
                this.mDrawPaint.setColor(color);
                setTextColor(color);
                setHintTextColor(color);
                Log.d("SystemUIEditText", "textColor=" + String.format("#%08X", Integer.valueOf(color)));
            }
            if (i3 > 0) {
                int color2 = ((EditText) this).mContext.getResources().getColor(i3, null);
                setShadowLayer(getShadowRadius(), getShadowDx(), getShadowDy(), color2);
                Log.d("SystemUIEditText", "shadowColor=" + String.format("#%08X", Integer.valueOf(color2)));
            }
        }
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
        this.mPluginLockManager = (PluginLockManager) Dependency.get(PluginLockManager.class);
        this.mLockStarCallback = new PluginLockListener$State() { // from class: com.android.systemui.widget.SystemUIEditText.1
            @Override // com.android.systemui.pluginlock.listener.PluginLockListener$State
            public final void onLockStarEnabled(boolean z) {
                SystemUIEditText systemUIEditText = SystemUIEditText.this;
                systemUIEditText.mIsLockStarEnabled = z;
                if (z) {
                    ResData resData = systemUIEditText.mResData;
                    resData.mWallpaperArea = ((PluginLockManagerImpl) systemUIEditText.mPluginLockManager).getLockStarItemLocationInfo(resData.mGroup);
                } else {
                    systemUIEditText.mResData.mWallpaperArea = systemUIEditText.mDefaultArea;
                }
                ((WallpaperEventNotifier) Dependency.get(WallpaperEventNotifier.class)).removeCallback(false, systemUIEditText);
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
            refreshResIds();
        }
        obtainStyledAttributes.recycle();
    }
}
