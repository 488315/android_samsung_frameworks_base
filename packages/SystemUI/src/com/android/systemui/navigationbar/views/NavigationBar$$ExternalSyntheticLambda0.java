package com.android.systemui.navigationbar.views;

/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((NavigationBarView) obj).updateStates();
                break;
            case 1:
                ((NavigationBar) obj).mNavigationBarTransitions.setAutoDim(true);
                break;
            case 2:
                NavigationBar.m2641$r8$lambda$HCBlCcLTUzLXTlWADhtZKsEEs4((NavigationBar) obj);
                break;
            default:
                NavigationBar.m2642$r8$lambda$PHzUD06Qyvu_i_ArfJRIF89Blg((NavigationBar) obj);
                break;
        }
    }
}
