package androidx.compose.ui.graphics.vector;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class VectorComponent extends VNode {
    public final DrawCache cacheDrawScope;
    public final Function1 drawVectorBlock;
    public final MutableState intrinsicColorFilter$delegate;
    public Lambda invalidateCallback;
    public boolean isDirty;
    public String name;
    public long previousDrawSize;
    public final GroupComponent root;
    public float rootScaleX;
    public float rootScaleY;
    public BlendModeColorFilter tintFilter;
    public final MutableState viewportSize$delegate;

    /* JADX WARN: Type inference failed for: r1v0, types: [androidx.compose.ui.graphics.vector.VectorComponent$1, kotlin.jvm.internal.Lambda] */
    public VectorComponent(GroupComponent groupComponent) {
        super(null);
        this.root = groupComponent;
        groupComponent.invalidateListener = new Function1() { // from class: androidx.compose.ui.graphics.vector.VectorComponent.1
            /* JADX WARN: Type inference failed for: r0v2, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                VectorComponent vectorComponent = VectorComponent.this;
                vectorComponent.isDirty = true;
                vectorComponent.invalidateCallback.invoke();
                return Unit.INSTANCE;
            }
        };
        this.name = "";
        this.isDirty = true;
        this.cacheDrawScope = new DrawCache();
        this.invalidateCallback = new Function0() { // from class: androidx.compose.ui.graphics.vector.VectorComponent$invalidateCallback$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        };
        this.intrinsicColorFilter$delegate = SnapshotStateKt.mutableStateOf$default(null);
        Size.Companion.getClass();
        this.viewportSize$delegate = SnapshotStateKt.mutableStateOf$default(Size.m413boximpl(0L));
        this.previousDrawSize = Size.Unspecified;
        this.rootScaleX = 1.0f;
        this.rootScaleY = 1.0f;
        this.drawVectorBlock = new VectorComponent$drawVectorBlock$1(this);
    }

    @Override // androidx.compose.ui.graphics.vector.VNode
    public final void draw(DrawScope drawScope) {
        draw(drawScope, 1.0f, null);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Params: \tname: ");
        sb.append(this.name);
        sb.append("\n\tviewportWidth: ");
        MutableState mutableState = this.viewportSize$delegate;
        sb.append(Float.intBitsToFloat((int) (((Size) ((SnapshotMutableStateImpl) mutableState).getValue()).packedValue >> 32)));
        sb.append("\n\tviewportHeight: ");
        sb.append(Float.intBitsToFloat((int) (((Size) ((SnapshotMutableStateImpl) mutableState).getValue()).packedValue & 4294967295L)));
        sb.append("\n");
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0181  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void draw(androidx.compose.ui.graphics.drawscope.DrawScope r39, float r40, androidx.compose.ui.graphics.ColorFilter r41) {
        /*
            Method dump skipped, instructions count: 445
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.VectorComponent.draw(androidx.compose.ui.graphics.drawscope.DrawScope, float, androidx.compose.ui.graphics.ColorFilter):void");
    }
}
