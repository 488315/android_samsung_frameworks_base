package com.android.server.input;

import com.android.server.input.InputManagerService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class InputManagerService$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ InputManagerService$$ExternalSyntheticLambda1(int i, boolean z) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        InputManagerService.AdditionalDisplayInputProperties additionalDisplayInputProperties = (InputManagerService.AdditionalDisplayInputProperties) obj;
        switch (i) {
            case 0:
                boolean z2 = InputManagerService.DEBUG;
                additionalDisplayInputProperties.pointerIconVisible = z;
                break;
            default:
                boolean z3 = InputManagerService.DEBUG;
                additionalDisplayInputProperties.mousePointerAccelerationEnabled = z;
                break;
        }
    }
}
