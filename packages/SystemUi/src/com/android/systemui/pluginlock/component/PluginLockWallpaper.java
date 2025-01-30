package com.android.systemui.pluginlock.component;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.LsRune;
import com.android.systemui.Prefs;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.feature.SemCscFeature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginLockWallpaper extends AbstractPluginLockItem {
    public static boolean sDualDisplayPlugin = false;
    public static int sScreenType = 0;
    public static boolean sScreenTypeChanged = false;
    public boolean mHasData;
    public boolean mHintUpdatedSkip;
    public int mRecoverRequestedScreen;
    public final List mWallpaperDataList;
    public PluginWallpaperCallback mWallpaperUpdateCallback;
    public boolean mWholeRecoverRequired;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PluginLockWallpaperData {
        public Bitmap mBitmap;
        public SemWallpaperColors mHints;
        public String mIntelligentCrop;
        public String mPath;
        public int mResourceId;
        public int mType;
        public Uri mUri;

        public /* synthetic */ PluginLockWallpaperData(int i) {
            this();
        }

        public final void resetAll() {
            this.mType = -2;
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.mBitmap.recycle();
            }
            this.mPath = null;
            this.mBitmap = null;
            this.mUri = null;
            this.mHints = null;
            this.mResourceId = 0;
        }

        private PluginLockWallpaperData() {
            this.mType = -2;
            this.mPath = null;
            this.mBitmap = null;
            this.mIntelligentCrop = null;
            this.mResourceId = 0;
            this.mUri = null;
            this.mHints = null;
        }
    }

    public PluginLockWallpaper(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mRecoverRequestedScreen = -1;
        int i = 0;
        this.mHintUpdatedSkip = false;
        this.mWholeRecoverRequired = false;
        this.mHasData = false;
        ArrayList arrayList = new ArrayList();
        this.mWallpaperDataList = arrayList;
        arrayList.add(0, new PluginLockWallpaperData(i));
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            arrayList.add(1, new PluginLockWallpaperData(i));
        }
    }

    public static Bitmap getBitmap(int i, Resources resources) {
        Drawable drawable = resources.getDrawable(i, null);
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(resources, i);
        }
        if (!(drawable instanceof GradientDrawable)) {
            Log.w("PluginLockWallpaper", "getBitmap() unsupported " + drawable);
            return null;
        }
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        int i2 = displayMetrics.widthPixels;
        int i3 = displayMetrics.heightPixels;
        Canvas canvas = new Canvas();
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(createBitmap);
        drawable.setBounds(0, 0, i2, i3);
        drawable.draw(canvas);
        return createBitmap;
    }

    public static String getMultiPackPkgName(String str) {
        if (str == null || str.isEmpty()) {
            return "no_matched_pkg_name";
        }
        Matcher matcher = Pattern.compile("MULTIPLE=(.*):tilt").matcher(str);
        return matcher.find() ? matcher.group(1) : "no_matched_pkg_name";
    }

    public static boolean isCloneDisplayRequired() {
        return !sDualDisplayPlugin && LsRune.LOCKUI_SUB_DISPLAY_LOCK;
    }

    public final void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d("PluginLockWallpaper", "apply()");
        if (dynamicLockData == null || !dynamicLockData2.getWallpaperData().equals(dynamicLockData.getWallpaperData())) {
            dynamicLockData2.getWallpaperData().getUpdateStyle().intValue();
            dynamicLockData2.getWallpaperData().getRecoverType().intValue();
        }
        if (isCloneDisplayRequired()) {
            Iterator it = ((ArrayList) this.mWallpaperDataList).iterator();
            while (it.hasNext()) {
                ((PluginLockWallpaperData) it.next()).resetAll();
            }
        }
    }

    public final void backupWallpaperSource(int i) {
        String str = i == 0 ? "lockscreen_wallpaper_transparent" : "sub_display_lockscreen_wallpaper_transparency";
        int settingsInt = getSettingsInt(-1, str);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("backupWallpaperSource() backupSource: ", settingsInt, ", screenType:", i, "PluginLockWallpaper");
        if (settingsInt != 1) {
            setWallpaperSourceBackupValue(i, settingsInt);
            putSettingsSystem(1, str);
        }
    }

    public final void backupWallpaperType(int i) {
        String str = i == 0 ? "lockscreen_wallpaper" : "lockscreen_wallpaper_sub";
        int settingsInt = getSettingsInt(-1, str);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("backupWallpaperType() backupType: ", settingsInt, ", screenType:", i, "PluginLockWallpaper");
        if (settingsInt == 0) {
            setWallpaperTypeBackupValue(i, -3);
            putSettingsSystem(1, str);
        }
    }

    public final int getScreenType() {
        int i = this.mRecoverRequestedScreen;
        if (i != -1) {
            return i;
        }
        int i2 = !LsRune.LOCKUI_SUB_DISPLAY_LOCK || sScreenType == 0 || isCloneDisplayRequired() ? 0 : sScreenType;
        if (((ArrayList) this.mWallpaperDataList).size() > i2) {
            return i2;
        }
        return 0;
    }

    public final String getWallpaperPath(int i) {
        String str = ((PluginLockWallpaperData) ((ArrayList) this.mWallpaperDataList).get(i)).mPath;
        if (str == null) {
            Log.d("PluginLockWallpaper", "getWallpaperPath() path: null");
            return null;
        }
        try {
            Log.d("PluginLockWallpaper", "getWallpaperPath() path: " + str.substring((str.length() * 20) / 100));
        } catch (Throwable unused) {
        }
        return str;
    }

    public final boolean isCustomPack(int i) {
        String wallpaperPath = getWallpaperPath(i);
        boolean z = wallpaperPath != null && wallpaperPath.contains("com.samsung.custompack");
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isCustomPack, ret:", z, "PluginLockWallpaper");
        return z;
    }

    public final boolean isDynamicWallpaper() {
        int screenType = getScreenType();
        boolean z = ((PluginLockWallpaperData) ((ArrayList) this.mWallpaperDataList).get(screenType)).mType != -2;
        AbstractC0731x5bb8a836.m72m("isDynamicWallpaper() screen:", screenType, ", ret:", z, "PluginLockWallpaper");
        return z;
    }

    public final boolean isServiceWallpaper(int i) {
        String wallpaperPath = getWallpaperPath(i);
        List list = this.mWallpaperDataList;
        return ((wallpaperPath == null || !wallpaperPath.contains("SamsungUX.DW.FreshP")) && ((PluginLockWallpaperData) ((ArrayList) list).get(i)).mBitmap == null && ((PluginLockWallpaperData) ((ArrayList) list).get(i)).mUri == null) ? false : true;
    }

    public final void recoverWallpaperSource(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        int intValue = (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) ? -1 : recoverData.getWallpaperSource(i).intValue();
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("recoverWallpaperSource() backupWallpaperSource: ", intValue, ", screenType:", i, "PluginLockWallpaper");
        if (intValue == -1 || intValue == -2 || intValue == 1) {
            return;
        }
        putSettingsSystem(intValue, i == 0 ? "lockscreen_wallpaper_transparent" : "sub_display_lockscreen_wallpaper_transparency");
    }

    public final void recoverWallpaperType(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        int i2 = -1;
        if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
            i2 = recoverData.getWallpaperType(i).intValue();
        }
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("recoverWallpaperType() backupType: ", i2, ", screenType:", i, "PluginLockWallpaper");
        if (i2 == -3) {
            putSettingsSystem(0, i == 0 ? "lockscreen_wallpaper" : "lockscreen_wallpaper_sub");
        }
    }

    public final void reset(boolean z) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        RecyclerView$$ExternalSyntheticOutline0.m46m(RowView$$ExternalSyntheticOutline0.m49m("reset() reconnectReq:", z, ", screenType:"), sScreenType, "PluginLockWallpaper");
        PluginLockWallpaperData pluginLockWallpaperData = (PluginLockWallpaperData) ((ArrayList) this.mWallpaperDataList).get(getScreenType());
        Bitmap bitmap = pluginLockWallpaperData.mBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            pluginLockWallpaperData.mBitmap.recycle();
        }
        pluginLockWallpaperData.mPath = null;
        pluginLockWallpaperData.mBitmap = null;
        pluginLockWallpaperData.mUri = null;
        pluginLockWallpaperData.mHints = null;
        pluginLockWallpaperData.mResourceId = 0;
        if (pluginLockWallpaperData.mType != -2) {
            if (isCloneDisplayRequired()) {
                recoverWallpaperSource(0);
                recoverWallpaperSource(1);
                recoverWallpaperType(0);
                recoverWallpaperType(1);
            } else {
                recoverWallpaperSource(sScreenType);
                recoverWallpaperType(sScreenType);
            }
            pluginLockWallpaperData.mType = -2;
            if (isCloneDisplayRequired()) {
                setWallpaperBackupValue();
            } else {
                int i = sScreenType;
                PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
                if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
                    recoverData.setWallpaperDynamic(i, -2);
                    recoverData.setWallpaperSource(i, -1);
                    recoverData.setWallpaperType(i, -1);
                    this.mInstanceState.updateDb();
                }
            }
            if (!z && !sScreenTypeChanged && this.mWallpaperUpdateCallback != null) {
                Log.d("PluginLockWallpaper", "reset() onWallpaperUpdate will be called");
                this.mWallpaperUpdateCallback.onWallpaperUpdate(isCloneDisplayRequired());
                updateHint();
            }
        }
        setPluginWallpaperType(0);
        sScreenTypeChanged = false;
        this.mHintUpdatedSkip = false;
        this.mWholeRecoverRequired = false;
        this.mRecoverRequestedScreen = -1;
    }

    public final void resetAll() {
        Log.d("PluginLockWallpaper", "resetAll()");
        Iterator it = ((ArrayList) this.mWallpaperDataList).iterator();
        while (it.hasNext()) {
            ((PluginLockWallpaperData) it.next()).resetAll();
        }
        recoverWallpaperSource(0);
        recoverWallpaperSource(1);
        recoverWallpaperType(0);
        recoverWallpaperType(1);
        setWallpaperBackupValue();
        if (this.mWallpaperUpdateCallback != null) {
            Log.d("PluginLockWallpaper", "resetAll() onWallpaperUpdate will be called");
            this.mWallpaperUpdateCallback.onWallpaperUpdate(false);
            updateHint();
        }
        Context context = this.mContext;
        Prefs.putBoolean(context, "WPaperChangedByDls", false);
        Prefs.putBoolean(context, "WPaperChangedByDlsSub", false);
        this.mWholeRecoverRequired = true;
        setPluginWallpaperType(0);
        sScreenTypeChanged = false;
        this.mHintUpdatedSkip = false;
        this.mWholeRecoverRequired = false;
        this.mRecoverRequestedScreen = -1;
        this.mHasData = false;
    }

    public final void setMultiPackWallpaperSource(int i) {
        boolean z;
        String str = i == 0 ? "lockscreen_wallpaper_transparent" : "sub_display_lockscreen_wallpaper_transparency";
        int settingsInt = getSettingsInt(-1, str);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("setMultiPackWallpaperSource() currentType: ", settingsInt, ", screenType:", i, "PluginLockWallpaper");
        setWallpaperTypeBackupValue(i, -1);
        setWallpaperSourceBackupValue(i, -1);
        String wallpaperPath = getWallpaperPath(getScreenType());
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
        String defaultMultipackStyle = wallpaperManager.getDefaultMultipackStyle(2);
        String defaultMultipackStyle2 = wallpaperManager.getDefaultMultipackStyle(18);
        if (wallpaperPath != null) {
            String multiPackPkgName = getMultiPackPkgName(defaultMultipackStyle);
            String multiPackPkgName2 = getMultiPackPkgName(defaultMultipackStyle2);
            z = wallpaperPath.contains(multiPackPkgName) || wallpaperPath.contains(multiPackPkgName2);
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(AbstractC0866xb1ce8deb.m87m("isPreloadedMultiPack, main:", multiPackPkgName, ", sub:", multiPackPkgName2, ", isPreload"), z, "PluginLockWallpaper");
        } else {
            z = false;
        }
        if (!z) {
            String wallpaperPath2 = getWallpaperPath(getScreenType());
            String str2 = null;
            String string = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigDefaultWallpaperStyle", (String) null);
            if (string != null && !string.isEmpty()) {
                str2 = new StringTokenizer(string, ";").nextToken();
            }
            boolean z2 = (wallpaperPath2 == null || str2 == null || !wallpaperPath2.contains(str2)) ? false : true;
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isSpecialEditionMultiPack, ret:", z2, "PluginLockWallpaper");
            if (!z2) {
                String wallpaperPath3 = getWallpaperPath(getScreenType());
                boolean z3 = wallpaperPath3 != null && wallpaperPath3.contains("SamsungUX.DW.Stub");
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isStubPack, ret:", z3, "PluginLockWallpaper");
                if (!z3) {
                    if (isCustomPack(getScreenType())) {
                        Log.d("PluginLockWallpaper", "setMultiPackWallpaperSource, custom");
                        if (settingsInt != 0) {
                            putSettingsSystem(0, str);
                            return;
                        }
                        return;
                    }
                    Log.d("PluginLockWallpaper", "setMultiPackWallpaperSource, theme");
                    if (settingsInt != 3) {
                        putSettingsSystem(3, str);
                        return;
                    }
                    return;
                }
            }
        }
        Log.d("PluginLockWallpaper", "setMultiPackWallpaperSource, preload");
        if (settingsInt != 1) {
            putSettingsSystem(1, str);
        }
    }

    public final void setPluginWallpaperType(int i) {
        if (sDualDisplayPlugin) {
            boolean z = LsRune.LOCKUI_SUB_DISPLAY_LOCK;
            boolean z2 = true;
            if (!(z && this.mWholeRecoverRequired)) {
                if (z && sScreenType != 0 && !isCloneDisplayRequired()) {
                    z2 = false;
                }
                if (z2) {
                    putSettingsSecure(i, "plugin_lock_wallpaper_type");
                    return;
                } else {
                    putSettingsSecure(i, "plugin_lock_wallpaper_type_sub");
                    return;
                }
            }
        }
        if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            putSettingsSecure(i, "plugin_lock_wallpaper_type");
        } else {
            putSettingsSecure(i, "plugin_lock_wallpaper_type");
            putSettingsSecure(i, "plugin_lock_wallpaper_type_sub");
        }
    }

    public final void setRecoverRequestedScreen(int i) {
        if (getScreenType() == i) {
            this.mRecoverRequestedScreen = -1;
        } else {
            SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("setRecoverRequestedScreen() screen: ", i, "PluginLockWallpaper");
            this.mRecoverRequestedScreen = i;
        }
    }

    public final void updateHint() {
        if (this.mWallpaperUpdateCallback == null || (!LsRune.WALLPAPER_SUB_WATCHFACE && !LsRune.WALLPAPER_VIRTUAL_DISPLAY && this.mHintUpdatedSkip && this.mHasData)) {
            StringBuilder sb = new StringBuilder("updateHint() mHintUpdatedSkip = ");
            sb.append(this.mHintUpdatedSkip);
            sb.append(", mHasData = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mHasData, "PluginLockWallpaper");
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("updateHint() onWallpaperHintUpdate will be called: ", getScreenType(), "PluginLockWallpaper");
            this.mWallpaperUpdateCallback.onWallpaperHintUpdate(((PluginLockWallpaperData) ((ArrayList) this.mWallpaperDataList).get(getScreenType())).mHints);
        }
        this.mHintUpdatedSkip = false;
        this.mHasData = true;
    }
}
