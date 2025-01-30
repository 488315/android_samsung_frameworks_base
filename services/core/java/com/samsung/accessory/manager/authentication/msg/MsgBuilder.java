package com.samsung.accessory.manager.authentication.msg;

import com.att.iqi.lib.metrics.hw.HwConstants;

/* loaded from: classes.dex */
public class MsgBuilder {
    public Message mMsg;
    public MsgHelper mMsgHelper;
    public byte[] randNum = new byte[16];

    public byte[] getReqPubKey() {
        Message message = new Message((byte) 0, (byte) 22, (byte) 0, (byte) 0, (byte) 82);
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }

    public byte[] getReqFirmwareVersion() {
        Message message = new Message((byte) 0, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, (byte) 0, (byte) 0, (byte) 8);
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }

    public byte[] getReadCoverId() {
        Message message = new Message((byte) 84, (byte) 34, (byte) 31, (byte) 0, (byte) 18);
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }

    public byte[] getDataVerification() {
        Message message = new Message((byte) 0, (byte) 118, (byte) 0, (byte) 0, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, (byte[]) this.randNum.clone());
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }

    public byte[] getReqUrl() {
        Message message = new Message((byte) 84, (byte) 34, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, (byte) 0, (byte) 0);
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }

    public byte[] getReqExtra() {
        Message message = new Message((byte) 84, (byte) 34, (byte) 17, (byte) 0, (byte) 0);
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }

    public byte[] getReqUnified3rd() {
        Message message = new Message((byte) 84, (byte) 36, (byte) 0, (byte) 0, (byte) 0);
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }

    public byte[] makeRandomNumber() {
        MsgHelper msgHelper = new MsgHelper();
        this.mMsgHelper = msgHelper;
        byte[] genRandom = msgHelper.genRandom();
        this.randNum = genRandom;
        return genRandom;
    }

    public byte[] getRandNum() {
        return this.randNum;
    }

    public byte[] getKeyChange() {
        Message message = new Message((byte) 0, (byte) 66, (byte) 0, (byte) 0, (byte) 0);
        this.mMsg = message;
        return (byte[]) message.getApdu().clone();
    }
}
