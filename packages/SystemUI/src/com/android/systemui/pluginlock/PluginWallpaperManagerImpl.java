package com.android.systemui.pluginlock;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.component.PluginWallpaperCallback;
import com.android.systemui.pluginlock.listener.KeyguardListener;
import com.android.systemui.pluginlock.utils.BitmapUtils;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.sdk.cover.ScoverManager;
import com.sec.ims.presence.ServiceTuple;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PluginWallpaperManagerImpl implements PluginWallpaperManager, KeyguardListener.UserSwitch {
    private static final String FBE_PATH = "/data/user_de/0/com.android.systemui/files/fresh_pack/";
    private static final String FBE_SUB_PATH = "/data/user_de/0/com.android.systemui/files/fresh_pack_sub/";
    private static final String TAG = "PluginWallpaperManagerImpl";
    private final Context mContext;
    private final PluginLockDelegateApp mDelegateApp;
    private boolean mIsSwitchingToSub = false;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private final PluginLockMediator mMediator;
    private int mScreenType;
    private final SettingsHelper mSettingsHelper;
    private final PluginLockUtils mUtils;

    public PluginWallpaperManagerImpl(PluginLockMediator pluginLockMediator, PluginLockDelegateApp pluginLockDelegateApp, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mScreenType = 0;
        this.mDelegateApp = pluginLockDelegateApp;
        this.mUtils = pluginLockUtils;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mMediator = pluginLockMediator;
        pluginLockMediator.setKeyguardUserSwitchListener(this);
        pluginLockUtils.addDump(TAG, "## PluginWallpaperManagerImpl ##, " + this);
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.WALLPAPER_SUB_WATCHFACE) {
            try {
                if (WallpaperManager.getInstance(context).getLidState() == 0) {
                    Log.d(TAG, "PluginLockWallpaperManager: mScreenType = PluginLock.SCREEN_SUB");
                    this.mScreenType = 1;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            ScoverManager scoverManager = new ScoverManager(context);
            if (scoverManager.getCoverState() != null && !scoverManager.getCoverState().switchState) {
                Log.d(TAG, "PluginLockWallpaperManager, virtual display: mScreenType = PluginLock.SCREEN_SUB");
                this.mScreenType = 1;
            }
        }
        if (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            fillFbeWallpaperData();
        }
    }

    private void fillFbeWallpaperData() {
        PluginHomeWallpaper pluginHomeWallpaper;
        boolean isFbeWallpaperAvailable = isFbeWallpaperAvailable(1);
        int fbeWallpaperType = getFbeWallpaperType(1);
        String fbeWallpaperPath = getFbeWallpaperPath(1);
        this.mUtils.addDump(TAG, BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(fbeWallpaperType, "fillFbeWallpaperData, fbeSubType: ", ", fbeSubPath: ", fbeWallpaperPath));
        if (!isFbeWallpaperAvailable || fbeWallpaperType == -2 || fbeWallpaperPath == null || (pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper()) == null) {
            return;
        }
        pluginHomeWallpaper.setWallpaper(1, fbeWallpaperType, 0, fbeWallpaperPath);
        pluginHomeWallpaper.setWallpaperHints(getFbeSemWallpaperColors(1));
    }

    private File getFbeColorFile(int i) {
        return getFbeFile(i, "color");
    }

    private File getFbeFile(int i, String str) {
        File[] listFiles;
        File file = new File(i == 0 ? FBE_PATH : FBE_SUB_PATH);
        if (!file.exists() || (listFiles = file.listFiles()) == null || listFiles.length == 0) {
            return null;
        }
        for (File file2 : listFiles) {
            if (file2 != null && file2.getName().startsWith(str)) {
                return file2;
            }
        }
        return null;
    }

    private File getFbeIcropFile(int i) {
        return getFbeFile(i, "icrops");
    }

    private File getFbeRectFile(int i) {
        return getFbeFile(i, "rect");
    }

    private File getFbeWallpaperFile(int i) {
        return getFbeFile(i, "fbe");
    }

    private boolean isDynamicLockEnabled() {
        return this.mMediator.isDynamicLockEnabled();
    }

    private boolean isNumeric(String str) {
        return str.matches("^(0|[1-9][0-9]*)$");
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void fillWallpaperData(int i, int i2, int i3, String str) {
        if (this.mMediator != null) {
            LogUtil.d(TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "fillWallpaperData screen:"), new Object[0]);
            PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
            if (pluginLockWallpaper != null) {
                pluginLockWallpaper.fillData(this.mContext, i, i2, i3, str);
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromPath(String str) {
        return BitmapUtils.getBitmapFromPath(this.mContext, str, true, false);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromUri(Uri uri) {
        return BitmapUtils.getBitmapFromUri(this.mContext, uri, true, false);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public SemWallpaperColors getFbeSemWallpaperColors() {
        return getFbeSemWallpaperColors(this.mScreenType);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getFbeWallpaper(int i) {
        File fbeWallpaperFile = getFbeWallpaperFile(i);
        if (fbeWallpaperFile != null) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getFbeWallpaper screen: ", ", path: ");
            m.append(fbeWallpaperFile.getPath());
            Log.d(TAG, m.toString());
            if (fbeWallpaperFile.exists() && fbeWallpaperFile.canRead()) {
                return getBitmapFromPath(fbeWallpaperFile.getPath());
            }
        }
        Log.d(TAG, "getFbeWallpaper null");
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperIntelligentCrop(int i) {
        FileInputStream fileInputStream;
        File fbeIcropFile = getFbeIcropFile(i);
        String str = null;
        try {
            fileInputStream = new FileInputStream(fbeIcropFile);
        } catch (Exception e) {
            Log.d(TAG, "getFbeWallpaperIntelligentCrop: " + e.getMessage());
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            try {
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    str = bufferedReader.readLine();
                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getFbeWallpaperIntelligentCrop: iCrops = ", str, TAG);
                    return str;
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperPath() {
        return getFbeWallpaperPath(this.mScreenType);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Rect getFbeWallpaperRect() {
        getFbeWallpaperRect(this.mScreenType);
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getFbeWallpaperType(int i) {
        File fbeWallpaperFile;
        String str;
        if (!this.mUtils.isGoingToRescueParty() && (fbeWallpaperFile = getFbeWallpaperFile(i)) != null && fbeWallpaperFile.exists() && (str = fbeWallpaperFile.getName().split("_")[1]) != null) {
            switch (str) {
                case "gif":
                    return 22;
                case "image":
                    return 1;
                case "video":
                    return 2;
            }
        }
        return -2;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getHomeCurrentScreen() {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            return pluginHomeWallpaper.getCurrentScreen();
        }
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getHomeWallpaperBitmap(int i) {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            return pluginHomeWallpaper.getWallpaperBitmap(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getHomeWallpaperIntelligentCrop(int i) {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            return pluginHomeWallpaper.getIntelligentCrops(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getHomeWallpaperPath(int i) {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            return pluginHomeWallpaper.getWallpaperPath(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Rect getHomeWallpaperRect(int i) {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            return pluginHomeWallpaper.getWallpaperRect(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getHomeWallpaperType(int i) {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            return pluginHomeWallpaper.getWallpaperType(i);
        }
        return -2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0047, code lost:
    
        if (r4.equals("image") == false) goto L15;
     */
    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getSubFbeWallpaperType() {
        /*
            r4 = this;
            r0 = 1
            com.android.systemui.pluginlock.PluginLockUtils r1 = r4.mUtils
            boolean r1 = r1.isGoingToRescueParty()
            r2 = -2
            if (r1 == 0) goto Lb
            return r2
        Lb:
            java.io.File r4 = r4.getFbeWallpaperFile(r0)
            if (r4 == 0) goto L61
            boolean r1 = r4.exists()
            if (r1 == 0) goto L61
            boolean r1 = r4.canRead()
            if (r1 == 0) goto L61
            java.lang.String r4 = r4.getName()
            java.lang.String r1 = "_"
            java.lang.String[] r4 = r4.split(r1)
            r4 = r4[r0]
            if (r4 == 0) goto L61
            r1 = -1
            int r3 = r4.hashCode()
            switch(r3) {
                case 102340: goto L4a;
                case 100313435: goto L41;
                case 112202875: goto L35;
                default: goto L33;
            }
        L33:
            r0 = r1
            goto L54
        L35:
            java.lang.String r0 = "video"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L3f
            goto L33
        L3f:
            r0 = 2
            goto L54
        L41:
            java.lang.String r3 = "image"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L54
            goto L33
        L4a:
            java.lang.String r0 = "gif"
            boolean r4 = r4.equals(r0)
            if (r4 != 0) goto L53
            goto L33
        L53:
            r0 = 0
        L54:
            switch(r0) {
                case 0: goto L5e;
                case 1: goto L5b;
                case 2: goto L58;
                default: goto L57;
            }
        L57:
            goto L61
        L58:
            r4 = 23
            return r4
        L5b:
            r4 = 21
            return r4
        L5e:
            r4 = 22
            return r4
        L61:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.pluginlock.PluginWallpaperManagerImpl.getSubFbeWallpaperType():int");
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getWallpaperBitmap() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperBitmap();
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperIndex(int i, Bundle bundle) {
        String str;
        int i2;
        if (!LsRune.SUBSCREEN_WATCHFACE || i != 1) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getWallpaperIndex: Not supported yet! screen = ", TAG);
            return -1;
        }
        if (bundle != null) {
            str = bundle.getString("caller");
            i2 = bundle.getInt("multi_pack_size");
        } else {
            str = null;
            i2 = -1;
        }
        StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "getWallpaperIndex: screen = ", ", source = ", str, ", size = ");
        m.append(i2);
        Log.d(TAG, m.toString());
        String homeWallpaperPath = getHomeWallpaperPath(i);
        if (homeWallpaperPath == null) {
            return -1;
        }
        try {
            String substring = homeWallpaperPath.substring(homeWallpaperPath.lastIndexOf("/") + 1);
            int indexOf = substring.indexOf(".");
            if (indexOf > 0) {
                substring = substring.substring(0, indexOf);
            }
            String replaceAll = substring.replaceAll("[^0-9]", "");
            if (isNumeric(replaceAll)) {
                int parseInt = Integer.parseInt(replaceAll);
                if ("Cover".equals(str) && parseInt - 1 == -1) {
                    parseInt = i2 - 1;
                }
                Log.d(TAG, "getWallpaperIndex: strIndex = " + replaceAll + ", index = " + parseInt);
                return parseInt;
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getWallpaperIndex: ", e, TAG);
        }
        return -1;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperIntelligentCrop() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperIntelligentCrop();
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperPath() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperPath();
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperType() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperType();
        }
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperUpdateStyle() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getUpdateStyle();
        }
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Uri getWallpaperUri() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperUri();
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean hasBackupWallpaper(int i) {
        PluginLockDelegateApp pluginLockDelegateApp = this.mDelegateApp;
        if (pluginLockDelegateApp != null) {
            return pluginLockDelegateApp.hasBackupWallpaper(i);
        }
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isCloneDisplayRequired() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.isCloneDisplayRequired();
        }
        return false;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isCustomPackApplied() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isCustomPack();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isDynamicWallpaperEnabled() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isDynamicWallpaper();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeAvailable() {
        return isFbeRequired() && isFbeWallpaperAvailable();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeRequired() {
        return isFbeRequired(this.mScreenType);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperAvailable() {
        return isFbeWallpaperAvailable(this.mScreenType);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperVideo() {
        return isFbeWallpaperVideo(this.mScreenType);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isHomeWallpaperRequired(int i) {
        return this.mMediator.isHomeWallpaperRequired(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isMultiPackApplied() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isMultiPack();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isServiceWallpaperApplied() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isServiceWallpaper();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isVideoWallpaperEnabled() {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isVideoWallpaper();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcBitmap() {
        return getWallpaperBitmap() != null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcPath() {
        return getWallpaperPath() != null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcUri() {
        return getWallpaperUri() != null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onHomeWallpaperChanged(int i) {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            int key = pluginHomeWallpaper.getKey(i);
            pluginHomeWallpaper.resetWallpaper(key);
            this.mMediator.onWallpaperChanged(key);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onLockWallpaperChanged(int i, boolean z) {
        if (z) {
            onLockWallpaperChanged(i);
            return;
        }
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            pluginLockWallpaper.resetWallpaperData(i);
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.UserSwitch
    public void onUserSwitchComplete(int i) {
        this.mUtils.addDump(TAG, "onUserSwitchComplete, userId: " + i);
        this.mIsSwitchingToSub = false;
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener.UserSwitch
    public void onUserSwitching(int i) {
        this.mUtils.addDump(TAG, "onUserSwitching, userId: " + i);
        this.mIsSwitchingToSub = true;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onWallpaperConsumed(int i, boolean z) {
        boolean isDynamicLockEnabled = this.mMediator.isDynamicLockEnabled();
        boolean isCurrentOwner = this.mUtils.isCurrentOwner();
        PluginLockUtils pluginLockUtils = this.mUtils;
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("onWallpaperConsumed, enabled:", ", mIsSwitchingToSub: ", isDynamicLockEnabled);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, this.mIsSwitchingToSub, ", isOwner: ", isCurrentOwner, ", screen:");
        m.append(i);
        m.append(", updateColor:");
        m.append(z);
        pluginLockUtils.addDump(TAG, m.toString());
        if (this.mMediator.getPluginLockWallpaper() == null || !isDynamicLockEnabled || this.mIsSwitchingToSub || !isCurrentOwner || this.mDelegateApp == null) {
            return;
        }
        try {
            this.mUtils.addDump(TAG, "onWallpaperConsumed called");
            this.mDelegateApp.onWallpaperConsumed(i, z);
        } catch (Throwable th) {
            this.mUtils.addDump(TAG, "onWallpaperConsumed, " + th.toString());
            th.printStackTrace();
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void setHomeWallpaperCallback(PluginWallpaperCallback pluginWallpaperCallback) {
        PluginHomeWallpaper pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper();
        if (pluginHomeWallpaper != null) {
            pluginHomeWallpaper.setWallpaperUpdateCallback(pluginWallpaperCallback);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void setLockWallpaperCallback(PluginWallpaperCallback pluginWallpaperCallback) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            pluginLockWallpaper.setWallpaperUpdateCallback(pluginWallpaperCallback);
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromPath(String str, boolean z) {
        return BitmapUtils.getBitmapFromPath(this.mContext, str, !z, false);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getBitmapFromUri(Uri uri, boolean z) {
        return BitmapUtils.getBitmapFromUri(this.mContext, uri, !z, false);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public SemWallpaperColors getFbeSemWallpaperColors(int i) {
        FileInputStream fileInputStream;
        File fbeColorFile = getFbeColorFile(i);
        if (fbeColorFile != null && fbeColorFile.exists() && fbeColorFile.canRead()) {
            StringBuilder sb = new StringBuilder();
            try {
                fileInputStream = new FileInputStream(fbeColorFile.getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
                try {
                    for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                        sb.append(readLine);
                    }
                    bufferedReader.close();
                    fileInputStream.close();
                    try {
                        String sb2 = sb.toString();
                        if (!sb2.isEmpty()) {
                            return SemWallpaperColors.fromXml(sb2);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } finally {
                }
            } finally {
            }
        }
        return SemWallpaperColors.getBlankWallpaperColors();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperPath(int i) {
        File fbeWallpaperFile = getFbeWallpaperFile(i);
        return fbeWallpaperFile != null ? fbeWallpaperFile.getAbsolutePath() : "";
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Rect getFbeWallpaperRect(int i) {
        FileInputStream fileInputStream;
        File fbeRectFile = getFbeRectFile(i);
        Rect rect = null;
        try {
            fileInputStream = new FileInputStream(fbeRectFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            try {
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    rect = Rect.unflattenFromString(bufferedReader.readLine());
                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();
                    Log.d(TAG, "getFbeWallpaperRect, rect: " + rect);
                    return rect;
                } finally {
                }
            } finally {
            }
        } finally {
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeAvailable(int i) {
        return isFbeRequired(i) && isFbeWallpaperAvailable(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeRequired(int i) {
        return !this.mKeyguardUpdateMonitor.isUserUnlocked() && (this.mSettingsHelper.getPluginLockValue(i) % 10 != 0 || this.mSettingsHelper.getPluginLockValue(i) == 30000);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperAvailable(int i) {
        File fbeWallpaperFile = getFbeWallpaperFile(i);
        boolean z = fbeWallpaperFile != null && fbeWallpaperFile.exists() && fbeWallpaperFile.canRead();
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("isFbeWallpaperAvailable: screen = ", i, ", flag = ", z, ", file = ");
        m.append(fbeWallpaperFile != null ? fbeWallpaperFile.getAbsolutePath() : "null");
        Log.i(TAG, m.toString());
        return z && !this.mUtils.isGoingToRescueParty();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperVideo(int i) {
        String str;
        File fbeWallpaperFile = getFbeWallpaperFile(i);
        return fbeWallpaperFile != null && fbeWallpaperFile.exists() && fbeWallpaperFile.canRead() && (str = fbeWallpaperFile.getName().split("_")[1]) != null && str.equals(ServiceTuple.MEDIA_CAP_VIDEO) && !this.mUtils.isGoingToRescueParty();
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcBitmap(int i) {
        return getWallpaperBitmap(i) != null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcPath(int i) {
        return getWallpaperPath(i) != null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isWallpaperSrcUri(int i) {
        return getWallpaperUri(i) != null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getWallpaperBitmap(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperBitmap(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperIntelligentCrop(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperIntelligentCrop(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getWallpaperPath(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperPath(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperType(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperType(i);
        }
        return 0;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Uri getWallpaperUri(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperUri(i);
        }
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isCustomPackApplied(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isCustomPack(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isDynamicWallpaperEnabled(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isDynamicWallpaper(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isMultiPackApplied(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isMultiPack(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isServiceWallpaperApplied(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isServiceWallpaper(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isVideoWallpaperEnabled(int i) {
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isVideoWallpaper(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onLockWallpaperChanged(int i) {
        boolean isDynamicLockEnabled = this.mMediator.isDynamicLockEnabled();
        boolean isCurrentOwner = this.mUtils.isCurrentOwner();
        PluginLockUtils pluginLockUtils = this.mUtils;
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("notifyWallpaperChanged, enabled:", ", mIsSwitchingToSub: ", isDynamicLockEnabled);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, this.mIsSwitchingToSub, ", isOwner: ", isCurrentOwner, ", screen:");
        m.append(i);
        pluginLockUtils.addDump(TAG, m.toString());
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper == null || !isDynamicLockEnabled || this.mIsSwitchingToSub || !isCurrentOwner) {
            return;
        }
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK) {
            if (i == 2) {
                pluginLockWallpaper.setWholeRecoverRequired(true);
                pluginLockWallpaper.setRecoverRequestedScreen(-1);
            } else {
                pluginLockWallpaper.setWholeRecoverRequired(false);
                pluginLockWallpaper.setRecoverRequestedScreen(i);
            }
        }
        pluginLockWallpaper.recover();
        if (this.mDelegateApp != null) {
            this.mMediator.recoverItem(1);
            try {
                this.mUtils.addDump(TAG, "onLockWallpaperChanged called");
                this.mDelegateApp.onWallpaperChanged(i);
            } catch (Throwable th) {
                this.mUtils.addDump(TAG, "onLockWallpaperChanged, " + th.toString());
                th.printStackTrace();
            }
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Bitmap getFbeWallpaper(int i, boolean z) {
        File fbeWallpaperFile = getFbeWallpaperFile(i);
        if (fbeWallpaperFile != null) {
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getFbeWallpaper screen: ", ", path: ");
            m.append(fbeWallpaperFile.getPath());
            Log.d(TAG, m.toString());
            if (fbeWallpaperFile.exists() && fbeWallpaperFile.canRead()) {
                return getBitmapFromPath(fbeWallpaperFile.getPath(), z);
            }
        }
        Log.d(TAG, "getFbeWallpaper null");
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public void onColorAreasChanged(int i) {
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public int getWallpaperIndex() {
        String wallpaperPath;
        if ((!isCustomPackApplied() && !isMultiPackApplied()) || (wallpaperPath = getWallpaperPath()) == null) {
            return -1;
        }
        try {
            String substring = wallpaperPath.substring(wallpaperPath.lastIndexOf("/") + 1);
            int indexOf = substring.indexOf(".");
            if (indexOf > 0) {
                substring = substring.substring(0, indexOf);
            }
            String replaceAll = substring.replaceAll("[^0-9]", "");
            if (isNumeric(replaceAll)) {
                int parseInt = Integer.parseInt(replaceAll);
                if (isMultiPackApplied()) {
                    parseInt--;
                }
                Log.d(TAG, "getWallpaperIndex: strIndex = " + replaceAll + ", index = " + parseInt);
                return parseInt;
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getWallpaperIndex, ", e, TAG);
        }
        return -1;
    }
}
