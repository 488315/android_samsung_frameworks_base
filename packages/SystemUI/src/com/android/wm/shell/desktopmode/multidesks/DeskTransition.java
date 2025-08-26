package com.android.wm.shell.desktopmode.multidesks;

import android.os.IBinder;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface DeskTransition {

    public final class ActiveDeskWithTask implements DeskTransition {
        public final int deskId;
        public final int displayId;
        public final int enterTaskId;
        public final IBinder token;

        public ActiveDeskWithTask(IBinder iBinder, int i, int i2, int i3) {
            this.token = iBinder;
            this.displayId = i;
            this.deskId = i2;
            this.enterTaskId = i3;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new ActiveDeskWithTask(iBinder, this.displayId, this.deskId, this.enterTaskId);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ActiveDeskWithTask)) {
                return false;
            }
            ActiveDeskWithTask activeDeskWithTask = (ActiveDeskWithTask) obj;
            return Intrinsics.areEqual(this.token, activeDeskWithTask.token) && this.displayId == activeDeskWithTask.displayId && this.deskId == activeDeskWithTask.deskId && this.enterTaskId == activeDeskWithTask.enterTaskId;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            return Integer.hashCode(this.enterTaskId) + ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, this.token.hashCode() * 31, 31), 31);
        }

        public final String toString() {
            IBinder iBinder = this.token;
            StringBuilder sb = new StringBuilder("ActiveDeskWithTask(token=");
            sb.append(iBinder);
            sb.append(", displayId=");
            sb.append(this.displayId);
            sb.append(", deskId=");
            sb.append(this.deskId);
            sb.append(", enterTaskId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.enterTaskId, ")", sb);
        }
    }

    public final class ChangeDeskDisplay implements DeskTransition {
        public final int deskId;
        public final int displayId;
        public final IBinder token;

        public ChangeDeskDisplay(IBinder iBinder, int i, int i2) {
            this.token = iBinder;
            this.deskId = i;
            this.displayId = i2;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new ChangeDeskDisplay(iBinder, this.deskId, this.displayId);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ChangeDeskDisplay)) {
                return false;
            }
            ChangeDeskDisplay changeDeskDisplay = (ChangeDeskDisplay) obj;
            return Intrinsics.areEqual(this.token, changeDeskDisplay.token) && this.deskId == changeDeskDisplay.deskId && this.displayId == changeDeskDisplay.displayId;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId) + ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, this.token.hashCode() * 31, 31);
        }

        public final String toString() {
            IBinder iBinder = this.token;
            StringBuilder sb = new StringBuilder("ChangeDeskDisplay(token=");
            sb.append(iBinder);
            sb.append(", deskId=");
            sb.append(this.deskId);
            sb.append(", displayId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", sb);
        }
    }

    public final class RemoveDesk implements DeskTransition {
        public final int deskId;
        public final int displayId;
        public final OnDeskRemovedListener onDeskRemovedListener;
        public final Set tasks;
        public final IBinder token;

        public RemoveDesk(IBinder iBinder, int i, int i2, Set<Integer> set, OnDeskRemovedListener onDeskRemovedListener) {
            this.token = iBinder;
            this.displayId = i;
            this.deskId = i2;
            this.tasks = set;
            this.onDeskRemovedListener = onDeskRemovedListener;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new RemoveDesk(iBinder, this.displayId, this.deskId, this.tasks, this.onDeskRemovedListener);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RemoveDesk)) {
                return false;
            }
            RemoveDesk removeDesk = (RemoveDesk) obj;
            return Intrinsics.areEqual(this.token, removeDesk.token) && this.displayId == removeDesk.displayId && this.deskId == removeDesk.deskId && Intrinsics.areEqual(this.tasks, removeDesk.tasks) && Intrinsics.areEqual(this.onDeskRemovedListener, removeDesk.onDeskRemovedListener);
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            int iHashCode = (this.tasks.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, this.token.hashCode() * 31, 31), 31)) * 31;
            OnDeskRemovedListener onDeskRemovedListener = this.onDeskRemovedListener;
            return iHashCode + (onDeskRemovedListener == null ? 0 : onDeskRemovedListener.hashCode());
        }

        public final String toString() {
            return "RemoveDesk(token=" + this.token + ", displayId=" + this.displayId + ", deskId=" + this.deskId + ", tasks=" + this.tasks + ", onDeskRemovedListener=" + this.onDeskRemovedListener + ")";
        }
    }

    public final class RemoveDisplay implements DeskTransition {
        public final int displayId;
        public final IBinder token;

        public RemoveDisplay(IBinder iBinder, int i) {
            this.token = iBinder;
            this.displayId = i;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new RemoveDisplay(iBinder, this.displayId);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof RemoveDisplay)) {
                return false;
            }
            RemoveDisplay removeDisplay = (RemoveDisplay) obj;
            return Intrinsics.areEqual(this.token, removeDisplay.token) && this.displayId == removeDisplay.displayId;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId) + (this.token.hashCode() * 31);
        }

        public final String toString() {
            return "RemoveDisplay(token=" + this.token + ", displayId=" + this.displayId + ")";
        }
    }

    DeskTransition copyWithToken(IBinder iBinder);

    IBinder getToken();

    public final class ActivateDesk implements DeskTransition {
        public final int deskId;
        public final int displayId;
        public final OnDeskRemovedListener onDeskRemovedListener;
        public final IBinder token;
        public final int userId;

        public ActivateDesk(IBinder iBinder, int i, int i2, OnDeskRemovedListener onDeskRemovedListener, int i3) {
            this.token = iBinder;
            this.displayId = i;
            this.deskId = i2;
            this.onDeskRemovedListener = onDeskRemovedListener;
            this.userId = i3;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new ActivateDesk(iBinder, this.displayId, this.deskId, this.onDeskRemovedListener, this.userId);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ActivateDesk)) {
                return false;
            }
            ActivateDesk activateDesk = (ActivateDesk) obj;
            return Intrinsics.areEqual(this.token, activateDesk.token) && this.displayId == activateDesk.displayId && this.deskId == activateDesk.deskId && Intrinsics.areEqual(this.onDeskRemovedListener, activateDesk.onDeskRemovedListener) && this.userId == activateDesk.userId;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            int iM = ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, this.token.hashCode() * 31, 31), 31);
            OnDeskRemovedListener onDeskRemovedListener = this.onDeskRemovedListener;
            return Integer.hashCode(this.userId) + ((iM + (onDeskRemovedListener == null ? 0 : onDeskRemovedListener.hashCode())) * 31);
        }

        public final String toString() {
            IBinder iBinder = this.token;
            StringBuilder sb = new StringBuilder("ActivateDesk(token=");
            sb.append(iBinder);
            sb.append(", displayId=");
            sb.append(this.displayId);
            sb.append(", deskId=");
            sb.append(this.deskId);
            sb.append(", onDeskRemovedListener=");
            sb.append(this.onDeskRemovedListener);
            sb.append(", userId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
        }

        public /* synthetic */ ActivateDesk(IBinder iBinder, int i, int i2, OnDeskRemovedListener onDeskRemovedListener, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(iBinder, i, i2, (i4 & 8) != 0 ? null : onDeskRemovedListener, (i4 & 16) != 0 ? -1 : i3);
        }
    }

    public final class DeactivateDesk implements DeskTransition {
        public final int deskId;
        public final IBinder token;
        public final int userId;

        public DeactivateDesk(IBinder iBinder, int i, int i2) {
            this.token = iBinder;
            this.deskId = i;
            this.userId = i2;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new DeactivateDesk(iBinder, this.deskId, this.userId);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DeactivateDesk)) {
                return false;
            }
            DeactivateDesk deactivateDesk = (DeactivateDesk) obj;
            return Intrinsics.areEqual(this.token, deactivateDesk.token) && this.deskId == deactivateDesk.deskId && this.userId == deactivateDesk.userId;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            return Integer.hashCode(this.userId) + ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, this.token.hashCode() * 31, 31);
        }

        public final String toString() {
            IBinder iBinder = this.token;
            StringBuilder sb = new StringBuilder("DeactivateDesk(token=");
            sb.append(iBinder);
            sb.append(", deskId=");
            sb.append(this.deskId);
            sb.append(", userId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
        }

        public /* synthetic */ DeactivateDesk(IBinder iBinder, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(iBinder, i, (i3 & 4) != 0 ? -1 : i2);
        }
    }
}
