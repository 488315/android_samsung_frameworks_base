package com.android.server.wm;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayRotationReversionController {
    public final DisplayContent mDisplayContent;
    public int mUserRotationOverridden = -1;
    public final boolean[] mSlots = new boolean[3];

    public DisplayRotationReversionController(DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
    }

    public final void beforeOverrideApplied(int i) {
        boolean[] zArr = this.mSlots;
        if (zArr[i]) {
            return;
        }
        DisplayRotation displayRotation = this.mDisplayContent.mDisplayRotation;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= 3) {
                break;
            }
            if (zArr[i2]) {
                z = true;
                break;
            }
            i2++;
        }
        if (!z && displayRotation.mUserRotationMode == 1) {
            this.mUserRotationOverridden = displayRotation.mUserRotation;
        }
        zArr[i] = true;
    }

    public final boolean revertOverride(int i) {
        boolean z;
        boolean[] zArr = this.mSlots;
        if (!zArr[i]) {
            return false;
        }
        zArr[i] = false;
        int i2 = 0;
        while (true) {
            if (i2 >= 3) {
                z = false;
                break;
            }
            if (zArr[i2]) {
                z = true;
                break;
            }
            i2++;
        }
        if (z) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1]) {
                ProtoLogImpl_54989576.v(
                        ProtoLogGroup.WM_DEBUG_ORIENTATION, -4296736202875980050L, 0, null, null);
            }
            return false;
        }
        DisplayRotation displayRotation = this.mDisplayContent.mDisplayRotation;
        int i3 = this.mUserRotationOverridden;
        if (i3 == -1 || displayRotation.mUserRotationMode != 1) {
            return false;
        }
        displayRotation.setUserRotation(1, i3, "DisplayRotationReversionController#revertOverride");
        this.mUserRotationOverridden = -1;
        return true;
    }
}
