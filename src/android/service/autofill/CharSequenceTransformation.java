package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Logging.Session;
import android.util.Log;
import android.util.Pair;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class CharSequenceTransformation extends InternalTransformation implements Transformation, Parcelable {
    public static final Parcelable.Creator<CharSequenceTransformation> CREATOR = new Parcelable.Creator<CharSequenceTransformation>() { // from class: android.service.autofill.CharSequenceTransformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CharSequenceTransformation createFromParcel(Parcel parcel) {
            AutofillId[] autofillIdArr = (AutofillId[]) parcel.readParcelableArray(null, AutofillId.class);
            Pattern[] patternArr = (Pattern[]) parcel.readSerializable();
            String[] strArrCreateStringArray = parcel.createStringArray();
            Builder builder = new Builder(autofillIdArr[0], patternArr[0], strArrCreateStringArray[0]);
            int length = autofillIdArr.length;
            for (int i = 1; i < length; i++) {
                builder.addField(autofillIdArr[i], patternArr[i], strArrCreateStringArray[i]);
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CharSequenceTransformation[] newArray(int i) {
            return new CharSequenceTransformation[i];
        }
    };
    private static final String TAG = "CharSequenceTransformation";
    private final LinkedHashMap<AutofillId, Pair<Pattern, String>> mFields;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CharSequenceTransformation(Builder builder) {
        this.mFields = builder.mFields;
    }

    @Override // android.service.autofill.InternalTransformation
    public void apply(ValueFinder valueFinder, RemoteViews remoteViews, int i) throws Exception {
        StringBuilder sb = new StringBuilder();
        int size = this.mFields.size();
        if (Helper.sDebug) {
            Log.d(TAG, size + " fields on id " + i);
        }
        for (Map.Entry<AutofillId, Pair<Pattern, String>> entry : this.mFields.entrySet()) {
            AutofillId key = entry.getKey();
            Pair<Pattern, String> value = entry.getValue();
            String strFindByAutofillId = valueFinder.findByAutofillId(key);
            if (strFindByAutofillId == null) {
                Log.w(TAG, "No value for id " + key);
                return;
            }
            try {
                Matcher matcher = value.first.matcher(strFindByAutofillId);
                if (!matcher.find()) {
                    if (Helper.sDebug) {
                        Log.d(TAG, "Match for " + value.first + " failed on id " + key);
                        return;
                    }
                    return;
                }
                sb.append(matcher.replaceAll(value.second));
            } catch (Exception e) {
                Log.w(TAG, "Cannot apply " + value.first.pattern() + Session.SUBSESSION_SEPARATION_CHAR + value.second + " to field with autofill id" + key + ": " + e.getClass());
                throw e;
            }
        }
        Log.d(TAG, "Converting text on child " + i + " to " + sb.length() + "_chars");
        remoteViews.setCharSequence(i, "setText", sb);
    }

    public static class Builder {
        private boolean mDestroyed;
        private final LinkedHashMap<AutofillId, Pair<Pattern, String>> mFields = new LinkedHashMap<>();

        public Builder(AutofillId autofillId, Pattern pattern, String str) {
            addField(autofillId, pattern, str);
        }

        public Builder addField(AutofillId autofillId, Pattern pattern, String str) {
            throwIfDestroyed();
            Objects.requireNonNull(autofillId);
            Objects.requireNonNull(pattern);
            Objects.requireNonNull(str);
            this.mFields.put(autofillId, new Pair<>(pattern, str));
            return this;
        }

        public CharSequenceTransformation build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new CharSequenceTransformation(this);
        }

        private void throwIfDestroyed() {
            Preconditions.checkState(!this.mDestroyed, "Already called build()");
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "MultipleViewsCharSequenceTransformation: [fields=" + this.mFields + NavigationBarInflaterView.SIZE_MOD_END;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.io.Serializable, java.util.regex.Pattern[]] */
    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        int size = this.mFields.size();
        AutofillId[] autofillIdArr = new AutofillId[size];
        ?? r2 = new Pattern[size];
        String[] strArr = new String[size];
        int i2 = 0;
        for (Map.Entry<AutofillId, Pair<Pattern, String>> entry : this.mFields.entrySet()) {
            autofillIdArr[i2] = entry.getKey();
            Pair<Pattern, String> value = entry.getValue();
            r2[i2] = value.first;
            strArr[i2] = value.second;
            i2++;
        }
        parcel.writeParcelableArray(autofillIdArr, i);
        parcel.writeSerializable(r2);
        parcel.writeStringArray(strArr);
    }
}
