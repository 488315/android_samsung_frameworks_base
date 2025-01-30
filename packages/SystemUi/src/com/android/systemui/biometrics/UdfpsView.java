package com.android.systemui.biometrics;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.fingerprint.IUdfpsRefreshRateRequestCallback;
import android.os.RemoteException;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import com.android.settingslib.udfps.UdfpsOverlayParams;
import com.android.systemui.R$styleable;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.util.concurrency.ExecutionImpl;
import kotlin.jdk7.AutoCloseableKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UdfpsView extends FrameLayout implements DozeReceiver {
    public UdfpsAnimationViewController animationViewController;
    public String debugMessage;
    public final Paint debugTextPaint;
    public boolean isDisplayConfigured;
    public UdfpsDisplayModeProvider mUdfpsDisplayMode;
    public UdfpsOverlayParams overlayParams;
    public Rect sensorRect;
    public final float sensorTouchAreaCoefficient;
    public boolean useExpandedOverlay;

    public UdfpsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sensorRect = new Rect();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-16776961);
        paint.setTextSize(32.0f);
        this.debugTextPaint = paint;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.UdfpsView, 0, 0);
        try {
            if (!obtainStyledAttributes.hasValue(1)) {
                throw new IllegalArgumentException("UdfpsView must contain sensorTouchAreaCoefficient".toString());
            }
            float f = obtainStyledAttributes.getFloat(1, 0.0f);
            AutoCloseableKt.closeFinally(obtainStyledAttributes, null);
            this.sensorTouchAreaCoefficient = f;
            this.overlayParams = new UdfpsOverlayParams(null, null, 0, 0, 0.0f, 0, 63, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                AutoCloseableKt.closeFinally(obtainStyledAttributes, th);
                throw th2;
            }
        }
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public final void dozeTimeTick() {
        UdfpsAnimationViewController udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController == null || !udfpsAnimationViewController.getView().dozeTimeTick()) {
            return;
        }
        udfpsAnimationViewController.getView().postInvalidate();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isDisplayConfigured) {
            return;
        }
        String str = this.debugMessage;
        if (str == null || str.length() == 0) {
            return;
        }
        String str2 = this.debugMessage;
        Intrinsics.checkNotNull(str2);
        canvas.drawText(str2, 0.0f, 160.0f, this.debugTextPaint);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        UdfpsAnimationViewController udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            Intrinsics.checkNotNull(udfpsAnimationViewController);
            if (udfpsAnimationViewController.shouldPauseAuth()) {
                return false;
            }
        }
        return true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        UdfpsAnimationViewController udfpsAnimationViewController = this.animationViewController;
        if (this.useExpandedOverlay) {
            if (udfpsAnimationViewController != null) {
                udfpsAnimationViewController.getView().onSensorRectUpdated(new RectF(this.sensorRect));
                return;
            }
            return;
        }
        this.sensorRect.set(0, 0, this.overlayParams.sensorBounds.width() + 0, this.overlayParams.sensorBounds.height() + 0);
        UdfpsAnimationViewController udfpsAnimationViewController2 = this.animationViewController;
        if (udfpsAnimationViewController2 != null) {
            udfpsAnimationViewController2.getView().onSensorRectUpdated(new RectF(this.sensorRect));
        }
    }

    public final void unconfigureDisplay$1() {
        this.isDisplayConfigured = false;
        UdfpsAnimationViewController udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            udfpsAnimationViewController.getView().onDisplayUnconfigured();
            udfpsAnimationViewController.getView().postInvalidate();
        }
        UdfpsDisplayModeProvider udfpsDisplayModeProvider = this.mUdfpsDisplayMode;
        if (udfpsDisplayModeProvider != null) {
            UdfpsDisplayMode udfpsDisplayMode = (UdfpsDisplayMode) udfpsDisplayModeProvider;
            ((ExecutionImpl) udfpsDisplayMode.execution).mainLooper.isCurrentThread();
            UdfpsLogger udfpsLogger = udfpsDisplayMode.logger;
            udfpsLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", logLevel, "disable", null, 8, null);
            Request request = udfpsDisplayMode.currentRequest;
            if (request == null) {
                LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", LogLevel.WARNING, "disable | already disabled", null, 8, null);
                return;
            }
            Trace.beginSection("UdfpsDisplayMode.disable");
            try {
                IUdfpsRefreshRateRequestCallback iUdfpsRefreshRateRequestCallback = udfpsDisplayMode.authController.mUdfpsRefreshRateRequestCallback;
                Intrinsics.checkNotNull(iUdfpsRefreshRateRequestCallback);
                iUdfpsRefreshRateRequestCallback.onRequestDisabled(request.displayId);
                LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", logLevel, "disable | removed the UDFPS refresh rate request", null, 8, null);
            } catch (RemoteException e) {
                LogLevel logLevel2 = LogLevel.ERROR;
                UdfpsLogger$e$2 udfpsLogger$e$2 = new UdfpsLogger$e$2("disable");
                LogBuffer logBuffer = udfpsLogger.logBuffer;
                logBuffer.commit(logBuffer.obtain("UdfpsDisplayMode", logLevel2, udfpsLogger$e$2, e));
            }
            udfpsDisplayMode.currentRequest = null;
            LogBuffer.log$default(udfpsLogger.logBuffer, "UdfpsDisplayMode", LogLevel.WARNING, "disable | onDisabled is null", null, 8, null);
            Trace.endSection();
        }
    }
}
