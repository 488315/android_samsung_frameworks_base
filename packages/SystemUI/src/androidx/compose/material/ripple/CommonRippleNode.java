package androidx.compose.material.ripple;

import androidx.collection.MutableScatterMap;
import androidx.compose.animation.core.Animatable;
import androidx.compose.foundation.BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.ClipOp;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope$drawContext$1;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.util.MathHelpersKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CommonRippleNode extends RippleNode {
    public final MutableScatterMap ripples;

    public /* synthetic */ CommonRippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(interactionSource, z, f, colorProducer, function0);
    }

    @Override // androidx.compose.material.ripple.RippleNode
    public final void addRipple$1(PressInteraction$Press pressInteraction$Press) {
        MutableScatterMap mutableScatterMap = this.ripples;
        Object[] objArr = mutableScatterMap.keys;
        Object[] objArr2 = mutableScatterMap.values;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            int i4 = (i << 3) + i3;
                            Object obj = objArr[i4];
                            RippleAnimation rippleAnimation = (RippleAnimation) objArr2[i4];
                            ((SnapshotMutableStateImpl) rippleAnimation.finishRequested$delegate).setValue(Boolean.TRUE);
                            rippleAnimation.finishSignalDeferred.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                } else {
                    i++;
                }
            }
        }
        boolean z = this.bounded;
        RippleAnimation rippleAnimation2 = new RippleAnimation(z ? Offset.m393boximpl(pressInteraction$Press.pressPosition) : null, this.targetRadius, z, null);
        mutableScatterMap.set(pressInteraction$Press, rippleAnimation2);
        BuildersKt.launch$default(getCoroutineScope(), null, null, new CommonRippleNode$addRipple$2(rippleAnimation2, this, pressInteraction$Press, null), 3);
        DrawModifierNodeKt.invalidateDraw(this);
    }

    @Override // androidx.compose.material.ripple.RippleNode
    public final void drawRipples(LayoutNodeDrawScope layoutNodeDrawScope) {
        float f;
        long[] jArr;
        Object[] objArr;
        Object[] objArr2;
        float f2;
        long[] jArr2;
        Object[] objArr3;
        Object[] objArr4;
        long j;
        int i;
        long Color;
        long Color2;
        CommonRippleNode commonRippleNode = this;
        float f3 = ((RippleAlpha) commonRippleNode.rippleAlpha.invoke()).pressedAlpha;
        if (f3 == 0.0f) {
            return;
        }
        MutableScatterMap mutableScatterMap = commonRippleNode.ripples;
        Object[] objArr5 = mutableScatterMap.keys;
        Object[] objArr6 = mutableScatterMap.values;
        long[] jArr3 = mutableScatterMap.metadata;
        int length = jArr3.length - 2;
        if (length < 0) {
            return;
        }
        int i2 = 0;
        while (true) {
            long j2 = jArr3[i2];
            if ((((~j2) << 7) & j2 & (-9187201950435737472L)) != -9187201950435737472L) {
                int i3 = 8;
                int i4 = 8 - ((~(i2 - length)) >>> 31);
                int i5 = 0;
                while (i5 < i4) {
                    if ((255 & j2) < 128) {
                        int i6 = (i2 << 3) + i5;
                        Object obj = objArr5[i6];
                        RippleAnimation rippleAnimation = (RippleAnimation) objArr6[i6];
                        Color = ColorKt.Color(Color.m461getRedimpl(r14), Color.m460getGreenimpl(r14), Color.m458getBlueimpl(r14), f3, Color.m459getColorSpaceimpl(commonRippleNode.color.mo261invoke0d7_KjU()));
                        Float f4 = rippleAnimation.startRadius;
                        i = i3;
                        CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
                        if (f4 == null) {
                            long mo545getSizeNHjbRc = canvasDrawScope.mo545getSizeNHjbRc();
                            float f5 = RippleAnimationKt.BoundedRippleExtraRadius;
                            f2 = f3;
                            rippleAnimation.startRadius = Float.valueOf(Math.max(Size.m417getWidthimpl(mo545getSizeNHjbRc), Size.m415getHeightimpl(mo545getSizeNHjbRc)) * 0.3f);
                        } else {
                            f2 = f3;
                        }
                        if (rippleAnimation.origin == null) {
                            rippleAnimation.origin = Offset.m393boximpl(canvasDrawScope.mo544getCenterF1C5BW0());
                        }
                        if (rippleAnimation.targetCenter == null) {
                            rippleAnimation.targetCenter = Offset.m393boximpl(OffsetKt.Offset(Size.m417getWidthimpl(canvasDrawScope.mo545getSizeNHjbRc()) / 2.0f, Size.m415getHeightimpl(canvasDrawScope.mo545getSizeNHjbRc()) / 2.0f));
                        }
                        float floatValue = (!((Boolean) ((SnapshotMutableStateImpl) rippleAnimation.finishRequested$delegate).getValue()).booleanValue() || ((Boolean) ((SnapshotMutableStateImpl) rippleAnimation.finishedFadingIn$delegate).getValue()).booleanValue()) ? ((Number) rippleAnimation.animatedAlpha.internalState.getValue()).floatValue() : 1.0f;
                        Float f6 = rippleAnimation.startRadius;
                        f6.getClass();
                        float f7 = floatValue;
                        jArr2 = jArr3;
                        float lerp = MathHelpersKt.lerp(f6.floatValue(), rippleAnimation.radius, ((Number) rippleAnimation.animatedRadiusPercent.internalState.getValue()).floatValue());
                        Offset offset = rippleAnimation.origin;
                        offset.getClass();
                        float m398getXimpl = Offset.m398getXimpl(offset.packedValue);
                        Offset offset2 = rippleAnimation.targetCenter;
                        offset2.getClass();
                        objArr3 = objArr5;
                        float m398getXimpl2 = Offset.m398getXimpl(offset2.packedValue);
                        Animatable animatable = rippleAnimation.animatedCenterPercent;
                        objArr4 = objArr6;
                        float lerp2 = MathHelpersKt.lerp(m398getXimpl, m398getXimpl2, ((Number) animatable.internalState.getValue()).floatValue());
                        Offset offset3 = rippleAnimation.origin;
                        offset3.getClass();
                        j = j2;
                        float m399getYimpl = Offset.m399getYimpl(offset3.packedValue);
                        Offset offset4 = rippleAnimation.targetCenter;
                        offset4.getClass();
                        long Offset = OffsetKt.Offset(lerp2, MathHelpersKt.lerp(m399getYimpl, Offset.m399getYimpl(offset4.packedValue), ((Number) animatable.internalState.getValue()).floatValue()));
                        Color2 = ColorKt.Color(Color.m461getRedimpl(Color), Color.m460getGreenimpl(Color), Color.m458getBlueimpl(Color), Color.m457getAlphaimpl(Color) * f7, Color.m459getColorSpaceimpl(Color));
                        if (rippleAnimation.bounded) {
                            float m417getWidthimpl = Size.m417getWidthimpl(canvasDrawScope.mo545getSizeNHjbRc());
                            float m415getHeightimpl = Size.m415getHeightimpl(canvasDrawScope.mo545getSizeNHjbRc());
                            ClipOp.Companion.getClass();
                            int i7 = ClipOp.Intersect;
                            CanvasDrawScope$drawContext$1 canvasDrawScope$drawContext$1 = canvasDrawScope.drawContext;
                            long m526getSizeNHjbRc = canvasDrawScope$drawContext$1.m526getSizeNHjbRc();
                            canvasDrawScope$drawContext$1.getCanvas().save();
                            canvasDrawScope$drawContext$1.transform.m528clipRectN_I0leg(0.0f, 0.0f, m417getWidthimpl, m415getHeightimpl, i7);
                            DrawScope.m532drawCircleVaOC9Bg$default(layoutNodeDrawScope, Color2, lerp, Offset, 0.0f, null, 0, 120);
                            BorderModifierNode$drawRoundRectBorder$1$$ExternalSyntheticOutline0.m(canvasDrawScope$drawContext$1, m526getSizeNHjbRc);
                        } else {
                            DrawScope.m532drawCircleVaOC9Bg$default(layoutNodeDrawScope, Color2, lerp, Offset, 0.0f, null, 0, 120);
                        }
                    } else {
                        f2 = f3;
                        jArr2 = jArr3;
                        objArr3 = objArr5;
                        objArr4 = objArr6;
                        j = j2;
                        i = i3;
                    }
                    j2 = j >> i;
                    i5++;
                    commonRippleNode = this;
                    objArr5 = objArr3;
                    i3 = i;
                    f3 = f2;
                    jArr3 = jArr2;
                    objArr6 = objArr4;
                }
                f = f3;
                jArr = jArr3;
                objArr = objArr5;
                objArr2 = objArr6;
                if (i4 != i3) {
                    return;
                }
            } else {
                f = f3;
                jArr = jArr3;
                objArr = objArr5;
                objArr2 = objArr6;
            }
            if (i2 == length) {
                return;
            }
            i2++;
            commonRippleNode = this;
            objArr5 = objArr;
            f3 = f;
            jArr3 = jArr;
            objArr6 = objArr2;
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        this.ripples.clear();
    }

    @Override // androidx.compose.material.ripple.RippleNode
    public final void removeRipple(PressInteraction$Press pressInteraction$Press) {
        RippleAnimation rippleAnimation = (RippleAnimation) this.ripples.get(pressInteraction$Press);
        if (rippleAnimation != null) {
            ((SnapshotMutableStateImpl) rippleAnimation.finishRequested$delegate).setValue(Boolean.TRUE);
            rippleAnimation.finishSignalDeferred.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Unit.INSTANCE);
        }
    }

    private CommonRippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0) {
        super(interactionSource, z, f, colorProducer, function0, null);
        this.ripples = new MutableScatterMap(0, 1, null);
    }
}
