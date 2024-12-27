package com.android.server.input;

import java.util.function.Consumer;

public final /* synthetic */ class InputManagerService$$ExternalSyntheticLambda3
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        InputManagerService.AdditionalDisplayInputProperties additionalDisplayInputProperties =
                (InputManagerService.AdditionalDisplayInputProperties) obj;
        additionalDisplayInputProperties.mousePointerAccelerationEnabled = true;
        additionalDisplayInputProperties.pointerIconVisible = true;
    }
}
