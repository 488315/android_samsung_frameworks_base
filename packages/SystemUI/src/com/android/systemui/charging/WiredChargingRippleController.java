package com.android.systemui.charging;

import android.content.Context;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
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
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;

public final class WiredChargingRippleController {
    public final Context context;
    public final float normalizedPortPosX;
    public final float normalizedPortPosY;
    public final RippleView rippleView;
    public final SystemClock systemClock;
    public final UiEventLogger uiEventLogger;
    public final WindowManager.LayoutParams windowLayoutParams;
    public final WindowManager windowManager;

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
            wiredChargingRippleController.windowLayoutParams.packageName = wiredChargingRippleController.context.getOpPackageName();
            rippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.charging.WiredChargingRippleController$startRipple$1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    WiredChargingRippleController wiredChargingRippleController2 = WiredChargingRippleController.this;
                    Rect bounds = wiredChargingRippleController2.windowManager.getCurrentWindowMetrics().getBounds();
                    int width = bounds.width();
                    int height = bounds.height();
                    float max = Integer.max(width, height) * 2.0f;
                    RippleView rippleView2 = wiredChargingRippleController2.rippleView;
                    RippleShader rippleShader = rippleView2.rippleShader;
                    if (rippleShader == null) {
                        rippleShader = null;
                    }
                    RippleShader.RippleSize rippleSize = rippleShader.rippleSize;
                    rippleSize.getClass();
                    rippleSize.setSizeAtProgresses(rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, max, max));
                    Display display = wiredChargingRippleController2.context.getDisplay();
                    Integer valueOf = display != null ? Integer.valueOf(display.getRotation()) : null;
                    float f = wiredChargingRippleController2.normalizedPortPosY;
                    float f2 = wiredChargingRippleController2.normalizedPortPosX;
                    if (valueOf != null && valueOf.intValue() == 0) {
                        rippleView2.setCenter(width * f2, height * f);
                    } else if (valueOf != null && valueOf.intValue() == 1) {
                        rippleView2.setCenter(width * f, (1 - f2) * height);
                    } else if (valueOf != null && valueOf.intValue() == 2) {
                        float f3 = 1;
                        rippleView2.setCenter((f3 - f2) * width, (f3 - f) * height);
                    } else if (valueOf != null && valueOf.intValue() == 3) {
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
            wiredChargingRippleController.windowManager.addView(rippleView, wiredChargingRippleController.windowLayoutParams);
            wiredChargingRippleController.uiEventLogger.log(WiredChargingRippleEvent.CHARGING_RIPPLE_PLAYED);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class WiredChargingRippleEvent implements UiEventLogger.UiEventEnum {
        public static final /* synthetic */ WiredChargingRippleEvent[] $VALUES;
        public static final WiredChargingRippleEvent CHARGING_RIPPLE_PLAYED;
        private final int _id;

        static {
            WiredChargingRippleEvent wiredChargingRippleEvent = new WiredChargingRippleEvent("CHARGING_RIPPLE_PLAYED", 0, 829);
            CHARGING_RIPPLE_PLAYED = wiredChargingRippleEvent;
            WiredChargingRippleEvent[] wiredChargingRippleEventArr = {wiredChargingRippleEvent};
            $VALUES = wiredChargingRippleEventArr;
            EnumEntriesKt.enumEntries(wiredChargingRippleEventArr);
        }

        private WiredChargingRippleEvent(String str, int i, int i2) {
            this._id = i2;
        }

        public static WiredChargingRippleEvent valueOf(String str) {
            return (WiredChargingRippleEvent) Enum.valueOf(WiredChargingRippleEvent.class, str);
        }

        public static WiredChargingRippleEvent[] values() {
            return (WiredChargingRippleEvent[]) $VALUES.clone();
        }

        public final int getId() {
            return this._id;
        }
    }

    public WiredChargingRippleController(CommandRegistry commandRegistry, BatteryController batteryController, ConfigurationController configurationController, FeatureFlags featureFlags, Context context, WindowManager windowManager, SystemClock systemClock, UiEventLogger uiEventLogger) {
        this.context = context;
        this.windowManager = windowManager;
        this.systemClock = systemClock;
        this.uiEventLogger = uiEventLogger;
        Flags.INSTANCE.getClass();
        if (((FeatureFlagsClassicRelease) featureFlags).isEnabled(Flags.CHARGING_RIPPLE)) {
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
