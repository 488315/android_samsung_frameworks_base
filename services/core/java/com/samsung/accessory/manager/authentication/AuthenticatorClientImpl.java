package com.samsung.accessory.manager.authentication;

import android.content.Context;
import android.os.Debug;
import android.os.FactoryTest;
import android.util.Slog;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.accessory.manager.authentication.msg.MsgBuilder;
import com.samsung.accessory.manager.authentication.msg.MsgParser;
import com.samsung.accessory.manager.connectivity.Connectivity;
import java.io.IOException;
import java.util.Arrays;

/* loaded from: classes.dex */
public class AuthenticatorClientImpl extends Authenticator {
    public final int COMMAND_ATQS;
    public final int COMMAND_FIRMWARE;
    public final int COMMAND_KEY_CHANGE;
    public final int COMMAND_PUB_KEY;
    public final int COMMAND_READ_ID;
    public final int COMMAND_REQEXTRA;
    public final int COMMAND_REQUNIFIED;
    public final int COMMAND_REQURL;
    public final int COMMAND_SEC_PUB_KEY;
    public final int COMMAND_VERIFICATION;
    public final int COMMAND_WRITE_ID;
    public final int RETRY_TIME;
    public final byte[] SW_FAIL;
    public final byte[] SW_SECURITY_ATTACK;
    public final byte[] SW_SUCCESS;
    public final byte[] SW_SUCCESS_UBIVELOX;
    public volatile boolean isInterrupted;
    public MsgBuilder mMsgBuilder;
    public MsgParser mMsgParser;
    public byte[] randNum;
    public byte[] statusWord;
    public static final String TAG = "SAccessoryManager_" + AuthenticatorClientImpl.class.getSimpleName();
    public static final boolean DBG = Debug.semIsProductDev();

    @Override // com.samsung.accessory.manager.authentication.Authenticator
    public boolean onAuthenticationChallenge(AuthenticationResult authenticationResult) {
        Slog.i(TAG, "onAuthenticationChallenge, type =" + this.mType);
        int i = this.mType;
        if (i == 1) {
            return authNFC(authenticationResult);
        }
        if (i == 3) {
            return authUsb(authenticationResult);
        }
        if (i == 4) {
            return authWirelessCharger(authenticationResult);
        }
        return false;
    }

    @Override // com.samsung.accessory.manager.authentication.Authenticator
    public void onInterrupted() {
        this.isInterrupted = true;
    }

    @Override // com.samsung.accessory.manager.authentication.Authenticator
    public void setInterrupt(boolean z) {
        this.isInterrupted = z;
    }

    public final boolean authWirelessCharger(AuthenticationResult authenticationResult) {
        boolean openNode;
        String str;
        if (this.mConnectivity == null) {
            Slog.e(TAG, "onAuthenticationChallenge, mConnection is null!");
            authenticationResult.setReason(12);
            return false;
        }
        this.mMsgParser.parseData(1, new byte[]{0, 85, 6, 8, 5, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED, 29, 23, 0, 0, 2, 1, -98, -87, -127, 2});
        try {
            openNode = openNode(authenticationResult);
            str = TAG;
            Slog.i(str, "open wirelesscharger: " + byteArrayToString(null));
        } catch (IOException e) {
            Slog.e(TAG, e.toString());
            authenticationResult.setReason(14);
        }
        if (!openNode) {
            Slog.e(str, "open fail");
            stopAuthentication(authenticationResult);
            return false;
        }
        if (!sendCommand(authenticationResult, 2)) {
            stopAuthentication(authenticationResult);
            return false;
        }
        if (!sendCommand(authenticationResult, 3)) {
            stopAuthentication(authenticationResult);
            return false;
        }
        Slog.i(str, "Success auth, call sendStopAuth()");
        authenticationResult.setReason(0);
        stopAuthentication(authenticationResult);
        return true;
    }

