package com.android.server.wm;

import android.R;
import android.app.ActivityOptions;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Slog;
import android.util.SparseArray;
import android.view.KeyEvent;

import com.android.internal.os.SomeArgs;

import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

import java.lang.ref.WeakReference;

public final class DexMetaKeyPolicy {
    public final ActivityTaskManagerService mAtm;
    public final DexController mDexController;
    public final SparseArray mMetaKeyBoundsProviderMap;
    public final Rect mTaskBounds = new Rect();
    public final Rect mDisplayBounds = new Rect();
    public final Rect mLeftHalfDisplayBounds = new Rect();
    public final Rect mRightHalfDisplayBounds = new Rect();
    public final Rect mLeftDockingBounds = new Rect();
    public final Rect mRightDockingBounds = new Rect();
    public final Rect mLeftTopQuarterDisplayBounds = new Rect();
    public final Rect mLeftBottomQuarterDisplayBounds = new Rect();
    public final Rect mRightTopQuarterDisplayBounds = new Rect();
    public final Rect mRightBottomQuarterDisplayBounds = new Rect();
    public final Rect mMinHeightBounds = new Rect();
    public final Rect mMaxHeightBounds = new Rect();
    public Task mSnappingTask = null;
    public Rect mOtherSnappingBounds = null;

    public abstract class MetaKeyBoundsProvider {
        public MetaKeyBoundsProvider() {}

        public void applyBounds(Task task, Rect rect) {
            boolean isMoveToDefaultDisplayBounds =
                    MultiWindowUtils.MetaKeyBoundsChecker.isMoveToDefaultDisplayBounds(rect);
            DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
            if (isMoveToDefaultDisplayBounds) {
                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                makeBasic.setLaunchDisplayId(0);
                dexMetaKeyPolicy.mAtm.startActivityFromRecents(task.mTaskId, makeBasic.toBundle());
            } else {
                if (task.inFreeformWindowingMode()) {
                    dexMetaKeyPolicy.mAtm.resizeTask(task.mTaskId, rect, 0);
                    return;
                }
                if (task.inFullscreenWindowingMode()) {
                    ActivityRecord activityRecord = task.topRunningActivity(false);
                    IBinder iBinder = activityRecord != null ? activityRecord.token : null;
                    if (iBinder != null) {
                        dexMetaKeyPolicy.mAtm.mActivityClientController.toggleFreeformWindowingMode(
                                iBinder);
                    }
                }
            }
        }

        public final Rect getDefaultBounds() {
            int i;
            float f;
            float f2;
            DexMetaKeyPolicy dexMetaKeyPolicy = DexMetaKeyPolicy.this;
            int width = dexMetaKeyPolicy.mDisplayBounds.width();
            int height = dexMetaKeyPolicy.mDisplayBounds.height();
            if (dexMetaKeyPolicy.mDexController.getDexModeLocked() == 2) {
                PointF pointF = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO;
                i = (int) (width * pointF.x);
                f = height;
                f2 = pointF.y;
            } else {
                PointF pointF2 = MultiWindowUtils.DEX_DEFAULT_SIZE_RATIO_FOR_STANDALONE;
                i = (int) (width * pointF2.x);
                f = height;
                f2 = pointF2.y;
            }
            int i2 = (int) (f * f2);
            int i3 = (width - i) / 2;
            int i4 = (height - i2) / 2;
            return new Rect(i3, i4, i + i3, i2 + i4);
        }

        public abstract Rect getLaunchBounds(Task task, KeyEvent keyEvent);

        public abstract void updateTaskBoundsInfoIfNeeded(Task task, Rect rect);
    }

    public final class UpMetaKeyBoundsProvider extends MetaKeyBoundsProvider {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ DexMetaKeyPolicy this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ UpMetaKeyBoundsProvider(DexMetaKeyPolicy dexMetaKeyPolicy, int i) {
            super();
            this.$r8$classId = i;
            this.this$0 = dexMetaKeyPolicy;
        }

