package com.android.systemui.biometrics.domain.interactor;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.util.time.SystemClock;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
