package com.android.systemui.statusbar.model;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.hardware.input.InputDeviceIdentifier;
import android.hardware.input.InputManager;
import android.provider.DeviceConfig;
import android.view.InputDevice;
import android.view.KeyboardShortcutInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.Optional;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SamsungSystemShortcutsEnum {
    public static final /* synthetic */ SamsungSystemShortcutsEnum[] $VALUES = {new C26781(), new C26892(), new C26933(), new C26944(), new C26955(), new C26966(), new C26977(), new C26988(), new C26999(), new C267910(), new C268011(), new C268112(), new C268213(), new C268314(), new C268415(), new C268516(), new C268617(), new C268718(), new C268819(), new C269020(), new C269121(), new C269222()};

    /* JADX INFO: Fake field, exist only in values array */
    SamsungSystemShortcutsEnum EF3;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$1 */
    public enum C26781 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26781() {
            this("SYSTEM_HOME", 0);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_home), 66, 65536));
        }

        private C26781(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$10 */
    public enum C267910 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C267910() {
            this("SYSTEM_MICROPHONE_ACCESS_ON_OFF", 9);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return ((SensorPrivacyManager) kshDataUtils.mContext.getSystemService("sensor_privacy")).supportsSensorToggle(1) && DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true) ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_microphone_access_on_off), 29, 65537)) : Optional.empty();
        }

        private C267910(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$11 */
    public enum C268011 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268011() {
            this("SYSTEM_LOCK_SCREEN", 10);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_lock_screen), 40, 65537));
        }

        private C268011(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$12 */
    public enum C268112 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268112() {
            this("SYSTEM_SCREEN_OFF", 11);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_screen_off), 40, 65536));
        }

        private C268112(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$13 */
    public enum C268213 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268213() {
            this("SYSTEM_CAPTURE_WINDOW", 12);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_capture_window), 120, 2));
        }

        private C268213(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$14 */
    public enum C268314 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268314() {
            this("SYSTEM_SWITCH_LANGUAGES_SPACE_SHIFT_ON", 13);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_switch_languages), 62, 1));
        }

        private C268314(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$15 */
    public enum C268415 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268415() {
            this("SYSTEM_SWITCH_LANGUAGES_SPACE_CTRL_ON", 14);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_switch_languages), 62, 4096));
        }

        private C268415(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$16 */
    public enum C268516 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268516() {
            this("SYSTEM_SWITCH_LANGUAGES_SHIFT_ALT_LEFT_ON", 15);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_switch_languages), 1, 16));
        }

        private C268516(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$17 */
    public enum C268617 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268617() {
            this("SYSTEM_START_EXIT_DEX_MODE", 16);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            kshDataUtils.getClass();
            return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP", false) ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_start_exit_dex_mode), 51, 65536)) : Optional.empty();
        }

        private C268617(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$18 */
    public enum C268718 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268718() {
            this("SYSTEM_MAXIMIZE_WINDOW", 17);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_maximize_window), 19, 69632));
        }

        private C268718(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$19 */
    public enum C268819 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C268819() {
            this("SYSTEM_POPUP_VIEW", 18);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_popup_view), 20, 69632));
        }

        private C268819(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$2 */
    public enum C26892 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26892() {
            this("SYSTEM_BACK", 1);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_back), 67, 65536));
        }

        private C26892(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$20 */
    public enum C269020 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C269020() {
            this("SYSTEM_SPLIT_SCREEN_VIEW_DPAD_LEFT", 19);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_split_screen_view), 21, 69632));
        }

        private C269020(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$21 */
    public enum C269121 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C269121() {
            this("SYSTEM_SPLIT_SCREEN_VIEW_DPAD_RIGHT", 20);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_split_screen_view), 22, 69632));
        }

        private C269121(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$22 */
    public enum C269222 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C269222() {
            this("SYSTEM_WIRELESS_KEYBOARD_SHARING", 21);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            InputManager inputManager = (InputManager) kshDataUtils.mContext.getSystemService("input");
            boolean z = false;
            for (int i : inputManager.getInputDeviceIds()) {
                InputDevice inputDevice = inputManager.getInputDevice(i);
                if (inputDevice != null && !inputDevice.isVirtual() && inputDevice.isFullKeyboard()) {
                    InputDeviceIdentifier identifier = inputDevice.getIdentifier();
                    int vendorId = identifier.getVendorId();
                    int productId = identifier.getProductId();
                    if ((vendorId == 1256 && productId == 41013) || "Tab S3 Book Cover Keyboard".equals(inputDevice.getName())) {
                        z = true;
                        break;
                    }
                }
            }
            return z ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_wireless_keyboard_sharing), 204, 65536)) : Optional.empty();
        }

        private C269222(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$3 */
    public enum C26933 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26933() {
            this("SYSTEM_RECENT", 2);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_recents), 61, 2));
        }

        private C26933(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$4 */
    public enum C26944 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26944() {
            this("SYSTEM_CLOSE_CURRENT_APP", 3);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_close_current_app), 134, 2));
        }

        private C26944(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$5 */
    public enum C26955 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26955() {
            this("SYSTEM_SHOW_EMOJIS", 4);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return BasicRune.KEYBOARD_SUPPORT_EMOJI_SHORTCUT ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_show_emojis), 56, 65536)) : Optional.empty();
        }

        private C26955(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$6 */
    public enum C26966 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26966() {
            this("SYSTEM_SHOW_KANJI_INPUT", 5);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            if (BasicRune.KEYBOARD_SUPPORT_EMOJI_SHORTCUT) {
                InputMethodSubtype currentInputMethodSubtype = ((InputMethodManager) kshDataUtils.mContext.getSystemService("input_method")).getCurrentInputMethodSubtype();
                String languageTag = currentInputMethodSubtype == null ? null : currentInputMethodSubtype.getLanguageTag();
                if (languageTag != null && languageTag.contains("ko")) {
                    return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_show_hanja_input), 55, 65536));
                }
            }
            return Optional.empty();
        }

        private C26966(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$7 */
    public enum C26977 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26977() {
            this("SYSTEM_KEYBOARD_SHORTCUTS", 6);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_keyboard_shortcuts), 76, 65536));
        }

        private C26977(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$8 */
    public enum C26988 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26988() {
            this("SYSTEM_NOTIFICATIONS", 7);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_notifications), 42, 65536));
        }

        private C26988(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$9 */
    public enum C26999 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ C26999() {
            this("SYSTEM_CAMERA_ACCESS_ON_OFF", 8);
        }

        /* JADX WARN: Code restructure failed: missing block: B:4:0x001c, code lost:
        
            if (android.provider.DeviceConfig.getBoolean("privacy", "camera_toggle_enabled", true) != false) goto L8;
         */
        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            boolean z = ((SensorPrivacyManager) kshDataUtils.mContext.getSystemService("sensor_privacy")).supportsSensorToggle(2);
            return z ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_camera_access_on_off), 50, 65537)) : Optional.empty();
        }

        private C26999(String str, int i) {
            super(str, i, 0);
        }
    }

    public /* synthetic */ SamsungSystemShortcutsEnum(String str, int i, int i2) {
        this(str, i);
    }

    public static SamsungSystemShortcutsEnum valueOf(String str) {
        return (SamsungSystemShortcutsEnum) Enum.valueOf(SamsungSystemShortcutsEnum.class, str);
    }

    public static SamsungSystemShortcutsEnum[] values() {
        return (SamsungSystemShortcutsEnum[]) $VALUES.clone();
    }

    public abstract Optional getKshInfo(Context context, KshDataUtils kshDataUtils);

    private SamsungSystemShortcutsEnum(String str, int i) {
    }
}
