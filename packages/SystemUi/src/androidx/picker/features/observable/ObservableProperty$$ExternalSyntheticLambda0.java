package androidx.picker.features.observable;

import kotlin.Function;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class ObservableProperty$$ExternalSyntheticLambda0 implements DisposableHandle {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ObservableProperty f$0;
    public final /* synthetic */ Function f$1;

    public /* synthetic */ ObservableProperty$$ExternalSyntheticLambda0(ObservableProperty observableProperty, Function function, int i) {
        this.$r8$classId = i;
        this.f$0 = observableProperty;
        this.f$1 = function;
    }

    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        int i = this.$r8$classId;
        ObservableProperty observableProperty = this.f$0;
        Function function = this.f$1;
        switch (i) {
            case 0:
                ObservableProperty.m336registerBeforeChangeUpdateListener$lambda1(observableProperty, (Function2) function);
                break;
            case 1:
                ObservableProperty.m335registerAfterChangeUpdateListener$lambda2(observableProperty, (Function2) function);
                break;
            default:
                ObservableProperty.m334bind$lambda0(observableProperty, (Function1) function);
                break;
        }
    }
}
