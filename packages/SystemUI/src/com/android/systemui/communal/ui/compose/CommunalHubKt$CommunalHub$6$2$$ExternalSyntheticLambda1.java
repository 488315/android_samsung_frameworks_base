package com.android.systemui.communal.ui.compose;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.unit.IntSize;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$CommunalHub$6$2$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MutableState f$0;

    public /* synthetic */ CommunalHubKt$CommunalHub$6$2$$ExternalSyntheticLambda1(MutableState mutableState, int i) {
        this.$r8$classId = i;
        this.f$0 = mutableState;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.setValue((IntSize) obj);
                break;
            case 1:
                this.f$0.setValue((LayoutCoordinates) obj);
                break;
            case 2:
                this.f$0.setValue(Offset.m393boximpl(((Offset) obj).packedValue));
                break;
            default:
                this.f$0.setValue((LayoutCoordinates) obj);
                break;
        }
        return Unit.INSTANCE;
    }
}
