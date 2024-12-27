package com.android.server.display;

import android.util.IntArray;
import android.util.SparseArray;
import android.view.SurfaceControl;

import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda2
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda2(
            Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DisplayManagerService displayManagerService = (DisplayManagerService) this.f$0;
                SparseArray sparseArray = (SparseArray) this.f$1;
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) this.f$2;
                LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                displayManagerService.getClass();
                DisplayDevice displayDevice = logicalDisplay.mPrimaryDisplayDevice;
                int i = logicalDisplay.mDisplayId;
                SurfaceControl.Transaction transaction2 =
                        (SurfaceControl.Transaction) sparseArray.get(i, transaction);
                if (displayDevice != null) {
                    if (displayManagerService.mEnabledDexDisplay != null) {
                        DisplayDeviceInfo displayDeviceInfoLocked =
                                displayDevice.getDisplayDeviceInfoLocked();
                        displayManagerService.mDisplayDeviceRepo.getClass();
                        if (DisplayDeviceRepository.isExternalDisplayDeviceForDexLocked(
                                        displayDeviceInfoLocked)
                                && displayDevice.mCurrentLayerStack == i) {
                            displayManagerService.configureDisplayLocked(
                                    transaction, displayDevice);
                            displayDevice.performTraversalLocked(transaction);
                            break;
                        }
                    }
                    displayManagerService.configureDisplayLocked(transaction2, displayDevice);
                    displayDevice.performTraversalLocked(transaction2);
                    break;
                }
                break;
            default:
                DisplayManagerService.LocalService localService =
                        (DisplayManagerService.LocalService) this.f$0;
                Set set = (Set) this.f$1;
                IntArray intArray = (IntArray) this.f$2;
                int displayGroupIdFromDisplayIdLocked =
                        DisplayManagerService.this.mLogicalDisplayMapper
                                .getDisplayGroupIdFromDisplayIdLocked(
                                        ((LogicalDisplay) obj).mDisplayId);
                if (!set.contains(Integer.valueOf(displayGroupIdFromDisplayIdLocked))) {
                    set.add(Integer.valueOf(displayGroupIdFromDisplayIdLocked));
                    intArray.add(displayGroupIdFromDisplayIdLocked);
                    break;
                }
                break;
        }
    }
}