    public final boolean authUsb(AuthenticationResult authenticationResult) {
        byte[] sendStartAuth;
        String str;
        if (this.mConnectivity == null) {
            Slog.e(TAG, "onAuthenticationChallenge, mConnection is null!");
            authenticationResult.setReason(12);
            return false;
        }
        try {
            sendStartAuth = sendStartAuth(authenticationResult);
            str = TAG;
            Slog.i(str, "response from ccic: " + byteArrayToString(sendStartAuth));
        } catch (IOException e) {
            Slog.e(TAG, e.toString());
            authenticationResult.setReason(14);
        }
        if (sendStartAuth.length == 1) {
            Slog.e(str, "need define error code");
            return false;
        }
        if (!this.mMsgParser.parseData(1, sendStartAuth)) {
            authenticationResult.setReason(26);
            Slog.e(str, "atqS fail, call sendStopAuth()");
            stopAuthentication(authenticationResult);
            return false;
        }
        if (!sendCommand(authenticationResult, 2)) {
            stopAuthentication(authenticationResult);
            return false;
        }
        if (!sendCommand(authenticationResult, 3)) {
            stopAuthentication(authenticationResult);
            return false;
        }
        if (!sendCommand(authenticationResult, 5)) {
            Slog.e(str, "Read id fail.");
            stopAuthentication(authenticationResult);
            return false;
        }
        int i = authenticationResult.isUrlExist;
        if (i == 1) {
            if (!sendCommand(authenticationResult, 9)) {
                Slog.e(str, "Url is not exist.");
                stopAuthentication(authenticationResult);
                return false;
            }
        } else if (i == 2) {
            if (!sendCommand(authenticationResult, 10)) {
                Slog.e(str, "Extra is not exist.");
                stopAuthentication(authenticationResult);
                return false;
            }
        } else if (i == 3 && !sendCommand(authenticationResult, 11)) {
            Slog.e(str, "3rd data is not exist.");
            stopAuthentication(authenticationResult);
            return false;
        }
        Slog.i(str, "Success auth, call sendStopAuth()");
        stopAuthentication(authenticationResult);
        authenticationResult.setReason(0);
        return true;
    }

    public final void stopAuthentication(AuthenticationResult authenticationResult) {
        try {
            sendStopAuth();
        } catch (IOException unused) {
            Slog.e(TAG, "fail stop command");
        }
    }

