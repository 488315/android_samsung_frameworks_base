package androidx.compose.ui.graphics.vector;

import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Matrix;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScopeKt$asDrawTransform$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class GroupComponent extends VNode {
    public final List children;
    public AndroidPath clipPath;
    public List clipPathData;
    public float[] groupMatrix;
    public Lambda invalidateListener;
    public boolean isClipPathDirty;
    public boolean isMatrixDirty;
    public boolean isTintable;
    public String name;
    public float pivotX;
    public float pivotY;
    public float rotation;
    public float scaleX;
    public float scaleY;
    public long tintColor;
    public float translationX;
    public float translationY;
    public final Function1 wrappedListener;

    public GroupComponent() {
        super(null);
        this.children = new ArrayList();
        this.isTintable = true;
        Color.Companion.getClass();
        this.tintColor = Color.Unspecified;
        this.clipPathData = VectorKt.EmptyPath;
        this.isClipPathDirty = true;
        this.wrappedListener = new Function1() { // from class: androidx.compose.ui.graphics.vector.GroupComponent$wrappedListener$1
            {
                super(1);
            }

            /* JADX WARN: Type inference failed for: r1v2, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                VNode vNode = (VNode) obj;
                GroupComponent.this.markTintForVNode(vNode);
                ?? r1 = GroupComponent.this.invalidateListener;
                if (r1 != 0) {
                    r1.mo779invoke(vNode);
                }
                return Unit.INSTANCE;
            }
        };
        this.name = "";
        this.scaleX = 1.0f;
        this.scaleY = 1.0f;
        this.isMatrixDirty = true;
    }

    @Override // androidx.compose.ui.graphics.vector.VNode
    public final void draw(DrawScope drawScope) {
        if (this.isMatrixDirty) {
            float[] fArr = this.groupMatrix;
            if (fArr == null) {
                fArr = Matrix.m481constructorimpl$default();
                this.groupMatrix = fArr;
            } else {
                Matrix.m484resetimpl(fArr);
            }
            Matrix.m488translateimpl(this.translationX + this.pivotX, this.translationY + this.pivotY, fArr);
            Matrix.m485rotateZimpl(this.rotation, fArr);
            Matrix.m486scaleimpl(this.scaleX, this.scaleY, fArr);
            Matrix.m488translateimpl(-this.pivotX, -this.pivotY, fArr);
            this.isMatrixDirty = false;
        }
        if (this.isClipPathDirty) {
            if (!this.clipPathData.isEmpty()) {
                AndroidPath androidPath = this.clipPath;
                if (androidPath == null) {
                    androidPath = AndroidPath_androidKt.Path();
                    this.clipPath = androidPath;
                }
                PathParserKt.toPath(this.clipPathData, androidPath);
            }
            this.isClipPathDirty = false;
        }
        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
        long m526getSizeNHjbRc = drawContext.m526getSizeNHjbRc();
        drawContext.getCanvas().save();
        try {
            CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = drawContext.transform;
            float[] fArr2 = this.groupMatrix;
            if (fArr2 != null) {
                ((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$1.$this_asDrawTransform).getCanvas().mo425concat58bKbWc(Matrix.m480boximpl(fArr2).values);
            }
            AndroidPath androidPath2 = this.clipPath;
            if (!this.clipPathData.isEmpty() && androidPath2 != null) {
                ClipOp.Companion.getClass();
                ((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$1.$this_asDrawTransform).getCanvas().mo423clipPathmtrdDE(androidPath2, ClipOp.Intersect);
            }
            ArrayList arrayList = (ArrayList) this.children;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((VNode) arrayList.get(i)).draw(drawScope);
            }
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
        } catch (Throwable th) {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, m526getSizeNHjbRc);
            throw th;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    @Override // androidx.compose.ui.graphics.vector.VNode
    public final Function1 getInvalidateListener$ui_release() {
        return this.invalidateListener;
    }

    public final void insertAt(int i, VNode vNode) {
        if (i < ((ArrayList) this.children).size()) {
            ((ArrayList) this.children).set(i, vNode);
        } else {
            ((ArrayList) this.children).add(vNode);
        }
        markTintForVNode(vNode);
        vNode.setInvalidateListener$ui_release(this.wrappedListener);
        invalidate();
    }

    /* renamed from: markTintForColor-8_81llA, reason: not valid java name */
    public final void m563markTintForColor8_81llA(long j) {
        if (this.isTintable && j != 16) {
            long j2 = this.tintColor;
            if (j2 == 16) {
                this.tintColor = j;
                return;
            }
            EmptyList emptyList = VectorKt.EmptyPath;
            if (Color.m461getRedimpl(j2) == Color.m461getRedimpl(j) && Color.m460getGreenimpl(j2) == Color.m460getGreenimpl(j) && Color.m458getBlueimpl(j2) == Color.m458getBlueimpl(j)) {
                return;
            }
            this.isTintable = false;
            Color.Companion.getClass();
            this.tintColor = Color.Unspecified;
        }
    }

    public final void markTintForVNode(VNode vNode) {
        if (!(vNode instanceof PathComponent)) {
            if (vNode instanceof GroupComponent) {
                GroupComponent groupComponent = (GroupComponent) vNode;
                if (groupComponent.isTintable && this.isTintable) {
                    m563markTintForColor8_81llA(groupComponent.tintColor);
                    return;
                }
                this.isTintable = false;
                Color.Companion.getClass();
                this.tintColor = Color.Unspecified;
                return;
            }
            return;
        }
        PathComponent pathComponent = (PathComponent) vNode;
        Brush brush = pathComponent.fill;
        if (this.isTintable && brush != null) {
            if (brush instanceof SolidColor) {
                m563markTintForColor8_81llA(((SolidColor) brush).value);
            } else {
                this.isTintable = false;
                Color.Companion.getClass();
                this.tintColor = Color.Unspecified;
            }
        }
        Brush brush2 = pathComponent.stroke;
        if (this.isTintable && brush2 != null) {
            if (brush2 instanceof SolidColor) {
                m563markTintForColor8_81llA(((SolidColor) brush2).value);
                return;
            }
            this.isTintable = false;
            Color.Companion.getClass();
            this.tintColor = Color.Unspecified;
        }
    }

    public final void remove(int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (i < ((ArrayList) this.children).size()) {
                ((VNode) ((ArrayList) this.children).get(i)).setInvalidateListener$ui_release(null);
                ((ArrayList) this.children).remove(i);
            }
        }
        invalidate();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.graphics.vector.VNode
    public final void setInvalidateListener$ui_release(Function1 function1) {
        this.invalidateListener = (Lambda) function1;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("VGroup: ");
        sb.append(this.name);
        List list = this.children;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            VNode vNode = (VNode) ((ArrayList) list).get(i);
            sb.append("\t");
            sb.append(vNode.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
