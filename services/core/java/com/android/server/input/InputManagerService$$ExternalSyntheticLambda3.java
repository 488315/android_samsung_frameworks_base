package com.android.server.input;

import com.android.server.input.InputManagerService;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class InputManagerService$$ExternalSyntheticLambda3 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        InputManagerService.AdditionalDisplayInputProperties additionalDisplayInputProperties = (InputManagerService.AdditionalDisplayInputProperties) obj;
        additionalDisplayInputProperties.mousePointerAccelerationEnabled = true;
        additionalDisplayInputProperties.pointerIconVisible = true;
    }
}
