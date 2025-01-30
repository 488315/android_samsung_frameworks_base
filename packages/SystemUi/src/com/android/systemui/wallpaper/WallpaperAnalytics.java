package com.android.systemui.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.app.AbstractC0147x487e7be7;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.PluginLockMediatorImpl;
import com.android.systemui.pluginlock.PluginWallpaperManager;
import com.android.systemui.pluginlock.PluginWallpaperManagerImpl;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.utils.WhichChecker;
import com.samsung.android.wallpaper.utils.SemWallpaperProperties;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WallpaperAnalytics {
    public final Context mContext;
    public final PluginWallpaperManager mPluginWallpaperManager;
    public final SettingsHelper mSettingsHelper;
    public final WallpaperManager mWallpaperManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wallpaper.WallpaperAnalytics$1 */
    public abstract /* synthetic */ class AbstractC36781 {

        /* renamed from: $SwitchMap$com$android$systemui$wallpaper$WallpaperAnalytics$StatusField */
        public static final /* synthetic */ int[] f402x7bce0953;

        static {
            int[] iArr = new int[StatusField.values().length];
            f402x7bce0953 = iArr;
            try {
                iArr[StatusField.TYPE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f402x7bce0953[StatusField.FROM.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    enum StatusField {
        TYPE,
        FROM
    }

    public WallpaperAnalytics(Context context, PluginWallpaperManager pluginWallpaperManager, SettingsHelper settingsHelper) {
        this.mContext = context;
        this.mWallpaperManager = WallpaperManager.getInstance(context);
        this.mPluginWallpaperManager = pluginWallpaperManager;
        this.mSettingsHelper = settingsHelper;
    }

    public static String getStatusId(int i, StatusField statusField) {
        if ((i & 60) == 0) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("getStatusId: mode is missing. which=", i, "WallpaperAnalytics");
        }
        int i2 = AbstractC36781.f402x7bce0953[statusField.ordinal()];
        String str = i2 != 1 ? i2 != 2 ? null : WhichChecker.isFlagEnabled(i, 2) ? "WS0004" : "WS0002" : WhichChecker.isFlagEnabled(i, 2) ? "WS0003" : "WS0001";
        return (str == null || !WhichChecker.isSubDisplay(i)) ? str : str.concat("_C");
    }

    public final boolean isSggApplied(int i) {
        Uri semGetUri;
        int lastIndexOf;
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (wallpaperManager.semGetWallpaperType(i) != 1000 || (semGetUri = wallpaperManager.semGetUri(i)) == null) {
            return false;
        }
        String uri = semGetUri.toString();
        if (TextUtils.isEmpty(uri) || (lastIndexOf = uri.lastIndexOf("/")) < 0) {
            return false;
        }
        String substring = uri.substring(lastIndexOf + 1);
        return !TextUtils.isEmpty(substring) && substring.contains("sgg");
    }

    public final void setWallpaperStatus(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        Log.i("WallpaperAnalytics", "setWallpaperStatus: " + str + " = " + str2);
        this.mContext.getSharedPreferences("wallpaper_pref", 0).edit().putString(str, str2).apply();
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0201, code lost:
    
        if ("layered".equals(r4) != false) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x020e, code lost:
    
        if (android.text.TextUtils.isEmpty(r0) == false) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0195, code lost:
    
        if (r1 != false) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01d8, code lost:
    
        if (r0 != 3) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01e2, code lost:
    
        if (android.text.TextUtils.isEmpty(r4) == false) goto L140;
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x012c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateWallpaperStatus(int i) {
        int i2;
        int screenId;
        int semGetWallpaperType;
        String str;
        int screenId2;
        boolean isFlagEnabled;
        int intValue;
        String imageCategory;
        PluginWallpaperManagerImpl pluginWallpaperManagerImpl;
        PluginWallpaperManagerImpl pluginWallpaperManagerImpl2;
        boolean z = false;
        if ((i & 2) == 2) {
            if ((i & 1) == 1) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("updateWallpaperStatus : system&lock requested. which=", i, "WallpaperAnalytics");
                int i3 = i & 60;
                updateWallpaperStatus(i3 | 1);
                updateWallpaperStatus(i3 | 2);
                return;
            }
        }
        boolean z2 = LsRune.WALLPAPER_SUB_DISPLAY_MODE;
        if (z2 && LsRune.WALLPAPER_SUB_WATCHFACE && WhichChecker.isFlagEnabled(i, 2) && WhichChecker.isSubDisplay(i)) {
            AbstractC0147x487e7be7.m26m("updateWallpaperStatus : which = ", i, ". This model does not have Lockscreen wallpaper for cover display. Convering to main screen.", "WallpaperAnalytics");
            i = 6;
        }
        int i4 = i & 60;
        WallpaperManager wallpaperManager = this.mWallpaperManager;
        if (i4 == 0) {
            int i5 = i & 3;
            i = z2 && wallpaperManager.getLidState() == 0 ? i5 | 16 : i5 | 4;
        }
        int screenId3 = PluginWallpaperManager.getScreenId(i);
        boolean isFlagEnabled2 = WhichChecker.isFlagEnabled(i, 2);
        PluginWallpaperManager pluginWallpaperManager = this.mPluginWallpaperManager;
        if (!isFlagEnabled2 || pluginWallpaperManager == null || !((PluginWallpaperManagerImpl) pluginWallpaperManager).isDynamicWallpaperEnabled(screenId3)) {
            int i6 = i & 60;
            if (wallpaperManager.isSystemAndLockPaired(i6)) {
                i2 = i6 | 1;
                Log.i("WallpaperAnalytics", "updateWallpaperStatus : which=" + i + ", sourceWhich=" + i2);
                screenId = PluginWallpaperManager.getScreenId(i2);
                if (WhichChecker.isFlagEnabled(i2, 2) && pluginWallpaperManager != null) {
                    pluginWallpaperManagerImpl2 = (PluginWallpaperManagerImpl) pluginWallpaperManager;
                    if (pluginWallpaperManagerImpl2.isDynamicWallpaperEnabled(screenId)) {
                        if (!isSggApplied(i2)) {
                            PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) pluginWallpaperManagerImpl2.mMediator).mLockWallpaper;
                            if (pluginWallpaperManagerImpl2.isDynamicLockEnabled() && pluginLockWallpaper != null && pluginLockWallpaper.isCustomPack(screenId)) {
                                str = "Gallery Multi pack";
                            } else if (pluginWallpaperManagerImpl2.isMultiPackApplied(screenId)) {
                                str = "Theme Multi pack";
                            }
                            setWallpaperStatus(getStatusId(i, StatusField.TYPE), str);
                            screenId2 = PluginWallpaperManager.getScreenId(i2);
                            if (WhichChecker.isFlagEnabled(i2, 2) && pluginWallpaperManager != null) {
                                pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) pluginWallpaperManager;
                                if (pluginWallpaperManagerImpl.isDynamicWallpaperEnabled(screenId2)) {
                                    PluginLockMediator pluginLockMediator = pluginWallpaperManagerImpl.mMediator;
                                    PluginLockWallpaper pluginLockWallpaper2 = ((PluginLockMediatorImpl) pluginLockMediator).mLockWallpaper;
                                    boolean z3 = pluginWallpaperManagerImpl.isDynamicLockEnabled() && pluginLockWallpaper2 != null && pluginLockWallpaper2.isCustomPack(screenId2);
                                    PluginLockWallpaper pluginLockWallpaper3 = ((PluginLockMediatorImpl) pluginLockMediator).mLockWallpaper;
                                    if (pluginWallpaperManagerImpl.isDynamicLockEnabled() && pluginLockWallpaper3 != null && pluginLockWallpaper3.isServiceWallpaper(screenId2)) {
                                        z = true;
                                    }
                                    boolean isMultiPackApplied = pluginWallpaperManagerImpl.isMultiPackApplied(screenId2);
                                    boolean isSggApplied = isSggApplied(i2);
                                    StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("getWallpaperSourceString: isCustomMultiPack = ", z3, ", isSgg = ", isSggApplied, ", isServiceWallpaper = ");
                                    m69m.append(z);
                                    m69m.append(", isThemeMultiPack = ");
                                    m69m.append(isMultiPackApplied);
                                    Log.i("WallpaperAnalytics", m69m.toString());
                                    if (isSggApplied) {
                                        imageCategory = "SGG";
                                    } else {
                                        if (!z3) {
                                            if (z) {
                                                imageCategory = "DLS";
                                            }
                                        }
                                        imageCategory = "Custom";
                                    }
                                    setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
                                }
                            }
                            Context context = this.mContext;
                            SemWallpaperProperties semWallpaperProperties = new SemWallpaperProperties(context, i2, context.getUserId());
                            String contentType = semWallpaperProperties.getContentType();
                            boolean isSubDisplay = WhichChecker.isSubDisplay(i2);
                            isFlagEnabled = WhichChecker.isFlagEnabled(i2, 2);
                            SettingsHelper settingsHelper = this.mSettingsHelper;
                            if (isFlagEnabled) {
                                intValue = settingsHelper.getLockscreenWallpaperTransparent(isSubDisplay);
                            } else {
                                SettingsHelper.ItemMap itemMap = settingsHelper.mItemLists;
                                intValue = isSubDisplay ? itemMap.get("sub_display_system_wallpaper_transparency").getIntValue() : itemMap.get("android.wallpaper.settings_systemui_transparency").getIntValue();
                            }
                            if (intValue == 0) {
                                if (TextUtils.isEmpty(contentType)) {
                                    imageCategory = semWallpaperProperties.getImageCategory();
                                } else if ("prompt".equals(contentType)) {
                                    imageCategory = "prompt_g";
                                }
                                setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
                            }
                            if (intValue != 1) {
                                if (intValue != 2) {
                                }
                                imageCategory = "Theme";
                                setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
                            }
                            imageCategory = "Featured";
                            setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
                            imageCategory = contentType;
                            setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
                        }
                        str = "Multi pack";
                        setWallpaperStatus(getStatusId(i, StatusField.TYPE), str);
                        screenId2 = PluginWallpaperManager.getScreenId(i2);
                        if (WhichChecker.isFlagEnabled(i2, 2)) {
                            pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) pluginWallpaperManager;
                            if (pluginWallpaperManagerImpl.isDynamicWallpaperEnabled(screenId2)) {
                            }
                        }
                        Context context2 = this.mContext;
                        SemWallpaperProperties semWallpaperProperties2 = new SemWallpaperProperties(context2, i2, context2.getUserId());
                        String contentType2 = semWallpaperProperties2.getContentType();
                        boolean isSubDisplay2 = WhichChecker.isSubDisplay(i2);
                        isFlagEnabled = WhichChecker.isFlagEnabled(i2, 2);
                        SettingsHelper settingsHelper2 = this.mSettingsHelper;
                        if (isFlagEnabled) {
                        }
                        if (intValue == 0) {
                        }
                        imageCategory = contentType2;
                        setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
                    }
                }
                semGetWallpaperType = wallpaperManager.semGetWallpaperType(i2);
                if (semGetWallpaperType != 0) {
                    if (semGetWallpaperType != 1) {
                        if (semGetWallpaperType != 3) {
                            if (semGetWallpaperType == 4) {
                                str = "Animated";
                            } else if (semGetWallpaperType == 5) {
                                str = "Gif";
                            } else if (semGetWallpaperType == 7) {
                                str = wallpaperManager.isPreloadedLiveWallpaper(i2) ? "Internal live" : "3rd party live";
                            } else if (semGetWallpaperType != 8) {
                                IconCompat$$ExternalSyntheticOutline0.m30m("getWallpaperTypeString: Unknown wpType. type=", semGetWallpaperType, "WallpaperAnalytics");
                            } else {
                                str = "Video";
                            }
                        }
                        str = "Multi pack";
                    } else {
                        str = "Motion";
                    }
                    setWallpaperStatus(getStatusId(i, StatusField.TYPE), str);
                    screenId2 = PluginWallpaperManager.getScreenId(i2);
                    if (WhichChecker.isFlagEnabled(i2, 2)) {
                    }
                    Context context22 = this.mContext;
                    SemWallpaperProperties semWallpaperProperties22 = new SemWallpaperProperties(context22, i2, context22.getUserId());
                    String contentType22 = semWallpaperProperties22.getContentType();
                    boolean isSubDisplay22 = WhichChecker.isSubDisplay(i2);
                    isFlagEnabled = WhichChecker.isFlagEnabled(i2, 2);
                    SettingsHelper settingsHelper22 = this.mSettingsHelper;
                    if (isFlagEnabled) {
                    }
                    if (intValue == 0) {
                    }
                    imageCategory = contentType22;
                    setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
                }
                str = "Image";
                setWallpaperStatus(getStatusId(i, StatusField.TYPE), str);
                screenId2 = PluginWallpaperManager.getScreenId(i2);
                if (WhichChecker.isFlagEnabled(i2, 2)) {
                }
                Context context222 = this.mContext;
                SemWallpaperProperties semWallpaperProperties222 = new SemWallpaperProperties(context222, i2, context222.getUserId());
                String contentType222 = semWallpaperProperties222.getContentType();
                boolean isSubDisplay222 = WhichChecker.isSubDisplay(i2);
                isFlagEnabled = WhichChecker.isFlagEnabled(i2, 2);
                SettingsHelper settingsHelper222 = this.mSettingsHelper;
                if (isFlagEnabled) {
                }
                if (intValue == 0) {
                }
                imageCategory = contentType222;
                setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
            }
        }
        i2 = i;
        Log.i("WallpaperAnalytics", "updateWallpaperStatus : which=" + i + ", sourceWhich=" + i2);
        screenId = PluginWallpaperManager.getScreenId(i2);
        if (WhichChecker.isFlagEnabled(i2, 2)) {
            pluginWallpaperManagerImpl2 = (PluginWallpaperManagerImpl) pluginWallpaperManager;
            if (pluginWallpaperManagerImpl2.isDynamicWallpaperEnabled(screenId)) {
            }
        }
        semGetWallpaperType = wallpaperManager.semGetWallpaperType(i2);
        if (semGetWallpaperType != 0) {
        }
        str = "Image";
        setWallpaperStatus(getStatusId(i, StatusField.TYPE), str);
        screenId2 = PluginWallpaperManager.getScreenId(i2);
        if (WhichChecker.isFlagEnabled(i2, 2)) {
        }
        Context context2222 = this.mContext;
        SemWallpaperProperties semWallpaperProperties2222 = new SemWallpaperProperties(context2222, i2, context2222.getUserId());
        String contentType2222 = semWallpaperProperties2222.getContentType();
        boolean isSubDisplay2222 = WhichChecker.isSubDisplay(i2);
        isFlagEnabled = WhichChecker.isFlagEnabled(i2, 2);
        SettingsHelper settingsHelper2222 = this.mSettingsHelper;
        if (isFlagEnabled) {
        }
        if (intValue == 0) {
        }
        imageCategory = contentType2222;
        setWallpaperStatus(getStatusId(i, StatusField.FROM), imageCategory);
    }
}
