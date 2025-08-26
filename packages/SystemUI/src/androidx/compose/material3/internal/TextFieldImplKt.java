package androidx.compose.material3.internal;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.ColorVectorConverterKt;
import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.animation.core.TransitionKt;
import androidx.compose.animation.core.TransitionState;
import androidx.compose.animation.core.TwoWayConverter;
import androidx.compose.animation.core.VectorConvertersKt;
import androidx.compose.foundation.interaction.FocusInteractionKt;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.material3.ContentColorKt;
import androidx.compose.material3.InteractiveComponentSizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.MotionSchemeKt;
import androidx.compose.material3.OutlinedTextFieldKt;
import androidx.compose.material3.TextFieldColors;
import androidx.compose.material3.TextFieldKt;
import androidx.compose.material3.TextFieldLabelPosition;
import androidx.compose.material3.TextFieldLabelScope;
import androidx.compose.material3.Typography;
import androidx.compose.material3.tokens.MotionSchemeKeyTokens;
import androidx.compose.material3.tokens.SmallIconButtonTokens;
import androidx.compose.material3.tokens.TypeScaleTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.CacheDrawScope;
import androidx.compose.ui.draw.DrawModifierKt;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScope;
import androidx.compose.ui.graphics.Outline;
import androidx.compose.ui.graphics.OutlineKt;
import androidx.compose.ui.graphics.ReusableGraphicsLayerScope;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.colorspace.ColorSpace;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.text.EmojiSupportMatch;
import androidx.compose.ui.text.ParagraphStyle;
import androidx.compose.ui.text.ParagraphStyleKt;
import androidx.compose.ui.text.PlatformParagraphStyle;
import androidx.compose.ui.text.PlatformSpanStyle;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.SpanStyleKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextDrawStyleKt;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.util.MathHelpersKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ULong;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference0Impl;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.reflect.KProperty;

