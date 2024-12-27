package com.android.systemui.statusbar;

import android.util.ArrayMap;
import android.util.Pair;
import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public final class RemoteInputController {
    public final Delegate mDelegate;
    public final RemoteInputControllerLogger mLogger;
    public final RemoteInputUriController mRemoteInputUriController;
    public final ArrayList mOpen = new ArrayList();
    public final ArrayMap mSpinning = new ArrayMap();
    public final ArrayList mCallbacks = new ArrayList(3);
    public Boolean mLastAppliedRemoteInputActive = null;

    public interface Delegate {
    }

    public RemoteInputController(Delegate delegate, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger) {
        this.mDelegate = delegate;
        this.mRemoteInputUriController = remoteInputUriController;
        this.mLogger = remoteInputControllerLogger;
    }

    public final void apply(NotificationEntry notificationEntry) {
        boolean pruneWeakThenRemoveAndContains = pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = NotificationStackScrollLayoutController.this;
        HeadsUpManagerPhone headsUpManagerPhone = (HeadsUpManagerPhone) notificationStackScrollLayoutController.mHeadsUpManager;
        headsUpManagerPhone.getClass();
        HeadsUpManagerPhone.HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpManagerPhone.HeadsUpEntryPhone) headsUpManagerPhone.mHeadsUpEntryMap.get(notificationEntry.mKey);
        if (headsUpEntryPhone != null && headsUpEntryPhone.mRemoteInputActive != pruneWeakThenRemoveAndContains) {
            headsUpEntryPhone.mRemoteInputActive = pruneWeakThenRemoveAndContains;
            if (pruneWeakThenRemoveAndContains) {
                headsUpEntryPhone.cancelAutoRemovalCallbacks("setRemoteInputActive(true)");
            } else {
                headsUpEntryPhone.updateEntry("setRemoteInputActive(false)", false);
            }
            headsUpManagerPhone.updateTopHeadsUpFlow();
        }
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.notifyHeightChanged(true);
        }
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        notificationStackScrollLayoutController.updateFooter();
        boolean isRemoteInputActive$1 = isRemoteInputActive$1();
        int size = this.mCallbacks.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Callback) this.mCallbacks.get(i2)).onRemoteInputActive(isRemoteInputActive$1);
        }
        this.mLastAppliedRemoteInputActive = Boolean.valueOf(isRemoteInputActive$1);
    }

    public final void closeRemoteInputs(boolean z) {
        RemoteInputView remoteInputView;
        RemoteInputView remoteInputView2;
        if (this.mOpen.size() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mOpen.size());
        for (int size = this.mOpen.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) ((Pair) this.mOpen.get(size)).first).get();
            if (notificationEntry != null && notificationEntry.rowExists()) {
                arrayList.add(notificationEntry);
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(size2);
            if (notificationEntry2.rowExists()) {
                if (z) {
                    ExpandableNotificationRow expandableNotificationRow = notificationEntry2.row;
                    if (expandableNotificationRow != null) {
                        for (NotificationContentView notificationContentView : expandableNotificationRow.mLayouts) {
                            RemoteInputView remoteInputView3 = notificationContentView.mHeadsUpRemoteInput;
                            if (remoteInputView3 != null && (remoteInputView2 = remoteInputView3.mEditText.mRemoteInputView) != null) {
                                remoteInputView2.onDefocus(false, true, null);
                            }
                            RemoteInputView remoteInputView4 = notificationContentView.mExpandedRemoteInput;
                            if (remoteInputView4 != null && (remoteInputView = remoteInputView4.mEditText.mRemoteInputView) != null) {
                                remoteInputView.onDefocus(false, true, null);
                            }
                        }
                    }
                } else {
                    ExpandableNotificationRow expandableNotificationRow2 = notificationEntry2.row;
                    if (expandableNotificationRow2 != null) {
                        for (NotificationContentView notificationContentView2 : expandableNotificationRow2.mLayouts) {
                            RemoteInputView remoteInputView5 = notificationContentView2.mHeadsUpRemoteInput;
                            if (remoteInputView5 != null) {
                                RemoteInputView.RemoteEditText remoteEditText = remoteInputView5.mEditText;
                                int i = RemoteInputView.RemoteEditText.$r8$clinit;
                                remoteEditText.defocusIfNeeded(false);
                            }
                            RemoteInputView remoteInputView6 = notificationContentView2.mExpandedRemoteInput;
                            if (remoteInputView6 != null) {
                                RemoteInputView.RemoteEditText remoteEditText2 = remoteInputView6.mEditText;
                                int i2 = RemoteInputView.RemoteEditText.$r8$clinit;
                                remoteEditText2.defocusIfNeeded(false);
                            }
                        }
                    }
                }
            }
        }
    }

    public final boolean isRemoteInputActive$1() {
        pruneWeakThenRemoveAndContains(null, null, null);
        return !this.mOpen.isEmpty();
    }

    public final boolean pruneWeakThenRemoveAndContains(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, Object obj) {
        boolean z = false;
        for (int size = this.mOpen.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry3 = (NotificationEntry) ((WeakReference) ((Pair) this.mOpen.get(size)).first).get();
            Object obj2 = ((Pair) this.mOpen.get(size)).second;
            boolean z2 = obj == null || obj2 == obj;
            if (notificationEntry3 == null || (notificationEntry3 == notificationEntry2 && z2)) {
                this.mOpen.remove(size);
            } else if (notificationEntry3 == notificationEntry) {
                if (obj == null || obj == obj2) {
                    z = true;
                } else {
                    this.mOpen.remove(size);
                }
            }
        }
        return z;
    }

    public final void removeRemoteInput(NotificationEntry notificationEntry, Object obj, String str) {
        Objects.requireNonNull(notificationEntry);
        boolean pruneWeakThenRemoveAndContains = pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        boolean isRemoteInputActive$1 = isRemoteInputActive$1();
        this.mLogger.logRemoveRemoteInput(notificationEntry.mKey, notificationEntry.mRemoteEditImeVisible, notificationEntry.mRemoteEditImeAnimatingAway, pruneWeakThenRemoveAndContains, isRemoteInputActive$1, str, notificationEntry.getNotificationStyle());
        if (pruneWeakThenRemoveAndContains) {
            pruneWeakThenRemoveAndContains(null, notificationEntry, obj);
            apply(notificationEntry);
            return;
        }
        Boolean bool = this.mLastAppliedRemoteInputActive;
        if (bool == null || !bool.booleanValue() || isRemoteInputActive$1) {
            return;
        }
        this.mLogger.logRemoteInputApplySkipped(notificationEntry.mKey, str, notificationEntry.getNotificationStyle());
    }

    public interface Callback {
        default void onRemoteInputActive(boolean z) {
        }

        default void onRemoteInputSent(NotificationEntry notificationEntry) {
        }
    }
}
