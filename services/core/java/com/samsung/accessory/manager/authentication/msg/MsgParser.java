package com.samsung.accessory.manager.authentication.msg;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.Slog;

import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MsgParser {
    public byte[] extraData;
    public KeyInformation keyInform;
    public Message mMsg;
    public MsgHelper mMsgHelper;
    public byte[] managerUrl;
    public byte productId;
    public String publicKey;
    public byte[] randNum;
    public byte[] serialNumber;
    public byte[] urlExtra;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KeyInformation {
        public byte[] chipPubKey;
        public int keySize;
        public byte[] rootPriv_Sig_r;
        public byte[] rootPriv_Sig_s;
        public int signatureSize;
        public byte[] signedRandVal_r;
        public byte[] signedRandVal_s;
    }

    public static String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < bArr.length) {
            i =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%02x", new Object[] {Byte.valueOf(bArr[i])}, sb, i, 1);
        }
        return sb.toString();
    }

    public final boolean checkPubKey(int i, boolean z) {
        KeyInformation keyInformation = this.keyInform;
        if (this.mMsg.getData().length != keyInformation.keySize + keyInformation.signatureSize) {
            return false;
        }
        KeyInformation keyInformation2 = this.keyInform;
        byte[] data = this.mMsg.getData();
        byte[] bArr = keyInformation2.chipPubKey;
        int i2 = keyInformation2.keySize;
        System.arraycopy(data, 0, bArr, 0, i2);
        int i3 = 2;
        int i4 = keyInformation2.signatureSize / 2;
        System.arraycopy(data, i2, keyInformation2.rootPriv_Sig_r, 0, i4);
        System.arraycopy(data, i2 + i4, keyInformation2.rootPriv_Sig_s, 0, i4);
        if (z) {
            i3 = 3;
        } else if (i == 2) {
            i3 = 1;
        }
        boolean verify_certificate =
                this.mMsgHelper.verify_certificate(
                        i3,
                        byteArrayToString(this.keyInform.chipPubKey),
                        byteArrayToString(this.keyInform.rootPriv_Sig_r),
                        byteArrayToString(this.keyInform.rootPriv_Sig_s));
        if (verify_certificate) {
            this.publicKey = byteArrayToString(this.keyInform.chipPubKey);
        }
        return verify_certificate;
    }

    public final boolean parseData(int i, byte[] bArr, boolean z) {
        boolean z2;
        if (i == 1) {
            Message message = new Message(i, bArr);
            this.mMsg = message;
            if (message.getData().length < 2) {
                return false;
            }
            this.productId = this.mMsg.getData()[1];
            Slog.secD(
                    "SAccessoryManager_MsgParser",
                    "productId = " + Integer.toHexString(this.productId) + "h");
            byte b = this.productId;
            KeyInformation keyInformation = new KeyInformation();
            int i2 = b & 255;
            if (i2 == 51 || i2 == 85) {
                keyInformation.keySize = 40;
                keyInformation.signatureSize = 42;
                keyInformation.chipPubKey = new byte[40];
                keyInformation.rootPriv_Sig_r = new byte[21];
                keyInformation.rootPriv_Sig_s = new byte[21];
                keyInformation.signedRandVal_r = new byte[21];
                keyInformation.signedRandVal_s = new byte[21];
            }
            this.keyInform = keyInformation;
            return true;
        }
        if (i == 2) {
            this.mMsg = new Message(i, bArr);
            boolean checkPubKey = checkPubKey(2, z);
            Slog.secD("SAccessoryManager_MsgParser", "command " + i + ":" + checkPubKey);
            return checkPubKey;
        }
        if (i == 3) {
            Message message2 = new Message(i, bArr);
            this.mMsg = message2;
            if (message2.getData().length == this.keyInform.signatureSize) {
                KeyInformation keyInformation2 = this.keyInform;
                byte[] data = this.mMsg.getData();
                int i3 = keyInformation2.signatureSize / 2;
                System.arraycopy(data, 0, keyInformation2.signedRandVal_r, 0, i3);
                System.arraycopy(data, i3, keyInformation2.signedRandVal_s, 0, i3);
                z2 =
                        this.mMsgHelper.verify_rand_signature(
                                byteArrayToString(this.keyInform.chipPubKey),
                                this.randNum,
                                byteArrayToString(this.keyInform.signedRandVal_r),
                                byteArrayToString(this.keyInform.signedRandVal_s));
            } else {
                Slog.e("SAccessoryManager_MsgParser", "signature is invalid");
                z2 = false;
            }
            Slog.secD("SAccessoryManager_MsgParser", "command " + i + ":" + z2);
            return z2;
        }
        if (i == 5) {
            Message message3 = new Message(i, bArr);
            this.mMsg = message3;
            this.serialNumber = message3.getData();
            return true;
        }
        switch (i) {
            case 8:
                this.mMsg = new Message(i, bArr);
                return checkPubKey(8, z);
            case 9:
                Message message4 = new Message(i, bArr);
                this.mMsg = message4;
                byte[] data2 = message4.getData();
                this.urlExtra = data2;
                int length = data2.length;
                byte[] bArr2 = new byte[length + 1];
                this.managerUrl = bArr2;
                bArr2[0] = (byte) length;
                System.arraycopy(data2, 0, bArr2, 1, length);
                Slog.secD(
                        "SAccessoryManager_MsgParser",
                        "url: " + byteArrayToString(this.managerUrl));
                return true;
            case 10:
                Message message5 = new Message(i, bArr);
                this.mMsg = message5;
                byte[] data3 = message5.getData();
                this.urlExtra = data3;
                int length2 = data3.length;
                byte[] bArr3 = new byte[length2 + 1];
                this.extraData = bArr3;
                bArr3[0] = (byte) length2;
                System.arraycopy(data3, 0, bArr3, 1, length2);
                Slog.secD(
                        "SAccessoryManager_MsgParser",
                        "extra Data: " + byteArrayToString(this.extraData));
                return true;
            case 11:
                Message message6 = new Message(i, bArr);
                this.mMsg = message6;
                byte[] data4 = message6.getData();
                this.urlExtra = data4;
                int i4 =
                        (data4[0] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                                + 1;
                int i5 = data4[i4] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
                byte[] bArr4 = new byte[i4];
                this.managerUrl = bArr4;
                int i6 = i5 + 1;
                this.extraData = new byte[i6];
                System.arraycopy(data4, 0, bArr4, 0, i4);
                System.arraycopy(this.urlExtra, i4, this.extraData, 0, i6);
                Slog.secD(
                        "SAccessoryManager_MsgParser",
                        "url: " + byteArrayToString(this.managerUrl));
                Slog.secD(
                        "SAccessoryManager_MsgParser",
                        "extra Data: " + byteArrayToString(this.extraData));
                return true;
            default:
                return true;
        }
    }
}
