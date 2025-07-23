package androidx.window.core;

import android.util.Log;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArraysKt___ArraysKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FailedSpecification extends SpecificationComputer {
    public final WindowStrictModeException exception;
    public final Logger logger;
    public final String message;
    public final String tag;
    public final Object value;
    public final VerificationMode verificationMode;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VerificationMode.values().length];
            try {
                iArr[VerificationMode.STRICT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VerificationMode.LOG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VerificationMode.QUIET.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FailedSpecification(Object obj, String str, String str2, Logger logger, VerificationMode verificationMode) {
        this.value = obj;
        this.tag = str;
        this.message = str2;
        this.logger = logger;
        this.verificationMode = verificationMode;
        WindowStrictModeException windowStrictModeException = new WindowStrictModeException(str2 + " value: " + obj);
        windowStrictModeException.setStackTrace((StackTraceElement[]) ArraysKt___ArraysKt.drop(2, windowStrictModeException.getStackTrace()).toArray(new StackTraceElement[0]));
        this.exception = windowStrictModeException;
    }

    @Override // androidx.window.core.SpecificationComputer
    public final Object compute() {
        int i = WhenMappings.$EnumSwitchMapping$0[this.verificationMode.ordinal()];
        if (i == 1) {
            throw this.exception;
        }
        if (i != 2) {
            if (i == 3) {
                return null;
            }
            throw new NoWhenBranchMatchedException();
        }
        String str = this.message + " value: " + this.value;
        ((AndroidLogger) this.logger).getClass();
        Log.d(this.tag, str);
        return null;
    }
}
