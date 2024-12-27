package com.android.server.locksettings;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Objects;

import javax.crypto.SecretKey;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RebootEscrowData {
    public final byte mSpVersion;
    public final byte[] mSyntheticPassword;

    public RebootEscrowData(byte b, byte[] bArr, byte[] bArr2) {
        this.mSpVersion = b;
        this.mSyntheticPassword = bArr;
    }

    public static RebootEscrowData fromEncryptedData(
            RebootEscrowKey rebootEscrowKey, byte[] bArr, SecretKey secretKey) {
        Objects.requireNonNull(bArr);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        int readInt = dataInputStream.readInt();
        byte readByte = dataInputStream.readByte();
        if (readInt == 1) {
            return new RebootEscrowData(
                    readByte,
                    AesEncryptionUtil.decrypt(rebootEscrowKey.mKey, dataInputStream),
                    bArr);
        }
        if (readInt != 2) {
            throw new IOException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(readInt, "Unsupported version "));
        }
        if (secretKey == null) {
            throw new IOException(
                    "Failed to find wrapper key in keystore, cannot decrypt the escrow data");
        }
        byte[] decrypt = AesEncryptionUtil.decrypt(secretKey, dataInputStream);
        SecretKey secretKey2 = rebootEscrowKey.mKey;
        Objects.requireNonNull(secretKey2);
        Objects.requireNonNull(decrypt);
        return new RebootEscrowData(
                readByte,
                AesEncryptionUtil.decrypt(
                        secretKey2, new DataInputStream(new ByteArrayInputStream(decrypt))),
                bArr);
    }
}
