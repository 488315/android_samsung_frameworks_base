package com.android.internal.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.LockPatternView;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import libcore.util.HexEncoding;

/* loaded from: classes6.dex */
public class LockscreenCredential implements Parcelable, AutoCloseable {
    public static final Parcelable.Creator<LockscreenCredential> CREATOR = new Parcelable.Creator<LockscreenCredential>() { // from class: com.android.internal.widget.LockscreenCredential.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LockscreenCredential createFromParcel(Parcel parcel) {
            return new LockscreenCredential(parcel.readInt(), parcel.createByteArray(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LockscreenCredential[] newArray(int i) {
            return new LockscreenCredential[i];
        }
    };
    private byte[] mCredential;
    private final boolean mHasInvalidChars;
    private final int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private LockscreenCredential(int i, byte[] bArr, boolean z) {
        Objects.requireNonNull(bArr);
        if (i == -1) {
            Preconditions.checkArgument(bArr.length == 0);
        } else {
            Preconditions.checkArgument(i == 3 || i == 4 || i == 1 || i == 6);
        }
        this.mType = i;
        this.mCredential = bArr;
        this.mHasInvalidChars = z;
    }

    private LockscreenCredential(int i, CharSequence charSequence) {
        this(i, charsToBytesForUnicode(charSequence), hasInvalidChars(charSequence));
    }

    public static LockscreenCredential createNone() {
        return new LockscreenCredential(-1, new byte[0], false);
    }

    public static LockscreenCredential createPattern(List<LockPatternView.Cell> list) {
        return new LockscreenCredential(1, LockPatternUtils.patternToByteArray(list), false);
    }

    public static LockscreenCredential createPassword(CharSequence charSequence) {
        return new LockscreenCredential(4, charSequence);
    }

    public static LockscreenCredential createUnifiedProfilePassword(byte[] bArr) {
        return new LockscreenCredential(4, copyOfArrayNonMovable(bArr), false);
    }

    public static LockscreenCredential createPin(CharSequence charSequence) {
        return new LockscreenCredential(3, charSequence);
    }

    public static LockscreenCredential createPasswordOrNone(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return createNone();
        }
        return createPassword(charSequence);
    }

    public static LockscreenCredential createPinOrNone(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return createNone();
        }
        return createPin(charSequence);
    }

    private void ensureNotZeroized() {
        Preconditions.checkState(this.mCredential != null, "Credential is already zeroized");
    }

    public int getType() {
        ensureNotZeroized();
        return this.mType;
    }

    public byte[] getCredential() {
        ensureNotZeroized();
        return this.mCredential;
    }

    public boolean isNone() {
        ensureNotZeroized();
        return this.mType == -1;
    }

    public boolean isPattern() {
        ensureNotZeroized();
        return this.mType == 1;
    }

    public boolean isPin() {
        ensureNotZeroized();
        return this.mType == 3;
    }

    public boolean isPassword() {
        ensureNotZeroized();
        return this.mType == 4;
    }

    public int size() {
        ensureNotZeroized();
        return this.mCredential.length;
    }

    public boolean hasInvalidChars() {
        ensureNotZeroized();
        return this.mHasInvalidChars;
    }

    public LockscreenCredential duplicate() {
        int i = this.mType;
        byte[] bArr = this.mCredential;
        return new LockscreenCredential(i, bArr != null ? copyOfArrayNonMovable(bArr) : null, this.mHasInvalidChars);
    }

    public void zeroize() {
        byte[] bArr = this.mCredential;
        if (bArr != null) {
            LockPatternUtils.zeroize(bArr);
            this.mCredential = null;
        }
    }

    private static byte[] copyOfArrayNonMovable(byte[] bArr) {
        byte[] bArrNewNonMovableByteArray = LockPatternUtils.newNonMovableByteArray(bArr.length);
        System.arraycopy(bArr, 0, bArrNewNonMovableByteArray, 0, bArr.length);
        return bArrNewNonMovableByteArray;
    }

