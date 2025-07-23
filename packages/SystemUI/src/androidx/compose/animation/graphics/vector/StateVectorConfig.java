package androidx.compose.animation.graphics.vector;

import androidx.compose.runtime.State;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.vector.VectorConfig;
import androidx.compose.ui.graphics.vector.VectorProperty;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class StateVectorConfig implements VectorConfig {
    public State fillAlphaState;
    public State fillColorState;
    public State pathDataState;
    public State pivotXState;
    public State pivotYState;
    public State rotationState;
    public State scaleXState;
    public State scaleYState;
    public State strokeAlphaState;
    public State strokeColorState;
    public State strokeWidthState;
    public State translateXState;
    public State translateYState;
    public State trimPathEndState;
    public State trimPathOffsetState;
    public State trimPathStartState;

    @Override // androidx.compose.ui.graphics.vector.VectorConfig
    public final Object getOrDefault(VectorProperty vectorProperty, Object obj) {
        List list;
        if (vectorProperty instanceof VectorProperty.Rotation) {
            State state = this.rotationState;
            if (state != null) {
                return Float.valueOf(((Number) state.getValue()).floatValue());
            }
        } else if (vectorProperty instanceof VectorProperty.PivotX) {
            State state2 = this.pivotXState;
            if (state2 != null) {
                return Float.valueOf(((Number) state2.getValue()).floatValue());
            }
        } else if (vectorProperty instanceof VectorProperty.PivotY) {
            State state3 = this.pivotYState;
            if (state3 != null) {
                return Float.valueOf(((Number) state3.getValue()).floatValue());
            }
        } else if (vectorProperty instanceof VectorProperty.ScaleX) {
            State state4 = this.scaleXState;
            if (state4 != null) {
                return Float.valueOf(((Number) state4.getValue()).floatValue());
            }
        } else if (vectorProperty instanceof VectorProperty.ScaleY) {
            State state5 = this.scaleYState;
            if (state5 != null) {
                return Float.valueOf(((Number) state5.getValue()).floatValue());
            }
        } else if (vectorProperty instanceof VectorProperty.TranslateX) {
            State state6 = this.translateXState;
            if (state6 != null) {
                return Float.valueOf(((Number) state6.getValue()).floatValue());
            }
        } else if (vectorProperty instanceof VectorProperty.TranslateY) {
            State state7 = this.translateYState;
            if (state7 != null) {
                return Float.valueOf(((Number) state7.getValue()).floatValue());
            }
        } else if (vectorProperty instanceof VectorProperty.PathData) {
            State state8 = this.pathDataState;
            if (state8 != null && (list = (List) state8.getValue()) != null) {
                return list;
            }
        } else {
            DefaultConstructorMarker defaultConstructorMarker = null;
            if (vectorProperty instanceof VectorProperty.Fill) {
                State state9 = this.fillColorState;
                if (state9 != null) {
                    return new SolidColor(((Color) state9.getValue()).value, defaultConstructorMarker);
                }
            } else if (vectorProperty instanceof VectorProperty.FillAlpha) {
                State state10 = this.fillAlphaState;
                if (state10 != null) {
                    return Float.valueOf(((Number) state10.getValue()).floatValue());
                }
            } else if (vectorProperty instanceof VectorProperty.Stroke) {
                State state11 = this.strokeColorState;
                if (state11 != null) {
                    return new SolidColor(((Color) state11.getValue()).value, defaultConstructorMarker);
                }
            } else if (vectorProperty instanceof VectorProperty.StrokeLineWidth) {
                State state12 = this.strokeWidthState;
                if (state12 != null) {
                    return Float.valueOf(((Number) state12.getValue()).floatValue());
                }
            } else if (vectorProperty instanceof VectorProperty.StrokeAlpha) {
                State state13 = this.strokeAlphaState;
                if (state13 != null) {
                    return Float.valueOf(((Number) state13.getValue()).floatValue());
                }
            } else if (vectorProperty instanceof VectorProperty.TrimPathStart) {
                State state14 = this.trimPathStartState;
                if (state14 != null) {
                    return Float.valueOf(((Number) state14.getValue()).floatValue());
                }
            } else if (vectorProperty instanceof VectorProperty.TrimPathEnd) {
                State state15 = this.trimPathEndState;
                if (state15 != null) {
                    return Float.valueOf(((Number) state15.getValue()).floatValue());
                }
            } else {
                if (!(vectorProperty instanceof VectorProperty.TrimPathOffset)) {
                    throw new NoWhenBranchMatchedException();
                }
                State state16 = this.trimPathOffsetState;
                if (state16 != null) {
                    return Float.valueOf(((Number) state16.getValue()).floatValue());
                }
            }
        }
        return obj;
    }
}
