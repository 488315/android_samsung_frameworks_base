package com.android.server.wm;


public final /* synthetic */
class DisplayAreaPolicyBuilder$Feature$Builder$$ExternalSyntheticLambda0
        implements DisplayAreaPolicyBuilder.NewDisplayAreaSupplier {
    @Override // com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier
    public final DisplayArea create(
            WindowManagerService windowManagerService, DisplayArea.Type type, String str, int i) {
        return new DisplayArea(windowManagerService, type, str, i);
    }
}
