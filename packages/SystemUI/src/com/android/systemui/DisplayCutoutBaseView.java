package com.android.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.View;
import com.android.systemui.RegionInterceptingFrameLayout;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.statusbar.phone.IndicatorCutoutUtil;
import com.android.systemui.statusbar.phone.IndicatorGardenInputProperties;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SettingsHelper;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class DisplayCutoutBaseView extends View implements RegionInterceptingFrameLayout.RegionInterceptableView {
    public static final Companion Companion = new Companion(null);
    public float cameraProtectionProgress;
    public int cameraProtectionStrokeWidth;
    public final Path cutoutPath;
    public final IndicatorCutoutUtil cutoutUtil;
    public final DisplayInfo displayInfo;
    public Display.Mode displayMode;
    public int displayRotation;
    public String displayUniqueId;
    public int initialDisplayDensity;
    public int initialDisplayWidth;
    public boolean isCameraProtectionEnabled;
    public final int[] location;
    public final Paint paint;
    public final Paint paintForCameraProtection;
    public boolean pendingConfigChange;
    public final Path protectionPath;
    public final Path protectionPathOrig;
    public final RectF protectionRect;
    public final RectF protectionRectOrig;
    protected final SettingsHelper settingsHelper;
    public boolean shouldDrawCutout;
    public boolean showProtection;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DisplayCutoutBaseView(Context context) {
        super(context);
        Resources resources = getContext().getResources();
        Display display = getContext().getDisplay();
        this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(resources, display != null ? display.getUniqueId() : null);
        this.location = new int[2];
        this.displayInfo = new DisplayInfo();
        this.paint = new Paint();
        this.cutoutPath = new Path();
        this.protectionRect = new RectF();
        this.protectionPath = new Path();
        this.protectionRectOrig = new RectF();
        this.protectionPathOrig = new Path();
        this.cameraProtectionProgress = 0.5f;
        this.paintForCameraProtection = new Paint();
        this.isCameraProtectionEnabled = getResources().getBoolean(R.bool.config_enableDisplayCutoutProtection);
        this.cameraProtectionStrokeWidth = getResources().getDimensionPixelSize(R.dimen.camera_protection_stroke_width);
        this.settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.cutoutUtil = new IndicatorCutoutUtil(getContext(), new IndicatorGardenInputProperties(getContext()), (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class));
    }

    public void drawCutoutProtection(Canvas canvas) {
        if (this.cameraProtectionProgress > 0.5f) {
            this.protectionRect.isEmpty();
        }
        if (this.cutoutUtil.isUDCMainDisplay()) {
            canvas.drawPath(this.protectionPath, this.paintForCameraProtection);
        } else {
            canvas.drawPath(this.cutoutPath, this.paintForCameraProtection);
        }
    }

    public void drawCutouts(Canvas canvas) {
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout == null || displayCutout.getCutoutPath() == null) {
            return;
        }
        canvas.drawPath(this.cutoutPath, this.paint);
    }

    public void dump(PrintWriter printWriter) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.println("DisplayCutoutBaseView:");
        asIndenting.increaseIndent();
        asIndenting.println("shouldDrawCutout=" + this.shouldDrawCutout);
        asIndenting.println("cutout=" + this.displayInfo.displayCutout);
        asIndenting.println("cameraProtectionProgress=" + this.cameraProtectionProgress);
        asIndenting.println("protectionRect=" + this.protectionRect);
        asIndenting.println("protectionRectOrig=" + this.protectionRectOrig);
        asIndenting.decreaseIndent();
        asIndenting.decreaseIndent();
    }

    public void enableShowProtection(boolean z) {
        if (this.showProtection == z) {
            return;
        }
        this.showProtection = z;
        updateProtectionBoundingPath();
        invalidate();
    }

    public float getPhysicalPixelDisplaySizeRatio() {
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null) {
            return displayCutout.getCutoutPathParserInfo().getPhysicalPixelDisplaySizeRatio();
        }
        return 1.0f;
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Display display = getContext().getDisplay();
        this.displayUniqueId = display != null ? display.getUniqueId() : null;
        updateCutout();
        updateProtectionBoundingPath();
        onUpdate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.shouldDrawCutout || this.showProtection) {
            canvas.save();
            getLocationOnScreen(this.location);
            int[] iArr = this.location;
            canvas.translate(-iArr[0], -iArr[1]);
            if (this.shouldDrawCutout) {
                drawCutouts(canvas);
            }
            if (this.showProtection) {
                drawCutoutProtection(canvas);
            }
            canvas.restore();
        }
    }

    public final void updateConfiguration(String str) {
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = getContext().getDisplay();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        Display.Mode mode = this.displayMode;
        this.displayMode = displayInfo.getMode();
        String str2 = displayInfo.uniqueId;
        if (!Intrinsics.areEqual(this.displayUniqueId, str2)) {
            this.displayUniqueId = str2;
            this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(getContext().getResources(), this.displayUniqueId);
            IndicatorCutoutUtil indicatorCutoutUtil = this.cutoutUtil;
            if (indicatorCutoutUtil.isUDCModel) {
                enableShowProtection(indicatorCutoutUtil.isUDCMainDisplay() && this.settingsHelper.isFillUDCDisplayCutoutEnabled());
            }
            invalidate();
        }
        Display.Mode mode2 = this.displayMode;
        if (mode != null) {
            if (Integer.valueOf(mode.getPhysicalHeight()).equals(mode2 != null ? Integer.valueOf(mode2.getPhysicalHeight()) : null)) {
                if (Integer.valueOf(mode.getPhysicalWidth()).equals(mode2 != null ? Integer.valueOf(mode2.getPhysicalWidth()) : null) && Intrinsics.areEqual(this.displayInfo.displayCutout, displayInfo.displayCutout) && this.displayRotation == displayInfo.rotation) {
                    return;
                }
            }
        }
        if (Intrinsics.areEqual(str, displayInfo.uniqueId)) {
            this.displayRotation = displayInfo.rotation;
            updateCutout();
            updateProtectionBoundingPath();
            onUpdate();
        }
    }

    public void updateCutout() {
        Path cutoutPath;
        if (this.pendingConfigChange) {
            return;
        }
        this.cutoutPath.reset();
        Display display = getContext().getDisplay();
        if (display != null) {
            display.getDisplayInfo(this.displayInfo);
        }
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if (displayCutout != null && (cutoutPath = displayCutout.getCutoutPath()) != null) {
            this.cutoutPath.set(cutoutPath);
        }
        invalidate();
    }

    public void updateProtectionBoundingPath() {
        if (this.pendingConfigChange) {
            return;
        }
        Matrix matrix = new Matrix();
        float physicalPixelDisplaySizeRatio = getPhysicalPixelDisplaySizeRatio();
        matrix.postScale(physicalPixelDisplaySizeRatio, physicalPixelDisplaySizeRatio);
        DisplayInfo displayInfo = this.displayInfo;
        int i = displayInfo.logicalWidth;
        int i2 = displayInfo.logicalHeight;
        int i3 = displayInfo.rotation;
        boolean z = i3 == 1 || i3 == 3;
        int i4 = z ? i2 : i;
        if (!z) {
            i = i2;
        }
        Companion.getClass();
        if (i3 != 0) {
            if (i3 == 1) {
                matrix.postRotate(270.0f);
                matrix.postTranslate(0.0f, i4);
            } else if (i3 == 2) {
                matrix.postRotate(180.0f);
                matrix.postTranslate(i4, i);
            } else {
                if (i3 != 3) {
                    throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "Unknown rotation: "));
                }
                matrix.postRotate(90.0f);
                matrix.postTranslate(i, 0.0f);
            }
        }
        if (this.protectionPathOrig.isEmpty()) {
            return;
        }
        this.protectionPath.set(this.protectionPathOrig);
        this.protectionPath.transform(matrix);
        matrix.mapRect(this.protectionRect, this.protectionRectOrig);
    }

    public static /* synthetic */ void getCameraProtectionProgress$annotations() {
    }

    public static /* synthetic */ void getDisplayInfo$annotations() {
    }

    public static /* synthetic */ void getProtectionPath$annotations() {
    }

    public static /* synthetic */ void getProtectionRect$annotations() {
    }

    public void onUpdate() {
    }

    public DisplayCutoutBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = getContext().getResources();
        Display display = getContext().getDisplay();
        this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(resources, display != null ? display.getUniqueId() : null);
        this.location = new int[2];
        this.displayInfo = new DisplayInfo();
        this.paint = new Paint();
        this.cutoutPath = new Path();
        this.protectionRect = new RectF();
        this.protectionPath = new Path();
        this.protectionRectOrig = new RectF();
        this.protectionPathOrig = new Path();
        this.cameraProtectionProgress = 0.5f;
        this.paintForCameraProtection = new Paint();
        this.isCameraProtectionEnabled = getResources().getBoolean(R.bool.config_enableDisplayCutoutProtection);
        this.cameraProtectionStrokeWidth = getResources().getDimensionPixelSize(R.dimen.camera_protection_stroke_width);
        this.settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.cutoutUtil = new IndicatorCutoutUtil(getContext(), new IndicatorGardenInputProperties(getContext()), (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class));
    }

    public DisplayCutoutBaseView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Resources resources = getContext().getResources();
        Display display = getContext().getDisplay();
        this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(resources, display != null ? display.getUniqueId() : null);
        this.location = new int[2];
        this.displayInfo = new DisplayInfo();
        this.paint = new Paint();
        this.cutoutPath = new Path();
        this.protectionRect = new RectF();
        this.protectionPath = new Path();
        this.protectionRectOrig = new RectF();
        this.protectionPathOrig = new Path();
        this.cameraProtectionProgress = 0.5f;
        this.paintForCameraProtection = new Paint();
        this.isCameraProtectionEnabled = getResources().getBoolean(R.bool.config_enableDisplayCutoutProtection);
        this.cameraProtectionStrokeWidth = getResources().getDimensionPixelSize(R.dimen.camera_protection_stroke_width);
        this.settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        this.cutoutUtil = new IndicatorCutoutUtil(getContext(), new IndicatorGardenInputProperties(getContext()), (DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class));
    }
}
