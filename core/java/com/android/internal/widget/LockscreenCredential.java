package com.android.internal.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;

import libcore.util.HexEncoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LockscreenCredential implements Parcelable, AutoCloseable {
    public static final Parcelable.Creator<LockscreenCredential> CREATOR =
            new Parcelable.Creator<
                    LockscreenCredential>() { // from class:
                                              // com.android.internal.widget.LockscreenCredential.1
                @Override // android.os.Parcelable.Creator
                public LockscreenCredential createFromParcel(Parcel source) {
                    return new LockscreenCredential(
                            source.readInt(), source.createByteArray(), source.readBoolean());
                }

                @Override // android.os.Parcelable.Creator
                public LockscreenCredential[] newArray(int size) {
                    return new LockscreenCredential[size];
                }
            };
    private byte[] mCredential;
    private final boolean mHasInvalidChars;
    private final int mType;

    private LockscreenCredential(int type, byte[] credential, boolean hasInvalidChars) {
        Objects.requireNonNull(credential);
        if (type == -1) {
            Preconditions.checkArgument(credential.length == 0);
        } else {
            Preconditions.checkArgument(type == 3 || type == 4 || type == 1 || type == 6);
        }
        this.mType = type;
        this.mCredential = credential;
        this.mHasInvalidChars = hasInvalidChars;
    }

    private LockscreenCredential(int type, CharSequence credential) {
        this(type, charsToBytesForUnicode(credential), hasInvalidChars(credential));
    }

    public static LockscreenCredential createNone() {
        return new LockscreenCredential(-1, new byte[0], false);
    }

    public static LockscreenCredential createPattern(List<LockPatternView.Cell> pattern) {
        return new LockscreenCredential(1, LockPatternUtils.patternToByteArray(pattern), false);
    }

    public static LockscreenCredential createPassword(CharSequence password) {
        return new LockscreenCredential(4, password);
    }

    public static LockscreenCredential createUnifiedProfilePassword(byte[] password) {
        return new LockscreenCredential(4, Arrays.copyOf(password, password.length), false);
    }

    public static LockscreenCredential createPin(CharSequence pin) {
        return new LockscreenCredential(3, pin);
    }

    public static LockscreenCredential createPasswordOrNone(CharSequence password) {
        if (TextUtils.isEmpty(password)) {
            return createNone();
        }
        return createPassword(password);
    }

    public static LockscreenCredential createPinOrNone(CharSequence pin) {
        if (TextUtils.isEmpty(pin)) {
            return createNone();
        }
        return createPin(pin);
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
        return new LockscreenCredential(
                this.mType,
                this.mCredential != null
                        ? Arrays.copyOf(this.mCredential, this.mCredential.length)
                        : null,
                this.mHasInvalidChars);
    }

    public void zeroize() {
        if (this.mCredential != null) {
            Arrays.fill(this.mCredential, (byte) 0);
            this.mCredential = null;
        }
    }

    public void validateBasicRequirements() {
        if (this.mHasInvalidChars) {
            throw new IllegalArgumentException("credential contains invalid characters");
        }
        switch (getType()) {
            case 1:
                if (size() < 4) {
                    throw new IllegalArgumentException("pattern must be at least 4 dots long.");
                }
                return;
            case 2:
            default:
                return;
            case 3:
                if (size() < 4) {
                    throw new IllegalArgumentException("PIN must be at least 4 digits long.");
                }
                return;
            case 4:
                if (size() < 4) {
                    throw new IllegalArgumentException(
                            "password must be at least 4 characters long.");
                }
                return;
        }
    }

    public boolean checkAgainstStoredType(int storedCredentialType) {
        return storedCredentialType == 2
                ? getType() == 4 || getType() == 3 || getType() == 6
                : getType() == storedCredentialType;
    }

    public String passwordToHistoryHash(byte[] salt, byte[] hashFactor) {
        return passwordToHistoryHash(this.mCredential, salt, hashFactor);
    }

    public static String passwordToHistoryHash(
            byte[] passwordToHash, byte[] salt, byte[] hashFactor) {
        if (passwordToHash == null
                || passwordToHash.length == 0
                || hashFactor == null
                || salt == null) {
            return null;
        }
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(hashFactor);
            sha256.update(passwordToHash);
            sha256.update(salt);
            return HexEncoding.encodeToString(sha256.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Missing digest algorithm: ", e);
        }
    }

    @Deprecated
    public static String legacyPasswordToHash(byte[] password, byte[] salt) {
        if (password == null || password.length == 0 || salt == null) {
            return null;
        }
        try {
            byte[] saltedPassword = ArrayUtils.concat(password, salt);
            byte[] sha1 = MessageDigest.getInstance("SHA-1").digest(saltedPassword);
            byte[] md5 = MessageDigest.getInstance(KeyProperties.DIGEST_MD5).digest(saltedPassword);
            Arrays.fill(saltedPassword, (byte) 0);
            return HexEncoding.encodeToString(ArrayUtils.concat(sha1, md5));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("Missing digest algorithm: ", e);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeByteArray(this.mCredential);
        dest.writeBoolean(this.mHasInvalidChars);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        zeroize();
    }

    public void finalize() {
        zeroize();
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mType),
                Integer.valueOf(Arrays.hashCode(this.mCredential)),
                Boolean.valueOf(this.mHasInvalidChars));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LockscreenCredential)) {
            return false;
        }
        LockscreenCredential other = (LockscreenCredential) o;
        return this.mType == other.mType
                && Arrays.equals(this.mCredential, other.mCredential)
                && this.mHasInvalidChars == other.mHasInvalidChars;
    }

    private static boolean hasInvalidChars(CharSequence chars) {
        for (int i = 0; i < chars.length(); i++) {
            char c = chars.charAt(i);
            if (c < ' ' || c > 127) {
                return true;
            }
        }
        return false;
    }

    private static byte[] charsToBytesTruncating(CharSequence chars) {
        byte[] bytes = new byte[chars.length()];
        for (int i = 0; i < chars.length(); i++) {
            bytes[i] = (byte) chars.charAt(i);
        }
        return bytes;
    }

    private static byte[] charsToBytesForUnicode(CharSequence chars) {
        byte[] bytes = new byte[chars.length() * 2];
        int pos = 0;
        int i = 0;
        while (i < chars.length()) {
            char c = chars.charAt(i);
            if (c > 255) {
                bytes[pos] = (byte) (c >> '\b');
                pos++;
            }
            bytes[pos] = (byte) c;
            i++;
            pos++;
        }
        byte[] bytesCopy = Arrays.copyOf(bytes, pos);
        Arrays.fill(bytes, (byte) 0);
        return bytesCopy;
    }

    public static LockscreenCredential streamCredential(int type, byte[] credential) {
        return new LockscreenCredential(
                type,
                credential != null ? Arrays.copyOf(credential, credential.length) : null,
                false);
    }

    public static LockscreenCredential createSmartcardPassword(byte[] password) {
        return new LockscreenCredential(6, Arrays.copyOf(password, password.length), false);
    }

    public boolean isUCM() {
        ensureNotZeroized();
        return this.mType == 6;
    }
}
