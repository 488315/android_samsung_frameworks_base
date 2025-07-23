package com.android.internal.widget.remotecompose.core;

import com.android.internal.widget.remotecompose.core.operations.BitmapData;
import com.android.internal.widget.remotecompose.core.operations.BitmapFontData;
import com.android.internal.widget.remotecompose.core.operations.ClickArea;
import com.android.internal.widget.remotecompose.core.operations.ClipPath;
import com.android.internal.widget.remotecompose.core.operations.ClipRect;
import com.android.internal.widget.remotecompose.core.operations.ColorAttribute;
import com.android.internal.widget.remotecompose.core.operations.ColorConstant;
import com.android.internal.widget.remotecompose.core.operations.ColorExpression;
import com.android.internal.widget.remotecompose.core.operations.ComponentValue;
import com.android.internal.widget.remotecompose.core.operations.ConditionalOperations;
import com.android.internal.widget.remotecompose.core.operations.DataListFloat;
import com.android.internal.widget.remotecompose.core.operations.DataListIds;
import com.android.internal.widget.remotecompose.core.operations.DataMapIds;
import com.android.internal.widget.remotecompose.core.operations.DataMapLookup;
import com.android.internal.widget.remotecompose.core.operations.DebugMessage;
import com.android.internal.widget.remotecompose.core.operations.DrawArc;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmap;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmapFontText;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmapInt;
import com.android.internal.widget.remotecompose.core.operations.DrawBitmapScaled;
import com.android.internal.widget.remotecompose.core.operations.DrawCircle;
import com.android.internal.widget.remotecompose.core.operations.DrawContent;
import com.android.internal.widget.remotecompose.core.operations.DrawLine;
import com.android.internal.widget.remotecompose.core.operations.DrawOval;
import com.android.internal.widget.remotecompose.core.operations.DrawPath;
import com.android.internal.widget.remotecompose.core.operations.DrawRect;
import com.android.internal.widget.remotecompose.core.operations.DrawRoundRect;
import com.android.internal.widget.remotecompose.core.operations.DrawSector;
import com.android.internal.widget.remotecompose.core.operations.DrawText;
import com.android.internal.widget.remotecompose.core.operations.DrawTextAnchored;
import com.android.internal.widget.remotecompose.core.operations.DrawTextOnPath;
import com.android.internal.widget.remotecompose.core.operations.DrawTweenPath;
import com.android.internal.widget.remotecompose.core.operations.FloatConstant;
import com.android.internal.widget.remotecompose.core.operations.FloatExpression;
import com.android.internal.widget.remotecompose.core.operations.FloatFunctionCall;
import com.android.internal.widget.remotecompose.core.operations.FloatFunctionDefine;
import com.android.internal.widget.remotecompose.core.operations.HapticFeedback;
import com.android.internal.widget.remotecompose.core.operations.Header;
import com.android.internal.widget.remotecompose.core.operations.ImageAttribute;
import com.android.internal.widget.remotecompose.core.operations.IntegerExpression;
import com.android.internal.widget.remotecompose.core.operations.MatrixRestore;
import com.android.internal.widget.remotecompose.core.operations.MatrixRotate;
import com.android.internal.widget.remotecompose.core.operations.MatrixSave;
import com.android.internal.widget.remotecompose.core.operations.MatrixScale;
import com.android.internal.widget.remotecompose.core.operations.MatrixSkew;
import com.android.internal.widget.remotecompose.core.operations.MatrixTranslate;
import com.android.internal.widget.remotecompose.core.operations.NamedVariable;
import com.android.internal.widget.remotecompose.core.operations.PaintData;
import com.android.internal.widget.remotecompose.core.operations.ParticlesCreate;
import com.android.internal.widget.remotecompose.core.operations.ParticlesLoop;
import com.android.internal.widget.remotecompose.core.operations.PathAppend;
import com.android.internal.widget.remotecompose.core.operations.PathCombine;
import com.android.internal.widget.remotecompose.core.operations.PathCreate;
import com.android.internal.widget.remotecompose.core.operations.PathData;
import com.android.internal.widget.remotecompose.core.operations.PathTween;
import com.android.internal.widget.remotecompose.core.operations.RootContentBehavior;
import com.android.internal.widget.remotecompose.core.operations.RootContentDescription;
import com.android.internal.widget.remotecompose.core.operations.ShaderData;
import com.android.internal.widget.remotecompose.core.operations.TextAttribute;
import com.android.internal.widget.remotecompose.core.operations.TextData;
import com.android.internal.widget.remotecompose.core.operations.TextFromFloat;
import com.android.internal.widget.remotecompose.core.operations.TextLength;
import com.android.internal.widget.remotecompose.core.operations.TextLookup;
import com.android.internal.widget.remotecompose.core.operations.TextLookupInt;
import com.android.internal.widget.remotecompose.core.operations.TextMeasure;
import com.android.internal.widget.remotecompose.core.operations.TextMerge;
import com.android.internal.widget.remotecompose.core.operations.Theme;
import com.android.internal.widget.remotecompose.core.operations.TimeAttribute;
import com.android.internal.widget.remotecompose.core.operations.TouchExpression;
import com.android.internal.widget.remotecompose.core.operations.layout.CanvasContent;
import com.android.internal.widget.remotecompose.core.operations.layout.CanvasOperations;
import com.android.internal.widget.remotecompose.core.operations.layout.ClickModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.ComponentStart;
import com.android.internal.widget.remotecompose.core.operations.layout.ContainerEnd;
import com.android.internal.widget.remotecompose.core.operations.layout.ImpulseOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.ImpulseProcess;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponentContent;
import com.android.internal.widget.remotecompose.core.operations.layout.LoopOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.RootLayoutComponent;
import com.android.internal.widget.remotecompose.core.operations.layout.TouchCancelModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.TouchDownModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.TouchUpModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.animation.AnimationSpec;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.BoxLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.CanvasLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.CollapsibleColumnLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.CollapsibleRowLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.ColumnLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.FitBoxLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.ImageLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.RowLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.StateLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.managers.TextLayout;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.BackgroundModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.BorderModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ClipRectModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.CollapsiblePriorityModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ComponentVisibilityOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.DrawContentOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.GraphicsLayerModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HeightInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HeightModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HostActionMetadataOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HostActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.HostNamedActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.MarqueeModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.OffsetModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.PaddingModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.RippleModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.RoundedClipRectModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.RunActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ScrollModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ValueFloatChangeActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ValueFloatExpressionChangeActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ValueIntegerChangeActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ValueIntegerExpressionChangeActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ValueStringChangeActionOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.WidthInModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.WidthModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.layout.modifiers.ZIndexModifierOperation;
import com.android.internal.widget.remotecompose.core.operations.utilities.IntMap;
import com.android.internal.widget.remotecompose.core.semantics.CoreSemantics;
import com.android.internal.widget.remotecompose.core.types.BooleanConstant;
import com.android.internal.widget.remotecompose.core.types.IntegerConstant;
import com.android.internal.widget.remotecompose.core.types.LongConstant;
import java.util.List;

