package androidx.compose.foundation;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.ULong;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BackgroundElement extends ModifierNodeElement<BackgroundNode> {
    public final float alpha;
    public final Brush brush;
    public final long color;
    public final Shape shape;

    public /* synthetic */ BackgroundElement(long j, Brush brush, float f, Shape shape, Function1 function1, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, brush, f, shape, function1);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final Modifier.Node create() {
        return new BackgroundNode(this.color, this.brush, this.alpha, this.shape, null);
    }

    public final boolean equals(Object obj) {
        BackgroundElement backgroundElement = obj instanceof BackgroundElement ? (BackgroundElement) obj : null;
        if (backgroundElement == null) {
            return false;
        }
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.color, backgroundElement.color) && Intrinsics.areEqual(this.brush, backgroundElement.brush) && this.alpha == backgroundElement.alpha && Intrinsics.areEqual(this.shape, backgroundElement.shape);
    }

    public final int hashCode() {
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int hashCode = Long.hashCode(this.color) * 31;
        Brush brush = this.brush;
        return this.shape.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.alpha, (hashCode + (brush != null ? brush.hashCode() : 0)) * 31, 31);
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public final void update(Modifier.Node node) {
        BackgroundNode backgroundNode = (BackgroundNode) node;
        backgroundNode.color = this.color;
        backgroundNode.brush = this.brush;
        backgroundNode.alpha = this.alpha;
        backgroundNode.shape = this.shape;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public BackgroundElement(long r9, androidx.compose.ui.graphics.Brush r11, float r12, androidx.compose.ui.graphics.Shape r13, kotlin.jvm.functions.Function1 r14, int r15, kotlin.jvm.internal.DefaultConstructorMarker r16) {
        /*
            r8 = this;
            r0 = r15 & 1
            if (r0 == 0) goto Lb
            androidx.compose.ui.graphics.Color$Companion r9 = androidx.compose.ui.graphics.Color.Companion
            r9.getClass()
            long r9 = androidx.compose.ui.graphics.Color.Unspecified
        Lb:
            r1 = r9
            r9 = r15 & 2
            if (r9 == 0) goto L11
            r11 = 0
        L11:
            r3 = r11
            r7 = 0
            r0 = r8
            r4 = r12
            r5 = r13
            r6 = r14
            r0.<init>(r1, r3, r4, r5, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.BackgroundElement.<init>(long, androidx.compose.ui.graphics.Brush, float, androidx.compose.ui.graphics.Shape, kotlin.jvm.functions.Function1, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private BackgroundElement(long j, Brush brush, float f, Shape shape, Function1 function1) {
        this.color = j;
        this.brush = brush;
        this.alpha = f;
        this.shape = shape;
    }
}
