package com.android.systemui.keyboard.shortcut.data.repository;

import android.content.Context;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.settings.ImsProfile;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShortcutHelperKeys {
    public static final Map modifierLabels;
    public static final Map specialKeyLabels;
    public static final ShortcutHelperKeys INSTANCE = new ShortcutHelperKeys();
    public static final int metaModifierIconResId = R.drawable.ic_ksh_key_meta;
    public static final Map keyIcons = MapsKt__MapsKt.mapOf(new Pair(4, Integer.valueOf(R.drawable.ic_arrow_back_2)), new Pair(3, Integer.valueOf(R.drawable.ic_radio_button_unchecked)), new Pair(312, Integer.valueOf(R.drawable.ic_check_box_outline_blank)));
    public static final Map keyLabelResIds = MapsKt__MapsKt.mapOf(new Pair(4, Integer.valueOf(R.string.group_system_go_back)), new Pair(3, Integer.valueOf(R.string.group_system_access_home_screen)), new Pair(312, Integer.valueOf(R.string.group_system_overview_open_apps)), new Pair(318, Integer.valueOf(R.string.group_system_full_screenshot)));

    static {
        final int i = 0;
        Pair pair = new Pair(65536, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i2 = 2;
        Pair pair2 = new Pair(4096, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i2) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i3 = 14;
        Pair pair3 = new Pair(2, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i3) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i4 = 26;
        Pair pair4 = new Pair(1, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i4) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i5 = 8;
        final int i6 = 20;
        modifierLabels = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, new Pair(4, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i5) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        }), new Pair(8, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i6) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        }));
        final int i7 = 3;
        Pair pair5 = new Pair(3, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i7) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i8 = 14;
        Pair pair6 = new Pair(4, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i8) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i9 = 25;
        Pair pair7 = new Pair(312, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i9) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i10 = 26;
        Pair pair8 = new Pair(19, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i10) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i11 = 11;
        Pair pair9 = new Pair(20, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i11) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i12 = 22;
        Pair pair10 = new Pair(21, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i12) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i13 = 0;
        Pair pair11 = new Pair(22, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i13) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i14 = 14;
        Pair pair12 = new Pair(23, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i14) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i15 = 25;
        Pair pair13 = new Pair(56, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i15) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i16 = 0;
        Pair pair14 = new Pair(61, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i16) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i17 = 17;
        Pair pair15 = new Pair(62, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i17) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i18 = 27;
        Pair pair16 = new Pair(66, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i18) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i19 = 28;
        Pair pair17 = new Pair(67, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i19) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i20 = 1;
        Pair pair18 = new Pair(85, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i20) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i21 = 3;
        Pair pair19 = new Pair(86, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i21) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i22 = 4;
        Pair pair20 = new Pair(87, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i22) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i23 = 5;
        Pair pair21 = new Pair(88, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i23) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i24 = 6;
        Pair pair22 = new Pair(89, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i24) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i25 = 7;
        Pair pair23 = new Pair(90, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i25) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i26 = 8;
        Pair pair24 = new Pair(92, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i26) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i27 = 9;
        Pair pair25 = new Pair(93, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i27) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i28 = 10;
        Pair pair26 = new Pair(96, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i28) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i29 = 12;
        Pair pair27 = new Pair(97, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i29) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i30 = 13;
        Pair pair28 = new Pair(98, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i30) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i31 = 15;
        Pair pair29 = new Pair(99, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i31) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i32 = 16;
        Pair pair30 = new Pair(100, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i32) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i33 = 17;
        Pair pair31 = new Pair(101, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i33) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i34 = 18;
        Pair pair32 = new Pair(102, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i34) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i35 = 19;
        Pair pair33 = new Pair(103, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i35) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i36 = 20;
        Pair pair34 = new Pair(104, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i36) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i37 = 21;
        Pair pair35 = new Pair(105, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i37) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i38 = 23;
        Pair pair36 = new Pair(108, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i38) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i39 = 24;
        Pair pair37 = new Pair(109, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i39) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i40 = 25;
        Pair pair38 = new Pair(110, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i40) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i41 = 27;
        Pair pair39 = new Pair(112, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i41) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i42 = 28;
        Pair pair40 = new Pair(111, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i42) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i43 = 29;
        Pair pair41 = new Pair(120, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i43) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i44 = 1;
        Pair pair42 = new Pair(121, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i44) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i45 = 2;
        Pair pair43 = new Pair(116, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i45) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i46 = 3;
        Pair pair44 = new Pair(122, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i46) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i47 = 4;
        Pair pair45 = new Pair(123, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i47) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i48 = 5;
        Pair pair46 = new Pair(124, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i48) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i49 = 6;
        Pair pair47 = new Pair(131, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i49) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i50 = 7;
        Pair pair48 = new Pair(132, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i50) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i51 = 9;
        Pair pair49 = new Pair(133, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i51) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i52 = 10;
        Pair pair50 = new Pair(134, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i52) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i53 = 11;
        Pair pair51 = new Pair(135, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i53) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i54 = 12;
        Pair pair52 = new Pair(136, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i54) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i55 = 13;
        Pair pair53 = new Pair(137, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i55) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i56 = 15;
        Pair pair54 = new Pair(138, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i56) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i57 = 16;
        Pair pair55 = new Pair(139, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i57) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i58 = 17;
        Pair pair56 = new Pair(140, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i58) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i59 = 18;
        Pair pair57 = new Pair(141, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i59) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i60 = 19;
        Pair pair58 = new Pair(142, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i60) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i61 = 21;
        Pair pair59 = new Pair(143, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i61) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i62 = 22;
        Pair pair60 = new Pair(69, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i62) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i63 = 23;
        Pair pair61 = new Pair(68, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i63) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i64 = 24;
        Pair pair62 = new Pair(70, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i64) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i65 = 26;
        Pair pair63 = new Pair(144, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i65) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i66 = 27;
        Pair pair64 = new Pair(145, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i66) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i67 = 28;
        Pair pair65 = new Pair(146, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i67) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i68 = 29;
        Pair pair66 = new Pair(147, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i68) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_right);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return "Break";
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Scroll Lock";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_move_end);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_insert);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return "F1";
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return "F2";
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return "Sym";
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return "F3";
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return "F4";
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return "F5";
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return "F6";
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return "F7";
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_center);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return "F8";
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return "F9";
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return "F10";
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return "F11";
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return "F12";
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "Fn";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_num_lock);
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "-";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "`";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "=";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return ".";
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "0");
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "1");
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "2");
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "3");
                }
            }
        });
        final int i69 = 1;
        Pair pair67 = new Pair(148, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i69) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i70 = 2;
        Pair pair68 = new Pair(149, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i70) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i71 = 4;
        Pair pair69 = new Pair(150, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i71) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i72 = 5;
        Pair pair70 = new Pair(151, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i72) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i73 = 6;
        Pair pair71 = new Pair(152, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i73) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i74 = 7;
        Pair pair72 = new Pair(153, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i74) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i75 = 8;
        Pair pair73 = new Pair(154, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i75) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i76 = 9;
        Pair pair74 = new Pair(155, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i76) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i77 = 10;
        Pair pair75 = new Pair(156, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i77) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i78 = 11;
        Pair pair76 = new Pair(157, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i78) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i79 = 12;
        Pair pair77 = new Pair(158, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i79) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i80 = 13;
        Pair pair78 = new Pair(159, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i80) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i81 = 15;
        Pair pair79 = new Pair(160, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i81) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i82 = 16;
        Pair pair80 = new Pair(161, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i82) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i83 = 18;
        Pair pair81 = new Pair(162, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i83) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i84 = 19;
        Pair pair82 = new Pair(163, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i84) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i85 = 20;
        Pair pair83 = new Pair(Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState), new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i85) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i86 = 21;
        Pair pair84 = new Pair(Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_getWifiState), new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i86) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i87 = 22;
        Pair pair85 = new Pair(Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber), new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i87) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i88 = 23;
        Pair pair86 = new Pair(Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber), new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i88) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i89 = 24;
        Pair pair87 = new Pair(Integer.valueOf(IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberDelay), new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i89) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_tab);
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "4");
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "5");
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_home);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "6");
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "7");
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "8");
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "9");
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "/");
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "*");
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "-");
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "+");
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ".");
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ",");
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_back);
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter));
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "=");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_space);
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, "(");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_numpad_template, ")");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return "半角/全角";
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return "英数";
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return "無変換";
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return "変換";
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return "かな";
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.accessibility_recent);
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_up);
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_enter);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_backspace);
                }
            }
        });
        final int i90 = 14;
        Pair pair88 = new Pair(57, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i90) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i91 = 14;
        Pair pair89 = new Pair(58, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i91) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i92 = 2;
        Pair pair90 = new Pair(113, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i92) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i93 = 2;
        Pair pair91 = new Pair(114, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i93) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i94 = 26;
        Pair pair92 = new Pair(59, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i94) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        });
        final int i95 = 26;
        specialKeyLabels = MapsKt__MapsKt.mapOf(pair5, pair6, pair7, pair8, pair9, pair10, pair11, pair12, pair13, pair14, pair15, pair16, pair17, pair18, pair19, pair20, pair21, pair22, pair23, pair24, pair25, pair26, pair27, pair28, pair29, pair30, pair31, pair32, pair33, pair34, pair35, pair36, pair37, pair38, pair39, pair40, pair41, pair42, pair43, pair44, pair45, pair46, pair47, pair48, pair49, pair50, pair51, pair52, pair53, pair54, pair55, pair56, pair57, pair58, pair59, pair60, pair61, pair62, pair63, pair64, pair65, pair66, pair67, pair68, pair69, pair70, pair71, pair72, pair73, pair74, pair75, pair76, pair77, pair78, pair79, pair80, pair81, pair82, pair83, pair84, pair85, pair86, pair87, pair88, pair89, pair90, pair91, pair92, new Pair(60, new Function1() { // from class: com.android.systemui.keyboard.shortcut.data.repository.ShortcutHelperKeys$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Context context = (Context) obj;
                switch (i95) {
                    case 0:
                        ShortcutHelperKeys shortcutHelperKeys = ShortcutHelperKeys.INSTANCE;
                        return "Meta";
                    case 1:
                        ShortcutHelperKeys shortcutHelperKeys2 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_play_pause);
                    case 2:
                        ShortcutHelperKeys shortcutHelperKeys3 = ShortcutHelperKeys.INSTANCE;
                        return "Ctrl";
                    case 3:
                        ShortcutHelperKeys shortcutHelperKeys4 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_stop);
                    case 4:
                        ShortcutHelperKeys shortcutHelperKeys5 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_next);
                    case 5:
                        ShortcutHelperKeys shortcutHelperKeys6 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_previous);
                    case 6:
                        ShortcutHelperKeys shortcutHelperKeys7 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_rewind);
                    case 7:
                        ShortcutHelperKeys shortcutHelperKeys8 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_media_fast_forward);
                    case 8:
                        ShortcutHelperKeys shortcutHelperKeys9 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_up);
                    case 9:
                        ShortcutHelperKeys shortcutHelperKeys10 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_page_down);
                    case 10:
                        ShortcutHelperKeys shortcutHelperKeys11 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A);
                    case 11:
                        ShortcutHelperKeys shortcutHelperKeys12 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_down);
                    case 12:
                        ShortcutHelperKeys shortcutHelperKeys13 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B);
                    case 13:
                        ShortcutHelperKeys shortcutHelperKeys14 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C);
                    case 14:
                        ShortcutHelperKeys shortcutHelperKeys15 = ShortcutHelperKeys.INSTANCE;
                        return "Alt";
                    case 15:
                        ShortcutHelperKeys shortcutHelperKeys16 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "X");
                    case 16:
                        ShortcutHelperKeys shortcutHelperKeys17 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Y");
                    case 17:
                        ShortcutHelperKeys shortcutHelperKeys18 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Z");
                    case 18:
                        ShortcutHelperKeys shortcutHelperKeys19 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L1");
                    case 19:
                        ShortcutHelperKeys shortcutHelperKeys20 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R1");
                    case 20:
                        ShortcutHelperKeys shortcutHelperKeys21 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "L2");
                    case 21:
                        ShortcutHelperKeys shortcutHelperKeys22 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "R2");
                    case 22:
                        ShortcutHelperKeys shortcutHelperKeys23 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_dpad_left);
                    case 23:
                        ShortcutHelperKeys shortcutHelperKeys24 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Start");
                    case 24:
                        ShortcutHelperKeys shortcutHelperKeys25 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Select");
                    case 25:
                        ShortcutHelperKeys shortcutHelperKeys26 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_button_template, "Mode");
                    case 26:
                        ShortcutHelperKeys shortcutHelperKeys27 = ShortcutHelperKeys.INSTANCE;
                        return "Shift";
                    case 27:
                        ShortcutHelperKeys shortcutHelperKeys28 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_forward_del);
                    case 28:
                        ShortcutHelperKeys shortcutHelperKeys29 = ShortcutHelperKeys.INSTANCE;
                        return context.getString(R.string.keyboard_key_esc);
                    default:
                        ShortcutHelperKeys shortcutHelperKeys30 = ShortcutHelperKeys.INSTANCE;
                        return "SysRq";
                }
            }
        }));
    }

    private ShortcutHelperKeys() {
    }
}
