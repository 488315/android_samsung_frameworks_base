package com.android.server.wm;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayAreaPolicy$DefaultProvider$$ExternalSyntheticLambda0
        implements DisplayAreaPolicyBuilder.NewDisplayAreaSupplier {
    @Override // com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier
    public final DisplayArea create(
            WindowManagerService windowManagerService, DisplayArea.Type type, String str, int i) {
        return new DisplayArea.Dimmable(windowManagerService, type, str, i);
    }
}
