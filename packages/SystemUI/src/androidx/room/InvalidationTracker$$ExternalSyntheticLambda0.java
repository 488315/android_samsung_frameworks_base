package androidx.room;

import androidx.room.support.AutoCloser;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class InvalidationTracker$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InvalidationTracker f$0;

    public /* synthetic */ InvalidationTracker$$ExternalSyntheticLambda0(InvalidationTracker invalidationTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = invalidationTracker;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                AutoCloser autoCloser = this.f$0.autoCloser;
                if (autoCloser != null) {
                    autoCloser.incrementCountAndEnsureDbIsOpen();
                }
                return Unit.INSTANCE;
            case 1:
                AutoCloser autoCloser2 = this.f$0.autoCloser;
                if (autoCloser2 != null) {
                    autoCloser2.decrementCountAndScheduleClose();
                }
                return Unit.INSTANCE;
            default:
                InvalidationTracker invalidationTracker = this.f$0;
                return Boolean.valueOf(!invalidationTracker.database.inCompatibilityMode$room_runtime_release() || invalidationTracker.database.isOpenInternal());
        }
    }
}
