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
            public final Object mo781invoke(Object obj) {
                VNode vNode = (VNode) obj;
                this.this$0.markTintForVNode(vNode);
                ?? r1 = this.this$0.invalidateListener;
                if (r1 != 0) {
                    r1.mo781invoke(vNode);
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
            float[] fArrM483constructorimpl$default = this.groupMatrix;
            if (fArrM483constructorimpl$default == null) {
                fArrM483constructorimpl$default = Matrix.m483constructorimpl$default();
                this.groupMatrix = fArrM483constructorimpl$default;
            } else {
                Matrix.m486resetimpl(fArrM483constructorimpl$default);
            }
            Matrix.m490translateimpl(this.translationX + this.pivotX, this.translationY + this.pivotY, fArrM483constructorimpl$default);
            Matrix.m487rotateZimpl(this.rotation, fArrM483constructorimpl$default);
            Matrix.m488scaleimpl(this.scaleX, this.scaleY, fArrM483constructorimpl$default);
            Matrix.m490translateimpl(-this.pivotX, -this.pivotY, fArrM483constructorimpl$default);
            this.isMatrixDirty = false;
        }
        if (this.isClipPathDirty) {
            if (!this.clipPathData.isEmpty()) {
                AndroidPath androidPathPath = this.clipPath;
                if (androidPathPath == null) {
                    androidPathPath = AndroidPath_androidKt.Path();
                    this.clipPath = androidPathPath;
                }
                PathParserKt.toPath(this.clipPathData, androidPathPath);
            }
            this.isClipPathDirty = false;
        }
        CanvasDrawScope$drawContext$1 drawContext = drawScope.getDrawContext();
        long jM528getSizeNHjbRc = drawContext.m528getSizeNHjbRc();
        drawContext.getCanvas().save();
        try {
            CanvasDrawScopeKt$asDrawTransform$1 canvasDrawScopeKt$asDrawTransform$1 = drawContext.transform;
            float[] fArr = this.groupMatrix;
            if (fArr != null) {
                ((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$1.$this_asDrawTransform).getCanvas().mo427concat58bKbWc(Matrix.m482boximpl(fArr).values);
            }
            AndroidPath androidPath = this.clipPath;
            if (!this.clipPathData.isEmpty() && androidPath != null) {
                ClipOp.Companion.getClass();
                ((CanvasDrawScope$drawContext$1) canvasDrawScopeKt$asDrawTransform$1.$this_asDrawTransform).getCanvas().mo425clipPathmtrdDE(androidPath, ClipOp.Intersect);
            }
            ArrayList arrayList = (ArrayList) this.children;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((VNode) arrayList.get(i)).draw(drawScope);
            }
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
        } catch (Throwable th) {
            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(drawContext, jM528getSizeNHjbRc);
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
    public final void m565markTintForColor8_81llA(long j) {
        if (this.isTintable && j != 16) {
            long j2 = this.tintColor;
            if (j2 == 16) {
                this.tintColor = j;
                return;
            }
            EmptyList emptyList = VectorKt.EmptyPath;
            if (Color.m463getRedimpl(j2) == Color.m463getRedimpl(j) && Color.m462getGreenimpl(j2) == Color.m462getGreenimpl(j) && Color.m460getBlueimpl(j2) == Color.m460getBlueimpl(j)) {
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
                    m565markTintForColor8_81llA(groupComponent.tintColor);
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
                m565markTintForColor8_81llA(((SolidColor) brush).value);
            } else {
                this.isTintable = false;
                Color.Companion.getClass();
                this.tintColor = Color.Unspecified;
            }
        }
        Brush brush2 = pathComponent.stroke;
        if (this.isTintable && brush2 != null) {
            if (brush2 instanceof SolidColor) {
                m565markTintForColor8_81llA(((SolidColor) brush2).value);
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
