package com.android.systemui.statusbar;

import android.util.ArrayMap;
import android.util.Pair;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RemoteInputController {
    public final Delegate mDelegate;
    public final RemoteInputControllerLogger mLogger;
    public final RemoteInputUriController mRemoteInputUriController;
    public final ArrayList mOpen = new ArrayList();
    public final ArrayMap mSpinning = new ArrayMap();
    public final ArrayList mCallbacks = new ArrayList(3);
    public Boolean mLastAppliedRemoteInputActive = null;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Delegate {
    }

    public RemoteInputController(Delegate delegate, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger) {
        this.mDelegate = delegate;
        this.mRemoteInputUriController = remoteInputUriController;
        this.mLogger = remoteInputControllerLogger;
    }

    public final void apply(NotificationEntry notificationEntry) {
        boolean pruneWeakThenRemoveAndContains = pruneWeakThenRemoveAndContains(notificationEntry, null, null);
        NotificationStackScrollLayoutController.AnonymousClass18 anonymousClass18 = (NotificationStackScrollLayoutController.AnonymousClass18) this.mDelegate;
        anonymousClass18.getClass();
        int i = SceneContainerFlag.$r8$clinit;
        ((HeadsUpManagerImpl) NotificationStackScrollLayoutController.this.mHeadsUpManager).setRemoteInputActive(notificationEntry, pruneWeakThenRemoveAndContains);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.notifyHeightChanged(true);
        }
        boolean isRemoteInputActive$1 = isRemoteInputActive$1();
        int size = this.mCallbacks.size();
        for (int i2 = 0; i2 < size; i2++) {
            ((Callback) this.mCallbacks.get(i2)).onRemoteInputActive(isRemoteInputActive$1);
        }
        this.mLastAppliedRemoteInputActive = Boolean.valueOf(isRemoteInputActive$1);
    }

    public final void closeRemoteInputs(boolean z) {
        RemoteInputView remoteInputView;
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
                            notificationContentView.getClass();
                            RemoteInputView remoteInputView2 = notificationContentView.mExpandedRemoteInput;
                            if (remoteInputView2 != null && (remoteInputView = remoteInputView2.mEditText.mRemoteInputView) != null) {
                                remoteInputView.onDefocus(false, true, null);
                            }
                        }
                    }
                } else {
                    ExpandableNotificationRow expandableNotificationRow2 = notificationEntry2.row;
                    if (expandableNotificationRow2 != null) {
                        for (NotificationContentView notificationContentView2 : expandableNotificationRow2.mLayouts) {
                            notificationContentView2.getClass();
                            RemoteInputView remoteInputView3 = notificationContentView2.mExpandedRemoteInput;
                            if (remoteInputView3 != null) {
                                RemoteInputView.RemoteEditText remoteEditText = remoteInputView3.mEditText;
                                int i = RemoteInputView.RemoteEditText.$r8$clinit;
                                remoteEditText.defocusIfNeeded(false);
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
        boolean z = notificationEntry.mRemoteEditImeVisible;
        boolean z2 = notificationEntry.mRemoteEditImeAnimatingAway;
        String notificationStyle = notificationEntry.getNotificationStyle();
        RemoteInputControllerLogger remoteInputControllerLogger = this.mLogger;
        remoteInputControllerLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        RemoteInputControllerLogger$$ExternalSyntheticLambda0 remoteInputControllerLogger$$ExternalSyntheticLambda0 = new RemoteInputControllerLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = remoteInputControllerLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("RemoteInputControllerLog", logLevel, remoteInputControllerLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        String str2 = notificationEntry.mKey;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = str;
        logMessageImpl.str3 = notificationStyle;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logMessageImpl.bool3 = pruneWeakThenRemoveAndContains;
        logMessageImpl.bool4 = isRemoteInputActive$1;
        logBuffer.commit(obtain);
        if (pruneWeakThenRemoveAndContains) {
            pruneWeakThenRemoveAndContains(null, notificationEntry, obj);
            apply(notificationEntry);
            return;
        }
        Boolean bool = this.mLastAppliedRemoteInputActive;
        if (bool == null || !bool.booleanValue() || isRemoteInputActive$1) {
            return;
        }
        String notificationStyle2 = notificationEntry.getNotificationStyle();
        LogMessage obtain2 = logBuffer.obtain("RemoteInputControllerLog", logLevel, new RemoteInputControllerLogger$$ExternalSyntheticLambda0(1), null);
        LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
        logMessageImpl2.str1 = str2;
        logMessageImpl2.str2 = str;
        logMessageImpl2.str3 = notificationStyle2;
        logBuffer.commit(obtain2);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        default void onRemoteInputActive(boolean z) {
        }

        default void onRemoteInputSent(NotificationEntry notificationEntry) {
        }
    }
}
