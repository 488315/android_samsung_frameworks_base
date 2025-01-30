package com.android.server.input;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.input.InputSettings;
import android.net.Uri;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;
import android.view.PointerIcon;
import com.android.server.display.DisplayPowerController2;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class InputSettingsObserver extends ContentObserver {
    public final Context mContext;
    public final Handler mHandler;
    public final NativeInputManagerService mNative;
    public final Map mObservers;
    public final InputManagerService mService;

    public InputSettingsObserver(Context context, Handler handler, InputManagerService inputManagerService, NativeInputManagerService nativeInputManagerService) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
        this.mService = inputManagerService;
        this.mNative = nativeInputManagerService;
        this.mObservers = Map.ofEntries(Map.entry(Settings.System.getUriFor("mouse_pointer_size_step"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$0((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("mouse_pointer_color"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$1((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("pointer_speed"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$2((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("touchpad_pointer_speed"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$3((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("touchpad_natural_scrolling"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$4((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("touchpad_tap_to_click"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$5((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("touchpad_right_click_zone"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$6((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("show_touches"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$7((String) obj);
            }
        }), Map.entry(Settings.Secure.getUriFor("accessibility_large_pointer_icon"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$8((String) obj);
            }
        }), Map.entry(Settings.Secure.getUriFor("long_press_timeout"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda25
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$9((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("pen_hovering_pointer"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$10((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("pen_hovering"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$11((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("cover_test_mode"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$12((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("new_dex"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$13((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("primary_mouse_button_option"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$14((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("mouse_scrolling_speed"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$15((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("enhance_pointer_precision"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$16((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("mouse_secondary_button_option"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$17((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("mouse_middle_button_option"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$18((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("mouse_additional_1_option"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$19((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("mouse_additional_2_option"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$20((String) obj);
            }
        }), Map.entry(Settings.Secure.getUriFor("enabled_accessibility_services"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$21((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("tap_to_click_enabled"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$22((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("touchpad_scrolling_direction"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$23((String) obj);
            }
        }), Map.entry(Settings.Global.getUriFor("maximum_obscuring_opacity_for_touch"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$24((String) obj);
            }
        }), Map.entry(Settings.System.getUriFor("show_key_presses"), new Consumer() { // from class: com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InputSettingsObserver.this.lambda$new$25((String) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str) {
        updatePointerIconCustomFromSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(String str) {
        updatePointerIconCustomFromSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(String str) {
        updateMousePointerSpeed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(String str) {
        updateTouchpadPointerSpeed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(String str) {
        updateTouchpadNaturalScrollingEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(String str) {
        updateTouchpadTapToClickEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(String str) {
        updateTouchpadRightClickZoneEnabled();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(String str) {
        updateShowTouches();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(String str) {
        updateAccessibilityLargePointer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(String str) {
        updateShowHoveringFromSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$11(String str) {
        updatePenHoveringFromSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$12(String str) {
        updateCoverTestModeFromSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$13(String str) {
        updateNewDexStateFromSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$14(String str) {
        updatePrimaryMouseButtonFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$15(String str) {
        updateScrollSpeedFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$16(String str) {
        updateUseMouseAccelerationFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$17(String str) {
        updateSecondaryButtonBehaviorFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$18(String str) {
        updateTertiaryButtonBehaviorFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$19(String str) {
        updateBackButtonBehaviorFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$20(String str) {
        updateForwardButtonBehaviorFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$21(String str) {
        updateAccessibilityEnabledFromSettings();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$22(String str) {
        updateTapToClickFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$23(String str) {
        updateReverseSwipeFromSetting();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$24(String str) {
        updateMaximumObscuringOpacityForTouch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$25(String str) {
        updateShowKeyPresses();
    }

    public void registerAndUpdate() {
        Iterator it = this.mObservers.keySet().iterator();
        while (it.hasNext()) {
            this.mContext.getContentResolver().registerContentObserver((Uri) it.next(), true, this, -1);
        }
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.input.InputSettingsObserver.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                Iterator it2 = InputSettingsObserver.this.mObservers.values().iterator();
                while (it2.hasNext()) {
                    ((Consumer) it2.next()).accept("user switched");
                }
            }
        }, new IntentFilter("android.intent.action.USER_SWITCHED"), null, this.mHandler);
        Iterator it2 = this.mObservers.values().iterator();
        while (it2.hasNext()) {
            ((Consumer) it2.next()).accept("just booted");
        }
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z, Uri uri) {
        ((Consumer) this.mObservers.get(uri)).accept("setting changed");
    }

    public final boolean getBoolean(String str, boolean z) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), str, z ? 1 : 0, -2) != 0;
    }

    public final int getPointerSpeedValue(String str) {
        return Math.min(Math.max(Settings.System.getIntForUser(this.mContext.getContentResolver(), str, 0, -2), -7), 7);
    }

    public final void updateMousePointerSpeed() {
        this.mNative.setPointerSpeed(getPointerSpeedValue("pointer_speed"));
    }

    public final void updateTouchpadPointerSpeed() {
        this.mNative.setTouchpadPointerSpeed(getPointerSpeedValue("touchpad_pointer_speed"));
    }

    public final void updateTouchpadNaturalScrollingEnabled() {
        this.mNative.setTouchpadNaturalScrollingEnabled(getBoolean("touchpad_natural_scrolling", true));
    }

    public final void updateTouchpadTapToClickEnabled() {
        this.mNative.setTouchpadTapToClickEnabled(getBoolean("touchpad_tap_to_click", true));
    }

    public final void updateTouchpadRightClickZoneEnabled() {
        this.mNative.setTouchpadRightClickZoneEnabled(getBoolean("touchpad_right_click_zone", false));
    }

    public final void updateShowTouches() {
        this.mNative.setShowTouches(getBoolean("show_touches", false));
    }

    public final void updateShowKeyPresses() {
        this.mService.updateFocusEventDebugViewEnabled(getBoolean("show_key_presses", false));
    }

    public final void updateAccessibilityLargePointer() {
        PointerIcon.setUseLargeIcons(Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_large_pointer_icon", 0, -2) == 1);
        this.mNative.reloadPointerIcons();
    }

    /* renamed from: updateLongPressTimeout, reason: merged with bridge method [inline-methods] */
    public final void lambda$new$9(String str) {
        this.mNative.notifyKeyGestureTimeoutsChanged();
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "long_press_timeout", 400, -2);
        boolean z = DeviceConfig.getBoolean("input_native_boot", "deep_press_enabled", true);
        boolean z2 = z && intForUser <= 400;
        StringBuilder sb = new StringBuilder();
        sb.append(z2 ? "Enabling" : "Disabling");
        sb.append(" motion classifier because ");
        sb.append(str);
        sb.append(": feature ");
        sb.append(z ? "enabled" : "disabled");
        sb.append(", long press timeout = ");
        sb.append(intForUser);
        Log.i("InputManager", sb.toString());
        this.mNative.setMotionClassifierEnabled(z2);
    }

    public final void updateMaximumObscuringOpacityForTouch() {
        float maximumObscuringOpacityForTouch = InputSettings.getMaximumObscuringOpacityForTouch(this.mContext);
        if (maximumObscuringOpacityForTouch < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || maximumObscuringOpacityForTouch > 1.0f) {
            Log.e("InputManager", "Invalid maximum obscuring opacity " + maximumObscuringOpacityForTouch + ", it should be >= 0 and <= 1, rejecting update.");
            return;
        }
        this.mNative.setMaximumObscuringOpacityForTouch(maximumObscuringOpacityForTouch);
    }

    public final boolean getShowHoveringSetting(boolean z) {
        try {
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), "pen_hovering_pointer", 0, -2) == 1;
        } catch (Exception e) {
            Log.e("InputManager", "Exception getShowHoveringSetting '" + z + "'", e);
            return z;
        }
    }

    public final boolean getPenHoveringSetting(boolean z) {
        try {
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), "pen_hovering", 0, -2) == 1;
        } catch (Exception e) {
            Log.e("InputManager", "Exception getPenHoveringSetting '" + z + "'", e);
            return z;
        }
    }

    public void updateShowHoveringFromSettings() {
        this.mNative.setShowHovering(getShowHoveringSetting(false));
    }

    public void updatePenHoveringFromSettings() {
        boolean penHoveringSetting = getPenHoveringSetting(false);
        this.mNative.setPenHovering(penHoveringSetting);
        if (this.mContext.getPackageManager().semGetSystemFeatureLevel("com.sec.feature.spen_usp") == 2) {
            if (!penHoveringSetting) {
                this.mNative.setShowHovering(false);
            } else {
                updateShowHoveringFromSettings();
            }
        }
    }

    public void updateCoverTestModeFromSettings() {
        this.mNative.setCoverTestModeType(getCoverTestModeSetting(-1));
    }

    public final int getCoverTestModeSetting(int i) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), "cover_test_mode", i, -2);
    }

    public void updateNewDexStateFromSettings() {
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "new_dex", 0, -2);
        Log.d("InputManager", "NEW_DEX state: " + intForUser);
        if (intForUser == 1) {
            this.mNative.setDexMode(true, 101, 256);
        } else if (intForUser == 2) {
            this.mNative.setDexMode(true, 102, 256);
        } else {
            this.mNative.setDexMode(false, 0, 0);
        }
    }

    public final void updatePointerIconCustomFromSettings() {
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "mouse_pointer_color", 16777215, -2);
        float intForUser2 = (Settings.System.getIntForUser(this.mContext.getContentResolver(), "mouse_pointer_size_step", 1, -2) + 1.0f) / 2.0f;
        Log.i("InputManager", "updatePointerIcon color=0x" + Integer.toHexString(intForUser) + " size=" + intForUser2);
        PointerIcon.setCustomIcons(intForUser, intForUser2);
        this.mNative.reloadPointerIcons();
    }

    public final int getPrimaryMouseButtonLocation() {
        try {
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), "primary_mouse_button_option", -2);
        } catch (Exception unused) {
            Log.d("InputManager", "Exception primary mouse button");
            return 0;
        }
    }

    public final void setPrimaryMouseButtonLocation(int i) {
        if (i != 0 && i != 1) {
            Log.d("InputManager", "wrong primary mouse button : " + i);
            i = 0;
        }
        this.mNative.setPrimaryMouseButtonLocation(i);
    }

    public void updatePrimaryMouseButtonFromSetting() {
        setPrimaryMouseButtonLocation(getPrimaryMouseButtonLocation());
    }

    public void updateScrollSpeedFromSetting() {
        int i = 1;
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "mouse_scrolling_speed", 1, -2);
        if (intForUser < 1) {
            Log.d("InputManager", "wrong scroll speed : " + intForUser);
        } else {
            i = intForUser;
        }
        this.mNative.setScrollSpeed(i);
    }

    public void updateUseMouseAccelerationFromSetting() {
        this.mNative.setUseMouseAcceleration(Settings.System.getIntForUser(this.mContext.getContentResolver(), "enhance_pointer_precision", 1, -2) == 1);
    }

    public void updateSecondaryButtonBehaviorFromSetting() {
        this.mNative.setSecondaryButtonBehavior(Settings.System.getIntForUser(this.mContext.getContentResolver(), "mouse_secondary_button_option", 1, -2));
    }

    public void updateTertiaryButtonBehaviorFromSetting() {
        this.mNative.setTertiaryButtonBehavior(Settings.System.getIntForUser(this.mContext.getContentResolver(), "mouse_middle_button_option", 3, -2));
    }

    public void updateBackButtonBehaviorFromSetting() {
        this.mNative.setBackButtonBehavior(Settings.System.getIntForUser(this.mContext.getContentResolver(), "mouse_additional_1_option", 7, -2));
    }

    public void updateForwardButtonBehaviorFromSetting() {
        this.mNative.setForwardButtonBehavior(Settings.System.getIntForUser(this.mContext.getContentResolver(), "mouse_additional_2_option", 4, -2));
    }

    public void updateAccessibilityEnabledFromSettings() {
        String string = Settings.Secure.getString(this.mContext.getContentResolver(), "enabled_accessibility_services");
        if (string != null) {
            boolean z = string.matches("(?i).*com.samsung.android.app.talkback.TalkBackService.*") || string.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*");
            Log.d("InputManager", "accessibility service : " + string);
            Log.d("InputManager", "talkbackEnabled : " + z);
            this.mNative.setTalkBack(z);
        }
    }

    public final int getTapToClickSetting() {
        try {
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), "tap_to_click_enabled", 1, -2);
        } catch (Exception e) {
            Log.e("InputManager", "Exception getTapToClickSetting '1'", e);
            return 1;
        }
    }

    public void updateTapToClickFromSetting() {
        int tapToClickSetting = getTapToClickSetting();
        this.mNative.setEnableTapToClick(tapToClickSetting != 0);
        Log.d("InputManager", "updateTapToClickFromSetting : " + tapToClickSetting);
    }

    public final int getReverseSwipeSetting() {
        try {
            return Settings.System.getIntForUser(this.mContext.getContentResolver(), "touchpad_scrolling_direction", 0, -2);
        } catch (Exception e) {
            Log.e("InputManager", "Exception getReverseSwipeSetting '0'", e);
            return 0;
        }
    }

    public void updateReverseSwipeFromSetting() {
        int reverseSwipeSetting = getReverseSwipeSetting();
        this.mNative.setReverseSwipeGesture(reverseSwipeSetting != 0);
        Log.d("InputManager", "updateReverseSwipeFromSetting : " + reverseSwipeSetting);
    }
}
