package com.android.systemui.biometrics.domain.interactor;

import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.content.pm.UserInfo;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.biometrics.shared.model.BiometricUserInfo;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes.dex */
final class CredentialInteractorImpl$verifyCredential$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LockscreenCredential $credential;
    final /* synthetic */ BiometricPromptRequest.Credential $request;
    int I$0;
    long J$0;
    long J$1;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CredentialInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CredentialInteractorImpl$verifyCredential$1(BiometricPromptRequest.Credential credential, CredentialInteractorImpl credentialInteractorImpl, LockscreenCredential lockscreenCredential, Continuation continuation) {
        super(2, continuation);
        this.$request = credential;
        this.this$0 = credentialInteractorImpl;
        this.$credential = lockscreenCredential;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CredentialInteractorImpl$verifyCredential$1 credentialInteractorImpl$verifyCredential$1 = new CredentialInteractorImpl$verifyCredential$1(this.$request, this.this$0, this.$credential, continuation);
        credentialInteractorImpl$verifyCredential$1.L$0 = obj;
        return credentialInteractorImpl$verifyCredential$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CredentialInteractorImpl$verifyCredential$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:117:0x0271, code lost:
    
        if (r2.emit(r11, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0274, code lost:
    
        r1 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0289, code lost:
    
        if (r2.emit(r8, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x009f, code lost:
    
        if (r2.emit(r4, r16) == r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0108, code lost:
    
        if (kotlinx.coroutines.DelayKt.delay(r8, r16) != r1) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0122, code lost:
    
        if (r2.emit(r10, r16) == r1) goto L122;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x010e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x0108 -> B:34:0x010c). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        int i;
        String str;
        int i2;
        long lockoutAttemptDeadline;
        long j;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        String string = null;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                BiometricUserInfo biometricUserInfo = this.$request.userInfo;
                int i3 = biometricUserInfo.deviceCredentialOwnerId;
                int i4 = biometricUserInfo.userId;
                VerifyCredentialResponse verifyCredentialResponseVerifyTiedProfileChallenge = i3 != i4 ? this.this$0.lockPatternUtils.verifyTiedProfileChallenge(this.$credential, i4, 1) : this.this$0.lockPatternUtils.verifyCredential(this.$credential, i3, 1);
                verifyCredentialResponseVerifyTiedProfileChallenge.getClass();
                if (verifyCredentialResponseVerifyTiedProfileChallenge.isMatched()) {
                    this.this$0.lockPatternUtils.userPresent(i3);
                    long gatekeeperPasswordHandle = verifyCredentialResponseVerifyTiedProfileChallenge.getGatekeeperPasswordHandle();
                    LockPatternUtils lockPatternUtils = this.this$0.lockPatternUtils;
                    BiometricPromptRequest.Credential credential = this.$request;
                    byte[] gatekeeperHAT = lockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, credential.operationInfo.gatekeeperChallenge, credential.userInfo.userId).getGatekeeperHAT();
                    this.this$0.lockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
                    if (gatekeeperHAT == null) {
                        throw new IllegalStateException("Required value was null.");
                    }
                    CredentialStatus$Success$Verified credentialStatus$Success$Verified = new CredentialStatus$Success$Verified(gatekeeperHAT);
                    this.label = 1;
                    break;
                } else {
                    if (verifyCredentialResponseVerifyTiedProfileChallenge.getTimeout() <= 0) {
                        int currentFailedPasswordAttempts = this.this$0.lockPatternUtils.getCurrentFailedPasswordAttempts(i3) + 1;
                        int maximumFailedPasswordsForWipe = this.this$0.lockPatternUtils.getMaximumFailedPasswordsForWipe(i3);
                        if (maximumFailedPasswordsForWipe > 0 && currentFailedPasswordAttempts > 0) {
                            int i5 = maximumFailedPasswordsForWipe - currentFailedPasswordAttempts;
                            if (i5 < 0) {
                                i5 = 0;
                            }
                            String string2 = this.this$0.applicationContext.getString(R.string.biometric_dialog_credential_attempts_before_wipe, new Integer(currentFailedPasswordAttempts), new Integer(maximumFailedPasswordsForWipe));
                            Integer num = new Integer(i5);
                            CredentialInteractorImpl credentialInteractorImpl = this.this$0;
                            final BiometricPromptRequest.Credential credential2 = this.$request;
                            Integer num2 = new Integer(i5);
                            credentialInteractorImpl.getClass();
                            if (num2.intValue() <= 1) {
                                final Context context = credentialInteractorImpl.applicationContext;
                                DevicePolicyManager devicePolicyManager = credentialInteractorImpl.devicePolicyManager;
                                UserInfo userInfo = credentialInteractorImpl.userManager.getUserInfo(devicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(credential2.userInfo.deviceCredentialOwnerId));
                                final UserType userType = (userInfo == null || userInfo.isPrimary()) ? UserType.PRIMARY : userInfo.isManagedProfile() ? UserType.MANAGED_PROFILE : UserType.SECONDARY;
                                int iIntValue = num2.intValue();
                                if (iIntValue == 1) {
                                    int i6 = CredentialInteractorKt$WhenMappings.$EnumSwitchMapping$0[userType.ordinal()];
                                    if (i6 == 1) {
                                        if (credential2 instanceof BiometricPromptRequest.Credential.Pin) {
                                            i = R.string.biometric_dialog_last_pin_attempt_before_wipe_device;
                                        } else if (credential2 instanceof BiometricPromptRequest.Credential.Pattern) {
                                            i = R.string.biometric_dialog_last_pattern_attempt_before_wipe_device;
                                        } else {
                                            if (!(credential2 instanceof BiometricPromptRequest.Credential.Password)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            i = R.string.biometric_dialog_last_password_attempt_before_wipe_device;
                                        }
                                        string = context.getString(i);
                                    } else if (i6 == 2) {
                                        if (credential2 instanceof BiometricPromptRequest.Credential.Pin) {
                                            str = "SystemUi.BIOMETRIC_DIALOG_WORK_PIN_LAST_ATTEMPT";
                                        } else if (credential2 instanceof BiometricPromptRequest.Credential.Pattern) {
                                            str = "SystemUi.BIOMETRIC_DIALOG_WORK_PATTERN_LAST_ATTEMPT";
                                        } else {
                                            if (!(credential2 instanceof BiometricPromptRequest.Credential.Password)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            str = "SystemUi.BIOMETRIC_DIALOG_WORK_PASSWORD_LAST_ATTEMPT";
                                        }
                                        final int i7 = 1;
                                        final Function0 function0 = new Function0() { // from class: com.android.systemui.biometrics.domain.interactor.CredentialInteractorKt$$ExternalSyntheticLambda1
                                            @Override // kotlin.jvm.functions.Function0
                                            public final Object invoke() {
                                                int i8;
                                                int i9;
                                                switch (i7) {
                                                    case 0:
                                                        Context context2 = context;
                                                        int i10 = CredentialInteractorKt$WhenMappings.$EnumSwitchMapping$0[((UserType) credential2).ordinal()];
                                                        if (i10 == 1) {
                                                            i8 = R.string.failed_attempts_now_wiping_device;
                                                        } else if (i10 == 2) {
                                                            i8 = R.string.failed_attempts_now_wiping_profile;
                                                        } else {
                                                            if (i10 != 3) {
                                                                throw new NoWhenBranchMatchedException();
                                                            }
                                                            i8 = R.string.failed_attempts_now_wiping_user;
                                                        }
                                                        return context2.getString(i8);
                                                    default:
                                                        Context context3 = context;
                                                        BiometricPromptRequest.Credential credential3 = (BiometricPromptRequest.Credential) credential2;
                                                        if (credential3 instanceof BiometricPromptRequest.Credential.Pin) {
                                                            i9 = R.string.biometric_dialog_last_pin_attempt_before_wipe_profile;
                                                        } else if (credential3 instanceof BiometricPromptRequest.Credential.Pattern) {
                                                            i9 = R.string.biometric_dialog_last_pattern_attempt_before_wipe_profile;
                                                        } else {
                                                            if (!(credential3 instanceof BiometricPromptRequest.Credential.Password)) {
                                                                throw new NoWhenBranchMatchedException();
                                                            }
                                                            i9 = R.string.biometric_dialog_last_password_attempt_before_wipe_profile;
                                                        }
                                                        return context3.getString(i9);
                                                }
                                            }
                                        };
                                        DevicePolicyResourcesManager resources = devicePolicyManager.getResources();
                                        if (resources == null || (string = resources.getString(str, new Supplier() { // from class: com.android.systemui.biometrics.domain.interactor.CredentialInteractorKt$sam$java_util_function_Supplier$0
                                            @Override // java.util.function.Supplier
                                            public final /* synthetic */ Object get() {
                                                return function0.invoke();
                                            }
                                        })) == null) {
                                            string = (String) function0.invoke();
                                        }
                                    } else {
                                        if (i6 != 3) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        if (credential2 instanceof BiometricPromptRequest.Credential.Pin) {
                                            i2 = R.string.biometric_dialog_last_pin_attempt_before_wipe_user;
                                        } else if (credential2 instanceof BiometricPromptRequest.Credential.Pattern) {
                                            i2 = R.string.biometric_dialog_last_pattern_attempt_before_wipe_user;
                                        } else {
                                            if (!(credential2 instanceof BiometricPromptRequest.Credential.Password)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            i2 = R.string.biometric_dialog_last_password_attempt_before_wipe_user;
                                        }
                                        string = context.getString(i2);
                                    }
                                } else if (iIntValue <= 0) {
                                    String str2 = CredentialInteractorKt$WhenMappings.$EnumSwitchMapping$0[userType.ordinal()] == 2 ? "SystemUi.BIOMETRIC_DIALOG_WORK_LOCK_FAILED_ATTEMPTS" : PeripheralBarcodeConstants.Symbology.UNDEFINED;
                                    final int i8 = 0;
                                    final Function0 function02 = new Function0() { // from class: com.android.systemui.biometrics.domain.interactor.CredentialInteractorKt$$ExternalSyntheticLambda1
                                        @Override // kotlin.jvm.functions.Function0
                                        public final Object invoke() {
                                            int i82;
                                            int i9;
                                            switch (i8) {
                                                case 0:
                                                    Context context2 = context;
                                                    int i10 = CredentialInteractorKt$WhenMappings.$EnumSwitchMapping$0[((UserType) userType).ordinal()];
                                                    if (i10 == 1) {
                                                        i82 = R.string.failed_attempts_now_wiping_device;
                                                    } else if (i10 == 2) {
                                                        i82 = R.string.failed_attempts_now_wiping_profile;
                                                    } else {
                                                        if (i10 != 3) {
                                                            throw new NoWhenBranchMatchedException();
                                                        }
                                                        i82 = R.string.failed_attempts_now_wiping_user;
                                                    }
                                                    return context2.getString(i82);
                                                default:
                                                    Context context3 = context;
                                                    BiometricPromptRequest.Credential credential3 = (BiometricPromptRequest.Credential) userType;
                                                    if (credential3 instanceof BiometricPromptRequest.Credential.Pin) {
                                                        i9 = R.string.biometric_dialog_last_pin_attempt_before_wipe_profile;
                                                    } else if (credential3 instanceof BiometricPromptRequest.Credential.Pattern) {
                                                        i9 = R.string.biometric_dialog_last_pattern_attempt_before_wipe_profile;
                                                    } else {
                                                        if (!(credential3 instanceof BiometricPromptRequest.Credential.Password)) {
                                                            throw new NoWhenBranchMatchedException();
                                                        }
                                                        i9 = R.string.biometric_dialog_last_password_attempt_before_wipe_profile;
                                                    }
                                                    return context3.getString(i9);
                                            }
                                        }
                                    };
                                    DevicePolicyResourcesManager resources2 = devicePolicyManager.getResources();
                                    if (resources2 == null || (string = resources2.getString(str2, new Supplier() { // from class: com.android.systemui.biometrics.domain.interactor.CredentialInteractorKt$sam$java_util_function_Supplier$0
                                        @Override // java.util.function.Supplier
                                        public final /* synthetic */ Object get() {
                                            return function02.invoke();
                                        }
                                    })) == null) {
                                        string = (String) function02.invoke();
                                    }
                                } else {
                                    string = "";
                                }
                            }
                            CredentialStatus.Fail.Error error = new CredentialStatus.Fail.Error(string2, num, string);
                            this.I$0 = i3;
                            this.label = 6;
                            break;
                        } else {
                            CredentialStatus.Fail.Error error2 = new CredentialStatus.Fail.Error(null, null, null, 7, null);
                            this.I$0 = i3;
                            this.label = 5;
                            break;
                        }
                        this.this$0.lockPatternUtils.reportFailedPasswordAttempt(i);
                        return Unit.INSTANCE;
                    }
                    lockoutAttemptDeadline = this.this$0.lockPatternUtils.setLockoutAttemptDeadline(i3, verifyCredentialResponseVerifyTiedProfileChallenge.getTimeout()) - this.this$0.systemClock.elapsedRealtime();
                    j = 1000;
                    if (lockoutAttemptDeadline > 0) {
                        CredentialStatus.Fail.Error error3 = new CredentialStatus.Fail.Error("", null, null, 6, null);
                        this.L$0 = null;
                        this.label = 4;
                        break;
                    } else {
                        CredentialStatus.Fail.Throttled throttled = new CredentialStatus.Fail.Throttled(this.this$0.applicationContext.getString(R.string.biometric_dialog_credential_too_many_attempts, new Long(lockoutAttemptDeadline / 1000)));
                        this.L$0 = flowCollector;
                        this.J$0 = j;
                        this.J$1 = lockoutAttemptDeadline;
                        this.label = 2;
                        if (flowCollector.emit(throttled, this) != coroutineSingletons) {
                            this.L$0 = flowCollector;
                            this.J$0 = j;
                            this.J$1 = lockoutAttemptDeadline;
                            this.label = 3;
                            break;
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
            case 4:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 2:
                lockoutAttemptDeadline = this.J$1;
                j = this.J$0;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                this.L$0 = flowCollector;
                this.J$0 = j;
                this.J$1 = lockoutAttemptDeadline;
                this.label = 3;
                break;
            case 3:
                lockoutAttemptDeadline = this.J$1;
                j = this.J$0;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                lockoutAttemptDeadline -= j;
                if (lockoutAttemptDeadline > 0) {
                }
                return coroutineSingletons;
            case 5:
            case 6:
                int i9 = this.I$0;
                ResultKt.throwOnFailure(obj);
                this.this$0.lockPatternUtils.reportFailedPasswordAttempt(i9);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
