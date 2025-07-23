package com.android.systemui.dextouchpad.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.OrientationEventListener;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import com.android.systemui.dextouchpad.activity.ButtonWindow;
import com.android.systemui.dextouchpad.data.TouchpadButtonItems;
import com.android.systemui.dextouchpad.manager.TouchpadOrientationManager;
import com.android.systemui.dextouchpad.util.Features;
import com.android.systemui.dextouchpad.util.Utils;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.hardware.context.SemContextManager;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class RotationButtonWindow extends ButtonWindow {
    public int mLatestRotation;
    public TouchpadOrientationManager mOrientationEventManager;
    public final AnonymousClass1 mRotationSettingObserver;

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.systemui.dextouchpad.activity.RotationButtonWindow$1] */
    public RotationButtonWindow(AtomicBoolean atomicBoolean) {
        super(TouchpadButtonItems.ROTATION, atomicBoolean);
        this.mLatestRotation = 0;
        this.mRotationSettingObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.systemui.dextouchpad.activity.RotationButtonWindow.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                if (Features.DEBUG) {
                    Log.d("DexTouchpadRotationButtonWindow", "mRotationSettingObserver, onChange(selfChange=" + z + ")");
                }
                FragmentActivity fragmentActivity = RotationButtonWindow.this.mActivity;
                if (fragmentActivity == null) {
                    return;
                }
                if ((Settings.System.getInt(fragmentActivity.getContentResolver(), SettingsHelper.INDEX_ACCELEROMETER_ROTATION, 0) == 1) || RotationButtonWindow.this.mActivity.getResources().getConfiguration().orientation != 2) {
                    RotationButtonWindow.this.mActivity.setRequestedOrientation(2);
                } else {
                    RotationButtonWindow.this.mActivity.setRequestedOrientation(6);
                }
            }
        };
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.dextouchpad.manager.TouchpadOrientationManager$1] */
    @Override // com.android.systemui.dextouchpad.activity.ButtonWindow, com.android.systemui.dextouchpad.activity.FloatingWindow, com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public final void onStartSetup() {
        super.onStartSetup();
        this.mOnGestureListener = new ButtonWindow.OnGestureListener() { // from class: com.android.systemui.dextouchpad.activity.RotationButtonWindow$$ExternalSyntheticLambda0
            @Override // com.android.systemui.dextouchpad.activity.ButtonWindow.OnGestureListener
            public final void onDoubleTap() {
                RotationButtonWindow rotationButtonWindow = RotationButtonWindow.this;
                int i = rotationButtonWindow.mActivity.getResources().getConfiguration().orientation;
                boolean z = i == 2;
                boolean z2 = Features.DEBUG;
                if (z2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Rotation button clicked, currentOrientation=", "DexTouchpadRotationButtonWindow");
                }
                String str = "1";
                if (!z) {
                    Utils.sendSALogging("703", "7014", "1");
                    rotationButtonWindow.mActivity.setRequestedOrientation(6);
                    return;
                }
                int rotation = rotationButtonWindow.mActivity.getWindowManager().getDefaultDisplay().getRotation();
                if (z2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(rotation, "ROTATION : ", "DexTouchpadRotationButtonWindow");
                }
                if (rotation == 1) {
                    str = "3";
                } else if (rotation == 3) {
                    str = "2";
                }
                Utils.sendSALogging("703", "7014", str);
                rotationButtonWindow.mActivity.setRequestedOrientation(1);
            }
        };
        if (this.mOrientationEventManager == null) {
            this.mOrientationEventManager = new TouchpadOrientationManager(this.mActivity.getApplicationContext());
        }
        this.mActivity.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_ACCELEROMETER_ROTATION), true, this.mRotationSettingObserver);
        final TouchpadOrientationManager touchpadOrientationManager = this.mOrientationEventManager;
        if (touchpadOrientationManager != null) {
            Log.d("DexTouchpadOrientationManager", "startListening()");
            PackageManager packageManager = touchpadOrientationManager.mContext.getPackageManager();
            if (packageManager != null && packageManager.hasSystemFeature("com.sec.feature.sensorhub")) {
                if (touchpadOrientationManager.mSContextManager == null) {
                    touchpadOrientationManager.mSContextManager = (SemContextManager) touchpadOrientationManager.mContext.getSystemService("scontext");
                }
                SemContextManager semContextManager = touchpadOrientationManager.mSContextManager;
                if (semContextManager != null) {
                    touchpadOrientationManager.mIsSContextListenerAvailable = semContextManager.isAvailableService(6);
                }
            }
            ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("initialize(), mIsSContextListenerAvailable="), touchpadOrientationManager.mIsSContextListenerAvailable, "DexTouchpadOrientationManager");
            if (!touchpadOrientationManager.mIsSContextListenerAvailable) {
                final Context context = touchpadOrientationManager.mContext;
                touchpadOrientationManager.mOrientationListener = new OrientationEventListener(context) { // from class: com.android.systemui.dextouchpad.manager.TouchpadOrientationManager.1
                    public AnonymousClass1(final Context context2) {
                        super(context2);
                    }

                    @Override // android.view.OrientationEventListener
                    public final void onOrientationChanged(int i) {
                        TouchpadOrientationManager.this.getClass();
                        int i2 = i == -1 ? -1 : (300 <= i || i < 60) ? 0 : i < 150 ? 3 : i < 240 ? 2 : 1;
                        if (i2 == -1 || TouchpadOrientationManager.this.mLastOrientation != i2) {
                            TouchpadOrientationManager touchpadOrientationManager2 = TouchpadOrientationManager.this;
                            touchpadOrientationManager2.mLastOrientation = i2;
                            touchpadOrientationManager2.notifyOrientationChanged(i2);
                        }
                    }
                };
            }
            touchpadOrientationManager.mTouchpadOrientationEventListener = this;
            if (touchpadOrientationManager.mIsSContextListenerAvailable) {
                touchpadOrientationManager.mSContextManager.registerListener(touchpadOrientationManager, 6);
            } else {
                touchpadOrientationManager.mOrientationListener.enable();
            }
        }
        this.mLatestRotation = Utils.mLatestRotation;
    }

    @Override // com.android.systemui.dextouchpad.activity.ButtonWindow, com.android.systemui.dextouchpad.activity.FloatingWindow, com.android.systemui.dextouchpad.activity.ViewPositionTracker
    public final void onStartTearDown() {
        this.mActivity.getContentResolver().unregisterContentObserver(this.mRotationSettingObserver);
        TouchpadOrientationManager touchpadOrientationManager = this.mOrientationEventManager;
        if (touchpadOrientationManager != null) {
            Log.d("DexTouchpadOrientationManager", "stopListening()");
            touchpadOrientationManager.mTouchpadOrientationEventListener = null;
            if (touchpadOrientationManager.mIsSContextListenerAvailable) {
                SemContextManager semContextManager = touchpadOrientationManager.mSContextManager;
                if (semContextManager != null) {
                    semContextManager.unregisterListener(touchpadOrientationManager);
                }
            } else {
                TouchpadOrientationManager.AnonymousClass1 anonymousClass1 = touchpadOrientationManager.mOrientationListener;
                if (anonymousClass1 != null) {
                    anonymousClass1.disable();
                }
            }
            touchpadOrientationManager.mLastOrientation = -1;
        }
        Utils.mLatestRotation = this.mLatestRotation;
        super.onStartTearDown();
    }
}
