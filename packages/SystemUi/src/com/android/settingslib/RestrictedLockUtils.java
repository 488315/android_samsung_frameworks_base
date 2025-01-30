package com.android.settingslib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import com.samsung.android.knox.EnterpriseDeviceManager;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class RestrictedLockUtils {
    public static Intent getShowAdminSupportDetailsIntent(EnforcedAdmin enforcedAdmin) {
        Intent intent = new Intent("android.settings.SHOW_ADMIN_SUPPORT_DETAILS");
        if (enforcedAdmin != null) {
            ComponentName componentName = enforcedAdmin.component;
            if (componentName != null) {
                intent.putExtra(EnterpriseDeviceManager.EXTRA_DEVICE_ADMIN, componentName);
            }
            intent.putExtra("android.intent.extra.USER", enforcedAdmin.user);
        }
        return intent;
    }

    public static void sendShowAdminSupportDetailsIntent(Context context, EnforcedAdmin enforcedAdmin) {
        Intent showAdminSupportDetailsIntent = getShowAdminSupportDetailsIntent(enforcedAdmin);
        int myUserId = UserHandle.myUserId();
        if (enforcedAdmin != null) {
            UserHandle userHandle = enforcedAdmin.user;
            if (userHandle != null) {
                if (((UserManager) context.getSystemService(UserManager.class)).getUserProfiles().contains(UserHandle.of(userHandle.getIdentifier()))) {
                    myUserId = enforcedAdmin.user.getIdentifier();
                }
            }
            showAdminSupportDetailsIntent.putExtra("android.app.extra.RESTRICTION", enforcedAdmin.enforcedRestriction);
        }
        context.startActivityAsUser(showAdminSupportDetailsIntent, UserHandle.of(myUserId));
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EnforcedAdmin {
        public final ComponentName component;
        public String enforcedRestriction;
        public UserHandle user;

        static {
            new EnforcedAdmin();
        }

        public EnforcedAdmin(ComponentName componentName, UserHandle userHandle) {
            this.component = null;
            this.enforcedRestriction = null;
            this.component = componentName;
            this.user = userHandle;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || EnforcedAdmin.class != obj.getClass()) {
                return false;
            }
            EnforcedAdmin enforcedAdmin = (EnforcedAdmin) obj;
            return Objects.equals(this.user, enforcedAdmin.user) && Objects.equals(this.component, enforcedAdmin.component) && Objects.equals(this.enforcedRestriction, enforcedAdmin.enforcedRestriction);
        }

        public final int hashCode() {
            return Objects.hash(this.component, this.enforcedRestriction, this.user);
        }

        public final String toString() {
            return "EnforcedAdmin{component=" + this.component + ", enforcedRestriction='" + this.enforcedRestriction + ", user=" + this.user + '}';
        }

        public EnforcedAdmin(ComponentName componentName, String str, UserHandle userHandle) {
            this.component = null;
            this.component = componentName;
            this.enforcedRestriction = str;
            this.user = userHandle;
        }

        public EnforcedAdmin(EnforcedAdmin enforcedAdmin) {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
            if (enforcedAdmin != null) {
                this.component = enforcedAdmin.component;
                this.enforcedRestriction = enforcedAdmin.enforcedRestriction;
                this.user = enforcedAdmin.user;
                return;
            }
            throw new IllegalArgumentException();
        }

        public EnforcedAdmin() {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
        }
    }
}
