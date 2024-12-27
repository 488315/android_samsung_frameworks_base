package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.os.Trace;
import android.view.Display;
import android.view.DisplayInfo;
import com.android.app.tracing.TraceUtilsKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class BiometricDisplayListener implements DisplayManager.DisplayListener {
    public final DisplayInfo cachedDisplayInfo;
    public final Context context;
    public final DisplayManager displayManager;
    public final Handler handler;
    public final Function0 onChanged;
    public final SensorType sensorType;

    public abstract class SensorType {

        public final class Generic extends SensorType {
            public static final Generic INSTANCE = new Generic();

            private Generic() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Generic);
            }

            public final int hashCode() {
                return -12627015;
            }

            public final String toString() {
                return "Generic";
            }
        }

        public final class SideFingerprint extends SensorType {
            public final FingerprintSensorPropertiesInternal properties;

            public SideFingerprint(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
                super(null);
                this.properties = fingerprintSensorPropertiesInternal;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof SideFingerprint) && Intrinsics.areEqual(this.properties, ((SideFingerprint) obj).properties);
            }

            public final int hashCode() {
                return this.properties.hashCode();
            }

            public final String toString() {
                return "SideFingerprint(properties=" + this.properties + ")";
            }
        }

        public final class UnderDisplayFingerprint extends SensorType {
            public static final UnderDisplayFingerprint INSTANCE = new UnderDisplayFingerprint();

            private UnderDisplayFingerprint() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof UnderDisplayFingerprint);
            }

            public final int hashCode() {
                return -949352036;
            }

            public final String toString() {
                return "UnderDisplayFingerprint";
            }
        }

        private SensorType() {
        }

        public /* synthetic */ SensorType(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BiometricDisplayListener(Context context, DisplayManager displayManager, Handler handler, SensorType sensorType, Function0 function0) {
        this.context = context;
        this.displayManager = displayManager;
        this.handler = handler;
        this.sensorType = sensorType;
        this.onChanged = function0;
        this.cachedDisplayInfo = new DisplayInfo();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayChanged(int i) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("BiometricDisplayListener(" + this.sensorType + ")#onDisplayChanged");
        }
        try {
            int i2 = this.cachedDisplayInfo.rotation;
            Display display = this.context.getDisplay();
            if (display != null) {
                display.getDisplayInfo(this.cachedDisplayInfo);
            }
            boolean z = i2 != this.cachedDisplayInfo.rotation;
            if (this.sensorType instanceof SensorType.SideFingerprint) {
                this.onChanged.invoke();
            } else if (z) {
                this.onChanged.invoke();
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public /* synthetic */ BiometricDisplayListener(Context context, DisplayManager displayManager, Handler handler, SensorType sensorType, Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, displayManager, handler, (i & 8) != 0 ? SensorType.Generic.INSTANCE : sensorType, function0);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public final void onDisplayRemoved(int i) {
    }
}
