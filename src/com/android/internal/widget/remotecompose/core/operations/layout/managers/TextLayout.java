package com.android.internal.widget.remotecompose.core.operations.layout.managers;

import android.graphics.Typeface;
import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.Platform;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.documentation.DocumentationBuilder;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.ComponentMeasure;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.MeasurePass;
import com.android.internal.widget.remotecompose.core.operations.layout.measure.Size;
import com.android.internal.widget.remotecompose.core.operations.paint.PaintBundle;
import com.android.internal.widget.remotecompose.core.operations.utilities.StringSerializer;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import java.util.List;

/* loaded from: classes6.dex */
public class TextLayout extends LayoutManager implements VariableSupport, AccessibleComponent {
    private static final boolean DEBUG = false;
    public static final int OVERFLOW_CLIP = 1;
    public static final int OVERFLOW_ELLIPSIS = 3;
    public static final int OVERFLOW_MIDDLE_ELLIPSIS = 5;
    public static final int OVERFLOW_START_ELLIPSIS = 4;
    public static final int OVERFLOW_VISIBLE = 2;
    public static final int TEXT_ALIGN_CENTER = 3;
    public static final int TEXT_ALIGN_END = 6;
    public static final int TEXT_ALIGN_JUSTIFY = 4;
    public static final int TEXT_ALIGN_LEFT = 1;
    public static final int TEXT_ALIGN_RIGHT = 2;
    public static final int TEXT_ALIGN_START = 5;
    private final Size mCachedSize;
    private String mCachedString;
    private int mColor;
    Platform.ComputedTextLayout mComputedTextLayout;
    private int mFontFamilyId;
    private float mFontSize;
    private int mFontStyle;
    private float mFontWeight;
    private int mMaxLines;
    private String mNewString;
    private int mOverflow;
    public PaintBundle mPaint;
    private int mTextAlign;
    private float mTextH;
    private int mTextId;
    private float mTextW;
    private float mTextX;
    private float mTextY;
    private int mType;

    public static int id() {
        return 208;
    }

    @Override // com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent
    public Integer getTextId() {
        return Integer.valueOf(this.mTextId);
    }