/* loaded from: classes6.dex */
public class Operations {
    public static final int ACCESSIBILITY_SEMANTICS = 250;
    public static final int ANIMATED_FLOAT = 81;
    public static final int ANIMATION_SPEC = 14;
    public static final int ATTRIBUTE_COLOR = 180;
    public static final int ATTRIBUTE_IMAGE = 171;
    public static final int ATTRIBUTE_TEXT = 170;
    public static final int ATTRIBUTE_TIME = 172;
    public static final int CANVAS_OPERATIONS = 173;
    public static final int CLICK_AREA = 64;
    public static final int CLIP_PATH = 38;
    public static final int CLIP_RECT = 39;
    public static final int COLOR_CONSTANT = 138;
    public static final int COLOR_EXPRESSIONS = 134;
    public static final int COMPONENT_START = 2;
    public static final int COMPONENT_VALUE = 150;
    public static final int CONDITIONAL_OPERATIONS = 178;
    public static final int CONTAINER_END = 214;
    public static final int DATA_BITMAP = 101;
    public static final int DATA_BITMAP_FONT = 167;
    public static final int DATA_BOOLEAN = 143;
    public static final int DATA_FLOAT = 80;
    public static final int DATA_INT = 140;
    public static final int DATA_LONG = 148;
    public static final int DATA_MAP_LOOKUP = 154;
    public static final int DATA_PATH = 123;
    public static final int DATA_SHADER = 45;
    public static final int DATA_TEXT = 102;
    public static final int DEBUG_MESSAGE = 179;
    public static final int DRAW_ARC = 152;
    public static final int DRAW_BITMAP = 44;
    public static final int DRAW_BITMAP_FONT_TEXT_RUN = 48;
    public static final int DRAW_BITMAP_INT = 66;
    public static final int DRAW_BITMAP_SCALED = 149;
    public static final int DRAW_CIRCLE = 46;
    public static final int DRAW_CONTENT = 139;
    public static final int DRAW_LINE = 47;
    public static final int DRAW_OVAL = 56;
    public static final int DRAW_PATH = 124;
    public static final int DRAW_RECT = 42;
    public static final int DRAW_ROUND_RECT = 51;
    public static final int DRAW_SECTOR = 52;
    public static final int DRAW_TEXT_ANCHOR = 133;
    public static final int DRAW_TEXT_ON_PATH = 53;
    public static final int DRAW_TEXT_RUN = 43;
    public static final int DRAW_TWEEN_PATH = 125;
    public static final int FLOAT_LIST = 147;
    public static final int FUNCTION_CALL = 166;
    public static final int FUNCTION_DEFINE = 168;
    public static final int HAPTIC_FEEDBACK = 177;
    public static final int HEADER = 0;
    public static final int HOST_ACTION = 209;
    public static final int HOST_METADATA_ACTION = 216;
    public static final int HOST_NAMED_ACTION = 210;
    public static final int ID_LIST = 146;
    public static final int ID_MAP = 145;
    public static final int IMPULSE_PROCESS = 165;
    public static final int IMPULSE_START = 164;
    public static final int INTEGER_EXPRESSION = 144;
    public static final int LAYOUT_BOX = 202;
    public static final int LAYOUT_CANVAS = 205;
    public static final int LAYOUT_CANVAS_CONTENT = 207;
    public static final int LAYOUT_COLLAPSIBLE_COLUMN = 233;
    public static final int LAYOUT_COLLAPSIBLE_ROW = 230;
    public static final int LAYOUT_COLUMN = 204;
    public static final int LAYOUT_CONTENT = 201;
    public static final int LAYOUT_FIT_BOX = 176;
    public static final int LAYOUT_IMAGE = 234;
    public static final int LAYOUT_ROOT = 200;
    public static final int LAYOUT_ROW = 203;
    public static final int LAYOUT_STATE = 217;
    public static final int LAYOUT_TEXT = 208;
    public static final int LOAD_BITMAP = 4;
    public static final int LOOP_START = 215;
    public static final int MATRIX_RESTORE = 131;
    public static final int MATRIX_ROTATE = 129;
    public static final int MATRIX_SAVE = 130;
    public static final int MATRIX_SCALE = 126;
    public static final int MATRIX_SET = 132;
    public static final int MATRIX_SKEW = 128;
    public static final int MATRIX_TRANSLATE = 127;
    public static final int MODIFIER_BACKGROUND = 55;
    public static final int MODIFIER_BORDER = 107;
    public static final int MODIFIER_CLICK = 59;
    public static final int MODIFIER_CLIP_RECT = 108;
    public static final int MODIFIER_COLLAPSIBLE_PRIORITY = 235;
    public static final int MODIFIER_DRAW_CONTENT = 174;
    public static final int MODIFIER_GRAPHICS_LAYER = 224;
    public static final int MODIFIER_HEIGHT = 67;
    public static final int MODIFIER_HEIGHT_IN = 232;
    public static final int MODIFIER_MARQUEE = 228;
    public static final int MODIFIER_OFFSET = 221;
    public static final int MODIFIER_PADDING = 58;
    public static final int MODIFIER_RIPPLE = 229;
    public static final int MODIFIER_ROUNDED_CLIP_RECT = 54;
    public static final int MODIFIER_SCROLL = 226;
    public static final int MODIFIER_TOUCH_CANCEL = 225;
    public static final int MODIFIER_TOUCH_DOWN = 219;
    public static final int MODIFIER_TOUCH_UP = 220;
    public static final int MODIFIER_VISIBILITY = 211;
    public static final int MODIFIER_WIDTH = 16;
    public static final int MODIFIER_WIDTH_IN = 231;
    public static final int MODIFIER_ZINDEX = 223;
    public static final int NAMED_VARIABLE = 137;
    public static final int PAINT_VALUES = 40;
    public static final int PARTICLE_DEFINE = 161;
    public static final int PARTICLE_LOOP = 163;
    public static final int PARTICLE_PROCESS = 162;
    public static final int PATH_ADD = 160;
    public static final int PATH_COMBINE = 175;
    public static final int PATH_CREATE = 159;
    public static final int PATH_TWEEN = 158;
    public static final int ROOT_CONTENT_BEHAVIOR = 65;
    public static final int ROOT_CONTENT_DESCRIPTION = 103;
    public static final int RUN_ACTION = 236;
    public static final int TEXT_FROM_FLOAT = 135;
    public static final int TEXT_LENGTH = 156;
    public static final int TEXT_LOOKUP = 151;
    public static final int TEXT_LOOKUP_INT = 153;
    public static final int TEXT_MEASURE = 155;
    public static final int TEXT_MERGE = 136;
    public static final int THEME = 63;
    public static final int TOUCH_EXPRESSION = 157;
    public static final int VALUE_FLOAT_CHANGE_ACTION = 222;
    public static final int VALUE_FLOAT_EXPRESSION_CHANGE_ACTION = 227;
    public static final int VALUE_INTEGER_CHANGE_ACTION = 212;
    public static final int VALUE_INTEGER_EXPRESSION_CHANGE_ACTION = 218;
    public static final int VALUE_STRING_CHANGE_ACTION = 213;
    public static UniqueIntMap<CompanionOperation> map;

