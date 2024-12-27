package com.android.systemui.qs.tileimpl;

import com.android.systemui.R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.sec.ims.settings.ImsProfile;
import java.util.HashMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SubtitleArrayMapping {
    public static final SubtitleArrayMapping INSTANCE = new SubtitleArrayMapping();
    public static final HashMap subtitleIdsMap;

    static {
        HashMap hashMap = new HashMap();
        subtitleIdsMap = hashMap;
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_internet, hashMap, ImsProfile.PDN_INTERNET, R.array.tile_states_wifi, ImsProfile.PDN_WIFI);
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_cell, hashMap, "cell", R.array.tile_states_battery, "battery");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_dnd, hashMap, "dnd", R.array.tile_states_flashlight, "flashlight");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_rotation, hashMap, "rotation", R.array.tile_states_bt, "bt");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_airplane, hashMap, SubRoom.EXTRA_KEY_AIRPLANE_MODE, R.array.tile_states_location, "location");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_hotspot, hashMap, "hotspot", R.array.tile_states_inversion, "inversion");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_saver, hashMap, "saver", R.array.tile_states_dark, "dark");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_work, hashMap, "work", R.array.tile_states_cast, "cast");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_night, hashMap, "night", R.array.tile_states_screenrecord, "screenrecord");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_record_issue, hashMap, "record_issue", R.array.tile_states_reverse, "reverse");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_reduce_brightness, hashMap, "reduce_brightness", R.array.tile_states_cameratoggle, "cameratoggle");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_mictoggle, hashMap, "mictoggle", R.array.tile_states_controls, "controls");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_wallet, hashMap, "wallet", R.array.tile_states_qr_code_scanner, "qr_code_scanner");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_alarm, hashMap, "alarm", R.array.tile_states_onehanded, "onehanded");
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_color_correction, hashMap, "color_correction", R.array.tile_states_dream, BcSmartspaceDataPlugin.UI_SURFACE_DREAM);
        SubtitleArrayMapping$$ExternalSyntheticOutline0.m(R.array.tile_states_font_scaling, hashMap, "font_scaling", R.array.tile_states_hearing_devices, "hearing_devices");
    }

    private SubtitleArrayMapping() {
    }
}
