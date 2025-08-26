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
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
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
        boolean zIsFbeWallpaperAvailable = isFbeWallpaperAvailable(1);
        int fbeWallpaperType = getFbeWallpaperType(1);
        String fbeWallpaperPath = getFbeWallpaperPath(1);
        this.mUtils.addDump(TAG, BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(fbeWallpaperType, "fillFbeWallpaperData, fbeSubType: ", ", fbeSubPath: ", fbeWallpaperPath));
        if (!zIsFbeWallpaperAvailable || fbeWallpaperType == -2 || fbeWallpaperPath == null || (pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper()) == null) {
            return;
        }
        pluginHomeWallpaper.setWallpaper(1, fbeWallpaperType, 0, fbeWallpaperPath);
        pluginHomeWallpaper.setWallpaperHints(getFbeSemWallpaperColors(1));
    }

    private File getFbeColorFile(int i) {
        return getFbeFile(i, "color");
    }

    private File getFbeFile(int i, String str) {
        File[] fileArrListFiles;
        File file = new File(i == 0 ? FBE_PATH : FBE_SUB_PATH);
        if (!file.exists() || (fileArrListFiles = file.listFiles()) == null || fileArrListFiles.length == 0) {
            return null;
        }
        for (File file2 : fileArrListFiles) {
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
    public void fillWallpaperData(int i, int i2, int i3, String str) throws NumberFormatException {
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
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getFbeWallpaper screen: ", ", path: ");
            sbM.append(fbeWallpaperFile.getPath());
            Log.d(TAG, sbM.toString());
            if (fbeWallpaperFile.exists() && fbeWallpaperFile.canRead()) {
                return getBitmapFromPath(fbeWallpaperFile.getPath());
            }
        }
        Log.d(TAG, "getFbeWallpaper null");
        return null;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperIntelligentCrop(int i) throws IOException {
        File fbeIcropFile = getFbeIcropFile(i);
        String line = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fbeIcropFile);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        line = bufferedReader.readLine();
                        bufferedReader.close();
                        inputStreamReader.close();
                        fileInputStream.close();
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.d(TAG, "getFbeWallpaperIntelligentCrop: " + e.getMessage());
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getFbeWallpaperIntelligentCrop: iCrops = ", line, TAG);
        return line;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public String getFbeWallpaperPath() {
        return getFbeWallpaperPath(this.mScreenType);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public Rect getFbeWallpaperRect() throws IOException {
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getSubFbeWallpaperType() {
        File fbeWallpaperFile;
        String str;
        char c = 1;
        if (!this.mUtils.isGoingToRescueParty() && (fbeWallpaperFile = getFbeWallpaperFile(1)) != null && fbeWallpaperFile.exists() && fbeWallpaperFile.canRead() && (str = fbeWallpaperFile.getName().split("_")[1]) != null) {
            switch (str.hashCode()) {
                case 102340:
                    if (!str.equals("gif")) {
                        c = 65535;
                        break;
                    } else {
                        c = 0;
                        break;
                    }
                case 100313435:
                    if (!str.equals("image")) {
                    }
                    break;
                case 112202875:
                    if (str.equals(ServiceTuple.MEDIA_CAP_VIDEO)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            switch (c) {
            }
            return -2;
        }
        return -2;
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
    public int getWallpaperIndex(int i, Bundle bundle) throws NumberFormatException {
        String string;
        int i2;
        if (!LsRune.SUBSCREEN_WATCHFACE || i != 1) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "getWallpaperIndex: Not supported yet! screen = ", TAG);
            return -1;
        }
        if (bundle != null) {
            string = bundle.getString("caller");
            i2 = bundle.getInt("multi_pack_size");
        } else {
            string = null;
            i2 = -1;
        }
        StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(i, "getWallpaperIndex: screen = ", ", source = ", string, ", size = ");
        sbM.append(i2);
        Log.d(TAG, sbM.toString());
        String homeWallpaperPath = getHomeWallpaperPath(i);
        if (homeWallpaperPath == null) {
            return -1;
        }
        try {
            String strSubstring = homeWallpaperPath.substring(homeWallpaperPath.lastIndexOf("/") + 1);
            int iIndexOf = strSubstring.indexOf(".");
            if (iIndexOf > 0) {
                strSubstring = strSubstring.substring(0, iIndexOf);
            }
            String strReplaceAll = strSubstring.replaceAll("[^0-9]", "");
            if (isNumeric(strReplaceAll)) {
                int i3 = Integer.parseInt(strReplaceAll);
                if ("Cover".equals(string) && i3 - 1 == -1) {
                    i3 = i2 - 1;
                }
                Log.d(TAG, "getWallpaperIndex: strIndex = " + strReplaceAll + ", index = " + i3);
                return i3;
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
        PluginHomeWallpaper pluginHomeWallpaper;
        if (isHomeWallpaperRequired(i) && (pluginHomeWallpaper = this.mMediator.getPluginHomeWallpaper()) != null) {
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
        boolean zIsDynamicLockEnabled = this.mMediator.isDynamicLockEnabled();
        boolean zIsCurrentOwner = this.mUtils.isCurrentOwner();
        PluginLockUtils pluginLockUtils = this.mUtils;
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("onWallpaperConsumed, enabled:", ", mIsSwitchingToSub: ", zIsDynamicLockEnabled);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mIsSwitchingToSub, ", isOwner: ", zIsCurrentOwner, ", screen:");
        sbM.append(i);
        sbM.append(", updateColor:");
        sbM.append(z);
        pluginLockUtils.addDump(TAG, sbM.toString());
        if (this.mMediator.getPluginLockWallpaper() == null || !zIsDynamicLockEnabled || this.mIsSwitchingToSub || !zIsCurrentOwner || this.mDelegateApp == null) {
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
    public SemWallpaperColors getFbeSemWallpaperColors(int i) throws IOException {
        File fbeColorFile = getFbeColorFile(i);
        if (fbeColorFile != null && fbeColorFile.exists() && fbeColorFile.canRead()) {
            StringBuilder sb = new StringBuilder();
            try {
                FileInputStream fileInputStream = new FileInputStream(fbeColorFile.getPath());
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
                    try {
                        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                            sb.append(line);
                        }
                        bufferedReader.close();
                        fileInputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String string = sb.toString();
                if (!string.isEmpty()) {
                    return SemWallpaperColors.fromXml(string);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
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
    public Rect getFbeWallpaperRect(int i) throws IOException {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        File fbeRectFile = getFbeRectFile(i);
        Rect rectUnflattenFromString = null;
        try {
            fileInputStream = new FileInputStream(fbeRectFile);
            try {
                inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    bufferedReader = new BufferedReader(inputStreamReader);
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            rectUnflattenFromString = Rect.unflattenFromString(bufferedReader.readLine());
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            Log.d(TAG, "getFbeWallpaperRect, rect: " + rectUnflattenFromString);
            return rectUnflattenFromString;
        } finally {
        }
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeAvailable(int i) {
        return isFbeRequired(i) && isFbeWallpaperAvailable(i);
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeRequired(int i) {
        if (this.mKeyguardUpdateMonitor.isUserUnlocked$1()) {
            return false;
        }
        return this.mSettingsHelper.getPluginLockValue(i) % 10 != 0 || this.mSettingsHelper.getPluginLockValue(i) == 30000;
    }

    @Override // com.android.systemui.pluginlock.PluginWallpaperManager
    public boolean isFbeWallpaperAvailable(int i) {
        File fbeWallpaperFile = getFbeWallpaperFile(i);
        boolean z = fbeWallpaperFile != null && fbeWallpaperFile.exists() && fbeWallpaperFile.canRead();
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("isFbeWallpaperAvailable: screen = ", i, ", flag = ", z, ", file = ");
        sbM.append(fbeWallpaperFile != null ? fbeWallpaperFile.getAbsolutePath() : "null");
        Log.i(TAG, sbM.toString());
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
        boolean zIsDynamicLockEnabled = this.mMediator.isDynamicLockEnabled();
        boolean zIsCurrentOwner = this.mUtils.isCurrentOwner();
        PluginLockUtils pluginLockUtils = this.mUtils;
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("notifyWallpaperChanged, enabled:", ", mIsSwitchingToSub: ", zIsDynamicLockEnabled);
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mIsSwitchingToSub, ", isOwner: ", zIsCurrentOwner, ", screen:");
        sbM.append(i);
        pluginLockUtils.addDump(TAG, sbM.toString());
        PluginLockWallpaper pluginLockWallpaper = this.mMediator.getPluginLockWallpaper();
        if (pluginLockWallpaper == null || !zIsDynamicLockEnabled || this.mIsSwitchingToSub || !zIsCurrentOwner) {
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
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "getFbeWallpaper screen: ", ", path: ");
            sbM.append(fbeWallpaperFile.getPath());
            Log.d(TAG, sbM.toString());
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
    public int getWallpaperIndex() throws NumberFormatException {
        String wallpaperPath;
        if ((!isCustomPackApplied() && !isMultiPackApplied()) || (wallpaperPath = getWallpaperPath()) == null) {
            return -1;
        }
        try {
            String strSubstring = wallpaperPath.substring(wallpaperPath.lastIndexOf("/") + 1);
            int iIndexOf = strSubstring.indexOf(".");
            if (iIndexOf > 0) {
                strSubstring = strSubstring.substring(0, iIndexOf);
            }
            String strReplaceAll = strSubstring.replaceAll("[^0-9]", "");
            if (isNumeric(strReplaceAll)) {
                int i = Integer.parseInt(strReplaceAll);
                if (isMultiPackApplied()) {
                    i--;
                }
                Log.d(TAG, "getWallpaperIndex: strIndex = " + strReplaceAll + ", index = " + i);
                return i;
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m("getWallpaperIndex, ", e, TAG);
        }
        return -1;
    }
}
