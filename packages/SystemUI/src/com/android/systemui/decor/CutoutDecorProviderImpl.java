package com.android.systemui.decor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.ScreenDecorations;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CutoutDecorProviderImpl extends BoundDecorProvider {
    public final int alignedBound;
    public final int cameraProtectionStrokeWidth;
    public final boolean isCameraProtectionEnabled;
    public final int viewId;

    public CutoutDecorProviderImpl(int i, boolean z, int i2) {
        this.alignedBound = i;
        this.isCameraProtectionEnabled = z;
        this.cameraProtectionStrokeWidth = i2;
        this.viewId = i != 0 ? i != 1 ? i != 2 ? R.id.display_cutout_bottom : R.id.display_cutout_right : R.id.display_cutout : R.id.display_cutout_left;
    }

    @Override // com.android.systemui.decor.BoundDecorProvider
    public final int getAlignedBound() {
        return this.alignedBound;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final int getViewId() {
        return this.viewId;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final View inflateView(Context context, ViewGroup viewGroup, int i, int i2) {
        ScreenDecorations.DisplayCutoutView displayCutoutView = new ScreenDecorations.DisplayCutoutView(context, this.alignedBound);
        displayCutoutView.setId(this.viewId);
        displayCutoutView.setColor$1(i2);
        viewGroup.addView(displayCutoutView);
        if (i != displayCutoutView.mRotation) {
            displayCutoutView.mRotation = i;
            displayCutoutView.displayRotation = i;
            displayCutoutView.updateCutout();
            displayCutoutView.updateProtectionBoundingPath();
        }
        displayCutoutView.isCameraProtectionEnabled = this.isCameraProtectionEnabled;
        displayCutoutView.updateCutout();
        int i3 = this.cameraProtectionStrokeWidth;
        displayCutoutView.cameraProtectionStrokeWidth = i3;
        displayCutoutView.paintForCameraProtection.setStrokeWidth(i3);
        displayCutoutView.updateCutout();
        return displayCutoutView;
    }

    @Override // com.android.systemui.decor.DecorProvider
    public final void onReloadResAndMeasure(View view, int i, int i2, int i3, String str) {
        ScreenDecorations.DisplayCutoutView displayCutoutView = view instanceof ScreenDecorations.DisplayCutoutView ? (ScreenDecorations.DisplayCutoutView) view : null;
        if (displayCutoutView != null) {
            displayCutoutView.setColor$1(i3);
            if (i2 != displayCutoutView.mRotation) {
                displayCutoutView.mRotation = i2;
                displayCutoutView.displayRotation = i2;
                displayCutoutView.updateCutout();
                displayCutoutView.updateProtectionBoundingPath();
            }
            displayCutoutView.updateConfiguration(str);
        }
    }
}
