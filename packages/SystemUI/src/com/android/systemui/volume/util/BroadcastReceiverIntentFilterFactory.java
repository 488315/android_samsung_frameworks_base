package com.android.systemui.volume.util;

import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class BroadcastReceiverIntentFilterFactory {
    public static final BroadcastReceiverIntentFilterFactory INSTANCE = new BroadcastReceiverIntentFilterFactory();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BroadcastReceiverType.values().length];
            try {
                iArr[BroadcastReceiverType.DISPLAY_MANAGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BroadcastReceiverType.ALLSOUND_OFF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BroadcastReceiverType.MIRROR_LINK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BroadcastReceiverType.BUDS_TOGETHER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[BroadcastReceiverType.MUSIC_SHARE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[BroadcastReceiverType.DUAL_AUDIO_MODE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[BroadcastReceiverType.OPEN_THEME.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[BroadcastReceiverType.AOD.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[BroadcastReceiverType.HEADSET_CONNECTION.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[BroadcastReceiverType.A2DP_ACTIVE_DEVICE_CHANGE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[BroadcastReceiverType.BUDS_ICON_SERVER_CHANGE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[BroadcastReceiverType.UNINSTALL_PACKAGE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[BroadcastReceiverType.HDMI_CONNECTION.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private BroadcastReceiverIntentFilterFactory() {
    }

    public static IntentFilter create(BroadcastReceiverType broadcastReceiverType) {
        switch (WhenMappings.$EnumSwitchMapping$0[broadcastReceiverType.ordinal()]) {
            case 1:
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE");
                intentFilter.addAction("com.samsung.intent.action.DLNA_STATUS_CHANGED");
                intentFilter.addAction("com.samsung.intent.action.WIFI_DISPLAY_VOLUME_SUPPORT_CHANGED");
                return intentFilter;
            case 2:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.settings.ALL_SOUND_MUTE");
            case 3:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.android.mirrorlink.ML_STATE");
            case 4:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_MODE_CHANGED", "com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_DEVICE_VOLUME_CHANGED");
            case 5:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED", "com.samsung.android.bluetooth.cast.device.action.FOUND");
            case 6:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED");
            case 7:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.android.theme.themecenter.THEME_APPLY");
            case 8:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
            case 9:
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter2.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
                intentFilter2.addAction("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
                intentFilter2.addAction("android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
                return intentFilter2;
            case 10:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
            case 11:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL");
            case 12:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGE_REMOVED");
            case 13:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("android.media.action.HDMI_AUDIO_PLUG");
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
