package com.android.systemui.statusbar.notification.headsup;

import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes3.dex */
public class HeadsUpTouchHelper implements Gefingerpoken {
    public final AmbientState mAmbientState = (AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class);
    public final Callback mCallback;
    public boolean mCollapseSnoozes;
    public final HeadsUpManager mHeadsUpManager;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final HeadsUpNotificationViewController mPanel;
    public ExpandableNotificationRow mPickedChild;
    public final IStatusBarService mStatusBarService;
    public final float mTouchSlop;
    public boolean mTouchingHeadsUpView;
    public boolean mTrackingHeadsUp;
    public int mTrackingPointer;

    public interface Callback {
    }

    public interface HeadsUpNotificationViewController {
        void setHeadsUpDraggingStartingHeight(int i);

        void setTrackedHeadsUp(ExpandableNotificationRow expandableNotificationRow);

        void startExpand(float f, float f2, float f3);
    }

    public HeadsUpTouchHelper(HeadsUpManager headsUpManager, IStatusBarService iStatusBarService, Callback callback, HeadsUpNotificationViewController headsUpNotificationViewController) {
        this.mHeadsUpManager = headsUpManager;
        this.mStatusBarService = iStatusBarService;
        this.mCallback = callback;
        this.mPanel = headsUpNotificationViewController;
        this.mTouchSlop = ViewConfiguration.get(((ViewGroup) NotificationStackScrollLayout.this).mContext).getScaledTouchSlop();
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        HeadsUpManagerImpl headsUpManagerImpl;
        HeadsUpManagerImpl.HeadsUpEntry headsUpEntry;
        int pointerId;
        if (this.mTouchingHeadsUpView || motionEvent.getActionMasked() == 0) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
            if (iFindPointerIndex < 0) {
                this.mTrackingPointer = motionEvent.getPointerId(0);
                iFindPointerIndex = 0;
            }
            float x = motionEvent.getX(iFindPointerIndex);
            float y = motionEvent.getY(iFindPointerIndex);
            int actionMasked = motionEvent.getActionMasked();
            HeadsUpManager headsUpManager = this.mHeadsUpManager;
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked == 2) {
                        float f = y - this.mInitialTouchY;
                        if (this.mTouchingHeadsUpView && Math.abs(f) > this.mTouchSlop && Math.abs(f) > Math.abs(x - this.mInitialTouchX)) {
                            int i = SceneContainerFlag.$r8$clinit;
                            setTrackingHeadsUp$1(true);
                            boolean z = f < 0.0f;
                            this.mCollapseSnoozes = z;
                            this.mAmbientState.mIsCollapsingHeadsup = z;
                            this.mInitialTouchX = x;
                            this.mInitialTouchY = y;
                            int translationY = (int) (this.mPickedChild.getTranslationY() + r11.mActualHeight);
                            HeadsUpNotificationViewController headsUpNotificationViewController = this.mPanel;
                            headsUpNotificationViewController.setHeadsUpDraggingStartingHeight(translationY);
                            headsUpNotificationViewController.startExpand(x, y, translationY);
                            ((HeadsUpManagerImpl) headsUpManager).unpinAll();
                            SecPanelSAStatusLogInteractor secPanelSAStatusLogInteractor = (SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class);
                            secPanelSAStatusLogInteractor.getClass();
                            SecPanelSplitHelper.Companion.getClass();
                            if (SecPanelSplitHelper.isEnabled) {
                                StateFlowImpl stateFlowImpl = secPanelSAStatusLogInteractor.repository._openNotificationPanelFromHun;
                                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                            }
                            try {
                                this.mStatusBarService.clearNotificationEffects();
                            } catch (RemoteException unused) {
                            }
                            this.mTrackingPointer = -1;
                            this.mPickedChild = null;
                            this.mTouchingHeadsUpView = false;
                            return true;
                        }
                    } else if (actionMasked != 3) {
                        if (actionMasked == 6 && this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                            int i2 = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                            this.mTrackingPointer = motionEvent.getPointerId(i2);
                            this.mInitialTouchX = motionEvent.getX(i2);
                            this.mInitialTouchY = motionEvent.getY(i2);
                            return false;
                        }
                    }
                }
                ExpandableNotificationRow expandableNotificationRow = this.mPickedChild;
                if (expandableNotificationRow == null || !this.mTouchingHeadsUpView || (headsUpEntry = (headsUpManagerImpl = (HeadsUpManagerImpl) headsUpManager).getHeadsUpEntry(expandableNotificationRow.getKey())) == null || headsUpManagerImpl.mSystemClock.elapsedRealtime() >= headsUpEntry.mPostTime) {
                    this.mTrackingPointer = -1;
                    this.mPickedChild = null;
                    this.mTouchingHeadsUpView = false;
                    return false;
                }
                this.mTrackingPointer = -1;
                this.mPickedChild = null;
                this.mTouchingHeadsUpView = false;
                return true;
            }
            this.mInitialTouchY = y;
            this.mInitialTouchX = x;
            setTrackingHeadsUp$1(false);
            NotificationStackScrollLayout.AnonymousClass10 anonymousClass10 = (NotificationStackScrollLayout.AnonymousClass10) this.mCallback;
            ExpandableView childAtRawPosition = NotificationStackScrollLayout.this.getChildAtRawPosition(x, y);
            this.mTouchingHeadsUpView = false;
            if (childAtRawPosition instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) childAtRawPosition;
                boolean z2 = !NotificationStackScrollLayout.this.mIsExpanded && expandableNotificationRow2.mIsHeadsUp && expandableNotificationRow2.mPinnedStatus.isPinned();
                this.mTouchingHeadsUpView = z2;
                if (z2) {
                    this.mPickedChild = expandableNotificationRow2;
                    return false;
                }
            } else if (childAtRawPosition == null && !NotificationStackScrollLayout.this.mIsExpanded) {
                HeadsUpManagerImpl.HeadsUpEntry topHeadsUpEntry = ((HeadsUpManagerImpl) headsUpManager).getTopHeadsUpEntry();
                NotificationEntry notificationEntry = topHeadsUpEntry != null ? topHeadsUpEntry.mEntry : null;
                if (notificationEntry != null && notificationEntry.isRowPinned()) {
                    this.mPickedChild = notificationEntry.row;
                    this.mTouchingHeadsUpView = true;
                }
            }
        }
        return false;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int i = SceneContainerFlag.$r8$clinit;
        if (!this.mTrackingHeadsUp) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1 && actionMasked != 3) {
            return true;
        }
        this.mTrackingPointer = -1;
        this.mPickedChild = null;
        this.mTouchingHeadsUpView = false;
        setTrackingHeadsUp$1(false);
        return true;
    }

    public final void setTrackingHeadsUp$1(boolean z) {
        this.mTrackingHeadsUp = z;
        ((HeadsUpManagerImpl) this.mHeadsUpManager).mTrackingHeadsUp.updateState(null, Boolean.valueOf(z));
        this.mPanel.setTrackedHeadsUp(z ? this.mPickedChild : null);
    }
}
