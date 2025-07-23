package androidx.compose.ui.graphics.colorspace;

import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.ui.graphics.colorspace.RenderIntent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ConnectorKt {
    public static final MutableIntObjectMap Connectors;

    static {
        ColorSpaces.INSTANCE.getClass();
        Rgb rgb = ColorSpaces.Srgb;
        int i = rgb.id;
        RenderIntent.Companion companion = RenderIntent.Companion;
        companion.getClass();
        Connector.Companion.getClass();
        companion.getClass();
        Connector$Companion$identity$1 connector$Companion$identity$1 = new Connector$Companion$identity$1(rgb, RenderIntent.Relative);
        Oklab oklab = ColorSpaces.Oklab;
        int i2 = oklab.id << 6;
        int i3 = rgb.id;
        int i4 = i2 | i3;
        int i5 = 0;
        DefaultConstructorMarker defaultConstructorMarker = null;
        Connector connector = new Connector(rgb, oklab, i5, defaultConstructorMarker);
        int i6 = (i3 << 6) | oklab.id;
        Connector connector2 = new Connector(oklab, rgb, i5, defaultConstructorMarker);
        MutableIntObjectMap mutableIntObjectMap = IntObjectMapKt.EmptyIntObjectMap;
        MutableIntObjectMap mutableIntObjectMap2 = new MutableIntObjectMap(0, 1, null);
        mutableIntObjectMap2.set(i | (i << 6), connector$Companion$identity$1);
        mutableIntObjectMap2.set(i4, connector);
        mutableIntObjectMap2.set(i6, connector2);
        Connectors = mutableIntObjectMap2;
    }
}
