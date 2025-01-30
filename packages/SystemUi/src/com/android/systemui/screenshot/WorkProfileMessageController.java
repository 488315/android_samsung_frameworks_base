package com.android.systemui.screenshot;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.UserManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WorkProfileMessageController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final PackageManager packageManager;
    public final UserManager userManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WorkProfileFirstRunData {
        public final CharSequence appName;
        public final Drawable icon;

        public WorkProfileFirstRunData(CharSequence charSequence, Drawable drawable) {
            this.appName = charSequence;
            this.icon = drawable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WorkProfileFirstRunData)) {
                return false;
            }
            WorkProfileFirstRunData workProfileFirstRunData = (WorkProfileFirstRunData) obj;
            return Intrinsics.areEqual(this.appName, workProfileFirstRunData.appName) && Intrinsics.areEqual(this.icon, workProfileFirstRunData.icon);
        }

        public final int hashCode() {
            int hashCode = this.appName.hashCode() * 31;
            Drawable drawable = this.icon;
            return hashCode + (drawable == null ? 0 : drawable.hashCode());
        }

        public final String toString() {
            return "WorkProfileFirstRunData(appName=" + ((Object) this.appName) + ", icon=" + this.icon + ")";
        }
    }

    static {
        new Companion(null);
    }

    public WorkProfileMessageController(Context context, UserManager userManager, PackageManager packageManager) {
        this.context = context;
        this.userManager = userManager;
        this.packageManager = packageManager;
    }
}
