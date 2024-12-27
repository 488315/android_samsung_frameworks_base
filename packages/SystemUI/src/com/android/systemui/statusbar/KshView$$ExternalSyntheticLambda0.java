package com.android.systemui.statusbar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KshView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KshView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009b  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r6 = this;
            int r0 = r6.$r8$classId
            java.lang.Object r6 = r6.f$0
            switch(r0) {
                case 0: goto L11;
                default: goto L7;
            }
        L7:
            com.android.systemui.statusbar.KshView$1 r6 = (com.android.systemui.statusbar.KshView.AnonymousClass1) r6
            com.android.systemui.statusbar.KshView r6 = com.android.systemui.statusbar.KshView.this
            int r0 = r6.mPosition
            r6.moveSelector(r0)
            return
        L11:
            com.android.systemui.statusbar.KshView r6 = (com.android.systemui.statusbar.KshView) r6
            androidx.recyclerview.widget.RecyclerView r0 = r6.mKshGroupRecyclerView
            r1 = 1
            boolean r0 = r0.canScrollHorizontally(r1)
            androidx.recyclerview.widget.RecyclerView r2 = r6.mKshGroupRecyclerView
            r3 = -1
            boolean r2 = r2.canScrollHorizontally(r3)
            boolean r4 = r6.mRightScrolled
            r5 = 0
            if (r4 == 0) goto L3e
            if (r0 != 0) goto L3e
            boolean r0 = r6.isRTL()
            if (r0 == 0) goto L2f
            goto L39
        L2f:
            com.android.systemui.statusbar.KshViewAdapter r0 = r6.mKshViewAdapter
            java.util.List r0 = r0.mData
            int r0 = r0.size()
            int r5 = r0 + (-1)
        L39:
            r6.moveSelector(r5)
            goto Lcc
        L3e:
            if (r4 != 0) goto L57
            if (r2 != 0) goto L57
            boolean r0 = r6.isRTL()
            if (r0 == 0) goto L52
            com.android.systemui.statusbar.KshViewAdapter r0 = r6.mKshViewAdapter
            java.util.List r0 = r0.mData
            int r0 = r0.size()
            int r5 = r0 + (-1)
        L52:
            r6.moveSelector(r5)
            goto Lcc
        L57:
            boolean r0 = r6.isRTL()
            if (r0 == 0) goto L62
            boolean r0 = r6.mRightScrolled
            r0 = r0 ^ r1
            r6.mRightScrolled = r0
        L62:
            boolean r0 = r6.mRightScrolled
            if (r0 == 0) goto L7a
            androidx.recyclerview.widget.LinearLayoutManager r0 = r6.mLayoutManager
            int r2 = r0.getChildCount()
            int r2 = r2 - r1
            android.view.View r0 = r0.findOneVisibleChild(r2, r3, r1, r5)
            if (r0 != 0) goto L75
        L73:
            r0 = r3
            goto L8b
        L75:
            int r0 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r0)
            goto L8b
        L7a:
            androidx.recyclerview.widget.LinearLayoutManager r0 = r6.mLayoutManager
            int r2 = r0.getChildCount()
            android.view.View r0 = r0.findOneVisibleChild(r5, r2, r1, r5)
            if (r0 != 0) goto L87
            goto L73
        L87:
            int r0 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r0)
        L8b:
            int r2 = r6.mMaxColumn
            if (r2 != r1) goto L9b
            androidx.recyclerview.widget.LinearLayoutManager r0 = r6.mLayoutManager
            int r0 = r0.findFirstVisibleItemPosition()
            boolean r2 = r6.mRightScrolled
            int r0 = r0 + r2
            r6.mPosition = r0
            goto La3
        L9b:
            boolean r2 = r6.mRightScrolled
            if (r2 == 0) goto La0
            r3 = r1
        La0:
            int r0 = r0 + r3
            r6.mPosition = r0
        La3:
            int r0 = r6.mPosition
            if (r0 >= 0) goto La9
            r6.mPosition = r5
        La9:
            int r0 = r6.mPosition
            com.android.systemui.statusbar.KshViewAdapter r2 = r6.mKshViewAdapter
            java.util.List r2 = r2.mData
            int r2 = r2.size()
            if (r0 < r2) goto Lc0
            com.android.systemui.statusbar.KshViewAdapter r0 = r6.mKshViewAdapter
            java.util.List r0 = r0.mData
            int r0 = r0.size()
            int r0 = r0 - r1
            r6.mPosition = r0
        Lc0:
            androidx.recyclerview.widget.RecyclerView r0 = r6.mKshGroupRecyclerView
            int r1 = r6.mPosition
            r0.smoothScrollToPosition(r1)
            int r0 = r6.mPosition
            r6.moveSelector(r0)
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KshView$$ExternalSyntheticLambda0.run():void");
    }
}
