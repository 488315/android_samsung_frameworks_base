package androidx.compose.foundation.text;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.ui.layout.LayoutModifier;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.text.input.TransformedText;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.LayoutDirection;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class HorizontalScrollLayoutModifier implements LayoutModifier {
    public final int cursorOffset;
    public final TextFieldScrollerPosition scrollerPosition;
    public final Function0 textLayoutResultProvider;
    public final TransformedText transformedText;

    public HorizontalScrollLayoutModifier(TextFieldScrollerPosition textFieldScrollerPosition, int i, TransformedText transformedText, Function0 function0) {
        this.scrollerPosition = textFieldScrollerPosition;
        this.cursorOffset = i;
        this.transformedText = transformedText;
        this.textLayoutResultProvider = function0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HorizontalScrollLayoutModifier)) {
            return false;
        }
        HorizontalScrollLayoutModifier horizontalScrollLayoutModifier = (HorizontalScrollLayoutModifier) obj;
        return Intrinsics.areEqual(this.scrollerPosition, horizontalScrollLayoutModifier.scrollerPosition) && this.cursorOffset == horizontalScrollLayoutModifier.cursorOffset && Intrinsics.areEqual(this.transformedText, horizontalScrollLayoutModifier.transformedText) && Intrinsics.areEqual(this.textLayoutResultProvider, horizontalScrollLayoutModifier.textLayoutResultProvider);
    }

    public final int hashCode() {
        return this.textLayoutResultProvider.hashCode() + ((this.transformedText.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.cursorOffset, this.scrollerPosition.hashCode() * 31, 31)) * 31);
    }

    @Override // androidx.compose.ui.layout.LayoutModifier
    /* renamed from: measure-3p2s80s */
    public final MeasureResult mo108measure3p2s80s(final MeasureScope measureScope, Measurable measurable, long j) {
        long j2;
        MeasureResult layout$1;
        if (measurable.maxIntrinsicWidth(Constraints.m820getMaxHeightimpl(j)) < Constraints.m821getMaxWidthimpl(j)) {
            j2 = j;
        } else {
            j2 = j;
            j = Constraints.m814copyZbe2FdA$default(j2, 0, Integer.MAX_VALUE, 0, 0, 13);
        }
        final Placeable mo608measureBRTryo0 = measurable.mo608measureBRTryo0(j);
        final int min = Math.min(mo608measureBRTryo0.width, Constraints.m821getMaxWidthimpl(j2));
        layout$1 = measureScope.layout$1(min, mo608measureBRTryo0.height, MapsKt__MapsKt.emptyMap(), new Function1() { // from class: androidx.compose.foundation.text.HorizontalScrollLayoutModifier$measure$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Placeable.PlacementScope placementScope = (Placeable.PlacementScope) obj;
                MeasureScope measureScope2 = MeasureScope.this;
                HorizontalScrollLayoutModifier horizontalScrollLayoutModifier = this;
                int i = horizontalScrollLayoutModifier.cursorOffset;
                TextLayoutResultProxy textLayoutResultProxy = (TextLayoutResultProxy) horizontalScrollLayoutModifier.textLayoutResultProvider.invoke();
                this.scrollerPosition.update(Orientation.Horizontal, TextFieldScrollKt.access$getCursorRectInScroller(measureScope2, i, horizontalScrollLayoutModifier.transformedText, textLayoutResultProxy != null ? textLayoutResultProxy.value : null, MeasureScope.this.getLayoutDirection() == LayoutDirection.Rtl, mo608measureBRTryo0.width), min, mo608measureBRTryo0.width);
                placementScope.placeRelative(mo608measureBRTryo0, Math.round(-((SnapshotMutableFloatStateImpl) this.scrollerPosition.offset$delegate).getFloatValue()), 0, 0.0f);
                return Unit.INSTANCE;
            }
        });
        return layout$1;
    }

    public final String toString() {
        return "HorizontalScrollLayoutModifier(scrollerPosition=" + this.scrollerPosition + ", cursorOffset=" + this.cursorOffset + ", transformedText=" + this.transformedText + ", textLayoutResultProvider=" + this.textLayoutResultProvider + ')';
    }
}
