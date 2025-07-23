package com.android.systemui.mediaprojection.devicepolicy;

import android.app.admin.DevicePolicyManager;
import android.os.UserHandle;
import android.os.UserManager;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ScreenCaptureDevicePolicyResolver {
    public final DevicePolicyManager devicePolicyManager;
    public final Lazy disallowSharingIntoManagedProfile$delegate;
    public final Lazy personalProfileScreenCaptureDisabled$delegate;
    public final UserHandle personalProfileUserHandle;
    public final UserManager userManager;
    public final Lazy workProfileScreenCaptureDisabled$delegate;
    public final UserHandle workProfileUserHandle;

    public ScreenCaptureDevicePolicyResolver(DevicePolicyManager devicePolicyManager, UserManager userManager, UserHandle userHandle, UserHandle userHandle2) {
        this.devicePolicyManager = devicePolicyManager;
        this.userManager = userManager;
        this.personalProfileUserHandle = userHandle;
        this.workProfileUserHandle = userHandle2;
        final int i = 0;
        this.personalProfileScreenCaptureDisabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver$$ExternalSyntheticLambda0
            public final /* synthetic */ ScreenCaptureDevicePolicyResolver f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver = this.f$0;
                        return Boolean.valueOf(screenCaptureDevicePolicyResolver.devicePolicyManager.getScreenCaptureDisabled(null, screenCaptureDevicePolicyResolver.personalProfileUserHandle.getIdentifier()));
                    case 1:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver2 = this.f$0;
                        UserHandle userHandle3 = screenCaptureDevicePolicyResolver2.workProfileUserHandle;
                        return Boolean.valueOf(userHandle3 != null ? screenCaptureDevicePolicyResolver2.devicePolicyManager.getScreenCaptureDisabled(null, userHandle3.getIdentifier()) : false);
                    default:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver3 = this.f$0;
                        UserHandle userHandle4 = screenCaptureDevicePolicyResolver3.workProfileUserHandle;
                        return Boolean.valueOf(userHandle4 != null ? screenCaptureDevicePolicyResolver3.userManager.hasUserRestrictionForUser("no_sharing_into_profile", userHandle4) : false);
                }
            }
        });
        final int i2 = 1;
        this.workProfileScreenCaptureDisabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver$$ExternalSyntheticLambda0
            public final /* synthetic */ ScreenCaptureDevicePolicyResolver f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver = this.f$0;
                        return Boolean.valueOf(screenCaptureDevicePolicyResolver.devicePolicyManager.getScreenCaptureDisabled(null, screenCaptureDevicePolicyResolver.personalProfileUserHandle.getIdentifier()));
                    case 1:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver2 = this.f$0;
                        UserHandle userHandle3 = screenCaptureDevicePolicyResolver2.workProfileUserHandle;
                        return Boolean.valueOf(userHandle3 != null ? screenCaptureDevicePolicyResolver2.devicePolicyManager.getScreenCaptureDisabled(null, userHandle3.getIdentifier()) : false);
                    default:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver3 = this.f$0;
                        UserHandle userHandle4 = screenCaptureDevicePolicyResolver3.workProfileUserHandle;
                        return Boolean.valueOf(userHandle4 != null ? screenCaptureDevicePolicyResolver3.userManager.hasUserRestrictionForUser("no_sharing_into_profile", userHandle4) : false);
                }
            }
        });
        final int i3 = 2;
        this.disallowSharingIntoManagedProfile$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.mediaprojection.devicepolicy.ScreenCaptureDevicePolicyResolver$$ExternalSyntheticLambda0
            public final /* synthetic */ ScreenCaptureDevicePolicyResolver f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver = this.f$0;
                        return Boolean.valueOf(screenCaptureDevicePolicyResolver.devicePolicyManager.getScreenCaptureDisabled(null, screenCaptureDevicePolicyResolver.personalProfileUserHandle.getIdentifier()));
                    case 1:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver2 = this.f$0;
                        UserHandle userHandle3 = screenCaptureDevicePolicyResolver2.workProfileUserHandle;
                        return Boolean.valueOf(userHandle3 != null ? screenCaptureDevicePolicyResolver2.devicePolicyManager.getScreenCaptureDisabled(null, userHandle3.getIdentifier()) : false);
                    default:
                        ScreenCaptureDevicePolicyResolver screenCaptureDevicePolicyResolver3 = this.f$0;
                        UserHandle userHandle4 = screenCaptureDevicePolicyResolver3.workProfileUserHandle;
                        return Boolean.valueOf(userHandle4 != null ? screenCaptureDevicePolicyResolver3.userManager.hasUserRestrictionForUser("no_sharing_into_profile", userHandle4) : false);
                }
            }
        });
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
        return (userHandle2 == null || !isScreenCaptureAllowed(userHandle2, userHandle)) && !isScreenCaptureAllowed(this.personalProfileUserHandle, userHandle);
    }
}
