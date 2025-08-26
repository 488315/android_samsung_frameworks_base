package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shared.customization.data.SensorLocation;
import dagger.Lazy;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class DefaultDeviceEntrySection extends KeyguardSection {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthController authController;
    public final Logger blueprintLogger;
    public final Context context;
    public final int deviceEntryIconViewId = R.id.device_entry_icon_view;
    public final Lazy deviceEntryIconViewModel;
    public final FeatureFlags featureFlags;
    public final WindowManager windowManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DefaultDeviceEntrySection(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, AuthController authController, WindowManager windowManager, Context context, NotificationPanelView notificationPanelView, FeatureFlags featureFlags, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, LogBuffer logBuffer, LogBuffer logBuffer2) {
        this.authController = authController;
        this.windowManager = windowManager;
        this.context = context;
        this.featureFlags = featureFlags;
        this.deviceEntryIconViewModel = lazy;
        this.blueprintLogger = new Logger(logBuffer2, "DefaultDeviceEntrySection");
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) throws Resources.NotFoundException {
        Lazy lazy = this.deviceEntryIconViewModel;
        boolean zBooleanValue = ((Boolean) ((DeviceEntryIconViewModel) lazy.get()).isUdfpsSupported.$$delegate_0.getValue()).booleanValue();
        Logger logger = this.blueprintLogger;
        final int i = 0;
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.DefaultDeviceEntrySection$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i) {
                    case 0:
                        int i2 = DefaultDeviceEntrySection.$r8$clinit;
                        return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isUdfpsSupported=", logMessage.getBool1());
                    default:
                        int i3 = DefaultDeviceEntrySection.$r8$clinit;
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        String str3 = logMessage.getStr3();
                        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("udfpsLocation=", str1, ", scaledLocation=", str2, ", unusedAuthController=");
                        sbM.append(str3);
                        return sbM.toString();
                }
            }
        };
        LogLevel logLevel = LogLevel.DEBUG;
        LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), logLevel, function1, null);
        logMessageObtain.setBool1(zBooleanValue);
        logger.getBuffer().commit(logMessageObtain);
        AuthController authController = this.authController;
        float f = authController.mScaleFactor;
        int dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R$dimen.lock_icon_margin_bottom);
        Rect bounds = this.windowManager.getCurrentWindowMetrics().getBounds();
        float f2 = bounds.right;
        Flags flags = Flags.INSTANCE;
        this.featureFlags.getClass();
        float f3 = bounds.bottom;
        int i2 = (int) ((DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f) * 36);
        if (!zBooleanValue) {
            centerIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core(new Point((int) (f2 / 2), (int) (f3 - ((dimensionPixelSize + i2) * f))), i2 * f, constraintSet);
            return;
        }
        SensorLocation sensorLocation = (SensorLocation) ((DeviceEntryIconViewModel) lazy.get()).udfpsLocation.$$delegate_0.getValue();
        if (sensorLocation != null) {
            final int i3 = 1;
            LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), logLevel, new Function1() { // from class: com.android.systemui.keyguard.ui.view.layout.sections.DefaultDeviceEntrySection$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    switch (i3) {
                        case 0:
                            int i22 = DefaultDeviceEntrySection.$r8$clinit;
                            return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("isUdfpsSupported=", logMessage.getBool1());
                        default:
                            int i32 = DefaultDeviceEntrySection.$r8$clinit;
                            String str1 = logMessage.getStr1();
                            String str2 = logMessage.getStr2();
                            String str3 = logMessage.getStr3();
                            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("udfpsLocation=", str1, ", scaledLocation=", str2, ", unusedAuthController=");
                            sbM.append(str3);
                            return sbM.toString();
                    }
                }
            }, null);
            logMessageObtain2.setStr1(String.valueOf(sensorLocation));
            int i4 = sensorLocation.naturalCenterX;
            float f4 = sensorLocation.scale;
            int i5 = sensorLocation.naturalCenterY;
            logMessageObtain2.setStr2("(" + (i4 * f4) + ", " + (i5 * f4) + ")");
            logMessageObtain2.setStr3(String.valueOf(authController.getUdfpsLocation()));
            logger.getBuffer().commit(logMessageObtain2);
            centerIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core(new Point((int) (((float) i4) * f4), (int) (((float) i5) * f4)), ((float) sensorLocation.naturalRadius) * f4, constraintSet);
        }
    }

    public final void centerIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core(Point point, float f, ConstraintSet constraintSet) {
        Rect rect = new Rect();
        int i = point.x;
        int i2 = (int) f;
        int i3 = point.y;
        rect.set(i - i2, i3 - i2, i + i2, i3 + i2);
        int i4 = rect.right - rect.left;
        int i5 = this.deviceEntryIconViewId;
        constraintSet.constrainWidth(i5, i4);
        constraintSet.constrainHeight(i5, rect.bottom - rect.top);
        constraintSet.connect(i5, 3, 0, 3, rect.top);
        constraintSet.connect(i5, 6, 0, 6, rect.left);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
        ExtensionsKt.removeView(constraintLayout, this.deviceEntryIconViewId);
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
    }
}
