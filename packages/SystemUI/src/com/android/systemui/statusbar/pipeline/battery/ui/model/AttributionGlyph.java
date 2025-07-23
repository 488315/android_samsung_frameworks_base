package com.android.systemui.statusbar.pipeline.battery.ui.model;

import com.android.systemui.statusbar.pipeline.battery.shared.ui.BatteryGlyph;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AttributionGlyph {
    public final BatteryGlyph inline;
    public final BatteryGlyph standalone;

    public AttributionGlyph(BatteryGlyph batteryGlyph, BatteryGlyph batteryGlyph2) {
        this.inline = batteryGlyph;
        this.standalone = batteryGlyph2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AttributionGlyph)) {
            return false;
        }
        AttributionGlyph attributionGlyph = (AttributionGlyph) obj;
        return Intrinsics.areEqual(this.inline, attributionGlyph.inline) && Intrinsics.areEqual(this.standalone, attributionGlyph.standalone);
    }

    public final int hashCode() {
        return this.standalone.hashCode() + (this.inline.hashCode() * 31);
    }

    public final String toString() {
        return "AttributionGlyph(inline=" + this.inline + ", standalone=" + this.standalone + ")";
    }
}
