package com.android.p038wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.common.SyncTransactionQueue;
import com.android.p038wm.shell.common.split.MultiSplitLayoutInfo;
import com.android.p038wm.shell.common.split.SplitScreenUtils;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.splitscreen.StageCoordinator;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NaturalSwitchingChanger {
    public Consumer mHideLayoutCallback;
    public boolean mIsMainDisplay;
    public SplitScreenController mSplitController;
    public SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTask;
    public int mToPosition = 0;
    public int mCurrentSplitMode = 0;
    public int mRequestedCreateMode = -1;
    public final Rect mDropBounds = new Rect();
    public boolean mNeedToReparentCell = false;
    public RunnableC4009x244370c2 mRunAfterTransitionStarted = null;
    public final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FreeformToFreeformChanger extends NaturalSwitchingChanger {
        @Override // com.android.p038wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Layout changed");
            }
            Rect bounds = this.mTask.configuration.windowConfiguration.getBounds();
            Rect rect = this.mDropBounds;
            bounds.offsetTo(((rect.width() - bounds.width()) / 2) + rect.left, rect.top);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setBounds(this.mTask.token, bounds);
            this.mSyncQueue.queue(windowContainerTransaction);
            this.mSyncQueue.runInSync(new C4008xe41b4ca5(this, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FreeformToSplitChanger extends NaturalSwitchingChanger {
        @Override // com.android.p038wm.shell.naturalswitching.NaturalSwitchingChanger
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
            this.mRunAfterTransitionStarted = new RunnableC4009x244370c2(this, 1);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FullToFreeformChanger extends NaturalSwitchingChanger {
        @Override // com.android.p038wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Fullscreen -> Freeform");
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setWindowingMode(this.mTask.token, 5);
            windowContainerTransaction.setChangeTransitMode(this.mTask.token, 4, "ns_full_to_freeform");
            WindowContainerToken windowContainerToken = this.mTask.token;
            Rect rect = this.mDropBounds;
            windowContainerTransaction.setBounds(windowContainerToken, rect);
            windowContainerTransaction.setChangeTransitStartBounds(this.mTask.token, rect);
            this.mSyncQueue.queue(windowContainerTransaction);
            this.mSyncQueue.runInSync(new C4008xe41b4ca5(this, 2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipToPipChanger extends NaturalSwitchingChanger {
        @Override // com.android.p038wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Layout changed");
            }
            Rect bounds = this.mTask.configuration.windowConfiguration.getBounds();
            Rect rect = this.mDropBounds;
            bounds.offsetTo(((rect.width() - bounds.width()) / 2) + rect.left, rect.top);
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            windowContainerTransaction.setBounds(this.mTask.token, bounds);
            this.mSyncQueue.queue(windowContainerTransaction);
            this.mSyncQueue.runInSync(new C4008xe41b4ca5(this, 0));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipToSplitChanger extends NaturalSwitchingChanger {
        @Override // com.android.p038wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            boolean z = CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING;
            this.mSplitController.onPipToSplitRequested(this.mTask, false, this.mToPosition, this.mNeedToReparentCell, this.mDropBounds, true);
            this.mRunAfterTransitionStarted = new RunnableC4009x244370c2(this, 0);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SplitToFreeformChanger extends NaturalSwitchingChanger {
        @Override // com.android.p038wm.shell.naturalswitching.NaturalSwitchingChanger
        public final void changeLayout() {
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Split -> Freeform");
                CoreSaLogger.logForAdvanced("2004", "From Split view_HandleGesture");
            }
            this.mSplitController.moveSplitToFreeform(this.mTask.token, this.mDropBounds, true);
            this.mRunAfterTransitionStarted = new RunnableC4009x244370c2(this, 2);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SplitToSplitChanger extends NaturalSwitchingChanger {
        public static boolean isOneDirectionPosition(int i) {
            return i == 8 || i == 16 || i == 32 || i == 64;
        }

        public final void applyTransactionWithAnimation(WindowContainerTransaction windowContainerTransaction) {
            this.mSyncQueue.queue(windowContainerTransaction);
            this.mSyncQueue.runInSync(new C4008xe41b4ca5(this, 3));
        }

        /* JADX WARN: Code restructure failed: missing block: B:206:0x02c6, code lost:
        
            r4 = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00b1, code lost:
        
            if (r11 != 5) goto L45;
         */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00bf  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x010c  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x016a  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x0176  */
        @Override // com.android.p038wm.shell.naturalswitching.NaturalSwitchingChanger
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void changeLayout() {
            int i;
            int i2;
            int i3;
            if (!((this.mCurrentSplitMode == 0 || this.mToPosition == 0 || this.mTask.configuration.windowConfiguration.getStagePosition() == this.mToPosition) ? false : true)) {
                Slog.w("NaturalSwitchingChanger", "changeLayout: failed");
                this.mHideLayoutCallback.accept(Boolean.FALSE);
                return;
            }
            if (CoreRune.MW_NATURAL_SWITCHING_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1041", "Layout changed");
            }
            if (this.mTask.configuration.windowConfiguration.getStagePosition() == this.mToPosition) {
                this.mHideLayoutCallback.accept(Boolean.TRUE);
                return;
            }
            char c = 2;
            int i4 = -1;
            if (!CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT || !this.mSplitController.isMultiSplitScreenVisible()) {
                int splitDivision = this.mSplitController.getSplitDivision();
                int i5 = this.mToPosition;
                if (splitDivision == (((i5 & 8) == 0 && (i5 & 32) == 0) ? 1 : 0)) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    SplitScreenController splitScreenController = this.mSplitController;
                    splitScreenController.setSideStagePosition(SplitScreenUtils.reverseSplitPosition(splitScreenController.getSideStagePosition()), windowContainerTransaction);
                    this.mSplitController.invertDividerPosition(windowContainerTransaction);
                    applyTransactionWithAnimation(windowContainerTransaction);
                    return;
                }
                if (CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT) {
                    int i6 = ((i5 & 8) == 0 && (i5 & 32) == 0) ? 1 : 0;
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    MultiSplitLayoutInfo multiSplitLayoutInfo = new MultiSplitLayoutInfo();
                    int stageType = this.mTask.configuration.windowConfiguration.getStageType();
                    if (stageType == 1) {
                        c = 0;
                    } else if (stageType == 2) {
                        c = 1;
                    } else if (stageType != 4) {
                        c = 65535;
                    }
                    if (c == 1) {
                        int i7 = this.mToPosition;
                        if (i7 == 16 || i7 == 8) {
                            i = 0;
                            multiSplitLayoutInfo.sideStagePosition = 0;
                        } else {
                            multiSplitLayoutInfo.sideStagePosition = 1;
                            i = 0;
                        }
                    } else {
                        i = 0;
                        int i8 = this.mToPosition;
                        if (i8 == 16 || i8 == 8) {
                            multiSplitLayoutInfo.sideStagePosition = 1;
                        } else {
                            multiSplitLayoutInfo.sideStagePosition = 0;
                        }
                    }
                    multiSplitLayoutInfo.splitDivision = i6;
                    multiSplitLayoutInfo.cellStagePosition = i;
                    this.mSplitController.updateMultiSplitLayout(multiSplitLayoutInfo, true, windowContainerTransaction2);
                    applyTransactionWithAnimation(windowContainerTransaction2);
                    return;
                }
                return;
            }
            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
            MultiSplitLayoutInfo multiSplitLayoutInfo2 = new MultiSplitLayoutInfo();
            int stageType2 = this.mTask.configuration.windowConfiguration.getStageType();
            char c2 = stageType2 != 1 ? stageType2 != 2 ? stageType2 != 4 ? (char) 65535 : (char) 2 : (char) 1 : (char) 0;
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
                    this.mSplitController.swapStageTasks(2, this.mSplitController.getCellHostStageType() == 0 ? 1 : 0, windowContainerTransaction3);
                } else {
                    int i11 = this.mRequestedCreateMode;
                    if (i11 != 2) {
                        if (i11 != 3) {
                            if (i11 != 4) {
                            }
                        }
                        i4 = 1;
                        if (i4 == this.mSplitController.getSplitDivision()) {
                            multiSplitLayoutInfo2.splitDivision = i4;
                            if (isOneDirectionPosition(i9)) {
                                if (this.mSplitController.getCellHostStageType() == 0) {
                                    multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 1 : 0;
                                } else {
                                    multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 0 : 1;
                                }
                                int cellStageWindowConfigPosition = this.mSplitController.getCellStageWindowConfigPosition();
                                if (i4 == 0) {
                                    if (i9 == 8 || i9 == 32) {
                                        int i12 = cellStageWindowConfigPosition & (-33) & (-9);
                                        cellStageWindowConfigPosition = i9 == 8 ? i12 | 32 : i12 | 8;
                                    }
                                } else if (i9 == 16 || i9 == 64) {
                                    int i13 = cellStageWindowConfigPosition & (-17) & (-65);
                                    cellStageWindowConfigPosition = i9 == 16 ? i13 | 64 : i13 | 16;
                                }
                                multiSplitLayoutInfo2.cellStagePosition = cellStageWindowConfigPosition;
                            }
                        } else {
                            multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition();
                            multiSplitLayoutInfo2.splitDivision = i4;
                            multiSplitLayoutInfo2.cellStagePosition = i9;
                        }
                        SplitScreenController splitScreenController2 = this.mSplitController;
                        splitScreenController2.swapStageTasks(2, splitScreenController2.getCellHostStageType(), windowContainerTransaction3);
                    }
                    i4 = 0;
                    if (i4 == this.mSplitController.getSplitDivision()) {
                    }
                    SplitScreenController splitScreenController22 = this.mSplitController;
                    splitScreenController22.swapStageTasks(2, splitScreenController22.getCellHostStageType(), windowContainerTransaction3);
                }
            } else {
                int i14 = this.mRequestedCreateMode;
                if (i14 == -1 || i14 == StageCoordinator.convertCreateMode(multiSplitLayoutInfo2)) {
                    multiSplitLayoutInfo2.splitDivision = this.mSplitController.getSplitDivision();
                    if (!isOneDirectionPosition(i9)) {
                        boolean z = (r6 = this.mRequestedCreateMode) != 2 ? false : false;
                        if (!z) {
                            multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition() == 0 ? 1 : 0;
                        } else if (c2 == 2) {
                            multiSplitLayoutInfo2.cellStagePosition = i9;
                        } else {
                            if (multiSplitLayoutInfo2.splitDivision == 0) {
                                if ((i9 & 64) != 0) {
                                    i9 = (i9 & (-65)) | 16;
                                } else if ((i9 & 16) != 0) {
                                    i9 = (i9 & (-17)) | 64;
                                }
                            } else if ((i9 & 32) != 0) {
                                i9 = (i9 & (-33)) | 8;
                            } else if ((i9 & 8) != 0) {
                                i9 = (i9 & (-9)) | 32;
                            }
                            multiSplitLayoutInfo2.cellStagePosition = i9;
                        }
                    } else if (c2 == 0) {
                        multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 1 : 0;
                    } else if (c2 == 1) {
                        multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 0 : 1;
                    }
                } else {
                    int i15 = this.mRequestedCreateMode;
                    if (i15 != 2) {
                        if (i15 != 3) {
                            if (i15 != 4) {
                                if (i15 != 5) {
                                    i2 = -1;
                                    multiSplitLayoutInfo2.splitDivision = i2;
                                    if (c2 == 2) {
                                        multiSplitLayoutInfo2.cellStagePosition = i9;
                                        multiSplitLayoutInfo2.sideStagePosition = this.mSplitController.getSideStagePosition();
                                    } else if (isOneDirectionPosition(i9)) {
                                        int i16 = multiSplitLayoutInfo2.sideStagePosition;
                                        if (c2 == 0) {
                                            multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 1 : 0;
                                        } else if (c2 == 1) {
                                            multiSplitLayoutInfo2.sideStagePosition = (i9 == 8 || i9 == 16) ? 0 : 1;
                                        }
                                        if (multiSplitLayoutInfo2.splitDivision != this.mSplitController.getSplitDivision()) {
                                            int cellStageWindowConfigPosition2 = this.mSplitController.getCellStageWindowConfigPosition();
                                            int i17 = this.mRequestedCreateMode;
                                            if (i17 == 3 || i17 == 5) {
                                                int i18 = cellStageWindowConfigPosition2 & (-17) & (-65);
                                                i3 = i9 == 16 ? i18 | 64 : i9 == 64 ? i18 | 16 : i18;
                                            } else {
                                                i3 = cellStageWindowConfigPosition2 & (-33) & (-9);
                                                if (i9 == 8) {
                                                    i3 |= 32;
                                                } else if (i9 == 32) {
                                                    i3 |= 8;
                                                }
                                            }
                                            multiSplitLayoutInfo2.cellStagePosition = i3;
                                        } else if (i16 != multiSplitLayoutInfo2.sideStagePosition) {
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
                                    } else if (c2 == 0) {
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
                                    } else if (c2 == 1 && this.mSplitController.getCellHostStageType() == 1) {
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
                                }
                            }
                        }
                        i2 = 1;
                        multiSplitLayoutInfo2.splitDivision = i2;
                        if (c2 == 2) {
                        }
                    }
                    i2 = 0;
                    multiSplitLayoutInfo2.splitDivision = i2;
                    if (c2 == 2) {
                    }
                }
            }
            this.mSplitController.updateMultiSplitLayout(multiSplitLayoutInfo2, true, windowContainerTransaction3);
            applyTransactionWithAnimation(windowContainerTransaction3);
        }
    }

    public abstract void changeLayout();

    public final String toString() {
        return getClass().getSimpleName();
    }
}
