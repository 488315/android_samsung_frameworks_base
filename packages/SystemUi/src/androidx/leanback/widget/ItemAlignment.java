package androidx.leanback.widget;

import androidx.leanback.widget.ItemAlignmentFacet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ItemAlignment {
    public final Axis vertical = new Axis(1);
    public final Axis horizontal = new Axis(0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Axis extends ItemAlignmentFacet.ItemAlignmentDef {
        public final int mOrientation;

        public Axis(int i) {
            this.mOrientation = i;
        }
    }
}
