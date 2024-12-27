package com.android.server.wm;


public final /* synthetic */ class DisplayAreaPolicy$DefaultProvider$$ExternalSyntheticLambda0
        implements DisplayAreaPolicyBuilder.NewDisplayAreaSupplier {
    @Override // com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier
    public final DisplayArea create(
            WindowManagerService windowManagerService, DisplayArea.Type type, String str, int i) {
        return new DisplayArea.Dimmable(windowManagerService, type, str, i);
    }
}
