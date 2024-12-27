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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.feature.SemCscFeature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginLockWallpaper extends AbstractPluginLockItem {
    private static final String CUSTOM_PACK = "com.samsung.custompack";
    private static final String DLS_PACK = "SamsungUX.DW.FreshP";
    private static final String KEY_PLUGIN_LOCK_WALLPAPER_TYPE = "plugin_lock_wallpaper_type";
    private static final String KEY_PLUGIN_LOCK_WALLPAPER_TYPE_SUB = "plugin_lock_wallpaper_type_sub";
    public static final String KEY_WALLPAPER_SOURCE = "lockscreen_wallpaper_transparent";
    public static final String KEY_WALLPAPER_SOURCE_SUB = "sub_display_lockscreen_wallpaper_transparency";
    private static final String STUB_PACK = "SamsungUX.DW.Stub";
    private static final String TAG = "PluginLockWallpaper";
    private static final int VALUE_TYPE_INFINITY = 0;
    private static final int VALUE_TYPE_LOCK = 1;
    private static boolean sDualDisplayPlugin = false;
    private static int sScreenType = 0;
    private static boolean sScreenTypeChanged = false;
    private boolean mHasData;
    private boolean mHintUpdatedSkip;
    private Consumer<Boolean> mNoSensorConsumer;
    private int mRecoverRequestedScreen;
    private int mUpdateStyle;
    private final List<PluginLockWallpaperData> mWallpaperDataList;
    private int mWallpaperRecoverType;
    private PluginWallpaperCallback mWallpaperUpdateCallback;
    private boolean mWholeRecoverRequired;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class PluginLockWallpaperData {
        private Bitmap mBitmap;
        private SemWallpaperColors mHints;
        private String mIntelligentCrop;
        private String mPath;
        private int mResourceId;
        private int mType;
        private Uri mUri;

        public /* synthetic */ PluginLockWallpaperData(int i) {
            this();
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public SemWallpaperColors getHints() {
            return this.mHints;
        }

        public String getIntelligentCrop() {
            return this.mIntelligentCrop;
        }

        public String getPath() {
            return this.mPath;
        }

        public int getResourceId() {
            return this.mResourceId;
        }

        public int getType() {
            return this.mType;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public boolean hasData() {
            return (this.mType == -2 || (this.mBitmap == null && this.mPath == null && this.mUri == null)) ? false : true;
        }

        public void resetAll() {
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

        public void resetData() {
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

        public void resetType() {
            this.mType = -2;
        }

        public void setBitmap(Bitmap bitmap) {
            this.mPath = null;
            this.mBitmap = bitmap;
            this.mUri = null;
            if (bitmap == null) {
                this.mResourceId = 0;
            }
        }

        public void setHints(SemWallpaperColors semWallpaperColors) {
            this.mHints = semWallpaperColors;
        }

        public void setIntelligentCrop(String str) {
            this.mIntelligentCrop = str;
        }

        public void setPath(String str) {
            this.mPath = str;
            this.mBitmap = null;
            this.mUri = null;
        }

        public void setType(int i) {
            this.mType = i;
        }

        public void setUri(Uri uri) {
            this.mPath = null;
            this.mBitmap = null;
            this.mUri = uri;
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

        public void setBitmap(Bitmap bitmap, int i) {
            setBitmap(bitmap);
            this.mResourceId = i;
        }
    }

    public PluginLockWallpaper(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mRecoverRequestedScreen = -1;
        int i = 0;
        this.mHintUpdatedSkip = false;
        this.mWholeRecoverRequired = false;
        this.mHasData = false;
        this.mUpdateStyle = 0;
        this.mWallpaperRecoverType = 1;
        ArrayList arrayList = new ArrayList();
        this.mWallpaperDataList = arrayList;
        arrayList.add(0, new PluginLockWallpaperData(i));
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            arrayList.add(1, new PluginLockWallpaperData(i));
        }
    }

    private void backupWallpaperSource() {
        backupWallpaperSource(0);
        backupWallpaperSource(1);
    }

    private Bitmap getBitmap(Resources resources, int i) {
        Drawable drawable = resources.getDrawable(i, null);
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(resources, i);
        }
        if (!(drawable instanceof GradientDrawable)) {
            Log.w(TAG, "getBitmap() unsupported " + drawable);
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

    private String getMultiPackPkgName(String str) {
        if (str == null || str.isEmpty()) {
            return "no_matched_pkg_name";
        }
        Matcher matcher = Pattern.compile("MULTIPLE=(.*):tilt").matcher(str);
        return matcher.find() ? matcher.group(1) : "no_matched_pkg_name";
    }

    private int getScreenType() {
        int i = this.mRecoverRequestedScreen;
        if (i != -1) {
            return i;
        }
        int i2 = isSingleDisplayRequired() ? 0 : sScreenType;
        if (this.mWallpaperDataList.size() > i2) {
            return i2;
        }
        return 0;
    }

    private boolean isSingleDisplayRequired() {
        return !LsRune.LOCKUI_SUB_DISPLAY_LOCK || sScreenType == 0 || isCloneDisplayRequired();
    }

    private boolean isWholeRecoverRequired() {
        return LsRune.LOCKUI_SUB_DISPLAY_LOCK && this.mWholeRecoverRequired;
    }

    private void recoverWallpaperSource() {
        recoverWallpaperSource(0);
        recoverWallpaperSource(1);
    }

    public static void setDualDisplayPlugin(boolean z) {
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("setDualDisplayPlugin() : ", TAG, z);
        sDualDisplayPlugin = z;
    }

    private void setMultiPackWallpaperSource() {
        setMultiPackWallpaperSource(0);
        setMultiPackWallpaperSource(1);
    }

    private void setPluginWallpaperType(int i) {
        if (sDualDisplayPlugin && !isWholeRecoverRequired()) {
            if (isSingleDisplayRequired()) {
                putSettingsSecure("plugin_lock_wallpaper_type", i);
                return;
            } else {
                putSettingsSecure("plugin_lock_wallpaper_type_sub", i);
                return;
            }
        }
        if (!LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            putSettingsSecure("plugin_lock_wallpaper_type", i);
        } else {
            putSettingsSecure("plugin_lock_wallpaper_type", i);
            putSettingsSecure("plugin_lock_wallpaper_type_sub", i);
        }
    }

    public static void setScreenTypeChanged(int i) {
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "setScreenTypeChanged() type: ", TAG);
        boolean z = LsRune.LOCKUI_SUB_DISPLAY_LOCK;
        if (!z) {
            i = 0;
        }
        sScreenType = i;
        sScreenTypeChanged = z;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "apply()");
        if (dynamicLockData2 != null && (dynamicLockData == null || !dynamicLockData2.getWallpaperData().equals(dynamicLockData.getWallpaperData()))) {
            this.mUpdateStyle = dynamicLockData2.getWallpaperData().getUpdateStyle().intValue();
            this.mWallpaperRecoverType = dynamicLockData2.getWallpaperData().getRecoverType().intValue();
        }
        if (isCloneDisplayRequired()) {
            Iterator<PluginLockWallpaperData> it = this.mWallpaperDataList.iterator();
            while (it.hasNext()) {
                it.next().resetAll();
            }
        }
    }

    public void fillData(Context context, int i, int i2, int i3, String str) {
        if (i == 1 && isCloneDisplayRequired()) {
            return;
        }
        PluginLockWallpaperData pluginLockWallpaperData = this.mWallpaperDataList.get(i);
        boolean hasData = pluginLockWallpaperData.hasData();
        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "fillData() screen:", ", wallpaperType:", ", sourceType:");
        m.append(i3);
        m.append(",source:");
        m.append(str);
        m.append(", hasData:");
        ActionBarContextView$$ExternalSyntheticOutline0.m(m, hasData, TAG);
        if (hasData) {
            return;
        }
        pluginLockWallpaperData.setType(i2);
        if (i3 == 0) {
            pluginLockWallpaperData.setPath(str);
            pluginLockWallpaperData.setBitmap(null);
            return;
        }
        if (i3 != 1) {
            pluginLockWallpaperData.resetAll();
            return;
        }
        try {
            pluginLockWallpaperData.setPath(null);
            int parseInt = Integer.parseInt(str);
            if (pluginLockWallpaperData.getResourceId() == parseInt && pluginLockWallpaperData.getBitmap() != null) {
                return;
            }
            pluginLockWallpaperData.setBitmap(getBitmap(context.getResources(), parseInt), parseInt);
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("couldn't load bitmap:"), TAG);
        }
    }

    public PluginWallpaperCallback getCallback() {
        return this.mWallpaperUpdateCallback;
    }

    public int getUpdateStyle() {
        return this.mUpdateStyle;
    }

    public Bitmap getWallpaperBitmap() {
        return getWallpaperBitmap(getScreenType());
    }

    public String getWallpaperIntelligentCrop() {
        return this.mWallpaperDataList.get(getScreenType()).getIntelligentCrop();
    }

    public String getWallpaperPath() {
        return getWallpaperPath(getScreenType());
    }

    public int getWallpaperType() {
        return getWallpaperType(getScreenType());
    }

    public Uri getWallpaperUri() {
        return getWallpaperUri(getScreenType());
    }

    public boolean isCloneDisplayRequired() {
        return !sDualDisplayPlugin && LsRune.LOCKUI_SUB_DISPLAY_LOCK;
    }

    public boolean isCustomPack() {
        return isCustomPack(getScreenType());
    }

    public boolean isDynamicWallpaper() {
        int screenType = getScreenType();
        boolean z = this.mWallpaperDataList.get(screenType).getType() != -2;
        KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("isDynamicWallpaper() screen:", screenType, ", ret:", z, TAG);
        return z;
    }

    public boolean isMultiPack() {
        return (isCustomPack() || isServiceWallpaper()) ? false : true;
    }

    public boolean isPreloadedMultiPack() {
        String wallpaperPath = getWallpaperPath();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this.mContext);
        String defaultMultipackStyle = wallpaperManager.getDefaultMultipackStyle(2);
        String defaultMultipackStyle2 = wallpaperManager.getDefaultMultipackStyle(18);
        if (wallpaperPath != null) {
            String multiPackPkgName = getMultiPackPkgName(defaultMultipackStyle);
            String multiPackPkgName2 = getMultiPackPkgName(defaultMultipackStyle2);
            r3 = wallpaperPath.contains(multiPackPkgName) || wallpaperPath.contains(multiPackPkgName2);
            ActionBarContextView$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("isPreloadedMultiPack, main:", multiPackPkgName, ", sub:", multiPackPkgName2, ", isPreload"), r3, TAG);
        }
        return r3;
    }

    public boolean isRecoverRequiredWallpaper() {
        int settingsInt = getSettingsInt(sScreenType == 0 ? "lockscreen_wallpaper_transparent" : "sub_display_lockscreen_wallpaper_transparency", 1);
        RecyclerView$$ExternalSyntheticOutline0.m(sScreenType, TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(settingsInt, "isRecoverRequiredWallpaper() type: ", ", screenType:"));
        return settingsInt == 2;
    }

    public boolean isServiceWallpaper() {
        return isServiceWallpaper(getScreenType());
    }

    public boolean isSpecialEditionMultiPack() {
        String wallpaperPath = getWallpaperPath();
        String string = SemCscFeature.getInstance().getString("CscFeature_LockScreen_ConfigDefaultWallpaperStyle", "");
        String nextToken = (string == null || string.isEmpty()) ? null : new StringTokenizer(string, ";").nextToken();
        boolean z = (wallpaperPath == null || nextToken == null || !wallpaperPath.contains(nextToken)) ? false : true;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isSpecialEditionMultiPack, ret:", TAG, z);
        return z;
    }

    public boolean isStickyRecoverType() {
        return this.mWallpaperRecoverType == 2;
    }

    public boolean isStubPack() {
        String wallpaperPath = getWallpaperPath();
        boolean z = wallpaperPath != null && wallpaperPath.contains(STUB_PACK);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isStubPack, ret:", TAG, z);
        return z;
    }

    public boolean isVideoWallpaper() {
        return isDynamicWallpaper() && this.mWallpaperDataList.get(getScreenType()).getType() == 2;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
        RecyclerView$$ExternalSyntheticOutline0.m(sScreenType, TAG, new StringBuilder("recover() screenType:"));
        if (isWholeRecoverRequired()) {
            Iterator<PluginLockWallpaperData> it = this.mWallpaperDataList.iterator();
            while (it.hasNext()) {
                it.next().resetAll();
            }
        } else {
            this.mWallpaperDataList.get(getScreenType()).setType(-2);
        }
        if (isCloneDisplayRequired() || isWholeRecoverRequired()) {
            setWallpaperBackupValue(-2, -1, -1);
        } else {
            setWallpaperBackupValue(sScreenType, -2, -1, -1);
        }
        reset(false);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        RecyclerView$$ExternalSyntheticOutline0.m(sScreenType, TAG, RowView$$ExternalSyntheticOutline0.m("reset() reconnectReq:", ", screenType:", z));
        PluginLockWallpaperData pluginLockWallpaperData = this.mWallpaperDataList.get(getScreenType());
        pluginLockWallpaperData.resetData();
        if (pluginLockWallpaperData.getType() != -2) {
            if (isCloneDisplayRequired()) {
                recoverWallpaperSource();
            } else {
                recoverWallpaperSource(sScreenType);
            }
            pluginLockWallpaperData.resetType();
            if (isCloneDisplayRequired()) {
                setWallpaperBackupValue(-2, -1, -1);
            } else {
                setWallpaperBackupValue(sScreenType, -2, -1, -1);
            }
        }
        setPluginWallpaperType(0);
        sScreenTypeChanged = false;
        this.mHintUpdatedSkip = false;
        this.mWholeRecoverRequired = false;
        this.mRecoverRequestedScreen = -1;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void resetAll() {
        Log.d(TAG, "resetAll()");
        Iterator<PluginLockWallpaperData> it = this.mWallpaperDataList.iterator();
        while (it.hasNext()) {
            it.next().resetAll();
        }
        recoverWallpaperSource();
        setWallpaperBackupValue(-2, -1, -1);
        this.mWholeRecoverRequired = true;
        setPluginWallpaperType(0);
        sScreenTypeChanged = false;
        this.mHintUpdatedSkip = false;
        this.mWholeRecoverRequired = false;
        this.mRecoverRequestedScreen = -1;
        this.mHasData = false;
    }

    public void resetWallpaperData(int i) {
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "resetWallpaperData: screen = ", ", isCloneDisplayRequired = ");
        m.append(isCloneDisplayRequired());
        Log.d(TAG, m.toString());
        if (isCloneDisplayRequired()) {
            Iterator<PluginLockWallpaperData> it = this.mWallpaperDataList.iterator();
            while (it.hasNext()) {
                it.next().resetAll();
            }
        } else {
            PluginLockWallpaperData pluginLockWallpaperData = this.mWallpaperDataList.get(i);
            if (pluginLockWallpaperData != null) {
                pluginLockWallpaperData.resetAll();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(pluginLockInstanceState);
    }

    public void setNoSensorConsumer(Consumer<Boolean> consumer) {
        Log.d(TAG, "setNoSensorConsumer() consumer:" + consumer);
        this.mNoSensorConsumer = consumer;
    }

    public void setRecoverRequestedScreen(int i) {
        if (getScreenType() == i) {
            this.mRecoverRequestedScreen = -1;
        } else {
            KeyguardSecPatternView$$ExternalSyntheticOutline0.m(i, "setRecoverRequestedScreen() screen: ", TAG);
            this.mRecoverRequestedScreen = i;
        }
    }

    public void setWallpaperHints(SemWallpaperColors semWallpaperColors) {
        this.mWallpaperDataList.get(getScreenType()).setHints(semWallpaperColors);
    }

    public void setWallpaperUpdateCallback(PluginWallpaperCallback pluginWallpaperCallback) {
        this.mWallpaperUpdateCallback = pluginWallpaperCallback;
    }

    public void setWholeRecoverRequired(boolean z) {
        this.mWholeRecoverRequired = z;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "update()");
        if (dynamicLockData2 != null) {
            if (dynamicLockData == null || !dynamicLockData2.getWallpaperData().equals(dynamicLockData.getWallpaperData())) {
                this.mUpdateStyle = dynamicLockData2.getWallpaperData().getUpdateStyle().intValue();
                this.mWallpaperRecoverType = dynamicLockData2.getWallpaperData().getRecoverType().intValue();
            }
        }
    }

    public void updateHint() {
        if (this.mWallpaperUpdateCallback == null || (!LsRune.WALLPAPER_SUB_WATCHFACE && !LsRune.WALLPAPER_VIRTUAL_DISPLAY && this.mHintUpdatedSkip && this.mHasData)) {
            StringBuilder sb = new StringBuilder("updateHint() mHintUpdatedSkip = ");
            sb.append(this.mHintUpdatedSkip);
            sb.append(", mHasData = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mHasData, TAG);
        } else {
            ListPopupWindow$$ExternalSyntheticOutline0.m(getScreenType(), "updateHint() onWallpaperHintUpdate will be called: ", TAG);
            this.mWallpaperUpdateCallback.onWallpaperHintUpdate(this.mWallpaperDataList.get(getScreenType()).getHints());
        }
        this.mHintUpdatedSkip = false;
        this.mHasData = true;
    }

    public Bitmap getWallpaperBitmap(int i) {
        return this.mWallpaperDataList.get(i).getBitmap();
    }

    public String getWallpaperPath(int i) {
        String path = this.mWallpaperDataList.get(i).getPath();
        if (path == null) {
            Log.d(TAG, "getWallpaperPath() path: null");
            return null;
        }
        try {
            Log.d(TAG, "getWallpaperPath() path: " + path.substring((path.length() * 20) / 100));
        } catch (Throwable unused) {
        }
        return path;
    }

    public int getWallpaperType(int i) {
        return this.mWallpaperDataList.get(i).getType() != 2 ? 0 : 8;
    }

    public Uri getWallpaperUri(int i) {
        return this.mWallpaperDataList.get(i).getUri();
    }

    public boolean isCustomPack(int i) {
        String wallpaperPath = getWallpaperPath(i);
        boolean z = wallpaperPath != null && wallpaperPath.contains(CUSTOM_PACK);
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isCustomPack, ret:", TAG, z);
        return z;
    }

    public boolean isMultiPack(int i) {
        return (isCustomPack(i) || isServiceWallpaper(i)) ? false : true;
    }

    public boolean isServiceWallpaper(int i) {
        String wallpaperPath = getWallpaperPath(i);
        return ((wallpaperPath == null || !wallpaperPath.contains(DLS_PACK)) && getWallpaperBitmap(i) == null && getWallpaperUri(i) == null) ? false : true;
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(i, pluginLockInstanceState);
        Log.d(TAG, "setInstanceState, screen: " + i + ", instanceState: " + pluginLockInstanceState);
        if (pluginLockInstanceState == null) {
            if (((LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) && i == 1) || !isDynamicWallpaper()) {
                return;
            }
            reset(false);
        }
    }

    private void backupWallpaperSource(int i) {
        String str = i == 0 ? "lockscreen_wallpaper_transparent" : "sub_display_lockscreen_wallpaper_transparency";
        int settingsInt = getSettingsInt(str, -1);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(settingsInt, i, "backupWallpaperSource() backupSource: ", ", screenType:", TAG);
        if (settingsInt != 1) {
            setWallpaperSourceBackupValue(i, settingsInt);
            putSettingsSystem(str, 1);
        }
    }

    private void recoverWallpaperSource(int i) {
        int wallpaperSourceBackupValue = getWallpaperSourceBackupValue(i);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(wallpaperSourceBackupValue, i, "recoverWallpaperSource() backupWallpaperSource: ", ", screenType:", TAG);
        if (wallpaperSourceBackupValue == -1 || wallpaperSourceBackupValue == -2 || wallpaperSourceBackupValue == 1) {
            return;
        }
        putSettingsSystem(i == 0 ? "lockscreen_wallpaper_transparent" : "sub_display_lockscreen_wallpaper_transparency", wallpaperSourceBackupValue);
    }

    private void setMultiPackWallpaperSource(int i) {
        String str = i == 0 ? "lockscreen_wallpaper_transparent" : "sub_display_lockscreen_wallpaper_transparency";
        int settingsInt = getSettingsInt(str, -1);
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(settingsInt, i, "setMultiPackWallpaperSource() currentType: ", ", screenType:", TAG);
        setWallpaperTypeBackupValue(i, -1);
        setWallpaperSourceBackupValue(i, -1);
        if (!isPreloadedMultiPack() && !isSpecialEditionMultiPack() && !isStubPack()) {
            if (isCustomPack()) {
                Log.d(TAG, "setMultiPackWallpaperSource, custom");
                if (settingsInt != 0) {
                    putSettingsSystem(str, 0);
                    return;
                }
                return;
            }
            Log.d(TAG, "setMultiPackWallpaperSource, theme");
            if (settingsInt != 3) {
                putSettingsSystem(str, 3);
                return;
            }
            return;
        }
        Log.d(TAG, "setMultiPackWallpaperSource, preload");
        if (settingsInt != 1) {
            putSettingsSystem(str, 1);
        }
    }

    public String getWallpaperIntelligentCrop(int i) {
        if (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY || isCloneDisplayRequired()) {
            i = 0;
        }
        return this.mWallpaperDataList.get(i).getIntelligentCrop();
    }

    public boolean isVideoWallpaper(int i) {
        return isDynamicWallpaper() && this.mWallpaperDataList.get(i).getType() == 2;
    }

    public void update(Context context, int i, int i2, String str) {
        update(context, i, i2, str, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:91:0x008f, code lost:
    
        if (r2.getBitmap() == null) goto L107;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00cd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void update(android.content.Context r6, int r7, int r8, java.lang.String r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 428
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.component.PluginLockWallpaper.update(android.content.Context, int, int, java.lang.String, java.lang.String):void");
    }

    public boolean isDynamicWallpaper(int i) {
        int i2 = (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY || isCloneDisplayRequired()) ? 0 : i;
        boolean z = this.mWallpaperDataList.get(i2).getType() != -2;
        ActionBarContextView$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "isDynamicWallpaper() required:", ", final: ", ", ret:"), z, TAG);
        return z;
    }
}
