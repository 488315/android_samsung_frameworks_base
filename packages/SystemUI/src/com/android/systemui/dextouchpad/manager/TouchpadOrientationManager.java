package com.android.systemui.dextouchpad.manager;

import android.content.Context;
import android.provider.Settings;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.dextouchpad.activity.RotationButtonWindow;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;

/* loaded from: classes2.dex */
public class TouchpadOrientationManager implements SemContextListener {
    public final Context mContext;
    public SemContextManager mSContextManager = null;
    public boolean mIsSContextListenerAvailable = false;
    public AnonymousClass1 mOrientationListener = null;
    public RotationButtonWindow mTouchpadOrientationEventListener = null;
    public int mLastOrientation = -1;

    public TouchpadOrientationManager(Context context) {
        this.mContext = context;
    }

    public final void notifyOrientationChanged(int i) {
        if (this.mTouchpadOrientationEventListener != null) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "notifyOrientationChanged, angle=", "DexTouchpadOrientationManager");
            RotationButtonWindow rotationButtonWindow = this.mTouchpadOrientationEventListener;
            rotationButtonWindow.getClass();
            boolean z = Features.DEBUG;
            if (z) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onOrientationChanged: orientation=", "DexTouchpadRotationButtonWindow");
            }
            if (Settings.System.getInt(rotationButtonWindow.mActivity.getContentResolver(), SettingsHelper.INDEX_ACCELEROMETER_ROTATION, 0) == 1) {
                FragmentActivity fragmentActivity = rotationButtonWindow.mActivity;
                if ((!Features.IS_SUPPORT_WINNER || fragmentActivity.getResources().getConfiguration().semDisplayDeviceType != 5) && (i < 0 || ((rotationButtonWindow.mActivity.getRequestedOrientation() == 1 || rotationButtonWindow.mActivity.getRequestedOrientation() == 6) && rotationButtonWindow.mLatestRotation != i))) {
                    if (z) {
                        RecyclerView$$ExternalSyntheticOutline0.m(rotationButtonWindow.mLatestRotation, "DexTouchpadRotationButtonWindow", new StringBuilder("mLatestRotation="));
                    }
                    rotationButtonWindow.mActivity.setRequestedOrientation(2);
                }
            }
            if (i >= 0) {
                rotationButtonWindow.mLatestRotation = i;
            }
        }
    }

    public final void onSemContextChanged(SemContextEvent semContextEvent) {
        if (semContextEvent.semContext.getType() == 6) {
            int angle = semContextEvent.getAutoRotationContext().getAngle();
            this.mLastOrientation = angle;
            notifyOrientationChanged(angle);
        }
    }
}