/* loaded from: classes.dex */
public abstract class TextFieldImplKt {
    public static final float AboveLabelBottomPadding;
    public static final float AboveLabelHorizontalPadding;
    public static final float MinFocusedLabelLineHeight;
    public static final float MinSupportingTextLineHeight;
    public static final float MinTextLineHeight;
    public static final float PrefixSuffixTextPadding;
    public static final float SupportingTopPadding;
    public static final float TextFieldPadding;

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TextFieldType.values().length];
            try {
                iArr[TextFieldType.Filled.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TextFieldType.Outlined.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[InputPhase.values().length];
            try {
                iArr2[InputPhase.Focused.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[InputPhase.UnfocusedEmpty.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[InputPhase.UnfocusedNotEmpty.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        float f = 16;
        Dp.Companion companion = Dp.Companion;
        TextFieldPadding = f;
        float f2 = 4;
        AboveLabelHorizontalPadding = f2;
        AboveLabelBottomPadding = f2;
        SupportingTopPadding = f2;
        PrefixSuffixTextPadding = 2;
        MinTextLineHeight = 24;
        MinFocusedLabelLineHeight = f;
        MinSupportingTextLineHeight = f;
    }

    /* JADX WARN: Removed duplicated region for block: B:196:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x02ee  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x037f  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x03a4  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x03b0  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x03c2  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x03ca  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x0418  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x042b  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0454  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x046f  */
    /* JADX WARN: Removed duplicated region for block: B:288:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:291:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x04c4  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x04cc  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x04e5  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x04ec  */
    /* JADX WARN: Removed duplicated region for block: B:335:0x05e3  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x05ec  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x0609  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0612  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x0640  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x064e  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x066c  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x06a0  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x06a6  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x06bb  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x06ff  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x070e  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x0712  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0725  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0762  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0771  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x0774  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x07a7  */
    /* JADX WARN: Removed duplicated region for block: B:402:0x07b5  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x07b8  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x07c6  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x07d3  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x07ec  */
    /* JADX WARN: Removed duplicated region for block: B:413:0x07ef  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x07fd  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x080a  */
    /* JADX WARN: Removed duplicated region for block: B:422:0x0823  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0826  */
    /* JADX WARN: Removed duplicated region for block: B:429:0x0834  */
    /* JADX WARN: Removed duplicated region for block: B:430:0x083f  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0860  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0912  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x0973  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void CommonDecorationBox(final TextFieldType textFieldType, final CharSequence charSequence, final Function2 function2, final TextFieldLabelPosition textFieldLabelPosition, final Function3 function3, final Function2 function22, final Function2 function23, final Function2 function24, final Function2 function25, final Function2 function26, final Function2 function27, final boolean z, final boolean z2, final boolean z3, final InteractionSource interactionSource, final PaddingValues paddingValues, final TextFieldColors textFieldColors, final Function2 function28, Composer composer, final int i, final int i2) {
        int i3;
        int i4;
        TextFieldColors textFieldColors2;
        float f;
        int i5;
        float f2;
        int i6;
        float f3;
        int i7;
        float f4;
        int i8;
        float f5;
        int i9;
        boolean zChanged;
        String str;
        Object objRememberedValue;
        Composer$Companion$Empty$1 composer$Companion$Empty$1;
        Composer$Companion$Empty$1 composer$Companion$Empty$12;
        int i10;
        final Transition.TransitionAnimationState transitionAnimationState;
        ComposerImpl composerImpl;
        final Transition.TransitionAnimationState transitionAnimationState2;
        final TextStyle textStyle;
        final TextStyle textStyle2;
        final Transition.TransitionAnimationState transitionAnimationState3;
        ComposableLambdaImpl composableLambdaImpl;
        TextFieldColors textFieldColors3;
        long j;
        Object objRememberedValue2;
        TextFieldColors textFieldColors4;
        ComposableLambdaImpl composableLambdaImpl2;
        Object objRememberedValue3;
        final Transition.TransitionAnimationState transitionAnimationState4;
        ComposableLambdaImpl composableLambdaImpl3;
        ComposableLambdaImpl composableLambdaImplRememberComposableLambda;
        ComposableLambdaImpl composableLambdaImpl4;
        ComposableLambdaImpl composableLambdaImpl5;
        int i11;
        ComposerImpl composerImpl2;
        ComposerImpl composerImpl3 = (ComposerImpl) composer;
        composerImpl3.startRestartGroup(-1388996979);
        if ((i & 6) == 0) {
            i3 = i | (composerImpl3.changed(textFieldType) ? 4 : 2);
        } else {
            i3 = i;
        }
        if ((i & 48) == 0) {
            i3 |= composerImpl3.changedInstance(charSequence) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i3 |= composerImpl3.changedInstance(function2) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i3 |= composerImpl3.changed(textFieldLabelPosition) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i3 |= composerImpl3.changedInstance(function3) ? 16384 : 8192;
        }
        if ((i & 196608) == 0) {
            i3 |= composerImpl3.changedInstance(function22) ? 131072 : 65536;
        }
        int i12 = i & 1572864;
        int i13 = NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        if (i12 == 0) {
            i3 |= composerImpl3.changedInstance(function23) ? 1048576 : 524288;
        }
        if ((i & 12582912) == 0) {
            i3 |= composerImpl3.changedInstance(function24) ? 8388608 : 4194304;
        }
        if ((i & 100663296) == 0) {
            i3 |= composerImpl3.changedInstance(function25) ? 67108864 : 33554432;
        }
        if ((i & 805306368) == 0) {
            i3 |= composerImpl3.changedInstance(function26) ? VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS : 268435456;
        }
        int i14 = i3;
        if ((i2 & 6) == 0) {
            i4 = i2 | (composerImpl3.changedInstance(function27) ? 4 : 2);
        } else {
            i4 = i2;
        }
        if ((i2 & 48) == 0) {
            i4 |= composerImpl3.changed(z) ? 32 : 16;
        }
        if ((i2 & 384) == 0) {
            i4 |= composerImpl3.changed(z2) ? 256 : 128;
        }
        if ((i2 & 3072) == 0) {
            i4 |= composerImpl3.changed(z3) ? 2048 : 1024;
        }
        if ((i2 & 24576) == 0) {
            i4 |= composerImpl3.changed(interactionSource) ? 16384 : 8192;
        }
        if ((i2 & 196608) == 0) {
            i4 |= composerImpl3.changed(paddingValues) ? 131072 : 65536;
        }
        if ((i2 & 1572864) == 0) {
            textFieldColors2 = textFieldColors;
            if (composerImpl3.changed(textFieldColors2)) {
                i13 = 1048576;
            }
            i4 |= i13;
        } else {
            textFieldColors2 = textFieldColors;
        }
        if ((i2 & 12582912) == 0) {
            i4 |= composerImpl3.changedInstance(function28) ? 8388608 : 4194304;
        }
        int i15 = i4;
        if ((i14 & 306783379) == 306783378 && (i15 & 4793491) == 4793490 && composerImpl3.getSkipping()) {
            composerImpl3.skipToGroupEnd();
            composerImpl2 = composerImpl3;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox (TextFieldImpl.kt:98)");
            }
            boolean zBooleanValue = ((Boolean) FocusInteractionKt.collectIsFocusedAsState(interactionSource, composerImpl3, (i15 >> 12) & 14).getValue()).booleanValue();
            InputPhase inputPhase = zBooleanValue ? InputPhase.Focused : charSequence.length() == 0 ? InputPhase.UnfocusedEmpty : InputPhase.UnfocusedNotEmpty;
            long j2 = !z2 ? textFieldColors2.disabledLabelColor : z3 ? textFieldColors2.errorLabelColor : zBooleanValue ? textFieldColors2.focusedLabelColor : textFieldColors2.unfocusedLabelColor;
            MaterialTheme.INSTANCE.getClass();
            long j3 = j2;
            Typography typography = MaterialTheme.getTypography(composerImpl3);
            final TextStyle textStyle3 = typography.bodyLarge;
            long jM758getColor0d7_KjU = textStyle3.m758getColor0d7_KjU();
            Color.Companion.getClass();
            long j4 = Color.Unspecified;
            boolean zM3447equalsimpl0 = ULong.m3447equalsimpl0(jM758getColor0d7_KjU, j4);
            final TextStyle textStyle4 = typography.bodySmall;
            boolean z4 = (zM3447equalsimpl0 && !ULong.m3447equalsimpl0(textStyle4.m758getColor0d7_KjU(), j4)) || (!ULong.m3447equalsimpl0(textStyle3.m758getColor0d7_KjU(), j4) && ULong.m3447equalsimpl0(textStyle4.m758getColor0d7_KjU(), j4));
            long jM758getColor0d7_KjU2 = textStyle4.m758getColor0d7_KjU();
            if (z4 && jM758getColor0d7_KjU2 == 16) {
                jM758getColor0d7_KjU2 = j3;
            }
            long jM758getColor0d7_KjU3 = textStyle3.m758getColor0d7_KjU();
            long j5 = (z4 && jM758getColor0d7_KjU3 == 16) ? j3 : jM758getColor0d7_KjU3;
            boolean z5 = (function3 == null || !(textFieldLabelPosition instanceof TextFieldLabelPosition.Attached) || ((TextFieldLabelPosition.Attached) textFieldLabelPosition).alwaysMinimize) ? false : true;
            long j6 = jM758getColor0d7_KjU2;
            Transition transitionUpdateTransition = TransitionKt.updateTransition(inputPhase, "TextFieldInputState", composerImpl3, 48, 0);
            final SpringSpec springSpecValue = MotionSchemeKt.value(MotionSchemeKeyTokens.FastSpatial, composerImpl3);
            Function3 function32 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$labelProgress$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj2);
                    composerImpl4.startReplaceGroup(1276209157);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:393)");
                    }
                    FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl4.end(false);
                    return finiteAnimationSpec;
                }
            };
            FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
            TwoWayConverter twoWayConverter = VectorConvertersKt.FloatToVector;
            TransitionState transitionState = transitionUpdateTransition.transitionState;
            InputPhase inputPhase2 = (InputPhase) transitionState.getCurrentState();
            composerImpl3.startReplaceGroup(-2036730335);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:394)");
            }
            int[] iArr = WhenMappings.$EnumSwitchMapping$1;
            int i16 = iArr[inputPhase2.ordinal()];
            float f6 = 0.0f;
            if (i16 == 1) {
                f = 1.0f;
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                composerImpl3.end(false);
                Float fValueOf = Float.valueOf(f);
                InputPhase inputPhase3 = (InputPhase) transitionUpdateTransition.getTargetState();
                composerImpl3.startReplaceGroup(-2036730335);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:394)");
                }
                i5 = iArr[inputPhase3.ordinal()];
                if (i5 == 1) {
                    f2 = 1.0f;
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                    composerImpl3.end(false);
                    final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf, Float.valueOf(f2), (FiniteAnimationSpec) function32.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "LabelProgress", composerImpl3, 196608);
                    MotionSchemeKeyTokens motionSchemeKeyTokens = MotionSchemeKeyTokens.FastEffects;
                    final SpringSpec springSpecValue2 = MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl3);
                    final SpringSpec springSpecValue3 = MotionSchemeKt.value(MotionSchemeKeyTokens.SlowEffects, composerImpl3);
                    boolean z6 = z5;
                    Function3 function33 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$placeholderOpacity$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Transition.Segment segment = (Transition.Segment) obj;
                            ((Number) obj3).intValue();
                            ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj2);
                            composerImpl4.startReplaceGroup(-1154662212);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:407)");
                            }
                            InputPhase inputPhase4 = InputPhase.Focused;
                            InputPhase inputPhase5 = InputPhase.UnfocusedEmpty;
                            FiniteAnimationSpec<Float> finiteAnimationSpec = segment.isTransitioningTo(inputPhase4, inputPhase5) ? springSpecValue2 : (segment.isTransitioningTo(inputPhase5, inputPhase4) || segment.isTransitioningTo(InputPhase.UnfocusedNotEmpty, inputPhase5)) ? springSpecValue3 : springSpecValue2;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl4.end(false);
                            return finiteAnimationSpec;
                        }
                    };
                    InputPhase inputPhase4 = (InputPhase) transitionState.getCurrentState();
                    composerImpl3.startReplaceGroup(1435837472);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:419)");
                    }
                    i6 = iArr[inputPhase4.ordinal()];
                    final boolean z7 = z4;
                    if (i6 == 1) {
                        f3 = 1.0f;
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                        composerImpl3.end(false);
                        Float fValueOf2 = Float.valueOf(f3);
                        InputPhase inputPhase5 = (InputPhase) transitionUpdateTransition.getTargetState();
                        composerImpl3.startReplaceGroup(1435837472);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:419)");
                        }
                        i7 = iArr[inputPhase5.ordinal()];
                        if (i7 == 1) {
                            f4 = 1.0f;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl3.end(false);
                            Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation2 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf2, Float.valueOf(f4), (FiniteAnimationSpec) function33.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "PlaceholderOpacity", composerImpl3, 196608);
                            Function3 function34 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$prefixSuffixOpacity$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    ((Number) obj3).intValue();
                                    ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj2);
                                    composerImpl4.startReplaceGroup(-1868044898);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:429)");
                                    }
                                    FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue2;
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl4.end(false);
                                    return finiteAnimationSpec;
                                }
                            };
                            InputPhase inputPhase6 = (InputPhase) transitionState.getCurrentState();
                            composerImpl3.startReplaceGroup(1128033978);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:431)");
                            }
                            i8 = iArr[inputPhase6.ordinal()];
                            if (i8 == 1) {
                                f5 = 1.0f;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl3.end(false);
                                Float fValueOf3 = Float.valueOf(f5);
                                InputPhase inputPhase7 = (InputPhase) transitionUpdateTransition.getTargetState();
                                composerImpl3.startReplaceGroup(1128033978);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:431)");
                                }
                                i9 = iArr[inputPhase7.ordinal()];
                                if (i9 != 1) {
                                    if (i9 == 2) {
                                        if (!z6) {
                                        }
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl3.end(false);
                                        Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation3 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf3, Float.valueOf(f6), (FiniteAnimationSpec) function34.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "PrefixSuffixOpacity", composerImpl3, 196608);
                                        final SpringSpec springSpecValue4 = MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl3);
                                        Function3 function35 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$labelTextStyleColor$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                ((Number) obj3).intValue();
                                                ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj2);
                                                composerImpl4.startReplaceGroup(1528582156);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:441)");
                                                }
                                                FiniteAnimationSpec<Color> finiteAnimationSpec = springSpecValue4;
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl4.end(false);
                                                return finiteAnimationSpec;
                                            }
                                        };
                                        InputPhase inputPhase8 = (InputPhase) transitionUpdateTransition.getTargetState();
                                        composerImpl3.startReplaceGroup(-107432127);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:444)");
                                        }
                                        long j7 = iArr[inputPhase8.ordinal()] != 1 ? j6 : j5;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl3.end(false);
                                        ColorSpace colorSpaceM461getColorSpaceimpl = Color.m461getColorSpaceimpl(j7);
                                        zChanged = composerImpl3.changed(colorSpaceM461getColorSpaceimpl);
                                        Object objRememberedValue4 = composerImpl3.rememberedValue();
                                        Composer.Companion companion = Composer.Companion;
                                        if (zChanged) {
                                            companion.getClass();
                                            if (objRememberedValue4 == Composer.Companion.Empty) {
                                                objRememberedValue4 = (TwoWayConverter) ColorVectorConverterKt.ColorToVector.mo781invoke(colorSpaceM461getColorSpaceimpl);
                                                composerImpl3.updateRememberedValue(objRememberedValue4);
                                            }
                                            TwoWayConverter twoWayConverter2 = (TwoWayConverter) objRememberedValue4;
                                            InputPhase inputPhase9 = (InputPhase) transitionState.getCurrentState();
                                            composerImpl3.startReplaceGroup(-107432127);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:444)");
                                            }
                                            long j8 = iArr[inputPhase9.ordinal()] == 1 ? j6 : j5;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl3.end(false);
                                            Color colorM456boximpl = Color.m456boximpl(j8);
                                            InputPhase inputPhase10 = (InputPhase) transitionUpdateTransition.getTargetState();
                                            composerImpl3.startReplaceGroup(-107432127);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:444)");
                                            }
                                            if (iArr[inputPhase10.ordinal()] == 1) {
                                                j5 = j6;
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl3.end(false);
                                            final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation4 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, colorM456boximpl, Color.m456boximpl(j5), (FiniteAnimationSpec) function35.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter2, "LabelTextStyleColor", composerImpl3, 196608);
                                            Function3 function36 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$labelContentColor$1
                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(3);
                                                }

                                                @Override // kotlin.jvm.functions.Function3
                                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                    ((Number) obj3).intValue();
                                                    ComposerImpl composerImpl4 = (ComposerImpl) ((Composer) obj2);
                                                    composerImpl4.startReplaceGroup(-543659263);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:453)");
                                                    }
                                                    FiniteAnimationSpec<Color> finiteAnimationSpec = springSpecValue4;
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                    composerImpl4.end(false);
                                                    return finiteAnimationSpec;
                                                }
                                            };
                                            composerImpl3.startReplaceGroup(1023351670);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:455)");
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl3.end(false);
                                            ColorSpace colorSpaceM461getColorSpaceimpl2 = Color.m461getColorSpaceimpl(j3);
                                            boolean zChanged2 = composerImpl3.changed(colorSpaceM461getColorSpaceimpl2);
                                            Object objRememberedValue5 = composerImpl3.rememberedValue();
                                            if (zChanged2) {
                                                str = "androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:455)";
                                            } else {
                                                companion.getClass();
                                                str = "androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:455)";
                                                if (objRememberedValue5 == Composer.Companion.Empty) {
                                                }
                                                TwoWayConverter twoWayConverter3 = (TwoWayConverter) objRememberedValue5;
                                                composerImpl3.startReplaceGroup(1023351670);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart(str);
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl3.end(false);
                                                Color colorM456boximpl2 = Color.m456boximpl(j3);
                                                composerImpl3.startReplaceGroup(1023351670);
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventStart(str);
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                                composerImpl3.end(false);
                                                final Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation5 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, colorM456boximpl2, Color.m456boximpl(j3), (FiniteAnimationSpec) function36.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter3, "LabelContentColor", composerImpl3, 196608);
                                                objRememberedValue = composerImpl3.rememberedValue();
                                                companion.getClass();
                                                composer$Companion$Empty$1 = Composer.Companion.Empty;
                                                if (objRememberedValue == composer$Companion$Empty$1) {
                                                    objRememberedValue = new TextFieldLabelScope(transitionAnimationStateCreateTransitionAnimation) { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$labelScope$1$1
                                                    };
                                                    composerImpl3.updateRememberedValue(objRememberedValue);
                                                }
                                                final TextFieldImplKt$CommonDecorationBox$3$labelScope$1$1 textFieldImplKt$CommonDecorationBox$3$labelScope$1$1 = (TextFieldImplKt$CommonDecorationBox$3$labelScope$1$1) objRememberedValue;
                                                ComposableLambdaImpl composableLambdaImplRememberComposableLambda2 = null;
                                                if (function3 != null) {
                                                    composerImpl3.startReplaceGroup(-571905479);
                                                    composerImpl3.end(false);
                                                    composerImpl = composerImpl3;
                                                    composer$Companion$Empty$12 = composer$Companion$Empty$1;
                                                    transitionAnimationState3 = transitionAnimationStateCreateTransitionAnimation;
                                                    i10 = i14;
                                                    transitionAnimationState = transitionAnimationStateCreateTransitionAnimation3;
                                                    composableLambdaImpl = null;
                                                    textStyle = textStyle3;
                                                    textStyle2 = textStyle4;
                                                    transitionAnimationState2 = transitionAnimationStateCreateTransitionAnimation2;
                                                } else {
                                                    composerImpl3.startReplaceGroup(-571905478);
                                                    composer$Companion$Empty$12 = composer$Companion$Empty$1;
                                                    i10 = i14;
                                                    transitionAnimationState = transitionAnimationStateCreateTransitionAnimation3;
                                                    composerImpl = composerImpl3;
                                                    transitionAnimationState2 = transitionAnimationStateCreateTransitionAnimation2;
                                                    Function2 function29 = new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedLabel$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            float f7;
                                                            PlatformSpanStyle platformSpanStyle;
                                                            PlatformParagraphStyle platformParagraphStyle;
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous>.<anonymous> (TextFieldImpl.kt:139)");
                                                                    }
                                                                    TextStyle textStyle5 = textStyle3;
                                                                    TextStyle textStyle6 = textStyle4;
                                                                    float fFloatValue = ((Number) transitionAnimationStateCreateTransitionAnimation.getValue()).floatValue();
                                                                    SpanStyle spanStyle = textStyle5.spanStyle;
                                                                    SpanStyle spanStyle2 = textStyle6.spanStyle;
                                                                    TextForegroundStyle textForegroundStyle = SpanStyleKt.DefaultColorForegroundStyle;
                                                                    TextForegroundStyle textForegroundStyleLerp = TextDrawStyleKt.lerp(spanStyle.textForegroundStyle, spanStyle2.textForegroundStyle, fFloatValue);
                                                                    FontFamily fontFamily = (FontFamily) SpanStyleKt.lerpDiscrete(fFloatValue, spanStyle.fontFamily, spanStyle2.fontFamily);
                                                                    long jM744lerpTextUnitInheritableC3pnCVY = SpanStyleKt.m744lerpTextUnitInheritableC3pnCVY(spanStyle.fontSize, spanStyle2.fontSize, fFloatValue);
                                                                    FontWeight fontWeight = spanStyle.fontWeight;
                                                                    if (fontWeight == null) {
                                                                        FontWeight.Companion.getClass();
                                                                        fontWeight = FontWeight.Normal;
                                                                    }
                                                                    FontWeight fontWeight2 = spanStyle2.fontWeight;
                                                                    if (fontWeight2 == null) {
                                                                        FontWeight.Companion.getClass();
                                                                        fontWeight2 = FontWeight.Normal;
                                                                    }
                                                                    FontWeight fontWeight3 = new FontWeight(RangesKt___RangesKt.coerceIn(MathHelpersKt.lerp(fFloatValue, fontWeight.weight, fontWeight2.weight), 1, 1000));
                                                                    FontStyle fontStyle = (FontStyle) SpanStyleKt.lerpDiscrete(fFloatValue, spanStyle.fontStyle, spanStyle2.fontStyle);
                                                                    FontSynthesis fontSynthesis = (FontSynthesis) SpanStyleKt.lerpDiscrete(fFloatValue, spanStyle.fontSynthesis, spanStyle2.fontSynthesis);
                                                                    String str2 = (String) SpanStyleKt.lerpDiscrete(fFloatValue, spanStyle.fontFeatureSettings, spanStyle2.fontFeatureSettings);
                                                                    long jM744lerpTextUnitInheritableC3pnCVY2 = SpanStyleKt.m744lerpTextUnitInheritableC3pnCVY(spanStyle.letterSpacing, spanStyle2.letterSpacing, fFloatValue);
                                                                    float f8 = 0.0f;
                                                                    BaselineShift baselineShift = spanStyle.baselineShift;
                                                                    if (baselineShift != null) {
                                                                        f7 = baselineShift.multiplier;
                                                                    } else {
                                                                        BaselineShift.Companion companion2 = BaselineShift.Companion;
                                                                        f7 = 0.0f;
                                                                    }
                                                                    BaselineShift baselineShift2 = spanStyle2.baselineShift;
                                                                    if (baselineShift2 != null) {
                                                                        f8 = baselineShift2.multiplier;
                                                                    } else {
                                                                        BaselineShift.Companion companion3 = BaselineShift.Companion;
                                                                    }
                                                                    float fLerp = MathHelpersKt.lerp(f7, f8, fFloatValue);
                                                                    BaselineShift.Companion companion4 = BaselineShift.Companion;
                                                                    TextGeometricTransform textGeometricTransform = spanStyle.textGeometricTransform;
                                                                    if (textGeometricTransform == null) {
                                                                        TextGeometricTransform.Companion.getClass();
                                                                        textGeometricTransform = TextGeometricTransform.None;
                                                                    }
                                                                    TextGeometricTransform textGeometricTransform2 = spanStyle2.textGeometricTransform;
                                                                    if (textGeometricTransform2 == null) {
                                                                        TextGeometricTransform.Companion.getClass();
                                                                        textGeometricTransform2 = TextGeometricTransform.None;
                                                                    }
                                                                    TextGeometricTransform textGeometricTransform3 = new TextGeometricTransform(MathHelpersKt.lerp(textGeometricTransform.scaleX, textGeometricTransform2.scaleX, fFloatValue), MathHelpersKt.lerp(textGeometricTransform.skewX, textGeometricTransform2.skewX, fFloatValue));
                                                                    LocaleList localeList = (LocaleList) SpanStyleKt.lerpDiscrete(fFloatValue, spanStyle.localeList, spanStyle2.localeList);
                                                                    long jM467lerpjxsXWHM = ColorKt.m467lerpjxsXWHM(spanStyle.background, spanStyle2.background, fFloatValue);
                                                                    TextDecoration textDecoration = (TextDecoration) SpanStyleKt.lerpDiscrete(fFloatValue, spanStyle.textDecoration, spanStyle2.textDecoration);
                                                                    Shadow shadow = spanStyle.shadow;
                                                                    if (shadow == null) {
                                                                        shadow = new Shadow(0L, 0L, 0.0f, 7, null);
                                                                    }
                                                                    Shadow shadow2 = spanStyle2.shadow;
                                                                    if (shadow2 == null) {
                                                                        shadow2 = new Shadow(0L, 0L, 0.0f, 7, null);
                                                                    }
                                                                    Shadow shadow3 = new Shadow(ColorKt.m467lerpjxsXWHM(shadow.color, shadow2.color, fFloatValue), OffsetKt.m406lerpWko1d7g(shadow.offset, shadow2.offset, fFloatValue), MathHelpersKt.lerp(shadow.blurRadius, shadow2.blurRadius, fFloatValue), null);
                                                                    PlatformSpanStyle platformSpanStyle2 = spanStyle.platformStyle;
                                                                    PlatformSpanStyle platformSpanStyle3 = spanStyle2.platformStyle;
                                                                    if (platformSpanStyle2 == null && platformSpanStyle3 == null) {
                                                                        platformSpanStyle = null;
                                                                    } else {
                                                                        if (platformSpanStyle2 == null) {
                                                                            PlatformSpanStyle.Companion.getClass();
                                                                            platformSpanStyle2 = PlatformSpanStyle.Default;
                                                                        }
                                                                        if (platformSpanStyle3 == null) {
                                                                            PlatformSpanStyle.Companion.getClass();
                                                                        }
                                                                        platformSpanStyle = platformSpanStyle2;
                                                                    }
                                                                    SpanStyle spanStyle3 = new SpanStyle(textForegroundStyleLerp, jM744lerpTextUnitInheritableC3pnCVY, fontWeight3, fontStyle, fontSynthesis, fontFamily, str2, jM744lerpTextUnitInheritableC3pnCVY2, BaselineShift.m793boximpl(fLerp), textGeometricTransform3, localeList, jM467lerpjxsXWHM, textDecoration, shadow3, platformSpanStyle, (DrawStyle) SpanStyleKt.lerpDiscrete(fFloatValue, spanStyle.drawStyle, spanStyle2.drawStyle), (DefaultConstructorMarker) null);
                                                                    int i17 = ParagraphStyleKt.$r8$clinit;
                                                                    ParagraphStyle paragraphStyle = textStyle5.paragraphStyle;
                                                                    TextAlign textAlignM807boximpl = TextAlign.m807boximpl(paragraphStyle.textAlign);
                                                                    ParagraphStyle paragraphStyle2 = textStyle6.paragraphStyle;
                                                                    int i18 = ((TextAlign) SpanStyleKt.lerpDiscrete(fFloatValue, textAlignM807boximpl, TextAlign.m807boximpl(paragraphStyle2.textAlign))).value;
                                                                    int i19 = ((TextDirection) SpanStyleKt.lerpDiscrete(fFloatValue, TextDirection.m809boximpl(paragraphStyle.textDirection), TextDirection.m809boximpl(paragraphStyle2.textDirection))).value;
                                                                    long jM744lerpTextUnitInheritableC3pnCVY3 = SpanStyleKt.m744lerpTextUnitInheritableC3pnCVY(paragraphStyle.lineHeight, paragraphStyle2.lineHeight, fFloatValue);
                                                                    TextIndent textIndent = paragraphStyle.textIndent;
                                                                    if (textIndent == null) {
                                                                        TextIndent.Companion.getClass();
                                                                        textIndent = TextIndent.None;
                                                                    }
                                                                    TextIndent textIndent2 = paragraphStyle2.textIndent;
                                                                    if (textIndent2 == null) {
                                                                        TextIndent.Companion.getClass();
                                                                        textIndent2 = TextIndent.None;
                                                                    }
                                                                    TextIndent textIndent3 = new TextIndent(SpanStyleKt.m744lerpTextUnitInheritableC3pnCVY(textIndent.firstLine, textIndent2.firstLine, fFloatValue), SpanStyleKt.m744lerpTextUnitInheritableC3pnCVY(textIndent.restLine, textIndent2.restLine, fFloatValue), null);
                                                                    PlatformParagraphStyle platformParagraphStyle2 = paragraphStyle.platformStyle;
                                                                    PlatformParagraphStyle platformParagraphStyle3 = paragraphStyle2.platformStyle;
                                                                    if (platformParagraphStyle2 == null && platformParagraphStyle3 == null) {
                                                                        platformParagraphStyle = null;
                                                                    } else {
                                                                        if (platformParagraphStyle2 == null) {
                                                                            PlatformParagraphStyle.Companion.getClass();
                                                                            platformParagraphStyle2 = PlatformParagraphStyle.Default;
                                                                        }
                                                                        if (platformParagraphStyle3 == null) {
                                                                            PlatformParagraphStyle.Companion.getClass();
                                                                            platformParagraphStyle3 = PlatformParagraphStyle.Default;
                                                                        }
                                                                        platformParagraphStyle = platformParagraphStyle2.includeFontPadding == platformParagraphStyle3.includeFontPadding ? platformParagraphStyle2 : new PlatformParagraphStyle(((EmojiSupportMatch) SpanStyleKt.lerpDiscrete(fFloatValue, EmojiSupportMatch.m731boximpl(platformParagraphStyle2.emojiSupportMatch), EmojiSupportMatch.m731boximpl(platformParagraphStyle3.emojiSupportMatch))).value, ((Boolean) SpanStyleKt.lerpDiscrete(fFloatValue, Boolean.valueOf(platformParagraphStyle2.includeFontPadding), Boolean.valueOf(platformParagraphStyle3.includeFontPadding))).booleanValue(), (DefaultConstructorMarker) null);
                                                                    }
                                                                    TextStyle textStyle7 = new TextStyle(spanStyle3, new ParagraphStyle(i18, i19, jM744lerpTextUnitInheritableC3pnCVY3, textIndent3, platformParagraphStyle, (LineHeightStyle) SpanStyleKt.lerpDiscrete(fFloatValue, paragraphStyle.lineHeightStyle, paragraphStyle2.lineHeightStyle), ((LineBreak) SpanStyleKt.lerpDiscrete(fFloatValue, LineBreak.m797boximpl(paragraphStyle.lineBreak), LineBreak.m797boximpl(paragraphStyle2.lineBreak))).mask, ((Hyphens) SpanStyleKt.lerpDiscrete(fFloatValue, Hyphens.m795boximpl(paragraphStyle.hyphens), Hyphens.m795boximpl(paragraphStyle2.hyphens))).value, (TextMotion) SpanStyleKt.lerpDiscrete(fFloatValue, paragraphStyle.textMotion, paragraphStyle2.textMotion), (DefaultConstructorMarker) null));
                                                                    boolean z8 = z7;
                                                                    State<Color> state = transitionAnimationStateCreateTransitionAnimation4;
                                                                    if (z8) {
                                                                        textStyle7 = TextStyle.m756copyp1EtxEg$default(textStyle7, ((Color) state.getValue()).value, 0L, null, null, 0L, 0, 0L, null, null, 0, 16777214);
                                                                    }
                                                                    TextStyle textStyle8 = textStyle7;
                                                                    long j9 = ((Color) transitionAnimationStateCreateTransitionAnimation5.getValue()).value;
                                                                    final Function3 function37 = function3;
                                                                    final TextFieldImplKt$CommonDecorationBox$3$labelScope$1$1 textFieldImplKt$CommonDecorationBox$3$labelScope$1$12 = textFieldImplKt$CommonDecorationBox$3$labelScope$1$1;
                                                                    TextFieldImplKt.m323access$Decoration3JVO9M(j9, textStyle8, ComposableLambdaKt.rememberComposableLambda(-1245867650, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedLabel$1$1.1
                                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                        {
                                                                            super(2);
                                                                        }

                                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                                        @Override // kotlin.jvm.functions.Function2
                                                                        /*
                                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                                        */
                                                                        public final Object invoke(Object obj3, Object obj4) {
                                                                            Composer composer3 = (Composer) obj3;
                                                                            if ((((Number) obj4).intValue() & 3) == 2) {
                                                                                ComposerImpl composerImpl5 = (ComposerImpl) composer3;
                                                                                if (composerImpl5.getSkipping()) {
                                                                                    composerImpl5.skipToGroupEnd();
                                                                                } else {
                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous>.<anonymous>.<anonymous> (TextFieldImpl.kt:147)");
                                                                                    }
                                                                                    function37.invoke(textFieldImplKt$CommonDecorationBox$3$labelScope$1$12, composer3, 6);
                                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                                        ComposerKt.traceEventEnd();
                                                                                    }
                                                                                }
                                                                            }
                                                                            return Unit.INSTANCE;
                                                                        }
                                                                    }, composer2), composer2, 384);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                                    textStyle = textStyle3;
                                                    textStyle2 = textStyle4;
                                                    transitionAnimationState3 = transitionAnimationStateCreateTransitionAnimation;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda3 = ComposableLambdaKt.rememberComposableLambda(699073215, function29, composerImpl);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl = composableLambdaImplRememberComposableLambda3;
                                                }
                                                if (z2) {
                                                    textFieldColors3 = textFieldColors;
                                                    j = textFieldColors3.disabledPlaceholderColor;
                                                } else {
                                                    textFieldColors3 = textFieldColors;
                                                    j = z3 ? textFieldColors3.errorPlaceholderColor : zBooleanValue ? textFieldColors3.focusedPlaceholderColor : textFieldColors3.unfocusedPlaceholderColor;
                                                }
                                                final long j9 = j;
                                                objRememberedValue2 = composerImpl.rememberedValue();
                                                if (objRememberedValue2 == composer$Companion$Empty$12) {
                                                    objRememberedValue2 = SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$showPlaceholder$2$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            return Boolean.valueOf(((Number) transitionAnimationState2.getValue()).floatValue() > 0.0f);
                                                        }
                                                    });
                                                    composerImpl.updateRememberedValue(objRememberedValue2);
                                                }
                                                State state = (State) objRememberedValue2;
                                                if (function22 == null && charSequence.length() == 0 && ((Boolean) state.getValue()).booleanValue()) {
                                                    composerImpl.startReplaceGroup(-570794965);
                                                    final Transition.TransitionAnimationState transitionAnimationState5 = transitionAnimationState2;
                                                    textFieldColors4 = textFieldColors;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda4 = ComposableLambdaKt.rememberComposableLambda(413771370, new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedPlaceholder$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(3);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                                                        /* JADX WARN: Removed duplicated region for block: B:22:0x0055  */
                                                        @Override // kotlin.jvm.functions.Function3
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                                                            Modifier modifier = (Modifier) obj;
                                                            Composer composer2 = (Composer) obj2;
                                                            int iIntValue = ((Number) obj3).intValue();
                                                            if ((iIntValue & 6) == 0) {
                                                                iIntValue |= ((ComposerImpl) composer2).changed(modifier) ? 4 : 2;
                                                            }
                                                            if ((iIntValue & 19) == 18) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous> (TextFieldImpl.kt:161)");
                                                                    }
                                                                    ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                                    boolean zChanged3 = composerImpl5.changed(transitionAnimationState5);
                                                                    final State<Float> state2 = transitionAnimationState5;
                                                                    Object objRememberedValue6 = composerImpl5.rememberedValue();
                                                                    if (!zChanged3) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                            objRememberedValue6 = new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedPlaceholder$1$1$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj4) {
                                                                                    ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj4)).setAlpha(((Number) state2.getValue()).floatValue());
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            };
                                                                            composerImpl5.updateRememberedValue(objRememberedValue6);
                                                                        }
                                                                        Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(modifier, (Function1) objRememberedValue6);
                                                                        long j10 = j9;
                                                                        TextStyle textStyle5 = textStyle;
                                                                        Function2 function210 = function22;
                                                                        Alignment.Companion.getClass();
                                                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl5);
                                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl5, modifierGraphicsLayer);
                                                                        ComposeUiNode.Companion.getClass();
                                                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                                        if (composerImpl5.applier == null) {
                                                                            ComposablesKt.invalidApplier();
                                                                            throw null;
                                                                        }
                                                                        composerImpl5.startReusableNode();
                                                                        if (composerImpl5.inserting) {
                                                                            composerImpl5.createNode(function0);
                                                                        } else {
                                                                            composerImpl5.useNode();
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                        Updater.m337setimpl(composerImpl5, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                        Function2 function211 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                        if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function211);
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                        TextFieldImplKt.m323access$Decoration3JVO9M(j10, textStyle5, function210, composerImpl5, 0);
                                                                        composerImpl5.end(true);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl2 = composableLambdaImplRememberComposableLambda4;
                                                } else {
                                                    textFieldColors4 = textFieldColors3;
                                                    composerImpl.startReplaceGroup(-570398724);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl2 = null;
                                                }
                                                final long j10 = z2 ? textFieldColors4.disabledPrefixColor : z3 ? textFieldColors4.errorPrefixColor : zBooleanValue ? textFieldColors4.focusedPrefixColor : textFieldColors4.unfocusedPrefixColor;
                                                objRememberedValue3 = composerImpl.rememberedValue();
                                                if (objRememberedValue3 == composer$Companion$Empty$12) {
                                                    objRememberedValue3 = SnapshotStateKt.derivedStateOf(SnapshotStateKt.structuralEqualityPolicy(), new Function0() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$showPrefixSuffix$2$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        public final Object invoke() {
                                                            return Boolean.valueOf(((Number) transitionAnimationState.getValue()).floatValue() > 0.0f);
                                                        }
                                                    });
                                                    composerImpl.updateRememberedValue(objRememberedValue3);
                                                }
                                                State state2 = (State) objRememberedValue3;
                                                if (function25 == null && ((Boolean) state2.getValue()).booleanValue()) {
                                                    composerImpl.startReplaceGroup(-570059552);
                                                    transitionAnimationState4 = transitionAnimationState;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda5 = ComposableLambdaKt.rememberComposableLambda(-1779648892, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedPrefix$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous> (TextFieldImpl.kt:178)");
                                                                    }
                                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                                    ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                                    boolean zChanged3 = composerImpl5.changed(transitionAnimationState4);
                                                                    final State<Float> state3 = transitionAnimationState4;
                                                                    Object objRememberedValue6 = composerImpl5.rememberedValue();
                                                                    if (!zChanged3) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                            objRememberedValue6 = new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedPrefix$1$1$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj3) {
                                                                                    ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj3)).setAlpha(((Number) state3.getValue()).floatValue());
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            };
                                                                            composerImpl5.updateRememberedValue(objRememberedValue6);
                                                                        }
                                                                        Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(companion2, (Function1) objRememberedValue6);
                                                                        long j11 = j10;
                                                                        TextStyle textStyle5 = textStyle;
                                                                        Function2 function210 = function25;
                                                                        Alignment.Companion.getClass();
                                                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl5);
                                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl5, modifierGraphicsLayer);
                                                                        ComposeUiNode.Companion.getClass();
                                                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                                        if (composerImpl5.applier == null) {
                                                                            ComposablesKt.invalidApplier();
                                                                            throw null;
                                                                        }
                                                                        composerImpl5.startReusableNode();
                                                                        if (composerImpl5.inserting) {
                                                                            composerImpl5.createNode(function0);
                                                                        } else {
                                                                            composerImpl5.useNode();
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                        Updater.m337setimpl(composerImpl5, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                        Function2 function211 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                        if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function211);
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                        TextFieldImplKt.m323access$Decoration3JVO9M(j11, textStyle5, function210, composerImpl5, 0);
                                                                        composerImpl5.end(true);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl3 = composableLambdaImplRememberComposableLambda5;
                                                } else {
                                                    transitionAnimationState4 = transitionAnimationState;
                                                    composerImpl.startReplaceGroup(-569683492);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl3 = null;
                                                }
                                                final long j11 = z2 ? textFieldColors4.disabledSuffixColor : z3 ? textFieldColors4.errorSuffixColor : zBooleanValue ? textFieldColors4.focusedSuffixColor : textFieldColors4.unfocusedSuffixColor;
                                                if (function26 == null && ((Boolean) state2.getValue()).booleanValue()) {
                                                    composerImpl.startReplaceGroup(-569484192);
                                                    composableLambdaImplRememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-294363133, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedSuffix$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:15:0x0043  */
                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous> (TextFieldImpl.kt:192)");
                                                                    }
                                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                                    ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                                    boolean zChanged3 = composerImpl5.changed(transitionAnimationState4);
                                                                    final State<Float> state3 = transitionAnimationState4;
                                                                    Object objRememberedValue6 = composerImpl5.rememberedValue();
                                                                    if (!zChanged3) {
                                                                        Composer.Companion.getClass();
                                                                        if (objRememberedValue6 == Composer.Companion.Empty) {
                                                                            objRememberedValue6 = new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedSuffix$1$1$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                /* renamed from: invoke */
                                                                                public final Object mo781invoke(Object obj3) {
                                                                                    ((ReusableGraphicsLayerScope) ((GraphicsLayerScope) obj3)).setAlpha(((Number) state3.getValue()).floatValue());
                                                                                    return Unit.INSTANCE;
                                                                                }
                                                                            };
                                                                            composerImpl5.updateRememberedValue(objRememberedValue6);
                                                                        }
                                                                        Modifier modifierGraphicsLayer = GraphicsLayerModifierKt.graphicsLayer(companion2, (Function1) objRememberedValue6);
                                                                        long j12 = j11;
                                                                        TextStyle textStyle5 = textStyle;
                                                                        Function2 function210 = function26;
                                                                        Alignment.Companion.getClass();
                                                                        MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
                                                                        int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerImpl5);
                                                                        PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl5.currentCompositionLocalScope();
                                                                        Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerImpl5, modifierGraphicsLayer);
                                                                        ComposeUiNode.Companion.getClass();
                                                                        Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                                        if (composerImpl5.applier == null) {
                                                                            ComposablesKt.invalidApplier();
                                                                            throw null;
                                                                        }
                                                                        composerImpl5.startReusableNode();
                                                                        if (composerImpl5.inserting) {
                                                                            composerImpl5.createNode(function0);
                                                                        } else {
                                                                            composerImpl5.useNode();
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                        Updater.m337setimpl(composerImpl5, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                        Function2 function211 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                        if (composerImpl5.inserting || !Intrinsics.areEqual(composerImpl5.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                            AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl5, currentCompositeKeyHash, function211);
                                                                        }
                                                                        Updater.m337setimpl(composerImpl5, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                        BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                        TextFieldImplKt.m323access$Decoration3JVO9M(j12, textStyle5, function210, composerImpl5, 0);
                                                                        composerImpl5.end(true);
                                                                        if (ComposerKt.isTraceInProgress()) {
                                                                            ComposerKt.traceEventEnd();
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl);
                                                    composerImpl.end(false);
                                                } else {
                                                    composerImpl.startReplaceGroup(-569108132);
                                                    composerImpl.end(false);
                                                    composableLambdaImplRememberComposableLambda = null;
                                                }
                                                final long j12 = z2 ? textFieldColors4.disabledLeadingIconColor : z3 ? textFieldColors4.errorLeadingIconColor : zBooleanValue ? textFieldColors4.focusedLeadingIconColor : textFieldColors4.unfocusedLeadingIconColor;
                                                if (function23 != null) {
                                                    composerImpl.startReplaceGroup(-568933261);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl4 = null;
                                                } else {
                                                    composerImpl.startReplaceGroup(-568933260);
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda6 = ComposableLambdaKt.rememberComposableLambda(-1767136112, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedLeading$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous>.<anonymous> (TextFieldImpl.kt:205)");
                                                                    }
                                                                    TextFieldImplKt.m324access$DecorationIv8Zu3U(j12, function23, composer2, 0);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl4 = composableLambdaImplRememberComposableLambda6;
                                                }
                                                final long j13 = z2 ? textFieldColors4.disabledTrailingIconColor : z3 ? textFieldColors4.errorTrailingIconColor : zBooleanValue ? textFieldColors4.focusedTrailingIconColor : textFieldColors4.unfocusedTrailingIconColor;
                                                if (function24 != null) {
                                                    composerImpl.startReplaceGroup(-568653486);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl5 = null;
                                                } else {
                                                    composerImpl.startReplaceGroup(-568653485);
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda7 = ComposableLambdaKt.rememberComposableLambda(1412117636, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedTrailing$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001b  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous>.<anonymous> (TextFieldImpl.kt:211)");
                                                                    }
                                                                    TextFieldImplKt.m324access$DecorationIv8Zu3U(j13, function24, composer2, 0);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl5 = composableLambdaImplRememberComposableLambda7;
                                                }
                                                final long j14 = z2 ? textFieldColors4.disabledSupportingTextColor : z3 ? textFieldColors4.errorSupportingTextColor : zBooleanValue ? textFieldColors4.focusedSupportingTextColor : textFieldColors4.unfocusedSupportingTextColor;
                                                if (function27 != null) {
                                                    composerImpl.startReplaceGroup(-568360009);
                                                    composerImpl.end(false);
                                                } else {
                                                    composerImpl.startReplaceGroup(-568360008);
                                                    composableLambdaImplRememberComposableLambda2 = ComposableLambdaKt.rememberComposableLambda(2043467035, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$decoratedSupporting$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                                                if (composerImpl4.getSkipping()) {
                                                                    composerImpl4.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous>.<anonymous> (TextFieldImpl.kt:218)");
                                                                    }
                                                                    TextFieldImplKt.m323access$Decoration3JVO9M(j14, textStyle2, function27, composer2, 0);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl);
                                                    composerImpl.end(false);
                                                }
                                                i11 = WhenMappings.$EnumSwitchMapping$0[textFieldType.ordinal()];
                                                if (i11 != 1) {
                                                    int i17 = i10;
                                                    ComposableLambdaImpl composableLambdaImpl6 = composableLambdaImplRememberComposableLambda;
                                                    ComposerImpl composerImpl4 = composerImpl;
                                                    composerImpl4.startReplaceGroup(-568015319);
                                                    TextFieldKt.TextFieldLayout(Modifier.Companion, function2, composableLambdaImpl, composableLambdaImpl2, composableLambdaImpl4, composableLambdaImpl5, composableLambdaImpl3, composableLambdaImpl6, z, textFieldLabelPosition, new TextFieldImplKt$sam$androidx_compose_material3_internal_FloatProducer$0(new PropertyReference0Impl(transitionAnimationState3) { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$1
                                                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                                        public final Object get() {
                                                            return ((State) this.receiver).getValue();
                                                        }
                                                    }), ComposableLambdaKt.rememberComposableLambda(1222288666, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$containerWithId$1
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                                if (composerImpl5.getSkipping()) {
                                                                    composerImpl5.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous> (TextFieldImpl.kt:229)");
                                                                    }
                                                                    Modifier modifierLayoutId = LayoutIdKt.layoutId(Modifier.Companion, "Container");
                                                                    Function2 function210 = function28;
                                                                    Alignment.Companion.getClass();
                                                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                                                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                                                    ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl6.currentCompositionLocalScope();
                                                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierLayoutId);
                                                                    ComposeUiNode.Companion.getClass();
                                                                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                                    if (composerImpl6.applier == null) {
                                                                        ComposablesKt.invalidApplier();
                                                                        throw null;
                                                                    }
                                                                    composerImpl6.startReusableNode();
                                                                    if (composerImpl6.inserting) {
                                                                        composerImpl6.createNode(function0);
                                                                    } else {
                                                                        composerImpl6.useNode();
                                                                    }
                                                                    Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                    Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                    Function2 function211 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                    if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl6, currentCompositeKeyHash, function211);
                                                                    }
                                                                    Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                    function210.invoke(composer2, 0);
                                                                    composerImpl6.end(true);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl4), composableLambdaImplRememberComposableLambda2, paddingValues, composerImpl4, ((i17 >> 3) & 112) | 6 | ((i15 << 21) & 234881024) | ((i17 << 18) & 1879048192), ((i15 >> 6) & 7168) | 48);
                                                    composerImpl2 = composerImpl4;
                                                    composerImpl2.end(false);
                                                    Unit unit = Unit.INSTANCE;
                                                } else if (i11 != 2) {
                                                    composerImpl.startReplaceGroup(-564872415);
                                                    composerImpl.end(false);
                                                    Unit unit2 = Unit.INSTANCE;
                                                    composerImpl2 = composerImpl;
                                                } else {
                                                    composerImpl.startReplaceGroup(-566963861);
                                                    Object objRememberedValue6 = composerImpl.rememberedValue();
                                                    if (objRememberedValue6 == composer$Companion$Empty$12) {
                                                        Size.Companion.getClass();
                                                        objRememberedValue6 = SnapshotStateKt.mutableStateOf$default(Size.m415boximpl(0L));
                                                        composerImpl.updateRememberedValue(objRememberedValue6);
                                                    }
                                                    final MutableState mutableState = (MutableState) objRememberedValue6;
                                                    ComposableLambdaImpl composableLambdaImpl7 = composableLambdaImplRememberComposableLambda;
                                                    ComposableLambdaImpl composableLambdaImplRememberComposableLambda8 = ComposableLambdaKt.rememberComposableLambda(1483811447, new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$borderContainerWithId$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(2);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
                                                        @Override // kotlin.jvm.functions.Function2
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke(Object obj, Object obj2) {
                                                            Composer composer2 = (Composer) obj;
                                                            if ((((Number) obj2).intValue() & 3) == 2) {
                                                                ComposerImpl composerImpl5 = (ComposerImpl) composer2;
                                                                if (composerImpl5.getSkipping()) {
                                                                    composerImpl5.skipToGroupEnd();
                                                                } else {
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.CommonDecorationBox.<anonymous>.<anonymous> (TextFieldImpl.kt:255)");
                                                                    }
                                                                    Modifier modifierOutlineCutout = OutlinedTextFieldKt.outlineCutout(LayoutIdKt.layoutId(Modifier.Companion, "Container"), new MutablePropertyReference0Impl(mutableState) { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$borderContainerWithId$1.1
                                                                        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KProperty0
                                                                        public final Object get() {
                                                                            return ((MutableState) this.receiver).getValue();
                                                                        }

                                                                        @Override // kotlin.jvm.internal.MutablePropertyReference0Impl, kotlin.reflect.KMutableProperty0
                                                                        public final void set(Object obj3) {
                                                                            ((MutableState) this.receiver).setValue(obj3);
                                                                        }
                                                                    }, TextFieldImplKt.getMinimizedAlignment(textFieldLabelPosition), paddingValues);
                                                                    Function2 function210 = function28;
                                                                    Alignment.Companion.getClass();
                                                                    MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, true);
                                                                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                                                                    ComposerImpl composerImpl6 = (ComposerImpl) composer2;
                                                                    PersistentCompositionLocalMap persistentCompositionLocalMapCurrentCompositionLocalScope = composerImpl6.currentCompositionLocalScope();
                                                                    Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierOutlineCutout);
                                                                    ComposeUiNode.Companion.getClass();
                                                                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                                                                    if (composerImpl6.applier == null) {
                                                                        ComposablesKt.invalidApplier();
                                                                        throw null;
                                                                    }
                                                                    composerImpl6.startReusableNode();
                                                                    if (composerImpl6.inserting) {
                                                                        composerImpl6.createNode(function0);
                                                                    } else {
                                                                        composerImpl6.useNode();
                                                                    }
                                                                    Updater.m337setimpl(composer2, measurePolicyMaybeCachedBoxMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                                                                    Updater.m337setimpl(composer2, persistentCompositionLocalMapCurrentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                                                                    Function2 function211 = ComposeUiNode.Companion.SetCompositeKeyHash;
                                                                    if (composerImpl6.inserting || !Intrinsics.areEqual(composerImpl6.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                                                                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl6, currentCompositeKeyHash, function211);
                                                                    }
                                                                    Updater.m337setimpl(composer2, modifierMaterializeModifier, ComposeUiNode.Companion.SetModifier);
                                                                    BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                                                                    function210.invoke(composer2, 0);
                                                                    composerImpl6.end(true);
                                                                    if (ComposerKt.isTraceInProgress()) {
                                                                        ComposerKt.traceEventEnd();
                                                                    }
                                                                }
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }, composerImpl);
                                                    Modifier.Companion companion2 = Modifier.Companion;
                                                    TextFieldImplKt$sam$androidx_compose_material3_internal_FloatProducer$0 textFieldImplKt$sam$androidx_compose_material3_internal_FloatProducer$0 = new TextFieldImplKt$sam$androidx_compose_material3_internal_FloatProducer$0(new PropertyReference0Impl(transitionAnimationState3) { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$2
                                                        @Override // kotlin.jvm.internal.PropertyReference0Impl, kotlin.reflect.KProperty0
                                                        public final Object get() {
                                                            return ((State) this.receiver).getValue();
                                                        }
                                                    });
                                                    int i18 = i10;
                                                    boolean zChanged3 = composerImpl.changed(transitionAnimationState3) | ((i18 & 7168) == 2048);
                                                    Object objRememberedValue7 = composerImpl.rememberedValue();
                                                    if (zChanged3 || objRememberedValue7 == composer$Companion$Empty$12) {
                                                        objRememberedValue7 = new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt$CommonDecorationBox$3$3$1
                                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                            {
                                                                super(1);
                                                            }

                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj) {
                                                                long j15 = ((Size) obj).packedValue;
                                                                if (!(textFieldLabelPosition instanceof TextFieldLabelPosition.Above)) {
                                                                    float fFloatValue = ((Number) transitionAnimationState3.getValue()).floatValue();
                                                                    float fM419getWidthimpl = Size.m419getWidthimpl(j15) * fFloatValue;
                                                                    float fM417getHeightimpl = Size.m417getHeightimpl(j15) * fFloatValue;
                                                                    if (Size.m419getWidthimpl(((Size) mutableState.getValue()).packedValue) != fM419getWidthimpl || Size.m417getHeightimpl(((Size) mutableState.getValue()).packedValue) != fM417getHeightimpl) {
                                                                        mutableState.setValue(Size.m415boximpl(SizeKt.Size(fM419getWidthimpl, fM417getHeightimpl)));
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        };
                                                        composerImpl.updateRememberedValue(objRememberedValue7);
                                                    }
                                                    ComposerImpl composerImpl5 = composerImpl;
                                                    OutlinedTextFieldKt.OutlinedTextFieldLayout(companion2, function2, composableLambdaImpl2, composableLambdaImpl, composableLambdaImpl4, composableLambdaImpl5, composableLambdaImpl3, composableLambdaImpl7, z, textFieldLabelPosition, textFieldImplKt$sam$androidx_compose_material3_internal_FloatProducer$0, (Function1) objRememberedValue7, composableLambdaImplRememberComposableLambda8, composableLambdaImplRememberComposableLambda2, paddingValues, composerImpl5, ((i18 >> 3) & 112) | 6 | ((i15 << 21) & 234881024) | ((i18 << 18) & 1879048192), (57344 & (i15 >> 3)) | 384);
                                                    composerImpl2 = composerImpl5;
                                                    composerImpl2.end(false);
                                                    Unit unit3 = Unit.INSTANCE;
                                                }
                                                if (ComposerKt.isTraceInProgress()) {
                                                    ComposerKt.traceEventEnd();
                                                }
                                            }
                                            objRememberedValue5 = (TwoWayConverter) ColorVectorConverterKt.ColorToVector.mo781invoke(colorSpaceM461getColorSpaceimpl2);
                                            composerImpl3.updateRememberedValue(objRememberedValue5);
                                            TwoWayConverter twoWayConverter32 = (TwoWayConverter) objRememberedValue5;
                                            composerImpl3.startReplaceGroup(1023351670);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            composerImpl3.end(false);
                                            Color colorM456boximpl22 = Color.m456boximpl(j3);
                                            composerImpl3.startReplaceGroup(1023351670);
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            if (ComposerKt.isTraceInProgress()) {
                                            }
                                            composerImpl3.end(false);
                                            final State<Color> transitionAnimationStateCreateTransitionAnimation52 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, colorM456boximpl22, Color.m456boximpl(j3), (FiniteAnimationSpec) function36.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter32, "LabelContentColor", composerImpl3, 196608);
                                            objRememberedValue = composerImpl3.rememberedValue();
                                            companion.getClass();
                                            composer$Companion$Empty$1 = Composer.Companion.Empty;
                                            if (objRememberedValue == composer$Companion$Empty$1) {
                                            }
                                            final TextFieldImplKt$CommonDecorationBox$3$labelScope$1$1 textFieldImplKt$CommonDecorationBox$3$labelScope$1$12 = (TextFieldImplKt$CommonDecorationBox$3$labelScope$1$1) objRememberedValue;
                                            ComposableLambdaImpl composableLambdaImplRememberComposableLambda22 = null;
                                            if (function3 != null) {
                                            }
                                            if (z2) {
                                            }
                                            final long j92 = j;
                                            objRememberedValue2 = composerImpl.rememberedValue();
                                            if (objRememberedValue2 == composer$Companion$Empty$12) {
                                            }
                                            State state3 = (State) objRememberedValue2;
                                            if (function22 == null) {
                                                textFieldColors4 = textFieldColors3;
                                                composerImpl.startReplaceGroup(-570398724);
                                                composerImpl.end(false);
                                                composableLambdaImpl2 = null;
                                                final long j102 = z2 ? textFieldColors4.disabledPrefixColor : z3 ? textFieldColors4.errorPrefixColor : zBooleanValue ? textFieldColors4.focusedPrefixColor : textFieldColors4.unfocusedPrefixColor;
                                                objRememberedValue3 = composerImpl.rememberedValue();
                                                if (objRememberedValue3 == composer$Companion$Empty$12) {
                                                }
                                                State state22 = (State) objRememberedValue3;
                                                if (function25 == null) {
                                                    transitionAnimationState4 = transitionAnimationState;
                                                    composerImpl.startReplaceGroup(-569683492);
                                                    composerImpl.end(false);
                                                    composableLambdaImpl3 = null;
                                                    if (z2) {
                                                    }
                                                    if (function26 == null) {
                                                        composerImpl.startReplaceGroup(-569108132);
                                                        composerImpl.end(false);
                                                        composableLambdaImplRememberComposableLambda = null;
                                                        if (z2) {
                                                        }
                                                        if (function23 != null) {
                                                        }
                                                        if (z2) {
                                                        }
                                                        if (function24 != null) {
                                                        }
                                                        if (z2) {
                                                        }
                                                        if (function27 != null) {
                                                        }
                                                        i11 = WhenMappings.$EnumSwitchMapping$0[textFieldType.ordinal()];
                                                        if (i11 != 1) {
                                                        }
                                                        if (ComposerKt.isTraceInProgress()) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (i9 != 3) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                }
                                f6 = 1.0f;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl3.end(false);
                                Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation32 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf3, Float.valueOf(f6), (FiniteAnimationSpec) function34.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "PrefixSuffixOpacity", composerImpl3, 196608);
                                final FiniteAnimationSpec<Color> springSpecValue42 = MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl3);
                                Function3 function352 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$labelTextStyleColor$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                                        ((Number) obj3).intValue();
                                        ComposerImpl composerImpl42 = (ComposerImpl) ((Composer) obj2);
                                        composerImpl42.startReplaceGroup(1528582156);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:441)");
                                        }
                                        FiniteAnimationSpec<Color> finiteAnimationSpec = springSpecValue42;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl42.end(false);
                                        return finiteAnimationSpec;
                                    }
                                };
                                InputPhase inputPhase82 = (InputPhase) transitionUpdateTransition.getTargetState();
                                composerImpl3.startReplaceGroup(-107432127);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                if (iArr[inputPhase82.ordinal()] != 1) {
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl3.end(false);
                                ColorSpace colorSpaceM461getColorSpaceimpl3 = Color.m461getColorSpaceimpl(j7);
                                zChanged = composerImpl3.changed(colorSpaceM461getColorSpaceimpl3);
                                Object objRememberedValue42 = composerImpl3.rememberedValue();
                                Composer.Companion companion3 = Composer.Companion;
                                if (zChanged) {
                                }
                            } else {
                                if (i8 == 2) {
                                    if (z6) {
                                        f5 = 0.0f;
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl3.end(false);
                                    Float fValueOf32 = Float.valueOf(f5);
                                    InputPhase inputPhase72 = (InputPhase) transitionUpdateTransition.getTargetState();
                                    composerImpl3.startReplaceGroup(1128033978);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    i9 = iArr[inputPhase72.ordinal()];
                                    if (i9 != 1) {
                                    }
                                    f6 = 1.0f;
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl3.end(false);
                                    Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation322 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf32, Float.valueOf(f6), (FiniteAnimationSpec) function34.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "PrefixSuffixOpacity", composerImpl3, 196608);
                                    final FiniteAnimationSpec<Color> springSpecValue422 = MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl3);
                                    Function3 function3522 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$labelTextStyleColor$1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                                            ((Number) obj3).intValue();
                                            ComposerImpl composerImpl42 = (ComposerImpl) ((Composer) obj2);
                                            composerImpl42.startReplaceGroup(1528582156);
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:441)");
                                            }
                                            FiniteAnimationSpec<Color> finiteAnimationSpec = springSpecValue422;
                                            if (ComposerKt.isTraceInProgress()) {
                                                ComposerKt.traceEventEnd();
                                            }
                                            composerImpl42.end(false);
                                            return finiteAnimationSpec;
                                        }
                                    };
                                    InputPhase inputPhase822 = (InputPhase) transitionUpdateTransition.getTargetState();
                                    composerImpl3.startReplaceGroup(-107432127);
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    if (iArr[inputPhase822.ordinal()] != 1) {
                                    }
                                    if (ComposerKt.isTraceInProgress()) {
                                    }
                                    composerImpl3.end(false);
                                    ColorSpace colorSpaceM461getColorSpaceimpl32 = Color.m461getColorSpaceimpl(j7);
                                    zChanged = composerImpl3.changed(colorSpaceM461getColorSpaceimpl32);
                                    Object objRememberedValue422 = composerImpl3.rememberedValue();
                                    Composer.Companion companion32 = Composer.Companion;
                                    if (zChanged) {
                                    }
                                } else if (i8 != 3) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                f5 = 1.0f;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl3.end(false);
                                Float fValueOf322 = Float.valueOf(f5);
                                InputPhase inputPhase722 = (InputPhase) transitionUpdateTransition.getTargetState();
                                composerImpl3.startReplaceGroup(1128033978);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                i9 = iArr[inputPhase722.ordinal()];
                                if (i9 != 1) {
                                }
                                f6 = 1.0f;
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl3.end(false);
                                Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation3222 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf322, Float.valueOf(f6), (FiniteAnimationSpec) function34.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "PrefixSuffixOpacity", composerImpl3, 196608);
                                final FiniteAnimationSpec<Color> springSpecValue4222 = MotionSchemeKt.value(motionSchemeKeyTokens, composerImpl3);
                                Function3 function35222 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$labelTextStyleColor$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                                        ((Number) obj3).intValue();
                                        ComposerImpl composerImpl42 = (ComposerImpl) ((Composer) obj2);
                                        composerImpl42.startReplaceGroup(1528582156);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:441)");
                                        }
                                        FiniteAnimationSpec<Color> finiteAnimationSpec = springSpecValue4222;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl42.end(false);
                                        return finiteAnimationSpec;
                                    }
                                };
                                InputPhase inputPhase8222 = (InputPhase) transitionUpdateTransition.getTargetState();
                                composerImpl3.startReplaceGroup(-107432127);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                if (iArr[inputPhase8222.ordinal()] != 1) {
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl3.end(false);
                                ColorSpace colorSpaceM461getColorSpaceimpl322 = Color.m461getColorSpaceimpl(j7);
                                zChanged = composerImpl3.changed(colorSpaceM461getColorSpaceimpl322);
                                Object objRememberedValue4222 = composerImpl3.rememberedValue();
                                Composer.Companion companion322 = Composer.Companion;
                                if (zChanged) {
                                }
                            }
                        } else {
                            if (i7 == 2) {
                                if (z6) {
                                }
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                composerImpl3.end(false);
                                Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation22 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf2, Float.valueOf(f4), (FiniteAnimationSpec) function33.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "PlaceholderOpacity", composerImpl3, 196608);
                                Function3 function342 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$prefixSuffixOpacity$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                                        ((Number) obj3).intValue();
                                        ComposerImpl composerImpl42 = (ComposerImpl) ((Composer) obj2);
                                        composerImpl42.startReplaceGroup(-1868044898);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:429)");
                                        }
                                        FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue2;
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                        composerImpl42.end(false);
                                        return finiteAnimationSpec;
                                    }
                                };
                                InputPhase inputPhase62 = (InputPhase) transitionState.getCurrentState();
                                composerImpl3.startReplaceGroup(1128033978);
                                if (ComposerKt.isTraceInProgress()) {
                                }
                                i8 = iArr[inputPhase62.ordinal()];
                                if (i8 == 1) {
                                }
                            } else if (i7 != 3) {
                                throw new NoWhenBranchMatchedException();
                            }
                            f4 = 0.0f;
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            composerImpl3.end(false);
                            Transition.TransitionAnimationState transitionAnimationStateCreateTransitionAnimation222 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf2, Float.valueOf(f4), (FiniteAnimationSpec) function33.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "PlaceholderOpacity", composerImpl3, 196608);
                            Function3 function3422 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$prefixSuffixOpacity$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj, Object obj2, Object obj3) {
                                    ((Number) obj3).intValue();
                                    ComposerImpl composerImpl42 = (ComposerImpl) ((Composer) obj2);
                                    composerImpl42.startReplaceGroup(-1868044898);
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:429)");
                                    }
                                    FiniteAnimationSpec<Float> finiteAnimationSpec = springSpecValue2;
                                    if (ComposerKt.isTraceInProgress()) {
                                        ComposerKt.traceEventEnd();
                                    }
                                    composerImpl42.end(false);
                                    return finiteAnimationSpec;
                                }
                            };
                            InputPhase inputPhase622 = (InputPhase) transitionState.getCurrentState();
                            composerImpl3.startReplaceGroup(1128033978);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            i8 = iArr[inputPhase622.ordinal()];
                            if (i8 == 1) {
                            }
                        }
                    } else {
                        if (i6 == 2) {
                            if (z6) {
                            }
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            composerImpl3.end(false);
                            Float fValueOf22 = Float.valueOf(f3);
                            InputPhase inputPhase52 = (InputPhase) transitionUpdateTransition.getTargetState();
                            composerImpl3.startReplaceGroup(1435837472);
                            if (ComposerKt.isTraceInProgress()) {
                            }
                            i7 = iArr[inputPhase52.ordinal()];
                            if (i7 == 1) {
                            }
                        } else if (i6 != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        f3 = 0.0f;
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        composerImpl3.end(false);
                        Float fValueOf222 = Float.valueOf(f3);
                        InputPhase inputPhase522 = (InputPhase) transitionUpdateTransition.getTargetState();
                        composerImpl3.startReplaceGroup(1435837472);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        i7 = iArr[inputPhase522.ordinal()];
                        if (i7 == 1) {
                        }
                    }
                } else {
                    if (i5 == 2) {
                        if (z5) {
                            f2 = 0.0f;
                        }
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        composerImpl3.end(false);
                        final State<Float> transitionAnimationStateCreateTransitionAnimation6 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf, Float.valueOf(f2), (FiniteAnimationSpec) function32.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "LabelProgress", composerImpl3, 196608);
                        MotionSchemeKeyTokens motionSchemeKeyTokens2 = MotionSchemeKeyTokens.FastEffects;
                        final FiniteAnimationSpec<Float> springSpecValue22 = MotionSchemeKt.value(motionSchemeKeyTokens2, composerImpl3);
                        final FiniteAnimationSpec<Float> springSpecValue32 = MotionSchemeKt.value(MotionSchemeKeyTokens.SlowEffects, composerImpl3);
                        boolean z62 = z5;
                        Function3 function332 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$placeholderOpacity$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(3);
                            }

                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                Transition.Segment segment = (Transition.Segment) obj;
                                ((Number) obj3).intValue();
                                ComposerImpl composerImpl42 = (ComposerImpl) ((Composer) obj2);
                                composerImpl42.startReplaceGroup(-1154662212);
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:407)");
                                }
                                InputPhase inputPhase42 = InputPhase.Focused;
                                InputPhase inputPhase53 = InputPhase.UnfocusedEmpty;
                                FiniteAnimationSpec<Float> finiteAnimationSpec = segment.isTransitioningTo(inputPhase42, inputPhase53) ? springSpecValue22 : (segment.isTransitioningTo(inputPhase53, inputPhase42) || segment.isTransitioningTo(InputPhase.UnfocusedNotEmpty, inputPhase53)) ? springSpecValue32 : springSpecValue22;
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventEnd();
                                }
                                composerImpl42.end(false);
                                return finiteAnimationSpec;
                            }
                        };
                        InputPhase inputPhase42 = (InputPhase) transitionState.getCurrentState();
                        composerImpl3.startReplaceGroup(1435837472);
                        if (ComposerKt.isTraceInProgress()) {
                        }
                        i6 = iArr[inputPhase42.ordinal()];
                        final boolean z72 = z4;
                        if (i6 == 1) {
                        }
                    } else if (i5 != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    f2 = 1.0f;
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    composerImpl3.end(false);
                    final State<Float> transitionAnimationStateCreateTransitionAnimation62 = TransitionKt.createTransitionAnimation(transitionUpdateTransition, fValueOf, Float.valueOf(f2), (FiniteAnimationSpec) function32.invoke(transitionUpdateTransition.getSegment(), composerImpl3, 0), twoWayConverter, "LabelProgress", composerImpl3, 196608);
                    MotionSchemeKeyTokens motionSchemeKeyTokens22 = MotionSchemeKeyTokens.FastEffects;
                    final FiniteAnimationSpec<Float> springSpecValue222 = MotionSchemeKt.value(motionSchemeKeyTokens22, composerImpl3);
                    final FiniteAnimationSpec<Float> springSpecValue322 = MotionSchemeKt.value(MotionSchemeKeyTokens.SlowEffects, composerImpl3);
                    boolean z622 = z5;
                    Function3 function3322 = new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt$TextFieldTransitionScope$placeholderOpacity$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(3);
                        }

                        @Override // kotlin.jvm.functions.Function3
                        public final Object invoke(Object obj, Object obj2, Object obj3) {
                            Transition.Segment segment = (Transition.Segment) obj;
                            ((Number) obj3).intValue();
                            ComposerImpl composerImpl42 = (ComposerImpl) ((Composer) obj2);
                            composerImpl42.startReplaceGroup(-1154662212);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventStart("androidx.compose.material3.internal.TextFieldTransitionScope.<anonymous> (TextFieldImpl.kt:407)");
                            }
                            InputPhase inputPhase422 = InputPhase.Focused;
                            InputPhase inputPhase53 = InputPhase.UnfocusedEmpty;
                            FiniteAnimationSpec<Float> finiteAnimationSpec = segment.isTransitioningTo(inputPhase422, inputPhase53) ? springSpecValue222 : (segment.isTransitioningTo(inputPhase53, inputPhase422) || segment.isTransitioningTo(InputPhase.UnfocusedNotEmpty, inputPhase53)) ? springSpecValue322 : springSpecValue222;
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                            composerImpl42.end(false);
                            return finiteAnimationSpec;
                        }
                    };
                    InputPhase inputPhase422 = (InputPhase) transitionState.getCurrentState();
                    composerImpl3.startReplaceGroup(1435837472);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    i6 = iArr[inputPhase422.ordinal()];
                    final boolean z722 = z4;
                    if (i6 == 1) {
                    }
                }
            } else {
                if (i16 == 2) {
                    if (z5) {
                        f = 0.0f;
                    }
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    composerImpl3.end(false);
                    Float fValueOf4 = Float.valueOf(f);
                    InputPhase inputPhase32 = (InputPhase) transitionUpdateTransition.getTargetState();
                    composerImpl3.startReplaceGroup(-2036730335);
                    if (ComposerKt.isTraceInProgress()) {
                    }
                    i5 = iArr[inputPhase32.ordinal()];
                    if (i5 == 1) {
                    }
                } else if (i16 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                f = 1.0f;
                if (ComposerKt.isTraceInProgress()) {
                }
                composerImpl3.end(false);
                Float fValueOf42 = Float.valueOf(f);
                InputPhase inputPhase322 = (InputPhase) transitionUpdateTransition.getTargetState();
                composerImpl3.startReplaceGroup(-2036730335);
                if (ComposerKt.isTraceInProgress()) {
                }
                i5 = iArr[inputPhase322.ordinal()];
                if (i5 == 1) {
                }
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl2.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt.CommonDecorationBox.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextFieldImplKt.CommonDecorationBox(textFieldType, charSequence, function2, textFieldLabelPosition, function3, function22, function23, function24, function25, function26, function27, z, z2, z3, interactionSource, paddingValues, textFieldColors, function28, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$Decoration-3J-VO9M, reason: not valid java name */
    public static final void m323access$Decoration3JVO9M(long j, TextStyle textStyle, Function2 function2, Composer composer, final int i) {
        int i2;
        final long j2;
        final TextStyle textStyle2;
        final Function2 function22;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1208685580);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changed(textStyle) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 256 : 128;
        }
        if ((i2 & 147) == 146 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            function22 = function2;
            textStyle2 = textStyle;
            j2 = j;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.Decoration (TextFieldImpl.kt:325)");
            }
            ProvideContentColorTextStyleKt.m321ProvideContentColorTextStyle3JVO9M(j, textStyle, function2, composerImpl, i2 & 1022);
            j2 = j;
            textStyle2 = textStyle;
            function22 = function2;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$Decoration$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextFieldImplKt.m323access$Decoration3JVO9M(j2, textStyle2, function22, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* renamed from: access$Decoration-Iv8Zu3U, reason: not valid java name */
    public static final void m324access$DecorationIv8Zu3U(final long j, final Function2 function2, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(660142980);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(j) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.material3.internal.Decoration (TextFieldImpl.kt:330)");
            }
            CompositionLocalKt.CompositionLocalProvider(ContentColorKt.LocalContentColor.defaultProvidedValue$runtime_release(Color.m456boximpl(j)), function2, composerImpl, (i2 & 112) | 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.material3.internal.TextFieldImplKt$Decoration$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    TextFieldImplKt.m324access$DecorationIv8Zu3U(j, function2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final Modifier defaultErrorSemantics(Modifier modifier, boolean z, final String str) {
        return z ? SemanticsModifierKt.semantics(modifier, false, new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt.defaultErrorSemantics.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                String str2 = str;
                KProperty[] kPropertyArr = SemanticsPropertiesKt.$$delegatedProperties;
                SemanticsProperties.INSTANCE.getClass();
                ((SemanticsConfiguration) ((SemanticsPropertyReceiver) obj)).set(SemanticsProperties.Error, str2);
                return Unit.INSTANCE;
            }
        }) : modifier;
    }

    public static final Alignment.Horizontal getExpandedAlignment(TextFieldLabelPosition textFieldLabelPosition) {
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Above) {
            return ((TextFieldLabelPosition.Above) textFieldLabelPosition).alignment;
        }
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Attached) {
            return ((TextFieldLabelPosition.Attached) textFieldLabelPosition).expandedAlignment;
        }
        throw new IllegalArgumentException("Unknown position: " + textFieldLabelPosition);
    }

    public static final Alignment.Horizontal getMinimizedAlignment(TextFieldLabelPosition textFieldLabelPosition) {
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Above) {
            return ((TextFieldLabelPosition.Above) textFieldLabelPosition).alignment;
        }
        if (textFieldLabelPosition instanceof TextFieldLabelPosition.Attached) {
            return ((TextFieldLabelPosition.Attached) textFieldLabelPosition).minimizedAlignment;
        }
        throw new IllegalArgumentException("Unknown position: " + textFieldLabelPosition);
    }

    public static final float minimizedLabelHalfHeight(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.internal.minimizedLabelHalfHeight (TextFieldImpl.kt:530)");
        }
        MaterialTheme.INSTANCE.getClass();
        long j = MaterialTheme.getTypography(composer).bodySmall.paragraphStyle.lineHeight;
        TypeScaleTokens.INSTANCE.getClass();
        long j2 = TypeScaleTokens.BodySmallLineHeight;
        if ((1095216660480L & j) != 4294967296L) {
            j = j2;
        }
        float fMo53toDpGaN1DYA = ((Density) ((ComposerImpl) composer).consume(CompositionLocalsKt.LocalDensity)).mo53toDpGaN1DYA(j) / 2;
        Dp.Companion companion = Dp.Companion;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return fMo53toDpGaN1DYA;
    }

    public static final Modifier textFieldBackground(Modifier modifier, final ColorProducer colorProducer, final Shape shape) {
        return DrawModifierKt.drawWithCache(modifier, new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt.textFieldBackground.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                CacheDrawScope cacheDrawScope = (CacheDrawScope) obj;
                final Outline outlineMo41createOutlinePq9zytI = shape.mo41createOutlinePq9zytI(cacheDrawScope.cacheParams.mo361getSizeNHjbRc(), cacheDrawScope.cacheParams.getLayoutDirection(), cacheDrawScope);
                final ColorProducer colorProducer2 = colorProducer;
                return cacheDrawScope.onDrawBehind(new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt.textFieldBackground.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        OutlineKt.m492drawOutlinewDX37Ww$default((DrawScope) obj2, outlineMo41createOutlinePq9zytI, colorProducer2.mo262invoke0d7_KjU(), 0.0f, null, 60);
                        return Unit.INSTANCE;
                    }
                });
            }
        });
    }

    public static final float textFieldHorizontalIconPadding(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.internal.textFieldHorizontalIconPadding (TextFieldImpl.kt:523)");
        }
        float f = ((Dp) ((ComposerImpl) composer).consume(InteractiveComponentSizeKt.LocalMinimumInteractiveComponentSize)).value;
        if (Float.isNaN(f)) {
            f = 0;
        }
        SmallIconButtonTokens.INSTANCE.getClass();
        float f2 = (f - SmallIconButtonTokens.IconSize) / 2;
        float f3 = 0;
        if (f2 < f3) {
            f2 = f3;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return f2;
    }

    public static final Modifier textFieldLabelMinHeight(Modifier modifier, final Function0 function0) {
        return LayoutModifierKt.layout(modifier, new Function3() { // from class: androidx.compose.material3.internal.TextFieldImplKt.textFieldLabelMinHeight.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                MeasureScope measureScope = (MeasureScope) obj;
                Measurable measurable = (Measurable) obj2;
                long j = ((Constraints) obj3).value;
                float f = ((Dp) function0.invoke()).value;
                Dp.Companion.getClass();
                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.m816copyZbe2FdA$default(j, 0, 0, ConstraintsKt.m833constrainHeightK40F9xA(!Dp.m838equalsimpl0(f, Dp.Unspecified) ? measureScope.mo52roundToPx0680j_4(f) : 0, j), 0, 11));
                return measureScope.layout$1(placeableMo610measureBRTryo0.width, placeableMo610measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.material3.internal.TextFieldImplKt.textFieldLabelMinHeight.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj4) {
                        ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                        return Unit.INSTANCE;
                    }
                });
            }
        });
    }
}
