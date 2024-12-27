package com.samsung.android.media.audiofx;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.audiofx.AudioEffect;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class SemVolumeMonitor extends AudioEffect {
    public static final UUID EFFECT_TYPE_VOLUME_MONITOR =
            UUID.fromString("f15b944b-0202-451e-a6ff-c61f11beda02");
    private static final int PARAM_GET_ONE_MIN_SCORE_STATUS = 1;
    private static final int PARAM_RESET_DATA = 5;
    private static final int PARAM_SET_ABS_VOLUME_STATE = 4;
    private static final int PARAM_SET_BT_VOL_INDEX = 2;
    private static final int PARAM_SET_ON_OFF = 3;
    private static final String TAG = "SemVolumeMonitor";

    public SemVolumeMonitor(int priority, int audioSession) throws IllegalArgumentException {
        super(EFFECT_TYPE_VOLUME_MONITOR, EFFECT_TYPE_NULL, priority, audioSession);
        if (audioSession == 0) {
            Log.w(
                    TAG,
                    "WARNING: attaching an SemVolumeMonitor to global output mix is deprecated!");
        }
    }

    public byte[] getOneMinScoreStatus(int energyValueSizeInByte, int scoreValueSizeInByte) {
        Integer[] params = {1};
        byte[] paramBytes = integerArrayToByteArray(params);
        int valueSizeInByte = Math.max(energyValueSizeInByte, scoreValueSizeInByte);
        byte[] valueBytes = new byte[valueSizeInByte];
        Log.i(
                TAG,
                "getOneHourRms: call getParameter. bytes:"
                        + valueSizeInByte
                        + "=max("
                        + energyValueSizeInByte
                        + ","
                        + scoreValueSizeInByte
                        + NavigationBarInflaterView.KEY_CODE_END);
        getParameter(paramBytes, valueBytes);
        Log.i(TAG, "getOneHourRms: getParameter done");
        return valueBytes;
    }

    public void setBluetoothVolume(int volumeIndex) {
        Integer[] params = {2};
        Integer[] values = {Integer.valueOf(volumeIndex)};
        byte[] paramBytes = integerArrayToByteArray(params);
        byte[] valueBytes = integerArrayToByteArray(values);
        Log.i(TAG, "setBluetoothVolume: call setParameter");
        try {
            setParameter(paramBytes, valueBytes);
        } catch (IllegalStateException e) {
            Log.e(TAG, "setBluetoothVolume#setParameter", e);
        }
        Log.i(TAG, "setBluetoothVolume: setParameter done");
    }

    public void onOff(boolean z) {
        Integer[] numArr = {Integer.valueOf(z ? 1 : 0)};
        byte[] integerArrayToByteArray = integerArrayToByteArray(new Integer[] {3});
        byte[] integerArrayToByteArray2 = integerArrayToByteArray(numArr);
        Log.i(TAG, "onOff: call setParameter");
        setParameter(integerArrayToByteArray, integerArrayToByteArray2);
        Log.i(TAG, "onOff: setParameter done");
    }

    public void setAbsoluteVolumeState(boolean z) {
        setParameter(
                integerArrayToByteArray(new Integer[] {4}),
                integerArrayToByteArray(new Integer[] {Integer.valueOf(z ? 1 : 0)}));
    }

    public void resetData() {
        byte[] paramBytes = integerArrayToByteArray(new Integer[] {5});
        byte[] valueBytes = integerArrayToByteArray(new Integer[] {1});
        setParameter(paramBytes, valueBytes);
    }

    private byte[] integerArrayToByteArray(Integer[] values) {
        ByteBuffer converter = ByteBuffer.allocate(values.length * 4);
        converter.order(ByteOrder.nativeOrder());
        for (Integer value : values) {
            converter.putInt(value.intValue());
        }
        return converter.array();
    }

    private void byteArrayToIntegerArray(byte[] valuesIn, Integer[] valuesOut) {
        int inIndex = 0;
        int outIndex = 0;
        while (inIndex < valuesIn.length && outIndex < valuesOut.length) {
            valuesOut[outIndex] = Integer.valueOf(byteArrayToInt(valuesIn, inIndex));
            inIndex += 4;
            outIndex++;
        }
        int outIndex2 = valuesOut.length;
        if (outIndex != outIndex2) {
            throw new IllegalArgumentException(
                    "only converted "
                            + outIndex
                            + " values out of "
                            + valuesOut.length
                            + " expected");
        }
    }
}
