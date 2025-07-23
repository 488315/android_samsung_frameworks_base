package com.android.systemui.controls.ui;

import com.android.systemui.R;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapWithDefaultKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class SecRenderInfoKt {
    public static final Map defaultActionIconMap;
    public static final Map secDeviceColorMap;
    public static final Map statusIconResourceMap = MapsKt__MapsKt.mapOf(new Pair(1, Integer.valueOf(R.drawable.ic_control_error)), new Pair(2, Integer.valueOf(R.drawable.ic_control_scene_badge_failed)));

    static {
        final int i = 0;
        secDeviceColorMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.mapOf(new Pair(49001, new Pair(Integer.valueOf(R.color.sec_control_default_foreground), Integer.valueOf(R.color.sec_control_default_background))), new Pair(49002, new Pair(Integer.valueOf(R.color.sec_thermo_heat_foreground), Integer.valueOf(R.color.sec_control_enabled_thermo_heat_background))), new Pair(49003, new Pair(Integer.valueOf(R.color.sec_thermo_cool_foreground), Integer.valueOf(R.color.sec_control_enabled_thermo_cool_background))), new Pair(13, new Pair(Integer.valueOf(R.color.sec_light_foreground), Integer.valueOf(R.color.sec_control_enabled_light_background))), new Pair(50, new Pair(Integer.valueOf(R.color.camera_foreground), Integer.valueOf(R.color.control_enabled_default_background)))), new Function1() { // from class: com.android.systemui.controls.ui.SecRenderInfoKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Integer num = (Integer) obj;
                switch (i) {
                    case 0:
                        num.intValue();
                        return new Pair(Integer.valueOf(R.color.sec_control_foreground), Integer.valueOf(R.color.sec_control_enabled_default_background));
                    default:
                        num.getClass();
                        Map map = SecRenderInfoKt.secDeviceColorMap;
                        return Integer.valueOf(R.drawable.ic_control_action_button_switch);
                }
            }
        });
        final int i2 = 1;
        defaultActionIconMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.controls.ui.SecRenderInfoKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Integer num = (Integer) obj;
                switch (i2) {
                    case 0:
                        num.intValue();
                        return new Pair(Integer.valueOf(R.color.sec_control_foreground), Integer.valueOf(R.color.sec_control_enabled_default_background));
                    default:
                        num.getClass();
                        Map map = SecRenderInfoKt.secDeviceColorMap;
                        return Integer.valueOf(R.drawable.ic_control_action_button_switch);
                }
            }
        });
    }
}
