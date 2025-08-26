package com.samsung.android.lock;

import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.widget.LockPatternUtils;
import com.android.server.SecureKeyConst;
import com.samsung.android.core.AppJumpBlockTool;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* loaded from: classes6.dex */
public class LsLogEnroll {
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static final String REQUESTOR_NAME = "ERequestor.log";
    private static final String TAG = "LsLogEnroll";
    private static final String UNKNOWN_REQUESTOR = "Unknown";
    private static LsLogEnroll mEnrollResult;
    int mElapsedTime;
    byte[] mMessage;
    byte[] mPackage;
    int mProcessId;
    long mProtectorId;
    byte[] mReason;
    long mReqTime;
    int mResponse;
    byte[] mSalt;
    int mSlot;
    int mType;
    int mUserId;

    public LsLogEnroll(int i) {
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
        this.mReqTime = -1L;
        this.mProtectorId = -1L;
        this.mReason = null;
        this.mPackage = null;
        this.mSalt = null;
        this.mMessage = null;
    }

    public LsLogEnroll setPackage(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            if (DEBUG) {
                LsLog.enroll("Unknown package :\n" + Debug.getCallers(10, "    "));
            }
            str = "Unknown";
        }
        this.mProcessId = i;
        this.mPackage = str.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public LsLogEnroll setReason(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "Unknown";
        }
        this.mReason = str.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public LsLogEnroll setData(int i, int i2, long j, byte[] bArr) {
        this.mType = i;
        this.mSlot = i2;
        this.mProtectorId = j;
        if (bArr != null) {
            this.mSalt = Arrays.copyOf(bArr, bArr.length);
        }
        return this;
    }

