package com.android.systemui.media.mediaoutput.compose.material;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel;
import com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel;
import kotlin.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ContainerBoxKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ MediaSessionViewModel f$1;
    public final /* synthetic */ MediaDeviceViewModel f$2;
    public final /* synthetic */ LabsViewModel f$3;
    public final /* synthetic */ Function f$4;

    public /* synthetic */ ContainerBoxKt$$ExternalSyntheticLambda1(Function1 function1, MediaSessionViewModel mediaSessionViewModel, MediaDeviceViewModel mediaDeviceViewModel, LabsViewModel labsViewModel, boolean z, int i) {
        this.f$4 = function1;
        this.f$1 = mediaSessionViewModel;
        this.f$2 = mediaDeviceViewModel;
        this.f$3 = labsViewModel;
        this.f$0 = z;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(24583);
                ComposableLambdaImpl composableLambdaImpl = (ComposableLambdaImpl) this.f$4;
                ContainerBoxKt.ContainerBox(this.f$0, this.f$1, this.f$2, this.f$3, composableLambdaImpl, (Composer) obj, iUpdateChangedFlags);
                break;
            default:
                ((Integer) obj2).getClass();
                int iUpdateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(24577);
                LabsViewModel labsViewModel = this.f$3;
                boolean z = this.f$0;
                ContainerBoxKt.ActionButton((Function1) this.f$4, this.f$1, this.f$2, labsViewModel, z, (Composer) obj, iUpdateChangedFlags2);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ ContainerBoxKt$$ExternalSyntheticLambda1(boolean z, MediaSessionViewModel mediaSessionViewModel, MediaDeviceViewModel mediaDeviceViewModel, LabsViewModel labsViewModel, ComposableLambdaImpl composableLambdaImpl, int i) {
        this.f$0 = z;
        this.f$1 = mediaSessionViewModel;
        this.f$2 = mediaDeviceViewModel;
        this.f$3 = labsViewModel;
        this.f$4 = composableLambdaImpl;
    }
}
