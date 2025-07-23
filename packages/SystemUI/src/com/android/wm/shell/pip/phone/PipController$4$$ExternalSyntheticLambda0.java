package com.android.wm.shell.pip.phone;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipController$4$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                PipController pipController = PipController.this;
                pipController.onDisplayChangedUncheck(pipController.mDisplayController.getDisplayLayout(pipController.mPipDisplayLayoutState.mDisplayId), false);
                break;
            default:
                PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
                if (!pipTouchHandler.mTouchState.mIsUserInteracting) {
                    pipTouchHandler.mMenuController.showMenuInternal(pipTouchHandler.mPipBoundsState.getBounds(), pipTouchHandler.willResizeMenu(), false, pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                    break;
                }
                break;
        }
    }
}
