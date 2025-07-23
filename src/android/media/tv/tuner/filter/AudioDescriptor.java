package android.media.tv.tuner.filter;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes3.dex */
public class AudioDescriptor {
    private final byte mAdFade;
    private final byte mAdGainCenter;
    private final byte mAdGainFront;
    private final byte mAdGainSurround;
    private final byte mAdPan;
    private final char mVersionTextTag;

    private AudioDescriptor(byte b, byte b2, char c, byte b3, byte b4, byte b5) {
        this.mAdFade = b;
        this.mAdPan = b2;
        this.mVersionTextTag = c;
        this.mAdGainCenter = b3;
        this.mAdGainFront = b4;
        this.mAdGainSurround = b5;
    }

    public byte getAdFade() {
        return this.mAdFade;
    }

    public byte getAdPan() {
        return this.mAdPan;
    }

    public char getAdVersionTextTag() {
        return this.mVersionTextTag;
    }

    public byte getAdGainCenter() {
        return this.mAdGainCenter;
    }

    public byte getAdGainFront() {
        return this.mAdGainFront;
    }

    public byte getAdGainSurround() {
        return this.mAdGainSurround;
    }
}
