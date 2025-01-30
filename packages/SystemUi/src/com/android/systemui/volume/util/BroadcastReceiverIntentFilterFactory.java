package com.android.systemui.volume.util;

import android.content.IntentFilter;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BroadcastReceiverIntentFilterFactory {
    public static final BroadcastReceiverIntentFilterFactory INSTANCE = new BroadcastReceiverIntentFilterFactory();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("android.settings.ALL_SOUND_MUTE");
            case 3:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.android.mirrorlink.ML_STATE");
            case 4:
                IntentFilter intentFilter2 = new IntentFilter();
                intentFilter2.addAction("com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_MODE_CHANGED");
                intentFilter2.addAction("com.samsung.android.bluetooth.audiocast.action.device.AUDIO_SHARING_DEVICE_VOLUME_CHANGED");
                return intentFilter2;
            case 5:
                IntentFilter intentFilter3 = new IntentFilter();
                intentFilter3.addAction("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
                intentFilter3.addAction("com.samsung.android.bluetooth.cast.device.action.FOUND");
                return intentFilter3;
            case 6:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED");
            case 7:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.android.theme.themecenter.THEME_APPLY");
            case 8:
                return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.android.app.aodservice.intent.action.CHANGE_AOD_MODE");
            case 9:
                IntentFilter intentFilter4 = new IntentFilter();
                intentFilter4.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter4.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
                intentFilter4.addAction("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
                return intentFilter4;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
