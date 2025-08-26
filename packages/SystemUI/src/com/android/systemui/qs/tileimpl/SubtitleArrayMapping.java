package com.android.systemui.qs.tileimpl;

import com.android.systemui.R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;

/* loaded from: classes2.dex */
public final class SubtitleArrayMapping {
    public static final SubtitleArrayMapping INSTANCE = new SubtitleArrayMapping();
    public static final HashMap subtitleIdsMap;

    static {
        HashMap map = new HashMap();
        subtitleIdsMap = map;
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_internet, map, ImsProfile.PDN_INTERNET, R.array.tile_states_wifi, ImsProfile.PDN_WIFI);
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_cell, map, "cell", R.array.tile_states_battery, "battery");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_dnd, map, "dnd", R.array.tile_states_modes_dnd, "modes_dnd");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_flashlight, map, "flashlight", R.array.tile_states_rotation, "rotation");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_bt, map, "bt", R.array.tile_states_airplane, SubRoom.EXTRA_KEY_AIRPLANE_MODE);
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_location, map, "location", R.array.tile_states_hotspot, "hotspot");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_inversion, map, "inversion", R.array.tile_states_saver, "saver");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_dark, map, "dark", R.array.tile_states_work, "work");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_cast, map, "cast", R.array.tile_states_night, "night");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_screenrecord, map, "screenrecord", R.array.tile_states_record_issue, "record_issue");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_reverse, map, "reverse", R.array.tile_states_reduce_brightness, "reduce_brightness");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_cameratoggle, map, "cameratoggle", R.array.tile_states_mictoggle, "mictoggle");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_controls, map, "controls", R.array.tile_states_wallet, "wallet");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_qr_code_scanner, map, "qr_code_scanner", R.array.tile_states_alarm, "alarm");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_onehanded, map, "onehanded", R.array.tile_states_color_correction, "color_correction");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_dream, map, BcSmartspaceDataPlugin.UI_SURFACE_DREAM, R.array.tile_states_font_scaling, "font_scaling");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_hearing_devices, map, "hearing_devices", R.array.tile_states_notes, "notes");
        map.put("desktopeffects", Integer.valueOf(R.array.tile_states_desktopeffects));
    }

    private SubtitleArrayMapping() {
    }

    public static int getSubtitleId(String str) {
        Integer num;
        return (str == null || (num = (Integer) subtitleIdsMap.get(str)) == null) ? R.array.tile_states_default : num.intValue();
    }
}
