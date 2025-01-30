package com.samsung.android.battauthmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.p009os.BatteryManager;
import android.p009os.Handler;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.PowerManager;
import android.system.ErrnoException;
import android.system.Os;
import android.telecom.ParcelableCallAnalytics;
import android.util.Log;
import com.samsung.android.ims.options.SemCapabilities;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes5.dex */
public class WpcAuthenticator {
    private static final String DIGEST_DIR = "/efs/Battery/qi_digests/";
    private static final String DIGEST_PREFIX = "qi_digest_";
    private static final int MAX_CACHED_DIGEST = 15;
    private static final int MSG_AUTH_GET_AND_CHECK_DIGEST = 3;
    private static final int MSG_AUTH_GET_AND_VERIFY_CERT_CHAIN = 5;
    private static final int MSG_AUTH_GET_AND_VERIFY_CHALLENGE = 8;
    private static final int MSG_AUTH_REQ_CERT_CHAIN = 4;
    private static final int MSG_AUTH_REQ_CHALLENGE = 7;
    private static final int MSG_AUTH_REQ_DIGEST = 2;
    private static final int MSG_AUTH_START = 1;
    private static final int MSG_AUTH_STOP = 0;
    private static final int MSG_AUTH_TIMEOUT = 1000;
    private static final String TAG = "BattAuthManager_" + WpcAuthenticator.class.getSimpleName();
    private static final int TIMEOUT_MILLIS_REQ_CERT_CHAIN = 300000;
    private static final int TIMEOUT_MILLIS_REQ_CHALLENGE = 90000;
    private static final int TIMEOUT_MILLIS_REQ_DIGEST = 60000;
    private static final int WAIT_CERT_CHAIN = 2;
    private static final int WAIT_CHALLENGE_AUTH = 3;
    private static final int WAIT_DIGEST = 1;
    private final PowerManager.WakeLock mAuthWakeLock;
    private final Context mContext;
    private final WpcAuthHandler mWpcAuthHandler;
    private boolean mIsAttachedAuthPad = false;
    private byte[] certChainHash = null;
    private byte[] productPublicKey = null;
    private byte[] requestChallenge = null;
    private int currentStatus = 0;
    private String matchedDigestFileName = null;
    private final BattAuthHelper mBattAuthHelper = new BattAuthHelper();

