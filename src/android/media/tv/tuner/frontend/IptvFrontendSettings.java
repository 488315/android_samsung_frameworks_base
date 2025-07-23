package android.media.tv.tuner.frontend;

import android.annotation.SystemApi;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
/* loaded from: classes3.dex */
public final class IptvFrontendSettings extends FrontendSettings {
    public static final int IGMP_UNDEFINED = 0;
    public static final int IGMP_V1 = 1;
    public static final int IGMP_V2 = 2;
    public static final int IGMP_V3 = 4;
    public static final int PROTOCOL_RTP = 2;
    public static final int PROTOCOL_UDP = 1;
    public static final int PROTOCOL_UNDEFINED = 0;
    private final long mBitrate;
    private final String mContentUrl;
    private final byte[] mDstIpAddress;
    private final int mDstPort;
    private final IptvFrontendSettingsFec mFec;
    private final int mIgmp;
    private final int mProtocol;
    private final byte[] mSrcIpAddress;
    private final int mSrcPort;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Igmp {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Protocol {
    }

    @Override // android.media.tv.tuner.frontend.FrontendSettings
    public int getType() {
        return 11;
    }

    private IptvFrontendSettings(byte[] bArr, byte[] bArr2, int i, int i2, IptvFrontendSettingsFec iptvFrontendSettingsFec, int i3, int i4, long j, String str) {
        super(0L);
        this.mSrcIpAddress = bArr;
        this.mDstIpAddress = bArr2;
        this.mSrcPort = i;
        this.mDstPort = i2;
        this.mFec = iptvFrontendSettingsFec;
        this.mProtocol = i3;
        this.mIgmp = i4;
        this.mBitrate = j;
        this.mContentUrl = str;
    }

    public byte[] getSrcIpAddress() {
        return this.mSrcIpAddress;
    }

    public byte[] getDstIpAddress() {
        return this.mDstIpAddress;
    }

    public int getSrcPort() {
        return this.mSrcPort;
    }

    public int getDstPort() {
        return this.mDstPort;
    }

    public IptvFrontendSettingsFec getFec() {
        return this.mFec;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public int getIgmp() {
        return this.mIgmp;
    }

    public long getBitrate() {
        return this.mBitrate;
    }

    public String getContentUrl() {
        return this.mContentUrl;
    }

    public static final class Builder {
        private byte[] mSrcIpAddress = {0, 0, 0, 0};
        private byte[] mDstIpAddress = {0, 0, 0, 0};
        private int mSrcPort = 0;
        private int mDstPort = 0;
        private IptvFrontendSettingsFec mFec = null;
        private int mProtocol = 0;
        private int mIgmp = 0;
        private long mBitrate = 0;
        private String mContentUrl = "";

        public Builder setSrcIpAddress(byte[] bArr) {
            this.mSrcIpAddress = bArr;
            return this;
        }

        public Builder setDstIpAddress(byte[] bArr) {
            this.mDstIpAddress = bArr;
            return this;
        }

        public Builder setSrcPort(int i) {
            this.mSrcPort = i;
            return this;
        }

        public Builder setDstPort(int i) {
            this.mDstPort = i;
            return this;
        }

        public Builder setFec(IptvFrontendSettingsFec iptvFrontendSettingsFec) {
            this.mFec = iptvFrontendSettingsFec;
            return this;
        }

        public Builder setProtocol(int i) {
            this.mProtocol = i;
            return this;
        }

        public Builder setIgmp(int i) {
            this.mIgmp = i;
            return this;
        }

        public Builder setBitrate(long j) {
            this.mBitrate = j;
            return this;
        }

        public Builder setContentUrl(String str) {
            this.mContentUrl = str;
            return this;
        }

        public IptvFrontendSettings build() {
            return new IptvFrontendSettings(this.mSrcIpAddress, this.mDstIpAddress, this.mSrcPort, this.mDstPort, this.mFec, this.mProtocol, this.mIgmp, this.mBitrate, this.mContentUrl);
        }
    }
}
