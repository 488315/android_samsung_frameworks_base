package com.android.systemui.biometrics.domain.interactor;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.SystemClock;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.systemui.R;
import com.android.systemui.biometrics.domain.interactor.CredentialStatus;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import com.android.systemui.util.time.SystemClockImpl;
import com.samsung.android.knox.p045ex.peripheral.PeripheralBarcodeConstants;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.domain.interactor.CredentialInteractorImpl$verifyCredential$1", m277f = "CredentialInteractor.kt", m278l = {92, 101, 109, 112, 118, 121}, m279m = "invokeSuspend")
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
    public CredentialInteractorImpl$verifyCredential$1(BiometricPromptRequest.Credential credential, CredentialInteractorImpl credentialInteractorImpl, LockscreenCredential lockscreenCredential, Continuation<? super CredentialInteractorImpl$verifyCredential$1> continuation) {
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ee A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00f1  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x00ec -> B:11:0x00ef). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        int i;
        int i2;
        String str;
        int i3;
        long elapsedRealtime;
        long j;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        String str2 = null;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                flowCollector = (FlowCollector) this.L$0;
                int i4 = this.$request.userInfo.deviceCredentialOwnerId;
                VerifyCredentialResponse verifyCredential = this.this$0.lockPatternUtils.verifyCredential(this.$credential, i4, 1);
                if (verifyCredential.isMatched()) {
                    this.this$0.lockPatternUtils.userPresent(i4);
                    long gatekeeperPasswordHandle = verifyCredential.getGatekeeperPasswordHandle();
                    byte[] gatekeeperHAT = this.this$0.lockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, this.$request.operationInfo.gatekeeperChallenge, i4).getGatekeeperHAT();
                    this.this$0.lockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
                    CredentialStatus$Success$Verified credentialStatus$Success$Verified = new CredentialStatus$Success$Verified(gatekeeperHAT);
                    this.label = 1;
                    if (flowCollector.emit(credentialStatus$Success$Verified, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                if (verifyCredential.getTimeout() <= 0) {
                    int currentFailedPasswordAttempts = this.this$0.lockPatternUtils.getCurrentFailedPasswordAttempts(i4) + 1;
                    int maximumFailedPasswordsForWipe = this.this$0.lockPatternUtils.getMaximumFailedPasswordsForWipe(i4);
                    if (maximumFailedPasswordsForWipe <= 0 || currentFailedPasswordAttempts <= 0) {
                        CredentialStatus.Fail.Error error = new CredentialStatus.Fail.Error(null, null, null, 7, null);
                        this.I$0 = i4;
                        this.label = 5;
                        if (flowCollector.emit(error, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        int i5 = maximumFailedPasswordsForWipe - currentFailedPasswordAttempts;
                        if (i5 < 0) {
                            i5 = 0;
                        }
                        String string = this.this$0.applicationContext.getString(R.string.biometric_dialog_credential_attempts_before_wipe, new Integer(currentFailedPasswordAttempts), new Integer(maximumFailedPasswordsForWipe));
                        Integer num = new Integer(i5);
                        CredentialInteractorImpl credentialInteractorImpl = this.this$0;
                        final BiometricPromptRequest.Credential credential = this.$request;
                        Integer num2 = new Integer(i5);
                        credentialInteractorImpl.getClass();
                        if (num2.intValue() <= 1) {
                            int i6 = credential.userInfo.deviceCredentialOwnerId;
                            DevicePolicyManager devicePolicyManager = credentialInteractorImpl.devicePolicyManager;
                            UserInfo userInfo = credentialInteractorImpl.userManager.getUserInfo(devicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(i6));
                            final UserType userType = (userInfo == null || userInfo.isPrimary()) ? UserType.PRIMARY : userInfo.isManagedProfile() ? UserType.MANAGED_PROFILE : UserType.SECONDARY;
                            int intValue = num2.intValue();
                            final Context context = credentialInteractorImpl.applicationContext;
                            if (intValue == 1) {
                                int i7 = CredentialInteractorKt$WhenMappings.$EnumSwitchMapping$0[userType.ordinal()];
                                if (i7 == 1) {
                                    if (credential instanceof BiometricPromptRequest.Credential.Pin) {
                                        i2 = R.string.biometric_dialog_last_pin_attempt_before_wipe_device;
                                    } else if (credential instanceof BiometricPromptRequest.Credential.Pattern) {
                                        i2 = R.string.biometric_dialog_last_pattern_attempt_before_wipe_device;
                                    } else {
                                        if (!(credential instanceof BiometricPromptRequest.Credential.Password)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        i2 = R.string.biometric_dialog_last_password_attempt_before_wipe_device;
                                    }
                                    str2 = context.getString(i2);
                                } else if (i7 == 2) {
                                    if (credential instanceof BiometricPromptRequest.Credential.Pin) {
                                        str = "SystemUi.BIOMETRIC_DIALOG_WORK_PIN_LAST_ATTEMPT";
                                    } else if (credential instanceof BiometricPromptRequest.Credential.Pattern) {
                                        str = "SystemUi.BIOMETRIC_DIALOG_WORK_PATTERN_LAST_ATTEMPT";
                                    } else {
                                        if (!(credential instanceof BiometricPromptRequest.Credential.Password)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        str = "SystemUi.BIOMETRIC_DIALOG_WORK_PASSWORD_LAST_ATTEMPT";
                                    }
                                    str2 = devicePolicyManager.getResources().getString(str, new Supplier() { // from class: com.android.systemui.biometrics.domain.interactor.CredentialInteractorKt$getLastAttemptBeforeWipeProfileMessage$1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            int i8;
                                            BiometricPromptRequest.Credential credential2 = BiometricPromptRequest.Credential.this;
                                            if (credential2 instanceof BiometricPromptRequest.Credential.Pin) {
                                                i8 = R.string.biometric_dialog_last_pin_attempt_before_wipe_profile;
                                            } else if (credential2 instanceof BiometricPromptRequest.Credential.Pattern) {
                                                i8 = R.string.biometric_dialog_last_pattern_attempt_before_wipe_profile;
                                            } else {
                                                if (!(credential2 instanceof BiometricPromptRequest.Credential.Password)) {
                                                    throw new NoWhenBranchMatchedException();
                                                }
                                                i8 = R.string.biometric_dialog_last_password_attempt_before_wipe_profile;
                                            }
                                            return context.getString(i8);
                                        }
                                    });
                                } else {
                                    if (i7 != 3) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                    if (credential instanceof BiometricPromptRequest.Credential.Pin) {
                                        i3 = R.string.biometric_dialog_last_pin_attempt_before_wipe_user;
                                    } else if (credential instanceof BiometricPromptRequest.Credential.Pattern) {
                                        i3 = R.string.biometric_dialog_last_pattern_attempt_before_wipe_user;
                                    } else {
                                        if (!(credential instanceof BiometricPromptRequest.Credential.Password)) {
                                            throw new NoWhenBranchMatchedException();
                                        }
                                        i3 = R.string.biometric_dialog_last_password_attempt_before_wipe_user;
                                    }
                                    str2 = context.getString(i3);
                                }
                            } else if (intValue <= 0) {
                                str2 = devicePolicyManager.getResources().getString(CredentialInteractorKt$WhenMappings.$EnumSwitchMapping$0[userType.ordinal()] == 2 ? "SystemUi.BIOMETRIC_DIALOG_WORK_LOCK_FAILED_ATTEMPTS" : PeripheralBarcodeConstants.Symbology.UNDEFINED, new Supplier() { // from class: com.android.systemui.biometrics.domain.interactor.CredentialInteractorKt$getNowWipingMessage$1

                                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                                    public abstract /* synthetic */ class WhenMappings {
                                        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                                        static {
                                            int[] iArr = new int[UserType.values().length];
                                            try {
                                                iArr[UserType.PRIMARY.ordinal()] = 1;
                                            } catch (NoSuchFieldError unused) {
                                            }
                                            try {
                                                iArr[UserType.MANAGED_PROFILE.ordinal()] = 2;
                                            } catch (NoSuchFieldError unused2) {
                                            }
                                            try {
                                                iArr[UserType.SECONDARY.ordinal()] = 3;
                                            } catch (NoSuchFieldError unused3) {
                                            }
                                            $EnumSwitchMapping$0 = iArr;
                                        }
                                    }

                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        int i8;
                                        int i9 = WhenMappings.$EnumSwitchMapping$0[UserType.this.ordinal()];
                                        if (i9 == 1) {
                                            i8 = R.string.failed_attempts_now_wiping_device;
                                        } else if (i9 == 2) {
                                            i8 = R.string.failed_attempts_now_wiping_profile;
                                        } else {
                                            if (i9 != 3) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            i8 = R.string.failed_attempts_now_wiping_user;
                                        }
                                        return context.getString(i8);
                                    }
                                });
                            } else {
                                str2 = "";
                            }
                        }
                        CredentialStatus.Fail.Error error2 = new CredentialStatus.Fail.Error(string, num, str2);
                        this.I$0 = i4;
                        this.label = 6;
                        if (flowCollector.emit(error2, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                    i = i4;
                    this.this$0.lockPatternUtils.reportFailedPasswordAttempt(i);
                    return Unit.INSTANCE;
                }
                long lockoutAttemptDeadline = this.this$0.lockPatternUtils.setLockoutAttemptDeadline(i4, verifyCredential.getTimeout());
                ((SystemClockImpl) this.this$0.systemClock).getClass();
                elapsedRealtime = lockoutAttemptDeadline - SystemClock.elapsedRealtime();
                j = 1000;
                if (elapsedRealtime <= 0) {
                    CredentialStatus.Fail.Throttled throttled = new CredentialStatus.Fail.Throttled(this.this$0.applicationContext.getString(R.string.biometric_dialog_credential_too_many_attempts, new Long(elapsedRealtime / 1000)));
                    this.L$0 = flowCollector;
                    this.J$0 = j;
                    this.J$1 = elapsedRealtime;
                    this.label = 2;
                    if (flowCollector.emit(throttled, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    this.L$0 = flowCollector;
                    this.J$0 = j;
                    this.J$1 = elapsedRealtime;
                    this.label = 3;
                    if (DelayKt.delay(j, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    elapsedRealtime -= j;
                    if (elapsedRealtime <= 0) {
                        CredentialStatus.Fail.Error error3 = new CredentialStatus.Fail.Error("", null, null, 6, null);
                        this.L$0 = null;
                        this.label = 4;
                        if (flowCollector.emit(error3, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        return Unit.INSTANCE;
                    }
                }
                break;
            case 1:
            case 4:
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            case 2:
                elapsedRealtime = this.J$1;
                j = this.J$0;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                this.L$0 = flowCollector;
                this.J$0 = j;
                this.J$1 = elapsedRealtime;
                this.label = 3;
                if (DelayKt.delay(j, this) == coroutineSingletons) {
                }
                elapsedRealtime -= j;
                if (elapsedRealtime <= 0) {
                }
                break;
            case 3:
                elapsedRealtime = this.J$1;
                j = this.J$0;
                flowCollector = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure(obj);
                elapsedRealtime -= j;
                if (elapsedRealtime <= 0) {
                }
                break;
            case 5:
            case 6:
                i = this.I$0;
                ResultKt.throwOnFailure(obj);
                this.this$0.lockPatternUtils.reportFailedPasswordAttempt(i);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
