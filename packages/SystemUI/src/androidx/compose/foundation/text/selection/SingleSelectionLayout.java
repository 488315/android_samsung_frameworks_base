package androidx.compose.foundation.text.selection;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SingleSelectionLayout implements SelectionLayout {
    public final int endSlot;
    public final SelectableInfo info;
    public final boolean isStartHandle;
    public final Selection previousSelection;
    public final int startSlot;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SingleSelectionLayout(boolean z, int i, int i2, Selection selection, SelectableInfo selectableInfo) {
        this.isStartHandle = z;
        this.startSlot = i;
        this.endSlot = i2;
        this.previousSelection = selection;
        this.info = selectableInfo;
    }

    public final CrossStatus getCrossStatus() {
        int i = this.startSlot;
        int i2 = this.endSlot;
        if (i < i2) {
            return CrossStatus.NOT_CROSSED;
        }
        if (i > i2) {
            return CrossStatus.CROSSED;
        }
        SelectableInfo selectableInfo = this.info;
        int i3 = selectableInfo.rawStartHandleOffset;
        int i4 = selectableInfo.rawEndHandleOffset;
        return i3 < i4 ? CrossStatus.NOT_CROSSED : i3 > i4 ? CrossStatus.CROSSED : CrossStatus.COLLAPSED;
    }

    public final String toString() {
        return "SingleSelectionLayout(isStartHandle=" + this.isStartHandle + ", crossed=" + getCrossStatus() + ", info=\n\t" + this.info + ')';
    }
}
