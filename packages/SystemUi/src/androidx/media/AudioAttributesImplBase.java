package androidx.media;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AudioAttributesImplBase implements AudioAttributesImpl {
    public int mContentType;
    public int mFlags;
    public int mLegacyStream;
    public int mUsage;

    public AudioAttributesImplBase() {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mFlags = 0;
        this.mLegacyStream = -1;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesImplBase)) {
            return false;
        }
        AudioAttributesImplBase audioAttributesImplBase = (AudioAttributesImplBase) obj;
        return this.mContentType == audioAttributesImplBase.mContentType && this.mFlags == audioAttributesImplBase.getFlags() && this.mUsage == audioAttributesImplBase.mUsage && this.mLegacyStream == audioAttributesImplBase.mLegacyStream;
    }

    @Override // androidx.media.AudioAttributesImpl
    public final Object getAudioAttributes() {
        return null;
    }

    public final int getFlags() {
        int i;
        int i2 = this.mFlags;
        int i3 = this.mLegacyStream;
        if (i3 == -1) {
            int i4 = this.mUsage;
            int i5 = AudioAttributesCompat.$r8$clinit;
            if ((i2 & 1) != 1) {
                if ((i2 & 4) != 4) {
                    switch (i4) {
                        case 2:
                            i = 0;
                            i3 = i;
                            break;
                        case 3:
                            i = 8;
                            i3 = i;
                            break;
                        case 4:
                            i3 = 4;
                            break;
                        case 5:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                            i = 5;
                            i3 = i;
                            break;
                        case 6:
                            i = 2;
                            i3 = i;
                            break;
                        case 11:
                            i = 10;
                            i3 = i;
                            break;
                        case 12:
                        default:
                            i = 3;
                            i3 = i;
                            break;
                        case 13:
                            i3 = 1;
                            break;
                    }
                } else {
                    i3 = 6;
                }
            } else {
                i3 = 7;
            }
        }
        if (i3 == 6) {
            i2 |= 4;
        } else if (i3 == 7) {
            i2 |= 1;
        }
        return i2 & IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastExternal;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), Integer.valueOf(this.mLegacyStream)});
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (this.mLegacyStream != -1) {
            sb.append(" stream=");
            sb.append(this.mLegacyStream);
            sb.append(" derived");
        }
        sb.append(" usage=");
        int i = this.mUsage;
        int i2 = AudioAttributesCompat.$r8$clinit;
        switch (i) {
            case 0:
                str = "USAGE_UNKNOWN";
                break;
            case 1:
                str = "USAGE_MEDIA";
                break;
            case 2:
                str = "USAGE_VOICE_COMMUNICATION";
                break;
            case 3:
                str = "USAGE_VOICE_COMMUNICATION_SIGNALLING";
                break;
            case 4:
                str = "USAGE_ALARM";
                break;
            case 5:
                str = "USAGE_NOTIFICATION";
                break;
            case 6:
                str = "USAGE_NOTIFICATION_RINGTONE";
                break;
            case 7:
                str = "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
                break;
            case 8:
                str = "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
                break;
            case 9:
                str = "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
                break;
            case 10:
                str = "USAGE_NOTIFICATION_EVENT";
                break;
            case 11:
                str = "USAGE_ASSISTANCE_ACCESSIBILITY";
                break;
            case 12:
                str = "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
                break;
            case 13:
                str = "USAGE_ASSISTANCE_SONIFICATION";
                break;
            case 14:
                str = "USAGE_GAME";
                break;
            case 15:
            default:
                str = AbstractC0000x2c234b15.m0m("unknown usage ", i);
                break;
            case 16:
                str = "USAGE_ASSISTANT";
                break;
        }
        sb.append(str);
        sb.append(" content=");
        sb.append(this.mContentType);
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.mFlags).toUpperCase());
        return sb.toString();
    }

    public AudioAttributesImplBase(int i, int i2, int i3, int i4) {
        this.mContentType = i;
        this.mFlags = i2;
        this.mUsage = i3;
        this.mLegacyStream = i4;
    }
}
