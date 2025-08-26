package com.android.systemui.shared.clocks;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class LayerConfig {
    public final DigitalAlignment alignment;
    public final FontTextStyle aodStyle;
    public final String dateTimeFormat;
    public final FontTextStyle style;
    public final DigitalTimespec timespec;

    public LayerConfig(FontTextStyle fontTextStyle, FontTextStyle fontTextStyle2, DigitalAlignment digitalAlignment, DigitalTimespec digitalTimespec, String str) {
        this.style = fontTextStyle;
        this.aodStyle = fontTextStyle2;
        this.alignment = digitalAlignment;
        this.timespec = digitalTimespec;
        this.dateTimeFormat = str;
    }

    public static LayerConfig copy$default(LayerConfig layerConfig, DigitalTimespec digitalTimespec, String str) {
        return new LayerConfig(layerConfig.style, layerConfig.aodStyle, layerConfig.alignment, digitalTimespec, str);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LayerConfig)) {
            return false;
        }
        LayerConfig layerConfig = (LayerConfig) obj;
        return Intrinsics.areEqual(this.style, layerConfig.style) && Intrinsics.areEqual(this.aodStyle, layerConfig.aodStyle) && Intrinsics.areEqual(this.alignment, layerConfig.alignment) && this.timespec == layerConfig.timespec && Intrinsics.areEqual(this.dateTimeFormat, layerConfig.dateTimeFormat);
    }

    public final int hashCode() {
        return this.dateTimeFormat.hashCode() + ((this.timespec.hashCode() + ((this.alignment.hashCode() + ((this.aodStyle.hashCode() + (this.style.hashCode() * 31)) * 31)) * 31)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LayerConfig(style=");
        sb.append(this.style);
        sb.append(", aodStyle=");
        sb.append(this.aodStyle);
        sb.append(", alignment=");
        sb.append(this.alignment);
        sb.append(", timespec=");
        sb.append(this.timespec);
        sb.append(", dateTimeFormat=");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.dateTimeFormat, ")");
    }
}
