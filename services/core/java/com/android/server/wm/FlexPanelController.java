package com.android.server.wm;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.packagefeature.PackageFeatureUserChangePersister;
import com.samsung.android.server.packagefeature.util.FoldablePackageSpecialManagementList;

public final class FlexPanelController extends PackagesChange {
    public final FlexPanelController$$ExternalSyntheticLambda1 mDumpInterface;
    public final FoldablePackageSpecialManagementList mFlexModeAppList;
    public final FoldablePackageSpecialManagementList mFlexPanelEnabledList;
    public final PackageFeatureUserChange mUserChange;

    public FlexPanelController(ActivityTaskManagerService activityTaskManagerService) {
        super(activityTaskManagerService);
        this.mFlexModeAppList =
                new FoldablePackageSpecialManagementList(PackageFeature.FLEX_MODE_APP);
        this.mFlexPanelEnabledList =
                new FoldablePackageSpecialManagementList(PackageFeature.FLEX_PANEL_DEFAULT);
        PackageFeatureUserChange packageFeatureUserChange =
                new PackageFeatureUserChange(
                        16,
                        PackageFeatureUserChangePersister.MULTI_DISPLAY_DIRECTORY,
                        "SupportsFlexPanelPackageMap",
                        new PackageFeatureUserChange
                                .DumpInterface() { // from class:
                                                   // com.android.server.wm.FlexPanelController$$ExternalSyntheticLambda1
                            @Override // com.samsung.android.server.packagefeature.PackageFeatureUserChange.DumpInterface
                            public final String valueToString(String str, int i, Object obj) {
                                FlexPanelController flexPanelController = FlexPanelController.this;
                                flexPanelController.getClass();
                                return ((Boolean) obj)
                                        + ", get=0x"
                                        + Integer.toHexString(
                                                flexPanelController.getSupportsFlexPanel(i, str));
                            }
                        });
        this.mUserChange = packageFeatureUserChange;
        this.mUserChanges = new PackageFeatureUserChange[] {packageFeatureUserChange};
    }

    public static boolean isFlexPanelTopEnabled(WindowState windowState) {
        Task rootTask;
        DisplayContent displayContent = windowState.getDisplayContent();
        return displayContent != null
                && displayContent.getDefaultTaskDisplayArea().isSplitScreenModeActivated()
                && displayContent.getWindowConfiguration().isFlexPanelEnabled()
                && (rootTask =
                                displayContent.getRootTask(
                                        new FlexPanelController$$ExternalSyntheticLambda0()))
                        != null
                && rootTask.isFullscreenRootForStageTask();
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00b5, code lost:

       if (r1.getBoolean("com.samsung.android.unsupports_flexpanel", false) != false) goto L90;
    */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0141, code lost:

       if (android.content.pm.ActivityInfo.isPreserveOrientationMode(r3.resizeMode) == false) goto L56;
    */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0147, code lost:

       if (r3.supportsPictureInPicture() != false) goto L56;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getSupportsFlexPanel(int r7, java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 418
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.FlexPanelController.getSupportsFlexPanel(int,"
                    + " java.lang.String):int");
    }
}
