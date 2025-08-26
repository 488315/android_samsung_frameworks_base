package com.android.systemui.statusbar.phone;

import android.content.res.Resources;
import android.os.Handler;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.policy.LocationController;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class PhoneStatusBarPolicyExt {
    public final String SLOT_NAME_LOCATION;
    public final Handler handler;
    public final StatusBarIconController iconController;
    public boolean isTimeToEnsureLocationIconDisplay;
    public final LocationController locationController;
    public final PhoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1 turnOffTimeToEnsureLocationIconDisplay = new Runnable() { // from class: com.android.systemui.statusbar.phone.PhoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1
        @Override // java.lang.Runnable
        public final void run() {
            this.this$0.isTimeToEnsureLocationIconDisplay = false;
        }
    };
    public final PhoneStatusBarPolicyExt$removeLocationIconRunnable$1 removeLocationIconRunnable = new PhoneStatusBarPolicyExt$removeLocationIconRunnable$1(this);

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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.statusbar.phone.PhoneStatusBarPolicyExt$turnOffTimeToEnsureLocationIconDisplay$1] */
    public PhoneStatusBarPolicyExt(Handler handler, Resources resources, LocationController locationController, StatusBarIconController statusBarIconController) {
        this.handler = handler;
        this.locationController = locationController;
        this.iconController = statusBarIconController;
        this.SLOT_NAME_LOCATION = resources.getString(17043284);
    }
}
