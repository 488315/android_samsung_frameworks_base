package androidx.compose.foundation.pager;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.saveable.ListSaverKt;
import androidx.compose.runtime.saveable.SaverKt$Saver$1;
import java.util.Arrays;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DefaultPagerState extends PagerState {
    public static final Companion Companion = new Companion(null);
    public static final SaverKt$Saver$1 Saver = ListSaverKt.listSaver(new Function2() { // from class: androidx.compose.foundation.pager.DefaultPagerState$Companion$Saver$1
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            DefaultPagerState defaultPagerState = (DefaultPagerState) obj2;
            return Arrays.asList(Integer.valueOf(defaultPagerState.getCurrentPage()), Float.valueOf(RangesKt___RangesKt.coerceIn(defaultPagerState.getCurrentPageOffsetFraction(), -0.5f, 0.5f)), Integer.valueOf(defaultPagerState.getPageCount()));
        }
    }, new Function1() { // from class: androidx.compose.foundation.pager.DefaultPagerState$Companion$Saver$2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo779invoke(Object obj) {
            final List list = (List) obj;
            return new DefaultPagerState(((Integer) list.get(0)).intValue(), ((Float) list.get(1)).floatValue(), new Function0() { // from class: androidx.compose.foundation.pager.DefaultPagerState$Companion$Saver$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return (Integer) list.get(2);
                }
            });
        }
    });
    public final MutableState pageCountState;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DefaultPagerState(int i, float f, Function0 function0) {
        super(i, f);
        this.pageCountState = SnapshotStateKt.mutableStateOf$default(function0);
    }

    @Override // androidx.compose.foundation.pager.PagerState
    public final int getPageCount() {
        return ((Number) ((Function0) ((SnapshotMutableStateImpl) this.pageCountState).getValue()).invoke()).intValue();
    }
}
