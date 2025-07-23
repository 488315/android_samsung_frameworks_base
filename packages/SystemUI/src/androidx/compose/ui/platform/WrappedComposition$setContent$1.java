package androidx.compose.ui.platform;

import androidx.compose.runtime.Composition;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.lifecycle.Lifecycle;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class WrappedComposition$setContent$1 extends Lambda implements Function1 {
    final /* synthetic */ Function2 $content;
    final /* synthetic */ WrappedComposition this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrappedComposition$setContent$1(WrappedComposition wrappedComposition, Function2 function2) {
        super(1);
        this.this$0 = wrappedComposition;
        this.$content = function2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        AndroidComposeView.ViewTreeOwners viewTreeOwners = (AndroidComposeView.ViewTreeOwners) obj;
        if (!this.this$0.disposed) {
            Lifecycle lifecycle = viewTreeOwners.lifecycleOwner.getLifecycle();
            WrappedComposition wrappedComposition = this.this$0;
            wrappedComposition.lastContent = this.$content;
            if (wrappedComposition.addedToLifecycle == null) {
                wrappedComposition.addedToLifecycle = lifecycle;
                lifecycle.addObserver(wrappedComposition);
            } else if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                final WrappedComposition wrappedComposition2 = this.this$0;
                Composition composition = wrappedComposition2.original;
                final Function2 function2 = this.$content;
                composition.setContent(new ComposableLambdaImpl(-2000640158, true, new Function2() { // from class: androidx.compose.ui.platform.WrappedComposition$setContent$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:43:0x00b2, code lost:
                    
                        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L47;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d6, code lost:
                    
                        if (r4 == androidx.compose.runtime.Composer.Companion.Empty) goto L52;
                     */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r7, java.lang.Object r8) {
                        /*
                            Method dump skipped, instructions count: 272
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.WrappedComposition$setContent$1.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }));
            }
        }
        return Unit.INSTANCE;
    }
}