    public final boolean authNFC(AuthenticationResult authenticationResult) {
        boolean z;
        boolean isFactoryBinary = FactoryTest.isFactoryBinary();
        if (this.mConnectivity == null) {
            Slog.e(TAG, "onAuthenticationChallenge, mConnection is null!");
            authenticationResult.setReason(12);
            return false;
        }
        try {
            byte[] sendStartAuth = sendStartAuth(authenticationResult);
            if (sendStartAuth != null) {
                String str = TAG;
                Slog.i(str, "Received atqS Data: " + byteArrayToString(sendStartAuth));
                Arrays.equals(Connectivity.NOT_SUPPORT, sendStartAuth);
                if (sendStartAuth.length == 1) {
                    byte b = sendStartAuth[0];
                    if (b == 1) {
                        authenticationResult.setReason(20);
                    } else if (b == -79) {
                        authenticationResult.setReason(21);
                    } else if (b == -78) {
                        authenticationResult.setReason(22);
                    } else if (b == -32) {
                        authenticationResult.setReason(13);
                    } else if (b == -15) {
                        authenticationResult.setReason(40);
                    } else if (b == -14) {
                        authenticationResult.setReason(41);
                    } else if (b == -13) {
                        authenticationResult.setReason(42);
                    } else if (b == -12) {
                        authenticationResult.setReason(43);
                    } else if (b == -11) {
                        authenticationResult.setReason(44);
                    } else if (b == -10) {
                        authenticationResult.setReason(45);
                    } else if (b == -9) {
                        authenticationResult.setReason(46);
                    } else {
                        authenticationResult.setReason(27);
                    }
                    sendStopAuth();
                    return false;
                }
                if (sendStartAuth.length == 16) {
                    z = this.mMsgParser.parseData(1, sendStartAuth);
                } else {
                    Slog.e(str, "atqS is not correct");
                    authenticationResult.setReason(25);
                    sendStopAuth();
                    return false;
                }
            } else {
                Slog.e(TAG, " atqS is null");
                z = false;
            }
            if (!z) {
                authenticationResult.setReason(26);
                Slog.e(TAG, "atqS fail, call sendStopAuth()");
                sendStopAuth();
                return false;
            }
            if (!authenticationResult.isKeyChanged()) {
                if (!sendCommand(authenticationResult, 2)) {
                    sendStopAuth();
                    return false;
                }
                if (!sendCommand(authenticationResult, 3)) {
                    sendStopAuth();
                    return false;
                }
                if (!sendCommand(authenticationResult, 5)) {
                    Slog.e(TAG, "Read id fail.");
                    sendStopAuth();
                    return false;
                }
                int i = authenticationResult.isUrlExist;
                if (i == 1) {
                    if (!sendCommand(authenticationResult, 9)) {
                        Slog.e(TAG, "Url is not exist.");
                        if (!isFactoryBinary) {
                            sendStopAuth();
                            return false;
                        }
                    }
                } else if (i == 2) {
                    if (!sendCommand(authenticationResult, 10)) {
                        Slog.e(TAG, "Extra is not exist.");
                        if (!isFactoryBinary) {
                            sendStopAuth();
                            return false;
                        }
                    }
                } else if (i == 3 && !sendCommand(authenticationResult, 11)) {
                    Slog.e(TAG, "3rd data is not exist.");
                    if (!isFactoryBinary) {
                        sendStopAuth();
                        return false;
                    }
                }
                Slog.i(TAG, "Success auth, call sendStopAuth()");
            }
            if (authenticationResult.needKeyChange()) {
                Slog.i(TAG, "need to key change, retry authentication throught chaeged key");
                if (sendCommand(authenticationResult, 7)) {
                    authenticationResult.setKeyChanged(true);
                }
            }
            if (authenticationResult.isKeyChanged()) {
                if (!sendCommand(authenticationResult, 8)) {
                    sendStopAuth();
                    return false;
                }
                if (!sendCommand(authenticationResult, 3)) {
                    sendStopAuth();
                    return false;
                }
                if (!sendCommand(authenticationResult, 5)) {
                    Slog.e(TAG, "Read id fail.");
                    sendStopAuth();
                    return false;
                }
                int i2 = authenticationResult.isUrlExist;
                if (i2 == 1) {
                    if (!sendCommand(authenticationResult, 9)) {
                        Slog.e(TAG, "url is not exist.");
                        if (!isFactoryBinary) {
                            sendStopAuth();
                            return false;
                        }
                    }
                } else if (i2 == 2) {
                    if (!sendCommand(authenticationResult, 10)) {
                        Slog.e(TAG, "extra is not exist.");
                        if (!isFactoryBinary) {
                            sendStopAuth();
                            return false;
                        }
                    }
                } else if (i2 == 3 && !sendCommand(authenticationResult, 11)) {
                    Slog.e(TAG, "3rd data is not exist.");
                    if (!isFactoryBinary) {
                        sendStopAuth();
                        return false;
                    }
                }
                Slog.i(TAG, "Success auth, call sendStopAuth()");
            }
            sendStopAuth();
            authenticationResult.setReason(0);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            authenticationResult.setReason(14);
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final boolean sendCommand(AuthenticationResult authenticationResult, int i) {
        boolean checkError;
        boolean checkError2;
        boolean z = false;
        boolean z2 = true;
        if (this.isInterrupted) {
            Slog.e(TAG, "interrupted!");
            authenticationResult.setReason(32);
            return false;
        }
        switch (i) {
            case 2:
                byte[] reqPubKey = this.mMsgBuilder.getReqPubKey();
                try {
                    Slog.i(TAG, "Send Command " + i);
                    checkError2 = checkError(i, sendSynchronously(reqPubKey, authenticationResult), authenticationResult);
                    if (checkError2) {
                        if (this.mMsgParser.getPublicKey() != null) {
                            authenticationResult.setPublicKey(this.mMsgParser.getPublicKey());
                        }
                        z = checkError2;
                        Slog.i(TAG, "Receive Command " + i);
                        break;
                    }
                } catch (IOException unused) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 3:
                try {
                    this.mMsgBuilder.makeRandomNumber();
                    byte[] randNum = this.mMsgBuilder.getRandNum();
                    this.randNum = randNum;
                    this.mMsgParser.setRandNum(randNum);
                    byte[] dataVerification = this.mMsgBuilder.getDataVerification();
                    Slog.i(TAG, "Send Command " + i);
                    checkError = checkError(i, sendSynchronously(dataVerification, authenticationResult), authenticationResult);
                    if (!checkError) {
                    }
                    z = checkError;
                    Slog.i(TAG, "Receive Command " + i);
                    break;
                } catch (IOException unused2) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 4:
                z = z2;
                Slog.i(TAG, "Receive Command " + i);
                break;
            case 5:
                byte[] readCoverId = this.mMsgBuilder.getReadCoverId();
                try {
                    Slog.i(TAG, "Send Command " + i);
                    if (checkError(i, sendSynchronously(readCoverId, authenticationResult), authenticationResult)) {
                        if (this.mMsgParser.getSerialNumber() != null) {
                            authenticationResult.setExtraId(this.mMsgParser.getSerialNumber());
                        }
                        z = z2;
                        Slog.i(TAG, "Receive Command " + i);
                        break;
                    }
                } catch (IOException unused3) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 6:
                byte[] reqFirmwareVersion = this.mMsgBuilder.getReqFirmwareVersion();
                try {
                    Slog.i(TAG, "Send Command " + i);
                    sendSynchronously(reqFirmwareVersion, authenticationResult);
                    z = z2;
                    Slog.i(TAG, "Receive Command " + i);
                    break;
                } catch (IOException unused4) {
                    authenticationResult.setReason(14);
                    return false;
                }
            case 7:
                byte[] keyChange = this.mMsgBuilder.getKeyChange();
                try {
                    Slog.i(TAG, "Send Command " + i);
                    if (!checkError(i, sendSynchronously(keyChange, authenticationResult), authenticationResult)) {
                    }
                    z = z2;
                    Slog.i(TAG, "Receive Command " + i);
                    break;
                } catch (IOException unused5) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 8:
                byte[] reqPubKey2 = this.mMsgBuilder.getReqPubKey();
                try {
                    Slog.i(TAG, "Send Command " + i);
                    checkError = checkError(i, sendSynchronously(reqPubKey2, authenticationResult), authenticationResult);
                    if (!checkError) {
                    }
                    z = checkError;
                    Slog.i(TAG, "Receive Command " + i);
                    break;
                } catch (IOException unused6) {
                    authenticationResult.setReason(14);
                    return false;
                }
                break;
            case 9:
                byte[] reqUrl = this.mMsgBuilder.getReqUrl();
                try {
                    String str = TAG;
                    Slog.i(str, "Send Command " + i);
                    checkError2 = checkError(i, sendSynchronously(reqUrl, authenticationResult), authenticationResult);
                    if (!checkError2) {
                        authenticationResult.setReason(94);
                        break;
                    } else if (this.mMsgParser.getManagerUrl() != null) {
                        authenticationResult.setManagerURI(this.mMsgParser.getManagerUrl());
                        z = checkError2;
                        Slog.i(TAG, "Receive Command " + i);
                        break;
                    } else {
                        Slog.e(str, "URI is NULL");
                        authenticationResult.setReason(94);
                        break;
                    }
                } catch (IOException unused7) {
                    authenticationResult.setReason(14);
                    return false;
                }
            case 10:
                byte[] reqExtra = this.mMsgBuilder.getReqExtra();
                try {
                    String str2 = TAG;
                    Slog.i(str2, "Send Command " + i);
                    z2 = checkError(i, sendSynchronously(reqExtra, authenticationResult), authenticationResult);
                    if (!z2) {
                        authenticationResult.setReason(95);
                        break;
                    } else if (this.mMsgParser.getExtraData() != null) {
                        authenticationResult.setExtraData(this.mMsgParser.getExtraData());
                        z = z2;
                        Slog.i(TAG, "Receive Command " + i);
                        break;
                    } else {
                        Slog.e(str2, "ExtraData is NULL");
                        authenticationResult.setReason(95);
                        break;
                    }
                } catch (IOException unused8) {
                    authenticationResult.setReason(14);
                    return false;
                }
            case 11:
                byte[] reqUnified3rd = this.mMsgBuilder.getReqUnified3rd();
                try {
                    String str3 = TAG;
                    Slog.i(str3, "Send Command " + i);
                    z2 = checkError(i, sendSynchronously(reqUnified3rd, authenticationResult), authenticationResult);
                    if (!z2) {
                        authenticationResult.setReason(94);
                        break;
                    } else if (this.mMsgParser.getManagerUrl() != null) {
                        authenticationResult.setManagerURI(this.mMsgParser.getManagerUrl());
                        if (this.mMsgParser.getExtraData() != null) {
                            authenticationResult.setExtraData(this.mMsgParser.getExtraData());
                            z = z2;
                            Slog.i(TAG, "Receive Command " + i);
                            break;
                        } else {
                            Slog.e(str3, "ExtraData is NULL");
                            authenticationResult.setReason(95);
                            break;
                        }
                    } else {
                        Slog.e(str3, "URI is NULL");
                        authenticationResult.setReason(94);
                        break;
                    }
                } catch (IOException unused9) {
                    authenticationResult.setReason(14);
                    return false;
                }
            default:
                Slog.i(TAG, "Receive Command " + i);
                break;
        }
        return false;
    }

    public final boolean checkError(int i, byte[] bArr, AuthenticationResult authenticationResult) {
        String str = TAG;
        Slog.e(str, "checkError: " + byteArrayToString(bArr));
        if (bArr == null || bArr.length == 0) {
            authenticationResult.setReason(12);
            return false;
        }
        if (bArr.length == 1) {
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
        if (bArr.length <= 3) {
            return false;
        }
        System.arraycopy(bArr, bArr.length - 3, this.statusWord, 0, 2);
        Slog.i(str, "Status Word: " + byteArrayToString(this.statusWord));
        if (Arrays.equals(this.statusWord, this.SW_SUCCESS) || Arrays.equals(this.statusWord, this.SW_SUCCESS_UBIVELOX)) {
            boolean parseData = this.mMsgParser.parseData(i, bArr, Arrays.equals(this.statusWord, this.SW_SUCCESS_UBIVELOX));
            if (parseData) {
                return parseData;
            }
            authenticationResult.setReason(1);
            return false;
        }
        if (Arrays.equals(this.statusWord, this.SW_SECURITY_ATTACK)) {
            authenticationResult.setReason(24);
            return false;
        }
        if (!Arrays.equals(this.statusWord, this.SW_FAIL)) {
            return false;
        }
        authenticationResult.setReason(23);
        return false;
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

    public AuthenticatorClientImpl(Context context, int i) {
        super(context);
        this.RETRY_TIME = 1;
        this.COMMAND_ATQS = 1;
        this.COMMAND_PUB_KEY = 2;
        this.COMMAND_VERIFICATION = 3;
        this.COMMAND_WRITE_ID = 4;
        this.COMMAND_READ_ID = 5;
        this.COMMAND_FIRMWARE = 6;
        this.COMMAND_KEY_CHANGE = 7;
        this.COMMAND_SEC_PUB_KEY = 8;
        this.COMMAND_REQURL = 9;
        this.COMMAND_REQEXTRA = 10;
        this.COMMAND_REQUNIFIED = 11;
        this.SW_SUCCESS = new byte[]{-112, 0};
        this.SW_SUCCESS_UBIVELOX = new byte[]{-112, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED};
        this.SW_FAIL = new byte[]{105, -127};
        this.SW_SECURITY_ATTACK = new byte[]{105, -126};
        this.mMsgBuilder = null;
        this.mMsgParser = null;
        this.randNum = new byte[16];
        this.statusWord = new byte[2];
        this.isInterrupted = false;
        this.mType = i;
        this.mMsgBuilder = new MsgBuilder();
        this.mMsgParser = new MsgParser();
    }
}
