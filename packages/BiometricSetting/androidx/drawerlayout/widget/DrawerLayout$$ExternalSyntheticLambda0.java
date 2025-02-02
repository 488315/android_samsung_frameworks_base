package androidx.drawerlayout.widget;

import androidx.drawerlayout.widget.DrawerLayout;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DrawerLayout$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DrawerLayout$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DrawerLayout) this.f$0).closeDrawers(false);
                break;
            default:
                ((DrawerLayout.ViewDragCallback) this.f$0).peekDrawer();
                break;
        }
    }
}
