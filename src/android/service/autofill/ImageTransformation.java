package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class ImageTransformation extends InternalTransformation implements Transformation, Parcelable {
    public static final Parcelable.Creator<ImageTransformation> CREATOR = new Parcelable.Creator<ImageTransformation>() { // from class: android.service.autofill.ImageTransformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageTransformation createFromParcel(Parcel parcel) {
            Builder builder;
            AutofillId autofillId = (AutofillId) parcel.readParcelable(null, AutofillId.class);
            Pattern[] patternArr = (Pattern[]) parcel.readSerializable();
            int[] createIntArray = parcel.createIntArray();
            CharSequence[] readCharSequenceArray = parcel.readCharSequenceArray();
            CharSequence charSequence = readCharSequenceArray[0];
            if (charSequence != null) {
                builder = new Builder(autofillId, patternArr[0], createIntArray[0], charSequence);
            } else {
                builder = new Builder(autofillId, patternArr[0], createIntArray[0]);
            }
            int length = patternArr.length;
            for (int i = 1; i < length; i++) {
                CharSequence charSequence2 = readCharSequenceArray[i];
                if (charSequence2 != null) {
                    builder.addOption(patternArr[i], createIntArray[i], charSequence2);
                } else {
                    builder.addOption(patternArr[i], createIntArray[i]);
                }
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImageTransformation[] newArray(int i) {
            return new ImageTransformation[i];
        }
    };
    private static final String TAG = "ImageTransformation";
    private final AutofillId mId;
    private final ArrayList<Option> mOptions;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ImageTransformation(Builder builder) {
        this.mId = builder.mId;
        this.mOptions = builder.mOptions;
    }

    @Override // android.service.autofill.InternalTransformation
    public void apply(ValueFinder valueFinder, RemoteViews remoteViews, int i) throws Exception {
        String findByAutofillId = valueFinder.findByAutofillId(this.mId);
        if (findByAutofillId == null) {
            Log.w(TAG, "No view for id " + this.mId);
            return;
        }
        int size = this.mOptions.size();
        if (Helper.sDebug) {
            Log.d(TAG, size + " multiple options on id " + i + " to compare against");
        }
        for (int i2 = 0; i2 < size; i2++) {
            Option option = this.mOptions.get(i2);
            try {
                if (option.pattern.matcher(findByAutofillId).matches()) {
                    Log.d(TAG, "Found match at " + i2 + ": " + option);
                    remoteViews.setImageViewResource(i, option.resId);
                    if (option.contentDescription != null) {
                        remoteViews.setContentDescription(i, option.contentDescription);
                        return;
                    }
                    return;
                }
            } catch (Exception e) {
                Log.w(TAG, "Error matching regex #" + i2 + NavigationBarInflaterView.KEY_CODE_START + option.pattern + ") on id " + option.resId + ": " + e.getClass());
                throw e;
            }
        }
        if (Helper.sDebug) {
            Log.d(TAG, "No match for " + findByAutofillId);
        }
    }

    public static class Builder {
        private boolean mDestroyed;
        private final AutofillId mId;
        private final ArrayList<Option> mOptions = new ArrayList<>();

        @Deprecated
        public Builder(AutofillId autofillId, Pattern pattern, int i) {
            this.mId = (AutofillId) Objects.requireNonNull(autofillId);
            addOption(pattern, i);
        }

        public Builder(AutofillId autofillId, Pattern pattern, int i, CharSequence charSequence) {
            this.mId = (AutofillId) Objects.requireNonNull(autofillId);
            addOption(pattern, i, charSequence);
        }

        @Deprecated
        public Builder addOption(Pattern pattern, int i) {
            addOptionInternal(pattern, i, null);
            return this;
        }

        public Builder addOption(Pattern pattern, int i, CharSequence charSequence) {
            addOptionInternal(pattern, i, (CharSequence) Objects.requireNonNull(charSequence));
            return this;
        }

        private void addOptionInternal(Pattern pattern, int i, CharSequence charSequence) {
            throwIfDestroyed();
            Objects.requireNonNull(pattern);
            Preconditions.checkArgument(i != 0);
            this.mOptions.add(new Option(pattern, i, charSequence));
        }

        public ImageTransformation build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new ImageTransformation(this);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(!this.mDestroyed, "Already called build()");
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "ImageTransformation: [id=" + this.mId + ", options=" + this.mOptions + NavigationBarInflaterView.SIZE_MOD_END;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.io.Serializable, java.util.regex.Pattern[]] */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mId, i);
        int size = this.mOptions.size();
        ?? r0 = new Pattern[size];
        int[] iArr = new int[size];
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            Option option = this.mOptions.get(i2);
            r0[i2] = option.pattern;
            iArr[i2] = option.resId;
            strArr[i2] = option.contentDescription;
        }
        parcel.writeSerializable(r0);
        parcel.writeIntArray(iArr);
        parcel.writeCharSequenceArray(strArr);
    }

    private static final class Option {
        public final CharSequence contentDescription;
        public final Pattern pattern;
        public final int resId;

        Option(Pattern pattern, int i, CharSequence charSequence) {
            this.pattern = pattern;
            this.resId = i;
            this.contentDescription = TextUtils.trimNoCopySpans(charSequence);
        }
    }
}
