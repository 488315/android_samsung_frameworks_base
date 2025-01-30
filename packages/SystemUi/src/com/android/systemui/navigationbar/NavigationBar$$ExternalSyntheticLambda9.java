package com.android.systemui.navigationbar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda9(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((NavigationBar) this.f$0).mNavigationBarTransitions.setAutoDim(true);
                break;
            case 1:
                NavigationBarView navigationBarView = (NavigationBarView) ((NavigationBar) this.f$0).mView;
                navigationBarView.mLayoutTransitionsEnabled = true;
                navigationBarView.updateLayoutTransitionsEnabled();
                break;
            case 2:
                NavigationBar navigationBar = (NavigationBar) this.f$0;
                if (navigationBar.onHomeLongClick(((NavigationBarView) navigationBar.mView).getHomeButton().mCurrentView)) {
                    ((NavigationBarView) navigationBar.mView).getHomeButton().mCurrentView.performHapticFeedback(0, 1);
                    break;
                }
                break;
            default:
                ((NavigationBarView) this.f$0).updateStates();
                break;
        }
    }
}
