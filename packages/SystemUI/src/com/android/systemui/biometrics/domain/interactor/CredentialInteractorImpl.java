package com.android.systemui.biometrics.domain.interactor;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.util.time.SystemClock;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CredentialInteractorImpl implements CredentialInteractor {
    public final Context applicationContext;
    public final DevicePolicyManager devicePolicyManager;
    public final LockPatternUtils lockPatternUtils;
    public final SystemClock systemClock;
    public final UserManager userManager;

    public CredentialInteractorImpl(Context context, LockPatternUtils lockPatternUtils, UserManager userManager, DevicePolicyManager devicePolicyManager, SystemClock systemClock) {
        this.applicationContext = context;
        this.lockPatternUtils = lockPatternUtils;
        this.userManager = userManager;
        this.devicePolicyManager = devicePolicyManager;
        this.systemClock = systemClock;
    }
}