    @Override // com.android.internal.widget.remotecompose.core.VariableSupport
    public void registerListening(RemoteContext remoteContext) {
        int i = this.mTextId;
        if (i != -1) {
            remoteContext.listensTo(i, this);
        }
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.VariableSupport
    public void updateVariables(RemoteContext remoteContext) {
        String text = remoteContext.getText(this.mTextId);
        if (text == null || !text.equalsIgnoreCase(this.mCachedString)) {
            this.mNewString = text;
            if (this.mType == -1) {
                int i = this.mFontFamilyId;
                if (i != -1) {
                    String text2 = remoteContext.getText(i);
                    if (text2 != null) {
                        this.mType = 0;
                        if (text2.equalsIgnoreCase("default")) {
                            this.mType = 0;
                        } else if (text2.equalsIgnoreCase(Typeface.DEFAULT_FAMILY)) {
                            this.mType = 1;
                        } else if (text2.equalsIgnoreCase("serif")) {
                            this.mType = 2;
                        } else if (text2.equalsIgnoreCase("monospace")) {
                            this.mType = 3;
                        }
                    }
                } else {
                    this.mType = 0;
                }
            }
            if (this.mHorizontalScrollDelegate != null) {
                this.mHorizontalScrollDelegate.reset();
            }
            if (this.mVerticalScrollDelegate != null) {
                this.mVerticalScrollDelegate.reset();
            }
            invalidateMeasure();
        }
    }

    public TextLayout(Component component, int i, int i2, float f, float f2, float f3, float f4, int i3, int i4, float f5, int i5, float f6, int i6, int i7, int i8, int i9) {
        super(component, i, i2, f, f2, f3, f4);
        this.mTextId = -1;
        this.mColor = 0;
        this.mFontSize = 16.0f;
        this.mFontStyle = 0;
        this.mFontWeight = 400.0f;
        this.mFontFamilyId = -1;
        this.mTextAlign = -1;
        this.mOverflow = 1;
        this.mMaxLines = Integer.MAX_VALUE;
        this.mType = -1;
        this.mTextW = -1.0f;
        this.mTextH = -1.0f;
        this.mCachedSize = new Size(0.0f, 0.0f);
        this.mCachedString = "";
        this.mPaint = new PaintBundle();
        this.mTextId = i3;
        this.mColor = i4;
        this.mFontSize = f5;
        this.mFontStyle = i5;
        this.mFontWeight = f6;
        this.mFontFamilyId = i6;
        this.mTextAlign = i7;
        this.mOverflow = i8;
        this.mMaxLines = i9;
    }

    public TextLayout(Component component, int i, int i2, int i3, int i4, float f, int i5, float f2, int i6, int i7, int i8, int i9) {
        this(component, i, i2, 0.0f, 0.0f, 0.0f, 0.0f, i3, i4, f, i5, f2, i6, i7, i8, i9);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00f2  */
    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void paintingComponent(PaintContext paintContext) {
        PaintContext paintContext2;
        Component component = paintContext.getContext().mLastComponent;
        paintContext.getContext().mLastComponent = this;
        paintContext.save();
        paintContext.translate(this.mX, this.mY);
        if (this.mGraphicsLayerModifier != null) {
            paintContext.startGraphicsLayer((int) getWidth(), (int) getHeight());
            this.mCachedAttributes.clear();
            this.mGraphicsLayerModifier.fillInAttributes(this.mCachedAttributes);
            paintContext.setGraphicsLayer(this.mCachedAttributes);
        }
        this.mComponentModifiers.paint(paintContext);
        float f = this.mPaddingLeft;
        float f2 = this.mPaddingTop;
        paintContext.translate(f, f2);
        paintContext.savePaint();
        this.mPaint.reset();
        this.mPaint.setStyle(0);
        this.mPaint.setColor(this.mColor);
        this.mPaint.setTextSize(this.mFontSize);
        this.mPaint.setTextStyle(this.mType, (int) this.mFontWeight, this.mFontStyle == 1);
        paintContext.replacePaint(this.mPaint);
        String str = this.mCachedString;
        if (str == null) {
            return;
        }
        int length = str.length();
        Platform.ComputedTextLayout computedTextLayout = this.mComputedTextLayout;
        if (computedTextLayout != null) {
            paintContext.drawComplexText(computedTextLayout);
            paintContext2 = paintContext;
        } else {
            float f3 = this.mTextX;
            int i = this.mTextAlign;
            if (i == 2) {
                f3 = ((this.mWidth - this.mPaddingLeft) - this.mPaddingRight) - this.mTextW;
                float f4 = f3;
                if (this.mTextW <= (this.mWidth - this.mPaddingLeft) - this.mPaddingRight) {
                    paintContext.save();
                    paintContext.clipRect(0.0f, 0.0f, (this.mWidth - this.mPaddingLeft) - this.mPaddingRight, (this.mHeight - this.mPaddingTop) - this.mPaddingBottom);
                    paintContext.translate(getScrollX(), getScrollY());
                    paintContext2 = paintContext;
                    paintContext2.drawTextRun(this.mTextId, 0, length, 0, 0, f4, this.mTextY, false);
                    paintContext2.restore();
                } else {
                    paintContext2 = paintContext;
                    paintContext2.drawTextRun(this.mTextId, 0, length, 0, 0, f4, this.mTextY, false);
                }
            } else {
                if (i == 3) {
                    f3 = (((this.mWidth - this.mPaddingLeft) - this.mPaddingRight) - this.mTextW) / 2.0f;
                } else if (i == 6) {
                }
                float f42 = f3;
                if (this.mTextW <= (this.mWidth - this.mPaddingLeft) - this.mPaddingRight) {
                }
            }
        }
        paintContext2.restorePaint();
        if (this.mGraphicsLayerModifier != null) {
            paintContext2.endGraphicsLayer();
        }
        paintContext2.translate(-f, -f2);
        paintContext2.restore();
        paintContext2.getContext().mLastComponent = component;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public String toString() {
        return "TEXT_LAYOUT [" + this.mComponentId + ":" + this.mAnimationId + "] (" + this.mX + ", " + this.mY + " - " + this.mWidth + " x " + this.mHeight + ") " + Component.Visibility.toString(this.mVisibility);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component
    protected String getSerializedName() {
        return "TEXT_LAYOUT";
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.SerializableToString
    public void serializeToString(int i, StringSerializer stringSerializer) {
        stringSerializer.append(i, getSerializedName() + " [" + this.mComponentId + ":" + this.mAnimationId + "] = [" + this.mX + ", " + this.mY + ", " + this.mWidth + ", " + this.mHeight + "] " + Component.Visibility.toString(this.mVisibility) + " (" + this.mTextId + ":\"" + this.mCachedString + "\")");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeSize(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass) {
        super.computeSize(paintContext, f, f2, f3, f4, measurePass);
        computeWrapSize(paintContext, f2, f4, true, true, measurePass, this.mCachedSize);
        ComponentMeasure componentMeasure = measurePass.get(this);
        componentMeasure.setW(this.mCachedSize.getWidth());
        componentMeasure.setH(this.mCachedSize.getHeight());
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager
    public void computeWrapSize(PaintContext paintContext, float f, float f2, boolean z, boolean z2, MeasurePass measurePass, Size size) {
        int i;
        boolean z3;
        float f3;
        int i2;
        paintContext.savePaint();
        this.mPaint.reset();
        this.mPaint.setTextSize(this.mFontSize);
        this.mPaint.setTextStyle(this.mType, (int) this.mFontWeight, this.mFontStyle == 1);
        this.mPaint.setColor(this.mColor);
        paintContext.replacePaint(this.mPaint);
        float[] fArr = new float[4];
        String str = this.mNewString;
        if (str != null && !str.equals(this.mCachedString)) {
            this.mCachedString = this.mNewString;
        }
        if (this.mCachedString == null) {
            return;
        }
        int i3 = (this.mMaxLines == 1 && ((i2 = this.mOverflow) == 4 || i2 == 5 || i2 == 3)) ? 14 : 6;
        if ((i3 & 8) != 8) {
            for (int i4 = 0; i4 < this.mCachedString.length(); i4++) {
                char cCharAt = this.mCachedString.charAt(i4);
                if (cCharAt == '\n' || cCharAt == '\t') {
                    i = 14;
                    z3 = true;
                    break;
                }
            }
            i = i3;
            z3 = false;
        } else {
            i = i3;
            z3 = false;
        }
        if (!z3) {
            paintContext.getTextBounds(this.mTextId, 0, this.mCachedString.length(), i, fArr);
        }
        if (z3 || (fArr[2] - fArr[1] > f && this.mMaxLines > 1 && f > 0.0f)) {
            f3 = f;
            Platform.ComputedTextLayout computedTextLayoutLayoutComplexText = paintContext.layoutComplexText(this.mTextId, 0, this.mCachedString.length(), this.mTextAlign, this.mOverflow, this.mMaxLines, f3, i);
            this.mComputedTextLayout = computedTextLayoutLayoutComplexText;
            if (computedTextLayoutLayoutComplexText != null) {
                fArr[0] = 0.0f;
                fArr[1] = 0.0f;
                fArr[2] = computedTextLayoutLayoutComplexText.getWidth();
                fArr[3] = this.mComputedTextLayout.getHeight();
            }
        } else {
            this.mComputedTextLayout = null;
            f3 = f;
        }
        paintContext.restorePaint();
        float f4 = fArr[2] - fArr[0];
        float f5 = fArr[3] - fArr[1];
        size.setWidth(Math.min(f3, f4));
        this.mTextX = -fArr[0];
        size.setHeight(Math.min(f2, f5));
        this.mTextY = -fArr[1];
        this.mTextW = f4;
        this.mTextH = f5;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicHeight(RemoteContext remoteContext) {
        return this.mTextH;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.managers.LayoutManager, com.android.internal.widget.remotecompose.core.operations.layout.Component
    public float minIntrinsicWidth(RemoteContext remoteContext) {
        return this.mTextW;
    }

    public static String name() {
        return "TextLayout";
    }

    public static void apply(WireBuffer wireBuffer, int i, int i2, int i3, int i4, float f, int i5, float f2, int i6, int i7, int i8, int i9) {
        wireBuffer.start(id());
        wireBuffer.writeInt(i);
        wireBuffer.writeInt(i2);
        wireBuffer.writeInt(i3);
        wireBuffer.writeInt(i4);
        wireBuffer.writeFloat(f);
        wireBuffer.writeInt(i5);
        wireBuffer.writeFloat(f2);
        wireBuffer.writeInt(i6);
        wireBuffer.writeInt(i7);
        wireBuffer.writeInt(i8);
        wireBuffer.writeInt(i9);
    }

    public static void read(WireBuffer wireBuffer, List<Operation> list) {
        list.add(new TextLayout(null, wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readInt(), wireBuffer.readFloat(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt(), wireBuffer.readInt()));
    }

    public static void documentation(DocumentationBuilder documentationBuilder) {
        documentationBuilder.operation("Layout Operations", id(), name()).description("Text layout implementation.\n\n").field(0, "COMPONENT_ID", "unique id for this component").field(0, "ANIMATION_ID", "id used to match components, for animation purposes").field(0, "COLOR", "text color").field(1, "FONT_SIZE", "font size").field(0, "FONT_STYLE", "font style (0 = normal, 1 = italic)").field(1, "FONT_WEIGHT", "font weight (1-1000, normal = 400)").field(0, "FONT_FAMILY_ID", "font family id");
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.Operation
    public void write(WireBuffer wireBuffer) {
        apply(wireBuffer, this.mComponentId, this.mAnimationId, this.mTextId, this.mColor, this.mFontSize, this.mFontStyle, this.mFontWeight, this.mFontFamilyId, this.mTextAlign, this.mOverflow, this.mMaxLines);
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent, com.android.internal.widget.remotecompose.core.operations.layout.Component, com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        super.serialize(mapSerializer);
        mapSerializer.add("textId", Integer.valueOf(this.mTextId));
        mapSerializer.add("color", Utils.colorInt(this.mColor));
        mapSerializer.add("fontSize", Float.valueOf(this.mFontSize));
        mapSerializer.add("fontStyle", Integer.valueOf(this.mFontStyle));
        mapSerializer.add("fontWeight", Float.valueOf(this.mFontWeight));
        mapSerializer.add("fontFamilyId", Integer.valueOf(this.mFontFamilyId));
        mapSerializer.add("textAlign", Integer.valueOf(this.mTextAlign));
    }
}
