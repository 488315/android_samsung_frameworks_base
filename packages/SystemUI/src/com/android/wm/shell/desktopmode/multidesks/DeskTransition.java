package com.android.wm.shell.desktopmode.multidesks;

import android.os.IBinder;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface DeskTransition {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DeactivateDesk implements DeskTransition {
        public final int deskId;
        public final IBinder token;

        public DeactivateDesk(IBinder iBinder, int i) {
            this.token = iBinder;
            this.deskId = i;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new DeactivateDesk(iBinder, this.deskId);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DeactivateDesk)) {
                return false;
            }
            DeactivateDesk deactivateDesk = (DeactivateDesk) obj;
            return Intrinsics.areEqual(this.token, deactivateDesk.token) && this.deskId == deactivateDesk.deskId;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            return Integer.hashCode(this.deskId) + (this.token.hashCode() * 31);
        }

        public final String toString() {
            return "DeactivateDesk(token=" + this.token + ", deskId=" + this.deskId + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (this.tasks.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, this.token.hashCode() * 31, 31), 31)) * 31;
            OnDeskRemovedListener onDeskRemovedListener = this.onDeskRemovedListener;
            return hashCode + (onDeskRemovedListener == null ? 0 : onDeskRemovedListener.hashCode());
        }

        public final String toString() {
            return "RemoveDesk(token=" + this.token + ", displayId=" + this.displayId + ", deskId=" + this.deskId + ", tasks=" + this.tasks + ", onDeskRemovedListener=" + this.onDeskRemovedListener + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ActivateDesk implements DeskTransition {
        public final int deskId;
        public final int displayId;
        public final OnDeskRemovedListener onDeskRemovedListener;
        public final IBinder token;

        public ActivateDesk(IBinder iBinder, int i, int i2, OnDeskRemovedListener onDeskRemovedListener) {
            this.token = iBinder;
            this.displayId = i;
            this.deskId = i2;
            this.onDeskRemovedListener = onDeskRemovedListener;
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final DeskTransition copyWithToken(IBinder iBinder) {
            return new ActivateDesk(iBinder, this.displayId, this.deskId, this.onDeskRemovedListener);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ActivateDesk)) {
                return false;
            }
            ActivateDesk activateDesk = (ActivateDesk) obj;
            return Intrinsics.areEqual(this.token, activateDesk.token) && this.displayId == activateDesk.displayId && this.deskId == activateDesk.deskId && Intrinsics.areEqual(this.onDeskRemovedListener, activateDesk.onDeskRemovedListener);
        }

        @Override // com.android.wm.shell.desktopmode.multidesks.DeskTransition
        public final IBinder getToken() {
            return this.token;
        }

        public final int hashCode() {
            int m = ReorderTile$$ExternalSyntheticOutline0.m(this.deskId, ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, this.token.hashCode() * 31, 31), 31);
            OnDeskRemovedListener onDeskRemovedListener = this.onDeskRemovedListener;
            return m + (onDeskRemovedListener == null ? 0 : onDeskRemovedListener.hashCode());
        }

        public final String toString() {
            return "ActivateDesk(token=" + this.token + ", displayId=" + this.displayId + ", deskId=" + this.deskId + ", onDeskRemovedListener=" + this.onDeskRemovedListener + ")";
        }

        public /* synthetic */ ActivateDesk(IBinder iBinder, int i, int i2, OnDeskRemovedListener onDeskRemovedListener, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(iBinder, i, i2, (i3 & 8) != 0 ? null : onDeskRemovedListener);
        }
    }
}
