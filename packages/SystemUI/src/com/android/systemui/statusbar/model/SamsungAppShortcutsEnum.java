package com.android.systemui.statusbar.model;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Pair;
import android.view.KeyboardShortcutInfo;
import com.android.systemui.R;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
public abstract class SamsungAppShortcutsEnum {
    public static final /* synthetic */ SamsungAppShortcutsEnum[] $VALUES = {new AnonymousClass1(), new AnonymousClass2(), new AnonymousClass3(), new AnonymousClass4(), new AnonymousClass5(), new AnonymousClass6(), new AnonymousClass7(), new AnonymousClass8(), new AnonymousClass9(), new AnonymousClass10(), new AnonymousClass11(), new AnonymousClass12(), new AnonymousClass13(), new AnonymousClass14(), new AnonymousClass15(), new AnonymousClass16()};

    /* JADX INFO: Fake field, exist only in values array */
    SamsungAppShortcutsEnum EF2;

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$1, reason: invalid class name */
    enum AnonymousClass1 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass1() {
            this("CMD_A", 0);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_a");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 29, 65536);
        }

        private AnonymousClass1(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$10, reason: invalid class name */
    enum AnonymousClass10 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass10() {
            this("CMD_K", 9);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_k");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 39, 65536);
        }

        private AnonymousClass10(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$11, reason: invalid class name */
    enum AnonymousClass11 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass11() {
            this("CMD_M", 10);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_m");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 41, 65536);
        }

        private AnonymousClass11(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$12, reason: invalid class name */
    enum AnonymousClass12 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass12() {
            this("CMD_P", 11);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_p");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 44, 65536);
        }

        private AnonymousClass12(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$13, reason: invalid class name */
    enum AnonymousClass13 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass13() {
            this("CMD_R", 12);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_r");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 46, 65536);
        }

        private AnonymousClass13(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$14, reason: invalid class name */
    enum AnonymousClass14 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass14() {
            this("CMD_S", 13);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_s");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 47, 65536);
        }

        private AnonymousClass14(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$15, reason: invalid class name */
    enum AnonymousClass15 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass15() {
            this("CMD_Y", 14);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return new KeyboardShortcutInfo(context.getString(R.string.ksh_group_applications_smart_view), kshDataUtils.getIconForPackageName("com.samsung.android.smartmirroring"), 53, 65536);
        }

        private AnonymousClass15(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$16, reason: invalid class name */
    enum AnonymousClass16 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass16() {
            this("CMD_Z", 15);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_z");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 54, 65536);
        }

        private AnonymousClass16(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$2, reason: invalid class name */
    enum AnonymousClass2 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass2() {
            this("CMD_B", 1);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_b");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 30, 65536);
        }

        private AnonymousClass2(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$3, reason: invalid class name */
    enum AnonymousClass3 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass3() {
            this("CMD_C", 2);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_c");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 31, 65536);
        }

        private AnonymousClass3(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$4, reason: invalid class name */
    enum AnonymousClass4 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass4() {
            this("CMD_D", 3);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_d");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 32, 65536);
        }

        private AnonymousClass4(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$5, reason: invalid class name */
    enum AnonymousClass5 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass5() {
            this("CMD_E", 4);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_e");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 33, 65536);
        }

        private AnonymousClass5(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$6, reason: invalid class name */
    enum AnonymousClass6 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass6() {
            this("CMD_F", 5);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_f");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 34, 65536);
        }

        private AnonymousClass6(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$7, reason: invalid class name */
    enum AnonymousClass7 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass7() {
            this("CMD_H", 6);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_h");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 36, 65536);
        }

        private AnonymousClass7(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$8, reason: invalid class name */
    enum AnonymousClass8 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass8() {
            this("CMD_I", 7);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_i");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 37, 65536);
        }

        private AnonymousClass8(String str, int i) {
            super(str, i, 0);
        }
    }

    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$9, reason: invalid class name */
    enum AnonymousClass9 extends SamsungAppShortcutsEnum {
        public /* synthetic */ AnonymousClass9() {
            this("CMD_J", 8);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_j");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 38, 65536);
        }

        private AnonymousClass9(String str, int i) {
            super(str, i, 0);
        }
    }

    public /* synthetic */ SamsungAppShortcutsEnum(String str, int i, int i2) {
        this(str, i);
    }

    public static SamsungAppShortcutsEnum valueOf(String str) {
        return (SamsungAppShortcutsEnum) Enum.valueOf(SamsungAppShortcutsEnum.class, str);
    }

    public static SamsungAppShortcutsEnum[] values() {
        return (SamsungAppShortcutsEnum[]) $VALUES.clone();
    }

    public abstract KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils);

    private SamsungAppShortcutsEnum(String str, int i) {
    }
}
