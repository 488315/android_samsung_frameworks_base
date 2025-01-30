package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HeadsUpTouchHelper implements Gefingerpoken {
    public final Callback mCallback;
    public boolean mCollapseSnoozes;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public float mInitialTouchX;
    public float mInitialTouchY;
    public final HeadsUpNotificationViewController mPanel;
    public ExpandableNotificationRow mPickedChild;
    public final float mTouchSlop;
    public boolean mTouchingHeadsUpView;
    public boolean mTrackingHeadsUp;
    public int mTrackingPointer;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface HeadsUpNotificationViewController {
    }

    public HeadsUpTouchHelper(HeadsUpManagerPhone headsUpManagerPhone, Callback callback, HeadsUpNotificationViewController headsUpNotificationViewController) {
        Context context;
        this.mHeadsUpManager = headsUpManagerPhone;
        this.mCallback = callback;
        this.mPanel = headsUpNotificationViewController;
        context = ((ViewGroup) NotificationStackScrollLayout.this).mContext;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public final void endMotion$1() {
        this.mTrackingPointer = -1;
        this.mPickedChild = null;
        this.mTouchingHeadsUpView = false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ea  */
    @Override // com.android.systemui.Gefingerpoken
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Object[] objArr;
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
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    float f = y - this.mInitialTouchY;
                    if (this.mTouchingHeadsUpView && Math.abs(f) > this.mTouchSlop && Math.abs(f) > Math.abs(x - this.mInitialTouchX)) {
                        setTrackingHeadsUp(true);
                        this.mCollapseSnoozes = f < 0.0f;
                        this.mInitialTouchX = x;
                        this.mInitialTouchY = y;
                        int translationY = (int) (this.mPickedChild.getTranslationY() + r9.mActualHeight);
                        NotificationPanelViewController.HeadsUpNotificationViewControllerImpl headsUpNotificationViewControllerImpl = (NotificationPanelViewController.HeadsUpNotificationViewControllerImpl) this.mPanel;
                        NotificationPanelViewController.this.setHeadsUpDraggingStartingHeight(translationY);
                        NotificationPanelViewController.m1695$$Nest$mstartExpandMotion(NotificationPanelViewController.this, x, y, true, translationY);
                        headsUpManagerPhone.unpinAll();
                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) NotificationPanelViewController.this.mCentralSurfaces;
                        centralSurfacesImpl.getClass();
                        try {
                            centralSurfacesImpl.mBarService.clearNotificationEffects();
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
            if (expandableNotificationRow != null && this.mTouchingHeadsUpView) {
                HeadsUpManager.HeadsUpEntry headsUpEntry = headsUpManagerPhone.getHeadsUpEntry(expandableNotificationRow.mEntry.mSbn.getKey());
                if (headsUpEntry != null) {
                    headsUpManagerPhone.mClock.getClass();
                    if (SystemClock.elapsedRealtime() < headsUpEntry.mPostTime) {
                        objArr = true;
                        if (objArr != false) {
                            endMotion$1();
                            return true;
                        }
                    }
                }
                objArr = false;
                if (objArr != false) {
                }
            }
            endMotion$1();
        } else {
            this.mInitialTouchY = y;
            this.mInitialTouchX = x;
            setTrackingHeadsUp(false);
            NotificationStackScrollLayout.C292815 c292815 = (NotificationStackScrollLayout.C292815) this.mCallback;
            ExpandableView childAtRawPosition = NotificationStackScrollLayout.this.getChildAtRawPosition(x, y);
            this.mTouchingHeadsUpView = false;
            if (childAtRawPosition instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) childAtRawPosition;
                boolean z = !NotificationStackScrollLayout.this.mIsExpanded && expandableNotificationRow2.mIsHeadsUp && expandableNotificationRow2.mIsPinned;
                this.mTouchingHeadsUpView = z;
                if (z) {
                    this.mPickedChild = expandableNotificationRow2;
                }
            } else if (childAtRawPosition == null && !NotificationStackScrollLayout.this.mIsExpanded) {
                HeadsUpManager.HeadsUpEntry topHeadsUpEntry = headsUpManagerPhone.getTopHeadsUpEntry();
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
            setTrackingHeadsUp(false);
        }
        return true;
    }

    public final void setTrackingHeadsUp(boolean z) {
        this.mTrackingHeadsUp = z;
        this.mHeadsUpManager.mTrackingHeadsUp = z;
        ExpandableNotificationRow expandableNotificationRow = z ? this.mPickedChild : null;
        NotificationPanelViewController.HeadsUpNotificationViewControllerImpl headsUpNotificationViewControllerImpl = (NotificationPanelViewController.HeadsUpNotificationViewControllerImpl) this.mPanel;
        if (expandableNotificationRow == null) {
            headsUpNotificationViewControllerImpl.getClass();
            return;
        }
        NotificationPanelViewController notificationPanelViewController = NotificationPanelViewController.this;
        NotificationPanelViewController notificationPanelViewController2 = NotificationPanelViewController.this;
        notificationPanelViewController2.mTrackedHeadsUpNotification = expandableNotificationRow;
        int i = 0;
        while (true) {
            ArrayList arrayList = notificationPanelViewController2.mTrackingHeadsUpListeners;
            if (i >= arrayList.size()) {
                notificationPanelViewController.mExpandingFromHeadsUp = true;
                return;
            } else {
                ((Consumer) arrayList.get(i)).accept(expandableNotificationRow);
                i++;
            }
        }
    }
}
