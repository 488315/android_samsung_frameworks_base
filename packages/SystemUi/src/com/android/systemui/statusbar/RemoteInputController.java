package com.android.systemui.statusbar;

import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Pair;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RemoteInputController {
    public static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    public final Delegate mDelegate;
    public final RemoteInputControllerLogger mLogger;
    public final RemoteInputUriController mRemoteInputUriController;
    public final ArrayList mOpen = new ArrayList();
    public final ArrayMap mSpinning = new ArrayMap();
    public final ArrayList mCallbacks = new ArrayList(3);
    public Boolean mLastAppliedRemoteInputActive = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        HeadsUpManagerPhone headsUpManagerPhone = notificationStackScrollLayoutController.mHeadsUpManager;
        headsUpManagerPhone.getClass();
        HeadsUpManagerPhone.HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpManagerPhone.HeadsUpEntryPhone) headsUpManagerPhone.mAlertEntries.get(notificationEntry.mKey);
        if (headsUpEntryPhone != null && headsUpEntryPhone.remoteInputActive != pruneWeakThenRemoveAndContains) {
            headsUpEntryPhone.remoteInputActive = pruneWeakThenRemoveAndContains;
            if (pruneWeakThenRemoveAndContains) {
                headsUpEntryPhone.removeAutoRemovalCallbacks();
            } else {
                headsUpEntryPhone.updateEntry(false);
            }
        }
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.notifyHeightChanged(true);
        }
        notificationStackScrollLayoutController.updateFooter();
        boolean isRemoteInputActive$1 = isRemoteInputActive$1();
        ArrayList arrayList = this.mCallbacks;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((Callback) arrayList.get(i)).onRemoteInputActive(isRemoteInputActive$1);
        }
        this.mLastAppliedRemoteInputActive = Boolean.valueOf(isRemoteInputActive$1);
    }

    public final void closeRemoteInputs(boolean z) {
        RemoteInputView remoteInputView;
        RemoteInputView remoteInputView2;
        ArrayList arrayList = this.mOpen;
        if (arrayList.size() == 0) {
            return;
        }
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) ((Pair) arrayList.get(size)).first).get();
            if (notificationEntry != null && notificationEntry.rowExists()) {
                arrayList2.add(notificationEntry);
            }
        }
        int size2 = arrayList2.size();
        while (true) {
            size2--;
            if (size2 < 0) {
                return;
            }
            NotificationEntry notificationEntry2 = (NotificationEntry) arrayList2.get(size2);
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
        ArrayList arrayList = this.mOpen;
        boolean z = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry3 = (NotificationEntry) ((WeakReference) ((Pair) arrayList.get(size)).first).get();
            Object obj2 = ((Pair) arrayList.get(size)).second;
            boolean z2 = obj == null || obj2 == obj;
            if (notificationEntry3 == null || (notificationEntry3 == notificationEntry2 && z2)) {
                arrayList.remove(size);
            } else if (notificationEntry3 == notificationEntry) {
                if (obj == null || obj == obj2) {
                    z = true;
                } else {
                    arrayList.remove(size);
                }
            }
        }
        return z;
    }

    public final void removeRemoteInput(NotificationEntry notificationEntry, Object obj) {
        Objects.requireNonNull(notificationEntry);
        boolean pruneWeakThenRemoveAndContains = pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        this.mLogger.logRemoveRemoteInput(notificationEntry.mKey, notificationEntry.mRemoteEditImeVisible, notificationEntry.mRemoteEditImeAnimatingAway, Boolean.valueOf(pruneWeakThenRemoveAndContains));
        if (pruneWeakThenRemoveAndContains) {
            pruneWeakThenRemoveAndContains(null, notificationEntry, obj);
            apply(notificationEntry);
        }
    }

    public final void removeSpinning(Object obj, String str) {
        Objects.requireNonNull(str);
        ArrayMap arrayMap = this.mSpinning;
        if (obj == null || arrayMap.get(str) == obj) {
            arrayMap.remove(str);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        default void onRemoteInputActive(boolean z) {
        }

        default void onRemoteInputSent(NotificationEntry notificationEntry) {
        }
    }
}
