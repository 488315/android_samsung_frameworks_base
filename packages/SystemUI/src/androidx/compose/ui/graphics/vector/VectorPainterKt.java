package androidx.compose.ui.graphics.vector;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ColorFilter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class VectorPainterKt {
    /* JADX WARN: Removed duplicated region for block: B:10:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void RenderVectorGroup(final androidx.compose.ui.graphics.vector.VectorGroup r23, java.util.Map r24, androidx.compose.runtime.Composer r25, final int r26, final int r27) {
        /*
            Method dump skipped, instructions count: 617
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.VectorPainterKt.RenderVectorGroup(androidx.compose.ui.graphics.vector.VectorGroup, java.util.Map, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: configureVectorPainter-T4PVSW8, reason: not valid java name */
    public static final void m567configureVectorPainterT4PVSW8(VectorPainter vectorPainter, long j, long j2, String str, ColorFilter colorFilter, boolean z) {
        ((SnapshotMutableStateImpl) vectorPainter.size$delegate).setValue(Size.m413boximpl(j));
        ((SnapshotMutableStateImpl) vectorPainter.autoMirror$delegate).setValue(Boolean.valueOf(z));
        VectorComponent vectorComponent = vectorPainter.vector;
        ((SnapshotMutableStateImpl) vectorComponent.intrinsicColorFilter$delegate).setValue(colorFilter);
        ((SnapshotMutableStateImpl) vectorComponent.viewportSize$delegate).setValue(Size.m413boximpl(j2));
        vectorComponent.name = str;
    }

    public static final void createGroupComponent(GroupComponent groupComponent, VectorGroup vectorGroup) {
        int size = vectorGroup.children.size();
        for (int i = 0; i < size; i++) {
            VectorNode vectorNode = (VectorNode) vectorGroup.children.get(i);
            if (vectorNode instanceof VectorPath) {
                PathComponent pathComponent = new PathComponent();
                VectorPath vectorPath = (VectorPath) vectorNode;
                pathComponent.pathData = vectorPath.pathData;
                pathComponent.isPathDirty = true;
                pathComponent.invalidate();
                pathComponent.renderPath.m444setFillTypeoQ8Xj4U(vectorPath.pathFillType);
                pathComponent.invalidate();
                pathComponent.invalidate();
                pathComponent.fill = vectorPath.fill;
                pathComponent.invalidate();
                pathComponent.fillAlpha = vectorPath.fillAlpha;
                pathComponent.invalidate();
                pathComponent.stroke = vectorPath.stroke;
                pathComponent.invalidate();
                pathComponent.strokeAlpha = vectorPath.strokeAlpha;
                pathComponent.invalidate();
                pathComponent.strokeLineWidth = vectorPath.strokeLineWidth;
                pathComponent.isStrokeDirty = true;
                pathComponent.invalidate();
                pathComponent.strokeLineCap = vectorPath.strokeLineCap;
                pathComponent.isStrokeDirty = true;
                pathComponent.invalidate();
                pathComponent.strokeLineJoin = vectorPath.strokeLineJoin;
                pathComponent.isStrokeDirty = true;
                pathComponent.invalidate();
                pathComponent.strokeLineMiter = vectorPath.strokeLineMiter;
                pathComponent.isStrokeDirty = true;
                pathComponent.invalidate();
                pathComponent.trimPathStart = vectorPath.trimPathStart;
                pathComponent.isTrimPathDirty = true;
                pathComponent.invalidate();
                pathComponent.trimPathEnd = vectorPath.trimPathEnd;
                pathComponent.isTrimPathDirty = true;
                pathComponent.invalidate();
                pathComponent.trimPathOffset = vectorPath.trimPathOffset;
                pathComponent.isTrimPathDirty = true;
                pathComponent.invalidate();
                groupComponent.insertAt(i, pathComponent);
            } else if (vectorNode instanceof VectorGroup) {
                GroupComponent groupComponent2 = new GroupComponent();
                VectorGroup vectorGroup2 = (VectorGroup) vectorNode;
                groupComponent2.name = vectorGroup2.name;
                groupComponent2.invalidate();
                groupComponent2.rotation = vectorGroup2.rotation;
                groupComponent2.isMatrixDirty = true;
                groupComponent2.invalidate();
                groupComponent2.scaleX = vectorGroup2.scaleX;
                groupComponent2.isMatrixDirty = true;
                groupComponent2.invalidate();
                groupComponent2.scaleY = vectorGroup2.scaleY;
                groupComponent2.isMatrixDirty = true;
                groupComponent2.invalidate();
                groupComponent2.translationX = vectorGroup2.translationX;
                groupComponent2.isMatrixDirty = true;
                groupComponent2.invalidate();
                groupComponent2.translationY = vectorGroup2.translationY;
                groupComponent2.isMatrixDirty = true;
                groupComponent2.invalidate();
                groupComponent2.pivotX = vectorGroup2.pivotX;
                groupComponent2.isMatrixDirty = true;
                groupComponent2.invalidate();
                groupComponent2.pivotY = vectorGroup2.pivotY;
                groupComponent2.isMatrixDirty = true;
                groupComponent2.invalidate();
                groupComponent2.clipPathData = vectorGroup2.clipPathData;
                groupComponent2.isClipPathDirty = true;
                groupComponent2.invalidate();
                createGroupComponent(groupComponent2, vectorGroup2);
                groupComponent.insertAt(i, groupComponent2);
            }
        }
    }

    /* renamed from: obtainViewportSize-Pq9zytI, reason: not valid java name */
    public static final long m568obtainViewportSizePq9zytI(float f, float f2, long j) {
        if (Float.isNaN(f)) {
            f = Float.intBitsToFloat((int) (j >> 32));
        }
        if (Float.isNaN(f2)) {
            f2 = Float.intBitsToFloat((int) (j & 4294967295L));
        }
        long floatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Size.Companion companion = Size.Companion;
        return floatToRawIntBits;
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0041, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.graphics.vector.VectorPainter rememberVectorPainter(androidx.compose.ui.graphics.vector.ImageVector r14, androidx.compose.runtime.Composer r15) {
        /*
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto Lb
            java.lang.String r0 = "androidx.compose.ui.graphics.vector.rememberVectorPainter (VectorPainter.kt:169)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        Lb:
            androidx.compose.runtime.StaticProvidableCompositionLocal r0 = androidx.compose.ui.platform.CompositionLocalsKt.LocalDensity
            androidx.compose.runtime.ComposerImpl r15 = (androidx.compose.runtime.ComposerImpl) r15
            java.lang.Object r0 = r15.consume(r0)
            androidx.compose.ui.unit.Density r0 = (androidx.compose.ui.unit.Density) r0
            int r1 = r14.genId
            float r1 = (float) r1
            float r2 = r0.getDensity()
            int r1 = java.lang.Float.floatToRawIntBits(r1)
            long r3 = (long) r1
            int r1 = java.lang.Float.floatToRawIntBits(r2)
            long r1 = (long) r1
            r5 = 32
            long r3 = r3 << r5
            r6 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r1 = r1 & r6
            long r1 = r1 | r3
            boolean r1 = r15.changed(r1)
            java.lang.Object r2 = r15.rememberedValue()
            if (r1 != 0) goto L43
            androidx.compose.runtime.Composer$Companion r1 = androidx.compose.runtime.Composer.Companion
            r1.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r1 = androidx.compose.runtime.Composer.Companion.Empty
            if (r2 != r1) goto L9c
        L43:
            androidx.compose.ui.graphics.vector.GroupComponent r1 = new androidx.compose.ui.graphics.vector.GroupComponent
            r1.<init>()
            androidx.compose.ui.graphics.vector.VectorGroup r2 = r14.root
            createGroupComponent(r1, r2)
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            float r2 = r14.defaultWidth
            float r2 = r0.mo57toPx0680j_4(r2)
            float r3 = r14.defaultHeight
            float r0 = r0.mo57toPx0680j_4(r3)
            int r2 = java.lang.Float.floatToRawIntBits(r2)
            long r2 = (long) r2
            int r0 = java.lang.Float.floatToRawIntBits(r0)
            long r8 = (long) r0
            long r2 = r2 << r5
            long r4 = r8 & r6
            long r7 = r2 | r4
            androidx.compose.ui.geometry.Size$Companion r0 = androidx.compose.ui.geometry.Size.Companion
            float r0 = r14.viewportWidth
            float r2 = r14.viewportHeight
            long r9 = m568obtainViewportSizePq9zytI(r0, r2, r7)
            androidx.compose.ui.graphics.vector.VectorPainter r6 = new androidx.compose.ui.graphics.vector.VectorPainter
            r6.<init>(r1)
            r0 = 16
            long r2 = r14.tintColor
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            r1 = 0
            if (r0 == 0) goto L90
            androidx.compose.ui.graphics.ColorFilter$Companion r0 = androidx.compose.ui.graphics.ColorFilter.Companion
            r0.getClass()
            androidx.compose.ui.graphics.BlendModeColorFilter r0 = new androidx.compose.ui.graphics.BlendModeColorFilter
            int r4 = r14.tintBlendMode
            r0.<init>(r2, r4, r1)
            r12 = r0
            goto L91
        L90:
            r12 = r1
        L91:
            java.lang.String r11 = r14.name
            boolean r13 = r14.autoMirror
            m567configureVectorPainterT4PVSW8(r6, r7, r9, r11, r12, r13)
            r15.updateRememberedValue(r6)
            r2 = r6
        L9c:
            androidx.compose.ui.graphics.vector.VectorPainter r2 = (androidx.compose.ui.graphics.vector.VectorPainter) r2
            boolean r14 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r14 == 0) goto La7
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        La7:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.VectorPainterKt.rememberVectorPainter(androidx.compose.ui.graphics.vector.ImageVector, androidx.compose.runtime.Composer):androidx.compose.ui.graphics.vector.VectorPainter");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x005f, code lost:
    
        if (r9 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    /* renamed from: rememberVectorPainter-vIP8VLU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.graphics.vector.VectorPainter m569rememberVectorPaintervIP8VLU(float r19, float r20, float r21, float r22, java.lang.String r23, long r24, int r26, final androidx.compose.runtime.internal.ComposableLambdaImpl r27, androidx.compose.runtime.Composer r28) {
        /*
            Method dump skipped, instructions count: 283
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.graphics.vector.VectorPainterKt.m569rememberVectorPaintervIP8VLU(float, float, float, float, java.lang.String, long, int, androidx.compose.runtime.internal.ComposableLambdaImpl, androidx.compose.runtime.Composer):androidx.compose.ui.graphics.vector.VectorPainter");
    }
}
