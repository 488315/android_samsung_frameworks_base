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
            SVG.SvgElementBase svgElementBaseResolveIRI = textContainer.document.resolveIRI(textPath.href);
            if (svgElementBaseResolveIRI == null) {
                SVGAndroidRenderer.error("TextPath path reference '%s' not found", textPath.href);
                return false;
            }
            SVG.Path path = (SVG.Path) svgElementBaseResolveIRI;
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
        float fAbs = Math.abs(f3);
        float fAbs2 = Math.abs(f4);
        double radians = Math.toRadians(f5 % 360.0d);
        double dCos = Math.cos(radians);
        double dSin = Math.sin(radians);
        double d = (f - f6) / 2.0d;
        double d2 = (f2 - f7) / 2.0d;
        double d3 = (dSin * d2) + (dCos * d);
        double d4 = (dCos * d2) + ((-dSin) * d);
        double d5 = fAbs * fAbs;
        double d6 = fAbs2 * fAbs2;
        double d7 = d3 * d3;
        double d8 = d4 * d4;
        double d9 = (d8 / d6) + (d7 / d5);
        if (d9 > 0.99999d) {
            double dSqrt = Math.sqrt(d9) * 1.00001d;
            fAbs = (float) (fAbs * dSqrt);
            fAbs2 = (float) (dSqrt * fAbs2);
            d5 = fAbs * fAbs;
            d6 = fAbs2 * fAbs2;
        }
        double d10 = z == z2 ? -1.0d : 1.0d;
        double d11 = d5 * d6;
        double d12 = d5 * d8;
        double d13 = d6 * d7;
        double d14 = ((d11 - d12) - d13) / (d12 + d13);
        if (d14 < 0.0d) {
            d14 = 0.0d;
        }
        double dSqrt2 = Math.sqrt(d14) * d10;
        double d15 = fAbs;
        double d16 = fAbs2;
        double d17 = ((d15 * d4) / d16) * dSqrt2;
        double d18 = dSqrt2 * (-((d16 * d3) / d15));
        double d19 = ((dCos * d17) - (dSin * d18)) + ((f + f6) / 2.0d);
        double d20 = (dCos * d18) + (dSin * d17) + ((f2 + f7) / 2.0d);
        double d21 = (d3 - d17) / d15;
        double d22 = (d4 - d18) / d16;
        double d23 = ((-d3) - d17) / d15;
        double d24 = ((-d4) - d18) / d16;
        double d25 = (d22 * d22) + (d21 * d21);
        double dAcos = Math.acos(d21 / Math.sqrt(d25)) * (d22 < 0.0d ? -1.0d : 1.0d);
        double dSqrt3 = ((d22 * d24) + (d21 * d23)) / Math.sqrt(((d24 * d24) + (d23 * d23)) * d25);
        double dAcos2 = ((d21 * d24) - (d22 * d23) < 0.0d ? -1.0d : 1.0d) * (dSqrt3 < -1.0d ? 3.141592653589793d : dSqrt3 > 1.0d ? 0.0d : Math.acos(dSqrt3));
        if (!z2 && dAcos2 > 0.0d) {
            dAcos2 -= 6.283185307179586d;
        } else if (z2 && dAcos2 < 0.0d) {
            dAcos2 += 6.283185307179586d;
        }
        double d26 = dAcos2 % 6.283185307179586d;
        double d27 = dAcos % 6.283185307179586d;
        int iCeil = (int) Math.ceil((Math.abs(d26) * 2.0d) / 3.141592653589793d);
        double d28 = d26 / iCeil;
        double d29 = d28 / 2.0d;
        double dSin2 = (Math.sin(d29) * 1.3333333333333333d) / (Math.cos(d29) + 1.0d);
        int i = iCeil * 6;
        float[] fArr = new float[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < iCeil) {
            double d30 = d27;
            double d31 = (i2 * d28) + d30;
            double dCos2 = Math.cos(d31);
            double dSin3 = Math.sin(d31);
            int i4 = i2;
            int i5 = i3;
            fArr[i5] = (float) (dCos2 - (dSin2 * dSin3));
            fArr[i3 + 1] = (float) ((dCos2 * dSin2) + dSin3);
            double d32 = d31 + d28;
            double dCos3 = Math.cos(d32);
            double dSin4 = Math.sin(d32);
            fArr[i5 + 2] = (float) ((dSin2 * dSin4) + dCos3);
            fArr[i5 + 3] = (float) (dSin4 - (dSin2 * dCos3));
            fArr[i5 + 4] = (float) dCos3;
            i3 = i5 + 6;
            fArr[i5 + 5] = (float) dSin4;
            i2 = i4 + 1;
            d27 = d30;
            iCeil = iCeil;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(fAbs, fAbs2);
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

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0077, code lost:
    
        if (r1 != 8) goto L37;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Matrix calculateViewBoxTransform(SVG.Box box, SVG.Box box2, PreserveAspectRatio preserveAspectRatio) {
        PreserveAspectRatio.Alignment alignment;
        int i;
        float f;
        float f2;
        Matrix matrix = new Matrix();
        if (preserveAspectRatio != null && (alignment = preserveAspectRatio.alignment) != null) {
            float f3 = box.width / box2.width;
            float f4 = box.height / box2.height;
            float f5 = -box2.minX;
            float f6 = -box2.minY;
            if (preserveAspectRatio.equals(PreserveAspectRatio.STRETCH)) {
                matrix.preTranslate(box.minX, box.minY);
                matrix.preScale(f3, f4);
                matrix.preTranslate(f5, f6);
                return matrix;
            }
            float fMax = preserveAspectRatio.scale == PreserveAspectRatio.Scale.slice ? Math.max(f3, f4) : Math.min(f3, f4);
            float f7 = box.width / fMax;
            float f8 = box.height / fMax;
            int[] iArr = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment;
            switch (iArr[alignment.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    f2 = (box2.width - f7) / 2.0f;
                    break;
                case 4:
                case 5:
                case 6:
                    f2 = box2.width - f7;
                    break;
                default:
                    i = iArr[alignment.ordinal()];
                    if (i == 2) {
                        f = (box2.height - f8) / 2.0f;
                        f6 -= f;
                        matrix.preTranslate(box.minX, box.minY);
                        matrix.preScale(fMax, fMax);
                        matrix.preTranslate(f5, f6);
                        break;
                    } else {
                        if (i != 3) {
                            if (i != 5) {
                                if (i != 6) {
                                    if (i != 7) {
                                        break;
                                    }
                                }
                            }
                            f = (box2.height - f8) / 2.0f;
                            f6 -= f;
                            matrix.preTranslate(box.minX, box.minY);
                            matrix.preScale(fMax, fMax);
                            matrix.preTranslate(f5, f6);
                        }
                        f = box2.height - f8;
                        f6 -= f;
                        matrix.preTranslate(box.minX, box.minY);
                        matrix.preScale(fMax, fMax);
                        matrix.preTranslate(f5, f6);
                    }
            }
            f5 -= f2;
            i = iArr[alignment.ordinal()];
            if (i == 2) {
            }
        }
        return matrix;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Typeface checkGenericFont(String str, Integer num, SVG.Style.FontStyle fontStyle) {
        char c = 2;
        boolean z = fontStyle == SVG.Style.FontStyle.Italic;
        int i = num.intValue() > 500 ? z ? 3 : 1 : z ? 2 : 0;
        str.getClass();
        switch (str.hashCode()) {
            case -1536685117:
                if (!str.equals("sans-serif")) {
                    c = 65535;
                    break;
                } else {
                    c = 0;
                    break;
                }
            case -1431958525:
                if (str.equals("monospace")) {
                    c = 1;
                    break;
                }
                break;
            case -1081737434:
                if (!str.equals("fantasy")) {
                }
                break;
            case 109326717:
                if (str.equals("serif")) {
                    c = 3;
                    break;
                }
                break;
            case 1126973893:
                if (str.equals("cursive")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Typeface.create(Typeface.SANS_SERIF, i);
            case 1:
                return Typeface.create(Typeface.MONOSPACE, i);
            case 2:
                return Typeface.create(Typeface.SANS_SERIF, i);
            case 3:
                return Typeface.create(Typeface.SERIF, i);
            case 4:
                return Typeface.create(Typeface.SANS_SERIF, i);
            default:
                return null;
        }
    }

    public static int colourWithOpacity(float f, int i) {
        int i2 = 255;
        int iRound = Math.round(((i >> 24) & 255) * f);
        if (iRound < 0) {
            i2 = 0;
        } else if (iRound <= 255) {
            i2 = iRound;
        }
        return (i2 << 24) | (i & 16777215);
    }

    public static void error(String str, Object... objArr) {
        Log.e("SVGAndroidRenderer", String.format(str, objArr));
    }

    public static void fillInChainedGradientFields(SVG.GradientElement gradientElement, String str) {
        SVG.SvgElementBase svgElementBaseResolveIRI = gradientElement.document.resolveIRI(str);
        if (svgElementBaseResolveIRI == null) {
            Log.w("SVGAndroidRenderer", "Gradient reference '" + str + "' not found");
            return;
        }
        if (!(svgElementBaseResolveIRI instanceof SVG.GradientElement)) {
            error("Gradient href attributes must point to other gradient elements", new Object[0]);
            return;
        }
        if (svgElementBaseResolveIRI == gradientElement) {
            error("Circular reference in gradient href attribute '%s'", str);
            return;
        }
        SVG.GradientElement gradientElement2 = (SVG.GradientElement) svgElementBaseResolveIRI;
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
                SVG.SvgLinearGradient svgLinearGradient2 = (SVG.SvgLinearGradient) svgElementBaseResolveIRI;
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
                fillInChainedGradientFields((SVG.SvgRadialGradient) gradientElement, (SVG.SvgRadialGradient) svgElementBaseResolveIRI);
            }
        } catch (ClassCastException unused) {
        }
        String str2 = gradientElement2.href;
        if (str2 != null) {
            fillInChainedGradientFields(gradientElement, str2);
        }
    }

    public static void fillInChainedPatternFields(SVG.Pattern pattern, String str) {
        SVG.SvgElementBase svgElementBaseResolveIRI = pattern.document.resolveIRI(str);
        if (svgElementBaseResolveIRI == null) {
            Log.w("SVGAndroidRenderer", "Pattern reference '" + str + "' not found");
            return;
        }
        if (!(svgElementBaseResolveIRI instanceof SVG.Pattern)) {
            error("Pattern href attributes must point to other pattern elements", new Object[0]);
            return;
        }
        if (svgElementBaseResolveIRI == pattern) {
            error("Circular reference in pattern href attribute '%s'", str);
            return;
        }
        SVG.Pattern pattern2 = (SVG.Pattern) svgElementBaseResolveIRI;
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
        float fFloatValue = (z ? style.fillOpacity : style.strokeOpacity).floatValue();
        if (svgPaint instanceof SVG.Colour) {
            i = ((SVG.Colour) svgPaint).colour;
        } else if (!(svgPaint instanceof SVG.CurrentColor)) {
            return;
        } else {
            i = rendererState.style.color.colour;
        }
        int iColourWithOpacity = colourWithOpacity(fFloatValue, i);
        if (z) {
            rendererState.fillPaint.setColor(iColourWithOpacity);
        } else {
            rendererState.strokePaint.setColor(iColourWithOpacity);
        }
    }

    public final Path calculateClipPath(SVG.SvgElement svgElement, SVG.Box box) {
        Path pathObjectToPath;
        SVG.SvgElementBase svgElementBaseResolveIRI = svgElement.document.resolveIRI(this.state.style.clipPath);
        if (svgElementBaseResolveIRI == null) {
            error("ClipPath reference '%s' not found", this.state.style.clipPath);
            return null;
        }
        SVG.ClipPath clipPath = (SVG.ClipPath) svgElementBaseResolveIRI;
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
            if ((svgObject instanceof SVG.SvgElement) && (pathObjectToPath = objectToPath((SVG.SvgElement) svgObject, true)) != null) {
                path.op(pathObjectToPath, Path.Op.UNION);
            }
        }
        if (this.state.style.clipPath != null) {
            if (clipPath.boundingBox == null) {
                clipPath.boundingBox = calculatePathBounds(path);
            }
            Path pathCalculateClipPath = calculateClipPath(clipPath, clipPath.boundingBox);
            if (pathCalculateClipPath != null) {
                path.op(pathCalculateClipPath, Path.Op.INTERSECT);
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
        Path pathCalculateClipPath;
        if (this.state.style.clipPath == null || (pathCalculateClipPath = calculateClipPath(svgElement, box)) == null) {
            return;
        }
        this.canvas.clipPath(pathCalculateClipPath);
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
        float fFloatValue;
        float f2;
        float fFloatValue2;
        float f3;
        float fFloatValue3;
        float f4;
        float f5;
        SVG.SvgElementBase svgElementBaseResolveIRI = this.document.resolveIRI(paintReference.href);
        if (svgElementBaseResolveIRI == null) {
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
        if (svgElementBaseResolveIRI instanceof SVG.SvgLinearGradient) {
            SVG.SvgLinearGradient svgLinearGradient = (SVG.SvgLinearGradient) svgElementBaseResolveIRI;
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
                float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
                SVG.Length length2 = svgLinearGradient.y1;
                fFloatValue2 = length2 != null ? length2.floatValueY(this) : 0.0f;
                SVG.Length length3 = svgLinearGradient.x2;
                float fFloatValueX2 = length3 != null ? length3.floatValueX(this) : box2.width;
                SVG.Length length4 = svgLinearGradient.y2;
                f4 = fFloatValueX;
                f5 = fFloatValueX2;
                fFloatValue3 = length4 != null ? length4.floatValueY(this) : 0.0f;
                f3 = 256.0f;
            } else {
                SVG.Length length5 = svgLinearGradient.x1;
                float fFloatValue4 = length5 != null ? length5.floatValue(this, 1.0f) : 0.0f;
                SVG.Length length6 = svgLinearGradient.y1;
                fFloatValue2 = length6 != null ? length6.floatValue(this, 1.0f) : 0.0f;
                SVG.Length length7 = svgLinearGradient.x2;
                float fFloatValue5 = length7 != null ? length7.floatValue(this, 1.0f) : 1.0f;
                f3 = 256.0f;
                SVG.Length length8 = svgLinearGradient.y2;
                fFloatValue3 = length8 != null ? length8.floatValue(this, 1.0f) : 0.0f;
                f4 = fFloatValue4;
                f5 = fFloatValue5;
            }
            float f7 = fFloatValue2;
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
                float fFloatValue6 = f8 != null ? f8.floatValue() : 0.0f;
                if (i == 0 || fFloatValue6 >= f6) {
                    fArr[i] = fFloatValue6;
                    f6 = fFloatValue6;
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
            if ((f4 == f5 && f7 == fFloatValue3) || size == 1) {
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
            LinearGradient linearGradient = new LinearGradient(f4, f7, f5, fFloatValue3, iArr, fArr, tileMode2);
            linearGradient.setLocalMatrix(matrix);
            paint.setShader(linearGradient);
            int iFloatValue = (int) (this.state.style.fillOpacity.floatValue() * f3);
            paint.setAlpha(iFloatValue < 0 ? 0 : iFloatValue > 255 ? 255 : iFloatValue);
            return;
        }
        if (!(svgElementBaseResolveIRI instanceof SVG.SvgRadialGradient)) {
            if (svgElementBaseResolveIRI instanceof SVG.SolidColor) {
                SVG.SolidColor solidColor = (SVG.SolidColor) svgElementBaseResolveIRI;
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
        SVG.SvgRadialGradient svgRadialGradient = (SVG.SvgRadialGradient) svgElementBaseResolveIRI;
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
            float fFloatValueX3 = length10 != null ? length10.floatValueX(this) : length9.floatValueX(this);
            SVG.Length length11 = svgRadialGradient.cy;
            float fFloatValueY = length11 != null ? length11.floatValueY(this) : length9.floatValueY(this);
            SVG.Length length12 = svgRadialGradient.r;
            fFloatValue = length12 != null ? length12.floatValue(this) : length9.floatValue(this);
            f = fFloatValueX3;
            f2 = fFloatValueY;
        } else {
            SVG.Length length13 = svgRadialGradient.cx;
            float fFloatValue7 = length13 != null ? length13.floatValue(this, 1.0f) : 0.5f;
            SVG.Length length14 = svgRadialGradient.cy;
            float fFloatValue8 = length14 != null ? length14.floatValue(this, 1.0f) : 0.5f;
            SVG.Length length15 = svgRadialGradient.r;
            f = fFloatValue7;
            fFloatValue = length15 != null ? length15.floatValue(this, 1.0f) : 0.5f;
            f2 = fFloatValue8;
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
            float fFloatValue9 = f9 != null ? f9.floatValue() : 0.0f;
            if (i4 == 0 || fFloatValue9 >= f6) {
                fArr2[i4] = fFloatValue9;
                f6 = fFloatValue9;
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
        if (fFloatValue == 0.0f || size3 == 1) {
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
        RadialGradient radialGradient = new RadialGradient(f, f2, fFloatValue, iArr2, fArr2, tileMode4);
        radialGradient.setLocalMatrix(matrix3);
        paint2.setShader(radialGradient);
        int iFloatValue2 = (int) (this.state.style.fillOpacity.floatValue() * 256.0f);
        if (iFloatValue2 < 0) {
            iFloatValue2 = 0;
        } else if (iFloatValue2 > 255) {
            iFloatValue2 = 255;
        }
        paint2.setAlpha(iFloatValue2);
    }

    public final boolean display() {
        Boolean bool = this.state.style.display;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:79:0x0177  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void doFilledPath(SVG.SvgElement svgElement, Path path) {
        float fFloatValueX;
        float fFloatValueY;
        float fFloatValueY2;
        float fFloatValueX2;
        int i;
        int i2;
        float f;
        SVG.SvgPaint svgPaint = this.state.style.fill;
        if (svgPaint instanceof SVG.PaintReference) {
            SVG.SvgElementBase svgElementBaseResolveIRI = this.document.resolveIRI(((SVG.PaintReference) svgPaint).href);
            if (svgElementBaseResolveIRI instanceof SVG.Pattern) {
                SVG.Pattern pattern = (SVG.Pattern) svgElementBaseResolveIRI;
                Boolean bool = pattern.patternUnitsAreUser;
                boolean z = bool != null && bool.booleanValue();
                String str = pattern.href;
                if (str != null) {
                    fillInChainedPatternFields(pattern, str);
                }
                if (z) {
                    SVG.Length length = pattern.x;
                    fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
                    SVG.Length length2 = pattern.y;
                    fFloatValueY2 = length2 != null ? length2.floatValueY(this) : 0.0f;
                    SVG.Length length3 = pattern.width;
                    fFloatValueX2 = length3 != null ? length3.floatValueX(this) : 0.0f;
                    SVG.Length length4 = pattern.height;
                    fFloatValueY = length4 != null ? length4.floatValueY(this) : 0.0f;
                } else {
                    SVG.Length length5 = pattern.x;
                    float fFloatValue = length5 != null ? length5.floatValue(this, 1.0f) : 0.0f;
                    SVG.Length length6 = pattern.y;
                    float fFloatValue2 = length6 != null ? length6.floatValue(this, 1.0f) : 0.0f;
                    SVG.Length length7 = pattern.width;
                    float fFloatValue3 = length7 != null ? length7.floatValue(this, 1.0f) : 0.0f;
                    SVG.Length length8 = pattern.height;
                    float fFloatValue4 = length8 != null ? length8.floatValue(this, 1.0f) : 0.0f;
                    SVG.Box box = svgElement.boundingBox;
                    float f2 = box.minX;
                    float f3 = box.width;
                    fFloatValueX = (fFloatValue * f3) + f2;
                    float f4 = box.minY;
                    float f5 = box.height;
                    float f6 = fFloatValue3 * f3;
                    fFloatValueY = fFloatValue4 * f5;
                    fFloatValueY2 = (fFloatValue2 * f5) + f4;
                    fFloatValueX2 = f6;
                }
                if (fFloatValueX2 == 0.0f || fFloatValueY == 0.0f) {
                    return;
                }
                PreserveAspectRatio preserveAspectRatio = pattern.preserveAspectRatio;
                if (preserveAspectRatio == null) {
                    preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
                }
                statePush();
                this.canvas.clipPath(path);
                RendererState rendererState = new RendererState(this);
                updateStyle(rendererState, SVG.Style.getDefaultStyle());
                rendererState.style.overflow = Boolean.FALSE;
                findInheritFromAncestorState(pattern, rendererState);
                this.state = rendererState;
                SVG.Box box2 = svgElement.boundingBox;
                Matrix matrix = pattern.patternTransform;
                if (matrix != null) {
                    this.canvas.concat(matrix);
                    Matrix matrix2 = new Matrix();
                    if (pattern.patternTransform.invert(matrix2)) {
                        SVG.Box box3 = svgElement.boundingBox;
                        float f7 = box3.minX;
                        i = 0;
                        float f8 = box3.minY;
                        float fMaxX = box3.maxX();
                        SVG.Box box4 = svgElement.boundingBox;
                        i2 = 1;
                        float f9 = box4.minY;
                        float fMaxX2 = box4.maxX();
                        float fMaxY = svgElement.boundingBox.maxY();
                        SVG.Box box5 = svgElement.boundingBox;
                        float[] fArr = {f7, f8, fMaxX, f9, fMaxX2, fMaxY, box5.minX, box5.maxY()};
                        matrix2.mapPoints(fArr);
                        float f10 = fArr[0];
                        float f11 = fArr[1];
                        RectF rectF = new RectF(f10, f11, f10, f11);
                        for (int i3 = 2; i3 <= 6; i3 += 2) {
                            float f12 = fArr[i3];
                            if (f12 < rectF.left) {
                                rectF.left = f12;
                            }
                            if (f12 > rectF.right) {
                                rectF.right = f12;
                            }
                            float f13 = fArr[i3 + 1];
                            if (f13 < rectF.top) {
                                rectF.top = f13;
                            }
                            if (f13 > rectF.bottom) {
                                rectF.bottom = f13;
                            }
                        }
                        float f14 = rectF.left;
                        float f15 = rectF.top;
                        box2 = new SVG.Box(f14, f15, rectF.right - f14, rectF.bottom - f15);
                    } else {
                        i = 0;
                        i2 = 1;
                    }
                }
                float fFloor = (((float) Math.floor((box2.minX - fFloatValueX) / fFloatValueX2)) * fFloatValueX2) + fFloatValueX;
                float fMaxX3 = box2.maxX();
                float fMaxY2 = box2.maxY();
                SVG.Box box6 = new SVG.Box(0.0f, 0.0f, fFloatValueX2, fFloatValueY);
                boolean zPushLayer = pushLayer();
                for (float fFloor2 = (((float) Math.floor((box2.minY - fFloatValueY2) / fFloatValueY)) * fFloatValueY) + fFloatValueY2; fFloor2 < fMaxY2; fFloor2 += fFloatValueY) {
                    float f16 = fFloor;
                    while (f16 < fMaxX3) {
                        box6.minX = f16;
                        box6.minY = fFloor2;
                        statePush();
                        if (this.state.style.overflow.booleanValue()) {
                            f = fMaxY2;
                        } else {
                            f = fMaxY2;
                            setClipRect(box6.minX, box6.minY, box6.width, box6.height);
                        }
                        SVG.Box box7 = pattern.viewBox;
                        if (box7 != null) {
                            this.canvas.concat(calculateViewBoxTransform(box6, box7, preserveAspectRatio));
                        } else {
                            Boolean bool2 = pattern.patternContentUnitsAreUser;
                            int i4 = (bool2 == null || bool2.booleanValue()) ? i2 : i;
                            this.canvas.translate(f16, fFloor2);
                            if (i4 == 0) {
                                Canvas canvas = this.canvas;
                                SVG.Box box8 = svgElement.boundingBox;
                                canvas.scale(box8.width, box8.height);
                            }
                        }
                        ArrayList arrayList = (ArrayList) pattern.children;
                        int size = arrayList.size();
                        int i5 = i;
                        while (i5 < size) {
                            Object obj = arrayList.get(i5);
                            i5++;
                            render((SVG.SvgObject) obj);
                        }
                        statePop();
                        f16 += fFloatValueX2;
                        fMaxY2 = f;
                    }
                }
                if (zPushLayer) {
                    popLayer(pattern.boundingBox);
                }
                statePop();
                return;
            }
        }
        this.canvas.drawPath(path, this.state.fillPaint);
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
        float fFloatValueY;
        float fFloatValueX;
        SVG.Style.TextAnchor anchorPosition;
        if (display()) {
            Iterator it = ((ArrayList) textContainer.children).iterator();
            boolean z = true;
            while (it.hasNext()) {
                SVG.SvgObject svgObject = (SVG.SvgObject) it.next();
                if (svgObject instanceof SVG.TextSequence) {
                    textProcessor.processText(textXMLSpaceTransform(((SVG.TextSequence) svgObject).text, z, !it.hasNext()));
                } else if (textProcessor.doTextContainer((SVG.TextContainer) svgObject)) {
                    float fFloatValueY2 = 0.0f;
                    if (svgObject instanceof SVG.TextPath) {
                        statePush();
                        SVG.TextPath textPath = (SVG.TextPath) svgObject;
                        updateStyleForElement(textPath, this.state);
                        if (display() && visible()) {
                            SVG.SvgElementBase svgElementBaseResolveIRI = textPath.document.resolveIRI(textPath.href);
                            if (svgElementBaseResolveIRI == null) {
                                error("TextPath reference '%s' not found", textPath.href);
                            } else {
                                SVG.Path path = (SVG.Path) svgElementBaseResolveIRI;
                                Path path2 = new PathConverter(this, path.d).path;
                                Matrix matrix = path.transform;
                                if (matrix != null) {
                                    path2.transform(matrix);
                                }
                                PathMeasure pathMeasure = new PathMeasure(path2, false);
                                SVG.Length length = textPath.startOffset;
                                float fFloatValue = length != null ? length.floatValue(this, pathMeasure.getLength()) : 0.0f;
                                SVG.Style.TextAnchor anchorPosition2 = getAnchorPosition();
                                if (anchorPosition2 != SVG.Style.TextAnchor.Start) {
                                    float fCalculateTextWidth = calculateTextWidth(textPath);
                                    if (anchorPosition2 == SVG.Style.TextAnchor.Middle) {
                                        fCalculateTextWidth /= 2.0f;
                                    }
                                    fFloatValue -= fCalculateTextWidth;
                                }
                                checkForGradientsAndPatterns(textPath.textRoot);
                                boolean zPushLayer = pushLayer();
                                enumerateTextSpans(textPath, new PathTextDrawer(path2, fFloatValue, 0.0f));
                                if (zPushLayer) {
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
                                float fFloatValueX2 = !z2 ? ((PlainTextDrawer) textProcessor).x : ((SVG.Length) ((ArrayList) tSpan.x).get(0)).floatValueX(this);
                                List list2 = tSpan.y;
                                fFloatValueY = (list2 == null || ((ArrayList) list2).size() == 0) ? ((PlainTextDrawer) textProcessor).y : ((SVG.Length) ((ArrayList) tSpan.y).get(0)).floatValueY(this);
                                List list3 = tSpan.dx;
                                fFloatValueX = (list3 == null || ((ArrayList) list3).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) tSpan.dx).get(0)).floatValueX(this);
                                List list4 = tSpan.dy;
                                if (list4 != null && ((ArrayList) list4).size() != 0) {
                                    fFloatValueY2 = ((SVG.Length) ((ArrayList) tSpan.dy).get(0)).floatValueY(this);
                                }
                                float f2 = fFloatValueX2;
                                f = fFloatValueY2;
                                fFloatValueY2 = f2;
                            } else {
                                f = 0.0f;
                                fFloatValueY = 0.0f;
                                fFloatValueX = 0.0f;
                            }
                            if (z2 && (anchorPosition = getAnchorPosition()) != SVG.Style.TextAnchor.Start) {
                                float fCalculateTextWidth2 = calculateTextWidth(tSpan);
                                if (anchorPosition == SVG.Style.TextAnchor.Middle) {
                                    fCalculateTextWidth2 /= 2.0f;
                                }
                                fFloatValueY2 -= fCalculateTextWidth2;
                            }
                            checkForGradientsAndPatterns(tSpan.textRoot);
                            if (z3) {
                                PlainTextDrawer plainTextDrawer = (PlainTextDrawer) textProcessor;
                                plainTextDrawer.x = fFloatValueY2 + fFloatValueX;
                                plainTextDrawer.y = fFloatValueY + f;
                            }
                            boolean zPushLayer2 = pushLayer();
                            enumerateTextSpans(tSpan, textProcessor);
                            if (zPushLayer2) {
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
                            SVG.SvgElementBase svgElementBaseResolveIRI2 = svgObject.document.resolveIRI(tRef.href);
                            if (svgElementBaseResolveIRI2 == null || !(svgElementBaseResolveIRI2 instanceof SVG.TextContainer)) {
                                error("Tref reference '%s' not found", tRef.href);
                            } else {
                                StringBuilder sb = new StringBuilder();
                                extractRawText((SVG.TextContainer) svgElementBaseResolveIRI2, sb);
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Path makePathAndBoundingBox(SVG.Rect rect) {
        float fFloatValueX;
        float fFloatValueY;
        float fMin;
        float fFloatValueX2;
        float fFloatValueY2;
        float f;
        float f2;
        Path path;
        SVG.Length length = rect.rx;
        if (length == null && rect.ry == null) {
            fFloatValueX = 0.0f;
        } else if (length == null) {
            fFloatValueX = rect.ry.floatValueY(this);
        } else {
            if (rect.ry != null) {
                fFloatValueX = length.floatValueX(this);
                fFloatValueY = rect.ry.floatValueY(this);
                fMin = Math.min(fFloatValueX, rect.width.floatValueX(this) / 2.0f);
                float fMin2 = Math.min(fFloatValueY, rect.height.floatValueY(this) / 2.0f);
                SVG.Length length2 = rect.x;
                fFloatValueX2 = length2 == null ? length2.floatValueX(this) : 0.0f;
                SVG.Length length3 = rect.y;
                fFloatValueY2 = length3 == null ? length3.floatValueY(this) : 0.0f;
                float fFloatValueX3 = rect.width.floatValueX(this);
                float fFloatValueY3 = rect.height.floatValueY(this);
                if (rect.boundingBox == null) {
                    rect.boundingBox = new SVG.Box(fFloatValueX2, fFloatValueY2, fFloatValueX3, fFloatValueY3);
                }
                f = fFloatValueX3 + fFloatValueX2;
                f2 = fFloatValueY2 + fFloatValueY3;
                path = new Path();
                if (fMin != 0.0f || fMin2 == 0.0f) {
                    path.moveTo(fFloatValueX2, fFloatValueY2);
                    path.lineTo(f, fFloatValueY2);
                    path.lineTo(f, f2);
                    path.lineTo(fFloatValueX2, f2);
                    path.lineTo(fFloatValueX2, fFloatValueY2);
                } else {
                    float f3 = fMin * 0.5522848f;
                    float f4 = 0.5522848f * fMin2;
                    float f5 = fFloatValueY2 + fMin2;
                    path.moveTo(fFloatValueX2, f5);
                    float f6 = f5 - f4;
                    float f7 = fFloatValueX2 + fMin;
                    float f8 = f7 - f3;
                    path.cubicTo(fFloatValueX2, f6, f8, fFloatValueY2, f7, fFloatValueY2);
                    float f9 = f - fMin;
                    path.lineTo(f9, fFloatValueY2);
                    float f10 = f9 + f3;
                    path.cubicTo(f10, fFloatValueY2, f, f6, f, f5);
                    float f11 = f2 - fMin2;
                    path.lineTo(f, f11);
                    float f12 = f11 + f4;
                    path.cubicTo(f, f12, f10, f2, f9, f2);
                    path.lineTo(f7, f2);
                    float f13 = fFloatValueX2;
                    path.cubicTo(f8, f2, f13, f12, fFloatValueX2, f11);
                    path.lineTo(f13, f5);
                }
                path.close();
                return path;
            }
            fFloatValueX = length.floatValueX(this);
        }
        fFloatValueY = fFloatValueX;
        fMin = Math.min(fFloatValueX, rect.width.floatValueX(this) / 2.0f);
        float fMin22 = Math.min(fFloatValueY, rect.height.floatValueY(this) / 2.0f);
        SVG.Length length22 = rect.x;
        if (length22 == null) {
        }
        SVG.Length length32 = rect.y;
        if (length32 == null) {
        }
        float fFloatValueX32 = rect.width.floatValueX(this);
        float fFloatValueY32 = rect.height.floatValueY(this);
        if (rect.boundingBox == null) {
        }
        f = fFloatValueX32 + fFloatValueX2;
        f2 = fFloatValueY2 + fFloatValueY32;
        path = new Path();
        if (fMin != 0.0f) {
            path.moveTo(fFloatValueX2, fFloatValueY2);
            path.lineTo(f, fFloatValueY2);
            path.lineTo(f, f2);
            path.lineTo(fFloatValueX2, f2);
            path.lineTo(fFloatValueX2, fFloatValueY2);
        }
        path.close();
        return path;
    }

    public final SVG.Box makeViewPort(SVG.Length length, SVG.Length length2, SVG.Length length3, SVG.Length length4) {
        float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
        float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        RendererState rendererState = this.state;
        SVG.Box box = rendererState.viewBox;
        if (box == null) {
            box = rendererState.viewPort;
        }
        return new SVG.Box(fFloatValueX, fFloatValueY, length3 != null ? length3.floatValueX(this) : box.width, length4 != null ? length4.floatValueY(this) : box.height);
    }

    public final Path objectToPath(SVG.SvgElement svgElement, boolean z) {
        Path pathMakePathAndBoundingBox;
        Path pathCalculateClipPath;
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
            SVG.SvgElementBase svgElementBaseResolveIRI = svgElement.document.resolveIRI(use.href);
            if (svgElementBaseResolveIRI == null) {
                error("Use reference '%s' not found", use.href);
                this.state = (RendererState) this.stateStack.pop();
                return null;
            }
            if (!(svgElementBaseResolveIRI instanceof SVG.SvgElement)) {
                this.state = (RendererState) this.stateStack.pop();
                return null;
            }
            pathMakePathAndBoundingBox = objectToPath((SVG.SvgElement) svgElementBaseResolveIRI, false);
            if (pathMakePathAndBoundingBox != null) {
                if (use.boundingBox == null) {
                    use.boundingBox = calculatePathBounds(pathMakePathAndBoundingBox);
                }
                Matrix matrix = use.transform;
                if (matrix != null) {
                    pathMakePathAndBoundingBox.transform(matrix);
                }
                if (this.state.style.clipPath != null && (pathCalculateClipPath = calculateClipPath(svgElement, svgElement.boundingBox)) != null) {
                    pathMakePathAndBoundingBox.op(pathCalculateClipPath, Path.Op.INTERSECT);
                }
                this.state = (RendererState) this.stateStack.pop();
                return pathMakePathAndBoundingBox;
            }
            return null;
        }
        if (svgElement instanceof SVG.GraphicsElement) {
            SVG.GraphicsElement graphicsElement = (SVG.GraphicsElement) svgElement;
            if (svgElement instanceof SVG.Path) {
                pathMakePathAndBoundingBox = new PathConverter(this, ((SVG.Path) svgElement).d).path;
                if (svgElement.boundingBox == null) {
                    svgElement.boundingBox = calculatePathBounds(pathMakePathAndBoundingBox);
                }
            } else {
                pathMakePathAndBoundingBox = svgElement instanceof SVG.Rect ? makePathAndBoundingBox((SVG.Rect) svgElement) : svgElement instanceof SVG.Circle ? makePathAndBoundingBox((SVG.Circle) svgElement) : svgElement instanceof SVG.Ellipse ? makePathAndBoundingBox((SVG.Ellipse) svgElement) : svgElement instanceof SVG.PolyLine ? makePathAndBoundingBox((SVG.PolyLine) svgElement) : null;
            }
            if (pathMakePathAndBoundingBox != null) {
                if (graphicsElement.boundingBox == null) {
                    graphicsElement.boundingBox = calculatePathBounds(pathMakePathAndBoundingBox);
                }
                Matrix matrix2 = graphicsElement.transform;
                if (matrix2 != null) {
                    pathMakePathAndBoundingBox.transform(matrix2);
                }
                pathMakePathAndBoundingBox.setFillType(getClipRuleFromState());
            }
            return null;
        }
        if (!(svgElement instanceof SVG.Text)) {
            error("Invalid %s element found in clipPath definition", svgElement.getNodeName());
            return null;
        }
        SVG.Text text = (SVG.Text) svgElement;
        List list = text.x;
        float fFloatValueY = 0.0f;
        float fFloatValueX = (list == null || ((ArrayList) list).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.x).get(0)).floatValueX(this);
        List list2 = text.y;
        float fFloatValueY2 = (list2 == null || ((ArrayList) list2).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.y).get(0)).floatValueY(this);
        List list3 = text.dx;
        float fFloatValueX2 = (list3 == null || ((ArrayList) list3).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.dx).get(0)).floatValueX(this);
        List list4 = text.dy;
        if (list4 != null && ((ArrayList) list4).size() != 0) {
            fFloatValueY = ((SVG.Length) ((ArrayList) text.dy).get(0)).floatValueY(this);
        }
        if (this.state.style.textAnchor != SVG.Style.TextAnchor.Start) {
            float fCalculateTextWidth = calculateTextWidth(text);
            if (this.state.style.textAnchor == SVG.Style.TextAnchor.Middle) {
                fCalculateTextWidth /= 2.0f;
            }
            fFloatValueX -= fCalculateTextWidth;
        }
        if (text.boundingBox == null) {
            TextBoundsCalculator textBoundsCalculator = new TextBoundsCalculator(fFloatValueX, fFloatValueY2);
            enumerateTextSpans(text, textBoundsCalculator);
            RectF rectF = textBoundsCalculator.bbox;
            text.boundingBox = new SVG.Box(rectF.left, rectF.top, rectF.width(), textBoundsCalculator.bbox.height());
        }
        Path path = new Path();
        enumerateTextSpans(text, new PlainTextToPath(fFloatValueX + fFloatValueX2, fFloatValueY2 + fFloatValueY, path));
        Matrix matrix3 = text.transform;
        if (matrix3 != null) {
            path.transform(matrix3);
        }
        path.setFillType(getClipRuleFromState());
        pathMakePathAndBoundingBox = path;
        if (this.state.style.clipPath != null) {
            pathMakePathAndBoundingBox.op(pathCalculateClipPath, Path.Op.INTERSECT);
        }
        this.state = (RendererState) this.stateStack.pop();
        return pathMakePathAndBoundingBox;
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
        SVG.SvgElementBase svgElementBaseResolveIRI;
        int i = 0;
        if (this.state.style.opacity.floatValue() >= 1.0f && this.state.style.mask == null) {
            return false;
        }
        Canvas canvas = this.canvas;
        int iFloatValue = (int) (this.state.style.opacity.floatValue() * 256.0f);
        if (iFloatValue >= 0) {
            i = 255;
            if (iFloatValue <= 255) {
                i = iFloatValue;
            }
        }
        canvas.saveLayerAlpha(null, i, 31);
        this.stateStack.push(this.state);
        RendererState rendererState = new RendererState(this, this.state);
        this.state = rendererState;
        String str = rendererState.style.mask;
        if (str != null && ((svgElementBaseResolveIRI = this.document.resolveIRI(str)) == null || !(svgElementBaseResolveIRI instanceof SVG.Mask))) {
            error("Mask reference '%s' not found", this.state.style.mask);
            this.state.style.mask = null;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void render(SVG.SvgObject svgObject) {
        SVG.Length length;
        String str;
        int iIndexOf;
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
            Bitmap bitmapDecodeByteArray = null;
            if (svgObject instanceof SVG.Use) {
                SVG.Use use = (SVG.Use) svgObject;
                SVG.Length length3 = use.width;
                if ((length3 == null || !length3.isZero()) && ((length2 = use.height) == null || !length2.isZero())) {
                    updateStyleForElement(use, this.state);
                    if (display()) {
                        SVG.SvgObject svgObjectResolveIRI = use.document.resolveIRI(use.href);
                        if (svgObjectResolveIRI == null) {
                            error("Use reference '%s' not found", use.href);
                        } else {
                            Matrix matrix = use.transform;
                            if (matrix != null) {
                                this.canvas.concat(matrix);
                            }
                            SVG.Length length4 = use.x;
                            float fFloatValueX = length4 != null ? length4.floatValueX(this) : 0.0f;
                            SVG.Length length5 = use.y;
                            this.canvas.translate(fFloatValueX, length5 != null ? length5.floatValueY(this) : 0.0f);
                            checkForClipPath(use, use.boundingBox);
                            boolean zPushLayer = pushLayer();
                            this.parentStack.push(use);
                            this.matrixStack.push(this.canvas.getMatrix());
                            if (svgObjectResolveIRI instanceof SVG.Svg) {
                                SVG.Svg svg2 = (SVG.Svg) svgObjectResolveIRI;
                                SVG.Box boxMakeViewPort = makeViewPort(null, null, use.width, use.height);
                                statePush();
                                render(svg2, boxMakeViewPort, svg2.viewBox, svg2.preserveAspectRatio);
                                statePop();
                            } else if (svgObjectResolveIRI instanceof SVG.Symbol) {
                                SVG.Length length6 = use.width;
                                if (length6 == null) {
                                    length6 = new SVG.Length(100.0f, SVG.Unit.percent);
                                }
                                SVG.Length length7 = use.height;
                                if (length7 == null) {
                                    length7 = new SVG.Length(100.0f, SVG.Unit.percent);
                                }
                                SVG.Box boxMakeViewPort2 = makeViewPort(null, null, length6, length7);
                                statePush();
                                SVG.Symbol symbol = (SVG.Symbol) svgObjectResolveIRI;
                                if (boxMakeViewPort2.width != 0.0f && boxMakeViewPort2.height != 0.0f) {
                                    PreserveAspectRatio preserveAspectRatio = symbol.preserveAspectRatio;
                                    if (preserveAspectRatio == null) {
                                        preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
                                    }
                                    updateStyleForElement(symbol, this.state);
                                    RendererState rendererState = this.state;
                                    rendererState.viewPort = boxMakeViewPort2;
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
                                    boolean zPushLayer2 = pushLayer();
                                    renderChildren(symbol, true);
                                    if (zPushLayer2) {
                                        popLayer(symbol.boundingBox);
                                    }
                                    updateParentBoundingBox(symbol);
                                }
                                statePop();
                            } else {
                                render(svgObjectResolveIRI);
                            }
                            this.parentStack.pop();
                            this.matrixStack.pop();
                            if (zPushLayer) {
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
                        boolean zPushLayer3 = pushLayer();
                        String language = Locale.getDefault().getLanguage();
                        ArrayList arrayList = (ArrayList) r13.children;
                        int size = arrayList.size();
                        while (true) {
                            if (i >= size) {
                                break;
                            }
                            Object obj = arrayList.get(i);
                            i++;
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
                                        if (requiredFeatures.isEmpty() || !supportedFeatures.containsAll(requiredFeatures)) {
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
                        if (zPushLayer3) {
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
                        boolean zPushLayer4 = pushLayer();
                        renderChildren(group, true);
                        if (zPushLayer4) {
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
                        if (str.startsWith("data:") && str.length() >= 14 && (iIndexOf = str.indexOf(44)) >= 12 && ";base64".equals(str.substring(iIndexOf - 7, iIndexOf))) {
                            try {
                                byte[] bArrDecode = Base64.decode(str.substring(iIndexOf + 1), 0);
                                bitmapDecodeByteArray = BitmapFactory.decodeByteArray(bArrDecode, 0, bArrDecode.length);
                            } catch (Exception e) {
                                Log.e("SVGAndroidRenderer", "Could not decode bad Data URL", e);
                            }
                        }
                        if (bitmapDecodeByteArray != null) {
                            SVG.Box box4 = new SVG.Box(0.0f, 0.0f, bitmapDecodeByteArray.getWidth(), bitmapDecodeByteArray.getHeight());
                            updateStyleForElement(image, this.state);
                            if (display() && visible()) {
                                Matrix matrix4 = image.transform;
                                if (matrix4 != null) {
                                    this.canvas.concat(matrix4);
                                }
                                SVG.Length length9 = image.x;
                                float fFloatValueX2 = length9 != null ? length9.floatValueX(this) : 0.0f;
                                SVG.Length length10 = image.y;
                                this.state.viewPort = new SVG.Box(fFloatValueX2, length10 != null ? length10.floatValueY(this) : 0.0f, image.width.floatValueX(this), image.height.floatValueX(this));
                                if (!this.state.style.overflow.booleanValue()) {
                                    SVG.Box box5 = this.state.viewPort;
                                    setClipRect(box5.minX, box5.minY, box5.width, box5.height);
                                }
                                image.boundingBox = this.state.viewPort;
                                updateParentBoundingBox(image);
                                checkForClipPath(image, image.boundingBox);
                                boolean zPushLayer5 = pushLayer();
                                viewportFill();
                                this.canvas.save();
                                this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, box4, preserveAspectRatio2));
                                this.canvas.drawBitmap(bitmapDecodeByteArray, 0.0f, 0.0f, new Paint(this.state.style.imageRendering != SVG.Style.RenderQuality.optimizeSpeed ? 2 : 0));
                                this.canvas.restore();
                                if (zPushLayer5) {
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
                                boolean zPushLayer6 = pushLayer();
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
                                if (zPushLayer6) {
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
                            Path pathMakePathAndBoundingBox = makePathAndBoundingBox(rect);
                            updateParentBoundingBox(rect);
                            checkForGradientsAndPatterns(rect);
                            checkForClipPath(rect, rect.boundingBox);
                            boolean zPushLayer7 = pushLayer();
                            if (this.state.hasFill) {
                                doFilledPath(rect, pathMakePathAndBoundingBox);
                            }
                            if (this.state.hasStroke) {
                                doStroke(pathMakePathAndBoundingBox);
                            }
                            if (zPushLayer7) {
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
                            Path pathMakePathAndBoundingBox2 = makePathAndBoundingBox(circle);
                            updateParentBoundingBox(circle);
                            checkForGradientsAndPatterns(circle);
                            checkForClipPath(circle, circle.boundingBox);
                            boolean zPushLayer8 = pushLayer();
                            if (this.state.hasFill) {
                                doFilledPath(circle, pathMakePathAndBoundingBox2);
                            }
                            if (this.state.hasStroke) {
                                doStroke(pathMakePathAndBoundingBox2);
                            }
                            if (zPushLayer8) {
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
                            Path pathMakePathAndBoundingBox3 = makePathAndBoundingBox(ellipse);
                            updateParentBoundingBox(ellipse);
                            checkForGradientsAndPatterns(ellipse);
                            checkForClipPath(ellipse, ellipse.boundingBox);
                            boolean zPushLayer9 = pushLayer();
                            if (this.state.hasFill) {
                                doFilledPath(ellipse, pathMakePathAndBoundingBox3);
                            }
                            if (this.state.hasStroke) {
                                doStroke(pathMakePathAndBoundingBox3);
                            }
                            if (zPushLayer9) {
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
                        float fFloatValueX3 = length14 == null ? 0.0f : length14.floatValueX(this);
                        SVG.Length length15 = line.y1;
                        float fFloatValueY = length15 == null ? 0.0f : length15.floatValueY(this);
                        SVG.Length length16 = line.x2;
                        float fFloatValueX4 = length16 == null ? 0.0f : length16.floatValueX(this);
                        SVG.Length length17 = line.y2;
                        fFloatValueY = length17 != null ? length17.floatValueY(this) : 0.0f;
                        if (line.boundingBox == null) {
                            line.boundingBox = new SVG.Box(Math.min(fFloatValueX3, fFloatValueX4), Math.min(fFloatValueY, fFloatValueY), Math.abs(fFloatValueX4 - fFloatValueX3), Math.abs(fFloatValueY - fFloatValueY));
                        }
                        Path path3 = new Path();
                        path3.moveTo(fFloatValueX3, fFloatValueY);
                        path3.lineTo(fFloatValueX4, fFloatValueY);
                        updateParentBoundingBox(line);
                        checkForGradientsAndPatterns(line);
                        checkForClipPath(line, line.boundingBox);
                        boolean zPushLayer10 = pushLayer();
                        doStroke(path3);
                        renderMarkers(line);
                        if (zPushLayer10) {
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
                                Path pathMakePathAndBoundingBox4 = makePathAndBoundingBox(polygon);
                                updateParentBoundingBox(polygon);
                                checkForGradientsAndPatterns(polygon);
                                checkForClipPath(polygon, polygon.boundingBox);
                                boolean zPushLayer11 = pushLayer();
                                if (this.state.hasFill) {
                                    doFilledPath(polygon, pathMakePathAndBoundingBox4);
                                }
                                if (this.state.hasStroke) {
                                    doStroke(pathMakePathAndBoundingBox4);
                                }
                                renderMarkers(polygon);
                                if (zPushLayer11) {
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
                                Path pathMakePathAndBoundingBox5 = makePathAndBoundingBox(polyLine);
                                updateParentBoundingBox(polyLine);
                                SVG.Style.FillRule fillRule2 = this.state.style.fillRule;
                                pathMakePathAndBoundingBox5.setFillType((fillRule2 == null || fillRule2 != SVG.Style.FillRule.EvenOdd) ? Path.FillType.WINDING : Path.FillType.EVEN_ODD);
                                checkForGradientsAndPatterns(polyLine);
                                checkForClipPath(polyLine, polyLine.boundingBox);
                                boolean zPushLayer12 = pushLayer();
                                if (this.state.hasFill) {
                                    doFilledPath(polyLine, pathMakePathAndBoundingBox5);
                                }
                                if (this.state.hasStroke) {
                                    doStroke(pathMakePathAndBoundingBox5);
                                }
                                renderMarkers(polyLine);
                                if (zPushLayer12) {
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
                        float fFloatValueX5 = (list == null || ((ArrayList) list).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.x).get(0)).floatValueX(this);
                        List list2 = text.y;
                        float fFloatValueY2 = (list2 == null || ((ArrayList) list2).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.y).get(0)).floatValueY(this);
                        List list3 = text.dx;
                        float fFloatValueX6 = (list3 == null || ((ArrayList) list3).size() == 0) ? 0.0f : ((SVG.Length) ((ArrayList) text.dx).get(0)).floatValueX(this);
                        List list4 = text.dy;
                        if (list4 != null && ((ArrayList) list4).size() != 0) {
                            fFloatValueY = ((SVG.Length) ((ArrayList) text.dy).get(0)).floatValueY(this);
                        }
                        SVG.Style.TextAnchor anchorPosition = getAnchorPosition();
                        if (anchorPosition != SVG.Style.TextAnchor.Start) {
                            float fCalculateTextWidth = calculateTextWidth(text);
                            if (anchorPosition == SVG.Style.TextAnchor.Middle) {
                                fCalculateTextWidth /= 2.0f;
                            }
                            fFloatValueX5 -= fCalculateTextWidth;
                        }
                        if (text.boundingBox == null) {
                            TextBoundsCalculator textBoundsCalculator = new TextBoundsCalculator(fFloatValueX5, fFloatValueY2);
                            enumerateTextSpans(text, textBoundsCalculator);
                            RectF rectF = textBoundsCalculator.bbox;
                            text.boundingBox = new SVG.Box(rectF.left, rectF.top, rectF.width(), textBoundsCalculator.bbox.height());
                        }
                        updateParentBoundingBox(text);
                        checkForGradientsAndPatterns(text);
                        checkForClipPath(text, text.boundingBox);
                        boolean zPushLayer13 = pushLayer();
                        enumerateTextSpans(text, new PlainTextDrawer(fFloatValueX5 + fFloatValueX6, fFloatValueY2 + fFloatValueY));
                        if (zPushLayer13) {
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

    /* JADX WARN: Code restructure failed: missing block: B:74:0x012c, code lost:
    
        if (r7 != 8) goto L79;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0143  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void renderMarker(SVG.Marker marker, MarkerVector markerVector) {
        float fFloatValue;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        statePush();
        Float f7 = marker.orient;
        float f8 = 0.0f;
        if (f7 == null) {
            fFloatValue = 0.0f;
        } else if (Float.isNaN(f7.floatValue())) {
            float f9 = markerVector.dx;
            if (f9 != 0.0f || markerVector.dy != 0.0f) {
                fFloatValue = (float) Math.toDegrees(Math.atan2(markerVector.dy, f9));
            }
        } else {
            fFloatValue = marker.orient.floatValue();
        }
        if (marker.markerUnitsAreUser) {
            f = 1.0f;
        } else {
            SVG.Length length = this.state.style.strokeWidth;
            length.getClass();
            int i = SVG.AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[length.unit.ordinal()];
            if (i != 1) {
                float f10 = this.dpi;
                switch (i) {
                    case 4:
                        f = length.value * f10;
                        break;
                    case 5:
                        f2 = length.value * f10;
                        f3 = 2.54f;
                        f = f2 / f3;
                        break;
                    case 6:
                        f2 = length.value * f10;
                        f3 = 25.4f;
                        f = f2 / f3;
                        break;
                    case 7:
                        f2 = length.value * f10;
                        f3 = 72.0f;
                        f = f2 / f3;
                        break;
                    case 8:
                        f2 = length.value * f10;
                        f3 = 6.0f;
                        f = f2 / f3;
                        break;
                    default:
                        f = length.value;
                        break;
                }
            } else {
                f = length.value;
            }
        }
        this.state = findInheritFromAncestorState(marker);
        Matrix matrix = new Matrix();
        matrix.preTranslate(markerVector.x, markerVector.y);
        matrix.preRotate(fFloatValue);
        matrix.preScale(f, f);
        SVG.Length length2 = marker.refX;
        float fFloatValueX = length2 != null ? length2.floatValueX(this) : 0.0f;
        SVG.Length length3 = marker.refY;
        float fFloatValueY = length3 != null ? length3.floatValueY(this) : 0.0f;
        SVG.Length length4 = marker.markerWidth;
        float fFloatValueX2 = length4 != null ? length4.floatValueX(this) : 3.0f;
        SVG.Length length5 = marker.markerHeight;
        float fFloatValueY2 = length5 != null ? length5.floatValueY(this) : 3.0f;
        SVG.Box box = marker.viewBox;
        if (box != null) {
            float fMax = fFloatValueX2 / box.width;
            float f11 = fFloatValueY2 / box.height;
            PreserveAspectRatio preserveAspectRatio = marker.preserveAspectRatio;
            if (preserveAspectRatio == null) {
                preserveAspectRatio = PreserveAspectRatio.LETTERBOX;
            }
            if (!preserveAspectRatio.equals(PreserveAspectRatio.STRETCH)) {
                fMax = preserveAspectRatio.scale == PreserveAspectRatio.Scale.slice ? Math.max(fMax, f11) : Math.min(fMax, f11);
                f11 = fMax;
            }
            matrix.preTranslate((-fFloatValueX) * fMax, (-fFloatValueY) * f11);
            this.canvas.concat(matrix);
            SVG.Box box2 = marker.viewBox;
            float f12 = box2.width * fMax;
            float f13 = box2.height * f11;
            int[] iArr = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment;
            PreserveAspectRatio.Alignment alignment = preserveAspectRatio.alignment;
            switch (iArr[alignment.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    f4 = (fFloatValueX2 - f12) / 2.0f;
                    f5 = 0.0f - f4;
                    break;
                case 4:
                case 5:
                case 6:
                    f4 = fFloatValueX2 - f12;
                    f5 = 0.0f - f4;
                    break;
                default:
                    f5 = 0.0f;
                    break;
            }
            int i2 = iArr[alignment.ordinal()];
            if (i2 == 2) {
                f6 = (fFloatValueY2 - f13) / 2.0f;
                f8 = 0.0f - f6;
                if (!this.state.style.overflow.booleanValue()) {
                    setClipRect(f5, f8, fFloatValueX2, fFloatValueY2);
                }
                matrix.reset();
                matrix.preScale(fMax, f11);
                this.canvas.concat(matrix);
            } else {
                if (i2 != 3) {
                    if (i2 != 5) {
                        if (i2 != 6) {
                            if (i2 != 7) {
                            }
                        }
                    }
                    f6 = (fFloatValueY2 - f13) / 2.0f;
                    f8 = 0.0f - f6;
                    if (!this.state.style.overflow.booleanValue()) {
                    }
                    matrix.reset();
                    matrix.preScale(fMax, f11);
                    this.canvas.concat(matrix);
                }
                f6 = fFloatValueY2 - f13;
                f8 = 0.0f - f6;
                if (!this.state.style.overflow.booleanValue()) {
                }
                matrix.reset();
                matrix.preScale(fMax, f11);
                this.canvas.concat(matrix);
            }
        } else {
            matrix.preTranslate(-fFloatValueX, -fFloatValueY);
            this.canvas.concat(matrix);
            if (!this.state.style.overflow.booleanValue()) {
                setClipRect(0.0f, 0.0f, fFloatValueX2, fFloatValueY2);
            }
        }
        boolean zPushLayer = pushLayer();
        renderChildren(marker, false);
        if (zPushLayer) {
            popLayer(marker.boundingBox);
        }
        statePop();
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x016f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void renderMarkers(SVG.GraphicsElement graphicsElement) {
        SVG.Marker marker;
        SVG.Marker marker2;
        SVG.Marker marker3;
        float f;
        float f2;
        float f3;
        List list;
        int size;
        SVGAndroidRenderer sVGAndroidRenderer = this;
        SVG.Style style = sVGAndroidRenderer.state.style;
        String str = style.markerStart;
        if (str == null && style.markerMid == null && style.markerEnd == null) {
            return;
        }
        if (str == null) {
            marker = null;
        } else {
            SVG.SvgElementBase svgElementBaseResolveIRI = graphicsElement.document.resolveIRI(str);
            if (svgElementBaseResolveIRI != null) {
                marker = (SVG.Marker) svgElementBaseResolveIRI;
            } else {
                error("Marker reference '%s' not found", sVGAndroidRenderer.state.style.markerStart);
                marker = null;
            }
        }
        String str2 = sVGAndroidRenderer.state.style.markerMid;
        if (str2 == null) {
            marker2 = null;
        } else {
            SVG.SvgElementBase svgElementBaseResolveIRI2 = graphicsElement.document.resolveIRI(str2);
            if (svgElementBaseResolveIRI2 != null) {
                marker2 = (SVG.Marker) svgElementBaseResolveIRI2;
            } else {
                error("Marker reference '%s' not found", sVGAndroidRenderer.state.style.markerMid);
                marker2 = null;
            }
        }
        String str3 = sVGAndroidRenderer.state.style.markerEnd;
        if (str3 == null) {
            marker3 = null;
        } else {
            SVG.SvgElementBase svgElementBaseResolveIRI3 = graphicsElement.document.resolveIRI(str3);
            if (svgElementBaseResolveIRI3 != null) {
                marker3 = (SVG.Marker) svgElementBaseResolveIRI3;
            } else {
                error("Marker reference '%s' not found", sVGAndroidRenderer.state.style.markerEnd);
                marker3 = null;
            }
        }
        if (graphicsElement instanceof SVG.Path) {
            list = sVGAndroidRenderer.new MarkerPositionCalculator(((SVG.Path) graphicsElement).d).markers;
            f = 0.0f;
        } else if (graphicsElement instanceof SVG.Line) {
            SVG.Line line = (SVG.Line) graphicsElement;
            SVG.Length length = line.x1;
            float fFloatValueX = length != null ? length.floatValueX(sVGAndroidRenderer) : 0.0f;
            SVG.Length length2 = line.y1;
            float fFloatValueY = length2 != null ? length2.floatValueY(sVGAndroidRenderer) : 0.0f;
            SVG.Length length3 = line.x2;
            float fFloatValueX2 = length3 != null ? length3.floatValueX(sVGAndroidRenderer) : 0.0f;
            SVG.Length length4 = line.y2;
            float fFloatValueY2 = length4 != null ? length4.floatValueY(sVGAndroidRenderer) : 0.0f;
            ArrayList arrayList = new ArrayList(2);
            float f4 = fFloatValueX2 - fFloatValueX;
            float f5 = fFloatValueY2 - fFloatValueY;
            f = 0.0f;
            arrayList.add(new MarkerVector(sVGAndroidRenderer, fFloatValueX, fFloatValueY, f4, f5));
            arrayList.add(new MarkerVector(this, fFloatValueX2, fFloatValueY2, f4, f5));
            sVGAndroidRenderer = this;
            list = arrayList;
        } else {
            f = 0.0f;
            SVG.PolyLine polyLine = (SVG.PolyLine) graphicsElement;
            int length5 = polyLine.points.length;
            if (length5 < 2) {
                sVGAndroidRenderer = this;
                list = null;
            } else {
                ArrayList arrayList2 = new ArrayList();
                float[] fArr = polyLine.points;
                MarkerVector markerVector = new MarkerVector(this, fArr[0], fArr[1], 0.0f, 0.0f);
                int i = 2;
                float f6 = 0.0f;
                float f7 = 0.0f;
                while (true) {
                    f2 = markerVector.y;
                    f3 = markerVector.x;
                    if (i >= length5) {
                        break;
                    }
                    float[] fArr2 = polyLine.points;
                    f6 = fArr2[i];
                    f7 = fArr2[i + 1];
                    markerVector.add(f6, f7);
                    arrayList2.add(markerVector);
                    markerVector = new MarkerVector(this, f6, f7, f6 - f3, f7 - f2);
                    i += 2;
                }
                if (polyLine instanceof SVG.Polygon) {
                    float[] fArr3 = polyLine.points;
                    float f8 = f6;
                    float f9 = fArr3[0];
                    if (f8 != f9) {
                        float f10 = fArr3[1];
                        if (f7 != f10) {
                            markerVector.add(f9, f10);
                            arrayList2.add(markerVector);
                            float f11 = f9 - f3;
                            float f12 = f10 - f2;
                            sVGAndroidRenderer = this;
                            MarkerVector markerVector2 = new MarkerVector(sVGAndroidRenderer, f9, f10, f11, f12);
                            markerVector2.add((MarkerVector) arrayList2.get(0));
                            arrayList2.add(markerVector2);
                            arrayList2.set(0, markerVector2);
                        } else {
                            sVGAndroidRenderer = this;
                        }
                    }
                } else {
                    sVGAndroidRenderer = this;
                    arrayList2.add(markerVector);
                }
                list = arrayList2;
            }
        }
        if (list == null || (size = list.size()) == 0) {
            return;
        }
        SVG.Style style2 = sVGAndroidRenderer.state.style;
        style2.markerEnd = null;
        style2.markerMid = null;
        style2.markerStart = null;
        if (marker != null) {
            sVGAndroidRenderer.renderMarker(marker, (MarkerVector) list.get(0));
        }
        if (marker2 != null && list.size() > 2) {
            MarkerVector markerVector3 = (MarkerVector) list.get(0);
            MarkerVector markerVector4 = (MarkerVector) list.get(1);
            int i2 = 1;
            while (i2 < size - 1) {
                i2++;
                MarkerVector markerVector5 = (MarkerVector) list.get(i2);
                if (markerVector4.isAmbiguous) {
                    float f13 = markerVector4.dx;
                    float f14 = markerVector4.dy;
                    float f15 = markerVector3.x;
                    float f16 = markerVector4.x;
                    float f17 = markerVector4.y;
                    float f18 = ((f17 - markerVector3.y) * f14) + ((f16 - f15) * f13);
                    if (f18 == f) {
                        f18 = ((markerVector5.x - f16) * f13) + ((markerVector5.y - f17) * f14);
                    }
                    if (f18 <= f && (f18 != f || (f13 <= f && f14 < f))) {
                        markerVector4.dx = -f13;
                        markerVector4.dy = -f14;
                    }
                }
                sVGAndroidRenderer.renderMarker(marker2, markerVector4);
                markerVector3 = markerVector4;
                markerVector4 = markerVector5;
            }
        }
        if (marker3 != null) {
            sVGAndroidRenderer.renderMarker(marker3, (MarkerVector) list.get(size - 1));
        }
    }

    public final void renderMask(SVG.Mask mask, SVG.Box box) {
        float fFloatValueX;
        float fFloatValueY;
        Boolean bool = mask.maskUnitsAreUser;
        if (bool == null || !bool.booleanValue()) {
            SVG.Length length = mask.width;
            float fFloatValue = length != null ? length.floatValue(this, 1.0f) : 1.2f;
            SVG.Length length2 = mask.height;
            float fFloatValue2 = length2 != null ? length2.floatValue(this, 1.0f) : 1.2f;
            fFloatValueX = fFloatValue * box.width;
            fFloatValueY = fFloatValue2 * box.height;
        } else {
            SVG.Length length3 = mask.width;
            fFloatValueX = length3 != null ? length3.floatValueX(this) : box.width;
            SVG.Length length4 = mask.height;
            fFloatValueY = length4 != null ? length4.floatValueY(this) : box.height;
        }
        if (fFloatValueX == 0.0f || fFloatValueY == 0.0f) {
            return;
        }
        statePush();
        RendererState rendererStateFindInheritFromAncestorState = findInheritFromAncestorState(mask);
        this.state = rendererStateFindInheritFromAncestorState;
        rendererStateFindInheritFromAncestorState.style.opacity = Float.valueOf(1.0f);
        boolean zPushLayer = pushLayer();
        this.canvas.save();
        Boolean bool2 = mask.maskContentUnitsAreUser;
        if (bool2 != null && !bool2.booleanValue()) {
            this.canvas.translate(box.minX, box.minY);
            this.canvas.scale(box.width, box.height);
        }
        renderChildren(mask, false);
        this.canvas.restore();
        if (zPushLayer) {
            popLayer(box);
        }
        statePop();
    }

    public final void setClipRect(float f, float f2, float f3, float f4) {
        float fFloatValueX = f3 + f;
        float fFloatValueY = f4 + f2;
        SVG.CSSClipRect cSSClipRect = this.state.style.clip;
        if (cSSClipRect != null) {
            f += cSSClipRect.left.floatValueX(this);
            f2 += this.state.style.clip.top.floatValueY(this);
            fFloatValueX -= this.state.style.clip.right.floatValueX(this);
            fFloatValueY -= this.state.style.clip.bottom.floatValueY(this);
        }
        this.canvas.clipRect(f, f2, fFloatValueX, fFloatValueY);
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
        String strReplaceAll = str.replaceAll("\\n", "").replaceAll("\\t", " ");
        if (z) {
            strReplaceAll = strReplaceAll.replaceAll("^\\s+", "");
        }
        if (z2) {
            strReplaceAll = strReplaceAll.replaceAll("\\s+$", "");
        }
        return strReplaceAll.replaceAll("\\s{2,}", " ");
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
            float fMaxX = box.maxX();
            SVG.Box box2 = svgElement.boundingBox;
            float f3 = box2.minY;
            float fMaxX2 = box2.maxX();
            float fMaxY = svgElement.boundingBox.maxY();
            SVG.Box box3 = svgElement.boundingBox;
            float[] fArr = {f, f2, fMaxX, f3, fMaxX2, fMaxY, box3.minX, box3.maxY()};
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
        Typeface typefaceCheckGenericFont = null;
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
                    float fFloatValue = style2.strokeDashArray[i4 % length2].floatValue(this);
                    fArr[i4] = fFloatValue;
                    f += fFloatValue;
                    i4++;
                }
                if (f == 0.0f) {
                    rendererState.strokePaint.setPathEffect(null);
                } else {
                    float fFloatValue2 = style2.strokeDashOffset.floatValue(this);
                    if (fFloatValue2 < 0.0f) {
                        fFloatValue2 = (fFloatValue2 % f) + f;
                    }
                    rendererState.strokePaint.setPathEffect(new DashPathEffect(fArr, fFloatValue2));
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
                while (it.hasNext() && (typefaceCheckGenericFont = checkGenericFont((String) it.next(), style6.fontWeight, style6.fontStyle)) == null) {
                }
            }
            if (typefaceCheckGenericFont == null) {
                typefaceCheckGenericFont = checkGenericFont("serif", style6.fontWeight, style6.fontStyle);
            }
            rendererState.fillPaint.setTypeface(typefaceCheckGenericFont);
            rendererState.strokePaint.setTypeface(typefaceCheckGenericFont);
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
        int iColourWithOpacity;
        SVG.Style style = this.state.style;
        SVG.SvgPaint svgPaint = style.viewportFill;
        if (svgPaint instanceof SVG.Colour) {
            iColourWithOpacity = ((SVG.Colour) svgPaint).colour;
        } else if (!(svgPaint instanceof SVG.CurrentColor)) {
            return;
        } else {
            iColourWithOpacity = style.color.colour;
        }
        Float f = style.viewportFillOpacity;
        if (f != null) {
            iColourWithOpacity = colourWithOpacity(f.floatValue(), iColourWithOpacity);
        }
        this.canvas.drawColor(iColourWithOpacity);
    }

    public final boolean visible() {
        Boolean bool = this.state.style.visibility;
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

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
            double dSqrt = Math.sqrt((f4 * f4) + (f3 * f3));
            if (dSqrt != 0.0d) {
                this.dx = (float) (f3 / dSqrt);
                this.dy = (float) (f4 / dSqrt);
            }
        }

        public final void add(float f, float f2) {
            float f3 = f - this.x;
            float f4 = f2 - this.y;
            double dSqrt = Math.sqrt((f4 * f4) + (f3 * f3));
            if (dSqrt != 0.0d) {
                f3 = (float) (f3 / dSqrt);
                f4 = (float) (f4 / dSqrt);
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
        float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
        SVG.Length length2 = circle.cy;
        float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        float fFloatValue = circle.r.floatValue(this);
        float f = fFloatValueX - fFloatValue;
        float f2 = fFloatValueY - fFloatValue;
        float f3 = fFloatValueX + fFloatValue;
        float f4 = fFloatValueY + fFloatValue;
        if (circle.boundingBox == null) {
            float f5 = 2.0f * fFloatValue;
            circle.boundingBox = new SVG.Box(f, f2, f5, f5);
        }
        float f6 = fFloatValue * 0.5522848f;
        Path pathM = PathInterpolator$$ExternalSyntheticOutline0.m(fFloatValueX, f2);
        float f7 = fFloatValueX + f6;
        float f8 = fFloatValueY - f6;
        pathM.cubicTo(f7, f2, f3, f8, f3, fFloatValueY);
        float f9 = fFloatValueY + f6;
        pathM.cubicTo(f3, f9, f7, f4, fFloatValueX, f4);
        float f10 = fFloatValueX - f6;
        pathM.cubicTo(f10, f4, f, f9, f, fFloatValueY);
        pathM.cubicTo(f, f8, f10, f2, fFloatValueX, f2);
        pathM.close();
        return pathM;
    }

    public final Path makePathAndBoundingBox(SVG.Ellipse ellipse) {
        SVG.Length length = ellipse.cx;
        float fFloatValueX = length != null ? length.floatValueX(this) : 0.0f;
        SVG.Length length2 = ellipse.cy;
        float fFloatValueY = length2 != null ? length2.floatValueY(this) : 0.0f;
        float fFloatValueX2 = ellipse.rx.floatValueX(this);
        float fFloatValueY2 = ellipse.ry.floatValueY(this);
        float f = fFloatValueX - fFloatValueX2;
        float f2 = fFloatValueY - fFloatValueY2;
        float f3 = fFloatValueX + fFloatValueX2;
        float f4 = fFloatValueY + fFloatValueY2;
        if (ellipse.boundingBox == null) {
            ellipse.boundingBox = new SVG.Box(f, f2, fFloatValueX2 * 2.0f, 2.0f * fFloatValueY2);
        }
        float f5 = fFloatValueX2 * 0.5522848f;
        float f6 = fFloatValueY2 * 0.5522848f;
        Path pathM = PathInterpolator$$ExternalSyntheticOutline0.m(fFloatValueX, f2);
        float f7 = fFloatValueX + f5;
        float f8 = fFloatValueY - f6;
        pathM.cubicTo(f7, f2, f3, f8, f3, fFloatValueY);
        float f9 = fFloatValueY + f6;
        pathM.cubicTo(f3, f9, f7, f4, fFloatValueX, f4);
        float f10 = fFloatValueX - f5;
        pathM.cubicTo(f10, f4, f, f9, f, fFloatValueY);
        pathM.cubicTo(f, f8, f10, f2, fFloatValueX, f2);
        pathM.close();
        return pathM;
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
            boolean zPushLayer = pushLayer();
            viewportFill();
            renderChildren(svg, true);
            if (zPushLayer) {
                popLayer(svg.boundingBox);
            }
            updateParentBoundingBox(svg);
        }
    }
}
