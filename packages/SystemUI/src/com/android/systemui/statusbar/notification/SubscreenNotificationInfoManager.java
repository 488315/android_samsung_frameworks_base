package com.android.systemui.statusbar.notification;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SubscreenNotificationInfoManager implements ConfigurationController.ConfigurationListener {
    public static final ArrayList mSubscreenNotificationInfoList = new ArrayList();
    public Context mContext;
    public boolean mIsShownGroup;
    public final NotifCollection mNotifCollection;
    public final SubscreenNotificationDetailAdapter mNotificationDetailAdapter;
    public final SubscreenNotificationGroupAdapter mNotificationGroupAdapter;
    public final SubscreenNotificationListAdapter mNotificationListAdapter;
    private SettingsHelper mSettingsHelper;
    public final SubscreenNotificationController mSubscreenNotificationController;
    public final ArrayList mRecyclerViewItemHolderArray = new ArrayList();
    public final ArrayList mGroupDataArray = new ArrayList();
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());
    public final ArrayList mReplyWordList = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DisplayLifecycleObserver implements DisplayLifecycle.Observer {
        public /* synthetic */ DisplayLifecycleObserver(SubscreenNotificationInfoManager subscreenNotificationInfoManager, int i) {
            this();
        }

        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            SubscreenNotificationInfoManager subscreenNotificationInfoManager = SubscreenNotificationInfoManager.this;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = subscreenNotificationInfoManager.mNotificationDetailAdapter;
            if (subscreenNotificationDetailAdapter.mNeedToUnlock) {
                Log.e("SubscreenNotificationDetailAdapter", "needToUnlock");
                KeyguardManager keyguardManager = (KeyguardManager) subscreenNotificationDetailAdapter.mContext.getSystemService("keyguard");
                Intent intent = new Intent();
                intent.putExtra("ignoreKeyguardState", true);
                keyguardManager.semSetPendingIntentAfterUnlock(null, intent);
                subscreenNotificationDetailAdapter.cleanAdapter();
            } else if (subscreenNotificationDetailAdapter.mReplyclicked && subscreenNotificationDetailAdapter.mSelectHolder != null) {
                Log.e("SubscreenNotificationDetailAdapter", "showRemoteInput");
                ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).executeRunnableDismissingKeyguard(new SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0(subscreenNotificationDetailAdapter.mSelectHolder.mInfo.mKey, 1), null, false, true, false);
                subscreenNotificationDetailAdapter.cleanAdapter();
            }
            if (z) {
                return;
            }
            subscreenNotificationInfoManager.setReplyWordList();
        }

        private DisplayLifecycleObserver() {
        }
    }

    public SubscreenNotificationInfoManager(Context context, SubscreenNotificationListAdapter subscreenNotificationListAdapter, SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter) {
        this.mContext = context;
        this.mNotificationListAdapter = subscreenNotificationListAdapter;
        this.mNotificationDetailAdapter = subscreenNotificationDetailAdapter;
        this.mNotificationGroupAdapter = subscreenNotificationGroupAdapter;
        ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(new DisplayLifecycleObserver(this, 0));
        this.mNotifCollection = (NotifCollection) Dependency.sDependency.getDependencyInner(NotifCollection.class);
        this.mSubscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
        this.mSettingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.sDependency.getDependencyInner(ConfigurationController.class))).addCallback(this);
        setReplyWordList();
    }

    public static boolean canViewBeCleared(ExpandableNotificationRow expandableNotificationRow) {
        if (expandableNotificationRow == null) {
            return true;
        }
        return expandableNotificationRow.canViewBeCleared();
    }

    public static boolean checkRemoveNotification() {
        int subscreenNotificationInfoListSize = getSubscreenNotificationInfoListSize();
        for (int i = 0; i < subscreenNotificationInfoListSize; i++) {
            if (canViewBeCleared(((SubscreenNotificationInfo) mSubscreenNotificationInfoList.get(i)).mRow)) {
                return true;
            }
        }
        return false;
    }

    public static int getSubscreenNotificationInfoListSize() {
        ArrayList arrayList = mSubscreenNotificationInfoList;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public static int removeSubscreenNotificationInfoItem(NotificationEntry notificationEntry) {
        Log.d("SubscreenNotificationInfoManager", "removeSubscreenNotificationInfoItem entry : " + notificationEntry + " >>>>> currentThread : " + Thread.currentThread());
        if (mSubscreenNotificationInfoList == null) {
            return -1;
        }
        for (int i = 0; i < getSubscreenNotificationInfoListSize(); i++) {
            ArrayList arrayList = mSubscreenNotificationInfoList;
            if (notificationEntry.mKey.equals(((SubscreenNotificationInfo) arrayList.get(i)).mRow.mEntry.mKey)) {
                arrayList.remove(i);
                return i;
            }
        }
        return -1;
    }

    public final void addRecyclerViewItemView(SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
        int size = this.mRecyclerViewItemHolderArray.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            if (((SubscreenParentItemViewHolder) this.mRecyclerViewItemHolderArray.get(i)).mInfo.mKey.equals(subscreenParentItemViewHolder.mInfo.mKey)) {
                this.mRecyclerViewItemHolderArray.remove(i);
                break;
            }
            i++;
        }
        this.mRecyclerViewItemHolderArray.add(subscreenParentItemViewHolder);
    }

    public final void clearAllRecyclerViewItem() {
        this.mRecyclerViewItemHolderArray.clear();
    }

    public final SubscreenNotificationInfo createItemsData(ExpandableNotificationRow expandableNotificationRow) {
        SubscreenNotificationInfo subscreenNotificationInfo = new SubscreenNotificationInfo(this.mContext);
        subscreenNotificationInfo.setItemsData(expandableNotificationRow);
        return subscreenNotificationInfo;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        setReplyWordList();
    }

    public final int removeGroupDataArrayItem(NotificationEntry notificationEntry) {
        if (!this.mIsShownGroup) {
            return -1;
        }
        for (int i = 0; i < this.mGroupDataArray.size(); i++) {
            if (((SubscreenNotificationInfo) this.mGroupDataArray.get(i)).mKey.equals(notificationEntry.mKey)) {
                this.mGroupDataArray.remove(i);
                return !this.mSubscreenNotificationController.mDeviceModel.isMainHeader() ? i + 1 : i;
            }
        }
        return -1;
    }

    public final void removeNotification(NotificationEntry notificationEntry) {
        SubscreenDeviceModelParent subscreenDeviceModelParent;
        SubscreenSubRoomNotification subscreenSubRoomNotification;
        SubscreenNotificationListAdapter subscreenNotificationListAdapter;
        NotificationChildrenContainer notificationChildrenContainer;
        int notificationChildCount;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        SubscreenNotificationController subscreenNotificationController = this.mSubscreenNotificationController;
        if (expandableNotificationRow != null) {
            if (expandableNotificationRow.isInsignificantSummary()) {
                subscreenNotificationController.notificationStackScrollLayoutController.mView.clearNotifications(2, true);
            } else {
                notificationEntry.row.performDismiss(false);
            }
        }
        boolean isGroupSummary = notificationEntry.mSbn.getNotification().isGroupSummary();
        String str = notificationEntry.mKey;
        if (isGroupSummary && (notificationChildrenContainer = notificationEntry.row.mChildrenContainer) != null && (notificationChildCount = notificationChildrenContainer.getNotificationChildCount()) > 0) {
            boolean z = false;
            for (int i = 0; i < notificationChildCount; i++) {
                NotificationEntry notificationEntry2 = ((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i)).mEntry;
                if (notificationEntry2.mRanking.canBubble()) {
                    subscreenNotificationController.mDeviceModel.mMainListArrayHashMap.remove(notificationEntry2.mKey);
                    z = true;
                }
            }
            if (z) {
                subscreenNotificationController.mDeviceModel.mMainListArrayHashMap.remove(str);
            }
        }
        if (notificationEntry.mRanking.canBubble()) {
            subscreenNotificationController.mDeviceModel.mMainListArrayHashMap.remove(str);
        }
        int removeSubscreenNotificationInfoItem = removeSubscreenNotificationInfoItem(notificationEntry);
        if (removeSubscreenNotificationInfoItem >= 0 && (subscreenSubRoomNotification = (subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel).mSubRoomNotification) != null && (subscreenNotificationListAdapter = subscreenSubRoomNotification.mNotificationListAdapter) != null) {
            subscreenNotificationListAdapter.notifyItemRemoved(subscreenDeviceModelParent.convertInfoIndexToAdapterPosition(removeSubscreenNotificationInfoItem));
        }
        int removeGroupDataArrayItem = removeGroupDataArrayItem(notificationEntry);
        if (removeGroupDataArrayItem >= 0) {
            this.mNotificationGroupAdapter.notifyItemRemoved(removeGroupDataArrayItem);
        }
    }

    public final void setReplyWordList() {
        if (this.mReplyWordList.size() > 0) {
            this.mReplyWordList.clear();
        }
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.subscreen_quick_reply_list);
        String coverScreenQuickReplyText = this.mSettingsHelper.getCoverScreenQuickReplyText();
        String coverScreenQuickReplyTextTranslatePos = this.mSettingsHelper.getCoverScreenQuickReplyTextTranslatePos();
        if (coverScreenQuickReplyTextTranslatePos == null) {
            this.mReplyWordList.addAll(Arrays.asList(stringArray));
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(coverScreenQuickReplyText);
            JSONArray jSONArray2 = new JSONArray(coverScreenQuickReplyTextTranslatePos);
            for (int i = 0; i < jSONArray.length(); i++) {
                String string = jSONArray2.getString(i);
                if (TextUtils.isEmpty(string) || "-1".equals(string)) {
                    this.mReplyWordList.add(jSONArray.getString(i));
                } else {
                    this.mReplyWordList.add(stringArray[Integer.parseInt(string)]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final void setShownGroup(boolean z) {
        Log.e("SubscreenNotificationInfoManager", "setShownGroup : " + z);
        this.mIsShownGroup = z;
    }
}
