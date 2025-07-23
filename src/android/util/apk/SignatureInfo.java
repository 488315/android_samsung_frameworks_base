package android.util.apk;

import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class SignatureInfo {
    public final long apkSigningBlockOffset;
    public final long centralDirOffset;
    public final ByteBuffer eocd;
    public final long eocdOffset;
    public final ByteBuffer signatureBlock;

    SignatureInfo(ByteBuffer byteBuffer, long j, long j2, long j3, ByteBuffer byteBuffer2) {
        this.signatureBlock = byteBuffer;
        this.apkSigningBlockOffset = j;
        this.centralDirOffset = j2;
        this.eocdOffset = j3;
        this.eocd = byteBuffer2;
    }
}
