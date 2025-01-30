package com.android.systemui.controls.ui;

import com.android.systemui.R;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapWithDefaultKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class CustomRenderInfoKt {
    public static final Map deviceCustomColorMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.mapOf(new Pair(49001, new Pair(Integer.valueOf(R.color.control_custom_default_foreground), Integer.valueOf(R.color.control_custom_default_background))), new Pair(49002, new Pair(Integer.valueOf(R.color.custom_thermo_heat_foreground), Integer.valueOf(R.color.control_custom_enabled_thermo_heat_background))), new Pair(49003, new Pair(Integer.valueOf(R.color.custom_thermo_cool_foreground), Integer.valueOf(R.color.control_custom_enabled_thermo_cool_background))), new Pair(13, new Pair(Integer.valueOf(R.color.custom_light_foreground), Integer.valueOf(R.color.control_custom_enabled_light_background))), new Pair(50, new Pair(Integer.valueOf(R.color.camera_foreground), Integer.valueOf(R.color.control_enabled_default_background)))), new Function1() { // from class: com.android.systemui.controls.ui.CustomRenderInfoKt$deviceCustomColorMap$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ((Number) obj).intValue();
            return new Pair(Integer.valueOf(R.color.control_custom_foreground), Integer.valueOf(R.color.control_custom_enabled_default_background));
        }
    });
    public static final Map defaultActionIconMap = MapsKt__MapWithDefaultKt.withDefault(MapsKt__MapsKt.emptyMap(), new Function1() { // from class: com.android.systemui.controls.ui.CustomRenderInfoKt$defaultActionIconMap$1
        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            ((Number) obj).intValue();
            return Integer.valueOf(R.drawable.ic_control_action_button_switch);
        }
    });
    public static final Map statusIconResourceMap = MapsKt__MapsKt.mapOf(new Pair(1, Integer.valueOf(R.drawable.ic_control_error)), new Pair(2, Integer.valueOf(R.drawable.ic_control_scene_badge_failed)));
}
