package com.android.server.display.mode;

import com.android.server.display.config.SupportedModeData;
import com.android.server.display.mode.RefreshRateVote;
import com.android.server.display.mode.SupportedRefreshRatesVote;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface Vote {
    static CombinedVote forPhysicalRefreshRates(float f, float f2) {
        return new CombinedVote(List.of(new RefreshRateVote.PhysicalVote(f, f2), new DisableRefreshRateSwitchingVote(f == f2)));
    }

    static Vote forPolicyRate(float f, float f2) {
        return CoreRune.FW_VRR_DISCRETE ? new RefreshRateVote.RenderVote(f, f2) : forPhysicalRefreshRates(f, f2);
    }

    static SupportedRefreshRatesVote forSupportedRefreshRates(List list) {
        if (list.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            SupportedModeData supportedModeData = (SupportedModeData) it.next();
            arrayList.add(new SupportedRefreshRatesVote.RefreshRates(supportedModeData.refreshRate, supportedModeData.vsyncRate));
        }
        return new SupportedRefreshRatesVote(arrayList);
    }

    static String priorityToString(int i) {
        switch (i) {
            case 0:
                return "PRIORITY_DEFAULT_REFRESH_RATE";
            case 1:
                return "PRIORITY_REFRESH_RATE_MAX_LIMIT";
            case 2:
                return "PRIORITY_REFRESH_RATE_MIN_LIMIT";
            case 3:
                return "PRIORITY_FLICKER_REFRESH_RATE";
            case 4:
                return "PRIORITY_HIGH_BRIGHTNESS_MODE";
            case 5:
                return "PRIORITY_USER_SETTING_MIN_RENDER_FRAME_RATE";
            case 6:
                return "PRIORITY_USER_SETTING_DISPLAY_PREFERRED_SIZE";
            case 7:
                return "PRIORITY_APP_REQUEST_RENDER_FRAME_RATE_RANGE";
            case 8:
                return "PRIORITY_APP_REQUEST_BASE_MODE_REFRESH_RATE";
            case 9:
                return "PRIORITY_APP_REQUEST_SIZE";
            case 10:
                return "PRIORITY_USER_SETTING_PEAK_REFRESH_RATE";
            case 11:
                return "PRIORITY_REFRESH_RATE_MODE";
            case 12:
                return "PRIORITY_RESOLUTION";
            case 13:
                return "PRIORITY_USER_SETTING_PEAK_RENDER_FRAME_RATE";
            case 14:
                return "PRIORITY_SYNCHRONIZED_REFRESH_RATE";
            case 15:
                return "PRIORITY_LIMIT_MODE";
            case 16:
                return "PRIORITY_AUTH_OPTIMIZER_RENDER_FRAME_RATE";
            case 17:
                return "PRIORITY_LAYOUT_LIMITED_FRAME_RATE";
            case 18:
                return "PRIORITY_SYSTEM_REQUESTED_MODES";
            case 19:
                return "PRIORITY_LOW_POWER_MODE_MODES";
            case 20:
                return "PRIORITY_LOW_POWER_MODE_RENDER_RATE";
            case 21:
                return "PRIORITY_FLICKER_REFRESH_RATE_SWITCH";
            case 22:
                return "PRIORITY_SKIN_TEMPERATURE";
            case 23:
                return "PRIORITY_PROXIMITY";
            case 24:
                return "PRIORITY_UDFPS";
            default:
                return Integer.toString(i);
        }
    }

    void updateSummary(VoteSummary voteSummary);
}
