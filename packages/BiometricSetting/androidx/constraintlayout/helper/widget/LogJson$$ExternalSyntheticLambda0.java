package androidx.constraintlayout.helper.widget;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class LogJson$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LogJson f$0;

    public /* synthetic */ LogJson$$ExternalSyntheticLambda0(LogJson logJson, int i) {
        this.$r8$classId = i;
        this.f$0 = logJson;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LogJson.$r8$lambda$TrDMQ9reyW_61Fo0RCam71GwuYw(this.f$0);
                break;
            case 1:
                this.f$0.writeLog();
                break;
            default:
                LogJson.$r8$lambda$TrDMQ9reyW_61Fo0RCam71GwuYw(this.f$0);
                break;
        }
    }
}
