package android.service.autofill;

import android.os.Parcel;
import android.view.autofill.Helper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class FieldClassification {
    private final ArrayList<Match> mMatches;

    public FieldClassification(ArrayList<Match> arrayList) {
        ArrayList<Match> arrayList2 = (ArrayList) Objects.requireNonNull(arrayList);
        this.mMatches = arrayList2;
        Collections.sort(arrayList2, new Comparator<Match>(this) { // from class: android.service.autofill.FieldClassification.1
            @Override // java.util.Comparator
            public int compare(Match match, Match match2) {
                if (match.mScore > match2.mScore) {
                    return -1;
                }
                return match.mScore < match2.mScore ? 1 : 0;
            }
        });
    }

    public List<Match> getMatches() {
        return this.mMatches;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "FieldClassification: " + this.mMatches;
    }

    private void writeToParcel(Parcel parcel) {
        parcel.writeInt(this.mMatches.size());
        for (int i = 0; i < this.mMatches.size(); i++) {
            this.mMatches.get(i).writeToParcel(parcel);
        }
    }

    private static FieldClassification readFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < readInt; i++) {
            arrayList.add(i, Match.readFromParcel(parcel));
        }
        return new FieldClassification(arrayList);
    }

    static FieldClassification[] readArrayFromParcel(Parcel parcel) {
        int readInt = parcel.readInt();
        FieldClassification[] fieldClassificationArr = new FieldClassification[readInt];
        for (int i = 0; i < readInt; i++) {
            fieldClassificationArr[i] = readFromParcel(parcel);
        }
        return fieldClassificationArr;
    }

    static void writeArrayToParcel(Parcel parcel, FieldClassification[] fieldClassificationArr) {
        parcel.writeInt(fieldClassificationArr.length);
        for (FieldClassification fieldClassification : fieldClassificationArr) {
            fieldClassification.writeToParcel(parcel);
        }
    }

    public static final class Match {
        private final String mCategoryId;
        private final float mScore;

        public Match(String str, float f) {
            this.mCategoryId = (String) Objects.requireNonNull(str);
            this.mScore = f;
        }

        public String getCategoryId() {
            return this.mCategoryId;
        }

        public float getScore() {
            return this.mScore;
        }

        public String toString() {
            if (!Helper.sDebug) {
                return super.toString();
            }
            StringBuilder sb = new StringBuilder("Match: categoryId=");
            Helper.appendRedacted(sb, this.mCategoryId);
            sb.append(", score=");
            sb.append(this.mScore);
            return sb.toString();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(Parcel parcel) {
            parcel.writeString(this.mCategoryId);
            parcel.writeFloat(this.mScore);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Match readFromParcel(Parcel parcel) {
            return new Match(parcel.readString(), parcel.readFloat());
        }
    }
}
