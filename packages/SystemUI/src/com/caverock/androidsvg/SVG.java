package com.caverock.androidsvg;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import com.caverock.androidsvg.CSSParser;
import com.caverock.androidsvg.SVGAndroidRenderer;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.zip.GZIPInputStream;

/* loaded from: classes3.dex */
public class SVG {
    public Svg rootElement = null;
    public final CSSParser.Ruleset cssRules = new CSSParser.Ruleset();
    public final Map idToElementMap = new HashMap();

    /* renamed from: com.caverock.androidsvg.SVG$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$SVG$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$com$caverock$androidsvg$SVG$Unit = iArr;
            try {
                iArr[Unit.px.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.em.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.ex.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.in.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.cm.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.mm.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.pt.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.pc.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$SVG$Unit[Unit.percent.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public class CSSClipRect {
        public final Length bottom;
        public final Length left;
        public final Length right;
        public final Length top;

        public CSSClipRect(Length length, Length length2, Length length3, Length length4) {
            this.top = length;
            this.right = length2;
            this.bottom = length3;
            this.left = length4;
        }
    }

    public class Circle extends GraphicsElement {
        public Length cx;
        public Length cy;
        public Length r;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "circle";
        }
    }

    public class ClipPath extends Group implements NotDirectlyRendered {
        public Boolean clipPathUnitsAreUser;

        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "clipPath";
        }
    }

    public class Colour extends SvgPaint {
        public static final Colour BLACK = new Colour(-16777216);
        public static final Colour TRANSPARENT = new Colour(0);
        public final int colour;

        public Colour(int i) {
            this.colour = i;
        }

        public final String toString() {
            return String.format("#%08x", Integer.valueOf(this.colour));
        }
    }

    public class CurrentColor extends SvgPaint {
        public static final CurrentColor instance = new CurrentColor();

        private CurrentColor() {
        }
    }

    public class Defs extends Group implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "defs";
        }
    }

    public class Ellipse extends GraphicsElement {
        public Length cx;
        public Length cy;
        public Length rx;
        public Length ry;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "ellipse";
        }
    }

    public abstract class GradientElement extends SvgElementBase implements SvgContainer {
        public List children = new ArrayList();
        public Matrix gradientTransform;
        public Boolean gradientUnitsAreUser;
        public String href;
        public GradientSpread spreadMethod;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) throws SVGParseException {
            if (svgObject instanceof Stop) {
                ((ArrayList) this.children).add(svgObject);
                return;
            }
            throw new SVGParseException("Gradient elements cannot contain " + svgObject + " elements.");
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return this.children;
        }
    }

    enum GradientSpread {
        /* JADX INFO: Fake field, exist only in values array */
        pad,
        reflect,
        repeat
    }

    public abstract class GraphicsElement extends SvgConditionalElement implements HasTransform {
        public Matrix transform;

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public class Group extends SvgConditionalContainer implements HasTransform {
        public Matrix transform;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "group";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public interface HasTransform {
        void setTransform(Matrix matrix);
    }

    public class Image extends SvgPreserveAspectRatioContainer implements HasTransform {
        public Length height;
        public String href;
        public Matrix transform;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "image";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public class Line extends GraphicsElement {
        public Length x1;
        public Length x2;
        public Length y1;
        public Length y2;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "line";
        }
    }

    public class Marker extends SvgViewBoxContainer implements NotDirectlyRendered {
        public Length markerHeight;
        public boolean markerUnitsAreUser;
        public Length markerWidth;
        public Float orient;
        public Length refX;
        public Length refY;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "marker";
        }
    }

    public class Mask extends SvgConditionalContainer implements NotDirectlyRendered {
        public Length height;
        public Boolean maskContentUnitsAreUser;
        public Boolean maskUnitsAreUser;
        public Length width;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "mask";
        }
    }

    public interface NotDirectlyRendered {
    }

    public class PaintReference extends SvgPaint {
        public final SvgPaint fallback;
        public final String href;

        public PaintReference(String str, SvgPaint svgPaint) {
            this.href = str;
            this.fallback = svgPaint;
        }

        public final String toString() {
            return this.href + " " + this.fallback;
        }
    }

    public class Path extends GraphicsElement {
        public PathDefinition d;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "path";
        }
    }

    public class PathDefinition implements PathInterface {
        public int commandsLength = 0;
        public int coordsLength = 0;
        public byte[] commands = new byte[8];
        public float[] coords = new float[16];

        public final void addCommand(byte b) {
            int i = this.commandsLength;
            byte[] bArr = this.commands;
            if (i == bArr.length) {
                byte[] bArr2 = new byte[bArr.length * 2];
                System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                this.commands = bArr2;
            }
            byte[] bArr3 = this.commands;
            int i2 = this.commandsLength;
            this.commandsLength = i2 + 1;
            bArr3[i2] = b;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
            addCommand((byte) ((z ? 2 : 0) | 4 | (z2 ? 1 : 0)));
            coordsEnsure(5);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = f;
            int i3 = i + 2;
            this.coordsLength = i3;
            fArr[i2] = f2;
            int i4 = i + 3;
            this.coordsLength = i4;
            fArr[i3] = f3;
            int i5 = i + 4;
            this.coordsLength = i5;
            fArr[i4] = f4;
            this.coordsLength = i + 5;
            fArr[i5] = f5;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void close() {
            addCommand((byte) 8);
        }

        public final void coordsEnsure(int i) {
            float[] fArr = this.coords;
            if (fArr.length < this.coordsLength + i) {
                float[] fArr2 = new float[fArr.length * 2];
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                this.coords = fArr2;
            }
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
            addCommand((byte) 2);
            coordsEnsure(6);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = f;
            int i3 = i + 2;
            this.coordsLength = i3;
            fArr[i2] = f2;
            int i4 = i + 3;
            this.coordsLength = i4;
            fArr[i3] = f3;
            int i5 = i + 4;
            this.coordsLength = i5;
            fArr[i4] = f4;
            int i6 = i + 5;
            this.coordsLength = i6;
            fArr[i5] = f5;
            this.coordsLength = i + 6;
            fArr[i6] = f6;
        }

        public final void enumeratePath(PathInterface pathInterface) {
            int i = 0;
            for (int i2 = 0; i2 < this.commandsLength; i2++) {
                byte b = this.commands[i2];
                if (b == 0) {
                    float[] fArr = this.coords;
                    int i3 = i + 1;
                    float f = fArr[i];
                    i += 2;
                    pathInterface.moveTo(f, fArr[i3]);
                } else if (b == 1) {
                    float[] fArr2 = this.coords;
                    int i4 = i + 1;
                    float f2 = fArr2[i];
                    i += 2;
                    pathInterface.lineTo(f2, fArr2[i4]);
                } else if (b == 2) {
                    float[] fArr3 = this.coords;
                    pathInterface.cubicTo(fArr3[i], fArr3[i + 1], fArr3[i + 2], fArr3[i + 3], fArr3[i + 4], fArr3[i + 5]);
                    i += 6;
                } else if (b == 3) {
                    float[] fArr4 = this.coords;
                    float f3 = fArr4[i];
                    float f4 = fArr4[i + 1];
                    int i5 = i + 3;
                    float f5 = fArr4[i + 2];
                    i += 4;
                    pathInterface.quadTo(f3, f4, f5, fArr4[i5]);
                } else if (b != 8) {
                    boolean z = (b & 2) != 0;
                    boolean z2 = (b & 1) != 0;
                    float[] fArr5 = this.coords;
                    pathInterface.arcTo(fArr5[i], fArr5[i + 1], fArr5[i + 2], z, z2, fArr5[i + 3], fArr5[i + 4]);
                    i += 5;
                } else {
                    pathInterface.close();
                }
            }
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void lineTo(float f, float f2) {
            addCommand((byte) 1);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = f;
            this.coordsLength = i + 2;
            fArr[i2] = f2;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void moveTo(float f, float f2) {
            addCommand((byte) 0);
            coordsEnsure(2);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = f;
            this.coordsLength = i + 2;
            fArr[i2] = f2;
        }

        @Override // com.caverock.androidsvg.SVG.PathInterface
        public final void quadTo(float f, float f2, float f3, float f4) {
            addCommand((byte) 3);
            coordsEnsure(4);
            float[] fArr = this.coords;
            int i = this.coordsLength;
            int i2 = i + 1;
            this.coordsLength = i2;
            fArr[i] = f;
            int i3 = i + 2;
            this.coordsLength = i3;
            fArr[i2] = f2;
            int i4 = i + 3;
            this.coordsLength = i4;
            fArr[i3] = f3;
            this.coordsLength = i + 4;
            fArr[i4] = f4;
        }
    }

    public interface PathInterface {
        void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5);

        void close();

        void cubicTo(float f, float f2, float f3, float f4, float f5, float f6);

        void lineTo(float f, float f2);

        void moveTo(float f, float f2);

        void quadTo(float f, float f2, float f3, float f4);
    }

    public class Pattern extends SvgViewBoxContainer implements NotDirectlyRendered {
        public Length height;
        public String href;
        public Boolean patternContentUnitsAreUser;
        public Matrix patternTransform;
        public Boolean patternUnitsAreUser;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "pattern";
        }
    }

    public class PolyLine extends GraphicsElement {
        public float[] points;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public String getNodeName() {
            return "polyline";
        }
    }

    public class Polygon extends PolyLine {
        @Override // com.caverock.androidsvg.SVG.PolyLine, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "polygon";
        }
    }

    public class Rect extends GraphicsElement {
        public Length height;
        public Length rx;
        public Length ry;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "rect";
        }
    }

    public class Style implements Cloneable {
        public CSSClipRect clip;
        public String clipPath;
        public FillRule clipRule;
        public Colour color;
        public TextDirection direction;
        public Boolean display;
        public SvgPaint fill;
        public Float fillOpacity;
        public FillRule fillRule;
        public List fontFamily;
        public Length fontSize;
        public FontStyle fontStyle;
        public Integer fontWeight;
        public RenderQuality imageRendering;
        public String markerEnd;
        public String markerMid;
        public String markerStart;
        public String mask;
        public Float opacity;
        public Boolean overflow;
        public SvgPaint solidColor;
        public Float solidOpacity;
        public long specifiedFlags = 0;
        public SvgPaint stopColor;
        public Float stopOpacity;
        public SvgPaint stroke;
        public Length[] strokeDashArray;
        public Length strokeDashOffset;
        public LineCap strokeLineCap;
        public LineJoin strokeLineJoin;
        public Float strokeMiterLimit;
        public Float strokeOpacity;
        public Length strokeWidth;
        public TextAnchor textAnchor;
        public TextDecoration textDecoration;
        public VectorEffect vectorEffect;
        public SvgPaint viewportFill;
        public Float viewportFillOpacity;
        public Boolean visibility;

        public enum FillRule {
            NonZero,
            EvenOdd
        }

        public enum FontStyle {
            Normal,
            Italic,
            Oblique
        }

        public enum LineCap {
            Butt,
            Round,
            Square
        }

        public enum LineJoin {
            Miter,
            Round,
            Bevel
        }

        public enum RenderQuality {
            auto,
            optimizeQuality,
            optimizeSpeed
        }

        public enum TextAnchor {
            Start,
            Middle,
            End
        }

        public enum TextDecoration {
            None,
            Underline,
            Overline,
            LineThrough,
            Blink
        }

        public enum TextDirection {
            LTR,
            RTL
        }

        public enum VectorEffect {
            None,
            NonScalingStroke
        }

        public static Style getDefaultStyle() {
            Style style = new Style();
            style.specifiedFlags = -1L;
            Colour colour = Colour.BLACK;
            style.fill = colour;
            FillRule fillRule = FillRule.NonZero;
            style.fillRule = fillRule;
            Float fValueOf = Float.valueOf(1.0f);
            style.fillOpacity = fValueOf;
            style.stroke = null;
            style.strokeOpacity = fValueOf;
            style.strokeWidth = new Length(1.0f);
            style.strokeLineCap = LineCap.Butt;
            style.strokeLineJoin = LineJoin.Miter;
            style.strokeMiterLimit = Float.valueOf(4.0f);
            style.strokeDashArray = null;
            style.strokeDashOffset = new Length(0.0f);
            style.opacity = fValueOf;
            style.color = colour;
            style.fontFamily = null;
            style.fontSize = new Length(12.0f, Unit.pt);
            style.fontWeight = 400;
            style.fontStyle = FontStyle.Normal;
            style.textDecoration = TextDecoration.None;
            style.direction = TextDirection.LTR;
            style.textAnchor = TextAnchor.Start;
            Boolean bool = Boolean.TRUE;
            style.overflow = bool;
            style.clip = null;
            style.markerStart = null;
            style.markerMid = null;
            style.markerEnd = null;
            style.display = bool;
            style.visibility = bool;
            style.stopColor = colour;
            style.stopOpacity = fValueOf;
            style.clipPath = null;
            style.clipRule = fillRule;
            style.mask = null;
            style.solidColor = null;
            style.solidOpacity = fValueOf;
            style.viewportFill = null;
            style.viewportFillOpacity = fValueOf;
            style.vectorEffect = VectorEffect.None;
            style.imageRendering = RenderQuality.auto;
            return style;
        }

        public final Object clone() {
            Style style = (Style) super.clone();
            Length[] lengthArr = this.strokeDashArray;
            if (lengthArr != null) {
                style.strokeDashArray = (Length[]) lengthArr.clone();
            }
            return style;
        }
    }

    public class Svg extends SvgViewBoxContainer {
        public Length height;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "svg";
        }
    }

    public interface SvgConditional {
        String getRequiredExtensions();

        Set getRequiredFeatures();

        Set getRequiredFonts();

        Set getRequiredFormats();

        Set getSystemLanguage();

        void setRequiredExtensions(String str);

        void setRequiredFeatures(Set set);

        void setRequiredFonts(Set set);

        void setRequiredFormats(Set set);

        void setSystemLanguage(Set set);
    }

    public abstract class SvgConditionalContainer extends SvgElement implements SvgContainer, SvgConditional {
        public Set systemLanguage;
        public List children = new ArrayList();
        public Set requiredFeatures = null;
        public String requiredExtensions = null;
        public Set requiredFormats = null;
        public Set requiredFonts = null;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public void addChild(SvgObject svgObject) {
            ((ArrayList) this.children).add(svgObject);
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return this.children;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFeatures() {
            return this.requiredFeatures;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFonts() {
            return this.requiredFonts;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFormats() {
            return this.requiredFormats;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getSystemLanguage() {
            return null;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredExtensions(String str) {
            this.requiredExtensions = str;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFeatures(Set set) {
            this.requiredFeatures = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFonts(Set set) {
            this.requiredFonts = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFormats(Set set) {
            this.requiredFormats = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setSystemLanguage(Set set) {
            this.systemLanguage = set;
        }
    }

    public abstract class SvgConditionalElement extends SvgElement implements SvgConditional {
        public Set requiredFeatures = null;
        public String requiredExtensions = null;
        public Set systemLanguage = null;
        public Set requiredFormats = null;
        public Set requiredFonts = null;

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final String getRequiredExtensions() {
            return this.requiredExtensions;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFeatures() {
            return this.requiredFeatures;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFonts() {
            return this.requiredFonts;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getRequiredFormats() {
            return this.requiredFormats;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final Set getSystemLanguage() {
            return this.systemLanguage;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredExtensions(String str) {
            this.requiredExtensions = str;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFeatures(Set set) {
            this.requiredFeatures = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFonts(Set set) {
            this.requiredFonts = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setRequiredFormats(Set set) {
            this.requiredFormats = set;
        }

        @Override // com.caverock.androidsvg.SVG.SvgConditional
        public final void setSystemLanguage(Set set) {
            this.systemLanguage = set;
        }
    }

    public interface SvgContainer {
        void addChild(SvgObject svgObject);

        List getChildren();
    }

    public abstract class SvgElement extends SvgElementBase {
        public Box boundingBox = null;
    }

    public abstract class SvgElementBase extends SvgObject {
        public String id = null;
        public Boolean spacePreserve = null;
        public Style baseStyle = null;
        public Style style = null;
        public List classNames = null;

        public final String toString() {
            return getNodeName();
        }
    }

    public class SvgLinearGradient extends GradientElement {
        public Length x1;
        public Length x2;
        public Length y1;
        public Length y2;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "linearGradient";
        }
    }

    public class SvgObject {
        public SVG document;
        public SvgContainer parent;

        public String getNodeName() {
            return "";
        }
    }

    public abstract class SvgPaint implements Cloneable {
    }

    public abstract class SvgPreserveAspectRatioContainer extends SvgConditionalContainer {
        public PreserveAspectRatio preserveAspectRatio = null;
    }

    public class SvgRadialGradient extends GradientElement {
        public Length cx;
        public Length cy;
        public Length fx;
        public Length fy;
        public Length r;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "radialGradient";
        }
    }

    public abstract class SvgViewBoxContainer extends SvgPreserveAspectRatioContainer {
        public Box viewBox;
    }

    public class Switch extends Group {
        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "switch";
        }
    }

    public class Symbol extends SvgViewBoxContainer implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "symbol";
        }
    }

    public class TRef extends TextContainer implements TextChild {
        public String href;
        public Text textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "tref";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return this.textRoot;
        }
    }

    public class TSpan extends TextPositionedContainer implements TextChild {
        public Text textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "tspan";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return this.textRoot;
        }
    }

    public class Text extends TextPositionedContainer implements HasTransform {
        public Matrix transform;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "text";
        }

        @Override // com.caverock.androidsvg.SVG.HasTransform
        public final void setTransform(Matrix matrix) {
            this.transform = matrix;
        }
    }

    public interface TextChild {
        Text getTextRoot();
    }

    public abstract class TextContainer extends SvgConditionalContainer {
        @Override // com.caverock.androidsvg.SVG.SvgConditionalContainer, com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) throws SVGParseException {
            if (svgObject instanceof TextChild) {
                ((ArrayList) this.children).add(svgObject);
                return;
            }
            throw new SVGParseException("Text content elements cannot contain " + svgObject + " elements.");
        }
    }

    public class TextPath extends TextContainer implements TextChild {
        public String href;
        public Length startOffset;
        public Text textRoot;

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "textPath";
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return this.textRoot;
        }
    }

    public abstract class TextPositionedContainer extends TextContainer {
        public List dx;
        public List dy;
        public List x;
        public List y;
    }

    public class TextSequence extends SvgObject implements TextChild {
        public String text;

        public TextSequence(String str) {
            this.text = str;
        }

        @Override // com.caverock.androidsvg.SVG.TextChild
        public final Text getTextRoot() {
            return null;
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("TextChild: '"), this.text, "'");
        }
    }

    enum Unit {
        px,
        em,
        ex,
        in,
        cm,
        mm,
        pt,
        pc,
        percent
    }

    public class Use extends Group {
        public Length height;
        public String href;
        public Length width;
        public Length x;
        public Length y;

        @Override // com.caverock.androidsvg.SVG.Group, com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "use";
        }
    }

    public class View extends SvgViewBoxContainer implements NotDirectlyRendered {
        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "view";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SvgElementBase getElementById(SvgContainer svgContainer, String str) {
        SvgElementBase elementById;
        SvgElementBase svgElementBase = (SvgElementBase) svgContainer;
        if (str.equals(svgElementBase.id)) {
            return svgElementBase;
        }
        for (Object obj : svgContainer.getChildren()) {
            if (obj instanceof SvgElementBase) {
                SvgElementBase svgElementBase2 = (SvgElementBase) obj;
                if (str.equals(svgElementBase2.id)) {
                    return svgElementBase2;
                }
                if ((obj instanceof SvgContainer) && (elementById = getElementById((SvgContainer) obj, str)) != null) {
                    return elementById;
                }
            }
        }
        return null;
    }

    public static SVG getFromInputStream(InputStream inputStream) {
        SVGParser sVGParser = new SVGParser();
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }
        try {
            inputStream.mark(3);
            int i = inputStream.read() + (inputStream.read() << 8);
            inputStream.reset();
            if (i == 35615) {
                inputStream = new BufferedInputStream(new GZIPInputStream(inputStream));
            }
        } catch (IOException unused) {
        }
        try {
            inputStream.mark(4096);
            sVGParser.parseUsingXmlPullParser(inputStream);
            return sVGParser.svgDocument;
        } finally {
            try {
                inputStream.close();
            } catch (IOException unused2) {
                Log.e("SVGParser", "Exception thrown closing input stream");
            }
        }
    }

    public final void renderToCanvas(Canvas canvas) {
        RenderOptions renderOptions = new RenderOptions();
        if (renderOptions.viewPort == null) {
            renderOptions.viewPort = new Box(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight());
        }
        SVGAndroidRenderer sVGAndroidRenderer = new SVGAndroidRenderer(canvas, 96.0f);
        sVGAndroidRenderer.document = this;
        Svg svg = this.rootElement;
        if (svg == null) {
            Log.w("SVGAndroidRenderer", "Nothing to render. Document is empty.");
            return;
        }
        Box box = svg.viewBox;
        PreserveAspectRatio preserveAspectRatio = svg.preserveAspectRatio;
        sVGAndroidRenderer.state = new SVGAndroidRenderer.RendererState(sVGAndroidRenderer);
        sVGAndroidRenderer.stateStack = new Stack();
        sVGAndroidRenderer.updateStyle(sVGAndroidRenderer.state, Style.getDefaultStyle());
        SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
        rendererState.viewPort = null;
        rendererState.spacePreserve = false;
        sVGAndroidRenderer.stateStack.push(new SVGAndroidRenderer.RendererState(sVGAndroidRenderer, rendererState));
        sVGAndroidRenderer.matrixStack = new Stack();
        sVGAndroidRenderer.parentStack = new Stack();
        Boolean bool = svg.spacePreserve;
        if (bool != null) {
            sVGAndroidRenderer.state.spacePreserve = bool.booleanValue();
        }
        sVGAndroidRenderer.statePush();
        Box box2 = new Box(renderOptions.viewPort);
        Length length = svg.width;
        if (length != null) {
            box2.width = length.floatValue(sVGAndroidRenderer, box2.width);
        }
        Length length2 = svg.height;
        if (length2 != null) {
            box2.height = length2.floatValue(sVGAndroidRenderer, box2.height);
        }
        sVGAndroidRenderer.render(svg, box2, box, preserveAspectRatio);
        sVGAndroidRenderer.statePop();
    }

    public final SvgElementBase resolveIRI(String str) {
        String strSubstring;
        if (str == null) {
            return null;
        }
        if (str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1).replace("\\\"", "\"");
        } else if (str.startsWith("'") && str.endsWith("'")) {
            str = str.substring(1, str.length() - 1).replace("\\'", "'");
        }
        String strReplace = str.replace("\\\n", "").replace("\\A", "\n");
        if (strReplace.length() <= 1 || !strReplace.startsWith("#") || (strSubstring = strReplace.substring(1)) == null || strSubstring.length() == 0) {
            return null;
        }
        if (strSubstring.equals(this.rootElement.id)) {
            return this.rootElement;
        }
        if (((HashMap) this.idToElementMap).containsKey(strSubstring)) {
            return (SvgElementBase) ((HashMap) this.idToElementMap).get(strSubstring);
        }
        SvgElementBase elementById = getElementById(this.rootElement, strSubstring);
        ((HashMap) this.idToElementMap).put(strSubstring, elementById);
        return elementById;
    }

    public final void setDocumentHeight(float f) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.height = new Length(f);
    }

    public final void setDocumentWidth(float f) {
        Svg svg = this.rootElement;
        if (svg == null) {
            throw new IllegalArgumentException("SVG document is empty");
        }
        svg.width = new Length(f);
    }

    public class Length implements Cloneable {
        public final Unit unit;
        public final float value;

        public Length(float f, Unit unit) {
            this.value = f;
            this.unit = unit;
        }

        public final float floatValue(SVGAndroidRenderer sVGAndroidRenderer) {
            float f;
            if (this.unit != Unit.percent) {
                return floatValueX(sVGAndroidRenderer);
            }
            SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
            Box box = rendererState.viewBox;
            if (box == null) {
                box = rendererState.viewPort;
            }
            if (box == null) {
                return this.value;
            }
            float fSqrt = box.width;
            if (fSqrt == box.height) {
                f = this.value;
            } else {
                fSqrt = (float) (Math.sqrt((r0 * r0) + (fSqrt * fSqrt)) / 1.414213562373095d);
                f = this.value;
            }
            return (f * fSqrt) / 100.0f;
        }

        public final float floatValueX(SVGAndroidRenderer sVGAndroidRenderer) {
            float f;
            float f2;
            switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[this.unit.ordinal()]) {
                case 1:
                    return this.value;
                case 2:
                    return sVGAndroidRenderer.state.fillPaint.getTextSize() * this.value;
                case 3:
                    return (sVGAndroidRenderer.state.fillPaint.getTextSize() / 2.0f) * this.value;
                case 4:
                    return this.value * sVGAndroidRenderer.dpi;
                case 5:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 2.54f;
                    break;
                case 6:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 25.4f;
                    break;
                case 7:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 72.0f;
                    break;
                case 8:
                    f = this.value * sVGAndroidRenderer.dpi;
                    f2 = 6.0f;
                    break;
                case 9:
                    SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
                    Box box = rendererState.viewBox;
                    if (box == null) {
                        box = rendererState.viewPort;
                    }
                    if (box != null) {
                        f = this.value * box.width;
                        f2 = 100.0f;
                        break;
                    } else {
                        return this.value;
                    }
                default:
                    return this.value;
            }
            return f / f2;
        }

        public final float floatValueY(SVGAndroidRenderer sVGAndroidRenderer) {
            if (this.unit != Unit.percent) {
                return floatValueX(sVGAndroidRenderer);
            }
            SVGAndroidRenderer.RendererState rendererState = sVGAndroidRenderer.state;
            Box box = rendererState.viewBox;
            if (box == null) {
                box = rendererState.viewPort;
            }
            return box == null ? this.value : (this.value * box.height) / 100.0f;
        }

        public final boolean isNegative() {
            return this.value < 0.0f;
        }

        public final boolean isZero() {
            return this.value == 0.0f;
        }

        public final String toString() {
            return String.valueOf(this.value) + this.unit;
        }

        public Length(float f) {
            this.value = f;
            this.unit = Unit.px;
        }

        public final float floatValue(SVGAndroidRenderer sVGAndroidRenderer, float f) {
            if (this.unit == Unit.percent) {
                return (this.value * f) / 100.0f;
            }
            return floatValueX(sVGAndroidRenderer);
        }
    }

    public class Box {
        public float height;
        public float minX;
        public float minY;
        public float width;

        public Box(float f, float f2, float f3, float f4) {
            this.minX = f;
            this.minY = f2;
            this.width = f3;
            this.height = f4;
        }

        public final float maxX() {
            return this.minX + this.width;
        }

        public final float maxY() {
            return this.minY + this.height;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("[");
            sb.append(this.minX);
            sb.append(" ");
            sb.append(this.minY);
            sb.append(" ");
            sb.append(this.width);
            sb.append(" ");
            return DpCornerSize$$ExternalSyntheticOutline0.m(this.height, "]", sb);
        }

        public Box(Box box) {
            this.minX = box.minX;
            this.minY = box.minY;
            this.width = box.width;
            this.height = box.height;
        }
    }

    public class SolidColor extends SvgElementBase implements SvgContainer {
        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return Collections.EMPTY_LIST;
        }

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "solidColor";
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) {
        }
    }

    public class Stop extends SvgElementBase implements SvgContainer {
        public Float offset;

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final List getChildren() {
            return Collections.EMPTY_LIST;
        }

        @Override // com.caverock.androidsvg.SVG.SvgObject
        public final String getNodeName() {
            return "stop";
        }

        @Override // com.caverock.androidsvg.SVG.SvgContainer
        public final void addChild(SvgObject svgObject) {
        }
    }
}
