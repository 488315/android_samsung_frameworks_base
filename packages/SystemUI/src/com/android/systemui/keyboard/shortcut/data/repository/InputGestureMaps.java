package com.android.systemui.keyboard.shortcut.data.repository;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InputGestureMaps {
    public final Context context;
    public final Map gestureToInternalKeyboardShortcutGroupLabelResIdMap;
    public final Map gestureToInternalKeyboardShortcutInfoLabelResIdMap;
    public final Map gestureToShortcutCategoryTypeMap;

    public InputGestureMaps(Context context) {
        this.context = context;
        ShortcutCategoryType.System system = ShortcutCategoryType.System.INSTANCE;
        Pair pair = new Pair(1, system);
        Pair pair2 = new Pair(2, system);
        Pair pair3 = new Pair(3, system);
        Pair pair4 = new Pair(10, system);
        Pair pair5 = new Pair(12, system);
        Pair pair6 = new Pair(8, system);
        Pair pair7 = new Pair(32, system);
        Pair pair8 = new Pair(7, system);
        Pair pair9 = new Pair(5, system);
        Pair pair10 = new Pair(6, system);
        Pair pair11 = new Pair(21, system);
        ShortcutCategoryType.MultiTasking multiTasking = ShortcutCategoryType.MultiTasking.INSTANCE;
        Pair pair12 = new Pair(54, multiTasking);
        Pair pair13 = new Pair(27, multiTasking);
        Pair pair14 = new Pair(28, multiTasking);
        Pair pair15 = new Pair(53, multiTasking);
        Pair pair16 = new Pair(29, multiTasking);
        Pair pair17 = new Pair(30, multiTasking);
        Pair pair18 = new Pair(68, multiTasking);
        Pair pair19 = new Pair(69, multiTasking);
        Pair pair20 = new Pair(70, multiTasking);
        Pair pair21 = new Pair(71, multiTasking);
        Pair pair22 = new Pair(62, multiTasking);
        Pair pair23 = new Pair(51, ShortcutCategoryType.AppCategories.INSTANCE);
        ShortcutCategoryType.Accessibility accessibility = ShortcutCategoryType.Accessibility.INSTANCE;
        this.gestureToShortcutCategoryTypeMap = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, pair9, pair10, pair11, pair12, pair13, pair14, pair15, pair16, pair17, pair18, pair19, pair20, pair21, pair22, pair23, new Pair(65, accessibility), new Pair(67, accessibility), new Pair(64, accessibility), new Pair(66, accessibility), new Pair(76, accessibility), new Pair(63, accessibility), new Pair(72, accessibility), new Pair(73, accessibility));
        this.gestureToInternalKeyboardShortcutGroupLabelResIdMap = MapsKt__MapsKt.mapOf(new Pair(1, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(2, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(3, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(10, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(12, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(8, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(32, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(21, Integer.valueOf(R.string.shortcut_helper_category_system_controls)), new Pair(7, Integer.valueOf(R.string.shortcut_helper_category_system_apps)), new Pair(5, Integer.valueOf(R.string.shortcut_helper_category_system_apps)), new Pair(6, Integer.valueOf(R.string.shortcut_helper_category_system_apps)), new Pair(27, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(28, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(53, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(29, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(30, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(68, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(69, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(70, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(71, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(62, Integer.valueOf(R.string.shortcutHelper_category_split_screen)), new Pair(51, Integer.valueOf(R.string.keyboard_shortcut_group_applications)), new Pair(65, Integer.valueOf(R.string.shortcutHelper_category_accessibility)), new Pair(67, Integer.valueOf(R.string.shortcutHelper_category_accessibility)), new Pair(64, Integer.valueOf(R.string.shortcutHelper_category_accessibility)), new Pair(66, Integer.valueOf(R.string.shortcutHelper_category_accessibility)), new Pair(76, Integer.valueOf(R.string.shortcutHelper_category_accessibility)), new Pair(63, Integer.valueOf(R.string.shortcutHelper_category_accessibility)), new Pair(72, Integer.valueOf(R.string.shortcutHelper_category_accessibility)), new Pair(73, Integer.valueOf(R.string.shortcutHelper_category_accessibility)));
        this.gestureToInternalKeyboardShortcutInfoLabelResIdMap = MapsKt__MapsKt.mapOf(new Pair(1, Integer.valueOf(R.string.group_system_access_home_screen)), new Pair(2, Integer.valueOf(R.string.group_system_overview_open_apps)), new Pair(3, Integer.valueOf(R.string.group_system_go_back)), new Pair(10, Integer.valueOf(R.string.group_system_full_screenshot)), new Pair(12, Integer.valueOf(R.string.group_system_access_system_app_shortcuts)), new Pair(8, Integer.valueOf(R.string.group_system_access_notification_shade)), new Pair(32, Integer.valueOf(R.string.group_system_lock_screen)), new Pair(21, Integer.valueOf(R.string.group_system_access_all_apps_search)), new Pair(7, Integer.valueOf(R.string.group_system_access_system_settings)), new Pair(5, Integer.valueOf(R.string.group_system_access_google_assistant)), new Pair(6, Integer.valueOf(R.string.group_system_access_google_assistant)), new Pair(54, Integer.valueOf(R.string.group_system_cycle_forward)), new Pair(27, Integer.valueOf(R.string.system_multitasking_lhs)), new Pair(28, Integer.valueOf(R.string.system_multitasking_rhs)), new Pair(53, Integer.valueOf(R.string.system_multitasking_full_screen)), new Pair(68, Integer.valueOf(R.string.system_desktop_mode_snap_left_window)), new Pair(69, Integer.valueOf(R.string.system_desktop_mode_snap_right_window)), new Pair(70, Integer.valueOf(R.string.system_desktop_mode_minimize_window)), new Pair(71, Integer.valueOf(R.string.system_desktop_mode_toggle_maximize_window)), new Pair(62, Integer.valueOf(R.string.system_multitasking_move_to_next_display)), new Pair(65, Integer.valueOf(R.string.group_accessibility_toggle_bounce_keys)), new Pair(67, Integer.valueOf(R.string.group_accessibility_toggle_mouse_keys)), new Pair(64, Integer.valueOf(R.string.group_accessibility_toggle_sticky_keys)), new Pair(66, Integer.valueOf(R.string.group_accessibility_toggle_slow_keys)), new Pair(76, Integer.valueOf(R.string.group_accessibility_toggle_voice_access)), new Pair(63, Integer.valueOf(R.string.group_accessibility_toggle_talkback)), new Pair(72, Integer.valueOf(R.string.group_accessibility_toggle_magnification)), new Pair(73, Integer.valueOf(R.string.group_accessibility_activate_select_to_speak)));
    }
}
