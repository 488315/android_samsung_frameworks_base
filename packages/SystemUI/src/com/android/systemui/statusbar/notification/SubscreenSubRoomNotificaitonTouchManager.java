package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;

public final class SubscreenSubRoomNotificaitonTouchManager {
    public final VibrationEffect effect;
    public Context mContext;
    public float mItemTouchDownX;
    public final ItemTouchHelper mItemTouchHelper;
    public int mLayoutDirection;
    public final SubscreenNotificationInfoManager mNotificationInfoManager;
    public final AnonymousClass2 mOnItemTouchListener;
    public final AnonymousClass1 mSimpleItemTouchCallBack;
    public final Vibrator mVibrator;
    public float mSwipeThreshold = 0.3f;
    public float mSwipeEscapeVelocity = 5.0f;
    public boolean mItemViewSwipeEnabled = true;

    /* JADX WARN: Type inference failed for: r0v4, types: [androidx.recyclerview.widget.ItemTouchHelper$Callback, com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonTouchManager$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonTouchManager$2] */
    public SubscreenSubRoomNotificaitonTouchManager(Context context, SubscreenNotificationInfoManager subscreenNotificationInfoManager, Vibrator vibrator) {
        this.mItemTouchHelper = null;
        ?? r0 = new ItemTouchHelper.SimpleCallback(0, 8) { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonTouchManager.1
            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final float getSwipeEscapeVelocity(float f) {
                return f * SubscreenSubRoomNotificaitonTouchManager.this.mSwipeEscapeVelocity;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final float getSwipeThreshold() {
                return SubscreenSubRoomNotificaitonTouchManager.this.mSwipeThreshold;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean isItemViewSwipeEnabled() {
                return SubscreenSubRoomNotificaitonTouchManager.this.mItemViewSwipeEnabled;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                return false;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public final void onSwiped(RecyclerView.ViewHolder viewHolder) {
                RecyclerView.Adapter subscreenNotificationListAdapter;
                Log.d("SubscreenSubRoomNotificaitonTouchManager", "notification onSwiped : " + viewHolder);
                SubscreenSubRoomNotificaitonTouchManager subscreenSubRoomNotificaitonTouchManager = SubscreenSubRoomNotificaitonTouchManager.this;
                boolean z = subscreenSubRoomNotificaitonTouchManager.mNotificationInfoManager.mIsShownGroup;
                if (z) {
                    if (SubscreenNotificationGroupAdapter.sInstance == null) {
                        SubscreenNotificationGroupAdapter.sInstance = new SubscreenNotificationGroupAdapter();
                    }
                    subscreenNotificationListAdapter = SubscreenNotificationGroupAdapter.sInstance;
                } else {
                    subscreenNotificationListAdapter = SubscreenNotificationListAdapter.getInstance();
                }
                if (!(viewHolder instanceof SubscreenParentItemViewHolder)) {
                    subscreenNotificationListAdapter.notifyDataSetChanged();
                    return;
                }
                SubscreenParentItemViewHolder subscreenParentItemViewHolder = (SubscreenParentItemViewHolder) viewHolder;
                SubscreenNotificationInfoManager subscreenNotificationInfoManager2 = subscreenParentItemViewHolder.mNotificationInfoManager;
                ExpandableNotificationRow expandableNotificationRow = subscreenParentItemViewHolder.mInfo.mRow;
                subscreenNotificationInfoManager2.getClass();
                if (!expandableNotificationRow.canViewBeDismissed$1()) {
                    subscreenNotificationListAdapter.notifyDataSetChanged();
                    return;
                }
                Log.e("SubscreenSubRoomNotificaitonTouchManager", "removeItem mSwipeNotificationType : " + subscreenParentItemViewHolder.mInfo.mKey);
                SubscreenNotificationInfoManager subscreenNotificationInfoManager3 = subscreenSubRoomNotificaitonTouchManager.mNotificationInfoManager;
                boolean z2 = subscreenNotificationInfoManager3.mIsShownGroup;
                subscreenParentItemViewHolder.mInfo.getClass();
                subscreenNotificationInfoManager3.removeNotification(subscreenParentItemViewHolder.mInfo.mRow.mEntry);
                if (z2 && subscreenNotificationInfoManager3.mGroupDataArray.size() <= 1) {
                    ((SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class)).mDeviceModel.hideGroupNotification();
                }
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isHapticFeedbackEnabled()) {
                    subscreenSubRoomNotificaitonTouchManager.mVibrator.vibrate(subscreenSubRoomNotificaitonTouchManager.effect);
                }
                Log.d("SubscreenSubRoomNotificaitonTouchManager", "notification removed with onSwiped");
                SystemUIAnalytics.sendEventCDLog(z ? SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_GROUP : SystemUIAnalytics.EID_QPNE_COVER_SCREEN_ID_LIST, SystemUIAnalytics.EID_QPNE_COVER_CLEAR_NOTIFICATION, "from", "swipe");
            }
        };
        this.mSimpleItemTouchCallBack = r0;
        this.mOnItemTouchListener = new RecyclerView.OnItemTouchListener() { // from class: com.android.systemui.statusbar.notification.SubscreenSubRoomNotificaitonTouchManager.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public final boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                recyclerView.getParent().requestDisallowInterceptTouchEvent(false);
                int actionMasked = motionEvent.getActionMasked();
                SubscreenSubRoomNotificaitonTouchManager subscreenSubRoomNotificaitonTouchManager = SubscreenSubRoomNotificaitonTouchManager.this;
                if (actionMasked == 0) {
                    subscreenSubRoomNotificaitonTouchManager.mItemTouchDownX = motionEvent.getX();
                } else if (actionMasked == 2) {
                    if (subscreenSubRoomNotificaitonTouchManager.mLayoutDirection == 1) {
                        if (subscreenSubRoomNotificaitonTouchManager.mItemTouchDownX < motionEvent.getX()) {
                            recyclerView.getParent().requestDisallowInterceptTouchEvent(false);
                            return false;
                        }
                    } else if (subscreenSubRoomNotificaitonTouchManager.mItemTouchDownX > motionEvent.getX()) {
                        recyclerView.getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }
                }
                View findChildViewUnder = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (findChildViewUnder != null) {
                    RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(findChildViewUnder);
                    if (childViewHolder instanceof SubscreenParentDetailItemViewHolder) {
                        recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    if (childViewHolder instanceof SubscreenParentItemViewHolder) {
                        SubscreenParentItemViewHolder subscreenParentItemViewHolder = (SubscreenParentItemViewHolder) childViewHolder;
                        SubscreenNotificationInfoManager subscreenNotificationInfoManager2 = subscreenParentItemViewHolder.mNotificationInfoManager;
                        ExpandableNotificationRow expandableNotificationRow = subscreenParentItemViewHolder.mInfo.mRow;
                        subscreenNotificationInfoManager2.getClass();
                        subscreenSubRoomNotificaitonTouchManager.mSwipeThreshold = expandableNotificationRow.canViewBeDismissed$1() ? 0.3f : 1.0f;
                        SubscreenNotificationInfoManager subscreenNotificationInfoManager3 = subscreenParentItemViewHolder.mNotificationInfoManager;
                        ExpandableNotificationRow expandableNotificationRow2 = subscreenParentItemViewHolder.mInfo.mRow;
                        subscreenNotificationInfoManager3.getClass();
                        subscreenSubRoomNotificaitonTouchManager.mSwipeEscapeVelocity = expandableNotificationRow2.canViewBeDismissed$1() ? 5.0f : 1000.0f;
                        recyclerView.getParent().requestDisallowInterceptTouchEvent(true);
                        subscreenSubRoomNotificaitonTouchManager.mItemViewSwipeEnabled = true;
                    } else {
                        subscreenSubRoomNotificaitonTouchManager.mItemViewSwipeEnabled = false;
                    }
                }
                return false;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public final void onRequestDisallowInterceptTouchEvent(boolean z) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public final void onTouchEvent(MotionEvent motionEvent) {
            }
        };
        this.mNotificationInfoManager = subscreenNotificationInfoManager;
        this.mContext = context;
        this.mItemTouchHelper = new ItemTouchHelper(r0);
        this.mVibrator = vibrator;
        this.effect = VibrationEffect.semCreateWaveform(HapticFeedbackConstants.semGetVibrationIndex(41), -1, VibrationEffect.SemMagnitudeType.TYPE_TOUCH);
    }
}
