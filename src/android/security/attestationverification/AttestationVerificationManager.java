package android.security.attestationverification;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.ParcelDuration;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.infra.AndroidFuture;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class AttestationVerificationManager {
    public static final int FLAG_FAILURE_BOOT_STATE = 32;
    public static final int FLAG_FAILURE_CERTS = 4;
    public static final int FLAG_FAILURE_KEYSTORE_REQUIREMENTS = 16;
    public static final int FLAG_FAILURE_LOCAL_BINDING_REQUIREMENTS = 8;
    public static final int FLAG_FAILURE_PATCH_LEVEL_DIFF = 64;
    public static final int FLAG_FAILURE_UNKNOWN = 1;
    public static final int FLAG_FAILURE_UNSUPPORTED_PROFILE = 2;
    private static final Duration MAX_TOKEN_AGE = Duration.ofHours(1);
    public static final String PARAM_CHALLENGE = "localbinding.challenge";
    public static final String PARAM_ID = "localbinding.id";
    public static final String PARAM_MAX_PATCH_LEVEL_DIFF_MONTHS = "param_max_patch_level_diff_months";
    public static final String PARAM_PUBLIC_KEY = "localbinding.public_key";
    public static final int PROFILE_APP_DEFINED = 1;
    public static final int PROFILE_PEER_DEVICE = 3;
    public static final int PROFILE_SELF_TRUSTED = 2;
    public static final int PROFILE_UNKNOWN = 0;
    private static final String TAG = "AVF";
    public static final int TYPE_APP_DEFINED = 1;
    public static final int TYPE_CHALLENGE = 3;
    public static final int TYPE_PUBLIC_KEY = 2;
    public static final int TYPE_UNKNOWN = 0;
    private final Context mContext;
    private final IAttestationVerificationManagerService mService;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttestationProfileId {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface LocalBindingType {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface VerificationResultFlags {
    }

    public void verifyAttestation(AttestationProfile attestationProfile, int i, Bundle bundle, byte[] bArr, final Executor executor, final BiConsumer<Integer, VerificationToken> biConsumer) {
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            androidFuture.thenAccept(new Consumer() { // from class: android.security.attestationverification.AttestationVerificationManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AttestationVerificationManager.lambda$verifyAttestation$1(executor, biConsumer, (IVerificationResult) obj);
                }
            });
            this.mService.verifyAttestation(attestationProfile, i, bundle, bArr, androidFuture);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$verifyAttestation$1(Executor executor, final BiConsumer biConsumer, final IVerificationResult iVerificationResult) {
        Log.d(TAG, "verifyAttestation result: " + iVerificationResult.resultCode + " / " + iVerificationResult.token);
        executor.execute(new Runnable() { // from class: android.security.attestationverification.AttestationVerificationManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BiConsumer biConsumer2 = biConsumer;
                IVerificationResult iVerificationResult2 = iVerificationResult;
                biConsumer2.accept(Integer.valueOf(iVerificationResult2.resultCode), iVerificationResult2.token);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int verifyToken(AttestationProfile attestationProfile, int i, Bundle bundle, VerificationToken verificationToken, Duration duration) {
        if (duration == null) {
            duration = MAX_TOKEN_AGE;
        } else {
            Duration duration2 = MAX_TOKEN_AGE;
            if (duration.compareTo(duration2) > 0) {
                throw new IllegalArgumentException("maximumAge cannot be greater than " + duration2 + "; was " + duration);
            }
        }
        try {
            AndroidFuture androidFuture = new AndroidFuture();
            androidFuture.orTimeout(5L, TimeUnit.SECONDS);
            this.mService.verifyToken(verificationToken, new ParcelDuration(duration), androidFuture);
            return ((Integer) androidFuture.get()).intValue();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (Throwable th) {
            throw new RuntimeException("Error verifying token.", th);
        }
    }

    public AttestationVerificationManager(Context context, IAttestationVerificationManagerService iAttestationVerificationManagerService) {
        this.mContext = context;
        this.mService = iAttestationVerificationManagerService;
    }

    public static String localBindingTypeToString(int i) {
        String str;
        if (i == 0) {
            str = "UNKNOWN";
        } else if (i == 1) {
            str = "APP_DEFINED";
        } else if (i == 2) {
            str = "PUBLIC_KEY";
        } else if (i == 3) {
            str = "CHALLENGE";
        } else {
            return Integer.toString(i);
        }
        return str + NavigationBarInflaterView.KEY_CODE_START + i + NavigationBarInflaterView.KEY_CODE_END;
    }
}
