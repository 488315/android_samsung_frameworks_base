package com.samsung.android.hardware.context;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextWakeUpVoiceAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextWakeUpVoiceAttribute> CREATOR = new Parcelable.Creator<SemContextWakeUpVoiceAttribute>() { // from class: com.samsung.android.hardware.context.SemContextWakeUpVoiceAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWakeUpVoiceAttribute createFromParcel(Parcel in) {
            return new SemContextWakeUpVoiceAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextWakeUpVoiceAttribute[] newArray(int size) {
            return new SemContextWakeUpVoiceAttribute[size];
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
        this.mMode = -1;
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = 0;
        setAttribute();
    }

    SemContextWakeUpVoiceAttribute(Parcel src) {
        super(src);
        this.mMode = -1;
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
    }

    SemContextWakeUpVoiceAttribute(int mode) {
        this.mMode = -1;
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = 0;
        this.mVoiceMode = mode;
        setAttribute();
    }

    public SemContextWakeUpVoiceAttribute(byte[] netData, byte[] gramData) {
        this.mMode = -1;
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = 1;
        byte[] bArr = new byte[netData.length];
        this.mNetData = bArr;
        System.arraycopy(netData, 0, bArr, 0, netData.length);
        byte[] bArr2 = new byte[gramData.length];
        this.mGramData = bArr2;
        System.arraycopy(gramData, 0, bArr2, 0, gramData.length);
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mMode == 0) {
            int i = this.mVoiceMode;
            return i == 1 || i == 2;
        }
        if (this.mNetData == null) {
            Log.m96e(TAG, "The net data is null.");
            return false;
        }
        if (this.mGramData != null) {
            return true;
        }
        Log.m96e(TAG, "The gram data is null.");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("mode", this.mMode);
        if (this.mMode == 0) {
            attribute.putInt("voice_mode", this.mVoiceMode);
        } else {
            attribute.putByteArray("net_data", this.mNetData);
            attribute.putByteArray("gram_data", this.mGramData);
        }
        super.setAttribute(16, attribute);
    }
}