        @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
        public void applyBounds(Task task, Rect rect) {
            switch (this.$r8$classId) {
                case 0:
                    DexMetaKeyPolicy dexMetaKeyPolicy = this.this$0;
                    if (rect == null) {
                        ActivityRecord activityRecord = task.topRunningActivity(false);
                        IBinder iBinder = activityRecord != null ? activityRecord.token : null;
                        if (iBinder != null) {
                            dexMetaKeyPolicy.mAtm.mActivityClientController
                                    .toggleFreeformWindowingMode(iBinder);
                            break;
                        }
                    } else {
                        dexMetaKeyPolicy.mAtm.resizeTask(task.mTaskId, rect, 0);
                        break;
                    }
                    break;
                case 1:
                    boolean isMinimizeBounds =
                            MultiWindowUtils.MetaKeyBoundsChecker.isMinimizeBounds(rect);
                    DexMetaKeyPolicy dexMetaKeyPolicy2 = this.this$0;
                    if (!isMinimizeBounds) {
                        if (!task.inFreeformWindowingMode()) {
                            ActivityRecord activityRecord2 = task.topRunningActivity(false);
                            IBinder iBinder2 =
                                    activityRecord2 != null ? activityRecord2.token : null;
                            if (iBinder2 != null && task.inFullscreenWindowingMode()) {
                                dexMetaKeyPolicy2.mAtm.mActivityClientController
                                        .toggleFreeformWindowingMode(iBinder2);
                                break;
                            }
                        } else {
                            dexMetaKeyPolicy2.mAtm.resizeTask(task.mTaskId, rect, 0);
                            break;
                        }
                    } else {
                        dexMetaKeyPolicy2.mAtm.mMultiTaskingController.minimizeTaskLocked(
                                -1, -1, task, true);
                        break;
                    }
                    break;
                default:
                    super.applyBounds(task, rect);
                    break;
            }
        }

