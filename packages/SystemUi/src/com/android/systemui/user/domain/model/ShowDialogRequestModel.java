package com.android.systemui.user.domain.model;

import android.os.UserHandle;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.animation.Expandable;
import com.android.systemui.p016qs.user.UserSwitchDialogController;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ShowDialogRequestModel {
    public final UserSwitchDialogController.DialogShower dialogShower;
    public final Expandable expandable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShowAddUserDialog extends ShowDialogRequestModel {
        public final UserSwitchDialogController.DialogShower dialogShower;
        public final boolean isKeyguardShowing;
        public final boolean showEphemeralMessage;
        public final UserHandle userHandle;

        /* JADX WARN: Multi-variable type inference failed */
        public ShowAddUserDialog(UserHandle userHandle, boolean z, boolean z2, UserSwitchDialogController.DialogShower dialogShower) {
            super(dialogShower, null, 2, 0 == true ? 1 : 0);
            this.userHandle = userHandle;
            this.isKeyguardShowing = z;
            this.showEphemeralMessage = z2;
            this.dialogShower = dialogShower;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ShowAddUserDialog)) {
                return false;
            }
            ShowAddUserDialog showAddUserDialog = (ShowAddUserDialog) obj;
            return Intrinsics.areEqual(this.userHandle, showAddUserDialog.userHandle) && this.isKeyguardShowing == showAddUserDialog.isKeyguardShowing && this.showEphemeralMessage == showAddUserDialog.showEphemeralMessage && Intrinsics.areEqual(this.dialogShower, showAddUserDialog.dialogShower);
        }

        @Override // com.android.systemui.user.domain.model.ShowDialogRequestModel
        public final UserSwitchDialogController.DialogShower getDialogShower() {
            return this.dialogShower;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int hashCode = this.userHandle.hashCode() * 31;
            boolean z = this.isKeyguardShowing;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (hashCode + i) * 31;
            boolean z2 = this.showEphemeralMessage;
            int i3 = (i2 + (z2 ? 1 : z2 ? 1 : 0)) * 31;
            UserSwitchDialogController.DialogShower dialogShower = this.dialogShower;
            return i3 + (dialogShower == null ? 0 : dialogShower.hashCode());
        }

        public final String toString() {
            return "ShowAddUserDialog(userHandle=" + this.userHandle + ", isKeyguardShowing=" + this.isKeyguardShowing + ", showEphemeralMessage=" + this.showEphemeralMessage + ", dialogShower=" + this.dialogShower + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShowExitGuestDialog extends ShowDialogRequestModel {
        public final UserSwitchDialogController.DialogShower dialogShower;
        public final int guestUserId;
        public final boolean isGuestEphemeral;
        public final boolean isKeyguardShowing;
        public final Function3 onExitGuestUser;
        public final int targetUserId;

        /* JADX WARN: Multi-variable type inference failed */
        public ShowExitGuestDialog(int i, int i2, boolean z, boolean z2, Function3 function3, UserSwitchDialogController.DialogShower dialogShower) {
            super(dialogShower, null, 2, 0 == true ? 1 : 0);
            this.guestUserId = i;
            this.targetUserId = i2;
            this.isGuestEphemeral = z;
            this.isKeyguardShowing = z2;
            this.onExitGuestUser = function3;
            this.dialogShower = dialogShower;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ShowExitGuestDialog)) {
                return false;
            }
            ShowExitGuestDialog showExitGuestDialog = (ShowExitGuestDialog) obj;
            return this.guestUserId == showExitGuestDialog.guestUserId && this.targetUserId == showExitGuestDialog.targetUserId && this.isGuestEphemeral == showExitGuestDialog.isGuestEphemeral && this.isKeyguardShowing == showExitGuestDialog.isKeyguardShowing && Intrinsics.areEqual(this.onExitGuestUser, showExitGuestDialog.onExitGuestUser) && Intrinsics.areEqual(this.dialogShower, showExitGuestDialog.dialogShower);
        }

        @Override // com.android.systemui.user.domain.model.ShowDialogRequestModel
        public final UserSwitchDialogController.DialogShower getDialogShower() {
            return this.dialogShower;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.targetUserId, Integer.hashCode(this.guestUserId) * 31, 31);
            boolean z = this.isGuestEphemeral;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            int i2 = (m42m + i) * 31;
            boolean z2 = this.isKeyguardShowing;
            int hashCode = (this.onExitGuestUser.hashCode() + ((i2 + (z2 ? 1 : z2 ? 1 : 0)) * 31)) * 31;
            UserSwitchDialogController.DialogShower dialogShower = this.dialogShower;
            return hashCode + (dialogShower == null ? 0 : dialogShower.hashCode());
        }

        public final String toString() {
            return "ShowExitGuestDialog(guestUserId=" + this.guestUserId + ", targetUserId=" + this.targetUserId + ", isGuestEphemeral=" + this.isGuestEphemeral + ", isKeyguardShowing=" + this.isKeyguardShowing + ", onExitGuestUser=" + this.onExitGuestUser + ", dialogShower=" + this.dialogShower + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShowUserCreationDialog extends ShowDialogRequestModel {
        public final boolean isGuest;

        /* JADX WARN: Multi-variable type inference failed */
        public ShowUserCreationDialog(boolean z) {
            super(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
            this.isGuest = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ShowUserCreationDialog) && this.isGuest == ((ShowUserCreationDialog) obj).isGuest;
        }

        public final int hashCode() {
            boolean z = this.isGuest;
            if (z) {
                return 1;
            }
            return z ? 1 : 0;
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(new StringBuilder("ShowUserCreationDialog(isGuest="), this.isGuest, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShowUserSwitcherDialog extends ShowDialogRequestModel {
        public final Expandable expandable;

        /* JADX WARN: Multi-variable type inference failed */
        public ShowUserSwitcherDialog(Expandable expandable) {
            super(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
            this.expandable = expandable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ShowUserSwitcherDialog) {
                return Intrinsics.areEqual(this.expandable, ((ShowUserSwitcherDialog) obj).expandable);
            }
            return false;
        }

        @Override // com.android.systemui.user.domain.model.ShowDialogRequestModel
        public final Expandable getExpandable() {
            return this.expandable;
        }

        public final int hashCode() {
            Expandable expandable = this.expandable;
            if (expandable == null) {
                return 0;
            }
            return expandable.hashCode();
        }

        public final String toString() {
            return "ShowUserSwitcherDialog(expandable=" + this.expandable + ")";
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShowUserSwitcherFullscreenDialog extends ShowDialogRequestModel {
        public final Expandable expandable;

        /* JADX WARN: Multi-variable type inference failed */
        public ShowUserSwitcherFullscreenDialog(Expandable expandable) {
            super(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
            this.expandable = expandable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ShowUserSwitcherFullscreenDialog) {
                return Intrinsics.areEqual(this.expandable, ((ShowUserSwitcherFullscreenDialog) obj).expandable);
            }
            return false;
        }

        @Override // com.android.systemui.user.domain.model.ShowDialogRequestModel
        public final Expandable getExpandable() {
            return this.expandable;
        }

        public final int hashCode() {
            Expandable expandable = this.expandable;
            if (expandable == null) {
                return 0;
            }
            return expandable.hashCode();
        }

        public final String toString() {
            return "ShowUserSwitcherFullscreenDialog(expandable=" + this.expandable + ")";
        }
    }

    public /* synthetic */ ShowDialogRequestModel(UserSwitchDialogController.DialogShower dialogShower, Expandable expandable, DefaultConstructorMarker defaultConstructorMarker) {
        this(dialogShower, expandable);
    }

    public UserSwitchDialogController.DialogShower getDialogShower() {
        return this.dialogShower;
    }

    public Expandable getExpandable() {
        return this.expandable;
    }

    private ShowDialogRequestModel(UserSwitchDialogController.DialogShower dialogShower, Expandable expandable) {
        this.dialogShower = dialogShower;
        this.expandable = expandable;
    }

    public /* synthetic */ ShowDialogRequestModel(UserSwitchDialogController.DialogShower dialogShower, Expandable expandable, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : dialogShower, (i & 2) != 0 ? null : expandable, null);
    }
}
