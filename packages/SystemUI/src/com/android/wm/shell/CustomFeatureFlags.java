package com.android.wm.shell;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CustomFeatureFlags implements FeatureFlags {
    public final BiPredicate mGetValueImpl;

    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> biPredicate) {
        new HashSet(Arrays.asList("com.android.wm.shell.bubble_view_info_executors", "com.android.wm.shell.enable_auto_task_stack_controller", "com.android.wm.shell.enable_bubble_anything", "com.android.wm.shell.enable_bubble_bar", "com.android.wm.shell.enable_bubble_bar_on_phones", "com.android.wm.shell.enable_bubble_stashing", "com.android.wm.shell.enable_bubble_task_view_listener", "com.android.wm.shell.enable_bubble_to_fullscreen", "com.android.wm.shell.enable_bubbles_long_press_nav_handle", "com.android.wm.shell.enable_create_any_bubble", "com.android.wm.shell.enable_dynamic_insets_for_app_launch", "com.android.wm.shell.enable_flexible_split", "com.android.wm.shell.enable_flexible_two_app_split", "com.android.wm.shell.enable_gsf", "com.android.wm.shell.enable_magnetic_split_divider", "com.android.wm.shell.enable_new_bubble_animations", "com.android.wm.shell.enable_optional_bubble_overflow", "com.android.wm.shell.enable_pip2", "com.android.wm.shell.enable_pip_umo_experience", "com.android.wm.shell.enable_recents_bookend_transition", "com.android.wm.shell.enable_retrievable_bubbles", "com.android.wm.shell.enable_shell_top_task_tracking", "com.android.wm.shell.enable_task_view_controller_cleanup", "com.android.wm.shell.enable_taskbar_navbar_unification", "com.android.wm.shell.enable_taskbar_on_phones", "com.android.wm.shell.enable_tiny_taskbar", "com.android.wm.shell.fix_missing_user_change_callbacks", "com.android.wm.shell.only_reuse_bubbled_task_when_launched_from_bubble", "com.android.wm.shell.task_view_repository", ""));
        this.mGetValueImpl = biPredicate;
    }
}
