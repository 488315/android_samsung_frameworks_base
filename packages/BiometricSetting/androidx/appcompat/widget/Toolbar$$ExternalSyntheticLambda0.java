package androidx.appcompat.widget;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class Toolbar$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Toolbar f$0;

    public /* synthetic */ Toolbar$$ExternalSyntheticLambda0(Toolbar toolbar, int i) {
        this.$r8$classId = i;
        this.f$0 = toolbar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.invalidateMenu();
                break;
            default:
                this.f$0.collapseActionView();
                break;
        }
    }
}
