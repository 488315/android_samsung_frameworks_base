package com.android.systemui.pluginlock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.facewidget.plugin.FaceWidgetPluginLockManagerWrapper;
import com.android.systemui.keyguard.CustomizationProvider$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.component.PluginLockShortcutDnd;
import com.android.systemui.pluginlock.component.PluginLockShortcutFlashLight;
import com.android.systemui.pluginlock.component.PluginLockWallpaper;
import com.android.systemui.pluginlock.listener.PluginLockListener$State;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.pluginlock.model.FingerPrintData;
import com.android.systemui.pluginlock.model.IndicationData;
import com.android.systemui.pluginlock.model.MusicData;
import com.android.systemui.pluginlock.model.NotificationData;
import com.android.systemui.pluginlock.model.ServiceBoxData;
import com.android.systemui.pluginlock.model.ShortcutData;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.LogUtil;
import com.google.gson.Gson;
import com.samsung.systemui.splugins.pluginlock.PluginLock;
import com.samsung.systemui.splugins.pluginlock.PluginLockBasicManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PluginLockProvider extends ContentProvider {
    public static void onEventReceived(PluginLockManager pluginLockManager, Bundle bundle) {
        PluginLock pluginLock = ((PluginLockManagerImpl) pluginLockManager).mPluginLock;
        LogUtil.m223d("PluginLockProvider", "onEventReceived, pluginLock:" + pluginLock, new Object[0]);
        if (pluginLock != null) {
            PluginLockBasicManager basicManager = pluginLock.getBasicManager();
            LogUtil.m223d("PluginLockProvider", "onEventReceived, basicManager:" + basicManager, new Object[0]);
            if (basicManager != null) {
                LogUtil.m223d("PluginLockProvider", "call => onEventReceived", new Object[0]);
                basicManager.onEventReceived(bundle);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        char c;
        int wallpaperIndex;
        int i;
        int i2;
        int i3;
        PluginLockShortcutFlashLight pluginLockShortcutFlashLight;
        boolean isEnabled;
        int i4;
        Bundle bundle2 = new Bundle();
        PluginLockManager pluginLockManager = (PluginLockManager) Dependency.get(PluginLockManager.class);
        CustomizationProvider$$ExternalSyntheticOutline0.m135m("call: method ", str, ", arg:", str2, "PluginLockProvider");
        if (pluginLockManager != null) {
            str.getClass();
            switch (str.hashCode()) {
                case -1950545556:
                    if (str.equals("get_wallpaper_index")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1344377341:
                    if (str.equals("fill_wallpaper_data")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -222035686:
                    if (str.equals("remove_lockstar_task_shortcut_listener")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 98718662:
                    if (str.equals("update_lockstar_task_shortcut_state")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 541279321:
                    if (str.equals("get_lockstar_task_shortcut_state")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1008599367:
                    if (str.equals("get_dls_data")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                String str3 = null;
                boolean z = LsRune.SUBSCREEN_WATCHFACE;
                if (z && !TextUtils.isEmpty(str2) && Integer.parseInt(str2) == 1) {
                    PluginWallpaperManagerImpl pluginWallpaperManagerImpl = (PluginWallpaperManagerImpl) ((PluginWallpaperManager) Dependency.get(PluginWallpaperManager.class));
                    pluginWallpaperManagerImpl.getClass();
                    if (z) {
                        if (bundle != null) {
                            str3 = bundle.getString("caller");
                            i = bundle.getInt("multi_pack_size");
                        } else {
                            i = -1;
                        }
                        Log.d("PluginWallpaperManagerImpl", "getWallpaperIndex: screen = 1, source = " + str3 + ", size = " + i);
                        String homeWallpaperPath = pluginWallpaperManagerImpl.getHomeWallpaperPath(1);
                        if (homeWallpaperPath != null) {
                            try {
                                String substring = homeWallpaperPath.substring(homeWallpaperPath.lastIndexOf("/") + 1);
                                int indexOf = substring.indexOf(".");
                                if (indexOf > 0) {
                                    substring = substring.substring(0, indexOf);
                                }
                                String replaceAll = substring.replaceAll("[^0-9]", "");
                                if (replaceAll.matches("^(0|[1-9][0-9]*)$")) {
                                    int parseInt = Integer.parseInt(replaceAll);
                                    try {
                                        if ("Cover".equals(str3)) {
                                            parseInt--;
                                            i3 = -1;
                                            if (parseInt == -1) {
                                                int i5 = i - 1;
                                                i2 = -1;
                                                wallpaperIndex = i5;
                                                Log.d("PluginWallpaperManagerImpl", "getWallpaperIndex: strIndex = " + replaceAll + ", index = " + wallpaperIndex);
                                            }
                                        } else {
                                            i3 = -1;
                                        }
                                        Log.d("PluginWallpaperManagerImpl", "getWallpaperIndex: strIndex = " + replaceAll + ", index = " + wallpaperIndex);
                                    } catch (Exception e) {
                                        e = e;
                                        EmergencyButton$$ExternalSyntheticOutline0.m58m("getWallpaperIndex: ", e, "PluginWallpaperManagerImpl");
                                        wallpaperIndex = i2;
                                        bundle2.putInt("wallpaper_index", wallpaperIndex);
                                        return bundle2;
                                    }
                                    i2 = i3;
                                    wallpaperIndex = parseInt;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                i2 = -1;
                            }
                        }
                        wallpaperIndex = -1;
                    } else {
                        wallpaperIndex = -1;
                        Log.d("PluginWallpaperManagerImpl", "getWallpaperIndex: Not supported yet! screen = 1");
                    }
                } else {
                    wallpaperIndex = ((PluginWallpaperManagerImpl) ((PluginWallpaperManager) Dependency.get(PluginWallpaperManager.class))).getWallpaperIndex();
                }
                bundle2.putInt("wallpaper_index", wallpaperIndex);
            } else if (c == 1) {
                int i6 = bundle.getInt(PluginLock.KEY_SCREEN);
                int i7 = bundle.getInt("wallpaper_type");
                int i8 = bundle.getInt("source_type");
                String string = bundle.getString("source");
                PluginWallpaperManagerImpl pluginWallpaperManagerImpl2 = (PluginWallpaperManagerImpl) ((PluginWallpaperManager) Dependency.get(PluginWallpaperManager.class));
                PluginLockMediator pluginLockMediator = pluginWallpaperManagerImpl2.mMediator;
                if (pluginLockMediator != null) {
                    LogUtil.m223d("PluginWallpaperManagerImpl", AbstractC0000x2c234b15.m0m("fillWallpaperData screen:", i6), new Object[0]);
                    PluginLockWallpaper pluginLockWallpaper = ((PluginLockMediatorImpl) pluginLockMediator).mLockWallpaper;
                    if (pluginLockWallpaper != null) {
                        Context context = pluginWallpaperManagerImpl2.mContext;
                        if (i6 != 1 || !PluginLockWallpaper.isCloneDisplayRequired()) {
                            PluginLockWallpaper.PluginLockWallpaperData pluginLockWallpaperData = (PluginLockWallpaper.PluginLockWallpaperData) ((ArrayList) pluginLockWallpaper.mWallpaperDataList).get(i6);
                            boolean z2 = (pluginLockWallpaperData.mType == -2 || (pluginLockWallpaperData.mBitmap == null && pluginLockWallpaperData.mPath == null && pluginLockWallpaperData.mUri == null)) ? false : true;
                            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("fillData() screen:", i6, ", wallpaperType:", i7, ", sourceType:");
                            m45m.append(i8);
                            m45m.append(",source:");
                            m45m.append(string);
                            m45m.append(", hasData:");
                            ActionBarContextView$$ExternalSyntheticOutline0.m9m(m45m, z2, "PluginLockWallpaper");
                            if (!z2) {
                                pluginLockWallpaperData.mType = i7;
                                if (i8 == 0) {
                                    pluginLockWallpaperData.mPath = null;
                                    pluginLockWallpaperData.mBitmap = null;
                                    pluginLockWallpaperData.mUri = null;
                                    pluginLockWallpaperData.mResourceId = 0;
                                } else if (i8 != 1) {
                                    pluginLockWallpaperData.resetAll();
                                } else {
                                    try {
                                        pluginLockWallpaperData.mPath = null;
                                        pluginLockWallpaperData.mBitmap = null;
                                        pluginLockWallpaperData.mUri = null;
                                        int parseInt2 = Integer.parseInt(string);
                                        if (pluginLockWallpaperData.mResourceId != parseInt2 || pluginLockWallpaperData.mBitmap == null) {
                                            Bitmap bitmap = PluginLockWallpaper.getBitmap(parseInt2, context.getResources());
                                            pluginLockWallpaperData.mPath = null;
                                            pluginLockWallpaperData.mBitmap = bitmap;
                                            pluginLockWallpaperData.mUri = null;
                                            if (bitmap == null) {
                                                pluginLockWallpaperData.mResourceId = 0;
                                            }
                                            pluginLockWallpaperData.mResourceId = parseInt2;
                                        }
                                    } catch (Exception e3) {
                                        AbstractC0790xf8f53ce8.m80m(e3, new StringBuilder("couldn't load bitmap:"), "PluginLockWallpaper");
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (c == 2) {
                LogUtil.m223d("PluginLockProvider", "call method:".concat(str), new Object[0]);
                PluginLockManagerImpl pluginLockManagerImpl = (PluginLockManagerImpl) pluginLockManager;
                LogUtil.m223d("PluginLockManagerImpl", "removeShortcutTaskListener", new Object[0]);
                PluginLockShortcutDnd pluginLockShortcutDnd = pluginLockManagerImpl.mTaskDnd;
                if (pluginLockShortcutDnd != null) {
                    ((ZenModeControllerImpl) pluginLockShortcutDnd.mZenModeController).removeCallback(pluginLockShortcutDnd);
                    pluginLockManagerImpl.mTaskDnd = null;
                }
                PluginLockShortcutFlashLight pluginLockShortcutFlashLight2 = pluginLockManagerImpl.mTaskFlashLight;
                if (pluginLockShortcutFlashLight2 != null) {
                    FlashlightControllerImpl flashlightControllerImpl = (FlashlightControllerImpl) pluginLockShortcutFlashLight2.mFlashlightController;
                    synchronized (flashlightControllerImpl.mListeners) {
                        flashlightControllerImpl.cleanUpListenersLocked(pluginLockShortcutFlashLight2);
                    }
                    pluginLockManagerImpl.mTaskFlashLight = null;
                }
            } else if (c == 3) {
                PluginLockManagerImpl pluginLockManagerImpl2 = (PluginLockManagerImpl) pluginLockManager;
                if (str2.equals("Dnd")) {
                    PluginLockShortcutDnd pluginLockShortcutDnd2 = pluginLockManagerImpl2.mTaskDnd;
                    if (pluginLockShortcutDnd2 != null) {
                        LogUtil.m223d("PluginLockShortcutDnd", "excute", new Object[0]);
                        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) pluginLockShortcutDnd2.mZenModeController;
                        zenModeControllerImpl.setZen(zenModeControllerImpl.mZenMode == 1 ? 0 : 1, null, "Keyguard");
                    }
                } else if (str2.equals("Flashlight") && (pluginLockShortcutFlashLight = pluginLockManagerImpl2.mTaskFlashLight) != null) {
                    LogUtil.m223d("PluginLockShortcutFlashLight", "excute()", new Object[0]);
                    ((FlashlightControllerImpl) pluginLockShortcutFlashLight.mFlashlightController).setFlashlight(!r0.isEnabled());
                }
            } else if (c == 4) {
                PluginLockManagerImpl pluginLockManagerImpl3 = (PluginLockManagerImpl) pluginLockManager;
                boolean equals = str2.equals("Dnd");
                PluginLockMediator pluginLockMediator2 = pluginLockManagerImpl3.mMediator;
                Context context2 = pluginLockManagerImpl3.mContext;
                if (equals) {
                    if (pluginLockManagerImpl3.mTaskDnd == null) {
                        pluginLockManagerImpl3.mTaskDnd = new PluginLockShortcutDnd(context2, pluginLockMediator2);
                    }
                    if (((ZenModeControllerImpl) pluginLockManagerImpl3.mTaskDnd.mZenModeController).mZenMode == 1) {
                        isEnabled = true;
                    }
                    isEnabled = false;
                } else {
                    if (str2.equals("Flashlight")) {
                        if (pluginLockManagerImpl3.mTaskFlashLight == null) {
                            pluginLockManagerImpl3.mTaskFlashLight = new PluginLockShortcutFlashLight(context2, pluginLockMediator2);
                        }
                        isEnabled = ((FlashlightControllerImpl) pluginLockManagerImpl3.mTaskFlashLight.mFlashlightController).isEnabled();
                        LogUtil.m223d("PluginLockShortcutFlashLight", AbstractC0866xb1ce8deb.m86m("isEnabled [isEnabled] ", isEnabled), new Object[0]);
                    }
                    isEnabled = false;
                }
                LogUtil.m223d("PluginLockManagerImpl", "getShortcutTaskState [taskName] " + str2 + ",[isEnable] " + isEnabled, new Object[0]);
                Bundle bundle3 = new Bundle();
                bundle3.putBoolean("isEnable", isEnabled);
                bundle2.putString("action", str);
                bundle2.putString("arg", str2);
                bundle2.putBundle("extras", bundle3);
                onEventReceived(pluginLockManager, bundle2);
            } else if (c != 5) {
                bundle2.putString("action", str);
                bundle2.putString("arg", str2);
                bundle2.putBundle("extras", bundle);
                LogUtil.m223d("PluginLockProvider", "call bundle:" + bundle2.toString(), new Object[0]);
                LogUtil.m223d("PluginLockProvider", "call method:".concat(str), new Object[0]);
                LogUtil.m223d("PluginLockProvider", "call arg:" + str2, new Object[0]);
                onEventReceived(pluginLockManager, bundle2);
            } else {
                Log.d("PluginLockProvider", "call: GET_DLS_DATA");
                PluginLockMediatorImpl pluginLockMediatorImpl = (PluginLockMediatorImpl) ((PluginLockManagerImpl) ((PluginLockManager) Dependency.get(PluginLockManager.class))).mMediator;
                pluginLockMediatorImpl.getClass();
                DynamicLockData dynamicLockData = new DynamicLockData();
                Bundle bundle4 = new Bundle();
                Bundle bundle5 = new Bundle();
                Bundle bundle6 = new Bundle();
                Bundle bundle7 = new Bundle();
                Bundle bundle8 = new Bundle();
                int i9 = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) pluginLockMediatorImpl.mStateListenerList;
                    if (i9 < arrayList.size()) {
                        WeakReference weakReference = (WeakReference) arrayList.get(i9);
                        if (weakReference != null) {
                            PluginLockListener$State pluginLockListener$State = (PluginLockListener$State) weakReference.get();
                            Log.d("PluginLockMediatorImpl", "getDynamicLockData() listener: " + pluginLockListener$State);
                            if (pluginLockListener$State != null) {
                                if (pluginLockListener$State instanceof NotificationPanelViewController) {
                                    bundle4 = pluginLockListener$State.onUiInfoRequested(false);
                                    bundle7 = pluginLockListener$State.onUiInfoRequested(true);
                                    Log.d("PluginLockMediatorImpl", "getDynamicLockData() bottom: " + bundle4.toString());
                                    Log.d("PluginLockMediatorImpl", "getDynamicLockData() bottom_land: " + bundle7.toString());
                                } else if (pluginLockListener$State instanceof FaceWidgetPluginLockManagerWrapper.FaceWidgetLockStarStateCallbackWrapper) {
                                    bundle5 = pluginLockListener$State.onUiInfoRequested(true);
                                    Log.d("PluginLockMediatorImpl", "getDynamicLockData() faceWidget: " + bundle5.toString());
                                } else if (pluginLockListener$State instanceof SecLockIconViewController) {
                                    bundle6 = pluginLockListener$State.onUiInfoRequested(false);
                                    bundle8 = pluginLockListener$State.onUiInfoRequested(true);
                                    Log.d("PluginLockMediatorImpl", "getDynamicLockData() lockIcon: " + bundle6.toString());
                                    Log.d("PluginLockMediatorImpl", "getDynamicLockData() lockIcon_land: " + bundle8.toString());
                                }
                            }
                        }
                        i9++;
                    } else {
                        ShortcutData shortcutData = dynamicLockData.getShortcutData();
                        shortcutData.setShortcutInfo(bundle4.getString("shortcut_info"));
                        shortcutData.setVisibility(Integer.valueOf(bundle4.getInt("shortcut_enable")));
                        shortcutData.setPaddingBottom(Integer.valueOf(bundle4.getInt("shortcut_bottom")));
                        shortcutData.setPaddingSide(Integer.valueOf(bundle4.getInt("shortcut_side")));
                        shortcutData.setPaddingBottomLand(Integer.valueOf(bundle7.getInt("shortcut_bottom")));
                        shortcutData.setPaddingSideLand(Integer.valueOf(bundle7.getInt("shortcut_side")));
                        shortcutData.setImageSize(Integer.valueOf(bundle4.getInt("shortcut_size")));
                        FingerPrintData fingerPrintData = dynamicLockData.getFingerPrintData();
                        fingerPrintData.setHeight(Integer.valueOf(bundle4.getInt("finger_print_height")));
                        fingerPrintData.setImageSize(Integer.valueOf(bundle4.getInt("finger_print_image_size")));
                        fingerPrintData.setPaddingBottom(Integer.valueOf(bundle4.getInt("finger_print_margin")));
                        fingerPrintData.setEnabled(Boolean.valueOf(bundle4.getBoolean("finger_print_enabled")));
                        NotificationData notificationData = dynamicLockData.getNotificationData();
                        int i10 = bundle5.getInt("nio_gravity", 17);
                        int i11 = bundle5.getInt("nio_gravity_land", 17);
                        int i12 = bundle4.getInt("noti_type");
                        notificationData.setNotiType(Integer.valueOf(i12 + 1));
                        notificationData.setVisibility(Integer.valueOf(bundle4.getInt("noti_visibility")));
                        notificationData.getCardData().setTopY(Integer.valueOf(bundle4.getInt("noti_top")));
                        notificationData.getCardData().setTopYLand(Integer.valueOf(bundle7.getInt("noti_top")));
                        if (i12 == 0) {
                            notificationData.getCardData().setNotiCardNumbers(Integer.valueOf(bundle4.getInt("noti_number")));
                        }
                        notificationData.getIconOnlyData().setGravity(Integer.valueOf(i10));
                        if (i10 == 8388611) {
                            i4 = 0;
                            notificationData.getIconOnlyData().setPaddingStart(Integer.valueOf(bundle5.getInt("nio_start", 0)));
                        } else if (i10 == 8388613) {
                            i4 = 0;
                            notificationData.getIconOnlyData().setPaddingEnd(Integer.valueOf(bundle5.getInt("nio_start", 0)));
                        } else {
                            i4 = 0;
                        }
                        notificationData.getIconOnlyData().setTopY(Integer.valueOf(bundle4.getInt("noti_top", i4)));
                        notificationData.getIconOnlyData().setTopYLand(Integer.valueOf(bundle7.getInt("noti_top", i4)));
                        notificationData.getIconOnlyData().setGravityLand(Integer.valueOf(i11));
                        if (i11 == 8388611) {
                            notificationData.getIconOnlyData().setPaddingStartLand(Integer.valueOf(bundle5.getInt("nio_start_land", i4)));
                        } else if (i11 == 8388613) {
                            notificationData.getIconOnlyData().setPaddingEndLand(Integer.valueOf(bundle5.getInt("nio_start_land", i4)));
                        }
                        if (i12 == 0) {
                            notificationData.getCardData().setNotiCardNumbersLand(Integer.valueOf(bundle7.getInt("noti_number")));
                        }
                        IndicationData.HelpTextData helpTextData = dynamicLockData.getIndicationData().getHelpTextData();
                        helpTextData.setHeight(Integer.valueOf(bundle4.getInt("help_text_height", 0)));
                        helpTextData.setPaddingBottom(Integer.valueOf(bundle4.getInt("help_text_margin", 0)));
                        helpTextData.setVisibility(Integer.valueOf(bundle4.getInt("help_text_visibility", 0)));
                        helpTextData.setPaddingBottomLand(Integer.valueOf(bundle7.getInt("help_text_margin", 0)));
                        helpTextData.setVisibilityLand(Integer.valueOf(bundle7.getInt("help_text_visibility", 0)));
                        ServiceBoxData serviceBoxData = dynamicLockData.getServiceBoxData();
                        serviceBoxData.getClockInfo().setClockType(bundle5.getInt("clock_type", 2));
                        serviceBoxData.getClockInfo().setGravity(Integer.valueOf(bundle5.getInt("clock_gravity", 17)));
                        serviceBoxData.setVisibility(Integer.valueOf(bundle5.getInt("clock_visibility", 0)));
                        serviceBoxData.getClockInfo().setScale(bundle5.getFloat("clock_scale", 1.0f));
                        serviceBoxData.getClockInfo().setPaddingStart(Integer.valueOf(bundle5.getInt("clock_side_padding", 0)));
                        serviceBoxData.getClockInfo().setPaddingEnd(Integer.valueOf(bundle5.getInt("clock_side_padding", 0)));
                        serviceBoxData.setTopY(Integer.valueOf(bundle5.getInt("clock_top", 0)));
                        serviceBoxData.setBottomY(Integer.valueOf(bundle5.getInt("clock_bottom", 0)));
                        serviceBoxData.getClockInfo().setGravityLand(Integer.valueOf(bundle5.getInt("clock_gravity_land", 17)));
                        serviceBoxData.setVisibilityLand(Integer.valueOf(bundle5.getInt("clock_visibility_land", 0)));
                        serviceBoxData.getClockInfo().setScaleLand(bundle5.getFloat("clock_scale_land", 1.0f));
                        serviceBoxData.getClockInfo().setPaddingStartLand(Integer.valueOf(bundle5.getInt("clock_side_padding_land", 0)));
                        serviceBoxData.getClockInfo().setPaddingEndLand(Integer.valueOf(bundle5.getInt("clock_side_padding_land", 0)));
                        serviceBoxData.setTopYLand(Integer.valueOf(bundle5.getInt("clock_top_land", 0)));
                        serviceBoxData.setBottomYLand(Integer.valueOf(bundle5.getInt("clock_bottom_land", 0)));
                        MusicData musicData = dynamicLockData.getMusicData();
                        musicData.setTopY(Integer.valueOf(bundle5.getInt("music_top", 0)));
                        musicData.setHeight(Integer.valueOf(bundle5.getInt("music_height", 0)));
                        musicData.setWidth(Integer.valueOf(bundle5.getInt("music_width", 0)));
                        musicData.setPaddingStart(Integer.valueOf(bundle5.getInt("clock_side_padding", 0)));
                        musicData.setPaddingEnd(Integer.valueOf(bundle5.getInt("clock_side_padding", 0)));
                        musicData.setGravity(Integer.valueOf(bundle5.getInt("music_gravity", 17)));
                        musicData.setVisibility(Integer.valueOf(bundle5.getInt("music_visibility", 0)));
                        musicData.setTopYLand(Integer.valueOf(bundle5.getInt("music_top_land", 0)));
                        musicData.setHeightLand(Integer.valueOf(bundle5.getInt("music_height_land", 0)));
                        musicData.setWidthLand(Integer.valueOf(bundle5.getInt("music_width_land", 0)));
                        musicData.setPaddingStartLand(Integer.valueOf(bundle5.getInt("clock_side_padding_land", 0)));
                        musicData.setPaddingEndLand(Integer.valueOf(bundle5.getInt("clock_side_padding_land", 0)));
                        musicData.setGravityLand(Integer.valueOf(bundle5.getInt("music_gravity_land", 17)));
                        musicData.setVisibilityLand(Integer.valueOf(bundle5.getInt("music_visibility_land", 0)));
                        dynamicLockData.getIndicationData().getLockIconData().setVisibility(Integer.valueOf(bundle6.getInt("lock_icon_visibility", 0)));
                        dynamicLockData.getIndicationData().getLockIconData().setVisibilityLand(Integer.valueOf(bundle8.getInt("lock_icon_visibility", 0)));
                        Log.d("PluginLockMediatorImpl", "getDynamicLockData() dlsData: " + new Gson().toJson(dynamicLockData));
                        bundle2.putString("dynamicLockData", new Gson().toJson(dynamicLockData));
                    }
                }
            }
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
