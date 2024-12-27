package com.samsung.accessory.manager.authentication;

import android.content.Context;
import android.os.Debug;
import android.util.Slog;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.accessory.manager.authentication.msg.Message;
import com.samsung.accessory.manager.authentication.msg.MsgBuilder;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;
import com.samsung.accessory.manager.authentication.msg.MsgParser;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AuthenticatorClientImpl extends Authenticator {
    public final byte[] SW_FAIL;
    public final byte[] SW_SECURITY_ATTACK;
    public final byte[] SW_SUCCESS;
    public final byte[] SW_SUCCESS_UBIVELOX;
    public volatile boolean isInterrupted;
    public final MsgBuilder mMsgBuilder;
    public final MsgParser mMsgParser;
    public final byte[] statusWord;

    static {
        Debug.semIsProductDev();
    }

    public AuthenticatorClientImpl(Context context, int i) {
        this.mContext = context;
        this.SW_SUCCESS = new byte[]{-112, 0};
        this.SW_SUCCESS_UBIVELOX = new byte[]{-112, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED};
        this.SW_FAIL = new byte[]{105, -127};
        this.SW_SECURITY_ATTACK = new byte[]{105, -126};
        this.mMsgBuilder = null;
        this.mMsgParser = null;
        this.statusWord = new byte[2];
        this.isInterrupted = false;
        this.mType = i;
        MsgBuilder msgBuilder = new MsgBuilder();
        msgBuilder.randNum = new byte[16];
        this.mMsgBuilder = msgBuilder;
        MsgParser msgParser = new MsgParser();
        msgParser.randNum = new byte[16];
        msgParser.mMsgHelper = new MsgHelper();
        this.mMsgParser = msgParser;
    }

    public static String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < bArr.length) {
            i = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%02x", new Object[]{Byte.valueOf(bArr[i])}, sb, i, 1);
        }
        return sb.toString();
    }

    public final boolean checkError(int i, byte[] bArr, AuthenticationResult authenticationResult) {
        Slog.e("SAccessoryManager_AuthenticatorClientImpl", "checkError: " + byteArrayToString(bArr));
        if (bArr == null || bArr.length == 0) {
            authenticationResult.setReason(12);
            return false;
        }
        if (bArr.length != 1) {
            if (bArr.length <= 3) {
                return false;
            }
            int length = bArr.length - 3;
            byte[] bArr2 = this.statusWord;
            System.arraycopy(bArr, length, bArr2, 0, 2);
            Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Status Word: " + byteArrayToString(bArr2));
            boolean equals = Arrays.equals(bArr2, this.SW_SUCCESS);
            byte[] bArr3 = this.SW_SUCCESS_UBIVELOX;
            if (equals || Arrays.equals(bArr2, bArr3)) {
                boolean parseData = this.mMsgParser.parseData(i, bArr, Arrays.equals(bArr2, bArr3));
                if (parseData) {
                    return parseData;
                }
                authenticationResult.setReason(1);
                return false;
            }
            if (Arrays.equals(bArr2, this.SW_SECURITY_ATTACK)) {
                authenticationResult.setReason(24);
                return false;
            }
            if (!Arrays.equals(bArr2, this.SW_FAIL)) {
                return false;
            }
            authenticationResult.setReason(23);
            return false;
        }
        byte b = bArr[0];
        if (b == 1) {
            authenticationResult.setReason(20);
            return false;
        }
        if (b == -80) {
            authenticationResult.setReason(21);
            return false;
        }
        if (b == -78) {
            authenticationResult.setReason(22);
            return false;
        }
        if (b == -32) {
            authenticationResult.setReason(13);
            return false;
        }
        if (b == -15) {
            authenticationResult.setReason(40);
            return false;
        }
        if (b == -14) {
            authenticationResult.setReason(41);
            return false;
        }
        if (b == -13) {
            authenticationResult.setReason(42);
            return false;
        }
        if (b == -12) {
            authenticationResult.setReason(43);
            return false;
        }
        if (b == -11) {
            authenticationResult.setReason(44);
            return false;
        }
        if (b == -10) {
            authenticationResult.setReason(45);
            return false;
        }
        if (b == -9) {
            authenticationResult.setReason(46);
            return false;
        }
        if (b == -2) {
            authenticationResult.setReason(28);
            return false;
        }
        authenticationResult.setReason(27);
        return false;
    }

    public final boolean sendCommand(AuthenticationResult authenticationResult, int i) {
        boolean checkError;
        boolean checkError2;
        boolean z = false;
        if (this.isInterrupted) {
            Slog.e("SAccessoryManager_AuthenticatorClientImpl", "interrupted!");
            authenticationResult.setReason(32);
            return false;
        }
        switch (i) {
            case 2:
                this.mMsgBuilder.getClass();
                byte[] bArr = (byte[]) new Message((byte) 0, (byte) 22, (byte) 0, (byte) 82).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    checkError2 = checkError(i, sendSynchronously(bArr, authenticationResult), authenticationResult);
                    if (checkError2) {
                        String str = this.mMsgParser.publicKey;
                        if (str == null) {
                            str = "";
                        }
                        authenticationResult.publicKey = str;
                        z = checkError2;
                        HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                        break;
                    }
                } catch (IOException unused) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 3:
                try {
                    MsgBuilder msgBuilder = this.mMsgBuilder;
                    msgBuilder.getClass();
                    msgBuilder.randNum = new MsgHelper().genRandom();
                    byte[] bArr2 = this.mMsgBuilder.randNum;
                    MsgParser msgParser = this.mMsgParser;
                    msgParser.getClass();
                    msgParser.randNum = (byte[]) bArr2.clone();
                    byte[] dataVerification = this.mMsgBuilder.getDataVerification();
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    checkError = checkError(i, sendSynchronously(dataVerification, authenticationResult), authenticationResult);
                    if (!checkError) {
                    }
                    z = checkError;
                    HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                    break;
                } catch (IOException unused2) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 5:
                this.mMsgBuilder.getClass();
                byte[] bArr3 = (byte[]) new Message((byte) 84, (byte) 34, (byte) 31, (byte) 18).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    if (checkError(i, sendSynchronously(bArr3, authenticationResult), authenticationResult)) {
                        byte[] bArr4 = this.mMsgParser.serialNumber;
                        if (bArr4 != null) {
                            byte[] bArr5 = new byte[bArr4.length];
                            authenticationResult.mExtraID = bArr5;
                            System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
                            if (bArr4.length == 23) {
                                int i2 = authenticationResult.mExtraID[21] & 255;
                                checkError2 = true;
                                if (i2 == 1) {
                                    authenticationResult.isUrlExist = 1;
                                } else if (i2 == 2) {
                                    authenticationResult.isUrlExist = 2;
                                } else if (i2 == 3) {
                                    authenticationResult.isUrlExist = 3;
                                }
                                z = checkError2;
                                HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                                break;
                            }
                        }
                    }
                } catch (IOException unused3) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 4:
                checkError2 = true;
                z = checkError2;
                HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                break;
            case 6:
                this.mMsgBuilder.getClass();
                byte[] bArr6 = (byte[]) new Message((byte) 0, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, (byte) 0, (byte) 8).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    sendSynchronously(bArr6, authenticationResult);
                    z = true;
                    HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                    break;
                } catch (IOException unused4) {
                    authenticationResult.setReason(14);
                    return false;
                }
            case 7:
                this.mMsgBuilder.getClass();
                byte[] bArr7 = (byte[]) new Message((byte) 0, (byte) 66, (byte) 0, (byte) 0).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    if (!checkError(i, sendSynchronously(bArr7, authenticationResult), authenticationResult)) {
                    }
                    z = true;
                    HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                    break;
                } catch (IOException unused5) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 8:
                this.mMsgBuilder.getClass();
                byte[] bArr8 = (byte[]) new Message((byte) 0, (byte) 22, (byte) 0, (byte) 82).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    checkError = checkError(i, sendSynchronously(bArr8, authenticationResult), authenticationResult);
                    if (!checkError) {
                    }
                    z = checkError;
                    HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                    break;
                } catch (IOException unused6) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 9:
                this.mMsgBuilder.getClass();
                byte[] bArr9 = (byte[]) new Message((byte) 84, (byte) 34, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, (byte) 0).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    checkError2 = checkError(i, sendSynchronously(bArr9, authenticationResult), authenticationResult);
                    if (!checkError2) {
                        authenticationResult.setReason(94);
                        break;
                    } else {
                        byte[] bArr10 = this.mMsgParser.managerUrl;
                        if (bArr10 == null) {
                            Slog.e("SAccessoryManager_AuthenticatorClientImpl", "URI is NULL");
                            authenticationResult.setReason(94);
                            break;
                        } else {
                            authenticationResult.setManagerURI(bArr10);
                            z = checkError2;
                            HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                            break;
                        }
                    }
                } catch (IOException unused7) {
                    authenticationResult.setReason(14);
                    return false;
                }
            case 10:
                this.mMsgBuilder.getClass();
                byte[] bArr11 = (byte[]) new Message((byte) 84, (byte) 34, (byte) 17, (byte) 0).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    checkError2 = checkError(i, sendSynchronously(bArr11, authenticationResult), authenticationResult);
                    if (!checkError2) {
                        authenticationResult.setReason(95);
                        break;
                    } else {
                        byte[] bArr12 = this.mMsgParser.extraData;
                        if (bArr12 == null) {
                            Slog.e("SAccessoryManager_AuthenticatorClientImpl", "ExtraData is NULL");
                            authenticationResult.setReason(95);
                            break;
                        } else {
                            authenticationResult.setExtraData(bArr12);
                            z = checkError2;
                            HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                            break;
                        }
                    }
                } catch (IOException unused8) {
                    authenticationResult.setReason(14);
                    return false;
                }
            case 11:
                this.mMsgBuilder.getClass();
                byte[] bArr13 = (byte[]) new Message((byte) 84, (byte) 36, (byte) 0, (byte) 0).getApdu().clone();
                try {
                    Slog.i("SAccessoryManager_AuthenticatorClientImpl", "Send Command " + i);
                    checkError2 = checkError(i, sendSynchronously(bArr13, authenticationResult), authenticationResult);
                    if (!checkError2) {
                        authenticationResult.setReason(94);
                        break;
                    } else {
                        byte[] bArr14 = this.mMsgParser.managerUrl;
                        if (bArr14 == null) {
                            Slog.e("SAccessoryManager_AuthenticatorClientImpl", "URI is NULL");
                            authenticationResult.setReason(94);
                            break;
                        } else {
                            authenticationResult.setManagerURI(bArr14);
                            byte[] bArr15 = this.mMsgParser.extraData;
                            if (bArr15 == null) {
                                Slog.e("SAccessoryManager_AuthenticatorClientImpl", "ExtraData is NULL");
                                authenticationResult.setReason(95);
                                break;
                            } else {
                                authenticationResult.setExtraData(bArr15);
                                z = checkError2;
                                HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                                break;
                            }
                        }
                    }
                } catch (IOException unused9) {
                    authenticationResult.setReason(14);
                    return false;
                }
            default:
                HermesService$3$$ExternalSyntheticOutline0.m(i, "Receive Command ", "SAccessoryManager_AuthenticatorClientImpl");
                break;
        }
        return false;
    }

    public final void stopAuthentication() {
        try {
            sendStopAuth();
        } catch (IOException unused) {
            Slog.e("SAccessoryManager_AuthenticatorClientImpl", "fail stop command");
        }
    }
}
