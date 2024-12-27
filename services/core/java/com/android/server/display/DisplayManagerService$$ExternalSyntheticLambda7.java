package com.android.server.display;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda7 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayManagerService f$0;
    public final /* synthetic */ int[] f$1;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda7(DisplayManagerService displayManagerService, int[] iArr, int i) {
        this.$r8$classId = i;
        this.f$0 = displayManagerService;
        this.f$1 = iArr;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DisplayManagerService displayManagerService = this.f$0;
                int[] iArr = this.f$1;
                LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                displayManagerService.getClass();
                logicalDisplay.setUserDisabledHdrTypes(iArr);
                displayManagerService.handleLogicalDisplayChangedLocked(logicalDisplay);
                break;
            default:
                DisplayManagerService displayManagerService2 = this.f$0;
                int[] iArr2 = this.f$1;
                LogicalDisplay logicalDisplay2 = (LogicalDisplay) obj;
                displayManagerService2.getClass();
                logicalDisplay2.setUserDisabledHdrTypes(iArr2);
                displayManagerService2.handleLogicalDisplayChangedLocked(logicalDisplay2);
                break;
        }
    }
}
