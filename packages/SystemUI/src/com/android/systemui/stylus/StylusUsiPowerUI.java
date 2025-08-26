package com.android.systemui.stylus;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.log.DebugLogger;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes3.dex */
public final class StylusUsiPowerUI {
    public static final int USI_NOTIFICATION_ID;
    public final Context context;
    public final Handler handler;
    public Integer inputDeviceId;
    public final InputManager inputManager;
    public InstanceId instanceId;
    public final NotificationManagerCompat notificationManager;
    public boolean suppressed;
    public final UiEventLogger uiEventLogger;
    public float batteryCapacity = 1.0f;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(8192);
    public final StylusUsiPowerUI$receiver$1 receiver = new BroadcastReceiver() { // from class: com.android.systemui.stylus.StylusUsiPowerUI$receiver$1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null) {
                int iHashCode = action.hashCode();
                if (iHashCode != -263791366) {
                    if (iHashCode == 710718844 && action.equals("StylusUsiPowerUI.dismiss")) {
                        DebugLogger debugLogger = DebugLogger.INSTANCE;
                        boolean z = Build.IS_DEBUGGABLE;
                        Reflection.getOrCreateKotlinClass(StylusUsiPowerUI$receiver$1.class).getSimpleName();
                        StylusUsiPowerUI stylusUsiPowerUI = this.this$0;
                        StylusUiEvent stylusUiEvent = StylusUiEvent.STYLUS_LOW_BATTERY_NOTIFICATION_DISMISSED;
                        int i = StylusUsiPowerUI.USI_NOTIFICATION_ID;
                        stylusUsiPowerUI.logUiEvent(stylusUiEvent);
                        StylusUsiPowerUI stylusUsiPowerUI2 = this.this$0;
                        stylusUsiPowerUI2.getClass();
                        stylusUsiPowerUI2.handler.post(new StylusUsiPowerUI$updateSuppression$1(stylusUsiPowerUI2, true));
                        return;
                    }
                    return;
                }
                if (action.equals("StylusUsiPowerUI.click")) {
                    DebugLogger debugLogger2 = DebugLogger.INSTANCE;
                    boolean z2 = Build.IS_DEBUGGABLE;
                    Reflection.getOrCreateKotlinClass(StylusUsiPowerUI$receiver$1.class).getSimpleName();
                    StylusUsiPowerUI stylusUsiPowerUI3 = this.this$0;
                    StylusUiEvent stylusUiEvent2 = StylusUiEvent.STYLUS_LOW_BATTERY_NOTIFICATION_CLICKED;
                    int i2 = StylusUsiPowerUI.USI_NOTIFICATION_ID;
                    stylusUsiPowerUI3.logUiEvent(stylusUiEvent2);
                    StylusUsiPowerUI stylusUsiPowerUI4 = this.this$0;
                    stylusUsiPowerUI4.getClass();
                    stylusUsiPowerUI4.handler.post(new StylusUsiPowerUI$updateSuppression$1(stylusUsiPowerUI4, true));
                    if (this.this$0.inputDeviceId == null) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    Integer num = this.this$0.inputDeviceId;
                    num.getClass();
                    bundle.putInt("device_input_id", num.intValue());
                    try {
                        context.startActivity(new Intent("com.android.settings.STYLUS_USI_DETAILS_SETTINGS").putExtra(":settings:show_fragment_args", bundle).addFlags(67108864).addFlags(268435456));
                        Unit unit = Unit.INSTANCE;
                    } catch (ActivityNotFoundException unused) {
                        Log.e("StylusUsiPowerUI", "Cannot open USI details page.");
                    }
                }
            }
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ void getACTION_CLICKED_LOW_BATTERY$annotations() {
        }

        public static /* synthetic */ void getACTION_DISMISSED_LOW_BATTERY$annotations() {
        }

        public static /* synthetic */ void getACTION_STYLUS_USI_DETAILS$annotations() {
        }

        public static /* synthetic */ void getKEY_DEVICE_INPUT_ID$annotations() {
        }

        public static /* synthetic */ void getKEY_SETTINGS_FRAGMENT_ARGS$annotations() {
        }
    }

    static {
        new Companion(null);
        Reflection.getOrCreateKotlinClass(StylusUsiPowerUI.class).getSimpleName();
        USI_NOTIFICATION_ID = R.string.stylus_battery_low_percentage;
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.stylus.StylusUsiPowerUI$receiver$1] */
    public StylusUsiPowerUI(Context context, NotificationManagerCompat notificationManagerCompat, InputManager inputManager, Handler handler, UiEventLogger uiEventLogger) {
        this.context = context;
        this.notificationManager = notificationManagerCompat;
        this.inputManager = inputManager;
        this.handler = handler;
        this.uiEventLogger = uiEventLogger;
    }

    public final InstanceId getInstanceId() {
        InstanceId instanceIdNewInstanceId = this.instanceId;
        if (instanceIdNewInstanceId == null) {
            if (instanceIdNewInstanceId == null) {
                instanceIdNewInstanceId = this.instanceIdSequence.newInstanceId();
            }
            this.instanceId = instanceIdNewInstanceId;
        }
        return this.instanceId;
    }

    public final void logUiEvent(StylusUiEvent stylusUiEvent) {
        this.uiEventLogger.logWithInstanceIdAndPosition(stylusUiEvent, ActivityManager.getCurrentUser(), this.context.getPackageName(), getInstanceId(), (int) (this.batteryCapacity * 100.0d));
    }

    public static /* synthetic */ void getInputDeviceId$annotations() {
    }

    public static /* synthetic */ void getInstanceIdSequence$annotations() {
    }

    public static /* synthetic */ void getReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }
}
