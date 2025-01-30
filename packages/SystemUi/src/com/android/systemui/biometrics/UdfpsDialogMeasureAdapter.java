package com.android.systemui.biometrics;

import android.graphics.Insets;
import android.graphics.Rect;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthDialog;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsDialogMeasureAdapter {
    public static final boolean DEBUG;
    public int mBottomSpacerHeight;
    public final FingerprintSensorPropertiesInternal mSensorProps;
    public final ViewGroup mView;
    public final WindowManager mWindowManager;

    static {
        DEBUG = Build.IS_USERDEBUG || Build.IS_ENG;
    }

    public UdfpsDialogMeasureAdapter(ViewGroup viewGroup, FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        this.mView = viewGroup;
        this.mSensorProps = fingerprintSensorPropertiesInternal;
        this.mWindowManager = (WindowManager) viewGroup.getContext().getSystemService(WindowManager.class);
    }

    public static int calculateBottomSpacerHeightForLandscape(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        int i8 = ((((i + i2) + i3) + i4) - (i5 + i6)) - i7;
        if (DEBUG) {
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Title height: ", i, ", Subtitle height: ", i2, ", Description height: ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i3, ", Top spacer height: ", i4, ", Text indicator height: ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i5, ", Button bar height: ", i6, ", Navbar bottom inset: ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(m45m, i7, ", Bottom spacer height (landscape): ", i8, "UdfpsDialogMeasurementAdapter");
        }
        return i8;
    }

    public static int calculateBottomSpacerHeightForPortrait(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, int i, int i2, int i3, int i4, int i5, float f) {
        SensorLocationInternal location = fingerprintSensorPropertiesInternal.getLocation();
        int i6 = (i - ((int) (location.sensorLocationY * f))) - ((int) (location.sensorRadius * f));
        int i7 = (((i6 - i2) - i3) - i4) - i5;
        if (DEBUG) {
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Display height: ", i, ", Distance from bottom: ", i6, ", Bottom margin: ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i4, ", Navbar bottom inset: ", i5, ", Bottom spacer height (portrait): ");
            m45m.append(i7);
            m45m.append(", Scale Factor: ");
            m45m.append(f);
            Log.d("UdfpsDialogMeasurementAdapter", m45m.toString());
        }
        return i7;
    }

    public static int calculateHorizontalSpacerWidthForLandscape(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, int i, int i2, int i3, float f) {
        SensorLocationInternal location = fingerprintSensorPropertiesInternal.getLocation();
        int i4 = (i - ((int) (location.sensorLocationY * f))) - ((int) (location.sensorRadius * f));
        int i5 = (i4 - i2) - i3;
        if (DEBUG) {
            StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("Display width: ", i, ", Distance from edge: ", i4, ", Dialog margin: ");
            AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(m45m, i2, ", Navbar horizontal inset: ", i3, ", Horizontal spacer width (landscape): ");
            m45m.append(i5);
            m45m.append(", Scale Factor: ");
            m45m.append(f);
            Log.d("UdfpsDialogMeasurementAdapter", m45m.toString());
        }
        return i5;
    }

    public final int getSensorDiameter(float f) {
        return (int) (f * this.mSensorProps.getLocation().sensorRadius * 2.0f);
    }

    public final int getViewHeightPx(int i) {
        View findViewById = this.mView.findViewById(i);
        if (findViewById == null || findViewById.getVisibility() == 8) {
            return 0;
        }
        return findViewById.getMeasuredHeight();
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0140 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x024e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AuthDialog.LayoutParams onMeasureInternal(int i, int i2, AuthDialog.LayoutParams layoutParams, float f) {
        int i3;
        ViewGroup viewGroup = this.mView;
        int rotation = viewGroup.getDisplay().getRotation();
        int i4 = R.id.biometric_icon_frame;
        int i5 = 0;
        WindowManager windowManager = this.mWindowManager;
        if (rotation == 0) {
            WindowMetrics maximumWindowMetrics = windowManager.getMaximumWindowMetrics();
            int viewHeightPx = getViewHeightPx(R.id.indicator);
            int viewHeightPx2 = getViewHeightPx(R.id.button_bar);
            int dimensionPixelSize = viewGroup.getResources().getDimensionPixelSize(R.dimen.biometric_dialog_border_padding);
            int height = (maximumWindowMetrics != null ? maximumWindowMetrics.getBounds() : new Rect()).height();
            int i6 = 0;
            this.mBottomSpacerHeight = calculateBottomSpacerHeightForPortrait(this.mSensorProps, height, viewHeightPx, viewHeightPx2, dimensionPixelSize, (maximumWindowMetrics != null ? maximumWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.navigationBars()) : Insets.NONE).bottom, f);
            int childCount = viewGroup.getChildCount();
            int sensorDiameter = getSensorDiameter(f);
            int i7 = 0;
            for (int i8 = 0; i8 < childCount; i8++) {
                View childAt = viewGroup.getChildAt(i8);
                if (childAt.getId() == R.id.biometric_icon_frame) {
                    FrameLayout frameLayout = (FrameLayout) childAt;
                    View childAt2 = frameLayout.getChildAt(i7);
                    frameLayout.measure(View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().width, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(sensorDiameter, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                    childAt2.measure(View.MeasureSpec.makeMeasureSpec(sensorDiameter, VideoPlayer.MEDIA_ERROR_SYSTEM), View.MeasureSpec.makeMeasureSpec(sensorDiameter, VideoPlayer.MEDIA_ERROR_SYSTEM));
                } else if (childAt.getId() == R.id.space_above_icon) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                } else if (childAt.getId() == R.id.button_bar) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                } else {
                    if (childAt.getId() == R.id.space_below_icon) {
                        childAt.measure(View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(Math.max(this.mBottomSpacerHeight, 0), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                        i7 = 0;
                    } else {
                        i7 = 0;
                        if (childAt.getId() != R.id.description) {
                            childAt.measure(View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i2, VideoPlayer.MEDIA_ERROR_SYSTEM));
                        }
                    }
                    if (childAt.getVisibility() == 8) {
                        i6 = childAt.getMeasuredHeight() + i6;
                    }
                }
                i7 = 0;
                if (childAt.getVisibility() == 8) {
                }
            }
            View findViewById = viewGroup.findViewById(R.id.description);
            if (findViewById != null && findViewById.getVisibility() != 8) {
                int i9 = (int) (height * 0.75d);
                if (findViewById.getMeasuredHeight() + i6 > i9) {
                    findViewById.measure(View.MeasureSpec.makeMeasureSpec(i, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i9 - i6, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                }
                i6 = findViewById.getMeasuredHeight() + i6;
            }
            return new AuthDialog.LayoutParams(i, i6);
        }
        if (rotation != 1 && rotation != 3) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Unsupported display rotation: ", rotation, "UdfpsDialogMeasurementAdapter");
            return layoutParams;
        }
        WindowMetrics maximumWindowMetrics2 = windowManager.getMaximumWindowMetrics();
        int viewHeightPx3 = getViewHeightPx(R.id.title);
        int viewHeightPx4 = getViewHeightPx(R.id.subtitle);
        int viewHeightPx5 = getViewHeightPx(R.id.description);
        int viewHeightPx6 = getViewHeightPx(R.id.space_above_icon);
        int viewHeightPx7 = getViewHeightPx(R.id.indicator);
        int viewHeightPx8 = getViewHeightPx(R.id.button_bar);
        Insets insets = maximumWindowMetrics2 != null ? maximumWindowMetrics2.getWindowInsets().getInsets(WindowInsets.Type.navigationBars()) : Insets.NONE;
        int calculateBottomSpacerHeightForLandscape = calculateBottomSpacerHeightForLandscape(viewHeightPx3, viewHeightPx4, viewHeightPx5, viewHeightPx6, viewHeightPx7, viewHeightPx8, insets.bottom);
        int calculateHorizontalSpacerWidthForLandscape = calculateHorizontalSpacerWidthForLandscape(this.mSensorProps, (maximumWindowMetrics2 != null ? maximumWindowMetrics2.getBounds() : new Rect()).width(), viewGroup.getResources().getDimensionPixelSize(R.dimen.biometric_dialog_border_padding), insets.left + insets.right, f);
        int sensorDiameter2 = getSensorDiameter(f);
        int i10 = (calculateHorizontalSpacerWidthForLandscape * 2) + sensorDiameter2;
        int childCount2 = viewGroup.getChildCount();
        int i11 = 0;
        int i12 = 0;
        while (i5 < childCount2) {
            View childAt3 = viewGroup.getChildAt(i5);
            if (childAt3.getId() == i4) {
                FrameLayout frameLayout2 = (FrameLayout) childAt3;
                View childAt4 = frameLayout2.getChildAt(i12);
                frameLayout2.measure(View.MeasureSpec.makeMeasureSpec(i10, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(sensorDiameter2, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                childAt4.measure(View.MeasureSpec.makeMeasureSpec(sensorDiameter2, VideoPlayer.MEDIA_ERROR_SYSTEM), View.MeasureSpec.makeMeasureSpec(sensorDiameter2, VideoPlayer.MEDIA_ERROR_SYSTEM));
            } else if (childAt3.getId() == R.id.space_above_icon) {
                childAt3.measure(View.MeasureSpec.makeMeasureSpec(i10, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(childAt3.getLayoutParams().height - Math.min(calculateBottomSpacerHeightForLandscape, 0), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            } else if (childAt3.getId() == R.id.button_bar) {
                childAt3.measure(View.MeasureSpec.makeMeasureSpec(i10, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(childAt3.getLayoutParams().height, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
            } else {
                if (childAt3.getId() == R.id.space_below_icon) {
                    i3 = 0;
                    childAt3.measure(View.MeasureSpec.makeMeasureSpec(i10, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(Math.max(calculateBottomSpacerHeightForLandscape, 0), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
                } else {
                    i3 = 0;
                    childAt3.measure(View.MeasureSpec.makeMeasureSpec(i10, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(i2, VideoPlayer.MEDIA_ERROR_SYSTEM));
                }
                i12 = i3;
                if (childAt3.getVisibility() == 8) {
                    i11 = childAt3.getMeasuredHeight() + i11;
                }
                i5++;
                i4 = R.id.biometric_icon_frame;
            }
            i3 = 0;
            i12 = i3;
            if (childAt3.getVisibility() == 8) {
            }
            i5++;
            i4 = R.id.biometric_icon_frame;
        }
        return new AuthDialog.LayoutParams(i10, i11);
    }
}
