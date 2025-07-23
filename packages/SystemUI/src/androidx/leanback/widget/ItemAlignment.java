package androidx.leanback.widget;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ItemAlignment {
    public final Axis vertical = new Axis(1);
    public final Axis horizontal = new Axis(0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Axis extends ItemAlignmentFacet$ItemAlignmentDef {
        public final int mOrientation;

        public Axis(int i) {
            this.mOrientation = i;
        }
    }
}
