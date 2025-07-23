package android.service.autofill;

import android.annotation.NonNull;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.inputmethod.InlineSuggestionsRequest;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.BitUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/* loaded from: classes3.dex */
public final class FillRequest implements Parcelable {
    public static final Parcelable.Creator<FillRequest> CREATOR = new Parcelable.Creator<FillRequest>() { // from class: android.service.autofill.FillRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FillRequest[] newArray(int i) {
            return new FillRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FillRequest createFromParcel(Parcel parcel) {
            return new FillRequest(parcel);
        }
    };
    public static final int FLAG_COMPATIBILITY_MODE_REQUEST = 2;
    public static final int FLAG_IME_SHOWING = 128;
    public static final int FLAG_MANUAL_REQUEST = 1;
    public static final int FLAG_PASSWORD_INPUT_TYPE = 4;
    public static final int FLAG_PCC_DETECTION = 512;
    public static final int FLAG_RESET_FILL_DIALOG_STATE = 256;
    public static final int FLAG_SCREEN_HAS_CREDMAN_FIELD = 1024;

    @Deprecated
    public static final int FLAG_SUPPORTS_FILL_DIALOG = 64;
    public static final int FLAG_VIEW_NOT_FOCUSED = 16;
    public static final int FLAG_VIEW_REQUESTS_CREDMAN_SERVICE = 2048;
    public static final int INVALID_REQUEST_ID = Integer.MIN_VALUE;
    private final Bundle mClientState;
    private final IntentSender mDelayedFillIntentSender;
    private final List<FillContext> mFillContexts;
    private final int mFlags;
    private final List<String> mHints;
    private final int mId;
    private final InlineSuggestionsRequest mInlineSuggestionsRequest;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestFlags {
    }

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private void onConstructed() {
        Preconditions.checkCollectionElementsNotNull(this.mFillContexts, "contexts");
        Preconditions.checkCollectionElementsNotNull(this.mHints, "hints");
    }

    public static String requestFlagsToString(int i) {
        return BitUtils.flagsToString(i, new IntFunction() { // from class: android.service.autofill.FillRequest$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return FillRequest.singleRequestFlagsToString(i2);
            }
        });
    }

    static String singleRequestFlagsToString(int i) {
        if (i == 1) {
            return "FLAG_MANUAL_REQUEST";
        }
        if (i == 2) {
            return "FLAG_COMPATIBILITY_MODE_REQUEST";
        }
        if (i == 4) {
            return "FLAG_PASSWORD_INPUT_TYPE";
        }
        if (i == 16) {
            return "FLAG_VIEW_NOT_FOCUSED";
        }
        if (i == 64) {
            return "FLAG_SUPPORTS_FILL_DIALOG";
        }
        if (i == 128) {
            return "FLAG_IME_SHOWING";
        }
        if (i == 256) {
            return "FLAG_RESET_FILL_DIALOG_STATE";
        }
        if (i == 512) {
            return "FLAG_PCC_DETECTION";
        }
        if (i == 1024) {
            return "FLAG_SCREEN_HAS_CREDMAN_FIELD";
        }
        if (i == 2048) {
            return "FLAG_VIEW_REQUESTS_CREDMAN_SERVICE";
        }
        return Integer.toHexString(i);
    }

    public FillRequest(int i, List<FillContext> list, List<String> list2, Bundle bundle, int i2, InlineSuggestionsRequest inlineSuggestionsRequest, IntentSender intentSender) {
        this.mId = i;
        this.mFillContexts = list;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list);
        this.mHints = list2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) list2);
        this.mClientState = bundle;
        this.mFlags = i2;
        Preconditions.checkFlagsArgument(i2, 4055);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
        this.mDelayedFillIntentSender = intentSender;
        onConstructed();
    }

    public int getId() {
        return this.mId;
    }

    public List<FillContext> getFillContexts() {
        return this.mFillContexts;
    }

    public List<String> getHints() {
        return this.mHints;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public InlineSuggestionsRequest getInlineSuggestionsRequest() {
        return this.mInlineSuggestionsRequest;
    }

    public IntentSender getDelayedFillIntentSender() {
        return this.mDelayedFillIntentSender;
    }

    public String toString() {
        return "FillRequest { id = " + this.mId + ", fillContexts = " + this.mFillContexts + ", hints = " + this.mHints + ", clientState = " + this.mClientState + ", flags = " + requestFlagsToString(this.mFlags) + ", inlineSuggestionsRequest = " + this.mInlineSuggestionsRequest + ", delayedFillIntentSender = " + this.mDelayedFillIntentSender + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mClientState != null ? (byte) 8 : (byte) 0;
        if (this.mInlineSuggestionsRequest != null) {
            b = (byte) (b | 32);
        }
        if (this.mDelayedFillIntentSender != null) {
            b = (byte) (b | 64);
        }
        parcel.writeByte(b);
        parcel.writeInt(this.mId);
        parcel.writeParcelableList(this.mFillContexts, i);
        parcel.writeStringList(this.mHints);
        Bundle bundle = this.mClientState;
        if (bundle != null) {
            parcel.writeBundle(bundle);
        }
        parcel.writeInt(this.mFlags);
        InlineSuggestionsRequest inlineSuggestionsRequest = this.mInlineSuggestionsRequest;
        if (inlineSuggestionsRequest != null) {
            parcel.writeTypedObject(inlineSuggestionsRequest, i);
        }
        IntentSender intentSender = this.mDelayedFillIntentSender;
        if (intentSender != null) {
            parcel.writeTypedObject(intentSender, i);
        }
    }

    FillRequest(Parcel parcel) {
        byte readByte = parcel.readByte();
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        parcel.readParcelableList(arrayList, FillContext.class.getClassLoader());
        ArrayList arrayList2 = new ArrayList();
        parcel.readStringList(arrayList2);
        Bundle readBundle = (readByte & 8) == 0 ? null : parcel.readBundle();
        int readInt2 = parcel.readInt();
        InlineSuggestionsRequest inlineSuggestionsRequest = (readByte & 32) == 0 ? null : (InlineSuggestionsRequest) parcel.readTypedObject(InlineSuggestionsRequest.CREATOR);
        IntentSender intentSender = (readByte & 64) == 0 ? null : (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
        this.mId = readInt;
        this.mFillContexts = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mHints = arrayList2;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList2);
        this.mClientState = readBundle;
        this.mFlags = readInt2;
        Preconditions.checkFlagsArgument(readInt2, 4055);
        this.mInlineSuggestionsRequest = inlineSuggestionsRequest;
        this.mDelayedFillIntentSender = intentSender;
        onConstructed();
    }
}
