package com.android.systemui.qs.composefragment;

import android.os.Trace;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.layout.LayoutCoordinatesKt;
import androidx.compose.ui.semantics.CustomAccessibilityAction;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel;
import java.util.Collections;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda5 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda5(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Object obj2 = this.f$0;
        Object obj3 = this.f$1;
        switch (this.$r8$classId) {
            case 0:
                TransitionState.Transition transition = (TransitionState.Transition) obj;
                int i = QSFragmentCompose.$r8$clinit;
                QSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1 qSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1 = (QSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1) obj2;
                int i2 = qSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1.value;
                qSFragmentCompose$CollapsableQuickSettingsSTL$nextCookie$1$1.value = i2 + 1;
                ((Map) obj3).put(transition, Integer.valueOf(i2));
                SceneKeys.INSTANCE.getClass();
                Trace.beginAsyncSection("CollapsableQuickSettingsSTL " + SceneKeys.getDebugName(transition), i2);
                break;
            case 1:
                LayoutCoordinates layoutCoordinates = (LayoutCoordinates) obj;
                int i3 = QSFragmentCompose.$r8$clinit;
                long m854roundk4lQ0M = IntOffsetKt.m854roundk4lQ0M(LayoutCoordinatesKt.positionInRoot(layoutCoordinates));
                IntOffset.Companion companion = IntOffset.Companion;
                int i4 = (int) (m854roundk4lQ0M >> 32);
                int i5 = (int) (m854roundk4lQ0M & 4294967295L);
                QSFragmentCompose qSFragmentCompose = (QSFragmentCompose) obj2;
                qSFragmentCompose.qqsPositionOnRoot.set(i4, i5, ((int) (layoutCoordinates.mo610getSizeYbymL2g() >> 32)) + i4, ((int) (layoutCoordinates.mo610getSizeYbymL2g() & 4294967295L)) + i5);
                if (((Number) ((MutableState) obj3).getValue()).floatValue() == 1.0f) {
                    QSFragmentComposeViewModel qSFragmentComposeViewModel = qSFragmentCompose.viewModel;
                    if (qSFragmentComposeViewModel == null) {
                        qSFragmentComposeViewModel = null;
                    }
                    ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.qqsHeight$delegate).setValue(Integer.valueOf((int) (layoutCoordinates.mo610getSizeYbymL2g() & 4294967295L)));
                }
                break;
            default:
                int i6 = QSFragmentCompose.$r8$clinit;
                SemanticsPropertiesKt.setCustomActions((SemanticsPropertyReceiver) obj, Collections.singletonList(new CustomAccessibilityAction((String) obj2, new QSFragmentCompose$$ExternalSyntheticLambda0((Runnable) obj3, 5))));
                break;
        }
        return Unit.INSTANCE;
    }
}
