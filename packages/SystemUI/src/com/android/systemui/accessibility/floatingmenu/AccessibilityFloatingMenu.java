package com.android.systemui.accessibility.floatingmenu;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.settingslib.bluetooth.HearingAidDeviceManager;
import com.android.systemui.Prefs;
import com.android.systemui.accessibility.floatingmenu.MenuInfoRepository;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.util.settings.SecureSettings;
import java.util.List;

/* loaded from: classes.dex */
public class AccessibilityFloatingMenu implements IAccessibilityFloatingMenu, MenuInfoRepository.OnContentsChanged {
    public final Context mContext;
    public final MenuInfoRepository mInfoRepository;
    public final boolean mIsHideHandle;
    public final AccessibilityFloatingMenuView mMenuView;
    public final MenuViewModel mMenuViewModel;
    public final AccessibilityFloatingMenu$$ExternalSyntheticLambda0 mNavigationModeChangedListener;
    public final NavigationModeController mNavigationModeController;

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu$$ExternalSyntheticLambda0] */
    public AccessibilityFloatingMenu(Context context, WindowManager windowManager, AccessibilityManager accessibilityManager, SecureSettings secureSettings, NavigationModeController navigationModeController, HearingAidDeviceManager hearingAidDeviceManager) {
        this.mContext = context;
        this.mInfoRepository = new MenuInfoRepository(context, accessibilityManager, this, secureSettings, hearingAidDeviceManager);
        MenuViewModel menuViewModel = new MenuViewModel(context, accessibilityManager, secureSettings, hearingAidDeviceManager);
        this.mMenuViewModel = menuViewModel;
        this.mMenuView = new AccessibilityFloatingMenuView(context, getPosition(context), windowManager, hearingAidDeviceManager, menuViewModel);
        this.mNavigationModeController = navigationModeController;
        this.mNavigationModeChangedListener = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenu$$ExternalSyntheticLambda0
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                this.f$0.mMenuView.mAdapter.notifyDataSetChanged();
            }
        };
        this.mIsHideHandle = Settings.Secure.getInt(context.getContentResolver(), "accessibility_floating_menu_icon_type", 0) == 9;
    }

    public static Position getPosition(Context context) {
        String string = Prefs.get(context).getString("AccessibilityFloatingMenuPosition", null);
        return TextUtils.isEmpty(string) ? new Position(1.0f, 0.77f) : Position.fromString(string);
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onDevicesConnectionStatusChanged(int i) {
        this.mMenuViewModel.onDevicesConnectionStatusChanged(i);
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onFadeEffectInfoChanged(MenuFadeEffectInfo menuFadeEffectInfo) {
        this.mMenuView.updateOpacityWith(Settings.Secure.getFloat(this.mContext.getContentResolver(), "accessibility_floating_menu_opacity", 0.55f), Settings.Secure.getInt(this.mContext.getContentResolver(), "accessibility_floating_menu_fade_enabled", 1) == 1);
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onSizeTypeChanged(int i) {
        this.mMenuView.setSizeType(Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_floating_menu_size", 9, -2));
    }

    @Override // com.android.systemui.accessibility.floatingmenu.MenuInfoRepository.OnContentsChanged
    public final void onTargetFeaturesChanged(List list) {
        this.mMenuView.onTargetFeaturesChanged(AccessibilityTargetHelper.getTargets(this.mContext, 1));
    }
}