    public LsLogEnroll setResponse(int i, String str) {
        this.mResponse = i;
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
        return String.format("Enroll Finish [User %d %s(%d)][%s:%d]\n    [%s(%d)][%016x]%s(%dms)\n", Integer.valueOf(this.mUserId), LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mSlot), byteToText(bArr), Integer.valueOf(this.mProcessId), byteToText(this.mMessage), Integer.valueOf(this.mResponse), Long.valueOf(this.mProtectorId), LsUtil.gethashStr(this.mSalt), Integer.valueOf(this.mElapsedTime));
    }

    public String toSummary() {
        byte[] bArr = this.mPackage;
        if (bArr == null) {
            bArr = this.mReason;
        }
        String strByteToText = byteToText(bArr);
        String strByteToText2 = byteToText(this.mMessage);
        String str = String.format("%s Enroll %s [%s,U:%d,S:%d][%s]", LsUtil.getTimeForLog(this.mReqTime), this.mResponse == 0 ? SecureKeyConst.AT_RESPONSE_OK : AppJumpBlockTool.RESULT_FAIL, LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mUserId), Integer.valueOf(this.mSlot), strByteToText);
        if (this.mResponse == 0) {
            return str;
        }
        return str + String.format("[%s]", strByteToText2);
    }

    public String toDetailsLog(boolean z) {
        String str;
        byte[] bArr = this.mPackage;
        if (bArr == null) {
            bArr = this.mReason;
        }
        String strByteToText = byteToText(bArr);
        String strByteToText2 = byteToText(this.mMessage);
        if (z) {
            str = String.format("  = SI Checker Set Time : %s [%s:%d]\n  = SI Checker type : %s / slot(%d)\n  = SI Checker hash : %s\n", LsUtil.timestampToString(this.mReqTime), strByteToText, Integer.valueOf(this.mProcessId), LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mSlot), LsUtil.gethashStr(this.mSalt));
        } else {
            str = String.format("  = LSKF Set Time : %s [%s:%d]\n  = LSKF type : %s / slot(%d)\n  = LSKF salt : %s\n", LsUtil.timestampToString(this.mReqTime), strByteToText, Integer.valueOf(this.mProcessId), LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mSlot), LsUtil.gethashStr(this.mSalt));
        }
        if (!z && this.mResponse == 0) {
            return str;
        }
        return str + String.format("  = Result(%d) : %s\n", Integer.valueOf(this.mResponse), strByteToText2);
    }

    public byte[] toBytes() {
        int length = this.mReason.length + 50;
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
        byteBufferAllocate.put((byte) 1);
        byteBufferAllocate.put((byte) 1);
        byteBufferAllocate.putInt(this.mUserId);
        byteBufferAllocate.putInt(this.mProcessId);
        byteBufferAllocate.putInt(this.mType);
        byteBufferAllocate.putInt(this.mSlot);
        byteBufferAllocate.putInt(this.mResponse);
        byteBufferAllocate.putInt(this.mElapsedTime);
        byteBufferAllocate.putLong(this.mReqTime);
        byteBufferAllocate.putLong(this.mProtectorId);
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

    public static LsLogEnroll fromBytes(byte[] bArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArr.length - 2);
        byteBufferAllocate.put(bArr, 2, bArr.length - 2);
        byteBufferAllocate.flip();
        LsLogEnroll lsLogEnroll = new LsLogEnroll(byteBufferAllocate.getInt());
        lsLogEnroll.mProcessId = byteBufferAllocate.getInt();
        lsLogEnroll.mType = byteBufferAllocate.getInt();
        lsLogEnroll.mSlot = byteBufferAllocate.getInt();
        lsLogEnroll.mResponse = byteBufferAllocate.getInt();
        lsLogEnroll.mElapsedTime = byteBufferAllocate.getInt();
        lsLogEnroll.mReqTime = byteBufferAllocate.getLong();
        lsLogEnroll.mProtectorId = byteBufferAllocate.getLong();
        byte[] bArr2 = new byte[byteBufferAllocate.getInt()];
        lsLogEnroll.mReason = bArr2;
        byteBufferAllocate.get(bArr2);
        int i = byteBufferAllocate.getInt();
        if (i > 0) {
            byte[] bArr3 = new byte[i];
            lsLogEnroll.mPackage = bArr3;
            byteBufferAllocate.get(bArr3);
        } else {
            lsLogEnroll.mPackage = null;
        }
        int i2 = byteBufferAllocate.getInt();
        if (i2 > 0) {
            byte[] bArr4 = new byte[i2];
            lsLogEnroll.mSalt = bArr4;
            byteBufferAllocate.get(bArr4);
        } else {
            lsLogEnroll.mSalt = null;
        }
        int i3 = byteBufferAllocate.getInt();
        if (i3 > 0) {
            byte[] bArr5 = new byte[i3];
            lsLogEnroll.mMessage = bArr5;
            byteBufferAllocate.get(bArr5);
        }
        return lsLogEnroll;
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
        LsLogEnroll lsLogEnroll = new LsLogEnroll(i);
        lsLogEnroll.setPackage(i2, str);
        saveRequestor(lsLogEnroll);
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

    public static void finish(int i, String str) {
        Log.w(TAG, "finish");
        try {
            LsLogEnroll lsLogEnrollOpenResult = openResult();
            lsLogEnrollOpenResult.setResponse(i, str);
            LsLogSummary.addEnrollResult(lsLogEnrollOpenResult, true);
            LsLog.enroll(lsLogEnrollOpenResult.toString());
            closeResult();
        } catch (Exception e) {
            Log.w(TAG, "finish failed" + e);
        }
    }

    private static LsLogEnroll openResult() {
        return openResult(-1);
    }

    private static LsLogEnroll openResult(int i) {
        LsLogEnroll lsLogEnroll = mEnrollResult;
        if (lsLogEnroll != null) {
            return lsLogEnroll;
        }
        if (i >= 0) {
            try {
                mEnrollResult = loadRequestor(i);
            } catch (Exception e) {
                Log.e(TAG, "loadRequestor failed ", e);
            }
            if (mEnrollResult == null) {
                LsLog.enroll("No enroll data for user " + i + ". Unknown requestor :\n" + Debug.getCallers(10, "    "));
                mEnrollResult = new LsLogEnroll(i);
            }
        } else {
            LsLog.enroll("No enroll data for user " + i + ". Unknown requestor :\n" + Debug.getCallers(10, "    "));
            mEnrollResult = new LsLogEnroll(0);
        }
        return mEnrollResult;
    }

    private static void closeResult() {
        mEnrollResult = null;
        saveRequestor(null);
    }

    private static void saveRequestor(LsLogEnroll lsLogEnroll) {
        if (lsLogEnroll == null) {
            LsLogSummary.saveFile(REQUESTOR_NAME, new byte[]{0});
            return;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(lsLogEnroll.mPackage.length + 20);
        byteBufferAllocate.putInt(lsLogEnroll.mUserId);
        byteBufferAllocate.putInt(lsLogEnroll.mProcessId);
        byteBufferAllocate.putLong(lsLogEnroll.mReqTime);
        byteBufferAllocate.putInt(lsLogEnroll.mPackage.length);
        byteBufferAllocate.put(lsLogEnroll.mPackage);
        LsLogSummary.saveFile(REQUESTOR_NAME, byteBufferAllocate.array());
    }

    private static LsLogEnroll loadRequestor(int i) {
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
        LsLogEnroll lsLogEnroll = new LsLogEnroll(i);
        lsLogEnroll.mProcessId = i3;
        lsLogEnroll.mReqTime = j;
        byte[] bArr = new byte[byteBufferAllocate.getInt()];
        lsLogEnroll.mPackage = bArr;
        byteBufferAllocate.get(bArr);
        return lsLogEnroll;
    }
}
