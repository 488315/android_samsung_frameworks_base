package com.android.systemui.volume.view.icon;

import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.HashMap;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeIcons {
    public static final HashMap MUTE_ICONS;
    public static final HashMap NORMAL_ICONS;

    static {
        new VolumeIcons();
        Integer valueOf = Integer.valueOf(R.drawable.tw_ic_audio_sound_ringtone);
        NORMAL_ICONS = MapsKt__MapsKt.hashMapOf(new Pair(2, valueOf), new Pair(3, Integer.valueOf(R.drawable.tw_ic_audio_media_note)), new Pair(1, Integer.valueOf(R.drawable.tw_ic_audio_system_mtrl)), new Pair(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mtrl)), new Pair(10, Integer.valueOf(R.drawable.tw_ic_audio_accessibility_mtrl)), new Pair(4, Integer.valueOf(R.drawable.tw_ic_audio_alarm_mtrl)), new Pair(0, Integer.valueOf(R.drawable.tw_ic_audio_call_mtrl)), new Pair(6, Integer.valueOf(R.drawable.tw_ic_audio_call_bt_mtrl)), new Pair(11, Integer.valueOf(R.drawable.tw_ic_ai_assistant)), new Pair(20, valueOf), new Pair(21, Integer.valueOf(R.drawable.tw_ic_audio_media_note)), new Pair(22, Integer.valueOf(R.drawable.tw_ic_audio_bluetooth_mtrl)), new Pair(23, Integer.valueOf(R.drawable.tw_ic_audio_bluetooth_mtrl)));
        MUTE_ICONS = MapsKt__MapsKt.hashMapOf(new Pair(2, Integer.valueOf(R.drawable.tw_ic_audio_mute_mtrl)), new Pair(3, Integer.valueOf(R.drawable.tw_ic_audio_media_mute_mtrl)), new Pair(1, Integer.valueOf(R.drawable.tw_ic_audio_system_mute_mtrl)), new Pair(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mute_mtrl)), new Pair(10, Integer.valueOf(R.drawable.tw_ic_audio_accessibility_mtrl)), new Pair(4, Integer.valueOf(R.drawable.tw_ic_audio_alarm_mtrl)), new Pair(0, Integer.valueOf(R.drawable.tw_ic_audio_call_mtrl)), new Pair(6, Integer.valueOf(R.drawable.tw_ic_audio_call_bt_mtrl)), new Pair(11, Integer.valueOf(R.drawable.tw_ic_ai_assistant)), new Pair(20, Integer.valueOf(R.drawable.tw_ic_audio_mute_mtrl)), new Pair(21, Integer.valueOf(R.drawable.tw_ic_audio_media_mute_mtrl)), new Pair(22, Integer.valueOf(R.drawable.tw_ic_audio_bluetooth_mtrl)), new Pair(23, Integer.valueOf(R.drawable.tw_ic_audio_bluetooth_mtrl)));
    }

    private VolumeIcons() {
    }

    public static final int getDefaultIconResId(int i, int i2) {
        switch (i2) {
            case 0:
                return VolumePanelValues.isNotification(i) ? R.drawable.tw_ic_audio_noti_vibrate_mtrl : R.drawable.tw_ic_audio_vibrate_mtrl;
            case 1:
                Integer num = (Integer) MUTE_ICONS.get(Integer.valueOf(i));
                return num != null ? num.intValue() : R.drawable.tw_ic_audio_mute_mtrl;
            case 2:
                return i == 0 ? R.drawable.tw_ic_audio_call_bt_mtrl : R.drawable.tw_ic_audio_bluetooth_mtrl;
            case 3:
                Integer num2 = (Integer) NORMAL_ICONS.get(Integer.valueOf(i));
                return num2 != null ? num2.intValue() : R.drawable.tw_ic_audio_sound_ringtone;
            case 4:
            default:
                Integer num3 = (Integer) NORMAL_ICONS.get(Integer.valueOf(i));
                return num3 != null ? num3.intValue() : R.drawable.tw_ic_audio_sound_ringtone;
            case 5:
            case 6:
            case 7:
            case 8:
                return R.drawable.tw_ic_audio_mirroring_mtrl;
            case 9:
                return R.drawable.tw_ic_audio_wire_earphone_mtrl;
            case 10:
                return R.drawable.tw_ic_audio_buds_solid;
            case 11:
                return R.drawable.tw_ic_audio_mirroring_speaker_mtrl;
            case 12:
                return R.drawable.tw_ic_audio_galaxy_home_mini_solid;
            case 13:
                return R.drawable.tw_ic_audio_buds3_solid;
            case 14:
                return R.drawable.tw_ic_audio_hearing_aids;
            case 15:
                return R.drawable.tw_ic_audio_music_frame_solid;
        }
    }

    public static final boolean isAnimatableIcon(int i, int i2) {
        return (isForMediaIcon(i) && isAnimatableMediaIconType(i2)) || VolumePanelValues.isRing(i) || VolumePanelValues.isNotification(i) || VolumePanelValues.isSystem(i);
    }

    public static final boolean isAnimatableMediaIconType(int i) {
        return (i == 2 || i == 8 || i == 9 || i == 10 || i == 13 || i == 12 || i == 11 || i == 14 || i == 15) ? false : true;
    }

    public static final boolean isForMediaIcon(int i) {
        return VolumePanelValues.isMusic(i) || VolumePanelValues.isMultiSound(i);
    }
}
