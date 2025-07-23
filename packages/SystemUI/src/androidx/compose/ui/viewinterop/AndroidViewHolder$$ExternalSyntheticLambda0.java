package androidx.compose.ui.viewinterop;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AndroidViewHolder$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function0 f$0;

    public /* synthetic */ AndroidViewHolder$$ExternalSyntheticLambda0(int i, Function0 function0) {
        this.$r8$classId = i;
        this.f$0 = function0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Function0 function0 = this.f$0;
        switch (i) {
            case 0:
                Function1 function1 = AndroidViewHolder.OnCommitAffectingUpdate;
                function0.invoke();
                break;
            default:
                function0.invoke();
                break;
        }
    }
}
