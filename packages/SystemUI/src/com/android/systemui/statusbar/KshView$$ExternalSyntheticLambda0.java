package com.android.systemui.statusbar;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes3.dex */
public final /* synthetic */ class KshView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KshView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int position;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KshView kshView = (KshView) obj;
                boolean zCanScrollHorizontally = kshView.mKshGroupRecyclerView.canScrollHorizontally(1);
                boolean zCanScrollHorizontally2 = kshView.mKshGroupRecyclerView.canScrollHorizontally(-1);
                boolean z = kshView.mRightScrolled;
                if (z && !zCanScrollHorizontally) {
                    kshView.moveSelector(kshView.isRTL() ? 0 : kshView.mKshViewAdapter.mData.size() - 1);
                    break;
                } else if (!z && !zCanScrollHorizontally2) {
                    kshView.moveSelector(kshView.isRTL() ? kshView.mKshViewAdapter.mData.size() - 1 : 0);
                    break;
                } else {
                    if (kshView.isRTL()) {
                        kshView.mRightScrolled = !kshView.mRightScrolled;
                    }
                    if (kshView.mRightScrolled) {
                        position = kshView.mLayoutManager.findLastCompletelyVisibleItemPosition();
                    } else {
                        LinearLayoutManager linearLayoutManager = kshView.mLayoutManager;
                        View viewFindOneVisibleChild = linearLayoutManager.findOneVisibleChild(0, linearLayoutManager.getChildCount(), true, false);
                        position = viewFindOneVisibleChild == null ? -1 : RecyclerView.LayoutManager.getPosition(viewFindOneVisibleChild);
                    }
                    if (kshView.mMaxColumn == 1) {
                        kshView.mPosition = kshView.mLayoutManager.findFirstVisibleItemPosition() + (kshView.mRightScrolled ? 1 : 0);
                    } else {
                        kshView.mPosition = position + (kshView.mRightScrolled ? 1 : -1);
                    }
                    if (kshView.mPosition < 0) {
                        kshView.mPosition = 0;
                    }
                    if (kshView.mPosition >= kshView.mKshViewAdapter.mData.size()) {
                        kshView.mPosition = kshView.mKshViewAdapter.mData.size() - 1;
                    }
                    kshView.mKshGroupRecyclerView.smoothScrollToPosition(kshView.mPosition);
                    kshView.moveSelector(kshView.mPosition);
                    break;
                }
            default:
                KshView kshView2 = KshView.this;
                kshView2.moveSelector(kshView2.mPosition);
                break;
        }
    }
}
