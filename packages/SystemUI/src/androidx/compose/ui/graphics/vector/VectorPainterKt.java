package androidx.compose.ui.graphics.vector;

import androidx.compose.foundation.text.BasicTextKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.Composition;
import androidx.compose.runtime.CompositionImpl;
import androidx.compose.runtime.CompositionKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.vector.VectorGroup;
import androidx.compose.ui.graphics.vector.VectorProperty;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class VectorPainterKt {
    /* JADX WARN: Removed duplicated region for block: B:26:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void RenderVectorGroup(final VectorGroup vectorGroup, Map map, Composer composer, final int i, final int i2) {
        int i3;
        final Map map2;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup;
        Map map3;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-446179233);
        if ((i2 & 1) != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerImpl.changed(vectorGroup) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        int i4 = i2 & 2;
        if (i4 == 0) {
            if ((i & 48) == 0) {
                map2 = map;
                i3 |= composerImpl.changedInstance(map2) ? 32 : 16;
            }
            if (composerImpl.shouldExecute(i3 & 1, (i3 & 19) == 18)) {
                composerImpl.skipToGroupEnd();
            } else {
                Map mapEmptyMap = i4 != 0 ? MapsKt__MapsKt.emptyMap() : map2;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.ui.graphics.vector.RenderVectorGroup (VectorPainter.kt:428)");
                }
                vectorGroup.getClass();
                VectorGroup.AnonymousClass1 anonymousClass1 = new VectorGroup.AnonymousClass1(vectorGroup);
                while (anonymousClass1.it.hasNext()) {
                    final VectorNode vectorNode = (VectorNode) anonymousClass1.next();
                    if (vectorNode instanceof VectorPath) {
                        composerImpl.startReplaceGroup(-23302778);
                        VectorPath vectorPath = (VectorPath) vectorNode;
                        VectorConfig vectorConfig = (VectorConfig) mapEmptyMap.get(vectorPath.name);
                        if (vectorConfig == null) {
                            vectorConfig = new VectorConfig() { // from class: androidx.compose.ui.graphics.vector.VectorPainterKt$RenderVectorGroup$config$1
                            };
                        }
                        ComposerImpl composerImpl2 = composerImpl;
                        VectorComposeKt.m568Path9cdaXJ4((List) vectorConfig.getOrDefault(VectorProperty.PathData.INSTANCE, vectorPath.pathData), vectorPath.pathFillType, vectorPath.name, (Brush) vectorConfig.getOrDefault(VectorProperty.Fill.INSTANCE, vectorPath.fill), ((Number) vectorConfig.getOrDefault(VectorProperty.FillAlpha.INSTANCE, Float.valueOf(vectorPath.fillAlpha))).floatValue(), (Brush) vectorConfig.getOrDefault(VectorProperty.Stroke.INSTANCE, vectorPath.stroke), ((Number) vectorConfig.getOrDefault(VectorProperty.StrokeAlpha.INSTANCE, Float.valueOf(vectorPath.strokeAlpha))).floatValue(), ((Number) vectorConfig.getOrDefault(VectorProperty.StrokeLineWidth.INSTANCE, Float.valueOf(vectorPath.strokeLineWidth))).floatValue(), vectorPath.strokeLineCap, vectorPath.strokeLineJoin, vectorPath.strokeLineMiter, ((Number) vectorConfig.getOrDefault(VectorProperty.TrimPathStart.INSTANCE, Float.valueOf(vectorPath.trimPathStart))).floatValue(), ((Number) vectorConfig.getOrDefault(VectorProperty.TrimPathEnd.INSTANCE, Float.valueOf(vectorPath.trimPathEnd))).floatValue(), ((Number) vectorConfig.getOrDefault(VectorProperty.TrimPathOffset.INSTANCE, Float.valueOf(vectorPath.trimPathOffset))).floatValue(), composerImpl2, 0, 0, 0);
                        composerImpl = composerImpl2;
                        composerImpl.end(false);
                        anonymousClass1 = anonymousClass1;
                        mapEmptyMap = mapEmptyMap;
                    } else {
                        final Map map4 = mapEmptyMap;
                        VectorGroup.AnonymousClass1 anonymousClass12 = anonymousClass1;
                        if (vectorNode instanceof VectorGroup) {
                            composerImpl.startReplaceGroup(-21889209);
                            VectorGroup vectorGroup2 = (VectorGroup) vectorNode;
                            VectorConfig vectorConfig2 = (VectorConfig) map4.get(vectorGroup2.name);
                            if (vectorConfig2 == null) {
                                vectorConfig2 = new VectorConfig() { // from class: androidx.compose.ui.graphics.vector.VectorPainterKt$RenderVectorGroup$config$2
                                };
                            }
                            map3 = map4;
                            VectorComposeKt.Group(vectorGroup2.name, ((Number) vectorConfig2.getOrDefault(VectorProperty.Rotation.INSTANCE, Float.valueOf(vectorGroup2.rotation))).floatValue(), ((Number) vectorConfig2.getOrDefault(VectorProperty.PivotX.INSTANCE, Float.valueOf(vectorGroup2.pivotX))).floatValue(), ((Number) vectorConfig2.getOrDefault(VectorProperty.PivotY.INSTANCE, Float.valueOf(vectorGroup2.pivotY))).floatValue(), ((Number) vectorConfig2.getOrDefault(VectorProperty.ScaleX.INSTANCE, Float.valueOf(vectorGroup2.scaleX))).floatValue(), ((Number) vectorConfig2.getOrDefault(VectorProperty.ScaleY.INSTANCE, Float.valueOf(vectorGroup2.scaleY))).floatValue(), ((Number) vectorConfig2.getOrDefault(VectorProperty.TranslateX.INSTANCE, Float.valueOf(vectorGroup2.translationX))).floatValue(), ((Number) vectorConfig2.getOrDefault(VectorProperty.TranslateY.INSTANCE, Float.valueOf(vectorGroup2.translationY))).floatValue(), (List) vectorConfig2.getOrDefault(VectorProperty.PathData.INSTANCE, vectorGroup2.clipPathData), ComposableLambdaKt.rememberComposableLambda(1450046638, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorPainterKt.RenderVectorGroup.1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                /* JADX WARN: Multi-variable type inference failed */
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    int iIntValue = ((Number) obj2).intValue();
                                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                    if (composerImpl3.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.ui.graphics.vector.RenderVectorGroup.<anonymous> (VectorPainter.kt:468)");
                                        }
                                        VectorPainterKt.RenderVectorGroup((VectorGroup) vectorNode, map4, composerImpl3, 0, 0);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    } else {
                                        composerImpl3.skipToGroupEnd();
                                    }
                                    return Unit.INSTANCE;
                                }
                            }, composerImpl), composerImpl, 805306368, 0);
                            composerImpl.end(false);
                        } else {
                            map3 = map4;
                            composerImpl.startReplaceGroup(-20884003);
                            composerImpl.end(false);
                        }
                        mapEmptyMap = map3;
                        anonymousClass1 = anonymousClass12;
                    }
                }
                Map map5 = mapEmptyMap;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                map2 = map5;
            }
            recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
            if (recomposeScopeImplEndRestartGroup == null) {
                recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorPainterKt.RenderVectorGroup.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        ((Number) obj2).intValue();
                        VectorPainterKt.RenderVectorGroup(vectorGroup, map2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                        return Unit.INSTANCE;
                    }
                };
                return;
            }
            return;
        }
        i3 |= 48;
        map2 = map;
        if (composerImpl.shouldExecute(i3 & 1, (i3 & 19) == 18)) {
        }
        recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup == null) {
        }
    }

    /* renamed from: configureVectorPainter-T4PVSW8, reason: not valid java name */
    public static final void m569configureVectorPainterT4PVSW8(VectorPainter vectorPainter, long j, long j2, String str, ColorFilter colorFilter, boolean z) {
        ((SnapshotMutableStateImpl) vectorPainter.size$delegate).setValue(Size.m415boximpl(j));
        ((SnapshotMutableStateImpl) vectorPainter.autoMirror$delegate).setValue(Boolean.valueOf(z));
        VectorComponent vectorComponent = vectorPainter.vector;
        ((SnapshotMutableStateImpl) vectorComponent.intrinsicColorFilter$delegate).setValue(colorFilter);
        ((SnapshotMutableStateImpl) vectorComponent.viewportSize$delegate).setValue(Size.m415boximpl(j2));
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
                pathComponent.renderPath.m446setFillTypeoQ8Xj4U(vectorPath.pathFillType);
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
    public static final long m570obtainViewportSizePq9zytI(float f, float f2, long j) {
        if (Float.isNaN(f)) {
            f = Float.intBitsToFloat((int) (j >> 32));
        }
        if (Float.isNaN(f2)) {
            f2 = Float.intBitsToFloat((int) (j & 4294967295L));
        }
        long jFloatToRawIntBits = (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
        Size.Companion companion = Size.Companion;
        return jFloatToRawIntBits;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0043  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final VectorPainter rememberVectorPainter(ImageVector imageVector, Composer composer) {
        BlendModeColorFilter blendModeColorFilter;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.graphics.vector.rememberVectorPainter (VectorPainter.kt:169)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        float f = imageVector.genId;
        float density2 = density.getDensity();
        boolean zChanged = composerImpl.changed((Float.floatToRawIntBits(density2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        Object objRememberedValue = composerImpl.rememberedValue();
        if (!zChanged) {
            Composer.Companion.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                GroupComponent groupComponent = new GroupComponent();
                createGroupComponent(groupComponent, imageVector.root);
                Unit unit = Unit.INSTANCE;
                float fMo58toPx0680j_4 = density.mo58toPx0680j_4(imageVector.defaultWidth);
                float fMo58toPx0680j_42 = density.mo58toPx0680j_4(imageVector.defaultHeight);
                long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_42) & 4294967295L);
                Size.Companion companion = Size.Companion;
                long jM570obtainViewportSizePq9zytI = m570obtainViewportSizePq9zytI(imageVector.viewportWidth, imageVector.viewportHeight, jFloatToRawIntBits);
                VectorPainter vectorPainter = new VectorPainter(groupComponent);
                long j = imageVector.tintColor;
                if (j != 16) {
                    ColorFilter.Companion.getClass();
                    blendModeColorFilter = new BlendModeColorFilter(j, imageVector.tintBlendMode, (DefaultConstructorMarker) null);
                } else {
                    blendModeColorFilter = null;
                }
                m569configureVectorPainterT4PVSW8(vectorPainter, jFloatToRawIntBits, jM570obtainViewportSizePq9zytI, imageVector.name, blendModeColorFilter, imageVector.autoMirror);
                composerImpl.updateRememberedValue(vectorPainter);
                objRememberedValue = vectorPainter;
            }
        }
        VectorPainter vectorPainter2 = (VectorPainter) objRememberedValue;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return vectorPainter2;
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0061  */
    /* renamed from: rememberVectorPainter-vIP8VLU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final VectorPainter m571rememberVectorPaintervIP8VLU(float f, float f2, float f3, float f4, String str, long j, int i, final ComposableLambdaImpl composableLambdaImpl, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.graphics.vector.rememberVectorPainter (VectorPainter.kt:129)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Density density = (Density) composerImpl.consume(CompositionLocalsKt.LocalDensity);
        float fMo58toPx0680j_4 = density.mo58toPx0680j_4(f);
        float fMo58toPx0680j_42 = density.mo58toPx0680j_4(f2);
        long jFloatToRawIntBits = (Float.floatToRawIntBits(fMo58toPx0680j_4) << 32) | (Float.floatToRawIntBits(fMo58toPx0680j_42) & 4294967295L);
        Size.Companion companion = Size.Companion;
        final long jM570obtainViewportSizePq9zytI = m570obtainViewportSizePq9zytI(f3, f4, jFloatToRawIntBits);
        boolean zChanged = composerImpl.changed(j) | composerImpl.changed(i);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion companion2 = Composer.Companion;
        if (!zChanged) {
            companion2.getClass();
            if (objRememberedValue == Composer.Companion.Empty) {
                if (j != 16) {
                    ColorFilter.Companion.getClass();
                    objRememberedValue = new BlendModeColorFilter(j, i, (DefaultConstructorMarker) null);
                } else {
                    objRememberedValue = null;
                }
                composerImpl.updateRememberedValue(objRememberedValue);
            }
        }
        ColorFilter colorFilter = (ColorFilter) objRememberedValue;
        Object objM = BasicTextKt$$ExternalSyntheticOutline0.m(composerImpl, -1837507429, companion2);
        Object obj = Composer.Companion.Empty;
        if (objM == obj) {
            objM = new VectorPainter(null, 1, null);
            composerImpl.updateRememberedValue(objM);
        }
        VectorPainter vectorPainter = (VectorPainter) objM;
        m569configureVectorPainterT4PVSW8(vectorPainter, jFloatToRawIntBits, jM570obtainViewportSizePq9zytI, str, colorFilter, true);
        ComposerImpl.CompositionContextImpl compositionContextImplRememberCompositionContext = ComposablesKt.rememberCompositionContext(composerImpl);
        boolean zChanged2 = composerImpl.changed(f3) | composerImpl.changed(f4);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        Object obj2 = objRememberedValue2;
        if (zChanged2 || objRememberedValue2 == obj) {
            Composition compositionImpl = vectorPainter.composition;
            if (compositionImpl == null || compositionImpl.isDisposed()) {
                VectorApplier vectorApplier = new VectorApplier(vectorPainter.vector.root);
                Object obj3 = CompositionKt.PendingApplyNoModifications;
                compositionImpl = new CompositionImpl(compositionContextImplRememberCompositionContext, vectorApplier, null, 4, null);
            }
            Composition composition = compositionImpl;
            composition.setContent(new ComposableLambdaImpl(2008312779, true, new Function2() { // from class: androidx.compose.ui.graphics.vector.VectorPainterKt$rememberVectorPainter$2$composition$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj4, Object obj5) {
                    Composer composer2 = (Composer) obj4;
                    int iIntValue = ((Number) obj5).intValue();
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.ui.graphics.vector.rememberVectorPainter.<anonymous>.<anonymous>.<anonymous> (VectorPainter.kt:154)");
                        }
                        composableLambdaImpl.invoke(Float.valueOf(Float.intBitsToFloat((int) (jM570obtainViewportSizePq9zytI >> 32))), Float.valueOf(Float.intBitsToFloat((int) (jM570obtainViewportSizePq9zytI & 4294967295L))), composerImpl2, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    } else {
                        composerImpl2.skipToGroupEnd();
                    }
                    return Unit.INSTANCE;
                }
            }));
            composerImpl.updateRememberedValue(composition);
            obj2 = composition;
        }
        final Composition composition2 = (Composition) obj2;
        vectorPainter.composition = composition2;
        boolean zChangedInstance = composerImpl.changedInstance(composition2);
        Object objRememberedValue3 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue3 == obj) {
            objRememberedValue3 = new Function1() { // from class: androidx.compose.ui.graphics.vector.VectorPainterKt$rememberVectorPainter$2$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj4) {
                    final Composition composition3 = composition2;
                    return new DisposableEffectResult() { // from class: androidx.compose.ui.graphics.vector.VectorPainterKt$rememberVectorPainter$2$1$1$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public final void dispose() {
                            composition3.dispose();
                        }
                    };
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue3);
        }
        EffectsKt.DisposableEffect(vectorPainter, (Function1) objRememberedValue3, composerImpl);
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return vectorPainter;
    }
}
