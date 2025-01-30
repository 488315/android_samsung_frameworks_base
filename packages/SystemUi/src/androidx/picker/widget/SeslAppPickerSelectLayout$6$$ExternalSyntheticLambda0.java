package androidx.picker.widget;

import androidx.picker.common.log.LogTagHelperKt;
import androidx.picker.widget.SeslAppPickerSelectLayout;
import androidx.recyclerview.widget.RecyclerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SeslAppPickerSelectLayout$6$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SeslAppPickerSelectLayout.C03696 f$0;
    public final /* synthetic */ RecyclerView.ItemAnimator f$1;

    public /* synthetic */ SeslAppPickerSelectLayout$6$$ExternalSyntheticLambda0(SeslAppPickerSelectLayout.C03696 c03696, RecyclerView.ItemAnimator itemAnimator) {
        this.f$0 = c03696;
        this.f$1 = itemAnimator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SeslAppPickerSelectLayout.C03696 c03696 = this.f$0;
        RecyclerView.ItemAnimator itemAnimator = this.f$1;
        LogTagHelperKt.debug(SeslAppPickerSelectLayout.this, "setItemAnimator =" + itemAnimator);
        SeslAppPickerSelectLayout.this.mSelectedListView.setItemAnimator(itemAnimator);
    }
}
