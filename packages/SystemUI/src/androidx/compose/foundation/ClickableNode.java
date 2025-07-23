package androidx.compose.foundation;

import android.view.KeyEvent;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.semantics.Role;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ClickableNode extends AbstractClickableNode {
    public /* synthetic */ ClickableNode(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableInteractionSource, indicationNodeFactory, z, str, role, function0);
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    public final Object clickPointerInput(PointerInputScope pointerInputScope, Continuation continuation) {
        Object detectTapAndPress = TapGestureDetectorKt.detectTapAndPress(pointerInputScope, new ClickableNode$clickPointerInput$2(this, null), new Function1() { // from class: androidx.compose.foundation.ClickableNode$clickPointerInput$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                long j = ((Offset) obj).packedValue;
                ClickableNode clickableNode = ClickableNode.this;
                if (clickableNode.enabled) {
                    clickableNode.onClick.invoke();
                }
                return Unit.INSTANCE;
            }
        }, continuation);
        return detectTapAndPress == CoroutineSingletons.COROUTINE_SUSPENDED ? detectTapAndPress : Unit.INSTANCE;
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    /* renamed from: onClickKeyDownEvent-ZmokQxo */
    public final boolean mo13onClickKeyDownEventZmokQxo(KeyEvent keyEvent) {
        return false;
    }

    @Override // androidx.compose.foundation.AbstractClickableNode
    /* renamed from: onClickKeyUpEvent-ZmokQxo */
    public final void mo14onClickKeyUpEventZmokQxo(KeyEvent keyEvent) {
        this.onClick.invoke();
    }

    private ClickableNode(MutableInteractionSource mutableInteractionSource, IndicationNodeFactory indicationNodeFactory, boolean z, String str, Role role, Function0 function0) {
        super(mutableInteractionSource, indicationNodeFactory, z, str, role, function0, null);
    }
}
