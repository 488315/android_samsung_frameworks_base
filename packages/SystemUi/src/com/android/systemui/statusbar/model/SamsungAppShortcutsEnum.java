package com.android.systemui.statusbar.model;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Pair;
import android.view.KeyboardShortcutInfo;
import com.android.systemui.R;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class SamsungAppShortcutsEnum {
    public static final /* synthetic */ SamsungAppShortcutsEnum[] $VALUES = {new C26621(), new C26702(), new C26713(), new C26724(), new C26735(), new C26746(), new C26757(), new C26768(), new C26779(), new C266310(), new C266411(), new C266512(), new C266613(), new C266714(), new C266815(), new C266916()};

    /* JADX INFO: Fake field, exist only in values array */
    SamsungAppShortcutsEnum EF2;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$1 */
    public enum C26621 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26621() {
            this("CMD_A", 0);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_a");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 29, 65536);
        }

        private C26621(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$10 */
    public enum C266310 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C266310() {
            this("CMD_K", 9);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_k");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 39, 65536);
        }

        private C266310(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$11 */
    public enum C266411 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C266411() {
            this("CMD_M", 10);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_m");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 41, 65536);
        }

        private C266411(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$12 */
    public enum C266512 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C266512() {
            this("CMD_P", 11);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_p");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 44, 65536);
        }

        private C266512(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$13 */
    public enum C266613 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C266613() {
            this("CMD_R", 12);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_r");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 46, 65536);
        }

        private C266613(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$14 */
    public enum C266714 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C266714() {
            this("CMD_S", 13);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_s");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 47, 65536);
        }

        private C266714(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$15 */
    public enum C266815 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C266815() {
            this("CMD_Y", 14);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            return new KeyboardShortcutInfo(context.getString(R.string.ksh_group_applications_smart_view), kshDataUtils.getIconForPackageName("com.samsung.android.smartmirroring"), 53, 65536);
        }

        private C266815(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$16 */
    public enum C266916 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C266916() {
            this("CMD_Z", 15);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_z");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 54, 65536);
        }

        private C266916(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$2 */
    public enum C26702 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26702() {
            this("CMD_B", 1);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_b");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 30, 65536);
        }

        private C26702(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$3 */
    public enum C26713 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26713() {
            this("CMD_C", 2);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_c");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 31, 65536);
        }

        private C26713(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$4 */
    public enum C26724 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26724() {
            this("CMD_D", 3);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_d");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 32, 65536);
        }

        private C26724(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$5 */
    public enum C26735 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26735() {
            this("CMD_E", 4);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_e");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 33, 65536);
        }

        private C26735(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$6 */
    public enum C26746 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26746() {
            this("CMD_F", 5);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_f");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 34, 65536);
        }

        private C26746(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$7 */
    public enum C26757 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26757() {
            this("CMD_H", 6);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_h");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 36, 65536);
        }

        private C26757(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$8 */
    public enum C26768 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26768() {
            this("CMD_I", 7);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_i");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 37, 65536);
        }

        private C26768(String str, int i) {
            super(str, i, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.model.SamsungAppShortcutsEnum$9 */
    public enum C26779 extends SamsungAppShortcutsEnum {
        public /* synthetic */ C26779() {
            this("CMD_J", 8);
        }

        @Override // com.android.systemui.statusbar.model.SamsungAppShortcutsEnum
        public final KeyboardShortcutInfo getKshInfo(Context context, KshDataUtils kshDataUtils) {
            Pair packageInfoForSetting = kshDataUtils.getPackageInfoForSetting("app_shortcuts_command_j");
            return new KeyboardShortcutInfo((CharSequence) packageInfoForSetting.first, (Icon) packageInfoForSetting.second, 38, 65536);
        }

        private C26779(String str, int i) {
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
