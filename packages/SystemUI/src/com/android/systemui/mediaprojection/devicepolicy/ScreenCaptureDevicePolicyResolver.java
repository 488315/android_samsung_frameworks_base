package com.android.systemui.mediaprojection.devicepolicy;

import android.app.admin.DevicePolicyManager;
import android.os.UserHandle;
import android.os.UserManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenCaptureDevicePolicyResolver {
    public final DevicePolicyManager devicePolicyManager;
    public final UserHandle personalProfileUserHandle;
    public final UserManager userManager;
    public final UserHandle workProfileUserHandle;
    public final Lazy personalProfileScreenCaptureDisabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver$personalProfileScreenCaptureDisabled$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver = ScreenCaptureDevicePolicyResolver.this;
            return Boolean.valueOf(screenCaptureDevicePolicyResolver.devicePolicyManager.getScreenCaptureDisabled(null, screenCaptureDevicePolicyResolver.personalProfileUserHandle.getIdentifier()));
        }
    });
    public final Lazy workProfileScreenCaptureDisabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver$workProfileScreenCaptureDisabled$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver = ScreenCaptureDevicePolicyResolver.this;
            UserHandle userHandle = screenCaptureDevicePolicyResolver.workProfileUserHandle;
            return Boolean.valueOf(userHandle != null ? screenCaptureDevicePolicyResolver.devicePolicyManager.getScreenCaptureDisabled(null, userHandle.getIdentifier()) : false);
        }
    });
    public final Lazy disallowSharingIntoManagedProfile$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver$disallowSharingIntoManagedProfile$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver = ScreenCaptureDevicePolicyResolver.this;
            UserHandle userHandle = screenCaptureDevicePolicyResolver.workProfileUserHandle;
            return Boolean.valueOf(userHandle != null ? screenCaptureDevicePolicyResolver.userManager.hasUserRestrictionForUser("no_sharing_into_profile", userHandle) : false);
        }
    });

    public ScreenCaptureDevicePolicyResolver(DevicePolicyManager devicePolicyManager, UserManager userManager, UserHandle userHandle, UserHandle userHandle2) {
        this.devicePolicyManager = devicePolicyManager;
        this.userManager = userManager;
        this.personalProfileUserHandle = userHandle;
        this.workProfileUserHandle = userHandle2;
    }

    public final boolean isScreenCaptureAllowed(UserHandle userHandle, UserHandle userHandle2) {
        boolean areEqual = Intrinsics.areEqual(userHandle2, this.workProfileUserHandle);
        Lazy lazy = this.workProfileScreenCaptureDisabled$delegate;
        if (areEqual && ((Boolean) lazy.getValue()).booleanValue()) {
            return false;
        }
        Lazy lazy2 = this.personalProfileScreenCaptureDisabled$delegate;
        if (((Boolean) lazy2.getValue()).booleanValue()) {
            return false;
        }
        return Intrinsics.areEqual(userHandle, this.workProfileUserHandle) ? !((Boolean) lazy.getValue()).booleanValue() : ((Intrinsics.areEqual(userHandle2, this.workProfileUserHandle) && ((Boolean) this.disallowSharingIntoManagedProfile$delegate.getValue()).booleanValue()) || ((Boolean) lazy2.getValue()).booleanValue()) ? false : true;
    }

    public final boolean isScreenCaptureCompletelyDisabled(UserHandle userHandle) {
        UserHandle userHandle2 = this.workProfileUserHandle;
        return (userHandle2 == null || !isScreenCaptureAllowed(userHandle2, userHandle)) && (isScreenCaptureAllowed(this.personalProfileUserHandle, userHandle) ^ true);
    }
}
