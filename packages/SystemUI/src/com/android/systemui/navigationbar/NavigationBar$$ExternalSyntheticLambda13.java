package com.android.systemui.navigationbar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda13 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda13(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((NavigationBar) obj).mNavigationBarTransitions.setAutoDim(true);
                break;
            case 1:
                NavigationBar.m1983$r8$lambda$wga8QWBlGQNgzzuKsJs_6mEsNE((NavigationBar) obj);
                break;
            case 2:
                NavigationBar.m1984$r8$lambda$zY6mSQB2R28kIk6vWzi6hNheNA((NavigationBar) obj);
                break;
            default:
                ((NavigationBarView) obj).updateStates();
                break;
        }
    }
}
