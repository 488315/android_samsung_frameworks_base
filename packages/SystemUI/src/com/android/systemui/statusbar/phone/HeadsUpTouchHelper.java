package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dependency;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HeadsUpTouchHelper implements Gefingerpoken {
    public final AmbientState mAmbientState;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface HeadsUpNotificationViewController {
    }

    public HeadsUpTouchHelper(HeadsUpManager headsUpManager, IStatusBarService iStatusBarService, Callback callback, HeadsUpNotificationViewController headsUpNotificationViewController) {
        Context context;
        this.mHeadsUpManager = headsUpManager;
        this.mStatusBarService = iStatusBarService;
        this.mCallback = callback;
        this.mPanel = headsUpNotificationViewController;
        context = ((ViewGroup) NotificationStackScrollLayout.this).mContext;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mAmbientState = (AmbientState) Dependency.sDependency.getDependencyInner(AmbientState.class);
    }

    public final void endMotion$1() {
        this.mTrackingPointer = -1;
        this.mPickedChild = null;
        this.mTouchingHeadsUpView = false;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        HeadsUpManagerPhone headsUpManagerPhone;
        BaseHeadsUpManager.HeadsUpEntry headsUpEntry;
        int pointerId;
        if (!this.mTouchingHeadsUpView && motionEvent.getActionMasked() != 0) {
            return false;
        }
        int findPointerIndex = motionEvent.findPointerIndex(this.mTrackingPointer);
        if (findPointerIndex < 0) {
            this.mTrackingPointer = motionEvent.getPointerId(0);
            findPointerIndex = 0;
        }
        float x = motionEvent.getX(findPointerIndex);
        float y = motionEvent.getY(findPointerIndex);
        int actionMasked = motionEvent.getActionMasked();
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    float f = y - this.mInitialTouchY;
                    if (this.mTouchingHeadsUpView && Math.abs(f) > this.mTouchSlop && Math.abs(f) > Math.abs(x - this.mInitialTouchX)) {
                        setTrackingHeadsUp$1(true);
                        boolean z = f < 0.0f;
                        this.mCollapseSnoozes = z;
                        this.mAmbientState.mIsCollapsingHeadsup = z;
                        this.mInitialTouchX = x;
                        this.mInitialTouchY = y;
                        int translationY = (int) (this.mPickedChild.getTranslationY() + r9.mActualHeight);
                        NotificationPanelViewController.HeadsUpNotificationViewControllerImpl headsUpNotificationViewControllerImpl = (NotificationPanelViewController.HeadsUpNotificationViewControllerImpl) this.mPanel;
                        NotificationPanelViewController.this.setHeadsUpDraggingStartingHeight(translationY);
                        NotificationPanelViewController.m2106$$Nest$mstartExpandMotion(NotificationPanelViewController.this, x, y, true, translationY);
                        ((BaseHeadsUpManager) headsUpManager).unpinAll();
                        try {
                            this.mStatusBarService.clearNotificationEffects();
                        } catch (RemoteException unused) {
                        }
                        endMotion$1();
                        return true;
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 6 && this.mTrackingPointer == (pointerId = motionEvent.getPointerId(motionEvent.getActionIndex()))) {
                        int i = motionEvent.getPointerId(0) != pointerId ? 0 : 1;
                        this.mTrackingPointer = motionEvent.getPointerId(i);
                        this.mInitialTouchX = motionEvent.getX(i);
                        this.mInitialTouchY = motionEvent.getY(i);
                    }
                }
            }
            ExpandableNotificationRow expandableNotificationRow = this.mPickedChild;
            if (expandableNotificationRow != null && this.mTouchingHeadsUpView && (headsUpEntry = (headsUpManagerPhone = (HeadsUpManagerPhone) headsUpManager).getHeadsUpEntry(expandableNotificationRow.mEntry.mSbn.getKey())) != null && headsUpManagerPhone.mSystemClock.elapsedRealtime() < headsUpEntry.mPostTime) {
                endMotion$1();
                return true;
            }
            endMotion$1();
        } else {
            this.mInitialTouchY = y;
            this.mInitialTouchX = x;
            setTrackingHeadsUp$1(false);
            NotificationStackScrollLayout.AnonymousClass13 anonymousClass13 = (NotificationStackScrollLayout.AnonymousClass13) this.mCallback;
            ExpandableView childAtRawPosition = NotificationStackScrollLayout.this.getChildAtRawPosition(x, y);
            this.mTouchingHeadsUpView = false;
            if (childAtRawPosition instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) childAtRawPosition;
                boolean z2 = !NotificationStackScrollLayout.this.mIsExpanded && expandableNotificationRow2.mIsHeadsUp && expandableNotificationRow2.mIsPinned;
                this.mTouchingHeadsUpView = z2;
                if (z2) {
                    this.mPickedChild = expandableNotificationRow2;
                }
            } else if (childAtRawPosition == null && !NotificationStackScrollLayout.this.mIsExpanded) {
                BaseHeadsUpManager.HeadsUpEntry topHeadsUpEntry = ((BaseHeadsUpManager) headsUpManager).getTopHeadsUpEntry();
                NotificationEntry notificationEntry = topHeadsUpEntry != null ? topHeadsUpEntry.mEntry : null;
                if (notificationEntry != null && notificationEntry.isRowPinned()) {
                    this.mPickedChild = notificationEntry.row;
                    this.mTouchingHeadsUpView = true;
                }
            }
        }
        return false;
    }

    @Override // com.android.systemui.Gefingerpoken
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mTrackingHeadsUp) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            endMotion$1();
            setTrackingHeadsUp$1(false);
        }
        return true;
    }

    public final void setTrackingHeadsUp$1(boolean z) {
        this.mTrackingHeadsUp = z;
        ((HeadsUpManagerPhone) this.mHeadsUpManager).mTrackingHeadsUp = z;
        ExpandableNotificationRow expandableNotificationRow = z ? this.mPickedChild : null;
        NotificationPanelViewController.HeadsUpNotificationViewControllerImpl headsUpNotificationViewControllerImpl = (NotificationPanelViewController.HeadsUpNotificationViewControllerImpl) this.mPanel;
        if (expandableNotificationRow == null) {
            headsUpNotificationViewControllerImpl.getClass();
            return;
        }
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
        notificationPanelViewController2.mTrackedHeadsUpNotification = expandableNotificationRow;
        for (int i = 0; i < notificationPanelViewController2.mTrackingHeadsUpListeners.size(); i++) {
            ((Consumer) notificationPanelViewController2.mTrackingHeadsUpListeners.get(i)).accept(expandableNotificationRow);
        }
        notificationPanelViewController.mExpandingFromHeadsUp = true;
    }
}
