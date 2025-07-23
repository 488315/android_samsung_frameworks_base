package androidx.compose.ui.focus;

import androidx.compose.ui.ComposeUiFlags;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class FocusOwnerImpl$focusInvalidationManager$1 extends FunctionReferenceImpl implements Function0 {
    public FocusOwnerImpl$focusInvalidationManager$1(Object obj) {
        super(0, obj, FocusOwnerImpl.class, "invalidateOwnerFocusState", "invalidateOwnerFocusState()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        FocusOwnerImpl focusOwnerImpl = (FocusOwnerImpl) this.receiver;
        focusOwnerImpl.getClass();
        if ((ComposeUiFlags.isTrackFocusEnabled && focusOwnerImpl.activeFocusTargetNode == null) || focusOwnerImpl.rootFocusNode.getFocusState() == FocusStateImpl.Inactive) {
            focusOwnerImpl.onClearFocusForOwner.invoke();
        }
        return Unit.INSTANCE;
    }
}
