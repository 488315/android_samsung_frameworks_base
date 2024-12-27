package com.android.systemui.controls.ui;

import com.android.systemui.R;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapWithDefaultKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class RenderInfoKt {
    public static final Map deviceIconMap;

    static {
        MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.mapOf(new Pair(49001, new Pair(Integer.valueOf(R.color.control_default_foreground), Integer.valueOf(R.color.control_default_background))), new Pair(49002, new Pair(Integer.valueOf(R.color.thermo_heat_foreground), Integer.valueOf(R.color.control_enabled_thermo_heat_background))), new Pair(49003, new Pair(Integer.valueOf(R.color.thermo_cool_foreground), Integer.valueOf(R.color.control_enabled_thermo_cool_background))), new Pair(13, new Pair(Integer.valueOf(R.color.light_foreground), Integer.valueOf(R.color.control_enabled_light_background))), new Pair(50, new Pair(Integer.valueOf(R.color.camera_foreground), Integer.valueOf(R.color.control_enabled_default_background)))), new Function1() { // from class: com.android.systemui.controls.ui.RenderInfoKt$deviceColorMap$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Number) obj).intValue();
                return new Pair(Integer.valueOf(R.color.control_foreground), Integer.valueOf(R.color.control_enabled_default_background));
            }
        });
        deviceIconMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.mapOf(new Pair(49001, Integer.valueOf(R.drawable.ic_device_thermostat_off)), new Pair(49002, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(49003, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(49004, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(49005, Integer.valueOf(R.drawable.ic_device_thermostat_off)), new Pair(49, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(13, Integer.valueOf(R.drawable.ic_device_light)), new Pair(50, Integer.valueOf(R.drawable.ic_device_camera)), new Pair(45, Integer.valueOf(R.drawable.ic_device_lock)), new Pair(21, Integer.valueOf(R.drawable.ic_device_switch)), new Pair(15, Integer.valueOf(R.drawable.ic_device_outlet)), new Pair(32, Integer.valueOf(R.drawable.ic_device_vacuum)), new Pair(26, Integer.valueOf(R.drawable.ic_device_mop)), new Pair(3, Integer.valueOf(R.drawable.ic_device_air_freshener)), new Pair(4, Integer.valueOf(R.drawable.ic_device_air_purifier)), new Pair(8, Integer.valueOf(R.drawable.ic_device_fan)), new Pair(10, Integer.valueOf(R.drawable.ic_device_hood)), new Pair(12, Integer.valueOf(R.drawable.ic_device_kettle)), new Pair(14, Integer.valueOf(R.drawable.ic_device_microwave)), new Pair(17, Integer.valueOf(R.drawable.ic_device_remote_control)), new Pair(18, Integer.valueOf(R.drawable.ic_device_set_top)), new Pair(20, Integer.valueOf(R.drawable.ic_device_styler)), new Pair(22, Integer.valueOf(R.drawable.ic_device_tv)), new Pair(23, Integer.valueOf(R.drawable.ic_device_water_heater)), new Pair(24, Integer.valueOf(R.drawable.ic_device_dishwasher)), new Pair(28, Integer.valueOf(R.drawable.ic_device_multicooker)), new Pair(30, Integer.valueOf(R.drawable.ic_device_sprinkler)), new Pair(31, Integer.valueOf(R.drawable.ic_device_washer)), new Pair(34, Integer.valueOf(R.drawable.ic_device_blinds)), new Pair(38, Integer.valueOf(R.drawable.ic_device_drawer)), new Pair(39, Integer.valueOf(R.drawable.ic_device_garage)), new Pair(40, Integer.valueOf(R.drawable.ic_device_gate)), new Pair(41, Integer.valueOf(R.drawable.ic_device_pergola)), new Pair(43, Integer.valueOf(R.drawable.ic_device_window)), new Pair(44, Integer.valueOf(R.drawable.ic_device_valve)), new Pair(46, Integer.valueOf(R.drawable.ic_device_security_system)), new Pair(48, Integer.valueOf(R.drawable.ic_device_refrigerator)), new Pair(51, Integer.valueOf(R.drawable.ic_device_doorbell)), new Pair(52, -1), new Pair(1, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(2, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(5, Integer.valueOf(R.drawable.ic_device_kettle)), new Pair(6, Integer.valueOf(R.drawable.ic_device_air_freshener)), new Pair(16, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(19, Integer.valueOf(R.drawable.ic_device_cooking)), new Pair(7, Integer.valueOf(R.drawable.ic_device_display)), new Pair(25, Integer.valueOf(R.drawable.ic_device_washer)), new Pair(27, Integer.valueOf(R.drawable.ic_device_outdoor_garden)), new Pair(29, Integer.valueOf(R.drawable.ic_device_water)), new Pair(33, Integer.valueOf(R.drawable.ic_device_pergola)), new Pair(35, Integer.valueOf(R.drawable.ic_device_drawer)), new Pair(36, Integer.valueOf(R.drawable.ic_device_blinds)), new Pair(37, Integer.valueOf(R.drawable.ic_device_door)), new Pair(42, Integer.valueOf(R.drawable.ic_device_window)), new Pair(47, Integer.valueOf(R.drawable.ic_device_thermostat)), new Pair(-1000, Integer.valueOf(R.drawable.ic_error_outline))), new Function1() { // from class: com.android.systemui.controls.ui.RenderInfoKt$deviceIconMap$1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                ((Number) obj).intValue();
                return Integer.valueOf(R.drawable.ic_device_unknown);
            }
        });
    }
}
