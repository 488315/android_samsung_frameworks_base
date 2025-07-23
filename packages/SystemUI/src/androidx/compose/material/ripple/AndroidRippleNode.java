package androidx.compose.material.ripple;

import android.view.View;
import android.view.ViewGroup;
import androidx.compose.foundation.interaction.InteractionSource;
import androidx.compose.foundation.interaction.PressInteraction$Press;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ColorProducer;
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope;
import androidx.compose.ui.node.CompositionLocalConsumerModifierNodeKt;
import androidx.compose.ui.node.DrawModifierNodeKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import kotlin.Function;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Lambda;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AndroidRippleNode extends RippleNode implements RippleHostKey {
    public RippleContainer rippleContainer;
    public RippleHostView rippleHostView;
    public long rippleSize;

    public /* synthetic */ AndroidRippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(interactionSource, z, f, colorProducer, function0);
    }

    @Override // androidx.compose.material.ripple.RippleNode
    public final void addRipple$1(PressInteraction$Press pressInteraction$Press) {
        RippleContainer rippleContainer = this.rippleContainer;
        if (rippleContainer == null) {
            ViewGroup access$findNearestViewGroup = Ripple_androidKt.access$findNearestViewGroup((View) CompositionLocalConsumerModifierNodeKt.currentValueOf(this, AndroidCompositionLocals_androidKt.LocalView));
            int childCount = access$findNearestViewGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    RippleContainer rippleContainer2 = new RippleContainer(access$findNearestViewGroup.getContext());
                    access$findNearestViewGroup.addView(rippleContainer2);
                    rippleContainer = rippleContainer2;
                    break;
                } else {
                    View childAt = access$findNearestViewGroup.getChildAt(i);
                    if (childAt instanceof RippleContainer) {
                        rippleContainer = (RippleContainer) childAt;
                        break;
                    }
                    i++;
                }
            }
            this.rippleContainer = rippleContainer;
            rippleContainer.getClass();
        }
        RippleHostView rippleHostView = (RippleHostView) ((LinkedHashMap) rippleContainer.rippleHostMap.indicationToHostMap).get(this);
        if (rippleHostView == null) {
            ArrayList arrayList = (ArrayList) rippleContainer.unusedRippleHosts;
            rippleHostView = (RippleHostView) (arrayList.isEmpty() ? null : arrayList.remove(0));
            if (rippleHostView == null) {
                if (rippleContainer.nextHostIndex > CollectionsKt__CollectionsKt.getLastIndex(rippleContainer.rippleHosts)) {
                    rippleHostView = new RippleHostView(rippleContainer.getContext());
                    rippleContainer.addView(rippleHostView);
                    ((ArrayList) rippleContainer.rippleHosts).add(rippleHostView);
                } else {
                    rippleHostView = (RippleHostView) ((ArrayList) rippleContainer.rippleHosts).get(rippleContainer.nextHostIndex);
                    RippleHostKey rippleHostKey = (RippleHostKey) ((LinkedHashMap) rippleContainer.rippleHostMap.hostToIndicationMap).get(rippleHostView);
                    if (rippleHostKey != null) {
                        rippleHostKey.onResetRippleHostView();
                        RippleHostMap rippleHostMap = rippleContainer.rippleHostMap;
                        RippleHostView rippleHostView2 = (RippleHostView) ((LinkedHashMap) rippleHostMap.indicationToHostMap).get(rippleHostKey);
                        if (rippleHostView2 != null) {
                        }
                        rippleHostMap.indicationToHostMap.remove(rippleHostKey);
                        rippleHostView.disposeRipple();
                    }
                }
                int i2 = rippleContainer.nextHostIndex;
                if (i2 < rippleContainer.MaxRippleHosts - 1) {
                    rippleContainer.nextHostIndex = i2 + 1;
                } else {
                    rippleContainer.nextHostIndex = 0;
                }
            }
            RippleHostMap rippleHostMap2 = rippleContainer.rippleHostMap;
            rippleHostMap2.indicationToHostMap.put(this, rippleHostView);
            rippleHostMap2.hostToIndicationMap.put(rippleHostView, this);
        }
        RippleHostView rippleHostView3 = rippleHostView;
        long j = this.rippleSize;
        int roundToInt = MathKt__MathJVMKt.roundToInt(this.targetRadius);
        long mo261invoke0d7_KjU = this.color.mo261invoke0d7_KjU();
        float f = ((RippleAlpha) this.rippleAlpha.invoke()).pressedAlpha;
        Function function = new Function0() { // from class: androidx.compose.material.ripple.AndroidRippleNode$addRipple$1$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DrawModifierNodeKt.invalidateDraw(AndroidRippleNode.this);
                return Unit.INSTANCE;
            }
        };
        boolean z = this.bounded;
        if (rippleHostView3.ripple == null || !Boolean.valueOf(z).equals(rippleHostView3.bounded)) {
            UnprojectedRipple unprojectedRipple = new UnprojectedRipple(z);
            rippleHostView3.setBackground(unprojectedRipple);
            rippleHostView3.ripple = unprojectedRipple;
            rippleHostView3.bounded = Boolean.valueOf(z);
        }
        UnprojectedRipple unprojectedRipple2 = rippleHostView3.ripple;
        unprojectedRipple2.getClass();
        rippleHostView3.onInvalidateRipple = (Lambda) function;
        rippleHostView3.m244updateRipplePropertiesbiQXAtU(j, mo261invoke0d7_KjU, roundToInt, f);
        if (z) {
            unprojectedRipple2.setHotspot(Offset.m398getXimpl(pressInteraction$Press.pressPosition), Offset.m399getYimpl(pressInteraction$Press.pressPosition));
        } else {
            unprojectedRipple2.setHotspot(unprojectedRipple2.getBounds().centerX(), unprojectedRipple2.getBounds().centerY());
        }
        rippleHostView3.setRippleState(true);
        this.rippleHostView = rippleHostView3;
        DrawModifierNodeKt.invalidateDraw(this);
    }

    @Override // androidx.compose.material.ripple.RippleNode
    public final void drawRipples(LayoutNodeDrawScope layoutNodeDrawScope) {
        CanvasDrawScope canvasDrawScope = layoutNodeDrawScope.canvasDrawScope;
        this.rippleSize = canvasDrawScope.mo545getSizeNHjbRc();
        Canvas canvas = canvasDrawScope.drawContext.getCanvas();
        RippleHostView rippleHostView = this.rippleHostView;
        if (rippleHostView != null) {
            rippleHostView.m244updateRipplePropertiesbiQXAtU(canvasDrawScope.mo545getSizeNHjbRc(), this.color.mo261invoke0d7_KjU(), MathKt__MathJVMKt.roundToInt(this.targetRadius), ((RippleAlpha) this.rippleAlpha.invoke()).pressedAlpha);
            android.graphics.Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
            rippleHostView.draw(((AndroidCanvas) canvas).internalCanvas);
        }
    }

    @Override // androidx.compose.ui.Modifier.Node
    public final void onDetach() {
        RippleContainer rippleContainer = this.rippleContainer;
        if (rippleContainer != null) {
            onResetRippleHostView();
            RippleHostView rippleHostView = (RippleHostView) ((LinkedHashMap) rippleContainer.rippleHostMap.indicationToHostMap).get(this);
            if (rippleHostView != null) {
                rippleHostView.disposeRipple();
                RippleHostMap rippleHostMap = rippleContainer.rippleHostMap;
                RippleHostView rippleHostView2 = (RippleHostView) ((LinkedHashMap) rippleHostMap.indicationToHostMap).get(this);
                if (rippleHostView2 != null) {
                }
                rippleHostMap.indicationToHostMap.remove(this);
                ((ArrayList) rippleContainer.unusedRippleHosts).add(rippleHostView);
            }
        }
    }

    @Override // androidx.compose.material.ripple.RippleHostKey
    public final void onResetRippleHostView() {
        this.rippleHostView = null;
        DrawModifierNodeKt.invalidateDraw(this);
    }

    @Override // androidx.compose.material.ripple.RippleNode
    public final void removeRipple(PressInteraction$Press pressInteraction$Press) {
        RippleHostView rippleHostView = this.rippleHostView;
        if (rippleHostView != null) {
            rippleHostView.setRippleState(false);
        }
    }

    private AndroidRippleNode(InteractionSource interactionSource, boolean z, float f, ColorProducer colorProducer, Function0 function0) {
        super(interactionSource, z, f, colorProducer, function0, null);
        Size.Companion.getClass();
        this.rippleSize = 0L;
    }
}
