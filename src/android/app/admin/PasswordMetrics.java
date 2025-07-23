package android.app.admin;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.PasswordValidationError;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class PasswordMetrics implements Parcelable {
    private static final int CHAR_DIGIT = 2;
    private static final int CHAR_LOWER_CASE = 0;
    private static final int CHAR_SYMBOL = 3;
    private static final int CHAR_UPPER_CASE = 1;
    public static final Parcelable.Creator<PasswordMetrics> CREATOR = new Parcelable.Creator<PasswordMetrics>() { // from class: android.app.admin.PasswordMetrics.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordMetrics createFromParcel(Parcel parcel) {
            return new PasswordMetrics(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PasswordMetrics[] newArray(int i) {
            return new PasswordMetrics[i];
        }
    };
    public static final int MAX_ALLOWED_SEQUENCE = 3;
    private static final String TAG = "PasswordMetrics";
    public int credType;
    public int length;
    public int letters;
    public int lowerCase;
    public int nonLetter;
    public int nonNumeric;
    public int numeric;
    public int seqLength;
    public int symbols;
    public int upperCase;

    @Retention(RetentionPolicy.SOURCE)
    private @interface CharacterCatagory {
    }

    private static int categoryChar(char c) {
        if ('a' <= c && c <= 'z') {
            return 0;
        }
        if ('A' > c || c > 'Z') {
            return ('0' > c || c > '9') ? 3 : 2;
        }
        return 1;
    }

    public static int complexityLevelToMinQuality(int i) {
        int i2 = 65536;
        if (i != 65536) {
            i2 = 196608;
            if (i != 196608 && i != 327680) {
                return 0;
            }
        }
        return i2;
    }

    private static int maxDiffCategory(int i) {
        if (i == 0 || i == 1) {
            return 1;
        }
        return i != 2 ? 0 : 10;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PasswordMetrics(int i) {
        this.length = 0;
        this.letters = 0;
        this.upperCase = 0;
        this.lowerCase = 0;
        this.numeric = 0;
        this.symbols = 0;
        this.nonLetter = 0;
        this.nonNumeric = 0;
        this.seqLength = Integer.MAX_VALUE;
        this.credType = i;
    }

    public PasswordMetrics(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        this.credType = i;
        this.length = i2;
        this.letters = i3;
        this.upperCase = i4;
        this.lowerCase = i5;
        this.numeric = i6;
        this.symbols = i7;
        this.nonLetter = i8;
        this.nonNumeric = i9;
        this.seqLength = i10;
    }

    private PasswordMetrics(PasswordMetrics passwordMetrics) {
        this(passwordMetrics.credType, passwordMetrics.length, passwordMetrics.letters, passwordMetrics.upperCase, passwordMetrics.lowerCase, passwordMetrics.numeric, passwordMetrics.symbols, passwordMetrics.nonLetter, passwordMetrics.nonNumeric, passwordMetrics.seqLength);
    }

    public static int sanitizeComplexityLevel(int i) {
        if (i == 0 || i == 65536 || i == 196608 || i == 327680) {
            return i;
        }
        Log.w(TAG, "Invalid password complexity used: " + i);
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.credType);
        parcel.writeInt(this.length);
        parcel.writeInt(this.letters);
        parcel.writeInt(this.upperCase);
        parcel.writeInt(this.lowerCase);
        parcel.writeInt(this.numeric);
        parcel.writeInt(this.symbols);
        parcel.writeInt(this.nonLetter);
        parcel.writeInt(this.nonNumeric);
        parcel.writeInt(this.seqLength);
    }

    public static PasswordMetrics computeForCredential(LockscreenCredential lockscreenCredential) {
        if (lockscreenCredential.isPassword() || lockscreenCredential.isPin()) {
            return computeForPasswordOrPin(lockscreenCredential.getCredential(), lockscreenCredential.isPin());
        }
        if (lockscreenCredential.isPattern()) {
            PasswordMetrics passwordMetrics = new PasswordMetrics(1);
            passwordMetrics.length = lockscreenCredential.size();
            return passwordMetrics;
        }
        if (lockscreenCredential.isNone()) {
            return new PasswordMetrics(-1);
        }
        if (lockscreenCredential.isUCM()) {
            return new PasswordMetrics(6);
        }
        throw new IllegalArgumentException("Unknown credential type " + lockscreenCredential.getType());
    }

    private static PasswordMetrics computeForPasswordOrPin(byte[] bArr, boolean z) {
        int length = bArr.length;
        int length2 = bArr.length;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        while (true) {
            if (i >= length2) {
                break;
            }
            int categoryChar = categoryChar((char) bArr[i]);
            if (categoryChar == 0) {
                i2++;
                i4++;
            } else if (categoryChar != 1) {
                if (categoryChar == 2) {
                    i5++;
                    i7++;
                } else if (categoryChar == 3) {
                    i6++;
                    i7++;
                }
                i++;
            } else {
                i2++;
                i3++;
            }
            i8++;
            i++;
        }
        return new PasswordMetrics(z ? 3 : 4, length, i2, i3, i4, i5, i6, i7, i8, maxLengthSequence(bArr));
    }

    public static int maxLengthSequence(byte[] bArr) {
        if (bArr.length == 0) {
            return 0;
        }
        char c = (char) bArr[0];
        int categoryChar = categoryChar(c);
        int i = 0;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        int i4 = 1;
        while (i4 < bArr.length) {
            char c2 = (char) bArr[i4];
            int categoryChar2 = categoryChar(c2);
            int i5 = c2 - c;
            if (categoryChar2 != categoryChar || Math.abs(i5) > maxDiffCategory(categoryChar)) {
                i = Math.max(i, i4 - i2);
                z = false;
                i2 = i4;
                categoryChar = categoryChar2;
            } else {
                if (z && i5 != i3) {
                    i = Math.max(i, i4 - i2);
                    i2 = i4 - 1;
                }
                i3 = i5;
                z = true;
            }
            i4++;
            c = c2;
        }
        return Math.max(i, bArr.length - i2);
    }

    public static PasswordMetrics merge(List<PasswordMetrics> list) {
        PasswordMetrics passwordMetrics = new PasswordMetrics(-1);
        Iterator<PasswordMetrics> it = list.iterator();
        while (it.hasNext()) {
            passwordMetrics.maxWith(it.next());
        }
        return passwordMetrics;
    }

    public void maxWith(PasswordMetrics passwordMetrics) {
        int max = Math.max(this.credType, passwordMetrics.credType);
        this.credType = max;
        if (max == 4 || max == 3) {
            this.length = Math.max(this.length, passwordMetrics.length);
            this.letters = Math.max(this.letters, passwordMetrics.letters);
            this.upperCase = Math.max(this.upperCase, passwordMetrics.upperCase);
            this.lowerCase = Math.max(this.lowerCase, passwordMetrics.lowerCase);
            this.numeric = Math.max(this.numeric, passwordMetrics.numeric);
            this.symbols = Math.max(this.symbols, passwordMetrics.symbols);
            this.nonLetter = Math.max(this.nonLetter, passwordMetrics.nonLetter);
            this.nonNumeric = Math.max(this.nonNumeric, passwordMetrics.nonNumeric);
            this.seqLength = Math.min(this.seqLength, passwordMetrics.seqLength);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    private static abstract class ComplexityBucket {
        private static final /* synthetic */ ComplexityBucket[] $VALUES = $values();
        public static final ComplexityBucket BUCKET_HIGH;
        public static final ComplexityBucket BUCKET_LOW;
        public static final ComplexityBucket BUCKET_MEDIUM;
        public static final ComplexityBucket BUCKET_NONE;
        int mComplexityLevel;

        abstract boolean allowsCredType(int i);

        abstract boolean canHaveSequence();

        abstract int getMinimumLength(boolean z);

        private static /* synthetic */ ComplexityBucket[] $values() {
            return new ComplexityBucket[]{BUCKET_HIGH, BUCKET_MEDIUM, BUCKET_LOW, BUCKET_NONE};
        }

        public static ComplexityBucket valueOf(String str) {
            return (ComplexityBucket) Enum.valueOf(ComplexityBucket.class, str);
        }

        public static ComplexityBucket[] values() {
            return (ComplexityBucket[]) $VALUES.clone();
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$1, reason: invalid class name */
        enum AnonymousClass1 extends ComplexityBucket {
            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return i == 4 || i == 3;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return false;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return z ? 6 : 8;
            }

            private AnonymousClass1(String str, int i, int i2) {
                super(str, i, i2);
            }
        }

        static {
            int i = 0;
            BUCKET_HIGH = new AnonymousClass1("BUCKET_HIGH", i, 327680);
            BUCKET_MEDIUM = new AnonymousClass2("BUCKET_MEDIUM", 1, 196608);
            BUCKET_LOW = new AnonymousClass3("BUCKET_LOW", 2, 65536);
            BUCKET_NONE = new AnonymousClass4("BUCKET_NONE", 3, i);
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$2, reason: invalid class name */
        enum AnonymousClass2 extends ComplexityBucket {
            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return i == 4 || i == 3;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return false;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return 4;
            }

            private AnonymousClass2(String str, int i, int i2) {
                super(str, i, i2);
            }
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$3, reason: invalid class name */
        enum AnonymousClass3 extends ComplexityBucket {
            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return i != -1;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return true;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return 0;
            }

            private AnonymousClass3(String str, int i, int i2) {
                super(str, i, i2);
            }
        }

        /* renamed from: android.app.admin.PasswordMetrics$ComplexityBucket$4, reason: invalid class name */
        enum AnonymousClass4 extends ComplexityBucket {
            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean allowsCredType(int i) {
                return true;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            boolean canHaveSequence() {
                return true;
            }

            @Override // android.app.admin.PasswordMetrics.ComplexityBucket
            int getMinimumLength(boolean z) {
                return 0;
            }

            private AnonymousClass4(String str, int i, int i2) {
                super(str, i, i2);
            }
        }

        private ComplexityBucket(String str, int i, int i2) {
            this.mComplexityLevel = i2;
        }

        static ComplexityBucket forComplexity(int i) {
            for (ComplexityBucket complexityBucket : values()) {
                if (complexityBucket.mComplexityLevel == i) {
                    return complexityBucket;
                }
            }
            throw new IllegalArgumentException("Invalid complexity level: " + i);
        }
    }

    private boolean satisfiesBucket(ComplexityBucket complexityBucket) {
        if (!complexityBucket.allowsCredType(this.credType)) {
            return false;
        }
        int i = this.credType;
        if (i != 4 && i != 3) {
            return true;
        }
        if (complexityBucket.canHaveSequence() || this.seqLength <= 3) {
            if (this.length >= complexityBucket.getMinimumLength(this.nonNumeric > 0)) {
                return true;
            }
        }
        return false;
    }

    public int determineComplexity() {
        for (ComplexityBucket complexityBucket : ComplexityBucket.values()) {
            if (satisfiesBucket(complexityBucket)) {
                return complexityBucket.mComplexityLevel;
            }
        }
        throw new IllegalStateException("Failed to figure out complexity for a given metrics");
    }

    public static List<PasswordValidationError> validateCredential(PasswordMetrics passwordMetrics, int i, LockscreenCredential lockscreenCredential) {
        if (lockscreenCredential.hasInvalidChars()) {
            return Collections.singletonList(new PasswordValidationError(2, 0));
        }
        return validatePasswordMetrics(passwordMetrics, i, computeForCredential(lockscreenCredential));
    }

    public static List<PasswordValidationError> validatePasswordMetrics(PasswordMetrics passwordMetrics, int i, PasswordMetrics passwordMetrics2) {
        ComplexityBucket forComplexity = ComplexityBucket.forComplexity(i);
        int i2 = passwordMetrics2.credType;
        if (i2 < passwordMetrics.credType || !forComplexity.allowsCredType(i2)) {
            return Collections.singletonList(new PasswordValidationError(1, 0));
        }
        int i3 = passwordMetrics2.credType;
        if (i3 == 1) {
            int i4 = passwordMetrics2.length;
            if (i4 != 0 && i4 < 4) {
                return Collections.singletonList(new PasswordValidationError(3, 4));
            }
            return Collections.EMPTY_LIST;
        }
        if (i3 == -1) {
            return Collections.EMPTY_LIST;
        }
        if (i3 == 3 && passwordMetrics2.nonNumeric > 0) {
            return Collections.singletonList(new PasswordValidationError(2, 0));
        }
        ArrayList arrayList = new ArrayList();
        if (passwordMetrics2.length > 256) {
            arrayList.add(new PasswordValidationError(5, 256));
        }
        PasswordMetrics applyComplexity = applyComplexity(passwordMetrics, passwordMetrics2.credType == 3, forComplexity);
        applyComplexity.length = Math.min(256, Math.max(applyComplexity.length, 4));
        applyComplexity.removeOverlapping();
        comparePasswordMetrics(applyComplexity, forComplexity, passwordMetrics2, arrayList);
        return arrayList;
    }

    private static void comparePasswordMetrics(PasswordMetrics passwordMetrics, ComplexityBucket complexityBucket, PasswordMetrics passwordMetrics2, ArrayList<PasswordValidationError> arrayList) {
        int minimumLength;
        if (passwordMetrics2.length < passwordMetrics.length) {
            arrayList.add(new PasswordValidationError(3, passwordMetrics.length));
        }
        if (passwordMetrics2.nonNumeric == 0 && passwordMetrics.nonNumeric == 0 && passwordMetrics.letters == 0 && passwordMetrics.lowerCase == 0 && passwordMetrics.upperCase == 0 && passwordMetrics.symbols == 0 && (minimumLength = complexityBucket.getMinimumLength(false)) > passwordMetrics.length && minimumLength > passwordMetrics.numeric && passwordMetrics2.length < minimumLength) {
            arrayList.add(new PasswordValidationError(4, minimumLength));
        }
        if (passwordMetrics2.letters < passwordMetrics.letters) {
            arrayList.add(new PasswordValidationError(7, passwordMetrics.letters));
        }
        if (passwordMetrics2.upperCase < passwordMetrics.upperCase) {
            arrayList.add(new PasswordValidationError(8, passwordMetrics.upperCase));
        }
        if (passwordMetrics2.lowerCase < passwordMetrics.lowerCase) {
            arrayList.add(new PasswordValidationError(9, passwordMetrics.lowerCase));
        }
        if (passwordMetrics2.numeric < passwordMetrics.numeric) {
            arrayList.add(new PasswordValidationError(10, passwordMetrics.numeric));
        }
        if (passwordMetrics2.symbols < passwordMetrics.symbols) {
            arrayList.add(new PasswordValidationError(11, passwordMetrics.symbols));
        }
        if (passwordMetrics2.nonLetter < passwordMetrics.nonLetter) {
            arrayList.add(new PasswordValidationError(12, passwordMetrics.nonLetter));
        }
        if (passwordMetrics2.nonNumeric < passwordMetrics.nonNumeric) {
            arrayList.add(new PasswordValidationError(13, passwordMetrics.nonNumeric));
        }
        if (passwordMetrics2.seqLength > passwordMetrics.seqLength) {
            arrayList.add(new PasswordValidationError(6, 0));
        }
    }

    private void removeOverlapping() {
        int i = this.upperCase + this.lowerCase;
        int i2 = this.numeric + this.symbols;
        int max = Math.max(this.letters, i);
        int i3 = this.symbols + max;
        int max2 = Math.max(max + Math.max(this.nonLetter, i2), this.numeric + Math.max(this.nonNumeric, i3));
        if (i >= this.letters) {
            this.letters = 0;
        }
        if (i2 >= this.nonLetter) {
            this.nonLetter = 0;
        }
        if (i3 >= this.nonNumeric) {
            this.nonNumeric = 0;
        }
        if (max2 >= this.length) {
            this.length = 0;
        }
    }

    public static PasswordMetrics applyComplexity(PasswordMetrics passwordMetrics, boolean z, int i) {
        return applyComplexity(passwordMetrics, z, ComplexityBucket.forComplexity(i));
    }

    private static PasswordMetrics applyComplexity(PasswordMetrics passwordMetrics, boolean z, ComplexityBucket complexityBucket) {
        PasswordMetrics passwordMetrics2 = new PasswordMetrics(passwordMetrics);
        if (!complexityBucket.canHaveSequence()) {
            passwordMetrics2.seqLength = Math.min(passwordMetrics2.seqLength, 3);
        }
        passwordMetrics2.length = Math.max(passwordMetrics2.length, complexityBucket.getMinimumLength(!z));
        return passwordMetrics2;
    }

    public static boolean isNumericOnly(String str) {
        if (str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (categoryChar(str.charAt(i)) != 2) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PasswordMetrics passwordMetrics = (PasswordMetrics) obj;
            if (this.credType == passwordMetrics.credType && this.length == passwordMetrics.length && this.letters == passwordMetrics.letters && this.upperCase == passwordMetrics.upperCase && this.lowerCase == passwordMetrics.lowerCase && this.numeric == passwordMetrics.numeric && this.symbols == passwordMetrics.symbols && this.nonLetter == passwordMetrics.nonLetter && this.nonNumeric == passwordMetrics.nonNumeric && this.seqLength == passwordMetrics.seqLength) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.credType), Integer.valueOf(this.length), Integer.valueOf(this.letters), Integer.valueOf(this.upperCase), Integer.valueOf(this.lowerCase), Integer.valueOf(this.numeric), Integer.valueOf(this.symbols), Integer.valueOf(this.nonLetter), Integer.valueOf(this.nonNumeric), Integer.valueOf(this.seqLength));
    }
}
