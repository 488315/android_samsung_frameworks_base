package com.android.systemui.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Point;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.math.MathUtils;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.QpRune;
import com.android.systemui.Rune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.edgelighting.Feature;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SettingsHelper implements Dumpable {
    public final Context mContext;
    public final ContentResolver mResolver;
    public final ArrayMap mCallbacks = new ArrayMap();
    public final ItemMap mItemLists = new ItemMap(this, 0);
    public final C35831 mSettingsObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.systemui.util.SettingsHelper.1
        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            onChange(z);
            if (uri == null) {
                return;
            }
            long uptimeMillis = SystemClock.uptimeMillis();
            SettingsHelper settingsHelper = SettingsHelper.this;
            ItemMap itemMap = settingsHelper.mItemLists;
            ContentResolver contentResolver = settingsHelper.mResolver;
            ConcurrentHashMap concurrentHashMap = itemMap.mMap;
            for (String str : concurrentHashMap.keySet()) {
                if (uri.equals(((Item) concurrentHashMap.get(str)).mUri)) {
                    ((Item) concurrentHashMap.get(str)).mCachedIntegrity = false;
                    ((Item) concurrentHashMap.get(str)).read(contentResolver);
                }
            }
            Log.d("SettingsHelper", "onChange() COMPLETED elapsed= " + (SystemClock.uptimeMillis() - uptimeMillis));
            SettingsHelper.this.broadcastChange(uri);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Item {
        public boolean mCachedIntegrity;
        public final String mDataType;
        public final Object mDef;
        public float mFloatValue;
        public final String mForUser;
        public int mIntValue;
        public final boolean mIsUserAll;
        public final String mKey;
        public final String mSettingType;
        public String mStringValue;
        public final Uri mUri;

        public Item(SettingsHelper settingsHelper, String str, String str2, String str3, Object obj, boolean z) {
            this(str, str2, str3, obj, z, false);
        }

        public final int getIntValue() {
            ContentResolver contentResolver;
            if (!this.mCachedIntegrity && (contentResolver = SettingsHelper.this.mResolver) != null) {
                read(contentResolver);
            }
            return this.mIntValue;
        }

        public final String getStringValue() {
            ContentResolver contentResolver;
            if (!this.mCachedIntegrity && (contentResolver = SettingsHelper.this.mResolver) != null) {
                read(contentResolver);
            }
            return this.mStringValue;
        }

        public final Uri getUri(String str) {
            Uri uri = this.mUri;
            if (uri != null) {
                return uri;
            }
            try {
                Class<?> cls = Class.forName("android.provider.Settings$" + this.mSettingType);
                return (Uri) cls.getDeclaredMethod("getUriFor", String.class).invoke(cls, str);
            } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                Log.e("SettingsHelper", "Exception occurred", e);
                return null;
            }
        }

        public final void read(ContentResolver contentResolver) {
            String str = this.mForUser;
            try {
                Class<?> cls = Class.forName("android.provider.Settings$" + this.mSettingType);
                boolean equals = "ForUser".equals(str);
                Class<String> cls2 = String.class;
                Object obj = this.mDef;
                String str2 = this.mKey;
                String str3 = this.mDataType;
                if (equals) {
                    if (obj != null && !"String".equals(str3)) {
                        String str4 = "get" + str3 + str;
                        Class<?>[] clsArr = new Class[4];
                        clsArr[0] = ContentResolver.class;
                        clsArr[1] = cls2;
                        if (str3.equals("Int")) {
                            cls2 = Integer.TYPE;
                        } else if (str3.equals("Float")) {
                            cls2 = Float.TYPE;
                        }
                        clsArr[2] = cls2;
                        clsArr[3] = Integer.TYPE;
                        Method declaredMethod = cls.getDeclaredMethod(str4, clsArr);
                        if ("Int".equals(str3)) {
                            this.mIntValue = ((Integer) declaredMethod.invoke(cls, contentResolver, str2, obj, -2)).intValue();
                        } else if ("Float".equals(str3)) {
                            this.mFloatValue = ((Float) declaredMethod.invoke(cls, contentResolver, str2, obj, -2)).floatValue();
                        }
                    }
                    Method declaredMethod2 = cls.getDeclaredMethod("get" + str3 + str, ContentResolver.class, cls2, Integer.TYPE);
                    if ("Int".equals(str3)) {
                        this.mIntValue = ((Integer) declaredMethod2.invoke(cls, contentResolver, str2, -2)).intValue();
                    } else if ("String".equals(str3)) {
                        this.mStringValue = (String) declaredMethod2.invoke(cls, contentResolver, str2, -2);
                    } else if ("Float".equals(str3)) {
                        this.mFloatValue = ((Float) declaredMethod2.invoke(cls, contentResolver, str2, -2)).floatValue();
                    }
                } else {
                    if (obj != null && !"String".equals(str3)) {
                        String str5 = "get" + str3;
                        Class<?>[] clsArr2 = new Class[3];
                        clsArr2[0] = ContentResolver.class;
                        clsArr2[1] = cls2;
                        if (str3.equals("Int")) {
                            cls2 = Integer.TYPE;
                        } else if (str3.equals("Float")) {
                            cls2 = Float.TYPE;
                        }
                        clsArr2[2] = cls2;
                        Method declaredMethod3 = cls.getDeclaredMethod(str5, clsArr2);
                        if ("Int".equals(str3)) {
                            this.mIntValue = ((Integer) declaredMethod3.invoke(cls, contentResolver, str2, obj)).intValue();
                        } else if ("Float".equals(str3)) {
                            this.mFloatValue = ((Float) declaredMethod3.invoke(cls, contentResolver, str2, obj)).floatValue();
                        }
                    }
                    Method declaredMethod4 = cls.getDeclaredMethod("get" + str3, ContentResolver.class, cls2);
                    if ("Int".equals(str3)) {
                        this.mIntValue = ((Integer) declaredMethod4.invoke(cls, contentResolver, str2)).intValue();
                    } else if ("String".equals(str3)) {
                        this.mStringValue = (String) declaredMethod4.invoke(cls, contentResolver, str2);
                    } else if ("Float".equals(str3)) {
                        this.mFloatValue = ((Float) declaredMethod4.invoke(cls, contentResolver, str2)).floatValue();
                    }
                }
                this.mCachedIntegrity = true;
            } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                Log.e("SettingsHelper", "Exception occurred", e);
            }
        }

        public Item(String str, String str2, String str3, Object obj, boolean z, boolean z2) {
            this.mUri = null;
            this.mCachedIntegrity = false;
            this.mSettingType = str;
            this.mKey = str2;
            this.mDataType = str3;
            this.mDef = obj;
            this.mForUser = z ? "ForUser" : "";
            this.mIsUserAll = z2;
            if (!"Global".equals(str) && !"Secure".equals(str) && !"System".equals(str)) {
                throw new IllegalArgumentException("Invalid setting type");
            }
            if (str2 == null || str2.isEmpty()) {
                throw new IllegalArgumentException("Invalid setting key");
            }
            if (!"Int".equals(str3) && !"String".equals(str3) && !"Float".equals(str3)) {
                throw new IllegalArgumentException("Invalid data type");
            }
            this.mUri = getUri(str2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ItemMap {
        public final ConcurrentHashMap mMap;

        public /* synthetic */ ItemMap(SettingsHelper settingsHelper, int i) {
            this(settingsHelper);
        }

        public final void add(Item item) {
            ConcurrentHashMap concurrentHashMap = this.mMap;
            String str = item.mKey;
            if (!concurrentHashMap.containsKey(str)) {
                concurrentHashMap.put(str, item);
                return;
            }
            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("HashMap CollisionException!! Please don't add same setting uri!!! NewKey:", str, ", OriKey:");
            m4m.append(((Item) concurrentHashMap.get(str)).mKey);
            Log.e("SettingsHelper", m4m.toString());
        }

        public final Item get(String str) {
            return (Item) this.mMap.get(str);
        }

        private ItemMap(SettingsHelper settingsHelper) {
            this.mMap = new ConcurrentHashMap();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnChangedCallback {
        void onChanged(Uri uri);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.util.SettingsHelper$1] */
    public SettingsHelper(Context context, DumpManager dumpManager) {
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        setUpSettingsItem();
        Thread thread = new Thread(new SettingsHelper$$ExternalSyntheticLambda0(this, 0), "SettingsHelper");
        thread.setPriority(10);
        thread.start();
        dumpManager.registerNsDumpable("SettingsHelper", this);
    }

    public static int getDefaultScreenTransitionEffect() {
        int semGetTransitionEffectValue = FingerprintManager.semGetTransitionEffectValue();
        return semGetTransitionEffectValue != -1 ? semGetTransitionEffectValue : SemCscFeature.getInstance().getBoolean("CscFeature_LockScreen_DisableUnlockVI") ? 0 : 1;
    }

    public static boolean hasPackage(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 128);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("SettingsHelper", "Package not found : ".concat(str));
            return false;
        }
    }

    public final void broadcastChange(Uri uri) {
        ArrayList arrayList;
        Objects.toString(uri);
        synchronized (this) {
            arrayList = (ArrayList) this.mCallbacks.get(uri);
        }
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                OnChangedCallback onChangedCallback = (OnChangedCallback) ((WeakReference) arrayList.get(i)).get();
                if (onChangedCallback != null) {
                    onChangedCallback.onChanged(uri);
                }
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ContentResolver contentResolver;
        printWriter.println("SettingsHelper state:");
        ConcurrentHashMap concurrentHashMap = this.mItemLists.mMap;
        for (String str : concurrentHashMap.keySet()) {
            StringBuilder sb = new StringBuilder("    ");
            Item item = (Item) concurrentHashMap.get(str);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(item.mKey);
            sb2.append(" = ");
            String str2 = item.mDataType;
            if ("Int".equals(str2)) {
                sb2.append(item.getIntValue());
            } else if ("String".equals(str2)) {
                sb2.append(item.getStringValue());
            } else if ("Float".equals(str2)) {
                if (!item.mCachedIntegrity && (contentResolver = SettingsHelper.this.mResolver) != null) {
                    item.read(contentResolver);
                }
                sb2.append(item.mFloatValue);
            }
            sb.append(sb2.toString());
            printWriter.println(sb.toString());
        }
        printWriter.println();
    }

    public final String getActiveThemePackage() {
        return this.mItemLists.get("current_sec_active_themepackage").getStringValue();
    }

    public final int getBlueLightFilterMode(String str) {
        boolean equals = str.equals("blue_light_filter");
        ItemMap itemMap = this.mItemLists;
        if (equals) {
            return itemMap.get("blue_light_filter").getIntValue();
        }
        if (str.equals("blue_light_filter_opacity")) {
            return itemMap.get("blue_light_filter_opacity").getIntValue();
        }
        return 0;
    }

    public final int getBouncerOneHandPosition() {
        return this.mItemLists.get("bouncer_one_hand_position").getIntValue();
    }

    public final boolean getBudsEnable() {
        return this.mItemLists.get("buds_enable").getIntValue() == 1;
    }

    public final String getIconBlacklist() {
        return this.mItemLists.get("icon_blacklist").getStringValue();
    }

    public final int getLockNoticardOpacity() {
        int i = this.mContext.getResources().getConfiguration().uiMode & 48;
        ItemMap itemMap = this.mItemLists;
        return i == 32 ? itemMap.get("lock_noticard_opacity_dark_mode").getIntValue() : itemMap.get("lock_noticard_opacity").getIntValue();
    }

    public final int getLockscreenWallpaperTransparent() {
        return getLockscreenWallpaperTransparent(WallpaperUtils.isSubDisplay());
    }

    public final int getLockscreenWallpaperType(int i) {
        boolean z = (i & 60) == 16;
        ItemMap itemMap = this.mItemLists;
        return z ? itemMap.get("lockscreen_wallpaper_sub").getIntValue() : itemMap.get("lockscreen_wallpaper").getIntValue();
    }

    public final int getNavigationBarAlignPosition() {
        if (BasicRune.NAVBAR_MOVABLE_POSITION) {
            return this.mItemLists.get("navigationbar_key_position").getIntValue();
        }
        return 1;
    }

    public final int getPluginLockValue(int i) {
        return this.mItemLists.get((LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && i == 1) ? "plugin_lock_sub_enabled" : "lockstar_enabled").getIntValue();
    }

    public final int getRefreshRateMode(boolean z) {
        ItemMap itemMap = this.mItemLists;
        return z ? itemMap.get("refresh_rate_mode_cover").getIntValue() : itemMap.get("refresh_rate_mode").getIntValue();
    }

    public final float getTransitionAnimationScale() {
        String stringValue = this.mItemLists.get("transition_animation_scale").getStringValue();
        if (stringValue != null) {
            return MathUtils.clamp(Float.parseFloat(stringValue), 0.0f, 10.0f);
        }
        return 1.0f;
    }

    public final boolean hasTwoPhoneAccount() {
        return this.mItemLists.get("two_account").getIntValue() == 1;
    }

    public final boolean isAODEnabled() {
        return this.mItemLists.get("aod_mode").getIntValue() != 0;
    }

    public final boolean isAODShown() {
        return this.mItemLists.get("aod_show_state").getIntValue() == 1;
    }

    public final boolean isAdaptiveBluelight() {
        return this.mItemLists.get("blue_light_filter_adaptive_mode").getIntValue() == 1;
    }

    public final boolean isAdaptiveColorMode() {
        return this.mItemLists.get("lock_adaptive_color").getIntValue() != 0;
    }

    public final boolean isAirplaneModeOn() {
        return this.mItemLists.get("airplane_mode_on").getIntValue() != 0;
    }

    public final boolean isAllowPrivateNotificationsWhenUnsecure(int i) {
        Assert.isMainThread();
        if (!NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE || i != 0) {
            return false;
        }
        int intValue = this.mItemLists.get("lock_screen_allow_private_notifications_when_unsecure").getIntValue();
        return ((isEnabledFaceStayOnLock() && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.get(KeyguardStateController.class))).mFaceAuthEnabled) || ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).getUserHasTrust(i)) && intValue == 1;
    }

    public final boolean isAnimationRemoved() {
        ContentResolver contentResolver;
        Item item = this.mItemLists.get("animator_duration_scale");
        if (!item.mCachedIntegrity && (contentResolver = SettingsHelper.this.mResolver) != null) {
            item.read(contentResolver);
        }
        return item.mFloatValue == 0.0f;
    }

    public final boolean isApplyWallpaperThemeToNotif() {
        return this.mItemLists.get("notification_apply_wallpaper_theme").getIntValue() == 1;
    }

    public final boolean isAutomaticUnlockEnabled() {
        return this.mItemLists.get("automatic_unlock").getIntValue() == 1;
    }

    public final boolean isCallScreenRotationAllowed() {
        return this.mItemLists.get("call_auto_rotation").getIntValue() == 1;
    }

    public final boolean isCarrierLogoEnabled() {
        return this.mItemLists.get("status_bar_show_network_information").getIntValue() == 1;
    }

    public final boolean isColorThemeEnabled$1() {
        return this.mItemLists.get("wallpapertheme_state").getIntValue() == 1;
    }

    public final boolean isEasyModeOn() {
        return this.mItemLists.get("easy_mode_switch").getIntValue() != 1;
    }

    public final boolean isEmergencyMode() {
        return this.mItemLists.get("emergency_mode").getIntValue() == 1;
    }

    public final boolean isEnabledBiometricUnlockVI() {
        return this.mItemLists.get("screen_transition_effect").getIntValue() != 0;
    }

    public final boolean isEnabledFaceStayOnLock() {
        return this.mItemLists.get("face_stay_on_lock_screen").getIntValue() == 1;
    }

    public final boolean isEnabledWof() {
        return this.mItemLists.get("fingerprint_always_on").getIntValue() != 0;
    }

    public final boolean isExpandQsAtOnceEnabled() {
        return this.mItemLists.get("swipe_directly_to_quick_setting").getIntValue() == 1;
    }

    public final boolean isHapticFeedbackEnabled() {
        return this.mItemLists.get("haptic_feedback_enabled").getIntValue() != 0;
    }

    public final boolean isHomeScreenRotationAllowed() {
        return this.mItemLists.get("sehome_portrait_mode_only").getIntValue() == 0;
    }

    public final boolean isLiveWallpaperEnabled() {
        boolean z = (WallpaperUtils.sCurrentWhich & 60) == 16;
        ItemMap itemMap = this.mItemLists;
        return z ? itemMap.get("lockscreen_wallpaper_sub").getIntValue() == 0 : itemMap.get("lockscreen_wallpaper").getIntValue() == 0;
    }

    public final boolean isLockFunctionsEnabled() {
        return this.mItemLists.get("lock_function_val").getIntValue() != 0;
    }

    public final boolean isLockScreenRotationAllowed() {
        return LsRune.KEYGUARD_ALLOW_ROTATION || this.mItemLists.get("lock_screen_allow_rotation").getIntValue() == 1;
    }

    public final boolean isNavBarButtonOrderDefault() {
        return !BasicRune.NAVBAR_ENABLED || this.mItemLists.get("navigationbar_key_order").getIntValue() == 0;
    }

    public final boolean isNavigationBarGestureHintEnabled() {
        return (BasicRune.NAVBAR_GESTURE && this.mItemLists.get("navigation_bar_gesture_hint").getIntValue() == 0) ? false : true;
    }

    public final boolean isNavigationBarGestureWhileHidden() {
        return BasicRune.NAVBAR_GESTURE && this.mItemLists.get("navigation_bar_gesture_while_hidden").getIntValue() == 1;
    }

    public final boolean isNavigationBarHideKeyboardButtonEnabled() {
        return (BasicRune.NAVBAR_GESTURE && this.mItemLists.get("navigation_bar_button_to_hide_keyboard").getIntValue() == 0) ? false : true;
    }

    public final boolean isNavigationBarRotateSuggestionEnabled() {
        return !BasicRune.NAVBAR_ENABLED || this.mItemLists.get("navigation_bar_rotate_suggestion_enabled").getIntValue() == 1;
    }

    public final boolean isNotificationIconsOnlyOn() {
        return this.mItemLists.get("lockscreen_minimizing_notification").getIntValue() == 1;
    }

    public final boolean isOneHandModeRunning() {
        return this.mItemLists.get("any_screen_running").getIntValue() != 0;
    }

    public final boolean isOpenThemeLockWallpaper() {
        return isOpenThemeLook() && getLockscreenWallpaperTransparent() == 2;
    }

    public final boolean isOpenThemeLook() {
        return !TextUtils.isEmpty(getActiveThemePackage());
    }

    public final boolean isPowerSavingMode() {
        ItemMap itemMap = this.mItemLists;
        return (itemMap.get("powersaving_switch").getIntValue() == 1 && itemMap.get("psm_switch").getIntValue() == 1) || itemMap.get("low_power").getIntValue() == 1;
    }

    public final boolean isQSButtonGridPopupEnabled() {
        return this.mItemLists.get("quickstar_qs_tile_layout_custom_matrix").getIntValue() == 1;
    }

    public final boolean isReduceTransparencyEnabled() {
        return this.mItemLists.get("accessibility_reduce_transparency").getIntValue() != 0;
    }

    public final boolean isRotationLocked() {
        return this.mItemLists.get("accelerometer_rotation").getIntValue() == 0;
    }

    public final boolean isScreenOffMemoEnabled() {
        ItemMap itemMap = this.mItemLists;
        return (itemMap.get("action_memo_on_off_screen").getIntValue() == 0 && itemMap.get("screen_off_memo").getIntValue() == 0) ? false : true;
    }

    public final boolean isShowBatteryPercentInStatusBar() {
        return this.mItemLists.get("display_battery_percentage").getIntValue() != 0;
    }

    public final boolean isShowButtonBackground() {
        return this.mItemLists.get("show_button_background").getIntValue() == 1;
    }

    public final boolean isShowKeyboardButton() {
        return this.mItemLists.get("show_keyboard_button").getIntValue() != 0;
    }

    public final boolean isShowNotificationAppIconEnabled() {
        return this.mItemLists.get("show_notification_app_icon").getIntValue() == 1;
    }

    public final boolean isShowNotificationOnKeyguard() {
        return this.mItemLists.get("lock_screen_show_notifications").getIntValue() == 1;
    }

    public final boolean isSuggestResponsesEnabled() {
        return NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && this.mItemLists.get("suggestion_responses").getIntValue() != 0;
    }

    public final boolean isSupportTouchAndHoldToEdit() {
        return this.mItemLists.get("lock_editor_support_touch_hold").getIntValue() == 1;
    }

    public final boolean isTwoPhoneRegistered() {
        return this.mItemLists.get("two_register").getIntValue() == 1;
    }

    public final boolean isUltraPowerSavingMode() {
        ItemMap itemMap = this.mItemLists;
        return (itemMap.get("ultra_powersaving_mode").getIntValue() == 1) || itemMap.get("minimal_battery_use").getIntValue() == 1;
    }

    public final boolean isUserSwitcherSettingOn() {
        Point point = DeviceState.sDisplaySize;
        if (SystemProperties.getBoolean("debug.quick_mum_test_trigger", false)) {
            return true;
        }
        return Rune.SYSUI_MULTI_USER && this.mItemLists.get("user_switcher_enabled").getIntValue() == 1;
    }

    public final boolean isVoiceAssistantEnabled() {
        String stringValue = this.mItemLists.get("enabled_accessibility_services").getStringValue();
        if (TextUtils.isEmpty(stringValue)) {
            return false;
        }
        return stringValue.matches("(?i).*com.samsung.android.app.talkback.TalkBackService.*") || stringValue.matches("(?i).*com.samsung.android.marvin.talkback.TalkBackService.*");
    }

    public final boolean isWhiteKeyguardWallpaper() {
        return this.mItemLists.get("white_lockscreen_wallpaper").getIntValue() == 1;
    }

    public final void readSettingsDB() {
        long uptimeMillis = SystemClock.uptimeMillis();
        ConcurrentHashMap concurrentHashMap = this.mItemLists.mMap;
        Iterator it = concurrentHashMap.keySet().iterator();
        while (it.hasNext()) {
            ((Item) concurrentHashMap.get((String) it.next())).mCachedIntegrity = false;
        }
        for (String str : concurrentHashMap.keySet()) {
            if (!((Item) concurrentHashMap.get(str)).mCachedIntegrity) {
                ((Item) concurrentHashMap.get(str)).read(this.mResolver);
            }
        }
        Log.d("SettingsHelper", "readSettingsDB() COMPLETED elapsed= " + (SystemClock.uptimeMillis() - uptimeMillis));
    }

    public final void registerCallback(OnChangedCallback onChangedCallback, Uri... uriArr) {
        synchronized (this) {
            Objects.toString(onChangedCallback);
            int length = uriArr.length;
            WeakReference weakReference = new WeakReference(onChangedCallback);
            for (int i = 0; i < length; i++) {
                ArrayList arrayList = (ArrayList) this.mCallbacks.get(uriArr[i]);
                if (arrayList == null || !arrayList.contains(weakReference)) {
                    if (arrayList == null) {
                        this.mCallbacks.put(uriArr[i], new ArrayList());
                    }
                    ((ArrayList) this.mCallbacks.get(uriArr[i])).add(weakReference);
                } else {
                    Log.e("SettingsHelper", "Object tried to add another listener : " + Debug.getCallers(5));
                }
            }
        }
    }

    public final void registerSettingsObserver() {
        long uptimeMillis = SystemClock.uptimeMillis();
        ConcurrentHashMap concurrentHashMap = this.mItemLists.mMap;
        Iterator it = concurrentHashMap.keySet().iterator();
        while (it.hasNext()) {
            Item item = (Item) concurrentHashMap.get((String) it.next());
            SettingsHelper settingsHelper = SettingsHelper.this;
            settingsHelper.mResolver.registerContentObserver(item.getUri(item.mKey), false, settingsHelper.mSettingsObserver, item.mIsUserAll ? -1 : -2);
        }
        Log.d("SettingsHelper", "registerSettingsObserver() COMPLETED elapsed= " + (SystemClock.uptimeMillis() - uptimeMillis));
    }

    public final void resetShortcutValue() {
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        Boolean valueOf = Boolean.valueOf(DeviceType.isSupportPenDetachmentOption(context));
        Boolean valueOf2 = Boolean.valueOf(DeviceType.isTablet());
        String str = valueOf2.booleanValue() ? (valueOf.booleanValue() && hasPackage(context, "com.samsung.android.app.notes")) ? "com.samsung.android.app.notes/com.samsung.android.app.notes.memolist.MemoListActivity" : hasPackage(context, "com.sec.android.app.sbrowser") ? "com.sec.android.app.sbrowser/com.sec.android.app.sbrowser.SBrowserMainActivity" : "com.android.chrome/com.google.android.apps.chrome.Main" : "com.samsung.android.dialer/com.samsung.android.dialer.DialtactsActivity";
        String string = SemCscFeature.getInstance().getString("CscFeature_Setting_ConfigDefAppShortcutForLockScreen");
        if (TextUtils.isEmpty(string)) {
            string = PathParser$$ExternalSyntheticOutline0.m29m("1;", str, ";1;com.sec.android.app.camera/com.sec.android.app.camera.Camera;");
        } else {
            String[] split = string.split(";");
            if (split.length < 4) {
                Log.d("SettingsHelper", "shortcut cscfeature info have wrong format : ".concat(string));
                string = "1;" + str + ";1;com.sec.android.app.camera/com.sec.android.app.camera.Camera;";
            } else if (valueOf2.booleanValue() && "com.samsung.android.app.notes/com.samsung.android.app.notes.memolist.MemoListActivity".equals(split[3])) {
                StringBuilder sb = new StringBuilder("1;");
                sb.append(split[3]);
                sb.append(";1;");
                string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, split[1], ";");
            }
        }
        Settings.System.putStringForUser(contentResolver, "lock_application_shortcut", string, -2);
    }

    public final void setAdaptiveBluelight(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "blue_light_filter_adaptive_mode", i, -2);
        this.mItemLists.get("blue_light_filter_adaptive_mode").mIntValue = i;
    }

    public final void setAdaptiveColorMode(int i, boolean z) {
        ItemMap itemMap = this.mItemLists;
        Context context = this.mContext;
        if (z) {
            Settings.System.putIntForUser(context.getContentResolver(), "lock_adaptive_color_sub", i, -2);
            itemMap.get("lock_adaptive_color_sub").mIntValue = i;
        } else {
            Settings.System.putIntForUser(context.getContentResolver(), "lock_adaptive_color", i, -2);
            itemMap.get("lock_adaptive_color").mIntValue = i;
        }
    }

    public final void setAirplaneMode(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setAirplaneMode: ", z, "SettingsHelper");
        Settings.Global.putInt(this.mContext.getContentResolver(), "airplane_mode_on", z ? 1 : 0);
    }

    public final void setBlueLightFilterMode(int i, String str) {
        boolean equals = str.equals("blue_light_filter");
        ItemMap itemMap = this.mItemLists;
        Context context = this.mContext;
        if (equals) {
            Settings.System.putIntForUser(context.getContentResolver(), "blue_light_filter", i, -2);
            itemMap.get("blue_light_filter").mIntValue = i;
        } else if (str.equals("blue_light_filter_opacity")) {
            Settings.System.putIntForUser(context.getContentResolver(), "blue_light_filter_opacity", i, -2);
            itemMap.get("blue_light_filter_opacity").mIntValue = i;
        }
    }

    public final void setPluginLockValue(int i, int i2) {
        String str = (LsRune.PLUGIN_LOCK_MULTIPLE_ACTIVATION && i == 1) ? "plugin_lock_sub_enabled" : "lockstar_enabled";
        this.mItemLists.get(str).mIntValue = i2;
        Settings.System.putInt(this.mContext.getContentResolver(), str, i2);
    }

    public final void setScheduledBluelight(int i) {
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "blue_light_filter_scheduled", i, -2);
        this.mItemLists.get("blue_light_filter_scheduled").mIntValue = i;
    }

    public final void setSuggestResponsesUsed() {
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "suggestion_responses_used", 1);
            this.mItemLists.get("suggestion_responses_used").mIntValue = 1;
        }
    }

    public final void setUpSettingsItem() {
        Item item = new Item(this, "System", "white_lockscreen_statusbar", "Int", 0, true);
        ItemMap itemMap = this.mItemLists;
        itemMap.add(item);
        itemMap.add(new Item(this, "System", "accessibility_reduce_transparency", "Int", 0, true));
        itemMap.add(new Item(this, "System", "white_lockscreen_wallpaper", "Int", 0, true));
        itemMap.add(new Item(this, "System", "lockscreen_wallpaper", "Int", 1, true));
        itemMap.add(new Item(this, "System", "lockscreen_wallpaper_sub", "Int", 1, true));
        itemMap.add(new Item(this, "System", "lockscreen_wallpaper_transparent", "Int", 1, true));
        itemMap.add(new Item(this, "System", "sub_display_lockscreen_wallpaper_transparency", "Int", 1, true));
        itemMap.add(new Item(this, "System", "android.wallpaper.settings_systemui_transparency", "Int", 1, true));
        itemMap.add(new Item(this, "System", "sub_display_system_wallpaper_transparency", "Int", 1, true));
        itemMap.add(new Item(this, "System", "dualclock_menu_settings", "Int", 0, true));
        itemMap.add(new Item(this, "System", "sidesync_source_connect", "Int", 0, true));
        itemMap.add(new Item(this, "System", "automatic_unlock", "Int", 0, true));
        itemMap.add(new Item(this, "System", "theme_font_clock", "String", null, true));
        itemMap.add(new Item(this, "System", "theme_font_numeric", "String", null, true));
        itemMap.add(new Item(this, "System", "theme_font_system", "String", null, true));
        itemMap.add(new Item(this, "System", "homecity_timezone", "String", null, true));
        itemMap.add(new Item(this, "System", "current_sec_theme_package_open_theme", "String", null, false));
        itemMap.add(new Item(this, "System", "current_sec_appicon_theme_package", "String", null, false));
        itemMap.add(new Item(this, "System", "current_sec_active_themepackage", "String", null, false));
        itemMap.add(new Item(this, "Global", "white_cover_wallpaper", "Int", 0, false));
        itemMap.add(new Item(this, "System", "lock_application_shortcut", "String", 0, true));
        itemMap.add(new Item(this, "Global", "tap_to_icon", "Int", 0, false));
        itemMap.add(new Item(this, "System", "lock_fmm_Message", "String", null, false));
        itemMap.add(new Item(this, "System", "lock_fmm_phone", "String", null, false));
        itemMap.add(new Item(this, "System", "show_button_background", "Int", 0, true));
        itemMap.add(new Item(this, "System", "additional_information_val", "Int", 0, true));
        itemMap.add(new Item(this, "System", "add_info_music_control", "Int", 1, true));
        itemMap.add(new Item(this, "System", "add_info_today_schedule", "Int", 0, true));
        itemMap.add(new Item(this, "System", "add_info_alarm", "Int", 0, true));
        itemMap.add(new Item(this, "System", "servicebox_page_gravity", "Int", 0, true));
        itemMap.add(new Item(this, "System", "aod_servicebox_page_gravity", "Int", 0, true));
        itemMap.add(new Item(this, "System", "aod_mode", "Int", Integer.valueOf(LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE ? 1 : 0), true));
        itemMap.add(new Item(this, "Secure", "lock_function_val", "Int", 0, true));
        itemMap.add(new Item(this, "System", "white_lockscreen_navigationbar", "Int", 0, true));
        itemMap.add(new Item(this, "System", "face_widget_order", "String", null, true));
        itemMap.add(new Item(this, "Global", "lockscreen_aod_staying_music_page", "Int", 0, false));
        itemMap.add(new Item(this, "System", "reserve_battery_on", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "n_digits_pin_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "System", "voicecall_type", "Int", 1, true));
        itemMap.add(new Item(this, "Secure", "show_keyboard_button", "Int", 0, true));
        itemMap.add(new Item(this, "System", "voicecall_type2", "Int", 1, true));
        itemMap.add(new Item(this, "System", "lock_adaptive_color", "Int", 1, true));
        itemMap.add(new Item(this, "System", "lock_adaptive_color_sub", "Int", 0, true));
        itemMap.add(new Item(this, "System", "lock_clock_adaptive_colors", "String", null, true));
        itemMap.add(new Item(this, "Secure", "lock_screen_show_notifications", "Int", null, true));
        itemMap.add(new Item(this, "System", "lockscreen_minimizing_notification", "Int", 0, true));
        itemMap.add(new Item(this, "System", "notification_text_color_inversion", "Int", 1, true));
        itemMap.add(new Item(this, "Global", "mobile_data_question", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "notification_freeform_menuitem", "Int", 0, false));
        itemMap.add(new Item(this, "Secure", "isBikeMode", "Int", 0, false));
        itemMap.add(new Item(this, "System", "missing_phone_lock", "String", null, false));
        itemMap.add(new Item(this, "Secure", "display_cutout_hide_notch", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "enable_reserve_max_mode", "Int", 0, true));
        itemMap.add(new Item(this, "System", "covert_on", "Int", 0, false));
        itemMap.add(new Item(this, "System", "enable_fullscreen_user_switcher", "Int", -1, false));
        itemMap.add(new Item(this, "System", "display_battery_percentage", "Int", -1, true));
        itemMap.add(new Item(this, "System", "blue_light_filter", "Int", 0, true));
        itemMap.add(new Item(this, "System", "blue_light_filter_opacity", "Int", 5, true));
        itemMap.add(new Item(this, "System", "blue_light_filter_adaptive_mode", "Int", 1, true));
        itemMap.add(new Item(this, "System", "blue_light_filter_scheduled", "Int", 1, true));
        itemMap.add(new Item(this, "System", "blue_light_filter_type", "Int", 0, true));
        itemMap.add(new Item(this, "System", "greyscale_mode", "Int", 0, true));
        itemMap.add(new Item(this, "System", "high_contrast", "Int", 0, true));
        itemMap.add(new Item(this, "System", "color_blind", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "color_lens_switch", "Int", 0, true));
        itemMap.add(new Item(this, "System", "blue_light_filter_night_dim", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "reduce_bright_colors_activated", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "accessibility_display_inversion_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "accessibility_display_daltonizer_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "high_text_contrast_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "System", "wallpapertheme_color_isgray", "Int", 0, true));
        itemMap.add(new Item(this, "System", "show_notification_app_icon", "Int", 0, true));
        boolean z = Rune.SYSUI_MULTI_USER;
        Context context = this.mContext;
        if (z) {
            itemMap.add(new Item(this, "Global", "user_switcher_enabled", "Int", Integer.valueOf(UserManager.get(context).isUserSwitcherEnabled() ? 1 : 0), false));
        }
        itemMap.add(new Item(this, "Global", "swipe_directly_to_quick_setting", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "swipe_directly_to_quick_setting_area", "Int", -1, false));
        itemMap.add(new Item(this, "Global", "swipe_directly_to_quick_setting_position", "String", null, false));
        if (QpRune.QUICK_STYLE_ALTERNATE_CALENDAR) {
            itemMap.add(new Item(this, "System", "aodlock_support_lunar", "Int", 1, false));
            itemMap.add(new Item(this, "System", "aodlock_support_hijri", "Int", 0, false));
        }
        itemMap.add(new Item(this, "Global", "mode_ringer_time_on", "Int", 0, false));
        itemMap.add(new Item(this, "System", "ultra_powersaving_mode", "Int", 0, true));
        itemMap.add(new Item(this, "System", "minimal_battery_use", "Int", 0, true));
        itemMap.add(new Item(this, "System", "emergency_mode", "Int", 0, true));
        itemMap.add(new Item(this, "System", "Flashlight_brightness_level", "Int", Integer.valueOf(VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI), true));
        itemMap.add(new Item(this, "System", "flashlight_sos_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "Global", "protect_battery", "Int", 0, false));
        itemMap.add(new Item(this, "System", "powersaving_switch", "Int", 0, true));
        itemMap.add(new Item(this, "System", "psm_switch", "Int", 0, true));
        itemMap.add(new Item(this, "Global", "low_power", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "sehome_portrait_mode_only", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "call_auto_rotation", "Int", 0, false));
        itemMap.add(new Item(this, "System", "accelerometer_rotation", "Int", 0, true));
        boolean z2 = QpRune.QUICK_SETTINGS_SUBSCREEN;
        if (z2) {
            itemMap.add(new Item(this, "System", "sub_screen_brightness", "Int", 73, false));
        }
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            itemMap.add(new Item(this, "System", "sub_screen_brightness_mode", "Int", 0, false));
            itemMap.add(new Item(this, "System", "shown_max_brightness_dialog", "Int", 0, false));
            itemMap.add(new Item(this, "System", "auto_brightness_transition_time", "Int", -1, true));
        }
        itemMap.add(new Item(this, "System", "mic_mode_enable", "Int", 0, false));
        itemMap.add(new Item(this, "System", "mic_mode_effect", "Int", 0, false));
        itemMap.add(new Item(this, "System", "mic_mode_wificall", "Int", 0, false));
        itemMap.add(new Item(this, "System", "voip_translator_enable", "Int", 0, false));
        itemMap.add(new Item(this, "System", "easy_mode_switch", "Int", 1, true));
        itemMap.add(new Item(this, "System", "lock_shortcut_type", "String", null, true));
        itemMap.add(new Item(this, "System", "lockscreen_show_shortcut", "Int", 1, true));
        itemMap.add(new Item(this, "System", "set_shortcuts_mode", "Int", 1, false));
        itemMap.add(new Item(this, "System", "lock_editor_support_touch_hold", "Int", 1, true));
        itemMap.add(new Item(this, "System", "remove_animations", "Int", 0, true));
        itemMap.add(new Item(this, "System", "any_screen_running", "Int", 0, true));
        if (BasicRune.STATUS_REAL_TIME_NETWORK_SPEED) {
            itemMap.add(new Item(this, "System", "network_speed", "Int", 0, true));
        }
        if (BasicRune.STATUS_LAYOUT_SHOW_DATE) {
            itemMap.add(new Item(this, "System", "status_bar_show_date", "Int", 1, true));
        }
        itemMap.add(new Item(this, "System", "mptcp_value_internal", "Int", 0, true));
        itemMap.add(new Item(this, "Global", "mobile_data", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "data_roaming", "Int", 0, false));
        itemMap.add(new Item(this, "System", "status_bar_show_network_information", "Int", 1, true));
        itemMap.add(new Item(this, "Global", "two_register", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "two_account", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "two_call_enabled", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "two_sms_enabled", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "phone1_on", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "phone2_on", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "fill_udc_display_cutout", "Int", 0, false));
        itemMap.add(new Item(this, "System", "all_sound_off", "Int", 0, true));
        itemMap.add(new Item(this, "System", "haptic_feedback_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "System", "simple_status_bar", "Int", Integer.valueOf(NotiRune.NOTI_STATUSBAR_SIMPLE_DEFAULT_ON ? 1 : 0), true));
        itemMap.add(new Item(this, "System", "access_control_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "System", "time_12_24", "String", null, true));
        itemMap.add(new Item(this, "System", "lock_noticard_opacity", "Int", 80, true));
        itemMap.add(new Item(this, "System", "lock_noticard_opacity_dark_mode", "Int", 40, true));
        itemMap.add(new Item(this, "System", "lockscreen_sounds_enabled", "Int", 1, true));
        itemMap.add(new Item(this, "System", "lock_screen_allow_rotation", "Int", Integer.valueOf(LsRune.KEYGUARD_ENABLE_DEFAULT_ROTATION ? 1 : 0), true));
        itemMap.add(new Item(this, "System", "screen_transition_effect", "Int", Integer.valueOf(getDefaultScreenTransitionEffect()), true));
        itemMap.add(new Item(this, "System", "lift_to_wake", "Int", 0, true));
        itemMap.add(new Item(this, "System", "action_memo_on_off_screen", "Int", 0, true));
        itemMap.add(new Item(this, "System", "screen_off_memo", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "refresh_rate_mode", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "refresh_rate_mode_cover", "Int", 0, true));
        itemMap.add(new Item(this, "System", "double_tap_to_sleep", "Int", 0, true));
        itemMap.add(new Item(this, "System", "display_night_theme", "Int", 0, true));
        itemMap.add(new Item(this, "System", "premium_watch_switch_onoff", "Int", 0, true));
        if (DeviceType.isSupportPenDetachmentOption(context)) {
            itemMap.add(new Item(this, "System", "pen_detachment_option", "Int", null, false));
        }
        itemMap.add(new Item(this, "System", "transition_animation_scale", "String", null, true));
        itemMap.add(new Item(this, "Secure", "accessibility_interactive_ui_timeout_ms", "Int", 0, true));
        itemMap.add(new Item(this, "System", "aod_tap_to_show_mode", "Int", 0, true));
        itemMap.add(new Item(this, "System", "aod_mode_start_time", "Int", 0, true));
        itemMap.add(new Item(this, "System", "aod_mode_end_time", "Int", 0, true));
        itemMap.add(new Item(this, "System", "aod_show_state", "Int", 0, true));
        itemMap.add(new Item(this, "System", "aod_show_for_new_noti", "Int", 0, true));
        if (LsRune.SUBSCREEN_WATCHFACE) {
            itemMap.add(new Item(this, "Secure", "show_navigation_for_subscreen", "Int", 0, false));
        }
        boolean z3 = LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY;
        if (z3) {
            itemMap.add(new Item(this, "System", "turn_on_screen_while_unfolding_for_smart_cover", "Int", 1, true));
            itemMap.add(new Item(this, "System", "accessory_cover_uri", "String", null, false));
            itemMap.add(new Item(this, "System", "smart_cover_enabled", "Int", 0, true));
            itemMap.add(new Item(this, "System", "cover_screen_timeout", "Int", 10, true));
        }
        if (LsRune.AOD_FULLSCREEN) {
            itemMap.add(new Item(this, "System", "aod_show_lockscreen_wallpaper", "Int", 1, true));
            itemMap.add(new Item(this, "System", "aod_light_reveal_alpha", "String", null, true));
        }
        itemMap.add(new Item("System", "display_night_theme_wallpaper", "Int", 1, true, true));
        itemMap.add(new Item(this, "System", "wallpapertheme_state", "Int", 0, true));
        itemMap.add(new Item(this, "System", "wallpaper_highlight_filter_amount", "Int", -1, true));
        itemMap.add(new Item(this, "System", "lockstar_enabled", "Int", -1, false));
        itemMap.add(new Item(this, "System", "plugin_lock_sub_enabled", "Int", -1, false));
        itemMap.add(new Item(this, "Secure", "plugin_lock_wallpaper_type", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "plugin_lock_wallpaper_type_sub", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "plugin_lock_clock", "Int", -1, false));
        itemMap.add(new Item(this, "Secure", "lock_screen_show_silent_notifications", "Int", 1, true));
        itemMap.add(new Item(this, "Secure", "enabled_accessibility_services", "String", null, true));
        itemMap.add(new Item(this, "Secure", "fingerprint_always_on", "Int", 1, true));
        itemMap.add(new Item(this, "Secure", "fingerprint_always_on_type", "Int", Integer.valueOf(z3 ? 2 : 0), true));
        itemMap.add(new Item(this, "Secure", "fingerprint_sensor_block_popup_show_again", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "face_stay_on_lock_screen", "Int", 1, true));
        itemMap.add(new Item(this, "Global", "airplane_mode_on", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "bouncer_one_hand_position", "Int", 1, false));
        itemMap.add(new Item(this, "Secure", "knox_finger_print_plus", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "auto_swipe_main_user", "Int", 0, true));
        itemMap.add(new Item(this, "Global", "select_name_1", "String", null, true));
        itemMap.add(new Item(this, "Global", "select_name_2", "String", null, true));
        itemMap.add(new Item(this, "Secure", "reset_credential_from_previous", "Int", 0, true));
        if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
            itemMap.add(new Item(this, "System", "colortheme_app_icon", "Int", 0, true));
            itemMap.add(new Item(this, "System", "wallpapertheme_state", "Int", 0, true));
            itemMap.add(new Item(this, "System", "wallpapertheme_color", "Int", 0, true));
        }
        if (CoreRune.MW_FREEFORM_SMART_POPUP_VIEW) {
            itemMap.add(new Item(this, "Global", "lelink_cast_on", "Int", 0, true));
        }
        if (BasicRune.NAVBAR_ENABLED) {
            itemMap.add(new Item(this, "Global", "navigationbar_key_order", "Int", 0, false));
            itemMap.add(new Item(this, "Global", "navigation_bar_rotate_suggestion_enabled", "Int", 1, false));
            itemMap.add(new Item(this, "Global", "navigationbar_use_theme_default", "Int", 0, false));
            itemMap.add(new Item(this, "Global", "navigationbar_current_color", "Int", -855310, false));
            itemMap.add(new Item(this, "Global", "navigationbar_color", "Int", 0, false));
            itemMap.add(new Item(this, "Secure", "default_input_method", "String", null, true));
            itemMap.add(new Item(this, "Global", "navigationbar_splugin_flags", "Int", 0, false));
        }
        if (BasicRune.NAVBAR_REMOTEVIEW) {
            itemMap.add(new Item(this, "Secure", "game_double_swipe_enable", "Int", 0, false));
            itemMap.add(new Item(this, "Secure", "game_show_floating_icon", "Int", 0, false));
        }
        if (BasicRune.NAVBAR_GESTURE) {
            itemMap.add(new Item(this, "Global", "navigation_bar_gesture_while_hidden", "Int", 0, false));
            itemMap.add(new Item(this, "Global", "navigation_bar_gesture_hint", "Int", 1, false));
            itemMap.add(new Item(this, "Global", "navigation_bar_button_to_hide_keyboard", "Int", 1, false));
            itemMap.add(new Item(this, "Global", "navigation_bar_block_gestures_with_spen", "Int", 0, false));
            itemMap.add(new Item(this, "Global", "navigation_bar_gesture_disabled_by_policy", "Int", 0, false));
            itemMap.add(new Item(this, "Global", "navigation_bar_back_gesture_sensitivity", "Int", 1, false));
            itemMap.add(new Item(this, "Global", "navigation_bar_back_gesture_sensitivity_sub", "Int", 1, false));
            itemMap.add(new Item(this, "System", "navigation_gestures_vibrate", "Int", 1, true));
            itemMap.add(new Item(this, "System", "reduce_screen_running_info", "String", null, true));
            itemMap.add(new Item(this, "Global", "open_in_split_screen_view", "Int", 0, false));
        }
        if (BasicRune.NAVBAR_MOVABLE_POSITION) {
            itemMap.add(new Item(this, "Global", "navigationbar_key_position", "Int", 2, false));
        }
        if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
            itemMap.add(new Item(this, "Global", "task_bar", "Int", 1, false));
        }
        if (BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN) {
            itemMap.add(new Item(this, "System", "large_cover_screen_navigation", "Int", 0, false));
        }
        if (BasicRune.NAVBAR_NEW_DEX) {
            itemMap.add(new Item(this, "System", "new_dex", "Int", 0, true));
        }
        itemMap.add(new Item(this, "Secure", "show_notification_snooze", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "assist_disclosure_enabled", "Int", 0, true));
        itemMap.add(new Item(this, "Secure", "icon_blacklist", "String", null, true));
        itemMap.add(new Item(this, "Global", "notification_sort_order", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "notification_apply_wallpaper_theme", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "quickstar_qs_tile_layout_custom_matrix", "Int", 0, false));
        itemMap.add(new Item(this, "Global", "quickstar_qs_tile_layout_custom_matrix_width", "Int", -1, false));
        itemMap.add(new Item(this, "Global", "quickstar_indicator_clock_date_format", "Int", 0, false));
        itemMap.add(new Item(this, "System", "edge_lighting", "Int", Integer.valueOf((!SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION") || Feature.isEdgeLightingDefaultOff()) ? 0 : 1), true));
        itemMap.add(new Item(this, "Global", "smart_view_show_notification_on", "Int", 1, false));
        if (z2) {
            itemMap.add(new Item(this, "System", "camera_flash_notification", "Int", 0, true));
        }
        itemMap.add(new Item(this, "Secure", "location_mode", "Int", 0, true));
        boolean z4 = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON;
        if (z4 || NotiRune.NOTI_SUBSCREEN_CLEAR_COVER) {
            itemMap.add(new Item("Secure", "turn_on_cover_screen_for_notification", "Int", -1, true, true));
            itemMap.add(new Item(this, "Secure", "cover_screen_show_notification", "Int", 1, true));
            if (z4) {
                itemMap.add(new Item(this, "Secure", "cover_screen_show_notification_tip", "Int", 0, true));
                itemMap.add(new Item(this, "System", "cover_screen_quick_reply_text", "String", null, true));
                itemMap.add(new Item(this, "System", "cover_screen_quick_reply_text_pos_for_translation", "String", null, true));
            }
        }
        if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
            itemMap.add(new Item(this, "Secure", "notification_history_enabled", "Int", 1, true));
            itemMap.add(new Item(this, "System", "ai_info_confirmed", "Int", 0, true));
            if (NotiRune.NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA) {
                itemMap.add(new Item(this, "Global", "suggestion_responses", "Int", 0, false));
                itemMap.add(new Item(this, "Global", "suggestion_responses_used", "Int", 0, false));
            } else {
                itemMap.add(new Item(this, "Global", "suggestion_responses", "Int", 1, false));
            }
        }
        itemMap.add(new Item(this, "Global", "animator_duration_scale", "Float", Float.valueOf(1.0f), false));
        if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE) {
            itemMap.add(new Item(this, "Secure", "lock_screen_allow_private_notifications_when_unsecure", "Int", Integer.valueOf(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED").contains("defaulton") ? 1 : 0), true));
        }
        itemMap.add(new Item(this, "Secure", "lock_screen_show_notifications_on_keyguard", "Int", 1, true));
        itemMap.add(new Item(this, "System", "emergency_message_working_state", "Int", 0, true));
        if (BasicRune.SEARCLE) {
            itemMap.add(new Item(this, "Secure", "touch_and_hold_to_search", "Int", Integer.valueOf(Settings.Secure.getInt(context.getContentResolver(), "touch_and_hold_to_search", 1)), true));
            itemMap.add(new Item(this, "Secure", "assistant", "String", null, true));
            itemMap.add(new Item(this, "Secure", "voice_interaction_service", "String", null, true));
            if (BasicRune.BIXBY_TOUCH_SUPPORT_CIRCLE2SEARCH) {
                itemMap.add(new Item(this, "Secure", "cn_support_circe_to_search", "Int", Integer.valueOf(Settings.Secure.getInt(context.getContentResolver(), "cn_support_circe_to_search", 0)), true));
            }
        }
    }

    public final void unregisterCallback(OnChangedCallback onChangedCallback) {
        synchronized (this) {
            Objects.toString(onChangedCallback);
            for (int size = this.mCallbacks.size() - 1; size >= 0; size--) {
                ArrayMap arrayMap = this.mCallbacks;
                ArrayList arrayList = (ArrayList) arrayMap.get(arrayMap.keyAt(size));
                if (arrayList != null) {
                    for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                        if (((WeakReference) arrayList.get(size2)).get() == onChangedCallback) {
                            arrayList.remove(size2);
                        }
                    }
                    if (arrayList.isEmpty()) {
                        ArrayMap arrayMap2 = this.mCallbacks;
                        arrayMap2.remove(arrayMap2.keyAt(size));
                    }
                }
            }
        }
    }

    public final int getLockscreenWallpaperTransparent(boolean z) {
        ItemMap itemMap = this.mItemLists;
        return z ? itemMap.get("sub_display_lockscreen_wallpaper_transparency").getIntValue() : itemMap.get("lockscreen_wallpaper_transparent").getIntValue();
    }

    public final boolean isLiveWallpaperEnabled(boolean z) {
        ItemMap itemMap = this.mItemLists;
        return z ? itemMap.get("lockscreen_wallpaper_sub").getIntValue() == 0 : itemMap.get("lockscreen_wallpaper").getIntValue() == 0;
    }
}
