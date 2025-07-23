package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectorInfo;
import androidx.compose.ui.platform.ValueElementSequence;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.bixby2.controller.mediacontrol.MoveFromCurrentPositionController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class OffsetKt {
    public static final Modifier offset(Modifier modifier, final Function1 function1) {
        return modifier.then(new OffsetPxElement(function1, true, new Function1() { // from class: androidx.compose.foundation.layout.OffsetKt$offset$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                InspectorInfo inspectorInfo = (InspectorInfo) obj;
                inspectorInfo.getClass();
                inspectorInfo.properties.set(Function1.this, MoveFromCurrentPositionController.OFFSET);
                return Unit.INSTANCE;
            }
        }));
    }

    /* renamed from: offset-VpY3zN4, reason: not valid java name */
    public static final Modifier m114offsetVpY3zN4(Modifier modifier, final float f, final float f2) {
        return modifier.then(new OffsetElement(f, f2, true, new Function1() { // from class: androidx.compose.foundation.layout.OffsetKt$offset$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                InspectorInfo inspectorInfo = (InspectorInfo) obj;
                inspectorInfo.getClass();
                Dp m835boximpl = Dp.m835boximpl(f);
                ValueElementSequence valueElementSequence = inspectorInfo.properties;
                valueElementSequence.set(m835boximpl, "x");
                valueElementSequence.set(Dp.m835boximpl(f2), "y");
                return Unit.INSTANCE;
            }
        }, null));
    }

    /* renamed from: offset-VpY3zN4$default, reason: not valid java name */
    public static Modifier m115offsetVpY3zN4$default(Modifier modifier, float f, float f2, int i) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        return m114offsetVpY3zN4(modifier, f, f2);
    }
}
