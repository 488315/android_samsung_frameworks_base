package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.Slog;
import android.util.TypedValue;
import android.view.WindowManager;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemBioSysFsProvider;
import com.android.server.biometrics.sensors.fingerprint.aidl.FingerprintProvider;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemUdfpsHelper {
    public static final boolean DEBUG = Utils.DEBUG;
    public static final boolean IS_OPTICAL = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
    public BurnInHelper mBurnInHelper;
    public final Supplier mGetTspManager;
    public final SemUdfpsOpticalHelper mOpticalImpl;
    public final SemBioSysFsProvider mSysFsProviderImpl;
    public String mSemSensorAreaWidth = "9";
    public String mSemSensorAreaHeight = "4";
    public String mSemSensorMarginBottom = "13.77";
    public String mSemSensorMarginLeft = "0";
    public String mSemSensorImageSize = "13.00";
    public String mSemSensorActiveArea = "14.80";
    public String mSemSensorDraggingArea = "5.00";
    public final Rect mFodRect = new Rect();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BurnInHelper {
        public final int[] mIconArray;
        public int mIconLocationIndex;
        public final int mMaxMovingSize;
        public int mX;
        public int mY;

        public BurnInHelper(Context context) {
            int i;
            this.mMaxMovingSize =
                    (int)
                            TypedValue.applyDimension(
                                    1, 10.0f, context.getResources().getDisplayMetrics());
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setMaxMovingSize() returned: "),
                    this.mMaxMovingSize,
                    "FingerprintService");
            int i2 = this.mMaxMovingSize;
            this.mIconArray = new int[i2 * i2];
            int i3 = 0;
            while (true) {
                i = this.mMaxMovingSize;
                if (i3 >= i * i) {
                    break;
                }
                this.mIconArray[i3] = i3;
                i3++;
            }
            for (int i4 = (i * i) - 1; i4 >= 0; i4--) {
                int random = (int) (Math.random() * (i4 + 1));
                if (random != i4) {
                    int[] iArr = this.mIconArray;
                    int i5 = iArr[random];
                    iArr[random] = iArr[i4];
                    iArr[i4] = i5;
                }
            }
            double random2 = Math.random();
            int i6 = this.mMaxMovingSize;
            this.mIconLocationIndex = (int) (random2 * i6 * i6);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class InstanceHolder {
        public static final SemUdfpsHelper INSTANCE =
                new SemUdfpsHelper(
                        new AnonymousClass1(), new SemUdfpsHelper$$ExternalSyntheticLambda0(1));

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.biometrics.sensors.fingerprint.SemUdfpsHelper$InstanceHolder$1, reason: invalid class name */
        public final class AnonymousClass1 implements SemBioSysFsProvider {}
    }

    public SemUdfpsHelper(SemBioSysFsProvider semBioSysFsProvider, Supplier supplier) {
        this.mSysFsProviderImpl = semBioSysFsProvider;
        this.mGetTspManager = supplier;
        if (IS_OPTICAL) {
            this.mOpticalImpl =
                    new SemUdfpsOpticalHelper(
                            semBioSysFsProvider,
                            new SemUdfpsHelper$$ExternalSyntheticLambda0(0),
                            SemBiometricFeature.FP_FEATURE_HW_LIGHT_SOURCE);
        }
    }

    public final void dump(PrintWriter printWriter, Pair pair) {
        StringBuilder sb = new StringBuilder(" FOD : ");
        Bundle bundle = new Bundle();
        getInDisplaySensorArea(bundle);
        sb.append(bundle.toShortString());
        printWriter.println(sb.toString());
        SemUdfpsTspManager semUdfpsTspManager = SemUdfpsTspManager.get();
        StringBuilder sb2 = new StringBuilder(" TSP : ");
        sb2.append((String) semUdfpsTspManager.mCommands.get(7));
        sb2.append("\n TSP : ");
        sb2.append(semUdfpsTspManager.mLastCmd);
        sb2.append(", ");
        sb2.append(semUdfpsTspManager.mIsLpMode);
        sb2.append(", ");
        sb2.append(semUdfpsTspManager.mIsStrictMode);
        sb2.append(", ");
        sb2.append(semUdfpsTspManager.mIsEnable);
        sb2.append(", ");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                sb2, semUdfpsTspManager.mIsHalfMode, printWriter);
        SemUdfpsOpticalHelper semUdfpsOpticalHelper = this.mOpticalImpl;
        if (semUdfpsOpticalHelper != null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            printWriter,
                            semUdfpsOpticalHelper.mDisplayPanelType,
                            " Optical, LD : ",
                            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                    printWriter,
                                    semUdfpsOpticalHelper.mBrightnessColor,
                                    " Optical, DT : ",
                                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                            KillPolicyManager$$ExternalSyntheticOutline0.m(
                                                    BinaryTransparencyService$$ExternalSyntheticOutline0
                                                            .m(
                                                                    new StringBuilder(
                                                                            " Optical, HW_LS : "),
                                                                    semUdfpsOpticalHelper
                                                                            .mIsSupportHwLightSource,
                                                                    printWriter,
                                                                    " Optical, B : "),
                                                    semUdfpsOpticalHelper.mMaxBrightness,
                                                    printWriter,
                                                    " Optical, N : "),
                                            semUdfpsOpticalHelper.mNits,
                                            printWriter,
                                            " Optical, C : "))),
                    semUdfpsOpticalHelper.mIsLimitedDisplayOn,
                    printWriter);
            synchronized (semUdfpsOpticalHelper.mMaskClientList) {
                try {
                    Iterator it =
                            ((HashMap) semUdfpsOpticalHelper.mMaskClientList).values().iterator();
                    while (it.hasNext()) {
                        printWriter.println(
                                " Optical, M : " + ((SemFpOpticalClient) it.next()).mPackageName);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (pair != null) {
                StringBuilder sb3 = new StringBuilder(" Optical, Calibrated time : ");
                byte[] bArr = new byte[256];
                int semRequest =
                        ((FingerprintProvider) ((ServiceProvider) pair.second))
                                .semRequest(((Integer) pair.first).intValue(), 40, 0, null, bArr);
                sb3.append(
                        TextUtils.emptyIfNull(
                                semRequest > 0
                                        ? new String(
                                                        Arrays.copyOf(bArr, semRequest),
                                                        StandardCharsets.UTF_8)
                                                .trim()
                                        : null));
                printWriter.println(sb3.toString());
            }
        }
    }

    public final Rect getFodSensorAreaRect(Context context, int i, Point point) {
        Rect rect = new Rect();
        if (!SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE) {
            return rect;
        }
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (point == null) {
                point = new Point();
                windowManager.getDefaultDisplay().getRealSize(point);
            }
            if (i < 0) {
                i = windowManager.getDefaultDisplay().getRotation();
            }
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float applyDimension = TypedValue.applyDimension(5, 14.5f, displayMetrics);
            float applyDimension2 =
                    TypedValue.applyDimension(
                            5, Float.parseFloat(this.mSemSensorMarginBottom), displayMetrics);
            float applyDimension3 =
                    TypedValue.applyDimension(
                            5, Float.parseFloat(this.mSemSensorMarginLeft), displayMetrics);
            float applyDimension4 =
                    TypedValue.applyDimension(
                            5, Float.parseFloat(this.mSemSensorAreaHeight), displayMetrics);
            int i2 = (int) applyDimension;
            int i3 = (i2 / 2) - ((int) applyDimension3);
            int i4 = ((int) applyDimension2) + (((int) applyDimension4) / 2) + (i2 / 2);
            if (i == 0) {
                int i5 = (point.x / 2) - i3;
                rect.left = i5;
                rect.right = i5 + i2;
                int i6 = point.y - i4;
                rect.top = i6;
                rect.bottom = i6 + i2;
            } else if (i == 1) {
                int i7 = point.x - i4;
                rect.left = i7;
                rect.right = i7 + i2;
                int i8 = (point.y / 2) + i3;
                rect.bottom = i8;
                rect.top = i8 - i2;
            } else if (i == 2) {
                int i9 = (point.x / 2) + i3;
                rect.right = i9;
                rect.left = i9 - i2;
                rect.bottom = i4;
                rect.top = i4 - i2;
            } else if (i == 3) {
                rect.right = i4;
                rect.left = i4 - i2;
                int i10 = (point.y / 2) - i3;
                rect.top = i10;
                rect.bottom = i10 + i2;
            }
            if (DEBUG) {
                Slog.d(
                        "FingerprintService",
                        "getFodSensorAreaRect: " + i + ", " + rect.toShortString());
            }
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder(""), "FingerprintService");
        }
        return rect;
    }

    public final void getInDisplaySensorArea(Bundle bundle) {
        bundle.putStringArray(
                "sem_area",
                new String[] {
                    this.mSemSensorAreaWidth,
                    this.mSemSensorAreaHeight,
                    this.mSemSensorMarginBottom,
                    this.mSemSensorMarginLeft,
                    this.mSemSensorImageSize,
                    this.mSemSensorActiveArea,
                    this.mSemSensorDraggingArea
                });
        if (IS_OPTICAL) {
            SemUdfpsOpticalHelper semUdfpsOpticalHelper = this.mOpticalImpl;
            bundle.putFloat("brightness", semUdfpsOpticalHelper.mMaxBrightness);
            bundle.putFloat("lightColor", semUdfpsOpticalHelper.mNits);
            bundle.putString("nits", semUdfpsOpticalHelper.mBrightnessColor);
        }
    }
}
