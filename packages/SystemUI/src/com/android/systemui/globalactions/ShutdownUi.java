package com.android.systemui.globalactions;

import android.content.Context;
import android.nearby.NearbyManager;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import com.android.internal.hidden_from_bootclasspath.android.net.platform.flags.Flags;
import com.android.systemui.R;
import com.android.systemui.statusbar.BlurUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ShutdownUi {
    public final BlurUtils mBlurUtils;
    public final Context mContext;
    public final NearbyManager mNearbyManager;

    public ShutdownUi(Context context, BlurUtils blurUtils, NearbyManager nearbyManager) {
        this.mContext = context;
        this.mBlurUtils = blurUtils;
        this.mNearbyManager = nearbyManager;
    }

    public String getReasonMessage(String str) {
        if (str != null && str.startsWith("recovery-update")) {
            return this.mContext.getString(17042526);
        }
        if (str == null || !str.equals("recovery")) {
            return null;
        }
        return this.mContext.getString(17042522);
    }

    public int getRebootMessage(boolean z, String str) {
        if (str == null || !str.startsWith("recovery-update")) {
            return ((str == null || !str.equals("recovery")) && !z) ? 17043035 : 17042521;
        }
        return 17042525;
    }

    public int getShutdownDialogContent(boolean z) {
        int poweredOffFindingMode;
        if (Flags.poweredOffFindingPlatform() && (poweredOffFindingMode = this.mNearbyManager.getPoweredOffFindingMode()) != 1 && poweredOffFindingMode != 0) {
            if (poweredOffFindingMode == 2) {
                if (z) {
                    return 17367431;
                }
                return R.layout.shutdown_dialog_finder_active;
            }
            RecordingInputConnection$$ExternalSyntheticOutline0.m(poweredOffFindingMode, "Unexpected value for finder active: ", "ShutdownUi");
        }
        return 17367431;
    }
}
