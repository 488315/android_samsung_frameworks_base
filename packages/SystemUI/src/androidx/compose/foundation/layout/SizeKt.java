package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.WrapContentElement;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class SizeKt {
    public static final FillElement FillWholeMaxHeight;
    public static final FillElement FillWholeMaxSize;
    public static final FillElement FillWholeMaxWidth;
    public static final WrapContentElement WrapContentHeightCenter;
    public static final WrapContentElement WrapContentHeightTop;
    public static final WrapContentElement WrapContentSizeCenter;
    public static final WrapContentElement WrapContentSizeTopStart;
    public static final WrapContentElement WrapContentWidthCenter;
    public static final WrapContentElement WrapContentWidthStart;

    static {
        FillElement.Companion.getClass();
        Direction direction = Direction.Horizontal;
        FillWholeMaxWidth = new FillElement(direction, 1.0f, "fillMaxWidth");
        Direction direction2 = Direction.Vertical;
        FillWholeMaxHeight = new FillElement(direction2, 1.0f, "fillMaxHeight");
        Direction direction3 = Direction.Both;
        FillWholeMaxSize = new FillElement(direction3, 1.0f, "fillMaxSize");
        WrapContentElement.Companion companion = WrapContentElement.Companion;
        Alignment.Companion.getClass();
        BiasAlignment.Horizontal horizontal = Alignment.Companion.CenterHorizontally;
        companion.getClass();
        WrapContentWidthCenter = new WrapContentElement(direction, false, new WrapContentElement$Companion$width$1(horizontal), horizontal, "wrapContentWidth");
        BiasAlignment.Horizontal horizontal2 = Alignment.Companion.Start;
        WrapContentWidthStart = new WrapContentElement(direction, false, new WrapContentElement$Companion$width$1(horizontal2), horizontal2, "wrapContentWidth");
        BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
        WrapContentHeightCenter = new WrapContentElement(direction2, false, new WrapContentElement$Companion$height$1(vertical), vertical, "wrapContentHeight");
        BiasAlignment.Vertical vertical2 = Alignment.Companion.Top;
        WrapContentHeightTop = new WrapContentElement(direction2, false, new WrapContentElement$Companion$height$1(vertical2), vertical2, "wrapContentHeight");
        BiasAlignment biasAlignment = Alignment.Companion.Center;
        WrapContentSizeCenter = new WrapContentElement(direction3, false, new WrapContentElement$Companion$size$1(biasAlignment), biasAlignment, "wrapContentSize");
        BiasAlignment biasAlignment2 = Alignment.Companion.TopStart;
        WrapContentSizeTopStart = new WrapContentElement(direction3, false, new WrapContentElement$Companion$size$1(biasAlignment2), biasAlignment2, "wrapContentSize");
    }

    /* renamed from: defaultMinSize-VpY3zN4, reason: not valid java name */
    public static final Modifier m130defaultMinSizeVpY3zN4(Modifier modifier, float f, float f2) {
        return modifier.then(new UnspecifiedConstraintsElement(f, f2, null));
    }

    public static final Modifier fillMaxSize(Modifier modifier, float f) {
        FillElement fillElement;
        if (f == 1.0f) {
            fillElement = FillWholeMaxSize;
        } else {
            FillElement.Companion.getClass();
            fillElement = new FillElement(Direction.Both, f, "fillMaxSize");
        }
        return modifier.then(fillElement);
    }

    public static final Modifier fillMaxWidth(Modifier modifier, float f) {
        FillElement fillElement;
        if (f == 1.0f) {
            fillElement = FillWholeMaxWidth;
        } else {
            FillElement.Companion.getClass();
            fillElement = new FillElement(Direction.Horizontal, f, "fillMaxWidth");
        }
        return modifier.then(fillElement);
    }

    /* renamed from: height-3ABfNKs, reason: not valid java name */
    public static final Modifier m131height3ABfNKs(Modifier modifier, float f) {
        return modifier.then(new SizeElement(0.0f, f, 0.0f, f, true, InspectableValueKt.NoInspectorInfo, 5, null));
    }

    /* renamed from: heightIn-VpY3zN4, reason: not valid java name */
    public static final Modifier m132heightInVpY3zN4(Modifier modifier, float f, float f2) {
        return modifier.then(new SizeElement(0.0f, f, 0.0f, f2, true, InspectableValueKt.NoInspectorInfo, 5, null));
    }

    /* renamed from: heightIn-VpY3zN4$default, reason: not valid java name */
    public static Modifier m133heightInVpY3zN4$default(Modifier modifier, float f, float f2, int i) {
        if ((i & 1) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        return m132heightInVpY3zN4(modifier, f, f2);
    }

    /* renamed from: requiredHeight-3ABfNKs, reason: not valid java name */
    public static final Modifier m134requiredHeight3ABfNKs(Modifier modifier, float f) {
        return modifier.then(new SizeElement(0.0f, f, 0.0f, f, false, InspectableValueKt.NoInspectorInfo, 5, null));
    }

    /* renamed from: requiredHeightIn-VpY3zN4$default, reason: not valid java name */
    public static Modifier m135requiredHeightInVpY3zN4$default(Modifier modifier, float f, float f2, int i) {
        if ((i & 1) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        float f3 = f;
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        return modifier.then(new SizeElement(0.0f, f3, 0.0f, f2, false, InspectableValueKt.NoInspectorInfo, 5, null));
    }

    /* renamed from: requiredSize-3ABfNKs, reason: not valid java name */
    public static final Modifier m136requiredSize3ABfNKs(Modifier modifier, float f) {
        return modifier.then(new SizeElement(f, f, f, f, false, InspectableValueKt.NoInspectorInfo, null));
    }

    /* renamed from: requiredSize-VpY3zN4, reason: not valid java name */
    public static final Modifier m137requiredSizeVpY3zN4(Modifier modifier, float f, float f2) {
        return modifier.then(new SizeElement(f, f2, f, f2, false, InspectableValueKt.NoInspectorInfo, null));
    }

    /* renamed from: requiredSizeIn-qDBjuR0$default, reason: not valid java name */
    public static Modifier m138requiredSizeInqDBjuR0$default(Modifier modifier, float f, float f2, float f3, float f4, int i) {
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        float f5 = f2;
        if ((i & 4) != 0) {
            Dp.Companion.getClass();
            f3 = Dp.Unspecified;
        }
        float f6 = f3;
        if ((i & 8) != 0) {
            Dp.Companion.getClass();
            f4 = Dp.Unspecified;
        }
        return modifier.then(new SizeElement(f, f5, f6, f4, false, InspectableValueKt.NoInspectorInfo, null));
    }

    /* renamed from: requiredWidth-3ABfNKs, reason: not valid java name */
    public static final Modifier m139requiredWidth3ABfNKs(Modifier modifier, float f) {
        return modifier.then(new SizeElement(f, 0.0f, f, 0.0f, false, InspectableValueKt.NoInspectorInfo, 10, null));
    }

    /* renamed from: size-3ABfNKs, reason: not valid java name */
    public static final Modifier m140size3ABfNKs(Modifier modifier, float f) {
        return modifier.then(new SizeElement(f, f, f, f, true, InspectableValueKt.NoInspectorInfo, null));
    }

    /* renamed from: size-VpY3zN4, reason: not valid java name */
    public static final Modifier m141sizeVpY3zN4(Modifier modifier, float f, float f2) {
        return modifier.then(new SizeElement(f, f2, f, f2, true, InspectableValueKt.NoInspectorInfo, null));
    }

    /* renamed from: sizeIn-qDBjuR0, reason: not valid java name */
    public static final Modifier m142sizeInqDBjuR0(Modifier modifier, float f, float f2, float f3, float f4) {
        return modifier.then(new SizeElement(f, f2, f3, f4, true, InspectableValueKt.NoInspectorInfo, null));
    }

    /* renamed from: sizeIn-qDBjuR0$default, reason: not valid java name */
    public static Modifier m143sizeInqDBjuR0$default(Modifier modifier, float f, float f2, float f3, float f4, int i) {
        if ((i & 1) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        if ((i & 4) != 0) {
            Dp.Companion.getClass();
            f3 = Dp.Unspecified;
        }
        if ((i & 8) != 0) {
            Dp.Companion.getClass();
            f4 = Dp.Unspecified;
        }
        return m142sizeInqDBjuR0(modifier, f, f2, f3, f4);
    }

    /* renamed from: width-3ABfNKs, reason: not valid java name */
    public static final Modifier m144width3ABfNKs(Modifier modifier, float f) {
        return modifier.then(new SizeElement(f, 0.0f, f, 0.0f, true, InspectableValueKt.NoInspectorInfo, 10, null));
    }

    /* renamed from: widthIn-VpY3zN4, reason: not valid java name */
    public static final Modifier m145widthInVpY3zN4(Modifier modifier, float f, float f2) {
        return modifier.then(new SizeElement(f, 0.0f, f2, 0.0f, true, InspectableValueKt.NoInspectorInfo, 10, null));
    }

    /* renamed from: widthIn-VpY3zN4$default, reason: not valid java name */
    public static Modifier m146widthInVpY3zN4$default(Modifier modifier, float f, float f2, int i) {
        if ((i & 1) != 0) {
            Dp.Companion.getClass();
            f = Dp.Unspecified;
        }
        if ((i & 2) != 0) {
            Dp.Companion.getClass();
            f2 = Dp.Unspecified;
        }
        return m145widthInVpY3zN4(modifier, f, f2);
    }

    public static Modifier wrapContentHeight$default(Modifier modifier, int i) {
        WrapContentElement wrapContentElement;
        Alignment.Companion companion = Alignment.Companion;
        companion.getClass();
        BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
        boolean z = (i & 2) == 0;
        companion.getClass();
        if (Intrinsics.areEqual(vertical, vertical) && !z) {
            wrapContentElement = WrapContentHeightCenter;
        } else if (!Intrinsics.areEqual(vertical, Alignment.Companion.Top) || z) {
            WrapContentElement.Companion.getClass();
            wrapContentElement = new WrapContentElement(Direction.Vertical, z, new WrapContentElement$Companion$height$1(vertical), vertical, "wrapContentHeight");
        } else {
            wrapContentElement = WrapContentHeightTop;
        }
        return modifier.then(wrapContentElement);
    }

    public static Modifier wrapContentSize$default(Modifier modifier, BiasAlignment biasAlignment, int i) {
        WrapContentElement wrapContentElement;
        if ((i & 1) != 0) {
            Alignment.Companion.getClass();
            biasAlignment = Alignment.Companion.Center;
        }
        BiasAlignment biasAlignment2 = biasAlignment;
        boolean z = (i & 2) == 0;
        Alignment.Companion.getClass();
        if (Intrinsics.areEqual(biasAlignment2, Alignment.Companion.Center) && !z) {
            wrapContentElement = WrapContentSizeCenter;
        } else if (!Intrinsics.areEqual(biasAlignment2, Alignment.Companion.TopStart) || z) {
            WrapContentElement.Companion.getClass();
            wrapContentElement = new WrapContentElement(Direction.Both, z, new WrapContentElement$Companion$size$1(biasAlignment2), biasAlignment2, "wrapContentSize");
        } else {
            wrapContentElement = WrapContentSizeTopStart;
        }
        return modifier.then(wrapContentElement);
    }

    public static Modifier wrapContentWidth$default(Modifier modifier, BiasAlignment.Horizontal horizontal, int i) {
        WrapContentElement wrapContentElement;
        if ((i & 1) != 0) {
            Alignment.Companion.getClass();
            horizontal = Alignment.Companion.CenterHorizontally;
        }
        BiasAlignment.Horizontal horizontal2 = horizontal;
        boolean z = (i & 2) == 0;
        Alignment.Companion.getClass();
        if (Intrinsics.areEqual(horizontal2, Alignment.Companion.CenterHorizontally) && !z) {
            wrapContentElement = WrapContentWidthCenter;
        } else if (!Intrinsics.areEqual(horizontal2, Alignment.Companion.Start) || z) {
            WrapContentElement.Companion.getClass();
            wrapContentElement = new WrapContentElement(Direction.Horizontal, z, new WrapContentElement$Companion$width$1(horizontal2), horizontal2, "wrapContentWidth");
        } else {
            wrapContentElement = WrapContentWidthStart;
        }
        return modifier.then(wrapContentElement);
    }
}
