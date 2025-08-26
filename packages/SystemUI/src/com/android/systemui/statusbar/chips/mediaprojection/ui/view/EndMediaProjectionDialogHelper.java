package com.android.systemui.statusbar.chips.mediaprojection.ui.view;

import android.content.pm.PackageManager;
import android.util.Log;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class EndMediaProjectionDialogHelper {
    public final SystemUIDialog.Factory dialogFactory;
    public final DialogTransitionAnimator dialogTransitionAnimator;
    public final PackageManager packageManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public EndMediaProjectionDialogHelper(SystemUIDialog.Factory factory, DialogTransitionAnimator dialogTransitionAnimator, PackageManager packageManager) {
        this.dialogFactory = factory;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
        this.packageManager = packageManager;
    }

    public final CharSequence getAppName(String str) {
        try {
            return this.packageManager.getApplicationInfo(str, 0).loadLabel(this.packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("EndMediaProjectionDialogHelper", "Failed to find application info for package: " + str + " when creating end media projection dialog", e);
            return null;
        }
    }
}
