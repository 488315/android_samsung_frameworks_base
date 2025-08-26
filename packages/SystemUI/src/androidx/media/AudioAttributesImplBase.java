package androidx.media;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Arrays;

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
        if (this.mContentType == audioAttributesImplBase.mContentType) {
            int i = this.mFlags;
            int i2 = audioAttributesImplBase.mFlags;
            int i3 = audioAttributesImplBase.mLegacyStream;
            if (i3 == -1) {
                int i4 = audioAttributesImplBase.mUsage;
                int i5 = AudioAttributesCompat.$r8$clinit;
                if ((i2 & 1) != 1) {
                    if ((i2 & 4) != 4) {
                        switch (i4) {
                            case 2:
                                i3 = 0;
                                break;
                            case 3:
                                i3 = 8;
                                break;
                            case 4:
                                i3 = 4;
                                break;
                            case 5:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                                i3 = 5;
                                break;
                            case 6:
                                i3 = 2;
                                break;
                            case 11:
                                i3 = 10;
                                break;
                            case 12:
                            default:
                                i3 = 3;
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
            if (i == (i2 & IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastExternal) && this.mUsage == audioAttributesImplBase.mUsage && this.mLegacyStream == audioAttributesImplBase.mLegacyStream) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.media.AudioAttributesImpl
    public final Object getAudioAttributes() {
        return null;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), Integer.valueOf(this.mLegacyStream)});
    }

    public final String toString() {
        String strM;
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
                strM = "USAGE_UNKNOWN";
                break;
            case 1:
                strM = "USAGE_MEDIA";
                break;
            case 2:
                strM = "USAGE_VOICE_COMMUNICATION";
                break;
            case 3:
                strM = "USAGE_VOICE_COMMUNICATION_SIGNALLING";
                break;
            case 4:
                strM = "USAGE_ALARM";
                break;
            case 5:
                strM = "USAGE_NOTIFICATION";
                break;
            case 6:
                strM = "USAGE_NOTIFICATION_RINGTONE";
                break;
            case 7:
                strM = "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
                break;
            case 8:
                strM = "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
                break;
            case 9:
                strM = "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
                break;
            case 10:
                strM = "USAGE_NOTIFICATION_EVENT";
                break;
            case 11:
                strM = "USAGE_ASSISTANCE_ACCESSIBILITY";
                break;
            case 12:
                strM = "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
                break;
            case 13:
                strM = "USAGE_ASSISTANCE_SONIFICATION";
                break;
            case 14:
                strM = "USAGE_GAME";
                break;
            case 15:
            default:
                strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "unknown usage ");
                break;
            case 16:
                strM = "USAGE_ASSISTANT";
                break;
        }
        sb.append(strM);
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
