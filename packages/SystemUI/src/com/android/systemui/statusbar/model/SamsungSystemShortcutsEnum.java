package com.android.systemui.statusbar.model;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import android.provider.DeviceConfig;
import android.view.KeyboardShortcutInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.Optional;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class SamsungSystemShortcutsEnum {
    public static final /* synthetic */ SamsungSystemShortcutsEnum[] $VALUES = {new AnonymousClass1(), new AnonymousClass2(), new AnonymousClass3(), new AnonymousClass4(), new AnonymousClass5(), new AnonymousClass6(), new AnonymousClass7(), new AnonymousClass8(), new AnonymousClass9(), new AnonymousClass10(), new AnonymousClass11(), new AnonymousClass12(), new AnonymousClass13(), new AnonymousClass14(), new AnonymousClass15(), new AnonymousClass16(), new AnonymousClass17(), new AnonymousClass18(), new AnonymousClass19(), new AnonymousClass20(), new AnonymousClass21(), new AnonymousClass22(), new AnonymousClass23(), new AnonymousClass24(), new AnonymousClass25(), new AnonymousClass26(), new AnonymousClass27()};

    /* JADX INFO: Fake field, exist only in values array */
    SamsungSystemShortcutsEnum EF2;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$1, reason: invalid class name */
    enum AnonymousClass1 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass1() {
            this("SYSTEM_HOME", 0);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_home), 66, 65536));
        }

        private AnonymousClass1(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$10, reason: invalid class name */
    enum AnonymousClass10 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass10() {
            this("SYSTEM_NOTIFICATIONS", 9);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_notifications), 42, 65536));
        }

        private AnonymousClass10(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$11, reason: invalid class name */
    enum AnonymousClass11 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass11() {
            this("SYSTEM_CAMERA_ACCESS_ON_OFF", 10);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return (((SensorPrivacyManager) kshDataUtils.mContext.getSystemService("sensor_privacy")).supportsSensorToggle(2) && DeviceConfig.getBoolean("privacy", "camera_toggle_enabled", true)) ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_camera_access_on_off), 50, 65537)) : Optional.empty();
        }

        private AnonymousClass11(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$12, reason: invalid class name */
    enum AnonymousClass12 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass12() {
            this("SYSTEM_MICROPHONE_ACCESS_ON_OFF", 11);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return (((SensorPrivacyManager) kshDataUtils.mContext.getSystemService("sensor_privacy")).supportsSensorToggle(1) && DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true)) ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_microphone_access_on_off), 29, 65537)) : Optional.empty();
        }

        private AnonymousClass12(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$13, reason: invalid class name */
    enum AnonymousClass13 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass13() {
            this("SYSTEM_LOCK_SCREEN", 12);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_lock_screen), 40, 65537));
        }

        private AnonymousClass13(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$14, reason: invalid class name */
    enum AnonymousClass14 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass14() {
            this("SYSTEM_SCREEN_OFF", 13);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_screen_off), 40, 65536));
        }

        private AnonymousClass14(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$15, reason: invalid class name */
    enum AnonymousClass15 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass15() {
            this("SYSTEM_CAPTURE_WINDOW", 14);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_capture_window), 120, 2));
        }

        private AnonymousClass15(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$16, reason: invalid class name */
    enum AnonymousClass16 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass16() {
            this("SYSTEM_APPS", 15);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_apps), 0, 65536));
        }

        private AnonymousClass16(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$17, reason: invalid class name */
    enum AnonymousClass17 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass17() {
            this("SYSTEM_SWITCH_LANGUAGES_SPACE_SHIFT_ON", 16);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_switch_languages), 62, 1));
        }

        private AnonymousClass17(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$18, reason: invalid class name */
    enum AnonymousClass18 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass18() {
            this("SYSTEM_SWITCH_LANGUAGES_SPACE_CTRL_ON", 17);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_switch_languages), 62, 4096));
        }

        private AnonymousClass18(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$19, reason: invalid class name */
    enum AnonymousClass19 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass19() {
            this("SYSTEM_SWITCH_LANGUAGES_SHIFT_ALT_LEFT_ON", 18);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_switch_languages), 1, 16));
        }

        private AnonymousClass19(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$2, reason: invalid class name */
    enum AnonymousClass2 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass2() {
            this("SYSTEM_BACK", 1);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_back), 21, 65536));
        }

        private AnonymousClass2(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$20, reason: invalid class name */
    enum AnonymousClass20 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass20() {
            this("SYSTEM_START_EXIT_DEX_MODE", 19);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            kshDataUtils.getClass();
            return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_DESKTOP_WINDOWING", false) ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_start_exit_dex_mode), 51, 65536)) : Optional.empty();
        }

        private AnonymousClass20(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$21, reason: invalid class name */
    enum AnonymousClass21 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass21() {
            this("SYSTEM_MAXIMIZE_WINDOW", 20);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return kshDataUtils.isDexDisplay() ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_maximize_window), 70, 65536)) : Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_maximize_window), 19, 69632));
        }

        private AnonymousClass21(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$22, reason: invalid class name */
    enum AnonymousClass22 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass22() {
            this("SYSTEM_POPUP_VIEW", 21);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return !kshDataUtils.isDexDisplay() ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_popup_view), 20, 69632)) : Optional.empty();
        }

        private AnonymousClass22(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$23, reason: invalid class name */
    enum AnonymousClass23 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass23() {
            this("SYSTEM_MINIMIZE_WINDOW", 22);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return kshDataUtils.isDexDisplay() ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_minimize_window), 69, 65536)) : Optional.empty();
        }

        private AnonymousClass23(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$24, reason: invalid class name */
    enum AnonymousClass24 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass24() {
            this("SYSTEM_SNAP_WINDOW_TO_LEFT", 23);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return kshDataUtils.isDexDisplay() ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_snap_window_to_left), 71, 65536)) : Optional.empty();
        }

        private AnonymousClass24(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$25, reason: invalid class name */
    enum AnonymousClass25 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass25() {
            this("SYSTEM_SNAP_WINDOW_TO_RIGHT", 24);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return kshDataUtils.isDexDisplay() ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_snap_window_to_right), 72, 65536)) : Optional.empty();
        }

        private AnonymousClass25(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$26, reason: invalid class name */
    enum AnonymousClass26 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass26() {
            this("SYSTEM_SPLIT_SCREEN_VIEW_DPAD_LEFT", 25);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return !kshDataUtils.isDexDisplay() ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_split_screen_view), 21, 69632)) : Optional.empty();
        }

        private AnonymousClass26(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$27, reason: invalid class name */
    enum AnonymousClass27 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass27() {
            this("SYSTEM_SPLIT_SCREEN_VIEW_DPAD_RIGHT", 26);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return !kshDataUtils.isDexDisplay() ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_split_screen_view), 22, 69632)) : Optional.empty();
        }

        private AnonymousClass27(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$3, reason: invalid class name */
    enum AnonymousClass3 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass3() {
            this("SYSTEM_RECENT", 2);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_recents), 61, 2));
        }

        private AnonymousClass3(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$4, reason: invalid class name */
    enum AnonymousClass4 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass4() {
            this("SYSTEM_SWITCH_AI_APPS", 3);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_ai_key), 187, 2));
        }

        private AnonymousClass4(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$5, reason: invalid class name */
    enum AnonymousClass5 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass5() {
            this("SYSTEM_AI_KEY", 4);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_key_ai_hot), 43, 65536));
        }

        private AnonymousClass5(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$6, reason: invalid class name */
    enum AnonymousClass6 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass6() {
            this("SYSTEM_CLOSE_CURRENT_APP", 5);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_close_current_app), 134, 2));
        }

        private AnonymousClass6(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$7, reason: invalid class name */
    enum AnonymousClass7 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass7() {
            this("SYSTEM_SHOW_EMOJIS", 6);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return BasicRune.KEYBOARD_SUPPORT_EMOJI_SHORTCUT ? Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_show_emojis), 56, 65536)) : Optional.empty();
        }

        private AnonymousClass7(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$8, reason: invalid class name */
    enum AnonymousClass8 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass8() {
            this("SYSTEM_SHOW_KANJI_INPUT", 7);
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

        private AnonymousClass8(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum$9, reason: invalid class name */
    enum AnonymousClass9 extends SamsungSystemShortcutsEnum {
        public /* synthetic */ AnonymousClass9() {
            this("SYSTEM_KEYBOARD_SHORTCUTS", 8);
        }

        @Override // com.android.systemui.statusbar.model.SamsungSystemShortcutsEnum
        public final Optional getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return Optional.of(new KeyboardShortcutInfo(context.getString(R.string.ksh_group_system_keyboard_shortcuts), 76, 65536));
        }

        private AnonymousClass9(String str, int i) {
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
