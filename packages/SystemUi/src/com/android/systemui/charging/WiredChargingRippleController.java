package com.android.systemui.charging;

import android.content.Context;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.view.View;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.List;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WiredChargingRippleController {
    public final Context context;
    public final float normalizedPortPosX;
    public final float normalizedPortPosY;
    public final RippleView rippleView;
    public final UiEventLogger uiEventLogger;
    public final WindowManager.LayoutParams windowLayoutParams;
    public final WindowManager windowManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChargingRippleCommand implements Command {
        public ChargingRippleCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            final WiredChargingRippleController wiredChargingRippleController = WiredChargingRippleController.this;
            RippleView rippleView = wiredChargingRippleController.rippleView;
            if (rippleView.animator.isRunning() || rippleView.getParent() != null) {
                return;
            }
            WindowManager.LayoutParams layoutParams = wiredChargingRippleController.windowLayoutParams;
            layoutParams.packageName = wiredChargingRippleController.context.getOpPackageName();
            rippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.charging.WiredChargingRippleController$startRipple$1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    WiredChargingRippleController wiredChargingRippleController2 = WiredChargingRippleController.this;
                    Rect bounds = wiredChargingRippleController2.windowManager.getCurrentWindowMetrics().getBounds();
                    int width = bounds.width();
                    int height = bounds.height();
                    float max = Integer.max(width, height) * 2.0f;
                    RippleView rippleView2 = wiredChargingRippleController2.rippleView;
                    rippleView2.getRippleShader().rippleSize.setMaxSize(max, max);
                    int rotation = wiredChargingRippleController2.context.getDisplay().getRotation();
                    float f = wiredChargingRippleController2.normalizedPortPosY;
                    float f2 = wiredChargingRippleController2.normalizedPortPosX;
                    if (rotation == 0) {
                        rippleView2.setCenter(width * f2, height * f);
                    } else if (rotation == 1) {
                        rippleView2.setCenter(width * f, (1 - f2) * height);
                    } else if (rotation == 2) {
                        float f3 = 1;
                        rippleView2.setCenter((f3 - f2) * width, (f3 - f) * height);
                    } else if (rotation == 3) {
                        rippleView2.setCenter((1 - f) * width, height * f2);
                    }
                    final WiredChargingRippleController wiredChargingRippleController3 = WiredChargingRippleController.this;
                    wiredChargingRippleController3.rippleView.startRipple(new Runnable() { // from class: com.android.systemui.charging.WiredChargingRippleController$startRipple$1$onViewAttachedToWindow$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            WiredChargingRippleController wiredChargingRippleController4 = WiredChargingRippleController.this;
                            wiredChargingRippleController4.windowManager.removeView(wiredChargingRippleController4.rippleView);
                        }
                    });
                    WiredChargingRippleController.this.rippleView.removeOnAttachStateChangeListener(this);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
            wiredChargingRippleController.windowManager.addView(rippleView, layoutParams);
            wiredChargingRippleController.uiEventLogger.log(WiredChargingRippleEvent.CHARGING_RIPPLE_PLAYED);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum WiredChargingRippleEvent implements UiEventLogger.UiEventEnum {
        CHARGING_RIPPLE_PLAYED(829);

        private final int _id;

        WiredChargingRippleEvent(int i) {
            this._id = i;
        }

        public final int getId() {
            return this._id;
        }
    }

    public WiredChargingRippleController(CommandRegistry commandRegistry, BatteryController batteryController, ConfigurationController configurationController, FeatureFlags featureFlags, Context context, WindowManager windowManager, SystemClock systemClock, UiEventLogger uiEventLogger) {
        this.context = context;
        this.windowManager = windowManager;
        this.uiEventLogger = uiEventLogger;
        Flags.INSTANCE.getClass();
        if (((FeatureFlagsRelease) featureFlags).isEnabled(Flags.CHARGING_RIPPLE)) {
            SystemProperties.getBoolean("persist.debug.suppress-charging-ripple", false);
        }
        this.normalizedPortPosX = context.getResources().getFloat(R.dimen.physical_charger_port_location_normalized_x);
        this.normalizedPortPosY = context.getResources().getFloat(R.dimen.physical_charger_port_location_normalized_y);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.format = -3;
        layoutParams.type = 2009;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Wired Charging Animation");
        layoutParams.flags = 24;
        layoutParams.setTrustedOverlay();
        this.windowLayoutParams = layoutParams;
        RippleView rippleView = new RippleView(context, null);
        rippleView.setupShader(RippleShader.RippleShape.CIRCLE);
        this.rippleView = rippleView;
        commandRegistry.registerCommand("charging-ripple", new Function0() { // from class: com.android.systemui.charging.WiredChargingRippleController.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return WiredChargingRippleController.this.new ChargingRippleCommand();
            }
        });
        rippleView.setColor(Utils.getColorAttr(android.R.attr.colorAccent, context).getDefaultColor(), 115);
    }

    public static /* synthetic */ void getRippleView$annotations() {
    }
}
