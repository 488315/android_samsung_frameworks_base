package com.android.systemui.globalactions;

import android.content.Context;
import android.nearby.NearbyManager;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ShutdownUi {
    public final Context mContext;
    public final NearbyManager mNearbyManager;

    public ShutdownUi(Context context, NearbyManager nearbyManager) {
        this.mContext = context;
        this.mNearbyManager = nearbyManager;
    }

    public String getReasonMessage(String str) {
        if (str != null && str.startsWith("recovery-update")) {
            return this.mContext.getString(17042646);
        }
        if (str == null || !str.equals("recovery")) {
            return null;
        }
        return this.mContext.getString(17042642);
    }

    public int getRebootMessage(boolean z, String str) {
        if (str == null || !str.startsWith("recovery-update")) {
            return ((str == null || !str.equals("recovery")) && !z) ? 17043172 : 17042641;
        }
        return 17042645;
    }

    public int getShutdownDialogContent(boolean z) {
        int poweredOffFindingMode = this.mNearbyManager.getPoweredOffFindingMode();
        if (poweredOffFindingMode != 1 && poweredOffFindingMode != 0) {
            if (poweredOffFindingMode == 2) {
                if (z) {
                    return 17367474;
                }
                return R.layout.shutdown_dialog_finder_active;
            }
            RecordingInputConnection$$ExternalSyntheticOutline0.m(poweredOffFindingMode, "Unexpected value for finder active: ", "ShutdownUi");
        }
        return 17367474;
    }
}
