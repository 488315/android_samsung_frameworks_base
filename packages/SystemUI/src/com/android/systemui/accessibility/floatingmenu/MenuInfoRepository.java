package com.android.systemui.accessibility.floatingmenu;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.settingslib.bluetooth.HearingAidDeviceManager;
import com.android.systemui.Prefs;
import com.android.systemui.util.settings.SecureSettings;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MenuInfoRepository {
    public static final boolean DEBUG;
    public final AccessibilityManager mAccessibilityManager;
    final ComponentCallbacks mComponentCallbacks;
    public final Configuration mConfiguration;
    public final Context mContext;
    public final HearingAidDeviceManager mHearingAidDeviceManager;
    final ContentObserver mMenuFadeOutContentObserver;
    final ContentObserver mMenuSizeContentObserver;
    final ContentObserver mMenuTargetFeaturesContentObserver;
    public final SecureSettings mSecureSettings;
    public final OnContentsChanged mSettingsContentsCallback;
    public final MenuInfoRepository$$ExternalSyntheticLambda0 mA11yServicesStateChangeListener = new AccessibilityManager.AccessibilityServicesStateChangeListener() { // from class: com.android.systemui.accessibility.floatingmenu.MenuInfoRepository$$ExternalSyntheticLambda0
        @Override // android.view.accessibility.AccessibilityManager.AccessibilityServicesStateChangeListener
        public final void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
            MenuInfoRepository menuInfoRepository = MenuInfoRepository.this;
            boolean z = MenuInfoRepository.DEBUG;
            menuInfoRepository.mSettingsContentsCallback.onTargetFeaturesChanged(AccessibilityTargetHelper.getTargets(menuInfoRepository.mContext, 1));
        }
    };
    public final MenuInfoRepository$$ExternalSyntheticLambda1 mHearingDeviceStatusListener = new MenuInfoRepository$$ExternalSyntheticLambda1(this);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnContentsChanged {
        void onDevicesConnectionStatusChanged(int i);

        void onFadeEffectInfoChanged(MenuFadeEffectInfo menuFadeEffectInfo);

        void onSizeTypeChanged(int i);

        void onTargetFeaturesChanged(List list);
    }

    static {
        DEBUG = Log.isLoggable("MenuInfoRepository", 3) || Build.IS_DEBUGGABLE;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.accessibility.floatingmenu.MenuInfoRepository$$ExternalSyntheticLambda0] */
    public MenuInfoRepository(Context context, AccessibilityManager accessibilityManager, OnContentsChanged onContentsChanged, SecureSettings secureSettings, HearingAidDeviceManager hearingAidDeviceManager) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mMenuTargetFeaturesContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MenuInfoRepository menuInfoRepository = MenuInfoRepository.this;
                boolean z2 = MenuInfoRepository.DEBUG;
                menuInfoRepository.mSettingsContentsCallback.onTargetFeaturesChanged(AccessibilityTargetHelper.getTargets(menuInfoRepository.mContext, 1));
            }
        };
        this.mMenuSizeContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MenuInfoRepository menuInfoRepository = MenuInfoRepository.this;
                menuInfoRepository.mSettingsContentsCallback.onSizeTypeChanged(menuInfoRepository.mSecureSettings.getIntForUser("accessibility_floating_menu_size", 0, -2));
            }
        };
        this.mMenuFadeOutContentObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.3
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                MenuInfoRepository menuInfoRepository = MenuInfoRepository.this;
                OnContentsChanged onContentsChanged2 = menuInfoRepository.mSettingsContentsCallback;
                SecureSettings secureSettings2 = menuInfoRepository.mSecureSettings;
                onContentsChanged2.onFadeEffectInfoChanged(new MenuFadeEffectInfo(secureSettings2.getIntForUser("accessibility_floating_menu_fade_enabled", 1, -2) == 1, secureSettings2.getFloatForUser("accessibility_floating_menu_opacity", 0.55f, -2)));
            }
        };
        this.mComponentCallbacks = new ComponentCallbacks() { // from class: com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.4
            @Override // android.content.ComponentCallbacks
            public final void onConfigurationChanged(Configuration configuration) {
                int diff = configuration.diff(MenuInfoRepository.this.mConfiguration);
                if (MenuInfoRepository.DEBUG) {
                    Log.d("MenuInfoRepository", "onConfigurationChanged = " + Configuration.configurationDiffToString(diff));
                }
                if ((diff & 4) != 0) {
                    MenuInfoRepository menuInfoRepository = MenuInfoRepository.this;
                    menuInfoRepository.mSettingsContentsCallback.onTargetFeaturesChanged(AccessibilityTargetHelper.getTargets(menuInfoRepository.mContext, 1));
                }
                MenuInfoRepository.this.mConfiguration.setTo(configuration);
            }

            @Override // android.content.ComponentCallbacks
            public final void onLowMemory() {
            }
        };
        this.mContext = context;
        Context createContextAsUser = context.createContextAsUser(UserHandle.of(secureSettings.getRealUserHandle(-2)), 0);
        this.mAccessibilityManager = accessibilityManager;
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        this.mConfiguration = configuration;
        this.mSettingsContentsCallback = onContentsChanged;
        this.mSecureSettings = secureSettings;
        this.mHearingAidDeviceManager = hearingAidDeviceManager;
        String string = Prefs.get(createContextAsUser).getString("AccessibilityFloatingMenuPosition", null);
        float f = configuration.getLayoutDirection() == 1 ? 0.0f : 1.0f;
        if (TextUtils.isEmpty(string)) {
            new Position(f, 0.77f);
        } else {
            Position.fromString(string);
        }
    }
}
