package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.shared.model.SensorLocation;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.shade.NotificationPanelView;
import dagger.Lazy;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class DefaultDeviceEntrySection extends KeyguardSection {
    public final AuthController authController;
    public final Context context;
    public final int deviceEntryIconViewId = R.id.device_entry_icon_view;
    public final Lazy deviceEntryIconViewModel;
    public final FeatureFlags featureFlags;
    public final NotificationPanelView notificationPanelView;
    public final WindowManager windowManager;

    public DefaultDeviceEntrySection(CoroutineScope coroutineScope, AuthController authController, WindowManager windowManager, Context context, NotificationPanelView notificationPanelView, FeatureFlags featureFlags, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6) {
        this.authController = authController;
        this.windowManager = windowManager;
        this.context = context;
        this.notificationPanelView = notificationPanelView;
        this.featureFlags = featureFlags;
        this.deviceEntryIconViewModel = lazy2;
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
        Flags.deviceEntryUdfpsRefactor();
        Lazy lazy = this.deviceEntryIconViewModel;
        Log.d("DefaultDeviceEntrySection", "isUdfpsSupported=" + ((DeviceEntryIconViewModel) lazy.get()).isUdfpsSupported.getValue());
        boolean booleanValue = ((Boolean) ((DeviceEntryIconViewModel) lazy.get()).isUdfpsSupported.getValue()).booleanValue();
        AuthController authController = this.authController;
        float f = authController.mScaleFactor;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.lock_icon_margin_bottom);
        Rect bounds = this.windowManager.getCurrentWindowMetrics().getBounds();
        float f2 = bounds.right;
        com.android.systemui.flags.Flags flags = com.android.systemui.flags.Flags.INSTANCE;
        this.featureFlags.getClass();
        float f3 = bounds.bottom;
        int i = (int) ((DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f) * 36);
        if (!booleanValue) {
            centerIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core(new Point((int) (f2 / 2), (int) (f3 - ((dimensionPixelSize + i) * f))), i * f, constraintSet);
            return;
        }
        Flags.deviceEntryUdfpsRefactor();
        SensorLocation sensorLocation = (SensorLocation) ((DeviceEntryIconViewModel) lazy.get()).udfpsLocation.$$delegate_0.getValue();
        if (sensorLocation != null) {
            int i2 = sensorLocation.naturalCenterX;
            float f4 = sensorLocation.scale;
            int i3 = sensorLocation.naturalCenterY;
            Log.d("DeviceEntrySection", "udfpsLocation=" + sensorLocation + ", scaledLocation=(" + (i2 * f4) + "," + (i3 * f4) + "), unusedAuthController=" + authController.getUdfpsLocation());
            centerIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core(new Point((int) (((float) i2) * f4), (int) (((float) i3) * f4)), ((float) sensorLocation.naturalRadius) * f4, constraintSet);
        }
    }

    public final void centerIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core(Point point, float f, ConstraintSet constraintSet) {
        Rect rect = new Rect();
        int i = point.x;
        int i2 = (int) f;
        int i3 = point.y;
        rect.set(i - i2, i3 - i2, i + i2, i3 + i2);
        Flags.deviceEntryUdfpsRefactor();
        int i4 = rect.right - rect.left;
        int i5 = this.deviceEntryIconViewId;
        constraintSet.constrainWidth(i5, i4);
        constraintSet.constrainHeight(i5, rect.bottom - rect.top);
        constraintSet.connect(i5, 3, 0, 3, rect.top);
        constraintSet.connect(i5, 6, 0, 6, rect.left);
        Flags.deviceEntryUdfpsRefactor();
        Flags.keyguardBottomAreaRefactor();
        boolean booleanValue = ((Boolean) ((DeviceEntryIconViewModel) this.deviceEntryIconViewModel.get()).isUdfpsSupported.getValue()).booleanValue();
        NotificationPanelView notificationPanelView = this.notificationPanelView;
        View findViewById = notificationPanelView.findViewById(R.id.keyguard_bottom_area);
        int right = findViewById != null ? findViewById.getRight() : 0;
        View findViewById2 = notificationPanelView.findViewById(R.id.ambient_indication_container);
        if (findViewById2 != null) {
            int[] locationOnScreen = findViewById2.getLocationOnScreen();
            Intrinsics.checkNotNull(locationOnScreen);
            int i6 = locationOnScreen[0];
            int i7 = locationOnScreen[1];
            if (booleanValue) {
                findViewById2.layout(i6, rect.bottom, right - i6, findViewById2.getMeasuredHeight() + i7);
            } else {
                findViewById2.layout(i6, rect.top - findViewById2.getMeasuredHeight(), right - i6, rect.top);
            }
        }
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        Flags.deviceEntryUdfpsRefactor();
        ExtensionsKt.removeView(constraintLayout, this.deviceEntryIconViewId);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
    }
}
