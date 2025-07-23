package androidx.window.core;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ValidSpecification extends SpecificationComputer {
    public final Logger logger;
    public final String tag;
    public final Object value;
    public final VerificationMode verificationMode;

    public ValidSpecification(Object obj, String str, VerificationMode verificationMode, Logger logger) {
        this.value = obj;
        this.tag = str;
        this.verificationMode = verificationMode;
        this.logger = logger;
    }

    @Override // androidx.window.core.SpecificationComputer
    public final Object compute() {
        return this.value;
    }
}
