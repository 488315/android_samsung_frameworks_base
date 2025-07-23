package android.privacy.internal.rappor;

import android.privacy.DifferentialPrivacyEncoder;
import com.android.internal.midi.MidiConstants;
import com.google.android.rappor.Encoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes3.dex */
public class RapporEncoder implements DifferentialPrivacyEncoder {
    private static final byte[] INSECURE_SECRET = {-41, 104, -103, -109, -108, 19, 83, 84, -2, MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -2, MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -41, 104, -103, -109, -108, 19, 83, 84, -2, MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -2, MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -41, 104, -103, -109, -108, 19, 83, 84, -2, MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84, -2, MidiConstants.STATUS_CHANNEL_PRESSURE, 126, 84};
    private static final SecureRandom sSecureRandom = new SecureRandom();
    private final RapporConfig mConfig;
    private final Encoder mEncoder;
    private final boolean mIsSecure;

    private RapporEncoder(RapporConfig rapporConfig, boolean z, byte[] bArr) {
        Random random;
        byte[] bArr2;
        this.mConfig = rapporConfig;
        this.mIsSecure = z;
        if (z) {
            random = sSecureRandom;
            bArr2 = bArr;
        } else {
            random = new Random(getInsecureSeed(rapporConfig.mEncoderId));
            bArr2 = INSECURE_SECRET;
        }
        this.mEncoder = new Encoder(random, (MessageDigest) null, (MessageDigest) null, bArr2, rapporConfig.mEncoderId, rapporConfig.mNumBits, rapporConfig.mProbabilityF, rapporConfig.mProbabilityP, rapporConfig.mProbabilityQ, rapporConfig.mNumCohorts, rapporConfig.mNumBloomHashes);
    }

    private long getInsecureSeed(String str) {
        try {
            return ByteBuffer.wrap(MessageDigest.getInstance("SHA-256").digest(str.getBytes(StandardCharsets.UTF_8))).getLong();
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError("Unable generate insecure seed");
        }
    }

    public static RapporEncoder createEncoder(RapporConfig rapporConfig, byte[] bArr) {
        return new RapporEncoder(rapporConfig, true, bArr);
    }

    public static RapporEncoder createInsecureEncoderForTest(RapporConfig rapporConfig) {
        return new RapporEncoder(rapporConfig, false, null);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeString(String str) {
        return this.mEncoder.encodeString(str);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBoolean(boolean z) {
        return this.mEncoder.encodeBoolean(z);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public byte[] encodeBits(byte[] bArr) {
        return this.mEncoder.encodeBits(bArr);
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public RapporConfig getConfig() {
        return this.mConfig;
    }

    @Override // android.privacy.DifferentialPrivacyEncoder
    public boolean isInsecureEncoderForTest() {
        return !this.mIsSecure;
    }
}
