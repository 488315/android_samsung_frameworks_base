package com.android.server.input;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.input.InputSettings;
import android.net.Uri;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.Log;
import android.view.PointerIcon;
import android.view.ViewConfiguration;
import android.view.flags.Flags;

import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;

import java.util.Map;
import java.util.function.Consumer;

public final class InputSettingsObserver extends ContentObserver {
    public final Context mContext;
    public final Handler mHandler;
    public final NativeInputManagerService mNative;
    public final Map mObservers;
    public final InputManagerService mService;

    public InputSettingsObserver(
            Context context,
            InputManagerService.InputManagerHandler inputManagerHandler,
            InputManagerService inputManagerService,
            NativeInputManagerService.NativeImpl nativeImpl) {
        super(inputManagerHandler);
        this.mContext = context;
        this.mHandler = inputManagerHandler;
        this.mService = inputManagerService;
        this.mNative = nativeImpl;
        final int i = 0;
        final int i2 = 2;
        final int i3 = 14;
        final int i4 = 23;
        final int i5 = 24;
        final int i6 = 25;
        final int i7 = 26;
        final int i8 = 27;
        final int i9 = 28;
        final int i10 = 29;
        final int i11 = 11;
        final int i12 = 22;
        final int i13 = 0;
        final int i14 = 1;
        final int i15 = 2;
        final int i16 = 3;
        final int i17 = 4;
        final int i18 = 5;
        final int i19 = 6;
        final int i20 = 1;
        final int i21 = 3;
        final int i22 = 4;
        final int i23 = 5;
        final int i24 = 6;
        final int i25 = 7;
        final int i26 = 8;
        final int i27 = 9;
        final int i28 = 10;
        final int i29 = 12;
        final int i30 = 13;
        final int i31 = 15;
        final int i32 = 16;
        final int i33 = 17;
        final int i34 = 18;
        final int i35 = 19;
        final int i36 = 20;
        final int i37 = 21;
        this.mObservers =
                Map.ofEntries(
                        Map.entry(
                                Settings.System.getUriFor("mouse_pointer_size_step"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("mouse_pointer_color"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i2;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("pointer_speed"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i3;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("touchpad_pointer_speed"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i4;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("touchpad_natural_scrolling"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i5;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("touchpad_tap_to_click"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i6;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("touchpad_tap_dragging"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i7;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("touchpad_right_click_zone"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i8;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("show_touches"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i9;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("pointer_location"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i10;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("accessibility_large_pointer_icon"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i11;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("long_press_timeout"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i12;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("pen_hovering_pointer"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    /* JADX WARN: Code restructure failed: missing block: B:43:0x00cd, code lost:

                                       if (android.provider.Settings.System.getIntForUser(r4.mContext.getContentResolver(), "pen_hovering", 0, -2) == 1) goto L45;
                                    */
                                    @Override // java.util.function.Consumer
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final void accept(java.lang.Object r5) {
                                        /*
                                            Method dump skipped, instructions count: 270
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("pen_hovering"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        /*
                                            Method dump skipped, instructions count: 270
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("cover_test_mode"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        /*
                                            Method dump skipped, instructions count: 270
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("new_dex"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        /*
                                            Method dump skipped, instructions count: 270
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("primary_mouse_button_option"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        /*
                                            Method dump skipped, instructions count: 270
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("mouse_scrolling_speed"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        /*
                                            Method dump skipped, instructions count: 270
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("enhance_pointer_precision"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        /*
                                            Method dump skipped, instructions count: 270
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException(
                                                "Method not decompiled:"
                                                    + " com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda3.accept(java.lang.Object):void");
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("mouse_secondary_button_option"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i20;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("mouse_middle_button_option"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i21;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("mouse_additional_1_option"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i22;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("mouse_additional_2_option"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i23;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("enabled_accessibility_services"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i24;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("tap_to_click_enabled"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i25;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("touchpad_scrolling_direction"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i26;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Global.getUriFor("maximum_obscuring_opacity_for_touch"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i27;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("show_key_presses"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i28;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("key_repeat_timeout"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i29;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("key_repeat_delay"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i30;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("show_rotary_input"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i31;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("accessibility_bounce_keys"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i32;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("accessibility_slow_keys"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i33;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("accessibility_sticky_keys"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i34;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.Secure.getUriFor("stylus_pointer_icon_enabled"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i35;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("pointer_fill_style"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i36;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }),
                        Map.entry(
                                Settings.System.getUriFor("pointer_scale"),
                                new Consumer(this) { // from class:
                                    // com.android.server.input.InputSettingsObserver$$ExternalSyntheticLambda0
                                    public final /* synthetic */ InputSettingsObserver f$0;

                                    {
                                        this.f$0 = this;
                                    }

                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i38;
                                        int i39;
                                        int i40 = i37;
                                        InputSettingsObserver inputSettingsObserver = this.f$0;
                                        String str = (String) obj;
                                        switch (i40) {
                                            case 0:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 1:
                                                inputSettingsObserver.mNative
                                                        .setSecondaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_secondary_button_option",
                                                                        1,
                                                                        -2));
                                                break;
                                            case 2:
                                                inputSettingsObserver
                                                        .updatePointerIconCustomFromSettings();
                                                break;
                                            case 3:
                                                inputSettingsObserver.mNative
                                                        .setTertiaryButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_middle_button_option",
                                                                        3,
                                                                        -2));
                                                break;
                                            case 4:
                                                inputSettingsObserver.mNative.setBackButtonBehavior(
                                                        Settings.System.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "mouse_additional_1_option",
                                                                7,
                                                                -2));
                                                break;
                                            case 5:
                                                inputSettingsObserver.mNative
                                                        .setForwardButtonBehavior(
                                                                Settings.System.getIntForUser(
                                                                        inputSettingsObserver
                                                                                .mContext
                                                                                .getContentResolver(),
                                                                        "mouse_additional_2_option",
                                                                        4,
                                                                        -2));
                                                break;
                                            case 6:
                                                String string =
                                                        Settings.Secure.getString(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "enabled_accessibility_services");
                                                if (string != null) {
                                                    boolean z =
                                                            string.matches(
                                                                            "(?i).*com.samsung.android.app.talkback.TalkBackService.*")
                                                                    || string.matches(
                                                                            "(?i).*com.google.android.marvin.talkback.TalkBackService.*");
                                                    Log.d(
                                                            "InputManager",
                                                            "accessibility service : "
                                                                    .concat(string));
                                                    Log.d("InputManager", "talkbackEnabled : " + z);
                                                    inputSettingsObserver.mNative.setTalkBack(z);
                                                    break;
                                                }
                                                break;
                                            case 7:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i38 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "tap_to_click_enabled",
                                                                    1,
                                                                    -2);
                                                } catch (Exception e) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getTapToClickSetting '1'",
                                                            e);
                                                    i38 = 1;
                                                }
                                                inputSettingsObserver.mNative.setEnableTapToClick(
                                                        i38 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateTapToClickFromSetting : " + i38);
                                                break;
                                            case 8:
                                                inputSettingsObserver.getClass();
                                                try {
                                                    i39 =
                                                            Settings.System.getIntForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "touchpad_scrolling_direction",
                                                                    0,
                                                                    -2);
                                                } catch (Exception e2) {
                                                    Log.e(
                                                            "InputManager",
                                                            "Exception getReverseSwipeSetting '0'",
                                                            e2);
                                                    i39 = 0;
                                                }
                                                inputSettingsObserver.mNative
                                                        .setReverseSwipeGesture(i39 != 0);
                                                Log.d(
                                                        "InputManager",
                                                        "updateReverseSwipeFromSetting : " + i39);
                                                break;
                                            case 9:
                                                float maximumObscuringOpacityForTouch =
                                                        InputSettings
                                                                .getMaximumObscuringOpacityForTouch(
                                                                        inputSettingsObserver
                                                                                .mContext);
                                                if (maximumObscuringOpacityForTouch
                                                                >= FullScreenMagnificationGestureHandler
                                                                        .MAX_SCALE
                                                        && maximumObscuringOpacityForTouch
                                                                <= 1.0f) {
                                                    inputSettingsObserver.mNative
                                                            .setMaximumObscuringOpacityForTouch(
                                                                    maximumObscuringOpacityForTouch);
                                                    break;
                                                } else {
                                                    Log.e(
                                                            "InputManager",
                                                            "Invalid maximum obscuring opacity "
                                                                    + maximumObscuringOpacityForTouch
                                                                    + ", it should be >= 0 and <="
                                                                    + " 1, rejecting update.");
                                                    break;
                                                }
                                            case 10:
                                                inputSettingsObserver.mService.updateShowKeyPresses(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_key_presses"));
                                                break;
                                            case 11:
                                                inputSettingsObserver.mService
                                                        .setUseLargePointerIcons(
                                                                Settings.Secure.getIntForUser(
                                                                                inputSettingsObserver
                                                                                        .mContext
                                                                                        .getContentResolver(),
                                                                                "accessibility_large_pointer_icon",
                                                                                0,
                                                                                -2)
                                                                        == 1);
                                                break;
                                            case 12:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 13:
                                                inputSettingsObserver.updateKeyRepeatInfo();
                                                break;
                                            case 14:
                                                inputSettingsObserver.mNative.setPointerSpeed(
                                                        Math.min(
                                                                Math.max(
                                                                        Settings.System
                                                                                .getIntForUser(
                                                                                        inputSettingsObserver
                                                                                                .mContext
                                                                                                .getContentResolver(),
                                                                                        "pointer_speed",
                                                                                        0,
                                                                                        -2),
                                                                        -7),
                                                                7));
                                                break;
                                            case 15:
                                                inputSettingsObserver.mService
                                                        .updateShowRotaryInput(
                                                                inputSettingsObserver.getBoolean(
                                                                        "show_rotary_input"));
                                                break;
                                            case 16:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityBounceKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilityBounceKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 17:
                                                inputSettingsObserver.mService
                                                        .setAccessibilitySlowKeysThreshold(
                                                                InputSettings
                                                                        .getAccessibilitySlowKeysThreshold(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 18:
                                                inputSettingsObserver.mService
                                                        .setAccessibilityStickyKeysEnabled(
                                                                InputSettings
                                                                        .isAccessibilityStickyKeysEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 19:
                                                inputSettingsObserver.mNative
                                                        .setStylusPointerIconEnabled(
                                                                InputSettings
                                                                        .isStylusPointerIconEnabled(
                                                                                inputSettingsObserver
                                                                                        .mContext,
                                                                                true));
                                                break;
                                            case 20:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService
                                                            .setPointerFillStyle(
                                                                    Settings.System.getIntForUser(
                                                                            inputSettingsObserver
                                                                                    .mContext
                                                                                    .getContentResolver(),
                                                                            "pointer_fill_style",
                                                                            0,
                                                                            -2));
                                                    break;
                                                }
                                                break;
                                            case 21:
                                                inputSettingsObserver.getClass();
                                                if (Flags.enableVectorCursorA11ySettings()) {
                                                    inputSettingsObserver.mService.setPointerScale(
                                                            Settings.System.getFloatForUser(
                                                                    inputSettingsObserver.mContext
                                                                            .getContentResolver(),
                                                                    "pointer_scale",
                                                                    1.0f,
                                                                    -2));
                                                    break;
                                                }
                                                break;
                                            case 22:
                                                int intForUser =
                                                        Settings.Secure.getIntForUser(
                                                                inputSettingsObserver.mContext
                                                                        .getContentResolver(),
                                                                "long_press_timeout",
                                                                400,
                                                                -2);
                                                boolean z2 =
                                                        DeviceConfig.getBoolean(
                                                                "input_native_boot",
                                                                "deep_press_enabled",
                                                                true);
                                                boolean z3 = z2 && intForUser <= 400;
                                                StringBuilder sb = new StringBuilder();
                                                DirEncryptServiceHelper$$ExternalSyntheticOutline0
                                                        .m(
                                                                sb,
                                                                z3 ? "Enabling" : "Disabling",
                                                                " motion classifier because ",
                                                                str,
                                                                ": feature ");
                                                Log.i(
                                                        "InputManager",
                                                        ActiveServices$$ExternalSyntheticOutline0.m(
                                                                intForUser,
                                                                z2 ? "enabled" : "disabled",
                                                                ", long press timeout = ",
                                                                " ms",
                                                                sb));
                                                inputSettingsObserver.mNative
                                                        .setMotionClassifierEnabled(z3);
                                                break;
                                            case 23:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadPointerSpeed(
                                                                Math.min(
                                                                        Math.max(
                                                                                InputSettings
                                                                                        .getTouchpadPointerSpeed(
                                                                                                inputSettingsObserver
                                                                                                        .mContext),
                                                                                -7),
                                                                        7));
                                                break;
                                            case 24:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadNaturalScrollingEnabled(
                                                                InputSettings
                                                                        .useTouchpadNaturalScrolling(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 25:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapToClickEnabled(
                                                                InputSettings.useTouchpadTapToClick(
                                                                        inputSettingsObserver
                                                                                .mContext));
                                                break;
                                            case 26:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadTapDraggingEnabled(
                                                                InputSettings
                                                                        .useTouchpadTapDragging(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 27:
                                                inputSettingsObserver.mNative
                                                        .setTouchpadRightClickZoneEnabled(
                                                                InputSettings
                                                                        .useTouchpadRightClickZone(
                                                                                inputSettingsObserver
                                                                                        .mContext));
                                                break;
                                            case 28:
                                                inputSettingsObserver.mNative.setShowTouches(
                                                        inputSettingsObserver.getBoolean(
                                                                "show_touches"));
                                                break;
                                            default:
                                                inputSettingsObserver.mService
                                                        .updatePointerLocationEnabled(
                                                                inputSettingsObserver.getBoolean(
                                                                        "pointer_location"));
                                                break;
                                        }
                                    }
                                }));
    }

    public final boolean getBoolean(String str) {
        return Settings.System.getIntForUser(this.mContext.getContentResolver(), str, 0, -2) != 0;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        ((Consumer) this.mObservers.get(uri)).accept("setting changed");
    }

    public final void updateKeyRepeatInfo() {
        this.mNative.setKeyRepeatConfiguration(
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "key_repeat_timeout",
                        ViewConfiguration.getKeyRepeatTimeout(),
                        -2),
                Settings.Secure.getIntForUser(
                        this.mContext.getContentResolver(),
                        "key_repeat_delay",
                        ViewConfiguration.getKeyRepeatDelay(),
                        -2));
    }

    public final void updatePointerIconCustomFromSettings() {
        int intForUser =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "mouse_pointer_color", 16777215, -2);
        float intForUser2 =
                (Settings.System.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "mouse_pointer_size_step",
                                        1,
                                        -2)
                                + 1.0f)
                        / 2.0f;
        Log.i(
                "InputManager",
                "updatePointerIcon color=0x"
                        + Integer.toHexString(intForUser)
                        + " size="
                        + intForUser2);
        PointerIcon.setCustomIcons(intForUser, intForUser2);
        this.mService.setCustomPointerIcons(intForUser, intForUser2);
    }

    public final void updateShowHoveringFromSettings() {
        boolean z = false;
        try {
            if (Settings.System.getIntForUser(
                            this.mContext.getContentResolver(), "pen_hovering_pointer", 0, -2)
                    == 1) {
                z = true;
            }
        } catch (Exception e) {
            Log.e("InputManager", "Exception getShowHoveringSetting 'false'", e);
        }
        this.mNative.setShowHovering(z);
    }
}
