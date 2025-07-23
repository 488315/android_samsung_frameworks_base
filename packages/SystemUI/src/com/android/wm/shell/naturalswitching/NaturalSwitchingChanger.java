package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SplitToSplitChanger extends NaturalSwitchingChanger {
        public static boolean isOneDirectionPosition(int i) {
            return i == 8 || i == 16 || i == 32 || i == 64;
        }

        /* JADX WARN: Code restructure failed: missing block: B:104:0x018a, code lost:
        
            if (r4 != 5) goto L114;
         */
        /* JADX WARN: Code restructure failed: missing block: B:224:0x02ed, code lost:
        
            if (r9 != 5) goto L250;
         */
        /* JADX WARN: Code restructure failed: missing block: B:225:0x02ef, code lost:
        
            r8.cellStagePosition = r13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:227:0x02f4, code lost:
        
            if (r8.splitDivision != 0) goto L258;
         */
        /* JADX WARN: Code restructure failed: missing block: B:229:0x02f8, code lost:
        
            if ((r13 & 64) == 0) goto L255;
         */
        /* JADX WARN: Code restructure failed: missing block: B:230:0x02fa, code lost:
        
            r13 = (r13 & (-65)) | 16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:231:0x0319, code lost:
        
            r8.cellStagePosition = r13;
         */
        /* JADX WARN: Code restructure failed: missing block: B:233:0x0301, code lost:
        
            if ((r13 & 16) == 0) goto L264;
         */
        /* JADX WARN: Code restructure failed: missing block: B:234:0x0303, code lost:
        
            r13 = (r13 & (-17)) | 64;
         */
        /* JADX WARN: Code restructure failed: missing block: B:236:0x030a, code lost:
        
            if ((r13 & 32) == 0) goto L261;
         */
        /* JADX WARN: Code restructure failed: missing block: B:237:0x030c, code lost:
        
            r13 = (r13 & (-33)) | 8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:239:0x0313, code lost:
        
            if ((r13 & 8) == 0) goto L264;
         */
        /* JADX WARN: Code restructure failed: missing block: B:240:0x0315, code lost:
        
            r13 = (r13 & (-9)) | 32;
         */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x009e, code lost:
        
            if (r4 != 5) goto L40;
         */
        /* JADX WARN: Removed duplicated region for block: B:107:0x0194  */
        /* JADX WARN: Removed duplicated region for block: B:108:0x01a0  */
        /* JADX WARN: Removed duplicated region for block: B:243:0x0324  */
        /* JADX WARN: Removed duplicated region for block: B:245:0x0326  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00ac  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x00f3  */
        @Override // com.android.wm.shell.naturalswitching.NaturalSwitchingChanger
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void changeLayout() {
            /*
                Method dump skipped, instructions count: 1013
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.naturalswitching.NaturalSwitchingChanger.SplitToSplitChanger.changeLayout():void");
        }
    }

    public abstract void changeLayout();

    public final String toString() {
        return getClass().getSimpleName();
    }
}