    public WpcAuthenticator(Context context, Looper looper) {
        this.mContext = context;
        this.mWpcAuthHandler = new WpcAuthHandler(looper);
        IntentFilter batteryEventFilter = new IntentFilter(BatteryManager.ACTION_SEC_BATTERY_EVENT);
        BroadcastReceiver mBatteryEventReceiver = new BroadcastReceiver() { // from class: com.samsung.android.battauthmanager.WpcAuthenticator.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (BatteryManager.ACTION_SEC_BATTERY_EVENT.equals(intent.getAction())) {
                    int extra = intent.getIntExtra(BatteryManager.EXTRA_MISC_EVENT, 0);
                    int plug_type = intent.getIntExtra(BatteryManager.EXTRA_SEC_PLUG_TYPE_SUMMARY, 0);
                    boolean det_level = (intent.getIntExtra(BatteryManager.EXTRA_MISC_EVENT, 0) & 64) == 64;
                    Log.m98i(WpcAuthenticator.TAG, "onReceive: " + intent.getAction() + ", misc_event: " + extra + ", plug event: " + plug_type);
                    if ((intent.getIntExtra(BatteryManager.EXTRA_MISC_EVENT, 0) & 512) == 512) {
                        Log.m98i(WpcAuthenticator.TAG, "onReceive: attached auth pad");
                        int authMode = 0;
                        try {
                            authMode = Integer.parseInt(WpcAuthenticator.readString("/sys/class/power_supply/battery/wpc_auth_mode"));
                        } catch (NumberFormatException e) {
                            Log.m98i(WpcAuthenticator.TAG, "onReceive: fail to read auth mode");
                            e.printStackTrace();
                        }
                        if (authMode == 3 || authMode == 4) {
                            Log.m98i(WpcAuthenticator.TAG, "onReceive: wpc_auth_mode : " + authMode);
                            WpcAuthenticator.this.mIsAttachedAuthPad = true;
                            WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(1);
                            return;
                        }
                        return;
                    }
                    if (!det_level && WpcAuthenticator.this.mIsAttachedAuthPad) {
                        WpcAuthenticator.this.mIsAttachedAuthPad = false;
                        Log.m98i(WpcAuthenticator.TAG, "onReceive: detached auth pad");
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                    } else if (WpcAuthenticator.this.mIsAttachedAuthPad && (intent.getIntExtra(BatteryManager.EXTRA_MISC_EVENT, 0) & 1024) == 1024) {
                        switch (WpcAuthenticator.this.currentStatus) {
                            case 1:
                                WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(3);
                                break;
                            case 2:
                                WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(5);
                                break;
                            case 3:
                                WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(8);
                                break;
                            default:
                                WpcAuthenticator.this.currentStatus = 0;
                                break;
                        }
                    }
                }
            }
        };
        context.registerReceiver(mBatteryEventReceiver, batteryEventFilter);
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        PowerManager.WakeLock newWakeLock = powerManager.newWakeLock(1, TAG);
        this.mAuthWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestDigests() {
        byte[] reqMsg = this.mBattAuthHelper.makeGetDigestsReq((byte) 1, 1);
        String str = TAG;
        Log.m94d(str, "requestDigests, reqMsg : " + byteArrayToString(reqMsg));
        int ret = this.mBattAuthHelper.ioctl_longDataWrite_batt(reqMsg);
        if (ret == reqMsg.length) {
            return true;
        }
        Log.m96e(str, "requestDigests, fail to write req of digests");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0120, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean getAndCheckDigests() {
        String s;
        boolean wasFoundCachedDigest = false;
        byte[] resMsg = this.mBattAuthHelper.ioctl_longDataRead_batt();
        String str = TAG;
        Log.m94d(str, "getAndCheckDigests, digest : " + byteArrayToString(resMsg));
        if (resMsg.length != 34) {
            Log.m96e(str, "getAndCheckDigests, wrong length of digest : " + resMsg.length);
            return false;
        }
        String[] fileArr = getDigestFileNames();
        if (fileArr == null) {
            Log.m94d(str, "getAndCheckDigests, no digest dir");
            return false;
        }
        Log.m94d(str, "getAndCheckDigests, cached " + fileArr.length + " file(s)");
        int length = fileArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            s = fileArr[i];
            byte[] cachedDigest = readBytes(DIGEST_DIR + s);
            if (cachedDigest == null || cachedDigest.length < 32) {
                break;
            }
            wasFoundCachedDigest = true;
            int j = 0;
            while (true) {
                if (j >= 32) {
                    break;
                }
                if (resMsg[j + 2] == cachedDigest[j]) {
                    j++;
                } else {
                    wasFoundCachedDigest = false;
                    break;
                }
            }
            if (!wasFoundCachedDigest) {
                i++;
            } else {
                this.matchedDigestFileName = s;
                String str2 = TAG;
                Log.m94d(str2, "getAndCheckDigests, matched : " + s);
                this.certChainHash = Arrays.copyOf(cachedDigest, 32);
                this.productPublicKey = Arrays.copyOfRange(cachedDigest, 32, cachedDigest.length);
                Log.m94d(str2, "getAndCheckDigests, certChainHash : " + byteArrayToString(this.certChainHash));
                Log.m94d(str2, "getAndCheckDigests, productPublicKey : " + byteArrayToString(this.productPublicKey));
                break;
            }
        }
        Log.m96e(TAG, "getAndCheckDigests, can not read : " + s);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestCertChain() {
        byte[] reqMsg = this.mBattAuthHelper.makeGetCertReq(0, 1, 0, 0, 0, 0);
        String str = TAG;
        Log.m94d(str, "requestCertChain, reqMsg : " + byteArrayToString(reqMsg));
        int ret = this.mBattAuthHelper.ioctl_longDataWrite_batt(reqMsg);
        if (ret != reqMsg.length) {
            Log.m96e(str, "requestCertChain, fail to write req of cert chain");
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getAndVerifyCertChain() {
        byte[] certChain = this.mBattAuthHelper.ioctl_longDataRead_batt();
        if (certChain == null || certChain.length < 2) {
            Log.m96e(TAG, "getAndVerifyCertChain, certChain is invalid");
            return false;
        }
        String str = TAG;
        Log.m94d(str, "getAndVerifyCertChain, certchain : " + byteArrayToString(certChain));
        this.certChainHash = getSha256Hash(Arrays.copyOfRange(certChain, 1, certChain.length));
        byte[] verifyWpcCertChain = this.mBattAuthHelper.verifyWpcCertChain(certChain);
        this.productPublicKey = verifyWpcCertChain;
        if (verifyWpcCertChain == null) {
            Log.m96e(str, "getAndVerifyCertChain, fail to verify cert chain");
            return false;
        }
        Log.m94d(str, "getAndVerifyCertChain, verified, pubKey : " + byteArrayToString(this.productPublicKey));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean getAndVerifyChallenge() {
        byte[] challengeAuth = this.mBattAuthHelper.ioctl_longDataRead_batt();
        if (challengeAuth == null) {
            Log.m96e(TAG, "getAndVerifyChallenge, challengeAuth is invalid");
            return false;
        }
        String str = TAG;
        Log.m94d(str, "getAndVerifyChallenge, challengeAuth : " + byteArrayToString(challengeAuth));
        int ret = this.mBattAuthHelper.verifyChallengeAuth(this.certChainHash, this.productPublicKey, this.requestChallenge, challengeAuth);
        if (ret < 0) {
            Log.m96e(str, "getAndVerifyChallenge, fail to verify challenge");
            return false;
        }
        Log.m94d(str, "getAndVerifyChallenge, success to verify challenge");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean requestChallengeAuth() {
        this.requestChallenge = this.mBattAuthHelper.makeChallengeReq(0, 1);
        String str = TAG;
        Log.m94d(str, "requestChallengeAuth, reqMsg : " + byteArrayToString(this.requestChallenge));
        int ret = this.mBattAuthHelper.ioctl_longDataWrite_batt(this.requestChallenge);
        if (ret == this.requestChallenge.length) {
            return true;
        }
        Log.m96e(str, "requestChallengeAuth, fail to write req of challenge");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setAuthPass(boolean isPass) {
        int ret;
        byte[] success = {1};
        byte[] fail = {2};
        if (isPass) {
            ret = this.mBattAuthHelper.ioctl_longDataWrite_batt(success);
            saveCertHashAndPubkeyToFile();
        } else {
            ret = this.mBattAuthHelper.ioctl_longDataWrite_batt(fail);
        }
        if (ret != 1) {
            Log.m96e(TAG, "setAuthPass, fail to write req of challenge");
            return false;
        }
        Log.m94d(TAG, "setAuthPass, result : " + isPass);
        return true;
    }

    private String byteArrayToString(byte[] input) {
        if (input != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format(Locale.US, "len(%d), ", Integer.valueOf(input.length)));
            for (byte b : input) {
                builder.append(String.format("%02X ", Byte.valueOf(b)));
            }
            return builder.toString();
        }
        return SemCapabilities.FEATURE_TAG_NULL;
    }

    private static byte[] getSha256Hash(byte[] value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String[] getDigestFileNames() {
        File digestDir = new File(DIGEST_DIR);
        if (digestDir.exists()) {
            String[] fileArr = digestDir.list(new FilenameFilter() { // from class: com.samsung.android.battauthmanager.WpcAuthenticator$$ExternalSyntheticLambda0
                @Override // java.io.FilenameFilter
                public final boolean accept(File file, String str) {
                    boolean startsWith;
                    startsWith = str.startsWith(WpcAuthenticator.DIGEST_PREFIX);
                    return startsWith;
                }
            });
            if (fileArr != null) {
                Arrays.sort(fileArr);
            }
            return fileArr;
        }
        if (digestDir.mkdir()) {
            setPermission(DIGEST_DIR, 509);
            return null;
        }
        return null;
    }

    private void saveCertHashAndPubkeyToFile() {
        FileOutputStream fout = null;
        String path = "/efs/Battery/qi_digests/qi_digest_" + (System.currentTimeMillis() / 1000);
        if (this.matchedDigestFileName != null) {
            deleteFile(DIGEST_DIR + this.matchedDigestFileName);
            this.matchedDigestFileName = null;
        }
        String[] digestFileName = getDigestFileNames();
        if (digestFileName != null && digestFileName.length >= 15) {
            int delCnt = (digestFileName.length - 15) + 1;
            for (int i = 0; i < delCnt; i++) {
                deleteFile(DIGEST_DIR + digestFileName[i]);
            }
        }
        try {
            try {
                try {
                    fout = new FileOutputStream(path);
                    for (byte b : this.certChainHash) {
                        fout.write(b);
                    }
                    for (byte b2 : this.productPublicKey) {
                        fout.write(b2);
                    }
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.m96e(TAG, "saveCertHashAndPubkeyToFile, fail to save");
                    if (fout != null) {
                        fout.close();
                    }
                }
            } catch (Throwable th) {
                if (fout != null) {
                    try {
                        fout.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        setPermission(path, 432);
        Log.m94d(TAG, "saveCertHashAndPubkeyToFile, save : " + path);
    }

    private void setPermission(String path, int mode) {
        try {
            Os.chmod(path, mode);
            Os.chown(path, 1000, 1000);
        } catch (ErrnoException e) {
            e.printStackTrace();
        }
    }

    private byte[] readBytes(String path) {
        byte[] data = null;
        FileInputStream fin = null;
        try {
            try {
                try {
                    fin = new FileInputStream(path);
                    int size = (int) fin.getChannel().size();
                    Log.m98i(TAG, "readBytes, size : " + size);
                    data = new byte[size];
                    int n = 0;
                    while (true) {
                        int c = fin.read();
                        if (c == -1 || n >= size) {
                            break;
                        }
                        data[n] = (byte) c;
                        n++;
                    }
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (fin != null) {
                        fin.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Log.m98i(TAG, "readBytes : " + byteArrayToString(data));
            return data;
        } catch (Throwable th) {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String readString(String path) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            try {
                try {
                    in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
                    while (true) {
                        String line = in.readLine();
                        if (line == null) {
                            break;
                        }
                        sb.append(line);
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    if (in != null) {
                        in.close();
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            String retStr = sb.toString();
            Log.m98i(TAG, "readFile : " + retStr);
            return retStr;
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public void removeDigests() {
        String[] digestFileName = getDigestFileNames();
        if (digestFileName != null) {
            int delCnt = 0;
            for (String s : digestFileName) {
                if (deleteFile(DIGEST_DIR + s)) {
                    delCnt++;
                }
            }
            Log.m98i(TAG, "removeDigests, clear digest(s), " + delCnt);
        }
    }

    private boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    private final class WpcAuthHandler extends Handler {
        public WpcAuthHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
            Log.m98i(WpcAuthenticator.TAG, "handleMessage: " + getNameOfMsgWhat(msg.what));
            switch (msg.what) {
                case 0:
                    Log.m98i(WpcAuthenticator.TAG, "handleMessage, auth stop");
                    WpcAuthenticator.this.mWpcAuthHandler.removeCallbacksAndMessages(null);
                    if (WpcAuthenticator.this.mAuthWakeLock.isHeld()) {
                        WpcAuthenticator.this.mAuthWakeLock.release();
                    }
                    WpcAuthenticator.this.mBattAuthHelper.close_batt_misc();
                    WpcAuthenticator.this.currentStatus = 0;
                    break;
                case 1:
                    WpcAuthenticator.this.mWpcAuthHandler.removeCallbacksAndMessages(null);
                    if (!WpcAuthenticator.this.mAuthWakeLock.isHeld()) {
                        WpcAuthenticator.this.mAuthWakeLock.acquire(1200000L);
                    }
                    if (-1 == WpcAuthenticator.this.mBattAuthHelper.open_batt_misc()) {
                        Log.m96e(WpcAuthenticator.TAG, "fail to open_batt_misc");
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                        break;
                    } else {
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(2);
                        break;
                    }
                case 2:
                    if (!WpcAuthenticator.this.requestDigests()) {
                        Log.m96e(WpcAuthenticator.TAG, "handleMessage, requestDigests fail");
                        WpcAuthenticator.this.setAuthPass(false);
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                        break;
                    } else {
                        Log.m98i(WpcAuthenticator.TAG, "handleMessage, success req digests");
                        WpcAuthenticator.this.mWpcAuthHandler.sendMessageDelayed(WpcAuthenticator.this.mWpcAuthHandler.obtainMessage(1000, getNameOfMsgWhat(msg.what)), 60000L);
                        WpcAuthenticator.this.currentStatus = 1;
                        break;
                    }
                case 3:
                    WpcAuthenticator.this.mWpcAuthHandler.removeMessages(1000);
                    if (!WpcAuthenticator.this.getAndCheckDigests()) {
                        Log.m96e(WpcAuthenticator.TAG, "handleMessage, check digest fail, req cert chain");
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(4);
                        break;
                    } else {
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(7);
                        break;
                    }
                case 4:
                    if (!WpcAuthenticator.this.requestCertChain()) {
                        WpcAuthenticator.this.setAuthPass(false);
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                        break;
                    } else {
                        Log.m98i(WpcAuthenticator.TAG, "handleMessage, success req cert");
                        WpcAuthenticator.this.mWpcAuthHandler.sendMessageDelayed(WpcAuthenticator.this.mWpcAuthHandler.obtainMessage(1000, getNameOfMsgWhat(msg.what)), ParcelableCallAnalytics.MILLIS_IN_5_MINUTES);
                        WpcAuthenticator.this.currentStatus = 2;
                        break;
                    }
                case 5:
                    WpcAuthenticator.this.mWpcAuthHandler.removeMessages(1000);
                    if (!WpcAuthenticator.this.getAndVerifyCertChain()) {
                        WpcAuthenticator.this.setAuthPass(false);
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                        break;
                    } else {
                        Log.m98i(WpcAuthenticator.TAG, "handleMessage, success verify cert");
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(7);
                        break;
                    }
                case 7:
                    if (!WpcAuthenticator.this.requestChallengeAuth()) {
                        WpcAuthenticator.this.setAuthPass(false);
                        WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                        break;
                    } else {
                        Log.m98i(WpcAuthenticator.TAG, "handleMessage, success req challenge");
                        WpcAuthenticator.this.mWpcAuthHandler.sendMessageDelayed(WpcAuthenticator.this.mWpcAuthHandler.obtainMessage(1000, getNameOfMsgWhat(msg.what)), 90000L);
                        WpcAuthenticator.this.currentStatus = 3;
                        break;
                    }
                case 8:
                    WpcAuthenticator.this.mWpcAuthHandler.removeMessages(1000);
                    boolean isPass = WpcAuthenticator.this.getAndVerifyChallenge();
                    WpcAuthenticator.this.setAuthPass(isPass);
                    WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                    break;
                case 1000:
                    String msgSender = msg.obj.toString();
                    Log.m96e(WpcAuthenticator.TAG, "handleMessage, timeout after " + msgSender);
                    WpcAuthenticator.this.setAuthPass(false);
                    WpcAuthenticator.this.mWpcAuthHandler.sendEmptyMessage(0);
                    break;
            }
        }

        private String getNameOfMsgWhat(int what) {
            if (what == 0) {
                return "MSG_AUTH_STOP";
            }
            if (1 == what) {
                return "MSG_AUTH_START";
            }
            if (2 == what) {
                return "MSG_AUTH_REQ_DIGEST";
            }
            if (3 == what) {
                return "MSG_AUTH_GET_AND_CHECK_DIGEST";
            }
            if (4 == what) {
                return "MSG_AUTH_REQ_CERT_CHAIN";
            }
            if (5 == what) {
                return "MSG_AUTH_GET_AND_VERIFY_CERT_CHAIN";
            }
            if (7 == what) {
                return "MSG_AUTH_REQ_CHALLENGE";
            }
            if (8 == what) {
                return "MSG_AUTH_GET_AND_VERIFY_CHALLENGE";
            }
            if (1000 == what) {
                return "MSG_AUTH_TIMEOUT";
            }
            return what + "";
        }
    }
}
