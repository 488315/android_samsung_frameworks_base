package com.android.systemui.unfold.updates.hinge;

public final class EmptyHingeAngleProvider implements HingeAngleProvider {
    public static final EmptyHingeAngleProvider INSTANCE = new EmptyHingeAngleProvider();

    private EmptyHingeAngleProvider() {
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final /* bridge */ /* synthetic */ void addCallback(Object obj) {
    }

    @Override // com.android.systemui.unfold.updates.hinge.HingeAngleProvider
    public final void start() {
    }

    @Override // com.android.systemui.unfold.updates.hinge.HingeAngleProvider
    public final void stop() {
    }
}
