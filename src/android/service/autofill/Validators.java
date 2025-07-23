package android.service.autofill;

import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class Validators {
    private Validators() {
        throw new UnsupportedOperationException("contains static methods only");
    }

    public static Validator and(Validator... validatorArr) {
        return new RequiredValidators(getInternalValidators(validatorArr));
    }

    public static Validator or(Validator... validatorArr) {
        return new OptionalValidators(getInternalValidators(validatorArr));
    }

    public static Validator not(Validator validator) {
        Preconditions.checkArgument(validator instanceof InternalValidator, "validator not provided by Android System: %s", validator);
        return new NegationValidator((InternalValidator) validator);
    }

    private static InternalValidator[] getInternalValidators(Validator[] validatorArr) {
        Preconditions.checkArrayElementsNotNull(validatorArr, "validators");
        InternalValidator[] internalValidatorArr = new InternalValidator[validatorArr.length];
        for (int i = 0; i < validatorArr.length; i++) {
            Preconditions.checkArgument(validatorArr[i] instanceof InternalValidator, "element %d not provided by Android System: %s", Integer.valueOf(i), validatorArr[i]);
            internalValidatorArr[i] = (InternalValidator) validatorArr[i];
        }
        return internalValidatorArr;
    }
}
