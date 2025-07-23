package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FocusRequester {
    public final MutableVector focusRequesterNodes = new MutableVector(new FocusRequesterModifierNode[16], 0);
    public static final Companion Companion = new Companion(null);
    public static final FocusRequester Default = new FocusRequester();
    public static final FocusRequester Cancel = new FocusRequester();
    public static final FocusRequester Redirect = new FocusRequester();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: requestFocus-3ESFkO8$default, reason: not valid java name */
    public static void m376requestFocus3ESFkO8$default(FocusRequester focusRequester) {
        FocusDirection.Companion.getClass();
        final int i = FocusDirection.Enter;
        focusRequester.getClass();
        focusRequester.findFocusTargetNode$ui_release(new Function1() { // from class: androidx.compose.ui.focus.FocusRequester$requestFocus$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Boolean.valueOf(((FocusTargetNode) obj).m378requestFocus3ESFkO8(i));
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x0044, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean findFocusTargetNode$ui_release(kotlin.jvm.functions.Function1 r14) {
        /*
            Method dump skipped, instructions count: 240
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusRequester.findFocusTargetNode$ui_release(kotlin.jvm.functions.Function1):boolean");
    }
}
