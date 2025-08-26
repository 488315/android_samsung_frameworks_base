package androidx.compose.ui.graphics.layer;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.AndroidPaint;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.InlineClassHelperKt;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSizeKt;
import androidx.compose.ui.unit.LayoutDirection;

/* loaded from: classes.dex */
public abstract class GraphicsLayerKt {
    /* JADX WARN: Removed duplicated region for block: B:25:0x0097  */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void drawLayer(DrawScope drawScope, GraphicsLayer graphicsLayer) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        float f;
        Canvas canvas = drawScope.getDrawContext().getCanvas();
        GraphicsLayer graphicsLayer2 = drawScope.getDrawContext().graphicsLayer;
        if (graphicsLayer.isReleased) {
            return;
        }
        graphicsLayer.configureOutlineAndClip();
        GraphicsLayerImpl graphicsLayerImpl = graphicsLayer.impl;
        if (!graphicsLayerImpl.getHasDisplayList()) {
            try {
                graphicsLayer.recordInternal();
            } catch (Throwable unused) {
            }
        }
        boolean z5 = graphicsLayerImpl.getShadowElevation() > 0.0f;
        if (z5) {
            canvas.enableZ();
        }
        android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
        AndroidCanvas androidCanvas = (AndroidCanvas) canvas;
        android.graphics.Canvas canvas3 = androidCanvas.internalCanvas;
        boolean zIsHardwareAccelerated = canvas3.isHardwareAccelerated();
        if (zIsHardwareAccelerated) {
            z = z5;
            z2 = true;
        } else {
            long j = graphicsLayer.topLeft;
            IntOffset.Companion companion = IntOffset.Companion;
            float f2 = (int) (j >> 32);
            float f3 = (int) (j & 4294967295L);
            z2 = true;
            long j2 = graphicsLayer.size;
            z = z5;
            float f4 = f2 + ((int) (j2 >> 32));
            float f5 = ((int) (j2 & 4294967295L)) + f3;
            float alpha = graphicsLayerImpl.getAlpha();
            ColorFilter colorFilter = graphicsLayerImpl.getColorFilter();
            int iMo552getBlendMode0nO6VwU = graphicsLayerImpl.mo552getBlendMode0nO6VwU();
            if (alpha >= 1.0f) {
                BlendMode.Companion.getClass();
                if (iMo552getBlendMode0nO6VwU == BlendMode.SrcOver && colorFilter == null) {
                    int iMo553getCompositingStrategyke2Ky5w = graphicsLayerImpl.mo553getCompositingStrategyke2Ky5w();
                    CompositingStrategy.Companion.getClass();
                    if (iMo553getCompositingStrategyke2Ky5w != CompositingStrategy.Offscreen) {
                        canvas3.save();
                        f = f2;
                    }
                    canvas3.translate(f, f3);
                    canvas3.concat(graphicsLayerImpl.calculateMatrix());
                } else {
                    AndroidPaint androidPaint = graphicsLayer.softwareLayerPaint;
                    if (androidPaint == null) {
                        androidPaint = new AndroidPaint();
                        graphicsLayer.softwareLayerPaint = androidPaint;
                    }
                    androidPaint.setAlpha(alpha);
                    androidPaint.m439setBlendModes9anfk8(iMo552getBlendMode0nO6VwU);
                    androidPaint.setColorFilter(colorFilter);
                    f = f2;
                    canvas3.saveLayer(f, f3, f4, f5, androidPaint.internalPaint);
                    canvas3.translate(f, f3);
                    canvas3.concat(graphicsLayerImpl.calculateMatrix());
                }
            }
        }
        boolean z6 = (zIsHardwareAccelerated || !graphicsLayer.clip) ? false : z2;
        if (z6) {
            canvas.save();
            Outline outline = graphicsLayer.getOutline();
            if (outline instanceof Outline.Rectangle) {
                Canvas.m455clipRectmtrdDE$default(canvas, outline.getBounds());
            } else if (outline instanceof Outline.Rounded) {
                AndroidPath androidPathPath = graphicsLayer.roundRectClipPath;
                if (androidPathPath != null) {
                    androidPathPath.internalPath.rewind();
                } else {
                    androidPathPath = AndroidPath_androidKt.Path();
                    graphicsLayer.roundRectClipPath = androidPathPath;
                }
                Path.addRoundRect$default(androidPathPath, ((Outline.Rounded) outline).roundRect);
                Canvas.m454clipPathmtrdDE$default(canvas, androidPathPath);
            } else if (outline instanceof Outline.Generic) {
                Canvas.m454clipPathmtrdDE$default(canvas, ((Outline.Generic) outline).path);
            }
        }
        if (graphicsLayer2 != null) {
            ChildLayerDependenciesTracker childLayerDependenciesTracker = graphicsLayer2.childDependenciesTracker;
            if (!childLayerDependenciesTracker.trackingInProgress) {
                InlineClassHelperKt.throwIllegalArgumentException("Only add dependencies during a tracking");
            }
            MutableScatterSet mutableScatterSet = childLayerDependenciesTracker.dependenciesSet;
            if (mutableScatterSet != null) {
                mutableScatterSet.add(graphicsLayer);
            } else if (childLayerDependenciesTracker.dependency != null) {
                MutableScatterSet mutableScatterSetMutableScatterSetOf = ScatterSetKt.mutableScatterSetOf();
                GraphicsLayer graphicsLayer3 = childLayerDependenciesTracker.dependency;
                graphicsLayer3.getClass();
                mutableScatterSetMutableScatterSetOf.add(graphicsLayer3);
                mutableScatterSetMutableScatterSetOf.add(graphicsLayer);
                childLayerDependenciesTracker.dependenciesSet = mutableScatterSetMutableScatterSetOf;
                childLayerDependenciesTracker.dependency = null;
            } else {
                childLayerDependenciesTracker.dependency = graphicsLayer;
            }
            MutableScatterSet mutableScatterSet2 = childLayerDependenciesTracker.oldDependenciesSet;
            if (mutableScatterSet2 != null) {
                z4 = !mutableScatterSet2.remove(graphicsLayer);
            } else if (childLayerDependenciesTracker.oldDependency != graphicsLayer) {
                z4 = z2;
            } else {
                childLayerDependenciesTracker.oldDependency = null;
                z4 = false;
            }
            if (z4) {
                graphicsLayer.parentLayerUsages++;
            }
        }
        if (androidCanvas.internalCanvas.isHardwareAccelerated()) {
            z3 = zIsHardwareAccelerated;
            graphicsLayerImpl.draw(canvas);
        } else {
            CanvasDrawScope canvasDrawScope = graphicsLayer.softwareDrawScope;
            if (canvasDrawScope == null) {
                canvasDrawScope = new CanvasDrawScope();
                graphicsLayer.softwareDrawScope = canvasDrawScope;
            }
            Density density = graphicsLayer.density;
            LayoutDirection layoutDirection = graphicsLayer.layoutDirection;
            long jM866toSizeozmzZPI = IntSizeKt.m866toSizeozmzZPI(graphicsLayer.size);
            ?? r0 = graphicsLayer.drawBlock;
            CanvasDrawScope.DrawParams drawParams = canvasDrawScope.drawParams;
            Density density2 = drawParams.density;
            LayoutDirection layoutDirection2 = drawParams.layoutDirection;
            Canvas canvas4 = drawParams.canvas;
            z3 = zIsHardwareAccelerated;
            long j3 = drawParams.size;
            drawParams.density = density;
            drawParams.layoutDirection = layoutDirection;
            drawParams.canvas = canvas;
            drawParams.size = jM866toSizeozmzZPI;
            canvas.save();
            r0.mo781invoke(canvasDrawScope);
            canvas.restore();
            drawParams.density = density2;
            drawParams.layoutDirection = layoutDirection2;
            drawParams.canvas = canvas4;
            drawParams.size = j3;
        }
        if (z6) {
            canvas.restore();
        }
        if (z) {
            canvas.disableZ();
        }
        if (z3) {
            return;
        }
        canvas3.restore();
    }
}
