package com.android.systemui.media.controls.ui.view;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class GutsViewHolder$marquee$1 implements Runnable {
    public final /* synthetic */ boolean $start;
    public final /* synthetic */ GutsViewHolder this$0;

    public GutsViewHolder$marquee$1(GutsViewHolder gutsViewHolder, boolean z) {
        this.this$0 = gutsViewHolder;
        this.$start = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.this$0.gutsText.setSelected(this.$start);
    }
}
