package com.samsung.android.lock;

import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.widget.LockPatternUtils;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class LsLogVerify {
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final String REQUESTOR_NAME = "VRequestor.log";
    private static final String TAG = "LsLogVerify";
    private static final String UNKNOWN_REQUESTOR = "Unknown";
    private static LsLogVerify mVerifyResult;
    int mElapsedTime;
    int mFailCount;
    byte[] mMessage;
    byte[] mPackage;
    int mProcessId;
    long mProtectorId;
    byte[] mReason;
    long mReqTime;
    int mResponse;
    byte[] mSalt;
    int mSlot;
    long mTimeout;
    int mType;
    int mUserId;

    public LsLogVerify(int i) {
        init();
        this.mUserId = i;
        this.mReqTime = System.currentTimeMillis();
    }

    private void init() {
        this.mUserId = -1;
        this.mProcessId = -1;
        this.mType = -1;
        this.mSlot = -1;
        this.mResponse = -1;
        this.mElapsedTime = -1;
        this.mFailCount = -1;
        this.mReqTime = -1L;
        this.mProtectorId = -1L;
        this.mTimeout = -1L;
        this.mReason = null;
        this.mPackage = null;
        this.mSalt = null;
        this.mMessage = null;
    }

    public LsLogVerify setPackage(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            if (DEBUG) {
                Log.w(TAG, "Unknown package :\n" + Debug.getCallers(10, "    "));
            }
            str = "Unknown";
        }
        this.mProcessId = i;
        this.mPackage = str.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public LsLogVerify setReason(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "Unknown";
        }
        this.mReason = str.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public LsLogVerify setData(int i, int i2, long j, byte[] bArr) {
        this.mType = i;
        this.mSlot = i2;
        this.mProtectorId = j;
        if (bArr != null) {
            this.mSalt = Arrays.copyOf(bArr, bArr.length);
        }
        return this;
    }

    public LsLogVerify setResponse(int i, int i2, long j, String str) {
        this.mResponse = i;
        this.mFailCount = i2;
        this.mTimeout = j;
        if (!TextUtils.isEmpty(str)) {
            this.mMessage = str.getBytes(StandardCharsets.UTF_8);
        }
        this.mElapsedTime = (int) (System.currentTimeMillis() - this.mReqTime);
        return this;
    }

    private static String byteToText(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        return new String(bArr, StandardCharsets.UTF_8);
    }

    public String toString() {
        byte[] bArr = this.mPackage;
        if (bArr == null) {
            bArr = this.mReason;
        }
        String strByteToText = byteToText(bArr);
        String strByteToText2 = byteToText(this.mMessage);
        if (this.mResponse == 0) {
            return String.format("User %d(%d) %s [%s]%s(%dms)", Integer.valueOf(this.mUserId), Integer.valueOf(this.mSlot), strByteToText2, strByteToText, LsUtil.gethashStr(this.mSalt), Integer.valueOf(this.mElapsedTime));
        }
        return String.format("User %d(%d) %s(%d) [%s]%s(%d/%d)(%dms)", Integer.valueOf(this.mUserId), Integer.valueOf(this.mSlot), strByteToText2, Integer.valueOf(this.mResponse), strByteToText, LsUtil.gethashStr(this.mSalt), Integer.valueOf(this.mFailCount), Long.valueOf(this.mTimeout), Integer.valueOf(this.mElapsedTime));
    }

    public String toSummary() {
        byte[] bArr = this.mPackage;
        if (bArr == null) {
            bArr = this.mReason;
        }
        String strByteToText = byteToText(bArr);
        if (this.mResponse == 0) {
            return String.format("%s Verify OK(U:%d,S:%d) [%s]", LsUtil.getTimeForLog(this.mReqTime), Integer.valueOf(this.mUserId), Integer.valueOf(this.mSlot), strByteToText);
        }
        return String.format("%s Verify Fail(U:%d,S:%d,E:%d) [%s](%d/%d)]", LsUtil.getTimeForLog(this.mReqTime), Integer.valueOf(this.mUserId), Integer.valueOf(this.mSlot), Integer.valueOf(this.mResponse), strByteToText, Integer.valueOf(this.mFailCount), Long.valueOf(this.mTimeout));
    }

    public String toDetailsLog(boolean z) {
        String str;
        byte[] bArr = this.mPackage;
        if (bArr == null) {
            bArr = this.mReason;
        }
        String str2 = String.format("  = %s [%s:%d]\n", LsUtil.timestampToString(this.mReqTime), byteToText(bArr), Integer.valueOf(this.mProcessId));
        if (z) {
            str = str2 + String.format("  = Slot(%d) / Hash : %s\n", Integer.valueOf(this.mSlot), LsUtil.gethashStr(this.mSalt));
        } else {
            str = str2 + String.format("  = %s / Slot(%d) / Salt : %s\n", LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mSlot), LsUtil.gethashStr(this.mSalt));
        }
        if (!z && this.mResponse == 0) {
            return str;
        }
        return str + String.format("  = %s(%d) (%d/%d)\n", byteToText(this.mMessage), Integer.valueOf(this.mResponse), Integer.valueOf(this.mFailCount), Long.valueOf(this.mTimeout));
    }

    public String toEventLog() {
        byte[] bArr = this.mPackage;
        if (bArr == null) {
            bArr = this.mReason;
        }
        String strByteToText = byteToText(bArr);
        if (this.mResponse == 0) {
            return String.format("User %d %s OK on [%s]", Integer.valueOf(this.mUserId), LockPatternUtils.credentialTypeToString(this.mType), strByteToText);
        }
        return String.format("User %d %s Fail on [%s](%d/%d)]", Integer.valueOf(this.mUserId), LockPatternUtils.credentialTypeToString(this.mType), strByteToText, Integer.valueOf(this.mFailCount), Long.valueOf(this.mTimeout));
    }

    public byte[] toBytes() {
        int length = this.mReason.length + 62;
        byte[] bArr = this.mPackage;
        if (bArr != null) {
            length += bArr.length;
        }
        int length2 = length + 4;
        byte[] bArr2 = this.mSalt;
        if (bArr2 != null) {
            length2 += bArr2.length;
        }
        int length3 = length2 + 4;
        byte[] bArr3 = this.mMessage;
        if (bArr3 != null) {
            length3 += bArr3.length;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length3);
        if (this.mResponse == 0) {
            byteBufferAllocate.put((byte) 2);
        } else {
            byteBufferAllocate.put((byte) 3);
        }
        byteBufferAllocate.put((byte) 1);
        byteBufferAllocate.putInt(this.mUserId);
        byteBufferAllocate.putInt(this.mProcessId);
        byteBufferAllocate.putInt(this.mType);
        byteBufferAllocate.putInt(this.mSlot);
        byteBufferAllocate.putInt(this.mResponse);
        byteBufferAllocate.putInt(this.mElapsedTime);
        byteBufferAllocate.putInt(this.mFailCount);
        byteBufferAllocate.putLong(this.mReqTime);
        byteBufferAllocate.putLong(this.mProtectorId);
        byteBufferAllocate.putLong(this.mTimeout);
        byteBufferAllocate.putInt(this.mReason.length);
        byteBufferAllocate.put(this.mReason);
        byte[] bArr4 = this.mPackage;
        if (bArr4 != null) {
            byteBufferAllocate.putInt(bArr4.length);
            byteBufferAllocate.put(this.mPackage);
        } else {
            byteBufferAllocate.putInt(0);
        }
        byte[] bArr5 = this.mSalt;
        if (bArr5 != null) {
            byteBufferAllocate.putInt(bArr5.length);
            byteBufferAllocate.put(this.mSalt);
        } else {
            byteBufferAllocate.putInt(0);
        }
        byte[] bArr6 = this.mMessage;
        if (bArr6 != null) {
            byteBufferAllocate.putInt(bArr6.length);
            byteBufferAllocate.put(this.mMessage);
        } else {
            byteBufferAllocate.putInt(0);
        }
        return byteBufferAllocate.array();
    }

    public static LsLogVerify fromBytes(byte[] bArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length - 2);
        byteBufferAllocate.put(bArr, 2, bArr.length - 2);
        byteBufferAllocate.flip();
        LsLogVerify lsLogVerify = new LsLogVerify(byteBufferAllocate.getInt());
        lsLogVerify.mProcessId = byteBufferAllocate.getInt();
        lsLogVerify.mType = byteBufferAllocate.getInt();
        lsLogVerify.mSlot = byteBufferAllocate.getInt();
        lsLogVerify.mResponse = byteBufferAllocate.getInt();
        lsLogVerify.mElapsedTime = byteBufferAllocate.getInt();
        lsLogVerify.mFailCount = byteBufferAllocate.getInt();
        lsLogVerify.mReqTime = byteBufferAllocate.getLong();
        lsLogVerify.mProtectorId = byteBufferAllocate.getLong();
        lsLogVerify.mTimeout = byteBufferAllocate.getLong();
        byte[] bArr2 = new byte[byteBufferAllocate.getInt()];
        lsLogVerify.mReason = bArr2;
        byteBufferAllocate.get(bArr2);
        int i = byteBufferAllocate.getInt();
        if (i > 0) {
            byte[] bArr3 = new byte[i];
            lsLogVerify.mPackage = bArr3;
            byteBufferAllocate.get(bArr3);
        } else {
            lsLogVerify.mPackage = null;
        }
        int i2 = byteBufferAllocate.getInt();
        if (i2 > 0) {
            byte[] bArr4 = new byte[i2];
            lsLogVerify.mSalt = bArr4;
            byteBufferAllocate.get(bArr4);
        } else {
            lsLogVerify.mSalt = null;
        }
        int i3 = byteBufferAllocate.getInt();
        if (i3 > 0) {
            byte[] bArr5 = new byte[i3];
            lsLogVerify.mMessage = bArr5;
            byteBufferAllocate.get(bArr5);
        }
        return lsLogVerify;
    }

    public static void request(int i, int i2, String str) {
        Log.w(TAG, "request");
        if (i == -9899) {
            try {
                str = str + "(N-1)";
                i = 0;
            } catch (Exception e) {
                Log.w(TAG, "request failed" + e);
                return;
            }
        }
        LsLogVerify lsLogVerify = new LsLogVerify(i);
        lsLogVerify.setPackage(i2, str);
        saveRequestor(lsLogVerify);
    }

    public static void begin(int i, Throwable th) {
        Log.w(TAG, "begin");
        try {
            StackTraceElement[] stackTrace = th.getStackTrace();
            String methodName = stackTrace.length > 2 ? stackTrace[2].getMethodName() : null;
            if (methodName == null) {
                methodName = stackTrace.length > 1 ? stackTrace[1].getMethodName() : "Unknown";
            }
            openResult(i).setReason(methodName);
        } catch (Exception e) {
            Log.w(TAG, "begin failed" + e);
        }
    }

    public static void update(int i, int i2, long j, byte[] bArr) {
        Log.w(TAG, "update");
        try {
            openResult().setData(i, i2, j, bArr);
        } catch (Exception e) {
            Log.w(TAG, "update failed" + e);
        }
    }

    public static void finish(int i, long j, String str) {
        int i2;
        int i3;
        Log.w(TAG, "finish");
        try {
            LsLogVerify lsLogVerifyOpenResult = openResult();
            int failureCount = LsLog.getFailureCount(lsLogVerifyOpenResult.mUserId) + 1;
            if (i == 0) {
                i2 = i;
                lsLogVerifyOpenResult.setResponse(i2, 0, 0L, str);
                LsLogSummary.addVerifySuccess(lsLogVerifyOpenResult, true);
                i3 = failureCount;
            } else {
                i2 = i;
                i3 = failureCount;
                lsLogVerifyOpenResult.setResponse(i2, i3, j, str);
                LsLogSummary.addVerifyFailed(lsLogVerifyOpenResult, true);
            }
            if (i2 != 0 || i3 > 1) {
                LsLog.verify(lsLogVerifyOpenResult.toString());
            }
            LsLog.events(lsLogVerifyOpenResult.toEventLog());
            closeResult();
        } catch (Exception e) {
            Log.w(TAG, "finish failed" + e);
        }
    }

    private static LsLogVerify openResult() {
        return openResult(-1);
    }

    private static LsLogVerify openResult(int i) {
        LsLogVerify lsLogVerify = mVerifyResult;
        if (lsLogVerify != null) {
            return lsLogVerify;
        }
        if (i >= 0) {
            try {
                mVerifyResult = loadRequestor(i);
            } catch (Exception e) {
                Log.e(TAG, "loadRequestor failed ", e);
            }
            if (mVerifyResult == null) {
                Log.w(TAG, "No verify data for user " + i + ". Unknown requestor :\n" + Debug.getCallers(10, "    "));
                mVerifyResult = new LsLogVerify(i);
            }
        } else {
            LsLog.verify("No verify data for user " + i + ". Unknown requestor :\n" + Debug.getCallers(10, "    "));
            mVerifyResult = new LsLogVerify(0);
        }
        return mVerifyResult;
    }

    private static void closeResult() {
        mVerifyResult = null;
        saveRequestor(null);
    }

    private static void saveRequestor(LsLogVerify lsLogVerify) {
        if (lsLogVerify == null) {
            LsLogSummary.saveFile(REQUESTOR_NAME, new byte[]{0});
            return;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(lsLogVerify.mPackage.length + 20);
        byteBufferAllocate.putInt(lsLogVerify.mUserId);
        byteBufferAllocate.putInt(lsLogVerify.mProcessId);
        byteBufferAllocate.putLong(lsLogVerify.mReqTime);
        byteBufferAllocate.putInt(lsLogVerify.mPackage.length);
        byteBufferAllocate.put(lsLogVerify.mPackage);
        LsLogSummary.saveFile(REQUESTOR_NAME, byteBufferAllocate.array());
    }

    private static LsLogVerify loadRequestor(int i) {
        byte[] bArrLoadFile = LsLogSummary.loadFile(REQUESTOR_NAME, false);
        if (bArrLoadFile == null || bArrLoadFile.length <= 4) {
            Log.w(TAG, "no requestor data");
            return null;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrLoadFile.length);
        byteBufferAllocate.put(bArrLoadFile, 0, bArrLoadFile.length);
        byteBufferAllocate.flip();
        int i2 = byteBufferAllocate.getInt();
        int i3 = byteBufferAllocate.getInt();
        if (i2 != i) {
            Log.w(TAG, String.format("mismatch enroll data, Req User %d, Saved User %d, pid %d)", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
            saveRequestor(null);
            return null;
        }
        long j = byteBufferAllocate.getLong();
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (1000 + j < jCurrentTimeMillis) {
            Log.w(TAG, String.format("request data is too old, req = %s, cur = %s", LsUtil.getTimeForLog(j), LsUtil.getTimeForLog(jCurrentTimeMillis)));
            saveRequestor(null);
            return null;
        }
        LsLogVerify lsLogVerify = new LsLogVerify(i);
        lsLogVerify.mProcessId = i3;
        lsLogVerify.mReqTime = j;
        byte[] bArr = new byte[byteBufferAllocate.getInt()];
        lsLogVerify.mPackage = bArr;
        byteBufferAllocate.get(bArr);
        return lsLogVerify;
    }
}
