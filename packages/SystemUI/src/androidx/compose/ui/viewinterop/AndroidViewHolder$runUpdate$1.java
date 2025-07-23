package androidx.compose.ui.viewinterop;

import android.view.ViewParent;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.node.OwnerSnapshotObserver;
import androidx.compose.ui.platform.AndroidComposeView;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AndroidViewHolder$runUpdate$1 extends Lambda implements Function0 {
    final /* synthetic */ AndroidViewHolder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AndroidViewHolder$runUpdate$1(AndroidViewHolder androidViewHolder) {
        super(0);
        this.this$0 = androidViewHolder;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [kotlin.jvm.functions.Function0, kotlin.jvm.internal.Lambda] */
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        AndroidViewHolder androidViewHolder = this.this$0;
        if (androidViewHolder.hasUpdateBlock && androidViewHolder.isAttachedToWindow()) {
            ViewParent parent = this.this$0.view.getParent();
            AndroidViewHolder androidViewHolder2 = this.this$0;
            if (parent == androidViewHolder2) {
                if (!androidViewHolder2.isAttachedToWindow()) {
                    InlineClassHelperKt.throwIllegalStateException("Expected AndroidViewHolder to be attached when observing reads.");
                }
                OwnerSnapshotObserver ownerSnapshotObserver = ((AndroidComposeView) androidViewHolder2.owner).snapshotObserver;
                AndroidViewHolder androidViewHolder3 = this.this$0;
                ownerSnapshotObserver.observeReads$ui_release(androidViewHolder3, AndroidViewHolder.OnCommitAffectingUpdate, androidViewHolder3.update);
            }
        }
        return Unit.INSTANCE;
    }
}
