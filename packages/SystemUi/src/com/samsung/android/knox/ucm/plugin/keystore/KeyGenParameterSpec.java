package com.samsung.android.knox.ucm.plugin.keystore;

import android.os.Bundle;
import java.security.spec.AlgorithmParameterSpec;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class KeyGenParameterSpec implements AlgorithmParameterSpec {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Builder {
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

        public final KeyGenParameterSpec build() {
            return new KeyGenParameterSpec(this.mKeystoreAlias, this.mKeySize, this.mSourceUid, this.mIsManaged, this.mResourceId, this.mOwnerUid, this.mAlgorithm, this.mPurposes, this.mIsRandomizedEncryptionRequired, this.mEcCurveName, this.mBlockModes, this.mDigests, this.mSignaturePaddings, this.mOptions, 0);
        }

        public final Builder setAlgorithm(String str) {
            this.mAlgorithm = str;
            return this;
        }

        public final Builder setBlockModes(String[] strArr) {
            this.mBlockModes = strArr;
            return this;
        }

        public final Builder setDigests(String[] strArr) {
            this.mDigests = strArr;
            return this;
        }

        public final Builder setEcCurveName(String str) {
            this.mEcCurveName = str;
            return this;
        }

        public final Builder setOptions(Bundle bundle) {
            this.mOptions = bundle;
            return this;
        }

        public final Builder setPurpose(int i) {
            this.mPurposes = i;
            return this;
        }

        public final Builder setRandomizedEncryptionRequired(boolean z) {
            this.mIsRandomizedEncryptionRequired = z;
            return this;
        }

        public final Builder setSignaturePaddings(String[] strArr) {
            this.mSignaturePaddings = strArr;
            return this;
        }
    }

    public /* synthetic */ KeyGenParameterSpec(String str, int i, int i2, boolean z, int i3, int i4, String str2, int i5, boolean z2, String str3, String[] strArr, String[] strArr2, String[] strArr3, Bundle bundle, int i6) {
        this(str, i, i2, z, i3, i4, str2, i5, z2, str3, strArr, strArr2, strArr3, bundle);
    }

    public final String getAlgorithm() {
        return this.mAlgorithm;
    }

    public final String[] getBlockModes() {
        return this.mBlockModes;
    }

    public final String[] getDigests() {
        return this.mDigests;
    }

    public final String getEcCurveName() {
        return this.mEcCurveName;
    }

    public final int getKeySize() {
        return this.mKeySize;
    }

    public final String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    public final Bundle getOptions() {
        return this.mOptions;
    }

    public final int getOwnerUid() {
        return this.mOwnerUid;
    }

    public final int getPurposes() {
        return this.mPurposes;
    }

    public final int getResourceId() {
        return this.mResourceId;
    }

    public final String[] getSignaturePaddings() {
        return this.mSignaturePaddings;
    }

    public final int getSourceUid() {
        return this.mSourceUid;
    }

    public final boolean isManaged() {
        return this.mIsManaged;
    }

    public final boolean isRandomizedEncryptionRequired() {
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
