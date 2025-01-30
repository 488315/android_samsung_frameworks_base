package com.android.systemui.statusbar;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KshView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KshView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008f  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        int position;
        switch (this.$r8$classId) {
            case 0:
                KshView kshView = (KshView) this.f$0;
                boolean canScrollHorizontally = kshView.mKshGroupRecyclerView.canScrollHorizontally(1);
                boolean canScrollHorizontally2 = kshView.mKshGroupRecyclerView.canScrollHorizontally(-1);
                boolean z = kshView.mRightScrolled;
                if (z && !canScrollHorizontally) {
                    kshView.moveSelector(kshView.isRTL() ? 0 : kshView.mKshViewAdapter.getItemCount() - 1);
                    break;
                } else if (!z && !canScrollHorizontally2) {
                    kshView.moveSelector(kshView.isRTL() ? kshView.mKshViewAdapter.getItemCount() - 1 : 0);
                    break;
                } else {
                    if (kshView.isRTL()) {
                        kshView.mRightScrolled = !kshView.mRightScrolled;
                    }
                    if (kshView.mRightScrolled) {
                        LinearLayoutManager linearLayoutManager = kshView.mLayoutManager;
                        View findOneVisibleChild = linearLayoutManager.findOneVisibleChild(linearLayoutManager.getChildCount() - 1, -1, true, false);
                        if (findOneVisibleChild != null) {
                            position = RecyclerView.LayoutManager.getPosition(findOneVisibleChild);
                            if (kshView.mMaxColumn != 1) {
                                kshView.mPosition = kshView.mLayoutManager.findFirstVisibleItemPosition() + (kshView.mRightScrolled ? 1 : 0);
                            } else {
                                kshView.mPosition = position + (kshView.mRightScrolled ? 1 : -1);
                            }
                            if (kshView.mPosition < 0) {
                                kshView.mPosition = 0;
                            }
                            if (kshView.mPosition >= kshView.mKshViewAdapter.getItemCount()) {
                                kshView.mPosition = kshView.mKshViewAdapter.getItemCount() - 1;
                            }
                            kshView.mKshGroupRecyclerView.smoothScrollToPosition(kshView.mPosition);
                            kshView.moveSelector(kshView.mPosition);
                            break;
                        }
                        position = -1;
                        if (kshView.mMaxColumn != 1) {
                        }
                        if (kshView.mPosition < 0) {
                        }
                        if (kshView.mPosition >= kshView.mKshViewAdapter.getItemCount()) {
                        }
                        kshView.mKshGroupRecyclerView.smoothScrollToPosition(kshView.mPosition);
                        kshView.moveSelector(kshView.mPosition);
                    } else {
                        LinearLayoutManager linearLayoutManager2 = kshView.mLayoutManager;
                        View findOneVisibleChild2 = linearLayoutManager2.findOneVisibleChild(0, linearLayoutManager2.getChildCount(), true, false);
                        if (findOneVisibleChild2 != null) {
                            position = RecyclerView.LayoutManager.getPosition(findOneVisibleChild2);
                            if (kshView.mMaxColumn != 1) {
                            }
                            if (kshView.mPosition < 0) {
                            }
                            if (kshView.mPosition >= kshView.mKshViewAdapter.getItemCount()) {
                            }
                            kshView.mKshGroupRecyclerView.smoothScrollToPosition(kshView.mPosition);
                            kshView.moveSelector(kshView.mPosition);
                        }
                        position = -1;
                        if (kshView.mMaxColumn != 1) {
                        }
                        if (kshView.mPosition < 0) {
                        }
                        if (kshView.mPosition >= kshView.mKshViewAdapter.getItemCount()) {
                        }
                        kshView.mKshGroupRecyclerView.smoothScrollToPosition(kshView.mPosition);
                        kshView.moveSelector(kshView.mPosition);
                    }
                }
            default:
                KshView kshView2 = KshView.this;
                kshView2.moveSelector(kshView2.mPosition);
                break;
        }
    }
}
