package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.core.animation.PathInterpolator$$ExternalSyntheticOutline0;
import com.android.systemui.util.SystemUIAnalytics;
import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SVGAndroidRenderer {
    public static HashSet supportedFeatures;
    public final Canvas canvas;
    public SVG document;
    public final float dpi;
    public Stack matrixStack;
    public Stack parentStack;
    public RendererState state;
    public Stack stateStack;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.caverock.androidsvg.SVGAndroidRenderer$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment;
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap;
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin;

        static {
            int[] iArr = new int[SVG.Style.LineJoin.values().length];
            $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin = iArr;
            try {
                iArr[SVG.Style.LineJoin.Miter.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[SVG.Style.LineJoin.Round.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[SVG.Style.LineJoin.Bevel.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[SVG.Style.LineCap.values().length];
            $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap = iArr2;
            try {
                iArr2[SVG.Style.LineCap.Butt.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap[SVG.Style.LineCap.Round.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap[SVG.Style.LineCap.Square.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr3 = new int[PreserveAspectRatio.Alignment.values().length];
            $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment = iArr3;
            try {
                iArr3[PreserveAspectRatio.Alignment.xMidYMin.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMidYMid.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMidYMax.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMaxYMin.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMaxYMid.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMaxYMax.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMinYMid.ordinal()] = 7;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[PreserveAspectRatio.Alignment.xMinYMax.ordinal()] = 8;
            } catch (NoSuchFieldError unused14) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MarkerPositionCalculator implements SVG.PathInterface {
        public boolean closepathReAdjustPending;
        public MarkerVector lastPos;
        public final List markers;
        public boolean normalCubic;
        public boolean startArc;
        public float startX;
        public float startY;
        public int subpathStartIndex;

        public MarkerPositionCalculator(SVG.PathDefinition pathDefinition) {
            ArrayList arrayList = new ArrayList();
            this.markers = arrayList;
            this.lastPos = null;
            this.startArc = false;
            this.normalCubic = true;
            this.subpathStartIndex = -1;
            if (pathDefinition == null) {
                return;
            }
            pathDefinition.enumeratePath(this);
            if (this.closepathReAdjustPending) {
                this.lastPos.add((MarkerVector) arrayList.get(this.subpathStartIndex));
                arrayList.set(this.subpathStartIndex, this.lastPos);
                this.closepathReAdjustPending = false;
            }
            MarkerVector markerVector = this.lastPos;
            if (markerVector != null) {
                arrayList.add(markerVector);
            }
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
            this.startArc = true;
            this.normalCubic = false;
            MarkerVector markerVector = this.lastPos;
            SVGAndroidRenderer.access$700(markerVector.x, markerVector.y, f, f2, f3, z, z2, f4, f5, this);
            this.normalCubic = true;
            this.closepathReAdjustPending = false;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void close() {
            this.markers.add(this.lastPos);
            lineTo(this.startX, this.startY);
            this.closepathReAdjustPending = true;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
            if (this.normalCubic || this.startArc) {
                this.lastPos.add(f, f2);
                ((ArrayList) this.markers).add(this.lastPos);
                this.startArc = false;
            }
            this.lastPos = new MarkerVector(SVGAndroidRenderer.this, f5, f6, f5 - f3, f6 - f4);
            this.closepathReAdjustPending = false;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void lineTo(float f, float f2) {
            this.lastPos.add(f, f2);
            this.markers.add(this.lastPos);
            MarkerVector markerVector = this.lastPos;
            this.lastPos = new MarkerVector(SVGAndroidRenderer.this, f, f2, f - markerVector.x, f2 - markerVector.y);
            this.closepathReAdjustPending = false;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void moveTo(float f, float f2) {
            if (this.closepathReAdjustPending) {
                this.lastPos.add((MarkerVector) ((ArrayList) this.markers).get(this.subpathStartIndex));
                ((ArrayList) this.markers).set(this.subpathStartIndex, this.lastPos);
                this.closepathReAdjustPending = false;
            }
            MarkerVector markerVector = this.lastPos;
            if (markerVector != null) {
                ((ArrayList) this.markers).add(markerVector);
            }
            this.startX = f;
            this.startY = f2;
            this.lastPos = new MarkerVector(SVGAndroidRenderer.this, f, f2, 0.0f, 0.0f);
            this.subpathStartIndex = ((ArrayList) this.markers).size();
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void quadTo(float f, float f2, float f3, float f4) {
            this.lastPos.add(f, f2);
            this.markers.add(this.lastPos);
            this.lastPos = new MarkerVector(SVGAndroidRenderer.this, f3, f4, f3 - f, f4 - f2);
            this.closepathReAdjustPending = false;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PathConverter implements SVG.PathInterface {
        public float lastX;
        public float lastY;
        public final Path path = new Path();

        public PathConverter(SVGAndroidRenderer sVGAndroidRenderer, SVG.PathDefinition pathDefinition) {
            if (pathDefinition == null) {
                return;
            }
            pathDefinition.enumeratePath(this);
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
            SVGAndroidRenderer.access$700(this.lastX, this.lastY, f, f2, f3, z, z2, f4, f5, this);
            this.lastX = f4;
            this.lastY = f5;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void close() {
            this.path.close();
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
            this.path.cubicTo(f, f2, f3, f4, f5, f6);
            this.lastX = f5;
            this.lastY = f6;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void lineTo(float f, float f2) {
            this.path.lineTo(f, f2);
            this.lastX = f;
            this.lastY = f2;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void moveTo(float f, float f2) {
            this.path.moveTo(f, f2);
            this.lastX = f;
            this.lastY = f2;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void quadTo(float f, float f2, float f3, float f4) {
            this.path.quadTo(f, f2, f3, f4);
            this.lastX = f3;
            this.lastY = f4;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PathTextDrawer extends PlainTextDrawer {
        public final Path path;

        public PathTextDrawer(Path path, float f, float f2) {
            super(f, f2);
            this.path = path;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.PlainTextDrawer, com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public final void processText(String str) {
            SVGAndroidRenderer sVGAndroidRenderer = SVGAndroidRenderer.this;
            if (sVGAndroidRenderer.visible()) {
                RendererState rendererState = sVGAndroidRenderer.state;
                if (rendererState.hasFill) {
                    sVGAndroidRenderer.canvas.drawTextOnPath(str, this.path, this.x, this.y, rendererState.fillPaint);
                }
                RendererState rendererState2 = sVGAndroidRenderer.state;
                if (rendererState2.hasStroke) {
                    sVGAndroidRenderer.canvas.drawTextOnPath(str, this.path, this.x, this.y, rendererState2.strokePaint);
                }
            }
            this.x = sVGAndroidRenderer.state.fillPaint.measureText(str) + this.x;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PlainTextDrawer extends TextProcessor {
        public float x;
        public float y;

        public PlainTextDrawer(float f, float f2) {
            super(SVGAndroidRenderer.this, null);
            this.x = f;
            this.y = f2;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public void processText(String str) {
            SVGAndroidRenderer sVGAndroidRenderer = SVGAndroidRenderer.this;
            if (sVGAndroidRenderer.visible()) {
                RendererState rendererState = sVGAndroidRenderer.state;
                if (rendererState.hasFill) {
                    sVGAndroidRenderer.canvas.drawText(str, this.x, this.y, rendererState.fillPaint);
                }
                RendererState rendererState2 = sVGAndroidRenderer.state;
                if (rendererState2.hasStroke) {
                    sVGAndroidRenderer.canvas.drawText(str, this.x, this.y, rendererState2.strokePaint);
                }
            }
            this.x = sVGAndroidRenderer.state.fillPaint.measureText(str) + this.x;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PlainTextToPath extends TextProcessor {
        public final Path textAsPath;
        public float x;
        public final float y;

        public PlainTextToPath(float f, float f2, Path path) {
            super(SVGAndroidRenderer.this, null);
            this.x = f;
            this.y = f2;
            this.textAsPath = path;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public final boolean doTextContainer(SVG.TextContainer textContainer) {
            if (!(textContainer instanceof SVG.TextPath)) {
                return true;
            }
            Log.w("SVGAndroidRenderer", "Using <textPath> elements in a clip path is not supported.");
            return false;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public final void processText(String str) {
            String str2;
            SVGAndroidRenderer sVGAndroidRenderer = SVGAndroidRenderer.this;
            if (sVGAndroidRenderer.visible()) {
                Path path = new Path();
                str2 = str;
                sVGAndroidRenderer.state.fillPaint.getTextPath(str2, 0, str.length(), this.x, this.y, path);
                this.textAsPath.addPath(path);
            } else {
                str2 = str;
            }
            this.x = sVGAndroidRenderer.state.fillPaint.measureText(str2) + this.x;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TextBoundsCalculator extends TextProcessor {
        public final RectF bbox;
        public float x;
        public final float y;

        public TextBoundsCalculator(float f, float f2) {
            super(SVGAndroidRenderer.this, null);
            this.bbox = new RectF();
            this.x = f;
            this.y = f2;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public final boolean doTextContainer(SVG.TextContainer textContainer) {
            if (!(textContainer instanceof SVG.TextPath)) {
                return true;
            }
            SVG.TextPath textPath = (SVG.TextPath) textContainer;
            SVG.SvgElementBase resolveIRI = textContainer.document.resolveIRI(textPath.href);
            if (resolveIRI == null) {
                SVGAndroidRenderer.error("TextPath path reference '%s' not found", textPath.href);
                return false;
            }
            SVG.Path path = (SVG.Path) resolveIRI;
            Path path2 = new PathConverter(SVGAndroidRenderer.this, path.d).path;
            Matrix matrix = path.transform;
            if (matrix != null) {
                path2.transform(matrix);
            }
            RectF rectF = new RectF();
            path2.computeBounds(rectF, true);
            this.bbox.union(rectF);
            return false;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public final void processText(String str) {
            SVGAndroidRenderer sVGAndroidRenderer = SVGAndroidRenderer.this;
            if (sVGAndroidRenderer.visible()) {
                Rect rect = new Rect();
                sVGAndroidRenderer.state.fillPaint.getTextBounds(str, 0, str.length(), rect);
                RectF rectF = new RectF(rect);
                rectF.offset(this.x, this.y);
                this.bbox.union(rectF);
            }
            this.x = sVGAndroidRenderer.state.fillPaint.measureText(str) + this.x;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class TextProcessor {
        private TextProcessor(SVGAndroidRenderer sVGAndroidRenderer) {
        }

        public boolean doTextContainer(SVG.TextContainer textContainer) {
            return true;
        }

        public abstract void processText(String str);

        public /* synthetic */ TextProcessor(SVGAndroidRenderer sVGAndroidRenderer, AnonymousClass1 anonymousClass1) {
            this(sVGAndroidRenderer);
        }
    }

    public SVGAndroidRenderer(Canvas canvas, float f) {
        this.canvas = canvas;
        this.dpi = f;
    }

    public static void access$700(float f, float f2, float f3, float f4, float f5, boolean z, boolean z2, float f6, float f7, SVG.PathInterface pathInterface) {
        if (f == f6 && f2 == f7) {
            return;
        }
        if (f3 == 0.0f || f4 == 0.0f) {
            pathInterface.lineTo(f6, f7);
            return;
        }
        float abs = Math.abs(f3);
        float abs2 = Math.abs(f4);
        double radians = Math.toRadians(f5 % 360.0d);
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);
        double d = (f - f6) / 2.0d;
        double d2 = (f2 - f7) / 2.0d;
        double d3 = (sin * d2) + (cos * d);
        double d4 = (cos * d2) + ((-sin) * d);
        double d5 = abs * abs;
        double d6 = abs2 * abs2;
        double d7 = d3 * d3;
        double d8 = d4 * d4;
        double d9 = (d8 / d6) + (d7 / d5);
        if (d9 > 0.99999d) {
            double sqrt = Math.sqrt(d9) * 1.00001d;
            abs = (float) (abs * sqrt);
            abs2 = (float) (sqrt * abs2);
            d5 = abs * abs;
            d6 = abs2 * abs2;
        }
        double d10 = z == z2 ? -1.0d : 1.0d;
        double d11 = d5 * d6;
        double d12 = d5 * d8;
        double d13 = d6 * d7;
        double d14 = ((d11 - d12) - d13) / (d12 + d13);
        if (d14 < 0.0d) {
            d14 = 0.0d;
        }
        double sqrt2 = Math.sqrt(d14) * d10;
        double d15 = abs;
        double d16 = abs2;
        double d17 = ((d15 * d4) / d16) * sqrt2;
        double d18 = sqrt2 * (-((d16 * d3) / d15));
        double d19 = ((cos * d17) - (sin * d18)) + ((f + f6) / 2.0d);
        double d20 = (cos * d18) + (sin * d17) + ((f2 + f7) / 2.0d);
        double d21 = (d3 - d17) / d15;
        double d22 = (d4 - d18) / d16;
        double d23 = ((-d3) - d17) / d15;
        double d24 = ((-d4) - d18) / d16;
        double d25 = (d22 * d22) + (d21 * d21);
        double acos = Math.acos(d21 / Math.sqrt(d25)) * (d22 < 0.0d ? -1.0d : 1.0d);
        double sqrt3 = ((d22 * d24) + (d21 * d23)) / Math.sqrt(((d24 * d24) + (d23 * d23)) * d25);
        double acos2 = ((d21 * d24) - (d22 * d23) < 0.0d ? -1.0d : 1.0d) * (sqrt3 < -1.0d ? 3.141592653589793d : sqrt3 > 1.0d ? 0.0d : Math.acos(sqrt3));
        if (!z2 && acos2 > 0.0d) {
            acos2 -= 6.283185307179586d;
        } else if (z2 && acos2 < 0.0d) {
            acos2 += 6.283185307179586d;
        }
        double d26 = acos2 % 6.283185307179586d;
        double d27 = acos % 6.283185307179586d;
        int ceil = (int) Math.ceil((Math.abs(d26) * 2.0d) / 3.141592653589793d);
        double d28 = d26 / ceil;
        double d29 = d28 / 2.0d;
        double sin2 = (Math.sin(d29) * 1.3333333333333333d) / (Math.cos(d29) + 1.0d);
        int i = ceil * 6;
        float[] fArr = new float[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < ceil) {
            double d30 = d27;
            double d31 = (i2 * d28) + d30;
            double cos2 = Math.cos(d31);
            double sin3 = Math.sin(d31);
            int i4 = i2;
            int i5 = i3;
            fArr[i5] = (float) (cos2 - (sin2 * sin3));
            fArr[i3 + 1] = (float) ((cos2 * sin2) + sin3);
            double d32 = d31 + d28;
            double cos3 = Math.cos(d32);
            double sin4 = Math.sin(d32);
            fArr[i5 + 2] = (float) ((sin2 * sin4) + cos3);
            fArr[i5 + 3] = (float) (sin4 - (sin2 * cos3));
            fArr[i5 + 4] = (float) cos3;
            i3 = i5 + 6;
            fArr[i5 + 5] = (float) sin4;
            i2 = i4 + 1;
            d27 = d30;
            ceil = ceil;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(abs, abs2);
        matrix.postRotate(f5);
        matrix.postTranslate((float) d19, (float) d20);
        matrix.mapPoints(fArr);
        fArr[i - 2] = f6;
        fArr[i - 1] = f7;
        for (int i6 = 0; i6 < i; i6 += 6) {
            pathInterface.cubicTo(fArr[i6], fArr[i6 + 1], fArr[i6 + 2], fArr[i6 + 3], fArr[i6 + 4], fArr[i6 + 5]);
        }
    }

    public static SVG.Box calculatePathBounds(Path path) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        return new SVG.Box(rectF.left, rectF.top, rectF.width(), rectF.height());
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0077, code lost:
    
        if (r1 != 8) goto L37;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Matrix calculateViewBoxTransform(com.caverock.androidsvg.SVG.Box r9, com.caverock.androidsvg.SVG.Box r10, com.caverock.androidsvg.PreserveAspectRatio r11) {
        /*
            android.graphics.Matrix r0 = new android.graphics.Matrix
            r0.<init>()
            if (r11 == 0) goto L91
            com.caverock.androidsvg.PreserveAspectRatio$Alignment r1 = r11.alignment
            if (r1 != 0) goto Ld
            goto L91
        Ld:
            float r2 = r9.width
            float r3 = r10.width
            float r2 = r2 / r3
            float r3 = r9.height
            float r4 = r10.height
            float r3 = r3 / r4
            float r4 = r10.minX
            float r4 = -r4
            float r5 = r10.minY
            float r5 = -r5
            com.caverock.androidsvg.PreserveAspectRatio r6 = com.caverock.androidsvg.PreserveAspectRatio.STRETCH
            boolean r6 = r11.equals(r6)
            if (r6 == 0) goto L33
            float r10 = r9.minX
            float r9 = r9.minY
            r0.preTranslate(r10, r9)
            r0.preScale(r2, r3)
            r0.preTranslate(r4, r5)
            return r0
        L33:
            com.caverock.androidsvg.PreserveAspectRatio$Scale r6 = com.caverock.androidsvg.PreserveAspectRatio.Scale.slice
            com.caverock.androidsvg.PreserveAspectRatio$Scale r11 = r11.scale
            if (r11 != r6) goto L3e
            float r11 = java.lang.Math.max(r2, r3)
            goto L42
        L3e:
            float r11 = java.lang.Math.min(r2, r3)
        L42:
            float r2 = r9.width
            float r2 = r2 / r11
            float r3 = r9.height
            float r3 = r3 / r11
            int[] r6 = com.caverock.androidsvg.SVGAndroidRenderer.AnonymousClass1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment
            int r7 = r1.ordinal()
            r7 = r6[r7]
            r8 = 1073741824(0x40000000, float:2.0)
            switch(r7) {
                case 1: goto L5b;
                case 2: goto L5b;
                case 3: goto L5b;
                case 4: goto L56;
                case 5: goto L56;
                case 6: goto L56;
                default: goto L55;
            }
        L55:
            goto L60
        L56:
            float r7 = r10.width
            float r7 = r7 - r2
        L59:
            float r4 = r4 - r7
            goto L60
        L5b:
            float r7 = r10.width
            float r7 = r7 - r2
            float r7 = r7 / r8
            goto L59
        L60:
            int r1 = r1.ordinal()
            r1 = r6[r1]
            r2 = 2
            if (r1 == r2) goto L7f
            r2 = 3
            if (r1 == r2) goto L7a
            r2 = 5
            if (r1 == r2) goto L7f
            r2 = 6
            if (r1 == r2) goto L7a
            r2 = 7
            if (r1 == r2) goto L7f
            r2 = 8
            if (r1 == r2) goto L7a
            goto L84
        L7a:
            float r10 = r10.height
            float r10 = r10 - r3
        L7d:
            float r5 = r5 - r10
            goto L84
        L7f:
            float r10 = r10.height
            float r10 = r10 - r3
            float r10 = r10 / r8
            goto L7d
        L84:
            float r10 = r9.minX
            float r9 = r9.minY
            r0.preTranslate(r10, r9)
            r0.preScale(r11, r11)
            r0.preTranslate(r4, r5)
        L91:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.calculateViewBoxTransform(com.caverock.androidsvg.SVG$Box, com.caverock.androidsvg.SVG$Box, com.caverock.androidsvg.PreserveAspectRatio):android.graphics.Matrix");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0048, code lost:
    
        if (r5.equals("fantasy") == false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Typeface checkGenericFont(java.lang.String r5, java.lang.Integer r6, com.caverock.androidsvg.SVG.Style.FontStyle r7) {
        /*
            r0 = 2
            r1 = 3
            com.caverock.androidsvg.SVG$Style$FontStyle r2 = com.caverock.androidsvg.SVG.Style.FontStyle.Italic
            r3 = 0
            r4 = 1
            if (r7 != r2) goto La
            r7 = r4
            goto Lb
        La:
            r7 = r3
        Lb:
            int r6 = r6.intValue()
            r2 = 500(0x1f4, float:7.0E-43)
            if (r6 <= r2) goto L19
            if (r7 == 0) goto L17
            r6 = r1
            goto L1e
        L17:
            r6 = r4
            goto L1e
        L19:
            if (r7 == 0) goto L1d
            r6 = r0
            goto L1e
        L1d:
            r6 = r3
        L1e:
            r5.getClass()
            r7 = -1
            int r2 = r5.hashCode()
            switch(r2) {
                case -1536685117: goto L56;
                case -1431958525: goto L4b;
                case -1081737434: goto L42;
                case 109326717: goto L36;
                case 1126973893: goto L2b;
                default: goto L29;
            }
        L29:
            r0 = r7
            goto L61
        L2b:
            java.lang.String r0 = "cursive"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L34
            goto L29
        L34:
            r0 = 4
            goto L61
        L36:
            java.lang.String r0 = "serif"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L40
            goto L29
        L40:
            r0 = r1
            goto L61
        L42:
            java.lang.String r1 = "fantasy"
            boolean r5 = r5.equals(r1)
            if (r5 != 0) goto L61
            goto L29
        L4b:
            java.lang.String r0 = "monospace"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L54
            goto L29
        L54:
            r0 = r4
            goto L61
        L56:
            java.lang.String r0 = "sans-serif"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L60
            goto L29
        L60:
            r0 = r3
        L61:
            switch(r0) {
                case 0: goto L82;
                case 1: goto L7b;
                case 2: goto L74;
                case 3: goto L6d;
                case 4: goto L66;
                default: goto L64;
            }
        L64:
            r5 = 0
            return r5
        L66:
            android.graphics.Typeface r5 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r5 = android.graphics.Typeface.create(r5, r6)
            return r5
        L6d:
            android.graphics.Typeface r5 = android.graphics.Typeface.SERIF
            android.graphics.Typeface r5 = android.graphics.Typeface.create(r5, r6)
            return r5
        L74:
            android.graphics.Typeface r5 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r5 = android.graphics.Typeface.create(r5, r6)
            return r5
        L7b:
            android.graphics.Typeface r5 = android.graphics.Typeface.MONOSPACE
            android.graphics.Typeface r5 = android.graphics.Typeface.create(r5, r6)
            return r5
        L82:
            android.graphics.Typeface r5 = android.graphics.Typeface.SANS_SERIF
            android.graphics.Typeface r5 = android.graphics.Typeface.create(r5, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.checkGenericFont(java.lang.String, java.lang.Integer, com.caverock.androidsvg.SVG$Style$FontStyle):android.graphics.Typeface");
    }

    public static int colourWithOpacity(float f, int i) {
        int i2 = 255;
        int round = Math.round(((i >> 24) & 255) * f);
        if (round < 0) {
            i2 = 0;
        } else if (round <= 255) {
            i2 = round;
        }
        return (i2 << 24) | (i & 16777215);
    }

    public static void error(String str, Object... objArr) {
        Log.e("SVGAndroidRenderer", String.format(str, objArr));
    }

    public static void fillInChainedGradientFields(SVG.GradientElement gradientElement, String str) {
        SVG.SvgElementBase resolveIRI = gradientElement.document.resolveIRI(str);
        if (resolveIRI == null) {
            Log.w("SVGAndroidRenderer", "Gradient reference '" + str + "' not found");
            return;
        }
        if (!(resolveIRI instanceof SVG.GradientElement)) {
            error("Gradient href attributes must point to other gradient elements", new Object[0]);
            return;
        }
        if (resolveIRI == gradientElement) {
            error("Circular reference in gradient href attribute '%s'", str);
            return;
        }
        SVG.GradientElement gradientElement2 = (SVG.GradientElement) resolveIRI;
        if (gradientElement.gradientUnitsAreUser == null) {
            gradientElement.gradientUnitsAreUser = gradientElement2.gradientUnitsAreUser;
        }
        if (gradientElement.gradientTransform == null) {
            gradientElement.gradientTransform = gradientElement2.gradientTransform;
        }
        if (gradientElement.spreadMethod == null) {
            gradientElement.spreadMethod = gradientElement2.spreadMethod;
        }
        if (((ArrayList) gradientElement.children).isEmpty()) {
            gradientElement.children = gradientElement2.children;
        }
        try {
            if (gradientElement instanceof SVG.SvgLinearGradient) {
                SVG.SvgLinearGradient svgLinearGradient = (SVG.SvgLinearGradient) gradientElement;
                SVG.SvgLinearGradient svgLinearGradient2 = (SVG.SvgLinearGradient) resolveIRI;
                if (svgLinearGradient.x1 == null) {
                    svgLinearGradient.x1 = svgLinearGradient2.x1;
                }
                if (svgLinearGradient.y1 == null) {
                    svgLinearGradient.y1 = svgLinearGradient2.y1;
                }
                if (svgLinearGradient.x2 == null) {
                    svgLinearGradient.x2 = svgLinearGradient2.x2;
                }
                if (svgLinearGradient.y2 == null) {
                    svgLinearGradient.y2 = svgLinearGradient2.y2;
                }
            } else {
                fillInChainedGradientFields((SVG.SvgRadialGradient) gradientElement, (SVG.SvgRadialGradient) resolveIRI);
            }
        } catch (ClassCastException unused) {
        }
        String str2 = gradientElement2.href;
        if (str2 != null) {
            fillInChainedGradientFields(gradientElement, str2);
        }
    }

    public static void fillInChainedPatternFields(SVG.Pattern pattern, String str) {
        SVG.SvgElementBase resolveIRI = pattern.document.resolveIRI(str);
        if (resolveIRI == null) {
            Log.w("SVGAndroidRenderer", "Pattern reference '" + str + "' not found");
            return;
        }
        if (!(resolveIRI instanceof SVG.Pattern)) {
            error("Pattern href attributes must point to other pattern elements", new Object[0]);
            return;
        }
        if (resolveIRI == pattern) {
            error("Circular reference in pattern href attribute '%s'", str);
            return;
        }
        SVG.Pattern pattern2 = (SVG.Pattern) resolveIRI;
        if (pattern.patternUnitsAreUser == null) {
            pattern.patternUnitsAreUser = pattern2.patternUnitsAreUser;
        }
        if (pattern.patternContentUnitsAreUser == null) {
            pattern.patternContentUnitsAreUser = pattern2.patternContentUnitsAreUser;
        }
        if (pattern.patternTransform == null) {
            pattern.patternTransform = pattern2.patternTransform;
        }
        if (pattern.x == null) {
            pattern.x = pattern2.x;
        }
        if (pattern.y == null) {
            pattern.y = pattern2.y;
        }
        if (pattern.width == null) {
            pattern.width = pattern2.width;
        }
        if (pattern.height == null) {
            pattern.height = pattern2.height;
        }
        if (((ArrayList) pattern.children).isEmpty()) {
            pattern.children = pattern2.children;
        }
        if (pattern.viewBox == null) {
            pattern.viewBox = pattern2.viewBox;
        }
        if (pattern.preserveAspectRatio == null) {
            pattern.preserveAspectRatio = pattern2.preserveAspectRatio;
        }
        String str2 = pattern2.href;
        if (str2 != null) {
            fillInChainedPatternFields(pattern, str2);
        }
    }

    public static boolean isSpecified(SVG.Style style, long j) {
        return (style.specifiedFlags & j) != 0;
    }

    public static void setPaintColour(RendererState rendererState, boolean z, SVG.SvgPaint svgPaint) {
        int i;
        SVG.Style style = rendererState.style;
        float floatValue = (z ? style.fillOpacity : style.strokeOpacity).floatValue();
        if (svgPaint instanceof SVG.Colour) {
            i = ((SVG.Colour) svgPaint).colour;
        } else if (!(svgPaint instanceof SVG.CurrentColor)) {
            return;
        } else {
            i = rendererState.style.color.colour;
        }
        int colourWithOpacity = colourWithOpacity(floatValue, i);
        if (z) {
            rendererState.fillPaint.setColor(colourWithOpacity);
        } else {
            rendererState.strokePaint.setColor(colourWithOpacity);
        }
    }

    public final Path calculateClipPath(SVG.SvgElement svgElement, SVG.Box box) {
        Path objectToPath;
        SVG.SvgElementBase resolveIRI = svgElement.document.resolveIRI(this.state.style.clipPath);
        if (resolveIRI == null) {
            error("ClipPath reference '%s' not found", this.state.style.clipPath);
            return null;
        }
        SVG.ClipPath clipPath = (SVG.ClipPath) resolveIRI;
        this.stateStack.push(this.state);
        this.state = findInheritFromAncestorState(clipPath);
        Boolean bool = clipPath.clipPathUnitsAreUser;
        int i = 0;
        boolean z = bool == null || bool.booleanValue();
        Matrix matrix = new Matrix();
        if (!z) {
            matrix.preTranslate(box.minX, box.minY);
            matrix.preScale(box.width, box.height);
        }
        Matrix matrix2 = clipPath.transform;
        if (matrix2 != null) {
            matrix.preConcat(matrix2);
        }
        Path path = new Path();
        ArrayList arrayList = (ArrayList) clipPath.children;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            SVG.SvgObject svgObject = (SVG.SvgObject) obj;
            if ((svgObject instanceof SVG.SvgElement) && (objectToPath = objectToPath((SVG.SvgElement) svgObject, true)) != null) {
                path.op(objectToPath, Path.Op.UNION);
            }
        }
        if (this.state.style.clipPath != null) {
            if (clipPath.boundingBox == null) {
                clipPath.boundingBox = calculatePathBounds(path);
            }
            Path calculateClipPath = calculateClipPath(clipPath, clipPath.boundingBox);
            if (calculateClipPath != null) {
                path.op(calculateClipPath, Path.Op.INTERSECT);
            }
        }
        path.transform(matrix);
        this.state = (RendererState) this.stateStack.pop();
        return path;
    }

    public final float calculateTextWidth(SVG.TextContainer textContainer) {
        TextWidthCalculator textWidthCalculator = new TextWidthCalculator(this, null);
        enumerateTextSpans(textContainer, textWidthCalculator);
        return textWidthCalculator.x;
    }

    public final void checkForClipPath(SVG.SvgElement svgElement, SVG.Box box) {
        Path calculateClipPath;
        if (this.state.style.clipPath == null || (calculateClipPath = calculateClipPath(svgElement, box)) == null) {
            return;
        }
        this.canvas.clipPath(calculateClipPath);
    }

    public final void checkForGradientsAndPatterns(SVG.SvgElement svgElement) {
        SVG.SvgPaint svgPaint = this.state.style.fill;
        if (svgPaint instanceof SVG.PaintReference) {
            decodePaintReference(true, svgElement.boundingBox, (SVG.PaintReference) svgPaint);
        }
        SVG.SvgPaint svgPaint2 = this.state.style.stroke;
        if (svgPaint2 instanceof SVG.PaintReference) {
            decodePaintReference(false, svgElement.boundingBox, (SVG.PaintReference) svgPaint2);
        }
    }

    public final void decodePaintReference(boolean z, SVG.Box box, SVG.PaintReference paintReference) {
        float f;
        float floatValue;
        float f2;
        float floatValue2;
        float f3;
        float floatValue3;
        float f4;
        float f5;
        SVG.SvgElementBase resolveIRI = this.document.resolveIRI(paintReference.href);
        if (resolveIRI == null) {
            error("%s reference '%s' not found", z ? "Fill" : "Stroke", paintReference.href);
            SVG.SvgPaint svgPaint = paintReference.fallback;
            if (svgPaint != null) {
                setPaintColour(this.state, z, svgPaint);
                return;
            } else if (z) {
                this.state.hasFill = false;
                return;
            } else {
                this.state.hasStroke = false;
                return;
            }
        }
        float f6 = -1.0f;
        if (resolveIRI instanceof SVG.SvgLinearGradient) {
            SVG.SvgLinearGradient svgLinearGradient = (SVG.SvgLinearGradient) resolveIRI;
            String str = svgLinearGradient.href;
            if (str != null) {
                fillInChainedGradientFields(svgLinearGradient, str);
            }
            Boolean bool = svgLinearGradient.gradientUnitsAreUser;
            boolean z2 = bool != null && bool.booleanValue();
            RendererState rendererState = this.state;
            Paint paint = z ? rendererState.fillPaint : rendererState.strokePaint;
            if (z2) {
                RendererState rendererState2 = this.state;
                SVG.Box box2 = rendererState2.viewBox;
                if (box2 == null) {
                    box2 = rendererState2.viewPort;
                }
                SVG.Length length = svgLinearGradient.x1;
                float floatValueX = length != null ? length.floatValueX(this) : 0.0f;
                SVG.Length length2 = svgLinearGradient.y1;
                floatValue2 = length2 != null ? length2.floatValueY(this) : 0.0f;
                SVG.Length length3 = svgLinearGradient.x2;
                float floatValueX2 = length3 != null ? length3.floatValueX(this) : box2.width;
                SVG.Length length4 = svgLinearGradient.y2;
                f4 = floatValueX;
                f5 = floatValueX2;
                floatValue3 = length4 != null ? length4.floatValueY(this) : 0.0f;
                f3 = 256.0f;
            } else {
                SVG.Length length5 = svgLinearGradient.x1;
                float floatValue4 = length5 != null ? length5.floatValue(this, 1.0f) : 0.0f;
                SVG.Length length6 = svgLinearGradient.y1;
                floatValue2 = length6 != null ? length6.floatValue(this, 1.0f) : 0.0f;
                SVG.Length length7 = svgLinearGradient.x2;
                float floatValue5 = length7 != null ? length7.floatValue(this, 1.0f) : 1.0f;
                f3 = 256.0f;
                SVG.Length length8 = svgLinearGradient.y2;
                floatValue3 = length8 != null ? length8.floatValue(this, 1.0f) : 0.0f;
                f4 = floatValue4;
                f5 = floatValue5;
            }
            float f7 = floatValue2;
            statePush();
            this.state = findInheritFromAncestorState(svgLinearGradient);
            Matrix matrix = new Matrix();
            if (!z2) {
                matrix.preTranslate(box.minX, box.minY);
                matrix.preScale(box.width, box.height);
            }
            Matrix matrix2 = svgLinearGradient.gradientTransform;
            if (matrix2 != null) {
                matrix.preConcat(matrix2);
            }
            int size = ((ArrayList) svgLinearGradient.children).size();
            if (size == 0) {
                statePop();
                if (z) {
                    this.state.hasFill = false;
                    return;
                } else {
                    this.state.hasStroke = false;
                    return;
                }
            }
            int[] iArr = new int[size];
            float[] fArr = new float[size];
            ArrayList arrayList = (ArrayList) svgLinearGradient.children;
            int size2 = arrayList.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size2) {
                Object obj = arrayList.get(i2);
                i2++;
                SVG.Stop stop = (SVG.Stop) ((SVG.SvgObject) obj);
                Float f8 = stop.offset;
                float floatValue6 = f8 != null ? f8.floatValue() : 0.0f;
                if (i == 0 || floatValue6 >= f6) {
                    fArr[i] = floatValue6;
                    f6 = floatValue6;
                } else {
                    fArr[i] = f6;
                }
                statePush();
                updateStyleForElement(stop, this.state);
                SVG.Style style = this.state.style;
                SVG.Colour colour = (SVG.Colour) style.stopColor;
                if (colour == null) {
                    colour = SVG.Colour.BLACK;
                }
                iArr[i] = colourWithOpacity(style.stopOpacity.floatValue(), colour.colour);
                i++;
                statePop();
            }
            if ((f4 == f5 && f7 == floatValue3) || size == 1) {
                statePop();
                paint.setColor(iArr[size - 1]);
                return;
            }
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            SVG.GradientSpread gradientSpread = svgLinearGradient.spreadMethod;
            if (gradientSpread != null) {
                if (gradientSpread == SVG.GradientSpread.reflect) {
                    tileMode = Shader.TileMode.MIRROR;
                } else if (gradientSpread == SVG.GradientSpread.repeat) {
                    tileMode = Shader.TileMode.REPEAT;
                }
            }
            Shader.TileMode tileMode2 = tileMode;
            statePop();
            LinearGradient linearGradient = new LinearGradient(f4, f7, f5, floatValue3, iArr, fArr, tileMode2);
            linearGradient.setLocalMatrix(matrix);
            paint.setShader(linearGradient);
            int floatValue7 = (int) (this.state.style.fillOpacity.floatValue() * f3);
            paint.setAlpha(floatValue7 < 0 ? 0 : floatValue7 > 255 ? 255 : floatValue7);
            return;
        }
        if (!(resolveIRI instanceof SVG.SvgRadialGradient)) {
            if (resolveIRI instanceof SVG.SolidColor) {
                SVG.SolidColor solidColor = (SVG.SolidColor) resolveIRI;
                if (z) {
                    if (isSpecified(solidColor.baseStyle, 2147483648L)) {
                        RendererState rendererState3 = this.state;
                        SVG.Style style2 = rendererState3.style;
                        SVG.SvgPaint svgPaint2 = solidColor.baseStyle.solidColor;
                        style2.fill = svgPaint2;
                        rendererState3.hasFill = svgPaint2 != null;
                    }
                    if (isSpecified(solidColor.baseStyle, 4294967296L)) {
                        this.state.style.fillOpacity = solidColor.baseStyle.solidOpacity;
                    }
                    if (isSpecified(solidColor.baseStyle, 6442450944L)) {
                        RendererState rendererState4 = this.state;
                        setPaintColour(rendererState4, z, rendererState4.style.fill);
                        return;
                    }
                    return;
                }
                if (isSpecified(solidColor.baseStyle, 2147483648L)) {
                    RendererState rendererState5 = this.state;
                    SVG.Style style3 = rendererState5.style;
                    SVG.SvgPaint svgPaint3 = solidColor.baseStyle.solidColor;
                    style3.stroke = svgPaint3;
                    rendererState5.hasStroke = svgPaint3 != null;
                }
                if (isSpecified(solidColor.baseStyle, 4294967296L)) {
                    this.state.style.strokeOpacity = solidColor.baseStyle.solidOpacity;
                }
                if (isSpecified(solidColor.baseStyle, 6442450944L)) {
                    RendererState rendererState6 = this.state;
                    setPaintColour(rendererState6, z, rendererState6.style.stroke);
                    return;
                }
                return;
            }
            return;
        }
        SVG.SvgRadialGradient svgRadialGradient = (SVG.SvgRadialGradient) resolveIRI;
        String str2 = svgRadialGradient.href;
        if (str2 != null) {
            fillInChainedGradientFields(svgRadialGradient, str2);
        }
        Boolean bool2 = svgRadialGradient.gradientUnitsAreUser;
        boolean z3 = bool2 != null && bool2.booleanValue();
        RendererState rendererState7 = this.state;
        Paint paint2 = z ? rendererState7.fillPaint : rendererState7.strokePaint;
        if (z3) {
            SVG.Length length9 = new SVG.Length(50.0f, SVG.Unit.percent);
            SVG.Length length10 = svgRadialGradient.cx;
            float floatValueX3 = length10 != null ? length10.floatValueX(this) : length9.floatValueX(this);
            SVG.Length length11 = svgRadialGradient.cy;
            float floatValueY = length11 != null ? length11.floatValueY(this) : length9.floatValueY(this);
            SVG.Length length12 = svgRadialGradient.r;
            floatValue = length12 != null ? length12.floatValue(this) : length9.floatValue(this);
            f = floatValueX3;
            f2 = floatValueY;
        } else {
            SVG.Length length13 = svgRadialGradient.cx;
            float floatValue8 = length13 != null ? length13.floatValue(this, 1.0f) : 0.5f;
            SVG.Length length14 = svgRadialGradient.cy;
            float floatValue9 = length14 != null ? length14.floatValue(this, 1.0f) : 0.5f;
            SVG.Length length15 = svgRadialGradient.r;
            f = floatValue8;
            floatValue = length15 != null ? length15.floatValue(this, 1.0f) : 0.5f;
            f2 = floatValue9;
        }
        statePush();
        this.state = findInheritFromAncestorState(svgRadialGradient);
        Matrix matrix3 = new Matrix();
        if (!z3) {
            matrix3.preTranslate(box.minX, box.minY);
            matrix3.preScale(box.width, box.height);
        }
        Matrix matrix4 = svgRadialGradient.gradientTransform;
        if (matrix4 != null) {
            matrix3.preConcat(matrix4);
        }
        int size3 = ((ArrayList) svgRadialGradient.children).size();
        if (size3 == 0) {
            statePop();
            if (z) {
                this.state.hasFill = false;
                return;
            } else {
                this.state.hasStroke = false;
                return;
            }
        }
        int i3 = 0;
        int[] iArr2 = new int[size3];
        float[] fArr2 = new float[size3];
        ArrayList arrayList2 = (ArrayList) svgRadialGradient.children;
        int size4 = arrayList2.size();
        int i4 = 0;
        while (i3 < size4) {
            Object obj2 = arrayList2.get(i3);
            i3++;
            SVG.Stop stop2 = (SVG.Stop) ((SVG.SvgObject) obj2);
            Float f9 = stop2.offset;
            float floatValue10 = f9 != null ? f9.floatValue() : 0.0f;
            if (i4 == 0 || floatValue10 >= f6) {
                fArr2[i4] = floatValue10;
                f6 = floatValue10;
            } else {
                fArr2[i4] = f6;
            }
            statePush();
            updateStyleForElement(stop2, this.state);
            SVG.Style style4 = this.state.style;
            SVG.Colour colour2 = (SVG.Colour) style4.stopColor;
            if (colour2 == null) {
                colour2 = SVG.Colour.BLACK;
            }
            iArr2[i4] = colourWithOpacity(style4.stopOpacity.floatValue(), colour2.colour);
            i4++;
            statePop();
        }
        if (floatValue == 0.0f || size3 == 1) {
            statePop();
            paint2.setColor(iArr2[size3 - 1]);
            return;
        }
        Shader.TileMode tileMode3 = Shader.TileMode.CLAMP;
        SVG.GradientSpread gradientSpread2 = svgRadialGradient.spreadMethod;
        if (gradientSpread2 != null) {
            if (gradientSpread2 == SVG.GradientSpread.reflect) {
                tileMode3 = Shader.TileMode.MIRROR;
            } else if (gradientSpread2 == SVG.GradientSpread.repeat) {
                tileMode3 = Shader.TileMode.REPEAT;
            }
        }
        Shader.TileMode tileMode4 = tileMode3;
        statePop();
        RadialGradient radialGradient = new RadialGradient(f, f2, floatValue, iArr2, fArr2, tileMode4);
        radialGradient.setLocalMatrix(matrix3);
        paint2.setShader(radialGradient);
        int floatValue11 = (int) (this.state.style.fillOpacity.floatValue() * 256.0f);
        if (floatValue11 < 0) {
            floatValue11 = 0;
        } else if (floatValue11 > 255) {
            floatValue11 = 255;
        }
        paint2.setAlpha(floatValue11);
    }

    public final boolean display() {
        Boolean bool = this.state.style.display;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0226  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doFilledPath(com.caverock.androidsvg.SVG.SvgElement r23, android.graphics.Path r24) {
        /*
            Method dump skipped, instructions count: 569
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.doFilledPath(com.caverock.androidsvg.SVG$SvgElement, android.graphics.Path):void");
    }

    public final void doStroke(Path path) {
        RendererState rendererState = this.state;
        if (rendererState.style.vectorEffect != SVG.Style.VectorEffect.NonScalingStroke) {
            this.canvas.drawPath(path, rendererState.strokePaint);
            return;
        }
        Matrix matrix = this.canvas.getMatrix();
        Path path2 = new Path();
        path.transform(matrix, path2);
        this.canvas.setMatrix(new Matrix());
        Shader shader = this.state.strokePaint.getShader();
        Matrix matrix2 = new Matrix();
        if (shader != null) {
            shader.getLocalMatrix(matrix2);
            Matrix matrix3 = new Matrix(matrix2);
            matrix3.postConcat(matrix);
            shader.setLocalMatrix(matrix3);
        }
        this.canvas.drawPath(path2, this.state.strokePaint);
        this.canvas.setMatrix(matrix);
        if (shader != null) {
            shader.setLocalMatrix(matrix2);
        }
    }

    public final void enumerateTextSpans(SVG.TextContainer textContainer, TextProcessor textProcessor) {
        float f;
        float f2;
        float f3;
        SVG.Style.TextAnchor anchorPosition;
        if (display()) {
            Iterator it = ((ArrayList) textContainer.children).iterator();
            boolean z = true;
            while (it.hasNext()) {
                SVG.SvgObject svgObject = (SVG.SvgObject) it.next();
                if (svgObject instanceof SVG.TextSequence) {
                    textProcessor.processText(textXMLSpaceTransform(((SVG.TextSequence) svgObject).text, z, !it.hasNext()));
                } else if (textProcessor.doTextContainer((SVG.TextContainer) svgObject)) {
                    float f4 = 0.0f;
                    if (svgObject instanceof SVG.TextPath) {
                        statePush();
                        SVG.TextPath textPath = (SVG.TextPath) svgObject;
                        updateStyleForElement(textPath, this.state);
                        if (display() && visible()) {
                            SVG.SvgElementBase resolveIRI = textPath.document.resolveIRI(textPath.href);
                            if (resolveIRI == null) {
                                error("TextPath reference '%s' not found", textPath.href);
                            } else {
                                SVG.Path path = (SVG.Path) resolveIRI;
                                Path path2 = new PathConverter(this, path.d).path;
                                Matrix matrix = path.transform;
                                if (matrix != null) {
                                    path2.transform(matrix);
                                }
                                PathMeasure pathMeasure = new PathMeasure(path2, false);
                                SVG.Length length = textPath.startOffset;
                                float floatValue = length != null ? length.floatValue(this, pathMeasure.getLength()) : 0.0f;
                                SVG.Style.TextAnchor anchorPosition2 = getAnchorPosition();
                                if (anchorPosition2 != SVG.Style.TextAnchor.Start) {
                                    float calculateTextWidth = calculateTextWidth(textPath);
                                    if (anchorPosition2 == SVG.Style.TextAnchor.Middle) {
                                        calculateTextWidth /= 2.0f;
                                    }
                                    floatValue -= calculateTextWidth;
                                }
                                checkForGradientsAndPatterns(textPath.textRoot);
                                boolean pushLayer = pushLayer();
                                enumerateTextSpans(textPath, new PathTextDrawer(path2, floatValue, 0.0f));
                                if (pushLayer) {
                                    popLayer(textPath.boundingBox);
                                }
                            }
                        }
                        statePop();
                    } else if (svgObject instanceof SVG.TSpan) {
                        statePush();
                        SVG.TSpan tSpan = (SVG.TSpan) svgObject;
                        updateStyleForElement(tSpan, this.state);
                        if (display()) {
                            List list = tSpan.x;
                            boolean z2 = list != null && ((ArrayList) list).size() > 0;
                            boolean z3 = textProcessor instanceof PlainTextDrawer;
                            if (z3) {
                                float floatValueX = !z2 ? ((PlainTextDrawer) textProcessor).x : ((SVG.Length) ((ArrayList) tSpan.x).get(0)).floatValueX(this);
                                List list2 = tSpan.y;
                                f2 = (list2 == null || ((ArrayList) list2).size() == 0) ? ((PlainTextDrawer) textProcessor).y : ((SVG.Length) ((ArrayList) tSpan.y).get(0)).floatValueY(this);
                                List list3 = tSpan.dx;
                                f3 = (list3 == null || ((ArrayList) list3).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) tSpan.dx).get(0)).floatValueX(this);
                                List list4 = tSpan.dy;
                                if (list4 != null && ((ArrayList) list4).size() != 0) {
                                    f4 = ((SVG.Length) ((ArrayList) tSpan.dy).get(0)).floatValueY(this);
                                }
                                float f5 = floatValueX;
                                f = f4;
                                f4 = f5;
                            } else {
                                f = 0.0f;
                                f2 = 0.0f;
                                f3 = 0.0f;
                            }
                            if (z2 && (anchorPosition = getAnchorPosition()) != SVG.Style.TextAnchor.Start) {
                                float calculateTextWidth2 = calculateTextWidth(tSpan);
                                if (anchorPosition == SVG.Style.TextAnchor.Middle) {
                                    calculateTextWidth2 /= 2.0f;
                                }
                                f4 -= calculateTextWidth2;
                            }
                            checkForGradientsAndPatterns(tSpan.textRoot);
                            if (z3) {
                                PlainTextDrawer plainTextDrawer = (PlainTextDrawer) textProcessor;
                                plainTextDrawer.x = f4 + f3;
                                plainTextDrawer.y = f2 + f;
                            }
                            boolean pushLayer2 = pushLayer();
                            enumerateTextSpans(tSpan, textProcessor);
                            if (pushLayer2) {
                                popLayer(tSpan.boundingBox);
                            }
                        }
                        statePop();
                    } else if (svgObject instanceof SVG.TRef) {
                        statePush();
                        SVG.TRef tRef = (SVG.TRef) svgObject;
                        updateStyleForElement(tRef, this.state);
                        if (display()) {
                            checkForGradientsAndPatterns(tRef.textRoot);
                            SVG.SvgElementBase resolveIRI2 = svgObject.document.resolveIRI(tRef.href);
                            if (resolveIRI2 == null || !(resolveIRI2 instanceof SVG.TextContainer)) {
                                error("Tref reference '%s' not found", tRef.href);
                            } else {
                                StringBuilder sb = new StringBuilder();
                                extractRawText((SVG.TextContainer) resolveIRI2, sb);
                                if (sb.length() > 0) {
                                    textProcessor.processText(sb.toString());
                                }
                            }
                        }
                        statePop();
                    }
                }
                z = false;
            }
        }
    }

    public final void extractRawText(SVG.TextContainer textContainer, StringBuilder sb) {
        Iterator it = ((ArrayList) textContainer.children).iterator();
        boolean z = true;
        while (it.hasNext()) {
            SVG.SvgObject svgObject = (SVG.SvgObject) it.next();
            if (svgObject instanceof SVG.TextContainer) {
                extractRawText((SVG.TextContainer) svgObject, sb);
            } else if (svgObject instanceof SVG.TextSequence) {
                sb.append(textXMLSpaceTransform(((SVG.TextSequence) svgObject).text, z, !it.hasNext()));
            }
            z = false;
        }
    }

    public final RendererState findInheritFromAncestorState(SVG.SvgElementBase svgElementBase) {
        RendererState rendererState = new RendererState(this);
        updateStyle(rendererState, SVG.Style.getDefaultStyle());
        findInheritFromAncestorState(svgElementBase, rendererState);
        return rendererState;
    }

    public final SVG.Style.TextAnchor getAnchorPosition() {
        SVG.Style.TextAnchor textAnchor;
        SVG.Style style = this.state.style;
        if (style.direction == SVG.Style.TextDirection.LTR || (textAnchor = style.textAnchor) == SVG.Style.TextAnchor.Middle) {
            return style.textAnchor;
        }
        SVG.Style.TextAnchor textAnchor2 = SVG.Style.TextAnchor.Start;
        return textAnchor == textAnchor2 ? SVG.Style.TextAnchor.End : textAnchor2;
    }

    public final Path.FillType getClipRuleFromState() {
        SVG.Style.FillRule fillRule = this.state.style.clipRule;
        return (fillRule == null || fillRule != SVG.Style.FillRule.EvenOdd) ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Path makePathAndBoundingBox(com.caverock.androidsvg.SVG.Rect r20) {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.makePathAndBoundingBox(com.caverock.androidsvg.SVG$Rect):android.graphics.Path");
    }

    public final SVG.Box makeViewPort(SVG.Length length, SVG.Length length2, SVG.Length length3, SVG.Length length4) {
        float floatValueX = length != null ? length.floatValueX(this) : 0.0f;
        float floatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        RendererState rendererState = this.state;
        SVG.Box box = rendererState.viewBox;
        if (box == null) {
            box = rendererState.viewPort;
        }
        return new SVG.Box(floatValueX, floatValueY, length3 != null ? length3.floatValueX(this) : box.width, length4 != null ? length4.floatValueY(this) : box.height);
    }

    public final Path objectToPath(SVG.SvgElement svgElement, boolean z) {
        Path path;
        Path calculateClipPath;
        this.stateStack.push(this.state);
        RendererState rendererState = new RendererState(this, this.state);
        this.state = rendererState;
        updateStyleForElement(svgElement, rendererState);
        if (!display() || !visible()) {
            this.state = (RendererState) this.stateStack.pop();
            return null;
        }
        if (svgElement instanceof SVG.Use) {
            if (!z) {
                error("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
            }
            SVG.Use use = (SVG.Use) svgElement;
            SVG.SvgElementBase resolveIRI = svgElement.document.resolveIRI(use.href);
            if (resolveIRI == null) {
                error("Use reference '%s' not found", use.href);
                this.state = (RendererState) this.stateStack.pop();
                return null;
            }
            if (!(resolveIRI instanceof SVG.SvgElement)) {
                this.state = (RendererState) this.stateStack.pop();
                return null;
            }
            path = objectToPath((SVG.SvgElement) resolveIRI, false);
            if (path != null) {
                if (use.boundingBox == null) {
                    use.boundingBox = calculatePathBounds(path);
                }
                Matrix matrix = use.transform;
                if (matrix != null) {
                    path.transform(matrix);
                }
                if (this.state.style.clipPath != null && (calculateClipPath = calculateClipPath(svgElement, svgElement.boundingBox)) != null) {
                    path.op(calculateClipPath, Path.Op.INTERSECT);
                }
                this.state = (RendererState) this.stateStack.pop();
                return path;
            }
            return null;
        }
        if (svgElement instanceof SVG.GraphicsElement) {
            SVG.GraphicsElement graphicsElement = (SVG.GraphicsElement) svgElement;
            if (svgElement instanceof SVG.Path) {
                path = new PathConverter(this, ((SVG.Path) svgElement).d).path;
                if (svgElement.boundingBox == null) {
                    svgElement.boundingBox = calculatePathBounds(path);
                }
            } else {
                path = svgElement instanceof SVG.Rect ? makePathAndBoundingBox((SVG.Rect) svgElement) : svgElement instanceof SVG.Circle ? makePathAndBoundingBox((SVG.Circle) svgElement) : svgElement instanceof SVG.Ellipse ? makePathAndBoundingBox((SVG.Ellipse) svgElement) : svgElement instanceof SVG.PolyLine ? makePathAndBoundingBox((SVG.PolyLine) svgElement) : null;
            }
            if (path != null) {
                if (graphicsElement.boundingBox == null) {
                    graphicsElement.boundingBox = calculatePathBounds(path);
                }
                Matrix matrix2 = graphicsElement.transform;
                if (matrix2 != null) {
                    path.transform(matrix2);
                }
                path.setFillType(getClipRuleFromState());
            }
            return null;
        }
        if (!(svgElement instanceof SVG.Text)) {
            error("Invalid %s element found in clipPath definition", svgElement.getNodeName());
            return null;
        }
        SVG.Text text = (SVG.Text) svgElement;
        List list = text.x;
        float f = 0.0f;
        float floatValueX = (list == null || ((ArrayList) list).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.x).get(0)).floatValueX(this);
        List list2 = text.y;
        float floatValueY = (list2 == null || ((ArrayList) list2).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.y).get(0)).floatValueY(this);
        List list3 = text.dx;
        float floatValueX2 = (list3 == null || ((ArrayList) list3).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.dx).get(0)).floatValueX(this);
        List list4 = text.dy;
        if (list4 != null && ((ArrayList) list4).size() != 0) {
            f = ((SVG.Length) ((ArrayList) text.dy).get(0)).floatValueY(this);
        }
        if (this.state.style.textAnchor != SVG.Style.TextAnchor.Start) {
            float calculateTextWidth = calculateTextWidth(text);
            if (this.state.style.textAnchor == SVG.Style.TextAnchor.Middle) {
                calculateTextWidth /= 2.0f;
            }
            floatValueX -= calculateTextWidth;
        }
        if (text.boundingBox == null) {
            TextBoundsCalculator textBoundsCalculator = new TextBoundsCalculator(floatValueX, floatValueY);
            enumerateTextSpans(text, textBoundsCalculator);
            RectF rectF = textBoundsCalculator.bbox;
            text.boundingBox = new SVG.Box(rectF.left, rectF.top, rectF.width(), textBoundsCalculator.bbox.height());
        }
        Path path2 = new Path();
        enumerateTextSpans(text, new PlainTextToPath(floatValueX + floatValueX2, floatValueY + f, path2));
        Matrix matrix3 = text.transform;
        if (matrix3 != null) {
            path2.transform(matrix3);
        }
        path2.setFillType(getClipRuleFromState());
        path = path2;
        if (this.state.style.clipPath != null) {
            path.op(calculateClipPath, Path.Op.INTERSECT);
        }
        this.state = (RendererState) this.stateStack.pop();
        return path;
    }

    public final void popLayer(SVG.Box box) {
        if (this.state.style.mask != null) {
            Paint paint = new Paint();
            PorterDuff.Mode mode = PorterDuff.Mode.DST_IN;
            paint.setXfermode(new PorterDuffXfermode(mode));
            this.canvas.saveLayer(null, paint, 31);
            Paint paint2 = new Paint();
            paint2.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.2127f, 0.7151f, 0.0722f, 0.0f, 0.0f})));
            this.canvas.saveLayer(null, paint2, 31);
            SVG.Mask mask = (SVG.Mask) this.document.resolveIRI(this.state.style.mask);
            renderMask(mask, box);
            this.canvas.restore();
            Paint paint3 = new Paint();
            paint3.setXfermode(new PorterDuffXfermode(mode));
            this.canvas.saveLayer(null, paint3, 31);
            renderMask(mask, box);
            this.canvas.restore();
            this.canvas.restore();
        }
        statePop();
    }

    public final boolean pushLayer() {
        SVG.SvgElementBase resolveIRI;
        int i = 0;
        if (this.state.style.opacity.floatValue() >= 1.0f && this.state.style.mask == null) {
            return false;
        }
        Canvas canvas = this.canvas;
        int floatValue = (int) (this.state.style.opacity.floatValue() * 256.0f);
        if (floatValue >= 0) {
            i = 255;
            if (floatValue <= 255) {
                i = floatValue;
            }
        }
        canvas.saveLayerAlpha(null, i, 31);
        this.stateStack.push(this.state);
        RendererState rendererState = new RendererState(this, this.state);
        this.state = rendererState;
        String str = rendererState.style.mask;
        if (str != null && ((resolveIRI = this.document.resolveIRI(str)) == null || !(resolveIRI instanceof SVG.Mask))) {
            error("Mask reference '%s' not found", this.state.style.mask);
            this.state.style.mask = null;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void render(SVG.SvgObject svgObject) {
        SVG.Length length;
        String str;
        int indexOf;
        Set systemLanguage;
        SVG.Length length2;
        Boolean bool;
        if (svgObject instanceof SVG.NotDirectlyRendered) {
            return;
        }
        statePush();
        if ((svgObject instanceof SVG.SvgElementBase) && (bool = ((SVG.SvgElementBase) svgObject).spacePreserve) != null) {
            this.state.spacePreserve = bool.booleanValue();
        }
        if (svgObject instanceof SVG.Svg) {
            SVG.Svg svg = (SVG.Svg) svgObject;
            render(svg, makeViewPort(svg.x, svg.y, svg.width, svg.height), svg.viewBox, svg.preserveAspectRatio);
        } else {
            Bitmap bitmap = null;
            if (svgObject instanceof SVG.Use) {
                SVG.Use use = (SVG.Use) svgObject;
                SVG.Length length3 = use.width;
                if ((length3 == null || !length3.isZero()) && ((length2 = use.height) == null || !length2.isZero())) {
                    updateStyleForElement(use, this.state);
                    if (display()) {
                        SVG.SvgObject resolveIRI = use.document.resolveIRI(use.href);
                        if (resolveIRI == null) {
                            error("Use reference '%s' not found", use.href);
                        } else {
                            Matrix matrix = use.transform;
                            if (matrix != null) {
                                this.canvas.concat(matrix);
                            }
                            SVG.Length length4 = use.x;
                            float floatValueX = length4 != null ? length4.floatValueX(this) : 0.0f;
                            SVG.Length length5 = use.y;
                            this.canvas.translate(floatValueX, length5 != null ? length5.floatValueY(this) : 0.0f);
                            checkForClipPath(use, use.boundingBox);
                            boolean pushLayer = pushLayer();
                            this.parentStack.push(use);
                            this.matrixStack.push(this.canvas.getMatrix());
                            if (resolveIRI instanceof SVG.Svg) {
                                SVG.Svg svg2 = (SVG.Svg) resolveIRI;
                                SVG.Box makeViewPort = makeViewPort(null, null, use.width, use.height);
                                statePush();
                                render(svg2, makeViewPort, svg2.viewBox, svg2.preserveAspectRatio);
                                statePop();
                            } else if (resolveIRI instanceof SVG.Symbol) {
                                SVG.Length length6 = use.width;
                                if (length6 == null) {
                                    length6 = new SVG.Length(100.0f, SVG.Unit.percent);
                                }
                                SVG.Length length7 = use.height;
                                if (length7 == null) {
                                    length7 = new SVG.Length(100.0f, SVG.Unit.percent);
                                }
                                SVG.Box makeViewPort2 = makeViewPort(null, null, length6, length7);
                                statePush();
                                SVG.Symbol symbol = (SVG.Symbol) resolveIRI;
                                if (makeViewPort2.width != 0.0f && makeViewPort2.height != 0.0f) {
                                    PreserveAspectRatio preserveAspectRatio = symbol.preserveAspectRatio;
                                    if (preserveAspectRatio == null) {
                                        preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
                                    }
                                    updateStyleForElement(symbol, this.state);
                                    RendererState rendererState = this.state;
                                    rendererState.viewPort = makeViewPort2;
                                    if (!rendererState.style.overflow.booleanValue()) {
                                        SVG.Box box = this.state.viewPort;
                                        setClipRect(box.minX, box.minY, box.width, box.height);
                                    }
                                    SVG.Box box2 = symbol.viewBox;
                                    if (box2 != null) {
                                        this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, box2, preserveAspectRatio));
                                        this.state.viewBox = symbol.viewBox;
                                    } else {
                                        Canvas canvas = this.canvas;
                                        SVG.Box box3 = this.state.viewPort;
                                        canvas.translate(box3.minX, box3.minY);
                                    }
                                    boolean pushLayer2 = pushLayer();
                                    renderChildren(symbol, true);
                                    if (pushLayer2) {
                                        popLayer(symbol.boundingBox);
                                    }
                                    updateParentBoundingBox(symbol);
                                }
                                statePop();
                            } else {
                                render(resolveIRI);
                            }
                            this.parentStack.pop();
                            this.matrixStack.pop();
                            if (pushLayer) {
                                popLayer(use.boundingBox);
                            }
                            updateParentBoundingBox(use);
                        }
                    }
                }
            } else {
                if (svgObject instanceof SVG.Switch) {
                    SVG.Switch r13 = (SVG.Switch) svgObject;
                    updateStyleForElement(r13, this.state);
                    if (display()) {
                        Matrix matrix2 = r13.transform;
                        if (matrix2 != null) {
                            this.canvas.concat(matrix2);
                        }
                        checkForClipPath(r13, r13.boundingBox);
                        boolean pushLayer3 = pushLayer();
                        String language = Locale.getDefault().getLanguage();
                        ArrayList arrayList = (ArrayList) r13.children;
                        int size = arrayList.size();
                        while (true) {
                            if (r4 >= size) {
                                break;
                            }
                            Object obj = arrayList.get(r4);
                            r4++;
                            SVG.SvgObject svgObject2 = (SVG.SvgObject) obj;
                            if (svgObject2 instanceof SVG.SvgConditional) {
                                SVG.SvgConditional svgConditional = (SVG.SvgConditional) svgObject2;
                                if (svgConditional.getRequiredExtensions() == null && ((systemLanguage = svgConditional.getSystemLanguage()) == null || (!systemLanguage.isEmpty() && systemLanguage.contains(language)))) {
                                    Set requiredFeatures = svgConditional.getRequiredFeatures();
                                    if (requiredFeatures != null) {
                                        if (supportedFeatures == null) {
                                            synchronized (SVGAndroidRenderer.class) {
                                                HashSet hashSet = new HashSet();
                                                supportedFeatures = hashSet;
                                                hashSet.add(SystemUIAnalytics.CONTROL_KEY_STRUCTURE);
                                                supportedFeatures.add("BasicStructure");
                                                supportedFeatures.add("ConditionalProcessing");
                                                supportedFeatures.add(SystemUIAnalytics.DT_WALLPAPER_STATUS_TYPE_IMAGE);
                                                supportedFeatures.add("Style");
                                                supportedFeatures.add("ViewportAttribute");
                                                supportedFeatures.add("Shape");
                                                supportedFeatures.add("BasicText");
                                                supportedFeatures.add("PaintAttribute");
                                                supportedFeatures.add("BasicPaintAttribute");
                                                supportedFeatures.add("OpacityAttribute");
                                                supportedFeatures.add("BasicGraphicsAttribute");
                                                supportedFeatures.add("Marker");
                                                supportedFeatures.add("Gradient");
                                                supportedFeatures.add("Pattern");
                                                supportedFeatures.add("Clip");
                                                supportedFeatures.add("BasicClip");
                                                supportedFeatures.add("Mask");
                                                supportedFeatures.add("View");
                                            }
                                        }
                                        if (!requiredFeatures.isEmpty() && supportedFeatures.containsAll(requiredFeatures)) {
                                        }
                                    }
                                    Set requiredFormats = svgConditional.getRequiredFormats();
                                    if (requiredFormats == null) {
                                        Set requiredFonts = svgConditional.getRequiredFonts();
                                        if (requiredFonts == null) {
                                            render(svgObject2);
                                            break;
                                        }
                                        requiredFonts.isEmpty();
                                    } else {
                                        requiredFormats.isEmpty();
                                    }
                                }
                            }
                        }
                        if (pushLayer3) {
                            popLayer(r13.boundingBox);
                        }
                        updateParentBoundingBox(r13);
                    }
                } else if (svgObject instanceof SVG.Group) {
                    SVG.Group group = (SVG.Group) svgObject;
                    updateStyleForElement(group, this.state);
                    if (display()) {
                        Matrix matrix3 = group.transform;
                        if (matrix3 != null) {
                            this.canvas.concat(matrix3);
                        }
                        checkForClipPath(group, group.boundingBox);
                        boolean pushLayer4 = pushLayer();
                        renderChildren(group, true);
                        if (pushLayer4) {
                            popLayer(group.boundingBox);
                        }
                        updateParentBoundingBox(group);
                    }
                } else if (svgObject instanceof SVG.Image) {
                    SVG.Image image = (SVG.Image) svgObject;
                    SVG.Length length8 = image.width;
                    if (length8 != null && !length8.isZero() && (length = image.height) != null && !length.isZero() && (str = image.href) != null) {
                        PreserveAspectRatio preserveAspectRatio2 = image.preserveAspectRatio;
                        if (preserveAspectRatio2 == null) {
                            preserveAspectRatio2 = PreserveAspectRatio.LETTERBOX;
                        }
                        if (str.startsWith("data:") && str.length() >= 14 && (indexOf = str.indexOf(44)) >= 12 && ";base64".equals(str.substring(indexOf - 7, indexOf))) {
                            try {
                                byte[] decode = Base64.decode(str.substring(indexOf + 1), 0);
                                bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
                            } catch (Exception e) {
                                Log.e("SVGAndroidRenderer", "Could not decode bad Data URL", e);
                            }
                        }
                        if (bitmap != null) {
                            SVG.Box box4 = new SVG.Box(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
                            updateStyleForElement(image, this.state);
                            if (display() && visible()) {
                                Matrix matrix4 = image.transform;
                                if (matrix4 != null) {
                                    this.canvas.concat(matrix4);
                                }
                                SVG.Length length9 = image.x;
                                float floatValueX2 = length9 != null ? length9.floatValueX(this) : 0.0f;
                                SVG.Length length10 = image.y;
                                this.state.viewPort = new SVG.Box(floatValueX2, length10 != null ? length10.floatValueY(this) : 0.0f, image.width.floatValueX(this), image.height.floatValueX(this));
                                if (!this.state.style.overflow.booleanValue()) {
                                    SVG.Box box5 = this.state.viewPort;
                                    setClipRect(box5.minX, box5.minY, box5.width, box5.height);
                                }
                                image.boundingBox = this.state.viewPort;
                                updateParentBoundingBox(image);
                                checkForClipPath(image, image.boundingBox);
                                boolean pushLayer5 = pushLayer();
                                viewportFill();
                                this.canvas.save();
                                this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, box4, preserveAspectRatio2));
                                this.canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(this.state.style.imageRendering != SVG.Style.RenderQuality.optimizeSpeed ? 2 : 0));
                                this.canvas.restore();
                                if (pushLayer5) {
                                    popLayer(image.boundingBox);
                                }
                            }
                        }
                    }
                } else if (svgObject instanceof SVG.Path) {
                    SVG.Path path = (SVG.Path) svgObject;
                    if (path.d != null) {
                        updateStyleForElement(path, this.state);
                        if (display() && visible()) {
                            RendererState rendererState2 = this.state;
                            if (rendererState2.hasStroke || rendererState2.hasFill) {
                                Matrix matrix5 = path.transform;
                                if (matrix5 != null) {
                                    this.canvas.concat(matrix5);
                                }
                                Path path2 = new PathConverter(this, path.d).path;
                                if (path.boundingBox == null) {
                                    path.boundingBox = calculatePathBounds(path2);
                                }
                                updateParentBoundingBox(path);
                                checkForGradientsAndPatterns(path);
                                checkForClipPath(path, path.boundingBox);
                                boolean pushLayer6 = pushLayer();
                                RendererState rendererState3 = this.state;
                                if (rendererState3.hasFill) {
                                    SVG.Style.FillRule fillRule = rendererState3.style.fillRule;
                                    path2.setFillType((fillRule == null || fillRule != SVG.Style.FillRule.EvenOdd) ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                    doFilledPath(path, path2);
                                }
                                if (this.state.hasStroke) {
                                    doStroke(path2);
                                }
                                renderMarkers(path);
                                if (pushLayer6) {
                                    popLayer(path.boundingBox);
                                }
                            }
                        }
                    }
                } else if (svgObject instanceof SVG.Rect) {
                    SVG.Rect rect = (SVG.Rect) svgObject;
                    SVG.Length length11 = rect.width;
                    if (length11 != null && rect.height != null && !length11.isZero() && !rect.height.isZero()) {
                        updateStyleForElement(rect, this.state);
                        if (display() && visible()) {
                            Matrix matrix6 = rect.transform;
                            if (matrix6 != null) {
                                this.canvas.concat(matrix6);
                            }
                            Path makePathAndBoundingBox = makePathAndBoundingBox(rect);
                            updateParentBoundingBox(rect);
                            checkForGradientsAndPatterns(rect);
                            checkForClipPath(rect, rect.boundingBox);
                            boolean pushLayer7 = pushLayer();
                            if (this.state.hasFill) {
                                doFilledPath(rect, makePathAndBoundingBox);
                            }
                            if (this.state.hasStroke) {
                                doStroke(makePathAndBoundingBox);
                            }
                            if (pushLayer7) {
                                popLayer(rect.boundingBox);
                            }
                        }
                    }
                } else if (svgObject instanceof SVG.Circle) {
                    SVG.Circle circle = (SVG.Circle) svgObject;
                    SVG.Length length12 = circle.r;
                    if (length12 != null && !length12.isZero()) {
                        updateStyleForElement(circle, this.state);
                        if (display() && visible()) {
                            Matrix matrix7 = circle.transform;
                            if (matrix7 != null) {
                                this.canvas.concat(matrix7);
                            }
                            Path makePathAndBoundingBox2 = makePathAndBoundingBox(circle);
                            updateParentBoundingBox(circle);
                            checkForGradientsAndPatterns(circle);
                            checkForClipPath(circle, circle.boundingBox);
                            boolean pushLayer8 = pushLayer();
                            if (this.state.hasFill) {
                                doFilledPath(circle, makePathAndBoundingBox2);
                            }
                            if (this.state.hasStroke) {
                                doStroke(makePathAndBoundingBox2);
                            }
                            if (pushLayer8) {
                                popLayer(circle.boundingBox);
                            }
                        }
                    }
                } else if (svgObject instanceof SVG.Ellipse) {
                    SVG.Ellipse ellipse = (SVG.Ellipse) svgObject;
                    SVG.Length length13 = ellipse.rx;
                    if (length13 != null && ellipse.ry != null && !length13.isZero() && !ellipse.ry.isZero()) {
                        updateStyleForElement(ellipse, this.state);
                        if (display() && visible()) {
                            Matrix matrix8 = ellipse.transform;
                            if (matrix8 != null) {
                                this.canvas.concat(matrix8);
                            }
                            Path makePathAndBoundingBox3 = makePathAndBoundingBox(ellipse);
                            updateParentBoundingBox(ellipse);
                            checkForGradientsAndPatterns(ellipse);
                            checkForClipPath(ellipse, ellipse.boundingBox);
                            boolean pushLayer9 = pushLayer();
                            if (this.state.hasFill) {
                                doFilledPath(ellipse, makePathAndBoundingBox3);
                            }
                            if (this.state.hasStroke) {
                                doStroke(makePathAndBoundingBox3);
                            }
                            if (pushLayer9) {
                                popLayer(ellipse.boundingBox);
                            }
                        }
                    }
                } else if (svgObject instanceof SVG.Line) {
                    SVG.Line line = (SVG.Line) svgObject;
                    updateStyleForElement(line, this.state);
                    if (display() && visible() && this.state.hasStroke) {
                        Matrix matrix9 = line.transform;
                        if (matrix9 != null) {
                            this.canvas.concat(matrix9);
                        }
                        SVG.Length length14 = line.x1;
                        float floatValueX3 = length14 == null ? 0.0f : length14.floatValueX(this);
                        SVG.Length length15 = line.y1;
                        float floatValueY = length15 == null ? 0.0f : length15.floatValueY(this);
                        SVG.Length length16 = line.x2;
                        float floatValueX4 = length16 == null ? 0.0f : length16.floatValueX(this);
                        SVG.Length length17 = line.y2;
                        r3 = length17 != null ? length17.floatValueY(this) : 0.0f;
                        if (line.boundingBox == null) {
                            line.boundingBox = new SVG.Box(Math.min(floatValueX3, floatValueX4), Math.min(floatValueY, r3), Math.abs(floatValueX4 - floatValueX3), Math.abs(r3 - floatValueY));
                        }
                        Path path3 = new Path();
                        path3.moveTo(floatValueX3, floatValueY);
                        path3.lineTo(floatValueX4, r3);
                        updateParentBoundingBox(line);
                        checkForGradientsAndPatterns(line);
                        checkForClipPath(line, line.boundingBox);
                        boolean pushLayer10 = pushLayer();
                        doStroke(path3);
                        renderMarkers(line);
                        if (pushLayer10) {
                            popLayer(line.boundingBox);
                        }
                    }
                } else if (svgObject instanceof SVG.Polygon) {
                    SVG.Polygon polygon = (SVG.Polygon) svgObject;
                    updateStyleForElement(polygon, this.state);
                    if (display() && visible()) {
                        RendererState rendererState4 = this.state;
                        if (rendererState4.hasStroke || rendererState4.hasFill) {
                            Matrix matrix10 = polygon.transform;
                            if (matrix10 != null) {
                                this.canvas.concat(matrix10);
                            }
                            if (polygon.points.length >= 2) {
                                Path makePathAndBoundingBox4 = makePathAndBoundingBox(polygon);
                                updateParentBoundingBox(polygon);
                                checkForGradientsAndPatterns(polygon);
                                checkForClipPath(polygon, polygon.boundingBox);
                                boolean pushLayer11 = pushLayer();
                                if (this.state.hasFill) {
                                    doFilledPath(polygon, makePathAndBoundingBox4);
                                }
                                if (this.state.hasStroke) {
                                    doStroke(makePathAndBoundingBox4);
                                }
                                renderMarkers(polygon);
                                if (pushLayer11) {
                                    popLayer(polygon.boundingBox);
                                }
                            }
                        }
                    }
                } else if (svgObject instanceof SVG.PolyLine) {
                    SVG.PolyLine polyLine = (SVG.PolyLine) svgObject;
                    updateStyleForElement(polyLine, this.state);
                    if (display() && visible()) {
                        RendererState rendererState5 = this.state;
                        if (rendererState5.hasStroke || rendererState5.hasFill) {
                            Matrix matrix11 = polyLine.transform;
                            if (matrix11 != null) {
                                this.canvas.concat(matrix11);
                            }
                            if (polyLine.points.length >= 2) {
                                Path makePathAndBoundingBox5 = makePathAndBoundingBox(polyLine);
                                updateParentBoundingBox(polyLine);
                                SVG.Style.FillRule fillRule2 = this.state.style.fillRule;
                                makePathAndBoundingBox5.setFillType((fillRule2 == null || fillRule2 != SVG.Style.FillRule.EvenOdd) ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                checkForGradientsAndPatterns(polyLine);
                                checkForClipPath(polyLine, polyLine.boundingBox);
                                boolean pushLayer12 = pushLayer();
                                if (this.state.hasFill) {
                                    doFilledPath(polyLine, makePathAndBoundingBox5);
                                }
                                if (this.state.hasStroke) {
                                    doStroke(makePathAndBoundingBox5);
                                }
                                renderMarkers(polyLine);
                                if (pushLayer12) {
                                    popLayer(polyLine.boundingBox);
                                }
                            }
                        }
                    }
                } else if (svgObject instanceof SVG.Text) {
                    SVG.Text text = (SVG.Text) svgObject;
                    updateStyleForElement(text, this.state);
                    if (display()) {
                        Matrix matrix12 = text.transform;
                        if (matrix12 != null) {
                            this.canvas.concat(matrix12);
                        }
                        List list = text.x;
                        float floatValueX5 = (list == null || ((ArrayList) list).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.x).get(0)).floatValueX(this);
                        List list2 = text.y;
                        float floatValueY2 = (list2 == null || ((ArrayList) list2).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.y).get(0)).floatValueY(this);
                        List list3 = text.dx;
                        float floatValueX6 = (list3 == null || ((ArrayList) list3).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.dx).get(0)).floatValueX(this);
                        List list4 = text.dy;
                        if (list4 != null && ((ArrayList) list4).size() != 0) {
                            r3 = ((SVG.Length) ((ArrayList) text.dy).get(0)).floatValueY(this);
                        }
                        SVG.Style.TextAnchor anchorPosition = getAnchorPosition();
                        if (anchorPosition != SVG.Style.TextAnchor.Start) {
                            float calculateTextWidth = calculateTextWidth(text);
                            if (anchorPosition == SVG.Style.TextAnchor.Middle) {
                                calculateTextWidth /= 2.0f;
                            }
                            floatValueX5 -= calculateTextWidth;
                        }
                        if (text.boundingBox == null) {
                            TextBoundsCalculator textBoundsCalculator = new TextBoundsCalculator(floatValueX5, floatValueY2);
                            enumerateTextSpans(text, textBoundsCalculator);
                            RectF rectF = textBoundsCalculator.bbox;
                            text.boundingBox = new SVG.Box(rectF.left, rectF.top, rectF.width(), textBoundsCalculator.bbox.height());
                        }
                        updateParentBoundingBox(text);
                        checkForGradientsAndPatterns(text);
                        checkForClipPath(text, text.boundingBox);
                        boolean pushLayer13 = pushLayer();
                        enumerateTextSpans(text, new PlainTextDrawer(floatValueX5 + floatValueX6, floatValueY2 + r3));
                        if (pushLayer13) {
                            popLayer(text.boundingBox);
                        }
                    }
                }
            }
        }
        statePop();
    }

    public final void renderChildren(SVG.SvgConditionalContainer svgConditionalContainer, boolean z) {
        if (z) {
            this.parentStack.push(svgConditionalContainer);
            this.matrixStack.push(this.canvas.getMatrix());
        }
        ArrayList arrayList = (ArrayList) svgConditionalContainer.children;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            render((SVG.SvgObject) obj);
        }
        if (z) {
            this.parentStack.pop();
            this.matrixStack.pop();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:52:0x012c, code lost:
    
        if (r7 != 8) goto L79;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void renderMarker(com.caverock.androidsvg.SVG.Marker r12, com.caverock.androidsvg.SVGAndroidRenderer.MarkerVector r13) {
        /*
            Method dump skipped, instructions count: 412
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.renderMarker(com.caverock.androidsvg.SVG$Marker, com.caverock.androidsvg.SVGAndroidRenderer$MarkerVector):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x008f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void renderMarkers(com.caverock.androidsvg.SVG.GraphicsElement r19) {
        /*
            Method dump skipped, instructions count: 522
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.SVGAndroidRenderer.renderMarkers(com.caverock.androidsvg.SVG$GraphicsElement):void");
    }

    public final void renderMask(SVG.Mask mask, SVG.Box box) {
        float f;
        float f2;
        Boolean bool = mask.maskUnitsAreUser;
        if (bool == null || !bool.booleanValue()) {
            SVG.Length length = mask.width;
            float floatValue = length != null ? length.floatValue(this, 1.0f) : 1.2f;
            SVG.Length length2 = mask.height;
            float floatValue2 = length2 != null ? length2.floatValue(this, 1.0f) : 1.2f;
            f = floatValue * box.width;
            f2 = floatValue2 * box.height;
        } else {
            SVG.Length length3 = mask.width;
            f = length3 != null ? length3.floatValueX(this) : box.width;
            SVG.Length length4 = mask.height;
            f2 = length4 != null ? length4.floatValueY(this) : box.height;
        }
        if (f == 0.0f || f2 == 0.0f) {
            return;
        }
        statePush();
        RendererState findInheritFromAncestorState = findInheritFromAncestorState(mask);
        this.state = findInheritFromAncestorState;
        findInheritFromAncestorState.style.opacity = Float.valueOf(1.0f);
        boolean pushLayer = pushLayer();
        this.canvas.save();
        Boolean bool2 = mask.maskContentUnitsAreUser;
        if (bool2 != null && !bool2.booleanValue()) {
            this.canvas.translate(box.minX, box.minY);
            this.canvas.scale(box.width, box.height);
        }
        renderChildren(mask, false);
        this.canvas.restore();
        if (pushLayer) {
            popLayer(box);
        }
        statePop();
    }

    public final void setClipRect(float f, float f2, float f3, float f4) {
        float f5 = f3 + f;
        float f6 = f4 + f2;
        SVG.CSSClipRect cSSClipRect = this.state.style.clip;
        if (cSSClipRect != null) {
            f += cSSClipRect.left.floatValueX(this);
            f2 += this.state.style.clip.top.floatValueY(this);
            f5 -= this.state.style.clip.right.floatValueX(this);
            f6 -= this.state.style.clip.bottom.floatValueY(this);
        }
        this.canvas.clipRect(f, f2, f5, f6);
    }

    public final void statePop() {
        this.canvas.restore();
        this.state = (RendererState) this.stateStack.pop();
    }

    public final void statePush() {
        this.canvas.save();
        this.stateStack.push(this.state);
        this.state = new RendererState(this, this.state);
    }

    public final String textXMLSpaceTransform(String str, boolean z, boolean z2) {
        if (this.state.spacePreserve) {
            return str.replaceAll("[\\n\\t]", " ");
        }
        String replaceAll = str.replaceAll("\\n", "").replaceAll("\\t", " ");
        if (z) {
            replaceAll = replaceAll.replaceAll("^\\s+", "");
        }
        if (z2) {
            replaceAll = replaceAll.replaceAll("\\s+$", "");
        }
        return replaceAll.replaceAll("\\s{2,}", " ");
    }

    public final void updateParentBoundingBox(SVG.SvgElement svgElement) {
        if (svgElement.parent == null || svgElement.boundingBox == null) {
            return;
        }
        Matrix matrix = new Matrix();
        if (((Matrix) this.matrixStack.peek()).invert(matrix)) {
            SVG.Box box = svgElement.boundingBox;
            float f = box.minX;
            float f2 = box.minY;
            float maxX = box.maxX();
            SVG.Box box2 = svgElement.boundingBox;
            float f3 = box2.minY;
            float maxX2 = box2.maxX();
            float maxY = svgElement.boundingBox.maxY();
            SVG.Box box3 = svgElement.boundingBox;
            float[] fArr = {f, f2, maxX, f3, maxX2, maxY, box3.minX, box3.maxY()};
            matrix.preConcat(this.canvas.getMatrix());
            matrix.mapPoints(fArr);
            float f4 = fArr[0];
            float f5 = fArr[1];
            RectF rectF = new RectF(f4, f5, f4, f5);
            for (int i = 2; i <= 6; i += 2) {
                float f6 = fArr[i];
                if (f6 < rectF.left) {
                    rectF.left = f6;
                }
                if (f6 > rectF.right) {
                    rectF.right = f6;
                }
                float f7 = fArr[i + 1];
                if (f7 < rectF.top) {
                    rectF.top = f7;
                }
                if (f7 > rectF.bottom) {
                    rectF.bottom = f7;
                }
            }
            SVG.SvgElement svgElement2 = (SVG.SvgElement) this.parentStack.peek();
            SVG.Box box4 = svgElement2.boundingBox;
            if (box4 == null) {
                float f8 = rectF.left;
                float f9 = rectF.top;
                svgElement2.boundingBox = new SVG.Box(f8, f9, rectF.right - f8, rectF.bottom - f9);
                return;
            }
            float f10 = rectF.left;
            float f11 = rectF.top;
            SVG.Box box5 = new SVG.Box(f10, f11, rectF.right - f10, rectF.bottom - f11);
            float f12 = box5.minX;
            if (f12 < box4.minX) {
                box4.minX = f12;
            }
            float f13 = box5.minY;
            if (f13 < box4.minY) {
                box4.minY = f13;
            }
            if (box5.maxX() > box4.maxX()) {
                box4.width = box5.maxX() - box4.minX;
            }
            if (box5.maxY() > box4.maxY()) {
                box4.height = box5.maxY() - box4.minY;
            }
        }
    }

    public final void updateStyle(RendererState rendererState, SVG.Style style) {
        SVG.Style style2;
        if (isSpecified(style, 4096L)) {
            rendererState.style.color = style.color;
        }
        if (isSpecified(style, 2048L)) {
            rendererState.style.opacity = style.opacity;
        }
        if (isSpecified(style, 1L)) {
            rendererState.style.fill = style.fill;
            SVG.SvgPaint svgPaint = style.fill;
            rendererState.hasFill = (svgPaint == null || svgPaint == SVG.Colour.TRANSPARENT) ? false : true;
        }
        if (isSpecified(style, 4L)) {
            rendererState.style.fillOpacity = style.fillOpacity;
        }
        if (isSpecified(style, 6149L)) {
            setPaintColour(rendererState, true, rendererState.style.fill);
        }
        if (isSpecified(style, 2L)) {
            rendererState.style.fillRule = style.fillRule;
        }
        if (isSpecified(style, 8L)) {
            rendererState.style.stroke = style.stroke;
            SVG.SvgPaint svgPaint2 = style.stroke;
            rendererState.hasStroke = (svgPaint2 == null || svgPaint2 == SVG.Colour.TRANSPARENT) ? false : true;
        }
        if (isSpecified(style, 16L)) {
            rendererState.style.strokeOpacity = style.strokeOpacity;
        }
        if (isSpecified(style, 6168L)) {
            setPaintColour(rendererState, false, rendererState.style.stroke);
        }
        if (isSpecified(style, 34359738368L)) {
            rendererState.style.vectorEffect = style.vectorEffect;
        }
        if (isSpecified(style, 32L)) {
            SVG.Style style3 = rendererState.style;
            SVG.Length length = style.strokeWidth;
            style3.strokeWidth = length;
            rendererState.strokePaint.setStrokeWidth(length.floatValue(this));
        }
        if (isSpecified(style, 64L)) {
            rendererState.style.strokeLineCap = style.strokeLineCap;
            int i = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineCap[style.strokeLineCap.ordinal()];
            if (i == 1) {
                rendererState.strokePaint.setStrokeCap(Paint.Cap.BUTT);
            } else if (i == 2) {
                rendererState.strokePaint.setStrokeCap(Paint.Cap.ROUND);
            } else if (i == 3) {
                rendererState.strokePaint.setStrokeCap(Paint.Cap.SQUARE);
            }
        }
        if (isSpecified(style, 128L)) {
            rendererState.style.strokeLineJoin = style.strokeLineJoin;
            int i2 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[style.strokeLineJoin.ordinal()];
            if (i2 == 1) {
                rendererState.strokePaint.setStrokeJoin(Paint.Join.MITER);
            } else if (i2 == 2) {
                rendererState.strokePaint.setStrokeJoin(Paint.Join.ROUND);
            } else if (i2 == 3) {
                rendererState.strokePaint.setStrokeJoin(Paint.Join.BEVEL);
            }
        }
        if (isSpecified(style, 256L)) {
            rendererState.style.strokeMiterLimit = style.strokeMiterLimit;
            rendererState.strokePaint.setStrokeMiter(style.strokeMiterLimit.floatValue());
        }
        if (isSpecified(style, 512L)) {
            rendererState.style.strokeDashArray = style.strokeDashArray;
        }
        if (isSpecified(style, 1024L)) {
            rendererState.style.strokeDashOffset = style.strokeDashOffset;
        }
        Typeface typeface = null;
        if (isSpecified(style, 1536L)) {
            SVG.Length[] lengthArr = rendererState.style.strokeDashArray;
            if (lengthArr == null) {
                rendererState.strokePaint.setPathEffect(null);
            } else {
                int length2 = lengthArr.length;
                int i3 = length2 % 2 == 0 ? length2 : length2 * 2;
                float[] fArr = new float[i3];
                int i4 = 0;
                float f = 0.0f;
                while (true) {
                    style2 = rendererState.style;
                    if (i4 >= i3) {
                        break;
                    }
                    float floatValue = style2.strokeDashArray[i4 % length2].floatValue(this);
                    fArr[i4] = floatValue;
                    f += floatValue;
                    i4++;
                }
                if (f == 0.0f) {
                    rendererState.strokePaint.setPathEffect(null);
                } else {
                    float floatValue2 = style2.strokeDashOffset.floatValue(this);
                    if (floatValue2 < 0.0f) {
                        floatValue2 = (floatValue2 % f) + f;
                    }
                    rendererState.strokePaint.setPathEffect(new DashPathEffect(fArr, floatValue2));
                }
            }
        }
        if (isSpecified(style, 16384L)) {
            float textSize = this.state.fillPaint.getTextSize();
            rendererState.style.fontSize = style.fontSize;
            rendererState.fillPaint.setTextSize(style.fontSize.floatValue(this, textSize));
            rendererState.strokePaint.setTextSize(style.fontSize.floatValue(this, textSize));
        }
        if (isSpecified(style, 8192L)) {
            rendererState.style.fontFamily = style.fontFamily;
        }
        if (isSpecified(style, 32768L)) {
            if (style.fontWeight.intValue() == -1 && rendererState.style.fontWeight.intValue() > 100) {
                SVG.Style style4 = rendererState.style;
                style4.fontWeight = Integer.valueOf(style4.fontWeight.intValue() - 100);
            } else if (style.fontWeight.intValue() != 1 || rendererState.style.fontWeight.intValue() >= 900) {
                rendererState.style.fontWeight = style.fontWeight;
            } else {
                SVG.Style style5 = rendererState.style;
                style5.fontWeight = Integer.valueOf(style5.fontWeight.intValue() + 100);
            }
        }
        if (isSpecified(style, 65536L)) {
            rendererState.style.fontStyle = style.fontStyle;
        }
        if (isSpecified(style, 106496L)) {
            SVG.Style style6 = rendererState.style;
            List list = style6.fontFamily;
            if (list != null && this.document != null) {
                Iterator it = list.iterator();
                while (it.hasNext() && (typeface = checkGenericFont((String) it.next(), style6.fontWeight, style6.fontStyle)) == null) {
                }
            }
            if (typeface == null) {
                typeface = checkGenericFont("serif", style6.fontWeight, style6.fontStyle);
            }
            rendererState.fillPaint.setTypeface(typeface);
            rendererState.strokePaint.setTypeface(typeface);
        }
        if (isSpecified(style, 131072L)) {
            rendererState.style.textDecoration = style.textDecoration;
            Paint paint = rendererState.fillPaint;
            SVG.Style.TextDecoration textDecoration = style.textDecoration;
            SVG.Style.TextDecoration textDecoration2 = SVG.Style.TextDecoration.LineThrough;
            paint.setStrikeThruText(textDecoration == textDecoration2);
            Paint paint2 = rendererState.fillPaint;
            SVG.Style.TextDecoration textDecoration3 = style.textDecoration;
            SVG.Style.TextDecoration textDecoration4 = SVG.Style.TextDecoration.Underline;
            paint2.setUnderlineText(textDecoration3 == textDecoration4);
            rendererState.strokePaint.setStrikeThruText(style.textDecoration == textDecoration2);
            rendererState.strokePaint.setUnderlineText(style.textDecoration == textDecoration4);
        }
        if (isSpecified(style, 68719476736L)) {
            rendererState.style.direction = style.direction;
        }
        if (isSpecified(style, 262144L)) {
            rendererState.style.textAnchor = style.textAnchor;
        }
        if (isSpecified(style, 524288L)) {
            rendererState.style.overflow = style.overflow;
        }
        if (isSpecified(style, 2097152L)) {
            rendererState.style.markerStart = style.markerStart;
        }
        if (isSpecified(style, 4194304L)) {
            rendererState.style.markerMid = style.markerMid;
        }
        if (isSpecified(style, 8388608L)) {
            rendererState.style.markerEnd = style.markerEnd;
        }
        if (isSpecified(style, 16777216L)) {
            rendererState.style.display = style.display;
        }
        if (isSpecified(style, 33554432L)) {
            rendererState.style.visibility = style.visibility;
        }
        if (isSpecified(style, 1048576L)) {
            rendererState.style.clip = style.clip;
        }
        if (isSpecified(style, 268435456L)) {
            rendererState.style.clipPath = style.clipPath;
        }
        if (isSpecified(style, 536870912L)) {
            rendererState.style.clipRule = style.clipRule;
        }
        if (isSpecified(style, 1073741824L)) {
            rendererState.style.mask = style.mask;
        }
        if (isSpecified(style, 67108864L)) {
            rendererState.style.stopColor = style.stopColor;
        }
        if (isSpecified(style, 134217728L)) {
            rendererState.style.stopOpacity = style.stopOpacity;
        }
        if (isSpecified(style, 8589934592L)) {
            rendererState.style.viewportFill = style.viewportFill;
        }
        if (isSpecified(style, 17179869184L)) {
            rendererState.style.viewportFillOpacity = style.viewportFillOpacity;
        }
        if (isSpecified(style, 137438953472L)) {
            rendererState.style.imageRendering = style.imageRendering;
        }
    }

    public final void updateStyleForElement(SVG.SvgElementBase svgElementBase, RendererState rendererState) {
        int i = 0;
        boolean z = svgElementBase.parent == null;
        SVG.Style style = rendererState.style;
        Boolean bool = Boolean.TRUE;
        style.display = bool;
        if (!z) {
            bool = Boolean.FALSE;
        }
        style.overflow = bool;
        style.clip = null;
        style.clipPath = null;
        style.opacity = Float.valueOf(1.0f);
        style.stopColor = SVG.Colour.BLACK;
        style.stopOpacity = Float.valueOf(1.0f);
        style.mask = null;
        style.solidColor = null;
        style.solidOpacity = Float.valueOf(1.0f);
        style.viewportFill = null;
        style.viewportFillOpacity = Float.valueOf(1.0f);
        style.vectorEffect = SVG.Style.VectorEffect.None;
        SVG.Style style2 = svgElementBase.baseStyle;
        if (style2 != null) {
            updateStyle(rendererState, style2);
        }
        List list = this.document.cssRules.rules;
        if (list != null && !((ArrayList) list).isEmpty()) {
            ArrayList arrayList = (ArrayList) this.document.cssRules.rules;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                CSSParser.Rule rule = (CSSParser.Rule) obj;
                if (CSSParser.ruleMatch(rule.selector, svgElementBase)) {
                    updateStyle(rendererState, rule.style);
                }
            }
        }
        SVG.Style style3 = svgElementBase.style;
        if (style3 != null) {
            updateStyle(rendererState, style3);
        }
    }

    public final void viewportFill() {
        int i;
        SVG.Style style = this.state.style;
        SVG.SvgPaint svgPaint = style.viewportFill;
        if (svgPaint instanceof SVG.Colour) {
            i = ((SVG.Colour) svgPaint).colour;
        } else if (!(svgPaint instanceof SVG.CurrentColor)) {
            return;
        } else {
            i = style.color.colour;
        }
        Float f = style.viewportFillOpacity;
        if (f != null) {
            i = colourWithOpacity(f.floatValue(), i);
        }
        this.canvas.drawColor(i);
    }

    public final boolean visible() {
        Boolean bool = this.state.style.visibility;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class TextWidthCalculator extends TextProcessor {
        public float x;

        private TextWidthCalculator() {
            super(SVGAndroidRenderer.this, null);
            this.x = 0.0f;
        }

        @Override // com.caverock.androidsvg.SVGAndroidRenderer.TextProcessor
        public final void processText(String str) {
            this.x = SVGAndroidRenderer.this.state.fillPaint.measureText(str) + this.x;
        }

        public /* synthetic */ TextWidthCalculator(SVGAndroidRenderer sVGAndroidRenderer, AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.caverock.androidsvg.SVG$SvgObject] */
    public final void findInheritFromAncestorState(SVG.SvgElementBase svgElementBase, RendererState rendererState) {
        int i;
        ArrayList arrayList = new ArrayList();
        SVG.SvgElementBase svgElementBase2 = svgElementBase;
        while (true) {
            i = 0;
            if (svgElementBase2 instanceof SVG.SvgElementBase) {
                arrayList.add(0, svgElementBase2);
            }
            Object obj = svgElementBase2.parent;
            if (obj == null) {
                break;
            } else {
                svgElementBase2 = (SVG.SvgObject) obj;
            }
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            updateStyleForElement((SVG.SvgElementBase) obj2, rendererState);
        }
        RendererState rendererState2 = this.state;
        rendererState.viewBox = rendererState2.viewBox;
        rendererState.viewPort = rendererState2.viewPort;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MarkerVector {
        public float dx;
        public float dy;
        public boolean isAmbiguous = false;
        public final float x;
        public final float y;

        public MarkerVector(SVGAndroidRenderer sVGAndroidRenderer, float f, float f2, float f3, float f4) {
            this.dx = 0.0f;
            this.dy = 0.0f;
            this.x = f;
            this.y = f2;
            double sqrt = Math.sqrt((f4 * f4) + (f3 * f3));
            if (sqrt != 0.0d) {
                this.dx = (float) (f3 / sqrt);
                this.dy = (float) (f4 / sqrt);
            }
        }

        public final void add(float f, float f2) {
            float f3 = f - this.x;
            float f4 = f2 - this.y;
            double sqrt = Math.sqrt((f4 * f4) + (f3 * f3));
            if (sqrt != 0.0d) {
                f3 = (float) (f3 / sqrt);
                f4 = (float) (f4 / sqrt);
            }
            float f5 = this.dx;
            if (f3 != (-f5) || f4 != (-this.dy)) {
                this.dx = f5 + f3;
                this.dy += f4;
            } else {
                this.isAmbiguous = true;
                this.dx = -f4;
                this.dy = f3;
            }
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("(");
            sb.append(this.x);
            sb.append(",");
            sb.append(this.y);
            sb.append(" ");
            sb.append(this.dx);
            sb.append(",");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.dy, ")", sb);
        }

        public final void add(MarkerVector markerVector) {
            float f = markerVector.dx;
            float f2 = this.dx;
            if (f == (-f2)) {
                float f3 = markerVector.dy;
                if (f3 == (-this.dy)) {
                    this.isAmbiguous = true;
                    this.dx = -f3;
                    this.dy = markerVector.dx;
                    return;
                }
            }
            this.dx = f2 + f;
            this.dy += markerVector.dy;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RendererState {
        public final Paint fillPaint;
        public boolean hasFill;
        public boolean hasStroke;
        public boolean spacePreserve;
        public final Paint strokePaint;
        public final SVG.Style style;
        public SVG.Box viewBox;
        public SVG.Box viewPort;

        public RendererState(SVGAndroidRenderer sVGAndroidRenderer) {
            Paint paint = new Paint();
            this.fillPaint = paint;
            paint.setFlags(193);
            paint.setHinting(0);
            paint.setStyle(Paint.Style.FILL);
            Typeface typeface = Typeface.DEFAULT;
            paint.setTypeface(typeface);
            Paint paint2 = new Paint();
            this.strokePaint = paint2;
            paint2.setFlags(193);
            paint2.setHinting(0);
            paint2.setStyle(Paint.Style.STROKE);
            paint2.setTypeface(typeface);
            this.style = SVG.Style.getDefaultStyle();
        }

        public RendererState(SVGAndroidRenderer sVGAndroidRenderer, RendererState rendererState) {
            this.hasFill = rendererState.hasFill;
            this.hasStroke = rendererState.hasStroke;
            this.fillPaint = new Paint(rendererState.fillPaint);
            this.strokePaint = new Paint(rendererState.strokePaint);
            SVG.Box box = rendererState.viewPort;
            if (box != null) {
                this.viewPort = new SVG.Box(box);
            }
            SVG.Box box2 = rendererState.viewBox;
            if (box2 != null) {
                this.viewBox = new SVG.Box(box2);
            }
            this.spacePreserve = rendererState.spacePreserve;
            try {
                this.style = (SVG.Style) rendererState.style.clone();
            } catch (CloneNotSupportedException e) {
                Log.e("SVGAndroidRenderer", "Unexpected clone error", e);
                this.style = SVG.Style.getDefaultStyle();
            }
        }
    }

    public static void fillInChainedGradientFields(SVG.SvgRadialGradient svgRadialGradient, SVG.SvgRadialGradient svgRadialGradient2) {
        if (svgRadialGradient.cx == null) {
            svgRadialGradient.cx = svgRadialGradient2.cx;
        }
        if (svgRadialGradient.cy == null) {
            svgRadialGradient.cy = svgRadialGradient2.cy;
        }
        if (svgRadialGradient.r == null) {
            svgRadialGradient.r = svgRadialGradient2.r;
        }
        if (svgRadialGradient.fx == null) {
            svgRadialGradient.fx = svgRadialGradient2.fx;
        }
        if (svgRadialGradient.fy == null) {
            svgRadialGradient.fy = svgRadialGradient2.fy;
        }
    }

    public final Path makePathAndBoundingBox(SVG.Circle circle) {
        SVG.Length length = circle.cx;
        float floatValueX = length != null ? length.floatValueX(this) : 0.0f;
        SVG.Length length2 = circle.cy;
        float floatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        float floatValue = circle.r.floatValue(this);
        float f = floatValueX - floatValue;
        float f2 = floatValueY - floatValue;
        float f3 = floatValueX + floatValue;
        float f4 = floatValueY + floatValue;
        if (circle.boundingBox == null) {
            float f5 = 2.0f * floatValue;
            circle.boundingBox = new SVG.Box(f, f2, f5, f5);
        }
        float f6 = floatValue * 0.5522848f;
        Path m = PathInterpolator$$ExternalSyntheticOutline0.m(floatValueX, f2);
        float f7 = floatValueX + f6;
        float f8 = floatValueY - f6;
        m.cubicTo(f7, f2, f3, f8, f3, floatValueY);
        float f9 = floatValueY + f6;
        m.cubicTo(f3, f9, f7, f4, floatValueX, f4);
        float f10 = floatValueX - f6;
        m.cubicTo(f10, f4, f, f9, f, floatValueY);
        m.cubicTo(f, f8, f10, f2, floatValueX, f2);
        m.close();
        return m;
    }

    public final Path makePathAndBoundingBox(SVG.Ellipse ellipse) {
        SVG.Length length = ellipse.cx;
        float floatValueX = length != null ? length.floatValueX(this) : 0.0f;
        SVG.Length length2 = ellipse.cy;
        float floatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        float floatValueX2 = ellipse.rx.floatValueX(this);
        float floatValueY2 = ellipse.ry.floatValueY(this);
        float f = floatValueX - floatValueX2;
        float f2 = floatValueY - floatValueY2;
        float f3 = floatValueX + floatValueX2;
        float f4 = floatValueY + floatValueY2;
        if (ellipse.boundingBox == null) {
            ellipse.boundingBox = new SVG.Box(f, f2, floatValueX2 * 2.0f, 2.0f * floatValueY2);
        }
        float f5 = floatValueX2 * 0.5522848f;
        float f6 = floatValueY2 * 0.5522848f;
        Path m = PathInterpolator$$ExternalSyntheticOutline0.m(floatValueX, f2);
        float f7 = floatValueX + f5;
        float f8 = floatValueY - f6;
        m.cubicTo(f7, f2, f3, f8, f3, floatValueY);
        float f9 = floatValueY + f6;
        m.cubicTo(f3, f9, f7, f4, floatValueX, f4);
        float f10 = floatValueX - f5;
        m.cubicTo(f10, f4, f, f9, f, floatValueY);
        m.cubicTo(f, f8, f10, f2, floatValueX, f2);
        m.close();
        return m;
    }

    public static Path makePathAndBoundingBox(SVG.PolyLine polyLine) {
        Path path = new Path();
        float[] fArr = polyLine.points;
        path.moveTo(fArr[0], fArr[1]);
        int i = 2;
        while (true) {
            float[] fArr2 = polyLine.points;
            if (i >= fArr2.length) {
                break;
            }
            path.lineTo(fArr2[i], fArr2[i + 1]);
            i += 2;
        }
        if (polyLine instanceof SVG.Polygon) {
            path.close();
        }
        if (polyLine.boundingBox == null) {
            polyLine.boundingBox = calculatePathBounds(path);
        }
        return path;
    }

    public final void render(SVG.Svg svg, SVG.Box box, SVG.Box box2, PreserveAspectRatio preserveAspectRatio) {
        if (box.width == 0.0f || box.height == 0.0f) {
            return;
        }
        if (preserveAspectRatio == null && (preserveAspectRatio = svg.preserveAspectRatio) == null) {
            preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
        }
        updateStyleForElement(svg, this.state);
        if (display()) {
            RendererState rendererState = this.state;
            rendererState.viewPort = box;
            if (!rendererState.style.overflow.booleanValue()) {
                SVG.Box box3 = this.state.viewPort;
                setClipRect(box3.minX, box3.minY, box3.width, box3.height);
            }
            checkForClipPath(svg, this.state.viewPort);
            if (box2 != null) {
                this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, box2, preserveAspectRatio));
                this.state.viewBox = svg.viewBox;
            } else {
                Canvas canvas = this.canvas;
                SVG.Box box4 = this.state.viewPort;
                canvas.translate(box4.minX, box4.minY);
            }
            boolean pushLayer = pushLayer();
            viewportFill();
            renderChildren(svg, true);
            if (pushLayer) {
                popLayer(svg.boundingBox);
            }
            updateParentBoundingBox(svg);
        }
    }
}
