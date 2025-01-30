package com.android.systemui.volume.view.icon;

import com.android.systemui.R;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.HashMap;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeIcons {
    public static final HashMap MUTE_ICONS;
    public static final HashMap NORMAL_ICONS;

    static {
        new VolumeIcons();
        Integer valueOf = Integer.valueOf(R.drawable.tw_ic_audio_sound_ringtone);
        Pair pair = new Pair(2, valueOf);
        Integer valueOf2 = Integer.valueOf(R.drawable.tw_ic_audio_media_note);
        Pair pair2 = new Pair(3, valueOf2);
        Pair pair3 = new Pair(1, Integer.valueOf(R.drawable.tw_ic_audio_system_mtrl));
        Pair pair4 = new Pair(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mtrl));
        Integer valueOf3 = Integer.valueOf(R.drawable.tw_ic_audio_accessibility_mtrl);
        Pair pair5 = new Pair(10, valueOf3);
        Pair pair6 = new Pair(4, valueOf);
        Integer valueOf4 = Integer.valueOf(R.drawable.tw_ic_audio_call_mtrl);
        Pair pair7 = new Pair(0, valueOf4);
        Integer valueOf5 = Integer.valueOf(R.drawable.tw_ic_audio_call_bt_mtrl);
        Pair pair8 = new Pair(6, valueOf5);
        Integer valueOf6 = Integer.valueOf(R.drawable.tw_ic_audio_bixby_mtrl);
        Pair pair9 = new Pair(11, valueOf6);
        Pair pair10 = new Pair(20, valueOf);
        Pair pair11 = new Pair(21, valueOf2);
        Integer valueOf7 = Integer.valueOf(R.drawable.tw_ic_audio_bluetooth_mtrl);
        NORMAL_ICONS = MapsKt__MapsKt.hashMapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, pair9, pair10, pair11, new Pair(22, valueOf7), new Pair(23, valueOf7));
        Integer valueOf8 = Integer.valueOf(R.drawable.tw_ic_audio_mute_mtrl);
        MUTE_ICONS = MapsKt__MapsKt.hashMapOf(new Pair(2, valueOf8), new Pair(3, Integer.valueOf(R.drawable.tw_ic_audio_media_mute_mtrl)), new Pair(1, Integer.valueOf(R.drawable.tw_ic_audio_system_mute_mtrl)), new Pair(5, Integer.valueOf(R.drawable.tw_ic_audio_noti_mute_mtrl)), new Pair(10, valueOf3), new Pair(4, valueOf8), new Pair(0, valueOf4), new Pair(6, valueOf5), new Pair(11, valueOf6), new Pair(20, valueOf8), new Pair(21, Integer.valueOf(R.drawable.tw_ic_audio_media_mute_mtrl)), new Pair(22, valueOf7), new Pair(23, valueOf7));
    }

    private VolumeIcons() {
    }

    public static final int getDefaultIconResId(int i, int i2) {
        Integer valueOf = Integer.valueOf(R.drawable.tw_ic_audio_sound_ringtone);
        HashMap hashMap = NORMAL_ICONS;
        switch (i2) {
            case 0:
                return VolumePanelValues.isNotification(i) ? R.drawable.tw_ic_audio_noti_vibrate_mtrl : R.drawable.tw_ic_audio_vibrate_mtrl;
            case 1:
                Integer num = (Integer) MUTE_ICONS.get(Integer.valueOf(i));
                if (num == null) {
                    num = Integer.valueOf(R.drawable.tw_ic_audio_mute_mtrl);
                }
                return num.intValue();
            case 2:
                return R.drawable.tw_ic_audio_bluetooth_mtrl;
            case 3:
                Integer num2 = (Integer) hashMap.get(Integer.valueOf(i));
                if (num2 != null) {
                    valueOf = num2;
                }
                return valueOf.intValue();
            case 4:
            default:
                Integer num3 = (Integer) hashMap.get(Integer.valueOf(i));
                if (num3 != null) {
                    valueOf = num3;
                }
                return valueOf.intValue();
            case 5:
            case 6:
            case 7:
            case 8:
                return R.drawable.tw_ic_audio_mirroring_mtrl;
            case 9:
                return R.drawable.tw_ic_audio_wire_earphone_mtrl;
            case 10:
                return R.drawable.tw_ic_audio_buds;
            case 11:
                return R.drawable.tw_ic_audio_mirroring_speaker_mtrl;
            case 12:
                return R.drawable.tw_ic_audio_galaxy_home_mini;
            case 13:
                return R.drawable.tw_ic_audio_buds3;
            case 14:
                return R.drawable.tw_ic_audio_hearing_aids;
        }
    }

    public static final boolean isAnimatableIcon(int i, int i2) {
        return (isForMediaIcon(i) && isAnimatableMediaIconType(i2)) || VolumePanelValues.isRing(i) || VolumePanelValues.isNotification(i) || VolumePanelValues.isSystem(i) || VolumePanelValues.isAlarm(i);
    }

    public static final boolean isAnimatableMediaIconType(int i) {
        return (i == 2 || i == 8 || i == 9 || i == 10 || i == 13 || i == 12 || i == 11 || i == 14) ? false : true;
    }

    public static final boolean isForMediaIcon(int i) {
        return VolumePanelValues.isMusic(i) || VolumePanelValues.isMultiSound(i);
    }

    public static final boolean isWaveAnimatableIcon(int i, int i2) {
        return (isForMediaIcon(i) && isAnimatableMediaIconType(i2)) || VolumePanelValues.isRing(i) || VolumePanelValues.isAlarm(i);
    }
}
