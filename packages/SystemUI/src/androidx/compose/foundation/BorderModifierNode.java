package androidx.compose.foundation;

import androidx.compose.ui.draw.CacheDrawModifierNode;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.node.DelegatingNode;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BorderModifierNode extends DelegatingNode {
    public BorderCache borderCache;
    public Brush brush;
    public final CacheDrawModifierNode drawWithCacheModifierNode;
    public Shape shape;
    public float width;

    public /* synthetic */ BorderModifierNode(float f, Brush brush, Shape shape, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, brush, shape);
    }

    private BorderModifierNode(float f, Brush brush, Shape shape) {
        this.width = f;
        this.brush = brush;
        this.shape = shape;
        CacheDrawModifierNode CacheDrawModifierNode = DrawModifierKt.CacheDrawModifierNode(new Function1() { // from class: androidx.compose.foundation.BorderModifierNode$drawWithCacheModifierNode$1
            {
                super(1);
            }

            /* JADX WARN: Can't wrap try/catch for region: R(4:(3:(7:(2:(1:88)(1:93)|(25:92|(1:86)(2:37|(24:39|(22:42|43|44|(1:46)|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64)|84|43|44|(0)|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64))|85|84|43|44|(0)|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64))|59|60|61|62|63|64)|57|58)|54|55|56) */
            /* JADX WARN: Code restructure failed: missing block: B:77:0x033a, code lost:
            
                r0 = th;
             */
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:46:0x0214  */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object mo779invoke(java.lang.Object r46) {
                /*
                    Method dump skipped, instructions count: 1118
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.BorderModifierNode$drawWithCacheModifierNode$1.mo779invoke(java.lang.Object):java.lang.Object");
            }
        });
        delegate(CacheDrawModifierNode);
        this.drawWithCacheModifierNode = CacheDrawModifierNode;
    }
}
