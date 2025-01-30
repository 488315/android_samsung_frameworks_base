package com.android.systemui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.media.AbstractC0000x2c234b15;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class DisplayCutoutBaseView extends View implements RegionInterceptingFrameLayout.RegionInterceptableView {
    public static final Companion Companion = new Companion(null);
    public float cameraProtectionProgress;
    public final Path cutoutPath;
    public final IndicatorCutoutUtil cutoutUtil;
    public final DisplayInfo displayInfo;
    public Display.Mode displayMode;
    public int displayRotation;
    public String displayUniqueId;
    public int initialDisplayDensity;
    public int initialDisplayWidth;
    public final int[] location;
    public final Rect mBoundingRect;
    public final Paint paint;
    public final Paint paintForCameraProtection;
    public boolean pendingConfigChange;
    public final Path protectionPath;
    public final Path protectionPathOrig;
    public final RectF protectionRect;
    public final RectF protectionRectOrig;
    public final SettingsHelper settingsHelper;
    public boolean shouldDrawCutout;
    public boolean showProtection;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.cameraProtectionProgress = 1.0f;
        this.paintForCameraProtection = new Paint();
        this.mBoundingRect = new Rect();
        this.settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.cutoutUtil = new IndicatorCutoutUtil(getContext(), new IndicatorGardenInputProperties(getContext()), (DisplayLifecycle) Dependency.get(DisplayLifecycle.class));
    }

    public void drawCutoutProtection(Canvas canvas) {
        if (this.cameraProtectionProgress > 0.5f) {
            boolean z = true;
            if (this.cutoutUtil.isUDCMainDisplay() ? this.protectionRect.isEmpty() : this.mBoundingRect.isEmpty()) {
                z = false;
            }
            if (z) {
                if (this.cutoutUtil.isUDCMainDisplay()) {
                    canvas.drawPath(this.protectionPath, this.paintForCameraProtection);
                } else {
                    canvas.drawPath(this.cutoutPath, this.paintForCameraProtection);
                }
            }
        }
    }

    public void drawCutouts(Canvas canvas) {
        DisplayCutout displayCutout = this.displayInfo.displayCutout;
        if ((displayCutout != null ? displayCutout.getCutoutPath() : null) == null) {
            return;
        }
        canvas.drawPath(this.cutoutPath, this.paint);
    }

    public void dump(PrintWriter printWriter) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.println("DisplayCutoutBaseView:");
        asIndenting.increaseIndent();
        DisplayCutoutBaseView$$ExternalSyntheticOutline0.m93m("shouldDrawCutout=", this.shouldDrawCutout, asIndenting);
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

    public final void setProtection(Path path, Rect rect) {
        this.protectionPathOrig.reset();
        this.protectionPathOrig.set(path);
        this.protectionPath.reset();
        this.protectionRectOrig.setEmpty();
        this.protectionRectOrig.set(rect);
        this.protectionRect.setEmpty();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0098, code lost:
    
        if (kotlin.jvm.internal.Intrinsics.areEqual(java.lang.Integer.valueOf(r1.getPhysicalWidth()), r2 != null ? java.lang.Integer.valueOf(r2.getPhysicalWidth()) : null) == false) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateConfiguration(String str) {
        boolean z;
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = getContext().getDisplay();
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        Display.Mode mode = this.displayMode;
        this.displayMode = displayInfo.getMode();
        String str2 = displayInfo.uniqueId;
        boolean z2 = false;
        if (!Intrinsics.areEqual(this.displayUniqueId, str2)) {
            this.displayUniqueId = str2;
            this.shouldDrawCutout = DisplayCutout.getFillBuiltInDisplayCutout(getContext().getResources(), this.displayUniqueId);
            IndicatorCutoutUtil indicatorCutoutUtil = this.cutoutUtil;
            if (indicatorCutoutUtil.isUDCModel) {
                if (indicatorCutoutUtil.isUDCMainDisplay()) {
                    if (this.settingsHelper.mItemLists.get("fill_udc_display_cutout").getIntValue() != 0) {
                        z = true;
                        enableShowProtection(z);
                    }
                }
                z = false;
                enableShowProtection(z);
            }
            invalidate();
        }
        Display.Mode mode2 = this.displayMode;
        if (mode != null) {
            if (Intrinsics.areEqual(Integer.valueOf(mode.getPhysicalHeight()), mode2 != null ? Integer.valueOf(mode2.getPhysicalHeight()) : null)) {
            }
        }
        z2 = true;
        if (!(!z2 && Intrinsics.areEqual(this.displayInfo.displayCutout, displayInfo.displayCutout) && this.displayRotation == displayInfo.rotation) && Intrinsics.areEqual(str, displayInfo.uniqueId)) {
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
                    throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown rotation: ", i3));
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
        this.cameraProtectionProgress = 1.0f;
        this.paintForCameraProtection = new Paint();
        this.mBoundingRect = new Rect();
        this.settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.cutoutUtil = new IndicatorCutoutUtil(getContext(), new IndicatorGardenInputProperties(getContext()), (DisplayLifecycle) Dependency.get(DisplayLifecycle.class));
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
        this.cameraProtectionProgress = 1.0f;
        this.paintForCameraProtection = new Paint();
        this.mBoundingRect = new Rect();
        this.settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        this.cutoutUtil = new IndicatorCutoutUtil(getContext(), new IndicatorGardenInputProperties(getContext()), (DisplayLifecycle) Dependency.get(DisplayLifecycle.class));
    }
}
