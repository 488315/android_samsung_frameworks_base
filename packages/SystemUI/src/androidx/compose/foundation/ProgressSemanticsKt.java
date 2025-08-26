package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.semantics.ProgressBarRangeInfo;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.ClosedFloatRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public abstract class ProgressSemanticsKt {
    public static final Modifier progressSemantics(Modifier modifier, final float f, final ClosedFloatRange closedFloatRange, final int i) {
        return SemanticsModifierKt.semantics(modifier, true, new Function1() { // from class: androidx.compose.foundation.ProgressSemanticsKt.progressSemantics.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                SemanticsPropertiesKt.setProgressBarRangeInfo((SemanticsPropertyReceiver) obj, new ProgressBarRangeInfo(((Number) RangesKt___RangesKt.coerceIn(Float.valueOf(f), closedFloatRange)).floatValue(), closedFloatRange, i));
                return Unit.INSTANCE;
            }
        });
    }

    public static final Modifier progressSemantics(Modifier modifier) {
        return SemanticsModifierKt.semantics(modifier, true, new Function1() { // from class: androidx.compose.foundation.ProgressSemanticsKt.progressSemantics.2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                ProgressBarRangeInfo.Companion.getClass();
                SemanticsPropertiesKt.setProgressBarRangeInfo((SemanticsPropertyReceiver) obj, ProgressBarRangeInfo.Indeterminate);
                return Unit.INSTANCE;
            }
        });
    }
}
