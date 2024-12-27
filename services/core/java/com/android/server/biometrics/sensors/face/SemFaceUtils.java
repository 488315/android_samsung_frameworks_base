package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.hardware.face.FaceAuthenticateOptions;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.Surface;

import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.android.server.biometrics.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class SemFaceUtils {
    public static Bundle mBundle;
    public static byte[] mFidoRequestData;
    public static byte[] mFidoResultData;
    public static boolean mNeedtoAuthenticateExt;
    public static Surface mSurface;

    public static String byteArrayToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            i =
                    MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                            "%02x", new Object[] {Integer.valueOf(bArr[i] & 255)}, sb, i, 1);
        }
        return sb.toString();
    }

    public static FaceAuthenticateOptions copyOptions(
            int i, FaceAuthenticateOptions faceAuthenticateOptions) {
        return new FaceAuthenticateOptions.Builder()
                .setUserId(i)
                .setSensorId(faceAuthenticateOptions.getSensorId())
                .setOpPackageName(faceAuthenticateOptions.getOpPackageName())
                .setAttributionTag(
                        faceAuthenticateOptions.getAttributionTag() == null
                                ? ""
                                : faceAuthenticateOptions.getAttributionTag())
                .setDisplayState(faceAuthenticateOptions.getDisplayState())
                .build();
    }

    public static ArrayList getFidoRequestDataAsArrayList() {
        ArrayList arrayList = new ArrayList();
        byte[] bArr = mFidoRequestData;
        if (bArr != null && bArr.length > 0) {
            for (byte b : bArr) {
                arrayList.add(Byte.valueOf(b));
            }
        }
        mFidoRequestData = null;
        return arrayList;
    }

    public static int getSecurityMode(Context context) {
        int i =
                1
                                == Settings.Secure.getIntForUser(
                                        context.getContentResolver(), "face_open_eyes", -1, -2)
                        ? 4
                        : 0;
        if (1
                        == Settings.Secure.getIntForUser(
                                context.getContentResolver(), "face_recognize_mask", -1, -2)
                && FaceManager.semIsSupportOnMask()) {
            i |= 16;
        }
        Slog.i("SemFace", "mode=0x" + Integer.toBinaryString(i));
        return i;
    }

    public static void setFidoRequestData(byte[] bArr) {
        if (Utils.DEBUG) {
            if (bArr == null) {
                Log.i("SemFace", "fidoRequestData is null");
            } else {
                Log.i("SemFace", "fidoRequestData(" + bArr.length + ") = " + Arrays.toString(bArr));
            }
        }
        mFidoRequestData = bArr;
    }

    public static void setFidoResultData(byte[] bArr) {
        mFidoResultData = bArr;
        if (!Utils.DEBUG || mFidoResultData.length >= 128) {
            return;
        }
        Log.i(
                "SemFace",
                "fidoResultData("
                        + mFidoResultData.length
                        + ") = "
                        + Arrays.toString(mFidoResultData));
    }
}
