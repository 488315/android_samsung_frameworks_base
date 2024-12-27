package com.android.systemui.statusbar.model;

import android.content.Context;
import android.os.Binder;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyboardShortcutGroup;
import com.android.systemui.R;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.settings.ImsProfile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class KshData {
    public KeyCharacterMap mBackupKeyCharacterMap;
    public final Context mContext;
    public KeyCharacterMap mKeyCharacterMap;
    public final KshDataUtils mKshDataUtils;
    public List mKshGroups;
    public final SparseArray mModifierNames;
    public final SparseArray mSpecialCharacterDrawableDescriptions;
    public final SparseArray mSpecialCharacterDrawables;
    public final SparseArray mSpecialCharacterNames;
    public final SparseArray mModifierDrawables = new SparseArray();
    public HashMap mDefaultIcons = new HashMap();

    public KshData(final Context context) {
        this.mSpecialCharacterNames = new SparseArray();
        this.mModifierNames = new SparseArray();
        this.mSpecialCharacterDrawables = new SparseArray();
        this.mSpecialCharacterDrawableDescriptions = new SparseArray();
        this.mContext = context;
        this.mKshDataUtils = new KshDataUtils(context);
        this.mSpecialCharacterNames = new SparseArray(context) { // from class: com.android.systemui.statusbar.model.SpecialCharacterNames$1
            {
                put(3, context.getString(R.string.keyboard_key_home));
                put(4, context.getString(R.string.keyboard_key_back));
                put(19, context.getString(R.string.keyboard_key_dpad_up));
                put(20, context.getString(R.string.keyboard_key_dpad_down));
                put(21, context.getString(R.string.keyboard_key_dpad_left));
                put(22, context.getString(R.string.keyboard_key_dpad_right));
                put(23, context.getString(R.string.keyboard_key_dpad_center));
                put(56, ".");
                put(61, context.getString(R.string.keyboard_key_tab));
                put(66, context.getString(R.string.keyboard_key_enter));
                put(67, context.getString(R.string.keyboard_key_backspace));
                put(85, context.getString(R.string.keyboard_key_media_play_pause));
                put(86, context.getString(R.string.keyboard_key_media_stop));
                put(87, context.getString(R.string.keyboard_key_media_next));
                put(88, context.getString(R.string.keyboard_key_media_previous));
                put(89, context.getString(R.string.keyboard_key_media_rewind));
                put(90, context.getString(R.string.keyboard_key_media_fast_forward));
                put(92, context.getString(R.string.keyboard_key_page_up));
                put(93, context.getString(R.string.keyboard_key_page_down));
                put(96, context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_A));
                put(97, context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_B));
                put(98, context.getString(R.string.keyboard_key_button_template, ImsProfile.TIMER_NAME_C));
                put(99, context.getString(R.string.keyboard_key_button_template, "X"));
                put(100, context.getString(R.string.keyboard_key_button_template, "Y"));
                put(101, context.getString(R.string.keyboard_key_button_template, "Z"));
                put(102, context.getString(R.string.keyboard_key_button_template, "L1"));
                put(103, context.getString(R.string.keyboard_key_button_template, "R1"));
                put(104, context.getString(R.string.keyboard_key_button_template, "L2"));
                put(105, context.getString(R.string.keyboard_key_button_template, "R2"));
                put(108, context.getString(R.string.keyboard_key_button_template, "Start"));
                put(109, context.getString(R.string.keyboard_key_button_template, "Select"));
                put(110, context.getString(R.string.keyboard_key_button_template, "Mode"));
                put(112, context.getString(R.string.keyboard_key_forward_del));
                put(111, "Esc");
                put(121, "Break");
                put(116, "Scroll Lock");
                put(122, context.getString(R.string.keyboard_key_move_home));
                put(123, context.getString(R.string.keyboard_key_move_end));
                put(124, context.getString(R.string.keyboard_key_insert));
                put(131, "F1");
                put(132, "F2");
                put(133, "F3");
                put(134, "F4");
                put(135, "F5");
                put(136, "F6");
                put(137, "F7");
                put(138, "F8");
                put(139, "F9");
                put(140, "F10");
                put(141, "F11");
                put(142, "F12");
                put(143, context.getString(R.string.keyboard_key_num_lock));
                put(144, context.getString(R.string.keyboard_key_numpad_template, "0"));
                put(145, context.getString(R.string.keyboard_key_numpad_template, "1"));
                put(146, context.getString(R.string.keyboard_key_numpad_template, "2"));
                put(147, context.getString(R.string.keyboard_key_numpad_template, "3"));
                put(148, context.getString(R.string.keyboard_key_numpad_template, "4"));
                put(149, context.getString(R.string.keyboard_key_numpad_template, "5"));
                put(150, context.getString(R.string.keyboard_key_numpad_template, "6"));
                put(151, context.getString(R.string.keyboard_key_numpad_template, "7"));
                put(152, context.getString(R.string.keyboard_key_numpad_template, "8"));
                put(153, context.getString(R.string.keyboard_key_numpad_template, "9"));
                put(154, context.getString(R.string.keyboard_key_numpad_template, "/"));
                put(155, context.getString(R.string.keyboard_key_numpad_template, "*"));
                put(156, context.getString(R.string.keyboard_key_numpad_template, "-"));
                put(157, context.getString(R.string.keyboard_key_numpad_template, "+"));
                put(158, context.getString(R.string.keyboard_key_numpad_template, "."));
                put(159, context.getString(R.string.keyboard_key_numpad_template, ","));
                put(160, context.getString(R.string.keyboard_key_numpad_template, context.getString(R.string.keyboard_key_enter)));
                put(161, context.getString(R.string.keyboard_key_numpad_template, "="));
                put(162, context.getString(R.string.keyboard_key_numpad_template, "("));
                put(163, context.getString(R.string.keyboard_key_numpad_template, ")"));
                put(IKnoxCustomManager.Stub.TRANSACTION_getWifiHotspotEnabledState, "半角/全角");
                put(IKnoxCustomManager.Stub.TRANSACTION_getWifiState, "英数");
                put(IKnoxCustomManager.Stub.TRANSACTION_addAutoCallNumber, "無変換");
                put(IKnoxCustomManager.Stub.TRANSACTION_removeAutoCallNumber, "変換");
                put(IKnoxCustomManager.Stub.TRANSACTION_getAutoCallNumberDelay, "かな");
                put(62, context.getString(R.string.ksh_key_space));
                put(120, context.getString(R.string.ksh_key_prtscn));
                put(1, context.getString(R.string.ksh_key_shift));
                put(204, context.getString(R.string.ksh_key_lang));
            }
        };
        this.mModifierNames = new SparseArray(context) { // from class: com.android.systemui.statusbar.model.ModifierNames$1
            {
                put(4096, "Ctrl");
                put(2, "Alt");
                put(1, "Shift");
                put(4, "Sym");
                put(8, "Fn");
                put(65536, context.getString(R.string.ksh_key_cmd));
                put(16, context.getString(R.string.ksh_key_left_alt));
            }
        };
        this.mSpecialCharacterDrawables = new SparseArray(context) { // from class: com.android.systemui.statusbar.model.SpecialCharacterDrawables$1
            {
                put(67, context.getDrawable(R.drawable.btkeyboard_btn_ic_shortcut_back));
                put(66, context.getDrawable(R.drawable.btkeyboard_btn_ic_shortcut_enter));
                put(19, context.getDrawable(R.drawable.btkeyboard_btn_ic_shortcut_up));
                put(22, context.getDrawable(R.drawable.btkeyboard_btn_ic_shortcut_right));
                put(20, context.getDrawable(R.drawable.btkeyboard_btn_ic_shortcut_down));
                put(21, context.getDrawable(R.drawable.btkeyboard_btn_ic_shortcut_left));
            }
        };
        this.mSpecialCharacterDrawableDescriptions = new SparseArray(context) { // from class: com.android.systemui.statusbar.model.SpecialCharacterDrawableDescriptions$1
            {
                put(67, context.getString(R.string.keyboard_shortcut_special_character_backspace_drawable_description));
                put(66, context.getString(R.string.keyboard_shortcut_special_character_enter_drawable_description));
                put(19, context.getString(R.string.keyboard_shortcut_special_character_up_arrow_drawable_description));
                put(22, context.getString(R.string.keyboard_shortcut_special_character_right_arrow_drawable_description));
                put(20, context.getString(R.string.keyboard_shortcut_special_character_down_arrow_drawable_description));
                put(21, context.getString(R.string.keyboard_shortcut_special_character_left_arrow_drawable_description));
            }
        };
    }

    public final KeyboardShortcutGroup getSamsungSystemShortcuts() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List list = (List) Arrays.stream((SamsungSystemShortcutsEnum[]) SamsungSystemShortcutsEnum.class.getEnumConstants()).map(new KshData$$ExternalSyntheticLambda0(this, 1)).filter(new KshData$$ExternalSyntheticLambda1(1)).map(new KshData$$ExternalSyntheticLambda4()).collect(Collectors.toList());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return new KeyboardShortcutGroup(this.mContext.getString(R.string.ksh_group_system), list);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
