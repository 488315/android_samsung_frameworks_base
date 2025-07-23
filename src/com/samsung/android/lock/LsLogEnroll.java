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
        String byteToText = byteToText(bArr);
        String byteToText2 = byteToText(this.mMessage);
        String format = String.format("%s Enroll %s [%s,U:%d,S:%d][%s]", LsUtil.getTimeForLog(this.mReqTime), this.mResponse == 0 ? SecureKeyConst.AT_RESPONSE_OK : AppJumpBlockTool.RESULT_FAIL, LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mUserId), Integer.valueOf(this.mSlot), byteToText);
        if (this.mResponse == 0) {
            return format;
        }
        return format + String.format("[%s]", byteToText2);
    }

    public String toDetailsLog(boolean z) {
        String format;
        byte[] bArr = this.mPackage;
        if (bArr == null) {
            bArr = this.mReason;
        }
        String byteToText = byteToText(bArr);
        String byteToText2 = byteToText(this.mMessage);
        if (z) {
            format = String.format("  = SI Checker Set Time : %s [%s:%d]\n  = SI Checker type : %s / slot(%d)\n  = SI Checker hash : %s\n", LsUtil.timestampToString(this.mReqTime), byteToText, Integer.valueOf(this.mProcessId), LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mSlot), LsUtil.gethashStr(this.mSalt));
        } else {
            format = String.format("  = LSKF Set Time : %s [%s:%d]\n  = LSKF type : %s / slot(%d)\n  = LSKF salt : %s\n", LsUtil.timestampToString(this.mReqTime), byteToText, Integer.valueOf(this.mProcessId), LockPatternUtils.credentialTypeToString(this.mType), Integer.valueOf(this.mSlot), LsUtil.gethashStr(this.mSalt));
        }
        if (!z && this.mResponse == 0) {
            return format;
        }
        return format + String.format("  = Result(%d) : %s\n", Integer.valueOf(this.mResponse), byteToText2);
    }

    public byte[] toBytes() {
        int length = this.mReason.length + 50;
        byte[] bArr = this.mPackage;
        if (bArr != null) {
            length += bArr.length;
        }
        int i = length + 4;
        byte[] bArr2 = this.mSalt;
        if (bArr2 != null) {
            i += bArr2.length;
        }
        int i2 = i + 4;
        byte[] bArr3 = this.mMessage;
        if (bArr3 != null) {
            i2 += bArr3.length;
        }
        ByteBuffer allocate = ByteBuffer.allocate(i2);
        allocate.put((byte) 1);
        allocate.put((byte) 1);
        allocate.putInt(this.mUserId);
        allocate.putInt(this.mProcessId);
        allocate.putInt(this.mType);
        allocate.putInt(this.mSlot);
        allocate.putInt(this.mResponse);
        allocate.putInt(this.mElapsedTime);
        allocate.putLong(this.mReqTime);
        allocate.putLong(this.mProtectorId);
        allocate.putInt(this.mReason.length);
        allocate.put(this.mReason);
        byte[] bArr4 = this.mPackage;
        if (bArr4 != null) {
            allocate.putInt(bArr4.length);
            allocate.put(this.mPackage);
        } else {
            allocate.putInt(0);
        }
        byte[] bArr5 = this.mSalt;
        if (bArr5 != null) {
            allocate.putInt(bArr5.length);
            allocate.put(this.mSalt);
        } else {
            allocate.putInt(0);
        }
        byte[] bArr6 = this.mMessage;
        if (bArr6 != null) {
            allocate.putInt(bArr6.length);
            allocate.put(this.mMessage);
        } else {
            allocate.putInt(0);
        }
        return allocate.array();
    }

    public static LsLogEnroll fromBytes(byte[] bArr) {
        ByteBuffer allocate = ByteBuffer.allocate(bArr.length - 2);
        allocate.put(bArr, 2, bArr.length - 2);
        allocate.flip();
        LsLogEnroll lsLogEnroll = new LsLogEnroll(allocate.getInt());
        lsLogEnroll.mProcessId = allocate.getInt();
        lsLogEnroll.mType = allocate.getInt();
        lsLogEnroll.mSlot = allocate.getInt();
        lsLogEnroll.mResponse = allocate.getInt();
        lsLogEnroll.mElapsedTime = allocate.getInt();
        lsLogEnroll.mReqTime = allocate.getLong();
        lsLogEnroll.mProtectorId = allocate.getLong();
        byte[] bArr2 = new byte[allocate.getInt()];
        lsLogEnroll.mReason = bArr2;
        allocate.get(bArr2);
        int i = allocate.getInt();
        if (i > 0) {
            byte[] bArr3 = new byte[i];
            lsLogEnroll.mPackage = bArr3;
            allocate.get(bArr3);
        } else {
            lsLogEnroll.mPackage = null;
        }
        int i2 = allocate.getInt();
        if (i2 > 0) {
            byte[] bArr4 = new byte[i2];
            lsLogEnroll.mSalt = bArr4;
            allocate.get(bArr4);
        } else {
            lsLogEnroll.mSalt = null;
        }
        int i3 = allocate.getInt();
        if (i3 > 0) {
            byte[] bArr5 = new byte[i3];
            lsLogEnroll.mMessage = bArr5;
            allocate.get(bArr5);
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
            LsLogEnroll openResult = openResult();
            openResult.setResponse(i, str);
            LsLogSummary.addEnrollResult(openResult, true);
            LsLog.enroll(openResult.toString());
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
        ByteBuffer allocate = ByteBuffer.allocate(lsLogEnroll.mPackage.length + 20);
        allocate.putInt(lsLogEnroll.mUserId);
        allocate.putInt(lsLogEnroll.mProcessId);
        allocate.putLong(lsLogEnroll.mReqTime);
        allocate.putInt(lsLogEnroll.mPackage.length);
        allocate.put(lsLogEnroll.mPackage);
        LsLogSummary.saveFile(REQUESTOR_NAME, allocate.array());
    }

    private static LsLogEnroll loadRequestor(int i) {
        byte[] loadFile = LsLogSummary.loadFile(REQUESTOR_NAME, false);
        if (loadFile == null || loadFile.length <= 4) {
            Log.w(TAG, "no requestor data");
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(loadFile.length);
        allocate.put(loadFile, 0, loadFile.length);
        allocate.flip();
        int i2 = allocate.getInt();
        int i3 = allocate.getInt();
        if (i2 != i) {
            Log.w(TAG, String.format("mismatch enroll data, Req User %d, Saved User %d, pid %d)", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
            saveRequestor(null);
            return null;
        }
        long j = allocate.getLong();
        long currentTimeMillis = System.currentTimeMillis();
        if (1000 + j < currentTimeMillis) {
            Log.w(TAG, String.format("request data is too old, req = %s, cur = %s", LsUtil.getTimeForLog(j), LsUtil.getTimeForLog(currentTimeMillis)));
            saveRequestor(null);
            return null;
        }
        LsLogEnroll lsLogEnroll = new LsLogEnroll(i);
        lsLogEnroll.mProcessId = i3;
        lsLogEnroll.mReqTime = j;
        byte[] bArr = new byte[allocate.getInt()];
        lsLogEnroll.mPackage = bArr;
        allocate.get(bArr);
        return lsLogEnroll;
    }
}
