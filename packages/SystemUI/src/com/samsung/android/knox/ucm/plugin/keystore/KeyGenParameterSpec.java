package com.samsung.android.knox.ucm.plugin.keystore;

import android.os.Bundle;
import java.security.spec.AlgorithmParameterSpec;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class KeyGenParameterSpec implements AlgorithmParameterSpec {
    public final String mAlgorithm;
    public final String[] mBlockModes;
    public final String[] mDigests;
    public final String mEcCurveName;
    public final boolean mIsManaged;
    public final boolean mIsRandomizedEncryptionRequired;
    public final int mKeySize;
    public final String mKeystoreAlias;
    public final Bundle mOptions;
    public final int mOwnerUid;
    public final int mPurposes;
    public final int mResourceId;
    public final String[] mSignaturePaddings;
    public final int mSourceUid;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public String mAlgorithm;
        public String[] mBlockModes;
        public String[] mDigests;
        public String mEcCurveName;
        public boolean mIsManaged;
        public boolean mIsRandomizedEncryptionRequired;
        public int mKeySize;
        public String mKeystoreAlias;
        public Bundle mOptions;
        public int mOwnerUid;
        public int mPurposes;
        public int mResourceId;
        public String[] mSignaturePaddings;
        public int mSourceUid;

        public Builder(String str, int i, int i2, boolean z, int i3, int i4) {
            this.mKeystoreAlias = str;
            this.mKeySize = i;
            this.mSourceUid = i2;
            this.mIsManaged = z;
            this.mResourceId = i3;
            this.mOwnerUid = i4;
        }

        public KeyGenParameterSpec build() {
            return new KeyGenParameterSpec(this.mKeystoreAlias, this.mKeySize, this.mSourceUid, this.mIsManaged, this.mResourceId, this.mOwnerUid, this.mAlgorithm, this.mPurposes, this.mIsRandomizedEncryptionRequired, this.mEcCurveName, this.mBlockModes, this.mDigests, this.mSignaturePaddings, this.mOptions, 0);
        }

        public Builder setAlgorithm(String str) {
            this.mAlgorithm = str;
            return this;
        }

        public Builder setBlockModes(String[] strArr) {
            this.mBlockModes = strArr;
            return this;
        }

        public Builder setDigests(String[] strArr) {
            this.mDigests = strArr;
            return this;
        }

        public Builder setEcCurveName(String str) {
            this.mEcCurveName = str;
            return this;
        }

        public Builder setOptions(Bundle bundle) {
            this.mOptions = bundle;
            return this;
        }

        public Builder setPurpose(int i) {
            this.mPurposes = i;
            return this;
        }

        public Builder setRandomizedEncryptionRequired(boolean z) {
            this.mIsRandomizedEncryptionRequired = z;
            return this;
        }

        public Builder setSignaturePaddings(String[] strArr) {
            this.mSignaturePaddings = strArr;
            return this;
        }
    }

    public /* synthetic */ KeyGenParameterSpec(String str, int i, int i2, boolean z, int i3, int i4, String str2, int i5, boolean z2, String str3, String[] strArr, String[] strArr2, String[] strArr3, Bundle bundle, int i6) {
        this(str, i, i2, z, i3, i4, str2, i5, z2, str3, strArr, strArr2, strArr3, bundle);
    }

    public String getAlgorithm() {
        return this.mAlgorithm;
    }

    public String[] getBlockModes() {
        return this.mBlockModes;
    }

    public String[] getDigests() {
        return this.mDigests;
    }

    public String getEcCurveName() {
        return this.mEcCurveName;
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    public Bundle getOptions() {
        return this.mOptions;
    }

    public int getOwnerUid() {
        return this.mOwnerUid;
    }

    public int getPurposes() {
        return this.mPurposes;
    }

    public int getResourceId() {
        return this.mResourceId;
    }

    public String[] getSignaturePaddings() {
        return this.mSignaturePaddings;
    }

    public int getSourceUid() {
        return this.mSourceUid;
    }

    public boolean isManaged() {
        return this.mIsManaged;
    }

    public boolean isRandomizedEncryptionRequired() {
        return this.mIsRandomizedEncryptionRequired;
    }

    private KeyGenParameterSpec(String str, int i, int i2, boolean z, int i3, int i4, String str2, int i5, boolean z2, String str3, String[] strArr, String[] strArr2, String[] strArr3, Bundle bundle) {
        this.mKeystoreAlias = str;
        this.mKeySize = i;
        this.mSourceUid = i2;
        this.mIsManaged = z;
        this.mResourceId = i3;
        this.mOwnerUid = i4;
        this.mAlgorithm = str2;
        this.mPurposes = i5;
        this.mIsRandomizedEncryptionRequired = z2;
        this.mEcCurveName = str3;
        this.mBlockModes = strArr;
        this.mDigests = strArr2;
        this.mSignaturePaddings = strArr3;
        this.mOptions = bundle;
    }
}
