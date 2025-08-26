package com.samsung.android.media.audiofx;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audiofx.AudioEffect;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

/* loaded from: classes6.dex */
public class SemVolumeMonitor extends AudioEffect {
    public static final UUID EFFECT_TYPE_VOLUME_MONITOR = UUID.fromString("f15b944b-0202-451e-a6ff-c61f11beda02");
    private static final int PARAM_GET_ONE_MIN_SCORE_STATUS = 1;
    private static final int PARAM_RESET_DATA = 5;
    private static final int PARAM_SET_ABS_VOLUME_STATE = 4;
    private static final int PARAM_SET_BT_VOL_INDEX = 2;
    private static final int PARAM_SET_ON_OFF = 3;
    private static final String TAG = "SemVolumeMonitor";

    public SemVolumeMonitor(int i, int i2) throws IllegalArgumentException {
        super(EFFECT_TYPE_VOLUME_MONITOR, EFFECT_TYPE_NULL, i, i2);
        if (i2 == 0) {
            Log.w(TAG, "WARNING: attaching an SemVolumeMonitor to global output mix is deprecated!");
        }
    }

    public byte[] getOneMinScoreStatus(int i, int i2) {
        byte[] bArrIntegerArrayToByteArray = integerArrayToByteArray(new Integer[]{1});
        int iMax = Math.max(i, i2);
        byte[] bArr = new byte[iMax];
        Log.i(TAG, "getOneHourRms: call getParameter. bytes:" + iMax + "=max(" + i + "," + i2 + NavigationBarInflaterView.KEY_CODE_END);
        getParameter(bArrIntegerArrayToByteArray, bArr);
        Log.i(TAG, "getOneHourRms: getParameter done");
        return bArr;
    }

    public void setBluetoothVolume(int i) {
        Integer[] numArr = {Integer.valueOf(i)};
        byte[] bArrIntegerArrayToByteArray = integerArrayToByteArray(new Integer[]{2});
        byte[] bArrIntegerArrayToByteArray2 = integerArrayToByteArray(numArr);
        Log.i(TAG, "setBluetoothVolume: call setParameter");
        try {
            setParameter(bArrIntegerArrayToByteArray, bArrIntegerArrayToByteArray2);
        } catch (IllegalStateException e) {
            Log.e(TAG, "setBluetoothVolume#setParameter", e);
        }
        Log.i(TAG, "setBluetoothVolume: setParameter done");
    }

    public void onOff(boolean z) {
        Integer[] numArr = {Integer.valueOf(z ? 1 : 0)};
        byte[] bArrIntegerArrayToByteArray = integerArrayToByteArray(new Integer[]{3});
        byte[] bArrIntegerArrayToByteArray2 = integerArrayToByteArray(numArr);
        Log.i(TAG, "onOff: call setParameter");
        setParameter(bArrIntegerArrayToByteArray, bArrIntegerArrayToByteArray2);
        Log.i(TAG, "onOff: setParameter done");
    }

    public void setAbsoluteVolumeState(boolean z) {
        setParameter(integerArrayToByteArray(new Integer[]{4}), integerArrayToByteArray(new Integer[]{Integer.valueOf(z ? 1 : 0)}));
    }

    public void resetData() {
        setParameter(integerArrayToByteArray(new Integer[]{5}), integerArrayToByteArray(new Integer[]{1}));
    }

    private byte[] integerArrayToByteArray(Integer[] numArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(numArr.length * 4);
        byteBufferAllocate.order(ByteOrder.nativeOrder());
        for (Integer num : numArr) {
            byteBufferAllocate.putInt(num.intValue());
        }
        return byteBufferAllocate.array();
    }

    private void byteArrayToIntegerArray(byte[] bArr, Integer[] numArr) {
        int i = 0;
        int i2 = 0;
        while (i < bArr.length && i2 < numArr.length) {
            numArr[i2] = Integer.valueOf(byteArrayToInt(bArr, i));
            i += 4;
            i2++;
        }
        if (i2 == numArr.length) {
            return;
        }
        throw new IllegalArgumentException("only converted " + i2 + " values out of " + numArr.length + " expected");
    }
}
