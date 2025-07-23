package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.drawscope.DrawScope;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class VNode {
    public Function1 invalidateListener;

    public /* synthetic */ VNode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract void draw(DrawScope drawScope);

    public Function1 getInvalidateListener$ui_release() {
        return this.invalidateListener;
    }

    public final void invalidate() {
        Function1 invalidateListener$ui_release = getInvalidateListener$ui_release();
        if (invalidateListener$ui_release != null) {
            invalidateListener$ui_release.mo779invoke(this);
        }
    }

    public void setInvalidateListener$ui_release(Function1 function1) {
        this.invalidateListener = function1;
    }

    private VNode() {
    }
}