    static {
        UniqueIntMap<CompanionOperation> uniqueIntMap = new UniqueIntMap<>();
        map = uniqueIntMap;
        uniqueIntMap.put(0, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                Header.read(wireBuffer, list);
            }
        });
        map.put(66, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda35
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawBitmapInt.read(wireBuffer, list);
            }
        });
        map.put(101, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda46
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                BitmapData.read(wireBuffer, list);
            }
        });
        map.put(167, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda57
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                BitmapFontData.read(wireBuffer, list);
            }
        });
        map.put(102, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda68
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextData.read(wireBuffer, list);
            }
        });
        map.put(63, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda79
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                Theme.read(wireBuffer, list);
            }
        });
        map.put(64, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda90
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ClickArea.read(wireBuffer, list);
            }
        });
        map.put(65, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda101
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                RootContentBehavior.read(wireBuffer, list);
            }
        });
        map.put(103, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda112
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                RootContentDescription.read(wireBuffer, list);
            }
        });
        map.put(52, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda123
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawSector.read(wireBuffer, list);
            }
        });
        map.put(44, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda11
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawBitmap.read(wireBuffer, list);
            }
        });
        map.put(46, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda22
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawCircle.read(wireBuffer, list);
            }
        });
        map.put(47, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda27
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawLine.read(wireBuffer, list);
            }
        });
        map.put(56, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda28
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawOval.read(wireBuffer, list);
            }
        });
        map.put(124, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda29
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawPath.read(wireBuffer, list);
            }
        });
        map.put(42, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda30
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawRect.read(wireBuffer, list);
            }
        });
        map.put(51, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda31
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawRoundRect.read(wireBuffer, list);
            }
        });
        map.put(53, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda32
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawTextOnPath.read(wireBuffer, list);
            }
        });
        map.put(43, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda33
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawText.read(wireBuffer, list);
            }
        });
        map.put(48, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda34
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawBitmapFontText.read(wireBuffer, list);
            }
        });
        map.put(125, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda36
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawTweenPath.read(wireBuffer, list);
            }
        });
        map.put(123, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda37
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                PathData.read(wireBuffer, list);
            }
        });
        map.put(40, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda38
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                PaintData.read(wireBuffer, list);
            }
        });
        map.put(131, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda39
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                MatrixRestore.read(wireBuffer, list);
            }
        });
        map.put(129, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda40
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                MatrixRotate.read(wireBuffer, list);
            }
        });
        map.put(130, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda41
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                MatrixSave.read(wireBuffer, list);
            }
        });
        map.put(126, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda42
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                MatrixScale.read(wireBuffer, list);
            }
        });
        map.put(128, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda43
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                MatrixSkew.read(wireBuffer, list);
            }
        });
        map.put(127, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda44
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                MatrixTranslate.read(wireBuffer, list);
            }
        });
        map.put(38, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda45
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ClipPath.read(wireBuffer, list);
            }
        });
        map.put(39, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda47
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ClipRect.read(wireBuffer, list);
            }
        });
        map.put(45, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda48
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ShaderData.read(wireBuffer, list);
            }
        });
        map.put(80, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda49
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                FloatConstant.read(wireBuffer, list);
            }
        });
        map.put(81, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda50
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                FloatExpression.read(wireBuffer, list);
            }
        });
        map.put(133, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda51
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawTextAnchored.read(wireBuffer, list);
            }
        });
        map.put(134, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda52
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ColorExpression.read(wireBuffer, list);
            }
        });
        map.put(135, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda53
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextFromFloat.read(wireBuffer, list);
            }
        });
        map.put(136, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda54
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextMerge.read(wireBuffer, list);
            }
        });
        map.put(137, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda55
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                NamedVariable.read(wireBuffer, list);
            }
        });
        map.put(138, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda56
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ColorConstant.read(wireBuffer, list);
            }
        });
        map.put(140, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda58
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                IntegerConstant.read(wireBuffer, list);
            }
        });
        map.put(144, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda59
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                IntegerExpression.read(wireBuffer, list);
            }
        });
        map.put(143, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda60
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                BooleanConstant.read(wireBuffer, list);
            }
        });
        map.put(145, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda61
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DataMapIds.read(wireBuffer, list);
            }
        });
        map.put(146, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda62
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DataListIds.read(wireBuffer, list);
            }
        });
        map.put(147, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda63
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DataListFloat.read(wireBuffer, list);
            }
        });
        map.put(148, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda64
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                LongConstant.read(wireBuffer, list);
            }
        });
        map.put(149, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda65
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawBitmapScaled.read(wireBuffer, list);
            }
        });
        map.put(151, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda66
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextLookup.read(wireBuffer, list);
            }
        });
        map.put(153, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda67
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextLookupInt.read(wireBuffer, list);
            }
        });
        map.put(215, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda69
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                LoopOperation.read(wireBuffer, list);
            }
        });
        map.put(2, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda70
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ComponentStart.read(wireBuffer, list);
            }
        });
        map.put(14, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda71
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                AnimationSpec.read(wireBuffer, list);
            }
        });
        map.put(16, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda72
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                WidthModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(67, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda73
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                HeightModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(231, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda74
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                WidthInModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(232, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda75
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                HeightInModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(235, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda76
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                CollapsiblePriorityModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(58, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda77
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                PaddingModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(55, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda78
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                BackgroundModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(107, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda80
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                BorderModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(54, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda81
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                RoundedClipRectModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(108, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda82
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ClipRectModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(59, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda83
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ClickModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(219, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda84
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TouchDownModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(220, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda85
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TouchUpModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(225, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda86
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TouchCancelModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(211, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda87
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ComponentVisibilityOperation.read(wireBuffer, list);
            }
        });
        map.put(221, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda88
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                OffsetModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(223, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda89
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ZIndexModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(224, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda91
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                GraphicsLayerModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(226, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda92
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ScrollModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(228, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda93
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                MarqueeModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(229, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda94
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                RippleModifierOperation.read(wireBuffer, list);
            }
        });
        map.put(174, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda95
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawContentOperation.read(wireBuffer, list);
            }
        });
        map.put(214, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda96
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ContainerEnd.read(wireBuffer, list);
            }
        });
        map.put(236, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda97
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                RunActionOperation.read(wireBuffer, list);
            }
        });
        map.put(209, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda98
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                HostActionOperation.read(wireBuffer, list);
            }
        });
        map.put(216, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda99
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                HostActionMetadataOperation.read(wireBuffer, list);
            }
        });
        map.put(210, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda100
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                HostNamedActionOperation.read(wireBuffer, list);
            }
        });
        map.put(212, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda102
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ValueIntegerChangeActionOperation.read(wireBuffer, list);
            }
        });
        map.put(218, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda103
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ValueIntegerExpressionChangeActionOperation.read(wireBuffer, list);
            }
        });
        map.put(213, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda104
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ValueStringChangeActionOperation.read(wireBuffer, list);
            }
        });
        map.put(222, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda105
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ValueFloatChangeActionOperation.read(wireBuffer, list);
            }
        });
        map.put(227, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda106
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ValueFloatExpressionChangeActionOperation.read(wireBuffer, list);
            }
        });
        map.put(200, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda107
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                RootLayoutComponent.read(wireBuffer, list);
            }
        });
        map.put(201, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda108
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                LayoutComponentContent.read(wireBuffer, list);
            }
        });
        map.put(202, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda109
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                BoxLayout.read(wireBuffer, list);
            }
        });
        map.put(176, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda110
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                FitBoxLayout.read(wireBuffer, list);
            }
        });
        map.put(204, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda111
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ColumnLayout.read(wireBuffer, list);
            }
        });
        map.put(233, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda113
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                CollapsibleColumnLayout.read(wireBuffer, list);
            }
        });
        map.put(203, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda114
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                RowLayout.read(wireBuffer, list);
            }
        });
        map.put(230, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda115
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                CollapsibleRowLayout.read(wireBuffer, list);
            }
        });
        map.put(205, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda116
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                CanvasLayout.read(wireBuffer, list);
            }
        });
        map.put(207, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda117
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                CanvasContent.read(wireBuffer, list);
            }
        });
        map.put(208, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda118
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextLayout.read(wireBuffer, list);
            }
        });
        map.put(234, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda119
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ImageLayout.read(wireBuffer, list);
            }
        });
        map.put(217, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda120
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                StateLayout.read(wireBuffer, list);
            }
        });
        map.put(139, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda121
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawContent.read(wireBuffer, list);
            }
        });
        map.put(150, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda122
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ComponentValue.read(wireBuffer, list);
            }
        });
        map.put(152, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DrawArc.read(wireBuffer, list);
            }
        });
        map.put(154, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda2
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DataMapLookup.read(wireBuffer, list);
            }
        });
        map.put(155, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda3
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextMeasure.read(wireBuffer, list);
            }
        });
        map.put(156, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda4
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextLength.read(wireBuffer, list);
            }
        });
        map.put(157, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda5
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TouchExpression.read(wireBuffer, list);
            }
        });
        map.put(158, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda6
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                PathTween.read(wireBuffer, list);
            }
        });
        map.put(159, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda7
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                PathCreate.read(wireBuffer, list);
            }
        });
        map.put(160, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda8
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                PathAppend.read(wireBuffer, list);
            }
        });
        map.put(164, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda9
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ImpulseOperation.read(wireBuffer, list);
            }
        });
        map.put(165, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda10
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ImpulseProcess.read(wireBuffer, list);
            }
        });
        map.put(161, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda12
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ParticlesCreate.read(wireBuffer, list);
            }
        });
        map.put(163, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda13
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ParticlesLoop.read(wireBuffer, list);
            }
        });
        map.put(166, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda14
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                FloatFunctionCall.read(wireBuffer, list);
            }
        });
        map.put(168, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda15
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                FloatFunctionDefine.read(wireBuffer, list);
            }
        });
        map.put(173, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda16
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                CanvasOperations.read(wireBuffer, list);
            }
        });
        map.put(250, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda17
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                CoreSemantics.read(wireBuffer, list);
            }
        });
        map.put(171, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda18
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ImageAttribute.read(wireBuffer, list);
            }
        });
        map.put(170, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda19
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TextAttribute.read(wireBuffer, list);
            }
        });
        map.put(172, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda20
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                TimeAttribute.read(wireBuffer, list);
            }
        });
        map.put(175, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda21
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                PathCombine.read(wireBuffer, list);
            }
        });
        map.put(177, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda23
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                HapticFeedback.read(wireBuffer, list);
            }
        });
        map.put(178, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda24
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ConditionalOperations.read(wireBuffer, list);
            }
        });
        map.put(179, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda25
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                DebugMessage.read(wireBuffer, list);
            }
        });
        map.put(180, new CompanionOperation() { // from class: com.android.internal.widget.remotecompose.core.Operations$$ExternalSyntheticLambda26
            @Override // com.android.internal.widget.remotecompose.core.CompanionOperation
            public final void read(WireBuffer wireBuffer, List list) {
                ColorAttribute.read(wireBuffer, list);
            }
        });
    }

    static class UniqueIntMap<T> extends IntMap<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        UniqueIntMap() {
        }

        @Override // com.android.internal.widget.remotecompose.core.operations.utilities.IntMap
        public T put(int i, T t) {
            return (T) super.put(i, t);
        }
    }
}
