package com.android.systemui.biometrics;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.systemui.R;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class FaceAuthAccessibilityDelegate extends View.AccessibilityDelegate {
    public final DeviceEntryFaceAuthInteractor faceAuthInteractor;
    public final Resources resources;

    public FaceAuthAccessibilityDelegate(Resources resources, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor) {
        this.resources = resources;
        this.faceAuthInteractor = deviceEntryFaceAuthInteractor;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        if (this.faceAuthInteractor.canFaceAuthRun()) {
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId(), this.resources.getString(R.string.retry_face)));
        }
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (i != AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK.getId()) {
            return super.performAccessibilityAction(view, i, bundle);
        }
        this.faceAuthInteractor.onAccessibilityAction();
        return true;
    }
}
