package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.MultiSplitLayoutInfo;
import com.android.wm.shell.common.split.SplitScreenUtils;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public abstract class NaturalSwitchingChanger {
    public NaturalSwitchingLayout$$ExternalSyntheticLambda2 mHideLayoutCallback;
    public boolean mIsMainDisplay;
    public SplitScreenController mSplitController;
    public SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTask;
    public int mToPosition = 0;
    public int mCurrentSplitMode = 0;
    public int mRequestedCreateMode = -1;
    public final Rect mDropBounds = new Rect();
    public boolean mNeedToReparentCell = false;
    public Runnable mRunAfterTransitionStarted = null;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

    public class FreeformToFreeformChanger extends NaturalSwitchingChanger {
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Layout changed");
            }
            Rect bounds = this.mTask.configuration.windowConfiguration.getBounds();
            Rect rect = this.mDropBounds;
            bounds.offsetTo(((rect.width() - bounds.width()) / 2) + rect.left, this.mDropBounds.top);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setBounds(this.mTask.token, bounds);
            this.mSyncQueue.queue(windowContainerTransaction);
            this.mSyncQueue.runInSync(new NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0(this, 1));
        }
    }

    public class FreeformToSplitChanger extends NaturalSwitchingChanger {
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            int i;
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Freeform -> Split");
                CoreSaLogger.logForAdvanced("1000", "From Popup view_HandleGesture");
                if (CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT && this.mIsMainDisplay && ((i = this.mCurrentSplitMode) == 2 || i == 1)) {
                    CoreSaLogger.logForAdvanced("1021", "From Popup view_HandleGesture");
                }
            }
            this.mSplitController.onFreeformToSplitRequested(this.mTask, false, this.mToPosition, this.mNeedToReparentCell, this.mDropBounds, true);
            this.mRunAfterTransitionStarted = new NaturalSwitchingChanger$PipToSplitChanger$$ExternalSyntheticLambda0(this, 1);
        }
    }

    public class FullToFreeformChanger extends NaturalSwitchingChanger {
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Fullscreen -> Freeform");
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setWindowingMode(this.mTask.token, 5);
            windowContainerTransaction.setChangeTransitMode(this.mTask.token, 4, "ns_full_to_freeform");
            windowContainerTransaction.setBounds(this.mTask.token, this.mDropBounds);
            windowContainerTransaction.setChangeTransitStartBounds(this.mTask.token, this.mDropBounds);
            this.mSyncQueue.queue(windowContainerTransaction);
            this.mSyncQueue.runInSync(new NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0(this, 2));
        }
    }

    public class PipToPipChanger extends NaturalSwitchingChanger {
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Layout changed");
            }
            Rect bounds = this.mTask.configuration.windowConfiguration.getBounds();
            Rect rect = this.mDropBounds;
            bounds.offsetTo(((rect.width() - bounds.width()) / 2) + rect.left, this.mDropBounds.top);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setBounds(this.mTask.token, bounds);
            this.mSyncQueue.queue(windowContainerTransaction);
            this.mSyncQueue.runInSync(new NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0(this, 0));
        }
    }

    public class PipToSplitChanger extends NaturalSwitchingChanger {
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "PIP to Split");
            }
            this.mSplitController.onPipToSplitRequested(this.mTask, false, this.mToPosition, this.mNeedToReparentCell, this.mDropBounds, true);
            this.mRunAfterTransitionStarted = new NaturalSwitchingChanger$PipToSplitChanger$$ExternalSyntheticLambda0(this, 0);
        }
    }

    public class SplitToFreeformChanger extends NaturalSwitchingChanger {
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Split -> Freeform");
                CoreSaLogger.logForAdvanced("2004", "From Split view_HandleGesture");
            }
            this.mSplitController.moveSplitToFreeform(this.mTask.token, this.mDropBounds, true);
            this.mRunAfterTransitionStarted = new NaturalSwitchingChanger$PipToSplitChanger$$ExternalSyntheticLambda0(this, 2);
        }
    }

    public class SplitToSplitChanger extends NaturalSwitchingChanger {
        public static boolean isOneDirectionPosition(int i) {
            return i == 8 || i == 16 || i == 32 || i == 64;
        }

        /* JADX WARN: Code restructure failed: missing block: B:248:0x02ed, code lost:
        
            if (r9 != 5) goto L250;
         */
        /* JADX WARN: Code restructure failed: missing block: B:249:0x02ef, code lost:
        
            r8.cellStagePosition = r13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:251:0x02f4, code lost:
        
            if (r8.splitDivision != 0) goto L258;
         */
        /* JADX WARN: Code restructure failed: missing block: B:253:0x02f8, code lost:
        
            if ((r13 & 64) == 0) goto L255;
         */
        /* JADX WARN: Code restructure failed: missing block: B:254:0x02fa, code lost:
        
            r13 = (r13 & (-65)) | 16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:256:0x0301, code lost:
        
            if ((r13 & 16) == 0) goto L264;
         */
        /* JADX WARN: Code restructure failed: missing block: B:257:0x0303, code lost:
        
            r13 = (r13 & (-17)) | 64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:259:0x030a, code lost:
        
            if ((r13 & 32) == 0) goto L261;
         */
        /* JADX WARN: Code restructure failed: missing block: B:260:0x030c, code lost:
        
            r13 = (r13 & (-33)) | 8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:262:0x0313, code lost:
        
            if ((r13 & 8) == 0) goto L264;
         */
        /* JADX WARN: Code restructure failed: missing block: B:263:0x0315, code lost:
        
            r13 = (r13 & (-9)) | 32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:264:0x0319, code lost:
        
            r8.cellStagePosition = r13;
         */
        /* JADX WARN: Removed duplicated region for block: B:112:0x018d  */
        /* JADX WARN: Removed duplicated region for block: B:113:0x018f  */
        /* JADX WARN: Removed duplicated region for block: B:116:0x0194  */
        /* JADX WARN: Removed duplicated region for block: B:117:0x01a0  */
        /* JADX WARN: Removed duplicated region for block: B:267:0x0324  */
        /* JADX WARN: Removed duplicated region for block: B:268:0x0326  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00a3  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00ac  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x00f3  */
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void changeLayout() {
            int i;
            int i2;
            if (this.mCurrentSplitMode == 0 || this.mToPosition == 0 || this.mTask.configuration.windowConfiguration.getStagePosition() == this.mToPosition) {
                Slog.w("NaturalSwitchingChanger", "changeLayout: failed");
                this.mHideLayoutCallback.accept(Boolean.TRUE);
                return;
            }
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Layout changed");
            }
            if (this.mTask.configuration.windowConfiguration.getStagePosition() == this.mToPosition) {
                this.mHideLayoutCallback.accept(Boolean.TRUE);
                return;
            }
            boolean z = CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT;
            if (!z || !this.mSplitController.isMultiSplitScreenVisible()) {
                int splitDivision = this.mSplitController.getSplitDivision();
                int i3 = this.mToPosition;
                if (splitDivision == (((i3 & 8) == 0 && (i3 & 32) == 0) ? 1 : 0)) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    SplitScreenController splitScreenController = this.mSplitController;
                    splitScreenController.setSideStagePosition(windowContainerTransaction, SplitScreenUtils.reverseSplitPosition(splitScreenController.getSideStagePosition()));
                    this.mSplitController.invertDividerPosition(windowContainerTransaction);
                    this.mSyncQueue.queue(windowContainerTransaction);
                    this.mSyncQueue.runInSync(new NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0(this, 3));
                    return;
                }
                if (z) {
                    int i4 = ((i3 & 8) == 0 && (i3 & 32) == 0) ? 1 : 0;
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
                    int stageType = this.mTask.configuration.windowConfiguration.getStageType();
                    boolean z2 = true;
                    if (stageType == 1 || stageType != 2) {
                        i = 0;
                        int i5 = this.mToPosition;
                        if (i5 == 16 || i5 == 8) {
                            z2 = true;
                            multiSplitLayoutInfo.sideStagePosition = 1;
                        } else {
                            multiSplitLayoutInfo.sideStagePosition = 0;
                            z2 = true;
                        }
                    } else {
                        int i6 = this.mToPosition;
                        if (i6 == 16 || i6 == 8) {
                            i = 0;
                            multiSplitLayoutInfo.sideStagePosition = 0;
                            z2 = true;
                        } else {
                            multiSplitLayoutInfo.sideStagePosition = 1;
                            i = 0;
                        }
                    }
                    multiSplitLayoutInfo.splitDivision = i4;
                    multiSplitLayoutInfo.cellStagePosition = i;
                    this.mSplitController.updateMultiSplitLayout(multiSplitLayoutInfo, z2, windowContainerTransaction2);
                    this.mSyncQueue.queue(windowContainerTransaction2);
                    this.mSyncQueue.runInSync(new NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0(this, 3));
                    return;
                }
                return;
            }
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            MultiSplitLayoutInfo multiSplitLayoutInfo2 = new MultiSplitLayoutInfo();
            int stageType2 = this.mTask.configuration.windowConfiguration.getStageType();
            int i7 = -1;
            int i8 = stageType2 != 1 ? stageType2 != 2 ? stageType2 != 4 ? -1 : 5 : 1 : 0;
            int i9 = this.mToPosition;
            multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition();
            multiSplitLayoutInfo2.splitDivision = this.mSplitController.getSplitDivision();
            multiSplitLayoutInfo2.cellStagePosition = this.mSplitController.getCellStageWindowConfigPosition();
            if (this.mNeedToReparentCell) {
                int i10 = this.mRequestedCreateMode;
                if (i10 == -1 || i10 == StageCoordinator.convertCreateMode(multiSplitLayoutInfo2)) {
                    multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition();
                    multiSplitLayoutInfo2.splitDivision = this.mSplitController.getSplitDivision();
                    multiSplitLayoutInfo2.cellStagePosition = this.mSplitController.getCellStageWindowConfigPosition();
                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitController.isParallelMultiSplit()) {
                        int i11 = i9 == this.mSplitController.getMainStagePositionExt() ? 0 : i9 == this.mSplitController.getCellStageWindowConfigPosition() ? 5 : 1;
                        int cellHostStageType = this.mSplitController.getCellHostStageType();
                        if (i8 == cellHostStageType || i11 == cellHostStageType) {
                            this.mSplitController.swapStageTasks(i8, i11, windowContainerTransaction3);
                        } else {
                            this.mSplitController.shiftStageTasks(windowContainerTransaction3, i8, i11, (i8 != 5 ? i8 != 0 : i11 != 0) ? 0 : 1);
                        }
                    } else {
                        this.mSplitController.swapStageTasks(5, this.mSplitController.getCellHostStageType() == 0 ? 1 : 0, windowContainerTransaction3);
                    }
                } else {
                    int i12 = this.mRequestedCreateMode;
                    if (i12 == 2) {
                        i7 = 0;
                        if (i7 != this.mSplitController.getSplitDivision()) {
                            multiSplitLayoutInfo2.splitDivision = i7;
                            if (isOneDirectionPosition(i9)) {
                                if (this.mSplitController.getCellHostStageType() == 0) {
                                    multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 1 : 0;
                                } else {
                                    multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 0 : 1;
                                }
                                int cellStageWindowConfigPosition = this.mSplitController.getCellStageWindowConfigPosition();
                                if (i7 == 0) {
                                    if (i9 == 8 || i9 == 32) {
                                        int i13 = cellStageWindowConfigPosition & (-41);
                                        cellStageWindowConfigPosition = i9 == 8 ? i13 | 32 : i13 | 8;
                                    }
                                } else if (i9 == 16 || i9 == 64) {
                                    int i14 = cellStageWindowConfigPosition & (-81);
                                    cellStageWindowConfigPosition = i9 == 16 ? i14 | 64 : i14 | 16;
                                }
                                multiSplitLayoutInfo2.cellStagePosition = cellStageWindowConfigPosition;
                            }
                        } else {
                            multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition();
                            multiSplitLayoutInfo2.splitDivision = i7;
                            multiSplitLayoutInfo2.cellStagePosition = i9;
                        }
                        SplitScreenController splitScreenController2 = this.mSplitController;
                        splitScreenController2.swapStageTasks(5, splitScreenController2.getCellHostStageType(), windowContainerTransaction3);
                    } else if (i12 == 3) {
                        i7 = 1;
                        if (i7 != this.mSplitController.getSplitDivision()) {
                        }
                        SplitScreenController splitScreenController22 = this.mSplitController;
                        splitScreenController22.swapStageTasks(5, splitScreenController22.getCellHostStageType(), windowContainerTransaction3);
                    } else {
                        if (i12 != 4) {
                            if (i12 == 5) {
                            }
                        }
                        if (i7 != this.mSplitController.getSplitDivision()) {
                        }
                        SplitScreenController splitScreenController222 = this.mSplitController;
                        splitScreenController222.swapStageTasks(5, splitScreenController222.getCellHostStageType(), windowContainerTransaction3);
                    }
                }
            } else {
                int i15 = this.mRequestedCreateMode;
                if (i15 == -1 || i15 == StageCoordinator.convertCreateMode(multiSplitLayoutInfo2)) {
                    multiSplitLayoutInfo2.splitDivision = this.mSplitController.getSplitDivision();
                    if (isOneDirectionPosition(i9)) {
                        if (i8 == 0) {
                            multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 1 : 0;
                        } else if (i8 == 1) {
                            multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 0 : 1;
                        }
                    } else if ((r4 = this.mRequestedCreateMode) != 2) {
                        multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition() != 0 ? 1 : 0;
                    } else {
                        multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition() != 0 ? 1 : 0;
                    }
                } else {
                    int i16 = this.mRequestedCreateMode;
                    if (i16 == 2) {
                        i7 = 0;
                        multiSplitLayoutInfo2.splitDivision = i7;
                        if (i8 == 5) {
                            multiSplitLayoutInfo2.cellStagePosition = i9;
                            multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition();
                        } else if (isOneDirectionPosition(i9)) {
                            int i17 = multiSplitLayoutInfo2.sideStagePosition;
                            if (i8 == 0) {
                                multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 1 : 0;
                            } else if (i8 == 1) {
                                multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 0 : 1;
                            }
                            if (multiSplitLayoutInfo2.splitDivision != this.mSplitController.getSplitDivision()) {
                                int cellStageWindowConfigPosition2 = this.mSplitController.getCellStageWindowConfigPosition();
                                int i18 = this.mRequestedCreateMode;
                                if (i18 == 3 || i18 == 5) {
                                    i2 = cellStageWindowConfigPosition2 & (-81);
                                    if (i9 == 16) {
                                        i2 |= 64;
                                    } else if (i9 == 64) {
                                        i2 |= 16;
                                    }
                                } else {
                                    i2 = cellStageWindowConfigPosition2 & (-41);
                                    if (i9 == 8) {
                                        i2 |= 32;
                                    } else if (i9 == 32) {
                                        i2 |= 8;
                                    }
                                }
                                multiSplitLayoutInfo2.cellStagePosition = i2;
                            } else if (i17 != multiSplitLayoutInfo2.sideStagePosition) {
                                int cellStageWindowConfigPosition3 = this.mSplitController.getCellStageWindowConfigPosition() & (~i9);
                                if (i9 == 32) {
                                    cellStageWindowConfigPosition3 |= 8;
                                } else if (i9 == 8) {
                                    cellStageWindowConfigPosition3 |= 32;
                                } else if (i9 == 16) {
                                    cellStageWindowConfigPosition3 |= 64;
                                } else if (i9 == 64) {
                                    cellStageWindowConfigPosition3 |= 16;
                                }
                                multiSplitLayoutInfo2.cellStagePosition = cellStageWindowConfigPosition3;
                            } else {
                                multiSplitLayoutInfo2.cellStagePosition = this.mSplitController.getCellStageWindowConfigPosition();
                            }
                        } else if (i8 == 0) {
                            if (this.mSplitController.getCellHostStageType() == 0) {
                                int i19 = i9 & 8;
                                multiSplitLayoutInfo2.sideStagePosition = (i19 == 0 && (i9 & 16) == 0) ? 0 : 1;
                                if (multiSplitLayoutInfo2.splitDivision == 0) {
                                    if ((i9 & 64) != 0) {
                                        i9 = (i9 & (-65)) | 16;
                                    } else if ((i9 & 16) != 0) {
                                        i9 = (i9 & (-17)) | 64;
                                    }
                                } else if ((i9 & 32) != 0) {
                                    i9 = (i9 & (-33)) | 8;
                                } else if (i19 != 0) {
                                    i9 = (i9 & (-9)) | 32;
                                }
                                multiSplitLayoutInfo2.cellStagePosition = i9;
                            }
                        } else if (i8 == 1 && this.mSplitController.getCellHostStageType() == 1) {
                            int i20 = i9 & 8;
                            multiSplitLayoutInfo2.sideStagePosition = (i20 == 0 && (i9 & 16) == 0) ? 1 : 0;
                            if (multiSplitLayoutInfo2.splitDivision == 0) {
                                if ((i9 & 64) != 0) {
                                    i9 = (i9 & (-65)) | 16;
                                } else if ((i9 & 16) != 0) {
                                    i9 = (i9 & (-17)) | 64;
                                }
                            } else if ((i9 & 32) != 0) {
                                i9 = (i9 & (-33)) | 8;
                            } else if (i20 != 0) {
                                i9 = (i9 & (-9)) | 32;
                            }
                            multiSplitLayoutInfo2.cellStagePosition = i9;
                        }
                    } else if (i16 == 3) {
                        i7 = 1;
                        multiSplitLayoutInfo2.splitDivision = i7;
                        if (i8 == 5) {
                        }
                    } else {
                        if (i16 != 4) {
                            if (i16 == 5) {
                            }
                        }
                        multiSplitLayoutInfo2.splitDivision = i7;
                        if (i8 == 5) {
                        }
                    }
                }
            }
            this.mSplitController.updateMultiSplitLayout(multiSplitLayoutInfo2, true, windowContainerTransaction3);
            this.mSyncQueue.queue(windowContainerTransaction3);
            this.mSyncQueue.runInSync(new NaturalSwitchingChanger$PipToPipChanger$$ExternalSyntheticLambda0(this, 3));
        }
    }

    public abstract void changeLayout();

    public final String toString() {
        return getClass().getSimpleName();
    }
}
