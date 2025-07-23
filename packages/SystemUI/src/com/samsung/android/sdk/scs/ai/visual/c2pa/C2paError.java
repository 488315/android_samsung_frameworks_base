package com.samsung.android.sdk.scs.ai.visual.c2pa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.text.MatchResult;
import kotlin.text.Regex;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum C2paError {
    NO_C2PA_MANIFEST("no JUMBF data found"),
    CLAIM_MISSING("claim.missing"),
    CLAIM_MULTIPLE("claim.multiple"),
    HARD_BINDINGS_MISSING("claim.hardBindings.missing"),
    CLAIM_REQUIRED_MISSING("claim.required.missing"),
    CLAIM_CBOR_INVALID("claim.cbor.invalid"),
    INGREDIENT_HASHEDURI_MISMATCH("ingredient.hashedURI.mismatch"),
    CLAIM_SIGNATURE_MISSING("claimSignature.missing"),
    CLAIM_SIGNATURE_MISMATCH("claimSignature.mismatch"),
    MANIFEST_INACCESSIBLE("manifest.inaccessible"),
    MANIFEST_MULTIPLE_PARENTS("manifest.multipleParents"),
    MANIFEST_UPDATE_INVALID("manifest.update.invalid"),
    MANIFEST_UPDATE_WRONG_PARENTS("manifest.update.wrongParents"),
    SIGNING_CREDENTIAL_UNTRUSTED("signingCredential.untrusted"),
    SIGNING_CREDENTIAL_INVALID("signingCredential.invalid"),
    SIGNING_CREDENTIAL_REVOKED("signingCredential.revoked"),
    SIGNING_CREDENTIAL_EXPIRED("signingCredential.expired"),
    TIMESTAMP_MISMATCH("timeStamp.mismatch"),
    TIMESTAMP_UNTRUSTED("timeStamp.untrusted"),
    TIMESTAMP_OUTSIDE_VALIDITY("timeStamp.outsideValidity"),
    ASSERTION_HASHEDURI_MISMATCH("assertion.hashedURI.mismatch"),
    ASSERTION_MISSING("assertion.missing"),
    ASSERTION_UNDECLARED("assertion.undeclared"),
    ASSERTION_INACCESSIBLE("assertion.inaccessible"),
    ASSERTION_NOT_REDACTED("assertion.notRedacted"),
    ASSERTION_SELF_REDACTED("assertion.selfRedacted"),
    ASSERTION_REQUIRED_MISSING("assertion.required.missing"),
    ASSERTION_JSON_INVALID("assertion.json.invalid"),
    ASSERTION_CBOR_INVALID("assertion.cbor.invalid"),
    ACTION_ASSERTION_INGREDIENT_MISMATCH("assertion.action.ingredientMismatch"),
    ACTION_ASSERTION_REDACTED("assertion.action.redacted"),
    ASSERTION_DATAHASH_MISMATCH("assertion.dataHash.mismatch"),
    ASSERTION_BMFFHASH_MISMATCH("assertion.bmffHash.mismatch"),
    ASSERTION_BOXHASH_MISMATCH("assertion.boxesHash.mismatch"),
    ASSERTION_BOXHASH_UNKNOWN("::assertion.boxesHash."),
    ASSERTION_CLOUD_DATA_HARD_BINDING("assertion.cloud-data.hardBinding"),
    ASSERTION_CLOUD_DATA_ACTIONS("assertion.cloud-data.actions"),
    ALGORITHM_UNSUPPORTED("algorithm.unsupported"),
    GENERAL_ERROR("general.error"),
    OLD_VERSION("prerelease content detected"),
    UNSUPPORTED_TYPE("type is unsupported"),
    INVALID_CLAIM_SIGNATURE("claim signature"),
    INVALID_PATH("invalid path"),
    MANIFEST_PARSING_ERROR("ManifestParsingError"),
    INVALID_SIGN_ALG("InvalidSignAlg"),
    C2PA_ERROR_UNKNOWN("C2PAUnKnown"),
    SERVICE_NOT_INITIALIZED("ServiceNotInitialized"),
    INVALID_PARENT_PATH("ParentPathSetError"),
    INVALID_INGREDIENT_PATH("IngredietPathError"),
    INTERNAL_SERVICE_ERROR("InternalServiceError"),
    PFD_READ_ERROR("PfdReadError"),
    MISSING_CONFIG_ERROR("MissingConfigError");

    public static final Companion Companion = new Companion(null);
    private final String errString;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean checkInvalid(String str) {
            int i;
            int i2;
            int i3;
            int i4;
            List<C2paError> fromErrorString = fromErrorString(str);
            List<C2paError> list = fromErrorString;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (!ArraysKt___ArraysKt.toSet(new C2paError[]{C2paError.CLAIM_SIGNATURE_MISMATCH, C2paError.SIGNING_CREDENTIAL_UNTRUSTED, C2paError.GENERAL_ERROR}).contains((C2paError) it.next())) {
                        return true;
                    }
                }
            }
            if (fromErrorString.contains(C2paError.CLAIM_SIGNATURE_MISMATCH) && !fromErrorString.contains(C2paError.SIGNING_CREDENTIAL_UNTRUSTED)) {
                return true;
            }
            List<C2paError> list2 = fromErrorString;
            boolean z = list2 instanceof Collection;
            if (z && list2.isEmpty()) {
                i = 0;
            } else {
                Iterator<T> it2 = list2.iterator();
                i = 0;
                while (it2.hasNext()) {
                    if (((C2paError) it2.next()) == C2paError.SIGNING_CREDENTIAL_UNTRUSTED && (i = i + 1) < 0) {
                        CollectionsKt__CollectionsKt.throwCountOverflow();
                        throw null;
                    }
                }
            }
            if (z && list2.isEmpty()) {
                i2 = 0;
            } else {
                Iterator<T> it3 = list2.iterator();
                i2 = 0;
                while (it3.hasNext()) {
                    if (((C2paError) it3.next()) == C2paError.GENERAL_ERROR && (i2 = i2 + 1) < 0) {
                        CollectionsKt__CollectionsKt.throwCountOverflow();
                        throw null;
                    }
                }
            }
            if (i != i2) {
                if (z && list2.isEmpty()) {
                    i3 = 0;
                } else {
                    Iterator<T> it4 = list2.iterator();
                    i3 = 0;
                    while (it4.hasNext()) {
                        if (((C2paError) it4.next()) == C2paError.SIGNING_CREDENTIAL_UNTRUSTED && (i3 = i3 + 1) < 0) {
                            CollectionsKt__CollectionsKt.throwCountOverflow();
                            throw null;
                        }
                    }
                }
                if (z && list2.isEmpty()) {
                    i4 = 0;
                } else {
                    Iterator<T> it5 = list2.iterator();
                    i4 = 0;
                    while (it5.hasNext()) {
                        if (((C2paError) it5.next()) == C2paError.CLAIM_SIGNATURE_MISMATCH && (i4 = i4 + 1) < 0) {
                            CollectionsKt__CollectionsKt.throwCountOverflow();
                            throw null;
                        }
                    }
                }
                if (i3 != i4) {
                    return true;
                }
            }
            return false;
        }

        public final List<C2paError> fromErrorString(String str) {
            ArrayList arrayList = new ArrayList();
            for (final C2paError c2paError : C2paError.values()) {
                Regex.Companion companion = Regex.Companion;
                String errString = c2paError.getErrString();
                companion.getClass();
                arrayList.addAll(SequencesKt___SequencesKt.toMutableList(new TransformingSequence(Regex.findAll$default(new Regex(Pattern.quote(errString)), str), new Function1() { // from class: com.samsung.android.sdk.scs.ai.visual.c2pa.C2paError$Companion$fromErrorString$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke, reason: merged with bridge method [inline-methods] */
                    public final C2paError mo779invoke(MatchResult matchResult) {
                        return C2paError.this;
                    }
                })));
            }
            if (str.length() > 0 && arrayList.isEmpty()) {
                arrayList.add(C2paError.C2PA_ERROR_UNKNOWN);
            }
            return arrayList;
        }

        private Companion() {
        }
    }

    C2paError(String str) {
        this.errString = str;
    }

    public final String getErrString() {
        return this.errString;
    }
}
