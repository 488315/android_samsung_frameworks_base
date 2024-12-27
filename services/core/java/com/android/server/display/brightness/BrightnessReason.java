package com.android.server.display.brightness;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.util.Slog;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BrightnessReason {
    public final List changes = new ArrayList();
    public int mModifier;
    public int mReason;
    public int startBrightness;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrightnessChange {
        public final int adjustedBrightness;
        public final int modifier;

        public BrightnessChange(int i, float f) {
            this.modifier = i;
            this.adjustedBrightness = BrightnessSynchronizer.brightnessFloatToInt(f);
        }

        public final String toString() {
            String str;
            BrightnessReason.this.getClass();
            int i = this.modifier;
            if (i == 1) {
                str = "dim";
            } else if (i != 2) {
                switch (i) {
                    case 4:
                        str = "hdr";
                        break;
                    case 8:
                        str = "throttled";
                        break;
                    case 16:
                        str = "scale_factor";
                        break;
                    case 32:
                        str = "mb_limit";
                        break;
                    case 64:
                        str = "ab_limit";
                        break;
                    case 128:
                        str = "cover_limit";
                        break;
                    case 256:
                        str = "hbm_block";
                        break;
                    case 512:
                        str = "outdoor";
                        break;
                    case 1024:
                        str = "curtain";
                        break;
                    case 2048:
                        str = "doze_maximum";
                        break;
                    case 4096:
                        str = "force_dim";
                        break;
                    case 8192:
                        str = "restore_manual";
                        break;
                    case EndpointMonitorConst
                            .FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION /* 16384 */:
                        str = "cover_display_demo";
                        break;
                    case 32768:
                        str = "screen_curtain";
                        break;
                    case EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT /* 65536 */:
                        str = "freezing_brightness_mode";
                        break;
                    case 131072:
                        str = "hdr_limit";
                        break;
                    default:
                        str =
                                AudioChannelMask$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("0x"), i);
                        break;
                }
            } else {
                str = "low_pwr";
            }
            return String.format(" -> %s[%d]", str, Integer.valueOf(this.adjustedBrightness));
        }
    }

    public static String reasonToString(int i) {
        switch (i) {
            case 1:
                return "manual";
            case 2:
                return "doze";
            case 3:
                return "doze_default";
            case 4:
                return "automatic";
            case 5:
                return "screen_off";
            case 6:
                return "override";
            case 7:
                return "temporary";
            case 8:
                return "boost";
            case 9:
                return "screen_off_brightness_sensor";
            case 10:
                return "follower";
            case 11:
                return "offload";
            case 12:
                return "doze_manual";
            case 13:
                return "doze_initial";
            case 14:
                return "last_target";
            default:
                return Integer.toString(i);
        }
    }

    public final void addModifier(float f, int i) {
        setModifier(this.mModifier | i);
        ((ArrayList) this.changes).add(new BrightnessChange(i, f));
    }

    public final String changesToString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(reasonToString(this.mReason));
        sb.append(String.format("[%d]", Integer.valueOf(this.startBrightness)));
        if (((ArrayList) this.changes).size() > 0) {
            ((ArrayList) this.changes)
                    .forEach(
                            new Consumer() { // from class:
                                             // com.android.server.display.brightness.BrightnessReason$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    sb.append((BrightnessReason.BrightnessChange) obj);
                                }
                            });
        }
        return sb.toString();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof BrightnessReason)) {
            return false;
        }
        BrightnessReason brightnessReason = (BrightnessReason) obj;
        return brightnessReason.mReason == this.mReason
                && brightnessReason.mModifier == this.mModifier;
    }

    public final int getReason() {
        return this.mReason;
    }

    public final boolean hasModifier(int i) {
        return (this.mModifier & i) != 0;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mReason), Integer.valueOf(this.mModifier));
    }

    public final boolean isStartBrightnessChanged(BrightnessReason brightnessReason) {
        return this.startBrightness != brightnessReason.startBrightness;
    }

    public final void set(BrightnessReason brightnessReason) {
        setReason(
                brightnessReason == null ? 0 : brightnessReason.mReason,
                brightnessReason == null ? 0 : brightnessReason.startBrightness);
        setModifier(brightnessReason != null ? brightnessReason.mModifier : 0);
    }

    public final void setModifier(int i) {
        if (((-262144) & i) == 0) {
            this.mModifier = i;
            return;
        }
        Slog.w(
                "BrightnessReason",
                "brightness modifier out of bounds: 0x" + Integer.toHexString(i));
    }

    public final void setReason(float f, int i) {
        setReason(i, BrightnessSynchronizer.brightnessFloatToInt(f));
    }

    public final void setReason(int i, int i2) {
        if (i < 0 || i > 14) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i, "brightness reason out of bounds: ", "BrightnessReason");
            return;
        }
        this.mReason = i;
        this.startBrightness = i2;
        ((ArrayList) this.changes).clear();
    }

    public final String toString() {
        return toString(0);
    }

    public final String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(reasonToString(this.mReason));
        sb.append(" [");
        if ((i & 1) != 0) {
            sb.append(" temp_adj");
        }
        if ((i & 2) != 0) {
            sb.append(" auto_adj");
        }
        if ((this.mModifier & 2) != 0) {
            sb.append(" low_pwr");
        }
        if ((this.mModifier & 1) != 0) {
            sb.append(" dim");
        }
        if ((this.mModifier & 4) != 0) {
            sb.append(" hdr");
        }
        if ((this.mModifier & 8) != 0) {
            sb.append(" throttled");
        }
        if ((this.mModifier & 16) != 0) {
            sb.append(" lux_lower_bound");
        }
        if ((this.mModifier & 32) != 0) {
            sb.append(" user_min_pref");
        }
        int length = sb.length();
        if (sb.charAt(length - 1) == '[') {
            sb.setLength(length - 2);
        } else {
            sb.append(" ]");
        }
        return sb.toString();
    }
}
