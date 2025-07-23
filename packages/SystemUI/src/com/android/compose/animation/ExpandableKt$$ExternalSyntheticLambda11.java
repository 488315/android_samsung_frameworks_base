package com.android.compose.animation;

import androidx.compose.runtime.MutableState;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.ContentDrawScope;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.node.LayoutNodeDrawScope;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ExpandableKt$$ExternalSyntheticLambda11 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ExpandableKt$$ExternalSyntheticLambda11(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) obj;
                ((MutableState) this.f$0).setValue(Size.m413boximpl(LayoutCoordinatesKt.findRootCoordinates(layoutCoordinates).localBoundingBoxOf(layoutCoordinates, false).m408getSizeNHjbRc()));
                break;
            default:
                ((LayoutNodeDrawScope) ((ContentDrawScope) this.f$0)).drawContent();
                break;
        }
        return Unit.INSTANCE;
    }
}
