package com.android.systemui.user.domain.model;

import android.os.UserHandle;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.systemui.animation.Expandable;
import com.android.systemui.qs.user.UserSwitchDialogController;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ShowDialogRequestModel {
    public final UserSwitchDialogController.DialogShower dialogShower;
    public final Expandable expandable;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShowAddUserDialog extends ShowDialogRequestModel {
        public final UserSwitchDialogController.DialogShower dialogShower;
        public final boolean isKeyguardShowing;
        public final boolean showEphemeralMessage;
        public final UserHandle userHandle;

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

        public final int hashCode() {
            int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.userHandle.hashCode() * 31, 31, this.isKeyguardShowing), 31, this.showEphemeralMessage);
            UserSwitchDialogController.DialogShower dialogShower = this.dialogShower;
            return m + (dialogShower == null ? 0 : dialogShower.hashCode());
        }

        public final String toString() {
            return "ShowAddUserDialog(userHandle=" + this.userHandle + ", isKeyguardShowing=" + this.isKeyguardShowing + ", showEphemeralMessage=" + this.showEphemeralMessage + ", dialogShower=" + this.dialogShower + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShowExitGuestDialog extends ShowDialogRequestModel {
        public final UserSwitchDialogController.DialogShower dialogShower;
        public final int guestUserId;
        public final boolean isGuestEphemeral;
        public final boolean isKeyguardShowing;
        public final Function3 onExitGuestUser;
        public final int targetUserId;

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

        public final int hashCode() {
            int hashCode = (this.onExitGuestUser.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.targetUserId, Integer.hashCode(this.guestUserId) * 31, 31), 31, this.isGuestEphemeral), 31, this.isKeyguardShowing)) * 31;
            UserSwitchDialogController.DialogShower dialogShower = this.dialogShower;
            return hashCode + (dialogShower == null ? 0 : dialogShower.hashCode());
        }

        public final String toString() {
            return "ShowExitGuestDialog(guestUserId=" + this.guestUserId + ", targetUserId=" + this.targetUserId + ", isGuestEphemeral=" + this.isGuestEphemeral + ", isKeyguardShowing=" + this.isKeyguardShowing + ", onExitGuestUser=" + this.onExitGuestUser + ", dialogShower=" + this.dialogShower + ")";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShowUserCreationDialog extends ShowDialogRequestModel {
        public final boolean isGuest;

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
            return Boolean.hashCode(this.isGuest);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("ShowUserCreationDialog(isGuest="), this.isGuest, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShowUserSwitcherDialog extends ShowDialogRequestModel {
        public final Expandable expandable;

        public ShowUserSwitcherDialog(Expandable expandable) {
            super(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
            this.expandable = expandable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ShowUserSwitcherDialog) && Intrinsics.areEqual(this.expandable, ((ShowUserSwitcherDialog) obj).expandable);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ShowUserSwitcherFullscreenDialog extends ShowDialogRequestModel {
        public final Expandable expandable;

        public ShowUserSwitcherFullscreenDialog(Expandable expandable) {
            super(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
            this.expandable = expandable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ShowUserSwitcherFullscreenDialog) && Intrinsics.areEqual(this.expandable, ((ShowUserSwitcherFullscreenDialog) obj).expandable);
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
