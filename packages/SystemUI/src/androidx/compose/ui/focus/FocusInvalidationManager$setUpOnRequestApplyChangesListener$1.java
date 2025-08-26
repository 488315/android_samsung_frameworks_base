package androidx.compose.ui.focus;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class FocusInvalidationManager$setUpOnRequestApplyChangesListener$1 extends FunctionReferenceImpl implements Function0 {
    public FocusInvalidationManager$setUpOnRequestApplyChangesListener$1(Object obj) {
        super(0, obj, FocusInvalidationManager.class, "invalidateNodes", "invalidateNodes()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        FocusInvalidationManager.access$invalidateNodes((FocusInvalidationManager) this.receiver);
        return Unit.INSTANCE;
    }
}
