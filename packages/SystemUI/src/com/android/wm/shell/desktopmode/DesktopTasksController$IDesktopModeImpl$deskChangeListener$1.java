package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.window.DesktopModeFlags;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentTasksController;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;

/* loaded from: classes3.dex */
public final class DesktopTasksController$IDesktopModeImpl$deskChangeListener$1 implements DesktopRepository.DeskChangeListener {
    public final /* synthetic */ DesktopTasksController.IDesktopModeImpl this$0;

    public DesktopTasksController$IDesktopModeImpl$deskChangeListener$1(DesktopTasksController.IDesktopModeImpl iDesktopModeImpl) {
        this.this$0 = iDesktopModeImpl;
    }

    @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
    public final void onActiveDeskChanged(final int i, final int i2, final int i3) {
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onActiveDeskChanged display=%d new=%d old=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1$onActiveDeskChanged$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                int i4 = i;
                int i5 = i2;
                int i6 = i3;
                Parcel parcelObtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    iDesktopTaskListener$Stub$Proxy.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        });
    }

    @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
    public final void onCanCreateDesksChanged(final boolean z) {
        if (!CoreRune.DW_MULTIPLE_DESKS) {
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onCanCreateDesksChanged do not support multiple desks", new Object[0]);
            return;
        }
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onCanCreateDesksChanged canCreateDesks=%b", new Object[]{Boolean.valueOf(z)});
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1$onCanCreateDesksChanged$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                boolean z2 = z;
                IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                Parcel parcelObtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    parcelObtain.writeBoolean(z2);
                    iDesktopTaskListener$Stub$Proxy.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x007a  */
    @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDeskAdded(final int i, final int i2) {
        DesktopTasksController desktopTasksController;
        List recentTasks;
        Set set;
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onDeskAdded display=%d deskId=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        DesktopTasksController.IDesktopModeImpl iDesktopModeImpl = this.this$0;
        SingleInstanceRemoteListener singleInstanceRemoteListener = iDesktopModeImpl.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1$onDeskAdded$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                int i3 = i;
                int i4 = i2;
                Parcel parcelObtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    iDesktopTaskListener$Stub$Proxy.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        });
        if (i != 0 || (desktopTasksController = iDesktopModeImpl.controller) == null) {
            return;
        }
        Set activeTaskIdsInDesk = desktopTasksController.taskRepository.getActiveTaskIdsInDesk(i2);
        if (activeTaskIdsInDesk.isEmpty()) {
            return;
        }
        RecentTasksController recentTasksController = desktopTasksController.recentTasksController;
        if (recentTasksController != null) {
            try {
                recentTasks = recentTasksController.mActivityTaskManager.getRecentTasks(Integer.MAX_VALUE, 3, ActivityManager.getCurrentUser());
            } catch (BadParcelableException unused) {
                recentTasks = Collections.EMPTY_LIST;
            }
            if (recentTasks != null) {
                List list = recentTasks;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(((ActivityManager.RecentTaskInfo) it.next()).taskId));
                }
                set = CollectionsKt___CollectionsKt.toSet(arrayList);
                if (set == null) {
                    set = EmptySet.INSTANCE;
                }
            }
        }
        if (set.isEmpty()) {
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = activeTaskIdsInDesk.iterator();
        while (it2.hasNext()) {
            int iIntValue = ((Number) it2.next()).intValue();
            if (!set.contains(Integer.valueOf(iIntValue))) {
                arrayList2.add(Integer.valueOf(iIntValue));
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        DesktopRepository desktopRepository = desktopTasksController.taskRepository;
        desktopRepository.getClass();
        int size = arrayList2.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList2.get(i3);
            i3++;
            int iIntValue2 = ((Number) obj).intValue();
            desktopRepository.logD("removeTaskFromDesk: deskId=%d, taskId=%d", Integer.valueOf(i2), Integer.valueOf(iIntValue2));
            desktopRepository.boundsBeforeMaximizeByTaskId.remove(iIntValue2);
            desktopRepository.displayLayoutBeforeMaximizeByTaskId.remove(iIntValue2);
            desktopRepository.boundsBeforeFullImmersiveByTaskId.remove(iIntValue2);
            DesktopRepository.Desk desk = desktopRepository.desktopData.getDesk(i2);
            if (desk == null) {
                return;
            }
            if (desk.freeformTasksInZOrder.remove(Integer.valueOf(iIntValue2))) {
                desktopRepository.logD("Remaining freeform tasks in desk: %d, tasks: %s", Integer.valueOf(desk.deskId), CollectionsKt___CollectionsKt.joinToString$default(desk.freeformTasksInZOrder, ", ", "[", "]", null, 56));
            }
            desktopRepository.unminimizeTaskFromDesk(i2, iIntValue2);
            desktopRepository.setTaskInFullImmersiveStateInDesk(i2, iIntValue2, false);
            desktopRepository.removeActiveTaskFromDesk(i2, iIntValue2, true);
            desktopRepository.removeVisibleTaskFromDesk(i2, iIntValue2);
        }
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PERSISTENCE.isTrue()) {
            desktopRepository.updatePersistentRepositoryForDesk(i2);
        }
    }

    @Override // com.android.wm.shell.desktopmode.DesktopRepository.DeskChangeListener
    public final void onDeskRemoved(final int i, final int i2) {
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onDeskRemoved display=%d deskId=%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$deskChangeListener$1$onDeskRemoved$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                int i3 = i;
                int i4 = i2;
                Parcel parcelObtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                try {
                    parcelObtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    iDesktopTaskListener$Stub$Proxy.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        });
    }
}
