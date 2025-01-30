package com.samsung.accessory.manager.authentication.msg;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils;

/* loaded from: classes.dex */
public class MsgParser {
    public byte[] extraData;
    public KeyInformation keyInform;
    public Message mMsg;
    public byte[] managerUrl;
    public byte productId;
    public String publicKey;
    public byte[] serialNumber;
    public byte[] urlExtra;
    public String TAG = "SAccessoryManager_MsgParser";
    public final int ROOTPUBKEY = 1;
    public final int SEC_ROOTPUBKEY = 2;
    public final int SEC_UBIVELOX_ROOTPUBKEY = 3;
    public final int RES_ATQS = 1;
    public final int RES_PUB_KEY = 2;
    public final int RES_VERIFICATION = 3;
    public final int RES_WRITE_ID = 4;
    public final int RES_READ_ID = 5;
    public final int RES_FIRMWARE = 6;
    public final int RES_KEY_CHANGE = 7;
    public final int RES_SEC_PUB_KEY = 8;
    public final int RES_REQ_URL = 9;
    public final int RES_REQ_EXTRA = 10;
    public final int RES_REQ_UNIFIED3RD = 11;
    public byte[] randNum = new byte[16];
    public MsgHelper mMsgHelper = new MsgHelper();

    public class KeyInformation {
        public byte[] chipPubKey;
        public int keySize;
        public byte[] rootPriv_Sig_r;
        public byte[] rootPriv_Sig_s;
        public int signatureSize;
        public byte[] signedRandVal_r;
        public byte[] signedRandVal_s;

        public void setCertificateOfChip(byte[] bArr) {
            System.arraycopy(bArr, 0, this.chipPubKey, 0, this.keySize);
            System.arraycopy(bArr, this.keySize, this.rootPriv_Sig_r, 0, this.signatureSize / 2);
            int i = this.keySize;
            int i2 = this.signatureSize;
            System.arraycopy(bArr, i + (i2 / 2), this.rootPriv_Sig_s, 0, i2 / 2);
        }

        public void setSignatueOfRandomValue(byte[] bArr) {
            System.arraycopy(bArr, 0, this.signedRandVal_r, 0, this.signatureSize / 2);
            int i = this.signatureSize;
            System.arraycopy(bArr, i / 2, this.signedRandVal_s, 0, i / 2);
        }

        public boolean isValidCertificate(int i) {
            return i == this.keySize + this.signatureSize;
        }

        public boolean isValidSignature(int i) {
            return i == this.signatureSize;
        }

        public KeyInformation(byte b) {
            int i = b & 255;
            if (i == 51 || i == 85) {
                this.keySize = 40;
                this.signatureSize = 42;
                this.chipPubKey = new byte[40];
                this.rootPriv_Sig_r = new byte[42 / 2];
                this.rootPriv_Sig_s = new byte[42 / 2];
                this.signedRandVal_r = new byte[42 / 2];
                this.signedRandVal_s = new byte[42 / 2];
            }
        }
    }

    public boolean parseData(int i, byte[] bArr) {
        return parseData(i, bArr, false);
    }

    public boolean parseData(int i, byte[] bArr, boolean z) {
        if (i == 1) {
            this.mMsg = new Message(i, bArr);
            return checkAtqs();
        }
        if (i == 2) {
            this.mMsg = new Message(i, bArr);
            boolean checkPubKey = checkPubKey(2, z);
            Slog.secD(this.TAG, "command " + i + XmlUtils.STRING_ARRAY_SEPARATOR + checkPubKey);
            return checkPubKey;
        }
        if (i == 3) {
            this.mMsg = new Message(i, bArr);
            boolean checkVerification = checkVerification();
            Slog.secD(this.TAG, "command " + i + XmlUtils.STRING_ARRAY_SEPARATOR + checkVerification);
            return checkVerification;
        }
        if (i == 5) {
            this.mMsg = new Message(i, bArr);
            return checkReadId();
        }
        switch (i) {
            case 8:
                this.mMsg = new Message(i, bArr);
                return checkPubKey(8, z);
            case 9:
                this.mMsg = new Message(i, bArr);
                return checkUrl();
            case 10:
                this.mMsg = new Message(i, bArr);
                return checkExtra();
            case 11:
                this.mMsg = new Message(i, bArr);
                return checkUnified3rd();
            default:
                return true;
        }
    }

