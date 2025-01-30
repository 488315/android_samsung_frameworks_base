package com.android.systemui.pluginlock;

import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.component.PluginHomeWallpaper;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.KeyguardListener$UserSwitch;
import com.android.systemui.pluginlock.utils.BitmapUtils;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.sdk.cover.ScoverManager;
import com.sec.ims.presence.ServiceTuple;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PluginWallpaperManagerImpl implements PluginWallpaperManager, KeyguardListener$UserSwitch {
    public final Context mContext;
    public final PluginLockDelegateApp mDelegateApp;
    public boolean mIsSwitchingToSub = false;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final PluginLockMediator mMediator;
    public final int mScreenType;
    public final SettingsHelper mSettingsHelper;
    public final PluginLockUtils mUtils;

    public PluginWallpaperManagerImpl(PluginLockMediator pluginLockMediator, PluginLockDelegateApp pluginLockDelegateApp, SettingsHelper settingsHelper, PluginLockUtils pluginLockUtils, Context context, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        PluginHomeWallpaper pluginHomeWallpaper;
        this.mScreenType = 0;
        this.mDelegateApp = pluginLockDelegateApp;
        this.mUtils = pluginLockUtils;
        this.mContext = context;
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mMediator = pluginLockMediator;
        ((PluginLockMediatorImpl) pluginLockMediator).setKeyguardUserSwitchListener(this);
        pluginLockUtils.addDump("PluginWallpaperManagerImpl", "## PluginWallpaperManagerImpl ##, " + this);
        if (LsRune.LOCKUI_SUB_DISPLAY_LOCK || LsRune.WALLPAPER_SUB_WATCHFACE) {
            try {
                if (WallpaperManager.getInstance(context).getLidState() == 0) {
                    Log.d("PluginWallpaperManagerImpl", "PluginLockWallpaperManager: mScreenType = PluginLock.SCREEN_SUB");
                    this.mScreenType = 1;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            ScoverManager scoverManager = new ScoverManager(context);
            if (scoverManager.getCoverState() != null && !scoverManager.getCoverState().switchState) {
                Log.d("PluginWallpaperManagerImpl", "PluginLockWallpaperManager, virtual display: mScreenType = PluginLock.SCREEN_SUB");
                this.mScreenType = 1;
            }
        }
        if (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY) {
            boolean isFbeWallpaperAvailable = isFbeWallpaperAvailable(1);
            int subFbeWallpaperType = getSubFbeWallpaperType();
            String fbeWallpaperPath = getFbeWallpaperPath(1);
            this.mUtils.addDump("PluginWallpaperManagerImpl", "fillFbeWallpaperData, fbeSubType: " + subFbeWallpaperType + ", fbeSubPath: " + fbeWallpaperPath);
            if (!isFbeWallpaperAvailable || subFbeWallpaperType == -2 || fbeWallpaperPath == null || (pluginHomeWallpaper = ((PluginLockMediatorImpl) this.mMediator).mHomeWallpaper) == null) {
                return;
            }
            pluginHomeWallpaper.setWallpaper(1, subFbeWallpaperType, 0, fbeWallpaperPath, null);
            SemWallpaperColors fbeSemWallpaperColors = getFbeSemWallpaperColors(1);
            PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) pluginHomeWallpaper.mWallpaperDataList).get(Integer.valueOf(PluginHomeWallpaper.getKey(PluginHomeWallpaper.sScreenType)));
            if (wallpaperData != null) {
                wallpaperData.mHints = fbeSemWallpaperColors;
            }
        }
    }

    public static File getFbeFile(int i, String str) {
        File[] listFiles;
        File file = new File(i == 0 ? "/data/user_de/0/com.android.systemui/files/fresh_pack/" : "/data/user_de/0/com.android.systemui/files/fresh_pack_sub/");
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

    public final SemWallpaperColors getFbeSemWallpaperColors(int i) {
        File fbeFile = getFbeFile(i, "color");
        if (fbeFile != null && fbeFile.exists() && fbeFile.canRead()) {
            StringBuilder sb = new StringBuilder();
            try {
                FileInputStream fileInputStream = new FileInputStream(fbeFile.getPath());
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
                    try {
                        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                            sb.append(readLine);
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
                String sb2 = sb.toString();
                if (!sb2.isEmpty()) {
                    return SemWallpaperColors.fromXml(sb2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return SemWallpaperColors.getBlankWallpaperColors();
    }

    public final Bitmap getFbeWallpaper(int i, boolean z) {
        File fbeFile = getFbeFile(i, "fbe");
        if (fbeFile != null) {
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("getFbeWallpaper screen: ", i, ", path: ");
            m1m.append(fbeFile.getPath());
            Log.d("PluginWallpaperManagerImpl", m1m.toString());
            if (fbeFile.exists() && fbeFile.canRead()) {
                return BitmapUtils.getBitmapFromPath(this.mContext, fbeFile.getPath(), !z, false);
            }
        }
        Log.d("PluginWallpaperManagerImpl", "getFbeWallpaper null");
        return null;
    }

    public final String getFbeWallpaperIntelligentCrop(int i) {
        File fbeFile = getFbeFile(i, "icrops");
        String str = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fbeFile);
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        str = bufferedReader.readLine();
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
            e.printStackTrace();
        }
        AbstractC0000x2c234b15.m3m("getFbeWallpaperIntelligentCrop: iCrops = ", str, "PluginWallpaperManagerImpl");
        return str;
    }

    public final String getFbeWallpaperPath(int i) {
        File fbeFile = getFbeFile(i, "fbe");
        return fbeFile != null ? fbeFile.getAbsolutePath() : "";
    }

    public final String getHomeWallpaperPath(int i) {
        PluginHomeWallpaper pluginHomeWallpaper = ((PluginLockMediatorImpl) this.mMediator).mHomeWallpaper;
        if (pluginHomeWallpaper != null) {
            PluginHomeWallpaper.WallpaperData wallpaperData = (PluginHomeWallpaper.WallpaperData) ((HashMap) pluginHomeWallpaper.mWallpaperDataList).get(Integer.valueOf(PluginHomeWallpaper.getKey(i)));
            if (wallpaperData != null) {
                return wallpaperData.mPath;
            }
        }
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004c, code lost:
    
        if (r4.equals("image") == false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getSubFbeWallpaperType() {
        String str;
        this.mUtils.getClass();
        if (PluginLockUtils.isGoingToRescueParty()) {
            return -2;
        }
        char c = 1;
        File fbeFile = getFbeFile(1, "fbe");
        if (fbeFile != null && fbeFile.exists() && fbeFile.canRead() && (str = fbeFile.getName().split("_")[1]) != null) {
            switch (str.hashCode()) {
                case 102340:
                    if (str.equals("gif")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 100313435:
                    break;
                case 112202875:
                    if (str.equals(ServiceTuple.MEDIA_CAP_VIDEO)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
            }
            return -2;
        }
        return -2;
    }

    public final Bitmap getWallpaperBitmap() {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        if (pluginLockWallpaper == null) {
            return null;
        }
        return ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(pluginLockWallpaper.getScreenType())).mBitmap;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0038, code lost:
    
        if (r2 == false) goto L43;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0095 A[Catch: Exception -> 0x00af, TryCatch #0 {Exception -> 0x00af, blocks: (B:22:0x0041, B:24:0x0054, B:25:0x0058, B:27:0x0068, B:30:0x0078, B:32:0x0082, B:38:0x0095, B:39:0x0097), top: B:21:0x0041 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getWallpaperIndex() {
        boolean z;
        boolean isCustomPackApplied = isCustomPackApplied();
        PluginLockMediator pluginLockMediator = this.mMediator;
        boolean z2 = true;
        if (!isCustomPackApplied) {
            PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) pluginLockMediator).mLockWallpaper;
            if (isDynamicLockEnabled() && pluginLockWallpaper != null) {
                if ((pluginLockWallpaper.isCustomPack(pluginLockWallpaper.getScreenType()) || pluginLockWallpaper.isServiceWallpaper(pluginLockWallpaper.getScreenType())) ? false : true) {
                    z = true;
                }
            }
            z = false;
        }
        String wallpaperPath = getWallpaperPath();
        if (wallpaperPath == null) {
            return -1;
        }
        try {
            String substring = wallpaperPath.substring(wallpaperPath.lastIndexOf("/") + 1);
            int indexOf = substring.indexOf(".");
            if (indexOf > 0) {
                substring = substring.substring(0, indexOf);
            }
            String replaceAll = substring.replaceAll("[^0-9]", "");
            if (replaceAll.matches("^(0|[1-9][0-9]*)$")) {
                int parseInt = Integer.parseInt(replaceAll);
                PluginLockWallpaper pluginLockWallpaper2 = ((PluginLockMediatorImpl) pluginLockMediator).mLockWallpaper;
                if (isDynamicLockEnabled() && pluginLockWallpaper2 != null) {
                    if ((pluginLockWallpaper2.isCustomPack(pluginLockWallpaper2.getScreenType()) || pluginLockWallpaper2.isServiceWallpaper(pluginLockWallpaper2.getScreenType())) ? false : true) {
                        if (z2) {
                            parseInt--;
                        }
                        Log.d("PluginWallpaperManagerImpl", "getWallpaperIndex: strIndex = " + replaceAll + ", index = " + parseInt);
                        return parseInt;
                    }
                }
                z2 = false;
                if (z2) {
                }
                Log.d("PluginWallpaperManagerImpl", "getWallpaperIndex: strIndex = " + replaceAll + ", index = " + parseInt);
                return parseInt;
            }
        } catch (Exception e) {
            EmergencyButton$$ExternalSyntheticOutline0.m58m("getWallpaperIndex, ", e, "PluginWallpaperManagerImpl");
        }
        return -1;
    }

    public final String getWallpaperIntelligentCrop(int i) {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        if (pluginLockWallpaper == null) {
            return null;
        }
        if (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY || PluginLockWallpaper.isCloneDisplayRequired()) {
            i = 0;
        }
        return ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(i)).mIntelligentCrop;
    }

    public final String getWallpaperPath() {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        if (pluginLockWallpaper != null) {
            return pluginLockWallpaper.getWallpaperPath(pluginLockWallpaper.getScreenType());
        }
        return null;
    }

    public final Uri getWallpaperUri() {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        if (pluginLockWallpaper == null) {
            return null;
        }
        return ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(pluginLockWallpaper.getScreenType())).mUri;
    }

    public final boolean isCustomPackApplied() {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isCustomPack(pluginLockWallpaper.getScreenType());
    }

    public final boolean isDynamicLockEnabled() {
        return ((PluginLockMediatorImpl) this.mMediator).isDynamicLockEnabled();
    }

    public final boolean isDynamicWallpaperEnabled() {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        return isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isDynamicWallpaper();
    }

    public final boolean isFbeRequired(int i) {
        if (!this.mKeyguardUpdateMonitor.isUserUnlocked()) {
            SettingsHelper settingsHelper = this.mSettingsHelper;
            if (settingsHelper.getPluginLockValue(i) % 10 != 0 || settingsHelper.getPluginLockValue(i) == 30000) {
                return true;
            }
        }
        return false;
    }

    public final boolean isFbeWallpaperAvailable(int i) {
        File fbeFile = getFbeFile(i, "fbe");
        boolean z = fbeFile != null && fbeFile.exists() && fbeFile.canRead();
        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("isFbeWallpaperAvailable: screen = ", i, ", flag = ", z, ", file = ");
        m76m.append(fbeFile != null ? fbeFile.getAbsolutePath() : "null");
        Log.i("PluginWallpaperManagerImpl", m76m.toString());
        if (z) {
            this.mUtils.getClass();
            if (!PluginLockUtils.isGoingToRescueParty()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isFbeWallpaperVideo(int i) {
        String str;
        File fbeFile = getFbeFile(i, "fbe");
        if (fbeFile != null && fbeFile.exists() && fbeFile.canRead() && (str = fbeFile.getName().split("_")[1]) != null && str.equals(ServiceTuple.MEDIA_CAP_VIDEO)) {
            this.mUtils.getClass();
            if (!PluginLockUtils.isGoingToRescueParty()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isMultiPackApplied(int i) {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        if (!isDynamicLockEnabled() || pluginLockWallpaper == null) {
            return false;
        }
        return !pluginLockWallpaper.isCustomPack(i) && !pluginLockWallpaper.isServiceWallpaper(i);
    }

    public final boolean isVideoWallpaperEnabled() {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        if (!isDynamicLockEnabled() || pluginLockWallpaper == null) {
            return false;
        }
        return pluginLockWallpaper.isDynamicWallpaper() && ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(pluginLockWallpaper.getScreenType())).mType == 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLockWallpaperChanged(int i) {
        PluginLockDelegateApp pluginLockDelegateApp;
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) this.mMediator;
        boolean isDynamicLockEnabled = pluginLockMediatorImpl.isDynamicLockEnabled();
        PluginLockUtils pluginLockUtils = this.mUtils;
        pluginLockUtils.getClass();
        boolean isCurrentOwner = PluginLockUtils.isCurrentOwner();
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("notifyWallpaperChanged, enabled:", isDynamicLockEnabled, ", mIsSwitchingToSub: ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m49m, this.mIsSwitchingToSub, ", isOwner: ", isCurrentOwner, ", screen:");
        m49m.append(i);
        pluginLockUtils.addDump("PluginWallpaperManagerImpl", m49m.toString());
        PluginLockWallpaper pluginLockWallpaper = pluginLockMediatorImpl.mLockWallpaper;
        if (pluginLockWallpaper == null || !isDynamicLockEnabled || this.mIsSwitchingToSub || !isCurrentOwner) {
            return;
        }
        boolean z = LsRune.LOCKUI_SUB_DISPLAY_LOCK;
        if (z) {
            if (i == 2) {
                pluginLockWallpaper.mWholeRecoverRequired = true;
                pluginLockWallpaper.setRecoverRequestedScreen(-1);
            } else {
                pluginLockWallpaper.mWholeRecoverRequired = false;
                pluginLockWallpaper.setRecoverRequestedScreen(i);
            }
        }
        RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("recover() screenType:"), PluginLockWallpaper.sScreenType, "PluginLockWallpaper");
        boolean z2 = z && pluginLockWallpaper.mWholeRecoverRequired;
        List list = pluginLockWallpaper.mWallpaperDataList;
        if (z2) {
            Iterator it = ((ArrayList) list).iterator();
            while (it.hasNext()) {
                ((PluginLockWallpaper.PluginLockWallpaperData) it.next()).resetAll();
            }
        } else {
            ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) list).get(pluginLockWallpaper.getScreenType())).mType = -2;
        }
        if (!PluginLockWallpaper.isCloneDisplayRequired()) {
            if (!(LsRune.LOCKUI_SUB_DISPLAY_LOCK && pluginLockWallpaper.mWholeRecoverRequired)) {
                int i2 = PluginLockWallpaper.sScreenType;
                PluginLockInstanceState pluginLockInstanceState = pluginLockWallpaper.mInstanceState;
                if (pluginLockInstanceState != null && (recoverData = pluginLockInstanceState.getRecoverData()) != null) {
                    recoverData.setWallpaperDynamic(i2, -2);
                    recoverData.setWallpaperSource(i2, -1);
                    recoverData.setWallpaperType(i2, -1);
                    pluginLockWallpaper.mInstanceState.updateDb();
                }
                pluginLockWallpaper.reset(false);
                pluginLockDelegateApp = this.mDelegateApp;
                if (pluginLockDelegateApp == null) {
                    pluginLockMediatorImpl.getClass();
                    Log.d("PluginLockMediatorImpl", "recoverItem() type:1");
                    if (pluginLockMediatorImpl.mSecure != null) {
                        Log.d("PluginLockSecure", "recover()");
                        Log.d("PluginLockSecure", "reset()");
                    }
                    try {
                        pluginLockUtils.addDump("PluginWallpaperManagerImpl", "onLockWallpaperChanged called");
                        pluginLockDelegateApp.onWallpaperChanged(i);
                        return;
                    } catch (Throwable th) {
                        pluginLockUtils.addDump("PluginWallpaperManagerImpl", "onLockWallpaperChanged, " + th.toString());
                        th.printStackTrace();
                        return;
                    }
                }
                return;
            }
        }
        pluginLockWallpaper.setWallpaperBackupValue();
        pluginLockWallpaper.reset(false);
        pluginLockDelegateApp = this.mDelegateApp;
        if (pluginLockDelegateApp == null) {
        }
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener$UserSwitch
    public final void onUserSwitchComplete(int i) {
        this.mUtils.addDump("PluginWallpaperManagerImpl", "onUserSwitchComplete, userId: " + i);
        this.mIsSwitchingToSub = false;
    }

    @Override // com.android.systemui.pluginlock.listener.KeyguardListener$UserSwitch
    public final void onUserSwitching(int i) {
        this.mUtils.addDump("PluginWallpaperManagerImpl", "onUserSwitching, userId: " + i);
        this.mIsSwitchingToSub = true;
    }

    public final void onWallpaperConsumed(int i, boolean z) {
        PluginLockDelegateApp pluginLockDelegateApp;
        PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) this.mMediator;
        boolean isDynamicLockEnabled = pluginLockMediatorImpl.isDynamicLockEnabled();
        PluginLockUtils pluginLockUtils = this.mUtils;
        pluginLockUtils.getClass();
        boolean isCurrentOwner = PluginLockUtils.isCurrentOwner();
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("onWallpaperConsumed, enabled:", isDynamicLockEnabled, ", mIsSwitchingToSub: ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m49m, this.mIsSwitchingToSub, ", isOwner: ", isCurrentOwner, ", screen:");
        m49m.append(i);
        m49m.append(", updateColor:");
        m49m.append(z);
        pluginLockUtils.addDump("PluginWallpaperManagerImpl", m49m.toString());
        if (pluginLockMediatorImpl.mLockWallpaper == null || !isDynamicLockEnabled || this.mIsSwitchingToSub || !isCurrentOwner || (pluginLockDelegateApp = this.mDelegateApp) == null) {
            return;
        }
        try {
            pluginLockUtils.addDump("PluginWallpaperManagerImpl", "onWallpaperConsumed called");
            pluginLockDelegateApp.onWallpaperConsumed(i, z);
        } catch (Throwable th) {
            pluginLockUtils.addDump("PluginWallpaperManagerImpl", "onWallpaperConsumed, " + th.toString());
            th.printStackTrace();
        }
    }

    public final boolean isDynamicWallpaperEnabled(int i) {
        PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) this.mMediator).mLockWallpaper;
        if (!isDynamicLockEnabled() || pluginLockWallpaper == null) {
            return false;
        }
        int i2 = (LsRune.WALLPAPER_SUB_WATCHFACE || LsRune.WALLPAPER_VIRTUAL_DISPLAY || PluginLockWallpaper.isCloneDisplayRequired()) ? 0 : i;
        boolean z = ((PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(i2)).mType != -2;
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("isDynamicWallpaper() required:", i, ", final: ", i2, ", ret:"), z, "PluginLockWallpaper");
        return z;
    }
}
