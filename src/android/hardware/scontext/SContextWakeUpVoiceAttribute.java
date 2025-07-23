package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWakeUpVoiceAttribute extends SContextAttribute {
    static int MODE_REFERENCE_DATA = 1;
    static int MODE_REGISTER = 0;
    private static final String TAG = "SContextWakeUpVoiceAttribute";
    private byte[] mGramData;
    private int mMode;
    private byte[] mNetData;
    private int mVoiceMode;

    SContextWakeUpVoiceAttribute() {
        this.mVoiceMode = 1;
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = MODE_REGISTER;
        setAttribute();
    }

    SContextWakeUpVoiceAttribute(int i) {
        this.mNetData = null;
        this.mGramData = null;
        this.mMode = MODE_REGISTER;
        this.mVoiceMode = i;
        setAttribute();
    }

    public SContextWakeUpVoiceAttribute(byte[] bArr, byte[] bArr2) {
        this.mVoiceMode = 1;
        this.mMode = MODE_REFERENCE_DATA;
        this.mNetData = bArr;
        this.mGramData = bArr2;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        if (this.mMode == MODE_REGISTER) {
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
        if (this.mMode == MODE_REGISTER) {
            bundle.putInt("voice_mode", this.mVoiceMode);
        } else {
            bundle.putByteArray("net_data", this.mNetData);
            bundle.putByteArray("gram_data", this.mGramData);
        }
        super.setAttribute(16, bundle);
    }
}
