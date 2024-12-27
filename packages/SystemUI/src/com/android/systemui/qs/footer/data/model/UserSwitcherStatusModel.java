package com.android.systemui.qs.footer.data.model;

import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class UserSwitcherStatusModel {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Disabled extends UserSwitcherStatusModel {
        public static final Disabled INSTANCE = new Disabled();

        private Disabled() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Enabled extends UserSwitcherStatusModel {
        public final Drawable currentUserImage;
        public final String currentUserName;
        public final boolean isGuestUser;

        public Enabled(String str, Drawable drawable, boolean z) {
            super(null);
            this.currentUserName = str;
            this.currentUserImage = drawable;
            this.isGuestUser = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Enabled)) {
                return false;
            }
            Enabled enabled = (Enabled) obj;
            return Intrinsics.areEqual(this.currentUserName, enabled.currentUserName) && Intrinsics.areEqual(this.currentUserImage, enabled.currentUserImage) && this.isGuestUser == enabled.isGuestUser;
        }

        public final int hashCode() {
            String str = this.currentUserName;
            int hashCode = (str == null ? 0 : str.hashCode()) * 31;
            Drawable drawable = this.currentUserImage;
            return Boolean.hashCode(this.isGuestUser) + ((hashCode + (drawable != null ? drawable.hashCode() : 0)) * 31);
        }

        public final String toString() {
            Drawable drawable = this.currentUserImage;
            StringBuilder sb = new StringBuilder("Enabled(currentUserName=");
            sb.append(this.currentUserName);
            sb.append(", currentUserImage=");
            sb.append(drawable);
            sb.append(", isGuestUser=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isGuestUser, ")");
        }
    }

    private UserSwitcherStatusModel() {
    }

    public /* synthetic */ UserSwitcherStatusModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