    public void validateBasicRequirements() {
        if (this.mHasInvalidChars) {
            throw new IllegalArgumentException("credential contains invalid characters");
        }
        int type = getType();
        if (type == 1) {
            if (size() < 4) {
                throw new IllegalArgumentException("pattern must be at least 4 dots long.");
            }
        } else if (type == 3) {
            if (size() < 4) {
                throw new IllegalArgumentException("PIN must be at least 4 digits long.");
            }
        } else if (type == 4 && size() < 4) {
            throw new IllegalArgumentException("password must be at least 4 characters long.");
        }
    }

    public boolean checkAgainstStoredType(int i) {
        return i == 2 ? getType() == 4 || getType() == 3 || getType() == 6 : getType() == i;
    }

    public String passwordToHistoryHash(byte[] bArr, byte[] bArr2) {
        return passwordToHistoryHash(this.mCredential, bArr, bArr2);
    }

    public static String passwordToHistoryHash(byte[] bArr, byte[] bArr2, byte[] bArr3) throws NoSuchAlgorithmException {
        if (bArr == null || bArr.length == 0 || bArr3 == null || bArr2 == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bArr3);
            messageDigest.update(bArr);
            messageDigest.update(bArr2);
            return HexEncoding.encodeToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Missing digest algorithm: ", e);
        }
    }

    @Deprecated
    public static String legacyPasswordToHash(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length == 0 || bArr2 == null) {
            return null;
        }
        try {
            byte[] bArrConcat = ArrayUtils.concat(bArr, bArr2);
            byte[] bArrDigest = MessageDigest.getInstance("SHA-1").digest(bArrConcat);
            byte[] bArrDigest2 = MessageDigest.getInstance(KeyProperties.DIGEST_MD5).digest(bArrConcat);
            LockPatternUtils.zeroize(bArrConcat);
            return HexEncoding.encodeToString(ArrayUtils.concat(bArrDigest, bArrDigest2));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Missing digest algorithm: ", e);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeByteArray(this.mCredential);
        parcel.writeBoolean(this.mHasInvalidChars);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        zeroize();
    }

    public void finalize() {
        zeroize();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mType), Integer.valueOf(Arrays.hashCode(this.mCredential)), Boolean.valueOf(this.mHasInvalidChars));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LockscreenCredential)) {
            return false;
        }
        LockscreenCredential lockscreenCredential = (LockscreenCredential) obj;
        return this.mType == lockscreenCredential.mType && Arrays.equals(this.mCredential, lockscreenCredential.mCredential) && this.mHasInvalidChars == lockscreenCredential.mHasInvalidChars;
    }

    private static boolean hasInvalidChars(CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt < ' ' || cCharAt > 127) {
                return true;
            }
        }
        return false;
    }

    private static byte[] charsToBytesTruncating(CharSequence charSequence) {
        byte[] bArrNewNonMovableByteArray = LockPatternUtils.newNonMovableByteArray(charSequence.length());
        for (int i = 0; i < charSequence.length(); i++) {
            bArrNewNonMovableByteArray[i] = (byte) charSequence.charAt(i);
        }
        return bArrNewNonMovableByteArray;
    }

    private static byte[] charsToBytesForUnicode(CharSequence charSequence) {
        byte[] bArr = new byte[charSequence.length() * 2];
        int i = 0;
        int i2 = 0;
        while (i < charSequence.length()) {
            char cCharAt = charSequence.charAt(i);
            if (cCharAt > 255) {
                bArr[i2] = (byte) (cCharAt >> '\b');
                i2++;
            }
            bArr[i2] = (byte) cCharAt;
            i++;
            i2++;
        }
        byte[] bArrCopyOf = Arrays.copyOf(bArr, i2);
        Arrays.fill(bArr, (byte) 0);
        return bArrCopyOf;
    }

    public static LockscreenCredential streamCredential(int i, byte[] bArr) {
        return new LockscreenCredential(i, bArr != null ? Arrays.copyOf(bArr, bArr.length) : null, false);
    }

    public static LockscreenCredential createSmartcardPassword(byte[] bArr) {
        return new LockscreenCredential(6, Arrays.copyOf(bArr, bArr.length), false);
    }

    public boolean isUCM() {
        ensureNotZeroized();
        return this.mType == 6;
    }
}
