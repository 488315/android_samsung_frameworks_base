package com.android.server.biometrics.sensors.fingerprint;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManagerInternal;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.Slog;
import android.util.TypedValue;
import android.view.DisplayInfo;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.server.LocalServices;
import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.Utils;
import com.android.server.biometrics.sensors.SemBioSysFsProvider;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public class SemUdfpsHelper {
    public static final boolean DEBUG = Utils.DEBUG;
    public static final boolean IS_OPTICAL = SemBiometricFeature.FP_FEATURE_SENSOR_IS_OPTICAL;
    public BurnInHelper mBurnInHelper;
    public final Injector mInjector;
    public SemUdfpsOpticalHelper mOpticalImpl;
    public String mSemSensorAreaWidth = "9";
    public String mSemSensorAreaHeight = "4";
    public String mSemSensorMarginBottom = "13.77";
    public String mSemSensorMarginLeft = "0";
    public String mSemSensorImageSize = "13.00";
    public String mSemSensorActiveArea = "14.80";
    public String mSemSensorDraggingArea = "5.00";

    public abstract class InstanceHolder {
        public static final SemUdfpsHelper INSTANCE = new SemUdfpsHelper(new Injector());
    }

    public class Injector implements SemBioSysFsProvider {
        public SemUdfpsTspManager getTspManager() {
            return SemUdfpsTspManager.get();
        }

        @Override // com.android.server.biometrics.sensors.SemBioSysFsProvider
        public byte[] readSysFs(String str) {
            return Utils.readFile(new File(str));
        }

        @Override // com.android.server.biometrics.sensors.SemBioSysFsProvider
        public void writeSysFs(String str, byte[] bArr) {
            Utils.writeFile(new File(str), bArr);
        }
    }

    public static SemUdfpsHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public SemUdfpsHelper(Injector injector) {
        this.mInjector = injector;
        if (IS_OPTICAL) {
            this.mOpticalImpl = new SemUdfpsOpticalHelper(injector);
        }
    }

    public void onBootActivityManagerReady(Context context) {
        readSensorAreaFromSysFs();
        setFodRect(context);
    }

    public final void readSensorAreaFromSysFs() {
        byte[] readSysFs = this.mInjector.readSysFs("/sys/class/fingerprint/fingerprint/position");
        if (readSysFs != null) {
            try {
                String[] split = new String(readSysFs, StandardCharsets.UTF_8).trim().split(",");
                if (split.length >= 8) {
                    this.mSemSensorMarginBottom = split[0];
                    this.mSemSensorMarginLeft = split[1];
                    this.mSemSensorAreaWidth = split[2];
                    this.mSemSensorAreaHeight = split[3];
                    this.mSemSensorImageSize = split[7];
                    this.mSemSensorActiveArea = split[5];
                    this.mSemSensorDraggingArea = split[8];
                }
                if (DEBUG) {
                    Slog.i("FingerprintService", "readSensorConfig: " + split[2] + " x " + split[3] + ", " + split[0] + ", " + split[1] + ", " + split[7] + ", " + split[5] + ", " + split[8]);
                }
            } catch (Exception e) {
                Slog.w("FingerprintService", "readSensorConfig: " + e.getMessage());
            }
        }
    }

    public final void setFodRect(Context context) {
        Point point = new Point();
        try {
            ((DisplayManagerInternal) LocalServices.getService(DisplayManagerInternal.class)).getNonOverrideDisplayInfo(0, new DisplayInfo());
            WindowManagerGlobal.getWindowManagerService().getInitialDisplaySize(0, point);
            if (Utils.isCutoutNotchHidden(context)) {
                point.y += SystemProperties.getInt("persist.sys.displayinset.top", 0);
            }
            Rect rect = new Rect();
            double parseDouble = Double.parseDouble(this.mSemSensorActiveArea) * r1.physicalXDpi * 0.03937007859349251d;
            double parseDouble2 = Double.parseDouble(this.mSemSensorMarginBottom) * r1.physicalXDpi * 0.03937007859349251d;
            double parseDouble3 = Double.parseDouble(this.mSemSensorMarginLeft) * r1.physicalXDpi * 0.03937007859349251d;
            double parseDouble4 = Double.parseDouble(this.mSemSensorAreaHeight) * r1.physicalXDpi * 0.03937007859349251d;
            int i = (int) parseDouble;
            int i2 = (point.x / 2) - ((i / 2) - ((int) parseDouble3));
            rect.left = i2;
            rect.right = i2 + i;
            int i3 = point.y - ((((int) parseDouble2) + (((int) parseDouble4) / 2)) + (i / 2));
            rect.top = i3;
            rect.bottom = i3 + i;
            this.mInjector.getTspManager().setFodRect(rect);
        } catch (Exception e) {
            Slog.w("FingerprintService", " setFodRect: ", e);
        }
    }

    public Bundle getInDisplaySensorArea() {
        return getInDisplaySensorArea(new Bundle());
    }

    public Bundle getInDisplaySensorArea(Bundle bundle) {
        bundle.putStringArray("sem_area", new String[]{this.mSemSensorAreaWidth, this.mSemSensorAreaHeight, this.mSemSensorMarginBottom, this.mSemSensorMarginLeft, this.mSemSensorImageSize, this.mSemSensorActiveArea, this.mSemSensorDraggingArea});
        if (IS_OPTICAL) {
            this.mOpticalImpl.getOpticalBrightnessData(bundle);
        }
        return bundle;
    }

    public Rect getFodSensorAreaRectForKeyguard(Context context) {
        Rect fodSensorAreaRect = getFodSensorAreaRect(context, -1, null);
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int applyDimension = (((int) TypedValue.applyDimension(5, Float.parseFloat(this.mSemSensorActiveArea), displayMetrics)) - ((int) TypedValue.applyDimension(5, Float.parseFloat(this.mSemSensorImageSize), displayMetrics))) + ((int) TypedValue.applyDimension(5, Float.parseFloat(this.mSemSensorDraggingArea), displayMetrics));
            int rotation = windowManager.getDefaultDisplay().getRotation();
            if (rotation == 0) {
                fodSensorAreaRect.left -= applyDimension;
                fodSensorAreaRect.right += applyDimension;
                fodSensorAreaRect.top -= applyDimension;
                fodSensorAreaRect.bottom += applyDimension;
            } else if (rotation == 1) {
                fodSensorAreaRect.left -= applyDimension;
                fodSensorAreaRect.right += applyDimension;
                fodSensorAreaRect.bottom += applyDimension;
                fodSensorAreaRect.top -= applyDimension;
            } else if (rotation == 2) {
                fodSensorAreaRect.right += applyDimension;
                fodSensorAreaRect.left -= applyDimension;
                fodSensorAreaRect.bottom += applyDimension;
                fodSensorAreaRect.top -= applyDimension;
            } else if (rotation == 3) {
                fodSensorAreaRect.right += applyDimension;
                fodSensorAreaRect.left -= applyDimension;
                fodSensorAreaRect.top -= applyDimension;
                fodSensorAreaRect.bottom += applyDimension;
            }
        } catch (Exception e) {
            Slog.w("FingerprintService", "getFodSensorAreaRectForKeyguard: " + e.getMessage());
        }
        if (DEBUG) {
            Slog.d("FingerprintService", "getFodSensorAreaRectForKeyguard: " + fodSensorAreaRect.toShortString());
        }
        return fodSensorAreaRect;
    }

    public Rect getFodSensorAreaRect(Context context, int i, Point point) {
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
            float applyDimension2 = TypedValue.applyDimension(5, Float.parseFloat(this.mSemSensorMarginBottom), displayMetrics);
            float applyDimension3 = TypedValue.applyDimension(5, Float.parseFloat(this.mSemSensorMarginLeft), displayMetrics);
            float applyDimension4 = TypedValue.applyDimension(5, Float.parseFloat(this.mSemSensorAreaHeight), displayMetrics);
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
                Slog.d("FingerprintService", "getFodSensorAreaRect: " + i + ", " + rect.toShortString());
            }
        } catch (Exception e) {
            Slog.w("FingerprintService", "" + e.getMessage());
        }
        return rect;
    }

    public int getSensorAreaMarginFromBottomForFod(Context context) {
        Rect fodSensorAreaRect = getFodSensorAreaRect(context, -1, null);
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        return point.y - fodSensorAreaRect.top;
    }

    public Bundle getSensorIconRandomPos(Context context, Bundle bundle) {
        if (this.mBurnInHelper == null) {
            this.mBurnInHelper = new BurnInHelper(context);
        }
        return this.mBurnInHelper.getNextPosition(bundle);
    }

    public SemUdfpsOpticalHelper getOpticalSensorHelper() {
        return this.mOpticalImpl;
    }

    public void dump(PrintWriter printWriter, Pair pair) {
        printWriter.println(" FOD : " + getInDisplaySensorArea().toShortString());
        printWriter.println(SemUdfpsTspManager.get().toDumpString());
        SemUdfpsOpticalHelper semUdfpsOpticalHelper = this.mOpticalImpl;
        if (semUdfpsOpticalHelper != null) {
            semUdfpsOpticalHelper.dump(printWriter, pair);
        }
    }

    public class BurnInHelper {
        public final int[] mIconArray;
        public int mIconLocationIndex;
        public int mMaxMovingSize;

        /* renamed from: mX */
        public int f1661mX;

        /* renamed from: mY */
        public int f1662mY;

        public BurnInHelper(Context context) {
            int i;
            setMaxMovingSize(context);
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

        public final void setMaxMovingSize(Context context) {
            this.mMaxMovingSize = (int) TypedValue.applyDimension(1, 10.0f, context.getResources().getDisplayMetrics());
            Slog.d("FingerprintService", "setMaxMovingSize() returned: " + this.mMaxMovingSize);
        }

        public final void updateLocation() {
            int i = this.mIconLocationIndex + 1;
            this.mIconLocationIndex = i;
            int i2 = this.mMaxMovingSize;
            int i3 = i % (i2 * i2);
            this.mIconLocationIndex = i3;
            int i4 = this.mIconArray[i3];
            this.f1661mX = (i4 / i2) - (i2 >> 1);
            this.f1662mY = (i4 % i2) - (i2 >> 1);
        }

        public final Bundle getNextPosition(Bundle bundle) {
            updateLocation();
            bundle.putInt("x", this.f1661mX);
            bundle.putInt("y", this.f1662mY);
            Slog.i("FingerprintService", "getNextPosition: " + this.f1661mX + ", " + this.f1662mY);
            return bundle;
        }
    }
}