        @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
        public final Rect getLaunchBounds(Task task, KeyEvent keyEvent) {
            Rect rect;
            Rect rect2;
            Rect rect3;
            switch (this.$r8$classId) {
                case 0:
                    if (!task.inFreeformWindowingMode()) {
                        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
                    }
                    DexMetaKeyPolicy dexMetaKeyPolicy = this.this$0;
                    if (dexMetaKeyPolicy.mLeftHalfDisplayBounds.equals(
                            dexMetaKeyPolicy.mTaskBounds)) {
                        return dexMetaKeyPolicy.mLeftTopQuarterDisplayBounds;
                    }
                    if (dexMetaKeyPolicy.mRightHalfDisplayBounds.equals(
                            dexMetaKeyPolicy.mTaskBounds)) {
                        return dexMetaKeyPolicy.mRightTopQuarterDisplayBounds;
                    }
                    if (dexMetaKeyPolicy.mLeftBottomQuarterDisplayBounds.equals(
                            dexMetaKeyPolicy.mTaskBounds)) {
                        return dexMetaKeyPolicy.mLeftHalfDisplayBounds;
                    }
                    if (dexMetaKeyPolicy.mRightBottomQuarterDisplayBounds.equals(
                            dexMetaKeyPolicy.mTaskBounds)) {
                        return dexMetaKeyPolicy.mRightHalfDisplayBounds;
                    }
                    if (!keyEvent.isShiftPressed()) {
                        return null;
                    }
                    if (task.isDexCompatEnabled()
                            || !task.inFreeformWindowingMode()
                            || ((rect = dexMetaKeyPolicy.mTaskBounds) != null
                                    && rect.equals(dexMetaKeyPolicy.mMaxHeightBounds))) {
                        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
                    }
                    Rect rect4 = dexMetaKeyPolicy.mTaskBounds;
                    if (rect4 == null || !rect4.equals(dexMetaKeyPolicy.mMinHeightBounds)) {
                        return dexMetaKeyPolicy.mMaxHeightBounds;
                    }
                    Rect rect5 = task.mMetaKeyBounds;
                    if (rect5 != null) {
                        task.mMetaKeyBounds = null;
                        return rect5;
                    }
                    Rect defaultBounds = getDefaultBounds();
                    Rect rect6 = dexMetaKeyPolicy.mTaskBounds;
                    defaultBounds.left = rect6.left;
                    defaultBounds.right = rect6.right;
                    return defaultBounds;
                case 1:
                    DexMetaKeyPolicy dexMetaKeyPolicy2 = this.this$0;
                    if (dexMetaKeyPolicy2.mLeftHalfDisplayBounds.equals(
                            dexMetaKeyPolicy2.mTaskBounds)) {
                        return dexMetaKeyPolicy2.mLeftBottomQuarterDisplayBounds;
                    }
                    if (dexMetaKeyPolicy2.mRightHalfDisplayBounds.equals(
                            dexMetaKeyPolicy2.mTaskBounds)) {
                        return dexMetaKeyPolicy2.mRightBottomQuarterDisplayBounds;
                    }
                    if (dexMetaKeyPolicy2.mLeftTopQuarterDisplayBounds.equals(
                            dexMetaKeyPolicy2.mTaskBounds)) {
                        return dexMetaKeyPolicy2.mLeftHalfDisplayBounds;
                    }
                    if (dexMetaKeyPolicy2.mRightTopQuarterDisplayBounds.equals(
                            dexMetaKeyPolicy2.mTaskBounds)) {
                        return dexMetaKeyPolicy2.mRightHalfDisplayBounds;
                    }
                    if (keyEvent.isShiftPressed()) {
                        if (task.isDexCompatEnabled()
                                || !task.inFreeformWindowingMode()
                                || ((rect3 = dexMetaKeyPolicy2.mTaskBounds) != null
                                        && rect3.equals(dexMetaKeyPolicy2.mMinHeightBounds))) {
                            return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
                        }
                        Rect rect7 = dexMetaKeyPolicy2.mTaskBounds;
                        if (rect7 == null || !rect7.equals(dexMetaKeyPolicy2.mMaxHeightBounds)) {
                            return dexMetaKeyPolicy2.mMinHeightBounds;
                        }
                        rect2 = task.mMetaKeyBounds;
                        if (rect2 == null) {
                            Rect defaultBounds2 = getDefaultBounds();
                            Rect rect8 = dexMetaKeyPolicy2.mTaskBounds;
                            defaultBounds2.left = rect8.left;
                            defaultBounds2.right = rect8.right;
                            return defaultBounds2;
                        }
                        task.mMetaKeyBounds = null;
                    } else {
                        if (!task.inFullscreenWindowingMode()) {
                            return MultiWindowUtils.MetaKeyBoundsChecker.sMinimizeBounds;
                        }
                        rect2 = task.mMetaKeyBounds;
                        if (rect2 == null) {
                            return getDefaultBounds();
                        }
                        task.mMetaKeyBounds = null;
                    }
                    return rect2;
                case 2:
                    boolean isShiftPressed = keyEvent.isShiftPressed();
                    DexMetaKeyPolicy dexMetaKeyPolicy3 = this.this$0;
                    if (isShiftPressed) {
                        return dexMetaKeyPolicy3.mDexController.getDexModeLocked() == 2
                                ? MultiWindowUtils.MetaKeyBoundsChecker.sMoveToDefaultDisplayBounds
                                : MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
                    }
                    if (task.isDexCompatEnabled()) {
                        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
                    }
                    if (dexMetaKeyPolicy3.isLeftHalfDisplayBounds(dexMetaKeyPolicy3.mTaskBounds)) {
                        return dexMetaKeyPolicy3.mRightHalfDisplayBounds;
                    }
                    if (dexMetaKeyPolicy3.isRightHalfDisplayBounds(dexMetaKeyPolicy3.mTaskBounds)) {
                        Rect rect9 = task.mMetaKeyBounds;
                        if (rect9 == null) {
                            return getDefaultBounds();
                        }
                        task.mMetaKeyBounds = null;
                        return rect9;
                    }
                    Rect rect10 = dexMetaKeyPolicy3.mTaskBounds;
                    if (rect10 != null
                            && (dexMetaKeyPolicy3.mLeftTopQuarterDisplayBounds.equals(rect10)
                                    || dexMetaKeyPolicy3.mRightTopQuarterDisplayBounds.equals(
                                            rect10))) {
                        return dexMetaKeyPolicy3.mLeftTopQuarterDisplayBounds.equals(
                                        dexMetaKeyPolicy3.mTaskBounds)
                                ? dexMetaKeyPolicy3.mRightTopQuarterDisplayBounds
                                : dexMetaKeyPolicy3.mLeftTopQuarterDisplayBounds;
                    }
                    Rect rect11 = dexMetaKeyPolicy3.mTaskBounds;
                    return (rect11 != null
                                    && (dexMetaKeyPolicy3.mLeftBottomQuarterDisplayBounds.equals(
                                                    rect11)
                                            || dexMetaKeyPolicy3.mRightBottomQuarterDisplayBounds
                                                    .equals(rect11)))
                            ? dexMetaKeyPolicy3.mLeftBottomQuarterDisplayBounds.equals(
                                            dexMetaKeyPolicy3.mTaskBounds)
                                    ? dexMetaKeyPolicy3.mRightBottomQuarterDisplayBounds
                                    : dexMetaKeyPolicy3.mLeftBottomQuarterDisplayBounds
                            : dexMetaKeyPolicy3.mLeftHalfDisplayBounds;
                default:
                    boolean isShiftPressed2 = keyEvent.isShiftPressed();
                    DexMetaKeyPolicy dexMetaKeyPolicy4 = this.this$0;
                    if (isShiftPressed2) {
                        return dexMetaKeyPolicy4.mDexController.getDexModeLocked() == 2
                                ? MultiWindowUtils.MetaKeyBoundsChecker.sMoveToDefaultDisplayBounds
                                : MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
                    }
                    if (task.isDexCompatEnabled()) {
                        return MultiWindowUtils.MetaKeyBoundsChecker.sInvalidBounds;
                    }
                    if (dexMetaKeyPolicy4.isLeftHalfDisplayBounds(dexMetaKeyPolicy4.mTaskBounds)) {
                        Rect rect12 = task.mMetaKeyBounds;
                        if (rect12 == null) {
                            return getDefaultBounds();
                        }
                        task.mMetaKeyBounds = null;
                        return rect12;
                    }
                    if (dexMetaKeyPolicy4.isRightHalfDisplayBounds(dexMetaKeyPolicy4.mTaskBounds)) {
                        return dexMetaKeyPolicy4.mLeftHalfDisplayBounds;
                    }
                    Rect rect13 = dexMetaKeyPolicy4.mTaskBounds;
                    if (rect13 != null
                            && (dexMetaKeyPolicy4.mLeftTopQuarterDisplayBounds.equals(rect13)
                                    || dexMetaKeyPolicy4.mRightTopQuarterDisplayBounds.equals(
                                            rect13))) {
                        return dexMetaKeyPolicy4.mLeftTopQuarterDisplayBounds.equals(
                                        dexMetaKeyPolicy4.mTaskBounds)
                                ? dexMetaKeyPolicy4.mRightTopQuarterDisplayBounds
                                : dexMetaKeyPolicy4.mLeftTopQuarterDisplayBounds;
                    }
                    Rect rect14 = dexMetaKeyPolicy4.mTaskBounds;
                    return (rect14 != null
                                    && (dexMetaKeyPolicy4.mLeftBottomQuarterDisplayBounds.equals(
                                                    rect14)
                                            || dexMetaKeyPolicy4.mRightBottomQuarterDisplayBounds
                                                    .equals(rect14)))
                            ? dexMetaKeyPolicy4.mLeftBottomQuarterDisplayBounds.equals(
                                            dexMetaKeyPolicy4.mTaskBounds)
                                    ? dexMetaKeyPolicy4.mRightBottomQuarterDisplayBounds
                                    : dexMetaKeyPolicy4.mLeftBottomQuarterDisplayBounds
                            : dexMetaKeyPolicy4.mRightHalfDisplayBounds;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x006b  */
        @Override // com.android.server.wm.DexMetaKeyPolicy.MetaKeyBoundsProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void updateTaskBoundsInfoIfNeeded(
                com.android.server.wm.Task r2, android.graphics.Rect r3) {
            /*
                r1 = this;
                int r0 = r1.$r8$classId
                switch(r0) {
                    case 0: goto L75;
                    case 1: goto L57;
                    case 2: goto L2e;
                    default: goto L5;
                }
            L5:
                com.android.server.wm.DexMetaKeyPolicy r1 = r1.this$0
                boolean r0 = r1.isRightHalfDisplayBounds(r3)
                if (r0 != 0) goto Le
                goto L2d
            Le:
                android.graphics.Rect r0 = r1.mTaskBounds
                boolean r0 = r1.isQuarterDisplayBounds(r0)
                if (r0 == 0) goto L17
                goto L2d
            L17:
                boolean r0 = r2.inFullscreenWindowingMode()
                if (r0 == 0) goto L24
                r1 = 0
                r2.mMetaKeyBounds = r1
                r2.setLastNonFullscreenBounds(r3)
                goto L2d
            L24:
                android.graphics.Rect r3 = new android.graphics.Rect
                android.graphics.Rect r1 = r1.mTaskBounds
                r3.<init>(r1)
                r2.mMetaKeyBounds = r3
            L2d:
                return
            L2e:
                com.android.server.wm.DexMetaKeyPolicy r1 = r1.this$0
                boolean r0 = r1.isLeftHalfDisplayBounds(r3)
                if (r0 != 0) goto L37
                goto L56
            L37:
                android.graphics.Rect r0 = r1.mTaskBounds
                boolean r0 = r1.isQuarterDisplayBounds(r0)
                if (r0 == 0) goto L40
                goto L56
            L40:
                boolean r0 = r2.inFullscreenWindowingMode()
                if (r0 == 0) goto L4d
                r1 = 0
                r2.mMetaKeyBounds = r1
                r2.setLastNonFullscreenBounds(r3)
                goto L56
            L4d:
                android.graphics.Rect r3 = new android.graphics.Rect
                android.graphics.Rect r1 = r1.mTaskBounds
                r3.<init>(r1)
                r2.mMetaKeyBounds = r3
            L56:
                return
            L57:
                com.android.server.wm.DexMetaKeyPolicy r1 = r1.this$0
                if (r3 == 0) goto L65
                android.graphics.Rect r0 = r1.mMinHeightBounds
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L68
                r3 = 1
                goto L69
            L65:
                r1.getClass()
            L68:
                r3 = 0
            L69:
                if (r3 == 0) goto L74
                android.graphics.Rect r3 = new android.graphics.Rect
                android.graphics.Rect r1 = r1.mTaskBounds
                r3.<init>(r1)
                r2.mMetaKeyBounds = r3
            L74:
                return
            L75:
                com.android.server.wm.DexMetaKeyPolicy r1 = r1.this$0
                android.graphics.Rect r0 = r1.mTaskBounds
                boolean r0 = r1.isQuarterDisplayBounds(r0)
                if (r0 == 0) goto L80
                goto La0
            L80:
                if (r3 != 0) goto L93
                android.graphics.Rect r0 = r1.mTaskBounds
                boolean r0 = r1.isLeftHalfDisplayBounds(r0)
                if (r0 != 0) goto L93
                android.graphics.Rect r0 = r1.mTaskBounds
                boolean r0 = r1.isRightHalfDisplayBounds(r0)
                if (r0 != 0) goto L93
                goto L9d
            L93:
                if (r3 == 0) goto La0
                android.graphics.Rect r1 = r1.mMaxHeightBounds
                boolean r1 = r3.equals(r1)
                if (r1 == 0) goto La0
            L9d:
                r1 = 0
                r2.mMetaKeyBounds = r1
            La0:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.wm.DexMetaKeyPolicy.UpMetaKeyBoundsProvider.updateTaskBoundsInfoIfNeeded(com.android.server.wm.Task,"
                        + " android.graphics.Rect):void");
        }
    }

    public DexMetaKeyPolicy(
            ActivityTaskManagerService activityTaskManagerService, DexController dexController) {
        SparseArray sparseArray = new SparseArray();
        this.mMetaKeyBoundsProviderMap = sparseArray;
        this.mAtm = activityTaskManagerService;
        this.mDexController = dexController;
        sparseArray.put(21, new UpMetaKeyBoundsProvider(this, 2));
        sparseArray.put(22, new UpMetaKeyBoundsProvider(this, 3));
        sparseArray.put(19, new UpMetaKeyBoundsProvider(this, 0));
        sparseArray.put(20, new UpMetaKeyBoundsProvider(this, 1));
    }

    public MetaKeyBoundsProvider getMetaKeyBoundsProvider(int i) {
        return (MetaKeyBoundsProvider) this.mMetaKeyBoundsProviderMap.get(i);
    }

    public final void handleDexMetaKeyForSnapping() {
        if (this.mSnappingTask == null) {
            return;
        }
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        DexDockingController dexDockingController =
                activityTaskManagerService.mDexDockingController;
        Rect rect = this.mLeftDockingBounds;
        Rect rect2 = this.mRightDockingBounds;
        int width = this.mDisplayBounds.width();
        dexDockingController.mDockingBounds.put(1, rect);
        dexDockingController.mDockingBounds.put(2, rect2);
        dexDockingController.mDisplayWidth = width;
        DexDockingController dexDockingController2 =
                activityTaskManagerService.mDexDockingController;
        Task task = this.mSnappingTask;
        dexDockingController2.getClass();
        Slog.d("DexDockingController", "setCandidateTask t=" + task);
        dexDockingController2.mCandidateTask = new WeakReference(task);
        if (CoreRune.MW_CAPTION_SHELL_DEX_SNAPPING_WINDOW) {
            DexController dexController = activityTaskManagerService.mDexController;
            int i = this.mSnappingTask.mTaskId;
            Rect rect3 = this.mOtherSnappingBounds;
            dexController.getClass();
            SomeArgs obtain = SomeArgs.obtain();
            obtain.argi1 = i;
            obtain.arg1 = rect3;
            DexController.H h = dexController.mH;
            h.sendMessage(h.obtainMessage(11, obtain));
        }
        this.mSnappingTask = null;
        this.mOtherSnappingBounds = null;
    }

    public final void handleMetaKeyEvent(IBinder iBinder, KeyEvent keyEvent) {
        int i;
        int i2;
        MetaKeyBoundsProvider metaKeyBoundsProvider =
                getMetaKeyBoundsProvider(keyEvent.getKeyCode());
        if (metaKeyBoundsProvider == null) {
            return;
        }
        WindowState windowState =
                (WindowState) this.mAtm.mWindowManager.mInputToWindowMap.get(iBinder);
        Task task = windowState != null ? windowState.getTask() : null;
        if (task == null
                || task.getDisplayContent() == null
                || !task.isDexMode()
                || (!task.inFreeformWindowingMode()
                        && (!task.inFullscreenWindowingMode()
                                || !task.isActivityTypeStandardOrUndefined()))) {
            task = null;
        }
        if (task == null) {
            return;
        }
        task.getBounds(this.mTaskBounds);
        DisplayContent displayContent = task.getDisplayContent();
        displayContent.getStableRect(this.mDisplayBounds);
        if (CoreRune.MW_CAPTION_SHELL) {
            i = task.getFreeformThickness();
            i2 = i / 2;
        } else {
            i = 0;
            i2 = 0;
        }
        Rect rect = this.mDisplayBounds;
        int width = (rect.width() / 2) + rect.left;
        Rect rect2 = this.mDisplayBounds;
        int height = (rect2.height() / 2) + rect2.top;
        this.mLeftHalfDisplayBounds.set(this.mDisplayBounds);
        Rect rect3 = this.mLeftHalfDisplayBounds;
        rect3.right = width;
        this.mLeftDockingBounds.set(rect3);
        this.mLeftDockingBounds.right -= i;
        this.mRightHalfDisplayBounds.set(this.mDisplayBounds);
        Rect rect4 = this.mRightHalfDisplayBounds;
        rect4.left = width;
        this.mRightDockingBounds.set(rect4);
        this.mRightDockingBounds.left += i;
        this.mLeftTopQuarterDisplayBounds.set(this.mLeftHalfDisplayBounds);
        int i3 = height - i2;
        this.mLeftTopQuarterDisplayBounds.bottom = i3;
        this.mRightTopQuarterDisplayBounds.set(this.mRightHalfDisplayBounds);
        this.mRightTopQuarterDisplayBounds.bottom = i3;
        this.mLeftBottomQuarterDisplayBounds.set(this.mLeftHalfDisplayBounds);
        int i4 = height + i2;
        this.mLeftBottomQuarterDisplayBounds.top = i4;
        this.mRightBottomQuarterDisplayBounds.set(this.mRightHalfDisplayBounds);
        this.mRightBottomQuarterDisplayBounds.top = i4;
        Resources currentUserResources = displayContent.mDisplayPolicy.getCurrentUserResources();
        int i5 = task.mMinHeight;
        if (i5 == -1) {
            i5 =
                    currentUserResources.getDimensionPixelSize(
                            R.dimen.floating_toolbar_vertical_margin);
        }
        this.mMaxHeightBounds.set(this.mTaskBounds);
        Rect rect5 = this.mMaxHeightBounds;
        Rect rect6 = this.mDisplayBounds;
        rect5.top = rect6.top;
        rect5.bottom = rect6.bottom;
        this.mMinHeightBounds.set(this.mTaskBounds);
        this.mMinHeightBounds.bottom = this.mTaskBounds.top + i5;
        Rect launchBounds = metaKeyBoundsProvider.getLaunchBounds(task, keyEvent);
        if (MultiWindowUtils.MetaKeyBoundsChecker.isInvalidBounds(launchBounds)) {
            return;
        }
        metaKeyBoundsProvider.updateTaskBoundsInfoIfNeeded(task, launchBounds);
        metaKeyBoundsProvider.applyBounds(task, launchBounds);
        if (CoreRune.MW_CAPTION_SHELL_DEX_SNAPPING_WINDOW) {
            boolean isLeftHalfDisplayBounds = isLeftHalfDisplayBounds(launchBounds);
            boolean isRightHalfDisplayBounds = isRightHalfDisplayBounds(launchBounds);
            if (isLeftHalfDisplayBounds || isRightHalfDisplayBounds) {
                this.mSnappingTask = task;
                this.mOtherSnappingBounds =
                        isLeftHalfDisplayBounds
                                ? new Rect(this.mRightDockingBounds)
                                : new Rect(this.mLeftDockingBounds);
            } else {
                this.mSnappingTask = null;
                this.mOtherSnappingBounds = null;
            }
        }
    }

    public final boolean isLeftHalfDisplayBounds(Rect rect) {
        return rect != null && rect.equals(this.mLeftHalfDisplayBounds);
    }

    public final boolean isQuarterDisplayBounds(Rect rect) {
        if (rect == null) {
            return false;
        }
        return this.mLeftTopQuarterDisplayBounds.equals(rect)
                || this.mLeftBottomQuarterDisplayBounds.equals(rect)
                || this.mRightTopQuarterDisplayBounds.equals(rect)
                || this.mRightBottomQuarterDisplayBounds.equals(rect);
    }

    public final boolean isRightHalfDisplayBounds(Rect rect) {
        return rect != null && rect.equals(this.mRightHalfDisplayBounds);
    }
}
