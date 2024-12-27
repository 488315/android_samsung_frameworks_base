package com.android.systemui.statusbar.pipeline.satellite.ui.model;

import com.android.systemui.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.statusbar.pipeline.satellite.shared.model.SatelliteConnectionState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SatelliteIconModel {
    public static final SatelliteIconModel INSTANCE = new SatelliteIconModel();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SatelliteConnectionState.values().length];
            try {
                iArr[SatelliteConnectionState.Unknown.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SatelliteConnectionState.Off.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SatelliteConnectionState.On.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SatelliteConnectionState.Connected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private SatelliteIconModel() {
    }

    public static Icon.Resource fromSignalStrength(int i) {
        if (i == 0) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_0, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_no_connection));
        }
        if (i == 1) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_1, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_no_connection));
        }
        if (i == 2) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_2, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_poor_connection));
        }
        if (i == 3) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_2, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_poor_connection));
        }
        if (i == 4) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_3, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
        }
        if (i != 5) {
            return null;
        }
        return new Icon.Resource(R.drawable.stat_sys_sos_satellite_3, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
    }

    public static Icon.Resource fromSignalStrengthCN(int i) {
        if (i == 0) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_chn_0, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_no_connection));
        }
        if (i == 1) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_chn_1, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_poor_connection));
        }
        if (i == 2) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_chn_2, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_poor_connection));
        }
        if (i == 3) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_chn_3, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
        }
        if (i == 4) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_chn_4, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
        }
        if (i != 5) {
            return null;
        }
        return new Icon.Resource(R.drawable.stat_sys_sos_satellite_chn_4, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
    }

    public static Icon.Resource fromSignalStrengthVZW(int i) {
        if (i == 0) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_vzw_0, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_no_connection));
        }
        if (i == 1 || i == 2) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_vzw_1, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_poor_connection));
        }
        if (i == 3 || i == 4) {
            return new Icon.Resource(R.drawable.stat_sys_sos_satellite_vzw_2, new ContentDescription.Resource(R.string.accessibility_status_bar_satellite_good_connection));
        }
        return null;
    }
}
