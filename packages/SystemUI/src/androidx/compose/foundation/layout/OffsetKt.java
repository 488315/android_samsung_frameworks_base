package androidx.compose.foundation.layout;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectorInfo;
import androidx.compose.ui.platform.ValueElementSequence;
import androidx.compose.ui.unit.Dp;
import com.android.systemui.bixby2.controller.mediacontrol.MoveFromCurrentPositionController;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class OffsetKt {
    public static final Modifier offset(Modifier modifier, final Function1 function1) {
        return modifier.then(new OffsetPxElement(function1, true, new Function1() { // from class: androidx.compose.foundation.layout.OffsetKt.offset.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                InspectorInfo inspectorInfo = (InspectorInfo) obj;
                inspectorInfo.getClass();
                inspectorInfo.properties.set(function1, MoveFromCurrentPositionController.OFFSET);
                return Unit.INSTANCE;
            }
        }));
    }

    /* renamed from: offset-VpY3zN4, reason: not valid java name */
    public static final Modifier m115offsetVpY3zN4(Modifier modifier, final float f, final float f2) {
        return modifier.then(new OffsetElement(f, f2, true, new Function1() { // from class: androidx.compose.foundation.layout.OffsetKt.offset.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                InspectorInfo inspectorInfo = (InspectorInfo) obj;
                inspectorInfo.getClass();
                Dp dpM837boximpl = Dp.m837boximpl(f);
                ValueElementSequence valueElementSequence = inspectorInfo.properties;
                valueElementSequence.set(dpM837boximpl, "x");
                valueElementSequence.set(Dp.m837boximpl(f2), "y");
                return Unit.INSTANCE;
            }
        }, null));
    }

    /* renamed from: offset-VpY3zN4$default, reason: not valid java name */
    public static Modifier m116offsetVpY3zN4$default(Modifier modifier, float f, float f2, int i) {
        if ((i & 1) != 0) {
            f = 0;
            Dp.Companion companion = Dp.Companion;
        }
        if ((i & 2) != 0) {
            f2 = 0;
            Dp.Companion companion2 = Dp.Companion;
        }
        return m115offsetVpY3zN4(modifier, f, f2);
    }
}
