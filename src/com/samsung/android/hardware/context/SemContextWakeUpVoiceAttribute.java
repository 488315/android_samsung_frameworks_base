package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextWakeUpVoiceAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextWakeUpVoiceAttribute> CREATOR = new Parcelable.Creator<SemContextWakeUpVoiceAttribute>() { // from class: com.samsung.android.hardware.context.SemContextWakeUpVoiceAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWakeUpVoiceAttribute createFromParcel(Parcel parcel) {
            return new SemContextWakeUpVoiceAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWakeUpVoiceAttribute[] newArray(int i) {
            return new SemContextWakeUpVoiceAttribute[i];
        }
    };
    private static final int MODE_REFERENCE_DATA = 1;
    private static final int MODE_REGISTER = 0;
    private static final String TAG = "SemContextWakeUpVoiceAttribute";
    private byte[] mGramData;
    private int mMode;
    private byte[] mNetData;
    private int mVoiceMode;

    SemContextWakeUpVoiceAttribute() {
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = 0;
        setAttribute();
    }

    SemContextWakeUpVoiceAttribute(Parcel parcel) {
        super(parcel);
        this.mMode = -1;
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
    }

    SemContextWakeUpVoiceAttribute(int i) {
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = 0;
        this.mVoiceMode = i;
        setAttribute();
    }

    public SemContextWakeUpVoiceAttribute(byte[] bArr, byte[] bArr2) {
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = 1;
        byte[] bArr3 = new byte[bArr.length];
        this.mNetData = bArr3;
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        byte[] bArr4 = new byte[bArr2.length];
        this.mGramData = bArr4;
        System.arraycopy(bArr2, 0, bArr4, 0, bArr2.length);
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mMode == 0) {
            int i = this.mVoiceMode;
            return i == 1 || i == 2;
        }
        if (this.mNetData == null) {
            Log.e(TAG, "The net data is null.");
            return false;
        }
        if (this.mGramData != null) {
            return true;
        }
        Log.e(TAG, "The gram data is null.");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", this.mMode);
        if (this.mMode == 0) {
            bundle.putInt("voice_mode", this.mVoiceMode);
        } else {
            bundle.putByteArray("net_data", this.mNetData);
            bundle.putByteArray("gram_data", this.mGramData);
        }
        super.setAttribute(16, bundle);
    }
}
