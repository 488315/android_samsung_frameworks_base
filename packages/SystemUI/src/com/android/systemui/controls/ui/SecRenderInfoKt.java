package com.android.systemui.controls.ui;

import com.android.systemui.R;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapWithDefaultKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

public abstract class SecRenderInfoKt {
    public static final Map secDeviceColorMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.mapOf(new Pair(49001, new Pair(Integer.valueOf(R.color.sec_control_default_foreground), Integer.valueOf(R.color.sec_control_default_background))), new Pair(49002, new Pair(Integer.valueOf(R.color.sec_thermo_heat_foreground), Integer.valueOf(R.color.sec_control_enabled_thermo_heat_background))), new Pair(49003, new Pair(Integer.valueOf(R.color.sec_thermo_cool_foreground), Integer.valueOf(R.color.sec_control_enabled_thermo_cool_background))), new Pair(13, new Pair(Integer.valueOf(R.color.sec_light_foreground), Integer.valueOf(R.color.sec_control_enabled_light_background))), new Pair(50, new Pair(Integer.valueOf(R.color.camera_foreground), Integer.valueOf(R.color.control_enabled_default_background)))), new Function1() { // from class: com.android.systemui.controls.ui.SecRenderInfoKt$secDeviceColorMap$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ((Number) obj).intValue();
            return new Pair(Integer.valueOf(R.color.sec_control_foreground), Integer.valueOf(R.color.sec_control_enabled_default_background));
        }
    });
    public static final Map defaultActionIconMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.controls.ui.SecRenderInfoKt$defaultActionIconMap$1
        @Override // kotlin.jvm.functions.Function1
        public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
            ((Number) obj).intValue();
            return Integer.valueOf(R.drawable.ic_control_action_button_switch);
        }
    });
    public static final Map statusIconResourceMap = MapsKt__MapsKt.mapOf(new Pair(1, Integer.valueOf(R.drawable.ic_control_error)), new Pair(2, Integer.valueOf(R.drawable.ic_control_scene_badge_failed)));
}