    public boolean checkAtqs() {
        if (this.mMsg.getData().length < 2) {
            return false;
        }
        this.productId = this.mMsg.getData()[1];
        Slog.secD(this.TAG, "productId = " + Integer.toHexString(this.productId) + "h");
        this.keyInform = new KeyInformation(this.productId);
        return true;
    }

    public boolean checkPubKey(int i, boolean z) {
        if (!this.keyInform.isValidCertificate(this.mMsg.getData().length)) {
            return false;
        }
        this.keyInform.setCertificateOfChip(this.mMsg.getData());
        boolean verify_certificate = this.mMsgHelper.verify_certificate(z ? 3 : i == 2 ? 1 : 2, byteArrayToString(this.keyInform.chipPubKey), byteArrayToString(this.keyInform.rootPriv_Sig_r), byteArrayToString(this.keyInform.rootPriv_Sig_s));
        if (!verify_certificate) {
            return verify_certificate;
        }
        this.publicKey = byteArrayToString(this.keyInform.chipPubKey);
        return verify_certificate;
    }

    public boolean checkVerification() {
        if (this.keyInform.isValidSignature(this.mMsg.getData().length)) {
            this.keyInform.setSignatueOfRandomValue(this.mMsg.getData());
            return this.mMsgHelper.verify_rand_signature(byteArrayToString(this.keyInform.chipPubKey), this.randNum, byteArrayToString(this.keyInform.signedRandVal_r), byteArrayToString(this.keyInform.signedRandVal_s));
        }
        Slog.e(this.TAG, "signature is invalid");
        return false;
    }

    public boolean checkReadId() {
        this.serialNumber = this.mMsg.getData();
        return true;
    }

    public boolean checkUrl() {
        byte[] data = this.mMsg.getData();
        this.urlExtra = data;
        int length = data.length;
        byte[] bArr = new byte[length + 1];
        this.managerUrl = bArr;
        bArr[0] = (byte) length;
        System.arraycopy(data, 0, bArr, 1, length);
        Slog.secD(this.TAG, "url: " + byteArrayToString(this.managerUrl));
        return true;
    }

    public boolean checkExtra() {
        byte[] data = this.mMsg.getData();
        this.urlExtra = data;
        int length = data.length;
        byte[] bArr = new byte[length + 1];
        this.extraData = bArr;
        bArr[0] = (byte) length;
        System.arraycopy(data, 0, bArr, 1, length);
        Slog.secD(this.TAG, "extra Data: " + byteArrayToString(this.extraData));
        return true;
    }

    public boolean checkUnified3rd() {
        byte[] data = this.mMsg.getData();
        this.urlExtra = data;
        int i = (data[0] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT) + 1;
        int i2 = data[i] & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
        byte[] bArr = new byte[i];
        this.managerUrl = bArr;
        int i3 = i2 + 1;
        this.extraData = new byte[i3];
        System.arraycopy(data, 0, bArr, 0, i);
        System.arraycopy(this.urlExtra, i, this.extraData, 0, i3);
        Slog.secD(this.TAG, "url: " + byteArrayToString(this.managerUrl));
        Slog.secD(this.TAG, "extra Data: " + byteArrayToString(this.extraData));
        return true;
    }

    public void setRandNum(byte[] bArr) {
        this.randNum = (byte[]) bArr.clone();
    }

    public String getPublicKey() {
        String str = this.publicKey;
        return str == null ? "" : str;
    }

    public byte[] getSerialNumber() {
        return this.serialNumber;
    }

    public byte[] getManagerUrl() {
        return this.managerUrl;
    }

    public byte[] getExtraData() {
        return this.extraData;
    }

    public String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }
}
