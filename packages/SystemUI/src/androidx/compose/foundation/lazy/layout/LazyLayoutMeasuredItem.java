package androidx.compose.foundation.lazy.layout;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface LazyLayoutMeasuredItem {
    /* renamed from: getConstraints-msEJaDk */
    long mo152getConstraintsmsEJaDk();

    int getIndex();

    Object getKey();

    int getLane();

    int getMainAxisSizeWithSpacings();

    /* renamed from: getOffset-Bjo55l4 */
    long mo154getOffsetBjo55l4(int i);

    Object getParentData(int i);

    int getPlaceablesCount();

    int getSpan();

    boolean isVertical();

    void position(int i, int i2, int i3, int i4);

    void setNonScrollableItem();
}
