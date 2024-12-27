package com.samsung.android.lock;

import android.hardware.gnss.GnssSignalType;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class LsLogEnroll {
    private static final String REQUESTOR_NAME = "ERequestor.log";
    private static final String TAG = "LsLogEnroll";
    private static final String UNKNOWN_REQUESTOR = "Unknown";
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
    private static final boolean DEBUG = LsConstants.DEBUG;
    private static LsLogEnroll mEnrollResult = null;

    public LsLogEnroll(int userId) {
        init();
        this.mUserId = userId;
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

    public LsLogEnroll setPackage(int pid, String pkg) {
        if (TextUtils.isEmpty(pkg)) {
            pkg = UNKNOWN_REQUESTOR;
            if (DEBUG) {
                LsLog.enroll("Unknown package :\n" + Debug.getCallers(10, "    "));
            }
        }
        this.mProcessId = pid;
        this.mPackage = pkg.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public LsLogEnroll setReason(String reason) {
        if (TextUtils.isEmpty(reason)) {
            reason = UNKNOWN_REQUESTOR;
        }
        this.mReason = reason.getBytes(StandardCharsets.UTF_8);
        return this;
    }

    public LsLogEnroll setData(int type, int slot, long protector, byte[] salt) {
        this.mType = type;
        this.mSlot = slot;
        this.mProtectorId = protector;
        if (salt != null) {
            this.mSalt = Arrays.copyOf(salt, salt.length);
        }
        return this;
    }

    public LsLogEnroll setResponse(int response, String msg) {
        this.mResponse = response;
        if (!TextUtils.isEmpty(msg)) {
            this.mMessage = msg.getBytes(StandardCharsets.UTF_8);
        }
        this.mElapsedTime = (int) (System.currentTimeMillis() - this.mReqTime);
        return this;
    }

    private static String byteToText(byte[] data) {
        if (data == null || data.length == 0) {
            return "null";
        }
        return new String(data, StandardCharsets.UTF_8);
    }

    public String toString() {
        String req = byteToText(this.mPackage != null ? this.mPackage : this.mReason);
        String msg = byteToText(this.mMessage);
        String result =
                String.format(
                        "Finish [User %d %s(%d)][%s:%d]\n    [%s(%d)][%016x]%s(%dms)\n",
                        Integer.valueOf(this.mUserId),
                        LockPatternUtils.credentialTypeToString(this.mType),
                        Integer.valueOf(this.mSlot),
                        req,
                        Integer.valueOf(this.mProcessId),
                        msg,
                        Integer.valueOf(this.mResponse),
                        Long.valueOf(this.mProtectorId),
                        LsUtil.gethashStr(this.mSalt),
                        Integer.valueOf(this.mElapsedTime));
        return result;
    }

    public String toSummary() {
        String req = byteToText(this.mPackage != null ? this.mPackage : this.mReason);
        String msg = byteToText(this.mMessage);
        String result =
                String.format(
                        "%s E:%s [User %d %s]\n    [%s]\n",
                        LsUtil.getTimeForSummary(this.mReqTime),
                        this.mResponse == 0 ? GnssSignalType.CODE_TYPE_S : "F",
                        Integer.valueOf(this.mUserId),
                        LockPatternUtils.credentialTypeToString(this.mType),
                        req);
        if (this.mResponse != 0) {
            return result + String.format("    [%s]\n", msg);
        }
        return result;
    }

    public byte[] toBytes() {
        int bufferSize = this.mReason.length + 46 + 4;
        if (this.mPackage != null) {
            bufferSize += this.mPackage.length;
        }
        int bufferSize2 = bufferSize + 4;
        if (this.mSalt != null) {
            bufferSize2 += this.mSalt.length;
        }
        int bufferSize3 = bufferSize2 + 4;
        if (this.mMessage != null) {
            bufferSize3 += this.mMessage.length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize3);
        buffer.put((byte) 1);
        buffer.put((byte) 1);
        buffer.putInt(this.mUserId);
        buffer.putInt(this.mProcessId);
        buffer.putInt(this.mType);
        buffer.putInt(this.mSlot);
        buffer.putInt(this.mResponse);
        buffer.putInt(this.mElapsedTime);
        buffer.putLong(this.mReqTime);
        buffer.putLong(this.mProtectorId);
        buffer.putInt(this.mReason.length);
        buffer.put(this.mReason);
        if (this.mPackage != null) {
            buffer.putInt(this.mPackage.length);
            buffer.put(this.mPackage);
        } else {
            buffer.putInt(0);
        }
        if (this.mSalt != null) {
            buffer.putInt(this.mSalt.length);
            buffer.put(this.mSalt);
        } else {
            buffer.putInt(0);
        }
        if (this.mMessage != null) {
            buffer.putInt(this.mMessage.length);
            buffer.put(this.mMessage);
        } else {
            buffer.putInt(0);
        }
        return buffer.array();
    }

    public static LsLogEnroll fromBytes(byte[] data) {
        ByteBuffer buffer = ByteBuffer.allocate(data.length - 2);
        buffer.put(data, 2, data.length - 2);
        buffer.flip();
        int userId = buffer.getInt();
        LsLogEnroll result = new LsLogEnroll(userId);
        result.mProcessId = buffer.getInt();
        result.mType = buffer.getInt();
        result.mSlot = buffer.getInt();
        result.mResponse = buffer.getInt();
        result.mElapsedTime = buffer.getInt();
        result.mReqTime = buffer.getLong();
        result.mProtectorId = buffer.getLong();
        int reqLength = buffer.getInt();
        result.mReason = new byte[reqLength];
        buffer.get(result.mReason);
        int pkgLength = buffer.getInt();
        if (pkgLength > 0) {
            result.mPackage = new byte[pkgLength];
            buffer.get(result.mPackage);
        } else {
            result.mPackage = null;
        }
        int saltLength = buffer.getInt();
        if (saltLength > 0) {
            result.mSalt = new byte[saltLength];
            buffer.get(result.mSalt);
        } else {
            result.mSalt = null;
        }
        int msgLength = buffer.getInt();
        if (msgLength > 0) {
            result.mMessage = new byte[msgLength];
            buffer.get(result.mMessage);
        }
        return result;
    }

    public static void request(int userId, int pid, String pkg) {
        Log.w(TAG, "request");
        if (userId == -9899) {
            userId = 0;
            try {
                pkg = pkg + "(N-1)";
            } catch (Exception e) {
                Log.w(TAG, "request failed" + e);
                return;
            }
        }
        LsLogEnroll result = new LsLogEnroll(userId);
        result.setPackage(pid, pkg);
        saveRequestor(result);
    }

    public static void begin(int userId, Throwable throwable) {
        Log.w(TAG, "begin");
        try {
            StackTraceElement[] stacks = throwable.getStackTrace();
            String where = stacks.length > 2 ? stacks[2].getMethodName() : null;
            if (where == null) {
                where = stacks.length > 1 ? stacks[1].getMethodName() : UNKNOWN_REQUESTOR;
            }
            LsLogEnroll result = openResult(userId);
            result.setReason(where);
        } catch (Exception e) {
            Log.w(TAG, "begin failed" + e);
        }
    }

    public static void update(int type, int slot, long protector, byte[] salt) {
        Log.w(TAG, "update");
        try {
            LsLogEnroll result = openResult();
            result.setData(type, slot, protector, salt);
        } catch (Exception e) {
            Log.w(TAG, "update failed" + e);
        }
    }

    public static void finish(int response, String msg) {
        Log.w(TAG, "finish");
        try {
            LsLogEnroll result = openResult();
            result.setResponse(response, msg);
            LsLogSummary.addEnrollResult(result, true);
            LsLog.enroll(result.toString());
            closeResult();
        } catch (Exception e) {
            Log.w(TAG, "finish failed" + e);
        }
    }

    private static LsLogEnroll openResult() {
        return openResult(-1);
    }

    private static LsLogEnroll openResult(int userId) {
        if (mEnrollResult != null) {
            return mEnrollResult;
        }
        if (userId >= 0) {
            try {
                mEnrollResult = loadRequestor(userId);
            } catch (Exception e) {
                Log.e(TAG, "loadRequestor failed ", e);
            }
            if (mEnrollResult == null) {
                LsLog.enroll(
                        "No enroll data for user "
                                + userId
                                + ". Unknown requestor :\n"
                                + Debug.getCallers(10, "    "));
                mEnrollResult = new LsLogEnroll(userId);
            }
        } else {
            LsLog.enroll(
                    "No enroll data for user "
                            + userId
                            + ". Unknown requestor :\n"
                            + Debug.getCallers(10, "    "));
            mEnrollResult = new LsLogEnroll(0);
        }
        return mEnrollResult;
    }

    private static void closeResult() {
        mEnrollResult = null;
        saveRequestor(null);
    }

    private static void saveRequestor(LsLogEnroll result) {
        if (result == null) {
            byte[] data = {0};
            LsLogSummary.saveFile(REQUESTOR_NAME, data);
            return;
        }
        int bufferSize = result.mPackage.length + 20;
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        buffer.putInt(result.mUserId);
        buffer.putInt(result.mProcessId);
        buffer.putLong(result.mReqTime);
        buffer.putInt(result.mPackage.length);
        buffer.put(result.mPackage);
        LsLogSummary.saveFile(REQUESTOR_NAME, buffer.array());
    }

    private static LsLogEnroll loadRequestor(int userId) {
        byte[] data = LsLogSummary.loadFile(REQUESTOR_NAME, false);
        if (data == null || data.length <= 4) {
            Log.w(TAG, "no requestor data");
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data, 0, data.length);
        buffer.flip();
        int savedUserId = buffer.getInt();
        int savedPid = buffer.getInt();
        if (savedUserId != userId) {
            Log.w(
                    TAG,
                    String.format(
                            "mismatch enroll data, Req User %d, Saved User %d, pid %d)",
                            Integer.valueOf(userId),
                            Integer.valueOf(savedUserId),
                            Integer.valueOf(savedPid)));
            saveRequestor(null);
            return null;
        }
        long reqTime = buffer.getLong();
        long curTime = System.currentTimeMillis();
        if (1000 + reqTime < curTime) {
            Log.w(
                    TAG,
                    String.format(
                            "request data is too old, req = %s, cur = %s",
                            LsUtil.getTimeForLog(reqTime), LsUtil.getTimeForLog(curTime)));
            saveRequestor(null);
            return null;
        }
        LsLogEnroll result = new LsLogEnroll(userId);
        result.mProcessId = savedPid;
        result.mReqTime = reqTime;
        int pkgLength = buffer.getInt();
        result.mPackage = new byte[pkgLength];
        buffer.get(result.mPackage);
        return result;
    }
}
