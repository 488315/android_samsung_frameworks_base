package androidx.leanback.widget;

/* loaded from: classes.dex */
public class ItemAlignment {
    public final Axis vertical = new Axis(1);
    public final Axis horizontal = new Axis(0);

    public final class Axis extends ItemAlignmentFacet$ItemAlignmentDef {
        public final int mOrientation;

        public Axis(int i) {
            this.mOrientation = i;
        }
    }
}
