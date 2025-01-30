package com.android.systemui.p016qs.footer.data.model;

import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class UserSwitcherStatusModel {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Disabled extends UserSwitcherStatusModel {
        public static final Disabled INSTANCE = new Disabled();

        private Disabled() {
            super(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            String str = this.currentUserName;
            int hashCode = (str == null ? 0 : str.hashCode()) * 31;
            Drawable drawable = this.currentUserImage;
            int hashCode2 = (hashCode + (drawable != null ? drawable.hashCode() : 0)) * 31;
            boolean z = this.isGuestUser;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode2 + i;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Enabled(currentUserName=");
            sb.append(this.currentUserName);
            sb.append(", currentUserImage=");
            sb.append(this.currentUserImage);
            sb.append(", isGuestUser=");
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isGuestUser, ")");
        }
    }

    private UserSwitcherStatusModel() {
    }

    public /* synthetic */ UserSwitcherStatusModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
